// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Product_Category_BoilerPlate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Product_Category_BoilerPlate extends org.compiere.model.PO implements I_AD_Product_Category_BoilerPlate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -237579600L;

    /** Standard Constructor */
    public X_AD_Product_Category_BoilerPlate (final Properties ctx, final int AD_Product_Category_BoilerPlate_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Product_Category_BoilerPlate_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Product_Category_BoilerPlate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Product_Category_BoilerPlate_ID (final int AD_Product_Category_BoilerPlate_ID)
	{
		if (AD_Product_Category_BoilerPlate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Product_Category_BoilerPlate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Product_Category_BoilerPlate_ID, AD_Product_Category_BoilerPlate_ID);
	}

	@Override
	public int getAD_Product_Category_BoilerPlate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Product_Category_BoilerPlate_ID);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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