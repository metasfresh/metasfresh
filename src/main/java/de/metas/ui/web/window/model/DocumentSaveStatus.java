package de.metas.ui.web.window.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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

	public static final DocumentSaveStatus notSaved(final DocumentValidStatus invalidState)
	{
		final boolean hasChangesToBeSaved = true;
		final boolean error = false;
		final String reason = invalidState.getReason();
		return new DocumentSaveStatus(hasChangesToBeSaved, error, reason);
	}

	public static final DocumentSaveStatus notSaved(final Exception exception)
	{
		final boolean hasChangesToBeSaved = true;
		final boolean error = true;
		final String reason = exception.getLocalizedMessage();
		return new DocumentSaveStatus(hasChangesToBeSaved, error, reason);
	}

	public static final DocumentSaveStatus notSavedJustCreated()
	{
		return STATUS_NotSavedJustCreated;
	}

	public static final DocumentSaveStatus savedJustLoaded()
	{
		return STATUS_SavedJustLoaded;
	}

	private static final DocumentSaveStatus STATUS_Unknown = new DocumentSaveStatus(true, false, "not yet checked");
	private static final DocumentSaveStatus STATUS_Saved = new DocumentSaveStatus(false, false, null);
	private static final DocumentSaveStatus STATUS_NotSavedJustCreated = new DocumentSaveStatus(true, false, "new");
	private static final DocumentSaveStatus STATUS_SavedJustLoaded = new DocumentSaveStatus(false, false, "just loaded");

	@JsonProperty("saved")
	private final boolean saved;
	@JsonProperty("hasChanges")
	private final boolean hasChangesToBeSaved;
	@JsonProperty("error")
	private final boolean error;
	@JsonProperty("reason")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String reason;

	private transient Integer _hashcode;

	private DocumentSaveStatus(final boolean hasChangesToBeSaved, final boolean error, final String reason)
	{
		super();
		
		this.saved = !hasChangesToBeSaved && !error;

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
				&& error == other.error
				&& Objects.equals(reason, other.reason);
	}

	public boolean isSaved()
	{
		return !hasChangesToBeSaved && !error;
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
