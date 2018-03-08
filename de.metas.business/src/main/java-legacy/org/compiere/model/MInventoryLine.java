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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Services;
import org.compiere.util.DB;

import de.metas.inventory.IInventoryBL;
import de.metas.product.IProductBL;

/**
 * Physical Inventory Line Model
 *
 * @author Jorg Janke
 * @version $Id: MInventoryLine.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1817757 ] Error on saving MInventoryLine in a custom environment
 *         <li>BF [ 1722982 ] Error with inventory when you enter count qty in negative
 */
public class MInventoryLine extends X_M_InventoryLine
{
	private static final long serialVersionUID = 1336000922103246463L;

	/** Manually created */
	private boolean m_isManualEntry = true;

	public MInventoryLine(Properties ctx, int M_InventoryLine_ID, String trxName)
	{
		super(ctx, M_InventoryLine_ID, trxName);
		if (is_new())
		{
			// setM_Inventory_ID (0); // Parent
			// setM_InventoryLine_ID (0); // PK
			// setM_Locator_ID (0); // FK
			setLine(0);
			// setM_Product_ID (0); // FK
			setM_AttributeSetInstance_ID(0);	// FK
			setInventoryType(INVENTORYTYPE_InventoryDifference);
			setQtyBook(BigDecimal.ZERO);
			setQtyCount(BigDecimal.ZERO);
			setProcessed(false);
		}
	}

	public MInventoryLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Detail Constructor.
	 * Locator/Product/AttributeSetInstance must be unique
	 *
	 * @param inventory parent
	 * @param M_Locator_ID locator
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID instance
	 * @param QtyBook book value
	 * @param QtyCount count value
	 */
	public MInventoryLine(MInventory inventory,
			int M_Locator_ID, int M_Product_ID, int M_AttributeSetInstance_ID,
			BigDecimal QtyBook, BigDecimal QtyCount)
	{
		this(inventory.getCtx(), 0, inventory.get_TrxName());
		if (inventory.getM_Inventory_ID() <= 0)
			throw new IllegalArgumentException("Header not saved");
		setM_Inventory(inventory);
		setClientOrg(inventory.getAD_Client_ID(), inventory.getAD_Org_ID());
		setM_Locator_ID(M_Locator_ID);		// FK
		setM_Product_ID(M_Product_ID);		// FK
		setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		//
		if (QtyBook != null)
			setQtyBook(QtyBook);
		if (QtyCount != null && QtyCount.signum() != 0)
			setQtyCount(QtyCount);
		m_isManualEntry = false;
	}	// MInventoryLine

	private final BigDecimal adjustQtyToUOMPrecision(final BigDecimal qty)
	{
		if(qty == null)
		{
			return null;
		}
		
		final int productId = getM_Product_ID();
		if(productId <= 0)
		{
			return qty;
		}
			
		final int precision = Services.get(IProductBL.class).getUOMPrecision(productId);
		return qty.setScale(precision, BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * Set Count Qty - enforce UOM
	 *
	 * @param QtyCount qty
	 */
	@Override
	public void setQtyCount(final BigDecimal QtyCount)
	{
		super.setQtyCount(adjustQtyToUOMPrecision(QtyCount));
	}	// setQtyCount

	/**
	 * Set Internal Use Qty - enforce UOM
	 *
	 * @param QtyInternalUse qty
	 */
	@Override
	public void setQtyInternalUse(final BigDecimal QtyInternalUse)
	{
		super.setQtyInternalUse(adjustQtyToUOMPrecision(QtyInternalUse));
	}	// setQtyInternalUse

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MInventoryLine[");
		sb.append(get_ID())
				.append("-M_Product_ID=").append(getM_Product_ID())
				.append(",QtyCount=").append(getQtyCount())
				.append(",QtyInternalUse=").append(getQtyInternalUse())
				.append(",QtyBook=").append(getQtyBook())
				.append(",M_AttributeSetInstance_ID=").append(getM_AttributeSetInstance_ID())
				.append("]");
		return sb.toString();
	}	// toString

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (newRecord && Services.get(IInventoryBL.class).isComplete(getM_Inventory()))
		{
			throw new AdempiereException("@ParentComplete@ @M_Inventory_ID@");
		}
		
		if (newRecord && m_isManualEntry)
		{
			// Product requires ASI
			if (getM_AttributeSetInstance_ID() <= 0)
			{
				final MProduct product = MProduct.get(getCtx(), getM_Product_ID());
				if (product != null && product.isASIMandatory(isSOTrx()))
				{
					throw new FillMandatoryException(COLUMNNAME_M_AttributeSetInstance_ID);
				}
			}	// No ASI
		}	// new or manual

		// Set Line No
		if (getLine() <= 0)
		{
			final String sql = "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_InventoryLine WHERE M_Inventory_ID=?";
			final int lineNo = DB.getSQLValueEx(get_TrxName(), sql, getM_Inventory_ID());
			setLine(lineNo);
		}

		// Enforce QtyCount >= 0 - teo_sarca BF [ 1722982 ]
		if (getQtyCount().signum() < 0)
		{
			throw new AdempiereException("@Warning@ @" + COLUMNNAME_QtyCount + "@ < 0");
		}
		// Enforce Qty UOM
		if (newRecord || is_ValueChanged(COLUMNNAME_QtyCount))
		{
			setQtyCount(getQtyCount());
		}
		if (newRecord || is_ValueChanged(COLUMNNAME_QtyInternalUse))
		{
			setQtyInternalUse(getQtyInternalUse());
		}

		// InternalUse Inventory
		if(isInternalUseInventory())
		{
			if (!INVENTORYTYPE_ChargeAccount.equals(getInventoryType()))
			{
				setInventoryType(INVENTORYTYPE_ChargeAccount);
			}
			//
			if (getC_Charge_ID() <= 0)
			{
				throw new AdempiereException("@InternalUseNeedsCharge@");
			}
		}
		else if (INVENTORYTYPE_ChargeAccount.equals(getInventoryType()))
		{
			if (getC_Charge_ID() <= 0)
			{
				throw new FillMandatoryException(COLUMNNAME_C_Charge_ID);
			}
		}
		else if (getC_Charge_ID() > 0)
		{
			setC_Charge_ID(0);
		}

		// Set AD_Org to parent if not charge
		if (getC_Charge_ID() <= 0)
		{
			setAD_Org_ID(getM_Inventory().getAD_Org_ID());
		}

		return true;
	}

	@Deprecated
	private boolean isInternalUseInventory()
	{
		return Services.get(IInventoryBL.class).isInternalUseInventory(this);
	}

	/**
	 * @return true if is an outgoing transaction
	 */
	@Deprecated
	private boolean isSOTrx()
	{
		return Services.get(IInventoryBL.class).isSOTrx(this);
	}
}
