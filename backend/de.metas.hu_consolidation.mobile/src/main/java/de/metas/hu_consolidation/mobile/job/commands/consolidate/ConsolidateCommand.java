package de.metas.hu_consolidation.mobile.job.commands.consolidate;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobId;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobRepository;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConsolidateCommand
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final HUConsolidationJobRepository jobRepository;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final PickingSlotService pickingSlotService;

	// Params
	@NonNull private final UserId callerId;
	@NonNull private final HUConsolidationJobId jobId;
	@NonNull private final PickingSlotId fromPickingSlotId;
	@Nullable private final HuId huId;

	// State
	private HUTransformService huTransformService; // lazy
	private HUConsolidationJob job;
	private PickingSlotQueue _fromPickingSlotQueue; // lazy

	@Builder
	public ConsolidateCommand(
			@NonNull final HUConsolidationJobRepository jobRepository,
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final PickingSlotService pickingSlotService,
			//
			@NonNull final ConsolidateRequest request)
	{
		this.jobRepository = jobRepository;
		this.huQRCodesService = huQRCodesService;
		this.pickingSlotService = pickingSlotService;

		this.callerId = request.getCallerId();
		this.jobId = request.getJobId();
		this.fromPickingSlotId = request.getFromPickingSlotId();
		this.huId = request.getHuId();
	}

	public HUConsolidationJob execute()
	{
		return jobRepository.updateById(jobId, this::execute0);
	}

	private HUConsolidationJob execute0(@NonNull final HUConsolidationJob jobInitial)
	{
		this.huTransformService = HUTransformService.newInstance();
		this.job = jobInitial;

		job.assertUserCanEdit(callerId);

		final Set<HuId> huIds = getHuIdsToConsolidate();
		final List<I_M_HU> hus = handlingUnitsBL.getByIds(huIds);
		if (hus.isEmpty())
		{
			throw new AdempiereException("No HUs were found in picking slot " + fromPickingSlotId);
		}

		final ArrayList<I_M_HU> tus = new ArrayList<>();
		final ArrayList<I_M_HU> lus = new ArrayList<>();
		for (final I_M_HU hu : hus)
		{
			if (handlingUnitsBL.isLoadingUnit(hu))
			{
				lus.add(hu);
			}
			else
			{
				tus.add(hu);
			}
		}

		consolidateFromLUs(lus);
		consolidateFromTUs(tus);
		pickingSlotService.removeFromPickingSlotQueue(fromPickingSlotId, huIds);

		return job;
	}

	private Set<HuId> getHuIdsToConsolidate()
	{
		final PickingSlotQueue fromPickingSlotQueue = getFromPickingSlotQueue();
		if (huId != null)
		{
			if (!fromPickingSlotQueue.containsHuId(huId))
			{
				throw new AdempiereException("No HU with ID " + huId + " was found in picking slot " + fromPickingSlotQueue.getPickingSlotId() + "!");
			}
			return ImmutableSet.of(huId);
		}
		else
		{
			return fromPickingSlotQueue.getHuIds();
		}
	}

	private PickingSlotQueue getFromPickingSlotQueue()
	{
		PickingSlotQueue pickingSlotQueue = this._fromPickingSlotQueue;
		if (pickingSlotQueue == null)
		{
			pickingSlotQueue = this._fromPickingSlotQueue = pickingSlotService.getPickingSlotQueue(fromPickingSlotId);
		}
		return pickingSlotQueue;
	}

	private void setCurrentTarget(@NonNull final HUConsolidationTarget target)
	{
		this.job = this.job.withCurrentTarget(target);
	}

	@NonNull
	private HUConsolidationTarget getCurrentTarget()
	{
		return job.getCurrentTargetNotNull();
	}

	private void consolidateFromLUs(@NonNull final List<I_M_HU> fromLUs)
	{
		if (fromLUs.isEmpty())
		{
			return;
		}

		getCurrentTarget().apply(new HUConsolidationTarget.CaseConsumer()
		{
			@Override
			public void newLU(final HuPackingInstructionsId luPackingInstructionsId) {consolidate_FromLUs_ToNewLU(fromLUs, luPackingInstructionsId);}

			@Override
			public void existingLU(final HuId luId, final HUQRCode luQRCode) {consolidate_FromLUs_ToExistingLU(fromLUs, luId, luQRCode);}
		});
	}

	@SuppressWarnings("unused")
	private void consolidate_FromLUs_ToNewLU(@NonNull final List<I_M_HU> fromLUs, @NonNull final HuPackingInstructionsId luPackingInstructionsId)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	@SuppressWarnings("unused")
	private void consolidate_FromLUs_ToExistingLU(@NonNull final List<I_M_HU> fromLUs, @NonNull final HuId luId, @NonNull final HUQRCode luQRCode)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	private void consolidateFromTUs(@NonNull final List<I_M_HU> fromTUs)
	{
		if (fromTUs.isEmpty())
		{
			return;
		}

		getCurrentTarget().apply(new HUConsolidationTarget.CaseConsumer()
		{
			@Override
			public void newLU(final HuPackingInstructionsId luPackingInstructionsId) {consolidate_FromTUs_ToNewLU(fromTUs, luPackingInstructionsId);}

			@Override
			public void existingLU(final HuId luId, final HUQRCode luQRCode) {consolidate_FromTUs_ToExistingLU(fromTUs, luId);}
		});
	}

	private void consolidate_FromTUs_ToNewLU(@NonNull final List<I_M_HU> fromTUs, @NonNull final HuPackingInstructionsId luPackingInstructionsId)
	{
		if (fromTUs.isEmpty())
		{
			return;
		}

		final I_M_HU firstTU = fromTUs.get(0);
		final I_M_HU lu = huTransformService.tuToNewLU(firstTU, QtyTU.ONE, luPackingInstructionsId)
				.getSingleLURecord();

		if (fromTUs.size() > 1)
		{
			final List<I_M_HU> remainingTUs = fromTUs.subList(1, fromTUs.size());
			huTransformService.tusToExistingLU(remainingTUs, lu);
		}

		final HuId luId = HuId.ofRepoId(lu.getM_HU_ID());
		final HUQRCode qrCode = huQRCodesService.getQRCodeByHuId(luId);
		setCurrentTarget(HUConsolidationTarget.ofExistingLU(luId, qrCode));
	}

	private void consolidate_FromTUs_ToExistingLU(@NonNull final List<I_M_HU> fromTUs, @NonNull final HuId luId)
	{
		huTransformService.tusToExistingLUId(fromTUs, luId);
	}
}
