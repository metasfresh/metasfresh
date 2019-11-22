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
import static de.metas.edi.esb.commons.ValidationHelper.validateObject;
import static de.metas.edi.esb.commons.ValidationHelper.validateString;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Comparator;

import org.apache.camel.Exchange;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.ValidationHelper;
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
import lombok.Value;

public class StepComXMLDesadvBean
{
	public static final String METHOD_createXMLEDIData = "createXMLEDIData";

	private static final String DEFAULT_PACK_DETAIL = "1";
	private static final ObjectFactory DESADV_objectFactory = new ObjectFactory();

	public final void createXMLEDIData(@NonNull final Exchange exchange)
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
		final String supplierGln = exchange.getProperty(StepComXMLDesadvRoute.EDI_XML_SUPPLIER_GLN, String.class);

		xmlDesadv.getEDIExpDesadvLine().sort(Comparator.comparing(EDIExpDesadvLineType::getLine));
		final Document document = DESADV_objectFactory.createDocument();
		final Xlief4H xlief4H = DESADV_objectFactory.createXlief4H();

		final HEADERXlief header = DESADV_objectFactory.createHEADERXlief();
		final String documentId = xmlDesadv.getDocumentNo();

		// TODO instead of adding all the properties above to the exchange, add them to this settings instance
		final StepComDesadvSettings settings = StepComDesadvSettings.forReceiverGLN(exchange.getContext(), xmlDesadv.getCBPartnerID().getEdiRecipientGLN());

		header.setDOCUMENTID(documentId);
		header.setPARTNERID(settings.getPartnerId());
		header.setOWNERID(ownerId);
		header.setTESTINDICATOR(settings.getTestIndicator());
		header.setMESSAGEREF(formatNumber(xmlDesadv.getSequenceNoAttr(), decimalFormat));
		if (!Util.isEmpty(settings.getApplicationRef()))
		{
			header.setAPPLICATIONREF(settings.getApplicationRef());
		}
		header.setDOCUMENTTYP(DocumentType.DADV.toString());
		header.setDOCUMENTFUNCTION(DocumentFunction.ORIG.toString());

		mapDates(xmlDesadv, dateFormat, header);

		mapHeaderReferences(xmlDesadv, header, settings, dateFormat);

		mapAddresses(xmlDesadv, header, supplierGln);

		mapPackaging(xmlDesadv, header, settings, decimalFormat);

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
			@NonNull final StepComDesadvSettings settings,
			@NonNull final DecimalFormat decimalFormat)
	{
		final PACKINXlief packIn = DESADV_objectFactory.createPACKINXlief();
		packIn.setDOCUMENTID(header.getDOCUMENTID());

		String packagingCodeLUText = null;
		final boolean ssccRequired = settings.isDesadvLineSSCCRequired();
		final boolean packagingCodeTURequired = settings.isDesadvLinePackagingCodeTURequired();

		final ImmutableListMultimap<PACKINXliefKey, EDIExpDesadvLineType> //
		keyToPackages = Multimaps.index(
				xmlDesadv.getEDIExpDesadvLine(),
				line -> PACKINXliefKey.forLine(line, ssccRequired, packagingCodeTURequired));

		final int distinctKeySize = keyToPackages.keySet().size();
		final boolean ppack1Needed = ssccRequired || packagingCodeTURequired || distinctKeySize > 1;

		final int packagingTotalNumber = ppack1Needed ? distinctKeySize : 0;
		packIn.setPACKAGINGTOTAL(formatNumber(packagingTotalNumber, decimalFormat));

		for (final PACKINXliefKey key : keyToPackages.keySet())
		{
			for (final EDIExpDesadvLineType ediExpDesadvLineType : keyToPackages.get(key))
			{
				if (ppack1Needed)
				{
					boolean detailAdded = false;
					if (ssccRequired)
					{
						final PPACK1 ppack1 = createPPACK1(header);

						ppack1.setPACKAGINGCODE(ediExpDesadvLineType.getMHUPackagingCodeLUText());
						ppack1.setIDENTIFICATIONQUAL(PackIdentificationQual.SSCC.toString());
						ppack1.setIDENTIFICATIONCODE(Util.lpadZero(key.getSsccValue(), 18)/* if ssccRequired and we got here, then this is not null */);
						packIn.getPPACK1().add(ppack1);

						final DETAILXlief detailXlief = createDETAILXlief(packIn.getDOCUMENTID(), xmlDesadv, ediExpDesadvLineType, settings, decimalFormat);
						ppack1.getDETAIL().add(detailXlief);

						detailAdded = true;
					}
					if (packagingCodeTURequired)
					{
						final PPACK1 ppack1 = createPPACK1(header);
						ppack1.setPACKAGINGLEVEL(PackagingLevel.INNE.toString());

						ppack1.setPACKAGINGCODE(key.getPackagingCodeTUText()/* if packagingCodeTURequired, then this is set */);
						packIn.getPPACK1().add(ppack1);

						if (!detailAdded)
						{
							final DETAILXlief detailXlief = createDETAILXlief(packIn.getDOCUMENTID(), xmlDesadv, ediExpDesadvLineType, settings, decimalFormat);
							ppack1.getDETAIL().add(detailXlief);
						}
					}
				}
				else // we can add the detail directly to packIn
				{
					final DETAILXlief detail = createDETAILXlief(packIn.getDOCUMENTID(), xmlDesadv, ediExpDesadvLineType, settings, decimalFormat);
					packIn.getDETAIL().add(detail);
				}
			}
		}

		for (final EDIExpDesadvLineType ediExpDesadvLineType : xmlDesadv.getEDIExpDesadvLine())
		{

			boolean addDETAILXliefToPackInXlief = true;
			if (ppack1Needed)
			{
				if (ssccRequired)
				{
					final PPACK1 ppack1 = createPPACK1(header);

					// TODO: or the number of OLs we need need the number of different SSCC'ed LUs
					final int orderLineCount = xmlDesadv.getEDIExpDesadvLine().size();
					packIn.setPACKAGINGTOTAL(formatNumber(orderLineCount, decimalFormat));

					final String ssccValue = validateString(
							Util.removePrecedingZeros(ediExpDesadvLineType.getIPASSCC18()),
							"@FillMandatory@ SSCC in @EDI_DesadvLine_ID@ " + ediExpDesadvLineType.getLine());

					ppack1.setPACKAGINGCODE(ediExpDesadvLineType.getMHUPackagingCodeLUText());
					ppack1.setIDENTIFICATIONQUAL(PackIdentificationQual.SSCC.toString());
					ppack1.setIDENTIFICATIONCODE(ssccValue);

					final DETAILXlief detailXlief = createDETAILXlief(packIn.getDOCUMENTID(), xmlDesadv, ediExpDesadvLineType, settings, decimalFormat);
					ppack1.getDETAIL().add(detailXlief);
					addDETAILXliefToPackInXlief = false; // because we already added detailXlief to ppack1

					packIn.getPPACK1().add(ppack1);
				}
				else
				{
					packIn.setPACKAGINGTOTAL(formatNumber(0, decimalFormat));
				}

				if (packagingCodeTURequired)
				{
					final PPACK1 ppack1 = createPPACK1(header);
					ppack1.setPACKAGINGLEVEL(PackagingLevel.INNE.toString());

					final String packagingCodeTU = validateString(
							ediExpDesadvLineType.getMHUPackagingCodeTUText(),
							"@FillMandatory@ @EDI_DesadvLine_ID@=" + ediExpDesadvLineType.getLine() + " @M_HU_PackagingCode_TU_ID@");
					ppack1.setPACKAGINGCODE(packagingCodeTU);
					packIn.getPPACK1().add(ppack1);
				}

			}

			if (addDETAILXliefToPackInXlief)
			{
				final DETAILXlief detailXlief = createDETAILXlief(packIn.getDOCUMENTID(), xmlDesadv, ediExpDesadvLineType, settings, decimalFormat);
				packIn.getDETAIL().add(detailXlief);
			}

			if (packagingCodeLUText == null)
			{ // TODO only for now we assume they are all the same
				packagingCodeLUText = ediExpDesadvLineType.getMHUPackagingCodeLUText();
			}
		}

		packIn.setPACKAGINGCODE(packagingCodeLUText);
		packIn.setPACKAGINGLEVEL(PackagingLevel.OUTE.toString());

		header.setPACKIN(packIn);
	}

	@Value
	private static class PACKINXliefKey
	{
		private static PACKINXliefKey forLine(
				@NonNull final EDIExpDesadvLineType line,
				final boolean ssccRequired,
				final boolean packagingCodeTURequired)
		{
			final String ssccValue = ssccRequired
					? validateString(
							Util.removePrecedingZeros(line.getIPASSCC18()),
							"@FillMandatory@ SSCC in @EDI_DesadvLine_ID@ " + line.getLine())
					: "";

			final String packagingCodeTU = packagingCodeTURequired
					? validateString(
							line.getMHUPackagingCodeTUText(),
							"@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @M_HU_PackagingCode_TU_ID@")
					: "";

			return new PACKINXliefKey(
					ssccValue,
					packagingCodeTU,
					line.getMHUPackagingCodeLUText());
		}

		@Nullable
		String ssccValue;

		@Nullable
		String packagingCodeTUText;

		@Nullable
		String packagingCodeLUText;
	}

	private PPACK1 createPPACK1(final HEADERXlief header)
	{
		final PPACK1 ppack1 = DESADV_objectFactory.createPPACK1();
		ppack1.setDOCUMENTID(header.getDOCUMENTID());
		ppack1.setPACKAGINGDETAIL(DEFAULT_PACK_DETAIL); // one, as per spec
		return ppack1;
	}

	private DETAILXlief createDETAILXlief(
			final String documentId,
			final EDIExpDesadvType xmlDesadv,
			final EDIExpDesadvLineType ediExpDesadvLineType,
			final StepComDesadvSettings settings,
			final DecimalFormat decimalFormat)
	{
		final DETAILXlief detail = DESADV_objectFactory.createDETAILXlief();
		detail.setDOCUMENTID(documentId);

		final String lineNumber = formatNumber(ediExpDesadvLineType.getLine(), decimalFormat);
		detail.setLINENUMBER(lineNumber);

		if (settings.isDesadvLineEANCRequired())
		{
			final String eanc = ValidationHelper.validateString(ediExpDesadvLineType.getEANCU(),
					"@FillMandatory@ @EDI_DesadvLine_ID@=" + ediExpDesadvLineType.getLine() + " @EANCU@");

			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.EANC.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(eanc);
			detail.getDPRIN1().add(eancProdInfo);
		}
		if (settings.isDesadvLineEANTRequired())
		{
			final String eant = ValidationHelper.validateString(ediExpDesadvLineType.getEANTU(),
					"@FillMandatory@ @EDI_DesadvLine_ID@=" + ediExpDesadvLineType.getLine() + " @EANTU@");

			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.EANT.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(eant);
			detail.getDPRIN1().add(eancProdInfo);
		}
		if (settings.isDesadvLineGTINRequired())
		{
			final String gtin = ValidationHelper.validateString(ediExpDesadvLineType.getGTIN(),
					"@FillMandatory@ @EDI_DesadvLine_ID@=" + ediExpDesadvLineType.getLine() + " @GTIN@");

			final DPRIN1 gtinProdInfo = DESADV_objectFactory.createDPRIN1();
			gtinProdInfo.setDOCUMENTID(documentId);
			gtinProdInfo.setPRODUCTQUAL(ProductQual.GTIN.toString());
			gtinProdInfo.setLINENUMBER(lineNumber);
			gtinProdInfo.setPRODUCTID(gtin);
			detail.getDPRIN1().add(gtinProdInfo);
		}
		if (settings.isDesadvLineUPCCRequired())
		{
			final String upcc = ValidationHelper.validateString(ediExpDesadvLineType.getUPC(),
					"@FillMandatory@ @EDI_DesadvLine_ID@=" + ediExpDesadvLineType.getLine() + " @UPC@");
			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.UPCC.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(upcc);
			detail.getDPRIN1().add(eancProdInfo);
		}
		if (settings.isDesadvLineUPCCRequired())
		{
			final String upct = ValidationHelper.validateString(ediExpDesadvLineType.getUPCTU(),
					"@FillMandatory@ @EDI_DesadvLine_ID@=" + ediExpDesadvLineType.getLine() + " @UPCTU@");

			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.UPCT.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(upct);
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
		if (settings.isDesadvLinePRICRequired())
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

		final boolean orbuOrderReference = settings.isDesadvLineORBUOrderReference();
		final boolean orbuLineReference = settings.isDesadvLineORBUOrderLineReference();
		if (orbuOrderReference || orbuLineReference)
		{
			final DREFE1 orderHeaderRef = DESADV_objectFactory.createDREFE1();
			orderHeaderRef.setDOCUMENTID(documentId);
			orderHeaderRef.setLINENUMBER(lineNumber);
			orderHeaderRef.setREFERENCEQUAL(ReferenceQual.ORBU.toString());

			if (orbuOrderReference)
			{
				orderHeaderRef.setREFERENCE(xmlDesadv.getPOReference());
			}
			if (orbuLineReference)
			{
				orderHeaderRef.setREFERENCELINE(lineNumber);
			}
			detail.getDREFE1().add(orderHeaderRef);
		}

		if (settings.isDesadvLineLIRN())
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
		return detail;
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
			@NonNull final HEADERXlief header,
			@NonNull final StepComDesadvSettings settings,
			@NonNull final String dateFormat)
	{
		final String buyerReference = xmlDesadv.getPOReference();

		final HREFE1 orderReference = DESADV_objectFactory.createHREFE1();
		orderReference.setDOCUMENTID(header.getDOCUMENTID());
		orderReference.setREFERENCEQUAL(ReferenceQual.ORBU.toString());
		orderReference.setREFERENCE(buyerReference);
		orderReference.setREFERENCEDATE1(toFormattedStringDate(toDate(xmlDesadv.getDateOrdered()), dateFormat));
		header.getHREFE1().add(orderReference);

		if (!Util.isEmpty(settings.getDesadvHeaderORIGReference()))
		{
			final HREFE1 origReference = DESADV_objectFactory.createHREFE1();
			origReference.setDOCUMENTID(header.getDOCUMENTID());
			origReference.setREFERENCEQUAL(ReferenceQual.ORIG.toString());
			origReference.setREFERENCE(settings.getDesadvHeaderORIGReference().trim());
			origReference.setREFERENCEDATE1(toFormattedStringDate(toDate(xmlDesadv.getDateOrdered()), dateFormat));
			header.getHREFE1().add(origReference);
		}
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
