package de.metas.material.planning.pporder;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_WF_Node;

import javax.annotation.Nullable;
import java.util.Optional;

@AllArgsConstructor
public enum PPRoutingActivityType implements ReferenceListAwareEnum
{
	WorkReport(X_AD_WF_Node.PP_ACTIVITY_TYPE_WorkReport),
	RawMaterialsIssue(X_AD_WF_Node.PP_ACTIVITY_TYPE_RawMaterialsIssue),
	RawMaterialsIssueAdjustment(X_AD_WF_Node.PP_ACTIVITY_TYPE_RawMaterialsIssueAdjustment),
	MaterialReceipt(X_AD_WF_Node.PP_ACTIVITY_TYPE_MaterialReceipt),
	ActivityConfirmation(X_AD_WF_Node.PP_ACTIVITY_TYPE_ActivityConfirmation),
	GenerateHUQRCodes(X_AD_WF_Node.PP_ACTIVITY_TYPE_GenerateHUQRCodes),
	ScanScaleDevice(X_AD_WF_Node.PP_ACTIVITY_TYPE_ScanScaleDevice),
	CallExternalSystem(X_AD_WF_Node.PP_ACTIVITY_TYPE_CallExternalSystem);

	@Getter
	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<PPRoutingActivityType> index = ReferenceListAwareEnums.index(values());

	public static PPRoutingActivityType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static Optional<PPRoutingActivityType> ofNullableCode(@Nullable final String code)
	{
		return index.optionalOfNullableCode(code);
	}
}
