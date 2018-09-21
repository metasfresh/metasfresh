package org.adempiere.acct.api.impl;

import java.util.Optional;

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

import org.adempiere.acct.api.IDocFactory;
import org.adempiere.acct.api.IPostingRequestBuilder;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.Adempiere;
import org.compiere.acct.Doc;
import org.compiere.acct.PostingExecutionException;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MAcctSchema;
import org.slf4j.Logger;

import de.metas.Profiles;
import de.metas.acct.posting.DocumentPostRequest;
import de.metas.acct.posting.DocumentPostingBusService;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.IClientUIInvoker;
import de.metas.document.engine.IDocument;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
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
	private TableRecordReference _documentRef = null;
	private PostImmediate _postImmediate = PostImmediate.IfConfigured;
	private boolean _postWithoutServer = false;
	private boolean _failOnError = DEFAULT_FailOnError;

	// Status
	private boolean _executed = false;
	private PostingExecutionException _postedException = null;
	private boolean _posted = false;

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
				.setRunnable(this::postIt);
	}

	@Override
	public final IPostingRequestBuilder postIt()
	{
		setExecuted();

		if (!postingService.isEnabled())
		{
			setPostedError("Accounting module is disabled");
			postingComplete();
			return this;
		}

		//
		// Check if we shall post the document immediately by checking PostImmediate option
		// If not, we will enqueue it.
		if (!isPostImmediate())
		{
			postIt_Enqueue();
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
			postAfterTrxCommit(this::postIt_Directly);
			return this;
		}

		//
		// Post it on server
		try
		{
			//
			// Post the document, after this transaction is committed, because
			// there is a big chance the document will not be accessible on server until the transaction is not finished,
			// so instead of asking the server to post it immediately, we will ask the server after the transaction is completed.
			postAfterTrxCommit(this::postIt_Enqueue);
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
		final int adClientId = getAD_Client_ID();
		final TableRecordReference documentRef = getDocumentRef();
		final boolean force = isForce();

		log.debug("Posting on server: {}", PostingRequestBuilder.this);

		final DocumentPostingBusService postingBusService = Adempiere.getBean(DocumentPostingBusService.class);
		postingBusService.postRequest(DocumentPostRequest.builder()
				.record(documentRef)
				.adClientId(adClientId)
				.force(force)
				.build());
	}

	/**
	 * Directly Post the document (without contacting the server!)
	 *
	 * @param trxName transaction to be used for posting; NOTE: it could be different from {@link #getTrxName()}.
	 */
	private final void postIt_Directly()
	{
		log.debug("Posting directly: {}", this);

		final IDocFactory docFactory = Services.get(IDocFactory.class);

		final Properties ctx = getCtx();
		final int adClientId = getAD_Client_ID();
		final TableRecordReference documentRef = getDocumentRef();
		final boolean force = isForce();

		try
		{
			final MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(ctx, adClientId);

			final Doc doc = docFactory.getOrNull(ctx, ass, documentRef);
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
		setDocumentRef(TableRecordReference.of(adTableId, recordId));
		return this;
	}

	private final void setDocumentRef(final TableRecordReference documentRef)
	{
		assertNotExecuted();
		_documentRef = documentRef;
	}

	private final TableRecordReference getDocumentRef()
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

		// Post without server if we are running the accouting service/server
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
	private final void postAfterTrxCommit(@NonNull final Runnable postRunnable)
	{
		final String trxName = getTrxName();

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
