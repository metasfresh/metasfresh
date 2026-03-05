package de.metas.cucumber.stepdefs.zoom_into;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.POZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsFactory;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.document.references.related_documents.RelatedDocumentsPermissionsFactory;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
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

	@NonNull private final RelatedDocumentsFactory relatedDocumentsFactory = SpringContextHolder.instance.getBean(RelatedDocumentsFactory.class);
	@NonNull private final IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);

	private ImmutableList<RelatedDocumentsCandidateGroup> lastCandidateGroups;

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

	@When("related documents are retrieved for the C_Order identified by {string}")
	public void related_documents_retrieved_for_order(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		lastCandidateGroups = helper.getRelatedDocumentsCandidates(order);
	}

	@Then("the related documents include at least {int} candidate groups")
	public void related_documents_include_at_least_n_groups(final int minCount)
	{
		assertThat(lastCandidateGroups)
				.as("Related documents candidate groups")
				.isNotNull();

		assertThat(lastCandidateGroups.size())
				.as("Number of related document candidate groups")
				.isGreaterThanOrEqualTo(minCount);
	}

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
		final IZoomSource zoomSource = POZoomSource.of(po);
		final RelatedDocumentsPermissions permissions = RelatedDocumentsPermissionsFactory.allowAll();
		return relatedDocumentsFactory.getRelatedDocumentsCandidates(zoomSource, permissions);
	}

	private String getWindowName(@NonNull final AdWindowId windowId)
	{
		final ITranslatableString name = windowDAO.retrieveWindowName(windowId);
		return name != null ? name.translate("en_US") : "UNKNOWN(ID=" + windowId.getRepoId() + ")";
	}
}
