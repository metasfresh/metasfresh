package de.metas.util.hash;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import de.metas.util.Check;
import de.metas.util.lang.UIDStringUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.util
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

@EqualsAndHashCode
public final class HashableString
{
	public static HashableString ofPlainValue(final String value)
	{
		if (value == null || value.isEmpty())
		{
			return EMPTY;
		}

		final boolean hashed = false;
		final String salt = null;
		return new HashableString(value, hashed, salt);
	}

	public static HashableString empty()
	{
		return EMPTY;
	}

	public static HashableString ofHashedValue(@NonNull final String hashedValue, final String salt)
	{
		if (!hashedValue.startsWith(PREFIX_SHA512))
		{
			throw new IllegalArgumentException("Invalid hashed value '" + hashedValue + "' because it does not starts with '" + PREFIX_SHA512 + "'.");
		}

		final boolean hashed = true;
		return new HashableString(hashedValue, hashed, salt);
	}

	public static HashableString fromString(final String value)
	{
		if (value == null || value.isEmpty())
		{
			return EMPTY;
		}
		else if (value.startsWith(PREFIX_SHA512))
		{
			final List<String> parts = SPLITTER.splitToList(value);
			final String salt = parts.size() >= 3 ? parts.get(2) : null;
			return ofHashedValue(value, salt);
		}
		else
		{
			return ofPlainValue(value);
		}
	}

	private static final String SEPARATOR = ":";
	private static final String PREFIX_SHA512 = "sha512" + SEPARATOR;
	private static final HashableString EMPTY = new HashableString("", false, null);
	private static final Splitter SPLITTER = Splitter.on(SEPARATOR);

	@Getter
	private final String value;
	@Getter
	private final boolean hashed;
	@Getter
	private String salt;

	private transient HashableString _hashedObject = null;

	private HashableString(final String value, final boolean hashed, final String salt)
	{
		this.value = value;
		this.hashed = hashed;
		this.salt = salt;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("hashed", hashed)
				.add("value", hashed ? value : "*******")
				.toString();
	}

	public boolean isEmpty()
	{
		return isPlain() && Check.isEmpty(value, false);
	}

	public static boolean isEmpty(final HashableString hashableString)
	{
		return hashableString == null || hashableString.isEmpty();
	}

	public boolean isPlain()
	{
		return !isHashed();
	}

	public HashableString hash()
	{
		final String salt = UIDStringUtil.createRandomUUID();
		return hashWithSalt(salt);
	}

	public HashableString hashWithSalt(final String salt)
	{
		if (hashed)
		{
			return this;
		}

		HashableString hashedObject = _hashedObject;
		if (hashedObject == null)
		{
			final String valueHashed = hashValue(value, salt);
			hashedObject = _hashedObject = new HashableString(valueHashed, true, salt);
		}

		return hashedObject;
	}

	private static String hashValue(final String valuePlain, final String salt)
	{
		// IMPORTANT: please keep it in sync with "hash_column_value" database function

		final String valuePlainNorm = valuePlain != null ? valuePlain : "";

		final String valueWithSalt;
		if (salt != null && !salt.isEmpty())
		{
			valueWithSalt = valuePlainNorm + salt;
		}
		else
		{
			valueWithSalt = valuePlainNorm;
		}

		final HashCode valueHashed = Hashing.sha512().hashString(valueWithSalt, StandardCharsets.UTF_8);
		String valueHasedAndEncoded = valueHashed.toString(); // hex encoding
		return PREFIX_SHA512 + valueHasedAndEncoded + SEPARATOR + salt;
	}

	public boolean isMatching(final HashableString other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null)
		{
			return false;
		}

		if (isPlain())
		{
			if (other.isPlain())
			{
				return valueEquals(other.value);
			}
			else
			{
				return hashWithSalt(other.salt).valueEquals(other.value);
			}
		}
		else
		{
			if (other.isPlain())
			{
				return other.hashWithSalt(salt).valueEquals(value);
			}
			else
			{
				return valueEquals(other.value);
			}
		}
	}

	private boolean valueEquals(final String otherValue)
	{
		return Objects.equals(this.value, otherValue);
	}

}
