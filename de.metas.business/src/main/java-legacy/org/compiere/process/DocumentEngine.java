/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.process;

import java.io.File;
import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.acct.api.IPostingRequestBuilder.PostImmediate;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.logging.LogManager;

/**
 * Document Action Engine
 *
 * @author Jorg Janke
 * @author Karsten Thiemann FR [ 1782412 ]
 * @author victor.perez@e-evolution.com www.e-evolution.com FR [ 1866214 ] http://sourceforge.net/tracker/index.php?func=detail&aid=1866214&group_id=176962&atid=879335
 * @version $Id: DocumentEngine.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 */
public class DocumentEngine implements DocAction
{
	/**
	 * Doc Engine (Drafted)
	 *
	 * @param po document
	 */
	public DocumentEngine(final DocAction po)
	{
		this(po, STATUS_Drafted);
	}	// DocActionEngine

	/**
	 * Doc Engine
	 *
	 * @param po document
	 * @param docStatus initial document status
	 */
	public DocumentEngine(final DocAction po, final String docStatus)
	{
		m_document = po;
		if (docStatus != null)
		{
			m_status = docStatus;
		}
	}	// DocActionEngine

	/** Persistent Document */
	private final DocAction m_document;
	/** Document Status */
	private String m_status = STATUS_Drafted;
	/** Process Message */
	private String m_message = null;
	/** Actual Doc Action */
	private String m_action = null;

	/** Logger */
	private static final transient Logger logger = LogManager.getLogger(DocumentEngine.class);

	/**
	 * Get Doc Status
	 *
	 * @return document status
	 */
	@Override
	public String getDocStatus()
	{
		return m_status;
	}	// getDocStatus

	/**
	 * Set Doc Status - Ignored
	 *
	 * @param ignored Status is not set directly
	 * @see org.compiere.process.DocAction#setDocStatus(String)
	 */
	@Override
	public void setDocStatus(final String ignored)
	{
	}	// setDocStatus

	/**
	 * Document is Drafted
	 *
	 * @return true if drafted
	 */
	public boolean isDrafted()
	{
		return STATUS_Drafted.equals(m_status);
	}	// isDrafted

	/**
	 * Document is Invalid
	 *
	 * @return true if Invalid
	 */
	public boolean isInvalid()
	{
		return STATUS_Invalid.equals(m_status);
	}	// isInvalid

	/**
	 * Document is In Progress
	 *
	 * @return true if In Progress
	 */
	public boolean isInProgress()
	{
		return STATUS_InProgress.equals(m_status);
	}	// isInProgress

	/**
	 * Document is Approved
	 *
	 * @return true if Approved
	 */
	public boolean isApproved()
	{
		return STATUS_Approved.equals(m_status);
	}	// isApproved

	/**
	 * Document is Not Approved
	 *
	 * @return true if Not Approved
	 */
	public boolean isNotApproved()
	{
		return STATUS_NotApproved.equals(m_status);
	}	// isNotApproved

	/**
	 * Document is Waiting Payment or Confirmation
	 *
	 * @return true if Waiting Payment
	 */
	public boolean isWaiting()
	{
		return STATUS_WaitingPayment.equals(m_status)
				|| STATUS_WaitingConfirmation.equals(m_status);
	}	// isWaitingPayment

	/**
	 * Document is Completed
	 *
	 * @return true if Completed
	 */
	public boolean isCompleted()
	{
		return STATUS_Completed.equals(m_status);
	}	// isCompleted

	/**
	 * Document is Reversed
	 *
	 * @return true if Reversed
	 */
	public boolean isReversed()
	{
		return STATUS_Reversed.equals(m_status);
	}	// isReversed

	/**
	 * Document is Closed
	 *
	 * @return true if Closed
	 */
	public boolean isClosed()
	{
		return STATUS_Closed.equals(m_status);
	}	// isClosed

	/**
	 * Document is Voided
	 *
	 * @return true if Voided
	 */
	public boolean isVoided()
	{
		return STATUS_Voided.equals(m_status);
	}	// isVoided

	/**
	 * Document Status is Unknown
	 *
	 * @return true if unknown
	 */
	public boolean isUnknown()
	{
		return STATUS_Unknown.equals(m_status) ||
				!(isDrafted() || isInvalid() || isInProgress() || isNotApproved()
						|| isApproved() || isWaiting() || isCompleted()
						|| isReversed() || isClosed() || isVoided());
	}	// isUnknown

	/**
	 * Process actual document. Checks if user (document) action is valid and then process action Calls the individual actions which call the document action
	 *
	 * @param processAction document action based on workflow
	 * @param docAction document action based on document
	 * @return true if performed
	 */
	public boolean processIt(final String processAction, final String docAction)
	{
		m_message = null;
		m_action = null;
		// Std User Workflows - see MWFNodeNext.isValidFor

		if (isValidAction(processAction))
		{
			m_action = processAction;
		}
		else if (isValidAction(docAction))
		{
			m_action = docAction;
		}
		else if (processAction.equals(ACTION_None)
				|| docAction.equals(ACTION_None))
		{
			if (m_document != null)
			{
				m_document.get_Logger().debug("**** No Action (Prc={}/Doc={}) {}", processAction, docAction, m_document);
			}
			return true;
		}
		else
		{
			throw new IllegalStateException("Status=" + getDocStatus()
					+ " - Invalid Actions: Process=" + processAction + ", Doc=" + docAction);
		}
		if (m_document != null)
		{
			m_document.get_Logger().debug("**** Action={} (Prc={}/Doc={}) {}", m_action, processAction, docAction, m_document);
		}
		final boolean success = processIt(m_action);
		if (m_document != null)
		{
			m_document.get_Logger().debug("**** Action={} - Success={}", m_action, success);
		}
		return success;
	}	// process

	/**
	 * Process actual document - do not call directly. Calls the individual actions which call the document action
	 *
	 * @param action document action
	 * @return true if performed
	 */
	@Override
	public boolean processIt(final String action)
	{
		final LockOwner lockOwner = LockOwner.forOwnerName(DocumentEngine.class.getSimpleName() + "#processIt");
		final ILockManager lockManager = Services.get(ILockManager.class);

		final ILock lock = lockManager
				.lock()
				.setOwner(lockOwner)
				.setAllowAdditionalLocks(ILockCommand.AllowAdditionalLocks.FOR_DIFFERENT_OWNERS) // task 09849
				.setFailIfAlreadyLocked(true) // fail if the record was already locked
				.setAutoCleanup(true) // remove possible stale locks, e.g. after a client crash
				.addRecordByModel(m_document)
				.acquire();
		logger.debug("Acquired Lock {}", lock);

		try (final ILockAutoCloseable autoCloseableLock = lock.asAutoCloseable())
		{
			return processIt0(action);
		}
	}

	private boolean processIt0(final String action)
	{
		m_message = null;
		m_action = action;
		//
		if (ACTION_Unlock.equals(m_action))
		{
			return unlockIt();
		}
		if (ACTION_Invalidate.equals(m_action))
		{
			return invalidateIt();
		}
		if (ACTION_Prepare.equals(m_action))
		{
			return STATUS_InProgress.equals(prepareIt());
		}
		if (ACTION_Approve.equals(m_action))
		{
			return approveIt();
		}
		if (ACTION_Reject.equals(m_action))
		{
			return rejectIt();
		}
		if (ACTION_Complete.equals(m_action) || ACTION_WaitComplete.equals(m_action))
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
		if (ACTION_ReActivate.equals(m_action))
		{
			return reActivateIt();
		}
		if (ACTION_Reverse_Accrual.equals(m_action))
		{
			return reverseAccrualIt();
		}
		if (ACTION_Reverse_Correct.equals(m_action))
		{
			final boolean ok = reverseCorrectIt();
			return ok;
		}
		if (ACTION_Close.equals(m_action))
		{
			return closeIt();
		}
		if (ACTION_Void.equals(m_action))
		{
			return voidIt();
		}
		if (ACTION_Post.equals(m_action))
		{
			postIt(PostImmediate.Yes);
			return true; // return true because the posting request was enqueued
		}
		//
		return false;
	}	// processDocument

	/**
	 * Unlock Document. Status: Drafted
	 *
	 * @return true if success
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt()
	{
		if (!isValidAction(ACTION_Unlock))
		{
			return false;
		}
		if (m_document != null)
		{
			if (m_document.unlockIt())
			{
				m_status = STATUS_Drafted;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Drafted;
		return true;
	}	// unlockIt

	/**
	 * Invalidate Document. Status: Invalid
	 *
	 * @return true if success
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt()
	{
		if (!isValidAction(ACTION_Invalidate))
		{
			return false;
		}
		if (m_document != null)
		{
			if (m_document.invalidateIt())
			{
				m_status = STATUS_Invalid;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Invalid;
		return true;
	}	// invalidateIt

	/**
	 * Process Document. Status is set by process
	 *
	 * @return new status (In Progress or Invalid)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt()
	{
		if (!isValidAction(ACTION_Prepare))
		{
			return m_status;
		}
		if (m_document != null)
		{
			m_status = m_document.prepareIt();
			m_document.setDocStatus(m_status);
		}
		return m_status;
	}	// processIt

	/**
	 * Approve Document. Status: Approved
	 *
	 * @return true if success
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt()
	{
		if (!isValidAction(ACTION_Approve))
		{
			return false;
		}
		if (m_document != null)
		{
			if (m_document.approveIt())
			{
				m_status = STATUS_Approved;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Approved;
		return true;
	}	// approveIt

	/**
	 * Reject Approval. Status: Not Approved
	 *
	 * @return true if success
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt()
	{
		if (!isValidAction(ACTION_Reject))
		{
			return false;
		}
		if (m_document != null)
		{
			if (m_document.rejectIt())
			{
				m_status = STATUS_NotApproved;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_NotApproved;
		return true;
	}	// rejectIt

	/**
	 * Complete Document. Status is set by process
	 *
	 * @return new document status (Complete, In Progress, Invalid, Waiting ..)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt()
	{
		if (!isValidAction(ACTION_Complete))
		{
			return m_status;
		}
		if (m_document != null)
		{
			m_status = m_document.completeIt();
			m_document.setDocStatus(m_status);
		}
		return m_status;
	}	// completeIt

	/**
	 * Post Document Does not change status
	 */
	private final void postIt(final PostImmediate postImmediate)
	{
		// no document? shall not happen but ignore it for now
		if (m_document == null)
		{
			return;
		}

		// Make sure the Post action is supported by this document
		if (!isValidAction(ACTION_Post))
		{
			return;
		}

		// Make sure document is saved before we are asking to be posted
		InterfaceWrapperHelper.save(m_document);

		Services.get(IPostingService.class)
				.newPostingRequest()
				.setDocument(m_document)
				.setForce(true)
				.setPostImmediate(postImmediate)
				.setFailOnError(false) // backward compatibility
				.postIt();
	}	// postIt

	/**
	 * Void Document. Status: Voided
	 *
	 * @return true if success
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt()
	{
		if (!isValidAction(ACTION_Void))
		{
			return false;
		}
		if (m_document != null)
		{
			if (m_document.voidIt())
			{
				m_status = STATUS_Voided;

				if (STATUS_Reversed.equals(m_document.getDocStatus()))
				{
					// Case: user asked for voiding this document but the document decided that it will be a reversal instead
					// so we need to accept that status
					// else, we will end with a document which is marked as Voided and which has Fact_Accts => NOK
				}
				else
				{
					m_document.setDocStatus(m_status);
				}
				return true;
			}
			return false;
		}
		m_status = STATUS_Voided;
		return true;
	}	// voidIt

	/**
	 * Close Document. Status: Closed
	 *
	 * @return true if success
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt()
	{
		if (m_document != null 	// orders can be closed any time
				&& m_document.get_Table_ID() == I_C_Order.Table_ID)
		{
			;
		}
		else if (!isValidAction(ACTION_Close))
		{
			return false;
		}
		if (m_document != null)
		{
			if (m_document.closeIt())
			{
				m_status = STATUS_Closed;
				m_document.setDocStatus(m_status);

				// task 09243: update doc status in the fact accounts of the document
				Services.get(IFactAcctDAO.class).updateDocStatusForDocument(m_document, m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Closed;
		return true;
	}	// closeIt

	/**
	 * Reverse Correct Document. Status: Reversed
	 *
	 * @return true if success
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		if (!isValidAction(ACTION_Reverse_Correct))
		{
			return false;
		}
		if (m_document != null)
		{
			if (m_document.reverseCorrectIt())
			{
				m_status = STATUS_Reversed;
				m_document.setDocStatus(m_status);

				// task 09243: update doc status in the fact accounts of the document
				Services.get(IFactAcctDAO.class).updateDocStatusForDocument(m_document, m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Reversed;
		return true;
	}	// reverseCorrectIt

	/**
	 * Reverse Accrual Document. Status: Reversed
	 *
	 * @return true if success
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		if (!isValidAction(ACTION_Reverse_Accrual))
		{
			return false;
		}
		if (m_document != null)
		{
			if (m_document.reverseAccrualIt())
			{
				m_status = STATUS_Reversed;
				m_document.setDocStatus(m_status);

				// task 09243: update doc status in the fact accounts of the document
				Services.get(IFactAcctDAO.class).updateDocStatusForDocument(m_document, m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Reversed;
		return true;
	}	// reverseAccrualIt

	/**
	 * Re-activate Document. Status: In Progress
	 *
	 * @return true if success
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt()
	{
		if (!isValidAction(ACTION_ReActivate))
		{
			return false;
		}
		if (m_document != null)
		{
			if (m_document.reActivateIt())
			{
				m_status = STATUS_InProgress;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_InProgress;
		return true;
	}	// reActivateIt

	/**
	 * Set Document Status to new Status
	 *
	 * @param newStatus new status
	 */
	void setStatus(final String newStatus)
	{
		m_status = newStatus;
	}	// setStatus

	/**************************************************************************
	 * Get Action Options based on current Status
	 *
	 * @return array of actions
	 */
	public String[] getActionOptions()
	{
		if (isInvalid())
		{
			return new String[] { ACTION_Prepare, ACTION_Invalidate,
					ACTION_Unlock, ACTION_Void };
		}

		if (isDrafted())
		{
			return new String[] { ACTION_Prepare, ACTION_Invalidate, ACTION_Complete,
					ACTION_Unlock, ACTION_Void };
		}

		if (isInProgress() || isApproved())
		{
			return new String[] { ACTION_Complete, ACTION_WaitComplete,
					ACTION_Approve, ACTION_Reject,
					ACTION_Unlock, ACTION_Void, ACTION_Prepare };
		}

		if (isNotApproved())
		{
			return new String[] { ACTION_Reject, ACTION_Prepare,
					ACTION_Unlock, ACTION_Void };
		}

		if (isWaiting())
		{
			return new String[] { ACTION_Complete, ACTION_WaitComplete,
					ACTION_ReActivate, ACTION_Void, ACTION_Close };
		}

		if (isCompleted())
		{
			return new String[] { ACTION_Close, ACTION_ReActivate,
					ACTION_Reverse_Accrual, ACTION_Reverse_Correct,
					ACTION_Post, ACTION_Void };
		}

		if (isClosed())
		{
			return new String[] { ACTION_Post, ACTION_ReOpen };
		}

		if (isReversed() || isVoided())
		{
			return new String[] { ACTION_Post };
		}

		return new String[] {};
	}	// getActionOptions

	/**
	 * Is The Action Valid based on current state
	 *
	 * @param action action
	 * @return true if valid
	 */
	public boolean isValidAction(final String action)
	{
		final String[] options = getActionOptions();
		for (int i = 0; i < options.length; i++)
		{
			if (options[i].equals(action))
			{
				return true;
			}
		}
		return false;
	}	// isValidAction

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_message;
	}	// getProcessMsg

	/**
	 * Get Process Message
	 *
	 * @param msg clear text error message
	 */
	public void setProcessMsg(final String msg)
	{
		m_message = msg;
	}	// setProcessMsg

	/** Document Exception Message */
	private static String EXCEPTION_MSG = "Document Engine is no Document";

	/*************************************************************************
	 * Get Summary
	 *
	 * @return throw exception
	 */
	@Override
	public String getSummary()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * Get Document No
	 *
	 * @return throw exception
	 */
	@Override
	public String getDocumentNo()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * Get Document Info
	 *
	 * @return throw exception
	 */
	@Override
	public String getDocumentInfo()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * Get Document Owner
	 *
	 * @return throw exception
	 */
	@Override
	public int getDoc_User_ID()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * Get Document Currency
	 *
	 * @return throw exception
	 */
	@Override
	public int getC_Currency_ID()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * Get Document Approval Amount
	 *
	 * @return throw exception
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * Get Document Client
	 *
	 * @return throw exception
	 */
	@Override
	public int getAD_Client_ID()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	@Override
	public I_AD_Client getAD_Client()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * Get Document Organization
	 *
	 * @return throw exception
	 */
	@Override
	public int getAD_Org_ID()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * Get Doc Action
	 *
	 * @return Document Action
	 */
	@Override
	public String getDocAction()
	{
		return m_action;
	}

	/**
	 * Save Document
	 *
	 * @return throw exception
	 */
	@Override
	public boolean save()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * Get Context
	 *
	 * @return context
	 */
	@Override
	public Properties getCtx()
	{
		if (m_document != null)
		{
			return m_document.getCtx();
		}
		throw new IllegalStateException(EXCEPTION_MSG);
	}	// getCtx

	/**
	 * Get ID of record
	 *
	 * @return ID
	 */
	@Override
	public int get_ID()
	{
		if (m_document != null)
		{
			return m_document.get_ID();
		}
		throw new IllegalStateException(EXCEPTION_MSG);
	}	// get_ID

	/**
	 * Get AD_Table_ID
	 *
	 * @return AD_Table_ID
	 */
	@Override
	public int get_Table_ID()
	{
		if (m_document != null)
		{
			return m_document.get_Table_ID();
		}
		throw new IllegalStateException(EXCEPTION_MSG);
	}	// get_Table_ID

	/**
	 * Get Logger
	 *
	 * @return logger
	 */
	@Override
	public Logger get_Logger()
	{
		if (m_document != null)
		{
			return m_document.get_Logger();
		}
		throw new IllegalStateException(EXCEPTION_MSG);
	}	// get_Logger

	/**
	 * Get Transaction
	 *
	 * @return trx name
	 */
	@Override
	public String get_TrxName()
	{
		return null;
	}	// get_TrxName

	/**
	 * Always throws an <code>IllegalStateException</code>, because the interface's method declaration is intended for actual documents only.
	 *
	 * @throws IllegalStateException
	 */
	@Override
	public void set_TrxName(final String trxName)
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * CreatePDF
	 *
	 * @return null
	 */
	@Override
	public File createPDF()
	{
		return null;
	}

	@Override
	public boolean isActive()
	{
		if (m_document != null)
		{
			return m_document.isActive();
		}
		throw new IllegalStateException(EXCEPTION_MSG);
	}
}	// DocumentEnine
