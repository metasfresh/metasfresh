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

import java.math.BigDecimal;
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
		//TODO where to set sender
		//final String senderGln = exchange.getProperty(XMLInvoiceRoute.EDI_INVOICE_SENDER_GLN, String.class);
		//TODO looks like it's wrong, where to set location ID
		//headerXrech.setPARTNERID(invoice.getCbPartnerLocationID());
		//TODO check how to set partner id and application id
		//headerXrech.setPARTNERID();
		//headerXrech.setAPPLICATIONREF();
		//headerXrech.setOWNERID();
		//TODO check if invoice ID or invoice document number
		final String documentId = formatNumber(invoice.getCInvoiceID(), decimalFormat);
		headerXrech.setDOCUMENTID(documentId);
		//TODO check if credit memo / how to determine type , maybe EANCOM doc type
		headerXrech.setDOCUMENTTYP(DocumentType.CMIV.name());
		headerXrech.setDOCUMENTFUNCTION(DocumentFunction.ORIG.name());

		//TODO check date mappings
		final String dateFormat = (String)exchange.getProperty(AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);
		mapDates(invoice, objectFactory, headerXrech, documentId, dateFormat);

		//TODO check ref mapping
		mapReferences(invoice, objectFactory, headerXrech, documentId);

		//TODO check address mapping
		mapAddresses(invoice, objectFactory, headerXrech, documentId);

		final HCURR1 currency = objectFactory.createHCURR1();
		currency.setDOCUMENTID(documentId);
		currency.setCURRENCYQUAL(CurrencyQual.INVO.name());
		//TODO is this ok? where to get currency from - maybe from base payment term or from detail amount?
		currency.setCURRENCYCODE(invoice.getISOCode());
		headerXrech.getHCURR1().add(currency);

		//TODO check payment terms mapping
		mapPaymentTerms(invoice, decimalFormat, objectFactory, headerXrech, documentId, dateFormat);

		//TODO check
		mapAlCh(invoice, decimalFormat, objectFactory, headerXrech, documentId);

		//TODO check
		mapDetails(invoice, decimalFormat, objectFactory, headerXrech, documentId);

		final TRAILR docTrailer = objectFactory.createTRAILR();
		docTrailer.setDOCUMENTID(documentId);
		docTrailer.setCONTROLQUAL(ControlQual.LINE.name());
		//TODO how to calculate
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
		//TODO what if there are multiple currencies??? this could be misleading
		if (invoice.getTotalLines().compareTo(BigDecimal.ZERO) > 0)
		{
			final TAMOU1 trailerLinesAmount = objectFactory.createTAMOU1();
			trailerLinesAmount.setDOCUMENTID(documentId);
			trailerLinesAmount.setAMOUNTQUAL(AmountQual.TLIN.name());
			trailerLinesAmount.setAMOUNT(formatNumber(invoice.getTotalLines(), decimalFormat));
			docTrailer.getTAMOU1().add(trailerLinesAmount);
		}

		final TAMOU1 trailerAmount = objectFactory.createTAMOU1();
		trailerAmount.setDOCUMENTID(documentId);
		trailerAmount.setAMOUNTQUAL(AmountQual.TINV.name());
		trailerAmount.setAMOUNT(formatNumber(invoice.getGrandTotal(), decimalFormat));
		docTrailer.getTAMOU1().add(trailerAmount);

		BigDecimal alchAmount = BigDecimal.ZERO;
		BigDecimal discountAmount = BigDecimal.ZERO;

		for (final HALCH1 halch1 : headerXrech.getHALCH1())
		{
			final BigDecimal amount = new BigDecimal(halch1.getAMOUNT());
			alchAmount = alchAmount.add(amount);
			//TODO first check from where this is set in alch
			if (StringUtils.equals(halch1.getSPECSERVICECODE(), "DISC"))
			{
				discountAmount = discountAmount.add(amount);
			}
		}
		//TODO should we calc that, from details or header? or where to get it from
		if (alchAmount.compareTo(BigDecimal.ZERO) > 0)
		{
			final TAMOU1 trailerAlchAmount = objectFactory.createTAMOU1();
			trailerAlchAmount.setDOCUMENTID(documentId);
			trailerAlchAmount.setAMOUNTQUAL(AmountQual.TCHA.name());
			trailerAlchAmount.setAMOUNT(formatNumber(alchAmount, decimalFormat));
			docTrailer.getTAMOU1().add(trailerAlchAmount);
		}

		final TAMOU1 trailerTaxAmount = objectFactory.createTAMOU1();
		trailerTaxAmount.setDOCUMENTID(documentId);
		trailerTaxAmount.setAMOUNTQUAL(AmountQual.TZAX.name());
		trailerTaxAmount.setAMOUNT(formatNumber(invoice.getTotaltaxbaseamt(), decimalFormat));
		docTrailer.getTAMOU1().add(trailerTaxAmount);

		//TODO should we calc that, from details or header? or where to get it from
		if (discountAmount.compareTo(BigDecimal.ZERO) > 0)
		{
			final TAMOU1 trailerDiscountAmount = objectFactory.createTAMOU1();
			trailerDiscountAmount.setDOCUMENTID(documentId);
			trailerDiscountAmount.setAMOUNTQUAL(AmountQual.TDIS.name());
			trailerDiscountAmount.setAMOUNT(formatNumber(discountAmount, decimalFormat));
			docTrailer.getTAMOU1().add(trailerDiscountAmount);
		}

		//TODO what's the difference between this and the amount above for taxes?
		// maybe it makes sense with multiple qualifiers
		final TTAXI1 trailerTax = objectFactory.createTTAXI1();
		trailerTax.setDOCUMENTID(documentId);
		trailerTax.setTAXQUAL(TaxQual.VATX.name());
		//TODO where to get if from?
		//tax.setTAXRATE();
		trailerTax.setTAXAMOUNT(formatNumber(invoice.getTotalvat(), decimalFormat));
		//TODO should do a substraction between total amount and tax amount here?
		//alch.setTAXABLEAMOUNT();
		docTrailer.getTTAXI1().add(trailerTax);
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
			//TODO where to set this from
			//detailXrech.setSUBLINENUMBER();

			final DPRIN1 productInfo = objectFactory.createDPRIN1();
			productInfo.setDOCUMENTID(documentId);
			productInfo.setLINENUMBER(lineNumber);
			//TODO do we also have info about BUYR and SUPL??? is product nr ok? should this be SUPL
			productInfo.setPRODUCTQUAL(ProductQual.GTIN.name());
			productInfo.setPRODUCTID(xmlCctopInvoic500V.getVendorProductNo());
			detailXrech.getDPRIN1().add(productInfo);

			final DPRDE1 productDescr = objectFactory.createDPRDE1();
			productDescr.setDOCUMENTID(documentId);
			productDescr.setLINENUMBER(lineNumber);
			productDescr.setPRODUCTDESCQUAL(ProductDescQual.PROD.name());
			productDescr.setPRODUCTDESCTEXT(xmlCctopInvoic500V.getProductDescription());
			//TODO from where to set???
			//productDescr.setPRODUCTDESCTYPE(); - mandatory
			//productDescr.setPRODUCTDESCLANG();
			detailXrech.getDPRDE1().add(productDescr);

			final DQUAN1 invoicedQuantity = objectFactory.createDQUAN1();
			invoicedQuantity.setDOCUMENTID(documentId);
			invoicedQuantity.setLINENUMBER(lineNumber);
			//TODO what about DELV - delivery quantity
			invoicedQuantity.setQUANTITYQUAL(QuantityQual.INVO.name());
			//TODO check that measurement unit has the same values
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
			//TODO do we ever have INFO?
			price.setPRICEQUAL(PriceQual.NETT.name());
			price.setPRICE(formatNumber(xmlCctopInvoic500V.getPriceActual(), decimalFormat));
			//TODO where to get this from
			//price.setPRICEBASIS();
			price.setPRICEMEASUREUNIT(xmlCctopInvoic500V.getEanComPriceUOM());
			detailXrech.getDPRIC1().add(price);

			//TODO check how you can have multiple ref
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
			//TODO should do a substraction between total amount and tax amount here?
			//alch.setTAXABLEAMOUNT();
			detailXrech.setDTAXI1(tax);

			//TODO what about DALCH1? what kind of amount is needed here?

			headerXrech.getDETAIL().add(detailXrech);

		}
	}

	private void mapAlCh(EDICctopInvoicVType invoice, DecimalFormat decimalFormat, ObjectFactory objectFactory, HEADERXrech headerXrech, String documentId)
	{
		//TODO check if this mapping is for the deatils, or what the differences are
		for (final EDICctop901991VType xmlCctop901991V : invoice.getEDICctop901991V())
		{
			final HALCH1 alch = objectFactory.createHALCH1();
			alch.setDOCUMENTID(documentId);
			//TODO where to get this from???
			//charge.setSPECSERVICECODE();
			alch.setAMOUNT(formatNumber(xmlCctop901991V.getTotalAmt(), decimalFormat));
			alch.setTAXRATE(formatNumber(xmlCctop901991V.getRate(), decimalFormat));

			if (xmlCctop901991V.getTaxAmt() != null && xmlCctop901991V.getTaxBaseAmt() != null)
			{
				final BigDecimal taxSum = xmlCctop901991V.getTaxAmt().add(xmlCctop901991V.getTaxBaseAmt());
				alch.setTAXAMOUNT(formatNumber(taxSum, decimalFormat));
			}
			else
			{
				alch.setTAXAMOUNT(formatNumber(xmlCctop901991V.getTaxAmt(), decimalFormat));
			}
			//TODO check if condition is needed for when to set this
			alch.setTAXCODE(TaxQual.VATX.name());
			//TODO should do a substraction between total amount and tax amount here?
			//alch.setTAXABLEAMOUNT();
			headerXrech.getHALCH1().add(alch);
		}
		for (final EDICctop140VType xmlCctop901991V : invoice.getEDICctop140V())
		{
			final HALCH1 alch = objectFactory.createHALCH1();
			alch.setDOCUMENTID(documentId);
			//TODO refactor when you know how to get this from
			alch.setSPECSERVICECODE("DISC");
			alch.setAMOUNT(formatNumber(xmlCctop901991V.getDiscount(), decimalFormat));
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
			//TODO is this ok???
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

		//TODO check why this is a list. only one in spec
		for (final EDICctop140VType xmlCctop140V : invoice.getEDICctop140V())
		{
			final HPAYT1 paymentTerm = objectFactory.createHPAYT1();
			paymentTerm.setDOCUMENTID(documentId);
			paymentTerm.setTERMSQUAL(TermsQual.DISC.name());
			paymentTerm.setTIMEREFERENCE(ReferenceQual.INVO.name());
			paymentTerm.setTIMERELATION("AFTR");
			paymentTerm.setTIMEPERIODTYPE("DAYS");
			paymentTerm.setTIMEPERIODQUANTITY(formatNumber(xmlCctop140V.getDiscountDays(), decimalFormat));
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
			//TODO how to determine type - maybe ean com location type
			address.setADDRESSQUAL(AddressQual.BUYR.name());
			address.setPARTYIDGLN(xmlCctop119V.getGLN());

			//TODO only for BUYR and SUPL ???
			if (isEmpty(xmlCctop119V.getVATaxID()))
			{
				throw new RuntimeCamelException(xmlCctop119V + " must have a VATTaxID");
			}
			if (!isEmpty(xmlCctop119V.getAddress1()))
			{
				address.setSTREET1(normalize(xmlCctop119V.getAddress1()));
				address.setSTREET2(normalize(xmlCctop119V.getAddress2()));
			}
			else
			{
				if (isEmpty(xmlCctop119V.getAddress2()))
				{
					throw new RuntimeCamelException(xmlCctop119V + " must have at least one filled address");
				}
				address.setSTREET1(normalize(xmlCctop119V.getAddress2()));
			}
			if (!isEmpty(xmlCctop119V.getName()))
			{
				address.setNAME1(normalize(xmlCctop119V.getName()));
				address.setNAME2(normalize(xmlCctop119V.getName2()));
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
			//TODO additional cond to check if it's needed
			if (addressQual == AddressQual.SUPL)
			{
				final HFINI1 financialInst = objectFactory.createHFINI1();
				financialInst.setDOCUMENTID(documentId);
				financialInst.setADDRESSQUAL(AddressQual.SUPL.name());
				financialInst.setPARTYFUNCTIONQUAL(PartyFunctionQual.RCFI.name());
				//TODO check how to map the following
				financialInst.setACCOUNTHOLDERID(xmlCctop119V.getReferenceNo());
				//financialInst.setACCOUNTHOLDERNAME(); - optional
				//financialInst.setACCOUNTHOLDERCURR(); - optional
				//financialInst.setINSTITUTIONBRANCHID(); - optional
				address.setHFINI1(financialInst);
			}
			//TODO not mandatory for BUYR, check what other condition is needed
			if (addressQual == AddressQual.SUPL || addressQual == AddressQual.BUYR)
			{
				final HRFAD1 ref = objectFactory.createHRFAD1();
				ref.setDOCUMENTID(documentId);
				ref.setADDRESSQUAL(addressQual.name());
				ref.setREFERENCEQUAL(ReferenceQual.VATR.name());
				//TODO getVATaxID or getReferenceNo
				ref.setREFERENCE(xmlCctop119V.getVATaxID());
				address.getHRFAD1().add(ref);
			}

			//TODO this is optional for most, so add condition if it's needed
			final HCTAD1 contact = objectFactory.createHCTAD1();
			contact.setDOCUMENTID(documentId);
			contact.setADDRESSQUAL(addressQual.name());
			contact.setCONTACTQUAL(ContactQual.INFO.name());
			//TODO map this - it's mandatory
			//contact.setCONTACTDESC();
			contact.setCONTACTTELEFAX(xmlCctop119V.getFax());
			contact.setCONTACTTELEFON(xmlCctop119V.getPhone());
			//contact.setCONTACTEMAIL(); - optional
			address.setHCTAD1(contact);

			headerXrech.getHADRE1().add(address);

		}
	}

	private void mapReferences(EDICctopInvoicVType invoice, ObjectFactory objectFactory, HEADERXrech headerXrech, String documentId)
	{
		final HREFE1 orderRef = objectFactory.createHREFE1();
		orderRef.setDOCUMENTID(documentId);
		orderRef.setREFERENCEQUAL(ReferenceQual.ORBU.name());
		//TODO check if this needs to be built if missing
		orderRef.setREFERENCE(invoice.getPOReference());

		final HREFE1 despatchAdvRef = objectFactory.createHREFE1();
		despatchAdvRef.setDOCUMENTID(documentId);
		despatchAdvRef.setREFERENCEQUAL(ReferenceQual.DADV.name());
		despatchAdvRef.setREFERENCE(invoice.getShipmentDocumentno());

		// TODO only for credit note
		if (DocumentType.valueOf(headerXrech.getDOCUMENTTYP()) == DocumentType.CRNO)
		{
			final HREFE1 invoiceRef = objectFactory.createHREFE1();
			invoiceRef.setDOCUMENTID(documentId);
			invoiceRef.setREFERENCEQUAL(ReferenceQual.INVO.name());
			invoiceRef.setREFERENCE(invoice.getInvoiceDocumentno());
			headerXrech.getHREFE1().add(invoiceRef);
		}

		headerXrech.getHREFE1().add(orderRef);
		headerXrech.getHREFE1().add(despatchAdvRef);

		//TODO check if other ref types are needed
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
		//TODO what is value date?
		final HDATE1 valueDate = objectFactory.createHDATE1();
		valueDate.setDOCUMENTID(documentId);
		valueDate.setDATEQUAL(DateQual.VALU.name());
		valueDate.setDATEFROM(toFormattedStringDate(toDate(invoice.getMovementDate()), dateFormat));

		headerXrech.getHDATE1().add(documentDate);
		headerXrech.getHDATE1().add(deliveryDate);
		headerXrech.getHDATE1().add(valueDate);
	}
}
