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

import de.metas.common.util.CoalesceUtil;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductType;
import de.metas.product.ResourceId;
import de.metas.resource.ResourceGroupId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.LegacyAdapters;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>FR [ 1885153 ] Refactor: getMMPolicy code <li>BF [ 1885414 ] ASI should be always mandatory if CostingLevel is Batch/Lot <li>FR [ 2093551 ]
 * Refactor/Add org.compiere.model.MProduct.getCostingLevel <li>FR [ 2093569 ] Refactor/Add org.compiere.model.MProduct.getCostingMethod <li>BF [ 2824795 ] Deleting Resource product should be
 * forbidden https://sourceforge.net/tracker/?func=detail&aid=2824795&group_id=176962&atid=879332
 * @author Mark Ostermann (mark_o), metas consult GmbH <li>BF [ 2814628 ] Wrong evaluation of Product inactive in beforeSave()
 * @version $Id: MProduct.java,v 1.5 2006/07/30 00:51:05 jjanke Exp $
 */
public class MProduct extends X_M_Product
{
	private static final long serialVersionUID = 285926961771269935L;

	@Deprecated
	@Nullable
	public static MProduct get(final Properties ctx_IGNORED, final int M_Product_ID)
	{
		if (M_Product_ID <= 0)
		{
			return null;
		}

		// NOTE: we rely on table cache config
		final I_M_Product product = Services.get(IProductDAO.class).getById(M_Product_ID);
		return LegacyAdapters.convertToPO(product);
	}    // get

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param M_Product_ID id
	 * @param trxName transaction
	 */
	public MProduct(final Properties ctx, final int M_Product_ID, final String trxName)
	{
		super(ctx, M_Product_ID, trxName);
		if (is_new())
		{
			// setValue (null);
			// setName (null);
			// setM_Product_Category_ID (0);
			// setC_UOM_ID (0);
			//
			setProductType(PRODUCTTYPE_Item);    // I
			setIsBOM(false);    // N
			setIsInvoicePrintDetails(false);
			setIsPickListPrintDetails(false);
			setIsPurchased(true);    // Y
			setIsSold(true);    // Y
			setIsStocked(true);    // Y
			setIsSummary(false);
			setIsVerified(false);    // N
			setIsWebStoreFeatured(false);
			setIsSelfService(true);
			setIsExcludeAutoDelivery(false);
			setProcessing(false);	// N
		}
	}    // MProduct

	/**
	 * Load constructor
	 *
	 * @param ctx     context
	 * @param rs      result set
	 * @param trxName transaction
	 */
	public MProduct(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    // MProduct

	@Nullable private UOMPrecision _precision = null; // lazy

	/**
	 * Get UOM Standard Precision
	 *
	 * @return UOM Standard Precision
	 * @deprecated Please use {@link IProductBL#getUOMPrecision(I_M_Product)}
	 */
	@Deprecated
	public int getUOMPrecision()
	{
		UOMPrecision precision = this._precision;
		if (precision == null)
		{
			final UomId uomId = UomId.ofRepoIdOrNull(getC_UOM_ID());
			if (uomId == null)
			{
				precision = UOMPrecision.ZERO;    // EA
				// NOTE: don't cache the precision (i.e. don't set m_precision)
			}
			else
			{
				precision = this._precision = Services.get(IUOMDAO.class).getStandardPrecision(uomId);
			}
		}
		return precision.toInt();
	}    // getUOMPrecision

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
	}    // isCreated

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
		final MAssetGroup ag = MAssetGroup.get(getCtx(), pc.getA_Asset_Group_ID());
		return ag.isOneAssetPerUOM();
	}    // isOneAssetPerUOM

	@Override
	public String toString()
	{
		return "MProduct[" + get_ID() + "-" + getValue() + "_" + getName() + "]";
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// Reset Stocked if not Item
		// AZ Goodwill: Bug Fix isStocked always return false
		// if (isStocked() && !PRODUCTTYPE_Item.equals(getProductType()))
		final ProductType productType = ProductType.ofCode(getProductType());
		if (!productType.isItem())
		{
			setIsStocked(false);
		}

		if (productType.isResource())
		{
			final ResourceId resourceId = ResourceId.ofRepoIdOrNull(getS_Resource_ID());
			final ResourceGroupId resourceGroupId = ResourceGroupId.ofRepoIdOrNull(getS_Resource_Group_ID());
			if (CoalesceUtil.countNotNulls(resourceId, resourceGroupId) != 1)
			{
				throw new AdempiereException("In case of resource products the resource or the resource group shall be set (one and only one)");
			}
		}

		// UOM reset
		if (is_ValueChanged(COLUMNNAME_C_UOM_ID))
		{
			this._precision = null;
		}

		return true;
	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
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
			final String sql = DB.convertSqlToNative("UPDATE A_Asset a "
					+ "SET (Name, Description)="
					+ "(SELECT SUBSTR((SELECT bp.Name FROM C_BPartner bp WHERE bp.C_BPartner_ID=a.C_BPartner_ID) || ' - ' || p.Name,1,60), p.Description "
					+ "FROM M_Product p "
					+ "WHERE p.M_Product_ID=a.M_Product_ID) "
					+ "WHERE IsActive='Y'"
					+ "  AND M_Product_ID=" + getM_Product_ID());
			final int no = DB.executeUpdateAndSaveErrorOnFail(sql, get_TrxName());
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
	}    // afterSave

	@Override
	protected boolean beforeDelete()
	{
		final ProductType productType = ProductType.ofCode(getProductType());
		if (productType.isResource() && getS_Resource_ID() > 0)
		{
			final ResourceId resourceId = ResourceId.ofRepoIdOrNull(getS_Resource_ID());
			final ResourceGroupId resourceGroupId = ResourceGroupId.ofRepoIdOrNull(getS_Resource_Group_ID());
			if (CoalesceUtil.countNotNulls(resourceId, resourceGroupId) > 0)
			{
				throw new AdempiereException("Resource products cannot be deleted by user");
			}
		}

		//
		delete_Accounting("M_Product_Acct");

		return true;
	}
}
