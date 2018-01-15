package de.metas.material.dispo.model;


/** Generated Interface for MD_Candidate_Prod_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Candidate_Prod_Detail 
{

    /** TableName=MD_Candidate_Prod_Detail */
    public static final String Table_Name = "MD_Candidate_Prod_Detail";

    /** AD_Table_ID=540810 */
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_ActualQty = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "ActualQty", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_AD_Client>(I_MD_Candidate_Prod_Detail.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_AD_Org>(I_MD_Candidate_Prod_Detail.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_AD_User>(I_MD_Candidate_Prod_Detail.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object> COLUMN_IsAdvised = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, Object>(I_MD_Candidate_Prod_Detail.class, "IsAdvised", null);
    /** Column name IsAdvised */
    public static final String COLUMNNAME_IsAdvised = "IsAdvised";

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
	 * Set Dispo-Produktionsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_Prod_Detail_ID (int MD_Candidate_Prod_Detail_ID);

	/**
	 * Get Dispo-Produktionsdetail.
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
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
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
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MD_Candidate_Prod_Detail, org.compiere.model.I_AD_User>(I_MD_Candidate_Prod_Detail.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
