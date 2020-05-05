package de.metas.payment.paypal.model;


/** Generated Interface for PayPal_Order
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PayPal_Order 
{

    /** TableName=PayPal_Order */
    public static final String Table_Name = "PayPal_Order";

    /** AD_Table_ID=541389 */
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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PayPal_Order, org.compiere.model.I_AD_Client>(I_PayPal_Order.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PayPal_Order, org.compiere.model.I_AD_Org>(I_PayPal_Order.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Payment Reservation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_Reservation_ID (int C_Payment_Reservation_ID);

	/**
	 * Get Payment Reservation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_Reservation_ID();

	public org.compiere.model.I_C_Payment_Reservation getC_Payment_Reservation();

	public void setC_Payment_Reservation(org.compiere.model.I_C_Payment_Reservation C_Payment_Reservation);

    /** Column definition for C_Payment_Reservation_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, org.compiere.model.I_C_Payment_Reservation> COLUMN_C_Payment_Reservation_ID = new org.adempiere.model.ModelColumn<I_PayPal_Order, org.compiere.model.I_C_Payment_Reservation>(I_PayPal_Order.class, "C_Payment_Reservation_ID", org.compiere.model.I_C_Payment_Reservation.class);
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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PayPal_Order, Object>(I_PayPal_Order.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PayPal_Order, org.compiere.model.I_AD_User>(I_PayPal_Order.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalId();

    /** Column definition for ExternalId */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, Object> COLUMN_ExternalId = new org.adempiere.model.ModelColumn<I_PayPal_Order, Object>(I_PayPal_Order.class, "ExternalId", null);
    /** Column name ExternalId */
    public static final String COLUMNNAME_ExternalId = "ExternalId";

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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PayPal_Order, Object>(I_PayPal_Order.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PayPal AuthorizationId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPayPal_AuthorizationId (java.lang.String PayPal_AuthorizationId);

	/**
	 * Get PayPal AuthorizationId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPayPal_AuthorizationId();

    /** Column definition for PayPal_AuthorizationId */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, Object> COLUMN_PayPal_AuthorizationId = new org.adempiere.model.ModelColumn<I_PayPal_Order, Object>(I_PayPal_Order.class, "PayPal_AuthorizationId", null);
    /** Column name PayPal_AuthorizationId */
    public static final String COLUMNNAME_PayPal_AuthorizationId = "PayPal_AuthorizationId";

	/**
	 * Set PayPal Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayPal_Order_ID (int PayPal_Order_ID);

	/**
	 * Get PayPal Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPayPal_Order_ID();

    /** Column definition for PayPal_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, Object> COLUMN_PayPal_Order_ID = new org.adempiere.model.ModelColumn<I_PayPal_Order, Object>(I_PayPal_Order.class, "PayPal_Order_ID", null);
    /** Column name PayPal_Order_ID */
    public static final String COLUMNNAME_PayPal_Order_ID = "PayPal_Order_ID";

	/**
	 * Set Order JSON.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPayPal_OrderJSON (java.lang.String PayPal_OrderJSON);

	/**
	 * Get Order JSON.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPayPal_OrderJSON();

    /** Column definition for PayPal_OrderJSON */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, Object> COLUMN_PayPal_OrderJSON = new org.adempiere.model.ModelColumn<I_PayPal_Order, Object>(I_PayPal_Order.class, "PayPal_OrderJSON", null);
    /** Column name PayPal_OrderJSON */
    public static final String COLUMNNAME_PayPal_OrderJSON = "PayPal_OrderJSON";

	/**
	 * Set Payer Approve URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPayPal_PayerApproveUrl (java.lang.String PayPal_PayerApproveUrl);

	/**
	 * Get Payer Approve URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPayPal_PayerApproveUrl();

    /** Column definition for PayPal_PayerApproveUrl */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, Object> COLUMN_PayPal_PayerApproveUrl = new org.adempiere.model.ModelColumn<I_PayPal_Order, Object>(I_PayPal_Order.class, "PayPal_PayerApproveUrl", null);
    /** Column name PayPal_PayerApproveUrl */
    public static final String COLUMNNAME_PayPal_PayerApproveUrl = "PayPal_PayerApproveUrl";

	/**
	 * Set Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_PayPal_Order, Object>(I_PayPal_Order.class, "Status", null);
    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PayPal_Order, Object>(I_PayPal_Order.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Order, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PayPal_Order, org.compiere.model.I_AD_User>(I_PayPal_Order.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
