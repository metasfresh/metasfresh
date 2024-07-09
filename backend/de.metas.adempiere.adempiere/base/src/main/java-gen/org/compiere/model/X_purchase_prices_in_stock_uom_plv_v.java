// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for purchase_prices_in_stock_uom_plv_v
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_purchase_prices_in_stock_uom_plv_v extends org.compiere.model.PO implements I_purchase_prices_in_stock_uom_plv_v, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -384427277L;

	/**
	 * Standard Constructor
	 */
	public X_purchase_prices_in_stock_uom_plv_v(final Properties ctx, final int purchase_prices_in_stock_uom_plv_v_ID, @Nullable final String trxName)
	{
		super(ctx, purchase_prices_in_stock_uom_plv_v_ID, trxName);
	}

	/**
	 * Load Constructor
	 */
	public X_purchase_prices_in_stock_uom_plv_v(final Properties ctx, final ResultSet rs, @Nullable final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Load Meta Data
	 */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_Currency_ID(final int C_Currency_ID)
	{
		if (C_Currency_ID < 1)
			set_ValueNoCheck(COLUMNNAME_C_Currency_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setC_UOM_ID(final int C_UOM_ID)
	{
		if (C_UOM_ID < 1)
			set_ValueNoCheck(COLUMNNAME_C_UOM_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setM_PriceList_ID(final int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1)
			set_ValueNoCheck(COLUMNNAME_M_PriceList_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_M_PriceList_ID, M_PriceList_ID);
	}

	@Override
	public int getM_PriceList_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_ID);
	}

	@Override
	public void setM_PriceList_Version_ID(final int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1)
			set_ValueNoCheck(COLUMNNAME_M_PriceList_Version_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_M_PriceList_Version_ID, M_PriceList_Version_ID);
	}

	@Override
	public int getM_PriceList_Version_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_Version_ID);
	}

	@Override
	public void setM_Product_ID(final int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_ValueNoCheck(COLUMNNAME_M_Product_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setProductPriceInStockUOM(final BigDecimal ProductPriceInStockUOM)
	{
		set_ValueNoCheck(COLUMNNAME_ProductPriceInStockUOM, ProductPriceInStockUOM);
	}

	@Override
	public BigDecimal getProductPriceInStockUOM()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ProductPriceInStockUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setValidFrom(final java.sql.Timestamp ValidFrom)
	{
		set_ValueNoCheck(COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom()
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo(final java.sql.Timestamp ValidTo)
	{
		set_ValueNoCheck(COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo()
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}
}