// Generated Model - DO NOT CHANGE
package de.metas.shipper.gateway.dhl.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DHL_Shipper_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DHL_Shipper_Config extends org.compiere.model.PO implements I_DHL_Shipper_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1716512080L;

    /** Standard Constructor */
    public X_DHL_Shipper_Config (final Properties ctx, final int DHL_Shipper_Config_ID, @Nullable final String trxName)
    {
      super (ctx, DHL_Shipper_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_DHL_Shipper_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAccountNumber (final @Nullable java.lang.String AccountNumber)
	{
		set_Value (COLUMNNAME_AccountNumber, AccountNumber);
	}

	@Override
	public java.lang.String getAccountNumber() 
	{
		return get_ValueAsString(COLUMNNAME_AccountNumber);
	}

	@Override
	public void setapplicationID (final @Nullable java.lang.String applicationID)
	{
		set_Value (COLUMNNAME_applicationID, applicationID);
	}

	@Override
	public java.lang.String getapplicationID() 
	{
		return get_ValueAsString(COLUMNNAME_applicationID);
	}

	@Override
	public void setApplicationToken (final @Nullable java.lang.String ApplicationToken)
	{
		set_Value (COLUMNNAME_ApplicationToken, ApplicationToken);
	}

	@Override
	public java.lang.String getApplicationToken() 
	{
		return get_ValueAsString(COLUMNNAME_ApplicationToken);
	}

	@Override
	public void setdhl_api_url (final @Nullable java.lang.String dhl_api_url)
	{
		set_Value (COLUMNNAME_dhl_api_url, dhl_api_url);
	}

	@Override
	public java.lang.String getdhl_api_url() 
	{
		return get_ValueAsString(COLUMNNAME_dhl_api_url);
	}

	@Override
	public void setDhl_LenghtUOM_ID (final int Dhl_LenghtUOM_ID)
	{
		if (Dhl_LenghtUOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Dhl_LenghtUOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Dhl_LenghtUOM_ID, Dhl_LenghtUOM_ID);
	}

	@Override
	public int getDhl_LenghtUOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Dhl_LenghtUOM_ID);
	}

	@Override
	public void setDHL_Shipper_Config_ID (final int DHL_Shipper_Config_ID)
	{
		if (DHL_Shipper_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DHL_Shipper_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DHL_Shipper_Config_ID, DHL_Shipper_Config_ID);
	}

	@Override
	public int getDHL_Shipper_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DHL_Shipper_Config_ID);
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
	public void setSignature (final @Nullable java.lang.String Signature)
	{
		set_Value (COLUMNNAME_Signature, Signature);
	}

	@Override
	public java.lang.String getSignature() 
	{
		return get_ValueAsString(COLUMNNAME_Signature);
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