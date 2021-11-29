package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_MembershipMonth
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_MembershipMonth 
{

	String Table_Name = "C_MembershipMonth";

//	/** AD_Table_ID=541763 */
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
	 * Set Membership Month.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_MembershipMonth_ID (int C_MembershipMonth_ID);

	/**
	 * Get Membership Month.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_MembershipMonth_ID();

	ModelColumn<I_C_MembershipMonth, Object> COLUMN_C_MembershipMonth_ID = new ModelColumn<>(I_C_MembershipMonth.class, "C_MembershipMonth_ID", null);
	String COLUMNNAME_C_MembershipMonth_ID = "C_MembershipMonth_ID";

	/**
	 * Set Year.
	 * Calendar Year
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Year_ID (int C_Year_ID);

	/**
	 * Get Year.
	 * Calendar Year
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Year_ID();

	org.compiere.model.I_C_Year getC_Year();

	void setC_Year(org.compiere.model.I_C_Year C_Year);

	ModelColumn<I_C_MembershipMonth, org.compiere.model.I_C_Year> COLUMN_C_Year_ID = new ModelColumn<>(I_C_MembershipMonth.class, "C_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_MembershipMonth, Object> COLUMN_Created = new ModelColumn<>(I_C_MembershipMonth.class, "Created", null);
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

	ModelColumn<I_C_MembershipMonth, Object> COLUMN_IsActive = new ModelColumn<>(I_C_MembershipMonth.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_MembershipMonth, Object> COLUMN_Updated = new ModelColumn<>(I_C_MembershipMonth.class, "Updated", null);
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

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_C_MembershipMonth, Object> COLUMN_ValidTo = new ModelColumn<>(I_C_MembershipMonth.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";
}
