// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Attribute extends org.compiere.model.PO implements I_M_HU_Attribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 489210147L;

    /** Standard Constructor */
    public X_M_HU_Attribute (final Properties ctx, final int M_HU_Attribute_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Attribute_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Attribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Attribute_ID (final int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, M_Attribute_ID);
	}

	@Override
	public int getM_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Attribute_ID);
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
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_HU_PI_Attribute_ID (final int M_HU_PI_Attribute_ID)
	{
		if (M_HU_PI_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Attribute_ID, M_HU_PI_Attribute_ID);
	}

	@Override
	public int getM_HU_PI_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Attribute_ID);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setValueDate (final @Nullable java.sql.Timestamp ValueDate)
	{
		set_Value (COLUMNNAME_ValueDate, ValueDate);
	}

	@Override
	public java.sql.Timestamp getValueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValueDate);
	}

	@Override
	public void setValueDateInitial (final @Nullable java.sql.Timestamp ValueDateInitial)
	{
		set_Value (COLUMNNAME_ValueDateInitial, ValueDateInitial);
	}

	@Override
	public java.sql.Timestamp getValueDateInitial() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValueDateInitial);
	}

	@Override
	public void setValueInitial (final @Nullable java.lang.String ValueInitial)
	{
		set_ValueNoCheck (COLUMNNAME_ValueInitial, ValueInitial);
	}

	@Override
	public java.lang.String getValueInitial() 
	{
		return get_ValueAsString(COLUMNNAME_ValueInitial);
	}

	@Override
	public void setValueNumber (final @Nullable BigDecimal ValueNumber)
	{
		set_Value (COLUMNNAME_ValueNumber, ValueNumber);
	}

	@Override
	public BigDecimal getValueNumber() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ValueNumber);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setValueNumberInitial (final @Nullable BigDecimal ValueNumberInitial)
	{
		set_ValueNoCheck (COLUMNNAME_ValueNumberInitial, ValueNumberInitial);
	}

	@Override
	public BigDecimal getValueNumberInitial() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ValueNumberInitial);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}