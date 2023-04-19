package de.metas.marketing.base;

import de.metas.i18n.Language;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_Platform;
import de.metas.marketing.base.model.PlatformId;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static de.metas.i18n.Language.AD_Language_en_AU;
import static de.metas.i18n.Language.AD_Language_en_GB;
import static de.metas.i18n.Language.AD_Language_en_US;
import static de.metas.i18n.Language.asLanguage;
import static de.metas.i18n.Language.asLanguageStringOrNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

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
	private static final Language LANGUAGE_en_GB = Objects.requireNonNull(asLanguage(AD_Language_en_GB));
	private static final Language LANGUAGE_en_AU = Objects.requireNonNull(asLanguage(AD_Language_en_AU));
	private static final Language LANGUAGE_en_US = Objects.requireNonNull(asLanguage(AD_Language_en_US));

	private UserRepository userRepository;
	private ContactPersonService contactPersonService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		userRepository = new UserRepository();
		contactPersonService = new ContactPersonService(new ContactPersonRepository(), userRepository);
	}

	@SuppressWarnings("SameParameterValue")
	private User createUser(
			@NonNull final String name,
			@Nullable final String mail,
			@NonNull final Language language)
	{
		final User user = User.builder()
				.name(name)
				.emailAddress(mail)
				.language(language)
				.userLanguage(language)
				.build();
		return userRepository.save(user);
	}

	private ContactPerson createContactPersonRecord(
			@NonNull final UserId userId,
			@Nullable final String emailAddress,
			@Nullable final Language language,
			@NonNull final PlatformId platformId)
	{
		final I_MKTG_ContactPerson record = newInstance(I_MKTG_ContactPerson.class);

		record.setAD_User_ID(userId.getRepoId());
		record.setEMail(emailAddress);
		record.setAD_Language(asLanguageStringOrNull(language));
		record.setMKTG_Platform_ID(platformId.getRepoId());

		save(record);
		return ContactPersonRepository.toContactPerson(record);
	}

	private PlatformId createPlatformId()
	{
		final I_MKTG_Platform platform = newInstance(I_MKTG_Platform.class);

		save(platform);
		return PlatformId.ofRepoId(platform.getMKTG_Platform_ID());

	}

	@SuppressWarnings("unused")
	interface UserAndContactPersonSyncTests
	{
		void sameOldEmail();

		void differentOldEmail();

		void nullOldEmail();

		void emptyOldEmail();

		void sameOldEmailAndLanguage();
	}

	@Nested
	class updateContactPersonsEmailFromUser implements UserAndContactPersonSyncTests
	{
		private List<I_MKTG_ContactPerson> retrieveContactPersons()
		{
			return Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_ContactPerson.class)
					.addOnlyActiveRecordsFilter()
					.create()
					.list();
		}

		@Test
		@Override
		public void sameOldEmail()
		{
			final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
			final User user1 = createUser("name1", newUserAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();

			final String oldEmailAddress = "Oldtestmail@Oldtestmail.Oldtestmail";

			createContactPersonRecord(user1.getId(), oldEmailAddress, LANGUAGE_en_AU, platformId);

			contactPersonService.updateContactPersonsEmailFromUser(user1, oldEmailAddress, LANGUAGE_en_AU);

			final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

			assertThat(resultContactPersons).hasSize(1);
			final String resultAddress = resultContactPersons.get(0).getEMail();

			assertThat(resultAddress).isSameAs(newUserAddress);
		}

		@Test
		@Override
		public void sameOldEmailAndLanguage()
		{
			final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
			final Language newLanguage = LANGUAGE_en_US;

			final User user1 = createUser("name1", newUserAddress, newLanguage);

			final PlatformId platformId = createPlatformId();

			final String oldEmailAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
			final Language oldLanguage = LANGUAGE_en_GB;

			createContactPersonRecord(user1.getId(), oldEmailAddress, oldLanguage, platformId);

			contactPersonService.updateContactPersonsEmailFromUser(user1, oldEmailAddress, oldLanguage);

			final List<I_MKTG_ContactPerson> resultRecords = retrieveContactPersons();

			assertThat(resultRecords).hasSize(1);
			assertThat(resultRecords.get(0).getEMail()).isEqualTo(newUserAddress);
			assertThat(resultRecords.get(0).getAD_Language()).isEqualTo(newLanguage.getAD_Language());
		}

		@Test
		@Override
		public void differentOldEmail()
		{
			final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
			final User user1 = createUser("name1", newUserAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();

			final String contactPersonAddress = "Anothertestmail@Anothertestmail.Anothertestmail";

			createContactPersonRecord(user1.getId(), contactPersonAddress, LANGUAGE_en_AU, platformId);

			final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
			contactPersonService.updateContactPersonsEmailFromUser(user1, oldUserAddress, LANGUAGE_en_AU);

			final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

			assertThat(resultContactPersons).hasSize(1);
			final String resultAddress = resultContactPersons.get(0).getEMail();
			assertThat(resultAddress)
					.isNotSameAs(newUserAddress)
					.isSameAs(contactPersonAddress);
		}

		@Test
		@Override
		public void emptyOldEmail()
		{
			final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
			final User user1 = createUser("name1", newUserAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();

			final String contactPersonAddress = "";

			createContactPersonRecord(user1.getId(), contactPersonAddress, LANGUAGE_en_AU, platformId);

			final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
			contactPersonService.updateContactPersonsEmailFromUser(user1, oldUserAddress, LANGUAGE_en_AU);

			final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

			assertThat(resultContactPersons).hasSize(1);
			final String resultAddress = resultContactPersons.get(0).getEMail();

			assertThat(resultAddress).isSameAs(newUserAddress);
		}

		@Test
		@Override
		public void nullOldEmail()
		{
			final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
			final User user1 = createUser("name1", newUserAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();

			createContactPersonRecord(user1.getId(), null, LANGUAGE_en_AU, platformId);

			final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
			contactPersonService.updateContactPersonsEmailFromUser(user1, oldUserAddress, LANGUAGE_en_AU);

			final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

			assertThat(resultContactPersons).hasSize(1);
			final String resultAddress = resultContactPersons.get(0).getEMail();

			assertThat(resultAddress).isSameAs(newUserAddress);
		}
	}

	@Nested
	class updateUserEmailFromContactPerson implements UserAndContactPersonSyncTests
	{
		private List<I_AD_User> retrieveUsers()
		{
			return Services.get(IQueryBL.class).createQueryBuilder(I_AD_User.class)
					.addOnlyActiveRecordsFilter()
					.addNotEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, UserId.SYSTEM) // the test helper is creating an extra user with id 0, so exclude it
					.create()
					.list();
		}

		@SuppressWarnings("SameParameterValue")
		private UserId createUser(
				@NonNull final String name,
				@Nullable final String mail,
				@Nullable final Language language)
		{
			final I_AD_User userRecord = newInstance(I_AD_User.class);

			userRecord.setName(name);
			userRecord.setEMail(mail);
			userRecord.setAD_Language(asLanguageStringOrNull(language));

			save(userRecord);
			return UserId.ofRepoId(userRecord.getAD_User_ID());
		}

		@Override
		@Test
		public void sameOldEmail()
		{
			final String oldEmailAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
			final UserId userId = createUser("name1", oldEmailAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();
			final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
			ContactPerson contactPerson = createContactPersonRecord(userId, newContactPersonAddress, LANGUAGE_en_AU, platformId);

			contactPersonService.updateUserFromContactPersonIfFeasible(contactPerson, oldEmailAddress, null);

			final List<I_AD_User> resultUsers = retrieveUsers();

			assertThat(resultUsers).hasSize(1);

			final String resultEmailAddress = resultUsers.get(0).getEMail();

			assertThat(resultEmailAddress).isSameAs(newContactPersonAddress);
		}

		@Override
		@Test
		public void differentOldEmail()
		{
			final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
			final UserId userId = createUser("name1", oldUserAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();
			final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
			ContactPerson contactPerson = createContactPersonRecord(userId, newContactPersonAddress, LANGUAGE_en_AU, platformId);

			final String anotherEmailAddress = "AnotheremailAddress@AnotherEmailAddress.AnotherEmailAddress";

			contactPersonService.updateUserFromContactPersonIfFeasible(contactPerson, anotherEmailAddress, null);

			final List<I_AD_User> resultUsers = retrieveUsers();

			assertThat(resultUsers).hasSize(1);

			final String resultEmailAddress = resultUsers.get(0).getEMail();

			assertThat(resultEmailAddress)
					.isNotSameAs(newContactPersonAddress)
					.isSameAs(oldUserAddress);
		}

		@Override
		@Test
		public void nullOldEmail()
		{
			final String oldEmailAddress = null;
			final UserId userId = createUser("name1", oldEmailAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();
			final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
			ContactPerson contactPerson = createContactPersonRecord(userId, newContactPersonAddress, LANGUAGE_en_AU, platformId);

			contactPersonService.updateUserFromContactPersonIfFeasible(contactPerson, oldEmailAddress, null);

			final List<I_AD_User> resultUsers = retrieveUsers();

			assertThat(resultUsers).hasSize(1);

			final String resultEmailAddress = resultUsers.get(0).getEMail();

			assertThat(resultEmailAddress).isSameAs(newContactPersonAddress);
		}

		@Override
		@Test
		public void emptyOldEmail()
		{
			final String oldEmailAddress = "";
			final UserId userId = createUser("name1", oldEmailAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();
			final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
			ContactPerson contactPerson = createContactPersonRecord(userId, newContactPersonAddress, LANGUAGE_en_AU, platformId);

			contactPersonService.updateUserFromContactPersonIfFeasible(contactPerson, oldEmailAddress, null);

			final List<I_AD_User> resultUsers = retrieveUsers();

			assertThat(resultUsers).hasSize(1);

			final String resultEmailAddress = resultUsers.get(0).getEMail();

			assertThat(resultEmailAddress).isSameAs(newContactPersonAddress);
		}

		@Override
		@Test
		public void sameOldEmailAndLanguage()
		{
			final String oldEmailAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
			final Language oldLanguage = LANGUAGE_en_GB;

			final UserId userId = createUser("name1", oldEmailAddress, oldLanguage);

			final PlatformId platformId = createPlatformId();
			final String newContactPersonAddress = "Newtestmail@Newtestmail.Newtestmail";
			final Language newLanguage = LANGUAGE_en_US;

			ContactPerson contactPerson = createContactPersonRecord(userId, newContactPersonAddress, newLanguage, platformId);

			// invoke the method under test
			contactPersonService.updateUserFromContactPersonIfFeasible(contactPerson, oldEmailAddress, oldLanguage);

			final List<I_AD_User> resultRecords = retrieveUsers();

			assertThat(resultRecords).hasSize(1);
			assertThat(resultRecords.get(0).getEMail()).isEqualTo(newContactPersonAddress);
			assertThat(resultRecords.get(0).getAD_Language()).isEqualTo(newLanguage.getAD_Language());
		}
	}

}
