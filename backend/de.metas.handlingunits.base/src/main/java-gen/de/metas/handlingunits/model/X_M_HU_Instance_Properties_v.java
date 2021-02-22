// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Instance_Properties_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Instance_Properties_v extends org.compiere.model.PO implements I_M_HU_Instance_Properties_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 279740664L;

    /** Standard Constructor */
    public X_M_HU_Instance_Properties_v (final Properties ctx, final int M_HU_Instance_Properties_v_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Instance_Properties_v_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Instance_Properties_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
		set_ValueNoCheck (COLUMNNAME_AttributeName, AttributeName);
	}

	@Override
	public java.lang.String getAttributeName() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeName);
	}

	/** 
	 * DocumentName AD_Reference_ID=53258
	 * Reference name: A_Asset_ID
	 */
	public static final int DOCUMENTNAME_AD_Reference_ID=53258;
	@Override
	public void setDocumentName (final @Nullable java.lang.String DocumentName)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentName, DocumentName);
	}

	@Override
	public java.lang.String getDocumentName() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentName);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setM_Attribute_ID (final int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, M_Attribute_ID);
	}

	@Override
	public int getM_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Attribute_ID);
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
	public void setPIName (final @Nullable java.lang.String PIName)
	{
		set_ValueNoCheck (COLUMNNAME_PIName, PIName);
	}

	@Override
	public java.lang.String getPIName() 
	{
		return get_ValueAsString(COLUMNNAME_PIName);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setValueNumber (final @Nullable BigDecimal ValueNumber)
	{
		set_ValueNoCheck (COLUMNNAME_ValueNumber, ValueNumber);
	}

	@Override
	public BigDecimal getValueNumber() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ValueNumber);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}