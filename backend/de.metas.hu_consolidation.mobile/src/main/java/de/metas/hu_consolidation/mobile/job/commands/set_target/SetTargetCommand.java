package de.metas.hu_consolidation.mobile.job.commands.set_target;

import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobId;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobRepository;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Builder
public class SetTargetCommand
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	@NonNull private final HUConsolidationJobRepository jobRepository;
	@NonNull UserId callerId;
	@NonNull final HUConsolidationJobId jobId;
	@Nullable final HUConsolidationTarget target;

	public HUConsolidationJob execute()
	{
		assertTargetValid();

		return jobRepository.updateById(jobId, job -> {
			job.assertUserCanEdit(callerId);
			return job.withCurrentTarget(target);
		});
	}

	private void assertTargetValid()
	{
		if (target != null && target.isExistingLU())
		{
			final I_M_HU lu = handlingUnitsBL.getById(target.getLuIdNotNull());
			if (!handlingUnitsBL.isLoadingUnit(lu))
			{
				throw new AdempiereException("Target is not an LU: " + target);
			}
			if (!huStatusBL.isStatusActiveOrPicked(lu))
			{
				throw new AdempiereException("Target LU is not active/picked: " + lu);
			}
		}
	}
}
