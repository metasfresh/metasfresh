// Generated Model - DO NOT CHANGE
package de.metas.acct.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_VAT_Code
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_VAT_Code extends org.compiere.model.PO implements I_C_VAT_Code, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1273303698L;

    /** Standard Constructor */
    public X_C_VAT_Code (final Properties ctx, final int C_VAT_Code_ID, @Nullable final String trxName)
    {
      super (ctx, C_VAT_Code_ID, trxName);
    }

    /** Load Constructor */
    public X_C_VAT_Code (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(final org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	@Override
	public void setC_AcctSchema_ID (final int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
	}

	@Override
	public void setC_Tax_ID (final int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, C_Tax_ID);
	}

	@Override
	public int getC_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_ID);
	}

	@Override
	public void setC_VAT_Code_ID (final int C_VAT_Code_ID)
	{
		if (C_VAT_Code_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_VAT_Code_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_VAT_Code_ID, C_VAT_Code_ID);
	}

	@Override
	public int getC_VAT_Code_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_VAT_Code_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * IsSOTrx AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISSOTRX_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISSOTRX_Yes = "Y";
	/** No = N */
	public static final String ISSOTRX_No = "N";
	@Override
	public void setIsSOTrx (final @Nullable java.lang.String IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public java.lang.String getIsSOTrx() 
	{
		return get_ValueAsString(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}

	@Override
	public void setVATCode (final java.lang.String VATCode)
	{
		set_Value (COLUMNNAME_VATCode, VATCode);
	}

	@Override
	public java.lang.String getVATCode() 
	{
		return get_ValueAsString(COLUMNNAME_VATCode);
	}
}