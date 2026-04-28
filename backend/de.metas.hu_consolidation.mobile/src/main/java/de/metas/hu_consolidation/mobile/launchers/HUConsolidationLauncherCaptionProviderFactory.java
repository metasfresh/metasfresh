package de.metas.hu_consolidation.mobile.launchers;

import de.metas.document.location.IDocumentLocationBL;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HUConsolidationLauncherCaptionProviderFactory
{
	@NonNull private final IDocumentLocationBL documentLocationBL;

	public HUConsolidationLauncherCaptionProvider newCaptionProvider()
	{
		return HUConsolidationLauncherCaptionProvider.builder()
				.renderedAddressProvider(documentLocationBL.newRenderedAddressProvider())
				.build();
	}
}
