/** Generated Model - DO NOT CHANGE */
package de.metas.marketing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MKTG_Campaign
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MKTG_Campaign extends org.compiere.model.PO implements I_MKTG_Campaign, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2135258316L;

    /** Standard Constructor */
    public X_MKTG_Campaign (Properties ctx, int MKTG_Campaign_ID, String trxName)
    {
      super (ctx, MKTG_Campaign_ID, trxName);
      /** if (MKTG_Campaign_ID == 0)
        {
			setMarketingPlatformGatewayId (null);
			setMKTG_Campaign_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_MKTG_Campaign (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Enddatum.
		@param EndDate 
		Last effective date (inclusive)
	  */
	@Override
	public void setEndDate (java.sql.Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get Enddatum.
		@return Last effective date (inclusive)
	  */
	@Override
	public java.sql.Timestamp getEndDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EndDate);
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

	/** Set MKTG_Campaign.
		@param MKTG_Campaign_ID MKTG_Campaign	  */
	@Override
	public void setMKTG_Campaign_ID (int MKTG_Campaign_ID)
	{
		if (MKTG_Campaign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_Campaign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_Campaign_ID, Integer.valueOf(MKTG_Campaign_ID));
	}

	/** Get MKTG_Campaign.
		@return MKTG_Campaign	  */
	@Override
	public int getMKTG_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_Campaign_ID);
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

	/** Set Anfangsdatum.
		@param StartDate 
		First effective day (inclusive)
	  */
	@Override
	public void setStartDate (java.sql.Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Anfangsdatum.
		@return First effective day (inclusive)
	  */
	@Override
	public java.sql.Timestamp getStartDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_StartDate);
	}
}