package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.OrgId;
import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.money.Currency;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
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

	private final CurrencyRepository currencyRepository;

	public PurchaseCandidateRepository(
			@NonNull final PurchaseItemRepository purchaseItemRepository,
			@NonNull final CurrencyRepository currencyRepository)
	{
		this.purchaseItemRepository = purchaseItemRepository;
		this.currencyRepository = currencyRepository;
	}

	public Stream<PurchaseCandidate> streamAllByPurchaseDemandIds(@NonNull final Collection<PurchaseDemandId> purchaseDemandIds)
	{
		if (purchaseDemandIds.isEmpty())
		{
			return Stream.empty();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_PurchaseCandidate> queryBuilder = queryBL.createQueryBuilder(I_C_PurchaseCandidate.class);

		final ICompositeQueryFilter<I_C_PurchaseCandidate> recordsFilter = queryBL.createCompositeQueryFilter(I_C_PurchaseCandidate.class)
				.setJoinOr();

		final Map<String, Collection<Integer>> recordIdsByTableName = purchaseDemandIds.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(PurchaseDemandId::getTableName, PurchaseDemandId::getRecordId))
				.asMap();

		for (final String tableName : recordIdsByTableName.keySet())
		{
			if (I_C_OrderLine.Table_Name.equals(tableName))
			{
				recordsFilter.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID, recordIdsByTableName.get(tableName));
			}
			else
			{
				throw new AdempiereException("Unsupported purchase demand tablename: " + tableName);
			}
		}

		return queryBuilder
				.addOnlyActiveRecordsFilter()
				.filter(recordsFilter)
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
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_C_PurchaseCandidate_ID, purchaseCandidateIds)
				.create()
				.stream(I_C_PurchaseCandidate.class)
				.map(this::toPurchaseCandidate);
	}

	public List<PurchaseCandidate> getAllByIds(final Collection<Integer> purchaseCandidateIds)
	{
		return streamAllByIds(purchaseCandidateIds).collect(ImmutableList.toImmutableList());
	}

	public List<Integer> retrieveManualPurchaseCandidateIdsBySalesOrderIdFilterQtyToPurchase(final int salesOrderId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMN_C_Order_ID, salesOrderId)
				.andCollectChildren(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID)
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_IsAggregatePO, false) // manual
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_Processed, false)
				.addCompareFilter(I_C_PurchaseCandidate.COLUMN_QtyToPurchase, Operator.GREATER, BigDecimal.ZERO)
				.create()
				.listIds();
	}

	public void saveAll(final Collection<PurchaseCandidate> purchaseCandidates)
	{
		final boolean doLock = true;
		saveAll(purchaseCandidates, doLock);
	}

	public void saveAllNoLock(final Collection<PurchaseCandidate> purchaseCandidates)
	{
		final boolean doLock = false;
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
			for (final PurchaseCandidate purchaseCandidate : purchaseCandidatesToSave)
			{
				final int repoId = purchaseCandidate.getPurchaseCandidateId();
				final I_C_PurchaseCandidate existingRecord = repoId > 0 ? existingRecordsById.get(repoId) : null;
				createOrUpdateRecord(purchaseCandidate, existingRecord);

				purchaseItemRepository.storeRecords(purchaseCandidate.getPurchaseOrderItems());
				purchaseItemRepository.storeRecords(purchaseCandidate.getPurchaseErrorItems());
			} ;
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
	private final void createOrUpdateRecord(final PurchaseCandidate purchaseCandidate, final I_C_PurchaseCandidate existingRecord)
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

		record.setC_OrderSO_ID(purchaseCandidate.getSalesOrderId().getRepoId());
		record.setC_OrderLineSO_ID(purchaseCandidate.getSalesOrderLineId().getRepoId());

		record.setAD_Org_ID(purchaseCandidate.getOrgId().getRepoId());
		record.setM_WarehousePO_ID(purchaseCandidate.getWarehouseId().getRepoId());
		record.setM_Product_ID(purchaseCandidate.getProductId().getRepoId());
		record.setC_UOM_ID(purchaseCandidate.getUomId());
		record.setQtyToPurchase(purchaseCandidate.getQtyToPurchase());
		record.setDateRequired(TimeUtil.asTimestamp(purchaseCandidate.getDateRequired()));
		record.setReminderDate(TimeUtil.asTimestamp(purchaseCandidate.getReminderDate()));

		final BPartnerId vendorBPartnerId = purchaseCandidate.getVendorBPartnerId();
		record.setVendor_ID(vendorBPartnerId != null ? vendorBPartnerId.getRepoId() : -1);
		record.setC_BPartner_Product_ID(purchaseCandidate.getBpartnerProductId().orElse(-1));
		record.setIsAggregatePO(purchaseCandidate.isAggregatePOs());

		// set monetary values
		final PurchaseProfitInfo profitInfo = purchaseCandidate.getProfitInfo();
		record.setCustomerPriceGrossProfit(profitInfo.getSalesNetPrice().getValue());
		record.setPriceGrossProfit(profitInfo.getPurchaseNetPrice().getValue());
		record.setPurchasePriceActual(profitInfo.getPurchaseGrossPrice().getValue());
		record.setC_Currency_ID(profitInfo.getCommonCurrency().getId().getRepoId());

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
	 * Note to dev: keep in sync with {@link #createOrUpdateRecord(PurchaseCandidate, I_C_PurchaseCandidate)}
	 */
	private PurchaseCandidate toPurchaseCandidate(@NonNull final I_C_PurchaseCandidate purchaseCandidateRecord)
	{
		final boolean locked = Services.get(ILockManager.class).isLocked(purchaseCandidateRecord);

		final VendorProductInfo vendorProductInfo = extractVendorProductInfo(purchaseCandidateRecord);

		final Currency currency = currencyRepository.ofRecord(purchaseCandidateRecord.getC_Currency());

		final PurchaseProfitInfo profitInfo = PurchaseProfitInfo.builder()
				.salesNetPrice(Money.of(purchaseCandidateRecord.getCustomerPriceGrossProfit(), currency))
				.purchaseNetPrice(Money.of(purchaseCandidateRecord.getPriceGrossProfit(), currency))
				.purchaseGrossPrice(Money.of(purchaseCandidateRecord.getPurchasePriceActual(), currency))
				.build();

		final PurchaseCandidate purchaseCandidate = PurchaseCandidate
				.builder()
				.locked(locked)
				.purchaseCandidateId(purchaseCandidateRecord.getC_PurchaseCandidate_ID())
				.salesOrderId(OrderId.ofRepoId(purchaseCandidateRecord.getC_OrderSO_ID()))
				.salesOrderLineId(OrderLineId.ofRepoId(purchaseCandidateRecord.getC_OrderLineSO_ID()))
				.orgId(OrgId.ofRepoId(purchaseCandidateRecord.getAD_Org_ID()))
				.warehouseId(WarehouseId.ofRepoId(purchaseCandidateRecord.getM_WarehousePO_ID()))
				.productId(ProductId.ofRepoId(purchaseCandidateRecord.getM_Product_ID()))
				.uomId(purchaseCandidateRecord.getC_UOM_ID())
				.vendorProductInfo(vendorProductInfo)
				.qtyToPurchase(purchaseCandidateRecord.getQtyToPurchase())
				.dateRequired(TimeUtil.asLocalDateTime(purchaseCandidateRecord.getDateRequired()))
				.profitInfo(profitInfo)
				.processed(purchaseCandidateRecord.isProcessed())
				.build();

		purchaseItemRepository.retrieveForPurchaseCandidate(purchaseCandidate);

		return purchaseCandidate;
	}

	private VendorProductInfo extractVendorProductInfo(final I_C_PurchaseCandidate purchaseCandidatePO)
	{
		final I_C_BPartner_Product bpartnerProduct = purchaseCandidatePO.getC_BPartner_Product_ID() > 0
				? loadOutOfTrx(purchaseCandidatePO.getC_BPartner_Product_ID(), I_C_BPartner_Product.class)
				: null;
		// TODO: handle the null case!
		final VendorProductInfo vendorProductInfo = VendorProductInfo.fromDataRecord(
				bpartnerProduct,
				BPartnerId.ofRepoIdOrNull(purchaseCandidatePO.getVendor_ID()),
				purchaseCandidatePO.isAggregatePO());
		return vendorProductInfo;
	}

	public void deleteByIds(Collection<Integer> purchaseCandidateIds)
	{
		if (purchaseCandidateIds.isEmpty())
		{
			return;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_C_PurchaseCandidate_ID, purchaseCandidateIds)
				.create()
				.delete();
	}

	public Set<PurchaseCandidateReminder> retrieveReminders()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_Processed, false) // not processed
				.addNotNull(I_C_PurchaseCandidate.COLUMN_Vendor_ID)
				.addNotNull(I_C_PurchaseCandidate.COLUMN_ReminderDate)
				.create()
				.listDistinct(I_C_PurchaseCandidate.COLUMNNAME_Vendor_ID, I_C_PurchaseCandidate.COLUMNNAME_ReminderDate)
				.stream()
				.map(map -> PurchaseCandidateReminder.builder()
						.vendorBPartnerId(BPartnerId.ofRepoId(NumberUtils.asInt(map.get(I_C_PurchaseCandidate.COLUMNNAME_Vendor_ID), -1)))
						.notificationTime(TimeUtil.asLocalDateTime(map.get(I_C_PurchaseCandidate.COLUMNNAME_ReminderDate)))
						.build())
				.collect(ImmutableSet.toImmutableSet());
	}

	public static PurchaseCandidateReminder toPurchaseCandidateReminderOrNull(final I_C_PurchaseCandidate record)
	{
		final BPartnerId vendorBPartnerId = BPartnerId.ofRepoIdOrNull(record.getVendor_ID());
		if (vendorBPartnerId == null)
		{
			return null;
		}

		final LocalDateTime reminderDate = TimeUtil.asLocalDateTime(record.getReminderDate());
		if (reminderDate == null)
		{
			return null;
		}

		return PurchaseCandidateReminder.builder()
				.vendorBPartnerId(vendorBPartnerId)
				.notificationTime(reminderDate)
				.build();
	}
}
