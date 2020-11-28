/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.edi.esb.invoicexport.stepcom;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.InvoicSettings;
import de.metas.edi.esb.commons.MeasurementUnit;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.ValidationHelper;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.AddressQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.AmountQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.ControlQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.CurrencyQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.DateQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.DocumentFunction;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.DocumentType;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.EancomLocationQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.PriceQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.PriceSpecCode;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.ProductDescLang;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.ProductDescQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.ProductDescType;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.ProductQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.QuantityQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.ReferenceQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.TaxQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.TermsQual;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.TimePeriodType;
import de.metas.edi.esb.invoicexport.stepcom.qualifier.TimeRelation;
import de.metas.edi.esb.jaxb.metasfresh.EDICctop119VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctop120VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctop140VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctop901991VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoic500VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.stepcom.invoice.DAMOU1;
import de.metas.edi.esb.jaxb.stepcom.invoice.DETAILXrech;
import de.metas.edi.esb.jaxb.stepcom.invoice.DPRDE1;
import de.metas.edi.esb.jaxb.stepcom.invoice.DPRIC1;
import de.metas.edi.esb.jaxb.stepcom.invoice.DPRIN1;
import de.metas.edi.esb.jaxb.stepcom.invoice.DQUAN1;
import de.metas.edi.esb.jaxb.stepcom.invoice.DREFE1;
import de.metas.edi.esb.jaxb.stepcom.invoice.DTAXI1;
import de.metas.edi.esb.jaxb.stepcom.invoice.Document;
import de.metas.edi.esb.jaxb.stepcom.invoice.HADRE1;
import de.metas.edi.esb.jaxb.stepcom.invoice.HALCH1;
import de.metas.edi.esb.jaxb.stepcom.invoice.HCURR1;
import de.metas.edi.esb.jaxb.stepcom.invoice.HDATE1;
import de.metas.edi.esb.jaxb.stepcom.invoice.HEADERXrech;
import de.metas.edi.esb.jaxb.stepcom.invoice.HPAYT1;
import de.metas.edi.esb.jaxb.stepcom.invoice.HREFE1;
import de.metas.edi.esb.jaxb.stepcom.invoice.HRFAD1;
import de.metas.edi.esb.jaxb.stepcom.invoice.ObjectFactory;
import de.metas.edi.esb.jaxb.stepcom.invoice.TAMOU1;
import de.metas.edi.esb.jaxb.stepcom.invoice.TRAILR;
import de.metas.edi.esb.jaxb.stepcom.invoice.TTAXI1;
import de.metas.edi.esb.jaxb.stepcom.invoice.Xrech4H;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.commons.lang.StringUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import static de.metas.edi.esb.commons.Util.formatNumber;
import static de.metas.edi.esb.commons.Util.isEmpty;
import static de.metas.edi.esb.commons.Util.normalize;
import static de.metas.edi.esb.commons.Util.toDate;
import static de.metas.edi.esb.commons.Util.toFormattedStringDate;
import static de.metas.edi.esb.commons.Util.trimAndTruncate;
import static de.metas.edi.esb.commons.ValidationHelper.validateString;

public class StepComXMLInvoicBean
{
	public static final String METHOD_createXMLEDIData = "createXMLEDIData";

	private static final ObjectFactory INVOIC_objectFactory = new ObjectFactory();

	/**
	 * Credit note - metasfresh "ARC" base doc type and "CR" sub doc type
	 */
	private static final String DOC_CRNO_83 = "83";

	/**
	 * Credit note - metasfresh "ARC" base doc type and "CQ"="quality-adjustment-credit-memo", "CS"="material-return-credit-memo" sub doc types
	 */
	private static final String DOC_CRNF_381 = "381";

	/**
	 * Commercial invoice - metasfresh "ARI" base doc type
	 */
	private static final String DOC_CMIV_380 = "380";

	/**
	 * Debit note - metasfresh "ARI" base doc type and "AQ"="quantity-adjustment-debit-note" sub doc type
	 */
	private static final String DOC_DBNO_383 = "383";

	/**
	 * Debit note - metasfresh "ARI" base doc type and "AP"="price-adjustment-debit-note" sub doc type
	 */
	private static final String DOC_DBNF_84 = "84";

	public void createXMLEDIData(final Exchange exchange)
	{
		final EDICctopInvoicVType xmlCctopInvoice = exchange.getIn().getBody(EDICctopInvoicVType.class);

		final InvoicSettings settings = InvoicSettings.forReceiverGLN(exchange.getContext(), xmlCctopInvoice.getReceivergln());
		final Xrech4H xrech4H = createDocument(exchange, xmlCctopInvoice, settings);

		final Document document = INVOIC_objectFactory.createDocument();
		document.getXrech4H().add(xrech4H);

		exchange.getIn().setBody(INVOIC_objectFactory.createDocument(document));

		final String fileName = settings.getFileNamePrefix() + "_" + xrech4H.getHEADER().getDOCUMENTID() + "_" + SystemTime.millis() + ".xml";
		exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
	}

	private Xrech4H createDocument(
			@NonNull final Exchange exchange,
			@NonNull final EDICctopInvoicVType invoice,
			@NonNull final InvoicSettings settings)
	{
		final DecimalFormat decimalFormat = exchange.getProperty(Constants.DECIMAL_FORMAT, DecimalFormat.class);
		final String ownerId = exchange.getProperty(StepComXMLInvoicRoute.EDI_XML_OWNER_ID, String.class);

		final Xrech4H xrech4H = INVOIC_objectFactory.createXrech4H();

		final HEADERXrech headerXrech = INVOIC_objectFactory.createHEADERXrech();
		headerXrech.setTESTINDICATOR(settings.getTestIndicator());

		headerXrech.setPARTNERID(settings.getPartnerId());
		headerXrech.setAPPLICATIONREF(settings.getApplicationRef());
		headerXrech.setOWNERID(ownerId);
		final String documentId = invoice.getInvoiceDocumentno();
		headerXrech.setDOCUMENTID(documentId);
		final DocumentType documentType = mapDocumentType(invoice, settings);
		if (documentType == null)
		{
			throw new RuntimeCamelException("Could not identify document type");
		}
		headerXrech.setDOCUMENTTYP(documentType.name());
		headerXrech.setDOCUMENTFUNCTION(DocumentFunction.ORIG.name());

		final String dateFormat = (String)exchange.getProperty(AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);
		mapDates(invoice, headerXrech, dateFormat);

		mapReferences(invoice, headerXrech, dateFormat, settings);

		mapAddresses(invoice, headerXrech, settings);

		final HCURR1 currency = INVOIC_objectFactory.createHCURR1();
		currency.setDOCUMENTID(documentId);
		currency.setCURRENCYQUAL(CurrencyQual.INVO.name());
		currency.setCURRENCYCODE(invoice.getISOCode());
		headerXrech.getHCURR1().add(currency);

		mapPaymentTerms(invoice, headerXrech, dateFormat, decimalFormat);

		mapAlCh(invoice, headerXrech, decimalFormat);

		mapDetails(invoice, headerXrech, decimalFormat, settings);

		final TRAILR docTrailer = INVOIC_objectFactory.createTRAILR();
		docTrailer.setDOCUMENTID(documentId);
		docTrailer.setCONTROLQUAL(ControlQual.LINE.name());
		docTrailer.setCONTROLVALUE(formatNumber(invoice.getEDICctopInvoic500V().size(), decimalFormat));
		mapTrailer(invoice, decimalFormat, docTrailer);

		xrech4H.setHEADER(headerXrech);
		xrech4H.setTRAILR(docTrailer);

		return xrech4H;
	}

	private DocumentType mapDocumentType(
			@NonNull final EDICctopInvoicVType invoice,
			@NonNull final InvoicSettings settings)
	{
		final String eancomDoctype = invoice.getEancomDoctype();

		if (DOC_CRNO_83.equals(eancomDoctype))
		{
			return settings.getInvoicDocumentTypeCRNOAlias();
		}
		else if (DOC_CRNF_381.equals(eancomDoctype))
		{
			return settings.getInvoicDocumentTypeCRNFAlias();
		}
		else if (DOC_DBNO_383.equals(eancomDoctype))
		{
			return settings.getInvoicDocumentTypeDBNOAlias();
		}
		else if (DOC_DBNF_84.equals(eancomDoctype))
		{
			return settings.getInvoicDocumentTypeDBNFAlias();
		}
		else if (DOC_CMIV_380.equals(eancomDoctype))
		{
			return settings.getInvoicDocumentTypeCMIVAlias();
		}

		throw new RuntimeCamelException("@FillMandatory@ @C_Invoice_ID@=" + invoice.getInvoiceDocumentno() + " @EancomDoctype@");
	}

	private void mapTrailer(final EDICctopInvoicVType invoice, final DecimalFormat decimalFormat, final TRAILR docTrailer)
	{
		final String documentId = docTrailer.getDOCUMENTID();
		final TAMOU1 trailerLinesAmount = INVOIC_objectFactory.createTAMOU1();
		trailerLinesAmount.setDOCUMENTID(documentId);
		trailerLinesAmount.setAMOUNTQUAL(AmountQual.TLIN.name());
		trailerLinesAmount.setAMOUNT(formatNumber(invoice.getTotalLines(), decimalFormat));
		trailerLinesAmount.setCURRENCY(invoice.getISOCode());
		docTrailer.getTAMOU1().add(trailerLinesAmount);

		final TAMOU1 trailerAmount = INVOIC_objectFactory.createTAMOU1();
		trailerAmount.setDOCUMENTID(documentId);
		trailerAmount.setAMOUNTQUAL(AmountQual.TINV.name());
		trailerAmount.setAMOUNT(formatNumber(invoice.getGrandTotal(), decimalFormat));
		trailerAmount.setCURRENCY(invoice.getISOCode());
		docTrailer.getTAMOU1().add(trailerAmount);

		final TAMOU1 trailerTaxAmount = INVOIC_objectFactory.createTAMOU1();
		trailerTaxAmount.setDOCUMENTID(documentId);
		trailerTaxAmount.setAMOUNTQUAL(AmountQual.TZAX.name());
		trailerTaxAmount.setAMOUNT(formatNumber(invoice.getTotalvat(), decimalFormat));
		trailerTaxAmount.setCURRENCY(invoice.getISOCode());
		docTrailer.getTAMOU1().add(trailerTaxAmount);

		for (final EDICctop901991VType xmlCctop901991V : invoice.getEDICctop901991V())
		{
			final TTAXI1 trailerTax = INVOIC_objectFactory.createTTAXI1();
			trailerTax.setDOCUMENTID(documentId);
			trailerTax.setTAXQUAL(TaxQual.VATX.name());
			trailerTax.setTAXRATE(formatNumber(xmlCctop901991V.getRate(), decimalFormat));
			trailerTax.setTAXAMOUNT(formatNumber(xmlCctop901991V.getTaxAmt(), decimalFormat));
			trailerTax.setTAXABLEAMOUNT(formatNumber(xmlCctop901991V.getTaxBaseAmt(), decimalFormat));
			docTrailer.getTTAXI1().add(trailerTax);
		}
	}

	private void mapDetails(
			final EDICctopInvoicVType invoice,
			final HEADERXrech headerXrech,
			final DecimalFormat decimalFormat,
			final InvoicSettings settings)
	{
		invoice.getEDICctopInvoic500V().sort(Comparator.comparing(EDICctopInvoic500VType::getLine));
		final String documentId = headerXrech.getDOCUMENTID();
		for (final EDICctopInvoic500VType xmlCctopInvoic500V : invoice.getEDICctopInvoic500V())
		{
			final DETAILXrech detailXrech = INVOIC_objectFactory.createDETAILXrech();
			detailXrech.setDOCUMENTID(documentId);
			final String lineNumber = formatNumber(xmlCctopInvoic500V.getLine(), decimalFormat);
			detailXrech.setLINENUMBER(lineNumber);

			if (settings.isInvoicLineBUYRRequired())
			{
				final String buyr = ValidationHelper.validateString(xmlCctopInvoic500V.getVendorProductNo(),
						"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getVendorProductNo() + " @VendorProductNo@");

				final DPRIN1 productInfo = INVOIC_objectFactory.createDPRIN1();
				productInfo.setDOCUMENTID(documentId);
				productInfo.setLINENUMBER(lineNumber);
				productInfo.setPRODUCTQUAL(ProductQual.BUYR.name());
				productInfo.setPRODUCTID(buyr);
				detailXrech.getDPRIN1().add(productInfo);
			}

			if (settings.isInvoicLineGTINRequired())
			{
				final String gtin = ValidationHelper.validateString(xmlCctopInvoic500V.getGTIN(),
						"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @GTIN@");
				final DPRIN1 dprin1 = INVOIC_objectFactory.createDPRIN1();
				dprin1.setDOCUMENTID(documentId);
				dprin1.setLINENUMBER(lineNumber);
				dprin1.setPRODUCTQUAL(ProductQual.GTIN.name());
				dprin1.setPRODUCTID(gtin);
				detailXrech.getDPRIN1().add(dprin1);
			}

			if (settings.isInvoicLineEANTRequired())
			{
				final String eant = ValidationHelper.validateString(xmlCctopInvoic500V.getEANTU(),
						"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @EANTU@");
				final DPRIN1 dprin1 = INVOIC_objectFactory.createDPRIN1();
				dprin1.setDOCUMENTID(documentId);
				dprin1.setLINENUMBER(lineNumber);
				dprin1.setPRODUCTQUAL(ProductQual.EANT.name());
				dprin1.setPRODUCTID(eant);
				detailXrech.getDPRIN1().add(dprin1);
			}
			if (settings.isInvoicLineEANCRequired())
			{
				final String eanc = ValidationHelper.validateString(xmlCctopInvoic500V.getEANCU(),
						"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @EANCU@");
				final DPRIN1 dprin1 = INVOIC_objectFactory.createDPRIN1();
				dprin1.setDOCUMENTID(documentId);
				dprin1.setLINENUMBER(lineNumber);
				dprin1.setPRODUCTQUAL(ProductQual.EANC.name());
				dprin1.setPRODUCTID(eanc);
				detailXrech.getDPRIN1().add(dprin1);
			}
			if (settings.isInvoicLineUPCTRequired())
			{
				final String upct = ValidationHelper.validateString(xmlCctopInvoic500V.getUPCTU(),
						"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @UPCTU@");
				final DPRIN1 dprin1 = INVOIC_objectFactory.createDPRIN1();
				dprin1.setDOCUMENTID(documentId);
				dprin1.setLINENUMBER(lineNumber);
				dprin1.setPRODUCTQUAL(ProductQual.UPCT.name());
				dprin1.setPRODUCTID(upct);
				detailXrech.getDPRIN1().add(dprin1);
			}
			if (settings.isInvoicLineUPCCRequired())
			{
				final String upcc = ValidationHelper.validateString(xmlCctopInvoic500V.getUPCCU(),
						"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @UPCCU@");
				final DPRIN1 dprin1 = INVOIC_objectFactory.createDPRIN1();
				dprin1.setDOCUMENTID(documentId);
				dprin1.setLINENUMBER(lineNumber);
				dprin1.setPRODUCTQUAL(ProductQual.UPCT.name());
				dprin1.setPRODUCTID(upcc);
				detailXrech.getDPRIN1().add(dprin1);
			}

			{
				final DPRIN1 dprin1 = INVOIC_objectFactory.createDPRIN1();
				dprin1.setDOCUMENTID(documentId);
				dprin1.setLINENUMBER(lineNumber);
				dprin1.setPRODUCTQUAL(ProductQual.SUPL.name());
				dprin1.setPRODUCTID(xmlCctopInvoic500V.getValue());
				detailXrech.getDPRIN1().add(dprin1);
			}

			final DPRDE1 productDescr = INVOIC_objectFactory.createDPRDE1();
			productDescr.setDOCUMENTID(documentId);
			productDescr.setLINENUMBER(lineNumber);
			productDescr.setPRODUCTDESCQUAL(ProductDescQual.PROD.name());
			productDescr.setPRODUCTDESCTEXT(trimAndTruncate(xmlCctopInvoic500V.getProductDescription(), 512));
			// use consumer unit and german language as default
			productDescr.setPRODUCTDESCTYPE(ProductDescType.CU.name());
			productDescr.setPRODUCTDESCLANG(ProductDescLang.DE.name());
			detailXrech.getDPRDE1().add(productDescr);

			mapInvoicedQty(detailXrech, xmlCctopInvoic500V, settings, decimalFormat);

			final DAMOU1 amount = INVOIC_objectFactory.createDAMOU1();
			amount.setDOCUMENTID(documentId);
			amount.setLINENUMBER(lineNumber);
			amount.setAMOUNTQUAL(AmountQual.ANET.name());
			amount.setAMOUNT(formatNumber(xmlCctopInvoic500V.getLineNetAmt(), decimalFormat));
			amount.setCURRENCY(xmlCctopInvoic500V.getISOCode());
			detailXrech.getDAMOU1().add(amount);

			final DPRIC1 price = INVOIC_objectFactory.createDPRIC1();
			final String priceUOM = ValidationHelper.validateString(xmlCctopInvoic500V.getEanComPriceUOM(),
					"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @Price_UOM_ID@");
			final MeasurementUnit priceMeasurementUnit = MeasurementUnit.fromMetasfreshUOM(priceUOM);
			price.setDOCUMENTID(documentId);
			price.setLINENUMBER(lineNumber);
			price.setPRICEQUAL(PriceQual.NETT.name());
			price.setPRICE(formatNumber(xmlCctopInvoic500V.getPriceActual(), decimalFormat));
			price.setPRICESPEC(PriceSpecCode.NETP.name());
			price.setPRICEMEASUREUNIT(priceMeasurementUnit.name());

			detailXrech.getDPRIC1().add(price);

			if (xmlCctopInvoic500V.getOrderLine() != null)
			{
				final DREFE1 reference = INVOIC_objectFactory.createDREFE1();
				reference.setDOCUMENTID(documentId);
				reference.setLINENUMBER(lineNumber);
				reference.setREFERENCEQUAL(ReferenceQual.ORBU.name());
				reference.setREFERENCE(xmlCctopInvoic500V.getOrderPOReference());
				reference.setREFERENCELINE(xmlCctopInvoic500V.getOrderLine().toString());
				detailXrech.getDREFE1().add(reference);
			}

			final DTAXI1 tax = INVOIC_objectFactory.createDTAXI1();
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

	private void mapInvoicedQty(
			@NonNull final DETAILXrech detailXrech,
			@NonNull final EDICctopInvoic500VType xmlCctopInvoic500V,
			@NonNull final InvoicSettings settings,
			@NonNull final DecimalFormat decimalFormat)
	{
		final String lineNumber = formatNumber(xmlCctopInvoic500V.getLine(), decimalFormat);

		final DQUAN1 dQuan1 = INVOIC_objectFactory.createDQUAN1();
		detailXrech.getDQUAN1().add(dQuan1);
		dQuan1.setDOCUMENTID(detailXrech.getDOCUMENTID());
		dQuan1.setLINENUMBER(lineNumber);
		dQuan1.setQUANTITYQUAL(QuantityQual.INVO.name());

		switch (settings.getInvoicLineQuantityInUOM())
		{
			case InvoicedUOM -> {
				final BigDecimal qtyInvoiced = ValidationHelper.validateObject(xmlCctopInvoic500V.getQtyInvoiced(),
						"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @QtyInvoiced@");
				dQuan1.setQUANTITY(formatNumber(qtyInvoiced, decimalFormat));
				if (settings.isInvoicLineMEASUREMENTUNITRequired())
				{
					final String eanComUom = ValidationHelper.validateString(xmlCctopInvoic500V.getEanComUOM(),
							"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @EanComUOM@");
					final MeasurementUnit measurementUnit = MeasurementUnit.fromMetasfreshUOM(eanComUom);
					if (!settings.isMeasurementUnitAllowed(measurementUnit))
					{
						throw new RuntimeCamelException("@C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @EanComUOM@=" + settings.getInvoicLineRequiredMEASUREMENTUNIT() + " @REQUIRED@");
					}
					dQuan1.setMEASUREMENTUNIT(measurementUnit.name());
				}
			}
			case OrderedUOM -> {
				final BigDecimal qtyInvoicedInOrderedUOM = ValidationHelper.validateObject(xmlCctopInvoic500V.getQtyInvoicedInOrderedUOM(),
						"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @QtyInvoicedInOrderedUOM@");
				dQuan1.setQUANTITY(formatNumber(qtyInvoicedInOrderedUOM, decimalFormat));
				if (settings.isInvoicLineMEASUREMENTUNITRequired())
				{
					final String eanComUom = ValidationHelper.validateString(xmlCctopInvoic500V.getEanComOrderedUOM(),
							"@FillMandatory@ @C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @EanComOrderedUOM@");
					final MeasurementUnit measurementUnit = MeasurementUnit.fromMetasfreshUOM(eanComUom);
					if (!settings.isMeasurementUnitAllowed(measurementUnit))
					{
						throw new RuntimeCamelException("@C_InvoiceLine_ID@=" + xmlCctopInvoic500V.getLine() + " @EanComOrderedUOM@=" + settings.getInvoicLineRequiredMEASUREMENTUNIT() + " @REQUIRED@");
					}
					dQuan1.setMEASUREMENTUNIT(measurementUnit.name());
				}
			}
			default -> throw new RuntimeCamelException("Unexpected InvoicLineQuantityInUOM=" + settings.getInvoicLineQuantityInUOM());
		}
	}

	private void mapAlCh(final EDICctopInvoicVType invoice, final HEADERXrech headerXrech, final DecimalFormat decimalFormat)
	{
		for (final EDICctop901991VType xmlCctop901991V : invoice.getEDICctop901991V())
		{
			final HALCH1 alch = INVOIC_objectFactory.createHALCH1();
			alch.setDOCUMENTID(headerXrech.getDOCUMENTID());
			alch.setAMOUNT(formatNumber(xmlCctop901991V.getTotalAmt(), decimalFormat));
			alch.setTAXRATE(formatNumber(xmlCctop901991V.getRate(), decimalFormat));

			alch.setTAXAMOUNT(formatNumber(xmlCctop901991V.getTaxAmt(), decimalFormat));
			alch.setTAXCODE(TaxQual.VATX.name());
			alch.setTAXABLEAMOUNT(formatNumber(xmlCctop901991V.getTaxBaseAmt(), decimalFormat));
			headerXrech.getHALCH1().add(alch);
		}

	}

	private void mapPaymentTerms(
			final EDICctopInvoicVType invoice,
			final HEADERXrech headerXrech,
			final String dateFormat,
			final DecimalFormat decimalFormat)
	{
		for (final EDICctop120VType xmlCctop120V : invoice.getEDICctop120V())
		{
			// note: looking at the view, we can expect exactly one xmlCctop120V item
			final HPAYT1 paymentTerm = INVOIC_objectFactory.createHPAYT1();
			paymentTerm.setDOCUMENTID(headerXrech.getDOCUMENTID());
			paymentTerm.setTERMSQUAL(TermsQual.FIXD.name());
			paymentTerm.setTIMEREFERENCE(ReferenceQual.INVO.name());
			paymentTerm.setTERMSDATEFROM(toFormattedStringDate(toDate(invoice.getDateInvoiced()), dateFormat));
			paymentTerm.setTIMERELATION(TimeRelation.AFTR.name());
			paymentTerm.setTIMEPERIODQUANTITY(formatNumber(xmlCctop120V.getNetDays(), decimalFormat));
			paymentTerm.setTIMEPERIODTYPE(TimePeriodType.DAYS.name());
			headerXrech.getHPAYT1().add(paymentTerm);
		}

		for (final EDICctop140VType xmlCctop140V : invoice.getEDICctop140V())
		{
			final HPAYT1 paymentTerm = INVOIC_objectFactory.createHPAYT1();
			paymentTerm.setDOCUMENTID(headerXrech.getDOCUMENTID());
			paymentTerm.setTERMSQUAL(TermsQual.DISC.name());
			paymentTerm.setTIMEREFERENCE(ReferenceQual.INVO.name());
			paymentTerm.setTERMSDATEFROM(toFormattedStringDate(toDate(invoice.getDateInvoiced()), dateFormat));
			paymentTerm.setTIMERELATION(TimeRelation.AFTR.name());
			paymentTerm.setTIMEPERIODQUANTITY(formatNumber(xmlCctop140V.getDiscountDays(), decimalFormat));
			paymentTerm.setTIMEPERIODTYPE(TimePeriodType.DAYS.name());
			paymentTerm.setDISCOUNTPERCENT(formatNumber(xmlCctop140V.getDiscount(), decimalFormat));
			headerXrech.getHPAYT1().add(paymentTerm);
		}
	}

	private void mapAddresses(
			@NonNull final EDICctopInvoicVType invoice,
			@NonNull final HEADERXrech headerXrech,
			@NonNull final InvoicSettings settings)
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

			final HADRE1 address = INVOIC_objectFactory.createHADRE1();
			address.setDOCUMENTID(headerXrech.getDOCUMENTID());
			final EancomLocationQual eancomLocationQual = EancomLocationQual.valueOf(xmlCctop119V.getEancomLocationtype());
			final AddressQual addressQual = mapAddressQual(eancomLocationQual);
			if (addressQual == null)
			{
				throw new RuntimeCamelException(xmlCctop119V + " could not identify address qualifier");
			}
			address.setADDRESSQUAL(addressQual.name());
			address.setPARTYIDGLN(validateAndGetGLN(invoice, xmlCctop119V, settings));

			final String street1 = validateAndGetStreet1(invoice, xmlCctop119V, settings);
			if (!isEmpty(street1))
			{
				address.setSTREET1(trimAndTruncate(xmlCctop119V.getAddress1(), 35));
				address.setSTREET2(trimAndTruncate(xmlCctop119V.getAddress2(), 35));
			}
			else
			{
				if (isEmpty(xmlCctop119V.getAddress2()))
				{
					throw new RuntimeCamelException(xmlCctop119V + " must have at least one filled address");
				}
				address.setSTREET1(trimAndTruncate(xmlCctop119V.getAddress2(), 35));
			}

			final String addressName1 = validateAndGetAddressName1(invoice, xmlCctop119V, settings);
			if (!isEmpty(addressName1))
			{
				address.setNAME1(trimAndTruncate(addressName1, 35));
				address.setNAME2(trimAndTruncate(xmlCctop119V.getName2(), 35));
			}
			else
			{
				if (isEmpty(xmlCctop119V.getName2()))
				{
					throw new RuntimeCamelException(xmlCctop119V + " must have at least one filled name");
				}
				address.setNAME1(normalize(xmlCctop119V.getName2()));
			}

			final String city = validateAndGetCity(invoice, xmlCctop119V, settings);
			address.setCITY(trimAndTruncate(city, 35));

			final String postalCode = validateAndGetPostalCode(invoice, xmlCctop119V, settings);
			address.setPOSTALCODE(trimAndTruncate(postalCode, 20));

			address.setCOUNTRY(trimAndTruncate(xmlCctop119V.getCountryCode(), 20));

			if (addressQual == AddressQual.SUPL && isEmpty(xmlCctop119V.getVATaxID()))
			{
				throw new RuntimeCamelException(xmlCctop119V + " must have a VATTaxID");
			}
			if (addressQual == AddressQual.SUPL || addressQual == AddressQual.BUYR && StringUtils.isNotEmpty(xmlCctop119V.getVATaxID()))
			{
				final HRFAD1 ref = INVOIC_objectFactory.createHRFAD1();
				ref.setDOCUMENTID(headerXrech.getDOCUMENTID());
				ref.setADDRESSQUAL(addressQual.name());
				ref.setREFERENCEQUAL(ReferenceQual.VATR.name());
				ref.setREFERENCE(xmlCctop119V.getVATaxID());
				address.getHRFAD1().add(ref);
			}

			if (addressQual == AddressQual.SUPL)
			{
				// copy and create ISSI (Issuer of invoice) address
				final HADRE1 issiAddress = copyAddress(address, AddressQual.ISSI);
				headerXrech.getHADRE1().add(issiAddress);
			}

			headerXrech.getHADRE1().add(address);
		}
	}

	private String validateAndGetAddressName1(
			@NonNull final EDICctopInvoicVType invoice,
			@NonNull final EDICctop119VType xmlCctop119V,
			@NonNull final InvoicSettings settings)
	{
		final EancomLocationQual eancomLocationQual = EancomLocationQual.valueOf(xmlCctop119V.getEancomLocationtype());
		final AddressQual addressQual = mapAddressQual(eancomLocationQual);

		final String addressName = xmlCctop119V.getName();
		if (addressQual == AddressQual.BUYR && settings.isInvoicBUYRAddressName1Required())
		{
			validateString(addressName, "@FillMandatory@ @C_Partner_ID@ @C_BPartner_Location_ID@ @Name@: " + xmlCctop119V);
		}
		else if (addressQual == AddressQual.IVCE && settings.isInvoicIVCEAddressName1Required())
		{
			validateString(addressName, "@FillMandatory@ @Bill_Partner_ID@ @C_BPartner_Location_ID@ @Name@: " + xmlCctop119V);
		}
		else // default
		{
			validateString(addressName, "@FillMandatory@ @C_BPartner_Location_ID@ @Name@: " + xmlCctop119V);
		}
		return addressName;
	}

	private String validateAndGetGLN(
			@NonNull final EDICctopInvoicVType invoice,
			@NonNull final EDICctop119VType xmlCctop119V,
			@NonNull final InvoicSettings settings)
	{
		final EancomLocationQual eancomLocationQual = EancomLocationQual.valueOf(xmlCctop119V.getEancomLocationtype());
		final AddressQual addressQual = mapAddressQual(eancomLocationQual);

		final String gln = xmlCctop119V.getGLN();
		if (addressQual == AddressQual.BUYR && settings.isInvoicBUYRGLNRequired())
		{
			validateString(gln, "@FillMandatory@ @C_Partner_ID@ @C_BPartner_Location_ID@ @GLN@: " + xmlCctop119V);
		}
		else if (addressQual == AddressQual.IVCE && settings.isInvoicIVCEGLNRequired())
		{
			validateString(gln, "@FillMandatory@ @Bill_Partner_ID@ @C_BPartner_Location_ID@ @GLN@: " + xmlCctop119V);
		}
		else // default
		{
			validateString(gln, "@FillMandatory@ @C_BPartner_Location_ID@ @GLN@: " + xmlCctop119V);
		}
		return gln;
	}

	private String validateAndGetStreet1(
			@NonNull final EDICctopInvoicVType invoice,
			@NonNull final EDICctop119VType xmlCctop119V,
			@NonNull final InvoicSettings settings)
	{
		final EancomLocationQual eancomLocationQual = EancomLocationQual.valueOf(xmlCctop119V.getEancomLocationtype());
		final AddressQual addressQual = mapAddressQual(eancomLocationQual);

		final String street1 = xmlCctop119V.getAddress1();
		if (addressQual == AddressQual.BUYR && settings.isInvoicBUYRStreet1Required())
		{
			validateString(street1, "@FillMandatory@ @C_Partner_ID@ @C_BPartner_Location_ID@ @Address1@: " + xmlCctop119V);
		}
		else if (addressQual == AddressQual.IVCE && settings.isInvoicIVCEStreet1Required())
		{
			validateString(street1, "@FillMandatory@ @Bill_Partner_ID@ @C_BPartner_Location_ID@ @Address1@: " + xmlCctop119V);
		}
		return street1;
	}

	private String validateAndGetPostalCode(
			@NonNull final EDICctopInvoicVType invoice,
			@NonNull final EDICctop119VType xmlCctop119V,
			@NonNull final InvoicSettings settings)
	{
		final EancomLocationQual eancomLocationQual = EancomLocationQual.valueOf(xmlCctop119V.getEancomLocationtype());
		final AddressQual addressQual = mapAddressQual(eancomLocationQual);

		final String postal = xmlCctop119V.getPostal();
		if (addressQual == AddressQual.BUYR && settings.isInvoicBUYRPostaCodeRequired())
		{
			validateString(postal, "@FillMandatory@ @C_Partner_ID@ @C_BPartner_Location_ID@ @Postal@: " + xmlCctop119V);
		}
		else if (addressQual == AddressQual.IVCE && settings.isInvoicIVCEStreet1Required())
		{
			validateString(postal, "@FillMandatory@ @Bill_Partner_ID@ @C_BPartner_Location_ID@ @Postal@: " + xmlCctop119V);
		}
		return postal;
	}

	private String validateAndGetCity(
			@NonNull final EDICctopInvoicVType invoice,
			@NonNull final EDICctop119VType xmlCctop119V,
			@NonNull final InvoicSettings settings)
	{
		final EancomLocationQual eancomLocationQual = EancomLocationQual.valueOf(xmlCctop119V.getEancomLocationtype());
		final AddressQual addressQual = mapAddressQual(eancomLocationQual);

		final String city = xmlCctop119V.getCity();
		if (addressQual == AddressQual.BUYR && settings.isInvoicBUYRPostaCodeRequired())
		{
			validateString(city, "@FillMandatory@ @C_Partner_ID@ @C_BPartner_Location_ID@ @City@: " + xmlCctop119V);
		}
		else if (addressQual == AddressQual.IVCE && settings.isInvoicIVCEStreet1Required())
		{
			validateString(city, "@FillMandatory@ @Bill_Partner_ID@ @C_BPartner_Location_ID@ @City@: " + xmlCctop119V);
		}
		return city;
	}

	/**
	 * Note: currently doesn't copy the address.
	 */
	private HADRE1 copyAddress(
			@NonNull final HADRE1 address,
			@NonNull final AddressQual qualifier)
	{
		final HADRE1 issiAddress = INVOIC_objectFactory.createHADRE1();

		issiAddress.setADDRESSQUAL(qualifier.name());
		issiAddress.setDOCUMENTID(address.getDOCUMENTID());
		issiAddress.setPARTYIDGLN(address.getPARTYIDGLN());
		issiAddress.setSTREET1(trimAndTruncate(address.getSTREET1(), 35));
		issiAddress.setSTREET2(trimAndTruncate(address.getSTREET2(), 35));
		issiAddress.setNAME1(trimAndTruncate(address.getNAME1(), 35));
		issiAddress.setNAME2(trimAndTruncate(address.getNAME2(), 35));
		issiAddress.setCITY(trimAndTruncate(address.getCITY(), 35));
		issiAddress.setPOSTALCODE(trimAndTruncate(address.getPOSTALCODE(), 20));
		issiAddress.setCOUNTRY(trimAndTruncate(address.getCOUNTRY(), 20));

		return issiAddress;
	}

	private AddressQual mapAddressQual(@NonNull final EancomLocationQual eancomLocationQual)
	{
		AddressQual addressQual = null;
		switch (eancomLocationQual)
		{
			case DP -> addressQual = AddressQual.DELV;
			case IV -> addressQual = AddressQual.IVCE;
			case BY -> addressQual = AddressQual.BUYR;
			case SU -> addressQual = AddressQual.SUPL;
			case SN -> addressQual = AddressQual.ULCO;
		}
		return addressQual;
	}

	private void mapReferences(
			@NonNull final EDICctopInvoicVType invoice,
			@NonNull final HEADERXrech headerXrech,
			@NonNull final String dateFormat,
			@NonNull final InvoicSettings settings)
	{
		final HREFE1 buyerOrderRef = INVOIC_objectFactory.createHREFE1();
		buyerOrderRef.setDOCUMENTID(headerXrech.getDOCUMENTID());
		buyerOrderRef.setREFERENCEQUAL(ReferenceQual.ORBU.name());

		final String poReference = validateString(invoice.getPOReference(), "@FillMandatory@ @POReference@");
		buyerOrderRef.setREFERENCE(poReference);

		buyerOrderRef.setREFERENCEDATE1(toFormattedStringDate(toDate(invoice.getDateOrdered()), dateFormat));
		headerXrech.getHREFE1().add(buyerOrderRef);

		if (settings.isInvoicORSE())
		{
			final HREFE1 sellerOrderRef = INVOIC_objectFactory.createHREFE1();
			sellerOrderRef.setDOCUMENTID(headerXrech.getDOCUMENTID());
			sellerOrderRef.setREFERENCEQUAL(ReferenceQual.ORSE.name());
			sellerOrderRef.setREFERENCE(invoice.getEDICctop111V().getCOrderID().toString());
			sellerOrderRef.setREFERENCEDATE1(toFormattedStringDate(toDate(invoice.getDateOrdered()), dateFormat));
			headerXrech.getHREFE1().add(sellerOrderRef);
		}
		if (!isEmpty(invoice.getShipmentDocumentno()))
		{
			final HREFE1 despatchAdvRef = INVOIC_objectFactory.createHREFE1();
			despatchAdvRef.setDOCUMENTID(headerXrech.getDOCUMENTID());
			despatchAdvRef.setREFERENCEQUAL(ReferenceQual.DADV.name());
			despatchAdvRef.setREFERENCE(invoice.getShipmentDocumentno());
			despatchAdvRef.setREFERENCEDATE1(toFormattedStringDate(toDate(invoice.getMovementDate()), dateFormat));
			headerXrech.getHREFE1().add(despatchAdvRef);
		}
	}

	private void mapDates(
			@NonNull final EDICctopInvoicVType invoice,
			@NonNull final HEADERXrech headerXrech,
			@NonNull final String dateFormat)
	{
		final XMLGregorianCalendar dateInvoiced = ValidationHelper.validateObject(invoice.getDateInvoiced(),
				"@FillMandatory@ @C_Invoice_ID@=" + invoice.getInvoiceDocumentno() + " @DateInvoiced@");

		// CREA
		final HDATE1 documentDate = INVOIC_objectFactory.createHDATE1();
		documentDate.setDOCUMENTID(headerXrech.getDOCUMENTID());
		documentDate.setDATEQUAL(DateQual.CREA.name());
		documentDate.setDATEFROM(toFormattedStringDate(toDate(dateInvoiced), dateFormat));
		headerXrech.getHDATE1().add(documentDate);

		// VALU
		final HDATE1 valueDate = INVOIC_objectFactory.createHDATE1();
		valueDate.setDOCUMENTID(headerXrech.getDOCUMENTID());
		valueDate.setDATEQUAL(DateQual.VALU.name());
		valueDate.setDATEFROM(toFormattedStringDate(toDate(dateInvoiced), dateFormat));
		headerXrech.getHDATE1().add(valueDate);

		// DELV
		final XMLGregorianCalendar movementDate;
		if (DOC_CRNF_381.equals(invoice.getEancomDoctype()))
		{
			// may be null, if the invoice is a material return credit memo
			// TODO DOC_CRNF_381 means EITHER CQ => "credit memo - Lieferdifferenz" OR CS => "credit memo - Retoure"; missing movement date is only OK for "Retoure"
			movementDate = invoice.getMovementDate();
		}
		else
		{
			movementDate = ValidationHelper.validateObject(invoice.getMovementDate(),
					"@FillMandatory@ @C_Invoice_ID@=" + invoice.getInvoiceDocumentno() + " @MovementDate@");
		}
		if (movementDate != null)
		{
			final HDATE1 deliveryDate = INVOIC_objectFactory.createHDATE1();
			deliveryDate.setDOCUMENTID(headerXrech.getDOCUMENTID());
			deliveryDate.setDATEQUAL(DateQual.DELV.name());
			deliveryDate.setDATEFROM(toFormattedStringDate(toDate(movementDate), dateFormat));
			headerXrech.getHDATE1().add(deliveryDate);
		}
	}
}
