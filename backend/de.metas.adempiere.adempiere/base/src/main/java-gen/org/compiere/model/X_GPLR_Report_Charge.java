// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GPLR_Report_Charge
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GPLR_Report_Charge extends org.compiere.model.PO implements I_GPLR_Report_Charge, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 697997330L;

    /** Standard Constructor */
    public X_GPLR_Report_Charge (final Properties ctx, final int GPLR_Report_Charge_ID, @Nullable final String trxName)
    {
      super (ctx, GPLR_Report_Charge_ID, trxName);
    }

    /** Load Constructor */
    public X_GPLR_Report_Charge (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmount_FC (final @Nullable BigDecimal Amount_FC)
	{
		set_Value (COLUMNNAME_Amount_FC, Amount_FC);
	}

	@Override
	public BigDecimal getAmount_FC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount_FC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmount_LC (final @Nullable BigDecimal Amount_LC)
	{
		set_Value (COLUMNNAME_Amount_LC, Amount_LC);
	}

	@Override
	public BigDecimal getAmount_LC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount_LC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setForeignCurrencyCode (final @Nullable java.lang.String ForeignCurrencyCode)
	{
		set_Value (COLUMNNAME_ForeignCurrencyCode, ForeignCurrencyCode);
	}

	@Override
	public java.lang.String getForeignCurrencyCode() 
	{
		return get_ValueAsString(COLUMNNAME_ForeignCurrencyCode);
	}

	@Override
	public void setGPLR_Report_Charge_ID (final int GPLR_Report_Charge_ID)
	{
		if (GPLR_Report_Charge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_Charge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_Charge_ID, GPLR_Report_Charge_ID);
	}

	@Override
	public int getGPLR_Report_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_Charge_ID);
	}

	@Override
	public org.compiere.model.I_GPLR_Report getGPLR_Report()
	{
		return get_ValueAsPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class);
	}

	@Override
	public void setGPLR_Report(final org.compiere.model.I_GPLR_Report GPLR_Report)
	{
		set_ValueFromPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class, GPLR_Report);
	}

	@Override
	public void setGPLR_Report_ID (final int GPLR_Report_ID)
	{
		if (GPLR_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, GPLR_Report_ID);
	}

	@Override
	public int getGPLR_Report_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_ID);
	}

	@Override
	public void setLineNo (final @Nullable java.lang.String LineNo)
	{
		set_Value (COLUMNNAME_LineNo, LineNo);
	}

	@Override
	public java.lang.String getLineNo() 
	{
		return get_ValueAsString(COLUMNNAME_LineNo);
	}

	@Override
	public void setLocalCurrencyCode (final @Nullable java.lang.String LocalCurrencyCode)
	{
		set_Value (COLUMNNAME_LocalCurrencyCode, LocalCurrencyCode);
	}

	@Override
	public java.lang.String getLocalCurrencyCode() 
	{
		return get_ValueAsString(COLUMNNAME_LocalCurrencyCode);
	}

	@Override
	public void setOrderCostTypeName (final java.lang.String OrderCostTypeName)
	{
		set_Value (COLUMNNAME_OrderCostTypeName, OrderCostTypeName);
	}

	@Override
	public java.lang.String getOrderCostTypeName() 
	{
		return get_ValueAsString(COLUMNNAME_OrderCostTypeName);
	}

	@Override
	public void setOrderDocumentNo (final @Nullable java.lang.String OrderDocumentNo)
	{
		set_Value (COLUMNNAME_OrderDocumentNo, OrderDocumentNo);
	}

	@Override
	public java.lang.String getOrderDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_OrderDocumentNo);
	}

	@Override
	public void setPayee_BPartnerName (final @Nullable java.lang.String Payee_BPartnerName)
	{
		set_Value (COLUMNNAME_Payee_BPartnerName, Payee_BPartnerName);
	}

	@Override
	public java.lang.String getPayee_BPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_Payee_BPartnerName);
	}

	@Override
	public void setPayee_BPartnerValue (final @Nullable java.lang.String Payee_BPartnerValue)
	{
		set_Value (COLUMNNAME_Payee_BPartnerValue, Payee_BPartnerValue);
	}

	@Override
	public java.lang.String getPayee_BPartnerValue() 
	{
		return get_ValueAsString(COLUMNNAME_Payee_BPartnerValue);
	}
}