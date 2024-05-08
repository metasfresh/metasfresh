// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PP_ComponentGenerator
 *  @author metasfresh (generated) 
 */
public class X_PP_ComponentGenerator extends org.compiere.model.PO implements I_PP_ComponentGenerator, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -109296013L;

    /** Standard Constructor */
    public X_PP_ComponentGenerator (final Properties ctx, final int PP_ComponentGenerator_ID, @Nullable final String trxName)
    {
      super (ctx, PP_ComponentGenerator_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_ComponentGenerator (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_JavaClass_ID (final int AD_JavaClass_ID)
	{
		if (AD_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_AD_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_AD_JavaClass_ID, AD_JavaClass_ID);
	}

	@Override
	public int getAD_JavaClass_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_JavaClass_ID);
	}

	@Override
	public org.compiere.model.I_AD_Sequence getAD_Sequence()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Sequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setAD_Sequence(final org.compiere.model.I_AD_Sequence AD_Sequence)
	{
		set_ValueFromPO(COLUMNNAME_AD_Sequence_ID, org.compiere.model.I_AD_Sequence.class, AD_Sequence);
	}

	@Override
	public void setAD_Sequence_ID (final int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_Value (COLUMNNAME_AD_Sequence_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Sequence_ID, AD_Sequence_ID);
	}

	@Override
	public int getAD_Sequence_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Sequence_ID);
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
	public void setPP_ComponentGenerator_ID (final int PP_ComponentGenerator_ID)
	{
		if (PP_ComponentGenerator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_ComponentGenerator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_ComponentGenerator_ID, PP_ComponentGenerator_ID);
	}

	@Override
	public int getPP_ComponentGenerator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_ComponentGenerator_ID);
	}
}