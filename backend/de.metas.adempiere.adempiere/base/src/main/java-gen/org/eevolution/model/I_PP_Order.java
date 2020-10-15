package org.eevolution.model;


/** Generated Interface for PP_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Order 
{

    /** TableName=PP_Order */
    public static final String Table_Name = "PP_Order";

    /** AD_Table_ID=53027 */
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
	 * Set Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgTrx_ID();

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Workflow_ID();

	public org.compiere.model.I_AD_Workflow getAD_Workflow();

	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow);

    /** Column definition for AD_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_AD_Workflow> COLUMN_AD_Workflow_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_AD_Workflow>(I_PP_Order.class, "AD_Workflow_ID", org.compiere.model.I_AD_Workflow.class);
    /** Column name AD_Workflow_ID */
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Set Quantity Assay.
	 * Indicated the Quantity Assay to use into Quality Order
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAssay (java.math.BigDecimal Assay);

	/**
	 * Get Quantity Assay.
	 * Indicated the Quantity Assay to use into Quality Order
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAssay();

    /** Column definition for Assay */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_Assay = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "Assay", null);
    /** Column name Assay */
    public static final String COLUMNNAME_Assay = "Assay";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Can be exported from.
	 * Timestamp from which onwards the record may be exported
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCanBeExportedFrom (java.sql.Timestamp CanBeExportedFrom);

	/**
	 * Get Can be exported from.
	 * Timestamp from which onwards the record may be exported
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCanBeExportedFrom();

    /** Column definition for CanBeExportedFrom */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_CanBeExportedFrom = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "CanBeExportedFrom", null);
    /** Column name CanBeExportedFrom */
    public static final String COLUMNNAME_CanBeExportedFrom = "CanBeExportedFrom";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign();

	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

    /** Column definition for C_Campaign_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_Campaign>(I_PP_Order.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Zielbelegart.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/**
	 * Get Zielbelegart.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocTypeTarget_ID();

    /** Column name C_DocTypeTarget_ID */
    public static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/**
	 * Set Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCopyFrom (java.lang.String CopyFrom);

	/**
	 * Get Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCopyFrom();

    /** Column definition for CopyFrom */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_CopyFrom = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "CopyFrom", null);
    /** Column name CopyFrom */
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getC_Order_ID();

	@Deprecated
	public org.compiere.model.I_C_Order getC_Order();

	@Deprecated
	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_Order>(I_PP_Order.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_OrderLine>(I_PP_Order.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Auftragsposition (MTO).
	 * Auftragsposition (Make to Order)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_MTO_ID (int C_OrderLine_MTO_ID);

	/**
	 * Get Auftragsposition (MTO).
	 * Auftragsposition (Make to Order)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_MTO_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine_MTO();

	public void setC_OrderLine_MTO(org.compiere.model.I_C_OrderLine C_OrderLine_MTO);

    /** Column definition for C_OrderLine_MTO_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_MTO_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_OrderLine>(I_PP_Order.class, "C_OrderLine_MTO_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_MTO_ID */
    public static final String COLUMNNAME_C_OrderLine_MTO_ID = "C_OrderLine_MTO_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set DateConfirm.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateConfirm (java.sql.Timestamp DateConfirm);

	/**
	 * Get DateConfirm.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateConfirm();

    /** Column definition for DateConfirm */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DateConfirm = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DateConfirm", null);
    /** Column name DateConfirm */
    public static final String COLUMNNAME_DateConfirm = "DateConfirm";

	/**
	 * Set Lieferdatum.
	 * Date when the product was delivered
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateDelivered (java.sql.Timestamp DateDelivered);

	/**
	 * Get Lieferdatum.
	 * Date when the product was delivered
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateDelivered();

    /** Column definition for DateDelivered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DateDelivered = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DateDelivered", null);
    /** Column name DateDelivered */
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";

	/**
	 * Set Date planned finished.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateFinish (java.sql.Timestamp DateFinish);

	/**
	 * Get Date planned finished.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateFinish();

    /** Column definition for DateFinish */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DateFinish = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DateFinish", null);
    /** Column name DateFinish */
    public static final String COLUMNNAME_DateFinish = "DateFinish";

	/**
	 * Set DateFinishSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateFinishSchedule (java.sql.Timestamp DateFinishSchedule);

	/**
	 * Get DateFinishSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateFinishSchedule();

    /** Column definition for DateFinishSchedule */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DateFinishSchedule = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DateFinishSchedule", null);
    /** Column name DateFinishSchedule */
    public static final String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set DateStart.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateStart (java.sql.Timestamp DateStart);

	/**
	 * Get DateStart.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateStart();

    /** Column definition for DateStart */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DateStart = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DateStart", null);
    /** Column name DateStart */
    public static final String COLUMNNAME_DateStart = "DateStart";

	/**
	 * Set DateStartSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateStartSchedule (java.sql.Timestamp DateStartSchedule);

	/**
	 * Get DateStartSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateStartSchedule();

    /** Column definition for DateStartSchedule */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DateStartSchedule = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DateStartSchedule", null);
    /** Column name DateStartSchedule */
    public static final String COLUMNNAME_DateStartSchedule = "DateStartSchedule";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_ExportStatus = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "ExportStatus", null);
    /** Column name ExportStatus */
    public static final String COLUMNNAME_ExportStatus = "ExportStatus";

	/**
	 * Set Float After.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFloatAfter (java.math.BigDecimal FloatAfter);

	/**
	 * Get Float After.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFloatAfter();

    /** Column definition for FloatAfter */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_FloatAfter = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "FloatAfter", null);
    /** Column name FloatAfter */
    public static final String COLUMNNAME_FloatAfter = "FloatAfter";

	/**
	 * Set Float Befored.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFloatBefored (java.math.BigDecimal FloatBefored);

	/**
	 * Get Float Befored.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFloatBefored();

    /** Column definition for FloatBefored */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_FloatBefored = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "FloatBefored", null);
    /** Column name FloatBefored */
    public static final String COLUMNNAME_FloatBefored = "FloatBefored";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApproved();

    /** Column definition for IsApproved */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Picking Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPickingOrder (boolean IsPickingOrder);

	/**
	 * Get Picking Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPickingOrder();

    /** Column definition for IsPickingOrder */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_IsPickingOrder = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "IsPickingOrder", null);
    /** Column name IsPickingOrder */
    public static final String COLUMNNAME_IsPickingOrder = "IsPickingOrder";

	/**
	 * Set andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPrinted (boolean IsPrinted);

	/**
	 * Get andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPrinted();

    /** Column definition for IsPrinted */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_IsPrinted = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "IsPrinted", null);
    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Is Qty Percentage.
	 * Indicate that this component is based in % Quantity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsQtyPercentage (boolean IsQtyPercentage);

	/**
	 * Get Is Qty Percentage.
	 * Indicate that this component is based in % Quantity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isQtyPercentage();

    /** Column definition for IsQtyPercentage */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_IsQtyPercentage = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "IsQtyPercentage", null);
    /** Column name IsQtyPercentage */
    public static final String COLUMNNAME_IsQtyPercentage = "IsQtyPercentage";

	/**
	 * Set Selektiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelected (boolean IsSelected);

	/**
	 * Get Selektiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelected();

    /** Column definition for IsSelected */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_IsSelected = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "IsSelected", null);
    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Los-Nr..
	 * Los-Nummer (alphanumerisch)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLot (java.lang.String Lot);

	/**
	 * Get Los-Nr..
	 * Los-Nummer (alphanumerisch)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLot();

    /** Column definition for Lot */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_Lot = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "Lot", null);
    /** Column name Lot */
    public static final String COLUMNNAME_Lot = "Lot";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_M_AttributeSetInstance>(I_PP_Order.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set MRP Allow Cleanup.
	 * MRP is allowed to remove this document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMRP_AllowCleanup (boolean MRP_AllowCleanup);

	/**
	 * Get MRP Allow Cleanup.
	 * MRP is allowed to remove this document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMRP_AllowCleanup();

    /** Column definition for MRP_AllowCleanup */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_MRP_AllowCleanup = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "MRP_AllowCleanup", null);
    /** Column name MRP_AllowCleanup */
    public static final String COLUMNNAME_MRP_AllowCleanup = "MRP_AllowCleanup";

	/**
	 * Set MRP Generated Document.
	 * This document was generated by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMRP_Generated (boolean MRP_Generated);

	/**
	 * Get MRP Generated Document.
	 * This document was generated by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMRP_Generated();

    /** Column definition for MRP_Generated */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_MRP_Generated = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "MRP_Generated", null);
    /** Column name MRP_Generated */
    public static final String COLUMNNAME_MRP_Generated = "MRP_Generated";

	/**
	 * Set To be deleted (MRP).
	 * Indicates if this document is scheduled to be deleted by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMRP_ToDelete (boolean MRP_ToDelete);

	/**
	 * Get To be deleted (MRP).
	 * Indicates if this document is scheduled to be deleted by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMRP_ToDelete();

    /** Column definition for MRP_ToDelete */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_MRP_ToDelete = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "MRP_ToDelete", null);
    /** Column name MRP_ToDelete */
    public static final String COLUMNNAME_MRP_ToDelete = "MRP_ToDelete";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set OrderType.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrderType (java.lang.String OrderType);

	/**
	 * Get OrderType.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrderType();

    /** Column definition for OrderType */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_OrderType = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "OrderType", null);
    /** Column name OrderType */
    public static final String COLUMNNAME_OrderType = "OrderType";

	/**
	 * Set Planner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPlanner_ID (int Planner_ID);

	/**
	 * Get Planner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPlanner_ID();

    /** Column name Planner_ID */
    public static final String COLUMNNAME_Planner_ID = "Planner_ID";

	/**
	 * Set Planning status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPlanningStatus (java.lang.String PlanningStatus);

	/**
	 * Get Planning status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPlanningStatus();

    /** Column definition for PlanningStatus */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_PlanningStatus = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "PlanningStatus", null);
    /** Column name PlanningStatus */
    public static final String COLUMNNAME_PlanningStatus = "PlanningStatus";

	/**
	 * Set Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPosted (boolean Posted);

	/**
	 * Get Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isPosted();

    /** Column definition for Posted */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_Posted = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "Posted", null);
    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostingError_Issue_ID (int PostingError_Issue_ID);

	/**
	 * Get Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPostingError_Issue_ID();

	public org.compiere.model.I_AD_Issue getPostingError_Issue();

	public void setPostingError_Issue(org.compiere.model.I_AD_Issue PostingError_Issue);

    /** Column definition for PostingError_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_AD_Issue> COLUMN_PostingError_Issue_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_AD_Issue>(I_PP_Order.class, "PostingError_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name PostingError_Issue_ID */
    public static final String COLUMNNAME_PostingError_Issue_ID = "PostingError_Issue_ID";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_ID();

    /** Column definition for PP_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_PP_Order_ID = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "PP_Order_ID", null);
    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set BOM & Formula.
	 * BOM & Formula
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Product_BOM_ID (int PP_Product_BOM_ID);

	/**
	 * Get BOM & Formula.
	 * BOM & Formula
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Product_BOM_ID();

	public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM();

	public void setPP_Product_BOM(org.eevolution.model.I_PP_Product_BOM PP_Product_BOM);

    /** Column definition for PP_Product_BOM_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.eevolution.model.I_PP_Product_BOM> COLUMN_PP_Product_BOM_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.eevolution.model.I_PP_Product_BOM>(I_PP_Order.class, "PP_Product_BOM_ID", org.eevolution.model.I_PP_Product_BOM.class);
    /** Column name PP_Product_BOM_ID */
    public static final String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	/**
	 * Set Product Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Product_Planning_ID (int PP_Product_Planning_ID);

	/**
	 * Get Product Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Product_Planning_ID();

	public org.eevolution.model.I_PP_Product_Planning getPP_Product_Planning();

	public void setPP_Product_Planning(org.eevolution.model.I_PP_Product_Planning PP_Product_Planning);

    /** Column definition for PP_Product_Planning_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.eevolution.model.I_PP_Product_Planning> COLUMN_PP_Product_Planning_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.eevolution.model.I_PP_Product_Planning>(I_PP_Order.class, "PP_Product_Planning_ID", org.eevolution.model.I_PP_Product_Planning.class);
    /** Column name PP_Product_Planning_ID */
    public static final String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

	/**
	 * Set Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationDate (java.sql.Timestamp PreparationDate);

	/**
	 * Get Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationDate();

    /** Column definition for PreparationDate */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_PreparationDate = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "PreparationDate", null);
    /** Column name PreparationDate */
    public static final String COLUMNNAME_PreparationDate = "PreparationDate";

	/**
	 * Set Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriorityRule();

    /** Column definition for PriorityRule */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_PriorityRule = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "PriorityRule", null);
    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Qty Batchs.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyBatchs (java.math.BigDecimal QtyBatchs);

	/**
	 * Get Qty Batchs.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyBatchs();

    /** Column definition for QtyBatchs */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_QtyBatchs = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "QtyBatchs", null);
    /** Column name QtyBatchs */
    public static final String COLUMNNAME_QtyBatchs = "QtyBatchs";

	/**
	 * Set Qty Batch Size.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyBatchSize (java.math.BigDecimal QtyBatchSize);

	/**
	 * Get Qty Batch Size.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyBatchSize();

    /** Column definition for QtyBatchSize */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_QtyBatchSize = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "QtyBatchSize", null);
    /** Column name QtyBatchSize */
    public static final String COLUMNNAME_QtyBatchSize = "QtyBatchSize";

	/**
	 * Set Qty before close.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyBeforeClose (java.math.BigDecimal QtyBeforeClose);

	/**
	 * Get Qty before close.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyBeforeClose();

    /** Column definition for QtyBeforeClose */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_QtyBeforeClose = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "QtyBeforeClose", null);
    /** Column name QtyBeforeClose */
    public static final String COLUMNNAME_QtyBeforeClose = "QtyBeforeClose";

	/**
	 * Set Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered();

    /** Column definition for QtyDelivered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyEntered (java.math.BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyEntered();

    /** Column definition for QtyEntered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_QtyEntered = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "QtyEntered", null);
    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyReject (java.math.BigDecimal QtyReject);

	/**
	 * Get Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReject();

    /** Column definition for QtyReject */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_QtyReject = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "QtyReject", null);
    /** Column name QtyReject */
    public static final String COLUMNNAME_QtyReject = "QtyReject";

	/**
	 * Set Open Qty.
	 * Offene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved (java.math.BigDecimal QtyReserved);

	/**
	 * Get Open Qty.
	 * Offene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved();

    /** Column definition for QtyReserved */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_QtyReserved = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "QtyReserved", null);
    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set QtyScrap.
	 * Scrap Quantity for this componet
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyScrap (java.math.BigDecimal QtyScrap);

	/**
	 * Get QtyScrap.
	 * Scrap Quantity for this componet
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyScrap();

    /** Column definition for QtyScrap */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_QtyScrap = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "QtyScrap", null);
    /** Column name QtyScrap */
    public static final String COLUMNNAME_QtyScrap = "QtyScrap";

	/**
	 * Set Planungs-Art.
	 * Type of schedule
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setScheduleType (java.lang.String ScheduleType);

	/**
	 * Get Planungs-Art.
	 * Type of schedule
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getScheduleType();

    /** Column definition for ScheduleType */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_ScheduleType = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "ScheduleType", null);
    /** Column name ScheduleType */
    public static final String COLUMNNAME_ScheduleType = "ScheduleType";

	/**
	 * Set Serien-Nr..
	 * Product Serial Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSerNo (java.lang.String SerNo);

	/**
	 * Get Serien-Nr..
	 * Product Serial Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSerNo();

    /** Column definition for SerNo */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_SerNo = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "SerNo", null);
    /** Column name SerNo */
    public static final String COLUMNNAME_SerNo = "SerNo";

	/**
	 * Set Ressource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Ressource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getS_Resource_ID();

	public org.compiere.model.I_S_Resource getS_Resource();

	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

    /** Column definition for S_Resource_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_S_Resource>(I_PP_Order.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
    /** Column name S_Resource_ID */
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "Updated", null);
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
	 * Set User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser1_ID (int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser1_ID();

	public org.compiere.model.I_C_ElementValue getUser1();

	public void setUser1(org.compiere.model.I_C_ElementValue User1);

    /** Column definition for User1_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_ElementValue>(I_PP_Order.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User1_ID */
    public static final String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser2_ID (int User2_ID);

	/**
	 * Get User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser2_ID();

	public org.compiere.model.I_C_ElementValue getUser2();

	public void setUser2(org.compiere.model.I_C_ElementValue User2);

    /** Column definition for User2_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_PP_Order, org.compiere.model.I_C_ElementValue>(I_PP_Order.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setYield (java.math.BigDecimal Yield);

	/**
	 * Get Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getYield();

    /** Column definition for Yield */
    public static final org.adempiere.model.ModelColumn<I_PP_Order, Object> COLUMN_Yield = new org.adempiere.model.ModelColumn<I_PP_Order, Object>(I_PP_Order.class, "Yield", null);
    /** Column name Yield */
    public static final String COLUMNNAME_Yield = "Yield";
}
