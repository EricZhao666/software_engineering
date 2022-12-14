import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lab1_2 {
    static int total_num=0,switch_num=0,ifElseNum=0,ifElseIfNum=0;
	public static void main(String [] args) throws Exception {
        Scanner scanner=new Scanner(System.in);
		String code="";
        String fileName =scanner.nextLine();
        int level=scanner.nextInt();
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
        if (level==1){
            Find_All(keyArr,code);
        }
        else if (level==2){
            Find_All(keyArr,code);
            Find_Switch(code);
        }else if (level>=3){
            Find_All(keyArr,code);
            Find_Switch(code);
            processElse(code);
        }
        scanner.close();
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
        System.out.println();
    }

    //Compute if-else and if-elseif-else
    public static void processElse(String code) {
        Pattern p = Pattern.compile("else\\s*if|else|if");
        Matcher matcher=p.matcher(code);
        Stack<String> s = new Stack();
        while(matcher.find()) {
            String temp=code.substring(matcher.start(),matcher.end());
            s.push(temp);
        }
        Stack<String> res = new Stack<String>();
        boolean flag = false;
        while (!s.isEmpty()) {
            String temp = s.pop();
            if (temp.equals("else")) {
                res.push(temp);
            } else if (temp.equals("else if")) {
                res.push(temp);

            } else {//说明我们遇到的是if
                //当res栈的顶端是else if的时候我们就需要循环pop出else-if一直到else为止
                while (res.peek().equals("else if")) {
                    res.pop();
                    //做个标记说明是从else——if来的，而不是else
                    flag = true;

                }
                if (res.peek().equals("else")) {
                    res.pop();
                }
                //如果是else-if来的话就加到elseif上
                if (flag) {
                    ifElseIfNum++;
                    flag = false;
                    //否则加到else上
                } else {
                    ifElseNum++;
                }
            }
        }
        System.out.println("if-else num: " + ifElseNum);
        System.out.println("if-elseif-else: " + ifElseIfNum);
    }


}