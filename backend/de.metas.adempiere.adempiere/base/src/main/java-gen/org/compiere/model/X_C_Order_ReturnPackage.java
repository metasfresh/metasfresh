// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Order_ReturnPackage
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Order_ReturnPackage extends org.compiere.model.PO implements I_C_Order_ReturnPackage, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -778725519L;

    /** Standard Constructor */
    public X_C_Order_ReturnPackage (final Properties ctx, final int C_Order_ReturnPackage_ID, @Nullable final String trxName)
    {
      super (ctx, C_Order_ReturnPackage_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Order_ReturnPackage (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Order_ReturnPackage_ID (final int C_Order_ReturnPackage_ID)
	{
		if (C_Order_ReturnPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ReturnPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ReturnPackage_ID, C_Order_ReturnPackage_ID);
	}

	@Override
	public int getC_Order_ReturnPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ReturnPackage_ID);
	}

	/** 
	 * PalletType AD_Reference_ID=542107
	 * Reference name: Rücknahme Palettentyp
	 */
	public static final int PALLETTYPE_AD_Reference_ID=542107;
	/** EUR = EUR */
	public static final String PALLETTYPE_EUR = "EUR";
	/** H1 = H1 */
	public static final String PALLETTYPE_H1 = "H1";
	@Override
	public void setPalletType (final @Nullable java.lang.String PalletType)
	{
		set_Value (COLUMNNAME_PalletType, PalletType);
	}

	@Override
	public java.lang.String getPalletType() 
	{
		return get_ValueAsString(COLUMNNAME_PalletType);
	}

	@Override
	public void setQtyDeliveredLU (final @Nullable BigDecimal QtyDeliveredLU)
	{
		set_Value (COLUMNNAME_QtyDeliveredLU, QtyDeliveredLU);
	}

	@Override
	public BigDecimal getQtyDeliveredLU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredLU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReturnedLU (final @Nullable BigDecimal QtyReturnedLU)
	{
		set_Value (COLUMNNAME_QtyReturnedLU, QtyReturnedLU);
	}

	@Override
	public BigDecimal getQtyReturnedLU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReturnedLU);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}
