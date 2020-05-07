package org.compiere.model;


/** Generated Interface for M_RMALine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_RMALine 
{

    /** TableName=M_RMALine */
    public static final String Table_Name = "M_RMALine";

    /** AD_Table_ID=660 */
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
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_AD_Client>(I_M_RMALine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_AD_Org>(I_M_RMALine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Betrag.
	 * Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmt (java.math.BigDecimal Amt);

	/**
	 * Get Betrag.
	 * Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmt();

    /** Column definition for Amt */
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_Amt = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "Amt", null);
    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge();

	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column definition for C_Charge_ID */
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_C_Charge>(I_M_RMALine.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_AD_User>(I_M_RMALine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Zeilennetto.
	 * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt);

	/**
	 * Get Zeilennetto.
	 * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLineNetAmt();

    /** Column definition for LineNetAmt */
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_LineNetAmt = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "LineNetAmt", null);
    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Versand-/Wareneingangsposition.
	 * Line on Shipment or Receipt document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Line on Shipment or Receipt document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_M_InOutLine>(I_M_RMALine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Warenrücksendung - Freigabe (RMA).
	 * Return Material Authorization
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_RMA_ID (int M_RMA_ID);

	/**
	 * Get Warenrücksendung - Freigabe (RMA).
	 * Return Material Authorization
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_RMA_ID();

	public org.compiere.model.I_M_RMA getM_RMA();

	public void setM_RMA(org.compiere.model.I_M_RMA M_RMA);

    /** Column definition for M_RMA_ID */
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_M_RMA> COLUMN_M_RMA_ID = new org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_M_RMA>(I_M_RMALine.class, "M_RMA_ID", org.compiere.model.I_M_RMA.class);
    /** Column name M_RMA_ID */
    public static final String COLUMNNAME_M_RMA_ID = "M_RMA_ID";

	/**
	 * Set RMA-Position.
	 * Return Material Authorization Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_RMALine_ID (int M_RMALine_ID);

	/**
	 * Get RMA-Position.
	 * Return Material Authorization Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_RMALine_ID();

    /** Column definition for M_RMALine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_M_RMALine_ID = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "M_RMALine_ID", null);
    /** Column name M_RMALine_ID */
    public static final String COLUMNNAME_M_RMALine_ID = "M_RMALine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "Processed", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered();

    /** Column definition for QtyDelivered */
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Berechn. Menge.
	 * Menge, die bereits in Rechnung gestellt wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced);

	/**
	 * Get Berechn. Menge.
	 * Menge, die bereits in Rechnung gestellt wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyInvoiced();

    /** Column definition for QtyInvoiced */
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_QtyInvoiced = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "QtyInvoiced", null);
    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Set Referenced RMA Line.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRef_RMALine_ID (int Ref_RMALine_ID);

	/**
	 * Get Referenced RMA Line.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRef_RMALine_ID();

	public org.compiere.model.I_M_RMALine getRef_RMALine();

	public void setRef_RMALine(org.compiere.model.I_M_RMALine Ref_RMALine);

    /** Column definition for Ref_RMALine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_M_RMALine> COLUMN_Ref_RMALine_ID = new org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_M_RMALine>(I_M_RMALine.class, "Ref_RMALine_ID", org.compiere.model.I_M_RMALine.class);
    /** Column name Ref_RMALine_ID */
    public static final String COLUMNNAME_Ref_RMALine_ID = "Ref_RMALine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_RMALine, Object>(I_M_RMALine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_RMALine, org.compiere.model.I_AD_User>(I_M_RMALine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
