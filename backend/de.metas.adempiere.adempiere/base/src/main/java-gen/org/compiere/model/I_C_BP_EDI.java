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

/** Generated Interface for C_BP_EDI
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_BP_EDI 
{

    /** TableName=C_BP_EDI */
    public static final String Table_Name = "C_BP_EDI";

    /** AD_Table_ID=366 */
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

	public I_AD_Sequence getAD_Sequence() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BP_EDI_ID */
    public static final String COLUMNNAME_C_BP_EDI_ID = "C_BP_EDI_ID";

	/** Set EDI Definition.
	  * Electronic Data Interchange
	  */
	public void setC_BP_EDI_ID (int C_BP_EDI_ID);

	/** Get EDI Definition.
	  * Electronic Data Interchange
	  */
	public int getC_BP_EDI_ID();

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

    /** Column name CustomerNo */
    public static final String COLUMNNAME_CustomerNo = "CustomerNo";

	/** Set Customer No.
	  * EDI Identification Number 
	  */
	public void setCustomerNo (String CustomerNo);

	/** Get Customer No.
	  * EDI Identification Number 
	  */
	public String getCustomerNo();

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

    /** Column name EDIType */
    public static final String COLUMNNAME_EDIType = "EDIType";

	/** Set EDI Type	  */
	public void setEDIType (String EDIType);

	/** Get EDI Type	  */
	public String getEDIType();

    /** Column name EMail_Error_To */
    public static final String COLUMNNAME_EMail_Error_To = "EMail_Error_To";

	/** Set Error EMail.
	  * Email address to send error messages to
	  */
	public void setEMail_Error_To (String EMail_Error_To);

	/** Get Error EMail.
	  * Email address to send error messages to
	  */
	public String getEMail_Error_To();

    /** Column name EMail_From */
    public static final String COLUMNNAME_EMail_From = "EMail_From";

	/** Set From EMail.
	  * Full EMail address used to send requests - e.g. edi@organization.com
	  */
	public void setEMail_From (String EMail_From);

	/** Get From EMail.
	  * Full EMail address used to send requests - e.g. edi@organization.com
	  */
	public String getEMail_From();

    /** Column name EMail_From_Pwd */
    public static final String COLUMNNAME_EMail_From_Pwd = "EMail_From_Pwd";

	/** Set From EMail Password.
	  * Password of the sending EMail address
	  */
	public void setEMail_From_Pwd (String EMail_From_Pwd);

	/** Get From EMail Password.
	  * Password of the sending EMail address
	  */
	public String getEMail_From_Pwd();

    /** Column name EMail_From_Uid */
    public static final String COLUMNNAME_EMail_From_Uid = "EMail_From_Uid";

	/** Set From EMail User ID.
	  * User ID of the sending EMail address (on default SMTP Host) - e.g. edi
	  */
	public void setEMail_From_Uid (String EMail_From_Uid);

	/** Get From EMail User ID.
	  * User ID of the sending EMail address (on default SMTP Host) - e.g. edi
	  */
	public String getEMail_From_Uid();

    /** Column name EMail_Info_To */
    public static final String COLUMNNAME_EMail_Info_To = "EMail_Info_To";

	/** Set Info EMail.
	  * EMail address to send informational messages and copies
	  */
	public void setEMail_Info_To (String EMail_Info_To);

	/** Get Info EMail.
	  * EMail address to send informational messages and copies
	  */
	public String getEMail_Info_To();

    /** Column name EMail_To */
    public static final String COLUMNNAME_EMail_To = "EMail_To";

	/** Set To EMail.
	  * EMail address to send requests to - e.g. edi@manufacturer.com 
	  */
	public void setEMail_To (String EMail_To);

	/** Get To EMail.
	  * EMail address to send requests to - e.g. edi@manufacturer.com 
	  */
	public String getEMail_To();

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

    /** Column name IsInfoSent */
    public static final String COLUMNNAME_IsInfoSent = "IsInfoSent";

	/** Set Send Info.
	  * Send informational messages and copies
	  */
	public void setIsInfoSent (boolean IsInfoSent);

	/** Get Send Info.
	  * Send informational messages and copies
	  */
	public boolean isInfoSent();

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

    /** Column name ReceiveInquiryReply */
    public static final String COLUMNNAME_ReceiveInquiryReply = "ReceiveInquiryReply";

	/** Set Received Inquiry Reply	  */
	public void setReceiveInquiryReply (boolean ReceiveInquiryReply);

	/** Get Received Inquiry Reply	  */
	public boolean isReceiveInquiryReply();

    /** Column name ReceiveOrderReply */
    public static final String COLUMNNAME_ReceiveOrderReply = "ReceiveOrderReply";

	/** Set Receive Order Reply	  */
	public void setReceiveOrderReply (boolean ReceiveOrderReply);

	/** Get Receive Order Reply	  */
	public boolean isReceiveOrderReply();

    /** Column name SendInquiry */
    public static final String COLUMNNAME_SendInquiry = "SendInquiry";

	/** Set Send Inquiry.
	  * Quantity Availability Inquiry
	  */
	public void setSendInquiry (boolean SendInquiry);

	/** Get Send Inquiry.
	  * Quantity Availability Inquiry
	  */
	public boolean isSendInquiry();

    /** Column name SendOrder */
    public static final String COLUMNNAME_SendOrder = "SendOrder";

	/** Set Send Order	  */
	public void setSendOrder (boolean SendOrder);

	/** Get Send Order	  */
	public boolean isSendOrder();

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
