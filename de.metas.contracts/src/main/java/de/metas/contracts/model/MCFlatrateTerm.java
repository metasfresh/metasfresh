package de.metas.contracts.model;

/*
 * #%L
 * de.metas.contracts
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

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.Msg;

public class MCFlatrateTerm extends X_C_Flatrate_Term implements IDocument
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5991467188908320233L;

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	public MCFlatrateTerm(Properties ctx, int C_Flatrate_DataEntry_ID, String trxName)
	{
		super(ctx, C_Flatrate_DataEntry_ID, trxName);
	}

	public MCFlatrateTerm(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public boolean approveIt()
	{
		return true;
	} // approveIt

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

		setDocAction(DOCACTION_None);

		return true;
	} // closeIt

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

		// Note:
		// setting and saving the doc status here, because the model validator will search for invoice existing invoice
		// candidates what might be related to this term. To check this for a given candidate, this term's docstatus must be 'CO'.
		setDocStatus(DOCSTATUS_Completed);
		Check.assume(get_TrxName() != null, this + " has trxName!=null");
		saveEx();

		final String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return IDocument.STATUS_Invalid;
		}

		setProcessed(true);
		setDocAction(DOCACTION_Re_Activate);
		return IDocument.STATUS_Completed;
	}

	@Override
	public File createPDF()
	{
		return null;
	}

	@Override
	public BigDecimal getApprovalAmt()
	{
		return Env.ZERO;
	}

	@Override
	public int getDoc_User_ID()
	{
		return getUpdatedBy();
	} // getDoc_User_ID

	@Override
	public String getDocumentInfo()
	{
		return Msg.getElement(getCtx(), COLUMNNAME_C_Flatrate_Term_ID) + " " + getDocumentNo();
	} // getDocumentInfo


	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}

	@Override
	public String getSummary()
	{
		return getDocumentInfo();
	}

	@Override
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
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
		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}
		return IDocument.STATUS_InProgress;
	} // prepareIt

	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction);
	}

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
		setDocAction(DOCACTION_Complete);

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		return true;
	} // reActivateIt

	@Override
	public boolean rejectIt()
	{
		return true;
	}

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

		return true;
	} // reverseAccrualIt

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

		return true;
	}

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

	@Override
	public boolean voidIt()
	{
		return false;
	}

}
