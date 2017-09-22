package org.adempiere.inout.util;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.collections.IdentityHashSet;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MInOut;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.X_C_Order;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_Order;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;

/**
 * Helper class to manage the shipments that might actually be created in the end.
 * 
 * @author ts
 * 
 */
public class ShipmentCandidates implements IShipmentCandidates
{

	static final Logger logger = LogManager.getLogger(ShipmentCandidates.class);

	/**
	 * List to store the shipments before it is decided if they are persisted to the database.
	 */
	private final List<I_M_InOut> orderedCandidates = new ArrayList<>();

	/**
	 * Maps inOuts to their inOutLines. Usually this is done using the inOut's ID stored in the inOutLine. But as the inOut hasn't been saved yet, it doesn't have an ID.
	 */
	private final Map<I_M_InOut, List<I_M_InOutLine>> inOut2Line = new HashMap<>();

	private final Map<I_M_InOutLine, I_M_InOut> line2InOut = new HashMap<>();

	private final Map<I_M_InOutLine, StringBuilder> line2StatusInfo = new HashMap<>();

	/**
	 * Contains the quantities that would be delivered when postage free amount and order delivery rules where ignored.
	 */
	private final Map<Integer, BigDecimal> olId2QtyDeliverable = new HashMap<Integer, BigDecimal>();

	private final Map<I_M_InOutLine, CompleteStatus> line2CompleteStatus = new HashMap<>();

	private final Map<I_M_InOutLine, OverallStatus> line2OverallStatus = new HashMap<>();

	/**
	 * Used to keep track of which inOutLine's order has which delivery rule.
	 * 
	 * @see {@link #updateCompleteStatus()}
	 */
	private final Map<MOrder, Set<I_M_InOutLine>> order2InOutLine = new HashMap<>();

	private final Map<Integer, MOrderLine> orderLineCache;

	private final Map<Integer, I_M_InOutLine> orderLineId2InOutLine = new HashMap<>();

	private final Map<I_M_InOutLine, I_M_ShipmentSchedule> line2sched = new HashMap<>();

	/**
	 * Used when multiple orders need to be consolidated to one shipment
	 */
	private final Map<ArrayKey, MInOut> shipperKey2Candidate = new HashMap<ArrayKey, MInOut>();

	/**
	 * Used when one shipment per order is required
	 */
	private final Map<ArrayKey, MInOut> orderKey2Candidate = new HashMap<ArrayKey, MInOut>();

	/**
	 * Use this constructor if you won't need the method {@link ShipmentCandidates#updatePostageFreeStatus(boolean)}.
	 */
	public ShipmentCandidates()
	{
		orderLineCache = null;
	}

	public ShipmentCandidates(final Map<Integer, I_C_OrderLine> myOrderLineCache)
	{
		orderLineCache = new HashMap<Integer, MOrderLine>();

		for (final Integer orderLineId : myOrderLineCache.keySet())
		{
			final I_C_OrderLine orderLine = myOrderLineCache.get(orderLineId);
			final MOrderLine orderLinePO = InterfaceWrapperHelper.getPO(orderLine);
			orderLineCache.put(orderLineId, orderLinePO);
		}
	}

	@Override
	public void addInOut(final I_M_InOut inOut)
	{
		if (inOut == null)
		{
			throw new NullPointerException("inOut");
		}
		if (orderedCandidates.contains(inOut))
		{
			throw new IllegalArgumentException("Each input may be added only once");
		}

		orderedCandidates.add(inOut);

		final ArrayKey shipperKey = Util.mkKey(
				inOut.getBPartnerAddress(),
				inOut.getM_Warehouse_ID(),
				inOut.getM_Shipper_ID());
		shipperKey2Candidate.put(shipperKey, getPO(inOut));

		final ArrayKey orderKey = Util.mkKey(
				inOut.getBPartnerAddress(),
				inOut.getM_Warehouse_ID(),
				inOut.getC_Order_ID());
		orderKey2Candidate.put(orderKey, getPO(inOut));

		final List<I_M_InOutLine> inOutLines = new ArrayList<>();
		inOut2Line.put(inOut, inOutLines);
	}

	private MInOut getPO(final I_M_InOut inOut)
	{
		final MInOut inOutPO = InterfaceWrapperHelper.getPO(inOut);
		return inOutPO;
	}

	@Override
	public void addLine(
			final I_M_InOut inOut,
			final I_M_InOutLine inOutLine,
			final I_M_ShipmentSchedule sched,
			final CompleteStatus completeStatus,
			final I_C_Order order)
	{
		Check.assumeNotNull(inOut, "inOut not null");
		Check.assumeNotNull(inOutLine, "inOutLine not null");
		Check.assumeNotNull(sched, "sched not null");
		Check.assumeNotNull(completeStatus, "completeStatus not null");

		if (!orderedCandidates.contains(inOut))
		{
			throw new IllegalStateException("inOut needs to be added using 'addInOut' first"
					+ "\n InOut: " + inOut
					+ "\n orderedCandidates: " + orderedCandidates);
		}
		if (inOutLine.getC_OrderLine_ID() < 1)
		{
			throw new IllegalStateException("inOutLine needs to have a C_OrderLine_ID > 0"
					+ "\n inOutLine: " + inOutLine);
		}
		if (CompleteStatus.INCOMPLETE_ORDER.equals(completeStatus))
		{
			throw new IllegalArgumentException("completeStatus may not be " + CompleteStatus.INCOMPLETE_ORDER + " (this will be figured out later by this class)");
		}

		inOut2Line.get(inOut).add(inOutLine);
		line2InOut.put(inOutLine, inOut);

		// store the inoutLine's orderId and status as well to support our
		// later purge
		line2CompleteStatus.put(inOutLine, completeStatus);

		final MOrder orderPO = InterfaceWrapperHelper.getPO(order);

		Set<I_M_InOutLine> inOutLines = order2InOutLine.get(orderPO);
		if (inOutLines == null)
		{
			inOutLines = new HashSet<I_M_InOutLine>();
			order2InOutLine.put(orderPO, inOutLines);
		}
		inOutLines.add(inOutLine);

		//
		// C_OrderLine_ID to M_InOutLine mapping
		{
			final I_M_InOutLine inOutLine_Old = orderLineId2InOutLine.put(inOutLine.getC_OrderLine_ID(), inOutLine);
			if (inOutLine_Old != null && inOutLine_Old != inOutLine)
			{
				throw new IllegalArgumentException("An InOutLine was already set for order line in orderLineId2InOutLine mapping"
						+ "\n InOutLine: " + inOutLine
						+ "\n InOutLine (old): " + inOutLine_Old
						+ "\n orderLineId2InOutLine (after change): " + orderLineId2InOutLine);
			}
		}

		line2sched.put(inOutLine, sched);
	}

	public void removeLine(final I_M_InOutLine inOutLine)
	{
		if (inOutLine == null)
		{
			throw new NullPointerException("inOutLine");
		}

		final I_M_InOut inOut = line2InOut.remove(inOutLine);
		if (inOut == null)
		{
			throw new IllegalStateException("inOutLine wasn't in line2InOut");
		}

		boolean success = inOut2Line.get(inOut).remove(inOutLine);
		if (!success)
		{
			throw new IllegalStateException("inOutLine wasn't in inOut2Line");
		}

		final int orderLineId = inOutLine.getC_OrderLine_ID();
		success = orderLineId2InOutLine.remove(orderLineId) != null;
		if (!success)
		{
			throw new IllegalStateException("inOutLine wasn't in orderLineId2InOutLine."
					+ "\n orderLineId2InOutLine: " + orderLineId2InOutLine);
		}

		success = line2sched.remove(inOutLine) != null;
		if (!success)
		{
			throw new IllegalStateException("inOutLine wasn't in line2sched"
					+ "\n line2sched: " + line2sched);
		}
	}

	/**
	 * 
	 * @return a copy of the list of {@link I_M_InOut}s stored in this instance.
	 */
	@Override
	public List<I_M_InOut> getCandidates()
	{
		return ImmutableList.copyOf(orderedCandidates);
	}

	/**
	 * 
	 * @return the number of {@link I_M_InOut}s this instance contains.
	 */
	@Override
	public int size()
	{
		return orderedCandidates.size();
	}

	@Override
	public CompleteStatus getCompleteStatus(final I_M_InOutLine inOutLine)
	{
		if (inOutLine == null)
		{
			throw new NullPointerException("inOutLine");
		}
		if (!line2CompleteStatus.containsKey(inOutLine))
		{
			throw new IllegalArgumentException("inOutLine " + inOutLine
					+ " is not contained in this instance");
		}
		return line2CompleteStatus.get(inOutLine);
	}

	@Override
	public BigDecimal getQtyDeliverable(final int orderLineId)
	{
		if (olId2QtyDeliverable.containsKey(orderLineId))
		{
			return olId2QtyDeliverable.get(orderLineId);
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 
	 * @param inOut
	 * @return a copy of the list of {@link I_M_InOutLine}s that belong to the given <code>inOut</code>.
	 * @throws NullPointerException if <code>inOut</code> is <code>null</code>
	 */
	@Override
	public List<I_M_InOutLine> getLines(final I_M_InOut inOut)
	{
		if (inOut == null)
		{
			throw new NullPointerException("inOut");
		}

		final List<I_M_InOutLine> result = new ArrayList<I_M_InOutLine>();
		for (final I_M_InOutLine iol : inOut2Line.get(inOut))
		{
			result.add(InterfaceWrapperHelper.create(iol, I_M_InOutLine.class));
		}
		return result;
	}

	@Override
	public I_M_InOut getInOut(final I_M_InOutLine inOutLine)
	{
		final I_M_InOut inout = line2InOut.get(inOutLine);

		if (inout == null)
		{
			throw new IllegalArgumentException("inOutLine " + inOutLine + " is not contained in this instance");
		}
		return inout;
	}

	/**
	 * 
	 * @param inOut
	 * @return <code>true</code> if the given inOut has no inOutLines
	 * @throws NullPointerException if <code>inOut</code> is <code>null</code> or hasn't been added to this instance using {@link #addInOut(I_M_InOut)} before.
	 */
	@Override
	public boolean hasNoLines(final I_M_InOut inOut)
	{
		if (inOut == null)
		{
			throw new NullPointerException("inOut");
		}
		return inOut2Line.get(inOut).isEmpty();
	}

	/**
	 * 
	 * @param shipperId
	 * @param bPartNerLocationId
	 * @return the inOut with the given parameters
	 * @throws IllegalStateException if no inOut with the given bPartnerLocationId and shipperId has been added
	 * 
	 */
	@Override
	public I_M_InOut getInOutForShipper(
			final int shipperId,
			final int warehouseId,
			final String bPartnerAddress)
	{
		final ArrayKey key = Util.mkKey(bPartnerAddress, warehouseId, shipperId);

		final MInOut inOutPO = shipperKey2Candidate.get(key);
		return InterfaceWrapperHelper.create(inOutPO, I_M_InOut.class);
	}

	@Override
	public I_M_InOut getInOutForOrderId(
			final int orderId,
			final int warehouseId,
			final String bPartnerAddress)
	{
		final ArrayKey key = Util.mkKey(bPartnerAddress, warehouseId, orderId);

		final MInOut inOutPO = orderKey2Candidate.get(key);
		return InterfaceWrapperHelper.create(inOutPO, I_M_InOut.class);
	}

	@Override
	public I_C_OrderLine getOrderLine(final int olId)
	{
		return InterfaceWrapperHelper.create(orderLineCache.get(olId), I_C_OrderLine.class);
	}

	/**
	 * Updates the {@link CompleteStatus} of the candidates. Also sets the candidates' quantities to zero if this is implied by the status.
	 */
	public void afterFirstRun()
	{
		resetQtyDeliverables();
		updateCompleteStatus();
	}

	public I_M_InOutLine getInOutLineFor(final I_C_OrderLine orderLine)
	{
		final int orderLineId = orderLine.getC_OrderLine_ID();
		return getInOutLineForOrderLine(orderLineId);
	}

	/**
	 * Removes first the inoutLines with {@link I_M_InOutLine#getQtyEntered()} = 0 and afterwards the inouts that have no more lines.
	 */
	public void purgeLinesEmpty()
	{
		int rmInOuts = 0, rmInOutLines = 0;
		// removing empty inOutLines
		for (final I_M_InOut inOut : getCandidates())
		{
			for (final I_M_InOutLine inOutLine : getLines(inOut))
			{
				if (inOutLine.getMovementQty().signum() <= 0)
				{
					removeLine(inOutLine);
					rmInOutLines++;
				}
			}
		}

		// removing empty inOuts
		for (final I_M_InOut inOut : getCandidates())
		{
			if (hasNoLines(inOut))
			{
				final ArrayKey key =
						Util.mkKey(inOut.getBPartnerAddress(), inOut.getM_Warehouse_ID(), inOut.getM_Shipper_ID());
				shipperKey2Candidate.remove(key);
				inOut2Line.remove(inOut);
				orderedCandidates.remove(inOut);
				rmInOuts++;
			}
		}
		logger.info("Removed " + rmInOuts + " MInOut instances and " + rmInOutLines + " MInOutLine instances");
	}

	private void updateCompleteStatus()
	{

		for (final MOrder order : order2InOutLine.keySet())
		{
			final String deliveryRule = order.getDeliveryRule();

			if (X_C_Order.DELIVERYRULE_CompleteLine.equals(deliveryRule))
			{
				// We only deliver if the line qty is same as the qty
				// ordered by the customer
				for (final I_M_InOutLine inOutLine : order2InOutLine.get(order))
				{
					if (CompleteStatus.INCOMPLETE_LINE.equals(line2CompleteStatus.get(inOutLine)))
					{
						inOutLine.setQtyEntered(BigDecimal.ZERO);
						inOutLine.setMovementQty(BigDecimal.ZERO);
					}
				}
			}
			else if (X_C_Order.DELIVERYRULE_CompleteOrder.equals(deliveryRule))
			{
				// We only deliver any line at all if all line qtys as the
				// same as the qty ordered by the customer
				boolean removeAll = false;
				for (final I_M_InOutLine inOutLine : order2InOutLine.get(order))
				{
					if (CompleteStatus.INCOMPLETE_LINE.equals(line2CompleteStatus.get(inOutLine)))
					{
						removeAll = true;
						break;
					}
				}
				if (removeAll)
				{
					for (final I_M_InOutLine inOutLine : order2InOutLine.get(order))
					{
						inOutLine.setQtyEntered(BigDecimal.ZERO);
						inOutLine.setMovementQty(BigDecimal.ZERO);

						// update the status to show why we set the quantity to zero
						line2CompleteStatus.put(inOutLine, CompleteStatus.INCOMPLETE_ORDER);
					}
				}
			}
			else
			{
				for (I_M_InOutLine inOutLine : order2InOutLine.get(order))
				{
					// update the status to show that the "completeness" of this inOuLine is irrelevant
					line2CompleteStatus.put(inOutLine, CompleteStatus.OK);
				}
			}
		}
	}

	private void resetQtyDeliverables()
	{
		olId2QtyDeliverable.clear();

		for (final I_M_InOutLine inOutLine : line2InOut.keySet())
		{
			if (inOutLine.getMovementQty().signum() != 0)
			{
				olId2QtyDeliverable.put(inOutLine.getC_OrderLine_ID(), inOutLine.getMovementQty());
			}
		}
	}

	@Override
	public I_M_InOutLine getInOutLineForOrderLine(final int orderLineId)
	{
		return orderLineId2InOutLine.get(orderLineId);
	}

	@Override
	public void setOverallStatus(final I_M_InOutLine inOutLine, final OverallStatus status)
	{
		line2OverallStatus.put(inOutLine, status);
	}

	@Override
	public boolean isLineDiscarded(final I_M_InOutLine inOutLine)
	{

		return line2OverallStatus.containsKey(inOutLine)
				&& OverallStatus.DISCARD.equals(line2OverallStatus
						.get(inOutLine));
	}

	@Override
	public void addStatusInfo(final I_M_InOutLine inOutLine, final String string)
	{
		StringBuilder currentInfos = line2StatusInfo.get(inOutLine);

		boolean firstInfo = false;
		if (currentInfos == null)
		{
			currentInfos = new StringBuilder();
			line2StatusInfo.put(inOutLine, currentInfos);
			firstInfo = true;
		}

		if (!firstInfo)
		{
			currentInfos.append("; ");
		}
		currentInfos.append(string);
	}

	@Override
	public String getStatusInfos(final I_M_InOutLine inOutLine)
	{

		final StringBuilder statusInfos = line2StatusInfo.get(inOutLine);
		if (statusInfos == null)
		{
			return "";
		}
		return statusInfos.toString();
	}

	@Override
	public I_M_ShipmentSchedule getShipmentSchedule(final I_M_InOutLine inOutLine)
	{

		return line2sched.get(inOutLine);
	}

	@Override
	public Set<I_M_ShipmentSchedule> getAllShipmentSchedules()
	{
		// NOTE: we assume there are not duplicate instances for same M_ShipmentSchedule record.
		// Even if they are, we are returning them "twice" because the caller code will just iterate the result and do the proper updates and we want to have everything updated.

		final Set<I_M_ShipmentSchedule> shipmentSchedules = new IdentityHashSet<I_M_ShipmentSchedule>(line2sched.size());
		shipmentSchedules.addAll(line2sched.values());
		return shipmentSchedules;
	}

}
