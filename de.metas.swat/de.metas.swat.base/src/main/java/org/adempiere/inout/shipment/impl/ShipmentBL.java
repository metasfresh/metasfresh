package org.adempiere.inout.shipment.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.inout.shipment.IShipmentBL;
import org.adempiere.inout.shipment.ShipmentParams;
import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.inout.util.ShipmentCandidates;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Warehouse;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.engine.IDocActionBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.impl.ShipmentGenerateException;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;

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
	@VisibleForTesting
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
