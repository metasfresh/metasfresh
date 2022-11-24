package de.metas.picking.workflow.handlers;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.NonNull;

final class PickingWFProcessUtils
{
	@Builder(builderMethodName = "workflowCaption", builderClassName = "$WorkflowCaptionBuilder")
	private static ITranslatableString buildWorkflowCaption(
			@NonNull final String salesOrderDocumentNo,
			@NonNull final String customerName)
	{
		return TranslatableStrings.join(" | ", salesOrderDocumentNo, customerName);
	}
}
