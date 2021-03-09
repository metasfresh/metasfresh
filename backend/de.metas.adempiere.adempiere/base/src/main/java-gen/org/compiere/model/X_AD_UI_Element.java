// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_UI_Element
 *  @author metasfresh (generated) 
 */
public class X_AD_UI_Element extends org.compiere.model.PO implements I_AD_UI_Element, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 754699779L;

    /** Standard Constructor */
    public X_AD_UI_Element (final Properties ctx, final int AD_UI_Element_ID, @Nullable final String trxName)
    {
      super (ctx, AD_UI_Element_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_UI_Element (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Field getAD_Field()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Field_ID, org.compiere.model.I_AD_Field.class);
	}

	@Override
	public void setAD_Field(final org.compiere.model.I_AD_Field AD_Field)
	{
		set_ValueFromPO(COLUMNNAME_AD_Field_ID, org.compiere.model.I_AD_Field.class, AD_Field);
	}

	@Override
	public void setAD_Field_ID (final int AD_Field_ID)
	{
		if (AD_Field_ID < 1) 
			set_Value (COLUMNNAME_AD_Field_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Field_ID, AD_Field_ID);
	}

	@Override
	public int getAD_Field_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Field_ID);
	}

	@Override
	public org.compiere.model.I_AD_Tab getAD_Tab()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setAD_Tab(final org.compiere.model.I_AD_Tab AD_Tab)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class, AD_Tab);
	}

	@Override
	public void setAD_Tab_ID (final int AD_Tab_ID)
	{
		if (AD_Tab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tab_ID, AD_Tab_ID);
	}

	@Override
	public int getAD_Tab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Tab_ID);
	}

	@Override
	public void setAD_UI_Element_ID (final int AD_UI_Element_ID)
	{
		if (AD_UI_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UI_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UI_Element_ID, AD_UI_Element_ID);
	}

	@Override
	public int getAD_UI_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_UI_Element_ID);
	}

	@Override
	public org.compiere.model.I_AD_UI_ElementGroup getAD_UI_ElementGroup()
	{
		return get_ValueAsPO(COLUMNNAME_AD_UI_ElementGroup_ID, org.compiere.model.I_AD_UI_ElementGroup.class);
	}

	@Override
	public void setAD_UI_ElementGroup(final org.compiere.model.I_AD_UI_ElementGroup AD_UI_ElementGroup)
	{
		set_ValueFromPO(COLUMNNAME_AD_UI_ElementGroup_ID, org.compiere.model.I_AD_UI_ElementGroup.class, AD_UI_ElementGroup);
	}

	@Override
	public void setAD_UI_ElementGroup_ID (final int AD_UI_ElementGroup_ID)
	{
		if (AD_UI_ElementGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UI_ElementGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UI_ElementGroup_ID, AD_UI_ElementGroup_ID);
	}

	@Override
	public int getAD_UI_ElementGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_UI_ElementGroup_ID);
	}

	/** 
	 * AD_UI_ElementType AD_Reference_ID=540736
	 * Reference name: AD_UI_ElementType
	 */
	public static final int AD_UI_ELEMENTTYPE_AD_Reference_ID=540736;
	/** Field = F */
	public static final String AD_UI_ELEMENTTYPE_Field = "F";
	/** Labels = L */
	public static final String AD_UI_ELEMENTTYPE_Labels = "L";
	/** InlineTab = T */
	public static final String AD_UI_ELEMENTTYPE_InlineTab = "T";
	@Override
	public void setAD_UI_ElementType (final java.lang.String AD_UI_ElementType)
	{
		set_Value (COLUMNNAME_AD_UI_ElementType, AD_UI_ElementType);
	}

	@Override
	public java.lang.String getAD_UI_ElementType() 
	{
		return get_ValueAsString(COLUMNNAME_AD_UI_ElementType);
	}

	@Override
	public org.compiere.model.I_AD_Window getAD_Window()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setAD_Window(final org.compiere.model.I_AD_Window AD_Window)
	{
		set_ValueFromPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class, AD_Window);
	}

	@Override
	public void setAD_Window_ID (final int AD_Window_ID)
	{
		throw new IllegalArgumentException ("AD_Window_ID is virtual column");	}

	@Override
	public int getAD_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Window_ID);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setHelp (final java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public org.compiere.model.I_AD_Tab getInline_Tab()
	{
		return get_ValueAsPO(COLUMNNAME_Inline_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setInline_Tab(final org.compiere.model.I_AD_Tab Inline_Tab)
	{
		set_ValueFromPO(COLUMNNAME_Inline_Tab_ID, org.compiere.model.I_AD_Tab.class, Inline_Tab);
	}

	@Override
	public void setInline_Tab_ID (final int Inline_Tab_ID)
	{
		if (Inline_Tab_ID < 1) 
			set_Value (COLUMNNAME_Inline_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_Inline_Tab_ID, Inline_Tab_ID);
	}

	@Override
	public int getInline_Tab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Inline_Tab_ID);
	}

	@Override
	public void setIsAdvancedField (final boolean IsAdvancedField)
	{
		set_Value (COLUMNNAME_IsAdvancedField, IsAdvancedField);
	}

	@Override
	public boolean isAdvancedField() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAdvancedField);
	}

	@Override
	public void setIsAllowFiltering (final boolean IsAllowFiltering)
	{
		set_Value (COLUMNNAME_IsAllowFiltering, IsAllowFiltering);
	}

	@Override
	public boolean isAllowFiltering() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowFiltering);
	}

	@Override
	public void setIsDisplayed (final boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, IsDisplayed);
	}

	@Override
	public boolean isDisplayed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayed);
	}

	@Override
	public void setIsDisplayed_SideList (final boolean IsDisplayed_SideList)
	{
		set_Value (COLUMNNAME_IsDisplayed_SideList, IsDisplayed_SideList);
	}

	@Override
	public boolean isDisplayed_SideList() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayed_SideList);
	}

	@Override
	public void setIsDisplayedGrid (final boolean IsDisplayedGrid)
	{
		set_Value (COLUMNNAME_IsDisplayedGrid, IsDisplayedGrid);
	}

	@Override
	public boolean isDisplayedGrid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayedGrid);
	}

	@Override
	public void setIsMultiLine (final boolean IsMultiLine)
	{
		set_Value (COLUMNNAME_IsMultiLine, IsMultiLine);
	}

	@Override
	public boolean isMultiLine() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMultiLine);
	}

	@Override
	public org.compiere.model.I_AD_Field getLabels_Selector_Field()
	{
		return get_ValueAsPO(COLUMNNAME_Labels_Selector_Field_ID, org.compiere.model.I_AD_Field.class);
	}

	@Override
	public void setLabels_Selector_Field(final org.compiere.model.I_AD_Field Labels_Selector_Field)
	{
		set_ValueFromPO(COLUMNNAME_Labels_Selector_Field_ID, org.compiere.model.I_AD_Field.class, Labels_Selector_Field);
	}

	@Override
	public void setLabels_Selector_Field_ID (final int Labels_Selector_Field_ID)
	{
		if (Labels_Selector_Field_ID < 1) 
			set_Value (COLUMNNAME_Labels_Selector_Field_ID, null);
		else 
			set_Value (COLUMNNAME_Labels_Selector_Field_ID, Labels_Selector_Field_ID);
	}

	@Override
	public int getLabels_Selector_Field_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Labels_Selector_Field_ID);
	}

	@Override
	public org.compiere.model.I_AD_Tab getLabels_Tab()
	{
		return get_ValueAsPO(COLUMNNAME_Labels_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setLabels_Tab(final org.compiere.model.I_AD_Tab Labels_Tab)
	{
		set_ValueFromPO(COLUMNNAME_Labels_Tab_ID, org.compiere.model.I_AD_Tab.class, Labels_Tab);
	}

	@Override
	public void setLabels_Tab_ID (final int Labels_Tab_ID)
	{
		if (Labels_Tab_ID < 1) 
			set_Value (COLUMNNAME_Labels_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_Labels_Tab_ID, Labels_Tab_ID);
	}

	@Override
	public int getLabels_Tab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Labels_Tab_ID);
	}

	@Override
	public void setMediaTypes (final java.lang.String MediaTypes)
	{
		set_Value (COLUMNNAME_MediaTypes, MediaTypes);
	}

	@Override
	public java.lang.String getMediaTypes() 
	{
		return get_ValueAsString(COLUMNNAME_MediaTypes);
	}

	@Override
	public void setMultiLine_LinesCount (final int MultiLine_LinesCount)
	{
		set_Value (COLUMNNAME_MultiLine_LinesCount, MultiLine_LinesCount);
	}

	@Override
	public int getMultiLine_LinesCount() 
	{
		return get_ValueAsInt(COLUMNNAME_MultiLine_LinesCount);
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
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setSeqNo_SideList (final int SeqNo_SideList)
	{
		set_Value (COLUMNNAME_SeqNo_SideList, SeqNo_SideList);
	}

	@Override
	public int getSeqNo_SideList() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo_SideList);
	}

	@Override
	public void setSeqNoGrid (final int SeqNoGrid)
	{
		set_Value (COLUMNNAME_SeqNoGrid, SeqNoGrid);
	}

	@Override
	public int getSeqNoGrid() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNoGrid);
	}

	@Override
	public void setUIStyle (final java.lang.String UIStyle)
	{
		set_Value (COLUMNNAME_UIStyle, UIStyle);
	}

	@Override
	public java.lang.String getUIStyle() 
	{
		return get_ValueAsString(COLUMNNAME_UIStyle);
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
	/** ExtraLarge = XL */
	public static final String WIDGETSIZE_ExtraLarge = "XL";
	/** XXL = XXL */
	public static final String WIDGETSIZE_XXL = "XXL";
	@Override
	public void setWidgetSize (final java.lang.String WidgetSize)
	{
		set_Value (COLUMNNAME_WidgetSize, WidgetSize);
	}

	@Override
	public java.lang.String getWidgetSize() 
	{
		return get_ValueAsString(COLUMNNAME_WidgetSize);
	}
}