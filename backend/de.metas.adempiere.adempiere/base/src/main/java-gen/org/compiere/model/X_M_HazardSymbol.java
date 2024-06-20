// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HazardSymbol
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HazardSymbol extends org.compiere.model.PO implements I_M_HazardSymbol, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 147033401L;

    /** Standard Constructor */
    public X_M_HazardSymbol (final Properties ctx, final int M_HazardSymbol_ID, @Nullable final String trxName)
    {
      super (ctx, M_HazardSymbol_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HazardSymbol (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Image getAD_Image()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setAD_Image(final org.compiere.model.I_AD_Image AD_Image)
	{
		set_ValueFromPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class, AD_Image);
	}

	@Override
	public void setAD_Image_ID (final int AD_Image_ID)
	{
		if (AD_Image_ID < 1) 
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Image_ID, AD_Image_ID);
	}

	@Override
	public int getAD_Image_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Image_ID);
	}

	@Override
	public void setM_HazardSymbol_ID (final int M_HazardSymbol_ID)
	{
		if (M_HazardSymbol_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HazardSymbol_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HazardSymbol_ID, M_HazardSymbol_ID);
	}

	@Override
	public int getM_HazardSymbol_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HazardSymbol_ID);
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
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}