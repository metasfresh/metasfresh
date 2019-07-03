package de.metas.rest_api.utils;

import static de.metas.util.Check.assumeNotEmpty;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.util.Check;
import de.metas.util.rest.ExternalId;
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

/** Identifies a metasfresh resourse (e.g. business partner) */
@Value
public class IdentifierString
{
	public enum Type
	{
		/** Every metasfresh ressource can be identifies via its metasfresh-ID (i.e. the PK of its data base record) */
		METASFRESH_ID,

		EXTERNAL_ID, VALUE, GLN
	}

	Type type;

	String value;

	public static final IdentifierString of(@NonNull final String value)
	{
		assumeNotEmpty("Parameter may not be empty", value);
		if (value.toLowerCase().startsWith("ext-"))
		{
			final String externalId = value.substring(4);
			return new IdentifierString(Type.EXTERNAL_ID, externalId);
		}
		else if (value.toLowerCase().startsWith("val-"))
		{
			final String valueString = value.substring(4);
			return new IdentifierString(Type.VALUE, valueString);
		}
		else if (value.toLowerCase().startsWith("gln-"))
		{
			final String glnString = value.substring(4);
			return new IdentifierString(Type.GLN, glnString);
		}
		else
		{
			try
			{
				final int repoId = Integer.parseInt(value);
				if (repoId <= 0) // there is an AD_User with AD_User_ID=0, but we don't want the endpoint to provide it anyways
				{
					throw new InvalidIdentifierException(value);
				}
			}
			catch (final NumberFormatException e)
			{
				throw new InvalidIdentifierException(value);
			}
			return new IdentifierString(Type.METASFRESH_ID, value);
		}
	}

	private IdentifierString(
			@NonNull final Type type,
			@NonNull final String value)
	{
		this.type = type;
		this.value = assumeNotEmpty(value, "Parameter value may not be empty");
	}

	public ExternalId asExternalId()
	{
		Check.assume(Type.EXTERNAL_ID.equals(type), "The type of this instace needs to be {}; this={}", Type.EXTERNAL_ID, this);

		return ExternalId.of(value);
	}

	public JsonExternalId asJsonExternalId()
	{
		Check.assume(Type.EXTERNAL_ID.equals(type), "The type of this instace needs to be {}; this={}", Type.EXTERNAL_ID, this);

		return JsonExternalId.of(value);
	}

	public MetasfreshId asMetasfreshId()
	{
		Check.assume(Type.METASFRESH_ID.equals(type), "The type of this instace needs to be {}; this={}", Type.METASFRESH_ID, this);

		final int repoId = Integer.parseInt(value);
		return MetasfreshId.of(repoId);
	}

}
