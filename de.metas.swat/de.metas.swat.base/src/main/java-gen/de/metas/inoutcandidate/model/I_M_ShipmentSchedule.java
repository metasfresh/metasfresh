package de.metas.inoutcandidate.model;


/** Generated Interface for M_ShipmentSchedule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ShipmentSchedule 
{

    /** TableName=M_ShipmentSchedule */
    public static final String Table_Name = "M_ShipmentSchedule";

    /** AD_Table_ID=500221 */
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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_Client>(I_M_ShipmentSchedule.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_Org>(I_M_ShipmentSchedule.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_Table>(I_M_ShipmentSchedule.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_User>(I_M_ShipmentSchedule.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Ansprechpartner abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Override_ID (int AD_User_Override_ID);

	/**
	 * Get Ansprechpartner abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_Override_ID();

	public org.compiere.model.I_AD_User getAD_User_Override();

	public void setAD_User_Override(org.compiere.model.I_AD_User AD_User_Override);

    /** Column definition for AD_User_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_User> COLUMN_AD_User_Override_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_User>(I_M_ShipmentSchedule.class, "AD_User_Override_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_Override_ID */
    public static final String COLUMNNAME_AD_User_Override_ID = "AD_User_Override_ID";

	/**
	 * Set Sammel-Lieferscheine erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAllowConsolidateInOut (boolean AllowConsolidateInOut);

	/**
	 * Get Sammel-Lieferscheine erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isAllowConsolidateInOut();

    /** Column definition for AllowConsolidateInOut */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_AllowConsolidateInOut = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "AllowConsolidateInOut", null);
    /** Column name AllowConsolidateInOut */
    public static final String COLUMNNAME_AllowConsolidateInOut = "AllowConsolidateInOut";

	/**
	 * Set Rechnungspartner.
	 * Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Rechnungspartner.
	 * Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getBill_BPartner_ID();

	public org.compiere.model.I_C_BPartner getBill_BPartner();

	public void setBill_BPartner(org.compiere.model.I_C_BPartner Bill_BPartner);

    /** Column definition for Bill_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner> COLUMN_Bill_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner>(I_M_ShipmentSchedule.class, "Bill_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Anschrift-Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBPartnerAddress (java.lang.String BPartnerAddress);

	/**
	 * Get Anschrift-Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerAddress();

    /** Column definition for BPartnerAddress */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_BPartnerAddress = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "BPartnerAddress", null);
    /** Column name BPartnerAddress */
    public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/**
	 * Set Anschrift-Text abw..
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerAddress_Override (java.lang.String BPartnerAddress_Override);

	/**
	 * Get Anschrift-Text abw..
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerAddress_Override();

    /** Column definition for BPartnerAddress_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_BPartnerAddress_Override = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "BPartnerAddress_Override", null);
    /** Column name BPartnerAddress_Override */
    public static final String COLUMNNAME_BPartnerAddress_Override = "BPartnerAddress_Override";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner>(I_M_ShipmentSchedule.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner_Location>(I_M_ShipmentSchedule.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Geschäftspartner abw..
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Override_ID (int C_BPartner_Override_ID);

	/**
	 * Get Geschäftspartner abw..
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Override_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner_Override();

	public void setC_BPartner_Override(org.compiere.model.I_C_BPartner C_BPartner_Override);

    /** Column definition for C_BPartner_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_Override_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner>(I_M_ShipmentSchedule.class, "C_BPartner_Override_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_Override_ID */
    public static final String COLUMNNAME_C_BPartner_Override_ID = "C_BPartner_Override_ID";

	/**
	 * Set C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID);

	/**
	 * Get C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Vendor_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner_Vendor();

	public void setC_BPartner_Vendor(org.compiere.model.I_C_BPartner C_BPartner_Vendor);

    /** Column definition for C_BPartner_Vendor_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_Vendor_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner>(I_M_ShipmentSchedule.class, "C_BPartner_Vendor_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_Vendor_ID */
    public static final String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";

	/**
	 * Set Standort abw..
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Location_Override_ID (int C_BP_Location_Override_ID);

	/**
	 * Get Standort abw..
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Location_Override_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BP_Location_Override();

	public void setC_BP_Location_Override(org.compiere.model.I_C_BPartner_Location C_BP_Location_Override);

    /** Column definition for C_BP_Location_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BP_Location_Override_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_BPartner_Location>(I_M_ShipmentSchedule.class, "C_BP_Location_Override_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BP_Location_Override_ID */
    public static final String COLUMNNAME_C_BP_Location_Override_ID = "C_BP_Location_Override_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getC_Currency_ID();

	@Deprecated
	public org.compiere.model.I_C_Currency getC_Currency();

	@Deprecated
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_Currency>(I_M_ShipmentSchedule.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_DocType>(I_M_ShipmentSchedule.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_Order>(I_M_ShipmentSchedule.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_OrderLine>(I_M_ShipmentSchedule.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_User>(I_M_ShipmentSchedule.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getC_UOM_ID();

	@Deprecated
	public org.compiere.model.I_C_UOM getC_UOM();

	@Deprecated
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_C_UOM>(I_M_ShipmentSchedule.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Auftragsdatum.
	 * Datum des Auftrags
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Auftragsdatum.
	 * Datum des Auftrags
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Lieferdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate);

	/**
	 * Get Lieferdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDeliveryDate();

    /** Column definition for DeliveryDate */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryDate = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "DeliveryDate", null);
    /** Column name DeliveryDate */
    public static final String COLUMNNAME_DeliveryDate = "DeliveryDate";

	/**
	 * Set Lieferdatum eff..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDeliveryDate_Effective (java.sql.Timestamp DeliveryDate_Effective);

	/**
	 * Get Lieferdatum eff..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.sql.Timestamp getDeliveryDate_Effective();

    /** Column definition for DeliveryDate_Effective */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryDate_Effective = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "DeliveryDate_Effective", null);
    /** Column name DeliveryDate_Effective */
    public static final String COLUMNNAME_DeliveryDate_Effective = "DeliveryDate_Effective";

	/**
	 * Set Lieferdatum abw.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDate_Override (java.sql.Timestamp DeliveryDate_Override);

	/**
	 * Get Lieferdatum abw.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDeliveryDate_Override();

    /** Column definition for DeliveryDate_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryDate_Override = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "DeliveryDate_Override", null);
    /** Column name DeliveryDate_Override */
    public static final String COLUMNNAME_DeliveryDate_Override = "DeliveryDate_Override";

	/**
	 * Set Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule();

    /** Column definition for DeliveryRule */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryRule = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "DeliveryRule", null);
    /** Column name DeliveryRule */
    public static final String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferart abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryRule_Override (java.lang.String DeliveryRule_Override);

	/**
	 * Get Lieferart abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryRule_Override();

    /** Column definition for DeliveryRule_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryRule_Override = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "DeliveryRule_Override", null);
    /** Column name DeliveryRule_Override */
    public static final String COLUMNNAME_DeliveryRule_Override = "DeliveryRule_Override";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule();

    /** Column definition for DeliveryViaRule */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryViaRule = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "DeliveryViaRule", null);
    /** Column name DeliveryViaRule */
    public static final String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Lieferung durch abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryViaRule_Override (java.lang.String DeliveryViaRule_Override);

	/**
	 * Get Lieferung durch abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDeliveryViaRule_Override();

    /** Column definition for DeliveryViaRule_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DeliveryViaRule_Override = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "DeliveryViaRule_Override", null);
    /** Column name DeliveryViaRule_Override */
    public static final String COLUMNNAME_DeliveryViaRule_Override = "DeliveryViaRule_Override";

	/**
	 * Set Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocSubType (java.lang.String DocSubType);

	/**
	 * Get Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocSubType();

    /** Column definition for DocSubType */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_DocSubType = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "DocSubType", null);
    /** Column name DocSubType */
    public static final String COLUMNNAME_DocSubType = "DocSubType";

	/**
	 * Set Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHeaderAggregationKey (java.lang.String HeaderAggregationKey);

	/**
	 * Get Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHeaderAggregationKey();

    /** Column definition for HeaderAggregationKey */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_HeaderAggregationKey = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "HeaderAggregationKey", null);
    /** Column name HeaderAggregationKey */
    public static final String COLUMNNAME_HeaderAggregationKey = "HeaderAggregationKey";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set abw. Anschrift.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBPartnerAddress_Override (boolean IsBPartnerAddress_Override);

	/**
	 * Get abw. Anschrift.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBPartnerAddress_Override();

    /** Column definition for IsBPartnerAddress_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsBPartnerAddress_Override = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "IsBPartnerAddress_Override", null);
    /** Column name IsBPartnerAddress_Override */
    public static final String COLUMNNAME_IsBPartnerAddress_Override = "IsBPartnerAddress_Override";

	/**
	 * Set Geschlossen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsClosed (boolean IsClosed);

	/**
	 * Get Geschlossen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isClosed();

    /** Column definition for IsClosed */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsClosed = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "IsClosed", null);
    /** Column name IsClosed */
    public static final String COLUMNNAME_IsClosed = "IsClosed";

	/**
	 * Set Delivery stop.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDeliveryStop (boolean IsDeliveryStop);

	/**
	 * Get Delivery stop.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDeliveryStop();

    /** Column definition for IsDeliveryStop */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsDeliveryStop = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "IsDeliveryStop", null);
    /** Column name IsDeliveryStop */
    public static final String COLUMNNAME_IsDeliveryStop = "IsDeliveryStop";

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDisplayed (boolean IsDisplayed);

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDisplayed();

    /** Column definition for IsDisplayed */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsDisplayed = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "IsDisplayed", null);
    /** Column name IsDisplayed */
    public static final String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/**
	 * Set Streckengeschäft.
	 * Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Streckengeschäft.
	 * Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDropShip();

    /** Column definition for IsDropShip */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsDropShip = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "IsDropShip", null);
    /** Column name IsDropShip */
    public static final String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set zu Akt..
	 * Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsToRecompute (boolean IsToRecompute);

	/**
	 * Get zu Akt..
	 * Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public boolean isToRecompute();

    /** Column definition for IsToRecompute */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_IsToRecompute = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "IsToRecompute", null);
    /** Column name IsToRecompute */
    public static final String COLUMNNAME_IsToRecompute = "IsToRecompute";

	/**
	 * Set Zeilennetto.
	 * Noch offener der Nettowert Zeile (offene Menge * Einzelpreis) ohne Fracht und Geb�hren
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt);

	/**
	 * Get Zeilennetto.
	 * Noch offener der Nettowert Zeile (offene Menge * Einzelpreis) ohne Fracht und Geb�hren
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLineNetAmt();

    /** Column definition for LineNetAmt */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_LineNetAmt = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "LineNetAmt", null);
    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_AttributeSetInstance>(I_M_ShipmentSchedule.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set M_IolCandHandler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_IolCandHandler_ID (int M_IolCandHandler_ID);

	/**
	 * Get M_IolCandHandler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_IolCandHandler_ID();

	public de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler();

	public void setM_IolCandHandler(de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler);

    /** Column definition for M_IolCandHandler_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, de.metas.inoutcandidate.model.I_M_IolCandHandler> COLUMN_M_IolCandHandler_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, de.metas.inoutcandidate.model.I_M_IolCandHandler>(I_M_ShipmentSchedule.class, "M_IolCandHandler_ID", de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
    /** Column name M_IolCandHandler_ID */
    public static final String COLUMNNAME_M_IolCandHandler_ID = "M_IolCandHandler_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_Product>(I_M_ShipmentSchedule.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Shipment constraint.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Shipment_Constraint_ID (int M_Shipment_Constraint_ID);

	/**
	 * Get Shipment constraint.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Shipment_Constraint_ID();

	public de.metas.inoutcandidate.model.I_M_Shipment_Constraint getM_Shipment_Constraint();

	public void setM_Shipment_Constraint(de.metas.inoutcandidate.model.I_M_Shipment_Constraint M_Shipment_Constraint);

    /** Column definition for M_Shipment_Constraint_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, de.metas.inoutcandidate.model.I_M_Shipment_Constraint> COLUMN_M_Shipment_Constraint_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, de.metas.inoutcandidate.model.I_M_Shipment_Constraint>(I_M_ShipmentSchedule.class, "M_Shipment_Constraint_ID", de.metas.inoutcandidate.model.I_M_Shipment_Constraint.class);
    /** Column name M_Shipment_Constraint_ID */
    public static final String COLUMNNAME_M_Shipment_Constraint_ID = "M_Shipment_Constraint_ID";

	/**
	 * Set Lieferdisposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Lieferdisposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ID();

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "M_ShipmentSchedule_ID", null);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Ziel-Lager.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID);

	/**
	 * Get Ziel-Lager.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getM_Warehouse_Dest_ID();

	@Deprecated
	public org.compiere.model.I_M_Warehouse getM_Warehouse_Dest();

	@Deprecated
	public void setM_Warehouse_Dest(org.compiere.model.I_M_Warehouse M_Warehouse_Dest);

    /** Column definition for M_Warehouse_Dest_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_Dest_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_Warehouse>(I_M_ShipmentSchedule.class, "M_Warehouse_Dest_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_Dest_ID */
    public static final String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";

	/**
	 * Set Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_Warehouse>(I_M_ShipmentSchedule.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Lager abw..
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_Override_ID (int M_Warehouse_Override_ID);

	/**
	 * Get Lager abw..
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_Override_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse_Override();

	public void setM_Warehouse_Override(org.compiere.model.I_M_Warehouse M_Warehouse_Override);

    /** Column definition for M_Warehouse_Override_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_Override_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_M_Warehouse>(I_M_ShipmentSchedule.class, "M_Warehouse_Override_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_Override_ID */
    public static final String COLUMNNAME_M_Warehouse_Override_ID = "M_Warehouse_Override_ID";

	/**
	 * Set Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPOReference (java.lang.String POReference);

	/**
	 * Get Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getPOReference();

    /** Column definition for POReference */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationDate (java.sql.Timestamp PreparationDate);

	/**
	 * Get Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationDate();

    /** Column definition for PreparationDate */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PreparationDate = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "PreparationDate", null);
    /** Column name PreparationDate */
    public static final String COLUMNNAME_PreparationDate = "PreparationDate";

	/**
	 * Set Bereitstellungsdatum eff..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPreparationDate_Effective (java.sql.Timestamp PreparationDate_Effective);

	/**
	 * Get Bereitstellungsdatum eff..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.sql.Timestamp getPreparationDate_Effective();

    /** Column definition for PreparationDate_Effective */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PreparationDate_Effective = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "PreparationDate_Effective", null);
    /** Column name PreparationDate_Effective */
    public static final String COLUMNNAME_PreparationDate_Effective = "PreparationDate_Effective";

	/**
	 * Set Bereitstellungsdatum abw..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationDate_Override (java.sql.Timestamp PreparationDate_Override);

	/**
	 * Get Bereitstellungsdatum abw..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationDate_Override();

    /** Column definition for PreparationDate_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PreparationDate_Override = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "PreparationDate_Override", null);
    /** Column name PreparationDate_Override */
    public static final String COLUMNNAME_PreparationDate_Override = "PreparationDate_Override";

	/**
	 * Set Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriorityRule();

    /** Column definition for PriorityRule */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PriorityRule = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "PriorityRule", null);
    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	/**
	 * Set Priorität Abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriorityRule_Override (java.lang.String PriorityRule_Override);

	/**
	 * Get Priorität Abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriorityRule_Override();

    /** Column definition for PriorityRule_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_PriorityRule_Override = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "PriorityRule_Override", null);
    /** Column name PriorityRule_Override */
    public static final String COLUMNNAME_PriorityRule_Override = "PriorityRule_Override";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductDescription (java.lang.String ProductDescription);

	/**
	 * Get Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductDescription();

    /** Column definition for ProductDescription */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_ProductDescription = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "ProductDescription", null);
    /** Column name ProductDescription */
    public static final String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered();

    /** Column definition for QtyDelivered */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Bestand.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOnHand (java.math.BigDecimal QtyOnHand);

	/**
	 * Get Bestand.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOnHand();

    /** Column definition for QtyOnHand */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyOnHand = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "QtyOnHand", null);
    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/**
	 * Set Bestellt/ Beauftragt.
	 * Bestellt/ Beauftragt
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Bestellt/ Beauftragt.
	 * Bestellt/ Beauftragt
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set QtyOrdered_Calculated.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered_Calculated (java.math.BigDecimal QtyOrdered_Calculated);

	/**
	 * Get QtyOrdered_Calculated.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered_Calculated();

    /** Column definition for QtyOrdered_Calculated */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyOrdered_Calculated = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "QtyOrdered_Calculated", null);
    /** Column name QtyOrdered_Calculated */
    public static final String COLUMNNAME_QtyOrdered_Calculated = "QtyOrdered_Calculated";

	/**
	 * Set Bestellt abw..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered_Override (java.math.BigDecimal QtyOrdered_Override);

	/**
	 * Get Bestellt abw..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered_Override();

    /** Column definition for QtyOrdered_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyOrdered_Override = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "QtyOrdered_Override", null);
    /** Column name QtyOrdered_Override */
    public static final String COLUMNNAME_QtyOrdered_Override = "QtyOrdered_Override";

	/**
	 * Set Auf Packzettel.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyPickList (java.math.BigDecimal QtyPickList);

	/**
	 * Get Auf Packzettel.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPickList();

    /** Column definition for QtyPickList */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyPickList = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "QtyPickList", null);
    /** Column name QtyPickList */
    public static final String COLUMNNAME_QtyPickList = "QtyPickList";

	/**
	 * Set Offen.
	 * Offene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved (java.math.BigDecimal QtyReserved);

	/**
	 * Get Offen.
	 * Offene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved();

    /** Column definition for QtyReserved */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyReserved = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "QtyReserved", null);
    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Ausliefermenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToDeliver (java.math.BigDecimal QtyToDeliver);

	/**
	 * Get Ausliefermenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToDeliver();

    /** Column definition for QtyToDeliver */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyToDeliver = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "QtyToDeliver", null);
    /** Column name QtyToDeliver */
    public static final String COLUMNNAME_QtyToDeliver = "QtyToDeliver";

	/**
	 * Set Liefermenge abw..
	 * Menge, die abweichend von der ursprünglichen Vorgabe ausgeliefert wird
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToDeliver_Override (java.math.BigDecimal QtyToDeliver_Override);

	/**
	 * Get Liefermenge abw..
	 * Menge, die abweichend von der ursprünglichen Vorgabe ausgeliefert wird
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToDeliver_Override();

    /** Column definition for QtyToDeliver_Override */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyToDeliver_Override = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "QtyToDeliver_Override", null);
    /** Column name QtyToDeliver_Override */
    public static final String COLUMNNAME_QtyToDeliver_Override = "QtyToDeliver_Override";

	/**
	 * Set Erl. Liefermenge abw..
	 * Teilmenge von "Abw. Liefermenge", die bereits Ausgeliefert wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToDeliver_OverrideFulfilled (java.math.BigDecimal QtyToDeliver_OverrideFulfilled);

	/**
	 * Get Erl. Liefermenge abw..
	 * Teilmenge von "Abw. Liefermenge", die bereits Ausgeliefert wurde
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToDeliver_OverrideFulfilled();

    /** Column definition for QtyToDeliver_OverrideFulfilled */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_QtyToDeliver_OverrideFulfilled = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "QtyToDeliver_OverrideFulfilled", null);
    /** Column name QtyToDeliver_OverrideFulfilled */
    public static final String COLUMNNAME_QtyToDeliver_OverrideFulfilled = "QtyToDeliver_OverrideFulfilled";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Positionssumme.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSinglePriceTag_ID (java.lang.String SinglePriceTag_ID);

	/**
	 * Get Positionssumme.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSinglePriceTag_ID();

    /** Column definition for SinglePriceTag_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_SinglePriceTag_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "SinglePriceTag_ID", null);
    /** Column name SinglePriceTag_ID */
    public static final String COLUMNNAME_SinglePriceTag_ID = "SinglePriceTag_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "Status", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, Object>(I_M_ShipmentSchedule.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule, org.compiere.model.I_AD_User>(I_M_ShipmentSchedule.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
