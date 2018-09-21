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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPDemand;

import de.metas.util.Check;

public final class MRPDemand implements IMRPDemand
{
	private final Date dateStartSchedule;
	private final BigDecimal mrpDemandsQtyTotal;
	private final MRPYield yield;
	@ToStringBuilder(skip = true)
	private final List<I_PP_MRP> mrpDemandRecords;

	public MRPDemand(final Date dateStartSchedule, final BigDecimal mrpDemandsQtyTotal, final MRPYield yield, final List<I_PP_MRP> mrpDemandRecords)
	{
		super();

		Check.assumeNotNull(dateStartSchedule, "dateStartSchedule not null");
		this.dateStartSchedule = (Date)dateStartSchedule.clone();

		Check.assumeNotNull(mrpDemandsQtyTotal, "mrpDemandsQtyTotal not null");
		this.mrpDemandsQtyTotal = mrpDemandsQtyTotal;

		Check.assumeNotNull(yield, "yield not null");
		this.yield = yield;

		if (mrpDemandRecords == null || mrpDemandRecords.isEmpty())
		{
			this.mrpDemandRecords = Collections.emptyList();
		}
		else
		{
			this.mrpDemandRecords = Collections.unmodifiableList(new ArrayList<I_PP_MRP>(mrpDemandRecords));
		}
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public Date getDateStartSchedule()
	{
		return dateStartSchedule;
	}

	@Override
	public BigDecimal getMRPDemandsQty()
	{
		return mrpDemandsQtyTotal;
	}

	@Override
	public List<I_PP_MRP> getMRPDemandRecords()
	{
		return mrpDemandRecords;
	}

	@Override
	public final boolean hasDemandsRecords()
	{
		return !mrpDemandRecords.isEmpty();
	}

	@Override
	public MRPYield getYield()
	{
		return yield;
	}

}
