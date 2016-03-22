package org.adempiere.inout.shipment.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.inout.service.IInOutPA;
import org.adempiere.inout.shipment.IShipmentBL;
import org.adempiere.inout.shipment.ShipmentParams;
import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.inout.util.ShipmentCandidates;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Warehouse;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.document.engine.IDocActionBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.impl.ShipmentGenerateException;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;

public class ShipmentBL implements IShipmentBL
{
	private static final Logger logger = LogManager.getLogger(ShipmentBL.class);

	@Override
	public void addShipments(
			final Properties ctx,
			final ShipmentParams shipmentParams,
			final IShipmentCandidates candidates,
			final String trxName)
	{
		final ShipmentFactory factory = new ShipmentFactory();

		// try to lock schedules
		final List<Integer> olsAndSchedsLocked = new ArrayList<Integer>();

		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		final List<OlAndSched> olsAndSchedsToProcess =
				filterDatePromised(
						shipmentParams,
						retrieveOlsAndScheds(ctx, shipmentParams, trxName));

		final List<OlAndSched> olsAndSchedsToShip;

		if (isRecompute(shipmentParams))
		{
			logger.info("Params indicate to prefer bPartner '" + shipmentParams.getBPartnerId()
					+ "' or to use different movement date '" + shipmentParams.getMovementDate()
					+ "', so we need to compute their shipment quantities now.");

			shipmentScheduleBL.updateSchedules(ctx, olsAndSchedsToProcess, false, shipmentParams.getMovementDate(), null, trxName);

			if (isSelectedOrderLineIds(shipmentParams))
			{
				olsAndSchedsToShip = filterSelectedOLs(shipmentParams, olsAndSchedsToProcess);
			}
			else if (shipmentParams.getBPartnerId() != 0)
			{
				olsAndSchedsToShip = filterBPartner(shipmentParams, olsAndSchedsToProcess);
			}
			else
			{
				olsAndSchedsToShip = olsAndSchedsToProcess;
			}
		}
		else
		{
			olsAndSchedsToShip = olsAndSchedsToProcess;
		}

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		try
		{
			final List<OlAndSched> finalList = shipmentSchedulePA.createLocksForShipmentRun(
					olsAndSchedsToShip,
					shipmentParams.getAdPInstanceId(),
					shipmentParams.getAdUserId(),
					trxName);

			logger.info("Going to create shipments from " + finalList.size() + " shipment schedule entries");

			mkInOutsDefault(ctx, shipmentParams, factory, finalList, trxName);

			if (!shipmentParams.isIgnorePostageFreeamount())
			{
				int counter = 0;
				while (redistribute(ctx, finalList, factory, shipmentParams, trxName) > 0)
				{
					counter++;
					if (counter > olsAndSchedsLocked.size())
					{
						throw new RuntimeException("Redistribution doesn't finish. Aparently there's a bug.");
					}
				}
			}
			factory.addAllToCandidates(candidates);

		}
		finally
		{
			shipmentSchedulePA.markLocksForShipmentRunProcessed(
					shipmentParams.getAdPInstanceId(),
					shipmentParams.getAdUserId(),
					trxName);
		}
	}

	/**
	 * Creates shipments and adds them to 'candidates' based on the data from 'olsAndScheds'.
	 *
	 * @param shipmentParams
	 * @param candidates
	 * @param olsAndScheds
	 * @param trxName
	 */
	private void mkInOutsDefault(
			final Properties ctx,
			final ShipmentParams shipmentParams,
			final ShipmentFactory factory,
			final List<OlAndSched> olsAndScheds,
			final String trxName)
	{

		for (final OlAndSched olAndSched : olsAndScheds)
		{

			final I_M_ShipmentSchedule sched = olAndSched.getSched();

			final boolean ignorePostageFreeAmt = shipmentParams
					.isIgnorePostageFreeamount();

			if (hasQty(sched, ignorePostageFreeAmt))
			{
				factory.mkInOutLine(ctx, olAndSched, shipmentParams, trxName);
			}
		}
	}

	private boolean hasQty(final I_M_ShipmentSchedule sched, final boolean ignorePostageFreeAmt)
	{
		if (ignorePostageFreeAmt)
		{
			return sched.getQtyDeliverable().signum() > 0;
		}
		return sched.getQtyToDeliver().signum() > 0;

	}

	/**
	 *
	 * @param olsAndScheds
	 * @param factory
	 * @param shipmentParams are required in case a new inOutLine needs to be made
	 * @param trxName
	 * @return
	 */
	private int redistribute(
			final Properties ctx,
			final List<OlAndSched> olsAndScheds,
			final ShipmentFactory factory,
			final ShipmentParams shipmentParams,
			final String trxName)
	{

		// map (productId, asiId) -> (Qty to redistribute)
		final Map<ArrayKey, BigDecimal> qtys = evalPostageFreeAmt(olsAndScheds, factory);
		final int result = qtys.size();

		if (result == 0)
		{
			// there's nothing to redistribute
			return 0;
		}

		for (final OlAndSched olAndSched : olsAndScheds)
		{
			final I_C_OrderLine ol = olAndSched.getOl();
			final IDeliverRequest deliverRequest = olAndSched.getDeliverRequest();
			final I_M_ShipmentSchedule sched = olAndSched.getSched();

			if (sched.getQtyToDeliver().compareTo(deliverRequest.getQtyOrdered()) >= 0)
			{
				// the current line doesn't need redistribution
				continue;
			}
			if (factory.isInvalid(olAndSched))
			{
				// the current line has already been invalidated
				continue;
			}

			final ArrayKey currentKey = Util.mkKey(ol.getM_Product_ID(), ol.getM_AttributeSetInstance_ID());

			if (!qtys.containsKey(currentKey))
			{
				// there is nothing in store for the current line
				continue;
			}

			I_M_InOutLine inOutLine = factory.getInOutLine(olAndSched);
			if (inOutLine == null)
			{
				inOutLine = factory.mkInOutLine(ctx, olAndSched, shipmentParams, trxName);
			}

			final BigDecimal qtyCurrent = inOutLine.getMovementQty();
			final BigDecimal qtyDemand = deliverRequest.getQtyOrdered().subtract(qtyCurrent);

			final BigDecimal qtyAvail = qtys.get(currentKey);

			BigDecimal qtyNew;
			if (qtyDemand.compareTo(qtyAvail) >= 0)
			{
				// the full available qty is given to the current line
				qtyNew = qtyCurrent.add(qtyAvail);
				qtys.remove(currentKey);
			}
			else
			{
				qtyNew = qtyCurrent.add(qtyDemand);
				final BigDecimal newQtyAvail = qtyAvail.subtract(qtyDemand);
				qtys.put(currentKey, newQtyAvail);
			}

			final IInOutPA inoutPA = Services.get(IInOutPA.class);
			inoutPA.setLineQty(inOutLine, qtyNew);
		}
		return result;
	}

	private Map<ArrayKey, BigDecimal> evalPostageFreeAmt(
			final List<OlAndSched> olsAndScheds,
			final ShipmentFactory factory)
	{
		// map (productId, asiId) -> (Qty to redistribute)
		final Map<ArrayKey, BigDecimal> qtys = new HashMap<ArrayKey, BigDecimal>();

		for (final OlAndSched olAndSched : olsAndScheds)
		{
			if (factory.isInvalid(olAndSched))
			{
				// nothing to to
				continue;
			}

			final I_M_InOut inOut = factory.getInOut(olAndSched);
			if (inOut == null)
			{
				// nothing to do
				continue;
			}

			final BigDecimal valueOfInOut = factory.getValueOfInOut(inOut);

			if (valueOfInOut.compareTo(olAndSched.getSched().getPostageFreeAmt()) >= 0)
			{
				// nothing to do
				continue;
			}

			final I_M_InOutLine inOutLine = factory.getInOutLine(olAndSched);

			final ArrayKey key = Util.mkKey(inOutLine.getM_Product_ID(), inOutLine.getM_AttributeSetInstance_ID());

			final BigDecimal qtyCollected = qtys.get(key);

			if (qtyCollected == null)
			{
				qtys.put(key, inOutLine.getMovementQty());
			}
			else
			{
				qtys.put(key, qtyCollected.add(inOutLine.getMovementQty()));
			}

			factory.invalidate(olAndSched);
		}
		return qtys;
	}

	private List<OlAndSched> filterDatePromised(
			final ShipmentParams shipmentParams,
			final List<OlAndSched> olsAndScheds)
	{

		if (shipmentParams.getDatePromised() == null)
		{
			return olsAndScheds;
		}

		final List<OlAndSched> result = new ArrayList<OlAndSched>();
		for (final OlAndSched olAndSched : olsAndScheds)
		{

			if (isDatePromisedOk(shipmentParams, olAndSched))
			{
				result.add(olAndSched);
			}
		}
		return result;
	}

	/**
	 * Computes the {@link OlAndSched} instances that are later used to create shipments.
	 *
	 * @param params
	 * @param trxName
	 * @return
	 */
	/* note: this method is package visible for testing purposes. */
	List<OlAndSched> retrieveOlsAndScheds(
			final Properties ctx,
			final ShipmentParams params,
			final String trxName)
	{
		List<OlAndSched> list;

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		if (isSelectedOrderLineIds(params))
		{
			// only evaluate those olsAndScheds that have selected order lines.
			final List<OlAndSched> allOlsAndScheds =
					filterWarehouse(
							params,
							shipmentSchedulePA.retrieveForShipmentRun(params.isIncludeWhenIncompleteInOutExists(), trxName));

			if (isRecompute(params))
			{
				// we will later on compute new qtys for the scheds in
				// 'allOlsAndScheds', so we need them all
				list = allOlsAndScheds;
			}
			else
			{
				//
				list = filterSelectedOLs(params, allOlsAndScheds);
			}
		}
		else if (params.getBPartnerId() == 0 || (isAlternativeMovementDate(params) && !params.isPreferBPartner()))
		{
			// either no particular bPartner has been selected (so select and
			// evaluate all) or a movement date is set (so select and recompute all)
			list = filterWarehouse(
					params,
					shipmentSchedulePA.retrieveForShipmentRun(params.isIncludeWhenIncompleteInOutExists(), trxName));
		}
		else
		{
			list = filterWarehouse(
					params,
					shipmentSchedulePA.retrieveOlAndSchedsForBPartner(params.getBPartnerId(), params.isIncludeWhenIncompleteInOutExists(), trxName));
		}
		return list;
	}

	private boolean isSelectedOrderLineIds(final ShipmentParams params)
	{
		return params.getSelectedOrderLineIds() != null;
	}

	private boolean isRecompute(final ShipmentParams params)
	{
		return isAlternativeMovementDate(params) || isPreferredBPartner(params);
	}

	private boolean isPreferredBPartner(final ShipmentParams params)
	{
		return params.getBPartnerId() != 0 && params.isPreferBPartner();
	}

	private boolean isAlternativeMovementDate(final ShipmentParams params)
	{
		return params.getMovementDate() != null && !MiscUtils.isToday(params.getMovementDate());
	}

	private List<OlAndSched> filterSelectedOLs(
			final ShipmentParams shipmentParams,
			final List<OlAndSched> allOlsAndScheds)
	{
		if (shipmentParams.getSelectedOrderLineIds() == null)
		{
			return allOlsAndScheds;
		}

		final List<OlAndSched> result = new ArrayList<OlAndSched>();

		for (final OlAndSched olAndSched : allOlsAndScheds)
		{
			if (shipmentParams.getSelectedOrderLineIds().contains(olAndSched.getOl().getC_OrderLine_ID()))
			{
				result.add(olAndSched);
			}
		}
		return result;
	}

	private List<OlAndSched> filterBPartner(
			final ShipmentParams shipmentParams,
			final List<OlAndSched> allOlsAndScheds)
	{

		if (shipmentParams.getBPartnerId() == 0)
		{
			return allOlsAndScheds;
		}

		final List<OlAndSched> result = new ArrayList<OlAndSched>();

		for (final OlAndSched olAndSched : allOlsAndScheds)
		{
			if (shipmentParams.getBPartnerId() == olAndSched.getOl().getC_BPartner_ID())
			{
				result.add(olAndSched);
			}
		}
		return result;
	}

	private List<OlAndSched> filterWarehouse(
			final ShipmentParams shipmentParams,
			final List<OlAndSched> allOlsAndScheds)
	{

		if (shipmentParams.getWarehouseId() == 0)
		{
			return allOlsAndScheds;
		}

		final List<OlAndSched> result = new ArrayList<OlAndSched>();

		for (final OlAndSched olAndSched : allOlsAndScheds)
		{
			final I_M_Warehouse warehouse = Services.get(IShipmentScheduleEffectiveBL.class).getWarehouse(olAndSched.getSched());

			if (shipmentParams.getWarehouseId() == warehouse.getM_Warehouse_ID())
			{
				result.add(olAndSched);
			}
		}
		return result;
	}

	private boolean isDatePromisedOk(final ShipmentParams shipmentParams, final OlAndSched olAndSched)
	{

		final Timestamp datePromised = shipmentParams.getDatePromised();
		if (datePromised == null)
		{
			return true;
		}

		return datePromised.after(olAndSched.getOl().getDatePromised());
	}

	@Override
	public Iterator<I_M_InOut> generateShipments(final Properties ctx, final ShipmentParams params, final String docAction, final String trxName)
	{
		try
		{
			final IShipmentCandidates candidates = new ShipmentCandidates();
			addShipments(ctx, params, candidates, trxName);

			final List<I_M_InOut> shipments = completeShipments(candidates, params, docAction, trxName);
			return shipments.iterator();
		}
		catch (Exception e)
		{
			Services.get(IShipmentSchedulePA.class).deleteLocksForShipmentRun(params.getAdPInstanceId(), params.getAdUserId());

			throw new ShipmentGenerateException(e);
		}
	}

	/**
	 * Complete Shipments
	 */
	private List<I_M_InOut> completeShipments(final IShipmentCandidates candidates, final ShipmentParams params, final String docAction, final String trxName)
	{
		//
		// metas: first select the shipments that we really want to create, then create them
		final int bpartnerId = params.getBPartnerId();
		final List<I_M_InOut> shipmentsToCreate = new ArrayList<I_M_InOut>();
		for (final I_M_InOut shipmentCandidate : candidates.getCandidates())
		{
			if (bpartnerId > 0 && bpartnerId != shipmentCandidate.getC_BPartner_ID())
			{
				// a different bPartner was selected by the user, so this inOut won't be created
				continue;
			}

			shipmentsToCreate.add(shipmentCandidate);
		}

		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);

		for (final I_M_InOut currentShipment : shipmentsToCreate)
		{
			InterfaceWrapperHelper.setTrxName(currentShipment, trxName);
			InterfaceWrapperHelper.save(currentShipment);

			for (final I_M_InOutLine inOutLine : candidates.getLines(currentShipment))
			{
				InterfaceWrapperHelper.setTrxName(inOutLine, trxName);
				inOutLine.setM_InOut_ID(currentShipment.getM_InOut_ID());
				InterfaceWrapperHelper.save(inOutLine);
			}

			if (!docActionBL.processIt(currentShipment, docAction))
			{
				logger.warn("Failed: " + currentShipment);
			}
			InterfaceWrapperHelper.save(currentShipment);
		}

		return shipmentsToCreate;
	} // completeOrder

}
