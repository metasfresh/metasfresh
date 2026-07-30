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

package de.metas.cucumber.stepdefs.promotioncode;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_PromotionCode;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Cucumber step definitions for validating the promotion code evaluation report function
 * ({@code report.report_promotion_code_evaluation}).
 * <p>
 * The function returns completed/closed sales orders that have at least one promotion code,
 * along with their invoice status and details. It is used by ExportToSpreadsheetProcess
 * to generate Excel exports.
 * <p>
 * These steps call the SQL function directly via JDBC and validate the returned rows.
 *
 * @see <a href="https://github.com/metasfresh/metasfresh/issues/28565">gh#28565</a>
 */
@RequiredArgsConstructor
public class PromotionCodeReport_StepDef
{
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_PromotionCode_StepDefData promotionCodeTable;

	/**
	 * Validates that the promotion code evaluation report (unfiltered) contains expected rows.
	 * Calls {@code report.report_promotion_code_evaluation(NULL, NULL, NULL)} and checks
	 * that each expected order appears in the result.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Order_ID</b> — (required, identifier-ref) order that must appear in the report<br>
	 *   <b>C_PromotionCode_ID</b> — (optional, identifier-ref) expected promotion code 1 value<br>
	 *   <b>C_PromotionCode2_ID</b> — (optional, identifier-ref) expected promotion code 2 value<br>
	 *   <b>IsInvoiced</b> — (optional) expected invoiced flag: Y or N<br>
	 *   <b>HasInvoiceDocumentNo</b> — (optional) true if InvoiceDocumentNo should be non-null, false otherwise<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData, C_PromotionCode_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the promotion code report contains:
	 *   | C_Order_ID | C_PromotionCode_ID | IsInvoiced |
	 *   | order_1    | promoCode_1        | Y          |
	 * </pre>
	 */
	@Then("the promotion code report contains:")
	public void the_promotion_code_report_contains(@NonNull final DataTable dataTable)
	{
		final List<Map<String, Object>> reportRows = executeReport(null, null, null);
		DataTableRows.of(dataTable).forEach(row -> assertReportContainsOrder(reportRows, row));
	}

	/**
	 * Validates that the promotion code evaluation report filtered by {@code Invoiced='Y'}
	 * contains the expected rows. Only invoiced orders should appear.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Order_ID</b> — (required, identifier-ref) order that must appear<br>
	 *   <b>IsInvoiced</b> — (optional) expected invoiced flag (should always be Y here)<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the promotion code report with filter Invoiced Y contains:
	 *   | C_Order_ID | IsInvoiced |
	 *   | order_inv  | Y          |
	 * </pre>
	 */
	@Then("the promotion code report with filter Invoiced Y contains:")
	public void the_promotion_code_report_invoiced_Y_contains(@NonNull final DataTable dataTable)
	{
		final List<Map<String, Object>> reportRows = executeReport(null, null, "Y");
		DataTableRows.of(dataTable).forEach(row -> assertReportContainsOrder(reportRows, row));
	}

	/**
	 * Validates that the promotion code evaluation report filtered by {@code Invoiced='N'}
	 * contains the expected rows. Only non-invoiced orders should appear.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Order_ID</b> — (required, identifier-ref) order that must appear<br>
	 *   <b>IsInvoiced</b> — (optional) expected invoiced flag (should always be N here)<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the promotion code report with filter Invoiced N contains:
	 *   | C_Order_ID  | IsInvoiced |
	 *   | order_noinv | N          |
	 * </pre>
	 */
	@Then("the promotion code report with filter Invoiced N contains:")
	public void the_promotion_code_report_invoiced_N_contains(@NonNull final DataTable dataTable)
	{
		final List<Map<String, Object>> reportRows = executeReport(null, null, "N");
		DataTableRows.of(dataTable).forEach(row -> assertReportContainsOrder(reportRows, row));
	}

	/**
	 * Validates that the promotion code evaluation report filtered by {@code Invoiced='Y'}
	 * does NOT contain the given orders.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Order_ID</b> — (required, identifier-ref) order that must NOT appear in the report<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the promotion code report with filter Invoiced Y does not contain:
	 *   | C_Order_ID  |
	 *   | order_noinv |
	 * </pre>
	 */
	@And("the promotion code report with filter Invoiced Y does not contain:")
	public void the_promotion_code_report_invoiced_Y_does_not_contain(@NonNull final DataTable dataTable)
	{
		final List<Map<String, Object>> reportRows = executeReport(null, null, "Y");
		DataTableRows.of(dataTable).forEach(row -> assertReportDoesNotContainOrder(reportRows, row));
	}

	/**
	 * Validates that the promotion code evaluation report filtered by {@code Invoiced='N'}
	 * does NOT contain the given orders.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Order_ID</b> — (required, identifier-ref) order that must NOT appear in the report<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the promotion code report with filter Invoiced N does not contain:
	 *   | C_Order_ID |
	 *   | order_inv  |
	 * </pre>
	 */
	@And("the promotion code report with filter Invoiced N does not contain:")
	public void the_promotion_code_report_invoiced_N_does_not_contain(@NonNull final DataTable dataTable)
	{
		final List<Map<String, Object>> reportRows = executeReport(null, null, "N");
		DataTableRows.of(dataTable).forEach(row -> assertReportDoesNotContainOrder(reportRows, row));
	}

	/**
	 * Executes the {@code report.report_promotion_code_evaluation} SQL function
	 * with the given parameters and returns all result rows as maps.
	 *
	 * @param promotionCodeId  filter by C_PromotionCode_ID (null = all)
	 * @param promotionCode2Id filter by C_PromotionCode2_ID (null = all)
	 * @param invoiced         filter by invoiced status: "Y", "N", or null (all)
	 * @return list of row maps keyed by report column names
	 */
	private List<Map<String, Object>> executeReport(
			@Nullable final Integer promotionCodeId,
			@Nullable final Integer promotionCode2Id,
			@Nullable final String invoiced)
	{
		final String sql = "SELECT * FROM report.report_promotion_code_evaluation(?, ?, ?)";

		final List<Map<String, Object>> rows = new ArrayList<>();
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, null))
		{
			if (promotionCodeId != null)
			{
				pstmt.setInt(1, promotionCodeId);
			}
			else
			{
				pstmt.setNull(1, Types.NUMERIC);
			}

			if (promotionCode2Id != null)
			{
				pstmt.setInt(2, promotionCode2Id);
			}
			else
			{
				pstmt.setNull(2, Types.NUMERIC);
			}

			if (invoiced != null)
			{
				pstmt.setString(3, invoiced);
			}
			else
			{
				pstmt.setNull(3, Types.CHAR);
			}

			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					final Map<String, Object> rowMap = new HashMap<>();
					rowMap.put("C_PromotionCode_ID", rs.getString("C_PromotionCode_ID"));
					rowMap.put("ValidTo", rs.getDate("ValidTo"));
					rowMap.put("C_PromotionCode2_ID", rs.getString("C_PromotionCode2_ID"));
					rowMap.put("ValidTo2", rs.getDate("ValidTo2"));
					rowMap.put("OrderDocumentNo", rs.getString("OrderDocumentNo"));
					rowMap.put("DateOrdered", rs.getDate("DateOrdered"));
					rowMap.put("CustomerNo", rs.getString("CustomerNo"));
					rowMap.put("Name", rs.getString("Name"));
					rowMap.put("GrandTotal", rs.getBigDecimal("GrandTotal"));
					rowMap.put("IsInvoiced", rs.getString("IsInvoiced"));
					rowMap.put("InvoiceDocumentNo", rs.getString("InvoiceDocumentNo"));
					rowMap.put("DateInvoiced", rs.getDate("DateInvoiced"));
					rowMap.put("InvoiceGrandTotal", rs.getBigDecimal("InvoiceGrandTotal"));
					rows.add(rowMap);
				}
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e);
		}

		return rows;
	}

	/**
	 * Asserts that the report result contains a row matching the expected order,
	 * identified by its DocumentNo. Optionally validates promotion codes, invoiced flag,
	 * and presence of invoice document number.
	 */
	private void assertReportContainsOrder(
			@NonNull final List<Map<String, Object>> reportRows,
			@NonNull final DataTableRow expectedRow)
	{
		final I_C_Order order = expectedRow.getAsIdentifier("C_Order_ID").lookupNotNullIn(orderTable);
		final String orderDocumentNo = order.getDocumentNo();

		final Map<String, Object> matchingRow = reportRows.stream()
				.filter(r -> orderDocumentNo.equals(r.get("OrderDocumentNo")))
				.findFirst()
				.orElse(null);

		assertThat(matchingRow)
				.as("Report should contain order %s (DocumentNo=%s). Report has %d rows.",
						expectedRow.getAsIdentifier("C_Order_ID"), orderDocumentNo, reportRows.size())
				.isNotNull();

		// Validate promotion code 1
		expectedRow.getAsOptionalIdentifier("C_PromotionCode_ID").ifPresent(pcIdentifier -> {
			final I_C_PromotionCode pc = pcIdentifier.lookupNotNullIn(promotionCodeTable);
			assertThat(matchingRow.get("C_PromotionCode_ID"))
					.as("C_PromotionCode_ID value for order %s", orderDocumentNo)
					.isEqualTo(pc.getValue());
		});

		// Validate promotion code 2
		expectedRow.getAsOptionalIdentifier("C_PromotionCode2_ID").ifPresent(pcIdentifier -> {
			final I_C_PromotionCode pc = pcIdentifier.lookupNotNullIn(promotionCodeTable);
			assertThat(matchingRow.get("C_PromotionCode2_ID"))
					.as("C_PromotionCode2_ID value for order %s", orderDocumentNo)
					.isEqualTo(pc.getValue());
		});

		// Validate invoiced flag
		expectedRow.getAsOptionalString("IsInvoiced").ifPresent(expected ->
				assertThat(matchingRow.get("IsInvoiced"))
						.as("IsInvoiced for order %s", orderDocumentNo)
						.isEqualTo(expected));

		// Validate HasInvoiceDocumentNo
		expectedRow.getAsOptionalBoolean("HasInvoiceDocumentNo").ifPresent(expected -> {
			final boolean hasDocNo = matchingRow.get("InvoiceDocumentNo") != null;
			assertThat(hasDocNo)
					.as("HasInvoiceDocumentNo for order %s", orderDocumentNo)
					.isEqualTo(expected);
		});
	}

	/**
	 * Asserts that the report result does NOT contain a row for the given order.
	 */
	private void assertReportDoesNotContainOrder(
			@NonNull final List<Map<String, Object>> reportRows,
			@NonNull final DataTableRow expectedRow)
	{
		final I_C_Order order = expectedRow.getAsIdentifier("C_Order_ID").lookupNotNullIn(orderTable);
		final String orderDocumentNo = order.getDocumentNo();

		final boolean found = reportRows.stream()
				.anyMatch(r -> orderDocumentNo.equals(r.get("OrderDocumentNo")));

		assertThat(found)
				.as("Report should NOT contain order %s (DocumentNo=%s)", expectedRow.getAsIdentifier("C_Order_ID"), orderDocumentNo)
				.isFalse();
	}
}
