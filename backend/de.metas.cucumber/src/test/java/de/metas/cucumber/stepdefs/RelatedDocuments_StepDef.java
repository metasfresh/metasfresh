package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.POZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsFactory;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsPermissionsFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MQuery;
import org.compiere.model.PO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class RelatedDocuments_StepDef
{
	@NonNull private final C_Invoice_StepDefData invoiceTable;

	@And("^after not more than (.*)s, the (invoice) document with identifier (.*) has the following related documents:$")
	public void checkRelatedDocumentsUntilTimeout(
			final int timeoutSec,
			@NonNull final String tableType,
			@NonNull final String identifier,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final IZoomSource zoomSource = getZoomSource(tableType, identifier);
		final DataTableRows rows = DataTableRows.of(dataTable);
		assertThat(rows.size()).isGreaterThan(0);

		final RelatedDocumentsFactory relatedDocumentsFactory = SpringContextHolder.instance.getBean(RelatedDocumentsFactory.class);
		final RelatedDocumentsChecker checker = new RelatedDocumentsChecker(relatedDocumentsFactory, zoomSource, rows);

		StepDefUtil.tryAndWaitForItem(timeoutSec, 500, checker, checker::getCurrentContext);
	}

	@NonNull
	private IZoomSource getZoomSource(
			@NonNull final String tableType,
			@NonNull final String identifier)
	{
		final PO po = getTableRecordReference(tableType, identifier).getModel(PO.class);
		return POZoomSource.of(po);
	}

	@NonNull
	private TableRecordReference getTableRecordReference(
			@NonNull final String tableType,
			@NonNull final String identifier)
	{
		return switch (TableType.valueOf(tableType))
		{
			case invoice -> TableRecordReference.of(I_C_Invoice.Table_Name, invoiceTable.get(identifier).getC_Invoice_ID());
			default -> throw new AdempiereException("tableType `" + tableType + "` is not handled");
		};
	}

	//
	//
	//

	@RequiredArgsConstructor
	private static class RelatedDocumentsChecker implements ItemProvider<List<RelatedDocumentsCandidateGroup>>
	{
		@NonNull final RelatedDocumentsFactory relatedDocumentsFactory;
		@NonNull final IZoomSource zoomSource;
		@NonNull final DataTableRows rows;

		private ImmutableList<RelatedDocumentsCandidateGroup> _relatedDocumentsGroups = null; // lazy

		@Override
		public ProviderResult<List<RelatedDocumentsCandidateGroup>> execute()
		{
			final ImmutableList<RelatedDocumentsCandidateGroup> relatedDocumentsCandidateGroups = getRelatedDocumentsCandidates();

			final ArrayList<DataTableRow> rowsNotMatched = new ArrayList<>();
			rows.forEach((row) -> {
				if (!isMatching(relatedDocumentsCandidateGroups, row))
				{
					rowsNotMatched.add(row);
				}
			});

			return rowsNotMatched.isEmpty()
					? ProviderResult.resultWasFound(relatedDocumentsCandidateGroups)
					: ProviderResult.resultWasNotFound("Following rows were not found: " + rowsNotMatched);
		}

		private static boolean isMatching(ImmutableList<RelatedDocumentsCandidateGroup> relatedDocumentsGroups, final DataTableRow row)
		{
			for (final RelatedDocumentsCandidateGroup group : relatedDocumentsGroups)
			{
				for (RelatedDocumentsCandidate candidate : group.getCandidates())
				{
					if (isMatching(candidate, row))
					{
						return true;
					}
				}
			}

			return false;
		}

		private static boolean isMatching(final RelatedDocumentsCandidate candidate, final DataTableRow row)
		{
			final String internalName = row.getAsOptionalString("InternalName").orElse(null);
			if (internalName != null && !Objects.equals(candidate.getInternalName(), internalName))
			{
				return false;
			}

			final RelatedDocumentsId id = row.getAsOptionalString("id").map(RelatedDocumentsId::ofString).orElse(null);
			if (id != null && RelatedDocumentsId.equals(candidate.getId(), id))
			{
				return false;
			}

			final String targetTableName = row.getAsOptionalString("TargetTableName").orElse(null);
			if (targetTableName != null && !Objects.equals(candidate.getQuerySupplier().getQuery().getTableName(), targetTableName))
			{
				return false;
			}

			return true;
		}

		private ImmutableList<RelatedDocumentsCandidateGroup> getRelatedDocumentsCandidates()
		{
			return this._relatedDocumentsGroups = relatedDocumentsFactory.getRelatedDocumentsCandidates(
					zoomSource,
					RelatedDocumentsPermissionsFactory.allowAll()
			);
		}

		public String getCurrentContext()
		{
			return "Current related documents are: " + toContextString(_relatedDocumentsGroups);
		}

		private static CharSequence toContextString(final ImmutableList<RelatedDocumentsCandidateGroup> groups)
		{
			if (groups == null)
			{
				return "candidates were never retrieved";
			}
			if (groups.isEmpty())
			{
				return "no groups";
			}

			final StringBuilder sb = new StringBuilder();
			for (RelatedDocumentsCandidateGroup group : groups)
			{
				sb.append("\nGroup: targetWindowId=").append(group.getTargetWindowId());

				final ImmutableList<RelatedDocumentsCandidate> candidates = group.getCandidates();
				if (candidates.isEmpty())
				{
					sb.append("\n\t(no candidates)");
				}
				else
				{
					for (final RelatedDocumentsCandidate candidate : candidates)
					{
						sb.append("\n\t").append(toContextString(candidate));
					}
				}
			}

			return sb;
		}

		private static CharSequence toContextString(final RelatedDocumentsCandidate candidate)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("id=").append(candidate.getId());
			sb.append(", internalName=").append(candidate.getInternalName());
			sb.append(", windowCaption=").append(candidate.getWindowCaption().getDefaultValue());
			sb.append(", targetWindow=").append(candidate.getTargetWindow());

			final MQuery query = candidate.getQuerySupplier().getQuery();
			sb.append(", query={").append(query.getTableName()).append(" / ").append(query.getWhereClause()).append("}");

			return sb;
		}
	}
}
