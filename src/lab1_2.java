import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lab1_2 {
	public static void main(String [] args) throws Exception {
		int total_num=0,switch_num=0,if_else_num=0,if_else_if_else_num=0;
		String code="";
        String fileName ="src/samplec.c";
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line=bufferedReader.readLine();
        while(line!=null) {
        	code+=line;
        	line=bufferedReader.readLine();
        }
		

	
	}

}