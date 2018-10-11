package de.metas.marketing.base.model;

import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class EmailAddress implements ContactAddress
{
	public static Optional<EmailAddress> cast(@Nullable final ContactAddress contactAddress)
	{
		if (contactAddress != null && contactAddress instanceof EmailAddress)
		{
			return Optional.of((EmailAddress)contactAddress);
		}
		return Optional.empty();
	}

	public static String getEmailAddessStringOrNull(@Nullable final ContactAddress contactAddress)
	{
		final Optional<String> stringIfPresent = EmailAddress
				.cast(contactAddress)
				.map(EmailAddress::getValue)
				.filter(s -> !Check.isEmpty(s, true));

		return stringIfPresent.orElse(null);
	}

	public static Boolean getActiveOnRemotePlatformOrNull(@Nullable final ContactAddress contactAddress)
	{
		final Optional<Boolean> boolIfPresent = EmailAddress
				.cast(contactAddress)
				.map(EmailAddress::getDeactivatedOnRemotePlatform);

		return boolIfPresent.orElse(null);
	}

	public static EmailAddress ofString(@NonNull final String emailAddress)
	{
		return new EmailAddress(emailAddress, null);
	}

	public static EmailAddress ofStringOrNull(@Nullable final String emailAddress)
	{
		if (Check.isEmpty(emailAddress, true))
		{
			return null;
		}
		return new EmailAddress(emailAddress, null);
	}

	public static EmailAddress of(
			@NonNull final String emailAddress,
			final Boolean deactivatedOnRemotePlatform)
	{
		return new EmailAddress(emailAddress, deactivatedOnRemotePlatform);
	}

	String value;

	/** null means "unknown" */
	Boolean deactivatedOnRemotePlatform;

	public EmailAddress(
			@NonNull final String value,
			@Nullable final Boolean deactivatedOnRemotePlatform)
	{
		this.value = Check.assumeNotEmpty(value, "The given value may not be empty");
		this.deactivatedOnRemotePlatform = deactivatedOnRemotePlatform;
	}

	@Override
	public TYPE getType()
	{
		return TYPE.EMAIL;
	}
}
