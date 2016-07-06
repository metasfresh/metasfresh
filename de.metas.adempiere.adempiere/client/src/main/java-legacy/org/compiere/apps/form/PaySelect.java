/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps.form;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.MPaySelection;
import org.compiere.model.MPaySelectionLine;
import org.compiere.model.X_C_Order;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.ValueNamePair;

/**
 * 
 * @deprecated disabled because found already broken (mainly because of C_BP_BankAccount.CurrentBalance)
 */
@Deprecated
public class PaySelect
{
	/** @todo withholding */

	/**	Window No			*/
	public int         	m_WindowNo = 0;

	/** Format                  */
	public DecimalFormat   m_format = DisplayType.getNumberFormat(DisplayType.Amount);
	/** Bank Balance            */
	private BigDecimal      m_bankBalance = new BigDecimal(0.0);
	/** SQL for Query           */
	private String          m_sql;
	/** Number of selected rows */
	public int             m_noSelected = 0;
	/** Client ID               */
	private int             m_AD_Client_ID = 0;
	/**/
	public boolean         m_isLocked = false;
	/** Payment Selection		*/
	public MPaySelection	m_ps = null;
	/**	Logger			*/
	public static Logger log = LogManager.getLogger(PaySelect.class);
	
	public PaySelect()
	{
		super();
		throw new UnsupportedOperationException(); // disabled because already broken
	}

	public ArrayList<BankInfo> getBankAccountData()
	{
		ArrayList<BankInfo> data = new ArrayList<BankInfo>();
		//
		m_AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		//  Bank Account Info
		final String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT ba.C_BP_BankAccount_ID,"                       //  1
			+ "b.Name || ' ' || ba.AccountNo AS Name,"          //  2
			+ "ba.C_Currency_ID, c.ISO_Code,"                   //  3..4
			+ "ba.CurrentBalance "                              //  5
			+ "FROM C_Bank b, C_BP_BankAccount ba, C_Currency c "
			+ "WHERE b.C_Bank_ID=ba.C_Bank_ID"
			+ " AND ba.C_Currency_ID=c.C_Currency_ID AND ba.IsActive='Y' "
			+ "ORDER BY 2",
			"b", IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RW);
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				final boolean transfers = false;
				final BankInfo bi = new BankInfo(rs.getInt(1), // C_BP_BankAccount_ID
						rs.getInt(3),
						rs.getString(2), rs.getString(4),
						rs.getBigDecimal(5), transfers);
				data.add(bi);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		
		return data;
	}
	
	public ArrayList<KeyNamePair> getBPartnerData()
	{
		ArrayList<KeyNamePair> data = new ArrayList<KeyNamePair>();
		
		//  Optional BusinessPartner with unpaid AP Invoices
		KeyNamePair pp = new KeyNamePair(0, "");
		data.add(pp);
		
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT bp.C_BPartner_ID, bp.Name FROM C_BPartner bp", "bp",
			IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO)
			+ " AND EXISTS (SELECT * FROM C_Invoice i WHERE bp.C_BPartner_ID=i.C_BPartner_ID"
			//	X_C_Order.PAYMENTRULE_DirectDebit
			  + " AND (i.IsSOTrx='N' OR (i.IsSOTrx='Y' AND i.PaymentRule='D'))"
			  + " AND i.IsPaid<>'Y') "
			+ "ORDER BY 2";

		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				data.add(pp);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		
		return data;
	}
	
	public ArrayList<KeyNamePair> getDocTypeData()
	{
		ArrayList<KeyNamePair> data = new ArrayList<KeyNamePair>();
		String sql = null;
		/**Document type**/
		try
		{
			sql = Env.getUserRolePermissions().addAccessSQL(
				"SELECT doc.c_doctype_id,doc.name FROM c_doctype doc WHERE doc.ad_client_id = ? AND doc.docbasetype in ('API','APC') ORDER BY 2", "doc",
				IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);

			KeyNamePair dt = new KeyNamePair(0, "");
			data.add(dt);
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_AD_Client_ID);		//	Client
			ResultSet rs = pstmt.executeQuery();

			while (rs.next())
			{
				dt = new KeyNamePair(rs.getInt(1), rs.getString(2));
				data.add(dt);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		
		return data;
	}
	
	public void prepareTable(IMiniTable miniTable)
	{
		Properties ctx = Env.getCtx();
		/**  prepare MiniTable
		 *
		SELECT i.C_Invoice_ID, i.DateInvoiced+p.NetDays AS DateDue,
		bp.Name, i.DocumentNo, c.ISO_Code, i.GrandTotal,
		paymentTermDiscount(i.GrandTotal, i.C_PaymentTerm_ID, i.DateInvoiced, now()) AS Discount,
		now()-paymentTermDueDays(i.C_PaymentTerm_ID,i.DateInvoiced) AS DiscountDate,
		i.GrandTotal-paymentTermDiscount(i.GrandTotal,i.C_PaymentTerm_ID,i.DateInvoiced,now()) AS DueAmount,
		currencyConvert(i.GrandTotal-paymentTermDiscount(i.GrandTotal,i.C_PaymentTerm_ID,i.DateInvoiced,now(),null),
			i.C_Currency_ID,xx100,now()) AS PayAmt
		FROM C_Invoice i, C_BPartner bp, C_Currency c, C_PaymentTerm p
		WHERE i.IsSOTrx='N'
		AND i.C_BPartner_ID=bp.C_BPartner_ID
		AND i.C_Currency_ID=c.C_Currency_ID
		AND i.C_PaymentTerm_ID=p.C_PaymentTerm_ID
		AND i.DocStatus IN ('CO','CL')
		ORDER BY 2,3
		 */

		m_sql = miniTable.prepareTable(new ColumnInfo[] {
			//  0..4
			new ColumnInfo(" ", "i.C_Invoice_ID", IDColumn.class, false, false, null),
			new ColumnInfo(Msg.translate(ctx, "DueDate"), "paymentTermDueDate(i.C_PaymentTerm_ID, i.DateInvoiced) AS DateDue", Timestamp.class, true, true, null),
			new ColumnInfo(Msg.translate(ctx, "C_BPartner_ID"), "bp.Name", KeyNamePair.class, true, false, "i.C_BPartner_ID"),
			new ColumnInfo(Msg.translate(ctx, "DocumentNo"), "i.DocumentNo", String.class),
			new ColumnInfo(Msg.translate(ctx, "C_Currency_ID"), "c.ISO_Code", KeyNamePair.class, true, false, "i.C_Currency_ID"),
			// 5..9
			new ColumnInfo(Msg.translate(ctx, "GrandTotal"), "i.GrandTotal", BigDecimal.class),
			new ColumnInfo(Msg.translate(ctx, "DiscountAmt"), "paymentTermDiscount(i.GrandTotal,i.C_Currency_ID,i.C_PaymentTerm_ID,i.DateInvoiced, ?)", BigDecimal.class),
			new ColumnInfo(Msg.getMsg(ctx, "DiscountDate"), "now()-paymentTermDueDays(i.C_PaymentTerm_ID,i.DateInvoiced,now())", Timestamp.class),
			new ColumnInfo(Msg.getMsg(ctx, "AmountDue"), "currencyConvert(invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID),i.C_Currency_ID, ?,?,i.C_ConversionType_ID, i.AD_Client_ID,i.AD_Org_ID)", BigDecimal.class),
			new ColumnInfo(Msg.getMsg(ctx, "AmountPay"), "currencyConvert(invoiceOpen(i.C_Invoice_ID,i.C_InvoicePaySchedule_ID)-paymentTermDiscount(i.GrandTotal,i.C_Currency_ID,i.C_PaymentTerm_ID,i.DateInvoiced, ?),i.C_Currency_ID, ?,?,i.C_ConversionType_ID, i.AD_Client_ID,i.AD_Org_ID)", BigDecimal.class)
			},
			//	FROM
			"C_Invoice_v i"
			+ " INNER JOIN C_BPartner bp ON (i.C_BPartner_ID=bp.C_BPartner_ID)"
			+ " INNER JOIN C_Currency c ON (i.C_Currency_ID=c.C_Currency_ID)"
			+ " INNER JOIN C_PaymentTerm p ON (i.C_PaymentTerm_ID=p.C_PaymentTerm_ID)",
			//	WHERE
			"i.IsSOTrx=? AND IsPaid='N'"
			//	Different Payment Selection
			+ " AND NOT EXISTS (SELECT * FROM C_PaySelectionLine psl"
			+                 " INNER JOIN C_PaySelectionCheck psc ON (psl.C_PaySelectionCheck_ID=psc.C_PaySelectionCheck_ID)"
			+                 " LEFT OUTER JOIN C_Payment pmt ON (pmt.C_Payment_ID=psc.C_Payment_ID)"
			+                 " WHERE i.C_Invoice_ID=psl.C_Invoice_ID AND psl.IsActive='Y'"
			+				  " AND (pmt.DocStatus IS NULL OR pmt.DocStatus NOT IN ('VO','RE')) )"
			+ " AND i.DocStatus IN ('CO','CL')"
			+ " AND i.AD_Client_ID=?",	//	additional where & order in loadTableInfo()
			true, "i");
	}   //  dynInit

	/**
	 *  Load Bank Info - Load Info from Bank Account and valid Documents (PaymentRule)
	 */
	public ArrayList<ValueNamePair> getPaymentRuleData(final BankInfo bi)
	{
		if (bi == null)
			return null;
		m_bankBalance = bi.Balance;
		
		final ArrayList<ValueNamePair> data = new ArrayList<ValueNamePair>();

		final int AD_Reference_ID = 195;  //  MLookupInfo.getAD_Reference_ID("All_Payment Rule");
		final Language language = Env.getLanguage(Env.getCtx());
		final MLookupInfo info = MLookupFactory.getLookup_List(language, AD_Reference_ID);
		final String query = info.getSqlQuery();
		final String sql = query.substring(0, query.indexOf(" ORDER BY"))
			+ query.substring(query.indexOf(" ORDER BY"));
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, bi.C_BP_BankAccount_ID);
			ResultSet rs = pstmt.executeQuery();
			ValueNamePair vp = null;
			while (rs.next())
			{
				vp = new ValueNamePair(rs.getString(2), rs.getString(3));   //  returns also not active
				data.add(vp);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		return data;
	}

	/**
	 *  Query and create TableInfo
	 */
	public void loadTableInfo(BankInfo bi, Timestamp payDate, ValueNamePair paymentRule, boolean onlyDue, 
			KeyNamePair bpartner, KeyNamePair docType, IMiniTable miniTable)
	{
		log.info("");
		//  not yet initialized
		if (m_sql == null)
			return;

		String sql = m_sql;
		//  Parameters
		String isSOTrx = "N";
		if (paymentRule != null && X_C_Order.PAYMENTRULE_DirectDebit.equals(paymentRule.getValue()))
		{
			isSOTrx = "Y";
			sql += " AND i.PaymentRule='" + X_C_Order.PAYMENTRULE_DirectDebit + "'";
		}
		//
		if (onlyDue)
			sql += " AND paymentTermDueDate(i.C_PaymentTerm_ID, i.DateInvoiced) <= ?";
		//
		KeyNamePair pp = bpartner;
		int C_BPartner_ID = pp.getKey();
		if (C_BPartner_ID != 0)
			sql += " AND i.C_BPartner_ID=?";
		//Document Type
		KeyNamePair dt = docType;
		int c_doctype_id  = dt.getKey();
		if (c_doctype_id   != 0)
			sql += " AND i.c_doctype_id =?";
		sql += " ORDER BY 2,3";

		log.trace(sql + " - C_Currency_ID=" + bi.C_Currency_ID + ", C_BPartner_ID=" + C_BPartner_ID + ", C_doctype_id=" + c_doctype_id  );
		//  Get Open Invoices
		try
		{
			int index = 1;
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(index++, payDate);		//	DiscountAmt
			pstmt.setInt(index++, bi.C_Currency_ID);	//	DueAmt
			pstmt.setTimestamp(index++, payDate);
			pstmt.setTimestamp(index++, payDate);		//	PayAmt
			pstmt.setInt(index++, bi.C_Currency_ID);
			pstmt.setTimestamp(index++, payDate);
			pstmt.setString(index++, isSOTrx);			//	IsSOTrx
			pstmt.setInt(index++, m_AD_Client_ID);		//	Client
			if (onlyDue)
				pstmt.setTimestamp(index++, payDate);
			if (C_BPartner_ID != 0)
				pstmt.setInt(index++, C_BPartner_ID);
			if (c_doctype_id  != 0)                    //Document type
				pstmt.setInt(index++, c_doctype_id );
			//
			ResultSet rs = pstmt.executeQuery();
			miniTable.loadTable(rs);
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
	}   //  loadTableInfo

	/**
	 *  Calculate selected rows.
	 *  - add up selected rows
	 */
	public String calculateSelection(IMiniTable miniTable)
	{
		m_noSelected = 0;
		BigDecimal invoiceAmt = new BigDecimal(0.0);

		int rows = miniTable.getRowCount();
		for (int i = 0; i < rows; i++)
		{
			IDColumn id = (IDColumn)miniTable.getValueAt(i, 0);
			if (id.isSelected())
			{
				BigDecimal amt = (BigDecimal)miniTable.getValueAt(i, 9);
				if (amt != null)
					invoiceAmt = invoiceAmt.add(amt);
				m_noSelected++;
			}
		}

		//  Information
		BigDecimal remaining = m_bankBalance.subtract(invoiceAmt);
		StringBuffer info = new StringBuffer();
		info.append(m_noSelected).append(" ").append(Msg.getMsg(Env.getCtx(), "Selected")).append(" - ");
		info.append(m_format.format(invoiceAmt)).append(", ");
		info.append(Msg.getMsg(Env.getCtx(), "Remaining")).append(" ").append(m_format.format(remaining));
		return info.toString();
	}   //  calculateSelection

	public Trx trx = null;
	
	/**
	 *  Generate PaySelection
	 */
	public String generatePaySelect(IMiniTable miniTable, ValueNamePair paymentRule, Timestamp payDate, BankInfo bi)
	{
		log.info("");
	//	String trxName Trx.createTrxName("PaySelect");
	//	Trx trx = Trx.get(trxName, true);	trx needs to be committed too
		String trxName = null;
		trx = null;

		String PaymentRule = paymentRule.getValue();

		//  Create Header
		m_ps = new MPaySelection(Env.getCtx(), 0, trxName);
		m_ps.setName (Msg.getMsg(Env.getCtx(), "VPaySelect")
				+ " - " + paymentRule.getName()
				+ " - " + payDate);
		m_ps.setPayDate (payDate);
		m_ps.setC_BP_BankAccount_ID(bi.C_BP_BankAccount_ID);
		m_ps.setIsApproved(true);
		if (!m_ps.save())
		{
			m_ps = null;
			return Msg.translate(Env.getCtx(), "C_PaySelection_ID");
		}
		log.info(m_ps.toString());

		//  Create Lines
		int rows = miniTable.getRowCount();
		int line = 0;
		for (int i = 0; i < rows; i++)
		{
			IDColumn id = (IDColumn)miniTable.getValueAt(i, 0);
			if (id.isSelected())
			{
				line += 10;
				MPaySelectionLine psl = new MPaySelectionLine (m_ps, line, PaymentRule);
				int C_Invoice_ID = id.getRecord_ID().intValue();
				BigDecimal OpenAmt = (BigDecimal)miniTable.getValueAt(i, 8);
				BigDecimal PayAmt = (BigDecimal)miniTable.getValueAt(i, 9);
				boolean isSOTrx = false;
				//
				psl.setInvoice(C_Invoice_ID, isSOTrx,
					OpenAmt, PayAmt, OpenAmt.subtract(PayAmt));
				if (!psl.save(trxName))
				{
					return Msg.translate(Env.getCtx(), "C_PaySelectionLine_ID");
				}
				log.debug("C_Invoice_ID=" + C_Invoice_ID + ", PayAmt=" + PayAmt);
			}
		}   //  for all rows in table
		
		return null;
	}   //  generatePaySelect

	/**************************************************************************
	 * Bank Account Info
	 */
	public class BankInfo
	{
		/**
		 * BankInfo
		 *
		 * @param newC_BP_BankAccount_ID
		 * @param newC_Currency_ID
		 * @param newName
		 * @param newCurrency
		 * @param newBalance
		 * @param newTransfers
		 */
		public BankInfo(int newC_BP_BankAccount_ID, int newC_Currency_ID,
				String newName, String newCurrency, BigDecimal newBalance, boolean newTransfers)
		{
			C_BP_BankAccount_ID = newC_BP_BankAccount_ID;
			C_Currency_ID = newC_Currency_ID;
			Name = newName;
			Currency = newCurrency;
			Balance = newBalance;
		}

		int C_BP_BankAccount_ID;
		int C_Currency_ID;
		String Name;
		public String Currency;
		public BigDecimal Balance;
		boolean Transfers;

		/**
		 * to String
		 *
		 * @return info
		 */
		@Override
		public String toString()
		{
			return Name;
		}
	}   // BankInfo
}   // VPaySelect
