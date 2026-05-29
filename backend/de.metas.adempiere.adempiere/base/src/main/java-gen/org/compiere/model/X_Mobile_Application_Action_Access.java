// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Mobile_Application_Action_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Mobile_Application_Action_Access extends org.compiere.model.PO implements I_Mobile_Application_Action_Access, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1450884005L;

    /** Standard Constructor */
    public X_Mobile_Application_Action_Access (final Properties ctx, final int Mobile_Application_Action_Access_ID, @Nullable final String trxName)
    {
      super (ctx, Mobile_Application_Action_Access_ID, trxName);
    }

    /** Load Constructor */
    public X_Mobile_Application_Action_Access (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setMobile_Application_Action_Access_ID (final int Mobile_Application_Action_Access_ID)
	{
		if (Mobile_Application_Action_Access_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_Action_Access_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_Action_Access_ID, Mobile_Application_Action_Access_ID);
	}

	@Override
	public int getMobile_Application_Action_Access_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Mobile_Application_Action_Access_ID);
	}

	@Override
	public void setMobile_Application_Action_ID (final int Mobile_Application_Action_ID)
	{
		if (Mobile_Application_Action_ID < 1) 
			set_Value (COLUMNNAME_Mobile_Application_Action_ID, null);
		else 
			set_Value (COLUMNNAME_Mobile_Application_Action_ID, Mobile_Application_Action_ID);
	}

	@Override
	public int getMobile_Application_Action_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Mobile_Application_Action_ID);
	}

	@Override
	public void setMobile_Application_ID (final int Mobile_Application_ID)
	{
		if (Mobile_Application_ID < 1) 
			set_Value (COLUMNNAME_Mobile_Application_ID, null);
		else 
			set_Value (COLUMNNAME_Mobile_Application_ID, Mobile_Application_ID);
	}

	@Override
	public int getMobile_Application_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Mobile_Application_ID);
	}
}