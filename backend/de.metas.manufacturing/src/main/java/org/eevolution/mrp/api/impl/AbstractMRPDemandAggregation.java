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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.mrp.api.IMRPDemand;
import org.eevolution.mrp.api.IMRPDemandAggregation;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;

public abstract class AbstractMRPDemandAggregation implements IMRPDemandAggregation
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	//
	// Parameters
	private MRPYield _yield = MRPYield.ZERO;

	//
	// Status
	private Date dateStartSchedule;
	private BigDecimal mrpDemandQty = BigDecimal.ZERO;
	@ToStringBuilder(skip = true)
	private final List<I_PP_MRP> mrpDemands = new ArrayList<>();

	public AbstractMRPDemandAggregation()
	{
		super();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	private MRPYield getYield()
	{
		return _yield;
	}

	@Override
	public final void init(final I_PP_Product_Planning productPlanning)
	{
		Check.assumeNotNull(productPlanning, "productPlanning not null");
		final int yieldInt = productPlanning.getYield();
		this._yield = MRPYield.valueOf(yieldInt);
		
		afterInit(productPlanning);
	}

	protected void afterInit(final I_PP_Product_Planning productPlanning)
	{
		// nothing on this level
	}

	protected final void initFromFirstMRPDemand(final I_PP_MRP mrpDemand)
	{
		// Demand DateStartSchedule:
		final Date demandDateStartSchedule = mrpDemand.getDateStartSchedule();
		Check.assumeNotNull(demandDateStartSchedule, "demandDateStartSchedule not null");
		dateStartSchedule = demandDateStartSchedule;
		
		afterInitFromFirstMRPDemand(mrpDemand);
	}

	protected void afterInitFromFirstMRPDemand(final I_PP_MRP mrpDemand)
	{
		// nothing on this level
	}

	@Override
	public final void addMRPDemand(final I_PP_MRP mrpDemand)
	{
		if (!hasDemands())
		{
			initFromFirstMRPDemand(mrpDemand);
		}

		final BigDecimal qty = mrpDemand.getQty();

		mrpDemandQty = mrpDemandQty.add(qty);
		mrpDemands.add(mrpDemand);

		logger.debug("Demand Qty (Accumulation):" + mrpDemandQty);
	}

	@Override
	public Date getDateStartSchedule()
	{
		return dateStartSchedule;
	}

	protected void setDateStartSchedule(final Date dateStartSchedule)
	{
		this.dateStartSchedule = dateStartSchedule;
	}

	protected final BigDecimal getMRPDemandQty()
	{
		return mrpDemandQty;
	}

	@Override
	public final boolean hasDemands()
	{
		return !mrpDemands.isEmpty();
	}

	@Override
	public final IMRPDemand createMRPDemand()
	{
		final Date dateStartSchedule = getDateStartSchedule();
		final BigDecimal mrpDemandQtyTotal = getMRPDemandQty();
		final MRPYield yield = getYield();
		final MRPDemand mrpDemand = new MRPDemand(dateStartSchedule, mrpDemandQtyTotal, yield, mrpDemands);
		return mrpDemand;
	}
}
