/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is                  Compiere  ERP & CRM  Business Solution
 * The Initial Developer of the Original Code is Jorg Janke  and ComPiere, Inc.
 * Portions created by Jorg Janke are Copyright (C) 1999-2004 Jorg Janke, parts
 * created by ComPiere are Copyright (C) ComPiere, Inc.;   All Rights Reserved.
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
import java.sql.SQLException;
import org.compiere.model.MAssetAcct;
import org.compiere.model.MAssetChange;
import org.compiere.model.MRefList;
import org.compiere.model.X_A_Asset;
import org.compiere.model.X_A_Asset_Addition;
import org.compiere.model.X_A_Asset_Group_Acct;
import org.compiere.model.X_A_Depreciation_Exp;
import org.compiere.model.X_A_Depreciation_Workfile;
import org.compiere.model.X_C_BPartner;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_InvoiceLine;
import org.compiere.model.X_M_Product;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 *	Create Asset from Invoice Process
 *	
 *  @author Rob Klein
 *  
 */
public class CreateInvoicedAsset extends SvrProcess
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
		int uselifemonths = 0;
		int uselifeyears = 0;
		int C_Period_ID=0;
		int invoiceAcct=0;
		//Yvonne: add in recordInsertedCount
		int recordInsertedCount = 0;
		//Yvonne: changed A_Processed is null to A_Processed='N'
		String sql =" SELECT * FROM C_InvoiceLine WHERE A_Processed='N' and AD_Client_ID = ?" 
			+ " and A_CreateAsset = 'Y' and Processed = 'Y'";
		
		log.info("sql"+sql+p_client);
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement(sql,get_TrxName());
		ResultSet rs = null;
		try {
			pstmt.setInt(1, p_client);	
			rs = pstmt.executeQuery();
			int i=0;

			while (rs.next()){
				i=i+1;
				log.info("here is the counter "+i);
				X_A_Asset asset = new X_A_Asset (getCtx(), rs.getInt("A_Asset_ID"), get_TrxName());
				
				X_C_Invoice Invoice = new X_C_Invoice (getCtx(), rs.getInt("C_Invoice_ID"), get_TrxName());
				X_C_InvoiceLine InvoiceLine = new X_C_InvoiceLine (getCtx(), rs, get_TrxName());				
				X_M_Product product = new X_M_Product (getCtx(), InvoiceLine.getM_Product_ID(), get_TrxName());
				//X_M_Product_Category productcat = new X_M_Product_Category (getCtx(), product.getM_Product_Category_ID(), get_TrxName());
				
				
				//X_A_Asset_Addition assetadd = new X_A_Asset_Addition (getCtx(), 0);
				X_C_BPartner business = new X_C_BPartner (getCtx(), Invoice.getC_BPartner_ID(), get_TrxName());
				
				if (rs.getString("A_CapvsExp").equals("Cap")){
					String sqla ="SELECT * FROM A_Asset_Group_Acct WHERE A_Asset_Group_ID = ? AND IsActive='Y'";
					
					PreparedStatement pstmt1 = null;
					pstmt1 = DB.prepareStatement(sqla,get_TrxName());
					if(asset.getA_Asset_ID()==0) {
						int groupId = InvoiceLine.getA_Asset_Group_ID();
						pstmt1.setInt(1, groupId);
					} else
						pstmt1.setInt(1, asset.getA_Asset_Group_ID());
					ResultSet rs2 = pstmt1.executeQuery();
					
					while (rs2.next()){
					
					X_A_Asset_Group_Acct assetgrpacct = new X_A_Asset_Group_Acct (getCtx(), rs2,get_TrxName());				
					MAssetAcct assetacct = new MAssetAcct (getCtx(), 0, get_TrxName());		
					if (assetgrpacct.isProcessing()== true){					
						String sql2 = "SELECT COUNT(*) FROM A_Depreciation_Workfile WHERE A_Asset_ID=? and" 
								+ " PostingType = '"+assetgrpacct.getPostingType()+"'";
						if (DB.getSQLValue(get_TrxName(),sql2, asset.getA_Asset_ID())== 0)
					    {
							
							asset.setIsOwned(true);
							asset.setIsDepreciated(assetgrpacct.isProcessing());
							asset.setA_Asset_CreateDate(Invoice.getDateInvoiced());
							asset.setIsInPosession(true);									
							if (InvoiceLine.getM_Product_ID()>0)
								asset.setName(product.getName()+"-"+business.getName()+"-"+Invoice.getDocumentNo());
							else
								asset.setName(business.getName()+"-"+Invoice.getDocumentNo());									
							asset.setHelp("Created from Invoice #" + rs.getInt("C_Invoice_ID") + " on line #" + InvoiceLine.getLine());
							asset.setDescription(InvoiceLine.getDescription());
							asset.setUseLifeMonths(assetgrpacct.getUseLifeMonths());
							asset.setUseLifeYears(assetgrpacct.getUseLifeYears());
							asset.setA_Asset_Group_ID(assetgrpacct.getA_Asset_Group_ID());
							asset.setA_QTY_Current(InvoiceLine.getQtyEntered());
							asset.setA_QTY_Original(InvoiceLine.getQtyEntered());
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
									uselifemonths = assetgrpacct.getUseLifeMonths();
									uselifeyears = assetgrpacct.getUseLifeYears();
									
									}
								else if(asset.getUseLifeMonths() == 0){
									assetacct.setA_Period_End(asset.getUseLifeYears()*12);
									asset.setUseLifeMonths(asset.getUseLifeYears()*12);
									asset.setIsDepreciated(true);
									asset.setIsOwned(true);
									asset.save();
									uselifemonths = asset.getUseLifeYears()*12;
									uselifeyears = asset.getUseLifeYears();						
									}
								else{
									assetacct.setA_Period_End(asset.getUseLifeMonths());
									uselifemonths = asset.getUseLifeMonths();
									uselifeyears = asset.getUseLifeYears();}
								
								assetacct.setA_Depreciation_Method_ID(assetgrpacct.getA_Depreciation_Calc_Type());
								assetacct.setA_Asset_Acct(assetgrpacct.getA_Asset_Acct());
								assetacct.setC_AcctSchema_ID(assetgrpacct.getC_AcctSchema_ID());
								assetacct.setA_Accumdepreciation_Acct(assetgrpacct.getA_Accumdepreciation_Acct());
								assetacct.setA_Depreciation_Acct(assetgrpacct.getA_Depreciation_Acct());
								assetacct.setA_Disposal_Revenue(assetgrpacct.getA_Disposal_Revenue());
								assetacct.setA_Disposal_Loss(assetgrpacct.getA_Disposal_Loss());
								assetacct.setA_Salvage_Value(new BigDecimal(0.0));
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
								assetacct.getAD_Client_ID();
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
							    change.setAssetValueAmt(InvoiceLine.getLineTotalAmt());
							    change.setA_QTY_Current(InvoiceLine.getQtyEntered());
							    change.setA_QTY_Original(InvoiceLine.getQtyEntered());
							    change.setA_Asset_CreateDate(asset.getA_Asset_CreateDate());
							    change.setAD_User_ID(asset.getAD_User_ID());
							    change.setC_Location_ID(asset.getC_Location_ID());
							    change.save();
							}

	
					
							X_A_Depreciation_Workfile assetwk = new X_A_Depreciation_Workfile (getCtx(), 0, get_TrxName());
							assetwk.setA_Asset_ID(asset.getA_Asset_ID());		
							assetwk.setA_Life_Period(assetgrpacct.getUseLifeMonths());
							assetwk.setA_Asset_Life_Years(assetgrpacct.getUseLifeYears());
							assetwk.setA_Asset_Cost(assetwk.getA_Asset_Cost().add(InvoiceLine.getLineTotalAmt()));							
							assetwk.setA_QTY_Current(InvoiceLine.getQtyEntered());
							assetwk.setIsDepreciated(assetgrpacct.isProcessing());
							assetwk.setPostingType(assetgrpacct.getPostingType());
							assetwk.setA_Accumulated_Depr(new BigDecimal (0.0));
							assetwk.setA_Period_Posted(0);
							assetwk.setA_Asset_Life_Current_Year(new BigDecimal (0.0));
							assetwk.setA_Curr_Dep_Exp(new BigDecimal (0.0));
							assetwk.save();
							
							X_A_Asset_Addition assetadd = new X_A_Asset_Addition (getCtx(), 0, get_TrxName());
							assetadd.setA_Asset_ID(asset.getA_Asset_ID());
							assetadd.setAssetValueAmt(InvoiceLine.getLineTotalAmt());
							assetadd.setA_SourceType("INV");
							assetadd.setA_CapvsExp("Cap");
							assetadd.setM_InOutLine_ID(rs.getInt("C_Invoice_ID"));				
							assetadd.setC_Invoice_ID(rs.getInt("C_Invoice_ID"));
							assetadd.setDocumentNo(Invoice.getDocumentNo());
							assetadd.setLine(InvoiceLine.getLine());
							assetadd.setDescription(InvoiceLine.getDescription());
							assetadd.setA_QTY_Current(InvoiceLine.getQtyEntered());
							assetadd.setPostingType(assetwk.getPostingType());
							assetadd.save();
							
							String sql1 = "SELECT C_Period_ID "
								+ "FROM C_Period "
								+ "WHERE C_Year_ID IN "
								+ "	(SELECT C_Year_ID FROM C_Year WHERE C_Calendar_ID ="
								+ "  (SELECT C_Calendar_ID FROM AD_ClientInfo WHERE AD_Client_ID=?))"
								+ " AND ? BETWEEN StartDate AND EndDate"
								+ " AND PeriodType='S'";
							
							try
							{
								PreparedStatement pstmt4 = DB.prepareStatement(sql1,get_TrxName());
								pstmt4.setInt(1, asset.getAD_Client_ID());
								pstmt4.setTimestamp(2, Invoice.getDateAcct());
								ResultSet rs4 = pstmt4.executeQuery();
								if (rs4.next())
									 C_Period_ID = rs4.getInt(1);
								DB.close(rs4, pstmt4);
								pstmt4 = null;
							}
							catch (SQLException e)
							{
								log.error("Journal_Period - DateAcct", e);								
								return e.getLocalizedMessage();
							}
							
							/**Code below is for future functionality
							int DocumentNo = MSequence.getNextID (Env.getAD_Client_ID(Env.getCtx()), "DocumentNo_M_InOut", get_TrxName());
							
							
							//Adjust Inventory Quantity
							X_M_InOut mInOut = new X_M_InOut (getCtx(), 0, get_TrxName());							
							mInOut.setC_BPartner_ID (Invoice.getC_BPartner_ID());
							mInOut.setC_BPartner_Location_ID (Invoice.getC_BPartner_Location_ID());
							mInOut.setC_DocType_ID (Invoice.getC_DocType_ID());
							mInOut.setDateAcct (new Timestamp(System.currentTimeMillis()));	// @#Date@
							mInOut.setDeliveryRule ("A");	// A
							mInOut.setDeliveryViaRule ("P");	// P
							mInOut.setDocAction ("CO");	// CO
							mInOut.setDocStatus ("DR");	// DR
							mInOut.setDescription("Invoice transfered to assets");
							mInOut.setDocumentNo (""+DocumentNo);
							mInOut.setFreightCostRule ("I");	// I
							mInOut.setPriorityRule("5");
							mInOut.setIsApproved (false);
							mInOut.setIsInDispute (false);
							mInOut.setIsInTransit (false);
							mInOut.setIsPrinted (false);
							mInOut.setIsSOTrx (false);	// @IsSOTrx@
							mInOut.setM_InOut_ID (0);
							mInOut.setM_Warehouse_ID (0);
							mInOut.setMovementDate (new Timestamp(System.currentTimeMillis()));	// @#Date@
							mInOut.setMovementType ("V-");
							mInOut.setPosted (false);
							mInOut.setProcessed (false);
							mInOut.setSendEMail (false);
							mInOut.save();
							
							X_M_InOutLine mInOutLine = new X_M_InOutLine (getCtx(), 0, get_TrxName());
							mInOutLine.setC_UOM_ID (InvoiceLine.getC_UOM_ID());	// @#C_UOM_ID@
							mInOutLine.setIsDescription (false);	// N
							mInOutLine.setIsInvoiced (false);
							mInOutLine.setLine (10);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_InOutLine WHERE M_InOut_ID=@M_InOut_ID@
							mInOutLine.setM_AttributeSetInstance_ID (InvoiceLine.getM_AttributeSetInstance_ID());							
							mInOutLine.setM_InOut_ID (mInOut.getM_InOut_ID());
							mInOutLine.setM_Locator_ID (0);	// @M_Locator_ID@
							mInOutLine.setM_Product_ID (InvoiceLine.getM_Product_ID());
							mInOutLine.setMovementQty (InvoiceLine.getQtyInvoiced());	// 1
							mInOutLine.setProcessed (false);
							mInOutLine.setQtyEntered (Env.ZERO);	// 1
							**/
							
							//Determine non tax accounts to credit
							invoiceAcct = determineAcct (assetacct.getC_AcctSchema_ID(), InvoiceLine.getM_Product_ID(), InvoiceLine.getC_Charge_ID(), InvoiceLine.getLineNetAmt());	
														
							//Create Journal Entries for the new asset
							X_A_Depreciation_Exp depexp2 = new X_A_Depreciation_Exp (getCtx(), 0, get_TrxName());						 			
							depexp2.setPostingType(assetacct.getPostingType());
							depexp2.setA_Asset_ID(asset.getA_Asset_ID());
							depexp2.setExpense(InvoiceLine.getLineTotalAmt());
							depexp2.setDateAcct(Invoice.getDateAcct());
							depexp2.setA_Account_Number(assetacct.getA_Asset_Acct());
							depexp2.setDescription("Create Asset from Invoice");
							depexp2.setIsDepreciated(false);
							depexp2.setA_Period(C_Period_ID);
							depexp2.setA_Entry_Type("NEW");
							depexp2.save();
							recordInsertedCount++;
							
							X_A_Depreciation_Exp depexp3 = new X_A_Depreciation_Exp (getCtx(), 0, get_TrxName());
							depexp3.setPostingType(assetacct.getPostingType());
							depexp3.setA_Asset_ID(asset.getA_Asset_ID());
							depexp3.setExpense(InvoiceLine.getLineNetAmt().multiply(new BigDecimal (-1)));
							depexp3.setDateAcct(Invoice.getDateAcct());
							depexp3.setA_Account_Number(invoiceAcct);
							depexp3.setDescription("Create Asset from Invoice");
							depexp3.setIsDepreciated(false);
							depexp3.setA_Period(C_Period_ID);			
							depexp3.setA_Entry_Type("NEW");
							depexp3.save();
							recordInsertedCount++;
							
							//Determine if tax adjustment is necessary
							if (InvoiceLine.getTaxAmt().compareTo(new BigDecimal (0))!=0){
								
								invoiceAcct = determineTaxAcct (assetacct.getC_AcctSchema_ID(), InvoiceLine.getC_Tax_ID());
								
								X_A_Depreciation_Exp depexp4 = new X_A_Depreciation_Exp (getCtx(), 0, get_TrxName());						 			
								depexp4.setPostingType(assetacct.getPostingType());
								depexp4.setA_Asset_ID(asset.getA_Asset_ID());
								depexp4.setExpense(InvoiceLine.getTaxAmt().multiply(new BigDecimal (-1)));
								depexp4.setDateAcct(Invoice.getDateAcct());
								depexp4.setA_Account_Number(invoiceAcct);
								depexp4.setDescription("Create Asset from Invoice");
								depexp4.setIsDepreciated(false);
								depexp4.setA_Period(C_Period_ID);
								depexp4.setA_Entry_Type("NEW");
								depexp4.save();							
								recordInsertedCount++;

							}
					    }											
						else
						{		
							sql2 ="SELECT * FROM A_Depreciation_Workfile WHERE A_Asset_ID = ? and PostingType = ?";		
							PreparedStatement pstmt2 = null;
							pstmt2 = DB.prepareStatement(sql2,get_TrxName());
							ResultSet rs3 = null;
							try {
								pstmt2.setInt(1, asset.getA_Asset_ID());
								pstmt2.setString(2, assetgrpacct.getPostingType());
								rs3 = pstmt2.executeQuery();						
								while (rs3.next()){	
									X_A_Depreciation_Workfile assetwk = new X_A_Depreciation_Workfile (getCtx(), rs3, get_TrxName());
									assetwk.setA_Asset_ID(asset.getA_Asset_ID());		
									assetwk.setA_Life_Period(assetgrpacct.getUseLifeMonths());
									assetwk.setA_Asset_Life_Years(assetgrpacct.getUseLifeYears());
									assetwk.setA_Asset_Cost(assetwk.getA_Asset_Cost().add(InvoiceLine.getLineTotalAmt()));
									assetwk.setIsDepreciated(assetgrpacct.isProcessing());
									assetwk.setA_QTY_Current(InvoiceLine.getQtyEntered());							
									assetwk.save();
									
									X_A_Asset_Addition assetadd = new X_A_Asset_Addition (getCtx(), 0, get_TrxName());
									assetadd.setA_Asset_ID(asset.getA_Asset_ID());							
									assetadd.setAssetValueAmt(InvoiceLine.getLineTotalAmt());
									assetadd.setA_SourceType("INV");
									assetadd.setA_CapvsExp("Cap");
									assetadd.setM_InOutLine_ID(rs.getInt("C_Invoice_ID"));				
									assetadd.setC_Invoice_ID(rs.getInt("C_Invoice_ID"));
									assetadd.setDocumentNo(Invoice.getDocumentNo());
									assetadd.setLine(InvoiceLine.getLine());
									assetadd.setDescription(InvoiceLine.getDescription());
									assetadd.setA_QTY_Current(InvoiceLine.getQtyEntered());
									assetadd.setPostingType(assetwk.getPostingType());
									assetadd.save();
									
									
						            asset.setA_QTY_Original(assetadd.getA_QTY_Current().add(asset.getA_QTY_Original()));
						            asset.setA_QTY_Current(assetadd.getA_QTY_Current().add(asset.getA_QTY_Current()));
						            asset.save();
						            
						            MAssetChange change = new MAssetChange (getCtx(), 0, get_TrxName());
						            change.setA_Asset_ID(asset.getA_Asset_ID());            
						            change.setA_QTY_Current(assetadd.getA_QTY_Current());           
						            change.setChangeType("ADD");
						            change.setTextDetails(MRefList.getListDescription (getCtx(),"A_Update_Type" , "ADD"));
						            change.setPostingType(assetwk.getPostingType());
						            change.setAssetValueAmt(assetadd.getAssetValueAmt());
						            change.setA_QTY_Current(assetadd.getA_QTY_Current());            
						            change.save();
						            
						            
						            
						            
									//Determine non tax accounts to credit
									invoiceAcct = determineAcct (assetacct.getC_AcctSchema_ID(), InvoiceLine.getM_Product_ID(), InvoiceLine.getC_Charge_ID(), InvoiceLine.getLineNetAmt());	
																
									//Create Journal Entries for the new asset
									X_A_Depreciation_Exp depexp2 = new X_A_Depreciation_Exp (getCtx(), 0, get_TrxName());						 			
									depexp2.setPostingType(assetacct.getPostingType());
									depexp2.setA_Asset_ID(asset.getA_Asset_ID());
									depexp2.setExpense(InvoiceLine.getLineTotalAmt());
									depexp2.setDateAcct(Invoice.getDateAcct());
									depexp2.setA_Account_Number(assetacct.getA_Asset_Acct());
									depexp2.setDescription("Create Asset from Invoice");
									depexp2.setIsDepreciated(false);
									depexp2.setA_Period(C_Period_ID);
									depexp2.setA_Entry_Type("NEW");
									depexp2.save();
									recordInsertedCount++;
									
									X_A_Depreciation_Exp depexp3 = new X_A_Depreciation_Exp (getCtx(), 0, get_TrxName());
									depexp3.setPostingType(assetacct.getPostingType());
									depexp3.setA_Asset_ID(asset.getA_Asset_ID());
									depexp3.setExpense(InvoiceLine.getLineNetAmt().multiply(new BigDecimal (-1)));
									depexp3.setDateAcct(Invoice.getDateAcct());
									depexp3.setA_Account_Number(invoiceAcct);
									depexp3.setDescription("Create Asset from Invoice");
									depexp3.setIsDepreciated(false);
									depexp3.setA_Period(C_Period_ID);			
									depexp3.setA_Entry_Type("NEW");
									depexp3.save();
									recordInsertedCount++;
									
									//Determine if tax adjustment is necessary
									if (InvoiceLine.getTaxAmt().compareTo(new BigDecimal (0))!=0){
										
									invoiceAcct = determineTaxAcct (assetacct.getC_AcctSchema_ID(), InvoiceLine.getC_Tax_ID());
									
									X_A_Depreciation_Exp depexp4 = new X_A_Depreciation_Exp (getCtx(), 0, get_TrxName());						 			
									depexp4.setPostingType(assetacct.getPostingType());
									depexp4.setA_Asset_ID(asset.getA_Asset_ID());
									depexp4.setExpense(InvoiceLine.getTaxAmt().multiply(new BigDecimal (-1)));
									depexp4.setDateAcct(Invoice.getDateAcct());
									depexp4.setA_Account_Number(invoiceAcct);
									depexp4.setDescription("Create Asset from Invoice");
									depexp4.setIsDepreciated(false);
									depexp4.setA_Period(C_Period_ID);
									depexp4.setA_Entry_Type("NEW");
									depexp4.save();							
									recordInsertedCount++;
									}					            
		
								}
							}
							catch (Exception e)
							{
								log.info("getAssets "+ e);
							}
							finally
							{
								DB.close(rs3, pstmt2);
								pstmt2 = null;
							}
					    }
					}
									
					}
					DB.close(rs2, pstmt1);
					pstmt1 = null;	
				}
				else if (rs.getString("A_CapvsExp").equals("Exp"))				
				{
					X_A_Asset_Addition assetadd = new X_A_Asset_Addition (getCtx(), 0, get_TrxName());
					assetadd.setA_Asset_ID(asset.getA_Asset_ID());
					assetadd.setAssetValueAmt(InvoiceLine.getLineTotalAmt());
					assetadd.setA_SourceType("INV");
					assetadd.setA_CapvsExp("Exp");
					assetadd.setM_InOutLine_ID(rs.getInt("C_Invoice_ID"));				
					assetadd.setC_Invoice_ID(rs.getInt("C_Invoice_ID"));
					assetadd.setDocumentNo(Invoice.getDocumentNo());
					assetadd.setLine(InvoiceLine.getLine());
					assetadd.setDescription(InvoiceLine.getDescription());
					assetadd.setA_QTY_Current(InvoiceLine.getQtyEntered());
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
				InvoiceLine.set_ValueOfColumn(I_CustomColumn.A_Processed, Boolean.TRUE);
				InvoiceLine.save();
			}

			}
			catch (Exception e)
			{
				log.info("getAssets "+ e);
			}
			finally
			{
				DB.close(rs, pstmt);
				pstmt = null;
			}
			
			if (recordInsertedCount > 0) 
			{
				return recordInsertedCount + " record(s) inserted.";
			}
			else 
			{
				return "Zero record inserted.";
			}
	}	//	doIt
	
	/**
	 *  Get non tax posting accounts for invoice.  
	 *  
	 *  
	 */
	private int determineAcct (int C_AcctSchema_ID, int M_Product_ID, int C_Charge_ID, BigDecimal lineAmt)
	{
		int invoiceAcct =0;
		if (M_Product_ID == 0 && C_Charge_ID != 0)
		{	
			if(lineAmt.signum() > 0){
				String sqlb = "SELECT CH_Expense_Acct FROM C_Charge_Acct WHERE C_Charge_ID=? and C_AcctSchema_ID=?";
				invoiceAcct = DB.getSQLValue(get_TrxName(),sqlb,C_Charge_ID,C_AcctSchema_ID);
			}
			else{
				String sqlb = "SELECT CH_Revenue_Acct FROM C_Charge_Acct WHERE C_Charge_ID=? and C_AcctSchema_ID=?";
				invoiceAcct = DB.getSQLValue(get_TrxName(),sqlb,C_Charge_ID,C_AcctSchema_ID);
			}									
		}
		
		else if(M_Product_ID != 0){
			if(lineAmt.signum() > 0){
				String sqlb = "SELECT P_Expense_Acct FROM M_Product_Acct WHERE M_Product_ID=? and C_AcctSchema_ID=?";
				invoiceAcct = DB.getSQLValue(get_TrxName(),sqlb,M_Product_ID,C_AcctSchema_ID);
			}
			else{
				String sqlb = "SELECT P_Revenue_Acct FROM M_Product_Acct WHERE M_Product_ID=? and C_AcctSchema_ID=?";
				invoiceAcct = DB.getSQLValue(get_TrxName(),sqlb,M_Product_ID,C_AcctSchema_ID);
			}
		}
		else{
			if(lineAmt.signum() > 0){
				String sqlb = "SELECT P_Expense_Acct "										
					+ "FROM M_Product_Category pc, M_Product_Category_Acct pca "
					+ "WHERE pc.M_Product_Category_ID=pca.M_Product_Category_ID"
					+ " AND pca.C_AcctSchema_ID=? "
					+ "ORDER BY pc.IsDefault DESC, pc.Created";
				invoiceAcct = DB.getSQLValue(get_TrxName(),sqlb,C_AcctSchema_ID);
			}
			else{
				String sqlb = "SELECT P_Revenue_Acct "										
					+ "FROM M_Product_Category pc, M_Product_Category_Acct pca "
					+ "WHERE pc.M_Product_Category_ID=pca.M_Product_Category_ID"
					+ " AND pca.C_AcctSchema_ID=? "
					+ "ORDER BY pc.IsDefault DESC, pc.Created";
				invoiceAcct = DB.getSQLValue(get_TrxName(),sqlb,C_AcctSchema_ID);
			}
			
		}
		return invoiceAcct;
	}
	
	/**
	 *  Get tax posting accounts for invoice.  
	 *  
	 *  
	 */
	private int determineTaxAcct (int C_AcctSchema_ID, int C_Tax_ID)
	{
		int invoiceAcct =0;
		
		String sqlb = "SELECT T_Expense_Acct FROM C_Tax_Acct WHERE C_AcctSchema_ID=? and C_Tax_ID=?";
		invoiceAcct = DB.getSQLValue(get_TrxName(),sqlb,C_AcctSchema_ID,C_Tax_ID);
		
		return invoiceAcct;
	}
	
}	//	InvoiceCreateInOut
