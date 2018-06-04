/** Generated Model - DO NOT CHANGE */
package de.metas.product.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_LotNumber_Lock
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Product_LotNumber_Lock extends org.compiere.model.PO implements I_M_Product_LotNumber_Lock, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2096417141L;

    /** Standard Constructor */
    public X_M_Product_LotNumber_Lock (Properties ctx, int M_Product_LotNumber_Lock_ID, String trxName)
    {
      super (ctx, M_Product_LotNumber_Lock_ID, trxName);
      /** if (M_Product_LotNumber_Lock_ID == 0)
        {
			setLot (null);
			setM_Product_ID (0);
			setM_Product_LotNumber_Lock_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Product_LotNumber_Lock (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Los-Nr..
		@param Lot 
		Los-Nummer (alphanumerisch)
	  */
	@Override
	public void setLot (java.lang.String Lot)
	{
		set_Value (COLUMNNAME_Lot, Lot);
	}

	/** Get Los-Nr..
		@return Los-Nummer (alphanumerisch)
	  */
	@Override
	public java.lang.String getLot () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lot);
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

	/** Set M_Product_LotNumber_Lock.
		@param M_Product_LotNumber_Lock_ID M_Product_LotNumber_Lock	  */
	@Override
	public void setM_Product_LotNumber_Lock_ID (int M_Product_LotNumber_Lock_ID)
	{
		if (M_Product_LotNumber_Lock_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_LotNumber_Lock_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_LotNumber_Lock_ID, Integer.valueOf(M_Product_LotNumber_Lock_ID));
	}

	/** Get M_Product_LotNumber_Lock.
		@return M_Product_LotNumber_Lock	  */
	@Override
	public int getM_Product_LotNumber_Lock_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_LotNumber_Lock_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}