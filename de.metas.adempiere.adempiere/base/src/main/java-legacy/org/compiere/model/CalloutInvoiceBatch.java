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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.adempiere.bpartner.service.BPartnerCreditLimitRepository;
import org.adempiere.util.time.SystemTime;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.logging.MetasfreshLastError;


/**
 *	Callouts for Invoice Batch
 *
 *  @author Jorg Janke
 *  @version $Id: CalloutInvoiceBatch.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class CalloutInvoiceBatch extends CalloutEngine
{
	/**
	 *	Invoice Batch Line - DateInvoiced.
	 * 		- updates DateAcct
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String date (final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		if (value == null)
		{
			return "";
		}
		mTab.setValue ("DateAcct", value);
		//
		setDocumentNo(ctx, WindowNo, mTab);
		return "";
	}	//	date



	/**
	 *	Invoice Batch Line - BPartner.
	 *		- C_BPartner_Location_ID
	 *		- AD_User_ID
	 *		- PaymentRule
	 *		- C_PaymentTerm_ID
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String bPartner (final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		final Integer C_BPartner_ID = (Integer)value;
		if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0)
		{
			return "";
		}

		final String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
				+ " COALESCE(p.M_PriceList_ID,g.M_PriceList_ID) AS M_PriceList_ID, p.PaymentRule,p.POReference,"
				+ " p.SO_Description,p.IsDiscountPrinted,"
				+ " stats."	+ I_C_BPartner_Stats.COLUMNNAME_SO_CreditUsed + ", "
				+ " l.C_BPartner_Location_ID,c.AD_User_ID,"
				+ " COALESCE(p.PO_PriceList_ID,g.PO_PriceList_ID) AS PO_PriceList_ID, p.PaymentRulePO,p.PO_PaymentTerm_ID "
				+ "FROM C_BPartner p"
				+ " INNER JOIN "
				+ I_C_BPartner_Stats.Table_Name
				+ " stats ON (p."
				+ I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ " = stats."
				+ I_C_BPartner_Stats.COLUMNNAME_C_BPartner_ID
				+ ")"
				+ " INNER JOIN C_BP_Group g ON (p.C_BP_Group_ID=g.C_BP_Group_ID)"
				+ " LEFT OUTER JOIN C_BPartner_Location l ON (p.C_BPartner_ID=l.C_BPartner_ID AND l.IsBillTo='Y' AND l.IsActive='Y')"
				+ " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) "
				+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'";		// #1

		final boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_BPartner_ID.intValue());
			rs = pstmt.executeQuery();
			//
			if (rs.next())
			{
				//	PaymentRule
				String s = rs.getString(IsSOTrx ? "PaymentRule" : "PaymentRulePO");
				if (s != null && s.length() != 0)
				{
					if (Env.getContext(ctx, WindowNo, "DocBaseType").endsWith("C"))
					{
						s = "P";
					}
					else if (IsSOTrx && (s.equals("S") || s.equals("U")))
					 {
						s = "P";											//  Payment Term
			//		mTab.setValue("PaymentRule", s);
					}
				}
				//  Payment Term
				final Integer ii = new Integer(rs.getInt(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID"));
				if (!rs.wasNull())
				{
					mTab.setValue("C_PaymentTerm_ID", ii);
				}

				//	Location
				int locID = rs.getInt("C_BPartner_Location_ID");
				//	overwritten by InfoBP selection - works only if InfoWindow
				//	was used otherwise creates error (uses last value, may belong to differnt BP)
				if (C_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					final String loc = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_Location_ID");
					if (loc.length() > 0)
					{
						locID = Integer.parseInt(loc);
					}
				}
				if (locID == 0)
				{
					mTab.setValue("C_BPartner_Location_ID", null);
				}
				else
				{
					mTab.setValue("C_BPartner_Location_ID", new Integer(locID));
				}

				//	Contact - overwritten by InfoBP selection
				int contID = rs.getInt("AD_User_ID");
				if (C_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					final String cont = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "AD_User_ID");
					if (cont.length() > 0)
					{
						contID = Integer.parseInt(cont);
					}
				}
				if (contID == 0)
				{
					mTab.setValue("AD_User_ID", null);
				}
				else
				{
					mTab.setValue("AD_User_ID", new Integer(contID));
				}

				//	CreditAvailable
				if (IsSOTrx)
				{

					final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
					final Date dateInvoiced = mTab.get_ValueAsDate("DateInvoiced", SystemTime.asDate());
					final BigDecimal CreditLimit = creditLimitRepo.getCreditLimitByBPartnerId(C_BPartner_ID.intValue(), TimeUtil.asTimestamp(dateInvoiced));
					if (CreditLimit.signum() > 0)
					{
						final double creditUsed = rs.getDouble("SO_CreditUsed");
						final BigDecimal CreditAvailable = CreditLimit.subtract(BigDecimal.valueOf(creditUsed));
						if (!rs.wasNull() && CreditAvailable.signum() < 0)
						{
							mTab.fireDataStatusEEvent("CreditLimitOver",
									DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable),
									false);
						}
					}
				}
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		//
		setDocumentNo(ctx, WindowNo, mTab);
		return tax (ctx, WindowNo, mTab, mField, value);
	}	//	bPartner

	/**
	 *	Document Type.
	 *		- called from DocType
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String docType (final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		setDocumentNo(ctx, WindowNo, mTab);
		return "";
	}	//	docType

	/**
	 *	Set Document No (increase existing)
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 */
	private void setDocumentNo (final Properties ctx, final int WindowNo, final GridTab mTab)
	{
		//	Get last line
		final int C_InvoiceBatch_ID = Env.getContextAsInt(ctx, WindowNo, "C_InvoiceBatch_ID");
		final String sql = "SELECT COALESCE(MAX(C_InvoiceBatchLine_ID),0) FROM C_InvoiceBatchLine WHERE C_InvoiceBatch_ID=?";
		final int C_InvoiceBatchLine_ID = DB.getSQLValue(null, sql, C_InvoiceBatch_ID);
		if (C_InvoiceBatchLine_ID == 0)
		{
			return;
		}
		final MInvoiceBatchLine last = new MInvoiceBatchLine(Env.getCtx(), C_InvoiceBatchLine_ID, null);

		//	Need to Increase when different DocType or BP
		final int C_DocType_ID = Env.getContextAsInt(ctx, WindowNo, "C_DocType_ID");
		final int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
		if (C_DocType_ID == last.getC_DocType_ID()
			&& C_BPartner_ID == last.getC_BPartner_ID())
		{
			return;
		}

		//	New Number
		final String oldDocNo = last.getDocumentNo();
		if (oldDocNo == null)
		{
			return;
		}
		int docNo = 0;
		try
		{
			docNo = Integer.parseInt(oldDocNo);
		}
		catch (final Exception e)
		{
		}
		if (docNo == 0)
		{
			return;
		}
		final String newDocNo = String.valueOf(docNo+1);
		mTab.setValue("DocumentNo", newDocNo);
	}	//	setDocumentNo

	/**
	 *	Invoice Batch Line - Charge.
	 * 		- updates PriceEntered from Charge
	 * 	Calles tax
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String charge (final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		final Integer C_Charge_ID = (Integer)value;
		if (C_Charge_ID == null || C_Charge_ID.intValue() == 0)
		{
			return "";
		}

		final String sql = "SELECT ChargeAmt FROM C_Charge WHERE C_Charge_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_Charge_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				mTab.setValue ("PriceEntered", rs.getBigDecimal (1));
			}
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		return tax (ctx, WindowNo, mTab, mField, value);
	}	//	charge

	/**
	 *	Invoice Line - Tax.
	 *		- basis: Charge, BPartner Location
	 *		- sets C_Tax_ID
	 *  Calles Amount
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String tax (final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		final String column = mField.getColumnName();
		if (value == null)
		{
			return "";
		}

		int C_Charge_ID = 0;
		if (column.equals("C_Charge_ID"))
		{
			C_Charge_ID = ((Integer)value).intValue();
		}
		else
		{
			C_Charge_ID = Env.getContextAsInt(ctx, WindowNo, "C_Charge_ID");
		}
		log.debug("C_Charge_ID=" + C_Charge_ID);
		if (C_Charge_ID == 0)
		 {
			return amt (ctx, WindowNo, mTab, mField, value);	//
		}

		//	Check Partner Location
		final int C_BPartner_Location_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_Location_ID");
		if (C_BPartner_Location_ID == 0)
		 {
			return amt (ctx, WindowNo, mTab, mField, value);	//
		}
		log.debug("BP_Location=" + C_BPartner_Location_ID);

		//	Dates
		final Timestamp billDate = Env.getContextAsDate(ctx, WindowNo, "DateInvoiced");
		log.debug("Bill Date=" + billDate);
		final Timestamp shipDate = billDate;
		log.debug("Ship Date=" + shipDate);

		final int AD_Org_ID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");
		log.debug("Org=" + AD_Org_ID);

		final int M_Warehouse_ID = Env.getContextAsInt(ctx, "#M_Warehouse_ID");
		log.debug("Warehouse=" + M_Warehouse_ID);

		//
		final int C_Tax_ID = Tax.get(ctx, 0, C_Charge_ID, billDate, shipDate,
			AD_Org_ID, M_Warehouse_ID, C_BPartner_Location_ID, C_BPartner_Location_ID,
			Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y"));
		log.info("Tax ID=" + C_Tax_ID);
		//
		if (C_Tax_ID == 0)
		{
			mTab.fireDataStatusEEvent(MetasfreshLastError.retrieveError());
		}
		else
		{
			mTab.setValue("C_Tax_ID", new Integer(C_Tax_ID));
		}
		//
		return amt (ctx, WindowNo, mTab, mField, value);
	}	//	tax


	/**
	 *	Invoice - Amount.
	 *		- called from QtyEntered, PriceEntered
	 *		- calculates LineNetAmt
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String amt (final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive() || value == null)
		{
			return "";
		}

		final int StdPrecision = 2;		//	temporary

		//	get values
		BigDecimal QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
		BigDecimal PriceEntered = (BigDecimal)mTab.getValue("PriceEntered");
		log.debug("QtyEntered=" + QtyEntered + ", PriceEntered=" + PriceEntered);
		if (QtyEntered == null)
		{
			QtyEntered = Env.ZERO;
		}
		if (PriceEntered == null)
		{
			PriceEntered = Env.ZERO;
		}

		//	Line Net Amt
		BigDecimal LineNetAmt = QtyEntered.multiply(PriceEntered);
		if (LineNetAmt.scale() > StdPrecision)
		{
			LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		}

		//	Calculate Tax Amount
		final boolean IsSOTrx = "Y".equals(Env.getContext(Env.getCtx(), WindowNo, "IsSOTrx"));
		final boolean IsTaxIncluded = "Y".equals(Env.getContext(Env.getCtx(), WindowNo, "IsTaxIncluded"));

		BigDecimal TaxAmt = null;
		if (mField.getColumnName().equals("TaxAmt"))
		{
			TaxAmt = (BigDecimal)mTab.getValue("TaxAmt");
		}
		else
		{
			final Integer taxID = (Integer)mTab.getValue("C_Tax_ID");
			if (taxID != null)
			{
				final int C_Tax_ID = taxID.intValue();
				final MTax tax = new MTax (ctx, C_Tax_ID, null);
				TaxAmt = tax.calculateTax(LineNetAmt, IsTaxIncluded, StdPrecision);
				mTab.setValue("TaxAmt", TaxAmt);
			}
		}

		//
		if (IsTaxIncluded)
		{
			mTab.setValue("LineTotalAmt", LineNetAmt);
			mTab.setValue("LineNetAmt", LineNetAmt.subtract(TaxAmt));
		}
		else
		{
			mTab.setValue("LineNetAmt", LineNetAmt);
			mTab.setValue("LineTotalAmt", LineNetAmt.add(TaxAmt));
		}
		return "";
	}	//	amt



}	//	CalloutInvoiceBatch
