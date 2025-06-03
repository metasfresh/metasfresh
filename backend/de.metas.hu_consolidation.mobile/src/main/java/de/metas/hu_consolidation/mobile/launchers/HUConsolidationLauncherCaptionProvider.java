package de.metas.hu_consolidation.mobile.launchers;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.handlingunits.picking.job.model.RenderedAddressProvider;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobReference;
import de.metas.i18n.TranslatableStrings;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import lombok.Builder;
import lombok.NonNull;

class HUConsolidationLauncherCaptionProvider
{
	@NonNull private final RenderedAddressProvider renderedAddressProvider;

	@Builder
	private HUConsolidationLauncherCaptionProvider(@NonNull final IDocumentLocationBL documentLocationBL)
	{
		this.renderedAddressProvider = RenderedAddressProvider.newInstance(documentLocationBL);
	}

	public @NonNull WorkflowLauncherCaption computeCaption(final @NonNull HUConsolidationJobReference reference)
	{
		final String address = renderedAddressProvider.getAddress(reference.getBpartnerLocationId());
		return WorkflowLauncherCaption.of(TranslatableStrings.anyLanguage(address));
	}
}
