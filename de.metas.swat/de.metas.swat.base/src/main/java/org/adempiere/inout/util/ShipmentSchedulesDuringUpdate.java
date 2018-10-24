package org.adempiere.inout.util;

import lombok.NonNull;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.shipping.ShipperId;

/**
 * Helper class to manage the shipments that might actually be created in the end.
 *
 * @author ts
 *
 */
public class ShipmentSchedulesDuringUpdate implements IShipmentSchedulesDuringUpdate
{

	private static final Logger logger = LogManager.getLogger(ShipmentSchedulesDuringUpdate.class);

	/**
	 * List to store the shipments before it is decided if they are persisted to the database.
	 */
	private final List<DeliveryGroupCandidate> orderedCandidates = new ArrayList<>();

	private final Map<DeliveryLineCandidate, StringBuilder> line2StatusInfo = new HashMap<>();

	private final Map<Integer, DeliveryLineCandidate> shipmentScheduleId2DeliveryLineCandidate = new HashMap<>();

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
	public void addGroup(@NonNull final DeliveryGroupCandidate deliveryGroupCandidate)
	{
		if (orderedCandidates.contains(deliveryGroupCandidate))
		{
			throw new IllegalArgumentException("Each input may be added only once");
		}

		orderedCandidates.add(deliveryGroupCandidate);

		final ArrayKey shipperKey = createShipperKey(
				deliveryGroupCandidate.getShipperId(),
				deliveryGroupCandidate.getWarehouseId(),
				deliveryGroupCandidate.getBPartnerAddress());
		shipperKey2Candidate.put(shipperKey, deliveryGroupCandidate);

		final ArrayKey orderKey = createOrderKey(
				deliveryGroupCandidate.getGroupId(),
				deliveryGroupCandidate.getWarehouseId(),
				deliveryGroupCandidate.getBPartnerAddress());
		orderKey2Candidate.put(orderKey, deliveryGroupCandidate);
	}

	private static ArrayKey createOrderKey(final Integer groupId, final WarehouseId warehouseId, final String bpartnerAddress)
	{
		return Util.mkKey(bpartnerAddress, warehouseId, groupId);
	}

	@Override
	public void addLine(@NonNull final DeliveryLineCandidate deliveryLineCandidate)
	{
		final DeliveryGroupCandidate inOut = deliveryLineCandidate.getGroup();

		if (!orderedCandidates.contains(inOut))
		{
			throw new IllegalStateException("inOut needs to be added using 'addInOut' first"
					+ "\n InOut: " + inOut
					+ "\n orderedCandidates: " + orderedCandidates);
		}

		if (CompleteStatus.INCOMPLETE_ORDER.equals(deliveryLineCandidate.getCompleteStatus()))
		{
			throw new IllegalArgumentException("completeStatus may not be " + CompleteStatus.INCOMPLETE_ORDER + " (this will be figured out later by this class)");
		}

		//
		// C_OrderLine_ID to M_InOutLine mapping
		{
			final DeliveryLineCandidate oldCandidate = shipmentScheduleId2DeliveryLineCandidate.put(deliveryLineCandidate.getShipmentScheduleId(), deliveryLineCandidate);
			if (oldCandidate != null && !oldCandidate.equals(deliveryLineCandidate))
			{
				throw new IllegalArgumentException("Aa deliveryLineCandidate was already set for order line in orderLineId2InOutLine mapping"
						+ "\n deliveryLineCandidate: " + deliveryLineCandidate
						+ "\n old deliveryLineCandidate: " + oldCandidate
						+ "\n shipmentScheduleId2InOutLine (after change): " + shipmentScheduleId2DeliveryLineCandidate);
			}
		}

		deliveryLineCandidates.add(deliveryLineCandidate);
	}

	public void removeLine(@NonNull final DeliveryLineCandidate deliveryLineCandidate)
	{
		final int shipmentScheduleId = deliveryLineCandidate.getShipmentScheduleId();
		boolean success = shipmentScheduleId2DeliveryLineCandidate.remove(shipmentScheduleId) != null;
		if (!success)
		{
			throw new IllegalStateException("inOutLine wasn't in shipmentScheduleId2InOutLine."
					+ "\n shipmentScheduleId2InOutLine: " + shipmentScheduleId2DeliveryLineCandidate);
		}

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
			@NonNull final Optional<ShipperId> shipperId,
			final WarehouseId warehouseId,
			final String bPartnerAddress)
	{
		final ArrayKey key = createShipperKey(shipperId, warehouseId, bPartnerAddress);
		final DeliveryGroupCandidate inOut = shipperKey2Candidate.get(key);
		return inOut;
	}

	private static ArrayKey createShipperKey(final Optional<ShipperId> shipperId, final WarehouseId warehouseId, final String bPartnerAddress)
	{
		return Util.mkKey(bPartnerAddress, warehouseId, shipperId.orElse(null));
	}

	@Override
	public DeliveryGroupCandidate getInOutForOrderId(
			final int groupId,
			final WarehouseId warehouseId,
			final String bpartnerAddress)
	{
		final ArrayKey key = createOrderKey(groupId, warehouseId, bpartnerAddress);
		final DeliveryGroupCandidate inOut = orderKey2Candidate.get(key);
		return inOut;
	}

	public DeliveryLineCandidate getInOutLineFor(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return getLineCandidateForShipmentScheduleId(shipmentSchedule.getM_ShipmentSchedule_ID());
	}

	/**
	 * Removes first the inoutLines with {@link DeliveryLineCandidate#getQtyEntered()} = 0 and afterwards the inouts that have no more lines.
	 */
	public void removeEmptyLineandGroupCandidates()
	{
		// removing empty DeliveryLineCandidate
		int rmInOutLines = 0;
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

		// removing empty DeliveryGroupCandidate
		int rmInOuts = 0;
		for (final DeliveryGroupCandidate inOut : getCandidates())
		{
			if (!inOut.hasLines())
			{
				final ArrayKey key = createShipperKey(
						inOut.getShipperId(),
						inOut.getWarehouseId(),
						inOut.getBPartnerAddress());
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
		for (final DeliveryLineCandidate deliveryLineCandidate : deliveryLineCandidates)
		{
			updateCompleteStatusAndSetQtyToZeroIfNeeded(deliveryLineCandidate);
		}
	}

	private static void updateCompleteStatusAndSetQtyToZeroIfNeeded(@NonNull final DeliveryLineCandidate deliveryLineCandidate)
	{
		if (deliveryLineCandidate.isDiscarded())
		{
			return;
		}

		final DeliveryRule deliveryRule = deliveryLineCandidate.getDeliveryRule();

		if (DeliveryRule.COMPLETE_LINE.equals(deliveryRule))
		{
			discardLineCandidateIfIncomplete(deliveryLineCandidate);
		}
		else if (DeliveryRule.COMPLETE_ORDER.equals(deliveryRule))
		{
			discardAllLinesFromSameGroupIfIncomplete(deliveryLineCandidate);
		}
		else
		{
			deliveryLineCandidate.setCompleteStatus(CompleteStatus.OK); // update the status to show that the "completeness" of this deliveryLineCandidate is irrelevant
		}
	}

	/**
	 * We only deliver if the line qty is same as the qty
	 * ordered by the customer
	 *
	 * @param deliveryLineCandidate
	 */
	private static void discardLineCandidateIfIncomplete(@NonNull final DeliveryLineCandidate deliveryLineCandidate)
	{
		final boolean lineIsIncompletelyDelivered = CompleteStatus.INCOMPLETE_LINE.equals(deliveryLineCandidate.getCompleteStatus());

		if (lineIsIncompletelyDelivered)
		{
			deliveryLineCandidate.setQtyToDeliver(BigDecimal.ZERO);
			deliveryLineCandidate.setDiscarded();
		}
	}

	/**
	 * We only deliver any line at all if all line qtys as the
	 * same as the qty ordered by the customer
	 *
	 * @param deliveryLineCandidate
	 */
	private static void discardAllLinesFromSameGroupIfIncomplete(@NonNull final DeliveryLineCandidate deliveryLineCandidate)
	{
		if (CompleteStatus.OK.equals(deliveryLineCandidate.getCompleteStatus()))
		{
			return;
		}

		for (final DeliveryLineCandidate inOutLine : deliveryLineCandidate.getGroup().getLines())
		{
			inOutLine.setQtyToDeliver(BigDecimal.ZERO);
			inOutLine.setDiscarded();

			// update the status to show why we set the quantity to zero
			inOutLine.setCompleteStatus(CompleteStatus.INCOMPLETE_ORDER);
		}
	}

	@Override
	public DeliveryLineCandidate getLineCandidateForShipmentScheduleId(final int shipmentScheduleId)
	{
		return shipmentScheduleId2DeliveryLineCandidate.get(shipmentScheduleId);
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

	public ImmutableList<DeliveryLineCandidate> getAllLines()
	{
		return ImmutableList.copyOf(deliveryLineCandidates);
	}
}
