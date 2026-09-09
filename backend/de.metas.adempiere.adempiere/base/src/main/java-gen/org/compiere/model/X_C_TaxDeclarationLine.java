// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_TaxDeclarationLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_TaxDeclarationLine extends org.compiere.model.PO implements I_C_TaxDeclarationLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 46047980L;

    /** Standard Constructor */
    public X_C_TaxDeclarationLine (final Properties ctx, final int C_TaxDeclarationLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_TaxDeclarationLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_TaxDeclarationLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmount (final @Nullable BigDecimal Amount)
	{
		set_ValueNoCheck (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * AmountType AD_Reference_ID=542087
	 * Reference name: C_VAT_Code AmountType
	 */
	public static final int AMOUNTTYPE_AD_Reference_ID=542087;
	/** Net = N */
	public static final String AMOUNTTYPE_Net = "N";
	/** Tax = T */
	public static final String AMOUNTTYPE_Tax = "T";
	@Override
	public void setAmountType (final @Nullable java.lang.String AmountType)
	{
		set_ValueNoCheck (COLUMNNAME_AmountType, AmountType);
	}

	@Override
	public java.lang.String getAmountType() 
	{
		return get_ValueAsString(COLUMNNAME_AmountType);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public org.compiere.model.I_C_TaxDeclaration getC_TaxDeclaration()
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxDeclaration_ID, org.compiere.model.I_C_TaxDeclaration.class);
	}

	@Override
	public void setC_TaxDeclaration(final org.compiere.model.I_C_TaxDeclaration C_TaxDeclaration)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxDeclaration_ID, org.compiere.model.I_C_TaxDeclaration.class, C_TaxDeclaration);
	}

	@Override
	public void setC_TaxDeclaration_ID (final int C_TaxDeclaration_ID)
	{
		if (C_TaxDeclaration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclaration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclaration_ID, C_TaxDeclaration_ID);
	}

	@Override
	public int getC_TaxDeclaration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxDeclaration_ID);
	}

	@Override
	public void setC_TaxDeclarationLine_ID (final int C_TaxDeclarationLine_ID)
	{
		if (C_TaxDeclarationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclarationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclarationLine_ID, C_TaxDeclarationLine_ID);
	}

	@Override
	public int getC_TaxDeclarationLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxDeclarationLine_ID);
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

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setLineCount (final int LineCount)
	{
		set_ValueNoCheck (COLUMNNAME_LineCount, LineCount);
	}

	@Override
	public int getLineCount() 
	{
		return get_ValueAsInt(COLUMNNAME_LineCount);
	}
}