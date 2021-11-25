/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.pricing;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.location.ICountryDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_PriceList_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_PricingSystem_ID;

public class M_PriceList_StepDef
{
	private final OrgId defaultOrgId = StepDefConstants.ORG_ID;
	private final CurrencyRepository currencyRepository;
	private final StepDefData<I_M_Product> productTable;
	private final StepDefData<I_M_PricingSystem> pricingSystemTable;
	private final StepDefData<I_M_PriceList> priceListTable;
	private final StepDefData<I_M_PriceList_Version> priceListVersionTable;
	private final StepDefData<I_M_ProductPrice> productPriceTable;

	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	public M_PriceList_StepDef(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final StepDefData<I_M_Product> productTable,
			@NonNull final StepDefData<I_M_PricingSystem> pricingSystemTable,
			@NonNull final StepDefData<I_M_PriceList> priceListTable,
			@NonNull final StepDefData<I_M_PriceList_Version> priceListVersionTable,
			@NonNull final StepDefData<I_M_ProductPrice> productPriceTable)
	{
		this.currencyRepository = currencyRepository;
		this.productTable = productTable;
		this.pricingSystemTable = pricingSystemTable;
		this.priceListTable = priceListTable;
		this.priceListVersionTable = priceListVersionTable;
		this.productPriceTable = productPriceTable;
	}

	@And("metasfresh contains M_PricingSystems")
	public void add_M_PricingSystem(@NonNull final DataTable dataTable)
	{
		final Map<String, String> dataTableRow = dataTable.asMaps().get(0);
		createM_PricingSystem(dataTableRow);
	}

	@And("metasfresh contains M_PriceLists")
	public void add_M_PriceList(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			createM_PriceList(dataTableRow);
		}
	}

	private void createM_PricingSystem(@NonNull final Map<String, String> row)
	{
		final String name = DataTableUtil.extractStringForColumnName(row, "Name");
		final String value = DataTableUtil.extractStringForColumnName(row, "Value");
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.Description");
		final boolean isActive = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT.IsActive", true);

		final I_M_PricingSystem m_pricingSystem = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);

		m_pricingSystem.setAD_Org_ID(defaultOrgId.getRepoId());
		m_pricingSystem.setName(name);
		m_pricingSystem.setValue(value);
		m_pricingSystem.setIsActive(isActive);
		m_pricingSystem.setDescription(description);

		saveRecord(m_pricingSystem);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(row, I_M_PricingSystem.Table_Name);
		pricingSystemTable.putOrReplace(recordIdentifier, m_pricingSystem);
	}

	private void createM_PriceList(@NonNull final Map<String, String> row)
	{
		final String pricingSystemIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_PricingSystem_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final String countryCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.C_Country.CountryCode");
		final String isoCode = DataTableUtil.extractStringForColumnName(row, "C_Currency.ISO_Code");
		final String name = DataTableUtil.extractStringForColumnName(row, "Name");
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.Description");
		final boolean soTrx = DataTableUtil.extractBooleanForColumnName(row, "SOTrx");
		final boolean isTaxIncluded = DataTableUtil.extractBooleanForColumnName(row, "IsTaxIncluded");
		final String pricePrecision = DataTableUtil.extractStringForColumnName(row, "PricePrecision");
		final boolean isActive = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT.IsActive", true);

		final CurrencyId currencyId = getCurrencyIdByCurrencyISO(isoCode);

		final I_M_PriceList m_priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);

		m_priceList.setAD_Org_ID(defaultOrgId.getRepoId());
		m_priceList.setM_PricingSystem_ID(pricingSystemTable.get(pricingSystemIdentifier).getM_PricingSystem_ID());
		m_priceList.setC_Currency_ID(currencyId.getRepoId());
		m_priceList.setName(name);
		m_priceList.setIsTaxIncluded(isTaxIncluded);
		m_priceList.setPricePrecision(Integer.parseInt(pricePrecision));
		m_priceList.setIsActive(isActive);
		m_priceList.setIsSOPriceList(soTrx);

		if (countryCode != null)
		{
			final I_C_Country countryPO = Services.get(ICountryDAO.class).retrieveCountryByCountryCode(countryCode);
			m_priceList.setC_Country_ID(countryPO.getC_Country_ID());
		}

		if (description != null)
		{
			m_priceList.setDescription(description);
		}

		saveRecord(m_priceList);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(row, I_M_PriceList.Table_Name);
		priceListTable.putOrReplace(recordIdentifier, m_priceList);
	}

	@NonNull
	private CurrencyId getCurrencyIdByCurrencyISO(@NonNull final String currencyISO)
	{
		final CurrencyCode convertedToCurrencyCode = CurrencyCode.ofThreeLetterCode(currencyISO);
		return currencyRepository.getCurrencyIdByCurrencyCode(convertedToCurrencyCode);
	}

	@And("metasfresh contains M_PriceList_Versions")
	public void add_M_PriceListVersion(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			createM_PriceList_Version(dataTableRow);
		}
	}

	private void createM_PriceList_Version(@NonNull final Map<String, String> row)
	{
		final String priceListIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_PriceList_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(row, I_M_PriceList_Version.COLUMNNAME_ValidFrom);
		final String name = DataTableUtil.extractStringForColumnName(row, I_M_PriceList_Version.COLUMNNAME_Name);
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_PriceList_Version.COLUMNNAME_Description);

		final I_M_PriceList_Version m_priceList_Version = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);
		m_priceList_Version.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		m_priceList_Version.setM_PriceList_ID(priceListTable.get(priceListIdentifier).getM_PriceList_ID());
		m_priceList_Version.setName(name);
		m_priceList_Version.setDescription(description);
		m_priceList_Version.setValidFrom(validFrom);

		saveRecord(m_priceList_Version);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(row, I_M_PriceList_Version.Table_Name);
		priceListVersionTable.putOrReplace(recordIdentifier, m_priceList_Version);
	}

	@And("metasfresh contains M_ProductPrices")
	public void add_M_ProductPrice(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createM_ProductPrice(tableRow);
		}
	}

	private void createM_ProductPrice(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		final BigDecimal priceStd = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_PriceStd);

		final String taxCategoryInternalName = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "." + I_C_TaxCategory.COLUMNNAME_InternalName);
		final Optional<TaxCategoryId> taxCategoryId = taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName);
		assertThat(taxCategoryId).as("Missing taxCategory for internalName=%s", taxCategoryInternalName).isPresent();

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId productPriceUomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final String plvIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.newInstance(I_M_ProductPrice.class);

		productPrice.setM_PriceList_Version_ID(priceListVersionTable.get(plvIdentifier).getM_PriceList_Version_ID());
		productPrice.setM_Product_ID(product.getM_Product_ID());
		productPrice.setC_UOM_ID(productPriceUomId.getRepoId());
		productPrice.setPriceStd(priceStd);
		productPrice.setC_TaxCategory_ID(taxCategoryId.get().getRepoId());

		saveRecord(productPrice);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_M_ProductPrice.Table_Name);
		productPriceTable.putOrReplace(recordIdentifier, productPrice);
	}
}
