package de.metas.ui.web.window.datatypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Immutable
// JSON
public abstract class DocumentId implements Serializable
{
	private static final int NEW_ID = -1;
	/**
	 * If {@link DocumentId#of(String)} is called with this string, then {@link DocumentId#isNew()} will return {@code true}.
	 */
	public static final String NEW_ID_STRING = "NEW";
	@SuppressWarnings("StaticInitializerReferencesSubClass")
	public static final DocumentId NEW = new IntDocumentId(NEW_ID);

	private static final String DOCUMENT_ID_PREFIX = "D";

	private static final String COMPOSED_KEY_SEPARATOR = "$";
	private static final Splitter COMPOSED_KEY_SPLITTER = Splitter.on(COMPOSED_KEY_SEPARATOR);

	/**
	 * Attempts to parse the given {@code idStr} into an integer and return an {@link IntDocumentId}. If the parsing fails, it returns a {@link StringDocumentId} instead.
	 *
	 * @param idStr might represent an integer or a string, but may not be empty or {@code null}.
	 */
	@JsonCreator
	public static DocumentId of(final String idStr)
	{
		if (NEW_ID_STRING.equals(idStr))
		{
			return NEW;
		}

		if (idStr == null)
		{
			throw new NullPointerException("idStr shall not be null");
		}
		if (idStr.isEmpty())
		{
			throw new NullPointerException("idStr shall not be empty");
		}

		final int idInt;
		try
		{
			idInt = Integer.parseInt(idStr);
		}
		catch (final NumberFormatException e)
		{
			// TODO: find a better way to distinguish between IntegerDocumentId and StringDocumentId.
			// For now we are fine because in almost all cases they are integers...
			return new StringDocumentId(idStr);
		}

		return of(idInt);
	}

	@JsonCreator
	public static DocumentId of(final int idInt)
	{
		if (idInt == NEW_ID)
		{
			return NEW;
		}

		return new IntDocumentId(idInt);
	}

	public static DocumentId of(@NonNull final RepoIdAware id)
	{
		return of(id.getRepoId());
	}

	public static <T extends RepoIdAware> ImmutableSet<DocumentId> ofRepoIdsSet(@NonNull final Set<T> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableSet.of();
		}
		else
		{
			return ids.stream()
					.map(DocumentId::of)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

	@Nullable
	public static DocumentId ofStringOrEmpty(@Nullable final String idStr)
	{
		if (Check.isEmpty(idStr, true))
		{
			return null;
		}
		return of(idStr.trim());
	}

	public static DocumentId ofString(final String idStr)
	{
		return new StringDocumentId(idStr);
	}

	/**
	 * @return {@code null} if the given {@code idObj} is null or an empty string
	 */
	@Nullable
	public static DocumentId ofObjectOrNull(@Nullable final Object idObj)
	{
		if (idObj == null)
		{
			return null;
		}
		if (idObj instanceof String)
		{
			return ofStringOrEmpty((String)idObj);
		}
		return ofObject(idObj);
	}

	public static DocumentId ofObject(@NonNull final Object idObj)
	{
		if (JSONNullValue.isNull(idObj))
		{
			throw new NullPointerException("Null id");
		}
		else if (idObj instanceof Integer)
		{
			final int idInt = (int)idObj;
			return of(idInt);
		}
		else if (idObj instanceof String)
		{
			final String idStr = (String)idObj;
			return of(idStr);
		}
		else if (idObj instanceof LookupValue)
		{
			final LookupValue lookupValue = (LookupValue)idObj;
			return ofObject(lookupValue.getId());
		}
		else
		{
			throw new AdempiereException("Cannot convert '" + idObj + "' (" + idObj.getClass() + ") to " + DocumentId.class);
		}
	}

	public static Supplier<DocumentId> supplier(@NonNull final IntSupplier intSupplier)
	{
		return () -> of(intSupplier.getAsInt());
	}

	public static Supplier<DocumentId> supplier(@NonNull final String prefix, final int firstId)
	{
		final AtomicInteger nextId = new AtomicInteger(firstId);
		return () -> ofString(prefix + nextId.getAndIncrement());
	}

	public static DocumentId ofComposedKeyParts(final List<Object> composedKeyParts)
	{
		Check.assumeNotEmpty(composedKeyParts, "composedKeyParts is not empty");

		if (composedKeyParts.size() == 1)
		{
			final Object idObj = composedKeyParts.get(0);
			return ofObject(idObj);
		}
		else
		{
			final String idStr = composedKeyParts.stream()
					.map(DocumentId::convertToDocumentIdPart)
					.map(String::valueOf)
					.collect(Collectors.joining(COMPOSED_KEY_SEPARATOR));
			return ofString(idStr);
		}
	}

	@Nullable
	private static Object convertToDocumentIdPart(final Object idPartObj)
	{
		if (idPartObj == null)
		{
			return null;
		}
		else if (idPartObj instanceof LookupValue)
		{
			return ((LookupValue)idPartObj).getId();
		}
		else
		{
			return idPartObj;
		}
	}

	private DocumentId()
	{
	}

	@Override
	public String toString()
	{
		return toJson();
	}

	@JsonValue
	public abstract String toJson();

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(final Object obj);

	public abstract boolean isInt();

	public abstract int toInt();

	public abstract boolean isComposedKey();

	public abstract List<Object> toComposedKeyParts();

	public DocumentId toIncludedRowId()
	{
		return ofString(DocumentId.DOCUMENT_ID_PREFIX + toJson());
	}

	public int removeDocumentPrefixAndConvertToInt()
	{
		if (isInt())
		{
			return toInt();
		}

		String idStr = toJson();
		if (idStr.startsWith(DOCUMENT_ID_PREFIX))
		{
			idStr = idStr.substring(DOCUMENT_ID_PREFIX.length());
		}

		return Integer.parseInt(idStr);
	}

	public abstract boolean isNew();

	public int toIntOr(final int fallbackValue)
	{
		return isInt() ? toInt() : fallbackValue;
	}

	public <T extends RepoIdAware> T toId(@NonNull final IntFunction<T> mapper)
	{
		return mapper.apply(toInt());
	}

	private static final class IntDocumentId extends DocumentId
	{
		private final int idInt;

		private IntDocumentId(final int idInt)
		{
			super();
			this.idInt = idInt;
		}

		@Override
		public String toJson()
		{
			if (idInt == NEW_ID)
			{
				return NEW_ID_STRING;
			}
			return String.valueOf(idInt);
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(idInt);
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}

			if (!(obj instanceof IntDocumentId))
			{
				return false;
			}

			final IntDocumentId other = (IntDocumentId)obj;
			return idInt == other.idInt;
		}

		@Override
		public boolean isInt()
		{
			return true;
		}

		@Override
		public int toInt()
		{
			return idInt;
		}

		@Override
		public boolean isNew()
		{
			return idInt == NEW_ID;
		}

		@Override
		public boolean isComposedKey()
		{
			return false;
		}

		@Override
		public List<Object> toComposedKeyParts()
		{
			return ImmutableList.of(idInt);
		}
	}

	private static final class StringDocumentId extends DocumentId
	{
		private final String idStr;

		private StringDocumentId(final String idStr)
		{
			Check.assumeNotEmpty(idStr, "idStr is not empty");
			this.idStr = idStr;
		}

		@Override
		public String toJson()
		{
			return idStr;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(idStr);
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}

			if (!(obj instanceof StringDocumentId))
			{
				return false;
			}

			final StringDocumentId other = (StringDocumentId)obj;
			return Objects.equals(idStr, other.idStr);
		}

		@Override
		public boolean isInt()
		{
			return false;
		}

		@Override
		public int toInt()
		{
			if (isComposedKey())
			{
				throw new AdempiereException("Composed keys cannot be converted to int: " + this);
			}
			else
			{
				throw new AdempiereException("String document IDs cannot be converted to int: " + this);
			}
		}

		@Override
		public boolean isNew()
		{
			return false;
		}

		@Override
		public boolean isComposedKey()
		{
			return idStr.contains(COMPOSED_KEY_SEPARATOR);
		}

		@Override
		public List<Object> toComposedKeyParts()
		{
			final ImmutableList.Builder<Object> composedKeyParts = ImmutableList.builder();
			COMPOSED_KEY_SPLITTER.split(idStr).forEach(composedKeyParts::add);
			return composedKeyParts.build();
		}
	}
}
