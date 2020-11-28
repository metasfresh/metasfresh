/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for A_Asset
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_A_Asset 
{

    /** TableName=A_Asset */
    public static final String Table_Name = "A_Asset";

    /** AD_Table_ID=539 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name A_Asset_CreateDate */
    public static final String COLUMNNAME_A_Asset_CreateDate = "A_Asset_CreateDate";

	/** Set A_Asset_CreateDate	  */
	public void setA_Asset_CreateDate (Timestamp A_Asset_CreateDate);

	/** Get A_Asset_CreateDate	  */
	public Timestamp getA_Asset_CreateDate();

    /** Column name A_Asset_Group_ID */
    public static final String COLUMNNAME_A_Asset_Group_ID = "A_Asset_Group_ID";

	/** Set Asset Group.
	  * Group of Assets
	  */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID);

	/** Get Asset Group.
	  * Group of Assets
	  */
	public int getA_Asset_Group_ID();

	public I_A_Asset_Group getA_Asset_Group() throws RuntimeException;

    /** Column name A_Asset_ID */
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/** Set Asset.
	  * Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID);

	/** Get Asset.
	  * Asset used internally or by customers
	  */
	public int getA_Asset_ID();

    /** Column name A_Asset_RevalDate */
    public static final String COLUMNNAME_A_Asset_RevalDate = "A_Asset_RevalDate";

	/** Set A_Asset_RevalDate	  */
	public void setA_Asset_RevalDate (Timestamp A_Asset_RevalDate);

	/** Get A_Asset_RevalDate	  */
	public Timestamp getA_Asset_RevalDate();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public I_AD_User getAD_User() throws RuntimeException;

    /** Column name A_Parent_Asset_ID */
    public static final String COLUMNNAME_A_Parent_Asset_ID = "A_Parent_Asset_ID";

	/** Set Asset ID	  */
	public void setA_Parent_Asset_ID (int A_Parent_Asset_ID);

	/** Get Asset ID	  */
	public int getA_Parent_Asset_ID();

	public I_A_Asset getA_Parent_Asset() throws RuntimeException;

    /** Column name A_QTY_Current */
    public static final String COLUMNNAME_A_QTY_Current = "A_QTY_Current";

	/** Set Quantity	  */
	public void setA_QTY_Current (BigDecimal A_QTY_Current);

	/** Get Quantity	  */
	public BigDecimal getA_QTY_Current();

    /** Column name A_QTY_Original */
    public static final String COLUMNNAME_A_QTY_Original = "A_QTY_Original";

	/** Set Original Qty	  */
	public void setA_QTY_Original (BigDecimal A_QTY_Original);

	/** Get Original Qty	  */
	public BigDecimal getA_QTY_Original();

    /** Column name AssetDepreciationDate */
    public static final String COLUMNNAME_AssetDepreciationDate = "AssetDepreciationDate";

	/** Set Asset Depreciation Date.
	  * Date of last depreciation
	  */
	public void setAssetDepreciationDate (Timestamp AssetDepreciationDate);

	/** Get Asset Depreciation Date.
	  * Date of last depreciation
	  */
	public Timestamp getAssetDepreciationDate();

    /** Column name AssetDisposalDate */
    public static final String COLUMNNAME_AssetDisposalDate = "AssetDisposalDate";

	/** Set Asset Disposal Date.
	  * Date when the asset is/was disposed
	  */
	public void setAssetDisposalDate (Timestamp AssetDisposalDate);

	/** Get Asset Disposal Date.
	  * Date when the asset is/was disposed
	  */
	public Timestamp getAssetDisposalDate();

    /** Column name AssetServiceDate */
    public static final String COLUMNNAME_AssetServiceDate = "AssetServiceDate";

	/** Set In Service Date.
	  * Date when Asset was put into service
	  */
	public void setAssetServiceDate (Timestamp AssetServiceDate);

	/** Get In Service Date.
	  * Date when Asset was put into service
	  */
	public Timestamp getAssetServiceDate();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_BPartnerSR_ID */
    public static final String COLUMNNAME_C_BPartnerSR_ID = "C_BPartnerSR_ID";

	/** Set BPartner (Agent).
	  * Business Partner (Agent or Sales Rep)
	  */
	public void setC_BPartnerSR_ID (int C_BPartnerSR_ID);

	/** Get BPartner (Agent).
	  * Business Partner (Agent or Sales Rep)
	  */
	public int getC_BPartnerSR_ID();

	public I_C_BPartner getC_BPartnerSR() throws RuntimeException;

    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/** Set Address.
	  * Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID);

	/** Get Address.
	  * Location or Address
	  */
	public int getC_Location_ID();

	public I_C_Location getC_Location() throws RuntimeException;

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public I_C_Project getC_Project() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name GuaranteeDate */
    public static final String COLUMNNAME_GuaranteeDate = "GuaranteeDate";

	/** Set Guarantee Date.
	  * Date when guarantee expires
	  */
	public void setGuaranteeDate (Timestamp GuaranteeDate);

	/** Get Guarantee Date.
	  * Date when guarantee expires
	  */
	public Timestamp getGuaranteeDate();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsDepreciated */
    public static final String COLUMNNAME_IsDepreciated = "IsDepreciated";

	/** Set Depreciate.
	  * The asset will be depreciated
	  */
	public void setIsDepreciated (boolean IsDepreciated);

	/** Get Depreciate.
	  * The asset will be depreciated
	  */
	public boolean isDepreciated();

    /** Column name IsDisposed */
    public static final String COLUMNNAME_IsDisposed = "IsDisposed";

	/** Set Disposed.
	  * The asset is disposed
	  */
	public void setIsDisposed (boolean IsDisposed);

	/** Get Disposed.
	  * The asset is disposed
	  */
	public boolean isDisposed();

    /** Column name IsFullyDepreciated */
    public static final String COLUMNNAME_IsFullyDepreciated = "IsFullyDepreciated";

	/** Set Fully depreciated.
	  * The asset is fully depreciated
	  */
	public void setIsFullyDepreciated (boolean IsFullyDepreciated);

	/** Get Fully depreciated.
	  * The asset is fully depreciated
	  */
	public boolean isFullyDepreciated();

    /** Column name IsInPosession */
    public static final String COLUMNNAME_IsInPosession = "IsInPosession";

	/** Set In Possession.
	  * The asset is in the possession of the organization
	  */
	public void setIsInPosession (boolean IsInPosession);

	/** Get In Possession.
	  * The asset is in the possession of the organization
	  */
	public boolean isInPosession();

    /** Column name IsOwned */
    public static final String COLUMNNAME_IsOwned = "IsOwned";

	/** Set Owned.
	  * The asset is owned by the organization
	  */
	public void setIsOwned (boolean IsOwned);

	/** Get Owned.
	  * The asset is owned by the organization
	  */
	public boolean isOwned();

    /** Column name LastMaintenanceDate */
    public static final String COLUMNNAME_LastMaintenanceDate = "LastMaintenanceDate";

	/** Set Last Maintenance.
	  * Last Maintenance Date
	  */
	public void setLastMaintenanceDate (Timestamp LastMaintenanceDate);

	/** Get Last Maintenance.
	  * Last Maintenance Date
	  */
	public Timestamp getLastMaintenanceDate();

    /** Column name LastMaintenanceNote */
    public static final String COLUMNNAME_LastMaintenanceNote = "LastMaintenanceNote";

	/** Set Last Note.
	  * Last Maintenance Note
	  */
	public void setLastMaintenanceNote (String LastMaintenanceNote);

	/** Get Last Note.
	  * Last Maintenance Note
	  */
	public String getLastMaintenanceNote();

    /** Column name LastMaintenanceUnit */
    public static final String COLUMNNAME_LastMaintenanceUnit = "LastMaintenanceUnit";

	/** Set Last Unit.
	  * Last Maintenance Unit
	  */
	public void setLastMaintenanceUnit (int LastMaintenanceUnit);

	/** Get Last Unit.
	  * Last Maintenance Unit
	  */
	public int getLastMaintenanceUnit();

    /** Column name Lease_BPartner_ID */
    public static final String COLUMNNAME_Lease_BPartner_ID = "Lease_BPartner_ID";

	/** Set Lessor.
	  * The Business Partner who rents or leases
	  */
	public void setLease_BPartner_ID (int Lease_BPartner_ID);

	/** Get Lessor.
	  * The Business Partner who rents or leases
	  */
	public int getLease_BPartner_ID();

	public I_C_BPartner getLease_BPartner() throws RuntimeException;

    /** Column name LeaseTerminationDate */
    public static final String COLUMNNAME_LeaseTerminationDate = "LeaseTerminationDate";

	/** Set Lease Termination.
	  * Lease Termination Date
	  */
	public void setLeaseTerminationDate (Timestamp LeaseTerminationDate);

	/** Get Lease Termination.
	  * Lease Termination Date
	  */
	public Timestamp getLeaseTerminationDate();

    /** Column name LifeUseUnits */
    public static final String COLUMNNAME_LifeUseUnits = "LifeUseUnits";

	/** Set Life use.
	  * Units of use until the asset is not usable anymore
	  */
	public void setLifeUseUnits (int LifeUseUnits);

	/** Get Life use.
	  * Units of use until the asset is not usable anymore
	  */
	public int getLifeUseUnits();

    /** Column name LocationComment */
    public static final String COLUMNNAME_LocationComment = "LocationComment";

	/** Set Location comment.
	  * Additional comments or remarks concerning the location
	  */
	public void setLocationComment (String LocationComment);

	/** Get Location comment.
	  * Additional comments or remarks concerning the location
	  */
	public String getLocationComment();

    /** Column name Lot */
    public static final String COLUMNNAME_Lot = "Lot";

	/** Set Lot No.
	  * Lot number (alphanumeric)
	  */
	public void setLot (String Lot);

	/** Get Lot No.
	  * Lot number (alphanumeric)
	  */
	public String getLot();

    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/** Set Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/** Get Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID();

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/** Set Shipment/Receipt Line.
	  * Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/** Get Shipment/Receipt Line.
	  * Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID();

	public I_M_InOutLine getM_InOutLine() throws RuntimeException;

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public I_M_Locator getM_Locator() throws RuntimeException;

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name NextMaintenenceDate */
    public static final String COLUMNNAME_NextMaintenenceDate = "NextMaintenenceDate";

	/** Set Next Maintenence.
	  * Next Maintenence Date
	  */
	public void setNextMaintenenceDate (Timestamp NextMaintenenceDate);

	/** Get Next Maintenence.
	  * Next Maintenence Date
	  */
	public Timestamp getNextMaintenenceDate();

    /** Column name NextMaintenenceUnit */
    public static final String COLUMNNAME_NextMaintenenceUnit = "NextMaintenenceUnit";

	/** Set Next Unit.
	  * Next Maintenence Unit
	  */
	public void setNextMaintenenceUnit (int NextMaintenenceUnit);

	/** Get Next Unit.
	  * Next Maintenence Unit
	  */
	public int getNextMaintenenceUnit();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name SerNo */
    public static final String COLUMNNAME_SerNo = "SerNo";

	/** Set Serial No.
	  * Product Serial Number 
	  */
	public void setSerNo (String SerNo);

	/** Get Serial No.
	  * Product Serial Number 
	  */
	public String getSerNo();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name UseLifeMonths */
    public static final String COLUMNNAME_UseLifeMonths = "UseLifeMonths";

	/** Set Usable Life - Months.
	  * Months of the usable life of the asset
	  */
	public void setUseLifeMonths (int UseLifeMonths);

	/** Get Usable Life - Months.
	  * Months of the usable life of the asset
	  */
	public int getUseLifeMonths();

    /** Column name UseLifeYears */
    public static final String COLUMNNAME_UseLifeYears = "UseLifeYears";

	/** Set Usable Life - Years.
	  * Years of the usable life of the asset
	  */
	public void setUseLifeYears (int UseLifeYears);

	/** Get Usable Life - Years.
	  * Years of the usable life of the asset
	  */
	public int getUseLifeYears();

    /** Column name UseUnits */
    public static final String COLUMNNAME_UseUnits = "UseUnits";

	/** Set Use units.
	  * Currently used units of the assets
	  */
	public void setUseUnits (int UseUnits);

	/** Get Use units.
	  * Currently used units of the assets
	  */
	public int getUseUnits();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name VersionNo */
    public static final String COLUMNNAME_VersionNo = "VersionNo";

	/** Set Version No.
	  * Version Number
	  */
	public void setVersionNo (String VersionNo);

	/** Get Version No.
	  * Version Number
	  */
	public String getVersionNo();
}
