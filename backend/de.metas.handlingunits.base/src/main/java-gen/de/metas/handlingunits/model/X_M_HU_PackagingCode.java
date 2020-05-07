/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_PackagingCode
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_PackagingCode extends org.compiere.model.PO implements I_M_HU_PackagingCode, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -247359093L;

    /** Standard Constructor */
    public X_M_HU_PackagingCode (Properties ctx, int M_HU_PackagingCode_ID, String trxName)
    {
      super (ctx, M_HU_PackagingCode_ID, trxName);
      /** if (M_HU_PackagingCode_ID == 0)
        {
			setM_HU_PackagingCode_ID (0);
			setPackagingCode (null);
        } */
    }

    /** Load Constructor */
    public X_M_HU_PackagingCode (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Verpackungscode.
		@param M_HU_PackagingCode_ID Verpackungscode	  */
	@Override
	public void setM_HU_PackagingCode_ID (int M_HU_PackagingCode_ID)
	{
		if (M_HU_PackagingCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PackagingCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PackagingCode_ID, Integer.valueOf(M_HU_PackagingCode_ID));
	}

	/** Get Verpackungscode.
		@return Verpackungscode	  */
	@Override
	public int getM_HU_PackagingCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PackagingCode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verpackungscode.
		@param PackagingCode Verpackungscode	  */
	@Override
	public void setPackagingCode (java.lang.String PackagingCode)
	{
		set_Value (COLUMNNAME_PackagingCode, PackagingCode);
	}

	/** Get Verpackungscode.
		@return Verpackungscode	  */
	@Override
	public java.lang.String getPackagingCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PackagingCode);
	}
}