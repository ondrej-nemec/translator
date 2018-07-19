package translator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Test;

public class TranslatorTest {
	
	private Translator translator;
	
	public TranslatorTest() {
		translator = new Translator("translator/messages");
	}
	
	@Test
	public void testTranslateNotExistingKey(){
		assertEquals("not-existing-key", translator.translate("not-existing-key"));
	}
	
	@Test
	public void testTranslateCountNotExistingCount(){
		assertEquals("translate.works : 1", translator.translate("translate.works", 1));
	}
	
	@Test
	public void testTranslateWorks(){
		assertEquals(
				"Translated message",
				translator.translate("translate.works")
			);
	}
	
	@Test
	public void testTranslateFromWorks(){
		translator.addResource("from", ResourceBundle.getBundle("translator/from"));
		assertEquals(
				"Translated message from",
				translator.translateFrom("from", "translate.from.works")
			);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTranslateWithVariableThrowWhenVariableDontExist(){
		translator.translate("too.many.variables", "var");
	}
	
	@Test
	public void testTranslateWithVariableWorks(){
		assertEquals(
				"Variable: var",
				translator.translate(
						"test.one.variable",
						"var"
					)
			);
		assertEquals(
				"Variables: 4, four",
				translator.translate(
						"test.two.variables.%%",
						new Integer(4).toString(),
						"four"
					)
			);
		translator.setVariableSeparators('<', '>');
		assertEquals(
				"Variables: varA, varB",
				translator.translate(
						"test.two.variables.<>",
						"varA",
						"varB"
					)
			);
	}
		
	@Test
	public void testTranslateWithCountWorks(){
		List<Object[]> input = Arrays.asList(
				new Object[]{"Less", -3},
				new Object[]{"Negative", -1},
				new Object[]{"Zero value", 0},
				new Object[]{"Exactly: 1", 1},
				new Object[]{"Between: 3", 3},
				new Object[]{"Between: 4", 4},
				new Object[]{"Separator: 5", 5},
				new Object[]{"Separator: 6", 6},
				new Object[]{"More: 9", 9}
		);
		for(int i = 0; i < input.size(); i++){
			assertEquals(
					input.get(i)[0],
					translator.translate(
							"test.count",
							Integer.parseInt(
									input.get(i)[1].toString()
											)
						)
				);
		}
	}
}
