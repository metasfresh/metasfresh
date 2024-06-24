// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Bank
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Bank extends org.compiere.model.PO implements I_C_Bank, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -376628303L;

    /** Standard Constructor */
    public X_C_Bank (final Properties ctx, final int C_Bank_ID, @Nullable final String trxName)
    {
      super (ctx, C_Bank_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Bank (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Bank_ID (final int C_Bank_ID)
	{
		if (C_Bank_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Bank_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Bank_ID, C_Bank_ID);
	}

	@Override
	public int getC_Bank_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Bank_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(final org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (final int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, C_DataImport_ID);
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getC_Location()
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(final org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	@Override
	public void setC_Location_ID (final int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, C_Location_ID);
	}

	@Override
	public int getC_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Location_ID);
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
	public void setESR_PostBank (final boolean ESR_PostBank)
	{
		set_Value (COLUMNNAME_ESR_PostBank, ESR_PostBank);
	}

	@Override
	public boolean isESR_PostBank() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ESR_PostBank);
	}

	@Override
	public void setIsCashBank (final boolean IsCashBank)
	{
		set_Value (COLUMNNAME_IsCashBank, IsCashBank);
	}

	@Override
	public boolean isCashBank() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCashBank);
	}

	@Override
	public void setIsOwnBank (final boolean IsOwnBank)
	{
		set_Value (COLUMNNAME_IsOwnBank, IsOwnBank);
	}

	@Override
	public boolean isOwnBank() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOwnBank);
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
	public void setRoutingNo (final java.lang.String RoutingNo)
	{
		set_Value (COLUMNNAME_RoutingNo, RoutingNo);
	}

	@Override
	public java.lang.String getRoutingNo() 
	{
		return get_ValueAsString(COLUMNNAME_RoutingNo);
	}

	@Override
	public void setSwiftCode (final @Nullable java.lang.String SwiftCode)
	{
		set_Value (COLUMNNAME_SwiftCode, SwiftCode);
	}

	@Override
	public java.lang.String getSwiftCode() 
	{
		return get_ValueAsString(COLUMNNAME_SwiftCode);
	}
}