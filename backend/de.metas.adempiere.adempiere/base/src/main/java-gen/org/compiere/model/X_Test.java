// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Test
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Test extends org.compiere.model.PO implements I_Test, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1318713126L;

    /** Standard Constructor */
    public X_Test (final Properties ctx, final int Test_ID, @Nullable final String trxName)
    {
      super (ctx, Test_ID, trxName);
    }

    /** Load Constructor */
    public X_Test (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_ValidCombination getAccount_A()
	{
		return get_ValueAsPO(COLUMNNAME_Account_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setAccount_A(final org.compiere.model.I_C_ValidCombination Account_A)
	{
		set_ValueFromPO(COLUMNNAME_Account_Acct, org.compiere.model.I_C_ValidCombination.class, Account_A);
	}

	@Override
	public void setAccount_Acct (final int Account_Acct)
	{
		set_Value (COLUMNNAME_Account_Acct, Account_Acct);
	}

	@Override
	public int getAccount_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_Account_Acct);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public org.compiere.model.I_AD_Image getBinaryD()
	{
		return get_ValueAsPO(COLUMNNAME_BinaryData, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setBinaryD(final org.compiere.model.I_AD_Image BinaryD)
	{
		set_ValueFromPO(COLUMNNAME_BinaryData, org.compiere.model.I_AD_Image.class, BinaryD);
	}

	@Override
	public void setBinaryData (final int BinaryData)
	{
		set_Value (COLUMNNAME_BinaryData, BinaryData);
	}

	@Override
	public int getBinaryData() 
	{
		return get_ValueAsInt(COLUMNNAME_BinaryData);
	}

	@Override
	public void setC_Async_Batch_ID (final int C_Async_Batch_ID)
	{
		if (C_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_ID, C_Async_Batch_ID);
	}

	@Override
	public int getC_Async_Batch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_BPartner_Memo (final @Nullable java.lang.String C_BPartner_Memo)
	{
		set_Value (COLUMNNAME_C_BPartner_Memo, C_BPartner_Memo);
	}

	@Override
	public java.lang.String getC_BPartner_Memo() 
	{
		return get_ValueAsString(COLUMNNAME_C_BPartner_Memo);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
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
	public void setC_Payment_ID (final int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, C_Payment_ID);
	}

	@Override
	public int getC_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setCharacterData (final @Nullable java.lang.String CharacterData)
	{
		set_Value (COLUMNNAME_CharacterData, CharacterData);
	}

	@Override
	public java.lang.String getCharacterData() 
	{
		return get_ValueAsString(COLUMNNAME_CharacterData);
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
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
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
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setT_Amount (final @Nullable BigDecimal T_Amount)
	{
		set_Value (COLUMNNAME_T_Amount, T_Amount);
	}

	@Override
	public BigDecimal getT_Amount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_T_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setT_Date (final @Nullable java.sql.Timestamp T_Date)
	{
		set_Value (COLUMNNAME_T_Date, T_Date);
	}

	@Override
	public java.sql.Timestamp getT_Date() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_T_Date);
	}

	@Override
	public void setT_DateTime (final @Nullable java.sql.Timestamp T_DateTime)
	{
		set_Value (COLUMNNAME_T_DateTime, T_DateTime);
	}

	@Override
	public java.sql.Timestamp getT_DateTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_T_DateTime);
	}

	@Override
	public void setT_Integer (final int T_Integer)
	{
		set_Value (COLUMNNAME_T_Integer, T_Integer);
	}

	@Override
	public int getT_Integer() 
	{
		return get_ValueAsInt(COLUMNNAME_T_Integer);
	}

	@Override
	public void setT_Number (final @Nullable BigDecimal T_Number)
	{
		set_Value (COLUMNNAME_T_Number, T_Number);
	}

	@Override
	public BigDecimal getT_Number() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_T_Number);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setT_Qty (final @Nullable BigDecimal T_Qty)
	{
		set_Value (COLUMNNAME_T_Qty, T_Qty);
	}

	@Override
	public BigDecimal getT_Qty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_T_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setT_Time (final @Nullable java.sql.Timestamp T_Time)
	{
		set_Value (COLUMNNAME_T_Time, T_Time);
	}

	@Override
	public java.sql.Timestamp getT_Time() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_T_Time);
	}

	@Override
	public void setTest_ID (final int Test_ID)
	{
		if (Test_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Test_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Test_ID, Test_ID);
	}

	@Override
	public int getTest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Test_ID);
	}
}