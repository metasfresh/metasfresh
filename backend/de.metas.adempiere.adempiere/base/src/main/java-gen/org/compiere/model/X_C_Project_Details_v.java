// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_Details_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_Details_v extends org.compiere.model.PO implements I_C_Project_Details_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -357568157L;

    /** Standard Constructor */
    public X_C_Project_Details_v (final Properties ctx, final int C_Project_Details_v_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_Details_v_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_Details_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	@Override
	public void setAD_Language (final @Nullable java.lang.String AD_Language)
	{
		set_ValueNoCheck (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language() 
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setC_ProjectLine_ID (final int C_ProjectLine_ID)
	{
		if (C_ProjectLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectLine_ID, C_ProjectLine_ID);
	}

	@Override
	public int getC_ProjectLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ProjectLine_ID);
	}

	@Override
	public void setCommittedAmt (final @Nullable BigDecimal CommittedAmt)
	{
		set_ValueNoCheck (COLUMNNAME_CommittedAmt, CommittedAmt);
	}

	@Override
	public BigDecimal getCommittedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CommittedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCommittedQty (final @Nullable BigDecimal CommittedQty)
	{
		set_ValueNoCheck (COLUMNNAME_CommittedQty, CommittedQty);
	}

	@Override
	public BigDecimal getCommittedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CommittedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDocumentNote (final @Nullable java.lang.String DocumentNote)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNote, DocumentNote);
	}

	@Override
	public java.lang.String getDocumentNote() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNote);
	}

	@Override
	public void setInvoicedAmt (final BigDecimal InvoicedAmt)
	{
		set_ValueNoCheck (COLUMNNAME_InvoicedAmt, InvoicedAmt);
	}

	@Override
	public BigDecimal getInvoicedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoicedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInvoicedQty (final BigDecimal InvoicedQty)
	{
		set_ValueNoCheck (COLUMNNAME_InvoicedQty, InvoicedQty);
	}

	@Override
	public BigDecimal getInvoicedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoicedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setLine (final int Line)
	{
		set_ValueNoCheck (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setPlannedAmt (final BigDecimal PlannedAmt)
	{
		set_ValueNoCheck (COLUMNNAME_PlannedAmt, PlannedAmt);
	}

	@Override
	public BigDecimal getPlannedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedMarginAmt (final BigDecimal PlannedMarginAmt)
	{
		set_ValueNoCheck (COLUMNNAME_PlannedMarginAmt, PlannedMarginAmt);
	}

	@Override
	public BigDecimal getPlannedMarginAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedMarginAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedPrice (final BigDecimal PlannedPrice)
	{
		set_ValueNoCheck (COLUMNNAME_PlannedPrice, PlannedPrice);
	}

	@Override
	public BigDecimal getPlannedPrice() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedPrice);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedQty (final BigDecimal PlannedQty)
	{
		set_ValueNoCheck (COLUMNNAME_PlannedQty, PlannedQty);
	}

	@Override
	public BigDecimal getPlannedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProductValue (final @Nullable java.lang.String ProductValue)
	{
		set_ValueNoCheck (COLUMNNAME_ProductValue, ProductValue);
	}

	@Override
	public java.lang.String getProductValue() 
	{
		return get_ValueAsString(COLUMNNAME_ProductValue);
	}

	@Override
	public void setSKU (final @Nullable java.lang.String SKU)
	{
		set_ValueNoCheck (COLUMNNAME_SKU, SKU);
	}

	@Override
	public java.lang.String getSKU() 
	{
		return get_ValueAsString(COLUMNNAME_SKU);
	}

	@Override
	public void setUPC (final @Nullable java.lang.String UPC)
	{
		set_ValueNoCheck (COLUMNNAME_UPC, UPC);
	}

	@Override
	public java.lang.String getUPC() 
	{
		return get_ValueAsString(COLUMNNAME_UPC);
	}
}