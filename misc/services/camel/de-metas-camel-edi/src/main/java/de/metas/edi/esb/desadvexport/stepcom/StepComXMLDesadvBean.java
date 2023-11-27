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

package de.metas.edi.esb.desadvexport.stepcom;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.DesadvSettings;
import de.metas.edi.esb.commons.MeasurementUnit;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.ValidationHelper;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.desadvexport.LineAndPack;
import de.metas.edi.esb.desadvexport.helper.DesadvLines;
import de.metas.edi.esb.desadvexport.helper.DesadvParser;
import de.metas.edi.esb.desadvexport.helper.SinglePack;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.AddressQual;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.ControlQual;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.DateQual;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.DiscrepencyCode;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.DocumentFunction;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.DocumentType;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.IdentificationQual;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.PackIdentificationQual;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.PackagingLevel;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.ProductDescQual;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.ProductDescType;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.ProductQual;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.QuantityQual;
import de.metas.edi.esb.desadvexport.stepcom.qualifier.ReferenceQual;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpCBPartnerLocationType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpCUOMType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvLineType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvPackItemType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.stepcom.desadv.DETAILXlief;
import de.metas.edi.esb.jaxb.stepcom.desadv.DMARK1;
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
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static de.metas.edi.esb.commons.Util.formatNumber;
import static de.metas.edi.esb.commons.Util.toDate;
import static de.metas.edi.esb.commons.Util.toFormattedStringDate;
import static de.metas.edi.esb.commons.Util.trimAndTruncate;
import static de.metas.edi.esb.commons.ValidationHelper.validateObject;
import static de.metas.edi.esb.commons.ValidationHelper.validateString;
import static java.math.BigDecimal.ZERO;

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

		final DesadvSettings settings = DesadvSettings.forReceiverGLN(exchange.getContext(), xmlDesadv.getCBPartnerID().getEdiRecipientGLN());
		final Xlief4H xlief4H = createDesadvDocumentFromXMLBean(xmlDesadv, exchange, settings);

		final Document document = DESADV_objectFactory.createDocument();
		document.getXlief4H().add(xlief4H);
		exchange.getIn().setBody(DESADV_objectFactory.createDocument(document));

		final String fileName = settings.getFileNamePrefix() + "_" + xlief4H.getHEADER().getDOCUMENTID() + "_" + SystemTime.millis() + ".xml";
		exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
	}

	private Xlief4H createDesadvDocumentFromXMLBean(
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final Exchange exchange,
			@NonNull final DesadvSettings settings)
	{
		// TODO instead of adding all the properties above to the exchange, add them to this settings instance
		final DecimalFormat decimalFormat = exchange.getProperty(Constants.DECIMAL_FORMAT, DecimalFormat.class);
		final String dateFormat = (String)exchange.getProperty(AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);

		final String ownerId = exchange.getProperty(StepComXMLDesadvRoute.EDI_XML_OWNER_ID, String.class);
		final String supplierGln = exchange.getProperty(StepComXMLDesadvRoute.EDI_XML_SUPPLIER_GLN, String.class);

		final DesadvLines desadvLines = DesadvParser.getDesadvLinesEnforcingSinglePacks(xmlDesadv);

		final Xlief4H xlief4H = DESADV_objectFactory.createXlief4H();

		final HEADERXlief header = DESADV_objectFactory.createHEADERXlief();
		final String documentId = xmlDesadv.getDocumentNo();

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

		mapDates(xmlDesadv, header, dateFormat);

		mapHeaderReferences(xmlDesadv, header, settings, dateFormat);

		mapAddresses(xmlDesadv, header, supplierGln);

		mapPackaging(desadvLines, xmlDesadv, header, settings, decimalFormat, dateFormat);

		final TRAILR trailr = DESADV_objectFactory.createTRAILR();
		trailr.setDOCUMENTID(documentId);
		trailr.setCONTROLQUAL(ControlQual.LINE.toString());

		trailr.setCONTROLVALUE(formatNumber(desadvLines.getAllLines().size(), decimalFormat));

		xlief4H.setHEADER(header);
		xlief4H.setTRAILR(trailr);

		return xlief4H;
	}

	private void mapDates(
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final HEADERXlief header,
			@NonNull final String dateFormat)
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

	private void mapHeaderReferences(
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final HEADERXlief header,
			@NonNull final DesadvSettings settings,
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

	private void mapAddresses(
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final HEADERXlief header,
			@NonNull final String supplierGln)
	{
		final EDIExpCBPartnerLocationType buyrLocation = validateObject(xmlDesadv.getCBPartnerLocationID(),
																		"@FillMandatory@ @EDI_Desadv_ID@=" + xmlDesadv.getDocumentNo() + " @C_BPartner_Location_ID@");

		final HADRE1 buyerAddress = DESADV_objectFactory.createHADRE1();
		buyerAddress.setDOCUMENTID(header.getDOCUMENTID());
		buyerAddress.setADDRESSQUAL(AddressQual.BUYR.toString());
		buyerAddress.setPARTYIDGLN(buyrLocation.getGLN());
		header.getHADRE1().add(buyerAddress);

		final EDIExpCBPartnerLocationType delvLocation = xmlDesadv.getHandOverLocationID() != null ? xmlDesadv.getHandOverLocationID() : buyrLocation;

		final HADRE1 deliveryAddress = DESADV_objectFactory.createHADRE1();
		deliveryAddress.setDOCUMENTID(header.getDOCUMENTID());
		deliveryAddress.setADDRESSQUAL(AddressQual.DELV.toString());
		deliveryAddress.setPARTYIDGLN(delvLocation.getGLN());
		header.getHADRE1().add(deliveryAddress);

		final HADRE1 supplierAddress = DESADV_objectFactory.createHADRE1();
		supplierAddress.setDOCUMENTID(header.getDOCUMENTID());
		supplierAddress.setADDRESSQUAL(AddressQual.SUPL.toString());
		supplierAddress.setPARTYIDGLN(supplierGln);
		header.getHADRE1().add(supplierAddress);

		// TODO: add shipToLocation to the desadv
		final HADRE1 ucAddress = DESADV_objectFactory.createHADRE1();
		ucAddress.setDOCUMENTID(header.getDOCUMENTID());
		ucAddress.setADDRESSQUAL(AddressQual.ULCO.toString());
		ucAddress.setPARTYIDGLN(buyrLocation.getGLN());
		header.getHADRE1().add(ucAddress);

		// address HCTAD1 contact not mapped for now
		// transport details HTRSD1 not mapped for now
	}

	private void mapPackaging(
			@NonNull final DesadvLines desadvLines,
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final HEADERXlief header,
			@NonNull final DesadvSettings settings,
			@NonNull final DecimalFormat decimalFormat,
			@NonNull final String dateFormat)
	{
		final PACKINXlief packIn = DESADV_objectFactory.createPACKINXlief();
		header.setPACKIN(packIn);

		packIn.setDOCUMENTID(header.getDOCUMENTID());

		final boolean ssccRequired = settings.isDesadvLineSSCCRequired();
		final boolean packagingCodeTURequired = settings.isDesadvLinePackagingCodeTURequired();
		final boolean ppack1NeededInGeneral = ssccRequired || packagingCodeTURequired;

		final List<EDIExpDesadvLineType> sortedLines = desadvLines.getAllSortedByLine();

		if (ppack1NeededInGeneral)
		{
			int packagingTotalNumber = 0;

			for (final EDIExpDesadvLineType line : sortedLines)
			{
				final String documentId = xmlDesadv.getDocumentNo();
				final String lineNumber = extractLineNumber(line, decimalFormat);

				final List<SinglePack> packs = desadvLines.getPacksForLine(line.getEDIDesadvLineID().intValue());
				if (packs.isEmpty())
				{
					final LineAndPack lineAndPack = new LineAndPack(line, null/* pack */);
					final DETAILXlief detail = createDETAILXliefForLineAndPack(xmlDesadv, lineAndPack, settings, decimalFormat, dateFormat);
					packIn.getDETAIL().add(detail);

					// check if we need a discrepancy information
					final BigDecimal quantityDiff = ZERO.subtract(line.getQtyEntered());
					if (settings.isDesadvLineDQVAR1() && quantityDiff.signum() != 0)
					{
						detail.setDOCUMENTID(documentId);
						detail.setLINENUMBER(lineNumber);

						final DQVAR1 dqvar1 = createDQVAR1(documentId, line, quantityDiff, decimalFormat);
						detail.setDQVAR1(dqvar1);
					}
					continue;
				}

				BigDecimal qtyDelivered = ZERO;

				for (final SinglePack pack : packs)
				{
					final LineAndPack lineAndPack = new LineAndPack(line, pack);
					boolean detailAdded = false;
					if (ssccRequired)
					{
						final PPACK1 ppack1 = createPPACK1(header);
						packIn.getPPACK1().add(ppack1);

						final String sscc18 = validateString(
								lineAndPack.getSinglePack().getPack().getIPASSCC18(),
								"@FillMandatory@ SSCC in @EDI_DesadvLine_ID@ " + lineAndPack.getLine().getLine());
						ppack1.setIDENTIFICATIONCODE(Util.lpadZero(sscc18, 18)/* if ssccRequired and we got here, then this is not null */);
						ppack1.setIDENTIFICATIONQUAL(PackIdentificationQual.SSCC.toString());

						if (settings.isDesadvLinePackagingCodeLURequired())
						{
							final String packagingCodeLU = validateString(
									lineAndPack.getSinglePack().getPack().getMHUPackagingCodeLUText(),
									"@FillMandatory@ @EDI_DesadvLine_ID@=" + lineAndPack.getLine().getLine() + " @M_HU_PackagingCode_LU_ID@");
							ppack1.setPACKAGINGCODE(packagingCodeLU);
							ppack1.setPACKAGINGLEVEL(PackagingLevel.OUTE.toString());
						}

						final DETAILXlief detailXlief = createDETAILXliefForLineAndPack(xmlDesadv, lineAndPack, settings, decimalFormat, dateFormat);
						ppack1.getDETAIL().add(detailXlief);

						detailAdded = true;
					}
					if (packagingCodeTURequired)
					{
						final PPACK1 ppack1 = createPPACK1(header);
						packIn.getPPACK1().add(ppack1);

						final String packagingCodeTU = validateString(
								lineAndPack.getSinglePack().getPackItem().getMHUPackagingCodeTUText(),
								"@FillMandatory@ @EDI_DesadvLine_ID@=" + lineAndPack.getLine().getLine() + " @M_HU_PackagingCode_TU_ID@");
						ppack1.setPACKAGINGCODE(packagingCodeTU);
						ppack1.setPACKAGINGLEVEL(PackagingLevel.INNE.toString());

						if (!detailAdded)
						{
							final DETAILXlief detailXlief = createDETAILXliefForLineAndPack(xmlDesadv, lineAndPack, settings, decimalFormat, dateFormat);
							ppack1.getDETAIL().add(detailXlief);
						}
					}
					packagingTotalNumber++; // if there are no PPacks,packagingTotalNumber shall remain zero

					qtyDelivered = qtyDelivered.add(extractQtyDelivered(lineAndPack.getSinglePack()));
				}

				// we iterated all packs if the current line; now check if we a discrepancy information
				final BigDecimal quantityDiff = qtyDelivered.subtract(line.getQtyEntered());
				if (settings.isDesadvLineDQVAR1() && quantityDiff.signum() != 0)
				{
					final DETAILXlief detail = DESADV_objectFactory.createDETAILXlief();
					detail.setDOCUMENTID(documentId);
					detail.setLINENUMBER(lineNumber);

					final DQVAR1 dqvar1 = createDQVAR1(documentId, line, quantityDiff, decimalFormat);
					detail.setDQVAR1(dqvar1);
				}
			}
			packIn.setPACKAGINGTOTAL(formatNumber(packagingTotalNumber, decimalFormat));
		}
		else
		{
			final PackagingLUGroupings packagingCodeLUs = extractPackagingCodeLU(desadvLines, sortedLines, settings); // has at least one key, even if there is no packagingCodeLU

			if (packagingCodeLUs.hasSinglePackagingCode())
			{
				// only one global packagingCodeLU, so no need for PPACK1s. We can add the one packagingCodeLU to the PACKIN
				if (settings.isDesadvLinePackagingCodeLURequired())
				{
					final String packagingCodeLUText = packagingCodeLUs.getSinglePackagingCode();

					final String packagingCodeLU = validateString(
							"NONE".equals(packagingCodeLUText) ? null : packagingCodeLUText,
							"@FillMandatory@ @EDI_Desadv_ID@=" + xmlDesadv.getDocumentNo() + " @M_HU_PackagingCode_LU_ID@");
					packIn.setPACKAGINGCODE(packagingCodeLU);
					packIn.setPACKAGINGLEVEL(PackagingLevel.OUTE.toString());
				}
				for (final EDIExpDesadvLineType line : sortedLines)
				{
					// don't create PPACK1s, but create one aggregated detail per line
					final DETAILXlief detailXlief = createDETAILXliefForLine(desadvLines, xmlDesadv, line, settings, decimalFormat);
					packIn.getDETAIL().add(detailXlief);
				}
			}
			else
			{
				for (final String packagingCodeLUText : packagingCodeLUs.getPackagingCodes())
				{
					final PPACK1 ppack1 = createPPACK1(header);
					packIn.getPPACK1().add(ppack1);

					// no need to have PPACK1s. We can add the one packagingCodeLU to the PACKIN
					if (settings.isDesadvLinePackagingCodeLURequired())
					{
						final String packagingCodeLU = validateString(
								"NONE".equals(packagingCodeLUText) ? null : packagingCodeLUText, // fail if "NONE"
								"@FillMandatory@ @EDI_Desadv_ID@=" + xmlDesadv.getDocumentNo() + " @M_HU_PackagingCode_LU_ID@");
						ppack1.setPACKAGINGCODE(packagingCodeLU);
						ppack1.setPACKAGINGLEVEL(PackagingLevel.OUTE.toString());
					}
					for (final EDIExpDesadvLineType line : packagingCodeLUs.getLines(packagingCodeLUText))
					{
						for (final SinglePack pack : packagingCodeLUs.getPacks(packagingCodeLUText, line))
						{
							final LineAndPack lineAndPack = new LineAndPack(line, pack);
							final DETAILXlief detailXlief = createDETAILXliefForLineAndPack(xmlDesadv, lineAndPack, settings, decimalFormat, dateFormat);
							ppack1.getDETAIL().add(detailXlief);
						}
					}
				}
			}

			packIn.setPACKAGINGTOTAL(formatNumber(0, decimalFormat));
		}
	}

	private PackagingLUGroupings extractPackagingCodeLU(
			@NonNull final DesadvLines desadvLines,
			@NonNull final List<EDIExpDesadvLineType> lines,
			@NonNull final DesadvSettings settings)
	{
		final PackagingLUGroupings packingLUGroupings = new PackagingLUGroupings();

		for (final EDIExpDesadvLineType line : lines)
		{
			for (final SinglePack pack : desadvLines.getPacksForLine(line.getEDIDesadvLineID().intValue()))
			{
				final String packagingCodeLU;
				if (!settings.isDesadvLinePackagingCodeLURequired())
				{
					// Even if packagincCodes are not of interest, then still add "something", to avoid having different keys that we don't actually care for
					packagingCodeLU = "NONE";
				}
				else
				{
					packagingCodeLU = StringUtils.isEmpty(pack.getPack().getMHUPackagingCodeLUText()) ? "NONE" : pack.getPack().getMHUPackagingCodeLUText();
				}

				packingLUGroupings.add(packagingCodeLU, line, pack);
			}
		}
		return packingLUGroupings;
	}

	/** Needed if we have to partition our lines and packs by different LU-packagingCodes. One line can have more than one different LU-packagingCode. */
	@Value // note that this class is NOT immutable
	private static class PackagingLUGroupings
	{
		LinkedHashMap<String, LinkedHashSet<EDIExpDesadvLineType>> packagingCodeTolines = new LinkedHashMap<>();
		Map<PackagingCodeLUAndLine, List<SinglePack>> packs = new HashMap<>();

		public void add(
				@NonNull final String packagingCodeLU,
				@NonNull final EDIExpDesadvLineType line,
				@NonNull final SinglePack pack)
		{
			final LinkedHashSet<EDIExpDesadvLineType> codeLines = packagingCodeTolines.computeIfAbsent(packagingCodeLU, key -> new LinkedHashSet<>());
			codeLines.add(line);

			final PackagingCodeLUAndLine packagingCodeLUAndLine = new PackagingCodeLUAndLine(packagingCodeLU, line);
			final List<SinglePack> codeAndLinePacks = packs.computeIfAbsent(packagingCodeLUAndLine, key -> new ArrayList<>());
			codeAndLinePacks.add(pack);
		}

		public boolean hasSinglePackagingCode()
		{
			return packagingCodeTolines.size() == 1;
		}

		public String getSinglePackagingCode()
		{
			return packagingCodeTolines.keySet().iterator().next();
		}

		public Collection<String> getPackagingCodes()
		{
			return packagingCodeTolines.keySet();
		}

		public Collection<EDIExpDesadvLineType> getLines(@NonNull final String packagingCodeLUText)
		{
			return packagingCodeTolines.get(packagingCodeLUText);
		}

		public Collection<SinglePack> getPacks(@NonNull final String packagingCodeLU, @NonNull final EDIExpDesadvLineType line)
		{
			final PackagingCodeLUAndLine packagingCodeLUAndLine = new PackagingCodeLUAndLine(packagingCodeLU, line);
			return packs.get(packagingCodeLUAndLine);
		}
	}

	@Value
	private static class PackagingCodeLUAndLine
	{
		String packagingCodeLU;
		EDIExpDesadvLineType line;
	}

	private PPACK1 createPPACK1(@NonNull final HEADERXlief header)
	{
		final PPACK1 ppack1 = DESADV_objectFactory.createPPACK1();
		ppack1.setDOCUMENTID(header.getDOCUMENTID());
		ppack1.setPACKAGINGDETAIL(DEFAULT_PACK_DETAIL); // one, as per spec
		return ppack1;
	}

	private DETAILXlief createDETAILXliefForLineAndPack(
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final LineAndPack lineAndPack,
			@NonNull final DesadvSettings settings,
			@NonNull final DecimalFormat decimalFormat,
			@NonNull final String dateFormat)
	{
		final EDIExpDesadvLineType line = lineAndPack.getLine();

		final DETAILXlief detail = createDetailAndAddLineData(xmlDesadv, line, settings, decimalFormat);

		final String documentId = xmlDesadv.getDocumentNo();

		final String lineNumber = extractLineNumber(line, decimalFormat);
		final BigDecimal qtyDelivered = extractQtyDelivered(lineAndPack.getSinglePack());

		final DQUAN1 cuQuantity = createQuantityDetail(documentId, lineNumber, QuantityQual.DELV);
		cuQuantity.setQUANTITY(formatNumber(qtyDelivered, decimalFormat));
		detail.getDQUAN1().add(cuQuantity); // we set some of cuQuantity's properties in the following if-else-blocks

		if (lineAndPack.hasPack())
		{
			final EDIExpDesadvPackItemType packItem = lineAndPack.getSinglePack().getPackItem();

			final String measurementUnitName = extractMeasurementUnitOrNull(packItem.getEDIDesadvLineID().getCUOMID(), line, settings);
			cuQuantity.setMEASUREMENTUNIT(measurementUnitName);

			if (settings.isDesadvLineCUTURequired())
			{
				final BigDecimal qtyItemCapacity = validateObject(packItem.getQtyCUsPerTU(),
																  "@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @QtyCU@");
				final DQUAN1 cuTuQuantity = createQuantityDetail(documentId, lineNumber, QuantityQual.CUTU);
				cuTuQuantity.setQUANTITY(formatNumber(qtyItemCapacity, decimalFormat));
				cuTuQuantity.setMEASUREMENTUNIT(measurementUnitName);
				detail.getDQUAN1().add(cuTuQuantity);
			}

			final boolean dmark1Required = settings.isDesadvLineDMARK1BestBeforeDateRequired() //
					|| settings.isDesadvLineDMARK1BatchNoRequired();

			if (dmark1Required)
			{
				final String lotNumber = validateString(packItem.getLotNumber(),
														"@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @LotNumber@");

				final DMARK1 dmark1 = DESADV_objectFactory.createDMARK1();
				dmark1.setDOCUMENTID(documentId);
				dmark1.setLINENUMBER(lineNumber);
				dmark1.setIDENTIFICATIONQUAL(IdentificationQual.BATC.name());
				dmark1.setIDENTIFICATIONCODE(lotNumber);
				if (settings.isDesadvLineDMARK1BestBeforeDateRequired())
				{
					final XMLGregorianCalendar bestBefore = validateObject(packItem.getBestBeforeDate(),
																		   "@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @BestBeforeDate@");
					dmark1.setIDENTIFICATIONDATE1(toFormattedStringDate(toDate(bestBefore), dateFormat));
				}
				detail.getDMARK1().add(dmark1);
			}
		}
		return detail;
	}

	private DETAILXlief createDETAILXliefForLine(
			@NonNull final DesadvLines desadvLines,
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final EDIExpDesadvLineType line,
			@NonNull final DesadvSettings settings,
			@NonNull final DecimalFormat decimalFormat)
	{

		BigDecimal qtyCU = null;

		BigDecimal qtyDelivered = ZERO;
		String measurementUnitName = null;
		for (final SinglePack singlePack : desadvLines.getPacksForLine(line.getEDIDesadvLineID().intValue()))
		{
			qtyDelivered = qtyDelivered.add(extractQtyDelivered(singlePack));
			measurementUnitName = extractMeasurementUnitOrNull(singlePack.getDesadvLine().getCUOMID(), line, settings);
			qtyCU = singlePack.getPackItem().getQtyCUsPerTU();
		}

		if (measurementUnitName == null) // case: there were no packs
		{
			measurementUnitName = extractMeasurementUnitOrNull(line.getCUOMID(), line, settings);
		}

		final String documentId = xmlDesadv.getDocumentNo();
		final String lineNumber = extractLineNumber(line, decimalFormat);

		// now create the detail and add our stuff
		final DETAILXlief detail = createDetailAndAddLineData(xmlDesadv, line, settings, decimalFormat);

		final DQUAN1 cuQuantity = createQuantityDetail(documentId, lineNumber, QuantityQual.DELV);
		detail.getDQUAN1().add(cuQuantity);

		cuQuantity.setQUANTITY(formatNumber(qtyDelivered, decimalFormat));
		cuQuantity.setMEASUREMENTUNIT(measurementUnitName);
		if (qtyDelivered.signum() > 0 && settings.isDesadvLineCUTURequired())
		{
			final BigDecimal qtyItemCapacity = validateObject(qtyCU,
															  "@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @QtyCU@");
			final DQUAN1 cuTuQuantity = createQuantityDetail(documentId, lineNumber, QuantityQual.CUTU);
			cuTuQuantity.setQUANTITY(formatNumber(qtyItemCapacity, decimalFormat));
			cuTuQuantity.setMEASUREMENTUNIT(measurementUnitName);
			detail.getDQUAN1().add(cuTuQuantity);
		}

		// check if we need a discrepancy information
		final BigDecimal quantityDiff = qtyDelivered.subtract(line.getQtyEntered());
		if (settings.isDesadvLineDQVAR1() && quantityDiff.signum() != 0)
		{
			detail.setDOCUMENTID(documentId);
			detail.setLINENUMBER(lineNumber);

			final DQVAR1 dqvar1 = createDQVAR1(documentId, line, quantityDiff, decimalFormat);
			detail.setDQVAR1(dqvar1);
		}

		return detail;
	}

	@Nullable
	private String extractMeasurementUnitOrNull(
			@Nullable final EDIExpCUOMType uom,
			@NonNull final EDIExpDesadvLineType line,
			@NonNull final DesadvSettings settings)
	{
		if (!settings.isDesadvLineMEASUREMENTUNITRequired())
		{
			return null;
		}

		final String x12DE355 = ValidationHelper.validateString(
				uom == null ? null : uom.getX12DE355(),
				"@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @C_UOM_ID@");

		final MeasurementUnit measurementUnit = MeasurementUnit.fromMetasfreshUOM(x12DE355);
		if (!settings.isMeasurementUnitAllowed(measurementUnit))
		{
			throw new RuntimeCamelException("@C_InvoiceLine_ID@=" + line.getLine() + " @C_UOM_ID@=" + settings.getDesadvLineRequiredMEASUREMENTUNIT() + " @REQUIRED@");
		}
		return measurementUnit == null ? null : measurementUnit.name();
	}

	private String extractLineNumber(@NonNull final EDIExpDesadvLineType line, @NonNull final DecimalFormat decimalFormat)
	{
		return formatNumber(line.getLine(), decimalFormat);
	}

	private DETAILXlief createDetailAndAddLineData(
			@NonNull final EDIExpDesadvType xmlDesadv,
			@NonNull final EDIExpDesadvLineType line,
			@NonNull final DesadvSettings settings,
			final DecimalFormat decimalFormat)
	{
		final DETAILXlief detail = DESADV_objectFactory.createDETAILXlief();

		final String documentId = xmlDesadv.getDocumentNo();

		detail.setDOCUMENTID(documentId);

		final String lineNumber = extractLineNumber(line, decimalFormat);
		detail.setLINENUMBER(lineNumber);

		if (settings.isDesadvLineEANCRequired())
		{
			final String eanc = ValidationHelper.validateString(line.getEANCU(),
																"@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @EANCU@");

			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.EANC.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(eanc);
			detail.getDPRIN1().add(eancProdInfo);
		}
		if (settings.isDesadvLineEANTRequired())
		{
			final String eant = ValidationHelper.validateString(line.getEANTU(),
																"@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @EANTU@");

			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.EANT.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(eant);
			detail.getDPRIN1().add(eancProdInfo);
		}
		if (settings.isDesadvLineGTINRequired())
		{
			final String gtin = ValidationHelper.validateString(line.getGTIN(),
																"@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @GTIN@");

			final DPRIN1 gtinProdInfo = DESADV_objectFactory.createDPRIN1();
			gtinProdInfo.setDOCUMENTID(documentId);
			gtinProdInfo.setPRODUCTQUAL(ProductQual.GTIN.toString());
			gtinProdInfo.setLINENUMBER(lineNumber);
			gtinProdInfo.setPRODUCTID(gtin);
			detail.getDPRIN1().add(gtinProdInfo);
		}
		if (settings.isDesadvLineUPCCRequired())
		{
			final String upcc = ValidationHelper.validateString(line.getUPC(),
																"@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @UPC@");
			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.UPCC.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(upcc);
			detail.getDPRIN1().add(eancProdInfo);
		}
		if (settings.isDesadvLineUPCCRequired())
		{
			final String upct = ValidationHelper.validateString(line.getUPCTU(),
																"@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @UPCTU@");

			final DPRIN1 eancProdInfo = DESADV_objectFactory.createDPRIN1();
			eancProdInfo.setDOCUMENTID(documentId);
			eancProdInfo.setPRODUCTQUAL(ProductQual.UPCT.toString());
			eancProdInfo.setLINENUMBER(lineNumber);
			eancProdInfo.setPRODUCTID(upct);
			detail.getDPRIN1().add(eancProdInfo);
		}

		if (settings.isDesadvLineBUYRRequired())
		{
			final String buyr = ValidationHelper.validateString(line.getProductNo(),
																"@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @ProductNo@");

			final DPRIN1 prodInfo = DESADV_objectFactory.createDPRIN1();
			prodInfo.setDOCUMENTID(documentId);
			prodInfo.setPRODUCTQUAL(ProductQual.BUYR.toString());
			prodInfo.setLINENUMBER(lineNumber);
			prodInfo.setPRODUCTID(buyr);
			detail.getDPRIN1().add(prodInfo);
		}
		if (StringUtils.isNotEmpty(line.getProductDescription()))
		{
			final DPRDE1 prodDescr = DESADV_objectFactory.createDPRDE1();
			prodDescr.setDOCUMENTID(documentId);
			prodDescr.setLINENUMBER(lineNumber);
			prodDescr.setPRODUCTDESCQUAL(ProductDescQual.PROD.toString());
			prodDescr.setPRODUCTDESCTYPE(ProductDescType.CU.toString());
			prodDescr.setPRODUCTDESCTEXT(trimAndTruncate(line.getProductDescription(), 512));
			detail.getDPRDE1().add(prodDescr);
		}
		if (settings.isDesadvLinePRICRequired())
		{
			final BigDecimal priceActual = validateObject(line.getPriceActual(),
														  "@FillMandatory@ @EDI_DesadvLine_ID@=" + line.getLine() + " @PriceActual@");

			final DPRDE1 prodDescr = DESADV_objectFactory.createDPRDE1();
			prodDescr.setDOCUMENTID(documentId);
			prodDescr.setLINENUMBER(lineNumber);
			prodDescr.setPRODUCTDESCQUAL(ProductDescQual.PRIC.toString());
			prodDescr.setPRODUCTDESCTYPE(ProductDescType.CU.toString());
			prodDescr.setPRODUCTDESCTEXT(formatNumber(priceActual, decimalFormat));
			detail.getDPRDE1().add(prodDescr);
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

	private DQVAR1 createDQVAR1(
			@NonNull final String documentId,
			@NonNull final EDIExpDesadvLineType line,
			@NonNull final BigDecimal quantityDiff,
			@NonNull final DecimalFormat decimalFormat)
	{
		final DQVAR1 dqvar1 = DESADV_objectFactory.createDQVAR1();
		dqvar1.setDOCUMENTID(documentId);
		dqvar1.setLINENUMBER(extractLineNumber(line, decimalFormat));
		dqvar1.setQUANTITY(formatNumber(quantityDiff, decimalFormat));
		dqvar1.setDISCREPANCYCODE(extractDiscrepancyCode(line.getIsSubsequentDeliveryPlanned(), quantityDiff).toString());
		return dqvar1;
	}

	@NonNull
	private BigDecimal extractQtyDelivered(@Nullable final SinglePack singlePack)
	{
		if (singlePack == null)
		{
			return ZERO;
		}
		final BigDecimal qtyDelivered = singlePack.getPackItem().getQtyCUsPerLU();
		if (qtyDelivered == null)
		{
			return ZERO;
		}
		return qtyDelivered;
	}

	private DiscrepencyCode extractDiscrepancyCode(
			@Nullable final String isSubsequentDeliveryPlanned,
			@NonNull final BigDecimal diff)
	{
		DiscrepencyCode discrepancyCode;
		if (diff.signum() > 0)
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
}
