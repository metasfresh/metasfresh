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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 *****************************************************************************/
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.util.DB;

import de.metas.product.IProductBL;

/**
 * Order Line Model.
 * <code>
 * 			MDDOrderLine ol = new MDDOrderLine(m_order);
			ol.setM_Product_ID(wbl.getM_Product_ID());
			ol.setQtyOrdered(wbl.getQuantity());
			ol.setPriceActual(wbl.getPrice());
			ol.setTax();
			ol.save();

 *	</code>
 *
 * @author Jorg Janke
 * @version $Id: MOrderLine.java,v 1.6 2006/10/02 05:18:39 jjanke Exp $
 */
public class MDDOrderLine extends X_DD_OrderLine
{
	/**
	 *
	 */
	private static final long serialVersionUID = -8878804332001384969L;

	/**
	 * Get Order Unreserved Qty
	 *
	 * @param ctx context
	 * @param M_Locator_ID wh
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID asi
	 * @param excludeC_OrderLine_ID exclude C_OrderLine_ID
	 * @return Unreserved Qty
	 */
	public static BigDecimal getNotReserved(Properties ctx, int M_Locator_ID,
			int M_Product_ID, int M_AttributeSetInstance_ID, int excludeDD_OrderLine_ID)
	{

		ArrayList<Object> params = new ArrayList<>();
		params.add(M_Locator_ID);
		params.add(M_Product_ID);
		params.add(excludeDD_OrderLine_ID);

		String sql = "SELECT SUM(QtyOrdered-QtyDelivered-QtyReserved) "
				+ "FROM DD_OrderLine ol"
				+ " INNER JOIN DD_Order o ON (ol.DD_Order_ID=o.DD_Order_ID) "
				+ "WHERE ol.M_Locator_ID=?"	// #1
				+ " AND M_Product_ID=?"			// #2
				+ " AND o.IsSOTrx='N' AND o.DocStatus='DR'"
				+ " AND QtyOrdered-QtyDelivered-QtyReserved<>0"
				+ " AND ol.DD_OrderLine_ID<>?";

		if (M_AttributeSetInstance_ID != 0)
		{
			sql += " AND M_AttributeSetInstance_ID=?";
			params.add(M_AttributeSetInstance_ID);
		}
		return DB.getSQLValueBD(null, sql.toString(), params);
	}	// getNotReserved

	/**************************************************************************
	 * Default Constructor
	 *
	 * @param ctx context
	 * @param DD_OrderLine_ID order line to load
	 * @param trxName trx name
	 */
	public MDDOrderLine(Properties ctx, int DD_OrderLine_ID, String trxName)
	{
		super(ctx, DD_OrderLine_ID, trxName);
		if (is_new())
		{
			// setC_Order_ID (0);
			// setLine (0);
			// setM_Warehouse_ID (0); // @M_Warehouse_ID@
			// setC_BPartner_ID(0);
			// setC_BPartner_Location_ID (0); // @C_BPartner_Location_ID@
			// setC_Currency_ID (0); // @C_Currency_ID@
			// setDateOrdered (new Timestamp(System.currentTimeMillis())); // @DateOrdered@
			//
			// setC_Tax_ID (0);
			// setC_UOM_ID (0);
			//
			setFreightAmt(BigDecimal.ZERO);
			setLineNetAmt(BigDecimal.ZERO);
			//
			setM_AttributeSetInstance_ID(0);
			//
			setQtyEntered(BigDecimal.ZERO);
			setQtyInTransit(BigDecimal.ZERO);
			setConfirmedQty(BigDecimal.ZERO);
			setTargetQty(BigDecimal.ZERO);
			setPickedQty(BigDecimal.ZERO);
			setQtyOrdered(BigDecimal.ZERO);	// 1
			setQtyDelivered(BigDecimal.ZERO);
			setQtyReserved(BigDecimal.ZERO);
			//
			setIsDescription(false);	// N
			setProcessed(false);
			setLine(0);
		}
	}	// MDDOrderLine

	/**
	 * Parent Constructor.
	 */
	public MDDOrderLine(MDDOrder order)
	{
		this(order.getCtx(), 0, order.get_TrxName());
		if (order.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setDD_Order_ID(order.getDD_Order_ID());	// parent
		setOrder(order);
	}	// MDDOrderLine

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set record
	 * @param trxName transaction
	 */
	public MDDOrderLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MDDOrderLine

	/**
	 * Set Defaults from Order.
	 * Does not set Parent !!
	 *
	 * @param order order
	 */
	private void setOrder(MDDOrder order)
	{
		setClientOrg(order);
		setDateOrdered(order.getDateOrdered());
		setDatePromised(order.getDatePromised());

		// Don't set Activity, etc as they are overwrites
	}

	/**
	 * Get Parent
	 *
	 * @return parent
	 */
	@Deprecated
	private MDDOrder getParent()
	{
		return LegacyAdapters.convertToPO(getDD_Order());
	}	// getParent

	/**
	 * Set Product
	 *
	 * @param product product
	 */
	public void setProduct(MProduct product)
	{
		setM_Product(product);
		if (product != null)
		{
			setM_Product_ID(product.getM_Product_ID());
			setC_UOM_ID(product.getC_UOM_ID());
		}
		else
		{
			setM_Product_ID(0);
			set_ValueNoCheck(COLUMNNAME_C_UOM_ID, null);
		}
		setM_AttributeSetInstance_ID(0);
	}	// setProduct

	/**
	 * Set M_AttributeSetInstance_ID
	 *
	 * @param M_AttributeSetInstance_ID id
	 */
	@Override
	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID == 0)		// 0 is valid ID
			set_Value(COLUMNNAME_M_AttributeSetInstance_ID, new Integer(0));
		else
			super.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
	}	// setM_AttributeSetInstance_ID

	/**
	 * Can Change Warehouse
	 *
	 * @return true if warehouse can be changed
	 */
	boolean canChangeWarehouse()
	{
		if (getQtyDelivered().signum() != 0)
		{
			throw new AdempiereException("@QtyDelivered@=" + getQtyDelivered());
		}

		if (getQtyReserved().signum() != 0)
		{
			throw new AdempiereException("@QtyReserved=" + getQtyReserved());
		}
		// We can change
		return true;
	}	// canChangeWarehouse

	/**
	 * Get C_Project_ID
	 *
	 * @return project
	 */
	@Override
	public int getC_Project_ID()
	{
		int ii = super.getC_Project_ID();
		if (ii == 0)
			ii = getDD_Order().getC_Project_ID();
		return ii;
	}	// getC_Project_ID

	/**
	 * Get C_Activity_ID
	 *
	 * @return Activity
	 */
	@Override
	public int getC_Activity_ID()
	{
		int ii = super.getC_Activity_ID();
		if (ii == 0)
			ii = getDD_Order().getC_Activity_ID();
		return ii;
	}	// getC_Activity_ID

	/**
	 * Get C_Campaign_ID
	 *
	 * @return Campaign
	 */
	@Override
	public int getC_Campaign_ID()
	{
		int ii = super.getC_Campaign_ID();
		if (ii == 0)
			ii = getDD_Order().getC_Campaign_ID();
		return ii;
	}	// getC_Campaign_ID

	/**
	 * Get User2_ID
	 *
	 * @return User2
	 */
	@Override
	public int getUser1_ID()
	{
		int ii = super.getUser1_ID();
		if (ii == 0)
			ii = getDD_Order().getUser1_ID();
		return ii;
	}	// getUser1_ID

	/**
	 * Get User2_ID
	 *
	 * @return User2
	 */
	@Override
	public int getUser2_ID()
	{
		int ii = super.getUser2_ID();
		if (ii == 0)
			ii = getDD_Order().getUser2_ID();
		return ii;
	}	// getUser2_ID

	/**
	 * Get AD_OrgTrx_ID
	 *
	 * @return trx org
	 */
	@Override
	public int getAD_OrgTrx_ID()
	{
		int ii = super.getAD_OrgTrx_ID();
		if (ii == 0)
			ii = getDD_Order().getAD_OrgTrx_ID();
		return ii;
	}	// getAD_OrgTrx_ID

	/**************************************************************************
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MDDOrderLine[")
				.append(get_ID()).append(",Line=").append(getLine())
				.append(",Ordered=").append(getQtyOrdered())
				.append(",Delivered=").append(getQtyDelivered())
				.append(",Reserved=").append(getQtyReserved());
		if (getC_OrderLineSO_ID() > 0)
		{
			sb.append(", C_OrderLineSO_ID=").append(getC_OrderLineSO_ID());
		}
		sb.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	// addDescription

	/**
	 * Set C_Charge_ID
	 *
	 * @param C_Charge_ID charge
	 */
	@Override
	public void setC_Charge_ID(int C_Charge_ID)
	{
		super.setC_Charge_ID(C_Charge_ID);
		if (C_Charge_ID > 0)
			set_ValueNoCheck(COLUMNNAME_C_UOM_ID, null);
	}	// setC_Charge_ID

	/**
	 * Set Qty Entered/Ordered.
	 * Use this Method if the Line UOM is the Product UOM
	 *
	 * @param Qty QtyOrdered/Entered
	 */
	public void setQty(BigDecimal Qty)
	{
		super.setQtyEntered(Qty);
		super.setQtyOrdered(getQtyEntered());
	}	// setQty

	/**
	 * Set Qty Entered - enforce entered UOM
	 *
	 * @param QtyEntered
	 */
	@Override
	public void setQtyEntered(BigDecimal QtyEntered)
	{
		if (QtyEntered != null && getC_UOM_ID() != 0)
		{
			int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
			QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyEntered(QtyEntered);
	}	// setQtyEntered

	/**
	 * Set Qty Ordered - enforce Product UOM
	 *
	 * @param QtyOrdered
	 */
	@Override
	public void setQtyOrdered(BigDecimal QtyOrdered)
	{
		final I_M_Product product = getM_Product();
		if (QtyOrdered != null && product != null)
		{
			final int precision = Services.get(IProductBL.class).getUOMPrecision(product);
			QtyOrdered = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyOrdered(QtyOrdered);
	}	// setQtyOrdered

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord
	 * @return true if it can be sabed
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (newRecord && getParent().isComplete())
		{
			throw new AdempiereException("@ParentComplete@ @DD_OrderLine_ID@");
		}
		// Get Defaults from Parent
		/*
		 * if (getC_BPartner_ID() == 0 || getC_BPartner_Location_ID() == 0
		 * || getM_Warehouse_ID() == 0)
		 * setOrder (getParent());
		 */
		// if (m_M_PriceList_ID == 0)
		// setHeaderInfo(getParent());

		// R/O Check - Product/Warehouse Change
		if (!newRecord
				&& (is_ValueChanged("M_Product_ID") || is_ValueChanged("M_Locator_ID") || is_ValueChanged("M_LocatorTo_ID")))
		{
			if (!canChangeWarehouse())
			{
				throw new AdempiereException("Changing product/locator is not allowed");
			}
		}	// Product Changed

		// Charge
		if (getC_Charge_ID() != 0 && getM_Product_ID() != 0)
			setM_Product_ID(0);
		// No Product
		if (getM_Product_ID() == 0)
			setM_AttributeSetInstance_ID(0);
		// Product

		// UOM
		if (getC_UOM_ID() == 0
				&& (getM_Product_ID() != 0
						|| getC_Charge_ID() != 0))
		{
			int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
			if (C_UOM_ID > 0)
				setC_UOM_ID(C_UOM_ID);
		}
		// Qty Precision
		if (newRecord || is_ValueChanged(COLUMNNAME_QtyEntered))
			setQtyEntered(getQtyEntered());
		if (newRecord || is_ValueChanged(COLUMNNAME_QtyOrdered))
			setQtyOrdered(getQtyOrdered());

		// FreightAmt Not used
		if (BigDecimal.ZERO.compareTo(getFreightAmt()) != 0)
			setFreightAmt(BigDecimal.ZERO);

		// Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM DD_OrderLine WHERE DD_Order_ID=?";

			int ii = DB.getSQLValue(get_TrxName(), sql, getDD_Order_ID());
			setLine(ii);
		}

		return true;
	}	// beforeSave

	/**
	 * Before Delete
	 *
	 * @return true if it can be deleted
	 */
	@Override
	protected boolean beforeDelete()
	{
		// R/O Check - Something delivered. etc.
		if (getQtyDelivered().signum() != 0)
		{
			throw new AdempiereException("@QtyDelivered@: " + getQtyDelivered());
		}
		if (getQtyReserved().signum() != 0)
		{
			// For PO should be On Order
			throw new AdempiereException("@QtyReserved@: " + getQtyReserved());
		}

		return true;
	}	// beforeDelete
}	// MDDOrderLine
