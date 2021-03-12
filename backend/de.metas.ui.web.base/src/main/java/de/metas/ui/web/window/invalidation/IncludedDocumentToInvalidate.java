package de.metas.ui.web.window.invalidation;

import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
public final class IncludedDocumentToInvalidate
{
	@Getter
	private final String tableName;
	private boolean invalidateAll;
	private final HashSet<Integer> recordIds = new HashSet<>();

	IncludedDocumentToInvalidate(@NonNull final String tableName)
	{
		this.tableName = tableName;
	}

	public void invalidateAll()
	{
		if (invalidateAll)
		{
			return;
		}
		else
		{
			invalidateAll = true;
			recordIds.clear();
		}
	}

	public void addRecordId(final int recordId)
	{
		if (!invalidateAll)
		{
			recordIds.add(recordId);
		}
	}

	public DocumentIdsSelection toDocumentIdsSelection()
	{
		return invalidateAll
				? DocumentIdsSelection.ALL
				: DocumentIdsSelection.ofIntSet(recordIds);
	}

	IncludedDocumentToInvalidate combine(@NonNull final IncludedDocumentToInvalidate other)
	{
		Check.assumeEquals(tableName, other.tableName, "tableName");
		this.invalidateAll = this.invalidateAll || other.invalidateAll;

		if(invalidateAll)
		{
			this.recordIds.clear();
		}
		else
		{
			this.recordIds.addAll(other.recordIds);
		}

		return this;
	}
}
