package de.metas.vertical.pharma.vendor.gateway.msv3.model;


/** Generated Interface for MSV3_BestellungAntwortPosition
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_BestellungAntwortPosition 
{

    /** TableName=MSV3_BestellungAntwortPosition */
    public static final String Table_Name = "MSV3_BestellungAntwortPosition";

    /** AD_Table_ID=540913 */
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, org.compiere.model.I_AD_Client>(I_MSV3_BestellungAntwortPosition.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, org.compiere.model.I_AD_Org>(I_MSV3_BestellungAntwortPosition.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Purchase candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID);

	/**
	 * Get Purchase candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PurchaseCandidate_ID();

    /** Column definition for C_PurchaseCandidate_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object> COLUMN_C_PurchaseCandidate_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object>(I_MSV3_BestellungAntwortPosition.class, "C_PurchaseCandidate_ID", null);
    /** Column name C_PurchaseCandidate_ID */
    public static final String COLUMNNAME_C_PurchaseCandidate_ID = "C_PurchaseCandidate_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object>(I_MSV3_BestellungAntwortPosition.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, org.compiere.model.I_AD_User>(I_MSV3_BestellungAntwortPosition.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object>(I_MSV3_BestellungAntwortPosition.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set BestellLiefervorgabe.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellLiefervorgabe (java.lang.String MSV3_BestellLiefervorgabe);

	/**
	 * Get BestellLiefervorgabe.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_BestellLiefervorgabe();

    /** Column definition for MSV3_BestellLiefervorgabe */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object> COLUMN_MSV3_BestellLiefervorgabe = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object>(I_MSV3_BestellungAntwortPosition.class, "MSV3_BestellLiefervorgabe", null);
    /** Column name MSV3_BestellLiefervorgabe */
    public static final String COLUMNNAME_MSV3_BestellLiefervorgabe = "MSV3_BestellLiefervorgabe";

	/**
	 * Set BestellMenge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellMenge (int MSV3_BestellMenge);

	/**
	 * Get BestellMenge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_BestellMenge();

    /** Column definition for MSV3_BestellMenge */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object> COLUMN_MSV3_BestellMenge = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object>(I_MSV3_BestellungAntwortPosition.class, "MSV3_BestellMenge", null);
    /** Column name MSV3_BestellMenge */
    public static final String COLUMNNAME_MSV3_BestellMenge = "MSV3_BestellMenge";

	/**
	 * Set BestellPzn.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellPzn (java.lang.String MSV3_BestellPzn);

	/**
	 * Get BestellPzn.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_BestellPzn();

    /** Column definition for MSV3_BestellPzn */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object> COLUMN_MSV3_BestellPzn = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object>(I_MSV3_BestellungAntwortPosition.class, "MSV3_BestellPzn", null);
    /** Column name MSV3_BestellPzn */
    public static final String COLUMNNAME_MSV3_BestellPzn = "MSV3_BestellPzn";

	/**
	 * Set MSV3_BestellungAntwortAuftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellungAntwortAuftrag_ID (int MSV3_BestellungAntwortAuftrag_ID);

	/**
	 * Get MSV3_BestellungAntwortAuftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_BestellungAntwortAuftrag_ID();

	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortAuftrag getMSV3_BestellungAntwortAuftrag();

	public void setMSV3_BestellungAntwortAuftrag(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortAuftrag MSV3_BestellungAntwortAuftrag);

    /** Column definition for MSV3_BestellungAntwortAuftrag_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortAuftrag> COLUMN_MSV3_BestellungAntwortAuftrag_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortAuftrag>(I_MSV3_BestellungAntwortPosition.class, "MSV3_BestellungAntwortAuftrag_ID", de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortAuftrag.class);
    /** Column name MSV3_BestellungAntwortAuftrag_ID */
    public static final String COLUMNNAME_MSV3_BestellungAntwortAuftrag_ID = "MSV3_BestellungAntwortAuftrag_ID";

	/**
	 * Set MSV3_BestellungAntwortPosition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellungAntwortPosition_ID (int MSV3_BestellungAntwortPosition_ID);

	/**
	 * Get MSV3_BestellungAntwortPosition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_BestellungAntwortPosition_ID();

    /** Column definition for MSV3_BestellungAntwortPosition_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object> COLUMN_MSV3_BestellungAntwortPosition_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object>(I_MSV3_BestellungAntwortPosition.class, "MSV3_BestellungAntwortPosition_ID", null);
    /** Column name MSV3_BestellungAntwortPosition_ID */
    public static final String COLUMNNAME_MSV3_BestellungAntwortPosition_ID = "MSV3_BestellungAntwortPosition_ID";

	/**
	 * Set BestellungSubstitution.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellungSubstitution_ID (int MSV3_BestellungSubstitution_ID);

	/**
	 * Get BestellungSubstitution.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_BestellungSubstitution_ID();

	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Substitution getMSV3_BestellungSubstitution();

	public void setMSV3_BestellungSubstitution(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Substitution MSV3_BestellungSubstitution);

    /** Column definition for MSV3_BestellungSubstitution_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Substitution> COLUMN_MSV3_BestellungSubstitution_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Substitution>(I_MSV3_BestellungAntwortPosition.class, "MSV3_BestellungSubstitution_ID", de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Substitution.class);
    /** Column name MSV3_BestellungSubstitution_ID */
    public static final String COLUMNNAME_MSV3_BestellungSubstitution_ID = "MSV3_BestellungSubstitution_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, Object>(I_MSV3_BestellungAntwortPosition.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortPosition, org.compiere.model.I_AD_User>(I_MSV3_BestellungAntwortPosition.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
