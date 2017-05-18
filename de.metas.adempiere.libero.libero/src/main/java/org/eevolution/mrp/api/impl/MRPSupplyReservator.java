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
import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.util.TrxRunnable;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPDemand;
import org.eevolution.mrp.api.IMRPDemandAllocationResult;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPQueryBuilder;
import org.eevolution.mrp.api.IMRPSuppliesPool;
import org.eevolution.mrp.api.MRPFirmType;
import org.slf4j.Logger;

import de.metas.material.planning.IMRPContextFactory;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMutableMRPContext;

public class MRPSupplyReservator
{
	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);

	// MRP Errors
	public static final String MRP_ERROR_900_NegativeGrossRequirements = "MRP-900"; // shall not happen // TODO: add message

	// Parameters
	private MRPExecutor _mrpExecutor = null;

	public void setMRPExecutor(MRPExecutor mrpExecutor)
	{
		this._mrpExecutor = mrpExecutor;
	}

	private MRPExecutor getMRPExecutor()
	{
		Check.assumeNotNull(_mrpExecutor, "mrpExecutor not null");
		return _mrpExecutor;
	}

	private IMRPNotesCollector getMRPNotesCollector()
	{
		return getMRPExecutor().getMRPNotesCollector();
	}

	/**
	 * 
	 * NOTE: after running this method, {@link IMaterialPlanningContext#setQtyProjectOnHand(BigDecimal)} is adjusted by subtracting Projected QtyOnHand that was reserved.
	 * 
	 * @param mrpContext
	 * @param mrpDemand
	 */
	public IMRPDemandAllocationResult reserveMRPSupplies(final IMaterialPlanningContext mrpContext, final IMRPDemand mrpDemand)
	{
		final IMutable<IMRPDemandAllocationResult> result = new Mutable<>();
		trxManager.run(mrpContext.getTrxName(), new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final IMutableMRPContext mrpContextLocalTrx = mrpContextFactory.createMRPContext(mrpContext);
				mrpContextLocalTrx.setTrxName(localTrxName);
				final IMRPDemandAllocationResult mrpDemandAllocationResult = reserveMRPSupplies0(mrpContextLocalTrx, mrpDemand);
				result.setValue(mrpDemandAllocationResult);

				// Copy back Qtys
				mrpContext.setQtyProjectOnHand(mrpContextLocalTrx.getQtyProjectOnHand()); // copy it because we need to keep carrying it until another project will be evaluated; also we are updating
			}
		});

		return result.getValue();
	}

	private final IMRPDemandAllocationResult reserveMRPSupplies0(final IMaterialPlanningContext mrpContext, final IMRPDemand mrpDemand)
	{
		trxManager.assertTrxNotNull(mrpContext);

		final boolean mrpDemandValid = checkMRPDemand(mrpContext, mrpDemand);
		if (!mrpDemandValid)
		{
			return MRPDemandAllocationResult.ZERO;
		}

		//
		// Create a pool of Demands that needs to be allocated
		final IMRPDemandsPool mrpDemandsPool = new MRPDemandsPool(mrpContext, getMRPNotesCollector(), mrpDemand);

		//
		// Create the pools of Supplies that are available and ready to be allocated.
		final List<IMRPSuppliesPool> mrpSuppliesPoolList = retrieveAvailableMRPSuppliesPools(mrpContext);

		//
		// Allocate Demands to Supplies
		mrpDemandsPool.allocateAllMRPDemands(mrpSuppliesPoolList);

		//
		// Return the allocation result
		return mrpDemandsPool.createMRPDemandAllocationResult();
	}

	/**
	 * Checks if given MRP demand is valid and we shall start plan the MRP supplies for it.
	 * 
	 * @param mrpContext
	 * @param mrpDemand
	 * @return true if given MRP demand is valid and we can go on and try to allocate to supplies
	 */
	private boolean checkMRPDemand(final IMaterialPlanningContext mrpContext, final IMRPDemand mrpDemand)
	{
		//
		// Qty Gross Requirement (i.e. how much we need to allocate, how much is the demand)
		final BigDecimal qtyGrossReqsWithoutYield = mrpDemand.getMRPDemandsQty();
		// Case: Gross requirements are ZERO => nothing to do
		if (qtyGrossReqsWithoutYield.signum() == 0)
		{
			return false;
		}
		// Case: negative Gross requirements (i.e. kind of supply), shall not happen
		else if (qtyGrossReqsWithoutYield.signum() < 0)
		{
			getMRPNotesCollector().newMRPNoteBuilder(mrpContext, MRP_ERROR_900_NegativeGrossRequirements)
					.addParameter("QtyGrossReq", qtyGrossReqsWithoutYield)
					.addParameter("DemandDateStartSchedule", mrpDemand.getDateStartSchedule())
					.addMRPRecords(mrpDemand.getMRPDemandRecords()) // thouse are our trouble makers
					.setComment("Negative Gross requirements. Ignored.")
					.collect();
			return false;
		}

		return true;
	}

	/**
	 * Flag remaining supplies as not needed. Basically it will
	 * <ul>
	 * <li>mark them as not available
	 * <li>create an MRP notice to inform the planner (see {@link MRPExecutor#MRP_ERROR_050_CancelSupplyNotice}).
	 * </ul>
	 * 
	 * @param mrpContext
	 */
	public void cancelRemainingMRPSupplies(final IMaterialPlanningContext mrpContext)
	{
		final ListMRPSuppliesPool mrpSuppliesPool = new ListMRPSuppliesPool(mrpContext, getMRPExecutor());

		final List<IMutableMRPRecordAndQty> mrpSupplies = retrieveAvailableMRPSuppplies(mrpContext);
		mrpSuppliesPool.setMRPSupplies(mrpSupplies);

		mrpSuppliesPool.cancelSupplies();
	}

	/**
	 * 
	 * @param mrpContext
	 * @return available MRP supplies to be allocated
	 */
	private List<IMutableMRPRecordAndQty> retrieveAvailableMRPSuppplies(final IMaterialPlanningContext mrpContext)
	{
		final IMRPQueryBuilder mrpQueryBuilder = createSuppliesMRPQueryBuilder(mrpContext);
		
		// Make sure we are excluding the records which have BPartners/Warehouses/etc excluded from MRP calculation.
		mrpQueryBuilder.setSkipIfMRPExcluded(true);

		// Only those who are Available (i.e. not marked by MRP executor as already considered)
		mrpQueryBuilder.setMRPAvailable(true);

		// Create Query Builder
		final IQueryBuilder<I_PP_MRP> queryBuilder = mrpQueryBuilder.createQueryBuilder();

		//
		// Sort MRP supply records by DateStartSchedule
		queryBuilder.orderBy()
				.addColumn(I_PP_MRP.COLUMN_DateStartSchedule)
				.addColumn(I_PP_MRP.COLUMN_PP_MRP_ID);

		//
		// Fetch records
		final List<I_PP_MRP> mrpSupplies = queryBuilder.create()
				.list(I_PP_MRP.class);

		//
		// Return them
		return MRPRecordAndQty.asMutableMRPRecordAndQtyList(mrpSupplies);
	}

	private List<IMRPSuppliesPool> retrieveAvailableMRPSuppliesPools(final IMaterialPlanningContext mrpContext)
	{
		final QtyOnHandMRPSuppliesPool qtyOnHandMRPSuppliesPool = new QtyOnHandMRPSuppliesPool(mrpContext);

		//
		// Create & configure our supply pools
		final MRPExecutor mrpExecutor = getMRPExecutor();
		final List<IMutableMRPRecordAndQty> mrpSupplies = retrieveAvailableMRPSuppplies(mrpContext);
		final List<IMRPSuppliesPool> mrpSuppliesPoolList = new ArrayList<>();

		// 1: Update allocations for those MRP demands which were already allocated to MRP supplies.
		{
			final AlreadyAllocatedMRPSuppliesPool mrpSuppliesPool = new AlreadyAllocatedMRPSuppliesPool(mrpContext, mrpExecutor);
			mrpSuppliesPool.setMRPSupplies(mrpSupplies);
			mrpSuppliesPool.setQtyOnHand(qtyOnHandMRPSuppliesPool);
			mrpSuppliesPoolList.add(mrpSuppliesPool);
		}

		// 2: Try to allocate QtyOnHand to MRP demand
		{
			qtyOnHandMRPSuppliesPool.setMRPSupplies(mrpSupplies);
			mrpSuppliesPoolList.add(qtyOnHandMRPSuppliesPool);
		}

		// 3: Try to allocate the other remaining MRP supplies
		{
			final ListMRPSuppliesPool mrpSuppliesPool = new ListMRPSuppliesPool(mrpContext, mrpExecutor);
			mrpSuppliesPool.setMRPSupplies(mrpSupplies);
			mrpSuppliesPoolList.add(mrpSuppliesPool);
		}

		return mrpSuppliesPoolList;
	}

	private IMRPQueryBuilder createSuppliesMRPQueryBuilder(final IMaterialPlanningContext mrpContext)
	{
		final IMRPExecutor mrpExecutor = getMRPExecutor();
		final IMRPQueryBuilder mrpQueryBuilder = mrpExecutor.createMRPQueryBuilder(mrpContext)
				.setSkipIfMRPExcluded(true)
				// Only supply records
				.setTypeMRP(X_PP_MRP.TYPEMRP_Supply)
				// Only Scheduled receipts (firm) and Planned Orders (not firm) suppplies
				.setMRPFirmType(MRPFirmType.FirmAndNotFirm)
		// .setQtyNotZero(true) // retrieve even if Qty=0 because we need to check the allocation to know what to do. This is uber important!
		;

		return mrpQueryBuilder;
	}

	public void cleanupQuantityOnHandReservations(final IMaterialPlanningContext mrpContext)
	{
		final Logger logger = mrpContext.getLogger();
		final int countDeleted = createSuppliesMRPQueryBuilder(mrpContext)
				.setSkipIfMRPExcluded(false) // don't exclude them, we want to get rid of them!
				.setTypeMRP(X_PP_MRP.TYPEMRP_Supply)
				.addOnlyOrderType(X_PP_MRP.ORDERTYPE_QuantityOnHandReservation)
				.addOnlyOrderType(X_PP_MRP.ORDERTYPE_QuantityOnHandInTransit)
				.deleteMRPRecords();

		logger.info("Deleted QtyOnHand reservations: #{}", countDeleted);
	}

	public void markMRPSuppliesAvailable(final IMaterialPlanningContext mrpContext)
	{
		final Logger logger = mrpContext.getLogger();

		final int mrpSupplies_UpdateCount = createSuppliesMRPQueryBuilder(mrpContext)
				.setMRPAvailable(null) // match all, available or not; we need to get all of them available
				.updateMRPRecordsAndMarkAvailable();
		logger.debug("Marked supply available for #{} records\n{}", new Object[] { mrpSupplies_UpdateCount, mrpContext });

	}
}
