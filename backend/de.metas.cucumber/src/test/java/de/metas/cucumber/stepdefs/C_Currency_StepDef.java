/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Currency;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_Currency_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Currency_StepDefData currencyTable;

	public C_Currency_StepDef(@NonNull final C_Currency_StepDefData currencyTable)
	{
		this.currencyTable = currencyTable;
	}

	@And("load C_Currency:")
	public void load_C_Currency(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			loadCurrency(dataTableRow);
		}
	}

	@And("load C_Currency by ISO Code:")
	public void metasfresh_contains_C_Currency(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String isoCode = DataTableUtil.extractStringForColumnName(row, I_C_Currency.COLUMNNAME_ISO_Code);

			final I_C_Currency currency = queryBL.createQueryBuilder(I_C_Currency.class)
					.addEqualsFilter(I_C_Currency.COLUMNNAME_ISO_Code, isoCode)
					.create()
					.firstOnlyNotNull(I_C_Currency.class);

			final String currencyIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Currency.COLUMNNAME_C_Currency_ID + "." + TABLECOLUMN_IDENTIFIER);
			currencyTable.putOrReplace(currencyIdentifier, currency);
		}
	}

	private void loadCurrency(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_Currency.COLUMNNAME_C_Currency_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Integer id = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_C_Currency.COLUMNNAME_C_Currency_ID);

		if (id != null)
		{
			final I_C_Currency currencyRecord = InterfaceWrapperHelper.load(id, I_C_Currency.class);

			currencyTable.putOrReplace(identifier, currencyRecord);
		}
	}
}
