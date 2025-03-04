package org.adempiere.ad.modelvalidator;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.util.DisplayType;

import java.util.Map.Entry;
import java.util.Set;

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
 * This class allows us to disable annotated model interceptors via {@link org.compiere.model.I_AD_SysConfig} records.
 *
 * @author metas-dev <dev@metasfresh.com>
 * task https://github.com/metasfresh/metasfresh/issues/2482
 */
/* package */ class AnnotatedModelInterceptorDisabler
{
	public static AnnotatedModelInterceptorDisabler get()
	{
		return INSTANCE;
	}

	private final static AnnotatedModelInterceptorDisabler INSTANCE = new AnnotatedModelInterceptorDisabler();

	final static String SYS_CONFIG_NAME_PREFIX = "InterceptorEnabled_";

	private final ExtendedMemorizingSupplier<Set<String>> disabledPointcutIdsSupplier = ExtendedMemorizingSupplier.of(AnnotatedModelInterceptorDisabler::retrieveDisabledPointcutIds);

	@VisibleForTesting
	AnnotatedModelInterceptorDisabler()
	{
		// Make sure our cache it's invalidated when AD_SysConfig records are changed
		CacheMgt.get().addCacheResetListener(I_AD_SysConfig.Table_Name, request -> invalidateCache());
	}

	/**
	 * Invalidates disabled pointcuts cache.
	 *
	 * @return 1
	 */
	@VisibleForTesting
	int invalidateCache()
	{
		disabledPointcutIdsSupplier.forget();
		return 1;
	}

	/**
	 * Returns a message that tells an admin how to disable the given {@code pointcut}.
	 */
	static String createHowtoDisableMessage(@NonNull final Pointcut pointcut)
	{
		final String pointcutId = pointcut.getPointcutId();
		return String.format("Model interceptor method %s threw an exception.\nYou can disable this method with SysConfig %s='N' (with AD_Client_ID and AD_Org_ID=0!)",
				pointcutId, createDisabledSysConfigKey(pointcutId));
	}

	private static String createDisabledSysConfigKey(@NonNull final String pointcutId)
	{
		return SYS_CONFIG_NAME_PREFIX + pointcutId;
	}

	private static ImmutableSet<String> retrieveDisabledPointcutIds()
	{
		final boolean removePrefix = true;
		return Services.get(ISysConfigBL.class)
				.getValuesForPrefix(SYS_CONFIG_NAME_PREFIX, removePrefix, ClientAndOrgId.SYSTEM)
				.entrySet()
				.stream()
				.filter(entry -> !DisplayType.toBoolean(entry.getValue())) // =Y
				.map(Entry::getKey)
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean isDisabled(@NonNull final Pointcut pointcut)
	{
		final String pointcutId = pointcut.getPointcutId();
		return disabledPointcutIdsSupplier.get().contains(pointcutId);
	}
}
