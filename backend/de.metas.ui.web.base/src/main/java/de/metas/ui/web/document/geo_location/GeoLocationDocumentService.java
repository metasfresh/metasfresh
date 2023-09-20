package de.metas.ui.web.document.geo_location;

import com.google.common.collect.ImmutableSet;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsConstants;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.geo_location.GeoLocationDocumentDescriptor.LocationColumnNameType;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

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
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final AdMessageKey MSG_FILTER_CAPTION = AdMessageKey.of("LocationAreaSearch");
	private static final String SYS_CONFIG_ENABLE_GEO_LOCATION_SEARCH = "de.metas.ui.web.document.geo_location.filter_enabled";

	private static final ImmutableSet<GeoLocationDocumentDescriptor> DESCRIPTORS_TO_CHECK = ImmutableSet.<GeoLocationDocumentDescriptor>builder()
			.add(GeoLocationDocumentDescriptor.builder()
						 .type(LocationColumnNameType.LocationId)
						 .locationColumnName(I_C_Order.COLUMNNAME_C_BPartner_Location_Value_ID)
						 .build())
			.add(GeoLocationDocumentDescriptor.builder()
						 .type(LocationColumnNameType.LocationId)
						 .locationColumnName(I_C_Location.COLUMNNAME_C_Location_ID)
						 .build())
			.add(GeoLocationDocumentDescriptor.builder()
						 .type(LocationColumnNameType.BPartnerLocationId)
						 .locationColumnName(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID)
						 .build())
			.add(GeoLocationDocumentDescriptor.builder()
						 .type(LocationColumnNameType.BPartnerId)
						 .locationColumnName(I_C_BPartner.COLUMNNAME_C_BPartner_ID)
						 .build())
			.build();

	public GeoLocationDocumentService()
	{
	}

	@Override
	@Nullable
	public DocumentFilterDescriptorsProvider createFiltersProvider(
			@NonNull final CreateFiltersProviderContext context,
			final @NonNull Collection<DocumentFieldDescriptor> fields)
	{
		if (context.getTableName() == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}
		if (fields.isEmpty())
		{
			return null;
		}

		final ImmutableSet<String> fieldNames = extractFieldNames(fields);
		final GeoLocationDocumentDescriptor descriptor = getGeoLocationDocumentDescriptorIfExists(fieldNames).orElse(null);
		if (descriptor == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		return ImmutableDocumentFilterDescriptorsProvider.of(createDocumentFilterDescriptor(descriptor));
	}

	public GeoLocationDocumentDescriptor getGeoLocationDocumentDescriptor(@NonNull final Set<String> fieldNames)
	{
		return getGeoLocationDocumentDescriptorIfExists(fieldNames)
				.orElseThrow(() -> new AdempiereException("No geo-location support for " + fieldNames));
	}

	public boolean hasGeoLocationSupport(@NonNull final Set<String> fieldNames)
	{
		return getGeoLocationDocumentDescriptorIfExists(fieldNames).isPresent();
	}

	@Override
	public boolean isActive()
	{
		return sysConfigBL.getBooleanValue(SYS_CONFIG_ENABLE_GEO_LOCATION_SEARCH, Boolean.TRUE);
	}

	private static Optional<GeoLocationDocumentDescriptor> getGeoLocationDocumentDescriptorIfExists(@NonNull final Set<String> fieldNames)
	{
		return DESCRIPTORS_TO_CHECK
				.stream()
				.filter(descriptor -> fieldNames.contains(descriptor.getLocationColumnName()))
				.findFirst();
	}

	private static ImmutableSet<String> extractFieldNames(final Collection<DocumentFieldDescriptor> fields)
	{
		if (fields.isEmpty())
		{
			return ImmutableSet.of();
		}

		return fields.stream().map(DocumentFieldDescriptor::getFieldName).collect(ImmutableSet.toImmutableSet());
	}

	private DocumentFilterDescriptor createDocumentFilterDescriptor(final GeoLocationDocumentDescriptor descriptor)
	{
		final ITranslatableString caption = msgBL.getTranslatableMsgText(MSG_FILTER_CAPTION);

		return DocumentFilterDescriptor.builder()
				.setFilterId(GeoLocationFilterConverter.FILTER_ID)
				.setSortNo(DocumentFilterDescriptorsConstants.SORT_NO_GEO_LOCATION)
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
									  .setMandatory(true)
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
	public DocumentFilter createDocumentFilter(@NonNull final DocumentEntityDescriptor entityDescriptor, @NonNull final GeoLocationDocumentQuery query)
	{
		final ImmutableSet<String> fieldNames = extractFieldNames(entityDescriptor.getFields());
		final GeoLocationDocumentDescriptor descriptor = getGeoLocationDocumentDescriptor(fieldNames);

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
