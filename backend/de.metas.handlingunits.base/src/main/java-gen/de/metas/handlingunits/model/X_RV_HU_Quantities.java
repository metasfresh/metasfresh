// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for RV_HU_Quantities
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_RV_HU_Quantities extends org.compiere.model.PO implements I_RV_HU_Quantities, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -866062641L;

    /** Standard Constructor */
    public X_RV_HU_Quantities (final Properties ctx, final int RV_HU_Quantities_ID, @Nullable final String trxName)
    {
      super (ctx, RV_HU_Quantities_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_HU_Quantities (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setASIKey (final @Nullable java.lang.String ASIKey)
	{
		set_Value (COLUMNNAME_ASIKey, ASIKey);
	}

	@Override
	public java.lang.String getASIKey() 
	{
		return get_ValueAsString(COLUMNNAME_ASIKey);
	}

	@Override
	public void setASIKeyName (final @Nullable java.lang.String ASIKeyName)
	{
		set_Value (COLUMNNAME_ASIKeyName, ASIKeyName);
	}

	@Override
	public java.lang.String getASIKeyName() 
	{
		return get_ValueAsString(COLUMNNAME_ASIKeyName);
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
	public void setIsPurchased (final boolean IsPurchased)
	{
		set_Value (COLUMNNAME_IsPurchased, IsPurchased);
	}

	@Override
	public boolean isPurchased() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPurchased);
	}

	@Override
	public void setIsSold (final boolean IsSold)
	{
		set_Value (COLUMNNAME_IsSold, IsSold);
	}

	@Override
	public boolean isSold() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSold);
	}

	@Override
	public void setM_HU_PI_Item_Product (final @Nullable java.lang.String M_HU_PI_Item_Product)
	{
		set_Value (COLUMNNAME_M_HU_PI_Item_Product, M_HU_PI_Item_Product);
	}

	@Override
	public java.lang.String getM_HU_PI_Item_Product() 
	{
		return get_ValueAsString(COLUMNNAME_M_HU_PI_Item_Product);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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
	public void setProductValue (final @Nullable java.lang.String ProductValue)
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	@Override
	public java.lang.String getProductValue() 
	{
		return get_ValueAsString(COLUMNNAME_ProductValue);
	}

	@Override
	public void setQtyAvailable (final @Nullable BigDecimal QtyAvailable)
	{
		set_Value (COLUMNNAME_QtyAvailable, QtyAvailable);
	}

	@Override
	public BigDecimal getQtyAvailable() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyAvailable);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOnHand (final @Nullable BigDecimal QtyOnHand)
	{
		set_Value (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	@Override
	public BigDecimal getQtyOnHand() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOnHand);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (final @Nullable BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (final @Nullable BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}