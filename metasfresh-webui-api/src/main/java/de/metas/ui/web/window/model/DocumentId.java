package de.metas.ui.web.window.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class DocumentId
{
	private static final int NEW_ID = -1;
	public static final String NEW_ID_STRING = "NEW";
	public static final DocumentId NEW = new DocumentId(NEW_ID);

	public static final DocumentId of(final String idStr)
	{
		if (NEW_ID_STRING.equals(idStr))
		{
			return NEW;
		}

		final int idInt = Integer.parseInt(idStr);
		return of(idInt);
	}

	public static final DocumentId of(final int idInt)
	{
		if (idInt == NEW_ID)
		{
			return NEW;
		}

		return new DocumentId(idInt);
	}

	private final int idInt;

	private DocumentId(final int idInt)
	{
		super();
		this.idInt = idInt;
	}

	@Override
	public String toString()
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

		if (!(obj instanceof DocumentId))
		{
			return false;
		}

		final DocumentId other = (DocumentId)obj;
		return idInt == other.idInt;
	}

	public int toInt()
	{
		return idInt;
	}

	public boolean isNew()
	{
		return idInt == NEW_ID;
	}
}
