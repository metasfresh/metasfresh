package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.picking.plan.PickFromHU;
import de.metas.handlingunits.picking.plan.PickingPlan;
import de.metas.handlingunits.picking.plan.PickingPlanLine;
import de.metas.handlingunits.picking.plan.PickingPlanLineType;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import java.util.Objects;

public class CreatePickingCandidatesFromPickingPlanCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final PickingCandidateRepository pickingCandidateRepository;
	private final PickingPlan originalPickingPlan;

	@Builder
	private CreatePickingCandidatesFromPickingPlanCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final PickingPlan pickingPlan)
	{
		this.pickingCandidateRepository = pickingCandidateRepository;
		this.originalPickingPlan = pickingPlan;
	}

	public PickingPlan execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingPlan executeInTrx()
	{
		return originalPickingPlan.toBuilder()
				.lines(originalPickingPlan.getLines()
						.stream()
						.map(this::createPickingCandidates)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private PickingPlanLine createPickingCandidates(@NonNull final PickingPlanLine originalPlanLine)
	{
		if (originalPlanLine.getSourceDocumentInfo().getExistingPickingCandidate() != null)
		{
			return originalPlanLine;
		}

		switch (originalPlanLine.getType())
		{
			case PICK_FROM_HU:
				return createPickingCandidate_pickFromHU(originalPlanLine);
			case UNALLOCABLE:
				// skip unallocable lines
				return originalPlanLine;
			case PICK_FROM_PICKING_ORDER:
			case ISSUE_COMPONENTS_TO_PICKING_ORDER:
			default:
				throw new AdempiereException("Picking plan line type not supported: " + originalPlanLine);
		}
	}

	private PickingPlanLine createPickingCandidate_pickFromHU(final @NonNull PickingPlanLine originalPlanLine)
	{
		final PickFromHU pickFromHU = Objects.requireNonNull(originalPlanLine.getPickFromHU());

		final PickingCandidate pickingCandidate = PickingCandidate.builder()
				.processingStatus(PickingCandidateStatus.Draft)
				.qtyPicked(originalPlanLine.getQty())
				.shipmentScheduleId(originalPlanLine.getSourceDocumentInfo().getShipmentScheduleId())
				.pickFrom(PickFrom.ofHuId(pickFromHU.getHuId()))
				.pickingSlotId(null) // N/A
				.build();
		pickingCandidate.assertDraft();
		pickingCandidateRepository.save(pickingCandidate);

		return originalPlanLine.toBuilder()
				.sourceDocumentInfo(originalPlanLine.getSourceDocumentInfo().withExistingPickingCandidate(pickingCandidate))
				.build();
	}
}
