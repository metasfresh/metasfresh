package de.metas.script;

import org.compiere.model.X_AD_Rule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Runs Groovy scripts through the same JSR-223 path that AD_Rule scripts use
 * ({@link ScriptEngineFactory#createExecutor(String, String)} + {@link ScriptExecutor#execute(String)}).
 * <p>
 * A script that resolves a JDK or metasfresh class makes Groovy read that class file with its bundled ASM.
 * If that ASM is older than the runtime JDK, compilation fails with {@code Unsupported class file major version ...}.
 */
class ScriptExecutorGroovyTest
{
	private ScriptExecutor newGroovyExecutor()
	{
		return ScriptEngineFactory.get().createExecutor(X_AD_Rule.RULETYPE_JSR223ScriptingAPIs, "groovy");
	}

	@Test
	void scriptWithoutClassResolution()
	{
		final ScriptExecutor executor = newGroovyExecutor().putArgument("X", 41);

		assertThat(executor.execute("A_X + 1")).isEqualTo(42);
	}

	@Test
	void scriptResolvingJdkClasses()
	{
		final Object result = newGroovyExecutor().execute(
				"import java.math.BigDecimal\n"
						+ "def sum = [1, 2, 3].collect { new BigDecimal(it) }.sum()\n"
						+ "sum.toString() + ' ' + java.time.LocalDate.of(2026, 11, 8)");

		assertThat(result).isEqualTo("6 2026-11-08");
	}

	@Test
	void scriptResolvingMetasfreshClass()
	{
		final Object result = newGroovyExecutor().execute(
				"de.metas.script.ScriptEngineFactory.extractEngineNameFromRuleValue('groovy:MyRule')");

		assertThat(result).isEqualTo("groovy");
	}
}
