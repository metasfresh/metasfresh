package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.OrgId;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.CCache;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;

import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.money.Currency;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemRepository;
import de.metas.quantity.Quantity;
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
	// services
	private final CurrencyRepository currencyRepository;
	private final PurchaseItemRepository purchaseItemRepository;
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final transient IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	private final ReferenceGenerator referenceGenerator;

	private final LockOwner lockOwner = LockOwner.newOwner(PurchaseCandidateRepository.class.getSimpleName());

	public PurchaseCandidateRepository(
			@NonNull final PurchaseItemRepository purchaseItemRepository,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final ReferenceGenerator referenceGenerator)
	{
		this.purchaseItemRepository = purchaseItemRepository;
		this.currencyRepository = currencyRepository;
		this.referenceGenerator = referenceGenerator;
	}

	public ImmutableMultimap<DemandGroupReference, PurchaseCandidate> getAllByDemandIds(
			@NonNull final Collection<DemandGroupReference> purchaseDemandIds)
	{
		if (purchaseDemandIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		final ImmutableList<String> demandReferences = purchaseDemandIds
				.stream()
				.map(DemandGroupReference::getDemandReference)
				.collect(ImmutableList.toImmutableList());

		final ImmutableMultimap.Builder<DemandGroupReference, PurchaseCandidate> result = ImmutableMultimap.builder();

		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_DemandReference, demandReferences)
				.create()
				.list()
				.stream()
				.map(this::toPurchaseCandidate)
				.forEach(purchaseCandidate -> result.put(purchaseCandidate.getGroupReference(), purchaseCandidate));

		return result.build();
	}

	public ImmutableListMultimap<DemandGroupReference, PurchaseCandidate> getAllBySalesOrderLineIds(@NonNull final Collection<Integer> salesOrderLineIds)
	{
		if (salesOrderLineIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID, salesOrderLineIds)
				.create()
				.stream(I_C_PurchaseCandidate.class)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> DemandGroupReference.ofReference(record.getDemandReference()), // keyFunction
						record -> toPurchaseCandidate(record)));// valueFunction

	}

	public Stream<PurchaseCandidate> streamAllByIds(final Collection<PurchaseCandidateId> purchaseCandidateIds)
	{
		if (purchaseCandidateIds.isEmpty())
		{
			return Stream.empty();
		}

		return queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_C_PurchaseCandidate_ID, PurchaseCandidateId.toIntSet(purchaseCandidateIds))
				.create()
				.stream(I_C_PurchaseCandidate.class)
				.map(this::toPurchaseCandidate);
	}

	public List<PurchaseCandidate> getAllByIds(final Collection<PurchaseCandidateId> purchaseCandidateIds)
	{
		return streamAllByIds(purchaseCandidateIds).collect(ImmutableList.toImmutableList());
	}

	public Set<PurchaseCandidateId> retrieveManualPurchaseCandidateIdsBySalesOrderIdFilterQtyToPurchase(@NonNull final OrderId salesOrderId)
	{
		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMN_C_Order_ID, salesOrderId.getRepoId())
				.andCollectChildren(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID)
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_IsAggregatePO, false) // manual
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_Processed, false)
				.addCompareFilter(I_C_PurchaseCandidate.COLUMN_QtyToPurchase, Operator.GREATER, BigDecimal.ZERO)
				.create()
				.listIds()
				.stream()
				.map(PurchaseCandidateId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
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

		final Set<PurchaseCandidateId> existingPurchaseCandidateIds = purchaseCandidatesToSave.stream()
				.map(PurchaseCandidate::getId)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());

		final Map<PurchaseCandidateId, I_C_PurchaseCandidate> existingRecordsById;
		if (!existingPurchaseCandidateIds.isEmpty())
		{
			existingRecordsById = queryBL
					.createQueryBuilder(I_C_PurchaseCandidate.class)
					.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_C_PurchaseCandidate_ID, PurchaseCandidateId.toIntSet(existingPurchaseCandidateIds))
					.create()
					.map(I_C_PurchaseCandidate.class, record -> PurchaseCandidateId.ofRepoId(record.getC_PurchaseCandidate_ID()));
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
				final PurchaseCandidateId id = purchaseCandidate.getId();
				final I_C_PurchaseCandidate existingRecord = id != null ? existingRecordsById.get(id) : null;
				createOrUpdateRecord(purchaseCandidate, existingRecord);

				purchaseItemRepository.saveAll(purchaseCandidate.getPurchaseOrderItems());
				purchaseItemRepository.saveAll(purchaseCandidate.getPurchaseErrorItems());
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

	public PurchaseCandidateId save(@NonNull final PurchaseCandidate purchaseCandidate)
	{
		final I_C_PurchaseCandidate purchaseCandidateRecord = purchaseCandidate.getId() == null
				? null
				: load(purchaseCandidate.getId().getRepoId(), I_C_PurchaseCandidate.class);

		final I_C_PurchaseCandidate savedPurchaseCandidateRecord = createOrUpdateRecord(purchaseCandidate, purchaseCandidateRecord);
		return PurchaseCandidateId.ofRepoId(savedPurchaseCandidateRecord.getC_PurchaseCandidate_ID());
	}

	private ILockAutoCloseable lockByIds(final Set<PurchaseCandidateId> purchaseCandidateIds)
	{
		final ILockManager lockManager = Services.get(ILockManager.class);
		return lockManager.lock()
				.setOwner(lockOwner)
				.setAutoCleanup(true)
				.addRecordsByModel(TableRecordReference.ofRecordIds(I_C_PurchaseCandidate.Table_Name, PurchaseCandidateId.toIntSet(purchaseCandidateIds)))
				.acquire()
				.asAutoCloseable();
	}

	/**
	 * Note to dev: keep in sync with {@link #toPurchaseCandidate(I_C_PurchaseCandidate)}
	 */
	private final I_C_PurchaseCandidate createOrUpdateRecord(
			final PurchaseCandidate purchaseCandidate,
			final I_C_PurchaseCandidate existingRecord)
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

		final String demandReference;
		if (purchaseCandidate.getGroupReference().isNew())
		{
			demandReference = referenceGenerator.getNextDemandReference();
		}
		else
		{
			demandReference = purchaseCandidate.getGroupReference().getDemandReference();
		}
		record.setDemandReference(demandReference);

		final OrderAndLineId salesOrderAndLineId = purchaseCandidate.getSalesOrderAndLineId();
		record.setC_OrderSO_ID(OrderAndLineId.getOrderRepoIdOr(salesOrderAndLineId, -1));
		record.setC_OrderLineSO_ID(OrderAndLineId.getOrderLineRepoIdOr(salesOrderAndLineId, -1));

		record.setAD_Org_ID(purchaseCandidate.getOrgId().getRepoId());
		record.setM_WarehousePO_ID(purchaseCandidate.getWarehouseId().getRepoId());
		record.setM_Product_ID(purchaseCandidate.getProductId().getRepoId());

		final Quantity qtyToPurchase = purchaseCandidate.getQtyToPurchase();
		record.setC_UOM_ID(qtyToPurchase.getUOMId());
		record.setQtyToPurchase(qtyToPurchase.getAsBigDecimal());

		record.setDateRequired(TimeUtil.asTimestamp(purchaseCandidate.getPurchaseDatePromised()));
		record.setReminderDate(TimeUtil.asTimestamp(purchaseCandidate.getReminderDate()));

		final BPartnerId vendorId = purchaseCandidate.getVendorId();
		record.setVendor_ID(vendorId != null ? vendorId.getRepoId() : -1);

		record.setIsAggregatePO(purchaseCandidate.isAggregatePOs());

		updateRecordFromPurchaseProfitInfo(record, purchaseCandidate.getProfitInfo());

		record.setIsPrepared(purchaseCandidate.isPrepared());
		record.setProcessed(purchaseCandidate.isProcessed());

		InterfaceWrapperHelper.save(record);
		purchaseCandidate.markSaved(PurchaseCandidateId.ofRepoId(record.getC_PurchaseCandidate_ID()));

		return record;
	}

	public PurchaseCandidate getById(@NonNull final PurchaseCandidateId purchaseCandidateId)
	{
		final I_C_PurchaseCandidate record = load(purchaseCandidateId.getRepoId(), I_C_PurchaseCandidate.class);
		Check.assumeNotNull(record, "Unable to load I_C_PurchaseCandidate record for ID={}", purchaseCandidateId);

		return toPurchaseCandidate(record);
	}

	/**
	 * Note to dev: keep in sync with {@link #createOrUpdateRecord(PurchaseCandidate, I_C_PurchaseCandidate)}
	 */
	private PurchaseCandidate toPurchaseCandidate(@NonNull final I_C_PurchaseCandidate record)
	{
		final ILockManager lockManager = Services.get(ILockManager.class);

		final boolean locked = lockManager.isLocked(record);

		final LocalDateTime purchaseDatePromised = TimeUtil.asLocalDateTime(record.getDateRequired());
		final LocalDateTime dateReminder = TimeUtil.asLocalDateTime(record.getReminderDate());
		final Duration reminderTime = purchaseDatePromised != null && dateReminder != null ? Duration.between(purchaseDatePromised, dateReminder) : null;

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIdsOrNull(record.getC_OrderSO_ID(), record.getC_OrderLineSO_ID());
		final Quantity qtyToPurchase = Quantity.of(record.getQtyToPurchase(), uomsRepo.getById(record.getC_UOM_ID()));

		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.locked(locked)
				.id(PurchaseCandidateId.ofRepoIdOrNull(record.getC_PurchaseCandidate_ID()))
				.groupReference(DemandGroupReference.ofReference(record.getDemandReference()))
				.salesOrderAndLineId(salesOrderAndLineId)
				.processed(record.isProcessed())
				//
				.purchaseDatePromised(purchaseDatePromised)
				.reminderTime(reminderTime)
				//
				.vendorId(BPartnerId.ofRepoId(record.getVendor_ID()))
				//
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_WarehousePO_ID()))
				//
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.vendorProductNo(productsRepo.retrieveProductValueByProductId(record.getM_Product_ID()))
				//
				.qtyToPurchase(qtyToPurchase)
				//
				.profitInfo(toPurchaseProfitInfo(record))
				//
				.aggregatePOs(record.isAggregatePO())
				//
				.build();

		purchaseItemRepository.loadPurchaseItems(purchaseCandidate);

		return purchaseCandidate;
	}

	private PurchaseProfitInfo toPurchaseProfitInfo(final I_C_PurchaseCandidate purchaseCandidateRecord)
	{
		final int currencyId = purchaseCandidateRecord.getC_Currency_ID();
		if (currencyId <= 0)
		{
			return null;
		}
		final Currency currency = currencyRepository.getById(currencyId);

		return PurchaseProfitInfo.builder()
				.salesNetPrice(Money.of(purchaseCandidateRecord.getCustomerPriceGrossProfit(), currency))
				.purchaseNetPrice(Money.of(purchaseCandidateRecord.getPriceGrossProfit(), currency))
				.purchaseGrossPrice(Money.of(purchaseCandidateRecord.getPurchasePriceActual(), currency))
				.build();
	}

	private static void updateRecordFromPurchaseProfitInfo(final I_C_PurchaseCandidate record, final PurchaseProfitInfo profitInfo)
	{
		if (profitInfo != null)
		{
			record.setCustomerPriceGrossProfit(profitInfo.getSalesNetPriceAsBigDecimalOr(null));
			record.setPriceGrossProfit(profitInfo.getPurchaseNetPriceAsBigDecimalOr(null));
			record.setPurchasePriceActual(profitInfo.getPurchaseGrossPriceAsBigDecimalOr(null));
			record.setC_Currency_ID(profitInfo.getCommonCurrencyRepoIdOr(-1));
		}
		else
		{
			record.setCustomerPriceGrossProfit(null);
			record.setPriceGrossProfit(null);
			record.setPurchasePriceActual(null);
			record.setC_Currency_ID(-1);
		}
	}

	public void deleteByIds(final Collection<PurchaseCandidateId> purchaseCandidateIds)
	{
		if (purchaseCandidateIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_C_PurchaseCandidate_ID, PurchaseCandidateId.toIntSet(purchaseCandidateIds))
				.create()
				.delete();
	}

	public Set<PurchaseCandidateReminder> retrieveReminders()
	{
		return queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
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

	public PurchaseCandidateId getIdByPurchaseOrderLineIdOrNull(
			@Nullable final OrderLineId purchaseOrderLineId)
	{
		final Integer purchaseCandidateRepoId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PurchaseCandidate_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PurchaseCandidate_Alloc.COLUMN_C_OrderLinePO_ID, purchaseOrderLineId)
				.orderBy(I_C_PurchaseCandidate_Alloc.COLUMN_C_PurchaseCandidate_Alloc_ID) // technically not needed as we have a UC on C_OrderLinePO_ID
				.create()
				.first(I_C_PurchaseCandidate_Alloc.COLUMNNAME_C_PurchaseCandidate_ID, Integer.class);

		return purchaseCandidateRepoId != null
				? PurchaseCandidateId.ofRepoId(purchaseCandidateRepoId)
				: null;
	}

	private static final CCache<PurchaseDemand, List<PurchaseCandidate>> CACHE_PURCHASE_DEMAND_TO_CANDIDATES = CCache.newCache(
			I_C_PurchaseCandidate.Table_Name + "#by#"
					+ I_C_PurchaseCandidate.COLUMN_Processed + "#"
					+ I_C_PurchaseCandidate.COLUMN_AD_Org_ID + "#"
					+ I_C_PurchaseCandidate.COLUMN_M_Product_ID + "#"
					+ I_C_PurchaseCandidate.COLUMN_DateRequired + "#"
					+ I_C_PurchaseCandidate.COLUMN_C_UOM_ID + "#"
					+ I_C_PurchaseCandidate.COLUMN_M_WarehousePO_ID + "#"
					+ I_C_PurchaseCandidate.COLUMN_M_AttributeInstance_ID + "#"
					+ I_C_PurchaseCandidate.COLUMN_C_Currency_ID + "#"
					+ I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID + "#",
			0,
			CCache.EXPIREMINUTES_Never);

	public List<PurchaseCandidate> getForDemand(@NonNull final PurchaseDemand purchaseDemand)
	{
		return CACHE_PURCHASE_DEMAND_TO_CANDIDATES.getOrLoad(purchaseDemand, () -> getForDemand0(purchaseDemand));
	}

	private List<PurchaseCandidate> getForDemand0(@NonNull final PurchaseDemand purchaseDemand)
	{
		final IQueryBuilder<I_C_PurchaseCandidate> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_Processed, false)
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_AD_Org_ID, purchaseDemand.getOrgId())
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_M_Product_ID, purchaseDemand.getProductId())
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_DateRequired, purchaseDemand.getSalesDatePromised()) // this is how it currently is
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_C_UOM_ID, purchaseDemand.getQtyToDeliver().getUOMId());

		if (purchaseDemand.getWarehouseId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_M_WarehousePO_ID, purchaseDemand.getWarehouseId());
		}
		if (purchaseDemand.getAttributeSetInstanceId() != null && purchaseDemand.getAttributeSetInstanceId().getRepoId() > 0)
		{
			queryBuilder.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_M_AttributeInstance_ID, purchaseDemand.getAttributeSetInstanceId());
		}
		if (purchaseDemand.getCurrency() != null)
		{
			queryBuilder.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_C_Currency_ID, purchaseDemand.getCurrency().getId());
		}
		if (purchaseDemand.getSalesOrderAndLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID, purchaseDemand.getSalesOrderAndLineId().getOrderLineId());
		}

		return queryBuilder
				.create()
				.stream()
				.map(this::toPurchaseCandidate)
				.collect(ImmutableList.toImmutableList());
	}
}
