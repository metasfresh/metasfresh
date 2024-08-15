package org.adempiere.test;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Watches current test and dumps the database to console in case of failure.
 *
 * To include in your tests:
 * <ul>
 * <li>JUnit5: annotate your class with <b><code>@ExtendWith(AdempiereTestWatcher.class)</code></b>
 * <li>Legacy JUnit4: you need to declare a public field like this: <code>@Rule public final TestWatcher testWatcher = new AdempiereTestWatcher();</code>
 * </ul>
 */
public class AdempiereTestWatcher extends TestWatcher implements AfterTestExecutionCallback
{
	private static final Logger logger = LogManager.getLogger(AdempiereTestWatcher.class);

	/** Context variables to be printed to screen in case the test fails */
	private final Map<String, Object> context = new LinkedHashMap<>();

	@Override
	public final void afterTestExecution(final ExtensionContext context)
	{
		if (context.getExecutionException().isPresent())
		{
			onTestFailed(context.getDisplayName(), context.getExecutionException().get());
		}

		onTestFinished(context.getDisplayName());
	}

	/**
	 * Called after a test succeed.
	 *
	 * Does nothing at this level.
	 */
	@Override
	protected final void succeeded(final Description description)
	{
		// nothing
	}

	@Override
	protected final void failed(final Throwable exception, final Description description)
	{
		onTestFailed(description.getDisplayName(), exception);
	}

	/**
	 * Called after a test failed. It:
	 * <ul>
	 * <li>dump database content
	 * </ul>
	 */
	protected void onTestFailed(final String testName, final Throwable exception)
	{
		POJOLookupMap.get().dumpStatus("After test failed: " + testName);

		//
		// Dump retained context values
		for (final Entry<String, Object> entry : context.entrySet())
		{
			final String name = entry.getKey();
			final Object value = entry.getValue();
			System.out.println("\n" + name + ": " + value);
		}
	}

	@Override
	protected final void finished(final Description description)
	{
		onTestFinished(description.getDisplayName());
	}

	/**
	 * Called after a test finished (successful or not). It:
	 * <ul>
	 * <li>clears database content
	 * </ul>
	 */
	private void onTestFinished(final String name)
	{
		final POJOLookupMap pojoLookupMap = POJOLookupMap.get();
		if (pojoLookupMap != null)
		{
			pojoLookupMap.clear();
		}

		context.clear();
	}

	/**
	 * Put given variable to context.
	 *
	 * In case the test will fail, all context variables will be printed to console.
	 */
	public void putContext(final String name, final Object value)
	{
		Check.assumeNotEmpty(name, "name is not empty");
		context.put(name, value);
	}

	/**
	 * Convenient variant for {@link #putContext(String, Object)} which considers value's class name as the context variable <code>name</code>.
	 *
	 * @param value value, not null.
	 */
	public void putContext(final Object value)
	{
		if (value == null)
		{
			logger.error("Cannot put a null value to context. This is a development error. Skipped for now", new Exception("TRACE"));
			return;
		}

		final String name = value.getClass().getName();
		putContext(name, value);
	}
}
