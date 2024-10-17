package de.metas.acct.api.impl;

import de.metas.Profiles;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.IPostingRequestBuilder;
import de.metas.acct.api.IPostingService;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.posting.DocumentPostRequest;
import de.metas.acct.posting.DocumentPostingBusService;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.IClientUIInvoker;
import de.metas.event.Topic;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.acct.Doc;
import org.compiere.acct.PostingExecutionException;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;

@ToString(of = { "_force", "_clientId", "_documentRef", "_postImmediate", "_postWithoutServer", "_failOnError" })
/* package */class PostingRequestBuilder implements IPostingRequestBuilder
{
	public static final Topic NOTIFICATIONS_TOPIC = Topic.distributed("de.metas.acct.UserNotifications");

	// services
	private static final transient Logger logger = LogManager.getLogger(PostingRequestBuilder.class);
	private final transient IPostingService postingService = Services.get(IPostingService.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IClientDAO clientDAO = Services.get(IClientDAO.class);
	private final transient INotificationBL userNotifications = Services.get(INotificationBL.class);

	// Parameters
	private boolean _force;
	private ClientId _clientId = null;
	private TableRecordReference _documentRef = null;
	private boolean _failOnError = DEFAULT_FailOnError;
	private UserId _onErrorNotifyUserId = null;
	//
	private PostImmediate _postImmediate = PostImmediate.IfConfigured;
	private boolean _postWithoutServer = false;

	// Status
	private boolean _executed = false;
	private AdempiereException _postedException = null;

	@Override
	public IClientUIInvoker postItOnUI()
	{
		return Services.get(IClientUI.class)
				.invoke()
				.setRunnable(this::postIt);
	}

	@Override
	public final void postIt()
	{
		setExecuted();

		if (!postingService.isEnabled())
		{
			setPostedError("Accounting module is disabled");
			postingComplete();
			return;
		}

		//
		// Check if we shall post the document immediately by checking PostImmediate option
		// If not, we will enqueue it.
		if (!isPostImmediate())
		{
			postAfterTrxCommit(this::postIt_Enqueue);
			return;
		}

		//
		// Post directly (without server) if that's the case
		if (isPostWithoutServer())
		{
			// NOTE: even if we need to post it directly, without contacting the server
			// we shall do it after the transaction is committed, because some of the Doc* implementations
			// are relying on the case that dependent objects are accessible out of transaction.
			postAfterTrxCommit(this::postIt_Directly);
			return;
		}

		//
		// Post it on server
		postAfterTrxCommit(this::postIt_Enqueue);
	}

	/**
	 * Method called when we shall enqueue the document to posting queue instead of posting it immediately
	 */
	private final void postIt_Enqueue()
	{
		final ClientId clientId = getClientId();
		final TableRecordReference documentRef = getDocumentRef();
		final boolean force = isForce();

		logger.debug("Enqueueing to be posted on server server: {}", PostingRequestBuilder.this);

		final DocumentPostingBusService postingBusService = Adempiere.getBean(DocumentPostingBusService.class);
		postingBusService.postRequest(DocumentPostRequest.builder()
				.record(documentRef)
				.clientId(clientId)
				.force(force)
				.onErrorNotifyUserId(getOnErrorNotifyUserId())
				.build());
	}

	/**
	 * Directly Post the document (without contacting the server!)
	 */
	private final void postIt_Directly()
	{
		logger.debug("Posting directly: {}", this);

		final AcctDocRegistry docFactory = Adempiere.getBean(AcctDocRegistry.class);

		final ClientId clientId = getClientId();
		final TableRecordReference documentRef = getDocumentRef();
		final boolean force = isForce();

		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientId(ctx, clientId);

		try (final IAutoCloseable c = Env.switchContext(ctx))
		{
			final List<AcctSchema> ass = Services.get(IAcctSchemaDAO.class).getAllByClient(clientId);

			final Doc<?> doc = docFactory.get(ass, documentRef);
			final boolean repost = true;
			doc.post(force, repost);
		}
	}

	/** Makes sure we are still in configuration phase and not in execution phase */
	private final void assertNotExecuted()
	{
		Check.assume(!_executed, "posting request not already executed");
	}

	/** Change the status to "execution phase". From now on, no configurations are allowed */
	private final void setExecuted()
	{
		assertNotExecuted();
		_executed = true;
	}

	@Override
	public IPostingRequestBuilder setForce(final boolean force)
	{
		assertNotExecuted();

		_force = force;
		return this;
	}

	private final boolean isForce()
	{
		return _force;
	}

	@Override
	public IPostingRequestBuilder setClientId(@NonNull final ClientId clientId)
	{
		assertNotExecuted();
		Check.assume(!clientId.isSystem(), "AD_Client_ID is not system");
		_clientId = clientId;
		return this;
	}

	private final ClientId getClientId()
	{
		Check.assumeNotNull(_clientId, "AD_Client_ID is set");
		return _clientId;
	}

	@Override
	public IPostingRequestBuilder setDocumentRef(@NonNull final TableRecordReference documentRef)
	{
		assertNotExecuted();
		_documentRef = documentRef;
		return this;
	}

	private final TableRecordReference getDocumentRef()
	{
		Check.assumeNotNull(_documentRef, "document is set");
		return _documentRef;
	}

	@Override
	public IPostingRequestBuilder setPostImmediate(final PostImmediate postImmediate)
	{
		assertNotExecuted();
		Check.assumeNotNull(postImmediate, "postImmediate not null");
		_postImmediate = postImmediate;

		return this;
	}

	@Override
	public final IPostingRequestBuilder setPostWithoutServer()
	{
		this._postWithoutServer = true;
		return this;
	}

	/**
	 * @return <code>true</code> if we shall post this document right now
	 */
	private final boolean isPostImmediate()
	{
		if (PostImmediate.Yes == _postImmediate)
		{
			return true;
		}
		else if (PostImmediate.No == _postImmediate)
		{
			return false;
		}
		else if (PostImmediate.IfConfigured == _postImmediate)
		{
			// If accounting module is fully disabled, return false
			if (!postingService.isEnabled())
			{
				return false;
			}

			//
			// Check if PostImmediate is allowed by AD_Client configuration
			final I_AD_Client client = clientDAO.getById(getClientId());
			return client.isPostImmediate();
		}
		else
		{
			throw new AdempiereException("Unknown " + PostImmediate.class + ": " + _postImmediate);
		}
	}

	/** @return true if we shall post it directly (without contacting the server) */
	private final boolean isPostWithoutServer()
	{
		// If we were explicitly asked to post without contacting the server => do it
		if (_postWithoutServer)
		{
			return true;
		}

		// Post without server if we are running the accounting service/server
		return Profiles.isProfileActive(Profiles.PROFILE_AccountingService);
	}

	@Override
	public IPostingRequestBuilder setFailOnError(final boolean failOnError)
	{
		assertNotExecuted();

		_failOnError = failOnError;
		return this;
	}

	/** @return true if we shall fail (propagate the exception) */
	private final boolean isFailOnError()
	{
		return _failOnError;
	}

	@Override
	public IPostingRequestBuilder onErrorNotifyUser(final UserId userId)
	{
		assertNotExecuted();

		_onErrorNotifyUserId = userId;
		return this;
	}

	private UserId getOnErrorNotifyUserId()
	{
		return _onErrorNotifyUserId;
	}

	private final void setPostedError(final String postedErrorMessage)
	{
		final PostingExecutionException postedException = postedErrorMessage != null ? new PostingExecutionException(postedErrorMessage) : null;
		setPostedError(postedException);
	}

	private final void setPostedError(final Exception postedException)
	{
		if (postedException == null)
		{
			_postedException = null;
		}
		else
		{
			_postedException = AdempiereException.wrapIfNeeded(postedException);
		}
	}

	/**
	 * Method called when the posting is complete (with or without errors).
	 * <p>
	 * This method will set the {@link #isPosted()} flag and it will also throw the posting exception in case is configured to do so
	 *
	 * @throws PostingExecutionException in case there was a posting exception and {@link #isFailOnError()}
	 */
	private final void postingComplete()
	{
		final AdempiereException postedException = _postedException;

		//
		// Notify user
		if (getOnErrorNotifyUserId() != null && postedException != null)
		{
			final UserNotificationRequest notification = UserNotificationRequest.builder()
					.topic(NOTIFICATIONS_TOPIC)
					.recipientUserId(getOnErrorNotifyUserId())
					.contentPlain(postedException.getLocalizedMessage())
					.targetAction(TargetRecordAction.of(getDocumentRef()))
					.build();
			userNotifications.sendAfterCommit(notification);
		}

		//
		// Fail on error if needed
		// IMPORTANT: keep it last!
		if (isFailOnError() && postedException != null)
		{
			throw postedException;
		}
	}

	/**
	 * Execute the posting after transaction is committed.
	 * If the transaction was already committed, the posting is executed right away (synchronously).
	 *
	 * @param postRunnable runnable that shall do the actual posting; it is assumed that the runnable WILL NOT throw any exceptions.
	 */
	private final void postAfterTrxCommit(@NonNull final Runnable postRunnable)
	{
		final ITrxListenerManager trxListenerManager = trxManager.getCurrentTrxListenerManagerOrAutoCommit();
		trxListenerManager
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(innerTrx -> invokeRunnable(postRunnable));
	}

	private void invokeRunnable(@NonNull final Runnable postRunnable)
	{
		try
		{
			postRunnable.run();
		}
		catch (Exception ex)
		{
			setPostedError(ex);
		}
		postingComplete();
	}
}
