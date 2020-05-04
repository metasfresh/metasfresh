/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_GithubConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_GithubConfig extends org.compiere.model.PO implements I_S_GithubConfig, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -332964461L;

    /** Standard Constructor */
    public X_S_GithubConfig (Properties ctx, int S_GithubConfig_ID, String trxName)
    {
      super (ctx, S_GithubConfig_ID, trxName);
      /** if (S_GithubConfig_ID == 0)
        {
			setConfig (null);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_S_GithubConfig (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Config.
		@param Config Config	  */
	@Override
	public void setConfig (java.lang.String Config)
	{
		set_Value (COLUMNNAME_Config, Config);
	}

	/** Get Config.
		@return Config	  */
	@Override
	public java.lang.String getConfig () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Config);
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Github config.
		@param S_GithubConfig_ID Github config	  */
	@Override
	public void setS_GithubConfig_ID (int S_GithubConfig_ID)
	{
		if (S_GithubConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_GithubConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_GithubConfig_ID, Integer.valueOf(S_GithubConfig_ID));
	}

	/** Get Github config.
		@return Github config	  */
	@Override
	public int getS_GithubConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_GithubConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}