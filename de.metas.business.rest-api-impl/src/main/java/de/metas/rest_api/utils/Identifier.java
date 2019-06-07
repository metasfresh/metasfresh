package de.metas.rest_api.utils;

import static de.metas.util.Check.assumeNotEmpty;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

@Value
public class Identifier
{
	public enum Type
	{
		EXTERNAL_ID, VALUE, GLN, METASFRESH_ID
	}

	Type type;

	String string;

	public static final Identifier of(@NonNull final String string)
	{
		assumeNotEmpty("Parameter may not be empty", string);
		if (string.toLowerCase().startsWith("ext-"))
		{
			final String externalId = string.substring(4);
			return new Identifier(Type.EXTERNAL_ID, externalId);
		}
		else if (string.toLowerCase().startsWith("val-"))
		{
			final String externalId = string.substring(4);
			return new Identifier(Type.VALUE, externalId);
		}
		else if (string.toLowerCase().startsWith("gln-"))
		{
			final String externalId = string.substring(4);
			return new Identifier(Type.GLN, externalId);
		}
		else
		{
			try
			{
				final int repoId = Integer.parseInt(string);
				if (repoId <= 0) // there is an AD_User with AD_User_ID=0, but we don't want the endpoint to provide it anyways
				{
					throw new InvalidIdentifierException(string);
				}
			}
			catch (NumberFormatException e)
			{
				throw new InvalidIdentifierException(string);
			}
			return new Identifier(Type.METASFRESH_ID, string);
		}
	}
}
