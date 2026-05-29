// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_PackagingContainer
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_M_PackagingContainer extends org.compiere.model.PO implements I_M_PackagingContainer, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1577506953L;

	/**
	 * Standard Constructor
	 */
	public X_M_PackagingContainer(final Properties ctx, final int M_PackagingContainer_ID, @Nullable final String trxName)
	{
		super(ctx, M_PackagingContainer_ID, trxName);
	}

	/**
	 * Load Constructor
	 */
	public X_M_PackagingContainer(final Properties ctx, final ResultSet rs, @Nullable final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Load Meta Data
	 */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_UOM_Length_ID(final int C_UOM_Length_ID)
	{
		if (C_UOM_Length_ID < 1)
			set_Value(COLUMNNAME_C_UOM_Length_ID, null);
		else
			set_Value(COLUMNNAME_C_UOM_Length_ID, C_UOM_Length_ID);
	}

	@Override
	public int getC_UOM_Length_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_Length_ID);
	}

	@Override
	public void setC_UOM_Weight_ID(final int C_UOM_Weight_ID)
	{
		if (C_UOM_Weight_ID < 1)
			set_Value(COLUMNNAME_C_UOM_Weight_ID, null);
		else
			set_Value(COLUMNNAME_C_UOM_Weight_ID, C_UOM_Weight_ID);
	}

	@Override
	public int getC_UOM_Weight_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_Weight_ID);
	}

	@Override
	public void setDescription(final @Nullable java.lang.String Description)
	{
		set_Value(COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription()
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setHeight(final @Nullable BigDecimal Height)
	{
		set_Value(COLUMNNAME_Height, Height);
	}

	@Override
	public BigDecimal getHeight()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Height);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setLength(final @Nullable BigDecimal Length)
	{
		set_Value(COLUMNNAME_Length, Length);
	}

	@Override
	public BigDecimal getLength()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Length);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setMaxVolume(final BigDecimal MaxVolume)
	{
		set_Value(COLUMNNAME_MaxVolume, MaxVolume);
	}

	@Override
	public BigDecimal getMaxVolume()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MaxVolume);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setMaxWeight(final BigDecimal MaxWeight)
	{
		set_Value(COLUMNNAME_MaxWeight, MaxWeight);
	}

	@Override
	public BigDecimal getMaxWeight()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MaxWeight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setM_PackagingContainer_ID(final int M_PackagingContainer_ID)
	{
		if (M_PackagingContainer_ID < 1)
			set_ValueNoCheck(COLUMNNAME_M_PackagingContainer_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_M_PackagingContainer_ID, M_PackagingContainer_ID);
	}

	@Override
	public int getM_PackagingContainer_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_PackagingContainer_ID);
	}

	@Override
	public void setM_Product_ID(final int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_Value(COLUMNNAME_M_Product_ID, null);
		else
			set_Value(COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setName(final java.lang.String Name)
	{
		set_Value(COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName()
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setValue(final @Nullable java.lang.String Value)
	{
		set_Value(COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue()
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setWidth(final @Nullable BigDecimal Width)
	{
		set_Value(COLUMNNAME_Width, Width);
	}

	@Override
	public BigDecimal getWidth()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Width);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}