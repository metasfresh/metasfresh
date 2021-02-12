// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_PI_Item
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_PI_Item extends org.compiere.model.PO implements I_M_HU_PI_Item, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1927581782L;

    /** Standard Constructor */
    public X_M_HU_PI_Item (final Properties ctx, final int M_HU_PI_Item_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_PI_Item_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_PI_Item (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.handlingunits.model.I_M_HU_PI getIncluded_HU_PI()
	{
		return get_ValueAsPO(COLUMNNAME_Included_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setIncluded_HU_PI(final de.metas.handlingunits.model.I_M_HU_PI Included_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_Included_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, Included_HU_PI);
	}

	@Override
	public void setIncluded_HU_PI_ID (final int Included_HU_PI_ID)
	{
		if (Included_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_Included_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_Included_HU_PI_ID, Included_HU_PI_ID);
	}

	@Override
	public int getIncluded_HU_PI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Included_HU_PI_ID);
	}

	@Override
	public void setIsAllowDirectlyOnPM (final boolean IsAllowDirectlyOnPM)
	{
		set_Value (COLUMNNAME_IsAllowDirectlyOnPM, IsAllowDirectlyOnPM);
	}

	@Override
	public boolean isAllowDirectlyOnPM() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowDirectlyOnPM);
	}

	/** 
	 * ItemType AD_Reference_ID=540395
	 * Reference name: M_HU_PI_Item_ItemType
	 */
	public static final int ITEMTYPE_AD_Reference_ID=540395;
	/** Material = MI */
	public static final String ITEMTYPE_Material = "MI";
	/** PackingMaterial = PM */
	public static final String ITEMTYPE_PackingMaterial = "PM";
	/** HandlingUnit = HU */
	public static final String ITEMTYPE_HandlingUnit = "HU";
	@Override
	public void setItemType (final java.lang.String ItemType)
	{
		set_Value (COLUMNNAME_ItemType, ItemType);
	}

	@Override
	public java.lang.String getItemType() 
	{
		return get_ValueAsString(COLUMNNAME_ItemType);
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
	public de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class);
	}

	@Override
	public void setM_HU_PI_Version(final de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class, M_HU_PI_Version);
	}

	@Override
	public void setM_HU_PI_Version_ID (final int M_HU_PI_Version_ID)
	{
		if (M_HU_PI_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Version_ID, M_HU_PI_Version_ID);
	}

	@Override
	public int getM_HU_PI_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Version_ID);
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