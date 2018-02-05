/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_BestellungAntwort
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_BestellungAntwort extends org.compiere.model.PO implements I_MSV3_BestellungAntwort, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2027469969L;

    /** Standard Constructor */
    public X_MSV3_BestellungAntwort (Properties ctx, int MSV3_BestellungAntwort_ID, String trxName)
    {
      super (ctx, MSV3_BestellungAntwort_ID, trxName);
      /** if (MSV3_BestellungAntwort_ID == 0)
        {
			setMSV3_BestellungAntwort_ID (0);
			setMSV3_Id (null);
        } */
    }

    /** Load Constructor */
    public X_MSV3_BestellungAntwort (Properties ctx, ResultSet rs, String trxName)
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

	/** Set BestellSupportId.
		@param MSV3_BestellSupportId BestellSupportId	  */
	@Override
	public void setMSV3_BestellSupportId (int MSV3_BestellSupportId)
	{
		set_Value (COLUMNNAME_MSV3_BestellSupportId, Integer.valueOf(MSV3_BestellSupportId));
	}

	/** Get BestellSupportId.
		@return BestellSupportId	  */
	@Override
	public int getMSV3_BestellSupportId () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellSupportId);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MSV3_BestellungAntwort.
		@param MSV3_BestellungAntwort_ID MSV3_BestellungAntwort	  */
	@Override
	public void setMSV3_BestellungAntwort_ID (int MSV3_BestellungAntwort_ID)
	{
		if (MSV3_BestellungAntwort_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAntwort_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAntwort_ID, Integer.valueOf(MSV3_BestellungAntwort_ID));
	}

	/** Get MSV3_BestellungAntwort.
		@return MSV3_BestellungAntwort	  */
	@Override
	public int getMSV3_BestellungAntwort_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungAntwort_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Id.
		@param MSV3_Id Id	  */
	@Override
	public void setMSV3_Id (java.lang.String MSV3_Id)
	{
		set_Value (COLUMNNAME_MSV3_Id, MSV3_Id);
	}

	/** Get Id.
		@return Id	  */
	@Override
	public java.lang.String getMSV3_Id () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Id);
	}

	/** Set NachtBetrieb.
		@param MSV3_NachtBetrieb NachtBetrieb	  */
	@Override
	public void setMSV3_NachtBetrieb (boolean MSV3_NachtBetrieb)
	{
		set_Value (COLUMNNAME_MSV3_NachtBetrieb, Boolean.valueOf(MSV3_NachtBetrieb));
	}

	/** Get NachtBetrieb.
		@return NachtBetrieb	  */
	@Override
	public boolean isMSV3_NachtBetrieb () 
	{
		Object oo = get_Value(COLUMNNAME_MSV3_NachtBetrieb);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}