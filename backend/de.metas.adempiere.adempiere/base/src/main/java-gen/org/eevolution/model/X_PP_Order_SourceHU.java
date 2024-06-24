// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Order_SourceHU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order_SourceHU extends org.compiere.model.PO implements I_PP_Order_SourceHU, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 890056825L;

    /** Standard Constructor */
    public X_PP_Order_SourceHU (final Properties ctx, final int PP_Order_SourceHU_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_SourceHU_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_SourceHU (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
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
	public void setPP_Order_SourceHU_ID (final int PP_Order_SourceHU_ID)
	{
		if (PP_Order_SourceHU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_SourceHU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_SourceHU_ID, PP_Order_SourceHU_ID);
	}

	@Override
	public int getPP_Order_SourceHU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_SourceHU_ID);
	}
}