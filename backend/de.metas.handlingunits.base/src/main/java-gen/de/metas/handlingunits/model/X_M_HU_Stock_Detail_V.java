// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Stock_Detail_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Stock_Detail_V extends org.compiere.model.PO implements I_M_HU_Stock_Detail_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -683013165L;

    /** Standard Constructor */
    public X_M_HU_Stock_Detail_V (final Properties ctx, final int M_HU_Stock_Detail_V_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Stock_Detail_V_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Stock_Detail_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAttributeValue (final @Nullable java.lang.String AttributeValue)
	{
		set_ValueNoCheck (COLUMNNAME_AttributeValue, AttributeValue);
	}

	@Override
	public java.lang.String getAttributeValue() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeValue);
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
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
		set_ValueNoCheck (COLUMNNAME_HUStatus, HUStatus);
	}

	@Override
	public java.lang.String getHUStatus() 
	{
		return get_ValueAsString(COLUMNNAME_HUStatus);
	}

	@Override
	public void setM_Attribute_ID (final int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, M_Attribute_ID);
	}

	@Override
	public int getM_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Attribute_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Attribute getM_HU_Attribute()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Attribute_ID, de.metas.handlingunits.model.I_M_HU_Attribute.class);
	}

	@Override
	public void setM_HU_Attribute(final de.metas.handlingunits.model.I_M_HU_Attribute M_HU_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Attribute_ID, de.metas.handlingunits.model.I_M_HU_Attribute.class, M_HU_Attribute);
	}

	@Override
	public void setM_HU_Attribute_ID (final int M_HU_Attribute_ID)
	{
		if (M_HU_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Attribute_ID, M_HU_Attribute_ID);
	}

	@Override
	public int getM_HU_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Attribute_ID);
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
	public de.metas.handlingunits.model.I_M_HU_Storage getM_HU_Storage()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Storage_ID, de.metas.handlingunits.model.I_M_HU_Storage.class);
	}

	@Override
	public void setM_HU_Storage(final de.metas.handlingunits.model.I_M_HU_Storage M_HU_Storage)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Storage_ID, de.metas.handlingunits.model.I_M_HU_Storage.class, M_HU_Storage);
	}

	@Override
	public void setM_HU_Storage_ID (final int M_HU_Storage_ID)
	{
		if (M_HU_Storage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Storage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Storage_ID, M_HU_Storage_ID);
	}

	@Override
	public int getM_HU_Storage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Storage_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
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
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}