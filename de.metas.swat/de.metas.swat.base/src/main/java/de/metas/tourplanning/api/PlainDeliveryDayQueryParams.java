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

import org.adempiere.util.Check;

/**
 * Plain implementation of {@link IDeliveryDayQueryParams}
 * 
 * @author tsa
 *
 */
public final class PlainDeliveryDayQueryParams implements IDeliveryDayQueryParams
{
	private Date deliveryDate = null;
	private int bpartnerLocationId = -1;
	private Boolean toBeFetched = null;
	private Boolean processed = null;
	/**
	 * The time when the calculation is performed. For instance, the date+time when the order was created or the system time.
	 */
	private Timestamp calculationTime = null;

	/**
	 * The day when the products should be delivered
	 */
	private Timestamp preparationDay = null;

	@Override
	public String toString()
	{
		return "PlainDeliveryDayQueryParams ["
				+ "deliveryDate=" + deliveryDate
				+ ", bpartnerLocationId=" + bpartnerLocationId
				+ ", toBeFetched=" + toBeFetched
				+ ", processed=" + processed
				+ "]";
	}

	@Override
	public Date getDeliveryDate()
	{
		return deliveryDate;
	}

	public void setDeliveryDate(final Date deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return bpartnerLocationId;
	}

	public void setC_BPartner_Location_ID(final int bpartnerLocationId)
	{
		this.bpartnerLocationId = bpartnerLocationId;
	}

	@Override
	public boolean isToBeFetched()
	{
		Check.assumeNotNull(toBeFetched, "toBeFetched set");
		return toBeFetched;
	}

	/**
	 * 
	 * @param toBeFetched
	 * @see #isToBeFetched()
	 */
	public void setToBeFetched(final boolean toBeFetched)
	{
		this.toBeFetched = toBeFetched;
	}

	@Override
	public Boolean getProcessed()
	{
		return processed;
	}

	public void setProcessed(final Boolean processed)
	{
		this.processed = processed;
	}

	@Override
	public Timestamp getCalculationTime()
	{
		return calculationTime;
	}

	/**
	 * See {@link IDeliveryDayQueryParams#getCalculationTime()}.
	 * 
	 * @param calculationTime
	 */
	public void setCalculationTime(final Timestamp calculationTime)
	{
		this.calculationTime = calculationTime;
	}

	public Timestamp getPreparationDay()
	{
		return preparationDay;
	}

	public void setPreparationDay(Timestamp preparationDay)
	{
		this.preparationDay = preparationDay;
	}
}
