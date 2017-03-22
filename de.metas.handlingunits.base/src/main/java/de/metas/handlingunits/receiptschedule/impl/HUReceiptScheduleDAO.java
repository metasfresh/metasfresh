package de.metas.handlingunits.receiptschedule.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;

public class HUReceiptScheduleDAO implements IHUReceiptScheduleDAO
{
	// private final static transient Logger logger = CLogMgt.getLogger(HUReceiptScheduleDAO.class);

	private final transient IQueryFilter<I_M_ReceiptSchedule_Alloc> isHandlingUnitAllocationFilter = Services.get(IQueryBL.class)
			.createCompositeQueryFilter(I_M_ReceiptSchedule_Alloc.class)
			.setJoinOr()
			.addNotEqualsFilter(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_LU_HU_ID, null)
			.addNotEqualsFilter(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_TU_HU_ID, null);

	@Override
	public void deleteHandlingUnitAllocations(final de.metas.inoutcandidate.model.I_M_ReceiptSchedule receiptSchedule, final Collection<I_M_HU> husToUnassign, final String trxName)
	{
		if (husToUnassign == null || husToUnassign.isEmpty())
		{
			return;
		}

		final Set<Integer> huIdsToRemove = new HashSet<Integer>();
		for (final I_M_HU hu : husToUnassign)
		{
			huIdsToRemove.add(hu.getM_HU_ID());
		}

		for (final I_M_ReceiptSchedule_Alloc rsa : retrieveHandlingUnitAllocations(receiptSchedule, trxName))
		{
			final int tuHUId = rsa.getM_TU_HU_ID();
			final int luHUId = rsa.getM_LU_HU_ID();
			final int vhuId = rsa.getVHU_ID();
			if (!huIdsToRemove.contains(tuHUId)
					&& !huIdsToRemove.contains(luHUId)
					&& !huIdsToRemove.contains(vhuId))
			{
				continue;
			}

			InterfaceWrapperHelper.delete(rsa);
		}
	}

	@Override
	public void deleteTradingUnitAllocations(final I_M_ReceiptSchedule receiptSchedule, final Collection<I_M_HU> tusToUnassign, final String trxName)
	{
		if (tusToUnassign == null || tusToUnassign.isEmpty())
		{
			return;
		}

		final List<Integer> tuIdsToUnassign = new ArrayList<Integer>();
		for (final I_M_HU tuToUnassign : tusToUnassign)
		{
			Check.assumeNotNull(tuToUnassign, "tuToUnassign not null");
			tuIdsToUnassign.add(tuToUnassign.getM_HU_ID());
		}

		//
		// Use query builder for RSAs and filter TU-HUs
		final IQueryBuilder<I_M_ReceiptSchedule_Alloc> rsaQueryBuilder = retrieveHandlingUnitAllocationsQueryBuilder(receiptSchedule, trxName)
				.addInArrayOrAllFilter(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_TU_HU_ID, tuIdsToUnassign);

		//
		// Fetch RSA entries for TU-HUs
		final List<I_M_ReceiptSchedule_Alloc> receiptScheduleAllocations = rsaQueryBuilder.create()
				.list(I_M_ReceiptSchedule_Alloc.class);

		//
		// Delete all allocations
		for (final I_M_ReceiptSchedule_Alloc rsa : receiptScheduleAllocations)
		{
			InterfaceWrapperHelper.delete(rsa);
		}
	}

	private IQueryBuilder<I_M_ReceiptSchedule_Alloc> retrieveHandlingUnitAllocationsQueryBuilder(final de.metas.inoutcandidate.model.I_M_ReceiptSchedule schedule, final String trxName)
	{
		final IQueryBuilder<I_M_ReceiptSchedule_Alloc> queryBuilder = Services.get(IReceiptScheduleDAO.class)
				.createRsaForRsQueryBuilder(schedule, I_M_ReceiptSchedule_Alloc.class, trxName);

		queryBuilder.filter(isHandlingUnitAllocationFilter);

		return queryBuilder;
	}

	@Override
	public List<I_M_ReceiptSchedule_Alloc> retrieveHandlingUnitAllocations(final de.metas.inoutcandidate.model.I_M_ReceiptSchedule schedule, final String trxName)
	{
		final IQuery<I_M_ReceiptSchedule_Alloc> query = retrieveHandlingUnitAllocationsQueryBuilder(schedule, trxName).create();
		return query.list(I_M_ReceiptSchedule_Alloc.class);
	}

	@Override
	public void deleteHandlingUnitAllocations(final de.metas.inoutcandidate.model.I_M_ReceiptSchedule schedule, final String trxName)
	{
		final IQuery<I_M_ReceiptSchedule_Alloc> query = retrieveHandlingUnitAllocationsQueryBuilder(schedule, trxName).create();
		query.delete();
	}

	@Override
	public BigDecimal getQtyAllocatedOnHUs(final I_M_ReceiptSchedule schedule)
	{
		final BigDecimal huQtyAllocated = Services.get(IReceiptScheduleDAO.class)
				.createRsaForRsQueryBuilder(schedule, I_M_ReceiptSchedule_Alloc.class)
				.addOnlyActiveRecordsFilter() // make sure we are counting only active records
				.create()
				.aggregate(I_M_ReceiptSchedule_Alloc.COLUMNNAME_HU_QtyAllocated, IQuery.AGGREGATE_SUM, BigDecimal.class);

		return huQtyAllocated;
	}

	@Override
	public List<I_M_ReceiptSchedule> retrievePackingMaterialReceiptSchedules(final Properties ctx, final String headerAggregationKey, final int packingMaterialProductId)
	{
		Check.assumeNotEmpty(headerAggregationKey, "headerAggregationKey not empty");
		Check.assume(packingMaterialProductId > 0, "packingMaterialProductId > 0");

		final IQueryBuilder<I_M_ReceiptSchedule> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ReceiptSchedule.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_HeaderAggregationKey, headerAggregationKey)
				// .addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_IsPackagingMaterial, true) // cannot use it directly because it's a virtual column
				.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_M_Product_ID, packingMaterialProductId)
				.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_Processed, false);

		queryBuilder.orderBy()
				.addColumn(de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID);

		final List<I_M_ReceiptSchedule> packingMaterialReceiptSchedules = queryBuilder.create().list();

		//
		// Filter out receipt schedule lines which are not packing materials
		for (final Iterator<I_M_ReceiptSchedule> it = packingMaterialReceiptSchedules.iterator(); it.hasNext();)
		{
			final I_M_ReceiptSchedule packingMaterialReceiptSchedule = it.next();
			if (!packingMaterialReceiptSchedule.isPackagingMaterial())
			{
				it.remove();
			}
		}

		return packingMaterialReceiptSchedules;
	}

	@Override
	public void updateAllocationLUForTU(final I_M_HU tuHU)
	{
		// Services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		Check.assume(handlingUnitsBL.isTransportUnitOrVirtual(tuHU), "{} shall be a TU", tuHU);

		//
		// Get the LU
		final I_M_HU luHU = handlingUnitsBL.getLoadingUnitHU(tuHU);

		//
		// Retrieve allocations which are about our TU
		final List<I_M_ReceiptSchedule_Alloc> allocs = queryBL.createQueryBuilder(I_M_ReceiptSchedule_Alloc.class, tuHU)
				.addEqualsFilter(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_TU_HU_ID, tuHU.getM_HU_ID())
				// Only those which are active
				.addOnlyActiveRecordsFilter()
				// Only those which were not received.
				// Those which were received we consider as "Processed" and we don't want to touch them
				.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_InOutLine_ID, null)
				//
				.create()
				.list();

		//
		// Iterate all allocations and update M_LU_HU_ID
		for (final I_M_ReceiptSchedule_Alloc alloc : allocs)
		{
			// Update LU
			alloc.setM_LU_HU(luHU);
			InterfaceWrapperHelper.save(alloc);
		}
	}

	@Override
	public I_M_ReceiptSchedule retrieveReceiptScheduleForVHU(final I_M_HU vhu)
	{
		// Services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// Validate
		Check.assume(handlingUnitsBL.isVirtual(vhu), "{} shall be VirtualHU", vhu);

		return queryBL.createQueryBuilder(I_M_ReceiptSchedule_Alloc.class, vhu)
				.addEqualsFilter(I_M_ReceiptSchedule_Alloc.COLUMNNAME_VHU_ID, vhu.getM_HU_ID())
				// Only those which are active
				.addOnlyActiveRecordsFilter()
				// Only those which were not received.
				// Those which were received we consider as "Processed" and we don't want to touch them
				.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_InOutLine_ID, null)
				//
				// Collect the referenced Receipt schedule
				// NOTE: we assume it's only one because a VHU can be assigned to only one receipt schedule!
				.andCollect(de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc.COLUMN_M_ReceiptSchedule_ID)
				.create()
				.firstOnly(I_M_ReceiptSchedule.class);
	}
}
