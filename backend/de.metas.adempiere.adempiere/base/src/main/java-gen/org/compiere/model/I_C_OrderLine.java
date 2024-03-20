package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_OrderLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_OrderLine 
{

	String Table_Name = "C_OrderLine";

//	/** AD_Table_ID=260 */
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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Pricing system.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBase_PricingSystem_ID (int Base_PricingSystem_ID);

	/**
	 * Get Pricing system.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBase_PricingSystem_ID();

	String COLUMNNAME_Base_PricingSystem_ID = "Base_PricingSystem_ID";

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

	ModelColumn<I_C_OrderLine, Object> COLUMN_BPartnerAddress = new ModelColumn<>(I_C_OrderLine.class, "BPartnerAddress", null);
	String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/**
	 * Set BPartner Qty Item Capacity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartner_QtyItemCapacity (@Nullable BigDecimal BPartner_QtyItemCapacity);

	/**
	 * Get BPartner Qty Item Capacity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getBPartner_QtyItemCapacity();

	ModelColumn<I_C_OrderLine, Object> COLUMN_BPartner_QtyItemCapacity = new ModelColumn<>(I_C_OrderLine.class, "BPartner_QtyItemCapacity", null);
	String COLUMNNAME_BPartner_QtyItemCapacity = "BPartner_QtyItemCapacity";

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
	 * Set Business Partner (2).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner2_ID (int C_BPartner2_ID);

	/**
	 * Get Business Partner (2).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner2_ID();

	String COLUMNNAME_C_BPartner2_ID = "C_BPartner2_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Standort (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_Value_ID (int C_BPartner_Location_Value_ID);

	/**
	 * Get Standort (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_Value_ID();

	@Nullable org.compiere.model.I_C_Location getC_BPartner_Location_Value();

	void setC_BPartner_Location_Value(@Nullable org.compiere.model.I_C_Location C_BPartner_Location_Value);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Location> COLUMN_C_BPartner_Location_Value_ID = new ModelColumn<>(I_C_OrderLine.class, "C_BPartner_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_BPartner_Location_Value_ID = "C_BPartner_Location_Value_ID";

	/**
	 * Set C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID);

	/**
	 * Get C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Vendor_ID();

	String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";

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

	ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_C_OrderLine.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Compensations.
	 * Compensation lines are lines which are added at the bottom of the group (when created or updated) in order to apply discounts or surcharges.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_SchemaLine_ID (int C_CompensationGroup_SchemaLine_ID);

	/**
	 * Get Compensations.
	 * Compensation lines are lines which are added at the bottom of the group (when created or updated) in order to apply discounts or surcharges.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_SchemaLine_ID();

	@Nullable de.metas.order.model.I_C_CompensationGroup_SchemaLine getC_CompensationGroup_SchemaLine();

	void setC_CompensationGroup_SchemaLine(@Nullable de.metas.order.model.I_C_CompensationGroup_SchemaLine C_CompensationGroup_SchemaLine);

	ModelColumn<I_C_OrderLine, de.metas.order.model.I_C_CompensationGroup_SchemaLine> COLUMN_C_CompensationGroup_SchemaLine_ID = new ModelColumn<>(I_C_OrderLine.class, "C_CompensationGroup_SchemaLine_ID", de.metas.order.model.I_C_CompensationGroup_SchemaLine.class);
	String COLUMNNAME_C_CompensationGroup_SchemaLine_ID = "C_CompensationGroup_SchemaLine_ID";

	/**
	 * Set Template Lines.
	 * Template lines are added automatically when using order batch entry with a product which is defined to trigger a compensation group creation
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_Schema_TemplateLine_ID (int C_CompensationGroup_Schema_TemplateLine_ID);

	/**
	 * Get Template Lines.
	 * Template lines are added automatically when using order batch entry with a product which is defined to trigger a compensation group creation
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_Schema_TemplateLine_ID();

	@Nullable de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine getC_CompensationGroup_Schema_TemplateLine();

	void setC_CompensationGroup_Schema_TemplateLine(@Nullable de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine C_CompensationGroup_Schema_TemplateLine);

	ModelColumn<I_C_OrderLine, de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine> COLUMN_C_CompensationGroup_Schema_TemplateLine_ID = new ModelColumn<>(I_C_OrderLine.class, "C_CompensationGroup_Schema_TemplateLine_ID", de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine.class);
	String COLUMNNAME_C_CompensationGroup_Schema_TemplateLine_ID = "C_CompensationGroup_Schema_TemplateLine_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Contract Terms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Contract Terms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

	ModelColumn<I_C_OrderLine, Object> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(I_C_OrderLine.class, "C_Flatrate_Conditions_ID", null);
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

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

	ModelColumn<I_C_OrderLine, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_C_OrderLine.class, "C_Flatrate_Term_ID", null);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Order Compensation Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_CompensationGroup_ID (int C_Order_CompensationGroup_ID);

	/**
	 * Get Order Compensation Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_CompensationGroup_ID();

	@Nullable org.compiere.model.I_C_Order_CompensationGroup getC_Order_CompensationGroup();

	void setC_Order_CompensationGroup(@Nullable org.compiere.model.I_C_Order_CompensationGroup C_Order_CompensationGroup);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Order_CompensationGroup> COLUMN_C_Order_CompensationGroup_ID = new ModelColumn<>(I_C_OrderLine.class, "C_Order_CompensationGroup_ID", org.compiere.model.I_C_Order_CompensationGroup.class);
	String COLUMNNAME_C_Order_CompensationGroup_ID = "C_Order_CompensationGroup_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	org.compiere.model.I_C_Order getC_Order();

	void setC_Order(org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_OrderLine.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	ModelColumn<I_C_OrderLine, Object> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_C_OrderLine.class, "C_OrderLine_ID", null);
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

	ModelColumn<I_C_OrderLine, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_C_OrderLine.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Set Zahlungsbedingung abw..
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_Override_ID (int C_PaymentTerm_Override_ID);

	/**
	 * Get Zahlungsbedingung abw..
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_Override_ID();

	String COLUMNNAME_C_PaymentTerm_Override_ID = "C_PaymentTerm_Override_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectPhase_ID (int C_ProjectPhase_ID);

	/**
	 * Get Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectPhase_ID();

	@Nullable org.compiere.model.I_C_ProjectPhase getC_ProjectPhase();

	void setC_ProjectPhase(@Nullable org.compiere.model.I_C_ProjectPhase C_ProjectPhase);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_C_ProjectPhase> COLUMN_C_ProjectPhase_ID = new ModelColumn<>(I_C_OrderLine.class, "C_ProjectPhase_ID", org.compiere.model.I_C_ProjectPhase.class);
	String COLUMNNAME_C_ProjectPhase_ID = "C_ProjectPhase_ID";

	/**
	 * Set Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectTask_ID (int C_ProjectTask_ID);

	/**
	 * Get Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectTask_ID();

	@Nullable org.compiere.model.I_C_ProjectTask getC_ProjectTask();

	void setC_ProjectTask(@Nullable org.compiere.model.I_C_ProjectTask C_ProjectTask);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_C_ProjectTask> COLUMN_C_ProjectTask_ID = new ModelColumn<>(I_C_OrderLine.class, "C_ProjectTask_ID", org.compiere.model.I_C_ProjectTask.class);
	String COLUMNNAME_C_ProjectTask_ID = "C_ProjectTask_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_OrderLine, Object> COLUMN_Created = new ModelColumn<>(I_C_OrderLine.class, "Created", null);
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
	 * Set Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_TaxCategory_ID();

	String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Set Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set BPartner UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_BPartner_ID (int C_UOM_BPartner_ID);

	/**
	 * Get BPartner UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_BPartner_ID();

	String COLUMNNAME_C_UOM_BPartner_ID = "C_UOM_BPartner_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set VAT Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_VAT_Code_ID (int C_VAT_Code_ID);

	/**
	 * Get VAT Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_VAT_Code_ID();

	ModelColumn<I_C_OrderLine, Object> COLUMN_C_VAT_Code_ID = new ModelColumn<>(I_C_OrderLine.class, "C_VAT_Code_ID", null);
	String COLUMNNAME_C_VAT_Code_ID = "C_VAT_Code_ID";

	/**
	 * Set Date Delivered.
	 * Date when the product was delivered
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateDelivered (@Nullable java.sql.Timestamp DateDelivered);

	/**
	 * Get Date Delivered.
	 * Date when the product was delivered
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateDelivered();

	ModelColumn<I_C_OrderLine, Object> COLUMN_DateDelivered = new ModelColumn<>(I_C_OrderLine.class, "DateDelivered", null);
	String COLUMNNAME_DateDelivered = "DateDelivered";

	/**
	 * Set Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateInvoiced (@Nullable java.sql.Timestamp DateInvoiced);

	/**
	 * Get Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateInvoiced();

	ModelColumn<I_C_OrderLine, Object> COLUMN_DateInvoiced = new ModelColumn<>(I_C_OrderLine.class, "DateInvoiced", null);
	String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateOrdered();

	ModelColumn<I_C_OrderLine, Object> COLUMN_DateOrdered = new ModelColumn<>(I_C_OrderLine.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Date Promised From.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatePromised (@Nullable java.sql.Timestamp DatePromised);

	/**
	 * Get Date Promised From.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDatePromised();

	ModelColumn<I_C_OrderLine, Object> COLUMN_DatePromised = new ModelColumn<>(I_C_OrderLine.class, "DatePromised", null);
	String COLUMNNAME_DatePromised = "DatePromised";

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

	ModelColumn<I_C_OrderLine, Object> COLUMN_Description = new ModelColumn<>(I_C_OrderLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscount (@Nullable BigDecimal Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount();

	ModelColumn<I_C_OrderLine, Object> COLUMN_Discount = new ModelColumn<>(I_C_OrderLine.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Enforce price limit.
	 * Do not allow prices below the limit price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEnforcePriceLimit (boolean EnforcePriceLimit);

	/**
	 * Get Enforce price limit.
	 * Do not allow prices below the limit price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEnforcePriceLimit();

	ModelColumn<I_C_OrderLine, Object> COLUMN_EnforcePriceLimit = new ModelColumn<>(I_C_OrderLine.class, "EnforcePriceLimit", null);
	String COLUMNNAME_EnforcePriceLimit = "EnforcePriceLimit";

	/**
	 * Set Exploded From BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExplodedFrom_BOMLine_ID (int ExplodedFrom_BOMLine_ID);

	/**
	 * Get Exploded From BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExplodedFrom_BOMLine_ID();

	@Nullable org.eevolution.model.I_PP_Product_BOMLine getExplodedFrom_BOMLine();

	void setExplodedFrom_BOMLine(@Nullable org.eevolution.model.I_PP_Product_BOMLine ExplodedFrom_BOMLine);

	ModelColumn<I_C_OrderLine, org.eevolution.model.I_PP_Product_BOMLine> COLUMN_ExplodedFrom_BOMLine_ID = new ModelColumn<>(I_C_OrderLine.class, "ExplodedFrom_BOMLine_ID", org.eevolution.model.I_PP_Product_BOMLine.class);
	String COLUMNNAME_ExplodedFrom_BOMLine_ID = "ExplodedFrom_BOMLine_ID";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_C_OrderLine, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_OrderLine.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set ExternalSeqNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSeqNo (int ExternalSeqNo);

	/**
	 * Get ExternalSeqNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExternalSeqNo();

	ModelColumn<I_C_OrderLine, Object> COLUMN_ExternalSeqNo = new ModelColumn<>(I_C_OrderLine.class, "ExternalSeqNo", null);
	String COLUMNNAME_ExternalSeqNo = "ExternalSeqNo";

	/**
	 * Set Freight Amount.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFreightAmt (BigDecimal FreightAmt);

	/**
	 * Get Freight Amount.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getFreightAmt();

	ModelColumn<I_C_OrderLine, Object> COLUMN_FreightAmt = new ModelColumn<>(I_C_OrderLine.class, "FreightAmt", null);
	String COLUMNNAME_FreightAmt = "FreightAmt";

	/**
	 * Set Frequency Type.
	 * Frequency of event
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFrequencyType (java.lang.String FrequencyType);

	/**
	 * Get Frequency Type.
	 * Frequency of event
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFrequencyType();

	ModelColumn<I_C_OrderLine, Object> COLUMN_FrequencyType = new ModelColumn<>(I_C_OrderLine.class, "FrequencyType", null);
	String COLUMNNAME_FrequencyType = "FrequencyType";

	/**
	 * Set Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationAmtType (@Nullable java.lang.String GroupCompensationAmtType);

	/**
	 * Get Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGroupCompensationAmtType();

	ModelColumn<I_C_OrderLine, Object> COLUMN_GroupCompensationAmtType = new ModelColumn<>(I_C_OrderLine.class, "GroupCompensationAmtType", null);
	String COLUMNNAME_GroupCompensationAmtType = "GroupCompensationAmtType";

	/**
	 * Set Compensation base amount.
	 * Base amount for calculating percentage group compensation
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationBaseAmt (@Nullable BigDecimal GroupCompensationBaseAmt);

	/**
	 * Get Compensation base amount.
	 * Base amount for calculating percentage group compensation
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getGroupCompensationBaseAmt();

	ModelColumn<I_C_OrderLine, Object> COLUMN_GroupCompensationBaseAmt = new ModelColumn<>(I_C_OrderLine.class, "GroupCompensationBaseAmt", null);
	String COLUMNNAME_GroupCompensationBaseAmt = "GroupCompensationBaseAmt";

	/**
	 * Set Compensation percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationPercentage (@Nullable BigDecimal GroupCompensationPercentage);

	/**
	 * Get Compensation percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getGroupCompensationPercentage();

	ModelColumn<I_C_OrderLine, Object> COLUMN_GroupCompensationPercentage = new ModelColumn<>(I_C_OrderLine.class, "GroupCompensationPercentage", null);
	String COLUMNNAME_GroupCompensationPercentage = "GroupCompensationPercentage";

	/**
	 * Set Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationType (@Nullable java.lang.String GroupCompensationType);

	/**
	 * Get Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGroupCompensationType();

	ModelColumn<I_C_OrderLine, Object> COLUMN_GroupCompensationType = new ModelColumn<>(I_C_OrderLine.class, "GroupCompensationType", null);
	String COLUMNNAME_GroupCompensationType = "GroupCompensationType";

	/**
	 * Set Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicableQtyBasedOn (java.lang.String InvoicableQtyBasedOn);

	/**
	 * Get Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoicableQtyBasedOn();

	ModelColumn<I_C_OrderLine, Object> COLUMN_InvoicableQtyBasedOn = new ModelColumn<>(I_C_OrderLine.class, "InvoicableQtyBasedOn", null);
	String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";

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

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_OrderLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set CP.
	 * Campaign Price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCampaignPrice (boolean IsCampaignPrice);

	/**
	 * Get CP.
	 * Campaign Price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCampaignPrice();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsCampaignPrice = new ModelColumn<>(I_C_OrderLine.class, "IsCampaignPrice", null);
	String COLUMNNAME_IsCampaignPrice = "IsCampaignPrice";

	/**
	 * Set Delivery Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDeliveryClosed (boolean IsDeliveryClosed);

	/**
	 * Get Delivery Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDeliveryClosed();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsDeliveryClosed = new ModelColumn<>(I_C_OrderLine.class, "IsDeliveryClosed", null);
	String COLUMNNAME_IsDeliveryClosed = "IsDeliveryClosed";

	/**
	 * Set Description Only.
	 * if true, the line is just description and no transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDescription (boolean IsDescription);

	/**
	 * Get Description Only.
	 * if true, the line is just description and no transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDescription();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsDescription = new ModelColumn<>(I_C_OrderLine.class, "IsDescription", null);
	String COLUMNNAME_IsDescription = "IsDescription";

	/**
	 * Set Discount Editable.
	 * Allow user to change the discount
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDiscountEditable (boolean IsDiscountEditable);

	/**
	 * Get Discount Editable.
	 * Allow user to change the discount
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDiscountEditable();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsDiscountEditable = new ModelColumn<>(I_C_OrderLine.class, "IsDiscountEditable", null);
	String COLUMNNAME_IsDiscountEditable = "IsDiscountEditable";

	/**
	 * Set Group Discount Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsGroupCompensationLine (boolean IsGroupCompensationLine);

	/**
	 * Get Group Discount Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isGroupCompensationLine();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsGroupCompensationLine = new ModelColumn<>(I_C_OrderLine.class, "IsGroupCompensationLine", null);
	String COLUMNNAME_IsGroupCompensationLine = "IsGroupCompensationLine";

	/**
	 * Set Hide when printing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsHideWhenPrinting (boolean IsHideWhenPrinting);

	/**
	 * Get Hide when printing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHideWhenPrinting();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsHideWhenPrinting = new ModelColumn<>(I_C_OrderLine.class, "IsHideWhenPrinting", null);
	String COLUMNNAME_IsHideWhenPrinting = "IsHideWhenPrinting";

	/**
	 * Set Prod.-Beschr. ändern.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsIndividualDescription (boolean IsIndividualDescription);

	/**
	 * Get Prod.-Beschr. ändern.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isIndividualDescription();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsIndividualDescription = new ModelColumn<>(I_C_OrderLine.class, "IsIndividualDescription", null);
	String COLUMNNAME_IsIndividualDescription = "IsIndividualDescription";

	/**
	 * Set Discount Manual.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManualDiscount (boolean IsManualDiscount);

	/**
	 * Get Discount Manual.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManualDiscount();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsManualDiscount = new ModelColumn<>(I_C_OrderLine.class, "IsManualDiscount", null);
	String COLUMNNAME_IsManualDiscount = "IsManualDiscount";

	/**
	 * Set Manual payment term.
	 * The current payment term was set by a user and shall not be overwritten by the system.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManualPaymentTerm (boolean IsManualPaymentTerm);

	/**
	 * Get Manual payment term.
	 * The current payment term was set by a user and shall not be overwritten by the system.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManualPaymentTerm();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsManualPaymentTerm = new ModelColumn<>(I_C_OrderLine.class, "IsManualPaymentTerm", null);
	String COLUMNNAME_IsManualPaymentTerm = "IsManualPaymentTerm";

	/**
	 * Set Manual Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManualPrice (boolean IsManualPrice);

	/**
	 * Get Manual Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManualPrice();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsManualPrice = new ModelColumn<>(I_C_OrderLine.class, "IsManualPrice", null);
	String COLUMNNAME_IsManualPrice = "IsManualPrice";

	/**
	 * Set Goods on consignment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOnConsignment (boolean IsOnConsignment);

	/**
	 * Get Goods on consignment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnConsignment();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsOnConsignment = new ModelColumn<>(I_C_OrderLine.class, "IsOnConsignment", null);
	String COLUMNNAME_IsOnConsignment = "IsOnConsignment";

	/**
	 * Set Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsPackagingMaterial (boolean IsPackagingMaterial);

	/**
	 * Get Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPackagingMaterial();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsPackagingMaterial = new ModelColumn<>(I_C_OrderLine.class, "IsPackagingMaterial", null);
	String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";

	/**
	 * Set Price Editable.
	 * Allow user to change the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPriceEditable (boolean IsPriceEditable);

	/**
	 * Get Price Editable.
	 * Allow user to change the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPriceEditable();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsPriceEditable = new ModelColumn<>(I_C_OrderLine.class, "IsPriceEditable", null);
	String COLUMNNAME_IsPriceEditable = "IsPriceEditable";

	/**
	 * Set Abo.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSubscription (boolean IsSubscription);

	/**
	 * Get Abo.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSubscription();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsSubscription = new ModelColumn<>(I_C_OrderLine.class, "IsSubscription", null);
	String COLUMNNAME_IsSubscription = "IsSubscription";

	/**
	 * Set Temporary pricing conditions.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTempPricingConditions (boolean IsTempPricingConditions);

	/**
	 * Get Temporary pricing conditions.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTempPricingConditions();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsTempPricingConditions = new ModelColumn<>(I_C_OrderLine.class, "IsTempPricingConditions", null);
	String COLUMNNAME_IsTempPricingConditions = "IsTempPricingConditions";

	/**
	 * Set Benutze abw. Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsUseBPartnerAddress (boolean IsUseBPartnerAddress);

	/**
	 * Get Benutze abw. Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isUseBPartnerAddress();

	ModelColumn<I_C_OrderLine, Object> COLUMN_IsUseBPartnerAddress = new ModelColumn<>(I_C_OrderLine.class, "IsUseBPartnerAddress", null);
	String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

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

	ModelColumn<I_C_OrderLine, Object> COLUMN_Line = new ModelColumn<>(I_C_OrderLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLineNetAmt (BigDecimal LineNetAmt);

	/**
	 * Get Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getLineNetAmt();

	ModelColumn<I_C_OrderLine, Object> COLUMN_LineNetAmt = new ModelColumn<>(I_C_OrderLine.class, "LineNetAmt", null);
	String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Manual Compensation Line Position.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setManualCompensationLinePosition (@Nullable java.lang.String ManualCompensationLinePosition);

	/**
	 * Get Manual Compensation Line Position.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getManualCompensationLinePosition();

	ModelColumn<I_C_OrderLine, Object> COLUMN_ManualCompensationLinePosition = new ModelColumn<>(I_C_OrderLine.class, "ManualCompensationLinePosition", null);
	String COLUMNNAME_ManualCompensationLinePosition = "ManualCompensationLinePosition";

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

	ModelColumn<I_C_OrderLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_OrderLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Discount Schema Break.
	 * Trade Discount Break
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID);

	/**
	 * Get Discount Schema Break.
	 * Trade Discount Break
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchemaBreak_ID();

	@Nullable org.compiere.model.I_M_DiscountSchemaBreak getM_DiscountSchemaBreak();

	void setM_DiscountSchemaBreak(@Nullable org.compiere.model.I_M_DiscountSchemaBreak M_DiscountSchemaBreak);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_M_DiscountSchemaBreak> COLUMN_M_DiscountSchemaBreak_ID = new ModelColumn<>(I_C_OrderLine.class, "M_DiscountSchemaBreak_ID", org.compiere.model.I_M_DiscountSchemaBreak.class);
	String COLUMNNAME_M_DiscountSchemaBreak_ID = "M_DiscountSchemaBreak_ID";

	/**
	 * Set Discount Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Discount Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchema_ID();

	@Nullable org.compiere.model.I_M_DiscountSchema getM_DiscountSchema();

	void setM_DiscountSchema(@Nullable org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new ModelColumn<>(I_C_OrderLine.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
	String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Price List Version.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Price List Version.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_Version_ID();

	String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	/**
	 * Set Product note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_DocumentNote (@Nullable java.lang.String M_Product_DocumentNote);

	/**
	 * Get Product note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getM_Product_DocumentNote();

	ModelColumn<I_C_OrderLine, Object> COLUMN_M_Product_DocumentNote = new ModelColumn<>(I_C_OrderLine.class, "M_Product_DocumentNote", null);
	String COLUMNNAME_M_Product_DocumentNote = "M_Product_DocumentNote";

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
	 * Set Promotion.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Promotion_ID (int M_Promotion_ID);

	/**
	 * Get Promotion.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Promotion_ID();

	@Nullable org.compiere.model.I_M_Promotion getM_Promotion();

	void setM_Promotion(@Nullable org.compiere.model.I_M_Promotion M_Promotion);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_M_Promotion> COLUMN_M_Promotion_ID = new ModelColumn<>(I_C_OrderLine.class, "M_Promotion_ID", org.compiere.model.I_M_Promotion.class);
	String COLUMNNAME_M_Promotion_ID = "M_Promotion_ID";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_OrderLine.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	@Nullable org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(@Nullable org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_C_OrderLine.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

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
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set No Price Conditions Indicator.
	 * Red means that mandatory pricing conditions are missing;
 yellow means that temporary pricing conditions were created just for the respective position.
	 *
	 * <br>Type: Color
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNoPriceConditionsColor_ID (int NoPriceConditionsColor_ID);

	/**
	 * Get No Price Conditions Indicator.
	 * Red means that mandatory pricing conditions are missing;
 yellow means that temporary pricing conditions were created just for the respective position.
	 *
	 * <br>Type: Color
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getNoPriceConditionsColor_ID();

	ModelColumn<I_C_OrderLine, Object> COLUMN_NoPriceConditionsColor_ID = new ModelColumn<>(I_C_OrderLine.class, "NoPriceConditionsColor_ID", null);
	String COLUMNNAME_NoPriceConditionsColor_ID = "NoPriceConditionsColor_ID";

	/**
	 * Set Gesamtauftragsrabbat.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderDiscount (@Nullable BigDecimal OrderDiscount);

	/**
	 * Get Gesamtauftragsrabbat.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOrderDiscount();

	ModelColumn<I_C_OrderLine, Object> COLUMN_OrderDiscount = new ModelColumn<>(I_C_OrderLine.class, "OrderDiscount", null);
	String COLUMNNAME_OrderDiscount = "OrderDiscount";

	/**
	 * Set Minimum Order Qty.
	 * Minimum order quantity in UOM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrder_Min (@Nullable BigDecimal Order_Min);

	/**
	 * Get Minimum Order Qty.
	 * Minimum order quantity in UOM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOrder_Min();

	ModelColumn<I_C_OrderLine, Object> COLUMN_Order_Min = new ModelColumn<>(I_C_OrderLine.class, "Order_Min", null);
	String COLUMNNAME_Order_Min = "Order_Min";

	/**
	 * Set Payment Discount %.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentDiscount (@Nullable BigDecimal PaymentDiscount);

	/**
	 * Get Payment Discount %.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPaymentDiscount();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PaymentDiscount = new ModelColumn<>(I_C_OrderLine.class, "PaymentDiscount", null);
	String COLUMNNAME_PaymentDiscount = "PaymentDiscount";

	/**
	 * Set Abrufauftragsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOCallOrderDate (@Nullable java.sql.Timestamp POCallOrderDate);

	/**
	 * Get Abrufauftragsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPOCallOrderDate();

	ModelColumn<I_C_OrderLine, Object> COLUMN_POCallOrderDate = new ModelColumn<>(I_C_OrderLine.class, "POCallOrderDate", null);
	String COLUMNNAME_POCallOrderDate = "POCallOrderDate";

	/**
	 * Set Manufacturing Cost Collector.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID);

	/**
	 * Get Manufacturing Cost Collector.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Cost_Collector_ID();

	@Nullable org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector();

	void setPP_Cost_Collector(@Nullable org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector);

	ModelColumn<I_C_OrderLine, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new ModelColumn<>(I_C_OrderLine.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
	String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/**
	 * Set Preset Date Invoiced.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPresetDateInvoiced (@Nullable java.sql.Timestamp PresetDateInvoiced);

	/**
	 * Get Preset Date Invoiced.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPresetDateInvoiced();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PresetDateInvoiced = new ModelColumn<>(I_C_OrderLine.class, "PresetDateInvoiced", null);
	String COLUMNNAME_PresetDateInvoiced = "PresetDateInvoiced";

	/**
	 * Set Preset Date Shipped.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPresetDateShipped (@Nullable java.sql.Timestamp PresetDateShipped);

	/**
	 * Get Preset Date Shipped.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPresetDateShipped();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PresetDateShipped = new ModelColumn<>(I_C_OrderLine.class, "PresetDateShipped", null);
	String COLUMNNAME_PresetDateShipped = "PresetDateShipped";

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceActual (BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PriceActual = new ModelColumn<>(I_C_OrderLine.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Cost Price.
	 * Price per Unit of Measure including all indirect costs (Freight, etc.)
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceCost (@Nullable BigDecimal PriceCost);

	/**
	 * Get Cost Price.
	 * Price per Unit of Measure including all indirect costs (Freight, etc.)
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceCost();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PriceCost = new ModelColumn<>(I_C_OrderLine.class, "PriceCost", null);
	String COLUMNNAME_PriceCost = "PriceCost";

	/**
	 * Set Price imp..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceEntered (BigDecimal PriceEntered);

	/**
	 * Get Price imp..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceEntered();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PriceEntered = new ModelColumn<>(I_C_OrderLine.class, "PriceEntered", null);
	String COLUMNNAME_PriceEntered = "PriceEntered";

	/**
	 * Set Limit Price.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceLimit (BigDecimal PriceLimit);

	/**
	 * Get Limit Price.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceLimit();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PriceLimit = new ModelColumn<>(I_C_OrderLine.class, "PriceLimit", null);
	String COLUMNNAME_PriceLimit = "PriceLimit";

	/**
	 * Set Price Limit Note.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceLimitNote (@Nullable java.lang.String PriceLimitNote);

	/**
	 * Get Price Limit Note.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPriceLimitNote();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PriceLimitNote = new ModelColumn<>(I_C_OrderLine.class, "PriceLimitNote", null);
	String COLUMNNAME_PriceLimitNote = "PriceLimitNote";

	/**
	 * Set List Price.
	 * List Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceList (BigDecimal PriceList);

	/**
	 * Get List Price.
	 * List Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceList();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PriceList = new ModelColumn<>(I_C_OrderLine.class, "PriceList", null);
	String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Auszeichnungspreis (standard).
	 * Auszeichnungspreis (standard)
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceList_Std (@Nullable BigDecimal PriceList_Std);

	/**
	 * Get Auszeichnungspreis (standard).
	 * Auszeichnungspreis (standard)
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceList_Std();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PriceList_Std = new ModelColumn<>(I_C_OrderLine.class, "PriceList_Std", null);
	String COLUMNNAME_PriceList_Std = "PriceList_Std";

	/**
	 * Set Standard Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceStd (@Nullable BigDecimal PriceStd);

	/**
	 * Get Standard Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceStd();

	ModelColumn<I_C_OrderLine, Object> COLUMN_PriceStd = new ModelColumn<>(I_C_OrderLine.class, "PriceStd", null);
	String COLUMNNAME_PriceStd = "PriceStd";

	/**
	 * Set Price Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrice_UOM_ID (int Price_UOM_ID);

	/**
	 * Get Price Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPrice_UOM_ID();

	String COLUMNNAME_Price_UOM_ID = "Price_UOM_ID";

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

	ModelColumn<I_C_OrderLine, Object> COLUMN_Processed = new ModelColumn<>(I_C_OrderLine.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Product Description.
	 * Product Description
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductDescription (@Nullable java.lang.String ProductDescription);

	/**
	 * Get Product Description.
	 * Product Description
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductDescription();

	ModelColumn<I_C_OrderLine, Object> COLUMN_ProductDescription = new ModelColumn<>(I_C_OrderLine.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Gross Profit Price.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProfitPriceActual (@Nullable BigDecimal ProfitPriceActual);

	/**
	 * Get Gross Profit Price.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getProfitPriceActual();

	ModelColumn<I_C_OrderLine, Object> COLUMN_ProfitPriceActual = new ModelColumn<>(I_C_OrderLine.class, "ProfitPriceActual", null);
	String COLUMNNAME_ProfitPriceActual = "ProfitPriceActual";

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

	ModelColumn<I_C_OrderLine, Object> COLUMN_QtyDelivered = new ModelColumn<>(I_C_OrderLine.class, "QtyDelivered", null);
	String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered();

	ModelColumn<I_C_OrderLine, Object> COLUMN_QtyEntered = new ModelColumn<>(I_C_OrderLine.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Qty Entered In BPartner UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEnteredInBPartnerUOM (@Nullable BigDecimal QtyEnteredInBPartnerUOM);

	/**
	 * Get Qty Entered In BPartner UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEnteredInBPartnerUOM();

	ModelColumn<I_C_OrderLine, Object> COLUMN_QtyEnteredInBPartnerUOM = new ModelColumn<>(I_C_OrderLine.class, "QtyEnteredInBPartnerUOM", null);
	String COLUMNNAME_QtyEnteredInBPartnerUOM = "QtyEnteredInBPartnerUOM";

	/**
	 * Set Quantity in price unit.
	 * Bestellte Menge in Preiseinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEnteredInPriceUOM (@Nullable BigDecimal QtyEnteredInPriceUOM);

	/**
	 * Get Quantity in price unit.
	 * Bestellte Menge in Preiseinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEnteredInPriceUOM();

	ModelColumn<I_C_OrderLine, Object> COLUMN_QtyEnteredInPriceUOM = new ModelColumn<>(I_C_OrderLine.class, "QtyEnteredInPriceUOM", null);
	String COLUMNNAME_QtyEnteredInPriceUOM = "QtyEnteredInPriceUOM";

	/**
	 * Set Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyInvoiced (BigDecimal QtyInvoiced);

	/**
	 * Get Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInvoiced();

	ModelColumn<I_C_OrderLine, Object> COLUMN_QtyInvoiced = new ModelColumn<>(I_C_OrderLine.class, "QtyInvoiced", null);
	String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

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

	ModelColumn<I_C_OrderLine, Object> COLUMN_QtyItemCapacity = new ModelColumn<>(I_C_OrderLine.class, "QtyItemCapacity", null);
	String COLUMNNAME_QtyItemCapacity = "QtyItemCapacity";

	/**
	 * Set Lost Sales Qty.
	 * Quantity of potential sales
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyLostSales (BigDecimal QtyLostSales);

	/**
	 * Get Lost Sales Qty.
	 * Quantity of potential sales
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyLostSales();

	ModelColumn<I_C_OrderLine, Object> COLUMN_QtyLostSales = new ModelColumn<>(I_C_OrderLine.class, "QtyLostSales", null);
	String COLUMNNAME_QtyLostSales = "QtyLostSales";

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

	ModelColumn<I_C_OrderLine, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_C_OrderLine.class, "QtyOrdered", null);
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

	ModelColumn<I_C_OrderLine, Object> COLUMN_QtyOrderedOverUnder = new ModelColumn<>(I_C_OrderLine.class, "QtyOrderedOverUnder", null);
	String COLUMNNAME_QtyOrderedOverUnder = "QtyOrderedOverUnder";

	/**
	 * Set Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyReserved (BigDecimal QtyReserved);

	/**
	 * Get Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReserved();

	ModelColumn<I_C_OrderLine, Object> COLUMN_QtyReserved = new ModelColumn<>(I_C_OrderLine.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Referenced Order Line.
	 * Reference to corresponding Sales/Purchase Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_OrderLine_ID (int Ref_OrderLine_ID);

	/**
	 * Get Referenced Order Line.
	 * Reference to corresponding Sales/Purchase Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getRef_OrderLine();

	void setRef_OrderLine(@Nullable org.compiere.model.I_C_OrderLine Ref_OrderLine);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_C_OrderLine> COLUMN_Ref_OrderLine_ID = new ModelColumn<>(I_C_OrderLine.class, "Ref_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_Ref_OrderLine_ID = "Ref_OrderLine_ID";

	/**
	 * Set Proposal Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_ProposalLine_ID (int Ref_ProposalLine_ID);

	/**
	 * Get Proposal Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_ProposalLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getRef_ProposalLine();

	void setRef_ProposalLine(@Nullable org.compiere.model.I_C_OrderLine Ref_ProposalLine);

	ModelColumn<I_C_OrderLine, org.compiere.model.I_C_OrderLine> COLUMN_Ref_ProposalLine_ID = new ModelColumn<>(I_C_OrderLine.class, "Ref_ProposalLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_Ref_ProposalLine_ID = "Ref_ProposalLine_ID";

	/**
	 * Set Revenue Recognition Amt.
	 * Revenue Recognition Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRRAmt (@Nullable BigDecimal RRAmt);

	/**
	 * Get Revenue Recognition Amt.
	 * Revenue Recognition Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getRRAmt();

	ModelColumn<I_C_OrderLine, Object> COLUMN_RRAmt = new ModelColumn<>(I_C_OrderLine.class, "RRAmt", null);
	String COLUMNNAME_RRAmt = "RRAmt";

	/**
	 * Set Revenue Recognition Start.
	 * Revenue Recognition Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRRStartDate (@Nullable java.sql.Timestamp RRStartDate);

	/**
	 * Get Revenue Recognition Start.
	 * Revenue Recognition Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getRRStartDate();

	ModelColumn<I_C_OrderLine, Object> COLUMN_RRStartDate = new ModelColumn<>(I_C_OrderLine.class, "RRStartDate", null);
	String COLUMNNAME_RRStartDate = "RRStartDate";

	/**
	 * Set Maximum Runs.
	 * Number of recurring runs
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRunsMax (int RunsMax);

	/**
	 * Get Maximum Runs.
	 * Number of recurring runs
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRunsMax();

	ModelColumn<I_C_OrderLine, Object> COLUMN_RunsMax = new ModelColumn<>(I_C_OrderLine.class, "RunsMax", null);
	String COLUMNNAME_RunsMax = "RunsMax";

	/**
	 * Set Best Before Policy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipmentAllocation_BestBefore_Policy (@Nullable java.lang.String ShipmentAllocation_BestBefore_Policy);

	/**
	 * Get Best Before Policy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipmentAllocation_BestBefore_Policy();

	ModelColumn<I_C_OrderLine, Object> COLUMN_ShipmentAllocation_BestBefore_Policy = new ModelColumn<>(I_C_OrderLine.class, "ShipmentAllocation_BestBefore_Policy", null);
	String COLUMNNAME_ShipmentAllocation_BestBefore_Policy = "ShipmentAllocation_BestBefore_Policy";

	/**
	 * Set Resource Assignment.
	 * Resource Assignment
	 *
	 * <br>Type: Assignment
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_ResourceAssignment_ID (int S_ResourceAssignment_ID);

	/**
	 * Get Resource Assignment.
	 * Resource Assignment
	 *
	 * <br>Type: Assignment
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_ResourceAssignment_ID();

	ModelColumn<I_C_OrderLine, Object> COLUMN_S_ResourceAssignment_ID = new ModelColumn<>(I_C_OrderLine.class, "S_ResourceAssignment_ID", null);
	String COLUMNNAME_S_ResourceAssignment_ID = "S_ResourceAssignment_ID";

	/**
	 * Set Positions-Steuer.
	 * Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxAmtInfo (@Nullable BigDecimal TaxAmtInfo);

	/**
	 * Get Positions-Steuer.
	 * Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxAmtInfo();

	ModelColumn<I_C_OrderLine, Object> COLUMN_TaxAmtInfo = new ModelColumn<>(I_C_OrderLine.class, "TaxAmtInfo", null);
	String COLUMNNAME_TaxAmtInfo = "TaxAmtInfo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_OrderLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_OrderLine.class, "Updated", null);
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

	String COLUMNNAME_User2_ID = "User2_ID";

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

	ModelColumn<I_C_OrderLine, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_C_OrderLine.class, "UserElementString1", null);
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

	ModelColumn<I_C_OrderLine, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_C_OrderLine.class, "UserElementString2", null);
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

	ModelColumn<I_C_OrderLine, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_C_OrderLine.class, "UserElementString3", null);
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

	ModelColumn<I_C_OrderLine, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_C_OrderLine.class, "UserElementString4", null);
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

	ModelColumn<I_C_OrderLine, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_C_OrderLine.class, "UserElementString5", null);
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

	ModelColumn<I_C_OrderLine, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_C_OrderLine.class, "UserElementString6", null);
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

	ModelColumn<I_C_OrderLine, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_C_OrderLine.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";
}
