// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS extends org.compiere.model.PO implements I_C_POS, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2043683520L;

    /** Standard Constructor */
    public X_C_POS (final Properties ctx, final int C_POS_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAutoLogoutDelay (final int AutoLogoutDelay)
	{
		set_Value (COLUMNNAME_AutoLogoutDelay, AutoLogoutDelay);
	}

	@Override
	public int getAutoLogoutDelay() 
	{
		return get_ValueAsInt(COLUMNNAME_AutoLogoutDelay);
	}

	@Override
	public void setCashLastBalance (final BigDecimal CashLastBalance)
	{
		set_Value (COLUMNNAME_CashLastBalance, CashLastBalance);
	}

	@Override
	public BigDecimal getCashLastBalance() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CashLastBalance);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_BPartnerCashTrx_ID (final int C_BPartnerCashTrx_ID)
	{
		if (C_BPartnerCashTrx_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerCashTrx_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerCashTrx_ID, C_BPartnerCashTrx_ID);
	}

	@Override
	public int getC_BPartnerCashTrx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartnerCashTrx_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	@Override
	public void setC_DocTypeOrder_ID (final int C_DocTypeOrder_ID)
	{
		if (C_DocTypeOrder_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeOrder_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeOrder_ID, C_DocTypeOrder_ID);
	}

	@Override
	public int getC_DocTypeOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeOrder_ID);
	}

	@Override
	public void setC_POS_ID (final int C_POS_ID)
	{
		if (C_POS_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_ID, C_POS_ID);
	}

	@Override
	public int getC_POS_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_ID);
	}

	@Override
	public void setC_POS_Journal_ID (final int C_POS_Journal_ID)
	{
		if (C_POS_Journal_ID < 1) 
			set_Value (COLUMNNAME_C_POS_Journal_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_Journal_ID, C_POS_Journal_ID);
	}

	@Override
	public int getC_POS_Journal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Journal_ID);
	}

	@Override
	public org.compiere.model.I_C_Workplace getC_Workplace()
	{
		return get_ValueAsPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class);
	}

	@Override
	public void setC_Workplace(final org.compiere.model.I_C_Workplace C_Workplace)
	{
		set_ValueFromPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class, C_Workplace);
	}

	@Override
	public void setC_Workplace_ID (final int C_Workplace_ID)
	{
		if (C_Workplace_ID < 1) 
			set_Value (COLUMNNAME_C_Workplace_ID, null);
		else 
			set_Value (COLUMNNAME_C_Workplace_ID, C_Workplace_ID);
	}

	@Override
	public int getC_Workplace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_ID);
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
	public void setIsModifyPrice (final boolean IsModifyPrice)
	{
		set_Value (COLUMNNAME_IsModifyPrice, IsModifyPrice);
	}

	@Override
	public boolean isModifyPrice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsModifyPrice);
	}

	@Override
	public void setM_PriceList_ID (final int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, M_PriceList_ID);
	}

	@Override
	public int getM_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_ID);
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
	 * POSPaymentProcessor AD_Reference_ID=541896
	 * Reference name: POSPaymentProcessor
	 */
	public static final int POSPAYMENTPROCESSOR_AD_Reference_ID=541896;
	/** SumUp = sumup */
	public static final String POSPAYMENTPROCESSOR_SumUp = "sumup";
	@Override
	public void setPOSPaymentProcessor (final @Nullable java.lang.String POSPaymentProcessor)
	{
		set_Value (COLUMNNAME_POSPaymentProcessor, POSPaymentProcessor);
	}

	@Override
	public java.lang.String getPOSPaymentProcessor() 
	{
		return get_ValueAsString(COLUMNNAME_POSPaymentProcessor);
	}

	@Override
	public void setPrinterName (final @Nullable java.lang.String PrinterName)
	{
		set_Value (COLUMNNAME_PrinterName, PrinterName);
	}

	@Override
	public java.lang.String getPrinterName() 
	{
		return get_ValueAsString(COLUMNNAME_PrinterName);
	}

	@Override
	public void setSUMUP_Config_ID (final int SUMUP_Config_ID)
	{
		if (SUMUP_Config_ID < 1) 
			set_Value (COLUMNNAME_SUMUP_Config_ID, null);
		else 
			set_Value (COLUMNNAME_SUMUP_Config_ID, SUMUP_Config_ID);
	}

	@Override
	public int getSUMUP_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SUMUP_Config_ID);
	}
}