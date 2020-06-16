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

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.util.LegacyAdapters;
import org.compiere.util.DB;
import org.compiere.util.KeyNamePair;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

/**
 * Product Attribute Set Instance
 * 
 * @author Jorg Janke
 * @version $Id: MAttributeSetInstance.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro <li>BF [ 2675699 ] MAttributeSetInstance.create should create Lot/Serial/Guaran
 */
public class MAttributeSetInstance extends X_M_AttributeSetInstance
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7870720973216607658L;

	/**
	 * Get Attribute Set Instance from ID or Product
	 * 
	 * @param ctx context
	 * @param M_AttributeSetInstance_ID id or 0
	 * @param M_Product_ID required if id is 0
	 * @return Attribute Set Instance or null if error'
	 * @deprecated Please use {@link IProductBL#getCreateASI(Properties, int, int)}
	 */
	@Deprecated
	public static MAttributeSetInstance get(Properties ctx, int M_AttributeSetInstance_ID, int M_Product_ID)
	{
		final I_M_AttributeSetInstance asi = Services.get(IProductBL.class).getCreateASI(ctx, M_AttributeSetInstance_ID, M_Product_ID);
		return LegacyAdapters.convertToPO(asi);
	}	// get

//	private static Logger s_log = CLogMgt.getLogger(MAttributeSetInstance.class);

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param M_AttributeSetInstance_ID id
	 * @param trxName transaction
	 */
	public MAttributeSetInstance(Properties ctx, int M_AttributeSetInstance_ID, String trxName)
	{
		super(ctx, M_AttributeSetInstance_ID, trxName);
	}	// MAttributeSetInstance

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MAttributeSetInstance(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MAttributeSetInstance

	/**
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param M_AttributeSetInstance_ID id
	 * @param M_AttributeSet_ID attribute set
	 * @param trxName transaction
	 */
	public MAttributeSetInstance(Properties ctx, int M_AttributeSetInstance_ID,
			int M_AttributeSet_ID, String trxName)
	{
		this(ctx, M_AttributeSetInstance_ID, trxName);
		setM_AttributeSet_ID(M_AttributeSet_ID);
	}	// MAttributeSetInstance

	/** Attribute Set */
	private MAttributeSet m_mas = null;

	/**
	 * Set Attribute Set
	 * 
	 * @param mas attribute set
	 */
	public void setMAttributeSet(MAttributeSet mas)
	{
		m_mas = mas;
		setM_AttributeSet(mas);
	}	// setAttributeSet

	/**
	 * Get Attribute Set
	 * 
	 * @return Attrbute Set or null
	 */
	public MAttributeSet getMAttributeSet()
	{
		if (m_mas == null && getM_AttributeSet_ID() != 0)
			m_mas = new MAttributeSet(getCtx(), getM_AttributeSet_ID(), get_TrxName());
		return m_mas;
	}	// getMAttributeSet

	/**
	 * Get Lot No
	 * 
	 * @param getNew if true create/set new lot
	 * @param productId product used if new
	 * @return lot
	 */
	private String getLot(boolean getNew, final ProductId productId)
	{
		if (getNew)
			createLot(productId);
		return getLot();
	}	// getLot

	/**
	 * Create Lot
	 * 
	 * @param productId product used if new
	 * @return lot info
	 */
	private KeyNamePair createLot(final ProductId productId)
	{
		final KeyNamePair retValue = getMAttributeSet().createLot(productId);
		if(retValue != null)
		{
			setM_Lot_ID(retValue.getKey());
			setLot(retValue.getName());
		}
		return retValue;
	}	// createLot

	/**
	 * To to find lot and set Lot/ID
	 * 
	 * @param Lot lot
	 * @param M_Product_ID product
	 */
	public void setLot(String Lot, int M_Product_ID)
	{
		// Try to find it
		MLot mLot = MLot.getProductLot(getCtx(), M_Product_ID, Lot, get_TrxName());
		if (mLot != null)
			setM_Lot_ID(mLot.getM_Lot_ID());
		setLot(Lot);
	}	// setLot

	/**
	 * Exclude Lot creation
	 * 
	 * @param AD_Column_ID column
	 * @param isSOTrx SO
	 * @return true if excluded
	 */
	public boolean isExcludeLot(int AD_Column_ID, boolean isSOTrx)
	{
		getMAttributeSet();
		if (m_mas != null)
			return m_mas.isExcludeLot(AD_Column_ID, isSOTrx);
		return false;
	}	// isExcludeLot

	/**
	 * Get Serial No
	 * 
	 * @param getNew if true create/set new Ser No
	 * @return Serial Number
	 */
	public String getSerNo(final boolean getNew)
	{
		if (getNew)
		{
			final String serNo = getMAttributeSet().createSerNo();
			if(serNo != null)
			{
				setSerNo(serNo);
			}
		}
		return getSerNo();
	}	// getSerNo

	/**
	 * Exclude SerNo creation
	 * 
	 * @param AD_Column_ID column
	 * @param isSOTrx SO
	 * @return true if excluded
	 */
	public boolean isExcludeSerNo(int AD_Column_ID, boolean isSOTrx)
	{
		getMAttributeSet();
		if (m_mas != null)
			return m_mas.isExcludeSerNo(AD_Column_ID, isSOTrx);
		return false;
	}	// isExcludeSerNo

	/**
	 * Create & save a new ASI for given product. Automatically creates Lot#, Serial#.
	 * 
	 * NOTE: Guarantee Date needs to be explicitly calculated and set.
	 * 
	 * @param ctx
	 * @param product
	 * @param trxName
	 * @return newly created ASI
	 */
	public static MAttributeSetInstance create(Properties ctx, I_M_Product product, String trxName)
	{
		final AttributeSetId attributeSetId = Services.get(IProductBL.class).getAttributeSetId(product);

		MAttributeSetInstance asi = new MAttributeSetInstance(ctx, 0, trxName);
		asi.setClientOrg(product.getAD_Client_ID(), 0);
		asi.setM_AttributeSet_ID(attributeSetId.getRepoId());
		// Create new Lot, Serial# and Guarantee Date
		if (asi.getM_AttributeSet_ID() > 0)
		{
			asi.getLot(true, ProductId.ofRepoId(product.getM_Product_ID()));
			asi.getSerNo(true);
			
			// metas-tsa: guarantee date needs to be explicitly set because for calculating it we need more info (vendor bpartner, product, receipt date).
			// see task #09363.
			//asi.getGuaranteeDate(true);
		}
		//
		asi.saveEx();
		return asi;
	}
}	// MAttributeSetInstance
