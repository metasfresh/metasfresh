/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_PI_Version
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_PI_Version extends org.compiere.model.PO implements I_M_HU_PI_Version, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -59133326L;

    /** Standard Constructor */
    public X_M_HU_PI_Version (Properties ctx, int M_HU_PI_Version_ID, String trxName)
    {
      super (ctx, M_HU_PI_Version_ID, trxName);
      /** if (M_HU_PI_Version_ID == 0)
        {
			setIsCurrent (false); // N
			setM_HU_PI_ID (0);
			setM_HU_PI_Version_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_HU_PI_Version (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * HU_UnitType AD_Reference_ID=540472
	 * Reference name: HU_UnitType
	 */
	public static final int HU_UNITTYPE_AD_Reference_ID=540472;
	/** TransportUnit = TU */
	public static final String HU_UNITTYPE_TransportUnit = "TU";
	/** LoadLogistiqueUnit = LU */
	public static final String HU_UNITTYPE_LoadLogistiqueUnit = "LU";
	/** VirtualPI = V */
	public static final String HU_UNITTYPE_VirtualPI = "V";
	/** Set Handling Unit Typ.
		@param HU_UnitType Handling Unit Typ	  */
	@Override
	public void setHU_UnitType (java.lang.String HU_UnitType)
	{

		set_Value (COLUMNNAME_HU_UnitType, HU_UnitType);
	}

	/** Get Handling Unit Typ.
		@return Handling Unit Typ	  */
	@Override
	public java.lang.String getHU_UnitType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HU_UnitType);
	}

	/** Set Aktuell gültige Version.
		@param IsCurrent Aktuell gültige Version	  */
	@Override
	public void setIsCurrent (boolean IsCurrent)
	{
		set_Value (COLUMNNAME_IsCurrent, Boolean.valueOf(IsCurrent));
	}

	/** Get Aktuell gültige Version.
		@return Aktuell gültige Version	  */
	@Override
	public boolean isCurrent () 
	{
		Object oo = get_Value(COLUMNNAME_IsCurrent);
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

	/** Set Packvorschrift Version.
		@param M_HU_PI_Version_ID Packvorschrift Version	  */
	@Override
	public void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID)
	{
		if (M_HU_PI_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Version_ID, Integer.valueOf(M_HU_PI_Version_ID));
	}

	/** Get Packvorschrift Version.
		@return Packvorschrift Version	  */
	@Override
	public int getM_HU_PI_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Version_ID);
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