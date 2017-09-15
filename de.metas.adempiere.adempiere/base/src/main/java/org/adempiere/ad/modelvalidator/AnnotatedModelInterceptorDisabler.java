package org.adempiere.ad.modelvalidator;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.util.CacheMgt;
import org.compiere.util.ICacheResetListener;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * This class allows us to disable annotated model validators via {@link org.compiere.model.I_AD_SysConfig}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/2482
 */
public class AnnotatedModelInterceptorDisabler
{
	public final static String SYS_CONFIG_NAME_PREFIX = "IntercetorEnabled_";

	private final static AnnotatedModelInterceptorDisabler INSTANCE = new AnnotatedModelInterceptorDisabler();

	public static AnnotatedModelInterceptorDisabler get()
	{
		return INSTANCE;
	}

	private final LoadingCache<IPointcut, String> howtoDisableMsgCache = CacheBuilder.newBuilder().build(new CacheLoader<IPointcut, String>()
	{
		@Override
		public String load(final IPointcut pointcut) throws Exception
		{
			return createHowtoDisableMsg0(pointcut);
		}
	});

	private final HashSet<String> disabledPointcutNames = new HashSet<>();

	private final ReadWriteLock lockForDisabledPointcutNames = new ReentrantReadWriteLock();

	@VisibleForTesting
	AnnotatedModelInterceptorDisabler()
	{
		// make sure we reload if AD_Sysconfig was changed
		CacheMgt.get().addCacheResetListener(I_AD_SysConfig.Table_Name, new ICacheResetListener()
		{
			@Override
			public int reset(@NonNull final String tableName, @NonNull final Object key)
			{
				return reloadDisabledPointcutNames();
			}
		});
	}

	/**
	 * Returns a message that tells an admin how to disable the given {@code pointcut}.
	 * 
	 * @param pointcut
	 * @return
	 */
	public String createHowtoDisableMsg(@NonNull final IPointcut pointcut)
	{
		return howtoDisableMsgCache.getUnchecked(pointcut);
	}

	@VisibleForTesting
	String createHowtoDisableMsg0(@NonNull final IPointcut pointcut)
	{
		final String methodString = createMethodString(pointcut);

		return String.format("Model interceptor method %s threw an exception.\nYou can disable this method with SysConfig %s='N' (with AD_Client_ID and AD_Org_ID=0!)",
				methodString, createDisabledSysConfigKey(methodString));
	}

	private String createMethodString(@NonNull final IPointcut pointcut)
	{
		final Method method = pointcut.getMethod();

		final String methodString = String.format("%s#%s",
				method.getDeclaringClass().getName(),
				method.getName());

		return methodString;
	}

	private String createDisabledSysConfigKey(@NonNull final String methodString)
	{
		return SYS_CONFIG_NAME_PREFIX + methodString;
	}

	public int reloadDisabledPointcutNames()
	{
		final int result = disabledPointcutNames.size();
		
		final Set<String> preparedSet = retrieveNewSetOfDisabledNames();
		
		lockForDisabledPointcutNames.writeLock().lock();
		try
		{
			disabledPointcutNames.clear();
			disabledPointcutNames.addAll(preparedSet);
		}
		finally
		{
			lockForDisabledPointcutNames.writeLock().unlock();
		}
		return result;
	}

	/**
	 * do the the relatively slow DB query outside the synchronized area.
	 * @return
	 */
	private Set<String> retrieveNewSetOfDisabledNames()
	{
		final boolean removePrefix = true;
		final Set<String> preparedSet = Services.get(ISysConfigBL.class)
				.getValuesForPrefix(SYS_CONFIG_NAME_PREFIX, removePrefix, 0, 0)
				.entrySet()
				.stream()
				.filter(entry -> "N".equalsIgnoreCase(entry.getValue()))
				.map(Entry::getKey)
				.collect(Collectors.toSet());
		return preparedSet;
	}

	public boolean isDisabled(@NonNull final IPointcut pointcut)
	{
		lockForDisabledPointcutNames.readLock().lock();
		try
		{
			return disabledPointcutNames.contains(createMethodString(pointcut));
		}
		finally
		{
			lockForDisabledPointcutNames.readLock().unlock();
		}
	}
}
