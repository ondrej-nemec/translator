package translator;


public interface Translator {

	public String translate(String key);
	
	public String translate(String key, String... variables);
	
	public void setVariableSeparators(char start, char end);
	
	public char getVariableStartSeparator();
	
	public char getVariableEndSeparator();
	
	public void setDegug(boolean debug);
	
	public boolean isDebug();
	
}
