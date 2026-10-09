// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Flatrate_RefundConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Flatrate_RefundConfig extends org.compiere.model.PO implements I_C_Flatrate_RefundConfig, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 394528154L;

    /** Standard Constructor */
    public X_C_Flatrate_RefundConfig (final Properties ctx, final int C_Flatrate_RefundConfig_ID, @Nullable final String trxName)
    {
      super (ctx, C_Flatrate_RefundConfig_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Flatrate_RefundConfig (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBonus_Product_ID (final int Bonus_Product_ID)
	{
		if (Bonus_Product_ID < 1) 
			set_Value (COLUMNNAME_Bonus_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Bonus_Product_ID, Bonus_Product_ID);
	}

	@Override
	public int getBonus_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bonus_Product_ID);
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
	public void setC_Flatrate_Conditions_ID (final int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, C_Flatrate_Conditions_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_ID);
	}

	@Override
	public void setC_Flatrate_RefundConfig_ID (final int C_Flatrate_RefundConfig_ID)
	{
		if (C_Flatrate_RefundConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_RefundConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_RefundConfig_ID, C_Flatrate_RefundConfig_ID);
	}

	@Override
	public int getC_Flatrate_RefundConfig_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_RefundConfig_ID);
	}

	@Override
	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class);
	}

	@Override
	public void setC_InvoiceSchedule(final org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class, C_InvoiceSchedule);
	}

	@Override
	public void setC_InvoiceSchedule_ID (final int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, C_InvoiceSchedule_ID);
	}

	@Override
	public int getC_InvoiceSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceSchedule_ID);
	}

	@Override
	public void setIsDeductedAtPayment (final boolean IsDeductedAtPayment)
	{
		set_Value (COLUMNNAME_IsDeductedAtPayment, IsDeductedAtPayment);
	}

	@Override
	public boolean isDeductedAtPayment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDeductedAtPayment);
	}

	@Override
	public void setIsPackingOptionFiltered (final boolean IsPackingOptionFiltered)
	{
		set_Value (COLUMNNAME_IsPackingOptionFiltered, IsPackingOptionFiltered);
	}

	@Override
	public boolean isPackingOptionFiltered() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPackingOptionFiltered);
	}

	@Override
	public void setIsUseInProfitCalculation (final boolean IsUseInProfitCalculation)
	{
		set_Value (COLUMNNAME_IsUseInProfitCalculation, IsUseInProfitCalculation);
	}

	@Override
	public boolean isUseInProfitCalculation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseInProfitCalculation);
	}

	@Override
	public void setMinQty (final BigDecimal MinQty)
	{
		set_Value (COLUMNNAME_MinQty, MinQty);
	}

	@Override
	public BigDecimal getMinQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MinQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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
	public void setRefundAmt (final @Nullable BigDecimal RefundAmt)
	{
		set_Value (COLUMNNAME_RefundAmt, RefundAmt);
	}

	@Override
	public BigDecimal getRefundAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RefundAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * RefundBase AD_Reference_ID=540902
	 * Reference name: RefundBase
	 */
	public static final int REFUNDBASE_AD_Reference_ID=540902;
	/** percentage = P */
	public static final String REFUNDBASE_Percentage = "P";
	/** amount = F */
	public static final String REFUNDBASE_Amount = "F";
	@Override
	public void setRefundBase (final java.lang.String RefundBase)
	{
		set_Value (COLUMNNAME_RefundBase, RefundBase);
	}

	@Override
	public java.lang.String getRefundBase() 
	{
		return get_ValueAsString(COLUMNNAME_RefundBase);
	}

	/** 
	 * RefundInvoiceType AD_Reference_ID=540863
	 * Reference name: RefundInvoiceType
	 */
	public static final int REFUNDINVOICETYPE_AD_Reference_ID=540863;
	/** Invoice = Invoice */
	public static final String REFUNDINVOICETYPE_Invoice = "Invoice";
	/** Creditmemo = Creditmemo */
	public static final String REFUNDINVOICETYPE_Creditmemo = "Creditmemo";
	@Override
	public void setRefundInvoiceType (final java.lang.String RefundInvoiceType)
	{
		set_Value (COLUMNNAME_RefundInvoiceType, RefundInvoiceType);
	}

	@Override
	public java.lang.String getRefundInvoiceType() 
	{
		return get_ValueAsString(COLUMNNAME_RefundInvoiceType);
	}

	/** 
	 * RefundMode AD_Reference_ID=540903
	 * Reference name: RefundMode
	 */
	public static final int REFUNDMODE_AD_Reference_ID=540903;
	/** Tiered = T */
	public static final String REFUNDMODE_Tiered = "T";
	/** Accumulated = A */
	public static final String REFUNDMODE_Accumulated = "A";
	@Override
	public void setRefundMode (final java.lang.String RefundMode)
	{
		set_Value (COLUMNNAME_RefundMode, RefundMode);
	}

	@Override
	public java.lang.String getRefundMode() 
	{
		return get_ValueAsString(COLUMNNAME_RefundMode);
	}

	@Override
	public void setRefundPercent (final @Nullable BigDecimal RefundPercent)
	{
		set_Value (COLUMNNAME_RefundPercent, RefundPercent);
	}

	@Override
	public BigDecimal getRefundPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RefundPercent);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}