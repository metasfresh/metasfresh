package de.metas.cucumber.stepdefs.zoom_into;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.lang.SOTrx;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Order;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ZoomInto_StepDef
{
	@NonNull private final ZoomIntoTestHelper helper;
	@NonNull private final C_Order_StepDefData orderTable;

	private List<String> tablesWithMissingWindows;
	private AdWindowId lastResolvedWindowId;

	@When("RecordWindowFinder is asked to resolve a window for each table listed in ad_table_windows_v, excluding:")
	public void resolve_window_for_each_table_excluding(@NonNull final DataTable dataTable)
	{
		final ImmutableSet<String> excludedTables = DataTableRows.of(dataTable)
				.stream()
				.map(row -> row.getAsString("TableName"))
				.collect(ImmutableSet.toImmutableSet());

		tablesWithMissingWindows = helper.findTablesWithMissingZoomIntoWindows(excludedTables);
	}

	@Then("every table in ad_table_windows_v resolves to a non-null window")
	public void every_table_resolves_to_non_null_window()
	{
		assertThat(tablesWithMissingWindows)
				.as("Tables in ad_table_windows_v that failed to resolve to a window via RecordWindowFinder")
				.isEmpty();
	}

	@Given("zoom-into for the following tables resolves to the expected windows by SOTrx:")
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

	@When("RecordWindowFinder resolves zoom-into for the C_Order identified by {string}")
	public void resolve_zoom_into_for_order(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final Optional<AdWindowId> windowId = helper.resolveZoomIntoWindowForRecord(
				I_C_Order.Table_Name,
				order.getC_Order_ID());

		lastResolvedWindowId = windowId.orElse(null);
	}

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
}
