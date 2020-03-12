package de.metas.monitoring.api;

/*
 * #%L
 * de.metas.monitoring
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

import java.math.BigDecimal;

import de.metas.monitoring.adapter.PerformanceMonitoringService;

/**
 * @deprecated not used anymore; please use and improve {@link PerformanceMonitoringService}.
 */
@Deprecated
public interface IMeter
{
	/**
	 * Increases the gauge by one.
	 */
	void plusOne();

	/**
	 * Decreases the gauge by one.
	 */
	void minusOne();

	/**
	 *
	 * @return the current gauge (increased by {@link #plusOne()}, decreased by {@link #minusOne()}).
	 */
	long getGauge();

	/**
	 *
	 * @return the number of times, {@link #plusOne()} and {@link #minusOne()} have been called.
	 */
	long getInvokeCount();

	/**
	 *
	 * @return rate (per second) of {@link #plusOne()} and {@link #minusOne()} invocations.
	 */
	BigDecimal getInvokeRate();

}
