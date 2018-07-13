package localization;

import localization.Localization;
import translator.Translator;
import translator.TranslatorTest;

public class LocalizationTest extends TranslatorTest{

	public LocalizationTest(String key, String message,  boolean debug, String[] variables) {
		super(key, message, debug, variables);
	}
	
	@Override
	protected Translator getTranslator() {
		return new Localization("localization/messages");
	}
	
}
