/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.bpartner.GLN;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.JsonExternalId;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.Check;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.RepoIdAware;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.function.IntFunction;

import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.isEmpty;

/**
 * Identifies a metasfresh resource (e.g. business partner).
 * In most cases, the identifier is unique only within one org.
 */
@Value
public class IdentifierString
{
	public enum Type
	{
		/**
		 * Every metasfresh ressource can be identified via its metasfresh-ID (i.e. the PK of its data base record, e.g. {@code C_BPartner_ID} or {@code M_Product_ID})
		 */
		METASFRESH_ID,

		/**
		 * Note that at least for C_BPartner, the external ID is only unique per Org!
		 */
		EXTERNAL_ID,

		VALUE,

		GLN,

		INTERNALNAME,

		/**
		 * Note that in general document numbers are only unique per Org and DocType!
		 */
		DOC
	}

	Type type;

	@Getter(AccessLevel.NONE)
	String value;

	String rawIdentifierString;

	public static final String PREFIX_EXTERNAL_ID = "ext-";
	public static final String PREFIX_VALUE = "val-";
	public static final String PREFIX_INTERNALNAME = "int-";
	public static final String PREFIX_GLN = "gln-";
	public static final String PREFIX_DOC = "doc-";

	public static final IdentifierString ofOrNull(@Nullable final String rawIdentifierString)
	{
		if (isEmpty(rawIdentifierString, true))
		{
			return null;
		}
		return of(rawIdentifierString);
	}

	@JsonCreator
	public static IdentifierString of(@NonNull final String rawIdentifierString)
	{
		assumeNotEmpty("Parameter rawIdentifierString may not be empty", rawIdentifierString);
		if (rawIdentifierString.toLowerCase().startsWith(PREFIX_EXTERNAL_ID))
		{
			final String externalId = rawIdentifierString.substring(4).trim();
			if (externalId.isEmpty())
			{
				throw new AdempiereException("Invalid external ID: `" + rawIdentifierString + "`");
			}
			return new IdentifierString(Type.EXTERNAL_ID, externalId, rawIdentifierString);
		}
		else if (rawIdentifierString.toLowerCase().startsWith(PREFIX_VALUE))
		{
			final String valueString = rawIdentifierString.substring(4).trim();
			if (valueString.isEmpty())
			{
				throw new AdempiereException("Invalid value: `" + rawIdentifierString + "`");
			}
			return new IdentifierString(Type.VALUE, valueString, rawIdentifierString);
		}
		else if (rawIdentifierString.toLowerCase().startsWith(PREFIX_INTERNALNAME))
		{
			final String valueString = rawIdentifierString.substring(4).trim();
			if (valueString.isEmpty())
			{
				throw new AdempiereException("Invalid internal name: `" + rawIdentifierString + "`");
			}
			return new IdentifierString(Type.INTERNALNAME, valueString, rawIdentifierString);
		}
		else if (rawIdentifierString.toLowerCase().startsWith(PREFIX_GLN))
		{
			final String glnString = rawIdentifierString.substring(4).trim();
			if (glnString.isEmpty())
			{
				throw new AdempiereException("Invalid GLN: `" + rawIdentifierString + "`");
			}
			return new IdentifierString(Type.GLN, glnString, rawIdentifierString);
		}
		else if (rawIdentifierString.toLowerCase().startsWith(PREFIX_DOC))
		{
			final String docString = rawIdentifierString.substring(4).trim();
			if (docString.isEmpty())
			{
				throw new AdempiereException("Invalid documentId: `" + rawIdentifierString + "`");
			}
			return new IdentifierString(Type.DOC, docString, rawIdentifierString);
		}
		else
		{
			try
			{
				final int repoId = Integer.parseInt(rawIdentifierString);
				if (repoId <= 0) // there is an AD_User with AD_User_ID=0, but we don't want the endpoint to provide it anyways
				{
					throw new InvalidIdentifierException(rawIdentifierString);
				}

				return new IdentifierString(Type.METASFRESH_ID, rawIdentifierString, rawIdentifierString);
			}
			catch (final NumberFormatException ex)
			{
				throw new InvalidIdentifierException(rawIdentifierString, ex);
			}
		}
	}

	public static IdentifierString ofRepoId(final int repoId)
	{
		Check.assumeGreaterOrEqualToZero(repoId, "repoId");
		return new IdentifierString(Type.METASFRESH_ID, String.valueOf(repoId), String.valueOf(repoId));
	}

	public static final IdentifierString ofJsonExternalId(@Nullable final JsonExternalId jsonExternalId)
	{
		return of(PREFIX_EXTERNAL_ID + JsonExternalId.toValue(jsonExternalId));
	}

	public static final IdentifierString ofValue(@NonNull final String code)
	{
		return of(PREFIX_VALUE + code);
	}

	public static IdentifierString ofGLN(String gln)
	{
		return of(PREFIX_GLN + gln);
	}

	public static IdentifierString ofDoc(String doc)
	{
		return of(PREFIX_DOC + doc);
	}

	private IdentifierString(
			@NonNull final Type type,
			@NonNull final String value,
			@NonNull final String rawIdentifierString)
	{
		this.type = type;
		this.value = assumeNotEmpty(value, "Parameter value may not be empty");
		this.rawIdentifierString = rawIdentifierString;
	}

	/**
	 * @deprecated please use {@link #toJson()} instead
	 */
	@Override
	@Deprecated
	public String toString()
	{
		// using toJson because it's much more user friendly
		return toJson();
	}

	@JsonValue
	public String toJson()
	{
		final String prefix;
		if (Type.METASFRESH_ID.equals(type))
		{
			prefix = "";
		}
		else if (Type.EXTERNAL_ID.equals(type))
		{
			prefix = PREFIX_EXTERNAL_ID;
		}
		else if (Type.VALUE.equals(type))
		{
			prefix = PREFIX_VALUE;
		}
		else if (Type.GLN.equals(type))
		{
			prefix = PREFIX_GLN;
		}
		else if (Type.DOC.equals(type))
		{
			prefix = PREFIX_DOC;
		}
		else if (Type.INTERNALNAME.equals(type))
		{
			prefix = PREFIX_INTERNALNAME;
		}
		else
		{
			throw new AdempiereException("Unknown type: " + type);
		}

		return !prefix.isEmpty() ? prefix + value : value;
	}

	public ExternalId asExternalId()
	{
		Check.assume(Type.EXTERNAL_ID.equals(type), "The type of this instance needs to be {}; this={}", Type.EXTERNAL_ID, this);

		return ExternalId.of(value);
	}

	public JsonExternalId asJsonExternalId()
	{
		Check.assume(Type.EXTERNAL_ID.equals(type), "The type of this instance needs to be {}; this={}", Type.EXTERNAL_ID, this);

		return JsonExternalId.of(value);
	}

	public MetasfreshId asMetasfreshId()
	{
		Check.assume(Type.METASFRESH_ID.equals(type), "The type of this instance needs to be {}; this={}", Type.METASFRESH_ID, this);

		final int repoId = Integer.parseInt(value);
		return MetasfreshId.of(repoId);
	}

	public JsonMetasfreshId asJsonMetasfreshId()
	{
		Check.assume(Type.METASFRESH_ID.equals(type), "The type of this instance needs to be {}; this={}", Type.METASFRESH_ID, this);

		final int repoId = Integer.parseInt(value);
		return JsonMetasfreshId.of(repoId);
	}
	
	public <T extends RepoIdAware> T asMetasfreshId(@NonNull final IntFunction<T> mapper)
	{
		Check.assume(Type.METASFRESH_ID.equals(type), "The type of this instance needs to be {}; this={}", Type.METASFRESH_ID, this);

		final int repoId = Integer.parseInt(value);
		return mapper.apply(repoId);
	}

	public GLN asGLN()
	{
		Check.assume(Type.GLN.equals(type), "The type of this instance needs to be {}; this={}", Type.GLN, this);

		return GLN.ofString(value);
	}

	public String asDoc()
	{
		Check.assume(Type.DOC.equals(type), "The type of this instance needs to be {}; this={}", Type.DOC, this);

		return value;
	}

	public String asValue()
	{
		Check.assume(Type.VALUE.equals(type), "The type of this instance needs to be {}; this={}", Type.VALUE, this);

		return value;
	}

	public String asInternalName()
	{
		Check.assume(Type.INTERNALNAME.equals(type), "The type of this instance needs to be {}; this={}", Type.INTERNALNAME, this);

		return value;
	}
}
