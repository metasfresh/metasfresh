package de.metas.marketing.base;

import java.util.Set;

import org.adempiere.user.User;
import org.springframework.stereotype.Service;

import de.metas.marketing.base.model.ContactPerson;
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

	public void updateContactPersonsEmailFromUser(final User user, final String oldUserEmail)
	{
		final Set<ContactPerson> contactPersonsForUser = contactPersonRepo.getByUserId(user.getId());

		if (contactPersonsForUser.isEmpty())
		{
			// no contact person to update email
			return;
		}

		final EmailAddress userNewMailaddress = Check.isEmpty(user.getEmailAddress(), true) ? null : EmailAddress.of(user.getEmailAddress());

		contactPersonsForUser.stream()
				.filter(contactPerson -> isFitForEmailUpdate(contactPerson.getEmailAddessStringOrNull(), oldUserEmail))
				.forEach(contactPerson -> updateContactPersonEmail(contactPerson, userNewMailaddress));
	}

	private ContactPerson updateContactPersonEmail(final ContactPerson contactPerson, final EmailAddress newEmailaddress)
	{
		final ContactPerson updatedContactPerson = contactPerson.toBuilder()
				.address(newEmailaddress)
				.build();

		contactPersonRepo.save(updatedContactPerson);

		return updatedContactPerson;
	}

	private boolean isFitForEmailUpdate(final String currentEmailAddress, final String oldEmailAddress)
	{

		if (Check.isEmpty(currentEmailAddress))
		{
			return true;
		}

		return currentEmailAddress.equals(oldEmailAddress);
	}
}
