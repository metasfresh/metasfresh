package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Order 
{

	String Table_Name = "PP_Order";

//	/** AD_Table_ID=53027 */
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
	 * Set Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgTrx_ID();

	String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Verantwortlicher Benutzer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_Responsible_ID (int AD_User_Responsible_ID);

	/**
	 * Get Verantwortlicher Benutzer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_Responsible_ID();

	String COLUMNNAME_AD_User_Responsible_ID = "AD_User_Responsible_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Workflow_ID();

	String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Set Quantity Assay.
	 * Indicated the Quantity Assay to use into Quality Order
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssay (@Nullable BigDecimal Assay);

	/**
	 * Get Quantity Assay.
	 * Indicated the Quantity Assay to use into Quality Order
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAssay();

	ModelColumn<I_PP_Order, Object> COLUMN_Assay = new ModelColumn<>(I_PP_Order.class, "Assay", null);
	String COLUMNNAME_Assay = "Assay";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Can be exported from.
	 * Timestamp from which onwards the record may be exported
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCanBeExportedFrom (@Nullable java.sql.Timestamp CanBeExportedFrom);

	/**
	 * Get Can be exported from.
	 * Timestamp from which onwards the record may be exported
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCanBeExportedFrom();

	ModelColumn<I_PP_Order, Object> COLUMN_CanBeExportedFrom = new ModelColumn<>(I_PP_Order.class, "CanBeExportedFrom", null);
	String COLUMNNAME_CanBeExportedFrom = "CanBeExportedFrom";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable org.compiere.model.I_C_Campaign C_Campaign);

	ModelColumn<I_PP_Order, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_PP_Order.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Document Type.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/**
	 * Get Document Type.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeTarget_ID();

	String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/**
	 * Set Copy BOM Lines From.
	 * Copy BOM Lines from an exising BOM
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopyFrom (@Nullable java.lang.String CopyFrom);

	/**
	 * Get Copy BOM Lines From.
	 * Copy BOM Lines from an exising BOM
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCopyFrom();

	ModelColumn<I_PP_Order, Object> COLUMN_CopyFrom = new ModelColumn<>(I_PP_Order.class, "CopyFrom", null);
	String COLUMNNAME_CopyFrom = "CopyFrom";

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
	void setC_Order_ID (int C_Order_ID);

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
	int getC_Order_ID();

	@Deprecated
	@Nullable org.compiere.model.I_C_Order getC_Order();

	@Deprecated
	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_PP_Order, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_PP_Order.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_PP_Order, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_PP_Order.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Auftragsposition (MTO).
	 * Auftragsposition (Make to Order)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_MTO_ID (int C_OrderLine_MTO_ID);

	/**
	 * Get Auftragsposition (MTO).
	 * Auftragsposition (Make to Order)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_MTO_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine_MTO();

	void setC_OrderLine_MTO(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine_MTO);

	ModelColumn<I_PP_Order, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_MTO_ID = new ModelColumn<>(I_PP_Order.class, "C_OrderLine_MTO_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_MTO_ID = "C_OrderLine_MTO_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_Order, Object> COLUMN_Created = new ModelColumn<>(I_PP_Order.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Current Receiving LU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrent_Receiving_LU_HU_ID (int Current_Receiving_LU_HU_ID);

	/**
	 * Get Current Receiving LU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCurrent_Receiving_LU_HU_ID();

	ModelColumn<I_PP_Order, Object> COLUMN_Current_Receiving_LU_HU_ID = new ModelColumn<>(I_PP_Order.class, "Current_Receiving_LU_HU_ID", null);
	String COLUMNNAME_Current_Receiving_LU_HU_ID = "Current_Receiving_LU_HU_ID";

	/**
	 * Set Current Receiving TU instructions.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrent_Receiving_TU_PI_Item_Product_ID (int Current_Receiving_TU_PI_Item_Product_ID);

	/**
	 * Get Current Receiving TU instructions.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCurrent_Receiving_TU_PI_Item_Product_ID();

	ModelColumn<I_PP_Order, Object> COLUMN_Current_Receiving_TU_PI_Item_Product_ID = new ModelColumn<>(I_PP_Order.class, "Current_Receiving_TU_PI_Item_Product_ID", null);
	String COLUMNNAME_Current_Receiving_TU_PI_Item_Product_ID = "Current_Receiving_TU_PI_Item_Product_ID";

	/**
	 * Set Current Scale DeviceId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrentScaleDeviceId (@Nullable java.lang.String CurrentScaleDeviceId);

	/**
	 * Get Current Scale DeviceId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCurrentScaleDeviceId();

	ModelColumn<I_PP_Order, Object> COLUMN_CurrentScaleDeviceId = new ModelColumn<>(I_PP_Order.class, "CurrentScaleDeviceId", null);
	String COLUMNNAME_CurrentScaleDeviceId = "CurrentScaleDeviceId";

	/**
	 * Set Date Confirm.
	 * Date Confirm of this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateConfirm (@Nullable java.sql.Timestamp DateConfirm);

	/**
	 * Get Date Confirm.
	 * Date Confirm of this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateConfirm();

	ModelColumn<I_PP_Order, Object> COLUMN_DateConfirm = new ModelColumn<>(I_PP_Order.class, "DateConfirm", null);
	String COLUMNNAME_DateConfirm = "DateConfirm";

	/**
	 * Set Date Delivered.
	 * Date when the product was delivered
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateDelivered (@Nullable java.sql.Timestamp DateDelivered);

	/**
	 * Get Date Delivered.
	 * Date when the product was delivered
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateDelivered();

	ModelColumn<I_PP_Order, Object> COLUMN_DateDelivered = new ModelColumn<>(I_PP_Order.class, "DateDelivered", null);
	String COLUMNNAME_DateDelivered = "DateDelivered";

	/**
	 * Set Date planned finished.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateFinish (@Nullable java.sql.Timestamp DateFinish);

	/**
	 * Get Date planned finished.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateFinish();

	ModelColumn<I_PP_Order, Object> COLUMN_DateFinish = new ModelColumn<>(I_PP_Order.class, "DateFinish", null);
	String COLUMNNAME_DateFinish = "DateFinish";

	/**
	 * Set Date Finish Schedule.
	 * Scheduled Finish date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateFinishSchedule (@Nullable java.sql.Timestamp DateFinishSchedule);

	/**
	 * Get Date Finish Schedule.
	 * Scheduled Finish date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateFinishSchedule();

	ModelColumn<I_PP_Order, Object> COLUMN_DateFinishSchedule = new ModelColumn<>(I_PP_Order.class, "DateFinishSchedule", null);
	String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateOrdered();

	ModelColumn<I_PP_Order, Object> COLUMN_DateOrdered = new ModelColumn<>(I_PP_Order.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Date Promised.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Date Promised.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDatePromised();

	ModelColumn<I_PP_Order, Object> COLUMN_DatePromised = new ModelColumn<>(I_PP_Order.class, "DatePromised", null);
	String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Start Date.
	 * Indicate the real date to start
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateStart (@Nullable java.sql.Timestamp DateStart);

	/**
	 * Get Start Date.
	 * Indicate the real date to start
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateStart();

	ModelColumn<I_PP_Order, Object> COLUMN_DateStart = new ModelColumn<>(I_PP_Order.class, "DateStart", null);
	String COLUMNNAME_DateStart = "DateStart";

	/**
	 * Set Date Start Schedule.
	 * Scheduled start date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateStartSchedule (java.sql.Timestamp DateStartSchedule);

	/**
	 * Get Date Start Schedule.
	 * Scheduled start date for this Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateStartSchedule();

	ModelColumn<I_PP_Order, Object> COLUMN_DateStartSchedule = new ModelColumn<>(I_PP_Order.class, "DateStartSchedule", null);
	String COLUMNNAME_DateStartSchedule = "DateStartSchedule";

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

	ModelColumn<I_PP_Order, Object> COLUMN_Description = new ModelColumn<>(I_PP_Order.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_PP_Order, Object> COLUMN_DocAction = new ModelColumn<>(I_PP_Order.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocBaseType (@Nullable java.lang.String DocBaseType);

	/**
	 * Get Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocBaseType();

	ModelColumn<I_PP_Order, Object> COLUMN_DocBaseType = new ModelColumn<>(I_PP_Order.class, "DocBaseType", null);
	String COLUMNNAME_DocBaseType = "DocBaseType";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_PP_Order, Object> COLUMN_DocStatus = new ModelColumn<>(I_PP_Order.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_PP_Order, Object> COLUMN_DocumentNo = new ModelColumn<>(I_PP_Order.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExportStatus (java.lang.String ExportStatus);

	/**
	 * Get Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExportStatus();

	ModelColumn<I_PP_Order, Object> COLUMN_ExportStatus = new ModelColumn<>(I_PP_Order.class, "ExportStatus", null);
	String COLUMNNAME_ExportStatus = "ExportStatus";

	/**
	 * Set Float After.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFloatAfter (@Nullable BigDecimal FloatAfter);

	/**
	 * Get Float After.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFloatAfter();

	ModelColumn<I_PP_Order, Object> COLUMN_FloatAfter = new ModelColumn<>(I_PP_Order.class, "FloatAfter", null);
	String COLUMNNAME_FloatAfter = "FloatAfter";

	/**
	 * Set Float Before.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFloatBefored (@Nullable BigDecimal FloatBefored);

	/**
	 * Get Float Before.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFloatBefored();

	ModelColumn<I_PP_Order, Object> COLUMN_FloatBefored = new ModelColumn<>(I_PP_Order.class, "FloatBefored", null);
	String COLUMNNAME_FloatBefored = "FloatBefored";

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

	ModelColumn<I_PP_Order, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Order.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApproved();

	ModelColumn<I_PP_Order, Object> COLUMN_IsApproved = new ModelColumn<>(I_PP_Order.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Picking Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickingOrder (boolean IsPickingOrder);

	/**
	 * Get Picking Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickingOrder();

	ModelColumn<I_PP_Order, Object> COLUMN_IsPickingOrder = new ModelColumn<>(I_PP_Order.class, "IsPickingOrder", null);
	String COLUMNNAME_IsPickingOrder = "IsPickingOrder";

	/**
	 * Set Printed.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrinted (boolean IsPrinted);

	/**
	 * Get Printed.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrinted();

	ModelColumn<I_PP_Order, Object> COLUMN_IsPrinted = new ModelColumn<>(I_PP_Order.class, "IsPrinted", null);
	String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Is %.
	 * Indicate that this component is based in % Quantity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsQtyPercentage (boolean IsQtyPercentage);

	/**
	 * Get Is %.
	 * Indicate that this component is based in % Quantity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isQtyPercentage();

	ModelColumn<I_PP_Order, Object> COLUMN_IsQtyPercentage = new ModelColumn<>(I_PP_Order.class, "IsQtyPercentage", null);
	String COLUMNNAME_IsQtyPercentage = "IsQtyPercentage";

	/**
	 * Set Selected.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSelected (boolean IsSelected);

	/**
	 * Get Selected.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSelected();

	ModelColumn<I_PP_Order, Object> COLUMN_IsSelected = new ModelColumn<>(I_PP_Order.class, "IsSelected", null);
	String COLUMNNAME_IsSelected = "IsSelected";

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

	ModelColumn<I_PP_Order, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_PP_Order.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_PP_Order, Object> COLUMN_Line = new ModelColumn<>(I_PP_Order.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Lot No..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLot (@Nullable java.lang.String Lot);

	/**
	 * Get Lot No..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLot();

	ModelColumn<I_PP_Order, Object> COLUMN_Lot = new ModelColumn<>(I_PP_Order.class, "Lot", null);
	String COLUMNNAME_Lot = "Lot";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_PP_Order, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_PP_Order.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	ModelColumn<I_PP_Order, Object> COLUMN_M_HU_PI_Item_Product_ID = new ModelColumn<>(I_PP_Order.class, "M_HU_PI_Item_Product_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	// /**
	//  * Set Shipment Candidate.
	//  *
	//  * <br>Type: TableDir
	//  * <br>Mandatory: false
	//  * <br>Virtual Column: false
	//  */
	// void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);
	//
	// /**
	//  * Get Shipment Candidate.
	//  *
	//  * <br>Type: TableDir
	//  * <br>Mandatory: false
	//  * <br>Virtual Column: false
	//  */
	// int getM_ShipmentSchedule_ID();
	//
	// ModelColumn<I_PP_Order, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_PP_Order.class, "M_ShipmentSchedule_ID", null);
	// String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";
	//
	// /**
	//  * Set Warehouse.
	//  * Storage Warehouse and Service Point
	//  *
	//  * <br>Type: TableDir
	//  * <br>Mandatory: true
	//  * <br>Virtual Column: false
	//  */
	// void setM_Warehouse_ID (int M_Warehouse_ID);
	//
	// /**
	//  * Get Warehouse.
	//  * Storage Warehouse and Service Point
	//  *
	//  * <br>Type: TableDir
	//  * <br>Mandatory: true
	//  * <br>Virtual Column: false
	//  */
	// int getM_Warehouse_ID();
	//
	// String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Modular Contract.
	 * Document lines linked to a modular contract will generate contract module logs.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModular_Flatrate_Term_ID (int Modular_Flatrate_Term_ID);

	/**
	 * Get Modular Contract.
	 * Document lines linked to a modular contract will generate contract module logs.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModular_Flatrate_Term_ID();

	ModelColumn<I_PP_Order, Object> COLUMN_Modular_Flatrate_Term_ID = new ModelColumn<>(I_PP_Order.class, "Modular_Flatrate_Term_ID", null);
	String COLUMNNAME_Modular_Flatrate_Term_ID = "Modular_Flatrate_Term_ID";

	/**
	 * Set MRP Allow Cleanup.
	 * MRP is allowed to remove this document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMRP_AllowCleanup (boolean MRP_AllowCleanup);

	/**
	 * Get MRP Allow Cleanup.
	 * MRP is allowed to remove this document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMRP_AllowCleanup();

	ModelColumn<I_PP_Order, Object> COLUMN_MRP_AllowCleanup = new ModelColumn<>(I_PP_Order.class, "MRP_AllowCleanup", null);
	String COLUMNNAME_MRP_AllowCleanup = "MRP_AllowCleanup";

	/**
	 * Set MRP Generated Document.
	 * This document was generated by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMRP_Generated (boolean MRP_Generated);

	/**
	 * Get MRP Generated Document.
	 * This document was generated by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMRP_Generated();

	ModelColumn<I_PP_Order, Object> COLUMN_MRP_Generated = new ModelColumn<>(I_PP_Order.class, "MRP_Generated", null);
	String COLUMNNAME_MRP_Generated = "MRP_Generated";

	/**
	 * Set To be deleted (MRP).
	 * Indicates if this document is scheduled to be deleted by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMRP_ToDelete (boolean MRP_ToDelete);

	/**
	 * Get To be deleted (MRP).
	 * Indicates if this document is scheduled to be deleted by MRP
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMRP_ToDelete();

	ModelColumn<I_PP_Order, Object> COLUMN_MRP_ToDelete = new ModelColumn<>(I_PP_Order.class, "MRP_ToDelete", null);
	String COLUMNNAME_MRP_ToDelete = "MRP_ToDelete";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_PP_Order, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_PP_Order.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Order Type.
	 * Type of Order: MRP records grouped by source (Sales Order, Purchase Order, Distribution Order, Requisition)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderType (@Nullable java.lang.String OrderType);

	/**
	 * Get Order Type.
	 * Type of Order: MRP records grouped by source (Sales Order, Purchase Order, Distribution Order, Requisition)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrderType();

	ModelColumn<I_PP_Order, Object> COLUMN_OrderType = new ModelColumn<>(I_PP_Order.class, "OrderType", null);
	String COLUMNNAME_OrderType = "OrderType";

	/**
	 * Set Planner.
	 * Company Agent for Planning
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlanner_ID (int Planner_ID);

	/**
	 * Get Planner.
	 * Company Agent for Planning
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPlanner_ID();

	String COLUMNNAME_Planner_ID = "Planner_ID";

	/**
	 * Set Planning status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlanningStatus (java.lang.String PlanningStatus);

	/**
	 * Get Planning status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPlanningStatus();

	ModelColumn<I_PP_Order, Object> COLUMN_PlanningStatus = new ModelColumn<>(I_PP_Order.class, "PlanningStatus", null);
	String COLUMNNAME_PlanningStatus = "PlanningStatus";

	/**
	 * Set Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPosted (boolean Posted);

	/**
	 * Get Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPosted();

	ModelColumn<I_PP_Order, Object> COLUMN_Posted = new ModelColumn<>(I_PP_Order.class, "Posted", null);
	String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostingError_Issue_ID (int PostingError_Issue_ID);

	/**
	 * Get Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPostingError_Issue_ID();

	String COLUMNNAME_PostingError_Issue_ID = "PostingError_Issue_ID";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_ID();

	ModelColumn<I_PP_Order, Object> COLUMN_PP_Order_ID = new ModelColumn<>(I_PP_Order.class, "PP_Order_ID", null);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set BOM & Formula Version.
	 * BOM & Formula
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOM_ID (int PP_Product_BOM_ID);

	/**
	 * Get BOM & Formula Version.
	 * BOM & Formula
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOM_ID();

	org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM();

	void setPP_Product_BOM(org.eevolution.model.I_PP_Product_BOM PP_Product_BOM);

	ModelColumn<I_PP_Order, org.eevolution.model.I_PP_Product_BOM> COLUMN_PP_Product_BOM_ID = new ModelColumn<>(I_PP_Order.class, "PP_Product_BOM_ID", org.eevolution.model.I_PP_Product_BOM.class);
	String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	/**
	 * Set Product Planning.
	 * Product Planning
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_Planning_ID (int PP_Product_Planning_ID);

	/**
	 * Get Product Planning.
	 * Product Planning
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_Planning_ID();

	String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

	/**
	 * Set Date ready.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationDate (@Nullable java.sql.Timestamp PreparationDate);

	/**
	 * Get Date ready.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationDate();

	ModelColumn<I_PP_Order, Object> COLUMN_PreparationDate = new ModelColumn<>(I_PP_Order.class, "PreparationDate", null);
	String COLUMNNAME_PreparationDate = "PreparationDate";

	/**
	 * Set Priority.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priority.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPriorityRule();

	ModelColumn<I_PP_Order, Object> COLUMN_PriorityRule = new ModelColumn<>(I_PP_Order.class, "PriorityRule", null);
	String COLUMNNAME_PriorityRule = "PriorityRule";

	/**
	 * Set ProblemCode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProblemCode (@Nullable java.lang.String ProblemCode);

	/**
	 * Get ProblemCode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProblemCode();

	ModelColumn<I_PP_Order, Object> COLUMN_ProblemCode = new ModelColumn<>(I_PP_Order.class, "ProblemCode", null);
	String COLUMNNAME_ProblemCode = "ProblemCode";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_PP_Order, Object> COLUMN_Processed = new ModelColumn<>(I_PP_Order.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

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

	ModelColumn<I_PP_Order, Object> COLUMN_Processing = new ModelColumn<>(I_PP_Order.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Qty Batches.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyBatchs (@Nullable BigDecimal QtyBatchs);

	/**
	 * Get Qty Batches.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBatchs();

	ModelColumn<I_PP_Order, Object> COLUMN_QtyBatchs = new ModelColumn<>(I_PP_Order.class, "QtyBatchs", null);
	String COLUMNNAME_QtyBatchs = "QtyBatchs";

	/**
	 * Set Qty Batch Size.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyBatchSize (@Nullable BigDecimal QtyBatchSize);

	/**
	 * Get Qty Batch Size.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBatchSize();

	ModelColumn<I_PP_Order, Object> COLUMN_QtyBatchSize = new ModelColumn<>(I_PP_Order.class, "QtyBatchSize", null);
	String COLUMNNAME_QtyBatchSize = "QtyBatchSize";

	/**
	 * Set Qty before close.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyBeforeClose (BigDecimal QtyBeforeClose);

	/**
	 * Get Qty before close.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBeforeClose();

	ModelColumn<I_PP_Order, Object> COLUMN_QtyBeforeClose = new ModelColumn<>(I_PP_Order.class, "QtyBeforeClose", null);
	String COLUMNNAME_QtyBeforeClose = "QtyBeforeClose";

	/**
	 * Set Shipped Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyDelivered (BigDecimal QtyDelivered);

	/**
	 * Get Shipped Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDelivered();

	ModelColumn<I_PP_Order, Object> COLUMN_QtyDelivered = new ModelColumn<>(I_PP_Order.class, "QtyDelivered", null);
	String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (@Nullable BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered();

	ModelColumn<I_PP_Order, Object> COLUMN_QtyEntered = new ModelColumn<>(I_PP_Order.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_PP_Order, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_PP_Order.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyReject (BigDecimal QtyReject);

	/**
	 * Get Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReject();

	ModelColumn<I_PP_Order, Object> COLUMN_QtyReject = new ModelColumn<>(I_PP_Order.class, "QtyReject", null);
	String COLUMNNAME_QtyReject = "QtyReject";

	/**
	 * Set Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyReserved (@Nullable BigDecimal QtyReserved);

	/**
	 * Get Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReserved();

	ModelColumn<I_PP_Order, Object> COLUMN_QtyReserved = new ModelColumn<>(I_PP_Order.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Quantity Scrap %.
	 * Scrap % Quantity for this componet
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyScrap (BigDecimal QtyScrap);

	/**
	 * Get Quantity Scrap %.
	 * Scrap % Quantity for this componet
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyScrap();

	ModelColumn<I_PP_Order, Object> COLUMN_QtyScrap = new ModelColumn<>(I_PP_Order.class, "QtyScrap", null);
	String COLUMNNAME_QtyScrap = "QtyScrap";

	/**
	 * Set Repair Summary.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRepairOrderSummary (@Nullable java.lang.String RepairOrderSummary);

	/**
	 * Get Repair Summary.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRepairOrderSummary();

	ModelColumn<I_PP_Order, Object> COLUMN_RepairOrderSummary = new ModelColumn<>(I_PP_Order.class, "RepairOrderSummary", null);
	String COLUMNNAME_RepairOrderSummary = "RepairOrderSummary";

	/**
	 * Set Repair Service Performed.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRepairServicePerformed_Product_ID (int RepairServicePerformed_Product_ID);

	/**
	 * Get Repair Service Performed.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRepairServicePerformed_Product_ID();

	String COLUMNNAME_RepairServicePerformed_Product_ID = "RepairServicePerformed_Product_ID";

	/**
	 * Set Schedule Type.
	 * Type of schedule
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setScheduleType (@Nullable java.lang.String ScheduleType);

	/**
	 * Get Schedule Type.
	 * Type of schedule
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getScheduleType();

	ModelColumn<I_PP_Order, Object> COLUMN_ScheduleType = new ModelColumn<>(I_PP_Order.class, "ScheduleType", null);
	String COLUMNNAME_ScheduleType = "ScheduleType";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_PP_Order, Object> COLUMN_SeqNo = new ModelColumn<>(I_PP_Order.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Serial No.
	 * Product Serial Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSerNo (@Nullable java.lang.String SerNo);

	/**
	 * Get Serial No.
	 * Product Serial Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSerNo();

	ModelColumn<I_PP_Order, Object> COLUMN_SerNo = new ModelColumn<>(I_PP_Order.class, "SerNo", null);
	String COLUMNNAME_SerNo = "SerNo";

	/**
	 * Set Resource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Resource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	org.compiere.model.I_S_Resource getS_Resource();

	void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

	ModelColumn<I_PP_Order, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new ModelColumn<>(I_PP_Order.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Order, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Order.class, "Updated", null);
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
	 * Set User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser1_ID (int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser1_ID();

	@Nullable org.compiere.model.I_C_ElementValue getUser1();

	void setUser1(@Nullable org.compiere.model.I_C_ElementValue User1);

	ModelColumn<I_PP_Order, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new ModelColumn<>(I_PP_Order.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser2_ID (int User2_ID);

	/**
	 * Get User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser2_ID();

	@Nullable org.compiere.model.I_C_ElementValue getUser2();

	void setUser2(@Nullable org.compiere.model.I_C_ElementValue User2);

	ModelColumn<I_PP_Order, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new ModelColumn<>(I_PP_Order.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set Work Station.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWorkStation_ID (int WorkStation_ID);

	/**
	 * Get Work Station.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWorkStation_ID();

	@Nullable org.compiere.model.I_S_Resource getWorkStation();

	void setWorkStation(@Nullable org.compiere.model.I_S_Resource WorkStation);

	ModelColumn<I_PP_Order, org.compiere.model.I_S_Resource> COLUMN_WorkStation_ID = new ModelColumn<>(I_PP_Order.class, "WorkStation_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_WorkStation_ID = "WorkStation_ID";

	/**
	 * Set Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setYield (BigDecimal Yield);

	/**
	 * Get Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getYield();

	ModelColumn<I_PP_Order, Object> COLUMN_Yield = new ModelColumn<>(I_PP_Order.class, "Yield", null);
	String COLUMNNAME_Yield = "Yield";
}
