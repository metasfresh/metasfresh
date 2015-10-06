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
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for R_Request
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_R_Request extends PO implements I_R_Request, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_R_Request (Properties ctx, int R_Request_ID, String trxName)
    {
      super (ctx, R_Request_ID, trxName);
      /** if (R_Request_ID == 0)
        {
			setConfidentialType (null);
// C
			setConfidentialTypeEntry (null);
// C
			setDocumentNo (null);
			setDueType (null);
// 5
			setIsEscalated (false);
			setIsInvoiced (false);
			setIsSelfService (false);
// N
			setPriority (null);
// 5
			setProcessed (false);
			setRequestAmt (Env.ZERO);
			setR_Request_ID (0);
			setR_RequestType_ID (0);
			setSummary (null);
        } */
    }

    /** Load Constructor */
    public X_R_Request (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_R_Request[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_A_Asset getA_Asset() throws RuntimeException
    {
		return (I_A_Asset)MTable.get(getCtx(), I_A_Asset.Table_Name)
			.getPO(getA_Asset_ID(), get_TrxName());	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Role getAD_Role() throws RuntimeException
    {
		return (I_AD_Role)MTable.get(getCtx(), I_AD_Role.Table_Name)
			.getPO(getAD_Role_ID(), get_TrxName());	}

	/** Set Role.
		@param AD_Role_ID 
		Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_User getAD_User() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Activity getC_Activity() throws RuntimeException
    {
		return (I_C_Activity)MTable.get(getCtx(), I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Campaign getC_Campaign() throws RuntimeException
    {
		return (I_C_Campaign)MTable.get(getCtx(), I_C_Campaign.Table_Name)
			.getPO(getC_Campaign_ID(), get_TrxName());	}

	/** Set Campaign.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Campaign.
		@return Marketing Campaign
	  */
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Invoice getC_InvoiceRequest() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_InvoiceRequest_ID(), get_TrxName());	}

	/** Set Request Invoice.
		@param C_InvoiceRequest_ID 
		The generated invoice for this request
	  */
	public void setC_InvoiceRequest_ID (int C_InvoiceRequest_ID)
	{
		if (C_InvoiceRequest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceRequest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceRequest_ID, Integer.valueOf(C_InvoiceRequest_ID));
	}

	/** Get Request Invoice.
		@return The generated invoice for this request
	  */
	public int getC_InvoiceRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Close Date.
		@param CloseDate 
		Close Date
	  */
	public void setCloseDate (Timestamp CloseDate)
	{
		set_Value (COLUMNNAME_CloseDate, CloseDate);
	}

	/** Get Close Date.
		@return Close Date
	  */
	public Timestamp getCloseDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CloseDate);
	}

	/** ConfidentialType AD_Reference_ID=340 */
	public static final int CONFIDENTIALTYPE_AD_Reference_ID=340;
	/** Public Information = A */
	public static final String CONFIDENTIALTYPE_PublicInformation = "A";
	/** Partner Confidential = C */
	public static final String CONFIDENTIALTYPE_PartnerConfidential = "C";
	/** Internal = I */
	public static final String CONFIDENTIALTYPE_Internal = "I";
	/** Private Information = P */
	public static final String CONFIDENTIALTYPE_PrivateInformation = "P";
	/** Set Confidentiality.
		@param ConfidentialType 
		Type of Confidentiality
	  */
	public void setConfidentialType (String ConfidentialType)
	{

		set_Value (COLUMNNAME_ConfidentialType, ConfidentialType);
	}

	/** Get Confidentiality.
		@return Type of Confidentiality
	  */
	public String getConfidentialType () 
	{
		return (String)get_Value(COLUMNNAME_ConfidentialType);
	}

	/** ConfidentialTypeEntry AD_Reference_ID=340 */
	public static final int CONFIDENTIALTYPEENTRY_AD_Reference_ID=340;
	/** Public Information = A */
	public static final String CONFIDENTIALTYPEENTRY_PublicInformation = "A";
	/** Partner Confidential = C */
	public static final String CONFIDENTIALTYPEENTRY_PartnerConfidential = "C";
	/** Internal = I */
	public static final String CONFIDENTIALTYPEENTRY_Internal = "I";
	/** Private Information = P */
	public static final String CONFIDENTIALTYPEENTRY_PrivateInformation = "P";
	/** Set Entry Confidentiality.
		@param ConfidentialTypeEntry 
		Confidentiality of the individual entry
	  */
	public void setConfidentialTypeEntry (String ConfidentialTypeEntry)
	{

		set_Value (COLUMNNAME_ConfidentialTypeEntry, ConfidentialTypeEntry);
	}

	/** Get Entry Confidentiality.
		@return Confidentiality of the individual entry
	  */
	public String getConfidentialTypeEntry () 
	{
		return (String)get_Value(COLUMNNAME_ConfidentialTypeEntry);
	}

	public I_C_Order getC_Order() throws RuntimeException
    {
		return (I_C_Order)MTable.get(getCtx(), I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Payment getC_Payment() throws RuntimeException
    {
		return (I_C_Payment)MTable.get(getCtx(), I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment identifier
	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Project getC_Project() throws RuntimeException
    {
		return (I_C_Project)MTable.get(getCtx(), I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Complete Plan.
		@param DateCompletePlan 
		Planned Completion Date
	  */
	public void setDateCompletePlan (Timestamp DateCompletePlan)
	{
		set_Value (COLUMNNAME_DateCompletePlan, DateCompletePlan);
	}

	/** Get Complete Plan.
		@return Planned Completion Date
	  */
	public Timestamp getDateCompletePlan () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateCompletePlan);
	}

	/** Set Date last action.
		@param DateLastAction 
		Date this request was last acted on
	  */
	public void setDateLastAction (Timestamp DateLastAction)
	{
		set_ValueNoCheck (COLUMNNAME_DateLastAction, DateLastAction);
	}

	/** Get Date last action.
		@return Date this request was last acted on
	  */
	public Timestamp getDateLastAction () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLastAction);
	}

	/** Set Last Alert.
		@param DateLastAlert 
		Date when last alert were sent
	  */
	public void setDateLastAlert (Timestamp DateLastAlert)
	{
		set_Value (COLUMNNAME_DateLastAlert, DateLastAlert);
	}

	/** Get Last Alert.
		@return Date when last alert were sent
	  */
	public Timestamp getDateLastAlert () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLastAlert);
	}

	/** Set Date next action.
		@param DateNextAction 
		Date that this request should be acted on
	  */
	public void setDateNextAction (Timestamp DateNextAction)
	{
		set_Value (COLUMNNAME_DateNextAction, DateNextAction);
	}

	/** Get Date next action.
		@return Date that this request should be acted on
	  */
	public Timestamp getDateNextAction () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateNextAction);
	}

	/** Set Start Plan.
		@param DateStartPlan 
		Planned Start Date
	  */
	public void setDateStartPlan (Timestamp DateStartPlan)
	{
		set_Value (COLUMNNAME_DateStartPlan, DateStartPlan);
	}

	/** Get Start Plan.
		@return Planned Start Date
	  */
	public Timestamp getDateStartPlan () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStartPlan);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	/** DueType AD_Reference_ID=222 */
	public static final int DUETYPE_AD_Reference_ID=222;
	/** Overdue = 3 */
	public static final String DUETYPE_Overdue = "3";
	/** Due = 5 */
	public static final String DUETYPE_Due = "5";
	/** Scheduled = 7 */
	public static final String DUETYPE_Scheduled = "7";
	/** Set Due type.
		@param DueType 
		Status of the next action for this Request
	  */
	public void setDueType (String DueType)
	{

		set_Value (COLUMNNAME_DueType, DueType);
	}

	/** Get Due type.
		@return Status of the next action for this Request
	  */
	public String getDueType () 
	{
		return (String)get_Value(COLUMNNAME_DueType);
	}

	/** Set End Time.
		@param EndTime 
		End of the time span
	  */
	public void setEndTime (Timestamp EndTime)
	{
		set_Value (COLUMNNAME_EndTime, EndTime);
	}

	/** Get End Time.
		@return End of the time span
	  */
	public Timestamp getEndTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndTime);
	}

	/** Set Escalated.
		@param IsEscalated 
		This request has been escalated
	  */
	public void setIsEscalated (boolean IsEscalated)
	{
		set_Value (COLUMNNAME_IsEscalated, Boolean.valueOf(IsEscalated));
	}

	/** Get Escalated.
		@return This request has been escalated
	  */
	public boolean isEscalated () 
	{
		Object oo = get_Value(COLUMNNAME_IsEscalated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Invoiced.
		@param IsInvoiced 
		Is this invoiced?
	  */
	public void setIsInvoiced (boolean IsInvoiced)
	{
		set_Value (COLUMNNAME_IsInvoiced, Boolean.valueOf(IsInvoiced));
	}

	/** Get Invoiced.
		@return Is this invoiced?
	  */
	public boolean isInvoiced () 
	{
		Object oo = get_Value(COLUMNNAME_IsInvoiced);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Self-Service.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService)
	{
		set_ValueNoCheck (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Self-Service.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Result.
		@param LastResult 
		Result of last contact
	  */
	public void setLastResult (String LastResult)
	{
		set_Value (COLUMNNAME_LastResult, LastResult);
	}

	/** Get Last Result.
		@return Result of last contact
	  */
	public String getLastResult () 
	{
		return (String)get_Value(COLUMNNAME_LastResult);
	}

	public I_M_ChangeRequest getM_ChangeRequest() throws RuntimeException
    {
		return (I_M_ChangeRequest)MTable.get(getCtx(), I_M_ChangeRequest.Table_Name)
			.getPO(getM_ChangeRequest_ID(), get_TrxName());	}

	/** Set Change Request.
		@param M_ChangeRequest_ID 
		BOM (Engineering) Change Request
	  */
	public void setM_ChangeRequest_ID (int M_ChangeRequest_ID)
	{
		if (M_ChangeRequest_ID < 1) 
			set_Value (COLUMNNAME_M_ChangeRequest_ID, null);
		else 
			set_Value (COLUMNNAME_M_ChangeRequest_ID, Integer.valueOf(M_ChangeRequest_ID));
	}

	/** Get Change Request.
		@return BOM (Engineering) Change Request
	  */
	public int getM_ChangeRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ChangeRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_ChangeNotice getM_FixChangeNotice() throws RuntimeException
    {
		return (I_M_ChangeNotice)MTable.get(getCtx(), I_M_ChangeNotice.Table_Name)
			.getPO(getM_FixChangeNotice_ID(), get_TrxName());	}

	/** Set Fixed in.
		@param M_FixChangeNotice_ID 
		Fixed in Change Notice
	  */
	public void setM_FixChangeNotice_ID (int M_FixChangeNotice_ID)
	{
		if (M_FixChangeNotice_ID < 1) 
			set_Value (COLUMNNAME_M_FixChangeNotice_ID, null);
		else 
			set_Value (COLUMNNAME_M_FixChangeNotice_ID, Integer.valueOf(M_FixChangeNotice_ID));
	}

	/** Get Fixed in.
		@return Fixed in Change Notice
	  */
	public int getM_FixChangeNotice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FixChangeNotice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_InOut getM_InOut() throws RuntimeException
    {
		return (I_M_InOut)MTable.get(getCtx(), I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_ProductSpent() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_ProductSpent_ID(), get_TrxName());	}

	/** Set Product Used.
		@param M_ProductSpent_ID 
		Product/Resource/Service used in Request
	  */
	public void setM_ProductSpent_ID (int M_ProductSpent_ID)
	{
		if (M_ProductSpent_ID < 1) 
			set_Value (COLUMNNAME_M_ProductSpent_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductSpent_ID, Integer.valueOf(M_ProductSpent_ID));
	}

	/** Get Product Used.
		@return Product/Resource/Service used in Request
	  */
	public int getM_ProductSpent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductSpent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_RMA getM_RMA() throws RuntimeException
    {
		return (I_M_RMA)MTable.get(getCtx(), I_M_RMA.Table_Name)
			.getPO(getM_RMA_ID(), get_TrxName());	}

	/** Set RMA.
		@param M_RMA_ID 
		Return Material Authorization
	  */
	public void setM_RMA_ID (int M_RMA_ID)
	{
		if (M_RMA_ID < 1) 
			set_Value (COLUMNNAME_M_RMA_ID, null);
		else 
			set_Value (COLUMNNAME_M_RMA_ID, Integer.valueOf(M_RMA_ID));
	}

	/** Get RMA.
		@return Return Material Authorization
	  */
	public int getM_RMA_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_RMA_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** NextAction AD_Reference_ID=219 */
	public static final int NEXTACTION_AD_Reference_ID=219;
	/** None = N */
	public static final String NEXTACTION_None = "N";
	/** Follow up = F */
	public static final String NEXTACTION_FollowUp = "F";
	/** Set Next action.
		@param NextAction 
		Next Action to be taken
	  */
	public void setNextAction (String NextAction)
	{

		set_Value (COLUMNNAME_NextAction, NextAction);
	}

	/** Get Next action.
		@return Next Action to be taken
	  */
	public String getNextAction () 
	{
		return (String)get_Value(COLUMNNAME_NextAction);
	}

	/** Priority AD_Reference_ID=154 */
	public static final int PRIORITY_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITY_High = "3";
	/** Medium = 5 */
	public static final String PRIORITY_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITY_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITY_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITY_Minor = "9";
	/** Set Priority.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (String Priority)
	{

		set_Value (COLUMNNAME_Priority, Priority);
	}

	/** Get Priority.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	public String getPriority () 
	{
		return (String)get_Value(COLUMNNAME_Priority);
	}

	/** PriorityUser AD_Reference_ID=154 */
	public static final int PRIORITYUSER_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITYUSER_High = "3";
	/** Medium = 5 */
	public static final String PRIORITYUSER_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITYUSER_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITYUSER_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITYUSER_Minor = "9";
	/** Set User Importance.
		@param PriorityUser 
		Priority of the issue for the User
	  */
	public void setPriorityUser (String PriorityUser)
	{

		set_Value (COLUMNNAME_PriorityUser, PriorityUser);
	}

	/** Get User Importance.
		@return Priority of the issue for the User
	  */
	public String getPriorityUser () 
	{
		return (String)get_Value(COLUMNNAME_PriorityUser);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Plan.
		@param QtyPlan 
		Planned Quantity
	  */
	public void setQtyPlan (BigDecimal QtyPlan)
	{
		set_Value (COLUMNNAME_QtyPlan, QtyPlan);
	}

	/** Get Quantity Plan.
		@return Planned Quantity
	  */
	public BigDecimal getQtyPlan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPlan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Used.
		@param QtySpent 
		Quantity used for this event
	  */
	public void setQtySpent (BigDecimal QtySpent)
	{
		set_Value (COLUMNNAME_QtySpent, QtySpent);
	}

	/** Get Quantity Used.
		@return Quantity used for this event
	  */
	public BigDecimal getQtySpent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySpent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_R_Category getR_Category() throws RuntimeException
    {
		return (I_R_Category)MTable.get(getCtx(), I_R_Category.Table_Name)
			.getPO(getR_Category_ID(), get_TrxName());	}

	/** Set Category.
		@param R_Category_ID 
		Request Category
	  */
	public void setR_Category_ID (int R_Category_ID)
	{
		if (R_Category_ID < 1) 
			set_Value (COLUMNNAME_R_Category_ID, null);
		else 
			set_Value (COLUMNNAME_R_Category_ID, Integer.valueOf(R_Category_ID));
	}

	/** Get Category.
		@return Request Category
	  */
	public int getR_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Request Amount.
		@param RequestAmt 
		Amount associated with this request
	  */
	public void setRequestAmt (BigDecimal RequestAmt)
	{
		set_Value (COLUMNNAME_RequestAmt, RequestAmt);
	}

	/** Get Request Amount.
		@return Amount associated with this request
	  */
	public BigDecimal getRequestAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RequestAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Result.
		@param Result 
		Result of the action taken
	  */
	public void setResult (String Result)
	{
		set_Value (COLUMNNAME_Result, Result);
	}

	/** Get Result.
		@return Result of the action taken
	  */
	public String getResult () 
	{
		return (String)get_Value(COLUMNNAME_Result);
	}

	public I_R_Group getR_Group() throws RuntimeException
    {
		return (I_R_Group)MTable.get(getCtx(), I_R_Group.Table_Name)
			.getPO(getR_Group_ID(), get_TrxName());	}

	/** Set Group.
		@param R_Group_ID 
		Request Group
	  */
	public void setR_Group_ID (int R_Group_ID)
	{
		if (R_Group_ID < 1) 
			set_Value (COLUMNNAME_R_Group_ID, null);
		else 
			set_Value (COLUMNNAME_R_Group_ID, Integer.valueOf(R_Group_ID));
	}

	/** Get Group.
		@return Request Group
	  */
	public int getR_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_MailText getR_MailText() throws RuntimeException
    {
		return (I_R_MailText)MTable.get(getCtx(), I_R_MailText.Table_Name)
			.getPO(getR_MailText_ID(), get_TrxName());	}

	/** Set Mail Template.
		@param R_MailText_ID 
		Text templates for mailings
	  */
	public void setR_MailText_ID (int R_MailText_ID)
	{
		if (R_MailText_ID < 1) 
			set_Value (COLUMNNAME_R_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_R_MailText_ID, Integer.valueOf(R_MailText_ID));
	}

	/** Get Mail Template.
		@return Text templates for mailings
	  */
	public int getR_MailText_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_MailText_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Request.
		@param R_Request_ID 
		Request from a Business Partner or Prospect
	  */
	public void setR_Request_ID (int R_Request_ID)
	{
		if (R_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
	}

	/** Get Request.
		@return Request from a Business Partner or Prospect
	  */
	public int getR_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_Request getR_RequestRelated() throws RuntimeException
    {
		return (I_R_Request)MTable.get(getCtx(), I_R_Request.Table_Name)
			.getPO(getR_RequestRelated_ID(), get_TrxName());	}

	/** Set Related Request.
		@param R_RequestRelated_ID 
		Related Request (Master Issue, ..)
	  */
	public void setR_RequestRelated_ID (int R_RequestRelated_ID)
	{
		if (R_RequestRelated_ID < 1) 
			set_Value (COLUMNNAME_R_RequestRelated_ID, null);
		else 
			set_Value (COLUMNNAME_R_RequestRelated_ID, Integer.valueOf(R_RequestRelated_ID));
	}

	/** Get Related Request.
		@return Related Request (Master Issue, ..)
	  */
	public int getR_RequestRelated_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestRelated_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_RequestType getR_RequestType() throws RuntimeException
    {
		return (I_R_RequestType)MTable.get(getCtx(), I_R_RequestType.Table_Name)
			.getPO(getR_RequestType_ID(), get_TrxName());	}

	/** Set Request Type.
		@param R_RequestType_ID 
		Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public void setR_RequestType_ID (int R_RequestType_ID)
	{
		if (R_RequestType_ID < 1) 
			set_Value (COLUMNNAME_R_RequestType_ID, null);
		else 
			set_Value (COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
	}

	/** Get Request Type.
		@return Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public int getR_RequestType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_Resolution getR_Resolution() throws RuntimeException
    {
		return (I_R_Resolution)MTable.get(getCtx(), I_R_Resolution.Table_Name)
			.getPO(getR_Resolution_ID(), get_TrxName());	}

	/** Set Resolution.
		@param R_Resolution_ID 
		Request Resolution
	  */
	public void setR_Resolution_ID (int R_Resolution_ID)
	{
		if (R_Resolution_ID < 1) 
			set_Value (COLUMNNAME_R_Resolution_ID, null);
		else 
			set_Value (COLUMNNAME_R_Resolution_ID, Integer.valueOf(R_Resolution_ID));
	}

	/** Get Resolution.
		@return Request Resolution
	  */
	public int getR_Resolution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Resolution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_StandardResponse getR_StandardResponse() throws RuntimeException
    {
		return (I_R_StandardResponse)MTable.get(getCtx(), I_R_StandardResponse.Table_Name)
			.getPO(getR_StandardResponse_ID(), get_TrxName());	}

	/** Set Standard Response.
		@param R_StandardResponse_ID 
		Request Standard Response 
	  */
	public void setR_StandardResponse_ID (int R_StandardResponse_ID)
	{
		if (R_StandardResponse_ID < 1) 
			set_Value (COLUMNNAME_R_StandardResponse_ID, null);
		else 
			set_Value (COLUMNNAME_R_StandardResponse_ID, Integer.valueOf(R_StandardResponse_ID));
	}

	/** Get Standard Response.
		@return Request Standard Response 
	  */
	public int getR_StandardResponse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_StandardResponse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_Status getR_Status() throws RuntimeException
    {
		return (I_R_Status)MTable.get(getCtx(), I_R_Status.Table_Name)
			.getPO(getR_Status_ID(), get_TrxName());	}

	/** Set Status.
		@param R_Status_ID 
		Request Status
	  */
	public void setR_Status_ID (int R_Status_ID)
	{
		if (R_Status_ID < 1) 
			set_Value (COLUMNNAME_R_Status_ID, null);
		else 
			set_Value (COLUMNNAME_R_Status_ID, Integer.valueOf(R_Status_ID));
	}

	/** Get Status.
		@return Request Status
	  */
	public int getR_Status_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Status_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_User getSalesRep() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Start Time.
		@param StartTime 
		Time started
	  */
	public void setStartTime (Timestamp StartTime)
	{
		set_Value (COLUMNNAME_StartTime, StartTime);
	}

	/** Get Start Time.
		@return Time started
	  */
	public Timestamp getStartTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartTime);
	}

	/** Set Summary.
		@param Summary 
		Textual summary of this request
	  */
	public void setSummary (String Summary)
	{
		set_Value (COLUMNNAME_Summary, Summary);
	}

	/** Get Summary.
		@return Textual summary of this request
	  */
	public String getSummary () 
	{
		return (String)get_Value(COLUMNNAME_Summary);
	}

	/** TaskStatus AD_Reference_ID=366 */
	public static final int TASKSTATUS_AD_Reference_ID=366;
	/**  0% Not Started = 0 */
	public static final String TASKSTATUS_0NotStarted = "0";
	/** 100% Complete = D */
	public static final String TASKSTATUS_100Complete = "D";
	/**  20% Started = 2 */
	public static final String TASKSTATUS_20Started = "2";
	/**  80% Nearly Done = 8 */
	public static final String TASKSTATUS_80NearlyDone = "8";
	/**  40% Busy = 4 */
	public static final String TASKSTATUS_40Busy = "4";
	/**  60% Good Progress = 6 */
	public static final String TASKSTATUS_60GoodProgress = "6";
	/**  90% Finishing = 9 */
	public static final String TASKSTATUS_90Finishing = "9";
	/**  95% Almost Done = A */
	public static final String TASKSTATUS_95AlmostDone = "A";
	/**  99% Cleaning up = C */
	public static final String TASKSTATUS_99CleaningUp = "C";
	/** Set Task Status.
		@param TaskStatus 
		Status of the Task
	  */
	public void setTaskStatus (String TaskStatus)
	{

		set_Value (COLUMNNAME_TaskStatus, TaskStatus);
	}

	/** Get Task Status.
		@return Status of the Task
	  */
	public String getTaskStatus () 
	{
		return (String)get_Value(COLUMNNAME_TaskStatus);
	}
}