/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.stock;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs;
import de.metas.material.event.commons.AttributesKey;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for {@code MD_Stock} — the aggregated on-hand read model
 * ({@code MD_Stock.QtyOnHand} is a rolled-up aggregate of {@code M_HU_Storage} per
 * product/warehouse/{@code AttributesKey}).
 *
 * <p>Provides:
 * <ul>
 *   <li>{@link #seed_divergent_MD_Stock_row(io.cucumber.datatable.DataTable)} — seeds a single active
 *       row whose {@code QtyOnHand} is deliberately out of sync with the HU-derived truth (a
 *       precondition that, in production, only a bug artifact produces)</li>
 *   <li>{@link #deactivate_MD_Stock_row(io.cucumber.datatable.DataTable)} — deactivates the active row
 *       for a business key, leaving an HU-only bucket (no active {@code MD_Stock} row)</li>
 *   <li>{@link #run_MD_Stock_reconciliation_process()} — runs {@code MD_Stock_Update_From_M_HUs} to
 *       reset {@code QtyOnHand} back to the HU-derived truth</li>
 *   <li>{@link #verify_MD_Stock_Data(int, io.cucumber.datatable.DataTable)} — asserts the resulting
 *       {@code MD_Stock} rows</li>
 * </ul>
 */
public class MD_Stock_StepDef
{
	private final static transient Logger logger = LogManager.getLogger(MD_Stock_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final M_Product_StepDefData productTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_AttributeSetInstance_StepDefData asiTable;

	public MD_Stock_StepDef(
			final M_Product_StepDefData productTable,
			final M_Warehouse_StepDefData warehouseTable,
			final M_AttributeSetInstance_StepDefData asiTable)
	{
		this.productTable = productTable;
		this.warehouseTable = warehouseTable;
		this.asiTable = asiTable;
	}

	/**
	 * Runs {@link MD_Stock_Update_From_M_HUs}, which resets {@code MD_Stock.QtyOnHand} to the
	 * {@code M_HU_Storage}-derived truth for every product/warehouse/attributes-key row where the
	 * two diverge.
	 *
	 * <p>Stands in for the {@code AD_Scheduler} (CronPattern {@code * /15 * * * *}) that, where
	 * enabled, periodically runs this same process to reconcile MD_Stock from HU data — this step
	 * invokes the process directly and synchronously so the test doesn't depend on the scheduler
	 * being enabled or wait for its next tick.
	 *
	 * <p>Takes no DataTable; the process itself finds and corrects every diverging row.
	 *
	 * <p>Example:
	 * <pre>
	 * When the MD_Stock reconciliation process is run
	 * </pre>
	 */
	@When("the MD_Stock reconciliation process is run")
	public void run_MD_Stock_reconciliation_process()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(MD_Stock_Update_From_M_HUs.class);

		ProcessInfo.builder()
				.setAD_Process_ID(processId)
				.buildAndPrepareExecution()
				.onErrorThrowException()
				.executeSync();
	}

	/**
	 * Seeds a single active {@code MD_Stock} row whose {@code QtyOnHand} is deliberately set away
	 * from the {@code M_HU_Storage}-derived truth — the corrective target of
	 * {@link #run_MD_Stock_reconciliation_process()}.
	 *
	 * <p><b>Real-world trigger this stands in for:</b> in production, {@code MD_Stock.QtyOnHand} is
	 * kept in sync with {@code M_HU_Storage} by the event-driven path
	 * ({@code TransactionEventHandlerForStockRecords} adds the {@code M_Transaction} delta on every
	 * {@code TransactionCreatedEvent}/{@code TransactionDeletedEvent}). Divergence therefore only
	 * arises from a bug artifact — a missed/dropped stock event, or a create-create race between two
	 * concurrent first-writers of the same business key — never from a normal, reproducible user
	 * action. No deterministic multi-event flow produces this state on demand: every event path either
	 * stays in sync by construction or requires losing/duplicating an event non-deterministically.
	 *
	 * <p><b>Why a direct seed is necessary:</b> a cucumber scenario needs a deterministic starting
	 * state. Since the real trigger is a non-reproducible bug artifact, this step bypasses the
	 * event-driven update path entirely and writes the row directly via
	 * {@link InterfaceWrapperHelper} — no {@code StockChangedEvent} is fired, matching production
	 * (a missed event fires no event either).
	 *
	 * <p>The row is written for the business key
	 * {@code (AD_Client_ID, AD_Org_ID, M_Product_ID, M_Warehouse_ID, AttributesKey)} — the partial
	 * unique index enforced on {@code MD_Stock} — so an existing active row for that key is updated
	 * in place rather than duplicated.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_Product_ID} — (identifier-ref) product</li>
	 *   <li>{@code M_Warehouse_ID} — (identifier-ref) warehouse</li>
	 *   <li>{@code QtyOnHand} — the deliberately wrong quantity on hand to seed</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 *   <li>{@code OPT.M_AttributeSetInstance_ID} — (identifier-ref) ASI whose storage-relevant
	 *       attributes determine the {@code AttributesKey} bucket; omitted defaults to
	 *       {@link AttributesKey#NONE}</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Given metasfresh has a divergent MD_Stock row:
	 *   | M_Product_ID | M_Warehouse_ID | QtyOnHand |
	 *   | product      | warehouseStd   | 999       |
	 * </pre>
	 */
	@Given("metasfresh has a divergent MD_Stock row:")
	public void seed_divergent_MD_Stock_row(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::seedDivergentStockRow);
	}

	private void seedDivergentStockRow(@NonNull final DataTableRow row)
	{
		final I_M_Product product = row.getAsIdentifier(I_MD_Stock.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
		final I_M_Warehouse warehouse = row.getAsIdentifier(I_MD_Stock.COLUMNNAME_M_Warehouse_ID).lookupNotNullIn(warehouseTable);
		final BigDecimal qtyOnHand = row.getAsBigDecimal(I_MD_Stock.COLUMNNAME_QtyOnHand);
		final AttributesKey attributesKey = resolveAttributesKeyFromRow(row);

		final I_MD_Stock stockRecord = buildActiveStockRecordQuery(product, warehouse, attributesKey)
				.create()
				.firstOnly(I_MD_Stock.class);

		final I_MD_Stock dataRecord = stockRecord != null ? stockRecord : InterfaceWrapperHelper.newInstance(I_MD_Stock.class);
		dataRecord.setM_Product_ID(product.getM_Product_ID());
		dataRecord.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		dataRecord.setAttributesKey(attributesKey.getAsString());
		dataRecord.setQtyOnHand(qtyOnHand);
		InterfaceWrapperHelper.saveRecord(dataRecord);
	}

	/**
	 * Deactivates the active {@code MD_Stock} row for a business key, leaving an HU-only bucket: HU
	 * storage still holds the product's on-hand quantity, but no active {@code MD_Stock} row
	 * represents it — the case {@code MD_Stock_From_HUs_V}'s {@code FULL OUTER JOIN} (against the
	 * view's {@code IsActive='Y'}-filtered {@code MD_Stock} side) must still surface, and
	 * {@link #run_MD_Stock_reconciliation_process()} must then CREATE a fresh active row for it.
	 *
	 * <p><b>Real-world trigger this stands in for:</b> in production, this zero-active-row state
	 * results from a manual/support deactivation of the sole {@code MD_Stock} row for a business key
	 * (e.g. believed stale or corrupted) — the same UPDATE-style deactivation the {@code MD_Stock}
	 * dedup data-fix (migration {@code 5812890_sys_MD_Stock_dedup.sql}) performs on *duplicate* rows,
	 * though that particular script always keeps the lowest {@code MD_Stock_ID} row active and so
	 * never leaves a business key with zero active rows on its own. Either way, the bucket then sits
	 * HU-only until the next reconciliation run recreates the missing active row.
	 *
	 * <p><b>Why a direct deactivation is necessary:</b> there is no user/system action that
	 * deactivates an {@code MD_Stock} row through the normal event-driven path (that path only ever
	 * adds/updates rows); the deactivation is itself a maintenance action (an UPDATE), so this step
	 * writes it directly via {@link InterfaceWrapperHelper} — no {@code StockChangedEvent} is fired,
	 * matching a maintenance UPDATE, which fires none either.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_Product_ID} — (identifier-ref) product</li>
	 *   <li>{@code M_Warehouse_ID} — (identifier-ref) warehouse</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 *   <li>{@code OPT.M_AttributeSetInstance_ID} — (identifier-ref) ASI whose storage-relevant
	 *       attributes determine the {@code AttributesKey} bucket; omitted defaults to
	 *       {@link AttributesKey#NONE}</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Given the active MD_Stock row is deactivated:
	 *   | M_Product_ID | M_Warehouse_ID |
	 *   | product      | warehouseStd   |
	 * </pre>
	 */
	@Given("the active MD_Stock row is deactivated:")
	public void deactivate_MD_Stock_row(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::deactivateStockRow);
	}

	private void deactivateStockRow(@NonNull final DataTableRow row)
	{
		final I_M_Product product = row.getAsIdentifier(I_MD_Stock.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
		final I_M_Warehouse warehouse = row.getAsIdentifier(I_MD_Stock.COLUMNNAME_M_Warehouse_ID).lookupNotNullIn(warehouseTable);
		final AttributesKey attributesKey = resolveAttributesKeyFromRow(row);

		final I_MD_Stock stockRecord = buildActiveStockRecordQuery(product, warehouse, attributesKey)
				.create()
				.firstOnlyNotNull(I_MD_Stock.class);

		stockRecord.setIsActive(false);
		InterfaceWrapperHelper.saveRecord(stockRecord);
	}

	/**
	 * Resolves the {@link AttributesKey} bucket from a DataTable row's optional
	 * {@code M_AttributeSetInstance_ID} identifier column, defaulting to {@link AttributesKey#NONE}.
	 */
	@NonNull
	private AttributesKey resolveAttributesKeyFromRow(@NonNull final DataTableRow row)
	{
		final String asiIdentifier = row.getAsOptionalIdentifier("M_AttributeSetInstance_ID")
				.map(StepDefDataIdentifier::getAsString)
				.orElse(null);
		return resolveAttributesKey(asiIdentifier);
	}

	/**
	 * Builds the (not-yet-executed) query for the single active {@code MD_Stock} row matching a
	 * business key — shared by {@link #seedDivergentStockRow} and {@link #deactivateStockRow}, which
	 * differ only in whether a miss is tolerated ({@code firstOnly}) or an error ({@code firstOnlyNotNull}).
	 */
	@NonNull
	private IQueryBuilder<I_MD_Stock> buildActiveStockRecordQuery(
			@NonNull final I_M_Product product,
			@NonNull final I_M_Warehouse warehouse,
			@NonNull final AttributesKey attributesKey)
	{
		return queryBL.createQueryBuilder(I_MD_Stock.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_AD_Client_ID, Env.getClientId())
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_AD_Org_ID, Env.getOrgId())
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, warehouse.getM_Warehouse_ID())
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_AttributesKey, attributesKey.getAsString());
	}

	/**
	 * Waits up to {@code timeoutSeconds} for all rows in the DataTable to match MD_Stock records,
	 * then validates each row exactly.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_Product_ID.Identifier} — product identifier registered in the step-def data store</li>
	 *   <li>{@code QtyOnHand} — expected quantity on hand</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 *   <li>{@code OPT.M_Warehouse_ID.Identifier} — narrows the filter to a specific warehouse</li>
	 *   <li>{@code OPT.M_AttributeSetInstance_ID.Identifier} — ASI identifier whose storage-relevant
	 *       attributes are used to compute the {@link AttributesKey} filter applied on
	 *       {@code MD_Stock.AttributesKey}; resolves via
	 *       {@link AttributesKeys#createAttributesKeyFromASIStorageAttributes}</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * And after not more than 30 seconds metasfresh has MD_Stock data
	 *   | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
	 *   | product                 | 10        | warehouseStd                  | asiA                                     |
	 *   | product                 | 0         | warehouseStd                  | asiB                                     |
	 * </pre>
	 */
	@And("after not more than {int} seconds metasfresh has MD_Stock data")
	public void verify_MD_Stock_Data(final int timeoutSeconds, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> rows = dataTable.asMaps();

		final Supplier<Boolean> supplier = () -> rows.stream().allMatch(this::waitForStock);

		StepDefUtil.tryAndWait(timeoutSeconds, 500, supplier);

		for (final Map<String, String> row : rows)
		{
			validateMD_Stock(row);
		}
	}

	private boolean waitForStock(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final int productId = productTable.get(productIdentifier).getM_Product_ID();

		final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, "QtyOnHand");

		final I_MD_Stock mdStock = buildStockQuery(productId, row).create().firstOnly(I_MD_Stock.class);
		return mdStock != null && mdStock.getQtyOnHand().compareTo(qtyOnHand) == 0;
	}

	private void validateMD_Stock(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, "QtyOnHand");

		final I_M_Product product = productTable.get(productIdentifier);

		final I_MD_Stock mdStock = buildStockQuery(product.getM_Product_ID(), row).create().firstOnly(I_MD_Stock.class);

		if (mdStock == null || mdStock.getQtyOnHand().compareTo(qtyOnHand) != 0)
		{
			logEventLogDiagnostics(product.getM_Product_ID(), qtyOnHand, mdStock);
		}

		assertThat(mdStock).isNotNull();
		assertThat(mdStock.getQtyOnHand()).isEqualTo(qtyOnHand);
	}

	@And("log AD_EventLog count for product {string}")
	public void logAD_EventLog_count(@NonNull final String productIdentifier)
	{
		final I_M_Product product = productTable.get(productIdentifier);
		final int productId = product.getM_Product_ID();
		final String productIdPattern = "\"productId\" : " + productId;

		final List<I_AD_EventLog> eventLogs = queryBL.createQueryBuilder(I_AD_EventLog.class)
				.addStringLikeFilter(I_AD_EventLog.COLUMNNAME_EventData, "%" + productIdPattern + "%", /* ignoreCase */ false)
				.orderBy(I_AD_EventLog.COLUMNNAME_Created)
				.create()
				.list();

		logger.info("=== AD_EventLog count for M_Product_ID={} ({}): {} entries ===",
				productId, product.getName(), eventLogs.size());
		for (final I_AD_EventLog eventLog : eventLogs)
		{
			logger.info("  AD_EventLog_ID={}, EventName={}, IsError={}, Created={}",
					eventLog.getAD_EventLog_ID(),
					eventLog.getEventName(),
					eventLog.isError(),
					eventLog.getCreated());
		}
	}

	private void logEventLogDiagnostics(final int productId, final BigDecimal expectedQty, final I_MD_Stock mdStock)
	{
		final BigDecimal actualQty = mdStock != null ? mdStock.getQtyOnHand() : null;
		logger.warn("=== MD_Stock MISMATCH for M_Product_ID={} ===", productId);
		logger.warn("  Expected QtyOnHand={}, Actual QtyOnHand={}", expectedQty, actualQty);

		// Query recent AD_EventLog entries whose EventData contains the product ID
		final String productIdPattern = "\"productId\" : " + productId;
		final List<I_AD_EventLog> eventLogs = queryBL.createQueryBuilder(I_AD_EventLog.class)
				.addStringLikeFilter(I_AD_EventLog.COLUMNNAME_EventData, "%" + productIdPattern + "%", /* ignoreCase */ false)
				.orderByDescending(I_AD_EventLog.COLUMNNAME_Created)
				.setLimit(10)
				.create()
				.list();

		if (eventLogs.isEmpty())
		{
			logger.warn("  No AD_EventLog entries found containing productId={}", productId);
			return;
		}

		logger.warn("  Found {} AD_EventLog entries for productId={}:", eventLogs.size(), productId);
		for (final I_AD_EventLog eventLog : eventLogs)
		{
			logger.warn("  --- AD_EventLog_ID={}, EventType={}, IsError={}, Created={} ---",
					eventLog.getAD_EventLog_ID(),
					eventLog.getEventTypeName(),
					eventLog.isError(),
					eventLog.getCreated());

			// Query entries (handler results) for this event log
			final List<I_AD_EventLog_Entry> entries = queryBL.createQueryBuilder(I_AD_EventLog_Entry.class)
					.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_ID, eventLog.getAD_EventLog_ID())
					.orderBy(I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_Entry_ID)
					.create()
					.list();

			for (final I_AD_EventLog_Entry entry : entries)
			{
				logger.warn("    Handler={}, IsError={}, MsgText={}",
						entry.getClassname(),
						entry.isError(),
						entry.getMsgText());
			}
		}
		logger.warn("=== END MD_Stock diagnostics ===");
	}

	/**
	 * Builds a query for MD_Stock, applying a mandatory product filter plus optional warehouse
	 * and {@code AttributesKey} filters derived from the DataTable row.
	 */
	@NonNull
	private IQueryBuilder<I_MD_Stock> buildStockQuery(final int productId, @NonNull final Map<String, String> row)
	{
		final IQueryBuilder<I_MD_Stock> builder = queryBL.createQueryBuilder(I_MD_Stock.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, productId);

		final String warehouseIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_MD_Stock.COLUMNNAME_M_Warehouse_ID + ".Identifier");
		if (warehouseIdentifier != null)
		{
			final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);
			assertThat(warehouse).isNotNull();
			builder.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, warehouse.getM_Warehouse_ID());
		}

		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.M_AttributeSetInstance_ID.Identifier");
		if (asiIdentifier != null)
		{
			final AttributesKey attributesKey = resolveAttributesKey(asiIdentifier);
			builder.addEqualsFilter(I_MD_Stock.COLUMNNAME_AttributesKey, attributesKey.getAsString());
		}

		return builder;
	}

	@NonNull
	private AttributesKey resolveAttributesKey(@Nullable final String asiIdentifier)
	{
		if (asiIdentifier == null)
		{
			return AttributesKey.NONE;
		}
		final I_M_AttributeSetInstance asi = asiTable.get(asiIdentifier);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(asi.getM_AttributeSetInstance_ID());
		return AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiId).orElse(AttributesKey.NONE);
	}
}
