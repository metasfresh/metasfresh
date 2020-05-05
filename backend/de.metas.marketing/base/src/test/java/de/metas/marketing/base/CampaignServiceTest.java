package de.metas.marketing.base;

import static de.metas.i18n.Language.AD_Language_en_AU;
import static de.metas.i18n.Language.asLanguage;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerLocationInfoRepository;
import de.metas.interfaces.I_C_BPartner;
import de.metas.marketing.base.bpartner.DefaultAddressType;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_Consent;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_Platform;
import de.metas.marketing.base.model.PlatformRepository;
import de.metas.user.User;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.util.time.FixedTimeSource;
import de.metas.util.time.SystemTime;

/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class CampaignServiceTest
{
	private CampaignService campaignService;
	private UserRepository userRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		userRepository = new UserRepository();
		campaignService = new CampaignService(
				new ContactPersonRepository(new BPartnerLocationInfoRepository()),
				new CampaignRepository(),
				new PlatformRepository());
	}

	@Test
	public void addToNewsletter()
	{
		final User user = createUser("User1", "mail@mail.mail");

		final I_MKTG_Platform platform = createPlatform();
		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		campaignService.addToCampaignIfHasEmailAddress(user, CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID()));

		final I_MKTG_Consent consentRecord = getConsentRecord();
		final I_MKTG_Campaign_ContactPerson contactPerson = getContactPerson();

		assertNotNull(consentRecord);
		assertNotNull(contactPerson);
	}

	@Test
	public void removeFromNewsletter_ExistingConsent()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 19, 4, 4));

		final User user = createUser("User1", "mail@mail.mail");

		final I_MKTG_Platform platform = createPlatform();
		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		campaignService.addToCampaignIfHasEmailAddress(user, CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID()));

		final I_MKTG_Consent consent = getConsentRecord();
		campaignService.removeUserFromCampaign(user, CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID()));

		refresh(consent);

		assertTrue(SystemTime.asTimestamp().equals(consent.getConsentRevokedOn()));

	}

	@Test
	public void removeFromNewsletter_NoConsent()
	{
		final User user = createUser("User1", "mail@mail.mail");

		final I_MKTG_Platform platform = createPlatform();
		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		campaignService.removeUserFromCampaign(user, CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID()));

		final I_MKTG_Consent consentRecord = getConsentRecord();
		final I_MKTG_Campaign_ContactPerson contactPerson = getContactPerson();

		assertNull(consentRecord);
		assertNull(contactPerson);
	}

	private I_MKTG_Consent getConsentRecord()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Consent.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_MKTG_Consent.class);
	}

	private I_MKTG_Campaign_ContactPerson getContactPerson()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_MKTG_Campaign_ContactPerson.class);
	}

	private User createUser(final String name, final String mail)
	{
		final User user = User.builder()
				.name(name)
				.emailAddress(mail)
				.language(asLanguage(AD_Language_en_AU))
				.build();
		return userRepository.save(user);
	}

	private I_MKTG_Campaign createCampaign(final I_MKTG_Platform platform)
	{
		final I_MKTG_Campaign campaign = newInstance(I_MKTG_Campaign.class);

		campaign.setMKTG_Platform_ID(platform.getMKTG_Platform_ID());
		save(campaign);

		return campaign;
	}

	private I_MKTG_Platform createPlatform()
	{
		final I_MKTG_Platform platform = newInstance(I_MKTG_Platform.class);

		save(platform);
		return platform;

	}

	@Test
	public void addAsContactPersonsToCampaign_NullDefaultAddressType_ExistingBill()
	{
		final I_C_BPartner partner1 = createBPartner("Partner1");
		final I_C_BPartner_Location bpLocation = createBPartnerLocation(partner1);
		bpLocation.setIsBillToDefault(true);
		save(bpLocation);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner1.getC_BPartner_ID());

		final Stream<User> users = Collections.singletonList(createUserForPartner("User1", "testmail@testmail.testmail", bpartnerId)).stream();

		final I_MKTG_Platform platform = createPlatform();
		platform.setIsRequiredLocation(true);
		save(platform);

		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID());

		final DefaultAddressType defaultAddressType = null;

		campaignService.addAsContactPersonsToCampaign(users, campaignId, defaultAddressType);

		final List<I_MKTG_ContactPerson> existingContactPersons = retrieveExistingContactPersons();
		assertTrue(existingContactPersons.size() == 1);
		assertTrue(existingContactPersons.get(0).getC_BPartner_Location_ID() == bpLocation.getC_BPartner_Location_ID());

	}

	@Test
	public void addAsContactPersonsToCampaign_NullDefaultAddressType_NonExistingBill_MandatoryLocation()
	{
		final I_C_BPartner partner1 = createBPartner("Partner1");
		createBPartnerLocation(partner1);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner1.getC_BPartner_ID());

		final Stream<User> users = Collections.singletonList(createUserForPartner("User1", "testmail@testmail.testmail", bpartnerId)).stream();

		final I_MKTG_Platform platform = createPlatform();
		platform.setIsRequiredLocation(true);
		save(platform);

		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID());

		final DefaultAddressType defaultAddressType = null;

		campaignService.addAsContactPersonsToCampaign(users, campaignId, defaultAddressType);

		final List<I_MKTG_ContactPerson> existingContactPersons = retrieveExistingContactPersons();
		assertTrue(existingContactPersons.isEmpty());
	}

	@Test
	public void addAsContactPersonsToCampaign_NullDefaultAddressType_NonExistingBill_NotMandatoryLocation()
	{
		final I_C_BPartner partner1 = createBPartner("Partner1");
		createBPartnerLocation(partner1);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner1.getC_BPartner_ID());

		final Stream<User> users = Collections.singletonList(createUserForPartner("User1", "testmail@testmail.testmail", bpartnerId)).stream();

		final I_MKTG_Platform platform = createPlatform();
		platform.setIsRequiredLocation(false);
		save(platform);

		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID());

		final DefaultAddressType defaultAddressType = null;

		campaignService.addAsContactPersonsToCampaign(users, campaignId, defaultAddressType);

		final List<I_MKTG_ContactPerson> existingContactPersons = retrieveExistingContactPersons();
		assertTrue(existingContactPersons.size() == 1);
	}

	@Test
	public void addAsContactPersonsToCampaign_BillToDefaultAddressType_Existing()
	{
		final I_C_BPartner partner1 = createBPartner("Partner1");
		final I_C_BPartner_Location bpLocation = createBPartnerLocation(partner1);
		bpLocation.setIsBillToDefault(true);
		save(bpLocation);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner1.getC_BPartner_ID());

		final Stream<User> users = Collections.singletonList(createUserForPartner("User1", "testmail@testmail.testmail", bpartnerId)).stream();

		final I_MKTG_Platform platform = createPlatform();
		platform.setIsRequiredLocation(true);
		save(platform);

		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID());

		final DefaultAddressType defaultAddressType = DefaultAddressType.BillToDefault;

		campaignService.addAsContactPersonsToCampaign(users, campaignId, defaultAddressType);

		final List<I_MKTG_ContactPerson> existingContactPersons = retrieveExistingContactPersons();
		assertTrue(existingContactPersons.size() == 1);
		assertTrue(existingContactPersons.get(0).getC_BPartner_Location_ID() == bpLocation.getC_BPartner_Location_ID());
	}

	@Test
	public void addAsContactPersonsToCampaign_BillToDefaultAddressType_NotExisting_MandatoryLocation()
	{
		final I_C_BPartner partner1 = createBPartner("Partner1");
		createBPartnerLocation(partner1);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner1.getC_BPartner_ID());

		final Stream<User> users = Collections.singletonList(createUserForPartner("User1", "testmail@testmail.testmail", bpartnerId)).stream();

		final I_MKTG_Platform platform = createPlatform();
		platform.setIsRequiredLocation(true);
		save(platform);

		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID());

		final DefaultAddressType defaultAddressType = DefaultAddressType.BillToDefault;

		campaignService.addAsContactPersonsToCampaign(users, campaignId, defaultAddressType);

		final List<I_MKTG_ContactPerson> existingContactPersons = retrieveExistingContactPersons();
		assertTrue(existingContactPersons.isEmpty());
	}

	@Test
	public void addAsContactPersonsToCampaign_BillToDefaultAddressType_NotExisting_NotMandatoryLocation()
	{
		final I_C_BPartner partner1 = createBPartner("Partner1");
		createBPartnerLocation(partner1);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner1.getC_BPartner_ID());

		final Stream<User> users = Collections.singletonList(createUserForPartner("User1", "testmail@testmail.testmail", bpartnerId)).stream();

		final I_MKTG_Platform platform = createPlatform();
		platform.setIsRequiredLocation(false);
		save(platform);

		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID());

		final DefaultAddressType defaultAddressType = DefaultAddressType.BillToDefault;

		campaignService.addAsContactPersonsToCampaign(users, campaignId, defaultAddressType);

		final List<I_MKTG_ContactPerson> existingContactPersons = retrieveExistingContactPersons();
		assertTrue(existingContactPersons.isEmpty());
	}

	@Test
	public void addAsContactPersonsToCampaign_ShipToDefaultAddressType_Existing()
	{
		final I_C_BPartner partner1 = createBPartner("Partner1");
		final I_C_BPartner_Location bpLocation = createBPartnerLocation(partner1);
		bpLocation.setIsShipToDefault(true);
		save(bpLocation);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner1.getC_BPartner_ID());

		final Stream<User> users = Collections.singletonList(createUserForPartner("User1", "testmail@testmail.testmail", bpartnerId)).stream();

		final I_MKTG_Platform platform = createPlatform();
		platform.setIsRequiredLocation(true);
		save(platform);

		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID());

		final DefaultAddressType defaultAddressType = DefaultAddressType.ShipToDefault;

		campaignService.addAsContactPersonsToCampaign(users, campaignId, defaultAddressType);

		final List<I_MKTG_ContactPerson> existingContactPersons = retrieveExistingContactPersons();
		assertTrue(existingContactPersons.size() == 1);
		assertTrue(existingContactPersons.get(0).getC_BPartner_Location_ID() == bpLocation.getC_BPartner_Location_ID());
	}

	@Test
	public void addAsContactPersonsToCampaign_ShipToDefaultAddressType_NotExisting_MandatoryLocation()
	{
		final I_C_BPartner partner1 = createBPartner("Partner1");
		createBPartnerLocation(partner1);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner1.getC_BPartner_ID());

		final Stream<User> users = Collections.singletonList(createUserForPartner("User1", "testmail@testmail.testmail", bpartnerId)).stream();

		final I_MKTG_Platform platform = createPlatform();
		platform.setIsRequiredLocation(true);
		save(platform);

		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID());

		final DefaultAddressType defaultAddressType = DefaultAddressType.ShipToDefault;

		campaignService.addAsContactPersonsToCampaign(users, campaignId, defaultAddressType);

		final List<I_MKTG_ContactPerson> existingContactPersons = retrieveExistingContactPersons();
		assertTrue(existingContactPersons.isEmpty());
	}

	@Test
	public void addAsContactPersonsToCampaign_ShipToDefaultAddressType_NotExisting_NotMandatoryLocation()
	{
		final I_C_BPartner partner1 = createBPartner("Partner1");
		createBPartnerLocation(partner1);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner1.getC_BPartner_ID());

		final Stream<User> users = Collections.singletonList(createUserForPartner("User1", "testmail@testmail.testmail", bpartnerId)).stream();

		final I_MKTG_Platform platform = createPlatform();
		platform.setIsRequiredLocation(false);
		save(platform);

		final I_MKTG_Campaign campaignRecord = createCampaign(platform);

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID());

		final DefaultAddressType defaultAddressType = DefaultAddressType.ShipToDefault;

		campaignService.addAsContactPersonsToCampaign(users, campaignId, defaultAddressType);

		final List<I_MKTG_ContactPerson> existingContactPersons = retrieveExistingContactPersons();
		assertTrue(existingContactPersons.isEmpty());
	}

	private I_C_BPartner createBPartner(final String name)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(name);

		save(partner);

		return partner;

	}

	private I_C_BPartner_Location createBPartnerLocation(final I_C_BPartner bpartner)
	{

		final I_C_Location location = createLocation();
		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);

		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setC_Location(location);

		save(bpLocation);

		return bpLocation;
	}

	private I_C_Location createLocation()
	{
		final I_C_Location location = newInstance(I_C_Location.class);

		save(location);

		return location;
	}

	private User createUserForPartner(final String name, final String mail, final BPartnerId bpartnerId)
	{
		final User user = User.builder()
				.name(name)
				.emailAddress(mail)
				.bpartnerId(bpartnerId)
				.language(asLanguage(AD_Language_en_AU))
				.build();
		return userRepository.save(user);
	}

	private List<I_MKTG_ContactPerson> retrieveExistingContactPersons()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_MKTG_ContactPerson.class);
	}
}
