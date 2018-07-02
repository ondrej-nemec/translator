package memory;

import java.util.Map;
import java.util.Set;

import translator.Translator;

public class Memory implements Translator{

	private final Map<String, String> map;
	
	private boolean debug;
	
	public Memory(Map<String, String> map) {
		this.map = map;
		this.debug = false;
	}
	
	@Override
	public String translate(String key) {
		try{
			return map.get(key);
		}catch (Exception e){
			if(debug){
				System.err.println("Missing key: " + key);
				return "??? " + key + " !!!";
			}else{
				return key;
			}
		}
	}

	@Override
	public void setDegug(boolean debug) {
		this.debug = debug;
		
	}
	
	public Set<String> getKeys(){
		return map.keySet();
	}

}
