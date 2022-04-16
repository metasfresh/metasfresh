package de.metas.marketing.base;

import de.metas.i18n.Language;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_Platform;
import de.metas.marketing.base.model.PlatformId;
import de.metas.user.User;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
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
import static org.assertj.core.api.Assertions.assertThat;

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
		contactPersonService = new ContactPersonService(new ContactPersonRepository());
	}

	private void createContactPersonRecord(
			@NonNull final User user,
			@Nullable final String emailAddress,
			@NonNull final PlatformId platformId)
	{
		createContactPersonRecord(user, emailAddress, LANGUAGE_en_AU, platformId);
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

	private void createContactPersonRecord(
			@NonNull final User user,
			@Nullable final String emailAddress,
			@Nullable final Language language,
			@NonNull final PlatformId platformId)
	{
		final I_MKTG_ContactPerson contactPerson = newInstance(I_MKTG_ContactPerson.class);

		contactPerson.setAD_User_ID(user.getId().getRepoId());
		contactPerson.setEMail(emailAddress);
		contactPerson.setAD_Language(asLanguageStringOrNull(language));
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

	@Nested
	class updateContactPersonsEmailFromUser
	{
		@Test
		public void sameOldAddress()
		{
			final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
			final User user1 = createUser("name1", newUserAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();

			final String oldEmailAddress = "Oldtestmail@Oldtestmail.Oldtestmail";

			createContactPersonRecord(user1, oldEmailAddress, platformId);

			contactPersonService.updateContactPersonsEmailFromUser(user1, oldEmailAddress, LANGUAGE_en_AU);

			final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

			assertThat(resultContactPersons).hasSize(1);
			final String resultAddress = resultContactPersons.get(0).getEMail();

			assertThat(resultAddress).isSameAs(newUserAddress);
		}

		@Test
		public void sameOldAddressAndLanguage()
		{
			final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
			final Language newLanguage = LANGUAGE_en_US;

			final User user1 = createUser("name1", newUserAddress, newLanguage);

			final PlatformId platformId = createPlatformId();

			final String oldEmailAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
			final Language oldLanguage = LANGUAGE_en_GB;

			createContactPersonRecord(user1, oldEmailAddress, oldLanguage, platformId);

			contactPersonService.updateContactPersonsEmailFromUser(user1, oldEmailAddress, oldLanguage);

			final List<I_MKTG_ContactPerson> resultRecords = retrieveContactPersons();

			assertThat(resultRecords).hasSize(1);
			assertThat(resultRecords.get(0).getEMail()).isEqualTo(newUserAddress);
			assertThat(resultRecords.get(0).getAD_Language()).isEqualTo(newLanguage.getAD_Language());
		}

		@Test
		public void differentOldAddress()
		{
			final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
			final User user1 = createUser("name1", newUserAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();

			final String contactPersonAddress = "Anothertestmail@Anothertestmail.Anothertestmail";

			createContactPersonRecord(user1, contactPersonAddress, platformId);

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
		public void emptyOldAddress()
		{
			final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
			final User user1 = createUser("name1", newUserAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();

			final String contactPersonAddress = "";

			createContactPersonRecord(user1, contactPersonAddress, platformId);

			final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
			contactPersonService.updateContactPersonsEmailFromUser(user1, oldUserAddress, LANGUAGE_en_AU);

			final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

			assertThat(resultContactPersons).hasSize(1);
			final String resultAddress = resultContactPersons.get(0).getEMail();

			assertThat(resultAddress).isSameAs(newUserAddress);
		}

		@Test
		public void nullOldAddress()
		{
			final String newUserAddress = "Newtestmail@Newtestmail.Newtestmail";
			final User user1 = createUser("name1", newUserAddress, LANGUAGE_en_AU);

			final PlatformId platformId = createPlatformId();

			final String contactPersonAddress = null;
			createContactPersonRecord(user1, contactPersonAddress, platformId);

			final String oldUserAddress = "Oldtestmail@Oldtestmail.Oldtestmail";
			contactPersonService.updateContactPersonsEmailFromUser(user1, oldUserAddress, LANGUAGE_en_AU);

			final List<I_MKTG_ContactPerson> resultContactPersons = retrieveContactPersons();

			assertThat(resultContactPersons).hasSize(1);
			final String resultAddress = resultContactPersons.get(0).getEMail();

			assertThat(resultAddress).isSameAs(newUserAddress);
		}
	}
}
