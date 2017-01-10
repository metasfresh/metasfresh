package de.metas.ui.web.view;

import java.util.List;
import java.util.stream.Stream;

import org.compiere.util.Evaluatee;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.filters.DocumentFilter;

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

public interface IDocumentViewSelection
{
	String getViewId();

	int getAD_Window_ID();

	long size();
	
	void close();

	DocumentViewResult getPage(int firstRow, int pageLength, List<DocumentQueryOrderBy> orderBys);

	default DocumentViewResult getPage(final int firstRow, final int pageLength, final String orderBysListStr)
	{
		final List<DocumentQueryOrderBy> orderBys = DocumentQueryOrderBy.parseOrderBysList(orderBysListStr);
		return getPage(firstRow, pageLength, orderBys);
	}
	
	IDocumentView getById(DocumentId documentId) throws EntityNotFoundException;

	default IDocumentView getById(final int documentIdInt) throws EntityNotFoundException
	{
		return getById(DocumentId.of(documentIdInt));
	}


	LookupValuesList getFilterParameterDropdown(String filterId, String filterParameterName, Evaluatee ctx);

	LookupValuesList getFilterParameterTypeahead(String filterId, String filterParameterName, String query, Evaluatee ctx);

	List<DocumentFilter> getStickyFilters();

	/**
	 * @return active filters
	 */
	List<DocumentFilter> getFilters();

	List<DocumentQueryOrderBy> getDefaultOrderBys();

	String getSqlWhereClause(List<Integer> viewDocumentIds);

	/** @return stream of actions which can be executed on this view */
	Stream<ProcessDescriptor> streamActions();

	boolean hasAttributesSupport();

	default IDocumentViewSelection assertWindowIdMatches(final int expectedWindowId)
	{
		// NOTE: for now, if the windowId is not provided, let's not validate it because deprecate API cannot provide the windowId
		if (expectedWindowId <= 0)
		{
			return this;
		}

		if (expectedWindowId != getAD_Window_ID())
		{
			throw new IllegalArgumentException("View's windowId is not matching the expected one."
					+ "\n Expected windowId: " + expectedWindowId
					+ "\n View: " + this);
		}

		return this;
	}
}
