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

import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.ProductId;
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
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_PriceList_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_PricingSystem_ID;

public class M_PriceList_StepDef
{
	private final CurrencyRepository currencyRepository;
	private final M_Product_StepDefData productTable;
	private final M_PricingSystem_StepDefData pricingSystemTable;
	private final M_PriceList_StepDefData priceListTable;
	private final M_PriceList_Version_StepDefData priceListVersionTable;
	private final M_ProductPrice_StepDefData productPriceTable;
	private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;

	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

	public M_PriceList_StepDef(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_PricingSystem_StepDefData pricingSystemTable,
			@NonNull final M_PriceList_StepDefData priceListTable,
			@NonNull final M_PriceList_Version_StepDefData priceListVersionTable,
			@NonNull final M_ProductPrice_StepDefData productPriceTable,
			@NonNull final M_HU_PI_Item_Product_StepDefData huPiItemProductTable)
	{
		this.currencyRepository = currencyRepository;
		this.productTable = productTable;
		this.pricingSystemTable = pricingSystemTable;
		this.priceListTable = priceListTable;
		this.priceListVersionTable = priceListVersionTable;
		this.productPriceTable = productPriceTable;
		this.huPiItemProductTable = huPiItemProductTable;
	}

	@And("metasfresh contains M_PricingSystems")
	public void add_M_PricingSystem(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			createM_PricingSystem(dataTableRow);
		}
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

		final PricingSystemId pricingSystemId = priceListDAO.getPricingSystemIdByValueOrNull(value);

		final I_M_PricingSystem m_pricingSystem = InterfaceWrapperHelper.loadOrNew(pricingSystemId, I_M_PricingSystem.class);

		m_pricingSystem.setAD_Org_ID(ORG_ID.getRepoId());
		m_pricingSystem.setName(name);
		m_pricingSystem.setValue(value);
		m_pricingSystem.setIsActive(isActive);
		m_pricingSystem.setDescription(description);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(row, I_M_PricingSystem.Table_Name);

		saveRecord(m_pricingSystem);

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

		final int pricingSystemId = pricingSystemTable.get(pricingSystemIdentifier).getM_PricingSystem_ID();
		CountryId countryId = null;
		final I_M_PriceList m_priceList;
		if (countryCode != null)
		{
			final I_C_Country countryPO = Services.get(ICountryDAO.class).retrieveCountryByCountryCode(countryCode);
			countryId = CountryId.ofRepoIdOrNull(countryPO.getC_Country_ID());
			m_priceList = getExistingPriceList(soTrx, pricingSystemId, countryId);
		}
		else
		{
			m_priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);
		}

		m_priceList.setAD_Org_ID(ORG_ID.getRepoId());
		m_priceList.setM_PricingSystem_ID(pricingSystemId);
		m_priceList.setC_Currency_ID(currencyId.getRepoId());
		m_priceList.setName(name);
		m_priceList.setIsTaxIncluded(isTaxIncluded);
		m_priceList.setPricePrecision(Integer.parseInt(pricePrecision));
		m_priceList.setIsActive(isActive);
		m_priceList.setIsSOPriceList(soTrx);
		m_priceList.setC_Country_ID(CountryId.toRepoId(countryId));

		if (description != null)
		{
			m_priceList.setDescription(description);
		}

		saveRecord(m_priceList);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(row, I_M_PriceList.Table_Name);
		priceListTable.putOrReplace(recordIdentifier, m_priceList);
	}

	private I_M_PriceList getExistingPriceList(final boolean soTrx, final int pricingSystemId, final CountryId countryId)
	{
		final I_M_PriceList m_priceList;
		final List<I_M_PriceList> existingPriceLists = priceListDAO.retrievePriceLists(PricingSystemId.ofRepoId(pricingSystemId), countryId, SOTrx.ofBoolean(soTrx));
		m_priceList = existingPriceLists.isEmpty() ? InterfaceWrapperHelper.newInstance(I_M_PriceList.class) : existingPriceLists.get(0);
		return m_priceList;
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
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_PriceList_Version.COLUMNNAME_Description);
		final int priceListID = priceListTable.get(priceListIdentifier).getM_PriceList_ID();

		I_M_PriceList_Version m_priceList_Version = priceListDAO.retrievePriceListVersionOrNull(PriceListId.ofRepoId(priceListID), TimeUtil.asZonedDateTime(validFrom.toInstant()), null);
		if (m_priceList_Version == null)
		{
			m_priceList_Version = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);
		}

		m_priceList_Version.setAD_Org_ID(ORG_ID.getRepoId());

		m_priceList_Version.setM_PriceList_ID(priceListID);
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

	@And("update metasfresh masterdata M_ProductPrice")
	public void update_masterdata_M_ProductPrice(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			updateMasterDataM_ProductPrice(tableRow);
		}
	}

	private void updateMasterDataM_ProductPrice(@NonNull final Map<String, String> tableRow)
	{
		final int productPriceId = DataTableUtil.extractIntForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_M_ProductPrice_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_M_ProductPrice productPriceRecord = InterfaceWrapperHelper.load(productPriceId, I_M_ProductPrice.class);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId productPriceUomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		productPriceRecord.setC_UOM_ID(productPriceUomId.getRepoId());

		saveRecord(productPriceRecord);
	}

	@And("update M_ProductPrice:")
	public void update_M_ProductPrice(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String productPriceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_M_ProductPrice_ID + ".Identifier");
			final I_M_ProductPrice productPrice = productPriceTable.get(productPriceIdentifier);

			final BigDecimal priceStd = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_M_ProductPrice.COLUMNNAME_PriceStd);
			if (priceStd != null)
			{
				productPrice.setPriceStd(priceStd);
			}

			final String x12de355Code = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			if (Check.isNotBlank(x12de355Code))
			{
				final UomId productPriceUomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));
				productPrice.setC_UOM_ID(productPriceUomId.getRepoId());
			}

			final Boolean isActive = DataTableUtil.extractBooleanForColumnNameOr(tableRow, I_M_ProductPrice.COLUMNNAME_IsActive, true);
			productPrice.setIsActive(isActive);

			saveRecord(productPrice);

			productPriceTable.putOrReplace(productPriceIdentifier, productPrice);
		}
	}

	private void createM_ProductPrice(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final int productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final BigDecimal priceStd = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_PriceStd);

		final String taxCategoryInternalName = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "." + I_C_TaxCategory.COLUMNNAME_InternalName);
		final Optional<TaxCategoryId> taxCategoryId = taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName);
		assertThat(taxCategoryId).as("Missing taxCategory for internalName=%s", taxCategoryInternalName).isPresent();

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId productPriceUomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final String plvIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final Optional<I_M_PriceList_Version> priceListVersionOptional = priceListVersionTable.getOptional(plvIdentifier);

		final I_M_PriceList_Version priceListVersion = priceListVersionOptional.orElseGet(() -> InterfaceWrapperHelper.load(Integer.parseInt(plvIdentifier), I_M_PriceList_Version.class));

		final I_M_ProductPrice existingProductPrice = ProductPrices.retrieveMainProductPriceOrNull(priceListVersion, ProductId.ofRepoId(productId));

		final String invoiceableQtyBasedOn = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_ProductPrice.COLUMNNAME_InvoicableQtyBasedOn);

		final String useScalePrice = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_ProductPrice.COLUMNNAME_UseScalePrice);

		final de.metas.handlingunits.model.I_M_ProductPrice productPrice = existingProductPrice == null
				? InterfaceWrapperHelper.newInstance(de.metas.handlingunits.model.I_M_ProductPrice.class)
				: InterfaceWrapperHelper.load(existingProductPrice.getM_ProductPrice_ID(), de.metas.handlingunits.model.I_M_ProductPrice.class);

		productPrice.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());

		productPrice.setM_Product_ID(productId);
		productPrice.setC_UOM_ID(productPriceUomId.getRepoId());
		productPrice.setPriceStd(priceStd);
		productPrice.setC_TaxCategory_ID(taxCategoryId.get().getRepoId());

		if (useScalePrice != null)
		{
			productPrice.setUseScalePrice(useScalePrice);
		}

		if (Check.isNotBlank(invoiceableQtyBasedOn))
		{
			productPrice.setInvoicableQtyBasedOn(invoiceableQtyBasedOn);
		}

		final String huPiItemProductIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + de.metas.handlingunits.model.I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(huPiItemProductIdentifier))
		{
			final I_M_HU_PI_Item_Product packingItem = huPiItemProductTable.get(huPiItemProductIdentifier);

			productPrice.setM_HU_PI_Item_Product(packingItem);
		}

		saveRecord(productPrice);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_M_ProductPrice.Table_Name);
		productPriceTable.putOrReplace(recordIdentifier, productPrice);
	}
}
