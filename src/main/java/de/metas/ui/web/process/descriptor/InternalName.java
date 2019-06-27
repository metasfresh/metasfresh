package de.metas.ui.web.process.descriptor;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

@EqualsAndHashCode(doNotUseGetters = true)
public final class InternalName
{
	@Nullable public static InternalName ofNullableString(@Nullable final String str)
	{
		return Check.isEmpty(str, true) ? null : ofString(str);
	}

	@JsonCreator
	public static InternalName ofString(@NonNull final String str)
	{
		final String strNorm = normalizeString(str);
		if (str.isEmpty())
		{
			throw new AdempiereException("Invalid internal name: " + str);
		}

		return new InternalName(strNorm);
	}

	/**
	 * This is mostly for cypress and also to be in line with the html specification (https://www.w3.org/TR/html50/dom.html#the-id-attribute).
	 *
	 * There are some processes (actions) which have space in their #ID.
	 * On the frontend side: that internalName field is used to create the JSON.
	 * On the backend side: the process value is used to create the internalName.
	 *
	 * The simple solution is to replace spaces with underscores.
	 */
	@NonNull private static String normalizeString(@NonNull final String str)
	{
		return str.trim()
				.replace(" ", "_");
	}

	private final String stringValue;

	private InternalName(@NonNull final String stringValue)
	{
		this.stringValue = stringValue;
	}

	/**
	 * @deprecated please use {@link #getAsString()}
	 */
	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return stringValue;
	}
}
