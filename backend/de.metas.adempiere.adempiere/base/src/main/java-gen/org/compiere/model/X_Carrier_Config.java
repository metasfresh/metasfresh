// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Carrier_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Carrier_Config extends org.compiere.model.PO implements I_Carrier_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1305250872L;

    /** Standard Constructor */
    public X_Carrier_Config (final Properties ctx, final int Carrier_Config_ID, @Nullable final String trxName)
    {
      super (ctx, Carrier_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_Carrier_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setActorId (final @Nullable java.lang.String ActorId)
	{
		set_Value (COLUMNNAME_ActorId, ActorId);
	}

	@Override
	public java.lang.String getActorId() 
	{
		return get_ValueAsString(COLUMNNAME_ActorId);
	}

	@Override
	public void setBase_url (final @Nullable java.lang.String Base_url)
	{
		set_Value (COLUMNNAME_Base_url, Base_url);
	}

	@Override
	public java.lang.String getBase_url() 
	{
		return get_ValueAsString(COLUMNNAME_Base_url);
	}

	@Override
	public void setCarrier_Config_ID (final int Carrier_Config_ID)
	{
		if (Carrier_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Carrier_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Carrier_Config_ID, Carrier_Config_ID);
	}

	@Override
	public int getCarrier_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_Config_ID);
	}

	@Override
	public void setClient_Id (final @Nullable java.lang.String Client_Id)
	{
		set_Value (COLUMNNAME_Client_Id, Client_Id);
	}

	@Override
	public java.lang.String getClient_Id() 
	{
		return get_ValueAsString(COLUMNNAME_Client_Id);
	}

	@Override
	public void setClient_Secret (final @Nullable java.lang.String Client_Secret)
	{
		set_Value (COLUMNNAME_Client_Secret, Client_Secret);
	}

	@Override
	public java.lang.String getClient_Secret() 
	{
		return get_ValueAsString(COLUMNNAME_Client_Secret);
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
	public void setPassword (final @Nullable java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	@Override
	public java.lang.String getPassword() 
	{
		return get_ValueAsString(COLUMNNAME_Password);
	}

	@Override
	public void setUserName (final @Nullable java.lang.String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	@Override
	public java.lang.String getUserName() 
	{
		return get_ValueAsString(COLUMNNAME_UserName);
	}
}