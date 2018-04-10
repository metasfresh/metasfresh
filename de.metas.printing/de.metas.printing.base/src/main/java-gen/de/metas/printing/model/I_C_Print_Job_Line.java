package de.metas.printing.model;


/** Generated Interface for C_Print_Job_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Print_Job_Line 
{

    /** TableName=C_Print_Job_Line */
    public static final String Table_Name = "C_Print_Job_Line";

    /** AD_Table_ID=540436 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant fĂĽr diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, org.compiere.model.I_AD_Client>(I_C_Print_Job_Line.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, org.compiere.model.I_AD_Org>(I_C_Print_Job_Line.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Druck-Warteschlangendatensatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID);

	/**
	 * Get Druck-Warteschlangendatensatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Printing_Queue_ID();

	public de.metas.printing.model.I_C_Printing_Queue getC_Printing_Queue();

	public void setC_Printing_Queue(de.metas.printing.model.I_C_Printing_Queue C_Printing_Queue);

    /** Column definition for C_Printing_Queue_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, de.metas.printing.model.I_C_Printing_Queue> COLUMN_C_Printing_Queue_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, de.metas.printing.model.I_C_Printing_Queue>(I_C_Print_Job_Line.class, "C_Printing_Queue_ID", de.metas.printing.model.I_C_Printing_Queue.class);
    /** Column name C_Printing_Queue_ID */
    public static final String COLUMNNAME_C_Printing_Queue_ID = "C_Printing_Queue_ID";

	/**
	 * Set Druck-Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Job_ID (int C_Print_Job_ID);

	/**
	 * Get Druck-Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Job_ID();

	public de.metas.printing.model.I_C_Print_Job getC_Print_Job();

	public void setC_Print_Job(de.metas.printing.model.I_C_Print_Job C_Print_Job);

    /** Column definition for C_Print_Job_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, de.metas.printing.model.I_C_Print_Job> COLUMN_C_Print_Job_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, de.metas.printing.model.I_C_Print_Job>(I_C_Print_Job_Line.class, "C_Print_Job_ID", de.metas.printing.model.I_C_Print_Job.class);
    /** Column name C_Print_Job_ID */
    public static final String COLUMNNAME_C_Print_Job_ID = "C_Print_Job_ID";

	/**
	 * Set Druck-Job Position.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Job_Line_ID (int C_Print_Job_Line_ID);

	/**
	 * Get Druck-Job Position.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Job_Line_ID();

    /** Column definition for C_Print_Job_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, Object> COLUMN_C_Print_Job_Line_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, Object>(I_C_Print_Job_Line.class, "C_Print_Job_Line_ID", null);
    /** Column name C_Print_Job_Line_ID */
    public static final String COLUMNNAME_C_Print_Job_Line_ID = "C_Print_Job_Line_ID";

	/**
	 * Set Druckpaket.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Package_ID (int C_Print_Package_ID);

	/**
	 * Get Druckpaket.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Package_ID();

	public de.metas.printing.model.I_C_Print_Package getC_Print_Package();

	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package);

    /** Column definition for C_Print_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, de.metas.printing.model.I_C_Print_Package> COLUMN_C_Print_Package_ID = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, de.metas.printing.model.I_C_Print_Package>(I_C_Print_Job_Line.class, "C_Print_Package_ID", de.metas.printing.model.I_C_Print_Package.class);
    /** Column name C_Print_Package_ID */
    public static final String COLUMNNAME_C_Print_Package_ID = "C_Print_Package_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, Object>(I_C_Print_Job_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, org.compiere.model.I_AD_User>(I_C_Print_Job_Line.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, Object>(I_C_Print_Job_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, Object>(I_C_Print_Job_Line.class, "SeqNo", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, Object>(I_C_Print_Job_Line.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Line, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Print_Job_Line, org.compiere.model.I_AD_User>(I_C_Print_Job_Line.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
