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

import com.jgoodies.common.base.Objects;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner_Export;

public class BPartnerExportFilterConverter implements SqlDocumentFilterConverter
{
	public static final transient BPartnerExportFilterConverter instance = new BPartnerExportFilterConverter();

	static final String FILTER_ID = "postal";

	public static final String PARAM_POSTAL_FROM = "PostalFrom";
	public static final String PARAM_POSTAL_TO = "PostalTo";

	private BPartnerExportFilterConverter()
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
		final String postalFrom = filter.getParameterValueAsString(PARAM_POSTAL_FROM, null);
		final String postalTo = filter.getParameterValueAsString(PARAM_POSTAL_TO, null);

		Check.assumeNotEmpty(postalFrom, "PostalFrom not empty");
		Check.assumeNotEmpty(postalTo, "PostalTo not empty");

		return sqlOpts.getTableNameOrAlias() + "." + I_C_BPartner_Export.COLUMNNAME_Postal + " >= '" + postalFrom
				+ "' AND " + sqlOpts.getTableNameOrAlias() + "." + I_C_BPartner_Export.COLUMNNAME_Postal
				+ " <= '" + postalTo + "'";
	}
}
