package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_UI_Element
 *  @author metasfresh (generated) 
 */
public interface I_AD_UI_Element 
{

	String Table_Name = "AD_UI_Element";

//	/** AD_Table_ID=540783 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Feld.
	 * Ein Feld einer Datenbanktabelle
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Field_ID (int AD_Field_ID);

	/**
	 * Get Feld.
	 * Ein Feld einer Datenbanktabelle
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Field_ID();

	@Nullable org.compiere.model.I_AD_Field getAD_Field();

	void setAD_Field(@Nullable org.compiere.model.I_AD_Field AD_Field);

	ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Field> COLUMN_AD_Field_ID = new ModelColumn<>(I_AD_UI_Element.class, "AD_Field_ID", org.compiere.model.I_AD_Field.class);
	String COLUMNNAME_AD_Field_ID = "AD_Field_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Register.
	 * Register auf einem Fenster
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Register.
	 * Register auf einem Fenster
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Tab_ID();

	org.compiere.model.I_AD_Tab getAD_Tab();

	void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab);

	ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Tab> COLUMN_AD_Tab_ID = new ModelColumn<>(I_AD_UI_Element.class, "AD_Tab_ID", org.compiere.model.I_AD_Tab.class);
	String COLUMNNAME_AD_Tab_ID = "AD_Tab_ID";

	/**
	 * Set UI Element.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_UI_Element_ID (int AD_UI_Element_ID);

	/**
	 * Get UI Element.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_UI_Element_ID();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_AD_UI_Element_ID = new ModelColumn<>(I_AD_UI_Element.class, "AD_UI_Element_ID", null);
	String COLUMNNAME_AD_UI_Element_ID = "AD_UI_Element_ID";

	/**
	 * Set UI Element Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_UI_ElementGroup_ID (int AD_UI_ElementGroup_ID);

	/**
	 * Get UI Element Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_UI_ElementGroup_ID();

	@Nullable org.compiere.model.I_AD_UI_ElementGroup getAD_UI_ElementGroup();

	void setAD_UI_ElementGroup(@Nullable org.compiere.model.I_AD_UI_ElementGroup AD_UI_ElementGroup);

	ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_UI_ElementGroup> COLUMN_AD_UI_ElementGroup_ID = new ModelColumn<>(I_AD_UI_Element.class, "AD_UI_ElementGroup_ID", org.compiere.model.I_AD_UI_ElementGroup.class);
	String COLUMNNAME_AD_UI_ElementGroup_ID = "AD_UI_ElementGroup_ID";

	/**
	 * Set Element type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_UI_ElementType (java.lang.String AD_UI_ElementType);

	/**
	 * Get Element type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAD_UI_ElementType();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_AD_UI_ElementType = new ModelColumn<>(I_AD_UI_Element.class, "AD_UI_ElementType", null);
	String COLUMNNAME_AD_UI_ElementType = "AD_UI_ElementType";

	/**
	 * Set Window.
	 * Data entry or display window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Window.
	 * Data entry or display window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getAD_Window_ID();

	@Nullable org.compiere.model.I_AD_Window getAD_Window();

	@Deprecated
	void setAD_Window(@Nullable org.compiere.model.I_AD_Window AD_Window);

	ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new ModelColumn<>(I_AD_UI_Element.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
	String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_Created = new ModelColumn<>(I_AD_UI_Element.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_Description = new ModelColumn<>(I_AD_UI_Element.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_Help = new ModelColumn<>(I_AD_UI_Element.class, "Help", null);
	String COLUMNNAME_Help = "Help";

	/**
	 * Set Inline Tab.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInline_Tab_ID (int Inline_Tab_ID);

	/**
	 * Get Inline Tab.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getInline_Tab_ID();

	@Nullable org.compiere.model.I_AD_Tab getInline_Tab();

	void setInline_Tab(@Nullable org.compiere.model.I_AD_Tab Inline_Tab);

	ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Tab> COLUMN_Inline_Tab_ID = new ModelColumn<>(I_AD_UI_Element.class, "Inline_Tab_ID", org.compiere.model.I_AD_Tab.class);
	String COLUMNNAME_Inline_Tab_ID = "Inline_Tab_ID";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_UI_Element.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Advanced field.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAdvancedField (boolean IsAdvancedField);

	/**
	 * Get Advanced field.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAdvancedField();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_IsAdvancedField = new ModelColumn<>(I_AD_UI_Element.class, "IsAdvancedField", null);
	String COLUMNNAME_IsAdvancedField = "IsAdvancedField";

	/**
	 * Set Allow filtering.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowFiltering (boolean IsAllowFiltering);

	/**
	 * Get Allow filtering.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowFiltering();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_IsAllowFiltering = new ModelColumn<>(I_AD_UI_Element.class, "IsAllowFiltering", null);
	String COLUMNNAME_IsAllowFiltering = "IsAllowFiltering";

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDisplayed (boolean IsDisplayed);

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDisplayed();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_IsDisplayed = new ModelColumn<>(I_AD_UI_Element.class, "IsDisplayed", null);
	String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/**
	 * Set Displayed in Side List.
	 * Determines, if this field is displayed in Side list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDisplayed_SideList (boolean IsDisplayed_SideList);

	/**
	 * Get Displayed in Side List.
	 * Determines, if this field is displayed in Side list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDisplayed_SideList();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_IsDisplayed_SideList = new ModelColumn<>(I_AD_UI_Element.class, "IsDisplayed_SideList", null);
	String COLUMNNAME_IsDisplayed_SideList = "IsDisplayed_SideList";

	/**
	 * Set Displayed in Grid.
	 * Determines, if this field is displayed in grid mode
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDisplayedGrid (boolean IsDisplayedGrid);

	/**
	 * Get Displayed in Grid.
	 * Determines, if this field is displayed in grid mode
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDisplayedGrid();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_IsDisplayedGrid = new ModelColumn<>(I_AD_UI_Element.class, "IsDisplayedGrid", null);
	String COLUMNNAME_IsDisplayedGrid = "IsDisplayedGrid";

	/**
	 * Set Multi Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMultiLine (boolean IsMultiLine);

	/**
	 * Get Multi Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMultiLine();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_IsMultiLine = new ModelColumn<>(I_AD_UI_Element.class, "IsMultiLine", null);
	String COLUMNNAME_IsMultiLine = "IsMultiLine";

	/**
	 * Set Labels selector field.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLabels_Selector_Field_ID (int Labels_Selector_Field_ID);

	/**
	 * Get Labels selector field.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLabels_Selector_Field_ID();

	@Nullable org.compiere.model.I_AD_Field getLabels_Selector_Field();

	void setLabels_Selector_Field(@Nullable org.compiere.model.I_AD_Field Labels_Selector_Field);

	ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Field> COLUMN_Labels_Selector_Field_ID = new ModelColumn<>(I_AD_UI_Element.class, "Labels_Selector_Field_ID", org.compiere.model.I_AD_Field.class);
	String COLUMNNAME_Labels_Selector_Field_ID = "Labels_Selector_Field_ID";

	/**
	 * Set Labels content tab.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLabels_Tab_ID (int Labels_Tab_ID);

	/**
	 * Get Labels content tab.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLabels_Tab_ID();

	@Nullable org.compiere.model.I_AD_Tab getLabels_Tab();

	void setLabels_Tab(@Nullable org.compiere.model.I_AD_Tab Labels_Tab);

	ModelColumn<I_AD_UI_Element, org.compiere.model.I_AD_Tab> COLUMN_Labels_Tab_ID = new ModelColumn<>(I_AD_UI_Element.class, "Labels_Tab_ID", org.compiere.model.I_AD_Tab.class);
	String COLUMNNAME_Labels_Tab_ID = "Labels_Tab_ID";

	/**
	 * Set Media Types.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMediaTypes (@Nullable java.lang.String MediaTypes);

	/**
	 * Get Media Types.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMediaTypes();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_MediaTypes = new ModelColumn<>(I_AD_UI_Element.class, "MediaTypes", null);
	String COLUMNNAME_MediaTypes = "MediaTypes";

	/**
	 * Set Lines Count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMultiLine_LinesCount (int MultiLine_LinesCount);

	/**
	 * Get Lines Count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMultiLine_LinesCount();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_MultiLine_LinesCount = new ModelColumn<>(I_AD_UI_Element.class, "MultiLine_LinesCount", null);
	String COLUMNNAME_MultiLine_LinesCount = "MultiLine_LinesCount";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_Name = new ModelColumn<>(I_AD_UI_Element.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_SeqNo = new ModelColumn<>(I_AD_UI_Element.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Reihenfolge (Side List).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo_SideList (int SeqNo_SideList);

	/**
	 * Get Reihenfolge (Side List).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo_SideList();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_SeqNo_SideList = new ModelColumn<>(I_AD_UI_Element.class, "SeqNo_SideList", null);
	String COLUMNNAME_SeqNo_SideList = "SeqNo_SideList";

	/**
	 * Set Reihenfolge (grid).
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNoGrid (int SeqNoGrid);

	/**
	 * Get Reihenfolge (grid).
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNoGrid();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_SeqNoGrid = new ModelColumn<>(I_AD_UI_Element.class, "SeqNoGrid", null);
	String COLUMNNAME_SeqNoGrid = "SeqNoGrid";

	/**
	 * Set UI Style.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUIStyle (@Nullable java.lang.String UIStyle);

	/**
	 * Get UI Style.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUIStyle();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_UIStyle = new ModelColumn<>(I_AD_UI_Element.class, "UIStyle", null);
	String COLUMNNAME_UIStyle = "UIStyle";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_Updated = new ModelColumn<>(I_AD_UI_Element.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Widget size.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWidgetSize (@Nullable java.lang.String WidgetSize);

	/**
	 * Get Widget size.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWidgetSize();

	ModelColumn<I_AD_UI_Element, Object> COLUMN_WidgetSize = new ModelColumn<>(I_AD_UI_Element.class, "WidgetSize", null);
	String COLUMNNAME_WidgetSize = "WidgetSize";
}
