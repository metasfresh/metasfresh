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

/** Generated Interface for PA_ColorSchema
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_PA_ColorSchema 
{

    /** TableName=PA_ColorSchema */
    public static final String Table_Name = "PA_ColorSchema";

    /** AD_Table_ID=831 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

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

    /** Column name AD_PrintColor1_ID */
    public static final String COLUMNNAME_AD_PrintColor1_ID = "AD_PrintColor1_ID";

	/** Set Color 1.
	  * First color used
	  */
	public void setAD_PrintColor1_ID (int AD_PrintColor1_ID);

	/** Get Color 1.
	  * First color used
	  */
	public int getAD_PrintColor1_ID();

	public I_AD_PrintColor getAD_PrintColor1() throws RuntimeException;

    /** Column name AD_PrintColor2_ID */
    public static final String COLUMNNAME_AD_PrintColor2_ID = "AD_PrintColor2_ID";

	/** Set Color 2.
	  * Second color used
	  */
	public void setAD_PrintColor2_ID (int AD_PrintColor2_ID);

	/** Get Color 2.
	  * Second color used
	  */
	public int getAD_PrintColor2_ID();

	public I_AD_PrintColor getAD_PrintColor2() throws RuntimeException;

    /** Column name AD_PrintColor3_ID */
    public static final String COLUMNNAME_AD_PrintColor3_ID = "AD_PrintColor3_ID";

	/** Set Color 3.
	  * Third color used
	  */
	public void setAD_PrintColor3_ID (int AD_PrintColor3_ID);

	/** Get Color 3.
	  * Third color used
	  */
	public int getAD_PrintColor3_ID();

	public I_AD_PrintColor getAD_PrintColor3() throws RuntimeException;

    /** Column name AD_PrintColor4_ID */
    public static final String COLUMNNAME_AD_PrintColor4_ID = "AD_PrintColor4_ID";

	/** Set Color 4.
	  * Forth color used
	  */
	public void setAD_PrintColor4_ID (int AD_PrintColor4_ID);

	/** Get Color 4.
	  * Forth color used
	  */
	public int getAD_PrintColor4_ID();

	public I_AD_PrintColor getAD_PrintColor4() throws RuntimeException;

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

    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/** Set Entity Type.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType);

	/** Get Entity Type.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public String getEntityType();

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

    /** Column name Mark1Percent */
    public static final String COLUMNNAME_Mark1Percent = "Mark1Percent";

	/** Set Mark 1 Percent.
	  * Percentage up to this color is used
	  */
	public void setMark1Percent (int Mark1Percent);

	/** Get Mark 1 Percent.
	  * Percentage up to this color is used
	  */
	public int getMark1Percent();

    /** Column name Mark2Percent */
    public static final String COLUMNNAME_Mark2Percent = "Mark2Percent";

	/** Set Mark 2 Percent.
	  * Percentage up to this color is used
	  */
	public void setMark2Percent (int Mark2Percent);

	/** Get Mark 2 Percent.
	  * Percentage up to this color is used
	  */
	public int getMark2Percent();

    /** Column name Mark3Percent */
    public static final String COLUMNNAME_Mark3Percent = "Mark3Percent";

	/** Set Mark 3 Percent.
	  * Percentage up to this color is used
	  */
	public void setMark3Percent (int Mark3Percent);

	/** Get Mark 3 Percent.
	  * Percentage up to this color is used
	  */
	public int getMark3Percent();

    /** Column name Mark4Percent */
    public static final String COLUMNNAME_Mark4Percent = "Mark4Percent";

	/** Set Mark 4 Percent.
	  * Percentage up to this color is used
	  */
	public void setMark4Percent (int Mark4Percent);

	/** Get Mark 4 Percent.
	  * Percentage up to this color is used
	  */
	public int getMark4Percent();

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

    /** Column name PA_ColorSchema_ID */
    public static final String COLUMNNAME_PA_ColorSchema_ID = "PA_ColorSchema_ID";

	/** Set Color Schema.
	  * Performance Color Schema
	  */
	public void setPA_ColorSchema_ID (int PA_ColorSchema_ID);

	/** Get Color Schema.
	  * Performance Color Schema
	  */
	public int getPA_ColorSchema_ID();

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
