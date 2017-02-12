package de.metas.procurement.base.model;


/** Generated Interface for PMM_RfQResponse_ChangeEvent
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PMM_RfQResponse_ChangeEvent 
{

    /** TableName=PMM_RfQResponse_ChangeEvent */
    public static final String Table_Name = "PMM_RfQResponse_ChangeEvent";

    /** AD_Table_ID=540765 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, org.compiere.model.I_AD_Client>(I_PMM_RfQResponse_ChangeEvent.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, org.compiere.model.I_AD_Org>(I_PMM_RfQResponse_ChangeEvent.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set RfQ Response Line.
	 * Request for Quotation Response Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponseLine_ID (int C_RfQResponseLine_ID);

	/**
	 * Get RfQ Response Line.
	 * Request for Quotation Response Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponseLine_ID();

	public de.metas.rfq.model.I_C_RfQResponseLine getC_RfQResponseLine();

	public void setC_RfQResponseLine(de.metas.rfq.model.I_C_RfQResponseLine C_RfQResponseLine);

    /** Column definition for C_RfQResponseLine_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, de.metas.rfq.model.I_C_RfQResponseLine> COLUMN_C_RfQResponseLine_ID = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, de.metas.rfq.model.I_C_RfQResponseLine>(I_PMM_RfQResponse_ChangeEvent.class, "C_RfQResponseLine_ID", de.metas.rfq.model.I_C_RfQResponseLine.class);
    /** Column name C_RfQResponseLine_ID */
    public static final String COLUMNNAME_C_RfQResponseLine_ID = "C_RfQResponseLine_ID";

	/**
	 * Set RfQ Response Line (UUID).
	 * Request for Quotation Response Line (UUID)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponseLine_UUID (java.lang.String C_RfQResponseLine_UUID);

	/**
	 * Get RfQ Response Line (UUID).
	 * Request for Quotation Response Line (UUID)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getC_RfQResponseLine_UUID();

    /** Column definition for C_RfQResponseLine_UUID */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_C_RfQResponseLine_UUID = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "C_RfQResponseLine_UUID", null);
    /** Column name C_RfQResponseLine_UUID */
    public static final String COLUMNNAME_C_RfQResponseLine_UUID = "C_RfQResponseLine_UUID";

	/**
	 * Set RfQ Response Line Qty.
	 * Request for Quotation Response Line Quantity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponseLineQty_ID (int C_RfQResponseLineQty_ID);

	/**
	 * Get RfQ Response Line Qty.
	 * Request for Quotation Response Line Quantity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponseLineQty_ID();

	public de.metas.rfq.model.I_C_RfQResponseLineQty getC_RfQResponseLineQty();

	public void setC_RfQResponseLineQty(de.metas.rfq.model.I_C_RfQResponseLineQty C_RfQResponseLineQty);

    /** Column definition for C_RfQResponseLineQty_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, de.metas.rfq.model.I_C_RfQResponseLineQty> COLUMN_C_RfQResponseLineQty_ID = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, de.metas.rfq.model.I_C_RfQResponseLineQty>(I_PMM_RfQResponse_ChangeEvent.class, "C_RfQResponseLineQty_ID", de.metas.rfq.model.I_C_RfQResponseLineQty.class);
    /** Column name C_RfQResponseLineQty_ID */
    public static final String COLUMNNAME_C_RfQResponseLineQty_ID = "C_RfQResponseLineQty_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, org.compiere.model.I_AD_User>(I_PMM_RfQResponse_ChangeEvent.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Fehlermeldung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setErrorMsg (java.lang.String ErrorMsg);

	/**
	 * Get Fehlermeldung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getErrorMsg();

    /** Column definition for ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_ErrorMsg = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "ErrorMsg", null);
    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEvent_UUID (java.lang.String Event_UUID);

	/**
	 * Get Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEvent_UUID();

    /** Column definition for Event_UUID */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_Event_UUID = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "Event_UUID", null);
    /** Column name Event_UUID */
    public static final String COLUMNNAME_Event_UUID = "Event_UUID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsError (boolean IsError);

	/**
	 * Get Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isError();

    /** Column definition for IsError */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Lieferprodukt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPMM_Product_ID (int PMM_Product_ID);

	/**
	 * Get Lieferprodukt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPMM_Product_ID();

	public de.metas.procurement.base.model.I_PMM_Product getPMM_Product();

	public void setPMM_Product(de.metas.procurement.base.model.I_PMM_Product PMM_Product);

    /** Column definition for PMM_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, de.metas.procurement.base.model.I_PMM_Product> COLUMN_PMM_Product_ID = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, de.metas.procurement.base.model.I_PMM_Product>(I_PMM_RfQResponse_ChangeEvent.class, "PMM_Product_ID", de.metas.procurement.base.model.I_PMM_Product.class);
    /** Column name PMM_Product_ID */
    public static final String COLUMNNAME_PMM_Product_ID = "PMM_Product_ID";

	/**
	 * Set RfQ Response Change Event.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPMM_RfQResponse_ChangeEvent_ID (int PMM_RfQResponse_ChangeEvent_ID);

	/**
	 * Get RfQ Response Change Event.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPMM_RfQResponse_ChangeEvent_ID();

    /** Column definition for PMM_RfQResponse_ChangeEvent_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_PMM_RfQResponse_ChangeEvent_ID = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "PMM_RfQResponse_ChangeEvent_ID", null);
    /** Column name PMM_RfQResponse_ChangeEvent_ID */
    public static final String COLUMNNAME_PMM_RfQResponse_ChangeEvent_ID = "PMM_RfQResponse_ChangeEvent_ID";

	/**
	 * Set Preis.
	 * Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrice (java.math.BigDecimal Price);

	/**
	 * Get Preis.
	 * Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrice();

    /** Column definition for Price */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_Price = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "Price", null);
    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/**
	 * Set Preis (old).
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrice_Old (java.math.BigDecimal Price_Old);

	/**
	 * Get Preis (old).
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrice_Old();

    /** Column definition for Price_Old */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_Price_Old = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "Price_Old", null);
    /** Column name Price_Old */
    public static final String COLUMNNAME_Price_Old = "Price_Old";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Produkt UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProduct_UUID (java.lang.String Product_UUID);

	/**
	 * Get Produkt UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProduct_UUID();

    /** Column definition for Product_UUID */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_Product_UUID = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "Product_UUID", null);
    /** Column name Product_UUID */
    public static final String COLUMNNAME_Product_UUID = "Product_UUID";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Menge (old).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty_Old (java.math.BigDecimal Qty_Old);

	/**
	 * Get Menge (old).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty_Old();

    /** Column definition for Qty_Old */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_Qty_Old = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "Qty_Old", null);
    /** Column name Qty_Old */
    public static final String COLUMNNAME_Qty_Old = "Qty_Old";

	/**
	 * Set Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setType (java.lang.String Type);

	/**
	 * Get Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getType();

    /** Column definition for Type */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_Type = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "Type", null);
    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, Object>(I_PMM_RfQResponse_ChangeEvent.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PMM_RfQResponse_ChangeEvent, org.compiere.model.I_AD_User>(I_PMM_RfQResponse_ChangeEvent.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
