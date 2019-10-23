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
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com
 * _____________________________________________
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
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.compiere.util.DB;
/**
 * Fixed Asset Depreciation
 * 
 * @author Rob Klein
 * @version 	$Id: Conventions.java,v 1.0 $
 * 
 */
public class Depreciation {
 
	/**
	*
	*
	*/
	static public BigDecimal Dep_Type(String Type,int p_A_ASSET_ID, double p_A_CURRENT_PERIOD, String p_POSTINGTYPE,
			int p_A_ASSET_ACCT_ID, BigDecimal p_Accum_Dep) 
	{		
		BigDecimal A_Period_Exp = new BigDecimal(0.0);
		if(Type.compareTo("DB150")==0){			
			A_Period_Exp = DB150( 0, p_A_ASSET_ID,  p_A_CURRENT_PERIOD,  p_POSTINGTYPE,  p_A_ASSET_ACCT_ID, p_Accum_Dep);
			//System.out.println("DB150 Main: "+A_Period_Exp);
			return A_Period_Exp;
		}
		else if(Type.compareTo("DB1SL")==0){		
			A_Period_Exp = DB150( 1, p_A_ASSET_ID,  p_A_CURRENT_PERIOD,  p_POSTINGTYPE,  p_A_ASSET_ACCT_ID, p_Accum_Dep);
			//System.out.println("DB1SL Main: "+A_Period_Exp);
			return A_Period_Exp;
		}
		else if(Type.compareTo("DB200")==0){					
			A_Period_Exp = DB200( 0, p_A_ASSET_ID,  p_A_CURRENT_PERIOD,  p_POSTINGTYPE,  p_A_ASSET_ACCT_ID, p_Accum_Dep);
			//System.out.println("DB200 Main: "+A_Period_Exp);
			return A_Period_Exp;
		}
		else if(Type.compareTo("DB2SL")==0){		
			A_Period_Exp =  DB200( 1, p_A_ASSET_ID,  p_A_CURRENT_PERIOD,  p_POSTINGTYPE,  p_A_ASSET_ACCT_ID, p_Accum_Dep);
			//System.out.println("DB2SL Main: "+A_Period_Exp);
		    return A_Period_Exp;
		}
		else if(Type.compareTo("VAR")==0){					
			A_Period_Exp = DBVAR( 0, p_A_ASSET_ID,  p_A_CURRENT_PERIOD,  p_POSTINGTYPE,  p_A_ASSET_ACCT_ID, p_Accum_Dep);
			//System.out.println("VAR Main: "+A_Period_Exp);
			return A_Period_Exp;
		}
		else if(Type.compareTo("VARSL")==0){					
			A_Period_Exp = DBVAR( 1, p_A_ASSET_ID,  p_A_CURRENT_PERIOD,  p_POSTINGTYPE,  p_A_ASSET_ACCT_ID, p_Accum_Dep);
			//System.out.println("VARSL Main: "+A_Period_Exp);
			return A_Period_Exp;
		}
		else if(Type.compareTo("MAN")==0){					
			A_Period_Exp = MAN( 1, p_A_ASSET_ID,  p_A_CURRENT_PERIOD,  p_POSTINGTYPE,  p_A_ASSET_ACCT_ID, p_Accum_Dep);
			//System.out.println("MAN Main: "+A_Period_Exp);
			return A_Period_Exp;
		}
		else if(Type.compareTo("SL")==0){					
			A_Period_Exp =  SL( 1, p_A_ASSET_ID,  p_A_CURRENT_PERIOD,  p_POSTINGTYPE,  p_A_ASSET_ACCT_ID, p_Accum_Dep);
			//System.out.println("SL Main: "+A_Period_Exp);
			return A_Period_Exp;
		}
		else if(Type.compareTo("SYD")==0){					
			A_Period_Exp =  SYD( 1, p_A_ASSET_ID,  p_A_CURRENT_PERIOD,  p_POSTINGTYPE,  p_A_ASSET_ACCT_ID, p_Accum_Dep);
			//System.out.println("SYD Main: "+A_Period_Exp);
			return A_Period_Exp;
		}
		else if(Type.compareTo("TAB")==0){					
			A_Period_Exp =  TAB( 1, p_A_ASSET_ID,  p_A_CURRENT_PERIOD,  p_POSTINGTYPE,  p_A_ASSET_ACCT_ID, p_Accum_Dep);
			//System.out.println("TAB Main: "+A_Period_Exp);
			return A_Period_Exp;
		}
		else
			return new BigDecimal(0.0);
			
	 }	
	
	
	static public BigDecimal DB150(int p_SL, int p_A_ASSET_ID, double p_A_CURRENT_PERIOD, String p_POSTINGTYPE,
			int p_A_ASSET_ACCT_ID,  BigDecimal p_Accum_Dep) 
	{		
		  BigDecimal v_DB = new BigDecimal(0.0);
		  BigDecimal v_Accum_Dep = new BigDecimal(0.0);
		  BigDecimal v_SL = new BigDecimal(0.0);
		  BigDecimal A_Period_Exp = new BigDecimal(0.0);
		  int v_counter = 0;
		  double v_months = 0;
		  int v_firstyr = 0;
		  double v_monthadj = 0;
		  BigDecimal v_adj = new BigDecimal(0.0);		  
		  BigDecimal v_Base_Amount = new BigDecimal(0.0);
		  BigDecimal v_Salvage_Amount = new BigDecimal(0.0);
		  double v_Life_Periods = 0;
		  double v_Life_Years = 0;
		  String v_con_type = null;
		  
		  StringBuffer sqlB = new StringBuffer ("SELECT A.A_ASSET_COST, A.A_SALVAGE_VALUE, A.A_LIFE_PERIOD, A.A_ASSET_LIFE_YEARS, A.DATEACCT,"
			+ " D.ASSETSERVICEDATE, C.CONVENTIONTYPE "
			+ " FROM A_DEPRECIATION_WORKFILE A, A_ASSET_ACCT B,A_ASSET D,A_DEPRECIATION_CONVENTION C "
			+ " WHERE A.A_ASSET_ID = " + p_A_ASSET_ID  
			+ " AND B.A_ASSET_ID = " + p_A_ASSET_ID 
			+ " AND A_PERIOD_POSTED+1 >= A_PERIOD_START AND A_PERIOD_POSTED+1 <= A_PERIOD_END "
			+ " AND A.POSTINGTYPE = '" +  p_POSTINGTYPE
			+ "' AND B.POSTINGTYPE = '" +  p_POSTINGTYPE
			+ "' AND B.A_ASSET_ACCT_ID = " +  p_A_ASSET_ACCT_ID
			+ " AND D.A_ASSET_ID = " + p_A_ASSET_ID
			+ " AND B.A_DEPRECIATION_CONV_ID = C.A_DEPRECIATION_CONVENTION_ID");		
		  //System.out.println("DB150: "+sqlB.toString());
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  try {				
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()){
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(rs.getDate("ASSETSERVICEDATE"));
					int AssetServiceDateYear = calendar.get(Calendar.YEAR);
					int AssetServiceDateMonth = calendar.get(Calendar.MONTH);					
					calendar.setTime(rs.getDate("DATEACCT"));
					int DateAcctYear = calendar.get(Calendar.YEAR);
					
				  v_Base_Amount = rs.getBigDecimal("A_ASSET_COST");
				  v_Salvage_Amount = rs.getBigDecimal("A_SALVAGE_VALUE");
				  v_Life_Periods = rs.getDouble("A_LIFE_PERIOD");
				  v_Life_Years = rs.getDouble("A_ASSET_LIFE_YEARS");
				  v_con_type = rs.getString("CONVENTIONTYPE");
				  
				  double v_period = DateAcctYear - AssetServiceDateYear;				  
				    while (v_counter <= v_period){				      
				        if(v_firstyr == 0){
				        v_DB = (v_Base_Amount.subtract(v_Accum_Dep).subtract(v_Salvage_Amount)).multiply(new BigDecimal(1.5/v_Life_Years));
				        v_months = 12 - AssetServiceDateMonth +1;				        
				        if (v_con_type.compareTo("HYCON") ==0){
				          v_adj = new BigDecimal(Conventions.HYCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));				          
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj).multiply(new BigDecimal(v_months)));
				          v_monthadj = 6-v_months;
				        }
				        else if (v_con_type.compareTo("FYCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.FYCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,0,AssetServiceDateYear));
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));				          
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj).multiply(new BigDecimal(v_months)));				          
				          v_monthadj = 12-v_months;
				        }
				        else if (v_con_type.compareTo("DYCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.DYCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj));
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));				          
				          v_monthadj = 0;
				        }
				        else if (v_con_type.compareTo("MQCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.MQCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj));
				          v_DB =(v_DB.divide(new BigDecimal(v_months),2, BigDecimal.ROUND_HALF_UP));				          				          
				          v_monthadj = 0;
				        }
				        else if (v_con_type.compareTo("FMCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.FMCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));				          
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(new BigDecimal(v_months)));				          
				          v_monthadj = 0;
				        }
				        else if (v_con_type.compareTo("MMCON") ==0){
				        	
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(.5)).add(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(v_months-1));
				          v_adj = new BigDecimal(Conventions.MMCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateMonth+1));
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP)).multiply(v_adj);				          
				          v_monthadj = 0;
				        }
				        v_firstyr = 1;
				        }
				     else{				    	
				    	v_DB = (v_Base_Amount.subtract(v_Accum_Dep).subtract(v_Salvage_Amount)).multiply(new BigDecimal(1.5/v_Life_Years));				        
				        v_Accum_Dep = v_Accum_Dep.add(v_DB);
				        v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));				        
				     }
			        v_counter = v_counter+1;				     
				    }				 
				 A_Period_Exp = v_DB;
				 
				 if (p_SL ==1 ){   
					 if (v_Life_Periods-(p_A_CURRENT_PERIOD+v_monthadj)>0 )					 
					     v_SL = ((v_Base_Amount.subtract(v_Salvage_Amount).subtract(p_Accum_Dep)).divide(new BigDecimal(v_Life_Periods-(p_A_CURRENT_PERIOD+v_monthadj)),2, BigDecimal.ROUND_HALF_UP));				 
	
					 if (A_Period_Exp.compareTo(v_SL)==-1)				   
					   A_Period_Exp = v_SL;				 
					  
					 if (v_Base_Amount.subtract(p_Accum_Dep).subtract(A_Period_Exp).compareTo(v_Salvage_Amount)==-1 )
						  A_Period_Exp =v_Base_Amount.subtract(p_Accum_Dep).subtract(v_Salvage_Amount);
				 }
				 
				}
				//System.out.println("DB150: "+A_Period_Exp);
				return A_Period_Exp;
			}
		  catch (Exception e)
		  {
			  System.out.println("DB150: "+e);
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
		  return A_Period_Exp;		
	}
	
	static public BigDecimal DB200(int p_SL,int p_A_ASSET_ID, double p_A_CURRENT_PERIOD, String p_POSTINGTYPE,
			int p_A_ASSET_ACCT_ID,  BigDecimal p_Accum_Dep) 
	{		
		  BigDecimal v_DB = new BigDecimal(0.0);		 
		  BigDecimal v_Accum_Dep = new BigDecimal(0.0);
		  BigDecimal v_SL = new BigDecimal(0.0);
		  BigDecimal A_Period_Exp = new BigDecimal(0.0);
		  int v_counter = 0;
		  double v_months = 0;
		  int v_firstyr = 0;
		  double v_monthadj = 0;
		  BigDecimal v_adj = new BigDecimal(0.0);		  
		  BigDecimal v_Base_Amount = new BigDecimal(0.0);
		  BigDecimal v_Salvage_Amount = new BigDecimal(0.0);
		  double v_Life_Periods = 0;
		  double v_Life_Years = 0;
		  String v_con_type = null;
		 
		  StringBuffer sqlB = new StringBuffer ("SELECT A.A_ASSET_COST, A.A_SALVAGE_VALUE, A.A_LIFE_PERIOD, A.A_ASSET_LIFE_YEARS, A.DATEACCT,"
			+ " D.ASSETSERVICEDATE, C.CONVENTIONTYPE "
			+ " FROM A_DEPRECIATION_WORKFILE A, A_ASSET_ACCT B,A_ASSET D,A_DEPRECIATION_CONVENTION C "
			+ " WHERE A.A_ASSET_ID = " + p_A_ASSET_ID 
			+ " AND B.A_ASSET_ID = " + p_A_ASSET_ID 
			+ " AND A_PERIOD_POSTED+1 >= A_PERIOD_START AND A_PERIOD_POSTED+1 <= A_PERIOD_END "
			+ " AND A.POSTINGTYPE = '" +  p_POSTINGTYPE
			+ "' AND B.POSTINGTYPE = '" +  p_POSTINGTYPE
			+ "' AND B.A_ASSET_ACCT_ID = " +  p_A_ASSET_ACCT_ID
			+ " AND D.A_ASSET_ID = " + p_A_ASSET_ID
			+ " AND B.A_DEPRECIATION_CONV_ID = C.A_DEPRECIATION_CONVENTION_ID");		
		  //System.out.println("DB200: "+sqlB.toString());
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  ResultSet rs = null;
		  try {				
				rs = pstmt.executeQuery();
				while (rs.next()){
				  Calendar calendar = new GregorianCalendar();
				  calendar.setTime(rs.getDate("ASSETSERVICEDATE"));
				  int AssetServiceDateYear = calendar.get(Calendar.YEAR);
				  int AssetServiceDateMonth = calendar.get(Calendar.MONTH);					
				  calendar.setTime(rs.getDate("DATEACCT"));
				  int DateAcctYear = calendar.get(Calendar.YEAR);
				  v_counter = 0;
				  v_months = 0;
				  v_firstyr = 0;
				  v_monthadj = 0;
				  v_adj = new BigDecimal(0.0);				  
				  v_Base_Amount = rs.getBigDecimal("A_ASSET_COST");
				  v_Salvage_Amount = rs.getBigDecimal("A_SALVAGE_VALUE");
				  v_Life_Periods = rs.getDouble("A_LIFE_PERIOD");
				  v_Life_Years = rs.getDouble("A_ASSET_LIFE_YEARS");
				  v_con_type = rs.getString("CONVENTIONTYPE");
				  
				  double v_period = DateAcctYear - AssetServiceDateYear;				  
				    while (v_counter <= v_period){				      
				        if(v_firstyr == 0){
				        v_DB = (v_Base_Amount.subtract(v_Accum_Dep).subtract(v_Salvage_Amount)).multiply(new BigDecimal(2/v_Life_Years));				        
				        v_months = 12 - AssetServiceDateMonth +1;				        
				        if (v_con_type.compareTo("HYCON") ==0){
				          v_adj = new BigDecimal(Conventions.HYCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj).multiply(new BigDecimal(v_months)));
				          v_monthadj = 6-v_months;
				        }
				        else if (v_con_type.compareTo("FYCON") ==0){
				        	
				          v_adj = new BigDecimal(Conventions.FYCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,0,AssetServiceDateYear));				          
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));				          
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj).multiply(new BigDecimal(v_months)));				          
				          v_monthadj = 12-v_months;
				        }
				        else if (v_con_type.compareTo("DYCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.DYCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj));				          
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));
				          v_monthadj = 0;
				        }
				        else if (v_con_type.compareTo("MQCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.MQCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj));
				          v_DB =(v_DB.divide(new BigDecimal(v_months),2, BigDecimal.ROUND_HALF_UP));				          				          
				          v_monthadj = 0;
				        }
				        else if (v_con_type.compareTo("FMCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.FMCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(new BigDecimal(v_months)));				          
				          v_monthadj = 0;
				        }
				        else if (v_con_type.compareTo("MMCON") ==0){
				        	
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(.5)).add(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(v_months-1));
				          v_adj = new BigDecimal(Conventions.MMCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateMonth+1));
				          v_DB = (v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP)).multiply(v_adj);
				          v_monthadj = 0;
				        }
				        v_firstyr = 1;
				        }
				     else{				    	
				    	v_DB = (v_Base_Amount.subtract(v_Accum_Dep).subtract(v_Salvage_Amount)).multiply(new BigDecimal(2/v_Life_Years));				        
				        v_Accum_Dep = v_Accum_Dep.add(v_DB);
				        v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));
				     }
			        v_counter = v_counter+1;				     
				    }
				    A_Period_Exp = v_DB;					 
					 if (p_SL ==1 ){   
						 if (v_Life_Periods-(p_A_CURRENT_PERIOD+v_monthadj)>0 )
							 v_SL = ((v_Base_Amount.subtract(v_Salvage_Amount).subtract(p_Accum_Dep)).divide(new BigDecimal(v_Life_Periods-(p_A_CURRENT_PERIOD+v_monthadj)),2, BigDecimal.ROUND_HALF_UP));						     				 
		
						 if (A_Period_Exp.compareTo(v_SL)==-1)				   
						   A_Period_Exp = v_SL;				 
						  
						 if (v_Base_Amount.subtract(p_Accum_Dep).subtract(A_Period_Exp).compareTo(v_Salvage_Amount)==-1 )
							  A_Period_Exp =v_Base_Amount.subtract(p_Accum_Dep).subtract(v_Salvage_Amount);
					 }		
				}
				//System.out.println("DB200: "+A_Period_Exp);
				return A_Period_Exp;
			}
		  catch (Exception e)
		  {
			  System.out.println("DB200: "+e);
		  }
		  finally
		  {
			  DB.close(rs, pstmt);
			  rs = null; pstmt = null;
		  }
		  return A_Period_Exp;		
	}
	
	static public BigDecimal DBVAR(int p_SL,int p_A_ASSET_ID, double p_A_CURRENT_PERIOD, String p_POSTINGTYPE,
			int p_A_ASSET_ACCT_ID,  BigDecimal p_Accum_Dep) 
	{		
		  BigDecimal v_DB = new BigDecimal(0.0);		 
		  BigDecimal v_Accum_Dep = new BigDecimal(0.0);
		  BigDecimal v_SL = new BigDecimal(0.0);
		  BigDecimal A_Period_Exp = new BigDecimal(0.0);
		  double v_Var = 0.0;
		  int v_counter = 0;
		  double v_months = 0;
		  int v_firstyr = 0;
		  double v_monthadj = 0;
		  BigDecimal v_adj = new BigDecimal(0.0);		  
		  BigDecimal v_Base_Amount = new BigDecimal(0.0);
		  BigDecimal v_Salvage_Amount = new BigDecimal(0.0);
		  double v_Life_Periods = 0;
		  double v_Life_Years = 0;
		  String v_con_type = null;
		 
		  StringBuffer sqlB = new StringBuffer ("SELECT A.A_ASSET_COST, A.A_SALVAGE_VALUE, A.A_LIFE_PERIOD, A.A_ASSET_LIFE_YEARS, A.DATEACCT,"
			+ " D.ASSETSERVICEDATE, C.CONVENTIONTYPE, A_DEPRECIATION_VARIABLE_PERC  "
			+ " FROM A_DEPRECIATION_WORKFILE A, A_ASSET_ACCT B,A_ASSET D,A_DEPRECIATION_CONVENTION C "
			+ " WHERE A.A_ASSET_ID = " + p_A_ASSET_ID  
			+ " AND B.A_ASSET_ID = " + p_A_ASSET_ID
			+ " AND A_PERIOD_POSTED+1 >= A_PERIOD_START AND A_PERIOD_POSTED+1 <= A_PERIOD_END "
			+ " AND A.POSTINGTYPE = '" +  p_POSTINGTYPE
			+ "' AND B.POSTINGTYPE = '" +  p_POSTINGTYPE
			+ "' AND B.A_ASSET_ACCT_ID = " +  p_A_ASSET_ACCT_ID
			+ " AND D.A_ASSET_ID = " + p_A_ASSET_ID
			+ " AND B.A_DEPRECIATION_CONV_ID = C.A_DEPRECIATION_CONVENTION_ID");		
		  //System.out.println("DBVAR: "+sqlB.toString());
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  ResultSet rs = null;
		  try {				
				rs = pstmt.executeQuery();
				while (rs.next()){				  				 
				  v_counter = 0;
				  v_months = 0;
				  v_firstyr = 0;
				  v_monthadj = 0;
				  v_Var=rs.getDouble("A_DEPRECIATION_VARIABLE_PERC");
				  v_adj = new BigDecimal(0.0);				  
				  v_Base_Amount = rs.getBigDecimal("A_ASSET_COST");
				  v_Salvage_Amount = rs.getBigDecimal("A_SALVAGE_VALUE");
				  v_Life_Periods = rs.getDouble("A_LIFE_PERIOD");
				  v_Life_Years = rs.getDouble("A_ASSET_LIFE_YEARS");
				  v_con_type = rs.getString("CONVENTIONTYPE");
				  Calendar calendar = new GregorianCalendar();
				  calendar.setTime(rs.getDate("ASSETSERVICEDATE"));
				  int AssetServiceDateYear = calendar.get(Calendar.YEAR);
				  int AssetServiceDateMonth = calendar.get(Calendar.MONTH);					
				  calendar.setTime(rs.getDate("DATEACCT"));
				  int DateAcctYear = calendar.get(Calendar.YEAR);
				  
				  double v_period = DateAcctYear - AssetServiceDateYear;				  
				    while (v_counter <= v_period){				      
				        if(v_firstyr == 0){
				        v_DB = (v_Base_Amount.subtract(v_Accum_Dep).subtract(v_Salvage_Amount)).multiply(new BigDecimal(v_Var/v_Life_Years));
				        v_months = 12 - AssetServiceDateMonth +1;				        
				        if (v_con_type.compareTo("HYCON") ==0){
				          v_adj = new BigDecimal(Conventions.HYCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj).multiply(new BigDecimal(v_months)));
				          v_monthadj = 6-v_months;
				        }
				        else if (v_con_type.compareTo("FYCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.FYCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,0,AssetServiceDateYear));
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj).multiply(new BigDecimal(v_months)));				          
				          v_monthadj = 12-v_months;
				        }
				        else if (v_con_type.compareTo("DYCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.DYCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj));				          
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));
				          v_monthadj = 0;
				        }
				        else if (v_con_type.compareTo("MQCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.MQCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(v_adj));
				          v_DB =(v_DB.divide(new BigDecimal(v_months),2, BigDecimal.ROUND_HALF_UP));				          				          
				          v_monthadj = 0;
				        }
				        else if (v_con_type.compareTo("FMCON") ==0){				        
				          v_adj = new BigDecimal(Conventions.FMCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateYear));
				          v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.multiply(new BigDecimal(v_months)));				          
				          v_monthadj = 0;
				        }
				        else if (v_con_type.compareTo("MMCON") ==0){
				        	
				          v_Accum_Dep = v_Accum_Dep.add(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(.5)).add(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(v_months-1));
				          v_adj = new BigDecimal(Conventions.MMCON(p_A_ASSET_ID,p_POSTINGTYPE,p_A_ASSET_ACCT_ID,2,AssetServiceDateMonth+1));
				          v_DB = (v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP).multiply(v_adj));
				          v_monthadj = 0;
				        }
				        v_firstyr = 1;
				        }
				     else{				    	
				    	v_DB = (v_Base_Amount.subtract(v_Accum_Dep).subtract(v_Salvage_Amount)).multiply(new BigDecimal(v_Var/v_Life_Years));				        
				        v_Accum_Dep = v_Accum_Dep.add(v_DB);
				        v_DB =(v_DB.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP));
				     }
			        v_counter = v_counter+1;				     
				    }
				    A_Period_Exp = v_DB;					 
					 if (p_SL ==1 ){   
						 if (v_Life_Periods-(p_A_CURRENT_PERIOD+v_monthadj)>0 )					 
							 v_SL = ((v_Base_Amount.subtract(v_Salvage_Amount).subtract(p_Accum_Dep)).divide(new BigDecimal(v_Life_Periods-(p_A_CURRENT_PERIOD+v_monthadj)),2, BigDecimal.ROUND_HALF_UP));				 
		
						 if (A_Period_Exp.compareTo(v_SL)==-1)				   
						   A_Period_Exp = v_SL;				 
						  
						 if (v_Base_Amount.subtract(p_Accum_Dep).subtract(A_Period_Exp).compareTo(v_Salvage_Amount)==-1 )
							  A_Period_Exp =v_Base_Amount.subtract(p_Accum_Dep).subtract(v_Salvage_Amount);
					 }		
				}
				//System.out.println("DBVAR: "+A_Period_Exp);
				return A_Period_Exp;
			}
		  catch (Exception e)
		  {
			  System.out.println("DBVAR: "+e);
		  }
		  finally
		  {
			  DB.close(rs, pstmt);
			  rs = null; pstmt = null;
		  }
		  return A_Period_Exp;		
	}
	
	static public BigDecimal MAN(int p_SL,int p_A_ASSET_ID, double p_A_CURRENT_PERIOD, String p_POSTINGTYPE,
			int p_A_ASSET_ACCT_ID, BigDecimal p_Accum_Dep) 
	{		
		int v_Dep_Mon = 0;
		BigDecimal v_Dep_Sprd =  new BigDecimal(0.0);
		BigDecimal A_Period_Exp = new BigDecimal(0.0);
		
		StringBuffer sqlB = new StringBuffer ("SELECT B.A_ASSET_COST, B.A_SALVAGE_VALUE, B.A_LIFE_PERIOD, "
			+ " A_ASSET_LIFE_YEARS, A_ASSET_LIFE_CURRENT_YEAR,A_ACCUMULATED_DEPR, A_DEPRECIATION_MANUAL_AMOUNT, "
			+ " A_ASSET_SPREAD_ID, A_DEPRECIATION_MANUAL_PERIOD, A.AD_Client_ID "			
			+ " FROM A_DEPRECIATION_WORKFILE B, A_ASSET_ACCT A "
			+ " WHERE B.A_ASSET_ID = " + p_A_ASSET_ID 
			+ " AND	A.A_ASSET_ID = " + p_A_ASSET_ID
			+ " AND	B.POSTINGTYPE = '"+p_POSTINGTYPE
			+ "' AND A.POSTINGTYPE = '"+p_POSTINGTYPE
			+ "' AND A.A_ASSET_ACCT_ID = " + p_A_ASSET_ACCT_ID);
		  //System.out.println("MAN: "+sqlB.toString());
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  ResultSet rs = null;
		  try {				
				rs = pstmt.executeQuery();
				while (rs.next()){
					  if (rs.getString("A_DEPRECIATION_MANUAL_PERIOD").compareTo("PR")==0) 
						  A_Period_Exp = rs.getBigDecimal("A_DEPRECIATION_MANUAL_AMOUNT");
					  else if (rs.getString("A_DEPRECIATION_MANUAL_PERIOD").compareTo("YR")==0){  
					    if (p_A_CURRENT_PERIOD == -1){
					    	//System.out.println("MAN YR - A_CURRENT_PERIOD = -1 ");
					    	StringBuffer sql3 =  new StringBuffer("SELECT PeriodNo"
								  + " FROM C_Period WHERE C_Period_ID = 183 "
								  + " AND AD_Client_ID = ? ");								
							v_Dep_Mon = DB.getSQLValue(null, sql3.toString(), rs.getInt("AD_Client_ID"));
					    }					        
					    else{
					    	//System.out.println("MAN YR - A_CURRENT_PERIOD = " + p_A_CURRENT_PERIOD);
					    	v_Dep_Mon = (int)p_A_CURRENT_PERIOD - (int)(Math.ceil((double)p_A_CURRENT_PERIOD/12)*12-12) ;					  
					    }
					//System.out.println("MAN v_Dep_Mon: "+v_Dep_Mon);
					if (v_Dep_Mon==1){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_1"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());				    	
					    }					          
					else if (v_Dep_Mon==2){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_2"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				  
					else if (v_Dep_Mon==3){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_3"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");			
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				  
					else if (v_Dep_Mon==4){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_4"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				    
					else if (v_Dep_Mon==5){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_5"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				    
					else if (v_Dep_Mon==6){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_6"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				  
					else if (v_Dep_Mon==7){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_7"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				  
					else if (v_Dep_Mon==8){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_8"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				  
					else if (v_Dep_Mon==9){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_9"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				  
					else if (v_Dep_Mon==10){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_10"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				  
					else if (v_Dep_Mon==11)
					{
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_11"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				  
					else if (v_Dep_Mon==12){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_12"
				    			+ " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				  
					else if (v_Dep_Mon==13){
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_13"
				    			+ " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					    }				  
					else
					{
				    	StringBuffer sql3 =  new StringBuffer("SELECT A_Period_14"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = ? ");				    	
				    	v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("A_ASSET_SPREAD_ID"));
				    	//System.out.println("MAN YR - SpreadAmnt = " + sql3.toString());
					 }
					//System.out.println("MAN v_Dep_Sprd: "+v_Dep_Sprd);
					A_Period_Exp = rs.getBigDecimal("A_DEPRECIATION_MANUAL_AMOUNT").multiply(v_Dep_Sprd);
				  }
					  
				}
				//System.out.println("MAN: "+A_Period_Exp);
				return A_Period_Exp;
			}
		  catch (Exception e)
		  {
			  System.out.println("MAN: "+e);
		  }
		  finally
		  {
			  DB.close(rs, pstmt);
			  rs = null; pstmt = null;
		  }
		  return A_Period_Exp;		
	}
	static public BigDecimal SL(int p_SL,int p_A_ASSET_ID, double p_A_CURRENT_PERIOD, String p_POSTINGTYPE,
			int p_A_ASSET_ACCT_ID, BigDecimal p_Accum_Dep) 
	{		
		BigDecimal A_Period_Exp = new BigDecimal(0.0);
		StringBuffer sqlB = new StringBuffer ("SELECT A_DEPRECIATION_WORKFILE.A_ASSET_COST, "
			+ "	A_DEPRECIATION_WORKFILE.A_SALVAGE_VALUE, A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD"
			+ " FROM A_DEPRECIATION_WORKFILE, A_ASSET_ACCT"
			+ " WHERE A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + p_A_ASSET_ID 
			+ " AND	A_ASSET_ACCT.A_ASSET_ID = " + p_A_ASSET_ID
			+ " AND	A_DEPRECIATION_WORKFILE.POSTINGTYPE = '"+p_POSTINGTYPE
			+ "' AND A_ASSET_ACCT.POSTINGTYPE = '"+p_POSTINGTYPE
			+ "' AND A_ASSET_ACCT.A_ASSET_ACCT_ID = " + p_A_ASSET_ACCT_ID);
		  
		  //System.out.println("SL: "+sqlB.toString());
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  ResultSet rs = null;
			  try {				
				rs = pstmt.executeQuery();
				while (rs.next()){
					A_Period_Exp = ((rs.getBigDecimal("A_ASSET_COST").subtract(rs.getBigDecimal("A_SALVAGE_VALUE"))).divide( rs.getBigDecimal("A_LIFE_PERIOD"),2, BigDecimal.ROUND_HALF_UP));				
				}
				return A_Period_Exp;
			}
		  catch (Exception e)
		  {
			  System.out.println("SL: "+e);
		  }
		  finally
		  {
			  DB.close(rs, pstmt);
			  rs = null; pstmt = null;
		  }
		  return A_Period_Exp;		
	}

	static public BigDecimal UOP(int p_SL,int p_A_ASSET_ID, int p_A_CURRENT_PERIOD, String p_POSTINGTYPE,
			int p_A_ASSET_ACCT_ID,  BigDecimal p_Accum_Dep) 
	{		
		BigDecimal A_Period_Exp = new BigDecimal(0.0);
		StringBuffer sqlB = new StringBuffer ("SELECT A_DEPRECIATION_WORKFILE.A_ASSET_COST, "
			+ "	A_DEPRECIATION_WORKFILE.A_SALVAGE_VALUE, A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD"
			+ " A_ASSET.LIFEUSEUNITS, A_ASSET.USEUNITS, A_DEPRECIATION_WORKFILE.A_ACCUMULATED_DEPR"
			+ " FROM A_DEPRECIATION_WORKFILE, A_ASSET_ACCT"
			+ " WHERE A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + p_A_ASSET_ID 
			+ " AND	A_ASSET_ACCT.A_ASSET_ID = " + p_A_ASSET_ID
			+ " AND	A_DEPRECIATION_WORKFILE.POSTINGTYPE = '"+p_POSTINGTYPE
			+ "' AND A_ASSET_ACCT.POSTINGTYPE = '"+p_POSTINGTYPE
			+ "' AND A_ASSET_ACCT.A_ASSET_ACCT_ID = " + p_A_ASSET_ACCT_ID);
		  //System.out.println("UOP: "+sqlB.toString());
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  try {				
				rs = pstmt.executeQuery();
				while (rs.next()){
					A_Period_Exp = (rs.getBigDecimal("A_ASSET_COST").subtract(rs.getBigDecimal("A_SALVAGE_VALUE"))
							.multiply(new BigDecimal(rs.getDouble("USEUNITS")/rs.getDouble("LIFEUSEUNITS")))
							.subtract(rs.getBigDecimal("A_ACCUMULATED_DEPR")));					
				}
				//System.out.println("UOP: "+A_Period_Exp);
				return A_Period_Exp;
			}
		  catch (Exception e)
		  {
			  System.out.println("UOP: "+e);
		  }
		  finally
		  {
			  DB.close(rs, pstmt);
			  rs = null; pstmt = null;
		  }
		  return A_Period_Exp;		
	}
	static public BigDecimal SYD(int p_SL,int p_A_ASSET_ID, double p_A_CURRENT_PERIOD, String p_POSTINGTYPE, int p_A_ASSET_ACCT_ID,  BigDecimal p_Accum_Dep) 
	{		
		BigDecimal A_Period_Exp = new BigDecimal(0.0);
		StringBuffer sqlB = new StringBuffer ("SELECT A_DEPRECIATION_WORKFILE.A_ASSET_COST, " 
		  + " A_DEPRECIATION_WORKFILE.A_SALVAGE_VALUE, A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD, "
		  + " A_DEPRECIATION_WORKFILE.A_ASSET_LIFE_CURRENT_YEAR, A_DEPRECIATION_WORKFILE.A_ASSET_LIFE_YEARS, "
		  + " A_ASSET.ASSETSERVICEDATE, A_DEPRECIATION_BUILD.DATEACCT"
		  + " FROM A_DEPRECIATION_WORKFILE, A_ASSET_ACCT, A_ASSET, A_DEPRECIATION_BUILD"
		  + " WHERE A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + p_A_ASSET_ID 
		  + " AND	A_ASSET_ACCT.A_ASSET_ID = " + p_A_ASSET_ID
		  + " AND	A_ASSET.A_ASSET_ID = " + p_A_ASSET_ID
		  + " AND A_PERIOD_POSTED+1 >= A_PERIOD_START AND A_PERIOD_POSTED+1 <= A_PERIOD_END "
		  + " AND	A_DEPRECIATION_WORKFILE.POSTINGTYPE = '"+p_POSTINGTYPE
		  + "' AND A_ASSET_ACCT.POSTINGTYPE = '"+p_POSTINGTYPE
		  + "' AND A_ASSET_ACCT.A_ASSET_ACCT_ID = " + p_A_ASSET_ACCT_ID);
		
		  //System.out.println("SYD: "+sqlB.toString());
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  ResultSet rs = null;
		  try {				
				rs = pstmt.executeQuery();
				while (rs.next()){
					int v_life_current_year = (int)(p_A_CURRENT_PERIOD/(12))+1;
					
					
					A_Period_Exp = ((rs.getBigDecimal("A_ASSET_COST").subtract(rs.getBigDecimal("A_SALVAGE_VALUE")))
							.multiply(new BigDecimal(2*(rs.getInt("A_ASSET_LIFE_YEARS")-v_life_current_year+1)))
							.divide(new BigDecimal(rs.getInt("A_ASSET_LIFE_YEARS")*(rs.getInt("A_ASSET_LIFE_YEARS")+1)),2, BigDecimal.ROUND_HALF_UP));
					A_Period_Exp = A_Period_Exp.divide(new BigDecimal(12.0),2, BigDecimal.ROUND_HALF_UP);
										
				}
				//System.out.println("SYD: "+A_Period_Exp);
				return A_Period_Exp;
			}
		  catch (Exception e)
		  {
			  System.out.println("SYD: "+e);
		  }
		  finally
		  {
			  DB.close(rs, pstmt);
			  rs = null; pstmt = null;
		  }
		  return A_Period_Exp;		
	}
	
	static public BigDecimal TAB(int p_SL,int p_A_ASSET_ID, double p_A_CURRENT_PERIOD, String p_POSTINGTYPE,
			int p_A_ASSET_ACCT_ID, BigDecimal p_Accum_Dep) 
	{		
		BigDecimal A_Period_Exp = new BigDecimal(0.0);
		BigDecimal v_Dep_Rate = new BigDecimal(0.0);
		BigDecimal v_Dep_Sprd = new BigDecimal(0.0);
		int v_Dep_Mon = 0;
		int v_Dep_Per = 0;
		StringBuffer sqlB = new StringBuffer ("SELECT A.A_ASSET_COST, A.A_SALVAGE_VALUE, A.A_LIFE_PERIOD," 
		  +	" B.A_DEPRECIATION_TABLE_HEADER_ID,  A.AD_Client_ID, C.A_TERM, C.A_Depreciation_Table_Code "
		  + " FROM A_DEPRECIATION_WORKFILE A, A_ASSET_ACCT B, A_Depreciation_Table_Header C"
		  + " WHERE A.A_ASSET_ID = " + p_A_ASSET_ID 
		  + " AND	B.A_ASSET_ID = " + p_A_ASSET_ID		  
		  + " AND	A.POSTINGTYPE = '"+p_POSTINGTYPE
		  + "' AND B.POSTINGTYPE = '"+p_POSTINGTYPE
		  + "' AND B.A_ASSET_ACCT_ID = " + p_A_ASSET_ACCT_ID
		  + "' AND C.A_DEPRECIATION_TABLE_HEADER_ID = A_DEPRECIATION_TABLE_HEADER_ID");
		
		  //System.out.println("TAB: "+sqlB.toString());
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  ResultSet rs = null;
		  try {				
				rs = pstmt.executeQuery();
				while (rs.next()){
					
					
				if (rs.getString("A_TERM").compareTo("PR")==0){
			    	StringBuffer sql3 =  new StringBuffer("SELECT A_Depreciation_Rate"
							  + " FROM A_Depreciation_Table_Detail WHERE A_DEPRECIATION_TABLE_CODE = " + rs.getString("A_Depreciation_Table_Code")
							  + " AND A_Period = ? ");								
			    	    v_Dep_Rate = DB.getSQLValueBD(null, sql3.toString(), (int)p_A_CURRENT_PERIOD);
						A_Period_Exp = (rs.getBigDecimal("A_ASSET_COST").subtract(rs.getBigDecimal("A_SALVAGE_VALUE")).multiply(v_Dep_Rate));
				    }				    
				else if (rs.getString("A_TERM").compareTo("YR")==0){
					StringBuffer sql3 =  new StringBuffer("SELECT A_Depreciation_Rate"
							  + " FROM A_Depreciation_Table_Detail WHERE A_DEPRECIATION_TABLE_CODE = " + rs.getString("A_Depreciation_Table_Code")
							  + " AND A_Period = ? ");								
			    	v_Dep_Rate = DB.getSQLValueBD(null, sql3.toString(), (int)(Math.ceil((double)p_A_CURRENT_PERIOD+1)/12));
			    	
			    	sql3 =  new StringBuffer("SELECT C_Period_ID"
								  + " FROM A_Depreciation_Build ");								
			    	v_Dep_Per = DB.getSQLValue(null, sql3.toString());
			    	
				    if (p_A_CURRENT_PERIOD == -1){
						sql3 =  new StringBuffer("SELECT PeriodNo"
								  + " FROM C_Period WHERE C_Period_ID = " + v_Dep_Per
								  + " AND AD_Client_ID = ? ");								
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				    
				    else
				    	
				    v_Dep_Mon = (int)p_A_CURRENT_PERIOD - (int)(Math.ceil((double)p_A_CURRENT_PERIOD)/12*12-12) ;
				    
				    if (v_Dep_Mon==1){
						sql3 =  new StringBuffer("SELECT A_Period_1"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");								
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				          
				    else if (v_Dep_Mon==2){
						sql3 =  new StringBuffer("SELECT A_Period_2"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    else if (v_Dep_Mon==3){
						sql3 =  new StringBuffer("SELECT A_Period_3"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    else if (v_Dep_Mon==4){
						sql3 =  new StringBuffer("SELECT A_Period_4"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				     
				    else if (v_Dep_Mon==5){
						sql3 =  new StringBuffer("SELECT A_Period_5"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				     
				    else if (v_Dep_Mon==6){
						sql3 =  new StringBuffer("SELECT A_Period_6"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    else if (v_Dep_Mon==7){
						sql3 =  new StringBuffer("SELECT A_Period_7"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    else if (v_Dep_Mon==8){
						sql3 =  new StringBuffer("SELECT A_Period_8"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    else if (v_Dep_Mon==9){
						sql3 =  new StringBuffer("SELECT A_Period_9"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    else if (v_Dep_Mon==10){
						sql3 =  new StringBuffer("SELECT A_Period_10"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    else if (v_Dep_Mon==11){
						sql3 =  new StringBuffer("SELECT A_Period_11"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    else if (v_Dep_Mon==12){
						sql3 =  new StringBuffer("SELECT A_Period_12"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    else if (v_Dep_Mon==13){
						sql3 =  new StringBuffer("SELECT A_Period_13"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    else{
						sql3 =  new StringBuffer("SELECT A_Period_14"
								  + " FROM A_Asset_Spread WHERE A_Asset_Spread_ID = " + rs.getString("A_Depreciation_Table_Code")
								  + " AND AD_Client_ID = ? ");																
						v_Dep_Sprd = DB.getSQLValueBD(null, sql3.toString(), rs.getInt("AD_Client_ID"));
				    }				   
				    
				    A_Period_Exp = (rs.getBigDecimal("A_ASSET_COST").subtract(rs.getBigDecimal("A_SALVAGE_VALUE")).multiply(v_Dep_Rate).multiply(v_Dep_Sprd));				        
				}
				
				}
				//System.out.println("TAB: "+A_Period_Exp);
				return A_Period_Exp;				
		  }
		  catch (Exception e)
		  {
			  System.out.println("TAB: "+e);
		  }
		  finally
		  {
			  DB.close(rs, pstmt);
			  rs = null; pstmt = null;

		  }
		  return A_Period_Exp;		
	}
	
	}//	Depreciation
