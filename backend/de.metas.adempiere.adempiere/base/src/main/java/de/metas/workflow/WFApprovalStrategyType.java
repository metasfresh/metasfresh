package de.metas.workflow;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_WF_Node;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum WFApprovalStrategyType implements ReferenceListAwareEnum
{
	Standard(X_AD_WF_Node.APPROVALSTRATEGY_Standard),
	RequestorHierarcyProjectManagerPlusCFO(X_AD_WF_Node.APPROVALSTRATEGY_RequestorHierarcyProjectManagerPlusCFO);

	@Getter private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<WFApprovalStrategyType> index = ReferenceListAwareEnums.index(values());

	public static WFApprovalStrategyType ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static WFApprovalStrategyType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}
}
