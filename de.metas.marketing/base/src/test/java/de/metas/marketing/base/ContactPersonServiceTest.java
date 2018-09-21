package de.metas.marketing.base;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.user.User;
import org.adempiere.user.UserRepository;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.service.BPartnerLocationRepository;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_Platform;
import de.metas.marketing.base.model.PlatformId;
import de.metas.util.Services;

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

public class ContactPersonServiceTest
{
	private UserRepository userRepository;
	private ContactPersonService contactPersonService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		userRepository = new UserRepository();
		contactPersonService = new ContactPersonService(
				new ContactPersonRepository(
						new BPartnerLocationRepository()));
	}

	@Test
	public void updateContactPersonsEmailFromUser_SameOldAddress()
	{
		final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
		final User user1 = createUser("name1", newUserAddress);

		final PlatformId platformId = createPlatformId();

		final String oldEmailAddress = "Oldtestmail@Oldtestmail.Oldtestmail";

		createContactPerson(user1, oldEmailAddress, platformId);

		contactPersonService.updateContactPersonsEmailFromUser(user1, oldEmailAddress);

		final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

		assertTrue(resultContactPersons.size() == 1);
		final String resultAddress = resultContactPersons.get(0).getEMail();

		assertSame(newUserAddress, resultAddress);
	}

	@Test
	public void updateContactPersonsEmailFromUser_DifferentOldAddress()
	{
		final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
		final User user1 = createUser("name1", newUserAddress);

		final PlatformId platformId = createPlatformId();

		final String contactPersonAddress = "Anothertestmail@Anothertestmail.Anothertestmail";

		createContactPerson(user1, contactPersonAddress, platformId);

		final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
		contactPersonService.updateContactPersonsEmailFromUser(user1, oldUserAddress);

		final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

		assertTrue(resultContactPersons.size() == 1);
		final String resultAddress = resultContactPersons.get(0).getEMail();
		assertNotSame(newUserAddress, resultAddress);
		assertSame(contactPersonAddress, resultAddress);
	}

	@Test
	public void updateContactPersonsEmailFromUser_EmptyOldAddress()
	{
		final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
		final User user1 = createUser("name1", newUserAddress);

		final PlatformId platformId = createPlatformId();

		final String contactPersonAddress = "";

		createContactPerson(user1, contactPersonAddress, platformId);

		final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
		contactPersonService.updateContactPersonsEmailFromUser(user1, oldUserAddress);

		final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

		assertTrue(resultContactPersons.size() == 1);
		final String resultAddress = resultContactPersons.get(0).getEMail();

		assertSame(newUserAddress, resultAddress);
	}

	@Test
	public void updateContactPersonsEmailFromUser_NullOldAddress()
	{
		final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
		final User user1 = createUser("name1", newUserAddress);

		final PlatformId platformId = createPlatformId();

		final String contactPersonAddress = null;
		createContactPerson(user1, contactPersonAddress, platformId);

		final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
		contactPersonService.updateContactPersonsEmailFromUser(user1, oldUserAddress);

		final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

		assertTrue(resultContactPersons.size() == 1);
		final String resultAddress = resultContactPersons.get(0).getEMail();

		assertSame(newUserAddress, resultAddress);
	}

	private User createUser(final String name, final String mail)
	{
		final User user = User.builder()
				.name(name)
				.emailAddress(mail)
				.build();
		return userRepository.save(user);

	}

	private void createContactPerson(final User user, final String emailAddress, final PlatformId platformId)
	{

		final I_MKTG_ContactPerson contactPerson = newInstance(I_MKTG_ContactPerson.class);

		contactPerson.setAD_User_ID(user.getId().getRepoId());
		contactPerson.setEMail(emailAddress);
		contactPerson.setMKTG_Platform_ID(platformId.getRepoId());

		save(contactPerson);

	}

	private PlatformId createPlatformId()
	{
		final I_MKTG_Platform platform = newInstance(I_MKTG_Platform.class);

		save(platform);
		return PlatformId.ofRepoId(platform.getMKTG_Platform_ID());

	}

	private List<I_MKTG_ContactPerson> retrieveContactPersons()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

}
