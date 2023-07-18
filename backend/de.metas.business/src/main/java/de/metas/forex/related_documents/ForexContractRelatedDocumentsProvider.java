package de.metas.forex.related_documents;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySupplier;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySuppliers;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractService;
import de.metas.order.OrderId;
import de.metas.order.model.I_C_Order;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.compiere.model.MQuery;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

@Component
public class ForexContractRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	private final ForexContractService forexContractService;

	public ForexContractRelatedDocumentsProvider(final ForexContractService forexContractService) {this.forexContractService = forexContractService;}

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		if (I_C_Order.Table_Name.equals(fromDocument.getTableName()))
		{
			final OrderId orderId = OrderId.ofRepoId(fromDocument.getRecord_ID());
			return getForOrder(orderId, targetWindowId);
		}
		else
		{
			return ImmutableList.of();
		}
	}

	private List<RelatedDocumentsCandidateGroup> getForOrder(
			@NonNull final OrderId orderId,
			@Nullable final AdWindowId expectedWindowId)
	{
		final AdWindowId fecWindowId = getFECWindowId(expectedWindowId);
		if (fecWindowId == null)
		{
			return ImmutableList.of();
		}

		final ImmutableSet<ForexContractId> contractIds = forexContractService.getContractIdsByOrderId(orderId);
		return toRelatedDocumentsCandidateGroups(contractIds, fecWindowId);
	}

	@NonNull
	private ImmutableList<RelatedDocumentsCandidateGroup> toRelatedDocumentsCandidateGroups(
			@NonNull final ImmutableSet<ForexContractId> contractIds,
			@NonNull final AdWindowId fecWindowId)
	{
		if (contractIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return ImmutableList.of(
				RelatedDocumentsCandidateGroup.of(
						RelatedDocumentsCandidate.builder()
								.id(RelatedDocumentsId.ofString(I_C_ForeignExchangeContract.Table_Name))
								.internalName(I_C_ForeignExchangeContract.Table_Name)
								.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(fecWindowId))
								.priority(Priority.HIGHEST)
								.querySupplier(createQuerySupplier(contractIds))
								.windowCaption(adWindowDAO.retrieveWindowName(fecWindowId))
								.documentsCountSupplier(permissions -> contractIds.size())
								.build()
				)
		);
	}

	@Nullable
	private static AdWindowId getFECWindowId(@Nullable final AdWindowId expectedWindowId)
	{
		final AdWindowId adWindowId = RecordWindowFinder.findAdWindowId(I_C_ForeignExchangeContract.Table_Name).orElse(null);

		// If not the expected window ID, return null
		if (expectedWindowId != null && !AdWindowId.equals(expectedWindowId, adWindowId))
		{
			return null;
		}

		return adWindowId;
	}

	private static RelatedDocumentsQuerySupplier createQuerySupplier(final ImmutableSet<ForexContractId> contractIds)
	{
		final MQuery query = new MQuery(I_C_ForeignExchangeContract.Table_Name);
		query.addRestriction(DB.buildSqlList(I_C_ForeignExchangeContract.COLUMNNAME_C_ForeignExchangeContract_ID, contractIds, null));
		return RelatedDocumentsQuerySuppliers.ofQuery(query);
	}
}
