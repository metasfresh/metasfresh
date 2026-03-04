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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Step definitions for testing {@code report.Package_Licensing_Product_Report(p_C_Country_ID)}.
 * <p>
 * Uses raw SQL for test data setup because no Java model classes (I_/X_) exist
 * for the packaging licensing tables (M_PackageLicensing_MaterialGroup,
 * M_Product_SmallPackagingMaterial, M_Product_OuterPackagingMaterial, etc.).
 * <p>
 * The SQL function is called directly via JDBC rather than through ExportToSpreadsheetProcess,
 * because the process just wraps the function + Excel export, and testing the function
 * directly covers the actual business logic without needing to parse Excel output.
 *
 * @see <a href="https://github.com/metasfresh/metasfresh/issues/28225">gh#28225</a>
 * @see <a href="https://github.com/metasfresh/metasfresh/issues/28487">gh#28487</a>
 */
@RequiredArgsConstructor
public class PackageLicensingProductReport_StepDef
{
	@NonNull private final M_Product_StepDefData productTable;

	private final List<Map<String, String>> reportResults = new ArrayList<>();

	/**
	 * Sets up packaging licensing master data for the given products.
	 * Each row creates country-specific group/material records and links them to the product.
	 *
	 * <pre>
	 * | M_Product_ID.Identifier | C_Country_ID | ProductGroupName           | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
	 * | p_1                     | 101          | Lebensmittel               | Glas                       | 0.150                | Kunststoffe                | 0.030                |
	 * </pre>
	 *
	 * <ul>
	 *   <li>{@code M_Product_ID.Identifier} — references a product created by {@code metasfresh contains M_Products}</li>
	 *   <li>{@code C_Country_ID} — country for the material/product group records (e.g. 101=DE, 108=AT)</li>
	 *   <li>{@code ProductGroupName} (optional) — creates M_PackageLicensing_ProductGroup and links it to the product</li>
	 *   <li>{@code SmallPackagingMaterialName} (optional) — creates M_PackageLicensing_MaterialGroup and links via M_Product_SmallPackagingMaterial</li>
	 *   <li>{@code SmallPackagingWeight} (optional) — sets M_Product.SmallPackagingWeight</li>
	 *   <li>{@code OuterPackagingMaterialName} (optional) — creates M_PackageLicensing_MaterialGroup and links via M_Product_OuterPackagingMaterial</li>
	 *   <li>{@code OuterPackagingWeight} (optional) — sets M_Product.OuterPackagingWeight</li>
	 *   <li>{@code ProductCategoryName} (optional) — creates M_Product_Category and assigns it to the product</li>
	 * </ul>
	 */
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

		final String productCategoryName = row.getAsOptionalString("ProductCategoryName").orElse(null);
		if (productCategoryName != null && !productCategoryName.isEmpty())
		{
			final int categoryId = insertProductCategory(productCategoryName);
			DB.executeUpdateAndThrowExceptionOnFail(
					"UPDATE M_Product SET M_Product_Category_ID=" + categoryId + " WHERE M_Product_ID=" + productId,
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

	private int insertProductCategory(@NonNull final String name)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_PRODUCT_CATEGORY_SEQ')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_Product_Category "
						+ "(M_Product_Category_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, Name, IsDefault, IsSelfService, PlannedMargin, MMPolicy) "
						+ "VALUES (" + id + ", " + Env.getAD_Client_ID(Env.getCtx()) + ", " + Env.getAD_Org_ID(Env.getCtx()) + ", 'Y', now(), 100, now(), 100, "
						+ sqlQuote(name) + ", " + sqlQuote(name) + ", 'N', 'N', 0, 'F')",
				ITrx.TRXNAME_None);
		return id;
	}

	/**
	 * Sets up M_HU_PI_Item_Product records for packaging instruction factor testing.
	 *
	 * <pre>
	 * | M_Product_ID.Identifier | Qty | IsDefaultForProduct |
	 * | p_1                     | 12  | Y                   |
	 * </pre>
	 */
	@And("package licensing packaging instruction test data is set up:")
	public void setupPackagingInstructionTestData(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::setupPackagingInstructionForProduct);
	}

	private void setupPackagingInstructionForProduct(@NonNull final DataTableRow row)
	{
		final I_M_Product product = productTable.get(row.getAsIdentifier("M_Product_ID"));
		final int productId = product.getM_Product_ID();
		final BigDecimal qty = row.getAsBigDecimal("Qty");
		final String isDefault = row.getAsOptionalString("IsDefaultForProduct").orElse("N");

		final int piItemId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT M_HU_PI_Item_ID FROM M_HU_PI_Item WHERE IsActive='Y' ORDER BY M_HU_PI_Item_ID LIMIT 1");

		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_HU_PI_ITEM_PRODUCT_SEQ')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_HU_PI_Item_Product "
						+ "(M_HU_PI_Item_Product_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
						+ "M_HU_PI_Item_ID, M_Product_ID, Qty, IsDefaultForProduct, IsInfiniteCapacity, IsAllowAnyProduct, ValidFrom, IsOrderInTUOMWhenMatched) "
						+ "VALUES (" + id + ", " + Env.getAD_Client_ID(Env.getCtx()) + ", " + Env.getAD_Org_ID(Env.getCtx()) + ", 'Y', now(), 100, now(), 100, "
						+ piItemId + ", " + productId + ", " + qty + ", " + sqlQuote(isDefault) + ", 'N', 'N', '1970-01-01', 'N')",
				ITrx.TRXNAME_None);
	}

	/**
	 * Sets up M_InOut + M_InOutLine records for delivered quantity testing.
	 * Creates a completed customer shipment (MovementType='C-', DocStatus='CO') with the given movement quantity.
	 *
	 * <pre>
	 * | M_Product_ID.Identifier | MovementQty |
	 * | p_1                     | 100         |
	 * </pre>
	 */
	@And("package licensing shipment test data is set up:")
	public void setupShipmentTestData(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::setupShipmentForProduct);
	}

	private void setupShipmentForProduct(@NonNull final DataTableRow row)
	{
		final I_M_Product product = productTable.get(row.getAsIdentifier("M_Product_ID"));
		final int productId = product.getM_Product_ID();
		final BigDecimal movementQty = row.getAsBigDecimal("MovementQty");

		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = Env.getAD_Org_ID(Env.getCtx());

		// Look up required FK references from existing data
		final int docTypeId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='MMS' AND IsActive='Y' AND AD_Client_ID=" + clientId + " ORDER BY C_DocType_ID LIMIT 1");
		final int bPartnerId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT C_BPartner_ID FROM C_BPartner WHERE IsActive='Y' AND AD_Client_ID=" + clientId + " ORDER BY C_BPartner_ID LIMIT 1");
		final int bPartnerLocationId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT C_BPartner_Location_ID FROM C_BPartner_Location WHERE C_BPartner_ID=" + bPartnerId + " AND IsActive='Y' LIMIT 1");
		final int warehouseId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT M_Warehouse_ID FROM M_Warehouse WHERE IsActive='Y' AND AD_Client_ID=" + clientId + " ORDER BY M_Warehouse_ID LIMIT 1");
		final int uomId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT C_UOM_ID FROM C_UOM WHERE IsActive='Y' ORDER BY C_UOM_ID LIMIT 1");

		// Insert M_InOut (completed customer shipment)
		final int inOutId = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_INOUT_SEQ')");
		final String documentNo = "PLR-TEST-" + inOutId;
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_InOut "
						+ "(M_InOut_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
						+ "IsSOTrx, DocumentNo, DocAction, DocStatus, Posted, Processed, C_DocType_ID, IsPrinted, "
						+ "MovementType, MovementDate, DateAcct, C_BPartner_ID, C_BPartner_Location_ID, M_Warehouse_ID, "
						+ "DeliveryRule, FreightCostRule, DeliveryViaRule, PriorityRule, SendEMail, "
						+ "IsInTransit, IsApproved, IsInDispute, IsUseBPartnerAddress, "
						+ "EDI_ExportStatus, IsEdiEnabled, IsManual, IsInOutApprovedForInvoicing, IsExportedToCustomsInvoice, IsFillUpSpareParts) "
						+ "VALUES (" + inOutId + ", " + clientId + ", " + orgId + ", 'Y', now(), 100, now(), 100, "
						+ "'Y', " + sqlQuote(documentNo) + ", 'CO', 'CO', 'N', 'Y', " + docTypeId + ", 'N', "
						+ "'C-', CURRENT_DATE, CURRENT_DATE, " + bPartnerId + ", " + bPartnerLocationId + ", " + warehouseId + ", "
						+ "'A', 'I', 'P', '5', 'N', "
						+ "'N', 'Y', 'N', 'N', "
						+ "'P', 'N', 'N', 'N', 'N', 'N')",
				ITrx.TRXNAME_None);

		// Insert M_InOutLine
		final int inOutLineId = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_INOUTLINE_SEQ')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_InOutLine "
						+ "(M_InOutLine_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
						+ "Line, M_InOut_ID, M_Product_ID, C_UOM_ID, MovementQty, QtyEntered, IsInvoiced, IsDescription, "
						+ "Processed, IsIndividualDescription, IsInDispute, QualityDiscountPercent, "
						+ "IsPackagingMaterial, IsManualPackingMaterial, IsHUPrepared, IsWarrantyCase) "
						+ "VALUES (" + inOutLineId + ", " + clientId + ", " + orgId + ", 'Y', now(), 100, now(), 100, "
						+ "10, " + inOutId + ", " + productId + ", " + uomId + ", " + movementQty + ", " + movementQty + ", 'N', 'N', "
						+ "'Y', 'N', 'N', 0, "
						+ "'N', 'N', 'N', 'N')",
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
					row.put("ProductCategory", rs.getString("ProductCategory"));
					row.put("MaterialType", rs.getString("MaterialType"));
					row.put("SmallPackagingMaterial", rs.getString("SmallPackagingMaterial"));
					row.put("SmallPackagingWeight", bigDecimalToString(rs.getBigDecimal("SmallPackagingWeight")));
					row.put("OverpackMaterial", rs.getString("OverpackMaterial"));
					row.put("OverpackWeight", bigDecimalToString(rs.getBigDecimal("OverpackWeight")));
					row.put("PackagingInstructionFactor", bigDecimalToString(rs.getBigDecimal("PackagingInstructionFactor")));
					row.put("DeliveredQtyLast12Months", bigDecimalToString(rs.getBigDecimal("DeliveredQtyLast12Months")));
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

	/**
	 * Verifies the report results contain exactly the expected rows (matched by ProductName).
	 * Empty cells in the expected table assert that the actual value is null/empty.
	 * Columns not present in the expected table are not checked.
	 *
	 * <pre>
	 * | ProductName     | MaterialType | ProductCategory   | SmallPackagingMaterial | SmallPackagingWeight | OverpackMaterial | OverpackWeight | PackagingInstructionFactor | DeliveredQtyLast12Months |
	 * | PLR Test Prod 1 | Lebensmittel | Test Category     | Glas                   | 0.150                | Kunststoffe      | 0.030          | 12                         | 100                      |
	 * </pre>
	 *
	 * <ul>
	 *   <li>{@code ProductName} — required; used to match actual report rows</li>
	 *   <li>{@code MaterialType} (optional) — expected comma-separated product group names; empty = assert null</li>
	 *   <li>{@code ProductCategory} (optional) — expected product category name; empty = assert null</li>
	 *   <li>{@code SmallPackagingMaterial} (optional) — expected small packaging material name; empty = assert null</li>
	 *   <li>{@code SmallPackagingWeight} (optional) — expected weight as decimal; empty = assert null</li>
	 *   <li>{@code OverpackMaterial} (optional) — expected outer packaging material name; empty = assert null</li>
	 *   <li>{@code OverpackWeight} (optional) — expected weight as decimal; empty = assert null</li>
	 *   <li>{@code PackagingInstructionFactor} (optional) — expected PI quantity; empty = assert null</li>
	 *   <li>{@code DeliveredQtyLast12Months} (optional) — expected delivered quantity; empty = assert null</li>
	 * </ul>
	 *
	 * Also asserts that no row multiplication occurred (exactly one result row per expected product).
	 */
	@Then("the Package Licensing Product Report result contains:")
	public void verifyReportResults(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> expectedRows = dataTable.asMaps();

		final SoftAssertions softly = new SoftAssertions();

		final Set<String> expectedProductNames = expectedRows.stream()
				.map(r -> r.get("ProductName"))
				.collect(Collectors.toSet());

		final List<Map<String, String>> matchingResults = reportResults.stream()
				.filter(r -> expectedProductNames.contains(r.get("ProductName")))
				.collect(Collectors.toList());

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

			assertOptionalColumnIfPresent(softly, expectedRow, actualRow, "MaterialType");
			assertOptionalColumnIfPresent(softly, expectedRow, actualRow, "ProductCategory");
			assertOptionalColumnIfPresent(softly, expectedRow, actualRow, "SmallPackagingMaterial");
			assertOptionalBigDecimalColumnIfPresent(softly, expectedRow, actualRow, "SmallPackagingWeight");
			assertOptionalColumnIfPresent(softly, expectedRow, actualRow, "OverpackMaterial");
			assertOptionalBigDecimalColumnIfPresent(softly, expectedRow, actualRow, "OverpackWeight");
			assertOptionalBigDecimalColumnIfPresent(softly, expectedRow, actualRow, "PackagingInstructionFactor");
			assertOptionalBigDecimalColumnIfPresent(softly, expectedRow, actualRow, "DeliveredQtyLast12Months");
		}

		softly.assertThat(matchingResults)
				.as("Report should contain exactly one row per expected product (no row multiplication)")
				.hasSize(expectedRows.size());

		softly.assertAll();
	}

	private static void assertOptionalColumnIfPresent(
			@NonNull final SoftAssertions softly,
			@NonNull final Map<String, String> expectedRow,
			@NonNull final Map<String, String> actualRow,
			@NonNull final String columnName)
	{
		if (!expectedRow.containsKey(columnName))
		{
			return; // column not in expected table, skip
		}

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

	private static void assertOptionalBigDecimalColumnIfPresent(
			@NonNull final SoftAssertions softly,
			@NonNull final Map<String, String> expectedRow,
			@NonNull final Map<String, String> actualRow,
			@NonNull final String columnName)
	{
		if (!expectedRow.containsKey(columnName))
		{
			return; // column not in expected table, skip
		}

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
