package de.metas.ui.web.pporder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Evaluatee;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.DocumentViewAsPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.view.DocumentViewResult;
import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.event.DocumentViewChangesCollector;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.filters.DocumentFilter;

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

public class PPOrderLinesView implements IDocumentViewSelection
{
	public static final Builder builder()
	{
		return new Builder();
	}

	// services
	private final ProcessDescriptorsFactory processDescriptorsFactory;

	private final String parentViewId;
	
	private final String viewId;
	private final int adWindowId;
	private final int ppOrderId;

	private final PPOrderLinesLoader loader;
	private final ExtendedMemorizingSupplier<IndexedDocumentViews> _recordsSupplier = ExtendedMemorizingSupplier.of(() -> retrieveRecords());


	private PPOrderLinesView(final Builder builder)
	{
		// services
		processDescriptorsFactory = builder.getProcessDescriptorFactory();
		
		parentViewId = builder.getParentViewId();

		viewId = builder.getViewId();
		adWindowId = builder.getAD_Window_ID();

		loader = builder.getLoader();
		ppOrderId = loader.getPP_Order_ID();
	}
	
	@Override
	public String getParentViewId()
	{
		return parentViewId;
	}

	@Override
	public String getViewId()
	{
		return viewId;
	}

	@Override
	public int getAD_Window_ID()
	{
		return adWindowId;
	}
	
	@Override
	public String getTableName()
	{
		return null; // no particular table (i.e. we have more)
	}
	
	public int getPP_Order_ID()
	{
		return ppOrderId;
	}

	@Override
	public long size()
	{
		return getRecords().size();
	}

	@Override
	public void close()
	{
		invalidateAllNoNotify();
	}

	@Override
	public int getQueryLimit()
	{
		return -1;
	}

	@Override
	public boolean isQueryLimitHit()
	{
		return false;
	}

	@Override
	public DocumentViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		final Stream<PPOrderLineRow> stream = getRecords().stream()
				.skip(firstRow)
				.limit(pageLength);

		final List<IDocumentView> page = stream.collect(GuavaCollectors.toImmutableList());

		return DocumentViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, page);
	}

	@Override
	public PPOrderLineRow getById(final DocumentId documentId) throws EntityNotFoundException
	{
		return getRecords().getById(documentId);
	}

	@Override
	public LookupValuesList getFilterParameterDropdown(final String filterId, final String filterParameterName, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFilterParameterTypeahead(final String filterId, final String filterParameterName, final String query, final Evaluatee ctx)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<DocumentFilter> getFilters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return ImmutableList.of();
	}

	@Override
	public String getSqlWhereClause(final Collection<DocumentId> viewDocumentIds)
	{
		return null; // not supported
	}

	@Override
	public Stream<WebuiRelatedProcessDescriptor> streamActions(final Collection<DocumentId> selectedDocumentIds)
	{
		final DocumentViewAsPreconditionsContext preconditionsContext = DocumentViewAsPreconditionsContext.newInstance(this, getTableName(), selectedDocumentIds);
		return processDescriptorsFactory.streamDocumentRelatedProcesses(preconditionsContext);
		
//		if (selectedDocumentIds.isEmpty())
//		{
//			return Stream.empty();
//		}
//
//		if (selectedDocumentIds.size() != 1)
//		{
//			return Stream.empty();
//		}
//
//		final DocumentId selectedDocumentId = selectedDocumentIds.iterator().next();
//		final PPOrderLineRow record = getById(selectedDocumentId);
//		if (record == null)
//		{
//			return Stream.empty();
//		}
//
//		final DocumentViewAsPreconditionsContext preconditionsContext = DocumentViewAsPreconditionsContext.newInstance(this, record.getTableName(), ImmutableSet.of(record.getDocumentId()));
//		return processDescriptorsFactory.streamDocumentRelatedProcesses(preconditionsContext);
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return false;
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final Collection<DocumentId> documentIds, final Class<T> modelClass)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<PPOrderLineRow> streamByIds(final Collection<DocumentId> documentIds)
	{
		return getRecords().streamByIds(documentIds);
	}
	
	/** @return top level rows and included rows recursive stream */
	public Stream<PPOrderLineRow> streamAllRecursive()
	{
		return getRecords().streamRecursive();
	}


	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		// TODO Auto-generated method stub
	}

	public void invalidateAll()
	{
		invalidateAllNoNotify();

		DocumentViewChangesCollector.getCurrentOrAutoflush()
				.collectFullyChanged(this);
	}

	private void invalidateAllNoNotify()
	{
		_recordsSupplier.forget();
	}

	private IndexedDocumentViews getRecords()
	{
		return _recordsSupplier.get();
	}

	private IndexedDocumentViews retrieveRecords()
	{
		final List<PPOrderLineRow> recordsList = loader.retrieveRecords(viewId);
		return new IndexedDocumentViews(recordsList);
	}

	//
	//
	//
	private static final class IndexedDocumentViews
	{
		/** Top level records list */
		private final List<PPOrderLineRow> records;
		/** All records (included ones too) indexed by DocumentId */
		private final Map<DocumentId, PPOrderLineRow> allRecordsById;

		public IndexedDocumentViews(final List<PPOrderLineRow> records)
		{
			super();
			this.records = ImmutableList.copyOf(records);
			allRecordsById = buildRecordsByIdMap(this.records);
		}

		public PPOrderLineRow getById(final DocumentId documentId)
		{
			final PPOrderLineRow record = allRecordsById.get(documentId);
			if (record == null)
			{
				throw new EntityNotFoundException("No document found for documentId=" + documentId);
			}
			return record;
		}

		public Stream<PPOrderLineRow> streamByIds(final Collection<DocumentId> documentIds)
		{
			if (documentIds == null || documentIds.isEmpty())
			{
				return Stream.empty();
			}

			return documentIds.stream()
					.distinct()
					.map(documentId -> allRecordsById.get(documentId))
					.filter(document -> document != null);
		}
		
		public Stream<PPOrderLineRow> stream()
		{
			return records.stream();
		}
		
		public Stream<PPOrderLineRow> streamRecursive()
		{
			return records.stream()
					.map(row -> streamIncludedRowsRecursive(row))
					.reduce(Stream::concat)
					.orElse(Stream.of());
		}

		private Stream<PPOrderLineRow> streamIncludedRowsRecursive(PPOrderLineRow row)
		{
			return row.getIncludedDocuments()
					.stream()
					.map(includedRow -> streamIncludedRowsRecursive(includedRow))
					.reduce(Stream.of(row), Stream::concat);
		}


		public long size()
		{
			return records.size();
		}

		private static ImmutableMap<DocumentId, PPOrderLineRow> buildRecordsByIdMap(final List<PPOrderLineRow> records)
		{
			if (records.isEmpty())
			{
				return ImmutableMap.of();
			}

			final ImmutableMap.Builder<DocumentId, PPOrderLineRow> recordsById = ImmutableMap.builder();
			records.forEach(record -> indexByIdRecursively(recordsById, record));
			return recordsById.build();
		}

		private static final void indexByIdRecursively(final ImmutableMap.Builder<DocumentId, PPOrderLineRow> collector, final PPOrderLineRow record)
		{
			collector.put(record.getDocumentId(), record);
			record.getIncludedDocuments()
					.forEach(includedRecord -> indexByIdRecursively(collector, includedRecord));
		}
	}

	//
	//
	//
	public static final class Builder
	{
		private String parentViewId;
		
		private String viewId;
		private int adWindowId;

		private ProcessDescriptorsFactory processDescriptorsFactory;
		private PPOrderLinesLoader loader;

		private Builder()
		{
			super();
		}

		public PPOrderLinesView build()
		{
			return new PPOrderLinesView(this);
		}
		
		public Builder setParentViewId(String parentViewId)
		{
			this.parentViewId = parentViewId;
			return this;
		}
		
		private String getParentViewId()
		{
			return parentViewId;
		}

		public Builder setViewId(final String viewId)
		{
			this.viewId = viewId;
			return this;
		}

		public String getViewId()
		{
			return viewId;
		}

		public Builder setAD_Window_ID(final int adWindowId)
		{
			this.adWindowId = adWindowId;
			return this;
		}

		private int getAD_Window_ID()
		{
			return adWindowId;
		}

		public Builder setRecords(final PPOrderLinesLoader loader)
		{
			this.loader = loader;
			return this;
		}

		private PPOrderLinesLoader getLoader()
		{
			Preconditions.checkNotNull(loader, "loader is null");
			return loader;
		}

		public Builder setServices(final ProcessDescriptorsFactory processDescriptorsFactory)
		{
			this.processDescriptorsFactory = processDescriptorsFactory;
			return this;
		}

		private ProcessDescriptorsFactory getProcessDescriptorFactory()
		{
			Check.assumeNotNull(processDescriptorsFactory, "Parameter processDescriptorsFactory is not null");
			return processDescriptorsFactory;
		}
	}
}
