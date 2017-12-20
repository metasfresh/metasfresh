/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Element
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Element extends org.compiere.model.PO implements I_AD_Element, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1438104714L;

    /** Standard Constructor */
    public X_AD_Element (Properties ctx, int AD_Element_ID, String trxName)
    {
      super (ctx, AD_Element_ID, trxName);
      /** if (AD_Element_ID == 0)
        {
			setAD_Element_ID (0);
			setEntityType (null); // U
			setName (null);
			setPrintName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Element (Properties ctx, ResultSet rs, String trxName)
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

	/** Set System-Element.
		@param AD_Element_ID 
		System Element enables the central maintenance of column description and help.
	  */
	@Override
	public void setAD_Element_ID (int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
	}

	/** Get System-Element.
		@return System Element enables the central maintenance of column description and help.
	  */
	@Override
	public int getAD_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Spaltenname.
		@param ColumnName 
		Name of the column in the database
	  */
	@Override
	public void setColumnName (java.lang.String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get Spaltenname.
		@return Name of the column in the database
	  */
	@Override
	public java.lang.String getColumnName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ColumnName);
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

	/** Set PO Description.
		@param PO_Description 
		Description in PO Screens
	  */
	@Override
	public void setPO_Description (java.lang.String PO_Description)
	{
		set_Value (COLUMNNAME_PO_Description, PO_Description);
	}

	/** Get PO Description.
		@return Description in PO Screens
	  */
	@Override
	public java.lang.String getPO_Description () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PO_Description);
	}

	/** Set PO Help.
		@param PO_Help 
		Help for PO Screens
	  */
	@Override
	public void setPO_Help (java.lang.String PO_Help)
	{
		set_Value (COLUMNNAME_PO_Help, PO_Help);
	}

	/** Get PO Help.
		@return Help for PO Screens
	  */
	@Override
	public java.lang.String getPO_Help () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PO_Help);
	}

	/** Set PO Name.
		@param PO_Name 
		Name on PO Screens
	  */
	@Override
	public void setPO_Name (java.lang.String PO_Name)
	{
		set_Value (COLUMNNAME_PO_Name, PO_Name);
	}

	/** Get PO Name.
		@return Name on PO Screens
	  */
	@Override
	public java.lang.String getPO_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PO_Name);
	}

	/** Set PO Print name.
		@param PO_PrintName 
		Print name on PO Screens/Reports
	  */
	@Override
	public void setPO_PrintName (java.lang.String PO_PrintName)
	{
		set_Value (COLUMNNAME_PO_PrintName, PO_PrintName);
	}

	/** Get PO Print name.
		@return Print name on PO Screens/Reports
	  */
	@Override
	public java.lang.String getPO_PrintName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PO_PrintName);
	}

	/** Set Drucktext.
		@param PrintName 
		The label text to be printed on a document or correspondence.
	  */
	@Override
	public void setPrintName (java.lang.String PrintName)
	{
		set_Value (COLUMNNAME_PrintName, PrintName);
	}

	/** Get Drucktext.
		@return The label text to be printed on a document or correspondence.
	  */
	@Override
	public java.lang.String getPrintName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintName);
	}

	/** 
	 * WidgetSize AD_Reference_ID=540724
	 * Reference name: WidgetSize_WEBUI
	 */
	public static final int WIDGETSIZE_AD_Reference_ID=540724;
	/** Small = S */
	public static final String WIDGETSIZE_Small = "S";
	/** Medium = M */
	public static final String WIDGETSIZE_Medium = "M";
	/** Large = L */
	public static final String WIDGETSIZE_Large = "L";
	/** Set Widget size.
		@param WidgetSize Widget size	  */
	@Override
	public void setWidgetSize (java.lang.String WidgetSize)
	{

		set_Value (COLUMNNAME_WidgetSize, WidgetSize);
	}

	/** Get Widget size.
		@return Widget size	  */
	@Override
	public java.lang.String getWidgetSize () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WidgetSize);
	}
}