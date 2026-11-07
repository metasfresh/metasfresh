package de.metas.ui.web.window.datatypes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.MoreObjects;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
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

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@EqualsAndHashCode
public final class Password
{
	/**
	 * @return {@code null} if the given password is {@code null} or empty; an empty password is no password, so it is never shown as set
	 */
	@JsonCreator
	@Nullable
	public static Password ofNullableString(@Nullable final String password)
	{
		return password != null && !password.isEmpty() ? new Password(password) : null;
	}

	public static Password cast(final Object value)
	{
		return (Password)value;
	}

	public static final String OBFUSCATE_STRING = "********";

	private final String password;

	private Password(@NonNull final String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("password", "********")
				.toString();
	}

	@JsonValue
	public String toJson()
	{
		return OBFUSCATE_STRING;
	}

	public String getAsString()
	{
		return password;
	}

}
