// Generated Model - DO NOT CHANGE
package de.metas.payment.sepa.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for SEPA_Export
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_SEPA_Export extends org.compiere.model.PO implements I_SEPA_Export, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 58551915L;

    /** Standard Constructor */
    public X_SEPA_Export (final Properties ctx, final int SEPA_Export_ID, @Nullable final String trxName)
    {
      super (ctx, SEPA_Export_ID, trxName);
    }

    /** Load Constructor */
    public X_SEPA_Export (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
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
	public void setDocumentNo (final java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setIBAN (final java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	@Override
	public java.lang.String getIBAN() 
	{
		return get_ValueAsString(COLUMNNAME_IBAN);
	}

	@Override
	public void setIsExportBatchBookings (final boolean IsExportBatchBookings)
	{
		set_Value (COLUMNNAME_IsExportBatchBookings, IsExportBatchBookings);
	}

	@Override
	public boolean isExportBatchBookings() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExportBatchBookings);
	}

	@Override
	public void setPaymentDate (final java.sql.Timestamp PaymentDate)
	{
		set_Value (COLUMNNAME_PaymentDate, PaymentDate);
	}

	@Override
	public java.sql.Timestamp getPaymentDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PaymentDate);
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
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setSEPA_CreditorIdentifier (final @Nullable java.lang.String SEPA_CreditorIdentifier)
	{
		set_Value (COLUMNNAME_SEPA_CreditorIdentifier, SEPA_CreditorIdentifier);
	}

	@Override
	public java.lang.String getSEPA_CreditorIdentifier() 
	{
		return get_ValueAsString(COLUMNNAME_SEPA_CreditorIdentifier);
	}

	@Override
	public void setSEPA_CreditorName (final java.lang.String SEPA_CreditorName)
	{
		set_Value (COLUMNNAME_SEPA_CreditorName, SEPA_CreditorName);
	}

	@Override
	public java.lang.String getSEPA_CreditorName() 
	{
		return get_ValueAsString(COLUMNNAME_SEPA_CreditorName);
	}

	@Override
	public void setSEPA_Export_ID (final int SEPA_Export_ID)
	{
		if (SEPA_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_ID, SEPA_Export_ID);
	}

	@Override
	public int getSEPA_Export_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SEPA_Export_ID);
	}

	/** 
	 * SEPA_Protocol AD_Reference_ID=541110
	 * Reference name: SEPA_Protocol
	 */
	public static final int SEPA_PROTOCOL_AD_Reference_ID=541110;
	/** Credit Transfer (pain.001.001.03.ch.02) = pain.001.001.03.ch.02 */
	public static final String SEPA_PROTOCOL_CreditTransferPain00100103Ch02 = "pain.001.001.03.ch.02";
	/** Direct Debit (pain.008.003.02) = pain.008.003.02 */
	public static final String SEPA_PROTOCOL_DirectDebitPain00800302 = "pain.008.003.02";
	/** Credit Transfer (pain.001.001.09.ch.03) = pain.001.001.09.ch.03 */
	public static final String SEPA_PROTOCOL_CreditTransferPain00100109Ch03 = "pain.001.001.09.ch.03";
	/** Direct Debit pain.008.001.08 = pain.008.001.08 */
	public static final String SEPA_PROTOCOL_DirectDebitPain00800108 = "pain.008.001.08";
	@Override
	public void setSEPA_Protocol (final java.lang.String SEPA_Protocol)
	{
		set_Value (COLUMNNAME_SEPA_Protocol, SEPA_Protocol);
	}

	@Override
	public java.lang.String getSEPA_Protocol() 
	{
		return get_ValueAsString(COLUMNNAME_SEPA_Protocol);
	}

	@Override
	public void setSwiftCode (final java.lang.String SwiftCode)
	{
		set_Value (COLUMNNAME_SwiftCode, SwiftCode);
	}

	@Override
	public java.lang.String getSwiftCode() 
	{
		return get_ValueAsString(COLUMNNAME_SwiftCode);
	}
}