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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPExecutor;

import de.metas.material.planning.IMaterialPlanningContext;


public /* package */final class MRPCreateSupplyRequest implements IMRPCreateSupplyRequest
{
	private final IMaterialPlanningContext mrpContext;
	private final IMRPExecutor mrpExecutor;
	private final BigDecimal qtyToSupply;
	private final Date demandDate;
	/**
	 * Single MRP Record that needs to be allocated.
	 * 
	 * NOTE: in case there are more MRP demand records that needs to be allocated, this field will be null
	 */
	private final I_PP_MRP mrpDemandRecord;
	private final List<I_PP_MRP> mrpDemandRecords;
	private final int mrpDemandBPartnerId;
	private final int mrpDemandOrderLineSOId;

	public MRPCreateSupplyRequest(final IMaterialPlanningContext mrpContext,
			final IMRPExecutor mrpExecutor,
			final BigDecimal qtyToSupply,
			final Date demandDate,
			final List<IMRPRecordAndQty> mrpDemandsToAllocate)
	{
		super();
		Check.assumeNotNull(mrpContext, "mrpContext not null");
		this.mrpContext = mrpContext;

		Check.assumeNotNull(mrpExecutor, "mrpExecutor not null");
		this.mrpExecutor = mrpExecutor;

		Check.assumeNotNull(qtyToSupply, "qtyToSupply not null");
		this.qtyToSupply = qtyToSupply;

		Check.assumeNotNull(demandDate, "demandDate not null");
		this.demandDate = demandDate;

		Check.assumeNotNull(mrpDemandsToAllocate, "mrpDemandsToAllocate not null");
		final Map<Integer, I_PP_MRP> id2mrpRecord = new HashMap<>();
		final Set<Integer> bpartnerIds = new HashSet<>();
		final Set<Integer> orderLineSOIds = new HashSet<>();
		for (final IMRPRecordAndQty mrp : mrpDemandsToAllocate)
		{
			final I_PP_MRP mrpRecord = mrp.getPP_MRP();
			final int mrpRecordId = mrpRecord.getPP_MRP_ID();
			id2mrpRecord.put(mrpRecordId, mrpRecord);

			final int bpartnerId = mrpRecord.getC_BPartner_ID();
			if (bpartnerId > 0)
			{
				bpartnerIds.add(bpartnerId);
			}

			final int orderLineSOId = mrpRecord.getC_OrderLineSO_ID();
			if (orderLineSOId > 0)
			{
				orderLineSOIds.add(orderLineSOId);
			}
		}

		//
		// MRP Demand record(s)
		this.mrpDemandRecords = Collections.unmodifiableList(new ArrayList<>(id2mrpRecord.values()));
		if (mrpDemandRecords.size() == 1)
		{
			this.mrpDemandRecord = mrpDemandRecords.get(0);
		}
		else
		{
			this.mrpDemandRecord = null;
		}

		//
		// C_BPartner_ID
		if (bpartnerIds.size() == 1)
		{
			this.mrpDemandBPartnerId = bpartnerIds.iterator().next();
		}
		else
		{
			this.mrpDemandBPartnerId = -1;
		}

		//
		// C_OrderLineSO_ID
		if (orderLineSOIds.size() == 1)
		{
			this.mrpDemandOrderLineSOId = orderLineSOIds.iterator().next();
		}
		else
		{
			this.mrpDemandOrderLineSOId = -1;
		}
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public IMaterialPlanningContext getMrpContext()
	{
		return mrpContext;
	}

	@Override
	public IMRPExecutor getMRPExecutor()
	{
		return mrpExecutor;
	}

	@Override
	public BigDecimal getQtyToSupply()
	{
		return qtyToSupply;
	}

	@Override
	public Date getDemandDate()
	{
		return demandDate;
	}

	@Override
	public I_PP_MRP getMRPDemandRecordOrNull()
	{
		return mrpDemandRecord;
	}

	@Override
	public List<I_PP_MRP> getMRPDemandRecords()
	{
		return mrpDemandRecords;
	}

	@Override
	public int getMrpDemandBPartnerId()
	{
		return mrpDemandBPartnerId;
	}

	@Override
	public int getMrpDemandOrderLineSOId()
	{
		return mrpDemandOrderLineSOId;
	}
}
