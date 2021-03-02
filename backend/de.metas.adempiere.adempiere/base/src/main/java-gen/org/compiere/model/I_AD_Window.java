package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Window
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Window 
{

	String Table_Name = "AD_Window";

//	/** AD_Table_ID=105 */
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
	 * Set System-Farbe.
	 * Color for backgrounds or indicators
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Color_ID (int AD_Color_ID);

	/**
	 * Get System-Farbe.
	 * Color for backgrounds or indicators
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Color_ID();

	@Nullable org.compiere.model.I_AD_Color getAD_Color();

	void setAD_Color(@Nullable org.compiere.model.I_AD_Color AD_Color);

	ModelColumn<I_AD_Window, org.compiere.model.I_AD_Color> COLUMN_AD_Color_ID = new ModelColumn<>(I_AD_Window.class, "AD_Color_ID", org.compiere.model.I_AD_Color.class);
	String COLUMNNAME_AD_Color_ID = "AD_Color_ID";

	/**
	 * Set System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Element_ID();

	org.compiere.model.I_AD_Element getAD_Element();

	void setAD_Element(org.compiere.model.I_AD_Element AD_Element);

	ModelColumn<I_AD_Window, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new ModelColumn<>(I_AD_Window.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
	String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

	/**
	 * Set Bild.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Image_ID (int AD_Image_ID);

	/**
	 * Get Bild.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Image_ID();

	@Nullable org.compiere.model.I_AD_Image getAD_Image();

	void setAD_Image(@Nullable org.compiere.model.I_AD_Image AD_Image);

	ModelColumn<I_AD_Window, org.compiere.model.I_AD_Image> COLUMN_AD_Image_ID = new ModelColumn<>(I_AD_Window.class, "AD_Image_ID", org.compiere.model.I_AD_Image.class);
	String COLUMNNAME_AD_Image_ID = "AD_Image_ID";

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
	 * Set Window.
	 * Data entry or display window
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Window.
	 * Data entry or display window
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Window_ID();

	ModelColumn<I_AD_Window, Object> COLUMN_AD_Window_ID = new ModelColumn<>(I_AD_Window.class, "AD_Window_ID", null);
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

	ModelColumn<I_AD_Window, Object> COLUMN_Created = new ModelColumn<>(I_AD_Window.class, "Created", null);
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
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Window, Object> COLUMN_Description = new ModelColumn<>(I_AD_Window.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_AD_Window, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Window.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_AD_Window, Object> COLUMN_Help = new ModelColumn<>(I_AD_Window.class, "Help", null);
	String COLUMNNAME_Help = "Help";

	/**
	 * Set Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInternalName (@Nullable java.lang.String InternalName);

	/**
	 * Get Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInternalName();

	ModelColumn<I_AD_Window, Object> COLUMN_InternalName = new ModelColumn<>(I_AD_Window.class, "InternalName", null);
	String COLUMNNAME_InternalName = "InternalName";

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

	ModelColumn<I_AD_Window, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Window.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Beta-Funktionalität.
	 * This functionality is considered Beta
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBetaFunctionality (boolean IsBetaFunctionality);

	/**
	 * Get Beta-Funktionalität.
	 * This functionality is considered Beta
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBetaFunctionality();

	ModelColumn<I_AD_Window, Object> COLUMN_IsBetaFunctionality = new ModelColumn<>(I_AD_Window.class, "IsBetaFunctionality", null);
	String COLUMNNAME_IsBetaFunctionality = "IsBetaFunctionality";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_AD_Window, Object> COLUMN_IsDefault = new ModelColumn<>(I_AD_Window.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Enable remote cache invalidation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEnableRemoteCacheInvalidation (boolean IsEnableRemoteCacheInvalidation);

	/**
	 * Get Enable remote cache invalidation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEnableRemoteCacheInvalidation();

	ModelColumn<I_AD_Window, Object> COLUMN_IsEnableRemoteCacheInvalidation = new ModelColumn<>(I_AD_Window.class, "IsEnableRemoteCacheInvalidation", null);
	String COLUMNNAME_IsEnableRemoteCacheInvalidation = "IsEnableRemoteCacheInvalidation";

	/**
	 * Set Exclude from Zoom Targets.
	 * Exclude from zoom targets / related documents
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExcludeFromZoomTargets (boolean IsExcludeFromZoomTargets);

	/**
	 * Get Exclude from Zoom Targets.
	 * Exclude from zoom targets / related documents
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludeFromZoomTargets();

	ModelColumn<I_AD_Window, Object> COLUMN_IsExcludeFromZoomTargets = new ModelColumn<>(I_AD_Window.class, "IsExcludeFromZoomTargets", null);
	String COLUMNNAME_IsExcludeFromZoomTargets = "IsExcludeFromZoomTargets";

	/**
	 * Set Override In Menu.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOverrideInMenu (boolean IsOverrideInMenu);

	/**
	 * Get Override In Menu.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverrideInMenu();

	ModelColumn<I_AD_Window, Object> COLUMN_IsOverrideInMenu = new ModelColumn<>(I_AD_Window.class, "IsOverrideInMenu", null);
	String COLUMNNAME_IsOverrideInMenu = "IsOverrideInMenu";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_AD_Window, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_AD_Window.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

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

	ModelColumn<I_AD_Window, Object> COLUMN_Name = new ModelColumn<>(I_AD_Window.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Overrides Base Window.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOverrides_Window_ID (int Overrides_Window_ID);

	/**
	 * Get Overrides Base Window.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOverrides_Window_ID();

	@Nullable org.compiere.model.I_AD_Window getOverrides_Window();

	void setOverrides_Window(@Nullable org.compiere.model.I_AD_Window Overrides_Window);

	ModelColumn<I_AD_Window, org.compiere.model.I_AD_Window> COLUMN_Overrides_Window_ID = new ModelColumn<>(I_AD_Window.class, "Overrides_Window_ID", org.compiere.model.I_AD_Window.class);
	String COLUMNNAME_Overrides_Window_ID = "Overrides_Window_ID";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_AD_Window, Object> COLUMN_Processing = new ModelColumn<>(I_AD_Window.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Window, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Window.class, "Updated", null);
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
	 * Set WindowType.
	 * Type or classification of a Window
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWindowType (java.lang.String WindowType);

	/**
	 * Get WindowType.
	 * Type or classification of a Window
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getWindowType();

	ModelColumn<I_AD_Window, Object> COLUMN_WindowType = new ModelColumn<>(I_AD_Window.class, "WindowType", null);
	String COLUMNNAME_WindowType = "WindowType";

	/**
	 * Set Fensterhöhe.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWinHeight (int WinHeight);

	/**
	 * Get Fensterhöhe.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWinHeight();

	ModelColumn<I_AD_Window, Object> COLUMN_WinHeight = new ModelColumn<>(I_AD_Window.class, "WinHeight", null);
	String COLUMNNAME_WinHeight = "WinHeight";

	/**
	 * Set Fensterbreite.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWinWidth (int WinWidth);

	/**
	 * Get Fensterbreite.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWinWidth();

	ModelColumn<I_AD_Window, Object> COLUMN_WinWidth = new ModelColumn<>(I_AD_Window.class, "WinWidth", null);
	String COLUMNNAME_WinWidth = "WinWidth";

	/**
	 * Set Zoom Into Priority.
	 * The priority for this window to be considered when the user is right click and zoom into a field. Small number means high priority.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setZoomIntoPriority (int ZoomIntoPriority);

	/**
	 * Get Zoom Into Priority.
	 * The priority for this window to be considered when the user is right click and zoom into a field. Small number means high priority.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getZoomIntoPriority();

	ModelColumn<I_AD_Window, Object> COLUMN_ZoomIntoPriority = new ModelColumn<>(I_AD_Window.class, "ZoomIntoPriority", null);
	String COLUMNNAME_ZoomIntoPriority = "ZoomIntoPriority";
}
