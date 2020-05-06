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
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * Payment Selection Line Model
 * 
 * @author Jorg Janke
 * @version $Id: MPaySelectionLine.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPaySelectionLine extends X_C_PaySelectionLine
{
	private static final long serialVersionUID = -3486055138810301789L;

	public MPaySelectionLine(Properties ctx, int C_PaySelectionLine_ID, String trxName)
	{
		super(ctx, C_PaySelectionLine_ID, trxName);
		if (is_new())
		{
			// setC_PaySelection_ID (0);
			// setPaymentRule (null); // S
			// setLine (0); // @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM C_PaySelectionLine WHERE C_PaySelection_ID=@C_PaySelection_ID@
			// setC_Invoice_ID (0);
			setIsSOTrx(false);
			setOpenAmt(BigDecimal.ZERO);
			setPayAmt(BigDecimal.ZERO);
			setDiscountAmt(BigDecimal.ZERO);
			setDifferenceAmt(BigDecimal.ZERO);
			setIsManual(false);
		}
	}

	public MPaySelectionLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		setDifferenceAmt(getOpenAmt().subtract(getPayAmt()).subtract(getDiscountAmt()));
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		setHeader();
		return success;
	}

	@Override
	protected boolean afterDelete(boolean success)
	{
		setHeader();
		return success;
	}

	private void setHeader()
	{
		// Update Header
		String sql = "UPDATE C_PaySelection ps "
				+ "SET TotalAmt = (SELECT COALESCE(SUM(psl.PayAmt),0) "
				+ "FROM C_PaySelectionLine psl "
				+ "WHERE ps.C_PaySelection_ID=psl.C_PaySelection_ID AND psl.IsActive='Y') "
				+ "WHERE C_PaySelection_ID=" + getC_PaySelection_ID();
		DB.executeUpdate(sql, get_TrxName());
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MPaySelectionLine[");
		sb.append(get_ID()).append(",C_Invoice_ID=").append(getC_Invoice_ID())
				.append(",PayAmt=").append(getPayAmt())
				.append(",DifferenceAmt=").append(getDifferenceAmt())
				.append("]");
		return sb.toString();
	}
}
