package de.metas.shipper.gateway.derkurier.model;


/** Generated Interface for DerKurier_DeliveryOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DerKurier_DeliveryOrder 
{

    /** TableName=DerKurier_DeliveryOrder */
    public static final String Table_Name = "DerKurier_DeliveryOrder";

    /** AD_Table_ID=540966 */
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_AD_Client>(I_DerKurier_DeliveryOrder.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_AD_Org>(I_DerKurier_DeliveryOrder.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_C_Country>(I_DerKurier_DeliveryOrder.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_AD_User>(I_DerKurier_DeliveryOrder.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DerKurier_DeliveryOrder.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDerKurier_DeliveryOrder_ID (int DerKurier_DeliveryOrder_ID);

	/**
	 * Get DerKurier_DeliveryOrder.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDerKurier_DeliveryOrder_ID();

    /** Column definition for DerKurier_DeliveryOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DerKurier_DeliveryOrder_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DerKurier_DeliveryOrder_ID", null);
    /** Column name DerKurier_DeliveryOrder_ID */
    public static final String COLUMNNAME_DerKurier_DeliveryOrder_ID = "DerKurier_DeliveryOrder_ID";

	/**
	 * Set Gewünschtes Abholdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_DesiredPickupDate (java.sql.Timestamp DK_DesiredPickupDate);

	/**
	 * Get Gewünschtes Abholdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDK_DesiredPickupDate();

    /** Column definition for DK_DesiredPickupDate */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_DesiredPickupDate = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_DesiredPickupDate", null);
    /** Column name DK_DesiredPickupDate */
    public static final String COLUMNNAME_DK_DesiredPickupDate = "DK_DesiredPickupDate";

	/**
	 * Set Gewünschte Abholuhrzeit von.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_DesiredPickupTime_From (java.sql.Timestamp DK_DesiredPickupTime_From);

	/**
	 * Get Gewünschte Abholuhrzeit von.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDK_DesiredPickupTime_From();

    /** Column definition for DK_DesiredPickupTime_From */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_DesiredPickupTime_From = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_DesiredPickupTime_From", null);
    /** Column name DK_DesiredPickupTime_From */
    public static final String COLUMNNAME_DK_DesiredPickupTime_From = "DK_DesiredPickupTime_From";

	/**
	 * Set Gewünschte Abholuhrzeit bis.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_DesiredPickupTime_To (java.sql.Timestamp DK_DesiredPickupTime_To);

	/**
	 * Get Gewünschte Abholuhrzeit bis.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDK_DesiredPickupTime_To();

    /** Column definition for DK_DesiredPickupTime_To */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_DesiredPickupTime_To = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_DesiredPickupTime_To", null);
    /** Column name DK_DesiredPickupTime_To */
    public static final String COLUMNNAME_DK_DesiredPickupTime_To = "DK_DesiredPickupTime_To";

	/**
	 * Set Absender Ort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Sender_City (java.lang.String DK_Sender_City);

	/**
	 * Get Absender Ort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Sender_City();

    /** Column definition for DK_Sender_City */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_Sender_City = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_Sender_City", null);
    /** Column name DK_Sender_City */
    public static final String COLUMNNAME_DK_Sender_City = "DK_Sender_City";

	/**
	 * Set Absender Land.
	 * Zweistelliger ISO-3166 Ländercode
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Sender_Country (java.lang.String DK_Sender_Country);

	/**
	 * Get Absender Land.
	 * Zweistelliger ISO-3166 Ländercode
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Sender_Country();

    /** Column definition for DK_Sender_Country */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_Sender_Country = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_Sender_Country", null);
    /** Column name DK_Sender_Country */
    public static final String COLUMNNAME_DK_Sender_Country = "DK_Sender_Country";

	/**
	 * Set Absender Hausnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Sender_HouseNumber (java.lang.String DK_Sender_HouseNumber);

	/**
	 * Get Absender Hausnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Sender_HouseNumber();

    /** Column definition for DK_Sender_HouseNumber */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_Sender_HouseNumber = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_Sender_HouseNumber", null);
    /** Column name DK_Sender_HouseNumber */
    public static final String COLUMNNAME_DK_Sender_HouseNumber = "DK_Sender_HouseNumber";

	/**
	 * Set Absender Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Sender_Name (java.lang.String DK_Sender_Name);

	/**
	 * Get Absender Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Sender_Name();

    /** Column definition for DK_Sender_Name */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_Sender_Name = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_Sender_Name", null);
    /** Column name DK_Sender_Name */
    public static final String COLUMNNAME_DK_Sender_Name = "DK_Sender_Name";

	/**
	 * Set Absender Name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Sender_Name2 (java.lang.String DK_Sender_Name2);

	/**
	 * Get Absender Name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Sender_Name2();

    /** Column definition for DK_Sender_Name2 */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_Sender_Name2 = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_Sender_Name2", null);
    /** Column name DK_Sender_Name2 */
    public static final String COLUMNNAME_DK_Sender_Name2 = "DK_Sender_Name2";

	/**
	 * Set Absender Name3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Sender_Name3 (java.lang.String DK_Sender_Name3);

	/**
	 * Get Absender Name3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Sender_Name3();

    /** Column definition for DK_Sender_Name3 */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_Sender_Name3 = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_Sender_Name3", null);
    /** Column name DK_Sender_Name3 */
    public static final String COLUMNNAME_DK_Sender_Name3 = "DK_Sender_Name3";

	/**
	 * Set Absender Strasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Sender_Street (java.lang.String DK_Sender_Street);

	/**
	 * Get Absender Strasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Sender_Street();

    /** Column definition for DK_Sender_Street */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_Sender_Street = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_Sender_Street", null);
    /** Column name DK_Sender_Street */
    public static final String COLUMNNAME_DK_Sender_Street = "DK_Sender_Street";

	/**
	 * Set Absender PLZ.
	 * Postleitzahl
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Sender_ZipCode (java.lang.String DK_Sender_ZipCode);

	/**
	 * Get Absender PLZ.
	 * Postleitzahl
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Sender_ZipCode();

    /** Column definition for DK_Sender_ZipCode */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_DK_Sender_ZipCode = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "DK_Sender_ZipCode", null);
    /** Column name DK_Sender_ZipCode */
    public static final String COLUMNNAME_DK_Sender_ZipCode = "DK_Sender_ZipCode";

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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_M_Shipper>(I_DerKurier_DeliveryOrder.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Transport Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transport Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ShipperTransportation_ID();

    /** Column definition for M_ShipperTransportation_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_M_ShipperTransportation_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "M_ShipperTransportation_ID", null);
    /** Column name M_ShipperTransportation_ID */
    public static final String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, Object>(I_DerKurier_DeliveryOrder.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrder, org.compiere.model.I_AD_User>(I_DerKurier_DeliveryOrder.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
