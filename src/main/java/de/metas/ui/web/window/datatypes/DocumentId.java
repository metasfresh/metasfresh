package de.metas.ui.web.window.datatypes;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import javax.annotation.concurrent.Immutable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.printing.esb.base.util.Check;
import lombok.NonNull;

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
@SuppressWarnings("serial")
// JSON
public abstract class DocumentId implements Serializable
{
	public static final transient String DOCUMENT_ID_PREFIX = "D";

	private static final transient int NEW_ID = -1;

	/**
	 * If {@link DocumentId#of(String)} is called with this string, then {@link DocumentId#isNew()} will return {@code true}.
	 */
	public static final transient String NEW_ID_STRING = "NEW";
	public static final transient DocumentId NEW = new IntDocumentId(NEW_ID);

	/**
	 * Attempts to parse the given {@code idStr} into an integer and return an {@link IntDocumentId}. If the parsing fails, it returns a {@link StringDocumentId} instead.
	 *
	 * @param idStr might represent an integer or a string, but may not be empty or {@code null}.
	 * @return
	 */
	@JsonCreator
	public static final DocumentId of(final String idStr)
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
	public static final DocumentId of(final int idInt)
	{
		if (idInt == NEW_ID)
		{
			return NEW;
		}

		return new IntDocumentId(idInt);
	}

	public static DocumentId ofString(final String idStr)
	{
		return new StringDocumentId(idStr);
	}

	public static final DocumentId fromNullable(final String idStr)
	{
		if (Check.isEmpty(idStr, true))
		{
			return null;
		}
		return of(idStr.trim());
	}

	public static final Supplier<DocumentId> supplier(final IntSupplier intSupplier)
	{
		Check.assumeNotNull(intSupplier, "Parameter intSupplier is not null");
		return () -> DocumentId.of(intSupplier.getAsInt());
	}

	public static final Supplier<DocumentId> supplier(final String prefix, final int firstId)
	{
		Check.assumeNotNull(prefix, "Parameter prefix is not null");

		final AtomicInteger nextId = new AtomicInteger(firstId);
		return () -> DocumentId.ofString(prefix + nextId.getAndIncrement());
	}

	private DocumentId()
	{
		super();
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

	public <X extends Throwable> int toIntOrThrow(@NonNull final Supplier<? extends X> exceptionSupplier) throws X
	{
		if(isInt())
		{
			return toInt();
		}
		else
		{
			throw exceptionSupplier.get();
		}
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
			throw new AdempiereException("String document IDs cannot be converted to int: " + this);
		}

		@Override
		public boolean isNew()
		{
			return false;
		}
	}
}
