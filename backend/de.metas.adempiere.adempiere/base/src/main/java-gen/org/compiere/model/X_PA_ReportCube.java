/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for PA_ReportCube
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_PA_ReportCube extends PO implements I_PA_ReportCube, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_PA_ReportCube (Properties ctx, int PA_ReportCube_ID, String trxName)
    {
      super (ctx, PA_ReportCube_ID, trxName);
      /** if (PA_ReportCube_ID == 0)
        {
			setC_Calendar_ID (0);
			setName (null);
			setPA_ReportCube_ID (0);
			setProcessing (false);
// N
        } */
    }

    /** Load Constructor */
    public X_PA_ReportCube (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_PA_ReportCube[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Calendar getC_Calendar() throws RuntimeException
    {
		return (I_C_Calendar)MTable.get(getCtx(), I_C_Calendar.Table_Name)
			.getPO(getC_Calendar_ID(), get_TrxName());	}

	/** Set Calendar.
		@param C_Calendar_ID 
		Accounting Calendar Name
	  */
	public void setC_Calendar_ID (int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
	}

	/** Get Calendar.
		@return Accounting Calendar Name
	  */
	public int getC_Calendar_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Calendar_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Activity Dimension.
		@param IsActivityDim 
		Include Activity as a cube dimension
	  */
	public void setIsActivityDim (boolean IsActivityDim)
	{
		set_Value (COLUMNNAME_IsActivityDim, Boolean.valueOf(IsActivityDim));
	}

	/** Get Activity Dimension.
		@return Include Activity as a cube dimension
	  */
	public boolean isActivityDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsActivityDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Business Partner Dimension.
		@param IsBPartnerDim 
		Include Business Partner as a cube dimension
	  */
	public void setIsBPartnerDim (boolean IsBPartnerDim)
	{
		set_Value (COLUMNNAME_IsBPartnerDim, Boolean.valueOf(IsBPartnerDim));
	}

	/** Get Business Partner Dimension.
		@return Include Business Partner as a cube dimension
	  */
	public boolean isBPartnerDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsBPartnerDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Campaign Dimension.
		@param IsCampaignDim 
		Include Campaign as a cube dimension
	  */
	public void setIsCampaignDim (boolean IsCampaignDim)
	{
		set_Value (COLUMNNAME_IsCampaignDim, Boolean.valueOf(IsCampaignDim));
	}

	/** Get Campaign Dimension.
		@return Include Campaign as a cube dimension
	  */
	public boolean isCampaignDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsCampaignDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set GL Budget Dimension.
		@param IsGLBudgetDim 
		Include GL Budget as a cube dimension
	  */
	public void setIsGLBudgetDim (boolean IsGLBudgetDim)
	{
		set_Value (COLUMNNAME_IsGLBudgetDim, Boolean.valueOf(IsGLBudgetDim));
	}

	/** Get GL Budget Dimension.
		@return Include GL Budget as a cube dimension
	  */
	public boolean isGLBudgetDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsGLBudgetDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Location From Dimension.
		@param IsLocFromDim 
		Include Location From as a cube dimension
	  */
	public void setIsLocFromDim (boolean IsLocFromDim)
	{
		set_Value (COLUMNNAME_IsLocFromDim, Boolean.valueOf(IsLocFromDim));
	}

	/** Get Location From Dimension.
		@return Include Location From as a cube dimension
	  */
	public boolean isLocFromDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsLocFromDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Location To  Dimension.
		@param IsLocToDim 
		Include Location To as a cube dimension
	  */
	public void setIsLocToDim (boolean IsLocToDim)
	{
		set_Value (COLUMNNAME_IsLocToDim, Boolean.valueOf(IsLocToDim));
	}

	/** Get Location To  Dimension.
		@return Include Location To as a cube dimension
	  */
	public boolean isLocToDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsLocToDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set OrgTrx Dimension.
		@param IsOrgTrxDim 
		Include OrgTrx as a cube dimension
	  */
	public void setIsOrgTrxDim (boolean IsOrgTrxDim)
	{
		set_Value (COLUMNNAME_IsOrgTrxDim, Boolean.valueOf(IsOrgTrxDim));
	}

	/** Get OrgTrx Dimension.
		@return Include OrgTrx as a cube dimension
	  */
	public boolean isOrgTrxDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsOrgTrxDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Product Dimension.
		@param IsProductDim 
		Include Product as a cube dimension
	  */
	public void setIsProductDim (boolean IsProductDim)
	{
		set_Value (COLUMNNAME_IsProductDim, Boolean.valueOf(IsProductDim));
	}

	/** Get Product Dimension.
		@return Include Product as a cube dimension
	  */
	public boolean isProductDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsProductDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Project Dimension.
		@param IsProjectDim 
		Include Project as a cube dimension
	  */
	public void setIsProjectDim (boolean IsProjectDim)
	{
		set_Value (COLUMNNAME_IsProjectDim, Boolean.valueOf(IsProjectDim));
	}

	/** Get Project Dimension.
		@return Include Project as a cube dimension
	  */
	public boolean isProjectDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsProjectDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Project Phase  Dimension.
		@param IsProjectPhaseDim 
		Include Project Phase as a cube dimension
	  */
	public void setIsProjectPhaseDim (boolean IsProjectPhaseDim)
	{
		set_Value (COLUMNNAME_IsProjectPhaseDim, Boolean.valueOf(IsProjectPhaseDim));
	}

	/** Get Project Phase  Dimension.
		@return Include Project Phase as a cube dimension
	  */
	public boolean isProjectPhaseDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsProjectPhaseDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Project Task  Dimension.
		@param IsProjectTaskDim 
		Include Project Task as a cube dimension
	  */
	public void setIsProjectTaskDim (boolean IsProjectTaskDim)
	{
		set_Value (COLUMNNAME_IsProjectTaskDim, Boolean.valueOf(IsProjectTaskDim));
	}

	/** Get Project Task  Dimension.
		@return Include Project Task as a cube dimension
	  */
	public boolean isProjectTaskDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsProjectTaskDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Region Dimension.
		@param IsSalesRegionDim 
		Include Sales Region as a cube dimension
	  */
	public void setIsSalesRegionDim (boolean IsSalesRegionDim)
	{
		set_Value (COLUMNNAME_IsSalesRegionDim, Boolean.valueOf(IsSalesRegionDim));
	}

	/** Get Sales Region Dimension.
		@return Include Sales Region as a cube dimension
	  */
	public boolean isSalesRegionDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsSalesRegionDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sub Acct Dimension.
		@param IsSubAcctDim 
		Include Sub Acct as a cube dimension
	  */
	public void setIsSubAcctDim (boolean IsSubAcctDim)
	{
		set_Value (COLUMNNAME_IsSubAcctDim, Boolean.valueOf(IsSubAcctDim));
	}

	/** Get Sub Acct Dimension.
		@return Include Sub Acct as a cube dimension
	  */
	public boolean isSubAcctDim () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubAcctDim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set User 1 Dimension.
		@param IsUser1Dim 
		Include User 1 as a cube dimension
	  */
	public void setIsUser1Dim (boolean IsUser1Dim)
	{
		set_Value (COLUMNNAME_IsUser1Dim, Boolean.valueOf(IsUser1Dim));
	}

	/** Get User 1 Dimension.
		@return Include User 1 as a cube dimension
	  */
	public boolean isUser1Dim () 
	{
		Object oo = get_Value(COLUMNNAME_IsUser1Dim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set User 2 Dimension.
		@param IsUser2Dim 
		Include User 2 as a cube dimension
	  */
	public void setIsUser2Dim (boolean IsUser2Dim)
	{
		set_Value (COLUMNNAME_IsUser2Dim, Boolean.valueOf(IsUser2Dim));
	}

	/** Get User 2 Dimension.
		@return Include User 2 as a cube dimension
	  */
	public boolean isUser2Dim () 
	{
		Object oo = get_Value(COLUMNNAME_IsUser2Dim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set User Element 1 Dimension.
		@param IsUserElement1Dim 
		Include User Element 1 as a cube dimension
	  */
	public void setIsUserElement1Dim (boolean IsUserElement1Dim)
	{
		set_Value (COLUMNNAME_IsUserElement1Dim, Boolean.valueOf(IsUserElement1Dim));
	}

	/** Get User Element 1 Dimension.
		@return Include User Element 1 as a cube dimension
	  */
	public boolean isUserElement1Dim () 
	{
		Object oo = get_Value(COLUMNNAME_IsUserElement1Dim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set User Element 2 Dimension.
		@param IsUserElement2Dim 
		Include User Element 2 as a cube dimension
	  */
	public void setIsUserElement2Dim (boolean IsUserElement2Dim)
	{
		set_Value (COLUMNNAME_IsUserElement2Dim, Boolean.valueOf(IsUserElement2Dim));
	}

	/** Get User Element 2 Dimension.
		@return Include User Element 2 as a cube dimension
	  */
	public boolean isUserElement2Dim () 
	{
		Object oo = get_Value(COLUMNNAME_IsUserElement2Dim);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Recalculated.
		@param LastRecalculated 
		The time last recalculated.
	  */
	public void setLastRecalculated (Timestamp LastRecalculated)
	{
		set_Value (COLUMNNAME_LastRecalculated, LastRecalculated);
	}

	/** Get Last Recalculated.
		@return The time last recalculated.
	  */
	public Timestamp getLastRecalculated () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastRecalculated);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Report Cube.
		@param PA_ReportCube_ID 
		Define reporting cube for pre-calculation of summary accounting data.
	  */
	public void setPA_ReportCube_ID (int PA_ReportCube_ID)
	{
		if (PA_ReportCube_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PA_ReportCube_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PA_ReportCube_ID, Integer.valueOf(PA_ReportCube_ID));
	}

	/** Get Report Cube.
		@return Define reporting cube for pre-calculation of summary accounting data.
	  */
	public int getPA_ReportCube_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_ReportCube_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}