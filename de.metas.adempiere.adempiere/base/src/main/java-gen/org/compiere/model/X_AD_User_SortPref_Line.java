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

/** Generated Model for AD_User_SortPref_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_User_SortPref_Line extends org.compiere.model.PO implements I_AD_User_SortPref_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 843245777L;

    /** Standard Constructor */
    public X_AD_User_SortPref_Line (Properties ctx, int AD_User_SortPref_Line_ID, String trxName)
    {
      super (ctx, AD_User_SortPref_Line_ID, trxName);
      /** if (AD_User_SortPref_Line_ID == 0)
        {
			setAD_User_SortPref_Hdr_ID (0);
			setAD_User_SortPref_Line_ID (0);
			setIsAscending (true);
// Y
			setSeqNo (0);
        } */
    }

    /** Load Constructor */
    public X_AD_User_SortPref_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_User_SortPref_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Field getAD_Field() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Field_ID, org.compiere.model.I_AD_Field.class);
	}

	@Override
	public void setAD_Field(org.compiere.model.I_AD_Field AD_Field)
	{
		set_ValueFromPO(COLUMNNAME_AD_Field_ID, org.compiere.model.I_AD_Field.class, AD_Field);
	}

	/** Set Feld.
		@param AD_Field_ID 
		Ein Feld einer Datenbanktabelle
	  */
	@Override
	public void setAD_Field_ID (int AD_Field_ID)
	{
		if (AD_Field_ID < 1) 
			set_Value (COLUMNNAME_AD_Field_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Field_ID, Integer.valueOf(AD_Field_ID));
	}

	/** Get Feld.
		@return Ein Feld einer Datenbanktabelle
	  */
	@Override
	public int getAD_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_InfoColumn getAD_InfoColumn() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_InfoColumn_ID, org.compiere.model.I_AD_InfoColumn.class);
	}

	@Override
	public void setAD_InfoColumn(org.compiere.model.I_AD_InfoColumn AD_InfoColumn)
	{
		set_ValueFromPO(COLUMNNAME_AD_InfoColumn_ID, org.compiere.model.I_AD_InfoColumn.class, AD_InfoColumn);
	}

	/** Set Info Column.
		@param AD_InfoColumn_ID 
		Info Window Column
	  */
	@Override
	public void setAD_InfoColumn_ID (int AD_InfoColumn_ID)
	{
		if (AD_InfoColumn_ID < 1) 
			set_Value (COLUMNNAME_AD_InfoColumn_ID, null);
		else 
			set_Value (COLUMNNAME_AD_InfoColumn_ID, Integer.valueOf(AD_InfoColumn_ID));
	}

	/** Get Info Column.
		@return Info Window Column
	  */
	@Override
	public int getAD_InfoColumn_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_InfoColumn_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User_SortPref_Hdr getAD_User_SortPref_Hdr() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_SortPref_Hdr_ID, org.compiere.model.I_AD_User_SortPref_Hdr.class);
	}

	@Override
	public void setAD_User_SortPref_Hdr(org.compiere.model.I_AD_User_SortPref_Hdr AD_User_SortPref_Hdr)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_SortPref_Hdr_ID, org.compiere.model.I_AD_User_SortPref_Hdr.class, AD_User_SortPref_Hdr);
	}

	/** Set Sortierbegriff pro Benutzer.
		@param AD_User_SortPref_Hdr_ID Sortierbegriff pro Benutzer	  */
	@Override
	public void setAD_User_SortPref_Hdr_ID (int AD_User_SortPref_Hdr_ID)
	{
		if (AD_User_SortPref_Hdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_SortPref_Hdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_SortPref_Hdr_ID, Integer.valueOf(AD_User_SortPref_Hdr_ID));
	}

	/** Get Sortierbegriff pro Benutzer.
		@return Sortierbegriff pro Benutzer	  */
	@Override
	public int getAD_User_SortPref_Hdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_SortPref_Hdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sortierbegriff pro Benutzer Position.
		@param AD_User_SortPref_Line_ID Sortierbegriff pro Benutzer Position	  */
	@Override
	public void setAD_User_SortPref_Line_ID (int AD_User_SortPref_Line_ID)
	{
		if (AD_User_SortPref_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_SortPref_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_SortPref_Line_ID, Integer.valueOf(AD_User_SortPref_Line_ID));
	}

	/** Get Sortierbegriff pro Benutzer Position.
		@return Sortierbegriff pro Benutzer Position	  */
	@Override
	public int getAD_User_SortPref_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_SortPref_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Spaltenname.
		@param ColumnName 
		Name der Spalte in der Datenbank
	  */
	@Override
	public void setColumnName (java.lang.String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get Spaltenname.
		@return Name der Spalte in der Datenbank
	  */
	@Override
	public java.lang.String getColumnName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ColumnName);
	}

	/** Set Aufsteigender.
		@param IsAscending Aufsteigender	  */
	@Override
	public void setIsAscending (boolean IsAscending)
	{
		set_Value (COLUMNNAME_IsAscending, Boolean.valueOf(IsAscending));
	}

	/** Get Aufsteigender.
		@return Aufsteigender	  */
	@Override
	public boolean isAscending () 
	{
		Object oo = get_Value(COLUMNNAME_IsAscending);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}