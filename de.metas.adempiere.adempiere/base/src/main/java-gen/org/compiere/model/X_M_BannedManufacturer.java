/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_BannedManufacturer
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_BannedManufacturer extends org.compiere.model.PO implements I_M_BannedManufacturer, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 216647831L;

    /** Standard Constructor */
    public X_M_BannedManufacturer (Properties ctx, int M_BannedManufacturer_ID, String trxName)
    {
      super (ctx, M_BannedManufacturer_ID, trxName);
      /** if (M_BannedManufacturer_ID == 0)
        {
			setC_BPartner_ID (0);
			setExclusionFromSaleReason (null);
			setM_BannedManufacturer_ID (0);
			setManufacturer_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_BannedManufacturer (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Exclusion From Sale Reason.
		@param ExclusionFromSaleReason Exclusion From Sale Reason	  */
	@Override
	public void setExclusionFromSaleReason (java.lang.String ExclusionFromSaleReason)
	{
		set_Value (COLUMNNAME_ExclusionFromSaleReason, ExclusionFromSaleReason);
	}

	/** Get Exclusion From Sale Reason.
		@return Exclusion From Sale Reason	  */
	@Override
	public java.lang.String getExclusionFromSaleReason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExclusionFromSaleReason);
	}

	/** Set Banned Manufacturer .
		@param M_BannedManufacturer_ID Banned Manufacturer 	  */
	@Override
	public void setM_BannedManufacturer_ID (int M_BannedManufacturer_ID)
	{
		if (M_BannedManufacturer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_BannedManufacturer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_BannedManufacturer_ID, Integer.valueOf(M_BannedManufacturer_ID));
	}

	/** Get Banned Manufacturer .
		@return Banned Manufacturer 	  */
	@Override
	public int getM_BannedManufacturer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_BannedManufacturer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getManufacturer() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Manufacturer_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setManufacturer(org.compiere.model.I_C_BPartner Manufacturer)
	{
		set_ValueFromPO(COLUMNNAME_Manufacturer_ID, org.compiere.model.I_C_BPartner.class, Manufacturer);
	}

	/** Set Hersteller.
		@param Manufacturer_ID 
		Hersteller des Produktes
	  */
	@Override
	public void setManufacturer_ID (int Manufacturer_ID)
	{
		if (Manufacturer_ID < 1) 
			set_Value (COLUMNNAME_Manufacturer_ID, null);
		else 
			set_Value (COLUMNNAME_Manufacturer_ID, Integer.valueOf(Manufacturer_ID));
	}

	/** Get Hersteller.
		@return Hersteller des Produktes
	  */
	@Override
	public int getManufacturer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Manufacturer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}