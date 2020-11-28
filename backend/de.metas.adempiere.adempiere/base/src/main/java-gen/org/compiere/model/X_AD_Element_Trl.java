/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Element_Trl
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Element_Trl extends org.compiere.model.PO implements I_AD_Element_Trl, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1112012905L;

    /** Standard Constructor */
    public X_AD_Element_Trl (Properties ctx, int AD_Element_Trl_ID, String trxName)
    {
      super (ctx, AD_Element_Trl_ID, trxName);
      /** if (AD_Element_Trl_ID == 0)
        {
			setAD_Element_ID (0);
			setAD_Language (null);
			setIsTranslated (false);
			setIsUseCustomization (false); // N
			setName (null);
			setPrintName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Element_Trl (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Element getAD_Element() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
	public void setAD_Element(org.compiere.model.I_AD_Element AD_Element)
	{
		set_ValueFromPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class, AD_Element);
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

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	/** Set Sprache.
		@param AD_Language 
		Language for this entity
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{

		set_ValueNoCheck (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Language for this entity
	  */
	@Override
	public java.lang.String getAD_Language () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
	}

	/** Set Spaltenname.
		@param ColumnName 
		Name der Spalte in der Datenbank
	  */
	@Override
	public void setColumnName (java.lang.String ColumnName)
	{
		throw new IllegalArgumentException ("ColumnName is virtual column");	}

	/** Get Spaltenname.
		@return Name der Spalte in der Datenbank
	  */
	@Override
	public java.lang.String getColumnName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ColumnName);
	}

	/** Set Speicherwarnung.
		@param CommitWarning 
		Warnung, die beim Speichern angezeigt wird
	  */
	@Override
	public void setCommitWarning (java.lang.String CommitWarning)
	{
		set_Value (COLUMNNAME_CommitWarning, CommitWarning);
	}

	/** Get Speicherwarnung.
		@return Warnung, die beim Speichern angezeigt wird
	  */
	@Override
	public java.lang.String getCommitWarning () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CommitWarning);
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

	/** Set Beschreibung Anpassung.
		@param Description_Customized Beschreibung Anpassung	  */
	@Override
	public void setDescription_Customized (java.lang.String Description_Customized)
	{
		set_Value (COLUMNNAME_Description_Customized, Description_Customized);
	}

	/** Get Beschreibung Anpassung.
		@return Beschreibung Anpassung	  */
	@Override
	public java.lang.String getDescription_Customized () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description_Customized);
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

	/** Set Kommentar/Hilfe Anpassung.
		@param Help_Customized Kommentar/Hilfe Anpassung	  */
	@Override
	public void setHelp_Customized (java.lang.String Help_Customized)
	{
		set_Value (COLUMNNAME_Help_Customized, Help_Customized);
	}

	/** Get Kommentar/Hilfe Anpassung.
		@return Kommentar/Hilfe Anpassung	  */
	@Override
	public java.lang.String getHelp_Customized () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help_Customized);
	}

	/** Set Übersetzt.
		@param IsTranslated 
		This column is translated
	  */
	@Override
	public void setIsTranslated (boolean IsTranslated)
	{
		set_Value (COLUMNNAME_IsTranslated, Boolean.valueOf(IsTranslated));
	}

	/** Get Übersetzt.
		@return This column is translated
	  */
	@Override
	public boolean isTranslated () 
	{
		Object oo = get_Value(COLUMNNAME_IsTranslated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Individuelle Anpassungen verwenden.
		@param IsUseCustomization Individuelle Anpassungen verwenden	  */
	@Override
	public void setIsUseCustomization (boolean IsUseCustomization)
	{
		set_Value (COLUMNNAME_IsUseCustomization, Boolean.valueOf(IsUseCustomization));
	}

	/** Get Individuelle Anpassungen verwenden.
		@return Individuelle Anpassungen verwenden	  */
	@Override
	public boolean isUseCustomization () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseCustomization);
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

	/** Set Name Anpassung.
		@param Name_Customized Name Anpassung	  */
	@Override
	public void setName_Customized (java.lang.String Name_Customized)
	{
		set_Value (COLUMNNAME_Name_Customized, Name_Customized);
	}

	/** Get Name Anpassung.
		@return Name Anpassung	  */
	@Override
	public java.lang.String getName_Customized () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name_Customized);
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

	/** Set Browse name.
		@param WEBUI_NameBrowse Browse name	  */
	@Override
	public void setWEBUI_NameBrowse (java.lang.String WEBUI_NameBrowse)
	{
		set_Value (COLUMNNAME_WEBUI_NameBrowse, WEBUI_NameBrowse);
	}

	/** Get Browse name.
		@return Browse name	  */
	@Override
	public java.lang.String getWEBUI_NameBrowse () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WEBUI_NameBrowse);
	}

	/** Set New record name.
		@param WEBUI_NameNew New record name	  */
	@Override
	public void setWEBUI_NameNew (java.lang.String WEBUI_NameNew)
	{
		set_Value (COLUMNNAME_WEBUI_NameNew, WEBUI_NameNew);
	}

	/** Get New record name.
		@return New record name	  */
	@Override
	public java.lang.String getWEBUI_NameNew () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WEBUI_NameNew);
	}

	/** Set New record name (breadcrumb).
		@param WEBUI_NameNewBreadcrumb New record name (breadcrumb)	  */
	@Override
	public void setWEBUI_NameNewBreadcrumb (java.lang.String WEBUI_NameNewBreadcrumb)
	{
		set_Value (COLUMNNAME_WEBUI_NameNewBreadcrumb, WEBUI_NameNewBreadcrumb);
	}

	/** Get New record name (breadcrumb).
		@return New record name (breadcrumb)	  */
	@Override
	public java.lang.String getWEBUI_NameNewBreadcrumb () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WEBUI_NameNewBreadcrumb);
	}
}