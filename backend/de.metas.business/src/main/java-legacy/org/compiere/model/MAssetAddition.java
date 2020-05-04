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
import java.util.Properties;

import org.compiere.util.DB;

/**
 *  Asset Addition Model
 *
 *
 */
public class MAssetAddition extends X_A_Asset_Addition
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 511552459407382309L;

	/**
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param M_InventoryLine_ID line
	 */
	public MAssetAddition (Properties ctx, int A_Asset_Addition_ID, String trxName)
	{
		super (ctx, A_Asset_Addition_ID, trxName);
		if (A_Asset_Addition_ID == 0)
		{
		//
			
		}
	}	//	MAssetAddition
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public MAssetAddition (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MAAssetAddition

		/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		log.info("beforeSave");
		int		p_A_Asset_ID = 0;
		p_A_Asset_ID = getA_Asset_ID();		
		String sql = "SELECT COUNT(*) FROM A_Depreciation_Workfile WHERE A_Asset_ID=?";		
		if (DB.getSQLValue(null,sql, p_A_Asset_ID) == 0)
        {
			X_A_Depreciation_Workfile assetwk = new X_A_Depreciation_Workfile (getCtx(), 0, null);
			assetwk.setA_Asset_ID(p_A_Asset_ID);
			assetwk.setPostingType("A");
            assetwk.setA_QTY_Current(getA_QTY_Current());            
            assetwk.setA_Asset_Cost(getAssetValueAmt());            
            assetwk.save();
            
            MAsset asset = new MAsset (getCtx(), p_A_Asset_ID, null);
            asset.setA_QTY_Original(getA_QTY_Current().add(asset.getA_QTY_Original()));
            asset.setA_QTY_Current(getA_QTY_Current().add(asset.getA_QTY_Current()));
            asset.save();
            
            MAssetChange change = new MAssetChange (getCtx(), 0,null);
            change.setA_Asset_ID(p_A_Asset_ID);            
            change.setA_QTY_Current(getA_QTY_Current());           
            change.setChangeType("ADD");
            MRefList RefList = new MRefList (getCtx(), 0, null);	
        	change.setTextDetails(RefList.getListDescription (getCtx(),"A_Update_Type" , "ADD"));
            change.setPostingType("A");
            change.setAssetValueAmt(getAssetValueAmt());
            change.setA_QTY_Current(getA_QTY_Current());            
            change.save();
            
            
        }
		else
		{
			sql ="SELECT * FROM A_Depreciation_Workfile WHERE A_Asset_ID=? AND IsActive='Y'";
			PreparedStatement pstmt = null;
			try
			{
			pstmt = DB.prepareStatement(sql,null);
			pstmt.setInt(1, p_A_Asset_ID);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()){
				//MADepreciationWorkfile asset = new MADepreciationWorkfile (getCtx(), rs.getInt("A_Depreciation_Workfile_ID"));
				X_A_Depreciation_Workfile assetwk = new X_A_Depreciation_Workfile (getCtx(), rs, null);
				assetwk.setA_Asset_Cost(getAssetValueAmt().add(assetwk.getA_Asset_Cost()));
				assetwk.setA_QTY_Current(getA_QTY_Current().add(assetwk.getA_QTY_Current()));				
				assetwk.save();
				
				MAssetChange change = new MAssetChange (getCtx(), 0, null);
	            change.setA_Asset_ID(p_A_Asset_ID);            
	            change.setA_QTY_Current(getA_QTY_Current());
	            change.setChangeType("ADD");
	            MRefList RefList = new MRefList (getCtx(), 0, null);	
	        	change.setTextDetails(RefList.getListDescription (getCtx(),"A_Update_Type" , "ADD"));
	            change.setPostingType(rs.getString("PostingType"));
	            change.setAssetValueAmt(getAssetValueAmt());
	            change.setA_QTY_Current(getA_QTY_Current());	            
	            change.save();
	            
	            MAsset asset = new MAsset (getCtx(), p_A_Asset_ID, null);
	            asset.setA_QTY_Original(getA_QTY_Current().add(asset.getA_QTY_Original()));
	            asset.setA_QTY_Current(getA_QTY_Current().add(asset.getA_QTY_Current()));
	            asset.setProcessing(false);
	            asset.save();
			}
			rs.close();
			pstmt.close();
			pstmt = null;
			}
			catch (Exception e)
			{
				log.info("getAssets"+ e);
			}
			finally
			{
				try
				{
					if (pstmt != null)
						pstmt.close ();
				}
				catch (Exception e)
				{}
				pstmt = null;
			}
		}       		
		return true;		
	}	//	beforeSave


}	//	MAssetAddition
