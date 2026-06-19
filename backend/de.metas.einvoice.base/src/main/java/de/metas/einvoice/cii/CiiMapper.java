package de.metas.einvoice.cii;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.einvoice.EInvoiceFormat;
import de.metas.einvoice.EInvoiceRecipientConfig;
import de.metas.einvoice.cii.model.AmountType;
import de.metas.einvoice.cii.model.CodeType;
import de.metas.einvoice.cii.model.CountryIDType;
import de.metas.einvoice.cii.model.CrossIndustryInvoiceType;
import de.metas.einvoice.cii.model.CurrencyCodeType;
import de.metas.einvoice.cii.model.DateTimeType;
import de.metas.einvoice.cii.model.DocumentCodeType;
import de.metas.einvoice.cii.model.DocumentContextParameterType;
import de.metas.einvoice.cii.model.DocumentLineDocumentType;
import de.metas.einvoice.cii.model.ExchangedDocumentContextType;
import de.metas.einvoice.cii.model.ExchangedDocumentType;
import de.metas.einvoice.cii.model.FormattedDateTimeType;
import de.metas.einvoice.cii.model.HeaderTradeAgreementType;
import de.metas.einvoice.cii.model.HeaderTradeDeliveryType;
import de.metas.einvoice.cii.model.HeaderTradeSettlementType;
import de.metas.einvoice.cii.model.IDType;
import de.metas.einvoice.cii.model.LegalOrganizationType;
import de.metas.einvoice.cii.model.LineTradeAgreementType;
import de.metas.einvoice.cii.model.LineTradeDeliveryType;
import de.metas.einvoice.cii.model.LineTradeSettlementType;
import de.metas.einvoice.cii.model.PercentType;
import de.metas.einvoice.cii.model.QuantityType;
import de.metas.einvoice.cii.model.ReferencedDocumentType;
import de.metas.einvoice.cii.model.SupplyChainTradeLineItemType;
import de.metas.einvoice.cii.model.SupplyChainTradeTransactionType;
import de.metas.einvoice.cii.model.TaxCategoryCodeType;
import de.metas.einvoice.cii.model.TaxRegistrationType;
import de.metas.einvoice.cii.model.TaxTypeCodeType;
import de.metas.einvoice.cii.model.TextType;
import de.metas.einvoice.cii.model.TradeAddressType;
import de.metas.einvoice.cii.model.TradePartyType;
import de.metas.einvoice.cii.model.TradePaymentTermsType;
import de.metas.einvoice.cii.model.TradePriceType;
import de.metas.einvoice.cii.model.TradeProductType;
import de.metas.einvoice.cii.model.TradeSettlementLineMonetarySummationType;
import de.metas.einvoice.cii.model.TradeTaxType;
import de.metas.einvoice.cii.model.UniversalCommunicationType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Maps a metasfresh {@code C_Invoice} to the CII {@link CrossIndustryInvoiceType} structure.
 *
 * <p>Scope: ExchangedDocument header, seller / buyer trade parties, document-level references,
 * and BG-25 invoice lines (BT-126 through BT-152).
 * VAT breakdown and monetary totals are populated by a subsequent pass (B5).
 */
public class CiiMapper
{
	/** EN 16931 guideline IDs per invoice format. */
	private static final String GUIDELINE_ID_ZUGFERD =
			"urn:cen.eu:en16931:2017#compliant#urn:factur-x.eu:1p0:en16931";
	private static final String GUIDELINE_ID_XRECHNUNG =
			"urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_3.0";
	/** Peppol BIS Billing 3.0 — European profile. */
	private static final String GUIDELINE_ID_PEPPOL =
			"urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0";

	/** CII date format code for yyyyMMdd (UNCL2379 code 102). */
	private static final String DATE_FORMAT_102 = "102";

	@NonNull private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	@NonNull
	public CrossIndustryInvoiceType map(
			@NonNull final I_C_Invoice invoice,
			@NonNull final EInvoiceRecipientConfig recipientConfig)
	{
		final CrossIndustryInvoiceType cii = new CrossIndustryInvoiceType();

		cii.setExchangedDocumentContext(buildDocumentContext(recipientConfig.getFormat()));
		cii.setExchangedDocument(buildExchangedDocument(invoice));
		cii.setSupplyChainTradeTransaction(buildTradeTransaction(invoice, recipientConfig));

		return cii;
	}

	// ===== ExchangedDocumentContext =====

	private ExchangedDocumentContextType buildDocumentContext(@NonNull final EInvoiceFormat format)
	{
		final ExchangedDocumentContextType ctx = new ExchangedDocumentContextType();

		final String guidelineId;
		if (format.isXRechnung())
		{
			guidelineId = GUIDELINE_ID_XRECHNUNG;
		}
		else if (format.isPeppol())
		{
			guidelineId = GUIDELINE_ID_PEPPOL;
		}
		else
		{
			// ZUGFeRD / Factur-X (default)
			guidelineId = GUIDELINE_ID_ZUGFERD;
		}

		final DocumentContextParameterType guidelineParam = new DocumentContextParameterType();
		guidelineParam.setID(id(guidelineId));
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

		// EN 16931 §6.3.4: commercial invoice → type code 380, credit note → 381
		final String typeCodeValue = "ARC".equals(docBaseType) ? "381" : "380";

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

		// BG-25 Invoice lines
		final List<I_C_InvoiceLine> lines = invoiceDAO.retrieveLines(invoice);
		for (final I_C_InvoiceLine line : lines)
		{
			tx.getIncludedSupplyChainTradeLineItem().add(buildLineItem(line));
		}

		tx.setApplicableHeaderTradeAgreement(buildTradeAgreement(invoice, recipientConfig));
		tx.setApplicableHeaderTradeDelivery(new HeaderTradeDeliveryType());
		tx.setApplicableHeaderTradeSettlement(buildTradeSettlement(invoice));

		return tx;
	}

	// ===== BG-25 Invoice line item =====

	private SupplyChainTradeLineItemType buildLineItem(@NonNull final I_C_InvoiceLine line)
	{
		final SupplyChainTradeLineItemType item = new SupplyChainTradeLineItemType();

		// BT-126 Line id
		final DocumentLineDocumentType lineDoc = new DocumentLineDocumentType();
		lineDoc.setLineID(id(String.valueOf(line.getLine())));
		item.setAssociatedDocumentLineDocument(lineDoc);

		// BT-153 Item name: use productDescription if set, fall back to product name
		final TradeProductType product = new TradeProductType();
		final String productDescription = line.getProductDescription();
		if (productDescription != null && !productDescription.isEmpty())
		{
			product.setName(text(productDescription));
		}
		else
		{
			final I_M_Product mProduct = InterfaceWrapperHelper.load(line.getM_Product_ID(), I_M_Product.class);
			final String productName = mProduct != null ? mProduct.getName() : "";
			product.setName(text(productName));
		}
		item.setSpecifiedTradeProduct(product);

		// BT-146 Item net price (NetPriceProductTradePrice.ChargeAmount)
		final LineTradeAgreementType tradeAgreement = new LineTradeAgreementType();
		final TradePriceType netPrice = new TradePriceType();
		final AmountType priceAmount = new AmountType();
		priceAmount.setValue(line.getPriceActual());
		netPrice.setChargeAmount(priceAmount);
		tradeAgreement.setNetPriceProductTradePrice(netPrice);
		item.setSpecifiedLineTradeAgreement(tradeAgreement);

		// BT-129 Billed quantity + BT-130 unit code (mandatory EN16931 — fail fast if missing)
		final LineTradeDeliveryType delivery = new LineTradeDeliveryType();
		final QuantityType qty = new QuantityType();
		qty.setValue(line.getQtyInvoiced());
		final I_C_UOM uom = line.getC_UOM_ID() != 0
				? InterfaceWrapperHelper.load(line.getC_UOM_ID(), I_C_UOM.class)
				: null;
		final String unitCode = uom != null ? uom.getX12DE355() : null;
		if (unitCode == null || unitCode.isEmpty())
		{
			throw new AdempiereException(
					"CII mapping: line UOM has no X12DE355 unit code — set C_UOM.X12DE355"
							+ " [C_UOM_ID=" + line.getC_UOM_ID()
							+ ", C_InvoiceLine_ID=" + line.getC_InvoiceLine_ID() + "]");
		}
		qty.setUnitCode(unitCode);
		delivery.setBilledQuantity(qty);
		item.setSpecifiedLineTradeDelivery(delivery);

		// BT-151 VAT category code (fail fast if null) + BT-152 VAT rate + BT-131 line net amount
		final I_C_Tax tax = InterfaceWrapperHelper.load(line.getC_Tax_ID(), I_C_Tax.class);
		final String vatCategory = tax != null ? tax.getEN16931VATCategory() : null;
		if (vatCategory == null || vatCategory.isEmpty())
		{
			final int taxId = tax != null ? tax.getC_Tax_ID() : line.getC_Tax_ID();
			throw new AdempiereException(
					"CII mapping: line tax has no EN16931 VAT category — set C_Tax.EN16931VATCategory"
							+ " [C_Tax_ID=" + taxId
							+ ", C_InvoiceLine_ID=" + line.getC_InvoiceLine_ID() + "]");
		}

		final LineTradeSettlementType settlement = new LineTradeSettlementType();

		final TradeTaxType tradeTax = new TradeTaxType();
		final TaxTypeCodeType taxTypeCode = new TaxTypeCodeType();
		taxTypeCode.setValue("VAT");
		tradeTax.setTypeCode(taxTypeCode);
		final TaxCategoryCodeType categoryCode = new TaxCategoryCodeType();
		categoryCode.setValue(vatCategory);
		tradeTax.setCategoryCode(categoryCode);
		if (tax.getRate() != null)
		{
			final PercentType rate = new PercentType();
			rate.setValue(tax.getRate());
			tradeTax.setRateApplicablePercent(rate);
		}
		settlement.setApplicableTradeTax(tradeTax);

		// BT-131 Line net amount
		final TradeSettlementLineMonetarySummationType monetarySummation = new TradeSettlementLineMonetarySummationType();
		final AmountType lineTotal = new AmountType();
		lineTotal.setValue(line.getLineNetAmt());
		monetarySummation.setLineTotalAmount(lineTotal);
		settlement.setSpecifiedTradeSettlementLineMonetarySummation(monetarySummation);

		item.setSpecifiedLineTradeSettlement(settlement);

		return item;
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

		// BG-4/BG-5 Seller
		agreement.setSellerTradeParty(buildSellerParty(invoice));

		// BG-7/BG-8 Buyer
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

	// ===== Seller trade party (BG-4/BG-5) =====

	private TradePartyType buildSellerParty(@NonNull final I_C_Invoice invoice)
	{
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

		// BT-30 Legal registration (SpecifiedLegalOrganization.ID, scheme blank — GAP-5 default)
		final String regNumber = sellerBP.getCommercialRegisterNumber();
		if (regNumber != null && !regNumber.isEmpty())
		{
			final LegalOrganizationType legalOrg = new LegalOrganizationType();
			final IDType regId = new IDType();
			regId.setValue(regNumber);
			// GAP-5: no scheme-ID column exists on C_BPartner; blank scheme is CII-compliant
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

		// BT-34 Seller electronic address (email, scheme EM — GAP-4 default)
		final String email = sellerBP.getEMail();
		if (email != null && !email.isEmpty())
		{
			seller.setURIUniversalCommunication(uriCommunication(email));
		}

		// BG-5 Seller postal address
		seller.setPostalTradeAddress(buildSellerAddress(sellerBP));

		return seller;
	}

	private TradeAddressType buildSellerAddress(@NonNull final I_C_BPartner sellerBP)
	{
		final List<I_C_BPartner_Location> locations = bPartnerDAO.retrieveBPartnerLocations(sellerBP);
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
			throw new AdempiereException("Cannot build CII: seller BPartner " + sellerBP.getC_BPartner_ID()
					+ " has no postal address (EN16931 BG-5/BG-8 mandatory)");
		}

		return buildAddress(InterfaceWrapperHelper.load(sellerBPLoc.getC_Location_ID(), I_C_Location.class));
	}

	// ===== Buyer trade party (BG-7/BG-8) =====

	private TradePartyType buildBuyerParty(@NonNull final I_C_Invoice invoice)
	{
		final I_C_BPartner buyerBP = bPartnerDAO.getById(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()));
		// invoices may reference inactive locations after completion
		final BPartnerLocationId buyerBPLocId = BPartnerLocationId.ofRepoIdOrNull(invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID());
		final I_C_BPartner_Location buyerBPLoc = buyerBPLocId != null
				? bPartnerDAO.getBPartnerLocationByIdEvenInactive(buyerBPLocId)
				: null;

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

		if (buyerBPLoc != null)
		{
			// BT-49 Buyer electronic address (BPartnerLocation email, scheme EM — GAP default)
			final String email = buyerBPLoc.getEMail();
			if (email != null && !email.isEmpty())
			{
				buyer.setURIUniversalCommunication(uriCommunication(email));
			}

			// BG-8 Buyer postal address
			buyer.setPostalTradeAddress(
					buildAddress(InterfaceWrapperHelper.load(buyerBPLoc.getC_Location_ID(), I_C_Location.class)));
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

		// BT-9 Payment due date
		final java.sql.Timestamp dueDate = invoice.getDueDate();
		if (dueDate != null)
		{
			final TradePaymentTermsType paymentTerms = new TradePaymentTermsType();
			paymentTerms.setDueDateDateTime(toDateTime(dueDate));
			settlement.setSpecifiedTradePaymentTerms(paymentTerms);
		}

		// BT-25/BT-26 Preceding invoice reference (credit notes)
		final InvoiceId refInvoiceId = InvoiceId.ofRepoIdOrNull(invoice.getRef_Invoice_ID());
		if (refInvoiceId != null)
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

		// BT-40 / BT-55 Country code — load explicitly to avoid implicit DB traversal
		final I_C_Country country = InterfaceWrapperHelper.load(location.getC_Country_ID(), I_C_Country.class);
		if (country != null)
		{
			final CountryIDType countryId = new CountryIDType();
			countryId.setValue(country.getCountryCode());
			address.setCountryID(countryId);
		}

		return address;
	}

	// ===== Shared helpers =====

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

	private UniversalCommunicationType uriCommunication(@NonNull final String email)
	{
		final IDType uriId = new IDType();
		uriId.setValue(email);
		uriId.setSchemeID("EM");
		final UniversalCommunicationType uri = new UniversalCommunicationType();
		uri.setURIID(uriId);
		return uri;
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

	/**
	 * Formats a timestamp as yyyyMMdd.
	 *
	 * <p>Metasfresh date columns (DateInvoiced, DateAcct, …) are stored as midnight UTC in the DB.
	 * Reading them via {@code Timestamp.toInstant().atOffset(ZoneOffset.UTC)} is therefore correct
	 * and avoids any JVM-timezone-dependent shift.
	 */
	private String formatDate(@NonNull final Timestamp timestamp)
	{
		final LocalDate date = timestamp.toInstant().atOffset(ZoneOffset.UTC).toLocalDate();
		return date.format(DateTimeFormatter.BASIC_ISO_DATE);
	}

}
