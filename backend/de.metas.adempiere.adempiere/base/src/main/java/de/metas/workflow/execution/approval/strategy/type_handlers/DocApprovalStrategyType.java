package de.metas.workflow.execution.approval.strategy.type_handlers;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_Doc_Approval_Strategy_Line;

@RequiredArgsConstructor
@Getter
public enum DocApprovalStrategyType implements ReferenceListAwareEnum
{
	WorkflowResponsible(X_C_Doc_Approval_Strategy_Line.TYPE_WorkflowResponsible),
	Requestor(X_C_Doc_Approval_Strategy_Line.TYPE_Requestor),
	ProjectManager(X_C_Doc_Approval_Strategy_Line.TYPE_ProjectManager),
	Job(X_C_Doc_Approval_Strategy_Line.TYPE_Job),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<DocApprovalStrategyType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static DocApprovalStrategyType ofCode(@NonNull String code) {return index.ofCode(code);}
}
