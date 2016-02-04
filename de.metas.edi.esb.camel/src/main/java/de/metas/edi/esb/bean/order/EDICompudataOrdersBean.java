package de.metas.edi.esb.bean.order;

/*
 * #%L
 * de.metas.edi.esb
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


import static de.metas.edi.esb.commons.Util.resolveGenericLookup;
import static de.metas.edi.esb.commons.Util.trimString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Property;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.COrderDeliveryRuleEnum;
import de.metas.edi.esb.jaxb.COrderDeliveryViaRuleEnum;
import de.metas.edi.esb.jaxb.EDIADOrgLookupBPLGLNVType;
import de.metas.edi.esb.jaxb.EDICBPartnerLookupBPLGLNVType;
import de.metas.edi.esb.jaxb.EDIImpADInputDataSourceLookupINType;
import de.metas.edi.esb.jaxb.EDIImpCBPartnerLocationLookupGLNType;
import de.metas.edi.esb.jaxb.EDIImpCCurrencyLookupISOCodeType;
import de.metas.edi.esb.jaxb.EDIImpCOLCandType;
import de.metas.edi.esb.jaxb.EDIImpCUOMLookupUOMSymbolType;
import de.metas.edi.esb.jaxb.EDIMHUPIItemProductLookupUPCVType;
import de.metas.edi.esb.jaxb.EDIMProductLookupUPCVType;
import de.metas.edi.esb.jaxb.ObjectFactory;
import de.metas.edi.esb.jaxb.ReplicationEventEnum;
import de.metas.edi.esb.jaxb.ReplicationModeEnum;
import de.metas.edi.esb.jaxb.ReplicationTypeEnum;
import de.metas.edi.esb.pojo.order.compudata.H000;
import de.metas.edi.esb.pojo.order.compudata.H100;
import de.metas.edi.esb.pojo.order.compudata.H110;
import de.metas.edi.esb.pojo.order.compudata.H120;
import de.metas.edi.esb.pojo.order.compudata.H130;
import de.metas.edi.esb.pojo.order.compudata.P100;
import de.metas.edi.esb.pojo.order.compudata.P110;
import de.metas.edi.esb.pojo.order.compudata.T100;
import de.metas.edi.esb.route.imports.EDIOrderRoute;

public class EDICompudataOrdersBean
{
	public static final String METHOD_createXMLDocument = "createXMLDocument";

	private static final ObjectFactory factory = Constants.JAXB_ObjectFactory;

	public List<Message> createXMLDocument(@Body final List<Object> ediLines,
			@Property(value = Exchange.FILE_NAME) final String CamelFileName,
			@Property(value = EDIOrderRoute.EDI_ORDER_EDIMessageDatePattern) final String EDIMessageDatePattern,
			@Property(value = EDIOrderRoute.EDI_ORDER_ADClientValue) final String ADClientValue,
			@Property(value = EDIOrderRoute.EDI_ORDER_ADOrgID) final BigInteger ADOrgID,
			@Property(value = EDIOrderRoute.EDI_ORDER_ADInputDataDestination_InternalName) final String ADInputDataDestination_InternalName,
			@Property(value = EDIOrderRoute.EDI_ORDER_ADInputDataSourceID) final BigInteger ADInputDataSourceID,
			@Property(value = EDIOrderRoute.EDI_ORDER_ADUserEnteredByID) final BigInteger ADUserEnteredByID,
			@Property(value = EDIOrderRoute.EDI_ORDER_DELIVERY_RULE) final String DeliveryRule,
			@Property(value = EDIOrderRoute.EDI_ORDER_DELIVERY_VIA_RULE) final String DeliveryViaRule)
	{
		final List<OrderEDI> ediDocuments = getEDIDocumentObjects(ediLines);

		final EDIConfigurationContext ctx = new EDIConfigurationContext(CamelFileName,
				EDIMessageDatePattern, ADClientValue, ADOrgID,
				ADInputDataDestination_InternalName, ADInputDataSourceID, ADUserEnteredByID, DeliveryRule, DeliveryViaRule);

		final List<Message> olCandMessages = createOLCandMessages(ctx, ediDocuments);

		return olCandMessages;
	}

	private List<OrderEDI> getEDIDocumentObjects(final List<Object> ediLines)
	{
		final List<OrderEDI> ediDocuments = new ArrayList<OrderEDI>();

		OrderEDI orderEDITmp = null;
		OrderHeader orderHeaderTmp = null;
		OrderLine orderLineTmp = null;
		for (final Object ediLine : ediLines)
		{
			// treat the EDI header
			if (ediLine instanceof H000)
			{
				orderEDITmp = new OrderEDI((H000)ediLine);
				ediDocuments.add(orderEDITmp);
			}

			// each H100 line is an OrderHeader
			if (ediLine instanceof H100)
			{
				orderHeaderTmp = new OrderHeader((H100)ediLine);
				orderEDITmp.addOrderHeader(orderHeaderTmp);
			}

			if (ediLine instanceof H110)
			{
				orderHeaderTmp.addH110((H110)ediLine);
			}

			if (ediLine instanceof H120)
			{
				orderHeaderTmp.addH120((H120)ediLine);
			}

			if (ediLine instanceof H130)
			{
				orderHeaderTmp.addH130((H130)ediLine);
			}

			if (ediLine instanceof P100)
			{
				orderLineTmp = new OrderLine((P100)ediLine);
				orderHeaderTmp.addOrderLine(orderLineTmp);
			}

			if (ediLine instanceof P110)
			{
				orderLineTmp.addP110((P110)ediLine);
			}

			if (ediLine instanceof T100)
			{
				orderHeaderTmp.addT100((T100)ediLine);
			}
		}

		return ediDocuments;
	}

	private List<Message> createOLCandMessages(final EDIConfigurationContext ctx, final List<OrderEDI> ediDocuments)
	{
		final List<Message> olCandMessages = new ArrayList<Message>();
		for (final OrderEDI ediDocument : ediDocuments)
		{
			final H000 h000 = ediDocument.getH000();
			for (final OrderHeader orderHeader : ediDocument.getOrderHeaders())
			{
				for (final OrderLine orderLine : orderHeader.getOrderLines())
				{
					final Message olCandMessage = createOLCandMessage(ctx, h000, orderHeader, orderLine);
					olCandMessages.add(olCandMessage);
				}
			}
		}

		return olCandMessages;
	}

	private Message createOLCandMessage(final EDIConfigurationContext ctx, final H000 h000, final OrderHeader orderHeader, final OrderLine orderLine)
	{
		final EDIImpCOLCandType olcand = EDICompudataOrdersBean.factory.createEDIImpCOLCandType();

		// set ReplicationTrx attribute
		olcand.setTrxNameAttr(ctx.getCamelFileName());

		// configured
		olcand.setADClientValueAttr(ctx.getADClientValue());
		olcand.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		olcand.setReplicationModeAttr(ReplicationModeEnum.Table);
		olcand.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		olcand.setVersionAttr(Constants.EXP_FORMAT_GENERIC_VERSION);

		// AD_Org_ID no longer taken from context, but via lookup from H100.SupplierID
		// olcand.setADOrgID(ctx.getADOrgID());

		final H100 h100 = orderHeader.getH100();
		{
			final String orgGLN = trimString(h100.getSupplierID());
			final EDIADOrgLookupBPLGLNVType orgLookup = resolveGenericLookup(EDIADOrgLookupBPLGLNVType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(orgGLN));
			olcand.setADOrgID(orgLookup);
		}

		// AD_DataDestination_ID lookup
		{
			final EDIImpADInputDataSourceLookupINType dataDestinationLookup = resolveGenericLookup(EDIImpADInputDataSourceLookupINType.class,
					Constants.LOOKUP_TEMPLATE_InternalName.createMandatoryValueLookup(ctx.getADInputDataDestination_InternalName()));
			olcand.setADDataDestinationID(dataDestinationLookup);
		}
		olcand.setADInputDataSourceID(ctx.getADInputDataSourceID());
		olcand.setADUserEnteredByID(ctx.getADUserEnteredByID());

		final P100 p100 = orderLine.getP100();

		// extracted from EDI document
		// flat
		// TODO check if we need msgNo and add it to a dedicated field. SequenceNoAttr is not read, it's only used for messages coming out of ADempiere.
		// final String messageNoStr = p100.getMessageNo();
		// if (messageNoStr != null)
		// {
		// try
		// {
		// final BigInteger sequenceNoAttr = new BigInteger(trimString(messageNoStr));
		// olcand.setSequenceNoAttr(sequenceNoAttr);
		// }
		// catch (final NumberFormatException e)
		// {
		// throw new RuntimeCamelException("Invalid reference number " + messageNoStr, e);
		// }
		// }
		// else
		// {
		// throw new RuntimeCamelException("Reference number cannot be null for " + h000);
		// }

		final String dateCandidateStr = trimString(h100.getMessageDate()); // TODO Check if this is it
		olcand.setDateCandidate(Util.createCalendarDate(dateCandidateStr, ctx.getEDIMessageDatePattern()));

		// task 06269: also get the desired delivery date from the customer (if set)
		final String datePromisedStr = trimString(h100.getDeliveryDate());
		olcand.setDatePromised(Util.createCalendarDate(datePromisedStr, ctx.getEDIMessageDatePattern()));

		final COrderDeliveryRuleEnum deliveryRule = COrderDeliveryRuleEnum.fromValue(ctx.getDeliveryRule());
		olcand.setDeliveryRule(deliveryRule);
		final COrderDeliveryViaRuleEnum deliveryViaRule = COrderDeliveryViaRuleEnum.fromValue(ctx.getDeliveryViaRule());
		olcand.setDeliveryViaRule(deliveryViaRule);

		// C_OLCand -> C_Order/C_OrderLine aggregation references
		final String poReference = trimString(h100.getDocumentNo());
		olcand.setPOReference(poReference);

		final BigInteger line = new BigInteger(trimString(p100.getPositionNo()));
		olcand.setLine(line);

		final BigDecimal qtyItemCapacity; // following block decides the value of QtyItemCapacity
		{
			final String CUPerTU = trimString(p100.getCuperTU());
			if (CUPerTU != null)
			{
				qtyItemCapacity = new BigDecimal(CUPerTU);
			}
			else
			{
				qtyItemCapacity = BigDecimal.ONE; // default assumption
			}
		}
		olcand.setQtyItemCapacity(qtyItemCapacity);

		final BigDecimal qty = new BigDecimal(trimString(p100.getOrderQty())).multiply(qtyItemCapacity); // qty = orderQty * CUPerTU
		olcand.setQty(qty);

		final BigDecimal priceEntered = new BigDecimal(trimString(p100.getBuyerPrice()));
		olcand.setPriceEntered(priceEntered);
		// Note that this price shall not be propagated to the order line, so C_OLCand.
		// IsManual shall not be set to Y.
		// We only need this price in ADempiere so we can detect discrepancies
		olcand.setIsManualPrice("N");

		//
		// ProductDescription
		final String productDescription = trimString(p100.getArtDescription());
		olcand.setProductDescription(productDescription);

		//
		// This is also the GLN of used in BPartner lookup for store
		final String storeNumberGLN = trimString(h100.getStoreNumber());

		// BuyerID = Main GLN of C_BPartner and C_BPartner_Location
		final String buyerGLN = trimString(h100.getBuyerID());
		{
			final EDICBPartnerLookupBPLGLNVType buyerBPartner = resolveGenericLookup(EDICBPartnerLookupBPLGLNVType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(buyerGLN),
					Constants.LOOKUP_TEMPLATE_StoreGLN.createNonMandatoryValueLookup(storeNumberGLN));
			olcand.setCBPartnerID(buyerBPartner);

			final EDIImpCBPartnerLocationLookupGLNType buyerLocation = resolveGenericLookup(EDIImpCBPartnerLocationLookupGLNType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(buyerGLN),
					Constants.LOOKUP_TEMPLATE_C_BPartner_ID.createMandatoryValueLookup(buyerBPartner));
			olcand.setCBPartnerLocationID(buyerLocation);
		}

		// DeliveryID = GLN of HandOver_Partner and HandOver_Location
		final String deliveryGLN = trimString(h100.getDeliveryID());
		{
			final EDICBPartnerLookupBPLGLNVType deliveryBPartner = resolveGenericLookup(EDICBPartnerLookupBPLGLNVType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(deliveryGLN),
					Constants.LOOKUP_TEMPLATE_StoreGLN.createNonMandatoryValueLookup(storeNumberGLN)); // TODO check specs
			olcand.setHandOverPartnerID(deliveryBPartner);

			final EDIImpCBPartnerLocationLookupGLNType deliveryLocation = resolveGenericLookup(EDIImpCBPartnerLocationLookupGLNType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createNonMandatoryValueLookup(deliveryGLN),
					Constants.LOOKUP_TEMPLATE_C_BPartner_ID.createMandatoryValueLookup(deliveryBPartner));
			olcand.setHandOverLocationID(deliveryLocation);
		}

		// StoreNumber = GLN of DropShip_BPartner and DropShip_Location
		{
			final EDICBPartnerLookupBPLGLNVType storeBPartner = resolveGenericLookup(EDICBPartnerLookupBPLGLNVType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(buyerGLN),
					Constants.LOOKUP_TEMPLATE_StoreGLN.createMandatoryValueLookup(storeNumberGLN));
			olcand.setDropShipBPartnerID(storeBPartner);

			final EDIImpCBPartnerLocationLookupGLNType storeLocation = resolveGenericLookup(EDIImpCBPartnerLocationLookupGLNType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createNonMandatoryValueLookup(storeNumberGLN),
					Constants.LOOKUP_TEMPLATE_C_BPartner_ID.createMandatoryValueLookup(storeBPartner));
			olcand.setDropShipLocationID(storeLocation);
		}

		// InvoiceID = GLN of Bill_BPartner and Bill_Location
		final String invoiceGLN = trimString(h100.getInvoiceID());
		{
			final EDICBPartnerLookupBPLGLNVType invoiceBPartner = resolveGenericLookup(EDICBPartnerLookupBPLGLNVType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createMandatoryValueLookup(invoiceGLN),
					Constants.LOOKUP_TEMPLATE_StoreGLN.createNonMandatoryValueLookup(storeNumberGLN));
			olcand.setBillBPartnerID(invoiceBPartner);

			final EDIImpCBPartnerLocationLookupGLNType invoiceLocation = resolveGenericLookup(EDIImpCBPartnerLocationLookupGLNType.class,
					Constants.LOOKUP_TEMPLATE_GLN.createNonMandatoryValueLookup(invoiceGLN),
					Constants.LOOKUP_TEMPLATE_C_BPartner_ID.createMandatoryValueLookup(invoiceBPartner));
			olcand.setBillLocationID(invoiceLocation);
		}

		final String upc = trimString(p100.getEanArtNo());
		//
		// M_Product lookup
		{
			final EDIMProductLookupUPCVType productLookup = resolveGenericLookup(EDIMProductLookupUPCVType.class,
					Constants.LOOKUP_TEMPLATE_UPC.createMandatoryValueLookup(upc),
					Constants.LOOKUP_TEMPLATE_GLN.createNonMandatoryValueLookup(buyerGLN));
			olcand.setMProductID(productLookup);
		}

		// M_HU_PI_Item_Product lookup
		{
			final EDIMHUPIItemProductLookupUPCVType huPIIPLookup = resolveGenericLookup(EDIMHUPIItemProductLookupUPCVType.class,
					Constants.LOOKUP_TEMPLATE_UPC.createMandatoryValueLookup(upc),
					Constants.LOOKUP_TEMPLATE_GLN.createNonMandatoryValueLookup(buyerGLN));
			olcand.setMHUPIItemProductID(huPIIPLookup);
		}

		// UOM lookup
		{
			final String uom = trimString(p100.getOrderUnit());
			final EDIImpCUOMLookupUOMSymbolType uomLookup = resolveGenericLookup(EDIImpCUOMLookupUOMSymbolType.class,
					Constants.LOOKUP_TEMPLATE_X12DE355.createMandatoryValueLookup(uom));
			olcand.setCUOMID(uomLookup);
		}

		// Currency lookup
		{
			final String currency = trimString(p100.getCurrency());
			final EDIImpCCurrencyLookupISOCodeType currencyLookup = resolveGenericLookup(EDIImpCCurrencyLookupISOCodeType.class,
					Constants.LOOKUP_TEMPLATE_ISOCode.createMandatoryValueLookup(currency));
			olcand.setCCurrencyID(currencyLookup);
		}

		return Util.createJaxbMessage(EDICompudataOrdersBean.factory.createEDIImpCOLCand(olcand));
	}

	private static final class EDIConfigurationContext
	{
		private final String CamelFileName;

		private final String EDIMessageDatePattern;
		private final String ADClientValue;
		// private final BigInteger ADOrgID;
		private final String ADInputDataDestination_InternalName;
		private final BigInteger ADInputDataSourceID;
		private final BigInteger ADUserEnteredByID;
		private final String DeliveryRule;
		private final String DeliveryViaRule;

		public EDIConfigurationContext(final String CamelFileName,
				final String EDIMessageDatePattern, final String ADClientValue, final BigInteger ADOrgID,
				final String ADInputDataDestination_InternalName, final BigInteger ADInputDataSourceID, final BigInteger ADUserEnteredByID,
				final String DeliveryRule, final String DeliveryViaRule)
		{
			this.CamelFileName = CamelFileName;
			this.EDIMessageDatePattern = EDIMessageDatePattern;
			this.ADClientValue = ADClientValue;
			// this.ADOrgID = ADOrgID;
			this.ADInputDataDestination_InternalName = ADInputDataDestination_InternalName;
			this.ADInputDataSourceID = ADInputDataSourceID;
			this.ADUserEnteredByID = ADUserEnteredByID;
			this.DeliveryRule = DeliveryRule;
			this.DeliveryViaRule = DeliveryViaRule;
		}

		public String getCamelFileName()
		{
			return CamelFileName;
		}

		public String getEDIMessageDatePattern()
		{
			return EDIMessageDatePattern;
		}

		public String getADClientValue()
		{
			return ADClientValue;
		}

		// public BigInteger getADOrgID()
		// {
		// return ADOrgID;
		// }

		public String getADInputDataDestination_InternalName()
		{
			return ADInputDataDestination_InternalName;
		}

		public BigInteger getADInputDataSourceID()
		{
			return ADInputDataSourceID;
		}

		public BigInteger getADUserEnteredByID()
		{
			return ADUserEnteredByID;
		}

		public String getDeliveryRule()
		{
			return DeliveryRule;
		}

		public String getDeliveryViaRule()
		{
			return DeliveryViaRule;
		}
	}
}
