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

/** Generated Interface for A_Depreciation_Forecast
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_A_Depreciation_Forecast 
{

    /** TableName=A_Depreciation_Forecast */
    public static final String Table_Name = "A_Depreciation_Forecast";

    /** AD_Table_ID=53118 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name A_Depreciation_Forecast_ID */
    public static final String COLUMNNAME_A_Depreciation_Forecast_ID = "A_Depreciation_Forecast_ID";

	/** Set A_Depreciation_Forecast_ID	  */
	public void setA_Depreciation_Forecast_ID (int A_Depreciation_Forecast_ID);

	/** Get A_Depreciation_Forecast_ID	  */
	public int getA_Depreciation_Forecast_ID();

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

    /** Column name A_End_Asset_ID */
    public static final String COLUMNNAME_A_End_Asset_ID = "A_End_Asset_ID";

	/** Set End Aset ID	  */
	public void setA_End_Asset_ID (int A_End_Asset_ID);

	/** Get End Aset ID	  */
	public int getA_End_Asset_ID();

	public I_A_Asset getA_End_Asset() throws RuntimeException;

    /** Column name A_Start_Asset_ID */
    public static final String COLUMNNAME_A_Start_Asset_ID = "A_Start_Asset_ID";

	/** Set A_Start_Asset_ID	  */
	public void setA_Start_Asset_ID (int A_Start_Asset_ID);

	/** Get A_Start_Asset_ID	  */
	public int getA_Start_Asset_ID();

	public I_A_Asset getA_Start_Asset() throws RuntimeException;

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

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
