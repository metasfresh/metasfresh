package de.metas.ui.web.document.geo_location;

import java.util.Collection;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdTabId;
import org.compiere.model.I_C_Country;
import org.springframework.stereotype.Component;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.util.Services;
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

@Component
public class GeoLocationDocumentService implements DocumentFilterDescriptorsProviderFactory
{
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final String MSG_FILTER_CAPTION = "LocationAreaSearch";

	public GeoLocationDocumentService()
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

		final GeoLocationDocumentDescriptor descriptor = GeoLocationDocumentDescriptors.getGeoLocationDocumentDescriptorOrNull(tableName, fields);
		if (descriptor == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		return ImmutableDocumentFilterDescriptorsProvider.of(createDocumentFilterDescriptor(descriptor));
	}

	private DocumentFilterDescriptor createDocumentFilterDescriptor(final GeoLocationDocumentDescriptor descriptor)
	{
		final ITranslatableString caption = msgBL.getTranslatableMsgText(MSG_FILTER_CAPTION);

		return DocumentFilterDescriptor.builder()
				.setFilterId(GeoLocationFilterConverter.FILTER_ID)
				.setDisplayName(caption)
				//
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(GeoLocationFilterConverter.PARAM_Address1)
						.setDisplayName(msgBL.translatable(GeoLocationFilterConverter.PARAM_Address1))
						.setWidgetType(DocumentFieldWidgetType.Text))
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(GeoLocationFilterConverter.PARAM_Postal)
						.setDisplayName(msgBL.translatable(GeoLocationFilterConverter.PARAM_Postal))
						.setWidgetType(DocumentFieldWidgetType.Text))
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(GeoLocationFilterConverter.PARAM_City)
						.setDisplayName(msgBL.translatable(GeoLocationFilterConverter.PARAM_City))
						.setWidgetType(DocumentFieldWidgetType.Text))
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(GeoLocationFilterConverter.PARAM_CountryId)
						.setDisplayName(msgBL.translatable(GeoLocationFilterConverter.PARAM_CountryId))
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptor(SqlLookupDescriptor.searchInTable(I_C_Country.Table_Name).provideForFilter()))
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(GeoLocationFilterConverter.PARAM_Distance)
						.setDisplayName(msgBL.translatable(GeoLocationFilterConverter.PARAM_Distance))
						.setWidgetType(DocumentFieldWidgetType.Integer))
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(GeoLocationFilterConverter.PARAM_VisitorsAddress)
						.setDisplayName(msgBL.translatable(GeoLocationFilterConverter.PARAM_VisitorsAddress))
						.setWidgetType(DocumentFieldWidgetType.YesNo))
				//
				.addInternalParameter(GeoLocationFilterConverter.PARAM_LocationAreaSearchDescriptor, descriptor)
				//
				.build();
	}

	@NonNull
	public DocumentFilter createDocumentFilter(final DocumentEntityDescriptor entityDescriptor, final GeoLocationQuery query)
	{
		final GeoLocationDocumentDescriptor descriptor = GeoLocationDocumentDescriptors.getGeoLocationDocumentDescriptor(
				entityDescriptor.getTableName(),
				entityDescriptor.getFields());

		return DocumentFilter.builder()
				.setFilterId(GeoLocationFilterConverter.FILTER_ID)
				.addInternalParameter(DocumentFilterParam.ofNameEqualsValue(GeoLocationFilterConverter.PARAM_LocationAreaSearchDescriptor, descriptor))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(GeoLocationFilterConverter.PARAM_Address1, query.getAddress1()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(GeoLocationFilterConverter.PARAM_City, query.getCity()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(GeoLocationFilterConverter.PARAM_Postal, query.getPostal()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(GeoLocationFilterConverter.PARAM_CountryId, query.getCountry()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(GeoLocationFilterConverter.PARAM_Distance, query.getDistanceInKm()))
				.addParameter(DocumentFilterParam.ofNameEqualsValue(GeoLocationFilterConverter.PARAM_VisitorsAddress, query.isVisitorsAddress()))
				.build();
	}

}
