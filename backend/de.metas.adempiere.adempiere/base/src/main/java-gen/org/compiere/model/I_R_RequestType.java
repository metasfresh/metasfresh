package org.compiere.model;


/** Generated Interface for R_RequestType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_R_RequestType 
{

    /** TableName=R_RequestType */
    public static final String Table_Name = "R_RequestType";

    /** AD_Table_ID=529 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_R_RequestType, org.compiere.model.I_AD_Client>(I_R_RequestType.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_R_RequestType, org.compiere.model.I_AD_Org>(I_R_RequestType.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Auto Due Date Days.
	 * Automatic Due Date Days
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAutoDueDateDays (int AutoDueDateDays);

	/**
	 * Get Auto Due Date Days.
	 * Automatic Due Date Days
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAutoDueDateDays();

    /** Column definition for AutoDueDateDays */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_AutoDueDateDays = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "AutoDueDateDays", null);
    /** Column name AutoDueDateDays */
    public static final String COLUMNNAME_AutoDueDateDays = "AutoDueDateDays";

	/**
	 * Set Confidentiality.
	 * Type of Confidentiality
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setConfidentialType (java.lang.String ConfidentialType);

	/**
	 * Get Confidentiality.
	 * Type of Confidentiality
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConfidentialType();

    /** Column definition for ConfidentialType */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_ConfidentialType = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "ConfidentialType", null);
    /** Column name ConfidentialType */
    public static final String COLUMNNAME_ConfidentialType = "ConfidentialType";

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
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "Created", null);
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

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_R_RequestType, org.compiere.model.I_AD_User>(I_R_RequestType.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Due Date Tolerance.
	 * Tolerance in days between the Date Next Action and the date the request is regarded as overdue
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDueDateTolerance (int DueDateTolerance);

	/**
	 * Get Due Date Tolerance.
	 * Tolerance in days between the Date Next Action and the date the request is regarded as overdue
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDueDateTolerance();

    /** Column definition for DueDateTolerance */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_DueDateTolerance = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "DueDateTolerance", null);
    /** Column name DueDateTolerance */
    public static final String COLUMNNAME_DueDateTolerance = "DueDateTolerance";

	/**
	 * Set Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInternalName (java.lang.String InternalName);

	/**
	 * Get Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInternalName();

    /** Column definition for InternalName */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_InternalName = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "InternalName", null);
    /** Column name InternalName */
    public static final String COLUMNNAME_InternalName = "InternalName";

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
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Create Change Request.
	 * Automatically create BOM (Engineering) Change Request
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAutoChangeRequest (boolean IsAutoChangeRequest);

	/**
	 * Get Create Change Request.
	 * Automatically create BOM (Engineering) Change Request
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAutoChangeRequest();

    /** Column definition for IsAutoChangeRequest */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_IsAutoChangeRequest = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "IsAutoChangeRequest", null);
    /** Column name IsAutoChangeRequest */
    public static final String COLUMNNAME_IsAutoChangeRequest = "IsAutoChangeRequest";

	/**
	 * Set Confidential Info.
	 * Can enter confidential information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsConfidentialInfo (boolean IsConfidentialInfo);

	/**
	 * Get Confidential Info.
	 * Can enter confidential information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isConfidentialInfo();

    /** Column definition for IsConfidentialInfo */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_IsConfidentialInfo = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "IsConfidentialInfo", null);
    /** Column name IsConfidentialInfo */
    public static final String COLUMNNAME_IsConfidentialInfo = "IsConfidentialInfo";

	/**
	 * Set Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefault (boolean IsDefault);

	/**
	 * Get Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefault();

    /** Column definition for IsDefault */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set EMail when Due.
	 * Send EMail when Request becomes due
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEMailWhenDue (boolean IsEMailWhenDue);

	/**
	 * Get EMail when Due.
	 * Send EMail when Request becomes due
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEMailWhenDue();

    /** Column definition for IsEMailWhenDue */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_IsEMailWhenDue = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "IsEMailWhenDue", null);
    /** Column name IsEMailWhenDue */
    public static final String COLUMNNAME_IsEMailWhenDue = "IsEMailWhenDue";

	/**
	 * Set EMail when Overdue.
	 * Send EMail when Request becomes overdue
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEMailWhenOverdue (boolean IsEMailWhenOverdue);

	/**
	 * Get EMail when Overdue.
	 * Send EMail when Request becomes overdue
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEMailWhenOverdue();

    /** Column definition for IsEMailWhenOverdue */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_IsEMailWhenOverdue = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "IsEMailWhenOverdue", null);
    /** Column name IsEMailWhenOverdue */
    public static final String COLUMNNAME_IsEMailWhenOverdue = "IsEMailWhenOverdue";

	/**
	 * Set Indexed.
	 * Index the document for the internal search engine
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsIndexed (boolean IsIndexed);

	/**
	 * Get Indexed.
	 * Index the document for the internal search engine
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isIndexed();

    /** Column definition for IsIndexed */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_IsIndexed = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "IsIndexed", null);
    /** Column name IsIndexed */
    public static final String COLUMNNAME_IsIndexed = "IsIndexed";

	/**
	 * Set Invoiced.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsInvoiced (boolean IsInvoiced);

	/**
	 * Get Invoiced.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isInvoiced();

    /** Column definition for IsInvoiced */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_IsInvoiced = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "IsInvoiced", null);
    /** Column name IsInvoiced */
    public static final String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/**
	 * Set Self-Service.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Self-Service.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelfService();

    /** Column definition for IsSelfService */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set IsUseForPartnerRequestWindow.
	 * Flag that tells if the R_Request entries of this type will be displayed or not in the business partner request window (Vorgang)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUseForPartnerRequestWindow (boolean IsUseForPartnerRequestWindow);

	/**
	 * Get IsUseForPartnerRequestWindow.
	 * Flag that tells if the R_Request entries of this type will be displayed or not in the business partner request window (Vorgang)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseForPartnerRequestWindow();

    /** Column definition for IsUseForPartnerRequestWindow */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_IsUseForPartnerRequestWindow = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "IsUseForPartnerRequestWindow", null);
    /** Column name IsUseForPartnerRequestWindow */
    public static final String COLUMNNAME_IsUseForPartnerRequestWindow = "IsUseForPartnerRequestWindow";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Request Type.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setR_RequestType_ID (int R_RequestType_ID);

	/**
	 * Get Request Type.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getR_RequestType_ID();

    /** Column definition for R_RequestType_ID */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_R_RequestType_ID = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "R_RequestType_ID", null);
    /** Column name R_RequestType_ID */
    public static final String COLUMNNAME_R_RequestType_ID = "R_RequestType_ID";

	/**
	 * Set Status Category.
	 * Request Status Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setR_StatusCategory_ID (int R_StatusCategory_ID);

	/**
	 * Get Status Category.
	 * Request Status Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getR_StatusCategory_ID();

	public org.compiere.model.I_R_StatusCategory getR_StatusCategory();

	public void setR_StatusCategory(org.compiere.model.I_R_StatusCategory R_StatusCategory);

    /** Column definition for R_StatusCategory_ID */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, org.compiere.model.I_R_StatusCategory> COLUMN_R_StatusCategory_ID = new org.adempiere.model.ModelColumn<I_R_RequestType, org.compiere.model.I_R_StatusCategory>(I_R_RequestType.class, "R_StatusCategory_ID", org.compiere.model.I_R_StatusCategory.class);
    /** Column name R_StatusCategory_ID */
    public static final String COLUMNNAME_R_StatusCategory_ID = "R_StatusCategory_ID";

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
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_R_RequestType, Object>(I_R_RequestType.class, "Updated", null);
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

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_R_RequestType, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_R_RequestType, org.compiere.model.I_AD_User>(I_R_RequestType.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
