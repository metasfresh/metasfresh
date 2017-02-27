package de.metas.ui.web.view;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Evaluatee;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IDocumentViewSelection
{
	String getViewId();

	int getAD_Window_ID();

	long size();

	void close();
	
	int getQueryLimit();

	boolean isQueryLimitHit();

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

	default List<IDocumentView> getByIds(final Set<DocumentId> documentIds)
	{
		Check.assumeNotEmpty(documentIds, "documentIds is not empty");
		return documentIds.stream()
				.map(documentId -> {
					try
					{
						return getById(documentId);
					}
					catch (final EntityNotFoundException e)
					{
						return null;
					}
				})
				.filter(document -> document != null)
				.collect(GuavaCollectors.toImmutableList());
	}

	LookupValuesList getFilterParameterDropdown(String filterId, String filterParameterName, Evaluatee ctx);

	LookupValuesList getFilterParameterTypeahead(String filterId, String filterParameterName, String query, Evaluatee ctx);

	List<DocumentFilter> getStickyFilters();

	/**
	 * @return active filters
	 */
	List<DocumentFilter> getFilters();

	List<DocumentQueryOrderBy> getDefaultOrderBys();

	String getSqlWhereClause(Collection<DocumentId> viewDocumentIds);

	/**
	 * @return stream of actions which can be executed on this view
	 */
	Stream<WebuiRelatedProcessDescriptor> streamActions(final Collection<DocumentId> selectedDocumentIds);

	/**
	 * @return stream of quick actions which can be executed on this view
	 */
	default Stream<WebuiRelatedProcessDescriptor> streamQuickActions(final Collection<DocumentId> selectedDocumentIds)
	{
		return streamActions(selectedDocumentIds).filter(WebuiRelatedProcessDescriptor::isQuickAction);
	}

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

	<T> List<T> retrieveModelsByIds(Collection<DocumentId> documentIds, Class<T> modelClass);

	Stream<? extends IDocumentView> streamByIds(Collection<DocumentId> documentIds);

	/**
	 * Notify the view that given record has changed.
	 */
	void notifyRecordChanged(TableRecordReference recordRef);
}
