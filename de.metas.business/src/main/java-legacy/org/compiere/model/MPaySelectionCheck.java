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
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.process.DocAction;
import org.compiere.process.PaySelectionCreateCheck;
import org.compiere.util.AdempiereUserError;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.currency.ICurrencyDAO;

/**
 * Payment Print/Export model.
 *
 * @author Jorg Janke
 * @version $Id: MPaySelectionCheck.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public final class MPaySelectionCheck extends X_C_PaySelectionCheck
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1059335268247781843L;

	/**
	 * Get Check for Payment
	 *
	 * @param ctx context
	 * @param C_Payment_ID id
	 * @param trxName transaction
	 * @return pay selection check for payment or null
	 */
	public static MPaySelectionCheck getOfPayment(Properties ctx, int C_Payment_ID, String trxName)
	{
		MPaySelectionCheck retValue = null;
		String sql = "SELECT * FROM C_PaySelectionCheck WHERE C_Payment_ID=?";
		int count = 0;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, C_Payment_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MPaySelectionCheck psc = new MPaySelectionCheck(ctx, rs, trxName);
				if (retValue == null)
					retValue = psc;
				else if (!retValue.isProcessed() && psc.isProcessed())
					retValue = psc;
				count++;
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		if (count > 1)
			s_log.warn("More then one for C_Payment_ID=" + C_Payment_ID);
		return retValue;
	}	// getOfPayment

	/**
	 * Create Check for Payment
	 *
	 * @param ctx context
	 * @param C_Payment_ID id
	 * @param trxName transaction
	 * @return pay selection check for payment or null
	 */
	public static MPaySelectionCheck createForPayment(Properties ctx, int C_Payment_ID, String trxName)
	{
		if (C_Payment_ID == 0)
			return null;
		MPayment payment = new MPayment(ctx, C_Payment_ID, null);
		// Map Payment Rule <- Tender Type
		String PaymentRule = PAYMENTRULE_Check;
		if (payment.getTenderType().equals(X_C_Payment.TENDERTYPE_CreditCard))
			PaymentRule = PAYMENTRULE_CreditCard;
		else if (payment.getTenderType().equals(X_C_Payment.TENDERTYPE_DirectDebit))
			PaymentRule = PAYMENTRULE_DirectDebit;
		else if (payment.getTenderType().equals(X_C_Payment.TENDERTYPE_DirectDeposit))
			PaymentRule = PAYMENTRULE_DirectDeposit;
		// else if (payment.getTenderType().equals(MPayment.TENDERTYPE_Check))
		// PaymentRule = MPaySelectionCheck.PAYMENTRULE_Check;

		// Create new PaySelection
		MPaySelection ps = new MPaySelection(ctx, 0, trxName);
		ps.setAD_Org_ID(payment.getAD_Org_ID());
		ps.setC_BP_BankAccount_ID(payment.getC_BP_BankAccount_ID());
		ps.setName(Services.get(IMsgBL.class).translate(ctx, "C_Payment_ID") + ": " + payment.getDocumentNo());
		ps.setDescription(payment.getDescription());
		ps.setPayDate(payment.getDateTrx());
		ps.setTotalAmt(payment.getPayAmt());
		ps.setIsApproved(true);
		ps.save();

		// Create new PaySelection Check
		MPaySelectionCheck psc = new MPaySelectionCheck(ps, PaymentRule);
		psc.setC_BPartner_ID(payment.getC_BPartner_ID());
		psc.setC_Payment_ID(payment.getC_Payment_ID());
		psc.setIsReceipt(payment.isReceipt());
		psc.setPayAmt(payment.getPayAmt());
		psc.setDiscountAmt(payment.getDiscountAmt());
		psc.setQty(1);
		psc.setDocumentNo(payment.getDocumentNo());
		psc.setProcessed(true);
		// afalcone - [ 1871567 ] Wrong value in Payment document
		psc.setIsGeneratedDraft(!payment.isProcessed());
		//
		psc.save();

		// Create new PaySelection Line
		MPaySelectionLine psl = null;
		if (payment.getC_Invoice_ID() != 0)
		{
			psl = new MPaySelectionLine(ps, 10, PaymentRule);
			psl.setC_Invoice_ID(payment.getC_Invoice_ID());
			psl.setIsSOTrx(payment.isReceipt());
			psl.setOpenAmt(payment.getPayAmt().add(payment.getDiscountAmt()));
			psl.setPayAmt(payment.getPayAmt());
			psl.setDiscountAmt(payment.getDiscountAmt());
			psl.setDifferenceAmt(Env.ZERO);
			psl.setC_PaySelectionCheck_ID(psc.getC_PaySelectionCheck_ID());
			psl.setProcessed(true);
			psl.save();
		}
		else
		{
			// globalqss - CarlosRuiz - fix bug [ 1803054 ] Empty Remittance lines on payments
			// look for existance of C_PaymentAllocate records
			// Allocate to multiple Payments based on entry
			MPaymentAllocate[] pAllocs = MPaymentAllocate.get(payment);
			if (pAllocs.length != 0)
			{
				int numInv = 0;
				for (int i = 0; i < pAllocs.length; i++)
				{
					MPaymentAllocate pAlloc = pAllocs[i];
					if (pAlloc.getC_Invoice_ID() != 0)
					{
						MPaySelectionLine psla = null;
						psla = new MPaySelectionLine(ps, 10 * (i + 1), PaymentRule);
						psla.setC_Invoice_ID(pAlloc.getC_Invoice_ID());
						psla.setIsSOTrx(payment.isReceipt());
						psla.setOpenAmt(pAlloc.getAmount().add(pAlloc.getDiscountAmt()));
						psla.setPayAmt(pAlloc.getAmount());
						psla.setDiscountAmt(pAlloc.getDiscountAmt());
						psla.setDifferenceAmt(Env.ZERO);
						psla.setC_PaySelectionCheck_ID(psc.getC_PaySelectionCheck_ID());
						psla.setProcessed(true);
						psla.save();
						numInv++;
					}
				}
				if (numInv > 0)
				{
					psc.setQty(numInv);
					psc.save();
				}
			}
		}

		// Indicate Done
		ps.setProcessed(true);
		ps.save();
		return psc;
	}	// createForPayment

	/**************************************************************************
	 * Get Checks of Payment Selection
	 *
	 * @param C_PaySelection_ID Payment Selection
	 * @param PaymentRule Payment Rule
	 * @param startDocumentNo start document no
	 * @param trxName transaction
	 * @return array of checks
	 */
	static public MPaySelectionCheck[] get(int C_PaySelection_ID,
			String PaymentRule, int startDocumentNo, String trxName)
	{
		s_log.debug("C_PaySelection_ID=" + C_PaySelection_ID
				+ ", PaymentRule=" + PaymentRule + ", startDocumentNo=" + startDocumentNo);
		ArrayList<MPaySelectionCheck> list = new ArrayList<MPaySelectionCheck>();

		int docNo = startDocumentNo;
		String sql = "SELECT * FROM C_PaySelectionCheck "
				+ "WHERE C_PaySelection_ID=? AND PaymentRule=?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, C_PaySelection_ID);
			pstmt.setString(2, PaymentRule);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MPaySelectionCheck check = new MPaySelectionCheck(Env.getCtx(), rs, trxName);
				// Set new Check Document No - saved in confirmPrint
				check.setDocumentNo(String.valueOf(docNo++));
				list.add(check);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			s_log.error(sql, e);
		}

		// convert to Array
		MPaySelectionCheck[] retValue = new MPaySelectionCheck[list.size()];
		list.toArray(retValue);
		return retValue;
	}   // get

	/**************************************************************************
	 * Export to File
	 * 
	 * @param checks array of checks
	 * @param file file to export checks
	 * @return number of lines
	 */
	public static int exportToFile(MPaySelectionCheck[] checks, File file)
	{
		if (checks == null || checks.length == 0)
			return 0;
		// Must be a file
		if (file.isDirectory())
		{
			s_log.warn("File is directory - " + file.getAbsolutePath());
			return 0;
		}
		// delete if exists
		try
		{
			if (file.exists())
				file.delete();
		}
		catch (Exception e)
		{
			s_log.warn("Could not delete - " + file.getAbsolutePath(), e);
		}

		char x = '"';      // ease
		int noLines = 0;
		StringBuffer line = null;
		try
		{
			FileWriter fw = new FileWriter(file);

			// write header
			line = new StringBuffer();
			line.append(x).append("Value").append(x).append(",")
					.append(x).append("Name").append(x).append(",")
					.append(x).append("Contact").append(x).append(",")
					.append(x).append("Addr1").append(x).append(",")
					.append(x).append("Addr2").append(x).append(",")
					.append(x).append("City").append(x).append(",")
					.append(x).append("State").append(x).append(",")
					.append(x).append("ZIP").append(x).append(",")
					.append(x).append("Country").append(x).append(",")
					.append(x).append("ReferenceNo").append(x).append(",")
					.append(x).append("DocumentNo").append(x).append(",")
					.append(x).append("PayDate").append(x).append(",")
					.append(x).append("Currency").append(x).append(",")
					.append(x).append("PayAmount").append(x).append(",")
					.append(x).append("Comment").append(x)
					.append(Env.NL);
			fw.write(line.toString());
			noLines++;

			// write lines
			for (int i = 0; i < checks.length; i++)
			{
				MPaySelectionCheck mpp = checks[i];
				if (mpp == null)
					continue;
				// BPartner Info
				String bp[] = getBPartnerInfo(mpp.getC_BPartner_ID());

				// Comment - list of invoice document no
				StringBuffer comment = new StringBuffer();
				MPaySelectionLine[] psls = mpp.getPaySelectionLines(false);
				for (int l = 0; l < psls.length; l++)
				{
					if (l > 0)
						comment.append(", ");
					comment.append(psls[l].getInvoice().getDocumentNo());
				}
				line = new StringBuffer();
				line.append(x).append(bp[BP_VALUE]).append(x).append(",")   // Value
						.append(x).append(bp[BP_NAME]).append(x).append(",")    // Name
						.append(x).append(bp[BP_CONTACT]).append(x).append(",") // Contact
						.append(x).append(bp[BP_ADDR1]).append(x).append(",")   // Addr1
						.append(x).append(bp[BP_ADDR2]).append(x).append(",")   // Addr2
						.append(x).append(bp[BP_CITY]).append(x).append(",")    // City
						.append(x).append(bp[BP_REGION]).append(x).append(",")  // State
						.append(x).append(bp[BP_POSTAL]).append(x).append(",")  // ZIP
						.append(x).append(bp[BP_COUNTRY]).append(x).append(",") // Country
						.append(x).append(bp[BP_REFNO]).append(x).append(",")   // ReferenceNo
						// Payment Info
						.append(x).append(mpp.getDocumentNo()).append(x).append(",")    // DocumentNo
						.append(mpp.getParent().getPayDate()).append(",")               // PayDate
						.append(x).append(Services.get(ICurrencyDAO.class).getISO_Code(Env.getCtx(), mpp.getParent().getC_Currency_ID())).append(x).append(",")    // Currency
						.append(mpp.getPayAmt()).append(",")                // PayAmount
						.append(x).append(comment.toString()).append(x)     // Comment
						.append(Env.NL);
				fw.write(line.toString());
				noLines++;
			}   // write line

			fw.flush();
			fw.close();
		}
		catch (Exception e)
		{
			s_log.error("", e);
		}

		return noLines;
	}   // exportToFile

	/**
	 * Get Customer/Vendor Info.
	 * Based on BP_ static variables
	 * 
	 * @param C_BPartner_ID BPartner
	 * @return info array
	 */
	private static String[] getBPartnerInfo(int C_BPartner_ID)
	{
		String[] bp = new String[10];

		String sql = DB.convertSqlToNative("SELECT bp.Value, bp.Name, c.Name AS Contact, "
				+ "a.Address1, a.Address2, a.City, r.Name AS Region, a.Postal, "
				+ "cc.Name AS Country, bp.ReferenceNo "
				+ "FROM C_BPartner bp, AD_User c, C_BPartner_Location l, C_Location a, C_Region r, C_Country cc "
				+ "WHERE bp.C_BPartner_ID=?"        // #1
				+ " AND bp.C_BPartner_ID=c.C_BPartner_ID(+)"
				+ " AND bp.C_BPartner_ID=l.C_BPartner_ID"
				+ " AND l.C_Location_ID=a.C_Location_ID"
				+ " AND a.C_Region_ID=r.C_Region_ID(+)"
				+ " AND a.C_Country_ID=cc.C_Country_ID "
				+ "ORDER BY l.IsBillTo DESC");
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_BPartner_ID);
			ResultSet rs = pstmt.executeQuery();
			//
			if (rs.next())
			{
				bp[BP_VALUE] = rs.getString(1);
				if (bp[BP_VALUE] == null)
					bp[BP_VALUE] = "";
				bp[BP_NAME] = rs.getString(2);
				if (bp[BP_NAME] == null)
					bp[BP_NAME] = "";
				bp[BP_CONTACT] = rs.getString(3);
				if (bp[BP_CONTACT] == null)
					bp[BP_CONTACT] = "";
				bp[BP_ADDR1] = rs.getString(4);
				if (bp[BP_ADDR1] == null)
					bp[BP_ADDR1] = "";
				bp[BP_ADDR2] = rs.getString(5);
				if (bp[BP_ADDR2] == null)
					bp[BP_ADDR2] = "";
				bp[BP_CITY] = rs.getString(6);
				if (bp[BP_CITY] == null)
					bp[BP_CITY] = "";
				bp[BP_REGION] = rs.getString(7);
				if (bp[BP_REGION] == null)
					bp[BP_REGION] = "";
				bp[BP_POSTAL] = rs.getString(8);
				if (bp[BP_POSTAL] == null)
					bp[BP_POSTAL] = "";
				bp[BP_COUNTRY] = rs.getString(9);
				if (bp[BP_COUNTRY] == null)
					bp[BP_COUNTRY] = "";
				bp[BP_REFNO] = rs.getString(10);
				if (bp[BP_REFNO] == null)
					bp[BP_REFNO] = "";
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			s_log.error(sql, e);
		}
		return bp;
	}   // getBPartnerInfo

	/**************************************************************************
	 * Confirm Print.
	 * Create Payments the first time
	 * 
	 * @param checks checks
	 * @param batch batch
	 * @return last Document number or 0 if nothing printed
	 */
	public static int confirmPrint(MPaySelectionCheck[] checks, MPaymentBatch batch)
	{
		int lastDocumentNo = 0;
		for (int i = 0; i < checks.length; i++)
		{
			MPaySelectionCheck check = checks[i];
			MPayment payment = new MPayment(check.getCtx(), check.getC_Payment_ID(), null);
			// Existing Payment
			if (check.getC_Payment_ID() != 0)
			{
				// Update check number
				if (check.getPaymentRule().equals(PAYMENTRULE_Check))
				{
					payment.setCheckNo(check.getDocumentNo());
					if (!payment.save())
						s_log.error("Payment not saved: " + payment);
				}
			}
			else
			// New Payment
			{
				payment = new MPayment(check.getCtx(), 0, null);
				payment.setAD_Org_ID(check.getAD_Org_ID());
				//
				if (check.getPaymentRule().equals(PAYMENTRULE_Check))
					payment.setBankCheck(check.getParent().getC_BP_BankAccount_ID(), false, check.getDocumentNo());
				else if (check.getPaymentRule().equals(PAYMENTRULE_CreditCard))
					payment.setTenderType(X_C_Payment.TENDERTYPE_CreditCard);
				else if (check.getPaymentRule().equals(PAYMENTRULE_DirectDeposit)
						|| check.getPaymentRule().equals(PAYMENTRULE_DirectDebit))
					payment.setBankACH(check);
				else
				{
					s_log.error("Unsupported Payment Rule=" + check.getPaymentRule());
					continue;
				}
				payment.setTrxType(X_C_Payment.TRXTYPE_CreditPayment);
				payment.setAmount(check.getParent().getC_Currency_ID(), check.getPayAmt());
				payment.setDiscountAmt(check.getDiscountAmt());
				payment.setDateTrx(check.getParent().getPayDate());
				payment.setDateAcct(payment.getDateTrx()); // globalqss [ 2030685 ]
				payment.setC_BPartner_ID(check.getC_BPartner_ID());
				// Link to Batch
				if (batch != null)
				{
					if (batch.getC_PaymentBatch_ID() == 0)
						batch.save();	// new
					payment.setC_PaymentBatch_ID(batch.getC_PaymentBatch_ID());
				}
				// Link to Invoice
				MPaySelectionLine[] psls = check.getPaySelectionLines(false);
				s_log.debug("confirmPrint - " + check + " (#SelectionLines=" + psls.length + ")");
				if (check.getQty() == 1 && psls != null && psls.length == 1)
				{
					MPaySelectionLine psl = psls[0];
					s_log.debug("Map to Invoice " + psl);
					//
					payment.setC_Invoice_ID(psl.getC_Invoice_ID());
					payment.setDiscountAmt(psl.getDiscountAmt());
					payment.setWriteOffAmt(psl.getDifferenceAmt());
					BigDecimal overUnder = psl.getOpenAmt().subtract(psl.getPayAmt())
							.subtract(psl.getDiscountAmt()).subtract(psl.getDifferenceAmt());
					payment.setOverUnderAmt(overUnder);
				}
				else
					payment.setDiscountAmt(Env.ZERO);
				payment.setWriteOffAmt(Env.ZERO);
				if (!payment.save())
					s_log.error("Payment not saved: " + payment);
				//
				int C_Payment_ID = payment.get_ID();
				if (C_Payment_ID < 1)
					s_log.error("Payment not created=" + check);
				else
				{
					check.setC_Payment_ID(C_Payment_ID);
					check.save();	// Payment process needs it
					// Should start WF
					payment.processIt(DocAction.ACTION_Complete);
					if (!payment.save())
						s_log.error("Payment not saved: " + payment);
				}
			}	// new Payment

			// Get Check Document No
			try
			{
				int no = Integer.parseInt(check.getDocumentNo());
				if (lastDocumentNo < no)
					lastDocumentNo = no;
			}
			catch (NumberFormatException ex)
			{
				s_log.error("DocumentNo=" + check.getDocumentNo(), ex);
			}
			check.setIsPrinted(true);
			check.setProcessed(true);
			if (!check.save())
				s_log.error("Check not saved: " + check);
		}	// all checks

		s_log.debug("Last Document No = " + lastDocumentNo);
		return lastDocumentNo;
	}	// confirmPrint

	/** Logger */
	static private Logger s_log = LogManager.getLogger(MPaySelectionCheck.class);

	/** BPartner Info Index for Value */
	private static final int BP_VALUE = 0;
	/** BPartner Info Index for Name */
	private static final int BP_NAME = 1;
	/** BPartner Info Index for Contact Name */
	private static final int BP_CONTACT = 2;
	/** BPartner Info Index for Address 1 */
	private static final int BP_ADDR1 = 3;
	/** BPartner Info Index for Address 2 */
	private static final int BP_ADDR2 = 4;
	/** BPartner Info Index for City */
	private static final int BP_CITY = 5;
	/** BPartner Info Index for Region */
	private static final int BP_REGION = 6;
	/** BPartner Info Index for Postal Code */
	private static final int BP_POSTAL = 7;
	/** BPartner Info Index for Country */
	private static final int BP_COUNTRY = 8;
	/** BPartner Info Index for Reference No */
	private static final int BP_REFNO = 9;

	/**************************************************************************
	 * Constructor
	 * 
	 * @param ctx context
	 * @param C_PaySelectionCheck_ID C_PaySelectionCheck_ID
	 * @param trxName transaction
	 */
	public MPaySelectionCheck(Properties ctx, int C_PaySelectionCheck_ID, String trxName)
	{
		super(ctx, C_PaySelectionCheck_ID, trxName);
		if (C_PaySelectionCheck_ID == 0)
		{
			// setC_PaySelection_ID (0);
			// setC_BPartner_ID (0);
			// setPaymentRule (null);
			setPayAmt(Env.ZERO);
			setDiscountAmt(Env.ZERO);
			setIsPrinted(false);
			setIsReceipt(false);
			setQty(0);
		}
	}   // MPaySelectionCheck

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MPaySelectionCheck(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}   // MPaySelectionCheck

	/**
	 * Create from Line
	 *
	 * @param line payment selection
	 * @param PaymentRule payment rule
	 * @throws AdempiereUserError
	 */
	public MPaySelectionCheck(MPaySelectionLine line, String PaymentRule) throws AdempiereUserError
	{

		this(line.getCtx(), 0, line.get_TrxName());
		setClientOrg(line);
		setC_PaySelection_ID(line.getC_PaySelection_ID());
		int C_BPartner_ID = line.getInvoice().getC_BPartner_ID();
		setC_BPartner_ID(C_BPartner_ID);

		final IBPBankAccountDAO dao = Services.get(IBPBankAccountDAO.class);

		final Properties ctx = getCtx();

		// 09500: also check for the currency
		// (this is safe because all the columns are mandatory)
		final int currencyID = line.getC_PaySelection().getC_BP_BankAccount().getC_Currency_ID();
		//
		if (X_C_Order.PAYMENTRULE_DirectDebit.equals(PaymentRule))
		{
			final List<I_C_BP_BankAccount> bas = dao.retrieveBankAccountsForPartnerAndCurrency(ctx, C_BPartner_ID, currencyID);
			for (I_C_BP_BankAccount account : bas)
			{
				// conversion needed for calling the isDirectDebit. To be refactored when we get rid of MBPBankAccount
				final MBPBankAccount maccount = new MBPBankAccount(ctx, account.getC_BP_BankAccount_ID(), ITrx.TRXNAME_None);
				if (maccount.isDirectDebit())
				{
					setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
					break;
				}
			}
		}
		else if (X_C_Order.PAYMENTRULE_DirectDeposit.equals(PaymentRule))
		{
			final List<I_C_BP_BankAccount> bas = dao.retrieveBankAccountsForPartnerAndCurrency(ctx, C_BPartner_ID, currencyID);
			for (I_C_BP_BankAccount account : bas)
			{
				// conversion needed for calling the isDirectDebit. To be refactored when we get rid of MBPBankAccount
				final MBPBankAccount maccount = new MBPBankAccount(ctx, account.getC_BP_BankAccount_ID(), ITrx.TRXNAME_None);

				if (maccount.isDirectDeposit())
				{
					setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
					break;
				}
			}
		}
		else
		{
			final I_C_PaySelectionLine paySelectionLine = InterfaceWrapperHelper.create(getCtx(), line.getC_PaySelectionLine_ID(), I_C_PaySelectionLine.class, get_TrxName());
			final int bpBankAccountID = paySelectionLine.getC_BP_BankAccount_ID();

			if (bpBankAccountID <= 0)
			{
				String msg = Services.get(IMsgBL.class).getMsg(getCtx(), PaySelectionCreateCheck.ERR_PaySelection_NoBPBankAccount);
				throw new AdempiereUserError(msg);
			}

			setC_BP_BankAccount_ID(bpBankAccountID);
		}
		setPaymentRule(PaymentRule);
		//
		setIsReceipt(line.isSOTrx());
		setPayAmt(line.getPayAmt());
		setDiscountAmt(line.getDiscountAmt());
		setQty(1);

		if (line != null && line.getC_Invoice_ID() > 0)
		{
			InterfaceWrapperHelper.setDynAttribute(this, I_C_DocType.COLUMNNAME_DocBaseType, line.getC_Invoice().getC_DocType().getDocBaseType());
		}

	}	// MPaySelectionCheck

	/**
	 * Create from Pay Selection
	 *
	 * @param ps payment selection
	 * @param PaymentRule payment rule
	 */
	public MPaySelectionCheck(MPaySelection ps, String PaymentRule)
	{
		this(ps.getCtx(), 0, ps.get_TrxName());
		setClientOrg(ps);
		setC_PaySelection_ID(ps.getC_PaySelection_ID());
		setPaymentRule(PaymentRule);
	}	// MPaySelectionCheck

	/** Parent */
	private MPaySelection m_parent = null;
	/** Payment Selection lines of this check */
	private MPaySelectionLine[] m_lines = null;

	/**
	 * Add Payment Selection Line
	 *
	 * @param line line
	 */
	public void addLine(MPaySelectionLine line)
	{
		if (getC_BPartner_ID() != line.getInvoice().getC_BPartner_ID())
			throw new IllegalArgumentException("Line for fifferent BPartner");
		//
		if (isReceipt() == line.isSOTrx())
		{
			setPayAmt(getPayAmt().add(line.getPayAmt()));
			setDiscountAmt(getDiscountAmt().add(line.getDiscountAmt()));
		}
		else
		{
			setPayAmt(getPayAmt().subtract(line.getPayAmt()));
			setDiscountAmt(getDiscountAmt().subtract(line.getDiscountAmt()));
		}
		setQty(getQty() + 1);
	}	// addLine

	/**
	 * Get Parent
	 *
	 * @return parent
	 */
	public MPaySelection getParent()
	{
		if (m_parent == null)
			m_parent = new MPaySelection(getCtx(), getC_PaySelection_ID(), get_TrxName());
		return m_parent;
	}	// getParent

	/**
	 * Is this a valid Prepared Payment
	 *
	 * @return true if valid
	 */
	public boolean isValid()
	{
		if (getC_BP_BankAccount_ID() > 0)
		{
			return true;
		}

		// 08340
		// we are enforcing C_BP_BankAccount
		/*
		 * if (isDirect())
		 * {
		 * return false;
		 * }
		 */
		return false;
	}	// isValid

	/**
	 * Is this a direct Debit or Deposit
	 *
	 * @return true if direct
	 */
	public boolean isDirect()
	{
		return (X_C_Order.PAYMENTRULE_DirectDeposit.equals(getPaymentRule())
		|| X_C_Order.PAYMENTRULE_DirectDebit.equals(getPaymentRule()));
	}	// isDirect

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MPaymentCheck[");
		sb.append(get_ID()).append("-").append(getDocumentNo())
				.append("-").append(getPayAmt())
				.append(",PaymetRule=").append(getPaymentRule())
				.append(",Qty=").append(getQty())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Get Payment Selection Lines of this check
	 *
	 * @param requery requery
	 * @return array of peyment selection lines
	 */
	public MPaySelectionLine[] getPaySelectionLines(boolean requery)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		ArrayList<MPaySelectionLine> list = new ArrayList<MPaySelectionLine>();
		String sql = "SELECT * FROM C_PaySelectionLine WHERE C_PaySelectionCheck_ID=? ORDER BY Line";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_PaySelectionCheck_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MPaySelectionLine(getCtx(), rs, get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//
		m_lines = new MPaySelectionLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}	// getPaySelectionLines

	/**
	 * Delete Payment Selection when generated as Draft (Print Preview)
	 *
	 * @param ctx context
	 * @param C_Payment_ID id
	 * @param trxName transaction
	 * @return
	 */
	public static boolean deleteGeneratedDraft(Properties ctx, int C_Payment_ID, String trxName)
	{

		MPaySelectionCheck mpsc = MPaySelectionCheck.getOfPayment(ctx, C_Payment_ID, trxName);

		if (mpsc != null && mpsc.isGeneratedDraft())
		{
			MPaySelection mps = new MPaySelection(ctx, mpsc.getC_PaySelection_ID(), trxName);
			MPaySelectionLine[] mpsl = mps.getLines(true);

			// Delete Pay Selection lines
			for (int i = 0; i < mpsl.length; i++)
			{
				if (!mpsl[i].delete(true, trxName))
					return false;
			}
			// Delete Pay Selection Check
			if (!mpsc.delete(true, trxName))
				return false;

			// Delete Pay Selection
			if (!mps.delete(true, trxName))
				return false;
		}
		return true;
	}

}   // MPaySelectionCheck
