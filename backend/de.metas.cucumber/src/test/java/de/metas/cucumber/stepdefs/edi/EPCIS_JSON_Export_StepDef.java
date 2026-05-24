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
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOut;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	private final ObjectMapper objectMapper = new ObjectMapper();

	private JsonNode lastEpcisResult;

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

		// Get a suitable LU M_HU_PI_Version (first available LU version)
		final int luPIVersionId = DB.getSQLValueEx(Trx.TRXNAME_None,
				"SELECT m_hu_pi_version_id FROM m_hu_pi_version WHERE hu_unittype='LU' AND iscurrent='Y' LIMIT 1");
		assertThat(luPIVersionId).as("A current LU M_HU_PI_Version must exist").isGreaterThan(0);

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

			// INSERT M_HU_Assignment rows linking this LU to every M_InOutLine of the shipment
			for (final int inoutLineId : inoutLineIds)
			{
				final int assignId = DB.getSQLValueEx(Trx.TRXNAME_None,
						"SELECT nextval('m_hu_assignment_seq')");
				DB.executeUpdateAndThrowExceptionOnFail(
						"INSERT INTO m_hu_assignment"
								+ " (m_hu_assignment_id, ad_client_id, ad_org_id, ad_table_id, record_id, m_hu_id, m_lu_hu_id, isactive,"
								+ " created, createdby, updated, updatedby)"
								+ " VALUES (" + assignId + "," + adClientId + "," + adOrgId + "," + inoutLineTableId + ","
								+ inoutLineId + "," + luHuId + "," + luHuId + ",'Y', now(), 100, now(), 100)",
						Trx.TRXNAME_None);
			}
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
