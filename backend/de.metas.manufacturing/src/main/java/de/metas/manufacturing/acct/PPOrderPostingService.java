package de.metas.manufacturing.acct;

import com.google.common.annotations.VisibleForTesting;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Posts a manufacturing order and its cost collectors synchronously, in the caller's thread, so that
 * {@code PP_Order_Cost} reflects every issue and receipt before anything reads it.
 */
@Service
public class PPOrderPostingService
{
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IPPCostCollectorDAO costCollectorDAO = Services.get(IPPCostCollectorDAO.class);

	@NonNull private final DocumentPoster documentPoster;

	@FunctionalInterface
	public interface DocumentPoster
	{
		/**
		 * Posts the document now; any posting error is thrown, not logged.
		 */
		void postNow(@NonNull TableRecordReference documentRef, @NonNull ClientId clientId);
	}

	@Autowired
	public PPOrderPostingService(@NonNull final AcctDocRegistry acctDocRegistry)
	{
		this(newDocumentPoster(acctDocRegistry));
	}

	@VisibleForTesting
	public PPOrderPostingService(@NonNull final DocumentPoster documentPoster)
	{
		this.documentPoster = documentPoster;
	}

	private static DocumentPoster newDocumentPoster(@NonNull final AcctDocRegistry acctDocRegistry)
	{
		final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);

		// Same call as PostingService.postNow, minus its catch-all: that one only logs the error.
		return (documentRef, clientId) -> acctDocRegistry
				.get(acctSchemaDAO.getAllByClient(clientId), documentRef)
				.post(false, true);
	}

	/**
	 * Posts the order first (it creates the {@code PP_Order_Cost} rows), then each processed cost collector in creation order.
	 * Documents already posted ({@code Posted='Y'}) are skipped; not posted and posting-error ones are (re)posted.
	 */
	public void postOrderAndCostCollectorsIfNotPosted(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);
		if (!ppOrder.isPosted())
		{
			documentPoster.postNow(TableRecordReference.of(ppOrder), ClientId.ofRepoId(ppOrder.getAD_Client_ID()));
		}

		for (final I_PP_Cost_Collector costCollector : costCollectorDAO.getByOrderId(ppOrderId))
		{
			if (isPostable(costCollector) && !costCollector.isPosted())
			{
				documentPoster.postNow(TableRecordReference.of(costCollector), ClientId.ofRepoId(costCollector.getAD_Client_ID()));
			}
		}
	}

	private static boolean isPostable(@NonNull final I_PP_Cost_Collector costCollector)
	{
		final DocStatus docStatus = DocStatus.ofNullableCode(costCollector.getDocStatus());
		return costCollector.isActive()
				&& costCollector.isProcessed()
				&& docStatus != null
				&& docStatus.isCompletedOrClosedReversedOrVoided();
	}
}
