/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/**
 * Generated Model for R_Request
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_R_Request extends org.compiere.model.PO implements I_R_Request, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -679768447L;

	/** Standard Constructor */
	public X_R_Request(Properties ctx, int R_Request_ID, String trxName)
	{
		super(ctx, R_Request_ID, trxName);
		/**
		 * if (R_Request_ID == 0)
		 * {
		 * setConfidentialType (null);
		 * // C
		 * setConfidentialTypeEntry (null);
		 * // C
		 * setDocumentNo (null);
		 * setDueType (null);
		 * // 7
		 * setIsEscalated (false);
		 * setIsInvoiced (false);
		 * setIsSelfService (false);
		 * // N
		 * setPriority (null);
		 * // 5
		 * setProcessed (false);
		 * setRequestAmt (Env.ZERO);
		 * setR_Request_ID (0);
		 * setR_RequestType_ID (0);
		 * setSummary (null);
		 * }
		 */
	}

	/** Load Constructor */
	public X_R_Request(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo(ctx, Table_Name, get_TrxName());
		return poi;
	}

	@Override
	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class);
	}

	@Override
	public void setA_Asset(org.compiere.model.I_A_Asset A_Asset)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class, A_Asset);
	}

	/**
	 * Set Asset.
	 * 
	 * @param A_Asset_ID
	 *            Asset used internally or by customers
	 */
	@Override
	public void setA_Asset_ID(int A_Asset_ID)
	{
		if (A_Asset_ID < 1)
			set_Value(COLUMNNAME_A_Asset_ID, null);
		else
			set_Value(COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/**
	 * Get Asset.
	 * 
	 * @return Asset used internally or by customers
	 */
	@Override
	public int getA_Asset_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	/**
	 * Set Rolle.
	 * 
	 * @param AD_Role_ID
	 *            Responsibility Role
	 */
	@Override
	public void setAD_Role_ID(int AD_Role_ID)
	{
		if (AD_Role_ID < 0)
			set_Value(COLUMNNAME_AD_Role_ID, null);
		else
			set_Value(COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/**
	 * Get Rolle.
	 * 
	 * @return Responsibility Role
	 */
	@Override
	public int getAD_Role_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/**
	 * Set DB-Tabelle.
	 * 
	 * @param AD_Table_ID
	 *            Database Table information
	 */
	@Override
	public void setAD_Table_ID(int AD_Table_ID)
	{
		if (AD_Table_ID < 1)
			set_ValueNoCheck(COLUMNNAME_AD_Table_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/**
	 * Get DB-Tabelle.
	 * 
	 * @return Database Table information
	 */
	@Override
	public int getAD_Table_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/**
	 * Set Ansprechpartner.
	 * 
	 * @param AD_User_ID
	 *            User within the system - Internal or Business Partner Contact
	 */
	@Override
	public void setAD_User_ID(int AD_User_ID)
	{
		if (AD_User_ID < 0)
			set_Value(COLUMNNAME_AD_User_ID, null);
		else
			set_Value(COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/**
	 * Get Ansprechpartner.
	 * 
	 * @return User within the system - Internal or Business Partner Contact
	 */
	@Override
	public int getAD_User_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class);
	}

	@Override
	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity)
	{
		set_ValueFromPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class, C_Activity);
	}

	/**
	 * Set Kostenstelle.
	 * 
	 * @param C_Activity_ID
	 *            Kostenstelle
	 */
	@Override
	public void setC_Activity_ID(int C_Activity_ID)
	{
		if (C_Activity_ID < 1)
			set_Value(COLUMNNAME_C_Activity_ID, null);
		else
			set_Value(COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/**
	 * Get Kostenstelle.
	 * 
	 * @return Kostenstelle
	 */
	@Override
	public int getC_Activity_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/**
	 * Set Business Partner .
	 * 
	 * @param C_BPartner_ID
	 *            Identifies a Business Partner
	 */
	@Override
	public void setC_BPartner_ID(int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_Value(COLUMNNAME_C_BPartner_ID, null);
		else
			set_Value(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/**
	 * Get Business Partner .
	 * 
	 * @return Identifies a Business Partner
	 */
	@Override
	public int getC_BPartner_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	/**
	 * Set Werbemassnahme.
	 * 
	 * @param C_Campaign_ID
	 *            Marketing Campaign
	 */
	@Override
	public void setC_Campaign_ID(int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1)
			set_Value(COLUMNNAME_C_Campaign_ID, null);
		else
			set_Value(COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/**
	 * Get Werbemassnahme.
	 * 
	 * @return Marketing Campaign
	 */
	@Override
	public int getC_Campaign_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/**
	 * Set Rechnung.
	 * 
	 * @param C_Invoice_ID
	 *            Invoice Identifier
	 */
	@Override
	public void setC_Invoice_ID(int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1)
			set_Value(COLUMNNAME_C_Invoice_ID, null);
		else
			set_Value(COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/**
	 * Get Rechnung.
	 * 
	 * @return Invoice Identifier
	 */
	@Override
	public int getC_Invoice_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_InvoiceRequest() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceRequest_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_InvoiceRequest(org.compiere.model.I_C_Invoice C_InvoiceRequest)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceRequest_ID, org.compiere.model.I_C_Invoice.class, C_InvoiceRequest);
	}

	/**
	 * Set Request Invoice.
	 * 
	 * @param C_InvoiceRequest_ID
	 *            The generated invoice for this request
	 */
	@Override
	public void setC_InvoiceRequest_ID(int C_InvoiceRequest_ID)
	{
		if (C_InvoiceRequest_ID < 1)
			set_ValueNoCheck(COLUMNNAME_C_InvoiceRequest_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_C_InvoiceRequest_ID, Integer.valueOf(C_InvoiceRequest_ID));
	}

	/**
	 * Get Request Invoice.
	 * 
	 * @return The generated invoice for this request
	 */
	@Override
	public int getC_InvoiceRequest_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceRequest_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Close Date.
	 * 
	 * @param CloseDate
	 *            Close Date
	 */
	@Override
	public void setCloseDate(java.sql.Timestamp CloseDate)
	{
		set_Value(COLUMNNAME_CloseDate, CloseDate);
	}

	/**
	 * Get Close Date.
	 * 
	 * @return Close Date
	 */
	@Override
	public java.sql.Timestamp getCloseDate()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_CloseDate);
	}

	/**
	 * ConfidentialType AD_Reference_ID=340
	 * Reference name: R_Request Confidential
	 */
	public static final int CONFIDENTIALTYPE_AD_Reference_ID = 340;
	/** Public Information = A */
	public static final String CONFIDENTIALTYPE_PublicInformation = "A";
	/** Partner Confidential = C */
	public static final String CONFIDENTIALTYPE_PartnerConfidential = "C";
	/** Internal = I */
	public static final String CONFIDENTIALTYPE_Internal = "I";
	/** Private Information = P */
	public static final String CONFIDENTIALTYPE_PrivateInformation = "P";

	/**
	 * Set Confidentiality.
	 * 
	 * @param ConfidentialType
	 *            Type of Confidentiality
	 */
	@Override
	public void setConfidentialType(java.lang.String ConfidentialType)
	{

		set_Value(COLUMNNAME_ConfidentialType, ConfidentialType);
	}

	/**
	 * Get Confidentiality.
	 * 
	 * @return Type of Confidentiality
	 */
	@Override
	public java.lang.String getConfidentialType()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ConfidentialType);
	}

	/**
	 * ConfidentialTypeEntry AD_Reference_ID=340
	 * Reference name: R_Request Confidential
	 */
	public static final int CONFIDENTIALTYPEENTRY_AD_Reference_ID = 340;
	/** Public Information = A */
	public static final String CONFIDENTIALTYPEENTRY_PublicInformation = "A";
	/** Partner Confidential = C */
	public static final String CONFIDENTIALTYPEENTRY_PartnerConfidential = "C";
	/** Internal = I */
	public static final String CONFIDENTIALTYPEENTRY_Internal = "I";
	/** Private Information = P */
	public static final String CONFIDENTIALTYPEENTRY_PrivateInformation = "P";

	/**
	 * Set Entry Confidentiality.
	 * 
	 * @param ConfidentialTypeEntry
	 *            Confidentiality of the individual entry
	 */
	@Override
	public void setConfidentialTypeEntry(java.lang.String ConfidentialTypeEntry)
	{

		set_Value(COLUMNNAME_ConfidentialTypeEntry, ConfidentialTypeEntry);
	}

	/**
	 * Get Entry Confidentiality.
	 * 
	 * @return Confidentiality of the individual entry
	 */
	@Override
	public java.lang.String getConfidentialTypeEntry()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ConfidentialTypeEntry);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/**
	 * Set Auftrag.
	 * 
	 * @param C_Order_ID
	 *            Order
	 */
	@Override
	public void setC_Order_ID(int C_Order_ID)
	{
		if (C_Order_ID < 1)
			set_Value(COLUMNNAME_C_Order_ID, null);
		else
			set_Value(COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/**
	 * Get Auftrag.
	 * 
	 * @return Order
	 */
	@Override
	public int getC_Order_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Payment_ID, org.compiere.model.I_C_Payment.class);
	}

	@Override
	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment)
	{
		set_ValueFromPO(COLUMNNAME_C_Payment_ID, org.compiere.model.I_C_Payment.class, C_Payment);
	}

	/**
	 * Set Zahlung.
	 * 
	 * @param C_Payment_ID
	 *            Payment identifier
	 */
	@Override
	public void setC_Payment_ID(int C_Payment_ID)
	{
		if (C_Payment_ID < 1)
			set_Value(COLUMNNAME_C_Payment_ID, null);
		else
			set_Value(COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/**
	 * Get Zahlung.
	 * 
	 * @return Payment identifier
	 */
	@Override
	public int getC_Payment_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_ID, org.compiere.model.I_C_Project.class);
	}

	@Override
	public void setC_Project(org.compiere.model.I_C_Project C_Project)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_ID, org.compiere.model.I_C_Project.class, C_Project);
	}

	/**
	 * Set Project.
	 * 
	 * @param C_Project_ID
	 *            Financial Project
	 */
	@Override
	public void setC_Project_ID(int C_Project_ID)
	{
		if (C_Project_ID < 1)
			set_Value(COLUMNNAME_C_Project_ID, null);
		else
			set_Value(COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/**
	 * Get Project.
	 * 
	 * @return Financial Project
	 */
	@Override
	public int getC_Project_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Complete Plan.
	 * 
	 * @param DateCompletePlan
	 *            Planned Completion Date
	 */
	@Override
	public void setDateCompletePlan(java.sql.Timestamp DateCompletePlan)
	{
		set_Value(COLUMNNAME_DateCompletePlan, DateCompletePlan);
	}

	/**
	 * Get Complete Plan.
	 * 
	 * @return Planned Completion Date
	 */
	@Override
	public java.sql.Timestamp getDateCompletePlan()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateCompletePlan);
	}

	/**
	 * Set Date last action.
	 * 
	 * @param DateLastAction
	 *            Date this request was last acted on
	 */
	@Override
	public void setDateLastAction(java.sql.Timestamp DateLastAction)
	{
		set_ValueNoCheck(COLUMNNAME_DateLastAction, DateLastAction);
	}

	/**
	 * Get Date last action.
	 * 
	 * @return Date this request was last acted on
	 */
	@Override
	public java.sql.Timestamp getDateLastAction()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateLastAction);
	}

	/**
	 * Set Last Alert.
	 * 
	 * @param DateLastAlert
	 *            Date when last alert were sent
	 */
	@Override
	public void setDateLastAlert(java.sql.Timestamp DateLastAlert)
	{
		set_Value(COLUMNNAME_DateLastAlert, DateLastAlert);
	}

	/**
	 * Get Last Alert.
	 * 
	 * @return Date when last alert were sent
	 */
	@Override
	public java.sql.Timestamp getDateLastAlert()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateLastAlert);
	}

	/**
	 * Set Date next action.
	 * 
	 * @param DateNextAction
	 *            Date that this request should be acted on
	 */
	@Override
	public void setDateNextAction(java.sql.Timestamp DateNextAction)
	{
		set_Value(COLUMNNAME_DateNextAction, DateNextAction);
	}

	/**
	 * Get Date next action.
	 * 
	 * @return Date that this request should be acted on
	 */
	@Override
	public java.sql.Timestamp getDateNextAction()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateNextAction);
	}

	/**
	 * Set Start Plan.
	 * 
	 * @param DateStartPlan
	 *            Planned Start Date
	 */
	@Override
	public void setDateStartPlan(java.sql.Timestamp DateStartPlan)
	{
		set_Value(COLUMNNAME_DateStartPlan, DateStartPlan);
	}

	/**
	 * Get Start Plan.
	 * 
	 * @return Planned Start Date
	 */
	@Override
	public java.sql.Timestamp getDateStartPlan()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateStartPlan);
	}

	/**
	 * Set Beleg Nr..
	 * 
	 * @param DocumentNo
	 *            Document sequence number of the document
	 */
	@Override
	public void setDocumentNo(java.lang.String DocumentNo)
	{
		set_Value(COLUMNNAME_DocumentNo, DocumentNo);
	}

	/**
	 * Get Beleg Nr..
	 * 
	 * @return Document sequence number of the document
	 */
	@Override
	public java.lang.String getDocumentNo()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/**
	 * DueType AD_Reference_ID=222
	 * Reference name: R_Request Due Type
	 */
	public static final int DUETYPE_AD_Reference_ID = 222;
	/** Überfällig = 3 */
	public static final String DUETYPE_Ueberfaellig = "3";
	/** Fällig = 5 */
	public static final String DUETYPE_Faellig = "5";
	/** Geplant = 7 */
	public static final String DUETYPE_Geplant = "7";

	/**
	 * Set Due type.
	 * 
	 * @param DueType
	 *            Status of the next action for this Request
	 */
	@Override
	public void setDueType(java.lang.String DueType)
	{

		set_Value(COLUMNNAME_DueType, DueType);
	}

	/**
	 * Get Due type.
	 * 
	 * @return Status of the next action for this Request
	 */
	@Override
	public java.lang.String getDueType()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DueType);
	}

	/**
	 * Set End Time.
	 * 
	 * @param EndTime
	 *            End of the time span
	 */
	@Override
	public void setEndTime(java.sql.Timestamp EndTime)
	{
		set_Value(COLUMNNAME_EndTime, EndTime);
	}

	/**
	 * Get End Time.
	 * 
	 * @return End of the time span
	 */
	@Override
	public java.sql.Timestamp getEndTime()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EndTime);
	}

	/**
	 * Set Escalated.
	 * 
	 * @param IsEscalated
	 *            This request has been escalated
	 */
	@Override
	public void setIsEscalated(boolean IsEscalated)
	{
		set_Value(COLUMNNAME_IsEscalated, Boolean.valueOf(IsEscalated));
	}

	/**
	 * Get Escalated.
	 * 
	 * @return This request has been escalated
	 */
	@Override
	public boolean isEscalated()
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

	/**
	 * Set Invoiced.
	 * 
	 * @param IsInvoiced
	 *            Is this invoiced?
	 */
	@Override
	public void setIsInvoiced(boolean IsInvoiced)
	{
		set_Value(COLUMNNAME_IsInvoiced, Boolean.valueOf(IsInvoiced));
	}

	/**
	 * Get Invoiced.
	 * 
	 * @return Is this invoiced?
	 */
	@Override
	public boolean isInvoiced()
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

	/**
	 * Set Self-Service.
	 * 
	 * @param IsSelfService
	 *            This is a Self-Service entry or this entry can be changed via Self-Service
	 */
	@Override
	public void setIsSelfService(boolean IsSelfService)
	{
		set_ValueNoCheck(COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/**
	 * Get Self-Service.
	 * 
	 * @return This is a Self-Service entry or this entry can be changed via Self-Service
	 */
	@Override
	public boolean isSelfService()
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

	/**
	 * Set Last Result.
	 * 
	 * @param LastResult
	 *            Result of last contact
	 */
	@Override
	public void setLastResult(java.lang.String LastResult)
	{
		set_Value(COLUMNNAME_LastResult, LastResult);
	}

	/**
	 * Get Last Result.
	 * 
	 * @return Result of last contact
	 */
	@Override
	public java.lang.String getLastResult()
	{
		return (java.lang.String)get_Value(COLUMNNAME_LastResult);
	}

	@Override
	public org.compiere.model.I_M_ChangeRequest getM_ChangeRequest() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ChangeRequest_ID, org.compiere.model.I_M_ChangeRequest.class);
	}

	@Override
	public void setM_ChangeRequest(org.compiere.model.I_M_ChangeRequest M_ChangeRequest)
	{
		set_ValueFromPO(COLUMNNAME_M_ChangeRequest_ID, org.compiere.model.I_M_ChangeRequest.class, M_ChangeRequest);
	}

	/**
	 * Set Change Request.
	 * 
	 * @param M_ChangeRequest_ID
	 *            BOM (Engineering) Change Request
	 */
	@Override
	public void setM_ChangeRequest_ID(int M_ChangeRequest_ID)
	{
		if (M_ChangeRequest_ID < 1)
			set_Value(COLUMNNAME_M_ChangeRequest_ID, null);
		else
			set_Value(COLUMNNAME_M_ChangeRequest_ID, Integer.valueOf(M_ChangeRequest_ID));
	}

	/**
	 * Get Change Request.
	 * 
	 * @return BOM (Engineering) Change Request
	 */
	@Override
	public int getM_ChangeRequest_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ChangeRequest_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_ChangeNotice getM_FixChangeNotice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_FixChangeNotice_ID, org.compiere.model.I_M_ChangeNotice.class);
	}

	@Override
	public void setM_FixChangeNotice(org.compiere.model.I_M_ChangeNotice M_FixChangeNotice)
	{
		set_ValueFromPO(COLUMNNAME_M_FixChangeNotice_ID, org.compiere.model.I_M_ChangeNotice.class, M_FixChangeNotice);
	}

	/**
	 * Set Fixed in.
	 * 
	 * @param M_FixChangeNotice_ID
	 *            Fixed in Change Notice
	 */
	@Override
	public void setM_FixChangeNotice_ID(int M_FixChangeNotice_ID)
	{
		if (M_FixChangeNotice_ID < 1)
			set_Value(COLUMNNAME_M_FixChangeNotice_ID, null);
		else
			set_Value(COLUMNNAME_M_FixChangeNotice_ID, Integer.valueOf(M_FixChangeNotice_ID));
	}

	/**
	 * Get Fixed in.
	 * 
	 * @return Fixed in Change Notice
	 */
	@Override
	public int getM_FixChangeNotice_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FixChangeNotice_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/**
	 * Set Lieferung/Wareneingang.
	 * 
	 * @param M_InOut_ID
	 *            Material Shipment Document
	 */
	@Override
	public void setM_InOut_ID(int M_InOut_ID)
	{
		if (M_InOut_ID < 1)
			set_Value(COLUMNNAME_M_InOut_ID, null);
		else
			set_Value(COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/**
	 * Get Lieferung/Wareneingang.
	 * 
	 * @return Material Shipment Document
	 */
	@Override
	public int getM_InOut_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/**
	 * Set Produkt.
	 * 
	 * @param M_Product_ID
	 *            Produkt, Leistung, Artikel
	 */
	@Override
	public void setM_Product_ID(int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_Value(COLUMNNAME_M_Product_ID, null);
		else
			set_Value(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/**
	 * Get Produkt.
	 * 
	 * @return Produkt, Leistung, Artikel
	 */
	@Override
	public int getM_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_ProductSpent() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductSpent_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_ProductSpent(org.compiere.model.I_M_Product M_ProductSpent)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductSpent_ID, org.compiere.model.I_M_Product.class, M_ProductSpent);
	}

	/**
	 * Set Product Used.
	 * 
	 * @param M_ProductSpent_ID
	 *            Product/Resource/Service used in Request
	 */
	@Override
	public void setM_ProductSpent_ID(int M_ProductSpent_ID)
	{
		if (M_ProductSpent_ID < 1)
			set_Value(COLUMNNAME_M_ProductSpent_ID, null);
		else
			set_Value(COLUMNNAME_M_ProductSpent_ID, Integer.valueOf(M_ProductSpent_ID));
	}

	/**
	 * Get Product Used.
	 * 
	 * @return Product/Resource/Service used in Request
	 */
	@Override
	public int getM_ProductSpent_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductSpent_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_RMA getM_RMA() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_RMA_ID, org.compiere.model.I_M_RMA.class);
	}

	@Override
	public void setM_RMA(org.compiere.model.I_M_RMA M_RMA)
	{
		set_ValueFromPO(COLUMNNAME_M_RMA_ID, org.compiere.model.I_M_RMA.class, M_RMA);
	}

	/**
	 * Set RMA.
	 * 
	 * @param M_RMA_ID
	 *            Return Material Authorization
	 */
	@Override
	public void setM_RMA_ID(int M_RMA_ID)
	{
		if (M_RMA_ID < 1)
			set_Value(COLUMNNAME_M_RMA_ID, null);
		else
			set_Value(COLUMNNAME_M_RMA_ID, Integer.valueOf(M_RMA_ID));
	}

	/**
	 * Get RMA.
	 * 
	 * @return Return Material Authorization
	 */
	@Override
	public int getM_RMA_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_RMA_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * NextAction AD_Reference_ID=219
	 * Reference name: R_Request Next Action
	 */
	public static final int NEXTACTION_AD_Reference_ID = 219;
	/** None = N */
	public static final String NEXTACTION_None = "N";
	/** Follow up = F */
	public static final String NEXTACTION_FollowUp = "F";

	/**
	 * Set Next action.
	 * 
	 * @param NextAction
	 *            Next Action to be taken
	 */
	@Override
	public void setNextAction(java.lang.String NextAction)
	{

		set_Value(COLUMNNAME_NextAction, NextAction);
	}

	/**
	 * Get Next action.
	 * 
	 * @return Next Action to be taken
	 */
	@Override
	public java.lang.String getNextAction()
	{
		return (java.lang.String)get_Value(COLUMNNAME_NextAction);
	}

	/**
	 * PerformanceType AD_Reference_ID=540689
	 * Reference name: R_Request.PerformanceType
	 */
	public static final int PERFORMANCETYPE_AD_Reference_ID = 540689;
	/** Liefer Performance = LP */
	public static final String PERFORMANCETYPE_LieferPerformance = "LP";
	/** Quality Performance = QP */
	public static final String PERFORMANCETYPE_QualityPerformance = "QP";

	/**
	 * Set PerformanceType.
	 * 
	 * @param PerformanceType PerformanceType
	 */
	@Override
	public void setPerformanceType(java.lang.String PerformanceType)
	{

		set_Value(COLUMNNAME_PerformanceType, PerformanceType);
	}

	/**
	 * Get PerformanceType.
	 * 
	 * @return PerformanceType
	 */
	@Override
	public java.lang.String getPerformanceType()
	{
		return (java.lang.String)get_Value(COLUMNNAME_PerformanceType);
	}

	/**
	 * Priority AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITY_AD_Reference_ID = 154;
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

	/**
	 * Set Priority.
	 * 
	 * @param Priority
	 *            Indicates if this request is of a high, medium or low priority.
	 */
	@Override
	public void setPriority(java.lang.String Priority)
	{

		set_Value(COLUMNNAME_Priority, Priority);
	}

	/**
	 * Get Priority.
	 * 
	 * @return Indicates if this request is of a high, medium or low priority.
	 */
	@Override
	public java.lang.String getPriority()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Priority);
	}

	/**
	 * PriorityUser AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITYUSER_AD_Reference_ID = 154;
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

	/**
	 * Set User Importance.
	 * 
	 * @param PriorityUser
	 *            Priority of the issue for the User
	 */
	@Override
	public void setPriorityUser(java.lang.String PriorityUser)
	{

		set_Value(COLUMNNAME_PriorityUser, PriorityUser);
	}

	/**
	 * Get User Importance.
	 * 
	 * @return Priority of the issue for the User
	 */
	@Override
	public java.lang.String getPriorityUser()
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriorityUser);
	}

	/**
	 * Set Verarbeitet.
	 * 
	 * @param Processed
	 *            Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	@Override
	public void setProcessed(boolean Processed)
	{
		set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/**
	 * Get Verarbeitet.
	 * 
	 * @return Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	@Override
	public boolean isProcessed()
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

	/**
	 * Set Berechn. Menge.
	 * 
	 * @param QtyInvoiced
	 *            Menge, die bereits in Rechnung gestellt wurde
	 */
	@Override
	public void setQtyInvoiced(java.math.BigDecimal QtyInvoiced)
	{
		set_Value(COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/**
	 * Get Berechn. Menge.
	 * 
	 * @return Menge, die bereits in Rechnung gestellt wurde
	 */
	@Override
	public java.math.BigDecimal getQtyInvoiced()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Quantity Plan.
	 * 
	 * @param QtyPlan
	 *            Planned Quantity
	 */
	@Override
	public void setQtyPlan(java.math.BigDecimal QtyPlan)
	{
		set_Value(COLUMNNAME_QtyPlan, QtyPlan);
	}

	/**
	 * Get Quantity Plan.
	 * 
	 * @return Planned Quantity
	 */
	@Override
	public java.math.BigDecimal getQtyPlan()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPlan);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Quantity Used.
	 * 
	 * @param QtySpent
	 *            Quantity used for this event
	 */
	@Override
	public void setQtySpent(java.math.BigDecimal QtySpent)
	{
		set_Value(COLUMNNAME_QtySpent, QtySpent);
	}

	/**
	 * Get Quantity Used.
	 * 
	 * @return Quantity used for this event
	 */
	@Override
	public java.math.BigDecimal getQtySpent()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySpent);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_R_Category getR_Category() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_Category_ID, org.compiere.model.I_R_Category.class);
	}

	@Override
	public void setR_Category(org.compiere.model.I_R_Category R_Category)
	{
		set_ValueFromPO(COLUMNNAME_R_Category_ID, org.compiere.model.I_R_Category.class, R_Category);
	}

	/**
	 * Set Category.
	 * 
	 * @param R_Category_ID
	 *            Request Category
	 */
	@Override
	public void setR_Category_ID(int R_Category_ID)
	{
		if (R_Category_ID < 1)
			set_Value(COLUMNNAME_R_Category_ID, null);
		else
			set_Value(COLUMNNAME_R_Category_ID, Integer.valueOf(R_Category_ID));
	}

	/**
	 * Get Category.
	 * 
	 * @return Request Category
	 */
	@Override
	public int getR_Category_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Category_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Datensatz-ID.
	 * 
	 * @param Record_ID
	 *            Direct internal record ID
	 */
	@Override
	public void setRecord_ID(int Record_ID)
	{
		if (Record_ID < 0)
			set_ValueNoCheck(COLUMNNAME_Record_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/**
	 * Get Datensatz-ID.
	 * 
	 * @return Direct internal record ID
	 */
	@Override
	public int getRecord_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Request Amount.
	 * 
	 * @param RequestAmt
	 *            Amount associated with this request
	 */
	@Override
	public void setRequestAmt(java.math.BigDecimal RequestAmt)
	{
		set_Value(COLUMNNAME_RequestAmt, RequestAmt);
	}

	/**
	 * Get Request Amount.
	 * 
	 * @return Amount associated with this request
	 */
	@Override
	public java.math.BigDecimal getRequestAmt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RequestAmt);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Request_includedTab.
	 * 
	 * @param Request_includedTab Request_includedTab
	 */
	@Override
	public void setRequest_includedTab(java.lang.String Request_includedTab)
	{
		set_Value(COLUMNNAME_Request_includedTab, Request_includedTab);
	}

	/**
	 * Get Request_includedTab.
	 * 
	 * @return Request_includedTab
	 */
	@Override
	public java.lang.String getRequest_includedTab()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Request_includedTab);
	}

	/**
	 * Set Ergebnis.
	 * 
	 * @param Result
	 *            Result of the action taken
	 */
	@Override
	public void setResult(java.lang.String Result)
	{
		set_Value(COLUMNNAME_Result, Result);
	}

	/**
	 * Get Ergebnis.
	 * 
	 * @return Result of the action taken
	 */
	@Override
	public java.lang.String getResult()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Result);
	}

	@Override
	public org.compiere.model.I_R_Group getR_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_Group_ID, org.compiere.model.I_R_Group.class);
	}

	@Override
	public void setR_Group(org.compiere.model.I_R_Group R_Group)
	{
		set_ValueFromPO(COLUMNNAME_R_Group_ID, org.compiere.model.I_R_Group.class, R_Group);
	}

	/**
	 * Set Group.
	 * 
	 * @param R_Group_ID
	 *            Request Group
	 */
	@Override
	public void setR_Group_ID(int R_Group_ID)
	{
		if (R_Group_ID < 1)
			set_Value(COLUMNNAME_R_Group_ID, null);
		else
			set_Value(COLUMNNAME_R_Group_ID, Integer.valueOf(R_Group_ID));
	}

	/**
	 * Get Group.
	 * 
	 * @return Request Group
	 */
	@Override
	public int getR_Group_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Group_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_MailText getR_MailText() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setR_MailText(org.compiere.model.I_R_MailText R_MailText)
	{
		set_ValueFromPO(COLUMNNAME_R_MailText_ID, org.compiere.model.I_R_MailText.class, R_MailText);
	}

	/**
	 * Set Mail Template.
	 * 
	 * @param R_MailText_ID
	 *            Text templates for mailings
	 */
	@Override
	public void setR_MailText_ID(int R_MailText_ID)
	{
		if (R_MailText_ID < 1)
			set_Value(COLUMNNAME_R_MailText_ID, null);
		else
			set_Value(COLUMNNAME_R_MailText_ID, Integer.valueOf(R_MailText_ID));
	}

	/**
	 * Get Mail Template.
	 * 
	 * @return Text templates for mailings
	 */
	@Override
	public int getR_MailText_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_MailText_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Request.
	 * 
	 * @param R_Request_ID
	 *            Request from a Business Partner or Prospect
	 */
	@Override
	public void setR_Request_ID(int R_Request_ID)
	{
		if (R_Request_ID < 1)
			set_ValueNoCheck(COLUMNNAME_R_Request_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
	}

	/**
	 * Get Request.
	 * 
	 * @return Request from a Business Partner or Prospect
	 */
	@Override
	public int getR_Request_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Request_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_Request getR_RequestRelated() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_RequestRelated_ID, org.compiere.model.I_R_Request.class);
	}

	@Override
	public void setR_RequestRelated(org.compiere.model.I_R_Request R_RequestRelated)
	{
		set_ValueFromPO(COLUMNNAME_R_RequestRelated_ID, org.compiere.model.I_R_Request.class, R_RequestRelated);
	}

	/**
	 * Set Related Request.
	 * 
	 * @param R_RequestRelated_ID
	 *            Related Request (Master Issue, ..)
	 */
	@Override
	public void setR_RequestRelated_ID(int R_RequestRelated_ID)
	{
		if (R_RequestRelated_ID < 1)
			set_Value(COLUMNNAME_R_RequestRelated_ID, null);
		else
			set_Value(COLUMNNAME_R_RequestRelated_ID, Integer.valueOf(R_RequestRelated_ID));
	}

	/**
	 * Get Related Request.
	 * 
	 * @return Related Request (Master Issue, ..)
	 */
	@Override
	public int getR_RequestRelated_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestRelated_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_RequestType getR_RequestType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_RequestType_ID, org.compiere.model.I_R_RequestType.class);
	}

	@Override
	public void setR_RequestType(org.compiere.model.I_R_RequestType R_RequestType)
	{
		set_ValueFromPO(COLUMNNAME_R_RequestType_ID, org.compiere.model.I_R_RequestType.class, R_RequestType);
	}

	/**
	 * Set Request Type.
	 * 
	 * @param R_RequestType_ID
	 *            Type of request (e.g. Inquiry, Complaint, ..)
	 */
	@Override
	public void setR_RequestType_ID(int R_RequestType_ID)
	{
		if (R_RequestType_ID < 1)
			set_Value(COLUMNNAME_R_RequestType_ID, null);
		else
			set_Value(COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
	}

	/**
	 * Get Request Type.
	 * 
	 * @return Type of request (e.g. Inquiry, Complaint, ..)
	 */
	@Override
	public int getR_RequestType_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestType_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Request Type Interner Name.
	 * 
	 * @param R_RequestType_InternalName Request Type Interner Name
	 */
	@Override
	public void setR_RequestType_InternalName(java.lang.String R_RequestType_InternalName)
	{
		set_ValueNoCheck(COLUMNNAME_R_RequestType_InternalName, R_RequestType_InternalName);
	}

	/**
	 * Get Request Type Interner Name.
	 * 
	 * @return Request Type Interner Name
	 */
	@Override
	public java.lang.String getR_RequestType_InternalName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_RequestType_InternalName);
	}

	@Override
	public org.compiere.model.I_R_Resolution getR_Resolution() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_Resolution_ID, org.compiere.model.I_R_Resolution.class);
	}

	@Override
	public void setR_Resolution(org.compiere.model.I_R_Resolution R_Resolution)
	{
		set_ValueFromPO(COLUMNNAME_R_Resolution_ID, org.compiere.model.I_R_Resolution.class, R_Resolution);
	}

	/**
	 * Set Resolution.
	 * 
	 * @param R_Resolution_ID
	 *            Request Resolution
	 */
	@Override
	public void setR_Resolution_ID(int R_Resolution_ID)
	{
		if (R_Resolution_ID < 1)
			set_Value(COLUMNNAME_R_Resolution_ID, null);
		else
			set_Value(COLUMNNAME_R_Resolution_ID, Integer.valueOf(R_Resolution_ID));
	}

	/**
	 * Get Resolution.
	 * 
	 * @return Request Resolution
	 */
	@Override
	public int getR_Resolution_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Resolution_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_StandardResponse getR_StandardResponse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_StandardResponse_ID, org.compiere.model.I_R_StandardResponse.class);
	}

	@Override
	public void setR_StandardResponse(org.compiere.model.I_R_StandardResponse R_StandardResponse)
	{
		set_ValueFromPO(COLUMNNAME_R_StandardResponse_ID, org.compiere.model.I_R_StandardResponse.class, R_StandardResponse);
	}

	/**
	 * Set Standard Response.
	 * 
	 * @param R_StandardResponse_ID
	 *            Request Standard Response
	 */
	@Override
	public void setR_StandardResponse_ID(int R_StandardResponse_ID)
	{
		if (R_StandardResponse_ID < 1)
			set_Value(COLUMNNAME_R_StandardResponse_ID, null);
		else
			set_Value(COLUMNNAME_R_StandardResponse_ID, Integer.valueOf(R_StandardResponse_ID));
	}

	/**
	 * Get Standard Response.
	 * 
	 * @return Request Standard Response
	 */
	@Override
	public int getR_StandardResponse_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_StandardResponse_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_Status getR_Status() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_Status_ID, org.compiere.model.I_R_Status.class);
	}

	@Override
	public void setR_Status(org.compiere.model.I_R_Status R_Status)
	{
		set_ValueFromPO(COLUMNNAME_R_Status_ID, org.compiere.model.I_R_Status.class, R_Status);
	}

	/**
	 * Set Status.
	 * 
	 * @param R_Status_ID
	 *            Request Status
	 */
	@Override
	public void setR_Status_ID(int R_Status_ID)
	{
		if (R_Status_ID < 1)
			set_Value(COLUMNNAME_R_Status_ID, null);
		else
			set_Value(COLUMNNAME_R_Status_ID, Integer.valueOf(R_Status_ID));
	}

	/**
	 * Get Status.
	 * 
	 * @return Request Status
	 */
	@Override
	public int getR_Status_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Status_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setSalesRep(org.compiere.model.I_AD_User SalesRep)
	{
		set_ValueFromPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class, SalesRep);
	}

	/**
	 * Set Sales Representative.
	 * 
	 * @param SalesRep_ID
	 *            Sales Representative or Company Agent
	 */
	@Override
	public void setSalesRep_ID(int SalesRep_ID)
	{
		if (SalesRep_ID < 1)
			set_Value(COLUMNNAME_SalesRep_ID, null);
		else
			set_Value(COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/**
	 * Get Sales Representative.
	 * 
	 * @return Sales Representative or Company Agent
	 */
	@Override
	public int getSalesRep_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Anfangsdatum.
	 * 
	 * @param StartDate
	 *            First effective day (inclusive)
	 */
	@Override
	public void setStartDate(java.sql.Timestamp StartDate)
	{
		set_Value(COLUMNNAME_StartDate, StartDate);
	}

	/**
	 * Get Anfangsdatum.
	 * 
	 * @return First effective day (inclusive)
	 */
	@Override
	public java.sql.Timestamp getStartDate()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/**
	 * Set Start Time.
	 * 
	 * @param StartTime
	 *            Time started
	 */
	@Override
	public void setStartTime(java.sql.Timestamp StartTime)
	{
		set_Value(COLUMNNAME_StartTime, StartTime);
	}

	/**
	 * Get Start Time.
	 * 
	 * @return Time started
	 */
	@Override
	public java.sql.Timestamp getStartTime()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_StartTime);
	}

	/**
	 * Set Summary.
	 * 
	 * @param Summary
	 *            Textual summary of this request
	 */
	@Override
	public void setSummary(java.lang.String Summary)
	{
		set_Value(COLUMNNAME_Summary, Summary);
	}

	/**
	 * Get Summary.
	 * 
	 * @return Textual summary of this request
	 */
	@Override
	public java.lang.String getSummary()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Summary);
	}

	/**
	 * TaskStatus AD_Reference_ID=366
	 * Reference name: R_Request TaskStatus
	 */
	public static final int TASKSTATUS_AD_Reference_ID = 366;
	/** 0% Not Started = 0 */
	public static final String TASKSTATUS_0NotStarted = "0";
	/** 100% Complete = D */
	public static final String TASKSTATUS_100Complete = "D";
	/** 20% Started = 2 */
	public static final String TASKSTATUS_20Started = "2";
	/** 80% Nearly Done = 8 */
	public static final String TASKSTATUS_80NearlyDone = "8";
	/** 40% Busy = 4 */
	public static final String TASKSTATUS_40Busy = "4";
	/** 60% Good Progress = 6 */
	public static final String TASKSTATUS_60GoodProgress = "6";
	/** 90% Finishing = 9 */
	public static final String TASKSTATUS_90Finishing = "9";
	/** 95% Almost Done = A */
	public static final String TASKSTATUS_95AlmostDone = "A";
	/** 99% Cleaning up = C */
	public static final String TASKSTATUS_99CleaningUp = "C";

	/**
	 * Set Task Status.
	 * 
	 * @param TaskStatus
	 *            Status of the Task
	 */
	@Override
	public void setTaskStatus(java.lang.String TaskStatus)
	{

		set_Value(COLUMNNAME_TaskStatus, TaskStatus);
	}

	/**
	 * Get Task Status.
	 * 
	 * @return Status of the Task
	 */
	@Override
	public java.lang.String getTaskStatus()
	{
		return (java.lang.String)get_Value(COLUMNNAME_TaskStatus);
	}
}
