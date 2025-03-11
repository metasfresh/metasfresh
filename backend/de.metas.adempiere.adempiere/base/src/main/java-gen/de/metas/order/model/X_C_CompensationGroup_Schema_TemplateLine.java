// Generated Model - DO NOT CHANGE
package de.metas.order.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_CompensationGroup_Schema_TemplateLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_CompensationGroup_Schema_TemplateLine extends org.compiere.model.PO implements I_C_CompensationGroup_Schema_TemplateLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -655535731L;

    /** Standard Constructor */
    public X_C_CompensationGroup_Schema_TemplateLine (final Properties ctx, final int C_CompensationGroup_Schema_TemplateLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_CompensationGroup_Schema_TemplateLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_CompensationGroup_Schema_TemplateLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, null);
		else 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, C_CompensationGroup_Schema_ID);
	}

	@Override
	public int getC_CompensationGroup_Schema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_Schema_ID);
	}

	@Override
	public void setC_CompensationGroup_Schema_TemplateLine_ID (final int C_CompensationGroup_Schema_TemplateLine_ID)
	{
		if (C_CompensationGroup_Schema_TemplateLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_Schema_TemplateLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_Schema_TemplateLine_ID, C_CompensationGroup_Schema_TemplateLine_ID);
	}

	@Override
	public int getC_CompensationGroup_Schema_TemplateLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_Schema_TemplateLine_ID);
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
	public void setIsSkipInvoicing (final boolean IsSkipInvoicing)
	{
		set_Value (COLUMNNAME_IsSkipInvoicing, IsSkipInvoicing);
	}

	@Override
	public boolean isSkipInvoicing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSkipInvoicing);
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
	public void setQty (final BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
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
}