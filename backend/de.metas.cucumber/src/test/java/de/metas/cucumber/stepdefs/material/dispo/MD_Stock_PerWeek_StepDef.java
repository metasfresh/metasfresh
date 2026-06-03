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

package de.metas.cucumber.stepdefs.material.dispo;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.ItemProvider.ProviderResult;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Stock_PerWeek_V;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * Step definitions for asserting the {@code MD_Stock_PerWeek_V} weekly aggregation view.
 *
 * <p>All date references are anchored to the DB's {@code date_trunc('week', current_date)} so
 * the tests remain stable regardless of the actual run date.</p>
 */
@RequiredArgsConstructor
public class MD_Stock_PerWeek_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;

	// ---------------------------------------------------------------------------
	// Helpers
	// ---------------------------------------------------------------------------

	/**
	 * Returns the ISO-week Monday for the current DB date (Monday = first day of ISO week).
	 * Using the DB as the anchor so seeding and assertion share the same reference.
	 */
	private LocalDate currentWeekMonday()
	{
		final Timestamp ts = DB.getSQLValueTSEx(ITrx.TRXNAME_None, "SELECT date_trunc('week', current_date)::date");
		return ts.toLocalDateTime().toLocalDate();
	}

	/** Computes the expected WeekStartDate for a given offset (0 = current week, 1 = +1 week, …). */
	private LocalDate weekStartDateForOffset(final LocalDate monday, final int weekOffset)
	{
		return monday.plusDays((long)weekOffset * 7);
	}

	/**
	 * Converts a {@link LocalDate} to a UTC-midnight {@link Timestamp} for use in DB filters.
	 *
	 * <p>Using UTC avoids a JVM-local-timezone mismatch when the CI executor runs in e.g. Europe/Berlin
	 * while the DB stores {@code date}-typed columns in terms of UTC epoch-days.</p>
	 */
	private static Timestamp toUtcMidnightTimestamp(final LocalDate date)
	{
		return Timestamp.from(date.atStartOfDay(ZoneOffset.UTC).toInstant());
	}

	// ---------------------------------------------------------------------------
	// Seeding step (relative to current DB week)
	// ---------------------------------------------------------------------------

	/**
	 * Seeds {@code MD_Candidate} rows with {@code DateProjected} anchored to the current DB week.
	 *
	 * <p>Use this step instead of the generic {@code metasfresh initially has this MD_Candidate data}
	 * whenever the assertion uses {@code WeekOffset}-based rows, so that seeding and assertion
	 * share the same DB {@code date_trunc('week', current_date)} reference and the test stays
	 * stable regardless of the actual run date.</p>
	 *
	 * @cucumber.columns
	 *   <b>Identifier</b> — unique identifier for this candidate<br>
	 *   <b>MD_Candidate_Type</b> — DEMAND, SUPPLY, or INVENTORY_UP<br>
	 *   <b>MD_Candidate_BusinessCase</b> — SHIPMENT, PURCHASE, or empty<br>
	 *   <b>M_Product_ID</b> — (identifier-ref) product<br>
	 *   <b>WeekOffset</b> — weeks relative to the current ISO week (0 = this week, -1 = last week, …)<br>
	 *   <b>DayWithinWeek</b> — day offset within the target week (1 = Monday+1, used to stay in the week)<br>
	 *   <b>Qty</b> — quantity<br>
	 *   <b>ATP</b> — projected stock<br>
	 *   <b>M_Warehouse_ID</b> — (identifier-ref) warehouse<br>
	 */
	@Given("^metasfresh initially has this MD_Candidate data relative to current week$")
	public void seed_md_candidates_relative_to_current_week(@NonNull final DataTable dataTable)
	{
		final LocalDate monday = currentWeekMonday();

		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Product productRecord = row.getAsIdentifier(I_MD_Stock_PerWeek_V.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
			final I_M_Warehouse warehouseRecord = row.getAsIdentifier(I_MD_Stock_PerWeek_V.COLUMNNAME_M_Warehouse_ID).lookupNotNullIn(warehouseTable);

			final int weekOffset = row.getAsInt("WeekOffset");
			final int dayWithinWeek = row.getAsInt("DayWithinWeek");
			final LocalDate dateProjectedDate = monday.plusDays((long)weekOffset * 7 + dayWithinWeek);
			// Timestamp at UTC noon — stays in the target calendar-date on any TZ-offset executor
			final Timestamp dateProjected = Timestamp.from(dateProjectedDate.atTime(12, 0).toInstant(ZoneOffset.UTC));

			final BigDecimal qty = row.getAsBigDecimal("Qty");
			final BigDecimal atp = row.getAsBigDecimal("ATP");
			final CandidateType type = CandidateType.ofCode(row.getAsString("MD_Candidate_Type"));
			final String businessCaseStr = row.getAsOptionalString("MD_Candidate_BusinessCase")
					.map(String::trim)
					.filter(s -> !s.isEmpty())
					.orElse(null);
			final String businessCaseCode = businessCaseStr != null ? CandidateBusinessCase.toCode(CandidateBusinessCase.ofCode(businessCaseStr)) : null;

			// Save the main candidate (DEMAND / SUPPLY / INVENTORY_UP)
			final I_MD_Candidate mainRecord = InterfaceWrapperHelper.newInstance(I_MD_Candidate.class);
			mainRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			mainRecord.setM_Product_ID(productRecord.getM_Product_ID());
			mainRecord.setM_Warehouse_ID(warehouseRecord.getM_Warehouse_ID());
			mainRecord.setMD_Candidate_Type(type.getCode());
			mainRecord.setMD_Candidate_BusinessCase(businessCaseCode);
			mainRecord.setQty(qty);
			mainRecord.setDateProjected(dateProjected);
			InterfaceWrapperHelper.saveRecord(mainRecord);
			mainRecord.setSeqNo(mainRecord.getMD_Candidate_ID());
			InterfaceWrapperHelper.saveRecord(mainRecord);

			// Save the paired STOCK candidate (mirrors what the dispo engine would create)
			final I_MD_Candidate stockRecord = InterfaceWrapperHelper.newInstance(I_MD_Candidate.class);
			stockRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			stockRecord.setM_Product_ID(productRecord.getM_Product_ID());
			stockRecord.setM_Warehouse_ID(warehouseRecord.getM_Warehouse_ID());
			stockRecord.setMD_Candidate_Type(CandidateType.STOCK.getCode());
			stockRecord.setSeqNo(mainRecord.getMD_Candidate_ID());
			stockRecord.setQty(atp);
			stockRecord.setDateProjected(dateProjected);

			final boolean isDemand = type.isDecreasingStock();
			final boolean isSupply = type.isIncreasingStock();

			if (isDemand)
			{
				stockRecord.setMD_Candidate_Parent_ID(mainRecord.getMD_Candidate_ID());
			}
			InterfaceWrapperHelper.saveRecord(stockRecord);

			if (isSupply)
			{
				mainRecord.setMD_Candidate_Parent_ID(stockRecord.getMD_Candidate_ID());
				InterfaceWrapperHelper.saveRecord(mainRecord);
			}

			// I3: assert the seeded candidates actually exist (prevents false-green from silent save failures)
			final int candidateCount = queryBL.createQueryBuilderOutOfTrx(I_MD_Candidate.class)
					.addEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_ID, mainRecord.getMD_Candidate_ID())
					.create()
					.count();
			org.assertj.core.api.Assertions.assertThat(candidateCount)
					.as("Seeded MD_Candidate for identifier=%s must exist in DB", row.getAsString("Identifier"))
					.isEqualTo(1);
		});
	}

	// ---------------------------------------------------------------------------
	// Assertion step
	// ---------------------------------------------------------------------------

	/**
	 * Polls {@code MD_Stock_PerWeek_V} until the expected rows are found or the timeout expires.
	 *
	 * <p>The {@code WeekOffset} column is relative to the current ISO week (0 = this week,
	 * 1 = next week, …). The stepdef resolves the actual {@code WeekStartDate} from the DB's
	 * {@code date_trunc('week', current_date)} anchor, so the test is date-stable.</p>
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) product<br>
	 *   <b>M_Warehouse_ID</b> — (required, identifier-ref) warehouse<br>
	 *   <b>WeekOffset</b> — (required) weeks relative to the current ISO week (0 = this week, 1 = next week, …)<br>
	 *   <b>QtyExpectedShipments</b> — (required) expected value in that week<br>
	 *   <b>QtyExpectedReceipts</b> — (required) expected value in that week<br>
	 *   <b>QtyATP</b> — (required) expected projected stock at week-end<br>
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 10s, MD_Stock_PerWeek_V contains:
	 *   | M_Product_ID | M_Warehouse_ID | WeekOffset | QtyExpectedShipments | QtyExpectedReceipts | QtyATP |
	 *   | product_1    | warehouse_1    | 1          | 5                    | 0                   | -5     |
	 * </pre>
	 */
	@Then("^after not more than (.*)s, MD_Stock_PerWeek_V contains:$")
	public void validate_stock_per_week(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final LocalDate monday = currentWeekMonday();

		DataTableRows.of(dataTable).forEach(row -> assertStockPerWeekRow(timeoutSec, row, monday));
	}

	private void assertStockPerWeekRow(final int timeoutSec, @NonNull final DataTableRow row, @NonNull final LocalDate monday) throws InterruptedException
	{
		final I_M_Product productRecord = row.getAsIdentifier(I_MD_Stock_PerWeek_V.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
		final I_M_Warehouse warehouseRecord = row.getAsIdentifier(I_MD_Stock_PerWeek_V.COLUMNNAME_M_Warehouse_ID).lookupNotNullIn(warehouseTable);

		final int productId = productRecord.getM_Product_ID();
		final int warehouseId = warehouseRecord.getM_Warehouse_ID();

		final int weekOffset = row.getAsInt("WeekOffset");
		final LocalDate weekStartDate = weekStartDateForOffset(monday, weekOffset);
		// I1: use UTC midnight to avoid JVM-local-tz mismatch on Berlin-tz CI executors
		final Timestamp weekStartTs = toUtcMidnightTimestamp(weekStartDate);

		final BigDecimal expectedShipments = row.getAsBigDecimal(I_MD_Stock_PerWeek_V.COLUMNNAME_QtyExpectedShipments);
		final BigDecimal expectedReceipts = row.getAsBigDecimal(I_MD_Stock_PerWeek_V.COLUMNNAME_QtyExpectedReceipts);
		final BigDecimal expectedAtp = row.getAsBigDecimal(I_MD_Stock_PerWeek_V.COLUMNNAME_QtyATP);

		// H1: use the builder form instead of the deprecated tryAndWaitForItem(int,long,ItemProvider) overload
		final I_MD_Stock_PerWeek_V record = StepDefUtil.<I_MD_Stock_PerWeek_V>tryAndWaitForItem()
				.maxWaitSeconds(timeoutSec)
				.checkingIntervalMs(500L)
				.worker(() -> {
					final I_MD_Stock_PerWeek_V r = queryBL.createQueryBuilderOutOfTrx(I_MD_Stock_PerWeek_V.class)
							.addEqualsFilter(I_MD_Stock_PerWeek_V.COLUMNNAME_M_Product_ID, productId)
							.addEqualsFilter(I_MD_Stock_PerWeek_V.COLUMNNAME_M_Warehouse_ID, warehouseId)
							.addEqualsFilter(I_MD_Stock_PerWeek_V.COLUMNNAME_WeekStartDate, weekStartTs)
							.create()
							.firstOnly(I_MD_Stock_PerWeek_V.class);

					if (r == null)
					{
						return ProviderResult.resultWasNotFound(
								"No MD_Stock_PerWeek_V row for product=" + productId
										+ " warehouse=" + warehouseId
										+ " weekOffset=" + weekOffset
										+ " (weekStart=" + weekStartDate + ")");
					}

					return ProviderResult.resultWasFound(r);
				})
				.execute();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(record.getQtyExpectedShipments())
				.as("QtyExpectedShipments for product=%d wh=%d weekOffset=%d (week=%s)", productId, warehouseId, weekOffset, weekStartDate)
				.isEqualByComparingTo(expectedShipments);
		softly.assertThat(record.getQtyExpectedReceipts())
				.as("QtyExpectedReceipts for product=%d wh=%d weekOffset=%d (week=%s)", productId, warehouseId, weekOffset, weekStartDate)
				.isEqualByComparingTo(expectedReceipts);
		softly.assertThat(record.getQtyATP())
				.as("QtyATP for product=%d wh=%d weekOffset=%d (week=%s)", productId, warehouseId, weekOffset, weekStartDate)
				.isEqualByComparingTo(expectedAtp);
		softly.assertAll();
	}
}
