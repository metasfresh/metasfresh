/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Bank
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Bank extends org.compiere.model.PO implements I_C_Bank, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -69481044L;

    /** Standard Constructor */
    public X_C_Bank (Properties ctx, int C_Bank_ID, String trxName)
    {
      super (ctx, C_Bank_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Bank (Properties ctx, ResultSet rs, String trxName)
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
	public void setC_Bank_ID (int C_Bank_ID)
	{
		if (C_Bank_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Bank_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
	}

	@Override
	public int getC_Bank_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Bank_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getC_Location()
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	@Override
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	@Override
	public int getC_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Location_ID);
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
	public void setESR_PostBank (boolean ESR_PostBank)
	{
		set_Value (COLUMNNAME_ESR_PostBank, Boolean.valueOf(ESR_PostBank));
	}

	@Override
	public boolean isESR_PostBank() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ESR_PostBank);
	}

	@Override
	public void setIsCashBank (boolean IsCashBank)
	{
		set_Value (COLUMNNAME_IsCashBank, Boolean.valueOf(IsCashBank));
	}

	@Override
	public boolean isCashBank() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCashBank);
	}

	@Override
	public void setIsOwnBank (boolean IsOwnBank)
	{
		set_Value (COLUMNNAME_IsOwnBank, Boolean.valueOf(IsOwnBank));
	}

	@Override
	public boolean isOwnBank() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOwnBank);
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
	public void setRoutingNo (java.lang.String RoutingNo)
	{
		set_Value (COLUMNNAME_RoutingNo, RoutingNo);
	}

	@Override
	public java.lang.String getRoutingNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RoutingNo);
	}

	@Override
	public void setSwiftCode (java.lang.String SwiftCode)
	{
		set_Value (COLUMNNAME_SwiftCode, SwiftCode);
	}

	@Override
	public java.lang.String getSwiftCode() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SwiftCode);
	}
}