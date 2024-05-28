// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_ProductPrice
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ProductPrice extends org.compiere.model.PO implements I_M_ProductPrice, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -223919631L;

    /** Standard Constructor */
    public X_M_ProductPrice (final Properties ctx, final int M_ProductPrice_ID, @Nullable final String trxName)
    {
      super (ctx, M_ProductPrice_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ProductPrice (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	/** 
	 * InvoicableQtyBasedOn AD_Reference_ID=541023
	 * Reference name: InvoicableQtyBasedOn
	 */
	public static final int INVOICABLEQTYBASEDON_AD_Reference_ID=541023;
	/** Nominal = Nominal */
	public static final String INVOICABLEQTYBASEDON_Nominal = "Nominal";
	/** CatchWeight = CatchWeight */
	public static final String INVOICABLEQTYBASEDON_CatchWeight = "CatchWeight";
	@Override
	public void setInvoicableQtyBasedOn (final java.lang.String InvoicableQtyBasedOn)
	{
		set_Value (COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn);
	}

	@Override
	public java.lang.String getInvoicableQtyBasedOn() 
	{
		return get_ValueAsString(COLUMNNAME_InvoicableQtyBasedOn);
	}

	@Override
	public void setIsAttributeDependant (final boolean IsAttributeDependant)
	{
		set_Value (COLUMNNAME_IsAttributeDependant, IsAttributeDependant);
	}

	@Override
	public boolean isAttributeDependant() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAttributeDependant);
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
	public void setIsDiscountEditable (final boolean IsDiscountEditable)
	{
		set_Value (COLUMNNAME_IsDiscountEditable, IsDiscountEditable);
	}

	@Override
	public boolean isDiscountEditable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDiscountEditable);
	}

	@Override
	public void setIsInvalidPrice (final boolean IsInvalidPrice)
	{
		set_Value (COLUMNNAME_IsInvalidPrice, IsInvalidPrice);
	}

	@Override
	public boolean isInvalidPrice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvalidPrice);
	}

	@Override
	public void setIsPriceEditable (final boolean IsPriceEditable)
	{
		set_Value (COLUMNNAME_IsPriceEditable, IsPriceEditable);
	}

	@Override
	public boolean isPriceEditable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPriceEditable);
	}

	@Override
	public void setIsSeasonFixedPrice (final boolean IsSeasonFixedPrice)
	{
		set_Value (COLUMNNAME_IsSeasonFixedPrice, IsSeasonFixedPrice);
	}

	@Override
	public boolean isSeasonFixedPrice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSeasonFixedPrice);
	}

	@Override
	public void setMatchSeqNo (final int MatchSeqNo)
	{
		set_Value (COLUMNNAME_MatchSeqNo, MatchSeqNo);
	}

	@Override
	public int getMatchSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_MatchSeqNo);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchemaLine getM_DiscountSchemaLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchemaLine_ID, org.compiere.model.I_M_DiscountSchemaLine.class);
	}

	@Override
	public void setM_DiscountSchemaLine(final org.compiere.model.I_M_DiscountSchemaLine M_DiscountSchemaLine)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchemaLine_ID, org.compiere.model.I_M_DiscountSchemaLine.class, M_DiscountSchemaLine);
	}

	@Override
	public void setM_DiscountSchemaLine_ID (final int M_DiscountSchemaLine_ID)
	{
		if (M_DiscountSchemaLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaLine_ID, M_DiscountSchemaLine_ID);
	}

	@Override
	public int getM_DiscountSchemaLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchemaLine_ID);
	}

	@Override
	public void setM_PriceList_ID (final int M_PriceList_ID)
	{
		throw new IllegalArgumentException ("M_PriceList_ID is virtual column");	}

	@Override
	public int getM_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_ID);
	}

	@Override
	public void setM_PriceList_Version_ID (final int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, M_PriceList_Version_ID);
	}

	@Override
	public int getM_PriceList_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_Version_ID);
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
	public org.compiere.model.I_M_ProductPrice getM_ProductPrice_Base()
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductPrice_Base_ID, org.compiere.model.I_M_ProductPrice.class);
	}

	@Override
	public void setM_ProductPrice_Base(final org.compiere.model.I_M_ProductPrice M_ProductPrice_Base)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductPrice_Base_ID, org.compiere.model.I_M_ProductPrice.class, M_ProductPrice_Base);
	}

	@Override
	public void setM_ProductPrice_Base_ID (final int M_ProductPrice_Base_ID)
	{
		if (M_ProductPrice_Base_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Base_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Base_ID, M_ProductPrice_Base_ID);
	}

	@Override
	public int getM_ProductPrice_Base_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ProductPrice_Base_ID);
	}

	@Override
	public void setM_ProductPrice_ID (final int M_ProductPrice_ID)
	{
		if (M_ProductPrice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, M_ProductPrice_ID);
	}

	@Override
	public int getM_ProductPrice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ProductPrice_ID);
	}

	@Override
	public void setPriceLimit (final BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	@Override
	public BigDecimal getPriceLimit() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceLimit);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceList (final BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	@Override
	public BigDecimal getPriceList() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceList);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceStd (final BigDecimal PriceStd)
	{
		set_Value (COLUMNNAME_PriceStd, PriceStd);
	}

	@Override
	public BigDecimal getPriceStd() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceStd);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ScalePriceQuantityFrom AD_Reference_ID=541870
	 * Reference name: Scale price quantity from
	 */
	public static final int SCALEPRICEQUANTITYFROM_AD_Reference_ID=541870;
	/** Quantity = Q */
	public static final String SCALEPRICEQUANTITYFROM_Quantity = "Q";
	/** UserElementNumber1 = UEN1 */
	public static final String SCALEPRICEQUANTITYFROM_UserElementNumber1 = "UEN1";
	/** UserElementNumber2 = UEN2 */
	public static final String SCALEPRICEQUANTITYFROM_UserElementNumber2 = "UEN2";
	@Override
	public void setScalePriceQuantityFrom (final java.lang.String ScalePriceQuantityFrom)
	{
		set_Value (COLUMNNAME_ScalePriceQuantityFrom, ScalePriceQuantityFrom);
	}

	@Override
	public java.lang.String getScalePriceQuantityFrom() 
	{
		return get_ValueAsString(COLUMNNAME_ScalePriceQuantityFrom);
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

	/** 
	 * UseScalePrice AD_Reference_ID=541376
	 * Reference name: UseScalePrice
	 */
	public static final int USESCALEPRICE_AD_Reference_ID=541376;
	/** Use scale price, fallback to product price = Y */
	public static final String USESCALEPRICE_UseScalePriceFallbackToProductPrice = "Y";
	/** Don't use scale price = N */
	public static final String USESCALEPRICE_DonTUseScalePrice = "N";
	/** Use scale price (strict) = S */
	public static final String USESCALEPRICE_UseScalePriceStrict = "S";
	@Override
	public void setUseScalePrice (final java.lang.String UseScalePrice)
	{
		set_Value (COLUMNNAME_UseScalePrice, UseScalePrice);
	}

	@Override
	public java.lang.String getUseScalePrice() 
	{
		return get_ValueAsString(COLUMNNAME_UseScalePrice);
	}

	@Override
	public void setValidFrom (final @Nullable java.sql.Timestamp ValidFrom)
	{
		throw new IllegalArgumentException ("ValidFrom is virtual column");	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}
}