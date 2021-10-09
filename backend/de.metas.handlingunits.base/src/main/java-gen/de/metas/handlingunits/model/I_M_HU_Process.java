package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Process
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Process 
{

	String Table_Name = "M_HU_Process";

//	/** AD_Table_ID=540607 */
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
	 * Set Prozess.
	 * Prozess oder Bericht
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Prozess oder Bericht
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Process_ID();

	org.compiere.model.I_AD_Process getAD_Process();

	void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

	ModelColumn<I_M_HU_Process, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_M_HU_Process.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_Process, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Process.class, "Created", null);
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

	ModelColumn<I_M_HU_Process, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Process.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Apply to CUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApplyToCUs (boolean IsApplyToCUs);

	/**
	 * Get Apply to CUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApplyToCUs();

	ModelColumn<I_M_HU_Process, Object> COLUMN_IsApplyToCUs = new ModelColumn<>(I_M_HU_Process.class, "IsApplyToCUs", null);
	String COLUMNNAME_IsApplyToCUs = "IsApplyToCUs";

	/**
	 * Set Apply to LUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApplyToLUs (boolean IsApplyToLUs);

	/**
	 * Get Apply to LUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApplyToLUs();

	ModelColumn<I_M_HU_Process, Object> COLUMN_IsApplyToLUs = new ModelColumn<>(I_M_HU_Process.class, "IsApplyToLUs", null);
	String COLUMNNAME_IsApplyToLUs = "IsApplyToLUs";

	/**
	 * Set Nur auf Top-Level HU anwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApplyToTopLevelHUsOnly (boolean IsApplyToTopLevelHUsOnly);

	/**
	 * Get Nur auf Top-Level HU anwenden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApplyToTopLevelHUsOnly();

	ModelColumn<I_M_HU_Process, Object> COLUMN_IsApplyToTopLevelHUsOnly = new ModelColumn<>(I_M_HU_Process.class, "IsApplyToTopLevelHUsOnly", null);
	String COLUMNNAME_IsApplyToTopLevelHUsOnly = "IsApplyToTopLevelHUsOnly";

	/**
	 * Set Apply to TUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApplyToTUs (boolean IsApplyToTUs);

	/**
	 * Get Apply to TUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApplyToTUs();

	ModelColumn<I_M_HU_Process, Object> COLUMN_IsApplyToTUs = new ModelColumn<>(I_M_HU_Process.class, "IsApplyToTUs", null);
	String COLUMNNAME_IsApplyToTUs = "IsApplyToTUs";

	/**
	 * Set Als Benutzeraktion verfügbar machen.
	 * Entscheidet, ob der Prozess als Aktion im Handling-Unit-Editor (WebUI und SwingUI) auswählbar sein soll.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsProvideAsUserAction (boolean IsProvideAsUserAction);

	/**
	 * Get Als Benutzeraktion verfügbar machen.
	 * Entscheidet, ob der Prozess als Aktion im Handling-Unit-Editor (WebUI und SwingUI) auswählbar sein soll.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProvideAsUserAction();

	ModelColumn<I_M_HU_Process, Object> COLUMN_IsProvideAsUserAction = new ModelColumn<>(I_M_HU_Process.class, "IsProvideAsUserAction", null);
	String COLUMNNAME_IsProvideAsUserAction = "IsProvideAsUserAction";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_ID (int M_HU_PI_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_PI getM_HU_PI();

	void setM_HU_PI(@Nullable de.metas.handlingunits.model.I_M_HU_PI M_HU_PI);

	ModelColumn<I_M_HU_Process, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_M_HU_PI_ID = new ModelColumn<>(I_M_HU_Process.class, "M_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
	String COLUMNNAME_M_HU_PI_ID = "M_HU_PI_ID";

	/**
	 * Set Handling Unit Process.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Process_ID (int M_HU_Process_ID);

	/**
	 * Get Handling Unit Process.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Process_ID();

	ModelColumn<I_M_HU_Process, Object> COLUMN_M_HU_Process_ID = new ModelColumn<>(I_M_HU_Process.class, "M_HU_Process_ID", null);
	String COLUMNNAME_M_HU_Process_ID = "M_HU_Process_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_Process, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Process.class, "Updated", null);
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
