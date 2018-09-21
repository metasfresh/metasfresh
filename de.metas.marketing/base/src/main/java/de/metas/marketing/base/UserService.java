package de.metas.marketing.base;

import org.adempiere.user.User;
import org.adempiere.user.UserId;
import org.adempiere.user.UserRepository;
import org.springframework.stereotype.Service;

import de.metas.marketing.base.model.ContactPerson;
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
public class UserService
{
	private final UserRepository userRepo;

	public UserService(@NonNull final UserRepository userRepo)
	{
		this.userRepo = userRepo;
	}

	public void updateUserEmailFromContactPerson(final ContactPerson contactPerson, final String oldContactPersonMail)
	{
		final UserId userId = contactPerson.getUserId();

		if (userId.getRepoId() <= 0)
		{
			// no user to update the email
			return;
		}
		final User user = userRepo.getById(userId);

		final String newContactPersonMail = contactPerson.getEmailAddessStringOrNull();

		final boolean isFitForEmailUpdate = isFitForEmailUpdate(user.getEmailAddress(), oldContactPersonMail);

		if (isFitForEmailUpdate)
		{
			updateUserEmail(user, newContactPersonMail);
		}
	}

	private User updateUserEmail(final User user, final String newEmailaddress)
	{
		final User updatedUser = user.toBuilder()
				.emailAddress(newEmailaddress)
				.build();

		userRepo.save(updatedUser);

		return updatedUser;

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
