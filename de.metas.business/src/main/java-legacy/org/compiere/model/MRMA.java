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
package org.compiere.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.adempiere.service.IOrderBL;
import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.engine.IDocActionBL;

/**
 *	RMA Model
 *
 *  @author Jorg Janke
 *  @version $Id: MRMA.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 *
 *  Modifications: Completed RMA functionality (Ashley Ramdass)
 */
public class MRMA extends X_M_RMA implements DocAction
{
	/**
	 *
	 */
	private static final long serialVersionUID = -3310525910645254261L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_RMA_ID id
	 *	@param trxName transaction
	 */
	public MRMA (Properties ctx, int M_RMA_ID, String trxName)
	{
		super (ctx, M_RMA_ID, trxName);
		if (M_RMA_ID == 0)
		{
		//	setName (null);
		//	setSalesRep_ID (0);
		//	setC_DocType_ID (0);
		//	setM_InOut_ID (0);
			setDocAction (DOCACTION_Complete);	// CO
			setDocStatus (DOCSTATUS_Drafted);	// DR
			setIsApproved(false);
			setProcessed (false);
		}
	}	//	MRMA

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MRMA (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRMA

	/** Lines					*/
	private MRMALine[]		m_lines = null;
	/** The Shipment			*/
	private MInOut			m_inout = null;

	/**
	 * 	Get Lines
	 *	@param requery requery
	 *	@return lines
	 */
	public MRMALine[] getLines (boolean requery)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		List<MRMALine> list = new Query(getCtx(), MRMALine.Table_Name, "M_RMA_ID=?", get_TrxName())
		.setParameters(new Object[]{getM_RMA_ID()})
		.setOrderBy(MRMALine.COLUMNNAME_Line)
		.list();

		m_lines = new MRMALine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines

	/**
	 * 	Get Shipment
	 *	@return shipment
	 */
	public MInOut getShipment()
	{
		if (m_inout == null && getInOut_ID() != 0)
			m_inout = new MInOut (getCtx(), getInOut_ID(), get_TrxName());
		return m_inout;
	}	//	getShipment

    /**
     * Get the original order on which the shipment/receipt defined is based upon.
     * @return order
     */
    public MOrder getOriginalOrder()
    {
       MInOut shipment = getShipment();
       if (shipment == null || shipment.getC_Order_ID() == 0)
       {
           return null;
       }
       return new MOrder(getCtx(), shipment.getC_Order_ID(), get_TrxName());
    }

    /**
     * Get the original invoice on which the shipment/receipt defined is based upon.
     * @return invoice
     */
    public MInvoice getOriginalInvoice()
    {
       MInOut shipment = getShipment();
       if (shipment == null)
       {
           return null;
       }

       int invId = 0;

       if (shipment.getC_Invoice_ID() != 0)
       {
           invId = shipment.getC_Invoice_ID();
       }
       else
       {
           String sqlStmt = "SELECT C_Invoice_ID FROM C_Invoice WHERE C_Order_ID=?";
           invId = DB.getSQLValueEx(null, sqlStmt, shipment.getC_Order_ID());
       }

       if (invId <= 0)
       {
           return null;
       }

       return new MInvoice(getCtx(), invId, get_TrxName());
    }

	/**
	 * 	Set M_InOut_ID
	 *	@param M_InOut_ID id
	 */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		setInOut_ID (M_InOut_ID);
		setC_Currency_ID(0);
		setAmt(Env.ZERO);
		setC_BPartner_ID(0);
		m_inout = null;
	}	//	setM_InOut_ID


	/**
	 * 	Get Document Info
	 *	@return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	//	getDocumentInfo

	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	@Override
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.error("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF


	/**
	 * 	Before Save
	 *	Set BPartner, Currency
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord)
			setC_Order_ID(0);
	    getShipment();
		//	Set BPartner
		if (getC_BPartner_ID() == 0)
		{
			if (m_inout != null)
				setC_BPartner_ID(m_inout.getC_BPartner_ID());
		}
		//	Set Currency
		if (getC_Currency_ID() == 0)
		{
			if (m_inout != null)
			{
				if (m_inout.getC_Order_ID() != 0)
				{
					MOrder order = new MOrder (getCtx(), m_inout.getC_Order_ID(), get_TrxName());
					setC_Currency_ID(order.getC_Currency_ID());
				}
				else if (m_inout.getC_Invoice_ID() != 0)
				{
					MInvoice invoice = new MInvoice (getCtx(), m_inout.getC_Invoice_ID(), get_TrxName());
					setC_Currency_ID(invoice.getC_Currency_ID());
				}
			}
		}

		// Verification whether Shipment/Receipt matches RMA for sales transaction
		if (m_inout != null && m_inout.isSOTrx() != isSOTrx())
		{
			throw new AdempiereException("RMA.IsSOTrx <> InOut.IsSOTrx");
		}

		return true;
	}	//	beforeSave


	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	@Override
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocActionBL.class).processIt(this, processAction); // task 09824
	}	//	process

	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success
	 */
	@Override
	public boolean unlockIt()
	{
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}	//	unlockIt

	/**
	 * 	Invalidate Document
	 * 	@return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		log.info("invalidateIt - " + toString());
		return true;
	}	//	invalidateIt

	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		MRMALine[] lines = getLines(false);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		
		for (MRMALine line : lines)
		{
			if (!line.checkQty())
			{
				m_processMsg = "@AmtReturned>Shipped@";
				return DocAction.STATUS_Invalid;
			}
		}

        // Updates Amount
		setAmt(getTotalAmount());

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}	//	prepareIt

	/**
	 * 	Approve Document
	 * 	@return true if success
	 */
	@Override
	public boolean  approveIt()
	{
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	//	approveIt

	/**
	 * 	Reject Approval
	 * 	@return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt

	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.debug("Completed: {}", this);
		//
		/*
		Flow for the creation of the credit memo document changed
        if (true)
		{
			m_processMsg = "Need to code creating the credit memo";
			return DocAction.STATUS_InProgress;
		}
        */

		//		Counter Documents
		MRMA counter = createCounterDoc();
		if (counter != null)
			m_processMsg = "@CounterDoc@: RMA=" + counter.getDocumentNo();

		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt

	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		/* No Document Date on RMA
		if (dt.isOverwriteDateOnComplete()) {
			setDate???(new Timestamp (System.currentTimeMillis()));
		}
		*/
		if (dt.isOverwriteSeqOnComplete()) 
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setTrxName(get_TrxName())
					.setDocumentModel(this)
					.setFailOnError(false)
					.build();
			if (value != null && value != IDocumentNoBuilder.NO_DOCUMENTNO)
				setDocumentNo(value);
		}
	}

	/**************************************************************************
	 * 	Create Counter Document
	 * 	@return InOut
	 */
	private MRMA createCounterDoc()
	{
		//	Is this a counter doc ?
		if (getRef_RMA_ID() > 0)
			return null;

		//	Org Must be linked to BPartner
		MOrg org = MOrg.get(getCtx(), getAD_Org_ID());
		int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(get_TrxName());
		if (counterC_BPartner_ID == 0)
			return null;
		//	Business Partner needs to be linked to Org
		MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
		int counterAD_Org_ID = bp.getAD_OrgBP_ID_Int();
		if (counterAD_Org_ID == 0)
			return null;

		//	Document Type
		int C_DocTypeTarget_ID = 0;
		MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(), getC_DocType_ID());
		if (counterDT != null)
		{
			log.debug(counterDT.toString());
			if (!counterDT.isCreateCounter() || !counterDT.isValid())
				return null;
			C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
		}
		else	//	indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(), getC_DocType_ID());
			log.debug("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
			if (C_DocTypeTarget_ID <= 0)
				return null;
		}

		//	Deep Copy
		MRMA counter = copyFrom(this, C_DocTypeTarget_ID, !isSOTrx(), true, get_TrxName());

		//
		counter.setAD_Org_ID(counterAD_Org_ID);
		counter.setC_BPartner_ID(counterC_BPartner_ID);
		counter.saveEx(get_TrxName());

		//	Update copied lines
		MRMALine[] counterLines = counter.getLines(true);
		for (int i = 0; i < counterLines.length; i++)
		{
			MRMALine counterLine = counterLines[i];
			counterLine.setClientOrg(counter);
			//
			counterLine.saveEx(get_TrxName());
		}

		log.debug(counter.toString());

		//	Document Action
		if (counterDT != null)
		{
			if (counterDT.getDocAction() != null)
			{
				counter.setDocAction(counterDT.getDocAction());
				counter.processIt(counterDT.getDocAction());
				counter.saveEx(get_TrxName());
			}
		}
		return counter;
	}	//	createCounterDoc

	/**
	 * 	Create new RMA by copying
	 * 	@param from RMA
	 * 	@param C_DocType_ID doc type
	 * 	@param isSOTrx sales order
	 * 	@param counter create counter links
	 * 	@param trxName trx
	 *	@return MRMA
	 */
	public static MRMA copyFrom (MRMA from, int C_DocType_ID, boolean isSOTrx, boolean counter, String trxName)
	{
		MRMA to = new MRMA (from.getCtx(), 0, null);
		to.set_TrxName(trxName);
		copyValues(from, to, from.getAD_Client_ID(), from.getAD_Org_ID());
		to.set_ValueNoCheck ("M_RMA_ID", I_ZERO);
		to.set_ValueNoCheck ("DocumentNo", null);
		to.setDocStatus (DOCSTATUS_Drafted);		//	Draft
		to.setDocAction(DOCACTION_Complete);
		to.setC_DocType_ID (C_DocType_ID);
		to.setIsSOTrx(isSOTrx);
		to.setIsApproved (false);
		to.setProcessed (false);
		to.setProcessing(false);

		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setSalesRep_ID(from.getSalesRep_ID());
		to.setHelp(from.getHelp());
		to.setM_RMAType_ID(from.getM_RMAType_ID());
		to.setAmt(from.getAmt());

		to.setC_Order_ID(0);
		//	Try to find Order/Shipment/Receipt link
		if (from.getC_Order_ID() != 0)
		{
			MOrder peer = new MOrder (from.getCtx(), from.getC_Order_ID(), from.get_TrxName());
			if (peer.getRef_Order_ID() != 0)
				to.setC_Order_ID(peer.getRef_Order_ID());
		}
		if (from.getInOut_ID() != 0)
		{
			MInOut peer = new MInOut (from.getCtx(), from.getInOut_ID(), from.get_TrxName());
			if (peer.getRef_InOut_ID() != 0)
				to.setInOut_ID(peer.getRef_InOut_ID());
		}
		to.setRef_RMA_ID(from.getM_RMA_ID());

		to.saveEx(trxName);
		if (counter)
			from.setRef_RMA_ID(to.getM_RMA_ID());

		if (to.copyLinesFrom(from, counter) == 0)
			throw new IllegalStateException("Could not create RMA Lines");

		return to;
	}	//	copyFrom

	/**
	 * 	Copy Lines From other RMA
	 *	@param otherRMA
	 *	@param counter set counter info
	 *	@param setOrder set order link
	 *	@return number of lines copied
	 */
	public int copyLinesFrom (MRMA otherRMA, boolean counter)
	{
		if (isProcessed() || otherRMA == null)
			return 0;
		MRMALine[] fromLines = otherRMA.getLines(false);
		int count = 0;
		for (int i = 0; i < fromLines.length; i++)
		{
			MRMALine line = new MRMALine(getCtx(), 0, null);
			MRMALine fromLine = fromLines[i];
			line.set_TrxName(get_TrxName());
			if (counter)	//	header
				PO.copyValues(fromLine, line, getAD_Client_ID(), getAD_Org_ID());
			else
				PO.copyValues(fromLine, line, fromLine.getAD_Client_ID(), fromLine.getAD_Org_ID());
			line.setM_RMA_ID(getM_RMA_ID());
			line.set_ValueNoCheck (MRMALine.COLUMNNAME_M_RMALine_ID, I_ZERO);	//	new
			if (counter)
			{
				line.setRef_RMALine_ID(fromLine.getM_RMALine_ID());
				if (fromLine.getM_InOutLine_ID() != 0)
				{
					MInOutLine peer = new MInOutLine (getCtx(), fromLine.getM_InOutLine_ID(), get_TrxName());
					if (peer.getRef_InOutLine_ID() != 0)
						line.setM_InOutLine_ID(peer.getRef_InOutLine_ID());
				}
			}
			//
			line.setProcessed(false);
			if (line.save(get_TrxName()))
				count++;
			//	Cross Link
			if (counter)
			{
				fromLine.setRef_RMALine_ID(line.getM_RMALine_ID());
				fromLine.saveEx(get_TrxName());
			}
		}
		if (fromLines.length != count)
			log.error("Line difference - From=" + fromLines.length + " <> Saved=" + count);
		return count;
	}	//	copyLinesFrom

	/**
	 * 	Void Document.
	 * 	@return true if success
	 */
	@Override
	public boolean voidIt()
	{
		log.info("voidIt - " + toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		MRMALine lines[] = getLines(true);
		// Set Qty and Amt on all lines to be Zero
		for (MRMALine rmaLine : lines)
		{
		    rmaLine.addDescription(Msg.getMsg(getCtx(), "Voided") + " (" + rmaLine.getQty() + ")");
		    rmaLine.setQty(Env.ZERO);
		    rmaLine.setAmt(Env.ZERO);
		    rmaLine.saveEx();
		}

		addDescription(Msg.getMsg(getCtx(), "Voided"));
		setAmt(Env.ZERO);

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
        setDocAction(DOCACTION_None);
		return true;
	}	//	voidIt

	/**
	 * 	Close Document.
	 * 	Cancel not delivered Qunatities
	 * 	@return true if success
	 */
	@Override
	public boolean closeIt()
	{
		log.info("closeIt - " + toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		return true;
	}	//	closeIt

	/**
	 * 	Reverse Correction
	 * 	@return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.info("reverseCorrectIt - " + toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reverseCorrectionIt

	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.info("reverseAccrualIt - " + toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reverseAccrualIt

	/**
	 * 	Re-activate
	 * 	@return true if success
	 */
	@Override
	public boolean reActivateIt()
	{
		log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reActivateIt

    /**
     *  Set Processed.
     *  Propagate to Lines
     *  @param processed processed
     */
    @Override
	public void setProcessed (boolean processed)
    {
        super.setProcessed (processed);
        if (get_ID() <= 0)
            return;
        int noLine = DB.executeUpdateEx("UPDATE M_RMALine SET Processed=? WHERE M_RMA_ID=?",
        		new Object[]{processed, get_ID()},
        		get_TrxName());
        m_lines = null;
        log.debug("setProcessed - " + processed + " - Lines=" + noLine);
    }   //  setProcessed

    /**
     *  Add to Description
     *  @param description text
     */
    public void addDescription (String description)
    {
        String desc = getDescription();
        if (desc == null)
            setDescription(description);
        else
            setDescription(desc + " | " + description);
    }   //  addDescription

    /**
     * Get the total amount based on the lines
     * @return Total Amount
     */
    public BigDecimal getTotalAmount()
    {
        MRMALine lines[] = this.getLines(true);

        BigDecimal amt = Env.ZERO;

        for (MRMALine line : lines)
        {
            amt = amt.add(line.getLineNetAmt());
        }

        return amt;
    }

    /**
     * Updates the amount on the document
     */
    public void updateAmount()
    {
        setAmt(getTotalAmount());
    }

	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(": ").
			append(Msg.translate(getCtx(),"Amt")).append("=").append(getAmt())
			.append(" (#").append(getLines(false).length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	//	getSummary

    /**
     * Retrieves all the charge lines that is present on the document
     * @return Charge Lines
     */
    public MRMALine[] getChargeLines()
    {
        StringBuffer whereClause = new StringBuffer();
        whereClause.append("IsActive='Y' AND M_RMA_ID=");
        whereClause.append(get_ID());
        whereClause.append(" AND C_Charge_ID IS NOT null");

        int rmaLineIds[] = MRMALine.getAllIDs(MRMALine.Table_Name, whereClause.toString(), get_TrxName());

        ArrayList<MRMALine> chargeLineList = new ArrayList<MRMALine>();

        for (int i = 0; i < rmaLineIds.length; i++)
        {
            MRMALine rmaLine = new MRMALine(getCtx(), rmaLineIds[i], get_TrxName());
            chargeLineList.add(rmaLine);
        }

        MRMALine lines[] = new MRMALine[chargeLineList.size()];
        chargeLineList.toArray(lines);

        return lines;
    }

    /**
     * Get whether Tax is included (based on the original order)
     * @return True if tax is included
     */
    public boolean isTaxIncluded(final I_C_Tax tax)
    {
        final I_C_Order order = getOriginalOrder();

        if (order != null && order.getC_Order_ID() > 0)
        {
        	return Services.get(IOrderBL.class).isTaxIncluded(order, tax);
        }

        return true;
    }

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg

	/**
	 * 	Get Document Owner (Responsible)
	 *	@return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getSalesRep_ID();
	}	//	getDoc_User_ID

	/**
	 * 	Get Document Approval Amount
	 *	@return amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return getAmt();
	}	//	getApprovalAmt

	/**
	 * 	Document Status is Complete or Closed
	 *	@return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
			|| DOCSTATUS_Closed.equals(ds)
			|| DOCSTATUS_Reversed.equals(ds);
	}	//	isComplete

}	//	MRMA
