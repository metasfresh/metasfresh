// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MobileConfiguration
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileConfiguration extends org.compiere.model.PO implements I_MobileConfiguration, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1713061336L;

    /** Standard Constructor */
    public X_MobileConfiguration (final Properties ctx, final int MobileConfiguration_ID, @Nullable final String trxName)
    {
      super (ctx, MobileConfiguration_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileConfiguration (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * DefaultAuthenticationMethod AD_Reference_ID=541848
	 * Reference name: MobileAuthenticationMethod
	 */
	public static final int DEFAULTAUTHENTICATIONMETHOD_AD_Reference_ID=541848;
	/** QR Code = QR_Code */
	public static final String DEFAULTAUTHENTICATIONMETHOD_QRCode = "QR_Code";
	/** User & Pass = UserPass */
	public static final String DEFAULTAUTHENTICATIONMETHOD_UserPass = "UserPass";
	@Override
	public void setDefaultAuthenticationMethod (final java.lang.String DefaultAuthenticationMethod)
	{
		set_Value (COLUMNNAME_DefaultAuthenticationMethod, DefaultAuthenticationMethod);
	}

	@Override
	public java.lang.String getDefaultAuthenticationMethod() 
	{
		return get_ValueAsString(COLUMNNAME_DefaultAuthenticationMethod);
	}

	@Override
	public void setMobileConfiguration_ID (final int MobileConfiguration_ID)
	{
		if (MobileConfiguration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileConfiguration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileConfiguration_ID, MobileConfiguration_ID);
	}

	@Override
	public int getMobileConfiguration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileConfiguration_ID);
	}
}