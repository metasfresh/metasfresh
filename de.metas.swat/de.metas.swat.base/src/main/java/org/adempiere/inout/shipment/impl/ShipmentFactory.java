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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.adempiere.inout.service.IInOutPA;
import org.adempiere.inout.shipment.ShipmentParams;
import org.adempiere.inout.util.IShipmentCandidates;
import org.adempiere.inout.util.IShipmentCandidates.CompleteStatus;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_Order;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;

/**
 * Tool class that does the actual work of creating {@link I_M_InOut}s and {@link I_M_InOutLine}s. It caches all required POs (e.g. Orders) to make sure the need to be loaded from DB only once.
 *
 * @author ts
 *
 */
public class ShipmentFactory
{

	private static final Logger logger = LogManager.getLogger(ShipmentFactory.class);

	// map ( I_M_InOut ) -> ( set of line numbers that are already used ).
	// this map is only used with consolidated shipments.
	private final Map<I_M_InOut, SortedSet<Integer>> inOut2LineNos = new HashMap<I_M_InOut, SortedSet<Integer>>();

	/**
	 * Maps inOuts to their value (i.e. what will show up in the invoice) in order to be later able to figure out if the value is above the postage free amount.
	 */
	private final Map<I_M_InOut, BigDecimal> inOut2Value = new HashMap<I_M_InOut, BigDecimal>();

	/**
	 * contains those lines that won'tbe delivered (because of the postage free amount)
	 */
	private final Set<OlAndSched> invalidLines = new HashSet<OlAndSched>();

	private final Map<ArrayKey, I_M_InOut> locOrder2InOut = new HashMap<ArrayKey, I_M_InOut>();

	private final Map<ArrayKey, I_M_InOut> locShipper2InOut = new HashMap<ArrayKey, I_M_InOut>();

	private final Map<OlAndSched, I_M_InOut> olAndSched2InOut = new HashMap<OlAndSched, I_M_InOut>();

	private final Map<OlAndSched, I_M_InOutLine> olAndSched2InOutLine = new HashMap<OlAndSched, I_M_InOutLine>();

	// map ( C_Order_ID ) -> ( Order )
	private final Map<Integer, I_C_Order> orderId2Order = new HashMap<Integer, I_C_Order>();

	public void addAllToCandidates(final IShipmentCandidates candidates)
	{

		assert olAndSched2InOut.keySet().equals(olAndSched2InOutLine.keySet());

		final Set<I_M_InOut> alreadyAdded = new HashSet<I_M_InOut>();

		for (final OlAndSched olAndSched : olAndSched2InOutLine.keySet())
		{
			if (isInvalid(olAndSched))
			{
				continue;
			}

			final I_M_InOut inOut = olAndSched2InOut.get(olAndSched);
			if (!alreadyAdded.contains(inOut))
			{
				candidates.addInOut(inOut);
				alreadyAdded.add(inOut);
			}

			final I_M_InOutLine inOutLine = olAndSched2InOutLine.get(olAndSched);

			final I_C_OrderLine orderLine = olAndSched.getOl();
			final I_M_ShipmentSchedule sched = olAndSched.getSched();
			final I_C_Order order = orderId2Order.get(orderLine.getC_Order_ID());
			candidates.addLine(inOut, inOutLine, sched, CompleteStatus.OK, order);
		}
	}

	private I_M_InOut cacheInOutLocOrder(
			final Properties ctx,
			final OlAndSched olAndSched,
			final ShipmentParams shipmentParams,
			final I_C_Order order,
			final String trxName)
	{

		final I_M_ShipmentSchedule sched = olAndSched.getSched();
		final I_C_OrderLine ol = olAndSched.getOl();

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		int c_BPartner_Location_ID = shipmentScheduleEffectiveBL.getBPartnerLocation(sched).getC_BPartner_Location_ID();

		int m_Warehouse_ID = Services.get(IWarehouseAdvisor.class).evaluateWarehouse(ol).getM_Warehouse_ID();

		final ArrayKey key = createKey(ctx,
				c_BPartner_Location_ID,
				m_Warehouse_ID,
				shipmentParams,
				order,
				trxName);

		I_M_InOut inOut = locOrder2InOut.get(key);
		if (inOut == null)
		{
			final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

			inOut = shipmentScheduleBL.createInOut(ctx, order, shipmentParams.getMovementDate(), olAndSched, trxName);
			locOrder2InOut.put(key, inOut);
		}

		return inOut;
	}

	private ArrayKey createKey(
			final Properties ctx,
			final int bpartnerLocationId,
			final int warehouseId,
			final ShipmentParams shipmentParams,
			final I_C_Order order,
			final String trxName)
	{
		// tsa: WARNING: please use Object[] instead of int[] as type because else MiscUtils.mkKey will not work correctly
		// see org.adempiere.util.MiscUtilsArrayKeyTests.arraysKeyWithIntAreEqual2()
		final Object[] keyParams = {
				// need to include the bill location because we never want to put stuff
				// into one package that is paid for by two different locations
				order != null ? order.getBill_Location_ID() : 0, //
				bpartnerLocationId, //
				order.getC_Order_ID(), //
				warehouseId //
		};

		final ArrayKey key = Util.mkKey(keyParams);

		return key;
	}

	private I_M_InOut cacheInOutLocShipper(
			final Properties ctx,
			final OlAndSched olAndSched,
			final ShipmentParams shipmentParams,
			final I_C_Order order,
			final String trxName)
	{
		final I_M_ShipmentSchedule sched = olAndSched.getSched();
		final I_C_OrderLine ol = olAndSched.getOl();

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		// tsa: WARNING: please use Object[] instead of int[] as type because else MiscUtils.mkKey will not work correctly
		// see org.adempiere.util.MiscUtilsArrayKeyTests.arraysKeyWithIntAreEqual2()
		final Object[] keyParams = {
				// need to include the bill location because we never want to put stuff
				// into one package that is paid for by two different locations
				order.getBill_Location_ID(), //
				shipmentScheduleEffectiveBL.getBPartnerLocation(sched).getC_BPartner_Location_ID(), //
				ol.getM_Shipper_ID(), //
				Services.get(IWarehouseAdvisor.class).evaluateWarehouse(ol).getM_Warehouse_ID() //
		};

		final ArrayKey key = Util.mkKey(keyParams);

		I_M_InOut inOut = locShipper2InOut.get(key);

		if (inOut == null)
		{
			final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

			inOut = shipmentScheduleBL.createInOut(ctx, order, shipmentParams.getMovementDate(), olAndSched, trxName);
			locShipper2InOut.put(key, inOut);
		}
		return inOut;
	}

	private I_C_Order cacheOrder(final String trxName, final Map<Integer, I_C_Order> orderCache, final I_C_OrderLine ol)
	{
		final int orderId = ol.getC_Order_ID();

		I_C_Order order = orderCache.get(orderId);
		if (orderCache.containsKey(orderId))
		{
			return order;
		}

		order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);
		orderCache.put(orderId, order);

		return order;
	}

	public I_M_InOut getInOut(final OlAndSched olAndSched)
	{
		return olAndSched2InOut.get(olAndSched);
	}

	public I_M_InOutLine getInOutLine(final OlAndSched olAndSched)
	{
		return olAndSched2InOutLine.get(olAndSched);
	}

	public BigDecimal getValueOfInOut(final I_M_InOut inOut)
	{
		return inOut2Value.get(inOut);
	}

	public void invalidate(final OlAndSched olAndSched)
	{
		invalidLines.add(olAndSched);
	}

	private boolean isConsolidate(final OlAndSched olAndSched, final ShipmentParams shipmentParams, final String trxName)
	{
		if (!shipmentParams.isConsolidateDocument())
		{
			logger.debug("According to shipment params '" + shipmentParams + "', consolidation into one shipment is not allowed");
			return false;
		}
		return Services.get(IShipmentScheduleBL.class).isSchedAllowsConsolidate(olAndSched.getSched());
	}

	public boolean isInvalid(final OlAndSched olAndSched)
	{

		return invalidLines.contains(olAndSched);
	}

	private I_M_InOut mkInOut(
			final Properties ctx,
			final OlAndSched olAndSched,
			final ShipmentParams shipmentParams, final boolean consolidate,
			final String trxName)
	{
		final I_C_Order order = cacheOrder(trxName, orderId2Order, olAndSched.getOl());

		// create the shipment if necessary.
		I_M_InOut inOut = null;

		if (consolidate)
		{
			inOut = cacheInOutLocShipper(ctx, olAndSched, shipmentParams, order, trxName);
		}
		else
		{
			inOut = cacheInOutLocOrder(ctx, olAndSched, shipmentParams, order, trxName);
		}
		return inOut;
	}

	public I_M_InOutLine mkInOutLine(
			final Properties ctx,
			final OlAndSched olAndSched,
			final ShipmentParams shipmentParams,
			final String trxName)
	{
		final I_C_OrderLine ol = olAndSched.getOl();

		final boolean consolidate = isConsolidate(olAndSched, shipmentParams, trxName);

		final I_M_InOut inOut = mkInOut(ctx, olAndSched, shipmentParams, consolidate, trxName);

		final IInOutPA inOutPA = Services.get(IInOutPA.class);

		BigDecimal movementQty;
		if (shipmentParams.isIgnorePostageFreeamount())
		{
			movementQty = olAndSched.getSched().getQtyDeliverable();
		}
		else
		{
			movementQty = olAndSched.getSched().getQtyToDeliver();
		}

		//
		// create the inOutLine
		final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(inOutPA.createNewLine(inOut, trxName), I_M_InOutLine.class);

		inOutPA.setLineOrderLine(inOutLine, ol, 0, movementQty);
		inOutPA.setLineQty(inOutLine, movementQty);
		mkLineNo(ol, consolidate, inOut, inOutLine);

		final BigDecimal valueOfLine = ol.getPriceActual().multiply(movementQty);

		final BigDecimal valueOfInOut = inOut2Value.get(inOut);
		if (valueOfInOut == null)
		{
			inOut2Value.put(inOut, valueOfLine);
		}
		else
		{
			inOut2Value.put(inOut, valueOfInOut.add(valueOfLine));
		}

		olAndSched2InOut.put(olAndSched, inOut);
		olAndSched2InOutLine.put(olAndSched, inOutLine);

		return inOutLine;
	}

	/**
	 * Assign a <code>M_InOut.Line</code> value to the given <code>inOutLine</code>.
	 *
	 * @param ol
	 * @param consolidate
	 * @param inOut
	 * @param inOutLine
	 */
	private void mkLineNo(
			final I_C_OrderLine ol,
			final boolean consolidate,
			final I_M_InOut inOut,
			final I_M_InOutLine inOutLine)
	{
		if (!consolidate)
		{
			inOutLine.setLine(ol.getLine());
			return;
		}

		SortedSet<Integer> lineNos = inOut2LineNos.get(inOut);
		if (lineNos == null)
		{
			lineNos = new TreeSet<Integer>();
			inOut2LineNos.put(inOut, lineNos);
		}

		int maxLine = 10;
		if (!lineNos.isEmpty())
		{
			maxLine += lineNos.last();
		}
		inOutLine.setLine(maxLine);
		inOut2LineNos.get(inOut).add(maxLine);
	}

	public void mkCustomInOutLine(Properties ctx, int bpartnerLocationId, int warehouseId, I_M_Product packingProduct, BigDecimal qtyTU, ShipmentParams shipmentParams, String trxName)
	{
		final IInOutPA inOutPA = Services.get(IInOutPA.class);
		final ArrayKey key = createKey(ctx, bpartnerLocationId, warehouseId, shipmentParams, null, trxName);

		final I_M_InOut inOut = locOrder2InOut.get(key);

		//
		// create the inOutLine
		final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(inOutPA.createNewLine(inOut, trxName), I_M_InOutLine.class);

		inOutPA.setLineQty(inOutLine, qtyTU);
		// TODO Auto-generated method stub

	}
}
