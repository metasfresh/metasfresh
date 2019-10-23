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

import org.compiere.util.DB;
/**
 * Fixed Asset Depreciation
 * 
 * @author Rob Klein
 * @version 	$Id: Conventions.java,v 1.0 $
 * 
 */
public class DepreciationAdj {
 
	/**
	*
	*
	*/
	static public BigDecimal Dep_Adj(String Type, int p_A_ASSET_ID, BigDecimal p_A_ASSET_ADJUSTMENT, double p_A_PERIODNO,
			String p_POSTINGTYPE,int p_A_ASSET_ACCT_ID)
	{		
		BigDecimal A_Dep_Adj = new BigDecimal(0.0);
		if(Type.compareTo("MDI")==0){			
			A_Dep_Adj = MDI( p_A_ASSET_ID,  p_A_ASSET_ADJUSTMENT,  p_A_PERIODNO,
					 p_POSTINGTYPE, p_A_ASSET_ACCT_ID);
			//System.out.println("MDI Main: "+A_Dep_Adj);
			return A_Dep_Adj;
		}
		else if(Type.compareTo("LDI")==0){		
			A_Dep_Adj = LDI( p_A_ASSET_ID,  p_A_ASSET_ADJUSTMENT,  p_A_PERIODNO,
					 p_POSTINGTYPE, p_A_ASSET_ACCT_ID);
			//System.out.println("LDI Main: "+A_Dep_Adj);
			return A_Dep_Adj;
		}
		else if(Type.compareTo("YDI")==0){					
			A_Dep_Adj = YDI( p_A_ASSET_ID,  p_A_ASSET_ADJUSTMENT,  p_A_PERIODNO,
					 p_POSTINGTYPE, p_A_ASSET_ACCT_ID);
			//System.out.println("YDI Main: "+A_Dep_Adj);
			return A_Dep_Adj;
		}		
		else
			return new BigDecimal(0.0);
			
	 }
	

	static public BigDecimal LDI( int p_A_ASSET_ID, BigDecimal p_A_ASSET_ADJUSTMENT, double p_A_PERIODNO,
			String p_POSTINGTYPE,int p_A_ASSET_ACCT_ID) 
	{		
		  
		  BigDecimal A_Dep_Adj = new BigDecimal(0.0);  
		  
		  StringBuffer sqlB = new StringBuffer ("SELECT A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD, A_DEPRECIATION_WORKFILE.A_PERIOD_POSTED"			
			+ " FROM A_DEPRECIATION_WORKFILE"
			+ " WHERE A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + p_A_ASSET_ID						
			+ " AND A_DEPRECIATION_WORKFILE.POSTINGTYPE = '" +  p_POSTINGTYPE +"'");
					
		  //System.out.println("LDI: "+sqlB.toString());
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  try {				
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()){					 
					A_Dep_Adj = p_A_ASSET_ADJUSTMENT.divide(new BigDecimal(rs.getDouble("A_LIFE_PERIOD")-rs.getDouble("A_PERIOD_POSTED")+1),2, BigDecimal.ROUND_HALF_UP);					 	
				 
				}
				//System.out.println("LDI: "+A_Period_Exp);
				return A_Dep_Adj;
			}
		  catch (Exception e)
		  {
			  System.out.println("LDI: "+e);
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
		  return A_Dep_Adj;		
	}	
	
	static public BigDecimal MDI( int p_A_ASSET_ID, BigDecimal p_A_ASSET_ADJUSTMENT, double p_A_PERIODNO,
			String p_POSTINGTYPE,int p_A_ASSET_ACCT_ID) 
	{				  
		  return p_A_ASSET_ADJUSTMENT;		
	}
	
	static public BigDecimal YDI( int p_A_ASSET_ID, BigDecimal p_A_ASSET_ADJUSTMENT, double p_A_PERIODNO,
			String p_POSTINGTYPE,int p_A_ASSET_ACCT_ID) 
	{		
		  
		  BigDecimal A_Dep_Adj = new BigDecimal(0.0);  
		  
		  StringBuffer sqlB = new StringBuffer ("SELECT A_DEPRECIATION_WORKFILE.A_LIFE_PERIOD, A_DEPRECIATION_WORKFILE.A_PERIOD_POSTED"			
			+ " FROM A_DEPRECIATION_WORKFILE"
			+ " WHERE A_DEPRECIATION_WORKFILE.A_ASSET_ID = " + p_A_ASSET_ID						
			+ " AND A_DEPRECIATION_WORKFILE.POSTINGTYPE = '" +  p_POSTINGTYPE +"'");
					
		  //System.out.println("DB150: "+sqlB.toString());
		  PreparedStatement pstmt = null;
		  pstmt = DB.prepareStatement (sqlB.toString(),null);
		  try {				
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()){					 
					A_Dep_Adj = p_A_ASSET_ADJUSTMENT.divide(new BigDecimal(12-p_A_PERIODNO),2, BigDecimal.ROUND_HALF_UP);				 
				}
				//System.out.println("LDI: "+A_Period_Exp);
				return A_Dep_Adj;
			}
		  catch (Exception e)
		  {
			  System.out.println("LDI: "+e);
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
		  return A_Dep_Adj;		
	}	

	}//	Depreciation
