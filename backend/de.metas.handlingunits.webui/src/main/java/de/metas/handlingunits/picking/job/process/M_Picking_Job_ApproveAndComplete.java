package de.metas.handlingunits.picking.job.process;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import org.compiere.SpringContextHolder;

import java.util.List;
import java.util.Objects;

public class M_Picking_Job_ApproveAndComplete extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final PickingJobService pickingJobService = SpringContextHolder.instance.getBean(PickingJobService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!pickingJobService.hasPickingJobsReadyToReview(getSelectedPickingJobIds()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Nothing ready to review");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final List<PickingJob> pickingJobsToReview = pickingJobService.getByIsReadyToReview(getSelectedPickingJobIds());
		pickingJobsToReview.forEach(pickingJobService::approveAndComplete);
		return MSG_OK;
	}

	private ImmutableSet<PickingJobId> getSelectedPickingJobIds()
	{
		return streamSelectedRows()
				.map(row -> row.getId().toId(PickingJobId::ofRepoIdOrNull))
				.filter(Objects::nonNull)
				.limit(100)
				.collect(ImmutableSet.toImmutableSet());
	}
}
