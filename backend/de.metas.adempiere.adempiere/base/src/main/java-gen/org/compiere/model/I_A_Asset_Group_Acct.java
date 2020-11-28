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

/** Generated Interface for A_Asset_Group_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_A_Asset_Group_Acct 
{

    /** TableName=A_Asset_Group_Acct */
    public static final String Table_Name = "A_Asset_Group_Acct";

    /** AD_Table_ID=53130 */
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

    /** Column name A_Asset_Group_Acct_ID */
    public static final String COLUMNNAME_A_Asset_Group_Acct_ID = "A_Asset_Group_Acct_ID";

	/** Set A_Asset_Group_Acct_ID	  */
	public void setA_Asset_Group_Acct_ID (int A_Asset_Group_Acct_ID);

	/** Get A_Asset_Group_Acct_ID	  */
	public int getA_Asset_Group_Acct_ID();

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

    /** Column name A_Depreciation_ID */
    public static final String COLUMNNAME_A_Depreciation_ID = "A_Depreciation_ID";

	/** Set Depreciation Type	  */
	public void setA_Depreciation_ID (int A_Depreciation_ID);

	/** Get Depreciation Type	  */
	public int getA_Depreciation_ID();

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

    /** Column name A_Disposal_Gain */
    public static final String COLUMNNAME_A_Disposal_Gain = "A_Disposal_Gain";

	/** Set A_Disposal_Gain	  */
	public void setA_Disposal_Gain (int A_Disposal_Gain);

	/** Get A_Disposal_Gain	  */
	public int getA_Disposal_Gain();

	public I_C_ValidCombination getA_Disposal_G() throws RuntimeException;

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

    /** Column name A_Reval_Accumdep_Offset_Cur */
    public static final String COLUMNNAME_A_Reval_Accumdep_Offset_Cur = "A_Reval_Accumdep_Offset_Cur";

	/** Set Revaluation Accumulated Depreciation Offset for Current Year	  */
	public void setA_Reval_Accumdep_Offset_Cur (int A_Reval_Accumdep_Offset_Cur);

	/** Get Revaluation Accumulated Depreciation Offset for Current Year	  */
	public int getA_Reval_Accumdep_Offset_Cur();

	public I_C_ValidCombination getA_Reval_Accumdep_Offset_() throws RuntimeException;

    /** Column name A_Reval_Accumdep_Offset_Prior */
    public static final String COLUMNNAME_A_Reval_Accumdep_Offset_Prior = "A_Reval_Accumdep_Offset_Prior";

	/** Set Revaluation Accumulated Depreciation Offset for Prior Year	  */
	public void setA_Reval_Accumdep_Offset_Prior (int A_Reval_Accumdep_Offset_Prior);

	/** Get Revaluation Accumulated Depreciation Offset for Prior Year	  */
	public int getA_Reval_Accumdep_Offset_Prior();

	public I_C_ValidCombination getA_Reval_Accumdep_Offset_Pr() throws RuntimeException;

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

	public I_C_ValidCombination getA_Reval_Cost_Off() throws RuntimeException;

    /** Column name A_Reval_Cost_Offset_Prior */
    public static final String COLUMNNAME_A_Reval_Cost_Offset_Prior = "A_Reval_Cost_Offset_Prior";

	/** Set Revaluation Cost Offset for Prior Year	  */
	public void setA_Reval_Cost_Offset_Prior (int A_Reval_Cost_Offset_Prior);

	/** Get Revaluation Cost Offset for Prior Year	  */
	public int getA_Reval_Cost_Offset_Prior();

	public I_C_ValidCombination getA_Reval_Cost_Offset_Pr() throws RuntimeException;

    /** Column name A_Reval_Depexp_Offset */
    public static final String COLUMNNAME_A_Reval_Depexp_Offset = "A_Reval_Depexp_Offset";

	/** Set Revaluation Expense Offs	  */
	public void setA_Reval_Depexp_Offset (int A_Reval_Depexp_Offset);

	/** Get Revaluation Expense Offs	  */
	public int getA_Reval_Depexp_Offset();

	public I_C_ValidCombination getA_Reval_Depexp_Off() throws RuntimeException;

    /** Column name A_Split_Percent */
    public static final String COLUMNNAME_A_Split_Percent = "A_Split_Percent";

	/** Set Split Percentage	  */
	public void setA_Split_Percent (BigDecimal A_Split_Percent);

	/** Get Split Percentage	  */
	public BigDecimal getA_Split_Percent();

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

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
}
