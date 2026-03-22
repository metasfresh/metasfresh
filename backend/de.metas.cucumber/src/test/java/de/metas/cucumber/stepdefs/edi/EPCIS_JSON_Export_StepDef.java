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
import org.compiere.util.Trx;

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
