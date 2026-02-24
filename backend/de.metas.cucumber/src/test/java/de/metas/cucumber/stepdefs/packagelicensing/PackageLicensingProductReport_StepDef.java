package de.metas.cucumber.stepdefs.packagelicensing;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PackageLicensingProductReport_StepDef
{
	@NonNull private final M_Product_StepDefData productTable;

	private final List<Map<String, String>> reportResults = new ArrayList<>();

	@And("package licensing test data is set up:")
	public void setupPackageLicensingTestData(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::setupPackageLicensingForProduct);
	}

	private void setupPackageLicensingForProduct(@NonNull final DataTableRow row)
	{
		final I_M_Product product = productTable.get(row.getAsIdentifier("M_Product_ID"));
		final int productId = product.getM_Product_ID();
		final int countryId = row.getAsInt("C_Country_ID");

		final String productGroupName = row.getAsOptionalString("ProductGroupName").orElse(null);
		final String smallPackagingMaterialName = row.getAsOptionalString("SmallPackagingMaterialName").orElse(null);
		final BigDecimal smallPackagingWeight = row.getAsOptionalBigDecimal("SmallPackagingWeight").orElse(null);
		final String outerPackagingMaterialName = row.getAsOptionalString("OuterPackagingMaterialName").orElse(null);
		final BigDecimal outerPackagingWeight = row.getAsOptionalBigDecimal("OuterPackagingWeight").orElse(null);

		if (productGroupName != null && !productGroupName.isEmpty())
		{
			final int productGroupId = insertPackageLicensingProductGroup(countryId, productGroupName);
			insertProductPackageLicensingProductGroup(productId, productGroupId);
		}

		if (smallPackagingMaterialName != null && !smallPackagingMaterialName.isEmpty())
		{
			final int materialGroupId = insertPackageLicensingMaterialGroup(countryId, smallPackagingMaterialName);
			insertProductSmallPackagingMaterial(productId, materialGroupId);
		}

		if (smallPackagingWeight != null)
		{
			DB.executeUpdateAndThrowExceptionOnFail(
					"UPDATE M_Product SET SmallPackagingWeight=" + smallPackagingWeight + " WHERE M_Product_ID=" + productId,
					ITrx.TRXNAME_None);
		}

		if (outerPackagingMaterialName != null && !outerPackagingMaterialName.isEmpty())
		{
			final int materialGroupId = insertPackageLicensingMaterialGroup(countryId, outerPackagingMaterialName);
			insertProductOuterPackagingMaterial(productId, materialGroupId);
		}

		if (outerPackagingWeight != null)
		{
			DB.executeUpdateAndThrowExceptionOnFail(
					"UPDATE M_Product SET OuterPackagingWeight=" + outerPackagingWeight + " WHERE M_Product_ID=" + productId,
					ITrx.TRXNAME_None);
		}
	}

	private int insertPackageLicensingMaterialGroup(final int countryId, @NonNull final String name)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_PACKAGELICENSING_MATERIALGROUP_SEQ')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_PackageLicensing_MaterialGroup "
						+ "(M_PackageLicensing_MaterialGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, Name, C_Country_ID) "
						+ "VALUES (" + id + ", " + Env.getAD_Client_ID(Env.getCtx()) + ", " + Env.getAD_Org_ID(Env.getCtx()) + ", 'Y', now(), 100, now(), 100, "
						+ sqlQuote(name) + ", " + sqlQuote(name) + ", " + countryId + ")",
				ITrx.TRXNAME_None);
		return id;
	}

	private int insertPackageLicensingProductGroup(final int countryId, @NonNull final String name)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_PACKAGELICENSING_PRODUCTGROUP_SEQ')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_PackageLicensing_ProductGroup "
						+ "(M_PackageLicensing_ProductGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, Name, C_Country_ID) "
						+ "VALUES (" + id + ", " + Env.getAD_Client_ID(Env.getCtx()) + ", " + Env.getAD_Org_ID(Env.getCtx()) + ", 'Y', now(), 100, now(), 100, "
						+ sqlQuote(name) + ", " + sqlQuote(name) + ", " + countryId + ")",
				ITrx.TRXNAME_None);
		return id;
	}

	private void insertProductPackageLicensingProductGroup(final int productId, final int productGroupId)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_PRODUCT_PACKAGELICENSING_PRODUCTGROUP_SEQ')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_Product_PackageLicensing_ProductGroup "
						+ "(M_Product_PackageLicensing_ProductGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, M_Product_ID, M_PackageLicensing_ProductGroup_ID) "
						+ "VALUES (" + id + ", " + Env.getAD_Client_ID(Env.getCtx()) + ", " + Env.getAD_Org_ID(Env.getCtx()) + ", 'Y', now(), 100, now(), 100, "
						+ productId + ", " + productGroupId + ")",
				ITrx.TRXNAME_None);
	}

	private void insertProductSmallPackagingMaterial(final int productId, final int materialGroupId)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_PRODUCT_SMALLPACKAGINGMATERIAL_SEQ')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_Product_SmallPackagingMaterial "
						+ "(M_Product_SmallPackagingMaterial_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, M_Product_ID, M_PackageLicensing_MaterialGroup_ID) "
						+ "VALUES (" + id + ", " + Env.getAD_Client_ID(Env.getCtx()) + ", " + Env.getAD_Org_ID(Env.getCtx()) + ", 'Y', now(), 100, now(), 100, "
						+ productId + ", " + materialGroupId + ")",
				ITrx.TRXNAME_None);
	}

	private void insertProductOuterPackagingMaterial(final int productId, final int materialGroupId)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_PRODUCT_OUTERPACKAGINGMATERIAL_SEQ')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_Product_OuterPackagingMaterial "
						+ "(M_Product_OuterPackagingMaterial_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, M_Product_ID, M_PackageLicensing_MaterialGroup_ID) "
						+ "VALUES (" + id + ", " + Env.getAD_Client_ID(Env.getCtx()) + ", " + Env.getAD_Org_ID(Env.getCtx()) + ", 'Y', now(), 100, now(), 100, "
						+ productId + ", " + materialGroupId + ")",
				ITrx.TRXNAME_None);
	}

	@When("the Package Licensing Product Report is executed without country filter")
	public void executeReportWithoutCountryFilter() throws SQLException
	{
		executeReport(null);
	}

	@When("the Package Licensing Product Report is executed with C_Country_ID {int}")
	public void executeReportWithCountryFilter(final int countryId) throws SQLException
	{
		executeReport(countryId);
	}

	private void executeReport(final Integer countryId) throws SQLException
	{
		reportResults.clear();

		final String sql = "SELECT * FROM report.Package_Licensing_Product_Report(?)";
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			if (countryId != null)
			{
				pstmt.setInt(1, countryId);
			}
			else
			{
				pstmt.setNull(1, Types.NUMERIC);
			}

			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					final Map<String, String> row = new LinkedHashMap<>();
					row.put("ProductNo", rs.getString("ProductNo"));
					row.put("ProductName", rs.getString("ProductName"));
					row.put("ProductGroup", rs.getString("ProductGroup"));
					row.put("SmallPackagingMaterial", rs.getString("SmallPackagingMaterial"));
					row.put("SmallPackagingWeight", bigDecimalToString(rs.getBigDecimal("SmallPackagingWeight")));
					row.put("OverpackMaterial", rs.getString("OverpackMaterial"));
					row.put("OverpackWeight", bigDecimalToString(rs.getBigDecimal("OverpackWeight")));
					reportResults.add(row);
				}
			}
		}
	}

	private static String sqlQuote(@NonNull final String value)
	{
		return "'" + value.replace("'", "''") + "'";
	}

	private static String bigDecimalToString(final BigDecimal value)
	{
		return value != null ? value.stripTrailingZeros().toPlainString() : null;
	}

	@Then("the Package Licensing Product Report result contains:")
	public void verifyReportResults(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> expectedRows = dataTable.asMaps();

		final SoftAssertions softly = new SoftAssertions();

		final java.util.Set<String> expectedProductNames = expectedRows.stream()
				.map(r -> r.get("ProductName"))
				.collect(java.util.stream.Collectors.toSet());

		final List<Map<String, String>> matchingResults = reportResults.stream()
				.filter(r -> expectedProductNames.contains(r.get("ProductName")))
				.collect(java.util.stream.Collectors.toList());

		for (final Map<String, String> expectedRow : expectedRows)
		{
			final String expectedProductName = expectedRow.get("ProductName");

			final Map<String, String> actualRow = matchingResults.stream()
					.filter(r -> expectedProductName.equals(r.get("ProductName")))
					.findFirst()
					.orElse(null);

			softly.assertThat(actualRow)
					.as("Report row for ProductName=" + expectedProductName)
					.isNotNull();

			if (actualRow == null)
			{
				continue;
			}

			assertOptionalColumn(softly, expectedRow, actualRow, "ProductGroup");
			assertOptionalColumn(softly, expectedRow, actualRow, "SmallPackagingMaterial");
			assertOptionalBigDecimalColumn(softly, expectedRow, actualRow, "SmallPackagingWeight");
			assertOptionalColumn(softly, expectedRow, actualRow, "OverpackMaterial");
			assertOptionalBigDecimalColumn(softly, expectedRow, actualRow, "OverpackWeight");
		}

		softly.assertThat(matchingResults)
				.as("Report should contain exactly one row per expected product (no row multiplication)")
				.hasSize(expectedRows.size());

		softly.assertAll();
	}

	@Then("the Package Licensing Product Report result does not contain product {string}")
	public void verifyReportDoesNotContainProduct(@NonNull final String productName)
	{
		final boolean found = reportResults.stream()
				.anyMatch(r -> productName.equals(r.get("ProductName")));

		org.assertj.core.api.Assertions.assertThat(found)
				.as("Report should NOT contain product " + productName)
				.isFalse();
	}

	private static void assertOptionalColumn(
			@NonNull final SoftAssertions softly,
			@NonNull final Map<String, String> expectedRow,
			@NonNull final Map<String, String> actualRow,
			@NonNull final String columnName)
	{
		final String expectedValue = expectedRow.get(columnName);
		if (expectedValue != null && !expectedValue.isEmpty())
		{
			softly.assertThat(actualRow.get(columnName))
					.as(columnName)
					.isEqualTo(expectedValue);
		}
		else
		{
			softly.assertThat(actualRow.get(columnName))
					.as(columnName + " should be null/empty")
					.isNullOrEmpty();
		}
	}

	private static void assertOptionalBigDecimalColumn(
			@NonNull final SoftAssertions softly,
			@NonNull final Map<String, String> expectedRow,
			@NonNull final Map<String, String> actualRow,
			@NonNull final String columnName)
	{
		final String expectedValue = expectedRow.get(columnName);
		if (expectedValue != null && !expectedValue.isEmpty())
		{
			final BigDecimal expected = new BigDecimal(expectedValue);
			final String actualStr = actualRow.get(columnName);
			softly.assertThat(actualStr)
					.as(columnName + " should not be null")
					.isNotNull();
			if (actualStr != null)
			{
				softly.assertThat(new BigDecimal(actualStr))
						.as(columnName)
						.isEqualByComparingTo(expected);
			}
		}
		else
		{
			softly.assertThat(actualRow.get(columnName))
					.as(columnName + " should be null/empty")
					.isNullOrEmpty();
		}
	}
}
