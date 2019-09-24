package org.adempiere.model;

/** Generated Model - DO NOT CHANGE */


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_FreightCostDetail
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_M_FreightCostDetail extends org.compiere.model.PO implements I_M_FreightCostDetail, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 142781241L;

    /** Standard Constructor */
    public X_M_FreightCostDetail (Properties ctx, int M_FreightCostDetail_ID, String trxName)
    {
      super (ctx, M_FreightCostDetail_ID, trxName);
      /** if (M_FreightCostDetail_ID == 0)
        {
			setFreightAmt (BigDecimal.ZERO);
			setM_FreightCostDetail_ID (0);
			setM_FreightCostShipper_ID (0);
			setShipmentValueAmt (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_FreightCostDetail (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Country getC_Country()
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	/** Set Land.
		@param C_Country_ID
		Land
	  */
	@Override
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1)
			set_Value (COLUMNNAME_C_Country_ID, null);
		else
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Land.
		@return Land
	  */
	@Override
	public int getC_Country_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Währung.
		@param C_Currency_ID
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1)
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Frachtbetrag.
		@param FreightAmt
		Frachtbetrag
	  */
	@Override
	public void setFreightAmt (java.math.BigDecimal FreightAmt)
	{
		set_Value (COLUMNNAME_FreightAmt, FreightAmt);
	}

	/** Get Frachtbetrag.
		@return Frachtbetrag
	  */
	@Override
	public java.math.BigDecimal getFreightAmt ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FreightAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Frachtkostendetail.
		@param M_FreightCostDetail_ID Frachtkostendetail	  */
	@Override
	public void setM_FreightCostDetail_ID (int M_FreightCostDetail_ID)
	{
		if (M_FreightCostDetail_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_FreightCostDetail_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_FreightCostDetail_ID, Integer.valueOf(M_FreightCostDetail_ID));
	}

	/** Get Frachtkostendetail.
		@return Frachtkostendetail	  */
	@Override
	public int getM_FreightCostDetail_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FreightCostDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.adempiere.model.I_M_FreightCostShipper getM_FreightCostShipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_FreightCostShipper_ID, org.adempiere.model.I_M_FreightCostShipper.class);
	}

	@Override
	public void setM_FreightCostShipper(org.adempiere.model.I_M_FreightCostShipper M_FreightCostShipper)
	{
		set_ValueFromPO(COLUMNNAME_M_FreightCostShipper_ID, org.adempiere.model.I_M_FreightCostShipper.class, M_FreightCostShipper);
	}

	/** Set Lieferweg-Versandkosten.
		@param M_FreightCostShipper_ID Lieferweg-Versandkosten	  */
	@Override
	public void setM_FreightCostShipper_ID (int M_FreightCostShipper_ID)
	{
		if (M_FreightCostShipper_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_FreightCostShipper_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_FreightCostShipper_ID, Integer.valueOf(M_FreightCostShipper_ID));
	}

	/** Get Lieferweg-Versandkosten.
		@return Lieferweg-Versandkosten	  */
	@Override
	public int getM_FreightCostShipper_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_FreightCostShipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferwert.
		@param ShipmentValueAmt Lieferwert	  */
	@Override
	public void setShipmentValueAmt (java.math.BigDecimal ShipmentValueAmt)
	{
		set_Value (COLUMNNAME_ShipmentValueAmt, ShipmentValueAmt);
	}

	/** Get Lieferwert.
		@return Lieferwert	  */
	@Override
	public java.math.BigDecimal getShipmentValueAmt ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ShipmentValueAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}