package de.metas.rest_api.invoicecandidates.impl;

import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v1.JsonSOTrx;
import de.metas.invoicecandidate.externallyreferenced.ExternallyReferencedCandidateRepository;
import de.metas.invoicecandidate.externallyreferenced.ManualCandidateService;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.pricing.service.impl.PricingTestHelper;
import de.metas.pricing.service.impl.ProductPriceBuilder;
import de.metas.rest_api.invoicecandidates.request.JsonCreateInvoiceCandidatesRequest;
import de.metas.rest_api.invoicecandidates.request.JsonCreateInvoiceCandidatesRequestItem;
import de.metas.rest_api.invoicecandidates.response.JsonCreateInvoiceCandidatesResponse;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.DocTypeService;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.user.UserRepository;
import de.metas.util.JSONObjectMapper;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidEntityException;
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
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_AD_OrgInfo;
import org.compiere.model.X_C_Tax;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static java.math.BigDecimal.TEN;
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

@ExtendWith(AdempiereTestWatcher.class)
class JsonInsertInvoiceCandidateServiceTest
{

	private static final String PRODUCT_VALUE = "product-123";
	private static final String BILL_PARTNER_VALUE = "billPartner-123";
	private static final String ORG_VALUE = "orgCode";
	private CreateInvoiceCandidatesService jsonInsertInvoiceCandidateService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final BPartnerBL partnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, partnerBL); // needed in case a ProductNotOnPriceListException shluld be thrown

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

		final I_C_Tax taxRecord = newInstance(I_C_Tax.class);
		taxRecord.setC_TaxCategory_ID(pricingTestHelper.getTaxCategoryId().getRepoId());
		taxRecord.setC_Country_ID(pricingTestHelper.getDefaultPriceList().getC_Country_ID());
		taxRecord.setTo_Country_ID(pricingTestHelper.getDefaultPriceList().getC_Country_ID());
		taxRecord.setTypeOfDestCountry(X_C_Tax.TYPEOFDESTCOUNTRY_Domestic);
		taxRecord.setSOPOType(X_C_Tax.SOPOTYPE_Both);
		taxRecord.setValidFrom(TimeUtil.parseTimestamp("2019-01-01"));
		saveRecord(taxRecord);

		final BPartnerCompositeRepository bpartnerCompositeRepository = new BPartnerCompositeRepository(partnerBL, new MockLogEntriesRepository(), new UserRoleRepository());
		jsonInsertInvoiceCandidateService = new CreateInvoiceCandidatesService(
				new BPartnerQueryService(),
				bpartnerCompositeRepository,
				new DocTypeService(),
				new CurrencyService(),
				new ManualCandidateService(bpartnerCompositeRepository),
				new ExternallyReferencedCandidateRepository());

		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
	}

	@Test
	void createInvoiceCandidates()
	{
		createInvoiceCandidates_performTest();
	}

	private void createInvoiceCandidates_performTest()
	{
		final JsonCreateInvoiceCandidatesRequest request = createMinimalRequest();
		JSONObjectMapper.forClass(JsonCreateInvoiceCandidatesRequest.class).writeValueAsString(request);

		// invoke the method under test
		final JsonCreateInvoiceCandidatesResponse result = jsonInsertInvoiceCandidateService.createInvoiceCandidates(request);

		assertThat(result.getResponseItems())
				.extracting("externalHeaderId.value", "externalLineId.value")
				.containsExactly(tuple("externalHeaderId", "externalLineId"));
		final MetasfreshId resultMetasfreshId = result.getResponseItems().get(0).getMetasfreshId();

		final List<I_C_Invoice_Candidate> records = POJOLookupMap.get().getRecords(I_C_Invoice_Candidate.class);
		assertThat(records)
				.hasSize(1)
				.extracting("C_Invoice_Candidate_ID", "ExternalHeaderId", "ExternalLineId")
				.containsExactly(tuple(resultMetasfreshId.getValue(), "externalHeaderId", "externalLineId"));
	}

	private JsonCreateInvoiceCandidatesRequest createMinimalRequest()
	{
		final JsonCreateInvoiceCandidatesRequestItem minimalItem = JsonCreateInvoiceCandidatesRequestItem.builder()
				.billPartnerIdentifier("val-" + BILL_PARTNER_VALUE)
				.externalHeaderId(JsonExternalId.of("externalHeaderId"))
				.externalLineId(JsonExternalId.of("externalLineId"))
				.orgCode(ORG_VALUE)
				.productIdentifier("val-" + PRODUCT_VALUE)
				.qtyOrdered(TEN)
				.soTrx(JsonSOTrx.SALES)
				.build();
		final JsonCreateInvoiceCandidatesRequest request = JsonCreateInvoiceCandidatesRequest.builder()
				.item(minimalItem)
				.build();
		return request;
	}

	@Test
	void createInvoiceCandidates_failIfExists()
	{
		createInvoiceCandidates_performTest();

		// invoke the method under test a second time with the same request
		assertThatThrownBy(() -> jsonInsertInvoiceCandidateService.createInvoiceCandidates(createMinimalRequest()))
				.isInstanceOf(InvalidEntityException.class);

	}
}
