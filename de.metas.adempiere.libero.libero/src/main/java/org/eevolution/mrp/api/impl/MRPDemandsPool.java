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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPDemand;
import org.eevolution.mrp.api.IMRPDemandAllocationResult;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPSuppliesPool;

import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.pporder.LiberoException;

public class MRPDemandsPool implements IMRPDemandsPool
{

	//
	// Services
	private final IMRPBL mrpBL = Services.get(IMRPBL.class);

	// Params
	private final IMaterialPlanningContext _mrpContext;
	private final IMRPNotesCollector _mrpNotesCollector;

	//
	// Status
	private final Date demandDateStartSchedule;
	private BigDecimal qtyNetReqRemaining;
	private BigDecimal qtySuppliedTotal = BigDecimal.ZERO;
	private BigDecimal qtyOnHandReserved = BigDecimal.ZERO;
	private BigDecimal qtyScheduledReceipts = BigDecimal.ZERO;
	private List<IMutableMRPRecordAndQty> mrpDemandsToAllocate;
	private List<IMRPDemandToSupplyAllocation> mrpDemand2supplyAllocations = new ArrayList<>();
	private Set<ArrayKey> _collectedMRPNoteKeys = new HashSet<>();

	public MRPDemandsPool(final IMaterialPlanningContext mrpContext, final IMRPNotesCollector mrpNotesCollector, final IMRPDemand mrpDemand)
	{
		super();

		Check.assumeNotNull(mrpContext, "mrpContext not null");
		this._mrpContext = mrpContext;

		Check.assumeNotNull(mrpNotesCollector, "mrpNotesCollector not null");
		this._mrpNotesCollector = mrpNotesCollector;

		Check.assumeNotNull(mrpDemand, LiberoException.class, "mrpDemand not null");

		//
		// Create MRP Demand & Qty that we will need to allocated
		BigDecimal qtyGrossReq = BigDecimal.ZERO;
		final List<IMutableMRPRecordAndQty> mrpDemandAndQtys = new ArrayList<IMutableMRPRecordAndQty>();
		final MRPYield yield = mrpDemand.getYield();
		for (final I_PP_MRP mrpDemandRecord : mrpDemand.getMRPDemandRecords())
		{
			final BigDecimal mrpDemandQty = mrpDemandRecord.getQty();
			final BigDecimal mrpDemandQtyWithYield = yield.calculateQtyWithYield(mrpDemandQty);
			final MRPRecordAndQty mrpDemandAndQty = new MRPRecordAndQty(mrpDemandRecord, mrpDemandQtyWithYield);

			qtyGrossReq = qtyGrossReq.add(mrpDemandQtyWithYield);
			mrpDemandAndQtys.add(mrpDemandAndQty);
		}

		this.demandDateStartSchedule = mrpDemand.getDateStartSchedule();

		this.qtyNetReqRemaining = qtyGrossReq;
		this.mrpDemandsToAllocate = mrpDemandAndQtys;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	private IMaterialPlanningContext getMRPContext()
	{
		return _mrpContext;
	}

	private IMRPNotesCollector getMRPNotesCollector()
	{
		return _mrpNotesCollector;
	}

	@Override
	public void allocateAllMRPDemands(final List<IMRPSuppliesPool> mrpSuppliesPools)
	{
		Check.assumeNotEmpty(mrpSuppliesPools, "mrpSuppliesPools not empty");

		//
		// Iterate each existing MRP demand that needs to be allocated
		final Iterator<IMutableMRPRecordAndQty> mrpDemandsToAllocateIterator = mrpDemandsToAllocate.iterator();
		while (mrpDemandsToAllocateIterator.hasNext())
		{
			final IMutableMRPRecordAndQty mrpDemand = mrpDemandsToAllocateIterator.next();

			//
			// Iterate each MRP supplies pool and ask to allocate
			for (final IMRPSuppliesPool mrpSuppliesPool : mrpSuppliesPools)
			{
				// Check if the MRP demand was fully allocated. If it was there is no point to try allocating it.
				if (mrpDemand.isZeroQty())
				{
					break;
				}

				// Ask current MRP supplies pool to allocate it
				final List<IMRPDemandToSupplyAllocation> mrpDemandAllocations = mrpSuppliesPool.allocate(mrpDemand);
				addAllocationsPerformed(mrpDemandAllocations);
			}
		}
	}

	private final void addAllocationsPerformed(final List<IMRPDemandToSupplyAllocation> allocations)
	{
		if (allocations == null || allocations.isEmpty())
		{
			return;
		}

		for (final IMRPDemandToSupplyAllocation alloc : allocations)
		{
			addAllocationPerformed(alloc);
		}
	}

	private final void addAllocationPerformed(final IMRPDemandToSupplyAllocation alloc)
	{
		Check.assumeNotNull(alloc, "alloc not null");

		final I_PP_MRP mrpSupplyRecord = alloc.getMRPSupply();
		final BigDecimal mrpSupplyQty = alloc.getQtyAllocated();

		//
		// Check allocated MRP supply and create MRP notes in case something is not ok
		checkMRPSupplyDates(alloc);

		//
		// Update cummulated values
		if (mrpBL.isQtyOnHandReservation(mrpSupplyRecord))
		{
			this.qtyOnHandReserved = this.qtyOnHandReserved.add(mrpSupplyQty);
		}
		else if (mrpBL.isQtyOnHandAnyReservation(mrpSupplyRecord))
		{
			// skip any other kind of reservations
		}
		else
		{
			final boolean mrpSupplyIsFirm = mrpBL.isReleased(mrpSupplyRecord);
			if (mrpSupplyIsFirm)
			{
				this.qtyScheduledReceipts = qtyScheduledReceipts.add(mrpSupplyQty);
			}
			this.qtySuppliedTotal = qtySuppliedTotal.add(mrpSupplyQty);
		}

		subtractFromQtyToSupplyRemaining(mrpSupplyQty);

		mrpDemand2supplyAllocations.add(alloc);
	}

	private final void subtractFromQtyToSupplyRemaining(final BigDecimal qtySupplied)
	{
		this.qtyNetReqRemaining = qtyNetReqRemaining.subtract(qtySupplied);

		// enforce class invariant: qtyNetReqRemaining >= 0 !
		Check.assume(qtyNetReqRemaining.signum() >= 0, "qtyNetReqRemaining >= 0 but it was {} after subtracting {}", qtyNetReqRemaining, qtySupplied);
	}

	/**
	 * Check Scheduled Dates for given MRP Supply record and creates MRP notes if needed
	 */
	private void checkMRPSupplyDates(final IMRPDemandToSupplyAllocation mrpDemandToSupplyAlloc)
	{
		//
		// Extract from parameters only what we need
		final IMaterialPlanningContext mrpContext = getMRPContext();
		final Date mrpRunDate = mrpContext.getDate(); // date when MRP is executed; this is our reference date
		final I_PP_MRP mrpDemandRecord = mrpDemandToSupplyAlloc.getMRPDemand(); // NOTE: MRP Demand is needed only as a reference, no other informations are/shall be used from there
		final I_PP_MRP mrpSupplyRecord = mrpDemandToSupplyAlloc.getMRPSupply();
		final BigDecimal mrpSupplyQty = mrpDemandToSupplyAlloc.getQtyAllocated();

		// In case is QOH allocation, consider it valid
		if (mrpBL.isQtyOnHandAnyReservation(mrpSupplyRecord))
		{
			return;
		}

		//
		//
		final boolean mrpSupplyIsFirm = mrpBL.isReleased(mrpSupplyRecord);
		final boolean mrpSupplyQtyNotZero = mrpSupplyQty.signum() != 0;
		final Date mrpSupplyDateFinishSchedule = mrpSupplyRecord.getDateFinishSchedule(); // Date when the supply it's scheduled by MRP to be available in our warehouse
		final Date mrpSupplyDatePromised = mrpSupplyRecord.getDatePromised(); // Date when supply is promised by "vendor" to be available in our warehouse

		//
		// MRP-030 De-Expedite Action Notice
		// Indicates that a schedule supply order is due before it is needed and should be delayed,
		// or demand rescheduled to an earlier date.
		// aka: Push Out
		if (mrpSupplyIsFirm
				&& mrpSupplyQtyNotZero
				&& mrpSupplyDateFinishSchedule != null
				&& mrpSupplyDateFinishSchedule.compareTo(demandDateStartSchedule) < 0 // supply is scheduled to arrive before it's needed
		)
		{
			newSupplyMRPNote(mrpSupplyRecord, "MRP-030")
					.addParameter("DateSupply", mrpSupplyDateFinishSchedule)
					.addParameter("DateDemand", demandDateStartSchedule)
					.addParameter("PP_MRP_Demand_ID", mrpDemandRecord)
					.collect();
		}

		//
		// MRP-040 Expedite Action Notice
		// Indicates that a scheduled supply order is due after is needed and should be rescheduled to
		// an earlier date or demand rescheduled to a later date.
		// aka: Pull In
		if (mrpSupplyIsFirm
				&& mrpSupplyQtyNotZero
				&& mrpSupplyDateFinishSchedule != null
				&& mrpSupplyDateFinishSchedule.compareTo(demandDateStartSchedule) > 0 // supply is scheduled to arrive after it's needed (that could be a serious problem)
		)
		{
			newSupplyMRPNote(mrpSupplyRecord, "MRP-040")
					.addParameter("DateSupply", mrpSupplyDateFinishSchedule)
					.addParameter("DateDemand", demandDateStartSchedule)
					.addParameter("PP_MRP_Demand_ID", mrpDemandRecord)
					.collect();
		}

		//
		// MRP-060 Release Due For Action Notice in time
		// Indicate that a supply order should be released. if it is a draft order, it must also be approved.
		// if(date release > today && date release + after floating)
		if (!mrpSupplyIsFirm
				&& mrpSupplyQtyNotZero
				&& mrpSupplyDatePromised.compareTo(mrpRunDate) >= 0)
		{
			newSupplyMRPNote(mrpSupplyRecord, MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased)
					.addParameter("SupplyDatePromised", mrpSupplyDatePromised)
					.addParameter("MRPDateRun", mrpRunDate)
					.collect();
		}

		//
		// MRP-070 Release Past Due For Action Notice overdue
		// Indicates that a supply order was not released when it was due, and should be either released
		// or expedited now, or the demand rescheduled for a later date.
		// if (date release < today && date erelese + before floating)
		if (!mrpSupplyIsFirm
				&& mrpSupplyQtyNotZero
				&& mrpSupplyDatePromised.compareTo(mrpRunDate) < 0)
		{
			newSupplyMRPNote(mrpSupplyRecord, MRPExecutor.MRP_ERROR_070_SupplyPastDueButNotReleased)
					.addParameter("SupplyDatePromised", mrpSupplyDatePromised)
					.addParameter("MRPDateRun", mrpRunDate)
					.collect();
		}

		//
		// MRP-110 Past Due Action Notice
		// Indicates that a schedule supply order receipt is past due.
		// i.e. it's a firm supply which was scheduled to be released in the past, but so far it's not received yet.
		if (mrpSupplyIsFirm
				&& mrpSupplyDatePromised.compareTo(mrpRunDate) < 0)
		{
			newSupplyMRPNote(mrpSupplyRecord, MRPExecutor.MRP_ERROR_110_SupplyPastDueButReleased)
					.addParameter("SupplyDatePromised", mrpSupplyDatePromised)
					.addParameter("MRPDateRun", mrpRunDate)
					.collect();
		}
	}

	private IMRPNoteBuilder newSupplyMRPNote(final I_PP_MRP mrpSupply, final String mrpErrorCode)
	{
		final IMaterialPlanningContext mrpContext = getMRPContext();
		final IMRPNotesCollector mrpNotesCollector = getMRPNotesCollector();

		final IMRPNoteBuilder noteBuilder = mrpNotesCollector.newMRPNoteBuilder(mrpContext, mrpErrorCode)
				.addParameter("PP_MRP_Supply_ID", mrpSupply)
				.addMRPRecord(mrpSupply) // that's the MRP record which causes troubles
		;

		// If we already reported this note, flag it as duplicate.
		final ArrayKey noteKey = Util.mkKey(mrpErrorCode, mrpSupply.getPP_MRP_ID());
		if (!_collectedMRPNoteKeys.add(noteKey))
		{
			noteBuilder.setDuplicate();
		}

		return noteBuilder;
	}

	@Override
	public IMRPDemandAllocationResult createMRPDemandAllocationResult()
	{
		//
		// Copy the MRP demands to allocate
		final List<IMutableMRPRecordAndQty> mrpDemandsToAllocateCopy = new ArrayList<>();
		for (final IMutableMRPRecordAndQty mrpDemand : mrpDemandsToAllocate)
		{
			// Skip those with ZERO quantity because there nothing to be done there (i.e. they were already allocated)
			if (mrpDemand.isZeroQty())
			{
				continue;
			}

			final MRPRecordAndQty mrpDemandCopy = new MRPRecordAndQty(mrpDemand);
			mrpDemandsToAllocateCopy.add(mrpDemandCopy);
		}

		//
		// Create and return the result
		return new MRPDemandAllocationResult(qtySuppliedTotal,
				qtyScheduledReceipts,
				qtyOnHandReserved,
				qtyNetReqRemaining,
				mrpDemandsToAllocateCopy,
				mrpDemand2supplyAllocations);
	}
}
