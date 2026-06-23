package de.metas.hu_consolidation.mobile.job.commands.set_target_grais;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobId;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobRepository;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

@Builder
public class SetTargetGraisCommand
{
	@NonNull private final HUConsolidationJobRepository jobRepository;
	@NonNull private final HUGraiService huGraiService;
	@NonNull private final HUConsolidationJobId jobId;
	@NonNull private final UserId callerId;
	@NonNull private final GRAISet graiSet;

	public void execute()
	{
		final HUConsolidationJob job = jobRepository.getById(jobId);
		job.assertUserCanEdit(callerId);

		final HUConsolidationTarget currentTarget = job.getCurrentTargetNotNull();
		if (!currentTarget.isExistingLU())
		{
			throw new AdempiereException("Cannot set GRAIs on a new (not yet created) target LU");
		}

		final HuId luId = currentTarget.getLuIdNotNull();
		huGraiService.setGrais(luId, graiSet);
	}
}
