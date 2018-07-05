package translator;


public interface Translator {

	public String translate(String key);
	
	public void setDegug(boolean debug);
	
	public boolean isDebug();
	
}
