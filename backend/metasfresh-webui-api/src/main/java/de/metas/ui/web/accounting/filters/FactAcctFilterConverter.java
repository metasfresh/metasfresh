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

package de.metas.ui.web.accounting.filters;

import com.google.common.collect.ImmutableSet;
import com.jgoodies.common.base.Objects;
import de.metas.acct.api.IElementValueDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_Fact_Acct;
import org.compiere.util.DB;

public class FactAcctFilterConverter implements SqlDocumentFilterConverter
{
	public static final transient FactAcctFilterConverter instance = new FactAcctFilterConverter();

	static final String FILTER_ID = "accountId";

	public static final String PARAM_ACCOUNT_VALUE_FROM = "AccountValueFrom";
	public static final String PARAM_ACCOUNT_VALUE_TO = "AccountValueTo";

	private FactAcctFilterConverter()
	{
	}

	@Override
	public boolean canConvert(final String filterId)
	{
		return Objects.equals(filterId, FILTER_ID);
	}

	@Override
	public String getSql(
			@NonNull final SqlParamsCollector ignored_sqlParamsOut,
			@NonNull final DocumentFilter filter,
			@NonNull final SqlOptions sqlOpts,
			final SqlDocumentFilterConverterContext ignored)
	{
		final String accountValueFrom = filter.getParameterValueAsString(PARAM_ACCOUNT_VALUE_FROM, null);
		final String accountValueTo = filter.getParameterValueAsString(PARAM_ACCOUNT_VALUE_TO, null);

		Check.assumeNotEmpty(accountValueFrom, "AccountValueFrom not empty");
		Check.assumeNotEmpty(accountValueTo, "AccountValueTo not empty");

		final IElementValueDAO elementValueDAO = Services.get(IElementValueDAO.class);
		@SuppressWarnings("ConstantConditions") // needed because intellij believes we could be sending nulls to getElementValueIdsBetween
		final ImmutableSet<ElementValueId> ids = elementValueDAO.getElementValueIdsBetween(accountValueFrom, accountValueTo);

		if (ids.isEmpty())
		{
			return "(false)";
		}

		final String columnName = sqlOpts.getTableNameOrAlias() + "." + I_Fact_Acct.COLUMNNAME_Account_ID;
		return "(" + DB.buildSqlList(columnName, ids) + ")";
	}
}
