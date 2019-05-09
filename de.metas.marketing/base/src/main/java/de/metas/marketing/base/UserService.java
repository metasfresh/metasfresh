package de.metas.marketing.base;

import java.util.Objects;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import de.metas.i18n.Language;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.user.User.UserBuilder;
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

	public void updateUserFromContactPersonIfFeasible(
			@NonNull final ContactPerson contactPerson,
			@Nullable final String oldContactPersonMail,
			@Nullable final Language oldContactPersonLanguage)
	{
		final UserId userId = contactPerson.getUserId();
		if (userId == null)
		{
			return; // no user to update the email
		}

		final User user = userRepo.getByIdInTrx(userId);

		final boolean updateUserMail = isFitForUpdate(user.getEmailAddress(), oldContactPersonMail);
		final boolean updateUserLanguage = isFitForUpdate(user.getUserLanguage(), oldContactPersonLanguage);
		if (!updateUserMail && !updateUserLanguage)
		{
			return; // nothing to do
		}

		final UserBuilder updatedUser = user.toBuilder();
		if (updateUserMail)
		{
			updatedUser.emailAddress(contactPerson.getEmailAddessStringOrNull());
		}
		if (updateUserLanguage)
		{
			updatedUser.userLanguage(contactPerson.getLanguage());
			if (contactPerson.getLanguage() != null)
			{
				updatedUser.language(contactPerson.getLanguage());
			}
		}
		userRepo.save(updatedUser.build());
	}

	private boolean isFitForUpdate(
			@Nullable final Object currentUserValue,
			@Nullable final Object oldContactValue)
	{
		if (Check.isEmpty(currentUserValue))
		{
			return true; // user has no value, so let's give him/her one
		}

		// if user and contact were in sync, then keep them in sync, i.e. forward the new contact value to the user.
		final boolean userValueInSyncWithOldcontactValue = Objects.equals(currentUserValue, oldContactValue);
		return userValueInSyncWithOldcontactValue;
	}
}
