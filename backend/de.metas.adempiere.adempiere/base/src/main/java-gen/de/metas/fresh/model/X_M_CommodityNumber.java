/** Generated Model - DO NOT CHANGE */
package de.metas.fresh.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_CommodityNumber
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_CommodityNumber extends org.compiere.model.PO implements I_M_CommodityNumber, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 942138765L;

    /** Standard Constructor */
    public X_M_CommodityNumber (Properties ctx, int M_CommodityNumber_ID, String trxName)
    {
      super (ctx, M_CommodityNumber_ID, trxName);
    }

    /** Load Constructor */
    public X_M_CommodityNumber (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setM_CommodityNumber_ID (int M_CommodityNumber_ID)
	{
		if (M_CommodityNumber_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CommodityNumber_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CommodityNumber_ID, Integer.valueOf(M_CommodityNumber_ID));
	}

	@Override
	public int getM_CommodityNumber_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CommodityNumber_ID);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}