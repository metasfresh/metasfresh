// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_Relationship
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_Relationship extends org.compiere.model.PO implements I_M_Product_Relationship, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1246567907L;

    /** Standard Constructor */
    public X_M_Product_Relationship (final Properties ctx, final int M_Product_Relationship_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_Relationship_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_Relationship (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AD_RelationType_ID AD_Reference_ID=541357
	 * Reference name: Product Relation Types
	 */
	public static final int AD_RELATIONTYPE_ID_AD_Reference_ID=541357;
	/** Parent Product = Parent */
	public static final String AD_RELATIONTYPE_ID_ParentProduct = "Parent";
	@Override
	public void setAD_RelationType_ID (final java.lang.String AD_RelationType_ID)
	{
		set_Value (COLUMNNAME_AD_RelationType_ID, AD_RelationType_ID);
	}

	@Override
	public java.lang.String getAD_RelationType_ID() 
	{
		return get_ValueAsString(COLUMNNAME_AD_RelationType_ID);
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
	public void setM_Product_Relationship_ID (final int M_Product_Relationship_ID)
	{
		if (M_Product_Relationship_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Relationship_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Relationship_ID, M_Product_Relationship_ID);
	}

	@Override
	public int getM_Product_Relationship_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Relationship_ID);
	}

	@Override
	public void setRelatedProduct_ID (final int RelatedProduct_ID)
	{
		if (RelatedProduct_ID < 1) 
			set_Value (COLUMNNAME_RelatedProduct_ID, null);
		else 
			set_Value (COLUMNNAME_RelatedProduct_ID, RelatedProduct_ID);
	}

	@Override
	public int getRelatedProduct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_RelatedProduct_ID);
	}
}