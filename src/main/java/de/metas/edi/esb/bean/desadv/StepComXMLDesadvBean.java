/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.edi.esb.bean.desadv;

import static de.metas.edi.esb.commons.Util.formatNumber;
import static de.metas.edi.esb.commons.Util.toDate;
import static de.metas.edi.esb.commons.Util.toFormattedStringDate;
import static de.metas.edi.esb.commons.Util.trimAndTruncate;
import static de.metas.edi.esb.commons.ValidationHelper.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Comparator;

import org.apache.camel.Exchange;
import org.apache.commons.lang.StringUtils;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.StepComDesadvSettings;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpCBPartnerLocationType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvLineType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.stepcom.desadv.DETAILXlief;
import de.metas.edi.esb.jaxb.stepcom.desadv.DPRDE1;
import de.metas.edi.esb.jaxb.stepcom.desadv.DPRIN1;
import de.metas.edi.esb.jaxb.stepcom.desadv.DQUAN1;
import de.metas.edi.esb.jaxb.stepcom.desadv.DQVAR1;
import de.metas.edi.esb.jaxb.stepcom.desadv.DREFE1;
import de.metas.edi.esb.jaxb.stepcom.desadv.Document;
import de.metas.edi.esb.jaxb.stepcom.desadv.HADRE1;
import de.metas.edi.esb.jaxb.stepcom.desadv.HDATE1;
import de.metas.edi.esb.jaxb.stepcom.desadv.HEADERXlief;
import de.metas.edi.esb.jaxb.stepcom.desadv.HREFE1;
import de.metas.edi.esb.jaxb.stepcom.desadv.ObjectFactory;
import de.metas.edi.esb.jaxb.stepcom.desadv.PACKINXlief;
import de.metas.edi.esb.jaxb.stepcom.desadv.PPACK1;
import de.metas.edi.esb.jaxb.stepcom.desadv.TRAILR;
import de.metas.edi.esb.jaxb.stepcom.desadv.Xlief4H;
import de.metas.edi.esb.pojo.common.MeasurementUnit;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.AddressQual;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.ControlQual;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.DateQual;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.DiscrepencyCode;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.DocumentFunction;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.DocumentType;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.PackIdentificationQual;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.PackagingLevel;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.ProductDescQual;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.ProductDescType;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.ProductQual;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.QuantityQual;
import de.metas.edi.esb.pojo.desadv.stepcom.qualifier.ReferenceQual;
import de.metas.edi.esb.route.AbstractEDIRoute;
import de.metas.edi.esb.route.exports.StepComXMLDesadvRoute;
import lombok.NonNull;

public class StepComXMLDesadvBean
{
	public static final String METHOD_createXMLEDIData = "createXMLEDIData";

	private static final String DEFAULT_PACK_DETAIL = "1";
	private static final ObjectFactory DESADV_objectFactory = new ObjectFactory();

	public final void createXMLEDIData(final Exchange exchange)
	{
		final StepComDesadvValidation validation = new StepComDesadvValidation();

		// validate mandatory exchange properties
		final EDIExpDesadvType xmlDesadv = validation.validateExchange(exchange); // throw exceptions if mandatory fields are missing
		final Document desadvDocument = createDesadvDocumentFromXMLBean(xmlDesadv, exchange);

		exchange
				.getIn()
				.setBody(DESADV_objectFactory.createDocument(desadvDocument));
	}

	private Document createDesadvDocumentFromXMLBean(@NonNull final EDIExpDesadvType xmlDesadv, @NonNull final Exchange exchange)
	{
		final DecimalFormat decimalFormat = exchange.getProperty(Constants.DECIMAL_FORMAT, DecimalFormat.class);
		final String dateFormat = (String)exchange.getProperty(AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);

		final String ownerId = exchange.getProperty(StepComXMLDesadvRoute.EDI_XML_OWNER_ID, String.class);
		final String applicationRef = exchange.getProperty(StepComXMLDesadvRoute.EDI_XML_APPLICATION_REF, String.class);
		final String supplierGln = exchange.getProperty(StepComXMLDesadvRoute.EDI_XML_SUPPLIER_GLN, String.class);

		xmlDesadv.getEDIExpDesadvLine().sort(Comparator.comparing(EDIExpDesadvLineType::getLine));
		final Document document = DESADV_objectFactory.createDocument();
		final Xlief4H xlief4H = DESADV_objectFactory.createXlief4H();

		final HEADERXlief header = DESADV_objectFactory.createHEADERXlief();
		final String documentId = xmlDesadv.getDocumentNo();

		// TODO instead of adding all the properties above to the exchange, add them to this settings instance
		final StepComDesadvSettings receiverSettings = StepComDesadvSettings.forReceiverGLN(exchange.getContext(), xmlDesadv.getCBPartnerID().getEdiRecipientGLN());

		header.setDOCUMENTID(documentId);
		header.setPARTNERID(receiverSettings.getPartnerId());
		header.setOWNERID(ownerId);
		header.setTESTINDICATOR(receiverSettings.getTestIndicator());
		header.setMESSAGEREF(formatNumber(xmlDesadv.getSequenceNoAttr(), decimalFormat));
		header.setAPPLICATIONREF(applicationRef);
		header.setDOCUMENTTYP(DocumentType.DADV.toString());
		header.setDOCUMENTFUNCTION(DocumentFunction.ORIG.toString());

		mapDates(xmlDesadv, dateFormat, header);

		mapHeaderReferences(xmlDesadv, dateFormat, header);

		mapAddresses(xmlDesadv, header, supplierGln);

		mapPackaging(xmlDesadv, header, receiverSettings, decimalFormat);

		final TRAILR trailr = DESADV_objectFactory.createTRAILR();
		trailr.setDOCUMENTID(documentId);
		trailr.setCONTROLQUAL(ControlQual.LINE.toString());
		trailr.setCONTROLVALUE(formatNumber(xmlDesadv.getEDIExpDesadvLine().size(), decimalFormat));

		xlief4H.setHEADER(header);
		xlief4H.setTRAILR(trailr);
		document.getXlief4H().add(xlief4H);
		return document;
	}

	private DiscrepencyCode getDiscrepancyCode(final String isSubsequentDeliveryPlanned, final BigDecimal diff)
	{
		DiscrepencyCode discrepancyCode;
		if (diff.signum() == -1)
		{
			discrepancyCode = DiscrepencyCode.OVSH; // = Over-shipped
			return discrepancyCode;
		}
		if (Boolean.parseBoolean(isSubsequentDeliveryPlanned))
		{
			discrepancyCode = DiscrepencyCode.BFOL; // = Shipment partial - back order to follow
		}
		else
		{
			discrepancyCode = DiscrepencyCode.BCOM; // = shipment partial - considered complete, no backorder;
		}
		return discrepancyCode;
	}

	private void mapPackaging(
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final HEADERXlief header,
			@NonNull final StepComDesadvSettings receiverSettings,
			@NonNull final DecimalFormat decimalFormat)
	{
		final PACKINXlief packaging = DESADV_objectFactory.createPACKINXlief();
		packaging.setDOCUMENTID(header.getDOCUMENTID());
		final int orderLineCount = xmlDesadv.getEDIExpDesadvLine().size();
		packaging.setPACKAGINGTOTAL(formatNumber(orderLineCount, decimalFormat));

		String packagingCodeLUText = null;

		for (final EDIExpDesadvLineType ediExpDesadvLineType : xmlDesadv.getEDIExpDesadvLine())
		{
			final PPACK1 packDetail = DESADV_objectFactory.createPPACK1();
			packDetail.setDOCUMENTID(header.getDOCUMENTID());
			// usually one, as per spec
			packDetail.setPACKAGINGDETAIL(DEFAULT_PACK_DETAIL);

			if (receiverSettings.isDesadvLinePackagingCodeTURequired())
			{
				packDetail.setPACKAGINGLEVEL(PackagingLevel.INNE.toString());
				final String packagingCodeTU = validateString(
						ediExpDesadvLineType.getMHUPackagingCodeTUText(),
						"@FillMandatory@ @EDI_DesadvLine_ID@=" + ediExpDesadvLineType.getLine() + " @M_HU_PackagingCode_TU_ID@");
				packDetail.setPACKAGINGCODE(packagingCodeTU);
			}

			packDetail.setIDENTIFICATIONQUAL(PackIdentificationQual.SSCC.toString());
			final String sscc18Value = Util.removePrecedingZeros(ediExpDesadvLineType.getIPASSCC18());
			packDetail.setIDENTIFICATIONCODE(sscc18Value);

			mapDetail(packDetail, xmlDesadv, ediExpDesadvLineType, receiverSettings, decimalFormat);
			packaging.getPPACK1().add(packDetail);

			if (packagingCodeLUText == null)
			{ // TODO only for now we assume they are all the same
				packagingCodeLUText = ediExpDesadvLineType.getMHUPackagingCodeLUText();
			}
		}

		packaging.setPACKAGINGCODE(packagingCodeLUText);
		packaging.setPACKAGINGLEVEL(PackagingLevel.OUTE.toString());

		header.setPACKIN(packaging);
	}

	private void mapDetail(
			@NonNull final PPACK1 packDetail,
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final EDIExpDesadvLineType ediExpDesadvLineType,
			@NonNull final StepComDesadvSettings receiverSettings,
			final DecimalFormat decimalFormat)
	{
		final String documentId = packDetail.getDOCUMENTID();
		final DETAILXlief detail = DESADV_objectFactory.createDETAILXlief();
		final String lineNumber = formatNumber(ediExpDesadvLineType.getLine(), decimalFormat);
		detail.setDOCUMENTID(documentId);
		detail.setLINENUMBER(lineNumber);

		if (StringUtils.isNotEmpty(ediExpDesadvLineType.getEANCU()))
		{
			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.EANC.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(ediExpDesadvLineType.getEANCU());
			detail.getDPRIN1().add(eancProdInfo);
		}
		if (StringUtils.isNotEmpty(ediExpDesadvLineType.getEANTU()))
		{
			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.EANT.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(ediExpDesadvLineType.getEANTU());
			detail.getDPRIN1().add(eancProdInfo);
		}
		if (StringUtils.isNotEmpty(ediExpDesadvLineType.getGTIN()))
		{
			final DPRIN1 gtinProdInfo = DESADV_objectFactory.createDPRIN1();
			gtinProdInfo.setDOCUMENTID(documentId);
			gtinProdInfo.setPRODUCTQUAL(ProductQual.GTIN.toString());
			gtinProdInfo.setLINENUMBER(lineNumber);
			gtinProdInfo.setPRODUCTID(ediExpDesadvLineType.getGTIN());
			detail.getDPRIN1().add(gtinProdInfo);
		}

		if (StringUtils.isNotEmpty(ediExpDesadvLineType.getUPC()))
		{
			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.UPCC.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(ediExpDesadvLineType.getUPC());
			detail.getDPRIN1().add(eancProdInfo);
		}
		if (StringUtils.isNotEmpty(ediExpDesadvLineType.getUPCTU()))
		{
			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.UPCT.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(ediExpDesadvLineType.getUPCTU());
			detail.getDPRIN1().add(eancProdInfo);
		}

		if (StringUtils.isNotEmpty(ediExpDesadvLineType.getProductNo()))
		{
			final DPRIN1 prodInfo = DESADV_objectFactory.createDPRIN1();
			prodInfo.setDOCUMENTID(documentId);
			prodInfo.setPRODUCTQUAL(ProductQual.BUYR.toString());
			prodInfo.setLINENUMBER(lineNumber);
			prodInfo.setPRODUCTID(ediExpDesadvLineType.getProductNo());
			detail.getDPRIN1().add(prodInfo);
		}
		if (StringUtils.isNotEmpty(ediExpDesadvLineType.getProductDescription()))
		{
			final DPRDE1 prodDescr = DESADV_objectFactory.createDPRDE1();
			prodDescr.setDOCUMENTID(documentId);
			prodDescr.setLINENUMBER(lineNumber);
			prodDescr.setPRODUCTDESCQUAL(ProductDescQual.PROD.toString());
			prodDescr.setPRODUCTDESCTYPE(ProductDescType.CU.toString());
			prodDescr.setPRODUCTDESCTEXT(trimAndTruncate(ediExpDesadvLineType.getProductDescription(), 512));
			detail.getDPRDE1().add(prodDescr);
		}
		if (receiverSettings.isDesadvLinePRICRequired())
		{
			final BigDecimal priceActual = validateObject(ediExpDesadvLineType.getPriceActual(),
					"@FillMandatory@ @EDI_DesadvLine_ID@=" + ediExpDesadvLineType.getLine() + " @PriceActual@");

			final DPRDE1 prodDescr = DESADV_objectFactory.createDPRDE1();
			prodDescr.setDOCUMENTID(documentId);
			prodDescr.setLINENUMBER(lineNumber);
			prodDescr.setPRODUCTDESCQUAL(ProductDescQual.PRIC.toString());
			prodDescr.setPRODUCTDESCTYPE(ProductDescType.CU.toString());
			prodDescr.setPRODUCTDESCTEXT(formatNumber(priceActual, decimalFormat));
			detail.getDPRDE1().add(prodDescr);
		}

		final DQUAN1 cuQuantity = createQuantityDetail(documentId, lineNumber, QuantityQual.DELV);
		BigDecimal qtyDelivered = ediExpDesadvLineType.getQtyDeliveredInUOM();
		if (qtyDelivered == null)
		{
			qtyDelivered = BigDecimal.ZERO;
		}
		cuQuantity.setQUANTITY(formatNumber(qtyDelivered, decimalFormat));
		final MeasurementUnit measurementUnit = MeasurementUnit.fromMetasfreshUOM(ediExpDesadvLineType.getCUOMID().getX12DE355());
		if (measurementUnit != null)
		{
			cuQuantity.setMEASUREMENTUNIT(measurementUnit.toString());
		}
		detail.getDQUAN1().add(cuQuantity);

		final BigDecimal qtyItemCapacity = ediExpDesadvLineType.getQtyItemCapacity();
		if (qtyItemCapacity != null)
		{
			final DQUAN1 cuTuQuantity = createQuantityDetail(documentId, lineNumber, QuantityQual.CUTU);
			cuTuQuantity.setQUANTITY(formatNumber(qtyItemCapacity, decimalFormat));
			detail.getDQUAN1().add(cuTuQuantity);
		}

		if (receiverSettings.isDesadvLineORBU())
		{
			final DREFE1 orderHeaderRef = DESADV_objectFactory.createDREFE1();
			orderHeaderRef.setDOCUMENTID(documentId);
			orderHeaderRef.setLINENUMBER(lineNumber);
			orderHeaderRef.setREFERENCEQUAL(ReferenceQual.ORBU.toString());
			orderHeaderRef.setREFERENCE(xmlDesadv.getPOReference());
			if (receiverSettings.isDesadvLineORBUOrderLineReference())
			{
				orderHeaderRef.setREFERENCELINE(lineNumber);
			}
			detail.getDREFE1().add(orderHeaderRef);
		}

		if (receiverSettings.isDesadvLineLINR())
		{
			final DREFE1 orderLineRef = DESADV_objectFactory.createDREFE1();
			orderLineRef.setDOCUMENTID(documentId);
			orderLineRef.setLINENUMBER(lineNumber);
			orderLineRef.setREFERENCEQUAL(ReferenceQual.LIRN.toString());
			orderLineRef.setREFERENCELINE(lineNumber);
			detail.getDREFE1().add(orderLineRef);
		}

		final BigDecimal quantityDiff = ediExpDesadvLineType.getQtyEntered().subtract(qtyDelivered);
		if (quantityDiff.signum() != 0)
		{
			final DQVAR1 varianceQuantity = DESADV_objectFactory.createDQVAR1();
			varianceQuantity.setDOCUMENTID(documentId);
			varianceQuantity.setLINENUMBER(lineNumber);
			varianceQuantity.setQUANTITY(formatNumber(quantityDiff, decimalFormat));
			varianceQuantity.setDISCREPANCYCODE(getDiscrepancyCode(ediExpDesadvLineType.getIsSubsequentDeliveryPlanned(), quantityDiff).toString());
			detail.setDQVAR1(varianceQuantity);
		}
		packDetail.getDETAIL().add(detail);
	}

	private DQUAN1 createQuantityDetail(
			@NonNull final String documentId,
			@NonNull final String lineNumber,
			@NonNull final QuantityQual qualifier)
	{
		final DQUAN1 cuQuantity = DESADV_objectFactory.createDQUAN1();
		cuQuantity.setDOCUMENTID(documentId);
		cuQuantity.setLINENUMBER(lineNumber);
		cuQuantity.setQUANTITYQUAL(qualifier.toString());
		return cuQuantity;
	}

	private void mapAddresses(
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final HEADERXlief header,
			@NonNull final String supplierGln)
	{
		final HADRE1 buyerAddress = DESADV_objectFactory.createHADRE1();
		buyerAddress.setDOCUMENTID(header.getDOCUMENTID());
		buyerAddress.setADDRESSQUAL(AddressQual.BUYR.toString());
		buyerAddress.setPARTYIDGLN(xmlDesadv.getBillLocationID().getGLN());
		header.getHADRE1().add(buyerAddress);

		EDIExpCBPartnerLocationType deliveryLocation = xmlDesadv.getHandOverLocationID();
		if (deliveryLocation == null)
		{
			deliveryLocation = xmlDesadv.getCBPartnerLocationID();
		}

		final HADRE1 deliveryAddress = DESADV_objectFactory.createHADRE1();
		deliveryAddress.setDOCUMENTID(header.getDOCUMENTID());
		deliveryAddress.setADDRESSQUAL(AddressQual.DELV.toString());
		deliveryAddress.setPARTYIDGLN(deliveryLocation.getGLN());
		header.getHADRE1().add(deliveryAddress);

		final HADRE1 supplierAddress = DESADV_objectFactory.createHADRE1();
		supplierAddress.setDOCUMENTID(header.getDOCUMENTID());
		supplierAddress.setADDRESSQUAL(AddressQual.SUPL.toString());
		supplierAddress.setPARTYIDGLN(supplierGln);

		// address HCTAD1 contact not mapped for now

		header.getHADRE1().add(supplierAddress);

		if (xmlDesadv.getCBPartnerLocationID() != null)
		{
			final HADRE1 ucAddress = DESADV_objectFactory.createHADRE1();
			ucAddress.setDOCUMENTID(header.getDOCUMENTID());
			ucAddress.setADDRESSQUAL(AddressQual.ULCO.toString());
			ucAddress.setPARTYIDGLN(xmlDesadv.getCBPartnerLocationID().getGLN());
			header.getHADRE1().add(ucAddress);
		}

		// transport details HTRSD1 not mapped for now
	}

	private void mapHeaderReferences(
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final String dateFormat,
			@NonNull final HEADERXlief header)
	{
		final String buyerReference = xmlDesadv.getPOReference();

		final HREFE1 orderReference = DESADV_objectFactory.createHREFE1();
		orderReference.setDOCUMENTID(header.getDOCUMENTID());
		orderReference.setREFERENCEQUAL(ReferenceQual.ORBU.toString());
		orderReference.setREFERENCE(buyerReference);
		orderReference.setREFERENCEDATE1(toFormattedStringDate(toDate(xmlDesadv.getDateOrdered()), dateFormat));
		header.getHREFE1().add(orderReference);

		// ORIG same as ORBU for now
		final HREFE1 origReference = DESADV_objectFactory.createHREFE1();
		origReference.setDOCUMENTID(header.getDOCUMENTID());
		origReference.setREFERENCEQUAL(ReferenceQual.ORIG.toString());
		origReference.setREFERENCE(buyerReference);
		origReference.setREFERENCEDATE1(toFormattedStringDate(toDate(xmlDesadv.getDateOrdered()), dateFormat));
		header.getHREFE1().add(origReference);
	}

	private void mapDates(final EDIExpDesadvType xmlDesadv, final String dateFormat, final HEADERXlief header)
	{
		final HDATE1 messageDate = DESADV_objectFactory.createHDATE1();
		messageDate.setDOCUMENTID(header.getDOCUMENTID());
		messageDate.setDATEQUAL(DateQual.CREA.toString());
		messageDate.setDATEFROM(toFormattedStringDate(SystemTime.asDate(), dateFormat));
		final HDATE1 deliveryDate = DESADV_objectFactory.createHDATE1();
		deliveryDate.setDOCUMENTID(header.getDOCUMENTID());
		deliveryDate.setDATEQUAL(DateQual.DELV.toString());
		deliveryDate.setDATEFROM(toFormattedStringDate(toDate(xmlDesadv.getMovementDate()), dateFormat));

		header.getHDATE1().add(messageDate);
		header.getHDATE1().add(deliveryDate);
	}
}
