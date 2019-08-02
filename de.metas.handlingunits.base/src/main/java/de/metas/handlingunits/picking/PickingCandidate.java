package de.metas.handlingunits.picking;

import java.math.BigDecimal;
import java.util.Collection;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@ToString
@Getter
public class PickingCandidate
{
	@Nullable
	private PickingCandidateId id;

	@NonNull
	@Default
	@Setter(AccessLevel.PRIVATE)
	private PickingCandidateStatus status = PickingCandidateStatus.Draft;

	@NonNull
	@Default
	private PickingCandidatePickStatus pickStatus = PickingCandidatePickStatus.TO_BE_PICKED;

	@NonNull
	@Default
	private PickingCandidateApprovalStatus approvalStatus = PickingCandidateApprovalStatus.TO_BE_APPROVED;

	@Nullable
	private final HuId pickFromHuId;

	@NonNull
	private Quantity qtyPicked;

	@Nullable
	private BigDecimal qtyReview;

	@Nullable
	private HuPackingInstructionsId packToInstructionsId;
	@Nullable
	@Setter(AccessLevel.PRIVATE)
	private HuId packedToHuId;

	@NonNull
	private final ShipmentScheduleId shipmentScheduleId;
	@Nullable
	private final PickingSlotId pickingSlotId;

	public static ImmutableSet<PickingSlotId> extractPickingSlotIds(@NonNull final Collection<PickingCandidate> candidates)
	{
		if (candidates.isEmpty())
		{
			return ImmutableSet.of();
		}

		return candidates.stream()
				.map(PickingCandidate::getPickingSlotId)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
	}

	public void markSaved(@NonNull final PickingCandidateId id)
	{
		if (this.id == null)
		{
			this.id = id;
		}
		else if (!this.id.equals(id))
		{
			throw new AdempiereException("Changing picking candidate's ID from " + this.id + " to " + id + " is not allowed");
		}
	}

	public void assertDraft()
	{
		if (!isDraft())
		{
			throw new AdempiereException("Picking candidate already processed")
					.setParameter("pickingCandidate", this);
		}
	}

	public boolean isDraft()
	{
		return PickingCandidateStatus.Draft.equals(getStatus());
	}

	public void assertProcessed()
	{
		if (!isProcessed())
		{
			throw new AdempiereException("Picking candidate is not processed")
					.setParameter("pickingCandidate", this);
		}
	}

	public boolean isProcessed()
	{
		return PickingCandidateStatus.Processed.equals(getStatus());
	}

	public boolean isRejectedToPick()
	{
		return PickingCandidatePickStatus.WILL_NOT_BE_PICKED.equals(getPickStatus());
	}

	public boolean isPacked()
	{
		return PickingCandidatePickStatus.PACKED.equals(getPickStatus())
				&& getPackedToHuId() != null;
	}

	private void assertNotApproved()
	{
		if (approvalStatus.isApproved())
		{
			throw new AdempiereException("Picking candidate shall not be already approved")
					.setParameter("pickingCandidate", this);
		}
	}

	public void changeStatusToDraft()
	{
		setStatus(PickingCandidateStatus.Draft);
	}

	public void changeStatusToProcessed()
	{
		changeStatusToProcessed(getPickFromHuId());
	}

	public void changeStatusToProcessed(@Nullable final HuId packedToHuId)
	{
		setPackedToHuId(packedToHuId);
		setStatus(PickingCandidateStatus.Processed);
	}

	public void changeStatusToClosed()
	{
		setStatus(PickingCandidateStatus.Closed);
	}

	public void pick(@NonNull final Quantity qtyPicked)
	{
		assertDraft();
		assertNotApproved();

		this.qtyPicked = qtyPicked;
		pickStatus = computePickOrPackStatus(this.packToInstructionsId);
	}

	public void rejectPicking(@NonNull final Quantity qtyRejected)
	{
		assertDraft();
		assertNotApproved();

		qtyPicked = qtyRejected;
		pickStatus = PickingCandidatePickStatus.WILL_NOT_BE_PICKED;
	}

	public void packTo(final HuPackingInstructionsId packToInstructionsId)
	{
		assertDraft();

		if (!pickStatus.isPickedOrPacked())
		{
			throw new AdempiereException("Invalid status when changing packing instructions: " + pickStatus);
		}

		this.packToInstructionsId = packToInstructionsId;
		pickStatus = computePickOrPackStatus(this.packToInstructionsId);
	}

	public void reviewPicking(final BigDecimal qtyReview)
	{
		assertDraft();

		if (!pickStatus.isPickedOrPacked() && !pickStatus.isPickRejected())
		{
			throw new AdempiereException("Picking candidate is not approvable because it's not picked or packed: " + this);
		}

		this.qtyReview = qtyReview;
		approvalStatus = computeApprovalStatus(qtyPicked, this.qtyReview, pickStatus);
	}

	private static PickingCandidateApprovalStatus computeApprovalStatus(final Quantity qtyPicked, final BigDecimal qtyReview, final PickingCandidatePickStatus pickStatus)
	{
		if (qtyReview == null)
		{
			return PickingCandidateApprovalStatus.TO_BE_APPROVED;
		}

		//
		final BigDecimal qtyReviewToMatch;
		if (pickStatus.isPickRejected())
		{
			qtyReviewToMatch = BigDecimal.ZERO;
		}
		else
		{
			qtyReviewToMatch = qtyPicked.toBigDecimal();
		}

		//
		if (qtyReview.compareTo(qtyReviewToMatch) == 0)
		{
			return PickingCandidateApprovalStatus.APPROVED;
		}
		else
		{
			return PickingCandidateApprovalStatus.REJECTED;
		}
	}

	private static PickingCandidatePickStatus computePickOrPackStatus(final HuPackingInstructionsId packToInstructionsId)
	{
		return packToInstructionsId != null ? PickingCandidatePickStatus.PACKED : PickingCandidatePickStatus.PICKED;
	}
}
