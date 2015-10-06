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

/** Generated Interface for C_CommissionAmt
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_CommissionAmt 
{

    /** TableName=C_CommissionAmt */
    public static final String Table_Name = "C_CommissionAmt";

    /** AD_Table_ID=430 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name ActualQty */
    public static final String COLUMNNAME_ActualQty = "ActualQty";

	/** Set Actual Quantity.
	  * The actual quantity
	  */
	public void setActualQty (BigDecimal ActualQty);

	/** Get Actual Quantity.
	  * The actual quantity
	  */
	public BigDecimal getActualQty();

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

    /** Column name C_CommissionAmt_ID */
    public static final String COLUMNNAME_C_CommissionAmt_ID = "C_CommissionAmt_ID";

	/** Set Commission Amount.
	  * Generated Commission Amount 
	  */
	public void setC_CommissionAmt_ID (int C_CommissionAmt_ID);

	/** Get Commission Amount.
	  * Generated Commission Amount 
	  */
	public int getC_CommissionAmt_ID();

    /** Column name C_CommissionLine_ID */
    public static final String COLUMNNAME_C_CommissionLine_ID = "C_CommissionLine_ID";

	/** Set Commission Line.
	  * Commission Line
	  */
	public void setC_CommissionLine_ID (int C_CommissionLine_ID);

	/** Get Commission Line.
	  * Commission Line
	  */
	public int getC_CommissionLine_ID();

	public I_C_CommissionLine getC_CommissionLine() throws RuntimeException;

    /** Column name C_CommissionRun_ID */
    public static final String COLUMNNAME_C_CommissionRun_ID = "C_CommissionRun_ID";

	/** Set Commission Run.
	  * Commission Run or Process
	  */
	public void setC_CommissionRun_ID (int C_CommissionRun_ID);

	/** Get Commission Run.
	  * Commission Run or Process
	  */
	public int getC_CommissionRun_ID();

	public I_C_CommissionRun getC_CommissionRun() throws RuntimeException;

    /** Column name CommissionAmt */
    public static final String COLUMNNAME_CommissionAmt = "CommissionAmt";

	/** Set Commission Amount.
	  * Commission Amount
	  */
	public void setCommissionAmt (BigDecimal CommissionAmt);

	/** Get Commission Amount.
	  * Commission Amount
	  */
	public BigDecimal getCommissionAmt();

    /** Column name ConvertedAmt */
    public static final String COLUMNNAME_ConvertedAmt = "ConvertedAmt";

	/** Set Converted Amount.
	  * Converted Amount
	  */
	public void setConvertedAmt (BigDecimal ConvertedAmt);

	/** Get Converted Amount.
	  * Converted Amount
	  */
	public BigDecimal getConvertedAmt();

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
