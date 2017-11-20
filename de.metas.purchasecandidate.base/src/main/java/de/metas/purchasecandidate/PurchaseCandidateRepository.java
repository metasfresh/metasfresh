package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;

import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Repository
public class PurchaseCandidateRepository
{
	public Stream<PurchaseCandidate> streamAllBySalesOrderLineIds(final Collection<Integer> salesOrderLineIds)
	{
		if (salesOrderLineIds.isEmpty())
		{
			return Stream.empty();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMNNAME_C_OrderLineSO_ID, salesOrderLineIds)
				.create()
				.stream(I_C_PurchaseCandidate.class)
				.map(this::toPurchaseCandidate);
	}

	public Stream<PurchaseCandidate> streamAllByIds(final Collection<Integer> purchaseCandidateIds)
	{
		if (purchaseCandidateIds.isEmpty())
		{
			return Stream.empty();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID, purchaseCandidateIds)
				.create()
				.stream(I_C_PurchaseCandidate.class)
				.map(this::toPurchaseCandidate);
	}
	
	public List<Integer> getAllPurchaseCandidateIdsBySalesOrderId(final int salesOrderId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, salesOrderId)
				.andCollectChildren(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID)
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_Processed, false)
				.create()
				.listIds();
	}

	public void saveAll(final Collection<PurchaseCandidate> purchaseCandidates)
	{
		if (purchaseCandidates.isEmpty())
		{
			return;
		}

		final Set<Integer> purchaseCandidateIds = purchaseCandidates.stream()
				.map(PurchaseCandidate::getRepoId)
				.filter(id -> id > 0)
				.collect(ImmutableSet.toImmutableSet());

		final Map<Integer, I_C_PurchaseCandidate> recordsById = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_C_PurchaseCandidate_ID, purchaseCandidateIds)
				.create()
				.map(I_C_PurchaseCandidate.class, I_C_PurchaseCandidate::getC_PurchaseCandidate_ID);

		purchaseCandidates.forEach(purchaseCandidate -> {
			final int repoId = purchaseCandidate.getRepoId();
			final I_C_PurchaseCandidate existingRecord = repoId > 0 ? recordsById.get(repoId) : null;
			save(purchaseCandidate, existingRecord);
		});
	}

	/**
	 * Note to dev: keep in sync with {@link #toPurchaseCandidate(I_C_PurchaseCandidate)}
	 */
	private final void save(final PurchaseCandidate purchaseCandidate, final I_C_PurchaseCandidate existingRecord)
	{
		I_C_PurchaseCandidate record = existingRecord;
		if (record == null)
		{
			record = newInstance(I_C_PurchaseCandidate.class);
		}

		record.setC_OrderLineSO_ID(purchaseCandidate.getSalesOrderLineId());
		record.setC_OrderLinePO_ID(purchaseCandidate.getPurchaseOrderLineId());
		record.setAD_Org_ID(purchaseCandidate.getOrgId());
		record.setM_Warehouse_ID(purchaseCandidate.getWarehouseId());
		record.setM_Product_ID(purchaseCandidate.getProductId());
		record.setC_UOM_ID(purchaseCandidate.getUomId());
		record.setVendor_ID(purchaseCandidate.getVendorBPartnerId());
		record.setQtyRequiered(purchaseCandidate.getQtyRequired());
		record.setDatePromised(TimeUtil.asTimestamp(purchaseCandidate.getDatePromised()));

		record.setProcessed(purchaseCandidate.isProcessed());

		InterfaceWrapperHelper.save(record);

		purchaseCandidate.setRepoId(record.getC_PurchaseCandidate_ID());
	}

	/**
	 * Note to dev: keep in sync with {@link #save(PurchaseCandidate, I_C_PurchaseCandidate)}
	 */
	private PurchaseCandidate toPurchaseCandidate(final I_C_PurchaseCandidate purchaseCandidatePO)
	{
		return PurchaseCandidate.builder()
				.repoId(purchaseCandidatePO.getC_PurchaseCandidate_ID())
				.salesOrderLineId(purchaseCandidatePO.getC_OrderLineSO_ID())
				.orgId(purchaseCandidatePO.getAD_Org_ID())
				.warehouseId(purchaseCandidatePO.getM_Warehouse_ID())
				.productId(purchaseCandidatePO.getM_Product_ID())
				.uomId(purchaseCandidatePO.getC_UOM_ID())
				.vendorBPartnerId(purchaseCandidatePO.getVendor_ID())
				.qtyRequired(purchaseCandidatePO.getQtyRequiered())
				.datePromised(purchaseCandidatePO.getDatePromised())
				.processed(purchaseCandidatePO.isProcessed())
				.build();
	}

	public void deleteByIds(Collection<Integer> purchaseCandidateIds)
	{
		if (purchaseCandidateIds.isEmpty())
		{
			return;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID, purchaseCandidateIds)
				.create()
				.delete();
	}
}
