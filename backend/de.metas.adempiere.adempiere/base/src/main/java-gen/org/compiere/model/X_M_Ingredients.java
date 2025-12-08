// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Ingredients
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Ingredients extends org.compiere.model.PO implements I_M_Ingredients, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -549590232L;

    /** Standard Constructor */
    public X_M_Ingredients (final Properties ctx, final int M_Ingredients_ID, @Nullable final String trxName)
    {
      super (ctx, M_Ingredients_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Ingredients (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * Category AD_Reference_ID=541533
	 * Reference name: M_Ingredients_Categories
	 */
	public static final int CATEGORY_AD_Reference_ID=541533;
	/** Fettlösliches  Vitamin = FSV */
	public static final String CATEGORY_FettloeslichesVitamin = "FSV";
	/** Mineralstoff = M */
	public static final String CATEGORY_Mineralstoff = "M";
	/** Spurenelement = TE */
	public static final String CATEGORY_Spurenelement = "TE";
	/** Wasserlösliches Vitamin = WSV */
	public static final String CATEGORY_WasserloeslichesVitamin = "WSV";
	/** Sonstige Zutat = O */
	public static final String CATEGORY_SonstigeZutat = "O";
	@Override
	public void setCategory (final @Nullable java.lang.String Category)
	{
		set_Value (COLUMNNAME_Category, Category);
	}

	@Override
	public java.lang.String getCategory() 
	{
		return get_ValueAsString(COLUMNNAME_Category);
	}

	@Override
	public void setM_Ingredients_ID (final int M_Ingredients_ID)
	{
		if (M_Ingredients_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Ingredients_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Ingredients_ID, M_Ingredients_ID);
	}

	@Override
	public int getM_Ingredients_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Ingredients_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}