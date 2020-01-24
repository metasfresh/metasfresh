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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ShipmentSchedulesMDC;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.shipping.ShipperId;
import lombok.NonNull;

/**
 * Helper class to manage the shipments (a.k.a {@link DeliveryGroupCandidate}s) that might actually be created in the end.
 */
public class ShipmentSchedulesDuringUpdate implements IShipmentSchedulesDuringUpdate
{

	private static final Logger logger = LogManager.getLogger(ShipmentSchedulesDuringUpdate.class);

	/**
	 * List to store the shipments before it is decided if they are persisted to the database.
	 */
	private final List<DeliveryGroupCandidate> orderedCandidates = new ArrayList<>();

	private final Map<DeliveryLineCandidate, StringBuilder> line2StatusInfo = new HashMap<>();

	private final Map<ShipmentScheduleId, DeliveryLineCandidate> shipmentScheduleId2DeliveryLineCandidate = new HashMap<>();

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

	private static ArrayKey createOrderKey(
			final DeliveryGroupCandidateGroupId groupId,
			final WarehouseId warehouseId,
			final String bpartnerAddress)
	{
		return ArrayKey.of(bpartnerAddress, warehouseId, groupId);
	}

	@Override
	public void addLine(@NonNull final DeliveryLineCandidate deliveryLineCandidate)
	{
		final DeliveryGroupCandidate group = deliveryLineCandidate.getGroup();

		if (!orderedCandidates.contains(group))
		{
			throw new IllegalStateException("group needs to be added using 'addGroup' first"
					+ "\n DeliveryGroupCandidate: " + group
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
		final ShipmentScheduleId shipmentScheduleId = deliveryLineCandidate.getShipmentScheduleId();
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
	 * @return a copy of the list of {@link DeliveryGroupCandidate}s stored in this instance.
	 */
	@Override
	public List<DeliveryGroupCandidate> getCandidates()
	{
		return ImmutableList.copyOf(orderedCandidates);
	}

	/**
	 * @return the number of {@link DeliveryGroupCandidate}s this instance contains.
	 */
	@Override
	public int size()
	{
		return orderedCandidates.size();
	}

	/**
	 * @return the inOut with the given parameters
	 * @throws IllegalStateException if no {@link DeliveryGroupCandidate} with the given bPartnerLocationId and shipperId has been added
	 */
	@Override
	public DeliveryGroupCandidate getGroupForShipper(
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
	public DeliveryGroupCandidate getInOutForRecordRef(
			final TableRecordReference tableRecordRef,
			final WarehouseId warehouseId,
			final String bpartnerAddress)
	{
		final ArrayKey key = createOrderKey(
				DeliveryGroupCandidateGroupId.of(tableRecordRef),
				warehouseId,
				bpartnerAddress);
		final DeliveryGroupCandidate inOut = orderKey2Candidate.get(key);
		return inOut;
	}

	public boolean hasDeliveryLineCandidateFor(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return getLineCandidateForShipmentScheduleId(shipmentScheduleId) != null;
	}

	/**
	 * Removes first the inoutLines with {@link DeliveryLineCandidate#getQtyEntered()} = 0 and afterwards the inouts that have no more lines.
	 */
	public void removeEmptyLineandGroupCandidates()
	{
		// removing empty DeliveryLineCandidate
		int rmLineCandidates = 0;
		for (final DeliveryGroupCandidate groupCandidate : getCandidates())
		{
			for (final DeliveryLineCandidate lineCandidate : groupCandidate.getLines())
			{
				if (lineCandidate.getQtyToDeliver().signum() <= 0)
				{
					removeLine(lineCandidate);
					rmLineCandidates++;
				}
			}
		}

		// removing empty DeliveryGroupCandidate
		int rmGroupCandidates = 0;
		for (final DeliveryGroupCandidate groupCandidate : getCandidates())
		{
			if (!groupCandidate.hasLines())
			{
				final ArrayKey key = createShipperKey(
						groupCandidate.getShipperId(),
						groupCandidate.getWarehouseId(),
						groupCandidate.getBPartnerAddress());
				shipperKey2Candidate.remove(key);
				orderedCandidates.remove(groupCandidate);
				rmGroupCandidates++;
			}
		}
		logger.info("Removed {} groupCandidates and {} lineCandidates", rmGroupCandidates, rmLineCandidates);
	}

	/**
	 * Updates the {@link CompleteStatus} of the candidates. Also sets the candidates' quantities to zero if this is implied by the status.
	 */
	public void updateCompleteStatusAndSetQtyToZeroWhereNeeded()
	{
		for (final DeliveryLineCandidate deliveryLineCandidate : deliveryLineCandidates)
		{
			try (final MDCCloseable shipmentScheduleMDC = ShipmentSchedulesMDC.putShipmentScheduleId(deliveryLineCandidate.getShipmentScheduleId()))
			{
				updateCompleteStatusAndSetQtyToZeroIfNeeded(deliveryLineCandidate);
			}
		}
	}

	private static void updateCompleteStatusAndSetQtyToZeroIfNeeded(@NonNull final DeliveryLineCandidate deliveryLineCandidate)
	{
		if (deliveryLineCandidate.isDiscarded())
		{
			return;
		}

		final DeliveryRule deliveryRule = deliveryLineCandidate.getDeliveryRule();
		logger.debug("lineCandidate has deliveryRule={}", deliveryRule);

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
	 * We only deliver if the line qty is same as the qty ordered by the customer
	 */
	private static void discardLineCandidateIfIncomplete(@NonNull final DeliveryLineCandidate deliveryLineCandidate)
	{
		final boolean lineIsIncompletelyDelivered = CompleteStatus.INCOMPLETE_LINE.equals(deliveryLineCandidate.getCompleteStatus());

		if (lineIsIncompletelyDelivered)
		{
			logger.debug("Discard this lineCandidate because it has completeStatus={}", deliveryLineCandidate.getCompleteStatus());
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

		try (final IAutoCloseable shipmentScheduleMDCRestorer = ShipmentSchedulesMDC.removeCurrentShipmentScheduleId())
		{
			for (final DeliveryLineCandidate candidateOfGroup : deliveryLineCandidate.getGroup().getLines())
			{
				try (final MDCCloseable shipmentScheduleOfGroupMDC = ShipmentSchedulesMDC.putShipmentScheduleId(candidateOfGroup.getShipmentScheduleId()))
				{
					logger.debug("Discard this lineCandidate because candidate with ShipmentScheduleId={} is in same group and has completeStatus={}",
							deliveryLineCandidate.getShipmentScheduleId().getRepoId(), deliveryLineCandidate.getCompleteStatus());

					candidateOfGroup.setQtyToDeliver(BigDecimal.ZERO);
					candidateOfGroup.setDiscarded();

					// update the status to show why we set the quantity to zero
					candidateOfGroup.setCompleteStatus(CompleteStatus.INCOMPLETE_ORDER);
				}
			}
		}
	}

	@Override
	public DeliveryLineCandidate getLineCandidateForShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
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
