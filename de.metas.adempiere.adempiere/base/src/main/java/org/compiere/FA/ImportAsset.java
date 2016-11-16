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
import java.sql.SQLException;
import java.sql.Timestamp;

import org.compiere.util.DB;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 *	Import Assets
 *
 * 	@author 	Rob Klein
 * 	@version 	$Id: ImportAsset.java,v 1.0 $
 */
public class ImportAsset extends SvrProcess 
{
	/**
	 * 	Import Asset
	 */
	public ImportAsset()
	{
		super();
		
	}	//	ImportAsset

	/**	Client to be imported to		*/
	private int				m_AD_Client_ID = 0;
	/**	Delete old Imported				*/
	private boolean			m_deleteOldImported = false;

	/** Organization to be imported to	*/
	private int				m_AD_Org_ID = 0;
	/** Effective						*/
	private Timestamp	m_DateValue = null;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_Client_ID"))
				m_AD_Client_ID = (para[i].getParameterAsInt());
			else if (name.equals("DeleteOldImported"))
				m_deleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.info("ImportAsset.prepare - Unknown Parameter: " + name);
		}
			m_DateValue = new Timestamp (System.currentTimeMillis());
			//java.util.Date today = new java.util.Date();
			//m_DateValue =  new java.sql.Date(today.getTime());

	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;

		//	****	Prepare	****

		//	Delete Old Imported
		/**if (m_deleteOldImported)
		{
			sql = new StringBuffer ("DELETE FROM I_Asset "
				+ "WHERE I_IsImported='Y'").append(clientCheck);
			no = DB.executeUpdate(sql.toString());			
		}**/

		//	Set Client, Org, IaActive, Created/Updated, 	ProductType
		sql = new StringBuffer ("UPDATE I_Asset "
			+ "SET AD_Client_ID = COALESCE (AD_Client_ID, ").append(m_AD_Client_ID).append("),"
			+ " AD_Org_ID = COALESCE (AD_Org_ID, 0),"
			+ " IsActive = COALESCE (IsActive, 'Y'),"			
			+ " CreatedBy = COALESCE (CreatedBy, 0),"
			+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
			+ " I_ErrorMsg = NULL,"
			+ " I_IsImported = 'N' "
			+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate(sql.toString(),null);		


		//	Set Currency
		/**sql = new StringBuffer ("UPDATE I_Asset i "
			+ "SET ISO_Code=(SELECT ISO_Code FROM C_Currency c"
			+ " INNER JOIN C_AcctSchema a ON (a.C_Currency_ID=c.C_Currency_ID)"
			+ " INNER JOIN AD_ClientInfo ci ON (a.C_AcctSchema_ID=ci.C_AcctSchema1_ID)"
			+ " WHERE ci.AD_Client_ID=i.AD_Client_ID) "
			+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString());
		Log.trace(Log.l5_DData, "ImportAsset.doIt", "Set Currency Default=" + no);
		//
		sql = new StringBuffer ("UPDATE I_Asset i "
			+ "SET C_Currency_ID=(SELECT C_Currency_ID FROM C_Currency c"
			+ " WHERE i.ISO_Code=c.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
			+ "WHERE C_Currency_ID IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString());
		Log.trace(Log.l5_DData, "ImportAsset.doIt", "Set Currency=" + no);
		//
		sql = new StringBuffer ("UPDATE I_Asset "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Currency,' "
			+ "WHERE C_Currency_ID IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString());
		Log.trace(Log.l3_Util, "ImportAsset.doIt", "Invalid Currency=" + no);
         **/		
	
		//	-------------------------------------------------------------------
		int noInsert = 0;
		int noUpdate = 0;
		//int noInsertPO = 0;
		//int noUpdatePO = 0;

		//	Go through Records
		sql = new StringBuffer ("SELECT I_Asset_ID, A_Asset_ID "
			+ "FROM I_Asset WHERE I_IsImported='N'").append(clientCheck).append(" Order By I_Asset_ID");		    
//		Connection conn = DB.createConnection(false, Connection.TRANSACTION_READ_COMMITTED);
		try
		{			
			//	Insert Asset from Import
			StringBuffer sqlA = new StringBuffer ("INSERT INTO A_Asset (A_Asset_ID,"
				+ "AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
				+ "Value,Name,Description,Help,"
				+ "A_Asset_Group_ID,M_Product_ID,SerNo,LOT,VersionNo,GuaranteeDate,"
				+ "AssetServiceDate,IsOwned,AssetDepreciationDate, UseLifeYears, UseLifeMonths," 
				+ "LifeUseUnits, UseUnits, Isdisposed, AssetDisposalDate, IsInPosession," 
				+ "LocationComment, M_Locator_ID, C_BPartner_ID, C_BPartner_Location_ID,"
				+ "C_Location_ID, IsDepreciated, IsFullyDepreciated, AD_User_ID,"
				+ "M_AttributeSetInstance_ID, A_Parent_Asset_ID, A_QTY_Original,"
				+ "A_QTY_Current) "
				+ "SELECT ?,"
				+ "AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
				+ "Value,Name,Description,Help,"				
				+ "A_Asset_Group_ID,M_Product_ID,SerNo,LOT,VersionNo,GuaranteeDate,"
				+ "AssetServiceDate,IsOwned,AssetDepreciationDate, UseLifeYears, UseLifeMonths," 
				+ "LifeUseUnits, UseUnits, Isdisposed, AssetDisposalDate, IsInPosession," 
				+ "LocationComment, M_Locator_ID, C_BPartner_ID, C_BPartner_Location_ID,"
				+ "C_Location_ID, IsDepreciated, IsFullyDepreciated, AD_User_ID,"
				+ "M_AttributeSetInstance_ID, A_Parent_Asset_ID, A_QTY_Original,"
				+ "A_QTY_Current "
				+ "FROM I_Asset "
				+ "WHERE I_Asset_ID=?");
				PreparedStatement pstmt_insertProduct =  DB.prepareStatement(sqlA.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE, null);

			//	Update Assets from Import
				StringBuffer sqlB = new StringBuffer ("UPDATE A_Asset "
				+ "SET( A_Asset_ID,"
				+ "AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
				+ "Value,Name,Description,Help,"
				+ "A_Asset_Group_ID,M_Product_ID,SerNo,LOT,VersionNo,GuaranteeDate,"
				+ "AssetServiceDate,IsOwned,AssetDepreciationDate, UseLifeYears, UseLifeMonths," 
				+ "LifeUseUnits, UseUnits, Isdisposed, AssetDisposalDate, IsInPosession," 
				+ "LocationComment, M_Locator_ID, C_BPartner_ID, C_BPartner_Location_ID,"
				+ "C_Location_ID, IsDepreciated, IsFullyDepreciated, AD_User_ID,"
				+ "M_AttributeSetInstance_ID, A_Parent_Asset_ID, A_QTY_Original,"
				+ "A_QTY_Current) = "
				+ "(SELECT A_Asset_ID,"
				+ "AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
				+ "Value,Name,Description,Help,"				
				+ "A_Asset_Group_ID,M_Product_ID,SerNo,LOT,VersionNo,GuaranteeDate,"
				+ "AssetServiceDate,IsOwned,AssetDepreciationDate, UseLifeYears, UseLifeMonths," 
				+ "LifeUseUnits, UseUnits, Isdisposed, AssetDisposalDate, IsInPosession," 
				+ "LocationComment, M_Locator_ID, C_BPartner_ID, C_BPartner_Location_ID,"
				+ "C_Location_ID, IsDepreciated, IsFullyDepreciated, AD_User_ID,"
				+ "M_AttributeSetInstance_ID, A_Parent_Asset_ID, A_QTY_Original,"
				+ "A_QTY_Current "	
				+ "FROM I_Asset "
				+ "WHERE I_Asset_ID=?) "
				+ "WHERE A_Asset_ID=?");
				PreparedStatement pstmt_updateProduct =  DB.prepareStatement(sqlB.toString(), ResultSet.TYPE_SCROLL_SENSITIVE,
		ResultSet.CONCUR_UPDATABLE, null);

			//	Insert Asset Accounts from Import			
				StringBuffer sqlC = new StringBuffer ("INSERT INTO A_Asset_Acct ("
				+ "A_ASSET_ID, C_ACCTSCHEMA_ID, AD_CLIENT_ID,"
				+ "AD_ORG_ID,ISACTIVE, CREATED, CREATEDBY, UPDATED ,UPDATEDBY," 
				+ "A_DEPRECIATION_ID, A_DEPRECIATION_ACCT, A_ACCUMDEPRECIATION_ACCT," 
				+ "A_DISPOSAL_LOSS, A_DISPOSAL_REVENUE, A_ASSET_ACCT,A_ASSET_SPREAD_ID," 
				+ "A_DEPRECIATION_METHOD_ID,A_PERIOD_START,A_PERIOD_END, A_DEPRECIATION_CONV_ID," 
				+ "A_SALVAGE_VALUE, POSTINGTYPE, A_SPLIT_PERCENT, A_DEPRECIATION_MANUAL_AMOUNT, " 
				+ "A_DEPRECIATION_MANUAL_PERIOD, A_DEPRECIATION_VARIABLE_PERC, A_ASSET_ACCT_ID) "
				+ "SELECT ?,C_ACCTSCHEMA_ID,"
				+ "AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
				+ "DEPRECIATIONTYPE, A_DEPRECIATION_ACCT, A_ACCUMDEPRECIATION_ACCT," 
				+ "A_DISPOSAL_LOSS, A_DISPOSAL_REVENUE, A_ASSET_ACCT,A_ASSET_SPREAD_TYPE," 
				+ "A_DEPRECIATION_CALC_TYPE,A_PERIOD_START,A_PERIOD_END, CONVENTIONTYPE," 
				+ "A_SALVAGE_VALUE, POSTINGTYPE, A_SPLIT_PERCENT, A_DEPRECIATION_MANUAL_AMOUNT," 
				+ "A_DEPRECIATION_MANUAL_PERIOD, A_DEPRECIATION_VARIABLE_PERC, ? "
				+ "FROM I_Asset "
				+ "WHERE I_Asset_ID=?");
			PreparedStatement pstmt_insertAssetAcct =  DB.prepareStatement(sqlC.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE, null);
				
			//	Insert Asset Balances from Import
			StringBuffer sqlD = new StringBuffer ("INSERT INTO A_DEPRECIATION_WORKFILE ("
				+ "A_DEPRECIATION_WORKFILE_ID, AD_CLIENT_ID, AD_ORG_ID, ISACTIVE,"
				+ "CREATED, CREATEDBY, UPDATED, UPDATEDBY, A_ASSET_ID, A_ASSET_COST,"
				+ "A_ACCUMULATED_DEPR, A_CALC_ACCUMULATED_DEPR, A_LIFE_PERIOD," 
				+ "A_PERIOD_POSTED, A_CURRENT_PERIOD, A_PRIOR_YEAR_ACCUMULATED_DEPR,"
				+ "A_BASE_AMOUNT, A_SALVAGE_VALUE, A_CURR_DEP_EXP, A_ASSET_LIFE_YEARS,"
				+ "A_ASSET_LIFE_CURRENT_YEAR, ISDEPRECIATED, POSTINGTYPE, A_QTY_CURRENT" 
				+ ")"
				+ "SELECT "
				+ "?, AD_CLIENT_ID, AD_ORG_ID, ISACTIVE,"
				+ "CREATED, CREATEDBY, UPDATED, UPDATEDBY, ?, A_ASSET_COST,"
				+ "A_ACCUMULATED_DEPR, A_CALC_ACCUMULATED_DEPR, A_LIFE_PERIOD," 
				+ "A_PERIOD_POSTED, A_CURRENT_PERIOD, A_PRIOR_YEAR_ACCUMULATED_DEPR,"
				+ "A_BASE_AMOUNT, A_SALVAGE_VALUE, A_CURR_DEP_EXP, A_ASSET_LIFE_YEARS,"
				+ "A_ASSET_LIFE_CURRENT_YEAR, ISDEPRECIATED, POSTINGTYPE, A_QTY_CURRENT"
				+ " "
				+ "FROM I_Asset "
				+ "WHERE I_Asset_ID=?");
			PreparedStatement pstmt_insertAssetBal =  DB.prepareStatement(sqlD.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE, null);
				
			//	Insert Asset Additions from Import
			StringBuffer sqlE = new StringBuffer ("INSERT INTO A_Asset_Addition ("
				+ " A_ASSET_ADDITION_ID, AD_CLIENT_ID, AD_ORG_ID, ISACTIVE,"
				+ " CREATED, CREATEDBY,  UPDATED, UPDATEDBY, A_ASSET_ID,"
				+ " ASSETVALUEAMT, DESCRIPTION,  M_INOUTLINE_ID, "
				+ " POSTINGTYPE, A_QTY_CURRENT, A_SOURCETYPE, A_CAPVSEXP) "
				+ "SELECT "
				+ " ?, AD_CLIENT_ID, AD_ORG_ID, ISACTIVE,"
				+ " CREATED, CREATEDBY,  UPDATED, UPDATEDBY, ?,"
				+ " A_ASSET_COST, 'Imported Asset', '1', "
				+ " POSTINGTYPE, A_QTY_CURRENT, 'IMP', 'Cap' "
				+ "FROM I_Asset "
				+ "WHERE I_Asset_ID=?");
			PreparedStatement pstmt_insertAssetAdd =  DB.prepareStatement(sqlE.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE, null);
					
			//	Insert Asset Additions from Import
			StringBuffer sqlF = new StringBuffer ("INSERT INTO A_Asset_Change ("
				+ "A_ASSET_CHANGE_ID, AD_CLIENT_ID, AD_ORG_ID, ISACTIVE, "
				+ "CREATED, CREATEDBY, UPDATED, UPDATEDBY, A_ASSET_ID, "
				+ "CHANGETYPE,  USELIFEYEARS, " 
				+ "USELIFEMONTHS, LIFEUSEUNITS, ASSETDEPRECIATIONDATE, "
				+ "A_ASSET_ADDITION_ID, SERNO," 
				+ "LOT, VERSIONNO, ASSETACCUMDEPRECIATIONAMT, TEXTDETAILS, " 
				+ "ASSETSERVICEDATE, ASSETBOOKVALUEAMT, ASSETMARKETVALUEAMT, "
				+ "ASSETVALUEAMT, ASSETDISPOSALDATE, A_PARENT_ASSET_ID, "
				+ "C_BPARTNER_ID, C_BPARTNER_LOCATION_ID, C_LOCATION_ID, "
				+ "A_DEPRECIATION_ACCT, A_ACCUMDEPRECIATION_ACCT, A_DISPOSAL_LOSS, " 
				+ "A_DISPOSAL_REVENUE, A_ASSET_ACCT, A_ASSET_SPREAD_TYPE, "
				+ "A_DEPRECIATION_CALC_TYPE, A_PERIOD_START, A_PERIOD_END, "
				+ "A_SALVAGE_VALUE, POSTINGTYPE, A_ASSET_ACCT_ID, CONVENTIONTYPE, " 
				+ "A_SPLIT_PERCENT, DEPRECIATIONTYPE, A_QTY_CURRENT, ISDEPRECIATED, " 
				+ "ISFULLYDEPRECIATED, ISINPOSESSION, ISDISPOSED, ISOWNED) "
				+ "SELECT " 
				+ "?, AD_CLIENT_ID, AD_ORG_ID, ISACTIVE, "
				+ "CREATED, CREATEDBY, UPDATED, UPDATEDBY, ?, "
				+ "'IMP',  USELIFEYEARS, " 
				+ "USELIFEMONTHS, LIFEUSEUNITS, ASSETDEPRECIATIONDATE, "
				+ "?, SERNO, " 
				+ "LOT, VERSIONNO, A_ACCUMULATED_DEPR, 'Imported Fixed Asset', " 
				+ "ASSETSERVICEDATE, A_BASE_AMOUNT, ASSETMARKETVALUEAMT, "
				+ "A_ASSET_COST, ASSETDISPOSALDATE, A_PARENT_ASSET_ID, "
				+ "C_BPARTNER_ID, C_BPARTNER_LOCATION_ID, C_LOCATION_ID, "
				+ "A_DEPRECIATION_ACCT, A_ACCUMDEPRECIATION_ACCT, A_DISPOSAL_LOSS, " 
				+ "A_DISPOSAL_REVENUE, A_ASSET_ACCT, A_ASSET_SPREAD_TYPE, "
				+ "A_DEPRECIATION_CALC_TYPE, A_PERIOD_START, A_PERIOD_END, "
				+ "A_SALVAGE_VALUE, POSTINGTYPE, ?, CONVENTIONTYPE, " 
				+ "A_SPLIT_PERCENT, DEPRECIATIONTYPE, A_QTY_CURRENT, ISDEPRECIATED, " 
				+ "ISFULLYDEPRECIATED, ISINPOSESSION, ISDISPOSED, ISOWNED "
				+ "FROM I_Asset "
				+ "WHERE I_Asset_ID=?");
			PreparedStatement pstmt_insertAssetChg =  DB.prepareStatement(sqlF.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE, null);
/*
			//	Update Asset Accounts from Import
			PreparedStatement pstmt_updateAssetAcct = conn.prepareStatement
				("UPDATE A_Asset_Acct "
				+ "SET(" 
				+ "A_ASSET_ID, C_ACCTSCHEMA_ID, AD_CLIENT_ID"
				+ "AD_ORG_ID,ISACTIVE, CREATED, CREATEDBY, UPDATED ,UPDATEDBY" 
				+ "A_DEPRECIATION_ID, A_DEPRECIATION_ACCT, A_ACCUMDEPRECIATION_ACCT" 
				+ "A_DISPOSAL_LOSS, A_DISPOSAL_REVENUE, A_ASSET_ACCT,A_ASSET_SPREAD_ID" 
				+ "A_DEPRECIATION_METHOD_ID,A_PERIOD_START,A_PERIOD_END" 
				+ "A_SALVAGE_VALUE, POSTINGTYPE, A_SPLIT_PERCENT) = "
				+ "(SELECT ?,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
				+ "DEPRECIATIONTYPE, A_DEPRECIATION_ACCT, A_ACCUMDEPRECIATION_ACCT" 
				+ "A_DISPOSAL_LOSS, A_DISPOSAL_REVENUE, A_ASSET_ACCT,A_ASSET_SPREAD_TYPE" 
				+ "A_DEPRECIATION_METHOD_ID,A_PERIOD_START,A_PERIOD_END" 
				+ "A_SALVAGE_VALUE, POSTINGTYPE, A_SPLIT_PERCENT"	
				+ "FROM I_Asset "
				+ "WHERE I_Asset_ID=?) "
				+ "WHERE A_Asset_ID=?");

*/
			//	Set Imported = Y
			PreparedStatement pstmt_setImported = DB.prepareStatement
				("UPDATE I_Asset SET I_IsImported='Y', "
				//+ "Updated= TO_DATE('"+m_DateValue+"','YYYY-MM-DD HH24:MI:SS.FFF') "
				+ "Processed='Y' WHERE I_Asset_ID=?",ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE, null);

			//
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE, null);
			ResultSet rs = pstmt.executeQuery();
			int x = 0;
			
			while (rs.next())
			{		
				x=x+1;
				
				int I_Asset_ID = rs.getInt(1);
				int A_Asset_ID = rs.getInt(2);
				int A_Asset_Acct_ID = 0;
				int A_DEPRECIATION_WORKFILE_ID = 0;
				int A_ASSET_ADDITION_ID = 0;
				int A_ASSET_CHANGE_ID = 0;
				boolean newAsset = true;
				
				if (A_Asset_ID == 0)
				{				
				A_Asset_ID = DB.getNextID(m_AD_Client_ID, "A_Asset", null);
				A_Asset_Acct_ID = DB.getNextID(m_AD_Client_ID, "A_Asset_Acct", null);
				A_DEPRECIATION_WORKFILE_ID = DB.getNextID(m_AD_Client_ID, "A_Depreciation_Workfile", null);				
				A_ASSET_ADDITION_ID = DB.getNextID(m_AD_Client_ID, "A_Asset_Addition", null);
				A_ASSET_CHANGE_ID = DB.getNextID(m_AD_Client_ID, "A_Asset_Change", null);
			
				newAsset = true;
				}
				else
				{
			
				// Check to see if Asset_ID exists
						int found = DB.getSQLValue(null,"SELECT COUNT(*) FROM A_ASSET WHERE A_ASSET_ID = " + A_Asset_ID + clientCheck);
					if (found == 0) 
					{
			
						newAsset = true;
						A_DEPRECIATION_WORKFILE_ID = DB.getNextID(m_AD_Client_ID, "A_Depreciation_Workfile", null);				
						A_ASSET_ADDITION_ID = DB.getNextID(m_AD_Client_ID, "A_Asset_Addition", null);
						A_ASSET_CHANGE_ID = DB.getNextID(m_AD_Client_ID, "A_Asset_Change", null);
						A_Asset_Acct_ID = DB.getNextID(m_AD_Client_ID, "A_Asset_Acct", null);
					}
					else
					{
			
						newAsset = false;
					}
				}									
				

				//	Product
				if (newAsset)			//	Insert new Asset
				{				
			
					pstmt_insertProduct.setInt(1, A_Asset_ID);
					pstmt_insertProduct.setInt(2, I_Asset_ID);
					
					pstmt_insertAssetAcct.setInt(1, A_Asset_ID);
					pstmt_insertAssetAcct.setInt(2, A_Asset_Acct_ID);
					pstmt_insertAssetAcct.setInt(3, I_Asset_ID);					
					
					pstmt_insertAssetBal.setInt(1, A_DEPRECIATION_WORKFILE_ID);
					pstmt_insertAssetBal.setInt(2, A_Asset_ID);
					pstmt_insertAssetBal.setInt(3, I_Asset_ID);
					
					pstmt_insertAssetAdd.setInt(1, A_ASSET_ADDITION_ID);
					pstmt_insertAssetAdd.setInt(2, A_Asset_ID);
					pstmt_insertAssetAdd.setInt(3, I_Asset_ID);
					
					pstmt_insertAssetChg.setInt(1, A_ASSET_CHANGE_ID);
					pstmt_insertAssetChg.setInt(2, A_Asset_ID);
					pstmt_insertAssetChg.setInt(3, A_ASSET_ADDITION_ID);
					pstmt_insertAssetChg.setInt(4, A_Asset_Acct_ID);
					pstmt_insertAssetChg.setInt(5, I_Asset_ID);
					
					try
					{
			
						no = pstmt_insertProduct.executeUpdate();
						no = pstmt_insertAssetAcct.executeUpdate();			
						no = pstmt_insertAssetBal.executeUpdate();			
						no = pstmt_insertAssetAdd.executeUpdate();						
						no = pstmt_insertAssetChg.executeUpdate();						
				
						noInsert++;
					}
					catch (SQLException ex)
					{
						
						sql = new StringBuffer ("UPDATE I_Asset "
							+ "SET I_IsImported='E', I_ErrorMsg=").append(DB.TO_STRING("Insert Asset: " + ex.toString()))
							.append(" WHERE I_Asset_ID=").append(I_Asset_ID);
						
						DB.executeUpdate(sql.toString(),null);
						continue;
					}
				}
				else					//	Update Asset
				{
					
					pstmt_updateProduct.setInt(1, I_Asset_ID);					
					pstmt_updateProduct.setInt(2, A_Asset_ID);
					
					try
					{						
						//String sqlcall = "UPDATE A_Asset SET(AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Value,Name,Description,Help,A_Asset_Group_ID,M_Product_ID,SerNo,LOT,VersionNo,GuaranteeDate,AssetServiceDate,IsOwned,AssetDepreciationDate, UseLifeYears, UseLifeMonths,LifeUseUnits, UseUnits, Isdisposed, AssetDisposalDate, IsInPosession,LocationComment, M_Locator_ID, C_BPartner_ID, C_BPartner_Location_ID,C_Location_ID, IsDepreciated, IsFullyDepreciated, AD_User_ID,M_AttributeSetInstance_ID, A_Parent_Asset_ID, A_QTY_Original,A_QTY_Current) = (SELECT AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Value,Name,Description,Help,A_Asset_Group_ID,M_Product_ID,SerNo,LOT,VersionNo,GuaranteeDate,AssetServiceDate,IsOwned,AssetDepreciationDate, UseLifeYears, UseLifeMonths,LifeUseUnits, UseUnits, Isdisposed, AssetDisposalDate, IsInPosession,LocationComment, M_Locator_ID, C_BPartner_ID, C_BPartner_Location_ID,C_Location_ID, IsDepreciated, IsFullyDepreciated, AD_User_ID,M_AttributeSetInstance_ID, A_Parent_Asset_ID, A_QTY_Original,A_QTY_Current FROM I_Asset WHERE I_Asset_ID=1000000) WHERE A_Asset_ID=2005000";
						//pstmt_updateProduct =  prepareStatement(sqlB.toString(), ResultSet.TYPE_FORWARD_ONLY,	ResultSet.CONCUR_UPDATABLE, null);
						pstmt_updateProduct.executeUpdate();
						noUpdate++;
					}
					catch (SQLException ex)
					{
					
						sql = new StringBuffer ("UPDATE I_Asset "
							+ "SET I_IsImported='E', I_ErrorMsg=").append(DB.TO_STRING("Update Asset: " + ex.toString()))
							.append(" WHERE I_Asset_ID=").append(I_Asset_ID);
						//DB.executeUpdate(sql.toString());
						continue;
					}
				}
			pstmt_setImported.setInt(1, I_Asset_ID);
			no = pstmt_setImported.executeUpdate();
			//conn.commit();
			}	//	for all I_Asset
			rs.close();
			pstmt.close();

			//
			pstmt_insertProduct.close();
			pstmt_updateProduct.close();
			pstmt_setImported.close();
			//
			//conn.close();
			//conn = null;
		}
		catch (SQLException e)
		{
			
			throw new Exception ("ImportAsset3.doIt", e);
		}
		finally
		{
			;
		}


		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_Asset "
			+ "SET I_IsImported='N' " 
			//+ "Updated= TO_DATE('"+m_DateValue+"','YYYY-MM-DD HH24:MI:SS.FFF') "
			+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(),null);
		addLog (0, null, new BigDecimal (no), "@Errors@");
		addLog (0, null, new BigDecimal (noInsert), "@A_Asset_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noUpdate), "@A_Asset_ID@: @Updated@");
		return "";
	}	//	doIt

}	//	ImportAsset
