// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MobileUI_UserProfile_MFG
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_UserProfile_MFG extends org.compiere.model.PO implements I_MobileUI_UserProfile_MFG, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -942272180L;

    /** Standard Constructor */
    public X_MobileUI_UserProfile_MFG (final Properties ctx, final int MobileUI_UserProfile_MFG_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_UserProfile_MFG_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_UserProfile_MFG (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	/** 
	 * IsScanResourceRequired AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISSCANRESOURCEREQUIRED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISSCANRESOURCEREQUIRED_Yes = "Y";
	/** No = N */
	public static final String ISSCANRESOURCEREQUIRED_No = "N";
	@Override
	public void setIsScanResourceRequired (final @Nullable java.lang.String IsScanResourceRequired)
	{
		set_Value (COLUMNNAME_IsScanResourceRequired, IsScanResourceRequired);
	}

	@Override
	public java.lang.String getIsScanResourceRequired() 
	{
		return get_ValueAsString(COLUMNNAME_IsScanResourceRequired);
	}

	@Override
	public void setMobileUI_UserProfile_MFG_ID (final int MobileUI_UserProfile_MFG_ID)
	{
		if (MobileUI_UserProfile_MFG_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_MFG_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_MFG_ID, MobileUI_UserProfile_MFG_ID);
	}

	@Override
	public int getMobileUI_UserProfile_MFG_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_MFG_ID);
	}
}