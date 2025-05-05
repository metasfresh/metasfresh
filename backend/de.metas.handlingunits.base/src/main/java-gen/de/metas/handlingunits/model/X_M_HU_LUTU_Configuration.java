// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_LUTU_Configuration
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_LUTU_Configuration extends org.compiere.model.PO implements I_M_HU_LUTU_Configuration, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2018209887L;

    /** Standard Constructor */
    public X_M_HU_LUTU_Configuration (final Properties ctx, final int M_HU_LUTU_Configuration_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_LUTU_Configuration_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_LUTU_Configuration (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
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
	 * HUStatus AD_Reference_ID=540478
	 * Reference name: HUStatus
	 */
	public static final int HUSTATUS_AD_Reference_ID=540478;
	/** Planning = P */
	public static final String HUSTATUS_Planning = "P";
	/** Active = A */
	public static final String HUSTATUS_Active = "A";
	/** Destroyed = D */
	public static final String HUSTATUS_Destroyed = "D";
	/** Picked = S */
	public static final String HUSTATUS_Picked = "S";
	/** Shipped = E */
	public static final String HUSTATUS_Shipped = "E";
	/** Issued = I */
	public static final String HUSTATUS_Issued = "I";
	@Override
	public void setHUStatus (final @Nullable java.lang.String HUStatus)
	{
		set_Value (COLUMNNAME_HUStatus, HUStatus);
	}

	@Override
	public java.lang.String getHUStatus() 
	{
		return get_ValueAsString(COLUMNNAME_HUStatus);
	}

	@Override
	public void setIsInfiniteQtyCU (final boolean IsInfiniteQtyCU)
	{
		set_Value (COLUMNNAME_IsInfiniteQtyCU, IsInfiniteQtyCU);
	}

	@Override
	public boolean isInfiniteQtyCU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInfiniteQtyCU);
	}

	@Override
	public void setIsInfiniteQtyLU (final boolean IsInfiniteQtyLU)
	{
		set_Value (COLUMNNAME_IsInfiniteQtyLU, IsInfiniteQtyLU);
	}

	@Override
	public boolean isInfiniteQtyLU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInfiniteQtyLU);
	}

	@Override
	public void setIsInfiniteQtyTU (final boolean IsInfiniteQtyTU)
	{
		set_Value (COLUMNNAME_IsInfiniteQtyTU, IsInfiniteQtyTU);
	}

	@Override
	public boolean isInfiniteQtyTU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInfiniteQtyTU);
	}

	@Override
	public void setM_HU_LUTU_Configuration_ID (final int M_HU_LUTU_Configuration_ID)
	{
		if (M_HU_LUTU_Configuration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_LUTU_Configuration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_LUTU_Configuration_ID, M_HU_LUTU_Configuration_ID);
	}

	@Override
	public int getM_HU_LUTU_Configuration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_LUTU_Configuration_ID);
	}

	@Override
	public void setM_HU_PI_Item_Product_ID (final int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, M_HU_PI_Item_Product_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI getM_LU_HU_PI()
	{
		return get_ValueAsPO(COLUMNNAME_M_LU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setM_LU_HU_PI(final de.metas.handlingunits.model.I_M_HU_PI M_LU_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_M_LU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, M_LU_HU_PI);
	}

	@Override
	public void setM_LU_HU_PI_ID (final int M_LU_HU_PI_ID)
	{
		if (M_LU_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_M_LU_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_M_LU_HU_PI_ID, M_LU_HU_PI_ID);
	}

	@Override
	public int getM_LU_HU_PI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_LU_HU_PI_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI_Item getM_LU_HU_PI_Item()
	{
		return get_ValueAsPO(COLUMNNAME_M_LU_HU_PI_Item_ID, de.metas.handlingunits.model.I_M_HU_PI_Item.class);
	}

	@Override
	public void setM_LU_HU_PI_Item(final de.metas.handlingunits.model.I_M_HU_PI_Item M_LU_HU_PI_Item)
	{
		set_ValueFromPO(COLUMNNAME_M_LU_HU_PI_Item_ID, de.metas.handlingunits.model.I_M_HU_PI_Item.class, M_LU_HU_PI_Item);
	}

	@Override
	public void setM_LU_HU_PI_Item_ID (final int M_LU_HU_PI_Item_ID)
	{
		if (M_LU_HU_PI_Item_ID < 1) 
			set_Value (COLUMNNAME_M_LU_HU_PI_Item_ID, null);
		else 
			set_Value (COLUMNNAME_M_LU_HU_PI_Item_ID, M_LU_HU_PI_Item_ID);
	}

	@Override
	public int getM_LU_HU_PI_Item_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_LU_HU_PI_Item_ID);
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
	public de.metas.handlingunits.model.I_M_HU_PI getM_TU_HU_PI()
	{
		return get_ValueAsPO(COLUMNNAME_M_TU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setM_TU_HU_PI(final de.metas.handlingunits.model.I_M_HU_PI M_TU_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_M_TU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, M_TU_HU_PI);
	}

	@Override
	public void setM_TU_HU_PI_ID (final int M_TU_HU_PI_ID)
	{
		if (M_TU_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_M_TU_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_M_TU_HU_PI_ID, M_TU_HU_PI_ID);
	}

	@Override
	public int getM_TU_HU_PI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_TU_HU_PI_ID);
	}

	@Override
	public void setQtyCUsPerTU (final BigDecimal QtyCUsPerTU)
	{
		set_Value (COLUMNNAME_QtyCUsPerTU, QtyCUsPerTU);
	}

	@Override
	public BigDecimal getQtyCUsPerTU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCUsPerTU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyLU (final BigDecimal QtyLU)
	{
		set_Value (COLUMNNAME_QtyLU, QtyLU);
	}

	@Override
	public BigDecimal getQtyLU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyLU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyTU (final BigDecimal QtyTU)
	{
		set_Value (COLUMNNAME_QtyTU, QtyTU);
	}

	@Override
	public BigDecimal getQtyTU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyTU);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}