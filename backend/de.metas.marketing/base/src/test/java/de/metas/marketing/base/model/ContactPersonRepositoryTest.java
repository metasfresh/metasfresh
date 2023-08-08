/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.marketing.base.model;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.Language;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Objects;

@ExtendWith(AdempiereTestWatcher.class)
class ContactPersonRepositoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@ParameterizedTest(name = "deactivatedOnRemotePlatform={argumentsWithNames}")
	@EnumSource(DeactivatedOnRemotePlatform.class)
	void test_save_load(final DeactivatedOnRemotePlatform deactivatedOnRemotePlatform)
	{
		final ContactPersonRepository contactPersonRepository = new ContactPersonRepository();
		final ContactPerson contactPerson = contactPersonRepository.save(ContactPerson.builder()
				.name("name")
				.userId(UserId.ofRepoId(1))
				.address(EmailAddress.of("a@b.com", deactivatedOnRemotePlatform))
				.contactPersonId(null)
				.remoteId("remoteId")
				.platformId(PlatformId.ofRepoId(3))
				//
				.bPartnerId(BPartnerId.ofRepoId(2))
				//.bpLocationId(BPartnerLocationId.ofRepoId(2, 4)) // commented out to avoid trying to load the BPL
				//.locationId(LocationId.ofRepoId(5))
				//
				// .boilerPlateId(BoilerPlateId.ofRepoId(6)) // not persisted
				//
				.language(Language.getLanguage("de_DE"))
				.build());
		System.out.println("saved: " + contactPerson);
		final ContactPersonId contactPersonId = Objects.requireNonNull(contactPerson.getContactPersonId());

		final ContactPerson contactPersonLoaded = contactPersonRepository.getById(contactPersonId);
		System.out.println("loaded: " + contactPersonLoaded);
		Assertions.assertThat(contactPersonLoaded)
				.usingRecursiveComparison()
				.isEqualTo(contactPerson);
	}
}