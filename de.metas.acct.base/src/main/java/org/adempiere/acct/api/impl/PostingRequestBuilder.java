package org.adempiere.acct.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.acct.api.ClientAccountingStatus;
import org.adempiere.acct.api.IDocFactory;
import org.adempiere.acct.api.IPostingRequestBuilder;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.acct.Doc;
import org.compiere.acct.PostingExecutionException;
import org.compiere.db.CConnection;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MAcctSchema;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import com.google.common.base.Optional;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.IClientUIInvoker;
import de.metas.document.engine.IDocument;
import de.metas.logging.LogManager;
import de.metas.session.jaxrs.IServerService;
import lombok.NonNull;

/* package */class PostingRequestBuilder implements IPostingRequestBuilder
{
	// services
	private static final transient Logger log = LogManager.getLogger(PostingRequestBuilder.class);
	private final transient IPostingService postingService = Services.get(IPostingService.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IClientDAO clientDAO = Services.get(IClientDAO.class);

	// Parameters
	@ToStringBuilder(skip = true)
	private Properties _ctx;
	private String _trxName = ITrx.TRXNAME_None;
	private boolean _force;
	private Integer _adClientId = null;
	private ITableRecordReference _documentRef = null;
	private PostImmediate _postImmediate = PostImmediate.IfConfigured;
	private boolean _postWithoutServer = false;
	private boolean _failOnError = DEFAULT_FailOnError;

	// Status
	private boolean _executed = false;
	private boolean _posted = false;
	private PostingExecutionException _postedException = null;

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public IClientUIInvoker postItOnUI()
	{
		return Services.get(IClientUI.class)
				.invoke()
				.setRunnable(new Runnable()
				{

					@Override
					public void run()
					{
						postIt();
					}
				});
	}

	@Override
	public final IPostingRequestBuilder postIt()
	{
		setExecuted();

		final String trxName = getTrxName();

		//
		// Check if we shall post the document immediately by checking PostImmediate option
		// If not, we will enqueue it.
		if (!isPostImmediate())
		{
			postIt_Enqueue();
			return this;
		}

		if (!postingService.isEnabled())
		{
			setPostedError(new PostingExecutionException("Accounting module is disabled"));
			postingComplete();
			return this;
		}

		//
		// Post directly (without server) if that's the case
		if (isPostWithoutServer())
		{
			// NOTE: even if we need to post it directly, without contacting the server
			// we shall do it after the transaction is commited, because some of the Doc* implementations
			// are realying on the case that dependent objects are accessible out of transaction.
			postAfterTrxCommit(trxName, new Runnable()
			{
				@Override
				public void run()
				{
					postIt_Directly(ITrx.TRXNAME_None);
				}
			});
			return this;
		}

		//
		// Check if application Server is available
		final boolean serverAvailable = CConnection.get().isAppsServerOK(true);
		if (!serverAvailable)
		{
			setPostedError("Cannot Post document because server is not available: " + this);
			postingComplete();
			return this;
		}

		//
		// Post it on server
		final Properties ctxReduced = Env.getRemoteCallCtx(getCtx());
		final int AD_Client_ID = getAD_Client_ID();
		final ITableRecordReference documentRef = getDocumentRef();
		final int AD_Table_ID = documentRef.getAD_Table_ID();
		final int Record_ID = documentRef.getRecord_ID();
		final boolean force = isForce();
		try
		{
			// Should work on Client and Server
			final IServerService server = Services.get(IServerService.class);
			Check.assumeNotNull(server, "server not null");

			//
			// Post the document, after this transaction is commited, because
			// there is a big chance the document will not be accessible on server until the transaction is not finished,
			// so instead of asking the server to post it immediately, we will ask the server after the transaction is completed.
			postAfterTrxCommit(trxName, new Runnable()
			{
				@Override
				public void run()
				{
					log.debug("Posting on server: {}", PostingRequestBuilder.this);
					final String error = server.postImmediate(ctxReduced,
							AD_Client_ID,
							AD_Table_ID, Record_ID,
							force);
					setPostedError(error);
					log.info("from Server: {}", error == null ? "OK" : error);
				}
			});
		}
		catch (final Exception e)
		{
			setPostedError(e);
		}

		postingComplete();
		return this;
	}

	/**
	 * Method called when we shall enqueue the document to posting queue instead of posting it immediately
	 */
	private final void postIt_Enqueue()
	{
		// nothing at the moment,
		// but when we will implement a true posting queue (using de.metas.async or something else),
		// this is the right place where the document shall be enqueued)

		// NOTE: when you will handle this part, also keep in mind:
		// * if Client Accounting is Queue => do nothing because there is a manual process which will do the job

		// NOTE: we are reaching this method, also when !postingService.isEnabled()

		postingComplete();
	}

	/**
	 * Directly Post the document (without contacting the server!)
	 *
	 * @param trxName transaction to be used for posting; NOTE: it could be different from {@link #getTrxName()}.
	 */
	private final void postIt_Directly(final String trxName)
	{
		log.debug("Posting directly: {}", this);

		final IDocFactory docFactory = Services.get(IDocFactory.class);

		final Properties ctx = getCtx();
		final int AD_Client_ID = getAD_Client_ID();
		final ITableRecordReference documentRef = getDocumentRef();
		final int AD_Table_ID = documentRef.getAD_Table_ID();
		final int Record_ID = documentRef.getRecord_ID();
		final boolean force = isForce();

		// log.info("Table=" + AD_Table_ID + ", Record=" + Record_ID);
		try
		{
			final MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(ctx, AD_Client_ID);

			final Doc<?> doc = docFactory.getOrNull(ctx, ass, AD_Table_ID, Record_ID, trxName);
			if (doc == null)
			{
				throw new PostingExecutionException("No accountable document found: " + this);
			}

			final boolean repost = true;
			final String error = doc.post(force, repost);
			setPostedError(error);
		}
		catch (final Exception e)
		{
			setPostedError(e);
		}

		postingComplete();
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
	public boolean isPosted()
	{
		return _posted
				&& _postedException == null;
	}

	@Override
	public boolean isError()
	{
		return _postedException != null;
	}

	@Override
	public String getPostedErrorMessage()
	{
		return _postedException == null ? null : _postedException.getLocalizedMessage();
	}

	@Override
	public PostingExecutionException getPostedException()
	{
		return _postedException;
	}

	@Override
	public IPostingRequestBuilder setContext(final Properties ctx, final String trxName)
	{
		assertNotExecuted();
		_ctx = ctx;
		_trxName = trxName;
		return this;
	}

	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx is set");
		return _ctx;
	}

	private final String getTrxName()
	{
		return _trxName;
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
	public IPostingRequestBuilder setAD_Client_ID(final int adClientId)
	{
		assertNotExecuted();

		_adClientId = adClientId;
		return this;
	}

	private final int getAD_Client_ID()
	{
		Check.assumeNotNull(_adClientId, "AD_Client_ID is set");
		Check.assume(_adClientId > 0, "AD_Client_ID > 0");
		return _adClientId;
	}

	private final I_AD_Client getAD_Client()
	{
		return clientDAO.retriveClient(getCtx(), getAD_Client_ID());
	}

	@Override
	public IPostingRequestBuilder setDocument(final int adTableId, final int recordId)
	{
		assertNotExecuted();

		final ITableRecordReference documentRef = new TableRecordReference(adTableId, recordId);
		setDocumentRef(documentRef);
		return this;
	}

	private final void setDocumentRef(final ITableRecordReference documentRef)
	{
		_documentRef = documentRef;
	}

	private final ITableRecordReference getDocumentRef()
	{
		Check.assumeNotNull(_documentRef, "document is set");
		return _documentRef;
	}

	@Override
	public IPostingRequestBuilder setDocumentFromModel(final Object documentObj)
	{
		assertNotExecuted();
		Check.assumeNotNull(documentObj, "documentObj not null");

		if (documentObj instanceof IDocument)
		{
			final IDocument document = IDocument.cast(documentObj);
			setDocument(document);
			return this;
		}
		else
		{
			setDocumentFromModel0(documentObj);
			return this;
		}
	}

	private void setDocumentFromModel0(@NonNull final Object documentObj)
	{
		assertNotExecuted();

		Properties ctx = InterfaceWrapperHelper.getCtx(documentObj);
		String trxName = InterfaceWrapperHelper.getTrxName(documentObj);
		final Optional<Integer> adClientIdOpt = InterfaceWrapperHelper.getValue(documentObj, "AD_Client_ID");
		final int adClientId = adClientIdOpt.get();
		final TableRecordReference documentRef = TableRecordReference.of(documentObj);

		setContext(ctx, trxName);
		setAD_Client_ID(adClientId);
		setDocumentRef(documentRef);
	}

	@Override
	public IPostingRequestBuilder setDocument(@NonNull final IDocument document)
	{
		assertNotExecuted();

		setContext(document.getCtx(), document.get_TrxName());
		setAD_Client_ID(document.getAD_Client_ID());
		setDocumentRef(document.toTableRecordReference());

		return this;
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
			// Case: we are running on Client side
			// => post immediate if ClientAccouting is saying so
			final boolean isClient = Ini.isClient();
			if (isClient)
			{
				final ClientAccountingStatus clientAccountingStatus = postingService.getClientAccountingStatus();
				if (clientAccountingStatus == ClientAccountingStatus.Immediate)
				{
					return true;
				}
			}

			//
			// Check if PostImmediate is allowed by AD_Client configuration
			final I_AD_Client client = getAD_Client();
			final boolean allowPosting = client.isPostImmediate();
			return allowPosting;
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

		//
		// Case: we are running on Client side
		// => post it only if ClientAccouting is enabled
		final boolean isClient = Ini.isClient();
		if (isClient)
		{
			return postingService.isClientAccountingEnabled();
		}
		// Case: we are running on Server side
		// => always post without Server because we are THE server
		else
		{
			return true;
		}
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

	private final void setPostedError(final String postedErrorMessage)
	{
		if (postedErrorMessage == null)
		{
			_postedException = null;
		}
		else
		{
			_postedException = new PostingExecutionException(postedErrorMessage);
			_posted = false;
		}
	}

	private final void setPostedError(final Exception postedException)
	{
		if (postedException == null)
		{
			_postedException = null;
		}
		else
		{
			_postedException = PostingExecutionException.wrapIfNeeded(postedException);
			_posted = false;
		}
	}

	/**
	 * Method called when the posting is complete (with or without errors).
	 *
	 * This method will set the {@link #isPosted()} flag and it will also throw the posting exception in case is configured to do so
	 *
	 * @throws PostingExecutionException in case there was a posting exception and {@link #isFailOnError()}
	 */
	private final void postingComplete()
	{
		//
		// Set the isPosted flag
		_posted = _postedException == null;

		//
		// Fail on error if needed
		if (!isFailOnError())
		{
			return;
		}
		if (_postedException == null)
		{
			return;
		}
		throw _postedException;
	}

	/**
	 * Execute the posting after given transaction is commited.
	 * If the transaction was already commited, the posting is executed right away (synchronously).
	 *
	 * @param trxName
	 * @param postRunnable runnable that shall do the actual posting; it is assumed that the runnable WILL NOT throw any exceptions.
	 */
	private final void postAfterTrxCommit(final String trxName, final Runnable postRunnable)
	{
		Check.assumeNotNull(postRunnable, "runnable not null");

		//
		// Case: we are running in a transaction.
		if (!trxManager.isNull(trxName))
		{
			trxManager.getTrxListenerManager(trxName)
					.newEventListener(TrxEventTiming.AFTER_COMMIT)
					.invokeMethodJustOnce(false) // invoking the method on *every* commit, because that's how it was and I can't check now if it's really needed
					.registerHandlingMethod(innerTrx -> postRunnable.run());

			// TODO figure out what shall be the posting status in this case,
			// or how shall be tell the caller that we don't know the status because the posting will be done async,
			// when the transaction will be completed
		}
		//
		// Case: we are running out of transaction
		else
		{
			postRunnable.run();
		}
	}
}
