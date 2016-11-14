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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.compiere.model.X_A_Depreciation;
import org.compiere.model.X_A_Depreciation_Convention;
import org.compiere.model.X_A_Depreciation_Exp;
import org.compiere.model.X_A_Depreciation_Forecast;
import org.compiere.model.X_A_Depreciation_Method;
import org.compiere.model.X_A_Depreciation_Workfile;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 *	Build Depreciation Forecast File
 *	
 *  @author Rob Klein
 *  @version $Id: BuildDepForecastFile.java,v 1.0 $
 */

public class BuildDepForecastFile extends SvrProcess
{
	/** Record ID				*/
	private int p_Depreciation_Build_ID = 0;
	private boolean	m_DeleteOld = false;
	
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
		p_Depreciation_Build_ID = getRecord_ID();
	}	//	prepare

	
	/**
	 * 	Build Depreciation Workfile
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		log.info("doIt - Depreciation_Build_ID=" + p_Depreciation_Build_ID);
		if (p_Depreciation_Build_ID == 0)
			throw new IllegalArgumentException("No Record");
		String clientCheck = " AND AD_Client_ID=" + getAD_Client_ID();
		//
		int no = 0;
		
		if (m_DeleteOld)
		{
			String sql =  "DELETE FROM A_DEPRECIATION_EXP "
				  + "WHERE A_Entry_Type = 'FOR'" + clientCheck;
			
			no = DB.executeUpdate (sql,null);
			log.info("doIt - Delete old processed entries =" + no);
		}
		
		
		X_A_Depreciation_Forecast DepBuild = new X_A_Depreciation_Forecast (getCtx(), p_Depreciation_Build_ID, null);
		String sql = null;
		sql = "DELETE FROM A_DEPRECIATION_EXP WHERE PostingType"
			+ " = '" + DepBuild.getPostingType() + "' and A_Asset_ID"
			+ " >= " + DepBuild.getA_Start_Asset_ID() + " and A_Asset_ID "
			+ " <= " + DepBuild.getA_End_Asset_ID()
			+ " and A_Entry_Type = 'FOR'"
			+ clientCheck;
		
		no = DB.executeUpdate(sql,null);
		log.info("doIt - Clear DepExpense = " + no);
		
		sql = null;
		sql = "UPDATE A_DEPRECIATION_WORKFILE SET A_CURR_DEP_EXP = 0, A_CURRENT_PERIOD = 0 WHERE POSTINGTYPE"
			+ " = '" + DepBuild.getPostingType() + "' and A_Asset_ID"
			+ " >= " + DepBuild.getA_Start_Asset_ID() + " and A_Asset_ID "
			+ " <= " + DepBuild.getA_End_Asset_ID()
			+ clientCheck;
		
		no = DB.executeUpdate(sql,null);
		log.info("doIt - DepExpense Reset= " + no);
		
		sql = null;
		sql =" SELECT A_ASSET.A_ASSET_ID, A_ASSET.USELIFEYEARS, A_ASSET.USELIFEMONTHS, A_ASSET.LIFEUSEUNITS, "
		    +" A_ASSET.USEUNITS, A_ASSET.ISOWNED, A_ASSET.ISDISPOSED,A_DEPRECIATION_WORKFILE.A_PERIOD_POSTED, "
		    +" A_DEPRECIATION_WORKFILE.A_CURR_DEP_EXP, A_ASSET.ASSETDEPRECIATIONDATE, A_ASSET.ISFULLYDEPRECIATED, "
		    +" A_ASSET.ASSETSERVICEDATE, A_DEPRECIATION_WORKFILE.A_ASSET_ID as v_Asset_ID, A_DEPRECIATION_WORKFILE.POSTINGTYPE, " 
		    +" A_DEPRECIATION_FORECAST.A_START_ASSET_ID, A_DEPRECIATION_FORECAST.A_END_ASSET_ID, A_DEPRECIATION_WORKFILE.A_ACCUMULATED_DEPR," 
		    +" A_DEPRECIATION_FORECAST.AD_CLIENT_ID, A_DEPRECIATION_FORECAST.AD_ORG_ID, A_DEPRECIATION_WORKFILE.A_SALVAGE_VALUE,"
		    +" A_DEPRECIATION_FORECAST.CREATEDBY, A_DEPRECIATION_FORECAST.UPDATEDBY, A_DEPRECIATION_FORECAST.POSTINGTYPE as v_PostingType," 
		    +" A_DEPRECIATION_WORKFILE.A_DEPRECIATION_WORKFILE_ID, A_DEPRECIATION_FORECAST.DATEDOC, A_DEPRECIATION_WORKFILE.A_ASSET_COST"
		    +" FROM A_DEPRECIATION_WORKFILE, A_ASSET, A_DEPRECIATION_FORECAST "
		    +" WHERE A_ASSET.A_ASSET_ID = A_DEPRECIATION_WORKFILE.A_ASSET_ID AND A_ASSET.ISOWNED = 'Y' AND A_DEPRECIATION_FORECAST.A_START_ASSET_ID  <= A_ASSET.A_ASSET_ID" 
		    +" AND A_DEPRECIATION_FORECAST.A_END_ASSET_ID >= A_ASSET.A_ASSET_ID AND A_ASSET.ISFULLYDEPRECIATED = 'N' AND A_ASSET.ISDEPRECIATED = 'Y'" 
		    +" AND A_DEPRECIATION_WORKFILE.POSTINGTYPE = ? ";
		
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement (sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE,null);
		try {
			
			pstmt.setString(1, DepBuild.getPostingType());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()){
				X_A_Depreciation_Workfile assetwk = new X_A_Depreciation_Workfile (getCtx(), rs.getInt("A_DEPRECIATION_WORKFILE_ID"), null);
				String sql2 = null;
				sql2 =" SELECT * FROM A_ASSET_ACCT WHERE PostingType"
					+ " = '" + DepBuild.getPostingType() + "' and A_Asset_ID"
					+ " = " + rs.getInt("A_ASSET_ID");
				
				PreparedStatement pstmt2 = null;
				pstmt2 = DB.prepareStatement (sql2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE,null);
				try {			
		
					ResultSet rs2 = pstmt2.executeQuery();					
					//CallableStatement cs;
					BigDecimal v_Dep_Exp_Inception = new BigDecimal("0.0");
					BigDecimal v_Dep_Exp_Inception2 = new BigDecimal("0.0");
					BigDecimal v_HalfYearConv = new BigDecimal("0.0");
					BigDecimal v_HalfYearConv_Adj = new BigDecimal("0.0");
					BigDecimal v_Dep_Exp_Adjustment = new BigDecimal("0.0");	
					BigDecimal v_Dep_Exp_Monthly = new BigDecimal("0.0");
					BigDecimal v_total_adjustment = new BigDecimal("0.0");
					int asset_id_current = 0;
					double v_current=0;
					BigDecimal v_current_adj = new BigDecimal(0.0);

					while (rs2.next()){
						//X_A_Asset_Acct assetacct = new X_A_Asset_Acct (getCtx(), rs2.getInt("A_ASSET_ACCT_ID"), null);
						X_A_Depreciation depreciation = new X_A_Depreciation (getCtx(), rs2.getInt("A_DEPRECIATION_ID"), null);
						X_A_Depreciation_Convention depreciation_conv = new X_A_Depreciation_Convention (getCtx(), rs2.getInt("A_DEPRECIATION_CONV_ID"), null);						
						X_A_Depreciation_Exp depexp = new X_A_Depreciation_Exp (getCtx(), 0, null);
						//Date d1,d2;
						//d1 = rs.getDate("ASSETSERVICEDATE");
						//d2 = rs.getDate("DATEDOC");
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(rs.getDate("ASSETSERVICEDATE"));
						int AssetServiceDateYear = calendar.get(Calendar.YEAR);
						int AssetServiceDateMonth = calendar.get(Calendar.MONTH);					
						calendar.setTime(rs.getDate("DATEDOC"));
						int DateAcctYear = calendar.get(Calendar.YEAR);
						int DateAcctMonth = calendar.get(Calendar.MONTH);
						
						double v_period =  (Math.ceil(DateAcctMonth)+ (Math.floor(DateAcctYear) - Math.floor(AssetServiceDateYear))*12 - Math.floor(AssetServiceDateMonth)) ;
						//Record booked depreciation expense
						if (rs2.getInt("A_ASSET_ID")!=asset_id_current )
						{
							v_current_adj = new BigDecimal(0.0);							
							depexp.setPostingType(DepBuild.getPostingType());
							depexp.setA_Asset_ID(rs.getInt("A_ASSET_ID"));					
							depexp.setA_Account_Number(rs2.getInt("A_Depreciation_Acct"));
							depexp.setPostingType(rs.getString("PostingType"));
							depexp.setExpense(assetwk.getA_Accumulated_Depr().setScale(5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(rs2.getFloat("A_Split_Percent"))));
							depexp.setDescription("Actual Depreciation Expense Booked");
							depexp.setA_Period(rs.getInt("A_Period_Posted"));
							depexp.setIsDepreciated(true);
							depexp.setDateAcct(assetwk.getAssetDepreciationDate());
							depexp.setA_Entry_Type("FOR");
							depexp.save();
						}
						else
						{
							v_current_adj = v_current_adj.subtract(new BigDecimal(0.0));
						}
						int method = 0;
						method = rs2.getInt("A_DEPRECIATION_METHOD_ID");
						
						//Set depreciation date and record in workfile
						Calendar cal = GregorianCalendar.getInstance();						
						Timestamp ts;					
						ts =(rs.getTimestamp("ASSETSERVICEDATE"));
						cal.setTime(ts);
						assetwk.setDateAcct(ts);
						assetwk.setA_Period_Forecast(new BigDecimal(assetwk.getA_Period_Posted()));
						assetwk.save();				
						
					
					//Calculate life to date depreciation
					while (v_current < v_period ){
						v_Dep_Exp_Inception2 = Depreciation.Dep_Type(depreciation.getDepreciationType(),rs2.getInt("A_Asset_ID"),v_current, rs2.getString("PostingType"),  rs2.getInt("A_ASSET_ACCT_ID"),  v_Dep_Exp_Inception);
						//cs = DB.prepareCall("{ ? = call "+ depreciation.getDepreciationType() + "(" + rs2.getInt("A_Asset_ID") + "," + (v_current) + ",'" +  rs2.getString("PostingType") + "'," + rs2.getInt("A_ASSET_ACCT_ID") + "," + v_Dep_Exp_Inception + ")}");						
						//cs.registerOutParameter(1, java.sql.Types.DECIMAL);						
						//cs.execute();						
						//v_Dep_Exp_Inception2 = cs.getBigDecimal(1);												
						//cs.close();
						v_HalfYearConv_Adj = new BigDecimal(Conventions.Dep_Convention(depreciation_conv.getConventionType(),rs2.getInt("A_Asset_ID"), rs2.getString("PostingType"), rs2.getInt("A_ASSET_ACCT_ID"), 1, (v_current -1 )));
						//cs = DB.prepareCall("{ ? = call " +  depreciation_conv.getConventionType() + "(" + rs2.getInt("A_Asset_ID") + ",'" + rs2.getString("PostingType") + "'," + rs2.getInt("A_ASSET_ACCT_ID") + ", 1 ," + (v_current -1 )+ ")}");
						//cs.registerOutParameter(1, java.sql.Types.DECIMAL);
						//cs.execute();
						//v_HalfYearConv_Adj = cs.getBigDecimal(1);
						//cs.close();												
					    v_HalfYearConv = v_HalfYearConv.add(v_HalfYearConv_Adj);
					    cal.add(Calendar.MONTH, 1);
						cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
						ts.setTime(cal.getTimeInMillis());						
						assetwk.setDateAcct(ts);
						v_current_adj = v_current_adj.add((v_HalfYearConv_Adj));
						assetwk.setA_Period_Forecast(v_current_adj);
						assetwk.save();
					    v_Dep_Exp_Inception = v_Dep_Exp_Inception .add(v_Dep_Exp_Inception2.multiply(v_HalfYearConv_Adj));    
					    v_current = v_current + 1;
					}
						
					//log.info("doIt - Booked Expense= "+assetwk.getA_Accumulated_Depr());
					//log.info("doIt - Calculated Expense= "+v_Dep_Exp_Inception);
					
					
					//Calculate necessary adjustment per period
					X_A_Depreciation_Method depreciation_method = new X_A_Depreciation_Method (getCtx(), method, null);
					if (v_Dep_Exp_Inception.compareTo( assetwk.getA_Accumulated_Depr())!=0)
					{ 
						v_Dep_Exp_Adjustment = DepreciationAdj.Dep_Adj(depreciation_method.getDepreciationType(),rs2.getInt("A_Asset_ID") , (v_Dep_Exp_Inception.subtract(assetwk.getA_Accumulated_Depr()))  , (Math.floor(DateAcctMonth)), rs2.getString("PostingType") , rs2.getInt("A_ASSET_ACCT_ID"));
						//cs = DB.prepareCall("{ ? = call " +  depreciation_method.getDepreciationType() + "(" + rs2.getInt("A_Asset_ID") + "," + (v_Dep_Exp_Inception.subtract(assetwk.getA_Accumulated_Depr()))  + "," + (Math.floor(d2.getMonth()))+ ",'" + rs2.getString("PostingType") + "'," +rs2.getInt("A_ASSET_ACCT_ID") +")}");
						//cs.registerOutParameter(1, java.sql.Types.DECIMAL);
						//cs.execute();
						//v_Dep_Exp_Adjustment = cs.getBigDecimal(1);
						//cs.close();						
						v_total_adjustment = v_Dep_Exp_Inception.subtract(assetwk.getA_Accumulated_Depr());
					}					
					
					v_current = v_current+1;
					//v_Dep_Exp_Inception = (rs.getBigDecimal("A_ACCUMULATED_DEPR"));
					BigDecimal v_period_adj = new BigDecimal(rs2.getInt("A_PERIOD_END"));
					int lastdepexp2=0;
					
					while (v_current_adj.compareTo(v_period_adj)<0){					
					//Calculation depreciation expense
					v_Dep_Exp_Monthly = Depreciation.Dep_Type(depreciation.getDepreciationType(),rs2.getInt("A_Asset_ID"),v_current-1, rs2.getString("PostingType"),  rs2.getInt("A_ASSET_ACCT_ID"),  v_Dep_Exp_Inception);					
					//cs = DB.prepareCall("{ ? = call " +  depreciation.getDepreciationType() + "(" + rs2.getInt("A_Asset_ID") +", "+ (v_current-1) +" ,'" + rs2.getString("PostingType") + "'," + rs2.getInt("A_ASSET_ACCT_ID") + " , " + (v_Dep_Exp_Inception) + ")}");					
					//cs.registerOutParameter(1, java.sql.Types.DECIMAL);
					//cs.execute();					
					//v_Dep_Exp_Monthly = cs.getBigDecimal(1);
					//cs.close();
					//Adjust for half year convention
					//log.info(depreciation_conv.getConventionType());
					//log.info(""+rs2.getInt("A_Asset_ID"));
					v_HalfYearConv_Adj = new BigDecimal(Conventions.Dep_Convention(depreciation_conv.getConventionType(),rs2.getInt("A_Asset_ID"), rs2.getString("PostingType"), rs2.getInt("A_ASSET_ACCT_ID"), 0, 1 ));
					//log.info(""+v_HalfYearConv_Adj);
					//cs = DB.prepareCall("{ ? = call " +  depreciation_conv.getConventionType() + "(" + rs2.getInt("A_Asset_ID") +",'" + rs2.getString("PostingType") + "'," + rs2.getInt("A_ASSET_ACCT_ID") + " , 0, 1)}");
					//cs.registerOutParameter(1, java.sql.Types.DECIMAL);
					//cs.execute();					
					//v_HalfYearConv_Adj = cs.getBigDecimal(1);
					//cs.close();				
					v_Dep_Exp_Monthly = v_Dep_Exp_Monthly.multiply((v_HalfYearConv_Adj));
					v_HalfYearConv = v_HalfYearConv.add( v_HalfYearConv_Adj);
					
					X_A_Depreciation_Exp depexp2 = new X_A_Depreciation_Exp (getCtx(), 0, null);
					if (v_total_adjustment.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal (0.00))!=0)
					{
						
						//Record necessary adjustments
						
						X_A_Depreciation_Exp depexp1 = new X_A_Depreciation_Exp (getCtx(), 0, null);	
						depexp1.setA_Entry_Type("FOR");
						depexp1.setA_Asset_ID(rs.getInt("A_ASSET_ID"));					
						depexp1.setA_Account_Number(rs2.getInt("A_Depreciation_Acct"));
						depexp1.setPostingType(rs.getString("PostingType"));
						depexp1.setExpense(v_Dep_Exp_Adjustment.setScale(5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(rs2.getFloat("A_Split_Percent"))));
						depexp1.setDescription("Forecasted Depreciation Expense Adj.");					
						depexp1.setA_Period((int)v_current);
						depexp1.setIsDepreciated(true);
						depexp1.setDateAcct(ts);
						depexp1.save();
						v_total_adjustment = v_total_adjustment.setScale(5, BigDecimal.ROUND_HALF_UP).subtract(v_Dep_Exp_Adjustment.setScale(5, BigDecimal.ROUND_HALF_UP));
						
						//Record adjusted expense							
						depexp2.setPostingType(DepBuild.getPostingType());
						depexp2.setA_Asset_ID(rs.getInt("A_ASSET_ID"));						
						depexp2.setA_Account_Number(rs2.getInt("A_Depreciation_Acct"));
						depexp2.setPostingType(rs.getString("PostingType"));
						depexp2.setExpense((v_Dep_Exp_Monthly.setScale(2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(rs2.getFloat("A_Split_Percent"))));
						depexp2.setDescription("Forecasted Depreciation Expense");					
						depexp2.setA_Period((int)v_current);
						depexp2.setIsDepreciated(true);
						depexp2.setDateAcct(ts);
						depexp2.setA_Entry_Type("FOR");
						depexp2.save();						
						v_Dep_Exp_Inception = v_Dep_Exp_Inception.add((v_Dep_Exp_Monthly.setScale(2, BigDecimal.ROUND_HALF_UP))).setScale(2, BigDecimal.ROUND_HALF_UP);
					}
					else
					{
						//Record expense							
						depexp2.setPostingType(DepBuild.getPostingType());
						depexp2.setA_Asset_ID(rs.getInt("A_ASSET_ID"));
						depexp2.setExpense(v_Dep_Exp_Adjustment.setScale(2, BigDecimal.ROUND_HALF_UP));
						depexp2.setA_Account_Number(rs2.getInt("A_Depreciation_Acct"));
						depexp2.setPostingType(rs.getString("PostingType"));
						depexp2.setExpense(v_Dep_Exp_Monthly.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(rs2.getFloat("A_Split_Percent"))));
						depexp2.setDescription("Forecasted Depreciation Expense");					
						depexp2.setA_Period((int)v_current);
						depexp2.setIsDepreciated(true);
						depexp2.setDateAcct(ts);
						depexp2.setA_Entry_Type("FOR");
						depexp2.save();
						v_Dep_Exp_Inception = v_Dep_Exp_Inception.add(v_Dep_Exp_Monthly).setScale(2, BigDecimal.ROUND_HALF_UP);
					}					
					lastdepexp2 = depexp2.get_ID();
					//Advance calender
					cal.add(Calendar.MONTH, 1);
					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
					ts.setTime(cal.getTimeInMillis());
					v_current_adj = v_current_adj.add((v_HalfYearConv_Adj)).setScale(2, BigDecimal.ROUND_HALF_UP);
					
					//record in workfile
					assetwk.setA_Period_Forecast(v_current_adj);
					assetwk.setDateAcct(ts);
					assetwk.save();
					v_current = v_current + 1;					
					}
					//adjust last entry for rounding errors
					X_A_Depreciation_Exp depexp2 = new X_A_Depreciation_Exp (getCtx(), lastdepexp2, null);					
					depexp2.setExpense(depexp2.getExpense().add(((rs.getBigDecimal("A_ASSET_COST").subtract(rs.getBigDecimal("A_SALVAGE_VALUE").subtract(v_total_adjustment))).subtract(v_Dep_Exp_Inception)).multiply(new BigDecimal(rs2.getFloat("A_Split_Percent")))));					
					depexp2.save();
					asset_id_current = rs2.getInt("A_ASSET_ID");
					log.info("Asset #"+asset_id_current);					
				}
				rs2.close();
				pstmt2.close();
				pstmt2 = null;
				}
				catch (Exception e)
				{
					log.info("getAssets"+ e);
				}
				finally
				{
					try
					{
						if (pstmt2 != null)
							pstmt2.close ();
					}
					catch (Exception e)
					{}
					pstmt2 = null;
				}				
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
			
			return "";
	}	//	doIt
	
}	//	BuildDepForecastFile
