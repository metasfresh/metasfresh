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

/** Generated Interface for A_Asset_Change
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_A_Asset_Change 
{

    /** TableName=A_Asset_Change */
    public static final String Table_Name = "A_Asset_Change";

    /** AD_Table_ID=53133 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name A_Accumdepreciation_Acct */
    public static final String COLUMNNAME_A_Accumdepreciation_Acct = "A_Accumdepreciation_Acct";

	/** Set Accumulated Depreciation	  */
	public void setA_Accumdepreciation_Acct (int A_Accumdepreciation_Acct);

	/** Get Accumulated Depreciation	  */
	public int getA_Accumdepreciation_Acct();

	public I_C_ValidCombination getA_Accumdepreciation_A() throws RuntimeException;

    /** Column name A_Asset_Acct */
    public static final String COLUMNNAME_A_Asset_Acct = "A_Asset_Acct";

	/** Set Asset Cost Account	  */
	public void setA_Asset_Acct (int A_Asset_Acct);

	/** Get Asset Cost Account	  */
	public int getA_Asset_Acct();

	public I_C_ValidCombination getA_Asset_A() throws RuntimeException;

    /** Column name A_Asset_Acct_ID */
    public static final String COLUMNNAME_A_Asset_Acct_ID = "A_Asset_Acct_ID";

	/** Set A_Asset_Acct_ID	  */
	public void setA_Asset_Acct_ID (int A_Asset_Acct_ID);

	/** Get A_Asset_Acct_ID	  */
	public int getA_Asset_Acct_ID();

    /** Column name A_Asset_Addition_ID */
    public static final String COLUMNNAME_A_Asset_Addition_ID = "A_Asset_Addition_ID";

	/** Set A_Asset_Addition_ID	  */
	public void setA_Asset_Addition_ID (int A_Asset_Addition_ID);

	/** Get A_Asset_Addition_ID	  */
	public int getA_Asset_Addition_ID();

    /** Column name A_Asset_Change_ID */
    public static final String COLUMNNAME_A_Asset_Change_ID = "A_Asset_Change_ID";

	/** Set A_Asset_Change_ID	  */
	public void setA_Asset_Change_ID (int A_Asset_Change_ID);

	/** Get A_Asset_Change_ID	  */
	public int getA_Asset_Change_ID();

    /** Column name A_Asset_CreateDate */
    public static final String COLUMNNAME_A_Asset_CreateDate = "A_Asset_CreateDate";

	/** Set A_Asset_CreateDate	  */
	public void setA_Asset_CreateDate (Timestamp A_Asset_CreateDate);

	/** Get A_Asset_CreateDate	  */
	public Timestamp getA_Asset_CreateDate();

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

	public I_A_Asset getA_Asset() throws RuntimeException;

    /** Column name A_Asset_Retirement_ID */
    public static final String COLUMNNAME_A_Asset_Retirement_ID = "A_Asset_Retirement_ID";

	/** Set Asset Retirement.
	  * Internally used asset is not longer used.
	  */
	public void setA_Asset_Retirement_ID (int A_Asset_Retirement_ID);

	/** Get Asset Retirement.
	  * Internally used asset is not longer used.
	  */
	public int getA_Asset_Retirement_ID();

    /** Column name A_Asset_RevalDate */
    public static final String COLUMNNAME_A_Asset_RevalDate = "A_Asset_RevalDate";

	/** Set A_Asset_RevalDate	  */
	public void setA_Asset_RevalDate (Timestamp A_Asset_RevalDate);

	/** Get A_Asset_RevalDate	  */
	public Timestamp getA_Asset_RevalDate();

    /** Column name A_Asset_Spread_Type */
    public static final String COLUMNNAME_A_Asset_Spread_Type = "A_Asset_Spread_Type";

	/** Set A_Asset_Spread_Type	  */
	public void setA_Asset_Spread_Type (int A_Asset_Spread_Type);

	/** Get A_Asset_Spread_Type	  */
	public int getA_Asset_Spread_Type();

	public I_A_Asset_Spread getA_Asset_Spread_T() throws RuntimeException;

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name A_Depreciation_Acct */
    public static final String COLUMNNAME_A_Depreciation_Acct = "A_Depreciation_Acct";

	/** Set Depreciation Expense Account	  */
	public void setA_Depreciation_Acct (int A_Depreciation_Acct);

	/** Get Depreciation Expense Account	  */
	public int getA_Depreciation_Acct();

	public I_C_ValidCombination getA_Depreciation_A() throws RuntimeException;

    /** Column name A_Depreciation_Calc_Type */
    public static final String COLUMNNAME_A_Depreciation_Calc_Type = "A_Depreciation_Calc_Type";

	/** Set Depreciation Calculation Type	  */
	public void setA_Depreciation_Calc_Type (int A_Depreciation_Calc_Type);

	/** Get Depreciation Calculation Type	  */
	public int getA_Depreciation_Calc_Type();

	public I_A_Depreciation_Method getA_Depreciation_Calc_T() throws RuntimeException;

    /** Column name A_Depreciation_Manual_Amount */
    public static final String COLUMNNAME_A_Depreciation_Manual_Amount = "A_Depreciation_Manual_Amount";

	/** Set A_Depreciation_Manual_Amount	  */
	public void setA_Depreciation_Manual_Amount (BigDecimal A_Depreciation_Manual_Amount);

	/** Get A_Depreciation_Manual_Amount	  */
	public BigDecimal getA_Depreciation_Manual_Amount();

    /** Column name A_Depreciation_Manual_Period */
    public static final String COLUMNNAME_A_Depreciation_Manual_Period = "A_Depreciation_Manual_Period";

	/** Set A_Depreciation_Manual_Period	  */
	public void setA_Depreciation_Manual_Period (String A_Depreciation_Manual_Period);

	/** Get A_Depreciation_Manual_Period	  */
	public String getA_Depreciation_Manual_Period();

    /** Column name A_Depreciation_Table_Header_ID */
    public static final String COLUMNNAME_A_Depreciation_Table_Header_ID = "A_Depreciation_Table_Header_ID";

	/** Set A_Depreciation_Table_Header_ID	  */
	public void setA_Depreciation_Table_Header_ID (int A_Depreciation_Table_Header_ID);

	/** Get A_Depreciation_Table_Header_ID	  */
	public int getA_Depreciation_Table_Header_ID();

	public I_A_Depreciation_Table_Header getA_Depreciation_Table_Header() throws RuntimeException;

    /** Column name A_Depreciation_Variable_Perc */
    public static final String COLUMNNAME_A_Depreciation_Variable_Perc = "A_Depreciation_Variable_Perc";

	/** Set A_Depreciation_Variable_Perc	  */
	public void setA_Depreciation_Variable_Perc (BigDecimal A_Depreciation_Variable_Perc);

	/** Get A_Depreciation_Variable_Perc	  */
	public BigDecimal getA_Depreciation_Variable_Perc();

    /** Column name A_Disposal_Loss */
    public static final String COLUMNNAME_A_Disposal_Loss = "A_Disposal_Loss";

	/** Set Loss on Disposal	  */
	public void setA_Disposal_Loss (int A_Disposal_Loss);

	/** Get Loss on Disposal	  */
	public int getA_Disposal_Loss();

	public I_C_ValidCombination getA_Disposal_L() throws RuntimeException;

    /** Column name A_Disposal_Revenue */
    public static final String COLUMNNAME_A_Disposal_Revenue = "A_Disposal_Revenue";

	/** Set Disposal Revenue	  */
	public void setA_Disposal_Revenue (int A_Disposal_Revenue);

	/** Get Disposal Revenue	  */
	public int getA_Disposal_Revenue();

	public I_C_ValidCombination getA_Disposal_Reve() throws RuntimeException;

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

    /** Column name A_Period_End */
    public static final String COLUMNNAME_A_Period_End = "A_Period_End";

	/** Set Period End	  */
	public void setA_Period_End (int A_Period_End);

	/** Get Period End	  */
	public int getA_Period_End();

    /** Column name A_Period_Start */
    public static final String COLUMNNAME_A_Period_Start = "A_Period_Start";

	/** Set Period Start	  */
	public void setA_Period_Start (int A_Period_Start);

	/** Get Period Start	  */
	public int getA_Period_Start();

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

    /** Column name A_Reval_Accumdep_Offset_Cur */
    public static final String COLUMNNAME_A_Reval_Accumdep_Offset_Cur = "A_Reval_Accumdep_Offset_Cur";

	/** Set Revaluation Accumulated Depreciation Offset for Current Year	  */
	public void setA_Reval_Accumdep_Offset_Cur (int A_Reval_Accumdep_Offset_Cur);

	/** Get Revaluation Accumulated Depreciation Offset for Current Year	  */
	public int getA_Reval_Accumdep_Offset_Cur();

    /** Column name A_Reval_Accumdep_Offset_Prior */
    public static final String COLUMNNAME_A_Reval_Accumdep_Offset_Prior = "A_Reval_Accumdep_Offset_Prior";

	/** Set Revaluation Accumulated Depreciation Offset for Prior Year	  */
	public void setA_Reval_Accumdep_Offset_Prior (int A_Reval_Accumdep_Offset_Prior);

	/** Get Revaluation Accumulated Depreciation Offset for Prior Year	  */
	public int getA_Reval_Accumdep_Offset_Prior();

    /** Column name A_Reval_Cal_Method */
    public static final String COLUMNNAME_A_Reval_Cal_Method = "A_Reval_Cal_Method";

	/** Set Revaluation Calculation Method	  */
	public void setA_Reval_Cal_Method (String A_Reval_Cal_Method);

	/** Get Revaluation Calculation Method	  */
	public String getA_Reval_Cal_Method();

    /** Column name A_Reval_Cost_Offset */
    public static final String COLUMNNAME_A_Reval_Cost_Offset = "A_Reval_Cost_Offset";

	/** Set Revaluation Cost Offset for Current Year	  */
	public void setA_Reval_Cost_Offset (int A_Reval_Cost_Offset);

	/** Get Revaluation Cost Offset for Current Year	  */
	public int getA_Reval_Cost_Offset();

    /** Column name A_Reval_Cost_Offset_Prior */
    public static final String COLUMNNAME_A_Reval_Cost_Offset_Prior = "A_Reval_Cost_Offset_Prior";

	/** Set Revaluation Cost Offset for Prior Year	  */
	public void setA_Reval_Cost_Offset_Prior (int A_Reval_Cost_Offset_Prior);

	/** Get Revaluation Cost Offset for Prior Year	  */
	public int getA_Reval_Cost_Offset_Prior();

    /** Column name A_Reval_Depexp_Offset */
    public static final String COLUMNNAME_A_Reval_Depexp_Offset = "A_Reval_Depexp_Offset";

	/** Set Revaluation Expense Offs	  */
	public void setA_Reval_Depexp_Offset (int A_Reval_Depexp_Offset);

	/** Get Revaluation Expense Offs	  */
	public int getA_Reval_Depexp_Offset();

    /** Column name A_Salvage_Value */
    public static final String COLUMNNAME_A_Salvage_Value = "A_Salvage_Value";

	/** Set Salvage Value	  */
	public void setA_Salvage_Value (BigDecimal A_Salvage_Value);

	/** Get Salvage Value	  */
	public BigDecimal getA_Salvage_Value();

    /** Column name A_Split_Percent */
    public static final String COLUMNNAME_A_Split_Percent = "A_Split_Percent";

	/** Set Split Percentage	  */
	public void setA_Split_Percent (BigDecimal A_Split_Percent);

	/** Get Split Percentage	  */
	public BigDecimal getA_Split_Percent();

    /** Column name AssetAccumDepreciationAmt */
    public static final String COLUMNNAME_AssetAccumDepreciationAmt = "AssetAccumDepreciationAmt";

	/** Set AssetAccumDepreciationAmt	  */
	public void setAssetAccumDepreciationAmt (BigDecimal AssetAccumDepreciationAmt);

	/** Get AssetAccumDepreciationAmt	  */
	public BigDecimal getAssetAccumDepreciationAmt();

    /** Column name AssetBookValueAmt */
    public static final String COLUMNNAME_AssetBookValueAmt = "AssetBookValueAmt";

	/** Set AssetBookValueAmt	  */
	public void setAssetBookValueAmt (BigDecimal AssetBookValueAmt);

	/** Get AssetBookValueAmt	  */
	public BigDecimal getAssetBookValueAmt();

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

    /** Column name AssetMarketValueAmt */
    public static final String COLUMNNAME_AssetMarketValueAmt = "AssetMarketValueAmt";

	/** Set Market value Amount.
	  * Market value of the asset
	  */
	public void setAssetMarketValueAmt (BigDecimal AssetMarketValueAmt);

	/** Get Market value Amount.
	  * Market value of the asset
	  */
	public BigDecimal getAssetMarketValueAmt();

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

    /** Column name AssetValueAmt */
    public static final String COLUMNNAME_AssetValueAmt = "AssetValueAmt";

	/** Set Asset value.
	  * Book Value of the asset
	  */
	public void setAssetValueAmt (BigDecimal AssetValueAmt);

	/** Get Asset value.
	  * Book Value of the asset
	  */
	public BigDecimal getAssetValueAmt();

    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/** Set Accounting Schema.
	  * Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/** Get Accounting Schema.
	  * Rules for accounting
	  */
	public int getC_AcctSchema_ID();

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException;

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

    /** Column name ChangeAmt */
    public static final String COLUMNNAME_ChangeAmt = "ChangeAmt";

	/** Set ChangeAmt	  */
	public void setChangeAmt (BigDecimal ChangeAmt);

	/** Get ChangeAmt	  */
	public BigDecimal getChangeAmt();

    /** Column name ChangeDate */
    public static final String COLUMNNAME_ChangeDate = "ChangeDate";

	/** Set ChangeDate	  */
	public void setChangeDate (Timestamp ChangeDate);

	/** Get ChangeDate	  */
	public Timestamp getChangeDate();

    /** Column name ChangeType */
    public static final String COLUMNNAME_ChangeType = "ChangeType";

	/** Set ChangeType	  */
	public void setChangeType (String ChangeType);

	/** Get ChangeType	  */
	public String getChangeType();

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

    /** Column name ConventionType */
    public static final String COLUMNNAME_ConventionType = "ConventionType";

	/** Set ConventionType	  */
	public void setConventionType (int ConventionType);

	/** Get ConventionType	  */
	public int getConventionType();

	public I_A_Depreciation_Convention getConventionT() throws RuntimeException;

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

    /** Column name C_ValidCombination_ID */
    public static final String COLUMNNAME_C_ValidCombination_ID = "C_ValidCombination_ID";

	/** Set Combination.
	  * Valid Account Combination
	  */
	public void setC_ValidCombination_ID (int C_ValidCombination_ID);

	/** Get Combination.
	  * Valid Account Combination
	  */
	public int getC_ValidCombination_ID();

	public I_C_ValidCombination getC_ValidCombination() throws RuntimeException;

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

    /** Column name DepreciationType */
    public static final String COLUMNNAME_DepreciationType = "DepreciationType";

	/** Set DepreciationType	  */
	public void setDepreciationType (int DepreciationType);

	/** Get DepreciationType	  */
	public int getDepreciationType();

	public I_A_Depreciation getDepreciationT() throws RuntimeException;

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

    /** Column name PostingType */
    public static final String COLUMNNAME_PostingType = "PostingType";

	/** Set PostingType.
	  * The type of posted amount for the transaction
	  */
	public void setPostingType (String PostingType);

	/** Get PostingType.
	  * The type of posted amount for the transaction
	  */
	public String getPostingType();

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

    /** Column name TextDetails */
    public static final String COLUMNNAME_TextDetails = "TextDetails";

	/** Set Details	  */
	public void setTextDetails (String TextDetails);

	/** Get Details	  */
	public String getTextDetails();

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
