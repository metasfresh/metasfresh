package de.metas.acct.open_items.related_documents;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Provides Open Item Clearing Accounting transactions
 * which used to clear open items of source document
 * and which where created by a SAP GL Journal document.
 */
@Component
public class ClearingFactAcctsRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	private final AcctDocRegistry acctDocRegistry;

	private static final String COLUMNNAME_Posted = "Posted";
	private final Priority relatedDocumentsPriority = Priority.HIGHEST;

	private static final String TABLENAME_Fact_Acct_Transactions_View = "Fact_Acct_Transactions_View";

	public ClearingFactAcctsRelatedDocumentsProvider(
			final AcctDocRegistry acctDocRegistry)
	{
		this.acctDocRegistry = acctDocRegistry;
	}

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		if (!acctDocRegistry.isAccountingTable(fromDocument.getTableName()))
		{
			return ImmutableList.of();
		}

		final AdWindowId factAcctWindowId = getFactAcctWindowId(targetWindowId);
		if (factAcctWindowId == null)
		{
			return ImmutableList.of();
		}

		// Return nothing if source is not Posted
		if (checkIsPosted(fromDocument).isFalse())
		{
			return ImmutableList.of();
		}

		final TableRecordReference fromRecordRef = TableRecordReference.of(fromDocument.getAD_Table_ID(), fromDocument.getRecord_ID());
		final ClearingFactAcctsQuerySupplier querySupplier = new ClearingFactAcctsQuerySupplier(factAcctDAO, fromRecordRef);

		final RelatedDocumentsId id = RelatedDocumentsId.ofString("OI_Clearing_GLJ_Fact_Acct");
		return ImmutableList.of(
				RelatedDocumentsCandidateGroup.of(
						RelatedDocumentsCandidate.builder()
								.id(id)
								.internalName(id.toJson())
								.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowIdAndCategory(factAcctWindowId, id.toJson()))
								.priority(relatedDocumentsPriority)
								.windowCaption(TranslatableStrings.anyLanguage("Clearing journal accounting transactions")) // TODO trl
								.querySupplier(querySupplier)
								.documentsCountSupplier(querySupplier)
								.build()));
	}

	private AdWindowId getFactAcctWindowId(@Nullable final AdWindowId expectedTargetWindowId)
	{
		//
		// Get the Fact_Acct AD_Window_ID
		final AdWindowId factAcctWindowId = CoalesceUtil.coalesceSuppliers(
				() -> RecordWindowFinder.findAdWindowId(TABLENAME_Fact_Acct_Transactions_View).orElse(null),
				() -> RecordWindowFinder.findAdWindowId(I_Fact_Acct.Table_Name).orElse(null)
		);
		if (factAcctWindowId == null)
		{
			return null;
		}

		// If not our target window ID, return nothing
		if (expectedTargetWindowId != null && !AdWindowId.equals(expectedTargetWindowId, factAcctWindowId))
		{
			return null;
		}

		return factAcctWindowId;
	}

	private OptionalBoolean checkIsPosted(@NonNull final IZoomSource document)
	{
		if (document.hasField(COLUMNNAME_Posted))
		{
			final boolean posted = document.getFieldValueAsBoolean(COLUMNNAME_Posted);
			return OptionalBoolean.ofBoolean(posted);
		}
		else
		{
			return OptionalBoolean.UNKNOWN;
		}
	}
}
