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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.compiere.util.DB;

/**
 * Fixed Asset Conventions
 * 
 * @author Rob Klein
 * @version 	$Id: Conventions.java,v 1.0 $
 * 
 */
public class Conventions {
 
	/**
	*
	*
	*/
	static public double Dep_Convention(String Type,int p_A_ASSET_ID, String p_POSTINGTYPE, int p_A_ASSET_ACCT_ID, int p_Flag, double p_Period) 
	{		
	
		if(Type.compareTo("FMCON")==0)			
			return FMCON(p_A_ASSET_ID, p_POSTINGTYPE, p_A_ASSET_ACCT_ID, p_Flag, p_Period);
		else if(Type.compareTo("FYCON")==0)		
			return FYCON(p_A_ASSET_ID, p_POSTINGTYPE, p_A_ASSET_ACCT_ID, p_Flag, p_Period);		
		else if(Type.compareTo("DYCON")==0)
			return DYCON(p_A_ASSET_ID, p_POSTINGTYPE, p_A_ASSET_ACCT_ID, p_Flag, p_Period);			
		else if(Type.compareTo("MMCON")==0)		
			return MMCON(p_A_ASSET_ID, p_POSTINGTYPE, p_A_ASSET_ACCT_ID, p_Flag, p_Period);
		else if(Type.compareTo("MQCON")==0)		
			return MQCON(p_A_ASSET_ID, p_POSTINGTYPE, p_A_ASSET_ACCT_ID, p_Flag, p_Period);
		else if(Type.compareTo("HYCON")==0)		
			return HYCON(p_A_ASSET_ID, p_POSTINGTYPE, p_A_ASSET_ACCT_ID, p_Flag, p_Period);
		else
			return 0.0;
			
	 }
	
	 
	/**
	*
	*
	*/
	static public double FMCON(int p_A_ASSET_ID, String p_POSTINGTYPE, int p_A_ASSET_ACCT_ID, int p_Flag, double p_Period) 
	{		
		return 1.0;
    
	 }
	
	static public double HYCON(int p_A_ASSET_ID, String p_POSTINGTYPE, int p_A_ASSET_ACCT_ID, int p_Flag, double p_Period) 
	{		
		int v_adj=0;
		  StringBuffer sqlB = new StringBuffer ("SELECT A_ASSET.ASSETSERVICEDATE, A_DEPRECIATION_WORKFILE.A_PERIOD_POSTED,"
		    + " A_DEPRECIATION_WORKFILE.A_ASSET_LIFE_YEARS, A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD,"
		    + " A_DEPRECIATION_WORKFILE.DATEACCT"		   
		    + " FROM A_DEPRECIATION_WORKFILE, A_ASSET"
		    + " WHERE A_ASSET.A_ASSET_ID = " + p_A_ASSET_ID 
		    + " AND A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + p_A_ASSET_ID 
		    + " AND A_DEPRECIATION_WORKFILE.POSTINGTYPE = '" + p_POSTINGTYPE+"'");
		  
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
					
					
					int v_Months_of_Half_Year = (12-(AssetServiceDateMonth)+1);
					
					//ADJUST PERIOD FOR MID-MONTH CONVENTION
					   if(DateAcctYear == AssetServiceDateYear)   
					      v_adj = 6/v_Months_of_Half_Year;
					   else
					       v_adj =  1;					   

					if(p_Flag == 2){
					 //ADJUST COST CORRECTIONS FOR HALF-YEAR CONVENTION
					    if (p_Period == AssetServiceDateYear)
					       v_adj = 6/v_Months_of_Half_Year;
					    else
					       v_adj =  1;						
					}				
				}
		  }
		  catch (Exception e)
		  {
			  System.out.println("HYCON"+e);
		  }
		  finally
		  {
			  DB.close(rs, pstmt);
			  rs = null; pstmt = null;
		  }
		  return v_adj;
		
	}				
	 
	static public double FYCON(int p_A_ASSET_ID, String p_POSTINGTYPE, int p_A_ASSET_ACCT_ID, int p_Flag, double p_Period) 
	{		
		int v_adj=0;
		  StringBuffer sqlB = new StringBuffer ("SELECT A_ASSET.ASSETSERVICEDATE," 
		  	+ " A_DEPRECIATION_WORKFILE.A_PERIOD_POSTED,"
			+ " A_DEPRECIATION_WORKFILE.A_ASSET_LIFE_YEARS, A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD,"
			+ " A_DEPRECIATION_WORKFILE.DATEACCT"
			+ " FROM A_DEPRECIATION_WORKFILE, A_ASSET"
			+ " WHERE A_ASSET.A_ASSET_ID = " + p_A_ASSET_ID 
		    + " AND A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + p_A_ASSET_ID 
		    + " AND A_DEPRECIATION_WORKFILE.POSTINGTYPE = '" + p_POSTINGTYPE+"'");
		  
		  
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
					
					int v_Months_of_Full_Year = 12 - AssetServiceDateMonth;					
					if(p_Flag < 2){
						//ADJUST PERIOD FOR FULL-YEAR CONVENTION
						if(DateAcctYear == AssetServiceDateYear)				        
				         v_adj =  12/v_Months_of_Full_Year;
				        else
				         v_adj = 1;
					}
					if(p_Flag == 2){
					 //ADJUST COST CORRECTIONS FOR FULL-YEAR CONVENTION
					       v_adj = 12/v_Months_of_Full_Year;
					}
					
					
			}				
				
		  }
		  catch (Exception e)
		  {
			  System.out.println("FYCON"+e);
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
		  
		  return v_adj;
		
	}				
	
	static public double DYCON(int p_A_ASSET_ID, String p_POSTINGTYPE, int p_A_ASSET_ACCT_ID, int p_Flag, double p_Period) 
	{		
		int v_adj=0;
		  StringBuffer sqlB = new StringBuffer ("SELECT A_ASSET.ASSETSERVICEDATE," 
		  	+ " A_DEPRECIATION_WORKFILE.A_PERIOD_POSTED,"
			+ " A_DEPRECIATION_WORKFILE.A_ASSET_LIFE_YEARS, A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD,"
			+ " A_DEPRECIATION_WORKFILE.DATEACCT"
			+ " FROM A_DEPRECIATION_WORKFILE, A_ASSET"
			+ " WHERE A_ASSET.A_ASSET_ID = " + p_A_ASSET_ID 
		    + " AND A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + p_A_ASSET_ID 
		    + " AND A_DEPRECIATION_WORKFILE.POSTINGTYPE = '" + p_POSTINGTYPE+"'");
		  
		  
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  try {				
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()){
					
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(rs.getDate("ASSETSERVICEDATE"));
					int AssetServiceDateYear = calendar.get(Calendar.YEAR);
					int AssetServiceDateMonth = calendar.get(Calendar.MONTH);
					int AssetServiceDateDay = calendar.get(Calendar.DAY_OF_YEAR);
					calendar.setTime(rs.getDate("DATEACCT"));
					int DateAcctYear = calendar.get(Calendar.YEAR);
					int DateAcctMonth = calendar.get(Calendar.MONTH);
					
					if(p_Flag ==0){
						
						 //ADJUST PERIOD FOR DAY CONVENTION
							if(DateAcctYear == AssetServiceDateYear){									
						           return ((365-(double)AssetServiceDateDay)/365);						
							}
							else if ((DateAcctYear*12 + DateAcctMonth) == (AssetServiceDateYear+rs.getInt("A_ASSET_LIFE_YEARS"))*12 + AssetServiceDateMonth) 
						    	   return ((double)AssetServiceDateDay/365);
							else if ((DateAcctYear*12 + DateAcctMonth) > (AssetServiceDateYear+rs.getInt("A_ASSET_LIFE_YEARS"))*12 + AssetServiceDateMonth)
						    	   return ((double)AssetServiceDateDay/365);
						    else        
						           return 1;
					}
					
					if(p_Flag ==1){						
						 //ADJUST PERIOD FOR DAY CONVENTION
							if(DateAcctYear == AssetServiceDateYear){								
					           return ((365-(double)AssetServiceDateDay)/365);
							}
							if(DateAcctYear == (AssetServiceDateYear+rs.getInt("A_ASSET_LIFE_YEARS")+1))
							   return ((double)AssetServiceDateDay/365);
							else        
						       return 1;					
					}
					
					if(p_Flag == 2){
					 //ADJUST PERIOD FOR DAY CONVENTION
						return ((365-(double)AssetServiceDateDay)/365);
					}					
			}				
		  }
		  catch (Exception e)
		  {
			  System.out.println("DYCON"+e);
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
		  
		  return v_adj;
		
	}	   
	static public double MMCON(int p_A_ASSET_ID, String p_POSTINGTYPE, int p_A_ASSET_ACCT_ID, int p_Flag, double p_Period) 
	{		
		int v_adj=0;
		  StringBuffer sqlB = new StringBuffer ("SELECT A_ASSET.ASSETSERVICEDATE," 
		  	+ " A_DEPRECIATION_WORKFILE.A_PERIOD_POSTED,"
			+ " A_DEPRECIATION_WORKFILE.A_ASSET_LIFE_YEARS, A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD,"
			+ " A_DEPRECIATION_WORKFILE.DATEACCT"
			+ " FROM A_DEPRECIATION_WORKFILE, A_ASSET"
			+ " WHERE A_ASSET.A_ASSET_ID = " + p_A_ASSET_ID 
		    + " AND A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + p_A_ASSET_ID 
		    + " AND A_DEPRECIATION_WORKFILE.POSTINGTYPE = '" + p_POSTINGTYPE+"'");
		  
		  
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  try {				
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()){
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(rs.getDate("ASSETSERVICEDATE"));					
					int AssetServiceDateMonth = calendar.get(Calendar.MONTH);
					calendar.setTime(rs.getDate("DATEACCT"));					
					int DateAcctMonth = calendar.get(Calendar.MONTH);
					if(p_Flag ==0){
						if(DateAcctMonth - AssetServiceDateMonth >1)						
						  return 1;
						else
						  return .5;
					}					
					if(p_Flag ==1){
						if(p_Period == 0)
							return .5;
						else
							return 1;						
					}					
					if(p_Flag == 2){
						if (p_Period - AssetServiceDateMonth >1)
						  return 1;
						else
						  return .5;
					}					
			}				
		  }
		  catch (Exception e)
		  {
			  System.out.println("MMCON"+e);
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
		  return v_adj;		
	}	  
	
	static public double MQCON(int p_A_ASSET_ID, String p_POSTINGTYPE, int p_A_ASSET_ACCT_ID, int p_Flag, double p_Period) 
	{		
		int v_adj=0;
		  StringBuffer sqlB = new StringBuffer ("SELECT A_ASSET.ASSETSERVICEDATE," 
		  	+ " A_DEPRECIATION_WORKFILE.A_PERIOD_POSTED,"
			+ " A_DEPRECIATION_WORKFILE.A_ASSET_LIFE_YEARS, A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD,"
			+ " A_DEPRECIATION_WORKFILE.DATEACCT"
			+ " FROM A_DEPRECIATION_WORKFILE, A_ASSET"
			+ " WHERE A_ASSET.A_ASSET_ID = " + p_A_ASSET_ID 
		    + " AND A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + p_A_ASSET_ID 
		    + " AND A_DEPRECIATION_WORKFILE.POSTINGTYPE = '" + p_POSTINGTYPE+"'");
		  
		  
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
					int DateAcctMonth = calendar.get(Calendar.MONTH);
					if(p_Flag <2){
						
						//ADJUST PERIOD FOR MID-QUARTER CONVENTION
						if(DateAcctYear == AssetServiceDateYear){					 
					          if(AssetServiceDateMonth < 4 )
					            return .875;
					          else if (AssetServiceDateMonth  < 7 )
					          	return  .625;
					          else if (AssetServiceDateMonth < 10 )
					          	return  .375;
					          else if (AssetServiceDateMonth  > 9 )
					          	return  .125;
						}
					   else if(DateAcctYear*12 + DateAcctMonth >= ((AssetServiceDateYear+rs.getInt("A_ASSET_LIFE_YEARS"))*12 + AssetServiceDateMonth)){					   
					          if(AssetServiceDateMonth < 4 )
					        	return  .125;
					          else if (AssetServiceDateMonth  < 7 )
					      	    return  .375;
					          else if (AssetServiceDateMonth < 10 )
					      	    return  .625;
					          else if (AssetServiceDateMonth  > 9 )
					      	    return  .875;
					   }
					   else
					     	return 1;						
					}					
										
					if(p_Flag == 2){
						if(AssetServiceDateMonth< 4 )
				            return .875;
				          else if (AssetServiceDateMonth  < 7 )
				          	return  .625;
				          else if (AssetServiceDateMonth  < 10 )
				          	return  .375;
				          else if (AssetServiceDateMonth > 9 )
				        	return  .125;
					}					
			}				
		  }
		  catch (Exception e)
		  {
			  System.out.println("MQCON"+e);
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
		  return v_adj;		
	}	  
	
	  
	}//	Conventions

