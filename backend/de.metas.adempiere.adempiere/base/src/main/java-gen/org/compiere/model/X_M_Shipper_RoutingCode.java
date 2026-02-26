// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Shipper_RoutingCode
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Shipper_RoutingCode extends org.compiere.model.PO implements I_M_Shipper_RoutingCode, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1719554458L;

    /** Standard Constructor */
    public X_M_Shipper_RoutingCode (final Properties ctx, final int M_Shipper_RoutingCode_ID, @Nullable final String trxName)
    {
      super (ctx, M_Shipper_RoutingCode_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Shipper_RoutingCode (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(final org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
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
	public void setM_Shipper_RoutingCode_ID (final int M_Shipper_RoutingCode_ID)
	{
		if (M_Shipper_RoutingCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_RoutingCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_RoutingCode_ID, M_Shipper_RoutingCode_ID);
	}

	@Override
	public int getM_Shipper_RoutingCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_RoutingCode_ID);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}