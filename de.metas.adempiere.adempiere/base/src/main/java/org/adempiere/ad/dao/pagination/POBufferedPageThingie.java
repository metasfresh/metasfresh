package org.adempiere.ad.dao.pagination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.adempiere.ad.dao.impl.QuerySelectionHelper;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.PlainContextAware;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.NonNull;

public final class POBufferedPageThingie<T, ET extends T>
{
	private static final transient Logger logger = LogManager.getLogger(POBufferedPageThingie.class);

	private final Class<ET> clazz;

	// TODO store this stuff in DB!
	private static final Map<String, PageDescriptor> pageDescriptors = new HashMap<>();

	public POBufferedPageThingie(@NonNull final Class<ET> clazz)
	{
		if (!DB.getDatabase().isPagingSupported())
		{
			throw new DBException("Database paging support is required in order to have " + POBufferedPageThingie.class + " working");
		}

		this.clazz = clazz;
	}

	public QueryResultPage<ET> loadFirstPage(final TypedSqlQuery<T> query, final int pageSize)
	{
		final String querySelectionUUID = UUID.randomUUID().toString();
		QuerySelectionHelper.createUUIDSelection(query, querySelectionUUID);

		final PageDescriptor firstPageDescriptor = PageDescriptor.createNew(querySelectionUUID, 50);

		return loadPage(firstPageDescriptor);
	}

	public QueryResultPage<ET> loadPage(String pageId)
	{
		final PageDescriptor currentPageDescriptor = pageDescriptors.remove(pageId);
		if (currentPageDescriptor == null)
		{
			throw new PaginationException("wtf");
		}

		return loadPage(currentPageDescriptor);
	}

	private QueryResultPage<ET> loadPage(@NonNull final PageDescriptor currentPageDescriptor)
	{
		final TypedSqlQuery<ET> query = QuerySelectionHelper.createUUIDSelectionQuery(
				PlainContextAware.newWithThreadInheritedTrx(),
				clazz,
				currentPageDescriptor.getSelectionUid());

		final int currentPageSize = currentPageDescriptor.getSize();
		query.setLimit(currentPageSize);
		final TypedSqlQuery<ET> queryToUse = query.addWhereClause(
				true /* joinByAnd */,
				QuerySelectionHelper.SELECTION_LINE_ALIAS + " > " + currentPageDescriptor.getOffset());

		final List<ET> items = queryToUse.list(clazz);
		final int actualPageSize = items.size();

		logger.debug("Loaded next page: bufferSize={}, offset={} => {} records",
				currentPageSize, currentPageDescriptor.getOffset(), actualPageSize);

		// True when buffer contains as much data as was required. If this flag is false then it's a good indicator that we are on last page.
		final boolean pageFullyLoaded = actualPageSize >= currentPageSize;

		final PageDescriptor newPageDescriptor;
		if (pageFullyLoaded)
		{
			newPageDescriptor = currentPageDescriptor.createNext();
			pageDescriptors.put(newPageDescriptor.getCombinedUid(), newPageDescriptor);
		}
		else
		{
			newPageDescriptor = null;
		}

		return new QueryResultPage<ET>(
				newPageDescriptor,
				currentPageDescriptor,
				items);
	}
}
