package de.metas.ui.web.handlingunits;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.process.descriptor.RelatedProcessDescriptorWrapper;
import de.metas.ui.web.view.DocumentViewResult;
import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.event.DocumentViewChangesCollector;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class HUDocumentViewSelection implements IDocumentViewSelection
{
	public static final Builder builder()
	{
		return new Builder();
	}

	// services
	private final ProcessDescriptorsFactory processDescriptorsFactory;

	private final String viewId;
	private final int adWindowId;

	private final DocumentPath referencingDocumentPath;

	private final HUDocumentViewLoader documentViewsLoader;
	private final ExtendedMemorizingSupplier<IndexedDocumentViews> _recordsSupplier = ExtendedMemorizingSupplier.of(() -> retrieveRecords());

	private HUDocumentViewSelection(final Builder builder)
	{
		super();

		// services
		processDescriptorsFactory = builder.getProcessDescriptorFactory();

		viewId = builder.getViewId();
		adWindowId = builder.getAD_Window_ID();

		documentViewsLoader = builder.getDocumentViewsLoader();

		referencingDocumentPath = builder.getReferencingDocumentPath();
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
	public long size()
	{
		return getRecords().size();
	}

	@Override
	public void close()
	{
	}

	@Override
	public DocumentViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		Stream<IDocumentView> stream = getRecords().stream()
				.skip(firstRow)
				.limit(pageLength);

		final Comparator<IDocumentView> comparator = createComparatorOrNull(orderBys);
		if (comparator != null)
		{
			stream = stream.sorted(comparator);
		}

		final List<IDocumentView> page = stream.collect(GuavaCollectors.toImmutableList());

		return DocumentViewResult.of(this, firstRow, pageLength, orderBys, page);
	}

	private static final Comparator<IDocumentView> createComparatorOrNull(final List<DocumentQueryOrderBy> orderBys)
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return null;
		}

		Comparator<IDocumentView> comparator = null;
		for (final DocumentQueryOrderBy orderBy : orderBys)
		{
			final Comparator<IDocumentView> orderByComparator = orderBy.<IDocumentView> asComparator((viewRecord, fieldName) -> viewRecord.getFieldValueAsJson(fieldName));
			if (comparator == null)
			{
				comparator = orderByComparator;
			}
			else
			{
				comparator = comparator.thenComparing(orderByComparator);
			}
		}

		return comparator;
	}

	@Override
	public IDocumentView getById(final DocumentId documentId) throws EntityNotFoundException
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
	public String getSqlWhereClause(final List<Integer> viewDocumentIds)
	{
		Check.assumeNotEmpty(viewDocumentIds, "viewDocumentIds is not empty");
		return I_M_HU.COLUMNNAME_M_HU_ID + " IN (" + DB.buildSqlList(viewDocumentIds) + ")";
	}

	@Override
	public Stream<RelatedProcessDescriptorWrapper> streamActions()
	{
		return processDescriptorsFactory.streamDocumentRelatedProcesses(I_M_HU.Table_Name);
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return true;
	}

	public DocumentPath getReferencingDocumentPath()
	{
		return referencingDocumentPath;
	}

	public void invalidateAll()
	{
		final DocumentViewChangesCollector changesCollector = DocumentViewChangesCollector.getCurrentOrNull();
		if (changesCollector != null)
		{
			changesCollector.collectFullyChanged(this);
		}
		
		_recordsSupplier.forget();
		documentViewsLoader.getAttributesProvider().invalidateAll();
	}

	private IndexedDocumentViews getRecords()
	{
		return _recordsSupplier.get();
	}

	private IndexedDocumentViews retrieveRecords()
	{
		final List<IDocumentView> recordsList = documentViewsLoader.retrieveDocumentViews();
		return new IndexedDocumentViews(recordsList);
	}

	private static final class IndexedDocumentViews
	{
		/** Top level records list */
		private final List<IDocumentView> records;
		/** All records (included ones too) indexed by DocumentId */
		private final Map<DocumentId, IDocumentView> allRecordsById;

		public IndexedDocumentViews(final List<IDocumentView> records)
		{
			super();
			this.records = ImmutableList.copyOf(records);
			allRecordsById = buildRecordsByIdMap(this.records);
		}

		public IDocumentView getById(final DocumentId documentId)
		{
			final IDocumentView record = allRecordsById.get(documentId);
			if (record == null)
			{
				throw new EntityNotFoundException("No document found for documentId=" + documentId);
			}
			return record;
		}

		public Stream<IDocumentView> stream()
		{
			return records.stream();
		}

		public long size()
		{
			return records.size();
		}

		private static ImmutableMap<DocumentId, IDocumentView> buildRecordsByIdMap(final List<IDocumentView> records)
		{
			if (records.isEmpty())
			{
				return ImmutableMap.of();
			}

			final ImmutableMap.Builder<DocumentId, IDocumentView> recordsById = ImmutableMap.builder();
			records.forEach(record -> indexByIdRecursively(recordsById, record));
			return recordsById.build();
		}

		private static final void indexByIdRecursively(final ImmutableMap.Builder<DocumentId, IDocumentView> collector, final IDocumentView record)
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
		private String viewId;
		private int adWindowId;
		private DocumentPath referencingDocumentPath;

		private ProcessDescriptorsFactory processDescriptorsFactory;
		private HUDocumentViewLoader documentViewsLoader;

		private Builder()
		{
			super();
		}

		public HUDocumentViewSelection build()
		{
			return new HUDocumentViewSelection(this);
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

		public Builder setRecords(final HUDocumentViewLoader documentViewsLoader)
		{
			this.documentViewsLoader = documentViewsLoader;
			return this;
		}

		private HUDocumentViewLoader getDocumentViewsLoader()
		{
			Check.assumeNotNull(documentViewsLoader, "Parameter documentViewsLoader is not null");
			return documentViewsLoader;
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

		public Builder setReferencingDocumentPath(final DocumentPath referencingDocumentPath)
		{
			this.referencingDocumentPath = referencingDocumentPath;
			return this;
		}

		private DocumentPath getReferencingDocumentPath()
		{
			return referencingDocumentPath;
		}
	}

}
