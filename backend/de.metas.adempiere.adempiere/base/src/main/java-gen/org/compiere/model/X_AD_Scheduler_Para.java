/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Scheduler_Para
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Scheduler_Para extends org.compiere.model.PO implements I_AD_Scheduler_Para, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2038397759L;

    /** Standard Constructor */
    public X_AD_Scheduler_Para (Properties ctx, int AD_Scheduler_Para_ID, String trxName)
    {
      super (ctx, AD_Scheduler_Para_ID, trxName);
      /** if (AD_Scheduler_Para_ID == 0)
        {
			setAD_Process_Para_ID (0);
			setAD_Scheduler_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Scheduler_Para (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_AD_Process_Para getAD_Process_Para() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_Para_ID, org.compiere.model.I_AD_Process_Para.class);
	}

	@Override
	public void setAD_Process_Para(org.compiere.model.I_AD_Process_Para AD_Process_Para)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_Para_ID, org.compiere.model.I_AD_Process_Para.class, AD_Process_Para);
	}

	/** Set Prozess-Parameter.
		@param AD_Process_Para_ID Prozess-Parameter	  */
	@Override
	public void setAD_Process_Para_ID (int AD_Process_Para_ID)
	{
		if (AD_Process_Para_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Process_Para_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Process_Para_ID, Integer.valueOf(AD_Process_Para_ID));
	}

	/** Get Prozess-Parameter.
		@return Prozess-Parameter	  */
	@Override
	public int getAD_Process_Para_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_Para_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Scheduler getAD_Scheduler() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Scheduler_ID, org.compiere.model.I_AD_Scheduler.class);
	}

	@Override
	public void setAD_Scheduler(org.compiere.model.I_AD_Scheduler AD_Scheduler)
	{
		set_ValueFromPO(COLUMNNAME_AD_Scheduler_ID, org.compiere.model.I_AD_Scheduler.class, AD_Scheduler);
	}

	/** Set Ablaufsteuerung.
		@param AD_Scheduler_ID 
		Schedule Processes
	  */
	@Override
	public void setAD_Scheduler_ID (int AD_Scheduler_ID)
	{
		if (AD_Scheduler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, Integer.valueOf(AD_Scheduler_ID));
	}

	/** Get Ablaufsteuerung.
		@return Schedule Processes
	  */
	@Override
	public int getAD_Scheduler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Scheduler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Default Parameter.
		@param ParameterDefault 
		Default value of the parameter
	  */
	@Override
	public void setParameterDefault (java.lang.String ParameterDefault)
	{
		set_Value (COLUMNNAME_ParameterDefault, ParameterDefault);
	}

	/** Get Default Parameter.
		@return Default value of the parameter
	  */
	@Override
	public java.lang.String getParameterDefault () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ParameterDefault);
	}
}