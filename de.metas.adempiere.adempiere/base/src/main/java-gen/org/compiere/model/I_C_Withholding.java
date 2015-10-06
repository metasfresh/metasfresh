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

/** Generated Interface for C_Withholding
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_Withholding 
{

    /** TableName=C_Withholding */
    public static final String Table_Name = "C_Withholding";

    /** AD_Table_ID=304 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

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

    /** Column name Beneficiary */
    public static final String COLUMNNAME_Beneficiary = "Beneficiary";

	/** Set Beneficiary.
	  * Business Partner to whom payment is made
	  */
	public void setBeneficiary (int Beneficiary);

	/** Get Beneficiary.
	  * Business Partner to whom payment is made
	  */
	public int getBeneficiary();

	public I_C_BPartner getBenefici() throws RuntimeException;

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

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

    /** Column name C_Withholding_ID */
    public static final String COLUMNNAME_C_Withholding_ID = "C_Withholding_ID";

	/** Set Withholding.
	  * Withholding type defined
	  */
	public void setC_Withholding_ID (int C_Withholding_ID);

	/** Get Withholding.
	  * Withholding type defined
	  */
	public int getC_Withholding_ID();

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

    /** Column name FixAmt */
    public static final String COLUMNNAME_FixAmt = "FixAmt";

	/** Set Fix amount.
	  * Fix amounted amount to be levied or paid
	  */
	public void setFixAmt (BigDecimal FixAmt);

	/** Get Fix amount.
	  * Fix amounted amount to be levied or paid
	  */
	public BigDecimal getFixAmt();

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

    /** Column name IsPaidTo3Party */
    public static final String COLUMNNAME_IsPaidTo3Party = "IsPaidTo3Party";

	/** Set Paid to third party.
	  * Amount paid to someone other than the Business Partner
	  */
	public void setIsPaidTo3Party (boolean IsPaidTo3Party);

	/** Get Paid to third party.
	  * Amount paid to someone other than the Business Partner
	  */
	public boolean isPaidTo3Party();

    /** Column name IsPercentWithholding */
    public static final String COLUMNNAME_IsPercentWithholding = "IsPercentWithholding";

	/** Set Percent withholding.
	  * Withholding amount is a percentage of the invoice amount
	  */
	public void setIsPercentWithholding (boolean IsPercentWithholding);

	/** Get Percent withholding.
	  * Withholding amount is a percentage of the invoice amount
	  */
	public boolean isPercentWithholding();

    /** Column name IsTaxProrated */
    public static final String COLUMNNAME_IsTaxProrated = "IsTaxProrated";

	/** Set Prorate tax.
	  * Tax is Prorated
	  */
	public void setIsTaxProrated (boolean IsTaxProrated);

	/** Get Prorate tax.
	  * Tax is Prorated
	  */
	public boolean isTaxProrated();

    /** Column name IsTaxWithholding */
    public static final String COLUMNNAME_IsTaxWithholding = "IsTaxWithholding";

	/** Set Tax withholding.
	  * This is a tax related withholding
	  */
	public void setIsTaxWithholding (boolean IsTaxWithholding);

	/** Get Tax withholding.
	  * This is a tax related withholding
	  */
	public boolean isTaxWithholding();

    /** Column name MaxAmt */
    public static final String COLUMNNAME_MaxAmt = "MaxAmt";

	/** Set Max Amount.
	  * Maximum Amount in invoice currency
	  */
	public void setMaxAmt (BigDecimal MaxAmt);

	/** Get Max Amount.
	  * Maximum Amount in invoice currency
	  */
	public BigDecimal getMaxAmt();

    /** Column name MinAmt */
    public static final String COLUMNNAME_MinAmt = "MinAmt";

	/** Set Min Amount.
	  * Minimum Amount in invoice currency
	  */
	public void setMinAmt (BigDecimal MinAmt);

	/** Get Min Amount.
	  * Minimum Amount in invoice currency
	  */
	public BigDecimal getMinAmt();

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

    /** Column name Percent */
    public static final String COLUMNNAME_Percent = "Percent";

	/** Set Percent.
	  * Percentage
	  */
	public void setPercent (BigDecimal Percent);

	/** Get Percent.
	  * Percentage
	  */
	public BigDecimal getPercent();

    /** Column name ThresholdMax */
    public static final String COLUMNNAME_ThresholdMax = "ThresholdMax";

	/** Set Threshold max.
	  * Maximum gross amount for withholding calculation  (0=no limit)
	  */
	public void setThresholdMax (BigDecimal ThresholdMax);

	/** Get Threshold max.
	  * Maximum gross amount for withholding calculation  (0=no limit)
	  */
	public BigDecimal getThresholdMax();

    /** Column name Thresholdmin */
    public static final String COLUMNNAME_Thresholdmin = "Thresholdmin";

	/** Set Threshold min.
	  * Minimum gross amount for withholding calculation
	  */
	public void setThresholdmin (BigDecimal Thresholdmin);

	/** Get Threshold min.
	  * Minimum gross amount for withholding calculation
	  */
	public BigDecimal getThresholdmin();

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
}
