package de.metas.rest_api.bpartner_pricelist;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyCode;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.productprice.ProductPriceRepository;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.ProductId;
import de.metas.rest_api.bpartner_pricelist.response.JsonResponsePrice;
import de.metas.rest_api.bpartner_pricelist.response.JsonResponsePriceList;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class BpartnerPriceListRestControllerTest
{
	private BpartnerPriceListRestController restController;

	private CountryId countryId_DE;
	private CurrencyId currencyId_EUR;
	private ProductId productId1;
	private ProductId productId2;
	private ProductId productId3;

	private TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoId(12345);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		createMasterData();

		restController = new BpartnerPriceListRestController(new BpartnerPriceListServicesFacade(new ProductPriceRepository(new ProductTaxCategoryService(new ProductTaxCategoryRepository()))));
	}

	private void createMasterData()
	{
		countryId_DE = createCountry("DE");
		currencyId_EUR = createCurrency(CurrencyCode.EUR);
		productId1 = createProduct("productValue1");
		productId2 = createProduct("productValue2");
		productId3 = createProduct("productValue3");
	}

	@Test
	public void standardCase()
	{
		final LocalDate date = LocalDate.of(2019, Month.SEPTEMBER, 04);

		//
		// Master data
		{
			final PriceListVersionCreateResult priceListVersion = preparePriceListVersion()
					.countryId(countryId_DE)
					.currencyId(currencyId_EUR)
					.soTrx(SOTrx.SALES)
					.priceListVersionValidFrom(date)
					.build();
			createProductPrice(priceListVersion, productId1, 10);
			createProductPrice(priceListVersion, productId2, 20);
			createProductPrice(priceListVersion, productId3, 30);

			prepareBPartner()
					.value("bpartnerValue1")
					.externalId("externalId1")
					.salesPricingSystemId(priceListVersion.getPricingSystemId())
					.build();
		}

		final ResponseEntity<JsonResponsePriceList> result = restController.getSalesPriceList(
				"val-bpartnerValue1",
				"DE",
				date.toString());

		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

		final JsonResponsePriceList resultBody = result.getBody();
		assertThat(resultBody).isEqualTo(JsonResponsePriceList.builder()
				.price(JsonResponsePrice.builder()
						.productId(productId1)
						.productCode("productValue1")
						.price(new BigDecimal("10"))
						.currencyCode(CurrencyCode.EUR)
						.taxCategoryId(taxCategoryId)
						.build())
				.price(JsonResponsePrice.builder()
						.productId(productId2)
						.productCode("productValue2")
						.price(new BigDecimal("20"))
						.currencyCode(CurrencyCode.EUR)
						.taxCategoryId(taxCategoryId)
						.build())
				.price(JsonResponsePrice.builder()
						.productId(productId3)
						.productCode("productValue3")
						.price(new BigDecimal("30"))
						.currencyCode(CurrencyCode.EUR)
						.taxCategoryId(taxCategoryId)
						.build())
				.build());
	}

	private CountryId createCountry(String countryCode)
	{
		final I_C_Country record = newInstance(I_C_Country.class);
		record.setName(countryCode);
		record.setCountryCode(countryCode);
		saveRecord(record);
		return CountryId.ofRepoId(record.getC_Country_ID());
	}

	private CurrencyId createCurrency(final CurrencyCode currencyCode)
	{
		final I_C_Currency record = newInstance(I_C_Currency.class);
		record.setISO_Code(currencyCode.toThreeLetterCode());
		saveRecord(record);
		return CurrencyId.ofRepoId(record.getC_Currency_ID());
	}

	@Builder(builderMethodName = "prepareBPartner", builderClassName = "_createBPartnerBuilder")
	private BPartnerId _createBPartner(
			final String value,
			final String externalId,
			final PricingSystemId salesPricingSystemId,
			final PricingSystemId purchasePricingSystemId)
	{
		final I_C_BPartner record = newInstance(I_C_BPartner.class);
		record.setValue(value);
		record.setExternalId(externalId);
		record.setM_PricingSystem_ID(PricingSystemId.toRepoId(salesPricingSystemId));
		record.setPO_PricingSystem_ID(PricingSystemId.toRepoId(purchasePricingSystemId));
		saveRecord(record);
		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}

	@Value
	@Builder
	private static class PriceListVersionCreateResult
	{
		@NonNull
		PricingSystemId pricingSystemId;
		@NonNull
		PriceListId priceListId;
		@NonNull
		PriceListVersionId priceListVersionId;
	}

	@Builder(builderMethodName = "preparePriceListVersion", builderClassName = "_createPriceListVersionBuilder")
	private PriceListVersionCreateResult _createPriceListVersion(
			@Nullable final CountryId countryId,
			@Nullable final CurrencyId currencyId,
			@NonNull final SOTrx soTrx,
			@NonNull final LocalDate priceListVersionValidFrom)
	{
		final I_M_PricingSystem pricingSystem = newInstance(I_M_PricingSystem.class);
		saveRecord(pricingSystem);
		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		priceList.setC_Currency_ID(currencyId.getRepoId());
		priceList.setC_Country_ID(CountryId.toRepoId(countryId));
		priceList.setIsSOPriceList(soTrx.toBoolean());
		saveRecord(priceList);
		final PriceListId priceListId = PriceListId.ofRepoId(priceList.getM_PriceList_ID());

		final I_M_PriceList_Version priceListVersion = newInstance(I_M_PriceList_Version.class);
		priceListVersion.setM_PriceList_ID(priceList.getM_PriceList_ID());
		priceListVersion.setValidFrom(TimeUtil.asTimestamp(priceListVersionValidFrom));
		saveRecord(priceListVersion);
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID());

		return PriceListVersionCreateResult.builder()
				.pricingSystemId(pricingSystemId)
				.priceListId(priceListId)
				.priceListVersionId(priceListVersionId)
				.build();
	}

	private ProductId createProduct(final String productValue)
	{
		I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(productValue);
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private void createProductPrice(
			final PriceListVersionCreateResult priceListVersion,
			final ProductId productId,
			int price)
	{
		final PriceListVersionId priceListVersionId = priceListVersion.getPriceListVersionId();

		final I_M_ProductPrice productPrice = newInstance(I_M_ProductPrice.class);
		productPrice.setM_PriceList_Version_ID(priceListVersionId.getRepoId());
		productPrice.setM_Product_ID(productId.getRepoId());
		productPrice.setPriceStd(BigDecimal.valueOf(price));
		productPrice.setC_TaxCategory_ID(taxCategoryId.getRepoId());
		productPrice.setC_UOM_ID(UomId.EACH.getRepoId());
		saveRecord(productPrice);
	}
}
