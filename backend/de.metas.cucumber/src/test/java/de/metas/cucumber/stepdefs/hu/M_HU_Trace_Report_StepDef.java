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
 * <p>Tests two specific SQL bugs that were fixed:
 * <ul>
 *   <li>Bug A (Section 5 — PRODUCTION_RECEIPT_DETAL): INNER JOIN to m_hu_attribute mhd
 *       excluded products without best-before date. Fixed by LEFT JOIN.</li>
 *   <li>Bug B (Section 6 — DIRECT_SALE_DETAIL): {@code shipment_trace.lotnumber = t.lotnumber}
 *       evaluated to false for NULL lot numbers. Fixed by {@code IS NOT DISTINCT FROM}.</li>
 * </ul>
 */
@RequiredArgsConstructor
public class M_HU_Trace_Report_StepDef
{
	private final M_Product_StepDefData productTable;

	private final HUTraceRepository huTraceRepository = SpringContextHolder.instance.getBean(HUTraceRepository.class);

	/** Maps scenario name → list of HUTraceType (section name) values returned by the report */
	private final Map<String, List<String>> reportResultsByScenario = new HashMap<>();

	// =====================================================================================
	// Setup steps
	// =====================================================================================

	/**
	 * Sets up all DB records required for a specific trace report test scenario.
	 *
	 * <p>Supported TestType values:
	 * <ul>
	 *   <li>{@code DIRECT_SALE_NULL_LOT} — creates MATERIAL_RECEIPT + MATERIAL_SHIPMENT traces
	 *       with {@code lotnumber=NULL}. Tests Bug B fix (IS NOT DISTINCT FROM).</li>
	 *   <li>{@code PRODUCTION_RECEIPT_NO_MHD} — creates PRODUCTION_RECEIPT + PRODUCTION_ISSUE traces
	 *       without MHD (best-before) attribute on the HU. Tests Bug A fix (LEFT JOIN).</li>
	 * </ul>
	 */
	@When("M_HU_Trace_Report test data is set up for scenario {string}:")
	public void setupTraceReportTestData(@NonNull final String scenarioName, @NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final String testType = row.getAsString("TestType");
		final ProductId productId = productTable.getId(row.getAsIdentifier("M_Product_ID"));

		switch (testType)
		{
			case "DIRECT_SALE_NULL_LOT":
				setupDirectSaleNullLot(scenarioName, productId);
				break;
			case "PRODUCTION_RECEIPT_NO_MHD":
				final ProductId rawMaterialProductId = productTable.getId(row.getAsIdentifier("RawMaterial_ID"));
				setupProductionReceiptNoMhd(scenarioName, productId, rawMaterialProductId);
				break;
			default:
				throw new AdempiereException("Unknown TestType: " + testType);
		}
	}

	// =====================================================================================
	// Invoke + assert steps
	// =====================================================================================

	/**
	 * Invokes the {@code M_HU_Trace_Report(?)} SQL function for the product associated with the given
	 * scenario and stores the returned {@code HUTraceType} section names (e.g. {@code PRODUCTION_RECEIPT_DETAL},
	 * {@code DIRECT_SALE_DETAIL}) for later assertion.
	 *
	 * <p>Note: the SQL function has two different columns — {@code HUTraceType} (the section name)
	 * and {@code detail_type} (the sub-record's trace type). We read {@code HUTraceType} because
	 * the feature file assertions reference section names.
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

	// =====================================================================================
	// Private state: scenario → product mapping
	// =====================================================================================

	/** Maps scenario name → product ID (for invoking the report) */
	private final Map<String, ProductId> scenarioProductIds = new HashMap<>();

	// =====================================================================================
	// Private setup helpers
	// =====================================================================================

	/**
	 * Bug B test setup: creates MATERIAL_RECEIPT + MATERIAL_SHIPMENT traces with lotnumber=NULL.
	 *
	 * <p>The DIRECT_SALE_DETAIL section of M_HU_Trace_Report uses:
	 * <pre>
	 * LEFT JOIN M_HU_Trace shipment_trace ON
	 *     shipment_trace.lotnumber IS NOT DISTINCT FROM t.lotnumber   -- Bug B fix
	 *     AND shipment_trace.m_product_id = t.m_product_id
	 *     AND shipment_trace.hutracetype = 'MATERIAL_SHIPMENT'
	 * </pre>
	 * Before the fix ({@code =} instead of {@code IS NOT DISTINCT FROM}), NULL=NULL evaluated
	 * to false, so the shipment_trace was never found, and the INNER JOIN on M_Product
	 * eliminated the row entirely.
	 */
	private void setupDirectSaleNullLot(@NonNull final String scenarioName, @NonNull final ProductId productId)
	{
		scenarioProductIds.put(scenarioName, productId);

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
			@NonNull final String scenarioName,
			@NonNull final ProductId finishedProductId,
			@NonNull final ProductId rawMaterialProductId)
	{
		scenarioProductIds.put(scenarioName, finishedProductId);

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

	// =====================================================================================
	// DB record creation helpers
	// =====================================================================================

	/**
	 * Loads the first active C_DocType with the given DocBaseType and IsSOTrx flag.
	 *
	 * <p>Filtering by IsSOTrx is critical because metasfresh has multiple doctypes per
	 * DocBaseType (e.g. MMR has both standard material receipt with isSOTrx='N' and
	 * customer return receipt with isSOTrx='Y'). Section 6 of M_HU_Trace_Report
	 * checks {@code receipt_dt.isSOTrx = 'N'} and {@code shipment_dt.isSOTrx = 'Y'}.
	 *
	 * @param docBaseType e.g. "MMR" (Material Receipt) or "MMS" (Material Shipment)
	 * @param isSOTrx     true for sales transactions, false for purchase transactions
	 */
	private I_C_DocType loadDocType(@NonNull final String docBaseType, final boolean isSOTrx)
	{
		final I_C_DocType docType = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_DocType.class)
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
		final I_M_HU_PI_Version virtualPiVersion = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_PI_Version.class)
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
	 * <p>Only the fields required by the M_HU_Trace_Report SQL function are set.
	 * Non-critical FK columns (C_BPartner_ID, etc.) use test defaults where possible.
	 *
	 * <p>Uses direct SQL to force DocStatus after saving because M_InOut model interceptors
	 * enforce the DocAction workflow — setting DocStatus='CO' on the model object gets
	 * overridden during save. Our test only needs the M_InOut as a FK reference for the
	 * SQL function's JOIN conditions.
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

		// Force DocStatus, Processed, and C_DocType_ID via SQL — model validators enforce
		// the DocAction workflow and may reset DocStatus and C_DocType_ID during save.
		// Section 6 of M_HU_Trace_Report JOINs C_DocType and checks isSOTrx, so
		// C_DocType_ID must match the intended document type.
		DB.executeUpdateAndThrowExceptionOnFail(
				"UPDATE M_InOut SET DocStatus = ?, Processed = 'Y', C_DocType_ID = ? WHERE M_InOut_ID = ?",
				new Object[] { docStatus, docType.getC_DocType_ID(), inOut.getM_InOut_ID() },
				ITrx.TRXNAME_None);

		return inOut;
	}

	/**
	 * Creates a minimal PP_Order record in docstatus='CO' for use in PRODUCTION_RECEIPT traces.
	 *
	 * <p>The M_HU_Trace_Report SQL function requires:
	 * <ul>
	 *   <li>{@code po.docstatus IN ('CO', 'CL')}</li>
	 * </ul>
	 *
	 * <p>Uses direct SQL because the PP_Order model validator requires PP_Product_BOM_ID &gt; 0,
	 * but the SQL report function only needs PP_Order_ID for its JOIN condition.
	 * Creating a full BOM hierarchy would be disproportionate to the test's purpose.
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
	 * @param vhu       the Virtual HU being traced (used for both VHU_ID and M_HU_ID)
	 * @param productId the product
	 * @param traceType the trace event type
	 * @param lotNumber the lot number (may be null)
	 * @param inOut     the linked M_InOut (may be null)
	 * @param ppOrder   the linked PP_Order (may be null)
	 */
	private void createHuTrace(
			@NonNull final I_M_HU vhu,
			@NonNull final ProductId productId,
			@NonNull final HUTraceType traceType,
			final String lotNumber,
			final I_M_InOut inOut,
			final I_PP_Order ppOrder)
	{
		final I_M_HU_Trace trace = newInstance(I_M_HU_Trace.class);
		trace.setVHU_ID(vhu.getM_HU_ID());
		trace.setM_HU_ID(vhu.getM_HU_ID());
		trace.setM_Product_ID(productId.getRepoId());
		trace.setC_UOM_ID(StepDefConstants.PCE_UOM_ID.getRepoId());
		trace.setQty(BigDecimal.ONE);
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
	}
}
