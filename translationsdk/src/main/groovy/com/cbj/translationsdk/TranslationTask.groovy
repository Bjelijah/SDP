package com.cbj.translationsdk

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskAction
import org.json.JSONArray
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class TranslationTask extends DefaultTask {

    Property<String> from
    Property<String> to
    Property<String> projectDir
    HashMap<String,String> srcMap = new HashMap<>()
    ConcurrentHashMap<String,String> outMap = new ConcurrentHashMap<>()
    File outFile
    @TaskAction
    void translationTask(){
        println "TranslationTask groovy  output from=${from.getOrNull()}  to=${to.getOrNull()}"
        if (from.getOrNull()==null || to.getOrNull() == null || projectDir.getOrNull() == null) return

        String rootDir = projectDir.get()
        String srcFilePath = rootDir + "/src/main/res/values-${from.get()}/strings.xml"
        File src = new File(srcFilePath)
        if (!src.exists()){
            logger.error("${src.absolutePath} not exists")
            return
        }
        String outDirPath = rootDir + "/src/main/res/values-${to.get()}"
        File outDir = new File(outDirPath)
        if (!outDir.exists()){
            outDir.mkdirs()
        }
        outFile = new File(outDirPath+"/strings.xml"  )
        if (outFile.exists()){
            outFile.delete()
        }



        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
        DocumentBuilder builder = factory.newDocumentBuilder()
        Document d = builder.parse(srcFilePath)
        def list = d.getElementsByTagName("resources")
        StringBuffer buf = new StringBuffer()
        buf.setLength(0)
        for (int i=0;i<list.getLength();i++){
            def node = list.item(i)
            def childNodes = node.getChildNodes()
            for (int j=0;j<childNodes.length;j++){
                def item = childNodes.item(j)
                if (item.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) item
                    def v = element.textContent
                    def name = element.getAttribute("name")
//                    println("name="+name+ "       value="+v)
                    srcMap.put(name,v)
                    if (v.startsWith("@string/") || v.length() <= 3){
                        outMap.put(name,v)
                    } else {
                        String vTmp = v.replaceAll("\\.","。")
                        String needTranslation = URLEncoder.encode(vTmp,"utf-8")+").%20"
                        if (buf.size() + needTranslation.length() > 1500){
                            translation(buf.toString())
                            buf.setLength(0)
                        }
                        buf.append(needTranslation)
                    }
                }
            }
        }
        if (buf.size()>0){
            translation(buf.toString())
        }

        println(">>>srcMap="+srcMap.size())


    }

    private void translation(String data){
        if (data.length()==0)return
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()
        String url = "https://translate.google.com/translate_a/single?client=gtx&sl=${from.get()}&tl=${to.get()}&dt=t&q="+data
//        println(">>>url="+url)
        Request req = new Request.Builder().url(url).get().build()
        Call call = httpClient.newCall(req)
        Response res = call.execute()
        boolean translationRes = parseTranslation(res.body().string())
        if (!translationRes){
            println("url="+url)
        }

        if (outMap.size() == srcMap.size()){
            createNewXml()
        }
    }



    private boolean parseTranslation(String s){
//        println("res="+s)
        boolean  translationRes = true
        try{
            JSONArray arr = new JSONArray(s)
            def item0 = arr.get(0)
//            println("arr="+arr)
//            println("item0="+item0.getClass().name)
            if (item0 instanceof JSONArray){
                def list = (JSONArray) item0
                StringBuffer noFindTrsBuffer = new StringBuffer()
                StringBuffer noFindStrBuffer = new StringBuffer()
                for (int i=0;i<list.size();i++){
                    def item = list.get(i)
//                    println("item="+item.getClass().name)

                    if (item instanceof JSONArray){
                        def transList = (JSONArray) item
                        String s1 = transList.get(0).toString().replaceAll("\\.","").replaceAll("。",".").trim()
                        String s2 = transList.get(1).toString().replaceAll("\\.","").replaceAll("。",".").trim()
                        if (s1.endsWith(")")){
                            s1 = s1.substring(0,s1.length()-1)
                        }
                        if (s2.endsWith(")")){
                            s2 = s2.substring(0,s2.length()-1)
                        }
//                        println("s1="+s1+"  s2="+s2)
                        def findMap = srcMap.findAll {
                            it.value.trim() == s2.trim()
                        }
                        if (findMap.size()>0){
                            findMap.forEach { k,v->
                                outMap.put(k,s1)
                            }
                            noFindStrBuffer.setLength(0)
                            noFindTrsBuffer.setLength(0)
                        } else {
                            if (noFindStrBuffer.size()>0){
                                noFindStrBuffer.append(" ")
                            }
                            if (noFindTrsBuffer.size()>0){
                                noFindTrsBuffer.append(" ")
                            }

                            noFindTrsBuffer.append(s1)
                            noFindStrBuffer.append(s2)
                            findMap = srcMap.findAll {
                                it.value.trim() == noFindStrBuffer.toString().trim()
                            }
                            if (findMap.size()>0){
                                findMap.forEach { k,v->
                                    outMap.put(k,noFindTrsBuffer.toString())
                                }
                                noFindStrBuffer.setLength(0)
                                noFindTrsBuffer.setLength(0)
                            } else {
                                logger.error("not find s1="+s1+"  s2="+s2+" noFindStrBuffer="+noFindStrBuffer.toString())
                                translationRes = false
                            }
                        }
                    }
                }
            }


        }catch (Exception e){
            logger.error(e.message)
        }
        println(">>>outMapSize="+outMap.size())
        return translationRes
    }

    private void createNewXml(){
        println(">>> createNewXml")
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
        DocumentBuilder builder = factory.newDocumentBuilder()
        Document xmlDoc = builder.newDocument()
        def outRoot = xmlDoc.createElement("resources")
        outMap.forEach { k,v->
            def outChild = xmlDoc.createElement("string")
            outChild.setAttribute("name",k)
            outChild.setTextContent(v)
            outRoot.appendChild(outChild)
        }
        xmlDoc.appendChild(outRoot)

        def source = new DOMSource(xmlDoc)
        def result = new StreamResult(new OutputStreamWriter(
                new FileOutputStream(outFile),"utf-8"
        ))
        def xFormer = TransformerFactory.newInstance().newTransformer()
        xFormer.transform(source,result)
    }


}