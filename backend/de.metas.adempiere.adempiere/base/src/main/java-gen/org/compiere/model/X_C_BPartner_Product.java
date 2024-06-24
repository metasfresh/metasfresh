// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Product extends org.compiere.model.PO implements I_C_BPartner_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1782571382L;

    /** Standard Constructor */
    public X_C_BPartner_Product (final Properties ctx, final int C_BPartner_Product_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Product (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Product_ID (final int C_BPartner_Product_ID)
	{
		if (C_BPartner_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Product_ID, C_BPartner_Product_ID);
	}

	@Override
	public int getC_BPartner_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Product_ID);
	}

	@Override
	public void setC_BPartner_Vendor_ID (final int C_BPartner_Vendor_ID)
	{
		if (C_BPartner_Vendor_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, C_BPartner_Vendor_ID);
	}

	@Override
	public int getC_BPartner_Vendor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Vendor_ID);
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
	public void setDeliveryTime_Promised (final int DeliveryTime_Promised)
	{
		set_Value (COLUMNNAME_DeliveryTime_Promised, DeliveryTime_Promised);
	}

	@Override
	public int getDeliveryTime_Promised() 
	{
		return get_ValueAsInt(COLUMNNAME_DeliveryTime_Promised);
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
	public void setEAN_CU (final @Nullable java.lang.String EAN_CU)
	{
		set_Value (COLUMNNAME_EAN_CU, EAN_CU);
	}

	@Override
	public java.lang.String getEAN_CU() 
	{
		return get_ValueAsString(COLUMNNAME_EAN_CU);
	}

	@Override
	public void setExclusionFromPurchaseReason (final @Nullable java.lang.String ExclusionFromPurchaseReason)
	{
		set_Value (COLUMNNAME_ExclusionFromPurchaseReason, ExclusionFromPurchaseReason);
	}

	@Override
	public java.lang.String getExclusionFromPurchaseReason() 
	{
		return get_ValueAsString(COLUMNNAME_ExclusionFromPurchaseReason);
	}

	@Override
	public void setExclusionFromSaleReason (final @Nullable java.lang.String ExclusionFromSaleReason)
	{
		set_Value (COLUMNNAME_ExclusionFromSaleReason, ExclusionFromSaleReason);
	}

	@Override
	public java.lang.String getExclusionFromSaleReason() 
	{
		return get_ValueAsString(COLUMNNAME_ExclusionFromSaleReason);
	}

	@Override
	public void setFLO_Identifier (final @Nullable java.lang.String FLO_Identifier)
	{
		set_Value (COLUMNNAME_FLO_Identifier, FLO_Identifier);
	}

	@Override
	public java.lang.String getFLO_Identifier()
	{
		return get_ValueAsString(COLUMNNAME_FLO_Identifier);
	}

	@Override
	public void setGTIN (final @Nullable java.lang.String GTIN)
	{
		set_Value (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public java.lang.String getGTIN() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN);
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
	public void setIsCurrentVendor (final boolean IsCurrentVendor)
	{
		set_Value (COLUMNNAME_IsCurrentVendor, IsCurrentVendor);
	}

	@Override
	public boolean isCurrentVendor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCurrentVendor);
	}

	@Override
	public void setIsDropShip (final boolean IsDropShip)
	{
		set_Value (COLUMNNAME_IsDropShip, IsDropShip);
	}

	@Override
	public boolean isDropShip() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDropShip);
	}

	@Override
	public void setIsExcludedFromPurchase (final boolean IsExcludedFromPurchase)
	{
		set_Value (COLUMNNAME_IsExcludedFromPurchase, IsExcludedFromPurchase);
	}

	@Override
	public boolean isExcludedFromPurchase() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludedFromPurchase);
	}

	@Override
	public void setIsExcludedFromSale (final boolean IsExcludedFromSale)
	{
		set_Value (COLUMNNAME_IsExcludedFromSale, IsExcludedFromSale);
	}

	@Override
	public boolean isExcludedFromSale() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludedFromSale);
	}

	@Override
	public void setLeadTime (final int LeadTime)
	{
		set_Value (COLUMNNAME_LeadTime, LeadTime);
	}

	@Override
	public int getLeadTime()
	{
		return get_ValueAsInt(COLUMNNAME_LeadTime);
	}

	@Override
	public void setManufacturer (final @Nullable java.lang.String Manufacturer)
	{
		set_Value (COLUMNNAME_Manufacturer, Manufacturer);
	}

	@Override
	public java.lang.String getManufacturer()
	{
		return get_ValueAsString(COLUMNNAME_Manufacturer);
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
	public void setOrder_Min (final @Nullable BigDecimal Order_Min)
	{
		set_Value (COLUMNNAME_Order_Min, Order_Min);
	}

	@Override
	public BigDecimal getOrder_Min() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Order_Min);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setOrder_Pack (final @Nullable BigDecimal Order_Pack)
	{
		set_Value (COLUMNNAME_Order_Pack, Order_Pack);
	}

	@Override
	public BigDecimal getOrder_Pack() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Order_Pack);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPicking_AgeTolerance_AfterMonths (final int Picking_AgeTolerance_AfterMonths)
	{
		set_Value (COLUMNNAME_Picking_AgeTolerance_AfterMonths, Picking_AgeTolerance_AfterMonths);
	}

	@Override
	public int getPicking_AgeTolerance_AfterMonths()
	{
		return get_ValueAsInt(COLUMNNAME_Picking_AgeTolerance_AfterMonths);
	}

	@Override
	public void setPicking_AgeTolerance_BeforeMonths (final int Picking_AgeTolerance_BeforeMonths)
	{
		set_Value (COLUMNNAME_Picking_AgeTolerance_BeforeMonths, Picking_AgeTolerance_BeforeMonths);
	}

	@Override
	public int getPicking_AgeTolerance_BeforeMonths()
	{
		return get_ValueAsInt(COLUMNNAME_Picking_AgeTolerance_BeforeMonths);
	}

	@Override
	public void setProductCategory (final @Nullable java.lang.String ProductCategory)
	{
		set_Value (COLUMNNAME_ProductCategory, ProductCategory);
	}

	@Override
	public java.lang.String getProductCategory() 
	{
		return get_ValueAsString(COLUMNNAME_ProductCategory);
	}

	@Override
	public void setProductDescription (final @Nullable java.lang.String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	@Override
	public java.lang.String getProductName() 
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setProductNo (final @Nullable java.lang.String ProductNo)
	{
		set_Value (COLUMNNAME_ProductNo, ProductNo);
	}

	@Override
	public java.lang.String getProductNo() 
	{
		return get_ValueAsString(COLUMNNAME_ProductNo);
	}

	@Override
	public void setQualityRating (final @Nullable BigDecimal QualityRating)
	{
		set_Value (COLUMNNAME_QualityRating, QualityRating);
	}

	@Override
	public BigDecimal getQualityRating() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QualityRating);
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
	public void setShelfLifeMinDays (final int ShelfLifeMinDays)
	{
		set_Value (COLUMNNAME_ShelfLifeMinDays, ShelfLifeMinDays);
	}

	@Override
	public int getShelfLifeMinDays() 
	{
		return get_ValueAsInt(COLUMNNAME_ShelfLifeMinDays);
	}

	@Override
	public void setShelfLifeMinPct (final int ShelfLifeMinPct)
	{
		set_Value (COLUMNNAME_ShelfLifeMinPct, ShelfLifeMinPct);
	}

	@Override
	public int getShelfLifeMinPct() 
	{
		return get_ValueAsInt(COLUMNNAME_ShelfLifeMinPct);
	}

	@Override
	public void setUPC (final @Nullable java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	@Override
	public java.lang.String getUPC() 
	{
		return get_ValueAsString(COLUMNNAME_UPC);
	}

	@Override
	public void setUsedForCustomer (final boolean UsedForCustomer)
	{
		set_Value (COLUMNNAME_UsedForCustomer, UsedForCustomer);
	}

	@Override
	public boolean isUsedForCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_UsedForCustomer);
	}

	@Override
	public void setUsedForVendor (final boolean UsedForVendor)
	{
		set_Value (COLUMNNAME_UsedForVendor, UsedForVendor);
	}

	@Override
	public boolean isUsedForVendor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_UsedForVendor);
	}

	@Override
	public void setVendorCategory (final @Nullable java.lang.String VendorCategory)
	{
		set_Value (COLUMNNAME_VendorCategory, VendorCategory);
	}

	@Override
	public java.lang.String getVendorCategory() 
	{
		return get_ValueAsString(COLUMNNAME_VendorCategory);
	}

	@Override
	public void setVendorProductNo (final @Nullable java.lang.String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	@Override
	public java.lang.String getVendorProductNo() 
	{
		return get_ValueAsString(COLUMNNAME_VendorProductNo);
	}
}