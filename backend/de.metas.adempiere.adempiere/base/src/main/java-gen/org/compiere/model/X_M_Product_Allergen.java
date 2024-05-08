/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_Allergen
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Product_Allergen extends org.compiere.model.PO implements I_M_Product_Allergen, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -516114650L;

    /** Standard Constructor */
    public X_M_Product_Allergen (Properties ctx, int M_Product_Allergen_ID, String trxName)
    {
      super (ctx, M_Product_Allergen_ID, trxName);
      /** if (M_Product_Allergen_ID == 0)
        {
			setM_Allergen_ID (0);
			setM_Product_Allergen_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Product_Allergen (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_M_Allergen getM_Allergen() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Allergen_ID, org.compiere.model.I_M_Allergen.class);
	}

	@Override
	public void setM_Allergen(org.compiere.model.I_M_Allergen M_Allergen)
	{
		set_ValueFromPO(COLUMNNAME_M_Allergen_ID, org.compiere.model.I_M_Allergen.class, M_Allergen);
	}

	/** Set M_Allergen.
		@param M_Allergen_ID M_Allergen	  */
	@Override
	public void setM_Allergen_ID (int M_Allergen_ID)
	{
		if (M_Allergen_ID < 1) 
			set_Value (COLUMNNAME_M_Allergen_ID, null);
		else 
			set_Value (COLUMNNAME_M_Allergen_ID, Integer.valueOf(M_Allergen_ID));
	}

	/** Get M_Allergen.
		@return M_Allergen	  */
	@Override
	public int getM_Allergen_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Allergen_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_Product_Allergen.
		@param M_Product_Allergen_ID M_Product_Allergen	  */
	@Override
	public void setM_Product_Allergen_ID (int M_Product_Allergen_ID)
	{
		if (M_Product_Allergen_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Allergen_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Allergen_ID, Integer.valueOf(M_Product_Allergen_ID));
	}

	/** Get M_Product_Allergen.
		@return M_Product_Allergen	  */
	@Override
	public int getM_Product_Allergen_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Allergen_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}