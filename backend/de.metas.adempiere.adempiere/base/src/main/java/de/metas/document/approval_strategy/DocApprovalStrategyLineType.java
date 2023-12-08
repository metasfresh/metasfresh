package de.metas.document.approval_strategy;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_Doc_Approval_Strategy_Line;

@RequiredArgsConstructor
@Getter
public enum DocApprovalStrategyLineType implements ReferenceListAwareEnum
{
	RequestorSupervisorsHierarchy(X_C_Doc_Approval_Strategy_Line.TYPE_SupervisorsHierarchy),
	ProjectManager(X_C_Doc_Approval_Strategy_Line.TYPE_ProjectManager),
	Job(X_C_Doc_Approval_Strategy_Line.TYPE_Job),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<DocApprovalStrategyLineType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static DocApprovalStrategyLineType ofCode(@NonNull String code) {return index.ofCode(code);}
}
