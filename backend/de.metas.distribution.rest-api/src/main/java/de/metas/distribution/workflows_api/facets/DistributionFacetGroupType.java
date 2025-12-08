package de.metas.distribution.workflows_api.facets;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroup;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupId;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DistributionFacetGroupType
{
	WAREHOUSE_FROM("warehouseFrom", TranslatableStrings.adElementOrMessage("M_Warehouse_From_ID")),
	WAREHOUSE_TO("warehouseTo", TranslatableStrings.adElementOrMessage("M_Warehouse_To_ID")),
	SALES_ORDER("salesOrderNo", TranslatableStrings.adElementOrMessage("C_OrderSO_ID")),
	MANUFACTURING_ORDER_NO("mfgOrderNo", TranslatableStrings.adElementOrMessage("PP_Order_ID")),
	DATE_PROMISED("datePromised", TranslatableStrings.adElementOrMessage("DatePromised")),
	PRODUCT("product", TranslatableStrings.adElementOrMessage("M_Product_ID")),
	QUANTITY("qty", TranslatableStrings.adElementOrMessage("Qty")),
	PLANT_RESOURCE_ID("plantResourceId", TranslatableStrings.adElementOrMessage("PP_Plant_ID")),
	;

	private static final ImmutableMap<String, DistributionFacetGroupType> byCode = Maps.uniqueIndex(Arrays.asList(values()), DistributionFacetGroupType::getCode);

	@NonNull private final String code;
	@NonNull private final ITranslatableString caption;

	public static DistributionFacetGroupType ofWorkflowLaunchersFacetGroupId(@NonNull final WorkflowLaunchersFacetGroupId groupId)
	{
		final String code = groupId.getAsString();
		final DistributionFacetGroupType type = byCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("Cannot convert `" + groupId + "` to " + DistributionFacetGroupType.class);
		}
		return type;
	}

	public WorkflowLaunchersFacetGroupId toWorkflowLaunchersFacetGroupId()
	{
		return WorkflowLaunchersFacetGroupId.ofString(code);
	}

	public WorkflowLaunchersFacetGroup.WorkflowLaunchersFacetGroupBuilder newWorkflowLaunchersFacetGroupBuilder()
	{
		return WorkflowLaunchersFacetGroup.builder()
				.id(toWorkflowLaunchersFacetGroupId())
				.caption(caption);
	}

	public int getSeqNo() {return ordinal();}
}
