/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_BestBefore_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_BestBefore_V extends org.compiere.model.PO implements I_M_HU_BestBefore_V, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1583365673L;

    /** Standard Constructor */
    public X_M_HU_BestBefore_V (Properties ctx, int M_HU_BestBefore_V_ID, String trxName)
    {
      super (ctx, M_HU_BestBefore_V_ID, trxName);
      /** if (M_HU_BestBefore_V_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_M_HU_BestBefore_V (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Min. Garantie-Tage.
		@param GuaranteeDaysMin 
		Mindestanzahl Garantie-Tage
	  */
	@Override
	public void setGuaranteeDaysMin (int GuaranteeDaysMin)
	{
		set_ValueNoCheck (COLUMNNAME_GuaranteeDaysMin, Integer.valueOf(GuaranteeDaysMin));
	}

	/** Get Min. Garantie-Tage.
		@return Mindestanzahl Garantie-Tage
	  */
	@Override
	public int getGuaranteeDaysMin () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GuaranteeDaysMin);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Best Before Date.
		@param HU_BestBeforeDate Best Before Date	  */
	@Override
	public void setHU_BestBeforeDate (java.sql.Timestamp HU_BestBeforeDate)
	{
		set_ValueNoCheck (COLUMNNAME_HU_BestBeforeDate, HU_BestBeforeDate);
	}

	/** Get Best Before Date.
		@return Best Before Date	  */
	@Override
	public java.sql.Timestamp getHU_BestBeforeDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_HU_BestBeforeDate);
	}

	/** Set Expired.
		@param HU_Expired Expired	  */
	@Override
	public void setHU_Expired (java.lang.String HU_Expired)
	{
		set_ValueNoCheck (COLUMNNAME_HU_Expired, HU_Expired);
	}

	/** Get Expired.
		@return Expired	  */
	@Override
	public java.lang.String getHU_Expired () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HU_Expired);
	}

	/** Set Expiring Warning Date.
		@param HU_ExpiredWarnDate Expiring Warning Date	  */
	@Override
	public void setHU_ExpiredWarnDate (java.sql.Timestamp HU_ExpiredWarnDate)
	{
		set_ValueNoCheck (COLUMNNAME_HU_ExpiredWarnDate, HU_ExpiredWarnDate);
	}

	/** Get Expiring Warning Date.
		@return Expiring Warning Date	  */
	@Override
	public java.sql.Timestamp getHU_ExpiredWarnDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_HU_ExpiredWarnDate);
	}

	/** Set Handling Units.
		@param M_HU_ID Handling Units	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Units.
		@return Handling Units	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}