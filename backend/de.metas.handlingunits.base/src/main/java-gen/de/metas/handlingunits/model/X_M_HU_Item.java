// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Item
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Item extends org.compiere.model.PO implements I_M_HU_Item, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 604754548L;

    /** Standard Constructor */
    public X_M_HU_Item (final Properties ctx, final int M_HU_Item_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Item_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Item (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * ItemType AD_Reference_ID=540699
	 * Reference name: M_HU_Item_ItemType
	 */
	public static final int ITEMTYPE_AD_Reference_ID=540699;
	/** HandlingUnit = HU */
	public static final String ITEMTYPE_HandlingUnit = "HU";
	/** Material = MI */
	public static final String ITEMTYPE_Material = "MI";
	/** PackingMaterial = PM */
	public static final String ITEMTYPE_PackingMaterial = "PM";
	/** HUAggregate = HA */
	public static final String ITEMTYPE_HUAggregate = "HA";
	@Override
	public void setItemType (final @Nullable java.lang.String ItemType)
	{
		set_Value (COLUMNNAME_ItemType, ItemType);
	}

	@Override
	public java.lang.String getItemType() 
	{
		return get_ValueAsString(COLUMNNAME_ItemType);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(final de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_HU_Item_ID (final int M_HU_Item_ID)
	{
		if (M_HU_Item_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Item_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Item_ID, M_HU_Item_ID);
	}

	@Override
	public int getM_HU_Item_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Item_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PackingMaterial getM_HU_PackingMaterial()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PackingMaterial_ID, de.metas.handlingunits.model.I_M_HU_PackingMaterial.class);
	}

	@Override
	public void setM_HU_PackingMaterial(final de.metas.handlingunits.model.I_M_HU_PackingMaterial M_HU_PackingMaterial)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PackingMaterial_ID, de.metas.handlingunits.model.I_M_HU_PackingMaterial.class, M_HU_PackingMaterial);
	}

	@Override
	public void setM_HU_PackingMaterial_ID (final int M_HU_PackingMaterial_ID)
	{
		if (M_HU_PackingMaterial_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PackingMaterial_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PackingMaterial_ID, M_HU_PackingMaterial_ID);
	}

	@Override
	public int getM_HU_PackingMaterial_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PackingMaterial_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_ID, M_HU_PI_Item_ID);
	}

	@Override
	public int getM_HU_PI_Item_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_ID);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}