package de.metas.ui.web.document.geo_location;

import java.util.Collection;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdTabId;
import org.compiere.model.I_C_Country;
import org.springframework.stereotype.Component;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.util.Services;

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

@Component
public class LocationAreaSearchDocumentFilterDescriptorsProviderFactory implements DocumentFilterDescriptorsProviderFactory
{
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final String MSG_FILTER_CAPTION = "LocationAreaSearch";

	public LocationAreaSearchDocumentFilterDescriptorsProviderFactory()
	{
	}

	@Override
	@Nullable
	public DocumentFilterDescriptorsProvider createFiltersProvider(
			@Nullable final AdTabId adTabId_NOTUSED,
			@Nullable final String tableName,
			@Nullable final Collection<DocumentFieldDescriptor> fields)
	{
		if (tableName == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}
		if (fields == null || fields.isEmpty())
		{
			return null;
		}

		final GeoLocationAwareDescriptor descriptor = GeoLocationAwareDescriptors.getGeoLocationAwareDescriptorOrNull(tableName, fields);
		if (descriptor == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		return ImmutableDocumentFilterDescriptorsProvider.of(createDocumentFilterDescriptor(descriptor));
	}

	private DocumentFilterDescriptor createDocumentFilterDescriptor(final GeoLocationAwareDescriptor descriptor)
	{
		final ITranslatableString caption = msgBL.getTranslatableMsgText(MSG_FILTER_CAPTION);

		return DocumentFilterDescriptor.builder()
				.setFilterId(LocationAreaSearchDocumentFilterConverter.FILTER_ID)
				.setDisplayName(caption)
				//
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(LocationAreaSearchDocumentFilterConverter.PARAM_Address1)
						.setDisplayName(msgBL.translatable(LocationAreaSearchDocumentFilterConverter.PARAM_Address1))
						.setWidgetType(DocumentFieldWidgetType.Text))
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(LocationAreaSearchDocumentFilterConverter.PARAM_Postal)
						.setDisplayName(msgBL.translatable(LocationAreaSearchDocumentFilterConverter.PARAM_Postal))
						.setWidgetType(DocumentFieldWidgetType.Text))
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(LocationAreaSearchDocumentFilterConverter.PARAM_City)
						.setDisplayName(msgBL.translatable(LocationAreaSearchDocumentFilterConverter.PARAM_City))
						.setWidgetType(DocumentFieldWidgetType.Text))
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(LocationAreaSearchDocumentFilterConverter.PARAM_CountryId)
						.setDisplayName(msgBL.translatable(LocationAreaSearchDocumentFilterConverter.PARAM_CountryId))
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptor(SqlLookupDescriptor.searchInTable(I_C_Country.Table_Name).provideForFilter()))
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(LocationAreaSearchDocumentFilterConverter.PARAM_Distance)
						.setDisplayName(msgBL.translatable(LocationAreaSearchDocumentFilterConverter.PARAM_Distance))
						.setWidgetType(DocumentFieldWidgetType.Integer))
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(LocationAreaSearchDocumentFilterConverter.PARAM_VisitorsAddress)
						.setDisplayName(msgBL.translatable(LocationAreaSearchDocumentFilterConverter.PARAM_VisitorsAddress))
						.setWidgetType(DocumentFieldWidgetType.YesNo))
				//
				.addInternalParameter(LocationAreaSearchDocumentFilterConverter.PARAM_LocationAreaSearchDescriptor, descriptor)
				//
				.build();
	}

}
