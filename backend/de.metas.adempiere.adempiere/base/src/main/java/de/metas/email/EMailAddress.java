package de.metas.email;

import java.util.List;

import javax.annotation.Nullable;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class EMailAddress
{
	public static EMailAddress ofNullableString(@Nullable final String emailStr)
	{
		return !Check.isEmpty(emailStr, true) ? new EMailAddress(emailStr) : null;
	}

	@JsonCreator
	public static EMailAddress ofString(@NonNull final String emailStr)
	{
		return new EMailAddress(emailStr);
	}

	public static List<EMailAddress> ofSemicolonSeparatedList(final String emailsListStr)
	{
		if (Check.isEmpty(emailsListStr, true))
		{
			return ImmutableList.of();
		}

		return Splitter.on(";")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(emailsListStr)
				.stream()
				.map(EMailAddress::ofNullableString)
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());
	}

	public static ITranslatableString checkEMailValid(@Nullable final EMailAddress email)
	{
		if (email == null)
		{
			return TranslatableStrings.constant("no email");
		}
		return checkEMailValid(email.getAsString());
	}

	public static ITranslatableString checkEMailValid(@Nullable final String email)
	{
		if (Check.isEmpty(email, true))
		{
			return TranslatableStrings.constant("no email");
		}
		try
		{
			final InternetAddress ia = new InternetAddress(email, true);
			ia.validate();	// throws AddressException

			if (ia.getAddress() == null)
			{
				return TranslatableStrings.constant("invalid email");
			}

			return null; // OK
		}
		catch (AddressException ex)
		{
			logger.warn("Invalid email address: {}", email, ex);
			return TranslatableStrings.constant(ex.getLocalizedMessage());
		}
	}

	private static final Logger logger = LogManager.getLogger(EMailAddress.class);

	private final String emailStr;

	private EMailAddress(@NonNull final String emailStr)
	{
		this.emailStr = emailStr.trim();
		if (this.emailStr.isEmpty())
		{
			throw new AdempiereException("Empty email address is not valid");
		}
	}

	@JsonValue
	public String getAsString()
	{
		return emailStr;
	}

	public static String toStringOrNull(@Nullable final EMailAddress emailAddress)
	{
		return emailAddress != null ? emailAddress.getAsString() : null;
	}

	@Deprecated
	@Override
	public String toString()
	{
		return getAsString();
	}

	public InternetAddress toInternetAddress() throws AddressException
	{
		return new InternetAddress(emailStr, true);
	}
}
