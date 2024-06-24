package de.metas.inoutcandidate.api.impl;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentConstraintsBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidRequest;
import de.metas.inoutcandidate.api.ShipmentSchedulesMDC;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.inoutcandidate.picking_bom.PickingBOMsReversedIndex;
import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLineFactory;
import de.metas.inoutcandidate.spi.impl.CompositeCandidateProcessor;
import de.metas.inoutcandidate.spi.impl.ShipmentScheduleOrderReferenceProvider;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.order.DeliveryRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;
import de.metas.tourplanning.model.TourId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.inout.util.DeliveryGroupCandidate;
import org.adempiere.inout.util.DeliveryGroupCandidateGroupId;
import org.adempiere.inout.util.DeliveryLineCandidate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate;
import org.adempiere.inout.util.IShipmentSchedulesDuringUpdate.CompleteStatus;
import org.adempiere.inout.util.ShipmentScheduleAvailableStock;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorage;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorageFactory;
import org.adempiere.inout.util.ShipmentSchedulesDuringUpdate;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

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

@Service
public class ShipmentScheduleUpdater implements IShipmentScheduleUpdater
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@VisibleForTesting
	public static ShipmentScheduleUpdater newInstanceForUnitTesting()
	{
		final StockRepository stockRepository = new StockRepository();
		final ShipmentScheduleQtyOnHandStorageFactory shipmentScheduleQtyOnHandStorageFactory = new ShipmentScheduleQtyOnHandStorageFactory(stockRepository);
		final ShipmentScheduleReferencedLineFactory shipmentScheduleReferencedLineFactory = new ShipmentScheduleReferencedLineFactory(Optional.of(ImmutableList.of(new ShipmentScheduleOrderReferenceProvider())));
		final PickingBOMService pickingBOMService = new PickingBOMService();

		return new ShipmentScheduleUpdater(
				shipmentScheduleQtyOnHandStorageFactory,
				shipmentScheduleReferencedLineFactory,
				pickingBOMService);
	}

	private static final Logger logger = LogManager.getLogger(ShipmentScheduleUpdater.class);

	private final IShipmentScheduleHandlerBL shipmentScheduleHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);
	private final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentScheduleDeliveryDayBL shipmentScheduleDeliveryDayBL = Services.get(IShipmentScheduleDeliveryDayBL.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final IShipmentConstraintsBL shipmentConstraintsBL = Services.get(IShipmentConstraintsBL.class);
	private final ShipmentScheduleQtyOnHandStorageFactory shipmentScheduleQtyOnHandStorageFactory;
	private final ShipmentScheduleReferencedLineFactory shipmentScheduleReferencedLineFactory;
	private final PickingBOMService pickingBOMService;
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);

	private final CompositeCandidateProcessor candidateProcessors = new CompositeCandidateProcessor();

	/**
	 * Flag which is set to true when shipment schedule updater is running.
	 * <p>
	 * This information is stored on thread level.
	 */
	private final ThreadLocal<Boolean> running = new ThreadLocal<>();

	public ShipmentScheduleUpdater(
			@NonNull final ShipmentScheduleQtyOnHandStorageFactory shipmentScheduleQtyOnHandStorageFactory,
			@NonNull final ShipmentScheduleReferencedLineFactory shipmentScheduleReferencedLineFactory,
			@NonNull final PickingBOMService pickingBOMService)
	{
		this.shipmentScheduleQtyOnHandStorageFactory = shipmentScheduleQtyOnHandStorageFactory;
		this.shipmentScheduleReferencedLineFactory = shipmentScheduleReferencedLineFactory;
		this.pickingBOMService = pickingBOMService;
	}

	private boolean isAllowConsolidateShipment(@NonNull final BPartnerId bpartnerId)
	{
		// NOTE: we cannot declare de service on top because BPartnerBL is a spring bean which will fail in JUnit tests.
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

		return bpartnerBL.isAllowConsolidateInOutEffective(bpartnerId, SOTrx.SALES);
	}

	@Override
	public void registerCandidateProcessor(final IShipmentSchedulesAfterFirstPassUpdater processor)
	{
		candidateProcessors.addCandidateProcessor(processor);
	}

	@Override
	public int updateShipmentSchedules(@NonNull final ShipmentScheduleUpdateInvalidRequest request)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		loggable.addLog("ShipmentScheduleUpdater - Invoked with ShipmentScheduleUpdateInvalidRequest={}", request);

		final PInstanceId selectionId = request.getSelectionId();

		final Boolean running = this.running.get();
		if (running != null && running)
		{
			throw new AdempiereException("updateShipmentSchedule is not already running");
		}
		this.running.set(true);

		try
		{
			// Create and invalidate missing shipment schedules, if asked
			if (request.isCreateMissingShipmentSchedules())
			{
				final Set<ShipmentScheduleId> shipmentSchedulesNewIds = shipmentScheduleHandlerBL.createMissingCandidates(request.getCtx());
				invalidSchedulesRepo.invalidateShipmentSchedules(shipmentSchedulesNewIds);

				loggable.addLog("ShipmentScheduleUpdater - created {} missing candidates", shipmentSchedulesNewIds.size());
			}

			final List<OlAndSched> olsAndScheds = shipmentSchedulePA.retrieveInvalid(selectionId);
			loggable.addLog("Found {} invalid shipment schedules and tagged them with {}", olsAndScheds.size(), selectionId);

			invalidatePickingBOMProducts(olsAndScheds, selectionId);

			updateSchedules(request.getCtx(), olsAndScheds);

			// cleanup the marker/pointer tables
			invalidSchedulesRepo.deleteRecomputeMarkersOutOfTrx(selectionId);

			logger.debug("Done");
			return olsAndScheds.size();
		}
		finally
		{
			try
			{
				// Make sure the recompute tag is released, just in case any error occurs.
				// Usually zero records will be released because, if everything goes fine, the tagged recompute records were already deleted.
				invalidSchedulesRepo.releaseRecomputeMarkerOutOfTrx(selectionId);
			}
			finally
			{
				this.running.set(false);
			}
		}
	}

	@Override
	public boolean isRunning()
	{
		final Boolean running = this.running.get();
		return running != null && running;
	}

	/**
	 * Updates the given {@link I_M_ShipmentSchedule}s by setting these columns:
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_QtyToDeliver}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_QtyOnHand}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_Status}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_AllowConsolidateInOut}
	 * <p>
	 * To actually set those values, this method calls the registered {@link IShipmentSchedulesAfterFirstPassUpdater}.
	 */
	@VisibleForTesting
	void updateSchedules(final Properties ctx, final List<OlAndSched> olsAndScheds)
	{
		if (olsAndScheds.isEmpty())
		{
			return;
		}

		//
		// first update those shipment schedule properties that don't need two passes
		for (final OlAndSched olAndSched : olsAndScheds)
		{
			try (final MDCCloseable ignored = ShipmentSchedulesMDC.putShipmentScheduleId(olAndSched.getShipmentScheduleId()))
			{
				final I_M_ShipmentSchedule sched = olAndSched.getSched();

				updateCatchUomId(sched);

				updateWarehouseId(sched);

				shipmentScheduleBL.updateCapturedLocationsAndRenderedAddresses(sched);

				shipmentScheduleBL.updateHeaderAggregationKey(sched);

				updateShipmentConstraints(sched);

				//
				// QtyPickList (i.e. qtyUnconfirmedShipments) is the sum of
				// * MovementQtys from all draft shipment lines which are pointing to shipment schedule's order line
				// * QtyPicked from QtyPicked records
				final BigDecimal qtyPickedOrOnDraftShipment;
				{
					// task 08123: we also take those numbers into account that are *not* on an M_InOutLine yet, but are nonetheless picked
					final Quantity qtyPickedAndUnconfirmed = shipmentScheduleAllocBL.retrieveQtyPickedAndUnconfirmed(sched);
					logger.debug("QtyPickedAndUnconfirmed={}", qtyPickedAndUnconfirmed);
					qtyPickedOrOnDraftShipment = qtyPickedAndUnconfirmed.toBigDecimal();

					sched.setQtyPickList(qtyPickedOrOnDraftShipment);
				}
			}
		}

		final ShipmentSchedulesDuringUpdate firstRun = generate_FirstRun(olsAndScheds);
		firstRun.updateCompleteStatusAndSetQtyToZeroWhereNeeded();

		applyCandidateProcessors(ctx, firstRun);

		// evaluate the processor's result: lines that have been discarded won't
		// be delivered and won't be validated in the second run.
		for (final DeliveryLineCandidate shipmentLineCandidate : firstRun.getAllLines())
		{
			if (shipmentLineCandidate.isDiscarded())
			{
				shipmentLineCandidate.setQtyToDeliver(BigDecimal.ZERO);
			}
			else
			{
				// remember: 'removeLine' means that a *new* line might be
				// created for the corresponding olAndSched
				shipmentLineCandidate.removeFromGroup();
				firstRun.removeLine(shipmentLineCandidate);
			}
		}

		// make the second run
		final IShipmentSchedulesDuringUpdate secondRun = generate_SecondRun(olsAndScheds, firstRun);

		// finally update the shipment schedule entries
		for (final OlAndSched olAndSched : olsAndScheds)
		{
			final I_M_ShipmentSchedule schedRecord = olAndSched.getSched();
			final BPartnerId bpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(schedRecord); // task 08756: we don't really care for the ol's partner, but for the partner who will actually receive the shipment.

			schedRecord.setAllowConsolidateInOut(isAllowConsolidateShipment(bpartnerId));

			updatePreparationAndDeliveryDate(schedRecord);

			//
			// Delivery Day related info:
			// TODO: invert dependency add make this pluggable from de.metas.tourplanning module
			shipmentScheduleDeliveryDayBL.updateDeliveryDayInfo(schedRecord);

			// task 09358: ol.qtyReserved should be as correct as QtyOrdered and QtyDelivered, but in some cases isn't. this here is a workaround to the problem
			// task 09869: don't rely on ol anyways
			final BigDecimal qtyDelivered = shipmentScheduleAllocDAO.retrieveQtyDelivered(schedRecord);
			schedRecord.setQtyDelivered(qtyDelivered);
			schedRecord.setQtyReserved(BigDecimal.ZERO.max(olAndSched.getQtyOrdered().subtract(schedRecord.getQtyDelivered())));

			updateLineNetAmt(olAndSched);

			ShipmentScheduleQtysHelper.updateQtyToDeliver(olAndSched, secondRun, shipmentScheduleAllocDAO);

			updateProcessedFlag(schedRecord);
			if (schedRecord.isProcessed())
			{
				// 04870 : Delivery rule force assumes we deliver full quantity ordered if qtyToDeliver_Override is null.
				// 06019 : check both DeliveryRule, as DeliveryRule_Override
				final boolean deliveryRuleIsForced = DeliveryRule.FORCE.equals(shipmentScheduleEffectiveBL.getDeliveryRule(schedRecord));
				if (deliveryRuleIsForced)
				{
					schedRecord.setQtyToDeliver(BigDecimal.ZERO);
				}
				else
				{
					Check.errorUnless(schedRecord.getQtyToDeliver().signum() == 0, "{} has QtyToDeliver = {} (should be zero)", schedRecord, schedRecord.getQtyToDeliver());
				}

				shipmentSchedulePA.save(schedRecord);

				continue;
			}

			// task 08694
			// I talked with Mark and he observed that in the wiki-page of 08459 it is specified differently.
			// I will let it here nevertheless, so we can keep track of it's way to work

			final BPartnerId partnerId = BPartnerId.ofRepoId(schedRecord.getC_BPartner_ID());
			final ProductId productId = ProductId.ofRepoId(schedRecord.getM_Product_ID());

			// FRESH-334 retrieve the bp product for org or for org 0
			final I_M_Product product = productsService.getById(productId);
			final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());

			final I_C_BPartner_Product bpp = bpartnerProductDAO.retrieveBPartnerProductAssociation(ctx, partnerId, productId, orgId);
			if (bpp == null)
			{
				// in case no dropship bpp entry was found, the schedule shall not be dropship
				schedRecord.setIsDropShip(false);
			}
			else
			{
				final boolean isDropShip = bpp.isDropShip();
				if (isDropShip)
				{
					// if there is bpp that is dropship and has a C_BPartner_Vendor_ID,
					// set the customer's vendor for the given product in the schedule
					schedRecord.setC_BPartner_Vendor_ID(bpp.getC_BPartner_Vendor_ID());
				}

				// set the dropship flag in shipment schedule as it is in the bpp
				schedRecord.setIsDropShip(isDropShip);
			}

			// 08860
			// update preparation date override based on delivery date effective
			// DO this only if the preparationDate_Override was not already set manually or by the process
			if (schedRecord.getDeliveryDate_Override() != null && schedRecord.getPreparationDate_Override() == null)
			{
				final ZonedDateTime deliveryDate = shipmentScheduleEffectiveBL.getDeliveryDate(schedRecord);

				final IContextAware contextAwareSched = InterfaceWrapperHelper.getContextAware(schedRecord);
				final BPartnerLocationId bpLocationId = shipmentScheduleEffectiveBL.getBPartnerLocationId(schedRecord);
				final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(schedRecord.getAD_Org_ID()));
				final ZonedDateTime calculationTime = TimeUtil.asZonedDateTime(schedRecord.getCreated(), timeZone);
				final ImmutablePair<TourId, ZonedDateTime> tourAndDate = deliveryDayBL.calculateTourAndPreparationDate(
						contextAwareSched,
						SOTrx.SALES,
						calculationTime,
						deliveryDate,
						bpLocationId);

				// In case the DeliveryDate Override is set, also update the preparationDate override
				final ZonedDateTime preparationDate = tourAndDate.getRight();
				schedRecord.setPreparationDate_Override(TimeUtil.asTimestamp(preparationDate));
				schedRecord.setM_Tour_ID(TourId.toRepoId(tourAndDate.getLeft()));
			}

			shipmentScheduleBL.updateExportStatus(schedRecord);
			schedRecord.setPOReference(olAndSched.getSalesOrderPORef());

			// do not invoke this method, it's invoked by a model interceptor when M_ShipmentSchedule.ExportStatus is changed *for whatevever reason*.
			// shipmentScheduleBL.updateCanBeExportedAfter(schedRecord);
			shipmentSchedulePA.save(schedRecord);
		}
	}

	ShipmentSchedulesDuringUpdate generate_FirstRun(@NonNull final List<OlAndSched> lines)
	{
		try (final MDCCloseable ignored = ShipmentSchedulesMDC.putShipmentScheduleUpdateRunNo(1))
		{
			final ShipmentSchedulesDuringUpdate firstRun = new ShipmentSchedulesDuringUpdate();
			return generate(lines, firstRun);
		}
	}

	ShipmentSchedulesDuringUpdate generate_SecondRun(
			@NonNull final List<OlAndSched> lines,
			@NonNull final ShipmentSchedulesDuringUpdate firstRun)
	{
		try (final MDCCloseable ignored = ShipmentSchedulesMDC.putShipmentScheduleUpdateRunNo(2))
		{
			return generate(lines, firstRun);
		}
	}

	private ShipmentSchedulesDuringUpdate generate(
			@NonNull final List<OlAndSched> lines,
			@NonNull final ShipmentSchedulesDuringUpdate shipmentSchedulesDuringUpdate)
	{
		//
		// Load QtyOnHand in scope for our lines
		// i.e. iterate all lines to cache the required storage info and to subtract the quantities that can't be allocated from the storage allocation.
		final ShipmentScheduleQtyOnHandStorage qtyOnHands = shipmentScheduleQtyOnHandStorageFactory.ofOlAndScheds(lines);

		//
		// Iterate and try to allocate the QtyOnHand
		for (final OlAndSched olAndSched : lines)
		{
			processSingleOlAndSched(olAndSched, shipmentSchedulesDuringUpdate, qtyOnHands);
		}
		return shipmentSchedulesDuringUpdate;
	} // generate

	private void processSingleOlAndSched(
			final @NonNull OlAndSched olAndSched,
			final @NonNull ShipmentSchedulesDuringUpdate shipmentSchedulesDuringUpdate,
			final @NonNull ShipmentScheduleQtyOnHandStorage shipmentScheduleQtyOnHandStorage)
	{
		try (final MDCCloseable ignored = ShipmentSchedulesMDC.putShipmentScheduleId(olAndSched.getShipmentScheduleId()))
		{
			final I_M_ShipmentSchedule sched = olAndSched.getSched();

			final DeliveryRule deliveryRule = shipmentScheduleEffectiveBL.getDeliveryRule(sched);
			logger.debug("DeliveryRule={}", deliveryRule);

			final BigDecimal qtyRequired = computeQtyRequiredForOlAndSched(olAndSched, deliveryRule);

			//
			// QtyToDeliver: qtyRequired - qtyPickList (non negative!)
			final BigDecimal qtyToDeliver = ShipmentScheduleQtysHelper.computeQtyToDeliver(qtyRequired, sched.getQtyPickList()/* qtyPickList was already updated by now*/);

			logger.debug("QtyToDeliver={}", qtyToDeliver);
			final ProductId productId = olAndSched.getProductId();
			if (!productsService.isStocked(productId))
			{
				// product not stocked => don't concern ourselves with the storage; just deliver what was ordered
				logger.debug("M_Product_ID={} is not stocked; will use qtyToDeliver={} as-is", productId.getRepoId(), qtyToDeliver);
				createLine(
						olAndSched,
						qtyToDeliver,
						ShipmentScheduleAvailableStock.of(),
						true/* force */,
						CompleteStatus.OK,
						shipmentSchedulesDuringUpdate);
				return; // we are done
			}

			//
			// Get the QtyOnHand storages suitable for our order line
			final ShipmentScheduleAvailableStock storages = shipmentScheduleQtyOnHandStorage.getStockDetailsMatching(sched);
			final BigDecimal qtyOnHandBeforeAllocation = storages.getTotalQtyAvailable();

			logger.debug("totalQtyAvailable={} from storages={}", qtyOnHandBeforeAllocation, storages);
			sched.setQtyOnHand(qtyOnHandBeforeAllocation);

			final CompleteStatus completeStatus = computeCompleteStatus(qtyToDeliver, qtyOnHandBeforeAllocation);

			//
			// Delivery rule: Force
			if (deliveryRule.isForce())
			{
				createLine( // createLine will also subtract the respective qty from storages
						olAndSched,
						qtyToDeliver,
						storages,
						true, // force
						completeStatus,
						shipmentSchedulesDuringUpdate);
			}
			//
			// Delivery rule: Complete Order/Line or Availability or Manual
			else if (deliveryRule.isCompleteOrderOrLine()
					|| deliveryRule.isAvailability()
					|| deliveryRule.isManual())
			{
				if (qtyOnHandBeforeAllocation.signum() > 0 || qtyToDeliver.signum() < 0)
				{
					//
					// if there is anything at all to deliver, create a new inOutLine
					final BigDecimal qtyToDeliverEffective = qtyToDeliver.min(qtyOnHandBeforeAllocation);

					// we invoke createLine even if ruleComplete is true and fullLine is false,
					// because we want the quantity to be allocated.
					// If the created line will make it into a real shipment will be decided later.
					createLine( // createLine will also subtract the respective qty from storages
							olAndSched,
							qtyToDeliverEffective,
							storages,
							false, // force
							completeStatus,
							shipmentSchedulesDuringUpdate);
				}
				else
				{
					logger.debug("No qtyOnHand to deliver[SKIP] - OnHand={}, ToDeliver={}, FullLine={}",
							qtyOnHandBeforeAllocation, qtyToDeliver, completeStatus);
				}
			}
			//
			// Unknown delivery rule
			else
			{
				throw new AdempiereException("Unsupported delivery rule: " + deliveryRule)
						.setParameter("qtyOnHandBeforeAllocation", qtyOnHandBeforeAllocation)
						.setParameter("qtyToDeliver", qtyToDeliver)
						.appendParametersToMessage();
			}
		}
	}

	@NonNull
	private BigDecimal computeQtyRequiredForOlAndSched(
			final @NonNull OlAndSched olAndSched,
			final @NonNull DeliveryRule deliveryRule)
	{
		final I_M_ShipmentSchedule sched = olAndSched.getSched();

		final BigDecimal qtyRequired;
		if (olAndSched.getQtyOverride() != null)
		{
			final BigDecimal qtyToDeliverOverrideFulFilled = ShipmentScheduleQtysHelper.computeQtyToDeliverOverrideFulFilled(olAndSched, shipmentScheduleAllocDAO);
			if (olAndSched.getQtyOverride().compareTo(qtyToDeliverOverrideFulFilled) > 0)
			{
				qtyRequired = olAndSched.getQtyOverride().subtract(qtyToDeliverOverrideFulFilled);
				logger.debug("QtyOverride={} is greater than QtyToDeliverOverrideFulFilled={}; => use the delta as QtyRequired={}", olAndSched.getQtyOverride(), qtyToDeliverOverrideFulFilled, qtyRequired);
			}
			else
			{
				final BigDecimal qtyDelivered = shipmentScheduleAllocDAO.retrieveQtyDelivered(sched);
				qtyRequired = olAndSched.getQtyOrdered().subtract(qtyDelivered);
				logger.debug("QtyOverride={} is less than QtyToDeliverOverrideFulFilled={}; => use QtyRequired={} as QtyOrdered={} minus QtyDelivered={}",
						olAndSched.getQtyOverride(), qtyToDeliverOverrideFulFilled, qtyRequired, olAndSched.getQtyOrdered(), qtyDelivered);
			}
		}
		else if (deliveryRule.isManual())
		{
			// lines with ruleManual need to be scheduled explicitly using QtyToDeliver_Override
			qtyRequired = BigDecimal.ZERO;
			logger.debug("DeliveryRule={}; => qtyRequired={}", deliveryRule, qtyRequired);
		}
		else
		{
			final BigDecimal qtyDelivered = shipmentScheduleAllocDAO.retrieveQtyDelivered(sched);
			qtyRequired = olAndSched.getQtyOrdered().subtract(qtyDelivered);
			logger.debug("DeliveryRule={}; => use QtyRequired={} as QtyOrdered={} minus QtyDelivered={}",
					deliveryRule, qtyRequired, olAndSched.getQtyOrdered(), qtyDelivered);
		}
		return qtyRequired;
	}

	private static CompleteStatus computeCompleteStatus(final BigDecimal qtyToDeliver, final BigDecimal qtyOnHand)
	{
		// fullLine==true means that we can create a shipment line with
		// the full (current!) quantity of the current order line
		final boolean fullLine = qtyToDeliver.signum() <= 0
				|| qtyOnHand.compareTo(qtyToDeliver) >= 0;

		return fullLine ? CompleteStatus.OK : CompleteStatus.INCOMPLETE_LINE;
	}

	/**
	 * Creates one or more inOutLines for a given orderLine.
	 *
	 * @param qty the quantity all created inOutLines' qtyEntered will sum up to
	 */
	private void createLine(
			@NonNull final OlAndSched olAndSched,
			@NonNull final BigDecimal qty,
			@NonNull final ShipmentScheduleAvailableStock storages,
			final boolean force,
			@NonNull final CompleteStatus completeStatus,
			@NonNull final ShipmentSchedulesDuringUpdate candidates)
	{
		if (candidates.hasDeliveryLineCandidateFor(olAndSched.getShipmentScheduleId()))
		{
			logger.debug("candidates contains already an delivery line candidate for {}", olAndSched);
			return;
		}

		final DeliveryGroupCandidate groupCandidate = getOrCreateGroupCandidateForShipmentSchedule(olAndSched.getSched(), candidates);

		if (storages.isEmpty())
		{
			final DeliveryLineCandidate deliveryLineCandidate = groupCandidate.createAndAddLineCandidate(olAndSched.getSched(), completeStatus);
			if (force) // Case: no Quantity on Hand storages and force => no need for allocations etc
			{
				deliveryLineCandidate.setQtyToDeliver(qty);
			}
			candidates.addLine(deliveryLineCandidate);
			return; // we are done
		}

		// Shipment Lines (i.e. candidate lines)
		final List<DeliveryLineCandidate> deliveryLines = new ArrayList<>();

		//
		// Iterate QtyOnHand storage records and try to allocate on current shipment schedule/order line.
		BigDecimal qtyRemainingToDeliver = qty; // how much still needs to be delivered; initially it's the whole qty required
		for (int storageIndex = 0; storageIndex < storages.size(); storageIndex++)
		{
			// Stop here if there is nothing remaining to be delivered
			if (qtyRemainingToDeliver.signum() == 0)
			{
				break;
			}

			BigDecimal qtyToDeliver = qtyRemainingToDeliver; // initially try to deliver the entire quantity remaining to be delivered

			//
			// Adjust the quantity that can be delivered from this storage line
			// Check: Not enough On Hand
			final BigDecimal qtyAvailable = storages.getQtyAvailable(storageIndex);
			if (qtyToDeliver.compareTo(qtyAvailable) > 0
					&& qtyAvailable.signum() >= 0)         // positive storage
			{
				if (!force // Adjust to OnHand Qty
						|| storageIndex + 1 != storages.size())         // if force not on last location
				{
					qtyToDeliver = qtyAvailable;
				}
			}
			// Skip if we cannot deliver something for current storage line
			if (qtyToDeliver.signum() == 0)
			{
				// zero deliver
				continue;
			}

			// Find existing lineCandidate that was added in a previous iteration
			DeliveryLineCandidate lineCandidate = null;
			for (final DeliveryLineCandidate existingLineCandidate : deliveryLines)
			{
				// skip if it's for a different order line
				if (existingLineCandidate.getShipmentScheduleId().equals(olAndSched.getShipmentScheduleId()))
				{
					lineCandidate = existingLineCandidate;
					break;
				}
			}

			// Case: No InOutLine found
			// => create a new InOutLine
			if (lineCandidate == null)
			{
				lineCandidate = groupCandidate.createAndAddLineCandidate(olAndSched.getSched(), completeStatus);

				lineCandidate.setQtyToDeliver(qtyToDeliver);

				deliveryLines.add(lineCandidate);
				candidates.addLine(lineCandidate);
			}
			//
			// Case: existing InOutLine found
			// => adjust the quantity
			else
			{
				final BigDecimal inoutLineQtyOld = lineCandidate.getQtyToDeliver();
				final BigDecimal inoutLineQtyNew = inoutLineQtyOld.add(qtyToDeliver);
				lineCandidate.setQtyToDeliver(inoutLineQtyNew);
			}

			qtyRemainingToDeliver = qtyRemainingToDeliver.subtract(qtyToDeliver);

			storages.subtractQtyOnHand(storageIndex, qtyToDeliver);
		}    // for each storage record
	}

	private DeliveryGroupCandidate getOrCreateGroupCandidateForShipmentSchedule(
			@NonNull final I_M_ShipmentSchedule sched,
			final IShipmentSchedulesDuringUpdate candidates)
	{
		final BPartnerId bpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(sched);

		final ShipmentScheduleReferencedLine scheduleSourceDoc = shipmentScheduleReferencedLineFactory.createFor(sched);
		final String bpartnerAddress = shipmentScheduleEffectiveBL.getBPartnerAddress(sched);

		DeliveryGroupCandidate candidate;

		final WarehouseId warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(sched);
		if (isAllowConsolidateShipment(bpartnerId))
		{
			// see if there is an existing shipment for this location and shipper
			candidate = candidates.getGroupForShipper(scheduleSourceDoc.getShipperId(), warehouseId, bpartnerAddress);
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

	@VisibleForTesting
	DeliveryGroupCandidate createGroup(
			final ShipmentScheduleReferencedLine scheduleSourceDoc,
			final I_M_ShipmentSchedule sched)
	{
		return DeliveryGroupCandidate.builder()
				.warehouseId(shipmentScheduleEffectiveBL.getWarehouseId(sched))
				.bPartnerAddress(shipmentScheduleEffectiveBL.getBPartnerAddress(sched))
				.groupId(DeliveryGroupCandidateGroupId.of(scheduleSourceDoc.getRecordRef()))
				.shipperId(scheduleSourceDoc.getShipperId())
				.build();
	}

	/**
	 * task 08336
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
		final boolean noQtyPickedAndNotDelivered = sched.getQtyPickList().signum() <= 0;

		sched.setProcessed(noQtyOverride && noQtyReserved && noQtyPickedAndNotDelivered);
	}

	private void updatePreparationAndDeliveryDate(@NonNull final I_M_ShipmentSchedule sched)
	{
		final ShipmentScheduleReferencedLine shipmentScheduleOrderDoc = shipmentScheduleReferencedLineFactory.createFor(sched);

		sched.setPreparationDate(TimeUtil.asTimestamp(shipmentScheduleOrderDoc.getPreparationDate()));
		sched.setDeliveryDate(TimeUtil.asTimestamp(shipmentScheduleOrderDoc.getDeliveryDate()));
	}

	private void updateShipmentConstraints(final I_M_ShipmentSchedule sched)
	{
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

	private void updateLineNetAmt(final OlAndSched olAndSched)
	{
		final BigDecimal lineNetAmt = computeLineNetAmt(olAndSched);
		olAndSched.setShipmentScheduleLineNetAmt(lineNetAmt);
	}

	/**
	 * Try to get the given <code>ol</code>'s <code>qtyReservedInPriceUOM</code> and update the given <code>sched</code>'s <code>LineNetAmt</code>.
	 *
	 * @throws AdempiereException in developer mode, if there the <code>qtyReservedInPriceUOM</code> can't be obtained.
	 * @implSpec <a href="https://github.com/metasfresh/metasfresh/issues/298">issue 298</a>
	 */
	private BigDecimal computeLineNetAmt(final OlAndSched olAndSched)
	{
		if (!olAndSched.hasSalesOrderLine())
		{
			return BigDecimal.ZERO;
		}

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
			//noinspection ThrowableNotThrown
			new AdempiereException(msg).throwIfDeveloperModeOrLogWarningElse(logger);
			return BigDecimal.ONE.negate();
		}
		else
		{
			final BigDecimal orderPriceActual = olAndSched.getOrderPriceActual();
			return qtyReservedInPriceUOM.multiply(orderPriceActual);
		}
	}

	private void applyCandidateProcessors(final Properties ctx, final IShipmentSchedulesDuringUpdate candidates)
	{
		candidateProcessors.doUpdateAfterFirstPass(ctx, candidates);
	}

	/**
	 * 07400 also update the M_Warehouse_ID; an order might have been reactivated and the warehouse might have been changed.
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
		final UomId catchUOMId = sched.isCatchWeight()
				? productsService.getCatchUOMId(ProductId.ofRepoId(sched.getM_Product_ID())).orElse(null)
				: null;

		sched.setCatch_UOM_ID(UomId.toRepoId(catchUOMId));
	}

	private void invalidatePickingBOMProducts(@NonNull final List<OlAndSched> olsAndScheds, final PInstanceId addToSelectionId)
	{
		if (olsAndScheds.isEmpty())
		{
			return;
		}

		final ImmutableSet<IShipmentScheduleSegment> pickingBOMsSegments = olsAndScheds.stream()
				.flatMap(this::extractPickingBOMsStorageSegments)
				.collect(ImmutableSet.toImmutableSet());
		if (pickingBOMsSegments.isEmpty())
		{
			return;
		}

		invalidSchedulesRepo.invalidateStorageSegments(pickingBOMsSegments, addToSelectionId);
	}

	private Stream<IShipmentScheduleSegment> extractPickingBOMsStorageSegments(final OlAndSched olAndSched)
	{
		try (final MDCCloseable ignored = ShipmentSchedulesMDC.putShipmentScheduleId(olAndSched.getShipmentScheduleId()))
		{
			final PickingBOMsReversedIndex pickingBOMsReversedIndex = pickingBOMService.getPickingBOMsReversedIndex();

			final ProductId componentId = olAndSched.getProductId();
			final ImmutableSet<ProductId> pickingBOMProductIds = pickingBOMsReversedIndex.getBOMProductIdsByComponentId(componentId);
			if (pickingBOMProductIds.isEmpty())
			{
				return Stream.empty();
			}

			final Set<WarehouseId> warehouseIds = warehousesRepo.getWarehouseIdsOfSamePickingGroup(olAndSched.getWarehouseId());

			final LinkedHashSet<IShipmentScheduleSegment> segments = new LinkedHashSet<>();
			for (final WarehouseId warehouseId : warehouseIds)
			{
				final Set<Integer> locatorRepoIds = warehousesRepo.getLocatorIds(warehouseId)
						.stream()
						.map(LocatorId::getRepoId)
						.collect(ImmutableSet.toImmutableSet());

				for (final ProductId pickingBOMProductId : pickingBOMProductIds)
				{
					final ImmutableShipmentScheduleSegment segment = ImmutableShipmentScheduleSegment.builder()
							.anyBPartner()
							.productId(pickingBOMProductId.getRepoId())
							.locatorIds(locatorRepoIds)
							.build();
					logger.debug("Add for pickingBOMProductId={} warehouseId={}: segment={}", pickingBOMProductId.getRepoId(), warehouseId.getRepoId(), segment);
					segments.add(segment);
				}
			}
			return segments.stream();
		}
	}
}
