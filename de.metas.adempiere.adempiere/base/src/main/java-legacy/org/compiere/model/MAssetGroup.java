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

/**
 * 	Asset Group Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MAssetGroup.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MAssetGroup extends X_A_Asset_Group
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1364948077775028283L;

	/**
	 * 	Get from Cache
	 *	@param ctx context
	 *	@param A_Asset_Group_ID id
	 *	@return category
	 */
	public static MAssetGroup get (Properties ctx, int A_Asset_Group_ID)
	{
		Integer ii = new Integer (A_Asset_Group_ID);
		MAssetGroup pc = (MAssetGroup)s_cache.get(ii);
		if (pc == null)
			pc = new MAssetGroup (ctx, A_Asset_Group_ID, null);
		return pc;
	}	//	get

	/**	Categopry Cache				*/
	private static CCache<Integer, MAssetGroup>	s_cache = new CCache<Integer, MAssetGroup> ("A_Asset_Group", 10);

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param A_Asset_Group_ID id
	 *	@param trxName trx
	 */
	public MAssetGroup (Properties ctx, int A_Asset_Group_ID, String trxName)
	{
		super (ctx, A_Asset_Group_ID, trxName);
		if (A_Asset_Group_ID == 0)
		{
		//	setName (null);
			setIsDepreciated (false);
			setIsOneAssetPerUOM (false);
			setIsOwned (false);
			setIsCreateAsActive(true);
			setIsTrackIssues(false);
		}
	}	//	MAssetGroup

	/**
	 * 	Load Cosntructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MAssetGroup (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MAssetGroup
	
}	//	MAssetGroup
