// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_TaxCategory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_TaxCategory extends org.compiere.model.PO implements I_C_TaxCategory, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1076080462L;

    /** Standard Constructor */
    public X_C_TaxCategory (final Properties ctx, final int C_TaxCategory_ID, @Nullable final String trxName)
    {
      super (ctx, C_TaxCategory_ID, trxName);
    }

    /** Load Constructor */
    public X_C_TaxCategory (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCommodityCode (final @Nullable java.lang.String CommodityCode)
	{
		set_Value (COLUMNNAME_CommodityCode, CommodityCode);
	}

	@Override
	public java.lang.String getCommodityCode() 
	{
		return get_ValueAsString(COLUMNNAME_CommodityCode);
	}

	@Override
	public void setC_TaxCategory_ID (final int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TaxCategory_ID, C_TaxCategory_ID);
	}

	@Override
	public int getC_TaxCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxCategory_ID);
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
	public void setInternalName (final @Nullable java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
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

	/** 
	 * VATType AD_Reference_ID=540842
	 * Reference name: VATType
	 */
	public static final int VATTYPE_AD_Reference_ID=540842;
	/** RegularVAT = N */
	public static final String VATTYPE_RegularVAT = "N";
	/** ReducedVAT = R */
	public static final String VATTYPE_ReducedVAT = "R";
	/** TaxExempt = E */
	public static final String VATTYPE_TaxExempt = "E";
	@Override
	public void setVATType (final @Nullable java.lang.String VATType)
	{
		set_Value (COLUMNNAME_VATType, VATType);
	}

	@Override
	public java.lang.String getVATType() 
	{
		return get_ValueAsString(COLUMNNAME_VATType);
	}
}