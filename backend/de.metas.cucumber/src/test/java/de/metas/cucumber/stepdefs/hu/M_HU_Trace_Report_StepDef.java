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

package de.metas.cucumber.stepdefs.hu;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for testing the M_HU_Trace_Report SQL function.
 *
 * <p>Covers two previously-fixed SQL bugs plus the receipt→shipment pairing behaviour of the
 * DIRECT_SALE_DETAIL section:
 * <ul>
 *   <li>Bug A (Section 5 — PRODUCTION_RECEIPT_DETAL): INNER JOIN to m_hu_attribute mhd
 *       excluded products without best-before date. Fixed by LEFT JOIN.</li>
 *   <li>Bug B (Section 6 — DIRECT_SALE_DETAIL): {@code shipment_trace.lotnumber = t.lotnumber}
 *       evaluated to false for NULL lot numbers. Fixed by {@code IS NOT DISTINCT FROM}.</li>
 *   <li>DIRECT_SALE_DETAIL pairing (Section 6): a shipment is paired with the receipt it is
 *       traceable to along the {@code M_HU_Trace} graph — the same VHU, or a chain of
 *       {@code VHU_Source_ID} edges — guarded by lot agreement between the two ends. Where the
 *       graph is silent for a (shipment document, product, lot) group, lot-level or product-level
 *       candidates are emitted for that group and labelled as such in {@code link_basis}.</li>
 * </ul>
 */
@RequiredArgsConstructor
public class M_HU_Trace_Report_StepDef
{
	private final M_Product_StepDefData productTable;

	@NonNull private final HUTraceRepository huTraceRepository = SpringContextHolder.instance.getBean(HUTraceRepository.class);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/** Maps scenario name → list of HUTraceType (section name) values returned by the report */
	private final Map<String, List<String>> reportResultsByScenario = new HashMap<>();

	/** Maps scenario name → list of DIRECT_SALE_DETAIL rows returned by the report */
	private final Map<String, List<DetailRow>> detailRowsByScenario = new HashMap<>();

	// =====================================================================================
	// Setup steps
	// =====================================================================================

	/**
	 * Sets up all DB records required for a specific trace report test scenario.
	 *
	 * <p>Supported TestType values (see each {@code setupXxx} method's Javadoc for the full
	 * behaviour):
	 * <ul>
	 *   <li>{@code DIRECT_SALE_NULL_LOT} — NULL lots on both sides; Bug B fix (IS NOT DISTINCT FROM).</li>
	 *   <li>{@code PRODUCTION_RECEIPT_NO_MHD} — no best-before attribute; Bug A fix (LEFT JOIN).</li>
	 *   <li>{@code TRACED_ONE_OF_TWO_RECEIPTS} — one of two same-lot receipts is graph-traced.</li>
	 *   <li>{@code LOT_DISAGREEMENT} — a graph-linked pair whose lots disagree; must be dropped.</li>
	 *   <li>{@code SAME_VHU_NO_TRANSFORM} — receipt and shipment on the same VHU (depth 0).</li>
	 *   <li>{@code TWO_STEP_TRANSFORM} — a two-edge TRANSFORM_LOAD chain.</li>
	 *   <li>{@code MIXED_TRACED_AND_CANDIDATE} — candidate suppression is per group, not per lot.</li>
	 *   <li>{@code NO_LOT_NO_LINK} — PRODUCT_CANDIDATE when neither side has a lot.</li>
	 *   <li>{@code RECEIPT_QTY_AND_DEDUP} — one receipt document, two VHUs, reported once.</li>
	 *   <li>{@code INELIGIBLE_RECEIPT_DOC} — graph-traced but an unreportable receipt doctype.</li>
	 * </ul>
	 */
	@When("M_HU_Trace_Report test data is set up for scenario {string}:")
	public void setupTraceReportTestData(@NonNull final String scenarioName, @NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final String testType = row.getAsString("TestType");
		final ProductId productId = productTable.getId(row.getAsIdentifier("M_Product_ID"));

		// see #deleteExistingHuTraceRows javadoc: makes the report selection see only THIS run's data
		deleteExistingHuTraceRows(productId);
		// the product all ten setups below build their traces for; #invokeReport reads it back
		scenarioProductIds.put(scenarioName, productId);

		switch (testType)
		{
			case "DIRECT_SALE_NULL_LOT":
				setupDirectSaleNullLot(productId);
				break;
			case "PRODUCTION_RECEIPT_NO_MHD":
				final ProductId rawMaterialProductId = productTable.getId(row.getAsIdentifier("RawMaterial_ID"));
				deleteExistingHuTraceRows(rawMaterialProductId);
				setupProductionReceiptNoMhd(productId, rawMaterialProductId);
				break;
			case "TRACED_ONE_OF_TWO_RECEIPTS":
				setupTracedOneOfTwoReceipts(scenarioName, productId);
				break;
			case "LOT_DISAGREEMENT":
				setupLotDisagreement(scenarioName, productId);
				break;
			case "SAME_VHU_NO_TRANSFORM":
				setupSameVhuNoTransform(scenarioName, productId);
				break;
			case "TWO_STEP_TRANSFORM":
				setupTwoStepTransform(scenarioName, productId);
				break;
			case "MIXED_TRACED_AND_CANDIDATE":
				setupMixedTracedAndCandidate(scenarioName, productId);
				break;
			case "NO_LOT_NO_LINK":
				setupNoLotNoLink(scenarioName, productId);
				break;
			case "RECEIPT_QTY_AND_DEDUP":
				setupReceiptQtyAndDedup(scenarioName, productId);
				break;
			case "INELIGIBLE_RECEIPT_DOC":
				setupIneligibleReceiptDoc(scenarioName, productId);
				break;
			default:
				throw new AdempiereException("Unknown TestType: " + testType);
		}
	}

	/**
	 * Deletes any {@code M_HU_Trace} rows left over from a PREVIOUS run of this feature against
	 * the same (persistent, not reset-between-runs) local stack.
	 *
	 * <p>Products are upserted by {@code Value}, so a re-run resolves to the SAME
	 * {@code M_Product_ID}, and {@link #invokeReport}'s query is scoped by product and trace type
	 * only (no lot/InOut/scenario/time filter) — so leftover rows from a prior run would still be
	 * picked up and break {@link #assertDetailRows}'s exhaustive comparison against freshly
	 * generated {@code DocumentNo}s. Deletes the trace rows rather than the product itself because
	 * {@code M_HU_Trace.M_Product_ID} has an {@code ON DELETE NO ACTION} FK that would block it.
	 */
	private void deleteExistingHuTraceRows(@NonNull final ProductId productId)
	{
		queryBL.createQueryBuilder(I_M_HU_Trace.class)
				.addEqualsFilter(I_M_HU_Trace.COLUMNNAME_M_Product_ID, productId)
				.create()
				.delete();
	}

	// =====================================================================================
	// Invoke + assert steps
	// =====================================================================================

	/**
	 * Invokes the {@code M_HU_Trace_Report(?)} SQL function for the product associated with the given
	 * scenario and stores the returned {@code HUTraceType} section names for later assertion.
	 *
	 * <p>Reads {@code HUTraceType} (the section name), not {@code detail_type} (the sub-record's
	 * trace type) — the feature file assertions reference section names.
	 */
	@And("M_HU_Trace_Report is invoked for scenario {string}")
	public void invokeReport(@NonNull final String scenarioName)
	{
		final ProductId productId = scenarioProductIds.get(scenarioName);
		assertThat(productId).as("Product ID for scenario %s was not set up", scenarioName).isNotNull();

		final HUTraceEventQuery query = HUTraceEventQuery.builder()
				.productId(productId)
				.types(HUTraceType.typesToReport())
				.recursionMode(HUTraceEventQuery.RecursionMode.BOTH)
				.build();

		final PInstanceId pInstanceId = huTraceRepository.queryToSelection(query);
		assertThat(pInstanceId).as("Expected traces to be found for product %s", productId).isNotNull();

		final List<String> detailTypes = new ArrayList<>();
		final String sql = "SELECT HUTraceType FROM M_HU_Trace_Report(?)";
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setInt(1, pInstanceId.getRepoId());
			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					final String huTraceType = rs.getString("HUTraceType");
					if (huTraceType != null)
					{
						detailTypes.add(huTraceType);
					}
				}
			}
		}
		catch (final SQLException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		reportResultsByScenario.put(scenarioName, ImmutableList.copyOf(detailTypes));

		final List<DetailRow> detailRows = new ArrayList<>();
		final String detailSql = "SELECT \"InOut\" AS receipt_docno, shipment_note AS shipment_docno,"
				+ " link_basis, qty AS menge, shipmentqty AS liefermenge"
				+ " FROM M_HU_Trace_Report(?)"
				+ " WHERE hutracetype = 'DIRECT_SALE_DETAIL'";
		try (final PreparedStatement pstmt = DB.prepareStatement(detailSql, ITrx.TRXNAME_None))
		{
			pstmt.setInt(1, pInstanceId.getRepoId());
			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					detailRows.add(new DetailRow(
							rs.getString("receipt_docno"),
							rs.getString("shipment_docno"),
							rs.getString("link_basis"),
							rs.getBigDecimal("menge"),
							rs.getBigDecimal("liefermenge")));
				}
			}
		}
		catch (final SQLException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		detailRowsByScenario.put(scenarioName, ImmutableList.copyOf(detailRows));
	}

	/**
	 * Asserts that the previously invoked {@code M_HU_Trace_Report} result for the given scenario
	 * contains a row with the specified {@code HUTraceType} section name.
	 */
	@Then("M_HU_Trace_Report result for scenario {string} contains detail_type row {string}")
	public void assertDetailTypePresent(@NonNull final String scenarioName, @NonNull final String expectedDetailType)
	{
		final List<String> detailTypes = reportResultsByScenario.get(scenarioName);
		assertThat(detailTypes)
				.as("M_HU_Trace_Report result for scenario '%s' should contain detail_type='%s'", scenarioName, expectedDetailType)
				.isNotNull()
				.contains(expectedDetailType);
	}

	/**
	 * Asserts the exact set of {@code DIRECT_SALE_DETAIL} rows returned for the given scenario —
	 * row-level, unlike {@link #assertDetailTypePresent}. {@code containsExactlyInAnyOrderElementsOf}
	 * is deliberate: it fails on a missing row AND on an extra one, which is what catches a cartesian
	 * (e.g. a shipment paired with more than one receipt of the same lot).
	 *
	 * <p>DataTable columns: {@code ReceiptDocNo | ShipmentDocNo | LinkBasis | Menge | Liefermenge}.
	 * {@code ReceiptDocNo}/{@code ShipmentDocNo} are identifiers (e.g. {@code receipt1}, {@code shipment})
	 * registered by the scenario's setup step in {@link #scenarioDocNos} — resolved to the DB-generated
	 * DocumentNo here.
	 */
	@Then("M_HU_Trace_Report detail rows for scenario {string} are:")
	public void assertDetailRows(@NonNull final String scenarioName, @NonNull final DataTable dataTable)
	{
		final List<DetailRow> actual = detailRowsByScenario.get(scenarioName);
		assertThat(actual).as("no report result stored for scenario '%s'", scenarioName).isNotNull();

		final List<DetailRow> expected = DataTableRows.of(dataTable).stream()
				.map(row -> new DetailRow(
						resolveDocNo(scenarioName, row.getAsString("ReceiptDocNo")),
						resolveDocNo(scenarioName, row.getAsString("ShipmentDocNo")),
						row.getAsString("LinkBasis"),
						row.getAsBigDecimal("Menge"),
						row.getAsBigDecimal("Liefermenge")))
				.collect(ImmutableList.toImmutableList());

		assertThat(actual)
				.as("DIRECT_SALE_DETAIL rows for scenario '%s'", scenarioName)
				.containsExactlyInAnyOrderElementsOf(expected);
	}

	/** Feature files name documents by identifier; the DB generates the numbers. */
	private String resolveDocNo(@NonNull final String scenarioName, @NonNull final String identifier)
	{
		final String docNo = scenarioDocNos.get(scenarioName + "." + identifier);
		assertThat(docNo).as("unknown document identifier '%s' in scenario '%s'", identifier, scenarioName).isNotNull();
		return docNo;
	}

	// =====================================================================================
	// Private state: scenario → product mapping
	// =====================================================================================

	/** Maps scenario name → product ID (for invoking the report) */
	private final Map<String, ProductId> scenarioProductIds = new HashMap<>();

	/** Maps {@code scenarioName + "." + identifier} (e.g. {@code "traced_one_of_two.receipt1"}) → generated DocumentNo */
	private final Map<String, String> scenarioDocNos = new HashMap<>();

	// =====================================================================================
	// Private setup helpers
	// =====================================================================================

	/**
	 * Bug B test setup: creates MATERIAL_RECEIPT + MATERIAL_SHIPMENT traces with lotnumber=NULL.
	 *
	 * <p>What keeps this pair reportable is the lot condition of the DIRECT_SALE_DETAIL section's
	 * candidate branch:
	 * <pre>
	 * FROM receipt_trace r
	 * JOIN shipment_trace_sel st
	 *        ON st.M_Product_ID = r.M_Product_ID
	 *       AND st.LotNumber IS NOT DISTINCT FROM r.LotNumber
	 * </pre>
	 * {@code IS NOT DISTINCT FROM} makes two NULL lots agree, so the pair reaches
	 * {@code candidate_pair} and is emitted with {@code link_basis = 'PRODUCT_CANDIDATE'}.
	 *
	 * <p>Historically this was a plain {@code =}: NULL=NULL evaluated to false, the shipment side
	 * was never found, and the INNER JOIN on M_Product eliminated the row entirely.
	 */
	private void setupDirectSaleNullLot(@NonNull final ProductId productId)
	{
		// Load standard C_DocTypes (receipt = isSOTrx='N', shipment = isSOTrx='Y')
		final I_C_DocType receiptDocType = loadDocType("MMR", false);
		final I_C_DocType shipmentDocType = loadDocType("MMS", true);

		// Create a VHU for the receipt trace
		final I_M_HU receiptVhu = createVhu();

		// Create M_InOut for receipt (purchase receipt, docstatus='CO')
		final I_M_InOut receiptInOut = createMinimalInOut(receiptDocType, "CO");

		// Create MATERIAL_RECEIPT trace (lot=NULL)
		createHuTrace(
				receiptVhu,
				productId,
				HUTraceType.MATERIAL_RECEIPT,
				null /*lotNumber*/,
				receiptInOut,
				null /*ppOrder*/);

		// Create a VHU for the shipment trace
		final I_M_HU shipmentVhu = createVhu();

		// Create M_InOut for shipment (customer shipment, docstatus='CO')
		final I_M_InOut shipmentInOut = createMinimalInOut(shipmentDocType, "CO");

		// Create MATERIAL_SHIPMENT trace (same product, lot=NULL)
		createHuTrace(
				shipmentVhu,
				productId,
				HUTraceType.MATERIAL_SHIPMENT,
				null /*lotNumber*/,
				shipmentInOut,
				null /*ppOrder*/);
	}

	/**
	 * Bug A test setup: creates PRODUCTION_RECEIPT + PRODUCTION_ISSUE traces without
	 * any MHD (best-before date) attribute on the PRODUCTION_ISSUE HU.
	 *
	 * <p>The PRODUCTION_RECEIPT_DETAL section of M_HU_Trace_Report uses:
	 * <pre>
	 * LEFT JOIN m_hu_attribute mhd           -- Bug A fix (was INNER JOIN)
	 *     ON mhd.m_hu_id = prod_trace.m_hu_id
	 *     AND mhd.m_attribute_id = 540020
	 * </pre>
	 * Before the fix (INNER JOIN), rows without a best-before attribute were excluded.
	 * After the fix (LEFT JOIN), they appear with {@code finished_product_mhd=NULL}.
	 */
	private void setupProductionReceiptNoMhd(
			@NonNull final ProductId finishedProductId,
			@NonNull final ProductId rawMaterialProductId)
	{
		// Create a PP_Order (docstatus='CO') — links receipt and issue traces
		final I_PP_Order ppOrder = createMinimalPpOrder(finishedProductId);

		// Create VHU for the PRODUCTION_RECEIPT trace (the finished product)
		final I_M_HU finishedVhu = createVhu();

		// Create PRODUCTION_RECEIPT trace for the finished product
		createHuTrace(
				finishedVhu,
				finishedProductId,
				HUTraceType.PRODUCTION_RECEIPT,
				"LOT-BUG-A",
				null /*inOut*/,
				ppOrder);

		// Create VHU for the PRODUCTION_ISSUE trace (the raw material)
		// Note: this VHU intentionally has NO m_hu_attribute with m_attribute_id=540020 (MHD)
		final I_M_HU rawMaterialVhu = createVhu();

		// Create PRODUCTION_ISSUE trace for the raw material — same PP_Order, no MHD attribute
		createHuTrace(
				rawMaterialVhu,
				rawMaterialProductId,
				HUTraceType.PRODUCTION_ISSUE,
				"LOT-BUG-A",
				null /*inOut*/,
				ppOrder);
	}

	/**
	 * Two receipts of the same product and lot, one of them graph-traced to the shipment: the
	 * shipment's VHU descends from the FIRST receipt's VHU through one TRANSFORM_LOAD edge. The
	 * second receipt is connected to nothing, and sharing the lot is not enough to be paired once
	 * the graph has answered — so the shipment yields exactly one row, linked to receipt1.
	 */
	private void setupTracedOneOfTwoReceipts(@NonNull final String scenarioName, @NonNull final ProductId productId)
	{
		final I_C_DocType receiptDocType = loadDocType("MMR", false);
		final I_C_DocType shipmentDocType = loadDocType("MMS", true);

		final I_M_HU receiptVhu1 = createVhu();
		final I_M_InOut receiptInOut1 = createMinimalInOut(receiptDocType, "CO");
		createHuTraceWithSource(receiptVhu1, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-ONE-OF-TWO", receiptInOut1, new BigDecimal("100"));

		// a second receipt of the SAME lot, connected to nothing — must not be paired with the shipment
		final I_M_HU receiptVhu2 = createVhu();
		final I_M_InOut receiptInOut2 = createMinimalInOut(receiptDocType, "CO");
		createHuTraceWithSource(receiptVhu2, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-ONE-OF-TWO", receiptInOut2, new BigDecimal("100"));

		// the shipped VHU descends from receipt 1
		final I_M_HU shippedVhu = createVhu();
		createHuTraceWithSource(shippedVhu, receiptVhu1, productId, HUTraceType.TRANSFORM_LOAD,
				"LOT-ONE-OF-TWO", null, new BigDecimal("24"));
		final I_M_InOut shipmentInOut = createMinimalInOut(shipmentDocType, "CO");
		createHuTraceWithSource(shippedVhu, null, productId, HUTraceType.MATERIAL_SHIPMENT,
				"LOT-ONE-OF-TWO", shipmentInOut, new BigDecimal("-24"));

		scenarioDocNos.put(scenarioName + ".receipt1", receiptInOut1.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".receipt2", receiptInOut2.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".shipment", shipmentInOut.getDocumentNo());
	}

	/**
	 * A receipt's VHU transforms (one TRANSFORM_LOAD edge) into the shipped VHU, but the shipment's
	 * own trace carries a different lot number. Guards the lot-agreement half of the graph-tracing
	 * rule: a graph link alone must not be enough to call the pair traced when the ends disagree.
	 *
	 * <p>The rejected pair is dropped, not demoted — the candidate branch also requires lot
	 * agreement, so nothing is emitted. A product-only fallback would cartesian every receipt of
	 * the product against every shipment of it.
	 */
	private void setupLotDisagreement(@NonNull final String scenarioName, @NonNull final ProductId productId)
	{
		final I_C_DocType receiptDocType = loadDocType("MMR", false);
		final I_C_DocType shipmentDocType = loadDocType("MMS", true);

		final I_M_HU receiptVhu = createVhu();
		final I_M_InOut receiptInOut = createMinimalInOut(receiptDocType, "CO");
		createHuTraceWithSource(receiptVhu, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-A", receiptInOut, new BigDecimal("100"));

		// the shipped VHU genuinely descends from the receipt's VHU ...
		final I_M_HU shippedVhu = createVhu();
		createHuTraceWithSource(shippedVhu, receiptVhu, productId, HUTraceType.TRANSFORM_LOAD,
				"LOT-A", null, new BigDecimal("24"));
		// ... but its own MATERIAL_SHIPMENT trace was recorded under a different lot
		final I_M_InOut shipmentInOut = createMinimalInOut(shipmentDocType, "CO");
		createHuTraceWithSource(shippedVhu, null, productId, HUTraceType.MATERIAL_SHIPMENT,
				"LOT-B", shipmentInOut, new BigDecimal("-24"));

		scenarioDocNos.put(scenarioName + ".receipt1", receiptInOut.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".shipment", shipmentInOut.getDocumentNo());
	}

	/**
	 * One VHU carries both the MATERIAL_RECEIPT and the MATERIAL_SHIPMENT trace directly, with
	 * no TRANSFORM_LOAD edge at all — received and shipped without repacking. The depth-0 case a
	 * rule that only walks {@code VHU_Source_ID} edges would forget.
	 */
	private void setupSameVhuNoTransform(@NonNull final String scenarioName, @NonNull final ProductId productId)
	{
		final I_C_DocType receiptDocType = loadDocType("MMR", false);
		final I_C_DocType shipmentDocType = loadDocType("MMS", true);

		final I_M_HU vhu = createVhu();
		final I_M_InOut receiptInOut = createMinimalInOut(receiptDocType, "CO");
		createHuTraceWithSource(vhu, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-SAME-VHU", receiptInOut, new BigDecimal("100"));

		final I_M_InOut shipmentInOut = createMinimalInOut(shipmentDocType, "CO");
		createHuTraceWithSource(vhu, null, productId, HUTraceType.MATERIAL_SHIPMENT,
				"LOT-SAME-VHU", shipmentInOut, new BigDecimal("-24"));

		scenarioDocNos.put(scenarioName + ".receipt1", receiptInOut.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".shipment", shipmentInOut.getDocumentNo());
	}

	/**
	 * A receipt's VHU transforms into an intermediate VHU, which transforms again into the
	 * shipped VHU — a two-edge TRANSFORM_LOAD chain. The receipt document must still resolve to
	 * the original receipt, not the intermediate step.
	 */
	private void setupTwoStepTransform(@NonNull final String scenarioName, @NonNull final ProductId productId)
	{
		final I_C_DocType receiptDocType = loadDocType("MMR", false);
		final I_C_DocType shipmentDocType = loadDocType("MMS", true);

		final I_M_HU receiptVhu = createVhu();
		final I_M_InOut receiptInOut = createMinimalInOut(receiptDocType, "CO");
		createHuTraceWithSource(receiptVhu, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-TWO-STEP", receiptInOut, new BigDecimal("100"));

		final I_M_HU intermediateVhu = createVhu();
		createHuTraceWithSource(intermediateVhu, receiptVhu, productId, HUTraceType.TRANSFORM_LOAD,
				"LOT-TWO-STEP", null, new BigDecimal("24"));

		final I_M_HU shippedVhu = createVhu();
		createHuTraceWithSource(shippedVhu, intermediateVhu, productId, HUTraceType.TRANSFORM_LOAD,
				"LOT-TWO-STEP", null, new BigDecimal("24"));
		final I_M_InOut shipmentInOut = createMinimalInOut(shipmentDocType, "CO");
		createHuTraceWithSource(shippedVhu, null, productId, HUTraceType.MATERIAL_SHIPMENT,
				"LOT-TWO-STEP", shipmentInOut, new BigDecimal("-24"));

		scenarioDocNos.put(scenarioName + ".receipt1", receiptInOut.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".shipment", shipmentInOut.getDocumentNo());
	}

	/**
	 * Two receipts and two shipments of one lot with no VHU link anywhere, plus a third shipment
	 * descending from the first receipt (one TRANSFORM_LOAD edge). Shows that candidate suppression
	 * is per (shipment, product, lot), not global across the lot: the third shipment is graph-traced
	 * to receipt1 and must never also appear as a candidate, while the two unlinked shipments remain
	 * candidates for BOTH receipts of the lot.
	 */
	private void setupMixedTracedAndCandidate(@NonNull final String scenarioName, @NonNull final ProductId productId)
	{
		final I_C_DocType receiptDocType = loadDocType("MMR", false);
		final I_C_DocType shipmentDocType = loadDocType("MMS", true);

		final I_M_HU receiptVhu1 = createVhu();
		final I_M_InOut receiptInOut1 = createMinimalInOut(receiptDocType, "CO");
		createHuTraceWithSource(receiptVhu1, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-MIXED", receiptInOut1, new BigDecimal("100"));

		final I_M_HU receiptVhu2 = createVhu();
		final I_M_InOut receiptInOut2 = createMinimalInOut(receiptDocType, "CO");
		createHuTraceWithSource(receiptVhu2, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-MIXED", receiptInOut2, new BigDecimal("50"));

		// shipmentA: no VHU link anywhere — a candidate for BOTH receipts of this lot
		final I_M_HU shipmentVhuA = createVhu();
		final I_M_InOut shipmentInOutA = createMinimalInOut(shipmentDocType, "CO");
		createHuTraceWithSource(shipmentVhuA, null, productId, HUTraceType.MATERIAL_SHIPMENT,
				"LOT-MIXED", shipmentInOutA, new BigDecimal("-30"));

		// shipmentB: same shape — also a candidate for BOTH receipts
		final I_M_HU shipmentVhuB = createVhu();
		final I_M_InOut shipmentInOutB = createMinimalInOut(shipmentDocType, "CO");
		createHuTraceWithSource(shipmentVhuB, null, productId, HUTraceType.MATERIAL_SHIPMENT,
				"LOT-MIXED", shipmentInOutB, new BigDecimal("-20"));

		// shipment3: graph-traced to receipt1 — must be TRACED, and must never also show up as a candidate
		final I_M_HU shippedVhu3 = createVhu();
		createHuTraceWithSource(shippedVhu3, receiptVhu1, productId, HUTraceType.TRANSFORM_LOAD,
				"LOT-MIXED", null, new BigDecimal("24"));
		final I_M_InOut shipmentInOut3 = createMinimalInOut(shipmentDocType, "CO");
		createHuTraceWithSource(shippedVhu3, null, productId, HUTraceType.MATERIAL_SHIPMENT,
				"LOT-MIXED", shipmentInOut3, new BigDecimal("-24"));

		scenarioDocNos.put(scenarioName + ".receipt1", receiptInOut1.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".receipt2", receiptInOut2.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".shipmentA", shipmentInOutA.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".shipmentB", shipmentInOutB.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".shipment3", shipmentInOut3.getDocumentNo());
	}

	/**
	 * A receipt and a shipment of one product, both with {@code lotnumber=NULL} and no VHU link.
	 * Labelled PRODUCT_CANDIDATE — distinct from the lot-sharing candidates of
	 * {@link #setupMixedTracedAndCandidate} — because there is no lot for the two to agree on, only
	 * the product. These are the semantics {@code @Id:S0000.1_HUTrace_BugB} depends on.
	 */
	private void setupNoLotNoLink(@NonNull final String scenarioName, @NonNull final ProductId productId)
	{
		final I_C_DocType receiptDocType = loadDocType("MMR", false);
		final I_C_DocType shipmentDocType = loadDocType("MMS", true);

		final I_M_HU receiptVhu = createVhu();
		final I_M_InOut receiptInOut = createMinimalInOut(receiptDocType, "CO");
		createHuTraceWithSource(receiptVhu, null, productId, HUTraceType.MATERIAL_RECEIPT,
				null /*lotNumber*/, receiptInOut, new BigDecimal("100"));

		final I_M_HU shipmentVhu = createVhu();
		final I_M_InOut shipmentInOut = createMinimalInOut(shipmentDocType, "CO");
		createHuTraceWithSource(shipmentVhu, null, productId, HUTraceType.MATERIAL_SHIPMENT,
				null /*lotNumber*/, shipmentInOut, new BigDecimal("-24"));

		scenarioDocNos.put(scenarioName + ".receipt1", receiptInOut.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".shipment", shipmentInOut.getDocumentNo());
	}

	/**
	 * One receipt document carrying two MATERIAL_RECEIPT traces of the same lot on different VHUs
	 * (e.g. two TUs unloaded together), with a shipment descending from the first of the two VHUs.
	 * The receipt document's total quantity across both VHUs must be reported once, not once per VHU.
	 */
	private void setupReceiptQtyAndDedup(@NonNull final String scenarioName, @NonNull final ProductId productId)
	{
		final I_C_DocType receiptDocType = loadDocType("MMR", false);
		final I_C_DocType shipmentDocType = loadDocType("MMS", true);

		final I_M_InOut receiptInOut = createMinimalInOut(receiptDocType, "CO");
		final I_M_HU receiptVhuA = createVhu();
		createHuTraceWithSource(receiptVhuA, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-DEDUP", receiptInOut, new BigDecimal("60"));
		final I_M_HU receiptVhuB = createVhu();
		createHuTraceWithSource(receiptVhuB, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-DEDUP", receiptInOut, new BigDecimal("40"));

		// the shipment descends from the FIRST of the two receipt VHUs
		final I_M_HU shippedVhu = createVhu();
		createHuTraceWithSource(shippedVhu, receiptVhuA, productId, HUTraceType.TRANSFORM_LOAD,
				"LOT-DEDUP", null, new BigDecimal("24"));
		final I_M_InOut shipmentInOut = createMinimalInOut(shipmentDocType, "CO");
		createHuTraceWithSource(shippedVhu, null, productId, HUTraceType.MATERIAL_SHIPMENT,
				"LOT-DEDUP", shipmentInOut, new BigDecimal("-24"));

		scenarioDocNos.put(scenarioName + ".receipt1", receiptInOut.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".shipment", shipmentInOut.getDocumentNo());
	}

	/**
	 * A shipment whose VHU descends from a receipt document the DIRECT_SALE_DETAIL section may not
	 * report, plus a purchase receipt of the same product and lot with no VHU link to anything.
	 *
	 * <p>The first document is ineligible only because its doctype is {@code IsSOTrx='Y'} (the
	 * section reports only {@code IsSOTrx='N'}); it is loaded as the first active
	 * {@code DocBaseType='MMR'}/{@code IsSOTrx='Y'} doctype by {@code C_DocType_ID} — which one
	 * that is depends on seed data, and the scenario doesn't depend on which.
	 *
	 * <p>The graph links the shipment to this ineligible receipt, but the section may not print it.
	 * The lot-level candidate on the purchase receipt must still be emitted: checking "group already
	 * has a traced receipt" before the receipt's eligibility would silently drop the group entirely.
	 */
	private void setupIneligibleReceiptDoc(@NonNull final String scenarioName, @NonNull final ProductId productId)
	{
		final I_C_DocType purchaseReceiptDocType = loadDocType("MMR", false);
		final I_C_DocType outboundReceiptDocType = loadDocType("MMR", true);
		final I_C_DocType shipmentDocType = loadDocType("MMS", true);

		// the receipt document this section may not report, and the shipment that descends from it
		final I_M_HU ineligibleVhu = createVhu();
		final I_M_InOut ineligibleInOut = createMinimalInOut(outboundReceiptDocType, "CO");
		createHuTraceWithSource(ineligibleVhu, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-INELIGIBLE", ineligibleInOut, new BigDecimal("100"));

		final I_M_HU shippedVhu = createVhu();
		createHuTraceWithSource(shippedVhu, ineligibleVhu, productId, HUTraceType.TRANSFORM_LOAD,
				"LOT-INELIGIBLE", null, new BigDecimal("24"));
		final I_M_InOut shipmentInOut = createMinimalInOut(shipmentDocType, "CO");
		createHuTraceWithSource(shippedVhu, null, productId, HUTraceType.MATERIAL_SHIPMENT,
				"LOT-INELIGIBLE", shipmentInOut, new BigDecimal("-24"));

		// a reportable purchase receipt of the same lot, linked to nothing — the lot-level candidate
		final I_M_HU purchaseVhu = createVhu();
		final I_M_InOut purchaseInOut = createMinimalInOut(purchaseReceiptDocType, "CO");
		createHuTraceWithSource(purchaseVhu, null, productId, HUTraceType.MATERIAL_RECEIPT,
				"LOT-INELIGIBLE", purchaseInOut, new BigDecimal("80"));

		scenarioDocNos.put(scenarioName + ".receipt1", purchaseInOut.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".ineligibleReceipt", ineligibleInOut.getDocumentNo());
		scenarioDocNos.put(scenarioName + ".shipment", shipmentInOut.getDocumentNo());
	}

	// =====================================================================================
	// DB record creation helpers
	// =====================================================================================

	/**
	 * Loads the first active C_DocType with the given DocBaseType and IsSOTrx flag.
	 *
	 * <p>IsSOTrx filtering matters because metasfresh has several doctypes per DocBaseType — MMR
	 * has both inbound ({@code isSOTrx='N'}) and outbound ({@code isSOTrx='Y'}) ones — and section 6
	 * of M_HU_Trace_Report checks {@code receipt_dt.isSOTrx = 'N'} / {@code shipment_dt.isSOTrx = 'Y'}.
	 *
	 * @param docBaseType e.g. "MMR" (Material Receipt) or "MMS" (Material Shipment)
	 * @param isSOTrx     true for sales transactions, false for purchase transactions
	 */
	private I_C_DocType loadDocType(@NonNull final String docBaseType, final boolean isSOTrx)
	{
		final I_C_DocType docType = queryBL.createQueryBuilder(I_C_DocType.class)
				.addEqualsFilter(I_C_DocType.COLUMNNAME_DocBaseType, docBaseType)
				.addEqualsFilter(I_C_DocType.COLUMNNAME_IsSOTrx, isSOTrx)
				.addEqualsFilter(I_C_DocType.COLUMNNAME_IsActive, true)
				.orderBy(I_C_DocType.COLUMNNAME_C_DocType_ID)
				.create()
				.first(I_C_DocType.class);
		assertThat(docType)
				.as("Expected at least one active C_DocType with DocBaseType='%s' and IsSOTrx='%s'", docBaseType, isSOTrx ? "Y" : "N")
				.isNotNull();
		return docType;
	}

	/**
	 * Creates a minimal Virtual Handling Unit (VHU) for use as M_HU_Trace.VHU_ID / M_HU_ID.
	 *
	 * <p>The VHU is created fresh for each call so that trace records reference distinct HUs.
	 */
	private I_M_HU createVhu()
	{
		// Load the Virtual PI (M_HU_PI_ID = 101 is the well-known Virtual PI in metasfresh)
		final I_M_HU_PI_Version virtualPiVersion = queryBL.createQueryBuilder(I_M_HU_PI_Version.class)
				.addEqualsFilter(I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_ID, 101)
				.addEqualsFilter(I_M_HU_PI_Version.COLUMNNAME_IsCurrent, true)
				.orderBy(I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_Version_ID)
				.create()
				.first(I_M_HU_PI_Version.class);

		if (virtualPiVersion != null)
		{
			// Use the standard Virtual PI version
			final I_M_HU vhu = newInstance(I_M_HU.class);
			vhu.setM_HU_PI_Version_ID(virtualPiVersion.getM_HU_PI_Version_ID());
			saveRecord(vhu);
			return vhu;
		}

		// Fallback: create a test-only PI + version
		final I_M_HU_PI testPI = newInstance(I_M_HU_PI.class);
		saveRecord(testPI);

		final I_M_HU_PI_Version testPiVersion = newInstance(I_M_HU_PI_Version.class);
		testPiVersion.setM_HU_PI_ID(testPI.getM_HU_PI_ID());
		testPiVersion.setHU_UnitType(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI);
		testPiVersion.setIsCurrent(true);
		saveRecord(testPiVersion);

		final I_M_HU vhu = newInstance(I_M_HU.class);
		vhu.setM_HU_PI_Version_ID(testPiVersion.getM_HU_PI_Version_ID());
		saveRecord(vhu);
		return vhu;
	}

	/**
	 * Creates a minimal M_InOut record with the given doctype and docstatus.
	 *
	 * <p>Forces DocStatus via direct SQL after saving because M_InOut model interceptors enforce
	 * the DocAction workflow and override a plain {@code DocStatus='CO'} set on the model during save.
	 */
	private I_M_InOut createMinimalInOut(
			@NonNull final I_C_DocType docType,
			@NonNull final String docStatus)
	{
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setC_DocType_ID(docType.getC_DocType_ID());
		inOut.setDocStatus(docStatus);
		inOut.setMovementDate(Timestamp.from(Instant.now()));
		inOut.setM_Warehouse_ID(StepDefConstants.WAREHOUSE_ID.getRepoId());
		inOut.setC_BPartner_ID(StepDefConstants.METASFRESH_AG_BPARTNER_ID.getRepoId());
		inOut.setC_BPartner_Location_ID(StepDefConstants.METASFRESH_AG_BPARTNER_LOCATION_ID.getRepoId());
		inOut.setIsSOTrx(docType.isSOTrx());
		// MovementType must match IsSOTrx: 'C-' for customer shipment, 'V+' for vendor receipt
		inOut.setMovementType(docType.isSOTrx() ? "C-" : "V+");
		saveRecord(inOut);

		// Model validators may reset DocStatus/C_DocType_ID during save; force them (and Processed)
		// via SQL. Section 6 checks isSOTrx, so C_DocType_ID must match the intended doctype.
		DB.executeUpdateAndThrowExceptionOnFail(
				"UPDATE M_InOut SET DocStatus = ?, Processed = 'Y', C_DocType_ID = ? WHERE M_InOut_ID = ?",
				new Object[] { docStatus, docType.getC_DocType_ID(), inOut.getM_InOut_ID() },
				ITrx.TRXNAME_None);

		return inOut;
	}

	/**
	 * Creates a minimal PP_Order record in docstatus='CO' for use in PRODUCTION_RECEIPT traces
	 * (M_HU_Trace_Report requires {@code po.docstatus IN ('CO', 'CL')}).
	 *
	 * <p>Uses direct SQL because the PP_Order model validator requires {@code PP_Product_BOM_ID > 0},
	 * while the report function only needs {@code PP_Order_ID} for its JOIN — a full BOM hierarchy
	 * would be disproportionate to the test's purpose.
	 */
	private I_PP_Order createMinimalPpOrder(@NonNull final ProductId productId)
	{
		final int warehouseId = StepDefConstants.WAREHOUSE_ID.getRepoId();

		// Look up the default locator for the warehouse (normally auto-filled by model validator)
		final int locatorId = DB.getSQLValueEx(
				ITrx.TRXNAME_None,
				"SELECT MIN(m_locator_id) FROM m_locator WHERE m_warehouse_id = ? AND isactive = 'Y'",
				warehouseId);
		assertThat(locatorId).as("Expected at least one active M_Locator for warehouse %s", warehouseId).isGreaterThan(0);

		// Look up valid FK references needed by PP_Order (can't use 0 — FK constraints)
		final int bomId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT MIN(pp_product_bom_id) FROM pp_product_bom WHERE isactive = 'Y'");
		final int workflowId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT MIN(ad_workflow_id) FROM ad_workflow WHERE isactive = 'Y'");
		final int docTypeId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT MIN(c_doctype_id) FROM c_doctype WHERE docbasetype = 'MOP' AND isactive = 'Y'");

		final String documentNo = "TEST-TRACE-" + System.nanoTime();
		final int ppOrderId = DB.getSQLValueEx(
				ITrx.TRXNAME_None,
				"INSERT INTO PP_Order "
						+ "(PP_Order_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
						+ " M_Product_ID, C_UOM_ID, QtyOrdered, QtyDelivered,"
						+ " DateOrdered, DatePromised, DateStartSchedule, M_Warehouse_ID, M_Locator_ID,"
						+ " DocStatus, DocAction, S_Resource_ID, DocumentNo, Processed, Line,"
						+ " PP_Product_BOM_ID, AD_Workflow_ID, C_DocTypeTarget_ID,"
						+ " PriorityRule, Yield, QtyBeforeClose, QtyReject, QtyScrap,"
						+ " ExportStatus, IsApproved, IsPrinted, IsSelected, IsSOTrx,"
						+ " MRP_AllowCleanup, MRP_Generated, MRP_ToDelete, PlanningStatus, IsPickingOrder)"
						+ " VALUES (nextval('pp_order_seq'), ?, ?, 'Y', now(), 100, now(), 100,"
						+ " ?, ?, 1, 0,"
						+ " now(), now(), now(), ?, ?,"
						+ " 'CO', '--', ?, ?, 'Y', 10,"
						+ " ?, ?, ?,"
						+ " 'M', 0, 0, 0, 0,"
						+ " 'PENDING', 'N', 'N', 'N', 'N',"
						+ " 'N', 'N', 'N', 'P', 'N')"
						+ " RETURNING PP_Order_ID",
				Env.getClientId().getRepoId(),
				Env.getOrgId(Env.getCtx()).getRepoId(),
				productId.getRepoId(),
				StepDefConstants.PCE_UOM_ID.getRepoId(),
				warehouseId,
				locatorId,
				StepDefConstants.PLANT_ID.getRepoId(),
				documentNo,
				bomId > 0 ? bomId : 1,
				workflowId > 0 ? workflowId : 1,
				docTypeId > 0 ? docTypeId : 1);

		return InterfaceWrapperHelper.loadOutOfTrx(ppOrderId, I_PP_Order.class);
	}

	/**
	 * Creates a single M_HU_Trace record.
	 *
	 * @param vhu the Virtual HU being traced (used for both VHU_ID and M_HU_ID)
	 */
	private I_M_HU_Trace createHuTrace(
			@NonNull final I_M_HU vhu,
			@NonNull final ProductId productId,
			@NonNull final HUTraceType traceType,
			@Nullable final String lotNumber,
			@Nullable final I_M_InOut inOut,
			@Nullable final I_PP_Order ppOrder)
	{
		return buildAndSaveHuTrace(vhu, null, productId, traceType, lotNumber, inOut, ppOrder, BigDecimal.ONE);
	}

	/** Creates an M_HU_Trace row that also records where its VHU came from (a TRANSFORM_LOAD edge). */
	private I_M_HU_Trace createHuTraceWithSource(
			@NonNull final I_M_HU vhu,
			@Nullable final I_M_HU sourceVhu,
			@NonNull final ProductId productId,
			@NonNull final HUTraceType type,
			@Nullable final String lotNumber,
			@Nullable final I_M_InOut inOut,
			@NonNull final BigDecimal qty)
	{
		return buildAndSaveHuTrace(vhu, sourceVhu, productId, type, lotNumber, inOut, null, qty);
	}

	/**
	 * Shared M_HU_Trace record builder behind {@link #createHuTrace} and
	 * {@link #createHuTraceWithSource} — the two differ only in whether a source VHU / PP_Order
	 * is set and in the qty, so both delegate here instead of duplicating the field-setting.
	 */
	private I_M_HU_Trace buildAndSaveHuTrace(
			@NonNull final I_M_HU vhu,
			@Nullable final I_M_HU sourceVhu,
			@NonNull final ProductId productId,
			@NonNull final HUTraceType traceType,
			@Nullable final String lotNumber,
			@Nullable final I_M_InOut inOut,
			@Nullable final I_PP_Order ppOrder,
			@NonNull final BigDecimal qty)
	{
		final I_M_HU_Trace trace = newInstance(I_M_HU_Trace.class);
		trace.setVHU_ID(vhu.getM_HU_ID());
		trace.setM_HU_ID(vhu.getM_HU_ID());
		if (sourceVhu != null)
		{
			trace.setVHU_Source_ID(sourceVhu.getM_HU_ID());
		}
		trace.setM_Product_ID(productId.getRepoId());
		trace.setC_UOM_ID(StepDefConstants.PCE_UOM_ID.getRepoId());
		trace.setQty(qty);
		trace.setHUTraceType(traceType.getCode());
		trace.setEventTime(Timestamp.from(Instant.now()));
		trace.setVHUStatus("A");
		if (lotNumber != null)
		{
			trace.setLotNumber(lotNumber);
		}
		if (inOut != null)
		{
			trace.setM_InOut_ID(inOut.getM_InOut_ID());
		}
		if (ppOrder != null)
		{
			trace.setPP_Order_ID(ppOrder.getPP_Order_ID());
		}
		saveRecord(trace);
		return trace;
	}

	/** One DIRECT_SALE_DETAIL row, reduced to the fields the scenarios assert. */
	@Value
	private static class DetailRow
	{
		String receiptDocNo;
		String shipmentDocNo;
		String linkBasis;
		BigDecimal menge;
		BigDecimal liefermenge;
	}
}
