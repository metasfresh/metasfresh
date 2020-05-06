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
package de.metas.workflow.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Doc_Responsible
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Doc_Responsible extends org.compiere.model.PO implements I_C_Doc_Responsible, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1159172570L;

    /** Standard Constructor */
    public X_C_Doc_Responsible (Properties ctx, int C_Doc_Responsible_ID, String trxName)
    {
      super (ctx, C_Doc_Responsible_ID, trxName);
      /** if (C_Doc_Responsible_ID == 0)
        {
			setAD_Table_ID (0);
			setAD_WF_Responsible_ID (0);
			setAD_WF_Responsible_Name (null);
			setC_Doc_Responsible_ID (0);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Doc_Responsible (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_Doc_Responsible[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User_Responsible() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_Responsible_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_Responsible(org.compiere.model.I_AD_User AD_User_Responsible)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_Responsible_ID, org.compiere.model.I_AD_User.class, AD_User_Responsible);
	}

	/** Set Responsible User.
		@param AD_User_Responsible_ID Responsible User	  */
	@Override
	public void setAD_User_Responsible_ID (int AD_User_Responsible_ID)
	{
		if (AD_User_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Responsible_ID, Integer.valueOf(AD_User_Responsible_ID));
	}

	/** Get Responsible User.
		@return Responsible User	  */
	@Override
	public int getAD_User_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Responsible_ID, org.compiere.model.I_AD_WF_Responsible.class);
	}

	@Override
	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Responsible_ID, org.compiere.model.I_AD_WF_Responsible.class, AD_WF_Responsible);
	}

	/** Set Workflow - Verantwortlicher.
		@param AD_WF_Responsible_ID 
		Verantwortlicher für die Ausführung des Workflow
	  */
	@Override
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
	}

	/** Get Workflow - Verantwortlicher.
		@return Verantwortlicher für die Ausführung des Workflow
	  */
	@Override
	public int getAD_WF_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Workflow - Verantwortlicher (text).
		@param AD_WF_Responsible_Name Workflow - Verantwortlicher (text)	  */
	@Override
	public void setAD_WF_Responsible_Name (java.lang.String AD_WF_Responsible_Name)
	{
		set_Value (COLUMNNAME_AD_WF_Responsible_Name, AD_WF_Responsible_Name);
	}

	/** Get Workflow - Verantwortlicher (text).
		@return Workflow - Verantwortlicher (text)	  */
	@Override
	public java.lang.String getAD_WF_Responsible_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_WF_Responsible_Name);
	}

	/** Set Document Responsible.
		@param C_Doc_Responsible_ID Document Responsible	  */
	@Override
	public void setC_Doc_Responsible_ID (int C_Doc_Responsible_ID)
	{
		if (C_Doc_Responsible_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Responsible_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Responsible_ID, Integer.valueOf(C_Doc_Responsible_ID));
	}

	/** Get Document Responsible.
		@return Document Responsible	  */
	@Override
	public int getC_Doc_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Doc_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}