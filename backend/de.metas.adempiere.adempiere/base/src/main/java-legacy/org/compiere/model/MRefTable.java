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

import de.metas.cache.CCache;


public class MRefTable extends X_AD_Ref_Table
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9123965213307214868L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Reference_ID id warning if you are referring to reference list or table type should be used AD_Reference_Value_ID
	 *	@param trxName trx
	 */
	@SuppressWarnings("unused")
	public MRefTable (Properties ctx, int AD_Reference_ID, String trxName)
	{
		super (ctx, AD_Reference_ID, trxName);
		if (is_new())
		{
			setEntityType (ENTITYTYPE_UserMaintained);	// U
			setIsValueDisplayed (false);
		}
	}	//	MRefTable

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	@SuppressWarnings("unused")
	public MRefTable (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MRefTable

	// metas: begin
	private static final CCache<Integer, MRefTable> s_cache = new CCache<>(Table_Name, 50, 0);

	@Deprecated
	public static MRefTable get(Properties ctx, int AD_Reference_ID)
	{
		if (AD_Reference_ID <= 0)
			return null;

		MRefTable retValue = s_cache.get(AD_Reference_ID);
		if (retValue != null)
			return retValue;

		retValue = new Query(ctx, Table_Name, COLUMNNAME_AD_Reference_ID + "=?", null)
				.setParameters(AD_Reference_ID)
				.firstOnly(MRefTable.class);

		if (retValue != null)
		{
			s_cache.put(AD_Reference_ID, retValue);
		}

		return retValue;
	}
	// metas: end
}	//	MRefTable
