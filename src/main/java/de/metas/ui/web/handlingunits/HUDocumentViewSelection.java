package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.DocumentViewAsPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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

	private final Set<DocumentPath> referencingDocumentPaths;

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

		referencingDocumentPaths = builder.getReferencingDocumentPaths();
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
		Stream<HUDocumentView> stream = getRecords().stream()
				.skip(firstRow)
				.limit(pageLength);

		final Comparator<HUDocumentView> comparator = createComparatorOrNull(orderBys);
		if (comparator != null)
		{
			stream = stream.sorted(comparator);
		}

		final List<IDocumentView> page = stream.collect(GuavaCollectors.toImmutableList());

		return DocumentViewResult.ofViewAndPage(this, firstRow, pageLength, orderBys, page);
	}

	private static final Comparator<HUDocumentView> createComparatorOrNull(final List<DocumentQueryOrderBy> orderBys)
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return null;
		}

		Comparator<HUDocumentView> comparator = null;
		for (final DocumentQueryOrderBy orderBy : orderBys)
		{
			final Comparator<HUDocumentView> orderByComparator = orderBy.<HUDocumentView> asComparator((viewRecord, fieldName) -> viewRecord.getFieldValueAsJson(fieldName));
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
	public HUDocumentView getById(final DocumentId documentId) throws EntityNotFoundException
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
		Check.assumeNotEmpty(viewDocumentIds, "viewDocumentIds is not empty");
		// NOTE: ignoring non integer IDs because those might of HUStorage records, about which we don't care
		final Set<Integer> viewDocumentIdsAsInts = DocumentId.toIntSetIgnoringNonInts(viewDocumentIds);

		return I_M_HU.COLUMNNAME_M_HU_ID + " IN " + DB.buildSqlList(viewDocumentIdsAsInts);
	}

	@Override
	public Stream<WebuiRelatedProcessDescriptor> streamActions(final Collection<DocumentId> selectedDocumentIds)
	{
		final DocumentViewAsPreconditionsContext preconditionsContext = DocumentViewAsPreconditionsContext.newInstance(this, I_M_HU.Table_Name, selectedDocumentIds);
		return processDescriptorsFactory.streamDocumentRelatedProcesses(preconditionsContext);
	}

	@Override
	public boolean hasAttributesSupport()
	{
		return true;
	}

	public Set<DocumentPath> getReferencingDocumentPaths()
	{
		return referencingDocumentPaths;
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
		documentViewsLoader.getAttributesProvider().invalidateAll();
	}

	public void addHUsAndInvalidate(final Collection<I_M_HU> husToAdd)
	{
		if (husToAdd.isEmpty())
		{
			return;
		}

		documentViewsLoader.addHUs(husToAdd);
		invalidateAll();
	}

	public void addHUAndInvalidate(final I_M_HU hu)
	{
		if (hu == null)
		{
			return;
		}

		documentViewsLoader.addHUs(ImmutableSet.of(hu));
		invalidateAll();
	}

	@Override
	public void notifyRecordsChanged(final Set<TableRecordReference> recordRefs)
	{
		final IndexedDocumentViews records = getRecordsNoLoad();
		if (records == null)
		{
			return;
		}

		final boolean containsSomeRecords = recordRefs.stream()
				.filter(recordRef -> I_M_HU.Table_Name.equals(recordRef.getTableName()))
				.map(recordRef -> DocumentId.of(recordRef.getRecord_ID()))
				.anyMatch(records::contains);
		if (!containsSomeRecords)
		{
			return;
		}

		invalidateAll();
	}

	private IndexedDocumentViews getRecords()
	{
		return _recordsSupplier.get();
	}

	private IndexedDocumentViews getRecordsNoLoad()
	{
		return _recordsSupplier.peek();
	}

	private IndexedDocumentViews retrieveRecords()
	{
		final List<HUDocumentView> recordsList = documentViewsLoader.retrieveDocumentViews();
		return new IndexedDocumentViews(recordsList);
	}

	private static final class IndexedDocumentViews
	{
		/** Top level records list */
		private final List<HUDocumentView> records;
		/** All records (included ones too) indexed by DocumentId */
		private final Map<DocumentId, HUDocumentView> allRecordsById;

		public IndexedDocumentViews(final List<HUDocumentView> records)
		{
			super();
			this.records = ImmutableList.copyOf(records);
			allRecordsById = buildRecordsByIdMap(this.records);
		}

		public HUDocumentView getById(final DocumentId documentId)
		{
			final HUDocumentView record = allRecordsById.get(documentId);
			if (record == null)
			{
				throw new EntityNotFoundException("No document found for documentId=" + documentId);
			}
			return record;
		}

		public boolean contains(final DocumentId documentId)
		{
			return allRecordsById.containsKey(documentId);
		}

		public Stream<HUDocumentView> streamByIds(final Collection<DocumentId> documentIds)
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

		public Stream<HUDocumentView> stream()
		{
			return records.stream();
		}

		public Stream<HUDocumentView> streamRecursive()
		{
			return records.stream()
					.map(row -> streamRecursive(row))
					.reduce(Stream::concat)
					.orElse(Stream.of());
		}

		private Stream<HUDocumentView> streamRecursive(HUDocumentView row)
		{
			return row.getIncludedDocuments()
					.stream()
					.map(includedRow -> streamRecursive(includedRow))
					.reduce(Stream.of(row), Stream::concat);
		}

		public long size()
		{
			return records.size();
		}

		private static ImmutableMap<DocumentId, HUDocumentView> buildRecordsByIdMap(final List<HUDocumentView> records)
		{
			if (records.isEmpty())
			{
				return ImmutableMap.of();
			}

			final ImmutableMap.Builder<DocumentId, HUDocumentView> recordsById = ImmutableMap.builder();
			records.forEach(record -> indexByIdRecursively(recordsById, record));
			return recordsById.build();
		}

		private static final void indexByIdRecursively(final ImmutableMap.Builder<DocumentId, HUDocumentView> collector, final HUDocumentView record)
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
		private Set<DocumentPath> referencingDocumentPaths;

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

		public Builder setReferencingDocumentPaths(final Set<DocumentPath> referencingDocumentPaths)
		{
			this.referencingDocumentPaths = referencingDocumentPaths;
			return this;
		}

		private Set<DocumentPath> getReferencingDocumentPaths()
		{
			return referencingDocumentPaths == null ? ImmutableSet.of() : ImmutableSet.copyOf(referencingDocumentPaths);
		}
	}

	@Override
	public Stream<HUDocumentView> streamByIds(final Collection<DocumentId> documentIds)
	{
		return getRecords().streamByIds(documentIds);
	}

	/** @return top level rows stream */
	public Stream<HUDocumentView> streamAll()
	{
		return getRecords().stream();
	}

	/** @return top level rows and included rows recursive stream */
	public Stream<HUDocumentView> streamAllRecursive()
	{
		return getRecords().streamRecursive();
	}

	@Override
	public <T> List<T> retrieveModelsByIds(final Collection<DocumentId> documentIds, final Class<T> modelClass)
	{
		final Set<Integer> huIds = getRecords()
				.streamByIds(documentIds)
				.filter(HUDocumentView::isPureHU)
				.map(HUDocumentView::getM_HU_ID)
				.collect(GuavaCollectors.toImmutableSet());
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_M_HU> hus = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, huIds)
				.create()
				.list(I_M_HU.class);

		return InterfaceWrapperHelper.createList(hus, modelClass);
	}
}
