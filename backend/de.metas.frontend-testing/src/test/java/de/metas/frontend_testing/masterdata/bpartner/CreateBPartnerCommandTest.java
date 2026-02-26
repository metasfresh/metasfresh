package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyRepository;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_PricingSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.frontend-testing
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

public class CreateBPartnerCommandTest
{
	private CurrencyRepository currencyRepository;
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		currencyRepository = new CurrencyRepository();
		context = new MasterdataContext();
	}

	@Test
	public void execute_withCustomerBPartner_shouldCreateCustomerRecord()
	{
		// given
		final JsonCreateBPartnerRequest request = JsonCreateBPartnerRequest.builder()
				.bpartnerCode("CUST_001")
				.name("Customer One")
				.isCustomer(true)
				.isVendor(false)
				.build();

		final CreateBPartnerCommand command = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier("customer1")
				.build();

		// when
		final JsonCreateBPartnerResponse response = command.execute();

		// then
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();
		assertThat(response.getBpartnerCode()).isEqualTo("CUST_001");

		// Verify database record
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(response.getId(), I_C_BPartner.class);
		assertThat(bpartner).isNotNull();
		assertThat(bpartner.getValue()).isEqualTo("CUST_001");
		assertThat(bpartner.getName()).isEqualTo("Customer One");
		assertThat(bpartner.isCustomer()).isTrue();
		assertThat(bpartner.isVendor()).isFalse();
		assertThat(bpartner.isCompany()).isTrue();
		assertThat(bpartner.getM_PricingSystem_ID()).isGreaterThan(0); // Sales pricing system set
	}

	@Test
	public void execute_withVendorBPartner_shouldCreateVendorRecord()
	{
		// given
		final JsonCreateBPartnerRequest request = JsonCreateBPartnerRequest.builder()
				.bpartnerCode("VEND_001")
				.name("Vendor One")
				.isCustomer(false)
				.isVendor(true)
				.build();

		final CreateBPartnerCommand command = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier("vendor1")
				.build();

		// when
		final JsonCreateBPartnerResponse response = command.execute();

		// then
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();

		// Verify database record
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(response.getId(), I_C_BPartner.class);
		assertThat(bpartner).isNotNull();
		assertThat(bpartner.getValue()).isEqualTo("VEND_001");
		assertThat(bpartner.getName()).isEqualTo("Vendor One");
		assertThat(bpartner.isCustomer()).isFalse();
		assertThat(bpartner.isVendor()).isTrue();
		assertThat(bpartner.getPO_PricingSystem_ID()).isGreaterThan(0); // Purchase pricing system set
	}

	@Test
	public void execute_withCustomerAndVendor_shouldCreateBothRoles()
	{
		// given
		final JsonCreateBPartnerRequest request = JsonCreateBPartnerRequest.builder()
				.bpartnerCode("BOTH_001")
				.name("Customer and Vendor")
				.isCustomer(true)
				.isVendor(true)
				.build();

		final CreateBPartnerCommand command = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier("both1")
				.build();

		// when
		final JsonCreateBPartnerResponse response = command.execute();

		// then
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(response.getId(), I_C_BPartner.class);
		assertThat(bpartner.isCustomer()).isTrue();
		assertThat(bpartner.isVendor()).isTrue();
		assertThat(bpartner.getM_PricingSystem_ID()).isGreaterThan(0); // Sales pricing system
		assertThat(bpartner.getPO_PricingSystem_ID()).isGreaterThan(0); // Purchase pricing system
	}

	@Test
	public void execute_withCustomName_shouldUseCustomName()
	{
		// given
		final JsonCreateBPartnerRequest request = JsonCreateBPartnerRequest.builder()
				.bpartnerCode("BP_002")
				.name("Custom Business Partner Name")
				.isCustomer(true)
				.isVendor(false)
				.build();

		final CreateBPartnerCommand command = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier("bp2")
				.build();

		// when
		final JsonCreateBPartnerResponse response = command.execute();

		// then
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(response.getId(), I_C_BPartner.class);
		assertThat(bpartner.getValue()).isEqualTo("BP_002");
		assertThat(bpartner.getName()).isEqualTo("Custom Business Partner Name");
		assertThat(bpartner.getCompanyName()).isEqualTo("Custom Business Partner Name");
	}

	@Test
	public void execute_withoutCustomName_shouldUseValueAsName()
	{
		// given
		final JsonCreateBPartnerRequest request = JsonCreateBPartnerRequest.builder()
				.bpartnerCode("BP_003")
				.isCustomer(true)
				.isVendor(false)
				.build();

		final CreateBPartnerCommand command = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier("bp3")
				.build();

		// when
		final JsonCreateBPartnerResponse response = command.execute();

		// then
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(response.getId(), I_C_BPartner.class);
		assertThat(bpartner.getValue()).isEqualTo("BP_003");
		assertThat(bpartner.getName()).isEqualTo("BP_003");
	}

	@Test
	public void execute_shouldCreateBPartnerLocation()
	{
		// given
		final JsonCreateBPartnerRequest request = JsonCreateBPartnerRequest.builder()
				.bpartnerCode("BP_LOC_001")
				.name("BP with Location")
				.isCustomer(true)
				.isVendor(false)
				.gln("123")
				.build();

		final CreateBPartnerCommand command = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier("bpLoc1")
				.build();

		// when
		final JsonCreateBPartnerResponse response = command.execute();

		// then
		assertThat(response.getBpartnerLocationId()).isNotNull();
		assertThat(response.getGln()).isNotNull();

		// Verify location record exists
		final I_C_BPartner_Location location = InterfaceWrapperHelper.load(
				response.getBpartnerLocationId(),
				I_C_BPartner_Location.class
		);
		assertThat(location).isNotNull();
		assertThat(location.getC_BPartner_ID()).isEqualTo(response.getId().getRepoId());
	}

	@Test
	public void execute_shouldStoreIdentifierInContext()
	{
		// given
		final JsonCreateBPartnerRequest request = JsonCreateBPartnerRequest.builder()
				.bpartnerCode("BP_CTX_001")
				.name("Context Test BP")
				.isCustomer(true)
				.isVendor(false)
				.build();

		final CreateBPartnerCommand command = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier("contextBP")
				.build();

		// when
		final JsonCreateBPartnerResponse response = command.execute();

		// then
		final BPartnerId bpartnerId = context.getId(Identifier.ofString("contextBP"), BPartnerId.class);
		assertThat(bpartnerId).isNotNull();
		assertThat(bpartnerId).isEqualTo(response.getId());
	}

	@Test
	public void execute_shouldCreatePricingSystem()
	{
		// given
		final JsonCreateBPartnerRequest request = JsonCreateBPartnerRequest.builder()
				.bpartnerCode("BP_PS_001")
				.name("BP with Pricing System")
				.isCustomer(true)
				.isVendor(false)
				.build();

		final CreateBPartnerCommand command = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier("bpPS1")
				.build();

		// when
		final JsonCreateBPartnerResponse response = command.execute();

		// then
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(response.getId(), I_C_BPartner.class);
		final int pricingSystemId = bpartner.getM_PricingSystem_ID();
		assertThat(pricingSystemId).isGreaterThan(0);

		// Verify pricing system exists
		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.load(
				pricingSystemId,
				I_M_PricingSystem.class
		);
		assertThat(pricingSystem).isNotNull();
	}

	@Test
	public void execute_withNullIdentifier_shouldGenerateUniqueBPartnerCode()
	{
		// given
		final JsonCreateBPartnerRequest request = JsonCreateBPartnerRequest.builder()
				.isCustomer(true)
				.isVendor(false)
				.build();

		final CreateBPartnerCommand command = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier(null)
				.build();

		// when
		final JsonCreateBPartnerResponse response = command.execute();

		// then
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();
		assertThat(response.getBpartnerCode()).isNotNull();
		assertThat(response.getBpartnerCode()).startsWith("BP");

		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(response.getId(), I_C_BPartner.class);
		assertThat(bpartner.getValue()).isNotNull();
	}

	@Test
	public void execute_multipleBPartners_shouldCreateUniqueRecords()
	{
		// given
		final JsonCreateBPartnerRequest request1 = JsonCreateBPartnerRequest.builder()
				.bpartnerCode("UNIQUE_001")
				.name("Unique BP 1")
				.isCustomer(true)
				.isVendor(false)
				.build();

		final JsonCreateBPartnerRequest request2 = JsonCreateBPartnerRequest.builder()
				.bpartnerCode("UNIQUE_002")
				.name("Unique BP 2")
				.isCustomer(true)
				.isVendor(false)
				.build();

		// when
		final JsonCreateBPartnerResponse response1 = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request1)
				.identifier("unique1")
				.build()
				.execute();

		final JsonCreateBPartnerResponse response2 = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request2)
				.identifier("unique2")
				.build()
				.execute();

		// then
		assertThat(response1.getId()).isNotEqualTo(response2.getId());
		assertThat(response1.getBpartnerCode()).isNotEqualTo(response2.getBpartnerCode());

		final I_C_BPartner bp1 = InterfaceWrapperHelper.load(response1.getId(), I_C_BPartner.class);
		final I_C_BPartner bp2 = InterfaceWrapperHelper.load(response2.getId(), I_C_BPartner.class);
		assertThat(bp1.getValue()).isEqualTo("UNIQUE_001");
		assertThat(bp2.getValue()).isEqualTo("UNIQUE_002");
	}
}
