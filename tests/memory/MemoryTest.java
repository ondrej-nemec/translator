package memory;

import java.util.HashMap;
import java.util.Map;

import translator.Translator;
import translator.TranslatorTest;

public class MemoryTest extends TranslatorTest{
	
	public MemoryTest(String key, String message,  boolean debug) {
		super(key, message, debug);
	}
	
	@Override
	protected Translator getTranslator() {
		Map<String, String> map = new HashMap<>();
		map.put("test.success", "Successful test");
		return new Memory(map);
	}
	
}
