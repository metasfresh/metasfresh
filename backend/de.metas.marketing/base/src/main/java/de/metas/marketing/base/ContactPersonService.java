package de.metas.marketing.base;

import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import de.metas.i18n.Language;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPerson.ContactPersonBuilder;
import de.metas.user.User;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.EmailAddress;
import de.metas.util.Check;
import lombok.NonNull;

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

@Service
public class ContactPersonService
{

	private final ContactPersonRepository contactPersonRepo;

	public ContactPersonService(@NonNull final ContactPersonRepository contactPersonRepo)
	{
		this.contactPersonRepo = contactPersonRepo;
	}

	public void updateContactPersonsEmailFromUser(
			@NonNull final User user,
			@Nullable final String oldUserEmail,
			@Nullable final Language oldUserLanguage)
	{
		final Set<ContactPerson> contactPersonsForUser = contactPersonRepo.getByUserId(user.getId());

		for (final ContactPerson contactPerson : contactPersonsForUser)
		{
			updateIfFeasible(contactPerson,
					user,
					oldUserEmail,
					oldUserLanguage);
		}
	}

	private void updateIfFeasible(
			@NonNull final ContactPerson contactPerson,
			@NonNull final User user,
			@Nullable final String oldUserEmail,
			@Nullable final Language oldUserLanguage)
	{
		final boolean updateContactMail = isFitForUpdate(contactPerson.getEmailAddessStringOrNull(), oldUserEmail);
		final boolean updateContactLanguage = isFitForUpdate(contactPerson.getLanguage(), oldUserLanguage);
		if (!updateContactMail && !updateContactLanguage)
		{
			return; // nothing to do
		}

		final ContactPersonBuilder updatedContactPerson = contactPerson.toBuilder();
		if (updateContactMail)
		{
			final EmailAddress userNewMailaddress = EmailAddress.ofStringOrNull(user.getEmailAddress());
			updatedContactPerson.address(userNewMailaddress);
		}
		if (updateContactLanguage)

		{
			updatedContactPerson.language(user.getUserLanguage());
		}
		contactPersonRepo.save(updatedContactPerson.build());

	}

	// private ContactPerson updateContactPersonEmail(
	// @NonNull final ContactPerson contactPerson,
	// final EmailAddress newEmailaddress)
	// {
	// final ContactPerson updatedContactPerson = contactPerson.toBuilder()
	// .address(newEmailaddress)
	// .build();
	//
	// contactPersonRepo.save(updatedContactPerson);
	//
	// return updatedContactPerson;
	// }

	private boolean isFitForUpdate(
			@Nullable final Object currentContactValue,
			@Nullable final Object oldUserValue)
	{
		if (Check.isEmpty(currentContactValue))
		{
			return true; // contact has no value, so let's give him/her one
		}

		// if user and contact were in sync, then keep them in sync, i.e. forward the new user value to the contact.
		final boolean userValueInSyncWithOldcontactValue = Objects.equals(currentContactValue, oldUserValue);
		return userValueInSyncWithOldcontactValue;
	}
}
