package de.metas.inoutcandidate.api.impl;

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

import static org.compiere.model.X_C_Order.DELIVERYRULE_Availability;
import static org.compiere.model.X_C_Order.DELIVERYRULE_CompleteLine;
import static org.compiere.model.X_C_Order.DELIVERYRULE_CompleteOrder;
import static org.compiere.model.X_C_Order.DELIVERYRULE_Force;
import static org.compiere.model.X_C_Order.DELIVERYRULE_Manual;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.inout.service.IInOutPA;
import org.adempiere.inout.util.CachedObjects;
import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.inout.util.IShipmentCandidates.CompleteStatus;
import org.adempiere.inout.util.IShipmentCandidates.PostageFreeStatus;
import org.adempiere.inout.util.ShipmentCandidates;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorage;
import org.adempiere.inout.util.ShipmentScheduleStorageRecord;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MInOut;
import org.compiere.model.MOrder;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.document.engine.IDocActionBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IInOutCandHandlerBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_IolCandHandler_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.spi.ICandidateProcessor;
import de.metas.inoutcandidate.spi.IShipmentScheduleQtyUpdateListener;
import de.metas.inoutcandidate.spi.impl.CompositeCandidateProcessor;
import de.metas.inoutcandidate.spi.impl.CompositeShipmentScheduleQtyUpdateListener;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.purchasing.api.IBPartnerProductDAO;
import de.metas.storage.IStorageBL;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;

/**
 * This service computes the quantities to be shipped to customers for a list of {@link I_C_OrderLine}s and their respective {@link I_M_ShipmentSchedule}s.
 *
 * @see IShipmentSchedulePA
 * @see OlAndSched
 *
 * @author ts
 *
 */
public class ShipmentScheduleBL implements IShipmentScheduleBL
{
	private static final String DYNATTR_ProcessedByBackgroundProcess = IShipmentScheduleBL.class.getName() + "#ProcessedByBackgroundProcess";

	private final static Logger logger = LogManager.getLogger(ShipmentScheduleBL.class);

	private final CompositeCandidateProcessor candidateProcessors = new CompositeCandidateProcessor();

	/**
	 * Listeners for delivery Qty updates (task 08959)
	 */
	final CompositeShipmentScheduleQtyUpdateListener listeners = new CompositeShipmentScheduleQtyUpdateListener();

	@Override
	public void updateSchedules(
			final Properties ctx,
			final List<OlAndSched> olsAndScheds,
			final boolean saveSchedules,
			final Timestamp date,
			final CachedObjects co,
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
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		final IInOutCandHandlerBL inOutCandHandlerBL = Services.get(IInOutCandHandlerBL.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final IAggregationKeyBuilder<I_M_ShipmentSchedule> shipmentScheduleKeyBuilder = mkShipmentHeaderAggregationKeyBuilder();

		//
		// Briefly update our shipment schedules:
		// * set BPartnerAddress_Override if was not set before
		// * update HeaderAggregationKey
		for (final OlAndSched olAndSched : olsAndScheds)
		{
			final I_M_ShipmentSchedule sched = olAndSched.getSched();

			// 07400
			// also update the M_Warehouse_ID
			final I_C_OrderLine ol = olAndSched.getOl();
			sched.setM_Warehouse(ol.getM_Warehouse());

			// Make sure that all records have a value for BPartnerAddress_Override
			// Note: we assume that *if* the value is set, it is as intended by the user
			if (Check.isEmpty(sched.getBPartnerAddress_Override(), true))
			{
				updateBPArtnerAddressOverride(ctx, sched, trxName);
			}

			//
			// Rebuild header aggregation key
			final String headerAggregationKey = shipmentScheduleKeyBuilder.buildKey(sched);
			sched.setHeaderAggregationKey(headerAggregationKey);
		}

		final CachedObjects coToUse = mkCoToUse(co);

		final ShipmentCandidates firstRun = generate(ctx, olsAndScheds, null, coToUse, date, trxName);
		firstRun.afterFirstRun(false);

		// prepare the second run
		// coToUse.resetOnHandQtys();

		final int removeCnt = applyCandidateProcessors(ctx, firstRun, coToUse, trxName);
		logger.info(removeCnt + " records were discarded by candidate processors");

		// evaluate the processor's result: lines that have been discarded won't
		// be delivered and won't be validated in the second run.
		for (final I_M_InOut inOut : firstRun.getCandidates())
		{
			for (final I_M_InOutLine inOutLine : firstRun.getLines(inOut))
			{
				if (firstRun.isLineDiscarded(inOutLine))
				{
					inOutLine.setMovementQty(BigDecimal.ZERO);
				}
				else
				{
					// remember: 'removeLine' means that a *new* line might be
					// created for the corresponding olAndSched
					firstRun.removeLine(inOutLine);
				}
			}
		}

		// make the second run
		final IShipmentCandidates secondRun = generate(ctx, olsAndScheds, firstRun, coToUse, date, trxName);

		// finally update the shipment schedule entries
		for (final OlAndSched olAndSched : olsAndScheds)
		{
			final I_C_OrderLine ol = olAndSched.getOl();
			final I_C_Order order = co.retrieveAndCacheOrder(ol, trxName);
			final boolean isSOTrx = order.isSOTrx();

			final I_M_ShipmentSchedule sched = olAndSched.getSched();
			final IDeliverRequest deliverRequest = olAndSched.getDeliverRequest();
			final I_C_BPartner bPartner = shipmentScheduleEffectiveBL.getBPartner(sched); // task 08756: we don't really care for the ol's partner, but for the partner who will actually receive the
			// shipment.

			final org.compiere.model.I_M_Product product = ol.getM_Product();

			final boolean isBPAllowConsolidateInOut = bpartnerBL.isAllowConsolidateInOutEffective(bPartner, isSOTrx);
			sched.setAllowConsolidateInOut(isBPAllowConsolidateInOut);
			sched.setPostageFreeAmt(bPartner.getPostageFreeAmt());

			//
			// Delivery Day related info:
			// TODO: invert dependency add make this pluggable from de.metas.tourplanning module

			shipmentScheduleDeliveryDayBL.updateDeliveryDayInfo(olAndSched.getSched());

			// task 09358: ol.qtyReserved should be as correct as QtyOrdered and QtyDelivered, but in some cases isn't. this here is a workaround to the problem
			// task 09869: don't rely on ol anyways
			final BigDecimal qtyDelivered = Services.get(IShipmentScheduleAllocDAO.class).retrieveQtyDelivered(sched);
			sched.setQtyDelivered(qtyDelivered);
			sched.setQtyReserved(BigDecimal.ZERO.max(deliverRequest.getQtyOrdered().subtract(sched.getQtyDelivered())));

			// task 08959
			// Additional qty updates from other projects
			listeners.updateQtys(sched);

			updateLineNewAmt(ctx, ol, sched, product);

			final I_M_InOutLine inOutLine = secondRun.getInOutLineForOrderLine(ol.getC_OrderLine_ID());
			if (inOutLine != null)
			{
				sched.setQtyToDeliver(inOutLine.getMovementQty());
				sched.setStatus(mkStatus(inOutLine, secondRun));
			}
			else
			{
				setQtyToDeliverWhenNullInoutLine(sched);
			}

			final BigDecimal newQtyToDeliverOverrideFulfilled = mkQtyToDeliverOverrideFulFilled(olAndSched);

			if (olAndSched.getQtyOverride() != null)
			{

				if (newQtyToDeliverOverrideFulfilled.compareTo(olAndSched.getQtyOverride()) >= 0)
				{
					// the QtyToDeliverOverride value that was set by the user has been fulfilled (or even
					// over-fulfilled)
					sched.setQtyToDeliver_Override(null);
					sched.setQtyToDeliver_OverrideFulfilled(null);
				}
				else
				{
					sched.setQtyToDeliver_OverrideFulfilled(newQtyToDeliverOverrideFulfilled);
				}
			}
			else
			{
				sched.setQtyToDeliver_OverrideFulfilled(null);
			}

			final String orderDocStatus = coToUse.retrieveAndCacheOrder(ol, trxName).getDocStatus();

			if (saveSchedules)
			{
				final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

				InterfaceWrapperHelper.setDynAttribute(sched, DYNATTR_ProcessedByBackgroundProcess, Boolean.TRUE);

				if (!docActionBL.isStatusCompletedOrClosedOrReversed(orderDocStatus) // task 07355: thread closed orders like completed orders
						&& !sched.isProcessed() // task 05206: ts: don't try to delete already processed scheds..it won'T work ^^
						&& sched.getQtyDelivered().signum() == 0 // also don'T try to delete if there is already a picked or delivered Qty.
						&& sched.getQtyPickList().signum() == 0)
				{
					final List<I_M_IolCandHandler_Log> olHandlerLogs = inOutCandHandlerBL.retrieveOLHandlerLogs(ctx, sched.getM_IolCandHandler_ID(), sched.getC_OrderLine_ID(), trxName);
					for (final I_M_IolCandHandler_Log logRecord : olHandlerLogs)
					{
						// we can s this log now
						InterfaceWrapperHelper.delete(logRecord);
					}
					logger.debug("QtyToDeliver_Override=" + sched.getQtyToDeliver_Override()
							+ "; QtyReserved=" + sched.getQtyReserved()
							+ "; DocStatus=" + orderDocStatus
							+ "; => Deleting " + sched);

					// Retrieve the M_ShipmentSchedule_QtyPicked entries for this schedule
					final List<I_M_ShipmentSchedule_QtyPicked> allocations = shipmentScheduleAllocDAO.retrieveAllQtyPickedRecords(sched, I_M_ShipmentSchedule_QtyPicked.class);
					for (final I_M_ShipmentSchedule_QtyPicked alloc : allocations)
					{
						// delete the qtyPicked entries

						if (alloc.getQtyPicked().signum() != 0)
						{
							final String msg = "Found QtyPicked record with non-zero qty even if the shipment schedule has QtyDelivered=0"
									+ "\n M_ShipmentSchedule_ID = " + sched.getM_ShipmentSchedule_ID()
									+ "\n QtyPicked = " + alloc.getQtyPicked();
							Loggables.get().addLog(msg);

							final AdempiereException ex = new AdempiereException(msg);
							logger.warn(ex.getLocalizedMessage(), ex);

							continue;
						}
						InterfaceWrapperHelper.delete(alloc);
					}
					// we can delete this schedule now
					InterfaceWrapperHelper.delete(sched);
					continue;
				}

				if (updateProcessedFlag(sched))
				{
					// 04870 : Delivery rule force assumes we deliver full quantity ordered if qtyToDeliver_Override is null.
					// 06019 : check both DeliveryRule, as DeliveryRule_Override
					Check.errorIf(sched.getQtyToDeliver().signum() != 0
							&& !X_C_Order.DELIVERYRULE_Force.equals(shipmentScheduleEffectiveBL.getDeliveryRule(sched)), "{} has QtyToDeliver = {} (should be zero)",
							sched, sched.getQtyToDeliver());

					sched.setQtyToDeliver(BigDecimal.ZERO);
					InterfaceWrapperHelper.save(sched);
					continue;
				}
			}

			final BigDecimal qtyDeliverable = secondRun.getQtyDeliverable(olAndSched.getOl().getC_OrderLine_ID());
			sched.setQtyDeliverable(qtyDeliverable);

			//
			// 08459: Configure dropShip, and, if true, then set the C_BPartner_Vendor of the C_BPartner (customer) on the shipment schedule
			// final boolean dropShip = order.isDropShip();
			// sched.setIsDropShip(dropShip);
			// if (dropShip && sched.getC_BPartner_Vendor_ID() <= 0)
			// {
			// final I_C_BPartner_Product bpp = Services.get(IBPartnerProductDAO.class).retrieveBPProductForCustomer(order.getDropShip_BPartner(), product);
			// if (bpp != null)
			// {
			// final org.compiere.model.I_C_BPartner bpVendor = bpp.getC_BPartner_Vendor(); // the customer's vendor for the given product
			// sched.setC_BPartner_Vendor(bpVendor);
			// }
			// }

			// task 08694
			// I talked with Mark and he observed that in the wiki-page of 08459 it is specified differently.
			// I will let it here nevertheless, so we can keep track of it's way to work

			final org.compiere.model.I_C_BPartner partner = sched.getC_BPartner();

			// FRESH-334 retrieve the bp product for org or for org 0
			final int orgId = product.getAD_Org_ID();

			final de.metas.interfaces.I_C_BPartner_Product bpp = InterfaceWrapperHelper.create(Services.get(IBPartnerProductDAO.class).retrieveBPartnerProductAssociation(partner, product, orgId),
					de.metas.interfaces.I_C_BPartner_Product.class);

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
					// if there is bpp that is dropship and has a C_BPartner_Vendor_ID, set it in the schedule
					final org.compiere.model.I_C_BPartner bpVendor = bpp.getC_BPartner_Vendor(); // the customer's vendor for the given product
					sched.setC_BPartner_Vendor(bpVendor);
				}

				// set the dropship flag in shipment schedule as it is in the bpp
				sched.setIsDropShip(isDropShip);
			}

			// 08860
			// update preparation date override based on delivery date effective
			// DO this only if the preparationDate_Override was not already set manually or by the process

			if (sched.getDeliveryDate_Override() != null && sched.getPreparationDate_Override() == null)
			{
				final Timestamp deliveryDate = shipmentScheduleEffectiveBL.getDeliveryDate(sched);

				final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);
				final IContextAware contextAwareSched = InterfaceWrapperHelper.getContextAware(sched);
				final int bpLocationId = shipmentScheduleEffectiveBL.getC_BP_Location_ID(sched);

				final Timestamp preparationDate = deliveryDayBL.calculatePreparationDateOrNull(contextAwareSched, isSOTrx, deliveryDate, bpLocationId);

				// In case the DeliveryDate Override is set, also update the preparationDate override
				sched.setPreparationDate_Override(preparationDate);

			}

			if (saveSchedules)
			{
				InterfaceWrapperHelper.save(sched);
			}

		}
	}

	/**
	 * Try to get the given <code>ol</code>'s <code>qtyReservedInPriceUOM</code> and update the given <code>sched</code>'s <code>LineNetAmt</code>.
	 *
	 * @param ctx
	 * @param ol
	 * @param sched
	 * @param product
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/298
	 * @throws AdempiereException in developer mode, if there the <code>qtyReservedInPriceUOM</code> can't be obtained.
	 */
	private void updateLineNewAmt(final Properties ctx, final I_C_OrderLine ol, final I_M_ShipmentSchedule sched, final org.compiere.model.I_M_Product product)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final de.metas.interfaces.I_C_OrderLine olEx = InterfaceWrapperHelper.create(ol, de.metas.interfaces.I_C_OrderLine.class);
		final BigDecimal qtyReservedInPriceUOM = uomConversionBL.convertFromProductUOM(ctx, product, olEx.getPrice_UOM(), ol.getQtyReserved());

		// qtyReservedInPriceUOM might be null. in that case, don't fail the whole updating, but set the value to null
		if (qtyReservedInPriceUOM == null)
		{
			final String msg = "IUOMConversionBL.convertFromProductUOM() failed for M_Product=" + product + ", and C_OrderLine=" + olEx + "; \n"
					+ "Therefore we can't set LineNetAmt for M_ShipmentSchedule=" + sched + "; \n"
					+ "Note: if this exception was thrown and not just logged, then check for stale M_ShipmentSchedule_Recompute records";
			new AdempiereException(msg).throwIfDeveloperModeOrLogWarningElse(logger);
			sched.setLineNetAmt(BigDecimal.ONE.negate());
		}
		else
		{
			sched.setLineNetAmt(qtyReservedInPriceUOM.multiply(ol.getPriceActual()));
		}
	}

	/**
	 * Method sets the give {@code sched}'s {@code QtyToDeliver} value in case the previous allocation runs did <b>not</b> allocate a qty to deliver. Note that if the effective delivery rule is
	 * "FORCE", then we still need to set a qty even in that case.
	 *
	 * @param sched
	 */
	/* package */void setQtyToDeliverWhenNullInoutLine(final I_M_ShipmentSchedule sched)
	{
		//
		// if delivery rule == force :
		// // set qtyToDeliver = qtyToDeliverOveride (if exists)
		// // else set qtyToDeliver = QtyOrdered
		// if not forced, qtyToDeliver is 0
		final String deliveryRule = Services.get(IShipmentScheduleEffectiveBL.class).getDeliveryRule(sched);
		final boolean ruleForce = DELIVERYRULE_Force.equals(deliveryRule);
		if (ruleForce)
		{
			final BigDecimal qtyToDeliverOverride = sched.getQtyToDeliver_Override();
			if (qtyToDeliverOverride.compareTo(BigDecimal.ZERO) > 0)
			{
				sched.setQtyToDeliver(qtyToDeliverOverride);
			}
			else
			{
				// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
				final BigDecimal qtyOrdered = Services.get(IShipmentScheduleEffectiveBL.class).getQtyOrdered(sched);

				// task 07884-IT1: even if the rule is force: if there is an unconfirmed qty, then *don't* deliver it again
				sched.setQtyToDeliver(mkQtyToDeliver(qtyOrdered, sched.getQtyPickList()));
			}
		}
		else
		{
			sched.setQtyToDeliver(BigDecimal.ZERO);
		}
	}

	private BigDecimal mkQtyToDeliverOverrideFulFilled(final OlAndSched olAndSched)
	{
		final I_C_OrderLine ol = olAndSched.getOl();
		final I_M_ShipmentSchedule sched = olAndSched.getSched();

		final BigDecimal deliveredDiff = ol.getQtyDelivered().subtract(olAndSched.getInitialSchedQtyDelivered());

		final BigDecimal newQtyToDeliverOverrideFulfilled = sched.getQtyToDeliver_OverrideFulfilled().add(deliveredDiff);
		if (newQtyToDeliverOverrideFulfilled.signum() < 0)
		{
			return BigDecimal.ZERO;
		}
		return newQtyToDeliverOverrideFulfilled;
	}

	/**
	 * Generate Shipments.
	 *
	 * Defaults:
	 * <ul>
	 * <li>Unconfirmed shipments count
	 * <li>consolidation is on (unless the BPartner forbids it)
	 * <li>movement date is now
	 * </ul>
	 *
	 * @param lines orderLines with optional override parameters. The lines are evaluated in list order
	 *
	 * @return firstRun may be <code>null</code>. Can contain results from a former run. InOutLines contained here are skipped.
	 */
	private ShipmentCandidates generate(
			final Properties ctx,
			final List<OlAndSched> lines,
			final ShipmentCandidates firstRun, final CachedObjects co,
			final Timestamp date,
			final String trxName)
	{
		logger.info("Starting to create shipment candiates from " + lines.size() + " orderlines");

		if (firstRun != null)
		{
			logger.info("Using candidates from first run");
		}

		// services
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
		final IProductBL productBL = Services.get(IProductBL.class);
		final IStorageBL storageBL = Services.get(IStorageBL.class);

		// if firstRun is not null, create a new instance, otherwise use firstRun
		final ShipmentCandidates candidates = mkCandidatesToUse(lines, firstRun);

		final ShipmentScheduleQtyOnHandStorage qtyOnHands = new ShipmentScheduleQtyOnHandStorage();
		qtyOnHands.setContext(new PlainContextAware(ctx, trxName));
		qtyOnHands.setDate(date);
		qtyOnHands.setCachedObjects(co);

		//
		// Load QtyOnHand in scope for our lines
		// i.e. iterate all lines to cache the required storage info and to subtract the quantities that can't be allocated from the storage allocation.
		qtyOnHands.loadStoragesFor(lines);

		//
		// Iterate again and:
		// * try to allocate the QtyOnHand
		for (final OlAndSched olAndSched : lines)
		{
			final I_C_OrderLine orderLine = olAndSched.getOl();
			final I_M_ShipmentSchedule sched = olAndSched.getSched();
			final IDeliverRequest deliverRequest = olAndSched.getDeliverRequest();

			final I_C_Order order = co.retrieveAndCacheOrder(orderLine, trxName);
			final String deliveryRule = shipmentScheduleEffectiveValuesBL.getDeliveryRule(sched);

			logger.debug("check: {} - DeliveryRule={}", new Object[] { order, deliveryRule });
			logger.debug("check: {}", orderLine);

			final boolean ruleManual = DELIVERYRULE_Manual.equals(deliveryRule);

			final BigDecimal qtyRequired;
			if (olAndSched.getQtyOverride() != null)
			{
				qtyRequired = olAndSched.getQtyOverride().subtract(mkQtyToDeliverOverrideFulFilled(olAndSched));
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
			BigDecimal qtyUnconfirmedShipments;
			{
				qtyUnconfirmedShipments = qtyOnHands.getQtyUnconfirmedShipmentsPerShipmentSchedule(sched);

				// task 08123: we also take those numbers into account that are *not* on an M_InOutLine yet, but are nonetheless picked
				final BigDecimal qtyPickedNotDelivered = shipmentScheduleAllocDAO.retrievePickedNotDeliveredQty(sched);
				qtyUnconfirmedShipments = qtyUnconfirmedShipments.add(qtyPickedNotDelivered);

				// Update shipment schedule's field
				sched.setQtyPickList(qtyUnconfirmedShipments);
			}

			//
			// Check if there is any point to continue calculating how much we can deliever and also create those internal M_InOutLines.
			final I_M_Product product = co.retrieveAndCacheProduct(orderLine);
			if (isNothingToDo(product, qtyRequired, orderLine))
			{
				continue;
			}

			final BigDecimal qtyToDeliver = mkQtyToDeliver(qtyRequired, qtyUnconfirmedShipments);

			final boolean ruleCompleteOrder = DELIVERYRULE_CompleteOrder.equals(deliveryRule);

			// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
			final BigDecimal qtyOrdered = Services.get(IShipmentScheduleEffectiveBL.class).getQtyOrdered(sched);

			// Comments & lines w/o product & services
			if ((product == null || !productBL.isStocked(product))
					&& (qtyOrdered.signum() == 0 // comments
							|| qtyToDeliver.signum() != 0))         // lines w/o product
			{
				if (!ruleCompleteOrder)
				{
					// note: the value of the second last parameter (completeStatus) is irrelevant here
					createLine(ctx, order, olAndSched, qtyToDeliver, null, false, CompleteStatus.OK, candidates, co, trxName);
				}
				continue;
			}

			//
			// Get the QtyOnHand storages suitable for our order line
			final List<ShipmentScheduleStorageRecord> storages = qtyOnHands.getStorageRecordsMatching(olAndSched);
			final BigDecimal qtyOnHandBeforeAllocation = storageBL.calculateQtyOnHandSum(storages);
			sched.setQtyOnHand(qtyOnHandBeforeAllocation);

			final CompleteStatus completeStatus = mkCompleteStatus(qtyToDeliver, qtyOnHandBeforeAllocation);
			final boolean ruleAvailable = DELIVERYRULE_Availability.equals(deliveryRule);
			final boolean ruleCompleteLine = DELIVERYRULE_CompleteLine.equals(deliveryRule);
			final boolean ruleForce = DELIVERYRULE_Force.equals(deliveryRule);

			if (ruleForce)
			{
				// delivery rule force
				logger.debug("Force - OnHand=" + qtyOnHandBeforeAllocation
						+ " (Unconfirmed=" + qtyUnconfirmedShipments + "), ToDeliver=" + qtyToDeliver
						+ ", Delivering=" + qtyToDeliver + " - " + orderLine);

				createLine(ctx, order, olAndSched, qtyToDeliver, storages, true, completeStatus, candidates, co, trxName);
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
							+ " (Unconfirmed=" + qtyUnconfirmedShipments + "), ToDeliver=" + qtyToDeliver
							+ ", FullLine=" + completeStatus + " - " + orderLine);

					createLine(ctx, order, olAndSched, deliver, storages, false, completeStatus, candidates, co, trxName);
				}
				else
				{
					logger.debug("No qtyOnHand to deliver[SKIP] - OnHand=" + qtyOnHandBeforeAllocation
							+ " (Unconfirmed=" + qtyUnconfirmedShipments + "), ToDeliver=" + qtyToDeliver
							+ ", FullLine=" + completeStatus + " - " + orderLine);
				}
			}
			else
			{
				throw new AdempiereException(
						"Unsupported delivery rule: " + deliveryRule
								+ " - OnHand=" + qtyOnHandBeforeAllocation + " (Unconfirmed=" + qtyUnconfirmedShipments + "), ToDeliver=" + qtyToDeliver + " - " + orderLine);
			}
		}

		logger.info("Created " + candidates.size() + " shipment candidates");

		return candidates;
	} // generate

	private boolean isNothingToDo(
			final I_M_Product product,
			final BigDecimal qtyRequired,
			final I_C_OrderLine line)
	{

		if (product != null && qtyRequired.signum() <= 0)
		{
			return true;
		}
		// or it's a charge - Bug#: 1603966
		if (line.getC_Charge_ID() != 0 && qtyRequired.signum() == 0)
		{
			return true;
		}

		// metas: guarding against a possible bug in the application
		// dictionary that allows to enter invalid order lines
		if (product == null && line.getQtyOrdered().signum() != 0)
		{
			logger.warn("Ignoring invalid order line " + line.getLine());
			return true;
		}

		return false;
	}

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

	private BigDecimal mkQtyToDeliver(final BigDecimal qtyRequired, final BigDecimal unconfirmedShippedQty)
	{
		final StringBuilder logInfo = new StringBuilder("Unconfirmed Qty=" + unconfirmedShippedQty + " - ToDeliver=" + qtyRequired + "->");

		BigDecimal toDeliver = qtyRequired.subtract(unconfirmedShippedQty);

		logInfo.append(toDeliver);

		if (toDeliver.signum() < 0)
		{
			toDeliver = Env.ZERO;
			logInfo.append(" (set to 0)");
		}
		logger.debug(logInfo.toString());
		return toDeliver;
	}

	private ShipmentCandidates mkCandidatesToUse(
			final List<OlAndSched> lines,
			final ShipmentCandidates firstRun)
	{
		if (firstRun != null)
		{
			return firstRun;
		}

		final Map<Integer, I_C_OrderLine> olCache = new HashMap<>();
		for (final OlAndSched olAndSched : lines)
		{
			olCache.put(olAndSched.getOl().getC_OrderLine_ID(), olAndSched.getOl());
		}

		return new ShipmentCandidates(olCache);

	}

	/**
	 * Creates one or more inOutLines for a given orderLine.
	 *
	 * @param order order
	 * @param olAndSched line
	 * @param qty the quantity all created inOutLines' qtyEntered will sum up to
	 * @param storages storage info
	 * @param force force delivery
	 * @param completeStatus
	 */
	private void createLine(
			final Properties ctx,
			final I_C_Order order,
			final OlAndSched olAndSched,
			final BigDecimal qty,
			final List<ShipmentScheduleStorageRecord> storages,
			final boolean force,
			final CompleteStatus completeStatus,
			final ShipmentCandidates candidates,
			final CachedObjects co,
			final String trxName)
	{

		final I_C_OrderLine orderLine = olAndSched.getOl();
		final I_M_ShipmentSchedule sched = olAndSched.getSched();

		if (candidates.getInOutLineFor(orderLine) != null)
		{
			logger.debug("candidates contains already an inoutLine for orderLine {}", orderLine);
			return;
		}

		//
		// Services
		final IInOutPA inOutPA = Services.get(IInOutPA.class);
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);

		//
		// Create the M_InOut header candidate
		final I_C_BPartner partner = co.retrieveAndCacheBPartner(orderLine);
		final boolean consolidateAllowed = bpartnerBL.isAllowConsolidateInOutEffective(partner, order.isSOTrx());
		I_M_InOut candidate = null;
		final String bPartnerAddress = sched.getBPartnerAddress_Override();
		final I_M_Warehouse warehouse = warehouseAdvisor.evaluateWarehouse(orderLine);
		if (consolidateAllowed)
		{
			// see if there is an existing shipment for this location and shipper
			candidate = candidates.getInOutForShipper(orderLine.getM_Shipper_ID(), warehouse.getM_Warehouse_ID(), bPartnerAddress);
		}
		else
		{
			// see if there is an existing shipment for this order
			candidate = candidates.getInOutForOrderId(orderLine.getC_Order_ID(), warehouse.getM_Warehouse_ID(), bPartnerAddress);
		}
		if (candidate == null)
		{
			// create a new Shipment
			candidate = createInOut(ctx, order, SystemTime.asTimestamp(), olAndSched, trxName);
			candidates.addInOut(candidate);
		}

		//
		// Case: no Quantity on Hand storages
		if (storages == null || storages.isEmpty())
		{
			final I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(inOutPA.createNewLine(candidate, trxName), I_M_InOutLine.class);

			final int locatorId = 0;
			inOutPA.setLineOrderLine(inoutLine, orderLine, locatorId, Env.ZERO);
			inOutPA.setLineQty(inoutLine, qty); // Correct UOM

			// 05292 : Propagate ASI to InOutLine
			// task 08811: no need to clone the ASI. The ASI won't be altered and the inoutLine won't be saved anyways.
			inoutLine.setM_AttributeSetInstance_ID(sched.getM_AttributeSetInstance_ID());
			logger.debug(inoutLine.toString());

			candidates.addLine(candidate, inoutLine, sched, completeStatus, order);

			return;
		}

		//
		// Get Product
		// final I_M_Product product = co.getProductCache().get(ol.getM_Product_ID());

		//
		// Get ASI related info
		// final int attributeSetId = Services.get(IProductBL.class).getM_AttributeSet_ID(product);
		// final boolean linePerASI;
		// if (attributeSetId > 0)
		// {
		// I_M_AttributeSet mas = co.getAttributeSetCache().get(attributeSetId);
		// if (mas == null)
		// {
		// mas = productPA.retrieveAttributeSet(attributeSetId, trxName);
		// co.getAttributeSetCache().put(attributeSetId, mas);
		// }
		// linePerASI = mas.isInstanceAttribute();
		// }
		// else
		// {
		// linePerASI = false;
		// }

		//
		// Shipment Lines (i.e. candidate lines)
		final List<I_M_InOutLine> inoutLines = new ArrayList<>();

		//
		// Iterate QtyOnHand storage records and try to allocate on current shipment schedule/order line.
		BigDecimal toDeliver = qty; // how much still needs to be delivered; initially it's the whole qty required
		for (int i = 0; i < storages.size(); i++)
		{
			// Stop here is there is nothing remaining to be delivered
			if (toDeliver.signum() == 0)
			{
				break;
			}

			final ShipmentScheduleStorageRecord storage = storages.get(i);
			BigDecimal deliver = toDeliver; // initially try to deliver the entire quantity remaining to be delivered

			//
			// Adjust the quantity that can be delivered from this storage line
			// Check: Not enough On Hand
			final BigDecimal qtyOnHandAvailable = storage.getQtyOnHand();
			if (deliver.compareTo(qtyOnHandAvailable) > 0
					&& qtyOnHandAvailable.signum() >= 0)         // positive storage
			{
				if (!force // Adjust to OnHand Qty
						|| force && i + 1 != storages.size())         // if force not on last location
				{
					deliver = qtyOnHandAvailable;
				}
			}
			// Skip if we cannot deliver something for current storage line
			if (deliver.signum() == 0)
			{
				// zero deliver
				continue;
			}

			//
			// Find existing line
			I_M_InOutLine inoutLine = null;
			for (final I_M_InOutLine inoutLineExisting : inoutLines)
			{
				// skip if it's for a different order line
				if (inoutLineExisting.getC_OrderLine_ID() != orderLine.getC_OrderLine_ID())
				{
					continue;
				}

				inoutLine = inoutLineExisting;
			}

			//
			// Case: No InOutLine found
			// => create a new InOutLine
			if (inoutLine == null)
			{
				inoutLine = InterfaceWrapperHelper.create(inOutPA.createNewLine(candidate, trxName), I_M_InOutLine.class);

				final I_M_Locator locator = storage.getLocator();
				final int M_Locator_ID = locator.getM_Locator_ID();
				inOutPA.setLineOrderLine(inoutLine, orderLine, M_Locator_ID, order.isSOTrx() ? deliver : Env.ZERO);
				inOutPA.setLineQty(inoutLine, deliver);

				inoutLines.add(inoutLine);
				candidates.addLine(candidate, inoutLine, sched, completeStatus, order);

				inoutLine.setM_AttributeSetInstance_ID(sched.getM_AttributeSetInstance_ID());
			}
			//
			// Case: existing InOutLine found
			// => adjust the quantity
			else
			{
				final BigDecimal inoutLineQtyOld = inoutLine.getMovementQty();
				final BigDecimal inoutLineQtyNew = inoutLineQtyOld.add(deliver);
				inOutPA.setLineQty(inoutLine, inoutLineQtyNew);
			}

			// if (linePerASI)
			// {
			// line.setM_AttributeSetInstance_ID(storage.getM_AttributeSetInstance_ID());
			// }

			logger.debug("ToDeliver=" + qty + "/" + deliver + " - " + inoutLine);
			toDeliver = toDeliver.subtract(deliver);

			storage.subtractQtyOnHand(deliver);
		}         // for each stoarge record

	} // createLine

	/**
	 * Creates the status string for the {@link I_M_ShipmentSchedule#COLUMNNAME_Status} column.
	 *
	 * @param inOutLine
	 * @param shipmentCandidates
	 * @return
	 */
	private String mkStatus(final I_M_InOutLine inOutLine, final IShipmentCandidates shipmentCandidates)
	{
		final CompleteStatus completeStatus = shipmentCandidates.getCompleteStatus(inOutLine);
		if (!IShipmentCandidates.CompleteStatus.OK.equals(completeStatus))
		{
			shipmentCandidates.addStatusInfo(inOutLine, Services.get(IMsgBL.class).getMsg(Env.getCtx(), completeStatus.toString()));
		}

		final PostageFreeStatus postageFreeStatus = shipmentCandidates.getPostageFreeStatus(inOutLine);
		if (!IShipmentCandidates.PostageFreeStatus.OK.equals(postageFreeStatus))
		{
			shipmentCandidates.addStatusInfo(inOutLine, Services.get(IMsgBL.class).getMsg(Env.getCtx(), postageFreeStatus.toString()));
		}
		return shipmentCandidates.getStatusInfos(inOutLine);
	}

	private CachedObjects mkCoToUse(final CachedObjects co)
	{

		if (co == null)
		{
			return new CachedObjects();
		}
		return co;
	}

	private int applyCandidateProcessors(final Properties ctx, final IShipmentCandidates candidates, final CachedObjects cachedObjects, final String trxName)
	{
		return candidateProcessors.processCandidates(ctx, candidates, cachedObjects, trxName);
	}

	@Override
	public void registerCandidateProcessor(final ICandidateProcessor processor)
	{
		candidateProcessors.addCandidateProcessor(processor);
	}

	@Override
	public ArrayKey mkKeyForGrouping(final I_M_ShipmentSchedule sched)
	{
		final boolean includeBPartner = false;
		return mkKeyForGrouping(sched, includeBPartner);
	}

	@Override
	public ArrayKey mkKeyForGrouping(final I_M_ShipmentSchedule sched, final boolean includeBPartner)
	{
		final int productId = sched.getM_Product_ID();
		final de.metas.adempiere.model.I_M_Product product = InterfaceWrapperHelper.create(sched.getM_Product(), de.metas.adempiere.model.I_M_Product.class);

		final int orderLineId;
		if (product.isDiverse())
		{
			// Diverse/misc products can't be merged into one pi
			// because they could represent totally different products.
			// So we are using order line ID (which is unique) to make the group unique.
			orderLineId = sched.getC_OrderLine_ID();
		}
		else
		{
			orderLineId = 0;
		}

		final int bpartnerId;
		final int bpLocId;
		if (includeBPartner)
		{
			final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

			bpartnerId = shipmentScheduleEffectiveBL.getC_BPartner_ID(sched);
			bpLocId = shipmentScheduleEffectiveBL.getC_BP_Location_ID(sched);
		}
		else
		{
			bpartnerId = 0;
			bpLocId = 0;
		}

		return Util.mkKey(productId, orderLineId, bpartnerId, bpLocId);
	}

	@Override
	public void invalidateProducts(
			final Collection<I_C_OrderLine> orderLines,
			final String trxName)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		final Set<Integer> alreadyDone = new HashSet<>();

		for (final I_C_OrderLine currentLine : orderLines)
		{
			final int productId = currentLine.getM_Product_ID();

			if (alreadyDone.contains(productId))
			{
				continue;
			}

			alreadyDone.add(productId);

			if (productId > 0)
			{
				shipmentSchedulePA.invalidateForProduct(productId, trxName);
			}
		}
	}

	@Override
	public void updateBPArtnerAddressOverride(
			final Properties ctx,
			final I_M_ShipmentSchedule sched,
			final String trxName)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final I_C_BPartner bPartner = shipmentScheduleEffectiveValuesBL.getBPartner(sched);
		final I_C_BPartner_Location location = shipmentScheduleEffectiveValuesBL.getBPartnerLocation(sched);
		final I_AD_User user = shipmentScheduleEffectiveValuesBL.getAD_User(sched);

		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
		final String adress = bPartnerBL.mkFullAddress(bPartner,
				InterfaceWrapperHelper.create(location, de.metas.adempiere.model.I_C_BPartner_Location.class),
				user, trxName);

		sched.setBPartnerAddress_Override(adress);
	}

	@Override
	public BigDecimal updateQtyOrdered(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final BigDecimal qtyOrderedOld = shipmentSchedule.getQtyOrdered(); // going to return it in the end
		final BigDecimal qtyOrderedToSet = shipmentScheduleEffectiveBL.getQtyOrdered(shipmentSchedule);
		shipmentSchedule.setQtyOrdered(qtyOrderedToSet);

		// Return the old value
		return qtyOrderedOld;
	}

	private String getDeliveryViaRule(final I_M_ShipmentSchedule sched)
	{
		final String deliveryViaRule = Check.isEmpty(sched.getDeliveryViaRule_Override(), true) ? sched.getDeliveryViaRule() : sched.getDeliveryViaRule_Override();

		return deliveryViaRule;
	}

	@Override
	public I_M_InOut createInOut(
			final Properties ctx,
			final I_C_Order order,
			final Timestamp movementDate,
			final OlAndSched olAndSched,
			final String trxName)
	{
		final MOrder orderPO;
		if (order instanceof MOrder)
		{
			orderPO = (MOrder)order;
		}
		else
		{
			orderPO = InterfaceWrapperHelper.getPO(order);
		}
		orderPO.set_TrxName(trxName); // making sure that inOut is created with 'trxName'
		final I_M_InOut inOut = InterfaceWrapperHelper.create(new MInOut(orderPO, 0, movementDate), I_M_InOut.class);

		final I_M_ShipmentSchedule sched = olAndSched.getSched();

		// Check if the warehouse is overridden in schedule.
		if (sched.getM_Warehouse_Override_ID() > 0)
		{
			inOut.setM_Warehouse_ID(sched.getM_Warehouse_Override_ID());
		}
		else
		{
			inOut.setM_Warehouse_ID(sched.getM_Warehouse_ID());
		}

		final int adUserId = Services.get(IShipmentScheduleEffectiveBL.class).getAD_User_ID(sched);
		if (adUserId >= 0)
		{
			inOut.setAD_User_ID(adUserId);
		}
		inOut.setC_BPartner_ID(Services.get(IShipmentScheduleEffectiveBL.class).getBPartner(sched).getC_BPartner_ID());
		inOut.setC_BPartner_Location_ID(Services.get(IShipmentScheduleEffectiveBL.class).getBPartnerLocation(sched).getC_BPartner_Location_ID());

		inOut.setBPartnerAddress(sched.getBPartnerAddress_Override());
		inOut.setDeliveryViaRule(getDeliveryViaRule(sched));

		final I_C_OrderLine ol = olAndSched.getOl();
		inOut.setM_Shipper_ID(ol.getM_Shipper_ID());

		return inOut;
	}

	@Override
	public boolean isChangedByUpdateProcess(final I_M_ShipmentSchedule sched)
	{
		final Boolean isUpdateProcess = InterfaceWrapperHelper.getDynAttribute(sched, DYNATTR_ProcessedByBackgroundProcess);
		return isUpdateProcess == null ? false : isUpdateProcess.booleanValue();
	}

	@Override
	public I_C_UOM getC_UOM(final I_M_ShipmentSchedule sched)
	{
		Check.assumeNotNull(sched, "sched not null");

		// FIXME: introduce M_ShipmentSchedule.C_UOM_ID
		// See http://dewiki908/mediawiki/index.php/05565_Introduce_M_ShipmentSchedule.C_UOM_ID_%28107483088069%29

		// return sched.getC_OrderLine().getC_UOM();
		return sched.getM_Product().getC_UOM();
	}

	@Override
	public I_C_UOM getC_UOM_For_ShipmentLine(final I_M_ShipmentSchedule sched)
	{
		Check.assumeNotNull(sched, "sched not null");

		// return sched.getC_OrderLine().getC_UOM(); // see javadoc
		return sched.getM_Product().getC_UOM();
	}

	@Override
	public boolean isSchedAllowsConsolidate(final I_M_ShipmentSchedule sched)
	{
		// task 08756: we don't really care for the ol's partner, but for the partner who will actually receive the shipment.
		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final boolean bpAllowsConsolidate = bPartnerBL.isAllowConsolidateInOutEffective(shipmentScheduleEffectiveBL.getBPartner(sched), true);
		if (!bpAllowsConsolidate)
		{
			logger.debug("According to the effective C_BPartner of shipment candidate '" + sched + "', consolidation into one shipment is not allowed");
			return false;
		}
		final I_C_Order order = InterfaceWrapperHelper.create(sched.getC_Order(), I_C_Order.class);

		final String docSubType = order.getC_DocType().getDocSubType();
		final boolean isPrePayOrder = de.metas.prepayorder.model.I_C_DocType.DOCSUBTYPE_PrepayOrder_metas.equals(docSubType)
				|| X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(docSubType);
		if (isPrePayOrder)
		{
			logger.debug("Because '" + order + "' is a prepay order, consolidation into one shipment is not allowed");
			return false;
		}

		final boolean isCustomFreightCostRule = isCustomFreightCostRule(order);
		if (isCustomFreightCostRule)
		{
			logger.debug("Because '" + order + "' has not the standard freight cost rule,  consolidation into one shipment is not allowed");
			return false;
		}

		return true;
	}

	private boolean isCustomFreightCostRule(final I_C_Order order)
	{
		return X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(order.getFreightCostRule())
		// || X_C_Order.FREIGHTCOSTRULE_FreightIncluded.equals(order.getFreightCostRule()) // 07973: included freight cost rule shall no longer be considered "custom"
		;
	}

	@Override
	public IAggregationKeyBuilder<I_M_ShipmentSchedule> mkShipmentHeaderAggregationKeyBuilder()
	{
		return new ShipmentScheduleHeaderAggregationKeyBuilder();
	}

	/**
	 *
	 * @param sched
	 * @return
	 * @task 08336
	 */
	private boolean updateProcessedFlag(final I_M_ShipmentSchedule sched)
	{
		final boolean newProcessed = sched.getQtyToDeliver_Override().signum() <= 0 && sched.getQtyReserved().signum() <= 0;

		sched.setProcessed(newProcessed);

		return newProcessed;
	}

	@Override
	public void addShipmentScheduleQtyUpdateListener(final IShipmentScheduleQtyUpdateListener listener)
	{
		listeners.addShipmentScheduleQtyUpdateListener(listener);
	}

	@Override
	public void closeShipmentSchedule(I_M_ShipmentSchedule schedule)
	{
		final BigDecimal qtyDelivered = schedule.getQtyDelivered();

		schedule.setQtyOrdered_Override(qtyDelivered);

		InterfaceWrapperHelper.save(schedule);
	}
}
