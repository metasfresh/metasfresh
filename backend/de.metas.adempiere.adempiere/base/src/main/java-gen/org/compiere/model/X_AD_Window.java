// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Window
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Window extends org.compiere.model.PO implements I_AD_Window, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1673008926L;

    /** Standard Constructor */
    public X_AD_Window (final Properties ctx, final int AD_Window_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Window_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Window (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Color getAD_Color()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Color_ID, org.compiere.model.I_AD_Color.class);
	}

	@Override
	public void setAD_Color(final org.compiere.model.I_AD_Color AD_Color)
	{
		set_ValueFromPO(COLUMNNAME_AD_Color_ID, org.compiere.model.I_AD_Color.class, AD_Color);
	}

	@Override
	public void setAD_Color_ID (final int AD_Color_ID)
	{
		if (AD_Color_ID < 1) 
			set_Value (COLUMNNAME_AD_Color_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Color_ID, AD_Color_ID);
	}

	@Override
	public int getAD_Color_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Color_ID);
	}

	@Override
	public org.compiere.model.I_AD_Element getAD_Element()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
	public void setAD_Element(final org.compiere.model.I_AD_Element AD_Element)
	{
		set_ValueFromPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class, AD_Element);
	}

	@Override
	public void setAD_Element_ID (final int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, AD_Element_ID);
	}

	@Override
	public int getAD_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Element_ID);
	}

	@Override
	public org.compiere.model.I_AD_Image getAD_Image()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setAD_Image(final org.compiere.model.I_AD_Image AD_Image)
	{
		set_ValueFromPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class, AD_Image);
	}

	@Override
	public void setAD_Image_ID (final int AD_Image_ID)
	{
		if (AD_Image_ID < 1) 
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Image_ID, AD_Image_ID);
	}

	@Override
	public int getAD_Image_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Image_ID);
	}

	@Override
	public void setAD_Window_ID (final int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, AD_Window_ID);
	}

	@Override
	public int getAD_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Window_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setInternalName (final @Nullable java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public void setIsBetaFunctionality (final boolean IsBetaFunctionality)
	{
		set_Value (COLUMNNAME_IsBetaFunctionality, IsBetaFunctionality);
	}

	@Override
	public boolean isBetaFunctionality() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBetaFunctionality);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsEnableRemoteCacheInvalidation (final boolean IsEnableRemoteCacheInvalidation)
	{
		set_Value (COLUMNNAME_IsEnableRemoteCacheInvalidation, IsEnableRemoteCacheInvalidation);
	}

	@Override
	public boolean isEnableRemoteCacheInvalidation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEnableRemoteCacheInvalidation);
	}

	@Override
	public void setIsExcludeFromZoomTargets (final boolean IsExcludeFromZoomTargets)
	{
		set_Value (COLUMNNAME_IsExcludeFromZoomTargets, IsExcludeFromZoomTargets);
	}

	@Override
	public boolean isExcludeFromZoomTargets() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludeFromZoomTargets);
	}

	@Override
	public void setIsOverrideInMenu (final boolean IsOverrideInMenu)
	{
		set_Value (COLUMNNAME_IsOverrideInMenu, IsOverrideInMenu);
	}

	@Override
	public boolean isOverrideInMenu() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOverrideInMenu);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public org.compiere.model.I_AD_Window getOverrides_Window()
	{
		return get_ValueAsPO(COLUMNNAME_Overrides_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setOverrides_Window(final org.compiere.model.I_AD_Window Overrides_Window)
	{
		set_ValueFromPO(COLUMNNAME_Overrides_Window_ID, org.compiere.model.I_AD_Window.class, Overrides_Window);
	}

	@Override
	public void setOverrides_Window_ID (final int Overrides_Window_ID)
	{
		if (Overrides_Window_ID < 1) 
			set_Value (COLUMNNAME_Overrides_Window_ID, null);
		else 
			set_Value (COLUMNNAME_Overrides_Window_ID, Overrides_Window_ID);
	}

	@Override
	public int getOverrides_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Overrides_Window_ID);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
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
	@Override
	public void setWindowType (final java.lang.String WindowType)
	{
		set_Value (COLUMNNAME_WindowType, WindowType);
	}

	@Override
	public java.lang.String getWindowType() 
	{
		return get_ValueAsString(COLUMNNAME_WindowType);
	}

	@Override
	public void setWinHeight (final int WinHeight)
	{
		set_Value (COLUMNNAME_WinHeight, WinHeight);
	}

	@Override
	public int getWinHeight() 
	{
		return get_ValueAsInt(COLUMNNAME_WinHeight);
	}

	@Override
	public void setWinWidth (final int WinWidth)
	{
		set_Value (COLUMNNAME_WinWidth, WinWidth);
	}

	@Override
	public int getWinWidth() 
	{
		return get_ValueAsInt(COLUMNNAME_WinWidth);
	}

	@Override
	public void setZoomIntoPriority (final int ZoomIntoPriority)
	{
		set_Value (COLUMNNAME_ZoomIntoPriority, ZoomIntoPriority);
	}

	@Override
	public int getZoomIntoPriority() 
	{
		return get_ValueAsInt(COLUMNNAME_ZoomIntoPriority);
	}
}