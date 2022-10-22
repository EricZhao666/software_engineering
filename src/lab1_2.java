import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lab1_2 {
    static int total_num=0,switch_num=0,if_else_num=0,if_else_if_else_num=0;
	public static void main(String [] args) throws Exception {

		String code="";
        String fileName ="src/samplec.c";
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line=bufferedReader.readLine();
        while(line!=null) {
        	code+=line;
        	line=bufferedReader.readLine();
        }
        String keywords="abstract、assert、boolean、break、byte、case、"
                + "catch、char、class、continue、default、do、double、else、"
                + "enum、extends、final、finally、float、for、if、implements、"
                + "import、int、interface、instanceof、long、native、new、"
                + "package、private、protected、public、return、short、static、"
                + "strictfp、super、switch、synchronized、this、throw、throws、"
                + "transient、try、void、volatile、while";//all keywords
        String []keyArr=keywords.split("、");
        //check total key word
        Find_All(keyArr,code);
        //check switch and case
        Find_Switch(code);
        //Compute if-else and if-elseif-else
        Find_if(code);
	}

    //check total key word
    public static void Find_All(String[] keyArr,String code){
        for(int i=0;i<keyArr.length;i++) {
            Pattern p=Pattern.compile("[^a-z]"+keyArr[i]+"[^a-z]");
            Matcher matcher=p.matcher(code);
            while(matcher.find()) {
                total_num++;
            }
        }
        System.out.println("total num is "+total_num);
    }
    //check switch and case
    public static void Find_Switch(String code){
        //check switch
        Pattern p=Pattern.compile("switch");
        Matcher matcher=p.matcher(code);
        while(matcher.find()) {
            switch_num++;
        }

        //check case
        p=Pattern.compile("switch.*?}");
        matcher=p.matcher(code);
        List case_num=new ArrayList();
        while(matcher.find()) {
            String tempText=matcher.toString();//get one switch section
            Pattern temp_p=Pattern.compile("case");
            Matcher temp_matcher=temp_p.matcher(tempText);
            int temp_case_num=0;
            while(temp_matcher.find()) {
                temp_case_num++;
            }
            case_num.add(temp_case_num);
        }
        System.out.println("switch num is "+switch_num);
        System.out.print("case num are ");
        for(int i=0;i<case_num.size();i++) {
            System.out.print(case_num.get(i)+" ");
        }
    }

    //Compute if-else and if-elseif-else
    public static void Find_if(String code){
        Pattern p = Pattern.compile("else\\s*if|else|if");
        Matcher matcher=p.matcher(code);
        Stack<String> s = new Stack();
        int count=0;
        while(matcher.find()) {
            String temp=code.substring(matcher.start(),matcher.end());
            s.push(temp);
        }
        while(!s.isEmpty()) {
            String temp=s.pop();
            if(temp.equals("else")) {
                String temp2=s.pop();
                if(temp2.equals("if")) if_else_num++;
                else if(temp2.equals("else if")) if_else_if_else_num++;
                else if(temp2.equals("else")) count+=2;
            }else if(count>0) {
                if(temp.equals("else if")) if_else_if_else_num++;
                else if(temp.equals("if")) if_else_num++;
                count--;
            }
        }
        System.out.println("\nif else num is "+if_else_num);
        System.out.println("if else if else num is "+if_else_if_else_num);
    }

}