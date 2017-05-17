package de.metas.inout.model;

import org.compiere.model.I_M_InventoryLine;

/**
 * Generated Interface for M_InOutLine_HU_Alloc
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_M_InOutLine_HU_Alloc
{

	/** TableName=M_InOutLine_HU_Alloc */
	public static final String Table_Name = "M_InOutLine_HU_Alloc";

	/** AD_Table_ID=540817 */
	// public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

	// org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

	/**
	 * AccessLevel = 7 - System - Client - Org
	 */
	// java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

	/** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

	/** Column definition for AD_Client_ID */
	public static final org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, org.compiere.model.I_AD_Client>(I_M_InOutLine_HU_Alloc.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
	/** Column name AD_Client_ID */
	public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setAD_Org_ID(int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/** Column definition for AD_Org_ID */
	public static final org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, org.compiere.model.I_AD_Org>(I_M_InOutLine_HU_Alloc.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
	/** Column name AD_Org_ID */
	public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

	/** Column definition for Created */
	public static final org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, Object>(I_M_InOutLine_HU_Alloc.class, "Created", null);
	/** Column name Created */
	public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>
	 * Type: Table
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getCreatedBy();

	/** Column definition for CreatedBy */
	public static final org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, org.compiere.model.I_AD_User>(I_M_InOutLine_HU_Alloc.class, "CreatedBy", org.compiere.model.I_AD_User.class);
	/** Column name CreatedBy */
	public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsActive(boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isActive();

	/** Column definition for IsActive */
	public static final org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, Object>(I_M_InOutLine_HU_Alloc.class, "IsActive", null);
	/** Column name IsActive */
	public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set M_InOutLine_HU_Alloc.
	 *
	 * <br>
	 * Type: ID
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setM_InOutLine_HU_Alloc_ID(int M_InOutLine_HU_Alloc_ID);

	/**
	 * Get M_InOutLine_HU_Alloc.
	 *
	 * <br>
	 * Type: ID
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getM_InOutLine_HU_Alloc_ID();

	/** Column definition for M_InOutLine_HU_Alloc_ID */
	public static final org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, Object> COLUMN_M_InOutLine_HU_Alloc_ID = new org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, Object>(I_M_InOutLine_HU_Alloc.class, "M_InOutLine_HU_Alloc_ID", null);
	/** Column name M_InOutLine_HU_Alloc_ID */
	public static final String COLUMNNAME_M_InOutLine_HU_Alloc_ID = "M_InOutLine_HU_Alloc_ID";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

	/** Column definition for Updated */
	public static final org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, Object>(I_M_InOutLine_HU_Alloc.class, "Updated", null);
	/** Column name Updated */
	public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>
	 * Type: Table
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getUpdatedBy();

	/** Column definition for UpdatedBy */
	public static final org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_InOutLine_HU_Alloc, org.compiere.model.I_AD_User>(I_M_InOutLine_HU_Alloc.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
	/** Column name UpdatedBy */
	public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setM_InOutLine_ID(int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

	/** Column definition for M_InOutLine_ID */
	public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_InOutLine>(I_M_InventoryLine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	/** Column name M_InOutLine_ID */
	public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

}
