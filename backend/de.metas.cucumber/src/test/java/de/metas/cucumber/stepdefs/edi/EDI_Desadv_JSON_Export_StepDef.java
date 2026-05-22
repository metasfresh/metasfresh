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
import com.google.common.collect.ImmutableMultiset;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.esb.edi.model.I_EDI_Desadv;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for verifying the DESADV JSON export.
 * <p>
 * All assertions inspect the last API response captured in
 * {@link TestContext#getApiResponseBodyAsString()}. The REST endpoint
 * {@code api/v2/processes/M_InOut_EDI_Export_JSON/invoke} returns a JSON <em>array</em> of
 * DESADV documents (one element per linked source DESADV) — {@code shouldExpectSingleResult()}
 * returns {@code false} in {@code M_InOut_EDI_Export_JSON}, so both single-source-order and
 * multi-source-order shipments are handled through the same REST path.
 */
@RequiredArgsConstructor
public class EDI_Desadv_JSON_Export_StepDef
{
	private final @NonNull TestContext testContext;
	private final @NonNull C_Order_StepDefData orderTable;
	private final @NonNull M_Product_StepDefData productTable;
	private final @NonNull EDI_Desadv_StepDefData desadvTable;
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
		final JsonNode packings = unwrapDesadvRoot(root).path("Packings");

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
		final JsonNode noPacking = unwrapDesadvRoot(root).path("DesadvLineWithNoPacking");

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
	 * Verifies that the last {@code M_InOut_EDI_Export_JSON/invoke} REST response (a JSON array)
	 * represents a strict per-element intersection between each emitted DESADV JSON and exactly one
	 * source order — at both header (POReference / EDI_Desadv_ID) and line level
	 * ({@code Packings[].LineItems[].DesadvLine.OrderPOReference} + {@code QtyDeliveredInDesadvLineUOM}).
	 * <p>
	 * Rationale (per PR #24042 review #4335557991): for a consolidated multi-source-order shipment the
	 * REST endpoint returns one array element per linked source DESADV. Each element must represent the
	 * intersection of (shipment ∩ that one source order) — never mix lines from a different source order.
	 * Header-only pairing is not sufficient: without the line-level check the SQL could silently swap
	 * line membership and the header pairing would still pass.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>ExpectedRowCount</b> — (required) number of array elements the response must contain<br>
	 *   <b>DistinctDesadvIds</b> — (required) number of distinct EDI_Desadv_IDs expected<br>
	 *   <b>OrderA_Identifier</b> — (required) step-def identifier of source order A; its {@code POReference} must appear in the response<br>
	 *   <b>OrderB_Identifier</b> — (required) step-def identifier of source order B; its {@code POReference} must appear in the response<br>
	 *   <b>ExpectedQtyDeliveredPerOrder</b> — uniform qty each source order's LineItems must show as
	 *       {@code QtyDeliveredInDesadvLineUOM}; pass {@code 0} to skip per-line qty assertion (use
	 *       {@code verify DESADV JSON export response is a strict projection of source orders:} for
	 *       scenarios with multi-line orders carrying distinct per-line qtys)<br>
	 * @cucumber.example
	 * <pre>
	 * Then verify DESADV JSON export response has multi-source-order intersection:
	 *   | ExpectedRowCount | DistinctDesadvIds | OrderA_Identifier | OrderB_Identifier | ExpectedQtyDeliveredPerOrder |
	 *   | 2                | 2                 | oA_S29231         | oB_S29231         | 10                           |
	 * </pre>
	 */
	@Then("verify DESADV JSON export response has multi-source-order intersection:")
	public void verifyExportResponseMultiSourceOrderIntersection(@NonNull final DataTable dataTable) throws Exception
	{
		final JsonNode responseArray = objectMapper.readTree(testContext.getApiResponseBodyAsString());
		assertThat(responseArray.isArray()).as("REST response must be a JSON array").isTrue();

		DataTableRows.of(dataTable).forEach(row -> assertMultiRowIntersection(responseArray, row));
	}

	/**
	 * Verifies that the last {@code M_InOut_EDI_Export_JSON/invoke} REST response (a JSON array)
	 * contains exactly 1 element whose header matches the given source order (EDI_Desadv_ID +
	 * POReference), and whose {@code Packings[].LineItems[]} all carry that order's POReference and
	 * the expected delivered qty. This is the 1-source-order regression baseline (S29231_110) for the
	 * multi-row fix.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Order_Identifier</b> — (required) step-def identifier of the single source order<br>
	 *   <b>ExpectedQtyDelivered</b> — (required) qty each LineItem must show as {@code QtyDeliveredInDesadvLineUOM}<br>
	 * @cucumber.example
	 * <pre>
	 * Then verify DESADV JSON export response has exactly 1 element matching:
	 *   | Order_Identifier | ExpectedQtyDelivered |
	 *   | o_S29231_110     | 10                   |
	 * </pre>
	 */
	@Then("verify DESADV JSON export response has exactly 1 element matching:")
	public void verifyExportResponseSingleElementIntersection(@NonNull final DataTable dataTable) throws Exception
	{
		final JsonNode responseArray = objectMapper.readTree(testContext.getApiResponseBodyAsString());
		assertThat(responseArray.isArray()).as("REST response must be a JSON array").isTrue();
		assertThat(responseArray.size())
				.as("REST response must contain exactly 1 element for a single-order shipment")
				.isEqualTo(1);

		DataTableRows.of(dataTable).forEach(row -> {
			final String orderIdentifier = row.getAsString("Order_Identifier");
			final I_C_Order order = orderTable.get(orderIdentifier);
			final int expectedQtyDelivered = row.getAsInt("ExpectedQtyDelivered");

			final de.metas.edi.model.I_C_Order ediOrder =
					InterfaceWrapperHelper.create(order, de.metas.edi.model.I_C_Order.class);

			final JsonNode element = responseArray.get(0);
			final JsonNode desadvNode = element.path("metasfresh_DESADV");
			final int actualEdiDesadvId = desadvNode.path("EDI_Desadv_ID").asInt();
			final String actualPoReference = desadvNode.path("POReference").asText(null);

			assertThat(actualEdiDesadvId)
					.as("Response element EDI_Desadv_ID must match the order's EDI_Desadv_ID")
					.isEqualTo(ediOrder.getEDI_Desadv_ID());

			assertThat(actualPoReference)
					.as("Response element POReference must match the order's POReference")
					.isEqualTo(order.getPOReference());

			// Line-level intersection: every LineItem must belong to this single order
			// and carry the expected QtyDeliveredInDesadvLineUOM.
			assertLineItemsBelongToOrder(actualEdiDesadvId, desadvNode, order.getPOReference(), expectedQtyDelivered);
		});
	}

	/**
	 * Verifies that each DESADV array element in the last {@code M_InOut_EDI_Export_JSON/invoke} REST response
	 * is a <em>strict projection</em> of its source order's lines. For each DataTable row the step:
	 * <ol>
	 *   <li>Locates the array element whose {@code metasfresh_DESADV.EDI_Desadv_ID} matches
	 *       the DESADV identified by {@code DESADV_Identifier}.</li>
	 *   <li>Asserts that the total number of {@code Packings[].LineItems[]} equals {@code ExpectedLineCount}.</li>
	 *   <li>Asserts that the multiset of {@code DesadvLine.Product.SupplierProductNo} values across
	 *       all LineItems equals the products named by {@code ExpectedProductIdentifiers} (comma-separated
	 *       step-def identifiers whose {@code M_Product.Value} is used for comparison).</li>
	 *   <li>Asserts that the multiset of {@code DesadvLine.QtyDeliveredInDesadvLineUOM} values across
	 *       all LineItems equals the quantities in {@code ExpectedQtys} (comma-separated integers).</li>
	 * </ol>
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *   <li>{@code DESADV_Identifier} (required) — step-def identifier for the EDI_Desadv record</li>
	 *   <li>{@code Order_Identifier} (required) — step-def identifier for the source order (used only for assertion messages)</li>
	 *   <li>{@code ExpectedLineCount} (required) — expected total count of LineItems in this DESADV element</li>
	 *   <li>{@code ExpectedProductIdentifiers} (required) — comma-separated step-def identifiers of M_Products;
	 *       each identifier's {@code M_Product.Value} must appear as {@code DesadvLine.Product.SupplierProductNo}</li>
	 *   <li>{@code ExpectedQtys} (required) — comma-separated integer delivered quantities matching the line list
	 *       (multiset comparison; order does not matter)</li>
	 * </ul>
	 * <p>
	 * Example usage:
	 * <pre>
	 * Then verify DESADV JSON export response is a strict projection of source orders:
	 *   | DESADV_Identifier | Order_Identifier | ExpectedLineCount | ExpectedProductIdentifiers    | ExpectedQtys |
	 *   | dA_S29231         | oA_S29231        | 2                 | pA_S29231_100,pB_S29231_100   | 10,5         |
	 *   | dB_S29231         | oB_S29231        | 1                 | pB_S29231_100                 | 7            |
	 * </pre>
	 */
	@Then("verify DESADV JSON export response is a strict projection of source orders:")
	public void verifyStrictProjectionPerSourceOrder(@NonNull final DataTable dataTable) throws Exception
	{
		final JsonNode responseArray = objectMapper.readTree(testContext.getApiResponseBodyAsString());
		assertThat(responseArray.isArray()).as("REST response must be a JSON array").isTrue();

		final List<JsonNode> desadvNodes = ImmutableList.copyOf(
				StreamSupport.stream(responseArray.spliterator(), false)
						.map(el -> el.path("metasfresh_DESADV"))
						.iterator());

		DataTableRows.of(dataTable).forEach(row -> {
			final String desadvIdentifier = row.getAsString("DESADV_Identifier");
			final String orderIdentifier = row.getAsString("Order_Identifier");
			final int expectedLineCount = row.getAsInt("ExpectedLineCount");
			final String expectedProductIdentifiersCsv = row.getAsString("ExpectedProductIdentifiers");
			final String expectedQtysCsv = row.getAsString("ExpectedQtys");

			// Resolve the EDI_Desadv_ID from the step-def data so we can find the right array element
			final I_EDI_Desadv desadvRecord = desadvTable.get(desadvIdentifier);
			final int targetDesadvId = desadvRecord.getEDI_Desadv_ID();

			final JsonNode desadvNode = desadvNodes.stream()
					.filter(n -> n.path("EDI_Desadv_ID").asInt() == targetDesadvId)
					.findFirst()
					.orElseThrow(() -> new AssertionError(
							"No response element found for DESADV '" + desadvIdentifier + "' (EDI_Desadv_ID=" + targetDesadvId + ")"
									+ " — response EDI_Desadv_IDs: "
									+ desadvNodes.stream().map(n -> String.valueOf(n.path("EDI_Desadv_ID").asInt())).collect(Collectors.joining(", "))));

			// Collect all LineItems from all Packings
			final JsonNode packings = desadvNode.path("Packings");
			assertThat(packings.isArray())
					.as("Packings must be an array for DESADV '%s' (EDI_Desadv_ID=%d)", desadvIdentifier, targetDesadvId)
					.isTrue();

			final List<JsonNode> allLineItems = new ArrayList<>();
			for (final JsonNode packing : packings)
			{
				final JsonNode lineItems = packing.path("LineItems");
				assertThat(lineItems.isArray())
						.as("LineItems must be an array under Packings for DESADV '%s'", desadvIdentifier)
						.isTrue();
				StreamSupport.stream(lineItems.spliterator(), false).forEach(allLineItems::add);
			}

			// 1. Line count
			assertThat(allLineItems.size())
					.as("DESADV '%s' (order '%s') must have exactly %d LineItem(s)", desadvIdentifier, orderIdentifier, expectedLineCount)
					.isEqualTo(expectedLineCount);

			// 2. Product multiset — compare DesadvLine.Product.SupplierProductNo against M_Product.Value
			final ImmutableMultiset<String> actualProductValues = allLineItems.stream()
					.map(li -> li.path("DesadvLine").path("Product").path("SupplierProductNo").asText(null))
					.collect(ImmutableMultiset.toImmutableMultiset());

			final ImmutableMultiset<String> expectedProductValues = Arrays.stream(expectedProductIdentifiersCsv.split(","))
					.map(String::trim)
					.map(pid -> {
						final I_M_Product product = productTable.get(pid);
						return product.getValue();
					})
					.collect(ImmutableMultiset.toImmutableMultiset());

			assertThat(actualProductValues)
					.as("DESADV '%s' (order '%s'): DesadvLine.Product.SupplierProductNo multiset must exactly match source order's products",
							desadvIdentifier, orderIdentifier)
					.isEqualTo(expectedProductValues);

			// 3. Qty multiset — compare QtyDeliveredInDesadvLineUOM (multiset, order-insensitive)
			final ImmutableMultiset<BigDecimal> actualQtys = allLineItems.stream()
					.map(li -> {
						final JsonNode qtyNode = li.path("DesadvLine").path("QtyDeliveredInDesadvLineUOM");
						assertThat(qtyNode.isMissingNode())
								.as("DesadvLine.QtyDeliveredInDesadvLineUOM must be present in DESADV '%s'", desadvIdentifier)
								.isFalse();
						return new BigDecimal(qtyNode.asText());
					})
					.collect(ImmutableMultiset.toImmutableMultiset());

			final ImmutableMultiset<BigDecimal> expectedQtys = Arrays.stream(expectedQtysCsv.split(","))
					.map(String::trim)
					.map(BigDecimal::new)
					.collect(ImmutableMultiset.toImmutableMultiset());

			assertThat(actualQtys)
					.as("DESADV '%s' (order '%s'): QtyDeliveredInDesadvLineUOM multiset must exactly match source order's quantities",
							desadvIdentifier, orderIdentifier)
					.isEqualTo(expectedQtys);
		});
	}

	// =========================================================================
	// private helpers
	// =========================================================================

	/** ARRAY-MODE: the REST response emits {@code [{...}]} instead of {@code {...}}; unwrap the first element. */
	private static JsonNode unwrapDesadvRoot(@NonNull final JsonNode root)
	{
		return root.isArray() ? root.get(0).path("metasfresh_DESADV") : root.path("metasfresh_DESADV");
	}

	private void assertMultiRowIntersection(
			@NonNull final JsonNode responseArray,
			@NonNull final DataTableRow row)
	{
		final int expectedRowCount = row.getAsInt("ExpectedRowCount");
		final int distinctDesadvIds = row.getAsInt("DistinctDesadvIds");
		final String orderAIdentifier = row.getAsString("OrderA_Identifier");
		final String orderBIdentifier = row.getAsString("OrderB_Identifier");
		// Optional: when absent or 0, per-line qty assertion is skipped (use the strict-projection step for that)
		final Optional<Integer> expectedQtyDeliveredPerOrderOpt = row.getAsOptionalInt("ExpectedQtyDeliveredPerOrder")
				.filter(q -> q != 0);

		// array size
		assertThat(responseArray.size())
				.as("REST response must contain %d element(s)", expectedRowCount)
				.isEqualTo(expectedRowCount);

		// collect (ediDesadvId, poReference, desadvNode) tuples from the array
		final List<JsonNode> desadvNodes = ImmutableList.copyOf(
				StreamSupport.stream(responseArray.spliterator(), false)
						.map(el -> el.path("metasfresh_DESADV"))
						.iterator());

		// distinct EDI_Desadv_ID count
		final long distinctCount = desadvNodes.stream()
				.map(n -> n.path("EDI_Desadv_ID").asInt())
				.distinct()
				.count();
		assertThat(distinctCount)
				.as("Number of distinct EDI_Desadv_IDs in the response")
				.isEqualTo(distinctDesadvIds);

		// POReferences — each source order must contribute its own
		final List<String> responsePoRefs = desadvNodes.stream()
				.map(n -> n.path("POReference").asText(null))
				.collect(ImmutableList.toImmutableList());

		final I_C_Order orderA = orderTable.get(orderAIdentifier);
		final String poRefA = orderA.getPOReference();
		assertThat(responsePoRefs)
				.as("Response must contain POReference from order A ('%s' = '%s')", orderAIdentifier, poRefA)
				.contains(poRefA);

		final I_C_Order orderB = orderTable.get(orderBIdentifier);
		final String poRefB = orderB.getPOReference();
		assertThat(responsePoRefs)
				.as("Response must contain POReference from order B ('%s' = '%s')", orderBIdentifier, poRefB)
				.contains(poRefB);

		// the two POReferences must be distinct (sanity-check that the two orders really differ)
		assertThat(poRefA)
				.as("Source orders A and B must have distinct POReferences")
				.isNotEqualTo(poRefB);

		// Strict header-level intersection (per PR #24042 review #4335557991): each element carrying
		// orderA's EDI_Desadv_ID must carry orderA's POReference (and never orderB's), and vice versa.
		final de.metas.edi.model.I_C_Order ediOrderA =
				InterfaceWrapperHelper.create(orderA, de.metas.edi.model.I_C_Order.class);
		final int desadvIdA = ediOrderA.getEDI_Desadv_ID();
		final JsonNode desadvNodeForOrderA = desadvNodes.stream()
				.filter(n -> n.path("EDI_Desadv_ID").asInt() == desadvIdA)
				.findFirst()
				.orElseThrow(() -> new AssertionError(
						"No response element found for orderA's EDI_Desadv_ID (" + desadvIdA + ")"));
		assertThat(desadvNodeForOrderA.path("POReference").asText(null))
				.as("Response element for orderA's DESADV must carry orderA's POReference (shipment ∩ orderA)")
				.isEqualTo(poRefA);

		final de.metas.edi.model.I_C_Order ediOrderB =
				InterfaceWrapperHelper.create(orderB, de.metas.edi.model.I_C_Order.class);
		final int desadvIdB = ediOrderB.getEDI_Desadv_ID();
		final JsonNode desadvNodeForOrderB = desadvNodes.stream()
				.filter(n -> n.path("EDI_Desadv_ID").asInt() == desadvIdB)
				.findFirst()
				.orElseThrow(() -> new AssertionError(
						"No response element found for orderB's EDI_Desadv_ID (" + desadvIdB + ")"));
		assertThat(desadvNodeForOrderB.path("POReference").asText(null))
				.as("Response element for orderB's DESADV must carry orderB's POReference (shipment ∩ orderB)")
				.isEqualTo(poRefB);

		// Strict LINE-LEVEL intersection: each element's LineItems must reference order lines from
		// its source order — never the other order — and (if provided) carry the expected delivered qty.
		assertLineItemsBelongToOrder(desadvIdA, desadvNodeForOrderA, poRefA, expectedQtyDeliveredPerOrderOpt.orElse(null));
		assertLineItemsBelongToOrder(desadvIdB, desadvNodeForOrderB, poRefB, expectedQtyDeliveredPerOrderOpt.orElse(null));
	}

	/**
	 * Asserts that every {@code Packings[].LineItems[]} entry in the given DESADV JSON node refers
	 * to the expected source-order POReference (line-level intersection) and, if {@code expectedQtyDelivered}
	 * is non-null, carries the expected uniform {@code QtyDeliveredInDesadvLineUOM}. When scenarios have
	 * multi-line orders with different per-line qtys, pass {@code null} to skip the uniform-qty check and
	 * use the strict-projection step for per-line qty verification instead.
	 *
	 * @param ediDesadvId          used only for assertion messages
	 * @param desadvNode           the {@code metasfresh_DESADV} node from one array element of the REST response
	 * @param expectedPoReference  the POReference every LineItem must carry
	 * @param expectedQtyDelivered uniform expected qty per LineItem; {@code null} to skip qty assertion
	 */
	private void assertLineItemsBelongToOrder(
			final int ediDesadvId,
			@NonNull final JsonNode desadvNode,
			@NonNull final String expectedPoReference,
			final Integer expectedQtyDelivered)
	{
		final JsonNode packings = desadvNode.path("Packings");
		assertThat(packings.isArray())
				.as("Packings must be an array for EDI_Desadv_ID=%d", ediDesadvId)
				.isTrue();
		assertThat(packings.size())
				.as("Packings must be non-empty for EDI_Desadv_ID=%d (otherwise line-level intersection cannot be verified)", ediDesadvId)
				.isGreaterThan(0);

		int lineItemsChecked = 0;
		for (final JsonNode packing : packings)
		{
			final JsonNode lineItems = packing.path("LineItems");
			assertThat(lineItems.isArray())
					.as("LineItems must be an array under Packings for EDI_Desadv_ID=%d", ediDesadvId)
					.isTrue();

			for (final JsonNode lineItem : lineItems)
			{
				final JsonNode desadvLine = lineItem.path("DesadvLine");
				assertThat(desadvLine.isMissingNode())
						.as("LineItem must contain DesadvLine for EDI_Desadv_ID=%d", ediDesadvId)
						.isFalse();

				final String actualPoRef = desadvLine.path("OrderPOReference").asText(null);
				assertThat(actualPoRef)
						.as("LineItem.DesadvLine.OrderPOReference for EDI_Desadv_ID=%d must equal the element's POReference "
								+ "(shipment ∩ orderPOReference=%s) — never the other source order's POReference",
								ediDesadvId, expectedPoReference)
						.isEqualTo(expectedPoReference);

				if (expectedQtyDelivered != null)
				{
					final JsonNode qtyNode = desadvLine.path("QtyDeliveredInDesadvLineUOM");
					assertThat(qtyNode.isMissingNode())
							.as("LineItem.DesadvLine must contain QtyDeliveredInDesadvLineUOM for EDI_Desadv_ID=%d "
									+ "(silent-zero guard — a missing JSON field would otherwise fail the qty assertion "
									+ "with a misleading 'expected 0' message)",
									ediDesadvId)
							.isFalse();
					final BigDecimal actualQtyDelivered = new BigDecimal(qtyNode.asText());
					assertThat(actualQtyDelivered)
							.as("LineItem.DesadvLine.QtyDeliveredInDesadvLineUOM for EDI_Desadv_ID=%d must equal source order's delivered qty",
									ediDesadvId)
							.isEqualByComparingTo(BigDecimal.valueOf(expectedQtyDelivered));
				}

				lineItemsChecked++;
			}
		}

		assertThat(lineItemsChecked)
				.as("At least one LineItem must be present under Packings for EDI_Desadv_ID=%d", ediDesadvId)
				.isGreaterThan(0);
	}
}
