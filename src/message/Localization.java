package message;

import java.util.Enumeration;
import java.util.ResourceBundle;

import translator.Translator;

public class Localization implements Translator{

	private final ResourceBundle messages;
	
	private boolean debug;
	
	public Localization(String path) {
		this.messages = ResourceBundle.getBundle(path);
		this.debug = false;
	}
	
	@Override
	public String translate(String key) {
		try{
			return messages.getString(key);
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
	
	public Enumeration<String> getKeys(){
		return messages.getKeys();
	}
	
	@Override
	public boolean isDebug() {
		return debug;
	}

}
