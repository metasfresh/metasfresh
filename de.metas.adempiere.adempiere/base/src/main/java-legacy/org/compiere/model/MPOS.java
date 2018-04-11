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
import org.compiere.util.CCache;


/**
 *	POS Terminal definition
 *	
 *  @author Jorg Janke
 *  @version $Id: MPOS.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MPOS extends X_C_POS
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1568195843844720536L;

	/**
	 * 	Get POS from Cache
	 *	@param ctx context
	 *	@param C_POS_ID id
	 *	@return MPOS
	 */
	public static MPOS get (Properties ctx, int C_POS_ID)
	{
		Integer key = new Integer (C_POS_ID);
		MPOS retValue = s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MPOS (ctx, C_POS_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	} //	get
	
	/**	Cache						*/
	private static CCache<Integer,MPOS> s_cache = new CCache<>("C_POS", 20);

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_POS_ID id
	 *	@param trxName transaction
	 */
	public MPOS (Properties ctx, int C_POS_ID, String trxName)
	{
		super (ctx, C_POS_ID, trxName);
		if (C_POS_ID == 0)
		{
		//	setName (null);
		//	setSalesRep_ID (0);
		//	setC_CashBook_ID (0);
		//	setM_PriceList_ID (0);
			setIsModifyPrice (false);	// N
		//	setM_Warehouse_ID (0);
		}	
	}	//	MPOS

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MPOS (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPOS
	
	/**	Cash Business Partner			*/
	private MBPartner	m_template = null;
	
	
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		//	Org Consistency
		if (newRecord 
			|| is_ValueChanged("C_CashBook_ID") || is_ValueChanged("M_Warehouse_ID"))
		{
			MCashBook cb = MCashBook.get(getCtx(), getC_CashBook_ID(), get_TrxName());
			if (cb.getAD_Org_ID() != getAD_Org_ID())
			{
				throw new AdempiereException("@AD_Org_ID@: @C_CashBook_ID@");
			}
			MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID(), get_TrxName());
			if (wh.getAD_Org_ID() != getAD_Org_ID())
			{
				throw new AdempiereException("@AD_Org_ID@: @M_Warehouse_ID@");
			}
		}
		return true;
	}	//	beforeSave

	
	/**
	 * 	Get default Cash BPartner
	 *	@return BPartner
	 */
	public MBPartner getBPartner()
	{
		if (m_template == null)
		{
			if (getC_BPartnerCashTrx_ID() == 0)
				m_template = MBPartner.getBPartnerCashTrx (getCtx(), getAD_Client_ID());
			else
				m_template = new MBPartner(getCtx(), getC_BPartnerCashTrx_ID(), get_TrxName());
			log.debug("getBPartner - " + m_template);
		}
		return m_template;
	}	//	getBPartner

	@Override
	public String toString() {
		return super.getName();
		}
	
}	//	MPOS
