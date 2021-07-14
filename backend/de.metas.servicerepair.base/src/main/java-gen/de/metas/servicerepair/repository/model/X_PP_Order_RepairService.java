// Generated Model - DO NOT CHANGE
package de.metas.servicerepair.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PP_Order_RepairService
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order_RepairService extends org.compiere.model.PO implements I_PP_Order_RepairService, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 483747502L;

    /** Standard Constructor */
    public X_PP_Order_RepairService (final Properties ctx, final int PP_Order_RepairService_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_RepairService_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_RepairService (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(final org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (final int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, PP_Order_ID);
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}

	@Override
	public void setPP_Order_RepairService_ID (final int PP_Order_RepairService_ID)
	{
		if (PP_Order_RepairService_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_RepairService_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_RepairService_ID, PP_Order_RepairService_ID);
	}

	@Override
	public int getPP_Order_RepairService_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_RepairService_ID);
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
}