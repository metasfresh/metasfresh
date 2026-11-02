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

package de.metas.cucumber.stepdefs.pricing;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies the output of the report SQL function {@code report.getSalesPriceSpecialAndBase}.
 * <p>
 * This function has no Java/DAO equivalent (it is called by the Jasper report layer), so it is
 * exercised here via a direct SELECT — the same read-via-SQL approach used by AccountingCucumberHelper.
 */
@RequiredArgsConstructor
public class GetSalesPriceSpecialAndBase_StepDef
{
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final C_BPartner_Location_StepDefData bpartnerLocationTable;

	/**
	 * @cucumber.stepdef
	 * Resolves the special (customer-specific) and base (standard list) price for a
	 * business-partner location + product on a given date and asserts the returned values.
	 * An empty price cell asserts the column comes back NULL.
	 * @cucumber.columns
	 *   <b>C_BPartner_Location_ID</b> — (required, identifier-ref) the delivery location whose pricing system resolves the price<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the article to price<br>
	 *   <b>Date</b> — (required) pricing date, e.g. 2022-05-17<br>
	 *   <b>SpecialPriceStd</b> — (optional) expected special price; empty = expect NULL<br>
	 *   <b>BasePriceStd</b> — (optional) expected base price; empty = expect NULL<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData, C_BPartner_Location_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then report.getSalesPriceSpecialAndBase returns:
	 *   | C_BPartner_Location_ID | M_Product_ID | Date       | SpecialPriceStd | BasePriceStd |
	 *   | customerLocation       | productBoth  | 2022-05-17 | 90              | 100          |
	 * </pre>
	 */
	@Then("report.getSalesPriceSpecialAndBase returns:")
	public void getSalesPriceSpecialAndBase_returns(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::assertSalesPriceSpecialAndBase);
	}

	private void assertSalesPriceSpecialAndBase(@NonNull final DataTableRow row)
	{
		final int bpartnerLocationId = row.getAsIdentifier(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID)
				.lookupNotNullIn(bpartnerLocationTable)
				.getC_BPartner_Location_ID();
		final int productId = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID)
				.lookupNotNullIn(productTable)
				.getM_Product_ID();
		final Timestamp date = row.getAsLocalDateTimestamp("Date");

		final SpecialAndBasePrice actual = resolve(date, bpartnerLocationId, productId);

		final Optional<BigDecimal> expectedSpecial = row.getAsOptionalBigDecimal("SpecialPriceStd");
		if (expectedSpecial.isPresent())
		{
			assertThat(actual.getSpecialPriceStd())
					.as("SpecialPriceStd for product=%s location=%s", productId, bpartnerLocationId)
					.isNotNull()
					.isEqualByComparingTo(expectedSpecial.get());
		}
		else
		{
			assertThat(actual.getSpecialPriceStd())
					.as("SpecialPriceStd should be empty for product=%s location=%s", productId, bpartnerLocationId)
					.isNull();
		}

		final Optional<BigDecimal> expectedBase = row.getAsOptionalBigDecimal("BasePriceStd");
		if (expectedBase.isPresent())
		{
			assertThat(actual.getBasePriceStd())
					.as("BasePriceStd for product=%s location=%s", productId, bpartnerLocationId)
					.isNotNull()
					.isEqualByComparingTo(expectedBase.get());
		}
		else
		{
			assertThat(actual.getBasePriceStd())
					.as("BasePriceStd should be empty for product=%s location=%s", productId, bpartnerLocationId)
					.isNull();
		}
	}

	private SpecialAndBasePrice resolve(@NonNull final Timestamp date, final int bpartnerLocationId, final int productId)
	{
		final String sql = "SELECT SpecialPriceStd, BasePriceStd"
				+ " FROM report.getSalesPriceSpecialAndBase(?, ?, ?)";

		final Object[] sqlParams = new Object[] { date, bpartnerLocationId, productId };
		final List<SpecialAndBasePrice> rows = DB.retrieveRows(
				sql,
				sqlParams,
				rs -> new SpecialAndBasePrice(rs.getBigDecimal("SpecialPriceStd"), rs.getBigDecimal("BasePriceStd")));

		assertThat(rows)
				.as("report.getSalesPriceSpecialAndBase must return exactly one row")
				.hasSize(1);

		return rows.get(0);
	}

	@Value
	private static class SpecialAndBasePrice
	{
		BigDecimal specialPriceStd;
		BigDecimal basePriceStd;
	}
}
