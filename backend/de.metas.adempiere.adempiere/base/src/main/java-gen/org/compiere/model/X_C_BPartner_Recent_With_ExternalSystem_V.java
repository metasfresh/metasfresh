// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Recent_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Recent_With_ExternalSystem_V extends PO implements I_C_BPartner_Recent_With_ExternalSystem_V, I_Persistent 
{

	private static final long serialVersionUID = 1566851913L;

    /** Standard Constructor */
    public X_C_BPartner_Recent_With_ExternalSystem_V(final Properties ctx, final int C_BPartner_Recent_V_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Recent_V_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Recent_With_ExternalSystem_V(final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected POInfo initPO(final Properties ctx)
	{
		return POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Recent_V_ID (final int C_BPartner_Recent_V_ID)
	{
		if (C_BPartner_Recent_V_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Recent_V_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Recent_V_ID, C_BPartner_Recent_V_ID);
	}

	@Override
	public int getC_BPartner_Recent_V_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Recent_V_ID);
	}

	@Override
	public void setExternalSystem (final @Nullable String ExternalSystem)
	{
		set_ValueNoCheck (COLUMNNAME_ExternalSystem, ExternalSystem);
	}

	@Override
	public String getExternalSystem() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystem);
	}
}