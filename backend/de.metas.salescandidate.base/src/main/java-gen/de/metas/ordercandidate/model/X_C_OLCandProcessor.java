/** Generated Model - DO NOT CHANGE */
package de.metas.ordercandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_OLCandProcessor
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_OLCandProcessor extends org.compiere.model.PO implements I_C_OLCandProcessor, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1082229742L;

    /** Standard Constructor */
    public X_C_OLCandProcessor (final Properties ctx, final int C_OLCandProcessor_ID, final String trxName)
    {
      super (ctx, C_OLCandProcessor_ID, trxName);
    }

    /** Load Constructor */
    public X_C_OLCandProcessor (final Properties ctx, final ResultSet rs, final String trxName)
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
	public org.compiere.model.I_AD_Scheduler getAD_Scheduler()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Scheduler_ID, org.compiere.model.I_AD_Scheduler.class);
	}

	@Override
	public void setAD_Scheduler(final org.compiere.model.I_AD_Scheduler AD_Scheduler)
	{
		set_ValueFromPO(COLUMNNAME_AD_Scheduler_ID, org.compiere.model.I_AD_Scheduler.class, AD_Scheduler);
	}

	@Override
	public void setAD_Scheduler_ID (final int AD_Scheduler_ID)
	{
		if (AD_Scheduler_ID < 1) 
			set_Value (COLUMNNAME_AD_Scheduler_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Scheduler_ID, AD_Scheduler_ID);
	}

	@Override
	public int getAD_Scheduler_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Scheduler_ID);
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
	public void setC_DocTypeTarget_ID (final int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, C_DocTypeTarget_ID);
	}

	@Override
	public int getC_DocTypeTarget_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeTarget_ID);
	}

	@Override
	public void setC_OLCandAgg_IncludedTab (final java.lang.String C_OLCandAgg_IncludedTab)
	{
		set_ValueNoCheck (COLUMNNAME_C_OLCandAgg_IncludedTab, C_OLCandAgg_IncludedTab);
	}

	@Override
	public java.lang.String getC_OLCandAgg_IncludedTab() 
	{
		return get_ValueAsString(COLUMNNAME_C_OLCandAgg_IncludedTab);
	}

	@Override
	public void setC_OLCandProcessor_ID (final int C_OLCandProcessor_ID)
	{
		if (C_OLCandProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCandProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCandProcessor_ID, C_OLCandProcessor_ID);
	}

	@Override
	public int getC_OLCandProcessor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCandProcessor_ID);
	}

	@Override
	public void setC_PaymentTerm_ID (final int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, C_PaymentTerm_ID);
	}

	@Override
	public int getC_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_ID);
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
	public void setDeliveryRule (final java.lang.String DeliveryRule)
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
	@Override
	public void setDeliveryViaRule (final java.lang.String DeliveryViaRule)
	{
		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	@Override
	public java.lang.String getDeliveryViaRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryViaRule);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * FreightCostRule AD_Reference_ID=153
	 * Reference name: C_Order FreightCostRule
	 */
	public static final int FREIGHTCOSTRULE_AD_Reference_ID=153;
	/** FreightIncluded = I */
	public static final String FREIGHTCOSTRULE_FreightIncluded = "I";
	/** FixPrice = F */
	public static final String FREIGHTCOSTRULE_FixPrice = "F";
	/** Calculated = C */
	public static final String FREIGHTCOSTRULE_Calculated = "C";
	/** Line = L */
	public static final String FREIGHTCOSTRULE_Line = "L";
	/** Versandkostenpauschale = P */
	public static final String FREIGHTCOSTRULE_Versandkostenpauschale = "P";
	@Override
	public void setFreightCostRule (final java.lang.String FreightCostRule)
	{
		set_Value (COLUMNNAME_FreightCostRule, FreightCostRule);
	}

	@Override
	public java.lang.String getFreightCostRule() 
	{
		return get_ValueAsString(COLUMNNAME_FreightCostRule);
	}

	/** 
	 * InvoiceRule AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int INVOICERULE_AD_Reference_ID=150;
	/** AfterOrderDelivered = O */
	public static final String INVOICERULE_AfterOrderDelivered = "O";
	/** AfterDelivery = D */
	public static final String INVOICERULE_AfterDelivery = "D";
	/** CustomerScheduleAfterDelivery = S */
	public static final String INVOICERULE_CustomerScheduleAfterDelivery = "S";
	/** Immediate = I */
	public static final String INVOICERULE_Immediate = "I";
	/** OrderCompletelyDelivered = C */
	public static final String INVOICERULE_OrderCompletelyDelivered = "C";
	@Override
	public void setInvoiceRule (final java.lang.String InvoiceRule)
	{
		set_Value (COLUMNNAME_InvoiceRule, InvoiceRule);
	}

	@Override
	public java.lang.String getInvoiceRule() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceRule);
	}

	@Override
	public org.compiere.model.I_M_AttributeSet getM_AttributeSet()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class);
	}

	@Override
	public void setM_AttributeSet(final org.compiere.model.I_M_AttributeSet M_AttributeSet)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class, M_AttributeSet);
	}

	@Override
	public void setM_AttributeSet_ID (final int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSet_ID, M_AttributeSet_ID);
	}

	@Override
	public int getM_AttributeSet_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSet_ID);
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
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(final org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	/** 
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** PayPal = L */
	public static final String PAYMENTRULE_PayPal = "L";
	@Override
	public void setPaymentRule (final java.lang.String PaymentRule)
	{
		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	@Override
	public java.lang.String getPaymentRule() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRule);
	}
}