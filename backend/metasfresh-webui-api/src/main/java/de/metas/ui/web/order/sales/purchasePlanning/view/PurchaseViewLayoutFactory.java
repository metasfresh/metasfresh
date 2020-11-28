package de.metas.ui.web.order.sales.purchasePlanning.view;

import de.metas.cache.CCache;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;

import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PurchaseViewLayoutFactory
{
	private final CCache<LayoutKey, ViewLayout> viewLayoutCache = CCache.newCache(PurchaseViewLayoutFactory.class + "#ViewLayout", 1, 0);

	private final ITranslatableString caption;

	@Builder
	private PurchaseViewLayoutFactory(final ITranslatableString caption)
	{
		this.caption = caption;
	}

	public ViewLayout getViewLayout(@NonNull final WindowId windowId, @NonNull final JSONViewDataType viewDataType)
	{
		final LayoutKey key = LayoutKey.builder()
				.windowId(windowId)
				.viewDataType(viewDataType)
				.build();
		return viewLayoutCache.getOrLoad(key, this::createViewLayout);
	}

	private ViewLayout createViewLayout(final LayoutKey key)
	{
		return ViewLayout.builder()
				.setWindowId(key.getWindowId())
				.setCaption(caption)
				//
				.setHasAttributesSupport(false)
				.setHasTreeSupport(true)
				.setTreeCollapsible(true)
				.setTreeExpandedDepth(ViewLayout.TreeExpandedDepth_AllCollapsed)
				//
				.addElementsFromViewRowClass(PurchaseRow.class, key.getViewDataType())
				//
				.build();
	}

	@lombok.Value
	@lombok.Builder
	private static class LayoutKey
	{
		WindowId windowId;
		JSONViewDataType viewDataType;
	}
}
