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

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.location.ICountryDAO;
import de.metas.money.CurrencyId;
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
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Campaign_Price;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class C_CampaignPrice_StepDef
{
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	private final M_Product_StepDefData productTable;
	private final CurrencyRepository currencyRepository;
	private final C_BPartner_StepDefData bpartnerTable;
	private final M_PricingSystem_StepDefData pricingSystemTable;

	public C_CampaignPrice_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_PricingSystem_StepDefData pricingSystemTable)
	{
		this.productTable = productTable;
		this.currencyRepository = currencyRepository;
		this.bpartnerTable = bpartnerTable;
		this.pricingSystemTable = pricingSystemTable;
	}

	@And("metasfresh contains C_CampaignPrices")
	public void add_C_CampaignPrice(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createC_CampaignPrice(tableRow);
		}
	}

	private void createC_CampaignPrice(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Campaign_Price.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);

		final BigDecimal priceStd = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_Campaign_Price.COLUMNNAME_PriceStd);

		final String countryCode = DataTableUtil.extractStringForColumnName(tableRow, I_C_Country.Table_Name + "." + I_C_Country.COLUMNNAME_CountryCode);
		final I_C_Country country = countryDAO.retrieveCountryByCountryCode(countryCode);

		final String currencyISO = DataTableUtil.extractStringForColumnName(tableRow, I_C_Currency.Table_Name + "." + I_C_Currency.COLUMNNAME_ISO_Code);
		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyISO));

		final String taxCategoryInternalName = DataTableUtil.extractStringForColumnName(tableRow, I_C_Campaign_Price.COLUMNNAME_C_TaxCategory_ID + "." + I_C_TaxCategory.COLUMNNAME_InternalName);
		final Optional<TaxCategoryId> taxCategoryId = taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName);
		assertThat(taxCategoryId).as("Missing taxCategory for internalName=%s", taxCategoryInternalName).isPresent();

		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_C_Campaign_Price.COLUMNNAME_ValidFrom);
		final Timestamp validTo = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_C_Campaign_Price.COLUMNNAME_ValidTo);

		final String bpartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Campaign_Price.COLUMNNAME_C_BPartner_ID + ".Identifier");
		final Integer bPartnerGroupId = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_C_Campaign_Price.COLUMNNAME_C_BP_Group_ID );

		final Integer bPartnerId = Optional.ofNullable(bpartnerIdentifier)
				.map(bPIdentifier -> bpartnerTable.getOptional(bPIdentifier)
					.map(I_C_BPartner::getC_BPartner_ID)
					.orElseGet(() -> Integer.parseInt(bPIdentifier)))
				.orElse(null);

		final String pricingSystem = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Campaign_Price.COLUMNNAME_M_PricingSystem_ID + ".Identifier");
		final Integer pricingSystemId = Optional.ofNullable(pricingSystem)
				.map(pricingSys -> pricingSystemTable.getOptional(pricingSys)
						.map(I_M_PricingSystem::getM_PricingSystem_ID)
						.orElseGet(() -> Integer.parseInt(pricingSys)))
				.orElse(null);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId campaignPriceUOM = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final I_C_Campaign_Price campaignPrice = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_Campaign_Price.class);
		campaignPrice.setM_Product_ID(product.getM_Product_ID());
		campaignPrice.setPriceStd(priceStd);
		campaignPrice.setC_Country_ID(country.getC_Country_ID());
		campaignPrice.setC_Currency_ID(currencyId.getRepoId());
		campaignPrice.setC_TaxCategory_ID(taxCategoryId.get().getRepoId());
		campaignPrice.setValidFrom(validFrom);
		campaignPrice.setValidTo(validTo);
		campaignPrice.setC_UOM_ID(campaignPriceUOM.getRepoId());

		if (bPartnerId != null)
		{
			campaignPrice.setC_BPartner_ID(bPartnerId);
		}
		if (bPartnerGroupId != null)
		{
			campaignPrice.setC_BP_Group_ID(bPartnerGroupId);
		}
		if (pricingSystemId != null)
		{
			campaignPrice.setM_PricingSystem_ID(pricingSystemId);
		}

		InterfaceWrapperHelper.saveRecord(campaignPrice);
	}

}
