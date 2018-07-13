package memory;

import java.util.HashMap;
import java.util.Map;

import translator.Translator;
import translator.TranslatorTest;

public class MemoryTest extends TranslatorTest{
	
	public MemoryTest(String key, String message,  boolean debug, String[] variables) {
		super(key, message, debug, variables);
	}
	
	@Override
	protected Translator getTranslator() {
		Map<String, String> map = new HashMap<>();
		map.put("test.success", "Successful test");
		map.put("test.choose%pieces%%liters%", "You choose: %pieces% pieces, %liters% liters");
		map.put("test.choose<pieces><liters>", "You choose: <pieces> pieces, <liters> liters");
		return new Memory(map);
	}
	
}
