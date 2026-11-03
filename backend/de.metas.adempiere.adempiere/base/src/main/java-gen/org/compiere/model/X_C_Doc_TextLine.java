// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Doc_TextLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Doc_TextLine extends org.compiere.model.PO implements I_C_Doc_TextLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1963398970L;

    /** Standard Constructor */
    public X_C_Doc_TextLine (final Properties ctx, final int C_Doc_TextLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_Doc_TextLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Doc_TextLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Doc_TextLine_ID (final int C_Doc_TextLine_ID)
	{
		if (C_Doc_TextLine_ID < 1) 
			set_Value (COLUMNNAME_C_Doc_TextLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_Doc_TextLine_ID, C_Doc_TextLine_ID);
	}

	@Override
	public int getC_Doc_TextLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_TextLine_ID);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setLine (final BigDecimal Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public BigDecimal getLine() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Line);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public void setTextLine (final @Nullable java.lang.String TextLine)
	{
		set_Value (COLUMNNAME_TextLine, TextLine);
	}

	@Override
	public java.lang.String getTextLine() 
	{
		return get_ValueAsString(COLUMNNAME_TextLine);
	}

	/** 
	 * TextLineScope AD_Reference_ID=542144
	 * Reference name: C_Doc_TextLine_Scope
	 */
	public static final int TEXTLINESCOPE_AD_Reference_ID=542144;
	/** Following = F */
	public static final String TEXTLINESCOPE_Following = "F";
	/** Document = D */
	public static final String TEXTLINESCOPE_Document = "D";
	@Override
	public void setTextLineScope (final java.lang.String TextLineScope)
	{
		set_Value (COLUMNNAME_TextLineScope, TextLineScope);
	}

	@Override
	public java.lang.String getTextLineScope() 
	{
		return get_ValueAsString(COLUMNNAME_TextLineScope);
	}
}