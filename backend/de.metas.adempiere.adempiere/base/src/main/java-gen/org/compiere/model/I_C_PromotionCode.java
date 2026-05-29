package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_PromotionCode
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_C_PromotionCode
{
	String Table_Name = "C_PromotionCode";

	/** AD_Table_ID=542586 */
	int Table_ID = 542586;

	/**
	 * Get Client.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	ModelColumn<I_C_PromotionCode, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new ModelColumn<>(I_C_PromotionCode.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	ModelColumn<I_C_PromotionCode, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new ModelColumn<>(I_C_PromotionCode.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Promotion Code.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PromotionCode_ID (int C_PromotionCode_ID);

	/**
	 * Get Promotion Code.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PromotionCode_ID();

	ModelColumn<I_C_PromotionCode, Object> COLUMN_C_PromotionCode_ID = new ModelColumn<>(I_C_PromotionCode.class, "C_PromotionCode_ID", null);
	String COLUMNNAME_C_PromotionCode_ID = "C_PromotionCode_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_PromotionCode, Object> COLUMN_Description = new ModelColumn<>(I_C_PromotionCode.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Get Created.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_PromotionCode, Object> COLUMN_Created = new ModelColumn<>(I_C_PromotionCode.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	ModelColumn<I_C_PromotionCode, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new ModelColumn<>(I_C_PromotionCode.class, "CreatedBy", org.compiere.model.I_AD_User.class);
	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Active.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_PromotionCode, Object> COLUMN_IsActive = new ModelColumn<>(I_C_PromotionCode.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_PromotionCode, Object> COLUMN_Name = new ModelColumn<>(I_C_PromotionCode.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_PromotionCode, Object> COLUMN_Updated = new ModelColumn<>(I_C_PromotionCode.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	ModelColumn<I_C_PromotionCode, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new ModelColumn<>(I_C_PromotionCode.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Valid to.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_C_PromotionCode, Object> COLUMN_ValidTo = new ModelColumn<>(I_C_PromotionCode.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set Search Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_C_PromotionCode, Object> COLUMN_Value = new ModelColumn<>(I_C_PromotionCode.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
