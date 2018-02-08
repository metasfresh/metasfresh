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

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.DB;

import de.metas.product.IProductBL;

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
	public static MProduct get(final Properties ctx_NOTUSED, final int M_Product_ID)
	{
		if (M_Product_ID <= 0)
		{
			return null;
		}

		// NOTE: we rely on table cache config
		final I_M_Product product = loadOutOfTrx(M_Product_ID, I_M_Product.class);
		return LegacyAdapters.convertToPO(product);
	}	// get

	public MProduct(Properties ctx, int M_Product_ID, String trxName)
	{
		super(ctx, M_Product_ID, trxName);
		if (is_new())
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

	public MProduct(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MProduct

	/**
	 * Import Constructor
	 *
	 * @param impP import
	 */
	public MProduct(I_I_Product impP)
	{
		this(InterfaceWrapperHelper.getCtx(impP),
				0,
				InterfaceWrapperHelper.getTrxName(impP));
		setClientOrg(InterfaceWrapperHelper.getPO(impP));
		setUpdatedBy(impP.getUpdatedBy());
		//
		setValue(impP.getValue());
		setName(impP.getName());
		setDescription(impP.getDescription());
		setDocumentNote(impP.getDocumentNote());
		setHelp(impP.getHelp());
		setUPC(impP.getUPC());
		setSKU(impP.getSKU());
		setC_UOM_ID(impP.getC_UOM_ID());
		setPackage_UOM_ID(impP.getPackage_UOM_ID());
		setPackageSize(impP.getPackageSize());
		setManufacturer(impP.getProductManufacturer());
		setM_Product_Category_ID(impP.getM_Product_Category_ID());
		setProductType(impP.getProductType());
		setImageURL(impP.getImageURL());
		setDescriptionURL(impP.getDescriptionURL());
		setIsSold(impP.isSold());
		setIsStocked(impP.isStocked());
		setM_ProductPlanningSchema_Selector(impP.getM_ProductPlanningSchema_Selector()); // #3406
	}	// MProduct


	/** UOM Precision */
	private Integer m_precision = null;

	/**
	 * Get UOM Standard Precision
	 *
	 * @return UOM Standard Precision
	 * @deprecated Please use {@link IProductBL#getUOMPrecision(I_M_Product)}
	 */
	@Deprecated
	public int getUOMPrecision()
	{
		if (m_precision == null)
		{
			int C_UOM_ID = getC_UOM_ID();
			if (C_UOM_ID == 0)
			 {
				return 0;	// EA
			}
			m_precision = MUOM.getPrecision(getCtx(), C_UOM_ID);
		}
		return m_precision.intValue();
	}	// getUOMPrecision

	/**
	 * Create Asset for this product
	 *
	 * @return true if asset is created
	 */
	public boolean isCreateAsset()
	{
		MProductCategory pc = MProductCategory.get(getCtx(), getM_Product_Category_ID());
		return pc.getA_Asset_Group_ID() != 0;
	}	// isCreated

	/**
	 * Create One Asset Per UOM
	 *
	 * @return individual asset
	 */
	public boolean isOneAssetPerUOM()
	{
		MProductCategory pc = MProductCategory.get(getCtx(), getM_Product_Category_ID());
		if (pc.getA_Asset_Group_ID() == 0)
		{
			return false;
		}
		MAssetGroup ag = MAssetGroup.get(getCtx(), pc.getA_Asset_Group_ID());
		return ag.isOneAssetPerUOM();
	}	// isOneAssetPerUOM

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// it checks if UOM has been changed , if so disallow the change if the condition is true.
		if ((!newRecord) && is_ValueChanged(COLUMNNAME_C_UOM_ID) && hasInventoryTrxs())
		{
			throw new AdempiereException("@SaveUomError@");
		}

		// Reset Stocked if not Item
		// AZ Goodwill: Bug Fix isStocked always return false
		// if (isStocked() && !PRODUCTTYPE_Item.equals(getProductType()))
		if (!PRODUCTTYPE_Item.equals(getProductType()))
		{
			setIsStocked(false);
		}

		// UOM reset
		if (m_precision != null && is_ValueChanged(COLUMNNAME_C_UOM_ID))
		{
			m_precision = null;
		}

		return true;
	}	// beforeSave
	
	private boolean hasInventoryTrxs()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Transaction.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Transaction.COLUMN_M_Product_ID, getM_Product_ID())
				.create()
				.match();
	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!success)
		{
			return success;
		}

		// Value/Name change in Account
		if (!newRecord && (is_ValueChanged(COLUMNNAME_Value) || is_ValueChanged(COLUMNNAME_Name)))
		{
			MAccount.updateValueDescription(getCtx(), "M_Product_ID=" + getM_Product_ID(), get_TrxName());
		}

		// Name/Description Change in Asset MAsset.setValueNameDescription
		if (!newRecord && (is_ValueChanged(COLUMNNAME_Name) || is_ValueChanged(COLUMNNAME_Description)))
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

		// New - Acct, Tree
		if (newRecord)
		{
			insert_Accounting(I_M_Product_Acct.Table_Name,
					I_M_Product_Category_Acct.Table_Name,
					"p.M_Product_Category_ID=" + getM_Product_Category_ID());
			
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
		return delete_Accounting(I_M_Product_Acct.Table_Name);
	}	// beforeDelete
}	// MProduct
