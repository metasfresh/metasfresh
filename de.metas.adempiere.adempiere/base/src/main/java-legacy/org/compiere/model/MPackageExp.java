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
 * Contributor(s): ______________________________________.                    *
 *****************************************************************************/
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.DB;

/**
 *	Package Export Model
 *	
 *  @author Rob Klein
 *  @version $Id: MMenu.java,v 1.0 2006/01/07 Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1826273 ] Error when creating MPackageExp
 */
public class MPackageExp extends X_AD_Package_Exp
{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8923634972273479831L;

	/**
	 * 	MPackageExp
	 *	@param ctx
	 *	@param int
	 */
	public MPackageExp (Properties ctx, int AD_Package_Exp_ID, String trxName)
	{
		super(ctx, AD_Package_Exp_ID, trxName);		
		
	}	//	MPackageExp

	/**
	 * 	MPackageExp
	 *	@param ctx
	 *	@param rs
	 */
	public MPackageExp (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);		
		
	}	//	MPackageExp
	
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return false;
		//
		String sql = "SELECT count(*) FROM AD_Package_Exp_Detail WHERE AD_Package_Exp_ID = ?";
		int recordCount = DB.getSQLValue(get_TrxName(), sql, getAD_Package_Exp_ID());
		
		if (recordCount == 0){
			sql = "SELECT * FROM AD_Package_Exp_Common";
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery ();
				int i = 1;
				while (rs.next()) {
					X_AD_Package_Exp_Detail PackDetail =new X_AD_Package_Exp_Detail(getCtx(), 0, null);
					PackDetail.setAD_Client_ID(this.getAD_Client_ID());
					PackDetail.setAD_Org_ID(this.getAD_Org_ID());
					PackDetail.setAD_Package_Exp_ID(getAD_Package_Exp_ID());					
					PackDetail.setType(rs.getString("TYPE"));
					PackDetail.setFileName(rs.getString("FILENAME"));
					PackDetail.setDescription(rs.getString("DESCRIPTION"));
					PackDetail.setTarget_Directory(rs.getString("TARGET_DIRECTORY"));
					PackDetail.setFile_Directory(rs.getString("FILE_DIRECTORY"));
					PackDetail.setDestination_Directory(rs.getString("DESTINATION_DIRECTORY"));
					PackDetail.setSQLStatement(rs.getString("SQLSTATEMENT"));
					PackDetail.setAD_Workflow_ID(rs.getInt("AD_WORKFLOW_ID"));
					PackDetail.setAD_Window_ID(rs.getInt("AD_WINDOW_ID"));
					PackDetail.setAD_Role_ID(rs.getInt("AD_ROLE_ID"));
					PackDetail.setAD_Process_ID(rs.getInt("AD_PROCESS_ID"));
					PackDetail.setAD_Menu_ID(rs.getInt("AD_MENU_ID"));
					PackDetail.setDBType(rs.getString("DBTYPE"));
					PackDetail.setAD_ImpFormat_ID(rs.getInt("AD_IMPFORMAT_ID"));
					PackDetail.setAD_Workbench_ID(rs.getInt("AD_WORKBENCH_ID"));
					PackDetail.setAD_Table_ID(rs.getInt("AD_TABLE_ID"));
					PackDetail.setAD_Form_ID(rs.getInt("AD_FORM_ID"));
					PackDetail.setAD_ReportView_ID(rs.getInt("AD_REPORTVIEW_ID"));
					PackDetail.setLine(i*10);
					PackDetail.save();
					i++;
				}						
			}
			catch (Exception e)
			{
				log.error(sql, e);
			}
			finally {
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
		}
		
		return true;
	}	//	afterSave

	/**
	 * 	Before Delete
	 *	@param success
	 *	@return deleted
	 */
	@Override
	protected boolean afterDelete (boolean success)
	{
	 String sql = "DELETE FROM AD_Package_Exp_Detail WHERE AD_Package_Exp_ID = "+ getAD_Package_Exp_ID();
	 
	 int deleteSuccess = DB.executeUpdate(sql, get_TrxName());
	 if (deleteSuccess == -1)
		return false;
	 return true;
	}	//	afterDelete
	
}	//	MPackageExp
