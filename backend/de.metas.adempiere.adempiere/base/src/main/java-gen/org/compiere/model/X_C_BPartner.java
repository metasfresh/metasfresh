// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner extends org.compiere.model.PO implements I_C_BPartner, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1416296865L;

    /** Standard Constructor */
    public X_C_BPartner (final Properties ctx, final int C_BPartner_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAcqusitionCost (final @Nullable BigDecimal AcqusitionCost)
	{
		set_Value (COLUMNNAME_AcqusitionCost, AcqusitionCost);
	}

	@Override
	public BigDecimal getAcqusitionCost() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AcqusitionCost);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAddress1 (final @Nullable java.lang.String Address1)
	{
		throw new IllegalArgumentException ("Address1 is virtual column");	}

	@Override
	public java.lang.String getAddress1() 
	{
		return get_ValueAsString(COLUMNNAME_Address1);
	}

	/** 
	 * AD_Language AD_Reference_ID=327
	 * Reference name: AD_Language System
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=327;
	@Override
	public void setAD_Language (final @Nullable java.lang.String AD_Language)
	{
		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language() 
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public void setAD_OrgBP_ID (final int AD_OrgBP_ID)
	{
		if (AD_OrgBP_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgBP_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgBP_ID, AD_OrgBP_ID);
	}

	@Override
	public int getAD_OrgBP_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgBP_ID);
	}

	@Override
	public org.compiere.model.I_AD_Org_Mapping getAD_Org_Mapping()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Org_Mapping_ID, org.compiere.model.I_AD_Org_Mapping.class);
	}

	@Override
	public void setAD_Org_Mapping(final org.compiere.model.I_AD_Org_Mapping AD_Org_Mapping)
	{
		set_ValueFromPO(COLUMNNAME_AD_Org_Mapping_ID, org.compiere.model.I_AD_Org_Mapping.class, AD_Org_Mapping);
	}

	@Override
	public void setAD_Org_Mapping_ID (final int AD_Org_Mapping_ID)
	{
		if (AD_Org_Mapping_ID < 1) 
			set_Value (COLUMNNAME_AD_Org_Mapping_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Org_Mapping_ID, AD_Org_Mapping_ID);
	}

	@Override
	public int getAD_Org_Mapping_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Org_Mapping_ID);
	}

	/** 
	 * AlbertaRole AD_Reference_ID=541322
	 * Reference name: AlbertaRole
	 */
	public static final int ALBERTAROLE_AD_Reference_ID=541322;
	/** Caregiver = CG */
	public static final String ALBERTAROLE_Caregiver = "CG";
	/** Caretaker = CT */
	public static final String ALBERTAROLE_Caretaker = "CT";
	/** General Practitioner = GP */
	public static final String ALBERTAROLE_GeneralPractitioner = "GP";
	/** Health Insurance = HI */
	public static final String ALBERTAROLE_HealthInsurance = "HI";
	/** Hostpital = HO */
	public static final String ALBERTAROLE_Hostpital = "HO";
	/** Main Producer = MP */
	public static final String ALBERTAROLE_MainProducer = "MP";
	/** Nursing Home = NH */
	public static final String ALBERTAROLE_NursingHome = "NH";
	/** Nursing Service = NS */
	public static final String ALBERTAROLE_NursingService = "NS";
	/** Payer = PA */
	public static final String ALBERTAROLE_Payer = "PA";
	/** Doctor = PD */
	public static final String ALBERTAROLE_Doctor = "PD";
	/** Pharmacy = PH */
	public static final String ALBERTAROLE_Pharmacy = "PH";
	/** Preferred Pharmacy = PP */
	public static final String ALBERTAROLE_PreferredPharmacy = "PP";
	/** Pacient = PT */
	public static final String ALBERTAROLE_Pacient = "PT";
	@Override
	public void setAlbertaRole (final @Nullable java.lang.String AlbertaRole)
	{
		set_Value (COLUMNNAME_AlbertaRole, AlbertaRole);
	}

	@Override
	public java.lang.String getAlbertaRole() 
	{
		return get_ValueAsString(COLUMNNAME_AlbertaRole);
	}

	@Override
	public void setAlbertaTitle (final @Nullable java.lang.String AlbertaTitle)
	{
		throw new IllegalArgumentException ("AlbertaTitle is virtual column");	}

	@Override
	public java.lang.String getAlbertaTitle() 
	{
		return get_ValueAsString(COLUMNNAME_AlbertaTitle);
	}

	@Override
	public void setAllowConsolidateInOut (final boolean AllowConsolidateInOut)
	{
		set_Value (COLUMNNAME_AllowConsolidateInOut, AllowConsolidateInOut);
	}

	@Override
	public boolean isAllowConsolidateInOut() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AllowConsolidateInOut);
	}

	@Override
	public void setBPartner_Parent_ID (final int BPartner_Parent_ID)
	{
		if (BPartner_Parent_ID < 1) 
			set_Value (COLUMNNAME_BPartner_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_BPartner_Parent_ID, BPartner_Parent_ID);
	}

	@Override
	public int getBPartner_Parent_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_BPartner_Parent_ID);
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
	public void setC_BPartner_SalesRep_ID (final int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, C_BPartner_SalesRep_ID);
	}

	@Override
	public int getC_BPartner_SalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_SalesRep_ID);
	}

	@Override
	public org.compiere.model.I_C_BP_Group getC_BP_Group()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(final org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	@Override
	public void setC_BP_Group_ID (final int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, C_BP_Group_ID);
	}

	@Override
	public int getC_BP_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Group_ID);
	}

	@Override
	public org.compiere.model.I_C_Dunning getC_Dunning()
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class);
	}

	@Override
	public void setC_Dunning(final org.compiere.model.I_C_Dunning C_Dunning)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class, C_Dunning);
	}

	@Override
	public void setC_Dunning_ID (final int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1) 
			set_Value (COLUMNNAME_C_Dunning_ID, null);
		else 
			set_Value (COLUMNNAME_C_Dunning_ID, C_Dunning_ID);
	}

	@Override
	public int getC_Dunning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Dunning_ID);
	}

	@Override
	public void setCertificateOfRegistrationCustomer (final boolean CertificateOfRegistrationCustomer)
	{
		set_Value (COLUMNNAME_CertificateOfRegistrationCustomer, CertificateOfRegistrationCustomer);
	}

	@Override
	public boolean isCertificateOfRegistrationCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_CertificateOfRegistrationCustomer);
	}

	@Override
	public void setCertificateOfRegistrationVendor (final boolean CertificateOfRegistrationVendor)
	{
		set_Value (COLUMNNAME_CertificateOfRegistrationVendor, CertificateOfRegistrationVendor);
	}

	@Override
	public boolean isCertificateOfRegistrationVendor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_CertificateOfRegistrationVendor);
	}

	@Override
	public void setC_Greeting_ID (final int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1) 
			set_Value (COLUMNNAME_C_Greeting_ID, null);
		else 
			set_Value (COLUMNNAME_C_Greeting_ID, C_Greeting_ID);
	}

	@Override
	public int getC_Greeting_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Greeting_ID);
	}

	@Override
	public org.compiere.model.I_C_Incoterms getC_Incoterms_Customer()
	{
		return get_ValueAsPO(COLUMNNAME_C_Incoterms_Customer_ID, org.compiere.model.I_C_Incoterms.class);
	}

	@Override
	public void setC_Incoterms_Customer(final org.compiere.model.I_C_Incoterms C_Incoterms_Customer)
	{
		set_ValueFromPO(COLUMNNAME_C_Incoterms_Customer_ID, org.compiere.model.I_C_Incoterms.class, C_Incoterms_Customer);
	}

	@Override
	public void setC_Incoterms_Customer_ID (final int C_Incoterms_Customer_ID)
	{
		if (C_Incoterms_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_Incoterms_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_Incoterms_Customer_ID, C_Incoterms_Customer_ID);
	}

	@Override
	public int getC_Incoterms_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Incoterms_Customer_ID);
	}

	@Override
	public org.compiere.model.I_C_Incoterms getC_Incoterms_Vendor()
	{
		return get_ValueAsPO(COLUMNNAME_C_Incoterms_Vendor_ID, org.compiere.model.I_C_Incoterms.class);
	}

	@Override
	public void setC_Incoterms_Vendor(final org.compiere.model.I_C_Incoterms C_Incoterms_Vendor)
	{
		set_ValueFromPO(COLUMNNAME_C_Incoterms_Vendor_ID, org.compiere.model.I_C_Incoterms.class, C_Incoterms_Vendor);
	}

	@Override
	public void setC_Incoterms_Vendor_ID (final int C_Incoterms_Vendor_ID)
	{
		if (C_Incoterms_Vendor_ID < 1) 
			set_Value (COLUMNNAME_C_Incoterms_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_C_Incoterms_Vendor_ID, C_Incoterms_Vendor_ID);
	}

	@Override
	public int getC_Incoterms_Vendor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Incoterms_Vendor_ID);
	}

	@Override
	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class);
	}

	@Override
	public void setC_InvoiceSchedule(final org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class, C_InvoiceSchedule);
	}

	@Override
	public void setC_InvoiceSchedule_ID (final int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, C_InvoiceSchedule_ID);
	}

	@Override
	public int getC_InvoiceSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceSchedule_ID);
	}

	@Override
	public void setCity (final @Nullable java.lang.String City)
	{
		throw new IllegalArgumentException ("City is virtual column");	}

	@Override
	public java.lang.String getCity() 
	{
		return get_ValueAsString(COLUMNNAME_City);
	}

	@Override
	public void setCompanyName (final @Nullable java.lang.String CompanyName)
	{
		set_Value (COLUMNNAME_CompanyName, CompanyName);
	}

	@Override
	public java.lang.String getCompanyName() 
	{
		return get_ValueAsString(COLUMNNAME_CompanyName);
	}

	@Override
	public void setContactStatusInfoCustomer (final boolean ContactStatusInfoCustomer)
	{
		set_Value (COLUMNNAME_ContactStatusInfoCustomer, ContactStatusInfoCustomer);
	}

	@Override
	public boolean isContactStatusInfoCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ContactStatusInfoCustomer);
	}

	@Override
	public void setContactStatusInfoVendor (final boolean ContactStatusInfoVendor)
	{
		set_Value (COLUMNNAME_ContactStatusInfoVendor, ContactStatusInfoVendor);
	}

	@Override
	public boolean isContactStatusInfoVendor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ContactStatusInfoVendor);
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
	public void setCreateSO (final @Nullable java.lang.String CreateSO)
	{
		set_Value (COLUMNNAME_CreateSO, CreateSO);
	}

	@Override
	public java.lang.String getCreateSO() 
	{
		return get_ValueAsString(COLUMNNAME_CreateSO);
	}

	@Override
	public void setCreditLimitIndicator (final @Nullable java.lang.String CreditLimitIndicator)
	{
		throw new IllegalArgumentException ("CreditLimitIndicator is virtual column");	}

	@Override
	public java.lang.String getCreditLimitIndicator() 
	{
		return get_ValueAsString(COLUMNNAME_CreditLimitIndicator);
	}

	@Override
	public void setCreditorId (final int CreditorId)
	{
		set_Value (COLUMNNAME_CreditorId, CreditorId);
	}

	@Override
	public int getCreditorId() 
	{
		return get_ValueAsInt(COLUMNNAME_CreditorId);
	}

	@Override
	public org.eevolution.model.I_C_TaxGroup getC_TaxGroup()
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxGroup_ID, org.eevolution.model.I_C_TaxGroup.class);
	}

	@Override
	public void setC_TaxGroup(final org.eevolution.model.I_C_TaxGroup C_TaxGroup)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxGroup_ID, org.eevolution.model.I_C_TaxGroup.class, C_TaxGroup);
	}

	@Override
	public void setC_TaxGroup_ID (final int C_TaxGroup_ID)
	{
		if (C_TaxGroup_ID < 1) 
			set_Value (COLUMNNAME_C_TaxGroup_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxGroup_ID, C_TaxGroup_ID);
	}

	@Override
	public int getC_TaxGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxGroup_ID);
	}

	@Override
	public void setCustomerNoAtVendor (final @Nullable java.lang.String CustomerNoAtVendor)
	{
		set_Value (COLUMNNAME_CustomerNoAtVendor, CustomerNoAtVendor);
	}

	@Override
	public java.lang.String getCustomerNoAtVendor() 
	{
		return get_ValueAsString(COLUMNNAME_CustomerNoAtVendor);
	}

	@Override
	public void setDateHaddexCheck (final @Nullable java.sql.Timestamp DateHaddexCheck)
	{
		set_Value (COLUMNNAME_DateHaddexCheck, DateHaddexCheck);
	}

	@Override
	public java.sql.Timestamp getDateHaddexCheck() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateHaddexCheck);
	}

	@Override
	public void setDebtorId (final int DebtorId)
	{
		set_Value (COLUMNNAME_DebtorId, DebtorId);
	}

	@Override
	public int getDebtorId() 
	{
		return get_ValueAsInt(COLUMNNAME_DebtorId);
	}

	@Override
	public void setDefaultShipTo_City (final @Nullable java.lang.String DefaultShipTo_City)
	{
		throw new IllegalArgumentException ("DefaultShipTo_City is virtual column");	}

	@Override
	public java.lang.String getDefaultShipTo_City() 
	{
		return get_ValueAsString(COLUMNNAME_DefaultShipTo_City);
	}

	@Override
	public void setDefaultShipTo_Postal (final @Nullable java.lang.String DefaultShipTo_Postal)
	{
		throw new IllegalArgumentException ("DefaultShipTo_Postal is virtual column");	}

	@Override
	public java.lang.String getDefaultShipTo_Postal() 
	{
		return get_ValueAsString(COLUMNNAME_DefaultShipTo_Postal);
	}

	/** 
	 * DeliveryRule AD_Reference_ID=151
	 * Reference name: C_Order DeliveryRule
	 */
	public static final int DELIVERYRULE_AD_Reference_ID=151;
	/** AfterReceipt = R */
	public static final String DELIVERYRULE_AfterReceipt = "R";
	/** Availability = A */
	public static final String DELIVERYRULE_Availability = "A";
	/** CompleteLine = L */
	public static final String DELIVERYRULE_CompleteLine = "L";
	/** CompleteOrder = O */
	public static final String DELIVERYRULE_CompleteOrder = "O";
	/** Force = F */
	public static final String DELIVERYRULE_Force = "F";
	/** Manual = M */
	public static final String DELIVERYRULE_Manual = "M";
	/** MitNaechsterAbolieferung = S */
	public static final String DELIVERYRULE_MitNaechsterAbolieferung = "S";
	@Override
	public void setDeliveryRule (final @Nullable java.lang.String DeliveryRule)
	{
		set_Value (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	@Override
	public java.lang.String getDeliveryRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryRule);
	}

	/** 
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	/** Normalpost = NP */
	public static final String DELIVERYVIARULE_Normalpost = "NP";
	/** Luftpost = LU */
	public static final String DELIVERYVIARULE_Luftpost = "LU";
	@Override
	public void setDeliveryViaRule (final @Nullable java.lang.String DeliveryViaRule)
	{
		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	@Override
	public java.lang.String getDeliveryViaRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryViaRule);
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
	public void setDunningGrace (final @Nullable java.sql.Timestamp DunningGrace)
	{
		set_Value (COLUMNNAME_DunningGrace, DunningGrace);
	}

	@Override
	public java.sql.Timestamp getDunningGrace() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DunningGrace);
	}

	@Override
	public void setDUNS (final @Nullable java.lang.String DUNS)
	{
		set_Value (COLUMNNAME_DUNS, DUNS);
	}

	@Override
	public java.lang.String getDUNS() 
	{
		return get_ValueAsString(COLUMNNAME_DUNS);
	}

	@Override
	public void setEMail (final @Nullable java.lang.String EMail)
	{
		throw new IllegalArgumentException ("EMail is virtual column");	}

	@Override
	public java.lang.String getEMail() 
	{
		return get_ValueAsString(COLUMNNAME_EMail);
	}

	@Override
	public void setEORI (final @Nullable java.lang.String EORI)
	{
		set_Value (COLUMNNAME_EORI, EORI);
	}

	@Override
	public java.lang.String getEORI() 
	{
		return get_ValueAsString(COLUMNNAME_EORI);
	}

	@Override
	public void setExcludeFromPromotions (final boolean ExcludeFromPromotions)
	{
		set_Value (COLUMNNAME_ExcludeFromPromotions, ExcludeFromPromotions);
	}

	@Override
	public boolean isExcludeFromPromotions() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ExcludeFromPromotions);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setFirstname (final @Nullable java.lang.String Firstname)
	{
		set_Value (COLUMNNAME_Firstname, Firstname);
	}

	@Override
	public java.lang.String getFirstname() 
	{
		return get_ValueAsString(COLUMNNAME_Firstname);
	}

	@Override
	public void setFirstSale (final @Nullable java.sql.Timestamp FirstSale)
	{
		set_Value (COLUMNNAME_FirstSale, FirstSale);
	}

	@Override
	public java.sql.Timestamp getFirstSale() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_FirstSale);
	}

	@Override
	public void setFlatDiscount (final @Nullable BigDecimal FlatDiscount)
	{
		set_Value (COLUMNNAME_FlatDiscount, FlatDiscount);
	}

	@Override
	public BigDecimal getFlatDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FlatDiscount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * FreightCostRule AD_Reference_ID=153
	 * Reference name: C_Order FreightCostRule
	 */
	public static final int FREIGHTCOSTRULE_AD_Reference_ID=153;
	/** FreightIncluded = I */
	public static final String FREIGHTCOSTRULE_FreightIncluded = "I";
	/** FixPrice = F */
	public static final String FREIGHTCOSTRULE_FixPrice = "F";
	/** Calculated = C */
	public static final String FREIGHTCOSTRULE_Calculated = "C";
	/** Line = L */
	public static final String FREIGHTCOSTRULE_Line = "L";
	/** Versandkostenpauschale = P */
	public static final String FREIGHTCOSTRULE_Versandkostenpauschale = "P";
	@Override
	public void setFreightCostRule (final @Nullable java.lang.String FreightCostRule)
	{
		set_Value (COLUMNNAME_FreightCostRule, FreightCostRule);
	}

	@Override
	public java.lang.String getFreightCostRule() 
	{
		return get_ValueAsString(COLUMNNAME_FreightCostRule);
	}

	@Override
	public void setGDPCertificateCustomer (final boolean GDPCertificateCustomer)
	{
		set_Value (COLUMNNAME_GDPCertificateCustomer, GDPCertificateCustomer);
	}

	@Override
	public boolean isGDPCertificateCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_GDPCertificateCustomer);
	}

	@Override
	public void setGDPCertificateVendor (final boolean GDPCertificateVendor)
	{
		set_Value (COLUMNNAME_GDPCertificateVendor, GDPCertificateVendor);
	}

	@Override
	public boolean isGDPCertificateVendor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_GDPCertificateVendor);
	}

	@Override
	public void setGlobalId (final @Nullable java.lang.String GlobalId)
	{
		set_Value (COLUMNNAME_GlobalId, GlobalId);
	}

	@Override
	public java.lang.String getGlobalId() 
	{
		return get_ValueAsString(COLUMNNAME_GlobalId);
	}

	@Override
	public void setHaddexControlNr (final @Nullable java.lang.String HaddexControlNr)
	{
		set_Value (COLUMNNAME_HaddexControlNr, HaddexControlNr);
	}

	@Override
	public java.lang.String getHaddexControlNr() 
	{
		return get_ValueAsString(COLUMNNAME_HaddexControlNr);
	}

	@Override
	public void setInternalName (final @Nullable java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public org.compiere.model.I_AD_PrintFormat getInvoice_PrintFormat()
	{
		return get_ValueAsPO(COLUMNNAME_Invoice_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setInvoice_PrintFormat(final org.compiere.model.I_AD_PrintFormat Invoice_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_Invoice_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, Invoice_PrintFormat);
	}

	@Override
	public void setInvoice_PrintFormat_ID (final int Invoice_PrintFormat_ID)
	{
		if (Invoice_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_Invoice_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_Invoice_PrintFormat_ID, Invoice_PrintFormat_ID);
	}

	@Override
	public int getInvoice_PrintFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Invoice_PrintFormat_ID);
	}

	/** 
	 * InvoiceRule AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int INVOICERULE_AD_Reference_ID=150;
	/** AfterOrderDelivered = O */
	public static final String INVOICERULE_AfterOrderDelivered = "O";
	/** AfterDelivery = D */
	public static final String INVOICERULE_AfterDelivery = "D";
	/** CustomerScheduleAfterDelivery = S */
	public static final String INVOICERULE_CustomerScheduleAfterDelivery = "S";
	/** Immediate = I */
	public static final String INVOICERULE_Immediate = "I";
	/** OrderCompletelyDelivered = C */
	public static final String INVOICERULE_OrderCompletelyDelivered = "C";
	/** After Pick = P */
	public static final String INVOICERULE_AfterPick = "P";
	@Override
	public void setInvoiceRule (final @Nullable java.lang.String InvoiceRule)
	{
		set_Value (COLUMNNAME_InvoiceRule, InvoiceRule);
	}

	@Override
	public java.lang.String getInvoiceRule() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceRule);
	}

	@Override
	public void setIsAggregatePO (final boolean IsAggregatePO)
	{
		set_Value (COLUMNNAME_IsAggregatePO, IsAggregatePO);
	}

	@Override
	public boolean isAggregatePO() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAggregatePO);
	}

	@Override
	public void setIsAlbertaDoctor (final boolean IsAlbertaDoctor)
	{
		throw new IllegalArgumentException ("IsAlbertaDoctor is virtual column");	}

	@Override
	public boolean isAlbertaDoctor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAlbertaDoctor);
	}

	@Override
	public void setIsAllowActionPrice (final boolean IsAllowActionPrice)
	{
		set_Value (COLUMNNAME_IsAllowActionPrice, IsAllowActionPrice);
	}

	@Override
	public boolean isAllowActionPrice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowActionPrice);
	}

	@Override
	public void setIsAllowPriceMutation (final boolean IsAllowPriceMutation)
	{
		set_Value (COLUMNNAME_IsAllowPriceMutation, IsAllowPriceMutation);
	}

	@Override
	public boolean isAllowPriceMutation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowPriceMutation);
	}

	@Override
	public void setIsArchived (final boolean IsArchived)
	{
		throw new IllegalArgumentException ("IsArchived is virtual column");	}

	@Override
	public boolean isArchived() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsArchived);
	}

	@Override
	public void setIsCompany (final boolean IsCompany)
	{
		set_Value (COLUMNNAME_IsCompany, IsCompany);
	}

	@Override
	public boolean isCompany() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCompany);
	}

	@Override
	public void setIsCreateDefaultPOReference (final boolean IsCreateDefaultPOReference)
	{
		set_Value (COLUMNNAME_IsCreateDefaultPOReference, IsCreateDefaultPOReference);
	}

	@Override
	public boolean isCreateDefaultPOReference() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreateDefaultPOReference);
	}

	@Override
	public void setIsCustomer (final boolean IsCustomer)
	{
		set_Value (COLUMNNAME_IsCustomer, IsCustomer);
	}

	@Override
	public boolean isCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCustomer);
	}

	@Override
	public void setIsDiscountPrinted (final boolean IsDiscountPrinted)
	{
		set_Value (COLUMNNAME_IsDiscountPrinted, IsDiscountPrinted);
	}

	@Override
	public boolean isDiscountPrinted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDiscountPrinted);
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
	public void setIsEmployee (final boolean IsEmployee)
	{
		set_Value (COLUMNNAME_IsEmployee, IsEmployee);
	}

	@Override
	public boolean isEmployee() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEmployee);
	}

	@Override
	public void setIsHaddexCheck (final boolean IsHaddexCheck)
	{
		set_Value (COLUMNNAME_IsHaddexCheck, IsHaddexCheck);
	}

	@Override
	public boolean isHaddexCheck() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsHaddexCheck);
	}

	@Override
	public void setIsManufacturer (final boolean IsManufacturer)
	{
		set_Value (COLUMNNAME_IsManufacturer, IsManufacturer);
	}

	@Override
	public boolean isManufacturer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManufacturer);
	}

	@Override
	public void setIsOneTime (final boolean IsOneTime)
	{
		set_Value (COLUMNNAME_IsOneTime, IsOneTime);
	}

	@Override
	public boolean isOneTime() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOneTime);
	}

	@Override
	public void setIsPOTaxExempt (final boolean IsPOTaxExempt)
	{
		set_Value (COLUMNNAME_IsPOTaxExempt, IsPOTaxExempt);
	}

	@Override
	public boolean isPOTaxExempt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPOTaxExempt);
	}

	@Override
	public void setIsProspect (final boolean IsProspect)
	{
		set_Value (COLUMNNAME_IsProspect, IsProspect);
	}

	@Override
	public boolean isProspect() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsProspect);
	}

	@Override
	public void setIsSalesPartnerRequired (final boolean IsSalesPartnerRequired)
	{
		set_Value (COLUMNNAME_IsSalesPartnerRequired, IsSalesPartnerRequired);
	}

	@Override
	public boolean isSalesPartnerRequired() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSalesPartnerRequired);
	}

	@Override
	public void setIsSalesRep (final boolean IsSalesRep)
	{
		set_Value (COLUMNNAME_IsSalesRep, IsSalesRep);
	}

	@Override
	public boolean isSalesRep() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSalesRep);
	}

	@Override
	public void setIsSEPASigned (final boolean IsSEPASigned)
	{
		set_Value (COLUMNNAME_IsSEPASigned, IsSEPASigned);
	}

	@Override
	public boolean isSEPASigned() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSEPASigned);
	}

	@Override
	public void setIsShippingNotificationEmail (final boolean IsShippingNotificationEmail)
	{
		set_Value (COLUMNNAME_IsShippingNotificationEmail, IsShippingNotificationEmail);
	}

	@Override
	public boolean isShippingNotificationEmail() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShippingNotificationEmail);
	}

	@Override
	public void setIsSummary (final boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, IsSummary);
	}

	@Override
	public boolean isSummary() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSummary);
	}

	@Override
	public void setIsTaxExempt (final boolean IsTaxExempt)
	{
		set_Value (COLUMNNAME_IsTaxExempt, IsTaxExempt);
	}

	@Override
	public boolean isTaxExempt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxExempt);
	}

	@Override
	public void setIsVendor (final boolean IsVendor)
	{
		set_Value (COLUMNNAME_IsVendor, IsVendor);
	}

	@Override
	public boolean isVendor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsVendor);
	}

	@Override
	public void setKundencockpit_includedTab1 (final @Nullable java.lang.String Kundencockpit_includedTab1)
	{
		set_Value (COLUMNNAME_Kundencockpit_includedTab1, Kundencockpit_includedTab1);
	}

	@Override
	public java.lang.String getKundencockpit_includedTab1() 
	{
		return get_ValueAsString(COLUMNNAME_Kundencockpit_includedTab1);
	}

	@Override
	public void setKundencockpit_includedTab2 (final @Nullable java.lang.String Kundencockpit_includedTab2)
	{
		set_Value (COLUMNNAME_Kundencockpit_includedTab2, Kundencockpit_includedTab2);
	}

	@Override
	public java.lang.String getKundencockpit_includedTab2() 
	{
		return get_ValueAsString(COLUMNNAME_Kundencockpit_includedTab2);
	}

	@Override
	public void setKundencockpit_includedTab3 (final @Nullable java.lang.String Kundencockpit_includedTab3)
	{
		set_Value (COLUMNNAME_Kundencockpit_includedTab3, Kundencockpit_includedTab3);
	}

	@Override
	public java.lang.String getKundencockpit_includedTab3() 
	{
		return get_ValueAsString(COLUMNNAME_Kundencockpit_includedTab3);
	}

	@Override
	public void setLastname (final @Nullable java.lang.String Lastname)
	{
		set_Value (COLUMNNAME_Lastname, Lastname);
	}

	@Override
	public java.lang.String getLastname() 
	{
		return get_ValueAsString(COLUMNNAME_Lastname);
	}

	@Override
	public org.compiere.model.I_AD_Image getLogo()
	{
		return get_ValueAsPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setLogo(final org.compiere.model.I_AD_Image Logo)
	{
		set_ValueFromPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class, Logo);
	}

	@Override
	public void setLogo_ID (final int Logo_ID)
	{
		if (Logo_ID < 1) 
			set_Value (COLUMNNAME_Logo_ID, null);
		else 
			set_Value (COLUMNNAME_Logo_ID, Logo_ID);
	}

	@Override
	public int getLogo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Logo_ID);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema()
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setM_DiscountSchema(final org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, M_DiscountSchema);
	}

	@Override
	public void setM_DiscountSchema_ID (final int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, M_DiscountSchema_ID);
	}

	@Override
	public int getM_DiscountSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_ID);
	}

	@Override
	public void setMemo (final @Nullable java.lang.String Memo)
	{
		set_Value (COLUMNNAME_Memo, Memo);
	}

	@Override
	public java.lang.String getMemo() 
	{
		return get_ValueAsString(COLUMNNAME_Memo);
	}

	@Override
	public void setMemo_Delivery (final @Nullable java.lang.String Memo_Delivery)
	{
		set_Value (COLUMNNAME_Memo_Delivery, Memo_Delivery);
	}

	@Override
	public java.lang.String getMemo_Delivery() 
	{
		return get_ValueAsString(COLUMNNAME_Memo_Delivery);
	}

	@Override
	public void setMemo_Invoicing (final @Nullable java.lang.String Memo_Invoicing)
	{
		set_Value (COLUMNNAME_Memo_Invoicing, Memo_Invoicing);
	}

	@Override
	public java.lang.String getMemo_Invoicing() 
	{
		return get_ValueAsString(COLUMNNAME_Memo_Invoicing);
	}

	@Override
	public void setM_FreightCost_ID (final int M_FreightCost_ID)
	{
		if (M_FreightCost_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCost_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCost_ID, M_FreightCost_ID);
	}

	@Override
	public int getM_FreightCost_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_FreightCost_ID);
	}

	@Override
	public void setMKTG_Campaign_ID (final int MKTG_Campaign_ID)
	{
		if (MKTG_Campaign_ID < 1) 
			set_Value (COLUMNNAME_MKTG_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_MKTG_Campaign_ID, MKTG_Campaign_ID);
	}

	@Override
	public int getMKTG_Campaign_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MKTG_Campaign_ID);
	}

	@Override
	public void setM_PriceList_ID (final int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, M_PriceList_ID);
	}

	@Override
	public int getM_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_ID);
	}

	@Override
	public void setM_PricingSystem_ID (final int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, M_PricingSystem_ID);
	}

	@Override
	public int getM_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PricingSystem_ID);
	}

	/** 
	 * MRP_Exclude AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MRP_EXCLUDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MRP_EXCLUDE_Yes = "Y";
	/** No = N */
	public static final String MRP_EXCLUDE_No = "N";
	@Override
	public void setMRP_Exclude (final @Nullable java.lang.String MRP_Exclude)
	{
		set_Value (COLUMNNAME_MRP_Exclude, MRP_Exclude);
	}

	@Override
	public java.lang.String getMRP_Exclude() 
	{
		return get_ValueAsString(COLUMNNAME_MRP_Exclude);
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(final org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setNAICS (final @Nullable java.lang.String NAICS)
	{
		set_Value (COLUMNNAME_NAICS, NAICS);
	}

	@Override
	public java.lang.String getNAICS() 
	{
		return get_ValueAsString(COLUMNNAME_NAICS);
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
	public void setName2 (final @Nullable java.lang.String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	@Override
	public java.lang.String getName2() 
	{
		return get_ValueAsString(COLUMNNAME_Name2);
	}

	@Override
	public void setName3 (final @Nullable java.lang.String Name3)
	{
		set_Value (COLUMNNAME_Name3, Name3);
	}

	@Override
	public java.lang.String getName3() 
	{
		return get_ValueAsString(COLUMNNAME_Name3);
	}

	@Override
	public void setNumberEmployees (final int NumberEmployees)
	{
		set_Value (COLUMNNAME_NumberEmployees, NumberEmployees);
	}

	@Override
	public int getNumberEmployees() 
	{
		return get_ValueAsInt(COLUMNNAME_NumberEmployees);
	}

	@Override
	public void setOldValue (final @Nullable java.lang.String OldValue)
	{
		set_Value (COLUMNNAME_OldValue, OldValue);
	}

	@Override
	public java.lang.String getOldValue() 
	{
		return get_ValueAsString(COLUMNNAME_OldValue);
	}

	@Override
	public void setOld_Value_Customer (final @Nullable java.lang.String Old_Value_Customer)
	{
		set_Value (COLUMNNAME_Old_Value_Customer, Old_Value_Customer);
	}

	@Override
	public java.lang.String getOld_Value_Customer() 
	{
		return get_ValueAsString(COLUMNNAME_Old_Value_Customer);
	}

	@Override
	public void setOld_Value_Vendor (final @Nullable java.lang.String Old_Value_Vendor)
	{
		set_Value (COLUMNNAME_Old_Value_Vendor, Old_Value_Vendor);
	}

	@Override
	public java.lang.String getOld_Value_Vendor() 
	{
		return get_ValueAsString(COLUMNNAME_Old_Value_Vendor);
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
	public void setPaymentRule (final java.lang.String PaymentRule)
	{
		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	@Override
	public java.lang.String getPaymentRule() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRule);
	}

	/** 
	 * PaymentRulePO AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULEPO_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULEPO_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULEPO_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULEPO_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULEPO_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULEPO_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULEPO_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULEPO_Mixed = "M";
	/** PayPal = L */
	public static final String PAYMENTRULEPO_PayPal = "L";
	/** PayPal Extern = V */
	public static final String PAYMENTRULEPO_PayPalExtern = "V";
	/** Kreditkarte Extern = U */
	public static final String PAYMENTRULEPO_KreditkarteExtern = "U";
	/** Sofortüberweisung = R */
	public static final String PAYMENTRULEPO_Sofortueberweisung = "R";
	@Override
	public void setPaymentRulePO (final java.lang.String PaymentRulePO)
	{
		set_Value (COLUMNNAME_PaymentRulePO, PaymentRulePO);
	}

	@Override
	public java.lang.String getPaymentRulePO() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRulePO);
	}

	@Override
	public void setPhone2 (final @Nullable java.lang.String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	@Override
	public java.lang.String getPhone2() 
	{
		return get_ValueAsString(COLUMNNAME_Phone2);
	}

	/** 
	 * PO_DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int PO_DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String PO_DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String PO_DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String PO_DELIVERYVIARULE_Shipper = "S";
	/** Normalpost = NP */
	public static final String PO_DELIVERYVIARULE_Normalpost = "NP";
	/** Luftpost = LU */
	public static final String PO_DELIVERYVIARULE_Luftpost = "LU";
	@Override
	public void setPO_DeliveryViaRule (final @Nullable java.lang.String PO_DeliveryViaRule)
	{
		set_Value (COLUMNNAME_PO_DeliveryViaRule, PO_DeliveryViaRule);
	}

	@Override
	public java.lang.String getPO_DeliveryViaRule() 
	{
		return get_ValueAsString(COLUMNNAME_PO_DeliveryViaRule);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getPO_DiscountSchema()
	{
		return get_ValueAsPO(COLUMNNAME_PO_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setPO_DiscountSchema(final org.compiere.model.I_M_DiscountSchema PO_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_PO_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, PO_DiscountSchema);
	}

	@Override
	public void setPO_DiscountSchema_ID (final int PO_DiscountSchema_ID)
	{
		if (PO_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_PO_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_PO_DiscountSchema_ID, PO_DiscountSchema_ID);
	}

	@Override
	public int getPO_DiscountSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PO_DiscountSchema_ID);
	}

	/** 
	 * PO_Incoterm AD_Reference_ID=501599
	 * Reference name: Incoterms
	 */
	public static final int PO_INCOTERM_AD_Reference_ID=501599;
	/** EXW_AbWerk = EXW */
	public static final String PO_INCOTERM_EXW_AbWerk = "EXW";
	/** FCA_FreiSpediteur = FCA */
	public static final String PO_INCOTERM_FCA_FreiSpediteur = "FCA";
	/** FAS_FreiLaengsseitsSchiff = FAS */
	public static final String PO_INCOTERM_FAS_FreiLaengsseitsSchiff = "FAS";
	/** FOB_FreiAnBord = FOB */
	public static final String PO_INCOTERM_FOB_FreiAnBord = "FOB";
	/** CFR_KostenUndFracht = CFR */
	public static final String PO_INCOTERM_CFR_KostenUndFracht = "CFR";
	/** CIF_KostenVersicherungUndFracht = CIF */
	public static final String PO_INCOTERM_CIF_KostenVersicherungUndFracht = "CIF";
	/** CPT_FrachtPortoBezahltBis = CPT */
	public static final String PO_INCOTERM_CPT_FrachtPortoBezahltBis = "CPT";
	/** CIP_FrachtPortoUndVersicherungBezahltBis = CIP */
	public static final String PO_INCOTERM_CIP_FrachtPortoUndVersicherungBezahltBis = "CIP";
	/** DAF_FreiGrenze = DAF */
	public static final String PO_INCOTERM_DAF_FreiGrenze = "DAF";
	/** DES_FreiAbSchiff = DES */
	public static final String PO_INCOTERM_DES_FreiAbSchiff = "DES";
	/** DEQ_FreiAbKai = DEQ */
	public static final String PO_INCOTERM_DEQ_FreiAbKai = "DEQ";
	/** DDU_FreiUnverzollt = DDU */
	public static final String PO_INCOTERM_DDU_FreiUnverzollt = "DDU";
	/** DDP_Verzollt = DDP */
	public static final String PO_INCOTERM_DDP_Verzollt = "DDP";
	/** DAP - Delivered at Place = DAP */
	public static final String PO_INCOTERM_DAP_DeliveredAtPlace = "DAP";
	/** DPU_geliefertBenannterOrtEntladen = DPU */
	public static final String PO_INCOTERM_DPU_geliefertBenannterOrtEntladen = "DPU";
	@Override
	public void setPO_Incoterm (final @Nullable java.lang.String PO_Incoterm)
	{
		set_Value (COLUMNNAME_PO_Incoterm, PO_Incoterm);
	}

	@Override
	public java.lang.String getPO_Incoterm() 
	{
		return get_ValueAsString(COLUMNNAME_PO_Incoterm);
	}

	/** 
	 * PO_InvoiceRule AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int PO_INVOICERULE_AD_Reference_ID=150;
	/** AfterOrderDelivered = O */
	public static final String PO_INVOICERULE_AfterOrderDelivered = "O";
	/** AfterDelivery = D */
	public static final String PO_INVOICERULE_AfterDelivery = "D";
	/** CustomerScheduleAfterDelivery = S */
	public static final String PO_INVOICERULE_CustomerScheduleAfterDelivery = "S";
	/** Immediate = I */
	public static final String PO_INVOICERULE_Immediate = "I";
	/** OrderCompletelyDelivered = C */
	public static final String PO_INVOICERULE_OrderCompletelyDelivered = "C";
	/** After Pick = P */
	public static final String PO_INVOICERULE_AfterPick = "P";
	@Override
	public void setPO_InvoiceRule (final @Nullable java.lang.String PO_InvoiceRule)
	{
		set_Value (COLUMNNAME_PO_InvoiceRule, PO_InvoiceRule);
	}

	@Override
	public java.lang.String getPO_InvoiceRule() 
	{
		return get_ValueAsString(COLUMNNAME_PO_InvoiceRule);
	}

	@Override
	public void setPO_PaymentTerm_ID (final int PO_PaymentTerm_ID)
	{
		if (PO_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, PO_PaymentTerm_ID);
	}

	@Override
	public int getPO_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PO_PaymentTerm_ID);
	}

	@Override
	public void setPO_PriceList_ID (final int PO_PriceList_ID)
	{
		if (PO_PriceList_ID < 1) 
			set_Value (COLUMNNAME_PO_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PriceList_ID, PO_PriceList_ID);
	}

	@Override
	public int getPO_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PO_PriceList_ID);
	}

	@Override
	public void setPO_PricingSystem_ID (final int PO_PricingSystem_ID)
	{
		if (PO_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_PO_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PricingSystem_ID, PO_PricingSystem_ID);
	}

	@Override
	public int getPO_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PO_PricingSystem_ID);
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setPOReferencePattern (final @Nullable java.lang.String POReferencePattern)
	{
		set_Value (COLUMNNAME_POReferencePattern, POReferencePattern);
	}

	@Override
	public java.lang.String getPOReferencePattern() 
	{
		return get_ValueAsString(COLUMNNAME_POReferencePattern);
	}

	@Override
	public void setPostal (final @Nullable java.lang.String Postal)
	{
		throw new IllegalArgumentException ("Postal is virtual column");	}

	@Override
	public java.lang.String getPostal() 
	{
		return get_ValueAsString(COLUMNNAME_Postal);
	}

	@Override
	public void setPotentialLifeTimeValue (final @Nullable BigDecimal PotentialLifeTimeValue)
	{
		set_Value (COLUMNNAME_PotentialLifeTimeValue, PotentialLifeTimeValue);
	}

	@Override
	public BigDecimal getPotentialLifeTimeValue() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PotentialLifeTimeValue);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQMSCertificateCustomer (final boolean QMSCertificateCustomer)
	{
		set_Value (COLUMNNAME_QMSCertificateCustomer, QMSCertificateCustomer);
	}

	@Override
	public boolean isQMSCertificateCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_QMSCertificateCustomer);
	}

	@Override
	public void setQMSCertificateVendor (final boolean QMSCertificateVendor)
	{
		set_Value (COLUMNNAME_QMSCertificateVendor, QMSCertificateVendor);
	}

	@Override
	public boolean isQMSCertificateVendor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_QMSCertificateVendor);
	}

	@Override
	public void setQualification (final @Nullable java.lang.String Qualification)
	{
		set_Value (COLUMNNAME_Qualification, Qualification);
	}

	@Override
	public java.lang.String getQualification() 
	{
		return get_ValueAsString(COLUMNNAME_Qualification);
	}

	@Override
	public void setRating (final @Nullable java.lang.String Rating)
	{
		set_Value (COLUMNNAME_Rating, Rating);
	}

	@Override
	public java.lang.String getRating() 
	{
		return get_ValueAsString(COLUMNNAME_Rating);
	}

	@Override
	public void setReferenceNo (final @Nullable java.lang.String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	@Override
	public java.lang.String getReferenceNo() 
	{
		return get_ValueAsString(COLUMNNAME_ReferenceNo);
	}

	@Override
	public void setReferrer (final @Nullable java.lang.String Referrer)
	{
		set_Value (COLUMNNAME_Referrer, Referrer);
	}

	@Override
	public java.lang.String getReferrer() 
	{
		return get_ValueAsString(COLUMNNAME_Referrer);
	}

	@Override
	public void setReminderDateExtern (final @Nullable java.sql.Timestamp ReminderDateExtern)
	{
		set_Value (COLUMNNAME_ReminderDateExtern, ReminderDateExtern);
	}

	@Override
	public java.sql.Timestamp getReminderDateExtern() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ReminderDateExtern);
	}

	@Override
	public void setReminderDateIntern (final @Nullable java.sql.Timestamp ReminderDateIntern)
	{
		set_Value (COLUMNNAME_ReminderDateIntern, ReminderDateIntern);
	}

	@Override
	public java.sql.Timestamp getReminderDateIntern() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ReminderDateIntern);
	}

	/** 
	 * Salesgroup AD_Reference_ID=540635
	 * Reference name: C_BPartner_Salesgroup
	 */
	public static final int SALESGROUP_AD_Reference_ID=540635;
	/** Klassischer Detailhandel = 0030 */
	public static final String SALESGROUP_KlassischerDetailhandel = "0030";
	/** Discounter = 0010 */
	public static final String SALESGROUP_Discounter = "0010";
	/** Gastronomie und Grosshandel = 0020 */
	public static final String SALESGROUP_GastronomieUndGrosshandel = "0020";
	@Override
	public void setSalesgroup (final @Nullable java.lang.String Salesgroup)
	{
		set_Value (COLUMNNAME_Salesgroup, Salesgroup);
	}

	@Override
	public java.lang.String getSalesgroup() 
	{
		return get_ValueAsString(COLUMNNAME_Salesgroup);
	}

	@Override
	public void setSalesPartnerCode (final @Nullable java.lang.String SalesPartnerCode)
	{
		set_Value (COLUMNNAME_SalesPartnerCode, SalesPartnerCode);
	}

	@Override
	public java.lang.String getSalesPartnerCode() 
	{
		return get_ValueAsString(COLUMNNAME_SalesPartnerCode);
	}

	@Override
	public void setSalesRep_ID (final int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, SalesRep_ID);
	}

	@Override
	public int getSalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SalesRep_ID);
	}

	@Override
	public void setSalesRepIntern_ID (final int SalesRepIntern_ID)
	{
		if (SalesRepIntern_ID < 1) 
			set_Value (COLUMNNAME_SalesRepIntern_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRepIntern_ID, SalesRepIntern_ID);
	}

	@Override
	public int getSalesRepIntern_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SalesRepIntern_ID);
	}

	@Override
	public void setSalesVolume (final int SalesVolume)
	{
		set_Value (COLUMNNAME_SalesVolume, SalesVolume);
	}

	@Override
	public int getSalesVolume() 
	{
		return get_ValueAsInt(COLUMNNAME_SalesVolume);
	}

	@Override
	public void setSelfDisclosureCustomer (final boolean SelfDisclosureCustomer)
	{
		set_Value (COLUMNNAME_SelfDisclosureCustomer, SelfDisclosureCustomer);
	}

	@Override
	public boolean isSelfDisclosureCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_SelfDisclosureCustomer);
	}

	@Override
	public void setSelfDisclosureVendor (final boolean SelfDisclosureVendor)
	{
		set_Value (COLUMNNAME_SelfDisclosureVendor, SelfDisclosureVendor);
	}

	@Override
	public boolean isSelfDisclosureVendor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_SelfDisclosureVendor);
	}

	@Override
	public void setSendEMail (final boolean SendEMail)
	{
		set_Value (COLUMNNAME_SendEMail, SendEMail);
	}

	@Override
	public boolean isSendEMail() 
	{
		return get_ValueAsBoolean(COLUMNNAME_SendEMail);
	}

	@Override
	public void setShareOfCustomer (final int ShareOfCustomer)
	{
		set_Value (COLUMNNAME_ShareOfCustomer, ShareOfCustomer);
	}

	@Override
	public int getShareOfCustomer() 
	{
		return get_ValueAsInt(COLUMNNAME_ShareOfCustomer);
	}

	@Override
	public void setShelfLifeMinPct (final int ShelfLifeMinPct)
	{
		set_Value (COLUMNNAME_ShelfLifeMinPct, ShelfLifeMinPct);
	}

	@Override
	public int getShelfLifeMinPct() 
	{
		return get_ValueAsInt(COLUMNNAME_ShelfLifeMinPct);
	}

	/** 
	 * ShipmentAllocation_BestBefore_Policy AD_Reference_ID=541043
	 * Reference name: ShipmentAllocation_BestBefore_Policy
	 */
	public static final int SHIPMENTALLOCATION_BESTBEFORE_POLICY_AD_Reference_ID=541043;
	/** Newest_First = N */
	public static final String SHIPMENTALLOCATION_BESTBEFORE_POLICY_Newest_First = "N";
	/** Expiring_First = E */
	public static final String SHIPMENTALLOCATION_BESTBEFORE_POLICY_Expiring_First = "E";
	@Override
	public void setShipmentAllocation_BestBefore_Policy (final @Nullable java.lang.String ShipmentAllocation_BestBefore_Policy)
	{
		set_Value (COLUMNNAME_ShipmentAllocation_BestBefore_Policy, ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public java.lang.String getShipmentAllocation_BestBefore_Policy() 
	{
		return get_ValueAsString(COLUMNNAME_ShipmentAllocation_BestBefore_Policy);
	}

	@Override
	public void setShortDescription (final @Nullable java.lang.String ShortDescription)
	{
		set_Value (COLUMNNAME_ShortDescription, ShortDescription);
	}

	@Override
	public java.lang.String getShortDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ShortDescription);
	}

	@Override
	public void setSO_Description (final @Nullable java.lang.String SO_Description)
	{
		set_Value (COLUMNNAME_SO_Description, SO_Description);
	}

	@Override
	public java.lang.String getSO_Description() 
	{
		return get_ValueAsString(COLUMNNAME_SO_Description);
	}

	@Override
	public void setSO_DocTypeTarget_ID (final int SO_DocTypeTarget_ID)
	{
		if (SO_DocTypeTarget_ID < 1) 
			set_Value (COLUMNNAME_SO_DocTypeTarget_ID, null);
		else 
			set_Value (COLUMNNAME_SO_DocTypeTarget_ID, SO_DocTypeTarget_ID);
	}

	@Override
	public int getSO_DocTypeTarget_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SO_DocTypeTarget_ID);
	}

	@Override
	public void setSO_TargetDocTypeReason (final @Nullable java.lang.String SO_TargetDocTypeReason)
	{
		set_Value (COLUMNNAME_SO_TargetDocTypeReason, SO_TargetDocTypeReason);
	}

	@Override
	public java.lang.String getSO_TargetDocTypeReason() 
	{
		return get_ValueAsString(COLUMNNAME_SO_TargetDocTypeReason);
	}

	@Override
	public void setTaxID (final @Nullable java.lang.String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	@Override
	public java.lang.String getTaxID() 
	{
		return get_ValueAsString(COLUMNNAME_TaxID);
	}

	@Override
	public void setTitleShort (final @Nullable java.lang.String TitleShort)
	{
		throw new IllegalArgumentException ("TitleShort is virtual column");	}

	@Override
	public java.lang.String getTitleShort() 
	{
		return get_ValueAsString(COLUMNNAME_TitleShort);
	}

	@Override
	public void setURL (final @Nullable java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	@Override
	public java.lang.String getURL() 
	{
		return get_ValueAsString(COLUMNNAME_URL);
	}

	@Override
	public void setURL2 (final @Nullable java.lang.String URL2)
	{
		set_Value (COLUMNNAME_URL2, URL2);
	}

	@Override
	public java.lang.String getURL2() 
	{
		return get_ValueAsString(COLUMNNAME_URL2);
	}

	@Override
	public void setURL3 (final @Nullable java.lang.String URL3)
	{
		set_Value (COLUMNNAME_URL3, URL3);
	}

	@Override
	public java.lang.String getURL3() 
	{
		return get_ValueAsString(COLUMNNAME_URL3);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setVATaxID (final @Nullable java.lang.String VATaxID)
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	@Override
	public java.lang.String getVATaxID() 
	{
		return get_ValueAsString(COLUMNNAME_VATaxID);
	}

	@Override
	public void setVendorCategory (final @Nullable java.lang.String VendorCategory)
	{
		set_Value (COLUMNNAME_VendorCategory, VendorCategory);
	}

	@Override
	public java.lang.String getVendorCategory() 
	{
		return get_ValueAsString(COLUMNNAME_VendorCategory);
	}
}