// Generated Model - DO NOT CHANGE
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for S_GithubConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_GithubConfig extends org.compiere.model.PO implements I_S_GithubConfig, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 835160211L;

    /** Standard Constructor */
    public X_S_GithubConfig (final Properties ctx, final int S_GithubConfig_ID, @Nullable final String trxName)
    {
      super (ctx, S_GithubConfig_ID, trxName);
    }

    /** Load Constructor */
    public X_S_GithubConfig (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setConfig (final java.lang.String Config)
	{
		set_Value (COLUMNNAME_Config, Config);
	}

	@Override
	public java.lang.String getConfig() 
	{
		return get_ValueAsString(COLUMNNAME_Config);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setS_GithubConfig_ID (final int S_GithubConfig_ID)
	{
		if (S_GithubConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_GithubConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_GithubConfig_ID, S_GithubConfig_ID);
	}

	@Override
	public int getS_GithubConfig_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_GithubConfig_ID);
	}
}