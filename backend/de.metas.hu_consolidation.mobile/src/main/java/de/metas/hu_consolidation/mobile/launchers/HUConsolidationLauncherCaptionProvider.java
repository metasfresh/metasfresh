package de.metas.hu_consolidation.mobile.launchers;

import de.metas.document.location.RenderedAddressProvider;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobReference;
import de.metas.i18n.TranslatableStrings;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class HUConsolidationLauncherCaptionProvider
{
	@NonNull private final RenderedAddressProvider renderedAddressProvider;

	@NonNull
	public WorkflowLauncherCaption computeCaption(final @NonNull HUConsolidationJobReference reference)
	{
		final String address = renderedAddressProvider.getAddress(reference.getBpartnerLocationId());
		return WorkflowLauncherCaption.of(TranslatableStrings.anyLanguage(address));
	}
}
