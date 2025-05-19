package de.metas.util;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.util.Set;

import javax.annotation.Nullable;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.adempiere.util.proxy.Cached;
import org.adempiere.util.proxy.IServiceInterceptor;
import org.adempiere.util.proxy.impl.JavaAssistInterceptor;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;

import de.metas.util.exceptions.ServicesException;
import lombok.NonNull;

/**
 * The metasfresh service registry.<br>
 * Under the hood, this registry is a map that associates interfaces which extend either {@link ISingletonService} or {@link IMultitonService} with their respective implementations.<br>
 * It is possible to explicitly register an implementation for a service by using the method {@link #registerService(Class, ISingletonService)}.<br>
 * However, usually, a service's implementation class is located and instantiated with the help of a {@link IServiceNameAutoDetectPolicy} that is set via {@link #setServiceNameAutoDetectPolicy(IServiceNameAutoDetectPolicy)}.
 * <p>
 * An instance of the respective service interface implementation is obtained using the {@link #get(Class)} method.<br>
 * Note that instead of returning the actual implementation that was registered or located, the {@link #get(Class)} returns a proxy which allows us to implement <b>caching</b> by using the {@link Cached} annotation.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class Services
{
	private final static Logger logger = LoggerFactory.getLogger(Services.class.getName());

	/** Services interceptor */
	private static IServiceInterceptor interceptor = new JavaAssistInterceptor();

	/**
	 * Can be used to provide service implementations from the outside. Concrete goal: allow us to provide spring managed service implementations via {@link Services#get(Class)}.
	 *
	 * @see Services#setExternalServiceImplProvider(IServiceImplProvider)
	 * @task https://github.com/metasfresh/metasfresh/issues/427
	 */
	@FunctionalInterface
	public interface IServiceImplProvider
	{
		/**
		 *
		 * @param serviceClazz
		 * @return a service implementation class, or {@code null} if none can't be provided.
		 */
		@Nullable
		<T extends IService> T provideServiceImpl(Class<T> serviceClazz);
	}

	private static IServiceImplProvider externalServiceImplProvider;

	/**
	 * This method can optionally be called so that invocations of the {@link #get(Class)} method are able to receive service implementations that are not coming right out of this class.
	 *
	 * If a service implementation was not yet cached within {@link Services} and is required, then the external service implementation provider (if one was set via this method) will be invoked first and if it returns a non null result, that result will be cached.
	 *
	 * @param externalServiceImplProvider
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/427
	 */
	public static void setExternalServiceImplProvider(final IServiceImplProvider externalServiceImplProvider)
	{
		Services.externalServiceImplProvider = externalServiceImplProvider;
		logger.info("Registered external service implementation provider: {}", externalServiceImplProvider);
	}

	/**
	 * Map from "service interface class" to "service implementation constructor"
	 * <p>
	 * NOTE:
	 * <ul>
	 * <li>the service implementation classes are already intercepted, if it was needed. See {@link #interceptor}.
	 * </ul>
	 */
	private static final LoadingCache<Class<? extends IService>, Constructor<? extends IService>> serviceInterface2implementionClassCtor = CacheBuilder.newBuilder()
			.build(new CacheLoader<Class<? extends IService>, Constructor<? extends IService>>()
			{
				@Override
				public Constructor<? extends IService> load(Class<? extends IService> serviceInterfaceClass) throws Exception
				{
					return findServiceImplementationConstructor(serviceInterfaceClass);
				}
			});

	private static final CacheLoader<Class<? extends IService>, Object> servicesCache_Loader = new CacheLoader<Class<? extends IService>, Object>()
	{
		@Override
		public Object load(Class<? extends IService> serviceClass) throws Exception
		{
			return findAndLoadService(serviceClass);
		}
	};
	private static final RemovalListener<Class<? extends IService>, Object> servicesCache_RemovalListener = removal -> {
		final Class<? extends IService> serviceInterfaceClass = removal.getKey();
		final Object serviceImpl = removal.getValue();
		unloadService(serviceInterfaceClass, serviceImpl);
	};

	private static final LoadingCache<Class<? extends IService>, Object> newServicesCache()
	{
		return CacheBuilder.newBuilder()
				.removalListener(servicesCache_RemovalListener)
				.build(servicesCache_Loader);
	}

	private static LoadingCache<Class<? extends IService>, Object> services = newServicesCache();

	public static IServiceInterceptor getInterceptor()
	{
		return interceptor;
	}

	private static IServiceNameAutoDetectPolicy serviceNameAutoDetectPolicy = new DefaultServiceNamePolicy();

	public static void setAutodetectServices(boolean enable)
	{
		if (!enable)
		{
			throw new ServicesException("Disabling services autodetect is no longer supported");
		}
	}

	public static final boolean isAutodetectServices()
	{
		return true;
	}

	/**
	 *
	 * @param serviceInterfaceClass
	 * @return
	 *         <ul>
	 *         <li>if <code>T</code> extends {@link ISingletonService} then this method returns a cached instance of that service implementation
	 *         <li>If <code>T</code> extends {@link IMultitonService}, then this method returns a NEW instance of that service implementation
	 *         </ul>
	 * @throws ServicesException if a service implementation was not found or could not be instantiated
	 */
	public static <T extends IService> T get(@NonNull final Class<T> serviceInterfaceClass)
	{
		Check.assume(serviceInterfaceClass.isInterface(), "Param 'clazz' shall be an interface: {}", serviceInterfaceClass);

		if (IMultitonService.class.isAssignableFrom(serviceInterfaceClass))
		{
			@SuppressWarnings("unchecked")
			final Class<? extends IMultitonService> multitonServiceClass = (Class<? extends IMultitonService>)serviceInterfaceClass;
			@SuppressWarnings("unchecked")
			final T multitonImpl = (T)newMultiton(multitonServiceClass);
			return multitonImpl;
		}
		else
		{
			final T service = getSingleton(serviceInterfaceClass);
			return service;
		}
	}

	/**
	 * Create a new instance of given multiton service.
	 *
	 * @param multitonServiceClass
	 * @return multiton service; never returns <code>null</code>
	 */
	public static <T extends IMultitonService> T newMultiton(final Class<T> multitonServiceClass)
	{
		try
		{
			final T multitonImpl = findAndLoadService(multitonServiceClass);
			return multitonImpl;
		}
		catch (Exception e)
		{
			throw ServicesException.wrapIfNeeded(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends IService> T getSingleton(final Class<T> serviceInterfaceClass)
	{
		try
		{
			final T serviceImpl = (T)services.get(serviceInterfaceClass);
			return serviceImpl;
		}
		catch (Exception e)
		{
			throw ServicesException.wrapIfNeeded(e);
		}
	}

	private static final <T extends IService> T findAndLoadService(@NonNull final Class<T> serviceInterfaceClass)
	{
		try
		{
			T serviceImpl = null;

			// gh #427 first check if an external service implementation provider was given to us, and if so, see what it has to offer.
			if (externalServiceImplProvider != null)
			{
				serviceImpl = externalServiceImplProvider.provideServiceImpl(serviceInterfaceClass);
			}

			if (serviceImpl == null)
			{
				// if we did not get an implementation via externalServiceImplProvider, try our legacy way of looking up and invoking the implementation's default constructor
				// Get the service implementation constructor
				@SuppressWarnings("unchecked")
				final Constructor<T> serviceImplConstructor = (Constructor<T>)serviceInterface2implementionClassCtor.get(serviceInterfaceClass);

				//
				// Create service implementation instance
				serviceImpl = serviceImplConstructor.newInstance();
				assertValidServiceImpl(serviceInterfaceClass, serviceImpl);
			}

			//
			// Load service
			loadService(serviceInterfaceClass, serviceImpl);

			if (logger.isDebugEnabled())
			{
				logger.debug("Loaded service for {}: {}", new Object[] { serviceInterfaceClass, serviceImpl.getClass() });
			}

			return serviceImpl;
		}
		catch (Exception e)
		{
			throw ServicesException.wrapIfNeeded(e);
		}
	}

	private static final <T extends IService> Constructor<T> findServiceImplementationConstructor(final Class<T> serviceInterfaceClass)
	{
		assertValidServiceInterfaceClass(serviceInterfaceClass);

		//
		// Find service implementation class
		final String serviceClassname = serviceNameAutoDetectPolicy.getServiceImplementationClassName(serviceInterfaceClass);
		Class<T> serviceImplClass = null;
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try
		{
			@SuppressWarnings("unchecked")
			final Class<T> clazz = (Class<T>)classLoader.loadClass(serviceClassname);
			serviceImplClass = clazz;
		}
		catch (ClassNotFoundException e)
		{
			throw new ServicesException("Cannot find service implementation class: " + serviceClassname + " (classLoader=" + classLoader + ")", e);
		}

		//
		// Create intercepted class, if applies
		if (interceptor != null)
		{
			// Make sure constructor is accessible (before intercepting)
			final Constructor<T> serviceImplConstructor = getDefaultConstructor(serviceImplClass);
			serviceImplConstructor.setAccessible(true);

			serviceImplClass = interceptor.createInterceptedClass(serviceImplClass);
		}

		//
		// Make sure constructor is accessible
		final Constructor<T> serviceImplConstructor = getDefaultConstructor(serviceImplClass);
		serviceImplConstructor.setAccessible(true);

		return serviceImplConstructor;
	}

	/**
	 * Use this method to find out if a service is available.
	 *
	 * @param serviceInterfaceClass
	 * @return <code>true</code> if the service was previously registered or autodetected.
	 */
	public static <T extends ISingletonService> boolean isAvailable(final Class<T> serviceInterfaceClass)
	{
		@SuppressWarnings("unchecked")
		final T service = (T)services.getIfPresent(serviceInterfaceClass);
		return service != null;
	}

	/**
	 * Register a new service class and an implementing instance.
	 * If there is another implementation already registered, it will be silently replaced with the given implementation.
	 *
	 * WARNING: the service implementation WILL NOT be intercepted.
	 *
	 * @param serviceInterfaceClass the API class that will later on be used to get the implementation.
	 * @param serviceImpl an actual instance of a class extending 'clazz'.
	 * @return the implementation that was previously registered or <code>null</code>.
	 */
	public static <T extends ISingletonService> void registerService(final Class<T> serviceInterfaceClass, final T serviceImpl)
	{
		assertValidServiceInterfaceClass(serviceInterfaceClass);
		assertValidServiceImpl(serviceInterfaceClass, serviceImpl);

		services.put(serviceInterfaceClass, serviceImpl);
		loadService(serviceInterfaceClass, serviceImpl);
	}

	private static final <T extends IService> void assertValidServiceInterfaceClass(final Class<T> serviceInterfaceClass)
	{
		Check.assumeNotNull(serviceInterfaceClass, "serviceInterfaceClass not null");
		if (!serviceInterfaceClass.isInterface())
		{
			throw new IllegalArgumentException("Parameter 'clazz' must be an interface class. clazz is" + serviceInterfaceClass.getName());
		}

		if (!ISingletonService.class.isAssignableFrom(serviceInterfaceClass)
				&& !IMultitonService.class.isAssignableFrom(serviceInterfaceClass))
		{
			throw new IllegalArgumentException("Parameter 'clazz' must extend " + IMultitonService.class + " or " + ISingletonService.class);
		}

	}

	private static final <T extends IService> void assertValidServiceImpl(final Class<T> serviceInterfaceClass, final T serviceImpl)
	{
		Check.assumeNotNull(serviceInterfaceClass, "serviceInterfaceClass not null");
		Check.assumeNotNull(serviceImpl, "serviceImpl not null");

		final Class<?> serviceImplClass = serviceImpl.getClass();
		if (!serviceInterfaceClass.isAssignableFrom(serviceImplClass))
		{
			throw new IllegalArgumentException("Service " + serviceImplClass + " must implement interface " + serviceInterfaceClass);
		}
	}

	/**
	 * Clears the service registry. Intended use between unit tests.
	 */
	public static void clear()
	{
		// Util.assume(Adempiere.isUnitTestMode(), "Clearing the service registry is allowed when running in JUnit test mode");
		// services.clear();
		logger.info("Clearing service registry");

		//
		// Dismiss current services cache and re-create it
		services.invalidateAll();
		services = newServicesCache();

		//
		// Reset interceptors
		interceptor = new JavaAssistInterceptor();
		// And also reset cached/intercepted service implementations
		serviceInterface2implementionClassCtor.invalidateAll();
	}

	private static final void loadService(final Class<? extends IService> serviceInterfaceClass, final Object serviceImpl)
	{
		if (serviceImpl instanceof IMBeanAwareService mbeanAwareService)
		{
			if (IMultitonService.class.isAssignableFrom(serviceInterfaceClass))
			{
				// Log the exception only if this service is not also a singleton.
				// Else it could be a false positive that will clutter our console.
				// e.g. see de.metas.async.processor.IStatefulWorkpackageProcessorFactory
				if (!ISingletonService.class.isAssignableFrom(serviceInterfaceClass))
				{
					final ServicesException ex = new ServicesException("Not registering MBean for service " + serviceImpl + " (" + serviceInterfaceClass + ") because it's a " + IMultitonService.class);
					logger.error(ex.getLocalizedMessage(), ex);
				}
			}
			else
			{
				registerMBean(serviceInterfaceClass, mbeanAwareService);
			}
		}
	}

	private static final void unloadService(final Class<? extends IService> serviceInterfaceClass, final Object serviceImpl)
	{
		if (serviceImpl instanceof IMBeanAwareService mbeanAwareService)
		{
			unregisterMBean(serviceInterfaceClass, mbeanAwareService);
		}
	}

	public static void setServiceNameAutoDetectPolicy(final IServiceNameAutoDetectPolicy serviceNameAutoDetectPolicy)
	{
		Check.assumeNotNull(serviceNameAutoDetectPolicy, "Param 'serviceNameAutoDetectPolicy' not null");

		final IServiceNameAutoDetectPolicy serviceNameAutoDetectPolicyOld = Services.serviceNameAutoDetectPolicy;
		Services.serviceNameAutoDetectPolicy = serviceNameAutoDetectPolicy;

		// If we change service name autodetect policy we also need to reset cached service classes,
		// because they might have changed
		if (serviceNameAutoDetectPolicyOld != Services.serviceNameAutoDetectPolicy)
		{
			serviceInterface2implementionClassCtor.invalidateAll();
		}
	}

	private static String createJMXName(final Class<?> interfaceClass, final IMBeanAwareService service)
	{
		final String jmxName = interfaceClass.getName() + ":type=" + service.getClass().getName();
		return jmxName;
	}

	private static void registerMBean(final Class<?> interfaceClass, final IMBeanAwareService service)
	{
		Check.errorIf(service instanceof IMultitonService && !(service instanceof ISingletonService), "IMBeanAwareService=" + service + " cannot implement IMultitonService");

		final Object mbean = service.getMBean();
		if (mbean == null)
		{
			logger.warn("No MBean found for " + service + ". Skip registering MBean.");
			return;
		}

		final String jmxName = createJMXName(interfaceClass, service);
		final ObjectName jmxObjectName;
		try
		{
			jmxObjectName = new ObjectName(jmxName);
		}
		catch (Exception e)
		{
			logger.warn("Cannot create JMX Name: " + jmxName + ". Skip registering MBean.", e);
			return;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try
		{
			if (!mbs.isRegistered(jmxObjectName))
			{
				mbs.registerMBean(mbean, jmxObjectName);
			}
		}
		catch (InstanceAlreadyExistsException e)
		{
			logger.warn("Cannot register MBean Name: " + jmxName + ". (caught InstanceAlreadyExistsException)", e);
		}
		catch (MBeanRegistrationException e)
		{
			logger.warn("Cannot register MBean Name: " + jmxName + ". (caught MBeanRegistrationException)", e);
		}
		catch (NotCompliantMBeanException e)
		{
			logger.warn("Cannot register MBean Name: " + jmxName + ". (caught NotCompliantMBeanException)", e);
		}
		catch (NullPointerException e)
		{
			logger.warn("Cannot register MBean Name: " + jmxName + ". (caught NullPointerException)", e);
		}
	}

	private static void unregisterMBean(Class<?> interfaceClass, IMBeanAwareService service)
	{
		final String jmxName = createJMXName(interfaceClass, service);
		final ObjectName jmxObjectName;
		try
		{
			jmxObjectName = new ObjectName(jmxName);
		}
		catch (Exception e)
		{
			logger.warn("Cannot create JMX Name: " + jmxName + ". Skip unregistering MBean.", e);
			return;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try
		{
			mbs.unregisterMBean(jmxObjectName);
		}
		catch (MBeanRegistrationException e)
		{
			logger.warn("Cannot unregister MBean Name: " + jmxName + ".", e);
		}
		catch (InstanceNotFoundException e)
		{
			// nothing
		}
	}

	public static long getLoadedServicesCount()
	{
		return services.size();
	}

	private static <T extends IService> Constructor<T> getDefaultConstructor(final Class<T> serviceInstanceClass)
	{
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final Set<Constructor> defaultConstructors = ReflectionUtils.getConstructors(serviceInstanceClass, ReflectionUtils.withParametersCount(0));
		final boolean hasNoDefaultConstructor = defaultConstructors.isEmpty();
		Check.errorIf(hasNoDefaultConstructor, "Param 'serviceInstanceClass' = {} has no default constructor", serviceInstanceClass);

		@SuppressWarnings("unchecked")
		final Constructor<T> defaultConstructor = defaultConstructors.iterator().next();
		return defaultConstructor;
	}

}
