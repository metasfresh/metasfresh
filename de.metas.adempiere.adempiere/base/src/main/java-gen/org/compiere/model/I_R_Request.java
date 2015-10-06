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

/** Generated Interface for R_Request
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_R_Request 
{

    /** TableName=R_Request */
    public static final String Table_Name = "R_Request";

    /** AD_Table_ID=417 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name A_Asset_ID */
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/** Set Asset.
	  * Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID);

	/** Get Asset.
	  * Asset used internally or by customers
	  */
	public int getA_Asset_ID();

	public I_A_Asset getA_Asset() throws RuntimeException;

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

    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/** Set Role.
	  * Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID);

	/** Get Role.
	  * Responsibility Role
	  */
	public int getAD_Role_ID();

	public I_AD_Role getAD_Role() throws RuntimeException;

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public I_AD_User getAD_User() throws RuntimeException;

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/** Set Activity.
	  * Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID);

	/** Get Activity.
	  * Business Activity
	  */
	public int getC_Activity_ID();

	public I_C_Activity getC_Activity() throws RuntimeException;

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

    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/** Set Campaign.
	  * Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/** Get Campaign.
	  * Marketing Campaign
	  */
	public int getC_Campaign_ID();

	public I_C_Campaign getC_Campaign() throws RuntimeException;

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name C_InvoiceRequest_ID */
    public static final String COLUMNNAME_C_InvoiceRequest_ID = "C_InvoiceRequest_ID";

	/** Set Request Invoice.
	  * The generated invoice for this request
	  */
	public void setC_InvoiceRequest_ID (int C_InvoiceRequest_ID);

	/** Get Request Invoice.
	  * The generated invoice for this request
	  */
	public int getC_InvoiceRequest_ID();

	public I_C_Invoice getC_InvoiceRequest() throws RuntimeException;

    /** Column name CloseDate */
    public static final String COLUMNNAME_CloseDate = "CloseDate";

	/** Set Close Date.
	  * Close Date
	  */
	public void setCloseDate (Timestamp CloseDate);

	/** Get Close Date.
	  * Close Date
	  */
	public Timestamp getCloseDate();

    /** Column name ConfidentialType */
    public static final String COLUMNNAME_ConfidentialType = "ConfidentialType";

	/** Set Confidentiality.
	  * Type of Confidentiality
	  */
	public void setConfidentialType (String ConfidentialType);

	/** Get Confidentiality.
	  * Type of Confidentiality
	  */
	public String getConfidentialType();

    /** Column name ConfidentialTypeEntry */
    public static final String COLUMNNAME_ConfidentialTypeEntry = "ConfidentialTypeEntry";

	/** Set Entry Confidentiality.
	  * Confidentiality of the individual entry
	  */
	public void setConfidentialTypeEntry (String ConfidentialTypeEntry);

	/** Get Entry Confidentiality.
	  * Confidentiality of the individual entry
	  */
	public String getConfidentialTypeEntry();

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public I_C_Order getC_Order() throws RuntimeException;

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/** Set Payment.
	  * Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID);

	/** Get Payment.
	  * Payment identifier
	  */
	public int getC_Payment_ID();

	public I_C_Payment getC_Payment() throws RuntimeException;

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public I_C_Project getC_Project() throws RuntimeException;

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

    /** Column name DateCompletePlan */
    public static final String COLUMNNAME_DateCompletePlan = "DateCompletePlan";

	/** Set Complete Plan.
	  * Planned Completion Date
	  */
	public void setDateCompletePlan (Timestamp DateCompletePlan);

	/** Get Complete Plan.
	  * Planned Completion Date
	  */
	public Timestamp getDateCompletePlan();

    /** Column name DateLastAction */
    public static final String COLUMNNAME_DateLastAction = "DateLastAction";

	/** Set Date last action.
	  * Date this request was last acted on
	  */
	public void setDateLastAction (Timestamp DateLastAction);

	/** Get Date last action.
	  * Date this request was last acted on
	  */
	public Timestamp getDateLastAction();

    /** Column name DateLastAlert */
    public static final String COLUMNNAME_DateLastAlert = "DateLastAlert";

	/** Set Last Alert.
	  * Date when last alert were sent
	  */
	public void setDateLastAlert (Timestamp DateLastAlert);

	/** Get Last Alert.
	  * Date when last alert were sent
	  */
	public Timestamp getDateLastAlert();

    /** Column name DateNextAction */
    public static final String COLUMNNAME_DateNextAction = "DateNextAction";

	/** Set Date next action.
	  * Date that this request should be acted on
	  */
	public void setDateNextAction (Timestamp DateNextAction);

	/** Get Date next action.
	  * Date that this request should be acted on
	  */
	public Timestamp getDateNextAction();

    /** Column name DateStartPlan */
    public static final String COLUMNNAME_DateStartPlan = "DateStartPlan";

	/** Set Start Plan.
	  * Planned Start Date
	  */
	public void setDateStartPlan (Timestamp DateStartPlan);

	/** Get Start Plan.
	  * Planned Start Date
	  */
	public Timestamp getDateStartPlan();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name DueType */
    public static final String COLUMNNAME_DueType = "DueType";

	/** Set Due type.
	  * Status of the next action for this Request
	  */
	public void setDueType (String DueType);

	/** Get Due type.
	  * Status of the next action for this Request
	  */
	public String getDueType();

    /** Column name EndTime */
    public static final String COLUMNNAME_EndTime = "EndTime";

	/** Set End Time.
	  * End of the time span
	  */
	public void setEndTime (Timestamp EndTime);

	/** Get End Time.
	  * End of the time span
	  */
	public Timestamp getEndTime();

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

    /** Column name IsEscalated */
    public static final String COLUMNNAME_IsEscalated = "IsEscalated";

	/** Set Escalated.
	  * This request has been escalated
	  */
	public void setIsEscalated (boolean IsEscalated);

	/** Get Escalated.
	  * This request has been escalated
	  */
	public boolean isEscalated();

    /** Column name IsInvoiced */
    public static final String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/** Set Invoiced.
	  * Is this invoiced?
	  */
	public void setIsInvoiced (boolean IsInvoiced);

	/** Get Invoiced.
	  * Is this invoiced?
	  */
	public boolean isInvoiced();

    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/** Set Self-Service.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService);

	/** Get Self-Service.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService();

    /** Column name LastResult */
    public static final String COLUMNNAME_LastResult = "LastResult";

	/** Set Last Result.
	  * Result of last contact
	  */
	public void setLastResult (String LastResult);

	/** Get Last Result.
	  * Result of last contact
	  */
	public String getLastResult();

    /** Column name M_ChangeRequest_ID */
    public static final String COLUMNNAME_M_ChangeRequest_ID = "M_ChangeRequest_ID";

	/** Set Change Request.
	  * BOM (Engineering) Change Request
	  */
	public void setM_ChangeRequest_ID (int M_ChangeRequest_ID);

	/** Get Change Request.
	  * BOM (Engineering) Change Request
	  */
	public int getM_ChangeRequest_ID();

	public I_M_ChangeRequest getM_ChangeRequest() throws RuntimeException;

    /** Column name M_FixChangeNotice_ID */
    public static final String COLUMNNAME_M_FixChangeNotice_ID = "M_FixChangeNotice_ID";

	/** Set Fixed in.
	  * Fixed in Change Notice
	  */
	public void setM_FixChangeNotice_ID (int M_FixChangeNotice_ID);

	/** Get Fixed in.
	  * Fixed in Change Notice
	  */
	public int getM_FixChangeNotice_ID();

	public I_M_ChangeNotice getM_FixChangeNotice() throws RuntimeException;

    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/** Set Shipment/Receipt.
	  * Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID);

	/** Get Shipment/Receipt.
	  * Material Shipment Document
	  */
	public int getM_InOut_ID();

	public I_M_InOut getM_InOut() throws RuntimeException;

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name M_ProductSpent_ID */
    public static final String COLUMNNAME_M_ProductSpent_ID = "M_ProductSpent_ID";

	/** Set Product Used.
	  * Product/Resource/Service used in Request
	  */
	public void setM_ProductSpent_ID (int M_ProductSpent_ID);

	/** Get Product Used.
	  * Product/Resource/Service used in Request
	  */
	public int getM_ProductSpent_ID();

	public I_M_Product getM_ProductSpent() throws RuntimeException;

    /** Column name M_RMA_ID */
    public static final String COLUMNNAME_M_RMA_ID = "M_RMA_ID";

	/** Set RMA.
	  * Return Material Authorization
	  */
	public void setM_RMA_ID (int M_RMA_ID);

	/** Get RMA.
	  * Return Material Authorization
	  */
	public int getM_RMA_ID();

	public I_M_RMA getM_RMA() throws RuntimeException;

    /** Column name NextAction */
    public static final String COLUMNNAME_NextAction = "NextAction";

	/** Set Next action.
	  * Next Action to be taken
	  */
	public void setNextAction (String NextAction);

	/** Get Next action.
	  * Next Action to be taken
	  */
	public String getNextAction();

    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

	/** Set Priority.
	  * Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (String Priority);

	/** Get Priority.
	  * Indicates if this request is of a high, medium or low priority.
	  */
	public String getPriority();

    /** Column name PriorityUser */
    public static final String COLUMNNAME_PriorityUser = "PriorityUser";

	/** Set User Importance.
	  * Priority of the issue for the User
	  */
	public void setPriorityUser (String PriorityUser);

	/** Get User Importance.
	  * Priority of the issue for the User
	  */
	public String getPriorityUser();

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

    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/** Set Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced);

	/** Get Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced();

    /** Column name QtyPlan */
    public static final String COLUMNNAME_QtyPlan = "QtyPlan";

	/** Set Quantity Plan.
	  * Planned Quantity
	  */
	public void setQtyPlan (BigDecimal QtyPlan);

	/** Get Quantity Plan.
	  * Planned Quantity
	  */
	public BigDecimal getQtyPlan();

    /** Column name QtySpent */
    public static final String COLUMNNAME_QtySpent = "QtySpent";

	/** Set Quantity Used.
	  * Quantity used for this event
	  */
	public void setQtySpent (BigDecimal QtySpent);

	/** Get Quantity Used.
	  * Quantity used for this event
	  */
	public BigDecimal getQtySpent();

    /** Column name R_Category_ID */
    public static final String COLUMNNAME_R_Category_ID = "R_Category_ID";

	/** Set Category.
	  * Request Category
	  */
	public void setR_Category_ID (int R_Category_ID);

	/** Get Category.
	  * Request Category
	  */
	public int getR_Category_ID();

	public I_R_Category getR_Category() throws RuntimeException;

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Record ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Record ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

    /** Column name RequestAmt */
    public static final String COLUMNNAME_RequestAmt = "RequestAmt";

	/** Set Request Amount.
	  * Amount associated with this request
	  */
	public void setRequestAmt (BigDecimal RequestAmt);

	/** Get Request Amount.
	  * Amount associated with this request
	  */
	public BigDecimal getRequestAmt();

    /** Column name Result */
    public static final String COLUMNNAME_Result = "Result";

	/** Set Result.
	  * Result of the action taken
	  */
	public void setResult (String Result);

	/** Get Result.
	  * Result of the action taken
	  */
	public String getResult();

    /** Column name R_Group_ID */
    public static final String COLUMNNAME_R_Group_ID = "R_Group_ID";

	/** Set Group.
	  * Request Group
	  */
	public void setR_Group_ID (int R_Group_ID);

	/** Get Group.
	  * Request Group
	  */
	public int getR_Group_ID();

	public I_R_Group getR_Group() throws RuntimeException;

    /** Column name R_MailText_ID */
    public static final String COLUMNNAME_R_MailText_ID = "R_MailText_ID";

	/** Set Mail Template.
	  * Text templates for mailings
	  */
	public void setR_MailText_ID (int R_MailText_ID);

	/** Get Mail Template.
	  * Text templates for mailings
	  */
	public int getR_MailText_ID();

	public I_R_MailText getR_MailText() throws RuntimeException;

    /** Column name R_Request_ID */
    public static final String COLUMNNAME_R_Request_ID = "R_Request_ID";

	/** Set Request.
	  * Request from a Business Partner or Prospect
	  */
	public void setR_Request_ID (int R_Request_ID);

	/** Get Request.
	  * Request from a Business Partner or Prospect
	  */
	public int getR_Request_ID();

    /** Column name R_RequestRelated_ID */
    public static final String COLUMNNAME_R_RequestRelated_ID = "R_RequestRelated_ID";

	/** Set Related Request.
	  * Related Request (Master Issue, ..)
	  */
	public void setR_RequestRelated_ID (int R_RequestRelated_ID);

	/** Get Related Request.
	  * Related Request (Master Issue, ..)
	  */
	public int getR_RequestRelated_ID();

	public I_R_Request getR_RequestRelated() throws RuntimeException;

    /** Column name R_RequestType_ID */
    public static final String COLUMNNAME_R_RequestType_ID = "R_RequestType_ID";

	/** Set Request Type.
	  * Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public void setR_RequestType_ID (int R_RequestType_ID);

	/** Get Request Type.
	  * Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public int getR_RequestType_ID();

	public I_R_RequestType getR_RequestType() throws RuntimeException;

    /** Column name R_Resolution_ID */
    public static final String COLUMNNAME_R_Resolution_ID = "R_Resolution_ID";

	/** Set Resolution.
	  * Request Resolution
	  */
	public void setR_Resolution_ID (int R_Resolution_ID);

	/** Get Resolution.
	  * Request Resolution
	  */
	public int getR_Resolution_ID();

	public I_R_Resolution getR_Resolution() throws RuntimeException;

    /** Column name R_StandardResponse_ID */
    public static final String COLUMNNAME_R_StandardResponse_ID = "R_StandardResponse_ID";

	/** Set Standard Response.
	  * Request Standard Response 
	  */
	public void setR_StandardResponse_ID (int R_StandardResponse_ID);

	/** Get Standard Response.
	  * Request Standard Response 
	  */
	public int getR_StandardResponse_ID();

	public I_R_StandardResponse getR_StandardResponse() throws RuntimeException;

    /** Column name R_Status_ID */
    public static final String COLUMNNAME_R_Status_ID = "R_Status_ID";

	/** Set Status.
	  * Request Status
	  */
	public void setR_Status_ID (int R_Status_ID);

	/** Get Status.
	  * Request Status
	  */
	public int getR_Status_ID();

	public I_R_Status getR_Status() throws RuntimeException;

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public I_AD_User getSalesRep() throws RuntimeException;

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name StartTime */
    public static final String COLUMNNAME_StartTime = "StartTime";

	/** Set Start Time.
	  * Time started
	  */
	public void setStartTime (Timestamp StartTime);

	/** Get Start Time.
	  * Time started
	  */
	public Timestamp getStartTime();

    /** Column name Summary */
    public static final String COLUMNNAME_Summary = "Summary";

	/** Set Summary.
	  * Textual summary of this request
	  */
	public void setSummary (String Summary);

	/** Get Summary.
	  * Textual summary of this request
	  */
	public String getSummary();

    /** Column name TaskStatus */
    public static final String COLUMNNAME_TaskStatus = "TaskStatus";

	/** Set Task Status.
	  * Status of the Task
	  */
	public void setTaskStatus (String TaskStatus);

	/** Get Task Status.
	  * Status of the Task
	  */
	public String getTaskStatus();

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
