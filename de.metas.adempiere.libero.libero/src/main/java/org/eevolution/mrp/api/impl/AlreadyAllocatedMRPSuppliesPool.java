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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPContextRunnable;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPSuppliesPool;

import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMutableMRPContext;

/**
 * An {@link IMRPSuppliesPool} implementation which is actually checking the current MRP Demand-Supply allocations status.
 *
 * As a result, it can:
 * <ul>
 * <li>1. create QtyOnHand reservations for MRP demands where there is QtyOnHand available
 * <li>2. re-confirm that the allocation is still valid (at least a partial quantity from it)
 * <li>3. create QtyInTransit reservations for MRP demand's remaining quantity
 * </ul>
 *
 * @author tsa
 *
 */
public class AlreadyAllocatedMRPSuppliesPool implements IMRPSuppliesPool
{
	// Services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IMRPBL mrpBL = Services.get(IMRPBL.class);
	private final transient IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

	// Parameters
	@ToStringBuilder(skip = true)
	private final IMaterialPlanningContext _mrpContext;
	@ToStringBuilder(skip = true)
	private final MRPExecutor _mrpExecutor;

	private QtyOnHandMRPSuppliesPool _qtyOnHandMRPSuppliesPool = null;
	private LinkedHashMap<Integer, IMutableMRPRecordAndQty> _mrpSuppliesAvailable = null;

	public AlreadyAllocatedMRPSuppliesPool(final IMaterialPlanningContext mrpContext, final MRPExecutor mrpExecutor)
	{
		super();

		Check.assumeNotNull(mrpContext, "mrpContext not null");
		_mrpContext = mrpContext;

		Check.assumeNotNull(mrpExecutor, "mrpExecutor not null");
		_mrpExecutor = mrpExecutor;
	}

	private final IMaterialPlanningContext getMRPContext()
	{
		return _mrpContext;
	}

	protected final MRPExecutor getMRPExecutor()
	{
		return _mrpExecutor;
	}

	@Override
	public final void setMRPSupplies(final List<IMutableMRPRecordAndQty> mrpSupplies)
	{
		Check.assumeNotNull(mrpSupplies, "mrpSupplies not null");
		Check.assumeNull(_mrpSuppliesAvailable, "MRP Supplies not already configured");

		_mrpSuppliesAvailable = new LinkedHashMap<>(mrpSupplies.size());
		for (final IMutableMRPRecordAndQty mrpSupply : mrpSupplies)
		{
			final int mrpSupplyId = mrpSupply.getPP_MRP().getPP_MRP_ID();
			_mrpSuppliesAvailable.put(mrpSupplyId, mrpSupply);
		}
	}

	/**
	 * Gets the MRP supply from our pool, which is referred by given MRP allocation.
	 *
	 * @param mrpAlloc
	 * @return MRP supply or <code>null</code>
	 */
	private IMutableMRPRecordAndQty getMRPSupplyOrNull(final I_PP_MRP_Alloc mrpAlloc)
	{
		final int ppMRPSupplyId = mrpAlloc.getPP_MRP_Supply_ID();
		final IMutableMRPRecordAndQty mrpSupply = _mrpSuppliesAvailable.get(ppMRPSupplyId);
		return mrpSupply;
	}

	public void setQtyOnHand(final QtyOnHandMRPSuppliesPool qtyOnHandMRPSuppliesPool)
	{
		_qtyOnHandMRPSuppliesPool = qtyOnHandMRPSuppliesPool;
	}

	private final IMRPSuppliesPool getQtyOnHand()
	{
		Check.assumeNotNull(_qtyOnHandMRPSuppliesPool, "_qtyOnHandMRPSuppliesPool not null");
		return _qtyOnHandMRPSuppliesPool;
	}

	@Override
	public final List<IMRPDemandToSupplyAllocation> allocate(final IMutableMRPRecordAndQty mrpDemand)
	{
		final List<IMRPDemandToSupplyAllocation> allocsAll = new ArrayList<>();

		final List<I_PP_MRP_Alloc> mrpAllocs = getMRPAllocsForDemand(mrpDemand);
		for (final I_PP_MRP_Alloc mrpAlloc : mrpAllocs)
		{
			final List<IMRPDemandToSupplyAllocation> allocs = allocate(mrpDemand, mrpAlloc);
			allocsAll.addAll(allocs);
		}

		return allocsAll;
	}

	/**
	 * Check given MRP demand and allocate it from 1. QtyOnHand, 2. available MRP supply, 3. QtyInTransit.
	 *
	 * @param mrpDemand
	 * @param mrpAlloc
	 * @return
	 */
	private final List<IMRPDemandToSupplyAllocation> allocate(final IMutableMRPRecordAndQty mrpDemand0, final I_PP_MRP_Alloc mrpAlloc)
	{
		final List<IMRPDemandToSupplyAllocation> allocs = new ArrayList<>();

		// Get the MRP Supply for that MRP Demand-Supply allocation.
		// If the MRP Supply was not found in our pool then skip this demand allocation because there is nothing we can do.
		final IMutableMRPRecordAndQty mrpSupply0 = getMRPSupplyOrNull(mrpAlloc);
		if (mrpSupply0 == null)
		{
			return Collections.emptyList();
		}

		// mark it as not available (i.e. flag it as considered), first time when we look at it.
		markNotAvailable(mrpSupply0);

		final BigDecimal qtyAllocated = mrpAlloc.getQtyAllocated();
		final IMutableMRPRecordAndQty mrpSupply = new BoundedMRPRecordAndQty(mrpSupply0, qtyAllocated);
		final IMutableMRPRecordAndQty mrpDemand = new BoundedMRPRecordAndQty(mrpDemand0, qtyAllocated);

		//
		// 1. Allocate MRP demand's quantity to allocate to current QtyOnHand, as much as possible
		{
			final List<IMRPDemandToSupplyAllocation> allocs_QtyOnHand = allocateToQtyOnHand(mrpDemand);
			allocs.addAll(allocs_QtyOnHand);
		}

		//
		// 2. Allocate MRP demand using how much is still available on MRP supply
		{
			//
			// Calculate how much demand quantity we can allocate
			// i.e. the actual MRP demand's qty to allocate (remaining), but not more than how much supply is available to allocate
			final BigDecimal qtyDemandToAllocate = mrpDemand.getQty(); // ... without qtyOnHand which was already allocated
			final BigDecimal qtySupplyAvailableToAllocate = mrpSupply.getQty();
			final BigDecimal qtyDemand = qtyDemandToAllocate.min(qtySupplyAvailableToAllocate);

			if (qtyDemand.signum() > 0)
			{
				final IMRPDemandToSupplyAllocation alloc_QtySupply = allocate(mrpDemand, mrpSupply.getPP_MRP(), qtyDemand);
				allocs.add(alloc_QtySupply);
			}
		}

		//
		// 3. Allocate remaining MRP demand's quantity (if any) to QtyInTransit
		// i.e. this quantity was not OnHand, is no longer available on supply so it means that it's on the road...
		{
			final BigDecimal qtyInTransit = mrpDemand.getQty();
			if (qtyInTransit.signum() > 0)
			{
				final IMRPDemandToSupplyAllocation alloc_QtyInTransit = allocateToQtyInTransit(mrpDemand, qtyInTransit);
				if (alloc_QtyInTransit != null)
				{
					allocs.add(alloc_QtyInTransit);
				}
			}
		}

		//
		// Mark everything as allocated
		mrpSupply.setQty(BigDecimal.ZERO);
		mrpDemand.setQty(BigDecimal.ZERO);

		//
		// Return all allocations the we have created
		return allocs;
	}

	/**
	 * Retrieves {@link I_PP_MRP_Alloc} for given MRP demand.
	 *
	 * @param mrpDemand
	 * @return list of MRP allocations or empty list
	 */
	private final List<I_PP_MRP_Alloc> getMRPAllocsForDemand(final IMutableMRPRecordAndQty mrpDemand)
	{
		// NOTE: i think there is no need to cache this list because it's used one time only for each MRP demand.

		final I_PP_MRP mrpDemandRecord = mrpDemand.getPP_MRP();
		final List<I_PP_MRP_Alloc> mrpAllocs = mrpDAO.retrieveMRPAllocsForDemandQuery(mrpDemandRecord)
				.create()
				.list();

		return mrpAllocs;
	}

	/**
	 * Allocate MRP demand's quantity to allocate to current QtyOnHand, as much as possible.
	 *
	 * As a result, MRP Demand's quantity will be decreased in case of an allocation.
	 *
	 * @param mrpDemand
	 * @return list of MRP Demand-Supply allocations or empty list
	 */
	private final List<IMRPDemandToSupplyAllocation> allocateToQtyOnHand(final IMutableMRPRecordAndQty mrpDemand)
	{
		final List<IMRPDemandToSupplyAllocation> allocs_qtyOnHand = getQtyOnHand().allocate(mrpDemand);
		return allocs_qtyOnHand;
	}

	private final IMRPDemandToSupplyAllocation allocate(final IMutableMRPRecordAndQty mrpDemand, final I_PP_MRP mrpSupplyRecord, final BigDecimal qtyToAllocate)
	{
		Check.assumeNotNull(qtyToAllocate, "qtyToAllocate not null");
		Check.assume(qtyToAllocate.signum() > 0, "qtyToAllocate > 0 but it was {}", qtyToAllocate);

		// mrpSupply.subtractQty(qtyToAllocate);
		mrpDemand.subtractQty(qtyToAllocate);

		final I_PP_MRP mrpDemandRecord = mrpDemand.getPP_MRP();
		final IMRPDemandToSupplyAllocation alloc = new MRPDemandToSupplyAllocation(mrpDemandRecord, mrpSupplyRecord, qtyToAllocate);
		return alloc;
	}

	private final IMRPDemandToSupplyAllocation allocateToQtyInTransit(final IMutableMRPRecordAndQty mrpDemand, final BigDecimal qtyInTransit)
	{
		final I_PP_MRP mrpSupplyRecord = createMRPSupplyForQtyOnHandInTransit(mrpDemand, qtyInTransit);
		return allocate(mrpDemand, mrpSupplyRecord, qtyInTransit);
	}

	private final I_PP_MRP createMRPSupplyForQtyOnHandInTransit(final IMRPRecordAndQty mrpDemand, final BigDecimal qtyInTransit)
	{
		Check.assume(qtyInTransit.signum() > 0, "qtyInTransit > 0 but it was {}", qtyInTransit);

		final IMaterialPlanningContext mrpContext = getMRPContext();
		Check.assumeNotNull(mrpContext, "mrpContext not null");
		trxManager.assertTrxNotNull(mrpContext);

		final I_PP_MRP mrpDemandRecord = mrpDemand.getPP_MRP();
		final I_C_UOM uom = mrpBL.getC_UOM(mrpDemandRecord);

		final IMutable<I_PP_MRP> mrpSupplyToReturn = new Mutable<I_PP_MRP>();
		mrpBL.executeInMRPContext(mrpContext, new IMRPContextRunnable()
		{

			@Override
			public void run(final IMutableMRPContext mrpContextLocal)
			{
				final Timestamp date = mrpDemandRecord.getDateStartSchedule();
				mrpContextLocal.setMRPDemands(Collections.singletonList(mrpDemandRecord));

				final I_PP_MRP mrpQtyInTransit = mrpBL.createMRP(mrpContextLocal);
				// Planning segment
				mrpQtyInTransit.setAD_Org(mrpContextLocal.getAD_Org());
				mrpQtyInTransit.setM_Warehouse(mrpContextLocal.getM_Warehouse());
				mrpQtyInTransit.setS_Resource(mrpContextLocal.getPlant());
				mrpQtyInTransit.setM_Product(mrpContextLocal.getM_Product());
				// Type
				mrpQtyInTransit.setTypeMRP(X_PP_MRP.TYPEMRP_Supply);
				mrpQtyInTransit.setOrderType(X_PP_MRP.ORDERTYPE_QuantityOnHandInTransit);
				mrpQtyInTransit.setDocStatus(X_PP_MRP.DOCSTATUS_Drafted); // Not Firm
				// Qtys
				final BigDecimal qtyRequired = BigDecimal.ZERO;
				mrpBL.setQty(mrpQtyInTransit, qtyRequired, qtyInTransit, uom);
				// Dates
				mrpQtyInTransit.setDateOrdered(date);
				mrpQtyInTransit.setDatePromised(date);
				mrpQtyInTransit.setDateConfirm(date);
				mrpQtyInTransit.setDateStartSchedule(date);
				mrpQtyInTransit.setDateFinishSchedule(date);
				mrpQtyInTransit.setDateStart(date);
				// Other
				mrpQtyInTransit.setIsAvailable(false); // not available because it is allocated already (that's why we are doing this)
				mrpQtyInTransit.setPP_MRP_Parent(mrpDemandRecord);
				mrpQtyInTransit.setC_BPartner_ID(mrpDemandRecord.getC_BPartner_ID());
				mrpQtyInTransit.setC_OrderLineSO_ID(mrpDemandRecord.getC_OrderLineSO_ID());
				//
				// Save it
				InterfaceWrapperHelper.save(mrpQtyInTransit);
				mrpSupplyToReturn.setValue(mrpQtyInTransit);

				// NOTE: we assume "MRP demand to MRP supply PP_MRP_Alloc" is done automatically be a interceptor on PP_MRP
			}
		});

		return mrpSupplyToReturn.getValue();
	}

	protected final void markNotAvailable(final IMRPRecordAndQty mrpSupply)
	{
		final I_PP_MRP mrpSupplyRecord = mrpSupply.getPP_MRP();
		final MRPExecutor mrpExecutor = getMRPExecutor();
		mrpExecutor.markNotAvailable(mrpSupplyRecord);
	}

}
