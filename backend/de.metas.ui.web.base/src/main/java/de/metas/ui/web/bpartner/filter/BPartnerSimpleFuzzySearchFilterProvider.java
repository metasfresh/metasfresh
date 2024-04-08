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
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterInlineRenderMode;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsConstants;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class BPartnerSimpleFuzzySearchFilterProvider implements DocumentFilterDescriptorsProviderFactory, SqlDocumentFilterConverter
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String FILTER_ID = "BPartnerSimpleFuzzySearchFilterProvider";
	private static final String PARAMETERNAME_SearchText = "searchText";
	private static final String BPARTNER_SEARCH_SQL_TEMPLATE = ".C_Bpartner_ID IN (SELECT p.C_Bpartner_ID "
			+ "FROM C_Bpartner p "
			+ "LEFT OUTER JOIN C_Bpartner_Location pl ON p.C_Bpartner_ID = pl.C_Bpartner_ID AND pl.IsActive='Y' AND pl.IsShipToDefault = 'Y' "
			+ "LEFT OUTER JOIN C_Location l ON pl.C_Location_ID = l.C_Location_ID AND pl.IsActive='Y' "
			+ "WHERE p.Name ILIKE ? "
			+ "OR p.Value ILIKE ? "
			+ "OR l.City ILIKE ? )";
	private static final AdMessageKey MSG_Caption = AdMessageKey.of("Member Search");
	public static final DocumentFilterDescriptor FILTER_DESCRIPTOR = DocumentFilterDescriptor.builder()
			.setFilterId(FILTER_ID)
			.setFrequentUsed(true)
			.setDisplayName(MSG_Caption)
			.setInlineRenderMode(DocumentFilterInlineRenderMode.INLINE_PARAMETERS)
			.setSortNo(DocumentFilterDescriptorsConstants.SORT_NO_INLINE_FILTERS)
			.addParameter(DocumentFilterParamDescriptor.builder()
					.fieldName(PARAMETERNAME_SearchText)
					.displayName(TranslatableStrings.adMessage(MSG_Caption))
					.widgetType(DocumentFieldWidgetType.Text)
			)
			.build();

	private static final String SYSCONFIG_Enabled = "webui.BPartnerSimpleFuzzySearchFilterProvider.enabled";

	@Nullable
	@Override
	public DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull final CreateFiltersProviderContext context)
	{
		if (I_C_BPartner.Table_Name.equals(context.getTableName())
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
	public FilterSql getSql(
			final DocumentFilter filter,
			final SqlOptions sqlOpts,
			final SqlDocumentFilterConverterContext context)
	{
		final DocumentFilterParam filterParameter = filter.getParameterOrNull(PARAMETERNAME_SearchText);
		if (filterParameter == null)
		{
			return null;
		}
		final String searchText = filterParameter.getValueAsString();
		if(Check.isBlank(searchText))
		{
			return null;
		}
		return FilterSql.ofWhereClause(SqlAndParams.builder()
				.append(sqlOpts.getTableNameOrAlias())
				.append(getSqlCriteria(searchText))
				.build());
	}

	private SqlAndParams getSqlCriteria(@NonNull final String searchText)
	{
		final String searchLikeValue = convertSearchTextToSqlLikeString(searchText);
		return SqlAndParams.of(BPARTNER_SEARCH_SQL_TEMPLATE, searchLikeValue, searchLikeValue, searchLikeValue);
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
