package de.metas.ui.web.window.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class DocumentSaveStatus
{
	public static DocumentSaveStatus unknown()
	{
		return STATUS_Unknown;
	}

	public static DocumentSaveStatus saved()
	{
		return STATUS_Saved;
	}

	public static DocumentSaveStatus deleted()
	{
		return STATUS_Deleted;
	}

	public static DocumentSaveStatus notSaved(final DocumentValidStatus invalidState)
	{
		final String reason = invalidState.getReason();
		return builder().hasChangesToBeSaved(true).error(false).reason(reason).build();
	}

	public static DocumentSaveStatus error(@NonNull final Exception exception)
	{
		final String reason = AdempiereException.extractMessage(exception);
		return builder().hasChangesToBeSaved(true).error(true).reason(reason).exception(exception).build();
	}

	public static DocumentSaveStatus notSavedJustCreated()
	{
		return STATUS_NotSavedJustCreated;
	}

	public static DocumentSaveStatus savedJustLoaded()
	{
		return STATUS_SavedJustLoaded;
	}

	private static final DocumentSaveStatus STATUS_Unknown = builder().hasChangesToBeSaved(true).error(false).reason("not yet checked").build();
	private static final DocumentSaveStatus STATUS_Saved = builder().hasChangesToBeSaved(false).error(false).build();
	private static final DocumentSaveStatus STATUS_Deleted = builder().hasChangesToBeSaved(false).deleted(true).error(false).build();
	private static final DocumentSaveStatus STATUS_NotSavedJustCreated = builder().hasChangesToBeSaved(true).error(false).reason("new").build();
	private static final DocumentSaveStatus STATUS_SavedJustLoaded = builder().hasChangesToBeSaved(false).error(false).reason("just loaded").build();

	@JsonProperty("hasChanges") private final boolean hasChangesToBeSaved;
	@JsonProperty("deleted") @Getter private final boolean deleted;
	@JsonProperty("error") @Getter private final boolean error;
	@JsonProperty("reason") @JsonInclude(JsonInclude.Include.NON_EMPTY) @Nullable @Getter private final String reason;
	@JsonIgnore @Nullable private final transient Exception exception;

	@JsonProperty("saved") @Getter private final boolean saved; // computed

	private transient Integer _hashcode;

	@Builder
	private DocumentSaveStatus(
			final boolean hasChangesToBeSaved,
			final boolean deleted,
			final boolean error,
			@Nullable final String reason,
			@Nullable final Exception exception)
	{
		this.hasChangesToBeSaved = hasChangesToBeSaved;
		this.deleted = deleted;
		this.error = error;
		this.reason = reason;
		this.exception = exception;

		this.saved = !this.hasChangesToBeSaved && !this.error && !this.deleted;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("saved", saved)
				.add("deleted", deleted)
				.add("hasChangesToBeSaved", hasChangesToBeSaved)
				.add("error", error)
				.add("reason", reason)
				.toString();
	}

	@Override
	public int hashCode()
	{
		Integer hashcode = this._hashcode;
		if (hashcode == null)
		{
			hashcode = this._hashcode = Objects.hash(hasChangesToBeSaved, error, reason, exception);
		}
		return hashcode;
	}

	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(final Object obj)
	{
		final boolean ignoreReason = false;
		return equals(obj, ignoreReason);
	}

	public boolean equalsIgnoreReason(final Object obj)
	{
		final boolean ignoreReason = true;
		return equals(obj, ignoreReason);
	}

	private boolean equals(final Object obj, final boolean ignoreReason)
	{
		if (this == obj)
		{
			return true;
		}

		if (!(obj instanceof DocumentSaveStatus))
		{
			return false;
		}

		final DocumentSaveStatus other = (DocumentSaveStatus)obj;
		return hasChangesToBeSaved == other.hasChangesToBeSaved
				&& deleted == other.deleted
				&& error == other.error
				&& (ignoreReason || Objects.equals(reason, other.reason))
				&& (ignoreReason || Objects.equals(exception, other.exception))
				;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isSavedOrDeleted()
	{
		return isSaved() || isDeleted();
	}

	public DocumentSaveStatus throwIfError()
	{
		if (!error)
		{
			return this;
		}

		if (exception != null)
		{
			throw AdempiereException.wrapIfNeeded(exception);
		}
		else
		{
			throw new AdempiereException(reason);
		}
	}
}
