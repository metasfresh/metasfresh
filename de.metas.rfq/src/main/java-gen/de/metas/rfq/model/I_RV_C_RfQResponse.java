package de.metas.rfq.model;


/** Generated Interface for RV_C_RfQResponse
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_RV_C_RfQResponse 
{

    /** TableName=RV_C_RfQResponse */
    public static final String Table_Name = "RV_C_RfQResponse";

    /** AD_Table_ID=710 */
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
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_AD_Client>(I_RV_C_RfQResponse.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_AD_Org>(I_RV_C_RfQResponse.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_AD_User>(I_RV_C_RfQResponse.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Benchmark Difference.
	 * Difference between Response Price and Benchmark Price
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBenchmarkDifference (java.math.BigDecimal BenchmarkDifference);

	/**
	 * Get Benchmark Difference.
	 * Difference between Response Price and Benchmark Price
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getBenchmarkDifference();

    /** Column definition for BenchmarkDifference */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_BenchmarkDifference = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "BenchmarkDifference", null);
    /** Column name BenchmarkDifference */
    public static final String COLUMNNAME_BenchmarkDifference = "BenchmarkDifference";

	/**
	 * Set Benchmark Price.
	 * Price to compare responses to
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBenchmarkPrice (java.math.BigDecimal BenchmarkPrice);

	/**
	 * Get Benchmark Price.
	 * Price to compare responses to
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getBenchmarkPrice();

    /** Column definition for BenchmarkPrice */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_BenchmarkPrice = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "BenchmarkPrice", null);
    /** Column name BenchmarkPrice */
    public static final String COLUMNNAME_BenchmarkPrice = "BenchmarkPrice";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_C_BPartner>(I_RV_C_RfQResponse.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_C_BPartner_Location>(I_RV_C_RfQResponse.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_C_Currency>(I_RV_C_RfQResponse.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, de.metas.rfq.model.I_C_RfQ> COLUMN_C_RfQ_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, de.metas.rfq.model.I_C_RfQ>(I_RV_C_RfQResponse.class, "C_RfQ_ID", de.metas.rfq.model.I_C_RfQ.class);
    /** Column name C_RfQ_ID */
    public static final String COLUMNNAME_C_RfQ_ID = "C_RfQ_ID";

	/**
	 * Set Ausschreibungs-Thema.
	 * Topic for Request for Quotations
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQ_Topic_ID (int C_RfQ_Topic_ID);

	/**
	 * Get Ausschreibungs-Thema.
	 * Topic for Request for Quotations
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQ_Topic_ID();

	public de.metas.rfq.model.I_C_RfQ_Topic getC_RfQ_Topic();

	public void setC_RfQ_Topic(de.metas.rfq.model.I_C_RfQ_Topic C_RfQ_Topic);

    /** Column definition for C_RfQ_Topic_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, de.metas.rfq.model.I_C_RfQ_Topic> COLUMN_C_RfQ_Topic_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, de.metas.rfq.model.I_C_RfQ_Topic>(I_RV_C_RfQResponse.class, "C_RfQ_Topic_ID", de.metas.rfq.model.I_C_RfQ_Topic.class);
    /** Column name C_RfQ_Topic_ID */
    public static final String COLUMNNAME_C_RfQ_Topic_ID = "C_RfQ_Topic_ID";

	/**
	 * Set Ausschreibungs-Antwort.
	 * Request for Quotation Response from a potential Vendor
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponse_ID (int C_RfQResponse_ID);

	/**
	 * Get Ausschreibungs-Antwort.
	 * Request for Quotation Response from a potential Vendor
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponse_ID();

	public de.metas.rfq.model.I_C_RfQResponse getC_RfQResponse();

	public void setC_RfQResponse(de.metas.rfq.model.I_C_RfQResponse C_RfQResponse);

    /** Column definition for C_RfQResponse_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, de.metas.rfq.model.I_C_RfQResponse> COLUMN_C_RfQResponse_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, de.metas.rfq.model.I_C_RfQResponse>(I_RV_C_RfQResponse.class, "C_RfQResponse_ID", de.metas.rfq.model.I_C_RfQResponse.class);
    /** Column name C_RfQResponse_ID */
    public static final String COLUMNNAME_C_RfQResponse_ID = "C_RfQResponse_ID";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_C_UOM>(I_RV_C_RfQResponse.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Antwort-datum.
	 * Date of the Response
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateResponse (java.sql.Timestamp DateResponse);

	/**
	 * Get Antwort-datum.
	 * Date of the Response
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateResponse();

    /** Column definition for DateResponse */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_DateResponse = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "DateResponse", null);
    /** Column name DateResponse */
    public static final String COLUMNNAME_DateResponse = "DateResponse";

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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_DateWorkComplete = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "DateWorkComplete", null);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_DateWorkStart = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "DateWorkStart", null);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_DeliveryDays = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "DeliveryDays", null);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Rabatt %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscount (java.math.BigDecimal Discount);

	/**
	 * Get Rabatt %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscount();

    /** Column definition for Discount */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelfService();

    /** Column definition for IsSelfService */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Line Work Complete.
	 * Date when line work is (planned to be) complete
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineDateWorkComplete (java.sql.Timestamp LineDateWorkComplete);

	/**
	 * Get Line Work Complete.
	 * Date when line work is (planned to be) complete
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLineDateWorkComplete();

    /** Column definition for LineDateWorkComplete */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_LineDateWorkComplete = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "LineDateWorkComplete", null);
    /** Column name LineDateWorkComplete */
    public static final String COLUMNNAME_LineDateWorkComplete = "LineDateWorkComplete";

	/**
	 * Set Line Work Start.
	 * Date when line work is (planned to be) started
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineDateWorkStart (java.sql.Timestamp LineDateWorkStart);

	/**
	 * Get Line Work Start.
	 * Date when line work is (planned to be) started
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLineDateWorkStart();

    /** Column definition for LineDateWorkStart */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_LineDateWorkStart = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "LineDateWorkStart", null);
    /** Column name LineDateWorkStart */
    public static final String COLUMNNAME_LineDateWorkStart = "LineDateWorkStart";

	/**
	 * Set Line Delivery Days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineDeliveryDays (int LineDeliveryDays);

	/**
	 * Get Line Delivery Days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLineDeliveryDays();

    /** Column definition for LineDeliveryDays */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_LineDeliveryDays = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "LineDeliveryDays", null);
    /** Column name LineDeliveryDays */
    public static final String COLUMNNAME_LineDeliveryDays = "LineDeliveryDays";

	/**
	 * Set Positions-Beschreibung.
	 * Description of the Line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineDescription (java.lang.String LineDescription);

	/**
	 * Get Positions-Beschreibung.
	 * Description of the Line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLineDescription();

    /** Column definition for LineDescription */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_LineDescription = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "LineDescription", null);
    /** Column name LineDescription */
    public static final String COLUMNNAME_LineDescription = "LineDescription";

	/**
	 * Set Line Help/Comment.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineHelp (java.lang.String LineHelp);

	/**
	 * Get Line Help/Comment.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLineHelp();

    /** Column definition for LineHelp */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_LineHelp = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "LineHelp", null);
    /** Column name LineHelp */
    public static final String COLUMNNAME_LineHelp = "LineHelp";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_M_AttributeSetInstance>(I_RV_C_RfQResponse.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, org.compiere.model.I_M_Product>(I_RV_C_RfQResponse.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Preis.
	 * Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrice (java.math.BigDecimal Price);

	/**
	 * Get Preis.
	 * Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrice();

    /** Column definition for Price */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_Price = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "Price", null);
    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Quantity Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyPrice (java.math.BigDecimal QtyPrice);

	/**
	 * Get Quantity Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPrice();

    /** Column definition for QtyPrice */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_QtyPrice = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "QtyPrice", null);
    /** Column name QtyPrice */
    public static final String COLUMNNAME_QtyPrice = "QtyPrice";

	/**
	 * Set Quantity Ranking.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyRanking (int QtyRanking);

	/**
	 * Get Quantity Ranking.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getQtyRanking();

    /** Column definition for QtyRanking */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_QtyRanking = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "QtyRanking", null);
    /** Column name QtyRanking */
    public static final String COLUMNNAME_QtyRanking = "QtyRanking";

	/**
	 * Set Ranking.
	 * Relative Rank Number
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRanking (int Ranking);

	/**
	 * Get Ranking.
	 * Relative Rank Number
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRanking();

    /** Column definition for Ranking */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object> COLUMN_Ranking = new org.adempiere.model.ModelColumn<I_RV_C_RfQResponse, Object>(I_RV_C_RfQResponse.class, "Ranking", null);
    /** Column name Ranking */
    public static final String COLUMNNAME_Ranking = "Ranking";
}
