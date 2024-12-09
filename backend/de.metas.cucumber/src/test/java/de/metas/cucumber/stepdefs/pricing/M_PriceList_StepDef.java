/*
 * #%L
 * de.metas.cucumber
 * %%
<<<<<<< HEAD
 * Copyright (C) 2021 metas GmbH
=======
 * Copyright (C) 2023 metas GmbH
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.material.event.commons.AttributesKey;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Optionals;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_PriceList_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_PricingSystem_ID;

@RequiredArgsConstructor
public class M_PriceList_StepDef
{
	@NonNull private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_PricingSystem_StepDefData pricingSystemTable;
	@NonNull private final M_PriceList_StepDefData priceListTable;
	@NonNull private final M_PriceList_Version_StepDefData priceListVersionTable;
	@NonNull private final M_ProductPrice_StepDefData productPriceTable;
	@NonNull private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	@NonNull private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	@NonNull private final AD_Org_StepDefData orgTable;

	private static final LocalDate DEFAULT_ValidFrom = LocalDate.parse("2000-01-01");
	private static final String DEFAULT_TaxCategory_InternalName = "Normal";

	@And("metasfresh contains M_PricingSystems")
	public void add_M_PricingSystem(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createM_PricingSystem);
	}

	@And("metasfresh contains M_PriceLists")
	public void add_M_PriceList(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createM_PriceList);
	}

	private void createM_PricingSystem(@NonNull final DataTableRow row)
	{
		final ValueAndName valueAndName = row.suggestValueAndName();
		final String description = row.getAsOptionalString("Description").orElse(null);
		final boolean isActive = row.getAsOptionalBoolean("IsActive").orElseTrue();

		final PricingSystemId pricingSystemId = priceListDAO.getPricingSystemIdByValueOrNull(valueAndName.getValue());
		final I_M_PricingSystem m_pricingSystem = InterfaceWrapperHelper.loadOrNew(pricingSystemId, I_M_PricingSystem.class);

		final OrgId orgId = row.getAsOptionalIdentifier("AD_Org_ID").map(orgTable::getId).orElse(ORG_ID);

		m_pricingSystem.setName(valueAndName.getName());
		m_pricingSystem.setValue(valueAndName.getValue());
		m_pricingSystem.setIsActive(isActive);
		m_pricingSystem.setDescription(description);
		m_pricingSystem.setAD_Org_ID(orgId.getRepoId());

		saveRecord(m_pricingSystem);
		row.getAsIdentifier().putOrReplace(pricingSystemTable, m_pricingSystem);
	}

	private void createM_PriceList(@NonNull final DataTableRow row)
	{
		I_M_PriceList priceList = null;

		final I_M_PricingSystem pricingSystem = row.getAsIdentifier(COLUMNNAME_M_PricingSystem_ID).lookupIn(pricingSystemTable);
		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID());
		final I_C_Country country = extractOptionalCountry(row).orElse(null);
		final SOTrx soTrx = SOTrx.ofBoolean(row.getAsBoolean("SOTrx"));
		if (country != null)
		{
			final CountryId countryId = CountryId.ofRepoId(country.getC_Country_ID());
			priceList = getExistingPriceList(soTrx, pricingSystemId, countryId).orElse(null);
		}

		if (priceList == null)
		{
			priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);
		}

		final OrgId orgId = row.getAsOptionalIdentifier("AD_Org_ID").map(orgTable::getId).orElse(StepDefConstants.ORG_ID);
		final String name = row.getAsOptionalName("Name").orElseGet(() -> pricingSystem.getName() + "_" + (country != null ? country.getCountryCode() : "-") + "_" + soTrx);

		priceList.setAD_Org_ID(orgId.getRepoId());
		priceList.setM_PricingSystem_ID(pricingSystemId.getRepoId());
		priceList.setC_Currency_ID(extractCurrencyId(row).getRepoId());
		priceList.setName(name);
		priceList.setIsTaxIncluded(row.getAsOptionalBoolean("IsTaxIncluded").orElseFalse());
		priceList.setPricePrecision(row.getAsOptionalInt("PricePrecision").orElse(2));
		priceList.setIsActive(row.getAsOptionalBoolean("IsActive").orElseTrue());
		priceList.setIsSOPriceList(soTrx.toBoolean());
		priceList.setC_Country_ID(country != null ? country.getC_Country_ID() : -1);

		row.getAsOptionalString("Description").ifPresent(priceList::setDescription);

		saveRecord(priceList);
		row.getAsIdentifier().putOrReplace(priceListTable, priceList);
	}

	private Optional<I_M_PriceList> getExistingPriceList(@NonNull final SOTrx soTrx, @NonNull final PricingSystemId pricingSystemId, final CountryId countryId)
	{
		final List<I_M_PriceList> existingPriceLists = priceListDAO.retrievePriceLists(pricingSystemId, countryId, soTrx);
		return existingPriceLists.isEmpty()
				? Optional.empty()
				: Optional.of(existingPriceLists.get(0));
	}

	private Optional<I_C_Country> extractOptionalCountry(final DataTableRow row)
	{
		return Optionals.firstPresentOfSuppliers(
						() -> row.getAsOptionalString("C_Country_ID"),
						() -> row.getAsOptionalString("C_Country.CountryCode")
				)
				.map(countryDAO::retrieveCountryByCountryCode);
	}

	@NonNull
	private CurrencyId extractCurrencyId(final DataTableRow row)
	{
		final CurrencyCode currencyCode = extractCurrencyCode(row);
		return currencyRepository.getCurrencyIdByCurrencyCode(currencyCode);
	}

<<<<<<< HEAD
=======
	private I_M_PriceList getExistingPriceList(final boolean soTrx, final int pricingSystemId, final CountryId countryId)
	{
		final I_M_PriceList m_priceList;
		final List<I_M_PriceList> existingPriceLists = priceListDAO.retrievePriceLists(PricingSystemId.ofRepoId(pricingSystemId), countryId, SOTrx.ofBoolean(soTrx));
		m_priceList = existingPriceLists.isEmpty() ? InterfaceWrapperHelper.newInstance(I_M_PriceList.class) : existingPriceLists.get(0);
		return m_priceList;
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@NonNull
	private static CurrencyCode extractCurrencyCode(final DataTableRow row)
	{
		final String currencyCodeStr = CoalesceUtil.coalesceSuppliersNotNull(
				() -> row.getAsOptionalString("C_Currency_ID").orElse(null),
				() -> row.getAsString("C_Currency.ISO_Code")
		);

		return CurrencyCode.ofThreeLetterCode(currencyCodeStr);
	}

	@And("metasfresh contains M_PriceList_Versions")
	public void add_M_PriceListVersion(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createM_PriceList_Version);
	}

	private void createM_PriceList_Version(@NonNull final DataTableRow row)
	{
		final @NonNull StepDefDataIdentifier priceListIdentifier2 = row.getAsIdentifier(COLUMNNAME_M_PriceList_ID);
		final I_M_PriceList priceList = priceListTable.get(priceListIdentifier2);

		final ZonedDateTime validFrom = row.getAsOptionalLocalDate(I_M_PriceList_Version.COLUMNNAME_ValidFrom)
				.orElseGet(((() -> DEFAULT_ValidFrom)))
				.atStartOfDay(SystemTime.zoneId()); // we shall use org's timezone but for now we use system zone to keep it working as before
		final String description = row.getAsOptionalString(I_M_PriceList_Version.COLUMNNAME_Description).orElse(null);

		I_M_PriceList_Version m_priceList_Version = priceListDAO.retrievePriceListVersionOrNull(PriceListId.ofRepoId(priceList.getM_PriceList_ID()), validFrom, null);
		if (m_priceList_Version == null)
		{
			m_priceList_Version = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);
		}

		m_priceList_Version.setAD_Org_ID(priceList.getAD_Org_ID());
		m_priceList_Version.setM_PriceList_ID(priceList.getM_PriceList_ID());
		m_priceList_Version.setDescription(description);
		m_priceList_Version.setValidFrom(Timestamp.from(validFrom.toInstant()));

		saveRecord(m_priceList_Version);
		row.getAsIdentifier().putOrReplace(priceListVersionTable, m_priceList_Version);
	}

	@And("metasfresh contains M_ProductPrices")
	public void add_M_ProductPrice(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createM_ProductPrice);
	}

<<<<<<< HEAD
=======
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

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@And("update M_ProductPrice:")
	public void update_M_ProductPrice(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String productPriceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductPrice.COLUMNNAME_M_ProductPrice_ID + ".Identifier");
			final Integer productPriceID = productPriceTable.getOptional(productPriceIdentifier)
					.map(I_M_ProductPrice::getM_ProductPrice_ID)
					.orElseGet(() -> Integer.parseInt(productPriceIdentifier));

			assertThat(productPriceID).isNotNull();

			final I_M_ProductPrice productPrice = InterfaceWrapperHelper.load(productPriceID, I_M_ProductPrice.class);

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

			final String invoicableQtyBasedOn = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_ProductPrice.COLUMNNAME_InvoicableQtyBasedOn);
			if (Check.isNotBlank(invoicableQtyBasedOn))
			{
				productPrice.setInvoicableQtyBasedOn(invoicableQtyBasedOn);
			}

			saveRecord(productPrice);

			productPriceTable.putOrReplace(productPriceIdentifier, productPrice);
		}
	}

	private void createM_ProductPrice(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_M_ProductPrice.COLUMNNAME_M_Product_ID);
		final int productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(productIdentifier::getAsInt);

		final BigDecimal priceStd = row.getAsBigDecimal(I_M_ProductPrice.COLUMNNAME_PriceStd);

		final UomId productPriceUomId = uomDAO.getUomIdByX12DE355(row.getAsUOMCode("C_UOM_ID"));

		final @NonNull StepDefDataIdentifier plvIdentifier = row.getAsIdentifier(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID);
		final Optional<I_M_PriceList_Version> priceListVersionOptional = priceListVersionTable.getOptional(plvIdentifier);

		final I_M_PriceList_Version priceListVersion = priceListVersionOptional.orElseGet(() -> InterfaceWrapperHelper.load(plvIdentifier.getAsInt(), I_M_PriceList_Version.class));

		final I_M_ProductPrice existingProductPrice = lookupForProductPrice(row);

		final de.metas.handlingunits.model.I_M_ProductPrice productPrice = existingProductPrice == null
				? InterfaceWrapperHelper.newInstance(de.metas.handlingunits.model.I_M_ProductPrice.class)
				: InterfaceWrapperHelper.load(existingProductPrice.getM_ProductPrice_ID(), de.metas.handlingunits.model.I_M_ProductPrice.class);

<<<<<<< HEAD
=======
		productPrice.setIsActive(true);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		productPrice.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());

		productPrice.setM_Product_ID(productId);
		productPrice.setC_UOM_ID(productPriceUomId.getRepoId());
		productPrice.setPriceStd(priceStd);
		productPrice.setC_TaxCategory_ID(extractTaxCategoryId(row).getRepoId());

		row.getAsOptionalString(I_M_ProductPrice.COLUMNNAME_UseScalePrice).ifPresent(productPrice::setUseScalePrice);
		row.getAsOptionalEnum(I_M_ProductPrice.COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn.class).ifPresent(invoiceableQtyBasedOn -> productPrice.setInvoicableQtyBasedOn(invoiceableQtyBasedOn.getCode()));

		row.getAsOptionalIdentifier(de.metas.handlingunits.model.I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.map(huPiItemProductTable::get)
				.ifPresent(productPrice::setM_HU_PI_Item_Product);

		row.getAsOptionalBoolean(I_M_ProductPrice.COLUMNNAME_IsAttributeDependant).ifPresent(productPrice::setIsAttributeDependant);

		row.getAsOptionalIdentifier(I_M_ProductPrice.COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::get)
				.ifPresent(asi -> productPrice.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID()));

		row.getAsOptionalInt(I_M_ProductPrice.COLUMNNAME_SeqNo)
				.ifPresent(productPrice::setSeqNo);

		saveRecord(productPrice);
		row.getAsOptionalIdentifier().ifPresent(id -> id.putOrReplace(productPriceTable, productPrice));
	}

	private TaxCategoryId extractTaxCategoryId(final @NonNull DataTableRow row)
	{
		final String taxCategoryInternalName = CoalesceUtil.coalesceSuppliersNotNull(
				() -> row.getAsOptionalString(I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID).orElse(null),
				() -> row.getAsOptionalString(I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "." + I_C_TaxCategory.COLUMNNAME_InternalName).orElse(null),
				() -> DEFAULT_TaxCategory_InternalName
		);

		return taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName)
				.orElseThrow(() -> new AdempiereException("Missing C_TaxCategory for internalName `" + taxCategoryInternalName + "` of row " + row));
	}

	private I_M_ProductPrice lookupForProductPrice(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_M_ProductPrice.COLUMNNAME_M_Product_ID);
		final int productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(productIdentifier::getAsInt);

		final int plvId = row.getAsIdentifier(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID)
				.lookupOrLoadById(priceListVersionTable, id -> InterfaceWrapperHelper.load(id, I_M_PriceList_Version.class))
				.getM_PriceList_Version_ID();

		final BigDecimal priceStd = row.getAsBigDecimal(I_M_ProductPrice.COLUMNNAME_PriceStd);

		final IQueryBuilder<I_M_ProductPrice> queryBuilder = queryBL.createQueryBuilder(I_M_ProductPrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, plvId)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_PriceStd, priceStd);

		row.getAsOptionalIdentifier(de.metas.handlingunits.model.I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.map(huPiItemProductTable::get)
				.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
				.ifPresent(huPiItemProductId -> queryBuilder.addEqualsFilter(de.metas.handlingunits.model.I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID, huPiItemProductId));

		row.getAsOptionalBoolean(I_M_ProductPrice.COLUMNNAME_IsAttributeDependant)
				.ifPresent(isAttributeDependant -> queryBuilder.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_IsAttributeDependant, isAttributeDependant));

		return queryBuilder
				.create()
				.list()
				.stream()
				.filter(record -> filterProductPriceByASI(row, record))
				.findFirst()
				.orElse(null);
	}

	private boolean filterProductPriceByASI(@NonNull final DataTableRow row, @NonNull final I_M_ProductPrice productPrice)
	{
		final AttributesKey expectedAttributesKey = row.getAsOptionalIdentifier(I_M_ProductPrice.COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::get)
				.map(this::toAttributesKey)
				.orElse(AttributesKey.NONE);

		if (AttributeSetInstanceId.ofRepoIdOrNone(productPrice.getM_AttributeSetInstance_ID()).isNone())
		{
			return true;
		}

		final I_M_AttributeSetInstance actualAsi = InterfaceWrapperHelper.load(productPrice.getM_AttributeSetInstance_ID(), I_M_AttributeSetInstance.class);
		assertThat(actualAsi).isNotNull();

		final AttributesKey actualAttributesKey = toAttributesKey(actualAsi);

		return expectedAttributesKey.equals(actualAttributesKey);
	}

	@NonNull
	private AttributesKey toAttributesKey(@NonNull final I_M_AttributeSetInstance asiRecord)
	{
		return Optional.of(asiRecord)
				.map(I_M_AttributeSetInstance::getM_AttributeSetInstance_ID)
				.map(AttributeSetInstanceId::ofRepoId)
				.map(AttributesKeys::createAttributesKeyFromASIStorageAttributes)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.orElse(AttributesKey.NONE);
	}
}