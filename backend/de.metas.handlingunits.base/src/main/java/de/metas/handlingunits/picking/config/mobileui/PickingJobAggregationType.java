package de.metas.handlingunits.picking.config.mobileui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_MobileUI_UserProfile_Picking_Job;

import javax.annotation.Nullable;

@RequiredArgsConstructor
@Getter
public enum PickingJobAggregationType implements ReferenceListAwareEnum
{
	SALES_ORDER(X_MobileUI_UserProfile_Picking_Job.PICKINGJOBAGGREGATIONTYPE_Sales_order, true),
	PRODUCT(X_MobileUI_UserProfile_Picking_Job.PICKINGJOBAGGREGATIONTYPE_Product, true),
	;

	public static final PickingJobAggregationType DEFAULT = SALES_ORDER;

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<PickingJobAggregationType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;
	private final boolean defaultAllowPickingAnyHU;

	@NonNull
	public static PickingJobAggregationType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static PickingJobAggregationType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@JsonCreator
	public static PickingJobAggregationType ofJson(@NonNull final String code) {return ofCode(code);}

	@JsonValue
	public String toJson() {return getCode();}

	public boolean isLineLevelPickTarget() {return this == PRODUCT;}
}
