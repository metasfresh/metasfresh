// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Config_Ebay_Mapping
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_Ebay_Mapping extends org.compiere.model.PO implements I_ExternalSystem_Config_Ebay_Mapping, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 314282995L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_Ebay_Mapping (final Properties ctx, final int ExternalSystem_Config_Ebay_Mapping_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_Ebay_Mapping_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_Ebay_Mapping (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * BPartner_IfExists AD_Reference_ID=541309
	 * Reference name: SyncAdvice_IfExists
	 */
	public static final int BPARTNER_IFEXISTS_AD_Reference_ID=541309;
	/** Update = UPDATE_MERGE */
	public static final String BPARTNER_IFEXISTS_Update = "UPDATE_MERGE";
	/** Nothing = DONT_UPDATE */
	public static final String BPARTNER_IFEXISTS_Nothing = "DONT_UPDATE";
	@Override
	public void setBPartner_IfExists (final java.lang.String BPartner_IfExists)
	{
		set_Value (COLUMNNAME_BPartner_IfExists, BPartner_IfExists);
	}

	@Override
	public java.lang.String getBPartner_IfExists() 
	{
		return get_ValueAsString(COLUMNNAME_BPartner_IfExists);
	}

	/** 
	 * BPartner_IfNotExists AD_Reference_ID=541310
	 * Reference name: SyncAdvice_IfNotExists
	 */
	public static final int BPARTNER_IFNOTEXISTS_AD_Reference_ID=541310;
	/** Create = CREATE */
	public static final String BPARTNER_IFNOTEXISTS_Create = "CREATE";
	/** Fail = FAIL */
	public static final String BPARTNER_IFNOTEXISTS_Fail = "FAIL";
	@Override
	public void setBPartner_IfNotExists (final java.lang.String BPartner_IfNotExists)
	{
		set_Value (COLUMNNAME_BPartner_IfNotExists, BPartner_IfNotExists);
	}

	@Override
	public java.lang.String getBPartner_IfNotExists() 
	{
		return get_ValueAsString(COLUMNNAME_BPartner_IfNotExists);
	}

	/** 
	 * BPartnerLocation_IfExists AD_Reference_ID=541309
	 * Reference name: SyncAdvice_IfExists
	 */
	public static final int BPARTNERLOCATION_IFEXISTS_AD_Reference_ID=541309;
	/** Update = UPDATE_MERGE */
	public static final String BPARTNERLOCATION_IFEXISTS_Update = "UPDATE_MERGE";
	/** Nothing = DONT_UPDATE */
	public static final String BPARTNERLOCATION_IFEXISTS_Nothing = "DONT_UPDATE";
	@Override
	public void setBPartnerLocation_IfExists (final java.lang.String BPartnerLocation_IfExists)
	{
		set_Value (COLUMNNAME_BPartnerLocation_IfExists, BPartnerLocation_IfExists);
	}

	@Override
	public java.lang.String getBPartnerLocation_IfExists() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerLocation_IfExists);
	}

	/** 
	 * BPartnerLocation_IfNotExists AD_Reference_ID=541310
	 * Reference name: SyncAdvice_IfNotExists
	 */
	public static final int BPARTNERLOCATION_IFNOTEXISTS_AD_Reference_ID=541310;
	/** Create = CREATE */
	public static final String BPARTNERLOCATION_IFNOTEXISTS_Create = "CREATE";
	/** Fail = FAIL */
	public static final String BPARTNERLOCATION_IFNOTEXISTS_Fail = "FAIL";
	@Override
	public void setBPartnerLocation_IfNotExists (final java.lang.String BPartnerLocation_IfNotExists)
	{
		set_Value (COLUMNNAME_BPartnerLocation_IfNotExists, BPartnerLocation_IfNotExists);
	}

	@Override
	public java.lang.String getBPartnerLocation_IfNotExists() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerLocation_IfNotExists);
	}

	@Override
	public void setC_DocTypeOrder_ID (final int C_DocTypeOrder_ID)
	{
		if (C_DocTypeOrder_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeOrder_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeOrder_ID, C_DocTypeOrder_ID);
	}

	@Override
	public int getC_DocTypeOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeOrder_ID);
	}

	@Override
	public void setC_PaymentTerm_ID (final int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, C_PaymentTerm_ID);
	}

	@Override
	public int getC_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_ID);
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
	public void setEBayCustomerGroup (final @Nullable java.lang.String EBayCustomerGroup)
	{
		set_Value (COLUMNNAME_EBayCustomerGroup, EBayCustomerGroup);
	}

	@Override
	public java.lang.String getEBayCustomerGroup() 
	{
		return get_ValueAsString(COLUMNNAME_EBayCustomerGroup);
	}

	@Override
	public void setEBayPaymentMethod (final @Nullable java.lang.String EBayPaymentMethod)
	{
		set_Value (COLUMNNAME_EBayPaymentMethod, EBayPaymentMethod);
	}

	@Override
	public java.lang.String getEBayPaymentMethod() 
	{
		return get_ValueAsString(COLUMNNAME_EBayPaymentMethod);
	}

	@Override
	public de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay getExternalSystem_Config_Ebay()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_Ebay_ID, de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay.class);
	}

	@Override
	public void setExternalSystem_Config_Ebay(final de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay ExternalSystem_Config_Ebay)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_Ebay_ID, de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay.class, ExternalSystem_Config_Ebay);
	}

	@Override
	public void setExternalSystem_Config_Ebay_ID (final int ExternalSystem_Config_Ebay_ID)
	{
		if (ExternalSystem_Config_Ebay_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_Ebay_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_Ebay_ID, ExternalSystem_Config_Ebay_ID);
	}

	@Override
	public int getExternalSystem_Config_Ebay_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Ebay_ID);
	}

	@Override
	public void setExternalSystem_Config_Ebay_Mapping_ID (final int ExternalSystem_Config_Ebay_Mapping_ID)
	{
		if (ExternalSystem_Config_Ebay_Mapping_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Ebay_Mapping_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Ebay_Mapping_ID, ExternalSystem_Config_Ebay_Mapping_ID);
	}

	@Override
	public int getExternalSystem_Config_Ebay_Mapping_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Ebay_Mapping_ID);
	}

	@Override
	public void setIsInvoiceEmailEnabled (final boolean IsInvoiceEmailEnabled)
	{
		set_Value (COLUMNNAME_IsInvoiceEmailEnabled, IsInvoiceEmailEnabled);
	}

	@Override
	public boolean isInvoiceEmailEnabled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoiceEmailEnabled);
	}

	/** 
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** PayPal = L */
	public static final String PAYMENTRULE_PayPal = "L";
	/** PayPal Extern = V */
	public static final String PAYMENTRULE_PayPalExtern = "V";
	/** Kreditkarte Extern = U */
	public static final String PAYMENTRULE_KreditkarteExtern = "U";
	/** Sofortüberweisung = R */
	public static final String PAYMENTRULE_Sofortueberweisung = "R";
	@Override
	public void setPaymentRule (final @Nullable java.lang.String PaymentRule)
	{
		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	@Override
	public java.lang.String getPaymentRule() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRule);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}