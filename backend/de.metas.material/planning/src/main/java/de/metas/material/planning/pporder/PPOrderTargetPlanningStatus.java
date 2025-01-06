package de.metas.material.planning.pporder;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_AD_WF_Node;
import org.eevolution.model.X_PP_Order;

import javax.annotation.Nullable;

@RequiredArgsConstructor
@Getter
public enum PPOrderTargetPlanningStatus implements ReferenceListAwareEnum
{
	REVIEW(X_AD_WF_Node.TARGETPLANNINGSTATUS_Review),
	COMPLETE(X_AD_WF_Node.TARGETPLANNINGSTATUS_Complete),
	;

	public static final int AD_REFERENCE_ID = X_PP_Order.PLANNINGSTATUS_AD_Reference_ID;

	private static final ReferenceListAwareEnums.ValuesIndex<PPOrderTargetPlanningStatus> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static PPOrderTargetPlanningStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static PPOrderTargetPlanningStatus ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}
}
