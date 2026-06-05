// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_EDI_Setting
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_EDI_Setting extends org.compiere.model.PO implements I_C_BPartner_EDI_Setting, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1587066449L;

    /** Standard Constructor */
    public X_C_BPartner_EDI_Setting (final Properties ctx, final int C_BPartner_EDI_Setting_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_EDI_Setting_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_EDI_Setting (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_EDI_Setting_ID (final int C_BPartner_EDI_Setting_ID)
	{
		if (C_BPartner_EDI_Setting_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_EDI_Setting_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_EDI_Setting_ID, C_BPartner_EDI_Setting_ID);
	}

	@Override
	public int getC_BPartner_EDI_Setting_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_EDI_Setting_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
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
	public void setEdiDESADVDefaultItemCapacity (final @Nullable BigDecimal EdiDESADVDefaultItemCapacity)
	{
		set_Value (COLUMNNAME_EdiDESADVDefaultItemCapacity, EdiDESADVDefaultItemCapacity);
	}

	@Override
	public BigDecimal getEdiDESADVDefaultItemCapacity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_EdiDESADVDefaultItemCapacity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setEdiDESADV_ExternalSystem_Config_ID (final int EdiDESADV_ExternalSystem_Config_ID)
	{
		if (EdiDESADV_ExternalSystem_Config_ID < 1) 
			set_Value (COLUMNNAME_EdiDESADV_ExternalSystem_Config_ID, null);
		else 
			set_Value (COLUMNNAME_EdiDESADV_ExternalSystem_Config_ID, EdiDESADV_ExternalSystem_Config_ID);
	}

	@Override
	public int getEdiDESADV_ExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EdiDESADV_ExternalSystem_Config_ID);
	}

	@Override
	public void setEdiDesadvRecipientGLN (final @Nullable java.lang.String EdiDesadvRecipientGLN)
	{
		set_Value (COLUMNNAME_EdiDesadvRecipientGLN, EdiDesadvRecipientGLN);
	}

	@Override
	public java.lang.String getEdiDesadvRecipientGLN() 
	{
		return get_ValueAsString(COLUMNNAME_EdiDesadvRecipientGLN);
	}

	/** 
	 * EdiDESADVSendingMode AD_Reference_ID=542047
	 * Reference name: EDISendingMode
	 */
	public static final int EDIDESADVSENDINGMODE_AD_Reference_ID=542047;
	/** ReplicationInterface = R */
	public static final String EDIDESADVSENDINGMODE_ReplicationInterface = "R";
	/** ExternalSystem = E */
	public static final String EDIDESADVSENDINGMODE_ExternalSystem = "E";
	@Override
	public void setEdiDESADVSendingMode (final java.lang.String EdiDESADVSendingMode)
	{
		set_Value (COLUMNNAME_EdiDESADVSendingMode, EdiDESADVSendingMode);
	}

	@Override
	public java.lang.String getEdiDESADVSendingMode() 
	{
		return get_ValueAsString(COLUMNNAME_EdiDESADVSendingMode);
	}

	@Override
	public void setEdiINVOIC_ExternalSystem_Config_ID (final int EdiINVOIC_ExternalSystem_Config_ID)
	{
		if (EdiINVOIC_ExternalSystem_Config_ID < 1) 
			set_Value (COLUMNNAME_EdiINVOIC_ExternalSystem_Config_ID, null);
		else 
			set_Value (COLUMNNAME_EdiINVOIC_ExternalSystem_Config_ID, EdiINVOIC_ExternalSystem_Config_ID);
	}

	@Override
	public int getEdiINVOIC_ExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EdiINVOIC_ExternalSystem_Config_ID);
	}

	@Override
	public void setEdiInvoicRecipientGLN (final @Nullable java.lang.String EdiInvoicRecipientGLN)
	{
		set_Value (COLUMNNAME_EdiInvoicRecipientGLN, EdiInvoicRecipientGLN);
	}

	@Override
	public java.lang.String getEdiInvoicRecipientGLN() 
	{
		return get_ValueAsString(COLUMNNAME_EdiInvoicRecipientGLN);
	}

	/** 
	 * EdiINVOICSendingMode AD_Reference_ID=542047
	 * Reference name: EDISendingMode
	 */
	public static final int EDIINVOICSENDINGMODE_AD_Reference_ID=542047;
	/** ReplicationInterface = R */
	public static final String EDIINVOICSENDINGMODE_ReplicationInterface = "R";
	/** ExternalSystem = E */
	public static final String EDIINVOICSENDINGMODE_ExternalSystem = "E";
	@Override
	public void setEdiINVOICSendingMode (final java.lang.String EdiINVOICSendingMode)
	{
		set_Value (COLUMNNAME_EdiINVOICSendingMode, EdiINVOICSendingMode);
	}

	@Override
	public java.lang.String getEdiINVOICSendingMode() 
	{
		return get_ValueAsString(COLUMNNAME_EdiINVOICSendingMode);
	}

	@Override
	public void setIsEdiDesadvRecipient (final boolean IsEdiDesadvRecipient)
	{
		set_Value (COLUMNNAME_IsEdiDesadvRecipient, IsEdiDesadvRecipient);
	}

	@Override
	public boolean isEdiDesadvRecipient() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEdiDesadvRecipient);
	}

	@Override
	public void setIsEdiInvoicRecipient (final boolean IsEdiInvoicRecipient)
	{
		set_Value (COLUMNNAME_IsEdiInvoicRecipient, IsEdiInvoicRecipient);
	}

	@Override
	public boolean isEdiInvoicRecipient() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEdiInvoicRecipient);
	}
}