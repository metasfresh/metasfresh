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
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.exceptions.WarehouseLocatorConflictException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.product.IProductBL;

/**
 * InOut Line
 *
 * @author Jorg Janke
 * @version $Id: MInOutLine.java,v 1.5 2006/07/30 00:51:03 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <ul>
 *         <li>BF [ 2784194 ] Check Warehouse-Locator conflict https://sourceforge.net/tracker/?func=detail&aid=2784194&group_id=176962&atid=879332
 *         <li>BF [ 2797938 ] Receipt should not allow lines with Qty=0 https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2797938&group_id=176962
 *         </ul>
 */
public class MInOutLine extends X_M_InOutLine
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8630611882798722864L;

	public MInOutLine(Properties ctx, int M_InOutLine_ID, String trxName)
	{
		super(ctx, M_InOutLine_ID, trxName);
		if (is_new())
		{
			// setLine (0);
			// setM_Locator_ID (0);
			// setC_UOM_ID (0);
			// setM_Product_ID (0);
			setM_AttributeSetInstance_ID(0);
			// setMovementQty (Env.ZERO);
			setConfirmedQty(Env.ZERO);
			setPickedQty(Env.ZERO);
			setScrappedQty(Env.ZERO);
			setTargetQty(Env.ZERO);
			setIsInvoiced(false);
			setIsDescription(false);
		}
	}	// MInOutLine

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set record
	 * @param trxName transaction
	 */
	public MInOutLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MInOutLine

	/**
	 * Parent Constructor
	 * 
	 * @param inout parent
	 */
	public MInOutLine(MInOut inout)
	{
		this(inout.getCtx(), 0, inout.get_TrxName());
		setClientOrg(inout);
		setM_InOut(inout);
		setM_Warehouse_ID(inout.getM_Warehouse_ID());
		setC_Project_ID(inout.getC_Project_ID());
	}	// MInOutLine

	/** Product */
	private MProduct m_product = null;
	/** Warehouse */
	private int m_M_Warehouse_ID = 0;

	/**
	 * Get Parent
	 *
	 * @return parent
	 */
	public final MInOut getParent()
	{
		final I_M_InOut parent = getM_InOut();
		return LegacyAdapters.convertToPO(parent);
	}

	/**
	 * Set Order Line.
	 * Does not set Quantity!
	 *
	 * @param oLine order line
	 * @param M_Locator_ID locator
	 * @param Qty used only to find suitable locator
	 */
	public void setOrderLine(I_C_OrderLine oLine, int M_Locator_ID, BigDecimal Qty)
	{
		setC_OrderLine_ID(oLine.getC_OrderLine_ID());
		setLine(oLine.getLine());
		setC_UOM_ID(oLine.getC_UOM_ID());
		MProduct product = (MProduct)oLine.getM_Product();
		if (product == null)
		{
			set_ValueNoCheck("M_Product_ID", null);
			set_ValueNoCheck("M_AttributeSetInstance_ID", null);
			set_ValueNoCheck("M_Locator_ID", null);
		}
		else
		{
			setM_Product_ID(oLine.getM_Product_ID());

			// 08811
			// avoid direct copy of ASI ID!
			if (oLine.getM_AttributeSetInstance_ID() > 0)
			{
				final I_M_AttributeSetInstance newASI = Services.get(IAttributeDAO.class).copy(oLine.getM_AttributeSetInstance());
				setM_AttributeSetInstance_ID(newASI.getM_AttributeSetInstance_ID());
			}
			else
			{
				setM_AttributeSetInstance_ID(oLine.getM_AttributeSetInstance_ID());
			}
			//
			if (Services.get(IProductBL.class).isItem(product))
			{
				if (M_Locator_ID == 0)
					setM_Locator_ID(Qty);	// requires warehouse, product, asi
				else
					setM_Locator_ID(M_Locator_ID);
			}
			else
				set_ValueNoCheck("M_Locator_ID", null);
		}
		setC_Charge_ID(oLine.getC_Charge_ID());
		setDescription(oLine.getDescription());
		setIsDescription(oLine.isDescription());
		//
		setC_Project_ID(oLine.getC_Project_ID());
		setC_ProjectPhase_ID(oLine.getC_ProjectPhase_ID());
		setC_ProjectTask_ID(oLine.getC_ProjectTask_ID());
		setC_Activity_ID(oLine.getC_Activity_ID());
		setC_Campaign_ID(oLine.getC_Campaign_ID());
		setAD_OrgTrx_ID(oLine.getAD_OrgTrx_ID());
		setUser1_ID(oLine.getUser1_ID());
		setUser2_ID(oLine.getUser2_ID());
	}	// setOrderLine

	/**
	 * Set Invoice Line.
	 * Does not set Quantity!
	 *
	 * @param iLine invoice line
	 * @param M_Locator_ID locator
	 * @param Qty qty only fo find suitable locator
	 */
	public void setInvoiceLine(MInvoiceLine iLine, int M_Locator_ID, BigDecimal Qty)
	{
		setC_OrderLine_ID(iLine.getC_OrderLine_ID());
		setLine(iLine.getLine());
		setC_UOM_ID(iLine.getC_UOM_ID());
		int M_Product_ID = iLine.getM_Product_ID();
		if (M_Product_ID == 0)
		{
			set_ValueNoCheck("M_Product_ID", null);
			set_ValueNoCheck("M_Locator_ID", null);
			set_ValueNoCheck("M_AttributeSetInstance_ID", null);
		}
		else
		{
			setM_Product_ID(M_Product_ID);
			setM_AttributeSetInstance_ID(iLine.getM_AttributeSetInstance_ID());
			if (M_Locator_ID == 0)
				setM_Locator_ID(Qty);	// requires warehouse, product, asi
			else
				setM_Locator_ID(M_Locator_ID);
		}
		setC_Charge_ID(iLine.getC_Charge_ID());
		setDescription(iLine.getDescription());
		setIsDescription(iLine.isDescription());
		//
		setC_Project_ID(iLine.getC_Project_ID());
		setC_ProjectPhase_ID(iLine.getC_ProjectPhase_ID());
		setC_ProjectTask_ID(iLine.getC_ProjectTask_ID());
		setC_Activity_ID(iLine.getC_Activity_ID());
		setC_Campaign_ID(iLine.getC_Campaign_ID());
		setAD_OrgTrx_ID(iLine.getAD_OrgTrx_ID());
		setUser1_ID(iLine.getUser1_ID());
		setUser2_ID(iLine.getUser2_ID());
	}	// setInvoiceLine

	/**
	 * Get Warehouse
	 *
	 * @return Returns the m_Warehouse_ID.
	 */
	public int getM_Warehouse_ID()
	{
		if (m_M_Warehouse_ID == 0)
			m_M_Warehouse_ID = getParent().getM_Warehouse_ID();
		return m_M_Warehouse_ID;
	}	// getM_Warehouse_ID

	/**
	 * Set Warehouse
	 *
	 * @param warehouse_ID The m_Warehouse_ID to set.
	 */
	public void setM_Warehouse_ID(int warehouse_ID)
	{
		m_M_Warehouse_ID = warehouse_ID;
	}	// setM_Warehouse_ID

	/**
	 * Set M_Locator_ID
	 *
	 * @param M_Locator_ID id
	 */
	@Override
	public void setM_Locator_ID(int M_Locator_ID)
	{
		if (M_Locator_ID < 0)
			throw new IllegalArgumentException("M_Locator_ID is mandatory.");
		// set to 0 explicitly to reset
		set_Value(COLUMNNAME_M_Locator_ID, new Integer(M_Locator_ID));
	}	// setM_Locator_ID

	/**
	 * Set (default) Locator based on qty.
	 * 
	 * @param Qty quantity
	 *            Assumes Warehouse is set
	 */
	public void setM_Locator_ID(BigDecimal Qty)
	{
		// Locator established
		if (getM_Locator_ID() != 0)
			return;
		// No Product
		if (getM_Product_ID() == 0)
		{
			set_ValueNoCheck(COLUMNNAME_M_Locator_ID, null);
			return;
		}

		// Get existing Location
		int M_Locator_ID = MStorage.getM_Locator_ID(getM_Warehouse_ID(),
				getM_Product_ID(), getM_AttributeSetInstance_ID(),
				Qty, get_TrxName());
		// Get default Location
		if (M_Locator_ID == 0)
		{
			MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
		}
		setM_Locator_ID(M_Locator_ID);
	}	// setM_Locator_ID

	/**
	 * Set Movement/Movement Qty
	 *
	 * @param Qty Entered/Movement Qty
	 */
	public void setQty(BigDecimal Qty)
	{
		setQtyEntered(Qty);
		setMovementQty(getQtyEntered());
	}	// setQtyInvoiced

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
	 * Set Movement Qty - enforce Product UOM
	 *
	 * @param MovementQty
	 */
	@Override
	public void setMovementQty(BigDecimal MovementQty)
	{
		MProduct product = getProduct();
		if (MovementQty != null && product != null)
		{
			int precision = product.getUOMPrecision();
			MovementQty = MovementQty.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setMovementQty(MovementQty);
	}	// setMovementQty

	/**
	 * Get Product
	 *
	 * @return product or null
	 */
	public MProduct getProduct()
	{
		if (m_product == null && getM_Product_ID() != 0)
			m_product = MProduct.get(getCtx(), getM_Product_ID());
		return m_product;
	}	// getProduct

	/**
	 * Set Product
	 *
	 * @param product product
	 */
	public void setProduct(MProduct product)
	{
		m_product = product;
		if (m_product != null)
		{
			setM_Product_ID(m_product.getM_Product_ID());
			setC_UOM_ID(m_product.getC_UOM_ID());
		}
		else
		{
			setM_Product_ID(0);
			setC_UOM_ID(0);
		}
		setM_AttributeSetInstance_ID(0);
	}	// setProduct

	/**
	 * Set M_Product_ID
	 *
	 * @param M_Product_ID product
	 * @param setUOM also set UOM from product
	 */
	public void setM_Product_ID(int M_Product_ID, boolean setUOM)
	{
		if (setUOM)
			setProduct(MProduct.get(getCtx(), M_Product_ID));
		else
			super.setM_Product_ID(M_Product_ID);
		setM_AttributeSetInstance_ID(0);
	}	// setM_Product_ID

	/**
	 * Set Product and UOM
	 *
	 * @param M_Product_ID product
	 * @param C_UOM_ID uom
	 */
	public void setM_Product_ID(int M_Product_ID, int C_UOM_ID)
	{
		if (M_Product_ID != 0)
			super.setM_Product_ID(M_Product_ID);
		super.setC_UOM_ID(C_UOM_ID);
		setM_AttributeSetInstance_ID(0);
		m_product = null;
	}	// setM_Product_ID

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	// addDescription

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
			ii = getParent().getC_Project_ID();
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
			ii = getParent().getC_Activity_ID();
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
			ii = getParent().getC_Campaign_ID();
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
			ii = getParent().getUser1_ID();
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
			ii = getParent().getUser2_ID();
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
			ii = getParent().getAD_OrgTrx_ID();
		return ii;
	}	// getAD_OrgTrx_ID

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord new
	 * @return save
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		log.debug("");
		if (newRecord && getParent().isComplete())
		{
			throw new AdempiereException("@ParentComplete@ @M_InOutLine_ID@");
		}
		// Locator is mandatory if no charge is defined - teo_sarca BF [ 2757978 ]
		if (getProduct() != null && MProduct.PRODUCTTYPE_Item.equals(getProduct().getProductType()))
		{
			if (getM_Locator_ID() <= 0 && getC_Charge_ID() <= 0)
			{
				throw new FillMandatoryException(COLUMNNAME_M_Locator_ID);
			}
		}

		// Receipt should not allow lines with Qty=0 - teo_sarca [ 2797938 ]
		//
		// 08278: Receipt shall allow lines with Qty=0 (i.e corrected SHIPMENTS after Auswahl Lieferung)
		//
		// if (!isDescription()
		// && getMovementQty().signum() == 0
		// && getPickedQty().signum() == 0
		// && getScrappedQty().signum() == 0)
		// {
		// throw new FillMandatoryException(COLUMNNAME_MovementQty);
		// }

		// Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM M_InOutLine WHERE M_InOut_ID=?";
			int ii = DB.getSQLValueEx(get_TrxName(), sql, getM_InOut_ID());
			setLine(ii);
		}
		// UOM
		if (getC_UOM_ID() == 0)
			setC_UOM_ID(Env.getContextAsInt(getCtx(), "#C_UOM_ID"));
		if (getC_UOM_ID() == 0)
		{
			int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
			if (C_UOM_ID > 0)
				setC_UOM_ID(C_UOM_ID);
		}
		// Qty Precision
		if (newRecord || is_ValueChanged("QtyEntered"))
			setQtyEntered(getQtyEntered());
		if (newRecord || is_ValueChanged("MovementQty"))
			setMovementQty(getMovementQty());
		//
		// // Order/RMA Line
		// if (getC_OrderLine_ID() == 0 && getM_RMALine_ID() == 0)
		// {
		// if (getParent().isSOTrx())
		// {
		// log.error("FillMandatory", Msg.translate(getCtx(), "C_Order_ID"));
		// return false;
		// }
		// }

		// Validate Locator/Warehouse - teo_sarca, BF [ 2784194 ]
		if (getM_Locator_ID() > 0)
		{
			MLocator locator = MLocator.get(getCtx(), getM_Locator_ID());
			if (getM_Warehouse_ID() != locator.getM_Warehouse_ID())
			{
				throw new WarehouseLocatorConflictException(
						MWarehouse.get(getCtx(), getM_Warehouse_ID()),
						locator,
						getLine());
			}
		}

		// if (getC_Charge_ID() == 0 && getM_Product_ID() == 0)
		// ;

		/**
		 * Qty on instance ASI
		 * if (getM_AttributeSetInstance_ID() != 0)
		 * {
		 * MProduct product = getProduct();
		 * int M_AttributeSet_ID = product.getM_AttributeSet_ID();
		 * boolean isInstance = M_AttributeSet_ID != 0;
		 * if (isInstance)
		 * {
		 * MAttributeSet mas = MAttributeSet.get(getCtx(), M_AttributeSet_ID);
		 * isInstance = mas.isInstanceAttribute();
		 * }
		 * // Max
		 * if (isInstance)
		 * {
		 * MStorage storage = MStorage.get(getCtx(), getM_Locator_ID(),
		 * getM_Product_ID(), getM_AttributeSetInstance_ID(), get_TrxName());
		 * if (storage != null)
		 * {
		 * BigDecimal qty = storage.getQtyOnHand();
		 * if (getMovementQty().compareTo(qty) > 0)
		 * {
		 * log.warn("Qty - Stock=" + qty + ", Movement=" + getMovementQty());
		 * log.error("QtyInsufficient", "=" + qty);
		 * return false;
		 * }
		 * }
		 * }
		 * } /
		 **/

		return true;
	}	// beforeSave

	/**
	 * Before Delete
	 *
	 * @return true if drafted
	 */
	@Override
	protected boolean beforeDelete()
	{
		final MInOut parent = getParent();
		if (Services.get(IDocumentBL.class).isDocumentStatusOneOf(parent, IDocument.STATUS_Drafted, IDocument.STATUS_InProgress))
		{
			return true;
		}
		throw new AdempiereException("@CannotDelete@");
	}	// beforeDelete

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MInOutLine[").append(get_ID())
				.append(",M_Product_ID=").append(getM_Product_ID())
				.append(",QtyEntered=").append(getQtyEntered())
				.append(",MovementQty=").append(getMovementQty())
				.append(",M_AttributeSetInstance_ID=").append(getM_AttributeSetInstance_ID())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Get Base value for Cost Distribution
	 *
	 * @param CostDistribution cost Distribution
	 * @return base number
	 */
	public BigDecimal getBase(String CostDistribution)
	{
		if (MLandedCost.LANDEDCOSTDISTRIBUTION_Costs.equals(CostDistribution))
		{
			MInvoiceLine m_il = MInvoiceLine.getOfInOutLine(this);
			if (m_il == null)
			{
				log.error("No Invoice Line for: " + this.toString());
				return Env.ZERO;
			}
			return m_il.getLineNetAmt();
		}
		else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Line.equals(CostDistribution))
			return Env.ONE;
		else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Quantity.equals(CostDistribution))
			return getMovementQty();
		else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Volume.equals(CostDistribution))
		{
			MProduct product = getProduct();
			if (product == null)
			{
				log.error("No Product");
				return Env.ZERO;
			}
			return getMovementQty().multiply(product.getVolume());
		}
		else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Weight.equals(CostDistribution))
		{
			MProduct product = getProduct();
			if (product == null)
			{
				log.error("No Product");
				return Env.ZERO;
			}
			return getMovementQty().multiply(product.getWeight());
		}
		//
		log.error("Invalid Criteria: " + CostDistribution);
		return Env.ZERO;
	}	// getBase

	public boolean sameOrderLineUOM()
	{
		if (getC_OrderLine_ID() <= 0)
			return false;

		final I_C_OrderLine oLine = getC_OrderLine();

		if (oLine.getC_UOM_ID() != getC_UOM_ID())
			return false;

		// inout has orderline and both has the same UOM
		return true;
	}

}	// MInOutLine
