/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.currency.ICurrencyBL;
import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/**
 * Cash Journal Model
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>FR [ 1866214 ]
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1831997 ] Cash journal allocation reversed
 * <li>BF [ 1894524 ] Pay an reversed invoice
 * <li>BF [ 1899477 ] MCash.getLines should return only active lines
 * <li>BF [ 2588326 ] Cash Lines are not correctly updated on voiding
 * @version $Id: MCash.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MCash extends X_C_Cash implements IDocument
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1221144207418749593L;

	/**
	 * Get Cash Journal for currency, org and date
	 *
	 * @param ctx           context
	 * @param C_Currency_ID currency
	 * @param AD_Org_ID     org
	 * @param dateAcct      date
	 * @param trxName       transaction
	 * @return cash
	 */
	public static MCash get(Properties ctx, int AD_Org_ID,
							Timestamp dateAcct, int C_Currency_ID, String trxName)
	{
		// Existing Journal
		String whereClause = "C_Cash.AD_Org_ID=?"                        // #1
				+ " AND TRUNC(C_Cash.StatementDate)=?"            // #2
				+ " AND C_Cash.Processed='N'"
				+ " AND EXISTS (SELECT * FROM C_CashBook cb "
				+ "WHERE C_Cash.C_CashBook_ID=cb.C_CashBook_ID AND cb.AD_Org_ID=C_Cash.AD_Org_ID"
				+ " AND cb.C_Currency_ID=?)";            // #3
		MCash retValue = new Query(ctx, MCash.Table_Name, whereClause, trxName)
				.setParameters(new Object[] { AD_Org_ID, TimeUtil.getDay(dateAcct), C_Currency_ID })
				.setOrderBy(I_C_Cash.COLUMNNAME_C_Cash_ID)
				.first();

		if (retValue != null)
		{
			return retValue;
		}

		// Get CashBook
		MCashBook cb = MCashBook.get(ctx, AD_Org_ID, C_Currency_ID);
		if (cb == null)
		{
			s_log.warn("No CashBook for AD_Org_ID=" + AD_Org_ID + ", C_Currency_ID=" + C_Currency_ID);
			return null;
		}

		// Create New Journal
		retValue = new MCash(cb, dateAcct);
		retValue.saveEx(trxName); // metas: tsa: use saveEx
		return retValue;
	}    // get

	/**
	 * Get Cash Journal for CashBook and date
	 *
	 * @param ctx           context
	 * @param C_CashBook_ID cashbook
	 * @param dateAcct      date
	 * @param trxName       transaction
	 * @return cash
	 */
	public static MCash get(Properties ctx, int C_CashBook_ID,
							Timestamp dateAcct, String trxName)
	{
		String whereClause = "C_CashBook_ID=?"            // #1
				+ " AND TRUNC(StatementDate)=?"            // #2
				+ " AND Processed='N'";

		MCash retValue = new Query(ctx, MCash.Table_Name, whereClause, trxName)
				.setParameters(new Object[] { C_CashBook_ID, TimeUtil.getDay(dateAcct) })
				.first();

		if (retValue != null)
		{
			return retValue;
		}

		// Get CashBook
		MCashBook cb = new MCashBook(ctx, C_CashBook_ID, trxName);
		if (cb.get_ID() == 0)
		{
			s_log.warn("Not found C_CashBook_ID=" + C_CashBook_ID);
			return null;
		}

		// Create New Journal
		retValue = new MCash(cb, dateAcct);
		retValue.saveEx(trxName);
		return retValue;
	}    // get

	/**
	 * Static Logger
	 */
	private static final Logger s_log = LogManager.getLogger(MCash.class);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param C_Cash_ID id
	 * @param trxName transaction
	 */
	public MCash(Properties ctx, int C_Cash_ID, String trxName)
	{
		super(ctx, C_Cash_ID, trxName);
		if (C_Cash_ID == 0)
		{
			// setC_CashBook_ID (0); // FK
			setBeginningBalance(BigDecimal.ZERO);
			setEndingBalance(BigDecimal.ZERO);
			setStatementDifference(BigDecimal.ZERO);
			setDocAction(DOCACTION_Complete);
			setDocStatus(DOCSTATUS_Drafted);
			//
			Timestamp today = TimeUtil.getDay(System.currentTimeMillis());
			setStatementDate(today);    // @#Date@
			setDateAcct(today);    // @#Date@

			final String orgName = Services.get(IOrgDAO.class).retrieveOrgName(getAD_Org_ID());
			String name = DisplayType.getDateFormat(DisplayType.Date).format(today)
					+ " " + orgName;
			setName(name);
			setIsApproved(false);
			setPosted(false);    // N
			setProcessed(false);
		}
	}    // MCash

	/**
	 * Load Constructor
	 *
	 * @param ctx     context
	 * @param rs      result set
	 * @param trxName transaction
	 */
	@SuppressWarnings("unused")
	public MCash(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}    // MCash

	/**
	 * Parent Constructor
	 *
	 * @param cb    cash book
	 * @param today date - if null today
	 */
	public MCash(MCashBook cb, Timestamp today)
	{
		this(cb.getCtx(), 0, cb.get_TrxName());
		setClientOrg(cb);
		setC_CashBook_ID(cb.getC_CashBook_ID());
		if (today != null)
		{
			setStatementDate(today);
			setDateAcct(today);
			String name = DisplayType.getDateFormat(DisplayType.Date).format(today)
					+ " " + cb.getName();
			setName(name);
		}
		m_book = cb;
	}    // MCash

	/**
	 * Lines
	 */
	private MCashLine[] m_lines = null;
	/**
	 * CashBook
	 */
	private MCashBook m_book = null;

	/**
	 * Get Lines
	 *
	 * @param requery requery
	 * @return lines
	 */
	public MCashLine[] getLines(boolean requery)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}

		String whereClause = MCashLine.COLUMNNAME_C_Cash_ID + "=?";
		List<MCashLine> list = new Query(getCtx(), MCashLine.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { getC_Cash_ID() })
				.setOrderBy(MCashLine.COLUMNNAME_Line)
				.setOnlyActiveRecords(true)
				.list(MCashLine.class);

		m_lines = list.toArray(new MCashLine[list.size()]);
		return m_lines;
	}    // getLines

	/**
	 * Get Cash Book
	 *
	 * @return cash book
	 */
	public MCashBook getCashBook()
	{
		if (m_book == null)
		{
			m_book = MCashBook.get(getCtx(), getC_CashBook_ID());
		}
		return m_book;
	}    // getCashBook

	/**
	 * Get Document No
	 *
	 * @return name
	 */
	@Override
	public String getDocumentNo()
	{
		return getName();
	}    // getDocumentNo

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		return Msg.getElement(getCtx(), "C_Cash_ID") + " " + getDocumentNo();
	}    // getDocumentInfo

	/**
	 * Create PDF
	 *
	 * @return File or null
	 */
	@Override
	public File createPDF()
	{
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (Exception e)
		{
			log.error("Could not create PDF - " + e.getMessage());
		}
		return null;
	}    // getPDF

	/**
	 * Create PDF file
	 *
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(File file)
	{
		// ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
		// if (re == null)
		return null;
		// return re.getPDF(file);
	}    // createPDF

	/**
	 * Before Save
	 *
	 * @param newRecord
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		setAD_Org_ID(getCashBook().getAD_Org_ID());
		if (getAD_Org_ID() <= 0)
		{
			throw new AdempiereException("@AD_Org_ID@");
		}
		// Calculate End Balance
		setEndingBalance(getBeginningBalance().add(getStatementDifference()));
		return true;
	}    // beforeSave

	/**************************************************************************
	 * Process document
	 *
	 * @param processAction document action
	 * @return true if performed
	 */
	@Override
	public boolean processIt(String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
	}    // process

	/**
	 * Process Message
	 */
	private String m_processMsg = null;
	/**
	 * Just Prepared Flag
	 */
	private boolean m_justPrepared = false;

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
	}    // unlockIt

	/**
	 * Invalidate Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}    // invalidateIt

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

		// Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), DocBaseType.CashJournal, getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return IDocument.STATUS_Invalid;
		}
		MCashLine[] lines = getLines(false);
		if (lines.length == 0)
		{
			throw AdempiereException.noLines();
		}
		// Add up Amounts
		BigDecimal difference = BigDecimal.ZERO;
		final CurrencyId currencyId = CurrencyId.ofRepoId(getC_Currency_ID());
		for (MCashLine line2 : lines)
		{
			MCashLine line = line2;
			if (!line.isActive())
			{
				continue;
			}

			final CurrencyId lineCurrencyId = CurrencyId.ofRepoId(line.getC_Currency_ID());
			if (CurrencyId.equals(currencyId, lineCurrencyId))
			{
				difference = difference.add(line.getAmount());
			}
			else
			{
				BigDecimal amt = Services.get(ICurrencyBL.class).convert(
						line.getAmount(),
						lineCurrencyId,
						currencyId,
						getDateAcct().toInstant(),
						(CurrencyConversionTypeId)null,
						ClientId.ofRepoId(getAD_Client_ID()),
						OrgId.ofRepoId(getAD_Org_ID()));
				difference = difference.add(amt);
			}
		}
		setStatementDifference(difference);
		// setEndingBalance(getBeginningBalance().add(getStatementDifference()));

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}
		return IDocument.STATUS_InProgress;
	}    // prepareIt

	/**
	 * Approve Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
		log.info(toString());
		setIsApproved(true);
		return true;
	}    // approveIt

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
	}    // rejectIt

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
			String status = prepareIt();
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

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}
		//
		log.debug("Completed: {}", this);

		MCashLine[] lines = getLines(false);
		for (MCashLine line2 : lines)
		{
			MCashLine line = line2;
			if (MCashLine.CASHTYPE_Invoice.equals(line.getCashType()))
			{
				// Check if the invoice is completed - teo_sarca BF [ 1894524 ]
				MInvoice invoice = line.getInvoice();
				final DocStatus invoiceDocStatus = DocStatus.ofCode(invoice.getDocStatus());
				if (!invoiceDocStatus.isCompletedOrClosedReversedOrVoided())
				{
					m_processMsg = "@Line@ " + line.getLine() + ": @InvoiceCreateDocNotCompleted@";
					return IDocument.STATUS_Invalid;
				}
				//
				String name = Msg.translate(getCtx(), "C_Cash_ID") + ": " + getName()
						+ " - " + Msg.translate(getCtx(), "Line") + " " + line.getLine();
				MAllocationHdr hdr = new MAllocationHdr(getCtx(), false,
						getDateAcct(), line.getC_Currency_ID(),
						name, get_TrxName());
				hdr.setAD_Org_ID(getAD_Org_ID());
				if (!hdr.save())
				{
					m_processMsg = MetasfreshLastError.retrieveErrorString("Could not create Allocation Hdr");
					return IDocument.STATUS_Invalid;
				}
				// Allocation Line
				MAllocationLine aLine = new MAllocationLine(hdr, line.getAmount(),
						line.getDiscountAmt(), line.getWriteOffAmt(), BigDecimal.ZERO);
				aLine.setC_Invoice_ID(line.getC_Invoice_ID());
				aLine.setC_CashLine_ID(line.getC_CashLine_ID());
				if (!aLine.save())
				{
					m_processMsg = MetasfreshLastError.retrieveErrorString("Could not create Allocation Line");
					return IDocument.STATUS_Invalid;
				}
				// Should start WF
				if (!hdr.processIt(IDocument.ACTION_Complete))
				{
					m_processMsg = MetasfreshLastError.retrieveErrorString("Could not process Allocation");
					return IDocument.STATUS_Invalid;
				}
				if (!hdr.save())
				{
					m_processMsg = MetasfreshLastError.retrieveErrorString("Could not save Allocation");
					return IDocument.STATUS_Invalid;
				}
			}
			else if (MCashLine.CASHTYPE_BankAccountTransfer.equals(line.getCashType()))
			{
				// Payment just as intermediate info
				MPayment pay = new MPayment(getCtx(), 0, get_TrxName());
				pay.setAD_Org_ID(getAD_Org_ID());
				String documentNo = getName();
				pay.setDocumentNo(documentNo);
				pay.setR_PnRef(documentNo);
				pay.set_ValueNoCheck("TrxType", "X");        // Transfer
				pay.set_ValueNoCheck("TenderType", "X");
				//
				// Modification for cash payment - Posterita
				pay.setC_CashBook_ID(getC_CashBook_ID());
				// End of modification - Posterita

				pay.setC_BP_BankAccount_ID(line.getC_BP_BankAccount_ID());
				pay.setIsReceiptAndUpdateDocType(true);    // Receipt
				pay.setDateTrx(getStatementDate());
				pay.setDateAcct(getDateAcct());
				pay.setAmount(line.getC_Currency_ID(), line.getAmount().negate());    // Transfer
				pay.setDescription(line.getDescription());
				pay.setDocStatus(DocStatus.Closed.getCode());
				pay.setDocAction(MPayment.DOCACTION_None);
				pay.setPosted(true);
				pay.setIsAllocated(true);    // Has No Allocation!
				pay.setProcessed(true);
				if (!pay.save())
				{
					m_processMsg = MetasfreshLastError.retrieveErrorString("Could not create Payment");
					return IDocument.STATUS_Invalid;
				}

				line.setC_Payment_ID(pay.getC_Payment_ID());
				if (!line.save())
				{
					m_processMsg = "Could not update Cash Line";
					return IDocument.STATUS_Invalid;
				}
			}
		}

		// User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return IDocument.STATUS_Invalid;
		}
		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return IDocument.STATUS_Completed;
	}    // completeIt

	/**
	 * Void Document.
	 * Same as Close.
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

		// FR [ 1866214 ]
		boolean retValue = reverseIt();

		if (retValue)
		{
			// After Void
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
			if (m_processMsg != null)
			{
				return false;
			}
			setDocAction(DOCACTION_None);
		}

		return retValue;
	}    // voidIt

	// FR [ 1866214 ]

	/**************************************************************************
	 * Reverse Cash
	 * Period needs to be open
	 *
	 * @return true if reversed
	 */
	private boolean reverseIt()
	{
		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			setDocAction(DOCACTION_None);
			return false;
		}

		// Can we delete posting
		if (!MPeriod.isOpen(getCtx(), this.getDateAcct(), DocBaseType.CashJournal, getAD_Org_ID()))
		{
			throw new IllegalStateException("@PeriodClosed@");
		}

		// Reverse Allocations
		MAllocationHdr[] allocations = MAllocationHdr.getOfCash(getCtx(), getC_Cash_ID(), get_TrxName());
		for (MAllocationHdr allocation : allocations)
		{
			allocation.reverseCorrectIt();
			if (!allocation.save())
			{
				throw new IllegalStateException("Cannot reverse allocations");
			}
		}

		MCashLine[] cashlines = getLines(true);
		for (MCashLine cashline : cashlines)
		{
			BigDecimal oldAmount = cashline.getAmount();
			BigDecimal oldDiscount = cashline.getDiscountAmt();
			BigDecimal oldWriteOff = cashline.getWriteOffAmt();
			cashline.setAmount(BigDecimal.ZERO);
			cashline.setDiscountAmt(BigDecimal.ZERO);
			cashline.setWriteOffAmt(BigDecimal.ZERO);
			cashline.addDescription(Msg.getMsg(getCtx(), "Voided")
					+ " (Amount=" + oldAmount + ", Discount=" + oldDiscount
					+ ", WriteOff=" + oldWriteOff + ", )");
			if (MCashLine.CASHTYPE_BankAccountTransfer.equals(cashline.getCashType()))
			{
				if (cashline.getC_Payment_ID() == 0)
				{
					throw new IllegalStateException("Cannot reverse payment");
				}

				MPayment payment = new MPayment(getCtx(), cashline.getC_Payment_ID(), get_TrxName());
				payment.reverseCorrectIt();
				payment.saveEx();
			}
			cashline.saveEx();
		}

		setName(getName() + "^");
		addDescription(Msg.getMsg(getCtx(), "Voided"));
		setDocStatus(DOCSTATUS_Reversed);    // for direct calls
		setProcessed(true);
		setPosted(true);
		setDocAction(DOCACTION_None);
		saveEx();

		// Delete Posting
		Services.get(IFactAcctDAO.class).deleteForDocument(this);

		return true;
	}    // reverse

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	}    // addDescription

	/**
	 * Close Document.
	 * Cancel not delivered Quantities
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
	}    // closeIt

	/**
	 * Reverse Correction
	 *
	 * @return true if success
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

		// FR [ 1866214 ]
		boolean retValue = reverseIt();

		if (retValue)
		{
			// After reverseCorrect
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
			if (m_processMsg != null)
			{
				return false;
			}
		}

		return retValue;
	}    // reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 *
	 * @return true if success
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
	}    // reverseAccrualIt

	/**
	 * Re-activate
	 *
	 * @return true if success
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
		if (reverseCorrectIt())
		{
			return true;
		}

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}
		return false;
	}    // reActivateIt

	/**
	 * Set Processed
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);
		String sql = "UPDATE C_CashLine SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE C_Cash_ID=" + getC_Cash_ID();
		int noLine = DB.executeUpdateAndSaveErrorOnFail(sql, get_TrxName());
		m_lines = null;
		log.debug(processed + " - Lines=" + noLine);
	}    // setProcessed

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MCash[");
		sb.append(get_ID())
				.append("-").append(getName())
				.append(", Balance=").append(getBeginningBalance())
				.append("->").append(getEndingBalance())
				.append("]");
		return sb.toString();
	}    // toString

	/*************************************************************************
	 * Get Summary
	 *
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getName());
		// : Total Lines = 123.00 (#1)
		sb.append(": ")
				.append(Msg.translate(getCtx(), "BeginningBalance")).append("=").append(getBeginningBalance())
				.append(",")
				.append(Msg.translate(getCtx(), "EndingBalance")).append("=").append(getEndingBalance())
				.append(" (#").append(getLines(false).length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
		return sb.toString();
	}    // getSummary

	@Override
	public InstantAndOrgId getDocumentDate()
	{
		return InstantAndOrgId.ofTimestamp(getStatementDate(), OrgId.ofRepoId(getAD_Org_ID()));
	}

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}    // getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getCreatedBy();
	}    // getDoc_User_ID

	/**
	 * Get Document Approval Amount
	 *
	 * @return amount difference
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return getStatementDifference();
	}    // getApprovalAmt

	/**
	 * Get Currency
	 *
	 * @return Currency
	 */
	@Override
	public int getC_Currency_ID()
	{
		return getCashBook().getC_Currency_ID();
	}    // getC_Currency_ID

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
				|| DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	}    // isComplete

}    // MCash
