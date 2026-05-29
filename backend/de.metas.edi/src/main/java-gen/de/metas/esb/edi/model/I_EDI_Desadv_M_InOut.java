package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for EDI_Desadv_M_InOut
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_EDI_Desadv_M_InOut
{

	String Table_Name = "EDI_Desadv_M_InOut";

//	/** AD_Table_ID=542601 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_Desadv_M_InOut, Object> COLUMN_Created = new ModelColumn<>(I_EDI_Desadv_M_InOut.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_Desadv_ID (int EDI_Desadv_ID);

	/**
	 * Get DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_Desadv_ID();

	de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv();

	void setEDI_Desadv(de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv);

	ModelColumn<I_EDI_Desadv_M_InOut, de.metas.esb.edi.model.I_EDI_Desadv> COLUMN_EDI_Desadv_ID = new ModelColumn<>(I_EDI_Desadv_M_InOut.class, "EDI_Desadv_ID", de.metas.esb.edi.model.I_EDI_Desadv.class);
	String COLUMNNAME_EDI_Desadv_ID = "EDI_Desadv_ID";

	/**
	 * Set EDI_Desadv_M_InOut.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_Desadv_M_InOut_ID (int EDI_Desadv_M_InOut_ID);

	/**
	 * Get EDI_Desadv_M_InOut.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_Desadv_M_InOut_ID();

	String COLUMNNAME_EDI_Desadv_M_InOut_ID = "EDI_Desadv_M_InOut_ID";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Receipt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Receipt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_EDI_Desadv_M_InOut, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_EDI_Desadv_M_InOut.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_Desadv_M_InOut, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_Desadv_M_InOut.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
