package de.metas.ui.web.login.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.util.hash.HashableString;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@ToString(exclude = "password")
public class JSONLoginAuthRequest
{
	@JsonProperty("username")
	String username;

	@JsonProperty("password")
	String password;

	@JsonCreator
	@Builder
	private JSONLoginAuthRequest(
			@JsonProperty("username") final String username,
			@JsonProperty("password") final String password)
	{
		// Tolerate null/empty values; we will validate them later and we will throw nice error messages
		// Check.assumeNotEmpty(username, "username is not empty");
		// Check.assumeNotEmpty(password, "password is not empty");

		this.username = username;
		this.password = password;
	}

	public HashableString getPasswordAsEncryptableString()
	{
		return HashableString.ofPlainValue(password);
	}
}
