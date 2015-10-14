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
import java.util.logging.Level;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.collections.IdentityHashSet;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.X_C_Order;
import org.compiere.util.CLogger;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.model.I_C_Order;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;

/**
 * Helper class to manage the shipments that might actually be created in the end.
 * 
 * @author ts
 * 
 */
public class ShipmentCandidates implements IShipmentCandidates
{

	static final CLogger logger = CLogger.getCLogger(ShipmentCandidates.class);

	/**
	 * List to store the shipments before it is decided if they are persisted to the database.
	 */
	private final List<MInOut> orderedCandidates = new ArrayList<MInOut>();

	/**
	 * Maps inOuts to their inOutLines. Usually this is done using the inOut's ID stored in the inOutLine. But as the inOut hasn't been saved yet, it doesn't have an ID.
	 */
	private final Map<MInOut, List<MInOutLine>> inOut2Line = new HashMap<MInOut, List<MInOutLine>>();

	private final Map<MInOutLine, MInOut> line2InOut = new HashMap<MInOutLine, MInOut>();

	private final Map<MInOutLine, StringBuilder> line2StatusInfo = new HashMap<MInOutLine, StringBuilder>();

	/**
	 * Contains the quantities that would be delivered when postage free amount and order delivery rules where ignored.
	 */
	private final Map<Integer, BigDecimal> olId2QtyDeliverable = new HashMap<Integer, BigDecimal>();

	private final Map<MInOutLine, CompleteStatus> line2CompleteStatus = new HashMap<MInOutLine, CompleteStatus>();

	private final Map<MInOutLine, PostageFreeStatus> line2PostageFreeStatus = new HashMap<MInOutLine, PostageFreeStatus>();

	private final Map<MInOutLine, OverallStatus> line2OverallStatus = new HashMap<MInOutLine, OverallStatus>();

	/**
	 * Used to keep track of which inOutLine's order has which delivery rule.
	 * 
	 * @see {@link #updateCompleteStatus()}
	 */
	private final Map<MOrder, Set<MInOutLine>> order2InOutLine = new HashMap<MOrder, Set<MInOutLine>>();

	private final Map<Integer, MOrderLine> orderLineCache;

	private final Map<Integer, MInOutLine> orderLineId2InOutLine = new HashMap<Integer, MInOutLine>();

	private final Map<MInOutLine, I_M_ShipmentSchedule> line2sched = new HashMap<MInOutLine, I_M_ShipmentSchedule>();

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

		orderedCandidates.add(getPO(inOut));

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

		final List<MInOutLine> inOutLines = new ArrayList<MInOutLine>();
		inOut2Line.put(getPO(inOut), inOutLines);
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

		final MInOutLine inOutLinePO = getPO(inOutLine);
		final MInOut inOutPO = getPO(inOut);

		inOut2Line.get(inOutPO).add(inOutLinePO);
		line2InOut.put(inOutLinePO, inOutPO);

		// store the inoutLine's orderId and status as well to support our
		// later purge
		line2CompleteStatus.put(inOutLinePO, completeStatus);
		line2PostageFreeStatus.put(inOutLinePO, PostageFreeStatus.OK);

		final MOrder orderPO = InterfaceWrapperHelper.getPO(order);

		Set<MInOutLine> inOutLines = order2InOutLine.get(orderPO);
		if (inOutLines == null)
		{
			inOutLines = new HashSet<MInOutLine>();
			order2InOutLine.put(orderPO, inOutLines);
		}
		inOutLines.add(inOutLinePO);

		//
		// C_OrderLine_ID to M_InOutLine mapping
		{
			final MInOutLine inOutLinePO_Old = orderLineId2InOutLine.put(inOutLine.getC_OrderLine_ID(), inOutLinePO);
			if (inOutLinePO_Old != null && inOutLinePO_Old != inOutLinePO)
			{
				throw new IllegalArgumentException("An InOutLine was already set for order line in orderLineId2InOutLine mapping"
						+ "\n InOutLine: " + inOutLinePO
						+ "\n InOutLine (old): " + inOutLinePO_Old
						+ "\n orderLineId2InOutLine (after change): " + orderLineId2InOutLine);
			}
		}

		line2sched.put(inOutLinePO, sched);
	}

	private MInOutLine getPO(final I_M_InOutLine inOutLine)
	{
		return InterfaceWrapperHelper.getPO(inOutLine);
	}

	public void removeLine(final I_M_InOutLine inOutLine)
	{
		if (inOutLine == null)
		{
			throw new NullPointerException("inOutLine");
		}

		final MInOutLine inOutLinePO = getPO(inOutLine);
		final MInOut inOutPO = line2InOut.remove(inOutLinePO);
		if (inOutPO == null)
		{
			throw new IllegalStateException("inOutLine wasn't in line2InOut");
		}

		boolean success = inOut2Line.get(inOutPO).remove(inOutLine);
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

		success = line2sched.remove(inOutLinePO) != null;
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
		final List<I_M_InOut> result = new ArrayList<I_M_InOut>(orderedCandidates.size());
		for (final MInOut inOutPO : orderedCandidates)
		{
			result.add(InterfaceWrapperHelper.create(inOutPO, I_M_InOut.class));
		}
		return result;
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
	public PostageFreeStatus getPostageFreeStatus(final I_M_InOutLine inOutLine)
	{
		if (inOutLine == null)
		{
			throw new NullPointerException("inOutLine");
		}
		if (!line2PostageFreeStatus.containsKey(inOutLine))
		{
			throw new IllegalArgumentException("inOutLine " + inOutLine
					+ " is not contained in this instance");
		}
		return line2PostageFreeStatus.get(inOutLine);
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
		for (final MInOutLine iol : inOut2Line.get(getPO(inOut)))
		{
			result.add(InterfaceWrapperHelper.create(iol, I_M_InOutLine.class));
		}
		return result;
	}

	@Override
	public I_M_InOut getInOut(final I_M_InOutLine inOutLine)
	{
		final MInOut inoutPO = line2InOut.get(getPO(inOutLine));

		if (inoutPO == null)
		{
			throw new IllegalArgumentException("inOutLine " + inOutLine + " is not contained in this instance");
		}
		return InterfaceWrapperHelper.create(inoutPO, I_M_InOut.class);
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
		return inOut2Line.get(getPO(inOut)).isEmpty();
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
	 * Updates the {@link CompleteStatus} and the {@link PostageFreeStatus} of the candidates. Also sets the candidates' quantities to zero if this is implied by the status.
	 */
	public void afterFirstRun(final boolean ignorePostageFreeAmt)
	{
		resetQtyDeliverables();
		updateCompleteStatus();
		updatePostageFreeStatus(ignorePostageFreeAmt);
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
				for (final MInOutLine inOutLinePO : order2InOutLine.get(order))
				{
					if (CompleteStatus.INCOMPLETE_LINE.equals(line2CompleteStatus.get(inOutLinePO)))
					{
						inOutLinePO.setQtyEntered(BigDecimal.ZERO);
						inOutLinePO.setMovementQty(BigDecimal.ZERO);
					}
				}
			}
			else if (X_C_Order.DELIVERYRULE_CompleteOrder.equals(deliveryRule))
			{
				// We only deliver any line at all if all line qtys as the
				// same as the qty ordered by the customer
				boolean removeAll = false;
				for (final MInOutLine inOutLinePO : order2InOutLine.get(order))
				{
					if (CompleteStatus.INCOMPLETE_LINE.equals(line2CompleteStatus.get(inOutLinePO)))
					{
						removeAll = true;
						break;
					}
				}
				if (removeAll)
				{
					for (final MInOutLine inOutLinePO : order2InOutLine.get(order))
					{
						inOutLinePO.setQtyEntered(BigDecimal.ZERO);
						inOutLinePO.setMovementQty(BigDecimal.ZERO);

						// update the status to show why we set the quantity to zero
						line2CompleteStatus.put(inOutLinePO, CompleteStatus.INCOMPLETE_ORDER);
					}
				}
			}
			else
			{
				for (MInOutLine inOutLinePO : order2InOutLine.get(order))
				{
					// update the status to show that the "completeness" of this inOuLine is irrelevant
					line2CompleteStatus.put(inOutLinePO, CompleteStatus.OK);
				}
			}
		}
	}

	/**
	 * Updated all inoutLines' PostageFreeStatus according to the respective bPartner's postage free amount.
	 * 
	 * @param ignorePostageFreeAmt if true, the lines qty is not set to {@link BigDecimal#ZERO}, even if we are below the customer's postage free amount. However, the status is still set.
	 */
	@Override
	public void updatePostageFreeStatus(final boolean ignorePostageFreeAmt)
	{
		for (final I_M_InOut shipmentCandidate : getCandidates())
		{
			final I_C_BPartner bPartner = InterfaceWrapperHelper.create(shipmentCandidate.getC_BPartner(), I_C_BPartner.class);

			final BigDecimal postageFree = (BigDecimal)bPartner.getPostageFreeAmt();

			BigDecimal shipmentValue = BigDecimal.ZERO;

			// if ignorePostageFreeAmount is false and a postage free amount
			// is set, we need to check if the value of this shipment is
			// enough to make shipping profitable
			boolean sufficiantValue = postageFree == null;

			if (!sufficiantValue)
			{
				for (final I_M_InOutLine inOutLine : getLines(shipmentCandidate))
				{

					// access orderLineCache instead of loading the line
					// from DB
					final MOrderLine orderLinePO = this.orderLineCache.get(inOutLine.getC_OrderLine_ID());
					final BigDecimal lineValue = orderLinePO.getPriceActual().multiply(inOutLine.getQtyEntered());

					shipmentValue = shipmentValue.add(lineValue);

					if (shipmentValue.compareTo(postageFree) >= 0)
					{
						sufficiantValue = true;
						break;
					}
				}
			}
			for (final I_M_InOutLine inOutLine : getLines(shipmentCandidate))
			{
				final PostageFreeStatus status;

				if (sufficiantValue)
				{
					status = PostageFreeStatus.OK;
				}
				else
				{
					if (!ignorePostageFreeAmt)
					{
						inOutLine.setQtyEntered(BigDecimal.ZERO);
						inOutLine.setMovementQty(BigDecimal.ZERO);
					}

					status = PostageFreeStatus.BELOW_POSTAGEFREE_AMT;

					if (logger.isLoggable(Level.INFO))
					{
						logger.info("Shipment "
								+ shipmentCandidate.getDocumentNo()
								+ " has an insufficient value of "
								+ shipmentValue.toPlainString()
								+ " (minimum value is "
								+ postageFree.toPlainString() + ")");
					}
				}

				final MInOutLine inOutLinePO = getPO(inOutLine);
				line2PostageFreeStatus.put(inOutLinePO, status);
			}
		}
	}

	private void resetQtyDeliverables()
	{
		olId2QtyDeliverable.clear();

		for (final MInOutLine inOutLine : line2InOut.keySet())
		{
			if (inOutLine.getMovementQty().signum() != 0)
			{
				olId2QtyDeliverable.put(inOutLine.getC_OrderLine_ID(), inOutLine.getMovementQty());
			}
		}
	}

	@Override
	public void setPostageFreeStatusOK(final I_M_InOut inOut)
	{
		for (final I_M_InOutLine inOutLine : getLines(inOut))
		{
			final MInOutLine inOutLinePO = getPO(inOutLine);
			line2PostageFreeStatus.put(inOutLinePO, PostageFreeStatus.OK);
		}
	}

	@Override
	public I_M_InOutLine getInOutLineForOrderLine(final int orderLineId)
	{
		final MInOutLine inoutLine = orderLineId2InOutLine.get(orderLineId);
		return InterfaceWrapperHelper.create(inoutLine, I_M_InOutLine.class);
	}

	@Override
	public void setOverallStatus(final I_M_InOutLine inOutLine, final OverallStatus status)
	{
		final MInOutLine inOutLinePO = getPO(inOutLine);
		line2OverallStatus.put(inOutLinePO, status);
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
		final MInOutLine inOutLinePO = getPO(inOutLine);

		StringBuilder currentInfos = line2StatusInfo.get(inOutLine);

		boolean firstInfo = false;
		if (currentInfos == null)
		{
			currentInfos = new StringBuilder();
			line2StatusInfo.put(inOutLinePO, currentInfos);
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
