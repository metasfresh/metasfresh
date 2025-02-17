// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Product_TaxCategory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_TaxCategory extends org.compiere.model.PO implements I_M_Product_TaxCategory, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 210540782L;

    /** Standard Constructor */
    public X_M_Product_TaxCategory (final Properties ctx, final int M_Product_TaxCategory_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_TaxCategory_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_TaxCategory (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Country getC_Country()
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(final org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	@Override
	public void setC_Country_ID (final int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, C_Country_ID);
	}

	@Override
	public int getC_Country_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Country_ID);
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
	public void setM_Product_TaxCategory_ID (final int M_Product_TaxCategory_ID)
	{
		if (M_Product_TaxCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_TaxCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_TaxCategory_ID, M_Product_TaxCategory_ID);
	}

	@Override
	public int getM_Product_TaxCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_TaxCategory_ID);
	}

	@Override
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}
}