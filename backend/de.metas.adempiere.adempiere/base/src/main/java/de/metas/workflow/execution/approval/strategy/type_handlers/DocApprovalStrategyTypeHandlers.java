package de.metas.workflow.execution.approval.strategy.type_handlers;

import de.metas.job.JobService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocApprovalStrategyTypeHandlers
{
	@NonNull private final WorkflowResponsibleHandler workflowResponsibleHandler = new WorkflowResponsibleHandler();
	@NonNull private final RequestorHandler requestorHandler = new RequestorHandler();
	@NonNull private final ProjectManagerHandler projectManagerHandler = new ProjectManagerHandler();
	@NonNull private final JobHandler jobHandler;

	public static DocApprovalStrategyTypeHandlers newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new DocApprovalStrategyTypeHandlers(new JobHandler(JobService.newInstanceForUnitTesting()));
	}

	public DocApprovalStrategyTypeHandler getHandler(@NonNull final DocApprovalStrategyType type)
	{
		return switch (type)
		{
			case WorkflowResponsible -> workflowResponsibleHandler;
			case Requestor -> requestorHandler;
			case ProjectManager -> projectManagerHandler;
			case Job -> jobHandler;
		};
	}
}
