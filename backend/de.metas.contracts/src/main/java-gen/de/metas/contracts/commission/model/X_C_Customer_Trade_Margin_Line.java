// Generated Model - DO NOT CHANGE
package de.metas.contracts.commission.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Customer_Trade_Margin_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Customer_Trade_Margin_Line extends org.compiere.model.PO implements I_C_Customer_Trade_Margin_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1658529849L;

    /** Standard Constructor */
    public X_C_Customer_Trade_Margin_Line (final Properties ctx, final int C_Customer_Trade_Margin_Line_ID, @Nullable final String trxName)
    {
      super (ctx, C_Customer_Trade_Margin_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Customer_Trade_Margin_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Customer_ID (final int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, C_BPartner_Customer_ID);
	}

	@Override
	public int getC_BPartner_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Customer_ID);
	}

	@Override
	public de.metas.contracts.commission.model.I_C_Customer_Trade_Margin getC_Customer_Trade_Margin()
	{
		return get_ValueAsPO(COLUMNNAME_C_Customer_Trade_Margin_ID, de.metas.contracts.commission.model.I_C_Customer_Trade_Margin.class);
	}

	@Override
	public void setC_Customer_Trade_Margin(final de.metas.contracts.commission.model.I_C_Customer_Trade_Margin C_Customer_Trade_Margin)
	{
		set_ValueFromPO(COLUMNNAME_C_Customer_Trade_Margin_ID, de.metas.contracts.commission.model.I_C_Customer_Trade_Margin.class, C_Customer_Trade_Margin);
	}

	@Override
	public void setC_Customer_Trade_Margin_ID (final int C_Customer_Trade_Margin_ID)
	{
		if (C_Customer_Trade_Margin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Trade_Margin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Trade_Margin_ID, C_Customer_Trade_Margin_ID);
	}

	@Override
	public int getC_Customer_Trade_Margin_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Customer_Trade_Margin_ID);
	}

	@Override
	public void setC_Customer_Trade_Margin_Line_ID (final int C_Customer_Trade_Margin_Line_ID)
	{
		if (C_Customer_Trade_Margin_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Trade_Margin_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Trade_Margin_Line_ID, C_Customer_Trade_Margin_Line_ID);
	}

	@Override
	public int getC_Customer_Trade_Margin_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Customer_Trade_Margin_Line_ID);
	}

	@Override
	public void setMargin (final int Margin)
	{
		set_Value (COLUMNNAME_Margin, Margin);
	}

	@Override
	public int getMargin() 
	{
		return get_ValueAsInt(COLUMNNAME_Margin);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
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
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}