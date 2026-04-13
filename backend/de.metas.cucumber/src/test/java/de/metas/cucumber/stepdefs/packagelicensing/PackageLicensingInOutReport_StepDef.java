package de.metas.cucumber.stepdefs.packagelicensing;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import de.metas.organization.OrgId;
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
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Step definitions for testing the Package Licensing report SQL functions:
 * <ul>
 *   <li>{@code report.Package_Licensing_InOut_Report()} — detail report (per InOut line)</li>
 *   <li>{@code report.Package_Licensing_Product_Report()} — product + vendor aggregation</li>
 *   <li>{@code report.Package_Licensing_InOut_Summary_Report()} — Mengenmeldung pivot by material group</li>
 * </ul>
 * <p>
 * Uses raw SQL for test data setup because no Java model classes exist
 * for the packaging licensing tables, and because the warehouse needs
 * a specific C_Location_ID (country) which the standard step defs don't support.
 * <p>
 * The SQL functions are called directly via JDBC rather than through ExportToSpreadsheetProcess.
 *
 * @see <a href="https://github.com/metasfresh/metasfresh/issues/28487">gh#28487</a>
 * @see <a href="https://github.com/metasfresh/metasfresh/issues/29223">gh#29223</a>
 */
@RequiredArgsConstructor
public class PackageLicensingInOutReport_StepDef
{
	@NonNull private final M_Product_StepDefData productTable;

	private final List<Map<String, String>> reportResults = new ArrayList<>();
	private final List<Map<String, String>> productReportResults = new ArrayList<>();
	private final List<Map<String, String>> summaryReportResults = new ArrayList<>();

	/** Maps material group name to the Summary Report column name (e.g. "Glas AT" → "Material_M3_Weight"). */
	private final Map<String, String> summaryMaterialColumnMapping = new LinkedHashMap<>();

	/** Maps SharedBPartnerKey (from feature file) to C_BPartner_ID, for reusing the same vendor across multiple InOuts. */
	private final Map<String, Integer> sharedBPartners = new LinkedHashMap<>();

	/**
	 * Sets up packaging licensing master data for the given products.
	 * Reuses the same pattern as the (dropped) Product Report step def.
	 *
	 * <pre>
	 * | M_Product_ID.Identifier | CountryCode | ProductGroupName | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
	 * </pre>
	 * {@code CountryCode} is preferred (ISO code like AT, DE). {@code C_Country_ID} also accepted as fallback.
	 */
	@And("package licensing master data is set up:")
	public void setupPackageLicensingMasterData(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::setupPackageLicensingForProduct);
	}

	private void setupPackageLicensingForProduct(@NonNull final DataTableRow row)
	{
		final I_M_Product product = productTable.get(row.getAsIdentifier("M_Product_ID"));
		final int productId = product.getM_Product_ID();
		final int countryId = row.getAsOptionalString("CountryCode")
				.map(PackageLicensingInOutReport_StepDef::getCountryIdByCode)
				.orElseGet(() -> row.getAsInt("C_Country_ID"));

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

	/**
	 * Creates M_InOut + M_InOutLine test records with full control over
	 * warehouse country and shipment destination country.
	 *
	 * <pre>
	 * | M_Product_ID.Identifier | DocumentNo | MovementDate | MovementType | IsSOTrx | DocStatus | WarehouseCountryCode | DestinationCountryCode | MovementQty |
	 * </pre>
	 *
	 * <ul>
	 *   <li>{@code WarehouseCountryCode} — ISO country code for the warehouse location (e.g. AT, DE)</li>
	 *   <li>{@code DestinationCountryCode} — ISO country code for the BPartner location (shipment destination)</li>
	 *   <li>{@code DocStatus} — document status (DR, CO, CL)</li>
	 *   <li>{@code MovementType} — V+ for vendor receipt, C- for customer shipment</li>
	 * </ul>
	 */
	@And("package licensing InOut test data is set up:")
	public void setupInOutTestData(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createInOutWithLine);
	}

	private void createInOutWithLine(@NonNull final DataTableRow row)
	{
		final I_M_Product product = productTable.get(row.getAsIdentifier("M_Product_ID"));
		final int productId = product.getM_Product_ID();
		final String documentNo = row.getAsString("DocumentNo");
		final String movementDate = row.getAsString("MovementDate");
		final String movementType = row.getAsString("MovementType");
		final String isSOTrx = row.getAsString("IsSOTrx");
		final String docStatus = row.getAsString("DocStatus");
		final String warehouseCountryCode = row.getAsString("WarehouseCountryCode");
		final String destinationCountryCode = row.getAsOptionalString("DestinationCountryCode").orElse(warehouseCountryCode);
		final BigDecimal movementQty = row.getAsBigDecimal("MovementQty");

		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = OrgId.MAIN.getRepoId();

		// Create or reuse BPartner. When SharedBPartnerKey is provided, subsequent InOuts
		// with the same key reuse the same BPartner (needed for exemption date range tests).
		final String sharedBPartnerKey = row.getAsOptionalString("SharedBPartnerKey").orElse(null);
		final int bpartnerId;
		if (sharedBPartnerKey != null && sharedBPartners.containsKey(sharedBPartnerKey))
		{
			bpartnerId = sharedBPartners.get(sharedBPartnerKey);
		}
		else
		{
			bpartnerId = createBPartner(clientId, orgId, documentNo + "_BP");
			if (sharedBPartnerKey != null)
			{
				sharedBPartners.put(sharedBPartnerKey, bpartnerId);
			}
		}

		// Resolve or create warehouse location in the specified country
		final int warehouseCountryId = getCountryIdByCode(warehouseCountryCode);
		final int warehouseLocationId = createLocation(warehouseCountryId);
		final int warehouseBPLocationId = createBPartnerLocation(clientId, orgId, bpartnerId, warehouseLocationId);
		final int warehouseId = createWarehouse(clientId, orgId, documentNo + "_WH", warehouseLocationId, bpartnerId, warehouseBPLocationId);
		final int locatorId = createLocator(clientId, orgId, warehouseId);

		// Create BPartner location in destination country
		final int destCountryId = getCountryIdByCode(destinationCountryCode);
		final int bpLocationId = createLocation(destCountryId);
		final int bpartnerLocationId = createBPartnerLocation(clientId, orgId, bpartnerId, bpLocationId);

		// Resolve C_DocType
		final String docBaseType = "Y".equals(isSOTrx) ? "MMS" : "MMR"; // Material Movement Shipment / Receipt
		final int docTypeId = getDocTypeId(clientId, orgId, docBaseType);

		// Create M_InOut header
		final int inoutId = createMInOut(clientId, orgId, documentNo, movementDate, movementType, isSOTrx,
				docStatus, warehouseId, bpartnerId, bpartnerLocationId, docTypeId);

		// Create M_InOutLine
		final int uomId = product.getC_UOM_ID();
		createMInOutLine(clientId, orgId, inoutId, productId, movementQty, uomId, locatorId);
	}

	/**
	 * Sets up M_HU_PI_Item_Product for packaging instruction factor.
	 *
	 * <pre>
	 * | M_Product_ID.Identifier | Qty | IsDefaultForProduct |
	 * </pre>
	 */
	@And("packaging instruction factor test data is set up:")
	public void setupPackagingInstructionFactor(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Product product = productTable.get(row.getAsIdentifier("M_Product_ID"));
			final int productId = product.getM_Product_ID();
			final BigDecimal qty = row.getAsBigDecimal("Qty");
			final String isDefault = row.getAsOptionalString("IsDefaultForProduct").orElse("Y");

			// Need M_HU_PI_Item first
			final int huPiItemId = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_HU_PI_Item_seq')");
			// Get any active M_HU_PI_Version
			final int huPiVersionId = DB.getSQLValueEx(ITrx.TRXNAME_None,
					"SELECT M_HU_PI_Version_ID FROM M_HU_PI_Version WHERE IsActive='Y' ORDER BY M_HU_PI_Version_ID LIMIT 1");

			DB.executeUpdateAndThrowExceptionOnFail(
					"INSERT INTO M_HU_PI_Item (M_HU_PI_Item_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
							+ "M_HU_PI_Version_ID, ItemType) "
							+ "VALUES (" + huPiItemId + ", " + Env.getAD_Client_ID(Env.getCtx()) + ", " + Env.getAD_Org_ID(Env.getCtx())
							+ ", 'Y', now(), 100, now(), 100, " + huPiVersionId + ", 'MI')",
					ITrx.TRXNAME_None);

			final int piipId = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_HU_PI_Item_Product_seq')");
			DB.executeUpdateAndThrowExceptionOnFail(
					"INSERT INTO M_HU_PI_Item_Product (M_HU_PI_Item_Product_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
							+ "M_HU_PI_Item_ID, M_Product_ID, Qty, IsDefaultForProduct, IsAllowAnyProduct, ValidFrom) "
							+ "VALUES (" + piipId + ", " + Env.getAD_Client_ID(Env.getCtx()) + ", " + Env.getAD_Org_ID(Env.getCtx())
							+ ", 'Y', now(), 100, now(), 100, "
							+ huPiItemId + ", " + productId + ", " + qty + ", '" + isDefault + "', 'N', '1970-01-01')",
					ITrx.TRXNAME_None);
		});
	}

	@When("the Package Licensing InOut Report is executed with C_Country_ID for country code {string} and date range {string} to {string}")
	public void executeReport(@NonNull final String countryCode, @NonNull final String dateFrom, @NonNull final String dateTo) throws SQLException
	{
		executeReport(countryCode, dateFrom, dateTo, "Y");
	}

	@When("the Package Licensing InOut Report is executed with C_Country_ID for country code {string} and date range {string} to {string} and IsIncludeAllProducts {string}")
	public void executeReport(@NonNull final String countryCode, @NonNull final String dateFrom, @NonNull final String dateTo, @NonNull final String isIncludeAllProducts) throws SQLException
	{
		reportResults.clear();

		final int countryId = getCountryIdByCode(countryCode);
		final String sql = "SELECT * FROM report.Package_Licensing_InOut_Report(?, ?, ?, ?)";
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setTimestamp(1, Timestamp.valueOf(dateFrom + " 00:00:00"));
			pstmt.setTimestamp(2, Timestamp.valueOf(dateTo + " 23:59:59"));
			pstmt.setInt(3, countryId);
			pstmt.setString(4, isIncludeAllProducts);

			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					final Map<String, String> resultRow = new LinkedHashMap<>();
					resultRow.put("DocumentNo", rs.getString("DocumentNo"));
					resultRow.put("MovementDate", rs.getString("MovementDate"));
					resultRow.put("CountryCode", rs.getString("CountryCode"));
					resultRow.put("ProductValue", rs.getString("ProductValue"));
					resultRow.put("ProductName", rs.getString("ProductName"));
					resultRow.put("TotalSalesQty", bigDecimalToString(rs.getBigDecimal("TotalSalesQty")));
					resultRow.put("PurchaseQty", bigDecimalToString(rs.getBigDecimal("PurchaseQty")));
					resultRow.put("ForeignSalesQty", bigDecimalToString(rs.getBigDecimal("ForeignSalesQty")));
					resultRow.put("UOMSymbol", rs.getString("UOMSymbol"));
					resultRow.put("Weight", bigDecimalToString(rs.getBigDecimal("Weight")));
					resultRow.put("ProductGroup", rs.getString("ProductGroup"));
					resultRow.put("MaterialType", rs.getString("MaterialType"));
					resultRow.put("SmallPackagingMaterial", rs.getString("SmallPackagingMaterial"));
					resultRow.put("SmallPackagingWeight", bigDecimalToString(rs.getBigDecimal("SmallPackagingWeight")));
					resultRow.put("OuterPackagingMaterial", rs.getString("OuterPackagingMaterial"));
					resultRow.put("OuterPackagingWeight", bigDecimalToString(rs.getBigDecimal("OuterPackagingWeight")));
					resultRow.put("PackagingInstructionFactor", bigDecimalToString(rs.getBigDecimal("PackagingInstructionFactor")));
					resultRow.put("VendorName", rs.getString("VendorName"));
					resultRow.put("VendorCountryCode", rs.getString("VendorCountryCode"));
					resultRow.put("IsVendorPackageLicensingExempt", rs.getString("IsVendorPackageLicensingExempt"));
					reportResults.add(resultRow);
				}
			}
		}
	}

	/**
	 * Verifies report rows matched by DocumentNo.
	 * Empty cells assert that the actual value is null.
	 *
	 * <pre>
	 * | DocumentNo | MovementQty | PurchaseQty | ForeignSalesQty | MaterialType | PackagingInstructionFactor |
	 * </pre>
	 */
	@Then("the Package Licensing InOut Report result contains:")
	public void verifyReportResults(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> expectedRows = dataTable.asMaps();
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> expectedRow : expectedRows)
		{
			final String expectedDocNo = expectedRow.get("DocumentNo");

			final Map<String, String> actualRow = reportResults.stream()
					.filter(r -> expectedDocNo.equals(r.get("DocumentNo")))
					.findFirst()
					.orElse(null);

			softly.assertThat(actualRow)
					.as("Report row for DocumentNo=" + expectedDocNo)
					.isNotNull();

			if (actualRow == null)
			{
				continue;
			}

			for (final Map.Entry<String, String> entry : expectedRow.entrySet())
			{
				final String col = entry.getKey();
				if ("DocumentNo".equals(col))
				{
					continue; // already matched
				}

				final String expectedValue = entry.getValue();
				final String actualValue = actualRow.get(col);

				if (expectedValue == null || expectedValue.isEmpty())
				{
					softly.assertThat(actualValue)
							.as(col + " for DocumentNo=" + expectedDocNo + " should be null/empty")
							.isNullOrEmpty();
				}
				else if (isNumericColumn(col))
				{
					softly.assertThat(actualValue)
							.as(col + " for DocumentNo=" + expectedDocNo + " should not be null")
							.isNotNull();
					if (actualValue != null)
					{
						softly.assertThat(new BigDecimal(actualValue))
								.as(col + " for DocumentNo=" + expectedDocNo)
								.isEqualByComparingTo(new BigDecimal(expectedValue));
					}
				}
				else
				{
					softly.assertThat(actualValue)
							.as(col + " for DocumentNo=" + expectedDocNo)
							.isEqualTo(expectedValue);
				}
			}
		}

		softly.assertAll();
	}

	@Then("the Package Licensing InOut Report result does not contain DocumentNo {string}")
	public void verifyDocumentNotInResults(@NonNull final String documentNo)
	{
		final boolean found = reportResults.stream()
				.anyMatch(r -> documentNo.equals(r.get("DocumentNo")));
		org.assertj.core.api.Assertions.assertThat(found)
				.as("DocumentNo=" + documentNo + " should NOT be in report results")
				.isFalse();
	}

	/**
	 * Asserts that a specific column is not null for a given DocumentNo in the detail report results.
	 * Use this to verify that vendor info is populated on purchase receipts without knowing the exact value.
	 *
	 * @param columnName the report column to check (e.g. "VendorName")
	 * @param documentNo the DocumentNo of the row to check
	 */
	@Then("the Package Licensing InOut Report result has non-null {string} for DocumentNo {string}")
	public void verifyNonNullColumn(@NonNull final String columnName, @NonNull final String documentNo)
	{
		final Map<String, String> actualRow = reportResults.stream()
				.filter(r -> documentNo.equals(r.get("DocumentNo")))
				.findFirst()
				.orElse(null);

		org.assertj.core.api.Assertions.assertThat(actualRow)
				.as("Report row for DocumentNo=" + documentNo)
				.isNotNull();

		if (actualRow != null)
		{
			org.assertj.core.api.Assertions.assertThat(actualRow.get(columnName))
					.as(columnName + " for DocumentNo=" + documentNo + " should not be null")
					.isNotNull();
		}
	}

	/**
	 * Sets up packaging licensing exemption flags on BPartners previously created via the InOut test data step.
	 * The BPartner is identified by SharedBPartnerKey (must have been used in a prior InOut setup step).
	 *
	 * <pre>
	 * | SharedBPartnerKey | IsPackageLicensingExempt | PackageLicensingExemptFrom | PackageLicensingExemptTo |
	 * </pre>
	 *
	 * <ul>
	 *   <li>{@code SharedBPartnerKey} — key used in the InOut test data step to identify the BPartner</li>
	 *   <li>{@code IsPackageLicensingExempt} — 'Y' or 'N'</li>
	 *   <li>{@code PackageLicensingExemptFrom} — optional start date (yyyy-MM-dd)</li>
	 *   <li>{@code PackageLicensingExemptTo} — optional end date (yyyy-MM-dd)</li>
	 * </ul>
	 */
	@And("package licensing BPartner exemption is set up:")
	public void setupBPartnerExemption(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String key = row.getAsString("SharedBPartnerKey");
			final Integer bpartnerId = sharedBPartners.get(key);
			org.assertj.core.api.Assertions.assertThat(bpartnerId)
					.as("SharedBPartnerKey=" + key + " must have been used in a prior InOut setup step")
					.isNotNull();

			final String isExempt = row.getAsString("IsPackageLicensingExempt");
			final String exemptFrom = row.getAsOptionalString("PackageLicensingExemptFrom").orElse(null);
			final String exemptTo = row.getAsOptionalString("PackageLicensingExemptTo").orElse(null);

			final StringBuilder sql = new StringBuilder("UPDATE C_BPartner SET IsPackageLicensingExempt=")
					.append(sqlQuote(isExempt));
			if (exemptFrom != null)
			{
				sql.append(", PackageLicensingExemptFrom=").append(sqlQuote(exemptFrom)).append("::date");
			}
			if (exemptTo != null)
			{
				sql.append(", PackageLicensingExemptTo=").append(sqlQuote(exemptTo)).append("::date");
			}
			sql.append(" WHERE C_BPartner_ID=").append(bpartnerId);

			DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_None);
		});
	}

	/**
	 * Executes {@code report.Package_Licensing_Product_Report()} and stores results for verification.
	 * The product report aggregates the detail report to product + vendor level.
	 *
	 * @param countryCode ISO country code (e.g. "AT")
	 * @param dateFrom    start of movement date range (yyyy-MM-dd)
	 * @param dateTo      end of movement date range (yyyy-MM-dd)
	 */
	@When("the Package Licensing Product Report is executed with C_Country_ID for country code {string} and date range {string} to {string}")
	public void executeProductReport(@NonNull final String countryCode, @NonNull final String dateFrom, @NonNull final String dateTo) throws SQLException
	{
		productReportResults.clear();

		final int countryId = getCountryIdByCode(countryCode);
		final String sql = "SELECT * FROM report.Package_Licensing_Product_Report(?, ?, ?, ?)";
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setTimestamp(1, Timestamp.valueOf(dateFrom + " 00:00:00"));
			pstmt.setTimestamp(2, Timestamp.valueOf(dateTo + " 23:59:59"));
			pstmt.setInt(3, countryId);
			pstmt.setString(4, "Y"); // IsIncludeAllProducts

			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					final Map<String, String> resultRow = new LinkedHashMap<>();
					resultRow.put("ProductValue", rs.getString("ProductValue"));
					resultRow.put("ProductName", rs.getString("ProductName"));
					resultRow.put("VendorName", rs.getString("VendorName"));
					resultRow.put("VendorCountryCode", rs.getString("VendorCountryCode"));
					resultRow.put("PurchaseQty", bigDecimalToString(rs.getBigDecimal("PurchaseQty")));
					resultRow.put("ForeignSalesQty", bigDecimalToString(rs.getBigDecimal("ForeignSalesQty")));
					resultRow.put("TotalSalesQty", bigDecimalToString(rs.getBigDecimal("TotalSalesQty")));
					resultRow.put("UOMSymbol", rs.getString("UOMSymbol"));
					resultRow.put("ProductGroup", rs.getString("ProductGroup"));
					resultRow.put("MaterialType", rs.getString("MaterialType"));
					resultRow.put("SmallPackagingMaterial", rs.getString("SmallPackagingMaterial"));
					resultRow.put("SmallPackagingWeight", bigDecimalToString(rs.getBigDecimal("SmallPackagingWeight")));
					resultRow.put("OuterPackagingMaterial", rs.getString("OuterPackagingMaterial"));
					resultRow.put("OuterPackagingWeight", bigDecimalToString(rs.getBigDecimal("OuterPackagingWeight")));
					resultRow.put("PackagingInstructionFactor", bigDecimalToString(rs.getBigDecimal("PackagingInstructionFactor")));
					resultRow.put("IsVendorPackageLicensingExempt", rs.getString("IsVendorPackageLicensingExempt"));
					productReportResults.add(resultRow);
				}
			}
		}
	}

	/**
	 * Verifies Product Report rows, matched by {@code ProductName} + {@code VendorCountryCode} composite key.
	 * Empty cells assert that the actual value is null.
	 *
	 * <pre>
	 * | ProductName | VendorCountryCode | PurchaseQty | ForeignSalesQty | TotalSalesQty | IsVendorPackageLicensingExempt |
	 * </pre>
	 */
	@Then("the Package Licensing Product Report result contains:")
	public void verifyProductReportResults(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> expectedRows = dataTable.asMaps();
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> expectedRow : expectedRows)
		{
			final String expectedProductName = expectedRow.get("ProductName");
			final String expectedVendorCountryCode = expectedRow.get("VendorCountryCode");
			final String matchKey = "ProductName=" + expectedProductName + ", VendorCountryCode=" + expectedVendorCountryCode;

			final Map<String, String> actualRow = productReportResults.stream()
					.filter(r -> expectedProductName.equals(r.get("ProductName"))
							&& (expectedVendorCountryCode == null || expectedVendorCountryCode.isEmpty()
							? r.get("VendorCountryCode") == null
							: expectedVendorCountryCode.equals(r.get("VendorCountryCode"))))
					.findFirst()
					.orElse(null);

			softly.assertThat(actualRow)
					.as("Product Report row for " + matchKey)
					.isNotNull();

			if (actualRow == null)
			{
				continue;
			}

			for (final Map.Entry<String, String> entry : expectedRow.entrySet())
			{
				final String col = entry.getKey();
				if ("ProductName".equals(col) || "VendorCountryCode".equals(col))
				{
					continue; // already matched
				}

				final String expectedValue = entry.getValue();
				final String actualValue = actualRow.get(col);

				if (expectedValue == null || expectedValue.isEmpty())
				{
					softly.assertThat(actualValue)
							.as(col + " for " + matchKey + " should be null/empty")
							.isNullOrEmpty();
				}
				else if (isNumericColumn(col))
				{
					softly.assertThat(actualValue)
							.as(col + " for " + matchKey + " should not be null")
							.isNotNull();
					if (actualValue != null)
					{
						softly.assertThat(new BigDecimal(actualValue))
								.as(col + " for " + matchKey)
								.isEqualByComparingTo(new BigDecimal(expectedValue));
					}
				}
				else
				{
					softly.assertThat(actualValue)
							.as(col + " for " + matchKey)
							.isEqualTo(expectedValue);
				}
			}
		}

		softly.assertAll();
	}

	/**
	 * Executes {@code report.Package_Licensing_InOut_Summary_Report()} and stores results for verification.
	 * The summary report returns a pivot table with ProductGroup x PackagingType x MaterialGroup columns.
	 * The first row is a header row (ProductGroup='---HEADER---') mapping material names to column positions.
	 *
	 * @param countryCode                  ISO country code (e.g. "AT")
	 * @param dateFrom                     start of movement date range (yyyy-MM-dd)
	 * @param dateTo                       end of movement date range (yyyy-MM-dd)
	 * @param isExcludeDomesticPurchases   "Y" to exclude domestic + exempt vendors, "N" to include all
	 */
	@When("the Package Licensing Summary Report is executed with C_Country_ID for country code {string} and date range {string} to {string} and IsExcludeDomesticPurchases {string}")
	public void executeSummaryReport(
			@NonNull final String countryCode,
			@NonNull final String dateFrom,
			@NonNull final String dateTo,
			@NonNull final String isExcludeDomesticPurchases) throws SQLException
	{
		summaryReportResults.clear();
		summaryMaterialColumnMapping.clear();

		final int countryId = getCountryIdByCode(countryCode);
		final String sql = "SELECT * FROM report.Package_Licensing_InOut_Summary_Report(?, ?, ?, ?)";
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setTimestamp(1, Timestamp.valueOf(dateFrom + " 00:00:00"));
			pstmt.setTimestamp(2, Timestamp.valueOf(dateTo + " 23:59:59"));
			pstmt.setInt(3, countryId);
			pstmt.setString(4, isExcludeDomesticPurchases);

			try (final ResultSet rs = pstmt.executeQuery())
			{
				boolean headerProcessed = false;
				while (rs.next())
				{
					final String productGroup = rs.getString("ProductGroup");

					// The first row is a header mapping material names to column positions
					if ("---HEADER---".equals(productGroup) && !headerProcessed)
					{
						for (int i = 1; i <= 16; i++)
						{
							final String colName = "Material_M" + i + "_Weight";
							final String materialName = rs.getString(colName);
							if (materialName != null && !materialName.isEmpty())
							{
								summaryMaterialColumnMapping.put(materialName, colName);
							}
						}
						headerProcessed = true;
						continue;
					}

					final Map<String, String> resultRow = new LinkedHashMap<>();
					resultRow.put("ProductGroup", productGroup);
					resultRow.put("PackagingType", rs.getString("PackagingType"));
					for (int i = 1; i <= 16; i++)
					{
						final String colName = "Material_M" + i + "_Weight";
						resultRow.put(colName, rs.getString(colName));
					}
					summaryReportResults.add(resultRow);
				}
			}
		}
	}

	/**
	 * Verifies Summary Report (Mengenmeldung) rows, matched by {@code ProductGroup} + {@code PackagingType}.
	 * The {@code MaterialName} is resolved to the correct dynamic column via the header row mapping.
	 * {@code Weight} is compared as BigDecimal.
	 *
	 * <pre>
	 * | ProductGroup | PackagingType | MaterialName | Weight |
	 * </pre>
	 */
	@Then("the Package Licensing Summary Report result contains:")
	public void verifySummaryReportResults(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> expectedRows = dataTable.asMaps();
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> expectedRow : expectedRows)
		{
			final String productGroup = expectedRow.get("ProductGroup");
			final String packagingType = expectedRow.get("PackagingType");
			final String materialName = expectedRow.get("MaterialName");
			final String expectedWeight = expectedRow.get("Weight");
			final String matchKey = "ProductGroup=" + productGroup + ", PackagingType=" + packagingType + ", Material=" + materialName;

			// Resolve material name to column via header mapping
			final String colName = summaryMaterialColumnMapping.get(materialName);
			softly.assertThat(colName)
					.as("Material column mapping for " + materialName + " (available: " + summaryMaterialColumnMapping.keySet() + ")")
					.isNotNull();

			if (colName == null)
			{
				continue;
			}

			// Find the data row by ProductGroup + PackagingType
			final Map<String, String> actualRow = summaryReportResults.stream()
					.filter(r -> productGroup.equals(r.get("ProductGroup")) && packagingType.equals(r.get("PackagingType")))
					.findFirst()
					.orElse(null);

			softly.assertThat(actualRow)
					.as("Summary row for " + matchKey)
					.isNotNull();

			if (actualRow == null)
			{
				continue;
			}

			final String actualWeight = actualRow.get(colName);
			if (expectedWeight == null || expectedWeight.isEmpty())
			{
				softly.assertThat(actualWeight)
						.as("Weight for " + matchKey)
						.isNullOrEmpty();
			}
			else
			{
				softly.assertThat(actualWeight)
						.as("Weight for " + matchKey + " should not be null")
						.isNotNull();
				if (actualWeight != null)
				{
					softly.assertThat(new BigDecimal(actualWeight))
							.as("Weight for " + matchKey)
							.isEqualByComparingTo(new BigDecimal(expectedWeight));
				}
			}
		}

		softly.assertAll();
	}

	// --- Helper methods ---

	private static boolean isNumericColumn(@NonNull final String col)
	{
		return "TotalSalesQty".equals(col) || "PurchaseQty".equals(col) || "ForeignSalesQty".equals(col)
				|| "Weight".equals(col) || "SmallPackagingWeight".equals(col) || "OuterPackagingWeight".equals(col)
				|| "PackagingInstructionFactor".equals(col);
	}

	private int insertPackageLicensingMaterialGroup(final int countryId, @NonNull final String name)
	{
		final int existingId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT M_PackageLicensing_MaterialGroup_ID FROM M_PackageLicensing_MaterialGroup WHERE Name=" + sqlQuote(name) + " AND C_Country_ID=" + countryId + " AND IsActive='Y' LIMIT 1");
		if (existingId > 0)
		{
			return existingId;
		}
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
		final int existingId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT M_PackageLicensing_ProductGroup_ID FROM M_PackageLicensing_ProductGroup WHERE Name=" + sqlQuote(name) + " AND C_Country_ID=" + countryId + " AND IsActive='Y' LIMIT 1");
		if (existingId > 0)
		{
			return existingId;
		}
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
		final int existingId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT M_Product_PackageLicensing_ProductGroup_ID FROM M_Product_PackageLicensing_ProductGroup WHERE M_Product_ID=" + productId + " AND M_PackageLicensing_ProductGroup_ID=" + productGroupId + " AND IsActive='Y' LIMIT 1");
		if (existingId > 0)
		{
			return;
		}
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
		final int existingId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT M_Product_SmallPackagingMaterial_ID FROM M_Product_SmallPackagingMaterial WHERE M_Product_ID=" + productId + " AND M_PackageLicensing_MaterialGroup_ID=" + materialGroupId + " AND IsActive='Y' LIMIT 1");
		if (existingId > 0)
		{
			return;
		}
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
		final int existingId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT M_Product_OuterPackagingMaterial_ID FROM M_Product_OuterPackagingMaterial WHERE M_Product_ID=" + productId + " AND M_PackageLicensing_MaterialGroup_ID=" + materialGroupId + " AND IsActive='Y' LIMIT 1");
		if (existingId > 0)
		{
			return;
		}
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_PRODUCT_OUTERPACKAGINGMATERIAL_SEQ')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_Product_OuterPackagingMaterial "
						+ "(M_Product_OuterPackagingMaterial_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, M_Product_ID, M_PackageLicensing_MaterialGroup_ID) "
						+ "VALUES (" + id + ", " + Env.getAD_Client_ID(Env.getCtx()) + ", " + Env.getAD_Org_ID(Env.getCtx()) + ", 'Y', now(), 100, now(), 100, "
						+ productId + ", " + materialGroupId + ")",
				ITrx.TRXNAME_None);
	}

	private static int getCountryIdByCode(@NonNull final String countryCode)
	{
		return DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT C_Country_ID FROM C_Country WHERE CountryCode=" + sqlQuote(countryCode));
	}

	private static int createLocation(final int countryId)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('C_Location_seq')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO C_Location (C_Location_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, C_Country_ID) "
						+ "VALUES (" + id + ", 0, 0, 'Y', now(), 100, now(), 100, " + countryId + ")",
				ITrx.TRXNAME_None);
		return id;
	}

	private static int createWarehouse(final int clientId, final int orgId, @NonNull final String value, final int locationId, final int bpartnerId, final int bpartnerLocationId)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_Warehouse_seq')");
		final String uniqueValue = value + "_" + id;
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_Warehouse (M_Warehouse_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
						+ "Value, Name, Separator, C_Location_ID, C_BPartner_ID, C_BPartner_Location_ID) "
						+ "VALUES (" + id + ", " + clientId + ", " + orgId + ", 'Y', now(), 100, now(), 100, "
						+ sqlQuote(uniqueValue) + ", " + sqlQuote(uniqueValue) + ", '*', " + locationId + ", " + bpartnerId + ", " + bpartnerLocationId + ")",
				ITrx.TRXNAME_None);
		return id;
	}

	private static int createLocator(final int clientId, final int orgId, final int warehouseId)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_Locator_seq')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_Locator (M_Locator_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
						+ "M_Warehouse_ID, Value, X, Y, Z, IsDefault, PriorityNo) "
						+ "VALUES (" + id + ", " + clientId + ", " + orgId + ", 'Y', now(), 100, now(), 100, "
						+ warehouseId + ", 'Default', '0', '0', '0', 'Y', 50)",
				ITrx.TRXNAME_None);
		return id;
	}

	private static int createBPartner(final int clientId, final int orgId, @NonNull final String value)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('C_BPartner_seq')");
		final String uniqueValue = value + "_" + id;
		final int bpGroupId = DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT C_BP_Group_ID FROM C_BP_Group WHERE AD_Client_ID=" + clientId + " AND IsActive='Y' ORDER BY IsDefault DESC, C_BP_Group_ID LIMIT 1");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO C_BPartner (C_BPartner_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
						+ "Value, Name, IsCustomer, IsVendor, C_BP_Group_ID) "
						+ "VALUES (" + id + ", " + clientId + ", " + orgId + ", 'Y', now(), 100, now(), 100, "
						+ sqlQuote(uniqueValue) + ", " + sqlQuote(uniqueValue) + ", 'Y', 'Y', " + bpGroupId + ")",
				ITrx.TRXNAME_None);
		return id;
	}

	private static int createBPartnerLocation(final int clientId, final int orgId, final int bpartnerId, final int locationId)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('C_BPartner_Location_seq')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO C_BPartner_Location (C_BPartner_Location_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
						+ "C_BPartner_ID, C_Location_ID, Name, IsShipTo, IsBillTo, IsRemitTo) "
						+ "VALUES (" + id + ", " + clientId + ", " + orgId + ", 'Y', now(), 100, now(), 100, "
						+ bpartnerId + ", " + locationId + ", 'Default', 'Y', 'Y', 'N')",
				ITrx.TRXNAME_None);
		return id;
	}

	private static int getDocTypeId(final int clientId, final int orgId, @NonNull final String docBaseType)
	{
		return DB.getSQLValueEx(ITrx.TRXNAME_None,
				"SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType=" + sqlQuote(docBaseType)
						+ " AND AD_Client_ID=" + clientId
						+ " AND IsActive='Y' ORDER BY IsDefault DESC, C_DocType_ID LIMIT 1");
	}

	private static int createMInOut(
			final int clientId, final int orgId, @NonNull final String documentNo,
			@NonNull final String movementDate, @NonNull final String movementType,
			@NonNull final String isSOTrx, @NonNull final String docStatus,
			final int warehouseId, final int bpartnerId, final int bpartnerLocationId,
			final int docTypeId)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_InOut_seq')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_InOut (M_InOut_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
						+ "DocumentNo, C_DocType_ID, MovementDate, DateAcct, "
						+ "MovementType, IsSOTrx, DocStatus, DocAction, "
						+ "M_Warehouse_ID, C_BPartner_ID, C_BPartner_Location_ID, "
						+ "Posted, SendEMail, DeliveryRule, DeliveryViaRule, FreightCostRule, PriorityRule, Processed, IsInDispute) "
						+ "VALUES (" + id + ", " + clientId + ", " + orgId + ", 'Y', now(), 100, now(), 100, "
						+ sqlQuote(documentNo) + ", " + docTypeId + ", "
						+ sqlQuote(movementDate) + "::date, " + sqlQuote(movementDate) + "::date, "
						+ sqlQuote(movementType) + ", " + sqlQuote(isSOTrx) + ", " + sqlQuote(docStatus) + ", 'CO', "
						+ warehouseId + ", " + bpartnerId + ", " + bpartnerLocationId + ", "
						+ "'N', 'N', 'A', 'P', 'I', '5', " + ("CO".equals(docStatus) || "CL".equals(docStatus) ? "'Y'" : "'N'") + ", 'N')",
				ITrx.TRXNAME_None);
		return id;
	}

	private static void createMInOutLine(
			final int clientId, final int orgId,
			final int inoutId, final int productId, @NonNull final BigDecimal movementQty,
			final int uomId, final int locatorId)
	{
		final int id = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT nextval('M_InOutLine_seq')");
		DB.executeUpdateAndThrowExceptionOnFail(
				"INSERT INTO M_InOutLine (M_InOutLine_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
						+ "M_InOut_ID, Line, M_Product_ID, MovementQty, QtyEntered, C_UOM_ID, M_Locator_ID, IsInvoiced) "
						+ "VALUES (" + id + ", " + clientId + ", " + orgId + ", 'Y', now(), 100, now(), 100, "
						+ inoutId + ", 10, " + productId + ", " + movementQty + ", " + movementQty + ", " + uomId + ", " + locatorId + ", 'N')",
				ITrx.TRXNAME_None);
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
