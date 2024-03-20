package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_QRCode
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_QRCode 
{

	String Table_Name = "M_HU_QRCode";

//	/** AD_Table_ID=541977 */
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
	 * Set attributes.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setattributes (String attributes);

	/**
	 * Get attributes.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getattributes();

	ModelColumn<I_M_HU_QRCode, Object> COLUMN_attributes = new ModelColumn<>(I_M_HU_QRCode.class, "attributes", null);
	String COLUMNNAME_attributes = "attributes";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_QRCode, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_QRCode.class, "Created", null);
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
	 * Set Displayable QR Code.
	 * It's the user friendly QR Code which displayed to the user. This might not be unique but it shall be unique enough for help the user distinquish between several nearby items. Also it's shorter and easy to remember on short term.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDisplayableQRCode (String DisplayableQRCode);

	/**
	 * Get Displayable QR Code.
	 * It's the user friendly QR Code which displayed to the user. This might not be unique but it shall be unique enough for help the user distinquish between several nearby items. Also it's shorter and easy to remember on short term.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getDisplayableQRCode();

	ModelColumn<I_M_HU_QRCode, Object> COLUMN_DisplayableQRCode = new ModelColumn<>(I_M_HU_QRCode.class, "DisplayableQRCode", null);
	String COLUMNNAME_DisplayableQRCode = "DisplayableQRCode";

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

	ModelColumn<I_M_HU_QRCode, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_QRCode.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set HU QR Code.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_QRCode_ID (int M_HU_QRCode_ID);

	/**
	 * Get HU QR Code.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_QRCode_ID();

	ModelColumn<I_M_HU_QRCode, Object> COLUMN_M_HU_QRCode_ID = new ModelColumn<>(I_M_HU_QRCode.class, "M_HU_QRCode_ID", null);
	String COLUMNNAME_M_HU_QRCode_ID = "M_HU_QRCode_ID";

	/**
	 * Set Rendered QR Code.
	 * It's the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRenderedQRCode (String RenderedQRCode);

	/**
	 * Get Rendered QR Code.
	 * It's the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getRenderedQRCode();

	ModelColumn<I_M_HU_QRCode, Object> COLUMN_RenderedQRCode = new ModelColumn<>(I_M_HU_QRCode.class, "RenderedQRCode", null);
	String COLUMNNAME_RenderedQRCode = "RenderedQRCode";

	/**
	 * Set Unique ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUniqueId (String UniqueId);

	/**
	 * Get Unique ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getUniqueId();

	ModelColumn<I_M_HU_QRCode, Object> COLUMN_UniqueId = new ModelColumn<>(I_M_HU_QRCode.class, "UniqueId", null);
	String COLUMNNAME_UniqueId = "UniqueId";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_QRCode, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_QRCode.class, "Updated", null);
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
