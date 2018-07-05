package translator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public abstract class TranslatorTest {

	protected String key;
	protected String message;
	protected boolean debug;
	
	public TranslatorTest(String key, String message, boolean debug) {
		this.key = key;
		this.message = message;
		this.debug = debug;
	}
	
	@Test
	public void testSetDebugWorks(){
		Translator translator = getTranslator();
		assertFalse(translator.isDebug());
		
		translator.setDegug(true);
		assertTrue(translator.isDebug());
		
		translator.setDegug(false);
		assertFalse(translator.isDebug());
	}
	
	@Test
	public void testTranslateWorks(){
		Translator translator = getTranslator();
		translator.setDegug(debug);
		assertEquals(message, translator.translate(key));
	}

	
	protected abstract Translator getTranslator();
	
	@Parameters
	public static Collection<Object[]> dataSet(){
		return Arrays.asList(
					new Object[]{"test.success", "Successful test", false},
					new Object[]{"test.success", "Successful test", true},
					new Object[]{"test.failure", "test.failure", false},
					new Object[]{"test.failure", "??? test.failure !!!", true}
				);
	}
	
	
}
