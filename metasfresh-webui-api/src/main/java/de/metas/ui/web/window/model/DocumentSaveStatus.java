package de.metas.ui.web.window.model;

import java.util.Objects;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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
		final String reason = "Invalid status: " + invalidState;
		return new DocumentSaveStatus(hasChangesToBeSaved, error, reason);
	}

	public static final DocumentSaveStatus notSaved(final Exception exception)
	{
		final boolean hasChangesToBeSaved = true;
		final boolean error = true;
		final String reason = "Error: " + exception.getLocalizedMessage();
		return new DocumentSaveStatus(hasChangesToBeSaved, error, reason);
	}

	private static final DocumentSaveStatus STATUS_Unknown = new DocumentSaveStatus(true, false, "not yet checked");
	private static final DocumentSaveStatus STATUS_Saved = new DocumentSaveStatus(false, false, null);

	private final boolean hasChangesToBeSaved;
	private final boolean error;
	private final String reason;

	private transient Integer _hashcode;

	private DocumentSaveStatus(final boolean hasChangesToBeSaved, final boolean error, final String reason)
	{
		super();
		this.hasChangesToBeSaved = hasChangesToBeSaved;
		this.error = error;
		this.reason = reason;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("hasChangesToBeSaved", hasChangesToBeSaved)
				.add("error", error)
				.add("reason", reason)
				.toString();
	}

	public String toJson()
	{
		return toString();
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
