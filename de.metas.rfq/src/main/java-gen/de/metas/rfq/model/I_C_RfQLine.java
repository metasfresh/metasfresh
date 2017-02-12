package de.metas.rfq.model;


/** Generated Interface for C_RfQLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQLine 
{

    /** TableName=C_RfQLine */
    public static final String Table_Name = "C_RfQLine";

    /** AD_Table_ID=676 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_AD_Client>(I_C_RfQLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_AD_Org>(I_C_RfQLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ausschreibung.
	 * Request for Quotation
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQ_ID (int C_RfQ_ID);

	/**
	 * Get Ausschreibung.
	 * Request for Quotation
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQ_ID();

	public de.metas.rfq.model.I_C_RfQ getC_RfQ();

	public void setC_RfQ(de.metas.rfq.model.I_C_RfQ C_RfQ);

    /** Column definition for C_RfQ_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, de.metas.rfq.model.I_C_RfQ> COLUMN_C_RfQ_ID = new org.adempiere.model.ModelColumn<I_C_RfQLine, de.metas.rfq.model.I_C_RfQ>(I_C_RfQLine.class, "C_RfQ_ID", de.metas.rfq.model.I_C_RfQ.class);
    /** Column name C_RfQ_ID */
    public static final String COLUMNNAME_C_RfQ_ID = "C_RfQ_ID";

	/**
	 * Set RfQ Line.
	 * Request for Quotation Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQLine_ID (int C_RfQLine_ID);

	/**
	 * Get RfQ Line.
	 * Request for Quotation Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQLine_ID();

    /** Column definition for C_RfQLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_C_RfQLine_ID = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "C_RfQLine_ID", null);
    /** Column name C_RfQLine_ID */
    public static final String COLUMNNAME_C_RfQLine_ID = "C_RfQLine_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_C_UOM>(I_C_RfQLine.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_AD_User>(I_C_RfQLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Arbeit fertiggestellt.
	 * Date when work is (planned to be) complete
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateWorkComplete (java.sql.Timestamp DateWorkComplete);

	/**
	 * Get Arbeit fertiggestellt.
	 * Date when work is (planned to be) complete
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateWorkComplete();

    /** Column definition for DateWorkComplete */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_DateWorkComplete = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "DateWorkComplete", null);
    /** Column name DateWorkComplete */
    public static final String COLUMNNAME_DateWorkComplete = "DateWorkComplete";

	/**
	 * Set Arbeitsbeginn.
	 * Date when work is (planned to be) started
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateWorkStart (java.sql.Timestamp DateWorkStart);

	/**
	 * Get Arbeitsbeginn.
	 * Date when work is (planned to be) started
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateWorkStart();

    /** Column definition for DateWorkStart */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_DateWorkStart = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "DateWorkStart", null);
    /** Column name DateWorkStart */
    public static final String COLUMNNAME_DateWorkStart = "DateWorkStart";

	/**
	 * Set Auslieferungstage.
	 * Number of Days (planned) until Delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDays (int DeliveryDays);

	/**
	 * Get Auslieferungstage.
	 * Number of Days (planned) until Delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDeliveryDays();

    /** Column definition for DeliveryDays */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_DeliveryDays = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "DeliveryDays", null);
    /** Column name DeliveryDays */
    public static final String COLUMNNAME_DeliveryDays = "DeliveryDays";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_M_AttributeSetInstance>(I_C_RfQLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_M_Product>(I_C_RfQLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Selected winners count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setRfQ_SelectedWinners_Count (int RfQ_SelectedWinners_Count);

	/**
	 * Get Selected winners count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getRfQ_SelectedWinners_Count();

    /** Column definition for RfQ_SelectedWinners_Count */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_RfQ_SelectedWinners_Count = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "RfQ_SelectedWinners_Count", null);
    /** Column name RfQ_SelectedWinners_Count */
    public static final String COLUMNNAME_RfQ_SelectedWinners_Count = "RfQ_SelectedWinners_Count";

	/**
	 * Set Selected winners Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setRfQ_SelectedWinners_QtySum (java.math.BigDecimal RfQ_SelectedWinners_QtySum);

	/**
	 * Get Selected winners Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getRfQ_SelectedWinners_QtySum();

    /** Column definition for RfQ_SelectedWinners_QtySum */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_RfQ_SelectedWinners_QtySum = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "RfQ_SelectedWinners_QtySum", null);
    /** Column name RfQ_SelectedWinners_QtySum */
    public static final String COLUMNNAME_RfQ_SelectedWinners_QtySum = "RfQ_SelectedWinners_QtySum";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQLine, org.compiere.model.I_AD_User>(I_C_RfQLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Use line quantity.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUseLineQty (boolean UseLineQty);

	/**
	 * Get Use line quantity.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseLineQty();

    /** Column definition for UseLineQty */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLine, Object> COLUMN_UseLineQty = new org.adempiere.model.ModelColumn<I_C_RfQLine, Object>(I_C_RfQLine.class, "UseLineQty", null);
    /** Column name UseLineQty */
    public static final String COLUMNNAME_UseLineQty = "UseLineQty";
}
