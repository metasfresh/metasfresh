package de.metas.handlingunits.picking;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

@EqualsAndHashCode(of = "id")
@ToString
@Getter
public class PickingCandidate
{
	@Nullable
	private PickingCandidateId id;

	@NonNull
	@Setter(AccessLevel.PRIVATE)
	private PickingCandidateStatus processingStatus;
	@NonNull
	private PickingCandidatePickStatus pickStatus;
	@NonNull
	private PickingCandidateApprovalStatus approvalStatus;

	@NonNull
	@Setter(AccessLevel.PRIVATE)
	private PickFrom pickFrom;

	@NonNull
	private Quantity qtyPicked;
	@Nullable
	private BigDecimal qtyReview;
	@Nullable
	private QtyRejectedWithReason qtyRejected;

	@Nullable private PackToSpec packToSpec;
	@Setter(AccessLevel.PRIVATE)
	@Nullable private HuId packedToHuId;

	@NonNull
	private final ShipmentScheduleId shipmentScheduleId;
	@Nullable
	@With
	private final PickingSlotId pickingSlotId;

	@NonNull
	private ImmutableList<PickingCandidateIssueToBOMLine> issuesToPickingOrder;

	@Builder(toBuilder = true)
	private PickingCandidate(
			@Nullable final PickingCandidateId id,
			//
			@Nullable final PickingCandidateStatus processingStatus,
			@Nullable final PickingCandidatePickStatus pickStatus,
			@Nullable final PickingCandidateApprovalStatus approvalStatus,
			//
			@NonNull final PickFrom pickFrom,
			//
			@NonNull final Quantity qtyPicked,
			@Nullable final BigDecimal qtyReview,
			@Nullable final QtyRejectedWithReason qtyRejected,
			//
			@Nullable final PackToSpec packToSpec,
			@Nullable final HuId packedToHuId,
			//
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final PickingSlotId pickingSlotId,
			//
			@Nullable @Singular("issueToPickingOrder") final ImmutableList<PickingCandidateIssueToBOMLine> issuesToPickingOrder)
	{
		this.id = id;
		this.processingStatus = CoalesceUtil.coalesceNotNull(processingStatus, PickingCandidateStatus.Draft);
		this.pickStatus = CoalesceUtil.coalesceNotNull(pickStatus, PickingCandidatePickStatus.TO_BE_PICKED);
		this.approvalStatus = CoalesceUtil.coalesceNotNull(approvalStatus, PickingCandidateApprovalStatus.TO_BE_APPROVED);

		this.pickFrom = pickFrom;
		this.pickingSlotId = pickingSlotId;

		Quantity.assertSameUOM(qtyPicked, qtyRejected != null ? qtyRejected.toQuantity() : null);
		this.qtyPicked = qtyPicked;
		this.qtyReview = qtyReview;
		this.qtyRejected = qtyRejected;

		this.packToSpec = packToSpec;
		this.packedToHuId = packedToHuId;

		this.shipmentScheduleId = shipmentScheduleId;

		this.issuesToPickingOrder = issuesToPickingOrder != null ? issuesToPickingOrder : ImmutableList.of();
	}

	public static ImmutableSet<PickingSlotId> extractPickingSlotIds(@NonNull final Collection<PickingCandidate> candidates)
	{
		if (candidates.isEmpty())
		{
			return ImmutableSet.of();
		}

		return candidates.stream()
				.map(PickingCandidate::getPickingSlotId)
				.filter(Objects::nonNull)
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

	public boolean isDraft() {return getProcessingStatus().isDraft();}

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
		return getProcessingStatus().isProcessed();
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

	public void assertNotApproved()
	{
		if (approvalStatus.isApproved())
		{
			throw new AdempiereException("Picking candidate shall not be already approved")
					.setParameter("pickingCandidate", this);
		}
	}

	public void changeStatusToDraft()
	{
		setPackedToHuId(null);
		setProcessingStatus(PickingCandidateStatus.Draft);
	}

	public void changeStatusToProcessed()
	{
		changeStatusToProcessed(getPickFrom().getHuId());
	}

	public void changeStatusToProcessed(@Nullable final HuId packedToHuId)
	{
		setPackedToHuId(packedToHuId);
		setProcessingStatus(PickingCandidateStatus.Processed);
	}

	public void changeStatusToClosed()
	{
		setProcessingStatus(PickingCandidateStatus.Closed);
	}

	public void pick(@NonNull final Quantity qtyPicked)
	{
		assertDraft();

		this.qtyPicked = qtyPicked;
		pickStatus = computePickOrPackStatus(this.packToSpec);
		approvalStatus = computeApprovalStatus(this.qtyPicked, this.qtyReview, this.pickStatus);
	}

	public void rejectPicking(@NonNull final Quantity qtyRejected)
	{
		assertDraft();
		assertNotApproved();

		qtyPicked = qtyRejected;
		pickStatus = PickingCandidatePickStatus.WILL_NOT_BE_PICKED;
	}

	public void rejectPickingPartially(@NonNull final QtyRejectedWithReason qtyRejected)
	{
		assertDraft();
		// assertNotApproved();
		Quantity.assertSameUOM(qtyPicked, qtyRejected.toQuantity());

		this.qtyRejected = qtyRejected;
	}

	public void packTo(@Nullable final PackToSpec packToSpec)
	{
		assertDraft();

		if (!pickStatus.isEligibleForPacking())
		{
			throw new AdempiereException("Invalid status when changing packing instructions: " + pickStatus);
		}

		this.packToSpec = packToSpec;
		pickStatus = computePickOrPackStatus(this.packToSpec);
	}

	public void reviewPicking(final BigDecimal qtyReview)
	{
		assertDraft();

		if (!pickStatus.isEligibleForReview())
		{
			throw new AdempiereException("Picking candidate cannot be approved because it's not picked or packed yet: " + this);
		}

		this.qtyReview = qtyReview;
		approvalStatus = computeApprovalStatus(qtyPicked, this.qtyReview, pickStatus);
	}

	public void reviewPicking()
	{
		reviewPicking(qtyPicked.toBigDecimal());
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

	private static PickingCandidatePickStatus computePickOrPackStatus(@Nullable final PackToSpec packToSpec)
	{
		return packToSpec != null ? PickingCandidatePickStatus.PACKED : PickingCandidatePickStatus.PICKED;
	}

	public boolean isPickFromPickingOrder()
	{
		return getPickFrom().isPickFromPickingOrder();
	}

	public void issueToPickingOrder(@Nullable final List<PickingCandidateIssueToBOMLine> issuesToPickingOrder)
	{
		this.issuesToPickingOrder = issuesToPickingOrder != null
				? ImmutableList.copyOf(issuesToPickingOrder)
				: ImmutableList.of();
	}

	public PickingCandidateSnapshot snapshot()
	{
		return PickingCandidateSnapshot.builder()
				.id(getId())
				.qtyReview(getQtyReview())
				.pickStatus(getPickStatus())
				.approvalStatus(getApprovalStatus())
				.processingStatus(getProcessingStatus())
				.build();
	}
}
