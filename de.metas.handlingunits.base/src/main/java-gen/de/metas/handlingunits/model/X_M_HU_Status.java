/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_Status
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Status extends org.compiere.model.PO implements I_M_HU_Status, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -543718351L;

    /** Standard Constructor */
    public X_M_HU_Status (Properties ctx, int M_HU_Status_ID, String trxName)
    {
      super (ctx, M_HU_Status_ID, trxName);
      /** if (M_HU_Status_ID == 0)
        {
			setExchangeGebindelagerWhenEmpty (false); // N
			setHUStatus (null);
			setM_HU_Status_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_HU_Status (Properties ctx, ResultSet rs, String trxName)
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
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set ExchangeGebindelagerWhenEmpty.
		@param ExchangeGebindelagerWhenEmpty ExchangeGebindelagerWhenEmpty	  */
	@Override
	public void setExchangeGebindelagerWhenEmpty (boolean ExchangeGebindelagerWhenEmpty)
	{
		set_ValueNoCheck (COLUMNNAME_ExchangeGebindelagerWhenEmpty, Boolean.valueOf(ExchangeGebindelagerWhenEmpty));
	}

	/** Get ExchangeGebindelagerWhenEmpty.
		@return ExchangeGebindelagerWhenEmpty	  */
	@Override
	public boolean isExchangeGebindelagerWhenEmpty () 
	{
		Object oo = get_Value(COLUMNNAME_ExchangeGebindelagerWhenEmpty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * HUStatus AD_Reference_ID=540478
	 * Reference name: HUStatus
	 */
	public static final int HUSTATUS_AD_Reference_ID=540478;
	/** Planning = P */
	public static final String HUSTATUS_Planning = "P";
	/** Active = A */
	public static final String HUSTATUS_Active = "A";
	/** Destroyed = D */
	public static final String HUSTATUS_Destroyed = "D";
	/** Picked = S */
	public static final String HUSTATUS_Picked = "S";
	/** Shipped = E */
	public static final String HUSTATUS_Shipped = "E";
	/** Set Gebinde Status.
		@param HUStatus Gebinde Status	  */
	@Override
	public void setHUStatus (java.lang.String HUStatus)
	{

		set_Value (COLUMNNAME_HUStatus, HUStatus);
	}

	/** Get Gebinde Status.
		@return Gebinde Status	  */
	@Override
	public java.lang.String getHUStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HUStatus);
	}

	/** Set Handling Unit Status.
		@param M_HU_Status_ID Handling Unit Status	  */
	@Override
	public void setM_HU_Status_ID (int M_HU_Status_ID)
	{
		if (M_HU_Status_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Status_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Status_ID, Integer.valueOf(M_HU_Status_ID));
	}

	/** Get Handling Unit Status.
		@return Handling Unit Status	  */
	@Override
	public int getM_HU_Status_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Status_ID);
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