package org.eevolution.model;


/** Generated Interface for PP_Cost_Collector_ImportAuditItem
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Cost_Collector_ImportAuditItem 
{

    /** TableName=PP_Cost_Collector_ImportAuditItem */
    public static final String Table_Name = "PP_Cost_Collector_ImportAuditItem";

    /** AD_Table_ID=541528 */
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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, org.compiere.model.I_AD_Issue>(I_PP_Cost_Collector_ImportAuditItem.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object>(I_PP_Cost_Collector_ImportAuditItem.class, "Created", null);
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
	 * Set Error Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setErrorMsg (java.lang.String ErrorMsg);

	/**
	 * Get Error Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getErrorMsg();

    /** Column definition for ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object> COLUMN_ErrorMsg = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object>(I_PP_Cost_Collector_ImportAuditItem.class, "ErrorMsg", null);
    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set Import Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setImportStatus (java.lang.String ImportStatus);

	/**
	 * Get Import Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getImportStatus();

    /** Column definition for ImportStatus */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object> COLUMN_ImportStatus = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object>(I_PP_Cost_Collector_ImportAuditItem.class, "ImportStatus", null);
    /** Column name ImportStatus */
    public static final String COLUMNNAME_ImportStatus = "ImportStatus";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object>(I_PP_Cost_Collector_ImportAuditItem.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set JSON Request.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJsonRequest (java.lang.String JsonRequest);

	/**
	 * Get JSON Request.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJsonRequest();

    /** Column definition for JsonRequest */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object> COLUMN_JsonRequest = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object>(I_PP_Cost_Collector_ImportAuditItem.class, "JsonRequest", null);
    /** Column name JsonRequest */
    public static final String COLUMNNAME_JsonRequest = "JsonRequest";

	/**
	 * Set JSON Response.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJsonResponse (java.lang.String JsonResponse);

	/**
	 * Get JSON Response.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJsonResponse();

    /** Column definition for JsonResponse */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object> COLUMN_JsonResponse = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object>(I_PP_Cost_Collector_ImportAuditItem.class, "JsonResponse", null);
    /** Column name JsonResponse */
    public static final String COLUMNNAME_JsonResponse = "JsonResponse";

	/**
	 * Set Manufacturing Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID);

	/**
	 * Get Manufacturing Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Cost_Collector_ID();

	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector();

	public void setPP_Cost_Collector(org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector);

    /** Column definition for PP_Cost_Collector_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, org.eevolution.model.I_PP_Cost_Collector>(I_PP_Cost_Collector_ImportAuditItem.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
    /** Column name PP_Cost_Collector_ID */
    public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/**
	 * Set Cost Collector Import Audit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Cost_Collector_ImportAudit_ID (int PP_Cost_Collector_ImportAudit_ID);

	/**
	 * Get Cost Collector Import Audit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Cost_Collector_ImportAudit_ID();

	public org.eevolution.model.I_PP_Cost_Collector_ImportAudit getPP_Cost_Collector_ImportAudit();

	public void setPP_Cost_Collector_ImportAudit(org.eevolution.model.I_PP_Cost_Collector_ImportAudit PP_Cost_Collector_ImportAudit);

    /** Column definition for PP_Cost_Collector_ImportAudit_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, org.eevolution.model.I_PP_Cost_Collector_ImportAudit> COLUMN_PP_Cost_Collector_ImportAudit_ID = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, org.eevolution.model.I_PP_Cost_Collector_ImportAudit>(I_PP_Cost_Collector_ImportAuditItem.class, "PP_Cost_Collector_ImportAudit_ID", org.eevolution.model.I_PP_Cost_Collector_ImportAudit.class);
    /** Column name PP_Cost_Collector_ImportAudit_ID */
    public static final String COLUMNNAME_PP_Cost_Collector_ImportAudit_ID = "PP_Cost_Collector_ImportAudit_ID";

	/**
	 * Set Cost Collector Import Audit Item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Cost_Collector_ImportAuditItem_ID (int PP_Cost_Collector_ImportAuditItem_ID);

	/**
	 * Get Cost Collector Import Audit Item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Cost_Collector_ImportAuditItem_ID();

    /** Column definition for PP_Cost_Collector_ImportAuditItem_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object> COLUMN_PP_Cost_Collector_ImportAuditItem_ID = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object>(I_PP_Cost_Collector_ImportAuditItem.class, "PP_Cost_Collector_ImportAuditItem_ID", null);
    /** Column name PP_Cost_Collector_ImportAuditItem_ID */
    public static final String COLUMNNAME_PP_Cost_Collector_ImportAuditItem_ID = "PP_Cost_Collector_ImportAuditItem_ID";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order();

	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

    /** Column definition for PP_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, org.eevolution.model.I_PP_Order>(I_PP_Cost_Collector_ImportAuditItem.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_ImportAuditItem, Object>(I_PP_Cost_Collector_ImportAuditItem.class, "Updated", null);
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
