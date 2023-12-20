// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_RequisitionLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_RequisitionLine extends org.compiere.model.PO implements I_M_RequisitionLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1115424436L;

    /** Standard Constructor */
    public X_M_RequisitionLine (final Properties ctx, final int M_RequisitionLine_ID, @Nullable final String trxName)
    {
      super (ctx, M_RequisitionLine_ID, trxName);
    }

    /** Load Constructor */
    public X_M_RequisitionLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_Charge_ID (final int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, C_Charge_ID);
	}

	@Override
	public int getC_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setEMail (final @Nullable java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	@Override
	public java.lang.String getEMail() 
	{
		return get_ValueAsString(COLUMNNAME_EMail);
	}

	@Override
	public void setIsNewSupplier (final boolean IsNewSupplier)
	{
		set_Value (COLUMNNAME_IsNewSupplier, IsNewSupplier);
	}

	@Override
	public boolean isNewSupplier() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNewSupplier);
	}

	@Override
	public void setIsNewSupplierAddress (final boolean IsNewSupplierAddress)
	{
		set_Value (COLUMNNAME_IsNewSupplierAddress, IsNewSupplierAddress);
	}

	@Override
	public boolean isNewSupplierAddress() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNewSupplierAddress);
	}

	@Override
	public void setIsVendor (final boolean IsVendor)
	{
		set_ValueNoCheck (COLUMNNAME_IsVendor, IsVendor);
	}

	@Override
	public boolean isVendor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsVendor);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setLineNetAmt (final BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
	public BigDecimal getLineNetAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public org.compiere.model.I_M_Requisition getM_Requisition()
	{
		return get_ValueAsPO(COLUMNNAME_M_Requisition_ID, org.compiere.model.I_M_Requisition.class);
	}

	@Override
	public void setM_Requisition(final org.compiere.model.I_M_Requisition M_Requisition)
	{
		set_ValueFromPO(COLUMNNAME_M_Requisition_ID, org.compiere.model.I_M_Requisition.class, M_Requisition);
	}

	@Override
	public void setM_Requisition_ID (final int M_Requisition_ID)
	{
		if (M_Requisition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Requisition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Requisition_ID, M_Requisition_ID);
	}

	@Override
	public int getM_Requisition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Requisition_ID);
	}

	@Override
	public void setM_RequisitionLine_ID (final int M_RequisitionLine_ID)
	{
		if (M_RequisitionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_RequisitionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_RequisitionLine_ID, M_RequisitionLine_ID);
	}

	@Override
	public int getM_RequisitionLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_RequisitionLine_ID);
	}

	@Override
	public void setNewSupplierAddress (final @Nullable java.lang.String NewSupplierAddress)
	{
		set_Value (COLUMNNAME_NewSupplierAddress, NewSupplierAddress);
	}

	@Override
	public java.lang.String getNewSupplierAddress() 
	{
		return get_ValueAsString(COLUMNNAME_NewSupplierAddress);
	}

	@Override
	public void setNewSupplierName (final @Nullable java.lang.String NewSupplierName)
	{
		set_Value (COLUMNNAME_NewSupplierName, NewSupplierName);
	}

	@Override
	public java.lang.String getNewSupplierName() 
	{
		return get_ValueAsString(COLUMNNAME_NewSupplierName);
	}

	/** 
	 * OrderChannel AD_Reference_ID=541736
	 * Reference name: OrderChannel
	 */
	public static final int ORDERCHANNEL_AD_Reference_ID=541736;
	/** Purchasing = P */
	public static final String ORDERCHANNEL_Purchasing = "P";
	/** Self = S */
	public static final String ORDERCHANNEL_Self = "S";
	@Override
	public void setOrderChannel (final @Nullable java.lang.String OrderChannel)
	{
		set_Value (COLUMNNAME_OrderChannel, OrderChannel);
	}

	@Override
	public java.lang.String getOrderChannel() 
	{
		return get_ValueAsString(COLUMNNAME_OrderChannel);
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
	/** PayPal Extern = V */
	public static final String PAYMENTRULE_PayPalExtern = "V";
	/** Kreditkarte Extern = U */
	public static final String PAYMENTRULE_KreditkarteExtern = "U";
	/** Sofortüberweisung = R */
	public static final String PAYMENTRULE_Sofortueberweisung = "R";
	@Override
	public void setPaymentRule (final @Nullable java.lang.String PaymentRule)
	{
		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	@Override
	public java.lang.String getPaymentRule() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRule);
	}

	@Override
	public void setPriceActual (final BigDecimal PriceActual)
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
	public void setQty (final BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}