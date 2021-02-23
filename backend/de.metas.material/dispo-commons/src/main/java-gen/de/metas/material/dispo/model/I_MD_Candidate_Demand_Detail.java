package de.metas.material.dispo.model;


/** Generated Interface for MD_Candidate_Demand_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Candidate_Demand_Detail 
{

    /** TableName=MD_Candidate_Demand_Detail */
    public static final String Table_Name = "MD_Candidate_Demand_Detail";

    /** AD_Table_ID=540815 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Istmenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setActualQty (java.math.BigDecimal ActualQty);

	/**
	 * Get Istmenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getActualQty();

    /** Column definition for ActualQty */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object> COLUMN_ActualQty = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object>(I_MD_Candidate_Demand_Detail.class, "ActualQty", null);
    /** Column name ActualQty */
    public static final String COLUMNNAME_ActualQty = "ActualQty";

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
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, org.compiere.model.I_C_OrderLine>(I_MD_Candidate_Demand_Detail.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object>(I_MD_Candidate_Demand_Detail.class, "Created", null);
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
	 * Set Abo-Verlauf.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_SubscriptionProgress_ID (int C_SubscriptionProgress_ID);

	/**
	 * Get Abo-Verlauf.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_SubscriptionProgress_ID();

    /** Column definition for C_SubscriptionProgress_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object> COLUMN_C_SubscriptionProgress_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object>(I_MD_Candidate_Demand_Detail.class, "C_SubscriptionProgress_ID", null);
    /** Column name C_SubscriptionProgress_ID */
    public static final String COLUMNNAME_C_SubscriptionProgress_ID = "C_SubscriptionProgress_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object>(I_MD_Candidate_Demand_Detail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Dispo-Warenausgangsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_Demand_Detail_ID (int MD_Candidate_Demand_Detail_ID);

	/**
	 * Get Dispo-Warenausgangsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_Demand_Detail_ID();

    /** Column definition for MD_Candidate_Demand_Detail_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object> COLUMN_MD_Candidate_Demand_Detail_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object>(I_MD_Candidate_Demand_Detail.class, "MD_Candidate_Demand_Detail_ID", null);
    /** Column name MD_Candidate_Demand_Detail_ID */
    public static final String COLUMNNAME_MD_Candidate_Demand_Detail_ID = "MD_Candidate_Demand_Detail_ID";

	/**
	 * Set Dispositionskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispositionskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_ID();

	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate();

	public void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate);

    /** Column definition for MD_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, de.metas.material.dispo.model.I_MD_Candidate>(I_MD_Candidate_Demand_Detail.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
    /** Column name MD_Candidate_ID */
    public static final String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Prognose-Position.
	 * Prognose-Position
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ForecastLine_ID (int M_ForecastLine_ID);

	/**
	 * Get Prognose-Position.
	 * Prognose-Position
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ForecastLine_ID();

	public org.compiere.model.I_M_ForecastLine getM_ForecastLine();

	public void setM_ForecastLine(org.compiere.model.I_M_ForecastLine M_ForecastLine);

    /** Column definition for M_ForecastLine_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, org.compiere.model.I_M_ForecastLine> COLUMN_M_ForecastLine_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, org.compiere.model.I_M_ForecastLine>(I_MD_Candidate_Demand_Detail.class, "M_ForecastLine_ID", org.compiere.model.I_M_ForecastLine.class);
    /** Column name M_ForecastLine_ID */
    public static final String COLUMNNAME_M_ForecastLine_ID = "M_ForecastLine_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ID();

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object>(I_MD_Candidate_Demand_Detail.class, "M_ShipmentSchedule_ID", null);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Geplante Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPlannedQty (java.math.BigDecimal PlannedQty);

	/**
	 * Get Geplante Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPlannedQty();

    /** Column definition for PlannedQty */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object> COLUMN_PlannedQty = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object>(I_MD_Candidate_Demand_Detail.class, "PlannedQty", null);
    /** Column name PlannedQty */
    public static final String COLUMNNAME_PlannedQty = "PlannedQty";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MD_Candidate_Demand_Detail, Object>(I_MD_Candidate_Demand_Detail.class, "Updated", null);
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
