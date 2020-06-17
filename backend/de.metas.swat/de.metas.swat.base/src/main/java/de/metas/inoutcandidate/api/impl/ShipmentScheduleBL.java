package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.createOld;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.ASICopy;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_C_OrderLine;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.freighcost.FreightCostRule;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequest;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequestsList;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.lang.SOTrx;
import de.metas.lock.api.ILockManager;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageEngineService;
import de.metas.storage.IStorageQuery;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * This service computes the quantities to be shipped to customers for a list of {@link I_C_OrderLine}s and their respective {@link I_M_ShipmentSchedule}s.
 *
 * @see IShipmentSchedulePA
 * @see OlAndSched
 *
 */
@Service
public class ShipmentScheduleBL implements IShipmentScheduleBL
{
	private static final AdMessageKey MSG_SHIPMENT_SCHEDULE_ALREADY_PROCESSED = AdMessageKey.of("ShipmentScheduleAlreadyProcessed");

	private static final String SYS_Config_M_ShipmentSchedule_Close_PartiallyShipped = "M_ShipmentSchedule_Close_PartiallyShipped";

	// services
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleBL.class);
	private final ThreadLocal<Boolean> postponeMissingSchedsCreationUntilClose = ThreadLocal.withInitial(() -> false);

	@Override
	public boolean allMissingSchedsWillBeCreatedLater()
	{
		return postponeMissingSchedsCreationUntilClose.get();
	}

	@Override
	public IAutoCloseable postponeMissingSchedsCreationUntilClose()
	{
		if (allMissingSchedsWillBeCreatedLater())
		{
			return NullAutoCloseable.instance; // we were already called;
		}

		postponeMissingSchedsCreationUntilClose.set(true);

		final IAutoCloseable onCloseCreateMissingScheds = //
				() -> {
					postponeMissingSchedsCreationUntilClose.set(false);
					CreateMissingShipmentSchedulesWorkpackageProcessor.scheduleIfNotPostponed(PlainContextAware.newWithThreadInheritedTrx());
				};

		return onCloseCreateMissingScheds;
	}

	@Override
	public void updateHeaderAggregationKey(@NonNull final I_M_ShipmentSchedule sched)
	{
		final ShipmentScheduleHeaderAggregationKeyBuilder shipmentScheduleKeyBuilder = mkShipmentHeaderAggregationKeyBuilder();
		final String headerAggregationKey = shipmentScheduleKeyBuilder.buildKey(sched);
		sched.setHeaderAggregationKey(headerAggregationKey);
	}

	/**
	 * Make sure that all records have a value for BPartnerAddress_Override
	 * <p>
	 * Note: we assume that *if* the value is set, it is as intended by the user
	 */
	@Override
	public void updateBPArtnerAddressOverrideIfNotYetSet(final I_M_ShipmentSchedule sched)
	{
		if (!Check.isEmpty(sched.getBPartnerAddress_Override(), true))
		{
			return;
		}

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final I_C_BPartner bpartner = shipmentScheduleEffectiveValuesBL.getBPartner(sched);
		final I_C_BPartner_Location location = shipmentScheduleEffectiveValuesBL.getBPartnerLocation(sched);
		final I_AD_User user = shipmentScheduleEffectiveValuesBL.getBPartnerContact(sched);

		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
		final String address = bPartnerBL.mkFullAddress(
				bpartner,
				location,
				user,
				InterfaceWrapperHelper.getTrxName(sched));

		sched.setBPartnerAddress_Override(address);
	}

	@Override
	public BigDecimal updateQtyOrdered(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final BigDecimal oldQtyOrdered = shipmentSchedule.getQtyOrdered(); // going to return it in the end

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final BigDecimal newQtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(shipmentSchedule);

		shipmentSchedule.setQtyOrdered(newQtyOrdered);

		return oldQtyOrdered;
	}

	@Override
	public I_C_UOM getUomOfProduct(@NonNull final I_M_ShipmentSchedule sched)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		return productBL.getStockUOM(sched.getM_Product_ID());
	}

	@Override
	public UomId getUomIdOfProduct(@NonNull final I_M_ShipmentSchedule sched)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		return productBL.getStockUOMId(sched.getM_Product_ID());
	}

	@Override
	public boolean isSchedAllowsConsolidate(final I_M_ShipmentSchedule sched)
	{
		// task 08756: we don't really care for the ol's partner, but for the partner who will actually receive the shipment.
		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final boolean bpAllowsConsolidate = bPartnerBL.isAllowConsolidateInOutEffective(shipmentScheduleEffectiveBL.getBPartner(sched), SOTrx.SALES);
		if (!bpAllowsConsolidate)
		{
			logger.debug("According to the effective C_BPartner of shipment candidate '" + sched + "', consolidation into one shipment is not allowed");
			return false;
		}

		return !isConsolidateVetoedByOrderOfSched(sched);
	}

	@VisibleForTesting
	boolean isConsolidateVetoedByOrderOfSched(final I_M_ShipmentSchedule sched)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(sched.getC_Order_ID());
		if (orderId == null)
		{
			return false;
		}

		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final I_C_Order order = orderBL.getById(orderId);
		if (orderBL.isPrepay(order))
		{
			logger.debug("Because '{}' is a prepay order, consolidation into one shipment is not allowed", order);
			return true;
		}

		final boolean isCustomFreightCostRule = isCustomFreightCostRule(order);
		if (isCustomFreightCostRule)
		{
			logger.debug("Because '{}' has not the standard freight cost rule,  consolidation into one shipment is not allowed", order);
			return true;
		}

		return false;
	}

	private boolean isCustomFreightCostRule(final I_C_Order order)
	{
		final FreightCostRule freightCostRule = FreightCostRule.ofCode(order.getFreightCostRule());
		return FreightCostRule.FixPrice.equals(freightCostRule)
		// || FreightCostRule.FreightIncluded.equals(freightCostRule) // 07973: included freight cost rule shall no longer be considered "custom"
		;
	}

	@Override
	public ShipmentScheduleHeaderAggregationKeyBuilder mkShipmentHeaderAggregationKeyBuilder()
	{
		return new ShipmentScheduleHeaderAggregationKeyBuilder();
	}

	@Override
	public void closeShipmentSchedule(@NonNull final I_M_ShipmentSchedule scheduleRecord)
	{
		scheduleRecord.setIsClosed(true);
		save(scheduleRecord);
	}

	@Override
	public void openShipmentSchedule(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		Check.errorUnless(shipmentScheduleRecord.isClosed(), "The given shipmentSchedule is not closed; shipmentSchedule={}", shipmentScheduleRecord);

		shipmentScheduleRecord.setIsClosed(false);

		Services.get(IShipmentScheduleHandlerBL.class).updateShipmentScheduleFromReferencedRecord(shipmentScheduleRecord);
		updateQtyOrdered(shipmentScheduleRecord);

		save(shipmentScheduleRecord);
	}

	@Override
	public boolean isJustOpened(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final boolean isClosed = shipmentScheduleRecord.isClosed();
		if (isClosed)
		{
			return false;
		}

		final boolean wasClosed = createOld(shipmentScheduleRecord, I_M_ShipmentSchedule.class).isClosed();
		if (!wasClosed)
		{
			return false;
		}

		return true; // was closed, but is now open
	}

	@Override
	public IStorageQuery createStorageQuery(
			@NonNull final I_M_ShipmentSchedule sched,
			final boolean considerAttributes)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

		// Create storage query
		final BPartnerId bpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(sched);

		final WarehouseId warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(sched);
		final Set<WarehouseId> warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(warehouseId);

		final IStorageEngineService storageEngineProvider = Services.get(IStorageEngineService.class);
		final IStorageEngine storageEngine = storageEngineProvider.getStorageEngine();

		final IStorageQuery storageQuery = storageEngine
				.newStorageQuery()
				.addBPartnerId(bpartnerId)
				.addWarehouseIds(warehouseIds)
				.addProductId(ProductId.ofRepoId(sched.getM_Product_ID()));

		// Add query attributes
		if (considerAttributes)
		{
			final I_M_AttributeSetInstance asi = sched.getM_AttributeSetInstance_ID() > 0 ? sched.getM_AttributeSetInstance() : null;
			if (asi != null && asi.getM_AttributeSetInstance_ID() > 0)
			{
				final IAttributeSet attributeSet = storageEngine.getAttributeSet(asi);
				storageQuery.addAttributes(attributeSet);
			}
		}

		if (sched.getC_OrderLine_ID() > 0)
		{
			storageQuery.setExcludeReservedToOtherThan(OrderLineId.ofRepoId(sched.getC_OrderLine_ID()));
		}
		else
		{
			storageQuery.setExcludeReserved();
		}
		return storageQuery;
	}

	@Override
	public Quantity getQtyToDeliver(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final BigDecimal qtyToDeliverBD = shipmentScheduleEffectiveBL.getQtyToDeliverBD(shipmentScheduleRecord);
		final I_C_UOM uom = getUomOfProduct(shipmentScheduleRecord);
		return Quantity.of(qtyToDeliverBD, uom);
	}

	@Override
	public Optional<Quantity> getCatchQtyOverride(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final boolean hasCatchOverrideQty = !isNull(shipmentScheduleRecord, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliverCatch_Override);
		final boolean hasCatchUOM = shipmentScheduleRecord.getCatch_UOM_ID() > 0;

		if (!hasCatchUOM)
		{
			return Optional.empty();
		}
		if (!hasCatchOverrideQty)
		{
			return Optional.empty();
		}

		final Quantity result = Quantitys.create(
				shipmentScheduleRecord.getQtyToDeliverCatch_Override(),
				UomId.ofRepoId(shipmentScheduleRecord.getCatch_UOM_ID()));
		return Optional.of(result);
	}

	@Override
	public void resetCatchQtyOverride(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		shipmentScheduleRecord.setQtyToDeliverCatch_Override(null);
		saveRecord(shipmentScheduleRecord);
	}

	@Override
	public void updateCatchUoms(@NonNull final ProductId productId, long delayMs)
	{
		if (delayMs < 0)
		{
			return; // doing nothing
		}

		// lambda doesn't work; see https://stackoverflow.com/questions/37970682/passing-lambda-to-a-timer-instead-of-timertask
		final TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				updateCatchUoms(productId);
			}
		};

		if (delayMs <= 0)
		{
			task.run(); // run directly
			return;
		}

		logger.info("Going to update shipment schedules for M_Product_ID={} in {}ms", productId.getRepoId(), delayMs);

		final String timerName = ShipmentScheduleBL.class.getSimpleName() + "-updateCatchUoms-productId=" + productId.getRepoId();
		new Timer(timerName).schedule(task, delayMs);
	}

	private void updateCatchUoms(@NonNull final ProductId productId)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final Integer catchUomRepoId = Services.get(IProductBL.class)
				.getCatchUOMId(productId)
				.map(UomId::getRepoId)
				.orElse(null);

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryUpdater<I_M_ShipmentSchedule> queryUpdater = queryBL
				.createCompositeQueryUpdater(I_M_ShipmentSchedule.class)
				.addSetColumnValue(I_M_ShipmentSchedule.COLUMNNAME_Catch_UOM_ID, catchUomRepoId);

		final ILockManager lockManager = Services.get(ILockManager.class);

		final int count = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID, productId)
				.addNotEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Catch_UOM_ID, catchUomRepoId)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMN_Processed, false)
				.filter(lockManager.getNotLockedFilter(I_M_ShipmentSchedule.class))
				.create()
				.update(queryUpdater);

		final long durationSecs = stopwatch.stop().elapsed(TimeUnit.SECONDS);
		logger.info("Updated {} shipment schedules for M_Product_ID={} in {}seconds", count, productId.getRepoId(), durationSecs);
	}

	@Override
	public I_M_ShipmentSchedule getById(@NonNull final ShipmentScheduleId id)
	{
		return Services.get(IShipmentSchedulePA.class).getById(id);
	}

	@Override
	public Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIdsOutOfTrx(final Set<ShipmentScheduleId> ids)
	{
		return Services.get(IShipmentSchedulePA.class).getByIdsOutOfTrx(ids);
	}

	@Override
	public <T extends I_M_ShipmentSchedule> Map<ShipmentScheduleId, T> getByIdsOutOfTrx(
			@NonNull final Set<ShipmentScheduleId> ids,
			@NonNull final Class<T> modelType)
	{
		return Services.get(IShipmentSchedulePA.class).getByIdsOutOfTrx(ids, modelType);
	}

	@Override
	public BPartnerId getBPartnerId(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		return shipmentScheduleEffectiveBL.getBPartnerId(schedule);
	}

	@Override
	public WarehouseId getWarehouseId(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		return shipmentScheduleEffectiveBL.getWarehouseId(schedule);
	}

	@Override
	public ZonedDateTime getPreparationDate(I_M_ShipmentSchedule schedule)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		return shipmentScheduleEffectiveBL.getPreparationDate(schedule);
	}

	@Override
	public ShipmentAllocationBestBeforePolicy getBestBeforePolicy(@NonNull final ShipmentScheduleId id)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getById(id);
		final Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy = ShipmentAllocationBestBeforePolicy.optionalOfNullableCode(shipmentSchedule.getShipmentAllocation_BestBefore_Policy());
		if (bestBeforePolicy.isPresent())
		{
			return bestBeforePolicy.get();
		}

		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final BPartnerId bpartnerId = getBPartnerId(shipmentSchedule);
		return bpartnerBL.getBestBeforePolicy(bpartnerId);
	}

	@Override
	public void applyUserChangesInTrx(@NonNull final ShipmentScheduleUserChangeRequestsList userChanges)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInThreadInheritedTrx(() -> applyUserChangesInTrx0(userChanges));
	}

	private void applyUserChangesInTrx0(@NonNull ShipmentScheduleUserChangeRequestsList userChanges)
	{
		final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);

		final Set<ShipmentScheduleId> shipmentScheduleIds = userChanges.getShipmentScheduleIds();
		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> recordsById = shipmentSchedulesRepo.getByIds(shipmentScheduleIds);

		for (final ShipmentScheduleId shipmentScheduleId : shipmentScheduleIds)
		{
			try (final MDCCloseable shipmentScheduleMDC = TableRecordMDC.putTableRecordReference(I_M_ShipmentSchedule.Table_Name, shipmentScheduleId))
			{

				final ShipmentScheduleUserChangeRequest userChange = userChanges.getByShipmentScheduleId(shipmentScheduleId);
				final I_M_ShipmentSchedule record = recordsById.get(shipmentScheduleId);
				if (record == null)
				{
					// shall not happen
					logger.warn("No record found for {}. Skip applying user changes: {}", shipmentScheduleId, userChange);
					continue;
				}
				if (record.isProcessed())
				{
					// shall not happen
					logger.warn("Record already processed {}. Skip applying user changes: {}", shipmentScheduleId, userChange);
					continue;
				}

				updateRecord(record, userChange);
				shipmentSchedulesRepo.save(record);
			}
		}
	}

	private static void updateRecord(
			@NonNull final I_M_ShipmentSchedule record,
			@NonNull final ShipmentScheduleUserChangeRequest from)
	{
		if (from.getQtyToDeliverStockOverride() != null)
		{
			record.setQtyToDeliver_Override(from.getQtyToDeliverStockOverride());
		}

		if (from.getQtyToDeliverCatchOverride() != null)
		{
			record.setQtyToDeliverCatch_Override(from.getQtyToDeliverCatchOverride());
		}

		if (from.getAsiId() != null)
		{
			record.setM_AttributeSetInstance_ID(from.getAsiId().getRepoId());
		}

		if (from.getBestBeforeDate() != null)
		{
			final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

			final AttributeSetInstanceId oldAsiId = AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID());
			final AttributeSetInstanceId asiId;
			if (oldAsiId.isNone())
			{
				final I_M_AttributeSetInstance asiNew = attributeSetInstanceBL.createASI(ProductId.ofRepoId(record.getM_Product_ID()));
				asiId = AttributeSetInstanceId.ofRepoId(asiNew.getM_AttributeSetInstance_ID());
			}
			else
			{
				final I_M_AttributeSetInstance asiCopy = ASICopy.newInstance(oldAsiId).copy();
				asiId = AttributeSetInstanceId.ofRepoId(asiCopy.getM_AttributeSetInstance_ID());
			}
			record.setM_AttributeSetInstance_ID(asiId.getRepoId());
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, AttributeConstants.ATTR_BestBeforeDate, from.getBestBeforeDate());
		}
	}

	@Override
	public boolean isCatchWeight(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		final int orderLineId = shipmentScheduleRecord.getC_OrderLine_ID();
		if (orderLineId <= 0)
		{
			// returning true to keep the old behavior for shipment schedules that are not for sales orders.
			return true;
		}

		final I_C_OrderLine orderLineRecord = orderDAO.getOrderLineById(orderLineId);

		final String invoicableQtyBasedOn = orderLineRecord.getInvoicableQtyBasedOn();

		return X_C_OrderLine.INVOICABLEQTYBASEDON_CatchWeight.equals(invoicableQtyBasedOn);
	}

	@Override
	public void closeShipmentSchedulesFor(@NonNull final ImmutableList<TableRecordReference> recordRefs)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final ImmutableList<I_M_ShipmentSchedule> records = shipmentSchedulePA.getByReferences(recordRefs);
		for (final I_M_ShipmentSchedule record : records)
		{
			if (record.isClosed())
			{
				continue;
			}
			if (record.isProcessed())
			{
				throw new AdempiereException(
						Services.get(IMsgBL.class)
								.getTranslatableMsgText(MSG_SHIPMENT_SCHEDULE_ALREADY_PROCESSED, record.getM_ShipmentSchedule_ID()))
										.markAsUserValidationError();
			}
			closeShipmentSchedule(record);
		}
	}

	@Override
	public void openShipmentSchedulesFor(@NonNull final ImmutableList<TableRecordReference> recordRefs)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final ImmutableList<I_M_ShipmentSchedule> records = shipmentSchedulePA.getByReferences(recordRefs);
		for (final I_M_ShipmentSchedule record : records)
		{
			if (record.isClosed())
			{
				openShipmentSchedule(record);
			}
		}
	}

	@Override
	public IAttributeSetInstanceAware toAttributeSetInstanceAware(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return InterfaceWrapperHelper.create(shipmentSchedule, IAttributeSetInstanceAware.class);
	}

	@Override
	public void closePartiallyShipped_ShipmentSchedules(@NonNull final I_M_InOut inoutRecord)
	{
		if (!inoutRecord.isSOTrx())
		{
			logger.debug("M_InOut.isSOTrx=false; => not closing any shipment schedules");
			return;
		}

		final IInOutBL inOutBL = Services.get(IInOutBL.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		final IShipmentSchedulePA shipmentScheduleDAO = Services.get(IShipmentSchedulePA.class);

		if (inOutBL.isReversal(inoutRecord))
		{
			logger.debug("M_InOut is a reversal; => not closing any shipment schedules");
			return;
		}
		if (!isCloseIfPartiallyShipped(OrgId.ofRepoId(inoutRecord.getAD_Org_ID())))
		{
			logger.debug("isCloseIfPartiallyShipped=false for AD_Org_ID={}; => not closing any shipment schedules\"", inoutRecord.getAD_Org_ID());
			return;
		}


		for (final I_M_InOutLine iolrecord : inOutDAO.retrieveLines(inoutRecord))
		{
			try (final MDCCloseable iolrecordMDC = TableRecordMDC.putTableRecordReference(iolrecord))
			{
				for (final I_M_ShipmentSchedule shipmentScheduleRecord : shipmentScheduleDAO.retrieveForInOutLine(iolrecord))
				{
					try (final MDCCloseable candidateMDC = TableRecordMDC.putTableRecordReference(shipmentScheduleRecord))
					{
						if (iolrecord.getMovementQty().compareTo(shipmentScheduleRecord.getQtyOrdered()) < 0)
						{
							logger.debug("inoutLine.MovementQty={} is < shipmentSchedule.qtyOrdered={}; -> closing shipment schedule",
									iolrecord.getMovementQty(), shipmentScheduleRecord.getQtyOrdered());
							closeShipmentSchedule(shipmentScheduleRecord);
						}
						else
						{
							logger.debug("inoutLine.MovementQty={} is >= shipmentSchedule.qtyOrdered={}; -> not closing shipment schedule",
									iolrecord.getMovementQty(), shipmentScheduleRecord.getQtyOrdered());
						}
					}
				}
			}
		}
	}

	private boolean isCloseIfPartiallyShipped(@NonNull final OrgId orgId)
	{
		final boolean isCloseIfPartiallyInvoiced = Services.get(ISysConfigBL.class)
				.getBooleanValue(SYS_Config_M_ShipmentSchedule_Close_PartiallyShipped, false, ClientId.METASFRESH.getRepoId(), orgId.getRepoId());

		return isCloseIfPartiallyInvoiced;
	}
}
