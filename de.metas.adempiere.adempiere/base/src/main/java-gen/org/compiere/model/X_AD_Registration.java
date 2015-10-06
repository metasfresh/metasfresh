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

/** Generated Model for AD_Registration
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Registration extends PO implements I_AD_Registration, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Registration (Properties ctx, int AD_Registration_ID, String trxName)
    {
      super (ctx, AD_Registration_ID, trxName);
      /** if (AD_Registration_ID == 0)
        {
			setAD_Registration_ID (0);
// 0
			setAD_System_ID (0);
// 0
			setIsAllowPublish (true);
// Y
			setIsAllowStatistics (true);
// Y
			setIsInProduction (false);
			setIsRegistered (false);
// N
        } */
    }

    /** Load Constructor */
    public X_AD_Registration (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_AD_Registration[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set System Registration.
		@param AD_Registration_ID 
		System Registration
	  */
	public void setAD_Registration_ID (int AD_Registration_ID)
	{
		if (AD_Registration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Registration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Registration_ID, Integer.valueOf(AD_Registration_ID));
	}

	/** Get System Registration.
		@return System Registration
	  */
	public int getAD_Registration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Registration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_System getAD_System() throws RuntimeException
    {
		return (I_AD_System)MTable.get(getCtx(), I_AD_System.Table_Name)
			.getPO(getAD_System_ID(), get_TrxName());	}

	/** Set System.
		@param AD_System_ID 
		System Definition
	  */
	public void setAD_System_ID (int AD_System_ID)
	{
		if (AD_System_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_System_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_System_ID, Integer.valueOf(AD_System_ID));
	}

	/** Get System.
		@return System Definition
	  */
	public int getAD_System_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_System_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Location getC_Location() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
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

	/** Set Industry Info.
		@param IndustryInfo 
		Information of the industry (e.g. professional service, distribution of furnitures, ..)
	  */
	public void setIndustryInfo (String IndustryInfo)
	{
		set_Value (COLUMNNAME_IndustryInfo, IndustryInfo);
	}

	/** Get Industry Info.
		@return Information of the industry (e.g. professional service, distribution of furnitures, ..)
	  */
	public String getIndustryInfo () 
	{
		return (String)get_Value(COLUMNNAME_IndustryInfo);
	}

	/** Set Allowed to be Published.
		@param IsAllowPublish 
		You allow to publish the information, not just statistical summary info
	  */
	public void setIsAllowPublish (boolean IsAllowPublish)
	{
		set_Value (COLUMNNAME_IsAllowPublish, Boolean.valueOf(IsAllowPublish));
	}

	/** Get Allowed to be Published.
		@return You allow to publish the information, not just statistical summary info
	  */
	public boolean isAllowPublish () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowPublish);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Maintain Statistics.
		@param IsAllowStatistics 
		Maintain general statistics
	  */
	public void setIsAllowStatistics (boolean IsAllowStatistics)
	{
		set_Value (COLUMNNAME_IsAllowStatistics, Boolean.valueOf(IsAllowStatistics));
	}

	/** Get Maintain Statistics.
		@return Maintain general statistics
	  */
	public boolean isAllowStatistics () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowStatistics);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set In Production.
		@param IsInProduction 
		The system is in production
	  */
	public void setIsInProduction (boolean IsInProduction)
	{
		set_Value (COLUMNNAME_IsInProduction, Boolean.valueOf(IsInProduction));
	}

	/** Get In Production.
		@return The system is in production
	  */
	public boolean isInProduction () 
	{
		Object oo = get_Value(COLUMNNAME_IsInProduction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Registered.
		@param IsRegistered 
		The application is registered.
	  */
	public void setIsRegistered (boolean IsRegistered)
	{
		set_ValueNoCheck (COLUMNNAME_IsRegistered, Boolean.valueOf(IsRegistered));
	}

	/** Get Registered.
		@return The application is registered.
	  */
	public boolean isRegistered () 
	{
		Object oo = get_Value(COLUMNNAME_IsRegistered);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Employees.
		@param NumberEmployees 
		Number of employees
	  */
	public void setNumberEmployees (int NumberEmployees)
	{
		set_Value (COLUMNNAME_NumberEmployees, Integer.valueOf(NumberEmployees));
	}

	/** Get Employees.
		@return Number of employees
	  */
	public int getNumberEmployees () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NumberEmployees);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Platform Info.
		@param PlatformInfo 
		Information about Server and Client Platform
	  */
	public void setPlatformInfo (String PlatformInfo)
	{
		set_Value (COLUMNNAME_PlatformInfo, PlatformInfo);
	}

	/** Get Platform Info.
		@return Information about Server and Client Platform
	  */
	public String getPlatformInfo () 
	{
		return (String)get_Value(COLUMNNAME_PlatformInfo);
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

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Remote Addr.
		@param Remote_Addr 
		Remote Address
	  */
	public void setRemote_Addr (String Remote_Addr)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Addr, Remote_Addr);
	}

	/** Get Remote Addr.
		@return Remote Address
	  */
	public String getRemote_Addr () 
	{
		return (String)get_Value(COLUMNNAME_Remote_Addr);
	}

	/** Set Remote Host.
		@param Remote_Host 
		Remote host Info
	  */
	public void setRemote_Host (String Remote_Host)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Host, Remote_Host);
	}

	/** Get Remote Host.
		@return Remote host Info
	  */
	public String getRemote_Host () 
	{
		return (String)get_Value(COLUMNNAME_Remote_Host);
	}

	/** Set Sales Volume in 1.000.
		@param SalesVolume 
		Total Volume of Sales in Thousands of Currency
	  */
	public void setSalesVolume (int SalesVolume)
	{
		set_Value (COLUMNNAME_SalesVolume, Integer.valueOf(SalesVolume));
	}

	/** Get Sales Volume in 1.000.
		@return Total Volume of Sales in Thousands of Currency
	  */
	public int getSalesVolume () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesVolume);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Start Implementation/Production.
		@param StartProductionDate 
		The day you started the implementation (if implementing) - or production (went life) with Adempiere
	  */
	public void setStartProductionDate (Timestamp StartProductionDate)
	{
		set_Value (COLUMNNAME_StartProductionDate, StartProductionDate);
	}

	/** Get Start Implementation/Production.
		@return The day you started the implementation (if implementing) - or production (went life) with Adempiere
	  */
	public Timestamp getStartProductionDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartProductionDate);
	}
}