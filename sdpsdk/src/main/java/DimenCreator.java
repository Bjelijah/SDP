import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DimenCreator {




    public static void writeFile( String text) {
        String file = "./sdpsdk/src/main/res/values/dimens.xml";

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

    private static void addDp(StringBuilder sb){
        final String dp = "\t<dimen name=\"{0}sdp\">{1}dp</dimen>\n";
        for (int i=-600;i<601;i++){
            if (i<0){
                sb.append(dp.replace("{0}","_minus"+Math.abs(i)).replace("{1}",i+""));
            }else{
                sb.append(dp.replace("{0}","_"+i).replace("{1}",i+""));
            }

        }
    }

    private static void addSp(StringBuilder sb){
        final String dp = "\t<dimen name=\"{0}ssp\">{1}sp</dimen>\n";
        for (int i=0;i<601;i++){
            sb.append(dp.replace("{0}","_"+i).replace("{1}",i+""));
        }
    }


    private static String createDimen(){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        addDp(sb);
        addSp(sb);
        sb.append("</resources>");
        return sb.toString();
    }




    public static void main(String[] args){
        writeFile(createDimen());
    }


}
