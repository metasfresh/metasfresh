// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Carrier_ShipmentOrder_Item
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Carrier_ShipmentOrder_Item extends org.compiere.model.PO implements I_Carrier_ShipmentOrder_Item, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1016123494L;

    /** Standard Constructor */
    public X_Carrier_ShipmentOrder_Item (final Properties ctx, final int Carrier_ShipmentOrder_Item_ID, @Nullable final String trxName)
    {
      super (ctx, Carrier_ShipmentOrder_Item_ID, trxName);
    }

    /** Load Constructor */
    public X_Carrier_ShipmentOrder_Item (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setArticleValue (final @Nullable java.lang.String ArticleValue)
	{
		set_Value (COLUMNNAME_ArticleValue, ArticleValue);
	}

	@Override
	public java.lang.String getArticleValue() 
	{
		return get_ValueAsString(COLUMNNAME_ArticleValue);
	}

	@Override
	public void setCarrier_ShipmentOrder_Item_ID (final int Carrier_ShipmentOrder_Item_ID)
	{
		if (Carrier_ShipmentOrder_Item_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Carrier_ShipmentOrder_Item_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Carrier_ShipmentOrder_Item_ID, Carrier_ShipmentOrder_Item_ID);
	}

	@Override
	public int getCarrier_ShipmentOrder_Item_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_ShipmentOrder_Item_ID);
	}

	@Override
	public void setCarrier_ShipmentOrder_Parcel_ID (final int Carrier_ShipmentOrder_Parcel_ID)
	{
		if (Carrier_ShipmentOrder_Parcel_ID < 1) 
			set_Value (COLUMNNAME_Carrier_ShipmentOrder_Parcel_ID, null);
		else 
			set_Value (COLUMNNAME_Carrier_ShipmentOrder_Parcel_ID, Carrier_ShipmentOrder_Parcel_ID);
	}

	@Override
	public int getCarrier_ShipmentOrder_Parcel_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_ShipmentOrder_Parcel_ID);
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
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	@Override
	public java.lang.String getProductName() 
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setQtyShipped (final @Nullable BigDecimal QtyShipped)
	{
		set_Value (COLUMNNAME_QtyShipped, QtyShipped);
	}

	@Override
	public BigDecimal getQtyShipped() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyShipped);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalPrice (final BigDecimal TotalPrice)
	{
		set_Value (COLUMNNAME_TotalPrice, TotalPrice);
	}

	@Override
	public BigDecimal getTotalPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalWeightInKg (final BigDecimal TotalWeightInKg)
	{
		set_Value (COLUMNNAME_TotalWeightInKg, TotalWeightInKg);
	}

	@Override
	public BigDecimal getTotalWeightInKg() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalWeightInKg);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}