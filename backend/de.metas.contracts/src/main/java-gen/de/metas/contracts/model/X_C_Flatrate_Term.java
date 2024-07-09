// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_Term
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Flatrate_Term extends org.compiere.model.PO implements I_C_Flatrate_Term, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -792729881L;

    /** Standard Constructor */
    public X_C_Flatrate_Term (final Properties ctx, final int C_Flatrate_Term_ID, @Nullable final String trxName)
    {
      super (ctx, C_Flatrate_Term_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Flatrate_Term (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_PInstance getAD_PInstance_EndOfTerm()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_EndOfTerm_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance_EndOfTerm(final org.compiere.model.I_AD_PInstance AD_PInstance_EndOfTerm)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_EndOfTerm_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance_EndOfTerm);
	}

	@Override
	public void setAD_PInstance_EndOfTerm_ID (final int AD_PInstance_EndOfTerm_ID)
	{
		if (AD_PInstance_EndOfTerm_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_EndOfTerm_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_EndOfTerm_ID, AD_PInstance_EndOfTerm_ID);
	}

	@Override
	public int getAD_PInstance_EndOfTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_EndOfTerm_ID);
	}

	@Override
	public void setAD_User_InCharge_ID (final int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, AD_User_InCharge_ID);
	}

	@Override
	public int getAD_User_InCharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_InCharge_ID);
	}

	@Override
	public void setBill_BPartner_ID (final int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Bill_BPartner_ID);
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public void setBill_Location_ID (final int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Bill_Location_ID);
	}

	@Override
	public int getBill_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getBill_Location_Value()
	{
		return get_ValueAsPO(COLUMNNAME_Bill_Location_Value_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setBill_Location_Value(final org.compiere.model.I_C_Location Bill_Location_Value)
	{
		set_ValueFromPO(COLUMNNAME_Bill_Location_Value_ID, org.compiere.model.I_C_Location.class, Bill_Location_Value);
	}

	@Override
	public void setBill_Location_Value_ID (final int Bill_Location_Value_ID)
	{
		if (Bill_Location_Value_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_Value_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_Value_ID, Bill_Location_Value_ID);
	}

	@Override
	public int getBill_Location_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_Value_ID);
	}

	@Override
	public void setBill_User_ID (final int Bill_User_ID)
	{
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID, Bill_User_ID);
	}

	@Override
	public int getBill_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_User_ID);
	}

	@Override
	public void setC_Async_Batch_ID (final int C_Async_Batch_ID)
	{
		if (C_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_ID, C_Async_Batch_ID);
	}

	@Override
	public int getC_Async_Batch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	}

	@Override
	public void setC_Flatrate_Conditions(final de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class, C_Flatrate_Conditions);
	}

	@Override
	public void setC_Flatrate_Conditions_ID (final int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, C_Flatrate_Conditions_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Data getC_Flatrate_Data()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Data_ID, de.metas.contracts.model.I_C_Flatrate_Data.class);
	}

	@Override
	public void setC_Flatrate_Data(final de.metas.contracts.model.I_C_Flatrate_Data C_Flatrate_Data)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Data_ID, de.metas.contracts.model.I_C_Flatrate_Data.class, C_Flatrate_Data);
	}

	@Override
	public void setC_Flatrate_Data_ID (final int C_Flatrate_Data_ID)
	{
		if (C_Flatrate_Data_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Data_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Data_ID, C_Flatrate_Data_ID);
	}

	@Override
	public int getC_Flatrate_Data_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Data_ID);
	}

	@Override
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term_Master()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_Master_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term_Master(final de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term_Master)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_Master_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, C_Flatrate_Term_Master);
	}

	@Override
	public void setC_Flatrate_Term_Master_ID (final int C_Flatrate_Term_Master_ID)
	{
		if (C_Flatrate_Term_Master_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_Master_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_Master_ID, C_Flatrate_Term_Master_ID);
	}

	@Override
	public int getC_Flatrate_Term_Master_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_Master_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Term getC_FlatrateTerm_Next()
	{
		return get_ValueAsPO(COLUMNNAME_C_FlatrateTerm_Next_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_FlatrateTerm_Next(final de.metas.contracts.model.I_C_Flatrate_Term C_FlatrateTerm_Next)
	{
		set_ValueFromPO(COLUMNNAME_C_FlatrateTerm_Next_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, C_FlatrateTerm_Next);
	}

	@Override
	public void setC_FlatrateTerm_Next_ID (final int C_FlatrateTerm_Next_ID)
	{
		if (C_FlatrateTerm_Next_ID < 1) 
			set_Value (COLUMNNAME_C_FlatrateTerm_Next_ID, null);
		else 
			set_Value (COLUMNNAME_C_FlatrateTerm_Next_ID, C_FlatrateTerm_Next_ID);
	}

	@Override
	public int getC_FlatrateTerm_Next_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_FlatrateTerm_Next_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Transition getC_Flatrate_Transition()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.contracts.model.I_C_Flatrate_Transition.class);
	}

	@Override
	public void setC_Flatrate_Transition(final de.metas.contracts.model.I_C_Flatrate_Transition C_Flatrate_Transition)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.contracts.model.I_C_Flatrate_Transition.class, C_Flatrate_Transition);
	}

	@Override
	public void setC_Flatrate_Transition_ID (final int C_Flatrate_Transition_ID)
	{
		throw new IllegalArgumentException ("C_Flatrate_Transition_ID is virtual column");	}

	@Override
	public int getC_Flatrate_Transition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Transition_ID);
	}

	@Override
	public void setChangeOrCancelTerm (final @Nullable java.lang.String ChangeOrCancelTerm)
	{
		set_Value (COLUMNNAME_ChangeOrCancelTerm, ChangeOrCancelTerm);
	}

	@Override
	public java.lang.String getChangeOrCancelTerm() 
	{
		return get_ValueAsString(COLUMNNAME_ChangeOrCancelTerm);
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Harvesting_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Harvesting_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Harvesting_Calendar(final org.compiere.model.I_C_Calendar C_Harvesting_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Harvesting_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Harvesting_Calendar);
	}

	@Override
	public void setC_Harvesting_Calendar_ID (final int C_Harvesting_Calendar_ID)
	{
		if (C_Harvesting_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Harvesting_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Harvesting_Calendar_ID, C_Harvesting_Calendar_ID);
	}

	@Override
	public int getC_Harvesting_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Harvesting_Calendar_ID);
	}

	/** 
	 * ContractStatus AD_Reference_ID=540000
	 * Reference name: SubscriptionStatus
	 */
	public static final int CONTRACTSTATUS_AD_Reference_ID=540000;
	/** Running = Ru */
	public static final String CONTRACTSTATUS_Running = "Ru";
	/** DeliveryPause = Pa */
	public static final String CONTRACTSTATUS_DeliveryPause = "Pa";
	/** Quit = Qu */
	public static final String CONTRACTSTATUS_Quit = "Qu";
	/** Info = In */
	public static final String CONTRACTSTATUS_Info = "In";
	/** Waiting = Wa */
	public static final String CONTRACTSTATUS_Waiting = "Wa";
	/** EndingContract = Ec */
	public static final String CONTRACTSTATUS_EndingContract = "Ec";
	/** Voided = Vo */
	public static final String CONTRACTSTATUS_Voided = "Vo";
	@Override
	public void setContractStatus (final @Nullable java.lang.String ContractStatus)
	{
		set_Value (COLUMNNAME_ContractStatus, ContractStatus);
	}

	@Override
	public java.lang.String getContractStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ContractStatus);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine_TermChange()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_TermChange_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine_TermChange(final org.compiere.model.I_C_OrderLine C_OrderLine_TermChange)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_TermChange_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine_TermChange);
	}

	@Override
	public void setC_OrderLine_TermChange_ID (final int C_OrderLine_TermChange_ID)
	{
		if (C_OrderLine_TermChange_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_TermChange_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_TermChange_ID, C_OrderLine_TermChange_ID);
	}

	@Override
	public int getC_OrderLine_TermChange_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_TermChange_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine_Term()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_Term_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine_Term(final org.compiere.model.I_C_OrderLine C_OrderLine_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_Term_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine_Term);
	}

	@Override
	public void setC_OrderLine_Term_ID (final int C_OrderLine_Term_ID)
	{
		if (C_OrderLine_Term_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_Term_ID, C_OrderLine_Term_ID);
	}

	@Override
	public int getC_OrderLine_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_Term_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order_TermChange()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_TermChange_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order_TermChange(final org.compiere.model.I_C_Order C_Order_TermChange)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_TermChange_ID, org.compiere.model.I_C_Order.class, C_Order_TermChange);
	}

	@Override
	public void setC_Order_TermChange_ID (final int C_Order_TermChange_ID)
	{
		throw new IllegalArgumentException ("C_Order_TermChange_ID is virtual column");	}

	@Override
	public int getC_Order_TermChange_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_TermChange_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order_Term()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_Term_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order_Term(final org.compiere.model.I_C_Order C_Order_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_Term_ID, org.compiere.model.I_C_Order.class, C_Order_Term);
	}

	@Override
	public void setC_Order_Term_ID (final int C_Order_Term_ID)
	{
		if (C_Order_Term_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_Term_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_Term_ID, C_Order_Term_ID);
	}

	@Override
	public int getC_Order_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_Term_ID);
	}

	@Override
	public void setC_TaxCategory_ID (final int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, C_TaxCategory_ID);
	}

	@Override
	public int getC_TaxCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxCategory_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDateContracted (final @Nullable java.sql.Timestamp DateContracted)
	{
		set_Value (COLUMNNAME_DateContracted, DateContracted);
	}

	@Override
	public java.sql.Timestamp getDateContracted() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateContracted);
	}

	/** 
	 * DeliveryRule AD_Reference_ID=151
	 * Reference name: C_Order DeliveryRule
	 */
	public static final int DELIVERYRULE_AD_Reference_ID=151;
	/** AfterReceipt = R */
	public static final String DELIVERYRULE_AfterReceipt = "R";
	/** Availability = A */
	public static final String DELIVERYRULE_Availability = "A";
	/** CompleteLine = L */
	public static final String DELIVERYRULE_CompleteLine = "L";
	/** CompleteOrder = O */
	public static final String DELIVERYRULE_CompleteOrder = "O";
	/** Force = F */
	public static final String DELIVERYRULE_Force = "F";
	/** Manual = M */
	public static final String DELIVERYRULE_Manual = "M";
	/** MitNaechsterAbolieferung = S */
	public static final String DELIVERYRULE_MitNaechsterAbolieferung = "S";
	@Override
	public void setDeliveryRule (final @Nullable java.lang.String DeliveryRule)
	{
		set_Value (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	@Override
	public java.lang.String getDeliveryRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryRule);
	}

	/** 
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	/** Normalpost = NP */
	public static final String DELIVERYVIARULE_Normalpost = "NP";
	/** Luftpost = LU */
	public static final String DELIVERYVIARULE_Luftpost = "LU";
	@Override
	public void setDeliveryViaRule (final @Nullable java.lang.String DeliveryViaRule)
	{
		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	@Override
	public java.lang.String getDeliveryViaRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryViaRule);
	}

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	@Override
	public void setDocAction (final java.lang.String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public java.lang.String getDocAction() 
	{
		return get_ValueAsString(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setDocStatus (final java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setDropShip_BPartner_ID (final int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, DropShip_BPartner_ID);
	}

	@Override
	public int getDropShip_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_BPartner_ID);
	}

	@Override
	public void setDropShip_Location_ID (final int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, DropShip_Location_ID);
	}

	@Override
	public int getDropShip_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getDropShip_Location_Value()
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_Location_Value_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setDropShip_Location_Value(final org.compiere.model.I_C_Location DropShip_Location_Value)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_Location_Value_ID, org.compiere.model.I_C_Location.class, DropShip_Location_Value);
	}

	@Override
	public void setDropShip_Location_Value_ID (final int DropShip_Location_Value_ID)
	{
		if (DropShip_Location_Value_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_Value_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_Value_ID, DropShip_Location_Value_ID);
	}

	@Override
	public int getDropShip_Location_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Location_Value_ID);
	}

	@Override
	public void setDropShip_User_ID (final int DropShip_User_ID)
	{
		if (DropShip_User_ID < 1) 
			set_Value (COLUMNNAME_DropShip_User_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_User_ID, DropShip_User_ID);
	}

	@Override
	public int getDropShip_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_User_ID);
	}

	@Override
	public void setEndDate (final @Nullable java.sql.Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	@Override
	public java.sql.Timestamp getEndDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EndDate);
	}

	@Override
	public void setExtendTerm (final @Nullable java.lang.String ExtendTerm)
	{
		set_Value (COLUMNNAME_ExtendTerm, ExtendTerm);
	}

	@Override
	public java.lang.String getExtendTerm() 
	{
		return get_ValueAsString(COLUMNNAME_ExtendTerm);
	}

	@Override
	public org.compiere.model.I_C_Year getHarvesting_Year()
	{
		return get_ValueAsPO(COLUMNNAME_Harvesting_Year_ID, org.compiere.model.I_C_Year.class);
	}

	@Override
	public void setHarvesting_Year(final org.compiere.model.I_C_Year Harvesting_Year)
	{
		set_ValueFromPO(COLUMNNAME_Harvesting_Year_ID, org.compiere.model.I_C_Year.class, Harvesting_Year);
	}

	@Override
	public void setHarvesting_Year_ID (final int Harvesting_Year_ID)
	{
		if (Harvesting_Year_ID < 1) 
			set_Value (COLUMNNAME_Harvesting_Year_ID, null);
		else 
			set_Value (COLUMNNAME_Harvesting_Year_ID, Harvesting_Year_ID);
	}

	@Override
	public int getHarvesting_Year_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Harvesting_Year_ID);
	}

	@Override
	public void setIsAutoRenew (final boolean IsAutoRenew)
	{
		set_Value (COLUMNNAME_IsAutoRenew, IsAutoRenew);
	}

	@Override
	public boolean isAutoRenew() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoRenew);
	}

	@Override
	public void setIsCloseInvoiceCandidate (final boolean IsCloseInvoiceCandidate)
	{
		set_Value (COLUMNNAME_IsCloseInvoiceCandidate, IsCloseInvoiceCandidate);
	}

	@Override
	public boolean isCloseInvoiceCandidate() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCloseInvoiceCandidate);
	}

	@Override
	public void setIsClosingWithActualSum (final boolean IsClosingWithActualSum)
	{
		throw new IllegalArgumentException ("IsClosingWithActualSum is virtual column");	}

	@Override
	public boolean isClosingWithActualSum() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosingWithActualSum);
	}

	@Override
	public void setIsClosingWithCorrectionSum (final boolean IsClosingWithCorrectionSum)
	{
		throw new IllegalArgumentException ("IsClosingWithCorrectionSum is virtual column");	}

	@Override
	public boolean isClosingWithCorrectionSum() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosingWithCorrectionSum);
	}

	@Override
	public void setIsReadyForDefinitiveInvoice (final boolean IsReadyForDefinitiveInvoice)
	{
		set_Value (COLUMNNAME_IsReadyForDefinitiveInvoice, IsReadyForDefinitiveInvoice);
	}

	@Override
	public boolean isReadyForDefinitiveInvoice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReadyForDefinitiveInvoice);
	}

	@Override
	public void setIsSimulation (final boolean IsSimulation)
	{
		set_Value (COLUMNNAME_IsSimulation, IsSimulation);
	}

	@Override
	public boolean isSimulation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSimulation);
	}

	@Override
	public void setIsTaxIncluded (final boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, IsTaxIncluded);
	}

	@Override
	public boolean isTaxIncluded() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxIncluded);
	}

	@Override
	public void setMasterDocumentNo (final @Nullable java.lang.String MasterDocumentNo)
	{
		set_Value (COLUMNNAME_MasterDocumentNo, MasterDocumentNo);
	}

	@Override
	public java.lang.String getMasterDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_MasterDocumentNo);
	}

	@Override
	public void setMasterEndDate (final @Nullable java.sql.Timestamp MasterEndDate)
	{
		set_Value (COLUMNNAME_MasterEndDate, MasterEndDate);
	}

	@Override
	public java.sql.Timestamp getMasterEndDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MasterEndDate);
	}

	@Override
	public void setMasterStartDate (final @Nullable java.sql.Timestamp MasterStartDate)
	{
		set_Value (COLUMNNAME_MasterStartDate, MasterStartDate);
	}

	@Override
	public java.sql.Timestamp getMasterStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MasterStartDate);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Term getModular_Flatrate_Term()
	{
		return get_ValueAsPO(COLUMNNAME_Modular_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setModular_Flatrate_Term(final de.metas.contracts.model.I_C_Flatrate_Term Modular_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_Modular_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, Modular_Flatrate_Term);
	}

	@Override
	public void setModular_Flatrate_Term_ID (final int Modular_Flatrate_Term_ID)
	{
		if (Modular_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_Modular_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_Modular_Flatrate_Term_ID, Modular_Flatrate_Term_ID);
	}

	@Override
	public int getModular_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Modular_Flatrate_Term_ID);
	}

	@Override
	public void setM_PricingSystem_ID (final int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, M_PricingSystem_ID);
	}

	@Override
	public int getM_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PricingSystem_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	@Override
	public void setNoticeDate (final @Nullable java.sql.Timestamp NoticeDate)
	{
		set_Value (COLUMNNAME_NoticeDate, NoticeDate);
	}

	@Override
	public java.sql.Timestamp getNoticeDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_NoticeDate);
	}

	@Override
	public void setPlannedQtyPerUnit (final BigDecimal PlannedQtyPerUnit)
	{
		set_Value (COLUMNNAME_PlannedQtyPerUnit, PlannedQtyPerUnit);
	}

	@Override
	public BigDecimal getPlannedQtyPerUnit() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedQtyPerUnit);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPrepareClosing (final @Nullable java.lang.String PrepareClosing)
	{
		set_Value (COLUMNNAME_PrepareClosing, PrepareClosing);
	}

	@Override
	public java.lang.String getPrepareClosing() 
	{
		return get_ValueAsString(COLUMNNAME_PrepareClosing);
	}

	@Override
	public void setPriceActual (final @Nullable BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	@Override
	public BigDecimal getPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setStartDate (final java.sql.Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	@Override
	public java.sql.Timestamp getStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_StartDate);
	}

	@Override
	public void setTerminationDate (final @Nullable java.sql.Timestamp TerminationDate)
	{
		set_Value (COLUMNNAME_TerminationDate, TerminationDate);
	}

	@Override
	public java.sql.Timestamp getTerminationDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_TerminationDate);
	}

	@Override
	public void setTerminationMemo (final @Nullable java.lang.String TerminationMemo)
	{
		set_Value (COLUMNNAME_TerminationMemo, TerminationMemo);
	}

	@Override
	public java.lang.String getTerminationMemo() 
	{
		return get_ValueAsString(COLUMNNAME_TerminationMemo);
	}

	/** 
	 * TerminationReason AD_Reference_ID=540761
	 * Reference name: Contracts_TerminationaReason
	 */
	public static final int TERMINATIONREASON_AD_Reference_ID=540761;
	/** HighAge = Hi */
	public static final String TERMINATIONREASON_HighAge = "Hi";
	/** DidNotOrder = Dno */
	public static final String TERMINATIONREASON_DidNotOrder = "Dno";
	/** General = Ge */
	public static final String TERMINATIONREASON_General = "Ge";
	/** Religion = Rel */
	public static final String TERMINATIONREASON_Religion = "Rel";
	/** NoTime = Nt */
	public static final String TERMINATIONREASON_NoTime = "Nt";
	/** TooMuchPapers = Tmp */
	public static final String TERMINATIONREASON_TooMuchPapers = "Tmp";
	/** FinancialReasons = Fr */
	public static final String TERMINATIONREASON_FinancialReasons = "Fr";
	/** TooModern = Tm */
	public static final String TERMINATIONREASON_TooModern = "Tm";
	/** NoInterest = Ni */
	public static final String TERMINATIONREASON_NoInterest = "Ni";
	/** NewSubscriptionType = Nst */
	public static final String TERMINATIONREASON_NewSubscriptionType = "Nst";
	/** GiftNotRenewed = Gnr */
	public static final String TERMINATIONREASON_GiftNotRenewed = "Gnr";
	/** StayingForeign = Sf */
	public static final String TERMINATIONREASON_StayingForeign = "Sf";
	/** Died = Di */
	public static final String TERMINATIONREASON_Died = "Di";
	/** Sick = Si */
	public static final String TERMINATIONREASON_Sick = "Si";
	/** DoubleReader = Dr */
	public static final String TERMINATIONREASON_DoubleReader = "Dr";
	/** SubscriptionSwitch = Ss */
	public static final String TERMINATIONREASON_SubscriptionSwitch = "Ss";
	/** LimitedDelivery = Ld */
	public static final String TERMINATIONREASON_LimitedDelivery = "Ld";
	/** PrivateReasons = Pr */
	public static final String TERMINATIONREASON_PrivateReasons = "Pr";
	/** CanNotRead = Cnr */
	public static final String TERMINATIONREASON_CanNotRead = "Cnr";
	/** NotReachable = Nr */
	public static final String TERMINATIONREASON_NotReachable = "Nr";
	/** IncorrectlyRecorded = Err */
	public static final String TERMINATIONREASON_IncorrectlyRecorded = "Err";
	/** OrgChange = Os */
	public static final String TERMINATIONREASON_OrgChange = "Os";
	@Override
	public void setTerminationReason (final @Nullable java.lang.String TerminationReason)
	{
		set_Value (COLUMNNAME_TerminationReason, TerminationReason);
	}

	@Override
	public java.lang.String getTerminationReason() 
	{
		return get_ValueAsString(COLUMNNAME_TerminationReason);
	}

	/** 
	 * Type_Conditions AD_Reference_ID=540271
	 * Reference name: Type_Conditions
	 */
	public static final int TYPE_CONDITIONS_AD_Reference_ID=540271;
	/** FlatFee = FlatFee */
	public static final String TYPE_CONDITIONS_FlatFee = "FlatFee";
	/** HoldingFee = HoldingFee */
	public static final String TYPE_CONDITIONS_HoldingFee = "HoldingFee";
	/** Subscription = Subscr */
	public static final String TYPE_CONDITIONS_Subscription = "Subscr";
	/** Refundable = Refundable */
	public static final String TYPE_CONDITIONS_Refundable = "Refundable";
	/** QualityBasedInvoicing = QualityBsd */
	public static final String TYPE_CONDITIONS_QualityBasedInvoicing = "QualityBsd";
	/** Procurement = Procuremnt */
	public static final String TYPE_CONDITIONS_Procurement = "Procuremnt";
	/** Refund = Refund */
	public static final String TYPE_CONDITIONS_Refund = "Refund";
	/** Commission = Commission */
	public static final String TYPE_CONDITIONS_Commission = "Commission";
	/** MarginCommission = MarginCommission */
	public static final String TYPE_CONDITIONS_MarginCommission = "MarginCommission";
	/** Mediated commission = MediatedCommission */
	public static final String TYPE_CONDITIONS_MediatedCommission = "MediatedCommission";
	/** LicenseFee = LicenseFee */
	public static final String TYPE_CONDITIONS_LicenseFee = "LicenseFee";
	/** CallOrder = CallOrder */
	public static final String TYPE_CONDITIONS_CallOrder = "CallOrder";
	/** InterimInvoice = InterimInvoice */
	public static final String TYPE_CONDITIONS_InterimInvoice = "InterimInvoice";
	/** ModularContract = ModularContract */
	public static final String TYPE_CONDITIONS_ModularContract = "ModularContract";
	@Override
	public void setType_Conditions (final java.lang.String Type_Conditions)
	{
		set_ValueNoCheck (COLUMNNAME_Type_Conditions, Type_Conditions);
	}

	@Override
	public java.lang.String getType_Conditions() 
	{
		return get_ValueAsString(COLUMNNAME_Type_Conditions);
	}

	/** 
	 * Type_Flatrate AD_Reference_ID=540264
	 * Reference name: Type_Flatrate
	 */
	public static final int TYPE_FLATRATE_AD_Reference_ID=540264;
	/** NONE = NONE */
	public static final String TYPE_FLATRATE_NONE = "NONE";
	/** Corridor_Percent = LIPE */
	public static final String TYPE_FLATRATE_Corridor_Percent = "LIPE";
	/** Reported Quantity = RPTD */
	public static final String TYPE_FLATRATE_ReportedQuantity = "RPTD";
	@Override
	public void setType_Flatrate (final @Nullable java.lang.String Type_Flatrate)
	{
		throw new IllegalArgumentException ("Type_Flatrate is virtual column");	}

	@Override
	public java.lang.String getType_Flatrate() 
	{
		return get_ValueAsString(COLUMNNAME_Type_Flatrate);
	}
}