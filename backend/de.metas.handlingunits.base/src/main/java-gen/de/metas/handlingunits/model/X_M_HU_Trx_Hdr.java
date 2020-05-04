/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_Trx_Hdr
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Trx_Hdr extends org.compiere.model.PO implements I_M_HU_Trx_Hdr, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 952308741L;

    /** Standard Constructor */
    public X_M_HU_Trx_Hdr (Properties ctx, int M_HU_Trx_Hdr_ID, String trxName)
    {
      super (ctx, M_HU_Trx_Hdr_ID, trxName);
      /** if (M_HU_Trx_Hdr_ID == 0)
        {
			setM_HU_Trx_Hdr_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_HU_Trx_Hdr (Properties ctx, ResultSet rs, String trxName)
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

	/** Set HU-Transaktionskopf.
		@param M_HU_Trx_Hdr_ID HU-Transaktionskopf	  */
	@Override
	public void setM_HU_Trx_Hdr_ID (int M_HU_Trx_Hdr_ID)
	{
		if (M_HU_Trx_Hdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, Integer.valueOf(M_HU_Trx_Hdr_ID));
	}

	/** Get HU-Transaktionskopf.
		@return HU-Transaktionskopf	  */
	@Override
	public int getM_HU_Trx_Hdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Trx_Hdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}