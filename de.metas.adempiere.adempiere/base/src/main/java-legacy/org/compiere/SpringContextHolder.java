package org.compiere;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.reflect.ClassReference;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class SpringContextHolder
{
	public static final transient SpringContextHolder instance = new SpringContextHolder();

	private static final transient Logger logger = LogManager.getLogger(SpringContextHolder.class);

	private ApplicationContext applicationContext;

	private final ConcurrentMap<ClassReference<?>, Object> junitRegisteredBeans = new ConcurrentHashMap<>();

	private SpringContextHolder()
	{
	}

	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	public boolean isApplicationContextSet()
	{
		return applicationContext != null;
	}

	/**
	 * Inject the application context from outside <b>and</b> enable {@link Services} to retrieve service implementations from it.
	 *
	 * Currently seems to be required because currently the client startup procedure needs to be decomposed more.
	 * See <code>SwingUIApplication</code> to know what I mean.
	 */
	public void setApplicationContext(@NonNull final ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
		logger.info("Set application context: {}", applicationContext);

		// gh #427: NOTE: the "Services.setExternalServiceImplProvider" is not called here because it might introduce a deadlock.
		// we will call it when the spring context was loaded.
	}

	public void clearApplicationContext()
	{
		this.applicationContext = null;
		this.junitRegisteredBeans.clear();
		logger.info("Cleared application context");
	}

	/**
	 * Allows to "statically" autowire a bean that is somehow not wired by spring. Needs the applicationContext to be set.
	 */
	public void autowire(@NonNull final Object bean)
	{
		final ApplicationContext springApplicationContext = getApplicationContext();
		if (springApplicationContext == null)
		{
			return;
		}
		springApplicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
	}

	/**
	 * With this method we work around the swing-client doing things (=>logon) before a the spring client was initialized.
	 * this method shall go together with the swing client (or earlier)
	 *
	 * @deprecated please use {@link #getBean(Class)} instead.
	 */
	@Deprecated
	public <T> T getBeanOrNull(@NonNull final Class<T> requiredType)
	{
		if (Adempiere.isUnitTestMode())
		{
			@SuppressWarnings("unchecked")
			final T beanImpl = (T)junitRegisteredBeans.get(ClassReference.of(requiredType));
			if (beanImpl != null)
			{
				logger.info("JUnit testingL Returning manually registered bean: {}", beanImpl);
				return beanImpl;
			}
		}

		final ApplicationContext springApplicationContext = getApplicationContext();
		if (springApplicationContext == null)
		{
			return null;
		}
		return springApplicationContext.getBean(requiredType);
	}

	/**
	 * When running this method from within a junit test, we need to fire up spring
	 */
	public <T> T getBean(@NonNull final Class<T> requiredType)
	{
		if (Adempiere.isUnitTestMode())
		{
			@SuppressWarnings("unchecked")
			final T beanImpl = (T)junitRegisteredBeans.get(ClassReference.of(requiredType));
			if (beanImpl != null)
			{
				logger.info("JUnit testingL Returning manually registered bean: {}", beanImpl);
				return beanImpl;
			}
		}

		final ApplicationContext springApplicationContext = getApplicationContext();
		try
		{
			throwExceptionIfNull(springApplicationContext);
		}
		catch (final AdempiereException e)
		{
			throw e.appendParametersToMessage()
					.setParameter("requiredType", requiredType);
		}
		return springApplicationContext.getBean(requiredType);
	}

	/**
	 * When running this method from within a junit test, we need to fire up spring
	 */
	public <T> Collection<T> getBeansOfType(@NonNull final Class<T> requiredType)
	{
		final ApplicationContext springApplicationContext = getApplicationContext();
		try
		{
			throwExceptionIfNull(springApplicationContext);
		}
		catch (final AdempiereException e)
		{
			throw e.appendParametersToMessage()
					.setParameter("requiredType", requiredType);
		}
		return springApplicationContext.getBeansOfType(requiredType).values();
	}

	private static ApplicationContext throwExceptionIfNull(@Nullable final ApplicationContext springApplicationContext)
	{
		if (springApplicationContext != null)
		{
			return springApplicationContext;
		}
		final String message;
		if (Adempiere.isUnitTestMode())
		{
			message = "This unit test requires a spring ApplicationContext; A known way to do that is to annotate the test class like this:\n"
					+ "\n"
					+ "@RunWith(SpringRunner.class)\n"
					+ "@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, <further classes> })\n"
					+ "public class YourTest ...\n"
					+ "\n"
					+ "Where the further configuration classes contain @ComponentScan annotations to discover spring components required by the actual tests"
					+ "Also see https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html";
		}
		else
		{
			message = "SpringApplicationContext not configured yet";
		}
		throw new AdempiereException(message);
	}

	public boolean isSpringProfileActive(@NonNull final String profileName)
	{
		final ApplicationContext springApplicationContext = throwExceptionIfNull(getApplicationContext());

		final String[] activeProfiles = springApplicationContext.getEnvironment().getActiveProfiles();
		final boolean profileIsActive = Arrays
				.stream(activeProfiles)
				.anyMatch(env -> env.equalsIgnoreCase(profileName));
		return profileIsActive;
	}

	public static <T> void registerJUnitBean(@NonNull final T beanImpl)
	{
		@SuppressWarnings("unchecked")
		final Class<T> beanType = (Class<T>)beanImpl.getClass();

		registerJUnitBean(beanType, beanImpl);
	}

	public static <BT, T extends BT> void registerJUnitBean(@NonNull final Class<BT> beanType, @NonNull final T beanImpl)
	{
		if (!Adempiere.isUnitTestMode())
		{
			throw new AdempiereException("JUnit mode is not active!");
		}

		instance.junitRegisteredBeans.put(ClassReference.of(beanType), beanImpl);
		logger.info("JUnit testing: Registered bean {}={}", beanType, beanImpl);
	}
}
