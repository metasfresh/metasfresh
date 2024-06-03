package de.metas.marketing.base.model;

import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;

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
	@NonNull String value;

	@NonNull DeactivatedOnRemotePlatform deactivatedOnRemotePlatform;

	public static Optional<EmailAddress> cast(@Nullable final ContactAddress contactAddress)
	{
		return contactAddress instanceof EmailAddress
				? Optional.of((EmailAddress)contactAddress)
				: Optional.empty();
	}

	public static String getEmailAddressStringOrNull(@Nullable final ContactAddress contactAddress)
	{
		return cast(contactAddress).map(EmailAddress::getValue).orElse(null);
	}

	public static DeactivatedOnRemotePlatform getDeactivatedOnRemotePlatform(@Nullable final ContactAddress contactAddress)
	{
		final EmailAddress emailAddress = cast(contactAddress).orElse(null);
		return emailAddress != null ? emailAddress.getDeactivatedOnRemotePlatform() : DeactivatedOnRemotePlatform.UNKNOWN;
	}

	public static EmailAddress ofString(@NonNull final String emailAddress)
	{
		return new EmailAddress(emailAddress, DeactivatedOnRemotePlatform.UNKNOWN);
	}

	public static EmailAddress ofStringOrNull(@Nullable final String emailAddress)
	{
		return emailAddress != null && !Check.isBlank(emailAddress)
				? ofString(emailAddress)
				: null;
	}

	public static EmailAddress of(
			@NonNull final String emailAddress,
			@NonNull final DeactivatedOnRemotePlatform deactivatedOnRemotePlatform)
	{
		return new EmailAddress(emailAddress, deactivatedOnRemotePlatform);
	}

	private EmailAddress(
			@NonNull final String value,
			@NonNull final DeactivatedOnRemotePlatform deactivatedOnRemotePlatform)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("blank email address is not allowed");
		}

		this.value = valueNorm;
		this.deactivatedOnRemotePlatform = deactivatedOnRemotePlatform;
	}

	@Override
	public TYPE getType()
	{
		return TYPE.EMAIL;
	}
}
