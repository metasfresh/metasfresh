package org.eevolution.mrp.api.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.lang.ObjectUtils;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;

import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.quantity.Quantity;
import lombok.NonNull;


public /* package */final class MRPCreateSupplyRequest implements IMRPCreateSupplyRequest
{
	private final IMaterialPlanningContext mrpContext;
	private final Quantity qtyToSupply;
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

	public MRPCreateSupplyRequest(
			@NonNull final IMaterialPlanningContext mrpContext,
			@NonNull final Quantity qtyToSupply,
			@NonNull final Date demandDate,
			@NonNull final List<IMRPRecordAndQty> mrpDemandsToAllocate)
	{
		this.mrpContext = mrpContext;
		this.qtyToSupply = qtyToSupply;
		this.demandDate = demandDate;

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
	public Quantity getQtyToSupply()
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
