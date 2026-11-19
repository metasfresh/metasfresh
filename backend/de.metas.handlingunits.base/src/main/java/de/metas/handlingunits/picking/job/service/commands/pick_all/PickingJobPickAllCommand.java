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
	private static final AdMessageKey MSG_CU_QUICK_PACK_ALL_NOT_ALLOWED = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_QUICK_PACK_ALL_NOT_ALLOWED");

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
		assertNoLineUsesNewCUQtyEntryMode(pickingJob);

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

	/**
	 * Pick-all books every line's open quantity without a label scan, which would bypass the pack-ID duplicate check, the box-first rule
	 * and the label's lot / best-before of the new CU quantity entry modes. The mode is resolved per line (customer value, else profile value);
	 * one line in a new mode refuses the whole job. Independent of {@code IsAllowQuickPackAll}: a direct call is refused the same way.
	 */
	private void assertNoLineUsesNewCUQtyEntryMode(final PickingJob pickingJob)
	{
		final boolean anyLineInNewMode = pickingJob.getLines().stream()
				.anyMatch(line -> configService.getPickingJobOptions(line.getCustomerId()).getCUQtyEntryModeOrDefault().isNewMode());
		if (anyLineInNewMode)
		{
			throw new AdempiereException(MSG_CU_QUICK_PACK_ALL_NOT_ALLOWED).markAsUserValidationError();
		}
	}
}
