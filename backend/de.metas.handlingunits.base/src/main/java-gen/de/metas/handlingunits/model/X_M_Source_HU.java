/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Source_HU
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Source_HU extends org.compiere.model.PO implements I_M_Source_HU, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 623349405L;

    /** Standard Constructor */
    public X_M_Source_HU (Properties ctx, int M_Source_HU_ID, String trxName)
    {
      super (ctx, M_Source_HU_ID, trxName);
      /** if (M_Source_HU_ID == 0)
        {
			setM_Source_HU_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Source_HU (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.handlingunits.model.I_M_HU getM_HU() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	/** Set Handling Units.
		@param M_HU_ID Handling Units	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
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

	/** Set Source-HU.
		@param M_Source_HU_ID Source-HU	  */
	@Override
	public void setM_Source_HU_ID (int M_Source_HU_ID)
	{
		if (M_Source_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Source_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Source_HU_ID, Integer.valueOf(M_Source_HU_ID));
	}

	/** Get Source-HU.
		@return Source-HU	  */
	@Override
	public int getM_Source_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Source_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PreDestroy_Snapshot_UUID.
		@param PreDestroy_Snapshot_UUID 
		Snapshot einer HU vor ihrer Zerstörung
	  */
	@Override
	public void setPreDestroy_Snapshot_UUID (java.lang.String PreDestroy_Snapshot_UUID)
	{
		set_Value (COLUMNNAME_PreDestroy_Snapshot_UUID, PreDestroy_Snapshot_UUID);
	}

	/** Get PreDestroy_Snapshot_UUID.
		@return Snapshot einer HU vor ihrer Zerstörung
	  */
	@Override
	public java.lang.String getPreDestroy_Snapshot_UUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PreDestroy_Snapshot_UUID);
	}
}