/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Product_Stats_InOut_Online_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BPartner_Product_Stats_InOut_Online_v extends org.compiere.model.PO implements I_C_BPartner_Product_Stats_InOut_Online_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1334011364L;

    /** Standard Constructor */
    public X_C_BPartner_Product_Stats_InOut_Online_v (Properties ctx, int C_BPartner_Product_Stats_InOut_Online_v_ID, String trxName)
    {
      super (ctx, C_BPartner_Product_Stats_InOut_Online_v_ID, trxName);
      /** if (C_BPartner_Product_Stats_InOut_Online_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_Product_Stats_InOut_Online_v (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set Letzter Wareneingang.
		@param LastReceiptDate Letzter Wareneingang	  */
	@Override
	public void setLastReceiptDate (java.sql.Timestamp LastReceiptDate)
	{
		set_ValueNoCheck (COLUMNNAME_LastReceiptDate, LastReceiptDate);
	}

	/** Get Letzter Wareneingang.
		@return Letzter Wareneingang	  */
	@Override
	public java.sql.Timestamp getLastReceiptDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LastReceiptDate);
	}

	/** Set Letzte Lieferung.
		@param LastShipDate Letzte Lieferung	  */
	@Override
	public void setLastShipDate (java.sql.Timestamp LastShipDate)
	{
		set_ValueNoCheck (COLUMNNAME_LastShipDate, LastShipDate);
	}

	/** Get Letzte Lieferung.
		@return Letzte Lieferung	  */
	@Override
	public java.sql.Timestamp getLastShipDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LastShipDate);
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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