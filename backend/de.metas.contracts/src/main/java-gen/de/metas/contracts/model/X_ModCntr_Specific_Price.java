// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ModCntr_Specific_Price
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_Specific_Price extends org.compiere.model.PO implements I_ModCntr_Specific_Price, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1381769254L;

    /** Standard Constructor */
    public X_ModCntr_Specific_Price (final Properties ctx, final int ModCntr_Specific_Price_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_Specific_Price_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_Specific_Price (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(final de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	@Override
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public void setC_TaxCategory_ID (final int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, C_TaxCategory_ID);
	}

	@Override
	public int getC_TaxCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxCategory_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Module getModCntr_Module()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Module_ID, de.metas.contracts.model.I_ModCntr_Module.class);
	}

	@Override
	public void setModCntr_Module(final de.metas.contracts.model.I_ModCntr_Module ModCntr_Module)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Module_ID, de.metas.contracts.model.I_ModCntr_Module.class, ModCntr_Module);
	}

	@Override
	public void setModCntr_Module_ID (final int ModCntr_Module_ID)
	{
		if (ModCntr_Module_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_Module_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_Module_ID, ModCntr_Module_ID);
	}

	@Override
	public int getModCntr_Module_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Module_ID);
	}

	@Override
	public void setModCntr_Specific_Price_ID (final int ModCntr_Specific_Price_ID)
	{
		if (ModCntr_Specific_Price_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Specific_Price_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Specific_Price_ID, ModCntr_Specific_Price_ID);
	}

	@Override
	public int getModCntr_Specific_Price_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Specific_Price_ID);
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
	public void setPrice (final BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	@Override
	public BigDecimal getPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Price);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setMinValue (final BigDecimal MinValue)
	{
		set_Value (COLUMNNAME_MinValue, MinValue);
	}

	@Override
	public BigDecimal getMinValue()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MinValue);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsScalePrice(final boolean IsScalePrice)
	{
		set_Value (COLUMNNAME_IsScalePrice, IsScalePrice);
	}

	@Override
	public boolean isScalePrice()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsScalePrice);
	}


}