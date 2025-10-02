package de.metas.shipper.gateway.spi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.i18n.Language;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.shipper.gateway.api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class ContactPerson
{
	@Nullable PhoneNumber phone;
	@Nullable String emailAddress;
	@Nullable String simplePhoneNumber;
	@NonNull String languageCode;

	@Builder
	@Jacksonized
	private ContactPerson(
			@Nullable final PhoneNumber phone,
			@Nullable final String simplePhoneNumber,
			@Nullable final String emailAddress,
			@Nullable final String languageCode)
	{
		this.phone = phone;
		this.simplePhoneNumber = simplePhoneNumber;
		this.emailAddress = emailAddress;
		this.languageCode = Check.isBlank(languageCode) ? Language.getBaseLanguage().getLanguageCode() : languageCode;

		final boolean simplePhoneNumberIsEmpty = Check.isEmpty(simplePhoneNumber);
		final boolean phoneIsEmpty = phone == null;
		Check.errorUnless(
				simplePhoneNumberIsEmpty || phoneIsEmpty,
				"Its not allowed to specify both a simple phone number string and a PhoneNumber instance because they might be contradictory; simplePhoneNumber={}; phone={}",
				simplePhoneNumber, phone);
	}

	@JsonIgnore
	@Nullable
	public String getPhoneAsStringOrNull()
	{
		if (simplePhoneNumber != null)
		{
			return simplePhoneNumber;
		}
		else if (phone != null)
		{
			return phone.getAsString();
		}
		return null;
	}

}
