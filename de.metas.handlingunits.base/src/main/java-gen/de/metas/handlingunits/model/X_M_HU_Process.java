/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_Process
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Process extends org.compiere.model.PO implements I_M_HU_Process, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1879959943L;

    /** Standard Constructor */
    public X_M_HU_Process (Properties ctx, int M_HU_Process_ID, String trxName)
    {
      super (ctx, M_HU_Process_ID, trxName);
      /** if (M_HU_Process_ID == 0)
        {
			setAD_Process_ID (0);
			setIsApplyToCUs (false); // N
			setIsApplyToLUs (false); // N
			setIsApplyToTopLevelHUsOnly (false); // N
			setIsApplyToTUs (false); // N
			setIsProvideAsUserAction (true); // Y
			setM_HU_Process_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_HU_Process (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	/** Set Prozess.
		@param AD_Process_ID 
		Prozess oder Bericht
	  */
	@Override
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Prozess.
		@return Prozess oder Bericht
	  */
	@Override
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auf CUs anwenden.
		@param IsApplyToCUs Auf CUs anwenden	  */
	@Override
	public void setIsApplyToCUs (boolean IsApplyToCUs)
	{
		set_Value (COLUMNNAME_IsApplyToCUs, Boolean.valueOf(IsApplyToCUs));
	}

	/** Get Auf CUs anwenden.
		@return Auf CUs anwenden	  */
	@Override
	public boolean isApplyToCUs () 
	{
		Object oo = get_Value(COLUMNNAME_IsApplyToCUs);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Auf LUs anwenden.
		@param IsApplyToLUs Auf LUs anwenden	  */
	@Override
	public void setIsApplyToLUs (boolean IsApplyToLUs)
	{
		set_Value (COLUMNNAME_IsApplyToLUs, Boolean.valueOf(IsApplyToLUs));
	}

	/** Get Auf LUs anwenden.
		@return Auf LUs anwenden	  */
	@Override
	public boolean isApplyToLUs () 
	{
		Object oo = get_Value(COLUMNNAME_IsApplyToLUs);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Nur auf Top-Level HU anwenden.
		@param IsApplyToTopLevelHUsOnly Nur auf Top-Level HU anwenden	  */
	@Override
	public void setIsApplyToTopLevelHUsOnly (boolean IsApplyToTopLevelHUsOnly)
	{
		set_Value (COLUMNNAME_IsApplyToTopLevelHUsOnly, Boolean.valueOf(IsApplyToTopLevelHUsOnly));
	}

	/** Get Nur auf Top-Level HU anwenden.
		@return Nur auf Top-Level HU anwenden	  */
	@Override
	public boolean isApplyToTopLevelHUsOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsApplyToTopLevelHUsOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Auf TUs anwenden.
		@param IsApplyToTUs Auf TUs anwenden	  */
	@Override
	public void setIsApplyToTUs (boolean IsApplyToTUs)
	{
		set_Value (COLUMNNAME_IsApplyToTUs, Boolean.valueOf(IsApplyToTUs));
	}

	/** Get Auf TUs anwenden.
		@return Auf TUs anwenden	  */
	@Override
	public boolean isApplyToTUs () 
	{
		Object oo = get_Value(COLUMNNAME_IsApplyToTUs);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Als Benutzeraktion verfügbar machen.
		@param IsProvideAsUserAction 
		Entscheidet, ob der Prozess als Aktion im Handling-Unit-Editor (WebUI und SwingUI) auswählbar sein soll.
	  */
	@Override
	public void setIsProvideAsUserAction (boolean IsProvideAsUserAction)
	{
		set_Value (COLUMNNAME_IsProvideAsUserAction, Boolean.valueOf(IsProvideAsUserAction));
	}

	/** Get Als Benutzeraktion verfügbar machen.
		@return Entscheidet, ob der Prozess als Aktion im Handling-Unit-Editor (WebUI und SwingUI) auswählbar sein soll.
	  */
	@Override
	public boolean isProvideAsUserAction () 
	{
		Object oo = get_Value(COLUMNNAME_IsProvideAsUserAction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI getM_HU_PI() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setM_HU_PI(de.metas.handlingunits.model.I_M_HU_PI M_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, M_HU_PI);
	}

	/** Set Packvorschrift.
		@param M_HU_PI_ID Packvorschrift	  */
	@Override
	public void setM_HU_PI_ID (int M_HU_PI_ID)
	{
		if (M_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_ID, Integer.valueOf(M_HU_PI_ID));
	}

	/** Get Packvorschrift.
		@return Packvorschrift	  */
	@Override
	public int getM_HU_PI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Handling Unit Process.
		@param M_HU_Process_ID Handling Unit Process	  */
	@Override
	public void setM_HU_Process_ID (int M_HU_Process_ID)
	{
		if (M_HU_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Process_ID, Integer.valueOf(M_HU_Process_ID));
	}

	/** Get Handling Unit Process.
		@return Handling Unit Process	  */
	@Override
	public int getM_HU_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}