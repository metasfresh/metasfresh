package de.metas.ui.web.document.geo_location;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import com.google.common.collect.ImmutableSet;

import de.metas.document.archive.model.I_C_BPartner;
import de.metas.ui.web.document.geo_location.GeoLocationDocumentDescriptor.LocationColumnNameType;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
final class GeoLocationDocumentDescriptors
{
	private static final String FIELDNAME_C_Location_ID = I_C_Location.COLUMNNAME_C_Location_ID;
	private static final String FIELDNAME_C_BPartner_ID = I_C_BPartner.COLUMNNAME_C_BPartner_ID;
	private static final String FIELDNAME_C_BPartner_Location_ID = I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;

	private static final GeoLocationDocumentDescriptor DESCRIPTOR_FOR_LocationId = GeoLocationDocumentDescriptor.builder()
			.type(LocationColumnNameType.LocationId)
			.locationColumnName(FIELDNAME_C_Location_ID)
			.build();
	private static final GeoLocationDocumentDescriptor DESCRIPTOR_FOR_BPartnerLocationId = GeoLocationDocumentDescriptor.builder()
			.type(LocationColumnNameType.BPartnerLocationId)
			.locationColumnName(FIELDNAME_C_BPartner_Location_ID)
			.build();
	private static final GeoLocationDocumentDescriptor DESCRIPTOR_FOR_BPartnerId = GeoLocationDocumentDescriptor.builder()
			.type(LocationColumnNameType.BPartnerId)
			.locationColumnName(FIELDNAME_C_BPartner_ID)
			.build();

	public static GeoLocationDocumentDescriptor getGeoLocationDocumentDescriptor(
			@NonNull final String tableName,
			@NonNull final Collection<DocumentFieldDescriptor> fields)
	{
		final ImmutableSet<String> fieldNames = extractFieldNames(fields);
		final GeoLocationDocumentDescriptor descriptor = getGeoLocationDocumentDescriptorOrNull(tableName, fieldNames);
		if (descriptor == null)
		{
			throw new AdempiereException("Table " + tableName + " does not have geo-location support")
					.appendParametersToMessage()
					.setParameter("fields", fieldNames);
		}

		return descriptor;
	}

	@Nullable
	public static GeoLocationDocumentDescriptor getGeoLocationDocumentDescriptorOrNull(
			@NonNull final String tableName,
			@NonNull final Collection<DocumentFieldDescriptor> fields)
	{
		final ImmutableSet<String> fieldNames = extractFieldNames(fields);
		return getGeoLocationDocumentDescriptorOrNull(tableName, fieldNames);
	}

	@Nullable
	private static GeoLocationDocumentDescriptor getGeoLocationDocumentDescriptorOrNull(
			@NonNull final String tableName,
			@NonNull final Set<String> fieldNames)
	{
		if (fieldNames.contains(FIELDNAME_C_Location_ID))
		{
			return DESCRIPTOR_FOR_LocationId;
		}
		else if (fieldNames.contains(FIELDNAME_C_BPartner_Location_ID))
		{
			return DESCRIPTOR_FOR_BPartnerLocationId;
		}
		else if (fieldNames.contains(FIELDNAME_C_BPartner_ID))
		{
			return DESCRIPTOR_FOR_BPartnerId;
		}
		else
		{
			return null;
		}
	}

	private static ImmutableSet<String> extractFieldNames(final Collection<DocumentFieldDescriptor> fields)
	{
		if (fields.isEmpty())
		{
			return ImmutableSet.of();
		}

		return fields.stream().map(DocumentFieldDescriptor::getFieldName).collect(ImmutableSet.toImmutableSet());
	}

}
