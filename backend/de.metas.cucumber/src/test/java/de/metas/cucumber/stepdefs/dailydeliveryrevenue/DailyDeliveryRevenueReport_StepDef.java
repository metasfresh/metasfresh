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

package de.metas.cucumber.stepdefs.dailydeliveryrevenue;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Step definitions for the "Tagesumsatz (auf Basis Lieferung)" / "Daily Revenue (based on Delivery)" report.
 *
 * @see DailyDeliveryRevenueReportRow
 * @see DailyDeliveryRevenueReportRepository
 */
@RequiredArgsConstructor
public class DailyDeliveryRevenueReport_StepDef
{
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final DailyDeliveryRevenueReportRepository dailyDeliveryRevenueReportRepository;

	/**
	 * Asserts the output of {@code de_metas_endcustomer_fresh_reports.Docs_Daily_Delivery_Revenue_Report(...)} —
	 * the SQL function backing the "Tagesumsatz (auf Basis Lieferung)" Jasper report.
	 *
	 * <p>The optional {@code C_BPartner_ID} filters the function call by {@code p_bpartner_id}; the optional
	 * {@code C_BP_Group_ID} is not exercised by this step (no BP-group fixtures are needed by the scenarios
	 * that use it) and is passed as {@code NULL}.
	 *
	 * <p><b>Supported DataTable columns</b>:
	 * <ul>
	 *     <li>{@code Day} — (required) the expected day, ISO local date</li>
	 *     <li>{@code DeliveryRevenueNet} — (required) the expected day total</li>
	 * </ul>
	 *
	 * <p><b>Matching philosophy</b>: exact row count, matched by {@code Day} (one row per day is the
	 * report's own invariant — AC2).
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Then Docs_Daily_Delivery_Revenue_Report for C_BPartner "customer" between "2026-05-04" and "2026-05-04" returns:
	 *   | Day        | DeliveryRevenueNet |
	 *   | 2026-05-04 | 70.00               |
	 * }</pre>
	 */
	@Then("Docs_Daily_Delivery_Revenue_Report for C_BPartner {string} between {string} and {string} returns:")
	public void expect_daily_delivery_revenue_report(
			@NonNull final String bpartnerIdentifier,
			@NonNull final String dateFromStr,
			@NonNull final String dateToStr,
			@NonNull final DataTable dataTable)
	{
		final I_C_BPartner bpartner = bpartnerTable.get(bpartnerIdentifier);
		final LocalDate dateFrom = LocalDate.parse(dateFromStr);
		final LocalDate dateTo = LocalDate.parse(dateToStr);

		final ImmutableList<DailyDeliveryRevenueReportRow> actualRows = dailyDeliveryRevenueReportRepository.list(
				bpartner.getC_BPartner_ID(),
				null,
				dateFrom,
				dateTo);

		assertDailyDeliveryRevenueReportRows(dataTable, actualRows);
	}

	/**
	 * Same as {@link #expect_daily_delivery_revenue_report(String, String, String, DataTable)}, but without a
	 * {@code C_BPartner_ID} filter (both {@code p_bpartner_id} and {@code p_bp_group_id} are {@code NULL}) —
	 * used to assert the unfiltered, all-partners total of a day.
	 *
	 * @cucumber.example
	 * <pre>{@code
	 * Then Docs_Daily_Delivery_Revenue_Report between "2026-05-04" and "2026-05-04" returns:
	 *   | Day        | DeliveryRevenueNet |
	 *   | 2026-05-04 | 70.00               |
	 * }</pre>
	 */
	@Then("Docs_Daily_Delivery_Revenue_Report between {string} and {string} returns:")
	public void expect_daily_delivery_revenue_report_unfiltered(
			@NonNull final String dateFromStr,
			@NonNull final String dateToStr,
			@NonNull final DataTable dataTable)
	{
		final LocalDate dateFrom = LocalDate.parse(dateFromStr);
		final LocalDate dateTo = LocalDate.parse(dateToStr);

		final ImmutableList<DailyDeliveryRevenueReportRow> actualRows = dailyDeliveryRevenueReportRepository.list(
				null,
				null,
				dateFrom,
				dateTo);

		assertDailyDeliveryRevenueReportRows(dataTable, actualRows);
	}

	private void assertDailyDeliveryRevenueReportRows(
			@NonNull final DataTable dataTable,
			@NonNull final List<DailyDeliveryRevenueReportRow> actualRows)
	{
		final ImmutableList<DataTableRow> expectedRows = DataTableRows.of(dataTable).toList();

		final String actualRowsDump = renderActualRows(actualRows);
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(actualRows)
				.as("Docs_Daily_Delivery_Revenue_Report: expected %s row(s), got:\n%s", expectedRows.size(), actualRowsDump)
				.hasSize(expectedRows.size());

		for (final DataTableRow expected : expectedRows)
		{
			final LocalDate expectedDay = expected.getAsLocalDate("Day");
			final BigDecimal expectedRevenue = expected.getAsBigDecimal("DeliveryRevenueNet");

			final Optional<DailyDeliveryRevenueReportRow> actualRow = actualRows.stream()
					.filter(row -> expectedDay.equals(row.getDay()))
					.findFirst();

			softly.assertThat(actualRow)
					.as("Docs_Daily_Delivery_Revenue_Report: no row for day %s\n    all actual rows:\n%s", expectedDay, actualRowsDump)
					.isPresent();

			actualRow.ifPresent(row -> softly.assertThat(row.getDeliveryRevenueNet())
					.as("Docs_Daily_Delivery_Revenue_Report: DeliveryRevenueNet for day %s", expectedDay)
					.isEqualByComparingTo(expectedRevenue));
		}

		softly.assertAll();
	}

	private static String renderActualRows(@NonNull final List<DailyDeliveryRevenueReportRow> rows)
	{
		if (rows.isEmpty())
		{
			return "      (none)";
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rows.size(); i++)
		{
			sb.append("      [").append(i).append("] ").append(rows.get(i)).append('\n');
		}
		return sb.toString();
	}
}
