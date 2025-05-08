// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_ScannableCode_Format_Part
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ScannableCode_Format_Part extends org.compiere.model.PO implements I_C_ScannableCode_Format_Part, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -668835490L;

    /** Standard Constructor */
    public X_C_ScannableCode_Format_Part (final Properties ctx, final int C_ScannableCode_Format_Part_ID, @Nullable final String trxName)
    {
      super (ctx, C_ScannableCode_Format_Part_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ScannableCode_Format_Part (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_ScannableCode_Format getC_ScannableCode_Format()
	{
		return get_ValueAsPO(COLUMNNAME_C_ScannableCode_Format_ID, org.compiere.model.I_C_ScannableCode_Format.class);
	}

	@Override
	public void setC_ScannableCode_Format(final org.compiere.model.I_C_ScannableCode_Format C_ScannableCode_Format)
	{
		set_ValueFromPO(COLUMNNAME_C_ScannableCode_Format_ID, org.compiere.model.I_C_ScannableCode_Format.class, C_ScannableCode_Format);
	}

	@Override
	public void setC_ScannableCode_Format_ID (final int C_ScannableCode_Format_ID)
	{
		if (C_ScannableCode_Format_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ScannableCode_Format_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ScannableCode_Format_ID, C_ScannableCode_Format_ID);
	}

	@Override
	public int getC_ScannableCode_Format_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ScannableCode_Format_ID);
	}

	@Override
	public void setC_ScannableCode_Format_Part_ID (final int C_ScannableCode_Format_Part_ID)
	{
		if (C_ScannableCode_Format_Part_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ScannableCode_Format_Part_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ScannableCode_Format_Part_ID, C_ScannableCode_Format_Part_ID);
	}

	@Override
	public int getC_ScannableCode_Format_Part_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ScannableCode_Format_Part_ID);
	}

	@Override
	public void setDataFormat (final @Nullable java.lang.String DataFormat)
	{
		set_Value (COLUMNNAME_DataFormat, DataFormat);
	}

	@Override
	public java.lang.String getDataFormat() 
	{
		return get_ValueAsString(COLUMNNAME_DataFormat);
	}

	/** 
	 * DataType AD_Reference_ID=541944
	 * Reference name: C_ScannableCode_Format_Part_DataType
	 */
	public static final int DATATYPE_AD_Reference_ID=541944;
	/** ProductCode = PRODUCT_CODE */
	public static final String DATATYPE_ProductCode = "PRODUCT_CODE";
	/** WeightInKg = WEIGHT_KG */
	public static final String DATATYPE_WeightInKg = "WEIGHT_KG";
	/** LotNo = LOT */
	public static final String DATATYPE_LotNo = "LOT";
	/** BestBeforeDate = BEST_BEFORE_DATE */
	public static final String DATATYPE_BestBeforeDate = "BEST_BEFORE_DATE";
	/** Ignored = IGNORE */
	public static final String DATATYPE_Ignored = "IGNORE";
	@Override
	public void setDataType (final java.lang.String DataType)
	{
		set_Value (COLUMNNAME_DataType, DataType);
	}

	@Override
	public java.lang.String getDataType() 
	{
		return get_ValueAsString(COLUMNNAME_DataType);
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
	public void setEndNo (final int EndNo)
	{
		set_Value (COLUMNNAME_EndNo, EndNo);
	}

	@Override
	public int getEndNo() 
	{
		return get_ValueAsInt(COLUMNNAME_EndNo);
	}

	@Override
	public void setStartNo (final int StartNo)
	{
		set_Value (COLUMNNAME_StartNo, StartNo);
	}

	@Override
	public int getStartNo() 
	{
		return get_ValueAsInt(COLUMNNAME_StartNo);
	}
}