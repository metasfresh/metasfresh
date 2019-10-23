package org.adempiere.ad.modelvalidator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

/**
 * Checks all {@link Interceptor} or {@link Validator} annotated classes if they are correctly defined.
 *
 * @author tsa
 *
 */
public class ClasspathAnnotatedModelInterceptorTester
{
	private final Set<Class<?>> testedClasses = new HashSet<>();

	/** Classnames to skip while checking (aka. known issues) */
	private final Set<String> classnamesToSkip = new HashSet<>();

	private final Map<Class<?>, Supplier<?>> instanceProviders = new HashMap<>();

	private int exceptionsCount = 0;

	public ClasspathAnnotatedModelInterceptorTester()
	{
	}

	public void test()
	{
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
		System.out.println("=> " + classes.size() + " classes to test");

		if(classes.isEmpty())
		{
			throw new AdempiereException("No classes found. Might be because for some reason Reflections does not work correctly with maven surefire plugin."
					+ "\n See https://github.com/metasfresh/metasfresh/issues/4773.");
		}

		for (final Class<?> clazz : classes)
		{
			testClass(clazz);
		}

		assertNoExceptions();
	}

	public final ClasspathAnnotatedModelInterceptorTester skipIfClassnameStartsWith(final String classnamePrefix)
	{
		classnamesToSkip.add(classnamePrefix);
		return this;
	}

	public final <T> ClasspathAnnotatedModelInterceptorTester useInstanceProvider(final Class<T> clazz, final Supplier<T> instanceProvider)
	{
		Check.assumeNotNull(clazz, "clazz not null");
		Check.assumeNotNull(instanceProvider, "instanceProvider not null");
		instanceProviders.put(clazz, instanceProvider);
		return this;
	}

	private final void testClass(final Class<?> clazz)
	{
		if (skipClass(clazz))
		{
			System.out.println("Skipped: " + clazz);
			return;
		}

		// Skip if already tested
		if (!testedClasses.add(clazz))
		{
			return;
		}

		try
		{
			new AnnotatedModelInterceptorDescriptorBuilder(clazz)
					.build();
		}
		catch (Exception ex)
		{
			logException(ex);
		}
	}

	private final boolean skipClass(final Class<?> clazz)
	{
		final String classname = clazz.getName();

		for (final String classnameToSkip : classnamesToSkip)
		{
			if (classname.startsWith(classnameToSkip))
			{
				return true;
			}
		}

		return false;
	}

	private final void logException(final Exception e)
	{
		e.printStackTrace();

		this.exceptionsCount++;
	}

	public void assertNoExceptions()
	{
		if (exceptionsCount > 0)
		{
			Assert.fail(exceptionsCount + " exceptions found while checking all classes. Check console");
		}
	}
}
