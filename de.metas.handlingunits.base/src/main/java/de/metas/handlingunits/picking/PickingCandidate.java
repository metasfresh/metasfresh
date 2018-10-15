package de.metas.handlingunits.picking;

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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;

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

@Data
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
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

	@NonNull
	private final HuId pickFromHuId;

	@NonNull
	private Quantity qtyPicked;

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

	public void assertApprovable()
	{
		assertDraft();

		if (pickStatus.isToBePicked())
		{
			throw new AdempiereException("Picking candidate not ready for approvable because " + pickStatus)
					.setParameter("pickingCandidate", this);
		}
	}

	public void changeStatusToDraft()
	{
		setStatus(PickingCandidateStatus.Draft);
	}

	public void changeStatusToProcessed(@NonNull final HuId packedToHuId)
	{
		setPackedToHuId(packedToHuId);
		setStatus(PickingCandidateStatus.Processed);
	}

	public void changeStatusToClosed()
	{
		setStatus(PickingCandidateStatus.Closed);
	}
}
