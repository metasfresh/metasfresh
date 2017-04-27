package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Buffer which contains all {@link HUEditorRow}s required for a given {@link HUEditorView} instance.
 * 
 * Implementations of this interface are responsible for fetching the {@link HUEditorRow}s and (maybe)caching them.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface HUEditorViewBuffer
{
	long size();

	void invalidateAll();

	/** @return top level rows and included rows recursive stream */
	Stream<HUEditorRow> streamAllRecursive();

	Stream<HUEditorRow> streamByIds(Collection<DocumentId> rowIds);

	Stream<HUEditorRow> streamPage(int firstRow, int pageLength, List<DocumentQueryOrderBy> orderBys);

	HUEditorRow getById(DocumentId rowId) throws EntityNotFoundException;

	boolean addHUIds(Collection<Integer> huIdsToAdd);

	boolean removeHUIds(Collection<Integer> huIdsToRemove);

	boolean containsAnyOfHUIds(Collection<Integer> huIdsToCheck);

}
