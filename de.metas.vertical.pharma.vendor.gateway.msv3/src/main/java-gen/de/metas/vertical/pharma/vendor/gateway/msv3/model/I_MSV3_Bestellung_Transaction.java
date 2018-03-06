package de.metas.vertical.pharma.vendor.gateway.msv3.model;


/** Generated Interface for MSV3_Bestellung_Transaction
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_Bestellung_Transaction 
{

    /** TableName=MSV3_Bestellung_Transaction */
    public static final String Table_Name = "MSV3_Bestellung_Transaction";

    /** AD_Table_ID=540927 */
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

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_AD_Client>(I_MSV3_Bestellung_Transaction.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue();

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_AD_Issue>(I_MSV3_Bestellung_Transaction.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_AD_Org>(I_MSV3_Bestellung_Transaction.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bestellung.
	 * Bestellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderPO_ID (int C_OrderPO_ID);

	/**
	 * Get Bestellung.
	 * Bestellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderPO_ID();

	public org.compiere.model.I_C_Order getC_OrderPO();

	public void setC_OrderPO(org.compiere.model.I_C_Order C_OrderPO);

    /** Column definition for C_OrderPO_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_C_Order> COLUMN_C_OrderPO_ID = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_C_Order>(I_MSV3_Bestellung_Transaction.class, "C_OrderPO_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_OrderPO_ID */
    public static final String COLUMNNAME_C_OrderPO_ID = "C_OrderPO_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, Object>(I_MSV3_Bestellung_Transaction.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_AD_User>(I_MSV3_Bestellung_Transaction.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, Object>(I_MSV3_Bestellung_Transaction.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MSV3_BestellungAntwort.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellungAntwort_ID (int MSV3_BestellungAntwort_ID);

	/**
	 * Get MSV3_BestellungAntwort.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_BestellungAntwort_ID();

	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort getMSV3_BestellungAntwort();

	public void setMSV3_BestellungAntwort(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort MSV3_BestellungAntwort);

    /** Column definition for MSV3_BestellungAntwort_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort> COLUMN_MSV3_BestellungAntwort_ID = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort>(I_MSV3_Bestellung_Transaction.class, "MSV3_BestellungAntwort_ID", de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort.class);
    /** Column name MSV3_BestellungAntwort_ID */
    public static final String COLUMNNAME_MSV3_BestellungAntwort_ID = "MSV3_BestellungAntwort_ID";

	/**
	 * Set MSV3_Bestellung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Bestellung_ID (int MSV3_Bestellung_ID);

	/**
	 * Get MSV3_Bestellung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_Bestellung_ID();

	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Bestellung getMSV3_Bestellung();

	public void setMSV3_Bestellung(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Bestellung MSV3_Bestellung);

    /** Column definition for MSV3_Bestellung_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Bestellung> COLUMN_MSV3_Bestellung_ID = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Bestellung>(I_MSV3_Bestellung_Transaction.class, "MSV3_Bestellung_ID", de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Bestellung.class);
    /** Column name MSV3_Bestellung_ID */
    public static final String COLUMNNAME_MSV3_Bestellung_ID = "MSV3_Bestellung_ID";

	/**
	 * Set MSV3_Bestellung_Transaction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Bestellung_Transaction_ID (int MSV3_Bestellung_Transaction_ID);

	/**
	 * Get MSV3_Bestellung_Transaction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_Bestellung_Transaction_ID();

    /** Column definition for MSV3_Bestellung_Transaction_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, Object> COLUMN_MSV3_Bestellung_Transaction_ID = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, Object>(I_MSV3_Bestellung_Transaction.class, "MSV3_Bestellung_Transaction_ID", null);
    /** Column name MSV3_Bestellung_Transaction_ID */
    public static final String COLUMNNAME_MSV3_Bestellung_Transaction_ID = "MSV3_Bestellung_Transaction_ID";

	/**
	 * Set MSV3_FaultInfo.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_FaultInfo_ID (int MSV3_FaultInfo_ID);

	/**
	 * Get MSV3_FaultInfo.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_FaultInfo_ID();

	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo getMSV3_FaultInfo();

	public void setMSV3_FaultInfo(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo MSV3_FaultInfo);

    /** Column definition for MSV3_FaultInfo_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo> COLUMN_MSV3_FaultInfo_ID = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo>(I_MSV3_Bestellung_Transaction.class, "MSV3_FaultInfo_ID", de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo.class);
    /** Column name MSV3_FaultInfo_ID */
    public static final String COLUMNNAME_MSV3_FaultInfo_ID = "MSV3_FaultInfo_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, Object>(I_MSV3_Bestellung_Transaction.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_Bestellung_Transaction, org.compiere.model.I_AD_User>(I_MSV3_Bestellung_Transaction.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
