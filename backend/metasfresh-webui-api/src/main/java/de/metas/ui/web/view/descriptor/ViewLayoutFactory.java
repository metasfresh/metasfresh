/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.view.descriptor;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import de.metas.cache.CCache;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.geo_location.GeoLocationDocumentService;
import de.metas.ui.web.view.SqlViewCustomizer;
import de.metas.ui.web.view.ViewProfile;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

public class ViewLayoutFactory
{
	private final DocumentDescriptorFactory documentDescriptorFactory;
	private final SqlViewBindingFactory viewBindingsFactory;
	private final SqlViewCustomizerMap viewCustomizers;
	private final GeoLocationDocumentService geoLocationDocumentService;

	private final transient CCache<ViewLayoutKey, ViewLayout> cache = CCache.newCache("SqlViewLayouts", 20, 0);

	@Builder
	private ViewLayoutFactory(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final SqlViewBindingFactory viewBindingsFactory,
			@NonNull final SqlViewCustomizerMap viewCustomizers,
			@NonNull final GeoLocationDocumentService geoLocationDocumentService)
	{
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.viewBindingsFactory = viewBindingsFactory;
		this.viewCustomizers = viewCustomizers;
		this.geoLocationDocumentService = geoLocationDocumentService;
	}

	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
	{
		final ViewLayoutKey viewLayoutKey = new ViewLayoutKey(windowId, viewDataType, profileId);
		return cache.getOrLoad(viewLayoutKey, this::createViewLayout);
	}

	private ViewLayout createViewLayout(final ViewLayoutKey viewLayoutKey)
	{
		final ViewLayout viewLayoutOrig = documentDescriptorFactory.getDocumentDescriptor(viewLayoutKey.getWindowId())
				.getViewLayout(viewLayoutKey.getViewDataType());

		final SqlViewBinding sqlViewBinding = getViewBinding(
				viewLayoutKey.getWindowId(),
				viewLayoutKey.getViewDataType().getRequiredFieldCharacteristic(),
				viewLayoutKey.getProfileId());
		final Collection<DocumentFilterDescriptor> filters = sqlViewBinding.getViewFilterDescriptors().getAll();
		final boolean hasTreeSupport = sqlViewBinding.hasGroupingFields();

		final ViewLayout.ChangeBuilder viewLayoutBuilder = viewLayoutOrig.toBuilder()
				.profileId(viewLayoutKey.getProfileId())
				.filters(filters)
				.treeSupport(hasTreeSupport, true/* treeCollapsible */, ViewLayout.TreeExpandedDepth_AllCollapsed)
				.geoLocationSupport(geoLocationDocumentService.hasGeoLocationSupport(viewLayoutOrig.getFieldNames()));

		//
		// Customize the view layout
		// NOTE to developer: keep it last, right before build().
		final SqlViewCustomizer sqlViewCustomizer = viewCustomizers.getOrNull(viewLayoutKey.getWindowId(), viewLayoutKey.getProfileId());
		if (sqlViewCustomizer != null)
		{
			sqlViewCustomizer.customizeViewLayout(viewLayoutBuilder);
		}

		return viewLayoutBuilder.build();
	}

	public SqlViewBinding getViewBinding(
			@NonNull final WindowId windowId,
			@Nullable final Characteristic requiredFieldCharacteristic,
			@Nullable final ViewProfileId profileId)
	{
		return viewBindingsFactory.getViewBinding(windowId, requiredFieldCharacteristic, profileId);
	}

	public List<ViewProfile> getAvailableProfiles(final WindowId windowId)
	{
		return viewCustomizers.getViewProfilesByWindowId(windowId);
	}

	@Value
	private static final class ViewLayoutKey
	{
		@NonNull
		final WindowId windowId;

		@NonNull
		final JSONViewDataType viewDataType;

		@Nullable
		final ViewProfileId profileId;
	}

}
