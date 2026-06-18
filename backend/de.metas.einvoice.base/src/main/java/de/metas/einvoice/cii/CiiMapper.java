package de.metas.einvoice.cii;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.einvoice.EInvoiceRecipientConfig;
import de.metas.einvoice.cii.model.CodeType;
import de.metas.einvoice.cii.model.CountryIDType;
import de.metas.einvoice.cii.model.CrossIndustryInvoiceType;
import de.metas.einvoice.cii.model.CurrencyCodeType;
import de.metas.einvoice.cii.model.DateTimeType;
import de.metas.einvoice.cii.model.DocumentCodeType;
import de.metas.einvoice.cii.model.DocumentContextParameterType;
import de.metas.einvoice.cii.model.ExchangedDocumentContextType;
import de.metas.einvoice.cii.model.ExchangedDocumentType;
import de.metas.einvoice.cii.model.FormattedDateTimeType;
import de.metas.einvoice.cii.model.HeaderTradeAgreementType;
import de.metas.einvoice.cii.model.HeaderTradeDeliveryType;
import de.metas.einvoice.cii.model.HeaderTradeSettlementType;
import de.metas.einvoice.cii.model.IDType;
import de.metas.einvoice.cii.model.LegalOrganizationType;
import de.metas.einvoice.cii.model.ReferencedDocumentType;
import de.metas.einvoice.cii.model.SupplyChainTradeTransactionType;
import de.metas.einvoice.cii.model.TaxRegistrationType;
import de.metas.einvoice.cii.model.TextType;
import de.metas.einvoice.cii.model.TradeAddressType;
import de.metas.einvoice.cii.model.TradePartyType;
import de.metas.einvoice.cii.model.UniversalCommunicationType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

/**
 * Maps a metasfresh {@code C_Invoice} to the CII {@link CrossIndustryInvoiceType} structure.
 *
 * <p>Scope (task B3): ExchangedDocument header, seller / buyer trade parties,
 * and document-level references.
 * Invoice lines, VAT breakdown, and monetary totals are populated by later tasks (B4/B5).
 */
public class CiiMapper
{
	/** ZUGFeRD / Factur-X EN16931 guideline ID used in ExchangedDocumentContext. */
	private static final String GUIDELINE_ID_EN16931 =
			"urn:cen.eu:en16931:2017#compliant#urn:factur-x.eu:1p0:en16931";

	/** CII date format code for yyyyMMdd (UNCL2379 code 102). */
	private static final String DATE_FORMAT_102 = "102";

	@NonNull
	public CrossIndustryInvoiceType map(
			@NonNull final I_C_Invoice invoice,
			@NonNull final EInvoiceRecipientConfig recipientConfig)
	{
		final CrossIndustryInvoiceType cii = new CrossIndustryInvoiceType();

		cii.setExchangedDocumentContext(buildDocumentContext());
		cii.setExchangedDocument(buildExchangedDocument(invoice));
		cii.setSupplyChainTradeTransaction(buildTradeTransaction(invoice, recipientConfig));

		return cii;
	}

	// ===== ExchangedDocumentContext =====

	private ExchangedDocumentContextType buildDocumentContext()
	{
		final ExchangedDocumentContextType ctx = new ExchangedDocumentContextType();

		final DocumentContextParameterType guidelineParam = new DocumentContextParameterType();
		guidelineParam.setID(id(GUIDELINE_ID_EN16931));
		ctx.setGuidelineSpecifiedDocumentContextParameter(guidelineParam);

		return ctx;
	}

	// ===== ExchangedDocument (BT-1, BT-2, BT-3) =====

	private ExchangedDocumentType buildExchangedDocument(@NonNull final I_C_Invoice invoice)
	{
		final ExchangedDocumentType doc = new ExchangedDocumentType();

		// BT-1 Invoice number
		doc.setID(id(invoice.getDocumentNo()));

		// BT-3 Invoice type code
		doc.setTypeCode(buildTypeCode(invoice));

		// BT-2 Invoice issue date
		doc.setIssueDateTime(toDateTime(invoice.getDateInvoiced()));

		return doc;
	}

	private DocumentCodeType buildTypeCode(@NonNull final I_C_Invoice invoice)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.load(invoice.getC_DocType_ID(), I_C_DocType.class);
		final String docBaseType = docType != null ? docType.getDocBaseType() : "ARI";

		// CII_MAPPING.md §6: ARI → 380, ARC → 381
		final String typeCodeValue;
		if ("ARC".equals(docBaseType))
		{
			typeCodeValue = "381";
		}
		else
		{
			// ARI (and any other — eligibility check in EInvoiceConfigService is upstream)
			typeCodeValue = "380";
		}

		final DocumentCodeType typeCode = new DocumentCodeType();
		typeCode.setValue(typeCodeValue);
		return typeCode;
	}

	// ===== SupplyChainTradeTransaction =====

	private SupplyChainTradeTransactionType buildTradeTransaction(
			@NonNull final I_C_Invoice invoice,
			@NonNull final EInvoiceRecipientConfig recipientConfig)
	{
		final SupplyChainTradeTransactionType tx = new SupplyChainTradeTransactionType();

		// Lines are populated in later task B4; no IncludedSupplyChainTradeLineItem added here.

		tx.setApplicableHeaderTradeAgreement(buildTradeAgreement(invoice, recipientConfig));
		tx.setApplicableHeaderTradeDelivery(new HeaderTradeDeliveryType());
		tx.setApplicableHeaderTradeSettlement(buildTradeSettlement(invoice));

		return tx;
	}

	// ===== HeaderTradeAgreement =====

	private HeaderTradeAgreementType buildTradeAgreement(
			@NonNull final I_C_Invoice invoice,
			@NonNull final EInvoiceRecipientConfig recipientConfig)
	{
		final HeaderTradeAgreementType agreement = new HeaderTradeAgreementType();

		// BT-10 Buyer reference (Leitweg-ID)
		final String buyerReference = recipientConfig.getBuyerReference();
		if (buyerReference != null && !buyerReference.isEmpty())
		{
			final TextType ref = new TextType();
			ref.setValue(buyerReference);
			agreement.setBuyerReference(ref);
		}

		// Seller (BG-4/BG-5)
		agreement.setSellerTradeParty(buildSellerParty(invoice));

		// Buyer (BG-7/BG-8)
		agreement.setBuyerTradeParty(buildBuyerParty(invoice));

		// BT-13 Purchase order reference
		final String poRef = invoice.getPOReference();
		if (poRef != null && !poRef.isEmpty())
		{
			final ReferencedDocumentType buyerOrderDoc = new ReferencedDocumentType();
			buyerOrderDoc.setIssuerAssignedID(id(poRef));
			agreement.setBuyerOrderReferencedDocument(buyerOrderDoc);
		}

		return agreement;
	}

	// ===== Seller trade party =====

	private TradePartyType buildSellerParty(@NonNull final I_C_Invoice invoice)
	{
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final I_C_BPartner sellerBP = bPartnerDAO.retrieveOrgBPartner(
				Env.getCtx(),
				invoice.getAD_Org_ID(),
				I_C_BPartner.class,
				null);

		final TradePartyType seller = new TradePartyType();

		// BT-27 Seller name: CompanyName if set, else Name
		final String companyName = sellerBP.getCompanyName();
		seller.setName(text(companyName != null && !companyName.isEmpty()
				? companyName
				: sellerBP.getName()));

		// BT-30 Legal registration (SpecifiedLegalOrganization.ID with blank scheme — GAP-5 default)
		final String regNumber = sellerBP.getCommercialRegisterNumber();
		if (regNumber != null && !regNumber.isEmpty())
		{
			final LegalOrganizationType legalOrg = new LegalOrganizationType();
			final IDType regId = new IDType();
			regId.setValue(regNumber);
			// GAP-5: scheme blank as workaround (no scheme-ID column available)
			legalOrg.setID(regId);
			seller.setSpecifiedLegalOrganization(legalOrg);
		}

		// BT-31 VAT id (scheme VA)
		final String taxId = sellerBP.getTaxID();
		if (taxId != null && !taxId.isEmpty())
		{
			final TaxRegistrationType vatReg = new TaxRegistrationType();
			final IDType vatId = new IDType();
			vatId.setValue(taxId);
			vatId.setSchemeID("VA");
			vatReg.setID(vatId);
			seller.getSpecifiedTaxRegistration().add(vatReg);
		}

		// BT-34 Seller electronic address (email with scheme EM — GAP-4 default)
		final String email = sellerBP.getEMail();
		if (email != null && !email.isEmpty())
		{
			final UniversalCommunicationType uri = new UniversalCommunicationType();
			final IDType uriId = new IDType();
			uriId.setValue(email);
			uriId.setSchemeID("EM");
			uri.setURIID(uriId);
			seller.setURIUniversalCommunication(uri);
		}

		// Seller postal address (BG-5)
		seller.setPostalTradeAddress(buildSellerAddress(invoice));

		return seller;
	}

	private TradeAddressType buildSellerAddress(@NonNull final I_C_Invoice invoice)
	{
		// Resolve seller BPartner location via AD_OrgInfo
		final I_AD_OrgInfo orgInfo = loadOrgInfo(invoice.getAD_Org_ID());
		if (orgInfo == null)
		{
			return new TradeAddressType();
		}

		final int orgBPartnerId = orgInfo.getOrg_BPartner_ID();
		if (orgBPartnerId <= 0)
		{
			return new TradeAddressType();
		}

		// Find a bill-to location for the seller BPartner
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final I_C_BPartner orgBP = InterfaceWrapperHelper.load(orgBPartnerId, I_C_BPartner.class);
		if (orgBP == null)
		{
			return new TradeAddressType();
		}

		final java.util.List<I_C_BPartner_Location> locations = bPartnerDAO.retrieveBPartnerLocations(orgBP);
		I_C_BPartner_Location sellerBPLoc = null;
		for (final I_C_BPartner_Location loc : locations)
		{
			if (loc.isBillTo())
			{
				sellerBPLoc = loc;
				break;
			}
		}
		if (sellerBPLoc == null && !locations.isEmpty())
		{
			sellerBPLoc = locations.get(0);
		}
		if (sellerBPLoc == null)
		{
			return new TradeAddressType();
		}

		return buildAddress(sellerBPLoc.getC_Location());
	}

	// ===== Buyer trade party =====

	private TradePartyType buildBuyerParty(@NonNull final I_C_Invoice invoice)
	{
		final I_C_BPartner buyerBP = InterfaceWrapperHelper.load(invoice.getC_BPartner_ID(), I_C_BPartner.class);
		final I_C_BPartner_Location buyerBPLoc = InterfaceWrapperHelper.load(
				invoice.getC_BPartner_Location_ID(),
				I_C_BPartner_Location.class);

		final TradePartyType buyer = new TradePartyType();

		if (buyerBP != null)
		{
			// BT-44 Buyer name: CompanyName if set, else Name
			final String companyName = buyerBP.getCompanyName();
			buyer.setName(text(companyName != null && !companyName.isEmpty()
					? companyName
					: buyerBP.getName()));

			// BT-48 Buyer VAT id
			final String taxId = buyerBP.getTaxID();
			if (taxId != null && !taxId.isEmpty())
			{
				final TaxRegistrationType vatReg = new TaxRegistrationType();
				final IDType vatId = new IDType();
				vatId.setValue(taxId);
				vatId.setSchemeID("VA");
				vatReg.setID(vatId);
				buyer.getSpecifiedTaxRegistration().add(vatReg);
			}
		}

		// BT-49 Buyer electronic address (from BPartnerLocation email — GAP default: EM scheme)
		if (buyerBPLoc != null)
		{
			final String email = buyerBPLoc.getEMail();
			if (email != null && !email.isEmpty())
			{
				final UniversalCommunicationType uri = new UniversalCommunicationType();
				final IDType uriId = new IDType();
				uriId.setValue(email);
				uriId.setSchemeID("EM");
				uri.setURIID(uriId);
				buyer.setURIUniversalCommunication(uri);
			}
		}

		// Buyer postal address (BG-8)
		if (buyerBPLoc != null)
		{
			buyer.setPostalTradeAddress(buildAddress(buyerBPLoc.getC_Location()));
		}

		return buyer;
	}

	// ===== HeaderTradeSettlement =====

	private HeaderTradeSettlementType buildTradeSettlement(@NonNull final I_C_Invoice invoice)
	{
		final HeaderTradeSettlementType settlement = new HeaderTradeSettlementType();

		// BT-5 Invoice currency code
		final I_C_Currency currency = InterfaceWrapperHelper.load(invoice.getC_Currency_ID(), I_C_Currency.class);
		if (currency != null)
		{
			final CurrencyCodeType currencyCode = new CurrencyCodeType();
			currencyCode.setValue(currency.getISO_Code());
			settlement.setInvoiceCurrencyCode(currencyCode);
		}

		// BT-9 Due date
		final Timestamp dueDate = invoice.getDueDate();
		if (dueDate != null)
		{
			// SpecifiedTradePaymentTerms.DueDateDateTime is populated in later tasks (B5)
			// Here we leave it intentionally for the settlement builder to fill
		}

		// BT-25/BT-26 Preceding invoice reference (for credit notes)
		final int refInvoiceId = invoice.getRef_Invoice_ID();
		if (refInvoiceId > 0)
		{
			final I_C_Invoice refInvoice = InterfaceWrapperHelper.load(refInvoiceId, I_C_Invoice.class);
			if (refInvoice != null)
			{
				final ReferencedDocumentType invoiceRef = new ReferencedDocumentType();
				// BT-25 Preceding invoice number
				invoiceRef.setIssuerAssignedID(id(refInvoice.getDocumentNo()));
				// BT-26 Preceding invoice issue date
				final FormattedDateTimeType refDate = new FormattedDateTimeType();
				final FormattedDateTimeType.DateTimeString dtStr = new FormattedDateTimeType.DateTimeString();
				dtStr.setValue(formatDate(refInvoice.getDateInvoiced()));
				dtStr.setFormat(DATE_FORMAT_102);
				refDate.setDateTimeString(dtStr);
				invoiceRef.setFormattedIssueDateTime(refDate);

				settlement.getInvoiceReferencedDocument().add(invoiceRef);
			}
		}

		return settlement;
	}

	// ===== Shared address builder =====

	private TradeAddressType buildAddress(@Nullable final I_C_Location location)
	{
		final TradeAddressType address = new TradeAddressType();
		if (location == null)
		{
			return address;
		}

		// BT-35 / BT-50 Address line 1
		final String addr1 = location.getAddress1();
		if (addr1 != null && !addr1.isEmpty())
		{
			address.setLineOne(text(addr1));
		}

		// BT-37 / BT-52 City
		final String city = location.getCity();
		if (city != null && !city.isEmpty())
		{
			address.setCityName(text(city));
		}

		// BT-38 / BT-53 Post code
		final String postal = location.getPostal();
		if (postal != null && !postal.isEmpty())
		{
			final CodeType postCode = new CodeType();
			postCode.setValue(postal);
			address.setPostcodeCode(postCode);
		}

		// BT-40 / BT-55 Country code
		final I_C_Country country = location.getC_Country();
		if (country != null)
		{
			final CountryIDType countryId = new CountryIDType();
			countryId.setValue(country.getCountryCode());
			address.setCountryID(countryId);
		}

		return address;
	}

	// ===== Helper builders =====

	private IDType id(@NonNull final String value)
	{
		final IDType idType = new IDType();
		idType.setValue(value);
		return idType;
	}

	private TextType text(@NonNull final String value)
	{
		final TextType textType = new TextType();
		textType.setValue(value);
		return textType;
	}

	private DateTimeType toDateTime(@Nullable final Timestamp timestamp)
	{
		final DateTimeType dt = new DateTimeType();
		if (timestamp == null)
		{
			return dt;
		}
		final DateTimeType.DateTimeString dtStr = new DateTimeType.DateTimeString();
		dtStr.setValue(formatDate(timestamp));
		dtStr.setFormat(DATE_FORMAT_102);
		dt.setDateTimeString(dtStr);
		return dt;
	}

	private String formatDate(@NonNull final Timestamp timestamp)
	{
		return timestamp.toLocalDateTime().toLocalDate()
				.format(DateTimeFormatter.BASIC_ISO_DATE);
	}

	@Nullable
	private I_AD_OrgInfo loadOrgInfo(final int orgId)
	{
		// Query via IQueryBL — standard metasfresh pattern
		final org.adempiere.ad.dao.IQueryBL queryBL = Services.get(org.adempiere.ad.dao.IQueryBL.class);
		return queryBL.createQueryBuilder(I_AD_OrgInfo.class)
				.addEqualsFilter(I_AD_OrgInfo.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.firstOnly(I_AD_OrgInfo.class);
	}
}
