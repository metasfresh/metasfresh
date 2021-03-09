// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Order_CompensationGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Order_CompensationGroup extends org.compiere.model.PO implements I_C_Order_CompensationGroup, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1808140734L;

    /** Standard Constructor */
    public X_C_Order_CompensationGroup (final Properties ctx, final int C_Order_CompensationGroup_ID, @Nullable final String trxName)
    {
      super (ctx, C_Order_CompensationGroup_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Order_CompensationGroup (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema()
	{
		return get_ValueAsPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class);
	}

	@Override
	public void setC_CompensationGroup_Schema(final de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema)
	{
		set_ValueFromPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class, C_CompensationGroup_Schema);
	}

	@Override
	public void setC_CompensationGroup_Schema_ID (final int C_CompensationGroup_Schema_ID)
	{
		if (C_CompensationGroup_Schema_ID < 1) 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, null);
		else 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, C_CompensationGroup_Schema_ID);
	}

	@Override
	public int getC_CompensationGroup_Schema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_Schema_ID);
	}

	@Override
	public void setC_Order_CompensationGroup_ID (final int C_Order_CompensationGroup_ID)
	{
		if (C_Order_CompensationGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_CompensationGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_CompensationGroup_ID, C_Order_CompensationGroup_ID);
	}

	@Override
	public int getC_Order_CompensationGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_CompensationGroup_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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

	@Override
	public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class);
	}

	@Override
	public void setPP_Product_BOM(final org.eevolution.model.I_PP_Product_BOM PP_Product_BOM)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class, PP_Product_BOM);
	}

	@Override
	public void setPP_Product_BOM_ID (final int PP_Product_BOM_ID)
	{
		if (PP_Product_BOM_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_BOM_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_BOM_ID, PP_Product_BOM_ID);
	}

	@Override
	public int getPP_Product_BOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOM_ID);
	}
}