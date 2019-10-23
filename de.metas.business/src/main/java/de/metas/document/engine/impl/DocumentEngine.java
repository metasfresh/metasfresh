/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General private License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General private License for more details. *
 * You should have received a copy of the GNU General private License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this private license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package de.metas.document.engine.impl;

import static de.metas.document.engine.IDocument.ACTION_Approve;
import static de.metas.document.engine.IDocument.ACTION_Close;
import static de.metas.document.engine.IDocument.ACTION_Complete;
import static de.metas.document.engine.IDocument.ACTION_Invalidate;
import static de.metas.document.engine.IDocument.ACTION_None;
import static de.metas.document.engine.IDocument.ACTION_Post;
import static de.metas.document.engine.IDocument.ACTION_Prepare;
import static de.metas.document.engine.IDocument.ACTION_ReActivate;
import static de.metas.document.engine.IDocument.ACTION_Reject;
import static de.metas.document.engine.IDocument.ACTION_Reverse_Accrual;
import static de.metas.document.engine.IDocument.ACTION_Reverse_Correct;
import static de.metas.document.engine.IDocument.ACTION_UnClose;
import static de.metas.document.engine.IDocument.ACTION_Unlock;
import static de.metas.document.engine.IDocument.ACTION_Void;
import static de.metas.document.engine.IDocument.ACTION_WaitComplete;
import static de.metas.document.engine.IDocument.STATUS_Approved;
import static de.metas.document.engine.IDocument.STATUS_Closed;
import static de.metas.document.engine.IDocument.STATUS_Completed;
import static de.metas.document.engine.IDocument.STATUS_Drafted;
import static de.metas.document.engine.IDocument.STATUS_InProgress;
import static de.metas.document.engine.IDocument.STATUS_Invalid;
import static de.metas.document.engine.IDocument.STATUS_NotApproved;
import static de.metas.document.engine.IDocument.STATUS_Reversed;
import static de.metas.document.engine.IDocument.STATUS_Voided;
import static de.metas.document.engine.IDocument.STATUS_WaitingConfirmation;
import static de.metas.document.engine.IDocument.STATUS_WaitingPayment;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.IPostingService;
import de.metas.acct.api.IPostingRequestBuilder.PostImmediate;
import de.metas.document.engine.IDocument;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Document Action Engine
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on original version of Jorg Janke
 */
/* package */class DocumentEngine
{
	public static DocumentEngine ofDocument(final IDocument document)
	{
		return new DocumentEngine(document);
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(DocumentEngine.class);
	private final transient ILockManager lockManager = Services.get(ILockManager.class);
	private final transient IPostingService postingService = Services.get(IPostingService.class);
	private final transient IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);

	private final IDocument _document;
	private String _docStatus;
	private String _docAction = null;

	private DocumentEngine(@NonNull final IDocument document)
	{
		_document = document;
		_docStatus = document.getDocStatus();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("document", _document)
				.add("docStatus", _docStatus)
				.add("docAction", _docAction)
				.toString();
	}

	private final IDocument getDocument()
	{
		return _document;
	}

	private String getDocStatus()
	{
		return _docStatus;
	}

	private void setDocStatusIntern(@NonNull final String newDocStatus)
	{
		_docStatus = newDocStatus;
	}

	private boolean isDrafted()
	{
		return STATUS_Drafted.equals(getDocStatus());
	}

	private boolean isInvalid()
	{
		return STATUS_Invalid.equals(getDocStatus());
	}

	private boolean isInProgress()
	{
		return STATUS_InProgress.equals(getDocStatus());
	}

	private boolean isApproved()
	{
		return STATUS_Approved.equals(getDocStatus());
	}

	private boolean isNotApproved()
	{
		return STATUS_NotApproved.equals(getDocStatus());
	}

	private boolean isWaiting()
	{
		final String docStatus = getDocStatus();
		return STATUS_WaitingPayment.equals(docStatus)
				|| STATUS_WaitingConfirmation.equals(docStatus);
	}

	private boolean isCompleted()
	{
		return STATUS_Completed.equals(getDocStatus());
	}

	private boolean isReversed()
	{
		return STATUS_Reversed.equals(getDocStatus());
	}

	private boolean isClosed()
	{
		return STATUS_Closed.equals(getDocStatus());
	}

	private boolean isVoided()
	{
		return STATUS_Voided.equals(getDocStatus());
	}

	/**
	 * Process actual document. Checks if user (document) action is valid and then process action Calls the individual actions which call the document action
	 *
	 * @param processAction document action based on workflow
	 * @param docAction document action based on document
	 * @return true if performed
	 */
	public boolean processIt(@NonNull final String processAction, @NonNull final String docAction)
	{
		setDocActionIntern(null);

		final IDocument document = getDocument();

		if (isValidDocAction(processAction))
		{
			setDocActionIntern(processAction);
		}
		else if (isValidDocAction(docAction))
		{
			setDocActionIntern(docAction);
		}
		else if (ACTION_None.equals(processAction) || ACTION_None.equals(docAction))
		{
			logger.debug("**** No Action (Prc={}/Doc={}) {}", processAction, docAction, document);
			return true;
		}
		else
		{
			throw new IllegalStateException("Status=" + getDocStatus() + " - Invalid Actions: Process=" + processAction + ", Doc=" + docAction);
		}

		final String docActionEffective = getDocAction();
		logger.debug("**** Action={} (Prc={}/Doc={}) {}", docActionEffective, processAction, docAction, document);
		final boolean success = processIt(docActionEffective);
		logger.debug("**** Action={} - Success={} {}", docActionEffective, success, document);
		return success;
	}

	/**
	 * Process actual document - do not call directly. Calls the individual actions which call the document action
	 *
	 * @param docAction document action
	 * @return true if performed
	 */
	private boolean processIt(final String docAction)
	{
		final LockOwner lockOwner = LockOwner.newOwner(DocumentEngine.class.getSimpleName() + "#processIt");

		final IDocument document = getDocument();

		final ILock lock = lockManager
				.lock()
				.setOwner(lockOwner)
				.setAllowAdditionalLocks(ILockCommand.AllowAdditionalLocks.FOR_DIFFERENT_OWNERS) // task 09849
				.setFailIfAlreadyLocked(true) // fail if the record was already locked
				.setAutoCleanup(true) // remove possible stale locks, e.g. after a client crash
				.addRecord(document.toTableRecordReference())
				.acquire();
		logger.debug("Acquired Lock {}", lock);

		try (final ILockAutoCloseable autoCloseableLock = lock.asAutoCloseable())
		{
			return processIt0(docAction);
		}
	}

	private boolean processIt0(final String docAction)
	{
		setDocActionIntern(docAction);
		//
		if (ACTION_Unlock.equals(docAction))
		{
			return unlockIt();
		}
		if (ACTION_Invalidate.equals(docAction))
		{
			return invalidateIt();
		}
		if (ACTION_Prepare.equals(docAction))
		{
			return STATUS_InProgress.equals(prepareIt());
		}
		if (ACTION_Approve.equals(docAction))
		{
			return approveIt();
		}
		if (ACTION_Reject.equals(docAction))
		{
			return rejectIt();
		}
		if (ACTION_Complete.equals(docAction) || ACTION_WaitComplete.equals(docAction))
		{
			String status = null;
			if (isDrafted() || isInvalid())		// prepare if not prepared yet
			{
				status = prepareIt();
				if (!STATUS_InProgress.equals(status))
				{
					return false;
				}
			}
			status = completeIt();

			// Post it if applies
			if (STATUS_Completed.equals(status))
			{
				postIt(PostImmediate.IfConfigured);
			}

			return STATUS_Completed.equals(status)
					|| STATUS_InProgress.equals(status)
					|| STATUS_WaitingPayment.equals(status)
					|| STATUS_WaitingConfirmation.equals(status);
		}
		if (ACTION_ReActivate.equals(docAction))
		{
			return reActivateIt();
		}
		if (ACTION_Reverse_Accrual.equals(docAction))
		{
			return reverseAccrualIt();
		}
		if (ACTION_Reverse_Correct.equals(docAction))
		{
			final boolean ok = reverseCorrectIt();
			return ok;
		}
		if (ACTION_Close.equals(docAction))
		{
			return closeIt();
		}
		if (ACTION_UnClose.equals(docAction))
		{
			return unCloseIt();
		}
		if (ACTION_Void.equals(docAction))
		{
			return voidIt();
		}
		if (ACTION_Post.equals(docAction))
		{
			postIt(PostImmediate.Yes);
			return true; // return true because the posting request was enqueued
		}
		//
		return false;
	}

	private boolean unlockIt()
	{
		if (!isValidDocAction(ACTION_Unlock))
		{
			return false;
		}

		final IDocument document = getDocument();
		if (document.unlockIt())
		{
			setDocStatusIntern(STATUS_Drafted);
			document.setDocStatus(STATUS_Drafted);
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean invalidateIt()
	{
		if (!isValidDocAction(ACTION_Invalidate))
		{
			return false;
		}

		final IDocument document = getDocument();
		if (document.invalidateIt())
		{
			setDocStatusIntern(STATUS_Invalid);
			document.setDocStatus(STATUS_Invalid);
			return true;
		}
		else
		{
			return false;
		}
	}

	private String prepareIt()
	{
		if (!isValidDocAction(ACTION_Prepare))
		{
			return getDocStatus();
		}

		final IDocument document = getDocument();
		final String newDocStatus = document.prepareIt();
		setDocStatusIntern(newDocStatus);
		document.setDocStatus(newDocStatus);
		return newDocStatus;
	}

	private boolean approveIt()
	{
		if (!isValidDocAction(ACTION_Approve))
		{
			return false;
		}

		final IDocument document = getDocument();
		if (document.approveIt())
		{
			setDocStatusIntern(STATUS_Approved);
			document.setDocStatus(STATUS_Approved);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Reject Approval. Status: Not Approved
	 *
	 * @return true if success
	 * @see de.metas.document.engine.IDocument#rejectIt()
	 */

	private boolean rejectIt()
	{
		if (!isValidDocAction(ACTION_Reject))
		{
			return false;
		}

		final IDocument document = getDocument();
		if (document.rejectIt())
		{
			setDocStatusIntern(STATUS_NotApproved);
			document.setDocStatus(STATUS_NotApproved);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Complete Document. Status is set by process
	 *
	 * @return new document status (Complete, In Progress, Invalid, Waiting ..)
	 * @see de.metas.document.engine.IDocument#completeIt()
	 */

	private String completeIt()
	{
		if (!isValidDocAction(ACTION_Complete))
		{
			return getDocStatus();
		}

		final IDocument document = getDocument();
		final String newDocStatus = document.completeIt();

		setDocStatusIntern(newDocStatus);
		document.setDocStatus(newDocStatus);

		return newDocStatus;
	}

	/**
	 * Post Document Does not change status
	 */
	private final void postIt(final PostImmediate postImmediate)
	{
		// Make sure the Post action is supported by this document
		if (!isValidDocAction(ACTION_Post))
		{
			return;
		}

		// Make sure document is saved before we are asking to be posted
		final IDocument document = getDocument();
		InterfaceWrapperHelper.save(document);

		postingService
				.newPostingRequest()
				.setDocument(document)
				.setForce(true)
				.setPostImmediate(postImmediate)
				.setFailOnError(false) // backward compatibility
				.postIt();
	}	// postIt

	/**
	 * Void Document. Status: Voided
	 *
	 * @return true if success
	 * @see de.metas.document.engine.IDocument#voidIt()
	 */

	private boolean voidIt()
	{
		if (!isValidDocAction(ACTION_Void))
		{
			return false;
		}

		final IDocument document = getDocument();
		if (document.voidIt())
		{
			final String newDocStatus = STATUS_Voided;
			setDocStatusIntern(newDocStatus);

			if (STATUS_Reversed.equals(document.getDocStatus()))
			{
				// Case: user asked for voiding this document but the document decided that it will be a reversal instead
				// so we need to accept that status
				// else, we will end with a document which is marked as Voided and which has Fact_Accts => NOK
			}
			else
			{
				document.setDocStatus(newDocStatus);
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Close Document. Status: Closed
	 *
	 * @return true if success
	 * @see de.metas.document.engine.IDocument#closeIt()
	 */

	private boolean closeIt()
	{
		final IDocument document = getDocument();
		if (document.get_Table_ID() == getTableId(I_C_Order.class)) // orders can be closed any time
		{
			
		}
		else if (!isValidDocAction(ACTION_Close))
		{
			return false;
		}

		if (document.closeIt())
		{
			final String newDocStatus = STATUS_Closed;
			setDocStatusIntern(newDocStatus);
			document.setDocStatus(newDocStatus);

			// task 09243: update doc status in the fact accounts of the document
			factAcctDAO.updateDocStatusForDocument(document);
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean unCloseIt()
	{
		if (!isValidDocAction(ACTION_UnClose))
		{
			return false;
		}

		final IDocument document = getDocument();
		document.unCloseIt();

		final String newDocStatus = STATUS_Completed;
		setDocStatusIntern(newDocStatus);
		document.setDocStatus(newDocStatus);

		// task 09243: update doc status in the fact accounts of the document
		factAcctDAO.updateDocStatusForDocument(document);
		return true;
	}

	/**
	 * Reverse Correct Document. Status: Reversed
	 *
	 * @return true if success
	 * @see de.metas.document.engine.IDocument#reverseCorrectIt()
	 */

	private boolean reverseCorrectIt()
	{
		if (!isValidDocAction(ACTION_Reverse_Correct))
		{
			return false;
		}
		final IDocument document = getDocument();
		if (document.reverseCorrectIt())
		{
			final String newDocStatus = STATUS_Reversed;
			setDocStatusIntern(newDocStatus);
			document.setDocStatus(newDocStatus);

			// task 09243: update doc status in the fact accounts of the document
			factAcctDAO.updateDocStatusForDocument(document);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Reverse Accrual Document. Status: Reversed
	 *
	 * @return true if success
	 * @see de.metas.document.engine.IDocument#reverseAccrualIt()
	 */

	private boolean reverseAccrualIt()
	{
		if (!isValidDocAction(ACTION_Reverse_Accrual))
		{
			return false;
		}

		final IDocument document = getDocument();
		if (document.reverseAccrualIt())
		{
			final String newDocStatus = STATUS_Reversed;
			setDocStatusIntern(newDocStatus);
			document.setDocStatus(newDocStatus);

			// task 09243: update doc status in the fact accounts of the document
			factAcctDAO.updateDocStatusForDocument(document);
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean reActivateIt()
	{
		if (!isValidDocAction(ACTION_ReActivate))
		{
			return false;
		}

		final IDocument document = getDocument();
		if (document.reActivateIt())
		{
			final String newDocStatus = STATUS_InProgress;
			setDocStatusIntern(newDocStatus);
			document.setDocStatus(newDocStatus);
			return true;
		}
		else
		{
			return false;
		}
	}

	/** @return all valid DocActions based on current DocStatus */
	private Set<String> getValidDocActionsForCurrentDocStatus()
	{
		if (isInvalid())
		{
			return ImmutableSet.of(ACTION_Prepare, ACTION_Invalidate, ACTION_Unlock, ACTION_Void);
		}

		if (isDrafted())
		{
			return ImmutableSet.of(ACTION_Prepare, ACTION_Invalidate, ACTION_Complete, ACTION_Unlock, ACTION_Void);
		}

		if (isInProgress() || isApproved())
		{
			return ImmutableSet.of(ACTION_Complete, ACTION_WaitComplete, ACTION_Approve, ACTION_Reject, ACTION_Unlock, ACTION_Void, ACTION_Prepare);
		}

		if (isNotApproved())
		{
			return ImmutableSet.of(ACTION_Reject, ACTION_Prepare, ACTION_Unlock, ACTION_Void);
		}

		if (isWaiting())
		{
			return ImmutableSet.of(ACTION_Complete, ACTION_WaitComplete, ACTION_ReActivate, ACTION_Void, ACTION_Close);
		}

		if (isCompleted())
		{
			return ImmutableSet.of(ACTION_Close, ACTION_ReActivate, ACTION_Reverse_Accrual, ACTION_Reverse_Correct, ACTION_Post, ACTION_Void);
		}

		if (isClosed())
		{
			return ImmutableSet.of(ACTION_Post, ACTION_UnClose);
		}

		if (isReversed() || isVoided())
		{
			return ImmutableSet.of(ACTION_Post);
		}

		return ImmutableSet.of();
	}

	/** @return true if given docAction is valid for current docStatus. */
	private boolean isValidDocAction(final String docAction)
	{
		final Set<String> availableDocActions = getValidDocActionsForCurrentDocStatus();
		return availableDocActions.contains(docAction);
	}

	private String getDocAction()
	{
		return _docAction;
	}

	private void setDocActionIntern(final String newDocAction)
	{
		_docAction = newDocAction;
	}
}
