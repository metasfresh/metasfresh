// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PP_ComponentGenerator_Param
 *  @author metasfresh (generated) 
 */
public class X_PP_ComponentGenerator_Param extends org.compiere.model.PO implements I_PP_ComponentGenerator_Param, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1661195109L;

    /** Standard Constructor */
    public X_PP_ComponentGenerator_Param (final Properties ctx, final int PP_ComponentGenerator_Param_ID, @Nullable final String trxName)
    {
      super (ctx, PP_ComponentGenerator_Param_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_ComponentGenerator_Param (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setParamValue (final java.lang.String ParamValue)
	{
		set_Value (COLUMNNAME_ParamValue, ParamValue);
	}

	@Override
	public java.lang.String getParamValue() 
	{
		return get_ValueAsString(COLUMNNAME_ParamValue);
	}

	@Override
	public org.compiere.model.I_PP_ComponentGenerator getPP_ComponentGenerator()
	{
		return get_ValueAsPO(COLUMNNAME_PP_ComponentGenerator_ID, org.compiere.model.I_PP_ComponentGenerator.class);
	}

	@Override
	public void setPP_ComponentGenerator(final org.compiere.model.I_PP_ComponentGenerator PP_ComponentGenerator)
	{
		set_ValueFromPO(COLUMNNAME_PP_ComponentGenerator_ID, org.compiere.model.I_PP_ComponentGenerator.class, PP_ComponentGenerator);
	}

	@Override
	public void setPP_ComponentGenerator_ID (final int PP_ComponentGenerator_ID)
	{
		if (PP_ComponentGenerator_ID < 1) 
			set_Value (COLUMNNAME_PP_ComponentGenerator_ID, null);
		else 
			set_Value (COLUMNNAME_PP_ComponentGenerator_ID, PP_ComponentGenerator_ID);
	}

	@Override
	public int getPP_ComponentGenerator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_ComponentGenerator_ID);
	}

	@Override
	public void setPP_ComponentGenerator_Param_ID (final int PP_ComponentGenerator_Param_ID)
	{
		if (PP_ComponentGenerator_Param_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_ComponentGenerator_Param_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_ComponentGenerator_Param_ID, PP_ComponentGenerator_Param_ID);
	}

	@Override
	public int getPP_ComponentGenerator_Param_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_ComponentGenerator_Param_ID);
	}
}