package de.metas.workflow.execution.approval.strategy.type_handlers;

import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DocApprovalStrategyTypeHandlers
{
	@NonNull private final WorkflowResponsibleHandler workflowResponsibleHandler = new WorkflowResponsibleHandler();
	@NonNull private final RequestorHandler requestorHandler = new RequestorHandler();
	@NonNull private final ProjectManagerHandler projectManagerHandler = new ProjectManagerHandler();
	@NonNull private final JobHandler jobHandler = new JobHandler();

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
