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

import org.compiere.model.X_A_Asset;
import org.compiere.model.X_A_Asset_Reval_Entry;
import org.compiere.model.X_A_Asset_Reval_Index;
import org.compiere.model.X_A_Depreciation_Exp;
import org.compiere.model.X_C_Period;
import org.compiere.util.DB;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;


/**
 *	Asset Revaluation Entry
 *
 *  @author Rob Klein
 *  @version $Id: AssetRevalEntry.java,v 1.0$
 */
public class AssetRevalEntry extends JavaProcess
{
	/** Record ID				*/
	private int p_Asset_Reval_Entry_ID = 0;
	private boolean	m_DeleteOld = false;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
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
		p_Asset_Reval_Entry_ID = getRecord_ID();
	}	//	prepare


	/**
	 * 	Build Depreciation Workfile
	 *	@return info
	 *	@throws Exception
	 */
	@Override
	protected String doIt() throws java.lang.Exception
	{
		log.info("doIt - Asset_Reval_Entry_ID=" + p_Asset_Reval_Entry_ID);
		if (p_Asset_Reval_Entry_ID == 0)
			throw new IllegalArgumentException("No Record");

		//
		int no = 0;
		BigDecimal v_Cost_reval = new BigDecimal("0.0");
		BigDecimal v_Cost_reval_pior = new BigDecimal("0.0");
		BigDecimal v_Accum_reval = new BigDecimal("0.0");
		BigDecimal v_Accum_reval_prior = new BigDecimal("0.0");
		BigDecimal v_Current_exp_reval = new BigDecimal("0.0");
		BigDecimal v_Multipler = new BigDecimal("0.0");
		BigDecimal v_Dep_Exp_reval = new BigDecimal("0.0");

		X_A_Asset_Reval_Entry AssetReval = new X_A_Asset_Reval_Entry (getCtx(), p_Asset_Reval_Entry_ID, null);
		X_C_Period Period = new X_C_Period (getCtx(), AssetReval.getC_Period_ID(), null);

		String sql = null;
		log.info("doIt - Starting Transfer = " + no);
		String clientCheck = " AND AD_Client_ID=" + AssetReval.getAD_Client_ID();

		sql = "SELECT A.A_REVAL_COST_OFFSET, A.A_REVAL_ACCUMDEP_OFFSET_CUR, A.A_REVAL_DEPEXP_OFFSET "
			+ " A.A_ACCUMDEPRECIATION_ACCT, A.A_ASSET_ACCT, A.A_DEPRECIATION_ACCT, "
			+ " A.A_REVAL_ACCUMDEP_OFFSET_PRIOR, A.A_REVAL_COST_OFFSET_PRIOR "
			+ " B.A_ASSET_ID, B.A_ASSET_COST, B.A_ACCUMULATED_DEPR, B.A_ASSET_COST_REVAL, B.A_ACCUMULATED_DEPR_REVAL"
			+ " FROM A_ASSET_ACCT A, A_DEPRECIATION_WORKFILE B"
			+ " WHERE A.POSTINGTYPE = '" + AssetReval.getPostingType()
			+ " AND B.POSTINGTYPE = '" + AssetReval.getPostingType()
			+ " AND A.AD_Client_ID=" + AssetReval.getAD_Client_ID()
			+ " AND B.AD_Client_ID=" + AssetReval.getAD_Client_ID()
			+ " AND A.AD_Asset_ID = A.AD_Asset_ID";

		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement (sql,null);
		log.info("doIt - SQL=" + sql);
		ResultSet rs = null;
		X_A_Depreciation_Exp depexp = new X_A_Depreciation_Exp (getCtx(), 0, null);

		if (m_DeleteOld)
		{
			sql =  "DELETE FROM A_DEPRECIATION_EXP "
				  + "WHERE Processed='Y'"
				  + " AND A_Entry_Type = 'RVL'" + clientCheck;

			no = DB.executeUpdate (sql,null);
			log.info("doIt - Delete old processed entries =" + no);
		}
		try {

			rs = pstmt.executeQuery();

			while (rs.next()){

				 String sql2 = "select A_ASSET_REVAL_INDEX_ID from A_ASSET_REVAL_INDEX where A_REVAL_CODE = " + AssetReval.getA_Rev_Code()
						   + " and A_EFFECTIVE_DATE = (select MAX(A_EFFECTIVE_DATE) from A_ASSET_REVAL_INDEX where  A_EFFECTIVE_DATE < "
						   + AssetReval.getA_Effective_Date()
						   + ")";

				 X_A_Asset_Reval_Index ARevalIndex = new X_A_Asset_Reval_Index (getCtx(), DB.getSQLValue(null ,sql), null);
				 X_A_Asset Asset = new X_A_Asset (getCtx(), rs.getInt("A_Asset_ID"), null);

				 if (AssetReval.getA_Reval_Multiplier().equals(X_A_Asset_Reval_Entry.A_REVAL_MULTIPLIER_Index))
				 {

				 	if (AssetReval.getA_Reval_Effective_Date().equals(X_A_Asset_Reval_Entry.A_REVAL_EFFECTIVE_DATE_DateAquired)){
				 		sql2 = "Select A_REVAL_MULTIPLIER from A_ASSET_REVAL_INDEX where A_REVAL_CODE = " + AssetReval.getA_Rev_Code()
						   + " and A_EFFECTIVE_DATE = (select MAX(A_EFFECTIVE_DATE) from A_ASSET_REVAL_INDEX where  A_EFFECTIVE_DATE < "
						   + Asset.getA_Asset_CreateDate()
						   + ")";

				 	}
				 	else if (AssetReval.getA_Reval_Effective_Date().equals(X_A_Asset_Reval_Entry.A_REVAL_EFFECTIVE_DATE_RevaluationDate)){
				 		sql2 = "Select A_REVAL_MULTIPLIER from A_ASSET_REVAL_INDEX where A_REVAL_CODE = " + AssetReval.getA_Rev_Code()
						   + " and A_EFFECTIVE_DATE = (select MAX(A_EFFECTIVE_DATE) from A_ASSET_REVAL_INDEX where  A_EFFECTIVE_DATE < "
						   + Asset.getA_Asset_RevalDate()
						   + ")";

				 	}
				 	else if (AssetReval.getA_Reval_Effective_Date().equals(X_A_Asset_Reval_Entry.A_REVAL_EFFECTIVE_DATE_DateDepreciationStarted)){
				 		sql2 = "Select A_REVAL_MULTIPLIER from A_ASSET_REVAL_INDEX where A_REVAL_CODE = " + AssetReval.getA_Rev_Code()
						   + " and A_EFFECTIVE_DATE = (select MAX(A_EFFECTIVE_DATE) from A_ASSET_REVAL_INDEX where  A_EFFECTIVE_DATE < "
						   + Asset.getAssetServiceDate()
						   + ")";

				 	}
				 	PreparedStatement pstmt2 = null;
					pstmt2 = DB.prepareStatement (sql2,null);
					ResultSet rs2 = null;
					log.info("doIt - SQL2=" + sql2);
					try {
						rs2 = pstmt2.executeQuery();
						while (rs2.next()){
						v_Multipler = ARevalIndex.getA_Reval_Rate().divide(rs2.getBigDecimal("A_REVAL_MULTIPLIER"),8);
					}

					v_Cost_reval = ((rs.getBigDecimal("A_Asset_Cost").subtract(rs.getBigDecimal("A_ASSET_COST_REVAL"))).multiply( v_Multipler)).subtract(rs.getBigDecimal("A_Asset_Cost"));
					v_Accum_reval = ((rs.getBigDecimal("A_ACCUMULATED_DEPR").subtract(rs.getBigDecimal("A_ACCUMULATED_DEPR_REVAL"))).multiply( v_Multipler)).subtract(rs.getBigDecimal("A_ACCUMULATED_DEPR"));

					sql2 = "SELECT SUM(CHANGEAMT) AS SUM_CHANGEAMT FROM A_ASSET_CHANGE WHERE A_ASSET_ID = "
						 + rs.getInt("A_Asset_ID")+ " AND CHANGETYPE = 'D'";
					pstmt2 = DB.prepareStatement (sql2,null);
					log.info("doIt - SQL2=" + sql2);
					try {
						rs2 = pstmt2.executeQuery();
						while (rs2.next()){
							v_Dep_Exp_reval = (rs2.getBigDecimal("SUM_CHANGEAMT").multiply( v_Multipler)).subtract(rs.getBigDecimal("SUM_CHANGEAMT"));
						}
 					}
						catch (Exception e)
						{
							log.info("getDeliveries"+ e);
						}
						  finally
						  {
							  DB.close(rs, pstmt2);
							  rs2 = null; pstmt2 = null;
						  }

//							 Create JV for the Reval Cost Amounts
							X_A_Depreciation_Exp depexp0 = new X_A_Depreciation_Exp (getCtx(), 0, null);
							depexp0.setPostingType(AssetReval.getPostingType());
							depexp0.setA_Asset_ID(Asset.getA_Asset_ID());
							depexp0.setExpense(v_Cost_reval);
							depexp0.setDateAcct(AssetReval.getDateAcct());
							depexp0.setA_Account_Number(rs.getInt("A_ASSET_ACCT"));
							depexp0.setDescription("Asset Revaluation");
							depexp0.setIsDepreciated(false);
							depexp0.setA_Period(AssetReval.getC_Period_ID());
							depexp0.setA_Entry_Type("RVL");
							depexp0.save();

							X_A_Depreciation_Exp depexp1 = new X_A_Depreciation_Exp (getCtx(), 0, null);
							depexp1.setPostingType(AssetReval.getPostingType());
							depexp1.setA_Asset_ID(Asset.getA_Asset_ID());
							depexp1.setExpense(v_Cost_reval.multiply(new BigDecimal(-1)));
							depexp1.setDateAcct(AssetReval.getDateAcct());
							depexp1.setA_Account_Number(rs.getInt("A_REVAL_COST_OFFSET"));
							depexp1.setDescription("Asset Revaluation");
							depexp1.setIsDepreciated(false);
							depexp1.setA_Period(AssetReval.getC_Period_ID());
							depexp1.setA_Entry_Type("RVL");
							depexp1.save();

//							 Create JV for the Reval Accum Depr Amounts
							X_A_Depreciation_Exp depexp2 = new X_A_Depreciation_Exp (getCtx(), 0, null);
							depexp2.setPostingType(AssetReval.getPostingType());
							depexp2.setA_Asset_ID(Asset.getA_Asset_ID());
							depexp2.setExpense(v_Accum_reval);
							depexp2.setDateAcct(AssetReval.getDateAcct());
							depexp2.setA_Account_Number(rs.getInt("A_ACCUMDEPRECIATION_ACCT"));
							depexp2.setDescription("Asset Revaluation");
							depexp2.setIsDepreciated(false);
							depexp2.setA_Period(AssetReval.getC_Period_ID());
							depexp2.setA_Entry_Type("RVL");
							depexp2.save();

							X_A_Depreciation_Exp depexp3 = new X_A_Depreciation_Exp (getCtx(), 0, null);
							depexp3.setPostingType(AssetReval.getPostingType());
							depexp3.setA_Asset_ID(Asset.getA_Asset_ID());
							depexp3.setExpense(v_Accum_reval.multiply(new BigDecimal(-1)));
							depexp3.setDateAcct(AssetReval.getDateAcct());
							depexp3.setA_Account_Number(rs.getInt("A_ACCUMULATED_DEPR_REVAL"));
							depexp3.setDescription("Asset Revaluation");
							depexp3.setIsDepreciated(false);
							depexp3.setA_Period(AssetReval.getC_Period_ID());
							depexp3.setA_Entry_Type("RVL");
							depexp3.save();

//							 Create JV for the Reval Depreciation Expense Amounts
							X_A_Depreciation_Exp depexp4 = new X_A_Depreciation_Exp (getCtx(), 0, null);
							depexp4.setPostingType(AssetReval.getPostingType());
							depexp4.setA_Asset_ID(Asset.getA_Asset_ID());
							depexp4.setExpense(v_Dep_Exp_reval);
							depexp4.setDateAcct(AssetReval.getDateAcct());
							depexp4.setA_Account_Number(rs.getInt("A_DEPRECIATION_ACCT"));
							depexp4.setDescription("Asset Revaluation");
							depexp4.setIsDepreciated(false);
							depexp4.setA_Period(AssetReval.getC_Period_ID());
							depexp4.setA_Entry_Type("RVL");
							depexp4.save();

							X_A_Depreciation_Exp depexp5 = new X_A_Depreciation_Exp (getCtx(), 0, null);
							depexp5.setPostingType(AssetReval.getPostingType());
							depexp5.setA_Asset_ID(Asset.getA_Asset_ID());
							depexp5.setExpense(v_Dep_Exp_reval.multiply(new BigDecimal(-1)));
							depexp5.setDateAcct(AssetReval.getDateAcct());
							depexp5.setA_Account_Number(rs.getInt("A_REVAL_DEPEXP_OFFSET"));
							depexp5.setDescription("Asset Revaluation");
							depexp5.setIsDepreciated(false);
							depexp5.setA_Period(AssetReval.getC_Period_ID());
							depexp5.setA_Entry_Type("RVL");
							depexp5.save();

					}
						catch (Exception e)
						{
							log.info("getDeliveries"+ e);
						}
						  finally
						  {
							  DB.close(rs, pstmt);
							  rs = null; pstmt = null;
						  }				 }
				 else if (AssetReval.getA_Reval_Multiplier().equals(X_A_Asset_Reval_Entry.A_REVAL_MULTIPLIER_Factor))
				 {


				 	if (AssetReval.getA_Reval_Effective_Date().equals(X_A_Asset_Reval_Entry.A_REVAL_EFFECTIVE_DATE_DateAquired)){
				 		sql2 = "Select A_REVAL_MULTIPLIER from A_ASSET_REVAL_INDEX where A_REVAL_CODE = " + AssetReval.getA_Rev_Code()
						   + " and A_EFFECTIVE_DATE = (select MAX(A_EFFECTIVE_DATE) from A_ASSET_REVAL_INDEX where  A_EFFECTIVE_DATE < "
						   + Asset.getA_Asset_CreateDate()
						   + ")";

				 	}
				 	else if (AssetReval.getA_Reval_Effective_Date().equals(X_A_Asset_Reval_Entry.A_REVAL_EFFECTIVE_DATE_RevaluationDate)){
				 		sql2 = "Select A_REVAL_MULTIPLIER from A_ASSET_REVAL_INDEX where A_REVAL_CODE = " + AssetReval.getA_Rev_Code()
						   + " and A_EFFECTIVE_DATE = (select MAX(A_EFFECTIVE_DATE) from A_ASSET_REVAL_INDEX where  A_EFFECTIVE_DATE < "
						   + Asset.getA_Asset_RevalDate()
						   + ")";

				 	}
				 	else if (AssetReval.getA_Reval_Effective_Date().equals(X_A_Asset_Reval_Entry.A_REVAL_EFFECTIVE_DATE_DateDepreciationStarted)){
				 		sql2 = "Select A_REVAL_MULTIPLIER from A_ASSET_REVAL_INDEX where A_REVAL_CODE = " + AssetReval.getA_Rev_Code()
						   + " and A_EFFECTIVE_DATE = (select MAX(A_EFFECTIVE_DATE) from A_ASSET_REVAL_INDEX where  A_EFFECTIVE_DATE < "
						   + Asset.getAssetServiceDate()
						   + ")";

				 	}
				 	PreparedStatement pstmt2 = null;
					pstmt2 = DB.prepareStatement (sql2,null);
					log.info("doIt - SQL2=" + sql2);
					try {
						ResultSet rs2 = pstmt2.executeQuery();
						while (rs2.next()){
						v_Multipler = rs2.getBigDecimal("A_REVAL_MULTIPLIER");
					}
						v_Cost_reval = ((rs.getBigDecimal("A_Asset_Cost").subtract(rs.getBigDecimal("A_ASSET_COST_REVAL"))).multiply( v_Multipler)).subtract(rs.getBigDecimal("A_Asset_Cost"));
						v_Accum_reval = ((rs.getBigDecimal("A_ACCUMULATED_DEPR").subtract(rs.getBigDecimal("A_ACCUMULATED_DEPR_REVAL"))).multiply( v_Multipler)).subtract(rs.getBigDecimal("A_ACCUMULATED_DEPR"));

					}
						catch (Exception e)
						{
							log.info("getDeliveries"+ e);
						}
						  finally
						  {
							  DB.close(rs, pstmt);
							  rs = null; pstmt = null;
						  }				 }

				v_Cost_reval = ((rs.getBigDecimal("A_Asset_Cost").subtract(rs.getBigDecimal("A_ASSET_COST_REVAL"))).multiply( v_Multipler)).subtract(rs.getBigDecimal("A_Asset_Cost"));
				v_Accum_reval = ((rs.getBigDecimal("A_ACCUMULATED_DEPR").subtract(rs.getBigDecimal("A_ACCUMULATED_DEPR_REVAL"))).multiply( v_Multipler)).subtract(rs.getBigDecimal("A_ACCUMULATED_DEPR"));

				// Create JV for the Reval Cost Amounts
				X_A_Depreciation_Exp depexp0 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				depexp0.setPostingType(AssetReval.getPostingType());
				depexp0.setA_Asset_ID(Asset.getA_Asset_ID());
				depexp0.setExpense(v_Cost_reval);
				depexp0.setDateAcct(AssetReval.getDateAcct());
				depexp0.setA_Account_Number(rs.getInt("A_ASSET_ACCT"));
				depexp0.setDescription("Asset Revaluation");
				depexp0.setIsDepreciated(false);
				depexp0.setA_Period(AssetReval.getC_Period_ID());
				depexp0.setA_Entry_Type("RVL");
				depexp0.save();

				X_A_Depreciation_Exp depexp1 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				depexp1.setPostingType(AssetReval.getPostingType());
				depexp1.setA_Asset_ID(Asset.getA_Asset_ID());
				depexp1.setExpense(v_Cost_reval.multiply(new BigDecimal(-1)));
				depexp1.setDateAcct(AssetReval.getDateAcct());
				depexp1.setA_Account_Number(rs.getInt("A_REVAL_COST_OFFSET"));
				depexp1.setDescription("Asset Revaluation");
				depexp1.setIsDepreciated(false);
				depexp1.setA_Period(AssetReval.getC_Period_ID());
				depexp1.setA_Entry_Type("RVL");
				depexp1.save();

//				 Create JV for the Reval Accum Depr Amounts
				X_A_Depreciation_Exp depexp2 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				depexp2.setPostingType(AssetReval.getPostingType());
				depexp2.setA_Asset_ID(Asset.getA_Asset_ID());
				depexp2.setExpense(v_Accum_reval);
				depexp2.setDateAcct(AssetReval.getDateAcct());
				depexp2.setA_Account_Number(rs.getInt("A_ACCUMDEPRECIATION_ACCT"));
				depexp2.setDescription("Asset Revaluation");
				depexp2.setIsDepreciated(false);
				depexp2.setA_Period(AssetReval.getC_Period_ID());
				depexp2.setA_Entry_Type("RVL");
				depexp2.save();

				X_A_Depreciation_Exp depexp3 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				depexp3.setPostingType(AssetReval.getPostingType());
				depexp3.setA_Asset_ID(Asset.getA_Asset_ID());
				depexp3.setExpense(v_Accum_reval.multiply(new BigDecimal(-1)));
				depexp3.setDateAcct(AssetReval.getDateAcct());
				depexp3.setA_Account_Number(rs.getInt("A_ACCUMULATED_DEPR_REVAL"));
				depexp3.setDescription("Asset Revaluation");
				depexp3.setIsDepreciated(false);
				depexp3.setA_Period(AssetReval.getC_Period_ID());
				depexp3.setA_Entry_Type("RVL");
				depexp3.save();

//				 Create JV for the Reval Depreciation Expense Amounts
				X_A_Depreciation_Exp depexp4 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				depexp4.setPostingType(AssetReval.getPostingType());
				depexp4.setA_Asset_ID(Asset.getA_Asset_ID());
				depexp4.setExpense(v_Accum_reval);
				depexp4.setDateAcct(AssetReval.getDateAcct());
				depexp4.setA_Account_Number(rs.getInt("A_DEPRECIATION_ACCT"));
				depexp4.setDescription("Asset Revaluation");
				depexp4.setIsDepreciated(false);
				depexp4.setA_Period(AssetReval.getC_Period_ID());
				depexp4.setA_Entry_Type("RVL");
				depexp4.save();

				X_A_Depreciation_Exp depexp5 = new X_A_Depreciation_Exp (getCtx(), 0, null);
				depexp5.setPostingType(AssetReval.getPostingType());
				depexp5.setA_Asset_ID(Asset.getA_Asset_ID());
				depexp5.setExpense(v_Accum_reval.multiply(new BigDecimal(-1)));
				depexp5.setDateAcct(AssetReval.getDateAcct());
				depexp5.setA_Account_Number(rs.getInt("A_REVAL_DEPEXP_OFFSET"));
				depexp5.setDescription("Asset Revaluation");
				depexp5.setIsDepreciated(false);
				depexp5.setA_Period(AssetReval.getC_Period_ID());
				depexp5.setA_Entry_Type("RVL");
				depexp5.save();
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
				return "";
		}	//	doIt

}	//	AssetRevalEntry
