// Generated Model - DO NOT CHANGE
package de.metas.order.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_CompensationGroup_SchemaLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_CompensationGroup_SchemaLine extends org.compiere.model.PO implements I_C_CompensationGroup_SchemaLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1218705922L;

    /** Standard Constructor */
    public X_C_CompensationGroup_SchemaLine (final Properties ctx, final int C_CompensationGroup_SchemaLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_CompensationGroup_SchemaLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_CompensationGroup_SchemaLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBreakValue (final @Nullable BigDecimal BreakValue)
	{
		set_Value (COLUMNNAME_BreakValue, BreakValue);
	}

	@Override
	public BigDecimal getBreakValue() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_BreakValue);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema()
	{
		return get_ValueAsPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class);
	}

	@Override
	public void setC_CompensationGroup_Schema(final de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema)
	{
		set_ValueFromPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class, C_CompensationGroup_Schema);
	}

	@Override
	public void setC_CompensationGroup_Schema_ID (final int C_CompensationGroup_Schema_ID)
	{
		if (C_CompensationGroup_Schema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_Schema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_Schema_ID, C_CompensationGroup_Schema_ID);
	}

	@Override
	public int getC_CompensationGroup_Schema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_Schema_ID);
	}

	@Override
	public void setC_CompensationGroup_SchemaLine_ID (final int C_CompensationGroup_SchemaLine_ID)
	{
		if (C_CompensationGroup_SchemaLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_SchemaLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_SchemaLine_ID, C_CompensationGroup_SchemaLine_ID);
	}

	@Override
	public int getC_CompensationGroup_SchemaLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_SchemaLine_ID);
	}

	@Override
	public void setC_Flatrate_Conditions_ID (final int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, C_Flatrate_Conditions_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_ID);
	}

	@Override
	public void setCompleteOrderDiscount (final @Nullable BigDecimal CompleteOrderDiscount)
	{
		set_Value (COLUMNNAME_CompleteOrderDiscount, CompleteOrderDiscount);
	}

	@Override
	public BigDecimal getCompleteOrderDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CompleteOrderDiscount);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	/** 
	 * Type AD_Reference_ID=540836
	 * Reference name: C_CompensationGroup_SchemaLine_Type
	 */
	public static final int TYPE_AD_Reference_ID=540836;
	/** Revenue = R */
	public static final String TYPE_Revenue = "R";
	/** Flatrate = F */
	public static final String TYPE_Flatrate = "F";
	@Override
	public void setType (final @Nullable java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}
}