package de.metas.inoutcandidate.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_ReceiptSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ReceiptSchedule 
{

	String Table_Name = "M_ReceiptSchedule";

//	/** AD_Table_ID=540524 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set User/ Contact override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_Override_ID (int AD_User_Override_ID);

	/**
	 * Get User/ Contact override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_Override_ID();

	String COLUMNNAME_AD_User_Override_ID = "AD_User_Override_ID";

	/**
	 * Set ATA.
	 * Actual Arrival Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setATA (@Nullable java.sql.Timestamp ATA);

	/**
	 * Get ATA.
	 * Actual Arrival Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getATA();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ATA = new ModelColumn<>(I_M_ReceiptSchedule.class, "ATA", null);
	String COLUMNNAME_ATA = "ATA";

	/**
	 * Set ATD.
	 * Actual Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setATD (@Nullable java.sql.Timestamp ATD);

	/**
	 * Get ATD.
	 * Actual Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getATD();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ATD = new ModelColumn<>(I_M_ReceiptSchedule.class, "ATD", null);
	String COLUMNNAME_ATD = "ATD";

	/**
	 * Set B/L Date.
	 * Date when the Bill of Lading was issued by the carrier. The Bill of Lading Date is automatically synchronized from the Transport Order to the Purchase Order once the transport is completed. Manual changes of the BL Date in the Purchase Order are not allowed — it is always derived from the corresponding Transport Order.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setBLDate (@Nullable java.sql.Timestamp BLDate);

	/**
	 * Get B/L Date.
	 * Date when the Bill of Lading was issued by the carrier. The Bill of Lading Date is automatically synchronized from the Transport Order to the Purchase Order once the transport is completed. Manual changes of the BL Date in the Purchase Order are not allowed — it is always derived from the corresponding Transport Order.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getBLDate();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_BLDate = new ModelColumn<>(I_M_ReceiptSchedule.class, "BLDate", null);
	String COLUMNNAME_BLDate = "BLDate";

	/**
	 * Set Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerAddress (@Nullable java.lang.String BPartnerAddress);

	/**
	 * Get Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerAddress();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_BPartnerAddress = new ModelColumn<>(I_M_ReceiptSchedule.class, "BPartnerAddress", null);
	String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/**
	 * Set User address note.
	 * Bezeichnet die letztendlich verwendete Lieferanschrift
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerAddress_Override (@Nullable java.lang.String BPartnerAddress_Override);

	/**
	 * Get User address note.
	 * Bezeichnet die letztendlich verwendete Lieferanschrift
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerAddress_Override();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_BPartnerAddress_Override = new ModelColumn<>(I_M_ReceiptSchedule.class, "BPartnerAddress_Override", null);
	String COLUMNNAME_BPartnerAddress_Override = "BPartnerAddress_Override";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set KW.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setCalendarWeek (@Nullable BigDecimal CalendarWeek);

	/**
	 * Get KW.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getCalendarWeek();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_CalendarWeek = new ModelColumn<>(I_M_ReceiptSchedule.class, "CalendarWeek", null);
	String COLUMNNAME_CalendarWeek = "CalendarWeek";

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

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_CanBeExportedFrom = new ModelColumn<>(I_M_ReceiptSchedule.class, "CanBeExportedFrom", null);
	String COLUMNNAME_CanBeExportedFrom = "CanBeExportedFrom";

	/**
	 * Set Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCatch_UOM_ID (int Catch_UOM_ID);

	/**
	 * Get Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCatch_UOM_ID();

	String COLUMNNAME_Catch_UOM_ID = "Catch_UOM_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Alt. Business Partner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Override_ID (int C_BPartner_Override_ID);

	/**
	 * Get Alt. Business Partner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Override_ID();

	String COLUMNNAME_C_BPartner_Override_ID = "C_BPartner_Override_ID";

	/**
	 * Set Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_BP_Group_ID();

	@Deprecated
	@Nullable org.compiere.model.I_C_BP_Group getC_BP_Group();

	@Deprecated
	void setC_BP_Group(@Nullable org.compiere.model.I_C_BP_Group C_BP_Group);

	ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Location override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_Location_Override_ID (int C_BP_Location_Override_ID);

	/**
	 * Get Location override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_Location_Override_ID();

	String COLUMNNAME_C_BP_Location_Override_ID = "C_BP_Location_Override_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable org.compiere.model.I_C_Campaign C_Campaign);

	ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "C_Flatrate_Term_ID", null);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Container No.
	 * Number of the container
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setContainerNo (@Nullable java.lang.String ContainerNo);

	/**
	 * Get Container No.
	 * Number of the container
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getContainerNo();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ContainerNo = new ModelColumn<>(I_M_ReceiptSchedule.class, "ContainerNo", null);
	String COLUMNNAME_ContainerNo = "ContainerNo";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
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

	ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Sales Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Sales Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderSO_ID();

	@Nullable org.compiere.model.I_C_Order getC_OrderSO();

	void setC_OrderSO(@Nullable org.compiere.model.I_C_Order C_OrderSO);

	ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

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
	 * Set CRD.
	 * Desired level of delivery readiness from the supplier
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setCRD (@Nullable java.sql.Timestamp CRD);

	/**
	 * Get CRD.
	 * Desired level of delivery readiness from the supplier
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getCRD();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_CRD = new ModelColumn<>(I_M_ReceiptSchedule.class, "CRD", null);
	String COLUMNNAME_CRD = "CRD";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Created = new ModelColumn<>(I_M_ReceiptSchedule.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (@Nullable java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateOrdered();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DateOrdered = new ModelColumn<>(I_M_ReceiptSchedule.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Date Promised eff..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDatePromised_Effective (@Nullable java.sql.Timestamp DatePromised_Effective);

	/**
	 * Get Date Promised eff..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getDatePromised_Effective();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DatePromised_Effective = new ModelColumn<>(I_M_ReceiptSchedule.class, "DatePromised_Effective", null);
	String COLUMNNAME_DatePromised_Effective = "DatePromised_Effective";

	/**
	 * Set Date Promised override.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatePromised_Override (@Nullable java.sql.Timestamp DatePromised_Override);

	/**
	 * Get Date Promised override.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDatePromised_Override();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DatePromised_Override = new ModelColumn<>(I_M_ReceiptSchedule.class, "DatePromised_Override", null);
	String COLUMNNAME_DatePromised_Override = "DatePromised_Override";

	/**
	 * Set Delivery Rule.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Delivery Rule.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeliveryRule();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryRule = new ModelColumn<>(I_M_ReceiptSchedule.class, "DeliveryRule", null);
	String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Alt. Deliverymethod.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule_Override (@Nullable java.lang.String DeliveryRule_Override);

	/**
	 * Get Alt. Deliverymethod.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryRule_Override();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryRule_Override = new ModelColumn<>(I_M_ReceiptSchedule.class, "DeliveryRule_Override", null);
	String COLUMNNAME_DeliveryRule_Override = "DeliveryRule_Override";

	/**
	 * Set Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeliveryViaRule();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_M_ReceiptSchedule.class, "DeliveryViaRule", null);
	String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Delivery via Rule override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule_Override (@Nullable java.lang.String DeliveryViaRule_Override);

	/**
	 * Get Delivery via Rule override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryViaRule_Override();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_DeliveryViaRule_Override = new ModelColumn<>(I_M_ReceiptSchedule.class, "DeliveryViaRule_Override", null);
	String COLUMNNAME_DeliveryViaRule_Override = "DeliveryViaRule_Override";

	/**
	 * Set ETA.
	 * The ETA is used for shipment tracking, delivery planning, and scheduling of warehouse operations. The ETA is automatically synchronized from the Transport Order to the Purchase Order when the transport information is updated. Manual changes of the ETA in the Purchase Order are not allowed — it is always maintained in the corresponding Transport Order.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setETA (@Nullable java.sql.Timestamp ETA);

	/**
	 * Get ETA.
	 * The ETA is used for shipment tracking, delivery planning, and scheduling of warehouse operations. The ETA is automatically synchronized from the Transport Order to the Purchase Order when the transport information is updated. Manual changes of the ETA in the Purchase Order are not allowed — it is always maintained in the corresponding Transport Order.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getETA();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ETA = new ModelColumn<>(I_M_ReceiptSchedule.class, "ETA", null);
	String COLUMNNAME_ETA = "ETA";

	/**
	 * Set ETD.
	 * Estimated Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setETD (@Nullable java.sql.Timestamp ETD);

	/**
	 * Get ETD.
	 * Estimated Shipping Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getETD();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ETD = new ModelColumn<>(I_M_ReceiptSchedule.class, "ETD", null);
	String COLUMNNAME_ETD = "ETD";

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

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ExportStatus = new ModelColumn<>(I_M_ReceiptSchedule.class, "ExportStatus", null);
	String COLUMNNAME_ExportStatus = "ExportStatus";

	/**
	 * Set External resource URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalResourceURL (@Nullable java.lang.String ExternalResourceURL);

	/**
	 * Get External resource URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalResourceURL();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ExternalResourceURL = new ModelColumn<>(I_M_ReceiptSchedule.class, "ExternalResourceURL", null);
	String COLUMNNAME_ExternalResourceURL = "ExternalResourceURL";

	/**
	 * Set Filtered-Count with order.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setFilteredItemsWithSameC_Order_ID (int FilteredItemsWithSameC_Order_ID);

	/**
	 * Get Filtered-Count with order.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getFilteredItemsWithSameC_Order_ID();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_FilteredItemsWithSameC_Order_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "FilteredItemsWithSameC_Order_ID", null);
	String COLUMNNAME_FilteredItemsWithSameC_Order_ID = "FilteredItemsWithSameC_Order_ID";

	/**
	 * Set Header  merge characteristic.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHeaderAggregationKey (@Nullable java.lang.String HeaderAggregationKey);

	/**
	 * Get Header  merge characteristic.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHeaderAggregationKey();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_HeaderAggregationKey = new ModelColumn<>(I_M_ReceiptSchedule.class, "HeaderAggregationKey", null);
	String COLUMNNAME_HeaderAggregationKey = "HeaderAggregationKey";

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

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ReceiptSchedule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set B/L Received.
	 * Has the bill of lading been received?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsBLReceived (boolean IsBLReceived);

	/**
	 * Get B/L Received.
	 * Has the bill of lading been received?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isBLReceived();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsBLReceived = new ModelColumn<>(I_M_ReceiptSchedule.class, "IsBLReceived", null);
	String COLUMNNAME_IsBLReceived = "IsBLReceived";

	/**
	 * Set Booking Confirmed.
	 * Has the shipping booking been confirmed?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsBookingConfirmed (boolean IsBookingConfirmed);

	/**
	 * Get Booking Confirmed.
	 * Has the shipping booking been confirmed?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isBookingConfirmed();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsBookingConfirmed = new ModelColumn<>(I_M_ReceiptSchedule.class, "IsBookingConfirmed", null);
	String COLUMNNAME_IsBookingConfirmed = "IsBookingConfirmed";

	/**
	 * Set Address override.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBPartnerAddress_Override (boolean IsBPartnerAddress_Override);

	/**
	 * Get Address override.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBPartnerAddress_Override();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsBPartnerAddress_Override = new ModelColumn<>(I_M_ReceiptSchedule.class, "IsBPartnerAddress_Override", null);
	String COLUMNNAME_IsBPartnerAddress_Override = "IsBPartnerAddress_Override";

	/**
	 * Set Confirmed by supplier.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsConfirmedBySupplier (boolean IsConfirmedBySupplier);

	/**
	 * Get Confirmed by supplier.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isConfirmedBySupplier();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsConfirmedBySupplier = new ModelColumn<>(I_M_ReceiptSchedule.class, "IsConfirmedBySupplier", null);
	String COLUMNNAME_IsConfirmedBySupplier = "IsConfirmedBySupplier";

	/**
	 * Set Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPackagingMaterial (boolean IsPackagingMaterial);

	/**
	 * Get Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPackagingMaterial();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsPackagingMaterial = new ModelColumn<>(I_M_ReceiptSchedule.class, "IsPackagingMaterial", null);
	String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";

	/**
	 * Set WE Notice.
	 * Has the container planning been completed?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsWENotice (boolean IsWENotice);

	/**
	 * Get WE Notice.
	 * Has the container planning been completed?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isWENotice();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsWENotice = new ModelColumn<>(I_M_ReceiptSchedule.class, "IsWENotice", null);
	String COLUMNNAME_IsWENotice = "IsWENotice";

	/**
	 * Set Material Receipt Info.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setMaterialReceiptInfo (@Nullable java.lang.String MaterialReceiptInfo);

	/**
	 * Get Material Receipt Info.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getMaterialReceiptInfo();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_MaterialReceiptInfo = new ModelColumn<>(I_M_ReceiptSchedule.class, "MaterialReceiptInfo", null);
	String COLUMNNAME_MaterialReceiptInfo = "MaterialReceiptInfo";

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

	ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Customs Tariff.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_CustomsTariff_ID (int M_CustomsTariff_ID);

	/**
	 * Get Customs Tariff.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_CustomsTariff_ID();

	@Deprecated
	@Nullable org.compiere.model.I_M_CustomsTariff getM_CustomsTariff();

	@Deprecated
	void setM_CustomsTariff(@Nullable org.compiere.model.I_M_CustomsTariff M_CustomsTariff);

	ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_M_CustomsTariff> COLUMN_M_CustomsTariff_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "M_CustomsTariff_ID", org.compiere.model.I_M_CustomsTariff.class);
	String COLUMNNAME_M_CustomsTariff_ID = "M_CustomsTariff_ID";

	/**
	 * Set Packing Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_LUTU_Configuration_ID (int M_HU_LUTU_Configuration_ID);

	/**
	 * Get Packing Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_LUTU_Configuration_ID();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_M_HU_LUTU_Configuration_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "M_HU_LUTU_Configuration_ID", null);
	String COLUMNNAME_M_HU_LUTU_Configuration_ID = "M_HU_LUTU_Configuration_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_M_HU_PI_Item_Product_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "M_HU_PI_Item_Product_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Inoutline Handler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_IolCandHandler_ID (int M_IolCandHandler_ID);

	/**
	 * Get Inoutline Handler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_IolCandHandler_ID();

	@Nullable de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler();

	void setM_IolCandHandler(@Nullable de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler);

	ModelColumn<I_M_ReceiptSchedule, de.metas.inoutcandidate.model.I_M_IolCandHandler> COLUMN_M_IolCandHandler_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "M_IolCandHandler_ID", de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
	String COLUMNNAME_M_IolCandHandler_ID = "M_IolCandHandler_ID";

	/**
	 * Set Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMovementDate (@Nullable java.sql.Timestamp MovementDate);

	/**
	 * Get Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getMovementDate();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_MovementDate = new ModelColumn<>(I_M_ReceiptSchedule.class, "MovementDate", null);
	String COLUMNNAME_MovementDate = "MovementDate";

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

	/**
	 * Set Material Receipt Candidates.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID);

	/**
	 * Get Material Receipt Candidates.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ReceiptSchedule_ID();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_M_ReceiptSchedule_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "M_ReceiptSchedule_ID", null);
	String COLUMNNAME_M_ReceiptSchedule_ID = "M_ReceiptSchedule_ID";

	/**
	 * Set Transportation Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transportation Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_ShipperTransportation_ID();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_M_ShipperTransportation_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "M_ShipperTransportation_ID", null);
	String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Set Destination warehouse locator.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID);

	/**
	 * Get Destination warehouse locator.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_Dest_ID();

	String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";

	/**
	 * Set Warehouse Eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_Warehouse_Effective_ID (int M_Warehouse_Effective_ID);

	/**
	 * Get Warehouse Eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_Warehouse_Effective_ID();

	String COLUMNNAME_M_Warehouse_Effective_ID = "M_Warehouse_Effective_ID";

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
	 * Set Alt. Warehouse.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_Override_ID (int M_Warehouse_Override_ID);

	/**
	 * Get Alt. Warehouse.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_Override_ID();

	String COLUMNNAME_M_Warehouse_Override_ID = "M_Warehouse_Override_ID";

	/**
	 * Set On material rcpt with target WH.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOnMaterialReceiptWithDestWarehouse (@Nullable java.lang.String OnMaterialReceiptWithDestWarehouse);

	/**
	 * Get On material rcpt with target WH.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOnMaterialReceiptWithDestWarehouse();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_OnMaterialReceiptWithDestWarehouse = new ModelColumn<>(I_M_ReceiptSchedule.class, "OnMaterialReceiptWithDestWarehouse", null);
	String COLUMNNAME_OnMaterialReceiptWithDestWarehouse = "OnMaterialReceiptWithDestWarehouse";

	/**
	 * Set Packings Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPackDescription (@Nullable java.lang.String PackDescription);

	/**
	 * Get Packings Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPackDescription();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_PackDescription = new ModelColumn<>(I_M_ReceiptSchedule.class, "PackDescription", null);
	String COLUMNNAME_PackDescription = "PackDescription";

	/**
	 * Set POD.
	 * Port of Discharge
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPOD_ID (int POD_ID);

	/**
	 * Get POD.
	 * Port of Discharge
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getPOD_ID();

	@Deprecated
	@Nullable org.compiere.model.I_C_Postal getPOD();

	@Deprecated
	void setPOD(@Nullable org.compiere.model.I_C_Postal POD);

	ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_Postal> COLUMN_POD_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "POD_ID", org.compiere.model.I_C_Postal.class);
	String COLUMNNAME_POD_ID = "POD_ID";

	/**
	 * Set POL.
	 * Port of Loading
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPOL_ID (int POL_ID);

	/**
	 * Get POL.
	 * Port of Loading
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getPOL_ID();

	@Deprecated
	@Nullable org.compiere.model.I_C_Postal getPOL();

	@Deprecated
	void setPOL(@Nullable org.compiere.model.I_C_Postal POL);

	ModelColumn<I_M_ReceiptSchedule, org.compiere.model.I_C_Postal> COLUMN_POL_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "POL_ID", org.compiere.model.I_C_Postal.class);
	String COLUMNNAME_POL_ID = "POL_ID";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_POReference = new ModelColumn<>(I_M_ReceiptSchedule.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Preparation Time.
	 * Preparation Time
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationTime (@Nullable java.sql.Timestamp PreparationTime);

	/**
	 * Get Preparation Time.
	 * Preparation Time
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationTime();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_PreparationTime = new ModelColumn<>(I_M_ReceiptSchedule.class, "PreparationTime", null);
	String COLUMNNAME_PreparationTime = "PreparationTime";

	/**
	 * Set Priority.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriorityRule (@Nullable java.lang.String PriorityRule);

	/**
	 * Get Priority.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPriorityRule();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_PriorityRule = new ModelColumn<>(I_M_ReceiptSchedule.class, "PriorityRule", null);
	String COLUMNNAME_PriorityRule = "PriorityRule";

	/**
	 * Set Priority override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriorityRule_Override (@Nullable java.lang.String PriorityRule_Override);

	/**
	 * Get Priority override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPriorityRule_Override();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_PriorityRule_Override = new ModelColumn<>(I_M_ReceiptSchedule.class, "PriorityRule_Override", null);
	String COLUMNNAME_PriorityRule_Override = "PriorityRule_Override";

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

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Processed = new ModelColumn<>(I_M_ReceiptSchedule.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosed (boolean IsClosed);

	/**
	 * Get Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isIsClosed();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_IsClosed = new ModelColumn<>(I_M_ReceiptSchedule.class, "IsClosed", null);
	String COLUMNNAME_IsClosed = "IsClosed";

	/**
	 * Set Packaging capacity.
	 * Capacity in the respective product's unit of measuerement
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyItemCapacity (@Nullable BigDecimal QtyItemCapacity);

	/**
	 * Get Packaging capacity.
	 * Capacity in the respective product's unit of measuerement
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyItemCapacity();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyItemCapacity = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyItemCapacity", null);
	String COLUMNNAME_QtyItemCapacity = "QtyItemCapacity";

	/**
	 * Set Moved Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyMoved (@Nullable BigDecimal QtyMoved);

	/**
	 * Get Moved Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyMoved();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMoved = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyMoved", null);
	String COLUMNNAME_QtyMoved = "QtyMoved";

	/**
	 * Set Moved catch quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyMovedInCatchUOM (@Nullable BigDecimal QtyMovedInCatchUOM);

	/**
	 * Get Moved catch quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyMovedInCatchUOM();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMovedInCatchUOM = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyMovedInCatchUOM", null);
	String COLUMNNAME_QtyMovedInCatchUOM = "QtyMovedInCatchUOM";

	/**
	 * Set Moved TU Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setQtyMovedTU (@Nullable BigDecimal QtyMovedTU);

	/**
	 * Get Moved TU Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getQtyMovedTU();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMovedTU = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyMovedTU", null);
	String COLUMNNAME_QtyMovedTU = "QtyMovedTU";

	/**
	 * Set Moved Qty (With Issues).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyMovedWithIssues (BigDecimal QtyMovedWithIssues);

	/**
	 * Get Moved Qty (With Issues).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyMovedWithIssues();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMovedWithIssues = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyMovedWithIssues", null);
	String COLUMNNAME_QtyMovedWithIssues = "QtyMovedWithIssues";

	/**
	 * Set Moved catch quantity with issues.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyMovedWithIssuesInCatchUOM (@Nullable BigDecimal QtyMovedWithIssuesInCatchUOM);

	/**
	 * Get Moved catch quantity with issues.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyMovedWithIssuesInCatchUOM();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyMovedWithIssuesInCatchUOM = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyMovedWithIssuesInCatchUOM", null);
	String COLUMNNAME_QtyMovedWithIssuesInCatchUOM = "QtyMovedWithIssuesInCatchUOM";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (@Nullable BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Over-/ Under Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrderedOverUnder (@Nullable BigDecimal QtyOrderedOverUnder);

	/**
	 * Get Over-/ Under Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrderedOverUnder();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyOrderedOverUnder = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyOrderedOverUnder", null);
	String COLUMNNAME_QtyOrderedOverUnder = "QtyOrderedOverUnder";

	/**
	 * Set Qty Ordered TU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setQtyOrderedTU (@Nullable BigDecimal QtyOrderedTU);

	/**
	 * Get Qty Ordered TU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getQtyOrderedTU();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyOrderedTU = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyOrderedTU", null);
	String COLUMNNAME_QtyOrderedTU = "QtyOrderedTU";

	/**
	 * Set Quantity to move.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToMove (@Nullable BigDecimal QtyToMove);

	/**
	 * Get Quantity to move.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToMove();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyToMove = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyToMove", null);
	String COLUMNNAME_QtyToMove = "QtyToMove";

	/**
	 * Set Movement Qty override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToMove_Override (@Nullable BigDecimal QtyToMove_Override);

	/**
	 * Get Movement Qty override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToMove_Override();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QtyToMove_Override = new ModelColumn<>(I_M_ReceiptSchedule.class, "QtyToMove_Override", null);
	String COLUMNNAME_QtyToMove_Override = "QtyToMove_Override";

	/**
	 * Set Qualitydiscount %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQualityDiscountPercent (@Nullable BigDecimal QualityDiscountPercent);

	/**
	 * Get Qualitydiscount %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQualityDiscountPercent();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QualityDiscountPercent = new ModelColumn<>(I_M_ReceiptSchedule.class, "QualityDiscountPercent", null);
	String COLUMNNAME_QualityDiscountPercent = "QualityDiscountPercent";

	/**
	 * Set Qualitynote.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQualityNote (@Nullable java.lang.String QualityNote);

	/**
	 * Get Qualitynote.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQualityNote();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_QualityNote = new ModelColumn<>(I_M_ReceiptSchedule.class, "QualityNote", null);
	String COLUMNNAME_QualityNote = "QualityNote";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Record_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Shipper Partner.
	 * This refers to the freight forwarder handling the transportation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setShipper_BPartner_ID (int Shipper_BPartner_ID);

	/**
	 * Get Shipper Partner.
	 * This refers to the freight forwarder handling the transportation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getShipper_BPartner_ID();

	String COLUMNNAME_Shipper_BPartner_ID = "Shipper_BPartner_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStatus (@Nullable java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStatus();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Status = new ModelColumn<>(I_M_ReceiptSchedule.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Set Tracking No.
	 * Tracking ID of the shipment
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setTrackingID (@Nullable java.lang.String TrackingID);

	/**
	 * Get Tracking No.
	 * Tracking ID of the shipment
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getTrackingID();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_TrackingID = new ModelColumn<>(I_M_ReceiptSchedule.class, "TrackingID", null);
	String COLUMNNAME_TrackingID = "TrackingID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_Updated = new ModelColumn<>(I_M_ReceiptSchedule.class, "Updated", null);
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
	 * Set UserElementString1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString1 (@Nullable java.lang.String UserElementString1);

	/**
	 * Get UserElementString1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString1();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_M_ReceiptSchedule.class, "UserElementString1", null);
	String COLUMNNAME_UserElementString1 = "UserElementString1";

	/**
	 * Set UserElementString2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString2 (@Nullable java.lang.String UserElementString2);

	/**
	 * Get UserElementString2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString2();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_M_ReceiptSchedule.class, "UserElementString2", null);
	String COLUMNNAME_UserElementString2 = "UserElementString2";

	/**
	 * Set UserElementString3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString3 (@Nullable java.lang.String UserElementString3);

	/**
	 * Get UserElementString3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString3();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_M_ReceiptSchedule.class, "UserElementString3", null);
	String COLUMNNAME_UserElementString3 = "UserElementString3";

	/**
	 * Set UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString4 (@Nullable java.lang.String UserElementString4);

	/**
	 * Get UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString4();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_M_ReceiptSchedule.class, "UserElementString4", null);
	String COLUMNNAME_UserElementString4 = "UserElementString4";

	/**
	 * Set UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString5 (@Nullable java.lang.String UserElementString5);

	/**
	 * Get UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString5();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_M_ReceiptSchedule.class, "UserElementString5", null);
	String COLUMNNAME_UserElementString5 = "UserElementString5";

	/**
	 * Set UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString6 (@Nullable java.lang.String UserElementString6);

	/**
	 * Get UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString6();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_M_ReceiptSchedule.class, "UserElementString6", null);
	String COLUMNNAME_UserElementString6 = "UserElementString6";

	/**
	 * Set UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString7 (@Nullable java.lang.String UserElementString7);

	/**
	 * Get UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString7();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_M_ReceiptSchedule.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";

	/**
	 * Set Vessel Name.
	 * Name of the ship
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setVesselName (@Nullable java.lang.String VesselName);

	/**
	 * Get Vessel Name.
	 * Name of the ship
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getVesselName();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_VesselName = new ModelColumn<>(I_M_ReceiptSchedule.class, "VesselName", null);
	String COLUMNNAME_VesselName = "VesselName";

	/**
	 * Set External Header ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalHeaderId (@Nullable String ExternalHeaderId);

	/**
	 * Get External Header ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getExternalHeaderId();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ExternalHeaderId = new ModelColumn<>(I_M_ReceiptSchedule.class, "ExternalHeaderId", null);
	String COLUMNNAME_ExternalHeaderId = "ExternalHeaderId";

	/**
	 * Set External Line ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalLineId (@Nullable String ExternalLineId);

	/**
	 * Get External Line ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getExternalLineId();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ExternalLineId = new ModelColumn<>(I_M_ReceiptSchedule.class, "ExternalLineId", null);
	String COLUMNNAME_ExternalLineId = "ExternalLineId";



	/**
	 * Set External System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_ID (int ExternalSystem_ID);

	/**
	 * Get External System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_ID();

	ModelColumn<I_M_ReceiptSchedule, Object> COLUMN_ExternalSystem_ID = new ModelColumn<>(I_M_ReceiptSchedule.class, "ExternalSystem_ID", null);
	String COLUMNNAME_ExternalSystem_ID = "ExternalSystem_ID";
}
