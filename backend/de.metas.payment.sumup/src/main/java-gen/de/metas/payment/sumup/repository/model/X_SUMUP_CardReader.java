// Generated Model - DO NOT CHANGE
package de.metas.payment.sumup.repository.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for SUMUP_CardReader
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_SUMUP_CardReader extends org.compiere.model.PO implements I_SUMUP_CardReader, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1064470712L;

    /** Standard Constructor */
    public X_SUMUP_CardReader (final Properties ctx, final int SUMUP_CardReader_ID, @Nullable final String trxName)
    {
      super (ctx, SUMUP_CardReader_ID, trxName);
    }

    /** Load Constructor */
    public X_SUMUP_CardReader (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setExternalId (final java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
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
	public void setSUMUP_CardReader_ID (final int SUMUP_CardReader_ID)
	{
		if (SUMUP_CardReader_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SUMUP_CardReader_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SUMUP_CardReader_ID, SUMUP_CardReader_ID);
	}

	@Override
	public int getSUMUP_CardReader_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SUMUP_CardReader_ID);
	}

	@Override
	public de.metas.payment.sumup.repository.model.I_SUMUP_Config getSUMUP_Config()
	{
		return get_ValueAsPO(COLUMNNAME_SUMUP_Config_ID, de.metas.payment.sumup.repository.model.I_SUMUP_Config.class);
	}

	@Override
	public void setSUMUP_Config(final de.metas.payment.sumup.repository.model.I_SUMUP_Config SUMUP_Config)
	{
		set_ValueFromPO(COLUMNNAME_SUMUP_Config_ID, de.metas.payment.sumup.repository.model.I_SUMUP_Config.class, SUMUP_Config);
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
}