package translator;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {

	private boolean debug;
	
	private char startSeparator = '%';
	
	private char endSeparator = '%';
	
	private String messageSeparator = ";";
	
	private String countSeparator = "~";
	
	private final ResourceBundle messages;
	
	
	public Translator(String path) {
		this.messages = ResourceBundle.getBundle(path);
	}
	
	public String translate(String key) {
		try{
			return messages.getString(key);
		}catch (MissingResourceException e){
			if(debug){
				System.err.println("Missing key: " + key);
				return "??? " + key + " !!!";
			}else{
				return key;
			}
		}
	}
	
	public String translate(String key, String... variables) {
		String message = translate(key);
		return translateVariables(message, variables);
	}
	
	private String translateVariables(String message, String... variables) {
		Pattern patern = Pattern.compile("(" + startSeparator + "[^ ]*" + endSeparator + ")");
		Matcher matcher = patern.matcher(message);
		int i = 0;
		while(matcher.find()){
			if(i >= variables.length)
				throw new RuntimeException("Too little variables in array: " + i);
			message = message.replace(matcher.group(), variables[i]);
			i++;
		}
		return message;
	}
	
	/*
	 * n
	 * -n
	 * n-m
	 * -n--m
	 * n<
	 * n<=
	 * n>
	 * n>=
	 */
	public String translate(String key, int count){
		String[] messages = translate(key).split(messageSeparator);
		for (String mess : messages) {
			String[] splited = mess.split(countSeparator);
			for (String stringCount : splited[0].split(",")){
				// n OR -n
				Pattern p = Pattern.compile("^-?[0-9]+$");
				Matcher m = p.matcher(stringCount);
				if(m.find() && Integer.parseInt(m.group()) == count)
					return translateVariables(splited[1], new Integer(count).toString());
				// n-m OR -n-m OR -n-m
				p = Pattern.compile("^(-?[0-9]+)(-)(-?[0-9]+)$");
				m = p.matcher(stringCount);
				if(m.find() && Integer.parseInt(m.group(1)) <= count && count <= Integer.parseInt(m.group(3))){
					return translateVariables(splited[1], new Integer(count).toString());
				}
				// n< OR n<= OR n> OR n>= 
				p = Pattern.compile("^([0-9]+)(<|<=|>|>=)$");
				m = p.matcher(stringCount);
				if(m.find()){
					switch(m.group(2)){
					case "<":
						if(new Integer(m.group(1)) < count)
							return translateVariables(splited[1], new Integer(count).toString());
					case "<=":
						if(new Integer(m.group(1)) <= count)
							return translateVariables(splited[1], new Integer(count).toString());
					case ">":
						if(new Integer(m.group(1)) > count)
							return translateVariables(splited[1], new Integer(count).toString());
					case ">=":
						if(new Integer(m.group(1)) >= count)
							return translateVariables(splited[1], new Integer(count).toString());
					}
				}
			}			
		}
		return key + " : " + count;
	}
	
	public void setVariableSeparators(char start, char end) {
		this.startSeparator = start;
		this.endSeparator = end;
	}
	
	public char getVariableStartSeparator() {
		return startSeparator;
	}
	
	public char getVariableEndSeparator() {
		return endSeparator;
	}
	
	public void setDegug(boolean debug) {
		this.debug = debug;
	}
	
	public boolean isDebug() {
		return debug;
	}
	
	public void setMessageSeparator(String separator){
		this.messageSeparator = separator;
	}
	
	public String getMessageSeparator(){
		return messageSeparator;
	}
	
	public void setCountSeparator(String separator){
		this.countSeparator = separator;
	}
	
	public String getCountSeparator(){
		return countSeparator;
	}

}
