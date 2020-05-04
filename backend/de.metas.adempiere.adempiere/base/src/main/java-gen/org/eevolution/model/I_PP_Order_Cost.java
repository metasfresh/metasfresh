package org.eevolution.model;


/** Generated Interface for PP_Order_Cost
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Order_Cost 
{

    /** TableName=PP_Order_Cost */
    public static final String Table_Name = "PP_Order_Cost";

    /** AD_Table_ID=53024 */
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_AD_Client>(I_PP_Order_Cost.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_AD_Org>(I_PP_Order_Cost.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_C_AcctSchema>(I_PP_Order_Cost.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Cost Distribution Percent.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCostDistributionPercent (java.math.BigDecimal CostDistributionPercent);

	/**
	 * Get Cost Distribution Percent.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCostDistributionPercent();

    /** Column definition for CostDistributionPercent */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_CostDistributionPercent = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "CostDistributionPercent", null);
    /** Column name CostDistributionPercent */
    public static final String COLUMNNAME_CostDistributionPercent = "CostDistributionPercent";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_AD_User>(I_PP_Order_Cost.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Betrag Kumuliert.
	 * Betrag Kumuliert
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCumulatedAmt (java.math.BigDecimal CumulatedAmt);

	/**
	 * Get Betrag Kumuliert.
	 * Betrag Kumuliert
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCumulatedAmt();

    /** Column definition for CumulatedAmt */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_CumulatedAmt = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "CumulatedAmt", null);
    /** Column name CumulatedAmt */
    public static final String COLUMNNAME_CumulatedAmt = "CumulatedAmt";

	/**
	 * Set Menge Kumuliert.
	 * Menge Kumuliert
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCumulatedQty (java.math.BigDecimal CumulatedQty);

	/**
	 * Get Menge Kumuliert.
	 * Menge Kumuliert
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCumulatedQty();

    /** Column definition for CumulatedQty */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_CumulatedQty = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "CumulatedQty", null);
    /** Column name CumulatedQty */
    public static final String COLUMNNAME_CumulatedQty = "CumulatedQty";

	/**
	 * Set Kostenpreis aktuell.
	 * Der gegenwärtig verwendete Kostenpreis
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCurrentCostPrice (java.math.BigDecimal CurrentCostPrice);

	/**
	 * Get Kostenpreis aktuell.
	 * Der gegenwärtig verwendete Kostenpreis
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCurrentCostPrice();

    /** Column definition for CurrentCostPrice */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_CurrentCostPrice = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "CurrentCostPrice", null);
    /** Column name CurrentCostPrice */
    public static final String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";

	/**
	 * Set Current Cost Price Lower Level.
	 * Current Price Lower Level Is the sum of the costs of the components of this product manufactured for this level.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCurrentCostPriceLL (java.math.BigDecimal CurrentCostPriceLL);

	/**
	 * Get Current Cost Price Lower Level.
	 * Current Price Lower Level Is the sum of the costs of the components of this product manufactured for this level.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCurrentCostPriceLL();

    /** Column definition for CurrentCostPriceLL */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_CurrentCostPriceLL = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "CurrentCostPriceLL", null);
    /** Column name CurrentCostPriceLL */
    public static final String COLUMNNAME_CurrentCostPriceLL = "CurrentCostPriceLL";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_AttributeSetInstance>(I_PP_Order_Cost.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Kostenart.
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
	public int getM_CostElement_ID();

	public org.compiere.model.I_M_CostElement getM_CostElement();

	public void setM_CostElement(org.compiere.model.I_M_CostElement M_CostElement);

    /** Column definition for M_CostElement_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_CostElement>(I_PP_Order_Cost.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
    /** Column name M_CostElement_ID */
    public static final String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_CostType_ID (int M_CostType_ID);

	/**
	 * Get Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_CostType_ID();

	public org.compiere.model.I_M_CostType getM_CostType();

	public void setM_CostType(org.compiere.model.I_M_CostType M_CostType);

    /** Column definition for M_CostType_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_CostType> COLUMN_M_CostType_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_CostType>(I_PP_Order_Cost.class, "M_CostType_ID", org.compiere.model.I_M_CostType.class);
    /** Column name M_CostType_ID */
    public static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_Product>(I_PP_Order_Cost.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Post Calculation Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPostCalculationAmt (java.math.BigDecimal PostCalculationAmt);

	/**
	 * Get Post Calculation Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPostCalculationAmt();

    /** Column definition for PostCalculationAmt */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_PostCalculationAmt = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "PostCalculationAmt", null);
    /** Column name PostCalculationAmt */
    public static final String COLUMNNAME_PostCalculationAmt = "PostCalculationAmt";

	/**
	 * Set Manufacturing Order Cost.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Cost_ID (int PP_Order_Cost_ID);

	/**
	 * Get Manufacturing Order Cost.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Cost_ID();

    /** Column definition for PP_Order_Cost_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_PP_Order_Cost_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "PP_Order_Cost_ID", null);
    /** Column name PP_Order_Cost_ID */
    public static final String COLUMNNAME_PP_Order_Cost_ID = "PP_Order_Cost_ID";

	/**
	 * Set Transaction Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Cost_TrxType (java.lang.String PP_Order_Cost_TrxType);

	/**
	 * Get Transaction Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPP_Order_Cost_TrxType();

    /** Column definition for PP_Order_Cost_TrxType */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_PP_Order_Cost_TrxType = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "PP_Order_Cost_TrxType", null);
    /** Column name PP_Order_Cost_TrxType */
    public static final String COLUMNNAME_PP_Order_Cost_TrxType = "PP_Order_Cost_TrxType";

	/**
	 * Set Produktionsauftrag.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Produktionsauftrag.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order();

	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

    /** Column definition for PP_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.eevolution.model.I_PP_Order>(I_PP_Order_Cost.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, Object>(I_PP_Order_Cost.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PP_Order_Cost, org.compiere.model.I_AD_User>(I_PP_Order_Cost.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
