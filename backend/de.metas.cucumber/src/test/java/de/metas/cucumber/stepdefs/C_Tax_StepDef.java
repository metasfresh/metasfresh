/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.location.ICountryDAO;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_ProductPrice;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class C_Tax_StepDef
{
	private final C_Tax_StepDefData taxTable;

	public C_Tax_StepDef(@NonNull final C_Tax_StepDefData taxTable)
	{
		this.taxTable = taxTable;
	}

	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("metasfresh contains C_Tax")
	public void add_C_Tax(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createC_Tax(tableRow);
		}
	}

	private void createC_Tax(@NonNull final Map<String, String> tableRow)
	{
		final String taxCategoryInternalName = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "." + I_C_TaxCategory.COLUMNNAME_InternalName);
		final Optional<TaxCategoryId> taxCategoryId = taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName);
		assertThat(taxCategoryId).as("Missing taxCategory for internalName=%s", taxCategoryInternalName).isPresent();

		final String taxName = DataTableUtil.extractStringForColumnName(tableRow, I_C_Tax.COLUMNNAME_Name);

		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_C_Tax.COLUMNNAME_ValidFrom);
		final BigDecimal rate = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_Tax.COLUMNNAME_Rate);
		final String countryCode = DataTableUtil.extractStringForColumnName(tableRow, I_C_Tax.COLUMNNAME_C_Country_ID + ".CountryCode");
		final String toCountryCode = DataTableUtil.extractStringForColumnName(tableRow, I_C_Tax.COLUMNNAME_To_Country_ID + ".CountryCode");

		final I_C_Country countryRecord = countryDAO.retrieveCountryByCountryCode(countryCode);
		final I_C_Country toCountryRecord = countryDAO.retrieveCountryByCountryCode(toCountryCode);

		final int seqNo = queryBL.createQueryBuilder(I_C_Tax.class)
				.addEqualsFilter(I_C_Tax.COLUMNNAME_C_TaxCategory_ID, taxCategoryId.get().getRepoId())
				.orderBy(I_C_Tax.COLUMNNAME_SeqNo)
				.create()
				.firstOptional(I_C_Tax.class)
				.map(I_C_Tax::getSeqNo)
				.map(currentMinSeqNo -> currentMinSeqNo - 1)
				.orElse(0);

		final I_C_Tax taxRecord = InterfaceWrapperHelper.newInstance(I_C_Tax.class);
		taxRecord.setName(taxName);
		taxRecord.setC_TaxCategory_ID(taxCategoryId.get().getRepoId());
		taxRecord.setValidFrom(validFrom);
		taxRecord.setRate(rate);
		taxRecord.setC_Country_ID(countryRecord.getC_Country_ID());
		taxRecord.setTo_Country_ID(toCountryRecord.getC_Country_ID());
		taxRecord.setSeqNo(seqNo);

		InterfaceWrapperHelper.saveRecord(taxRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_C_Tax.Table_Name);
		taxTable.putOrReplace(recordIdentifier, taxRecord);
	}
}