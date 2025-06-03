package de.metas.hu_consolidation.mobile.service.commands;

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
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;
import java.util.List;

public class ConsolidateCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final PickingSlotService pickingSlotService;
	@NonNull private final PickingSlotId fromPickingSlotId;

	private HUTransformService huTransformService; // lazy
	private PickingSlotQueue _fromPickingSlotQueue; // lazy
	private HUConsolidationJob job;

	@Builder
	public ConsolidateCommand(
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final PickingSlotService pickingSlotService,
			@NonNull final ConsolidateRequest request)
	{
		this.huQRCodesService = huQRCodesService;
		this.pickingSlotService = pickingSlotService;
		this.job = request.getJob();
		this.fromPickingSlotId = request.getFromPickingSlotId();
	}

	public HUConsolidationJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private HUConsolidationJob execute0()
	{
		huTransformService = HUTransformService.newInstance();

		final PickingSlotQueue fromPickingSlotQueue = getFromPickingSlotQueue();
		final List<I_M_HU> hus = handlingUnitsBL.getByIds(fromPickingSlotQueue.getHuIds());
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

		if (!lus.isEmpty())
		{
			consolidateFromLUs(lus);
		}
		if (!tus.isEmpty())
		{
			consolidateFromTUs(tus);
		}

		return job;
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
