package de.metas.acct.related_documents;

import com.google.common.collect.ImmutableList;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.model.I_Document_Acct_Log;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DocumentAcctLogsRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	@NonNull private final AcctDocRegistry acctDocRegistry;

	private final Priority relatedDocumentsPriority = Priority.HIGHEST;

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		if (!fromDocument.isSingleKeyRecord())
		{
			return ImmutableList.of();
		}

		final AdWindowId logsWindowId = RecordWindowFinder.findAdWindowId(I_Document_Acct_Log.Table_Name).orElse(null);
		if (logsWindowId == null)
		{
			return ImmutableList.of();
		}

		if (targetWindowId != null && !AdWindowId.equals(targetWindowId, logsWindowId))
		{
			return ImmutableList.of();
		}

		if (!isAccountableDocument(fromDocument))
		{
			return ImmutableList.of();
		}

		final DocumentAcctLogsQuerySupplier querySupplier = createQuerySupplier(fromDocument);

		return ImmutableList.of(
				RelatedDocumentsCandidateGroup.of(
						RelatedDocumentsCandidate.builder()
								.id(RelatedDocumentsId.ofString(I_Document_Acct_Log.Table_Name))
								.internalName(I_Document_Acct_Log.Table_Name)
								.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(logsWindowId))
								.windowCaption(adWindowDAO.retrieveWindowName(logsWindowId))
								.priority(relatedDocumentsPriority)
								.querySupplier(querySupplier)
								.documentsCountSupplier(querySupplier)
								.build()));
	}

	private boolean isAccountableDocument(final @NonNull IZoomSource fromDocument)
	{
		return acctDocRegistry.isAccountingTable(fromDocument.getTableName());
	}

	private DocumentAcctLogsQuerySupplier createQuerySupplier(final @NonNull IZoomSource fromDocument)
	{
		return DocumentAcctLogsQuerySupplier.builder()
				.queryBL(queryBL)
				.adTableId(fromDocument.getAD_Table_ID())
				.recordId(fromDocument.getRecord_ID())
				.build();
	}
}
