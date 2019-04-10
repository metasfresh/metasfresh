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

package de.metas.edi.esb.bean.desadv;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.EDIExpCBPartnerLocationType;
import de.metas.edi.esb.jaxb.EDIExpDesadvLineType;
import de.metas.edi.esb.jaxb.EDIExpDesadvType;
import de.metas.edi.esb.pojo.desadv.*;
import de.metas.edi.esb.pojo.desadv.qualifier.*;
import de.metas.edi.esb.route.AbstractEDIRoute;
import de.metas.edi.esb.route.exports.XMLDesadvRoute;
import org.apache.camel.Exchange;
import org.apache.commons.lang.StringUtils;
import org.milyn.payload.JavaSource;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Comparator;

import static de.metas.edi.esb.commons.Util.*;

public class EDIXMLDesadvBean
{
	public static final String METHOD_createXMLEDIData = "createXMLEDIData";

	public final void createXMLEDIData(final Exchange exchange)
	{
		final EDIDesadvValidation validation = new EDIDesadvValidation();
		final EDIExpDesadvType xmlDesadv = validation.validateExchange(exchange); // throw exceptions if mandatory fields are missing
		final Document desadvDocument = createDesadvDocumentFromXMLBean(xmlDesadv, exchange);

		final JavaSource source = new JavaSource(desadvDocument);
		exchange.getIn().setBody(source, Document.class);
	}

	private Document createDesadvDocumentFromXMLBean(EDIExpDesadvType xmlDesadv, Exchange exchange)
	{
		final DecimalFormat decimalFormat = exchange.getProperty(Constants.DECIMAL_FORMAT, DecimalFormat.class);
		final String isTest = exchange.getProperty(XMLDesadvRoute.EDI_XML_DESADV_IS_TEST, String.class);
		final String dateFormat = (String)exchange.getProperty(AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);

		xmlDesadv.getEDIExpDesadvLine().sort(Comparator.comparing(EDIExpDesadvLineType::getLine));
		final ObjectFactory objectFactory = new ObjectFactory();
		final Document document = objectFactory.createDocument();
		final Xlief4H xlief4H = objectFactory.createXlief4H();

		final HEADERXlief header = objectFactory.createHEADERXlief();
		final String documentId = xmlDesadv.getDocumentNo();
		header.setDOCUMENTID(documentId);
		//TODO use property
		header.setPARTNERID(xmlDesadv.getCBPartnerID().getValue());
		//TODO check if edi.props.000.sender.gln can be used or create new prop
		//header.setOWNERID();
		header.setTESTINDICATOR(isTest);
		header.setMESSAGEREF(formatNumber(xmlDesadv.getSequenceNoAttr(), decimalFormat));
		//TODO use proeprty
		//header.setAPPLICATIONREF();
		header.setDOCUMENTTYP(DocumentType.DADV.name());
		header.setDOCUMENTFUNCTION(DocumentFunction.ORIG.name());

		mapDates(xmlDesadv, dateFormat, objectFactory, header, documentId);

		mapReferences(xmlDesadv, objectFactory, header, documentId);

		mapAddresses(xmlDesadv, objectFactory, header, documentId);

		mapPackaging(xmlDesadv, decimalFormat, objectFactory, header, documentId);

		final TRAILR trailr = objectFactory.createTRAILR();
		trailr.setDOCUMENTID(documentId);
		trailr.setCONTROLQUAL(ControlQual.LINE.name());
		trailr.setCONTROLVALUE(formatNumber(xmlDesadv.getEDIExpDesadvLine().size(), decimalFormat));

		xlief4H.setHEADER(header);
		xlief4H.setTRAILR(trailr);
		document.getXlief4H().add(xlief4H);
		return document;
	}

	private DiscrepencyCode getDiscrepancyCode(final EDIExpDesadvLineType xmlDesadvLine)
	{
		DiscrepencyCode discrepancyCode;
		if (Boolean.parseBoolean(xmlDesadvLine.getIsSubsequentDeliveryPlanned()))
		{
			discrepancyCode = DiscrepencyCode.BFOL; // = Shipment partial - back order to follow
		}
		else
		{
			discrepancyCode = DiscrepencyCode.BCOM; // = shipment partial - considered complete, no backorder;
		}
		return discrepancyCode;
	}

	private void mapPackaging(EDIExpDesadvType xmlDesadv, DecimalFormat decimalFormat, ObjectFactory objectFactory, HEADERXlief header, String documentId)
	{
		final PACKINXlief packaging = objectFactory.createPACKINXlief();
		packaging.setDOCUMENTID(documentId);
		final int orderLineCount = xmlDesadv.getEDIExpDesadvLine().size();
		packaging.setPACKAGINGTOTAL(formatNumber(orderLineCount, decimalFormat));
		//TODO check if we have additional information to set for packaging

		for (EDIExpDesadvLineType ediExpDesadvLineType : xmlDesadv.getEDIExpDesadvLine())
		{
			final PPACK1 packDetail = objectFactory.createPPACK1();
			packDetail.setDOCUMENTID(documentId);
			//TODO comment why
			packDetail.setPACKAGINGDETAIL("1");
			//TODO property ISO1
			//packDetail.setPACKAGINGCODE();
			//TODO property OUTE
			//packDetail.setPACKAGINGLEVEL();
			packDetail.setIDENTIFICATIONQUAL(PackIdentificationQual.SSCC.name());
			final String sscc18Value = Util.removePrecedingZeros(ediExpDesadvLineType.getIPASSCC18());
			packDetail.setIDENTIFICATIONCODE(sscc18Value);

			mapDetail(decimalFormat, objectFactory, documentId, ediExpDesadvLineType, packDetail);
			packaging.getPPACK1().add(packDetail);
		}
		header.setPACKIN(packaging);
	}

	private void mapDetail(DecimalFormat decimalFormat, ObjectFactory objectFactory, String documentId, EDIExpDesadvLineType ediExpDesadvLineType, PPACK1 packDetail)
	{
		final DETAILXlief detail = objectFactory.createDETAILXlief();
		final String lineNumber = formatNumber(ediExpDesadvLineType.getLine(), decimalFormat);
		detail.setDOCUMENTID(documentId);
		detail.setLINENUMBER(lineNumber);

		if (StringUtils.isNotEmpty(ediExpDesadvLineType.getUPC()))
		{
			final DPRIN1 prodInfo = objectFactory.createDPRIN1();
			prodInfo.setDOCUMENTID(documentId);
			prodInfo.setPRODUCTQUAL(ProductQual.GTIN.name());
			prodInfo.setLINENUMBER(lineNumber);
			prodInfo.setPRODUCTID(ediExpDesadvLineType.getUPC());
			detail.getDPRIN1().add(prodInfo);
		}

		if (StringUtils.isNotEmpty(ediExpDesadvLineType.getProductNo()))
		{
			final DPRIN1 prodInfo = objectFactory.createDPRIN1();
			prodInfo.setDOCUMENTID(documentId);
			prodInfo.setPRODUCTQUAL(ProductQual.BUYR.name());
			prodInfo.setLINENUMBER(lineNumber);
			prodInfo.setPRODUCTID(ediExpDesadvLineType.getProductNo());
			detail.getDPRIN1().add(prodInfo);
		}

		if (StringUtils.isNotEmpty(ediExpDesadvLineType.getProductDescription()))
		{
			final DPRDE1 prodDescr = objectFactory.createDPRDE1();
			prodDescr.setDOCUMENTID(documentId);
			prodDescr.setLINENUMBER(lineNumber);
			prodDescr.setPRODUCTDESCQUAL(ProductDescQual.PROD.name());
			prodDescr.setPRODUCTDESCTEXT(ediExpDesadvLineType.getProductDescription());
			detail.getDPRDE1().add(prodDescr);
		}

		final DQUAN1 quantity = objectFactory.createDQUAN1();
		quantity.setDOCUMENTID(documentId);
		quantity.setLINENUMBER(lineNumber);
		quantity.setQUANTITYQUAL(QuantityQual.DELV.name());
		BigDecimal qtyDelivered = ediExpDesadvLineType.getQtyDeliveredInUOM();
		if (qtyDelivered == null)
		{
			qtyDelivered = BigDecimal.ZERO;
		}
		quantity.setQUANTITY(formatNumber(qtyDelivered, decimalFormat));
		// TODO map UOM from c_uom
		quantity.setMEASUREMENTUNIT(ediExpDesadvLineType.getCUOMID().getX12DE355());
		detail.getDQUAN1().add(quantity);

		final DREFE1 detailRef = objectFactory.createDREFE1();
		detailRef.setDOCUMENTID(documentId);
		detailRef.setLINENUMBER(lineNumber);
		detailRef.setREFERENCEQUAL(ReferenceQual.LIRN.name());
		//line number for now - but not sure
		detailRef.setREFERENCELINE(lineNumber);
		detail.getDREFE1().add(detailRef);

		BigDecimal quantityDiff = ediExpDesadvLineType.getQtyEntered().subtract(qtyDelivered);
		if (quantityDiff.signum() != 0)
		{
			final DQVAR1 varianceQuantity = objectFactory.createDQVAR1();
			varianceQuantity.setDOCUMENTID(documentId);
			varianceQuantity.setLINENUMBER(lineNumber);
			varianceQuantity.setQUANTITY(formatNumber(quantityDiff, decimalFormat));
			//TODO set OVSH  if negative diff
			varianceQuantity.setDISCREPANCYCODE(getDiscrepancyCode(ediExpDesadvLineType).name());
			detail.setDQVAR1(varianceQuantity);
		}
		packDetail.getDETAIL().add(detail);
	}

	private void mapAddresses(EDIExpDesadvType xmlDesadv, ObjectFactory objectFactory, HEADERXlief header, String documentId)
	{
		final HADRE1 buyerAddress = objectFactory.createHADRE1();
		buyerAddress.setDOCUMENTID(documentId);
		buyerAddress.setADDRESSQUAL(AddressQual.BUYR.name());
		buyerAddress.setPARTYIDGLN(xmlDesadv.getBillLocationID().getGLN());
		header.getHADRE1().add(buyerAddress);

		EDIExpCBPartnerLocationType deliveryLocation = xmlDesadv.getHandOverLocationID();
		if (deliveryLocation == null)
		{
			deliveryLocation = xmlDesadv.getCBPartnerLocationID();
		}

		final HADRE1 deliveryAddress = objectFactory.createHADRE1();
		deliveryAddress.setDOCUMENTID(documentId);
		deliveryAddress.setADDRESSQUAL(AddressQual.DELV.name());
		deliveryAddress.setPARTYIDGLN(deliveryLocation.getGLN());
		header.getHADRE1().add(deliveryAddress);

		final HADRE1 supplierAddress = objectFactory.createHADRE1();
		supplierAddress.setDOCUMENTID(documentId);
		supplierAddress.setADDRESSQUAL(AddressQual.SUPL.name());
		//TODO use property
		//supplierAddress.setPARTYIDGLN();
		//TODO check where to take adiitional partner from
		final HRFAD1 hrfad = objectFactory.createHRFAD1();
		hrfad.setDOCUMENTID(documentId);
		hrfad.setADDRESSQUAL(AddressQual.SUPL.name());
		hrfad.setREFERENCEQUAL(ReferenceQual.APAI.name());
		//	hrfad.setREFERENCE(xmlDesadv.getCBPartnerID().getValue());
		//TODO use property
		//	supplierAddress.setHRFAD1(hrfad);

		//TODO check if we have a contact to map
		//	supplierAddress.setHCTAD1();
		header.getHADRE1().add(supplierAddress);

		if (xmlDesadv.getCBPartnerLocationID() != null)
		{
			HADRE1 ucAddress = objectFactory.createHADRE1();
			ucAddress.setDOCUMENTID(documentId);
			ucAddress.setADDRESSQUAL(AddressQual.ULCO.name());
			ucAddress.setPARTYIDGLN(xmlDesadv.getCBPartnerLocationID().getGLN());
			header.getHADRE1().add(ucAddress);
		}

		//TODO check if we have transport details to map on addresses - HTRSD1
	}

	private void mapReferences(EDIExpDesadvType xmlDesadv, ObjectFactory objectFactory, HEADERXlief header, String documentId)
	{
		final HREFE1 orderReference = objectFactory.createHREFE1();
		orderReference.setDOCUMENTID(documentId);
		orderReference.setREFERENCEQUAL(ReferenceQual.ORBU.name());
		String reference;
		if (StringUtils.isNotEmpty(xmlDesadv.getPOReference()))
		{
			reference = xmlDesadv.getPOReference();
		}
		else
		{
			reference = xmlDesadv.getDocumentNo();
		}
		orderReference.setREFERENCE(reference);
		//TODO convert and set
		//orderReference.setREFERENCEDATE1(xmlDesadv.getDateOrdered());
		// TODO map the same for ORIG as for ORBU
		header.getHREFE1().add(orderReference);
	}

	private void mapDates(EDIExpDesadvType xmlDesadv, String dateFormat, ObjectFactory objectFactory, HEADERXlief header, String documentId)
	{
		final HDATE1 messageDate = objectFactory.createHDATE1();
		messageDate.setDOCUMENTID(documentId);
		messageDate.setDATEQUAL(DateQual.CREA.name());
		messageDate.setDATEFROM(toFormattedStringDate(SystemTime.asDate(), dateFormat));
		final HDATE1 deliveryDate = objectFactory.createHDATE1();
		deliveryDate.setDOCUMENTID(documentId);
		deliveryDate.setDATEQUAL(DateQual.DELV.name());
		deliveryDate.setDATEFROM(toFormattedStringDate(toDate(xmlDesadv.getMovementDate()), dateFormat));

		header.getHDATE1().add(messageDate);
		header.getHDATE1().add(deliveryDate);
	}
}
