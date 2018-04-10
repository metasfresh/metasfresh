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

/**
 * Cost Detail Model
 * 
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF: 2431123 Return Trx changes weighted average cost
 *         <li>BF: 1568752 Average invoice costing: landed costs incorrectly applied
 * @author Armen Rizal & Bayu Cahya
 *         <li>BF [ 2129781 ] Cost Detail not created properly for multi acc schema
 * @author Teo Sarca
 *         <li>BF [ 2847648 ] Manufacture & shipment cost errors
 *         https://sourceforge.net/tracker/?func=detail&aid=2847648&group_id=176962&atid=934929
 * @version $Id: MCostDetail.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 * 
 */
public class MCostDetail extends X_M_CostDetail
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5452006110417178583L;

	public MCostDetail(Properties ctx, int M_CostDetail_ID, String trxName)
	{
		super(ctx, M_CostDetail_ID, trxName);
		if (is_new())
		{
			// setC_AcctSchema_ID (0);
			// setM_Product_ID (0);
			setM_AttributeSetInstance_ID(0);
			// setC_OrderLine_ID (0);
			// setM_InOutLine_ID(0);
			// setC_InvoiceLine_ID (0);
			setProcessed(false);
			setAmt(BigDecimal.ZERO);
			setQty(BigDecimal.ZERO);
			setIsSOTrx(false);
			setDeltaAmt(BigDecimal.ZERO);
			setDeltaQty(BigDecimal.ZERO);
		}
	}	// MCostDetail

	public MCostDetail(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MCostDetail

	@Override
	public void setAmt(final BigDecimal Amt)
	{
		if (isProcessed())
			throw new IllegalStateException("Cannot change Amt - processed");
		if (Amt == null)
			super.setAmt(BigDecimal.ZERO);
		else
			super.setAmt(Amt);
	}	// setAmt

	@Override
	public void setQty(BigDecimal Qty)
	{
		if (isProcessed())
			throw new IllegalStateException("Cannot change Qty - processed");
		if (Qty == null)
			super.setQty(BigDecimal.ZERO);
		else
			super.setQty(Qty);
	}	// setQty

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MCostDetail[");
		sb.append(get_ID());
		if (getC_OrderLine_ID() != 0)
			sb.append(",C_OrderLine_ID=").append(getC_OrderLine_ID());
		if (getM_InOutLine_ID() != 0)
			sb.append(",M_InOutLine_ID=").append(getM_InOutLine_ID());
		if (getC_InvoiceLine_ID() != 0)
			sb.append(",C_InvoiceLine_ID=").append(getC_InvoiceLine_ID());
		if (getC_ProjectIssue_ID() != 0)
			sb.append(",C_ProjectIssue_ID=").append(getC_ProjectIssue_ID());
		if (getM_MovementLine_ID() != 0)
			sb.append(",M_MovementLine_ID=").append(getM_MovementLine_ID());
		if (getM_InventoryLine_ID() != 0)
			sb.append(",M_InventoryLine_ID=").append(getM_InventoryLine_ID());
		sb.append(",Amt=").append(getAmt())
				.append(",Qty=").append(getQty());

		sb.append(",DeltaAmt=").append(getDeltaAmt())
				.append(",DeltaQty=").append(getDeltaQty());

		sb.append("]");
		return sb.toString();
	}	// toString
}	// MCostDetail
