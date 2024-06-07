/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.material.cockpit;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import de.metas.material.cockpit.ProductWithDemandSupplyCollection;
import de.metas.material.cockpit.QtyDemandSupplyRepository;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.ProductId;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory.CreateRowsRequest.CreateRowsRequestBuilder;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.RowsDataTool;
import de.metas.ui.web.view.template.SynchronizedRowsIndexHolder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import de.metas.util.async.Debouncer;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Note: the tricky thing is that we can have rows that have neither an MD_Cockpit nor an MD_Stock record.
 * We just show an "empty" row for them, just for a particular product that was added to the MaterialCockpitFactory.
 */
public class MaterialCockpitRowsData implements IRowsData<MaterialCockpitRow>
{
	private final static String SYS_CONFIG_DEBOUNCER_DELAY_MILLISECONDS = "de.metas.ui.web.material.cockpit.MaterialCockpitRowsDataDebouncer.delayInMillis";

    private final MaterialCockpitDetailsRowAggregation detailsRowAggregation;
	private final MaterialCockpitRowFactory materialCockpitRowFactory;
	private final SynchronizedRowsIndexHolder<MaterialCockpitRow> rowsHolder;
	private final QtyDemandSupplyRepository qtyDemandSupplyRepository;

	private final Debouncer<DocumentIdsSelection> debouncer;

	/**
	 * Every row has a product, and so does every MD_Stock and MD_Candidate.
	 */
	private final Multimap<ProductId, DocumentId> productId2DocumentIds;

	public MaterialCockpitRowsData(
			@NonNull final MaterialCockpitDetailsRowAggregation detailsRowAggregation,
			@NonNull final MaterialCockpitRowFactory materialCockpitRowFactory,
			@NonNull final List<MaterialCockpitRow> rows,
			@NonNull final QtyDemandSupplyRepository qtyDemandSupplyRepository)
	{
		this.detailsRowAggregation = detailsRowAggregation;
		this.materialCockpitRowFactory = materialCockpitRowFactory;

		this.rowsHolder = SynchronizedRowsIndexHolder.of(rows);
		final ImmutableMultimap.Builder<ProductId, DocumentId> productIdDocumentIdBuilder = new ImmutableMultimap.Builder<>();

		for (final MaterialCockpitRow row : rows)
		{
			productIdDocumentIdBuilder.put(row.getProductId(), row.getId());
		}
		this.productId2DocumentIds = productIdDocumentIdBuilder.build();
		this.qtyDemandSupplyRepository = qtyDemandSupplyRepository;

        this.debouncer = Debouncer.<DocumentIdsSelection>builder()
				.name(MaterialCockpitRowsData.class.getSimpleName() + "-debouncer")
				.bufferMaxSize(500)
				.delayInMillis(Services.get(ISysConfigBL.class).getIntValue(SYS_CONFIG_DEBOUNCER_DELAY_MILLISECONDS, 1000))
				.distinct(true)
				.consumer(collectedItems -> {
					final DocumentIdsSelection combinedItems = collectedItems.stream()
							.reduce(DocumentIdsSelection.EMPTY, DocumentIdsSelection::addAll);

					invalidateNow(combinedItems);
				})
				.build();
	}

	@Override
	public Map<DocumentId, MaterialCockpitRow> getDocumentId2TopLevelRows()
	{
		return rowsHolder.getDocumentId2TopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(@NonNull final TableRecordReferenceSet recordRefs)
	{
		final List<DocumentId> documentIds = new ArrayList<>();
		for (final TableRecordReference recordRef : recordRefs)
		{
			if (recordRef.getTableName().equals(I_MD_Cockpit.Table_Name))
			{
				final I_MD_Cockpit cockpitRecord = recordRef.getModel(I_MD_Cockpit.class);
				final ProductId productId = ProductId.ofRepoId(cockpitRecord.getM_Product_ID());
				documentIds.addAll(productId2DocumentIds.get(productId));
			}
			if (recordRef.getTableName().equals(I_MD_Stock.Table_Name))
			{
				final I_MD_Stock stockRecord = recordRef.getModel(I_MD_Stock.class);
				final ProductId productId = ProductId.ofRepoId(stockRecord.getM_Product_ID());
				documentIds.addAll(productId2DocumentIds.get(productId));
			}
		}
		return DocumentIdsSelection.of(documentIds);
	}

	@Override
	public void invalidateAll()
	{
		invalidate(DocumentIdsSelection.ALL);
	}

	@Override
	public void invalidate(@NonNull final DocumentIdsSelection rowIds)
	{
		debouncer.add(rowIds);
	}

	private void invalidateNow(@NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return;
		}

		final ArrayList<MaterialCockpitRow> rowsToInvalidate = extractRows(rowsHolder.getDocumentId2TopLevelRows(), rowIds);

		final Map<LocalDate, CreateRowsRequestBuilder> builders = new HashMap<>();

		final ProductWithDemandSupplyCollection productWithDemandSupplyCollection = MaterialCockpitUtil.isI_QtyDemand_QtySupply_VActive()
				? loadQuantitiesRecords(rowsToInvalidate)
				: ProductWithDemandSupplyCollection.of(ImmutableMap.of());

		for (final MaterialCockpitRow row : rowsToInvalidate)
		{
			final CreateRowsRequestBuilder builder = builders.computeIfAbsent(row.getDate(), this::createNewBuilder);

			final List<I_MD_Cockpit> cockpitRecords = loadCockpitRecords(row.getAllIncludedCockpitRecordIds());
			builder.cockpitRecords(cockpitRecords);

			final List<I_MD_Stock> stockRecords = loadStockRecords(row.getAllIncludedStockRecordIds());
			builder.stockRecords(stockRecords);

			final ProductId productId = row.getProductId();

			builder.quantitiesRecords(productWithDemandSupplyCollection.getByProductId(productId));

			builder.productIdToListEvenIfEmpty(productId);
		}

		final List<MaterialCockpitRow> newRows = new ArrayList<>();
		for (final CreateRowsRequestBuilder builder : builders.values())
		{
			newRows.addAll(materialCockpitRowFactory.createRows(builder.build()));
		}

		rowsHolder.compute(rows -> rows.replacingRows(rowIds, newRows));
	}

	@NonNull
	private static ArrayList<MaterialCockpitRow> extractRows(
			@NonNull final ImmutableMap<DocumentId, MaterialCockpitRow> documentId2TopLevelRows,
			@NonNull final DocumentIdsSelection rowIds)
	{
		final ArrayList<MaterialCockpitRow> rowsToInvalidate = new ArrayList<>();
		if (rowIds.isAll())
		{
			rowsToInvalidate.addAll(RowsDataTool.extractAllRows(documentId2TopLevelRows.values()).values());
		}
		else
		{
			for (final DocumentId documentId : rowIds.toSet())
			{
				// we can be sure the documentId is in this map, because those ids were returned by our own getDocumentIdsToInvalidate() method.
				final MaterialCockpitRow row = documentId2TopLevelRows.get(documentId);
				rowsToInvalidate.add(row);
			}
		}
		return rowsToInvalidate;
	}

	private List<I_MD_Cockpit> loadCockpitRecords(@NonNull final Set<Integer> ids)
	{
		return InterfaceWrapperHelper.loadByIds(ids, I_MD_Cockpit.class);
	}

	private List<I_MD_Stock> loadStockRecords(@NonNull final Set<Integer> ids)
	{
		return InterfaceWrapperHelper.loadByIds(ids, I_MD_Stock.class);
	}

	private CreateRowsRequestBuilder createNewBuilder(@NonNull final LocalDate localDate)
	{
		return MaterialCockpitRowFactory.CreateRowsRequest.builder()
				.date(localDate)
				.detailsRowAggregation(detailsRowAggregation);
	}

	@NonNull
	private ProductWithDemandSupplyCollection loadQuantitiesRecords(@NonNull final Collection<MaterialCockpitRow> rows)
	{
		final Set<ProductId> productIds = rows.stream()
				.map(MaterialCockpitRow::getProductId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		return qtyDemandSupplyRepository.getByProductIds(productIds);
	}
}
