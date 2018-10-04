package de.metas.dunning.model;


/** Generated Interface for C_DunningDoc_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_DunningDoc_Line 
{

    /** TableName=C_DunningDoc_Line */
    public static final String Table_Name = "C_DunningDoc_Line";

    /** AD_Table_ID=540402 */
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_Client>(I_C_DunningDoc_Line.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_Org>(I_C_DunningDoc_Line.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Betrag.
	 * Betrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmt (java.math.BigDecimal Amt);

	/**
	 * Get Betrag.
	 * Betrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmt();

    /** Column definition for Amt */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object> COLUMN_Amt = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object>(I_C_DunningDoc_Line.class, "Amt", null);
    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_C_BPartner>(I_C_DunningDoc_Line.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_C_Currency>(I_C_DunningDoc_Line.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Mahnkontakt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Dunning_Contact_ID (int C_Dunning_Contact_ID);

	/**
	 * Get Mahnkontakt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Dunning_Contact_ID();

	public org.compiere.model.I_AD_User getC_Dunning_Contact();

	public void setC_Dunning_Contact(org.compiere.model.I_AD_User C_Dunning_Contact);

    /** Column definition for C_Dunning_Contact_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_User> COLUMN_C_Dunning_Contact_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_User>(I_C_DunningDoc_Line.class, "C_Dunning_Contact_ID", org.compiere.model.I_AD_User.class);
    /** Column name C_Dunning_Contact_ID */
    public static final String COLUMNNAME_C_Dunning_Contact_ID = "C_Dunning_Contact_ID";

	/**
	 * Set Dunning Document.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DunningDoc_ID (int C_DunningDoc_ID);

	/**
	 * Get Dunning Document.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DunningDoc_ID();

	public de.metas.dunning.model.I_C_DunningDoc getC_DunningDoc();

	public void setC_DunningDoc(de.metas.dunning.model.I_C_DunningDoc C_DunningDoc);

    /** Column definition for C_DunningDoc_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, de.metas.dunning.model.I_C_DunningDoc> COLUMN_C_DunningDoc_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, de.metas.dunning.model.I_C_DunningDoc>(I_C_DunningDoc_Line.class, "C_DunningDoc_ID", de.metas.dunning.model.I_C_DunningDoc.class);
    /** Column name C_DunningDoc_ID */
    public static final String COLUMNNAME_C_DunningDoc_ID = "C_DunningDoc_ID";

	/**
	 * Set Dunning Document Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DunningDoc_Line_ID (int C_DunningDoc_Line_ID);

	/**
	 * Get Dunning Document Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DunningDoc_Line_ID();

    /** Column definition for C_DunningDoc_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object> COLUMN_C_DunningDoc_Line_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object>(I_C_DunningDoc_Line.class, "C_DunningDoc_Line_ID", null);
    /** Column name C_DunningDoc_Line_ID */
    public static final String COLUMNNAME_C_DunningDoc_Line_ID = "C_DunningDoc_Line_ID";

	/**
	 * Set Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/**
	 * Get Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DunningLevel_ID();

	public org.compiere.model.I_C_DunningLevel getC_DunningLevel();

	public void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel);

    /** Column definition for C_DunningLevel_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_C_DunningLevel> COLUMN_C_DunningLevel_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_C_DunningLevel>(I_C_DunningDoc_Line.class, "C_DunningLevel_ID", org.compiere.model.I_C_DunningLevel.class);
    /** Column name C_DunningLevel_ID */
    public static final String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object>(I_C_DunningDoc_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_User>(I_C_DunningDoc_Line.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object>(I_C_DunningDoc_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Notiz.
	 * Optional weitere Information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNote (java.lang.String Note);

	/**
	 * Get Notiz.
	 * Optional weitere Information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNote();

    /** Column definition for Note */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object> COLUMN_Note = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object>(I_C_DunningDoc_Line.class, "Note", null);
    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object>(I_C_DunningDoc_Line.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Aussendienst.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Aussendienst.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep();

	public void setSalesRep(org.compiere.model.I_AD_User SalesRep);

    /** Column definition for SalesRep_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_User>(I_C_DunningDoc_Line.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, Object>(I_C_DunningDoc_Line.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_DunningDoc_Line, org.compiere.model.I_AD_User>(I_C_DunningDoc_Line.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
