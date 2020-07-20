package de.metas.inoutcandidate.model;


/** Generated Interface for M_ShipmentSchedule_ExportAudit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ShipmentSchedule_ExportAudit 
{

    /** TableName=M_ShipmentSchedule_ExportAudit */
    public static final String Table_Name = "M_ShipmentSchedule_ExportAudit";

    /** AD_Table_ID=541504 */
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
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue();

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, org.compiere.model.I_AD_Issue>(I_M_ShipmentSchedule_ExportAudit.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object>(I_M_ShipmentSchedule_ExportAudit.class, "Created", null);
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
	 * Set Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExportStatus (java.lang.String ExportStatus);

	/**
	 * Get Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExportStatus();

    /** Column definition for ExportStatus */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_ExportStatus = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object>(I_M_ShipmentSchedule_ExportAudit.class, "ExportStatus", null);
    /** Column name ExportStatus */
    public static final String COLUMNNAME_ExportStatus = "ExportStatus";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object>(I_M_ShipmentSchedule_ExportAudit.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipment Disposition Export Revision.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ExportAudit_ID (int M_ShipmentSchedule_ExportAudit_ID);

	/**
	 * Get Shipment Disposition Export Revision.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ExportAudit_ID();

    /** Column definition for M_ShipmentSchedule_ExportAudit_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_M_ShipmentSchedule_ExportAudit_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object>(I_M_ShipmentSchedule_ExportAudit.class, "M_ShipmentSchedule_ExportAudit_ID", null);
    /** Column name M_ShipmentSchedule_ExportAudit_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID = "M_ShipmentSchedule_ExportAudit_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ID();

	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule();

	public void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule);

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, de.metas.inoutcandidate.model.I_M_ShipmentSchedule>(I_M_ShipmentSchedule_ExportAudit.class, "M_ShipmentSchedule_ID", de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set API Transaction key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTransactionIdAPI (java.lang.String TransactionIdAPI);

	/**
	 * Get API Transaction key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTransactionIdAPI();

    /** Column definition for TransactionIdAPI */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_TransactionIdAPI = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object>(I_M_ShipmentSchedule_ExportAudit.class, "TransactionIdAPI", null);
    /** Column name TransactionIdAPI */
    public static final String COLUMNNAME_TransactionIdAPI = "TransactionIdAPI";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_ExportAudit, Object>(I_M_ShipmentSchedule_ExportAudit.class, "Updated", null);
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
}
