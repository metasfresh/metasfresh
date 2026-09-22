/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.rest_api.v2.invoicecandidates.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v1.JsonSOTrx;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.invoicecandidate.externallyreferenced.ExternallyReferencedCandidateRepository;
import de.metas.invoicecandidate.externallyreferenced.ManualCandidateService;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.pricing.service.impl.PricingTestHelper;
import de.metas.pricing.service.impl.ProductPriceBuilder;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.rest_api.invoicecandidates.response.JsonCreateInvoiceCandidatesResponse;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.DocTypeService;
import de.metas.rest_api.v2.bpartner.BPartnerMasterdataProvider;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.invoicecandidates.request.JsonCreateInvoiceCandidatesRequest;
import de.metas.rest_api.v2.invoicecandidates.request.JsonCreateInvoiceCandidatesRequestItem;
import de.metas.rest_api.v2.invoicecandidates.request.JsonTaxOverride;
import de.metas.rest_api.v2.ordercandidates.impl.MasterdataProvider;
import de.metas.security.permissions2.PermissionService;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingPropertyException;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_AD_OrgInfo;
import org.compiere.model.X_C_Tax;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Covers the {@code taxOverride} property of the <b>v2</b> {@code POST /api/v2/invoices/createCandidates} endpoint,
 * i.e. that the requested rate + tax category end up in {@code C_Invoice_Candidate.C_Tax_Override_ID}.
 */
@ExtendWith(AdempiereTestWatcher.class)
class CreateInvoiceCandidatesServiceTest
{
	private static final String PRODUCT_VALUE = "product-123";
	private static final String BILL_PARTNER_VALUE = "billPartner-123";
	private static final String ORG_VALUE = "orgCode";
	private static final String PAYMENT_TERM_VALUE = "term";

	private static final String OVERRIDE_TAX_CATEGORY_INTERNAL_NAME = "Transport";
	private static final String OVERRIDE_TAX_CATEGORY_IDENTIFIER = "int-" + OVERRIDE_TAX_CATEGORY_INTERNAL_NAME;
	private static final String OVERRIDE_TAX_CATEGORY_NAME = "Transport tax category";
	private static final BigDecimal OVERRIDE_TAX_RATE = new BigDecimal("19");
	private static final BigDecimal UNMATCHED_TAX_RATE = new BigDecimal("99.99");

	private CreateInvoiceCandidatesService createInvoiceCandidatesService;
	private MasterdataProvider masterdataProvider;

	private int overrideTaxId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final BPartnerBL partnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, partnerBL); // needed in case a ProductNotOnPriceListException should be thrown

		final I_C_ILCandHandler manualICHandler = newInstance(I_C_ILCandHandler.class);
		manualICHandler.setClassname(ManualCandidateHandler.class.getName());
		saveRecord(manualICHandler);

		final I_AD_Org orgRecord = newInstance(I_AD_Org.class);
		orgRecord.setValue(ORG_VALUE);
		saveRecord(orgRecord);

		final I_AD_OrgInfo orgInfoRecord = newInstance(I_AD_OrgInfo.class);
		orgInfoRecord.setAD_Org_ID(orgRecord.getAD_Org_ID());
		orgInfoRecord.setStoreCreditCardData(X_AD_OrgInfo.STORECREDITCARDDATA_NichtSpeichern);
		saveRecord(orgInfoRecord);

		final I_M_Product_Category productCategoryRecord = newInstance(I_M_Product_Category.class);
		saveRecord(productCategoryRecord);

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setAD_Org_ID(orgRecord.getAD_Org_ID());
		productRecord.setValue(PRODUCT_VALUE);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		productRecord.setM_Product_Category_ID(productCategoryRecord.getM_Product_Category_ID());
		saveRecord(productRecord);

		final PricingTestHelper pricingTestHelper = new PricingTestHelper();

		new ProductPriceBuilder(
				pricingTestHelper.getDefaultPriceListVerion(),
				productRecord)
				.setTaxCategoryId(pricingTestHelper.getTaxCategoryId())
				.setPrice(20)
				.build();

		final I_C_BP_Group bpGroupRecord = newInstance(I_C_BP_Group.class);
		bpGroupRecord.setM_PricingSystem_ID(pricingTestHelper.getDefaultPricingSystem().getM_PricingSystem_ID());
		saveRecord(bpGroupRecord);

		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setAD_Org_ID(orgRecord.getAD_Org_ID());
		bpartnerRecord.setValue(BILL_PARTNER_VALUE);
		bpartnerRecord.setC_BP_Group_ID(bpGroupRecord.getC_BP_Group_ID());
		saveRecord(bpartnerRecord);

		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setC_Country_ID(pricingTestHelper.getDefaultPriceList().getC_Country_ID());
		saveRecord(locationRecord);

		final I_C_BPartner_Location bpartnerLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		bpartnerLocationRecord.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		bpartnerLocationRecord.setIsBillTo(true);
		saveRecord(bpartnerLocationRecord);

		final int countryId = pricingTestHelper.getDefaultPriceList().getC_Country_ID();

		// the tax that the "regular" pricing-driven tax resolution finds
		createTaxRecord("tax", pricingTestHelper.getTaxCategoryId().getRepoId(), countryId, BigDecimal.ZERO);

		// the tax that the taxOverride is supposed to resolve to
		final I_C_TaxCategory overrideTaxCategoryRecord = newInstance(I_C_TaxCategory.class);
		overrideTaxCategoryRecord.setName(OVERRIDE_TAX_CATEGORY_NAME);
		overrideTaxCategoryRecord.setInternalName(OVERRIDE_TAX_CATEGORY_INTERNAL_NAME);
		saveRecord(overrideTaxCategoryRecord);

		overrideTaxId = createTaxRecord(
				"overrideTax",
				overrideTaxCategoryRecord.getC_TaxCategory_ID(),
				countryId,
				OVERRIDE_TAX_RATE);

		final I_C_PaymentTerm paymentTermRecord = newInstance(I_C_PaymentTerm.class);
		paymentTermRecord.setValue(PAYMENT_TERM_VALUE);
		saveRecord(paymentTermRecord);

		final BPartnerCompositeRepository bpartnerCompositeRepository = new BPartnerCompositeRepository(partnerBL, new MockLogEntriesRepository(), new UserRoleRepository(), new BPartnerCreditLimitRepository());
		createInvoiceCandidatesService = new CreateInvoiceCandidatesService(
				new BPartnerQueryService(),
				bpartnerCompositeRepository,
				new DocTypeService(),
				new CurrencyService(),
				new ExternallyReferencedCandidateRepository(),
				new ManualCandidateService(bpartnerCompositeRepository));

		final BPartnerMasterdataProvider bPartnerMasterdataProvider = Mockito.mock(BPartnerMasterdataProvider.class);
		Mockito.when(bPartnerMasterdataProvider.resolveBPartnerExternalIdentifier(Mockito.any(), Mockito.any()))
				.thenReturn(Optional.of(BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID())));

		masterdataProvider = MasterdataProvider.builder()
				.permissionService(Mockito.mock(PermissionService.class))
				.bpartnerRestController(Mockito.mock(BpartnerRestController.class))
				.bPartnerMasterdataProvider(bPartnerMasterdataProvider)
				.externalReferenceRestControllerService(Mockito.mock(ExternalReferenceRestControllerService.class))
				.jsonRetrieverService(Mockito.mock(JsonRetrieverService.class))
				.externalSystemRepository(Mockito.mock(ExternalSystemRepository.class))
				.build();

		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
	}

	private static int createTaxRecord(
			final String name,
			final int taxCategoryId,
			final int countryId,
			final BigDecimal rate)
	{
		final I_C_Tax taxRecord = newInstance(I_C_Tax.class);
		taxRecord.setName(name);
		taxRecord.setC_TaxCategory_ID(taxCategoryId);
		taxRecord.setC_Country_ID(countryId);
		taxRecord.setTo_Country_ID(countryId);
		taxRecord.setTypeOfDestCountry(X_C_Tax.TYPEOFDESTCOUNTRY_Domestic);
		taxRecord.setSOPOType(X_C_Tax.SOPOTYPE_Both);
		taxRecord.setValidFrom(TimeUtil.parseTimestamp("2019-01-01"));
		taxRecord.setRate(rate);
		saveRecord(taxRecord);
		return taxRecord.getC_Tax_ID();
	}

	private static JsonCreateInvoiceCandidatesRequest createRequest(@Nullable final JsonTaxOverride taxOverride)
	{
		final JsonCreateInvoiceCandidatesRequestItem item = JsonCreateInvoiceCandidatesRequestItem.builder()
				.billPartnerIdentifier("val-" + BILL_PARTNER_VALUE)
				.externalHeaderId(JsonExternalId.of("externalHeaderId"))
				.externalLineId(JsonExternalId.of("externalLineId"))
				.orgCode(ORG_VALUE)
				.productIdentifier("val-" + PRODUCT_VALUE)
				.qtyOrdered(TEN)
				.soTrx(JsonSOTrx.SALES)
				.paymentTerm("val-" + PAYMENT_TERM_VALUE)
				.taxOverride(taxOverride)
				.build();

		return JsonCreateInvoiceCandidatesRequest.builder()
				.item(item)
				.build();
	}

	private static I_C_Invoice_Candidate getSingleInvoiceCandidateRecord()
	{
		final List<I_C_Invoice_Candidate> records = POJOLookupMap.get().getRecords(I_C_Invoice_Candidate.class);
		assertThat(records).hasSize(1);
		return records.get(0);
	}

	@Nested
	class TaxOverride
	{
		@Test
		void resolvable_setsTaxOverrideId()
		{
			final JsonCreateInvoiceCandidatesRequest request = createRequest(JsonTaxOverride.builder()
					.rate(OVERRIDE_TAX_RATE)
					.taxCategoryIdentifier(OVERRIDE_TAX_CATEGORY_IDENTIFIER)
					.build());

			final JsonCreateInvoiceCandidatesResponse result = createInvoiceCandidatesService.createInvoiceCandidates(request, masterdataProvider);

			assertThat(result.getResponseItems()).hasSize(1);
			assertThat(getSingleInvoiceCandidateRecord().getC_Tax_Override_ID()).isEqualTo(overrideTaxId);
		}

		/** Rate matching is scale-insensitive, i.e. {@code 19.00} matches the {@code C_Tax} whose rate is {@code 19}. */
		@Test
		void rateWithDifferentScale_setsTaxOverrideId()
		{
			final JsonCreateInvoiceCandidatesRequest request = createRequest(JsonTaxOverride.builder()
					.rate(new BigDecimal("19.00"))
					.taxCategoryIdentifier(OVERRIDE_TAX_CATEGORY_IDENTIFIER)
					.build());

			final JsonCreateInvoiceCandidatesResponse result = createInvoiceCandidatesService.createInvoiceCandidates(request, masterdataProvider);

			assertThat(result.getResponseItems()).hasSize(1);
			assertThat(getSingleInvoiceCandidateRecord().getC_Tax_Override_ID()).isEqualTo(overrideTaxId);
		}

		@Test
		void missingRate_throwsMissingProperty()
		{
			final JsonCreateInvoiceCandidatesRequest request = createRequest(JsonTaxOverride.builder()
					.rate(null)
					.taxCategoryIdentifier(OVERRIDE_TAX_CATEGORY_IDENTIFIER)
					.build());

			assertThatThrownBy(() -> createInvoiceCandidatesService.createInvoiceCandidates(request, masterdataProvider))
					.isInstanceOf(MissingPropertyException.class)
					.hasMessageContaining("taxOverride.rate");
		}

		@Test
		void missingCategory_throwsMissingProperty()
		{
			final JsonCreateInvoiceCandidatesRequest request = createRequest(JsonTaxOverride.builder()
					.rate(OVERRIDE_TAX_RATE)
					.taxCategoryIdentifier(null)
					.build());

			assertThatThrownBy(() -> createInvoiceCandidatesService.createInvoiceCandidates(request, masterdataProvider))
					.isInstanceOf(MissingPropertyException.class)
					.hasMessageContaining("taxOverride.taxCategoryIdentifier");
		}

		@Test
		void noMatch_throwsNamingRateAndCategory()
		{
			final JsonCreateInvoiceCandidatesRequest request = createRequest(JsonTaxOverride.builder()
					.rate(UNMATCHED_TAX_RATE)
					.taxCategoryIdentifier(OVERRIDE_TAX_CATEGORY_IDENTIFIER)
					.build());

			assertThatThrownBy(() -> createInvoiceCandidatesService.createInvoiceCandidates(request, masterdataProvider))
					.isInstanceOf(TaxNotFoundException.class)
					.hasMessageContaining("Rate: " + UNMATCHED_TAX_RATE.toPlainString())
					.hasMessageContaining(OVERRIDE_TAX_CATEGORY_IDENTIFIER) // the identifier the caller sent
					.hasMessageContaining(OVERRIDE_TAX_CATEGORY_NAME); // the category it resolved to
		}
	}

	@Test
	void noTaxOverride_leavesOverrideUnset()
	{
		final JsonCreateInvoiceCandidatesRequest request = createRequest(null);

		final JsonCreateInvoiceCandidatesResponse result = createInvoiceCandidatesService.createInvoiceCandidates(request, masterdataProvider);

		assertThat(result.getResponseItems()).hasSize(1);
		final I_C_Invoice_Candidate record = getSingleInvoiceCandidateRecord();
		// assert that the override is *not set*, not how a given layer encodes "not set": the in-memory POJOWrapper
		// keeps the raw int (-1), while production POWrapper.checkZeroIdValue collapses any *_ID below 1 to SQL NULL,
		// which reads back as 0.
		assertThat(record.getC_Tax_Override_ID()).isLessThanOrEqualTo(0);
		assertThat(TaxId.ofRepoIdOrNull(record.getC_Tax_Override_ID())).isNull();
	}
}
