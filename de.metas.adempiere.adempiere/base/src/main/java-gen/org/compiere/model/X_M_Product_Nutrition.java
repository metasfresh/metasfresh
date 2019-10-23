/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_Nutrition
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Product_Nutrition extends org.compiere.model.PO implements I_M_Product_Nutrition, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -605850119L;

    /** Standard Constructor */
    public X_M_Product_Nutrition (Properties ctx, int M_Product_Nutrition_ID, String trxName)
    {
      super (ctx, M_Product_Nutrition_ID, trxName);
      /** if (M_Product_Nutrition_ID == 0)
        {
			setM_Nutrition_Fact_ID (0);
			setM_Product_ID (0);
			setM_Product_Nutrition_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Product_Nutrition (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_M_Nutrition_Fact getM_Nutrition_Fact() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Nutrition_Fact_ID, org.compiere.model.I_M_Nutrition_Fact.class);
	}

	@Override
	public void setM_Nutrition_Fact(org.compiere.model.I_M_Nutrition_Fact M_Nutrition_Fact)
	{
		set_ValueFromPO(COLUMNNAME_M_Nutrition_Fact_ID, org.compiere.model.I_M_Nutrition_Fact.class, M_Nutrition_Fact);
	}

	/** Set Nutrition Fact.
		@param M_Nutrition_Fact_ID Nutrition Fact	  */
	@Override
	public void setM_Nutrition_Fact_ID (int M_Nutrition_Fact_ID)
	{
		if (M_Nutrition_Fact_ID < 1) 
			set_Value (COLUMNNAME_M_Nutrition_Fact_ID, null);
		else 
			set_Value (COLUMNNAME_M_Nutrition_Fact_ID, Integer.valueOf(M_Nutrition_Fact_ID));
	}

	/** Get Nutrition Fact.
		@return Nutrition Fact	  */
	@Override
	public int getM_Nutrition_Fact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Nutrition_Fact_ID);
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

	/** Set Product Nutrition.
		@param M_Product_Nutrition_ID Product Nutrition	  */
	@Override
	public void setM_Product_Nutrition_ID (int M_Product_Nutrition_ID)
	{
		if (M_Product_Nutrition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Nutrition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Nutrition_ID, Integer.valueOf(M_Product_Nutrition_ID));
	}

	/** Get Product Nutrition.
		@return Product Nutrition	  */
	@Override
	public int getM_Product_Nutrition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Nutrition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Nutrition Quanitity.
		@param NutritionQty Nutrition Quanitity	  */
	@Override
	public void setNutritionQty (java.lang.String NutritionQty)
	{
		set_Value (COLUMNNAME_NutritionQty, NutritionQty);
	}

	/** Get Nutrition Quanitity.
		@return Nutrition Quanitity	  */
	@Override
	public java.lang.String getNutritionQty () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NutritionQty);
	}
}