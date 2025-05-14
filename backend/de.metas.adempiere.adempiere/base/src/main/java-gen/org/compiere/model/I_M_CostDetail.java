package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_CostDetail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_CostDetail 
{

    /** TableName=M_CostDetail */
    public static final String Table_Name = "M_CostDetail";

    /** AD_Table_ID=808 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_AD_Client>(I_M_CostDetail.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_AD_Org>(I_M_CostDetail.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Betrag.
	 * Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmt (java.math.BigDecimal Amt);

	/**
	 * Get Betrag.
	 * Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmt();

    /** Column definition for Amt */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Amt = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Amt", null);
    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/**
	 * Set Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_AcctSchema>(I_M_CostDetail.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_Currency>(I_M_CostDetail.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Rechnungsposition.
	 * Invoice Detail Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Rechnungsposition.
	 * Invoice Detail Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	public void setC_InvoiceLine(org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

    /** Column definition for C_InvoiceLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_InvoiceLine>(I_M_CostDetail.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Set Auftragsposition.
	 * Sales Order Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Sales Order Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_OrderLine>(I_M_CostDetail.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Projekt-Problem.
	 * Project Issues (Material, Labor)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ProjectIssue_ID (int C_ProjectIssue_ID);

	/**
	 * Get Projekt-Problem.
	 * Project Issues (Material, Labor)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ProjectIssue_ID();

	public org.compiere.model.I_C_ProjectIssue getC_ProjectIssue();

	public void setC_ProjectIssue(org.compiere.model.I_C_ProjectIssue C_ProjectIssue);

    /** Column definition for C_ProjectIssue_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_ProjectIssue> COLUMN_C_ProjectIssue_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_ProjectIssue>(I_M_CostDetail.class, "C_ProjectIssue_ID", org.compiere.model.I_C_ProjectIssue.class);
    /** Column name C_ProjectIssue_ID */
    public static final String COLUMNNAME_C_ProjectIssue_ID = "C_ProjectIssue_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_C_UOM>(I_M_CostDetail.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_AD_User>(I_M_CostDetail.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateAcct();

	ModelColumn<I_M_CostDetail, Object> COLUMN_DateAcct = new ModelColumn<>(I_M_CostDetail.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Delta Amount.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeltaAmt (@Nullable BigDecimal DeltaAmt);

	/**
	 * Get Delta Amount.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDeltaAmt();

	ModelColumn<I_M_CostDetail, Object> COLUMN_DeltaAmt = new ModelColumn<>(I_M_CostDetail.class, "DeltaAmt", null);
	String COLUMNNAME_DeltaAmt = "DeltaAmt";

	/**
	 * Set Delta Quantity.
	 * Quantity Difference
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeltaQty (java.math.BigDecimal DeltaQty);

	/**
	 * Get Delta Quantity.
	 * Quantity Difference
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDeltaQty();

    /** Column definition for DeltaQty */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_DeltaQty = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "DeltaQty", null);
    /** Column name DeltaQty */
    public static final String COLUMNNAME_DeltaQty = "DeltaQty";

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
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Changing costs.
	 * Set if this record is changing the costs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsChangingCosts (boolean IsChangingCosts);

	/**
	 * Get Changing costs.
	 * Set if this record is changing the costs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isChangingCosts();

    /** Column definition for IsChangingCosts */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_IsChangingCosts = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "IsChangingCosts", null);
    /** Column name IsChangingCosts */
    public static final String COLUMNNAME_IsChangingCosts = "IsChangingCosts";

	/**
	 * Set Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_AttributeSetInstance>(I_M_CostDetail.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Kosten-Detail.
	 * Cost Detail Information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_CostDetail_ID (int M_CostDetail_ID);

	/**
	 * Get Kosten-Detail.
	 * Cost Detail Information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_CostDetail_ID();

    /** Column definition for M_CostDetail_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_M_CostDetail_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "M_CostDetail_ID", null);
    /** Column name M_CostDetail_ID */
    public static final String COLUMNNAME_M_CostDetail_ID = "M_CostDetail_ID";

	/**
	 * Set Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostDetail_Type (java.lang.String M_CostDetail_Type);

	/**
	 * Get Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getM_CostDetail_Type();

	org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_M_CostDetail_Type = new org.adempiere.model.ModelColumn<>(I_M_CostDetail.class, "M_CostDetail_Type", null);
	String COLUMNNAME_M_CostDetail_Type = "M_CostDetail_Type";

	/**
	 * Set Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_CostElement_ID (int M_CostElement_ID);

	/**
	 * Get Kostenart.
	 * Product Cost Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostElement_ID();

	@Nullable org.compiere.model.I_M_CostElement getM_CostElement();

	void setM_CostElement(@Nullable org.compiere.model.I_M_CostElement M_CostElement);

	org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new org.adempiere.model.ModelColumn<>(I_M_CostDetail.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Cost Revaluation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CostRevaluation_ID (int M_CostRevaluation_ID);

	/**
	 * Get Cost Revaluation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostRevaluation_ID();

	@Nullable org.compiere.model.I_M_CostRevaluation getM_CostRevaluation();

	void setM_CostRevaluation(@Nullable org.compiere.model.I_M_CostRevaluation M_CostRevaluation);

	String COLUMNNAME_M_CostRevaluation_ID = "M_CostRevaluation_ID";

	/**
	 * Set Cost Revaluation Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CostRevaluationLine_ID (int M_CostRevaluationLine_ID);

	/**
	 * Get Cost Revaluation Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostRevaluationLine_ID();

	@Nullable org.compiere.model.I_M_CostRevaluationLine getM_CostRevaluationLine();

	void setM_CostRevaluationLine(@Nullable org.compiere.model.I_M_CostRevaluationLine M_CostRevaluationLine);

	org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_CostRevaluationLine> COLUMN_M_CostRevaluationLine_ID = new org.adempiere.model.ModelColumn<>(I_M_CostDetail.class, "M_CostRevaluationLine_ID", org.compiere.model.I_M_CostRevaluationLine.class);
	String COLUMNNAME_M_CostRevaluationLine_ID = "M_CostRevaluationLine_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_InOutLine>(I_M_CostDetail.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Inventur-Position.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Inventur-Position.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InventoryLine_ID();

	public org.compiere.model.I_M_InventoryLine getM_InventoryLine();

	public void setM_InventoryLine(org.compiere.model.I_M_InventoryLine M_InventoryLine);

    /** Column definition for M_InventoryLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_InventoryLine> COLUMN_M_InventoryLine_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_InventoryLine>(I_M_CostDetail.class, "M_InventoryLine_ID", org.compiere.model.I_M_InventoryLine.class);
    /** Column name M_InventoryLine_ID */
    public static final String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

	/**
	 * Set Abgleich Rechnung.
	 * Match Shipment/Receipt to Invoice
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_MatchInv_ID (int M_MatchInv_ID);

	/**
	 * Get Abgleich Rechnung.
	 * Match Shipment/Receipt to Invoice
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_MatchInv_ID();

	public org.compiere.model.I_M_MatchInv getM_MatchInv();

	public void setM_MatchInv(org.compiere.model.I_M_MatchInv M_MatchInv);

    /** Column definition for M_MatchInv_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_MatchInv> COLUMN_M_MatchInv_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_MatchInv>(I_M_CostDetail.class, "M_MatchInv_ID", org.compiere.model.I_M_MatchInv.class);
    /** Column name M_MatchInv_ID */
    public static final String COLUMNNAME_M_MatchInv_ID = "M_MatchInv_ID";

	/**
	 * Set Abgleich Bestellung.
	 * Match Purchase Order to Shipment/Receipt and Invoice
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_MatchPO_ID (int M_MatchPO_ID);

	/**
	 * Get Abgleich Bestellung.
	 * Match Purchase Order to Shipment/Receipt and Invoice
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_MatchPO_ID();

	public org.compiere.model.I_M_MatchPO getM_MatchPO();

	public void setM_MatchPO(org.compiere.model.I_M_MatchPO M_MatchPO);

    /** Column definition for M_MatchPO_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_MatchPO> COLUMN_M_MatchPO_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_MatchPO>(I_M_CostDetail.class, "M_MatchPO_ID", org.compiere.model.I_M_MatchPO.class);
    /** Column name M_MatchPO_ID */
    public static final String COLUMNNAME_M_MatchPO_ID = "M_MatchPO_ID";

	/**
	 * Set Warenbewegungs- Position.
	 * Inventory Move document Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_MovementLine_ID (int M_MovementLine_ID);

	/**
	 * Get Warenbewegungs- Position.
	 * Inventory Move document Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_MovementLine_ID();

	public org.compiere.model.I_M_MovementLine getM_MovementLine();

	public void setM_MovementLine(org.compiere.model.I_M_MovementLine M_MovementLine);

    /** Column definition for M_MovementLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_MovementLine> COLUMN_M_MovementLine_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_MovementLine>(I_M_CostDetail.class, "M_MovementLine_ID", org.compiere.model.I_M_MovementLine.class);
    /** Column name M_MovementLine_ID */
    public static final String COLUMNNAME_M_MovementLine_ID = "M_MovementLine_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_M_Product>(I_M_CostDetail.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.eevolution.model.I_PP_Cost_Collector>(I_M_CostDetail.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
    /** Column name PP_Cost_Collector_ID */
    public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/**
	 * Set Previous Cumulated Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrev_CumulatedAmt (java.math.BigDecimal Prev_CumulatedAmt);

	/**
	 * Get Previous Cumulated Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrev_CumulatedAmt();

    /** Column definition for Prev_CumulatedAmt */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Prev_CumulatedAmt = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Prev_CumulatedAmt", null);
    /** Column name Prev_CumulatedAmt */
    public static final String COLUMNNAME_Prev_CumulatedAmt = "Prev_CumulatedAmt";

	/**
	 * Set Previous Cumulated Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrev_CumulatedQty (java.math.BigDecimal Prev_CumulatedQty);

	/**
	 * Get Previous Cumulated Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrev_CumulatedQty();

    /** Column definition for Prev_CumulatedQty */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Prev_CumulatedQty = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Prev_CumulatedQty", null);
    /** Column name Prev_CumulatedQty */
    public static final String COLUMNNAME_Prev_CumulatedQty = "Prev_CumulatedQty";

	/**
	 * Set Previous Current Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrev_CurrentCostPrice (java.math.BigDecimal Prev_CurrentCostPrice);

	/**
	 * Get Previous Current Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrev_CurrentCostPrice();

    /** Column definition for Prev_CurrentCostPrice */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Prev_CurrentCostPrice = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Prev_CurrentCostPrice", null);
    /** Column name Prev_CurrentCostPrice */
    public static final String COLUMNNAME_Prev_CurrentCostPrice = "Prev_CurrentCostPrice";

	/**
	 * Set Previous Current Cost Price LL.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrev_CurrentCostPriceLL (java.math.BigDecimal Prev_CurrentCostPriceLL);

	/**
	 * Get Previous Current Cost Price LL.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrev_CurrentCostPriceLL();

    /** Column definition for Prev_CurrentCostPriceLL */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Prev_CurrentCostPriceLL = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Prev_CurrentCostPriceLL", null);
    /** Column name Prev_CurrentCostPriceLL */
    public static final String COLUMNNAME_Prev_CurrentCostPriceLL = "Prev_CurrentCostPriceLL";

	/**
	 * Set Previous Current Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrev_CurrentQty (java.math.BigDecimal Prev_CurrentQty);

	/**
	 * Get Previous Current Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrev_CurrentQty();

    /** Column definition for Prev_CurrentQty */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Prev_CurrentQty = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Prev_CurrentQty", null);
    /** Column name Prev_CurrentQty */
    public static final String COLUMNNAME_Prev_CurrentQty = "Prev_CurrentQty";

	/**
	 * Set Preis.
	 * Price
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPrice (java.math.BigDecimal Price);

	/**
	 * Get Preis.
	 * Price
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getPrice();

    /** Column definition for Price */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Price = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Price", null);
    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Menge.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Amount in document currency.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSourceAmt (@Nullable BigDecimal SourceAmt);

	/**
	 * Get Amount in document currency.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getSourceAmt();

	ModelColumn<I_M_CostDetail, Object> COLUMN_SourceAmt = new ModelColumn<>(I_M_CostDetail.class, "SourceAmt", null);
	String COLUMNNAME_SourceAmt = "SourceAmt";

	/**
	 * Set Document currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSource_Currency_ID (int Source_Currency_ID);

	/**
	 * Get Document currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSource_Currency_ID();

	String COLUMNNAME_Source_Currency_ID = "Source_Currency_ID";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_CostDetail, Object>(I_M_CostDetail.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_CostDetail, org.compiere.model.I_AD_User>(I_M_CostDetail.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
