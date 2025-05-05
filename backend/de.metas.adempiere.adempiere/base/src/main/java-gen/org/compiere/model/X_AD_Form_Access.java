// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Form_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Form_Access extends org.compiere.model.PO implements I_AD_Form_Access, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 507238373L;

    /** Standard Constructor */
    public X_AD_Form_Access (final Properties ctx, final int AD_Form_Access_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Form_Access_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Form_Access (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Form getAD_Form()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class);
	}

	@Override
	public void setAD_Form(final org.compiere.model.I_AD_Form AD_Form)
	{
		set_ValueFromPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class, AD_Form);
	}

	@Override
	public void setAD_Form_ID (final int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Form_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Form_ID, AD_Form_ID);
	}

	@Override
	public int getAD_Form_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Form_ID);
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
	public void setIsReadWrite (final boolean IsReadWrite)
	{
		set_Value (COLUMNNAME_IsReadWrite, IsReadWrite);
	}

	@Override
	public boolean isReadWrite() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReadWrite);
	}
}