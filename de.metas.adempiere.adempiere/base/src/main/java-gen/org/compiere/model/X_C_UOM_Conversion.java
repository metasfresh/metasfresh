/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_UOM_Conversion
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_UOM_Conversion extends org.compiere.model.PO implements I_C_UOM_Conversion, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -397118694L;

    /** Standard Constructor */
    public X_C_UOM_Conversion (Properties ctx, int C_UOM_Conversion_ID, String trxName)
    {
      super (ctx, C_UOM_Conversion_ID, trxName);
      /** if (C_UOM_Conversion_ID == 0)
        {
			setC_UOM_Conversion_ID (0);
			setC_UOM_ID (0);
			setC_UOM_To_ID (0);
			setDivideRate (BigDecimal.ZERO);
			setMultiplyRate (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_UOM_Conversion (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Umrechnung Maßeinheit.
		@param C_UOM_Conversion_ID 
		Unit of Measure Conversion
	  */
	@Override
	public void setC_UOM_Conversion_ID (int C_UOM_Conversion_ID)
	{
		if (C_UOM_Conversion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_Conversion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_Conversion_ID, Integer.valueOf(C_UOM_Conversion_ID));
	}

	/** Get Umrechnung Maßeinheit.
		@return Unit of Measure Conversion
	  */
	@Override
	public int getC_UOM_Conversion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Conversion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Unit of Measure
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Unit of Measure
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM_To() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_To_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM_To(org.compiere.model.I_C_UOM C_UOM_To)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_To_ID, org.compiere.model.I_C_UOM.class, C_UOM_To);
	}

	/** Set Maßeinheit Nach.
		@param C_UOM_To_ID 
		Target or destination Unit of Measure
	  */
	@Override
	public void setC_UOM_To_ID (int C_UOM_To_ID)
	{
		if (C_UOM_To_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_To_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_To_ID, Integer.valueOf(C_UOM_To_ID));
	}

	/** Get Maßeinheit Nach.
		@return Target or destination Unit of Measure
	  */
	@Override
	public int getC_UOM_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Divisor.
		@param DivideRate 
		To convert Source number to Target number, the Source is divided
	  */
	@Override
	public void setDivideRate (java.math.BigDecimal DivideRate)
	{
		set_Value (COLUMNNAME_DivideRate, DivideRate);
	}

	/** Get Divisor.
		@return To convert Source number to Target number, the Source is divided
	  */
	@Override
	public java.math.BigDecimal getDivideRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DivideRate);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Faktor.
		@param MultiplyRate 
		Rate to multiple the source by to calculate the target.
	  */
	@Override
	public void setMultiplyRate (java.math.BigDecimal MultiplyRate)
	{
		set_Value (COLUMNNAME_MultiplyRate, MultiplyRate);
	}

	/** Get Faktor.
		@return Rate to multiple the source by to calculate the target.
	  */
	@Override
	public java.math.BigDecimal getMultiplyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MultiplyRate);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}