package de.metas.dao.selection.pagination;

import java.util.List;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.model.PlainContextAware;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.dao.selection.QuerySelectionHelper;
import de.metas.dao.selection.QuerySelectionHelper.UUISelection;
import de.metas.logging.LogManager;
import lombok.NonNull;

@Service
public class PaginationService
{
	private static final transient Logger logger = LogManager.getLogger(PaginationService.class);

	private final PageDescriptorRepository pageDescriptorRepository;

	public PaginationService(@NonNull final PageDescriptorRepository pageDescriptorRepository)
	{
		this.pageDescriptorRepository = pageDescriptorRepository;
	}

	public <T, ET extends T> QueryResultPage<ET> loadFirstPage(
			@NonNull final Class<ET> clazz,
			@NonNull final TypedSqlQuery<T> query,
			final int pageSize)
	{
		final UUISelection uuidSelection = QuerySelectionHelper.createUUIDSelection(query);

		final PageDescriptor firstPageDescriptor = PageDescriptor.createNew(
				uuidSelection.getUuid(),
				pageSize,
				uuidSelection.getSize(),
				uuidSelection.getTime());
		pageDescriptorRepository.save(firstPageDescriptor);

		return loadPage(
				clazz,
				firstPageDescriptor);
	}

	public <ET> QueryResultPage<ET> loadPage(
			@NonNull final Class<ET> clazz,
			@NonNull final String pageIdentifier)
	{
		final PageDescriptor currentPageDescriptor = pageDescriptorRepository.getBy(pageIdentifier);
		if (currentPageDescriptor == null)
		{
			throw new UnknownPageIdentifierException(pageIdentifier);
		}

		return loadPage(clazz, currentPageDescriptor);
	}

	private <ET> QueryResultPage<ET> loadPage(
			@NonNull final Class<ET> clazz,
			@NonNull final PageDescriptor currentPageDescriptor)
	{
		final TypedSqlQuery<ET> query = QuerySelectionHelper.createUUIDSelectionQuery(
				PlainContextAware.newWithThreadInheritedTrx(),
				clazz,
				currentPageDescriptor.getPageIdentifier().getSelectionUid());

		final int currentPageSize = currentPageDescriptor.getPageSize();

		final List<ET> items = query
				.addWhereClause(
						true /* joinByAnd */,
						QuerySelectionHelper.SELECTION_LINE_ALIAS + " > " + currentPageDescriptor.getOffset())
				.setLimit(currentPageSize)
				.list(clazz);

		final int actualPageSize = items.size();

		logger.debug("Loaded next page: bufferSize={}, offset={} => {} records",
				currentPageSize, currentPageDescriptor.getOffset(), actualPageSize);

		// True when buffer contains as much data as was required. If this flag is false then it's a good indicator that we are on last page.
		final boolean pageFullyLoaded = actualPageSize >= currentPageSize;

		final PageDescriptor nextPageDescriptor;
		if (pageFullyLoaded)
		{
			nextPageDescriptor = currentPageDescriptor.createNext();
			pageDescriptorRepository.save(nextPageDescriptor);
		}
		else
		{
			nextPageDescriptor = null;
		}

		return new QueryResultPage<ET>(
				currentPageDescriptor,
				nextPageDescriptor,
				currentPageDescriptor.getTotalSize(),
				currentPageDescriptor.getSelectionTime(),
				ImmutableList.copyOf(items));
	}
}
