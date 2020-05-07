/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_PI
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_PI extends org.compiere.model.PO implements I_M_HU_PI, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 287280127L;

    /** Standard Constructor */
    public X_M_HU_PI (Properties ctx, int M_HU_PI_ID, String trxName)
    {
      super (ctx, M_HU_PI_ID, trxName);
      /** if (M_HU_PI_ID == 0)
        {
			setIsDefaultLU (false); // N
			setM_HU_PI_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_HU_PI (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Standard-LU.
		@param IsDefaultLU Standard-LU	  */
	@Override
	public void setIsDefaultLU (boolean IsDefaultLU)
	{
		set_Value (COLUMNNAME_IsDefaultLU, Boolean.valueOf(IsDefaultLU));
	}

	/** Get Standard-LU.
		@return Standard-LU	  */
	@Override
	public boolean isDefaultLU () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefaultLU);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Packvorschrift.
		@param M_HU_PI_ID Packvorschrift	  */
	@Override
	public void setM_HU_PI_ID (int M_HU_PI_ID)
	{
		if (M_HU_PI_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_ID, Integer.valueOf(M_HU_PI_ID));
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