package de.metas.acct.api.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.DocumentPostMultiRequest;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.IPostingService;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.posting.DocumentPostingBusService;
import de.metas.acct.posting.DocumentPostingUserNotificationService;
import de.metas.acct.posting.log.DocumentPostingLogService;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.ReferencedRecordRepository;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.SpringContextHolder.Lazy;
import org.compiere.acct.Doc;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;

public class PostingService implements IPostingService
{
	@NonNull private static final Logger logger = LogManager.getLogger(PostingService.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	@NonNull private final Lazy<ReferencedRecordRepository> referencedRecordRepositoryHolder = SpringContextHolder.lazyBean(ReferencedRecordRepository.class);
	@NonNull private final Lazy<DocumentPostingUserNotificationService> userNotificationsHolder = SpringContextHolder.lazyBean(DocumentPostingUserNotificationService.class);
	@NonNull private final Lazy<AcctDocRegistry> acctDocRegistryHolder = SpringContextHolder.lazyBean(AcctDocRegistry.class);
	@NonNull private final Lazy<DocumentPostingBusService> postingBusServiceHolder = SpringContextHolder.lazyBean(DocumentPostingBusService.class);
	@NonNull private final Lazy<DocumentPostingLogService> documentPostingLogServiceHolder = SpringContextHolder.lazyBean(DocumentPostingLogService.class);

	/**
	 * Flag indicating that the whole accounting module is enabled or disabled
	 */
	@VisibleForTesting
	public static final String SYSCONFIG_Enabled = "org.adempiere.acct.Enabled";

	@Override
	public boolean isEnabled() {return sysConfigBL.getBooleanValue(SYSCONFIG_Enabled, true);}

	@Override
	public void schedule(@NonNull final DocumentPostRequest request)
	{
		schedule(DocumentPostMultiRequest.of(request));
	}

	@Override
	public void schedule(@NonNull DocumentPostMultiRequest requests)
	{
		if (!isEnabled())
		{
			userNotificationsHolder.get().notifyPostingError("Accounting module is disabled", requests);
			return;
		}

		postingBusServiceHolder.get().sendAfterCommit(requests);
	}

	@Override
	public void postAfterCommit(@NonNull final DocumentPostMultiRequest requests)
	{
		trxManager.accumulateAndProcessAfterCommit(
				"DocumentPostRequests.toPostNow",
				requests.toSet(),
				this::postNow
		);

	}

	private void postNow(@NonNull final List<DocumentPostRequest> requests)
	{
		requests.forEach(this::postNow);
	}

	private void postNow(@NonNull final DocumentPostRequest request)
	{
		// TODO check Profiles.isProfileActive(Profiles.PROFILE_AccountingService) ... maybe not
		// TODO check if disabled? maybe not....

		logger.debug("Posting directly: {}", this);

		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientId(ctx, request.getClientId());

		try (final IAutoCloseable ignored = Env.switchContext(ctx))
		{
			final List<AcctSchema> acctSchemas = acctSchemaDAO.getAllByClient(request.getClientId());
			final Doc<?> doc = acctDocRegistryHolder.get().get(acctSchemas, request.getRecord());
			doc.post(request.isForce(), true);

			documentPostingLogServiceHolder.get().logPostingOK(request);
		}
		catch (final Exception ex)
		{
			if (isDocumentDeleted(request.getRecord()))
			{
				// A deleted document has no accounting facts left to post: not an error to report to the user, but keep the exception in the log.
				logger.debug("Skip posting {} because the document does not exist anymore", request.getRecord(), ex);
				return;
			}

			final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex);
			
			documentPostingLogServiceHolder.get().logPostingError(request, metasfreshException);

			if (request.getOnErrorNotifyUserId() != null)
			{
				userNotificationsHolder.get().notifyPostingError(request.getOnErrorNotifyUserId(), request.getRecord(), metasfreshException);
			}
		}
	}

	private boolean isDocumentDeleted(@NonNull final TableRecordReference documentRef)
	{
		try
		{
			return !referencedRecordRepositoryHolder.get().exists(documentRef);
		}
		catch (final Exception ex)
		{
			// Called from a catch block: an exception thrown here would replace the actual posting error.
			logger.warn("Failed checking if {} still exists. Considering it as still existing.", documentRef, ex);
			return false;
		}
	}

	//
	//
	// ----------
	//
	//
}
