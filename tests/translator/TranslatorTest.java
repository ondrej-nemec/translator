package translator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TranslatorTest {

	protected String key;
	protected String message;
	protected boolean debug;
	protected String[] variables;
	
	public TranslatorTest(String key, String message, boolean debug, String[] variables) {
		this.key = key;
		this.message = message;
		this.debug = debug;
		this.variables = variables;
	}
	
	@Test
	public void testSetDebugWorks(){
		Translator translator = new Translator("translator/messages");
		assertFalse(translator.isDebug());
		
		translator.setDegug(true);
		assertTrue(translator.isDebug());
		
		translator.setDegug(false);
		assertFalse(translator.isDebug());
	}
		
	@Test
	public void testTranslateWorks(){
		Translator translator = new Translator("translator/messages");
		translator.setDegug(debug);
		assertEquals(message, translator.translate(key));
	}

	@Test(expected = RuntimeException.class)
	public void testTranslateWithVariablesThrowsWhenVariableDontExist(){
		Translator translator = new Translator("translator/messages");
		translator.translate("too %many% %variables%", "var");
	}
	
	@Test
	public void testTranslateWithVariablesWorks(){
		Translator translator = new Translator("translator/messages");
		assertEquals(
				"You choose: 4 pieces, 7 liters",
				translator.translate(
						"test.choose%pieces%%liters%",
						new Integer(4).toString(),
						new Integer(7).toString())
			);
		translator.setVariableSeparators('<', '>');
		assertEquals(
				"You choose: 8 pieces, 45 liters",
				translator.translate(
						"test.choose<pieces><liters>",
						new Integer(8).toString(),
						new Integer(45).toString())
			);
	}
		
	@Parameters
	public static Collection<Object[]> dataSet(){
		return Arrays.asList(
					new Object[]{"test.success", "Successful test", false, null},
					new Object[]{"test.success", "Successful test", true, null},
					new Object[]{"test.failure", "test.failure", false, null},
					new Object[]{"test.failure", "??? test.failure !!!", true, null}
				);
	}
	
	
}
