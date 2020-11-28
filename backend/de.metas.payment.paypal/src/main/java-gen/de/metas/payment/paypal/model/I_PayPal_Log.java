package de.metas.payment.paypal.model;


/** Generated Interface for PayPal_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PayPal_Log 
{

    /** TableName=PayPal_Log */
    public static final String Table_Name = "PayPal_Log";

    /** AD_Table_ID=541387 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_AD_Client>(I_PayPal_Log.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_AD_Org>(I_PayPal_Log.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Invoice>(I_PayPal_Log.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Order>(I_PayPal_Log.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Zahlung.
	 * Zahlung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Zahlung.
	 * Zahlung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment();

	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment);

    /** Column definition for C_Payment_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Payment> COLUMN_C_Payment_ID = new org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Payment>(I_PayPal_Log.class, "C_Payment_ID", org.compiere.model.I_C_Payment.class);
    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Payment Reservation Capture.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_Reservation_Capture_ID (int C_Payment_Reservation_Capture_ID);

	/**
	 * Get Payment Reservation Capture.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_Reservation_Capture_ID();

	public org.compiere.model.I_C_Payment_Reservation_Capture getC_Payment_Reservation_Capture();

	public void setC_Payment_Reservation_Capture(org.compiere.model.I_C_Payment_Reservation_Capture C_Payment_Reservation_Capture);

    /** Column definition for C_Payment_Reservation_Capture_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Payment_Reservation_Capture> COLUMN_C_Payment_Reservation_Capture_ID = new org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Payment_Reservation_Capture>(I_PayPal_Log.class, "C_Payment_Reservation_Capture_ID", org.compiere.model.I_C_Payment_Reservation_Capture.class);
    /** Column name C_Payment_Reservation_Capture_ID */
    public static final String COLUMNNAME_C_Payment_Reservation_Capture_ID = "C_Payment_Reservation_Capture_ID";

	/**
	 * Set Payment Reservation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_Reservation_ID (int C_Payment_Reservation_ID);

	/**
	 * Get Payment Reservation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_Reservation_ID();

	public org.compiere.model.I_C_Payment_Reservation getC_Payment_Reservation();

	public void setC_Payment_Reservation(org.compiere.model.I_C_Payment_Reservation C_Payment_Reservation);

    /** Column definition for C_Payment_Reservation_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Payment_Reservation> COLUMN_C_Payment_Reservation_ID = new org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Payment_Reservation>(I_PayPal_Log.class, "C_Payment_Reservation_ID", org.compiere.model.I_C_Payment_Reservation.class);
    /** Column name C_Payment_Reservation_ID */
    public static final String COLUMNNAME_C_Payment_Reservation_ID = "C_Payment_Reservation_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_AD_User>(I_PayPal_Log.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PayPal Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayPal_Log_ID (int PayPal_Log_ID);

	/**
	 * Get PayPal Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPayPal_Log_ID();

    /** Column definition for PayPal_Log_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_PayPal_Log_ID = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "PayPal_Log_ID", null);
    /** Column name PayPal_Log_ID */
    public static final String COLUMNNAME_PayPal_Log_ID = "PayPal_Log_ID";

	/**
	 * Set PayPal Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPayPal_Order_ID (int PayPal_Order_ID);

	/**
	 * Get PayPal Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPayPal_Order_ID();

	public de.metas.payment.paypal.model.I_PayPal_Order getPayPal_Order();

	public void setPayPal_Order(de.metas.payment.paypal.model.I_PayPal_Order PayPal_Order);

    /** Column definition for PayPal_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, de.metas.payment.paypal.model.I_PayPal_Order> COLUMN_PayPal_Order_ID = new org.adempiere.model.ModelColumn<I_PayPal_Log, de.metas.payment.paypal.model.I_PayPal_Order>(I_PayPal_Log.class, "PayPal_Order_ID", de.metas.payment.paypal.model.I_PayPal_Order.class);
    /** Column name PayPal_Order_ID */
    public static final String COLUMNNAME_PayPal_Order_ID = "PayPal_Order_ID";

	/**
	 * Set Request Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestBody (java.lang.String RequestBody);

	/**
	 * Get Request Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestBody();

    /** Column definition for RequestBody */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_RequestBody = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "RequestBody", null);
    /** Column name RequestBody */
    public static final String COLUMNNAME_RequestBody = "RequestBody";

	/**
	 * Set Request Headers.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestHeaders (java.lang.String RequestHeaders);

	/**
	 * Get Request Headers.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestHeaders();

    /** Column definition for RequestHeaders */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_RequestHeaders = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "RequestHeaders", null);
    /** Column name RequestHeaders */
    public static final String COLUMNNAME_RequestHeaders = "RequestHeaders";

	/**
	 * Set Request Method.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestMethod (java.lang.String RequestMethod);

	/**
	 * Get Request Method.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestMethod();

    /** Column definition for RequestMethod */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_RequestMethod = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "RequestMethod", null);
    /** Column name RequestMethod */
    public static final String COLUMNNAME_RequestMethod = "RequestMethod";

	/**
	 * Set Request Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestPath (java.lang.String RequestPath);

	/**
	 * Get Request Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestPath();

    /** Column definition for RequestPath */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_RequestPath = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "RequestPath", null);
    /** Column name RequestPath */
    public static final String COLUMNNAME_RequestPath = "RequestPath";

	/**
	 * Set Response Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResponseBody (java.lang.String ResponseBody);

	/**
	 * Get Response Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getResponseBody();

    /** Column definition for ResponseBody */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_ResponseBody = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "ResponseBody", null);
    /** Column name ResponseBody */
    public static final String COLUMNNAME_ResponseBody = "ResponseBody";

	/**
	 * Set Antwort .
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResponseCode (int ResponseCode);

	/**
	 * Get Antwort .
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getResponseCode();

    /** Column definition for ResponseCode */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_ResponseCode = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "ResponseCode", null);
    /** Column name ResponseCode */
    public static final String COLUMNNAME_ResponseCode = "ResponseCode";

	/**
	 * Set Response Headers.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResponseHeaders (java.lang.String ResponseHeaders);

	/**
	 * Get Response Headers.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getResponseHeaders();

    /** Column definition for ResponseHeaders */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_ResponseHeaders = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "ResponseHeaders", null);
    /** Column name ResponseHeaders */
    public static final String COLUMNNAME_ResponseHeaders = "ResponseHeaders";

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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PayPal_Log, Object>(I_PayPal_Log.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PayPal_Log, org.compiere.model.I_AD_User>(I_PayPal_Log.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
