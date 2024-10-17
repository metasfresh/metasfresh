// Generated Model - DO NOT CHANGE
package de.metas.pos.repository.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS_ProductCategory_Assignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS_ProductCategory_Assignment extends org.compiere.model.PO implements I_C_POS_ProductCategory_Assignment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 819421304L;

    /** Standard Constructor */
    public X_C_POS_ProductCategory_Assignment (final Properties ctx, final int C_POS_ProductCategory_Assignment_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_ProductCategory_Assignment_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS_ProductCategory_Assignment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.pos.repository.model.I_C_POS_ProductCategory getC_POS_ProductCategory()
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_ProductCategory_ID, de.metas.pos.repository.model.I_C_POS_ProductCategory.class);
	}

	@Override
	public void setC_POS_ProductCategory(final de.metas.pos.repository.model.I_C_POS_ProductCategory C_POS_ProductCategory)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_ProductCategory_ID, de.metas.pos.repository.model.I_C_POS_ProductCategory.class, C_POS_ProductCategory);
	}

	@Override
	public void setC_POS_ProductCategory_ID (final int C_POS_ProductCategory_ID)
	{
		if (C_POS_ProductCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_ProductCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_ProductCategory_ID, C_POS_ProductCategory_ID);
	}

	@Override
	public int getC_POS_ProductCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_ProductCategory_ID);
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