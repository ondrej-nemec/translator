package message;

import message.Localization;
import translator.Translator;
import translator.TranslatorTest;

public class LocalizationTest extends TranslatorTest{

	public LocalizationTest(String key, String message,  boolean debug) {
		super(key, message, debug);
	}
	
	@Override
	protected Translator getTranslator() {
		return new Localization("message/messages");
	}
	
}
