/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is                  Compiere  ERP & CRM  Business Solution
 * The Initial Developer of the Original Code is Jorg Janke  and ComPiere, Inc.
 *
 * Copyright (C) 2005 Robert KLEIN. robeklein@gmail.com * 
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.compiere.FA;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MAssetChange;
import org.compiere.model.MRefList;
import org.compiere.model.X_A_Asset_Disposed;
import org.compiere.model.X_A_Depreciation_Exp;
import org.compiere.util.DB;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;


/**
 *	Dispose Asset
 *	
 *  @author Rob klein
 *  @version $Id: AssetDisposed.java,v 1.0$
 */
public class AssetDisposed extends SvrProcess
{
	/** Record ID				*/
	private int p_Asset_Disposed_ID = 0;
	private boolean			m_DeleteOld = false;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DeleteOld"))
				m_DeleteOld = "Y".equals(para[i].getParameter());
			else
				log.info("prepare - Unknown Parameter: " + name);
		}
		p_Asset_Disposed_ID = getRecord_ID();
		
		
	}	//	prepare

	
	/**
	 * 	Build Depreciation Workfile
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		log.info("doIt - Asset_Disposed_ID=" + p_Asset_Disposed_ID);
		if (p_Asset_Disposed_ID == 0)
			throw new IllegalArgumentException("No Record");
		
		String sql = null;
		
		//
		int no = 0;
		BigDecimal v_Balance = new BigDecimal("0.0");		
		X_A_Asset_Disposed AssetDisposed = new X_A_Asset_Disposed (getCtx(), p_Asset_Disposed_ID, null);		
		String clientCheck = " AND AD_Client_ID=" + AssetDisposed.getAD_Client_ID();
		
		if (m_DeleteOld)
		{
			sql =  "DELETE FROM A_DEPRECIATION_EXP "
				  + "WHERE Processed='Y'" 
				  + " AND A_Entry_Type = 'DIS'" + clientCheck;
			
			no = DB.executeUpdate (sql,null);
			log.info("doIt - Delete old processed entries =" + no);
		}
		
		sql = null;
		log.info("doIt - Starting Disposal = " + no);
		
		sql = "SELECT A.A_ASSET_ID, A.POSTINGTYPE, A.A_DEPRECIATION_ACCT, "
			+ " A.A_ACCUMDEPRECIATION_ACCT, A.A_DISPOSAL_LOSS, A.A_DISPOSAL_REVENUE, "
			+ " A.A_ASSET_ACCT, A.A_SPLIT_PERCENT, A.AD_ORG_ID, A.AD_CLIENT_ID, "
			+ " B.A_ASSET_COST, B.A_ACCUMULATED_DEPR "
			+ " FROM A_ASSET_ACCT A,  A_DEPRECIATION_WORKFILE B "
			+ " WHERE A.A_ASSET_ID = " + AssetDisposed.getA_Asset_ID()
			+ " and B.A_ASSET_ID = " + AssetDisposed.getA_Asset_ID()
			+ " and A.POSTINGTYPE = B.POSTINGTYPE"
			+ " and A.AD_CLIENT_ID = B.AD_CLIENT_ID";
			
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement (sql,null);
		log.info("doIt - SQL=" + sql);
		String v_PostingType = null;
		//X_A_Depreciation_Exp depexp = new X_A_Depreciation_Exp (getCtx(), 0, null);
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();		
			while (rs.next()){
			if (v_PostingType != null && (!v_PostingType.equals(rs.getString("PostingType"))))
			{
			      sql = "UPDATE A_DEPRECIATION_WORKFILE "
			      	  + "SET A_ACCUMULATED_DEPR = " + v_Balance 
			    	  + "WHERE A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + AssetDisposed.getA_Asset_ID()
			    	  + "AND A_DEPRECIATION_WORKFILE.POSTINGTYPE = '" + v_PostingType + "'";
			      DB.executeUpdate(sql,null);
			      
			      v_Balance = new BigDecimal("0.0");
			      v_PostingType = rs.getString("PostingType");
			}
			else if (v_PostingType == null)
			{
			      v_PostingType = rs.getString("PostingType");
			}		

			// Create JV for the asset disposal - remove cost of asset on balance sheet 
			X_A_Depreciation_Exp depexp0 = new X_A_Depreciation_Exp (getCtx(), 0, null);
			depexp0.setPostingType(rs.getString("PostingType"));
			depexp0.setA_Asset_ID(AssetDisposed.getA_Asset_ID());			
			depexp0.setExpense(rs.getBigDecimal("A_Asset_Cost").multiply(new BigDecimal(-1)).multiply(rs.getBigDecimal("A_Split_Percent")));
			depexp0.setDateAcct(AssetDisposed.getDateAcct());
			depexp0.setA_Account_Number(rs.getInt("A_Asset_Acct"));
			depexp0.setDescription("Asset Disposed - Cost of Asset");
			depexp0.setIsDepreciated(true);
			depexp0.setA_Period(AssetDisposed.getC_Period_ID());
			depexp0.setA_Entry_Type("DIS");
			depexp0.save();
			
			X_A_Depreciation_Exp depexp1 = new X_A_Depreciation_Exp (getCtx(), 0, null);
			depexp1.setPostingType(rs.getString("PostingType"));
			depexp1.setA_Asset_ID(AssetDisposed.getA_Asset_ID());			
			depexp1.setExpense(rs.getBigDecimal("A_Asset_Cost").multiply(rs.getBigDecimal("A_Split_Percent")));
			depexp1.setDateAcct(AssetDisposed.getDateAcct());
			depexp1.setA_Account_Number(rs.getInt("A_Disposal_Loss"));
			depexp1.setDescription("Asset Disposed - Cost of Asset");
			depexp1.setIsDepreciated(false);
			depexp1.setA_Period(AssetDisposed.getC_Period_ID());
			depexp1.setA_Entry_Type("DIS");
			depexp1.save();
			
			v_Balance = v_Balance.add(rs.getBigDecimal("A_Asset_Cost").multiply(rs.getBigDecimal("A_Split_Percent")));
			
			// Create JV for the asset disposal - remove accumulated depreciation of the asset on balance sheet 
			X_A_Depreciation_Exp depexp2 = new X_A_Depreciation_Exp (getCtx(), 0, null);
			depexp2.setPostingType(rs.getString("PostingType"));
			depexp2.setA_Asset_ID(AssetDisposed.getA_Asset_ID());			
			depexp2.setExpense(rs.getBigDecimal("A_Accumulated_Depr").multiply(rs.getBigDecimal("A_Split_Percent")));
			depexp2.setDateAcct(AssetDisposed.getDateAcct());
			depexp2.setA_Account_Number(rs.getInt("A_Accumdepreciation_Acct"));
			depexp2.setDescription("Asset Disposed - Accum Depr");
			depexp2.setIsDepreciated(true);
			depexp2.setA_Period(AssetDisposed.getC_Period_ID());
			depexp2.setA_Entry_Type("DIS");
			depexp2.save();
			
			X_A_Depreciation_Exp depexp3 = new X_A_Depreciation_Exp (getCtx(), 0, null);
			depexp3.setPostingType(rs.getString("PostingType"));
			depexp3.setA_Asset_ID(AssetDisposed.getA_Asset_ID());			
			depexp3.setExpense(rs.getBigDecimal("A_Accumulated_Depr").multiply(new BigDecimal(-1)).multiply(rs.getBigDecimal("A_Split_Percent")));
			depexp3.setDateAcct(AssetDisposed.getDateAcct());
			depexp3.setA_Account_Number(rs.getInt("A_Disposal_Loss"));
			depexp3.setDescription("Asset Disposed - Accum Depr");
			depexp3.setIsDepreciated(false);
			depexp3.setA_Period(AssetDisposed.getC_Period_ID());
			depexp3.setA_Entry_Type("DIS");
			depexp3.save();			
			}
			
			sql = "UPDATE A_ASSET "
			    + "SET ISDISPOSED = 'Y',"
			    + " ASSETDISPOSALDATE = " + DB.TO_DATE(AssetDisposed.getA_Disposed_Date())
			    + " WHERE A_ASSET_ID = " + AssetDisposed.getA_Asset_ID();
			
			DB.executeUpdate(sql,null);
			
			sql = "UPDATE A_DEPRECIATION_WORKFILE "
		      	  + "SET A_ACCUMULATED_DEPR = " + v_Balance 
		    	  + "WHERE A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + AssetDisposed.getA_Asset_ID()
		    	  + "AND A_DEPRECIATION_WORKFILE.POSTINGTYPE = '" + v_PostingType + "'";
		    DB.executeUpdate(sql,null);
		    
		    MAssetChange change = new MAssetChange (getCtx(), 0, null);
			change.setA_Asset_ID(AssetDisposed.getA_Asset_ID());			
			change.setChangeType("DIS");
			change.setTextDetails(MRefList.getListDescription (getCtx(),"A_Update_Type" , "DIS"));   
			change.setAssetDisposalDate(AssetDisposed.getA_Disposed_Date());
			change.setAssetAccumDepreciationAmt(v_Balance);
			change.setIsFullyDepreciated(true);
			change.setIsDisposed(true);
			change.save();
			
		}
			catch (Exception e)
			{
				log.info("getDeliveries"+ e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;

			}
			
						return "";
	}	//	doIt
	
}	//	AssetDisposed
