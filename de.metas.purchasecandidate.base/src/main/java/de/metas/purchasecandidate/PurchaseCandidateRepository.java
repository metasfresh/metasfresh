package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.CalendarId;
import de.metas.calendar.ICalendarDAO;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemRepository;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.calendar.IBusinessDayMatcher;
import de.metas.util.calendar.NullBusinessDayMatcher;
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
	private final PurchaseItemRepository purchaseItemRepository;
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final BPPurchaseScheduleService bpPurchaseScheduleService;

	private final transient IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	private final ReferenceGenerator referenceGenerator;

	private final LockOwner lockOwner = LockOwner.newOwner(PurchaseCandidateRepository.class.getSimpleName());

	public PurchaseCandidateRepository(
			@NonNull final PurchaseItemRepository purchaseItemRepository,
			@NonNull final ReferenceGenerator referenceGenerator,
			@NonNull final BPPurchaseScheduleService bpPurchaseScheduleService)
	{
		this.purchaseItemRepository = purchaseItemRepository;
		this.referenceGenerator = referenceGenerator;
		this.bpPurchaseScheduleService = bpPurchaseScheduleService;
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

	public List<PurchaseCandidateId> getAllIdsBySalesOrderLineId(
			@NonNull final OrderLineId salesOrderLineIds)
	{
		return queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID, salesOrderLineIds)
				.create()
				.listIds()
				.stream()
				.map(PurchaseCandidateId::ofRepoId)
				.collect(ImmutableList.toImmutableList());
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

	public List<PurchaseCandidate> getAllByIds(@NonNull final Collection<PurchaseCandidateId> purchaseCandidateIds)
	{
		return streamAllByIds(purchaseCandidateIds).collect(ImmutableList.toImmutableList());
	}

	public Stream<PurchaseCandidate> streamAllByIds(@NonNull final Collection<PurchaseCandidateId> purchaseCandidateIds)
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
			}
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
	private I_C_PurchaseCandidate createOrUpdateRecord(
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

		final OrderAndLineId salesOrderAndLineId = purchaseCandidate.getSalesOrderAndLineIdOrNull();
		record.setC_OrderSO_ID(OrderAndLineId.getOrderRepoIdOr(salesOrderAndLineId, -1));
		record.setC_OrderLineSO_ID(OrderAndLineId.getOrderLineRepoIdOr(salesOrderAndLineId, -1));

		record.setAD_Org_ID(purchaseCandidate.getOrgId().getRepoId());
		record.setM_WarehousePO_ID(purchaseCandidate.getWarehouseId().getRepoId());
		record.setM_Product_ID(purchaseCandidate.getProductId().getRepoId());

		final Quantity qtyToPurchase = purchaseCandidate.getQtyToPurchase();
		record.setC_UOM_ID(qtyToPurchase.getUomId().getRepoId());
		record.setQtyToPurchase(qtyToPurchase.toBigDecimal());

		final ZonedDateTime purchaseDateOrdered = calculatePurchaseDateOrdered(purchaseCandidate);
		record.setPurchaseDatePromised(TimeUtil.asTimestamp(purchaseCandidate.getPurchaseDatePromised()));
		record.setPurchaseDateOrdered(TimeUtil.asTimestamp(purchaseDateOrdered));
		record.setReminderDate(TimeUtil.asTimestamp(calculateReminderDate(purchaseDateOrdered, purchaseCandidate)));

		final BPartnerId vendorId = purchaseCandidate.getVendorId();
		record.setVendor_ID(vendorId != null ? vendorId.getRepoId() : -1);

		record.setIsAggregatePO(purchaseCandidate.isAggregatePOs());

		updateRecordFromPurchaseProfitInfo(record, purchaseCandidate.getProfitInfoOrNull());

		record.setIsPrepared(purchaseCandidate.isPrepared());
		record.setProcessed(purchaseCandidate.isProcessed());

		saveRecord(record);
		purchaseCandidate.markSaved(PurchaseCandidateId.ofRepoId(record.getC_PurchaseCandidate_ID()));

		return record;
	}

	private ZonedDateTime calculatePurchaseDateOrdered(final PurchaseCandidate candidate)
	{
		final ZonedDateTime purchaseDatePromised = candidate.getPurchaseDatePromised();
		final BPartnerId vendorId = candidate.getVendorId();
		final BPPurchaseSchedule bpPurchaseSchedule = bpPurchaseScheduleService
				.getBPPurchaseSchedule(vendorId, purchaseDatePromised.toLocalDate())
				.orElse(null);
		if (bpPurchaseSchedule == null)
		{
			return purchaseDatePromised;
		}

		final IBusinessDayMatcher calendarNonBusinessDays;
		final CalendarId nonBusinessDaysCalendarId = bpPurchaseSchedule.getNonBusinessDaysCalendarId();
		if (nonBusinessDaysCalendarId != null)
		{
			final ICalendarDAO calendarsRepo = Services.get(ICalendarDAO.class);
			calendarNonBusinessDays = calendarsRepo.getCalendarNonBusinessDays(nonBusinessDaysCalendarId);
		}
		else
		{
			calendarNonBusinessDays = NullBusinessDayMatcher.instance;
		}

		final Duration leadTimeOffset = bpPurchaseSchedule.getLeadTimeOffset();
		return calendarNonBusinessDays.getPreviousBusinessDay(purchaseDatePromised, (int)leadTimeOffset.toDays());
	}

	private static ZonedDateTime calculateReminderDate(final ZonedDateTime purchaseDateOrdered, final PurchaseCandidate candidate)
	{
		final Duration reminderTime = candidate.getReminderTime();
		if (reminderTime == null || purchaseDateOrdered == null)
		{
			return null;
		}

		return purchaseDateOrdered.minus(reminderTime);
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

		final ZonedDateTime purchaseDatePromised = TimeUtil.asZonedDateTime(record.getPurchaseDatePromised());
		final LocalDateTime dateReminder = TimeUtil.asLocalDateTime(record.getReminderDate());
		final Duration reminderTime = purchaseDatePromised != null && dateReminder != null ? Duration.between(purchaseDatePromised, dateReminder) : null;

		final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIdsOrNull(record.getC_OrderSO_ID(), record.getC_OrderLineSO_ID());
		final Quantity qtyToPurchase = Quantity.of(record.getQtyToPurchase(), uomsRepo.getById(record.getC_UOM_ID()));

		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.locked(locked)
				.id(PurchaseCandidateId.ofRepoIdOrNull(record.getC_PurchaseCandidate_ID()))
				.groupReference(DemandGroupReference.ofReference(record.getDemandReference()))
				.salesOrderAndLineIdOrNull(salesOrderAndLineId)
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
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(record.getM_AttributeSetInstance_ID()))
				.vendorProductNo(productsRepo.retrieveProductValueByProductId(ProductId.ofRepoId(record.getM_Product_ID())))
				//
				.qtyToPurchase(qtyToPurchase)
				//
				.profitInfoOrNull(toPurchaseProfitInfo(record))
				//
				.aggregatePOs(record.isAggregatePO())
				//
				.build();

		purchaseItemRepository.loadPurchaseItems(purchaseCandidate);

		return purchaseCandidate;
	}

	private PurchaseProfitInfo toPurchaseProfitInfo(final I_C_PurchaseCandidate purchaseCandidateRecord)
	{
		final int currencyRepoId = purchaseCandidateRecord.getC_Currency_ID();
		if (currencyRepoId <= 0)
		{
			return null;
		}

		final CurrencyId currencyId = CurrencyId.ofRepoId(currencyRepoId);

		return PurchaseProfitInfo.builder()
				.profitSalesPriceActual(Money.of(purchaseCandidateRecord.getProfitSalesPriceActual(), currencyId))
				.profitPurchasePriceActual(Money.of(purchaseCandidateRecord.getProfitPurchasePriceActual(), currencyId))
				.purchasePriceActual(Money.of(purchaseCandidateRecord.getPurchasePriceActual(), currencyId))
				.build();
	}

	private static void updateRecordFromPurchaseProfitInfo(
			@NonNull final I_C_PurchaseCandidate record,
			@Nullable final PurchaseProfitInfo profitInfo)
	{
		if (profitInfo != null)
		{
			record.setProfitSalesPriceActual(profitInfo.getProfitSalesPriceActualAsBigDecimalOr(null));
			record.setProfitPurchasePriceActual(profitInfo.getProfitPurchasePriceActualAsBigDecimalOr(null));
			record.setPurchasePriceActual(profitInfo.getPurchasePriceActualAsBigDecimalOr(null));
			record.setC_Currency_ID(profitInfo.getCommonCurrencyRepoIdOr(-1));
		}
		else
		{
			record.setProfitSalesPriceActual(null);
			record.setProfitPurchasePriceActual(null);
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
						.notificationTime(TimeUtil.asZonedDateTime(map.get(I_C_PurchaseCandidate.COLUMNNAME_ReminderDate)))
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

		final ZonedDateTime reminderDate = TimeUtil.asZonedDateTime(record.getReminderDate());
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
