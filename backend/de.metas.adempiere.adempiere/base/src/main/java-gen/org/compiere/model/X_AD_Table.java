// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Table
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Table extends org.compiere.model.PO implements I_AD_Table, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -88752471L;

    /** Standard Constructor */
    public X_AD_Table (final Properties ctx, final int AD_Table_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Table_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Table (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AccessLevel AD_Reference_ID=5
	 * Reference name: AD_Table Access Levels
	 */
	public static final int ACCESSLEVEL_AD_Reference_ID=5;
	/** Organization = 1 */
	public static final String ACCESSLEVEL_Organization = "1";
	/** ClientPlusOrganization = 3 */
	public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
	/** SystemOnly = 4 */
	public static final String ACCESSLEVEL_SystemOnly = "4";
	/** All = 7 */
	public static final String ACCESSLEVEL_All = "7";
	/** SystemPlusClient = 6 */
	public static final String ACCESSLEVEL_SystemPlusClient = "6";
	/** ClientOnly = 2 */
	public static final String ACCESSLEVEL_ClientOnly = "2";
	@Override
	public void setAccessLevel (final java.lang.String AccessLevel)
	{
		set_Value (COLUMNNAME_AccessLevel, AccessLevel);
	}

	@Override
	public java.lang.String getAccessLevel() 
	{
		return get_ValueAsString(COLUMNNAME_AccessLevel);
	}

	@Override
	public void setACTriggerLength (final int ACTriggerLength)
	{
		set_Value (COLUMNNAME_ACTriggerLength, ACTriggerLength);
	}

	@Override
	public int getACTriggerLength() 
	{
		return get_ValueAsInt(COLUMNNAME_ACTriggerLength);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setAD_Val_Rule(final org.compiere.model.I_AD_Val_Rule AD_Val_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, AD_Val_Rule);
	}

	@Override
	public void setAD_Val_Rule_ID (final int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, AD_Val_Rule_ID);
	}

	@Override
	public int getAD_Val_Rule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Val_Rule_ID);
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
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, AD_Window_ID);
	}

	@Override
	public int getAD_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Window_ID);
	}

	/** 
	 * CloningEnabled AD_Reference_ID=541757
	 * Reference name: AD_Table_CloningEnabled
	 */
	public static final int CLONINGENABLED_AD_Reference_ID=541757;
	/** Enabled = E */
	public static final String CLONINGENABLED_Enabled = "E";
	/** Disabled = D */
	public static final String CLONINGENABLED_Disabled = "D";
	/** Auto = A */
	public static final String CLONINGENABLED_Auto = "A";
	@Override
	public void setCloningEnabled (final java.lang.String CloningEnabled)
	{
		set_Value (COLUMNNAME_CloningEnabled, CloningEnabled);
	}

	@Override
	public java.lang.String getCloningEnabled() 
	{
		return get_ValueAsString(COLUMNNAME_CloningEnabled);
	}

	@Override
	public void setCopyColumnsFromTable (final @Nullable java.lang.String CopyColumnsFromTable)
	{
		set_Value (COLUMNNAME_CopyColumnsFromTable, CopyColumnsFromTable);
	}

	@Override
	public java.lang.String getCopyColumnsFromTable() 
	{
		return get_ValueAsString(COLUMNNAME_CopyColumnsFromTable);
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
	 * DownlineCloningStrategy AD_Reference_ID=541755
	 * Reference name: AD_Table_DownlineCloningStrategy
	 */
	public static final int DOWNLINECLONINGSTRATEGY_AD_Reference_ID=541755;
	/** Skip = S */
	public static final String DOWNLINECLONINGSTRATEGY_Skip = "S";
	/** Auto = A */
	public static final String DOWNLINECLONINGSTRATEGY_Auto = "A";
	/** OnlyIncluded = I */
	public static final String DOWNLINECLONINGSTRATEGY_OnlyIncluded = "I";
	@Override
	public void setDownlineCloningStrategy (final java.lang.String DownlineCloningStrategy)
	{
		set_Value (COLUMNNAME_DownlineCloningStrategy, DownlineCloningStrategy);
	}

	@Override
	public java.lang.String getDownlineCloningStrategy() 
	{
		return get_ValueAsString(COLUMNNAME_DownlineCloningStrategy);
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
	public void setImportTable (final @Nullable java.lang.String ImportTable)
	{
		set_Value (COLUMNNAME_ImportTable, ImportTable);
	}

	@Override
	public java.lang.String getImportTable() 
	{
		return get_ValueAsString(COLUMNNAME_ImportTable);
	}

	@Override
	public void setIsAutocomplete (final boolean IsAutocomplete)
	{
		set_Value (COLUMNNAME_IsAutocomplete, IsAutocomplete);
	}

	@Override
	public boolean isAutocomplete() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutocomplete);
	}

	@Override
	public void setIsChangeLog (final boolean IsChangeLog)
	{
		set_Value (COLUMNNAME_IsChangeLog, IsChangeLog);
	}

	@Override
	public boolean isChangeLog() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsChangeLog);
	}

	@Override
	public void setIsDeleteable (final boolean IsDeleteable)
	{
		set_Value (COLUMNNAME_IsDeleteable, IsDeleteable);
	}

	@Override
	public boolean isDeleteable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDeleteable);
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
	public void setIsHighVolume (final boolean IsHighVolume)
	{
		set_Value (COLUMNNAME_IsHighVolume, IsHighVolume);
	}

	@Override
	public boolean isHighVolume() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsHighVolume);
	}

	@Override
	public void setIsSecurityEnabled (final boolean IsSecurityEnabled)
	{
		set_Value (COLUMNNAME_IsSecurityEnabled, IsSecurityEnabled);
	}

	@Override
	public boolean isSecurityEnabled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSecurityEnabled);
	}

	@Override
	public void setIsView (final boolean IsView)
	{
		set_Value (COLUMNNAME_IsView, IsView);
	}

	@Override
	public boolean isView() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsView);
	}

	@Override
	public void setLoadSeq (final int LoadSeq)
	{
		set_ValueNoCheck (COLUMNNAME_LoadSeq, LoadSeq);
	}

	@Override
	public int getLoadSeq() 
	{
		return get_ValueAsInt(COLUMNNAME_LoadSeq);
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

	/** 
	 * PersonalDataCategory AD_Reference_ID=540857
	 * Reference name: PersonalDataCategory
	 */
	public static final int PERSONALDATACATEGORY_AD_Reference_ID=540857;
	/** NotPersonal = NP */
	public static final String PERSONALDATACATEGORY_NotPersonal = "NP";
	/** Personal = P */
	public static final String PERSONALDATACATEGORY_Personal = "P";
	/** SensitivePersonal = SP */
	public static final String PERSONALDATACATEGORY_SensitivePersonal = "SP";
	@Override
	public void setPersonalDataCategory (final java.lang.String PersonalDataCategory)
	{
		set_Value (COLUMNNAME_PersonalDataCategory, PersonalDataCategory);
	}

	@Override
	public java.lang.String getPersonalDataCategory() 
	{
		return get_ValueAsString(COLUMNNAME_PersonalDataCategory);
	}

	@Override
	public org.compiere.model.I_AD_Window getPO_Window()
	{
		return get_ValueAsPO(COLUMNNAME_PO_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setPO_Window(final org.compiere.model.I_AD_Window PO_Window)
	{
		set_ValueFromPO(COLUMNNAME_PO_Window_ID, org.compiere.model.I_AD_Window.class, PO_Window);
	}

	@Override
	public void setPO_Window_ID (final int PO_Window_ID)
	{
		if (PO_Window_ID < 1) 
			set_Value (COLUMNNAME_PO_Window_ID, null);
		else 
			set_Value (COLUMNNAME_PO_Window_ID, PO_Window_ID);
	}

	@Override
	public int getPO_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PO_Window_ID);
	}

	/** 
	 * ReplicationType AD_Reference_ID=126
	 * Reference name: AD_Table Replication Type
	 */
	public static final int REPLICATIONTYPE_AD_Reference_ID=126;
	/** Local = L */
	public static final String REPLICATIONTYPE_Local = "L";
	/** Merge = M */
	public static final String REPLICATIONTYPE_Merge = "M";
	/** Reference = R */
	public static final String REPLICATIONTYPE_Reference = "R";
	@Override
	public void setReplicationType (final java.lang.String ReplicationType)
	{
		set_Value (COLUMNNAME_ReplicationType, ReplicationType);
	}

	@Override
	public java.lang.String getReplicationType() 
	{
		return get_ValueAsString(COLUMNNAME_ReplicationType);
	}

	@Override
	public void setTableName (final java.lang.String TableName)
	{
		set_Value (COLUMNNAME_TableName, TableName);
	}

	@Override
	public java.lang.String getTableName() 
	{
		return get_ValueAsString(COLUMNNAME_TableName);
	}

	@Override
	public void setTechnicalNote (final @Nullable java.lang.String TechnicalNote)
	{
		set_Value (COLUMNNAME_TechnicalNote, TechnicalNote);
	}

	@Override
	public java.lang.String getTechnicalNote() 
	{
		return get_ValueAsString(COLUMNNAME_TechnicalNote);
	}

	/** 
	 * TooltipType AD_Reference_ID=541141
	 * Reference name: TooltipType
	 */
	public static final int TOOLTIPTYPE_AD_Reference_ID=541141;
	/** DescriptionFallbackToTableIdentifier = DTI */
	public static final String TOOLTIPTYPE_DescriptionFallbackToTableIdentifier = "DTI";
	/** TableIdentifier = T */
	public static final String TOOLTIPTYPE_TableIdentifier = "T";
	/** Description = D */
	public static final String TOOLTIPTYPE_Description = "D";
	@Override
	public void setTooltipType (final java.lang.String TooltipType)
	{
		set_Value (COLUMNNAME_TooltipType, TooltipType);
	}

	@Override
	public java.lang.String getTooltipType() 
	{
		return get_ValueAsString(COLUMNNAME_TooltipType);
	}

	@Override
	public void setWEBUI_View_PageLength (final int WEBUI_View_PageLength)
	{
		set_Value (COLUMNNAME_WEBUI_View_PageLength, WEBUI_View_PageLength);
	}

	@Override
	public int getWEBUI_View_PageLength() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_View_PageLength);
	}

	/** 
	 * WhenChildCloningStrategy AD_Reference_ID=541756
	 * Reference name: AD_Table_CloningStrategy
	 */
	public static final int WHENCHILDCLONINGSTRATEGY_AD_Reference_ID=541756;
	/** Skip = S */
	public static final String WHENCHILDCLONINGSTRATEGY_Skip = "S";
	/** AllowCloning = A */
	public static final String WHENCHILDCLONINGSTRATEGY_AllowCloning = "A";
	/** AlwaysInclude = I */
	public static final String WHENCHILDCLONINGSTRATEGY_AlwaysInclude = "I";
	@Override
	public void setWhenChildCloningStrategy (final java.lang.String WhenChildCloningStrategy)
	{
		set_Value (COLUMNNAME_WhenChildCloningStrategy, WhenChildCloningStrategy);
	}

	@Override
	public java.lang.String getWhenChildCloningStrategy() 
	{
		return get_ValueAsString(COLUMNNAME_WhenChildCloningStrategy);
	}
}