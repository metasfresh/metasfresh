/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.composite.repository;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.creditLimit.BPartnerCreditLimit;
import de.metas.bpartner.creditLimit.CreditLimitTypeId;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.ICurrencyBL;
import de.metas.greeting.GreetingId;
import de.metas.greeting.GreetingRepository;
import de.metas.i18n.Language;
import de.metas.location.CountryId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationCreateRequest;
import de.metas.location.LocationId;
import de.metas.marketing.base.model.CampaignId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

class BPartnerCompositeRepositoryTest
{
	private BPartnerCompositeRepository bpartnerCompositeRepository;
	private ICurrencyBL currencyBL;
	private CountryId countryId_DE;
	private OrgId orgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		orgId = AdempiereTestHelper.createOrgWithTimeZone("defaultOrg");

		SpringContextHolder.registerJUnitBean(new GreetingRepository());

		bpartnerCompositeRepository = new BPartnerCompositeRepository(
				new BPartnerBL(new UserRepository()),
				new MockLogEntriesRepository(),
				new UserRoleRepository(),
				new BPartnerCreditLimitRepository());

		currencyBL = Services.get(ICurrencyBL.class);

		BusinessTestHelper.createStandardBPGroup();
		countryId_DE = BusinessTestHelper.createCountry("DE");
	}

	@Test
	void save_and_load_standardCase()
	{
		//dev-note: the ICurrencyBL returned it's a PlainCurrencyBL so the ClientId.METASFRESH, OrgId.MAIN sent as param don't matter
		final CurrencyId currencyId = currencyBL.getBaseCurrencyId(ClientId.METASFRESH, OrgId.MAIN);

		final BPartnerComposite bpartnerComposite = BPartnerComposite.builder()
				.orgId(orgId)
				.bpartner(BPartner.builder()
						.value("12345")
						.name("name1")
						.name2("name2")
						.name3("name3")
						.company(false)
						//.companyName("companyName")
						.greetingId(GreetingId.ofRepoId(1))
						.groupId(BPGroupId.STANDARD)
						.language(Language.asLanguage("de_DE"))
						.customer(true)
						.customerPaymentTermId(PaymentTermId.ofRepoId(11))
						.customerPricingSystemId(PricingSystemId.ofRepoId(12))
						.vendor(true)
						.vendorPaymentTermId(PaymentTermId.ofRepoId(21))
						.vendorPricingSystemId(PricingSystemId.ofRepoId(22))
						.excludeFromPromotions(true)
						.referrer("test referrer")
						.campaignId(CampaignId.ofRepoId(1111))
						.build())
				.location(BPartnerLocation.builder()
						.locationType(BPartnerLocationType.builder()
								.shipToDefault(true)
								.shipTo(true)
								.billToDefault(true)
								.billTo(true)
								.build())
						.gln(GLN.ofString("GLN"))
						.externalId(ExternalId.of("externalId"))
						.name("location name")
						.bpartnerName("bpartnerName")
						.address1("address1")
						.address2("address2")
						.address3("address3")
						.address4("address4")
						.postal("postal")
						.city("city")
						.region("region")
						//.district("district")
						.countryCode("DE")
						.poBox("poBox")
						.phone("phone")
						.email("email")
						.build())
				.contact(BPartnerContact.builder()
						// @Nullable final BPartnerContactId id,
						.externalId(ExternalId.of("externalId"))
						.value(null) // to be generated
						// .active(true) // true by default
						.name("name")
						.firstName("firstName")
						.lastName("lastName")
						.email("email")
						.newsletter(true)
						.invoiceEmailEnabled(true)
						.fax("fax")
						.mobilePhone("mobilePhone")
						.description("description")
						.greetingId(GreetingId.ofRepoId(12345))
						.newsletter(true)
						.membershipContact(true)
						.subjectMatterContact(true)
						.contactType(BPartnerContactType.builder()
								.defaultContact(true)
								.billToDefault(true)
								.shipToDefault(true)
								.sales(true)
								.salesDefault(true)
								.purchase(true)
								.purchaseDefault(true)
								.build())
						.changeLog(null)
						.orgMappingId(null)
						.birthday(LocalDate.parse("1901-02-03"))
						.bPartnerLocationId(null)
						.roles(ImmutableList.of())
						.email2("email2")
						.email3("email3")
						.build())
				.creditLimit(BPartnerCreditLimit.builder()
									 .amount(Money.of(BigDecimal.valueOf(54.20), currencyId))
									 .active(true)
									 .creditLimitTypeId(CreditLimitTypeId.ofRepoId(123))
									 .dateFrom(Instant.now())
									 .build())
				.build();

		bpartnerCompositeRepository.save(bpartnerComposite, true);

		final BPartnerId bpartnerId = bpartnerComposite.getBpartner().getId();
		Assertions.assertThat(bpartnerId).isNotNull();

		final BPartnerComposite bpartnerCompositeLoaded = bpartnerCompositeRepository.getById(bpartnerId);
		//System.out.println("bpartnerCompositeLoaded: " + bpartnerCompositeLoaded);

		Assertions.assertThat(bpartnerCompositeLoaded)
				.usingRecursiveComparison()
				.ignoringFields(
						"bpartner.changeLog",
						"locations.changeLog",
						"locations.original",
						"contacts.changeLog",
						"creditLimits.changeLog",
						"creditLimits.bPartnerId")
				.isEqualTo(bpartnerComposite);
	}

	@Test
	void save_and_load_existingLocationId()
	{
		final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
		final LocationId existingLocationId = locationDAO.createOrReuseLocation(LocationCreateRequest.builder()
				.address1("address1")
				.address2("address2")
				.address3("address3")
				.address4("address4")
				.postal("postal")
				.city("city")
				.regionName("region")
				.countryId(countryId_DE)
				.poBox("poBox")
				.build());

		final BPartnerComposite bpartnerComposite = BPartnerComposite.builder()
				.orgId(orgId)
				.bpartner(BPartner.builder()
						.value("value")
						.name("name1")
						.groupId(BPGroupId.STANDARD)
						.build())
				.location(BPartnerLocation.builder()
						.existingLocationId(existingLocationId)
						.build())
				.build();

		bpartnerCompositeRepository.save(bpartnerComposite, true);

		final BPartnerId bpartnerId = bpartnerComposite.getBpartner().getId();
		Assertions.assertThat(bpartnerId).isNotNull();

		final BPartnerComposite bpartnerCompositeLoaded = bpartnerCompositeRepository.getById(bpartnerId);
		System.out.println("bpartnerCompositeLoaded: " + bpartnerCompositeLoaded);

		Assertions.assertThat(bpartnerCompositeLoaded)
				.usingRecursiveComparison()
				.ignoringFields(
						"bpartner.changeLog",
						"locations.changeLog",
						"locations.original")
				.isEqualTo(bpartnerComposite);
	}
}