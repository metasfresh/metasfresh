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

import de.metas.money.CurrencyId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Conversion_Rate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_Conversion_Rate_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Currency_StepDefData currencyTable;
	private final C_Conversion_Rate_StepDefData conversionRateTable;

	public C_Conversion_Rate_StepDef(
			@NonNull final C_Currency_StepDefData currencyTable,
			@NonNull final C_Conversion_Rate_StepDefData conversionRateTable)
	{
		this.currencyTable = currencyTable;
		this.conversionRateTable = conversionRateTable;
	}

	@And("load C_Conversion_Rate:")
	public void createC_Conversion_Rate(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadConversionRate(tableRow);
		}
	}

	@And("validate C_Conversion_Rate:")
	public void validateC_Conversion_Rate(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateConversionRate(tableRow);
		}
	}

	private void validateConversionRate(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Conversion_Rate.COLUMNNAME_C_Conversion_Rate_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Conversion_Rate conversionRateRecord = conversionRateTable.get(identifier);

		final SoftAssertions softly = new SoftAssertions();

		final BigDecimal multiplyRate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_C_Conversion_Rate.COLUMNNAME_MultiplyRate);
		if (multiplyRate != null)
		{
			softly.assertThat(conversionRateRecord.getMultiplyRate()).as(I_C_Conversion_Rate.COLUMNNAME_MultiplyRate).isEqualByComparingTo(multiplyRate);
		}

		final BigDecimal divideRate = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_C_Conversion_Rate.COLUMNNAME_DivideRate);
		if (divideRate != null)
		{
			softly.assertThat(conversionRateRecord.getDivideRate()).as(I_C_Conversion_Rate.COLUMNNAME_DivideRate).isEqualByComparingTo(divideRate);
		}

		softly.assertAll();
	}

	private void loadConversionRate(@NonNull final Map<String, String> tableRow)
	{
		final String currencyFromIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID + "." + TABLECOLUMN_IDENTIFIER);
		final CurrencyId currencyFromId = CurrencyId.ofRepoId(currencyTable.get(currencyFromIdentifier).getC_Currency_ID());

		final String currencyToIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To + "." + TABLECOLUMN_IDENTIFIER);
		final CurrencyId currencyToId = CurrencyId.ofRepoId(currencyTable.get(currencyToIdentifier).getC_Currency_ID());

		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_C_Conversion_Rate.COLUMNNAME_ValidFrom);
		final Timestamp validTo = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_C_Conversion_Rate.COLUMNNAME_ValidTo);

		final I_C_Conversion_Rate conversionRateRecord = queryBL.createQueryBuilder(I_C_Conversion_Rate.class)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID, currencyFromId)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To, currencyToId)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_ValidFrom, validFrom)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMNNAME_ValidTo, validTo)
				.create()
				.firstOnlyOrNull(I_C_Conversion_Rate.class);

		assertThat(conversionRateRecord).isNotNull();

		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Conversion_Rate.COLUMNNAME_C_Conversion_Rate_ID + "." + TABLECOLUMN_IDENTIFIER);
		conversionRateTable.putOrReplace(identifier, conversionRateRecord);
	}
}
