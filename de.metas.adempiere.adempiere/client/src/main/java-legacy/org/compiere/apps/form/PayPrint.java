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
import java.util.ArrayList;

import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.MPaySelectionCheck;
import org.compiere.model.MPaymentBatch;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * 
 * @deprecated disabled because found already broken (mainly because of C_BP_BankAccount.CurrentBalance)
 */
@Deprecated
public class PayPrint {

	/**	Window No			*/
	public int         	m_WindowNo = 0;
	/**	Used Bank Account	*/
	public int				m_C_BP_BankAccount_ID = -1;

	/** Payment Information */
	public MPaySelectionCheck[]     m_checks = null;
	/** Payment Batch		*/
	public MPaymentBatch	m_batch = null; 
	/**	Logger			*/
	public static Logger log = LogManager.getLogger(PayPrint.class);
	
	public PayPrint()
	{
		super();
		throw new UnsupportedOperationException(); // disabled because already broken
	}
	
	public ArrayList<KeyNamePair> getPaySelectionData()
	{
		ArrayList<KeyNamePair> data = new ArrayList<KeyNamePair>();
		
		log.info("");
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());

		//  Load PaySelect
		String sql = "SELECT C_PaySelection_ID, Name || ' - ' || TotalAmt FROM C_PaySelection "
			+ "WHERE AD_Client_ID=? AND Processed='Y' AND IsActive='Y'"
			+ "ORDER BY PayDate DESC";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Client_ID);
			ResultSet rs = pstmt.executeQuery();
			//
			while (rs.next())
			{
				KeyNamePair pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
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

	public String bank;
	public String currency;
	public BigDecimal balance;
	
	/**
	 *  PaySelect changed - load Bank
	 */
	public void loadPaySelectInfo(int C_PaySelection_ID)
	{
		//  load Banks from PaySelectLine
		m_C_BP_BankAccount_ID = -1;
		String sql = "SELECT ps.C_BP_BankAccount_ID, b.Name || ' ' || ba.AccountNo,"	//	1..2
			+ " c.ISO_Code, CurrentBalance "										//	3..4
			+ "FROM C_PaySelection ps"
			+ " INNER JOIN C_BP_BankAccount ba ON (ps.C_BP_BankAccount_ID=ba.C_BP_BankAccount_ID)"
			+ " INNER JOIN C_Bank b ON (ba.C_Bank_ID=b.C_Bank_ID)"
			+ " INNER JOIN C_Currency c ON (ba.C_Currency_ID=c.C_Currency_ID) "
			+ "WHERE ps.C_PaySelection_ID=? AND ps.Processed='Y' AND ba.IsActive='Y'";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_PaySelection_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_C_BP_BankAccount_ID = rs.getInt(1);
				bank = rs.getString(2);
				currency = rs.getString(3);
				balance = rs.getBigDecimal(4);
			}
			else
			{
				m_C_BP_BankAccount_ID = -1;
				bank = "";
				currency = "";
				balance = Env.ZERO;
				log.error("No active BankAccount for C_PaySelection_ID=" + C_PaySelection_ID);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
	}   //  loadPaySelectInfo

	/**
	 *  Bank changed - load PaymentRule
	 */
	public ArrayList<ValueNamePair> loadPaymentRule(int C_PaySelection_ID)
	{
		ArrayList<ValueNamePair> data = new ArrayList<ValueNamePair>();

		// load PaymentRule for Bank
		int AD_Reference_ID = 195;  //  MLookupInfo.getAD_Reference_ID("All_Payment Rule");
		MLookupInfo info = MLookupFactory.getLookup_List(AD_Reference_ID);
		final String query = info.getSqlQuery();
		String sql = query.substring(0, query.indexOf(" ORDER BY"))
			+ " AND " + info.getKeyColumnFQ()
			+ " IN (SELECT PaymentRule FROM C_PaySelectionCheck WHERE C_PaySelection_ID=?) "
			+ query.substring(query.indexOf(" ORDER BY"));
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_PaySelection_ID);
			ResultSet rs = pstmt.executeQuery();
			//
			while (rs.next())
			{
				ValueNamePair pp = new ValueNamePair(rs.getString(2), rs.getString(3));
				data.add(pp);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		
		if (data.size() == 0)
			log.info("PaySel=" + C_PaySelection_ID + ", BAcct=" + m_C_BP_BankAccount_ID + " - " + sql);
		
		return data;
	}   //  loadPaymentRule
	
	public String noPayments;
	public Integer documentNo;
	

	/**
	 *  PaymentRule changed - load DocumentNo, NoPayments,
	 *  enable/disable EFT, Print
	 */
	public String loadPaymentRuleInfo(int C_PaySelection_ID, String PaymentRule)
	{
		String msg = null;
		
		String sql = "SELECT COUNT(*) "
			+ "FROM C_PaySelectionCheck "
			+ "WHERE C_PaySelection_ID=?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_PaySelection_ID);
			ResultSet rs = pstmt.executeQuery();
			//
			if (rs.next())
				noPayments = String.valueOf(rs.getInt(1));
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}

		//  DocumentNo
		sql = "SELECT CurrentNext "
			+ "FROM C_BP_BankAccountDoc "
			+ "WHERE C_BP_BankAccount_ID=? AND PaymentRule=? AND IsActive='Y'";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_C_BP_BankAccount_ID);
			pstmt.setString(2, PaymentRule);
			ResultSet rs = pstmt.executeQuery();
			//
			if (rs.next())
				documentNo = new Integer(rs.getInt(1));
			else
			{
				log.error("VPayPrint.loadPaymentRuleInfo - No active BankAccountDoc for C_BP_BankAccount_ID="
					+ m_C_BP_BankAccount_ID + " AND PaymentRule=" + PaymentRule);
				msg = "VPayPrintNoDoc";
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		
		return msg;
	}   //  loadPaymentRuleInfo
}
