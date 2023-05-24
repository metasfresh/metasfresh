package de.metas.dunning.invoice;

import com.google.common.collect.ImmutableList;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MQuery;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@Component
public class Invoice2DunningDocRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	private final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		final InvoiceId invoiceId = getInvoiceIdOrNull(fromDocument);
		if (invoiceId == null)
		{
			return ImmutableList.of();
		}

		final AdWindowId dunningDocWindowId = getDunningDocWindowId(targetWindowId);
		if (dunningDocWindowId == null)
		{
			return ImmutableList.of();
		}

		final Set<DunningDocId> dunningDocIds = dunningDAO.retrieveDunningDocIdsBySourceRef(TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId));
		if (dunningDocIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final MQuery query = new MQuery(I_AD_Window.Table_Name);
		query.addRestriction(DB.buildSqlList(I_AD_Window.COLUMNNAME_AD_Window_ID, dunningDocIds));

		final RelatedDocumentsId id = RelatedDocumentsId.ofString("C_DunningDocs_by_C_Invoice_ID");
		return ImmutableList.of(
				RelatedDocumentsCandidateGroup.builder()
						.candidate(
								RelatedDocumentsCandidate.builder()
										.id(id)
										.internalName(id.toJson())
										.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(dunningDocWindowId))
										.priority(Priority.HIGHEST)
										.query(query)
										.windowCaption(buildFilterCaption(dunningDocWindowId))
										.filterByFieldCaption(buildFilterByFieldCaption(invoiceId))
										.documentsCountSupplier((permissions) -> dunningDocIds.size())
										.build())

						.build()
		);
	}

	@Nullable
	private AdWindowId getDunningDocWindowId(@Nullable final AdWindowId targetWindowId)
	{
		final AdWindowId dunningDocWindowId = RecordWindowFinder.findAdWindowId(I_C_DunningDoc.Table_Name).orElse(null);
		if (dunningDocWindowId == null)
		{
			return null;
		}

		if (targetWindowId != null && !AdWindowId.equals(dunningDocWindowId, targetWindowId))
		{
			return null;
		}

		return dunningDocWindowId;
	}

	private InvoiceId getInvoiceIdOrNull(final IZoomSource fromDocument)
	{
		if (I_C_Invoice.Table_Name.equals(fromDocument.getTableName()))
		{
			return InvoiceId.ofRepoId(fromDocument.getRecord_ID());
		}
		else
		{
			return null;
		}
	}

	private ITranslatableString buildFilterCaption(final AdWindowId dunningDocWindowId)
	{
		return adWindowDAO.retrieveWindowName(dunningDocWindowId);
	}

	private ITranslatableString buildFilterByFieldCaption(@NonNull final InvoiceId invoiceId)
	{
		final String invoiceSummary = invoiceBL.getSummary(invoiceBL.getById(invoiceId));
		return TranslatableStrings.builder().appendADElement("C_Invoice_ID").append(": ").append(invoiceSummary).build();
	}

}
