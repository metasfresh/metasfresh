package de.metas.shipper.gateway.derkurier.model;


/** Generated Interface for DerKurier_DeliveryOrderLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DerKurier_DeliveryOrderLine 
{

    /** TableName=DerKurier_DeliveryOrderLine */
    public static final String Table_Name = "DerKurier_DeliveryOrderLine";

    /** AD_Table_ID=540967 */
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, org.compiere.model.I_AD_Client>(I_DerKurier_DeliveryOrderLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, org.compiere.model.I_AD_Org>(I_DerKurier_DeliveryOrderLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, org.compiere.model.I_C_Country>(I_DerKurier_DeliveryOrderLine.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, org.compiere.model.I_AD_User>(I_DerKurier_DeliveryOrderLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DerKurier_DeliveryOrder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDerKurier_DeliveryOrder_ID (int DerKurier_DeliveryOrder_ID);

	/**
	 * Get DerKurier_DeliveryOrder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDerKurier_DeliveryOrder_ID();

	public de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder getDerKurier_DeliveryOrder();

	public void setDerKurier_DeliveryOrder(de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder DerKurier_DeliveryOrder);

    /** Column definition for DerKurier_DeliveryOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder> COLUMN_DerKurier_DeliveryOrder_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder>(I_DerKurier_DeliveryOrderLine.class, "DerKurier_DeliveryOrder_ID", de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder.class);
    /** Column name DerKurier_DeliveryOrder_ID */
    public static final String COLUMNNAME_DerKurier_DeliveryOrder_ID = "DerKurier_DeliveryOrder_ID";

	/**
	 * Set DerKurier_DeliveryOrderLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDerKurier_DeliveryOrderLine_ID (int DerKurier_DeliveryOrderLine_ID);

	/**
	 * Get DerKurier_DeliveryOrderLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDerKurier_DeliveryOrderLine_ID();

    /** Column definition for DerKurier_DeliveryOrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DerKurier_DeliveryOrderLine_ID = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DerKurier_DeliveryOrderLine_ID", null);
    /** Column name DerKurier_DeliveryOrderLine_ID */
    public static final String COLUMNNAME_DerKurier_DeliveryOrderLine_ID = "DerKurier_DeliveryOrderLine_ID";

	/**
	 * Set Empfänger Ort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_City (java.lang.String DK_Consignee_City);

	/**
	 * Get Empfänger Ort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_City();

    /** Column definition for DK_Consignee_City */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_City = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_City", null);
    /** Column name DK_Consignee_City */
    public static final String COLUMNNAME_DK_Consignee_City = "DK_Consignee_City";

	/**
	 * Set Empfänger Land.
	 * Zweistelliger ISO-3166 Ländercode
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_Country (java.lang.String DK_Consignee_Country);

	/**
	 * Get Empfänger Land.
	 * Zweistelliger ISO-3166 Ländercode
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_Country();

    /** Column definition for DK_Consignee_Country */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_Country = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_Country", null);
    /** Column name DK_Consignee_Country */
    public static final String COLUMNNAME_DK_Consignee_Country = "DK_Consignee_Country";

	/**
	 * Set Gewünschtes Lieferdepot.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_DesiredStation (java.lang.String DK_Consignee_DesiredStation);

	/**
	 * Get Gewünschtes Lieferdepot.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_DesiredStation();

    /** Column definition for DK_Consignee_DesiredStation */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_DesiredStation = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_DesiredStation", null);
    /** Column name DK_Consignee_DesiredStation */
    public static final String COLUMNNAME_DK_Consignee_DesiredStation = "DK_Consignee_DesiredStation";

	/**
	 * Set Empfänger Email.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_EMail (java.lang.String DK_Consignee_EMail);

	/**
	 * Get Empfänger Email.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_EMail();

    /** Column definition for DK_Consignee_EMail */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_EMail = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_EMail", null);
    /** Column name DK_Consignee_EMail */
    public static final String COLUMNNAME_DK_Consignee_EMail = "DK_Consignee_EMail";

	/**
	 * Set Empfänger Hausnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_HouseNumber (java.lang.String DK_Consignee_HouseNumber);

	/**
	 * Get Empfänger Hausnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_HouseNumber();

    /** Column definition for DK_Consignee_HouseNumber */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_HouseNumber = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_HouseNumber", null);
    /** Column name DK_Consignee_HouseNumber */
    public static final String COLUMNNAME_DK_Consignee_HouseNumber = "DK_Consignee_HouseNumber";

	/**
	 * Set Empfänger Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_Name (java.lang.String DK_Consignee_Name);

	/**
	 * Get Empfänger Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_Name();

    /** Column definition for DK_Consignee_Name */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_Name = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_Name", null);
    /** Column name DK_Consignee_Name */
    public static final String COLUMNNAME_DK_Consignee_Name = "DK_Consignee_Name";

	/**
	 * Set Empfänger Name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_Name2 (java.lang.String DK_Consignee_Name2);

	/**
	 * Get Empfänger Name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_Name2();

    /** Column definition for DK_Consignee_Name2 */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_Name2 = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_Name2", null);
    /** Column name DK_Consignee_Name2 */
    public static final String COLUMNNAME_DK_Consignee_Name2 = "DK_Consignee_Name2";

	/**
	 * Set Empfänger Name3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_Name3 (java.lang.String DK_Consignee_Name3);

	/**
	 * Get Empfänger Name3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_Name3();

    /** Column definition for DK_Consignee_Name3 */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_Name3 = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_Name3", null);
    /** Column name DK_Consignee_Name3 */
    public static final String COLUMNNAME_DK_Consignee_Name3 = "DK_Consignee_Name3";

	/**
	 * Set Empfänger Telefon.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_Phone (java.lang.String DK_Consignee_Phone);

	/**
	 * Get Empfänger Telefon.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_Phone();

    /** Column definition for DK_Consignee_Phone */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_Phone = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_Phone", null);
    /** Column name DK_Consignee_Phone */
    public static final String COLUMNNAME_DK_Consignee_Phone = "DK_Consignee_Phone";

	/**
	 * Set Empfänger Strasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_Street (java.lang.String DK_Consignee_Street);

	/**
	 * Get Empfänger Strasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_Street();

    /** Column definition for DK_Consignee_Street */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_Street = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_Street", null);
    /** Column name DK_Consignee_Street */
    public static final String COLUMNNAME_DK_Consignee_Street = "DK_Consignee_Street";

	/**
	 * Set Empfänger PLZ.
	 * Postleitzahl
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_Consignee_ZipCode (java.lang.String DK_Consignee_ZipCode);

	/**
	 * Get Empfänger PLZ.
	 * Postleitzahl
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Consignee_ZipCode();

    /** Column definition for DK_Consignee_ZipCode */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Consignee_ZipCode = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Consignee_ZipCode", null);
    /** Column name DK_Consignee_ZipCode */
    public static final String COLUMNNAME_DK_Consignee_ZipCode = "DK_Consignee_ZipCode";

	/**
	 * Set Kundennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_CustomerNumber (java.lang.String DK_CustomerNumber);

	/**
	 * Get Kundennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_CustomerNumber();

    /** Column definition for DK_CustomerNumber */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_CustomerNumber = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_CustomerNumber", null);
    /** Column name DK_CustomerNumber */
    public static final String COLUMNNAME_DK_CustomerNumber = "DK_CustomerNumber";

	/**
	 * Set Gewünschtes Lieferdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_DesiredDeliveryDate (java.sql.Timestamp DK_DesiredDeliveryDate);

	/**
	 * Get Gewünschtes Lieferdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDK_DesiredDeliveryDate();

    /** Column definition for DK_DesiredDeliveryDate */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_DesiredDeliveryDate = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_DesiredDeliveryDate", null);
    /** Column name DK_DesiredDeliveryDate */
    public static final String COLUMNNAME_DK_DesiredDeliveryDate = "DK_DesiredDeliveryDate";

	/**
	 * Set Gewünschte Lieferuhrzeit von.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_DesiredDeliveryTime_From (java.sql.Timestamp DK_DesiredDeliveryTime_From);

	/**
	 * Get Gewünschte Lieferuhrzeit von.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDK_DesiredDeliveryTime_From();

    /** Column definition for DK_DesiredDeliveryTime_From */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_DesiredDeliveryTime_From = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_DesiredDeliveryTime_From", null);
    /** Column name DK_DesiredDeliveryTime_From */
    public static final String COLUMNNAME_DK_DesiredDeliveryTime_From = "DK_DesiredDeliveryTime_From";

	/**
	 * Set Gewünschte Lieferuhrzeit bis.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_DesiredDeliveryTime_To (java.sql.Timestamp DK_DesiredDeliveryTime_To);

	/**
	 * Get Gewünschte Lieferuhrzeit bis.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDK_DesiredDeliveryTime_To();

    /** Column definition for DK_DesiredDeliveryTime_To */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_DesiredDeliveryTime_To = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_DesiredDeliveryTime_To", null);
    /** Column name DK_DesiredDeliveryTime_To */
    public static final String COLUMNNAME_DK_DesiredDeliveryTime_To = "DK_DesiredDeliveryTime_To";

	/**
	 * Set Gesamtpaketanzahl.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_PackageAmount (int DK_PackageAmount);

	/**
	 * Get Gesamtpaketanzahl.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDK_PackageAmount();

    /** Column definition for DK_PackageAmount */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_PackageAmount = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_PackageAmount", null);
    /** Column name DK_PackageAmount */
    public static final String COLUMNNAME_DK_PackageAmount = "DK_PackageAmount";

	/**
	 * Set Pakethöhe.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_ParcelHeight (java.math.BigDecimal DK_ParcelHeight);

	/**
	 * Get Pakethöhe.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDK_ParcelHeight();

    /** Column definition for DK_ParcelHeight */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_ParcelHeight = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_ParcelHeight", null);
    /** Column name DK_ParcelHeight */
    public static final String COLUMNNAME_DK_ParcelHeight = "DK_ParcelHeight";

	/**
	 * Set Paketlänge.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_ParcelLength (java.math.BigDecimal DK_ParcelLength);

	/**
	 * Get Paketlänge.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDK_ParcelLength();

    /** Column definition for DK_ParcelLength */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_ParcelLength = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_ParcelLength", null);
    /** Column name DK_ParcelLength */
    public static final String COLUMNNAME_DK_ParcelLength = "DK_ParcelLength";

	/**
	 * Set Sendungsnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_ParcelNumber (java.lang.String DK_ParcelNumber);

	/**
	 * Get Sendungsnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_ParcelNumber();

    /** Column definition for DK_ParcelNumber */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_ParcelNumber = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_ParcelNumber", null);
    /** Column name DK_ParcelNumber */
    public static final String COLUMNNAME_DK_ParcelNumber = "DK_ParcelNumber";

	/**
	 * Set Paketgewicht.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDK_ParcelWeight (java.math.BigDecimal DK_ParcelWeight);

	/**
	 * Get Paketgewicht.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDK_ParcelWeight();

    /** Column definition for DK_ParcelWeight */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_ParcelWeight = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_ParcelWeight", null);
    /** Column name DK_ParcelWeight */
    public static final String COLUMNNAME_DK_ParcelWeight = "DK_ParcelWeight";

	/**
	 * Set Paketbreite.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_ParcelWidth (java.math.BigDecimal DK_ParcelWidth);

	/**
	 * Get Paketbreite.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDK_ParcelWidth();

    /** Column definition for DK_ParcelWidth */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_ParcelWidth = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_ParcelWidth", null);
    /** Column name DK_ParcelWidth */
    public static final String COLUMNNAME_DK_ParcelWidth = "DK_ParcelWidth";

	/**
	 * Set Referenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDK_Reference (java.lang.String DK_Reference);

	/**
	 * Get Referenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDK_Reference();

    /** Column definition for DK_Reference */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_DK_Reference = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "DK_Reference", null);
    /** Column name DK_Reference */
    public static final String COLUMNNAME_DK_Reference = "DK_Reference";

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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, Object>(I_DerKurier_DeliveryOrderLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DerKurier_DeliveryOrderLine, org.compiere.model.I_AD_User>(I_DerKurier_DeliveryOrderLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
