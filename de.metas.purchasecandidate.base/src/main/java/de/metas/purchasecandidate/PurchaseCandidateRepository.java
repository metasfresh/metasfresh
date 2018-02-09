package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemRepository;
import lombok.NonNull;

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
	private final LockOwner lockOwner = LockOwner.newOwner(PurchaseCandidateRepository.class.getSimpleName());

	private final PurchaseItemRepository purchaseItemRepository;

	public PurchaseCandidateRepository(@NonNull final PurchaseItemRepository purchaseItemRepository)
	{
		this.purchaseItemRepository = purchaseItemRepository;
	}

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

	public List<Integer> retrievePurchaseCandidateIdsBySalesOrderIdFilterQtyToPurchase(final int salesOrderId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, salesOrderId)
				.andCollectChildren(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID)
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_Processed, false)
				.addCompareFilter(I_C_PurchaseCandidate.COLUMN_QtyToPurchase, Operator.GREATER, BigDecimal.ZERO)
				.create()
				.listIds();
	}

	public void saveAll(final Collection<PurchaseCandidate> purchaseCandidates)
	{
		boolean doLock = true;
		saveAll(purchaseCandidates, doLock);
	}

	public void saveAllNoLock(final Collection<PurchaseCandidate> purchaseCandidates)
	{
		boolean doLock = false;
		saveAll(purchaseCandidates, doLock);
	}

	private void saveAll(final Collection<PurchaseCandidate> purchaseCandidates, final boolean doLock)
	{
		final List<PurchaseCandidate> purchaseCandidatesToSave = purchaseCandidates.stream()
				.filter(PurchaseCandidate::hasChanges)
				.collect(ImmutableList.toImmutableList());
		if (purchaseCandidatesToSave.isEmpty())
		{
			return;
		}

		final Set<Integer> existingPurchaseCandidateIds = purchaseCandidatesToSave.stream()
				.map(PurchaseCandidate::getPurchaseCandidateId)
				.filter(id -> id > 0)
				.collect(ImmutableSet.toImmutableSet());

		final Map<Integer, I_C_PurchaseCandidate> existingRecordsById;
		if (!existingPurchaseCandidateIds.isEmpty())
		{
			existingRecordsById = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_PurchaseCandidate.class)
					.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_C_PurchaseCandidate_ID, existingPurchaseCandidateIds)
					.create()
					.map(I_C_PurchaseCandidate.class, I_C_PurchaseCandidate::getC_PurchaseCandidate_ID);
		}
		else
		{
			existingRecordsById = ImmutableMap.of();
		}

		final ILockAutoCloseable lock = doLock && !existingPurchaseCandidateIds.isEmpty() ? lockByIds(existingPurchaseCandidateIds) : null;
		try
		{
			purchaseCandidatesToSave.forEach(purchaseCandidate -> {
				final int repoId = purchaseCandidate.getPurchaseCandidateId();
				final I_C_PurchaseCandidate existingRecord = repoId > 0 ? existingRecordsById.get(repoId) : null;
				save(purchaseCandidate, existingRecord);
			});
		}
		finally
		{
			if (lock != null)
			{
				lock.close();
			}
		}
	}

	private ILockAutoCloseable lockByIds(final Set<Integer> purchaseCandidateIds)
	{
		return Services.get(ILockManager.class).lock()
				.setOwner(lockOwner)
				.setAutoCleanup(true)
				.addRecordsByModel(TableRecordReference.ofRecordIds(I_C_PurchaseCandidate.Table_Name, purchaseCandidateIds))
				.acquire()
				.asAutoCloseable();
	}

	/**
	 * Note to dev: keep in sync with {@link #toPurchaseCandidate(I_C_PurchaseCandidate)}
	 */
	private final void save(final PurchaseCandidate purchaseCandidate, final I_C_PurchaseCandidate existingRecord)
	{
		if (existingRecord != null)
		{
			if (existingRecord.isProcessed() && !purchaseCandidate.isProcessed())
			{
				throw new AdempiereException("Purchase candidate was already processed").setParameter("purchaseCandidate", purchaseCandidate);
			}
		}

		I_C_PurchaseCandidate record = existingRecord;
		if (record == null)
		{
			record = newInstance(I_C_PurchaseCandidate.class);
		}

		record.setC_OrderSO_ID(purchaseCandidate.getSalesOrderId());
		record.setC_OrderLineSO_ID(purchaseCandidate.getSalesOrderLineId());

		record.setAD_Org_ID(purchaseCandidate.getOrgId());
		record.setM_WarehousePO_ID(purchaseCandidate.getWarehouseId());
		record.setM_Product_ID(purchaseCandidate.getProductId());
		record.setC_UOM_ID(purchaseCandidate.getUomId());
		record.setVendor_ID(purchaseCandidate.getVendorBPartnerId());
		record.setC_BPartner_Product_ID(purchaseCandidate.getVendorProductInfo().getBPartnerProductId());
		record.setQtyToPurchase(purchaseCandidate.getQtyToPurchase());
		record.setDateRequired(TimeUtil.asTimestamp(purchaseCandidate.getDateRequired()));

		record.setProcessed(purchaseCandidate.isProcessed());

		InterfaceWrapperHelper.save(record);
		purchaseCandidate.markSaved(record.getC_PurchaseCandidate_ID());
	}

	public PurchaseCandidate retrieveById(final int purchaseCandidateId)
	{
		Check.assume(purchaseCandidateId > 0, "The given parameter purchaseCandidateId > 0");

		final I_C_PurchaseCandidate record = load(purchaseCandidateId, I_C_PurchaseCandidate.class);
		Check.errorIf(record == null, "Unable to load I_C_PurchaseCandidate record for C_PurchaseCandidate_ID={}",
				purchaseCandidateId);

		return toPurchaseCandidate(record);
	}

	/**
	 * Note to dev: keep in sync with {@link #save(PurchaseCandidate, I_C_PurchaseCandidate)}
	 */
	private PurchaseCandidate toPurchaseCandidate(@NonNull final I_C_PurchaseCandidate purchaseCandidatePO)
	{
		final boolean locked = Services.get(ILockManager.class).isLocked(purchaseCandidatePO);

		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.purchaseCandidateId(purchaseCandidatePO.getC_PurchaseCandidate_ID())
				.salesOrderId(purchaseCandidatePO.getC_OrderSO_ID())
				.salesOrderLineId(purchaseCandidatePO.getC_OrderLineSO_ID())
				.orgId(purchaseCandidatePO.getAD_Org_ID())
				.warehouseId(purchaseCandidatePO.getM_WarehousePO_ID())
				.productId(purchaseCandidatePO.getM_Product_ID())
				.uomId(purchaseCandidatePO.getC_UOM_ID())
				.vendorBPartnerId(purchaseCandidatePO.getVendor_ID())
				.vendorProductInfo(VendorProductInfo.fromDataRecord(purchaseCandidatePO.getC_BPartner_Product()))
				.qtyToPurchase(purchaseCandidatePO.getQtyToPurchase())
				.dateRequired(purchaseCandidatePO.getDateRequired())
				.processed(purchaseCandidatePO.isProcessed())
				.locked(locked)
				.build();

		purchaseItemRepository.retrieveForPurchaseCandidate(purchaseCandidate);

		return purchaseCandidate;
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
