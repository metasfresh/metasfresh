package de.metas.printing.model;


/** Generated Interface for C_Printing_Queue_Recipient
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Printing_Queue_Recipient 
{

    /** TableName=C_Printing_Queue_Recipient */
    public static final String Table_Name = "C_Printing_Queue_Recipient";

    /** AD_Table_ID=540681 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

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
	 * Get Created.
	 * Date this record was created
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
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
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
	 * Get Updated.
	 * Date this record was updated
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
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
