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

/** Generated Interface for I_InOutLineConfirm
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_I_InOutLineConfirm 
{

    /** TableName=I_InOutLineConfirm */
    public static final String Table_Name = "I_InOutLineConfirm";

    /** AD_Table_ID=740 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(2);

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

    /** Column name ConfirmationNo */
    public static final String COLUMNNAME_ConfirmationNo = "ConfirmationNo";

	/** Set Confirmation No.
	  * Confirmation Number
	  */
	public void setConfirmationNo (String ConfirmationNo);

	/** Get Confirmation No.
	  * Confirmation Number
	  */
	public String getConfirmationNo();

    /** Column name ConfirmedQty */
    public static final String COLUMNNAME_ConfirmedQty = "ConfirmedQty";

	/** Set Confirmed Quantity.
	  * Confirmation of a received quantity
	  */
	public void setConfirmedQty (BigDecimal ConfirmedQty);

	/** Get Confirmed Quantity.
	  * Confirmation of a received quantity
	  */
	public BigDecimal getConfirmedQty();

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

    /** Column name DifferenceQty */
    public static final String COLUMNNAME_DifferenceQty = "DifferenceQty";

	/** Set Difference.
	  * Difference Quantity
	  */
	public void setDifferenceQty (BigDecimal DifferenceQty);

	/** Get Difference.
	  * Difference Quantity
	  */
	public BigDecimal getDifferenceQty();

    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/** Set Import Error Message.
	  * Messages generated from import process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg);

	/** Get Import Error Message.
	  * Messages generated from import process
	  */
	public String getI_ErrorMsg();

    /** Column name I_InOutLineConfirm_ID */
    public static final String COLUMNNAME_I_InOutLineConfirm_ID = "I_InOutLineConfirm_ID";

	/** Set Ship/Receipt Confirmation Import Line.
	  * Material Shipment or Receipt Confirmation Import Line
	  */
	public void setI_InOutLineConfirm_ID (int I_InOutLineConfirm_ID);

	/** Get Ship/Receipt Confirmation Import Line.
	  * Material Shipment or Receipt Confirmation Import Line
	  */
	public int getI_InOutLineConfirm_ID();

    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/** Set Imported.
	  * Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported);

	/** Get Imported.
	  * Has this import been processed
	  */
	public boolean isI_IsImported();

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

    /** Column name M_InOutLineConfirm_ID */
    public static final String COLUMNNAME_M_InOutLineConfirm_ID = "M_InOutLineConfirm_ID";

	/** Set Ship/Receipt Confirmation Line.
	  * Material Shipment or Receipt Confirmation Line
	  */
	public void setM_InOutLineConfirm_ID (int M_InOutLineConfirm_ID);

	/** Get Ship/Receipt Confirmation Line.
	  * Material Shipment or Receipt Confirmation Line
	  */
	public int getM_InOutLineConfirm_ID();

	public I_M_InOutLineConfirm getM_InOutLineConfirm() throws RuntimeException;

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name ScrappedQty */
    public static final String COLUMNNAME_ScrappedQty = "ScrappedQty";

	/** Set Scrapped Quantity.
	  * The Quantity scrapped due to QA issues
	  */
	public void setScrappedQty (BigDecimal ScrappedQty);

	/** Get Scrapped Quantity.
	  * The Quantity scrapped due to QA issues
	  */
	public BigDecimal getScrappedQty();

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
