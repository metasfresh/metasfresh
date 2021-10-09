package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_BestBefore_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_BestBefore_V 
{

	String Table_Name = "M_HU_BestBefore_V";

//	/** AD_Table_ID=540942 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_BestBefore_V.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Min. Garantie-Tage.
	 * Mindestanzahl Garantie-Tage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGuaranteeDaysMin (int GuaranteeDaysMin);

	/**
	 * Get Min. Garantie-Tage.
	 * Mindestanzahl Garantie-Tage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGuaranteeDaysMin();

	ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_GuaranteeDaysMin = new ModelColumn<>(I_M_HU_BestBefore_V.class, "GuaranteeDaysMin", null);
	String COLUMNNAME_GuaranteeDaysMin = "GuaranteeDaysMin";

	/**
	 * Set HU best before date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHU_BestBeforeDate (@Nullable java.sql.Timestamp HU_BestBeforeDate);

	/**
	 * Get HU best before date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getHU_BestBeforeDate();

	ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_HU_BestBeforeDate = new ModelColumn<>(I_M_HU_BestBefore_V.class, "HU_BestBeforeDate", null);
	String COLUMNNAME_HU_BestBeforeDate = "HU_BestBeforeDate";

	/**
	 * Set Expired.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHU_Expired (@Nullable java.lang.String HU_Expired);

	/**
	 * Get Expired.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHU_Expired();

	ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_HU_Expired = new ModelColumn<>(I_M_HU_BestBefore_V.class, "HU_Expired", null);
	String COLUMNNAME_HU_Expired = "HU_Expired";

	/**
	 * Set hu_expiredwarndate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHU_ExpiredWarnDate (@Nullable java.sql.Timestamp HU_ExpiredWarnDate);

	/**
	 * Get hu_expiredwarndate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getHU_ExpiredWarnDate();

	ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_HU_ExpiredWarnDate = new ModelColumn<>(I_M_HU_BestBefore_V.class, "HU_ExpiredWarnDate", null);
	String COLUMNNAME_HU_ExpiredWarnDate = "HU_ExpiredWarnDate";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_BestBefore_V.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	ModelColumn<I_M_HU_BestBefore_V, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU_BestBefore_V.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_BestBefore_V, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_BestBefore_V.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
