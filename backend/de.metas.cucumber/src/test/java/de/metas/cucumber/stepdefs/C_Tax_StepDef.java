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

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.location.ICountryDAO;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_ProductPrice;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class C_Tax_StepDef
{
	private final C_Tax_StepDefData taxTable;
	private final AD_Org_StepDefData orgTable;

	public C_Tax_StepDef(
			@NonNull final C_Tax_StepDefData taxTable,
			@NonNull final AD_Org_StepDefData orgTable)
	{
		this.taxTable = taxTable;
		this.orgTable = orgTable;
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

	@And("load C_Tax:")
	public void load_C_Tax(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			loadTax(row);
		}
	}

	@And("update C_Tax:")
	public void update_C_Tax(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			updateTax(row);
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

		final I_C_Tax taxRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_C_Tax.class)
						.addEqualsFilter(I_C_Tax.COLUMNNAME_Name, taxName)
						.create()
						.firstOnly(I_C_Tax.class),
				() -> InterfaceWrapperHelper.newInstance(I_C_Tax.class));

		taxRecord.setName(taxName);
		taxRecord.setC_TaxCategory_ID(taxCategoryId.get().getRepoId());
		taxRecord.setValidFrom(validFrom);
		taxRecord.setRate(rate);
		taxRecord.setC_Country_ID(countryRecord.getC_Country_ID());
		taxRecord.setTo_Country_ID(toCountryRecord.getC_Country_ID());
		taxRecord.setSeqNo(seqNo);

		final String orgIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Tax.COLUMNNAME_AD_Org_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(orgIdentifier))
		{
			final I_AD_Org org = orgTable.get(orgIdentifier);
			taxRecord.setAD_Org_ID(org.getAD_Org_ID());
		}

		saveRecord(taxRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_C_Tax.Table_Name);
		taxTable.putOrReplace(recordIdentifier, taxRecord);
	}

	private void loadTax(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Tax.COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Integer id = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_C_Tax.COLUMNNAME_C_Tax_ID);

		if (id != null)
		{
			final I_C_Tax taxRecord = InterfaceWrapperHelper.load(id, I_C_Tax.class);

			taxTable.putOrReplace(identifier, taxRecord);
		}
	}

	private void updateTax(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Tax.COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Tax taxRecord = taxTable.get(identifier);
		assertThat(taxRecord).isNotNull();

		final String seqNo = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Tax.COLUMNNAME_SeqNo);

		if (Check.isNotBlank(seqNo))
		{
			taxRecord.setSeqNo(Integer.parseInt(seqNo));
		}

		saveRecord(taxRecord);

		taxTable.putOrReplace(identifier, taxRecord);
	}
}