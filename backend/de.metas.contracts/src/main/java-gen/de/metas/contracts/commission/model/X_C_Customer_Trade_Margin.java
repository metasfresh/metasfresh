// Generated Model - DO NOT CHANGE
package de.metas.contracts.commission.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Customer_Trade_Margin
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Customer_Trade_Margin extends org.compiere.model.PO implements I_C_Customer_Trade_Margin, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 383807926L;

    /** Standard Constructor */
    public X_C_Customer_Trade_Margin (final Properties ctx, final int C_Customer_Trade_Margin_ID, @Nullable final String trxName)
    {
      super (ctx, C_Customer_Trade_Margin_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Customer_Trade_Margin (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCommission_Product_ID (final int Commission_Product_ID)
	{
		if (Commission_Product_ID < 1) 
			set_Value (COLUMNNAME_Commission_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Commission_Product_ID, Commission_Product_ID);
	}

	@Override
	public int getCommission_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Commission_Product_ID);
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
	public void setPointsPrecision (final int PointsPrecision)
	{
		set_Value (COLUMNNAME_PointsPrecision, PointsPrecision);
	}

	@Override
	public int getPointsPrecision() 
	{
		return get_ValueAsInt(COLUMNNAME_PointsPrecision);
	}
}