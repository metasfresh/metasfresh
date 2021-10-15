/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Value
@ToString
public class PickingJobStep
{
	@NonNull PickingJobStepId id;

	@NonNull ShipmentScheduleId shipmentScheduleId;
	@Nullable PickingCandidateId pickingCandidateId;

	//
	// What?
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyToPick;
	@NonNull Quantity qtyPicked;
	@Nullable QtyRejectedReasonCode qtyRejectedReasonCode;

	//
	// From where?
	@NonNull LocatorId locatorId;
	@NonNull String locatorName;

	@NonNull HuId huId;
	@NonNull HUBarcode huBarcode;

	@Builder(toBuilder = true)
	private PickingJobStep(
			@NonNull final PickingJobStepId id,
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final PickingCandidateId pickingCandidateId,
			//
			// What?
			@NonNull final ProductId productId,
			@NonNull final ITranslatableString productName,
			@NonNull final Quantity qtyToPick,
			@Nullable final Quantity qtyPicked,
			@Nullable final QtyRejectedReasonCode qtyRejectedReasonCode,
			//
			// From where?
			@NonNull final LocatorId locatorId,
			@NonNull final String locatorName,
			@NonNull final HuId huId,
			@NonNull final HUBarcode huBarcode)
	{
		this.id = id;
		this.shipmentScheduleId = shipmentScheduleId;
		this.pickingCandidateId = pickingCandidateId;
		this.productId = productId;
		this.productName = productName;

		Quantity.assertSameUOM(qtyToPick, qtyPicked); // make sure they have the same UOM
		this.qtyToPick = qtyToPick;
		this.qtyPicked = qtyPicked != null ? qtyPicked : qtyToPick.toZero();
		this.qtyRejectedReasonCode = validateQtyRejectedReasonCode(qtyRejectedReasonCode, this.qtyToPick, this.qtyPicked);

		this.locatorId = locatorId;
		this.locatorName = locatorName;
		this.huId = huId;
		this.huBarcode = huBarcode;
	}

	private static QtyRejectedReasonCode validateQtyRejectedReasonCode(
			final @Nullable QtyRejectedReasonCode qtyRejectedReasonCode,
			final @NonNull Quantity qtyToPick,
			final @NonNull Quantity qtyPicked)
	{
		if (isSomethingReported(qtyPicked, qtyRejectedReasonCode))
		{
			final Quantity qtyRejected = qtyToPick.subtract(qtyPicked);
			if (qtyRejected.signum() < 0)
			{
				throw new AdempiereException("Maximum allowed qty to pick is " + qtyToPick);
			}
			else if (qtyRejected.signum() > 0)
			{
				if (qtyRejectedReasonCode == null)
				{
					throw new AdempiereException("Reject reason must be provided when QtyRejected != 0");
				}
				return qtyRejectedReasonCode;
			}
			else // qtyRejected == 0
			{
				if (qtyRejectedReasonCode != null)
				{
					throw new AdempiereException("No reject reason needs to be provided when the whole qty was picked");
				}
				return null;
			}
		}
		else
		{
			// nothing reported
			return null;
		}
	}

	public I_C_UOM getUOM()
	{
		return qtyToPick.getUOM();
	}

	public Quantity getQtyRejected()
	{
		return qtyToPick.subtract(qtyPicked);
	}

	public Optional<QtyRejectedWithReason> getQtyRejectedWithReason()
	{
		return qtyRejectedReasonCode != null
				? Optional.of(QtyRejectedWithReason.of(getQtyRejected(), qtyRejectedReasonCode))
				: Optional.empty();
	}

	public boolean isSomethingReported()
	{
		return isSomethingReported(qtyPicked, qtyRejectedReasonCode);
	}

	private static boolean isSomethingReported(@Nullable final Quantity qtyPicked, @Nullable final QtyRejectedReasonCode qtyRejectedReasonCode)
	{
		final boolean qtyPickedNotZero = qtyPicked != null && qtyPicked.signum() != 0;
		return qtyPickedNotZero || qtyRejectedReasonCode != null;
	}

	public PickingJobStep applyingEvent(@NonNull final PickingJobStepEvent event)
	{
		final PickingJobStepEventType eventType = event.getEventType();
		switch (eventType)
		{
			case PICK:
			{
				final BigDecimal qtyPicked = event.getQtyPicked();
				if (qtyPicked == null)
				{
					throw new AdempiereException("qtyPicked must be provided in " + event);
				}
				return toBuilder()
						.qtyPicked(Quantity.of(qtyPicked, getUOM()))
						.qtyRejectedReasonCode(event.getQtyRejectedReasonCode())
						.build();
			}
			case UNPICK:
			{
				return toBuilder()
						.qtyPicked(null)
						.qtyRejectedReasonCode(null)
						.build();
			}
			default:
			{
				throw new AdempiereException("Unknown event type: " + eventType);
			}
		}
	}

	public PickingJobStep withPickingCandidateId(@NonNull final PickingCandidateId pickingCandidateId)
	{
		return toBuilder().pickingCandidateId(pickingCandidateId).build();
	}
}
