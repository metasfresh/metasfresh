package de.metas.ui.web.document.geo_location;

import java.util.Collection;

import org.compiere.model.I_C_Country;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
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

class LocationAreaSearchDocumentFilterDescriptorsProvider implements DocumentFilterDescriptorsProvider
{
	private static final String MSG_FILTER_CAPTION = "LocationAreaSearch";

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private final GeoLocationAwareDescriptor descriptor;

	private DocumentFilterDescriptor filterDescriptor;

	LocationAreaSearchDocumentFilterDescriptorsProvider(@NonNull final GeoLocationAwareDescriptor descriptor)
	{
		this.descriptor = descriptor;
	}

	@Override
	public Collection<DocumentFilterDescriptor> getAll()
	{
		return ImmutableList.of(getDocumentFilterDescriptor());
	}

	private DocumentFilterDescriptor getDocumentFilterDescriptor()
	{
		DocumentFilterDescriptor filterDescriptor = this.filterDescriptor;
		if (filterDescriptor == null)
		{
			filterDescriptor = this.filterDescriptor = createDocumentFilterDescriptor();
		}
		return filterDescriptor;
	}

	private DocumentFilterDescriptor createDocumentFilterDescriptor()
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

	@SuppressWarnings("ConstantConditions")
	@Override
	public DocumentFilterDescriptor getByFilterIdOrNull(final String filterId)
	{
		final DocumentFilterDescriptor filterDescriptor = getDocumentFilterDescriptor();
		return filterDescriptor.getFilterId().equals(filterId) ? filterDescriptor : null;
	}

}
