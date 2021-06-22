/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.bpartner.filter;

import de.metas.i18n.AdMessageKey;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;

@Component
public class BPartnerSimpleFuzzySearchFilterProvider implements DocumentFilterDescriptorsProviderFactory, SqlDocumentFilterConverter
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String FILTER_ID = "BPartnerSimpleFuzzySearchFilterProvider";
	private static final String PARAMETERNAME_SearchText = "searchText";
	private static final AdMessageKey MSG_Caption = AdMessageKey.of("Search");
	public static final DocumentFilterDescriptor FILTER_DESCRIPTOR = DocumentFilterDescriptor.builder()
			.setFilterId(FILTER_ID)
			.setFrequentUsed(true)
			.setDisplayName(MSG_Caption)
			.addParameter(DocumentFilterParamDescriptor.builder()
					.setMandatory(true)
					.setFieldName(PARAMETERNAME_SearchText)
					.setDisplayName(MSG_Caption)
					.setWidgetType(DocumentFieldWidgetType.Text)
			)
			.build();

	private static final String SYSCONFIG_Enabled = "webui.BPartnerSimpleFuzzySearchFilterProvider.enabled";

	@Nullable
	@Override
	public DocumentFilterDescriptorsProvider createFiltersProvider(
			@Nullable final AdTabId adTabId,
			@Nullable final String tableName,
			@NonNull final Collection<DocumentFieldDescriptor> fields)
	{
		if (I_C_BPartner.Table_Name.equals(tableName)
				&& isEnabled())
		{
			return ImmutableDocumentFilterDescriptorsProvider.of(FILTER_DESCRIPTOR);
		}
		else
		{
			return ImmutableDocumentFilterDescriptorsProvider.of();
		}
	}

	private boolean isEnabled()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_Enabled, false);
	}

	@Override
	public boolean canConvert(final String filterId)
	{
		return FILTER_ID.equals(filterId);
	}

	@Nullable
	@Override
	public String getSql(
			final SqlParamsCollector sqlParamsOut,
			final DocumentFilter filter,
			final SqlOptions sqlOpts,
			final SqlDocumentFilterConverterContext context)
	{
		final String searchText = filter.getParameterValueAsString(PARAMETERNAME_SearchText);
		if (Check.isBlank(searchText))
		{
			return null;
		}

		// TODO: implement
		// filter by: C_BPartner.Value, C_BPartner.Name, C_BPartner->C_BPartner_Location->C_Location.City etc (check the task description)
		// use org.adempiere.db.DBConstants.FUNC_unaccent_string

		throw new UnsupportedOperationException();
	}

	// converts user entered strings like ' john    doe  ' to '%john%doe%'
	private static String convertSearchTextToSqlLikeString(@NonNull final String searchText)
	{
		String sql = searchText.trim()
				.replaceAll("\\s+", "%")
				.replaceAll("%+", "%");

		if (!sql.startsWith("%"))
		{
			sql = "%" + sql;
		}
		if (!sql.endsWith("%"))
		{
			sql = sql + "%";
		}

		return sql;
	}
}
