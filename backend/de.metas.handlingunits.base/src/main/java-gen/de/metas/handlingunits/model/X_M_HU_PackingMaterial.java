// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_PackingMaterial
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_PackingMaterial extends org.compiere.model.PO implements I_M_HU_PackingMaterial, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 439819935L;

    /** Standard Constructor */
    public X_M_HU_PackingMaterial (final Properties ctx, final int M_HU_PackingMaterial_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_PackingMaterial_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_PackingMaterial (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAllowedPackingVolume (final @Nullable BigDecimal AllowedPackingVolume)
	{
		set_Value (COLUMNNAME_AllowedPackingVolume, AllowedPackingVolume);
	}

	@Override
	public BigDecimal getAllowedPackingVolume() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AllowedPackingVolume);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAllowedPackingWeight (final @Nullable BigDecimal AllowedPackingWeight)
	{
		set_Value (COLUMNNAME_AllowedPackingWeight, AllowedPackingWeight);
	}

	@Override
	public BigDecimal getAllowedPackingWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AllowedPackingWeight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_UOM_Dimension_ID (final int C_UOM_Dimension_ID)
	{
		if (C_UOM_Dimension_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Dimension_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Dimension_ID, C_UOM_Dimension_ID);
	}

	@Override
	public int getC_UOM_Dimension_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_Dimension_ID);
	}

	@Override
	public void setC_UOM_Weight_ID (final int C_UOM_Weight_ID)
	{
		if (C_UOM_Weight_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Weight_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Weight_ID, C_UOM_Weight_ID);
	}

	@Override
	public int getC_UOM_Weight_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_Weight_ID);
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
	public void setExcessVolumeTolerance (final @Nullable BigDecimal ExcessVolumeTolerance)
	{
		set_Value (COLUMNNAME_ExcessVolumeTolerance, ExcessVolumeTolerance);
	}

	@Override
	public BigDecimal getExcessVolumeTolerance() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ExcessVolumeTolerance);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setExcessWeightTolerance (final @Nullable BigDecimal ExcessWeightTolerance)
	{
		set_Value (COLUMNNAME_ExcessWeightTolerance, ExcessWeightTolerance);
	}

	@Override
	public BigDecimal getExcessWeightTolerance() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ExcessWeightTolerance);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFillingLevel (final @Nullable BigDecimal FillingLevel)
	{
		set_Value (COLUMNNAME_FillingLevel, FillingLevel);
	}

	@Override
	public BigDecimal getFillingLevel() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FillingLevel);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setHeight (final @Nullable BigDecimal Height)
	{
		set_Value (COLUMNNAME_Height, Height);
	}

	@Override
	public BigDecimal getHeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Height);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsClosed (final boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, IsClosed);
	}

	@Override
	public boolean isClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosed);
	}

	@Override
	public void setLength (final @Nullable BigDecimal Length)
	{
		set_Value (COLUMNNAME_Length, Length);
	}

	@Override
	public BigDecimal getLength() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Length);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setM_HU_PackingMaterial_ID (final int M_HU_PackingMaterial_ID)
	{
		if (M_HU_PackingMaterial_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PackingMaterial_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PackingMaterial_ID, M_HU_PackingMaterial_ID);
	}

	@Override
	public int getM_HU_PackingMaterial_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PackingMaterial_ID);
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
	public void setStackabilityFactor (final int StackabilityFactor)
	{
		set_Value (COLUMNNAME_StackabilityFactor, StackabilityFactor);
	}

	@Override
	public int getStackabilityFactor() 
	{
		return get_ValueAsInt(COLUMNNAME_StackabilityFactor);
	}

	@Override
	public void setWidth (final @Nullable BigDecimal Width)
	{
		set_Value (COLUMNNAME_Width, Width);
	}

	@Override
	public BigDecimal getWidth() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Width);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}