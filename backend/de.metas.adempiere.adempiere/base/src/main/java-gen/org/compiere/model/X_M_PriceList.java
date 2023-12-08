// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_PriceList
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_PriceList extends org.compiere.model.PO implements I_M_PriceList, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1600823182L;

    /** Standard Constructor */
    public X_M_PriceList (final Properties ctx, final int M_PriceList_ID, @Nullable final String trxName)
    {
      super (ctx, M_PriceList_ID, trxName);
    }

    /** Load Constructor */
    public X_M_PriceList (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBasePriceList_ID (final int BasePriceList_ID)
	{
		if (BasePriceList_ID < 1) 
			set_Value (COLUMNNAME_BasePriceList_ID, null);
		else 
			set_Value (COLUMNNAME_BasePriceList_ID, BasePriceList_ID);
	}

	@Override
	public int getBasePriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_BasePriceList_ID);
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
	public void setDefault_TaxCategory_ID (final int Default_TaxCategory_ID)
	{
		if (Default_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_Default_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_Default_TaxCategory_ID, Default_TaxCategory_ID);
	}

	@Override
	public int getDefault_TaxCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Default_TaxCategory_ID);
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
	public void setEnforcePriceLimit (final boolean EnforcePriceLimit)
	{
		set_Value (COLUMNNAME_EnforcePriceLimit, EnforcePriceLimit);
	}

	@Override
	public boolean isEnforcePriceLimit() 
	{
		return get_ValueAsBoolean(COLUMNNAME_EnforcePriceLimit);
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
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsMandatory (final boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, IsMandatory);
	}

	@Override
	public boolean isMandatory() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMandatory);
	}

	@Override
	public void setIsPresentForProduct (final boolean IsPresentForProduct)
	{
		set_Value (COLUMNNAME_IsPresentForProduct, IsPresentForProduct);
	}

	@Override
	public boolean isPresentForProduct() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPresentForProduct);
	}

	@Override
	public void setIsRoundNetAmountToCurrencyPrecision (final boolean IsRoundNetAmountToCurrencyPrecision)
	{
		set_Value (COLUMNNAME_IsRoundNetAmountToCurrencyPrecision, IsRoundNetAmountToCurrencyPrecision);
	}

	@Override
	public boolean isRoundNetAmountToCurrencyPrecision() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRoundNetAmountToCurrencyPrecision);
	}

	@Override
	public void setIsSOPriceList (final boolean IsSOPriceList)
	{
		set_Value (COLUMNNAME_IsSOPriceList, IsSOPriceList);
	}

	@Override
	public boolean isSOPriceList() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOPriceList);
	}

	@Override
	public void setIsTaxIncluded (final boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, IsTaxIncluded);
	}

	@Override
	public boolean isTaxIncluded() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxIncluded);
	}

	@Override
	public void setM_PriceList_ID (final int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_ID, M_PriceList_ID);
	}

	@Override
	public int getM_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_ID);
	}

	@Override
	public void setM_PricingSystem_ID (final int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PricingSystem_ID, M_PricingSystem_ID);
	}

	@Override
	public int getM_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PricingSystem_ID);
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
	public void setPricePrecision (final int PricePrecision)
	{
		set_Value (COLUMNNAME_PricePrecision, PricePrecision);
	}

	@Override
	public int getPricePrecision() 
	{
		return get_ValueAsInt(COLUMNNAME_PricePrecision);
	}
}