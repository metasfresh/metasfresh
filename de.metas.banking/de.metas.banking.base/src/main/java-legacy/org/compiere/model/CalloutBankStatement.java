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
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * Bank Statement Callout
 *
 * @author Jorg Janke
 * @version $Id: CalloutBankStatement.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class CalloutBankStatement extends CalloutEngine
{
	/**
	 * BankStmt - Amount. Calculate ChargeAmt = StmtAmt - TrxAmt - InterestAmt or id Charge is entered - InterestAmt = StmtAmt - TrxAmt - ChargeAmt
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String amount(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())
			return "";

		// Get Stmt & Trx
		BigDecimal stmt = (BigDecimal)mTab.getValue("StmtAmt");
		if (stmt == null)
			stmt = Env.ZERO;
		BigDecimal trx = (BigDecimal)mTab.getValue("TrxAmt");
		if (trx == null)
			trx = Env.ZERO;
		BigDecimal bd = stmt.subtract(trx);

		// Charge - calculate Interest
		if (mField.getColumnName().equals("ChargeAmt"))
		{
			BigDecimal charge = (BigDecimal)value;
			if (charge == null)
				charge = Env.ZERO;
			bd = bd.subtract(charge);
			// log.trace(log.l5_DData, "Interest (" + bd + ") = Stmt(" + stmt + ") - Trx(" + trx + ") - Charge(" + charge + ")");
			mTab.setValue("InterestAmt", bd);
		}
		// Calculate Charge
		else
		{
			BigDecimal interest = (BigDecimal)mTab.getValue("InterestAmt");
			if (interest == null)
				interest = Env.ZERO;
			bd = bd.subtract(interest);
			// log.trace(log.l5_DData, "Charge (" + bd + ") = Stmt(" + stmt + ") - Trx(" + trx + ") - Interest(" + interest + ")");
			mTab.setValue("ChargeAmt", bd);
		}
		return "";
	}   // amount

	/**
	 * BankStmt - Payment. Update Transaction Amount when payment is selected
	 *
	 * @param ctx context
	 * @param WindowNo window no
	 * @param mTab tab
	 * @param mField field
	 * @param value value
	 * @return null or error message
	 */
	public String payment(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_Payment_ID = (Integer)value;
		if (C_Payment_ID == null || C_Payment_ID.intValue() == 0)
			return "";
		//
		BigDecimal stmt = (BigDecimal)mTab.getValue("StmtAmt");
		if (stmt == null)
			stmt = Env.ZERO;

		String sql = "SELECT PayAmt FROM C_Payment_v WHERE C_Payment_ID=?";		// 1
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_Payment_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				BigDecimal bd = rs.getBigDecimal(1);
				mTab.setValue("TrxAmt", bd);
				if (stmt.compareTo(Env.ZERO) == 0)
					mTab.setValue("StmtAmt", bd);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "BankStmt_Payment", e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// Recalculate Amounts
		amount(ctx, WindowNo, mTab, mField, value);
		return "";
	}	// payment

}	// CalloutBankStatement
