package de.metas.marketing.base.misc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import de.metas.StartupListener;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_AD_User;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_Consent;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;

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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class,
		
		Tools.class,
		CampaignRepository.class,
		
		ContactPersonRepository.class})
public class ToolsTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void addToNewsletter_noCampaign()
	{
		final Tools converters = Adempiere.getBean(Tools.class);
		
		final I_AD_User user = createUser("User1", "mail@mail.mail");

		final I_MKTG_Campaign campaign = createCampaign();
		
		converters.addToNewsletter(user, campaign.getMKTG_Campaign_ID());
		
		final I_MKTG_Consent consentRecord = getConsentRecord();
		final I_MKTG_Campaign_ContactPerson contactPerson = getContactPerson();
		List<I_MKTG_ContactPerson> contactPersons = getContactPersons();
		
		assertNotNull(consentRecord);
		assertNotNull(contactPerson);
		assertTrue(contactPersons.size() == 1);
		
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
	
	private List<I_MKTG_ContactPerson> getContactPersons()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_MKTG_ContactPerson.class);
	}

	private I_AD_User createUser(final String name, final String mail)
	{
		final I_AD_User user = newInstance(I_AD_User.class);

		user.setName(name);
		user.setEMail(mail);

		save(user);

		return user;
	}

	private I_MKTG_Campaign createCampaign()
	{
		final I_MKTG_Campaign campaign = newInstance(I_MKTG_Campaign.class);

		save(campaign);

		return campaign;
	}
}
