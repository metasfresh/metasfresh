// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Mobile_Application_Action
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Mobile_Application_Action extends org.compiere.model.PO implements I_Mobile_Application_Action, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1658859922L;

    /** Standard Constructor */
    public X_Mobile_Application_Action (final Properties ctx, final int Mobile_Application_Action_ID, @Nullable final String trxName)
    {
      super (ctx, Mobile_Application_Action_ID, trxName);
    }

    /** Load Constructor */
    public X_Mobile_Application_Action (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setInternalName (final java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public void setMobile_Application_Action_ID (final int Mobile_Application_Action_ID)
	{
		if (Mobile_Application_Action_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_Action_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_Action_ID, Mobile_Application_Action_ID);
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