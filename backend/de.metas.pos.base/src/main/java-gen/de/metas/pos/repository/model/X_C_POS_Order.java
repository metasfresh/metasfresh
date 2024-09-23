// Generated Model - DO NOT CHANGE
package de.metas.pos.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS_Order extends org.compiere.model.PO implements I_C_POS_Order, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 891337770L;

    /** Standard Constructor */
    public X_C_POS_Order (final Properties ctx, final int C_POS_Order_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_Order_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS_Order (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCashier_ID (final int Cashier_ID)
	{
		if (Cashier_ID < 1) 
			set_Value (COLUMNNAME_Cashier_ID, null);
		else 
			set_Value (COLUMNNAME_Cashier_ID, Cashier_ID);
	}

	@Override
	public int getCashier_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Cashier_ID);
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
	public org.compiere.model.I_C_Location getC_BPartner_Location_Value()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_Value_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_BPartner_Location_Value(final org.compiere.model.I_C_Location C_BPartner_Location_Value)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_Value_ID, org.compiere.model.I_C_Location.class, C_BPartner_Location_Value);
	}

	@Override
	public void setC_BPartner_Location_Value_ID (final int C_BPartner_Location_Value_ID)
	{
		if (C_BPartner_Location_Value_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_Value_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_Value_ID, C_BPartner_Location_Value_ID);
	}

	@Override
	public int getC_BPartner_Location_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_Value_ID);
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
	public org.compiere.model.I_C_Country getC_Country()
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(final org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	@Override
	public void setC_Country_ID (final int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, C_Country_ID);
	}

	@Override
	public int getC_Country_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Country_ID);
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
	public org.compiere.model.I_C_POS getC_POS()
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_ID, org.compiere.model.I_C_POS.class);
	}

	@Override
	public void setC_POS(final org.compiere.model.I_C_POS C_POS)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_ID, org.compiere.model.I_C_POS.class, C_POS);
	}

	@Override
	public void setC_POS_ID (final int C_POS_ID)
	{
		if (C_POS_ID < 1) 
			set_Value (COLUMNNAME_C_POS_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_ID, C_POS_ID);
	}

	@Override
	public int getC_POS_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_ID);
	}

	@Override
	public void setC_POS_Order_ID (final int C_POS_Order_ID)
	{
		if (C_POS_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_Order_ID, C_POS_Order_ID);
	}

	@Override
	public int getC_POS_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Order_ID);
	}

	@Override
	public void setDateTrx (final java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	@Override
	public java.sql.Timestamp getDateTrx() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateTrx);
	}

	@Override
	public void setDocumentNo (final java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setExternalId (final java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setGrandTotal (final BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	@Override
	public BigDecimal getGrandTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotal);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setOpenAmt (final BigDecimal OpenAmt)
	{
		set_Value (COLUMNNAME_OpenAmt, OpenAmt);
	}

	@Override
	public BigDecimal getOpenAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OpenAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPaidAmt (final BigDecimal PaidAmt)
	{
		set_Value (COLUMNNAME_PaidAmt, PaidAmt);
	}

	@Override
	public BigDecimal getPaidAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PaidAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * Status AD_Reference_ID=541890
	 * Reference name: C_POS_Order_Status
	 */
	public static final int STATUS_AD_Reference_ID=541890;
	/** Drafted = DR */
	public static final String STATUS_Drafted = "DR";
	/** WaitingPayment = WP */
	public static final String STATUS_WaitingPayment = "WP";
	/** Completed = CO */
	public static final String STATUS_Completed = "CO";
	/** Voided = VO */
	public static final String STATUS_Voided = "VO";
	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}

	@Override
	public void setTaxAmt (final BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	@Override
	public BigDecimal getTaxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}