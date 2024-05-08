package de.metas.ui.web.window.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import lombok.Builder;

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
	public static final DocumentSaveStatus unknown()
	{
		return STATUS_Unknown;
	}

	public static final DocumentSaveStatus saved()
	{
		return STATUS_Saved;
	}

	public static final DocumentSaveStatus deleted()
	{
		return STATUS_Deleted;
	}

	public static final DocumentSaveStatus notSaved(final DocumentValidStatus invalidState)
	{
		final String reason = invalidState.getReason();
		return builder().hasChangesToBeSaved(true).error(false).reason(reason).build();
	}

	public static final DocumentSaveStatus notSaved(final Exception exception)
	{
		final String reason = exception.getLocalizedMessage();
		return builder().hasChangesToBeSaved(true).error(true).reason(reason).build();
	}

	public static final DocumentSaveStatus notSavedJustCreated()
	{
		return STATUS_NotSavedJustCreated;
	}

	public static final DocumentSaveStatus savedJustLoaded()
	{
		return STATUS_SavedJustLoaded;
	}

	private static final DocumentSaveStatus STATUS_Unknown = builder().hasChangesToBeSaved(true).error(false).reason("not yet checked").build();
	private static final DocumentSaveStatus STATUS_Saved = builder().hasChangesToBeSaved(false).error(false).build();
	private static final DocumentSaveStatus STATUS_Deleted = builder().hasChangesToBeSaved(false).deleted(true).error(false).build(); // FIXME: it's same as Saved!
	private static final DocumentSaveStatus STATUS_NotSavedJustCreated = builder().hasChangesToBeSaved(true).error(false).reason("new").build();
	private static final DocumentSaveStatus STATUS_SavedJustLoaded = builder().hasChangesToBeSaved(false).error(false).reason("just loaded").build();

	@JsonProperty("saved")
	private final boolean saved;
	@JsonProperty("hasChanges")
	private final boolean hasChangesToBeSaved;
	@JsonProperty("error")
	private final boolean error;
	@JsonProperty("reason")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String reason;

	private final boolean deleted;

	private transient Integer _hashcode;

	@Builder
	private DocumentSaveStatus(
			final boolean hasChangesToBeSaved,
			final boolean deleted,
			final boolean error,
			final String reason)
	{
		this.saved = !hasChangesToBeSaved && !error && !deleted;
		this.deleted = deleted;

		this.hasChangesToBeSaved = hasChangesToBeSaved;
		this.error = error;
		this.reason = reason;
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
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(hasChangesToBeSaved, error, reason);
		}
		return _hashcode;
	}

	@Override
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
				&& (ignoreReason || Objects.equals(reason, other.reason));
	}

	public boolean isSaved()
	{
		return saved;
	}

	public boolean isDeleted()
	{
		return deleted;
	}

	public boolean isSavedOrDeleted()
	{
		return isSaved() || isDeleted();
	}

	public boolean hasChangesToBeSaved()
	{
		return hasChangesToBeSaved;
	}

	public boolean isError()
	{
		return error;
	}

	public String getReason()
	{
		return reason;
	}
}
