// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Product_Exclude_FlatrateConditions
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_Exclude_FlatrateConditions extends org.compiere.model.PO implements I_M_Product_Exclude_FlatrateConditions, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 816936915L;

    /** Standard Constructor */
    public X_M_Product_Exclude_FlatrateConditions (final Properties ctx, final int M_Product_Exclude_FlatrateConditions_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_Exclude_FlatrateConditions_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_Exclude_FlatrateConditions (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Product_Exclude_FlatrateConditions_ID (final int M_Product_Exclude_FlatrateConditions_ID)
	{
		if (M_Product_Exclude_FlatrateConditions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Exclude_FlatrateConditions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Exclude_FlatrateConditions_ID, M_Product_Exclude_FlatrateConditions_ID);
	}

	@Override
	public int getM_Product_Exclude_FlatrateConditions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Exclude_FlatrateConditions_ID);
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
}