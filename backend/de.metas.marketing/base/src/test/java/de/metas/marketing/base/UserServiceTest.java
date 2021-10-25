package de.metas.marketing.base;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.i18n.Language;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_Platform;
import de.metas.marketing.base.model.PlatformId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.List;

import static de.metas.i18n.Language.AD_Language_en_AU;
import static de.metas.i18n.Language.AD_Language_en_GB;
import static de.metas.i18n.Language.AD_Language_en_US;
import static de.metas.i18n.Language.asLanguage;
import static de.metas.i18n.Language.asLanguageStringOrNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
	private static final Language LANGUAGE_en_GB = asLanguage(AD_Language_en_GB);
	private static final Language LANGUAGE_en_AU = asLanguage(AD_Language_en_AU);
	private static final Language LANGUAGE_en_US = asLanguage(AD_Language_en_US);

	private UserService userService;
	private ContactPersonRepository contactPersonRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final UserRepository userRepository = new UserRepository();

		userService = new UserService(userRepository);
		contactPersonRepository = new ContactPersonRepository();

		Services.registerService(IBPartnerBL.class, new BPartnerBL(userRepository));
	}

	@Test
	public void updateUserEmailFromContactPerson_SameOldEmail()
	{
		final String oldEmailAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
		final I_AD_User user1 = createUserRecord("name1", oldEmailAddress);

		final PlatformId platformId = createPlatformId();
		final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
		ContactPerson contactPerson = createContactPerson(user1.getAD_User_ID(), newContactPersonAddress, platformId);

		userService.updateUserFromContactPersonIfFeasible(contactPerson, oldEmailAddress, null);

		final List<I_AD_User> resultUsers = retrieveUsers();

		assertTrue(resultUsers.size() == 1);

		final String resultEmailAddress = resultUsers.get(0).getEMail();

		assertSame(newContactPersonAddress, resultEmailAddress);
	}

	@Test
	public void updateUserEmailFromContactPerson_DifferentOldEmail()
	{
		final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
		final I_AD_User user1 = createUserRecord("name1", oldUserAddress);

		final PlatformId platformId = createPlatformId();
		final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
		ContactPerson contactPerson = createContactPerson(user1.getAD_User_ID(), newContactPersonAddress, platformId);

		final String anotherEmailAddress = "AnotheremailAddress@AnotherEmailAddress.AnotherEmailAddress";

		userService.updateUserFromContactPersonIfFeasible(contactPerson, anotherEmailAddress, null);

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
		final I_AD_User user1 = createUserRecord("name1", oldEmailAddress);

		final PlatformId platformId = createPlatformId();
		final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
		ContactPerson contactPerson = createContactPerson(user1.getAD_User_ID(), newContactPersonAddress, platformId);

		userService.updateUserFromContactPersonIfFeasible(contactPerson, oldEmailAddress, null);

		final List<I_AD_User> resultUsers = retrieveUsers();

		assertTrue(resultUsers.size() == 1);

		final String resultEmailAddress = resultUsers.get(0).getEMail();

		assertSame(newContactPersonAddress, resultEmailAddress);
	}

	@Test
	public void updateUserEmailFromContactPerson_EmptyOldEmail()
	{
		final String oldEmailAddress = "";
		final I_AD_User user1 = createUserRecord("name1", oldEmailAddress);

		final PlatformId platformId = createPlatformId();
		final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
		ContactPerson contactPerson = createContactPerson(user1.getAD_User_ID(), newContactPersonAddress, platformId);

		userService.updateUserFromContactPersonIfFeasible(contactPerson, oldEmailAddress, null);

		final List<I_AD_User> resultUsers = retrieveUsers();

		assertTrue(resultUsers.size() == 1);

		final String resultEmailAddress = resultUsers.get(0).getEMail();

		assertSame(newContactPersonAddress, resultEmailAddress);
	}

	private I_AD_User createUserRecord(
			@NonNull final String name,
			@Nullable final String mail)
	{
		return createUserRecord(name, mail, LANGUAGE_en_AU);
	}

	private ContactPerson createContactPerson(
			@Nullable final int userId,
			@Nullable final String emailAddress,
			@NonNull final PlatformId platformId)
	{
		return createContactPerson(userId, emailAddress, LANGUAGE_en_AU, platformId);
	}

	@Test
	public void updateUserEmailFromContactPerson_SameOldEmailAndLanguage()
	{
		final String oldEmailAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
		final Language oldLanguage = LANGUAGE_en_GB;

		final I_AD_User user1 = createUserRecord("name1", oldEmailAddress, oldLanguage);

		final PlatformId platformId = createPlatformId();
		final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
		final Language newLanguage = LANGUAGE_en_US;

		ContactPerson contactPerson = createContactPerson(user1.getAD_User_ID(), newContactPersonAddress, newLanguage, platformId);

		// invoke the method under test
		userService.updateUserFromContactPersonIfFeasible(contactPerson, oldEmailAddress, oldLanguage);

		final List<I_AD_User> resultRecords = retrieveUsers();

		assertThat(resultRecords).hasSize(1);
		assertThat(resultRecords.get(0).getEMail()).isEqualTo(newContactPersonAddress);
		assertThat(resultRecords.get(0).getAD_Language()).isEqualTo(newLanguage.getAD_Language());
	}

	private I_AD_User createUserRecord(
			@NonNull final String name,
			@Nullable final String mail,
			@Nullable final Language language)
	{
		final I_AD_User userRecord = newInstance(I_AD_User.class);

		userRecord.setName(name);
		userRecord.setEMail(mail);
		userRecord.setAD_Language(asLanguageStringOrNull(language));

		save(userRecord);
		return userRecord;
	}

	private ContactPerson createContactPerson(
			final int userId,
			@Nullable final String emailAddress,
			@Nullable final Language language,
			@NonNull final PlatformId platformId)
	{
		final I_MKTG_ContactPerson contactPersonRecord = newInstance(I_MKTG_ContactPerson.class);

		contactPersonRecord.setAD_User_ID(userId);
		contactPersonRecord.setEMail(emailAddress);
		contactPersonRecord.setAD_Language(asLanguageStringOrNull(language));
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
