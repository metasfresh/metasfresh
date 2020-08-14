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

package de.metas.edi.esb.invoicexport.compudata;

import static de.metas.edi.esb.commons.Util.formatNumber;
import static de.metas.edi.esb.commons.Util.isEmpty;
import static de.metas.edi.esb.commons.Util.normalize;
import static de.metas.edi.esb.commons.Util.toDate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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
import de.metas.edi.esb.jaxb.metasfresh.CCreditMemoReasonEnum;
import de.metas.edi.esb.jaxb.metasfresh.EDICctop000VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctop111VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctop119VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctop120VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctop140VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctop901991VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoic500VType;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoicVType;

public class CompuDataInvoicBean
{
	public static final String METHOD_createEDIData = "createEDIData";

	/**
	 * <ul>
	 * <li>IN: {@link EDICctopInvoicVType}</li>
	 * <li>OUT: {@link CctopInvoice}</li>
	 * </ul>
	 */
	public void createEDIData(final Exchange exchange)
	{
		final EDICctopInvoicVType xmlCctopInvoice = exchange.getIn().getBody(EDICctopInvoicVType.class);

		final DecimalFormat decimalFormat = exchange.getProperty(Constants.DECIMAL_FORMAT, DecimalFormat.class);

		final CctopInvoice invoice = createEDIInvoiceFromXMLBean(xmlCctopInvoice, decimalFormat, exchange);

		final JavaSource source = new JavaSource(invoice);
		exchange.getIn().setBody(source, CctopInvoice.class);
	}

	private CctopInvoice createEDIInvoiceFromXMLBean(final EDICctopInvoicVType xmlCctopInvoice, final DecimalFormat decimalFormat, final Exchange exchange)
	{
		final CctopInvoice invoice = new CctopInvoice();

		invoice.setCbPartnerLocationID(formatNumber(xmlCctopInvoice.getCBPartnerLocationID(), decimalFormat));
		invoice.setcInvoiceID(formatNumber(xmlCctopInvoice.getCInvoiceID(), decimalFormat));

		// set CreditMemo-related data if the C_Invoice is actually a CreditMemo
		{
			final CCreditMemoReasonEnum creditMemoReason = xmlCctopInvoice.getCreditMemoReason();
			final String creditMemoReasonStr;
			if (creditMemoReason == null)
			{
				creditMemoReasonStr = "";
			}
			else
			{
				// TODO: see 05725 comments regarding this field; we might need an additional field OR make a proper message out of this 3-char string
				creditMemoReasonStr = creditMemoReason.toString();
			}
			invoice.setCreditMemoReason(creditMemoReasonStr);
			invoice.setCreditMemoReasonText(xmlCctopInvoice.getCreditMemoReasonText());
			invoice.setEancomDoctype(xmlCctopInvoice.getEancomDoctype());
		}

		invoice.setDateInvoiced(toDate(xmlCctopInvoice.getDateInvoiced()));
		invoice.setDateOrdered(toDate(xmlCctopInvoice.getDateOrdered()));

		invoice.setGrandTotal(formatNumber(xmlCctopInvoice.getGrandTotal(), decimalFormat));
		invoice.setInvoiceDocumentno(xmlCctopInvoice.getInvoiceDocumentno());
		invoice.setIsoCode(xmlCctopInvoice.getISOCode());
		invoice.setMovementDate(toDate(xmlCctopInvoice.getMovementDate()));
		// 05768
		if (xmlCctopInvoice.getPOReference() != null && !xmlCctopInvoice.getPOReference().isEmpty())
		{
			invoice.setPoReference(xmlCctopInvoice.getPOReference());
		}
		else if (xmlCctopInvoice.getShipmentDocumentno() != null && !xmlCctopInvoice.getShipmentDocumentno().isEmpty())
		{
			invoice.setPoReference(Util.mkOwnOrderNumber(xmlCctopInvoice.getShipmentDocumentno()));
		}
		else
		{
			invoice.setPoReference(Util.mkOwnOrderNumber(xmlCctopInvoice.getInvoiceDocumentno()));
		}
		invoice.setReceivergln(xmlCctopInvoice.getReceivergln());
		invoice.setSendergln(xmlCctopInvoice.getSendergln());
		invoice.setShipmentDocumentno(xmlCctopInvoice.getShipmentDocumentno());
		invoice.setVataxID(xmlCctopInvoice.getVATaxID());
		// invoice.setTotalLines(formatNumber(xmlCctopInvoice.getTotalLines(), decimalFormat)); // not used
		invoice.setTotaltaxbaseamt(formatNumber(xmlCctopInvoice.getTotaltaxbaseamt(), decimalFormat));
		invoice.setTotalvat(formatNumber(xmlCctopInvoice.getTotalvat(), decimalFormat));
		invoice.setCountryCode(xmlCctopInvoice.getCountryCode());
		invoice.setCountryCode3Digit(xmlCctopInvoice.getCountryCode3Digit());

		invoice.setCctop000V(createCctop000V(xmlCctopInvoice, decimalFormat, exchange));
		invoice.setCctop111V(createCctop111V(xmlCctopInvoice, decimalFormat));
		invoice.setCctop119V(createCctop119VList(xmlCctopInvoice.getEDICctop119V(), decimalFormat));
		invoice.setCctop120V(createCctop120VList(xmlCctopInvoice.getEDICctop120V(), decimalFormat));
		invoice.setCctop140V(createCctop140VList(xmlCctopInvoice.getEDICctop140V(), decimalFormat));
		invoice.setCctop901991V(createCctop901991VList(xmlCctopInvoice.getEDICctop901991V(), decimalFormat));

		xmlCctopInvoice.getEDICctopInvoic500V().sort(Comparator.comparing(EDICctopInvoic500VType::getLine)); // requirement, lines need to be sorted
		invoice.setCctopInvoice500V(createCctopInvoice500VList(xmlCctopInvoice.getEDICctopInvoic500V(), decimalFormat));

		invoice.setCurrentDate(SystemTime.asDate());

		return invoice;
	}

	private Cctop000V createCctop000V(final EDICctopInvoicVType xmlCctopInvoice, final DecimalFormat decimalFormat, final Exchange exchange)
	{
		final EDICctop000VType xmlCctop000V = xmlCctopInvoice.getEDICctop000V();

		if (xmlCctop000V == null)
		{
			return null;
		}

		if (isEmpty(xmlCctop000V.getGLN()))
		{
			throw new RuntimeCamelException(xmlCctop000V + " should have a GLN");
		}

		final Cctop000V cctop000V = new Cctop000V();
		cctop000V.setCbPartnerLocationID(formatNumber(xmlCctop000V.getCBPartnerLocationID(), decimalFormat));
		cctop000V.setGln(xmlCctop000V.getGLN());

		final BigInteger sequenceNoAttr = xmlCctopInvoice.getSequenceNoAttr();
		if (sequenceNoAttr == null)
		{
			throw new RuntimeCamelException("sequenceNoAttr cannot be null for " + xmlCctopInvoice);
		}
		cctop000V.setInterchangeReferenceNo(sequenceNoAttr.toString());

		final Object senderGln = exchange.getProperty(CompuDataInvoicRoute.EDI_INVOIC_SENDER_GLN);
		if (senderGln == null)
		{
			throw new RuntimeCamelException("senderGln property cannot be null for!");
		}
		cctop000V.setSenderGln(senderGln.toString());

		final Object isTest = exchange.getProperty(CompuDataInvoicRoute.EDI_INVOIC_IS_TEST);
		if (isTest == null)
		{
			throw new RuntimeCamelException("isTest property cannot be null for!");
		}
		cctop000V.setIsTest(isTest.toString());

		return cctop000V;
	}

	private Cctop111V createCctop111V(
			final EDICctopInvoicVType xmlCctopInvoice,
			final DecimalFormat decimalFormat)
	{
		final EDICctop111VType xmlCctop111V = xmlCctopInvoice.getEDICctop111V();
		final Cctop111V cctop111V = new Cctop111V();

		if (xmlCctop111V != null)
		{
			cctop111V.setcOrderID(formatNumber(xmlCctop111V.getCOrderID(), decimalFormat));
			cctop111V.setmInOutID(formatNumber(xmlCctop111V.getMInOutID(), decimalFormat));
			cctop111V.setDateOrdered(toDate(xmlCctop111V.getDateOrdered()));
			cctop111V.setMovementDate(toDate(xmlCctop111V.getMovementDate()));
			cctop111V.setShipmentDocumentno(xmlCctop111V.getShipmentDocumentno()); // not send in edi-marshal-compudata-fresh.ftl
		}

		// 09920 prefer to send the invoice's own POReference
		if (isSet(xmlCctopInvoice.getPOReference()))
		{
			cctop111V.setPoReference(xmlCctopInvoice.getPOReference());
		}
		// 05768
		else if (xmlCctop111V != null && isSet(xmlCctop111V.getPOReference()))
		{
			cctop111V.setPoReference(xmlCctop111V.getPOReference());
		}
		else if (isSet(xmlCctopInvoice.getShipmentDocumentno()))
		{
			cctop111V.setPoReference(Util.mkOwnOrderNumber(xmlCctopInvoice.getShipmentDocumentno()));
		}
		else if (xmlCctop111V != null && isSet(xmlCctop111V.getShipmentDocumentno()))
		{
			cctop111V.setPoReference(Util.mkOwnOrderNumber(xmlCctop111V.getShipmentDocumentno()));
		}

		return cctop111V;
	}

	private boolean isSet(final String str)
	{
		return str != null && !str.trim().isEmpty();
	}

	private List<Cctop119V> createCctop119VList(final List<EDICctop119VType> xmlCctop119VList, final DecimalFormat decimalFormat)
	{
		final List<Cctop119V> cctop119VList = new ArrayList<>();

		for (final EDICctop119VType xmlCctop119V : xmlCctop119VList)
		{
			if (isEmpty(xmlCctop119V.getGLN()))
			{
				throw new RuntimeCamelException(xmlCctop119V + " must have a GLN");
			}

			if (isEmpty(xmlCctop119V.getVATaxID()))
			{
				throw new RuntimeCamelException(xmlCctop119V + " must have a VATTaxID");
			}

			final Cctop119V cctop119V = new Cctop119V();
			cctop119V.setCbPartnerLocationID(formatNumber(xmlCctop119V.getCBPartnerLocationID(), decimalFormat));
			cctop119V.setcInvoiceID(formatNumber(xmlCctop119V.getCInvoiceID(), decimalFormat));
			cctop119V.setmInOutID(formatNumber(xmlCctop119V.getMInOutID(), decimalFormat));
			if (!isEmpty(xmlCctop119V.getAddress1()))
			{
				cctop119V.setAddress1(normalize(xmlCctop119V.getAddress1()));
			}
			else
			{
				if (isEmpty(xmlCctop119V.getAddress2()))
				{
					throw new RuntimeCamelException(xmlCctop119V + " must have at least one filled address");
				}
				cctop119V.setAddress1(normalize(xmlCctop119V.getAddress2()));
			}
			cctop119V.setAddress2(normalize(xmlCctop119V.getAddress2()));
			cctop119V.setCity(xmlCctop119V.getCity());
			cctop119V.setCountryCode(xmlCctop119V.getCountryCode());
			cctop119V.setEancomLocationtype(xmlCctop119V.getEancomLocationtype());
			cctop119V.setFax(xmlCctop119V.getFax());
			cctop119V.setGln(xmlCctop119V.getGLN());
			cctop119V.setName(normalize(xmlCctop119V.getName()));
			cctop119V.setName2(normalize(xmlCctop119V.getName2()));
			cctop119V.setPhone(xmlCctop119V.getPhone());
			cctop119V.setPostal(xmlCctop119V.getPostal());
			cctop119V.setValue(xmlCctop119V.getValue());
			cctop119V.setVaTaxID(xmlCctop119V.getVATaxID().trim());
			cctop119V.setReferenceNo(xmlCctop119V.getReferenceNo());
			cctop119VList.add(cctop119V);
		}

		return cctop119VList;
	}

	private List<Cctop120V> createCctop120VList(final List<EDICctop120VType> xmlCctop120VList, final DecimalFormat decimalFormat)
	{
		final List<Cctop120V> cctop120VList = new ArrayList<>();

		for (final EDICctop120VType xmlCctop120V : xmlCctop120VList)
		{
			final Cctop120V cctop120V = new Cctop120V();
			cctop120V.setcInvoiceID(formatNumber(xmlCctop120V.getCInvoiceID(), decimalFormat));
			cctop120V.setIsoCode(xmlCctop120V.getISOCode());
			cctop120V.setNetdate(toDate(xmlCctop120V.getNetdate()));
			cctop120V.setNetDays(formatNumber(xmlCctop120V.getNetDays(), decimalFormat));
			cctop120V.setSinglevat(formatNumber(xmlCctop120V.getSinglevat(), decimalFormat));
			cctop120V.setTaxfree(Util.getADBooleanString(xmlCctop120V.getTaxfree()));
			cctop120VList.add(cctop120V);
		}

		return cctop120VList;
	}

	private List<Cctop140V> createCctop140VList(final List<EDICctop140VType> xmlCctop140VList, final DecimalFormat decimalFormat)
	{
		final List<Cctop140V> cctop140VList = new ArrayList<>();

		for (final EDICctop140VType xmlCctop140V : xmlCctop140VList)
		{
			final Cctop140V cctop140V = new Cctop140V();
			cctop140V.setcInvoiceID(formatNumber(xmlCctop140V.getCInvoiceID(), decimalFormat));
			cctop140V.setDiscount(formatNumber(xmlCctop140V.getDiscount(), decimalFormat));
			cctop140V.setDiscountDate(toDate(xmlCctop140V.getDiscountDate()));
			cctop140V.setDiscountDays(formatNumber(xmlCctop140V.getDiscountDays(), decimalFormat));
			cctop140V.setRate(formatNumber(xmlCctop140V.getRate(), decimalFormat));
			cctop140VList.add(cctop140V);
		}

		return cctop140VList;
	}

	private List<Cctop901991V> createCctop901991VList(final List<EDICctop901991VType> xmlCctop901991VList, final DecimalFormat decimalFormat)
	{
		final List<Cctop901991V> cctop901991VList = new ArrayList<>();

		for (final EDICctop901991VType xmlCctop901991V : xmlCctop901991VList)
		{
			final Cctop901991V cctop901991V = new Cctop901991V();
			cctop901991V.setcInvoiceID(formatNumber(xmlCctop901991V.getCInvoiceID(), decimalFormat));
			cctop901991V.setRate(formatNumber(xmlCctop901991V.getRate(), decimalFormat));
			cctop901991V.setTaxAmt(formatNumber(xmlCctop901991V.getTaxAmt(), decimalFormat));
			cctop901991V.setTaxBaseAmt(formatNumber(xmlCctop901991V.getTaxBaseAmt(), decimalFormat));
			cctop901991V.setTotalAmt(formatNumber(xmlCctop901991V.getTotalAmt(), decimalFormat));
			cctop901991V.setESRNumber(xmlCctop901991V.getReferenceNo());

			if (xmlCctop901991V.getTaxAmt() != null && xmlCctop901991V.getTaxBaseAmt() != null)
			{
				final BigDecimal taxSum = xmlCctop901991V.getTaxAmt().add(xmlCctop901991V.getTaxBaseAmt());
				cctop901991V.setTaxAmtSumTaxBaseAmt(formatNumber(taxSum, decimalFormat));
			}

			cctop901991VList.add(cctop901991V);
		}

		return cctop901991VList;
	}

	private List<CctopInvoice500V> createCctopInvoice500VList(final List<EDICctopInvoic500VType> xmlCctopInvoic500VList, final DecimalFormat decimalFormat)
	{
		final List<CctopInvoice500V> cctopInvoice500VList = new ArrayList<>();

		for (final EDICctopInvoic500VType xmlCctopInvoic500V : xmlCctopInvoic500VList)
		{
			if (isEmpty(xmlCctopInvoic500V.getUPCCU()))
			{
				throw new RuntimeCamelException(xmlCctopInvoic500V + " must have a CU-UPC");
			}

			final CctopInvoice500V cctopInvoice500V = new CctopInvoice500V();
			cctopInvoice500V.setcInvoiceID(formatNumber(xmlCctopInvoic500V.getCInvoiceID(), decimalFormat));
			cctopInvoice500V.setEancomUom(xmlCctopInvoic500V.getEanComUOM());
			cctopInvoice500V.setIsoCode(xmlCctopInvoic500V.getISOCode());
			cctopInvoice500V.setLine(formatNumber(xmlCctopInvoic500V.getLine(), decimalFormat));
			cctopInvoice500V.setLineNetAmt(formatNumber(xmlCctopInvoic500V.getLineNetAmt(), decimalFormat));
			cctopInvoice500V.setName(normalize(xmlCctopInvoic500V.getName()));
			cctopInvoice500V.setName2(normalize(xmlCctopInvoic500V.getName2()));
			cctopInvoice500V.setPriceActual(formatNumber(xmlCctopInvoic500V.getPriceActual(), decimalFormat));
			cctopInvoice500V.setPriceList(formatNumber(xmlCctopInvoic500V.getPriceList(), decimalFormat));
			cctopInvoice500V.setQtyInvoiced(formatNumber(xmlCctopInvoic500V.getQtyInvoiced(), decimalFormat));
			cctopInvoice500V.setRate(formatNumber(xmlCctopInvoic500V.getRate(), decimalFormat));
			cctopInvoice500V.setTaxfree(Util.getADBooleanString(xmlCctopInvoic500V.getTaxfree()));
			cctopInvoice500V.setUpc(xmlCctopInvoic500V.getUPCCU());
			cctopInvoice500V.setValue(xmlCctopInvoic500V.getValue());
			cctopInvoice500V.setVendorProductNo(xmlCctopInvoic500V.getVendorProductNo());
			cctopInvoice500V.setProductDescription(xmlCctopInvoic500V.getProductDescription());
			if (xmlCctopInvoic500V.getOrderLine() == null)
			{
				throw new RuntimeCamelException(xmlCctopInvoic500V + " must reference an orderline");
			}
			else
			{
				// task 09182: we output the order's POReference which might be different from the POReference of the invoice header.
				cctopInvoice500V.setOrderPOReference(xmlCctopInvoic500V.getOrderPOReference());
				cctopInvoice500V.setOrderLine(xmlCctopInvoic500V.getOrderLine().toString());
			}
			cctopInvoice500V.setTaxAmount(formatNumber(xmlCctopInvoic500V.getTaxAmtInfo(), decimalFormat));

			cctopInvoice500V.setEancomPriceUom(xmlCctopInvoic500V.getEanComPriceUOM());

			final BigDecimal lineGrossAmt;
			if (xmlCctopInvoic500V.getRate() == null || xmlCctopInvoic500V.getRate().signum() == 0)
			{
				lineGrossAmt = BigDecimal.ZERO;
			}
			else if (xmlCctopInvoic500V.getPriceActual() == null || xmlCctopInvoic500V.getPriceActual().signum() == 0)
			{
				lineGrossAmt = BigDecimal.ZERO;
			}
			else
			{
				final BigDecimal hundret = new BigDecimal("100");
				lineGrossAmt = hundret.add(xmlCctopInvoic500V.getRate())
						.divide(hundret, RoundingMode.UNNECESSARY)
						.multiply(xmlCctopInvoic500V.getLineNetAmt()).setScale(3, RoundingMode.HALF_UP);
			}
			cctopInvoice500V.setLineGrossAmt(formatNumber(lineGrossAmt, decimalFormat));

			cctopInvoice500V.setLeergut(xmlCctopInvoic500V.getLeergut());

			cctopInvoice500VList.add(cctopInvoice500V);
		}

		return cctopInvoice500VList;
	}
}
