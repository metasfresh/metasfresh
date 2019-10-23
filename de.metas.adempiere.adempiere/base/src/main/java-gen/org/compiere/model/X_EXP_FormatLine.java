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

/** Generated Model for EXP_FormatLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EXP_FormatLine extends org.compiere.model.PO implements I_EXP_FormatLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1500385332L;

    /** Standard Constructor */
    public X_EXP_FormatLine (Properties ctx, int EXP_FormatLine_ID, String trxName)
    {
      super (ctx, EXP_FormatLine_ID, trxName);
      /** if (EXP_FormatLine_ID == 0)
        {
			setAD_Column_ID (0);
			setEntityType (null);
// U
			setEXP_FormatLine_ID (0);
			setName (null);
			setType (null);
// E
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_EXP_FormatLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
      */
    @Override
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_EXP_FormatLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	/** Set Spalte.
		@param AD_Column_ID 
		Column in the table
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Spalte.
		@return Column in the table
	  */
	@Override
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class, AD_Reference);
	}

	/** Set Referenz.
		@param AD_Reference_ID 
		System Reference and Validation
	  */
	@Override
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		throw new IllegalArgumentException ("AD_Reference_ID is virtual column");	}

	/** Get Referenz.
		@return System Reference and Validation
	  */
	@Override
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Override_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Override(org.compiere.model.I_AD_Reference AD_Reference_Override)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Override_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Override);
	}

	/** Set Referenz abw..
		@param AD_Reference_Override_ID Referenz abw.	  */
	@Override
	public void setAD_Reference_Override_ID (int AD_Reference_Override_ID)
	{
		if (AD_Reference_Override_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Override_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Override_ID, Integer.valueOf(AD_Reference_Override_ID));
	}

	/** Get Referenz abw..
		@return Referenz abw.	  */
	@Override
	public int getAD_Reference_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datums-Format.
		@param DateFormat 
		Date format used in the imput format
	  */
	@Override
	public void setDateFormat (java.lang.String DateFormat)
	{
		set_Value (COLUMNNAME_DateFormat, DateFormat);
	}

	/** Get Datums-Format.
		@return Date format used in the imput format
	  */
	@Override
	public java.lang.String getDateFormat () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DateFormat);
	}

	/** Set Standardwert-Logik.
		@param DefaultValue 
		Alternativen zur Bestimmung eines Standardwertes, separiert durch Semikolon
	  */
	@Override
	public void setDefaultValue (java.lang.String DefaultValue)
	{
		set_Value (COLUMNNAME_DefaultValue, DefaultValue);
	}

	/** Get Standardwert-Logik.
		@return Alternativen zur Bestimmung eines Standardwertes, separiert durch Semikolon
	  */
	@Override
	public java.lang.String getDefaultValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DefaultValue);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	@Override
	public org.compiere.model.I_EXP_Format getEXP_EmbeddedFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_EXP_EmbeddedFormat_ID, org.compiere.model.I_EXP_Format.class);
	}

	@Override
	public void setEXP_EmbeddedFormat(org.compiere.model.I_EXP_Format EXP_EmbeddedFormat)
	{
		set_ValueFromPO(COLUMNNAME_EXP_EmbeddedFormat_ID, org.compiere.model.I_EXP_Format.class, EXP_EmbeddedFormat);
	}

	/** Set Embedded Format.
		@param EXP_EmbeddedFormat_ID Embedded Format	  */
	@Override
	public void setEXP_EmbeddedFormat_ID (int EXP_EmbeddedFormat_ID)
	{
		if (EXP_EmbeddedFormat_ID < 1) 
			set_Value (COLUMNNAME_EXP_EmbeddedFormat_ID, null);
		else 
			set_Value (COLUMNNAME_EXP_EmbeddedFormat_ID, Integer.valueOf(EXP_EmbeddedFormat_ID));
	}

	/** Get Embedded Format.
		@return Embedded Format	  */
	@Override
	public int getEXP_EmbeddedFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_EmbeddedFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_EXP_Format getEXP_Format() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_EXP_Format_ID, org.compiere.model.I_EXP_Format.class);
	}

	@Override
	public void setEXP_Format(org.compiere.model.I_EXP_Format EXP_Format)
	{
		set_ValueFromPO(COLUMNNAME_EXP_Format_ID, org.compiere.model.I_EXP_Format.class, EXP_Format);
	}

	/** Set Export Format.
		@param EXP_Format_ID Export Format	  */
	@Override
	public void setEXP_Format_ID (int EXP_Format_ID)
	{
		if (EXP_Format_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EXP_Format_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EXP_Format_ID, Integer.valueOf(EXP_Format_ID));
	}

	/** Get Export Format.
		@return Export Format	  */
	@Override
	public int getEXP_Format_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_Format_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Format Line.
		@param EXP_FormatLine_ID Format Line	  */
	@Override
	public void setEXP_FormatLine_ID (int EXP_FormatLine_ID)
	{
		if (EXP_FormatLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EXP_FormatLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EXP_FormatLine_ID, Integer.valueOf(EXP_FormatLine_ID));
	}

	/** Get Format Line.
		@return Format Line	  */
	@Override
	public int getEXP_FormatLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_FormatLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Pflichtangabe.
		@param IsMandatory 
		Data entry is required in this column
	  */
	@Override
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Pflichtangabe.
		@return Data entry is required in this column
	  */
	@Override
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Part Unique Index.
		@param IsPartUniqueIndex Is Part Unique Index	  */
	@Override
	public void setIsPartUniqueIndex (boolean IsPartUniqueIndex)
	{
		set_Value (COLUMNNAME_IsPartUniqueIndex, Boolean.valueOf(IsPartUniqueIndex));
	}

	/** Get Is Part Unique Index.
		@return Is Part Unique Index	  */
	@Override
	public boolean isPartUniqueIndex () 
	{
		Object oo = get_Value(COLUMNNAME_IsPartUniqueIndex);
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

	/** Set Position.
		@param Position Position	  */
	@Override
	public void setPosition (int Position)
	{
		set_Value (COLUMNNAME_Position, Integer.valueOf(Position));
	}

	/** Get Position.
		@return Position	  */
	@Override
	public int getPosition () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Position);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * Type AD_Reference_ID=53241
	 * Reference name: EXP_Line_Type
	 */
	public static final int TYPE_AD_Reference_ID=53241;
	/** XML Element = E */
	public static final String TYPE_XMLElement = "E";
	/** XML Attribute = A */
	public static final String TYPE_XMLAttribute = "A";
	/** Embedded EXP Format = M */
	public static final String TYPE_EmbeddedEXPFormat = "M";
	/** Referenced EXP Format = R */
	public static final String TYPE_ReferencedEXPFormat = "R";
	/** Set Art.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public void setType (java.lang.String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Art.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public java.lang.String getType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}