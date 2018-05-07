package de.metas.tourplanning.api;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.Date;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;

import de.metas.tourplanning.model.I_M_TourVersion;

public interface ITourBL extends ISingletonService
{
	/**
	 * Gets Preparation Time for given Day of the Week.
	 * 
	 * @param tourVersion
	 * @param dayOfWeek
	 * @return preparation time (hour/minute/sec/millis) or null if there is no preparation time for that day of the week
	 */
	Timestamp getPreparationTime(I_M_TourVersion tourVersion, int dayOfWeek);

	/**
	 * Gets Preparation Date+Time for given Delivery Date.
	 * 
	 * @param tourVersion
	 * @param deliveryDate
	 * @return Preparation Date+Time or null if there is no preparation time set
	 */
	Timestamp getPreparationDateTime(I_M_TourVersion tourVersion, Date deliveryDate);

	IDeliveryDayGenerator createDeliveryDayGenerator(IContextAware context);
}
