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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.Adempiere;
import org.compiere.util.DB;

import de.metas.product.IProductBL;
import de.metas.requisition.RequisitionService;
import de.metas.uom.UomId;
import de.metas.util.Services;

/**
 * Requisition Line Model
 *
 * @author Jorg Janke
 * @version $Id: MRequisitionLine.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 2419978 ] Voiding PO, requisition don't set on NULL
 *         <li>BF [ 2608617 ] Error when I want to delete a PO document
 *         <li>BF [ 2609604 ] Add M_RequisitionLine.C_BPartner_ID
 *         <li>FR [ 2841841 ] Requisition Improvements
 *         https://sourceforge.net/tracker/?func=detail&aid=2841841&group_id=176962&atid=879335
 */
public class MRequisitionLine extends X_M_RequisitionLine
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3556301115666692794L;

	/**
	 * Get corresponding Requisition Line for given Order Line
	 *
	 * @param ctx
	 * @param C_OrderLine_ID order line
	 * @param trxName
	 * @return Requisition Line
	 */
	private static MRequisitionLine[] forC_Order_ID(Properties ctx, int C_Order_ID, String trxName)
	{
		final String whereClause = "EXISTS (SELECT 1 FROM C_OrderLine ol"
				+ " WHERE ol.C_OrderLine_ID=M_RequisitionLine.C_OrderLine_ID"
				+ " AND ol.C_Order_ID=?)";
		List<MRequisitionLine> list = new Query(ctx, MRequisitionLine.Table_Name, whereClause, trxName)
				.setParameters(new Object[] { C_Order_ID })
				.list(MRequisitionLine.class);
		return list.toArray(new MRequisitionLine[list.size()]);
	}

	/**
	 * UnLink Requisition Lines for given Order
	 *
	 * @param ctx
	 * @param C_Order_ID
	 * @param trxName
	 */
	public static void unlinkC_Order_ID(Properties ctx, int C_Order_ID, String trxName)
	{
		for (MRequisitionLine line : MRequisitionLine.forC_Order_ID(ctx, C_Order_ID, trxName))
		{
			line.setC_OrderLine_ID(0);
			line.saveEx();
		}
	}

	/**
	 * Get corresponding Requisition Line(s) for given Order Line
	 *
	 * @param ctx
	 * @param C_OrderLine_ID order line
	 * @param trxName
	 * @return array of Requisition Line(s)
	 */
	private static MRequisitionLine[] forC_OrderLine_ID(Properties ctx, int C_OrderLine_ID, String trxName)
	{
		final String whereClause = COLUMNNAME_C_OrderLine_ID + "=?";
		List<MRequisitionLine> list = new Query(ctx, MRequisitionLine.Table_Name, whereClause, trxName)
				.setParameters(new Object[] { C_OrderLine_ID })
				.list(MRequisitionLine.class);
		return list.toArray(new MRequisitionLine[list.size()]);
	}

	/**
	 * UnLink Requisition Lines for given Order Line
	 *
	 * @param ctx
	 * @param C_OrderLine_ID
	 * @param trxName
	 */
	public static void unlinkC_OrderLine_ID(Properties ctx, int C_OrderLine_ID, String trxName)
	{
		for (MRequisitionLine line : forC_OrderLine_ID(ctx, C_OrderLine_ID, trxName))
		{
			line.setC_OrderLine_ID(0);
			line.saveEx();
		}
	}

	public MRequisitionLine(Properties ctx, int M_RequisitionLine_ID, String trxName)
	{
		super(ctx, M_RequisitionLine_ID, trxName);
		if (is_new())
		{
			// setM_Requisition_ID (0);
			setLine(0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_RequisitionLine WHERE M_Requisition_ID=@M_Requisition_ID@
			setLineNetAmt(BigDecimal.ZERO);
			setPriceActual(BigDecimal.ZERO);
			setQty(BigDecimal.ONE);	// 1
		}

	}	// MRequisitionLine

	public MRequisitionLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MRequisitionLine(MRequisition req)
	{
		this(req.getCtx(), 0, req.get_TrxName());
		setClientOrg(req);
		setM_Requisition_ID(req.getM_Requisition_ID());
		m_parent = req;
	}

	/** Parent */
	private MRequisition m_parent = null;

	private MRequisition getParent()
	{
		if (m_parent == null)
			m_parent = new MRequisition(getCtx(), getM_Requisition_ID(), get_TrxName());
		return m_parent;
	}	// getParent

	private RequisitionService getRequisitionService()
	{
		return Adempiere.getBean(RequisitionService.class);
	}

	@Override
	public I_M_Requisition getM_Requisition()
	{
		return getParent();
	}

	public void setPrice()
	{
		final BigDecimal price = getRequisitionService().computePrice(getParent(), this);
		if (price != null)
		{
			setPriceActual(price);
		}
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (newRecord && getParent().isComplete())
		{
			throw new AdempiereException("@ParentComplete@ @M_RequisitionLine_ID@");
		}

		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM M_RequisitionLine WHERE M_Requisition_ID=?";
			int line = DB.getSQLValueEx(get_TrxName(), sql, getM_Requisition_ID());
			setLine(line);
		}

		// Product & ASI - Charge
		if (getM_Product_ID() > 0 && getC_Charge_ID() > 0)
		{
			setC_Charge_ID(0);
		}
		if (getM_AttributeSetInstance_ID() > 0 && getC_Charge_ID() > 0)
		{
			setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		}

		// Product UOM
		if (getM_Product_ID() > 0 && getC_UOM_ID() <= 0)
		{
			final UomId productUomId = Services.get(IProductBL.class).getStockUOMId(getM_Product_ID());
			setC_UOM_ID(productUomId.getRepoId());
		}

		//
		if (getPriceActual().signum() == 0)
		{
			setPrice();
		}

		getRequisitionService().updateLineNetAmt(this);

		return true;
	}	// beforeSave

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
			return success;

		updateHeader();

		return true;
	}	// afterSave

	@Override
	protected boolean afterDelete(boolean success)
	{
		if (!success)
			return success;

		updateHeader();

		return true;
	}	// afterDelete

	private void updateHeader()
	{
		String sql = "UPDATE M_Requisition r"
				+ " SET TotalLines="
				+ "(SELECT COALESCE(SUM(LineNetAmt),0) FROM M_RequisitionLine rl "
				+ "WHERE r.M_Requisition_ID=rl.M_Requisition_ID) "
				+ "WHERE M_Requisition_ID=?";
		DB.executeUpdateAndThrowExceptionOnFail(sql, new Object[] { getM_Requisition_ID() }, ITrx.TRXNAME_ThreadInherited);
		m_parent = null;
	}

}	// MRequisitionLine
