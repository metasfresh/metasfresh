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

/** Generated Interface for I_ElementValue
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_I_ElementValue 
{

    /** TableName=I_ElementValue */
    public static final String Table_Name = "I_ElementValue";

    /** AD_Table_ID=534 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

    /** Load Meta Data */

    /** Column name AccountSign */
    public static final String COLUMNNAME_AccountSign = "AccountSign";

	/** Set Account Sign.
	  * Indicates the Natural Sign of the Account as a Debit or Credit
	  */
	public void setAccountSign (String AccountSign);

	/** Get Account Sign.
	  * Indicates the Natural Sign of the Account as a Debit or Credit
	  */
	public String getAccountSign();

    /** Column name AccountType */
    public static final String COLUMNNAME_AccountType = "AccountType";

	/** Set Account Type.
	  * Indicates the type of account
	  */
	public void setAccountType (String AccountType);

	/** Get Account Type.
	  * Indicates the type of account
	  */
	public String getAccountType();

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

    /** Column name C_Element_ID */
    public static final String COLUMNNAME_C_Element_ID = "C_Element_ID";

	/** Set Element.
	  * Accounting Element
	  */
	public void setC_Element_ID (int C_Element_ID);

	/** Get Element.
	  * Accounting Element
	  */
	public int getC_Element_ID();

	public I_C_Element getC_Element() throws RuntimeException;

    /** Column name C_ElementValue_ID */
    public static final String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/** Set Account Element.
	  * Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID);

	/** Get Account Element.
	  * Account Element
	  */
	public int getC_ElementValue_ID();

	public I_C_ElementValue getC_ElementValue() throws RuntimeException;

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

    /** Column name Default_Account */
    public static final String COLUMNNAME_Default_Account = "Default_Account";

	/** Set Default Account.
	  * Name of the Default Account Column
	  */
	public void setDefault_Account (String Default_Account);

	/** Get Default Account.
	  * Name of the Default Account Column
	  */
	public String getDefault_Account();

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

    /** Column name ElementName */
    public static final String COLUMNNAME_ElementName = "ElementName";

	/** Set Element Name.
	  * Name of the Element
	  */
	public void setElementName (String ElementName);

	/** Get Element Name.
	  * Name of the Element
	  */
	public String getElementName();

    /** Column name I_ElementValue_ID */
    public static final String COLUMNNAME_I_ElementValue_ID = "I_ElementValue_ID";

	/** Set Import Account.
	  * Import Account Value
	  */
	public void setI_ElementValue_ID (int I_ElementValue_ID);

	/** Get Import Account.
	  * Import Account Value
	  */
	public int getI_ElementValue_ID();

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

    /** Column name IsDocControlled */
    public static final String COLUMNNAME_IsDocControlled = "IsDocControlled";

	/** Set Document Controlled.
	  * Control account - If an account is controlled by a document, you cannot post manually to it
	  */
	public void setIsDocControlled (boolean IsDocControlled);

	/** Get Document Controlled.
	  * Control account - If an account is controlled by a document, you cannot post manually to it
	  */
	public boolean isDocControlled();

    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/** Set Summary Level.
	  * This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary);

	/** Get Summary Level.
	  * This is a summary entity
	  */
	public boolean isSummary();

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

    /** Column name ParentElementValue_ID */
    public static final String COLUMNNAME_ParentElementValue_ID = "ParentElementValue_ID";

	/** Set Parent Account.
	  * The parent (summary) account
	  */
	public void setParentElementValue_ID (int ParentElementValue_ID);

	/** Get Parent Account.
	  * The parent (summary) account
	  */
	public int getParentElementValue_ID();

	public I_C_ElementValue getParentElementValue() throws RuntimeException;

    /** Column name ParentValue */
    public static final String COLUMNNAME_ParentValue = "ParentValue";

	/** Set Parent Key.
	  * Key if the Parent
	  */
	public void setParentValue (String ParentValue);

	/** Get Parent Key.
	  * Key if the Parent
	  */
	public String getParentValue();

    /** Column name PostActual */
    public static final String COLUMNNAME_PostActual = "PostActual";

	/** Set Post Actual.
	  * Actual Values can be posted
	  */
	public void setPostActual (boolean PostActual);

	/** Get Post Actual.
	  * Actual Values can be posted
	  */
	public boolean isPostActual();

    /** Column name PostBudget */
    public static final String COLUMNNAME_PostBudget = "PostBudget";

	/** Set Post Budget.
	  * Budget values can be posted
	  */
	public void setPostBudget (boolean PostBudget);

	/** Get Post Budget.
	  * Budget values can be posted
	  */
	public boolean isPostBudget();

    /** Column name PostEncumbrance */
    public static final String COLUMNNAME_PostEncumbrance = "PostEncumbrance";

	/** Set Post Encumbrance.
	  * Post commitments to this account
	  */
	public void setPostEncumbrance (boolean PostEncumbrance);

	/** Get Post Encumbrance.
	  * Post commitments to this account
	  */
	public boolean isPostEncumbrance();

    /** Column name PostStatistical */
    public static final String COLUMNNAME_PostStatistical = "PostStatistical";

	/** Set Post Statistical.
	  * Post statistical quantities to this account?
	  */
	public void setPostStatistical (boolean PostStatistical);

	/** Get Post Statistical.
	  * Post statistical quantities to this account?
	  */
	public boolean isPostStatistical();

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
}
