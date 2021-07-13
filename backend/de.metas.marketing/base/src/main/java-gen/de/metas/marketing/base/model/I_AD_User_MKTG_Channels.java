package de.metas.marketing.base.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_User_MKTG_Channels
 *  @author metasfresh (generated) 
 */
public interface I_AD_User_MKTG_Channels 
{

	String Table_Name = "AD_User_MKTG_Channels";

//	/** AD_Table_ID=541553 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set AD_User_MKTG_Channels.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_MKTG_Channels_ID (int AD_User_MKTG_Channels_ID);

	/**
	 * Get AD_User_MKTG_Channels.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_MKTG_Channels_ID();

	ModelColumn<I_AD_User_MKTG_Channels, Object> COLUMN_AD_User_MKTG_Channels_ID = new ModelColumn<>(I_AD_User_MKTG_Channels.class, "AD_User_MKTG_Channels_ID", null);
	String COLUMNNAME_AD_User_MKTG_Channels_ID = "AD_User_MKTG_Channels_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_AD_User_MKTG_Channels, Object> COLUMN_Created = new ModelColumn<>(I_AD_User_MKTG_Channels.class, "Created", null);
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

	ModelColumn<I_AD_User_MKTG_Channels, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_User_MKTG_Channels.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MKTG_Channel.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMKTG_Channel_ID (int MKTG_Channel_ID);

	/**
	 * Get MKTG_Channel.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMKTG_Channel_ID();

	@Nullable de.metas.marketing.base.model.I_MKTG_Channel getMKTG_Channel();

	void setMKTG_Channel(@Nullable de.metas.marketing.base.model.I_MKTG_Channel MKTG_Channel);

	ModelColumn<I_AD_User_MKTG_Channels, de.metas.marketing.base.model.I_MKTG_Channel> COLUMN_MKTG_Channel_ID = new ModelColumn<>(I_AD_User_MKTG_Channels.class, "MKTG_Channel_ID", de.metas.marketing.base.model.I_MKTG_Channel.class);
	String COLUMNNAME_MKTG_Channel_ID = "MKTG_Channel_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_User_MKTG_Channels, Object> COLUMN_Updated = new ModelColumn<>(I_AD_User_MKTG_Channels.class, "Updated", null);
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
