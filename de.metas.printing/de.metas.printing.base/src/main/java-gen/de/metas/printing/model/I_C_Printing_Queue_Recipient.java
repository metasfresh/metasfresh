package de.metas.printing.model;


/** Generated Interface for C_Printing_Queue_Recipient
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Printing_Queue_Recipient 
{

    /** TableName=C_Printing_Queue_Recipient */
    public static final String Table_Name = "C_Printing_Queue_Recipient";

    /** AD_Table_ID=540681 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, org.compiere.model.I_AD_Client>(I_C_Printing_Queue_Recipient.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, org.compiere.model.I_AD_Org>(I_C_Printing_Queue_Recipient.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ausdruck für.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ToPrint_ID (int AD_User_ToPrint_ID);

	/**
	 * Get Ausdruck für.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ToPrint_ID();

	public org.compiere.model.I_AD_User getAD_User_ToPrint();

	public void setAD_User_ToPrint(org.compiere.model.I_AD_User AD_User_ToPrint);

    /** Column definition for AD_User_ToPrint_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, org.compiere.model.I_AD_User> COLUMN_AD_User_ToPrint_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, org.compiere.model.I_AD_User>(I_C_Printing_Queue_Recipient.class, "AD_User_ToPrint_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ToPrint_ID */
    public static final String COLUMNNAME_AD_User_ToPrint_ID = "AD_User_ToPrint_ID";

	/**
	 * Set Druck-Warteschlangendatensatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID);

	/**
	 * Get Druck-Warteschlangendatensatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Printing_Queue_ID();

	public de.metas.printing.model.I_C_Printing_Queue getC_Printing_Queue();

	public void setC_Printing_Queue(de.metas.printing.model.I_C_Printing_Queue C_Printing_Queue);

    /** Column definition for C_Printing_Queue_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, de.metas.printing.model.I_C_Printing_Queue> COLUMN_C_Printing_Queue_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, de.metas.printing.model.I_C_Printing_Queue>(I_C_Printing_Queue_Recipient.class, "C_Printing_Queue_ID", de.metas.printing.model.I_C_Printing_Queue.class);
    /** Column name C_Printing_Queue_ID */
    public static final String COLUMNNAME_C_Printing_Queue_ID = "C_Printing_Queue_ID";

	/**
	 * Set Druck-Empfänger.
	 * Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Printing_Queue_Recipient_ID (int C_Printing_Queue_Recipient_ID);

	/**
	 * Get Druck-Empfänger.
	 * Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Printing_Queue_Recipient_ID();

    /** Column definition for C_Printing_Queue_Recipient_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, Object> COLUMN_C_Printing_Queue_Recipient_ID = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, Object>(I_C_Printing_Queue_Recipient.class, "C_Printing_Queue_Recipient_ID", null);
    /** Column name C_Printing_Queue_Recipient_ID */
    public static final String COLUMNNAME_C_Printing_Queue_Recipient_ID = "C_Printing_Queue_Recipient_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, Object>(I_C_Printing_Queue_Recipient.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, org.compiere.model.I_AD_User>(I_C_Printing_Queue_Recipient.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, Object>(I_C_Printing_Queue_Recipient.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, Object>(I_C_Printing_Queue_Recipient.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Printing_Queue_Recipient, org.compiere.model.I_AD_User>(I_C_Printing_Queue_Recipient.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
