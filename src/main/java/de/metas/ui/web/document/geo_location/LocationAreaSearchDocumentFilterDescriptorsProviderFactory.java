package de.metas.ui.web.document.geo_location;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdTabId;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import com.google.common.collect.Maps;

import de.metas.document.archive.model.I_C_BPartner;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.geo_location.GeoLocationAwareDescriptor.LocationColumnNameType;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import lombok.NonNull;

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

public class LocationAreaSearchDocumentFilterDescriptorsProviderFactory
{
	public static final transient LocationAreaSearchDocumentFilterDescriptorsProviderFactory instance = new LocationAreaSearchDocumentFilterDescriptorsProviderFactory();

	private static final String FIELDNAME_C_Location_ID = I_C_Location.COLUMNNAME_C_Location_ID;
	private static final String FIELDNAME_C_BPartner_ID = I_C_BPartner.COLUMNNAME_C_BPartner_ID;
	private static final String FIELDNAME_C_BPartner_Location_ID = I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;

	private LocationAreaSearchDocumentFilterDescriptorsProviderFactory()
	{
	}

	@Nullable public DocumentFilterDescriptorsProvider createFiltersProvider(
			@Nullable final AdTabId adTabId,
			@Nullable final String tableName,
			final Collection<DocumentFieldDescriptor> fields)
	{
		if (tableName == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}
		if (fields == null || fields.isEmpty())
		{
			return null;
		}

		final GeoLocationAwareDescriptor descriptor = getLocationAreaSearchDescriptor(tableName, fields);
		if (descriptor == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		return new LocationAreaSearchDocumentFilterDescriptorsProvider(descriptor);
	}

	@Nullable public static GeoLocationAwareDescriptor getLocationAreaSearchDescriptor(
			@NonNull final String tableName,
			@NonNull final Collection<DocumentFieldDescriptor> fields)
	{
		//noinspection ConstantConditions
		final Map<String, DocumentFieldDescriptor> fieldsByName = Maps.uniqueIndex(fields, DocumentFieldDescriptor::getFieldName);

		if (fieldsByName.containsKey(FIELDNAME_C_Location_ID))
		{
			return GeoLocationAwareDescriptor.builder()
					.type(LocationColumnNameType.LocationId)
					.locationColumnName(FIELDNAME_C_Location_ID)
					.build();
		}
		else if (fieldsByName.containsKey(FIELDNAME_C_BPartner_Location_ID))
		{
			return GeoLocationAwareDescriptor.builder()
					.type(LocationColumnNameType.BPartnerLocationId)
					.locationColumnName(FIELDNAME_C_BPartner_Location_ID)
					.build();
		}
		else if (fieldsByName.containsKey(FIELDNAME_C_BPartner_ID))
		{
			return GeoLocationAwareDescriptor.builder()
					.type(LocationColumnNameType.BPartnerId)
					.locationColumnName(FIELDNAME_C_BPartner_ID)
					.build();
		}
		else
		{
			return null;
		}
	}
}
