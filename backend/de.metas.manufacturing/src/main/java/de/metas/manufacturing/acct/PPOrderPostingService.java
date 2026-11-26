package de.metas.manufacturing.acct;

import com.google.common.annotations.VisibleForTesting;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.api.IPostingService;
import de.metas.acct.posting.log.DocumentPostingLogService;
import de.metas.document.engine.DocStatus;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.BooleanSupplier;

/**
 * Posts a manufacturing order and its cost collectors synchronously, in the caller's thread, so that
 * {@code PP_Order_Cost} reflects every issue and receipt before anything reads it.
 */
@Service
public class PPOrderPostingService
{
	private static final Duration DEFAULT_POLL_INTERVAL = Duration.ofMillis(250);
	private static final Duration DEFAULT_MAX_WAIT = Duration.ofSeconds(30);

	private static final Logger logger = LogManager.getLogger(PPOrderPostingService.class);

	@NonNull private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	@NonNull private final IPPCostCollectorDAO costCollectorDAO = Services.get(IPPCostCollectorDAO.class);

	@NonNull private final IPostingService postingService;
	@NonNull private final DocumentPostingLogService documentPostingLogService;
	@NonNull private final Sleeper sleeper;
	@NonNull private final Duration pollInterval;
	private final long maxPolls;

	@FunctionalInterface
	public interface Sleeper
	{
		void sleep(@NonNull Duration duration) throws InterruptedException;
	}

	@Autowired
	public PPOrderPostingService(@NonNull final DocumentPostingLogService documentPostingLogService)
	{
		this(
				Services.get(IPostingService.class),
				duration -> Thread.sleep(duration.toMillis()),
				documentPostingLogService);
	}

	@VisibleForTesting
	public PPOrderPostingService(
			@NonNull final IPostingService postingService,
			@NonNull final Sleeper sleeper,
			@NonNull final DocumentPostingLogService documentPostingLogService)
	{
		this.postingService = postingService;
		this.documentPostingLogService = documentPostingLogService;
		this.sleeper = sleeper;
		this.pollInterval = DEFAULT_POLL_INTERVAL;
		this.maxPolls = DEFAULT_MAX_WAIT.toMillis() / DEFAULT_POLL_INTERVAL.toMillis();
	}

	/**
	 * Posts the order first (it creates the {@code PP_Order_Cost} rows), then each processed cost collector in creation order.
	 * Documents already posted ({@code Posted='Y'}) are skipped; not posted and posting-error ones are (re)posted.
	 * A document that is currently being posted elsewhere is waited for, up to 30 seconds per document.
	 */
	public void postOrderAndCostCollectorsIfNotPosted(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);
		postIfNotPosted(ppOrder, ppOrder::isPosted, ppOrder::isProcessing, ClientId.ofRepoId(ppOrder.getAD_Client_ID()));

		for (final I_PP_Cost_Collector costCollector : costCollectorDAO.getByOrderId(ppOrderId))
		{
			if (isPostable(costCollector))
			{
				postIfNotPosted(costCollector, costCollector::isPosted, costCollector::isProcessing, ClientId.ofRepoId(costCollector.getAD_Client_ID()));
			}
		}
	}

	private void postIfNotPosted(
			@NonNull final Object document,
			@NonNull final BooleanSupplier isPosted,
			@NonNull final BooleanSupplier isProcessing,
			@NonNull final ClientId clientId)
	{
		final TableRecordReference documentRef = TableRecordReference.of(document);

		waitWhileProcessing(document, isProcessing, documentRef);
		if (isPosted.getAsBoolean())
		{
			return;
		}

		try
		{
			postNow(documentRef, clientId);
		}
		catch (final RuntimeException postingError)
		{
			// The lock fails when the accounting service locked the document between our check and our lock.
			InterfaceWrapperHelper.refresh(document);
			if (!isProcessing.getAsBoolean())
			{
				logPostingError(documentRef, clientId, postingError);
				throw postingError;
			}

			logger.debug("Accounting service locked {} before us; waiting for it", documentRef, postingError);
			waitWhileProcessing(document, isProcessing, documentRef);
			if (!isPosted.getAsBoolean())
			{
				try
				{
					postNow(documentRef, clientId);
				}
				catch (final RuntimeException retryPostingError)
				{
					logPostingError(documentRef, clientId, retryPostingError);
					throw retryPostingError;
				}
			}
		}
	}

	private void logPostingError(@NonNull final TableRecordReference documentRef, @NonNull final ClientId clientId, @NonNull final RuntimeException postingError)
	{
		documentPostingLogService.logPostingError(toPostRequest(documentRef, clientId), AdempiereException.wrapIfNeeded(postingError));
	}

	private static DocumentPostRequest toPostRequest(@NonNull final TableRecordReference documentRef, @NonNull final ClientId clientId)
	{
		return DocumentPostRequest.builder().record(documentRef).clientId(clientId).build();
	}

	/**
	 * Posts synchronously, no force: the accounting service posts with force, so forcing here could post the same document twice at once.
	 * Throws on a posting error, unlike the accounting service's own after-commit posting.
	 */
	private void postNow(@NonNull final TableRecordReference documentRef, @NonNull final ClientId clientId)
	{
		postingService.postNowAndThrow(toPostRequest(documentRef, clientId));
	}

	private void waitWhileProcessing(
			@NonNull final Object document,
			@NonNull final BooleanSupplier isProcessing,
			@NonNull final TableRecordReference documentRef)
	{
		InterfaceWrapperHelper.refresh(document);
		for (long poll = 0; isProcessing.getAsBoolean(); poll++)
		{
			if (poll >= maxPolls)
			{
				throw new AdempiereException("Document " + documentRef + " is still being posted by the accounting service after "
						+ DEFAULT_MAX_WAIT.getSeconds() + "s. Please try again; if it persists, check the document's posting.");
			}

			try
			{
				sleeper.sleep(pollInterval);
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
				throw AdempiereException.wrapIfNeeded(e);
			}

			InterfaceWrapperHelper.refresh(document);
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
