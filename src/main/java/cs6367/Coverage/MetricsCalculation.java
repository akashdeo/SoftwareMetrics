package cs6367.Coverage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;





public class MetricsCalculation {
	
private static Map<String,Map<String,ArrayList<String>>> finallist = new HashMap<String,Map<String,ArrayList<String>>>();


private static Set<String> StatementExpressions = new HashSet<String>(Arrays.asList(""
		+ "No. of Java Statements:","No. of expressions:","No. of casts:","No. of jump instructions:"));
private static Set<String> Operators = new HashSet<String>(Arrays.asList("No. of operators:"));
private static Set<String> Operands=new HashSet<String>(Arrays.asList("No. of Operands:"));


private static Set<String> localexternalvariabledeclaration = new HashSet<String>(Arrays.asList("No. of local methods:",
        "Variables Declared:","Exceptions Found:","No. of external methods:","Variables Referenced:","Class References:"));


public static void print(){
    PrintWriter writer = null;

    File report = new File("metrics.txt");
    try {
        writer = new PrintWriter(report);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    
    for (Map.Entry<String, Map<String, ArrayList<String>>> resultEntry :finallist.entrySet()) {
        
        
    	int uniqueOperatorcount=0;
        int operatorCount=0;
        int uniqueOperand=0;
        int operandCount=0;
        writer.println("Method Name: "+resultEntry.getKey());
        for (Map.Entry<String, ArrayList<String>> result :resultEntry.getValue().entrySet()) {

            if(StatementExpressions.contains(result.getKey())){
                writer.print(result.getKey());
                writer.println(result.getValue().size());
            }else if(localexternalvariabledeclaration.contains(result.getKey())){
                writer.print(result.getKey());
                writer.println(new HashSet<>(result.getValue()).size());
            }else if (Operators.contains(result.getKey())&&!Operands.contains(result.getKey())){
                int count =  result.getValue().size();
                int uniqueCount = new HashSet<>(result.getValue()).size();
                uniqueOperatorcount=uniqueCount;
                operatorCount=count;
                writer.print("Unique Operators: "+result.getKey());
                writer.println(uniqueCount);
                writer.print("total Operators:"+result.getKey());
                writer.println(count);

            }
            else if(Operands.contains(result.getKey())&&!Operators.contains(result.getKey()))
            {
               int count=result.getValue().size();
               int uniqueCount = new HashSet<>(result.getValue()).size();
               uniqueOperand=uniqueCount;
               operandCount=count;
               writer.print("Unique Operands:"+result.getKey());
               writer.println(uniqueCount);
               writer.print("Operands:"+result.getKey());
               writer.println(count);
            }
            else if(Operands.contains(result.getKey())&&Operators.contains(result.getKey()))
            {
            	int count =  result.getValue().size();
                int uniqueCount = new HashSet<>(result.getValue()).size();
                uniqueOperatorcount=uniqueCount;
                operatorCount=count;
                writer.print("Unique Operators: "+result.getKey());
                writer.println(uniqueCount);
                writer.print("total Operators:"+result.getKey());
                writer.println(count);
                int count1=result.getValue().size();
                int uniqueCount1 = new HashSet<>(result.getValue()).size();
                uniqueOperand=uniqueCount1;
                operandCount=count1;
                writer.print("Unique Operands:"+result.getKey());
                writer.println(uniqueCount1);
                writer.print("Operands:"+result.getKey());
                writer.println(count1);
            }
           
            		else {
                writer.print(result.getKey());
                for (String result1 : new HashSet<>(result.getValue())) {
                    writer.println(result1);
                }
            }
            
            
        }
        int halsteadLength=operandCount+operatorCount;
        int halsteadVocabulary=uniqueOperand+uniqueOperatorcount;
        writer.println("Halstead Length:"+halsteadLength);
        writer.println("Halstead Vocabulary:"+halsteadVocabulary);
       
        if(uniqueOperand!=0&&halsteadVocabulary!=0.0&&uniqueOperatorcount!=0)
        {
        double halsteadDifficulty=(uniqueOperatorcount/(2*1.0))*(operandCount/uniqueOperand*1.0)*1.0;
        double halsteadVolume=halsteadLength*(Math.log10(halsteadVocabulary)/Math.log10(2))*1.0;
        double halsteadEffort=halsteadDifficulty*halsteadVolume*1.0;
        double halsteadBugs=(halsteadVolume/3000)*1.0;
        
        writer.println("Halstead Difficulty:"+halsteadDifficulty);
        writer.println("Halstead Volume:"+halsteadVolume);
        writer.println("Haslstead Effort:"+halsteadEffort);
        writer.println("Halstead Bugs:"+halsteadBugs);
        }
        writer.write(System.getProperty("line.separator"));
    }
    writer.close();
}

public static void methodVisit(String res, String method, String resultType){

    if(finallist.containsKey(method)){
        Map<String,ArrayList<String>> results = finallist.get(method);
        if(results.containsKey(resultType)){
            results.get(resultType).add(res);
        }else{
            ArrayList<String> s = new ArrayList<String>();
            s.add(res);
            results.put(resultType,s);
        }
    }else {
        Map<String,ArrayList<String>> resMap = new HashMap<String,ArrayList<String>>();
        ArrayList<String> s = new ArrayList<String>();
        s.add(res);
        resMap.put(resultType,s);
        finallist.put(method,resMap);
    }
}

}
