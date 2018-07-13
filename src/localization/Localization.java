package localization;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import translator.Translator;

public class Localization implements Translator{

	private final ResourceBundle messages;
	
	private boolean debug;
	
	private char startSeparator = '%';
	
	private char endSeparator = '%';
	
	public Localization(String path) {
		this.messages = ResourceBundle.getBundle(path);
		this.debug = false;
	}
	
	@Override
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

	@Override
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

	@Override
	public void setVariableSeparators(char start, char end) {
		this.startSeparator = start;
		this.endSeparator = end;
	}

	@Override
	public char getVariableStartSeparator() {
		return startSeparator;
	}

	@Override
	public char getVariableEndSeparator() {
		return endSeparator;
	}
	
	@Override
	public void setDegug(boolean debug) {
		this.debug = debug;
	}
	
	public Enumeration<String> getKeys(){
		return messages.getKeys();
	}
	
	@Override
	public boolean isDebug() {
		return debug;
	}

}
