package de.metas.ui.web.view.template;

import java.util.Collection;
import java.util.Map;

import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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

public interface IRowsData<T extends IViewRow>
{
	/* protected */ Map<DocumentId, T> getDocumentId2TopLevelRows();

	DocumentIdsSelection getDocumentIdsToInvalidate(TableRecordReferenceSet recordRefs);

	void invalidateAll();

	default void invalidate(final DocumentIdsSelection rowIds)
	{
		invalidateAll();
	}

	default int size()
	{
		return getDocumentId2TopLevelRows().size();
	}

	/* private */default Map<DocumentId, T> getDocumentId2AllRows()
	{
		return RowsDataTool.extractAllRows(getDocumentId2TopLevelRows().values());
	}

	/** @return all rows (top level and included ones) */
	default Collection<T> getAllRows()
	{
		return getDocumentId2AllRows().values();
	}

	default Collection<T> getTopLevelRows()
	{
		return getDocumentId2TopLevelRows().values();
	}

	/** @return top level or include row */
	default T getById(final DocumentId rowId) throws EntityNotFoundException
	{
		final T row = getDocumentId2AllRows().get(rowId);
		if (row == null)
		{
			throw new EntityNotFoundException("Row not found")
					.appendParametersToMessage()
					.setParameter("rowId", rowId);
		}
		return row;
	}
}
