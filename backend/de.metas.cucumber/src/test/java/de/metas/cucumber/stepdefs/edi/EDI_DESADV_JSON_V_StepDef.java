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

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for asserting the row-set returned by the
 * {@code M_InOut_Export_EDI_DESADV_JSON_V} SQL view for a given shipment.
 * <p>
 * The view (rewritten as part of me03#29231) joins {@code m_inout} to
 * {@code edi_desadv} via the {@code edi_desadv_m_inout} junction table, so
 * a multi-source-order shipment returns one row per source-order DESADV.
 * <p>
 * Note: direct SQL via {@link DB#prepareStatement} is intentional here because
 * there is no DAO for a SQL view. The precedent is {@code EPCIS_JSON_Export_StepDef}.
 */
@RequiredArgsConstructor
public class EDI_DESADV_JSON_V_StepDef
{
	/** SQL query: for a given M_InOut_ID return one row per DESADV the view emits. */
	private static final String SQL_QUERY_VIEW =
			"SELECT"
					+ "  (v.embedded_json->'metasfresh_DESADV'->>'EDI_Desadv_ID')::int  AS edi_desadv_id,"
					+ "  v.embedded_json->'metasfresh_DESADV'->>'POReference'          AS po_reference"
					+ " FROM M_InOut_Export_EDI_DESADV_JSON_V v"
					+ " WHERE v.m_inout_id = ?";

	private final @NonNull M_InOut_StepDefData inoutTable;
	private final @NonNull C_Order_StepDefData orderTable;

	/**
	 * Queries {@code M_InOut_Export_EDI_DESADV_JSON_V} for the given shipment and asserts
	 * that the view returns exactly the expected number of rows and distinct DESADV IDs,
	 * and that every source-order's {@code POReference} appears in those rows.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>ExpectedRowCount</b> — (required) total rows the view must return for the M_InOut<br>
	 *   <b>DistinctDesadvIds</b> — (required) number of distinct EDI_Desadv_IDs expected<br>
	 *   <b>OrderA_Identifier</b> — (required) step-def identifier of source order A; its {@code POReference} must appear in the view rows<br>
	 *   <b>OrderB_Identifier</b> — (required) step-def identifier of source order B; its {@code POReference} must appear in the view rows<br>
	 * @cucumber.example
	 * <pre>
	 * Then the M_InOut_Export_EDI_DESADV_JSON_V export view for M_InOut identified by io_S29231_100 has:
	 *   | ExpectedRowCount | DistinctDesadvIds | OrderA_Identifier | OrderB_Identifier |
	 *   | 2                | 2                 | oA_S29231         | oB_S29231         |
	 * </pre>
	 */
	@Then("^the M_InOut_Export_EDI_DESADV_JSON_V export view for M_InOut identified by (.*) has:$")
	public void assertExportViewRows(
			@NonNull final String inoutIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_M_InOut inout = inoutTable.get(inoutIdentifier);
		final int inoutId = inout.getM_InOut_ID();

		// --- query the view -------------------------------------------------
		final List<ViewRow> rows = queryViewRows(inoutId);

		// --- validate each DataTable row ------------------------------------
		DataTableRows.of(dataTable).forEach(row -> assertViewRows(rows, row));
	}

	/**
	 * Queries {@code M_InOut_Export_EDI_DESADV_JSON_V} for the given shipment and asserts
	 * that the view returns exactly 1 row whose {@code EDI_Desadv_ID} and {@code POReference}
	 * match the given source order. Used for the single-order regression (TC2).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Order_Identifier</b> — (required) step-def identifier of the single source order<br>
	 * @cucumber.example
	 * <pre>
	 * Then the M_InOut_Export_EDI_DESADV_JSON_V export view for M_InOut identified by io_S29231_110 has exactly 1 row matching:
	 *   | Order_Identifier |
	 *   | o_S29231_110     |
	 * </pre>
	 */
	@Then("^the M_InOut_Export_EDI_DESADV_JSON_V export view for M_InOut identified by (.*) has exactly 1 row matching:$")
	public void assertExportViewSingleRow(
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

			// Wrap to EDI extension to access EDI_Desadv_ID
			final de.metas.edi.model.I_C_Order ediOrder =
					InterfaceWrapperHelper.create(order, de.metas.edi.model.I_C_Order.class);

			assertThat(rows.get(0).ediDesadvId())
					.as("View row EDI_Desadv_ID must match the order's EDI_Desadv_ID")
					.isEqualTo(ediOrder.getEDI_Desadv_ID());

			assertThat(rows.get(0).poReference())
					.as("View row POReference must match the order's POReference")
					.isEqualTo(order.getPOReference());
		});
	}

	// =========================================================================
	// private helpers
	// =========================================================================

	private void assertViewRows(
			@NonNull final List<ViewRow> viewRows,
			@NonNull final DataTableRow row)
	{
		final int expectedRowCount  = row.getAsInt("ExpectedRowCount");
		final int distinctDesadvIds = row.getAsInt("DistinctDesadvIds");
		final String orderAIdentifier = row.getAsString("OrderA_Identifier");
		final String orderBIdentifier = row.getAsString("OrderB_Identifier");

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

		// Strict intersection (per PR #24042 review #4335557991): each emitted view row must
		// represent the intersection of (shipment ∩ source-order). I.e. the row carrying
		// orderA's EDI_Desadv_ID must carry orderA's POReference (and never orderB's), and
		// vice versa. Without this pairing assertion, two rows could swap POReferences and
		// the earlier checks would still pass.
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
							rs.getString("po_reference")));
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

		ViewRow(final int ediDesadvId, final String poReference)
		{
			this.ediDesadvId = ediDesadvId;
			this.poReference = poReference;
		}

		int ediDesadvId() { return ediDesadvId; }
		String poReference() { return poReference; }
	}
}
