package de.metas.ui.web.window.descriptor.factory.standard;

import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
public class ADTabLoader
{
	AdWindowId adWindowId;

	@NonNull
	LayoutFactory rootLayoutFactory;

	@NonNull
	DocumentLayoutDescriptor.Builder layoutBuilder;

	public void load()
	{
		final GridWindowVO gridWindowVO = DocumentLoaderUtil.createGridWindoVO(adWindowId);
		//
		// Layout: Create UI details from child tabs
		for (final GridTabVO detailTabVO : gridWindowVO.getChildTabs(GridTabVO.MAIN_TabNo))
		{
			// Skip sort tabs because they are not supported
			if (detailTabVO.IsSortTab)
			{
				continue;
			}

			// Skip tabs which were already used/embedded in root layout
			if (rootLayoutFactory.isSkipAD_Tab_ID(detailTabVO.getAdTabId()))
			{
				continue;
			}

			final GridTabVO mainTabVO = gridWindowVO.getTab(GridTabVO.MAIN_TabNo);

			final LayoutFactory detailLayoutFactory = LayoutFactory.ofIncludedTab(gridWindowVO, mainTabVO, detailTabVO);

			detailLayoutFactory
					.layoutDetail() // might be empty
					.map(DocumentLayoutDetailDescriptor.Builder::build)
					.ifPresent(layoutBuilder::addDetail);

			final DocumentEntityDescriptor.Builder detailEntityBuilder = detailLayoutFactory.documentEntity();
			rootLayoutFactory.documentEntity().addIncludedEntity(detailEntityBuilder.build());
		}
	}
}
