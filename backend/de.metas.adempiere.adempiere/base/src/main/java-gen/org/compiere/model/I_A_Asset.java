package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for A_Asset
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_A_Asset 
{

	String Table_Name = "A_Asset";

//	/** AD_Table_ID=539 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set A_Asset_CreateDate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_CreateDate (@Nullable java.sql.Timestamp A_Asset_CreateDate);

	/**
	 * Get A_Asset_CreateDate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getA_Asset_CreateDate();

	ModelColumn<I_A_Asset, Object> COLUMN_A_Asset_CreateDate = new ModelColumn<>(I_A_Asset.class, "A_Asset_CreateDate", null);
	String COLUMNNAME_A_Asset_CreateDate = "A_Asset_CreateDate";

	/**
	 * Set Asset-Gruppe.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setA_Asset_Group_ID (int A_Asset_Group_ID);

	/**
	 * Get Asset-Gruppe.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getA_Asset_Group_ID();

	org.compiere.model.I_A_Asset_Group getA_Asset_Group();

	void setA_Asset_Group(org.compiere.model.I_A_Asset_Group A_Asset_Group);

	ModelColumn<I_A_Asset, org.compiere.model.I_A_Asset_Group> COLUMN_A_Asset_Group_ID = new ModelColumn<>(I_A_Asset.class, "A_Asset_Group_ID", org.compiere.model.I_A_Asset_Group.class);
	String COLUMNNAME_A_Asset_Group_ID = "A_Asset_Group_ID";

	/**
	 * Set Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setA_Asset_ID (int A_Asset_ID);

	/**
	 * Get Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getA_Asset_ID();

	ModelColumn<I_A_Asset, Object> COLUMN_A_Asset_ID = new ModelColumn<>(I_A_Asset.class, "A_Asset_ID", null);
	String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/**
	 * Set A_Asset_RevalDate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_RevalDate (@Nullable java.sql.Timestamp A_Asset_RevalDate);

	/**
	 * Get A_Asset_RevalDate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getA_Asset_RevalDate();

	ModelColumn<I_A_Asset, Object> COLUMN_A_Asset_RevalDate = new ModelColumn<>(I_A_Asset.class, "A_Asset_RevalDate", null);
	String COLUMNNAME_A_Asset_RevalDate = "A_Asset_RevalDate";

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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Asset ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Parent_Asset_ID (int A_Parent_Asset_ID);

	/**
	 * Get Asset ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getA_Parent_Asset_ID();

	@Nullable org.compiere.model.I_A_Asset getA_Parent_Asset();

	void setA_Parent_Asset(@Nullable org.compiere.model.I_A_Asset A_Parent_Asset);

	ModelColumn<I_A_Asset, org.compiere.model.I_A_Asset> COLUMN_A_Parent_Asset_ID = new ModelColumn<>(I_A_Asset.class, "A_Parent_Asset_ID", org.compiere.model.I_A_Asset.class);
	String COLUMNNAME_A_Parent_Asset_ID = "A_Parent_Asset_ID";

	/**
	 * Set Quantity.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_QTY_Current (@Nullable BigDecimal A_QTY_Current);

	/**
	 * Get Quantity.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getA_QTY_Current();

	ModelColumn<I_A_Asset, Object> COLUMN_A_QTY_Current = new ModelColumn<>(I_A_Asset.class, "A_QTY_Current", null);
	String COLUMNNAME_A_QTY_Current = "A_QTY_Current";

	/**
	 * Set Original Qty.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_QTY_Original (@Nullable BigDecimal A_QTY_Original);

	/**
	 * Get Original Qty.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getA_QTY_Original();

	ModelColumn<I_A_Asset, Object> COLUMN_A_QTY_Original = new ModelColumn<>(I_A_Asset.class, "A_QTY_Original", null);
	String COLUMNNAME_A_QTY_Original = "A_QTY_Original";

	/**
	 * Set Asset Depreciation Date.
	 * Date of last depreciation
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssetDepreciationDate (@Nullable java.sql.Timestamp AssetDepreciationDate);

	/**
	 * Get Asset Depreciation Date.
	 * Date of last depreciation
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getAssetDepreciationDate();

	ModelColumn<I_A_Asset, Object> COLUMN_AssetDepreciationDate = new ModelColumn<>(I_A_Asset.class, "AssetDepreciationDate", null);
	String COLUMNNAME_AssetDepreciationDate = "AssetDepreciationDate";

	/**
	 * Set Asset Disposal Date.
	 * Date when the asset is/was disposed
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssetDisposalDate (@Nullable java.sql.Timestamp AssetDisposalDate);

	/**
	 * Get Asset Disposal Date.
	 * Date when the asset is/was disposed
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getAssetDisposalDate();

	ModelColumn<I_A_Asset, Object> COLUMN_AssetDisposalDate = new ModelColumn<>(I_A_Asset.class, "AssetDisposalDate", null);
	String COLUMNNAME_AssetDisposalDate = "AssetDisposalDate";

	/**
	 * Set In Service Date.
	 * Date when Asset was put into service
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssetServiceDate (@Nullable java.sql.Timestamp AssetServiceDate);

	/**
	 * Get In Service Date.
	 * Date when Asset was put into service
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getAssetServiceDate();

	ModelColumn<I_A_Asset, Object> COLUMN_AssetServiceDate = new ModelColumn<>(I_A_Asset.class, "AssetServiceDate", null);
	String COLUMNNAME_AssetServiceDate = "AssetServiceDate";

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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set BPartner (Agent).
	 * Business Partner (Agent or Sales Rep)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartnerSR_ID (int C_BPartnerSR_ID);

	/**
	 * Get BPartner (Agent).
	 * Business Partner (Agent or Sales Rep)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartnerSR_ID();

	String COLUMNNAME_C_BPartnerSR_ID = "C_BPartnerSR_ID";

	/**
	 * Set Location.
	 * Location or Address
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Location.
	 * Location or Address
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Location_ID();

	@Nullable org.compiere.model.I_C_Location getC_Location();

	void setC_Location(@Nullable org.compiere.model.I_C_Location C_Location);

	ModelColumn<I_A_Asset, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new ModelColumn<>(I_A_Asset.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
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

	ModelColumn<I_A_Asset, Object> COLUMN_Created = new ModelColumn<>(I_A_Asset.class, "Created", null);
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

	ModelColumn<I_A_Asset, Object> COLUMN_Description = new ModelColumn<>(I_A_Asset.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_A_Asset, Object> COLUMN_Help = new ModelColumn<>(I_A_Asset.class, "Help", null);
	String COLUMNNAME_Help = "Help";

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

	ModelColumn<I_A_Asset, Object> COLUMN_IsActive = new ModelColumn<>(I_A_Asset.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Depreciate.
	 * The asset will be depreciated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDepreciated (boolean IsDepreciated);

	/**
	 * Get Depreciate.
	 * The asset will be depreciated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDepreciated();

	ModelColumn<I_A_Asset, Object> COLUMN_IsDepreciated = new ModelColumn<>(I_A_Asset.class, "IsDepreciated", null);
	String COLUMNNAME_IsDepreciated = "IsDepreciated";

	/**
	 * Set Disposed.
	 * The asset is disposed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDisposed (boolean IsDisposed);

	/**
	 * Get Disposed.
	 * The asset is disposed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDisposed();

	ModelColumn<I_A_Asset, Object> COLUMN_IsDisposed = new ModelColumn<>(I_A_Asset.class, "IsDisposed", null);
	String COLUMNNAME_IsDisposed = "IsDisposed";

	/**
	 * Set Fully depreciated.
	 * The asset is fully depreciated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFullyDepreciated (boolean IsFullyDepreciated);

	/**
	 * Get Fully depreciated.
	 * The asset is fully depreciated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFullyDepreciated();

	ModelColumn<I_A_Asset, Object> COLUMN_IsFullyDepreciated = new ModelColumn<>(I_A_Asset.class, "IsFullyDepreciated", null);
	String COLUMNNAME_IsFullyDepreciated = "IsFullyDepreciated";

	/**
	 * Set In Possession.
	 * The asset is in the possession of the organization
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInPosession (boolean IsInPosession);

	/**
	 * Get In Possession.
	 * The asset is in the possession of the organization
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInPosession();

	ModelColumn<I_A_Asset, Object> COLUMN_IsInPosession = new ModelColumn<>(I_A_Asset.class, "IsInPosession", null);
	String COLUMNNAME_IsInPosession = "IsInPosession";

	/**
	 * Set Owned.
	 * The asset is owned by the organization
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOwned (boolean IsOwned);

	/**
	 * Get Owned.
	 * The asset is owned by the organization
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOwned();

	ModelColumn<I_A_Asset, Object> COLUMN_IsOwned = new ModelColumn<>(I_A_Asset.class, "IsOwned", null);
	String COLUMNNAME_IsOwned = "IsOwned";

	/**
	 * Set Last Maintenance.
	 * Last Maintenance Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastMaintenanceDate (@Nullable java.sql.Timestamp LastMaintenanceDate);

	/**
	 * Get Last Maintenance.
	 * Last Maintenance Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLastMaintenanceDate();

	ModelColumn<I_A_Asset, Object> COLUMN_LastMaintenanceDate = new ModelColumn<>(I_A_Asset.class, "LastMaintenanceDate", null);
	String COLUMNNAME_LastMaintenanceDate = "LastMaintenanceDate";

	/**
	 * Set Last Note.
	 * Last Maintenance Note
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastMaintenanceNote (@Nullable java.lang.String LastMaintenanceNote);

	/**
	 * Get Last Note.
	 * Last Maintenance Note
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLastMaintenanceNote();

	ModelColumn<I_A_Asset, Object> COLUMN_LastMaintenanceNote = new ModelColumn<>(I_A_Asset.class, "LastMaintenanceNote", null);
	String COLUMNNAME_LastMaintenanceNote = "LastMaintenanceNote";

	/**
	 * Set Last Unit.
	 * Last Maintenance Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastMaintenanceUnit (int LastMaintenanceUnit);

	/**
	 * Get Last Unit.
	 * Last Maintenance Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLastMaintenanceUnit();

	ModelColumn<I_A_Asset, Object> COLUMN_LastMaintenanceUnit = new ModelColumn<>(I_A_Asset.class, "LastMaintenanceUnit", null);
	String COLUMNNAME_LastMaintenanceUnit = "LastMaintenanceUnit";

	/**
	 * Set Lessor.
	 * The Business Partner who rents or leases
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLease_BPartner_ID (int Lease_BPartner_ID);

	/**
	 * Get Lessor.
	 * The Business Partner who rents or leases
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLease_BPartner_ID();

	String COLUMNNAME_Lease_BPartner_ID = "Lease_BPartner_ID";

	/**
	 * Set Lease Termination.
	 * Lease Termination Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLeaseTerminationDate (@Nullable java.sql.Timestamp LeaseTerminationDate);

	/**
	 * Get Lease Termination.
	 * Lease Termination Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLeaseTerminationDate();

	ModelColumn<I_A_Asset, Object> COLUMN_LeaseTerminationDate = new ModelColumn<>(I_A_Asset.class, "LeaseTerminationDate", null);
	String COLUMNNAME_LeaseTerminationDate = "LeaseTerminationDate";

	/**
	 * Set Life use.
	 * Units of use until the asset is not usable anymore
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLifeUseUnits (int LifeUseUnits);

	/**
	 * Get Life use.
	 * Units of use until the asset is not usable anymore
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLifeUseUnits();

	ModelColumn<I_A_Asset, Object> COLUMN_LifeUseUnits = new ModelColumn<>(I_A_Asset.class, "LifeUseUnits", null);
	String COLUMNNAME_LifeUseUnits = "LifeUseUnits";

	/**
	 * Set Location comment.
	 * Additional comments or remarks concerning the location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocationComment (@Nullable java.lang.String LocationComment);

	/**
	 * Get Location comment.
	 * Additional comments or remarks concerning the location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocationComment();

	ModelColumn<I_A_Asset, Object> COLUMN_LocationComment = new ModelColumn<>(I_A_Asset.class, "LocationComment", null);
	String COLUMNNAME_LocationComment = "LocationComment";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_A_Asset, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_A_Asset.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	@Nullable org.compiere.model.I_M_InOutLine getM_InOutLine();

	void setM_InOutLine(@Nullable org.compiere.model.I_M_InOutLine M_InOutLine);

	ModelColumn<I_A_Asset, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_A_Asset.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

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

	ModelColumn<I_A_Asset, Object> COLUMN_Name = new ModelColumn<>(I_A_Asset.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Next Maintenence.
	 * Next Maintenence Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNextMaintenenceDate (@Nullable java.sql.Timestamp NextMaintenenceDate);

	/**
	 * Get Next Maintenence.
	 * Next Maintenence Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getNextMaintenenceDate();

	ModelColumn<I_A_Asset, Object> COLUMN_NextMaintenenceDate = new ModelColumn<>(I_A_Asset.class, "NextMaintenenceDate", null);
	String COLUMNNAME_NextMaintenenceDate = "NextMaintenenceDate";

	/**
	 * Set Next Unit.
	 * Next Maintenence Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNextMaintenenceUnit (int NextMaintenenceUnit);

	/**
	 * Get Next Unit.
	 * Next Maintenence Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getNextMaintenenceUnit();

	ModelColumn<I_A_Asset, Object> COLUMN_NextMaintenenceUnit = new ModelColumn<>(I_A_Asset.class, "NextMaintenenceUnit", null);
	String COLUMNNAME_NextMaintenenceUnit = "NextMaintenenceUnit";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_A_Asset, Object> COLUMN_Processing = new ModelColumn<>(I_A_Asset.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_A_Asset, Object> COLUMN_Qty = new ModelColumn<>(I_A_Asset.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_A_Asset, Object> COLUMN_Updated = new ModelColumn<>(I_A_Asset.class, "Updated", null);
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
	 * Set Usable Life - Months.
	 * Months of the usable life of the asset
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUseLifeMonths (int UseLifeMonths);

	/**
	 * Get Usable Life - Months.
	 * Months of the usable life of the asset
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUseLifeMonths();

	ModelColumn<I_A_Asset, Object> COLUMN_UseLifeMonths = new ModelColumn<>(I_A_Asset.class, "UseLifeMonths", null);
	String COLUMNNAME_UseLifeMonths = "UseLifeMonths";

	/**
	 * Set Usable Life - Years.
	 * Years of the usable life of the asset
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUseLifeYears (int UseLifeYears);

	/**
	 * Get Usable Life - Years.
	 * Years of the usable life of the asset
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUseLifeYears();

	ModelColumn<I_A_Asset, Object> COLUMN_UseLifeYears = new ModelColumn<>(I_A_Asset.class, "UseLifeYears", null);
	String COLUMNNAME_UseLifeYears = "UseLifeYears";

	/**
	 * Set Use units.
	 * Currently used units of the assets
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUseUnits (int UseUnits);

	/**
	 * Get Use units.
	 * Currently used units of the assets
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUseUnits();

	ModelColumn<I_A_Asset, Object> COLUMN_UseUnits = new ModelColumn<>(I_A_Asset.class, "UseUnits", null);
	String COLUMNNAME_UseUnits = "UseUnits";

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

	ModelColumn<I_A_Asset, Object> COLUMN_Value = new ModelColumn<>(I_A_Asset.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Versions-Nr..
	 * Version Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVersionNo (@Nullable java.lang.String VersionNo);

	/**
	 * Get Versions-Nr..
	 * Version Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVersionNo();

	ModelColumn<I_A_Asset, Object> COLUMN_VersionNo = new ModelColumn<>(I_A_Asset.class, "VersionNo", null);
	String COLUMNNAME_VersionNo = "VersionNo";
}
