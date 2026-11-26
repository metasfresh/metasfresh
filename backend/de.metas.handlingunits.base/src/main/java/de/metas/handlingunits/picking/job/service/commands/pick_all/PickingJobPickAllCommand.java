package de.metas.handlingunits.picking.job.service.commands.pick_all;

import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.i18n.AdMessageKey;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

@Builder
public class PickingJobPickAllCommand
{
	public static final AdMessageKey MSG_CU_QUICK_PACK_ALL_NOT_ALLOWED = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_QUICK_PACK_ALL_NOT_ALLOWED");

	// services
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final MobileUIPickingUserProfileService configService;

	// params
	@NonNull PickingJobId pickingJobId;
	@NonNull UserId callerId;

	public PickingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		PickingJob pickingJob = pickingJobService.getById(pickingJobId);
		pickingJob.assertCanBeEditedBy(callerId);
		assertNoLabelRequiredLine(pickingJob);

		PickingJob pickingJobChanged = pickingJob;

		for (PickingJobLine line : pickingJob.getLines())
		{
			pickingJobChanged = pickingJobService.newPickCommand()
					.pickingJob(pickingJobChanged)
					.pickingJobLineId(line.getId())
					.qtyToPickBD(line.getQtyRemainingToPick().toBigDecimal())
					//
					.build().execute();
		}

		return pickingJobService.complete(pickingJobChanged);
	}

	/** Pick-all books without a label, so one line in a label-required CU quantity entry mode refuses the whole job. */
	private void assertNoLabelRequiredLine(final PickingJob pickingJob)
	{
		if (configService.isAnyLabelRequiredLine(pickingJob.getLines()))
		{
			throw new AdempiereException(MSG_CU_QUICK_PACK_ALL_NOT_ALLOWED);
		}
	}
}
