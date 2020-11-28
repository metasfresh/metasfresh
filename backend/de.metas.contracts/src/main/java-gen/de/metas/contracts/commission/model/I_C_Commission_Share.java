/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.contracts.commission.model;


/** Generated Interface for C_Commission_Share
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Commission_Share 
{

    /** TableName=C_Commission_Share */
    public static final String Table_Name = "C_Commission_Share";

    /** AD_Table_ID=541406 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
	public void setAD_Org_ID(int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Zugeordneter Vertriebspartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_SalesRep_ID(int C_BPartner_SalesRep_ID);

	/**
	 * Get Zugeordneter Vertriebspartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_SalesRep_ID();

    /** Column name C_BPartner_SalesRep_ID */
    public static final String COLUMNNAME_C_BPartner_SalesRep_ID = "C_BPartner_SalesRep_ID";

	/**
	 * Set Provisionsvorgang.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Commission_Instance_ID(int C_Commission_Instance_ID);

	/**
	 * Get Provisionsvorgang.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Commission_Instance_ID();

	public I_C_Commission_Instance getC_Commission_Instance();

	public void setC_Commission_Instance(I_C_Commission_Instance C_Commission_Instance);

    /** Column definition for C_Commission_Instance_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, I_C_Commission_Instance> COLUMN_C_Commission_Instance_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Share, I_C_Commission_Instance>(I_C_Commission_Share.class, "C_Commission_Instance_ID", I_C_Commission_Instance.class);
    /** Column name C_Commission_Instance_ID */
    public static final String COLUMNNAME_C_Commission_Instance_ID = "C_Commission_Instance_ID";

	/**
	 * Set Buchauszug.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Commission_Share_ID(int C_Commission_Share_ID);

	/**
	 * Get Buchauszug.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Commission_Share_ID();

    /** Column definition for C_Commission_Share_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_C_Commission_Share_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "C_Commission_Share_ID", null);
    /** Column name C_Commission_Share_ID */
    public static final String COLUMNNAME_C_Commission_Share_ID = "C_Commission_Share_ID";

	/**
	 * Set Einstellungsdetail.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_CommissionSettingsLine_ID(int C_CommissionSettingsLine_ID);

	/**
	 * Get Einstellungsdetail.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_CommissionSettingsLine_ID();

	public I_C_CommissionSettingsLine getC_CommissionSettingsLine();

	public void setC_CommissionSettingsLine(I_C_CommissionSettingsLine C_CommissionSettingsLine);

    /** Column definition for C_CommissionSettingsLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, I_C_CommissionSettingsLine> COLUMN_C_CommissionSettingsLine_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Share, I_C_CommissionSettingsLine>(I_C_Commission_Share.class, "C_CommissionSettingsLine_ID", I_C_CommissionSettingsLine.class);
    /** Column name C_CommissionSettingsLine_ID */
    public static final String COLUMNNAME_C_CommissionSettingsLine_ID = "C_CommissionSettingsLine_ID";

	/**
	 * Set Pauschale - Vertragsperiode.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Term_ID(int C_Flatrate_Term_ID);

	/**
	 * Get Pauschale - Vertragsperiode.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Term_ID();

    /** Column definition for C_Flatrate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_C_Flatrate_Term_ID = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "C_Flatrate_Term_ID", null);
    /** Column name C_Flatrate_Term_ID */
    public static final String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Provisionsprodukt.
	 * Produkt in dessen Einheit Provisionspunkte geführt und abgerechnet werden
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCommission_Product_ID(int Commission_Product_ID);

	/**
	 * Get Provisionsprodukt.
	 * Produkt in dessen Einheit Provisionspunkte geführt und abgerechnet werden
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCommission_Product_ID();

    /** Column name Commission_Product_ID */
    public static final String COLUMNNAME_Commission_Product_ID = "Commission_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "Created", null);
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
	public void setIsActive(boolean IsActive);

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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Planspiel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSimulation(boolean IsSimulation);

	/**
	 * Get Planspiel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSimulation();

    /** Column definition for IsSimulation */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_IsSimulation = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "IsSimulation", null);
    /** Column name IsSimulation */
    public static final String COLUMNNAME_IsSimulation = "IsSimulation";

	/**
	 * Set Hierarchie-Ebene.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLevelHierarchy(int LevelHierarchy);

	/**
	 * Get Hierarchie-Ebene.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLevelHierarchy();

    /** Column definition for LevelHierarchy */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_LevelHierarchy = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "LevelHierarchy", null);
    /** Column name LevelHierarchy */
    public static final String COLUMNNAME_LevelHierarchy = "LevelHierarchy";

	/**
	 * Set Beauftragte Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPointsSum_Forecasted(java.math.BigDecimal PointsSum_Forecasted);

	/**
	 * Get Beauftragte Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsSum_Forecasted();

    /** Column definition for PointsSum_Forecasted */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_PointsSum_Forecasted = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "PointsSum_Forecasted", null);
    /** Column name PointsSum_Forecasted */
    public static final String COLUMNNAME_PointsSum_Forecasted = "PointsSum_Forecasted";

	/**
	 * Set Fakturierbare Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPointsSum_Invoiceable(java.math.BigDecimal PointsSum_Invoiceable);

	/**
	 * Get Fakturierbare Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsSum_Invoiceable();

    /** Column definition for PointsSum_Invoiceable */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_PointsSum_Invoiceable = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "PointsSum_Invoiceable", null);
    /** Column name PointsSum_Invoiceable */
    public static final String COLUMNNAME_PointsSum_Invoiceable = "PointsSum_Invoiceable";

	/**
	 * Set Fakturierte Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPointsSum_Invoiced(java.math.BigDecimal PointsSum_Invoiced);

	/**
	 * Get Fakturierte Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsSum_Invoiced();

    /** Column definition for PointsSum_Invoiced */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_PointsSum_Invoiced = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "PointsSum_Invoiced", null);
    /** Column name PointsSum_Invoiced */
    public static final String COLUMNNAME_PointsSum_Invoiced = "PointsSum_Invoiced";

	/**
	 * Set Abgerechnete Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPointsSum_Settled(java.math.BigDecimal PointsSum_Settled);

	/**
	 * Get Abgerechnete Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsSum_Settled();

    /** Column definition for PointsSum_Settled */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_PointsSum_Settled = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "PointsSum_Settled", null);
    /** Column name PointsSum_Settled */
    public static final String COLUMNNAME_PointsSum_Settled = "PointsSum_Settled";

	/**
	 * Set Abzurechnende Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPointsSum_ToSettle(java.math.BigDecimal PointsSum_ToSettle);

	/**
	 * Get Abzurechnende Punktzahl.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPointsSum_ToSettle();

    /** Column definition for PointsSum_ToSettle */
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_PointsSum_ToSettle = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "PointsSum_ToSettle", null);
    /** Column name PointsSum_ToSettle */
    public static final String COLUMNNAME_PointsSum_ToSettle = "PointsSum_ToSettle";

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
    public static final org.adempiere.model.ModelColumn<I_C_Commission_Share, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Commission_Share, Object>(I_C_Commission_Share.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
