// Generated Model - DO NOT CHANGE
package de.metas.payment.sumup.repository.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for SUMUP_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_SUMUP_Config extends org.compiere.model.PO implements I_SUMUP_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 886836191L;

    /** Standard Constructor */
    public X_SUMUP_Config (final Properties ctx, final int SUMUP_Config_ID, @Nullable final String trxName)
    {
      super (ctx, SUMUP_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_SUMUP_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setApiKey (final java.lang.String ApiKey)
	{
		set_Value (COLUMNNAME_ApiKey, ApiKey);
	}

	@Override
	public java.lang.String getApiKey() 
	{
		return get_ValueAsString(COLUMNNAME_ApiKey);
	}

	@Override
	public de.metas.payment.sumup.repository.model.I_SUMUP_CardReader getSUMUP_CardReader()
	{
		return get_ValueAsPO(COLUMNNAME_SUMUP_CardReader_ID, de.metas.payment.sumup.repository.model.I_SUMUP_CardReader.class);
	}

	@Override
	public void setSUMUP_CardReader(final de.metas.payment.sumup.repository.model.I_SUMUP_CardReader SUMUP_CardReader)
	{
		set_ValueFromPO(COLUMNNAME_SUMUP_CardReader_ID, de.metas.payment.sumup.repository.model.I_SUMUP_CardReader.class, SUMUP_CardReader);
	}

	@Override
	public void setSUMUP_CardReader_ID (final int SUMUP_CardReader_ID)
	{
		if (SUMUP_CardReader_ID < 1) 
			set_Value (COLUMNNAME_SUMUP_CardReader_ID, null);
		else 
			set_Value (COLUMNNAME_SUMUP_CardReader_ID, SUMUP_CardReader_ID);
	}

	@Override
	public int getSUMUP_CardReader_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SUMUP_CardReader_ID);
	}

	@Override
	public void setSUMUP_Config_ID (final int SUMUP_Config_ID)
	{
		if (SUMUP_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SUMUP_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SUMUP_Config_ID, SUMUP_Config_ID);
	}

	@Override
	public int getSUMUP_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SUMUP_Config_ID);
	}

	@Override
	public void setSUMUP_merchant_code (final java.lang.String SUMUP_merchant_code)
	{
		set_Value (COLUMNNAME_SUMUP_merchant_code, SUMUP_merchant_code);
	}

	@Override
	public java.lang.String getSUMUP_merchant_code() 
	{
		return get_ValueAsString(COLUMNNAME_SUMUP_merchant_code);
	}
}