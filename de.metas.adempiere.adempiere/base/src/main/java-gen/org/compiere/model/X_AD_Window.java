/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Window
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Window extends org.compiere.model.PO implements I_AD_Window, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -423169739L;

    /** Standard Constructor */
    public X_AD_Window (Properties ctx, int AD_Window_ID, String trxName)
    {
      super (ctx, AD_Window_ID, trxName);
      /** if (AD_Window_ID == 0)
        {
			setAD_Element_ID (0);
			setAD_Window_ID (0);
			setEntityType (null); // U
			setIsBetaFunctionality (false);
			setIsDefault (false);
			setIsEnableRemoteCacheInvalidation (false); // N
			setIsSOTrx (true); // Y
			setName (null);
			setWindowType (null); // M
        } */
    }

    /** Load Constructor */
    public X_AD_Window (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Color getAD_Color() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Color_ID, org.compiere.model.I_AD_Color.class);
	}

	@Override
	public void setAD_Color(org.compiere.model.I_AD_Color AD_Color)
	{
		set_ValueFromPO(COLUMNNAME_AD_Color_ID, org.compiere.model.I_AD_Color.class, AD_Color);
	}

	/** Set System-Farbe.
		@param AD_Color_ID 
		Color for backgrounds or indicators
	  */
	@Override
	public void setAD_Color_ID (int AD_Color_ID)
	{
		if (AD_Color_ID < 1) 
			set_Value (COLUMNNAME_AD_Color_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Color_ID, Integer.valueOf(AD_Color_ID));
	}

	/** Get System-Farbe.
		@return Color for backgrounds or indicators
	  */
	@Override
	public int getAD_Color_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Color_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	  */
	@Override
	public void setAD_Element_ID (int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
	}

	/** Get System-Element.
		@return Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	  */
	@Override
	public int getAD_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Image getAD_Image() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setAD_Image(org.compiere.model.I_AD_Image AD_Image)
	{
		set_ValueFromPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class, AD_Image);
	}

	/** Set Bild.
		@param AD_Image_ID 
		Image or Icon
	  */
	@Override
	public void setAD_Image_ID (int AD_Image_ID)
	{
		if (AD_Image_ID < 1) 
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Image_ID, Integer.valueOf(AD_Image_ID));
	}

	/** Get Bild.
		@return Image or Icon
	  */
	@Override
	public int getAD_Image_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Image_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fenster.
		@param AD_Window_ID 
		Data entry or display window
	  */
	@Override
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Fenster.
		@return Data entry or display window
	  */
	@Override
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Interner Name.
		@param InternalName 
		Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public java.lang.String getInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

	/** Set Beta-Funktionalität.
		@param IsBetaFunctionality 
		This functionality is considered Beta
	  */
	@Override
	public void setIsBetaFunctionality (boolean IsBetaFunctionality)
	{
		set_Value (COLUMNNAME_IsBetaFunctionality, Boolean.valueOf(IsBetaFunctionality));
	}

	/** Get Beta-Funktionalität.
		@return This functionality is considered Beta
	  */
	@Override
	public boolean isBetaFunctionality () 
	{
		Object oo = get_Value(COLUMNNAME_IsBetaFunctionality);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Enable remote cache invalidation.
		@param IsEnableRemoteCacheInvalidation Enable remote cache invalidation	  */
	@Override
	public void setIsEnableRemoteCacheInvalidation (boolean IsEnableRemoteCacheInvalidation)
	{
		set_Value (COLUMNNAME_IsEnableRemoteCacheInvalidation, Boolean.valueOf(IsEnableRemoteCacheInvalidation));
	}

	/** Get Enable remote cache invalidation.
		@return Enable remote cache invalidation	  */
	@Override
	public boolean isEnableRemoteCacheInvalidation () 
	{
		Object oo = get_Value(COLUMNNAME_IsEnableRemoteCacheInvalidation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verkaufs-Transaktion.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Verkaufs-Transaktion.
		@return This is a Sales Transaction
	  */
	@Override
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
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

	/** 
	 * WindowType AD_Reference_ID=108
	 * Reference name: AD_Window Types
	 */
	public static final int WINDOWTYPE_AD_Reference_ID=108;
	/** Single Record = S */
	public static final String WINDOWTYPE_SingleRecord = "S";
	/** Maintain = M */
	public static final String WINDOWTYPE_Maintain = "M";
	/** Transaktion = T */
	public static final String WINDOWTYPE_Transaktion = "T";
	/** Query Only = Q */
	public static final String WINDOWTYPE_QueryOnly = "Q";
	/** Set WindowType.
		@param WindowType 
		Type or classification of a Window
	  */
	@Override
	public void setWindowType (java.lang.String WindowType)
	{

		set_Value (COLUMNNAME_WindowType, WindowType);
	}

	/** Get WindowType.
		@return Type or classification of a Window
	  */
	@Override
	public java.lang.String getWindowType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WindowType);
	}

	/** Set Fensterhöhe.
		@param WinHeight Fensterhöhe	  */
	@Override
	public void setWinHeight (int WinHeight)
	{
		set_Value (COLUMNNAME_WinHeight, Integer.valueOf(WinHeight));
	}

	/** Get Fensterhöhe.
		@return Fensterhöhe	  */
	@Override
	public int getWinHeight () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WinHeight);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fensterbreite.
		@param WinWidth Fensterbreite	  */
	@Override
	public void setWinWidth (int WinWidth)
	{
		set_Value (COLUMNNAME_WinWidth, Integer.valueOf(WinWidth));
	}

	/** Get Fensterbreite.
		@return Fensterbreite	  */
	@Override
	public int getWinWidth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WinWidth);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}