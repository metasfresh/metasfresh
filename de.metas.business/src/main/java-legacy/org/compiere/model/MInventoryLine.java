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
import org.compiere.util.DB;

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
	/**
	 *
	 */
	private static final long serialVersionUID = 1336000922103246463L;

	/**
	 * Get Inventory Line with parameters
	 *
	 * @param inventory inventory
	 * @param M_Locator_ID locator
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID asi
	 * @return line or null
	 */
	public static MInventoryLine get(MInventory inventory,
			int M_Locator_ID, int M_Product_ID, int M_AttributeSetInstance_ID)
	{
		final String whereClause = "M_Inventory_ID=? AND M_Locator_ID=?"
				+ " AND M_Product_ID=? AND M_AttributeSetInstance_ID=?";
		return new Query(inventory.getCtx(), MInventoryLine.Table_Name, whereClause, inventory.get_TrxName())
				.setParameters(new Object[] { inventory.get_ID(), M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID })
				.firstOnly();
	}	// get

	/**************************************************************************
	 * Default Constructor
	 *
	 * @param ctx context
	 * @param M_InventoryLine_ID line
	 * @param trxName transaction
	 */
	public MInventoryLine(Properties ctx, int M_InventoryLine_ID, String trxName)
	{
		super(ctx, M_InventoryLine_ID, trxName);
		if (M_InventoryLine_ID == 0)
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
	}	// MInventoryLine

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MInventoryLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MInventoryLine

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
		if (inventory.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		m_parent = inventory;
		setM_Inventory_ID(inventory.getM_Inventory_ID());		// Parent
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

	/** Manually created */
	private boolean m_isManualEntry = true;
	/** Parent */
	private MInventory m_parent = null;
	/** Product */
	private MProduct m_product = null;

	/**
	 * Get Product
	 *
	 * @return product or null if not defined
	 */
	public MProduct getProduct()
	{
		int M_Product_ID = getM_Product_ID();
		if (M_Product_ID == 0)
			return null;
		if (m_product != null && m_product.getM_Product_ID() != M_Product_ID)
			m_product = null;	// reset
		if (m_product == null)
			m_product = MProduct.get(getCtx(), M_Product_ID);
		return m_product;
	}	// getProduct

	/**
	 * Set Count Qty - enforce UOM
	 *
	 * @param QtyCount qty
	 */
	@Override
	public void setQtyCount(BigDecimal QtyCount)
	{
		if (QtyCount != null)
		{
			MProduct product = getProduct();
			if (product != null)
			{
				int precision = product.getUOMPrecision();
				QtyCount = QtyCount.setScale(precision, BigDecimal.ROUND_HALF_UP);
			}
		}
		super.setQtyCount(QtyCount);
	}	// setQtyCount

	/**
	 * Set Internal Use Qty - enforce UOM
	 *
	 * @param QtyInternalUse qty
	 */
	@Override
	public void setQtyInternalUse(BigDecimal QtyInternalUse)
	{
		if (QtyInternalUse != null)
		{
			MProduct product = getProduct();
			if (product != null)
			{
				int precision = product.getUOMPrecision();
				QtyInternalUse = QtyInternalUse.setScale(precision, BigDecimal.ROUND_HALF_UP);
			}
		}
		super.setQtyInternalUse(QtyInternalUse);
	}	// setQtyInternalUse

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
	 * Get Parent
	 *
	 * @param parent parent
	 */
	protected void setParent(MInventory parent)
	{
		m_parent = parent;
	}	// setParent

	/**
	 * Get Parent
	 *
	 * @return parent
	 */
	public MInventory getParent()
	{
		if (m_parent == null)
			m_parent = new MInventory(getCtx(), getM_Inventory_ID(), get_TrxName());
		return m_parent;
	}	// getParent

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MInventoryLine[");
		sb.append(get_ID())
				.append("-M_Product_ID=").append(getM_Product_ID())
				.append(",QtyCount=").append(getQtyCount())
				.append(",QtyInternalUse=").append(getQtyInternalUse())
				.append(",QtyBook=").append(getQtyBook())
				.append(",M_AttributeSetInstance_ID=").append(getM_AttributeSetInstance_ID())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true if can be saved
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (newRecord && getParent().isComplete())
		{
			throw new AdempiereException("@ParentComplete@ @M_Inventory_ID@");
		}
		if (newRecord && m_isManualEntry)
		{
			// Product requires ASI
			if (getM_AttributeSetInstance_ID() == 0)
			{
				final MProduct product = MProduct.get(getCtx(), getM_Product_ID());
				if (product != null && product.isASIMandatory(isSOTrx()))
				{
					throw new FillMandatoryException(COLUMNNAME_M_AttributeSetInstance_ID);
				}
			}	// No ASI
		}	// new or manual

		// Set Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_InventoryLine WHERE M_Inventory_ID=?";
			int ii = DB.getSQLValueEx(get_TrxName(), sql, getM_Inventory_ID());
			setLine(ii);
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
		if (getQtyInternalUse().signum() != 0)
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
		else if (getC_Charge_ID() != 0)
		{
			setC_Charge_ID(0);
		}

		// Set AD_Org to parent if not charge
		if (getC_Charge_ID() == 0)
		{
			setAD_Org_ID(getParent().getAD_Org_ID());
		}

		return true;
	}	// beforeSave

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success success
	 * @return true
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
		{
			return false;
		}

		// Create MA
		// if (newRecord && success
		// && m_isManualEntry && getM_AttributeSetInstance_ID() == 0)
		// createMA();
		return true;
	}	// afterSave

	/**
	 * Create Material Allocations for new Instances
	 */
	private void createMA()
	{
		MStorage[] storages = MStorage.getAll(getCtx(), getM_Product_ID(), getM_Locator_ID(), get_TrxName());

		boolean allZeroASI = true;
		for (int i = 0; i < storages.length; i++)
		{
			if (storages[i].getM_AttributeSetInstance_ID() != 0)
			{
				allZeroASI = false;
				break;
			}
		}
		if (allZeroASI)
			return;

		MInventoryLineMA ma = null;
		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i < storages.length; i++)
		{
			MStorage storage = storages[i];
			if (storage.getQtyOnHand().signum() == 0)
				continue;
			if (ma != null
					&& ma.getM_AttributeSetInstance_ID() == storage.getM_AttributeSetInstance_ID())
			{
				ma.setMovementQty(ma.getMovementQty().add(storage.getQtyOnHand()));
			}
			else
			{
				ma = new MInventoryLineMA(this,
						0, // storage.getM_AttributeSetInstance_ID(),
						storage.getQtyOnHand());
			}
			if (!ma.save())
				;
			sum = sum.add(storage.getQtyOnHand());
		}
		if (sum.compareTo(getQtyBook()) != 0)
		{
			log.warn("QtyBook=" + getQtyBook() + " corrected to Sum of MA=" + sum);
			setQtyBook(sum);
		}
	}	// createMA

	/**
	 * Is Internal Use Inventory
	 *
	 * @return true if is internal use inventory
	 */
	public boolean isInternalUseInventory()
	{
		/*
		 * TODO: need to add M_Inventory.IsInternalUseInventory flag
		 * see FR [ 1879029 ] Added IsInternalUseInventory flag to M_Inventory table
		 * MInventory parent = getParent();
		 * return parent != null && parent.isInternalUseInventory();
		 */
		return getQtyInternalUse().signum() != 0;
	}

	/**
	 * Get Movement Qty (absolute value)
	 * <li>negative value means outgoing trx
	 * <li>positive value means incoming trx
	 *
	 * @return movement qty
	 */
	public BigDecimal getMovementQty()
	{
		if (isInternalUseInventory())
		{
			return getQtyInternalUse().negate();
		}
		else
		{
			return getQtyCount().subtract(getQtyBook());
		}
	}

	/**
	 * @return true if is an outgoing transaction
	 */
	public boolean isSOTrx()
	{
		return getMovementQty().signum() < 0;
	}
}	// MInventoryLine
