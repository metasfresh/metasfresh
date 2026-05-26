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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for validating the EPCIS JSON export function
 * ({@code "de.metas.edi".get_epcis_events_json_fn}).
 * <p>
 * Calls the SQL function directly and validates the returned JSON structure
 * including pallets, crates, items, GLNs, SSCC, GRAI, and product GTINs.
 */
@RequiredArgsConstructor
public class EPCIS_JSON_Export_StepDef
{
	private final @NonNull M_InOut_StepDefData inoutTable;
	private final @NonNull C_Order_StepDefData orderTable;
	private final ObjectMapper objectMapper = new ObjectMapper();

	private JsonNode lastEpcisResult;

	/** Tracks M_HU_IDs of LU/TU HUs injected by {@link #assignLuHUsWithSscc18ToInoutLines}; cleaned up in {@link #cleanupInjectedHUs()}. */
	private final List<Integer> injectedLuHuIds = new ArrayList<>();
	/** Tracks M_HU_IDs of TU/VTU HUs (children of injected LUs); cleaned up in {@link #cleanupInjectedHUs()}. */
	private final List<Integer> injectedTuHuIds = new ArrayList<>();
	/** Tracks M_HU_PI_Item_IDs (itemtype='HA') created on the fly for shared-LU HA scenarios. */
	private final List<Integer> injectedHaPiItemIds = new ArrayList<>();

	/**
	 * Calls the {@code get_epcis_events_json_fn} SQL function for the given shipment
	 * and stores the result for subsequent validation steps.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) shipment to export
	 * @cucumber.example
	 * <pre>
	 * When the EPCIS JSON export function is called for M_InOut identified by shipment_1
	 * </pre>
	 */
	@And("^the EPCIS JSON export function is called for M_InOut identified by (.*)$")
	public void callEpcisFunction(@NonNull final String inoutIdentifier)
	{
		final I_M_InOut inout = inoutTable.get(inoutIdentifier);
		final int inoutId = inout.getM_InOut_ID();
		final String sql = "SELECT \"de.metas.edi\".get_epcis_events_json_fn(?)::text";
		final String json = DB.getSQLValueStringEx(Trx.TRXNAME_None, sql, inoutId);

		if (json == null || json.isEmpty())
		{
			throw new AdempiereException("EPCIS JSON export returned null/empty for M_InOut_ID=" + inoutId);
		}

		try
		{
			lastEpcisResult = objectMapper.readTree(json);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed to parse EPCIS JSON", e);
		}
	}

	/**
	 * Validates top-level scalar fields of the EPCIS JSON.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>warehouseValue</b> — (optional) expected warehouse value for SGLN extension<br>
	 *   <b>supplierGLN</b> — (optional) expected supplier GLN<br>
	 *   <b>warehouseGLN</b> — (optional) expected warehouse GLN<br>
	 *   <b>buyerGLN</b> — (optional) expected buyer GLN<br>
	 *   <b>handoverGLN</b> — (optional) expected handover GLN (null-allowed)<br>
	 *   <b>dropshipGLN</b> — (optional) expected dropship GLN<br>
	 *   <b>desadvReference</b> — (optional) expected DESADV document number (null-allowed)<br>
	 *   <b>poReference</b> — (optional) expected PO reference<br>
	 *   <b>palletCount</b> — (optional) expected number of pallets<br>
	 * @cucumber.example
	 * <pre>
	 * Then the EPCIS JSON has:
	 *   | warehouseValue | palletCount |
	 *   | MainWarehouse  | 1           |
	 * </pre>
	 */
	@Then("the EPCIS JSON has:")
	public void validateEpcisTopLevel(@NonNull final DataTable dataTable)
	{
		assertThat(lastEpcisResult).as("EPCIS JSON result must exist (call the export function first)").isNotNull();

		DataTableRows.of(dataTable).forEach(row -> {
			row.getAsOptionalString("warehouseValue")
					.ifPresent(expected -> assertThat(lastEpcisResult.path("warehouseValue").asText())
							.as("warehouseValue").isEqualTo(expected));

			row.getAsOptionalString("supplierGLN")
					.ifPresent(expected -> assertJsonField("supplierGLN", expected));

			row.getAsOptionalString("warehouseGLN")
					.ifPresent(expected -> assertJsonField("warehouseGLN", expected));

			row.getAsOptionalString("buyerGLN")
					.ifPresent(expected -> assertJsonField("buyerGLN", expected));

			row.getAsOptionalString("handoverGLN")
					.ifPresent(expected -> assertJsonFieldNullable("handoverGLN", expected));

			row.getAsOptionalString("dropshipGLN")
					.ifPresent(expected -> assertJsonFieldNullable("dropshipGLN", expected));

			row.getAsOptionalString("desadvReference")
					.ifPresent(expected -> assertJsonFieldNullable("desadvReference", expected));

			row.getAsOptionalString("poReference")
					.ifPresent(expected -> assertJsonField("poReference", expected));

			row.getAsOptionalInt("palletCount")
					.ifPresent(expected -> assertThat(lastEpcisResult.path("pallets").size())
							.as("palletCount").isEqualTo(expected));
		});
	}

	/**
	 * Validates pallet-level fields in the EPCIS JSON.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>palletIndex</b> — (required) 0-based index into the pallets array<br>
	 *   <b>sscc</b> — (optional) expected SSCC18 value<br>
	 *   <b>crateCount</b> — (optional) expected number of crates<br>
	 * @cucumber.example
	 * <pre>
	 * Then the EPCIS JSON pallet has:
	 *   | palletIndex | sscc               | crateCount |
	 *   | 0           | 012345670010000005 | 2          |
	 * </pre>
	 */
	@Then("the EPCIS JSON pallet has:")
	public void validatePallet(@NonNull final DataTable dataTable)
	{
		assertThat(lastEpcisResult).as("EPCIS JSON result must exist").isNotNull();
		final JsonNode pallets = lastEpcisResult.path("pallets");
		assertThat(pallets.isArray()).as("pallets should be an array").isTrue();

		DataTableRows.of(dataTable).forEach(row -> {
			final int palletIndex = row.getAsInt("palletIndex");
			assertThat(palletIndex).as("palletIndex").isLessThan(pallets.size());
			final JsonNode pallet = pallets.get(palletIndex);

			row.getAsOptionalString("sscc")
					.ifPresent(expected -> assertThat(pallet.path("sscc").asText())
							.as("pallet[%d].sscc", palletIndex).isEqualTo(expected));

			row.getAsOptionalInt("crateCount")
					.ifPresent(expected -> assertThat(pallet.path("crates").size())
							.as("pallet[%d].crateCount", palletIndex).isEqualTo(expected));
		});
	}

	/**
	 * Validates crate-level fields in the EPCIS JSON.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>palletIndex</b> — (required) 0-based index into the pallets array<br>
	 *   <b>crateIndex</b> — (required) 0-based index into the crates array<br>
	 *   <b>grai</b> — (optional) expected GRAI value<br>
	 *   <b>lotNumber</b> — (optional) expected lot number<br>
	 *   <b>bestBeforeDate</b> — (optional) expected best-before date<br>
	 *   <b>itemCount</b> — (optional) expected number of items in this crate<br>
	 * @cucumber.example
	 * <pre>
	 * Then the EPCIS JSON crate has:
	 *   | palletIndex | crateIndex | grai                                  | lotNumber | itemCount |
	 *   | 0           | 0          | urn:epc:id:grai:7613204.00307.0000001 | LOT-A     | 1         |
	 * </pre>
	 */
	@Then("the EPCIS JSON crate has:")
	public void validateCrate(@NonNull final DataTable dataTable)
	{
		assertThat(lastEpcisResult).as("EPCIS JSON result must exist").isNotNull();

		DataTableRows.of(dataTable).forEach(row -> {
			final int palletIndex = row.getAsInt("palletIndex");
			final int crateIndex = row.getAsInt("crateIndex");

			final JsonNode crate = lastEpcisResult.path("pallets").get(palletIndex).path("crates").get(crateIndex);
			assertThat(crate).as("pallet[%d].crate[%d]", palletIndex, crateIndex).isNotNull();

			row.getAsOptionalString("grai")
					.ifPresent(expected -> assertThat(crate.path("grai").asText())
							.as("crate grai").isEqualTo(expected));

			row.getAsOptionalString("lotNumber")
					.ifPresent(expected -> assertThat(crate.path("lotNumber").asText())
							.as("crate lotNumber").isEqualTo(expected));

			row.getAsOptionalString("bestBeforeDate")
					.ifPresent(expected -> assertThat(crate.path("bestBeforeDate").asText())
							.as("crate bestBeforeDate").isEqualTo(expected));

			row.getAsOptionalInt("itemCount")
					.ifPresent(expected -> assertThat(crate.path("items").size())
							.as("crate itemCount").isEqualTo(expected));
		});
	}

	/**
	 * Validates item-level fields in the EPCIS JSON (product/quantity within a crate).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>palletIndex</b> — (required) 0-based index into the pallets array<br>
	 *   <b>crateIndex</b> — (required) 0-based index into the crates array<br>
	 *   <b>itemIndex</b> — (required) 0-based index into the items array<br>
	 *   <b>cuGTIN</b> — (optional) expected CU GTIN from M_Product.GTIN<br>
	 *   <b>tuGTIN</b> — (optional) expected TU GTIN from PI Item Product<br>
	 *   <b>quantity</b> — (optional) expected quantity<br>
	 *   <b>uom</b> — (optional) expected UOM (X12DE355 code, e.g. PCE, KGM)<br>
	 * @cucumber.example
	 * <pre>
	 * Then the EPCIS JSON item has:
	 *   | palletIndex | crateIndex | itemIndex | cuGTIN        | quantity | uom |
	 *   | 0           | 0          | 0         | 7640134460001 | 10       | PCE |
	 * </pre>
	 */
	@Then("the EPCIS JSON item has:")
	public void validateItem(@NonNull final DataTable dataTable)
	{
		assertThat(lastEpcisResult).as("EPCIS JSON result must exist").isNotNull();

		DataTableRows.of(dataTable).forEach(row -> {
			final int palletIndex = row.getAsInt("palletIndex");
			final int crateIndex = row.getAsInt("crateIndex");
			final int itemIndex = row.getAsInt("itemIndex");

			final JsonNode item = lastEpcisResult
					.path("pallets").get(palletIndex)
					.path("crates").get(crateIndex)
					.path("items").get(itemIndex);
			assertThat(item).as("pallet[%d].crate[%d].item[%d]", palletIndex, crateIndex, itemIndex).isNotNull();

			row.getAsOptionalString("cuGTIN")
					.ifPresent(expected -> assertThat(item.path("cuGTIN").asText())
							.as("item cuGTIN").isEqualTo(expected));

			row.getAsOptionalString("tuGTIN")
					.ifPresent(expected -> assertThat(item.path("tuGTIN").asText())
							.as("item tuGTIN").isEqualTo(expected));

			row.getAsOptionalString("quantity")
					.ifPresent(expected -> assertThat(item.path("quantity").asText())
							.as("item quantity").isEqualTo(expected));

			row.getAsOptionalString("uom")
					.ifPresent(expected -> assertThat(item.path("uom").asText())
							.as("item uom").isEqualTo(expected));
		});
	}

	/**
	 * Validates jsonb-array fields in the EPCIS JSON that carry arrays of string values.
	 * Validates {@code desadvReferences[]} and {@code poReferences[]} array fields in the EPCIS JSON.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>field</b> — (required) name of the top-level array field (e.g. {@code desadvReferences}, {@code poReferences})<br>
	 *   <b>expectedSize</b> — (required) expected number of elements in the array<br>
	 *   <b>containsValue</b> — (optional) a value that must appear somewhere in the array<br>
	 * @cucumber.example
	 * <pre>
	 * Then the EPCIS JSON array field has:
	 *   | field             | expectedSize | containsValue     |
	 *   | desadvReferences  | 2            | DESADV-2026-00001 |
	 *   | poReferences      | 2            | PO_A_S29231       |
	 * </pre>
	 */
	@Then("the EPCIS JSON array field has:")
	public void validateArrayField(@NonNull final DataTable dataTable)
	{
		assertThat(lastEpcisResult).as("EPCIS JSON result must exist (call the export function first)").isNotNull();

		DataTableRows.of(dataTable).forEach(row -> assertEpcisArrayField(row));
	}

	/**
	 * Creates minimal LU HUs with the given SSCC18 values and assigns them to the M_InOutLines of the
	 * given shipment via {@code M_HU_Assignment}. Exactly one LU is created per data-table row and
	 * assigned to all M_InOutLines of the shipment; the EPCIS pallet-discovery CTE uses
	 * {@code DISTINCT m_lu_hu_id} so each distinct LU appears exactly once in {@code pallets[]}.
	 *
	 * <p>Background: {@code QuantityType=D} shipments do not create real M_HU records, so
	 * {@code pallets[]} is always empty. This step injects the required M_HU + M_HU_Attribute +
	 * M_HU_Assignment rows without needing the full picking workflow, enabling an end-to-end test
	 * of the EPCIS pallet-array shape for consolidated multi-source-order shipments.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>sscc18</b> — (required) 18-digit SSCC value to set on the LU HU
	 * @cucumber.example
	 * <pre>
	 * And real LU HUs with SSCC18 are assigned to all inout lines of M_InOut identified by io_130:
	 *   | sscc18             |
	 *   | 987654321000000016 |
	 *   | 987654321000000023 |
	 * </pre>
	 */
	@And("^real LU HUs with SSCC18 are assigned to all inout lines of M_InOut identified by (.*)$")
	public void assignLuHUsWithSscc18ToInoutLines(@NonNull final String inoutIdentifier, @NonNull final DataTable dataTable)
	{
		final I_M_InOut inout = inoutTable.get(inoutIdentifier);
		final int inoutId = inout.getM_InOut_ID();

		// Look up AD_Table_ID for M_InOutLine and the SSCC18 M_Attribute_ID once
		final int inoutLineTableId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"SELECT ad_table_id FROM ad_table WHERE tablename='M_InOutLine'");
		assertThat(inoutLineTableId).as("AD_Table_ID for M_InOutLine").isGreaterThan(0);

		final int sscc18AttributeId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"SELECT m_attribute_id FROM m_attribute WHERE value='SSCC18' LIMIT 1");
		assertThat(sscc18AttributeId).as("M_Attribute_ID for SSCC18").isGreaterThan(0);

		// Get a suitable LU M_HU_PI_Version that has a TU child PI item, plus the TU version and PI item IDs.
		// The EPCIS function's individual_tu_ids CTE requires:
		//   M_HU_Item (itemtype='HU') on the LU → M_HU (child TU) via M_HU.m_hu_item_parent_id.
		// We therefore need luPIVersionId, tuPIVersionId, and m_hu_pi_item_id in one lookup.
		final int[] piVersionAndItem = new int[3]; // [0]=luPIVersionId, [1]=tuPIVersionId, [2]=luPiItemId
		try (final java.sql.PreparedStatement pstmt = DB.prepareStatement(
				"SELECT piv_lu.m_hu_pi_version_id, piv_tu.m_hu_pi_version_id, pii.m_hu_pi_item_id"
						+ " FROM m_hu_pi_version piv_lu"
						+ " JOIN m_hu_pi_item pii ON pii.m_hu_pi_version_id = piv_lu.m_hu_pi_version_id"
						+ "   AND pii.itemtype = 'HU' AND pii.isactive = 'Y'"
						+ " JOIN m_hu_pi pih ON pih.m_hu_pi_id = pii.included_hu_pi_id"
						+ " JOIN m_hu_pi_version piv_tu ON piv_tu.m_hu_pi_id = pih.m_hu_pi_id"
						+ "   AND piv_tu.iscurrent = 'Y' AND piv_tu.hu_unittype = 'TU'"
						+ " WHERE piv_lu.iscurrent = 'Y' AND piv_lu.hu_unittype = 'LU' AND piv_lu.isactive = 'Y'"
						+ " LIMIT 1",
				Trx.TRXNAME_None))
		{
			try (final ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
				{
					throw new AdempiereException("No current LU M_HU_PI_Version with a TU child PI item found");
				}
				piVersionAndItem[0] = rs.getInt(1);
				piVersionAndItem[1] = rs.getInt(2);
				piVersionAndItem[2] = rs.getInt(3);
			}
		}
		catch (final SQLException e)
		{
			throw new AdempiereException("Failed to look up LU/TU PI version and PI item", e);
		}
		final int luPIVersionId = piVersionAndItem[0];
		final int tuPIVersionId = piVersionAndItem[1];
		final int luPiItemId = piVersionAndItem[2];
		assertThat(luPIVersionId).as("A current LU M_HU_PI_Version must exist").isGreaterThan(0);
		assertThat(tuPIVersionId).as("A current TU M_HU_PI_Version (child of LU) must exist").isGreaterThan(0);
		assertThat(luPiItemId).as("M_HU_PI_Item linking LU to TU must exist").isGreaterThan(0);

		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		final int adOrgId = Env.getAD_Org_ID(Env.getCtx());
		if (adOrgId <= 0)
		{
			throw new AdempiereException("AD_Org_ID from context is 0; context may not be initialised");
		}

		// Collect all M_InOutLine_IDs for this shipment via PreparedStatement
		final List<Integer> inoutLineIds = new ArrayList<>();
		try (final java.sql.PreparedStatement pstmt = DB.prepareStatement(
				"SELECT m_inoutline_id FROM m_inoutline WHERE m_inout_id=? ORDER BY m_inoutline_id",
				Trx.TRXNAME_None))
		{
			pstmt.setInt(1, inoutId);
			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					inoutLineIds.add(rs.getInt(1));
				}
			}
		}
		catch (final SQLException e)
		{
			throw new AdempiereException("Failed to load M_InOutLine IDs for M_InOut_ID=" + inoutId, e);
		}
		assertThat(inoutLineIds).as("M_InOutLine records for M_InOut_ID=" + inoutId).isNotEmpty();

		dataTable.asMaps().forEach(rowMap -> {
			final String sscc18 = rowMap.get("sscc18");
			assertThat(sscc18).as("sscc18 column must be present in the data table row").isNotBlank();

			// INSERT a minimal LU M_HU record; value must be unique and non-null
			final int luHuId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"INSERT INTO m_hu (m_hu_id, ad_client_id, ad_org_id, m_hu_pi_version_id, hustatus, isactive,"
							+ " value, created, createdby, updated, updatedby)"
							+ " VALUES (nextval('m_hu_seq')," + adClientId + "," + adOrgId + "," + luPIVersionId + ",'E','Y',"
							+ " 'EPCIS_TEST_LU_' || nextval('m_hu_seq'), now(), 100, now(), 100)"
							+ " RETURNING m_hu_id");
			assertThat(luHuId).as("Newly created LU M_HU_ID").isGreaterThan(0);

			// INSERT the SSCC18 attribute value on the LU
			final int huAttrId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"SELECT nextval('m_hu_attribute_seq')");
			DB.executeUpdateAndThrowExceptionOnFail(
					"INSERT INTO m_hu_attribute"
							+ " (m_hu_attribute_id, ad_client_id, ad_org_id, m_hu_id, m_attribute_id, value, isactive,"
							+ " created, createdby, updated, updatedby)"
							+ " VALUES (" + huAttrId + "," + adClientId + "," + adOrgId + "," + luHuId + "," + sscc18AttributeId
							+ ",'" + sscc18 + "','Y', now(), 100, now(), 100)",
					Trx.TRXNAME_None);

			// Create the child TU FIRST so the assignment rows below can carry m_tu_hu_id.
			// The EPCIS function's individual_tu_ids CTE requires:
			//   M_HU_Item (itemtype='HU') on the LU → M_HU (child TU) via M_HU.m_hu_item_parent_id.
			// Additionally, since me03#29231 the function gates TUs by an EXISTS check against
			// m_hu_assignment.m_tu_hu_id (or .vhu_id) referencing the TU.
			final int huItemId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"INSERT INTO m_hu_item"
							+ " (m_hu_item_id, ad_client_id, ad_org_id, m_hu_id, m_hu_pi_item_id, itemtype, qty, isactive,"
							+ " created, createdby, updated, updatedby)"
							+ " VALUES (nextval('m_hu_item_seq')," + adClientId + "," + adOrgId + "," + luHuId + "," + luPiItemId
							+ ",'HU', 1,'Y', now(), 100, now(), 100)"
							+ " RETURNING m_hu_item_id");
			assertThat(huItemId).as("Newly created M_HU_Item_ID for LU→TU link").isGreaterThan(0);

			final int tuHuId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"INSERT INTO m_hu"
							+ " (m_hu_id, ad_client_id, ad_org_id, m_hu_pi_version_id, m_hu_item_parent_id, hustatus, isactive,"
							+ " value, created, createdby, updated, updatedby)"
							+ " VALUES (nextval('m_hu_seq')," + adClientId + "," + adOrgId + "," + tuPIVersionId + "," + huItemId
							+ ",'E','Y',"
							+ " 'EPCIS_TEST_TU_' || nextval('m_hu_seq'), now(), 100, now(), 100)"
							+ " RETURNING m_hu_id");
			assertThat(tuHuId).as("Newly created TU M_HU_ID (child of LU " + luHuId + ")").isGreaterThan(0);

			// INSERT M_HU_Assignment rows linking this LU+TU to every M_InOutLine of the shipment.
			// m_tu_hu_id is required by the EPCIS individual_tu_ids EXISTS gate (me03#29231).
			for (final int inoutLineId : inoutLineIds)
			{
				final int assignId = DB.getSQLValueEx(Trx.TRXNAME_None,
						"SELECT nextval('m_hu_assignment_seq')");
				DB.executeUpdateAndThrowExceptionOnFail(
						"INSERT INTO m_hu_assignment"
								+ " (m_hu_assignment_id, ad_client_id, ad_org_id, ad_table_id, record_id, m_hu_id, m_lu_hu_id, m_tu_hu_id, isactive,"
								+ " created, createdby, updated, updatedby)"
								+ " VALUES (" + assignId + "," + adClientId + "," + adOrgId + "," + inoutLineTableId + ","
								+ inoutLineId + "," + luHuId + "," + luHuId + "," + tuHuId + ",'Y', now(), 100, now(), 100)",
						Trx.TRXNAME_None);
			}

			// Track for cleanup in @After so these test HUs don't pollute other scenarios
			injectedLuHuIds.add(luHuId);
			injectedTuHuIds.add(tuHuId);
		});
	}

	/**
	 * Asserts that the {@code pallets[]} array in the last EPCIS JSON result contains exactly the
	 * given SSCC18 values (order-independent).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>sscc18</b> — (required) expected SSCC18 value; one row per expected pallet
	 * @cucumber.example
	 * <pre>
	 * Then the EPCIS JSON pallets contain SSCC18 values in any order:
	 *   | sscc18             |
	 *   | 987654321000000016 |
	 *   | 987654321000000023 |
	 * </pre>
	 */
	@Then("the EPCIS JSON pallets contain SSCC18 values in any order:")
	public void validatePalletsContainSscc18Values(@NonNull final DataTable dataTable)
	{
		assertThat(lastEpcisResult).as("EPCIS JSON result must exist (call the export function first)").isNotNull();

		final JsonNode pallets = lastEpcisResult.path("pallets");
		assertThat(pallets.isArray()).as("pallets must be a JSON array").isTrue();

		final List<String> expectedSscc18Values = new ArrayList<>();
		dataTable.asMaps().forEach(rowMap -> expectedSscc18Values.add(rowMap.get("sscc18")));

		assertThat(pallets.size())
				.as("pallets[] must contain exactly %d entries", expectedSscc18Values.size())
				.isEqualTo(expectedSscc18Values.size());

		final List<String> actualSscc18Values = new ArrayList<>();
		pallets.forEach(pallet -> actualSscc18Values.add(pallet.path("sscc").asText()));

		assertThat(actualSscc18Values)
				.as("pallets[] sscc18 values (any order)")
				.containsExactlyInAnyOrderElementsOf(expectedSscc18Values);
	}

	/**
	 * Creates minimal LU HUs with the given SSCC18 values and assigns them <em>only</em> to the
	 * M_InOutLines of the given shipment that belong to the specified source C_Order. This is the
	 * per-order variant of {@link #assignLuHUsWithSscc18ToInoutLines}: in an n:m consolidated
	 * shipment each LU is linked to exactly one source order so that the EPCIS function's
	 * {@code pallet_list} CTE resolves the correct per-LU POReference without cross-order leakage.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>sscc18</b> — (required) 18-digit SSCC value to set on the LU HU<br>
	 *   <b>C_Order_ID</b> — (required, identifier-ref) source order whose InOutLines this LU should be assigned to
	 * @cucumber.example
	 * <pre>
	 * And real LU HUs with SSCC18 are assigned to inout lines by source order of M_InOut identified by io_130:
	 *   | sscc18             | C_Order_ID    |
	 *   | 987654321000000016 | oA_S29231_130 |
	 *   | 987654321000000023 | oB_S29231_130 |
	 * </pre>
	 */
	@And("^real LU HUs with SSCC18 are assigned to inout lines by source order of M_InOut identified by (.*)$")
	public void assignLuHUsWithSscc18ToInoutLinesBySourceOrder(@NonNull final String inoutIdentifier,
	                                                           @NonNull final DataTable dataTable)
	{
		final I_M_InOut inout = inoutTable.get(inoutIdentifier);
		final int inoutId = inout.getM_InOut_ID();

		final int inoutLineTableId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"SELECT ad_table_id FROM ad_table WHERE tablename='M_InOutLine'");
		assertThat(inoutLineTableId).as("AD_Table_ID for M_InOutLine").isGreaterThan(0);

		final int sscc18AttributeId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"SELECT m_attribute_id FROM m_attribute WHERE value='SSCC18' LIMIT 1");
		assertThat(sscc18AttributeId).as("M_Attribute_ID for SSCC18").isGreaterThan(0);

		final int[] piVersionAndItem = new int[3]; // [0]=luPIVersionId, [1]=tuPIVersionId, [2]=luPiItemId
		try (final java.sql.PreparedStatement pstmt = DB.prepareStatement(
				"SELECT piv_lu.m_hu_pi_version_id, piv_tu.m_hu_pi_version_id, pii.m_hu_pi_item_id"
						+ " FROM m_hu_pi_version piv_lu"
						+ " JOIN m_hu_pi_item pii ON pii.m_hu_pi_version_id = piv_lu.m_hu_pi_version_id"
						+ "   AND pii.itemtype = 'HU' AND pii.isactive = 'Y'"
						+ " JOIN m_hu_pi pih ON pih.m_hu_pi_id = pii.included_hu_pi_id"
						+ " JOIN m_hu_pi_version piv_tu ON piv_tu.m_hu_pi_id = pih.m_hu_pi_id"
						+ "   AND piv_tu.iscurrent = 'Y' AND piv_tu.hu_unittype = 'TU'"
						+ " WHERE piv_lu.iscurrent = 'Y' AND piv_lu.hu_unittype = 'LU' AND piv_lu.isactive = 'Y'"
						+ " LIMIT 1",
				Trx.TRXNAME_None))
		{
			try (final ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
				{
					throw new AdempiereException("No current LU M_HU_PI_Version with a TU child PI item found");
				}
				piVersionAndItem[0] = rs.getInt(1);
				piVersionAndItem[1] = rs.getInt(2);
				piVersionAndItem[2] = rs.getInt(3);
			}
		}
		catch (final SQLException e)
		{
			throw new AdempiereException("Failed to look up LU/TU PI version and PI item", e);
		}
		final int luPIVersionId = piVersionAndItem[0];
		final int tuPIVersionId = piVersionAndItem[1];
		final int luPiItemId = piVersionAndItem[2];
		assertThat(luPIVersionId).as("A current LU M_HU_PI_Version must exist").isGreaterThan(0);

		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		final int adOrgId = Env.getAD_Org_ID(Env.getCtx());
		if (adOrgId <= 0)
		{
			throw new AdempiereException("AD_Org_ID from context is 0; context may not be initialised");
		}

		DataTableRows.of(dataTable).forEach(row -> {
			final String sscc18 = row.getAsString("sscc18");
			final I_C_Order sourceOrder = orderTable.get(row.getAsIdentifier("C_Order_ID"));
			final int sourceOrderId = sourceOrder.getC_Order_ID();

			// Collect only the M_InOutLine_IDs for this shipment that belong to this source order
			final List<Integer> inoutLineIds = new ArrayList<>();
			try (final java.sql.PreparedStatement pstmt = DB.prepareStatement(
					"SELECT iol.m_inoutline_id"
							+ " FROM m_inoutline iol"
							+ " JOIN c_orderline ol ON ol.c_orderline_id = iol.c_orderline_id"
							+ " WHERE iol.m_inout_id = ? AND ol.c_order_id = ?"
							+ " ORDER BY iol.m_inoutline_id",
					Trx.TRXNAME_None))
			{
				pstmt.setInt(1, inoutId);
				pstmt.setInt(2, sourceOrderId);
				try (final ResultSet rs = pstmt.executeQuery())
				{
					while (rs.next())
					{
						inoutLineIds.add(rs.getInt(1));
					}
				}
			}
			catch (final SQLException e)
			{
				throw new AdempiereException("Failed to load M_InOutLine IDs for M_InOut_ID=" + inoutId
						+ " and C_Order_ID=" + sourceOrderId, e);
			}
			assertThat(inoutLineIds)
					.as("M_InOutLine records for M_InOut_ID=%d and C_Order_ID=%d", inoutId, sourceOrderId)
					.isNotEmpty();

			// INSERT a minimal LU M_HU record
			final int luHuId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"INSERT INTO m_hu (m_hu_id, ad_client_id, ad_org_id, m_hu_pi_version_id, hustatus, isactive,"
							+ " value, created, createdby, updated, updatedby)"
							+ " VALUES (nextval('m_hu_seq')," + adClientId + "," + adOrgId + "," + luPIVersionId + ",'E','Y',"
							+ " 'EPCIS_TEST_LU_' || nextval('m_hu_seq'), now(), 100, now(), 100)"
							+ " RETURNING m_hu_id");
			assertThat(luHuId).as("Newly created LU M_HU_ID").isGreaterThan(0);

			// INSERT the SSCC18 attribute value on the LU
			final int huAttrId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"SELECT nextval('m_hu_attribute_seq')");
			DB.executeUpdateAndThrowExceptionOnFail(
					"INSERT INTO m_hu_attribute"
							+ " (m_hu_attribute_id, ad_client_id, ad_org_id, m_hu_id, m_attribute_id, value, isactive,"
							+ " created, createdby, updated, updatedby)"
							+ " VALUES (" + huAttrId + "," + adClientId + "," + adOrgId + "," + luHuId + "," + sscc18AttributeId
							+ ",'" + sscc18 + "','Y', now(), 100, now(), 100)",
					Trx.TRXNAME_None);

			// Create the child TU FIRST so the assignment rows below can carry m_tu_hu_id
			// (required by the individual_tu_ids EXISTS gate since me03#29231).
			final int huItemId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"INSERT INTO m_hu_item"
							+ " (m_hu_item_id, ad_client_id, ad_org_id, m_hu_id, m_hu_pi_item_id, itemtype, qty, isactive,"
							+ " created, createdby, updated, updatedby)"
							+ " VALUES (nextval('m_hu_item_seq')," + adClientId + "," + adOrgId + "," + luHuId + "," + luPiItemId
							+ ",'HU', 1,'Y', now(), 100, now(), 100)"
							+ " RETURNING m_hu_item_id");
			assertThat(huItemId).as("Newly created M_HU_Item_ID for LU→TU link").isGreaterThan(0);

			final int tuHuId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"INSERT INTO m_hu"
							+ " (m_hu_id, ad_client_id, ad_org_id, m_hu_pi_version_id, m_hu_item_parent_id, hustatus, isactive,"
							+ " value, created, createdby, updated, updatedby)"
							+ " VALUES (nextval('m_hu_seq')," + adClientId + "," + adOrgId + "," + tuPIVersionId + "," + huItemId
							+ ",'E','Y',"
							+ " 'EPCIS_TEST_TU_' || nextval('m_hu_seq'), now(), 100, now(), 100)"
							+ " RETURNING m_hu_id");
			assertThat(tuHuId).as("Newly created TU M_HU_ID (child of LU " + luHuId + ")").isGreaterThan(0);

			// INSERT M_HU_Assignment rows linking this LU+TU only to the InOutLines of its source order
			for (final int inoutLineId : inoutLineIds)
			{
				final int assignId = DB.getSQLValueEx(Trx.TRXNAME_None,
						"SELECT nextval('m_hu_assignment_seq')");
				DB.executeUpdateAndThrowExceptionOnFail(
						"INSERT INTO m_hu_assignment"
								+ " (m_hu_assignment_id, ad_client_id, ad_org_id, ad_table_id, record_id, m_hu_id, m_lu_hu_id, m_tu_hu_id, isactive,"
								+ " created, createdby, updated, updatedby)"
								+ " VALUES (" + assignId + "," + adClientId + "," + adOrgId + "," + inoutLineTableId + ","
								+ inoutLineId + "," + luHuId + "," + luHuId + "," + tuHuId + ",'Y', now(), 100, now(), 100)",
						Trx.TRXNAME_None);
			}

			injectedLuHuIds.add(luHuId);
			injectedTuHuIds.add(tuHuId);
		});
	}

	/**
	 * Asserts that every crate (TU) in the pallet identified by SSCC18 has a dummy GRAI whose middle
	 * segment contains the expected sanitized POReference string. Used to verify that the
	 * {@code get_epcis_events_json_fn} derives the per-LU POReference from the source order rather
	 * than leaking another order's POReference in n:m consolidated shipments.
	 *
	 * <p>The GRAI is structured as {@code <prefix><poref_sanitized><2-digit-counter>}. This step
	 * checks that {@code grai.contains(expectedPOReferenceSanitized)} — i.e. the middle segment
	 * appears somewhere in the GRAI string.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>sscc18</b> — (required) SSCC18 of the pallet to inspect<br>
	 *   <b>ExpectedPOReferenceSanitized</b> — (required) expected sanitized POReference substring
	 *     (≤10 chars, only [A-Za-z0-9_] after sanitization; {@code _} replaces any non-alphanumeric/hyphen char)
	 * @cucumber.example
	 * <pre>
	 * Then the EPCIS JSON pallets have dummy GRAIs containing the source order POReference:
	 *   | sscc18             | ExpectedPOReferenceSanitized |
	 *   | 987654321000000016 | PO_A_S2923                   |
	 *   | 987654321000000023 | PO_B_S2923                   |
	 * </pre>
	 */
	@Then("the EPCIS JSON pallets have dummy GRAIs containing the source order POReference:")
	public void validatePalletDummyGraiContainsPoreference(@NonNull final DataTable dataTable)
	{
		assertThat(lastEpcisResult).as("EPCIS JSON result must exist (call the export function first)").isNotNull();

		final JsonNode pallets = lastEpcisResult.path("pallets");
		assertThat(pallets.isArray()).as("pallets must be a JSON array").isTrue();

		DataTableRows.of(dataTable).forEach(row -> {
			final String sscc18 = row.getAsString("sscc18");
			final String expectedPoRefSanitized = row.getAsString("ExpectedPOReferenceSanitized");

			// Find the pallet with this SSCC18
			JsonNode matchedPallet = null;
			for (final JsonNode pallet : pallets)
			{
				if (sscc18.equals(pallet.path("sscc").asText()))
				{
					matchedPallet = pallet;
					break;
				}
			}
			assertThat(matchedPallet)
					.as("Pallet with SSCC18=%s must exist in pallets[]", sscc18)
					.isNotNull();

			// Every crate in this pallet must have a GRAI containing the expected POReference
			final JsonNode crates = matchedPallet.path("crates");
			assertThat(crates.isArray()).as("crates must be a JSON array for pallet sscc=%s", sscc18).isTrue();
			assertThat(crates.size()).as("pallet sscc=%s must have at least one crate", sscc18).isGreaterThan(0);

			for (int i = 0; i < crates.size(); i++)
			{
				final String grai = crates.get(i).path("grai").asText();
				assertThat(grai)
						.as("pallet sscc=%s crate[%d] grai must contain '%s'", sscc18, i, expectedPoRefSanitized)
						.contains(expectedPoRefSanitized);
			}
		});
	}

	/**
	 * Creates ONE shared LU with the given SSCC18 and attaches one HA aggregate per data-table row,
	 * using real metasfresh BL ({@code InterfaceWrapperHelper} for HU and M_HU_Item record creation,
	 * {@code IHUAssignmentBL} for assignment rows). This mirrors the DB shape that mobile picking
	 * produces in production for the LAF1010-3 case (one physical pallet, two crate allocations
	 * for two different M_InOuts) without using any raw {@code DB.executeUpdate} SQL.
	 *
	 * <p>HA {@code M_HU_Item} records do NOT reference an {@code M_HU_PI_Item} (the PI item table
	 * does not accept {@code ItemType='HA'}). This matches {@code HUAndItemsDAO.createAggregateHUItem()}.
	 *
	 * <p>The step creates the shared LU exactly once (first row). Every row creates one HA
	 * {@code M_HU_Item} under the LU, one child VTU {@code M_HU}, and one {@code M_HU_Assignment}
	 * row linking the VTU to the first M_InOutLine of the row's shipment. The
	 * {@code m_hu_assignment.vhu_id} column is set to the VTU's {@code M_HU_ID}, which is the
	 * critical invariant the {@code ha_items_with_vtu} CTE in
	 * {@code get_epcis_events_json_fn} matches on.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) shipment that "owns" this HA aggregate<br>
	 *   <b>crateCount</b> — (required) number of TUs aggregated under this HA (sets {@code ha_item.qty})
	 * @cucumber.example
	 * <pre>
	 * And one shared LU created via BL with SSCC18 '987654321000001400' carries HA aggregates assigned to inout lines:
	 *   | M_InOut_ID     | crateCount |
	 *   | ioA_S29231_140 | 5          |
	 *   | ioB_S29231_140 | 10         |
	 * </pre>
	 */
	@And("^one shared LU created via BL with SSCC18 '(.*)' carries HA aggregates assigned to inout lines:$")
	public void createSharedLuHaViaBL(@NonNull final String sscc18, @NonNull final DataTable dataTable)
	{
		// ── Lookup: SSCC18 M_Attribute_ID (read-only query) ──────────────────────────
		final int sscc18AttributeId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"SELECT m_attribute_id FROM m_attribute WHERE value='SSCC18' LIMIT 1");
		assertThat(sscc18AttributeId).as("M_Attribute_ID for SSCC18").isGreaterThan(0);

		// ── Lookup: LU PI version + TU PI version (read-only query) ──────────────────
		final int[] piVersions = new int[2]; // [0]=luPIVersionId, [1]=tuPIVersionId
		try (final java.sql.PreparedStatement pstmt = DB.prepareStatement(
				"SELECT piv_lu.m_hu_pi_version_id, piv_tu.m_hu_pi_version_id"
						+ " FROM m_hu_pi_version piv_lu"
						+ " JOIN m_hu_pi_item pii ON pii.m_hu_pi_version_id = piv_lu.m_hu_pi_version_id"
						+ "   AND pii.itemtype = 'HU' AND pii.isactive = 'Y'"
						+ " JOIN m_hu_pi pih ON pih.m_hu_pi_id = pii.included_hu_pi_id"
						+ " JOIN m_hu_pi_version piv_tu ON piv_tu.m_hu_pi_id = pih.m_hu_pi_id"
						+ "   AND piv_tu.iscurrent = 'Y' AND piv_tu.hu_unittype = 'TU'"
						+ " WHERE piv_lu.iscurrent = 'Y' AND piv_lu.hu_unittype = 'LU' AND piv_lu.isactive = 'Y'"
						+ " LIMIT 1",
				Trx.TRXNAME_None))
		{
			try (final ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
				{
					throw new AdempiereException("No current LU M_HU_PI_Version with a TU child PI item found");
				}
				piVersions[0] = rs.getInt(1);
				piVersions[1] = rs.getInt(2);
			}
		}
		catch (final SQLException e)
		{
			throw new AdempiereException("Failed to look up LU/TU PI versions for BL-based shared-LU step", e);
		}
		final int luPIVersionId = piVersions[0];
		final int tuPIVersionId = piVersions[1];
		assertThat(luPIVersionId).as("A current LU M_HU_PI_Version must exist").isGreaterThan(0);
		assertThat(tuPIVersionId).as("A current TU M_HU_PI_Version (child of LU) must exist").isGreaterThan(0);

		final int adOrgId = Env.getAD_Org_ID(Env.getCtx());
		if (adOrgId <= 0)
		{
			throw new AdempiereException("AD_Org_ID from context is 0; context may not be initialised");
		}

		// ── Create the shared LU via BL (InterfaceWrapperHelper) ─────────────────────
		final I_M_HU luHu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		luHu.setAD_Org_ID(adOrgId);
		luHu.setM_HU_PI_Version_ID(luPIVersionId);
		luHu.setHUStatus("E"); // Shipped — matches production LAF1010-3 state
		luHu.setIsActive(true);
		luHu.setValue("EPCIS_BL_LU_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12));
		InterfaceWrapperHelper.save(luHu);
		final int luHuId = luHu.getM_HU_ID();
		assertThat(luHuId).as("Newly created shared LU M_HU_ID (via BL)").isGreaterThan(0);
		injectedLuHuIds.add(luHuId);

		// ── Set SSCC18 attribute on the shared LU via BL ──────────────────────────────
		final I_M_HU_Attribute sscc18Attr = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class);
		sscc18Attr.setAD_Org_ID(adOrgId);
		sscc18Attr.setM_HU_ID(luHuId);
		sscc18Attr.setM_Attribute_ID(sscc18AttributeId);
		sscc18Attr.setValue(sscc18);
		sscc18Attr.setIsActive(true);
		InterfaceWrapperHelper.save(sscc18Attr);

		// ── Per-row: create HA item + VTU + assignment via BL ────────────────────────
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);

		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_InOut inout = inoutTable.get(row.getAsIdentifier("M_InOut_ID"));
			final int crateCount = row.getAsInt("crateCount");
			assertThat(crateCount).as("crateCount must be > 0").isGreaterThan(0);

			// Pick the first M_InOutLine of this shipment (read-only query)
			final int inoutLineId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"SELECT m_inoutline_id FROM m_inoutline"
							+ " WHERE m_inout_id=" + inout.getM_InOut_ID()
							+ " ORDER BY m_inoutline_id LIMIT 1");
			assertThat(inoutLineId)
					.as("M_InOutLine for M_InOut_ID=%d", inout.getM_InOut_ID())
					.isGreaterThan(0);

			// Create HA M_HU_Item under the shared LU via BL.
			// HA items are aggregate items and do NOT reference an M_HU_PI_Item (PI_Item_ID=0),
			// matching how HUAndItemsDAO.createAggregateHUItem() creates them in production.
			// M_HU_PI_Item.ItemType only accepts MI/PM/HU — "HA" is only valid on M_HU_Item.ItemType.
			final I_M_HU_Item haItem = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
			haItem.setAD_Org_ID(adOrgId);
			haItem.setM_HU_ID(luHuId);
			// M_HU_PI_Item_ID intentionally left at 0 (HA items have no backing PI item)
			haItem.setItemType(X_M_HU_Item.ITEMTYPE_HUAggregate);
			haItem.setQty(new BigDecimal(crateCount));
			haItem.setIsActive(true);
			InterfaceWrapperHelper.save(haItem);
			final int haItemId = haItem.getM_HU_Item_ID();
			assertThat(haItemId).as("Newly created HA M_HU_Item_ID (via BL)").isGreaterThan(0);

			// Create VTU M_HU as child of the HA item via BL
			// (VTU's m_hu_item_parent_id points to the HA M_HU_Item — standard aggregate-HU structure)
			final I_M_HU vtu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
			vtu.setAD_Org_ID(adOrgId);
			vtu.setM_HU_PI_Version_ID(tuPIVersionId);
			vtu.setM_HU_Item_Parent_ID(haItemId);
			vtu.setHUStatus("E"); // Shipped — mirrors production LAF1010-3
			vtu.setIsActive(true);
			vtu.setValue("EPCIS_BL_VTU_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12));
			InterfaceWrapperHelper.save(vtu);
			final int vtuHuId = vtu.getM_HU_ID();
			assertThat(vtuHuId).as("Newly created VTU M_HU_ID (via BL)").isGreaterThan(0);
			injectedTuHuIds.add(vtuHuId);

			// Create M_HU_Assignment via IHUAssignmentBL (the real BL path mobile picking uses)
			// setVHU(vtu) sets m_hu_assignment.vhu_id = vtu.M_HU_ID — the critical column that
			// the ha_items_with_vtu CTE in get_epcis_events_json_fn matches on (see RESEARCH Q3).
			// qty=0 mirrors production data (presence-only assignment; real crate count comes from ha_item.qty).
			final org.compiere.model.I_M_InOutLine inoutLine = InterfaceWrapperHelper.load(inoutLineId, org.compiere.model.I_M_InOutLine.class);
			final IHUAssignmentBuilder builder = huAssignmentBL.createHUAssignmentBuilder();
			builder.initializeAssignment(Env.getCtx(), Trx.TRXNAME_None);
			builder.setIsActive(true);
			builder.setModel(inoutLine);
			builder.setTopLevelHU(luHu);
			builder.setM_LU_HU(luHu);
			builder.setM_TU_HU(vtu);
			builder.setVHU(vtu);
			builder.setQty(BigDecimal.ZERO);
			builder.build();
		});
	}

	/**
	 * Creates ONE shared LU with the given SSCC18 and attaches one HA aggregate per data-table row.
	 * Each HA aggregate is assigned to one M_InOut via {@code m_hu_assignment.vhu_id} — simulating
	 * the real-world case where a picker consolidates two orders' goods onto one physical pallet
	 * but each shipment only "owns" some of the crates.
	 *
	 * <p>This is the HA-aggregate counterpart of {@link #assignLuHUsWithSscc18ToInoutLines}. It
	 * exercises the {@code ha_items_with_vtu} CTE and the per-shipment EXISTS gate added in me03#29231.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) shipment that "owns" this HA aggregate<br>
	 *   <b>crateCount</b> — (required) number of TUs aggregated under this HA (sets {@code ha_item.qty})
	 * @cucumber.example
	 * <pre>
	 * And one shared LU with SSCC18 '987654321000000040' carries HA aggregates assigned to inout lines:
	 *   | M_InOut_ID | crateCount |
	 *   | shipment_A | 5          |
	 *   | shipment_B | 10         |
	 * </pre>
	 */
	@And("^one shared LU with SSCC18 '(.*)' carries HA aggregates assigned to inout lines:$")
	public void assignSharedLuHaToInoutLines(@NonNull final String sscc18, @NonNull final DataTable dataTable)
	{
		final int inoutLineTableId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"SELECT ad_table_id FROM ad_table WHERE tablename='M_InOutLine'");
		assertThat(inoutLineTableId).as("AD_Table_ID for M_InOutLine").isGreaterThan(0);

		final int sscc18AttributeId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"SELECT m_attribute_id FROM m_attribute WHERE value='SSCC18' LIMIT 1");
		assertThat(sscc18AttributeId).as("M_Attribute_ID for SSCC18").isGreaterThan(0);

		// Look up a current LU PI version + its TU PI version (used for the VTU's PI version)
		final int[] piVersions = new int[2]; // [0]=luPIVersionId, [1]=tuPIVersionId
		try (final java.sql.PreparedStatement pstmt = DB.prepareStatement(
				"SELECT piv_lu.m_hu_pi_version_id, piv_tu.m_hu_pi_version_id"
						+ " FROM m_hu_pi_version piv_lu"
						+ " JOIN m_hu_pi_item pii ON pii.m_hu_pi_version_id = piv_lu.m_hu_pi_version_id"
						+ "   AND pii.itemtype = 'HU' AND pii.isactive = 'Y'"
						+ " JOIN m_hu_pi pih ON pih.m_hu_pi_id = pii.included_hu_pi_id"
						+ " JOIN m_hu_pi_version piv_tu ON piv_tu.m_hu_pi_id = pih.m_hu_pi_id"
						+ "   AND piv_tu.iscurrent = 'Y' AND piv_tu.hu_unittype = 'TU'"
						+ " WHERE piv_lu.iscurrent = 'Y' AND piv_lu.hu_unittype = 'LU' AND piv_lu.isactive = 'Y'"
						+ " LIMIT 1",
				Trx.TRXNAME_None))
		{
			try (final ResultSet rs = pstmt.executeQuery())
			{
				if (!rs.next())
				{
					throw new AdempiereException("No current LU M_HU_PI_Version with a TU child PI item found");
				}
				piVersions[0] = rs.getInt(1);
				piVersions[1] = rs.getInt(2);
			}
		}
		catch (final SQLException e)
		{
			throw new AdempiereException("Failed to look up LU/TU PI versions", e);
		}
		final int luPIVersionId = piVersions[0];
		final int tuPIVersionId = piVersions[1];

		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		final int adOrgId = Env.getAD_Org_ID(Env.getCtx());
		if (adOrgId <= 0)
		{
			throw new AdempiereException("AD_Org_ID from context is 0; context may not be initialised");
		}

		// Find or create an HA-itemtype M_HU_PI_Item on the LU PI version. Required by m_hu_item.m_hu_pi_item_id.
		int haPiItemId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"SELECT m_hu_pi_item_id FROM m_hu_pi_item"
						+ " WHERE m_hu_pi_version_id=" + luPIVersionId
						+ "   AND itemtype='HA' AND isactive='Y' LIMIT 1");
		if (haPiItemId <= 0)
		{
			haPiItemId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"INSERT INTO m_hu_pi_item (m_hu_pi_item_id, ad_client_id, ad_org_id, m_hu_pi_version_id, itemtype, qty, isactive,"
							+ " created, createdby, updated, updatedby)"
							+ " VALUES (nextval('m_hu_pi_item_seq')," + adClientId + "," + adOrgId + "," + luPIVersionId + ",'HA', 0,'Y',"
							+ " now(), 100, now(), 100) RETURNING m_hu_pi_item_id");
			assertThat(haPiItemId).as("Newly created HA M_HU_PI_Item_ID").isGreaterThan(0);
			injectedHaPiItemIds.add(haPiItemId);
		}
		final int haPiItemIdFinal = haPiItemId;

		// Create the shared LU (one for all rows)
		final int luHuId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"INSERT INTO m_hu (m_hu_id, ad_client_id, ad_org_id, m_hu_pi_version_id, hustatus, isactive,"
						+ " value, created, createdby, updated, updatedby)"
						+ " VALUES (nextval('m_hu_seq')," + adClientId + "," + adOrgId + "," + luPIVersionId + ",'E','Y',"
						+ " 'EPCIS_TEST_LU_HA_' || nextval('m_hu_seq'), now(), 100, now(), 100)"
						+ " RETURNING m_hu_id");
		assertThat(luHuId).as("Newly created shared LU M_HU_ID").isGreaterThan(0);
		injectedLuHuIds.add(luHuId);

		// SSCC18 attribute on the shared LU
		final int sscc18HuAttrId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"SELECT nextval('m_hu_attribute_seq')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO m_hu_attribute"
						+ " (m_hu_attribute_id, ad_client_id, ad_org_id, m_hu_id, m_attribute_id, value, isactive,"
						+ " created, createdby, updated, updatedby)"
						+ " VALUES (" + sscc18HuAttrId + "," + adClientId + "," + adOrgId + "," + luHuId + "," + sscc18AttributeId
						+ ",'" + sscc18 + "','Y', now(), 100, now(), 100)",
				Trx.TRXNAME_None);

		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_InOut inout = inoutTable.get(row.getAsIdentifier("M_InOut_ID"));
			final int crateCount = row.getAsInt("crateCount");
			assertThat(crateCount).as("crateCount must be > 0").isGreaterThan(0);

			// Pick one inoutline of this shipment to anchor the assignment on
			final int inoutLineId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"SELECT m_inoutline_id FROM m_inoutline"
							+ " WHERE m_inout_id=" + inout.getM_InOut_ID()
							+ " ORDER BY m_inoutline_id LIMIT 1");
			assertThat(inoutLineId)
					.as("M_InOutLine for M_InOut_ID=%d", inout.getM_InOut_ID())
					.isGreaterThan(0);

			// HA m_hu_item under the shared LU (itemtype='HA', qty = crate count for this shipment)
			final int haItemId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"INSERT INTO m_hu_item"
							+ " (m_hu_item_id, ad_client_id, ad_org_id, m_hu_id, m_hu_pi_item_id, itemtype, qty, isactive,"
							+ " created, createdby, updated, updatedby)"
							+ " VALUES (nextval('m_hu_item_seq')," + adClientId + "," + adOrgId + "," + luHuId + "," + haPiItemIdFinal
							+ ",'HA', " + crateCount + ",'Y', now(), 100, now(), 100)"
							+ " RETURNING m_hu_item_id");
			assertThat(haItemId).as("Newly created HA M_HU_Item_ID under shared LU").isGreaterThan(0);

			// VTU m_hu as child of the HA m_hu_item
			final int vtuHuId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"INSERT INTO m_hu"
							+ " (m_hu_id, ad_client_id, ad_org_id, m_hu_pi_version_id, m_hu_item_parent_id, hustatus, isactive,"
							+ " value, created, createdby, updated, updatedby)"
							+ " VALUES (nextval('m_hu_seq')," + adClientId + "," + adOrgId + "," + tuPIVersionId + "," + haItemId
							+ ",'E','Y',"
							+ " 'EPCIS_TEST_VTU_' || nextval('m_hu_seq'), now(), 100, now(), 100)"
							+ " RETURNING m_hu_id");
			assertThat(vtuHuId).as("Newly created VTU M_HU_ID (child of HA item " + haItemId + ")").isGreaterThan(0);
			injectedTuHuIds.add(vtuHuId);

			// m_hu_assignment: link the VTU to one inoutline of this shipment.
			// vhu_id = vtuHuId is the column the EPCIS function's EXISTS gate matches on for HA.
			// qty=0 mirrors production data observed in LAF1010-3 (assignment is presence-only;
			// the real crate count comes from ha_item.qty).
			final int assignId = DB.getSQLValueEx(Trx.TRXNAME_None,
					"SELECT nextval('m_hu_assignment_seq')");
			DB.executeUpdateAndThrowExceptionOnFail(
					"INSERT INTO m_hu_assignment"
							+ " (m_hu_assignment_id, ad_client_id, ad_org_id, ad_table_id, record_id, m_hu_id, m_lu_hu_id, m_tu_hu_id, vhu_id, qty, isactive,"
							+ " created, createdby, updated, updatedby)"
							+ " VALUES (" + assignId + "," + adClientId + "," + adOrgId + "," + inoutLineTableId + ","
							+ inoutLineId + "," + luHuId + "," + luHuId + "," + vtuHuId + "," + vtuHuId + ", 0,'Y', now(), 100, now(), 100)",
					Trx.TRXNAME_None);
		});
	}

	/**
	 * Deletes all M_HU / M_HU_Attribute / M_HU_Assignment / M_HU_Item rows that were injected
	 * by {@link #assignLuHUsWithSscc18ToInoutLines} in the current scenario.
	 * <p>
	 * Without this cleanup the test LUs (with {@code hustatus='E'}) survive across scenarios within
	 * the same JVM session and cause "Illegal M_HU.HUStatus change from E to D" failures in
	 * subsequent scenarios that run broad HU lifecycle operations.
	 */
	@After
	public void cleanupInjectedHUs()
	{
		if (injectedLuHuIds.isEmpty() && injectedHaPiItemIds.isEmpty())
		{
			return;
		}

		final List<Integer> allHuIds = new ArrayList<>(injectedLuHuIds);
		allHuIds.addAll(injectedTuHuIds);

		// Build comma-separated ID list for SQL IN clauses
		final String luIdList = injectedLuHuIds.stream().map(String::valueOf).reduce((a, b) -> a + "," + b).orElse("0");
		final String tuIdList = injectedTuHuIds.stream().map(String::valueOf).reduce((a, b) -> a + "," + b).orElse("0");
		final String allIdList = allHuIds.stream().map(String::valueOf).reduce((a, b) -> a + "," + b).orElse("0");

		// Delete in FK-dependency order. Each TU m_hu carries m_hu_item_parent_id → the LU's m_hu_item,
		// so the TU m_hu rows must go before the LU's m_hu_item rows.
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM m_hu_assignment WHERE m_lu_hu_id IN (" + luIdList + ")",
				Trx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM m_hu WHERE m_hu_id IN (" + tuIdList + ")",
				Trx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM m_hu_item WHERE m_hu_id IN (" + luIdList + ")",
				Trx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM m_hu_attribute WHERE m_hu_id IN (" + allIdList + ")",
				Trx.TRXNAME_None);
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM m_hu WHERE m_hu_id IN (" + luIdList + ")",
				Trx.TRXNAME_None);

		// HA PI items created on the fly by the shared-LU HA helper
		if (!injectedHaPiItemIds.isEmpty())
		{
			final String haPiList = injectedHaPiItemIds.stream().map(String::valueOf).reduce((a, b) -> a + "," + b).orElse("0");
			DB.executeUpdateAndThrowExceptionOnFail(
					"DELETE FROM m_hu_pi_item WHERE m_hu_pi_item_id IN (" + haPiList + ")",
					Trx.TRXNAME_None);
		}

		injectedLuHuIds.clear();
		injectedTuHuIds.clear();
		injectedHaPiItemIds.clear();
	}

	private void assertEpcisArrayField(@NonNull final DataTableRow row)
	{
		final String field = row.getAsString("field");
		final int expectedSize = row.getAsInt("expectedSize");

		final JsonNode arrayNode = lastEpcisResult.path(field);
		assertThat(arrayNode.isArray())
				.as("EPCIS JSON field '%s' must be a JSON array", field).isTrue();
		assertThat(arrayNode.size())
				.as("EPCIS JSON array '%s' must have %d element(s)", field, expectedSize)
				.isEqualTo(expectedSize);

		row.getAsOptionalString("containsValue").ifPresent(expected -> {
			final List<String> actualValues = new ArrayList<>();
			arrayNode.forEach(el -> actualValues.add(el.asText()));
			assertThat(actualValues)
					.as("EPCIS JSON array '%s' must contain '%s'", field, expected)
					.contains(expected);
		});
	}

	private void assertJsonField(@NonNull final String fieldName, @NonNull final String expected)
	{
		final JsonNode node = lastEpcisResult.path(fieldName);
		assertThat(node.isMissingNode()).as(fieldName + " should exist").isFalse();
		assertThat(node.asText()).as(fieldName).isEqualTo(expected);
	}

	private void assertJsonFieldNullable(@NonNull final String fieldName, @NonNull final String expected)
	{
		final JsonNode node = lastEpcisResult.path(fieldName);
		if ("null".equals(expected))
		{
			assertThat(node.isNull() || node.isMissingNode())
					.as(fieldName + " should be null").isTrue();
		}
		else if ("notNull".equals(expected))
		{
			assertThat(node.isNull() || node.isMissingNode())
					.as(fieldName + " should not be null").isFalse();
		}
		else
		{
			assertThat(node.asText()).as(fieldName).isEqualTo(expected);
		}
	}
}
