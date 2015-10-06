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

/** Generated Interface for M_Warehouse_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_M_Warehouse_Acct 
{

    /** TableName=M_Warehouse_Acct */
    public static final String Table_Name = "M_Warehouse_Acct";

    /** AD_Table_ID=191 */
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

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public I_M_Warehouse getM_Warehouse() throws RuntimeException;

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

    /** Column name W_Differences_Acct */
    public static final String COLUMNNAME_W_Differences_Acct = "W_Differences_Acct";

	/** Set Warehouse Differences.
	  * Warehouse Differences Account
	  */
	public void setW_Differences_Acct (int W_Differences_Acct);

	/** Get Warehouse Differences.
	  * Warehouse Differences Account
	  */
	public int getW_Differences_Acct();

	public I_C_ValidCombination getW_Differences_A() throws RuntimeException;

    /** Column name W_InvActualAdjust_Acct */
    public static final String COLUMNNAME_W_InvActualAdjust_Acct = "W_InvActualAdjust_Acct";

	/** Set Inventory Adjustment.
	  * Account for Inventory value adjustments for Actual Costing
	  */
	public void setW_InvActualAdjust_Acct (int W_InvActualAdjust_Acct);

	/** Get Inventory Adjustment.
	  * Account for Inventory value adjustments for Actual Costing
	  */
	public int getW_InvActualAdjust_Acct();

	public I_C_ValidCombination getW_InvActualAdjust_A() throws RuntimeException;

    /** Column name W_Inventory_Acct */
    public static final String COLUMNNAME_W_Inventory_Acct = "W_Inventory_Acct";

	/** Set (Not Used).
	  * Warehouse Inventory Asset Account - Currently not used
	  */
	public void setW_Inventory_Acct (int W_Inventory_Acct);

	/** Get (Not Used).
	  * Warehouse Inventory Asset Account - Currently not used
	  */
	public int getW_Inventory_Acct();

	public I_C_ValidCombination getW_Inventory_A() throws RuntimeException;

    /** Column name W_Revaluation_Acct */
    public static final String COLUMNNAME_W_Revaluation_Acct = "W_Revaluation_Acct";

	/** Set Inventory Revaluation.
	  * Account for Inventory Revaluation
	  */
	public void setW_Revaluation_Acct (int W_Revaluation_Acct);

	/** Get Inventory Revaluation.
	  * Account for Inventory Revaluation
	  */
	public int getW_Revaluation_Acct();

	public I_C_ValidCombination getW_Revaluation_A() throws RuntimeException;
}
