package de.metas.monitoring.adapter;

import java.util.concurrent.Callable;
<<<<<<< HEAD
=======
import java.util.concurrent.TimeUnit;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.monitoring
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class NoopPerformanceMonitoringService implements PerformanceMonitoringService
{
	public static final NoopPerformanceMonitoringService INSTANCE = new NoopPerformanceMonitoringService();

<<<<<<< HEAD
	@Override
	public <V> V monitorSpan(Callable<V> callable, SpanMetadata request)
=======
	private NoopPerformanceMonitoringService() {}

	@Override
	public <V> V monitor(Callable<V> callable, Metadata request)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		try
		{
			return callable.call();
		}
		catch (Exception e)
		{
			throw PerformanceMonitoringServiceUtil.asRTE(e);
		}
	}

	@Override
<<<<<<< HEAD
	public <V> V monitorTransaction(Callable<V> callable, TransactionMetadata request)
	{
		try
		{
			return callable.call();
		}
		catch (Exception e)
		{
			throw PerformanceMonitoringServiceUtil.asRTE(e);
		}
	}

=======
	public void recordElapsedTime(final long duration, final TimeUnit unit, final Metadata metadata)
	{
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
