package de.metas.ui.web.material.cockpit;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.i18n.ITranslatableString;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.material.cockpit.process.MD_Cockpit_DocumentDetail_Display;
import de.metas.ui.web.process.view.ViewActionDescriptorsFactory;
import de.metas.ui.web.process.view.ViewActionDescriptorsList;
import de.metas.ui.web.view.AbstractCustomView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.NonNull;

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

public class MaterialCockpitView extends AbstractCustomView<MaterialCockpitRow>
{
	private final ImmutableList<DocumentFilter> filters;

	private final List<RelatedProcessDescriptor> relatedProcessDescriptors;

	private final ExtendedMemorizingSupplier<Map<TableRecordReference, Collection<DocumentId>>> tableRecordReference2DocumentId;

	@Builder
	private MaterialCockpitView(
			@NonNull final ViewId viewId,
			@NonNull final ITranslatableString description,
			@NonNull final Supplier<List<MaterialCockpitRow>> rowsListSupplier,
			@NonNull final ImmutableList<DocumentFilter> filters,
			@NonNull final RelatedProcessDescriptor relatedProcessDescriptor)
	{
		super(viewId,
				description,
				rowsListSupplier);

		this.tableRecordReference2DocumentId = ExtendedMemorizingSupplier.of(() -> extractTableRecordReference2DocumentId2());

		this.filters = filters;
		this.relatedProcessDescriptors = ImmutableList.of(relatedProcessDescriptor);
	}

	private Map<TableRecordReference, Collection<DocumentId>> extractTableRecordReference2DocumentId2()
	{
		final ListMultimap<TableRecordReference, DocumentId> recordReference2DocumentId = ArrayListMultimap.create();

		final Map<DocumentId, MaterialCockpitRow> rows = getMainRowsAndSubRows();
		for (final Entry<DocumentId, MaterialCockpitRow> row : rows.entrySet())
		{
			final MaterialCockpitRow materialCockpitRow = row.getValue();

			final DocumentId documentId = row.getKey();
			materialCockpitRow
					.getAllIncludedCockpitRecordIds()
					.forEach(cockpitRecordId -> recordReference2DocumentId
							.put(TableRecordReference.of(I_MD_Cockpit.Table_Name, cockpitRecordId), documentId));

			materialCockpitRow
					.getAllIncludedStockRecordIds()
					.forEach(stockRecordId -> recordReference2DocumentId
							.put(TableRecordReference.of(I_MD_Stock.Table_Name, stockRecordId), documentId));

		}

		return recordReference2DocumentId.asMap();
	}

	/**
	 * @return {@code null}, because each record of this view is based on > 1 tables.
	 */
	@Override
	public String getTableNameOrNull(DocumentId documentId)
	{
		return null;
	}

	@Override
	public List<DocumentFilter> getFilters()
	{
		return filters;
	}

	@Override
	public void notifyRecordsChanged(@NonNull final Set<TableRecordReference> recordRefs)
	{
		final ImmutableSet<TableRecordReference> recordRefsWithRelatedTable = filterForRelevantTableName(recordRefs);
		if (recordRefsWithRelatedTable.isEmpty())
		{
			return;
		}

		final ImmutableList<DocumentId> changedDocumentIds = extractDocumentIds(recordRefs);
		if (changedDocumentIds.isEmpty())
		{
			return; // nothing to do
		}

		invalidateRowSuppliers();
		ViewChangesCollector
				.getCurrentOrAutoflush()
				.collectRowsChanged(this, DocumentIdsSelection.of(changedDocumentIds));
	}

	private ImmutableSet<TableRecordReference> filterForRelevantTableName(
			@NonNull final Set<TableRecordReference> recordRefs)
	{
		final Predicate<TableRecordReference> isRelatedTable = //
				ref -> I_MD_Cockpit.Table_Name.equals(ref.getTableName())
						|| I_MD_Stock.Table_Name.equals(ref.getTableName());
		final ImmutableSet<TableRecordReference> recordRefsWithRelatedTable = recordRefs.stream()
				.filter(isRelatedTable)
				.collect(ImmutableSet.toImmutableSet());
		return recordRefsWithRelatedTable;
	}

	private ImmutableList<DocumentId> extractDocumentIds(
			@NonNull final Set<TableRecordReference> recordRefs)
	{
		final Map<TableRecordReference, Collection<DocumentId>> recordReference2DocumentId = //
				tableRecordReference2DocumentId.get();

		final ImmutableList<DocumentId> changedDocumentIds = recordRefs.stream()
				.map(recordReference2DocumentId::get)
				.filter(Objects::nonNull)
				.flatMap(documentIds -> documentIds.stream())
				.collect(ImmutableList.toImmutableList());
		return changedDocumentIds;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return relatedProcessDescriptors;
	}

	@Override
	public ViewActionDescriptorsList getActions()
	{
		return ViewActionDescriptorsFactory.instance
				.getFromClass(MD_Cockpit_DocumentDetail_Display.class);
	}

}
