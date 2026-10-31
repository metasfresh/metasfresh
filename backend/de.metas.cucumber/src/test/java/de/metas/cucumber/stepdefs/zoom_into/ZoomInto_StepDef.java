package de.metas.cucumber.stepdefs.zoom_into;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.lang.SOTrx;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.PO;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ZoomInto_StepDef
{
	@NonNull private final ZoomIntoTestHelper helper;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;

	private List<String> tablesWithMissingWindows = ImmutableList.of();
	private AdWindowId lastResolvedWindowId;
	private ImmutableList<ReferenceFieldInfo> lastFieldScanResults;

	/**
	 * Iterates every table listed in the {@code ad_table_windows_v} view and attempts to resolve
	 * a zoom-into window via {@link de.metas.document.references.zoom_into.RecordWindowFinder}.
	 * Tables listed in the DataTable are excluded from the check.
	 * Stores the list of tables that failed to resolve in {@code tablesWithMissingWindows}.
	 *
	 * @param dataTable single-column table with header {@code TableName}; tables to skip
	 */
	@When("RecordWindowFinder is asked to resolve a window for each table listed in ad_table_windows_v, excluding:")
	public void resolve_window_for_each_table_excluding(@NonNull final DataTable dataTable)
	{
		final ImmutableSet<String> excludedTables = DataTableRows.of(dataTable)
				.stream()
				.map(row -> row.getAsString("TableName"))
				.collect(ImmutableSet.toImmutableSet());

		tablesWithMissingWindows = helper.findTablesWithMissingZoomIntoWindows(excludedTables);
	}

	/**
	 * Asserts that no tables were left unresolved by the preceding
	 * {@code RecordWindowFinder is asked to resolve a window for each table} step.
	 */
	@Then("every table in ad_table_windows_v resolves to a non-null window")
	public void every_table_resolves_to_non_null_window()
	{
		assertThat(tablesWithMissingWindows)
				.as("Tables in ad_table_windows_v that failed to resolve to a window via RecordWindowFinder")
				.isEmpty();
	}

	/**
	 * For each row in the DataTable, resolves a zoom-into window for the given table and SOTrx
	 * direction, then asserts the resolved window name matches the expected value.
	 * Uses soft assertions so all rows are validated before failing.
	 *
	 * @param dataTable columns: {@code TableName} (AD table name), {@code IsSOTrx} (true/false),
	 *                  {@code ExpectedWindowName} (expected window name in en_US)
	 */
	@Then("zoom-into for the following tables resolves to the expected windows by SOTrx:")
	public void zoom_into_resolves_to_expected_windows(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(dataTable).forEach(row -> {
			final String tableName = row.getAsString("TableName");
			final SOTrx soTrx = SOTrx.ofBoolean(row.getAsBoolean("IsSOTrx"));
			final String expectedWindowName = row.getAsString("ExpectedWindowName");

			final Optional<AdWindowId> windowId = helper.resolveZoomIntoWindow(tableName, soTrx);

			softly.assertThat(windowId)
					.as("Window for table=" + tableName + ", IsSOTrx=" + soTrx)
					.isPresent();

			windowId.ifPresent(id -> {
				final String actualWindowName = helper.getWindowName(id);
				softly.assertThat(actualWindowName)
						.as("Window name for table=" + tableName + ", IsSOTrx=" + soTrx)
						.isEqualTo(expectedWindowName);
			});
		});

		softly.assertAll();
	}

	/**
	 * Resolves the zoom-into window for a specific C_Order record using
	 * {@link de.metas.document.references.zoom_into.RecordWindowFinder#newInstance(String, int)}.
	 * Stores the resolved {@link AdWindowId} in {@code lastResolvedWindowId}.
	 *
	 * @param orderIdentifier the StepDefData identifier of the C_Order to look up
	 */
	@When("RecordWindowFinder resolves zoom-into for the C_Order identified by {string}")
	public void resolve_zoom_into_for_order(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final Optional<AdWindowId> windowId = helper.resolveZoomIntoWindowForRecord(
				I_C_Order.Table_Name,
				order.getC_Order_ID());

		lastResolvedWindowId = windowId.orElse(null);
	}

	/**
	 * Asserts that the window resolved by the preceding {@code RecordWindowFinder resolves zoom-into}
	 * step matches the given expected window name.
	 *
	 * @param expectedWindowName the expected window name in en_US (e.g. "Sales Order")
	 */
	@Then("the resolved window is {string}")
	public void the_resolved_window_is(@NonNull final String expectedWindowName)
	{
		assertThat(lastResolvedWindowId)
				.as("Resolved window ID should not be null")
				.isNotNull();

		final String actualWindowName = helper.getWindowName(lastResolvedWindowId);
		assertThat(actualWindowName)
				.as("Resolved window name")
				.isEqualTo(expectedWindowName);
	}

	// --- Field-level reference scan steps ---

	/**
	 * Scans all reference fields (Table, TableDir, Search display types) of the given table
	 * and resolves the zoom-to window for each target table via RecordWindowFinder.
	 * Columns listed in the DataTable are excluded from the scan.
	 * Results are stored in {@code lastFieldScanResults} for assertion by a subsequent {@code @Then} step.
	 *
	 * @param tableName the AD table name to scan (e.g. "C_Order")
	 * @param dataTable single-column table with header {@code ColumnName}; columns to skip
	 */
	@When("all reference fields of table {string} are scanned for zoom-to targets, excluding columns:")
	public void scan_reference_fields_with_exclusions(@NonNull final String tableName, @NonNull final DataTable dataTable)
	{
		final ImmutableSet<String> excludedColumns = DataTableRows.of(dataTable)
				.stream()
				.map(row -> row.getAsString("ColumnName"))
				.collect(ImmutableSet.toImmutableSet());

		lastFieldScanResults = helper.scanReferenceFieldsForTable(tableName, excludedColumns);
	}

	/**
	 * Scans all reference fields of the given table without any exclusions.
	 * Results are stored in {@code lastFieldScanResults}.
	 *
	 * @param tableName the AD table name to scan (e.g. "C_Order")
	 */
	@When("all reference fields of table {string} are scanned for zoom-to targets")
	public void scan_reference_fields(@NonNull final String tableName)
	{
		lastFieldScanResults = helper.scanReferenceFieldsForTable(tableName, ImmutableSet.of());
	}

	/**
	 * Asserts that every {@link ReferenceFieldInfo} in {@code lastFieldScanResults} has a
	 * non-null {@code resolvedWindowId}. Uses soft assertions to report all failures at once.
	 */
	@Then("every reference field resolves to a valid zoom-to window")
	public void every_reference_field_resolves_to_window()
	{
		assertThat(lastFieldScanResults)
				.as("Field scan results should not be null")
				.isNotNull();

		final SoftAssertions softly = new SoftAssertions();
		for (final ReferenceFieldInfo info : lastFieldScanResults)
		{
			softly.assertThat(info.getResolvedWindowId())
					.as("Zoom-to window for " + info.getSourceTableName() + "." + info.getSourceColumnName()
							+ " -> " + info.getTargetTableName())
					.isNotNull();
		}
		softly.assertAll();
	}

	// --- Record-level verification steps ---

	/**
	 * For the C_Order identified by the given StepDefData identifier, iterates all non-null FK
	 * reference fields and verifies each resolves to a zoom-to window with the target record
	 * present. No columns are excluded. Results stored in {@code lastFieldScanResults}.
	 *
	 * @param orderIdentifier the StepDefData identifier of the C_Order
	 */
	@When("all reference fields of the C_Order identified by {string} are verified for zoom-to")
	public void verify_zoom_to_for_order(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final PO po = InterfaceWrapperHelper.getPO(order);
		lastFieldScanResults = helper.verifyZoomToForRecord(po, ImmutableSet.of());
	}

	/**
	 * Same as {@link #verify_zoom_to_for_order} but excludes the columns listed in the DataTable.
	 *
	 * @param orderIdentifier the StepDefData identifier of the C_Order
	 * @param dataTable single-column table with header {@code ColumnName}; columns to skip
	 */
	@When("all reference fields of the C_Order identified by {string} are verified for zoom-to, excluding columns:")
	public void verify_zoom_to_for_order_with_exclusions(@NonNull final String orderIdentifier, @NonNull final DataTable dataTable)
	{
		final ImmutableSet<String> excludedColumns = DataTableRows.of(dataTable)
				.stream()
				.map(row -> row.getAsString("ColumnName"))
				.collect(ImmutableSet.toImmutableSet());

		final I_C_Order order = orderTable.get(orderIdentifier);
		final PO po = InterfaceWrapperHelper.getPO(order);
		lastFieldScanResults = helper.verifyZoomToForRecord(po, excludedColumns);
	}

	/**
	 * For the C_BPartner identified by the given StepDefData identifier, iterates all non-null FK
	 * reference fields and verifies each resolves to a zoom-to window with the target record
	 * present. No columns are excluded. Results stored in {@code lastFieldScanResults}.
	 *
	 * @param bpartnerIdentifier the StepDefData identifier of the C_BPartner
	 */
	@When("all reference fields of the C_BPartner identified by {string} are verified for zoom-to")
	public void verify_zoom_to_for_bpartner(@NonNull final String bpartnerIdentifier)
	{
		final I_C_BPartner bpartner = bpartnerTable.get(bpartnerIdentifier);
		final PO po = InterfaceWrapperHelper.getPO(bpartner);
		lastFieldScanResults = helper.verifyZoomToForRecord(po, ImmutableSet.of());
	}

	/**
	 * Same as {@link #verify_zoom_to_for_bpartner} but excludes the columns listed in the DataTable.
	 *
	 * @param bpartnerIdentifier the StepDefData identifier of the C_BPartner
	 * @param dataTable single-column table with header {@code ColumnName}; columns to skip
	 */
	@When("all reference fields of the C_BPartner identified by {string} are verified for zoom-to, excluding columns:")
	public void verify_zoom_to_for_bpartner_with_exclusions(@NonNull final String bpartnerIdentifier, @NonNull final DataTable dataTable)
	{
		final ImmutableSet<String> excludedColumns = DataTableRows.of(dataTable)
				.stream()
				.map(row -> row.getAsString("ColumnName"))
				.collect(ImmutableSet.toImmutableSet());

		final I_C_BPartner bpartner = bpartnerTable.get(bpartnerIdentifier);
		final PO po = InterfaceWrapperHelper.getPO(bpartner);
		lastFieldScanResults = helper.verifyZoomToForRecord(po, excludedColumns);
	}

	/**
	 * Asserts that every {@link ReferenceFieldInfo} in {@code lastFieldScanResults} from a
	 * record-level verification has a non-null {@code resolvedWindowId}. This confirms that
	 * RecordWindowFinder can resolve a window for each non-null FK value on the record,
	 * with the target record present in the window.
	 * Uses soft assertions to report all failures at once.
	 */
	@Then("every non-null reference field resolves to a valid zoom-to window for the record")
	public void every_non_null_reference_field_resolves_to_window()
	{
		assertThat(lastFieldScanResults)
				.as("Record verification results should not be null")
				.isNotNull();

		final SoftAssertions softly = new SoftAssertions();
		for (final ReferenceFieldInfo info : lastFieldScanResults)
		{
			softly.assertThat(info.getResolvedWindowId())
					.as("Zoom-to window for record field " + info.getSourceTableName() + "." + info.getSourceColumnName()
							+ " -> " + info.getTargetTableName())
					.isNotNull();
		}
		softly.assertAll();
	}
}
