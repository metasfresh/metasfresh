// Generated Model - DO NOT CHANGE
package de.metas.pos.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS_OrderLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS_OrderLine extends org.compiere.model.PO implements I_C_POS_OrderLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1341561492L;

    /** Standard Constructor */
    public X_C_POS_OrderLine (final Properties ctx, final int C_POS_OrderLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_OrderLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS_OrderLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCatch_UOM_ID (final int Catch_UOM_ID)
	{
		if (Catch_UOM_ID < 1) 
			set_Value (COLUMNNAME_Catch_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Catch_UOM_ID, Catch_UOM_ID);
	}

	@Override
	public int getCatch_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Catch_UOM_ID);
	}

	@Override
	public void setCatchWeight (final @Nullable BigDecimal CatchWeight)
	{
		set_Value (COLUMNNAME_CatchWeight, CatchWeight);
	}

	@Override
	public BigDecimal getCatchWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CatchWeight);
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
	public void setC_POS_OrderLine_ID (final int C_POS_OrderLine_ID)
	{
		if (C_POS_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_OrderLine_ID, C_POS_OrderLine_ID);
	}

	@Override
	public int getC_POS_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_OrderLine_ID);
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
	public void setC_Tax_ID (final int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, C_Tax_ID);
	}

	@Override
	public int getC_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_ID);
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
	public void setPrice (final BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	@Override
	public BigDecimal getPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Price);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProductName (final java.lang.String ProductName)
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	@Override
	public java.lang.String getProductName() 
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
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

	@Override
	public void setScannedBarcode (final @Nullable java.lang.String ScannedBarcode)
	{
		set_Value (COLUMNNAME_ScannedBarcode, ScannedBarcode);
	}

	@Override
	public java.lang.String getScannedBarcode() 
	{
		return get_ValueAsString(COLUMNNAME_ScannedBarcode);
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