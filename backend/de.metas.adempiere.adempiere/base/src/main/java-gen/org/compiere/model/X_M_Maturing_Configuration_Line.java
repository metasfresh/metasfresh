// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Maturing_Configuration_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Maturing_Configuration_Line extends org.compiere.model.PO implements I_M_Maturing_Configuration_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1613730971L;

    /** Standard Constructor */
    public X_M_Maturing_Configuration_Line (final Properties ctx, final int M_Maturing_Configuration_Line_ID, @Nullable final String trxName)
    {
      super (ctx, M_Maturing_Configuration_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Maturing_Configuration_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setFrom_Product_ID (final int From_Product_ID)
	{
		if (From_Product_ID < 1) 
			set_Value (COLUMNNAME_From_Product_ID, null);
		else 
			set_Value (COLUMNNAME_From_Product_ID, From_Product_ID);
	}

	@Override
	public int getFrom_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_From_Product_ID);
	}

	@Override
	public void setMatured_Product_ID (final int Matured_Product_ID)
	{
		if (Matured_Product_ID < 1) 
			set_Value (COLUMNNAME_Matured_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Matured_Product_ID, Matured_Product_ID);
	}

	@Override
	public int getMatured_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Matured_Product_ID);
	}

	@Override
	public void setMaturityAge (final int MaturityAge)
	{
		set_Value (COLUMNNAME_MaturityAge, MaturityAge);
	}

	@Override
	public int getMaturityAge() 
	{
		return get_ValueAsInt(COLUMNNAME_MaturityAge);
	}

	@Override
	public org.compiere.model.I_M_Maturing_Configuration getM_Maturing_Configuration()
	{
		return get_ValueAsPO(COLUMNNAME_M_Maturing_Configuration_ID, org.compiere.model.I_M_Maturing_Configuration.class);
	}

	@Override
	public void setM_Maturing_Configuration(final org.compiere.model.I_M_Maturing_Configuration M_Maturing_Configuration)
	{
		set_ValueFromPO(COLUMNNAME_M_Maturing_Configuration_ID, org.compiere.model.I_M_Maturing_Configuration.class, M_Maturing_Configuration);
	}

	@Override
	public void setM_Maturing_Configuration_ID (final int M_Maturing_Configuration_ID)
	{
		if (M_Maturing_Configuration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Maturing_Configuration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Maturing_Configuration_ID, M_Maturing_Configuration_ID);
	}

	@Override
	public int getM_Maturing_Configuration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Maturing_Configuration_ID);
	}

	@Override
	public void setM_Maturing_Configuration_Line_ID (final int M_Maturing_Configuration_Line_ID)
	{
		if (M_Maturing_Configuration_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Maturing_Configuration_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Maturing_Configuration_Line_ID, M_Maturing_Configuration_Line_ID);
	}

	@Override
	public int getM_Maturing_Configuration_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Maturing_Configuration_Line_ID);
	}
}