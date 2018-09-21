package de.metas.marketing.base;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.user.UserRepository;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.service.BPartnerLocationRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_AD_User;
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

public class UserServiceTest
{
	private UserService userService;
	private ContactPersonRepository contactPersonRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		userService = new UserService(new UserRepository());
		contactPersonRepository = new ContactPersonRepository(new BPartnerLocationRepository());
	}

	@Test
	public void updateUserEmailFromContactPerson_SameOldEmail()
	{
		final String oldEmailAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
		final I_AD_User user1 = createUser("name1", oldEmailAddress);

		final PlatformId platformId = createPlatformId();
		final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
		ContactPerson contactPerson = createContactPerson(user1.getAD_User_ID(), newContactPersonAddress, platformId);

		userService.updateUserEmailFromContactPerson(contactPerson, oldEmailAddress);

		final List<I_AD_User> resultUsers = retrieveUsers();

		assertTrue(resultUsers.size() == 1);

		final String resultEmailAddress = resultUsers.get(0).getEMail();

		assertSame(newContactPersonAddress, resultEmailAddress);

	}

	@Test
	public void updateUserEmailFromContactPerson_DifferentOldEmail()
	{
		final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
		final I_AD_User user1 = createUser("name1", oldUserAddress);

		final PlatformId platformId = createPlatformId();
		final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
		ContactPerson contactPerson = createContactPerson(user1.getAD_User_ID(), newContactPersonAddress, platformId);

		final String anotherEmailAddress = "AnotheremailAddress@AnotherEmailAddress.AnotherEmailAddress";

		userService.updateUserEmailFromContactPerson(contactPerson, anotherEmailAddress);

		final List<I_AD_User> resultUsers = retrieveUsers();

		assertTrue(resultUsers.size() == 1);

		final String resultEmailAddress = resultUsers.get(0).getEMail();

		assertNotSame(newContactPersonAddress, resultEmailAddress);
		assertSame(oldUserAddress, resultEmailAddress);

	}

	@Test
	public void updateUserEmailFromContactPerson_NullOldEmail()
	{
		final String oldEmailAddress = null;
		final I_AD_User user1 = createUser("name1", oldEmailAddress);

		final PlatformId platformId = createPlatformId();
		final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
		ContactPerson contactPerson = createContactPerson(user1.getAD_User_ID(), newContactPersonAddress, platformId);

		userService.updateUserEmailFromContactPerson(contactPerson, oldEmailAddress);

		final List<I_AD_User> resultUsers = retrieveUsers();

		assertTrue(resultUsers.size() == 1);

		final String resultEmailAddress = resultUsers.get(0).getEMail();

		assertSame(newContactPersonAddress, resultEmailAddress);

	}

	@Test
	public void updateUserEmailFromContactPerson_EmptyOldEmail()
	{
		final String oldEmailAddress = "";
		final I_AD_User user1 = createUser("name1", oldEmailAddress);

		final PlatformId platformId = createPlatformId();
		final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
		ContactPerson contactPerson = createContactPerson(user1.getAD_User_ID(), newContactPersonAddress, platformId);

		userService.updateUserEmailFromContactPerson(contactPerson, oldEmailAddress);

		final List<I_AD_User> resultUsers = retrieveUsers();

		assertTrue(resultUsers.size() == 1);

		final String resultEmailAddress = resultUsers.get(0).getEMail();

		assertSame(newContactPersonAddress, resultEmailAddress);

	}

	private I_AD_User createUser(final String name, final String mail)
	{
		final I_AD_User user = newInstance(I_AD_User.class);

		user.setName(name);
		user.setEMail(mail);

		save(user);
		return user;
	}

	private ContactPerson createContactPerson(final int userId, final String emailAddress, final PlatformId platformId)
	{
		final I_MKTG_ContactPerson contactPersonRecord = newInstance(I_MKTG_ContactPerson.class);

		contactPersonRecord.setAD_User_ID(userId);
		contactPersonRecord.setEMail(emailAddress);
		contactPersonRecord.setMKTG_Platform_ID(platformId.getRepoId());

		save(contactPersonRecord);

		return contactPersonRepository.asContactPerson(contactPersonRecord);

	}

	private PlatformId createPlatformId()
	{
		final I_MKTG_Platform platform = newInstance(I_MKTG_Platform.class);

		save(platform);
		return PlatformId.ofRepoId(platform.getMKTG_Platform_ID());

	}

	private List<I_AD_User> retrieveUsers()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, 0) // the test helper is creating an extra user with id 0, so exclude it
				.create()
				.list();
	}

}
