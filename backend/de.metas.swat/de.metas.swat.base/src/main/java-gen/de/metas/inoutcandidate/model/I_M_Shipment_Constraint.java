package de.metas.inoutcandidate.model;


/** Generated Interface for M_Shipment_Constraint
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Shipment_Constraint 
{

    /** TableName=M_Shipment_Constraint */
    public static final String Table_Name = "M_Shipment_Constraint";

    /** AD_Table_ID=540845 */
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
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_BPartner_ID();

    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	@Deprecated
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, org.compiere.model.I_C_Invoice>(I_M_Shipment_Constraint.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object>(I_M_Shipment_Constraint.class, "Created", null);
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
	 * Set Gezahlt.
	 * Der Beleg ist bezahlt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsPaid (boolean IsPaid);

	/**
	 * Get Gezahlt.
	 * Der Beleg ist bezahlt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isPaid();

    /** Column definition for IsPaid */
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_IsPaid = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object>(I_M_Shipment_Constraint.class, "IsPaid", null);
    /** Column name IsPaid */
    public static final String COLUMNNAME_IsPaid = "IsPaid";

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

    /** Column name SourceDoc_Table_ID */
    public static final String COLUMNNAME_SourceDoc_Table_ID = "SourceDoc_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Shipment_Constraint, Object>(I_M_Shipment_Constraint.class, "Updated", null);
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
