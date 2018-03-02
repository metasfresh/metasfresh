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

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.BPartnerCreditLimitRepository;
import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.bpartner.service.IBPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.currency.ICurrencyBL;
import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.prepayorder.service.IPrepayOrderAllocationBL;
import de.metas.process.IProcess;
import de.metas.process.ProcessInfo;

/**
 * Payment Model. - retrieve and create payments for invoice
 *
 * <pre>
 *  Event chain
 *  - Payment inserted
 *      C_Payment_Trg fires
 *          update DocumentNo with payment summary
 *  - Payment posted (C_Payment_Post)
 *      create allocation line
 *          C_Allocation_Trg fires
 *              Update C_BPartner Open Item Amount
 *      update invoice (IsPaid)
 *      link invoice-payment if batch
 *
 *  Lifeline:
 *  -   Created by VPayment or directly
 *  -   When changed in VPayment
 *      - old payment is reversed
 *      - new payment created
 *
 *  When Payment is posed, the Allocation is made
 * </pre>
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 1948157 ] Is necessary the reference for document reverse
 * @see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962
 *      <li>FR [ 1866214 ]
 * @sse http://sourceforge.net/tracker/index.php?func=detail&aid=1866214&group_id=176962&atid=879335
 *      <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *
 * @author Carlos Ruiz - globalqss [ 2141475 ] Payment <> allocations must not be completed - implement lots of validations on prepareIt
 * @version $Id: MPayment.java,v 1.4 2006/10/02 05:18:39 jjanke Exp $
 */
public final class MPayment extends X_C_Payment
		implements IDocument, IProcess
{

	/**
	 *
	 */
	private static final long serialVersionUID = 5273805787122033169L;

	/**
	 * Get Payments Of BPartner
	 *
	 * @param ctx context
	 * @param C_BPartner_ID id
	 * @param trxName transaction
	 * @return array
	 */
	public static MPayment[] getOfBPartner(final Properties ctx, final int C_BPartner_ID, final String trxName)
	{
		// FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		final String whereClause = "C_BPartner_ID=?";
		final List<MPayment> list = new Query(ctx, I_C_Payment.Table_Name, whereClause, trxName)
				.setParameters(C_BPartner_ID)
				.list();

		//
		final MPayment[] retValue = new MPayment[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getOfBPartner

	/**************************************************************************
	 * Default Constructor
	 *
	 * @param ctx context
	 * @param C_Payment_ID payment to load, (0 create new payment)
	 * @param trxName trx name
	 */
	public MPayment(final Properties ctx, final int C_Payment_ID, final String trxName)
	{
		super(ctx, C_Payment_ID, trxName);
		// New
		if (C_Payment_ID == 0)
		{
			setDocAction(DOCACTION_Complete);
			setDocStatus(DOCSTATUS_Drafted);
			setTrxType(TRXTYPE_Sales);
			//
			setR_AvsAddr(R_AVSZIP_Unavailable);
			setR_AvsZip(R_AVSZIP_Unavailable);
			//
			setIsReceipt(true);
			setIsApproved(false);
			setIsReconciled(false);
			setIsAllocated(false);
			setIsOnline(false);
			setIsSelfService(false);
			setIsDelayedCapture(false);
			setIsPrepayment(false);
			setProcessed(false);
			setProcessing(false);
			setPosted(false);
			//
			setPayAmt(BigDecimal.ZERO);
			setDiscountAmt(BigDecimal.ZERO);
			setTaxAmt(BigDecimal.ZERO);
			setWriteOffAmt(BigDecimal.ZERO);
			setIsOverUnderPayment(false);
			setOverUnderAmt(BigDecimal.ZERO);
			//
			setDateTrx(new Timestamp(System.currentTimeMillis()));
			setDateAcct(getDateTrx());
			setTenderType(TENDERTYPE_Check);
		}
	}   // MPayment

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set record
	 * @param trxName transaction
	 */
	public MPayment(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MPayment

	/** Temporary Payment Processors */
	private MPaymentProcessor[] m_mPaymentProcessors = null;
	/** Temporary Payment Processor */
	private MPaymentProcessor m_mPaymentProcessor = null;
	/** Logger */
	private static Logger s_log = LogManager.getLogger(MPayment.class);
	/** Error Message */
	private String m_errorMessage = null;

	/** Reversal Indicator */
	public static String REVERSE_INDICATOR = "^";

	/**
	 * Is Cashbook Transfer Trx
	 *
	 * @return true if Cash Trx
	 */
	public boolean isCashTrx()
	{
		return Services.get(IPaymentBL.class).isCashTrx(this);
	}	// isCashTrx

	/**************************************************************************
	 * Set Credit Card. Need to set PatmentProcessor after Amount/Currency Set
	 *
	 * @param TrxType Transaction Type see TRX_
	 * @param creditCardType CC type
	 * @param creditCardNumber CC number
	 * @param creditCardVV CC verification
	 * @param creditCardExpMM CC Exp MM
	 * @param creditCardExpYY CC Exp YY
	 * @return true if valid
	 */
	public boolean setCreditCard(final String TrxType, final String creditCardType, final String creditCardNumber,
			final String creditCardVV, final int creditCardExpMM, final int creditCardExpYY)
	{
		setTenderType(TENDERTYPE_CreditCard);
		setTrxType(TrxType);
		//
		setCreditCardType(creditCardType);
		setCreditCardNumber(creditCardNumber);
		setCreditCardVV(creditCardVV);
		setCreditCardExpMM(creditCardExpMM);
		setCreditCardExpYY(creditCardExpYY);
		//
		int check = MPaymentValidate.validateCreditCardNumber(creditCardNumber, creditCardType).length()
				+ MPaymentValidate.validateCreditCardExp(creditCardExpMM, creditCardExpYY).length();
		if (creditCardVV.length() > 0)
		{
			check += MPaymentValidate.validateCreditCardVV(creditCardVV, creditCardType).length();
		}
		return check == 0;
	}   // setCreditCard

	/**
	 * Set Credit Card - Exp. Need to set PatmentProcessor after Amount/Currency Set
	 *
	 * @param TrxType Transaction Type see TRX_
	 * @param creditCardType CC type
	 * @param creditCardNumber CC number
	 * @param creditCardVV CC verification
	 * @param creditCardExp CC Exp
	 * @return true if valid
	 */
	public boolean setCreditCard(final String TrxType, final String creditCardType, final String creditCardNumber,
			final String creditCardVV, final String creditCardExp)
	{
		return setCreditCard(TrxType, creditCardType, creditCardNumber,
				creditCardVV, MPaymentValidate.getCreditCardExpMM(creditCardExp),
				MPaymentValidate.getCreditCardExpYY(creditCardExp));
	}   // setCreditCard


	/**
	 * Set ACH BankAccount Info
	 *
	 * @param C_BP_BankAccount_ID bank account
	 * @param isReceipt true if receipt
	 * @param tenderType - Direct Debit or Direct Deposit
	 * @param routingNo routing
	 * @param accountNo account
	 * @return true if valid
	 */
	public boolean setBankACH(final int C_BP_BankAccount_ID, final boolean isReceipt, final String tenderType,
			final String routingNo, final String accountNo)
	{
		setTenderType(tenderType);
		setIsReceipt(isReceipt);
		//
		if (C_BP_BankAccount_ID > 0
				&& (routingNo == null || routingNo.length() == 0 || accountNo == null || accountNo.length() == 0))
		{
			setBankAccountDetails(C_BP_BankAccount_ID);
		}
		else
		{
			setC_BP_BankAccount_ID(C_BP_BankAccount_ID);
			setRoutingNo(routingNo);
			setAccountNo(accountNo);
		}
		setCheckNo("");
		//
		final int check = MPaymentValidate.validateRoutingNo(routingNo).length()
				+ MPaymentValidate.validateAccountNo(accountNo).length();
		return check == 0;
	}   // setBankACH

	/**
	 * Set Cash BankAccount Info
	 *
	 * @param C_BP_BankAccount_ID bank account
	 * @param isReceipt true if receipt
	 * @param tenderType - Cash (Payment)
	 * @return true if valid
	 */
	public boolean setBankCash(final int C_BP_BankAccount_ID, final boolean isReceipt, final String tenderType)
	{
		setTenderType(tenderType);
		setIsReceipt(isReceipt);
		//
		if (C_BP_BankAccount_ID > 0)
		{
			setBankAccountDetails(C_BP_BankAccount_ID);
		}
		else
		{
			setC_BP_BankAccount_ID(C_BP_BankAccount_ID);
		}
		//
		return true;
	}   // setBankCash

	/**
	 * Set Check BankAccount Info
	 *
	 * @param C_BP_BankAccount_ID bank account
	 * @param isReceipt true if receipt
	 * @param checkNo check no
	 * @return true if valid
	 */
	public boolean setBankCheck(final int C_BP_BankAccount_ID, final boolean isReceipt, final String checkNo)
	{
		return setBankCheck(C_BP_BankAccount_ID, isReceipt, null, null, checkNo);
	}	// setBankCheck

	/**
	 * Set Check BankAccount Info
	 *
	 * @param C_BP_BankAccount_ID bank account
	 * @param isReceipt true if receipt
	 * @param routingNo routing no
	 * @param accountNo account no
	 * @param checkNo chack no
	 * @return true if valid
	 */
	public boolean setBankCheck(final int C_BP_BankAccount_ID, final boolean isReceipt,
			final String routingNo, final String accountNo, final String checkNo)
	{
		setTenderType(TENDERTYPE_Check);
		setIsReceipt(isReceipt);
		//
		if (C_BP_BankAccount_ID > 0
				&& (routingNo == null || routingNo.length() == 0
						|| accountNo == null || accountNo.length() == 0))
		{
			setBankAccountDetails(C_BP_BankAccount_ID);
		}
		else
		{
			setC_BP_BankAccount_ID(C_BP_BankAccount_ID);
			setRoutingNo(routingNo);
			setAccountNo(accountNo);
		}
		setCheckNo(checkNo);
		//
		final int check = MPaymentValidate.validateRoutingNo(routingNo).length()
				+ MPaymentValidate.validateAccountNo(accountNo).length()
				+ MPaymentValidate.validateCheckNo(checkNo).length();
		return check == 0;       // no error message
	}   // setBankCheck

	/**
	 * Set Bank Account Details. Look up Routing No & Bank Acct No
	 *
	 * @param C_BP_BankAccount_ID bank account
	 */
	public void setBankAccountDetails(final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID == 0)
		{
			return;
		}
		setC_BP_BankAccount_ID(C_BP_BankAccount_ID);
		//
		final String sql = "SELECT b.RoutingNo, ba.AccountNo "
				+ "FROM C_BP_BankAccount ba"
				+ " INNER JOIN C_Bank b ON (ba.C_Bank_ID=b.C_Bank_ID) "
				+ "WHERE C_BP_BankAccount_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, C_BP_BankAccount_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				setRoutingNo(rs.getString(1));
				setAccountNo(rs.getString(2));
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}	// setBankAccountDetails

	/**
	 * Set Account Address
	 *
	 * @param name name
	 * @param street street
	 * @param city city
	 * @param state state
	 * @param zip zip
	 * @param country country
	 */
	public void setAccountAddress(final String name, final String street,
			final String city, final String state, final String zip, final String country)
	{
		setA_Name(name);
		setA_Street(street);
		setA_City(city);
		setA_State(state);
		setA_Zip(zip);
		setA_Country(country);
	}   // setAccountAddress

	/**************************************************************************
	 * Process Payment
	 *
	 * @return true if approved
	 */
	public boolean processOnline()
	{
		log.info("Amt=" + getPayAmt());
		//
		setIsOnline(true);
		setErrorMessage(null);
		// prevent charging twice
		if (isOnlineApproved())   // metas: tsa: use IsOnlineApproved instead of IsApproved
		{
			log.info("Already processed - " + getR_Result() + " - " + getR_RespMsg());
			setErrorMessage("Payment already Processed");
			return true;
		}

		if (m_mPaymentProcessor == null)
		{
			setPaymentProcessor();
		}
		if (m_mPaymentProcessor == null)
		{
			log.warn("No Payment Processor Model");
			setErrorMessage("No Payment Processor Model");
			return false;
		}

		boolean approved = false;

		try
		{
			final PaymentProcessor pp = PaymentProcessor.create(m_mPaymentProcessor, this);
			if (pp == null)
			{
				setErrorMessage("No Payment Processor");
			}
			else
			{
				// Validate before trying to process
				// metas: our revision of adempiere doesn't have the validate
				// feature yet :-(
				// String msg = pp.validate();
				// if (msg!=null && msg.trim().length()>0) {
				// setErrorMessage(Msg.getMsg(getCtx(), msg));
				// } else {
				// Process if validation succeeds
				approved = pp.processCC();
				if (approved)
				{
					setErrorMessage(null);
				}
				else {
					setErrorMessage("From " + getCreditCardName() + ": " + getR_RespMsg());
				// }
				}
			}
		}
		catch (final Exception e)
		{
			log.error("processOnline", e);
			setErrorMessage("Payment Processor Error: " + e.getMessage());
		}
		setIsOnlineApproved(approved); // metas: tsa: use IsOnlineApproved instead of IsApproved
		return approved;
	}   // processOnline

	/**
	 * Process Online Payment. implements {@link IProcess} after standard constructor Called when pressing the Process_Online button in C_Payment
	 *
	 * @param ctx Context
	 * @param pi Process Info
	 * @param trx transaction
	 * @return true if the next process should be performed
	 */
	@Override
	public void startProcess(final ProcessInfo pi, final ITrx trx)
	{
		log.info("startProcess: {}", pi);
		//
		if (pi.getRecord_ID() != getC_Payment_ID())
		{
			throw new AdempiereException("startProcess - Not same Payment - " + pi.getRecord_ID());
		}
		// Process it
		if (!processOnline())
		{
			throw new AdempiereException("Failed processing online: " + getErrorMessage());
		}
		saveEx(); // metas: changed to saveEx
	}   // startProcess

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return save
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// metas: tsa: us025b: begin: If is a cash bank, set TenderType=Cash
		if (getC_BP_BankAccount_ID() > 0)
		{
			final I_C_BP_BankAccount ba = getC_BP_BankAccount();
			final MBank bank = MBank.get(getCtx(), ba.getC_Bank_ID());
			if (bank.isCashBank())
			{
				setTenderType(TENDERTYPE_Cash);
			}
		}
		// metas: tsa: us025b: end
		// @Trifon - CashPayments
		// if ( getTenderType().equals("X") ) {
		if (isCashTrx() && ! Services.get(ISysConfigBL.class).getBooleanValue("CASH_AS_PAYMENT", true, getAD_Client_ID()))
		{
			// Cash Book Is mandatory
			if (getC_CashBook_ID() <= 0)
			{
				throw new FillMandatoryException("C_CashBook_ID");
			}
		}

		// end @Trifon - CashPayments

		// We have a charge
		if (getC_Charge_ID() != 0)
		{
			if (newRecord || is_ValueChanged("C_Charge_ID"))
			{
				setC_Order_ID(0);
				setC_Invoice_ID(0);
				setWriteOffAmt(BigDecimal.ZERO);
				setDiscountAmt(BigDecimal.ZERO);
				setIsOverUnderPayment(false);
				setOverUnderAmt(BigDecimal.ZERO);
				setIsPrepayment(false);
			}
		}
		// We need a BPartner
		else if (getC_BPartner_ID() == 0 && !isCashTrx())
		{
			if (getC_Invoice_ID() != 0)
			{
				;
			}
			else if (getC_Order_ID() != 0)
			{
				;
			}
			else
			{
				throw new AdempiereException("@NotFound@: @C_BPartner_ID@");
			}
		}
		// Prepayment: No charge and order or project (not as acct dimension)
		if (newRecord
				|| is_ValueChanged("C_Charge_ID") || is_ValueChanged("C_Invoice_ID")
				|| is_ValueChanged("C_Order_ID") || is_ValueChanged("C_Project_ID"))
		 {
			setIsPrepayment(getC_Charge_ID() == 0
					&& getC_BPartner_ID() != 0
					&& (getC_Order_ID() != 0
							|| (getC_Project_ID() != 0 && getC_Invoice_ID() == 0)));
						// metas: commented - Write off amount must not be set to 0.
						/*
						 * if (isPrepayment()) { if (newRecord || is_ValueChanged("C_Order_ID") || is_ValueChanged("C_Project_ID")) { setWriteOffAmt(Env.ZERO); setDiscountAmt(Env.ZERO); setIsOverUnderPayment(false);
						 * setOverUnderAmt(Env.ZERO); } }
						 */
		}

		// Document Type/Receipt
		if (getC_DocType_ID() <= 0)
		{
			setC_DocType_ID();
		}
		else
		{
			final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
			setIsReceipt(dt.isSOTrx());
		}
		setDocumentNo();
		//
		if (getDateAcct() == null)
		{
			setDateAcct(getDateTrx());
		}
		//
		if (!isOverUnderPayment())
		{
			setOverUnderAmt(BigDecimal.ZERO);
		}

		// Organization
		//
		// [ adempiere-Bugs-1885417 ] Validate BP on Payment Prepare or BeforeSave
		// there is bp and (invoice or order)
		if (getC_BPartner_ID() != 0 && (getC_Invoice_ID() != 0 || getC_Order_ID() != 0))
		{
			if (getC_Invoice_ID() != 0)
			{
				final I_C_Invoice inv = getC_Invoice();
				Check.errorIf(inv.getC_BPartner_ID() != getC_BPartner_ID(),
						"Payment {} has C_BPartner_ID={}, but invoice {} has C_BPartner_ID={}",
						this, getC_BPartner_ID(), inv, inv.getC_BPartner_ID());
			}
			if (getC_Order_ID() != 0)
			{
				final I_C_Order ord = getC_Order();
				Check.errorIf(ord.getC_BPartner_ID() != getC_BPartner_ID(),
						"Payment {} has C_BPartner_ID={}, but order {} has C_BPartner_ID={}",
						this, getC_BPartner_ID(), ord, ord.getC_BPartner_ID());
			}
		}

		return true;
	}	// beforeSave

	/**
	 * Get Allocated Amt in Payment Currency
	 *
	 * @return amount, never <code>null</code>, even if there are no allocations
	 */
	public BigDecimal getAllocatedAmt()
	{
		return Services.get(IPaymentDAO.class).getAllocatedAmt(this);
	}	// getAllocatedAmt

	/**
	 * Test Allocation (and set allocated flag)
	 *
	 * @return true if updated
	 */
	public boolean testAllocation()
	{
		return Services.get(IPaymentBL.class).testAllocation(this);
	}	// testAllocation

	/**
	 * Set Allocated Flag for payments
	 *
	 * @param ctx context
	 * @param C_BPartner_ID if 0 all
	 * @param trxName trx
	 */
	public static void setIsAllocated(final Properties ctx, final int C_BPartner_ID, final String trxName)
	{
		int counter = 0;
		String sql = "SELECT * FROM C_Payment "
				+ "WHERE IsAllocated='N' AND DocStatus IN ('CO','CL')";
		if (C_BPartner_ID > 1)
		{
			sql += " AND C_BPartner_ID=?";
		}
		else
		{
			sql += " AND AD_Client_ID=" + Env.getAD_Client_ID(ctx);
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			if (C_BPartner_ID > 1)
			{
				pstmt.setInt(1, C_BPartner_ID);
			}
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final MPayment pay = new MPayment(ctx, rs, trxName);
				if (pay.testAllocation())
				{
					if (pay.save())
					{
						counter++;
					}
				}
			}
		}
		catch (final Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		s_log.info("#" + counter);
	}	// setIsAllocated

	/**************************************************************************
	 * Set Error Message
	 *
	 * @param errorMessage error message
	 */
	public void setErrorMessage(final String errorMessage)
	{
		m_errorMessage = errorMessage;
	}	// setErrorMessage

	/**
	 * Get Error Message
	 *
	 * @return error message
	 */
	public String getErrorMessage()
	{
		return m_errorMessage;
	}	// getErrorMessage

	/**
	 * Set BankAccount and PaymentProcessor
	 *
	 * @return true if found
	 */
	public boolean setPaymentProcessor()
	{
		return setPaymentProcessor(getTenderType(), getCreditCardType());
	}	// setPaymentProcessor

	/**
	 * Set BankAccount and PaymentProcessor
	 *
	 * @param tender TenderType see TENDER_
	 * @param CCType CC Type see CC_
	 * @return true if found
	 */
	public boolean setPaymentProcessor(final String tender, final String CCType)
	{
		m_mPaymentProcessor = null;
		// Get Processor List
		if (m_mPaymentProcessors == null || m_mPaymentProcessors.length == 0)
		{
			m_mPaymentProcessors = MPaymentProcessor.find(getCtx(), tender, CCType, getAD_Client_ID(),
					getC_Currency_ID(), getPayAmt(), get_TrxName());
		}
		// Relax Amount
		if (m_mPaymentProcessors == null || m_mPaymentProcessors.length == 0)
		{
			m_mPaymentProcessors = MPaymentProcessor.find(getCtx(), tender, CCType, getAD_Client_ID(),
					getC_Currency_ID(), BigDecimal.ZERO, get_TrxName());
		}
		if (m_mPaymentProcessors == null || m_mPaymentProcessors.length == 0)
		{
			return false;
		}

		// Find the first right one
		for (final MPaymentProcessor m_mPaymentProcessor2 : m_mPaymentProcessors)
		{
			if (m_mPaymentProcessor2.accepts(tender, CCType))
			{
				m_mPaymentProcessor = m_mPaymentProcessor2;
			}
		}
		if (m_mPaymentProcessor != null)
		{
			setC_BP_BankAccount_ID(m_mPaymentProcessor.getC_BP_BankAccount_ID());
		}
		//
		return m_mPaymentProcessor != null;
	}   // setPaymentProcessor

	/**
	 * Get Accepted Credit Cards for PayAmt (default 0)
	 *
	 * @return credit cards
	 */
	public ValueNamePair[] getCreditCards()
	{
		return getCreditCards(getPayAmt());
	}	// getCreditCards

	/**
	 * Get Accepted Credit Cards for amount
	 *
	 * @param amt trx amount
	 * @return credit cards
	 */
	public ValueNamePair[] getCreditCards(final BigDecimal amt)
	{
		try
		{
			if (m_mPaymentProcessors == null || m_mPaymentProcessors.length == 0)
			{
				m_mPaymentProcessors = MPaymentProcessor.find(getCtx(), null, null,
						getAD_Client_ID(), getC_Currency_ID(), amt, get_TrxName());
			}
			//
			final HashMap<String, ValueNamePair> map = new HashMap<>(); // to eliminate duplicates
			for (final MPaymentProcessor m_mPaymentProcessor2 : m_mPaymentProcessors)
			{
				if (m_mPaymentProcessor2.isAcceptAMEX())
				{
					map.put(CREDITCARDTYPE_Amex, getCreditCardPair(CREDITCARDTYPE_Amex));
				}
				if (m_mPaymentProcessor2.isAcceptDiners())
				{
					map.put(CREDITCARDTYPE_Diners, getCreditCardPair(CREDITCARDTYPE_Diners));
				}
				if (m_mPaymentProcessor2.isAcceptDiscover())
				{
					map.put(CREDITCARDTYPE_Discover, getCreditCardPair(CREDITCARDTYPE_Discover));
				}
				if (m_mPaymentProcessor2.isAcceptMC())
				{
					map.put(CREDITCARDTYPE_MasterCard, getCreditCardPair(CREDITCARDTYPE_MasterCard));
				}
				if (m_mPaymentProcessor2.isAcceptCorporate())
				{
					map.put(CREDITCARDTYPE_PurchaseCard, getCreditCardPair(CREDITCARDTYPE_PurchaseCard));
				}
				if (m_mPaymentProcessor2.isAcceptVisa())
				{
					map.put(CREDITCARDTYPE_Visa, getCreditCardPair(CREDITCARDTYPE_Visa));
				}
			}  // for all payment processors
 //
			final ValueNamePair[] retValue = new ValueNamePair[map.size()];
			map.values().toArray(retValue);
			log.debug("getCreditCards - #" + retValue.length + " - Processors=" + m_mPaymentProcessors.length);
			return retValue;
		}
		catch (final Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}	// getCreditCards

	/**
	 * Get Type and name pair
	 *
	 * @param CreditCardType credit card Type
	 * @return pair
	 */
	private ValueNamePair getCreditCardPair(final String CreditCardType)
	{
		return new ValueNamePair(CreditCardType, getCreditCardName(CreditCardType));
	}	// getCreditCardPair

	/**************************************************************************
	 * Credit Card Number
	 *
	 * @param CreditCardNumber CreditCard Number
	 */
	@Override
	public void setCreditCardNumber(final String CreditCardNumber)
	{
		// metas: changed
		// super.setCreditCardNumber (MPaymentValidate.checkNumeric(CreditCardNumber));
		final String ccNumber = MPaymentValidate.checkNumeric(CreditCardNumber);

		volatileCCData.creditCardNumber = ccNumber; // metas 00036
		super.setCreditCardNumber(ccNumber);
	}	// setCreditCardNumber

	/**
	 * Verification Code
	 *
	 * @param newCreditCardVV CC verification
	 */
	@Override
	public void setCreditCardVV(final String newCreditCardVV)
	{
		// metas: changed
		// super.setCreditCardVV (MPaymentValidate.checkNumeric(newCreditCardVV));
		final String ccVV = MPaymentValidate.checkNumeric(newCreditCardVV);

		volatileCCData.creditCardVV = ccVV; // metas 00036
		super.setCreditCardVV(ccVV);
	}	// setCreditCardVV

	/**
	 * Two Digit CreditCard MM
	 *
	 * @param CreditCardExpMM Exp month
	 */
	@Override
	public void setCreditCardExpMM(final int CreditCardExpMM)
	{
		if (CreditCardExpMM < 1 || CreditCardExpMM > 12)
		{
			;
		}
		else
		{
			super.setCreditCardExpMM(CreditCardExpMM);
		}
	}	// setCreditCardExpMM

	/**
	 * Two digit CreditCard YY (til 2020)
	 *
	 * @param newCreditCardExpYY 2 or 4 digit year
	 */
	@Override
	public void setCreditCardExpYY(final int newCreditCardExpYY)
	{
		int CreditCardExpYY = newCreditCardExpYY;
		if (newCreditCardExpYY > 1999)
		{
			CreditCardExpYY = newCreditCardExpYY - 2000;
		}
		super.setCreditCardExpYY(CreditCardExpYY);
	}	// setCreditCardExpYY

	/**
	 * CreditCard Exp MMYY
	 *
	 * @param mmyy Exp in form of mmyy
	 * @return true if valid
	 */
	public boolean setCreditCardExp(final String mmyy)
	{
		if (MPaymentValidate.validateCreditCardExp(mmyy).length() != 0)
		{
			return false;
		}
		//
		final String exp = MPaymentValidate.checkNumeric(mmyy);
		final String mmStr = exp.substring(0, 2);
		final String yyStr = exp.substring(2, 4);
		setCreditCardExpMM(Integer.parseInt(mmStr));
		setCreditCardExpYY(Integer.parseInt(yyStr));
		return true;
	}   // setCreditCardExp

	/**
	 * CreditCard Exp MMYY
	 *
	 * @param delimiter / - or null
	 * @return Exp
	 */
	public String getCreditCardExp(final String delimiter)
	{
		final String mm = String.valueOf(getCreditCardExpMM());
		final String yy = String.valueOf(getCreditCardExpYY());

		final StringBuffer retValue = new StringBuffer();
		if (mm.length() == 1)
		{
			retValue.append("0");
		}
		retValue.append(mm);
		//
		if (delimiter != null)
		{
			retValue.append(delimiter);
		}
		//
		if (yy.length() == 1)
		{
			retValue.append("0");
		}
		retValue.append(yy);
		//
		return (retValue.toString());
	}   // getCreditCardExp

	/**
	 * MICR
	 *
	 * @param MICR MICR
	 */
	@Override
	public void setMicr(final String MICR)
	{
		super.setMicr(MPaymentValidate.checkNumeric(MICR));
	}	// setBankMICR

	/**
	 * Routing No
	 *
	 * @param RoutingNo Routing No
	 */
	@Override
	public void setRoutingNo(final String RoutingNo)
	{
		super.setRoutingNo(MPaymentValidate.checkNumeric(RoutingNo));
	}	// setBankRoutingNo

	/**
	 * Bank Account No
	 *
	 * @param AccountNo AccountNo
	 */
	@Override
	public void setAccountNo(final String AccountNo)
	{
		super.setAccountNo(MPaymentValidate.checkNumeric(AccountNo));
	}	// setBankAccountNo

	/**
	 * Check No
	 *
	 * @param CheckNo Check No
	 */
	@Override
	public void setCheckNo(final String CheckNo)
	{
		super.setCheckNo(MPaymentValidate.checkNumeric(CheckNo));
	}	// setBankCheckNo

	/**
	 * Set DocumentNo to Payment info. If there is a R_PnRef that is set automatically
	 */
	private void setDocumentNo()
	{
		// Cash Transfer
		if ("X".equals(getTenderType()))
		{
			return;
		}
		// Current Document No
		String documentNo = getDocumentNo();
		// Existing reversal
		if (documentNo != null
				&& documentNo.indexOf(REVERSE_INDICATOR) >= 0)
		{
			return;
		}

		// If external number exists - enforce it
		// metas: not enforcing external number
		// if (getR_PnRef() != null && getR_PnRef().length() > 0)
		// {
		// if (!getR_PnRef().equals(documentNo))
		// setDocumentNo(getR_PnRef());
		// return;
		// }
		// metas: end

		documentNo = "";
		// globalqss - read configuration to assign credit card or check number number for Payments
		// Credit Card
		if (TENDERTYPE_CreditCard.equals(getTenderType()))
		{
			if ( Services.get(ISysConfigBL.class).getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CREDIT_CARD", true, getAD_Client_ID()))
			{
				documentNo = getCreditCardType()
						+ " " + Obscure.obscure(getCreditCardNumber())
						+ " " + getCreditCardExpMM()
						+ "/" + getCreditCardExpYY();
			}
		}
		// Own Check No
		else if (TENDERTYPE_Check.equals(getTenderType())
				&& !isReceipt()
				&& getCheckNo() != null && getCheckNo().length() > 0)
		{
			if ( Services.get(ISysConfigBL.class).getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CHECK_ON_PAYMENT", true, getAD_Client_ID()))
			{
				documentNo = getCheckNo();
			}
		}
		// Customer Check: Routing: Account #Check
		else if (TENDERTYPE_Check.equals(getTenderType())
				&& isReceipt())
		{
			if ( Services.get(ISysConfigBL.class).getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CHECK_ON_RECEIPT", true, getAD_Client_ID()))
			{
				if (getRoutingNo() != null)
				{
					documentNo = getRoutingNo() + ": ";
				}
				if (getAccountNo() != null)
				{
					documentNo += getAccountNo();
				}
				if (getCheckNo() != null)
				{
					if (documentNo.length() > 0)
					{
						documentNo += " ";
					}
					documentNo += "#" + getCheckNo();
				}
			}
		}

		// Set Document No
		documentNo = documentNo.trim();
		if (documentNo.length() > 0)
		{
			setDocumentNo(documentNo);
		}
	}	// setDocumentNo

	/**
	 * Set Refernce No (and Document No)
	 *
	 * @param R_PnRef reference
	 */
	@Override
	public void setR_PnRef(final String R_PnRef)
	{
		super.setR_PnRef(R_PnRef);
		if (R_PnRef != null
				// metas: don't overwrite the documentno
				&&  Services.get(ISysConfigBL.class).getBooleanValue("PAYMENT_OVERWRITE_DOCUMENTNO_WITH_CREDIT_CARD", true, getAD_Client_ID())
		// metas: end
		)
		{

			setDocumentNo(R_PnRef);
		}
	}	// setR_PnRef

	// ---------------

	/**
	 * Set Payment Amount
	 *
	 * @param PayAmt Pay Amt
	 */
	@Override
	public void setPayAmt(final BigDecimal PayAmt)
	{
		super.setPayAmt(PayAmt == null ? BigDecimal.ZERO : PayAmt);
	}	// setPayAmt

	/**
	 * Set Payment Amount
	 *
	 * @param C_Currency_ID currency
	 * @param payAmt amount
	 */
	public void setAmount(final int C_Currency_ID, final BigDecimal payAmt)
	{
		final int currencyId =  C_Currency_ID == 0 ? Services.get(ICurrencyBL.class).getBaseCurrency(getCtx(), getAD_Client_ID(), getAD_Org_ID()).getC_Currency_ID() : C_Currency_ID;
		setC_Currency_ID(currencyId);
		setPayAmt(payAmt);
	}   // setAmount

	/**
	 * Discount Amt
	 *
	 * @param DiscountAmt Discount
	 */
	@Override
	public void setDiscountAmt(final BigDecimal DiscountAmt)
	{
		super.setDiscountAmt(DiscountAmt == null ? BigDecimal.ZERO : DiscountAmt);
	}	// setDiscountAmt

	/**
	 * WriteOff Amt
	 *
	 * @param WriteOffAmt WriteOff
	 */
	@Override
	public void setWriteOffAmt(final BigDecimal WriteOffAmt)
	{
		super.setWriteOffAmt(WriteOffAmt == null ? BigDecimal.ZERO : WriteOffAmt);
	}	// setWriteOffAmt

	/**
	 * OverUnder Amt
	 *
	 * @param OverUnderAmt OverUnder
	 */
	@Override
	public void setOverUnderAmt(final BigDecimal OverUnderAmt)
	{
		super.setOverUnderAmt(OverUnderAmt == null ? BigDecimal.ZERO : OverUnderAmt);
		setIsOverUnderPayment(getOverUnderAmt().compareTo(BigDecimal.ZERO) != 0);
	}	// setOverUnderAmt

	/**
	 * Tax Amt
	 *
	 * @param TaxAmt Tax
	 */
	@Override
	public void setTaxAmt(final BigDecimal TaxAmt)
	{
		super.setTaxAmt(TaxAmt == null ? BigDecimal.ZERO : TaxAmt);
	}	// setTaxAmt

	/**
	 * Set Info from BP Bank Account
	 *
	 * @param ba BP bank account
	 */
	public void setBP_BankAccount(final MBPBankAccount ba)
	{
		log.debug("" + ba);
		if (ba == null)
		{
			return;
		}
		setC_BPartner_ID(ba.getC_BPartner_ID());
		setAccountAddress(ba.getA_Name(), ba.getA_Street(), ba.getA_City(),
				ba.getA_State(), ba.getA_Zip(), ba.getA_Country());
		setA_EMail(ba.getA_EMail());
		setA_Ident_DL(ba.getA_Ident_DL());
		setA_Ident_SSN(ba.getA_Ident_SSN());
		// CC
		if (ba.getCreditCardType() != null)
		{
			setCreditCardType(ba.getCreditCardType());
		}
		if (ba.getCreditCardNumber() != null)
		{
			setCreditCardNumber(ba.getCreditCardNumber());
		}
		if (ba.getCreditCardExpMM() != 0)
		{
			setCreditCardExpMM(ba.getCreditCardExpMM());
		}
		if (ba.getCreditCardExpYY() != 0)
		{
			setCreditCardExpYY(ba.getCreditCardExpYY());
		}
		if (ba.getCreditCardVV() != null)
		{
			setCreditCardVV(ba.getCreditCardVV());
		}
		// Bank
		if (ba.getAccountNo() != null)
		{
			setAccountNo(ba.getAccountNo());
		}
		if (ba.getRoutingNo() != null)
		{
			setRoutingNo(ba.getRoutingNo());
		}
	}	// setBP_BankAccount

	/**
	 * Save Info from BP Bank Account
	 *
	 * @param ba BP bank account
	 * @return true if saved
	 */
	public boolean saveToBP_BankAccount(final MBPBankAccount ba)
	{
		if (ba == null)
		{
			return false;
		}
		ba.setA_Name(getA_Name());
		ba.setA_Street(getA_Street());
		ba.setA_City(getA_City());
		ba.setA_State(getA_State());
		ba.setA_Zip(getA_Zip());
		ba.setA_Country(getA_Country());
		ba.setA_EMail(getA_EMail());
		ba.setA_Ident_DL(getA_Ident_DL());
		ba.setA_Ident_SSN(getA_Ident_SSN());
		// CC
		ba.setCreditCardType(getCreditCardType());
		ba.setCreditCardNumber(getCreditCardNumber());
		ba.setCreditCardExpMM(getCreditCardExpMM());
		ba.setCreditCardExpYY(getCreditCardExpYY());
		ba.setCreditCardVV(getCreditCardVV());
		// Bank
		if (getAccountNo() != null)
		{
			ba.setAccountNo(getAccountNo());
		}
		if (getRoutingNo() != null)
		{
			ba.setRoutingNo(getRoutingNo());
		}
		// Trx
		ba.setR_AvsAddr(getR_AvsAddr());
		ba.setR_AvsZip(getR_AvsZip());
		//
		final boolean ok = ba.save(get_TrxName());
		log.debug("saveToBP_BankAccount - " + ba);
		return ok;
	}	// setBP_BankAccount

	/**
	 * Set Doc Type bases on IsReceipt
	 */
	private void setC_DocType_ID()
	{
		setC_DocType_ID(isReceipt());
	}	// setC_DocType_ID

	/**
	 * Set Doc Type
	 *
	 * @param isReceipt is receipt
	 */
	public void setC_DocType_ID(final boolean isReceipt)
	{
		setIsReceipt(isReceipt);
		final String sql = "SELECT C_DocType_ID FROM C_DocType WHERE AD_Client_ID=? AND DocBaseType=?"
				// metas: added IsActive='Y'
				// metas: begin: first return doctypes from current Org
				+ " ORDER BY IsDefault DESC, "
				+ " CASE "
				+ "   WHEN AD_Org_ID = ? THEN 0 "
				+ "   ELSE 1 "
				+ " END ";
		// metas: end
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getAD_Client_ID());
			if (isReceipt)
			{
				pstmt.setString(2, X_C_DocType.DOCBASETYPE_ARReceipt);
			}
			else
			{
				pstmt.setString(2, X_C_DocType.DOCBASETYPE_APPayment);
			}
			pstmt.setInt(3, getAD_Org_ID()); // metas
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				setC_DocType_ID(rs.getInt(1));
			}
			else
			{
				log.warn("setDocType - NOT found - isReceipt=" + isReceipt);
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}	// setC_DocType_ID

	/**
	 * Set Document Type
	 *
	 * @param C_DocType_ID doc type
	 */
	@Override
	public void setC_DocType_ID(final int C_DocType_ID)
	{
		// if (getDocumentNo() != null && getC_DocType_ID() != C_DocType_ID)
		// setDocumentNo(null);
		super.setC_DocType_ID(C_DocType_ID);
	}	// setC_DocType_ID

	/**
	 * Verify Document Type with Invoice
	 *
	 * @param pAllocs
	 * @return true if ok
	 */
	private boolean verifyDocType(final MPaymentAllocate[] pAllocs)
	{
		if (getC_DocType_ID() == 0)
		{
			return false;
		}
		//
		Boolean documentSO = null;
		// Check Invoice First
		if (getC_Invoice_ID() > 0)
		{
			final String sql = "SELECT idt.IsSOTrx "
					+ "FROM C_Invoice i"
					+ " INNER JOIN C_DocType idt ON (i.C_DocType_ID=idt.C_DocType_ID) "
					+ "WHERE i.C_Invoice_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, getC_Invoice_ID());
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					documentSO = new Boolean("Y".equals(rs.getString(1)));
				}
			}
			catch (final Exception e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		} 	// now Order - in Adempiere is allowed to pay PO or receive SO
		else if (getC_Order_ID() > 0)
		{
			final String sql = "SELECT odt.IsSOTrx "
					+ "FROM C_Order o"
					+ " INNER JOIN C_DocType odt ON (o.C_DocType_ID=odt.C_DocType_ID) "
					+ "WHERE o.C_Order_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, getC_Order_ID());
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					documentSO = new Boolean("Y".equals(rs.getString(1)));
				}
			}
			catch (final Exception e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		} 	// now Charge
		else if (getC_Charge_ID() > 0)
		{
			// do nothing about charge
		}  // now payment allocate
		else
		{
			if (pAllocs.length > 0)
			{
				for (final MPaymentAllocate pAlloc : pAllocs)
				{
					final String sql = "SELECT idt.IsSOTrx "
							+ "FROM C_Invoice i"
							+ " INNER JOIN C_DocType idt ON (i.C_DocType_ID=idt.C_DocType_ID) "
							+ "WHERE i.C_Invoice_ID=?";
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					try
					{
						pstmt = DB.prepareStatement(sql, get_TrxName());
						pstmt.setInt(1, pAlloc.getC_Invoice_ID());
						rs = pstmt.executeQuery();
						if (rs.next())
						{
							if (documentSO != null)
							{ // already set, compare with current
								if (documentSO.booleanValue() != ("Y".equals(rs.getString(1))))
								{
									return false;
								}
							}
							else
							{
								documentSO = new Boolean("Y".equals(rs.getString(1)));
							}
						}
					}
					catch (final Exception e)
					{
						log.error(sql, e);
					}
					finally
					{
						DB.close(rs, pstmt);
						rs = null;
						pstmt = null;
					}
				}
			}
		}

		// DocumentType
		Boolean paymentSO = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String sql = "SELECT IsSOTrx "
				+ "FROM C_DocType "
				+ "WHERE C_DocType_ID=?";
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_DocType_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				paymentSO = new Boolean("Y".equals(rs.getString(1)));
			}
		}
		catch (final Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// No Payment info
		if (paymentSO == null)
		{
			return false;
		}
		setIsReceipt(paymentSO.booleanValue());

		// 07564
		// Exception: in case we want to make an incoming payment (Zahlungseigang) with a credit memo incoming invoice (eingangsrechnung)
		// it is fine to have the 2 doctypes of different SO Trx

		if (getC_Invoice_ID() > 0)  // task 08438: avoid NPE when getting the doctype
		{
			final I_C_Invoice invoice = getC_Invoice();
			final I_C_DocType doctype = invoice.getC_DocType();

			if (X_C_DocType.DOCBASETYPE_APCreditMemo.equals(doctype.getDocBaseType()))
			{
				// in this case, the invoice doc type is credit memo, with IsSOTrx = false
				// if the payment doc type is IsSOTrx = true, the doctypes are ok
				if (paymentSO)
				{
					return true;
				}
			}
			else if (X_C_DocType.DOCBASETYPE_ARCreditMemo.equals(doctype.getDocBaseType()))
			{
				// in this case, the invoice doc type is credit memo, with IsSOTrx = true
				// if the payment doc type is IsSOTrx = false, the doctypes are ok
				if (!paymentSO)
				{
					return true;
				}
			}
		}
		// We have an Invoice .. and it does not match
		if (documentSO != null
				&& documentSO.booleanValue() != paymentSO.booleanValue())
		{
			return false;
		}
		// OK
		return true;
	}	// verifyDocType

	/**
	 * Verify Payment Allocate is ignored (must not exists) if the payment header has charge/invoice/order
	 *
	 * @param pAllocs
	 * @return true if ok
	 */
	private boolean verifyPaymentAllocateVsHeader(final MPaymentAllocate[] pAllocs)
	{
		if (pAllocs.length > 0)
		{
			if (getC_Charge_ID() > 0 || getC_Invoice_ID() > 0 || getC_Order_ID() > 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Verify Payment Allocate Sum must be equal to the Payment Amount
	 *
	 * @param pAllocs
	 * @return true if ok
	 */
	private boolean verifyPaymentAllocateSum(final MPaymentAllocate[] pAllocs)
	{
		BigDecimal sumPaymentAllocates = BigDecimal.ZERO;
		if (pAllocs.length > 0)
		{
			for (final MPaymentAllocate pAlloc : pAllocs)
			{
				sumPaymentAllocates = sumPaymentAllocates.add(pAlloc.getAmount());
			}
			if (getPayAmt().compareTo(sumPaymentAllocates) != 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Get Document Status
	 *
	 * @return Document Status Clear Text
	 */
	public String getDocStatusName()
	{
		return Services.get(IADReferenceDAO.class).retrieveListNameTrl(getCtx(), 131, getDocStatus());
	}	// getDocStatusName

	/**
	 * Get Name of Credit Card
	 *
	 * @return Name
	 */
	public String getCreditCardName()
	{
		return getCreditCardName(getCreditCardType());
	}	// getCreditCardName

	/**
	 * Get Name of Credit Card
	 *
	 * @param CreditCardType credit card type
	 * @return Name
	 */
	public String getCreditCardName(final String CreditCardType)
	{
		if (CreditCardType == null)
		{
			return "--";
		}
		else if (CREDITCARDTYPE_MasterCard.equals(CreditCardType))
		{
			return "MasterCard";
		}
		else if (CREDITCARDTYPE_Visa.equals(CreditCardType))
		{
			return "Visa";
		}
		else if (CREDITCARDTYPE_Amex.equals(CreditCardType))
		{
			return "Amex";
		}
		else if (CREDITCARDTYPE_ATM.equals(CreditCardType))
		{
			return "ATM";
		}
		else if (CREDITCARDTYPE_Diners.equals(CreditCardType))
		{
			return "Diners";
		}
		else if (CREDITCARDTYPE_Discover.equals(CreditCardType))
		{
			return "Discover";
		}
		else if (CREDITCARDTYPE_PurchaseCard.equals(CreditCardType))
		{
			return "PurchaseCard";
		}
		return "?" + CreditCardType + "?";
	}	// getCreditCardName

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(final String description)
	{
		final String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	}	// addDescription

	/**
	 * Get Pay Amt
	 *
	 * @param absolute if true the absolute amount (i.e. negative if payment)
	 * @return amount
	 */
	public BigDecimal getPayAmt(final boolean absolute)
	{
		if (isReceipt())
		{
			return super.getPayAmt();
		}
		return super.getPayAmt().negate();
	}	// getPayAmt

	/**
	 * Get Pay Amt in cents
	 *
	 * @return amount in cents
	 */
	public int getPayAmtInCents()
	{
		final BigDecimal bd = super.getPayAmt().multiply(Env.ONEHUNDRED);
		return bd.intValue();
	}	// getPayAmtInCents

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
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
	}	// process

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
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
	}	// unlockIt

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
	}	// invalidateIt

	/**************************************************************************
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
		if (!MPeriod.isOpen(getCtx(), getDateAcct(),
				isReceipt() ? X_C_DocType.DOCBASETYPE_ARReceipt : X_C_DocType.DOCBASETYPE_APPayment, getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return IDocument.STATUS_Invalid;
		}

		// Unsuccessful Online Payment
		if (isOnline() && !isApproved())
		{
			if (getR_Result() != null)
			{
				m_processMsg = "@OnlinePaymentFailed@";
			}
			else
			{
				m_processMsg = "@PaymentNotProcessed@";
			}
			return IDocument.STATUS_Invalid;
		}

		// Waiting Payment - Need to create Invoice & Shipment
		if (getC_Order_ID() != 0 && getC_Invoice_ID() == 0)
		{	// see WebOrder.process
			final MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
			if (DOCSTATUS_WaitingPayment.equals(order.getDocStatus()))
			{
				order.setC_Payment_ID(getC_Payment_ID());
				order.setDocAction(X_C_Order.DOCACTION_WaitComplete);
				order.set_TrxName(get_TrxName());
				// metas: Prepayment Order shall receive their DateAcct from
				// Payment, because the Order Dateacct could be far back
				order.setDateAcct(getDateAcct());
				// metas end
				// boolean ok =
				order.processIt(X_C_Order.DOCACTION_WaitComplete);
				m_processMsg = order.getProcessMsg();
				order.saveEx(get_TrxName());
				// Set Invoice
				final MInvoice[] invoices = order.getInvoices();
				final int length = invoices.length;
				if (length > 0)
				{
					setC_Invoice_ID(invoices[length - 1].getC_Invoice_ID());
				}
				//
				if (getC_Invoice_ID() == 0)
				{
					// metas (2009-0027-G5): with our new So Sub Type,
					// the invoice hasn't been created yet
					// m_processMsg = "@NotFound@ @C_Invoice_ID@";
					// return DocAction.STATUS_Invalid;
				}
			} 	// WaitingPayment
		}

		final MPaymentAllocate[] pAllocs = MPaymentAllocate.get(this);

		// Consistency of Invoice / Document Type and IsReceipt
		if (!verifyDocType(pAllocs))
		{
			m_processMsg = "@PaymentDocTypeInvoiceInconsistent@";
			return IDocument.STATUS_Invalid;
		}

		// Payment Allocate is ignored if charge/invoice/order exists in header
		if (!verifyPaymentAllocateVsHeader(pAllocs))
		{
			m_processMsg = "@PaymentAllocateIgnored@";
			return IDocument.STATUS_Invalid;
		}

		// Payment Amount must be equal to sum of Allocate amounts
		if (!verifyPaymentAllocateSum(pAllocs))
		{
			m_processMsg = "@PaymentAllocateSumInconsistent@";
			return IDocument.STATUS_Invalid;
		}

		// Do not pay when Credit Stop/Hold
		checkCreditLimit();

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
	}	// prepareIt

	private void checkCreditLimit()
	{
		if (isReceipt())
		{
			return;
		}

		final I_C_BPartner partner = InterfaceWrapperHelper.create(getCtx(), getC_BPartner_ID(), I_C_BPartner.class, get_TrxName());
		final IBPartnerStats stats = Services.get(IBPartnerStatsDAO.class).retrieveBPartnerStats(partner);
		final String soCreditStatus = stats.getSOCreditStatus();
		final BigDecimal crediUsed = stats.getSOCreditUsed();
		final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
		final BigDecimal creditLimit = creditLimitRepo.getCreditLimitByBPartner(getC_BPartner(), getDateTrx());


		if (Services.get(IBPartnerStatsBL.class).isCreditStopSales(stats, getPayAmt(true), getDateTrx()))
		{
			throw new AdempiereException("@BPartnerCreditStop@ - @SO_CreditUsed@="
					+ stats.getSOCreditUsed()
					+ ", @SO_CreditLimit@=" + creditLimit);
		}

		if (X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold.equals(soCreditStatus))
		{
			throw new AdempiereException("@BPartnerCreditHold@ - @SO_CreditUsed@="
					+ crediUsed
					+ ", @SO_CreditLimit@=" + creditLimit);
		}
	}

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
	}	// approveIt

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
	}	// rejectIt

	/**************************************************************************
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

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}
		log.debug("Completed: {}", this);

		// Charge Handling
		if (getC_Charge_ID() != 0)
		{
			setIsAllocated(true);
		}
		else
		{
			allocateIt();	// Create Allocation Records
			testAllocation();
		}

		// Project update
		if (getC_Project_ID() != 0)
		{
			// MProject project = new MProject(getCtx(), getC_Project_ID());
		}
		// Update BP for Prepayments
		if (getC_BPartner_ID() != 0 && getC_Invoice_ID() == 0 && getC_Charge_ID() == 0)
		{
			// task FRESH-152. Update bpartner stats
			final I_C_BPartner partner = InterfaceWrapperHelper.create(getCtx(), getC_BPartner_ID(), I_C_BPartner.class, get_TrxName());
			Services.get(IBPartnerStatisticsUpdater.class)
					.updateBPartnerStatistics(Collections.singleton(partner.getC_BPartner_ID()));
		}

		// Counter Doc
		final MPayment counter = createCounterDoc();
		if (counter != null)
		{
			m_processMsg += " @CounterDoc@: @C_Payment_ID@=" + counter.getDocumentNo();
		}

		// @Trifon - CashPayments
		// if ( getTenderType().equals("X") ) {
		if (isCashTrx() && ! Services.get(ISysConfigBL.class).getBooleanValue("CASH_AS_PAYMENT", true, getAD_Client_ID()))
		{
			// Create Cash Book entry
			if (getC_CashBook_ID() <= 0)
			{
				throw new FillMandatoryException("C_CashBook_ID");
				// m_processMsg = "@NoCashBook@";
				// return DocAction.STATUS_Invalid;
			}
			final MCash cash = MCash.get(getCtx(), getAD_Org_ID(), getDateAcct(), getC_Currency_ID(), get_TrxName());
			if (cash == null || cash.get_ID() == 0)
			{
				m_processMsg = "@NoCashBook@";
				return IDocument.STATUS_Invalid;
			}
			final MCashLine cl = new MCashLine(cash);
			cl.setCashType(X_C_CashLine.CASHTYPE_GeneralReceipts);
			cl.setDescription("Generated From Payment #" + getDocumentNo());
			cl.setC_Currency_ID(getC_Currency_ID());
			cl.setC_Payment_ID(getC_Payment_ID()); // Set Reference to payment.
			final StringBuffer info = new StringBuffer();
			info.append("Cash journal ( ")
					.append(cash.getDocumentNo()).append(" )");
			m_processMsg = info.toString();
			// Amount
			final BigDecimal amt = this.getPayAmt();
			/*
			 * MDocType dt = MDocType.get(getCtx(), invoice.getC_DocType_ID()); if (MDocType.DOCBASETYPE_APInvoice.equals( dt.getDocBaseType() ) || MDocType.DOCBASETYPE_ARCreditMemo.equals(
			 * dt.getDocBaseType() ) ) { amt = amt.negate(); }
			 */
			cl.setAmount(amt);
			//
			cl.setDiscountAmt(BigDecimal.ZERO);
			cl.setWriteOffAmt(BigDecimal.ZERO);
			cl.setIsGenerated(true);

			if (!cl.save(get_TrxName()))
			{
				m_processMsg = "Could not save Cash Journal Line";
				return IDocument.STATUS_Invalid;
			}
		}
		// End Trifon - CashPayments
		// metas: begin: CashPayments
		// NOTE: cash as payment is handled in de.metas.banking module. See de.metas.banking.payment.modelvalidator.C_Payment.createCashStatementLineIfNeeded(I_C_Payment).
		// metas: end: CashPayments

		// User Validation
		final String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return IDocument.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(DOCACTION_Reverse_Correct); // issue #347
		return IDocument.STATUS_Completed;
	}	// completeIt

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo()
	{
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete())
		{
			setDateTrx(new Timestamp(System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setTrxName(get_TrxName())
					.setDocumentModel(this)
					.setFailOnError(false)
					.build();
			if (value != null && value != IDocumentNoBuilder.NO_DOCUMENTNO)
			{
				setDocumentNo(value);
			}
		}
	}

	/**
	 * Create Counter Document
	 *
	 * @return payment
	 */
	private MPayment createCounterDoc()
	{
		// Is this a counter doc ?
		if (getRef_Payment_ID() != 0)
		{
			return null;
		}

		// Org Must be linked to BPartner
		final MOrg org = MOrg.get(getCtx(), getAD_Org_ID());
		final int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(get_TrxName());
		if (counterC_BPartner_ID == 0)
		{
			return null;
		}
		// Business Partner needs to be linked to Org
		final MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(), get_TrxName());
		final int counterAD_Org_ID = bp.getAD_OrgBP_ID_Int();
		if (counterAD_Org_ID == 0)
		{
			return null;
		}

		final MBPartner counterBP = new MBPartner(getCtx(), counterC_BPartner_ID, get_TrxName());
		// MOrgInfo counterOrgInfo = MOrgInfo.get(getCtx(), counterAD_Org_ID);
		log.info("Counter BP=" + counterBP.getName());

		// Document Type
		int C_DocTypeTarget_ID = 0;
		final MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(), getC_DocType_ID());
		if (counterDT != null)
		{
			log.debug(counterDT.toString());
			if (!counterDT.isCreateCounter() || !counterDT.isValid())
			{
				return null;
			}
			C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
		}
		else
		// indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(), getC_DocType_ID());
			log.debug("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
			if (C_DocTypeTarget_ID <= 0)
			{
				return null;
			}
		}

		// Deep Copy
		final MPayment counter = new MPayment(getCtx(), 0, get_TrxName());
		counter.setAD_Org_ID(counterAD_Org_ID);
		counter.setC_BPartner_ID(counterBP.getC_BPartner_ID());
		counter.setIsReceipt(!isReceipt());
		counter.setC_DocType_ID(C_DocTypeTarget_ID);
		counter.setTrxType(getTrxType());
		counter.setTenderType(getTenderType());
		//
		counter.setPayAmt(getPayAmt());
		counter.setDiscountAmt(getDiscountAmt());
		counter.setTaxAmt(getTaxAmt());
		counter.setWriteOffAmt(getWriteOffAmt());
		counter.setIsOverUnderPayment(isOverUnderPayment());
		counter.setOverUnderAmt(getOverUnderAmt());
		counter.setC_Currency_ID(getC_Currency_ID());
		counter.setC_ConversionType_ID(getC_ConversionType_ID());
		//
		counter.setDateTrx(getDateTrx());
		counter.setDateAcct(getDateAcct());
		counter.setRef_Payment_ID(getC_Payment_ID());
		//
		final String sql = "SELECT C_BP_BankAccount_ID FROM C_BP_BankAccount "
				+ "WHERE C_Currency_ID=? AND AD_Org_ID IN (0,?) AND IsActive='Y' "
				+ "ORDER BY IsDefault DESC";
		final int C_BP_BankAccount_ID = DB.getSQLValue(get_TrxName(), sql, getC_Currency_ID(), counterAD_Org_ID);
		counter.setC_BP_BankAccount_ID(C_BP_BankAccount_ID);

		// References
		counter.setC_Activity_ID(getC_Activity_ID());
		counter.setC_Campaign_ID(getC_Campaign_ID());
		counter.setC_Project_ID(getC_Project_ID());
		counter.setUser1_ID(getUser1_ID());
		counter.setUser2_ID(getUser2_ID());
		counter.saveEx(get_TrxName());
		log.debug(counter.toString());
		setRef_Payment_ID(counter.getC_Payment_ID());

		// Document Action
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
	}	// createCounterDoc

	/**
	 * Allocate It. Only call when there is NO allocation as it will create duplicates. If an invoice exists, it allocates that otherwise it allocates Payment Selection.
	 *
	 * @return true if allocated
	 */
	public boolean allocateIt()
	{
		final boolean result = allocateIt0();
		Services.get(IPrepayOrderAllocationBL.class).paymentAfterAllocateIt(this, result);
		return result;
	}

	private boolean allocateIt0()
	{
		// Create invoice Allocation - See also MCash.completeIt
		if (getC_Invoice_ID() != 0)
		{
			return allocateInvoice();
		}

		if (getC_Order_ID() != 0)
		{
			return false;
		}

		// Allocate to multiple Payments based on entry
		final MPaymentAllocate[] pAllocs = MPaymentAllocate.get(this);
		if (pAllocs.length == 0)
		{
			return false;
		}

		final MAllocationHdr alloc = new MAllocationHdr(getCtx(), false,
				getDateTrx(), getC_Currency_ID(),
				Services.get(IMsgBL.class).translate(getCtx(), "C_Payment_ID") + ": " + getDocumentNo(),
				get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		if (!alloc.save())
		{
			log.error("P.Allocations not created");
			return false;
		}
		// Lines
		for (final MPaymentAllocate pa : pAllocs)
		{
			MAllocationLine aLine = null;
			if (isReceipt())
			{
				aLine = new MAllocationLine(alloc, pa.getAmount(),
						pa.getDiscountAmt(), pa.getWriteOffAmt(), pa.getOverUnderAmt());
			}
			else
			{
				aLine = new MAllocationLine(alloc, pa.getAmount().negate(),
						pa.getDiscountAmt().negate(), pa.getWriteOffAmt().negate(), pa.getOverUnderAmt().negate());
			}
			aLine.setDocInfo(pa.getC_BPartner_ID(), 0, pa.getC_Invoice_ID());
			aLine.setPaymentInfo(getC_Payment_ID(), 0);
			if (!aLine.save(get_TrxName()))
			{
				log.warn("P.Allocations - line not saved");
			}
			else
			{
				pa.setC_AllocationLine_ID(aLine.getC_AllocationLine_ID());
				pa.saveEx();
			}
		}
		// Should start WF
		alloc.processIt(IDocument.ACTION_Complete);
		m_processMsg = "@C_AllocationHdr_ID@: " + alloc.getDocumentNo();
		return alloc.save(get_TrxName());
	}	// allocateIt

	/**
	 * Allocate single AP/AR Invoice
	 *
	 * @return true if allocated
	 */
	private boolean allocateInvoice()
	{
		// 04627 begin (commented out old code)
		// calculate actual allocation
		// BigDecimal allocationAmt = getPayAmt(); // underpayment
		// when allocating an invoice, we don't want to allocate more than the invoice's open amount
		final I_C_Invoice invoice = getC_Invoice();
		final BigDecimal invoiceOpenAmt = Services.get(IAllocationDAO.class).retrieveOpenAmt(invoice, false);

		// note: zero is ok, but with negative, i don't see the case and don't know what to do
		Check.errorIf(invoiceOpenAmt.signum() < 0, "{} has a negative open amount = {}", invoice, invoiceOpenAmt);
		Check.errorIf(getPayAmt().signum() < 0, "{} has a negative PayAmt = {}", this, getPayAmt());

		final BigDecimal allocationAmt = getPayAmt().min(invoiceOpenAmt);

		// 04627 end

		// 04614 (commented out old code)
		// OverUnderAmt shall not be part of the allocation amount. It's just an information!
		// if (getOverUnderAmt().signum() < 0 && getPayAmt().signum() > 0)
		// allocationAmt = allocationAmt.add(getOverUnderAmt()); // overpayment (negative)

		// we're not yet there :-(
//		// @formatter:off
//		final I_C_AllocationHdr alloc =
//				Services.get(IAllocationBL.class).newBuilder(this)
//					.setAD_Org_ID(getAD_Org_ID())
//					.setDateTrx(getDateTrx())
//					.setC_Currency_ID(getC_Currency_ID())
//					.setIsManual(false)
//					.setDescription(Msg.translate(getCtx(), "C_Payment_ID") + ": " + getDocumentNo() + " [1]")
//					.addLine()
//						.setAmount(isReceipt() ? allocationAmt: allocationAmt.negate())
//						.setDiscountAmt(isReceipt() ? getDiscountAmt(): getDiscountAmt().negate())
//						.setWriteOffAmt(isReceipt() ? getWriteOffAmt():getWriteOffAmt().negate())
//						.setOverUnderAmt(isReceipt() ? getOverUnderAmt(): getOverUnderAmt().negate())
//						.setC_Payment_ID(getC_Payment_ID())
//						.setC_BPartner_ID(getC_BPartner_ID())
//						.setC_Invoice_ID(getC_Invoice_ID())
//					.lineDone()
//					.create(true);
//		// @formatter:on
		//
		final MAllocationHdr alloc = new MAllocationHdr(getCtx(), false,
				getDateTrx(), getC_Currency_ID(),
				Services.get(IMsgBL.class).translate(getCtx(), "C_Payment_ID") + ": " + getDocumentNo() + " [1]", get_TrxName());

		// task 09643
		// When the Allocation has both invoice and payment, allocation's accounting date must e the max between the invoice date and payment date

		if (invoice != null)
		{
			Timestamp dateAcct = getDateAcct();

			final Timestamp invoiceDateAcct = invoice.getDateAcct();

			if (invoiceDateAcct.after(dateAcct))
			{
				dateAcct = invoiceDateAcct;
			}

			alloc.setDateAcct(dateAcct);

			// allocation's trx date must e the max between the invoice date and payment date

			Timestamp dateTrx = getDateTrx();

			final Timestamp invoiceDateTrx = invoice.getDateInvoiced();

			if (invoiceDateTrx.after(dateTrx))
			{
				dateTrx = invoiceDateTrx;
			}

			alloc.setDateTrx(dateTrx);
		}

		alloc.setAD_Org_ID(getAD_Org_ID());
		alloc.saveEx();
		MAllocationLine aLine = null;
		if (isReceipt())
		{
			aLine = new MAllocationLine(alloc, allocationAmt,
					getDiscountAmt(), getWriteOffAmt(), getOverUnderAmt());
		}
		else
		{
			aLine = new MAllocationLine(alloc, allocationAmt.negate(),
					getDiscountAmt().negate(), getWriteOffAmt().negate(), getOverUnderAmt().negate());
		}
		aLine.setDocInfo(getC_BPartner_ID(), 0, getC_Invoice_ID());
		aLine.setC_Payment_ID(getC_Payment_ID());
		aLine.saveEx(get_TrxName());
		// Should start WF
		alloc.processIt(IDocument.ACTION_Complete);
		alloc.saveEx(get_TrxName());
		m_processMsg = "@C_AllocationHdr_ID@: " + alloc.getDocumentNo();

		// Get Project from Invoice
		final int C_Project_ID = DB.getSQLValue(get_TrxName(),
				"SELECT MAX(C_Project_ID) FROM C_Invoice WHERE C_Invoice_ID=?", getC_Invoice_ID());
		if (C_Project_ID > 0 && getC_Project_ID() == 0)
		{
			setC_Project_ID(C_Project_ID);
		}
		else if (C_Project_ID > 0 && getC_Project_ID() > 0 && C_Project_ID != getC_Project_ID())
		{
			log.warn("Invoice C_Project_ID=" + C_Project_ID
					+ " <> Payment C_Project_ID=" + getC_Project_ID());
		}
		return true;
	}	// allocateInvoice

	/**
	 * De-allocate Payment. Unkink Invoices and Orders and delete Allocations
	 */
	private void deAllocate()
	{
		if (getC_Order_ID() != 0)
		{
			setC_Order_ID(0);
		}
		// if (getC_Invoice_ID() == 0)
		// return;
		// De-Allocate all
		final MAllocationHdr[] allocations = MAllocationHdr.getOfPayment(getCtx(),
				getC_Payment_ID(), get_TrxName());
		log.debug("#" + allocations.length);
		for (int i = 0; i < allocations.length; i++)
		{
			final String docStatus = allocations[i].getDocStatus();

			// 07570: Skip allocations which were already Reversed or Voided
			if (IDocument.STATUS_Reversed.equals(docStatus)
					|| IDocument.STATUS_Voided.equals(docStatus))
			{
				continue;
			}

			allocations[i].set_TrxName(get_TrxName());
			allocations[i].setDocAction(IDocument.ACTION_Reverse_Correct);
			if (!allocations[i].processIt(IDocument.ACTION_Reverse_Correct))
			{
				throw new AdempiereException(allocations[i].getProcessMsg());
			}
			allocations[i].saveEx();
		}

		// Unlink (in case allocation did not get it)
		if (getC_Invoice_ID() != 0)
		{
			// Invoice
			String sql = "UPDATE C_Invoice "
					+ "SET C_Payment_ID = NULL, IsPaid='N' "
					+ "WHERE C_Invoice_ID=" + getC_Invoice_ID()
					+ " AND C_Payment_ID=" + getC_Payment_ID();
			int no = DB.executeUpdate(sql, get_TrxName());
			if (no != 0)
			{
				log.debug("Unlink Invoice #" + no);
			}
			// Order
			sql = "UPDATE C_Order o "
					+ "SET C_Payment_ID = NULL "
					+ "WHERE EXISTS (SELECT * FROM C_Invoice i "
					+ "WHERE o.C_Order_ID=i.C_Order_ID AND i.C_Invoice_ID=" + getC_Invoice_ID() + ")"
					+ " AND C_Payment_ID=" + getC_Payment_ID();
			no = DB.executeUpdate(sql, get_TrxName());
			if (no != 0)
			{
				log.debug("Unlink Order #" + no);
			}
		}
		//
		setC_Invoice_ID(0);
		setIsAllocated(false);
	}	// deallocate

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

		//
		// Make sure not already closed
		final String docStatus = getDocStatus();
		if (DOCSTATUS_Closed.equals(docStatus)
				|| DOCSTATUS_Reversed.equals(docStatus)
				|| DOCSTATUS_Voided.equals(docStatus))
		{
			m_processMsg = "Document Closed: " + docStatus;
			setDocAction(DOCACTION_None);
			return false;
		}

		//
		// Make sure the payment was not already reconciled
		if (isReconciled())
		{
			throw new AdempiereException("@void.payment@");
		}

		//
		// Not Processed
		if (DOCSTATUS_Drafted.equals(docStatus)
				|| DOCSTATUS_Invalid.equals(docStatus)
				|| DOCSTATUS_InProgress.equals(docStatus)
				|| DOCSTATUS_Approved.equals(docStatus)
				|| DOCSTATUS_NotApproved.equals(docStatus))
		{
			addDescription(Services.get(IMsgBL.class).getMsg(getCtx(), "Voided") + " (" + getPayAmt() + ")");
			setPayAmt(BigDecimal.ZERO);
			setDiscountAmt(BigDecimal.ZERO);
			setWriteOffAmt(BigDecimal.ZERO);
			setOverUnderAmt(BigDecimal.ZERO);
			setIsAllocated(false);
			// Unlink & De-Allocate
			deAllocate();
		}
		else
		{
			return reverseCorrectIt();
		}

		//
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	// voidIt

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
	}	// closeIt

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

		// Std Period open?
		Timestamp dateAcct = getDateAcct();
		if (!MPeriod.isOpen(getCtx(), dateAcct,
				isReceipt() ? X_C_DocType.DOCBASETYPE_ARReceipt : X_C_DocType.DOCBASETYPE_APPayment, getAD_Org_ID()))
		{
			dateAcct = new Timestamp(System.currentTimeMillis());
		}

		//
		// Make sure the payment was not already reconciled
		if (isReconciled())
		{
			throw new AdempiereException("@void.payment@");
		}

		// Create Reversal
		final MPayment reversal = new MPayment(getCtx(), 0, get_TrxName());
		copyValues(this, reversal);
		reversal.setClientOrg(this);
		reversal.setC_Order_ID(0);
		reversal.setC_Invoice_ID(0);
		reversal.setDateAcct(dateAcct);
		//
		reversal.setDocumentNo(getDocumentNo() + REVERSE_INDICATOR);	// indicate reversals
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DOCACTION_Complete);
		//
		reversal.setPayAmt(getPayAmt().negate());
		reversal.setDiscountAmt(getDiscountAmt().negate());
		reversal.setWriteOffAmt(getWriteOffAmt().negate());
		reversal.setOverUnderAmt(getOverUnderAmt().negate());
		//
		reversal.setIsAllocated(true);
		// reversal.setIsReconciled(reconciled); // will be handled by banking module
		reversal.setIsOnline(false);
		reversal.setIsApproved(true);
		reversal.setR_PnRef(null);
		reversal.setR_Result(null);
		reversal.setR_RespMsg(null);
		reversal.setR_AuthCode(null);
		reversal.setR_Info(null);
		reversal.setProcessing(false);
		reversal.setOProcessing("N");
		reversal.setProcessed(false);
		reversal.setPosted(false);
		reversal.setDescription(getDescription());
		reversal.addDescription("{->" + getDocumentNo() + ")");
		// FR [ 1948157 ]
		reversal.setReversal_ID(getC_Payment_ID());
		reversal.saveEx(get_TrxName());
		// Post Reversal
		if (!reversal.processIt(IDocument.ACTION_Complete))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return false;
		}
		reversal.closeIt();
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.save(get_TrxName());

		// Unlink & De-Allocate
		deAllocate();
		// setIsReconciled(reconciled); // will be handled by banking module
		setIsAllocated(true);	// the allocation below is overwritten
		// Set Status
		addDescription("(" + reversal.getDocumentNo() + "<-)");
		setDocStatus(DOCSTATUS_Reversed);
		setDocAction(DOCACTION_None);
		setProcessed(true);
		// FR [ 1948157 ]
		setReversal_ID(reversal.getC_Payment_ID());

		// Create automatic Allocation
		final MAllocationHdr alloc = new MAllocationHdr(getCtx(), false,
				getDateTrx(), getC_Currency_ID(),
				Services.get(IMsgBL.class).translate(getCtx(), "C_Payment_ID") + ": " + reversal.getDocumentNo(), get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		if (!alloc.save())
		{
			log.warn("Automatic allocation - hdr not saved");
		}
		else
		{
			// Original Allocation
			MAllocationLine aLine = new MAllocationLine(alloc, getPayAmt(true),
					BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
			aLine.setDocInfo(getC_BPartner_ID(), 0, 0);
			aLine.setPaymentInfo(getC_Payment_ID(), 0);
			if (!aLine.save(get_TrxName()))
			{
				log.warn("Automatic allocation - line not saved");
			}
			// Reversal Allocation
			aLine = new MAllocationLine(alloc, reversal.getPayAmt(true),
					BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
			aLine.setDocInfo(reversal.getC_BPartner_ID(), 0, 0);
			aLine.setPaymentInfo(reversal.getC_Payment_ID(), 0);
			if (!aLine.save(get_TrxName()))
			{
				log.warn("Automatic allocation - reversal line not saved");
			}
		}
		alloc.processIt(IDocument.ACTION_Complete);
		alloc.save(get_TrxName());
		//
		final StringBuffer info = new StringBuffer(reversal.getDocumentNo());
		info.append(" - @C_AllocationHdr_ID@: ").append(alloc.getDocumentNo());

		// FRESH-152 Update BPartner stats
		if (getC_BPartner_ID() > 0)
		{
			Services.get(IBPartnerStatisticsUpdater.class)
					.updateBPartnerStatistics(Collections.singleton(getC_BPartner_ID()));

		}
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		m_processMsg = info.toString();
		return true;
	}	// reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 *
	 * @return false, not supported
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
	}	// reverseAccrualIt

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

		if (!reverseCorrectIt())
		{
			return false;
		}

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		return true;
	}	// reActivateIt

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer("MPayment[");
		sb.append(get_ID()).append("-").append(getDocumentNo())
				.append(",Receipt=").append(isReceipt())
				.append(",PayAmt=").append(getPayAmt())
				.append(",Discount=").append(getDiscountAmt())
				.append(",WriteOff=").append(getWriteOffAmt())
				.append(",OverUnder=").append(getOverUnderAmt());
		return sb.toString();
	}	// toString

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		final MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	// getDocumentInfo

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
			final File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (final Exception e)
		{
			log.error("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	// getPDF

	/**
	 * Create PDF file
	 *
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(final File file)
	{
		// ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.PAYMENT, getC_Payment_ID());
		// if (re == null)
		return null;
		// return re.getPDF(file);
	}	// createPDF

	/*************************************************************************
	 * Get Summary
	 *
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		// : Total Lines = 123.00 (#1)
		sb.append(": ")
				.append(Services.get(IMsgBL.class).translate(getCtx(), "PayAmt")).append("=").append(getPayAmt())
				.append(",").append(Services.get(IMsgBL.class).translate(getCtx(), "WriteOffAmt")).append("=").append(getWriteOffAmt());
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
		return sb.toString();
	}	// getSummary

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}	// getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getCreatedBy();
	}	// getDoc_User_ID

	/**
	 * Get Document Approval Amount
	 *
	 * @return amount payment(AP) or write-off(AR)
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		if (isReceipt())
		{
			return getWriteOffAmt();
		}
		return getPayAmt();
	}	// getApprovalAmt

	// metas: begin
	// metas 00036
	// adding a place where credit card data can be stored in memory.
	// The data might not be stored to DB due to privacy protection rules.
	private static class VolatileCCData
	{
		private String creditCardNumber;
		private String creditCardVV;
	}

	private final VolatileCCData volatileCCData = new VolatileCCData();

	// metas end

	@Override
	public void setC_Order_ID(final int C_Order_ID)
	{

		// first things first
		super.setC_Order_ID(C_Order_ID);

		// checking doc type of C_Order_ID=" + C_Order_ID
		final I_C_Order order = getC_Order();
		if(order == null || order.getC_Order_ID() <= 0)
		{
			return;
		}

		final I_C_DocType orderDocType = order.getC_DocType();
		if (orderDocType == null)
		{
			return; // shall not happen
		}

		final String orderDocSubType = orderDocType.getDocSubType();
		if (!X_C_DocType.DOCSUBTYPE_POSOrder.equals(orderDocSubType)
				&& !X_C_DocType.DOCSUBTYPE_OnCreditOrder.equals(orderDocSubType)
				&& !X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(orderDocSubType))
		{

			// nothing to do
			return;
		}

		// retrieving the order's invoice
		final String whereClause = I_C_Invoice.COLUMNNAME_C_Order_ID + "=?"
				+ " AND " + I_C_Invoice.COLUMNNAME_C_DocType_ID + "=?";

		final Object[] parameters = { C_Order_ID, orderDocType.getC_DocTypeInvoice_ID() };

		final List<MInvoice> invoices = new Query(getCtx(), I_C_Invoice.Table_Name, whereClause, get_TrxName())
				.setParameters(parameters)
				.setClient_ID()
				.list();

		if (invoices.size() == 1)
		{
			System.out.println("invoices.get(0).get_ID():" + invoices.get(0).get_ID());
			setC_Invoice_ID(invoices.get(0).getC_Invoice_ID());
		}
	}

	// metas 00036
	public String getVolatileCreditCardNumber()
	{
		if (volatileCCData.creditCardNumber == null)
		{
			return getCreditCardNumber();
		}
		return volatileCCData.creditCardNumber;
	}

	public String getVolatileCreditCardVV()
	{
		if (volatileCCData.creditCardVV == null)
		{
			return getCreditCardVV();
		}
		return volatileCCData.creditCardVV;
	}
} // MPayment
