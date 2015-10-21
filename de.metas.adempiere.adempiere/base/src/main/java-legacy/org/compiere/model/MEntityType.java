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

import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;

/**
 * 	Entity Type Model
 */
@Deprecated
public class MEntityType extends X_AD_EntityType
{
	private static final long serialVersionUID = -2183955192373166750L;

	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_EntityType_ID id
	 *	@param trxName transaction
	 */
	public MEntityType (Properties ctx, int AD_EntityType_ID, String trxName)
	{
		super (ctx, AD_EntityType_ID, trxName);
	}	//	MEntityType

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MEntityType (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MEntityType
	
	/**
	 * 	Set AD_EntityType_ID
	 */
	private final void setAD_EntityType_ID()
	{
		int AD_EntityType_ID = getAD_EntityType_ID();
		if (AD_EntityType_ID == 0)
		{
			String sql = "SELECT COALESCE(MAX(AD_EntityType_ID), 999999) FROM AD_EntityType WHERE AD_EntityType_ID > 1000";
			AD_EntityType_ID= DB.getSQLValue (get_TrxName(), sql);
			setAD_EntityType_ID(AD_EntityType_ID+1);
		}
	}	//	setAD_EntityType_ID
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true if it can be saved
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (!newRecord)
		{
			int id = getAD_EntityType_ID();
			boolean systemMaintained = (id == 10 || id == 20);	//	C/D
			if (systemMaintained)
			{
				throw new AdempiereException("You cannot modify a System maintained entity");
			}
			systemMaintained = is_ValueChanged("EntityType");
			if (systemMaintained)
			{
				throw new AdempiereException("You cannot modify EntityType");
			}
// yes we can
//			systemMaintained = isSystemMaintained()
//				&&	(is_ValueChanged("Name") || is_ValueChanged("Description") 
//					|| is_ValueChanged("Help") || is_ValueChanged("IsActive"));
//			if (systemMaintained)
//			{
//				throw new AdempiereException("You cannot modify Name,Description,Help");
//			}
		}
		else	//	new
		{
			/*
			setEntityType(getEntityType().toUpperCase());	//	upper case
			if (getEntityType().trim().length() < 4)
			{
				log.saveError("FillMandatory", Msg.getElement(getCtx(), "EntityType") 
					+ " - 4 Characters");
				return false;
			}
			boolean ok = true;
			char[] cc = getEntityType().toCharArray();
			for (int i = 0; i < cc.length; i++)
			{
				char c = cc[i];
				if (Character.isDigit(c) || (c >= 'A' && c <= 'Z'))
					continue;
				//
				log.saveError("FillMandatory", Msg.getElement(getCtx(), "EntityType") 
					+ " - Must be ASCII Letter or Digit");
				return false;
			}
			*/
			setAD_EntityType_ID();
		}	//	new
		
		CacheMgt.get().reset(I_AD_EntityType.Table_Name);
		
		return true;
	}	//	beforeSave
	
	/**
	 * 	Before Delete
	 *	@return true if it can be deleted
	 */
	@Override
	protected boolean beforeDelete ()
	{
		if (EntityTypesCache.instance.isSystemMaintained(getEntityType()))	//	all pre-defined
		{
			throw new AdempiereException("You cannot delete a System maintained entity");
		}
		return true;
	}	//	beforeDelete
	
}	//	MEntityType
