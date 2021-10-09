// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Trx_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Trx_Attribute extends org.compiere.model.PO implements I_M_HU_Trx_Attribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1149433268L;

    /** Standard Constructor */
    public X_M_HU_Trx_Attribute (final Properties ctx, final int M_HU_Trx_Attribute_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Trx_Attribute_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Trx_Attribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_Value (COLUMNNAME_M_HU_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_Attribute_ID, M_HU_Attribute_ID);
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
	public void setM_HU_Trx_Attribute_ID (final int M_HU_Trx_Attribute_ID)
	{
		if (M_HU_Trx_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Attribute_ID, M_HU_Trx_Attribute_ID);
	}

	@Override
	public int getM_HU_Trx_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Trx_Attribute_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Trx_Hdr getM_HU_Trx_Hdr()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Trx_Hdr_ID, de.metas.handlingunits.model.I_M_HU_Trx_Hdr.class);
	}

	@Override
	public void setM_HU_Trx_Hdr(final de.metas.handlingunits.model.I_M_HU_Trx_Hdr M_HU_Trx_Hdr)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Trx_Hdr_ID, de.metas.handlingunits.model.I_M_HU_Trx_Hdr.class, M_HU_Trx_Hdr);
	}

	@Override
	public void setM_HU_Trx_Hdr_ID (final int M_HU_Trx_Hdr_ID)
	{
		if (M_HU_Trx_Hdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, M_HU_Trx_Hdr_ID);
	}

	@Override
	public int getM_HU_Trx_Hdr_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Trx_Hdr_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Trx_Line getM_HU_Trx_Line()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	}

	@Override
	public void setM_HU_Trx_Line(final de.metas.handlingunits.model.I_M_HU_Trx_Line M_HU_Trx_Line)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class, M_HU_Trx_Line);
	}

	@Override
	public void setM_HU_Trx_Line_ID (final int M_HU_Trx_Line_ID)
	{
		if (M_HU_Trx_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, M_HU_Trx_Line_ID);
	}

	@Override
	public int getM_HU_Trx_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Trx_Line_ID);
	}

	/** 
	 * Operation AD_Reference_ID=540410
	 * Reference name: M_HU_PI_Attribute_Operation
	 */
	public static final int OPERATION_AD_Reference_ID=540410;
	/** Save = SAVE */
	public static final String OPERATION_Save = "SAVE";
	/** Drop = DROP */
	public static final String OPERATION_Drop = "DROP";
	@Override
	public void setOperation (final java.lang.String Operation)
	{
		set_Value (COLUMNNAME_Operation, Operation);
	}

	@Override
	public java.lang.String getOperation() 
	{
		return get_ValueAsString(COLUMNNAME_Operation);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
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
		set_Value (COLUMNNAME_ValueInitial, ValueInitial);
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
		set_Value (COLUMNNAME_ValueNumberInitial, ValueNumberInitial);
	}

	@Override
	public BigDecimal getValueNumberInitial() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ValueNumberInitial);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}