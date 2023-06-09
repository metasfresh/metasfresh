/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package org.compiere.model;

import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.LegacyAdapters;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product Model
 *
 * @author Jorg Janke
 * @version $Id: MProduct.java,v 1.5 2006/07/30 00:51:05 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>FR [ 1885153 ] Refactor: getMMPolicy code <li>BF [ 1885414 ] ASI should be always mandatory if CostingLevel is Batch/Lot <li>FR [ 2093551 ]
 *         Refactor/Add org.compiere.model.MProduct.getCostingLevel <li>FR [ 2093569 ] Refactor/Add org.compiere.model.MProduct.getCostingMethod <li>BF [ 2824795 ] Deleting Resource product should be
 *         forbidden https://sourceforge.net/tracker/?func=detail&aid=2824795&group_id=176962&atid=879332
 *
 * @author Mark Ostermann (mark_o), metas consult GmbH <li>BF [ 2814628 ] Wrong evaluation of Product inactive in beforeSave()
 */
public class MProduct extends X_M_Product
{
	private static final long serialVersionUID = 285926961771269935L;

	@Deprecated
	public static MProduct get(Properties ctx_IGNORED, int M_Product_ID)
	{
		if (M_Product_ID <= 0)
		{
			return null;
		}

		// NOTE: we rely on table cache config
		final I_M_Product product = Services.get(IProductDAO.class).getById(M_Product_ID);
		return LegacyAdapters.convertToPO(product);
	}	// get

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param M_Product_ID id
	 * @param trxName transaction
	 */
	public MProduct(Properties ctx, int M_Product_ID, String trxName)
	{
		super(ctx, M_Product_ID, trxName);
		if (M_Product_ID == 0)
		{
			// setValue (null);
			// setName (null);
			// setM_Product_Category_ID (0);
			// setC_UOM_ID (0);
			//
			setProductType(PRODUCTTYPE_Item);	// I
			setIsBOM(false);	// N
			setIsInvoicePrintDetails(false);
			setIsPickListPrintDetails(false);
			setIsPurchased(true);	// Y
			setIsSold(true);	// Y
			setIsStocked(true);	// Y
			setIsSummary(false);
			setIsVerified(false);	// N
			setIsWebStoreFeatured(false);
			setIsSelfService(true);
			setIsExcludeAutoDelivery(false);
			setProcessing(false);	// N
			setLowLevel(0);
		}
	}	// MProduct

	/**
	 * Load constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MProduct(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MProduct

	/**
	 * Parent Constructor
	 *
	 * @param et parent
	 */
	MProduct(MExpenseType et)
	{
		this(et.getCtx(), 0, et.get_TrxName());
		setProductType(X_M_Product.PRODUCTTYPE_ExpenseType);
		setExpenseType(et);
	}	// MProduct

//	/**
//	 * Parent Constructor
//	 *
//	 * @param resource parent
//	 * @param resourceType resource type
//	 */
//	public MProduct(MResource resource, MResourceType resourceType)
//	{
//		this(resource.getCtx(), 0, resource.get_TrxName());
//		setAD_Org_ID(resource.getAD_Org_ID());
//		setProductType(X_M_Product.PRODUCTTYPE_Resource);
//		setResource(resource);
//		setResource(resourceType);
//	}	// MProduct


	/**
	 * Set Expense Type
	 *
	 * @param parent expense type
	 * @return true if changed
	 */
	boolean setExpenseType(MExpenseType parent)
	{
		boolean changed = false;
		if (!PRODUCTTYPE_ExpenseType.equals(getProductType()))
		{
			setProductType(PRODUCTTYPE_ExpenseType);
			changed = true;
		}
		if (parent.getS_ExpenseType_ID() != getS_ExpenseType_ID())
		{
			setS_ExpenseType_ID(parent.getS_ExpenseType_ID());
			changed = true;
		}
		if (parent.isActive() != isActive())
		{
			setIsActive(parent.isActive());
			changed = true;
		}
		//
		if (!parent.getValue().equals(getValue()))
		{
			setValue(parent.getValue());
			changed = true;
		}
		if (!parent.getName().equals(getName()))
		{
			setName(parent.getName());
			changed = true;
		}
		if ((parent.getDescription() == null && getDescription() != null)
				|| (parent.getDescription() != null && !parent.getDescription().equals(getDescription())))
		{
			setDescription(parent.getDescription());
			changed = true;
		}
		if (parent.getC_UOM_ID() != getC_UOM_ID())
		{
			setC_UOM_ID(parent.getC_UOM_ID());
			changed = true;
		}
		if (parent.getM_Product_Category_ID() != getM_Product_Category_ID())
		{
			setM_Product_Category_ID(parent.getM_Product_Category_ID());
			changed = true;
		}

		// metas 05129 end
		return changed;
	}	// setExpenseType

	/** UOM Precision */
	private UOMPrecision m_precision = null;

	/**
	 * Get UOM Standard Precision
	 *
	 * @return UOM Standard Precision
	 * @deprecated Please use {@link IProductBL#getUOMPrecision(I_M_Product)}
	 */
	@Deprecated
	public int getUOMPrecision()
	{
		UOMPrecision precision = m_precision;
		if (precision == null)
		{
			final UomId uomId = UomId.ofRepoIdOrNull(getC_UOM_ID());
			if (uomId == null)
			{
				precision = UOMPrecision.ZERO;	// EA
				// NOTE: don't cache the precision (i.e. don't set m_precision)
			}
			else
			{
				precision = m_precision = Services.get(IUOMDAO.class).getStandardPrecision(uomId);
			}
		}
		return precision.toInt();
	}	// getUOMPrecision

	/**
	 * Create Asset for this product
	 *
	 * @return true if asset is created
	 */
	public boolean isCreateAsset()
	{
		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(getM_Product_Category_ID());
		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final I_M_Product_Category pc = productDAO.getProductCategoryById(productCategoryId);
		return pc.getA_Asset_Group_ID() > 0;
	}	// isCreated

	/**
	 * Create One Asset Per UOM
	 *
	 * @return individual asset
	 */
	public boolean isOneAssetPerUOM()
	{
		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(getM_Product_Category_ID());
		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final I_M_Product_Category pc = productDAO.getProductCategoryById(productCategoryId);
		if (pc.getA_Asset_Group_ID() <= 0)
		{
			return false;
		}
		MAssetGroup ag = MAssetGroup.get(getCtx(), pc.getA_Asset_Group_ID());
		return ag.isOneAssetPerUOM();
	}	// isOneAssetPerUOM

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MProduct[");
		sb.append(get_ID()).append("-").append(getValue()).append("_").append(getName())
				.append("]");
		return sb.toString();
	}	// toString

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// Reset Stocked if not Item
		// AZ Goodwill: Bug Fix isStocked always return false
		// if (isStocked() && !PRODUCTTYPE_Item.equals(getProductType()))
		if (!PRODUCTTYPE_Item.equals(getProductType()))
		{
			setIsStocked(false);
		}

		// UOM reset
		if (m_precision != null && is_ValueChanged("C_UOM_ID"))
		{
			m_precision = null;
		}

		return true;
	}	// beforeSave

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
		{
			return success;
		}

		// Value/Name change in Account
		if (!newRecord && (is_ValueChanged("Value") || is_ValueChanged("Name")))
		{
			MAccount.updateValueDescription(getCtx(), "M_Product_ID=" + getM_Product_ID(), get_TrxName());
		}

		// Name/Description Change in Asset MAsset.setValueNameDescription
		if (!newRecord && (is_ValueChanged("Name") || is_ValueChanged("Description")))
		{
			String sql = DB.convertSqlToNative("UPDATE A_Asset a "
					+ "SET (Name, Description)="
					+ "(SELECT SUBSTR((SELECT bp.Name FROM C_BPartner bp WHERE bp.C_BPartner_ID=a.C_BPartner_ID) || ' - ' || p.Name,1,60), p.Description "
					+ "FROM M_Product p "
					+ "WHERE p.M_Product_ID=a.M_Product_ID) "
					+ "WHERE IsActive='Y'"
					// + " AND GuaranteeDate > now()"
					+ "  AND M_Product_ID=" + getM_Product_ID());
			int no = DB.executeUpdate(sql, get_TrxName());
			log.debug("Asset Description updated #" + no);
		}

		// New - Acct, Tree, Old Costing
		if (newRecord)
		{
			if(!this.isCopying())
			{
				insert_Accounting(I_M_Product_Acct.Table_Name,
								  I_M_Product_Category_Acct.Table_Name,
								  "p.M_Product_Category_ID=" + getM_Product_Category_ID());
			}
			else
			{
				log.info("This M_Product is created via CopyRecordSupport; -> don't insert the default _acct records");
			}
			insert_Tree(X_AD_Tree.TREETYPE_Product);
		}
		
		return true;
	}	// afterSave
	

	@Override
	protected boolean beforeDelete()
	{
		if (PRODUCTTYPE_Resource.equals(getProductType()) && getS_Resource_ID() > 0)
		{
			throw new AdempiereException("@S_Resource_ID@<>0");
		}

		//
		return delete_Accounting("M_Product_Acct");
	}	// beforeDelete
}
