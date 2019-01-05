import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * android 规范为 sw320px 下 1px = 1dp
 * 自定义 最小宽度 300px 下  1px = 1sdp
 *
 */
public class DimenTool {

    public static void gen() {
        //以此文件夹下的dimens.xml文件内容为初始值参照
        File file = new File("./app/src/main/res/values/dimens.xml");

        BufferedReader reader = null;
        StringBuilder sw240 = new StringBuilder();   //0.8
        StringBuilder sw270 = new StringBuilder();  //0.9
        StringBuilder sw300 = new StringBuilder();  //1
        StringBuilder sw320 = new StringBuilder();  //1.06667
        StringBuilder sw330 = new StringBuilder();  //1.1
        StringBuilder sw360 = new StringBuilder();  //1.2
        StringBuilder sw390 = new StringBuilder();  //1.3
        StringBuilder sw420 = new StringBuilder();  //1.4
        StringBuilder sw450 = new StringBuilder();  //1.5
        StringBuilder sw480 = new StringBuilder();  //1.6
        StringBuilder sw510 = new StringBuilder(); //1.7
        StringBuilder sw540 = new StringBuilder();
        StringBuilder sw570 = new StringBuilder();
        StringBuilder sw600 = new StringBuilder();
        StringBuilder sw630 = new StringBuilder();
        StringBuilder sw660 = new StringBuilder();
        StringBuilder sw690 = new StringBuilder();
        StringBuilder sw720 = new StringBuilder();
        StringBuilder sw750 = new StringBuilder();
        StringBuilder sw780 = new StringBuilder();
        StringBuilder sw810 = new StringBuilder();
        StringBuilder sw840 = new StringBuilder();
        StringBuilder sw870 = new StringBuilder();
        StringBuilder sw900 = new StringBuilder();
        StringBuilder sw930 = new StringBuilder();
        StringBuilder sw1080 = new StringBuilder();

        try {

            System.out.println("生成不同分辨率：");

            reader = new BufferedReader(new FileReader(file));

            String tempString;

            int line = 1;

            // 一次读入一行，直到读入null为文件结束

            while ((tempString = reader.readLine()) != null) {


                if (tempString.contains("</dimen>")) {

                    //tempString = tempString.replaceAll(" ", "");

                    String start = tempString.substring(0, tempString.indexOf(">") + 1);

                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    Double num = Double.parseDouble
                            (tempString.substring(tempString.indexOf(">") + 1,
                                    tempString.indexOf("</dimen>") - 2));

                    //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。
                    sw240.append(start).append( String.format("%.2f",num*0.8)).append(end).append("\r\n");
                    sw270.append(start).append( String.format("%.2f",num*0.9)).append(end).append("\r\n");
                    sw300.append(start).append( String.format("%.2f",num*1.0)).append(end).append("\r\n");
                    sw320.append(start).append( String.format("%.2f",num*1.06667)).append(end).append("\r\n");
                    sw330.append(start).append( String.format("%.2f",num*1.1)).append(end).append("\r\n");
                    sw360.append(start).append( String.format("%.2f",num*1.2)).append(end).append("\r\n");
                    sw390.append(start).append( String.format("%.2f",num*1.3)).append(end).append("\r\n");
                    sw420.append(start).append( String.format("%.2f",num*1.4)).append(end).append("\r\n");
                    sw450.append(start).append( String.format("%.2f",num*1.5)).append(end).append("\r\n");
                    sw480.append(start).append( String.format("%.2f",num*1.6)).append(end).append("\r\n");
                    sw510.append(start).append( String.format("%.2f",num*1.7)).append(end).append("\r\n");
                    sw540.append(start).append( String.format("%.2f",num*1.8)).append(end).append("\r\n");
                    sw570.append(start).append( String.format("%.2f",num*1.9)).append(end).append("\r\n");
                    sw600.append(start).append( String.format("%.2f",num*2.0)).append(end).append("\r\n");
                    sw630.append(start).append( String.format("%.2f",num*2.1)).append(end).append("\r\n");
                    sw660.append(start).append( String.format("%.2f",num*2.2)).append(end).append("\r\n");
                    sw690.append(start).append( String.format("%.2f",num*2.3)).append(end).append("\r\n");
                    sw720.append(start).append( String.format("%.2f",num*2.4)).append(end).append("\r\n");
                    sw750.append(start).append( String.format("%.2f",num*2.5)).append(end).append("\r\n");
                    sw780.append(start).append( String.format("%.2f",num*2.6)).append(end).append("\r\n");
                    sw810.append(start).append( String.format("%.2f",num*2.7)).append(end).append("\r\n");
                    sw840.append(start).append( String.format("%.2f",num*2.8)).append(end).append("\r\n");
                    sw870.append(start).append( String.format("%.2f",num*2.9)).append(end).append("\r\n");
                    sw900.append(start).append( String.format("%.2f",num*3.0)).append(end).append("\r\n");
                    sw930.append(start).append( String.format("%.2f",num*3.1)).append(end).append("\r\n");
                    sw1080.append(start).append( String.format("%.2f",num*3.6)).append(end).append("\r\n");



                } else {
                    sw240.append(tempString).append("\n");
                    sw270.append(tempString).append("\n");
                    sw300.append(tempString).append("\n");
                    sw320.append(tempString).append("\n");
                    sw330.append(tempString).append("\n");
                    sw360.append(tempString).append("\n");
                    sw390.append(tempString).append("\n");
                    sw420.append(tempString).append("\n");
                    sw450.append(tempString).append("\n");
                    sw480.append(tempString).append("\n");
                    sw510.append(tempString).append("\n");
                    sw540.append(tempString).append("\n");
                    sw570.append(tempString).append("\n");
                    sw600.append(tempString).append("\n");
                    sw630.append(tempString).append("\n");
                    sw660.append(tempString).append("\n");
                    sw690.append(tempString).append("\n");
                    sw720.append(tempString).append("\n");
                    sw750.append(tempString).append("\n");
                    sw780.append(tempString).append("\n");
                    sw810.append(tempString).append("\n");
                    sw840.append(tempString).append("\n");
                    sw870.append(tempString).append("\n");
                    sw900.append(tempString).append("\n");
                    sw930.append(tempString).append("\n");
                    sw1080.append(tempString).append("\n");
                }
                line++;
            }

            reader.close();


            String sw240file = "./app/src/main/res/values-sw240dp/dimens.xml";
            String sw270file = "./app/src/main/res/values-sw270dp/dimens.xml";
            String sw300file = "./app/src/main/res/values-sw300dp/dimens.xml";
            String sw320file = "./app/src/main/res/values-sw320dp/dimens.xml";
            String sw330file = "./app/src/main/res/values-sw330dp/dimens.xml";
            String sw360file = "./app/src/main/res/values-sw360dp/dimens.xml";
            String sw390file = "./app/src/main/res/values-sw390dp/dimens.xml";
            String sw420file = "./app/src/main/res/values-sw420dp/dimens.xml";
            String sw450file = "./app/src/main/res/values-sw450dp/dimens.xml";
            String sw480file = "./app/src/main/res/values-sw480dp/dimens.xml";
            String sw510file = "./app/src/main/res/values-sw510dp/dimens.xml";
            String sw540file = "./app/src/main/res/values-sw540dp/dimens.xml";
            String sw570file = "./app/src/main/res/values-sw570dp/dimens.xml";
            String sw600file = "./app/src/main/res/values-sw600dp/dimens.xml";
            String sw630file = "./app/src/main/res/values-sw630dp/dimens.xml";
            String sw660file = "./app/src/main/res/values-sw660dp/dimens.xml";
            String sw690file = "./app/src/main/res/values-sw690dp/dimens.xml";
            String sw720file = "./app/src/main/res/values-sw720dp/dimens.xml";
            String sw750file = "./app/src/main/res/values-sw750dp/dimens.xml";
            String sw780file = "./app/src/main/res/values-sw780dp/dimens.xml";
            String sw810file = "./app/src/main/res/values-sw810dp/dimens.xml";
            String sw840file = "./app/src/main/res/values-sw840dp/dimens.xml";
            String sw870file = "./app/src/main/res/values-sw870dp/dimens.xml";
            String sw900file = "./app/src/main/res/values-sw900dp/dimens.xml";
            String sw930file = "./app/src/main/res/values-sw930dp/dimens.xml";
            String sw1080file = "./app/src/main/res/values-sw1080dp/dimens.xml";




            //将新的内容，写入到指定的文件中去
            writeFile(sw240file, sw240.toString());
            writeFile(sw270file, sw270.toString());
            writeFile(sw300file, sw300.toString());
            writeFile(sw320file, sw320.toString());
            writeFile(sw330file, sw330.toString());
            writeFile(sw360file, sw360.toString());
            writeFile(sw390file, sw390.toString());
            writeFile(sw420file, sw420.toString());
            writeFile(sw450file, sw450.toString());
            writeFile(sw480file, sw480.toString());
            writeFile(sw510file, sw510.toString());
            writeFile(sw540file, sw540.toString());
            writeFile(sw570file, sw570.toString());
            writeFile(sw600file, sw600.toString());
            writeFile(sw630file, sw630.toString());
            writeFile(sw660file, sw660.toString());
            writeFile(sw690file, sw690.toString());
            writeFile(sw720file, sw720.toString());
            writeFile(sw750file, sw750.toString());
            writeFile(sw780file, sw780.toString());
            writeFile(sw810file, sw810.toString());
            writeFile(sw840file, sw840.toString());
            writeFile(sw870file, sw870.toString());
            writeFile(sw900file, sw900.toString());
            writeFile(sw930file, sw930.toString());
            writeFile(sw1080file, sw1080.toString());



        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (reader != null) {

                try {

                    reader.close();

                } catch (IOException e1) {

                    e1.printStackTrace();

                }

            }

        }

    }


    /**
     * 写入方法
     *
     */

    public static void writeFile(String file, String text) {


        PrintWriter out = null;

        try {
            String [] dirStr = file.split("/dimens.xml");
            File dir = new File(dirStr[0]);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File f = new File(file);
            if (!f.exists()){
                f.createNewFile();
            }



            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            out.println(text);

        } catch (IOException e) {

            e.printStackTrace();

        }



        out.close();

    }
    public static void main(String[] args) {

        gen();

    }

}
