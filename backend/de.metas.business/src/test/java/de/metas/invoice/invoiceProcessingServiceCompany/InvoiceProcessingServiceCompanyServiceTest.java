/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.invoice.invoiceProcessingServiceCompany;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.Amount;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.IDocTypeDAO.DocTypeCreateRequest;
import de.metas.document.engine.DocStatus;
import de.metas.interfaces.I_C_BPartner;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.location.CountryId;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.payment.PaymentRule;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.tax.api.TaxCategoryId;
import de.metas.user.UserRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_InvoiceProcessingServiceCompany;
import org.compiere.model.I_InvoiceProcessingServiceCompany_BPartnerAssignment;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class InvoiceProcessingServiceCompanyServiceTest
{
	private InvoiceProcessingServiceCompanyConfigRepository configRepository;
	private InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService;

	private BPartnerId serviceCompanyBPartnerId;
	private DocTypeId serviceInvoiceDocTypeId;
	private ProductId serviceFeeProductId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		configRepository = new InvoiceProcessingServiceCompanyConfigRepository();
		final MoneyService moneyService = new MoneyService(new CurrencyRepository());
		invoiceProcessingServiceCompanyService = new InvoiceProcessingServiceCompanyService(configRepository, moneyService);

		serviceCompanyBPartnerId = BPartnerId.ofRepoId(111);
		serviceInvoiceDocTypeId = DocTypeId.ofRepoId(222);
		serviceFeeProductId = ProductId.ofRepoId(333);
	}

	@Nested
	public class computeFee
	{

		@Test
		public void standardCase()
		{
			config()
					.feePercentageOfGrandTotal("2")
					.customerId(BPartnerId.ofRepoId(2))
					.validFrom(LocalDate.parse("2020-04-30").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			final Optional<InvoiceProcessingFeeCalculation> result = invoiceProcessingServiceCompanyService.computeFee(InvoiceProcessingFeeComputeRequest.builder()
					.orgId(OrgId.ofRepoId(1))
					.evaluationDate(LocalDate.parse("2020-04-30").atStartOfDay(ZoneId.of("UTC+5")))
					.customerId(BPartnerId.ofRepoId(2))
					.invoiceId(InvoiceId.ofRepoId(3))
					.invoiceGrandTotal(Amount.of(100, CurrencyCode.EUR))
					.build());

			assertThat(result.orElse(null))
					.isEqualToComparingFieldByField(InvoiceProcessingFeeCalculation.builder()
							.orgId(OrgId.ofRepoId(1))
							.evaluationDate(LocalDate.parse("2020-04-30").atStartOfDay(ZoneId.of("UTC+5")))
							.customerId(BPartnerId.ofRepoId(2))
							.invoiceId(InvoiceId.ofRepoId(3))
							//
							.serviceCompanyBPartnerId(serviceCompanyBPartnerId)
							.serviceInvoiceDocTypeId(serviceInvoiceDocTypeId)
							.serviceFeeProductId(serviceFeeProductId)
							//
							.feeAmountIncludingTax(Amount.of(2, CurrencyCode.EUR))
							// 
							.build());
		}

		@Test
		public void alreadyExistingServiceFeeInvoice()
		{
			config()
					.feePercentageOfGrandTotal("2")
					.customerId(BPartnerId.ofRepoId(2))
					.validFrom(LocalDate.parse("2020-04-30").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			final I_C_Invoice serviceFeeInvoice = newInstance(I_C_Invoice.class);
			serviceFeeInvoice.setRef_Invoice_ID(3);
			serviceFeeInvoice.setDocStatus(DocStatus.Completed.getCode());
			saveRecord(serviceFeeInvoice);

			final Optional<InvoiceProcessingFeeCalculation> result = invoiceProcessingServiceCompanyService.computeFee(InvoiceProcessingFeeComputeRequest.builder()
					.orgId(OrgId.ofRepoId(1))
					.evaluationDate(LocalDate.parse("2020-04-30").atStartOfDay(ZoneId.of("UTC+5")))
					.customerId(BPartnerId.ofRepoId(2))
					.invoiceId(InvoiceId.ofRepoId(3))
					.invoiceGrandTotal(Amount.of(100, CurrencyCode.EUR))
					.build());

			assertThat(result).isEmpty();
		}

		@Test
		public void customerIsNotAssignedToInvoiceProcessingServiceCompany()
		{
			config()
					.feePercentageOfGrandTotal("2")
					.customerId(BPartnerId.ofRepoId(99999999))
					.validFrom(LocalDate.parse("2020-04-30").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			final Optional<InvoiceProcessingFeeCalculation> result = invoiceProcessingServiceCompanyService.computeFee(InvoiceProcessingFeeComputeRequest.builder()
					.orgId(OrgId.ofRepoId(1))
					.evaluationDate(LocalDate.parse("2020-04-30").atStartOfDay(ZoneId.of("UTC+5")))
					.customerId(BPartnerId.ofRepoId(2))
					.invoiceId(InvoiceId.ofRepoId(3))
					.invoiceGrandTotal(Amount.of(100, CurrencyCode.EUR))
					.build());

			assertThat(result).isEmpty();
		}

		@Test
		public void customerAssignedButNotYetValidFrom()
		{
			config()
					.feePercentageOfGrandTotal("2")
					.customerId(BPartnerId.ofRepoId(2))
					.validFrom(LocalDate.parse("3020-04-30").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			final Optional<InvoiceProcessingFeeCalculation> result = invoiceProcessingServiceCompanyService.computeFee(InvoiceProcessingFeeComputeRequest.builder()
					.orgId(OrgId.ofRepoId(1))
					.evaluationDate(LocalDate.parse("2020-04-30").atStartOfDay(ZoneId.of("UTC+5")))
					.customerId(BPartnerId.ofRepoId(2))
					.invoiceId(InvoiceId.ofRepoId(3))
					.invoiceGrandTotal(Amount.of(100, CurrencyCode.EUR))
					.build());

			assertThat(result).isEmpty();
		}
	}

	@Nested
	class RepositoryTest
	{
		@Test
		void selectCorrectConfigByValidDate()
		{
			final BPartnerId bpartner1 = BPartnerId.ofRepoId(2);
			final BPartnerId bpartner2 = BPartnerId.ofRepoId(7);

			config()
					.feePercentageOfGrandTotal("4")
					.customerId(bpartner1)
					.validFrom(LocalDate.parse("2020-04-01").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			config()
					.feePercentageOfGrandTotal("3")
					.customerId(bpartner2)
					.customerId(bpartner1)
					.validFrom(LocalDate.parse("2020-03-01").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			config()
					.feePercentageOfGrandTotal("2")
					.customerId(bpartner1)
					.customerId(bpartner2)
					.validFrom(LocalDate.parse("2020-02-01").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			config()
					.feePercentageOfGrandTotal("1")
					.customerId(bpartner1)
					.customerId(bpartner2)
					.validFrom(LocalDate.parse("2020-01-01").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			assertThat(configRepository.getByCustomerId(bpartner1, LocalDate.parse("2019-12-31").atStartOfDay(ZoneId.of("UTC+5")))).isEmpty();

			assertCorrectConfigReturned(bpartner1, "3030-01-01", 4);
			assertCorrectConfigReturned(bpartner1, "2020-04-02", 4);
			assertCorrectConfigReturned(bpartner1, "2020-04-01", 4);
			assertCorrectConfigReturned(bpartner1, "2020-03-28", 3);
			assertCorrectConfigReturned(bpartner1, "2020-01-01", 1);

			assertThat(configRepository.getByCustomerId(bpartner2, LocalDate.parse("2019-12-31").atStartOfDay(ZoneId.of("UTC+5")))).isEmpty();

			assertCorrectConfigReturned(bpartner2, "3030-01-01", 3);
			assertCorrectConfigReturned(bpartner2, "2020-04-02", 3);
			assertCorrectConfigReturned(bpartner2, "2020-04-01", 3);
			assertCorrectConfigReturned(bpartner2, "2020-03-28", 3);
			assertCorrectConfigReturned(bpartner2, "2020-01-01", 1);
		}

		@Test
		void selectConfigByCompanyBPartnerAndValidFrom()
		{
			final BPartnerId bpartner1 = BPartnerId.ofRepoId(2);
			final BPartnerId bpartner2 = BPartnerId.ofRepoId(7);
			final BPartnerId bpartnerNoConfig = BPartnerId.ofRepoId(9);

			config()
					.feePercentageOfGrandTotal("4")
					.customerId(bpartner1)
					.validFrom(LocalDate.parse("2020-04-01").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			config()
					.feePercentageOfGrandTotal("3")
					.customerId(bpartner2)
					.customerId(bpartner1)
					.validFrom(LocalDate.parse("2020-03-01").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			config()
					.feePercentageOfGrandTotal("2")
					.customerId(bpartner1)
					.customerId(bpartner2)
					.validFrom(LocalDate.parse("2020-02-01").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			config()
					.feePercentageOfGrandTotal("1")
					.customerId(bpartner1)
					.customerId(bpartner2)
					.validFrom(LocalDate.parse("2020-01-01").atStartOfDay(ZoneId.of("UTC+5")))
					.build();

			assertCorrectConfigByValidFrom(serviceCompanyBPartnerId, "3030-01-01", "2020-04-01");
			assertCorrectConfigByValidFrom(serviceCompanyBPartnerId, "2020-04-02", "2020-04-01");
			assertCorrectConfigByValidFrom(serviceCompanyBPartnerId, "2020-04-01", "2020-04-01");
			assertCorrectConfigByValidFrom(serviceCompanyBPartnerId, "2020-03-28", "2020-03-01");
			assertCorrectConfigByValidFrom(serviceCompanyBPartnerId, "2020-01-01", "2020-01-01");
			assertCorrectConfigByValidFrom(serviceCompanyBPartnerId, "2019-01-01", "2020-01-01");

			assertThat(configRepository.getByPaymentBPartnerAndValidFromDate(bpartnerNoConfig, LocalDate.parse("2019-12-31").atStartOfDay(ZoneId.of("UTC+5")))).isEmpty();
			assertThat(configRepository.getByPaymentBPartnerAndValidFromDate(bpartnerNoConfig, LocalDate.parse("2020-04-02").atStartOfDay(ZoneId.of("UTC+5")))).isEmpty();
		}

		@Test
		void noConfigsExist()
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(2);

			assertThat(configRepository.getByCustomerId(bpartnerId, LocalDate.parse("2020-04-01").atStartOfDay(ZoneId.of("UTC+5")))).isEmpty();

			assertThat(configRepository.getByPaymentBPartnerAndValidFromDate(bpartnerId, LocalDate.parse("2019-12-31").atStartOfDay(ZoneId.of("UTC+5")))).isEmpty();
			assertThat(configRepository.getByPaymentBPartnerAndValidFromDate(bpartnerId, LocalDate.parse("2020-04-02").atStartOfDay(ZoneId.of("UTC+5")))).isEmpty();
		}

		private void assertCorrectConfigReturned(final BPartnerId bpartnerId, final String localDate, final int percentValue)
		{
			assertThat(configRepository.getByCustomerId(bpartnerId, LocalDate.parse(localDate).atStartOfDay(ZoneId.of("UTC+5"))))
					.hasValueSatisfying(config -> assertThat(config.getFeePercentageOfGrandTotalByBpartner(bpartnerId)).hasValue(Percent.of(percentValue)));
		}

		private void assertCorrectConfigByValidFrom(final BPartnerId bpartnerId, final String localDate, final String expectedValidFrom)
		{
			assertThat(configRepository.getByPaymentBPartnerAndValidFromDate(bpartnerId, LocalDate.parse(localDate).atStartOfDay(ZoneId.of("UTC+5"))))
					.hasValueSatisfying(config -> assertThat(config.getValidFrom())
							.isEqualTo(LocalDate.parse(expectedValidFrom).atStartOfDay(ZoneId.of("UTC+5"))));
		}
	}

	@Nested
	public class generateServiceInvoice
	{
		private ITrxManager trxManager;

		private OrgId orgId;

		private DocTypeId serviceInvoiceDocTypeId;
		private ProductId serviceFeeProductId;
		private BPartnerLocationId serviceCompanyBPartnerAndLocationId;

		private PriceListId servicePriceListId;

		@BeforeEach
		public void beforeEach()
		{
			final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
			SpringContextHolder.registerJUnitBean(IBPartnerBL.class, bpartnerBL);
			Services.registerService(IBPartnerBL.class, bpartnerBL);

			trxManager = Services.get(ITrxManager.class);

			createMasterData();
		}

		private void createMasterData()
		{
			orgId = createOrg(ZoneId.of("UTC-8"));

			serviceInvoiceDocTypeId = Services.get(IDocTypeDAO.class)
					.createDocType(DocTypeCreateRequest.builder()
							.ctx(Env.getCtx())
							.name("invoice processing fee vendor invoice")
							.docBaseType(InvoiceDocBaseType.VendorInvoice.getDocBaseType())
							.build());

			final I_C_UOM uomEach = BusinessTestHelper.createUomEach();
			serviceFeeProductId = createServiceProduct("Service Fee", uomEach);

			final TaxCategoryId taxCategoryId = createTaxCategory();
			final CountryId countryId = BusinessTestHelper.createCountry("DE");

			final PricingSystemId servicePricingSystemId = createPricingSystem();
			servicePriceListId = createPriceList(servicePricingSystemId, countryId, CurrencyCode.EUR);
			final PriceListVersionId servicePriceListVersionId = createPriceListVersion(servicePriceListId);
			createProductPrice(servicePriceListVersionId, serviceFeeProductId, uomEach, taxCategoryId);

			serviceCompanyBPartnerAndLocationId = bpartnerAndLocation()
					.purchasePricingSystemId(servicePricingSystemId)
					.countryId(countryId)
					.build();
		}

		private OrgId createOrg(final ZoneId timeZone)
		{
			final I_AD_Org org = newInstance(I_AD_Org.class);
			saveRecord(org);
			final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

			final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
			orgInfo.setAD_Org_ID(orgId.getRepoId());
			orgInfo.setTimeZone(timeZone.getId());
			orgInfo.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
			saveRecord(orgInfo);

			return orgId;
		}

		@SuppressWarnings({ "SameParameterValue", "ConstantConditions" })
		private ProductId createServiceProduct(
				@NonNull final String name,
				@Nullable final I_C_UOM uom)
		{
			final I_M_Product product = newInstance(I_M_Product.class);
			product.setValue(name);
			product.setName(name);
			product.setC_UOM_ID(uom.getC_UOM_ID());
			product.setProductType(ProductType.Service.getCode());
			product.setIsStocked(false);
			saveRecord(product);
			return ProductId.ofRepoId(product.getM_Product_ID());
		}

		@Builder(builderMethodName = "bpartnerAndLocation", builderClassName = "$BPartnerAndLocationBuilder")
		private BPartnerLocationId createBPartnerAndLocation(
				@NonNull final PricingSystemId purchasePricingSystemId,
				@NonNull final CountryId countryId)
		{
			final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
			bpartner.setPO_PricingSystem_ID(purchasePricingSystemId.getRepoId());
			bpartner.setPaymentRule(PaymentRule.OnCredit.getCode());
			saveRecord(bpartner);
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());

			final I_C_Location location = newInstance(I_C_Location.class);
			location.setC_Country_ID(countryId.getRepoId());
			saveRecord(location);

			final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class);
			bpartnerLocation.setC_BPartner_ID(bpartnerId.getRepoId());
			bpartnerLocation.setIsBillToDefault(true);
			bpartnerLocation.setIsBillTo(true);
			bpartnerLocation.setC_Location_ID(location.getC_Location_ID());
			saveRecord(bpartnerLocation);

			return BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocation.getC_BPartner_Location_ID());
		}

		private PricingSystemId createPricingSystem()
		{
			final I_M_PricingSystem pricingSystem = newInstance(I_M_PricingSystem.class);
			saveRecord(pricingSystem);
			return PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID());
		}

		@SuppressWarnings("SameParameterValue")
		private PriceListId createPriceList(
				@NonNull final PricingSystemId pricingSystemId,
				@NonNull final CountryId countryId,
				@NonNull final CurrencyCode currencyCode)
		{
			final Currency currency = Services.get(ICurrencyDAO.class).getByCurrencyCode(currencyCode);

			final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
			priceList.setM_PricingSystem_ID(pricingSystemId.getRepoId());
			priceList.setC_Country_ID(countryId.getRepoId());
			priceList.setC_Currency_ID(currency.getId().getRepoId());
			priceList.setPricePrecision(2);
			saveRecord(priceList);
			return PriceListId.ofRepoId(priceList.getM_PriceList_ID());
		}

		private PriceListVersionId createPriceListVersion(final PriceListId priceListId)
		{
			final I_M_PriceList_Version priceListVersion = newInstance(I_M_PriceList_Version.class);
			priceListVersion.setM_PriceList_ID(priceListId.getRepoId());
			priceListVersion.setValidFrom(TimeUtil.asTimestamp(LocalDate.parse("1970-01-01")));
			saveRecord(priceListVersion);
			return PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID());
		}

		private void createProductPrice(
				@NonNull final PriceListVersionId priceListVersionId,
				@NonNull final ProductId productId,
				@NonNull final I_C_UOM uom,
				@NonNull final TaxCategoryId taxCategoryId)
		{
			final I_M_ProductPrice productPrice = newInstance(I_M_ProductPrice.class);
			productPrice.setM_PriceList_Version_ID(priceListVersionId.getRepoId());
			productPrice.setM_Product_ID(productId.getRepoId());
			productPrice.setC_UOM_ID(uom.getC_UOM_ID());
			productPrice.setC_TaxCategory_ID(taxCategoryId.getRepoId());
			saveRecord(productPrice);
		}

		private TaxCategoryId createTaxCategory()
		{
			final I_C_TaxCategory taxCategory = newInstance(I_C_TaxCategory.class);
			saveRecord(taxCategory);
			return TaxCategoryId.ofRepoId(taxCategory.getC_TaxCategory_ID());
		}

		@SuppressWarnings("ConstantConditions")
		@Test
		public void standardCase()
		{
			final InvoiceProcessingFeeCalculation calculation = InvoiceProcessingFeeCalculation.builder()
					.orgId(orgId)
					.evaluationDate(LocalDate.parse("2020-04-30").atStartOfDay(ZoneId.of("UTC-8")))
					.customerId(BPartnerId.ofRepoId(2))
					.invoiceId(InvoiceId.ofRepoId(3))
					//
					.serviceCompanyBPartnerId(serviceCompanyBPartnerAndLocationId.getBpartnerId())
					.serviceInvoiceDocTypeId(serviceInvoiceDocTypeId)
					.serviceFeeProductId(serviceFeeProductId)
					//
					.feeAmountIncludingTax(Amount.of(2, CurrencyCode.EUR))
					//
					.build();

			final InvoiceId serviceInvoiceId = trxManager.callInThreadInheritedTrx(
					() -> invoiceProcessingServiceCompanyService.generateServiceInvoice(
							calculation,
							Amount.of(3, CurrencyCode.EUR)));

			//
			// Check generate service invoice
			{
				// Check service invoice header
				final POJOLookupMap db = POJOLookupMap.get();
				final List<I_C_Invoice> invoices = db.getRecords(I_C_Invoice.class);
				assertThat(invoices).hasSize(1);
				final I_C_Invoice serviceInvoice = invoices.get(0);
				assertThat(serviceInvoice.getC_Invoice_ID()).isEqualTo(serviceInvoiceId.getRepoId());
				assertThat(serviceInvoice.getRef_Invoice_ID()).isEqualTo(3);
				assertThat(serviceInvoice.getC_BPartner_ID()).isEqualTo(serviceCompanyBPartnerAndLocationId.getBpartnerId().getRepoId());
				assertThat(serviceInvoice.getC_BPartner_Location_ID()).isEqualTo(serviceCompanyBPartnerAndLocationId.getRepoId());
				assertThat(TimeUtil.asLocalDate(serviceInvoice.getDateInvoiced(), ZoneId.of("UTC-8"))).isEqualTo("2020-04-30");
				assertThat(serviceInvoice.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
				assertThat(serviceInvoice.getC_DocTypeTarget_ID()).isEqualTo(serviceInvoiceDocTypeId.getRepoId());
				assertThat(serviceInvoice.getM_PriceList_ID()).isEqualTo(servicePriceListId.getRepoId());
				assertThat(serviceInvoice.isTaxIncluded()).isTrue();

				// Check service invoice line
				final List<I_C_InvoiceLine> serviceInvoiceLines = db.getRecords(I_C_InvoiceLine.class);
				assertThat(serviceInvoiceLines).hasSize(1);
				final I_C_InvoiceLine serviceInvoiceLine = serviceInvoiceLines.get(0);
				assertThat(serviceInvoiceLine.getC_Invoice_ID()).isEqualTo(serviceInvoice.getC_Invoice_ID());
				assertThat(serviceInvoiceLine.getM_Product_ID()).isEqualTo(serviceFeeProductId.getRepoId());
				assertThat(serviceInvoiceLine.isManualPrice()).isTrue();
				assertThat(serviceInvoiceLine.getPriceActual()).isEqualByComparingTo("3");
				assertThat(serviceInvoiceLine.getQtyEntered()).isEqualByComparingTo("1");
				assertThat(serviceInvoiceLine.getQtyInvoiced()).isEqualByComparingTo("1");
				// assertThat(serviceInvoiceLine.getLineNetAmt()).isEqualByComparingTo("3");
				// assertThat(serviceInvoiceLine.getC_Tax_ID()).isGreaterThan(0);
			}
		}
	}

	@Builder(builderMethodName = "config", builderClassName = "ConfigBuilder")
	private void createConfig(
			@NonNull final String feePercentageOfGrandTotal,
			@NonNull @Singular final Set<BPartnerId> customerIds,
			@NonNull final ZonedDateTime validFrom)
	{
		Check.assumeNotEmpty(customerIds, "customerIds is not empty");

		final I_InvoiceProcessingServiceCompany configRecord = newInstance(I_InvoiceProcessingServiceCompany.class);
		configRecord.setIsActive(true);
		configRecord.setServiceCompany_BPartner_ID(serviceCompanyBPartnerId.getRepoId());
		configRecord.setServiceInvoice_DocType_ID(serviceInvoiceDocTypeId.getRepoId());
		configRecord.setServiceFee_Product_ID(serviceFeeProductId.getRepoId());
		configRecord.setValidFrom(TimeUtil.asTimestamp(validFrom));
		saveRecord(configRecord);

		for (final BPartnerId customerId : customerIds)
		{
			final I_InvoiceProcessingServiceCompany_BPartnerAssignment assignmentRecord = newInstance(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class);
			assignmentRecord.setIsActive(true);
			assignmentRecord.setInvoiceProcessingServiceCompany_ID(configRecord.getInvoiceProcessingServiceCompany_ID());
			assignmentRecord.setC_BPartner_ID(customerId.getRepoId());
			assignmentRecord.setFeePercentageOfGrandTotal(new BigDecimal(feePercentageOfGrandTotal));
			saveRecord(assignmentRecord);

		}
	}
}
