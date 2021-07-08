// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_DiscountSchemaBreak_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_DiscountSchemaBreak_V extends org.compiere.model.PO implements I_M_DiscountSchemaBreak_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1372859297L;

    /** Standard Constructor */
    public X_M_DiscountSchemaBreak_V (final Properties ctx, final int M_DiscountSchemaBreak_V_ID, @Nullable final String trxName)
    {
      super (ctx, M_DiscountSchemaBreak_V_ID, trxName);
    }

    /** Load Constructor */
    public X_M_DiscountSchemaBreak_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema()
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setM_DiscountSchema(final org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, M_DiscountSchema);
	}

	@Override
	public void setM_DiscountSchema_ID (final int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, M_DiscountSchema_ID);
	}

	@Override
	public int getM_DiscountSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_ID);
	}

	@Override
	public void setM_DiscountSchemaBreak_V_ID (final int M_DiscountSchemaBreak_V_ID)
	{
		if (M_DiscountSchemaBreak_V_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_V_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaBreak_V_ID, M_DiscountSchemaBreak_V_ID);
	}

	@Override
	public int getM_DiscountSchemaBreak_V_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchemaBreak_V_ID);
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
}