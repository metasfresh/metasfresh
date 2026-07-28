// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Carrier_Product_GoodsType_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Carrier_Product_GoodsType_Alloc extends org.compiere.model.PO implements I_Carrier_Product_GoodsType_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2001968782L;

    /** Standard Constructor */
    public X_Carrier_Product_GoodsType_Alloc (final Properties ctx, final int Carrier_Product_GoodsType_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, Carrier_Product_GoodsType_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_Carrier_Product_GoodsType_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCarrier_Goods_Type_ID (final int Carrier_Goods_Type_ID)
	{
		if (Carrier_Goods_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Carrier_Goods_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Carrier_Goods_Type_ID, Carrier_Goods_Type_ID);
	}

	@Override
	public int getCarrier_Goods_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_Goods_Type_ID);
	}

	@Override
	public void setCarrier_Product_GoodsType_Alloc_ID (final int Carrier_Product_GoodsType_Alloc_ID)
	{
		if (Carrier_Product_GoodsType_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Carrier_Product_GoodsType_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Carrier_Product_GoodsType_Alloc_ID, Carrier_Product_GoodsType_Alloc_ID);
	}

	@Override
	public int getCarrier_Product_GoodsType_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_Product_GoodsType_Alloc_ID);
	}

	@Override
	public void setCarrier_Product_ID (final int Carrier_Product_ID)
	{
		if (Carrier_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Carrier_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Carrier_Product_ID, Carrier_Product_ID);
	}

	@Override
	public int getCarrier_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_Product_ID);
	}
}