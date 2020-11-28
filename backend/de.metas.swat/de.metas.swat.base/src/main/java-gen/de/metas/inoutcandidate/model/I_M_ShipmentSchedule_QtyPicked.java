package de.metas.inoutcandidate.model;


/** Generated Interface for M_ShipmentSchedule_QtyPicked
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ShipmentSchedule_QtyPicked 
{

    /** TableName=M_ShipmentSchedule_QtyPicked */
    public static final String Table_Name = "M_ShipmentSchedule_QtyPicked";

    /** AD_Table_ID=540542 */
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
	 * Set Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCatch_UOM_ID (int Catch_UOM_ID);

	/**
	 * Get Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCatch_UOM_ID();

    /** Column name Catch_UOM_ID */
    public static final String COLUMNNAME_Catch_UOM_ID = "Catch_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object>(I_M_ShipmentSchedule_QtyPicked.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object>(I_M_ShipmentSchedule_QtyPicked.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object>(I_M_ShipmentSchedule_QtyPicked.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Anonymous HU Picked On the Fly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAnonymousHuPickedOnTheFly (boolean IsAnonymousHuPickedOnTheFly);

	/**
	 * Get Anonymous HU Picked On the Fly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnonymousHuPickedOnTheFly();

    /** Column definition for IsAnonymousHuPickedOnTheFly */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object> COLUMN_IsAnonymousHuPickedOnTheFly = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object>(I_M_ShipmentSchedule_QtyPicked.class, "IsAnonymousHuPickedOnTheFly", null);
    /** Column name IsAnonymousHuPickedOnTheFly */
    public static final String COLUMNNAME_IsAnonymousHuPickedOnTheFly = "IsAnonymousHuPickedOnTheFly";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, org.compiere.model.I_M_InOutLine>(I_M_ShipmentSchedule_QtyPicked.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ID();

	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule();

	public void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule);

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, de.metas.inoutcandidate.model.I_M_ShipmentSchedule>(I_M_ShipmentSchedule_QtyPicked.class, "M_ShipmentSchedule_ID", de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set ShipmentSchedule QtyPicked.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_QtyPicked_ID (int M_ShipmentSchedule_QtyPicked_ID);

	/**
	 * Get ShipmentSchedule QtyPicked.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_QtyPicked_ID();

    /** Column definition for M_ShipmentSchedule_QtyPicked_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object> COLUMN_M_ShipmentSchedule_QtyPicked_ID = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object>(I_M_ShipmentSchedule_QtyPicked.class, "M_ShipmentSchedule_QtyPicked_ID", null);
    /** Column name M_ShipmentSchedule_QtyPicked_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID = "M_ShipmentSchedule_QtyPicked_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object>(I_M_ShipmentSchedule_QtyPicked.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Delivered catch.
	 * Actually delivered quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDeliveredCatch (java.math.BigDecimal QtyDeliveredCatch);

	/**
	 * Get Delivered catch.
	 * Actually delivered quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDeliveredCatch();

    /** Column definition for QtyDeliveredCatch */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object> COLUMN_QtyDeliveredCatch = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object>(I_M_ShipmentSchedule_QtyPicked.class, "QtyDeliveredCatch", null);
    /** Column name QtyDeliveredCatch */
    public static final String COLUMNNAME_QtyDeliveredCatch = "QtyDeliveredCatch";

	/**
	 * Set Quantity (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyPicked (java.math.BigDecimal QtyPicked);

	/**
	 * Get Quantity (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPicked();

    /** Column definition for QtyPicked */
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object> COLUMN_QtyPicked = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object>(I_M_ShipmentSchedule_QtyPicked.class, "QtyPicked", null);
    /** Column name QtyPicked */
    public static final String COLUMNNAME_QtyPicked = "QtyPicked";

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
    public static final org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ShipmentSchedule_QtyPicked, Object>(I_M_ShipmentSchedule_QtyPicked.class, "Updated", null);
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
