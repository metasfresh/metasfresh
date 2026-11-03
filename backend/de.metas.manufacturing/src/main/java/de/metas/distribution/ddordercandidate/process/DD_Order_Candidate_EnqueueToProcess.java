package de.metas.distribution.ddordercandidate.process;

import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.impexp.InputDataSourceId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_DD_Order_Candidate;

public class DD_Order_Candidate_EnqueueToProcess extends JavaProcess implements IProcessPrecondition
{
	private final DDOrderCandidateService ddOrderCandidateService = SpringContextHolder.instance.getBean(DDOrderCandidateService.class);

	@Param(parameterName = I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID, mandatory = false)
	private InputDataSourceId inputDataSourceId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		ddOrderCandidateService.enqueueToProcess(DDOrderCandidateQuery.builder()
				.userSelectionFilter(getProcessInfo().getQueryFilterOrElse(null))
				.inputDataSourceId(inputDataSourceId)
				.processed(false)
				.onlyPositiveQtyToProcess(true)
				.build());
		return MSG_OK;
	}
}
