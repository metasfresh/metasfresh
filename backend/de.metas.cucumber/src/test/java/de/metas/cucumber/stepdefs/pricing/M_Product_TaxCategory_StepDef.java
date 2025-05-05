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

package de.metas.cucumber.stepdefs.pricing;

import de.metas.cucumber.stepdefs.C_Country_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_TaxCategory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.I_C_Country.COLUMNNAME_C_Country_ID;
import static org.compiere.model.I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID;
import static org.compiere.model.I_M_Product_TaxCategory.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_M_Product_TaxCategory.COLUMNNAME_ValidFrom;

public class M_Product_TaxCategory_StepDef
{

	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final C_TaxCategory_StepDefData taxCategoryTable;
	private final M_Product_TaxCategory_StepDefData productTaxCategoryTable;
	private final C_Country_StepDefData countryTable;

	public M_Product_TaxCategory_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Country_StepDefData countryTable,
			@NonNull final C_TaxCategory_StepDefData taxCategoryTable,
			@NonNull final M_Product_TaxCategory_StepDefData productTaxCategoryTable)
	{
		this.productTable = productTable;
		this.countryTable = countryTable;
		this.taxCategoryTable = taxCategoryTable;
		this.productTaxCategoryTable = productTaxCategoryTable;
	}

	@Given("metasfresh contains M_Product_TaxCategory:")
	public void metasfresh_contains_m_product_taxCategory(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createM_Product_TaxCategory(tableRow);
		}
	}

	private void createM_Product_TaxCategory(@NonNull final Map<String, String> tableRow)
	{
		final String taxCategoryIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_TaxCategory_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, COLUMNNAME_ValidFrom);
		final String countryCode = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Country_ID + "." + I_C_Country.COLUMNNAME_CountryCode);
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Integer productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final CountryId countryId = countryDAO.getCountryIdByCountryCode(countryCode);

		final Integer taxCategoryId = taxCategoryTable.getOptional(taxCategoryIdentifier)
				.map(I_C_TaxCategory::getC_TaxCategory_ID)
				.orElseGet(() -> Integer.parseInt(taxCategoryIdentifier));

		final I_M_Product_TaxCategory productTaxCategoryRecord = queryBL
				.createQueryBuilder(I_M_Product_TaxCategory.class)
				.addEqualsFilter(COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(COLUMNNAME_C_Country_ID, countryId)
				.firstOptional()
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_M_Product_TaxCategory.class));

		productTaxCategoryRecord.setM_Product_ID(productId);
		productTaxCategoryRecord.setC_Country_ID(countryId.getRepoId());
		productTaxCategoryRecord.setC_TaxCategory_ID(taxCategoryId);
		productTaxCategoryRecord.setValidFrom(validFrom);
		productTaxCategoryRecord.setIsActive(true);

		saveRecord(productTaxCategoryRecord);

		productTaxCategoryTable.put(DataTableUtil.extractRecordIdentifier(tableRow, I_M_Product_TaxCategory.COLUMNNAME_M_Product_TaxCategory_ID), productTaxCategoryRecord);
	}
}

