package de.metas.ui.web.window.descriptor.factory.standard;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdTabId;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LayoutFactoryProvider
{
	@NonNull private final LayoutFactorySupportingServices supportingServices;

	public LayoutFactory ofMainTab(final GridWindowVO gridWindowVO)
	{
		final GridTabVO mainTabVO = gridWindowVO.getTab(GridTabVO.MAIN_TabNo);

		final DAOWindowUIElementsProvider windowUIElementsProvider = new DAOWindowUIElementsProvider();
		windowUIElementsProvider.warmUp(extractTemplateTabIds(gridWindowVO));

		return LayoutFactory.builder()
				.services(supportingServices)
				.windowUIElementsProvider(windowUIElementsProvider)
				//
				.gridWindowVO(gridWindowVO)
				.gridTabVO(mainTabVO)
				.parentTab(null)
				//
				.build();
	}

	private static ImmutableSet<AdTabId> extractTemplateTabIds(final GridWindowVO gridWindowVO)
	{
		return gridWindowVO.getTabs()
				.stream()
				.map(LayoutFactory::extractTemplateTabId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
