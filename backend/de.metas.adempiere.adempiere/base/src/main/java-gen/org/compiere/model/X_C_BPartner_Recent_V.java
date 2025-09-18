// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_Recent_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Recent_V extends org.compiere.model.PO implements I_C_BPartner_Recent_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 481788612L;

    /** Standard Constructor */
    public X_C_BPartner_Recent_V (final Properties ctx, final int C_BPartner_Recent_V_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Recent_V_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Recent_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
}