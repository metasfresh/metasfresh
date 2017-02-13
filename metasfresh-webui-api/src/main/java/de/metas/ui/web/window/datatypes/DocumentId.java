package de.metas.ui.web.window.datatypes;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.concurrent.Immutable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import de.metas.printing.esb.base.util.Check;

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
public abstract class DocumentId implements Serializable
{
	private static final int NEW_ID = -1;
	public static final String NEW_ID_STRING = "NEW";
	private static final DocumentId NEW = new IntDocumentId(NEW_ID);

	private static final Splitter SPLITTER_DocumentIds = Splitter.on(",").trimResults().omitEmptyStrings();

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

	public static Set<DocumentId> ofCommaSeparatedString(final String string)
	{
		if (string == null || string.isEmpty())
		{
			return ImmutableSet.of();
		}
		return streamFromCommaSeparatedString(string)
				.collect(GuavaCollectors.toImmutableSet());
	}

	public static Stream<DocumentId> streamFromCommaSeparatedString(final String string)
	{
		// avoid NPE
		if (string == null)
		{
			return Stream.empty();
		}

		return SPLITTER_DocumentIds.splitToList(string)
				.stream()
				.map(idStr -> DocumentId.fromNullable(idStr))
				.filter(documentId -> documentId != null);

	}

	public static Set<DocumentId> ofStringSet(final Collection<String> documentIds)
	{
		if (documentIds == null || documentIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		return documentIds
				.stream()
				.map(idStr -> of(idStr))
				.collect(GuavaCollectors.toImmutableSet());
	}

	public static final DocumentId fromNullable(final String idStr)
	{
		if (Check.isEmpty(idStr, true))
		{
			return null;
		}
		return of(idStr.trim());
	}

	public static final Set<Integer> toIntSet(final Collection<DocumentId> documentIds)
	{
		if (documentIds == null || documentIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return documentIds.stream()
				.map(documentId -> documentId.toInt())
				.collect(GuavaCollectors.toImmutableSet());
	}

	public static final Set<Integer> toIntSetIgnoringNonInts(final Collection<DocumentId> documentIds)
	{
		if (documentIds == null || documentIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return documentIds.stream()
				.filter(documentId -> documentId != null && documentId.isInt())
				.map(documentId -> documentId.toInt())
				.collect(GuavaCollectors.toImmutableSet());
	}

	public static final Supplier<DocumentId> supplier(IntSupplier intSupplier)
	{
		Check.assumeNotNull(intSupplier, "Parameter intSupplier is not null");
		return () -> DocumentId.of(intSupplier.getAsInt());
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

	public abstract String toJson();

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(final Object obj);

	protected abstract boolean isInt();

	public abstract int toInt();

	public abstract boolean isNew();

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
		protected boolean isInt()
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
			super();
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
		protected boolean isInt()
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
