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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.Date;

import de.metas.tourplanning.model.I_M_DeliveryDay;

/**
 * POJO used for grouping parameters when querying for {@link I_M_DeliveryDay} using {@link IDeliveryDayDAO}.
 * 
 * @author tsa
 *
 */
public interface IDeliveryDayQueryParams
{
	Date getDeliveryDate();

	int getC_BPartner_Location_ID();

	/**
	 * 
	 * @return
	 *         <ul>
	 *         <li>true if materials needs to be fetched from vendor (i.e. document has IsSOTrx=false)
	 *         <li>false if materials needs to be delivered to customer (i.e. document has IsSOTrx=true)
	 *         </ul>
	 */
	boolean isToBeFetched();

	Boolean getProcessed();

	/**
	 * The date+time when we calculate (e.g. the date+time when the order was created)
	 * 
	 * @return
	 */
	Timestamp getCalculationTime();

	/**
	 * If the returned value is not <code>null</code>, then the system will retrieve the chronologically <b>first</b> delivery date that is after the returned timestamp.<br>
	 * If the value is <code>null</code>, then it will return the chronologically <b>last</b> delivery date prior to {@link #getDeliveryDate()}.
	 * Also see {@link IDeliveryDayDAO#retrieveDeliveryDay(org.adempiere.model.IContextAware, IDeliveryDayQueryParams)}.
	 * 
	 * @return
	 */
	Timestamp getPreparationDay();

}
