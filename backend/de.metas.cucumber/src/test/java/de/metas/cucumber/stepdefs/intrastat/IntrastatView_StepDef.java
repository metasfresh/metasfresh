package de.metas.cucumber.stepdefs.intrastat;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Step definitions for testing {@code de_metas_endcustomer_fresh_reports.M_InOut_V}
 * (the Intrastat aggregation view).
 * <p>
 * Uses raw SQL to query the view because no Java model class exists for it.
 * <p>
 * The critical invariant being tested: weight must be per-line (per commodity group),
 * NOT the total shipment weight. Before gh#28336, the view used
 * {@code Docs_Sales_InOut_Sum_Weight(m_inout_id)} which returned the entire shipment's
 * total weight, causing every commodity group to show the full shipment weight.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/28336">me03#28336</a>
 */
@RequiredArgsConstructor
public class IntrastatView_StepDef
{
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_InOut_StepDefData inOutTable;

	private final List<Map<String, String>> viewResults = new ArrayList<>();

	/**
	 * Sets M_Product.Weight for the given products.
	 * This is the fallback weight used by M_InOut_V when no catch weight or UOM conversion is available.
	 *
	 * <pre>
	 * | M_Product_ID.Identifier | Weight |
	 * | p_1                     | 2.5    |
	 * </pre>
	 */
	@And("M_Product weight is set:")
	public void setProductWeight(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Product product = productTable.get(row.getAsIdentifier("M_Product_ID"));
			final BigDecimal weight = row.getAsBigDecimal("Weight");

			DB.executeUpdateAndThrowExceptionOnFail(
					"UPDATE M_Product SET Weight=" + weight + " WHERE M_Product_ID=" + product.getM_Product_ID(),
					ITrx.TRXNAME_None);
		});
	}

	/**
	 * Creates M_CommodityNumber records and links them to products.
	 *
	 * <pre>
	 * | M_Product_ID.Identifier | CommodityNumberValue |
	 * | p_1                     | 12345678             |
	 * </pre>
	 */
	@And("M_CommodityNumber is set for products:")
	public void setCommodityNumbers(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Product product = productTable.get(row.getAsIdentifier("M_Product_ID"));
			final String value = row.getAsString("CommodityNumberValue");

			int cnId = DB.getSQLValueEx(ITrx.TRXNAME_None,
					"SELECT M_CommodityNumber_ID FROM M_CommodityNumber WHERE Value=" + sqlQuote(value));
			if (cnId <= 0)
			{
				cnId = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_CommodityNumber_Seq')");
				DB.executeUpdateAndThrowExceptionOnFail(
						"INSERT INTO M_CommodityNumber (M_CommodityNumber_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, Name)"
								+ " VALUES (" + cnId + ", 1000000, 0, 'Y', now(), 100, now(), 100, "
								+ sqlQuote(value) + ", " + sqlQuote(value) + ")",
						ITrx.TRXNAME_None);
			}

			DB.executeUpdateAndThrowExceptionOnFail(
					"UPDATE M_Product SET M_CommodityNumber_ID=" + cnId + " WHERE M_Product_ID=" + product.getM_Product_ID(),
					ITrx.TRXNAME_None);
		});
	}

	/**
	 * Creates M_CustomsTariff records and links them to products.
	 *
	 * <pre>
	 * | M_Product_ID.Identifier | CustomsTariffValue |
	 * | p_1                     | 1234567890         |
	 * </pre>
	 */
	@And("M_CustomsTariff is set for products:")
	public void setCustomsTariffs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Product product = productTable.get(row.getAsIdentifier("M_Product_ID"));
			final String value = row.getAsString("CustomsTariffValue");

			int ctId = DB.getSQLValueEx(ITrx.TRXNAME_None,
					"SELECT M_CustomsTariff_ID FROM M_CustomsTariff WHERE Value=" + sqlQuote(value));
			if (ctId <= 0)
			{
				ctId = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_CustomsTariff_Seq')");
				DB.executeUpdateAndThrowExceptionOnFail(
						"INSERT INTO M_CustomsTariff (M_CustomsTariff_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, Name)"
								+ " VALUES (" + ctId + ", 1000000, 0, 'Y', now(), 100, now(), 100, "
								+ sqlQuote(value) + ", " + sqlQuote(value) + ")",
						ITrx.TRXNAME_None);
			}

			DB.executeUpdateAndThrowExceptionOnFail(
					"UPDATE M_Product SET M_CustomsTariff_ID=" + ctId + " WHERE M_Product_ID=" + product.getM_Product_ID(),
					ITrx.TRXNAME_None);
		});
	}

	/**
	 * Queries the M_InOut_V view filtering by a specific M_InOut (via its invoice).
	 * The view is filtered by the products that appear in the given shipment's lines.
	 *
	 * <pre>
	 * | M_InOut_ID.Identifier |
	 * | shipment_1            |
	 * </pre>
	 */
	@When("M_InOut_V is queried for shipment {string}")
	public void queryInOutView(@NonNull final String shipmentIdentifier) throws SQLException
	{
		viewResults.clear();

		final I_M_InOut inOut = inOutTable.get(shipmentIdentifier);
		final int inOutId = inOut.getM_InOut_ID();

		final String sql = "SELECT v.commoditynumber, v.productname, v.weight, v.movementqty, v.grandtotal, v.customstariff, v.deliveredFromCountry, v.deliveryCountry"
				+ " FROM de_metas_endcustomer_fresh_reports.M_InOut_V v"
				+ " WHERE EXISTS ("
				+ "   SELECT 1 FROM M_InOutLine iol"
				+ "   JOIN M_Product p ON p.M_Product_ID = iol.M_Product_ID"
				+ "   WHERE iol.M_InOut_ID = ? AND p.Name = v.productname"
				+ " )"
				+ " ORDER BY v.commoditynumber";

		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setInt(1, inOutId);
			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					final Map<String, String> row = new LinkedHashMap<>();
					row.put("commoditynumber", rs.getString("commoditynumber"));
					row.put("productname", rs.getString("productname"));
					row.put("weight", bigDecimalToString(rs.getBigDecimal("weight")));
					row.put("movementqty", bigDecimalToString(rs.getBigDecimal("movementqty")));
					row.put("grandtotal", bigDecimalToString(rs.getBigDecimal("grandtotal")));
					row.put("customstariff", rs.getString("customstariff"));
					row.put("deliveredFromCountry", rs.getString("deliveredFromCountry"));
					row.put("deliveryCountry", rs.getString("deliveryCountry"));
					viewResults.add(row);
				}
			}
		}
	}

	/**
	 * Validates the M_InOut_V results contain the expected rows, matched by product identifier.
	 * The critical assertion is that weight is per-product, not the total shipment weight.
	 *
	 * <p>Uses M_Product_ID.Identifier to look up the product's actual Name,
	 * then matches it against the view's productname column. This avoids
	 * dependence on hardcoded product names and isolates test runs from
	 * stale data in the view (which aggregates globally by productname).
	 *
	 * <pre>
	 * | M_Product_ID.Identifier | weight | movementqty |
	 * | p_intra_1               | 25     | 10          |
	 * | p_intra_2               | 15     | 5           |
	 * </pre>
	 */
	@Then("M_InOut_V result contains:")
	public void verifyResults(@NonNull final DataTable dataTable)
	{
		final DataTableRows rows = DataTableRows.of(dataTable);
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(viewResults)
				.as("M_InOut_V should return rows")
				.isNotEmpty();

		final int[] expectedCount = {0};
		rows.forEach(row -> {
			expectedCount[0]++;
			final String expectedProductName = row.getAsOptionalIdentifier("M_Product_ID")
					.map(id -> productTable.get(id).getName())
					.orElseGet(() -> row.getAsString("productname"));

			final Map<String, String> actualRow = viewResults.stream()
					.filter(r -> expectedProductName.equals(r.get("productname")))
					.findFirst()
					.orElse(null);

			softly.assertThat(actualRow)
					.as("M_InOut_V row for productname=" + expectedProductName)
					.isNotNull();

			if (actualRow == null)
			{
				return;
			}

			row.getAsOptionalBigDecimal("weight")
					.ifPresent(expectedWeight -> softly.assertThat(new BigDecimal(actualRow.get("weight")))
							.as("weight for " + expectedProductName)
							.isEqualByComparingTo(expectedWeight));

			row.getAsOptionalBigDecimal("movementqty")
					.ifPresent(expectedQty -> softly.assertThat(new BigDecimal(actualRow.get("movementqty")))
							.as("movementqty for " + expectedProductName)
							.isEqualByComparingTo(expectedQty));
		});

		softly.assertThat(viewResults)
				.as("M_InOut_V should have exactly one row per expected product (no row multiplication)")
				.hasSize(expectedCount[0]);

		softly.assertAll();
	}

	private static String sqlQuote(@NonNull final String value)
	{
		return "'" + value.replace("'", "''") + "'";
	}

	private static String bigDecimalToString(final BigDecimal value)
	{
		return value != null ? value.stripTrailingZeros().toPlainString() : null;
	}
}
