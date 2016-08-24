package de.metas.rfq.model;


/** Generated Interface for RV_C_RfQ_UnAnswered
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_RV_C_RfQ_UnAnswered 
{

    /** TableName=RV_C_RfQ_UnAnswered */
    public static final String Table_Name = "RV_C_RfQ_UnAnswered";

    /** AD_Table_ID=709 */
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_AD_Client>(I_RV_C_RfQ_UnAnswered.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_AD_Org>(I_RV_C_RfQ_UnAnswered.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_AD_User>(I_RV_C_RfQ_UnAnswered.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_C_BPartner>(I_RV_C_RfQ_UnAnswered.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_C_BPartner_Location>(I_RV_C_RfQ_UnAnswered.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_C_Currency>(I_RV_C_RfQ_UnAnswered.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, de.metas.rfq.model.I_C_RfQ> COLUMN_C_RfQ_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, de.metas.rfq.model.I_C_RfQ>(I_RV_C_RfQ_UnAnswered.class, "C_RfQ_ID", de.metas.rfq.model.I_C_RfQ.class);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, de.metas.rfq.model.I_C_RfQ_Topic> COLUMN_C_RfQ_Topic_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, de.metas.rfq.model.I_C_RfQ_Topic>(I_RV_C_RfQ_UnAnswered.class, "C_RfQ_Topic_ID", de.metas.rfq.model.I_C_RfQ_Topic.class);
    /** Column name C_RfQ_Topic_ID */
    public static final String COLUMNNAME_C_RfQ_Topic_ID = "C_RfQ_Topic_ID";

	/**
	 * Set Antwort-datum.
	 * Date of the Response
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateResponse (java.sql.Timestamp DateResponse);

	/**
	 * Get Antwort-datum.
	 * Date of the Response
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateResponse();

    /** Column definition for DateResponse */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_DateResponse = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "DateResponse", null);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_DateWorkComplete = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "DateWorkComplete", null);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_DateWorkStart = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "DateWorkStart", null);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_DeliveryDays = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "DeliveryDays", null);
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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Quote All Quantities.
	 * Suppliers are requested to provide responses for all quantities
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsQuoteAllQty (boolean IsQuoteAllQty);

	/**
	 * Get Quote All Quantities.
	 * Suppliers are requested to provide responses for all quantities
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isQuoteAllQty();

    /** Column definition for IsQuoteAllQty */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_IsQuoteAllQty = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "IsQuoteAllQty", null);
    /** Column name IsQuoteAllQty */
    public static final String COLUMNNAME_IsQuoteAllQty = "IsQuoteAllQty";

	/**
	 * Set Quote Total Amt.
	 * The respnse can have just the total amount for the RfQ
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsQuoteTotalAmt (boolean IsQuoteTotalAmt);

	/**
	 * Get Quote Total Amt.
	 * The respnse can have just the total amount for the RfQ
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isQuoteTotalAmt();

    /** Column definition for IsQuoteTotalAmt */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_IsQuoteTotalAmt = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "IsQuoteTotalAmt", null);
    /** Column name IsQuoteTotalAmt */
    public static final String COLUMNNAME_IsQuoteTotalAmt = "IsQuoteTotalAmt";

	/**
	 * Set Responses Accepted.
	 * Are Resonses to the Request for Quotation accepted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRfQResponseAccepted (boolean IsRfQResponseAccepted);

	/**
	 * Get Responses Accepted.
	 * Are Resonses to the Request for Quotation accepted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRfQResponseAccepted();

    /** Column definition for IsRfQResponseAccepted */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_IsRfQResponseAccepted = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "IsRfQResponseAccepted", null);
    /** Column name IsRfQResponseAccepted */
    public static final String COLUMNNAME_IsRfQResponseAccepted = "IsRfQResponseAccepted";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set RfQ Type.
	 * Request for Quotation Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQuoteType (java.lang.String QuoteType);

	/**
	 * Get RfQ Type.
	 * Request for Quotation Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getQuoteType();

    /** Column definition for QuoteType */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object> COLUMN_QuoteType = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, Object>(I_RV_C_RfQ_UnAnswered.class, "QuoteType", null);
    /** Column name QuoteType */
    public static final String COLUMNNAME_QuoteType = "QuoteType";

	/**
	 * Set Vertriebsbeauftragter.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Vertriebsbeauftragter.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep();

	public void setSalesRep(org.compiere.model.I_AD_User SalesRep);

    /** Column definition for SalesRep_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_RV_C_RfQ_UnAnswered, org.compiere.model.I_AD_User>(I_RV_C_RfQ_UnAnswered.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";
}
