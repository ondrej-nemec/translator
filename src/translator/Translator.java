package translator;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {

	private boolean debug;
	
	private char startSeparator = '%';
	
	private char endSeparator = '%';
	
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
}
