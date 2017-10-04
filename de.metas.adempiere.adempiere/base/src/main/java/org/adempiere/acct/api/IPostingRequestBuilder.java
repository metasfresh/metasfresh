package org.adempiere.acct.api;

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

import org.compiere.acct.PostingExecutionException;

import de.metas.adempiere.form.IClientUIInvoker;
import de.metas.document.engine.IDocument;

/**
 * Posting request builder: a helper class which assist the developer to Post a document.
 * 
 * This shall be the right and ONLY way to access the Document Posting API.
 * 
 * The standard use case of this interface is:
 * <ul>
 * <li>create a new instance of it using {@link IPostingService#newPostingRequest()}.
 * <li>configure it ({@link #setDocument(Object)}, {@link #setPostImmediate(PostImmediate)} etc)
 * <li>execute the request:
 * <ul>
 * <li>{@link #postIt()} - execute the posting as specified
 * <li>{@link #postItOnUI()} - delegates the execution of {@link #postIt()} to {@link IClientUIInvoker}. Use it if you want to add more UI options to {@link #postIt()} invocation (e.g. show a popup in
 * case of errors, show waiting cursor etc).
 * </ul>
 * </ul>
 * 
 * @author tsa
 *
 */
public interface IPostingRequestBuilder
{
	/**
	 * Default FailOnError option.
	 * 
	 * Because of backward compatibility, this is false now.
	 */
	boolean DEFAULT_FailOnError = false;

	/** Shall we post the document immediate? */
	enum PostImmediate
	{
		/** No, just enqueue it and it will be post later, when it times come */
		No,
		/** Yes, post it right away */
		Yes,
		/** Post it immediate only if the system is configured to do so. Else, just enqueue it */
		IfConfigured
	}

	/**
	 * Post it on UI
	 * 
	 * @return the Client UI invoker which when {@link IClientUIInvoker#invoke())ed will call #postIt().
	 */
	IClientUIInvoker postItOnUI();

	/**
	 * Post the document (i.e. execute this request).
	 */
	IPostingRequestBuilder postIt();

	/** @return true if document was posted */
	boolean isPosted();

	/** @return true if document was tried to be posted but an error was uncounted */
	boolean isError();

	/** @return posting exception or <code>null</code> if there was no error */
	PostingExecutionException getPostedException();

	/** @return posting error message or <code>null</code> if there was no error */
	String getPostedErrorMessage();

	/**
	 * Sets what do to in case an error is encounted while posting.
	 * 
	 * If true, the exception will be propagated. If false, it will be just logged but the execution will not fail. In this case the error will be accessible later by calling
	 * {@link #getPostedException()} or {@link #getPostedErrorMessage()}.
	 * 
	 * The default value is {@value #DEFAULT_FailOnError}.
	 * 
	 * @param failOnError true if the execution shall fail and the exception shall be propagated
	 */
	IPostingRequestBuilder setFailOnError(final boolean failOnError);

	/** Sets if we shall post the document immediate */
	IPostingRequestBuilder setPostImmediate(final PostImmediate postImmediate);

	/** Sets the processing context */
	IPostingRequestBuilder setContext(final Properties ctx, final String trxName);

	/** Sets the processing context's AD_Client_ID to be used */
	IPostingRequestBuilder setAD_Client_ID(int adClientId);

	/**
	 * Sets the document to be processed. i.e.
	 * <ul>
	 * <li>{@link #setDocument(int, int)}
	 * <li>{@link #setContext(Properties, String)}
	 * <li>{@link #setAD_Client_ID(int)}
	 * </ul>
	 * 
	 * @param document
	 */
	IPostingRequestBuilder setDocument(final IDocument document);

	/** see {@link #setDocument(IDocument)} */
	IPostingRequestBuilder setDocumentFromModel(final Object documentObj);

	/**
	 * Sets the document to be processed.
	 * 
	 * @param adTableId
	 * @param recordId
	 */
	IPostingRequestBuilder setDocument(final int adTableId, final int recordId);

	/**
	 * Sets if we shall do Force posting.
	 * 
	 * Force posting means that document shall be posted even if is currently locked (i.e. Processing flag is <code>true</code>).
	 * 
	 * @param force
	 */
	IPostingRequestBuilder setForce(final boolean force);

	/**
	 * Advice the document poster to NOT contact the server but do the posting right away.
	 * 
	 * This option shall be used when we want to directly post the document without server. Common use-cases are:
	 * <ul>
	 * <li>we are already on server side
	 * <li>we want do to Client Accounting
	 * <li>for some particular reason, we want to post the document directly from Client Application
	 * </ul>
	 * 
	 * WARNING: please handle this option with case, and use it only if you know what are you doing.
	 */
	IPostingRequestBuilder setPostWithoutServer();
}
