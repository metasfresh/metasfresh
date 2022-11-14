package de.metas.manufacturing.order.weighting.run.process;

import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRun;
import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunId;
import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class PP_Order_Weighting_Run_UnProcess extends JavaProcess implements IProcessPrecondition
{
	private final PPOrderWeightingRunService ppOrderWeightingRunService = SpringContextHolder.instance.getBean(PPOrderWeightingRunService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final PPOrderWeightingRunId weightingRunId = PPOrderWeightingRunId.ofRepoId(context.getSingleSelectedRecordId());
		final PPOrderWeightingRun weightingRun = ppOrderWeightingRunService.getById(weightingRunId);
		if (!weightingRun.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already not processed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PPOrderWeightingRunId weightingRunId = PPOrderWeightingRunId.ofRepoId(getRecord_ID());
		ppOrderWeightingRunService.unprocess(weightingRunId);
		return MSG_OK;
	}
}
