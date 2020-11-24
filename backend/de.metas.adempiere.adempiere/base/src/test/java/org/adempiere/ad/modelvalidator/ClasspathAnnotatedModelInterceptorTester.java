package org.adempiere.ad.modelvalidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.junit.Assert;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;

import lombok.NonNull;

/**
 * Checks all {@link Interceptor} or {@link Validator} annotated classes if they are correctly defined.
 *
 * @author tsa
 *
 */
public class ClasspathAnnotatedModelInterceptorTester
{
	private boolean failOnFirstError;

	private final Set<Class<?>> testedClasses = new HashSet<>();
	private int exceptionsCount = 0;

	public ClasspathAnnotatedModelInterceptorTester()
	{
	}

	public void testAll() throws Exception
	{
		final List<Class<?>> classes = getAllClasses();
		for (final Class<?> clazz : classes)
		{
			testClass(clazz);
		}

		assertNoExceptions();
	}

	public List<Class<?>> getAllClasses()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forClassLoader())
				.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner()));

		final Set<Class<?>> classes_withValidator = reflections.getTypesAnnotatedWith(Validator.class);
		System.out.println("Found " + classes_withValidator.size() + " classes annotated with " + Validator.class);

		final Set<Class<?>> classes_withInterceptor = reflections.getTypesAnnotatedWith(Interceptor.class);
		System.out.println("Found " + classes_withInterceptor.size() + " classes annotated with " + Interceptor.class);

		final Set<Class<?>> classes = ImmutableSet.<Class<?>> builder()
				.addAll(classes_withValidator)
				.addAll(classes_withInterceptor)
				.build();

		stopwatch.stop();
		System.out.println("=> " + classes.size() + " classes to test. Took " + stopwatch + " to find them.");

		if (classes.isEmpty())
		{
			throw new AdempiereException("No classes found. Might be because for some reason Reflections does not work correctly with maven surefire plugin."
					+ "\n See https://github.com/metasfresh/metasfresh/issues/4773.");
		}

		return sortByName(classes);
	}

	private static List<Class<?>> sortByName(final Set<Class<?>> classes)
	{
		final ArrayList<Class<?>> result = new ArrayList<>(classes);
		Collections.sort(result, Comparator.comparing(Class::getName));
		return result;
	}

	public final void testClass(final Class<?> interceptorClass) throws Exception
	{
		// Skip if already tested
		if (!testedClasses.add(interceptorClass))
		{
			return;
		}

		try
		{
			new AnnotatedModelInterceptorDescriptorBuilder(interceptorClass)
					.build();
		}
		catch (final Exception ex)
		{
			handleException(ex);
		}
	}

	public ClasspathAnnotatedModelInterceptorTester failOnFirstError(final boolean failOnFirstError)
	{
		this.failOnFirstError = failOnFirstError;
		return this;
	}

	private final void handleException(@NonNull final Exception ex) throws Exception
	{
		exceptionsCount++;

		if (failOnFirstError)
		{
			throw ex;
		}
		else
		{
			ex.printStackTrace();
		}
	}

	public void assertNoExceptions()
	{
		if (exceptionsCount > 0)
		{
			Assert.fail(exceptionsCount + " exceptions found while checking all classes. Check console");
		}
	}
}
