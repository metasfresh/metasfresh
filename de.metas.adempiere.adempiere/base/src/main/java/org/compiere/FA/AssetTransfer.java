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
import org.compiere.model.X_A_Asset_Transfer;
import org.compiere.model.X_A_Depreciation_Exp;
import org.compiere.util.DB;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 *	Asset Transfer
 *	
 *  @author Rob Klein
 *  @version $Id: AssetTransfer.java,v 1.0 $
 */
public class AssetTransfer extends JavaProcess
{
	/** Record ID				*/
	private int p_Asset_Transfer_ID = 0;
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
		p_Asset_Transfer_ID = getRecord_ID();
	}	//	prepare

	
	/**
	 * 	Build Depreciation Workfile
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		log.info("doIt - Asset_Transfer_ID=" + p_Asset_Transfer_ID);
		if (p_Asset_Transfer_ID == 0)
			throw new IllegalArgumentException("No Record");
		
		//
		int no = 0;
		BigDecimal v_Balance = new BigDecimal("0.0");
		
		X_A_Asset_Transfer AssetTransfer = new X_A_Asset_Transfer (getCtx(), p_Asset_Transfer_ID, null);
		//X_C_Period Period = new X_C_Period (getCtx(), AssetTransfer.getC_Period_ID(), null);
		
		String sql = null;
		log.info("doIt - Starting Transfer = " + no);
		
		sql = "SELECT A_ASSET_ID, CHANGEAMT "
			+ "FROM A_ASSET_CHANGE "
			+ "WHERE A_ASSET_CHANGE.A_ASSET_ID = " + AssetTransfer.getA_Asset_ID() 
			+ " AND A_ASSET_CHANGE.POSTINGTYPE = '" + AssetTransfer.getPostingType() 
			+ "' AND A_ASSET_CHANGE.CHANGETYPE= 'D' "
			+ "AND TRUNC(A_ASSET_CHANGE.DATEACCT, 'YY') = TRUNC( " + DB.TO_DATE(AssetTransfer.getDateAcct()) + ", 'YY') "
			+ "AND TRUNC(A_ASSET_CHANGE.DATEACCT, 'MM') <= TRUNC( " + DB.TO_DATE(AssetTransfer.getDateAcct()) + ", 'MM') "
			+ "AND A_ASSET_CHANGE.C_VALIDCOMBINATION_ID = " + AssetTransfer.getA_Depreciation_Acct();
				
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement (sql,null);
		log.info("doIt - SQL=" + sql);
		ResultSet rs = null;		
		//X_A_Depreciation_Exp depexp = new X_A_Depreciation_Exp (getCtx(), 0, null);
		
		String clientCheck = " AND AD_Client_ID=" + AssetTransfer.getAD_Client_ID();
		
		if (m_DeleteOld)
		{
			sql =  "DELETE FROM A_DEPRECIATION_EXP "
				  + "WHERE Processed='Y'" 
				  + " AND A_Entry_Type = 'TRN'" + clientCheck;
			
			no = DB.executeUpdate (sql,null);
			log.info("doIt - Delete old processed entries =" + no);
		}
		try {
			
			rs = pstmt.executeQuery();			
			if (AssetTransfer.isA_Transfer_Balance_IS()==true)
			{
			while (rs.next());
				v_Balance = v_Balance.add(rs.getBigDecimal("ChangeAmt"));
						
			// Create JV for the Income Statement Amounts
			X_A_Depreciation_Exp depexp0 = new X_A_Depreciation_Exp (getCtx(), 0, null);
			depexp0.setPostingType(AssetTransfer.getPostingType());
			depexp0.setA_Asset_ID(AssetTransfer.getA_Asset_ID());			
			depexp0.setExpense(v_Balance);
			depexp0.setDateAcct(AssetTransfer.getDateAcct());
			depexp0.setA_Account_Number(AssetTransfer.getA_Depreciation_Acct_New());
			depexp0.setDescription("Asset Transfer");
			depexp0.setIsDepreciated(false);
			depexp0.setA_Period(AssetTransfer.getC_Period_ID());
			depexp0.setA_Entry_Type("TRN");
			depexp0.save();
			
			X_A_Depreciation_Exp depexp1 = new X_A_Depreciation_Exp (getCtx(), 0, null);
			depexp1.setPostingType(AssetTransfer.getPostingType());
			depexp1.setA_Asset_ID(AssetTransfer.getA_Asset_ID());
			depexp1.setExpense(v_Balance.multiply(new BigDecimal(-1)));
			depexp1.setDateAcct(AssetTransfer.getDateAcct());
			depexp1.setA_Account_Number(AssetTransfer.getA_Depreciation_Acct_New());
			depexp1.setDescription("Asset Transfer");
			depexp1.setIsDepreciated(false);
			depexp1.setA_Period(AssetTransfer.getC_Period_ID());			
			depexp1.setA_Entry_Type("TRN");
			depexp1.save();
				}
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
			
			sql = null;
			
			sql = "SELECT A_ASSET_COST, A_ACCUMULATED_DEPR "				
				+ "FROM A_DEPRECIATION_WORKFILE "
				+ "WHERE  A_ASSET_ID = " + AssetTransfer.getA_Asset_ID() 
				+ " AND POSTINGTYPE = '" + AssetTransfer.getPostingType() 
				+ "' AND AD_CLIENT_ID = " + AssetTransfer.getAD_Client_ID() 
				+ " AND AD_ORG_ID = " + AssetTransfer.getAD_Org_ID();
					
			pstmt = null;
			pstmt = DB.prepareStatement (sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE,null);
			
			try {			
				rs = pstmt.executeQuery();
				rs.first();
				
				X_A_Depreciation_Exp depexp2 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				
				// Create JV for the accumulated depreciation of the asset 			
				depexp2.setPostingType(AssetTransfer.getPostingType());
				depexp2.setA_Asset_ID(AssetTransfer.getA_Asset_ID());			
				depexp2.setExpense(rs.getBigDecimal("A_ACCUMULATED_DEPR"));
				depexp2.setDateAcct(AssetTransfer.getDateAcct());
				depexp2.setA_Account_Number(AssetTransfer.getA_Accumdepreciation_Acct_New());
				depexp2.setDescription("Asset Transfer Accum Dep");
				depexp2.setIsDepreciated(false);
				depexp2.setA_Period(AssetTransfer.getC_Period_ID());
				depexp2.setA_Entry_Type("TRN");
				depexp2.save();			
				
				X_A_Depreciation_Exp depexp3 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				depexp3.setPostingType(AssetTransfer.getPostingType());
				depexp3.setA_Asset_ID(AssetTransfer.getA_Asset_ID());
				depexp3.setExpense(rs.getBigDecimal("A_ACCUMULATED_DEPR").multiply(new BigDecimal(-1)));
				depexp3.setDateAcct(AssetTransfer.getDateAcct());
				depexp3.setA_Account_Number(AssetTransfer.getA_Accumdepreciation_Acct());
				depexp3.setDescription("Asset Transfer Accum Dep");
				depexp3.setIsDepreciated(false);
				depexp3.setA_Period(AssetTransfer.getC_Period_ID());			
				depexp3.setA_Entry_Type("TRN");
				depexp3.save();
				
				X_A_Depreciation_Exp depexp4 = new X_A_Depreciation_Exp (getCtx(), 0, null);
//				 Create JV for the Cost of the asset 			
				depexp4.setPostingType(AssetTransfer.getPostingType());
				depexp4.setA_Asset_ID(AssetTransfer.getA_Asset_ID());			
				depexp4.setExpense(rs.getBigDecimal("A_Asset_Cost"));
				depexp4.setDateAcct(AssetTransfer.getDateAcct());
				depexp4.setA_Account_Number(AssetTransfer.getA_Asset_Acct_New());
				depexp4.setDescription("Asset Transfer Cost");
				depexp4.setIsDepreciated(false);
				depexp4.setA_Period(AssetTransfer.getC_Period_ID());
				depexp4.setA_Entry_Type("TRN");
				depexp4.save();			
			
				X_A_Depreciation_Exp depexp5 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				depexp5.setPostingType(AssetTransfer.getPostingType());
				depexp5.setA_Asset_ID(AssetTransfer.getA_Asset_ID());
				depexp5.setExpense(rs.getBigDecimal("A_Asset_Cost").multiply(new BigDecimal(-1)));
				depexp5.setDateAcct(AssetTransfer.getDateAcct());
				depexp5.setA_Account_Number(AssetTransfer.getA_Asset_Acct());
				depexp5.setDescription("Asset Transfer Cost");
				depexp5.setIsDepreciated(false);
				depexp5.setA_Period(AssetTransfer.getC_Period_ID());			
				depexp5.setA_Entry_Type("TRN");
				depexp5.save();
				
			
				 // Update Asset Setup for New Accounts
				sql = null;
				
				sql = "UPDATE A_ASSET_ACCT "
				    + "SET A_DEPRECIATION_ACCT = " + AssetTransfer.getA_Depreciation_Acct_New()  
				    + ", A_ACCUMDEPRECIATION_ACCT = " + AssetTransfer.getA_Accumdepreciation_Acct_New()
				    + ", A_DISPOSAL_LOSS = " + AssetTransfer.getA_Disposal_Loss_New() 
				    + ", A_DISPOSAL_REVENUE = " + AssetTransfer.getA_Disposal_Revenue_New() 
				    + ", A_ASSET_ACCT = "+ AssetTransfer.getA_Asset_Acct_New() 
				    + " WHERE A_ASSET_ID = " + AssetTransfer.getA_Asset_ID() 
				    + " AND POSTINGTYPE = '" + AssetTransfer.getPostingType()  
				    + "' AND A_PERIOD_START = " + AssetTransfer.getA_Period_Start()  
				    + " AND A_PERIOD_END = " + AssetTransfer.getA_Period_End();
				
				
				MAssetChange change = new MAssetChange (getCtx(), 0, null);
				change.setChangeType("TRN");	
				MRefList RefList = new MRefList (getCtx(), 0, null);	
				change.setTextDetails(RefList.getListDescription (getCtx(),"A_Update_Type" , "TRN"));    
				change.setPostingType(AssetTransfer.getPostingType());
				change.setA_Split_Percent(AssetTransfer.getA_Split_Percent());
				change.setA_Asset_ID(AssetTransfer.getA_Asset_ID());				
				change.setA_Period_Start(AssetTransfer.getA_Period_Start());
				change.setA_Period_End(AssetTransfer.getA_Period_End());				
				change.setA_Asset_Acct(AssetTransfer.getA_Asset_Acct_New());
				change.setC_AcctSchema_ID(AssetTransfer.getC_AcctSchema_ID());
				change.setA_Accumdepreciation_Acct(AssetTransfer.getA_Accumdepreciation_Acct_New());
				change.setA_Depreciation_Acct(AssetTransfer.getA_Depreciation_Acct_New());
				change.setA_Disposal_Revenue(AssetTransfer.getA_Disposal_Revenue_New());
				change.setA_Disposal_Loss(AssetTransfer.getA_Disposal_Loss_New());
				change.setAssetAccumDepreciationAmt(rs.getBigDecimal("A_ACCUMULATED_DEPR"));
				change.setAssetBookValueAmt(rs.getBigDecimal("A_Asset_Cost"));
				change.setChangeAmt(v_Balance);
				change.save();
				
				DB.executeUpdate(sql,null);
				
				AssetTransfer.setIsActive(false);
				AssetTransfer.save();

				//  Remove Entry from Processing file
				
				}
				catch (Exception e)
				{
					log.info("TransferAssets"+ e);
				}
				finally
				{
				  DB.close(rs, pstmt);
				  rs = null; pstmt = null;
				}
			return "";
	}	//	doIt
	
}	//	AssetTransfer
