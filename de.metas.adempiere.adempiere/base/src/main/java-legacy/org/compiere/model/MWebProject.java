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

import org.compiere.util.CCache;

/**
 * 	Web Project Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MWebProject.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MWebProject extends X_CM_WebProject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7404800005095450170L;


	/**
	 * 	Get MWebProject from Cache
	 *	@param ctx context
	 *	@param CM_WebProject_ID id
	 *	@return MWebProject
	 */
	public static MWebProject get (Properties ctx, int CM_WebProject_ID)
	{
		Integer key = new Integer (CM_WebProject_ID);
		MWebProject retValue = (MWebProject)s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MWebProject (ctx, CM_WebProject_ID, null);
		if (retValue.get_ID () == CM_WebProject_ID)
			s_cache.put (key, retValue);
		return retValue;
	} //	get

	/**	Cache						*/
	private static CCache<Integer, MWebProject> s_cache 
		= new CCache<Integer, MWebProject> ("CM_WebProject", 5);
	
	
	/**************************************************************************
	 * 	Web Project
	 *	@param ctx context
	 *	@param CM_WebProject_ID id
	 *	@param trxName transaction
	 */
	public MWebProject (Properties ctx, int CM_WebProject_ID, String trxName)
	{
		super (ctx, CM_WebProject_ID, trxName);
	}	//	MWebProject

	/**
	 * 	Web Project
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MWebProject (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MWebProject
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		//	Create Trees
		if (newRecord)
		{
			MTree_Base tree = new MTree_Base (getCtx(), 
				getName()+MTree_Base.TREETYPE_CMContainer, MTree_Base.TREETYPE_CMContainer, get_TrxName());
			if (!tree.save())
				return false;
			setAD_TreeCMC_ID(tree.getAD_Tree_ID());
			//
			tree = new MTree_Base (getCtx(), 
				getName()+MTree_Base.TREETYPE_CMContainerStage, MTree_Base.TREETYPE_CMContainerStage, get_TrxName());
			if (!tree.save())
				return false;
			setAD_TreeCMS_ID(tree.getAD_Tree_ID());
			//
			tree = new MTree_Base (getCtx(), 
				getName()+MTree_Base.TREETYPE_CMTemplate, MTree_Base.TREETYPE_CMTemplate, get_TrxName());
			if (!tree.save())
				return false;
			setAD_TreeCMT_ID(tree.getAD_Tree_ID());
			//
			tree = new MTree_Base (getCtx(), 
				getName()+MTree_Base.TREETYPE_CMMedia, MTree_Base.TREETYPE_CMMedia, get_TrxName());
			if (!tree.save())
				return false;
			setAD_TreeCMM_ID(tree.getAD_Tree_ID());
		}
		return true;
	}	//	beforeSave
	
	/**
	 * 	After Save.
	 * 	Insert
	 * 	- create tree
	 *	@param newRecord insert
	 *	@param success save success
	 *	@return true if saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if (!newRecord)
		{
			// Clean Web Project Cache
		}
		return success;
	}	//	afterSave
	
}	//	MWebProject
