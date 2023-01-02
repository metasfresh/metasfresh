// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Product_Trl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_Trl extends org.compiere.model.PO implements I_M_Product_Trl, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1875143507L;

    /** Standard Constructor */
    public X_M_Product_Trl (final Properties ctx, final int M_Product_Trl_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_Trl_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_Trl (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAdditional_produktinfos (final @Nullable java.lang.String Additional_produktinfos)
	{
		set_Value (COLUMNNAME_Additional_produktinfos, Additional_produktinfos);
	}

	@Override
	public java.lang.String getAdditional_produktinfos() 
	{
		return get_ValueAsString(COLUMNNAME_Additional_produktinfos);
	}

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	@Override
	public void setAD_Language (final java.lang.String AD_Language)
	{
		set_ValueNoCheck (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language() 
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public void setCustomerLabelName (final @Nullable java.lang.String CustomerLabelName)
	{
		set_Value (COLUMNNAME_CustomerLabelName, CustomerLabelName);
	}

	@Override
	public java.lang.String getCustomerLabelName() 
	{
		return get_ValueAsString(COLUMNNAME_CustomerLabelName);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDocumentNote (final @Nullable java.lang.String DocumentNote)
	{
		set_Value (COLUMNNAME_DocumentNote, DocumentNote);
	}

	@Override
	public java.lang.String getDocumentNote() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNote);
	}

	@Override
	public void setIngredients (final @Nullable java.lang.String Ingredients)
	{
		set_Value (COLUMNNAME_Ingredients, Ingredients);
	}

	@Override
	public java.lang.String getIngredients() 
	{
		return get_ValueAsString(COLUMNNAME_Ingredients);
	}

	@Override
	public void setIsTranslated (final boolean IsTranslated)
	{
		set_Value (COLUMNNAME_IsTranslated, IsTranslated);
	}

	@Override
	public boolean isTranslated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTranslated);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
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

	@Override
	public void setWarehouse_temperature (final @Nullable java.lang.String Warehouse_temperature)
	{
		set_Value (COLUMNNAME_Warehouse_temperature, Warehouse_temperature);
	}

	@Override
	public java.lang.String getWarehouse_temperature() 
	{
		return get_ValueAsString(COLUMNNAME_Warehouse_temperature);
	}
}