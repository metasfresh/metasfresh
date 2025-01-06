package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU 
{

	String Table_Name = "M_HU";

//	/** AD_Table_ID=540516 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Clearance Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClearanceDate (@Nullable java.sql.Timestamp ClearanceDate);

	/**
	 * Get Clearance Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getClearanceDate();

	ModelColumn<I_M_HU, Object> COLUMN_ClearanceDate = new ModelColumn<>(I_M_HU.class, "ClearanceDate", null);
	String COLUMNNAME_ClearanceDate = "ClearanceDate";

	/**
	 * Set Clearance Note.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClearanceNote (@Nullable java.lang.String ClearanceNote);

	/**
	 * Get Clearance Note.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClearanceNote();

	ModelColumn<I_M_HU, Object> COLUMN_ClearanceNote = new ModelColumn<>(I_M_HU.class, "ClearanceNote", null);
	String COLUMNNAME_ClearanceNote = "ClearanceNote";

	/**
	 * Set Clearance.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClearanceStatus (@Nullable java.lang.String ClearanceStatus);

	/**
	 * Get Clearance.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClearanceStatus();

	ModelColumn<I_M_HU, Object> COLUMN_ClearanceStatus = new ModelColumn<>(I_M_HU.class, "ClearanceStatus", null);
	String COLUMNNAME_ClearanceStatus = "ClearanceStatus";

	/**
	 * Set Cloned From.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClonedFrom_HU_ID (int ClonedFrom_HU_ID);

	/**
	 * Get Cloned From.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getClonedFrom_HU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getClonedFrom_HU();

	void setClonedFrom_HU(@Nullable de.metas.handlingunits.model.I_M_HU ClonedFrom_HU);

	ModelColumn<I_M_HU, de.metas.handlingunits.model.I_M_HU> COLUMN_ClonedFrom_HU_ID = new ModelColumn<>(I_M_HU.class, "ClonedFrom_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_ClonedFrom_HU_ID = "ClonedFrom_HU_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU, Object> COLUMN_Created = new ModelColumn<>(I_M_HU.class, "Created", null);
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
	 * Set Own Palette.
	 * If true, then the packing material's owner is "us" (the guys who ordered it). If false, then the packing material's owner is the PO's partner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHUPlanningReceiptOwnerPM (boolean HUPlanningReceiptOwnerPM);

	/**
	 * Get Own Palette.
	 * If true, then the packing material's owner is "us" (the guys who ordered it). If false, then the packing material's owner is the PO's partner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isHUPlanningReceiptOwnerPM();

	ModelColumn<I_M_HU, Object> COLUMN_HUPlanningReceiptOwnerPM = new ModelColumn<>(I_M_HU.class, "HUPlanningReceiptOwnerPM", null);
	String COLUMNNAME_HUPlanningReceiptOwnerPM = "HUPlanningReceiptOwnerPM";

	/**
	 * Set Packing Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHUStatus (java.lang.String HUStatus);

	/**
	 * Get Packing Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getHUStatus();

	ModelColumn<I_M_HU, Object> COLUMN_HUStatus = new ModelColumn<>(I_M_HU.class, "HUStatus", null);
	String COLUMNNAME_HUStatus = "HUStatus";

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

	ModelColumn<I_M_HU, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Included in other HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsChildHU (boolean IsChildHU);

	/**
	 * Get Included in other HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isChildHU();

	ModelColumn<I_M_HU, Object> COLUMN_IsChildHU = new ModelColumn<>(I_M_HU.class, "IsChildHU", null);
	String COLUMNNAME_IsChildHU = "IsChildHU";

	/**
	 * Set External property.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExternalProperty (boolean IsExternalProperty);

	/**
	 * Get External property.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExternalProperty();

	ModelColumn<I_M_HU, Object> COLUMN_IsExternalProperty = new ModelColumn<>(I_M_HU.class, "IsExternalProperty", null);
	String COLUMNNAME_IsExternalProperty = "IsExternalProperty";

	/**
	 * Set Reserviert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReserved (boolean IsReserved);

	/**
	 * Get Reserviert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReserved();

	ModelColumn<I_M_HU, Object> COLUMN_IsReserved = new ModelColumn<>(I_M_HU.class, "IsReserved", null);
	String COLUMNNAME_IsReserved = "IsReserved";

	/**
	 * Set Locked.
	 * Whether the terminal is locked
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setLocked (boolean Locked);

	/**
	 * Get Locked.
	 * Whether the terminal is locked
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isLocked();

	ModelColumn<I_M_HU, Object> COLUMN_Locked = new ModelColumn<>(I_M_HU.class, "Locked", null);
	String COLUMNNAME_Locked = "Locked";

	/**
	 * Set Lot No..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setLot (@Nullable java.lang.String Lot);

	/**
	 * Get Lot No..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getLot();

	ModelColumn<I_M_HU, Object> COLUMN_Lot = new ModelColumn<>(I_M_HU.class, "Lot", null);
	String COLUMNNAME_Lot = "Lot";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	ModelColumn<I_M_HU, Object> COLUMN_M_HU_ID = new ModelColumn<>(I_M_HU.class, "M_HU_ID", null);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Handling Unit Parent.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_Item_Parent_ID (int M_HU_Item_Parent_ID);

	/**
	 * Get Handling Unit Parent.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_Item_Parent_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item_Parent();

	void setM_HU_Item_Parent(@Nullable de.metas.handlingunits.model.I_M_HU_Item M_HU_Item_Parent);

	ModelColumn<I_M_HU, de.metas.handlingunits.model.I_M_HU_Item> COLUMN_M_HU_Item_Parent_ID = new ModelColumn<>(I_M_HU.class, "M_HU_Item_Parent_ID", de.metas.handlingunits.model.I_M_HU_Item.class);
	String COLUMNNAME_M_HU_Item_Parent_ID = "M_HU_Item_Parent_ID";

	/**
	 * Set Packing Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_LUTU_Configuration_ID (int M_HU_LUTU_Configuration_ID);

	/**
	 * Get Packing Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_LUTU_Configuration_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration();

	void setM_HU_LUTU_Configuration(@Nullable de.metas.handlingunits.model.I_M_HU_LUTU_Configuration M_HU_LUTU_Configuration);

	ModelColumn<I_M_HU, de.metas.handlingunits.model.I_M_HU_LUTU_Configuration> COLUMN_M_HU_LUTU_Configuration_ID = new ModelColumn<>(I_M_HU.class, "M_HU_LUTU_Configuration_ID", de.metas.handlingunits.model.I_M_HU_LUTU_Configuration.class);
	String COLUMNNAME_M_HU_LUTU_Configuration_ID = "M_HU_LUTU_Configuration_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Version_ID();

	de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version();

	void setM_HU_PI_Version(de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version);

	ModelColumn<I_M_HU, de.metas.handlingunits.model.I_M_HU_PI_Version> COLUMN_M_HU_PI_Version_ID = new ModelColumn<>(I_M_HU.class, "M_HU_PI_Version_ID", de.metas.handlingunits.model.I_M_HU_PI_Version.class);
	String COLUMNNAME_M_HU_PI_Version_ID = "M_HU_PI_Version_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set SerialNo.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setSerialNo (@Nullable java.lang.String SerialNo);

	/**
	 * Get SerialNo.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getSerialNo();

	ModelColumn<I_M_HU, Object> COLUMN_SerialNo = new ModelColumn<>(I_M_HU.class, "SerialNo", null);
	String COLUMNNAME_SerialNo = "SerialNo";

	/**
	 * Set Service Contract.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setServiceContract (@Nullable java.lang.String ServiceContract);

	/**
	 * Get Service Contract.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getServiceContract();

	ModelColumn<I_M_HU, Object> COLUMN_ServiceContract = new ModelColumn<>(I_M_HU.class, "ServiceContract", null);
	String COLUMNNAME_ServiceContract = "ServiceContract";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_M_HU, Object> COLUMN_Value = new ModelColumn<>(I_M_HU.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
