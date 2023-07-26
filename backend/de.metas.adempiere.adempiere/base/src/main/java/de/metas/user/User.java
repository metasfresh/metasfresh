package de.metas.user;

import java.time.LocalDate;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.Language;
import de.metas.util.Check;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class User
{
	/** can be null for not-yet-saved users */
	UserId id;

	BPartnerId bpartnerId;

	String name;

	String firstName;

	String lastName;

	String emailAddress;

	LocalDate birthday;

	String phone;

	/**
	 * Changes are persisted by the repo!
	 */
	Language userLanguage;

	/**
	 * Read-only; changes are <b>not</b> persisted by the repo!
	 */
	Language bPartnerLanguage;

	/**
	 * Either the user's or bPartner's or context's or base language. Never {@code null}.
	 * Read-only; changes are <b>not</b> persisted by the repo!
	 */
	Language language;

	ExternalId externalId;

	@Builder(toBuilder = true)
	private User(
			@Nullable final UserId id,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final String name,
			@Nullable final String firstName,
			@Nullable final String lastName,
			@Nullable final String emailAddress,
			@Nullable final LocalDate birthday,
			@Nullable final String phone,
			@Nullable final Language userLanguage,
			@Nullable final Language bPartnerLanguage,
			@NonNull final Language language,
			@Nullable final ExternalId externalId)
	{
		this.id = id;
		this.bpartnerId = bpartnerId;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.birthday = birthday;
		this.phone = phone;
		this.userLanguage = userLanguage;
		this.bPartnerLanguage = bPartnerLanguage;
		this.language = language;
		this.externalId = externalId;

		Check.assume(
				userLanguage == null || userLanguage.equals(language),
				"If a userLanguage parameter is specified, it needs to be equal to the language paramter; this={}",
				language);
	}

}
