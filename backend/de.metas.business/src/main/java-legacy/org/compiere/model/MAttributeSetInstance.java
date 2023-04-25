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

import de.metas.product.IProductBL;
import de.metas.util.Services;
import org.adempiere.util.LegacyAdapters;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product Attribute Set Instance
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro <li>BF [ 2675699 ] MAttributeSetInstance.create should create Lot/Serial/Guaran
 * @version $Id: MAttributeSetInstance.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
@Deprecated
public class MAttributeSetInstance extends X_M_AttributeSetInstance
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7870720973216607658L;

	/**
	 * Get Attribute Set Instance from ID or Product
	 *
	 * @param ctx                       context
	 * @param M_AttributeSetInstance_ID id or 0
	 * @param M_Product_ID              required if id is 0
	 * @return Attribute Set Instance or null if error'
	 * @deprecated Please use {@link IProductBL#getCreateASI(Properties, int, int)}
	 */
	@Deprecated
	public static MAttributeSetInstance get(Properties ctx, int M_AttributeSetInstance_ID, int M_Product_ID)
	{
		final I_M_AttributeSetInstance asi = Services.get(IProductBL.class).getCreateASI(ctx, M_AttributeSetInstance_ID, M_Product_ID);
		return LegacyAdapters.convertToPO(asi);
	}    // get

	public MAttributeSetInstance(Properties ctx, int M_AttributeSetInstance_ID, String trxName)
	{
		super(ctx, M_AttributeSetInstance_ID, trxName);
	}

	public MAttributeSetInstance(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MAttributeSetInstance(Properties ctx, int M_AttributeSetInstance_ID, int M_AttributeSet_ID, String trxName)
	{
		this(ctx, M_AttributeSetInstance_ID, trxName);
		setM_AttributeSet_ID(M_AttributeSet_ID);
	}

	/**
	 * Attribute Set
	 */
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
	}    // setAttributeSet

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
	}    // getMAttributeSet
}

