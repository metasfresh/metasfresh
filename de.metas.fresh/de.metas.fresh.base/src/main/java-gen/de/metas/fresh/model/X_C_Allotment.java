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
package de.metas.fresh.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Allotment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Allotment extends org.compiere.model.PO implements I_C_Allotment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1975188225L;

    /** Standard Constructor */
    public X_C_Allotment (Properties ctx, int C_Allotment_ID, String trxName)
    {
      super (ctx, C_Allotment_ID, trxName);
      /** if (C_Allotment_ID == 0)
        {
			setC_Allotment_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_C_Allotment (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Parzelle.
		@param C_Allotment_ID Parzelle	  */
	@Override
	public void setC_Allotment_ID (int C_Allotment_ID)
	{
		if (C_Allotment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Allotment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Allotment_ID, Integer.valueOf(C_Allotment_ID));
	}

	/** Get Parzelle.
		@return Parzelle	  */
	@Override
	public int getC_Allotment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Allotment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Location getC_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	/** Set Anschrift.
		@param C_Location_ID 
		Adresse oder Anschrift
	  */
	@Override
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Anschrift.
		@return Adresse oder Anschrift
	  */
	@Override
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}