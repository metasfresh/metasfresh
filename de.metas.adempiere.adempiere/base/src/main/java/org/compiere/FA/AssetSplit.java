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
import org.compiere.model.X_A_Asset;
import org.compiere.model.X_A_Asset_Acct;
import org.compiere.model.X_A_Asset_Addition;
import org.compiere.model.X_A_Asset_Split;
import org.compiere.model.X_A_Depreciation_Exp;
import org.compiere.model.X_A_Depreciation_Workfile;
import org.compiere.util.DB;

import de.metas.process.JavaProcess;


/**
 *	Split an Asset
 *	
 *  @author Rob Klein
 *  @version $Id: AssetSplit,v 1.0 $
 */
public class AssetSplit extends JavaProcess
{
	/** Record ID				*/
	private int p_Asset_Split_ID = 0;
	//private boolean			m_DeleteOld = false;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		//ProcessInfoParameter[] para = getParameter();
		p_Asset_Split_ID = getRecord_ID();
	}	//	prepare

	
	/**
	 * 	Transfer Asset
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		log.info("doIt - Asset_Split_ID=" + p_Asset_Split_ID);
		if (p_Asset_Split_ID == 0)
			throw new IllegalArgumentException("No Record");
		
		//
		int no = 0;
		BigDecimal v_AccumBalance_New = new BigDecimal("0.0");
		BigDecimal v_CostBalance_New = new BigDecimal("0.0");
		BigDecimal v_QTY_New = new BigDecimal("0.0");
		BigDecimal v_SalvageValue_New = new BigDecimal("0.0");
		BigDecimal v_AccumBalance_Org = new BigDecimal("0.0");
		BigDecimal v_CostBalance_Org = new BigDecimal("0.0");
		BigDecimal v_QTY_Org = new BigDecimal("0.0");
		BigDecimal v_SalvageValue_Org = new BigDecimal("0.0");
		BigDecimal v_multiplier_New = new BigDecimal("0.0");
		BigDecimal v_ManDep_Org = new BigDecimal("0.0");
		BigDecimal v_ManDep_New = new BigDecimal("0.0");
		
		int v_AssetNumber = 0, A_Accumdepreciation_Acct = 0;
		int A_Cost_Acct = 0, A_DepExp_Acct = 0;
		
		log.info("doIt - Starting Split = " + no);
		
		X_A_Asset_Split AssetSplit = new X_A_Asset_Split (getCtx(), p_Asset_Split_ID, null);
		X_A_Asset Asset = new X_A_Asset (getCtx(), AssetSplit.getA_Asset_ID(), null);		
		X_A_Depreciation_Workfile assetwk = new X_A_Depreciation_Workfile (getCtx(), AssetSplit.getA_Depreciation_Workfile_ID(), null);		
		X_A_Asset_Acct assetacct = new X_A_Asset_Acct (getCtx(), AssetSplit.getA_Asset_Acct_ID(), null);
		
		v_AccumBalance_Org = assetwk.getA_Accumulated_Depr(); 		
		v_SalvageValue_Org = assetwk.getA_Salvage_Value();
		v_CostBalance_Org = assetwk.getA_Asset_Cost();		
		v_QTY_Org = AssetSplit.getA_QTY_Current();
		v_ManDep_Org = assetacct.getA_Depreciation_Manual_Amount();
			
			if (AssetSplit.getA_Split_Type().compareTo("PER") == 0){
			  v_multiplier_New = AssetSplit.getA_Percent_Split(); 
		      v_AccumBalance_New = v_AccumBalance_Org.multiply(AssetSplit.getA_Percent_Split());
		      v_CostBalance_New = v_CostBalance_Org.multiply(AssetSplit.getA_Percent_Split());
		      v_QTY_New = v_QTY_Org.multiply(AssetSplit.getA_Percent_Split());
		      v_SalvageValue_New = v_SalvageValue_Org.multiply(AssetSplit.getA_Percent_Split());	      
		      v_AccumBalance_Org = v_AccumBalance_Org.subtract(v_AccumBalance_New);
		      v_CostBalance_Org = v_CostBalance_Org.subtract(v_CostBalance_New);
		      v_QTY_Org = v_QTY_Org.subtract(v_QTY_New);
		      v_SalvageValue_Org = v_SalvageValue_Org.subtract(v_SalvageValue_New);
		      v_ManDep_New = v_ManDep_Org.multiply(AssetSplit.getA_Percent_Split());
		      v_ManDep_Org =v_ManDep_Org.subtract(v_ManDep_New);
			}
			else if (AssetSplit.getA_Split_Type().compareTo("QTY")==0) {
		      v_multiplier_New = AssetSplit.getA_QTY_Split().setScale(5).divide(v_QTY_Org.setScale(5), 0);
		      v_AccumBalance_New = v_AccumBalance_Org .multiply(v_multiplier_New);
		      v_CostBalance_New = v_CostBalance_Org .multiply(v_multiplier_New);		      
		      v_QTY_New = AssetSplit.getA_QTY_Split();
		      v_SalvageValue_New = v_SalvageValue_Org.multiply(v_multiplier_New);
		      v_AccumBalance_Org = v_AccumBalance_Org.subtract(v_AccumBalance_New);
		      v_CostBalance_Org = v_CostBalance_Org .subtract(v_CostBalance_New);
		      v_QTY_Org = v_QTY_Org .subtract(v_QTY_New);
		      v_SalvageValue_Org = v_SalvageValue_Org .subtract(v_SalvageValue_New);
		      v_ManDep_New = v_ManDep_Org.multiply(v_multiplier_New);
		      v_ManDep_Org =v_ManDep_Org.subtract(v_ManDep_New);
		    }
		    else if (AssetSplit.getA_Split_Type().compareTo("AMT")==0) {
		      v_multiplier_New = AssetSplit.getA_Amount_Split().setScale(5).divide(v_CostBalance_Org.setScale(5), 0);		      
		      v_AccumBalance_New = v_AccumBalance_Org .multiply(v_multiplier_New);
		      v_CostBalance_New = AssetSplit.getA_Amount_Split();
		      v_QTY_New = v_QTY_Org .multiply(v_multiplier_New);
		      v_SalvageValue_New = v_SalvageValue_Org.multiply(v_multiplier_New);
		      v_AccumBalance_Org = v_AccumBalance_Org.subtract(v_AccumBalance_New);
		      v_CostBalance_Org = v_CostBalance_Org.subtract(v_CostBalance_New);
		      v_QTY_Org = v_QTY_Org.subtract(v_QTY_New);
		      v_SalvageValue_Org = v_SalvageValue_Org.subtract(v_SalvageValue_New);
		      v_ManDep_New = v_ManDep_Org.multiply(v_multiplier_New);
		      v_ManDep_Org =v_ManDep_Org.subtract(v_ManDep_New);
		   }
		    
		    
		    if (AssetSplit.getA_Asset_ID_To() == 0)
		    {
			    //Insert New Asset
			    X_A_Asset AssetNew = new X_A_Asset (getCtx(), 0, null);
				AssetNew.setValue(Asset.getValue());
				AssetNew.setA_Parent_Asset_ID(Asset.getA_Asset_ID());
				AssetNew.setName(Asset.getName());
				AssetNew.setDescription(Asset.getDescription());
				AssetNew.setHelp(Asset.getHelp());
				AssetNew.setA_Asset_Group_ID(Asset.getA_Asset_Group_ID());
				AssetNew.setM_Product_ID(Asset.getM_Product_ID());
				AssetNew.setSerNo(Asset.getSerNo());
				AssetNew.setLot(Asset.getLot());
				AssetNew.setVersionNo(Asset.getVersionNo());
				AssetNew.setGuaranteeDate(Asset.getGuaranteeDate());
				AssetNew.setAssetServiceDate(Asset.getAssetServiceDate());
				AssetNew.setUseLifeMonths(Asset.getUseLifeMonths());
				AssetNew.setUseLifeYears(Asset.getUseLifeYears());
				AssetNew.setUseUnits(Asset.getUseUnits());
				AssetNew.setIsOwned(Asset.isOwned());
				AssetNew.setIsDepreciated(Asset.isDepreciated());
				AssetNew.setAssetDepreciationDate(Asset.getAssetDepreciationDate());
				AssetNew.setIsInPosession(Asset.isInPosession());			 
				AssetNew.setLocationComment( "Split from Asset #" +AssetSplit.getA_Asset_ID());
				AssetNew.setC_BPartner_ID(Asset.getC_BPartner_ID());
				AssetNew.setC_BPartner_Location_ID(Asset.getC_BPartner_Location_ID());
				AssetNew.setA_QTY_Current(v_QTY_New);
				AssetNew.setA_QTY_Original(v_QTY_New);				
				AssetNew.save();				
				
				
			    v_AssetNumber = AssetNew.getA_Asset_ID();
			    
				//Create Asset Addition Record
				X_A_Asset_Addition assetadd = new X_A_Asset_Addition (getCtx(), 0, null);	
				assetadd.setA_Asset_ID(AssetNew.getA_Asset_ID());
				assetadd.setAssetValueAmt(v_CostBalance_New ); 
				assetadd.setDescription("Split from Asset #" + AssetSplit.getA_Asset_ID());
				assetadd.setA_CapvsExp("Cap");
				assetadd.setA_SourceType("MAN");
				assetadd.setA_QTY_Current(v_QTY_New);
				assetadd.setPostingType(AssetSplit.getPostingType());
				assetadd.setM_InOutLine_ID(1);				
				assetadd.save();
				
				
				String sql2 = "SELECT A_Asset_Acct_ID "
					+ "FROM A_Asset_Acct "
					+ "WHERE A_Asset_ID= " + v_AssetNumber
					+ "AND PostingType = '" + AssetSplit.getPostingType() +"'";
		
				int  v_NewAssetAcctID = 0;
				v_NewAssetAcctID = DB.getSQLValue(null, sql2);
				
				//Insert the New Asset in the Account File
				X_A_Asset_Acct assetacctnew = new X_A_Asset_Acct (getCtx(), v_NewAssetAcctID, null);			  
				assetacctnew.setA_Asset_ID(v_AssetNumber);
				assetacctnew.setC_AcctSchema_ID(assetacct.getC_AcctSchema_ID());			  
				assetacctnew.setA_Depreciation_ID(assetacct.getA_Depreciation_ID());
				assetacctnew.setA_Depreciation_Acct(assetacct.getA_Depreciation_Acct());
				assetacctnew.setA_Accumdepreciation_Acct(assetacct.getA_Accumdepreciation_Acct());
				assetacctnew.setA_Disposal_Loss(assetacct.getA_Disposal_Loss());
				assetacctnew.setA_Disposal_Revenue(assetacct.getA_Disposal_Revenue());
				assetacctnew.setA_Asset_Acct(assetacct.getA_Asset_Acct());
				assetacctnew.setA_Asset_Spread_ID(assetacct.getA_Asset_Spread_ID());
				assetacctnew.setA_Depreciation_Method_ID(assetacct.getA_Depreciation_Method_ID());
				assetacctnew.setA_Period_Start(assetacct.getA_Period_Start());
				assetacctnew.setA_Period_End(assetacct.getA_Period_End());
				assetacctnew.setA_Split_Percent(assetacct.getA_Split_Percent());
				assetacctnew.setA_Reval_Cal_Method(assetacct.getA_Reval_Cal_Method());
				assetacctnew.setA_Salvage_Value(v_SalvageValue_New);
				assetacctnew.setPostingType(assetacct.getPostingType());						
				assetacctnew.setA_Depreciation_Conv_ID(assetacct.getA_Depreciation_Conv_ID());
				assetacctnew.setA_Depreciation_Manual_Amount(v_ManDep_New);
				assetacctnew.setA_Depreciation_Manual_Period(assetacct.getA_Depreciation_Manual_Period());
				if (assetacct.getA_Depreciation_Manual_Period() == null)
					assetacctnew.setA_Depreciation_Manual_Period(" ");
				else	
					assetacctnew.setA_Depreciation_Manual_Period(assetacct.getA_Depreciation_Manual_Period());				
				assetacctnew.save();
				
				
				sql2 = null;
				sql2 = "SELECT A_Depreciation_Workfile_ID "
					+ "FROM A_Depreciation_Workfile "
					+ "WHERE A_Asset_ID= " + v_AssetNumber
					+ " AND PostingType = '" + AssetSplit.getPostingType() +"'";
		
				int  v_NewWorkfileID = 0;
				v_NewWorkfileID = DB.getSQLValue(null, sql2);
				
							
				//Insert the New Asset in the Deprecation Workfile
				X_A_Depreciation_Workfile assetwknew = new X_A_Depreciation_Workfile (getCtx(), v_NewWorkfileID, null);				
				assetwknew.setA_Asset_ID(v_AssetNumber);
				assetwknew.setA_Asset_Cost(assetwknew.getA_Asset_Cost().add(v_CostBalance_New));				
				assetwknew.setA_Accumulated_Depr(v_AccumBalance_New);
				assetwknew.setA_Life_Period(assetwk.getA_Life_Period());				
				assetwknew.setA_Period_Posted(assetwk.getA_Period_Posted());
				assetwknew.setA_Salvage_Value(v_SalvageValue_New);				
				assetwknew.setA_Asset_Life_Years(assetwk.getA_Asset_Life_Years());
				assetwknew.setPostingType(assetwk.getPostingType());				
				assetwknew.setA_QTY_Current(assetwknew.getA_QTY_Current().add(v_QTY_New));
				assetwknew.setIsDepreciated(assetwk.isDepreciated());				
				assetwknew.setA_Asset_Life_Current_Year(assetwk.getA_Asset_Life_Current_Year());								
				assetwknew.setA_Curr_Dep_Exp(new BigDecimal (0.0));				
				assetwknew.save();
				
				
				//Record transaction in Asset History				
				MAssetChange change = new MAssetChange (getCtx(), 0, null);
				change.setAssetValueAmt(v_CostBalance_New );			
				change.setPostingType(assetacct.getPostingType());			
				change.setA_Asset_ID(AssetNew.getA_Asset_ID());
				change.setAssetAccumDepreciationAmt(v_AccumBalance_New);
				change.setA_Salvage_Value(v_SalvageValue_New);			
				change.setPostingType(assetacct.getPostingType());
				change.setA_Split_Percent(assetacct.getA_Split_Percent());
				change.setConventionType(assetacct.getA_Depreciation_Conv_ID());
				change.setA_Asset_ID(AssetNew.getA_Asset_ID());						
				change.setDepreciationType(assetacct.getA_Depreciation_ID());
				change.setA_Asset_Spread_Type(assetacct.getA_Asset_Spread_ID());
				change.setA_Period_Start(assetacct.getA_Period_Start());
				change.setA_Period_End(assetacct.getA_Period_End());
				change.setIsInPosession(AssetNew.isOwned());
				change.setIsDisposed(AssetNew.isDisposed());
				change.setIsDepreciated(AssetNew.isDepreciated());
				change.setIsFullyDepreciated(AssetNew.isFullyDepreciated());					
				change.setA_Depreciation_Calc_Type(assetacct.getA_Depreciation_Method_ID());
				change.setA_Asset_Acct(assetacct.getA_Asset_Acct());
				change.setC_AcctSchema_ID(assetacct.getC_AcctSchema_ID());
				change.setA_Accumdepreciation_Acct(assetacct.getA_Accumdepreciation_Acct());
				change.setA_Depreciation_Acct(assetacct.getA_Depreciation_Acct());
				change.setA_Disposal_Revenue(assetacct.getA_Disposal_Revenue());
				change.setA_Disposal_Loss(assetacct.getA_Disposal_Loss());
				change.setA_Reval_Accumdep_Offset_Cur(assetacct.getA_Reval_Accumdep_Offset_Cur());
				change.setA_Reval_Accumdep_Offset_Prior(assetacct.getA_Reval_Accumdep_Offset_Prior());
				if (assetacct.getA_Reval_Cal_Method() == null)
					change.setA_Reval_Cal_Method(" ");
				else
					change.setA_Reval_Cal_Method(assetacct.getA_Reval_Cal_Method());
				change.setA_Reval_Cost_Offset(assetacct.getA_Reval_Cost_Offset());
				change.setA_Reval_Cost_Offset_Prior(assetacct.getA_Reval_Cost_Offset_Prior());
				change.setA_Reval_Depexp_Offset(assetacct.getA_Reval_Depexp_Offset());
				change.setA_Depreciation_Manual_Amount(assetacct.getA_Depreciation_Manual_Amount());
				if (assetacct.getA_Depreciation_Manual_Period() == null)
					change.setA_Depreciation_Manual_Period(" ");
				else	
					change.setA_Depreciation_Manual_Period(assetacct.getA_Depreciation_Manual_Period());				
				change.setA_Depreciation_Table_Header_ID(assetacct.getA_Depreciation_Table_Header_ID());
				change.setA_Depreciation_Variable_Perc(assetacct.getA_Depreciation_Variable_Perc());
				change.setA_Parent_Asset_ID(Asset.getA_Parent_Asset_ID());
			    change.setChangeType("SPL");
			    MRefList RefList = new MRefList (getCtx(), 0, null);	
				change.setTextDetails(RefList.getListDescription (getCtx(),"A_Update_Type" , "SPL"));   
			    change.setLot(AssetNew.getLot());
				change.setSerNo(AssetNew.getSerNo());
				change.setVersionNo(AssetNew.getVersionNo());
			    change.setUseLifeMonths(AssetNew.getUseLifeMonths());
			    change.setUseLifeYears(AssetNew.getUseLifeYears());
			    change.setLifeUseUnits(AssetNew.getLifeUseUnits());
			    change.setAssetDisposalDate(AssetNew.getAssetDisposalDate());
			    change.setAssetServiceDate(AssetNew.getAssetServiceDate());
			    change.setC_BPartner_Location_ID(AssetNew.getC_BPartner_Location_ID());
			    change.setC_BPartner_ID(AssetNew.getC_BPartner_ID());		    
			    change.setA_QTY_Current(AssetNew.getA_QTY_Current());
			    change.setA_QTY_Original(AssetNew.getA_QTY_Original());				
			    change.save();
				
			    
			    //Record Account Numbers for JE's
			    A_Accumdepreciation_Acct = assetacctnew.getA_Accumdepreciation_Acct(); 
				A_Cost_Acct = assetacctnew.getA_Asset_Acct();
				A_DepExp_Acct = assetacctnew.getA_Depreciation_Acct();				
		    }
		    else
		    {
			    v_AssetNumber = AssetSplit.getA_Asset_ID_To();
			    
			    //Update Target Asset Record 
			    X_A_Asset AssetNew = new X_A_Asset (getCtx(), v_AssetNumber, null);
			    AssetNew.setA_QTY_Current(AssetNew.getA_QTY_Current().add(v_QTY_New));
				AssetNew.setA_QTY_Original(AssetNew.getA_QTY_Original().add(v_QTY_New));
				AssetNew.save();
				
			     //Create Asset Addition Record
				X_A_Asset_Addition assetadd = new X_A_Asset_Addition (getCtx(), 0, null);	
				assetadd.setA_Asset_ID(v_AssetNumber);
				assetadd.setAssetValueAmt(v_CostBalance_New ); 
				assetadd.setDescription("Split from Asset #" + AssetSplit.getA_Asset_ID());
				assetadd.setA_CapvsExp("Cap");
				assetadd.setA_SourceType("MAN");
				assetadd.setA_QTY_Current(v_QTY_New);
				assetadd.setPostingType(AssetSplit.getPostingType());
				assetadd.setM_InOutLine_ID(1);				
				assetadd.save();
				
				
				String sql2 = "SELECT A_Asset_Acct_ID "
					+ "FROM A_Asset_Acct "
					+ "WHERE A_Asset_ID= " + v_AssetNumber
					+ "AND PostingType = '" + AssetSplit.getPostingType() +"'";
		
				int  v_NewAssetAcctID = 0;
				v_NewAssetAcctID = DB.getSQLValue(null, sql2);
				
				//Update Target Asset in the Account File
				X_A_Asset_Acct assetacctnew = new X_A_Asset_Acct (getCtx(), v_NewAssetAcctID, null);			  
				assetacctnew.setA_Salvage_Value(assetacctnew.getA_Salvage_Value().add(v_SalvageValue_New));
				assetacctnew.setA_Depreciation_Manual_Amount(assetacctnew.getA_Depreciation_Manual_Amount().add(v_ManDep_New));				
				assetacctnew.save();
				
			    
				sql2 = null;
				sql2 = "SELECT A_Depreciation_Workfile_ID "
					+ "FROM A_Depreciation_Workfile "
					+ "WHERE A_Asset_ID= " + v_AssetNumber
					+ " AND PostingType = '" + AssetSplit.getPostingType() +"'";
		
				int  v_NewWorkfileID = 0;
				v_NewWorkfileID = DB.getSQLValue(null, sql2);
				
							
				//Update Target Asset in the Deprecation Workfile
				X_A_Depreciation_Workfile assetwknew = new X_A_Depreciation_Workfile (getCtx(), v_NewWorkfileID, null);				
				assetwknew.setA_Asset_Cost(assetwknew.getA_Asset_Cost().add(v_CostBalance_New));
				assetwknew.setA_Accumulated_Depr(assetwknew.getA_Accumulated_Depr().add(v_AccumBalance_New));				
				assetwknew.setA_Salvage_Value(assetwknew.getA_Salvage_Value().add(v_SalvageValue_New));				
				assetwknew.setA_QTY_Current(assetwknew.getA_QTY_Current().add(v_QTY_New));				
				assetwknew.save();
				
				
				//Record transaction in Asset History
				MAssetChange change = new MAssetChange (getCtx(), 0, null);
				change.setAssetValueAmt(v_CostBalance_New );			
				change.setPostingType(assetacct.getPostingType());			
				change.setA_Asset_ID(AssetNew.getA_Asset_ID());
				change.setAssetAccumDepreciationAmt(v_AccumBalance_New);
				change.setA_Salvage_Value(v_SalvageValue_New);			
				change.setPostingType(assetacct.getPostingType());
				change.setA_Split_Percent(assetacct.getA_Split_Percent());
				change.setConventionType(assetacct.getA_Depreciation_Conv_ID());
				change.setA_Asset_ID(AssetNew.getA_Asset_ID());						
				change.setDepreciationType(assetacct.getA_Depreciation_ID());
				change.setA_Asset_Spread_Type(assetacct.getA_Asset_Spread_ID());
				change.setA_Period_Start(assetacct.getA_Period_Start());
				change.setA_Period_End(assetacct.getA_Period_End());
				change.setIsInPosession(AssetNew.isOwned());
				change.setIsDisposed(AssetNew.isDisposed());
				change.setIsDepreciated(AssetNew.isDepreciated());
				change.setIsFullyDepreciated(AssetNew.isFullyDepreciated());					
				change.setA_Depreciation_Calc_Type(assetacct.getA_Depreciation_Method_ID());
				change.setA_Asset_Acct(assetacct.getA_Asset_Acct());
				change.setC_AcctSchema_ID(assetacct.getC_AcctSchema_ID());
				change.setA_Accumdepreciation_Acct(assetacct.getA_Accumdepreciation_Acct());
				change.setA_Depreciation_Acct(assetacct.getA_Depreciation_Acct());
				change.setA_Disposal_Revenue(assetacct.getA_Disposal_Revenue());
				change.setA_Disposal_Loss(assetacct.getA_Disposal_Loss());
				change.setA_Reval_Accumdep_Offset_Cur(assetacct.getA_Reval_Accumdep_Offset_Cur());
				change.setA_Reval_Accumdep_Offset_Prior(assetacct.getA_Reval_Accumdep_Offset_Prior());
				change.setA_Reval_Cal_Method(assetacct.getA_Reval_Cal_Method());
				change.setA_Reval_Cost_Offset(assetacct.getA_Reval_Cost_Offset());
				change.setA_Reval_Cost_Offset_Prior(assetacct.getA_Reval_Cost_Offset_Prior());
				change.setA_Reval_Depexp_Offset(assetacct.getA_Reval_Depexp_Offset());
				change.setA_Depreciation_Manual_Amount(assetacct.getA_Depreciation_Manual_Amount());
				if (assetacct.getA_Reval_Cal_Method() == null)
					change.setA_Reval_Cal_Method(" ");
				else	
					change.setA_Reval_Cal_Method(assetacct.getA_Reval_Cal_Method());
				if (assetacct.getA_Depreciation_Manual_Period() == null)
					change.setA_Depreciation_Manual_Period(" ");
				else	
					change.setA_Depreciation_Manual_Period(assetacct.getA_Depreciation_Manual_Period());
				change.setA_Depreciation_Table_Header_ID(assetacct.getA_Depreciation_Table_Header_ID());
				change.setA_Depreciation_Variable_Perc(assetacct.getA_Depreciation_Variable_Perc());
				change.setA_Parent_Asset_ID(Asset.getA_Parent_Asset_ID());
			    change.setChangeType("SPL");
			    MRefList RefList = new MRefList (getCtx(), 0, null);	
				change.setTextDetails(RefList.getListDescription (getCtx(),"A_Update_Type" , "SPL"));   
			    change.setLot(AssetNew.getLot());
				change.setSerNo(AssetNew.getSerNo());
				change.setVersionNo(AssetNew.getVersionNo());
			    change.setUseLifeMonths(AssetNew.getUseLifeMonths());
			    change.setUseLifeYears(AssetNew.getUseLifeYears());
			    change.setLifeUseUnits(AssetNew.getLifeUseUnits());
			    change.setAssetDisposalDate(AssetNew.getAssetDisposalDate());
			    change.setAssetServiceDate(AssetNew.getAssetServiceDate());
			    change.setC_BPartner_Location_ID(AssetNew.getC_BPartner_Location_ID());
			    change.setC_BPartner_ID(AssetNew.getC_BPartner_ID());		    
			    change.setA_QTY_Current(AssetNew.getA_QTY_Current());
			    change.setA_QTY_Original(AssetNew.getA_QTY_Original());				
			    change.save();
				
				
			    //Record Account Numbers for JE's
			    A_Accumdepreciation_Acct = assetacctnew.getA_Accumdepreciation_Acct(); 
				A_Cost_Acct = assetacctnew.getA_Asset_Acct();
				A_DepExp_Acct = assetacctnew.getA_Depreciation_Acct();
				
			}
		    		    
		    //	Update original Asset
			Asset.setA_QTY_Current(v_QTY_Org);			
			Asset.save();		    
		    
			//	Update original asset for the split
			X_A_Asset_Addition assetaddold = new X_A_Asset_Addition (getCtx(), 0, null);	
			assetaddold.setA_Asset_ID(Asset.getA_Asset_ID());
			assetaddold.setAssetValueAmt(v_CostBalance_New.multiply( new BigDecimal(-1))); 
			assetaddold.setDescription("Split to Asset #" + v_AssetNumber);
			assetaddold.setA_CapvsExp("Cap");
			assetaddold.setA_SourceType("MAN");
			assetaddold.setA_QTY_Current(v_QTY_New.multiply( new BigDecimal(-1)));
			assetaddold.setPostingType(AssetSplit.getPostingType());
			assetaddold.setM_InOutLine_ID(1);
			assetaddold.save();			
			
			//Update the Original Asset in the Account File
			assetacct.setA_Salvage_Value(v_SalvageValue_Org);
			assetacct.setA_Depreciation_Manual_Amount(v_ManDep_Org);
			assetacct.save();		
			
			//Update the Original Asset in the Deprecation Workfile			
			assetwk.setA_Asset_Cost(v_CostBalance_Org);
			assetwk.setA_Accumulated_Depr(v_AccumBalance_Org);
			assetwk.setA_Salvage_Value(v_SalvageValue_Org);
			assetwk.setA_QTY_Current(v_QTY_Org);
			assetwk.save();			
			
			MAssetChange change1 = new MAssetChange (getCtx(), 0, null);
			change1.setChangeType("SPL");
		    MRefList RefList = new MRefList (getCtx(), 0, null);	
			change1.setTextDetails(RefList.getListDescription (getCtx(),"A_Update_Type" , "SPL"));
			change1.setAssetValueAmt(v_CostBalance_New.multiply(new BigDecimal(-1)));
			change1.setPostingType(assetacct.getPostingType());
			if (assetacct.getA_Reval_Cal_Method() == null)
				change1.setA_Reval_Cal_Method(" ");
			else	
				change1.setA_Reval_Cal_Method(assetacct.getA_Reval_Cal_Method());
			if (assetacct.getA_Depreciation_Manual_Period() == null)
				change1.setA_Depreciation_Manual_Period(" ");
			else	
				change1.setA_Depreciation_Manual_Period(assetacct.getA_Depreciation_Manual_Period());
			change1.setA_Asset_ID(Asset.getA_Asset_ID());
			change1.setAssetAccumDepreciationAmt(v_AccumBalance_New.multiply(new BigDecimal(-1)));
			change1.setA_Salvage_Value(v_SalvageValue_New.multiply(new BigDecimal(-1)));
			change1.setA_QTY_Current(v_QTY_New.multiply(new BigDecimal(-1)));
			change1.save();
			
	//Create Journal Entries for the split
			X_A_Depreciation_Exp depexp2 = new X_A_Depreciation_Exp (getCtx(), 0, null);
		//Create JV for the accumulated depreciation of the asset 			
			depexp2.setPostingType(AssetSplit.getPostingType());
			depexp2.setA_Asset_ID(v_AssetNumber);			
			depexp2.setExpense(v_AccumBalance_New.multiply(new BigDecimal(-1)));
			depexp2.setDateAcct(AssetSplit.getDateAcct());
			depexp2.setA_Account_Number(A_Accumdepreciation_Acct);
			depexp2.setDescription("Asset Split Accum Dep");
			depexp2.setIsDepreciated(false);
			depexp2.setA_Period(AssetSplit.getC_Period_ID());
			depexp2.setA_Entry_Type("SPL");			
			depexp2.save();			

			
			X_A_Depreciation_Exp depexp3 = new X_A_Depreciation_Exp (getCtx(), 0, null);
			depexp3.setPostingType(AssetSplit.getPostingType());
			depexp3.setA_Asset_ID(AssetSplit.getA_Asset_ID());
			depexp3.setExpense(v_AccumBalance_New);
			depexp3.setDateAcct(AssetSplit.getDateAcct());
			depexp3.setA_Account_Number(assetacct.getA_Accumdepreciation_Acct());
			depexp3.setDescription("Asset Split Accum Dep");
			depexp3.setIsDepreciated(false);
			depexp3.setA_Period(AssetSplit.getC_Period_ID());			
			depexp3.setA_Entry_Type("SPL");
			depexp3.save();
			
			X_A_Depreciation_Exp depexp4 = new X_A_Depreciation_Exp (getCtx(), 0, null);
		//Create JV for the Cost of the asset 			
			depexp4.setPostingType(AssetSplit.getPostingType());
			depexp4.setA_Asset_ID(v_AssetNumber);			
			depexp4.setExpense(v_CostBalance_New);
			depexp4.setDateAcct(AssetSplit.getDateAcct());
			depexp4.setA_Account_Number(A_Cost_Acct);
			depexp4.setDescription("Asset Split Cost");
			depexp4.setIsDepreciated(false);
			depexp4.setA_Period(AssetSplit.getC_Period_ID());
			depexp4.setA_Entry_Type("SPL");
			depexp4.save();			
		
			X_A_Depreciation_Exp depexp5 = new X_A_Depreciation_Exp (getCtx(), 0, null);
			depexp5.setPostingType(AssetSplit.getPostingType());
			depexp5.setA_Asset_ID(AssetSplit.getA_Asset_ID());
			depexp5.setExpense(v_CostBalance_New.multiply(new BigDecimal(-1)));
			depexp5.setDateAcct(AssetSplit.getDateAcct());
			depexp5.setA_Account_Number(assetacct.getA_Asset_Acct());
			depexp5.setDescription("Asset Split Cost");
			depexp5.setIsDepreciated(false);
			depexp5.setA_Period(AssetSplit.getC_Period_ID());			
			depexp5.setA_Entry_Type("SPL");
			depexp5.save();
			
			
			String sql = null;
			log.info("doIt - Finishing Split = " + no);
			
			sql = "SELECT A_ASSET_ID, CHANGEAMT "
				+ "FROM A_ASSET_CHANGE "
				+ "WHERE A_ASSET_CHANGE.A_ASSET_ID = " + AssetSplit.getA_Asset_ID() 
				+ " AND A_ASSET_CHANGE.POSTINGTYPE = '" + AssetSplit.getPostingType() 
				+ "' AND A_ASSET_CHANGE.CHANGETYPE= 'D' "
				+ "AND TRUNC(A_ASSET_CHANGE.DATEACCT, 'YY') = TRUNC( " + DB.TO_DATE(AssetSplit.getDateAcct()) + ", 'YY') "
				+ "AND TRUNC(A_ASSET_CHANGE.DATEACCT, 'MM') <= TRUNC( " + DB.TO_DATE(AssetSplit.getDateAcct()) + ", 'MM') "
				+ "AND A_ASSET_CHANGE.C_VALIDCOMBINATION_ID = " + assetacct.getA_Depreciation_Acct();
					
			PreparedStatement pstmt = null;
			pstmt = DB.prepareStatement (sql,null);
			log.info("doIt - SQL=" + sql);
			BigDecimal v_Balance = new BigDecimal("0.0");
			ResultSet rs = null;
			try {				
				rs = pstmt.executeQuery();			
				if (AssetSplit.isA_Transfer_Balance_IS()==true)
				{
				while (rs.next());
					v_Balance = v_Balance.add(rs.getBigDecimal("ChangeAmt"));
							
			// Create JV for YTD Depreciation Expense
				X_A_Depreciation_Exp depexp0 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				depexp0.setPostingType(AssetSplit.getPostingType());
				depexp0.setA_Asset_ID(v_AssetNumber);			
				depexp0.setExpense(v_Balance.multiply(v_multiplier_New));
				depexp0.setDateAcct(AssetSplit.getDateAcct());
				depexp0.setA_Account_Number(A_DepExp_Acct);
				depexp0.setDescription("Asset Split YTD Depreciation Expense");
				depexp0.setIsDepreciated(false);
				depexp0.setA_Period(AssetSplit.getC_Period_ID());
				depexp0.setA_Entry_Type("SPL");
				depexp0.save();
				
				X_A_Depreciation_Exp depexp1 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				depexp1.setPostingType(AssetSplit.getPostingType());
				depexp1.setA_Asset_ID(AssetSplit.getA_Asset_ID());
				depexp1.setExpense(v_Balance.multiply(new BigDecimal(-1)).multiply(v_multiplier_New));
				depexp1.setDateAcct(AssetSplit.getDateAcct());
				depexp1.setA_Account_Number(assetacct.getA_Depreciation_Acct());
				depexp1.setDescription("Asset Split YTD Depreciation Expense");
				depexp1.setIsDepreciated(false);
				depexp1.setA_Period(AssetSplit.getC_Period_ID());			
				depexp1.setA_Entry_Type("SPL");
				depexp1.save();
			}
				
				rs.close();
				pstmt.close();
				pstmt = null;
			}
				catch (Exception e)
				{
					log.info("AssetSplit"+ e);
				}
				  finally
				  {
					  DB.close(rs, pstmt);
					  rs = null; pstmt = null;
				  }
	return "";
	}	//	doIt
	
}	//	AssetSplit
