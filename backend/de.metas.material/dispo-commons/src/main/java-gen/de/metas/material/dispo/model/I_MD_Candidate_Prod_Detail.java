package de.metas.material.dispo.model;


/** Generated Interface for MD_Candidate_Prod_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Candidate_Prod_Detail 
{

    /** TableName=MD_Candidate_Prod_Detail */
    public static final String Table_Name = "MD_Candidate_Prod_Detail";

    /** AD_Table_ID=540810 */
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_ActualQty = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "ActualQty", null);
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "Created", null);
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
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Geplant.
	 * Ja bedeutet, dass es zumindest ursprünglich kein entsprechendes Dokument (z.B. Produktionsauftrag) gab, sondern dass das System einen Beleg vorgeschlagen hatte.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAdvised (boolean IsAdvised);

	/**
	 * Get Geplant.
	 * Ja bedeutet, dass es zumindest ursprünglich kein entsprechendes Dokument (z.B. Produktionsauftrag) gab, sondern dass das System einen Beleg vorgeschlagen hatte.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAdvised();

    /** Column definition for IsAdvised */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_IsAdvised = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "IsAdvised", null);
    /** Column name IsAdvised */
    public static final String COLUMNNAME_IsAdvised = "IsAdvised";

	/**
	 * Set Sofort Kommissionieren wenn möglich.
	 * Falls "Ja" und ein Bestand wird für einen bestimmten Lieferdispo-Eintrag bereit gestellt oder produziert, dann wird dieser sofort zugeordnet und als kommissioniert markiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPickDirectlyIfFeasible (boolean IsPickDirectlyIfFeasible);

	/**
	 * Get Sofort Kommissionieren wenn möglich.
	 * Falls "Ja" und ein Bestand wird für einen bestimmten Lieferdispo-Eintrag bereit gestellt oder produziert, dann wird dieser sofort zugeordnet und als kommissioniert markiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPickDirectlyIfFeasible();

    /** Column definition for IsPickDirectlyIfFeasible */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_IsPickDirectlyIfFeasible = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "IsPickDirectlyIfFeasible", null);
    /** Column name IsPickDirectlyIfFeasible */
    public static final String COLUMNNAME_IsPickDirectlyIfFeasible = "IsPickDirectlyIfFeasible";

	/**
	 * Set Dispositionskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispositionskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_ID();

	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate();

	public void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate);

    /** Column definition for MD_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, de.metas.material.dispo.model.I_MD_Candidate>(I_MD_Candidate_Prod_Detail.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
    /** Column name MD_Candidate_ID */
    public static final String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Dispo-Produktionsdetails.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_Prod_Detail_ID (int MD_Candidate_Prod_Detail_ID);

	/**
	 * Get Dispo-Produktionsdetails.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_Prod_Detail_ID();

    /** Column definition for MD_Candidate_Prod_Detail_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_MD_Candidate_Prod_Detail_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "MD_Candidate_Prod_Detail_ID", null);
    /** Column name MD_Candidate_Prod_Detail_ID */
    public static final String COLUMNNAME_MD_Candidate_Prod_Detail_ID = "MD_Candidate_Prod_Detail_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_PlannedQty = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "PlannedQty", null);
    /** Column name PlannedQty */
    public static final String COLUMNNAME_PlannedQty = "PlannedQty";

	/**
	 * Set Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID);

	/**
	 * Get Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_BOMLine_ID();

	public org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine();

	public void setPP_Order_BOMLine(org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine);

    /** Column definition for PP_Order_BOMLine_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Order_BOMLine> COLUMN_PP_Order_BOMLine_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Order_BOMLine>(I_MD_Candidate_Prod_Detail.class, "PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order_BOMLine.class);
    /** Column name PP_Order_BOMLine_ID */
    public static final String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

	/**
	 * Set Belegstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_DocStatus (java.lang.String PP_Order_DocStatus);

	/**
	 * Get Belegstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPP_Order_DocStatus();

    /** Column definition for PP_Order_DocStatus */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_PP_Order_DocStatus = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "PP_Order_DocStatus", null);
    /** Column name PP_Order_DocStatus */
    public static final String COLUMNNAME_PP_Order_DocStatus = "PP_Order_DocStatus";

	/**
	 * Set Produktionsauftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Produktionsauftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order();

	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

    /** Column definition for PP_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Order>(I_MD_Candidate_Prod_Detail.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Produktionsstätte.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Plant_ID (int PP_Plant_ID);

	/**
	 * Get Produktionsstätte.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Plant_ID();

	public org.compiere.model.I_S_Resource getPP_Plant();

	public void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant);

    /** Column definition for PP_Plant_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_S_Resource>(I_MD_Candidate_Prod_Detail.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
    /** Column name PP_Plant_ID */
    public static final String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

	/**
	 * Set BOM Line.
	 * BOM Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID);

	/**
	 * Get BOM Line.
	 * BOM Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Product_BOMLine_ID();

	public org.eevolution.model.I_PP_Product_BOMLine getPP_Product_BOMLine();

	public void setPP_Product_BOMLine(org.eevolution.model.I_PP_Product_BOMLine PP_Product_BOMLine);

    /** Column definition for PP_Product_BOMLine_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Product_BOMLine> COLUMN_PP_Product_BOMLine_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Product_BOMLine>(I_MD_Candidate_Prod_Detail.class, "PP_Product_BOMLine_ID", org.eevolution.model.I_PP_Product_BOMLine.class);
    /** Column name PP_Product_BOMLine_ID */
    public static final String COLUMNNAME_PP_Product_BOMLine_ID = "PP_Product_BOMLine_ID";

	/**
	 * Set Product Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Product_Planning_ID (int PP_Product_Planning_ID);

	/**
	 * Get Product Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Product_Planning_ID();

	public org.eevolution.model.I_PP_Product_Planning getPP_Product_Planning();

	public void setPP_Product_Planning(org.eevolution.model.I_PP_Product_Planning PP_Product_Planning);

    /** Column definition for PP_Product_Planning_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Product_Planning> COLUMN_PP_Product_Planning_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.eevolution.model.I_PP_Product_Planning>(I_MD_Candidate_Prod_Detail.class, "PP_Product_Planning_ID", org.eevolution.model.I_PP_Product_Planning.class);
    /** Column name PP_Product_Planning_ID */
    public static final String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "Updated", null);
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
