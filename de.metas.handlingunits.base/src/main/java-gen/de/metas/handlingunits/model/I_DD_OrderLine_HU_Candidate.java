package de.metas.handlingunits.model;


/** Generated Interface for DD_OrderLine_HU_Candidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DD_OrderLine_HU_Candidate 
{

    /** TableName=DD_OrderLine_HU_Candidate */
    public static final String Table_Name = "DD_OrderLine_HU_Candidate";

    /** AD_Table_ID=540686 */
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_AD_Client>(I_DD_OrderLine_HU_Candidate.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_AD_Org>(I_DD_OrderLine_HU_Candidate.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, Object>(I_DD_OrderLine_HU_Candidate.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_AD_User>(I_DD_OrderLine_HU_Candidate.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Distribution Order Line HU Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDD_OrderLine_HU_Candidate_ID (int DD_OrderLine_HU_Candidate_ID);

	/**
	 * Get Distribution Order Line HU Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDD_OrderLine_HU_Candidate_ID();

    /** Column definition for DD_OrderLine_HU_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_DD_OrderLine_HU_Candidate_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, Object>(I_DD_OrderLine_HU_Candidate.class, "DD_OrderLine_HU_Candidate_ID", null);
    /** Column name DD_OrderLine_HU_Candidate_ID */
    public static final String COLUMNNAME_DD_OrderLine_HU_Candidate_ID = "DD_OrderLine_HU_Candidate_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDD_OrderLine_ID();

	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	public void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine);

    /** Column definition for DD_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, org.eevolution.model.I_DD_OrderLine>(I_DD_OrderLine_HU_Candidate.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
    /** Column name DD_OrderLine_ID */
    public static final String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, Object>(I_DD_OrderLine_HU_Candidate.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

	public de.metas.handlingunits.model.I_M_HU getM_HU();

	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, de.metas.handlingunits.model.I_M_HU>(I_DD_OrderLine_HU_Candidate.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, Object>(I_DD_OrderLine_HU_Candidate.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_AD_User>(I_DD_OrderLine_HU_Candidate.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
