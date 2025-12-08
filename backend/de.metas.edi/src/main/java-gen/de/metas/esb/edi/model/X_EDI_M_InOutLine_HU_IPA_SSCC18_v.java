// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_M_InOutLine_HU_IPA_SSCC18_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_M_InOutLine_HU_IPA_SSCC18_v extends org.compiere.model.PO implements I_EDI_M_InOutLine_HU_IPA_SSCC18_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1071493390L;

    /** Standard Constructor */
    public X_EDI_M_InOutLine_HU_IPA_SSCC18_v (final Properties ctx, final int EDI_M_InOutLine_HU_IPA_SSCC18_v_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_M_InOutLine_HU_IPA_SSCC18_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_M_InOutLine_HU_IPA_SSCC18_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAttributeName (final @Nullable java.lang.String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	@Override
	public java.lang.String getAttributeName() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeName);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
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
}