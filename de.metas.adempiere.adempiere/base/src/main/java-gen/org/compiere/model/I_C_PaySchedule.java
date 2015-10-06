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

/** Generated Interface for C_PaySchedule
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_PaySchedule 
{

    /** TableName=C_PaySchedule */
    public static final String Table_Name = "C_PaySchedule";

    /** AD_Table_ID=548 */
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

    /** Column name C_PaySchedule_ID */
    public static final String COLUMNNAME_C_PaySchedule_ID = "C_PaySchedule_ID";

	/** Set Payment Schedule.
	  * Payment Schedule Template
	  */
	public void setC_PaySchedule_ID (int C_PaySchedule_ID);

	/** Get Payment Schedule.
	  * Payment Schedule Template
	  */
	public int getC_PaySchedule_ID();

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

    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/** Set Discount %.
	  * Discount in percent
	  */
	public void setDiscount (BigDecimal Discount);

	/** Get Discount %.
	  * Discount in percent
	  */
	public BigDecimal getDiscount();

    /** Column name DiscountDays */
    public static final String COLUMNNAME_DiscountDays = "DiscountDays";

	/** Set Discount Days.
	  * Number of days from invoice date to be eligible for discount
	  */
	public void setDiscountDays (int DiscountDays);

	/** Get Discount Days.
	  * Number of days from invoice date to be eligible for discount
	  */
	public int getDiscountDays();

    /** Column name GraceDays */
    public static final String COLUMNNAME_GraceDays = "GraceDays";

	/** Set Grace Days.
	  * Days after due date to send first dunning letter
	  */
	public void setGraceDays (int GraceDays);

	/** Get Grace Days.
	  * Days after due date to send first dunning letter
	  */
	public int getGraceDays();

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

    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/** Set Valid.
	  * Element is valid
	  */
	public void setIsValid (boolean IsValid);

	/** Get Valid.
	  * Element is valid
	  */
	public boolean isValid();

    /** Column name NetDay */
    public static final String COLUMNNAME_NetDay = "NetDay";

	/** Set Net Day.
	  * Day when payment is due net
	  */
	public void setNetDay (String NetDay);

	/** Get Net Day.
	  * Day when payment is due net
	  */
	public String getNetDay();

    /** Column name NetDays */
    public static final String COLUMNNAME_NetDays = "NetDays";

	/** Set Net Days.
	  * Net Days in which payment is due
	  */
	public void setNetDays (int NetDays);

	/** Get Net Days.
	  * Net Days in which payment is due
	  */
	public int getNetDays();

    /** Column name Percentage */
    public static final String COLUMNNAME_Percentage = "Percentage";

	/** Set Percentage.
	  * Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage);

	/** Get Percentage.
	  * Percent of the entire amount
	  */
	public BigDecimal getPercentage();

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
