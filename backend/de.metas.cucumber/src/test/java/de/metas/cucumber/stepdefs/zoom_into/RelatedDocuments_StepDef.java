package de.metas.cucumber.stepdefs.zoom_into;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Order;
import org.adempiere.ad.persistence.TableModelLoader;
import org.compiere.model.PO;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class RelatedDocuments_StepDef
{
	@NonNull private final ZoomIntoTestHelper helper;
	@NonNull private final C_Order_StepDefData orderTable;

	private ImmutableList<RelatedDocumentsCandidateGroup> lastCandidateGroups;

	/**
	 * For each source table listed in the DataTable, resolves related document candidate groups
	 * and asserts the count meets the given minimum.
	 * Uses soft assertions so all rows are validated before failing.
	 *
	 * @param dataTable columns: {@code SourceTable} (AD table name), {@code MinTargetCount} (minimum expected groups)
	 */
	@Then("the following source tables have at least the listed related document targets:")
	public void source_tables_have_minimum_related_document_targets(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(dataTable).forEach(row -> {
			final String sourceTable = row.getAsString("SourceTable");
			final int minTargetCount = row.getAsInt("MinTargetCount");

			final ImmutableList<RelatedDocumentsCandidateGroup> candidates = getCandidateGroupsForTable(sourceTable);

			softly.assertThat(candidates.size())
					.as("Related document candidate groups for source table " + sourceTable)
					.isGreaterThanOrEqualTo(minTargetCount);
		});

		softly.assertAll();
	}

	/**
	 * Resolves related document candidate groups for the given source table and asserts that
	 * the target windows include each window name listed in the DataTable.
	 *
	 * @param sourceTable the AD table name (e.g. "C_Order")
	 * @param dataTable single-column table with header {@code TargetWindowName}; expected window names in en_US
	 */
	@Then("related documents for source table {string} include targets:")
	public void related_documents_include_targets(@NonNull final String sourceTable, @NonNull final DataTable dataTable)
	{
		final ImmutableList<RelatedDocumentsCandidateGroup> candidates = getCandidateGroupsForTable(sourceTable);

		final Set<String> targetWindowNames = candidates.stream()
				.map(group -> getWindowName(group.getTargetWindowId()))
				.collect(Collectors.toSet());

		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(dataTable).forEach(row -> {
			final String expectedTargetWindowName = row.getAsString("TargetWindowName");

			softly.assertThat(targetWindowNames)
					.as("Related document target windows for source table " + sourceTable + " should include " + expectedTargetWindowName)
					.contains(expectedTargetWindowName);
		});

		softly.assertAll();
	}

	/**
	 * Retrieves related document candidate groups for a specific C_Order record.
	 * Stores the result in {@code lastCandidateGroups} for assertion by subsequent {@code @Then} steps.
	 *
	 * @param orderIdentifier the StepDefData identifier of the C_Order
	 */
	@When("related documents are retrieved for the C_Order identified by {string}")
	public void related_documents_retrieved_for_order(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		lastCandidateGroups = helper.getRelatedDocumentsCandidates(order);
	}

	/**
	 * Asserts that the number of related document candidate groups retrieved by the preceding
	 * {@code related documents are retrieved} step is at least the given minimum count.
	 *
	 * @param minCount the minimum number of candidate groups expected
	 */
	@Then("the related documents include at least {int} candidate groups")
	public void related_documents_include_at_least_n_groups(final int minCount)
	{
		assertThat(lastCandidateGroups)
				.as("Related documents candidate groups")
				.isNotNull()
				.hasSizeGreaterThanOrEqualTo(minCount);
	}

	/**
	 * Asserts that the related document candidate groups include at least one group
	 * whose target window matches the given expected window name.
	 *
	 * @param expectedWindowName the expected target window name in en_US (e.g. "Invoice (Customer)")
	 */
	@Then("the related documents include a candidate group targeting window {string}")
	public void related_documents_include_candidate_group_targeting_window(@NonNull final String expectedWindowName)
	{
		assertThat(lastCandidateGroups)
				.as("Related documents candidate groups should not be null")
				.isNotNull();

		final Set<String> windowNames = lastCandidateGroups.stream()
				.map(group -> getWindowName(group.getTargetWindowId()))
				.collect(Collectors.toSet());

		assertThat(windowNames)
				.as("Related document target window names should include " + expectedWindowName)
				.contains(expectedWindowName);
	}

	private ImmutableList<RelatedDocumentsCandidateGroup> getCandidateGroupsForTable(@NonNull final String tableName)
	{
		final PO po = TableModelLoader.instance.newPO(tableName);
		return helper.getRelatedDocumentsCandidates(po);
	}

	private String getWindowName(@NonNull final AdWindowId windowId)
	{
		final String name = helper.getWindowName(windowId);
		return name != null ? name : "UNKNOWN(ID=" + windowId.getRepoId() + ")";
	}
}
