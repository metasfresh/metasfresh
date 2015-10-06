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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.DB;


/**
 *	(Disk) Tree Node Model CM Stage
 *	
 *  @author Jorg Janke
 *  @version $Id: MTree_NodeCMS.java,v 1.2 2006/07/30 00:58:36 jjanke Exp $
 */
public class MTree_NodeCMS extends X_AD_TreeNodeCMS
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8092902713429554718L;

	/**
	 * 	Get Tree
	 *	@param ctx context
	 *	@param AD_Tree_ID tree
	 *	@param trxName transaction
	 *	@return array of nodes
	 */
	public static MTree_NodeCMS[] getTree (Properties ctx, int AD_Tree_ID, String trxName)
	{
		ArrayList<MTree_NodeCMS> list = new ArrayList<MTree_NodeCMS>();
		String sql = "SELECT * FROM AD_TreeNodeCMS WHERE AD_Tree_ID=? ORDER BY Node_ID";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, AD_Tree_ID);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MTree_NodeCMS (ctx, rs, trxName));
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		MTree_NodeCMS[] retValue = new MTree_NodeCMS[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getTree

	/**
	 * 	Get Tree Node
	 *	@param tree tree
	 *	@param Node_ID node
	 *	@return node or null
	 */
	public static MTree_NodeCMS get (MTree_Base tree, int Node_ID)
	{
		MTree_NodeCMS retValue = null;
		String sql = "SELECT * FROM AD_TreeNodeCMS WHERE AD_Tree_ID=? AND Node_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, tree.get_TrxName());
			pstmt.setInt (1, tree.getAD_Tree_ID());
			pstmt.setInt (2, Node_ID);
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = new MTree_NodeCMS (tree.getCtx(), rs, tree.get_TrxName());
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, "get", e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		return retValue;
	}	//	get

	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MTree_NodeCMS.class);

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MTree_NodeCMS (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MTree_NodeCMS

	/**
	 * 	Full Constructor
	 *	@param tree tree
	 *	@param Node_ID node
	 */
	public MTree_NodeCMS (MTree_Base tree, int Node_ID)
	{
		super (tree.getCtx(), 0, tree.get_TrxName());
		setClientOrg(tree);
		setAD_Tree_ID (tree.getAD_Tree_ID());
		setNode_ID(Node_ID);
		//	Add to root
		setParent_ID(0);
		setSeqNo (0);
	}	//	MTree_NodeCMS
	
}	//	MTree_NodeCMS
