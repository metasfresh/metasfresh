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
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;

import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.logging.LogManager;

public class MCAdvComDoc extends X_C_AdvComDoc implements DocAction
{
	private static final Logger logger = LogManager.getLogger(MCAdvComDoc.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 6992760220646838350L;

	public MCAdvComDoc(final Properties ctx, final int C_AdvComDoc_ID, final String trxName)
	{
		super(ctx, C_AdvComDoc_ID, trxName);
	}

	public MCAdvComDoc(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Returns the value of {@link I_C_AdvComDoc#COLUMNNAME_DateFact_Override} or, if DateFact_Override is <code>null</code>, the value of {@link I_C_AdvComDoc#COLUMNNAME_DateFact}.
	 */
	@Override
	public Timestamp getDateFact_Override()
	{
		final Timestamp result = super.getDateFact_Override();
		if (result == null)
		{
			return super.getDateFact();
		}
		return result;
	}

	@Override
	public String toString()
	{

		final StringBuffer sb = new StringBuffer();
		sb.append("MCAdvComDoc[Id=");
		sb.append(get_ID());
		sb.append(", DocumentNo_Ref=");
		sb.append(getDocumentNo_Ref());
		sb.append("]");

		return sb.toString();
	}

	@Cached(keyProperties = { "AD_Table_ID", "Record_ID" })
	public PO retrievePO()
	{
		final int tableId = getAD_Table_ID();
		final int recordId = getRecord_ID();

		final PO po = MTable.get(getCtx(), tableId).getPO(recordId, get_TrxName());

		return po;
	}

	/**
	 * 
	 * @param trigger a commission trigger (currently this could be a C_Order or C_Invoice). Important: {@link InterfaceWrapperHelper#getPO()} must return a {@link PO} for the given object.
	 * @return
	 */
	public static MCAdvComDoc retrieveForTrigger(final Object trigger)
	{
		final PO triggerPO = InterfaceWrapperHelper.getPO(trigger);
		Check.assume(triggerPO != null, trigger + " is expected to be a PO or a proxy created by InterfaceWrapperHelper");

		final Properties ctx = InterfaceWrapperHelper.getCtx(trigger);
		final String trxName = InterfaceWrapperHelper.getTrxName(trigger);

		final String wc = I_C_AdvComDoc.COLUMNNAME_AD_Table_ID + "=? AND " + I_C_AdvComDoc.COLUMNNAME_Record_ID + "=?";

		return new Query(ctx, I_C_AdvComDoc.Table_Name, wc, trxName)
				.setParameters(triggerPO.get_Table_ID(), triggerPO.get_ID())
				.setOnlyActiveRecords(false) // in this case, we also load inactive records!!
				.firstOnly();
	}

	public static MCAdvComDoc retrieveForAllocLine(final I_C_AllocationLine allocLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(allocLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(allocLine);

		final String wc = I_C_AdvComDoc.COLUMNNAME_C_AllocationLine_ID + "=?";

		return new Query(ctx, I_C_AdvComDoc.Table_Name, wc, trxName)
				.setParameters(allocLine.getC_AllocationLine_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_AdvComDoc.COLUMNNAME_C_AdvComDoc_ID)
				.firstOnly();
	}

	/**
	 * Note: we can't delete the record, because it is commission relevant
	 * 
	 * @param trigger a commission trigger (currently this could be a C_Order or C_Invoice). Important: {@link InterfaceWrapperHelper#getPO()} must return a {@link PO} for the given object.
	 */
	public static void deactivateIfExist(final Object trigger)
	{
		final MCAdvComDoc existingRecord = retrieveForTrigger(trigger);

		if (existingRecord != null)
		{
			existingRecord.setDateFact_Override(null);
			existingRecord.setC_AllocationLine_ID(0);
			existingRecord.setIsActive(false);
			existingRecord.saveEx();
		}
	}

	/**
	 * Method creates or updates a commission doc record for the given trigger (i.e. paid invoice or prepay order) and the given allocation line.
	 * 
	 * If there already exists a commission doc record for the given alloc line, but it references a different trigger (i.e. a prepay order when this method is called with an invoice), then the method
	 * returns null.
	 * 
	 * If there already exists a commission doc record for the given trigger, it is updated with the given alloc line.
	 * 
	 * If no commission doc record exists yet for the given trigger and alloc line, then one is created.
	 * 
	 * @param trigger a commission trigger (currently this could be a C_Order or C_Invoice). Important: {@link InterfaceWrapperHelper#getPO()} must return a {@link PO} for the given object.
	 * @param allocLine
	 * @return the record that was create or updated or <code>null</code>.
	 */
	public static MCAdvComDoc createOrUpdate(final Object trigger, final I_C_AllocationLine allocLine)
	{
		final MCAdvComDoc comDoc;

		final PO triggerPO = InterfaceWrapperHelper.getPO(trigger);
		Check.assume(triggerPO != null, trigger + " is expected to be a PO or a proxy created by InterfaceWrapperHelper");

		final Properties ctx = InterfaceWrapperHelper.getCtx(trigger);
		final String trxName = InterfaceWrapperHelper.getTrxName(trigger);

		final MCAdvComDoc comDocForAllocLine = retrieveForAllocLine(allocLine);
		if (comDocForAllocLine != null)
		{
			if (comDocForAllocLine.getAD_Table_ID() == triggerPO.get_Table_ID()
					&& comDocForAllocLine.getRecord_ID() == triggerPO.get_ID())
			{
				comDoc = comDocForAllocLine;
			}
			else
			{
				// There is an existing comDoc for 'allocLine', but it references a different document.
				// This happens if this method is called with a paid invoice, when the actual trigger is a prepay order.
				return null;
			}
		}
		else
		{
			// there is no commission doc for 'allocLine', but there might be a deactivated one for 'trigger'.
			MCAdvComDoc comDocForTrigger = retrieveForTrigger(trigger);

			if (comDocForTrigger == null)
			{
				comDocForTrigger = new MCAdvComDoc(ctx, 0, trxName);
				comDocForTrigger.setAD_Table_ID(triggerPO.get_Table_ID());
				comDocForTrigger.setRecord_ID(triggerPO.get_ID());
				comDocForTrigger.setClientOrg(triggerPO);
			}
			comDoc = comDocForTrigger;
		}

		comDoc.setIsActive(true);

		final Timestamp dateDoc = Services.get(ICommissionFactCandBL.class).retrieveDateDocOfPO(triggerPO);
		comDoc.setDateDoc_Ref(dateDoc);
		comDoc.setDocumentNo_Ref(triggerPO.get_ValueAsString("DocumentNo"));
		comDoc.setC_DocType_Ref_ID(triggerPO.get_ValueAsInt("C_DocType_ID"));

		Check.assume(allocLine.getC_AllocationLine_ID() > 0, allocLine + " is expected to have C_AllocationLine_ID>0");
		comDoc.setC_AllocationLine_ID(allocLine.getC_AllocationLine_ID());
		comDoc.setDateFact(allocLine.getDateTrx());

		comDoc.setIsDateFactOverridable(true);

		if (!X_C_AdvComDoc.DOCSTATUS_Fertiggestellt.equals(comDoc.getDocStatus()))
		{
			if (!comDoc.processIt(X_C_AdvComDoc.DOCACTION_Fertigstellen))
			{
				throw new AdempiereException("Unable to complete " + comDoc);
			}
		}

		try
		{
			comDoc.saveEx();
		}
		catch (final DBUniqueConstraintException e)
		{
			assert retrieveForTrigger(trigger) != null;
			MCAdvComDoc.logger.info("Record for " + trigger + " has already been created in a concurrent transaction");
		}

		return comDoc;
	}

	@Override
	public boolean approveIt()
	{
		log.info(toString());
		setIsApproved(true);
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

		setDocAction(X_C_AdvComDoc.DOCACTION_Nichts);

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
			if (!DocAction.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}
		log.debug("Completed: {}", this);

		// User Validation
		final String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		setProcessed(true);
		setDocAction(X_C_AdvComDoc.DOCACTION_Reaktivieren);
		return DocAction.STATUS_Completed;
	} // completeIt

	/**
	 * Create PDF
	 * 
	 * @return File or null
	 */
	@Override
	public File createPDF()
	{
		return null;
	} // getPDF

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
		return Msg.getElement(getCtx(), I_C_AdvComDoc.COLUMNNAME_C_AdvComDoc_ID) + " " + getDocumentNo();
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
		setDocAction(X_C_AdvComDoc.DOCACTION_Vorbereiten);
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
			return DocAction.STATUS_Invalid;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}
		//
		m_justPrepared = true;
		if (!X_C_AdvComDoc.DOCACTION_Fertigstellen.equals(getDocAction()))
		{
			setDocAction(X_C_AdvComDoc.DOCACTION_Fertigstellen);
		}
		return DocAction.STATUS_InProgress;
	} // prepareIt

	/**************************************************************************
	 * Process document
	 * 
	 * @param processAction document action
	 * @return true if performed
	 */
	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		final DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	} // processIt

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
		setDocAction(X_C_AdvComDoc.DOCACTION_Fertigstellen);

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
		setIsApproved(false);
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

		if (X_C_AdvComDoc.DOCSTATUS_Geschlossen.equals(getDocStatus())
				|| X_C_AdvComDoc.DOCSTATUS_Rueckgaengig.equals(getDocStatus())
				|| X_C_AdvComDoc.DOCSTATUS_Storniert.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}

		setDateFact_Override(null);
		setIsDateFactOverridable(false);

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(X_C_AdvComDoc.DOCACTION_Nichts);
		return true;
	} // voidIt

	@Override
	public String getDocumentNo()
	{
		return Integer.toString(get_ID());
	}

}
