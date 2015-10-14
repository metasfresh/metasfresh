package de.metas.edi.esb.bean.desadv;

/*
 * #%L
 * Metas :: Components :: EDI  (SMX-4.5.1)
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static de.metas.edi.esb.commons.Util.formatNumber;
import static de.metas.edi.esb.commons.Util.toDate;
import static de.metas.edi.esb.commons.ValidationHelper.validateObject;
import static de.metas.edi.esb.commons.ValidationHelper.validateString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.milyn.payload.JavaSource;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.EDIExpCBPartnerLocationType;
import de.metas.edi.esb.jaxb.EDIExpCBPartnerProductVType;
import de.metas.edi.esb.jaxb.EDIExpCBPartnerType;
import de.metas.edi.esb.jaxb.EDIExpCOrderLineType;
import de.metas.edi.esb.jaxb.EDIExpCOrderType;
import de.metas.edi.esb.jaxb.EDIExpMIOLHUIPASSCC18VType;
import de.metas.edi.esb.jaxb.EDIExpMInOutLineType;
import de.metas.edi.esb.jaxb.EDIExpMInOutType;
import de.metas.edi.esb.jaxb.EDIMInOutOverdeliveryCOrderLineVType;
import de.metas.edi.esb.pojo.desadv.compudata.H000;
import de.metas.edi.esb.pojo.desadv.compudata.H100;
import de.metas.edi.esb.pojo.desadv.compudata.P050;
import de.metas.edi.esb.pojo.desadv.compudata.P060;
import de.metas.edi.esb.pojo.desadv.compudata.P100;
import de.metas.edi.esb.pojo.desadv.compudata.P102;
import de.metas.edi.esb.pojo.desadv.compudata.join.JP060P100;
import de.metas.edi.esb.route.exports.EDIDesadvRoute;

public class EDIDesadvSingleBean extends AbstractEDIDesadvCommonBean
{
	/**
	 * <ul>
	 * <li>IN: {@link EDIExpMInOutType}</li>
	 * <li>OUT: {@link H000}</li>
	 * </ul>
	 *
	 * @param exchange
	 */
	@Override
	public final void createEDIData(final Exchange exchange)
	{
		final EDIExpMInOutType xmlInOut = validateExchange(exchange); // throw exceptions if mandatory fields are missing
		sortLines(xmlInOut);

		final DecimalFormat decimalFormat = exchange.getProperty(Constants.DECIMAL_FORMAT, DecimalFormat.class);

		final H000 desadvDocument = createEDIDesadvFromXMLBean(xmlInOut, decimalFormat, exchange.getProperty(EDIDesadvRoute.EDI_DESADV_IS_TEST, String.class));

		final JavaSource source = new JavaSource(desadvDocument);
		exchange.getIn().setBody(source, H000.class);
	}

	private void sortLines(final EDIExpMInOutType inOut)
	{
		// sort M_InOutLines
		final List<EDIExpMInOutLineType> inOutLines = inOut.getEDIMInOutLine();
		Collections.sort(inOutLines, new Comparator<EDIExpMInOutLineType>()
		{
			@Override
			public int compare(final EDIExpMInOutLineType iol1, final EDIExpMInOutLineType iol2)
			{
				final BigInteger line1 = iol1.getCOrderLineID().getLine();
				final BigInteger line2 = iol2.getCOrderLineID().getLine();
				return line1.compareTo(line2);
			}
		});

		// sort Overdelivery C_OrderLines
		final List<EDIMInOutOverdeliveryCOrderLineVType> overdeliveryLines = inOut.getEDIMInOutOverdeliveryCOrderLineV();
		Collections.sort(overdeliveryLines, new Comparator<EDIMInOutOverdeliveryCOrderLineVType>()
		{
			@Override
			public int compare(final EDIMInOutOverdeliveryCOrderLineVType odl1, final EDIMInOutOverdeliveryCOrderLineVType odl2)
			{
				final BigInteger line1 = odl1.getCOrderLineID().getLine();
				final BigInteger line2 = odl2.getCOrderLineID().getLine();
				return line1.compareTo(line2);
			}
		});
	}

	private H000 createEDIDesadvFromXMLBean(final EDIExpMInOutType xmlInOut, final DecimalFormat decimalFormat, final String testFlag)
	{
		final H000 h000 = new H000();
		h000.setMessageDate(SystemTime.asDate());
		h000.setReceiver(xmlInOut.getCBPartnerID().getEdiRecipientGLN());

		h000.setReference(formatNumber(xmlInOut.getSequenceNoAttr(), decimalFormat));
		h000.setTestFlag(testFlag);

		h000.setH100Lines(createH100LinesFromXmlInOut(xmlInOut, decimalFormat));

		return h000;
	}

	/**
	 * Validate document, and throw {@link RuntimeCamelException} if mandatory fields or properties are missing or empty.
	 *
	 * @param exchange
	 *
	 * @return {@link EDIExpMInOutType} M_InOut JAXB object
	 */
	private EDIExpMInOutType validateExchange(final Exchange exchange)
	{
		// validate mandatory exchange properties
		validateString(exchange.getProperty(EDIDesadvRoute.EDI_DESADV_IS_TEST, String.class), "exchange property IsTest cannot be null or empty");

		final EDIExpMInOutType xmlInOut = exchange.getIn().getBody(EDIExpMInOutType.class);

		// Evaluate M_InOuts
		// validate mandatory types (not null)
		validateObject(xmlInOut.getCBPartnerID(), "@FillMandatory@ @M_InOut_ID@=" + xmlInOut.getDocumentNo() + " @C_BPartner_ID@");
		validateObject(xmlInOut.getSequenceNoAttr(), "@FillMandatory@ @M_InOut_ID@=" + xmlInOut.getDocumentNo() + " @SequenceNoAttr@");
		validateObject(xmlInOut.getMovementDate(), "@FillMandatory@ @M_InOut_ID@=" + xmlInOut.getDocumentNo() + " @MovementDate@");
		validateObject(xmlInOut.getDateOrdered(), "@FillMandatory@ @M_InOut_ID@=" + xmlInOut.getDocumentNo() + " @DateOrdered@");
		validateObject(xmlInOut.getCBPartnerLocationID(), "@FillMandatory@ @M_InOut_ID@=" + xmlInOut.getDocumentNo() + " @C_BPartner_Location_ID@");
		validateObject(xmlInOut.getCOrderID(), "@FillMandatory@ @M_InOut_ID@=" + xmlInOut.getDocumentNo() + " @C_Order_ID@");
		validateObject(xmlInOut.getCOrderID().getCCurrencyID(), "@FillMandatory@ @C_Order_ID@=" + xmlInOut.getCOrderID().getDocumentNo() + " @C_Currency_ID@");
		validateObject(xmlInOut.getCOrderID().getBillBPartnerID(), "@FillMandatory@ @C_Order_ID@=" + xmlInOut.getCOrderID().getDocumentNo() + " @Bill_BPartner_ID@");
		validateObject(xmlInOut.getCOrderID().getBillLocationID(), "@FillMandatory@ @C_Order_ID@=" + xmlInOut.getCOrderID().getDocumentNo() + " @Bill_Location_ID@");

		// validate strings (not null or empty)
		validateString(xmlInOut.getCBPartnerID().getEdiRecipientGLN(), "@FillMandatory@ @C_BPartner_ID@=" + xmlInOut.getCBPartnerID().getValue() + " @EdiRecipientGLN@");
		validateString(xmlInOut.getCBPartnerLocationID().getGLN(), "@FillMandatory@ @C_BPartner_Location_ID@=" + xmlInOut.getCBPartnerLocationID().getName() + " @GLN@");
		validateString(xmlInOut.getCOrderID().getBillBPartnerID().getEdiRecipientGLN(), "@FillMandatory@ @Bill_BPartner_ID@=" + xmlInOut.getCOrderID().getBillBPartnerID().getValue()
				+ " @EdiRecipientGLN@");
		validateString(xmlInOut.getCOrderID().getBillLocationID().getGLN(), "@FillMandatory@ @Bill_Location_ID@=" + xmlInOut.getCOrderID().getBillLocationID().getName() + " @GLN@");
		if (xmlInOut.getCOrderID().getHandOverLocationID() != null)
		{
			validateString(xmlInOut.getCOrderID().getHandOverLocationID().getGLN(), "@FillMandatory@ @HandOver_Location_ID@=" + xmlInOut.getDocumentNo() + " @GLN@");
		}

		// Evaluate M_InOutLines
		for (final EDIExpMInOutLineType xmlInOutLine : xmlInOut.getEDIMInOutLine())
		{
			// validate mandatory types (not null)
			validateObject(xmlInOutLine.getMovementQty(), "@FillMandatory@ @M_InOut_ID@=" + xmlInOut.getDocumentNo() + " @MovementQty@");
			validateObject(xmlInOutLine.getCUOMID(), "@FillMandatory@ @M_InOut_ID@=" + xmlInOut.getDocumentNo() + " @C_UOM_ID@");
			validateObject(xmlInOutLine.getMProductID(), "@FillMandatory@ @M_InOut_ID@=" + xmlInOut.getDocumentNo() + " @M_Product_ID@");
			// validateObject(xmlInOutLine.getEDIExpMIOLHUIPASSCC18V(), "EDI_Exp_M_IOL_HU_IPA_SSCC18_vType List cannot be null for " + xmlInOutLine);
			// validateObject(xmlInOutLine.getEDIExpMIOLHUIPASSCC18V().get(0), "EDI_Exp_M_IOL_HU_IPA_SSCC18_vType must have at least one element " + xmlInOutLine.getEDIExpMIOLHUIPASSCC18V());

			final EDIExpCOrderLineType orderLine = xmlInOutLine.getCOrderLineID();
			validateObject(orderLine.getQtyItemCapacity(), "@FillMandatory@ @C_Order_ID@=" + xmlInOut.getCOrderID().getDocumentNo() + " @QtyItemCapacity@");
			validateObject(orderLine.getLine(), "@FillMandatory@ @C_Order_ID@=" + xmlInOut.getCOrderID().getDocumentNo() + " @Line@");
			validateObject(orderLine.getQtyOrdered(), "@FillMandatory@ @C_Order_ID@=" + xmlInOut.getCOrderID().getDocumentNo() + " @QtyOrdered@");

			final List<EDIExpCBPartnerProductVType> bppList = orderLine.getEDIExpCBPartnerProductV();
			// ValidationHelper.validateList(bppList, "@FillMandatory@ @M_Product_ID@=" + xmlInOutLine.getMProductID().getValue() + " @C_BPartner_Product@");
			if (bppList != null && !bppList.isEmpty())
			{
				validateString(bppList.get(0).getProductNo(), "@FillMandatory@ @C_BPartner_Product@=" + bppList.get(0).getProductNo() + " @ProductNo@"); // FIXME (get C_BPP identifier)
				validateString(bppList.get(0).getUPC(), "@FillMandatory@ @C_BPartner_Product@=" + bppList.get(0).getProductNo() + " @UPC@");
			}
			
			if (xmlInOutLine.getMovementQty().signum() != 0)
			{
				// no point checking for a SSCC if there is no HU shipped
				final List<EDIExpMIOLHUIPASSCC18VType> sscc18Values = xmlInOutLine.getEDIExpMIOLHUIPASSCC18V();
				{
					final String sscc18Value = Util.removePrecedingZeros(sscc18Values.get(0).getValue());
					validateString(sscc18Value, "@FillMandatory@ SSCC18 in @M_InOutLine@ " + xmlInOutLine.getLine());
				}
			}
		}
		for (final EDIMInOutOverdeliveryCOrderLineVType overdeliveryLine : xmlInOut.getEDIMInOutOverdeliveryCOrderLineV())
		{
			final EDIExpCOrderLineType orderLine = overdeliveryLine.getCOrderLineID();
			validateObject(orderLine.getQtyItemCapacity(), "@FillMandatory@ @C_Order_ID@=" + xmlInOut.getCOrderID().getDocumentNo() + " @QtyItemCapacity@");
			validateObject(orderLine.getLine(), "@FillMandatory@ @C_Order_ID@=" + xmlInOut.getCOrderID().getDocumentNo() + " @Line@");
			validateObject(orderLine.getQtyOrdered(), "@FillMandatory@ @C_Order_ID@=" + xmlInOut.getCOrderID().getDocumentNo() + " @QtyOrdered@");

			final List<EDIExpCBPartnerProductVType> bppList = orderLine.getEDIExpCBPartnerProductV();
			// ValidationHelper.validateList(bppList, "@FillMandatory@ @M_Product_ID@=" + orderLine.getMProductID().getValue() + " @C_BPartner_Product@");
			if (bppList != null && !bppList.isEmpty())
			{
				validateString(bppList.get(0).getProductNo(), "@FillMandatory@ @C_BPartner_Product@=" + bppList.get(0).getProductNo() + " @ProductNo@"); // FIXME (get C_BPP identifier)
				validateString(bppList.get(0).getUPC(), "@FillMandatory@ @C_BPartner_Product@=" + bppList.get(0).getProductNo() + " @UPC@");
			}
		}

		return xmlInOut;
	}

	protected final List<H100> createH100LinesFromXmlInOut(final EDIExpMInOutType xmlInOut, final DecimalFormat decimalFormat)
	{
		final H100 h100 = new H100();

		final EDIExpCOrderType inOutOrder = xmlInOut.getCOrderID();

		final String orderBillLocationGLN = inOutOrder.getBillLocationID().getGLN();
		h100.setBuyerID(orderBillLocationGLN);

		EDIExpCBPartnerLocationType deliveryLocation = inOutOrder.getHandOverLocationID();
		if (deliveryLocation == null)
		{
			deliveryLocation = xmlInOut.getCBPartnerLocationID();
		}

		h100.setDeliveryID(deliveryLocation.getGLN());
		h100.setDeliveryName(Util.normalize(deliveryLocation.getName()));

		h100.setDeliveryDate(toDate(xmlInOut.getMovementDate()));

		h100.setDespatchNoteNumber(xmlInOut.getDocumentNo());
		h100.setDocumentNo(xmlInOut.getDocumentNo());
		h100.setInvoiceID(voidString);
		h100.setMessageDate(SystemTime.asDate());
		h100.setMessageNo(formatNumber(xmlInOut.getSequenceNoAttr(), decimalFormat));

		h100.setOrderDate(toDate(xmlInOut.getDateOrdered()));
		// 05768
		if (xmlInOut.getPOReference() != null && !xmlInOut.getPOReference().isEmpty())
		{
			h100.setOrderNumber(xmlInOut.getPOReference());
		}
		else
		{
			h100.setOrderNumber(Util.mkOwnOrderNumber(xmlInOut.getDocumentNo()));
		}

		final EDIExpCBPartnerType partner = xmlInOut.getCBPartnerID();
		h100.setPartner(partner.getEdiRecipientGLN());

		h100.setProdGrpCode(voidString);
		h100.setRampeID(voidString);
		h100.setReferenceCode(voidString);

		h100.setStoreName(Util.normalize(partner.getName()));
		h100.setStoreNumber(xmlInOut.getCBPartnerLocationID().getGLN());

		h100.setSupplierNo(voidString);
		h100.setTransportCode(voidString);
		h100.setTransportName(voidString);
		h100.setTransportReference(voidString);

		h100.setP050(createP050FromXmlInOut(xmlInOut, decimalFormat));

		// we only have one M_InOut document per EDI file ATM, but we also support more if necessary
		final List<H100> h100Lines = new ArrayList<H100>();
		h100Lines.add(h100);
		return h100Lines;
	}

	private P050 createP050FromXmlInOut(final EDIExpMInOutType xmlInOut, final DecimalFormat decimalFormat)
	{
		final P050 p050 = new P050();

		p050.setPartner(xmlInOut.getCBPartnerID().getEdiRecipientGLN());
		p050.setMessageNo(formatNumber(xmlInOut.getSequenceNoAttr(), decimalFormat));

		final List<P102> p102Lines = new ArrayList<P102>();
		for (final EDIMInOutOverdeliveryCOrderLineVType overdeliveryLine : xmlInOut.getEDIMInOutOverdeliveryCOrderLineV())
		{
			p102Lines.add(createP102Line(xmlInOut, overdeliveryLine, decimalFormat));
		}
		p050.setP102Lines(p102Lines);

		int cpsCounter = 2; // see wiki

		final List<JP060P100> joinP060P100Lines = new ArrayList<JP060P100>();
		for (final EDIExpMInOutLineType xmlInOutLine : xmlInOut.getEDIMInOutLine())
		{
			joinP060P100Lines.add(createJoinP060P100Lines(xmlInOut, xmlInOutLine, decimalFormat, cpsCounter));
			cpsCounter++;
		}
		p050.setJoinP060P100Lines(joinP060P100Lines);

		final int orderLineCount = xmlInOut.getEDIMInOutLine().size();
		p050.setPackageQty(formatNumber(orderLineCount, decimalFormat));

		p050.setPackageType(voidString);

		return p050;
	}

	private JP060P100 createJoinP060P100Lines(final EDIExpMInOutType xmlInOut, final EDIExpMInOutLineType xmlInOutLine, final DecimalFormat decimalFormat, final int cpsCounter)
	{
		final JP060P100 join = new JP060P100();

		join.setP060(createP060(xmlInOut, xmlInOutLine, decimalFormat, cpsCounter));
		join.setP100(createP100(xmlInOut, xmlInOutLine, decimalFormat));

		return join;
	}

	private P060 createP060(final EDIExpMInOutType xmlInOut, final EDIExpMInOutLineType xmlInOutLine, final DecimalFormat decimalFormat, final int cpsCounter)
	{
		final P060 p060 = new P060();

		p060.setcPScounter(formatNumber(BigInteger.valueOf(cpsCounter), decimalFormat));
		p060.setInnerOuterCode(voidString);
		p060.setMessageNo(formatNumber(xmlInOut.getSequenceNoAttr(), decimalFormat));

		// p060.setPalettQTY(xmlInOutLine.getCOrderLineID().getQtyItemCapacity()); // leave empty for now
		p060.setPalettTyp(voidString); // empty in sample - leave empty for now (see wiki)

		p060.setPartner(xmlInOut.getCBPartnerID().getEdiRecipientGLN());

		final List<EDIExpMIOLHUIPASSCC18VType> sscc18Values = xmlInOutLine.getEDIExpMIOLHUIPASSCC18V();
		if (sscc18Values != null && !sscc18Values.isEmpty())
		{
			// if (sscc18Values.size() > 1)
			// {
			// throw new RuntimeCamelException("Invalid number of sscc18 attribute values (expected 1, received " + sscc18Values.size() + ") on " + xmlInOutLine);
			// }
			final EDIExpMIOLHUIPASSCC18VType sscc18ValueFirst = sscc18Values.get(0);
			final String sscc18Value = Util.removePrecedingZeros(sscc18ValueFirst.getValue());
			// p060.setHierachSSCC(sscc18Value);
			p060.setNormalSSCC(sscc18Value);
		}

		// p060.setBruttogewicht(xmlInOutLine.getMProductID().getWeight()); // leave empty for now
		// p060.setVolumen(xmlInOutLine.getMProductID().getVolume()); // leave empty for now

		return p060;
	}

	private P100 createP100(final EDIExpMInOutType xmlInOut, final EDIExpMInOutLineType xmlInOutLine, final DecimalFormat decimalFormat)
	{
		final P100 p100 = new P100();

		p100.setArtDescription(xmlInOutLine.getProductDescription() == null ? voidString : xmlInOutLine.getProductDescription());
		p100.setArticleClass(voidString);
		// p100.setBestBeforeDate(EDIDesadvBean.voidDate); // leave empty
		p100.setChargenNo(voidString);

		final EDIExpCOrderLineType orderLine = xmlInOutLine.getCOrderLineID();
		p100.setcUperTU(
				formatNumber(orderLine.getQtyItemCapacity(), // might be OK: returning our internal CUperTU-Qty, as we also return or CU-Qtys
						decimalFormat));
		p100.setCurrency(xmlInOut.getCOrderID().getCCurrencyID().getISOCode());

		p100.setDeliverQTY(formatNumber(
				xmlInOutLine.getMovementQty(), // OK internal product/CU-UOM.
				decimalFormat));
		p100.setDeliverUnit(xmlInOutLine.getCUOMID().getX12DE355()); // note that we make sure in adempiere that this is the internal stocking UOM

		// "12" => Liefermenge
		// "11" => Teilmenge
		p100.setDeliverQual("12"); // TODO hardcoded
		BigDecimal qtyDelivered = orderLine.getQtyDelivered(); // OK internal product/CU-UOM
		if (qtyDelivered == null)
		{
			qtyDelivered = BigDecimal.ZERO;
		}
		p100.setDifferenceQTY(formatNumber(orderLine.getQtyOrdered().subtract(qtyDelivered), decimalFormat));
		p100.setDiscrepancyCode("CP"); // TODO hardcoded
		p100.setDiscrepancyText(voidString);
		p100.setDeliveryDate(toDate(xmlInOut.getMovementDate()));
		p100.setDetailPrice(voidString);
		p100.setUnitCode(voidString);
		// p100.setDiffDeliveryDate(EDIDesadvBean.voidDate);
		p100.setEanTU(voidString);
		p100.setMessageNo(formatNumber(xmlInOut.getSequenceNoAttr(), decimalFormat));
		// 05768
		if (xmlInOut.getPOReference() != null && !xmlInOut.getPOReference().isEmpty())
		{
			p100.setOrderNo(xmlInOut.getPOReference());
		}
		else
		{
			p100.setOrderNo(Util.mkOwnOrderNumber(xmlInOut.getDocumentNo()));
		}

		p100.setOrderPosNo(formatNumber(orderLine.getLine(), decimalFormat));
		p100.setPartner(xmlInOut.getCBPartnerID().getEdiRecipientGLN());
		p100.setPositionNo(formatNumber(xmlInOutLine.getLine(), decimalFormat));
		// p100.setSellBeforeDate(EDIDesadvBean.voidDate);
		// p100.setProductionDate(EDIDesadvBean.voidDate);
		p100.setStoreNumber(voidString);
		p100.setSupplierArtNo(voidString);

		final List<EDIExpCBPartnerProductVType> bppList = xmlInOutLine.getEDIExpCBPartnerProductV();
		if (bppList != null && !bppList.isEmpty())
		{
			// if (bppList.size() > 1)
			// {
			// throw new RuntimeCamelException("Invalid number of C_BPartner_Products (expected 1, received " + bppList.size() + ") on " + xmlInOutLine.getCOrderLineID());
			// }
			final EDIExpCBPartnerProductVType bpp = bppList.get(0);
			p100.setEanArtNo(bpp.getUPC());
			p100.setBuyerArtNo(bpp.getProductNo());
			p100.setArtDescription(xmlInOutLine.getProductDescription() == null ? voidString : xmlInOutLine.getProductDescription()); // TODO use bpp description, and fallback
		}

		return p100;
	}

	/**
	 * @param xmlInOut
	 * @param overdeliveryLine
	 * @return P102 line
	 */
	private P102 createP102Line(final EDIExpMInOutType xmlInOut, final EDIMInOutOverdeliveryCOrderLineVType overdeliveryLine, final DecimalFormat decimalFormat)
	{
		final P102 p102 = new P102();

		final EDIExpCOrderLineType orderLine = overdeliveryLine.getCOrderLineID();
		p102.setArtDescription(orderLine.getProductDescription() == null ? voidString : orderLine.getProductDescription());
		p102.setArticleClass(voidString);
		// p102.setBestBeforeDate(EDIDesadvBean.voidDate); // leave empty
		p102.setChargenNo(voidString);
		p102.setcUperTU(formatNumber(orderLine.getQtyItemCapacity(), decimalFormat));
		p102.setCurrency(xmlInOut.getCOrderID().getCCurrencyID().getISOCode());

		p102.setDeliverQTY(formatNumber(orderLine.getQtyDelivered(), decimalFormat));
		p102.setDeliverUnit(orderLine.getCUOMID().getX12DE355());

		p102.setDeliverQual("12"); // TODO hardcoded
		BigDecimal qtyDelivered = orderLine.getQtyDelivered();
		if (qtyDelivered == null)
		{
			qtyDelivered = BigDecimal.ZERO;
		}
		p102.setDifferenceQTY(formatNumber(orderLine.getQtyOrdered().subtract(qtyDelivered), decimalFormat));
		p102.setDiscrepancyCode(voidString); // not used here

		p102.setDeliveryDate(toDate(xmlInOut.getMovementDate()));
		p102.setDetailPrice(voidString);
		p102.setUnitCode(voidString);
		// p102.setDiffDeliveryDate(EDIDesadvBean.voidDate);
		p102.setEanTU(voidString);
		p102.setMessageNo(formatNumber(xmlInOut.getSequenceNoAttr(), decimalFormat));
		// 05768
		if (xmlInOut.getPOReference() != null && !xmlInOut.getPOReference().isEmpty())
		{
			p102.setOrderNo(xmlInOut.getPOReference());
		}
		else
		{
			p102.setOrderNo(Util.mkOwnOrderNumber(xmlInOut.getDocumentNo()));
		}
		p102.setOrderPosNo(formatNumber(orderLine.getLine(), decimalFormat));
		p102.setPartner(xmlInOut.getCBPartnerID().getEdiRecipientGLN());
		p102.setPositionNo(formatNumber(orderLine.getLine(), decimalFormat));
		// p102.setSellBeforeDate(EDIDesadvBean.voidDate);
		// p102.setProductionDate(EDIDesadvBean.voidDate);
		p102.setStoreNumber(voidString);
		p102.setSupplierArtNo(voidString);

		final List<EDIExpCBPartnerProductVType> bppList = orderLine.getEDIExpCBPartnerProductV();
		if (bppList != null && !bppList.isEmpty())
		{
			// if (bppList.size() > 1)
			// {
			// throw new RuntimeCamelException("Invalid number of C_BPartner_Products (expected 1, received " + bppList.size() + ") on " + xmlInOutLine.getCOrderLineID());
			// }
			final EDIExpCBPartnerProductVType bpp = bppList.get(0);
			p102.setEanArtNo(bpp.getUPC());
			p102.setBuyerArtNo(bpp.getProductNo());
			p102.setArtDescription(orderLine.getProductDescription() == null ? voidString : orderLine.getProductDescription()); // TODO use bpp description, and fallback
		}

		return p102;
	}
}
