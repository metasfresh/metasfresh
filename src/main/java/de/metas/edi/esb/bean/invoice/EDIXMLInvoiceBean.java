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

	public void createXMLEDIData(final Exchange exchange)
	{
		final EDICctopInvoicVType xmlCctopInvoice = exchange.getIn().getBody(EDICctopInvoicVType.class);
		final Document document = createDocument(exchange, xmlCctopInvoice);
		final JavaSource source = new JavaSource(document);
		exchange.getIn().setBody(source, Document.class);
	}

	private Document createDocument(Exchange exchange, EDICctopInvoicVType invoice)
	{
		final DecimalFormat decimalFormat = exchange.getProperty(Constants.DECIMAL_FORMAT, DecimalFormat.class);
		final String isTest = exchange.getProperty(XMLInvoiceRoute.EDI_XML_INVOICE_IS_TEST, String.class);

		final ObjectFactory objectFactory = new ObjectFactory();
		final Document document = objectFactory.createDocument();
		final Xrech4H xrech4H = objectFactory.createXrech4H();

		final HEADERXrech headerXrech = objectFactory.createHEADERXrech();
		headerXrech.setTESTINDICATOR(isTest);

		//TODO use property - same as desavd
		//headerXrech.setPARTNERID(invoice.getCbPartnerLocationID());
		//headerXrech.setAPPLICATIONREF();
		//headerXrech.setOWNERID();
		final String documentId = invoice.getInvoiceDocumentno();
		headerXrech.setDOCUMENTID(documentId);
		// credit note - 83
		// debit note - 380
		// DBNF  - 383, 84
		// CRNF - 381
		headerXrech.setDOCUMENTTYP(DocumentType.CMIV.name());
		headerXrech.setDOCUMENTFUNCTION(DocumentFunction.ORIG.name());

		final String dateFormat = (String)exchange.getProperty(AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);
		mapDates(invoice, objectFactory, headerXrech, documentId, dateFormat);

		mapReferences(invoice, objectFactory, headerXrech, documentId);

		mapAddresses(invoice, objectFactory, headerXrech, documentId);

		final HCURR1 currency = objectFactory.createHCURR1();
		currency.setDOCUMENTID(documentId);
		currency.setCURRENCYQUAL(CurrencyQual.INVO.name());
		currency.setCURRENCYCODE(invoice.getISOCode());
		headerXrech.getHCURR1().add(currency);

		mapPaymentTerms(invoice, decimalFormat, objectFactory, headerXrech, documentId, dateFormat);

		mapAlCh(invoice, decimalFormat, objectFactory, headerXrech, documentId);

		mapDetails(invoice, decimalFormat, objectFactory, headerXrech, documentId);

		final TRAILR docTrailer = objectFactory.createTRAILR();
		docTrailer.setDOCUMENTID(documentId);
		docTrailer.setCONTROLQUAL(ControlQual.LINE.name());
		docTrailer.setCONTROLVALUE(formatNumber(invoice.getEDICctopInvoic500V().size(), decimalFormat));
		mapTrailer(invoice, decimalFormat, objectFactory, headerXrech, documentId, docTrailer);

		xrech4H.setHEADER(headerXrech);
		xrech4H.setTRAILR(docTrailer);
		document.getXrech4H().add(xrech4H);
		return document;

	}

	private void mapTrailer(EDICctopInvoicVType invoice, DecimalFormat decimalFormat, ObjectFactory objectFactory, HEADERXrech headerXrech, String documentId, TRAILR docTrailer)
	{
		//TODO check if different conditions need to be met
		//TODO set currency for all amounts from invoice
		final TAMOU1 trailerLinesAmount = objectFactory.createTAMOU1();
		trailerLinesAmount.setDOCUMENTID(documentId);
		trailerLinesAmount.setAMOUNTQUAL(AmountQual.TLIN.name());
		trailerLinesAmount.setAMOUNT(formatNumber(invoice.getTotalLines(), decimalFormat));
		docTrailer.getTAMOU1().add(trailerLinesAmount);

		final TAMOU1 trailerAmount = objectFactory.createTAMOU1();
		trailerAmount.setDOCUMENTID(documentId);
		trailerAmount.setAMOUNTQUAL(AmountQual.TINV.name());
		trailerAmount.setAMOUNT(formatNumber(invoice.getGrandTotal(), decimalFormat));
		docTrailer.getTAMOU1().add(trailerAmount);

		final TAMOU1 trailerTaxAmount = objectFactory.createTAMOU1();
		trailerTaxAmount.setDOCUMENTID(documentId);
		trailerTaxAmount.setAMOUNTQUAL(AmountQual.TZAX.name());
		trailerTaxAmount.setAMOUNT(formatNumber(invoice.getTotalvat(), decimalFormat));
		docTrailer.getTAMOU1().add(trailerTaxAmount);

		for (final EDICctop901991VType xmlCctop901991V : invoice.getEDICctop901991V())
		{
			final TTAXI1 trailerTax = objectFactory.createTTAXI1();
			trailerTax.setDOCUMENTID(documentId);
			trailerTax.setTAXQUAL(TaxQual.VATX.name());
			trailerTax.setTAXRATE(formatNumber(xmlCctop901991V.getRate(), decimalFormat));
			trailerTax.setTAXAMOUNT(formatNumber(xmlCctop901991V.getTaxAmt(), decimalFormat));
			trailerTax.setTAXABLEAMOUNT(formatNumber(xmlCctop901991V.getTaxBaseAmt(), decimalFormat));
			docTrailer.getTTAXI1().add(trailerTax);
		}
	}

	private void mapDetails(EDICctopInvoicVType invoice, DecimalFormat decimalFormat, ObjectFactory objectFactory, HEADERXrech headerXrech, String documentId)
	{
		invoice.getEDICctopInvoic500V().sort(Comparator.comparing(EDICctopInvoic500VType::getLine));
		for (final EDICctopInvoic500VType xmlCctopInvoic500V : invoice.getEDICctopInvoic500V())
		{
			final DETAILXrech detailXrech = objectFactory.createDETAILXrech();
			detailXrech.setDOCUMENTID(documentId);
			final String lineNumber = formatNumber(xmlCctopInvoic500V.getLine(), decimalFormat);
			detailXrech.setLINENUMBER(lineNumber);

			final DPRIN1 productInfo = objectFactory.createDPRIN1();
			productInfo.setDOCUMENTID(documentId);
			productInfo.setLINENUMBER(lineNumber);
			productInfo.setPRODUCTQUAL(ProductQual.BUYR.name());
			productInfo.setPRODUCTID(xmlCctopInvoic500V.getVendorProductNo());
			detailXrech.getDPRIN1().add(productInfo);

			final DPRIN1 productUpc = objectFactory.createDPRIN1();
			productUpc.setDOCUMENTID(documentId);
			productUpc.setLINENUMBER(lineNumber);
			productUpc.setPRODUCTQUAL(ProductQual.GTIN.name());
			productUpc.setPRODUCTID(xmlCctopInvoic500V.getUPC());
			detailXrech.getDPRIN1().add(productUpc);

			final DPRIN1 productSupl = objectFactory.createDPRIN1();
			productSupl.setDOCUMENTID(documentId);
			productSupl.setLINENUMBER(lineNumber);
			productSupl.setPRODUCTQUAL(ProductQual.SUPL.name());
			productSupl.setPRODUCTID(xmlCctopInvoic500V.getValue());
			detailXrech.getDPRIN1().add(productSupl);

			final DPRDE1 productDescr = objectFactory.createDPRDE1();
			productDescr.setDOCUMENTID(documentId);
			productDescr.setLINENUMBER(lineNumber);
			productDescr.setPRODUCTDESCQUAL(ProductDescQual.PROD.name());
			productDescr.setPRODUCTDESCTEXT(xmlCctopInvoic500V.getProductDescription());
			//TODO explain why
			productDescr.setPRODUCTDESCTYPE("CU");
			productDescr.setPRODUCTDESCLANG("DE");
			detailXrech.getDPRDE1().add(productDescr);

			final DQUAN1 invoicedQuantity = objectFactory.createDQUAN1();
			invoicedQuantity.setDOCUMENTID(documentId);
			invoicedQuantity.setLINENUMBER(lineNumber);
			invoicedQuantity.setQUANTITYQUAL(QuantityQual.INVO.name());
			// TODO map UOM from c_uom
			invoicedQuantity.setMEASUREMENTUNIT(xmlCctopInvoic500V.getEancomUom());
			invoicedQuantity.setQUANTITY(formatNumber(xmlCctopInvoic500V.getQtyInvoiced(), decimalFormat));
			detailXrech.getDQUAN1().add(invoicedQuantity);

			final DAMOU1 amount = objectFactory.createDAMOU1();
			amount.setDOCUMENTID(documentId);
			amount.setLINENUMBER(lineNumber);
			amount.setAMOUNTQUAL(AmountQual.ANET.name());
			amount.setAMOUNT(formatNumber(xmlCctopInvoic500V.getLineNetAmt(), decimalFormat));
			amount.setCURRENCY(xmlCctopInvoic500V.getISOCode());
			detailXrech.getDAMOU1().add(amount);

			final DPRIC1 price = objectFactory.createDPRIC1();
			price.setDOCUMENTID(documentId);
			price.setLINENUMBER(lineNumber);
			price.setPRICEQUAL(PriceQual.NETT.name());
			price.setPRICE(formatNumber(xmlCctopInvoic500V.getPriceActual(), decimalFormat));
			//TODO set net
			//price.setPRICESPEC();
			// TODO map UOM from c_uom
			price.setPRICEMEASUREUNIT(xmlCctopInvoic500V.getEanComPriceUOM());
			detailXrech.getDPRIC1().add(price);

			if (xmlCctopInvoic500V.getOrderLine() != null)
			{
				final DREFE1 reference = objectFactory.createDREFE1();
				reference.setDOCUMENTID(documentId);
				reference.setLINENUMBER(lineNumber);
				reference.setREFERENCEQUAL(ReferenceQual.ORBU.name());
				reference.setREFERENCE(xmlCctopInvoic500V.getOrderPOReference());
				reference.setREFERENCELINE(xmlCctopInvoic500V.getOrderLine().toString());
				detailXrech.getDREFE1().add(reference);
			}

			final DTAXI1 tax = objectFactory.createDTAXI1();
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

	private void mapAlCh(EDICctopInvoicVType invoice, DecimalFormat decimalFormat, ObjectFactory objectFactory, HEADERXrech headerXrech, String documentId)
	{
		for (final EDICctop901991VType xmlCctop901991V : invoice.getEDICctop901991V())
		{
			final HALCH1 alch = objectFactory.createHALCH1();
			alch.setDOCUMENTID(documentId);
			alch.setAMOUNT(formatNumber(xmlCctop901991V.getTotalAmt(), decimalFormat));
			alch.setTAXRATE(formatNumber(xmlCctop901991V.getRate(), decimalFormat));

			alch.setTAXAMOUNT(formatNumber(xmlCctop901991V.getTaxAmt(), decimalFormat));
			alch.setTAXCODE(TaxQual.VATX.name());
			alch.setTAXABLEAMOUNT(formatNumber(xmlCctop901991V.getTaxBaseAmt(), decimalFormat));
			headerXrech.getHALCH1().add(alch);
		}

	}

	private void mapPaymentTerms(EDICctopInvoicVType invoice, DecimalFormat decimalFormat, ObjectFactory objectFactory, HEADERXrech headerXrech, String documentId, String dateFormat)
	{
		for (final EDICctop120VType xmlCctop120V : invoice.getEDICctop120V())
		{
			final HPAYT1 paymentTerm = objectFactory.createHPAYT1();
			paymentTerm.setDOCUMENTID(documentId);
			paymentTerm.setTIMEREFERENCE(ReferenceQual.INVO.name());
			paymentTerm.setTIMEPERIODQUANTITY(formatNumber(xmlCctop120V.getNetDays(), decimalFormat));
			if (xmlCctop120V.getNetdate() != null)
			{
				paymentTerm.setTERMSQUAL(TermsQual.FIXD.name());
				paymentTerm.setTERMSDATEFROM(toFormattedStringDate(toDate(xmlCctop120V.getNetdate()), dateFormat));
			}
			else
			{
				paymentTerm.setTIMERELATION("AFTR");
				paymentTerm.setTIMEPERIODTYPE("DAYS");
				paymentTerm.setTERMSQUAL(TermsQual.BASE.name());
			}
			headerXrech.getHPAYT1().add(paymentTerm);
		}

		for (final EDICctop140VType xmlCctop140V : invoice.getEDICctop140V())
		{
			final HPAYT1 paymentTerm = objectFactory.createHPAYT1();
			paymentTerm.setDOCUMENTID(documentId);
			paymentTerm.setTERMSQUAL(TermsQual.DISC.name());
			paymentTerm.setTIMEREFERENCE(ReferenceQual.INVO.name());
			paymentTerm.setTIMERELATION("AFTR");
			paymentTerm.setTIMEPERIODTYPE("DAYS");
			paymentTerm.setTIMEPERIODQUANTITY(formatNumber(xmlCctop140V.getDiscountDays(), decimalFormat));
			paymentTerm.setDISCOUNTPERCENT(formatNumber(xmlCctop140V.getDiscount(), decimalFormat));
			headerXrech.getHPAYT1().add(paymentTerm);
		}
	}

	private void mapAddresses(EDICctopInvoicVType invoice, ObjectFactory objectFactory, HEADERXrech headerXrech, String documentId)
	{
		for (final EDICctop119VType xmlCctop119V : invoice.getEDICctop119V())
		{
			if (isEmpty(xmlCctop119V.getGLN()))
			{
				throw new RuntimeCamelException(xmlCctop119V + " must have a GLN");
			}

			final HADRE1 address = objectFactory.createHADRE1();
			address.setDOCUMENTID(documentId);
			//TODO map ean com location type
			//'DP'
			//'IV'
			//'BY'
			// 'SU' - SUPL, ISSI
			//'SN'
			address.setADDRESSQUAL(AddressQual.BUYR.name());
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

			final AddressQual addressQual = AddressQual.valueOf(address.getADDRESSQUAL());

			if (addressQual == AddressQual.SUPL && isEmpty(xmlCctop119V.getVATaxID()))
			{
				throw new RuntimeCamelException(xmlCctop119V + " must have a VATTaxID");
			}
			if (addressQual == AddressQual.SUPL || (addressQual == AddressQual.BUYR && StringUtils.isNotEmpty(xmlCctop119V.getVATaxID())))
			{
				final HRFAD1 ref = objectFactory.createHRFAD1();
				ref.setDOCUMENTID(documentId);
				ref.setADDRESSQUAL(addressQual.name());
				ref.setREFERENCEQUAL(ReferenceQual.VATR.name());
				ref.setREFERENCE(xmlCctop119V.getVATaxID());
				address.getHRFAD1().add(ref);
			}

			headerXrech.getHADRE1().add(address);

		}
	}

	private void mapReferences(EDICctopInvoicVType invoice, ObjectFactory objectFactory, HEADERXrech headerXrech, String documentId)
	{
		final HREFE1 orderRef = objectFactory.createHREFE1();
		orderRef.setDOCUMENTID(documentId);
		orderRef.setREFERENCEQUAL(ReferenceQual.ORBU.name());
		orderRef.setREFERENCE(invoice.getPOReference());

		final HREFE1 despatchAdvRef = objectFactory.createHREFE1();
		despatchAdvRef.setDOCUMENTID(documentId);
		despatchAdvRef.setREFERENCEQUAL(ReferenceQual.DADV.name());
		despatchAdvRef.setREFERENCE(invoice.getShipmentDocumentno());

		headerXrech.getHREFE1().add(orderRef);
		headerXrech.getHREFE1().add(despatchAdvRef);

	}

	private void mapDates(EDICctopInvoicVType invoice, ObjectFactory objectFactory, HEADERXrech headerXrech, String documentId, String dateFormat)
	{
		final HDATE1 documentDate = objectFactory.createHDATE1();
		documentDate.setDOCUMENTID(documentId);
		documentDate.setDATEQUAL(DateQual.CREA.name());
		documentDate.setDATEFROM(toFormattedStringDate(toDate(invoice.getDateInvoiced()), dateFormat));
		final HDATE1 deliveryDate = objectFactory.createHDATE1();
		deliveryDate.setDOCUMENTID(documentId);
		deliveryDate.setDATEQUAL(DateQual.DELV.name());
		deliveryDate.setDATEFROM(toFormattedStringDate(toDate(invoice.getMovementDate()), dateFormat));

		final HDATE1 valueDate = objectFactory.createHDATE1();
		valueDate.setDOCUMENTID(documentId);
		valueDate.setDATEQUAL(DateQual.VALU.name());
		// not sure what value date is
		valueDate.setDATEFROM(toFormattedStringDate(toDate(invoice.getDateInvoiced()), dateFormat));

		headerXrech.getHDATE1().add(documentDate);
		headerXrech.getHDATE1().add(deliveryDate);
		headerXrech.getHDATE1().add(valueDate);
	}
}
