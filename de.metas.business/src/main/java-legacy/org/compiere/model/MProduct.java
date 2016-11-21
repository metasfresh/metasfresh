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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;

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
	/**
	 *
	 */
	private static final long serialVersionUID = 285926961771269935L;

	/**
	 * Get MProduct from Cache
	 *
	 * @param ctx context
	 * @param M_Product_ID id
	 * @return MProduct or null
	 */
	public static MProduct get(Properties ctx, int M_Product_ID)
	{
		if (M_Product_ID <= 0)
		{
			return null;
		}

		// NOTE: we rely on table cache config
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, M_Product_ID, I_M_Product.class, ITrx.TRXNAME_None);
		return LegacyAdapters.convertToPO(product);
	}	// get

	/**
	 * Get MProduct from Cache
	 *
	 * @param ctx context
	 * @param whereClause sql where clause
	 * @param trxName trx
	 * @return MProduct
	 */
	public static MProduct[] get(Properties ctx, String whereClause, String trxName)
	{
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		List<MProduct> list = new Query(ctx, Table_Name, "AD_Client_ID=? AND " + whereClause, trxName)
				.setParameters(new Object[] { AD_Client_ID })
				.list();
		return list.toArray(new MProduct[list.size()]);
	}	// get

	/**
	 * Get MProduct using UPC/EAN (case sensitive)
	 *
	 * @param ctx Context
	 * @param upc The upc to look for
	 * @return List of MProduct
	 */
	public static List<MProduct> getByUPC(Properties ctx, String upc, String trxName)
	{
		String whereClause = "AD_Client_ID=? AND UPC=?";
		Query q = new Query(ctx, Table_Name, whereClause, trxName);
		q.setParameters(new Object[] { Env.getAD_Client_ID(ctx), upc });
		return (q.list());
	}

	/**
	 * Get Product from Cache
	 *
	 * @param ctx context
	 * @param S_Resource_ID resource ID
	 * @return MProduct or null if not found
	 * @deprecated Please use {@link IProductDAO#retrieveForResourceId(Properties, int, String)}
	 */
	@Deprecated
	public static MProduct forS_Resource_ID(Properties ctx, int S_Resource_ID)
	{
		return forS_Resource_ID(ctx, S_Resource_ID, null);
	}

	/**
	 * Get Product from Cache
	 *
	 * @param ctx context
	 * @param S_Resource_ID resource ID
	 * @param trxName
	 * @return MProduct or null if not found
	 * @deprecated Please use {@link IProductDAO#retrieveForResourceId(Properties, int, String)}
	 */
	@Deprecated
	public static MProduct forS_Resource_ID(Properties ctx, int S_Resource_ID, String trxName)
	{
		final I_M_Product product = Services.get(IProductDAO.class).retrieveForResourceId(ctx, S_Resource_ID, trxName);
		return LegacyAdapters.convertToPO(product);
	}

	/**
	 * Is Product Stocked
	 *
	 * @param ctx context
	 * @param M_Product_ID id
	 * @return true if found and stocked - false otherwise
	 * @deprecated Please use {@link IProductBL#isStocked(I_M_Product)}
	 */
	@Deprecated
	public static boolean isProductStocked(Properties ctx, int M_Product_ID)
	{
		final MProduct product = get(ctx, M_Product_ID);
		return Services.get(IProductBL.class).isStocked(product);
	}	// isProductStocked

	/**
	 * Product is an Item and Stocked
	 *
	 * @param product
	 * @return true if stocked and item
	 * @deprecated Please use {@link IProductBL#isStocked(I_M_Product)}
	 */
	@Deprecated
	public static boolean isProductStocked(final I_M_Product product)
	{
		return Services.get(IProductBL.class).isStocked(product);
	}

//	/** Cache */
//	private static CCache<Integer, MProduct> s_cache = new CCache<>(Table_Name, 40, 5);	// 5 minutes

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
	public MProduct(MExpenseType et)
	{
		this(et.getCtx(), 0, et.get_TrxName());
		setProductType(X_M_Product.PRODUCTTYPE_ExpenseType);
		setExpenseType(et);
	}	// MProduct

	/**
	 * Parent Constructor
	 *
	 * @param resource parent
	 * @param resourceType resource type
	 */
	public MProduct(MResource resource, MResourceType resourceType)
	{
		this(resource.getCtx(), 0, resource.get_TrxName());
		setAD_Org_ID(resource.getAD_Org_ID());
		setProductType(X_M_Product.PRODUCTTYPE_Resource);
		setResource(resource);
		setResource(resourceType);
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
		setM_Product_Category_ID(impP.getM_Product_Category_ID());
		setProductType(impP.getProductType());
		setImageURL(impP.getImageURL());
		setDescriptionURL(impP.getDescriptionURL());
	}	// MProduct

	/** Additional Downloads */
	private MProductDownload[] m_downloads = null;

	/**
	 * Set Expense Type
	 *
	 * @param parent expense type
	 * @return true if changed
	 */
	public boolean setExpenseType(MExpenseType parent)
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

	/**
	 * Set Resource
	 *
	 * @param parent resource
	 * @return true if changed
	 */
	public boolean setResource(MResource parent)
	{
		boolean changed = false;
		if (!PRODUCTTYPE_Resource.equals(getProductType()))
		{
			setProductType(PRODUCTTYPE_Resource);
			changed = true;
		}
		if (parent.getS_Resource_ID() != getS_Resource_ID())
		{
			setS_Resource_ID(parent.getS_Resource_ID());
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
		//
		return changed;
	}	// setResource

	/**
	 * Set Resource Type
	 *
	 * @param parent resource type
	 * @return true if changed
	 */
	public boolean setResource(MResourceType parent)
	{
		boolean changed = false;
		if (PRODUCTTYPE_Resource.equals(getProductType()))
		{
			setProductType(PRODUCTTYPE_Resource);
			changed = true;
		}
		//
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

		//
		// metas 05129 end
		return changed;
	}	// setResource

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
				return 0;	// EA
			m_precision = new Integer(MUOM.getPrecision(getCtx(), C_UOM_ID));
		}
		return m_precision.intValue();
	}	// getUOMPrecision

	/**
	 * Create Asset Group for this product
	 *
	 * @return asset group id
	 */
	public int getA_Asset_Group_ID()
	{
		MProductCategory pc = MProductCategory.get(getCtx(), getM_Product_Category_ID());
		return pc.getA_Asset_Group_ID();
	}	// getA_Asset_Group_ID

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
	 * Get Attribute Set
	 *
	 * @return set or null
	 * @deprecated Please use {@link IProductBL#getM_AttributeSet(I_M_Product)}
	 */
	@Deprecated
	public I_M_AttributeSet getAttributeSet()
	{
		return Services.get(IProductBL.class).getM_AttributeSet(this);
	}	// getAttributeSet

	/**
	 * Has the Product Instance Attribute
	 *
	 * @return true if instance attributes
	 */
	public boolean isInstanceAttribute()
	{
		I_M_AttributeSet mas = Services.get(IProductBL.class).getM_AttributeSet(this);

		if (mas == null)
		{
			return false;
		}
		return mas.isInstanceAttribute();
	}	// isInstanceAttribute

	/**
	 * Create One Asset Per UOM
	 *
	 * @return individual asset
	 */
	public boolean isOneAssetPerUOM()
	{
		MProductCategory pc = MProductCategory.get(getCtx(), getM_Product_Category_ID());
		if (pc.getA_Asset_Group_ID() == 0)
			return false;
		MAssetGroup ag = MAssetGroup.get(getCtx(), pc.getA_Asset_Group_ID());
		return ag.isOneAssetPerUOM();
	}	// isOneAssetPerUOM

	/**
	 * Product is Item
	 *
	 * @return true if item
	 * @deprecated please use {@link IProductBL#isItem(I_M_Product)}
	 */
	@Deprecated
	public boolean isItem()
	{
		return Services.get(IProductBL.class).isItem(this);
	}	// isItem

	/**
	 * Is Service
	 *
	 * @return true if service (resource, online)
	 * @deprecated Please use {@link IProductBL#isService(I_M_Product)}
	 */
	@Deprecated
	public boolean isService()
	{
		return Services.get(IProductBL.class).isService(this);
	}	// isService

	/**
	 * Get UOM Symbol
	 *
	 * @return UOM Symbol
	 */
	public String getUOMSymbol()
	{
		int C_UOM_ID = getC_UOM_ID();
		if (C_UOM_ID == 0)
			return "";
		return MUOM.get(getCtx(), C_UOM_ID).getUOMSymbol();
	}	// getUOMSymbol

	/**
	 * Get Active(!) Product Downloads
	 *
	 * @param requery requery
	 * @return array of downloads
	 */
	public MProductDownload[] getProductDownloads(boolean requery)
	{
		if (m_downloads != null && !requery)
			return m_downloads;
		//
		List<MProductDownload> list = new Query(getCtx(), MProductDownload.Table_Name, "M_Product_ID=?", get_TrxName())
				.setOnlyActiveRecords(true)
				.setOrderBy(MProductDownload.COLUMNNAME_Name)
				.setParameters(new Object[] { get_ID() })
				.list();
		m_downloads = list.toArray(new MProductDownload[list.size()]);
		return m_downloads;
	}	// getProductDownloads

	/**
	 * Does the product have downloads
	 *
	 * @return true if downloads exists
	 */
	public boolean hasDownloads()
	{
		return getProductDownloads(false).length > 0;
	}	// hasDownloads

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
		// Check Storage
		if (!newRecord && 	//
				((is_ValueChanged("IsActive") && !isActive())		// now not active
						|| (is_ValueChanged("IsStocked") && !Services.get(IProductBL.class).isStocked(this))	// now not stocked
				|| (is_ValueChanged("ProductType") 					// from Item
				&& PRODUCTTYPE_Item.equals(get_ValueOld("ProductType")))))
		{
			MStorage[] storages = MStorage.getOfProduct(getCtx(), get_ID(), get_TrxName());
			BigDecimal OnHand = Env.ZERO;
			BigDecimal Ordered = Env.ZERO;
			BigDecimal Reserved = Env.ZERO;
			for (int i = 0; i < storages.length; i++)
			{
				OnHand = OnHand.add(storages[i].getQtyOnHand());
				Ordered = Ordered.add(storages[i].getQtyOrdered());
				Reserved = Reserved.add(storages[i].getQtyReserved());
			}
			String errMsg = "";
			if (OnHand.signum() != 0)
				errMsg = "@QtyOnHand@ = " + OnHand;
			if (Ordered.signum() != 0)
				errMsg += " - @QtyOrdered@ = " + Ordered;
			if (Reserved.signum() != 0)
				errMsg += " - @QtyReserved@" + Reserved;
			if (errMsg.length() > 0)
			{
				throw new AdempiereException(errMsg);
			}
		}	// storage

		// it checks if UOM has been changed , if so disallow the change if the condition is true.
		if ((!newRecord) && is_ValueChanged("C_UOM_ID") && hasInventoryOrCost())
		{
			throw new AdempiereException("@SaveUomError@");
		}

		// Reset Stocked if not Item
		// AZ Goodwill: Bug Fix isStocked always return false
		// if (isStocked() && !PRODUCTTYPE_Item.equals(getProductType()))
		if (!PRODUCTTYPE_Item.equals(getProductType()))
			setIsStocked(false);

		// UOM reset
		if (m_precision != null && is_ValueChanged("C_UOM_ID"))
			m_precision = null;

		return true;
	}	// beforeSave

	/**
	 * HasInventoryOrCost
	 *
	 * @return true if it has Inventory or Cost
	 */
	protected boolean hasInventoryOrCost()
	{
		// check if it has transactions
		boolean hasTrx = new Query(getCtx(), MTransaction.Table_Name,
				MTransaction.COLUMNNAME_M_Product_ID + "=?", get_TrxName())
				.setOnlyActiveRecords(true)
				.setParameters(new Object[] { get_ID() })
				.match();
		if (hasTrx)
		{
			return true;
		}

		// check if it has cost
		boolean hasCosts = new Query(getCtx(), MCostDetail.Table_Name,
				MCostDetail.COLUMNNAME_M_Product_ID + "=?", get_TrxName())
				.setOnlyActiveRecords(true)
				.setParameters(new Object[] { get_ID() })
				.match();
		if (hasCosts)
		{
			return true;
		}

		return false;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
			return success;

		// Value/Name change in Account
		if (!newRecord && (is_ValueChanged("Value") || is_ValueChanged("Name")))
			MAccount.updateValueDescription(getCtx(), "M_Product_ID=" + getM_Product_ID(), get_TrxName());

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
			insert_Accounting("M_Product_Acct", "M_Product_Category_Acct",
					"p.M_Product_Category_ID=" + getM_Product_Category_ID());
			insert_Tree(X_AD_Tree.TREETYPE_Product);
			//
			final I_C_AcctSchema[] mass = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID());
			for (int i = 0; i < mass.length; i++)
			{
				// Old
				MProductCosting pcOld = new MProductCosting(this, mass[i].getC_AcctSchema_ID());
				pcOld.save();
			}
		}

		// New Costing
		if (newRecord || is_ValueChanged("M_Product_Category_ID"))
			MCost.create(this);

		return success;
	}	// afterSave

	@Override
	protected boolean beforeDelete()
	{
		if (PRODUCTTYPE_Resource.equals(getProductType()) && getS_Resource_ID() > 0)
		{
			throw new AdempiereException("@S_Resource_ID@<>0");
		}
		// Check Storage
		if (Services.get(IProductBL.class).isStocked(this) || PRODUCTTYPE_Item.equals(getProductType()))
		{
			MStorage[] storages = MStorage.getOfProduct(getCtx(), get_ID(), get_TrxName());
			BigDecimal OnHand = Env.ZERO;
			BigDecimal Ordered = Env.ZERO;
			BigDecimal Reserved = Env.ZERO;
			for (int i = 0; i < storages.length; i++)
			{
				OnHand = OnHand.add(storages[i].getQtyOnHand());
				Ordered = OnHand.add(storages[i].getQtyOrdered());
				Reserved = OnHand.add(storages[i].getQtyReserved());
			}
			String errMsg = "";
			if (OnHand.signum() != 0)
				errMsg = "@QtyOnHand@ = " + OnHand;
			if (Ordered.signum() != 0)
				errMsg += " - @QtyOrdered@ = " + Ordered;
			if (Reserved.signum() != 0)
				errMsg += " - @QtyReserved@" + Reserved;
			if (errMsg.length() > 0)
			{
				throw new AdempiereException(errMsg);
			}

		}
		// delete costing
		MProductCosting[] costings = MProductCosting.getOfProduct(getCtx(), get_ID(), get_TrxName());
		for (int i = 0; i < costings.length; i++)
			costings[i].delete(true, get_TrxName());

		MCost.delete(this);

		// [ 1674225 ] Delete Product: Costing deletion error
		/*
		 * MAcctSchema[] mass = MAcctSchema.getClientAcctSchema(getCtx(),getAD_Client_ID(), get_TrxName()); for(int i=0; i<mass.length; i++) { // Get Cost Elements MCostElement[] ces =
		 * MCostElement.getMaterialWithCostingMethods(this); MCostElement ce = null; for(int j=0; j<ces.length; j++) { if(MCostElement.COSTINGMETHOD_StandardCosting.equals(ces[i].getCostingMethod()))
		 * { ce = ces[i]; break; } }
		 *
		 * if(ce == null) continue;
		 *
		 * MCost mcost = MCost.get(this, 0, mass[i], 0, ce.getM_CostElement_ID()); mcost.delete(true, get_TrxName()); }
		 */

		//
		return delete_Accounting("M_Product_Acct");
	}	// beforeDelete

	@Override
	protected boolean afterDelete(boolean success)
	{
//		if (success)
//			delete_Tree(X_AD_Tree.TREETYPE_Product);
		return success;
	}	// afterDelete

	/**
	 * Gets Material Management Policy. Tries: Product Category, Client (in this order)
	 *
	 * @return Material Management Policy
	 * @deprecated Please use {@link IProductBL#getMMPolicy(I_M_Product)}
	 */
	@Deprecated
	public String getMMPolicy()
	{
		return Services.get(IProductBL.class).getMMPolicy(this);
	}

	/**
	 * Check if ASI is mandatory
	 *
	 * @param isSOTrx is outgoing trx?
	 * @return true if ASI is mandatory, false otherwise
	 */
	public boolean isASIMandatory(boolean isSOTrx)
	{
		//
		// If CostingLevel is BatchLot ASI is always mandatory - check all client acct schemas
		final MAcctSchema[] mass = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID());
		for (final MAcctSchema as : mass)
		{
			if(as.isSkipOrg(getAD_Org_ID()))
			{
				continue;
			}
			final String cl = Services.get(IProductBL.class).getCostingLevel(this, as);
			if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(cl))
			{
				return true;
			}
		}
		//
		// Check Attribute Set settings
		final int M_AttributeSet_ID = Services.get(IProductBL.class).getM_AttributeSet_ID(this);
		if (M_AttributeSet_ID > 0)
		{
			final MAttributeSet mas = MAttributeSet.get(getCtx(), M_AttributeSet_ID);
			if (mas == null || !mas.isInstanceAttribute())
			{
				return false;
			}
			// Outgoing transaction
			else if (isSOTrx)
			{
				return mas.isMandatory();
			}
			// Incoming transaction
			else
			{
				// isSOTrx == false
				return mas.isMandatoryAlways();
			}
		}
		//
		// Default not mandatory
		return false;
	}

	/**
	 * Get Product Costing Level
	 *
	 * @param as accounting schema
	 * @return product costing level
	 * @deprecated Please use {@link IProductBL#getCostingLevel(I_M_Product, I_C_AcctSchema)}.
	 */
	@Deprecated
	public String getCostingLevel(final I_C_AcctSchema as)
	{
		return Services.get(IProductBL.class).getCostingLevel(this, as);
	}

	/**
	 * Get Product Costing Method
	 *
	 * @param C_AcctSchema_ID accounting schema ID
	 * @return product costing method
	 * @deprecated Please use {@link IProductBL#getCostingMethod(I_M_Product, I_C_AcctSchema)}.
	 */
	@Deprecated
	public String getCostingMethod(I_C_AcctSchema as)
	{
		return Services.get(IProductBL.class).getCostingMethod(this, as);
	}
}	// MProduct
