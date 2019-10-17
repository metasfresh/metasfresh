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
	private static final long serialVersionUID = 216798144L;

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
			setIsCatchUOMForProduct (false); // N
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

	/** Set Ziel-Maßeinheit.
		@param C_UOM_To_ID 
		Maßeinheit, in die eine bestimmte Menge konvertiert werden soll
	  */
	@Override
	public void setC_UOM_To_ID (int C_UOM_To_ID)
	{
		if (C_UOM_To_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_To_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_To_ID, Integer.valueOf(C_UOM_To_ID));
	}

	/** Get Ziel-Maßeinheit.
		@return Maßeinheit, in die eine bestimmte Menge konvertiert werden soll
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
		Der Divisor ist der Kehrwert des Umrechnungsfaktors.
	  */
	@Override
	public void setDivideRate (java.math.BigDecimal DivideRate)
	{
		set_Value (COLUMNNAME_DivideRate, DivideRate);
	}

	/** Get Divisor.
		@return Der Divisor ist der Kehrwert des Umrechnungsfaktors.
	  */
	@Override
	public java.math.BigDecimal getDivideRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DivideRate);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Ziel ist Catch-Maßeinheit.
		@param IsCatchUOMForProduct 
		Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird
	  */
	@Override
	public void setIsCatchUOMForProduct (boolean IsCatchUOMForProduct)
	{
		set_Value (COLUMNNAME_IsCatchUOMForProduct, Boolean.valueOf(IsCatchUOMForProduct));
	}

	/** Get Ziel ist Catch-Maßeinheit.
		@return Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird
	  */
	@Override
	public boolean isCatchUOMForProduct () 
	{
		Object oo = get_Value(COLUMNNAME_IsCatchUOMForProduct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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