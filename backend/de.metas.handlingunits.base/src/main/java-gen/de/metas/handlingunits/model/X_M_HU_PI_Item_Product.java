// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_PI_Item_Product
 *  @author metasfresh (generated) 
 */
public class X_M_HU_PI_Item_Product extends org.compiere.model.PO implements I_M_HU_PI_Item_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1589174833L;

    /** Standard Constructor */
    public X_M_HU_PI_Item_Product (final Properties ctx, final int M_HU_PI_Item_Product_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_PI_Item_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_PI_Item_Product (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
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
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setEAN_TU (final java.lang.String EAN_TU)
	{
		set_Value (COLUMNNAME_EAN_TU, EAN_TU);
	}

	@Override
	public java.lang.String getEAN_TU() 
	{
		return get_ValueAsString(COLUMNNAME_EAN_TU);
	}

	@Override
	public void setGTIN (final java.lang.String GTIN)
	{
		set_Value (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public java.lang.String getGTIN() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN);
	}

	@Override
	public void setGTIN_LU_PackingMaterial_Fallback (final java.lang.String GTIN_LU_PackingMaterial_Fallback)
	{
		set_Value (COLUMNNAME_GTIN_LU_PackingMaterial_Fallback, GTIN_LU_PackingMaterial_Fallback);
	}

	@Override
	public java.lang.String getGTIN_LU_PackingMaterial_Fallback() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN_LU_PackingMaterial_Fallback);
	}

	@Override
	public void setIsAllowAnyProduct (final boolean IsAllowAnyProduct)
	{
		set_Value (COLUMNNAME_IsAllowAnyProduct, IsAllowAnyProduct);
	}

	@Override
	public boolean isAllowAnyProduct() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowAnyProduct);
	}

	@Override
	public void setIsDefaultForProduct (final boolean IsDefaultForProduct)
	{
		set_Value (COLUMNNAME_IsDefaultForProduct, IsDefaultForProduct);
	}

	@Override
	public boolean isDefaultForProduct() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefaultForProduct);
	}

	@Override
	public void setIsInfiniteCapacity (final boolean IsInfiniteCapacity)
	{
		set_Value (COLUMNNAME_IsInfiniteCapacity, IsInfiniteCapacity);
	}

	@Override
	public boolean isInfiniteCapacity() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInfiniteCapacity);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PackagingCode getM_HU_PackagingCode_LU_Fallback()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID, de.metas.handlingunits.model.I_M_HU_PackagingCode.class);
	}

	@Override
	public void setM_HU_PackagingCode_LU_Fallback(final de.metas.handlingunits.model.I_M_HU_PackagingCode M_HU_PackagingCode_LU_Fallback)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID, de.metas.handlingunits.model.I_M_HU_PackagingCode.class, M_HU_PackagingCode_LU_Fallback);
	}

	@Override
	public void setM_HU_PackagingCode_LU_Fallback_ID (final int M_HU_PackagingCode_LU_Fallback_ID)
	{
		if (M_HU_PackagingCode_LU_Fallback_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID, M_HU_PackagingCode_LU_Fallback_ID);
	}

	@Override
	public int getM_HU_PackagingCode_LU_Fallback_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI_Item getM_HU_PI_Item()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Item_ID, de.metas.handlingunits.model.I_M_HU_PI_Item.class);
	}

	@Override
	public void setM_HU_PI_Item(final de.metas.handlingunits.model.I_M_HU_PI_Item M_HU_PI_Item)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_Item_ID, de.metas.handlingunits.model.I_M_HU_PI_Item.class, M_HU_PI_Item);
	}

	@Override
	public void setM_HU_PI_Item_ID (final int M_HU_PI_Item_ID)
	{
		if (M_HU_PI_Item_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_ID, M_HU_PI_Item_ID);
	}

	@Override
	public int getM_HU_PI_Item_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_ID);
	}

	@Override
	public void setM_HU_PI_Item_Product_ID (final int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_Product_ID, M_HU_PI_Item_Product_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_ID);
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
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setQty (final BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUPC (final java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	@Override
	public java.lang.String getUPC() 
	{
		return get_ValueAsString(COLUMNNAME_UPC);
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

	@Override
	public void setValidTo (final java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}
}