<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

=======
// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_M_InOutLine_HU_IPA_SSCC18_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_M_InOutLine_HU_IPA_SSCC18_v extends org.compiere.model.PO implements I_EDI_M_InOutLine_HU_IPA_SSCC18_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -701467120L;

    /** Standard Constructor */
    public X_EDI_M_InOutLine_HU_IPA_SSCC18_v (Properties ctx, int EDI_M_InOutLine_HU_IPA_SSCC18_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_M_InOutLine_HU_IPA_SSCC18_v extends org.compiere.model.PO implements I_EDI_M_InOutLine_HU_IPA_SSCC18_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1071493390L;

    /** Standard Constructor */
    public X_EDI_M_InOutLine_HU_IPA_SSCC18_v (final Properties ctx, final int EDI_M_InOutLine_HU_IPA_SSCC18_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_M_InOutLine_HU_IPA_SSCC18_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_M_InOutLine_HU_IPA_SSCC18_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_M_InOutLine_HU_IPA_SSCC18_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
<<<<<<< HEAD
	protected org.compiere.model.POInfo initPO(Properties ctx)
=======
	protected org.compiere.model.POInfo initPO(final Properties ctx)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
<<<<<<< HEAD
	public void setAttributeName (java.lang.String AttributeName)
=======
	public void setAttributeName (final @Nullable java.lang.String AttributeName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	@Override
	public java.lang.String getAttributeName() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_AttributeName);
	}

	@Override
	public void setM_HU_ID (int M_HU_ID)
=======
		return get_ValueAsString(COLUMNNAME_AttributeName);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
=======
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
<<<<<<< HEAD
	public void setM_InOutLine_ID (int M_InOutLine_ID)
=======
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
=======
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}

	@Override
<<<<<<< HEAD
	public void setValue (java.lang.String Value)
=======
	public void setValue (final @Nullable java.lang.String Value)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Value);
=======
		return get_ValueAsString(COLUMNNAME_Value);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}