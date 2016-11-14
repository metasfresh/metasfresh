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

import org.compiere.model.MAssetAcct;
import org.compiere.model.MAssetChange;
import org.compiere.model.MRefList;
import org.compiere.model.X_A_Asset;
import org.compiere.model.X_A_Asset_Addition;
import org.compiere.model.X_A_Asset_Group_Acct;
import org.compiere.model.X_A_Depreciation_Workfile;
import org.compiere.model.X_GL_JournalLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 *	Create Asset from FA GL Process
 *	
 *  @author Rob Klein
 *  @version  $Id: CreateGLAsset.java,v 1.0 $
 */
public class CreateGLAsset extends SvrProcess
{

	private int p_client = 0;	
	//private int p_org = 0;

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
			else if (name.equals("AD_Client_ID"))
				p_client = para[i].getParameterAsInt();			
			else
				log.info("prepare - Unknown Parameter: " + name);
		}	
	}	//	prepare

	
	/**
	 * 	Process Invoices
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		log.info("Starting inbound invoice process");		
//		int uselifemonths = 0;
//		int uselifeyears = 0;
		
		String sql =" SELECT * FROM GL_JOURNALLINE WHERE A_Processed <> 'Y' and AD_Client_ID = ?" 
				+ " and A_CreateAsset = 'Y' and Processed = 'Y'";
		log.info(sql);
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql,get_TrxName());
		ResultSet rs = null;
		try {
			pstmt.setInt(1, p_client);	
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				
				X_A_Asset asset = new X_A_Asset (getCtx(), rs.getInt("A_Asset_ID"), get_TrxName());
				X_GL_JournalLine JVLine = new X_GL_JournalLine (getCtx(), rs.getInt("GL_JournalLine_ID"), get_TrxName());				
				String sql2 ="SELECT C.AccountType FROM GL_JournalLine A, C_ValidCombination B, C_ElementValue C WHERE A.GL_JournalLine_ID = ?" +
						" and  A.C_ValidCombination_ID = B.C_ValidCombination_ID and B.Account_ID = C.C_ElementValue_ID";				
				String acctType = DB.getSQLValueString(get_TrxName(),sql2, JVLine.getGL_JournalLine_ID());				
				if (acctType.equals("A")){
					sql ="SELECT * FROM A_Asset_Group_Acct WHERE A_Asset_Group_ID = ? AND IsActive='Y'";
					log.info("yes");
					pstmt = null;
					pstmt = DB.prepareStatement(sql,get_TrxName());
					if(asset.getA_Asset_ID()==0) {
						int groupId = JVLine.getA_Asset_Group_ID();
						pstmt.setInt(1, groupId);
					} else
						pstmt.setInt(1, asset.getA_Asset_Group_ID());
					ResultSet rs2 = pstmt.executeQuery();
					
					while (rs2.next()){
					
					X_A_Asset_Group_Acct assetgrpacct = new X_A_Asset_Group_Acct (getCtx(), rs2,get_TrxName());				
					MAssetAcct assetacct = new MAssetAcct (getCtx(), 0, get_TrxName());		
					if (assetgrpacct.isProcessing()== true){					
						sql2 = "SELECT COUNT(*) FROM A_Depreciation_Workfile WHERE A_Asset_ID=? and" 
								+ " PostingType = '"+assetgrpacct.getPostingType()+"'";
						if (DB.getSQLValue(get_TrxName(),sql2, asset.getA_Asset_ID())== 0) 
					    {
									asset.setIsOwned(true);
									asset.setIsDepreciated(assetgrpacct.isProcessing());
									asset.setA_Asset_CreateDate(JVLine.getDateAcct());
									asset.setIsInPosession(true);
									if(JVLine.getDescription()!= null)
										asset.setName(JVLine.getDescription());
									else
										asset.setName("Asset created from JV");
									asset.setHelp("Created from JV #" + JVLine.getGL_Journal_ID() + " on line #" + JVLine.getLine());
									asset.setDescription(JVLine.getDescription());
									asset.setUseLifeMonths(assetgrpacct.getUseLifeMonths());
									asset.setUseLifeYears(assetgrpacct.getUseLifeYears());
									asset.setA_Asset_Group_ID(assetgrpacct.getA_Asset_Group_ID());
									asset.setA_QTY_Current(JVLine.getQty());
									asset.setA_QTY_Original(JVLine.getQty());
									asset.save();
									asset.setA_Parent_Asset_ID(asset.getA_Asset_ID());
									asset.save();
									
									boolean isdepreciate = assetgrpacct.isProcessing();
									
									if (isdepreciate == true)
									{
										assetacct.setPostingType(assetgrpacct.getPostingType());										
										assetacct.setA_Split_Percent(assetgrpacct.getA_Split_Percent());
										assetacct.setA_Depreciation_Conv_ID(assetgrpacct.getConventionType());
										assetacct.setA_Asset_ID(asset.getA_Asset_ID());								
										assetacct.setA_Depreciation_ID(assetgrpacct.getDepreciationType());
										assetacct.setA_Asset_Spread_ID(assetgrpacct.getA_Asset_Spread_Type());
										assetacct.setA_Period_Start(1);										
						
										if (asset.getUseLifeMonths() == 0 & asset.getUseLifeYears() == 0){
											assetacct.setA_Period_End(assetgrpacct.getUseLifeMonths());
											asset.setUseLifeYears(assetgrpacct.getUseLifeYears());
											asset.setUseLifeMonths(assetgrpacct.getUseLifeMonths());
											asset.setIsDepreciated(true);
											asset.setIsOwned(true);
											asset.save();
//											uselifemonths = assetgrpacct.getUseLifeMonths();
//											uselifeyears = assetgrpacct.getUseLifeYears();
											
											}
										else if(asset.getUseLifeMonths() == 0){
											assetacct.setA_Period_End(asset.getUseLifeYears()*12);
											asset.setUseLifeMonths(asset.getUseLifeYears()*12);
											asset.setIsDepreciated(true);
											asset.setIsOwned(true);
											asset.save();
//											uselifemonths = asset.getUseLifeYears()*12;
//											uselifeyears = asset.getUseLifeYears();						
											}
										else{
											assetacct.setA_Period_End(asset.getUseLifeMonths());
//											uselifemonths = asset.getUseLifeMonths();
//											uselifeyears = asset.getUseLifeYears();
											}
										
										assetacct.setA_Depreciation_Method_ID(assetgrpacct.getA_Depreciation_Calc_Type());
										assetacct.setA_Asset_Acct(assetgrpacct.getA_Asset_Acct());
										assetacct.setC_AcctSchema_ID(assetgrpacct.getC_AcctSchema_ID());
										assetacct.setA_Salvage_Value(new BigDecimal (0.0));
										assetacct.setA_Accumdepreciation_Acct(assetgrpacct.getA_Accumdepreciation_Acct());
										assetacct.setA_Depreciation_Acct(assetgrpacct.getA_Depreciation_Acct());
										assetacct.setA_Disposal_Revenue(assetgrpacct.getA_Disposal_Revenue());
										assetacct.setA_Disposal_Loss(assetgrpacct.getA_Disposal_Loss());										
										assetacct.setA_Reval_Accumdep_Offset_Cur(assetgrpacct.getA_Reval_Accumdep_Offset_Cur());
										assetacct.setA_Reval_Accumdep_Offset_Prior(assetgrpacct.getA_Reval_Accumdep_Offset_Prior());
										assetacct.setA_Reval_Cal_Method(assetgrpacct.getA_Reval_Cal_Method());
										assetacct.setA_Reval_Cost_Offset(assetgrpacct.getA_Reval_Cost_Offset());
										assetacct.setA_Reval_Cost_Offset_Prior(assetgrpacct.getA_Reval_Cost_Offset_Prior());
										assetacct.setA_Reval_Depexp_Offset(assetgrpacct.getA_Reval_Depexp_Offset());
										assetacct.setA_Depreciation_Manual_Amount(assetgrpacct.getA_Depreciation_Manual_Amount());
										assetacct.setA_Depreciation_Manual_Period(assetgrpacct.getA_Depreciation_Manual_Period());
										assetacct.setA_Depreciation_Table_Header_ID(assetgrpacct.getA_Depreciation_Table_Header_ID());
										assetacct.setA_Depreciation_Variable_Perc(assetgrpacct.getA_Depreciation_Variable_Perc());
										assetacct.setProcessing(false);
										assetacct.save();
										
										MAssetChange change = new MAssetChange (getCtx(), 0, get_TrxName());						
										change.setPostingType(assetacct.getPostingType());
										change.setA_Split_Percent(assetacct.getA_Split_Percent());
										change.setConventionType(assetacct.getA_Depreciation_Conv_ID());
										change.setA_Asset_ID(asset.getA_Asset_ID());								
										change.setDepreciationType(assetacct.getA_Depreciation_ID());
										change.setA_Asset_Spread_Type(assetacct.getA_Asset_Spread_ID());
										change.setA_Period_Start(assetacct.getA_Period_Start());
										change.setA_Period_End(assetacct.getA_Period_End());
										change.setIsInPosession(asset.isOwned());
										change.setIsDisposed(asset.isDisposed());
										change.setIsDepreciated(asset.isDepreciated());
										change.setIsFullyDepreciated(asset.isFullyDepreciated());					
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
										change.setA_Depreciation_Manual_Period(assetacct.getA_Depreciation_Manual_Period());
										change.setA_Depreciation_Table_Header_ID(assetacct.getA_Depreciation_Table_Header_ID());
										change.setA_Depreciation_Variable_Perc(assetacct.getA_Depreciation_Variable_Perc());
										change.setA_Parent_Asset_ID(asset.getA_Parent_Asset_ID());
									    change.setChangeType("CRT");
									    change.setTextDetails(MRefList.getListDescription (getCtx(),"A_Update_Type" , "CRT"));		    
									    change.setIsInPosession(asset.isOwned());
										change.setIsDisposed(asset.isDisposed());
										change.setIsDepreciated(asset.isDepreciated());
										change.setIsFullyDepreciated(asset.isFullyDepreciated());
										change.setLot(asset.getLot());
										change.setSerNo(asset.getSerNo());
										change.setVersionNo(asset.getVersionNo());
									    change.setUseLifeMonths(asset.getUseLifeMonths());
									    change.setUseLifeYears(asset.getUseLifeYears());
									    change.setLifeUseUnits(asset.getLifeUseUnits());
									    change.setAssetDisposalDate(asset.getAssetDisposalDate());
									    change.setAssetServiceDate(asset.getAssetServiceDate());
									    change.setC_BPartner_Location_ID(asset.getC_BPartner_Location_ID());
									    change.setC_BPartner_ID(asset.getC_BPartner_ID());
									    change.setAssetValueAmt(JVLine.getAmtAcctDr().subtract(JVLine.getAmtAcctCr()));									    
									    change.setA_Asset_CreateDate(asset.getA_Asset_CreateDate());
									    change.setAD_User_ID(asset.getAD_User_ID());
									    change.setC_Location_ID(asset.getC_Location_ID());
									    change.save();
									}

			
							
									X_A_Depreciation_Workfile assetwk = new X_A_Depreciation_Workfile (getCtx(), 0, get_TrxName());
									assetwk.setA_Asset_ID(asset.getA_Asset_ID());		
									assetwk.setA_Life_Period(assetgrpacct.getUseLifeMonths());
									assetwk.setA_Asset_Life_Years(assetgrpacct.getUseLifeYears());
									assetwk.setA_Asset_Cost(assetwk.getA_Asset_Cost().add(JVLine.getAmtAcctDr().subtract(JVLine.getAmtAcctCr())));
									assetwk.setA_Accumulated_Depr(new BigDecimal (0.0));
									assetwk.setA_Salvage_Value(new BigDecimal (0.0));
									assetwk.setA_Period_Posted(0);
									assetwk.setA_Asset_Life_Current_Year(new BigDecimal (0.0));
									assetwk.setA_Curr_Dep_Exp(new BigDecimal (0.0));
									assetwk.setA_QTY_Current(JVLine.getQty());
									assetwk.setIsDepreciated(assetgrpacct.isProcessing());
									assetwk.setPostingType(assetgrpacct.getPostingType());
									assetwk.save();
									
									X_A_Asset_Addition assetadd = new X_A_Asset_Addition (getCtx(), 0, get_TrxName());
									assetadd.setA_Asset_ID(asset.getA_Asset_ID());
									assetadd.setAssetValueAmt(JVLine.getAmtAcctDr().subtract(JVLine.getAmtAcctCr()));
									assetadd.setA_SourceType("JRN");
									assetadd.setA_CapvsExp("Cap");
									assetadd.setA_QTY_Current(JVLine.getQty());
									assetadd.setDocumentNo(""+JVLine.getGL_Journal_ID());
									assetadd.setGL_JournalBatch_ID(JVLine.getGL_Journal_ID());
									assetadd.setLine(JVLine.getLine());
									assetadd.setDescription(JVLine.getDescription());									
									assetadd.setPostingType(assetwk.getPostingType());
									assetadd.save();
									
					    }											
						else
						{		
						sql2 ="SELECT * FROM A_Depreciation_Workfile WHERE A_Asset_ID = ? and PostingType = ?";		
						PreparedStatement pstmt2 = null;
						pstmt2 = DB.prepareStatement(sql2,get_TrxName());
						ResultSet rs3 = null;
						log.info("no");
						try {
							pstmt2.setInt(1, asset.getA_Asset_ID());
							pstmt2.setString(2, assetgrpacct.getPostingType());
							rs3 = pstmt2.executeQuery();						
							while (rs3.next()){	
								X_A_Depreciation_Workfile assetwk = new X_A_Depreciation_Workfile (getCtx(), rs3, get_TrxName());
								assetwk.setA_Asset_ID(asset.getA_Asset_ID());		
								assetwk.setA_Life_Period(assetgrpacct.getUseLifeMonths());
								assetwk.setA_Asset_Life_Years(assetgrpacct.getUseLifeYears());
								assetwk.setA_Asset_Cost(assetwk.getA_Asset_Cost().add(JVLine.getAmtAcctDr().subtract(JVLine.getAmtAcctCr())));
								assetwk.setA_QTY_Current(assetwk.getA_QTY_Current().add(JVLine.getQty()));
								assetwk.setIsDepreciated(assetgrpacct.isProcessing());															
								assetwk.save();
								
								X_A_Asset_Addition assetadd = new X_A_Asset_Addition (getCtx(), 0, get_TrxName());
								assetadd.setA_Asset_ID(asset.getA_Asset_ID());							
								assetadd.setAssetValueAmt((JVLine.getAmtAcctDr().subtract(JVLine.getAmtAcctCr())));
								assetadd.setA_SourceType("JRN");
								assetadd.setA_CapvsExp("Cap");
								assetadd.setA_QTY_Current(JVLine.getQty());
								assetadd.setDocumentNo(""+JVLine.getGL_Journal_ID());
								assetadd.setGL_JournalBatch_ID(JVLine.getGL_Journal_ID());
								assetadd.setLine(JVLine.getLine());
								assetadd.setDescription(JVLine.getDescription());								
								assetadd.setPostingType(assetwk.getPostingType());
								assetadd.save();
								
								
					            asset.setA_QTY_Original(assetadd.getA_QTY_Current().add(asset.getA_QTY_Original()));
					            asset.setA_QTY_Current(assetadd.getA_QTY_Current().add(asset.getA_QTY_Current()));
					            asset.save();
					            
					            MAssetChange change = new MAssetChange (getCtx(), 0, get_TrxName());
					            change.setA_Asset_ID(asset.getA_Asset_ID());					                       
					            change.setChangeType("ADD");
					        	change.setTextDetails(MRefList.getListDescription (getCtx(),"A_Update_Type" , "ADD"));
					            change.setPostingType(assetwk.getPostingType());
					            change.setAssetValueAmt(assetadd.getAssetValueAmt());
					            change.setA_QTY_Current(assetadd.getA_QTY_Current());            
					            change.save();
	
							}
							}
							catch (Exception e)
							{
								log.info("getAssets "+ e);
							}
							  finally
							  {
								  DB.close(rs3, pstmt2);
								  rs3 = null; pstmt2 = null;
							  }

					    	}
						}
					}
				}
				else if (acctType.equals("E"))				
				{					
					X_A_Asset_Addition assetadd = new X_A_Asset_Addition (getCtx(), 0, get_TrxName());
					assetadd.setA_Asset_ID(asset.getA_Asset_ID());
					assetadd.setAssetValueAmt((JVLine.getAmtAcctDr().subtract(JVLine.getAmtAcctCr())));
					assetadd.setA_SourceType("JRN");
					assetadd.setA_CapvsExp("Exp");
					assetadd.setA_QTY_Current(JVLine.getQty());
					assetadd.setDocumentNo(""+JVLine.getGL_Journal_ID());
					assetadd.setGL_JournalBatch_ID(JVLine.getGL_Journal_ID());
					assetadd.setLine(JVLine.getLine());
					assetadd.setDescription(JVLine.getDescription());					
					assetadd.setPostingType("A");
					assetadd.save();
					
					MAssetChange change = new MAssetChange (getCtx(), 0, get_TrxName());
		            change.setA_Asset_ID(asset.getA_Asset_ID());            
		            change.setA_QTY_Current(assetadd.getA_QTY_Current());           
		            change.setChangeType("EXP");
		        	change.setTextDetails(MRefList.getListDescription (getCtx(),"A_Update_Type" , "EXP"));
		        	assetadd.setPostingType("A");
		            change.setAssetValueAmt(assetadd.getAssetValueAmt());
		            change.setA_QTY_Current(assetadd.getA_QTY_Current());            
		            change.save();
				}		
				JVLine.set_ValueOfColumn(I_CustomColumn.A_Processed, Boolean.TRUE);
				JVLine.save();
			}
			rs.close();
			pstmt.close();
			pstmt = null;
			}
			catch (Exception e)
			{
				log.info("getAssets "+ e);
			}
			finally
			{
			  DB.close(rs, pstmt);
			  rs = null; pstmt = null;
			}
			 return "";
	}	//	doIt
	
}	//	CreateGLAsset
