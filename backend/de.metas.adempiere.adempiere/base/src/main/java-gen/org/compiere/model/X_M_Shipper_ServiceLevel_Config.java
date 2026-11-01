// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Shipper_ServiceLevel_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Shipper_ServiceLevel_Config extends org.compiere.model.PO implements I_M_Shipper_ServiceLevel_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1356895135L;

    /** Standard Constructor */
    public X_M_Shipper_ServiceLevel_Config (final Properties ctx, final int M_Shipper_ServiceLevel_Config_ID, @Nullable final String trxName)
    {
      super (ctx, M_Shipper_ServiceLevel_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Shipper_ServiceLevel_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setExternal_System_ID (final int External_System_ID)
	{
		if (External_System_ID < 1) 
			set_Value (COLUMNNAME_External_System_ID, null);
		else 
			set_Value (COLUMNNAME_External_System_ID, External_System_ID);
	}

	@Override
	public int getExternal_System_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_External_System_ID);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_Shipper_ServiceLevel_Config_ID (final int M_Shipper_ServiceLevel_Config_ID)
	{
		if (M_Shipper_ServiceLevel_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ServiceLevel_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ServiceLevel_Config_ID, M_Shipper_ServiceLevel_Config_ID);
	}

	@Override
	public int getM_Shipper_ServiceLevel_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ServiceLevel_Config_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setServiceLevel (final java.lang.String ServiceLevel)
	{
		set_Value (COLUMNNAME_ServiceLevel, ServiceLevel);
	}

	@Override
	public java.lang.String getServiceLevel() 
	{
		return get_ValueAsString(COLUMNNAME_ServiceLevel);
	}
}