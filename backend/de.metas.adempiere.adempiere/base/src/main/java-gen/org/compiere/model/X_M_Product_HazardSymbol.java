// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_HazardSymbol
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_HazardSymbol extends org.compiere.model.PO implements I_M_Product_HazardSymbol, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 892359115L;

    /** Standard Constructor */
    public X_M_Product_HazardSymbol (final Properties ctx, final int M_Product_HazardSymbol_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_HazardSymbol_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_HazardSymbol (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_M_HazardSymbol getM_HazardSymbol()
	{
		return get_ValueAsPO(COLUMNNAME_M_HazardSymbol_ID, org.compiere.model.I_M_HazardSymbol.class);
	}

	@Override
	public void setM_HazardSymbol(final org.compiere.model.I_M_HazardSymbol M_HazardSymbol)
	{
		set_ValueFromPO(COLUMNNAME_M_HazardSymbol_ID, org.compiere.model.I_M_HazardSymbol.class, M_HazardSymbol);
	}

	@Override
	public void setM_HazardSymbol_ID (final int M_HazardSymbol_ID)
	{
		if (M_HazardSymbol_ID < 1) 
			set_Value (COLUMNNAME_M_HazardSymbol_ID, null);
		else 
			set_Value (COLUMNNAME_M_HazardSymbol_ID, M_HazardSymbol_ID);
	}

	@Override
	public int getM_HazardSymbol_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HazardSymbol_ID);
	}

	@Override
	public void setM_Product_HazardSymbol_ID (final int M_Product_HazardSymbol_ID)
	{
		if (M_Product_HazardSymbol_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_HazardSymbol_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_HazardSymbol_ID, M_Product_HazardSymbol_ID);
	}

	@Override
	public int getM_Product_HazardSymbol_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_HazardSymbol_ID);
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
}