package de.metas.commission.model;

/*
 * #%L
 * de.metas.commission.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.Msg;

public class MCSponsorCond extends X_C_Sponsor_Cond implements IDocument
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6992760220646838350L;

	public MCSponsorCond(final Properties ctx, final int C_AdvComDoc_ID, final String trxName)
	{
		super(ctx, C_AdvComDoc_ID, trxName);
	}

	public MCSponsorCond(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{

		final StringBuffer sb = new StringBuffer();
		sb.append("MCSponsorCond[Id=");
		sb.append(get_ID());
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Close Document.
	 * 
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		setDocAction(X_C_Sponsor_Cond.DOCACTION_Nichts);

		return true;
	} // closeIt

	/**
	 * Complete Document
	 * 
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			final String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		setProcessed(true);
		setDocAction(X_C_Sponsor_Cond.DOCACTION_Nichts); // completed docs can't be reactivated

		// User Validation
		final String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return IDocument.STATUS_Invalid;
		}

		return IDocument.STATUS_Completed;
	} // completeIt

	/**
	 * Create PDF file
	 * 
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(final File file)
	{
		return null;
	} // createPDF

	/**
	 * Get Document Approval Amount
	 * 
	 * @return amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return Env.ZERO;
	} // getApprovalAmt

	/**
	 * Get Document Currency
	 * 
	 * @return C_Currency_ID
	 */
	@Override
	public int getC_Currency_ID()
	{
		return 0;
	}

	/**
	 * Get Document Owner (Responsible)
	 * 
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getUpdatedBy();
	} // getDoc_User_ID

	/**
	 * Get Document Info
	 * 
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		return Msg.getElement(getCtx(), I_C_Sponsor_Cond.COLUMNNAME_C_Sponsor_Cond_ID) + " " + getDocumentNo();
	} // getDocumentInfo

	/**
	 * Get Process Message
	 * 
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	} // getProcessMsg

	/*************************************************************************
	 * Get Summary
	 * 
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		return sb.toString();
	} // getSummary

	/**
	 * Invalidate Document
	 * 
	 * @return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(X_C_Sponsor_Cond.DOCACTION_Vorbereiten);
		return true;
	} // invalidateIt

	/**
	 * Prepare Document
	 * 
	 * @return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}
		//
		m_justPrepared = true;
		if (!X_C_Sponsor_Cond.DOCACTION_Fertigstellen.equals(getDocAction()))
		{
			setDocAction(X_C_Sponsor_Cond.DOCACTION_Fertigstellen);
		}
		return IDocument.STATUS_InProgress;
	} // prepareIt

	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction);
	}

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	/**
	 * Re-activate
	 * 
	 * @return false
	 */
	@Override
	public boolean reActivateIt()
	{
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(false);
		setDocAction(X_C_Sponsor_Cond.DOCACTION_Fertigstellen);

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		return true;
	} // reActivateIt

	/**
	 * Reject Approval
	 * 
	 * @return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		log.info(toString());

		return true;
	} // rejectIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		return false;
	} // reverseAccrualIt

	/**
	 * Reverse Correction
	 * 
	 * @return false
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		return false;
	} // reverseCorrectionIt

	/**
	 * Unlock Document.
	 * 
	 * @return true if success
	 */
	@Override
	public boolean unlockIt()
	{
		log.info(toString());
		setProcessing(false);
		return true;
	} // unlockIt

	/**
	 * Void Document.
	 * 
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		if (X_C_Sponsor_Cond.DOCSTATUS_Geschlossen.equals(getDocStatus())
				|| X_C_Sponsor_Cond.DOCSTATUS_Rueckgaengig.equals(getDocStatus())
				|| X_C_Sponsor_Cond.DOCSTATUS_Storniert.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(X_C_Sponsor_Cond.DOCACTION_Nichts);
		return true;
	} // voidIt

	@Override
	public String getDocumentNo()
	{
		return Integer.toString(get_ID());
	}

	@Override
	public boolean approveIt()
	{
		return true;
	}

	@Override
	public File createPDF()
	{
		return null;
	}

}
