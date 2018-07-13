package memory;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import translator.Translator;

public class Memory implements Translator{

	private final Map<String, String> map;
	
	private boolean debug;
	
	private char startSeparator = '%';
	
	private char endSeparator = '%';
	
	public Memory(Map<String, String> map) {
		this.map = map;
		this.debug = false;
	}
	
	@Override
	public String translate(String key) {
		String message = map.get(key);
		if(message == null){
			if(debug){
				System.err.println("Missing key: " + key);
				return "??? " + key + " !!!";
			}else{
				return key;
			}
		}
		return message;
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
	public char getVariableEndSeparator() {
		return endSeparator;
	}
	
	@Override
	public char getVariableStartSeparator() {
		return startSeparator;
	}


	@Override
	public void setDegug(boolean debug) {
		this.debug = debug;
		
	}
	
	public Set<String> getKeys(){
		return map.keySet();
	}

	@Override
	public boolean isDebug() {
		return debug;
	}

}
