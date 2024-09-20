// Generated Model - DO NOT CHANGE
package de.metas.pos.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS_Payment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS_Payment extends org.compiere.model.PO implements I_C_POS_Payment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -518492747L;

    /** Standard Constructor */
    public X_C_POS_Payment (final Properties ctx, final int C_POS_Payment_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_Payment_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS_Payment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmount (final BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public de.metas.pos.repository.model.I_C_POS_Order getC_POS_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_Order_ID, de.metas.pos.repository.model.I_C_POS_Order.class);
	}

	@Override
	public void setC_POS_Order(final de.metas.pos.repository.model.I_C_POS_Order C_POS_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_Order_ID, de.metas.pos.repository.model.I_C_POS_Order.class, C_POS_Order);
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
	public void setC_POS_Payment_ID (final int C_POS_Payment_ID)
	{
		if (C_POS_Payment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_Payment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_Payment_ID, C_POS_Payment_ID);
	}

	@Override
	public int getC_POS_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Payment_ID);
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

	/** 
	 * POSPaymentMethod AD_Reference_ID=541891
	 * Reference name: POSPaymentMethod
	 */
	public static final int POSPAYMENTMETHOD_AD_Reference_ID=541891;
	/** Cash = CASH */
	public static final String POSPAYMENTMETHOD_Cash = "CASH";
	/** Card = CARD */
	public static final String POSPAYMENTMETHOD_Card = "CARD";
	@Override
	public void setPOSPaymentMethod (final java.lang.String POSPaymentMethod)
	{
		set_Value (COLUMNNAME_POSPaymentMethod, POSPaymentMethod);
	}

	@Override
	public java.lang.String getPOSPaymentMethod() 
	{
		return get_ValueAsString(COLUMNNAME_POSPaymentMethod);
	}
}