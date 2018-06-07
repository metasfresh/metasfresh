/** Generated Model - DO NOT CHANGE */
package de.metas.marketing.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MKTG_Platform
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MKTG_Platform extends org.compiere.model.PO implements I_MKTG_Platform, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -863365227L;

    /** Standard Constructor */
    public X_MKTG_Platform (Properties ctx, int MKTG_Platform_ID, String trxName)
    {
      super (ctx, MKTG_Platform_ID, trxName);
      /** if (MKTG_Platform_ID == 0)
        {
			setIsRequiresLocation (false); // N
			setIsRequiresMailAddres (false); // N
			setMKTG_Platform_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_MKTG_Platform (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Requires Location.
		@param IsRequiresLocation Requires Location	  */
	@Override
	public void setIsRequiresLocation (boolean IsRequiresLocation)
	{
		set_Value (COLUMNNAME_IsRequiresLocation, Boolean.valueOf(IsRequiresLocation));
	}

	/** Get Requires Location.
		@return Requires Location	  */
	@Override
	public boolean isRequiresLocation () 
	{
		Object oo = get_Value(COLUMNNAME_IsRequiresLocation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Requires Mail Address.
		@param IsRequiresMailAddres Requires Mail Address	  */
	@Override
	public void setIsRequiresMailAddres (boolean IsRequiresMailAddres)
	{
		set_Value (COLUMNNAME_IsRequiresMailAddres, Boolean.valueOf(IsRequiresMailAddres));
	}

	/** Get Requires Mail Address.
		@return Requires Mail Address	  */
	@Override
	public boolean isRequiresMailAddres () 
	{
		Object oo = get_Value(COLUMNNAME_IsRequiresMailAddres);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * MarketingPlatformGatewayId AD_Reference_ID=540858
	 * Reference name: MarketingPlatformGatewayId
	 */
	public static final int MARKETINGPLATFORMGATEWAYID_AD_Reference_ID=540858;
	/** CleverReach = CleverReach */
	public static final String MARKETINGPLATFORMGATEWAYID_CleverReach = "CleverReach";
	/** Set Marketing Platform GatewayId.
		@param MarketingPlatformGatewayId Marketing Platform GatewayId	  */
	@Override
	public void setMarketingPlatformGatewayId (java.lang.String MarketingPlatformGatewayId)
	{

		set_Value (COLUMNNAME_MarketingPlatformGatewayId, MarketingPlatformGatewayId);
	}

	/** Get Marketing Platform GatewayId.
		@return Marketing Platform GatewayId	  */
	@Override
	public java.lang.String getMarketingPlatformGatewayId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MarketingPlatformGatewayId);
	}

	/** Set MKTG_Platform.
		@param MKTG_Platform_ID MKTG_Platform	  */
	@Override
	public void setMKTG_Platform_ID (int MKTG_Platform_ID)
	{
		if (MKTG_Platform_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_Platform_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_Platform_ID, Integer.valueOf(MKTG_Platform_ID));
	}

	/** Get MKTG_Platform.
		@return MKTG_Platform	  */
	@Override
	public int getMKTG_Platform_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_Platform_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}