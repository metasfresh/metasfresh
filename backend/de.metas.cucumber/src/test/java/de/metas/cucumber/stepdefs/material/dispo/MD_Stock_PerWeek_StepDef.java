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
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.material.dispo.model.I_MD_Stock_PerWeek_V;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Step definitions for asserting the {@code MD_Stock_PerWeek_V} weekly aggregation view.
 * Each row asserts one product × warehouse × week combination.
 */
@RequiredArgsConstructor
public class MD_Stock_PerWeek_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;

	/**
	 * Polls {@code MD_Stock_PerWeek_V} until the expected rows are found or the timeout expires.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) product<br>
	 *   <b>M_Warehouse_ID</b> — (required, identifier-ref) warehouse<br>
	 *   <b>WeekStartDate</b> — (required) ISO week start date (Monday), e.g. {@code 2026-06-08}<br>
	 *   <b>QtyExpectedShipments</b> — (required) expected value in that week<br>
	 *   <b>QtyExpectedReceipts</b> — (required) expected value in that week<br>
	 *   <b>QtyATP</b> — (required) expected projected stock at week-end<br>
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 10s, MD_Stock_PerWeek_V contains:
	 *   | M_Product_ID | M_Warehouse_ID | WeekStartDate | QtyExpectedShipments | QtyExpectedReceipts | QtyATP |
	 *   | product_1    | warehouse_1    | 2026-06-08    | 5                    | 0                   | -5     |
	 * </pre>
	 */
	@Then("^after not more than (.*)s, MD_Stock_PerWeek_V contains:$")
	public void validate_stock_per_week(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final DataTableRow row : DataTableRows.of(dataTable).toList())
		{
			assertStockPerWeekRow(timeoutSec, row);
		}
	}

	private void assertStockPerWeekRow(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final I_M_Product productRecord = row.getAsIdentifier(I_MD_Stock_PerWeek_V.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
		final I_M_Warehouse warehouseRecord = row.getAsIdentifier(I_MD_Stock_PerWeek_V.COLUMNNAME_M_Warehouse_ID).lookupNotNullIn(warehouseTable);

		final int productId = productRecord.getM_Product_ID();
		final int warehouseId = warehouseRecord.getM_Warehouse_ID();

		final LocalDate weekStartDate = row.getAsLocalDate(I_MD_Stock_PerWeek_V.COLUMNNAME_WeekStartDate);
		final Timestamp weekStartTs = Timestamp.valueOf(weekStartDate.atStartOfDay());

		final BigDecimal expectedShipments = row.getAsBigDecimal(I_MD_Stock_PerWeek_V.COLUMNNAME_QtyExpectedShipments);
		final BigDecimal expectedReceipts = row.getAsBigDecimal(I_MD_Stock_PerWeek_V.COLUMNNAME_QtyExpectedReceipts);
		final BigDecimal expectedAtp = row.getAsBigDecimal(I_MD_Stock_PerWeek_V.COLUMNNAME_QtyATP);

		final I_MD_Stock_PerWeek_V record = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, (ItemProvider<I_MD_Stock_PerWeek_V>)() ->  {
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
								+ " week=" + weekStartDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
			}

			return ProviderResult.resultWasFound(r);
		});

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(record.getQtyExpectedShipments())
				.as("QtyExpectedShipments for product=%d wh=%d week=%s", productId, warehouseId, weekStartDate)
				.isEqualByComparingTo(expectedShipments);
		softly.assertThat(record.getQtyExpectedReceipts())
				.as("QtyExpectedReceipts for product=%d wh=%d week=%s", productId, warehouseId, weekStartDate)
				.isEqualByComparingTo(expectedReceipts);
		softly.assertThat(record.getQtyATP())
				.as("QtyATP for product=%d wh=%d week=%s", productId, warehouseId, weekStartDate)
				.isEqualByComparingTo(expectedAtp);
		softly.assertAll();
	}
}
