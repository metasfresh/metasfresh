package de.metas.inoutcandidate.model;


/** Generated Interface for M_Shipment_Constraint
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Shipment_Constraint 
{

    /** TableName=M_Shipment_Constraint */
    public static final String Table_Name = "M_Shipment_Constraint";

    /** AD_Table_ID=540845 */
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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_AD_Client>(I_M_Shipment_Constraint.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_AD_Org>(I_M_Shipment_Constraint.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rechnungspartner.
	 * Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Rechnungspartner.
	 * Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_BPartner_ID();

	public org.compiere.model.I_C_BPartner getBill_BPartner();

	public void setBill_BPartner(org.compiere.model.I_C_BPartner Bill_BPartner);

    /** Column definition for Bill_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_C_BPartner> COLUMN_Bill_BPartner_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_C_BPartner>(I_M_Shipment_Constraint.class, "Bill_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object>(I_M_Shipment_Constraint.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_AD_User>(I_M_Shipment_Constraint.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object>(I_M_Shipment_Constraint.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_IsDeliveryStop = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object>(I_M_Shipment_Constraint.class, "IsDeliveryStop", null);
    /** Column name IsDeliveryStop */
    public static final String COLUMNNAME_IsDeliveryStop = "IsDeliveryStop";

	/**
	 * Set Shipment constraint.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Shipment_Constraint_ID (int M_Shipment_Constraint_ID);

	/**
	 * Get Shipment constraint.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Shipment_Constraint_ID();

    /** Column definition for M_Shipment_Constraint_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_M_Shipment_Constraint_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object>(I_M_Shipment_Constraint.class, "M_Shipment_Constraint_ID", null);
    /** Column name M_Shipment_Constraint_ID */
    public static final String COLUMNNAME_M_Shipment_Constraint_ID = "M_Shipment_Constraint_ID";

	/**
	 * Set Source document.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSourceDoc_Record_ID (int SourceDoc_Record_ID);

	/**
	 * Get Source document.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSourceDoc_Record_ID();

    /** Column definition for SourceDoc_Record_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_SourceDoc_Record_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object>(I_M_Shipment_Constraint.class, "SourceDoc_Record_ID", null);
    /** Column name SourceDoc_Record_ID */
    public static final String COLUMNNAME_SourceDoc_Record_ID = "SourceDoc_Record_ID";

	/**
	 * Set Source document (table).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSourceDoc_Table_ID (int SourceDoc_Table_ID);

	/**
	 * Get Source document (table).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSourceDoc_Table_ID();

	public org.compiere.model.I_AD_Table getSourceDoc_Table();

	public void setSourceDoc_Table(org.compiere.model.I_AD_Table SourceDoc_Table);

    /** Column definition for SourceDoc_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_AD_Table> COLUMN_SourceDoc_Table_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_AD_Table>(I_M_Shipment_Constraint.class, "SourceDoc_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name SourceDoc_Table_ID */
    public static final String COLUMNNAME_SourceDoc_Table_ID = "SourceDoc_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object>(I_M_Shipment_Constraint.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_AD_User>(I_M_Shipment_Constraint.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
