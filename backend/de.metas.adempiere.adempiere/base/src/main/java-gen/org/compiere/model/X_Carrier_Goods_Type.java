// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Carrier_Goods_Type
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Carrier_Goods_Type extends org.compiere.model.PO implements I_Carrier_Goods_Type, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -759963247L;

    /** Standard Constructor */
    public X_Carrier_Goods_Type (final Properties ctx, final int Carrier_Goods_Type_ID, @Nullable final String trxName)
    {
      super (ctx, Carrier_Goods_Type_ID, trxName);
    }

    /** Load Constructor */
    public X_Carrier_Goods_Type (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
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
}