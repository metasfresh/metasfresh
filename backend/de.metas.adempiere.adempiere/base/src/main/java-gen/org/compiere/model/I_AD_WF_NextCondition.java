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

/** Generated Interface for AD_WF_NextCondition
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_WF_NextCondition 
{

    /** TableName=AD_WF_NextCondition */
    public static final String Table_Name = "AD_WF_NextCondition";

    /** AD_Table_ID=706 */
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

    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/** Set Column.
	  * Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID);

	/** Get Column.
	  * Column in the table
	  */
	public int getAD_Column_ID();

	public I_AD_Column getAD_Column() throws RuntimeException;

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

    /** Column name AD_WF_NextCondition_ID */
    public static final String COLUMNNAME_AD_WF_NextCondition_ID = "AD_WF_NextCondition_ID";

	/** Set Transition Condition.
	  * Workflow Node Transition Condition
	  */
	public void setAD_WF_NextCondition_ID (int AD_WF_NextCondition_ID);

	/** Get Transition Condition.
	  * Workflow Node Transition Condition
	  */
	public int getAD_WF_NextCondition_ID();

    /** Column name AD_WF_NodeNext_ID */
    public static final String COLUMNNAME_AD_WF_NodeNext_ID = "AD_WF_NodeNext_ID";

	/** Set Node Transition.
	  * Workflow Node Transition
	  */
	public void setAD_WF_NodeNext_ID (int AD_WF_NodeNext_ID);

	/** Get Node Transition.
	  * Workflow Node Transition
	  */
	public int getAD_WF_NodeNext_ID();

	public I_AD_WF_NodeNext getAD_WF_NodeNext() throws RuntimeException;

    /** Column name AndOr */
    public static final String COLUMNNAME_AndOr = "AndOr";

	/** Set And/Or.
	  * Logical operation: AND or OR
	  */
	public void setAndOr (String AndOr);

	/** Get And/Or.
	  * Logical operation: AND or OR
	  */
	public String getAndOr();

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

    /** Column name Operation */
    public static final String COLUMNNAME_Operation = "Operation";

	/** Set Operation.
	  * Compare Operation
	  */
	public void setOperation (String Operation);

	/** Get Operation.
	  * Compare Operation
	  */
	public String getOperation();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name Value2 */
    public static final String COLUMNNAME_Value2 = "Value2";

	/** Set Value To.
	  * Value To
	  */
	public void setValue2 (String Value2);

	/** Get Value To.
	  * Value To
	  */
	public String getValue2();
}
