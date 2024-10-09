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

	private static final long serialVersionUID = -1505029870L;

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
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(final org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
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
	public org.compiere.model.I_Mobile_Application getMobile_Application()
	{
		return get_ValueAsPO(COLUMNNAME_Mobile_Application_ID, org.compiere.model.I_Mobile_Application.class);
	}

	@Override
	public void setMobile_Application(final org.compiere.model.I_Mobile_Application Mobile_Application)
	{
		set_ValueFromPO(COLUMNNAME_Mobile_Application_ID, org.compiere.model.I_Mobile_Application.class, Mobile_Application);
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