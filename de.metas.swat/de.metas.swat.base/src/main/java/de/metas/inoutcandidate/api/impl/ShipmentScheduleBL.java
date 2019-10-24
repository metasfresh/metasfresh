package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.inout.util.DeliveryGroupCandidate;
import org.adempiere.inout.util.DeliveryGroupCandidateGroupId;
import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate.CompleteStatus;
import org.adempiere.inout.util.ShipmentScheduleAvailableStockDetail;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorage;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorageFactory;
import org.adempiere.inout.util.ShipmentSchedulesDuringUpdate;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.NullAutoCloseable;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.document.engine.DocStatus;
import de.metas.freighcost.FreightCostRule;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentConstraintsBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequest;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequestsList;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLineFactory;
import de.metas.inoutcandidate.spi.impl.CompositeCandidateProcessor;
import de.metas.inoutcandidate.spi.impl.ShipmentScheduleOrderReferenceProvider;
import de.metas.lang.SOTrx;
import de.metas.lock.api.ILockManager;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.order.DeliveryRule;
import de.metas.order.IOrderBL;
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
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;
import de.metas.uom.IUOMConversionBL;
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
	// services
	private final static Logger logger = LogManager.getLogger(ShipmentScheduleBL.class);
	private final ShipmentScheduleQtyOnHandStorageFactory shipmentScheduleQtyOnHandStorageFactory;
	private final ShipmentScheduleReferencedLineFactory shipmentScheduleReferencedLineFactory;

	private static final String DYNATTR_ProcessedByBackgroundProcess = IShipmentScheduleBL.class.getName() + "#ProcessedByBackgroundProcess";

	private final CompositeCandidateProcessor candidateProcessors = new CompositeCandidateProcessor();
	private final ThreadLocal<Boolean> postponeMissingSchedsCreationUntilClose = ThreadLocal.withInitial(() -> false);

	@VisibleForTesting
	public static ShipmentScheduleBL newInstanceForUnitTesting()
	{
		final StockRepository stockRepository = new StockRepository();
		final ShipmentScheduleQtyOnHandStorageFactory shipmentScheduleQtyOnHandStorageFactory = new ShipmentScheduleQtyOnHandStorageFactory(stockRepository);
		final ShipmentScheduleReferencedLineFactory shipmentScheduleReferencedLineFactory = new ShipmentScheduleReferencedLineFactory(Optional.of(ImmutableList.of(new ShipmentScheduleOrderReferenceProvider())));

		return new ShipmentScheduleBL(
				shipmentScheduleQtyOnHandStorageFactory,
				shipmentScheduleReferencedLineFactory);
	}

	public ShipmentScheduleBL(
			@NonNull final ShipmentScheduleQtyOnHandStorageFactory shipmentScheduleQtyOnHandStorageFactory,
			@NonNull final ShipmentScheduleReferencedLineFactory shipmentScheduleReferencedLineFactory)
	{
		this.shipmentScheduleQtyOnHandStorageFactory = shipmentScheduleQtyOnHandStorageFactory;
		this.shipmentScheduleReferencedLineFactory = shipmentScheduleReferencedLineFactory;
	}

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
	public void updateSchedules(
			final Properties ctx,
			final List<OlAndSched> olsAndScheds,
			final String trxName)
	{
		if (olsAndScheds.isEmpty())
		{
			return;
		}

		//
		// Services
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final IShipmentScheduleDeliveryDayBL shipmentScheduleDeliveryDayBL = Services.get(IShipmentScheduleDeliveryDayBL.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		//
		// Briefly update our shipment schedules:
		// * set BPartnerAddress_Override if was not set before
		// * update HeaderAggregationKey
		for (final OlAndSched olAndSched : olsAndScheds)
		{
			final I_M_ShipmentSchedule sched = olAndSched.getSched();

			updateCatchUomId(sched);

			updateWarehouseId(sched);

			updateBPArtnerAddressOverrideIfNotYetSet(sched);

			updateHeaderAggregationKey(sched);

			updateShipmentConstraints(sched);
		}

		final ShipmentSchedulesDuringUpdate firstRun = generate(ctx, olsAndScheds, null, trxName);
		firstRun.updateCompleteStatusAndSetQtyToZeroWhereNeeded();

		final int removeCnt = applyCandidateProcessors(ctx, firstRun, trxName);
		logger.info("{} records were discarded by candidate processors", removeCnt);

		// evaluate the processor's result: lines that have been discarded won't
		// be delivered and won't be validated in the second run.
		for (final DeliveryLineCandidate inOutLine : firstRun.getAllLines())
		{
			if (inOutLine.isDiscarded())
			{
				inOutLine.setQtyToDeliver(BigDecimal.ZERO);
			}
			else
			{
				// remember: 'removeLine' means that a *new* line might be
				// created for the corresponding olAndSched
				inOutLine.removeFromGroup();
				firstRun.removeLine(inOutLine);
			}
		}

		// make the second run
		final IShipmentSchedulesDuringUpdate secondRun = generate(ctx, olsAndScheds, firstRun, trxName);

		// finally update the shipment schedule entries
		for (final OlAndSched olAndSched : olsAndScheds)
		{
			final I_M_ShipmentSchedule sched = olAndSched.getSched();
			final IDeliverRequest deliverRequest = olAndSched.getDeliverRequest();
			final I_C_BPartner bPartner = shipmentScheduleEffectiveBL.getBPartner(sched); // task 08756: we don't really care for the ol's partner, but for the partner who will actually receive the shipment.

			final boolean isBPAllowConsolidateInOut = bpartnerBL.isAllowConsolidateInOutEffective(bPartner, SOTrx.SALES);
			sched.setAllowConsolidateInOut(isBPAllowConsolidateInOut);

			updatePreparationAndDeliveryDate(sched);

			//
			// Delivery Day related info:
			// TODO: invert dependency add make this pluggable from de.metas.tourplanning module
			shipmentScheduleDeliveryDayBL.updateDeliveryDayInfo(sched);

			// task 09358: ol.qtyReserved should be as correct as QtyOrdered and QtyDelivered, but in some cases isn't. this here is a workaround to the problem
			// task 09869: don't rely on ol anyways
			final BigDecimal qtyDelivered = Services.get(IShipmentScheduleAllocDAO.class).retrieveQtyDelivered(sched);
			sched.setQtyDelivered(qtyDelivered);
			sched.setQtyReserved(BigDecimal.ZERO.max(deliverRequest.getQtyOrdered().subtract(sched.getQtyDelivered())));

			updateLineNetAmt(olAndSched);

			ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, secondRun);

			InterfaceWrapperHelper.setDynAttribute(sched, DYNATTR_ProcessedByBackgroundProcess, Boolean.TRUE);

			if (olAndSched.hasSalesOrderLine())
			{
				final DocStatus orderDocStatus = olAndSched.getOrderDocStatus();
				if (!orderDocStatus.isCompletedOrClosedOrReversed() // task 07355: thread closed orders like completed orders
						&& !sched.isProcessed() // task 05206: ts: don't try to delete already processed scheds..it won't work
						&& sched.getQtyDelivered().signum() == 0 // also don't try to delete if there is already a picked or delivered Qty.
						&& sched.getQtyPickList().signum() == 0)
				{
					logger.debug("QtyToDeliver_Override=" + sched.getQtyToDeliver_Override()
							+ "; QtyReserved=" + sched.getQtyReserved()
							+ "; DocStatus=" + orderDocStatus
							+ "; => Deleting " + sched);

					InterfaceWrapperHelper.delete(sched);
					continue;
				}
			}

			updateProcessedFlag(sched);
			if (sched.isProcessed())
			{
				// 04870 : Delivery rule force assumes we deliver full quantity ordered if qtyToDeliver_Override is null.
				// 06019 : check both DeliveryRule, as DeliveryRule_Override
				final boolean deliveryRuleIsForced = DeliveryRule.FORCE.equals(shipmentScheduleEffectiveBL.getDeliveryRule(sched));
				if (deliveryRuleIsForced)
				{
					sched.setQtyToDeliver(BigDecimal.ZERO);
					save(sched);
				}
				else
				{
					Check.errorUnless(sched.getQtyToDeliver().signum() == 0, "{} has QtyToDeliver = {} (should be zero)", sched, sched.getQtyToDeliver());
				}
				continue;
			}

			// task 08694
			// I talked with Mark and he observed that in the wiki-page of 08459 it is specified differently.
			// I will let it here nevertheless, so we can keep track of it's way to work

			final BPartnerId partnerId = BPartnerId.ofRepoId(sched.getC_BPartner_ID());

			// FRESH-334 retrieve the bp product for org or for org 0
			final org.compiere.model.I_M_Product product = loadOutOfTrx(sched.getM_Product_ID(), I_M_Product.class);
			final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());

			final I_C_BPartner_Product bpp = Services.get(IBPartnerProductDAO.class).retrieveBPartnerProductAssociation(ctx, partnerId, ProductId.ofRepoId(sched.getM_Product_ID()), orgId);
			if (bpp == null)
			{
				// in case no dropship bpp entry was found, the schedule shall not be dropship
				sched.setIsDropShip(false);
			}
			else
			{

				final boolean isDropShip = bpp.isDropShip();

				if (isDropShip)
				{
					// if there is bpp that is dropship and has a C_BPartner_Vendor_ID,
					// set the customer's vendor for the given product in the schedule
					sched.setC_BPartner_Vendor_ID(bpp.getC_BPartner_Vendor_ID());
				}

				// set the dropship flag in shipment schedule as it is in the bpp
				sched.setIsDropShip(isDropShip);
			}

			// 08860
			// update preparation date override based on delivery date effective
			// DO this only if the preparationDate_Override was not already set manually or by the process

			if (sched.getDeliveryDate_Override() != null && sched.getPreparationDate_Override() == null)
			{
				final ZonedDateTime deliveryDate = shipmentScheduleEffectiveBL.getDeliveryDate(sched);

				final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);
				final IContextAware contextAwareSched = InterfaceWrapperHelper.getContextAware(sched);
				final BPartnerLocationId bpLocationId = shipmentScheduleEffectiveBL.getBPartnerLocationId(sched);

				final ZonedDateTime calculationTime = TimeUtil.asZonedDateTime(sched.getCreated());
				final ZonedDateTime preparationDate = deliveryDayBL.calculatePreparationDateOrNull(
						contextAwareSched,
						SOTrx.SALES,
						calculationTime,
						deliveryDate,
						bpLocationId);

				// In case the DeliveryDate Override is set, also update the preparationDate override
				sched.setPreparationDate_Override(TimeUtil.asTimestamp(preparationDate));

			}
			save(sched);
		}
	}

	@Override
	public void updateHeaderAggregationKey(@NonNull final I_M_ShipmentSchedule sched)
	{
		final ShipmentScheduleHeaderAggregationKeyBuilder shipmentScheduleKeyBuilder = mkShipmentHeaderAggregationKeyBuilder();
		final String headerAggregationKey = shipmentScheduleKeyBuilder.buildKey(sched);
		sched.setHeaderAggregationKey(headerAggregationKey);
	}

	private void updateShipmentConstraints(final I_M_ShipmentSchedule sched)
	{
		final IShipmentConstraintsBL shipmentConstraintsBL = Services.get(IShipmentConstraintsBL.class);
		final int billBPartnerId = sched.getBill_BPartner_ID();
		final int deliveryStopShipmentConstraintId = shipmentConstraintsBL.getDeliveryStopShipmentConstraintId(billBPartnerId);
		final boolean isDeliveryStop = deliveryStopShipmentConstraintId > 0;
		if (isDeliveryStop)
		{
			sched.setIsDeliveryStop(true);
			sched.setM_Shipment_Constraint_ID(deliveryStopShipmentConstraintId);
		}
		else
		{
			sched.setIsDeliveryStop(false);
			sched.setM_Shipment_Constraint_ID(-1);
		}
	}

	@Override
	public void updatePreparationAndDeliveryDate(@NonNull final I_M_ShipmentSchedule sched)
	{
		final ShipmentScheduleReferencedLine shipmentScheduleOrderDoc = shipmentScheduleReferencedLineFactory.createFor(sched);

		sched.setPreparationDate(TimeUtil.asTimestamp(shipmentScheduleOrderDoc.getPreparationDate()));
		sched.setDeliveryDate(TimeUtil.asTimestamp(shipmentScheduleOrderDoc.getDeliveryDate()));
	}

	private void updateLineNetAmt(final OlAndSched olAndSched)
	{
		final BigDecimal lineNetAmt = computeLineNetAmt(olAndSched);
		olAndSched.setShipmentScheduleLineNetAmt(lineNetAmt);
	}

	/**
	 * Try to get the given <code>ol</code>'s <code>qtyReservedInPriceUOM</code> and update the given <code>sched</code>'s <code>LineNetAmt</code>.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/298
	 * @throws AdempiereException in developer mode, if there the <code>qtyReservedInPriceUOM</code> can't be obtained.
	 */
	private BigDecimal computeLineNetAmt(final OlAndSched olAndSched)
	{
		if (!olAndSched.hasSalesOrderLine())
		{
			return BigDecimal.ZERO;
		}

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		// final de.metas.interfaces.I_C_OrderLine olEx = InterfaceWrapperHelper.create(ol, de.metas.interfaces.I_C_OrderLine.class);
		final BigDecimal qtyReservedInPriceUOM = uomConversionBL.convertFromProductUOM(
				olAndSched.getProductId(),
				olAndSched.getOrderPriceUOM(),
				olAndSched.getOrderQtyReserved());

		// qtyReservedInPriceUOM might be null. in that case, don't fail the whole updating, but set the value to null
		if (qtyReservedInPriceUOM == null)
		{
			final String msg = "IUOMConversionBL.convertFromProductUOM() failed for " + olAndSched + "; \n"
					+ "Therefore we can't set LineNetAmt for M_ShipmentSchedule; \n"
					+ "Note: if this exception was thrown and not just logged, then check for stale M_ShipmentSchedule_Recompute records";
			new AdempiereException(msg).throwIfDeveloperModeOrLogWarningElse(logger);
			return BigDecimal.ONE.negate();
		}
		else
		{
			final BigDecimal orderPriceActual = olAndSched.getOrderPriceActual();
			return qtyReservedInPriceUOM.multiply(orderPriceActual);
		}

	}

	ShipmentSchedulesDuringUpdate generate(
			final Properties ctx,
			final List<OlAndSched> lines,
			final ShipmentSchedulesDuringUpdate firstRun,
			final String trxName)
	{
		// services
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
		final IProductBL productBL = Services.get(IProductBL.class);

		// if firstRun is not null, create a new instance, otherwise use firstRun
		final ShipmentSchedulesDuringUpdate candidates = mkCandidatesToUse(lines, firstRun);

		//
		// Load QtyOnHand in scope for our lines
		// i.e. iterate all lines to cache the required storage info and to subtract the quantities that can't be allocated from the storage allocation.
		final ShipmentScheduleQtyOnHandStorage qtyOnHands = shipmentScheduleQtyOnHandStorageFactory.ofOlAndScheds(lines);

		//
		// Iterate again and:
		// * try to allocate the QtyOnHand
		for (final OlAndSched olAndSched : lines)
		{
			final I_M_ShipmentSchedule sched = olAndSched.getSched();
			final IDeliverRequest deliverRequest = olAndSched.getDeliverRequest();

			final DeliveryRule deliveryRule = shipmentScheduleEffectiveValuesBL.getDeliveryRule(sched);

			final boolean ruleManual = DeliveryRule.MANUAL.equals(deliveryRule);

			final BigDecimal qtyRequired;
			if (olAndSched.getQtyOverride() != null)
			{
				qtyRequired = olAndSched.getQtyOverride().subtract(ShipmentScheduleQtysHelper.mkQtyToDeliverOverrideFulFilled(olAndSched));
			}
			else if (ruleManual)
			{
				// lines with ruleManual need to be scheduled explicitly using QtyToDeliver_Override
				logger.debug("Manual - QtyOverride not set");
				qtyRequired = BigDecimal.ZERO;
			}
			else
			{
				final BigDecimal qtyDelivered = Services.get(IShipmentScheduleAllocDAO.class).retrieveQtyDelivered(sched);
				qtyRequired = deliverRequest.getQtyOrdered().subtract(qtyDelivered);
			}

			//
			// QtyPickList (i.e. qtyUnconfirmedShipments) is the sum of
			// * MovementQtys from all draft shipment lines which are pointing to shipment schedule's order line
			// * QtyPicked from QtyPicked records
			final ProductId productId = ProductId.ofRepoId(sched.getM_Product_ID());

			final BigDecimal qtyPickList;
			{
				// task 08123: we also take those numbers into account that are *not* on an M_InOutLine yet, but are nonetheless picked
				final Quantity stockingQty = shipmentScheduleAllocBL.retrieveQtyPickedAndUnconfirmed(sched);
				qtyPickList = stockingQty.toBigDecimal();

				// Update shipment schedule's fields
				sched.setQtyPickList(qtyPickList);
			}

			final BigDecimal qtyToDeliver = ShipmentScheduleQtysHelper.mkQtyToDeliver(qtyRequired, qtyPickList);

			if (!productBL.isStocked(productId))
			{
				// product not stocked => don't concern ourselves with the storage; just deliver what was ordered
				createLine(ctx, olAndSched, qtyToDeliver,
						null/* storages */,
						true/* force */,
						CompleteStatus.OK, candidates, trxName);
				continue;
			}

			//
			// Get the QtyOnHand storages suitable for our order line
			final List<ShipmentScheduleAvailableStockDetail> storages = qtyOnHands.getStockDetailsMatching(sched);
			final BigDecimal qtyOnHandBeforeAllocation = ShipmentScheduleAvailableStockDetail.calculateQtyOnHandSum(storages);
			sched.setQtyOnHand(qtyOnHandBeforeAllocation);

			final CompleteStatus completeStatus = mkCompleteStatus(qtyToDeliver, qtyOnHandBeforeAllocation);
			final boolean ruleCompleteOrder = DeliveryRule.COMPLETE_ORDER.equals(deliveryRule);
			final boolean ruleAvailable = DeliveryRule.AVAILABILITY.equals(deliveryRule);
			final boolean ruleCompleteLine = DeliveryRule.COMPLETE_LINE.equals(deliveryRule);
			final boolean ruleForce = DeliveryRule.FORCE.equals(deliveryRule);

			if (ruleForce)
			{
				// delivery rule force
				logger.debug("Force - OnHand=" + qtyOnHandBeforeAllocation
						+ " (QtyPickList=" + qtyPickList + "), ToDeliver=" + qtyToDeliver
						+ ", Delivering=" + qtyToDeliver);

				createLine(ctx, olAndSched, qtyToDeliver, storages, ruleForce, completeStatus, candidates, trxName);
			}
			// delivery rules "Complete" and "Availability"
			else if (ruleCompleteOrder || ruleCompleteLine || ruleAvailable || ruleManual)
			{
				if (qtyOnHandBeforeAllocation.signum() > 0 || qtyToDeliver.signum() < 0)
				{
					//
					// if there is anything at all to deliver, create a new
					// inOutLine
					BigDecimal deliver = qtyToDeliver;
					if (deliver.compareTo(qtyOnHandBeforeAllocation) > 0)
					{
						deliver = qtyOnHandBeforeAllocation;
					}

					// we invoke createLine even if ruleComplete is true and
					// fullLine is false, because we want the quantity to be
					// allocated.
					// If the created line will make it into a real shipment will be
					// decided later.
					logger.debug("CompleteLine - OnHand=" + qtyOnHandBeforeAllocation
							+ " (QtyPickList=" + qtyPickList + "), ToDeliver=" + qtyToDeliver
							+ ", FullLine=" + completeStatus);

					createLine(ctx, olAndSched, deliver, storages, ruleForce, completeStatus, candidates, trxName);
				}
				else
				{
					logger.debug("No qtyOnHand to deliver[SKIP] - OnHand=" + qtyOnHandBeforeAllocation
							+ " (QtyPickList=" + qtyPickList + "), ToDeliver=" + qtyToDeliver
							+ ", FullLine=" + completeStatus);
				}
			}
			else
			{
				throw new AdempiereException(
						"Unsupported delivery rule: " + deliveryRule
								+ " - OnHand=" + qtyOnHandBeforeAllocation + " (PickedNotDelivered=" + qtyPickList + "), ToDeliver=" + qtyToDeliver);
			}
		}

		logger.info("Created " + candidates.size() + " shipment candidates");

		return candidates;
	} // generate

	private CompleteStatus mkCompleteStatus(final BigDecimal toDeliver, final BigDecimal onHand)
	{
		CompleteStatus completeStatus = CompleteStatus.OK;
		{
			// fullLine==true means that we can create a shipment line with
			// the full (current!) quantity of the current order line
			final boolean fullLine = onHand.compareTo(toDeliver) >= 0 || toDeliver.signum() <= 0;
			if (!fullLine)
			{
				completeStatus = CompleteStatus.INCOMPLETE_LINE;
			}
		}
		return completeStatus;
	}

	private ShipmentSchedulesDuringUpdate mkCandidatesToUse(
			final List<OlAndSched> lines,
			final ShipmentSchedulesDuringUpdate firstRun)
	{
		if (firstRun != null)
		{
			return firstRun;
		}

		return new ShipmentSchedulesDuringUpdate();

	}

	/**
	 * Creates one or more inOutLines for a given orderLine.
	 *
	 * @param qty the quantity all created inOutLines' qtyEntered will sum up to
	 */
	private void createLine(
			final Properties ctx,
			final OlAndSched olAndSched,
			final BigDecimal qty,
			final List<ShipmentScheduleAvailableStockDetail> storages,
			final boolean force,
			final CompleteStatus completeStatus,
			final ShipmentSchedulesDuringUpdate candidates,
			final String trxName)
	{
		final I_M_ShipmentSchedule sched = olAndSched.getSched();

		if (candidates.getInOutLineFor(sched) != null)
		{
			logger.debug("candidates contains already an inoutLine for M_ShipmentSchedule {}", sched);
			return;
		}

		final DeliveryGroupCandidate groupCandidate = getOrCreateGroupCandidateForShipmentSchedule(sched, candidates);

		if (storages == null || storages.isEmpty())
		{
			final DeliveryLineCandidate deliveryLineCandidate = groupCandidate.createAndAddLineCandidate(sched, completeStatus);
			if (force) // Case: no Quantity on Hand storages and force => no need for allocations etc
			{
				deliveryLineCandidate.setQtyToDeliver(qty);
			}
			candidates.addLine(deliveryLineCandidate);
			return;
		}

		// Shipment Lines (i.e. candidate lines)
		final List<DeliveryLineCandidate> inoutLines = new ArrayList<>();

		//
		// Iterate QtyOnHand storage records and try to allocate on current shipment schedule/order line.
		BigDecimal toDeliverTargetQty = qty; // how much still needs to be delivered; initially it's the whole qty required
		for (int i = 0; i < storages.size(); i++)
		{
			// Stop here is there is nothing remaining to be delivered
			if (toDeliverTargetQty.signum() == 0)
			{
				break;
			}

			final ShipmentScheduleAvailableStockDetail storage = storages.get(i);
			BigDecimal toDeliverCurrentQty = toDeliverTargetQty; // initially try to deliver the entire quantity remaining to be delivered

			//
			// Adjust the quantity that can be delivered from this storage line
			// Check: Not enough On Hand
			final BigDecimal qtyOnHandAvailable = storage.getQtyOnHand();
			if (toDeliverCurrentQty.compareTo(qtyOnHandAvailable) > 0
					&& qtyOnHandAvailable.signum() >= 0)         // positive storage
			{
				if (!force // Adjust to OnHand Qty
						|| force && i + 1 != storages.size())         // if force not on last location
				{
					toDeliverCurrentQty = qtyOnHandAvailable;
				}
			}
			// Skip if we cannot deliver something for current storage line
			if (toDeliverCurrentQty.signum() == 0)
			{
				// zero deliver
				continue;
			}

			// Find existing lineCandidate that was added in a previous iteration
			DeliveryLineCandidate lineCandidate = null;
			for (final DeliveryLineCandidate existingLineCandidate : inoutLines)
			{
				// skip if it's for a different order line
				if (existingLineCandidate.getShipmentScheduleId() != sched.getM_ShipmentSchedule_ID())
				{
					continue;
				}
				lineCandidate = existingLineCandidate;
			}

			// Case: No InOutLine found
			// => create a new InOutLine
			if (lineCandidate == null)
			{
				lineCandidate = groupCandidate.createAndAddLineCandidate(sched, completeStatus);

				lineCandidate.setQtyToDeliver(toDeliverCurrentQty);

				inoutLines.add(lineCandidate);
				candidates.addLine(lineCandidate);
			}
			//
			// Case: existing InOutLine found
			// => adjust the quantity
			else
			{
				final BigDecimal inoutLineQtyOld = lineCandidate.getQtyToDeliver();
				final BigDecimal inoutLineQtyNew = inoutLineQtyOld.add(toDeliverCurrentQty);
				lineCandidate.setQtyToDeliver(inoutLineQtyNew);
			}

			logger.debug("ToDeliver=" + qty + "/" + toDeliverCurrentQty + " - " + lineCandidate);
			toDeliverTargetQty = toDeliverTargetQty.subtract(toDeliverCurrentQty);

			storage.subtractQtyOnHand(toDeliverCurrentQty);
		}    // for each storage record
	}

	private DeliveryGroupCandidate getOrCreateGroupCandidateForShipmentSchedule(
			@NonNull final I_M_ShipmentSchedule sched,
			final IShipmentSchedulesDuringUpdate candidates)
	{
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final I_C_BPartner partner = loadOutOfTrx(sched.getC_BPartner_ID(), I_C_BPartner.class);

		final ShipmentScheduleReferencedLine scheduleSourceDoc = shipmentScheduleReferencedLineFactory.createFor(sched);
		final String bpartnerAddress = sched.getBPartnerAddress_Override();

		DeliveryGroupCandidate candidate = null;

		final WarehouseId warehouseId = Services.get(IShipmentScheduleEffectiveBL.class).getWarehouseId(sched);
		final boolean consolidateAllowed = bpartnerBL.isAllowConsolidateInOutEffective(partner, SOTrx.SALES);
		if (consolidateAllowed)
		{
			// see if there is an existing shipment for this location and shipper
			candidate = candidates.getInOutForShipper(scheduleSourceDoc.getShipperId(), warehouseId, bpartnerAddress);
		}
		else
		{
			// see if there is an existing shipment for this order (or flatrate term)
			candidate = candidates.getInOutForRecordRef(scheduleSourceDoc.getRecordRef(), warehouseId, bpartnerAddress);
		}

		if (candidate == null)
		{
			// create a new Shipment
			candidate = createGroup(scheduleSourceDoc, sched);
			candidates.addGroup(candidate);
		}
		return candidate;
	}

	private int applyCandidateProcessors(final Properties ctx, final IShipmentSchedulesDuringUpdate candidates, final String trxName)
	{
		return candidateProcessors.doUpdateAfterFirstPass(ctx, candidates, trxName);
	}

	@Override
	public void registerCandidateProcessor(final IShipmentSchedulesAfterFirstPassUpdater processor)
	{
		candidateProcessors.addCandidateProcessor(processor);
	}

	/**
	 * 07400 also update the M_Warehouse_ID; an order might have been reactivated and the warehouse might have been changed.
	 *
	 * @param sched
	 */
	private void updateWarehouseId(@NonNull final I_M_ShipmentSchedule sched)
	{
		final WarehouseId warehouseId = shipmentScheduleReferencedLineFactory
				.createFor(sched)
				.getWarehouseId();
		sched.setM_Warehouse_ID(warehouseId.getRepoId());
	}

	private void updateCatchUomId(@NonNull final I_M_ShipmentSchedule sched)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		final Optional<UomId> catchUOMId = productBL.getCatchUOMId(ProductId.ofRepoId(sched.getM_Product_ID()));
		final Integer catchUomRepoId = catchUOMId.map(UomId::getRepoId).orElse(0);

		sched.setCatch_UOM_ID(catchUomRepoId);
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
		final I_AD_User user = shipmentScheduleEffectiveValuesBL.getAD_User(sched);

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

	@VisibleForTesting
	DeliveryGroupCandidate createGroup(
			final ShipmentScheduleReferencedLine scheduleSourceDoc,
			final I_M_ShipmentSchedule sched)
	{

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		return DeliveryGroupCandidate.builder()
				.warehouseId(shipmentScheduleEffectiveBL.getWarehouseId(sched))
				.bPartnerAddress(sched.getBPartnerAddress_Override())
				.groupId(DeliveryGroupCandidateGroupId.of(scheduleSourceDoc.getRecordRef()))
				.shipperId(scheduleSourceDoc.getShipperId())
				.build();
	}

	@Override
	public boolean isChangedByUpdateProcess(final I_M_ShipmentSchedule sched)
	{
		final Boolean isUpdateProcess = InterfaceWrapperHelper.getDynAttribute(sched, DYNATTR_ProcessedByBackgroundProcess);
		return isUpdateProcess == null ? false : isUpdateProcess.booleanValue();
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

	/**
	 *
	 * @param sched
	 * @return
	 * @task 08336
	 */
	@VisibleForTesting
	void updateProcessedFlag(@NonNull final I_M_ShipmentSchedule sched)
	{
		if (sched.isClosed())
		{
			sched.setProcessed(true);
			return;

		}
		final boolean noQtyOverride = sched.getQtyToDeliver_Override().signum() <= 0;
		final boolean noQtyReserved = sched.getQtyReserved().signum() <= 0;

		sched.setProcessed(noQtyOverride && noQtyReserved);
	}

	@Override
	public void closeShipmentSchedule(@NonNull final I_M_ShipmentSchedule schedule)
	{
		schedule.setIsClosed(true);
		save(schedule);
	}

	@Override
	public void openShipmentSchedule(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		Check.assume(shipmentSchedule.isClosed(), "The given shipmentSchedule is not closed; shipmentSchedule={}", shipmentSchedule);

		shipmentSchedule.setIsClosed(false);
		updateQtyOrdered(shipmentSchedule);

		save(shipmentSchedule);
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
		return TimeUtil.asZonedDateTime(shipmentScheduleEffectiveBL.getPreparationDate(schedule));
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
	public void applyUserChanges(@NonNull final ShipmentScheduleUserChangeRequestsList userChanges)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInThreadInheritedTrx(() -> applyUserChangesInTrx(userChanges));
	}

	private void applyUserChangesInTrx(@NonNull ShipmentScheduleUserChangeRequestsList userChanges)
	{
		final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);

		final Set<ShipmentScheduleId> shipmentScheduleIds = userChanges.getShipmentScheduleIds();
		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> recordsById = shipmentSchedulesRepo.getByIds(shipmentScheduleIds);

		for (final ShipmentScheduleId shipmentScheduleId : shipmentScheduleIds)
		{
			final ShipmentScheduleUserChangeRequest userChange = userChanges.getByShipmentScheduleId(shipmentScheduleId);
			final I_M_ShipmentSchedule record = recordsById.get(shipmentScheduleId);
			if (record == null)
			{
				// shall not happen
				logger.warn("No record found for {}. Skip applying user changes: {}", shipmentScheduleId, userChange);
				continue;
			}

			updateRecord(record, userChange);

			shipmentSchedulesRepo.save(record);
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
	}

}
