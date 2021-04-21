/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Zebra_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Zebra_Config extends org.compiere.model.PO implements I_AD_Zebra_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1777983117L;

    /** Standard Constructor */
    public X_AD_Zebra_Config (final Properties ctx, final int AD_Zebra_Config_ID, final String trxName)
    {
      super (ctx, AD_Zebra_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Zebra_Config (final Properties ctx, final ResultSet rs, final String trxName)
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
	public void setAD_Zebra_Config_ID (final int AD_Zebra_Config_ID)
	{
		if (AD_Zebra_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Zebra_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Zebra_Config_ID, AD_Zebra_Config_ID);
	}

	@Override
	public int getAD_Zebra_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Zebra_Config_ID);
	}

	@Override
	public void setEncoding (final java.lang.String Encoding)
	{
		set_Value (COLUMNNAME_Encoding, Encoding);
	}

	@Override
	public java.lang.String getEncoding() 
	{
		return get_ValueAsString(COLUMNNAME_Encoding);
	}

	@Override
	public void setHeader_Line1 (final java.lang.String Header_Line1)
	{
		set_Value (COLUMNNAME_Header_Line1, Header_Line1);
	}

	@Override
	public java.lang.String getHeader_Line1() 
	{
		return get_ValueAsString(COLUMNNAME_Header_Line1);
	}

	@Override
	public void setHeader_Line2 (final java.lang.String Header_Line2)
	{
		set_Value (COLUMNNAME_Header_Line2, Header_Line2);
	}

	@Override
	public java.lang.String getHeader_Line2() 
	{
		return get_ValueAsString(COLUMNNAME_Header_Line2);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
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
	public void setSQL_Select (final java.lang.String SQL_Select)
	{
		set_Value (COLUMNNAME_SQL_Select, SQL_Select);
	}

	@Override
	public java.lang.String getSQL_Select() 
	{
		return get_ValueAsString(COLUMNNAME_SQL_Select);
	}
}