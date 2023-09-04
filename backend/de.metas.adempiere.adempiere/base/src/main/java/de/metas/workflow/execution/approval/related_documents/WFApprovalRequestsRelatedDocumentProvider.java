package de.metas.workflow.execution.approval.related_documents;

import com.google.common.collect.ImmutableList;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsCountSupplier;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import de.metas.workflow.execution.approval.WFApprovalRequestRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_WF_Approval_Request;
import org.compiere.model.MQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WFApprovalRequestsRelatedDocumentProvider implements IRelatedDocumentsProvider
{
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	private final WFApprovalRequestRepository wfApprovalRequestRepository;

	private final Priority relatedDocumentsPriority = Priority.HIGHEST;

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		final AdWindowId windowId = RecordWindowFinder.findAdWindowId(I_AD_WF_Approval_Request.Table_Name).orElse(null);
		if (windowId == null)
		{
			return ImmutableList.of();
		}

		// If not our target window ID, return nothing
		if (targetWindowId != null && !AdWindowId.equals(targetWindowId, windowId))
		{
			return ImmutableList.of();
		}

		final TableRecordReference documentRef = TableRecordReference.of(fromDocument.getAD_Table_ID(), fromDocument.getRecord_ID());
		if (!wfApprovalRequestRepository.hasRecordsForTable(documentRef.getAdTableId()))
		{
			return ImmutableList.of();
		}

		//
		// Build query and check count if needed
		final MQuery query = new MQuery(I_AD_WF_Approval_Request.Table_Name);
		query.addRestriction(I_AD_WF_Approval_Request.COLUMNNAME_AD_Table_ID, MQuery.Operator.EQUAL, fromDocument.getAD_Table_ID());
		query.addRestriction(I_AD_WF_Approval_Request.COLUMNNAME_Record_ID, MQuery.Operator.EQUAL, fromDocument.getRecord_ID());

		final RelatedDocumentsCountSupplier recordsCountSupplier = (permissions) -> wfApprovalRequestRepository.countByDocumentRef(documentRef);

		return ImmutableList.of(
				RelatedDocumentsCandidateGroup.of(
						RelatedDocumentsCandidate.builder()
								.id(RelatedDocumentsId.ofString(I_AD_WF_Approval_Request.Table_Name))
								.internalName(I_AD_WF_Approval_Request.Table_Name)
								.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(windowId))
								.priority(relatedDocumentsPriority)
								.querySupplier(() -> query)
								.windowCaption(adWindowDAO.retrieveWindowName(windowId))
								.documentsCountSupplier(recordsCountSupplier)
								.build()));
	}
}
