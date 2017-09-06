/** Generated Model - DO NOT CHANGE */
package de.metas.flatrate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_Data
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Flatrate_Data extends org.compiere.model.PO implements I_C_Flatrate_Data, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 114751667L;

    /** Standard Constructor */
    public X_C_Flatrate_Data (Properties ctx, int C_Flatrate_Data_ID, String trxName)
    {
      super (ctx, C_Flatrate_Data_ID, trxName);
      /** if (C_Flatrate_Data_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Flatrate_Data_ID (0);
			setHasContracts (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_Flatrate_Data (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datenerfassung.
		@param C_Flatrate_Data_ID Datenerfassung	  */
	@Override
	public void setC_Flatrate_Data_ID (int C_Flatrate_Data_ID)
	{
		if (C_Flatrate_Data_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Data_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Data_ID, Integer.valueOf(C_Flatrate_Data_ID));
	}

	/** Get Datenerfassung.
		@return Datenerfassung	  */
	@Override
	public int getC_Flatrate_Data_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Data_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Flatrate_DataEntry_IncludedTab.
		@param C_Flatrate_DataEntry_IncludedT C_Flatrate_DataEntry_IncludedTab	  */
	@Override
	public void setC_Flatrate_DataEntry_IncludedT (java.lang.String C_Flatrate_DataEntry_IncludedT)
	{
		set_Value (COLUMNNAME_C_Flatrate_DataEntry_IncludedT, C_Flatrate_DataEntry_IncludedT);
	}

	/** Get C_Flatrate_DataEntry_IncludedTab.
		@return C_Flatrate_DataEntry_IncludedTab	  */
	@Override
	public java.lang.String getC_Flatrate_DataEntry_IncludedT () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_Flatrate_DataEntry_IncludedT);
	}

	/** Set Existierende Verträge.
		@param HasContracts Existierende Verträge	  */
	@Override
	public void setHasContracts (boolean HasContracts)
	{
		set_Value (COLUMNNAME_HasContracts, Boolean.valueOf(HasContracts));
	}

	/** Get Existierende Verträge.
		@return Existierende Verträge	  */
	@Override
	public boolean isHasContracts () 
	{
		Object oo = get_Value(COLUMNNAME_HasContracts);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}