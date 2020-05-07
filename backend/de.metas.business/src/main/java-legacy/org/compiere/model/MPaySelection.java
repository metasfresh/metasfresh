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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * AP Payment Selection
 * 
 * @author Jorg Janke
 * @version $Id: MPaySelection.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPaySelection extends X_C_PaySelection
{
	public static final String ERR_PaySelection_NoBPBankAccount = "ERR_PaySelection_NoBPBankAccount";
	/**
	 * 
	 */
	private static final long serialVersionUID = -6521282913549455131L;

	public MPaySelection(Properties ctx, int C_PaySelection_ID, String trxName)
	{
		super(ctx, C_PaySelection_ID, trxName);
		if (C_PaySelection_ID == 0)
		{
			setTotalAmt(BigDecimal.ZERO);
			setIsApproved(false);
			setProcessed(false);
		}
	}

	public MPaySelection(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/** Lines */
	private MPaySelectionLine[] m_lines = null;
	/** Currency of Bank Account */
	private Integer m_C_Currency_ID = null;

	/**
	 * Get Lines
	 * 
	 * @param requery requery
	 * @return lines
	 */
	public MPaySelectionLine[] getLines(final boolean requery)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		ArrayList<MPaySelectionLine> list = new ArrayList<>();
		String sql = "SELECT * FROM C_PaySelectionLine WHERE C_PaySelection_ID=? ORDER BY Line";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_PaySelection_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MPaySelectionLine(getCtx(), rs, get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error("getLines", e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		//
		m_lines = new MPaySelectionLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}

	/**
	 * Get Currency of Bank Account
	 * 
	 * @return C_Currency_ID
	 */
	public int getC_Currency_ID()
	{
		if (m_C_Currency_ID == null)
		{
			m_C_Currency_ID = getC_BP_BankAccount().getC_Currency_ID();
		}
		return m_C_Currency_ID;
	}

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MPaySelection[");
		sb.append(get_ID()).append(",").append(getName())
				.append("]");
		return sb.toString();
	}

}
