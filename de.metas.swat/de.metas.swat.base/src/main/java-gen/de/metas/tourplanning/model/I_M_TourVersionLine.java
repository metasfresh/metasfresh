package de.metas.tourplanning.model;


/** Generated Interface for M_TourVersionLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_TourVersionLine 
{

    /** TableName=M_TourVersionLine */
    public static final String Table_Name = "M_TourVersionLine";

    /** AD_Table_ID=540296 */
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_AD_Client>(I_M_TourVersionLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_AD_Org>(I_M_TourVersionLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Puffer (Std).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBufferHours (int BufferHours);

	/**
	 * Get Puffer (Std).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBufferHours();

    /** Column definition for BufferHours */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object> COLUMN_BufferHours = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object>(I_M_TourVersionLine.class, "BufferHours", null);
    /** Column name BufferHours */
    public static final String COLUMNNAME_BufferHours = "BufferHours";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_C_BPartner>(I_M_TourVersionLine.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_C_BPartner_Location>(I_M_TourVersionLine.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object>(I_M_TourVersionLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_AD_User>(I_M_TourVersionLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object>(I_M_TourVersionLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Abholung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsToBeFetched (boolean IsToBeFetched);

	/**
	 * Get Abholung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isToBeFetched();

    /** Column definition for IsToBeFetched */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object> COLUMN_IsToBeFetched = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object>(I_M_TourVersionLine.class, "IsToBeFetched", null);
    /** Column name IsToBeFetched */
    public static final String COLUMNNAME_IsToBeFetched = "IsToBeFetched";

	/**
	 * Set Tour Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_TourVersion_ID (int M_TourVersion_ID);

	/**
	 * Get Tour Version.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_TourVersion_ID();

	public de.metas.tourplanning.model.I_M_TourVersion getM_TourVersion();

	public void setM_TourVersion(de.metas.tourplanning.model.I_M_TourVersion M_TourVersion);

    /** Column definition for M_TourVersion_ID */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, de.metas.tourplanning.model.I_M_TourVersion> COLUMN_M_TourVersion_ID = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, de.metas.tourplanning.model.I_M_TourVersion>(I_M_TourVersionLine.class, "M_TourVersion_ID", de.metas.tourplanning.model.I_M_TourVersion.class);
    /** Column name M_TourVersion_ID */
    public static final String COLUMNNAME_M_TourVersion_ID = "M_TourVersion_ID";

	/**
	 * Set Tour Version Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_TourVersionLine_ID (int M_TourVersionLine_ID);

	/**
	 * Get Tour Version Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_TourVersionLine_ID();

    /** Column definition for M_TourVersionLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object> COLUMN_M_TourVersionLine_ID = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object>(I_M_TourVersionLine.class, "M_TourVersionLine_ID", null);
    /** Column name M_TourVersionLine_ID */
    public static final String COLUMNNAME_M_TourVersionLine_ID = "M_TourVersionLine_ID";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object>(I_M_TourVersionLine.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, Object>(I_M_TourVersionLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_TourVersionLine, org.compiere.model.I_AD_User>(I_M_TourVersionLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
