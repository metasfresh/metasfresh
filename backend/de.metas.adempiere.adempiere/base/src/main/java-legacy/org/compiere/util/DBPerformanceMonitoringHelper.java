/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.compiere.util;

import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

import java.util.concurrent.Callable;

public class DBPerformanceMonitoringHelper
{

	private ISysConfigBL _sysConfigBL;
	private static PerformanceMonitoringService _performanceMonitoringService;
	private static final ThreadLocal<Boolean> isGettingSysconfig = ThreadLocal.withInitial(() -> false);
	private static boolean lastSysconfigResult;
	private static final String PERF_MON_SYSCONFIG_NAME = "de.metas.monitoring.db.enable";
	private static final boolean SYS_CONFIG_DEFAULT_VALUE = false;
	private static final String PM_METADATA_CLASS_NAME = "DB";
	private static final String PM_METADATA_PREPARE_STATEMENT_ACTION = "prepareStatement";
	private static final String PM_METADATA_EXECUTE_UPDATE_ACTION = "executeUpdate";

	private static final PerformanceMonitoringService.Metadata PM_METADATA_PREPARE_STATEMENT =
			PerformanceMonitoringService.Metadata
					.builder()
					.className(PM_METADATA_CLASS_NAME)
					.type(PerformanceMonitoringService.Type.DB)
					.functionName(PM_METADATA_PREPARE_STATEMENT_ACTION)
					.build();

	private static final PerformanceMonitoringService.Metadata PM_METADATA_EXECUTE_UPDATE =
			PerformanceMonitoringService.Metadata
					.builder()
					.className(PM_METADATA_CLASS_NAME)
					.type(PerformanceMonitoringService.Type.DB)
					.functionName(PM_METADATA_EXECUTE_UPDATE_ACTION)
					.build();

	public CPreparedStatement performanceMonitoringServicePrepareStatement(@NonNull final Callable<CPreparedStatement> callable)
	{
		final PerformanceMonitoringService performanceMonitoringService = performanceMonitoringService();

		return performanceMonitoringService.monitor(callable, PM_METADATA_PREPARE_STATEMENT);
	}

	public SQLUpdateResult performanceMonitoringServiceExecuteUpdate(@NonNull final Callable<SQLUpdateResult> callable)
	{
		final PerformanceMonitoringService performanceMonitoringService = performanceMonitoringService();

		return performanceMonitoringService.monitor(callable, PM_METADATA_EXECUTE_UPDATE);
	}

	private PerformanceMonitoringService performanceMonitoringService()
	{
		PerformanceMonitoringService performanceMonitoringService = _performanceMonitoringService;
		if (performanceMonitoringService == null || performanceMonitoringService instanceof NoopPerformanceMonitoringService)
		{
			performanceMonitoringService = _performanceMonitoringService = SpringContextHolder.instance.getBeanOr(
					PerformanceMonitoringService.class,
					NoopPerformanceMonitoringService.INSTANCE);
		}
		return performanceMonitoringService;
	}

	public boolean isPerfMonActive()
	{
		if(!isGettingSysconfig.get())
		{
			isGettingSysconfig.set(true);
			try
			{
				return lastSysconfigResult = sysConfigBL().getBooleanValue(PERF_MON_SYSCONFIG_NAME, SYS_CONFIG_DEFAULT_VALUE);
			}
			catch(final IllegalStateException ise)
			{
				//catch exception caused by read while modifying AD_SysConfig
				return lastSysconfigResult;
			}
			finally
			{
				isGettingSysconfig.set(false);
			}
		}

		return lastSysconfigResult;
	}

	private ISysConfigBL sysConfigBL()
	{
		ISysConfigBL sysConfigBL = this._sysConfigBL;
		if (sysConfigBL == null)
		{
			sysConfigBL = this._sysConfigBL = Services.get(ISysConfigBL.class);
		}
		return sysConfigBL;
	}
}
