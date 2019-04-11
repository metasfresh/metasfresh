/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.edi.esb.bean.invoice;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.jaxb.*;
import de.metas.edi.esb.pojo.common.MeasurementUnit;
import de.metas.edi.esb.pojo.invoice.ObjectFactory;
import de.metas.edi.esb.pojo.invoice.*;
import de.metas.edi.esb.pojo.invoice.qualifier.*;
import de.metas.edi.esb.route.AbstractEDIRoute;
import de.metas.edi.esb.route.exports.XMLInvoiceRoute;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.commons.lang.StringUtils;
import org.milyn.payload.JavaSource;

import java.text.DecimalFormat;
import java.util.Comparator;

import static de.metas.edi.esb.commons.Util.*;

public class EDIXMLInvoiceBean
{

	public static final String METHOD_createXMLEDIData = "createXMLEDIData";

	private static final ObjectFactory INVOICE_objectFactory = new ObjectFactory();
	private static final int DOC_CREDIT_NOTE_ID = 83;
	private static final int DOC_DEBIT_NOTE_ID = 380;
	//Debit note  to financial adjustments
	private static final int DOC_DBNF_ID = 382;
	private static final int DOC_DBNF2_ID = 84;
	//Credit note to financial adjustments
	private static final int DOC_CRNF_ID = 381;

	public void createXMLEDIData(final Exchange exchange)
	{
		final EDICctopInvoicVType xmlCctopInvoice = exchange.getIn().getBody(EDICctopInvoicVType.class);
		final Document document = createDocument(exchange, xmlCctopInvoice);
		final JavaSource source = new JavaSource(document);
		exchange.getIn().setBody(source, Document.class);
	}

	private Document createDocument(final Exchange exchange, final EDICctopInvoicVType invoice)
	{
		final DecimalFormat decimalFormat = exchange.getProperty(Constants.DECIMAL_FORMAT, DecimalFormat.class);
		final String isTest = exchange.getProperty(XMLInvoiceRoute.EDI_XML_INVOICE_IS_TEST, String.class);
		final String partnerId = exchange.getProperty(XMLInvoiceRoute.EDI_XML_PARTNER_ID, String.class);
		final String ownerId = exchange.getProperty(XMLInvoiceRoute.EDI_XML_OWNER_ID, String.class);
		final String applicationRef = exchange.getProperty(XMLInvoiceRoute.EDI_XML_APPLICATION_REF, String.class);

		final Document document = INVOICE_objectFactory.createDocument();
		final Xrech4H xrech4H = INVOICE_objectFactory.createXrech4H();

		final HEADERXrech headerXrech = INVOICE_objectFactory.createHEADERXrech();
		headerXrech.setTESTINDICATOR(isTest);

		headerXrech.setPARTNERID(partnerId);
		headerXrech.setAPPLICATIONREF(applicationRef);
		headerXrech.setOWNERID(ownerId);
		final String documentId = invoice.getInvoiceDocumentno();
		headerXrech.setDOCUMENTID(documentId);
		DocumentType documentType = mapDocumentType(invoice.getEancomDoctype());
		if (documentType == null)
		{
			throw new RuntimeCamelException("Could not identify document type");
		}
		headerXrech.setDOCUMENTTYP(documentType.name());
		headerXrech.setDOCUMENTFUNCTION(DocumentFunction.ORIG.name());

		final String dateFormat = (String)exchange.getProperty(AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);
		mapDates(invoice, headerXrech, dateFormat);

		mapReferences(invoice, headerXrech);

		mapAddresses(invoice, headerXrech);

		final HCURR1 currency = INVOICE_objectFactory.createHCURR1();
		currency.setDOCUMENTID(documentId);
		currency.setCURRENCYQUAL(CurrencyQual.INVO.name());
		currency.setCURRENCYCODE(invoice.getISOCode());
		headerXrech.getHCURR1().add(currency);

		mapPaymentTerms(invoice, decimalFormat, headerXrech, dateFormat);

		mapAlCh(invoice, decimalFormat, headerXrech);

		mapDetails(invoice, decimalFormat, headerXrech);

		final TRAILR docTrailer = INVOICE_objectFactory.createTRAILR();
		docTrailer.setDOCUMENTID(documentId);
		docTrailer.setCONTROLQUAL(ControlQual.LINE.name());
		docTrailer.setCONTROLVALUE(formatNumber(invoice.getEDICctopInvoic500V().size(), decimalFormat));
		mapTrailer(invoice, decimalFormat, docTrailer);

		xrech4H.setHEADER(headerXrech);
		xrech4H.setTRAILR(docTrailer);
		document.getXrech4H().add(xrech4H);
		return document;

	}

	private DocumentType mapDocumentType(final String eancomDocType)
	{
		int incomingDocType = Integer.parseInt(eancomDocType);
		DocumentType documentType = null;
		if (incomingDocType == DOC_CREDIT_NOTE_ID)
		{
			documentType = DocumentType.CRNO;
		}
		else if (incomingDocType == DOC_DEBIT_NOTE_ID)
		{
			documentType = DocumentType.DBNO;
		}
		else if (incomingDocType == DOC_DBNF_ID || incomingDocType == DOC_DBNF2_ID)
		{
			documentType = DocumentType.DBNF;
		}
		else if (incomingDocType == DOC_CRNF_ID)
		{
			documentType = DocumentType.CRNF;
		}
		return documentType;
	}

	private void mapTrailer(final EDICctopInvoicVType invoice, final DecimalFormat decimalFormat, final TRAILR docTrailer)
	{
		String documentId = docTrailer.getDOCUMENTID();
		final TAMOU1 trailerLinesAmount = INVOICE_objectFactory.createTAMOU1();
		trailerLinesAmount.setDOCUMENTID(documentId);
		trailerLinesAmount.setAMOUNTQUAL(AmountQual.TLIN.name());
		trailerLinesAmount.setAMOUNT(formatNumber(invoice.getTotalLines(), decimalFormat));
		trailerLinesAmount.setCURRENCY(invoice.getISOCode());
		docTrailer.getTAMOU1().add(trailerLinesAmount);

		final TAMOU1 trailerAmount = INVOICE_objectFactory.createTAMOU1();
		trailerAmount.setDOCUMENTID(documentId);
		trailerAmount.setAMOUNTQUAL(AmountQual.TINV.name());
		trailerAmount.setAMOUNT(formatNumber(invoice.getGrandTotal(), decimalFormat));
		trailerAmount.setCURRENCY(invoice.getISOCode());
		docTrailer.getTAMOU1().add(trailerAmount);

		final TAMOU1 trailerTaxAmount = INVOICE_objectFactory.createTAMOU1();
		trailerTaxAmount.setDOCUMENTID(documentId);
		trailerTaxAmount.setAMOUNTQUAL(AmountQual.TZAX.name());
		trailerTaxAmount.setAMOUNT(formatNumber(invoice.getTotalvat(), decimalFormat));
		trailerTaxAmount.setCURRENCY(invoice.getISOCode());
		docTrailer.getTAMOU1().add(trailerTaxAmount);

		for (final EDICctop901991VType xmlCctop901991V : invoice.getEDICctop901991V())
		{
			final TTAXI1 trailerTax = INVOICE_objectFactory.createTTAXI1();
			trailerTax.setDOCUMENTID(documentId);
			trailerTax.setTAXQUAL(TaxQual.VATX.name());
			trailerTax.setTAXRATE(formatNumber(xmlCctop901991V.getRate(), decimalFormat));
			trailerTax.setTAXAMOUNT(formatNumber(xmlCctop901991V.getTaxAmt(), decimalFormat));
			trailerTax.setTAXABLEAMOUNT(formatNumber(xmlCctop901991V.getTaxBaseAmt(), decimalFormat));
			docTrailer.getTTAXI1().add(trailerTax);
		}
	}

	private void mapDetails(final EDICctopInvoicVType invoice, final DecimalFormat decimalFormat, final HEADERXrech headerXrech)
	{
		invoice.getEDICctopInvoic500V().sort(Comparator.comparing(EDICctopInvoic500VType::getLine));
		String documentId = headerXrech.getDOCUMENTID();
		for (final EDICctopInvoic500VType xmlCctopInvoic500V : invoice.getEDICctopInvoic500V())
		{
			final DETAILXrech detailXrech = INVOICE_objectFactory.createDETAILXrech();
			detailXrech.setDOCUMENTID(documentId);
			final String lineNumber = formatNumber(xmlCctopInvoic500V.getLine(), decimalFormat);
			detailXrech.setLINENUMBER(lineNumber);

			final DPRIN1 productInfo = INVOICE_objectFactory.createDPRIN1();
			productInfo.setDOCUMENTID(documentId);
			productInfo.setLINENUMBER(lineNumber);
			productInfo.setPRODUCTQUAL(ProductQual.BUYR.name());
			productInfo.setPRODUCTID(xmlCctopInvoic500V.getVendorProductNo());
			detailXrech.getDPRIN1().add(productInfo);

			final DPRIN1 productUpc = INVOICE_objectFactory.createDPRIN1();
			productUpc.setDOCUMENTID(documentId);
			productUpc.setLINENUMBER(lineNumber);
			productUpc.setPRODUCTQUAL(ProductQual.GTIN.name());
			productUpc.setPRODUCTID(xmlCctopInvoic500V.getUPC());
			detailXrech.getDPRIN1().add(productUpc);

			final DPRIN1 productSupl = INVOICE_objectFactory.createDPRIN1();
			productSupl.setDOCUMENTID(documentId);
			productSupl.setLINENUMBER(lineNumber);
			productSupl.setPRODUCTQUAL(ProductQual.SUPL.name());
			productSupl.setPRODUCTID(xmlCctopInvoic500V.getValue());
			detailXrech.getDPRIN1().add(productSupl);

			final DPRDE1 productDescr = INVOICE_objectFactory.createDPRDE1();
			productDescr.setDOCUMENTID(documentId);
			productDescr.setLINENUMBER(lineNumber);
			productDescr.setPRODUCTDESCQUAL(ProductDescQual.PROD.name());
			productDescr.setPRODUCTDESCTEXT(xmlCctopInvoic500V.getProductDescription());
			//use consumer unit and german language as default
			productDescr.setPRODUCTDESCTYPE(ProductDescType.CU.name());
			productDescr.setPRODUCTDESCLANG(ProductDescLang.DE.name());
			detailXrech.getDPRDE1().add(productDescr);

			final DQUAN1 invoicedQuantity = INVOICE_objectFactory.createDQUAN1();
			invoicedQuantity.setDOCUMENTID(documentId);
			invoicedQuantity.setLINENUMBER(lineNumber);
			invoicedQuantity.setQUANTITYQUAL(QuantityQual.INVO.name());
			MeasurementUnit measurementUnit = MeasurementUnit.fromCUOM(xmlCctopInvoic500V.getEancomUom());
			if (measurementUnit != null)
			{
				invoicedQuantity.setMEASUREMENTUNIT(measurementUnit.name());
			}
			invoicedQuantity.setQUANTITY(formatNumber(xmlCctopInvoic500V.getQtyInvoiced(), decimalFormat));
			detailXrech.getDQUAN1().add(invoicedQuantity);

			final DAMOU1 amount = INVOICE_objectFactory.createDAMOU1();
			amount.setDOCUMENTID(documentId);
			amount.setLINENUMBER(lineNumber);
			amount.setAMOUNTQUAL(AmountQual.ANET.name());
			amount.setAMOUNT(formatNumber(xmlCctopInvoic500V.getLineNetAmt(), decimalFormat));
			amount.setCURRENCY(xmlCctopInvoic500V.getISOCode());
			detailXrech.getDAMOU1().add(amount);

			final DPRIC1 price = INVOICE_objectFactory.createDPRIC1();
			price.setDOCUMENTID(documentId);
			price.setLINENUMBER(lineNumber);
			price.setPRICEQUAL(PriceQual.NETT.name());
			price.setPRICE(formatNumber(xmlCctopInvoic500V.getPriceActual(), decimalFormat));
			price.setPRICESPEC(PriceSpecCode.NETP.name());
			MeasurementUnit priceMeasurementUnit = MeasurementUnit.fromCUOM(xmlCctopInvoic500V.getEanComPriceUOM());
			if (priceMeasurementUnit != null)
			{
				price.setPRICEMEASUREUNIT(priceMeasurementUnit.name());
			}
			detailXrech.getDPRIC1().add(price);

			if (xmlCctopInvoic500V.getOrderLine() != null)
			{
				final DREFE1 reference = INVOICE_objectFactory.createDREFE1();
				reference.setDOCUMENTID(documentId);
				reference.setLINENUMBER(lineNumber);
				reference.setREFERENCEQUAL(ReferenceQual.ORBU.name());
				reference.setREFERENCE(xmlCctopInvoic500V.getOrderPOReference());
				reference.setREFERENCELINE(xmlCctopInvoic500V.getOrderLine().toString());
				detailXrech.getDREFE1().add(reference);
			}

			final DTAXI1 tax = INVOICE_objectFactory.createDTAXI1();
			tax.setDOCUMENTID(documentId);
			tax.setLINENUMBER(lineNumber);
			tax.setTAXQUAL(TaxQual.VATX.name());
			tax.setTAXRATE(formatNumber(xmlCctopInvoic500V.getRate(), decimalFormat));
			tax.setTAXAMOUNT(formatNumber(xmlCctopInvoic500V.getTaxAmtInfo(), decimalFormat));
			tax.setTAXABLEAMOUNT(formatNumber(xmlCctopInvoic500V.getLineNetAmt(), decimalFormat));
			detailXrech.setDTAXI1(tax);

			headerXrech.getDETAIL().add(detailXrech);
		}
	}

	private void mapAlCh(final EDICctopInvoicVType invoice, final DecimalFormat decimalFormat, final HEADERXrech headerXrech)
	{
		for (final EDICctop901991VType xmlCctop901991V : invoice.getEDICctop901991V())
		{
			final HALCH1 alch = INVOICE_objectFactory.createHALCH1();
			alch.setDOCUMENTID(headerXrech.getDOCUMENTID());
			alch.setAMOUNT(formatNumber(xmlCctop901991V.getTotalAmt(), decimalFormat));
			alch.setTAXRATE(formatNumber(xmlCctop901991V.getRate(), decimalFormat));

			alch.setTAXAMOUNT(formatNumber(xmlCctop901991V.getTaxAmt(), decimalFormat));
			alch.setTAXCODE(TaxQual.VATX.name());
			alch.setTAXABLEAMOUNT(formatNumber(xmlCctop901991V.getTaxBaseAmt(), decimalFormat));
			headerXrech.getHALCH1().add(alch);
		}

	}

	private void mapPaymentTerms(final EDICctopInvoicVType invoice, final DecimalFormat decimalFormat, final HEADERXrech headerXrech, final String dateFormat)
	{
		for (final EDICctop120VType xmlCctop120V : invoice.getEDICctop120V())
		{
			final HPAYT1 paymentTerm = INVOICE_objectFactory.createHPAYT1();
			paymentTerm.setDOCUMENTID(headerXrech.getDOCUMENTID());
			paymentTerm.setTIMEREFERENCE(ReferenceQual.INVO.name());
			paymentTerm.setTIMEPERIODQUANTITY(formatNumber(xmlCctop120V.getNetDays(), decimalFormat));
			if (xmlCctop120V.getNetdate() != null)
			{
				paymentTerm.setTERMSQUAL(TermsQual.FIXD.name());
				paymentTerm.setTERMSDATEFROM(toFormattedStringDate(toDate(xmlCctop120V.getNetdate()), dateFormat));
			}
			else
			{
				paymentTerm.setTIMERELATION(TimeRelation.AFTR.name());
				paymentTerm.setTIMEPERIODTYPE(TimePeriodType.DAYS.name());
				paymentTerm.setTERMSQUAL(TermsQual.BASE.name());
			}
			headerXrech.getHPAYT1().add(paymentTerm);
		}

		for (final EDICctop140VType xmlCctop140V : invoice.getEDICctop140V())
		{
			final HPAYT1 paymentTerm = INVOICE_objectFactory.createHPAYT1();
			paymentTerm.setDOCUMENTID(headerXrech.getDOCUMENTID());
			paymentTerm.setTERMSQUAL(TermsQual.DISC.name());
			paymentTerm.setTIMEREFERENCE(ReferenceQual.INVO.name());
			paymentTerm.setTIMERELATION(TimeRelation.AFTR.name());
			paymentTerm.setTIMEPERIODTYPE(TimePeriodType.DAYS.name());
			paymentTerm.setTIMEPERIODQUANTITY(formatNumber(xmlCctop140V.getDiscountDays(), decimalFormat));
			paymentTerm.setDISCOUNTPERCENT(formatNumber(xmlCctop140V.getDiscount(), decimalFormat));
			headerXrech.getHPAYT1().add(paymentTerm);
		}
	}

	private void mapAddresses(EDICctopInvoicVType invoice, HEADERXrech headerXrech)
	{
		for (final EDICctop119VType xmlCctop119V : invoice.getEDICctop119V())
		{
			if (isEmpty(xmlCctop119V.getGLN()))
			{
				throw new RuntimeCamelException(xmlCctop119V + " must have a GLN");
			}

			if (isEmpty(xmlCctop119V.getEancomLocationtype()))
			{
				throw new RuntimeCamelException(xmlCctop119V + " must have a location type");
			}

			final HADRE1 address = INVOICE_objectFactory.createHADRE1();
			address.setDOCUMENTID(headerXrech.getDOCUMENTID());
			EancomLocationQual eancomLocationQual = EancomLocationQual.valueOf(xmlCctop119V.getEancomLocationtype());
			AddressQual addressQual = mapAddressQual(eancomLocationQual);
			if (addressQual == null)
			{
				throw new RuntimeCamelException(xmlCctop119V + " could not identify address qualifier");
			}
			address.setADDRESSQUAL(addressQual.name());
			address.setPARTYIDGLN(xmlCctop119V.getGLN());

			if (!isEmpty(xmlCctop119V.getAddress1()))
			{
				address.setSTREET1(xmlCctop119V.getAddress1());
				address.setSTREET2(xmlCctop119V.getAddress2());
			}
			else
			{
				if (isEmpty(xmlCctop119V.getAddress2()))
				{
					throw new RuntimeCamelException(xmlCctop119V + " must have at least one filled address");
				}
				address.setSTREET1(xmlCctop119V.getAddress2());
			}
			if (!isEmpty(xmlCctop119V.getName()))
			{
				address.setNAME1(xmlCctop119V.getName());
				address.setNAME2(xmlCctop119V.getName2());
			}
			else
			{
				if (isEmpty(xmlCctop119V.getName2()))
				{
					throw new RuntimeCamelException(xmlCctop119V + " must have at least one filled name");
				}
				address.setNAME1(normalize(xmlCctop119V.getName2()));
			}
			address.setCITY(xmlCctop119V.getCity());
			address.setPOSTALCODE(xmlCctop119V.getPostal());
			address.setCOUNTRY(xmlCctop119V.getCountryCode());

			if (addressQual == AddressQual.SUPL && isEmpty(xmlCctop119V.getVATaxID()))
			{
				throw new RuntimeCamelException(xmlCctop119V + " must have a VATTaxID");
			}
			if (addressQual == AddressQual.SUPL || (addressQual == AddressQual.BUYR && StringUtils.isNotEmpty(xmlCctop119V.getVATaxID())))
			{
				final HRFAD1 ref = INVOICE_objectFactory.createHRFAD1();
				ref.setDOCUMENTID(headerXrech.getDOCUMENTID());
				ref.setADDRESSQUAL(addressQual.name());
				ref.setREFERENCEQUAL(ReferenceQual.VATR.name());
				ref.setREFERENCE(xmlCctop119V.getVATaxID());
				address.getHRFAD1().add(ref);
			}

			if (addressQual == AddressQual.SUPL)
			{
				// copy and create ISSI (Issuer of invoice) address
				final HADRE1 issiAddress = INVOICE_objectFactory.createHADRE1();
				issiAddress.setDOCUMENTID(headerXrech.getDOCUMENTID());
				issiAddress.setADDRESSQUAL(AddressQual.ISSI.name());
				issiAddress.setPARTYIDGLN(address.getPARTYIDGLN());
				issiAddress.setSTREET1(address.getSTREET1());
				issiAddress.setSTREET2(address.getSTREET2());
				issiAddress.setNAME1(address.getNAME1());
				issiAddress.setNAME2(address.getNAME2());
				issiAddress.setCITY(address.getCITY());
				issiAddress.setPOSTALCODE(address.getPOSTALCODE());
				issiAddress.setCOUNTRY(address.getCOUNTRY());
				headerXrech.getHADRE1().add(issiAddress);
			}

			headerXrech.getHADRE1().add(address);

		}
	}

	private AddressQual mapAddressQual(final EancomLocationQual eancomLocationQual)
	{
		AddressQual addressQual = null;
		switch (eancomLocationQual)
		{
			case DP:
			{
				addressQual = AddressQual.DELV;
				break;
			}
			case IV:
			{
				addressQual = AddressQual.IVCE;
				break;
			}
			case BY:
			{
				addressQual = AddressQual.BUYR;
				break;
			}
			case SU:
			{
				addressQual = AddressQual.SUPL;
				break;
			}
			case SN:
			{
				addressQual = AddressQual.ULCO;
				break;
			}
		}
		return addressQual;
	}

	private void mapReferences(final EDICctopInvoicVType invoice, final HEADERXrech headerXrech)
	{
		final HREFE1 orderRef = INVOICE_objectFactory.createHREFE1();
		orderRef.setDOCUMENTID(headerXrech.getDOCUMENTID());
		orderRef.setREFERENCEQUAL(ReferenceQual.ORBU.name());
		orderRef.setREFERENCE(invoice.getPOReference());

		final HREFE1 despatchAdvRef = INVOICE_objectFactory.createHREFE1();
		despatchAdvRef.setDOCUMENTID(headerXrech.getDOCUMENTID());
		despatchAdvRef.setREFERENCEQUAL(ReferenceQual.DADV.name());
		despatchAdvRef.setREFERENCE(invoice.getShipmentDocumentno());

		headerXrech.getHREFE1().add(orderRef);
		headerXrech.getHREFE1().add(despatchAdvRef);

	}

	private void mapDates(final EDICctopInvoicVType invoice, final HEADERXrech headerXrech, final String dateFormat)
	{
		final HDATE1 documentDate = INVOICE_objectFactory.createHDATE1();
		documentDate.setDOCUMENTID(headerXrech.getDOCUMENTID());
		documentDate.setDATEQUAL(DateQual.CREA.name());
		documentDate.setDATEFROM(toFormattedStringDate(toDate(invoice.getDateInvoiced()), dateFormat));
		final HDATE1 deliveryDate = INVOICE_objectFactory.createHDATE1();
		deliveryDate.setDOCUMENTID(headerXrech.getDOCUMENTID());
		deliveryDate.setDATEQUAL(DateQual.DELV.name());
		deliveryDate.setDATEFROM(toFormattedStringDate(toDate(invoice.getMovementDate()), dateFormat));

		final HDATE1 valueDate = INVOICE_objectFactory.createHDATE1();
		valueDate.setDOCUMENTID(headerXrech.getDOCUMENTID());
		valueDate.setDATEQUAL(DateQual.VALU.name());
		// not sure what value date is
		valueDate.setDATEFROM(toFormattedStringDate(toDate(invoice.getDateInvoiced()), dateFormat));

		headerXrech.getHDATE1().add(documentDate);
		headerXrech.getHDATE1().add(deliveryDate);
		headerXrech.getHDATE1().add(valueDate);
	}
}
