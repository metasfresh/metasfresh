/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.edi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for verifying the DESADV JSON export.
 * <p>
 * Two assertion families are supported:
 * <ol>
 *   <li>REST-API response based assertions — inspect the last API response captured in
 *       {@link TestContext#getApiResponseBodyAsString()} (works for single-source-order
 *       shipments because the REST endpoint expects a single result).</li>
 *   <li>SQL-view based assertions — query {@code M_InOut_Export_EDI_DESADV_JSON_V} directly
 *       (required for multi-source-order shipments where the view emits N rows, which the
 *       REST endpoint cannot return because it requests {@code application/vnd.pgrst.object+json}).</li>
 * </ol>
 * <p>
 * The SQL-view path is intentional: there is no DAO for a SQL view, and the multi-row
 * response shape is incompatible with the {@code expectSingleResult(true)} setting of
 * {@code EDI_Export_JSON}. Precedent: {@code EPCIS_JSON_Export_StepDef}.
 */
@RequiredArgsConstructor
public class EDI_Desadv_JSON_Export_StepDef
{
	/** SQL query: for a given M_InOut_ID return one JSON row per DESADV the view emits. */
	private static final String SQL_QUERY_VIEW =
			"SELECT"
					+ "  (v.embedded_json->'metasfresh_DESADV'->>'EDI_Desadv_ID')::int  AS edi_desadv_id,"
					+ "  v.embedded_json->'metasfresh_DESADV'->>'POReference'          AS po_reference,"
					+ "  v.embedded_json->'metasfresh_DESADV'                          AS desadv_json"
					+ " FROM M_InOut_Export_EDI_DESADV_JSON_V v"
					+ " WHERE v.m_inout_id = ?";

	private final @NonNull TestContext testContext;
	private final @NonNull M_InOut_StepDefData inoutTable;
	private final @NonNull C_Order_StepDefData orderTable;
	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Verifies that the DESADV JSON export correctly merges compensation group sub-articles
	 * into the main article's packing entry.
	 * <p>
	 * Inspects the {@code Packings[].LineItems[]} in the last API response and counts:
	 * <ul>
	 *     <li>Total packings (should be reduced after merging)</li>
	 *     <li>Main articles ({@code IsSubArticle=false}): must have {@code MainArticleLine=null}</li>
	 *     <li>Sub-articles ({@code IsSubArticle=true}): must have {@code MainArticleLine > 0}</li>
	 * </ul>
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code PackingCount} (required) — expected number of packing entries after merging</li>
	 *     <li>{@code MainArticleCount} (required) — expected number of main article line items</li>
	 *     <li>{@code SubArticleCount} (required) — expected number of sub-article line items</li>
	 *     <li>{@code IsDeliveryClosed} (optional) — if set, verifies that all DesadvLine objects have this value</li>
	 * </ul>
	 * <p>
	 * Example usage:
	 * <pre>
	 * Then verify DESADV JSON export has compensation group packing:
	 *   | PackingCount | MainArticleCount | SubArticleCount |
	 *   | 1            | 1                | 2               |
	 * </pre>
	 */
	@Then("verify DESADV JSON export has compensation group packing:")
	public void verifyCompensationGroupPacking(@NonNull final DataTable dataTable) throws Exception
	{
		final String responseBody = testContext.getApiResponseBodyAsString();
		final JsonNode root = objectMapper.readTree(responseBody);
		final JsonNode packings = root.path("metasfresh_DESADV").path("Packings");

		assertThat(packings.isArray()).as("Packings should be an array").isTrue();

		DataTableRows.of(dataTable).forEach(row -> {
			final int expectedPackingCount = row.getAsInt("PackingCount");
			assertThat(packings.size())
					.as("Expected %d packings after compensation group merging", expectedPackingCount)
					.isEqualTo(expectedPackingCount);

			final int expectedMainArticles = row.getAsInt("MainArticleCount");
			final int expectedSubArticles = row.getAsInt("SubArticleCount");
			final Boolean expectedIsDeliveryClosed = row.getAsOptionalBoolean("IsDeliveryClosed").toBooleanOrNull();

			int actualMainArticles = 0;
			int actualSubArticles = 0;
			final List<Integer> subArticleMainLines = new ArrayList<>();

			for (final JsonNode packing : packings)
			{
				final JsonNode lineItems = packing.path("LineItems");
				assertThat(lineItems.isArray()).as("LineItems should be an array").isTrue();

				for (final JsonNode item : lineItems)
				{
					final boolean isSubArticle = item.path("IsSubArticle").asBoolean(false);
					if (isSubArticle)
					{
						actualSubArticles++;
						final JsonNode mainArticleLine = item.path("MainArticleLine");
						assertThat(mainArticleLine.isNull()).as("SubArticle should have MainArticleLine set").isFalse();
						subArticleMainLines.add(mainArticleLine.asInt());
					}
					else
					{
						actualMainArticles++;
						final JsonNode mainArticleLine = item.path("MainArticleLine");
						assertThat(mainArticleLine.isNull())
								.as("Non-sub-article should have MainArticleLine=null")
								.isTrue();
					}

					// LineItemLine invariant: the EDIFACT-LIN line number is exposed at two paths
					// (top-of-LineItem as `Line`, and inside `DesadvLine` as `LineItemLine`) and
					// MUST carry the same value — both are sourced from EDI_Desadv_Pack_Item.line.
					// This guards against future SQL drift between the two paths
					// (see metasfresh me03#29842 — Spavetti/Migros LAF-1021 packing-allocation rejection).
					final JsonNode desadvLine = item.path("DesadvLine");
					assertThat(item.has("Line"))
							.as("LineItem should contain top-level Line field")
							.isTrue();
					assertThat(desadvLine.has("LineItemLine"))
							.as("Packed LineItem.DesadvLine should contain LineItemLine field")
							.isTrue();
					assertThat(desadvLine.path("LineItemLine").asInt())
							.as("LineItem.DesadvLine.LineItemLine must equal LineItem.Line")
							.isEqualTo(item.path("Line").asInt());

					if (expectedIsDeliveryClosed != null)
					{
						assertThat(desadvLine.has("IsDeliveryClosed"))
								.as("DesadvLine should contain IsDeliveryClosed field")
								.isTrue();
						assertThat(desadvLine.path("IsDeliveryClosed").asBoolean())
								.as("DesadvLine.IsDeliveryClosed")
								.isEqualTo(expectedIsDeliveryClosed);
					}
				}
			}

			assertThat(actualMainArticles)
					.as("Expected %d main article line items", expectedMainArticles)
					.isEqualTo(expectedMainArticles);
			assertThat(actualSubArticles)
					.as("Expected %d sub-article line items", expectedSubArticles)
					.isEqualTo(expectedSubArticles);

			// Verify all sub-articles reference a valid main line
			for (final Integer mainLine : subArticleMainLines)
			{
				assertThat(mainLine).as("MainArticleLine should be > 0").isGreaterThan(0);
			}
		});
	}

	/**
	 * Verifies the {@code DesadvLineWithNoPacking} array in the DESADV JSON export.
	 * Each row in the DataTable represents an expected entry.
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code OrderLine} (required) — expected order line number</li>
	 *     <li>{@code QtyOrderedInDesadvLineUOM} (required) — expected ordered qty</li>
	 *     <li>{@code QtyDeliveredInDesadvLineUOM} (required) — expected delivered qty</li>
	 *     <li>{@code IsDeliveryClosed} (optional) — expected value of IsDeliveryClosed</li>
	 *     <li>{@code QtyCUsPerTU} (optional) — expected consumer units per traded unit (from order line's QtyItemCapacity)</li>
	 * </ul>
	 * <p>
	 * Structural invariant (asserted on every entry independently of the DataTable):
	 * each entry's {@code DesadvLine} sub-object MUST NOT contain {@code LineItemLine} —
	 * no pack-item exists for no-packing lines, so there is no pack-item line number to
	 * expose (see https://github.com/metasfresh/me03/issues/29842).
	 */
	@Then("verify DESADV JSON export has DesadvLineWithNoPacking:")
	public void verifyDesadvLineWithNoPacking(@NonNull final DataTable dataTable) throws Exception
	{
		final String responseBody = testContext.getApiResponseBodyAsString();
		final JsonNode root = objectMapper.readTree(responseBody);
		final JsonNode noPacking = root.path("metasfresh_DESADV").path("DesadvLineWithNoPacking");

		assertThat(noPacking.isArray()).as("DesadvLineWithNoPacking should be an array").isTrue();

		final DataTableRows expectedRows = DataTableRows.of(dataTable);
		assertThat(noPacking.size())
				.as("Expected %d entries in DesadvLineWithNoPacking", expectedRows.size())
				.isEqualTo(expectedRows.size());

		expectedRows.forEach((row, index) -> {
			final JsonNode entry = noPacking.get(index);
			final JsonNode desadvLine = entry.path("DesadvLine");
			assertThat(desadvLine.isMissingNode()).as("Entry %d should have DesadvLine", index).isFalse();

			// LineItemLine is intentionally absent for no-pack entries: there is no
			// EDI_Desadv_Pack_Item, so there is no pack-item line number to expose.
			assertThat(desadvLine.has("LineItemLine"))
					.as("DesadvLineWithNoPacking[%d].DesadvLine must NOT contain LineItemLine (no pack-item exists)", index)
					.isFalse();

			final int expectedOrderLine = row.getAsInt("OrderLine");
			assertThat(desadvLine.path("OrderLine").asInt())
					.as("DesadvLineWithNoPacking[%d].OrderLine", index)
					.isEqualTo(expectedOrderLine);

			final int expectedQtyOrdered = row.getAsInt("QtyOrderedInDesadvLineUOM");
			assertThat(desadvLine.path("QtyOrderedInDesadvLineUOM").asInt())
					.as("DesadvLineWithNoPacking[%d].QtyOrderedInDesadvLineUOM", index)
					.isEqualTo(expectedQtyOrdered);

			final int expectedQtyDelivered = row.getAsInt("QtyDeliveredInDesadvLineUOM");
			assertThat(desadvLine.path("QtyDeliveredInDesadvLineUOM").asInt())
					.as("DesadvLineWithNoPacking[%d].QtyDeliveredInDesadvLineUOM", index)
					.isEqualTo(expectedQtyDelivered);

			final Boolean expectedIsDeliveryClosed = row.getAsOptionalBoolean("IsDeliveryClosed").toBooleanOrNull();
			if (expectedIsDeliveryClosed != null)
			{
				assertThat(desadvLine.has("IsDeliveryClosed"))
						.as("DesadvLineWithNoPacking[%d] should contain IsDeliveryClosed", index)
						.isTrue();
				assertThat(desadvLine.path("IsDeliveryClosed").asBoolean())
						.as("DesadvLineWithNoPacking[%d].IsDeliveryClosed", index)
						.isEqualTo(expectedIsDeliveryClosed);
			}

			row.getAsOptionalInt("QtyCUsPerTU").ifPresent(expectedQtyCUsPerTU -> {
				assertThat(entry.has("QtyCUsPerTU"))
						.as("DesadvLineWithNoPacking[%d] should contain QtyCUsPerTU", index)
						.isTrue();
				assertThat(entry.path("QtyCUsPerTU").asInt())
						.as("DesadvLineWithNoPacking[%d].QtyCUsPerTU", index)
						.isEqualTo(expectedQtyCUsPerTU);
			});
		});
	}

	/**
	 * Queries {@code M_InOut_Export_EDI_DESADV_JSON_V} for the given (multi-source-order) shipment
	 * and asserts a strict per-row intersection between each emitted DESADV JSON and exactly one
	 * source order — at both the header (POReference / EDI_Desadv_ID) and the line level
	 * ({@code Packings[].LineItems[].DesadvLine.OrderPOReference} + {@code QtyDeliveredInDesadvLineUOM}).
	 * <p>
	 * Rationale (per PR #24042 review #4335557991): for a shipment that covers N source orders the
	 * view must emit N rows, and each row must represent the intersection of (shipment ∩ that one
	 * source order) — never mix lines from a different source order. Header-only pairing is not
	 * sufficient: without the line-level check, the SQL could silently swap line membership and the
	 * header pairing would still pass.
	 * <p>
	 * Implementation note: the REST endpoint {@code M_InOut_EDI_Export_JSON/invoke} cannot be used
	 * here because it requests {@code application/vnd.pgrst.object+json} ({@code expectSingleResult=true}),
	 * which fails for a multi-row view response. We therefore query the view directly — same precedent
	 * as {@code EPCIS_JSON_Export_StepDef}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>ExpectedRowCount</b> — (required) total rows the view must return for the M_InOut<br>
	 *   <b>DistinctDesadvIds</b> — (required) number of distinct EDI_Desadv_IDs expected<br>
	 *   <b>OrderA_Identifier</b> — (required) step-def identifier of source order A; its {@code POReference} must appear in the view rows<br>
	 *   <b>OrderB_Identifier</b> — (required) step-def identifier of source order B; its {@code POReference} must appear in the view rows<br>
	 *   <b>ExpectedQtyDeliveredPerOrder</b> — (required) qty each source order's order line must show as {@code QtyDeliveredInDesadvLineUOM} on every emitted LineItem<br>
	 * @cucumber.example
	 * <pre>
	 * Then verify DESADV JSON export view for M_InOut identified by io_S29231_100 has:
	 *   | ExpectedRowCount | DistinctDesadvIds | OrderA_Identifier | OrderB_Identifier | ExpectedQtyDeliveredPerOrder |
	 *   | 2                | 2                 | oA_S29231         | oB_S29231         | 10                           |
	 * </pre>
	 */
	@Then("^verify DESADV JSON export view for M_InOut identified by (.*) has:$")
	public void verifyExportViewMultiRowIntersection(
			@NonNull final String inoutIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_M_InOut inout = inoutTable.get(inoutIdentifier);
		final int inoutId = inout.getM_InOut_ID();

		final List<ViewRow> rows = queryViewRows(inoutId);

		DataTableRows.of(dataTable).forEach(row -> assertMultiRowIntersection(rows, row));
	}

	/**
	 * Queries {@code M_InOut_Export_EDI_DESADV_JSON_V} for the given (single-source-order) shipment
	 * and asserts exactly 1 emitted DESADV JSON whose header matches the given source order
	 * (EDI_Desadv_ID + POReference), and whose {@code Packings[].LineItems[]} all carry that order's
	 * POReference and the expected delivered qty. This is the 1-source-order regression baseline
	 * (S29231_110) for the multi-row fix.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Order_Identifier</b> — (required) step-def identifier of the single source order<br>
	 *   <b>ExpectedQtyDelivered</b> — (required) qty each LineItem must show as {@code QtyDeliveredInDesadvLineUOM}<br>
	 * @cucumber.example
	 * <pre>
	 * Then verify DESADV JSON export view for M_InOut identified by io_S29231_110 has exactly 1 row matching:
	 *   | Order_Identifier | ExpectedQtyDelivered |
	 *   | o_S29231_110     | 10                   |
	 * </pre>
	 */
	@Then("^verify DESADV JSON export view for M_InOut identified by (.*) has exactly 1 row matching:$")
	public void verifyExportViewSingleRowIntersection(
			@NonNull final String inoutIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_M_InOut inout = inoutTable.get(inoutIdentifier);
		final int inoutId = inout.getM_InOut_ID();

		final List<ViewRow> rows = queryViewRows(inoutId);

		assertThat(rows)
				.as("M_InOut_Export_EDI_DESADV_JSON_V must return exactly 1 row for single-order shipment")
				.hasSize(1);

		DataTableRows.of(dataTable).forEach(row -> {
			final String orderIdentifier = row.getAsString("Order_Identifier");
			final I_C_Order order = orderTable.get(orderIdentifier);
			final int expectedQtyDelivered = row.getAsInt("ExpectedQtyDelivered");

			final de.metas.edi.model.I_C_Order ediOrder =
					InterfaceWrapperHelper.create(order, de.metas.edi.model.I_C_Order.class);

			final ViewRow only = rows.get(0);
			assertThat(only.ediDesadvId())
					.as("View row EDI_Desadv_ID must match the order's EDI_Desadv_ID")
					.isEqualTo(ediOrder.getEDI_Desadv_ID());

			assertThat(only.poReference())
					.as("View row POReference must match the order's POReference")
					.isEqualTo(order.getPOReference());

			// Line-level intersection: every LineItem must belong to this single order
			// and carry the expected QtyDeliveredInDesadvLineUOM.
			assertLineItemsBelongToOrder(only, order.getPOReference(), expectedQtyDelivered);
		});
	}

	// =========================================================================
	// private helpers
	// =========================================================================

	private void assertMultiRowIntersection(
			@NonNull final List<ViewRow> viewRows,
			@NonNull final DataTableRow row)
	{
		final int expectedRowCount = row.getAsInt("ExpectedRowCount");
		final int distinctDesadvIds = row.getAsInt("DistinctDesadvIds");
		final String orderAIdentifier = row.getAsString("OrderA_Identifier");
		final String orderBIdentifier = row.getAsString("OrderB_Identifier");
		final int expectedQtyDeliveredPerOrder = row.getAsInt("ExpectedQtyDeliveredPerOrder");

		// row count
		assertThat(viewRows)
				.as("M_InOut_Export_EDI_DESADV_JSON_V must return %d row(s) for this M_InOut", expectedRowCount)
				.hasSize(expectedRowCount);

		// distinct EDI_Desadv_ID count
		final long distinctCount = viewRows.stream()
				.map(ViewRow::ediDesadvId)
				.distinct()
				.count();
		assertThat(distinctCount)
				.as("Number of distinct EDI_Desadv_IDs in the view rows")
				.isEqualTo(distinctDesadvIds);

		// POReferences — each source order must contribute its own
		final List<String> viewPoRefs = viewRows.stream()
				.map(ViewRow::poReference)
				.collect(ImmutableList.toImmutableList());

		final I_C_Order orderA = orderTable.get(orderAIdentifier);
		final String poRefA = orderA.getPOReference();
		assertThat(viewPoRefs)
				.as("View rows must contain POReference from order A ('%s' = '%s')", orderAIdentifier, poRefA)
				.contains(poRefA);

		final I_C_Order orderB = orderTable.get(orderBIdentifier);
		final String poRefB = orderB.getPOReference();
		assertThat(viewPoRefs)
				.as("View rows must contain POReference from order B ('%s' = '%s')", orderBIdentifier, poRefB)
				.contains(poRefB);

		// the two POReferences must be distinct (sanity-check that the two orders really differ)
		assertThat(poRefA)
				.as("Source orders A and B must have distinct POReferences")
				.isNotEqualTo(poRefB);

		// Strict header-level intersection (per PR #24042 review #4335557991): each emitted view row
		// must represent the intersection of (shipment ∩ source-order). I.e. the row carrying
		// orderA's EDI_Desadv_ID must carry orderA's POReference (and never orderB's), and vice versa.
		// Without this pairing assertion, two rows could swap POReferences and the earlier checks
		// would still pass.
		final de.metas.edi.model.I_C_Order ediOrderA =
				InterfaceWrapperHelper.create(orderA, de.metas.edi.model.I_C_Order.class);
		final ViewRow rowForOrderA = viewRows.stream()
				.filter(r -> r.ediDesadvId() == ediOrderA.getEDI_Desadv_ID())
				.findFirst()
				.orElseThrow(() -> new AssertionError(
						"No view row found for orderA's EDI_Desadv_ID (" + ediOrderA.getEDI_Desadv_ID() + ")"));
		assertThat(rowForOrderA.poReference())
				.as("View row for orderA's DESADV must carry orderA's POReference (shipment ∩ orderA)")
				.isEqualTo(poRefA);

		final de.metas.edi.model.I_C_Order ediOrderB =
				InterfaceWrapperHelper.create(orderB, de.metas.edi.model.I_C_Order.class);
		final ViewRow rowForOrderB = viewRows.stream()
				.filter(r -> r.ediDesadvId() == ediOrderB.getEDI_Desadv_ID())
				.findFirst()
				.orElseThrow(() -> new AssertionError(
						"No view row found for orderB's EDI_Desadv_ID (" + ediOrderB.getEDI_Desadv_ID() + ")"));
		assertThat(rowForOrderB.poReference())
				.as("View row for orderB's DESADV must carry orderB's POReference (shipment ∩ orderB)")
				.isEqualTo(poRefB);

		// Strict LINE-LEVEL intersection: each row's emitted LineItems must reference order lines
		// from the row's source order — never the other order — and carry the expected delivered qty.
		// This is the assertion the reviewer asked for: it would fail if the SQL view ever joined a
		// shipment line to the wrong DESADV (line-membership swap), even though header POReferences
		// would still pair correctly.
		assertLineItemsBelongToOrder(rowForOrderA, poRefA, expectedQtyDeliveredPerOrder);
		assertLineItemsBelongToOrder(rowForOrderB, poRefB, expectedQtyDeliveredPerOrder);
	}

	/**
	 * Asserts that every {@code Packings[].LineItems[]} entry in the given view row's JSON refers
	 * to the expected source-order POReference (line-level intersection) and carries the expected
	 * {@code QtyDeliveredInDesadvLineUOM}. Fails if any LineItem leaks across orders.
	 */
	private void assertLineItemsBelongToOrder(
			@NonNull final ViewRow row,
			@NonNull final String expectedPoReference,
			final int expectedQtyDelivered)
	{
		final JsonNode desadvJson;
		try
		{
			desadvJson = objectMapper.readTree(row.desadvJson());
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		final JsonNode packings = desadvJson.path("Packings");
		assertThat(packings.isArray())
				.as("Packings must be an array for EDI_Desadv_ID=%d", row.ediDesadvId())
				.isTrue();
		assertThat(packings.size())
				.as("Packings must be non-empty for EDI_Desadv_ID=%d (otherwise line-level intersection cannot be verified)", row.ediDesadvId())
				.isGreaterThan(0);

		int lineItemsChecked = 0;
		for (final JsonNode packing : packings)
		{
			final JsonNode lineItems = packing.path("LineItems");
			assertThat(lineItems.isArray())
					.as("LineItems must be an array under Packings for EDI_Desadv_ID=%d", row.ediDesadvId())
					.isTrue();

			for (final JsonNode lineItem : lineItems)
			{
				final JsonNode desadvLine = lineItem.path("DesadvLine");
				assertThat(desadvLine.isMissingNode())
						.as("LineItem must contain DesadvLine for EDI_Desadv_ID=%d", row.ediDesadvId())
						.isFalse();

				final String actualPoRef = desadvLine.path("OrderPOReference").asText(null);
				assertThat(actualPoRef)
						.as("LineItem.DesadvLine.OrderPOReference for EDI_Desadv_ID=%d must equal the row's POReference "
								+ "(shipment ∩ orderPOReference=%s) — never the other source order's POReference",
								row.ediDesadvId(), expectedPoReference)
						.isEqualTo(expectedPoReference);

				final JsonNode qtyNode = desadvLine.path("QtyDeliveredInDesadvLineUOM");
				assertThat(qtyNode.isMissingNode())
						.as("LineItem.DesadvLine must contain QtyDeliveredInDesadvLineUOM for EDI_Desadv_ID=%d "
								+ "(silent-zero guard — a missing JSON field would otherwise fail the qty assertion "
								+ "with a misleading 'expected 0' message)",
								row.ediDesadvId())
						.isFalse();
				final BigDecimal actualQtyDelivered = new BigDecimal(qtyNode.asText());
				assertThat(actualQtyDelivered)
						.as("LineItem.DesadvLine.QtyDeliveredInDesadvLineUOM for EDI_Desadv_ID=%d must equal source order's delivered qty",
								row.ediDesadvId())
						.isEqualByComparingTo(BigDecimal.valueOf(expectedQtyDelivered));

				lineItemsChecked++;
			}
		}

		assertThat(lineItemsChecked)
				.as("At least one LineItem must be present under Packings for EDI_Desadv_ID=%d", row.ediDesadvId())
				.isGreaterThan(0);
	}

	private List<ViewRow> queryViewRows(final int inoutId)
	{
		final List<ViewRow> result = new ArrayList<>();
		try (final PreparedStatement pstmt = DB.prepareStatement(SQL_QUERY_VIEW, ITrx.TRXNAME_None))
		{
			pstmt.setInt(1, inoutId);
			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					result.add(new ViewRow(
							rs.getInt("edi_desadv_id"),
							rs.getString("po_reference"),
							rs.getString("desadv_json")));
				}
			}
		}
		catch (final SQLException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		return result;
	}

	/** Immutable value holder for one row returned by the export view. */
	private static final class ViewRow
	{
		private final int ediDesadvId;
		private final String poReference;
		private final String desadvJson;

		ViewRow(final int ediDesadvId, final String poReference, final String desadvJson)
		{
			this.ediDesadvId = ediDesadvId;
			this.poReference = poReference;
			this.desadvJson = desadvJson;
		}

		int ediDesadvId() { return ediDesadvId; }
		String poReference() { return poReference; }
		String desadvJson() { return desadvJson; }
	}
}
