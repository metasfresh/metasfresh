package de.metas.manufacturing.job.model;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroup;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupId;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_S_ResourceType;

@Getter
@RequiredArgsConstructor
public enum ManufacturingJobFacetGroup
{
	RESOURCE_TYPE("RT", TranslatableStrings.adElementOrMessage(I_S_ResourceType.COLUMNNAME_S_ResourceType_ID)),
	;

	@NonNull private final String code;
	@NonNull private final ITranslatableString caption;

	public static ManufacturingJobFacetGroup ofWorkflowLaunchersFacetGroupId(@NonNull final WorkflowLaunchersFacetGroupId groupId)
	{
		final String code = groupId.getAsString();
		if (RESOURCE_TYPE.code.equals(code))
		{
			return RESOURCE_TYPE;
		}
		else
		{
			throw new AdempiereException("Cannot convert `" + groupId + "` to " + ManufacturingJobFacetGroup.class);
		}
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

}
