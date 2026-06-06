package de.metas.material.cockpit.stock.process;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.model.I_MD_Stock_From_HUs_V;
import de.metas.material.cockpit.stock.StockChangeSourceInfo;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockDataUpdateRequest;
import de.metas.material.cockpit.stock.StockDataUpdateRequestHandler;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;

import java.util.List;

import static java.math.BigDecimal.ZERO;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Reset the {@link I_MD_Stock} table from the actual HU storage truth.
 * May be run in parallel to "normal" production stock changes.
 * <p>
 * The diverging rows are processed as a <b>batched drain</b>: each iteration fetches the top
 * {@link #BATCH_SIZE} still-diverging rows of {@code MD_Stock_From_HUs_V} (NOT an OFFSET page),
 * and then re-queries. Because this process is {@code @RunOutOfTrx}, each
 * {@code handleDataUpdateRequest()} call commits immediately and fires its
 * {@code StockChangedEvent} immediately — there is no ambient transaction to wait for.
 * The committed corrections set those rows' {@code QtyOnHandChange} to zero, so they drop out of
 * the {@code <> 0} filter and the shrinking set drains to empty. Memory stays flat because only
 * {@link #BATCH_SIZE} rows are ever held at once (the {@code LIMIT} fetch), avoiding the
 * {@link OutOfMemoryError} the previous "load everything into one List" implementation suffered
 * on large backlogs.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class MD_Stock_Update_From_M_HUs extends JavaProcess
{
	/** Number of diverging rows fetched and corrected per iteration. */
	private static final int BATCH_SIZE = 500;

	/**
	 * Infinite-loop backstop. Set well above any plausible number of diverging rows; it is NOT a
	 * coverage cap (the drain empties naturally long before this). It only guards against the set
	 * never emptying - e.g. concurrent production stock changes (this process is explicitly designed
	 * to run in parallel to them) or UOM-rounding epsilon - so the run cannot spin forever.
	 */
	private static final int MAX_LOOPS = 100_000;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final StockDataUpdateRequestHandler dataUpdateRequestHandler;

	/** Seam — in production: {@link #retrieveHuData(int)}. */
	private final BatchSource batchSource;

	/** Seam — in production: {@link #createAndHandleDataUpdateRequests(List)}. */
	private final BatchProcessor batchProcessor;

	private final int maxLoops;

	/** Supplies the next batch of still-diverging {@code MD_Stock_From_HUs_V} rows. */
	@FunctionalInterface
	interface BatchSource
	{
		@NonNull List<I_MD_Stock_From_HUs_V> getNextBatch(int batchSize);
	}

	/** Applies the corrections for one fetched batch. */
	@FunctionalInterface
	interface BatchProcessor
	{
		void process(@NonNull List<I_MD_Stock_From_HUs_V> batch);
	}

	/** Production constructor: invoked by the process framework via reflection. */
	@SuppressWarnings("unused")
	public MD_Stock_Update_From_M_HUs()
	{
		this.dataUpdateRequestHandler = SpringContextHolder.instance.getBean(StockDataUpdateRequestHandler.class);
		this.batchSource = this::retrieveHuData;
		this.batchProcessor = this::createAndHandleDataUpdateRequests;
		this.maxLoops = MAX_LOOPS;
	}

	/** Test constructor: lets a unit test substitute the loop's seams and shrink the backstop. */
	MD_Stock_Update_From_M_HUs(
			@NonNull final BatchSource batchSource,
			@NonNull final BatchProcessor batchProcessor,
			final int maxLoops)
	{
		this.dataUpdateRequestHandler = null;       // not used through the injected seams
		this.batchSource = batchSource;
		this.batchProcessor = batchProcessor;
		this.maxLoops = maxLoops;
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		drainInBatches();
		return MSG_OK;
	}

	/**
	 * Runs the batched drain loop.
	 *
	 * @return the number of corrected rows when the set drained to empty; the number corrected up to
	 * the backstop trip otherwise (after logging a warning).
	 */
	int drainInBatches()
	{
		int total = 0;
		for (int loop = 1; loop <= maxLoops; loop++)
		{
			final List<I_MD_Stock_From_HUs_V> batch = batchSource.getNextBatch(BATCH_SIZE);
			if (batch.isEmpty())
			{
				Loggables.addLog("MD_Stock_From_HUs_V drained, {} rows corrected", total);
				return total;
			}

			batchProcessor.process(batch);
			total += batch.size();
			Loggables.addLog("MD_Stock_From_HUs_V: corrected {} rows so far", total);
		}

		// Backstop tripped: the diverging set never emptied within maxLoops iterations.
		Loggables.addLog("WARNING: MD_Stock_Update_From_M_HUs hit MAX_LOOPS={} after correcting {} rows; {} rows still diverge - investigate",
				maxLoops, total, countDivergingRows());
		return total;
	}

	private int countDivergingRows()
	{
		return queryBL
				.createQueryBuilder(I_MD_Stock_From_HUs_V.class)
				.addNotEqualsFilter(I_MD_Stock_From_HUs_V.COLUMNNAME_QtyOnHandChange, ZERO)
				.create()
				.count();
	}

	/**
	 * Fetches the top {@code batchSize} still-diverging rows, ordered by the view's grouping columns.
	 * <p>
	 * This is a DRAIN, NOT an OFFSET page: every call returns the top-N of whatever still diverges.
	 * Once a batch is corrected those rows leave the {@code QtyOnHandChange <> 0} set, so re-querying
	 * the top-N walks the shrinking set down to empty. An OFFSET would instead skip {@code batchSize}
	 * real rows on each iteration and never correct them.
	 */
	private List<I_MD_Stock_From_HUs_V> retrieveHuData(final int batchSize)
	{
		return queryBL
				.createQueryBuilder(I_MD_Stock_From_HUs_V.class)
				.addNotEqualsFilter(I_MD_Stock_From_HUs_V.COLUMNNAME_QtyOnHandChange, ZERO)
				.orderBy(I_MD_Stock_From_HUs_V.COLUMNNAME_AD_Client_ID)
				.orderBy(I_MD_Stock_From_HUs_V.COLUMNNAME_AD_Org_ID)
				.orderBy(I_MD_Stock_From_HUs_V.COLUMNNAME_M_Warehouse_ID)
				.orderBy(I_MD_Stock_From_HUs_V.COLUMNNAME_M_Product_ID)
				.orderBy(I_MD_Stock_From_HUs_V.COLUMNNAME_C_UOM_ID)
				.orderBy(I_MD_Stock_From_HUs_V.COLUMNNAME_AttributesKey)
				.create()
				.setLimit(QueryLimit.ofInt(batchSize))
				.list();
	}

	private void createAndHandleDataUpdateRequests(
			@NonNull final List<I_MD_Stock_From_HUs_V> huBasedDataRecords)
	{
		final ResetStockPInstanceId resetStockPInstanceId = ResetStockPInstanceId.ofPInstanceId(getProcessInfo().getPinstanceId());
		final StockChangeSourceInfo info = StockChangeSourceInfo.ofResetStockPInstanceId(resetStockPInstanceId);

		for (final I_MD_Stock_From_HUs_V huBasedDataRecord : huBasedDataRecords)
		{
			final StockDataUpdateRequest dataUpdateRequest = createDataUpdatedRequest(
					huBasedDataRecord,
					info);
			Loggables.addLog("Handling corrective dataUpdateRequest={}", dataUpdateRequest);
			dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);
		}
	}

	private StockDataUpdateRequest createDataUpdatedRequest(
			@NonNull final I_MD_Stock_From_HUs_V huBasedDataRecord,
			@NonNull final StockChangeSourceInfo stockDataUpdateRequestSourceInfo)
	{
		final StockDataRecordIdentifier recordIdentifier = toStockDataRecordIdentifier(huBasedDataRecord);

		final ProductId productId = ProductId.ofRepoId(huBasedDataRecord.getM_Product_ID());
		final Quantity qtyInStorageUOM = Quantitys.of(huBasedDataRecord.getQtyOnHandChange(), UomId.ofRepoId(huBasedDataRecord.getC_UOM_ID()));
		final Quantity qtyInProductUOM = uomConversionBL.convertToProductUOM(qtyInStorageUOM, productId);

		return StockDataUpdateRequest.builder()
				.identifier(recordIdentifier)
				.onHandQtyChange(qtyInProductUOM.toBigDecimal())
				.sourceInfo(stockDataUpdateRequestSourceInfo)
				.build();
	}

	private static StockDataRecordIdentifier toStockDataRecordIdentifier(@NonNull final I_MD_Stock_From_HUs_V huBasedDataRecord)
	{
		return StockDataRecordIdentifier.builder()
				.clientId(ClientId.ofRepoId(huBasedDataRecord.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(huBasedDataRecord.getAD_Org_ID()))
				.warehouseId(WarehouseId.ofRepoId(huBasedDataRecord.getM_Warehouse_ID()))
				.productId(ProductId.ofRepoId(huBasedDataRecord.getM_Product_ID()))
				.storageAttributesKey(AttributesKey.ofString(huBasedDataRecord.getAttributesKey()))
				.build();
	}
}
