package de.metas.material.dispo.model;


/** Generated Interface for MD_Candidate_Dist_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Candidate_Dist_Detail 
{

    /** TableName=MD_Candidate_Dist_Detail */
    public static final String Table_Name = "MD_Candidate_Dist_Detail";

    /** AD_Table_ID=540821 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_ActualQty = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object>(I_MD_Candidate_Dist_Detail.class, "ActualQty", null);
    /** Column name ActualQty */
    public static final String COLUMNNAME_ActualQty = "ActualQty";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_AD_Client>(I_MD_Candidate_Dist_Detail.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_AD_Org>(I_MD_Candidate_Dist_Detail.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object>(I_MD_Candidate_Dist_Detail.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_AD_User>(I_MD_Candidate_Dist_Detail.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Network Distribution Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDD_NetworkDistributionLine_ID (int DD_NetworkDistributionLine_ID);

	/**
	 * Get Network Distribution Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDD_NetworkDistributionLine_ID();

	public org.eevolution.model.I_DD_NetworkDistributionLine getDD_NetworkDistributionLine();

	public void setDD_NetworkDistributionLine(org.eevolution.model.I_DD_NetworkDistributionLine DD_NetworkDistributionLine);

    /** Column definition for DD_NetworkDistributionLine_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_NetworkDistributionLine> COLUMN_DD_NetworkDistributionLine_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_NetworkDistributionLine>(I_MD_Candidate_Dist_Detail.class, "DD_NetworkDistributionLine_ID", org.eevolution.model.I_DD_NetworkDistributionLine.class);
    /** Column name DD_NetworkDistributionLine_ID */
    public static final String COLUMNNAME_DD_NetworkDistributionLine_ID = "DD_NetworkDistributionLine_ID";

	/**
	 * Set Belegstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDD_Order_DocStatus (java.lang.String DD_Order_DocStatus);

	/**
	 * Get Belegstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDD_Order_DocStatus();

    /** Column definition for DD_Order_DocStatus */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_DD_Order_DocStatus = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object>(I_MD_Candidate_Dist_Detail.class, "DD_Order_DocStatus", null);
    /** Column name DD_Order_DocStatus */
    public static final String COLUMNNAME_DD_Order_DocStatus = "DD_Order_DocStatus";

	/**
	 * Set Distribution Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDD_Order_ID (int DD_Order_ID);

	/**
	 * Get Distribution Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDD_Order_ID();

	public org.eevolution.model.I_DD_Order getDD_Order();

	public void setDD_Order(org.eevolution.model.I_DD_Order DD_Order);

    /** Column definition for DD_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_Order> COLUMN_DD_Order_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_Order>(I_MD_Candidate_Dist_Detail.class, "DD_Order_ID", org.eevolution.model.I_DD_Order.class);
    /** Column name DD_Order_ID */
    public static final String COLUMNNAME_DD_Order_ID = "DD_Order_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDD_OrderLine_ID();

	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	public void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine);

    /** Column definition for DD_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_DD_OrderLine>(I_MD_Candidate_Dist_Detail.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object>(I_MD_Candidate_Dist_Detail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Vom System vorgeschlagen.
	 * Ja bedeutet, dass es zumindest ursprünglich kein entsprechendes Dokument (z.B. Produktionsauftrag) gab, sondern dass das System einen Beleg vorgeschlagen hatte.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAdvised (boolean IsAdvised);

	/**
	 * Get Vom System vorgeschlagen.
	 * Ja bedeutet, dass es zumindest ursprünglich kein entsprechendes Dokument (z.B. Produktionsauftrag) gab, sondern dass das System einen Beleg vorgeschlagen hatte.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAdvised();

    /** Column definition for IsAdvised */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_IsAdvised = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object>(I_MD_Candidate_Dist_Detail.class, "IsAdvised", null);
    /** Column name IsAdvised */
    public static final String COLUMNNAME_IsAdvised = "IsAdvised";

	/**
	 * Set Dispo-Bereitstellungsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_Dist_Detail_ID (int MD_Candidate_Dist_Detail_ID);

	/**
	 * Get Dispo-Bereitstellungsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_Dist_Detail_ID();

    /** Column definition for MD_Candidate_Dist_Detail_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_MD_Candidate_Dist_Detail_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object>(I_MD_Candidate_Dist_Detail.class, "MD_Candidate_Dist_Detail_ID", null);
    /** Column name MD_Candidate_Dist_Detail_ID */
    public static final String COLUMNNAME_MD_Candidate_Dist_Detail_ID = "MD_Candidate_Dist_Detail_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, de.metas.material.dispo.model.I_MD_Candidate>(I_MD_Candidate_Dist_Detail.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
    /** Column name MD_Candidate_ID */
    public static final String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_M_Shipper>(I_MD_Candidate_Dist_Detail.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_PlannedQty = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object>(I_MD_Candidate_Dist_Detail.class, "PlannedQty", null);
    /** Column name PlannedQty */
    public static final String COLUMNNAME_PlannedQty = "PlannedQty";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_S_Resource>(I_MD_Candidate_Dist_Detail.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
    /** Column name PP_Plant_ID */
    public static final String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_PP_Product_Planning> COLUMN_PP_Product_Planning_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.eevolution.model.I_PP_Product_Planning>(I_MD_Candidate_Dist_Detail.class, "PP_Product_Planning_ID", org.eevolution.model.I_PP_Product_Planning.class);
    /** Column name PP_Product_Planning_ID */
    public static final String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, Object>(I_MD_Candidate_Dist_Detail.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MD_Candidate_Dist_Detail, org.compiere.model.I_AD_User>(I_MD_Candidate_Dist_Detail.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
