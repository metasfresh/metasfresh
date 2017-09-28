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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.compiere.model.X_C_Order;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import lombok.NonNull;

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
	private final List<DeliveryGroupCandidate> orderedCandidates = new ArrayList<>();

	private final Map<DeliveryLineCandidate, StringBuilder> line2StatusInfo = new HashMap<>();

	private final Map<Integer, DeliveryLineCandidate> shipmentScheduleId2InOutLine = new HashMap<>();

	private final Set<DeliveryLineCandidate> deliveryLineCandidates = new HashSet<>();

	/**
	 * Used when multiple orders need to be consolidated to one shipment
	 */
	private final Map<ArrayKey, DeliveryGroupCandidate> shipperKey2Candidate = new HashMap<>();

	/**
	 * Used when one shipment per order is required
	 */
	private final Map<ArrayKey, DeliveryGroupCandidate> orderKey2Candidate = new HashMap<>();


	@Override
	public void addInOut(@NonNull final DeliveryGroupCandidate inOut)
	{
		if (orderedCandidates.contains(inOut))
		{
			throw new IllegalArgumentException("Each input may be added only once");
		}

		orderedCandidates.add(inOut);

		final ArrayKey shipperKey = Util.mkKey(
				inOut.getBPartnerAddress(),
				inOut.getWarehouseId(),
				inOut.getShipperId());
		shipperKey2Candidate.put(shipperKey, inOut);

		final ArrayKey orderKey = Util.mkKey(
				inOut.getBPartnerAddress(),
				inOut.getWarehouseId(),
				inOut.getGroupId());
		orderKey2Candidate.put(orderKey, inOut);
	}

	@Override
	public void addLine(@NonNull final DeliveryLineCandidate inOutLine)
	{
		final DeliveryGroupCandidate inOut = inOutLine.getGroup();

		if (!orderedCandidates.contains(inOut))
		{
			throw new IllegalStateException("inOut needs to be added using 'addInOut' first"
					+ "\n InOut: " + inOut
					+ "\n orderedCandidates: " + orderedCandidates);
		}

		if (CompleteStatus.INCOMPLETE_ORDER.equals(inOutLine.getCompleteStatus()))
		{
			throw new IllegalArgumentException("completeStatus may not be " + CompleteStatus.INCOMPLETE_ORDER + " (this will be figured out later by this class)");
		}

		final I_M_ShipmentSchedule sched = inOutLine.getShipmentSchedule();

		//
		// C_OrderLine_ID to M_InOutLine mapping
		{
			final DeliveryLineCandidate inOutLine_Old = shipmentScheduleId2InOutLine.put(sched.getM_ShipmentSchedule_ID(), inOutLine);
			if (inOutLine_Old != null && inOutLine_Old != inOutLine)
			{
				throw new IllegalArgumentException("An InOutLine was already set for order line in orderLineId2InOutLine mapping"
						+ "\n InOutLine: " + inOutLine
						+ "\n InOutLine (old): " + inOutLine_Old
						+ "\n shipmentScheduleId2InOutLine (after change): " + shipmentScheduleId2InOutLine);
			}
		}

		deliveryLineCandidates.add(inOutLine);
	}

	public void removeLine(@NonNull final DeliveryLineCandidate deliveryLineCandidate)
	{
		final int shipmentScheduleId = deliveryLineCandidate.getShipmentSchedule().getM_ShipmentSchedule_ID();
		boolean success = shipmentScheduleId2InOutLine.remove(shipmentScheduleId) != null;
		if (!success)
		{
			throw new IllegalStateException("inOutLine wasn't in shipmentScheduleId2InOutLine."
					+ "\n shipmentScheduleId2InOutLine: " + shipmentScheduleId2InOutLine);
		}
		//deliveryLineCandidate.hashCode() -1037930231
//		deliveryLineCandidates.contains(deliveryLineCandidate)
		success = deliveryLineCandidates.remove(deliveryLineCandidate);
		if (!success)
		{
			throw new IllegalStateException("deliveryLineCandidate wasn't in deliveryLineCandidates;"
					+ "\n deliveryLineCandidate=" + deliveryLineCandidate
					+ "\n deliveryLineCandidates: " + deliveryLineCandidates);
		}
	}

	/**
	 *
	 * @return a copy of the list of {@link I_M_InOut}s stored in this instance.
	 */
	@Override
	public List<DeliveryGroupCandidate> getCandidates()
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

	/**
	 *
	 * @param shipperId
	 * @param bPartNerLocationId
	 * @return the inOut with the given parameters
	 * @throws IllegalStateException if no inOut with the given bPartnerLocationId and shipperId has been added
	 *
	 */
	@Override
	public DeliveryGroupCandidate getInOutForShipper(
			final int shipperId,
			final int warehouseId,
			final String bPartnerAddress)
	{
		final ArrayKey key = Util.mkKey(bPartnerAddress, warehouseId, shipperId);

		final DeliveryGroupCandidate inOut = shipperKey2Candidate.get(key);
		return inOut;
	}

	@Override
	public DeliveryGroupCandidate getInOutForOrderId(
			final int groupId,
			final int warehouseId,
			final String bPartnerAddress)
	{
		final ArrayKey key = Util.mkKey(bPartnerAddress, warehouseId, groupId);

		final DeliveryGroupCandidate inOut = orderKey2Candidate.get(key);
		return inOut;
	}

	public DeliveryLineCandidate getInOutLineFor(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return getInOutLineForOrderLine(shipmentSchedule.getM_ShipmentSchedule_ID());
	}

	/**
	 * Removes first the inoutLines with {@link DeliveryLineCandidate#getQtyEntered()} = 0 and afterwards the inouts that have no more lines.
	 */
	public void purgeLinesEmpty()
	{
		int rmInOuts = 0, rmInOutLines = 0;
		// removing empty inOutLines
		for (final DeliveryGroupCandidate inOut : getCandidates())
		{
			for (final DeliveryLineCandidate inOutLine : inOut.getLines())
			{
				if (inOutLine.getQtyToDeliver().signum() <= 0)
				{
					removeLine(inOutLine);
					rmInOutLines++;
				}
			}
		}

		// removing empty inOuts
		for (final DeliveryGroupCandidate inOut : getCandidates())
		{
			if (inOut.getLines().isEmpty())
			{
				final ArrayKey key = Util.mkKey(inOut.getBPartnerAddress(),
						inOut.getWarehouseId(),
						inOut.getShipperId());
				shipperKey2Candidate.remove(key);
				orderedCandidates.remove(inOut);
				rmInOuts++;
			}
		}
		logger.info("Removed " + rmInOuts + " MInOut instances and " + rmInOutLines + " MInOutLine instances");
	}

	/**
	 * Updates the {@link CompleteStatus} of the candidates. Also sets the candidates' quantities to zero if this is implied by the status.
	 */
	public void updateCompleteStatusAndSetQtyToZeroWhereNeeded()
	{
		for (final DeliveryLineCandidate inOutLine : deliveryLineCandidates)
		{
			if (inOutLine.isDiscarded())
			{
				continue;
			}

			final String deliveryRule = inOutLine.getShipmentSchedule().getDeliveryRule();

			if (X_C_Order.DELIVERYRULE_CompleteLine.equals(deliveryRule))
			{
				// We only deliver if the line qty is same as the qty
				// ordered by the customer
				if (CompleteStatus.INCOMPLETE_LINE.equals(inOutLine.getCompleteStatus()))
				{
					inOutLine.setQtyToDeliver(BigDecimal.ZERO);
					inOutLine.setDiscarded(true);
				}

			}
			else if (X_C_Order.DELIVERYRULE_CompleteOrder.equals(deliveryRule))
			{
				// We only deliver any line at all if all line qtys as the
				// same as the qty ordered by the customer
				if (!CompleteStatus.OK.equals(inOutLine.getCompleteStatus()))
				{
					for (final DeliveryLineCandidate inOutLinee : inOutLine.getGroup().getLines())
					{
						inOutLinee.setQtyToDeliver(BigDecimal.ZERO);
						inOutLinee.setDiscarded(true);

						// update the status to show why we set the quantity to zero
						inOutLinee.setCompleteStatus(CompleteStatus.INCOMPLETE_ORDER);
					}
				}
			}
			else
			{
				// update the status to show that the "completeness" of this inOuLine is irrelevant
				inOutLine.setCompleteStatus(CompleteStatus.OK);
			}
		}
	}

	@Override
	public DeliveryLineCandidate getInOutLineForOrderLine(final int shipmentScheduleId)
	{
		return shipmentScheduleId2InOutLine.get(shipmentScheduleId);
	}

	@Override
	public void addStatusInfo(final DeliveryLineCandidate inOutLine, final String string)
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
	public String getStatusInfos(final DeliveryLineCandidate inOutLine)
	{

		final StringBuilder statusInfos = line2StatusInfo.get(inOutLine);
		if (statusInfos == null)
		{
			return "";
		}
		return statusInfos.toString();
	}

	public Collection<DeliveryLineCandidate> getAllLines()
	{
		return ImmutableList.copyOf(deliveryLineCandidates);
	}
}
