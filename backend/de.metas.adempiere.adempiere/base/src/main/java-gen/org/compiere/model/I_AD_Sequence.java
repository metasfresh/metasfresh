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

/** Generated Interface for AD_Sequence
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_Sequence 
{

    /** TableName=AD_Sequence */
    public static final String Table_Name = "AD_Sequence";

    /** AD_Table_ID=115 */
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

    /** Column name AD_Sequence_ID */
    public static final String COLUMNNAME_AD_Sequence_ID = "AD_Sequence_ID";

	/** Set Sequence.
	  * Document Sequence
	  */
	public void setAD_Sequence_ID (int AD_Sequence_ID);

	/** Get Sequence.
	  * Document Sequence
	  */
	public int getAD_Sequence_ID();

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

    /** Column name CurrentNext */
    public static final String COLUMNNAME_CurrentNext = "CurrentNext";

	/** Set Current Next.
	  * The next number to be used
	  */
	public void setCurrentNext (int CurrentNext);

	/** Get Current Next.
	  * The next number to be used
	  */
	public int getCurrentNext();

    /** Column name CurrentNextSys */
    public static final String COLUMNNAME_CurrentNextSys = "CurrentNextSys";

	/** Set Current Next (System).
	  * Next sequence for system use
	  */
	public void setCurrentNextSys (int CurrentNextSys);

	/** Get Current Next (System).
	  * Next sequence for system use
	  */
	public int getCurrentNextSys();

    /** Column name DateColumn */
    public static final String COLUMNNAME_DateColumn = "DateColumn";

	/** Set Date Column.
	  * Fully qualified date column
	  */
	public void setDateColumn (String DateColumn);

	/** Get Date Column.
	  * Fully qualified date column
	  */
	public String getDateColumn();

    /** Column name DecimalPattern */
    public static final String COLUMNNAME_DecimalPattern = "DecimalPattern";

	/** Set Decimal Pattern.
	  * Java Decimal Pattern
	  */
	public void setDecimalPattern (String DecimalPattern);

	/** Get Decimal Pattern.
	  * Java Decimal Pattern
	  */
	public String getDecimalPattern();

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

    /** Column name IncrementNo */
    public static final String COLUMNNAME_IncrementNo = "IncrementNo";

	/** Set Increment.
	  * The number to increment the last document number by
	  */
	public void setIncrementNo (int IncrementNo);

	/** Get Increment.
	  * The number to increment the last document number by
	  */
	public int getIncrementNo();

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

    /** Column name IsAudited */
    public static final String COLUMNNAME_IsAudited = "IsAudited";

	/** Set Activate Audit.
	  * Activate Audit Trail of what numbers are generated
	  */
	public void setIsAudited (boolean IsAudited);

	/** Get Activate Audit.
	  * Activate Audit Trail of what numbers are generated
	  */
	public boolean isAudited();

    /** Column name IsAutoSequence */
    public static final String COLUMNNAME_IsAutoSequence = "IsAutoSequence";

	/** Set Auto numbering.
	  * Automatically assign the next number
	  */
	public void setIsAutoSequence (boolean IsAutoSequence);

	/** Get Auto numbering.
	  * Automatically assign the next number
	  */
	public boolean isAutoSequence();

    /** Column name IsTableID */
    public static final String COLUMNNAME_IsTableID = "IsTableID";

	/** Set Used for Record ID.
	  * The document number  will be used as the record key
	  */
	public void setIsTableID (boolean IsTableID);

	/** Get Used for Record ID.
	  * The document number  will be used as the record key
	  */
	public boolean isTableID();

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

    /** Column name Prefix */
    public static final String COLUMNNAME_Prefix = "Prefix";

	/** Set Prefix.
	  * Prefix before the sequence number
	  */
	public void setPrefix (String Prefix);

	/** Get Prefix.
	  * Prefix before the sequence number
	  */
	public String getPrefix();

    /** Column name StartNewYear */
    public static final String COLUMNNAME_StartNewYear = "StartNewYear";

	/** Set Restart sequence every Year.
	  * Restart the sequence with Start on every 1/1
	  */
	public void setStartNewYear (boolean StartNewYear);

	/** Get Restart sequence every Year.
	  * Restart the sequence with Start on every 1/1
	  */
	public boolean isStartNewYear();

    /** Column name StartNo */
    public static final String COLUMNNAME_StartNo = "StartNo";

	/** Set Start No.
	  * Starting number/position
	  */
	public void setStartNo (int StartNo);

	/** Get Start No.
	  * Starting number/position
	  */
	public int getStartNo();

    /** Column name Suffix */
    public static final String COLUMNNAME_Suffix = "Suffix";

	/** Set Suffix.
	  * Suffix after the number
	  */
	public void setSuffix (String Suffix);

	/** Get Suffix.
	  * Suffix after the number
	  */
	public String getSuffix();

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

    /** Column name VFormat */
    public static final String COLUMNNAME_VFormat = "VFormat";

	/** Set Value Format.
	  * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setVFormat (String VFormat);

	/** Get Value Format.
	  * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public String getVFormat();
}
