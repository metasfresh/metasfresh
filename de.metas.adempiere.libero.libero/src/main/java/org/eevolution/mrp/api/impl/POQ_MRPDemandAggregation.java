package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.Date;

import org.adempiere.util.Check;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Product_Planning;

/**
 * Period Order Quantity (POQ) ordering type.
 * 
 * @author tsa
 *
 */
public class POQ_MRPDemandAggregation extends AbstractMRPDemandAggregation
{
	// Parameters
	private int _orderPeriodDays = 1; // 1 Days, means same day
	private boolean aggregateByBPartner = false;

	//
	// Status
	private Date _poqDateFrom;
	private Date _poqDateTo;
	//
	private int _bpartnerId = -1;

	@Override
	protected void afterInit(final I_PP_Product_Planning productPlanning)
	{
		this._orderPeriodDays = productPlanning.getOrder_Period().intValueExact();
		Check.assume(_orderPeriodDays >= 1, "orderPeriodDays >= 1");

		this.aggregateByBPartner = productPlanning.isPP_POQ_AggregateOnBPartnerLevel();
	}

	public Date getPOQDateFrom()
	{
		return _poqDateFrom;
	}

	public Date getPOQDateTo()
	{
		return _poqDateTo;
	}

	private int getOrderPeriodDays()
	{
		return _orderPeriodDays;
	}

	@Override
	public boolean canAdd(I_PP_MRP mrpDemand)
	{
		// accept anything if it's empty
		if (!hasDemands())
		{
			return true;
		}

		final Date demandDatePromised = mrpDemand.getDatePromised();

		// Check if demand's date promise if before our period start
		// NOTE: the upper margin of the interval is APLWAYS open because orderPeriodDays=1 means 1 day
		// => don't accept it
		final Date poqDateFrom = getPOQDateFrom();
		if (demandDatePromised.compareTo(poqDateFrom) < 0)
		{
			return false;
		}

		// Check if demand's date promise if after our period end
		// => don't accept it
		final Date poqDateTo = getPOQDateTo();
		if (demandDatePromised.compareTo(poqDateTo) >= 0)
		{
			return false;
		}

		// Check if aggregating by BPartner, we shall have the same BPartner
		if (aggregateByBPartner)
		{
			int mrpDemandBPartnerId = mrpDemand.getC_BPartner_ID();
			mrpDemandBPartnerId = mrpDemandBPartnerId <= 0 ? -1 : mrpDemandBPartnerId; // normalize the negative ID
			if (this._bpartnerId != mrpDemandBPartnerId)
			{
				return false;
			}
		}

		//
		// Accept MRP demand in our bucket
		return true;
	}

	@Override
	protected void afterInitFromFirstMRPDemand(final I_PP_MRP mrpDemand)
	{
		final Date demandDatePromised = mrpDemand.getDatePromised();
		Check.assumeNotNull(demandDatePromised, "demandDatePromised not null");

		//
		// Set POQ interval
		final int periodDays = getOrderPeriodDays();
		this._poqDateFrom = demandDatePromised;
		this._poqDateTo = TimeUtil.addDays(demandDatePromised, periodDays);
		// NOTE: in old code it was something like "POQDateStartSchedule = (level == 0 ? DatePromised : DateStartSchedule);" but i think is not needed because we can relly on DateStartSchedule

		if (aggregateByBPartner)
		{
			final int mrpBPartnerId = mrpDemand.getC_BPartner_ID();
			this._bpartnerId = mrpBPartnerId <= 0 ? -1 : mrpBPartnerId;
		}
		else
		{
			this._bpartnerId = -1;
		}
	}
}
