package org.compiere.model;


/** Generated Interface for AD_UI_Section
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_UI_Section 
{

    /** TableName=AD_UI_Section */
    public static final String Table_Name = "AD_UI_Section";

    /** AD_Table_ID=540780 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Register.
	 * Register auf einem Fenster
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Register.
	 * Register auf einem Fenster
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Tab_ID();

	public org.compiere.model.I_AD_Tab getAD_Tab();

	public void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab);

    /** Column definition for AD_Tab_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, org.compiere.model.I_AD_Tab> COLUMN_AD_Tab_ID = new org.adempiere.model.ModelColumn<I_AD_UI_Section, org.compiere.model.I_AD_Tab>(I_AD_UI_Section.class, "AD_Tab_ID", org.compiere.model.I_AD_Tab.class);
    /** Column name AD_Tab_ID */
    public static final String COLUMNNAME_AD_Tab_ID = "AD_Tab_ID";

	/**
	 * Set UI Section.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_UI_Section_ID (int AD_UI_Section_ID);

	/**
	 * Get UI Section.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_UI_Section_ID();

    /** Column definition for AD_UI_Section_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, Object> COLUMN_AD_UI_Section_ID = new org.adempiere.model.ModelColumn<I_AD_UI_Section, Object>(I_AD_UI_Section.class, "AD_UI_Section_ID", null);
    /** Column name AD_UI_Section_ID */
    public static final String COLUMNNAME_AD_UI_Section_ID = "AD_UI_Section_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_UI_Section, Object>(I_AD_UI_Section.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_UI_Section, Object>(I_AD_UI_Section.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_UI_Section, Object>(I_AD_UI_Section.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_UI_Section, Object>(I_AD_UI_Section.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_UI_Section, Object>(I_AD_UI_Section.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_UI_Section, Object>(I_AD_UI_Section.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set UI Style.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUIStyle (java.lang.String UIStyle);

	/**
	 * Get UI Style.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUIStyle();

    /** Column definition for UIStyle */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, Object> COLUMN_UIStyle = new org.adempiere.model.ModelColumn<I_AD_UI_Section, Object>(I_AD_UI_Section.class, "UIStyle", null);
    /** Column name UIStyle */
    public static final String COLUMNNAME_UIStyle = "UIStyle";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_UI_Section, Object>(I_AD_UI_Section.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_Section, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_AD_UI_Section, Object>(I_AD_UI_Section.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
