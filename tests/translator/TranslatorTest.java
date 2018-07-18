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

	private String path = "translator/messages";
	
	private String key;
	private String message;
	private boolean debug;
	private int count;
	
	public TranslatorTest(String key, String message, boolean debug, int count) {
		this.key = key;
		this.message = message;
		this.debug = debug;
		this.count = count;
	}

	@Test(expected = RuntimeException.class)
	public void testTranslateWithVariablesThrowsWhenVariableDontExist(){
		Translator translator = new Translator(path);
		translator.translate("too.many.variables", "var");
	}
			
	@Test
	public void testTranslateWorks(){
		if(key.equals("test.count"))
			return;
		Translator translator = new Translator(path);
		translator.setDegug(debug);
		assertEquals(message, translator.translate(key));
	}

	@Test
	public void testTranslateWithVariablesWorks(){
		Translator translator = new Translator(path);
		assertEquals(
				"You choose: 4 pieces, 7 liters",
				translator.translate(
						"test.choose.pieces.liters.%%",
						new Integer(4).toString(),
						new Integer(7).toString())
			);
		translator.setVariableSeparators('<', '>');
		assertEquals(
				"You choose: 8 pieces, 45 liters",
				translator.translate(
						"test.choose.pieces.liters.<>",
						new Integer(8).toString(),
						new Integer(45).toString())
			);
	}
	
	@Test
	public void testTranslateWithCountWorks(){
		if(!key.equals("test.count"))
			return;
		Translator translator = new Translator(path);
		assertEquals(
				message,
				translator.translate(key, count)
			);
	}
		
	@Parameters
	public static Collection<Object[]> dataSet(){
		return Arrays.asList(
					new Object[]{"test.success", "Successful test", false, 0},
					new Object[]{"test.success", "Successful test", true, 0},
					new Object[]{"test.failure", "test.failure", false, 0},
					new Object[]{"test.failure", "??? test.failure !!!", true, 0},
					
					
					new Object[]{"test.count", "No years", false, 0},
					new Object[]{"test.count", "1 year", false, 1},
					new Object[]{"test.count", "years: 3", false, 3},
					new Object[]{"test.count", "comma, year-5", false, 5},
					new Object[]{"test.count", "comma, year-6", false, 6},
					new Object[]{"test.count", "more: 9", false, 9},
					new Object[]{"test.count", "less year", false, -1}
				);
	}
	
	
}
