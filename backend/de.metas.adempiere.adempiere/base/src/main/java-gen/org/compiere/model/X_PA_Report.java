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
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for PA_Report
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_PA_Report extends PO implements I_PA_Report, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_PA_Report (Properties ctx, int PA_Report_ID, String trxName)
    {
      super (ctx, PA_Report_ID, trxName);
      /** if (PA_Report_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Calendar_ID (0);
			setListSources (false);
			setListTrx (false);
			setName (null);
			setPA_ReportColumnSet_ID (0);
			setPA_Report_ID (0);
			setPA_ReportLineSet_ID (0);
			setProcessing (false);
        } */
    }

    /** Load Constructor */
    public X_PA_Report (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_PA_Report[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_PrintFormat getAD_PrintFormat() throws RuntimeException
    {
		return (I_AD_PrintFormat)MTable.get(getCtx(), I_AD_PrintFormat.Table_Name)
			.getPO(getAD_PrintFormat_ID(), get_TrxName());	}

	/** Set Print Format.
		@param AD_PrintFormat_ID 
		Data Print Format
	  */
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
	}

	/** Get Print Format.
		@return Data Print Format
	  */
	public int getAD_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException
    {
		return (I_C_AcctSchema)MTable.get(getCtx(), I_C_AcctSchema.Table_Name)
			.getPO(getC_AcctSchema_ID(), get_TrxName());	}

	/** Set Accounting Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_AD_Process getJasperProcess() throws RuntimeException
    {
		return (I_AD_Process)MTable.get(getCtx(), I_AD_Process.Table_Name)
			.getPO(getJasperProcess_ID(), get_TrxName());	}

	/** Set Jasper Process.
		@param JasperProcess_ID 
		The Jasper Process used by the printengine if any process defined
	  */
	public void setJasperProcess_ID (int JasperProcess_ID)
	{
		if (JasperProcess_ID < 1) 
			set_Value (COLUMNNAME_JasperProcess_ID, null);
		else 
			set_Value (COLUMNNAME_JasperProcess_ID, Integer.valueOf(JasperProcess_ID));
	}

	/** Get Jasper Process.
		@return The Jasper Process used by the printengine if any process defined
	  */
	public int getJasperProcess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JasperProcess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Jasper Process Now.
		@param JasperProcessing Jasper Process Now	  */
	public void setJasperProcessing (String JasperProcessing)
	{
		set_Value (COLUMNNAME_JasperProcessing, JasperProcessing);
	}

	/** Get Jasper Process Now.
		@return Jasper Process Now	  */
	public String getJasperProcessing () 
	{
		return (String)get_Value(COLUMNNAME_JasperProcessing);
	}

	/** Set List Sources.
		@param ListSources 
		List Report Line Sources
	  */
	public void setListSources (boolean ListSources)
	{
		set_Value (COLUMNNAME_ListSources, Boolean.valueOf(ListSources));
	}

	/** Get List Sources.
		@return List Report Line Sources
	  */
	public boolean isListSources () 
	{
		Object oo = get_Value(COLUMNNAME_ListSources);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set List Transactions.
		@param ListTrx 
		List the report transactions
	  */
	public void setListTrx (boolean ListTrx)
	{
		set_Value (COLUMNNAME_ListTrx, Boolean.valueOf(ListTrx));
	}

	/** Get List Transactions.
		@return List the report transactions
	  */
	public boolean isListTrx () 
	{
		Object oo = get_Value(COLUMNNAME_ListTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	public I_PA_ReportColumnSet getPA_ReportColumnSet() throws RuntimeException
    {
		return (I_PA_ReportColumnSet)MTable.get(getCtx(), I_PA_ReportColumnSet.Table_Name)
			.getPO(getPA_ReportColumnSet_ID(), get_TrxName());	}

	/** Set Report Column Set.
		@param PA_ReportColumnSet_ID 
		Collection of Columns for Report
	  */
	public void setPA_ReportColumnSet_ID (int PA_ReportColumnSet_ID)
	{
		if (PA_ReportColumnSet_ID < 1) 
			set_Value (COLUMNNAME_PA_ReportColumnSet_ID, null);
		else 
			set_Value (COLUMNNAME_PA_ReportColumnSet_ID, Integer.valueOf(PA_ReportColumnSet_ID));
	}

	/** Get Report Column Set.
		@return Collection of Columns for Report
	  */
	public int getPA_ReportColumnSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_ReportColumnSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_PA_ReportCube getPA_ReportCube() throws RuntimeException
    {
		return (I_PA_ReportCube)MTable.get(getCtx(), I_PA_ReportCube.Table_Name)
			.getPO(getPA_ReportCube_ID(), get_TrxName());	}

	/** Set Report Cube.
		@param PA_ReportCube_ID 
		Define reporting cube for pre-calculation of summary accounting data.
	  */
	public void setPA_ReportCube_ID (int PA_ReportCube_ID)
	{
		if (PA_ReportCube_ID < 1) 
			set_Value (COLUMNNAME_PA_ReportCube_ID, null);
		else 
			set_Value (COLUMNNAME_PA_ReportCube_ID, Integer.valueOf(PA_ReportCube_ID));
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

	/** Set Financial Report.
		@param PA_Report_ID 
		Financial Report
	  */
	public void setPA_Report_ID (int PA_Report_ID)
	{
		if (PA_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PA_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PA_Report_ID, Integer.valueOf(PA_Report_ID));
	}

	/** Get Financial Report.
		@return Financial Report
	  */
	public int getPA_Report_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_Report_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_PA_ReportLineSet getPA_ReportLineSet() throws RuntimeException
    {
		return (I_PA_ReportLineSet)MTable.get(getCtx(), I_PA_ReportLineSet.Table_Name)
			.getPO(getPA_ReportLineSet_ID(), get_TrxName());	}

	/** Set Report Line Set.
		@param PA_ReportLineSet_ID Report Line Set	  */
	public void setPA_ReportLineSet_ID (int PA_ReportLineSet_ID)
	{
		if (PA_ReportLineSet_ID < 1) 
			set_Value (COLUMNNAME_PA_ReportLineSet_ID, null);
		else 
			set_Value (COLUMNNAME_PA_ReportLineSet_ID, Integer.valueOf(PA_ReportLineSet_ID));
	}

	/** Get Report Line Set.
		@return Report Line Set	  */
	public int getPA_ReportLineSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_ReportLineSet_ID);
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