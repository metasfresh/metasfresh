// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Mobile_Application_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Mobile_Application_Access extends org.compiere.model.PO implements I_Mobile_Application_Access, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1484383128L;

    /** Standard Constructor */
    public X_Mobile_Application_Access (final Properties ctx, final int Mobile_Application_Access_ID, @Nullable final String trxName)
    {
      super (ctx, Mobile_Application_Access_ID, trxName);
    }

    /** Load Constructor */
    public X_Mobile_Application_Access (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Role_ID (final int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setIsAllowAllActions (final boolean IsAllowAllActions)
	{
		set_Value (COLUMNNAME_IsAllowAllActions, IsAllowAllActions);
	}

	@Override
	public boolean isAllowAllActions() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowAllActions);
	}

	@Override
	public void setMobile_Application_Access_ID (final int Mobile_Application_Access_ID)
	{
		if (Mobile_Application_Access_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_Access_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_Access_ID, Mobile_Application_Access_ID);
	}

	@Override
	public int getMobile_Application_Access_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Mobile_Application_Access_ID);
	}

	@Override
	public void setMobile_Application_ID (final int Mobile_Application_ID)
	{
		if (Mobile_Application_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_ID, Mobile_Application_ID);
	}

	@Override
	public int getMobile_Application_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Mobile_Application_ID);
	}
}