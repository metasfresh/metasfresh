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

package de.metas.edi.esb.ordersimport;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.jaxb.metasfresh.COrderDeliveryRuleEnum;
import de.metas.edi.esb.jaxb.metasfresh.COrderDeliveryViaRuleEnum;
import de.metas.edi.esb.jaxb.metasfresh.EDIADOrgLookupBPLGLNVType;
import de.metas.edi.esb.jaxb.metasfresh.EDICBPartnerLookupBPLGLNVType;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpADInputDataSourceLookupINType;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpCBPartnerLocationLookupGLNType;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpCCurrencyLookupISOCodeType;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpCOLCandType;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpCUOMLookupUOMSymbolType;
import de.metas.edi.esb.jaxb.metasfresh.EDIMHUPIItemProductLookupUPCVType;
import de.metas.edi.esb.jaxb.metasfresh.EDIMProductLookupUPCVType;
import de.metas.edi.esb.jaxb.metasfresh.ObjectFactory;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationEventEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationModeEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationTypeEnum;
import de.metas.edi.esb.ordersimport.compudata.H100;
import de.metas.edi.esb.ordersimport.compudata.P100;
import lombok.NonNull;
import org.apache.camel.Body;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperty;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static de.metas.edi.esb.commons.Util.TimeIfNotSpecified.END_OF_DAY;
import static de.metas.edi.esb.commons.Util.TimeIfNotSpecified.START_OF_DAY;
import static de.metas.edi.esb.commons.Util.createCalendarDate;
import static de.metas.edi.esb.commons.Util.createJaxbMessage;
import static de.metas.edi.esb.commons.Util.isEmpty;
import static de.metas.edi.esb.commons.Util.resolveGenericLookup;
import static de.metas.edi.esb.commons.Util.stripTrailingDecimalZeros;
import static de.metas.edi.esb.commons.Util.trimString;

public abstract class AbstractEDIOrdersBean
{
	@Autowired
	private CamelContext camelContext;

	public static final String METHOD_createXMLDocument = "createXMLDocument";

	private static final ObjectFactory factory = Constants.JAXB_ObjectFactory;

	public List<Message> createXMLDocument(@Body final List<Object> ediLines,
			@ExchangeProperty(value = Exchange.FILE_NAME) final String camelFileName,
			@ExchangeProperty(value = AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern) final String EDIMessageDatePattern,
			@ExchangeProperty(value = AbstractEDIRoute.EDI_ORDER_ADClientValue) final String ADClientValue,
			@ExchangeProperty(value = AbstractEDIRoute.EDI_ORDER_ADOrgID) final BigInteger ADOrgID,
			@ExchangeProperty(value = AbstractEDIRoute.EDI_ORDER_ADInputDataDestination_InternalName) final String ADInputDataDestination_InternalName,
			@ExchangeProperty(value = AbstractEDIRoute.EDI_ORDER_ADInputDataSourceID) final BigInteger ADInputDataSourceID,
			@ExchangeProperty(value = AbstractEDIRoute.EDI_ORDER_ADUserEnteredByID) final BigInteger ADUserEnteredByID,
			@ExchangeProperty(value = AbstractEDIRoute.EDI_ORDER_DELIVERY_RULE) final String DeliveryRule,
			@ExchangeProperty(value = AbstractEDIRoute.EDI_ORDER_DELIVERY_VIA_RULE) final String DeliveryViaRule)
	{
		final List<OrderEDI> ediDocuments = convertToOrderEDIs(ediLines);

		final EDIConfigurationContext ctx = new EDIConfigurationContext(
				camelFileName,
				EDIMessageDatePattern,
				ADClientValue, ADOrgID,
				ADInputDataDestination_InternalName,
				ADInputDataSourceID,
				ADUserEnteredByID,
				DeliveryRule,
				DeliveryViaRule);

		return createOLCandMessages(ctx, ediDocuments);
	}

	protected abstract List<OrderEDI> convertToOrderEDIs(List<Object> ediLines);

	private List<Message> createOLCandMessages(
			@NonNull final EDIConfigurationContext ctx,
			@NonNull final List<OrderEDI> ediDocuments)
	{
		final List<Message> olCandMessages = new ArrayList<>();
		for (final OrderEDI ediDocument : ediDocuments)
		{
			for (final OrderHeader orderHeader : ediDocument.getOrderHeaders())
			{
				for (final OrderLine orderLine : orderHeader.getOrderLines())
				{
					final Message olCandMessage = createOLCandMessage(ctx, orderHeader, orderLine);
					olCandMessages.add(olCandMessage);
				}
			}
		}

		return olCandMessages;
	}

	private Message createOLCandMessage(
			final EDIConfigurationContext ctx,
			final OrderHeader orderHeader,
			final OrderLine orderLine)
	{
		final EDIImpCOLCandType olcand = AbstractEDIOrdersBean.factory.createEDIImpCOLCandType();

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

		final String dateCandidateStr = trimString(h100.getMessageDate());
		olcand.setDateCandidate(createCalendarDate(dateCandidateStr, ctx.getEDIMessageDatePattern(), START_OF_DAY));

		// task 06269: also get the desired delivery date from the customer (if set)
		final String datePromisedStr = trimString(h100.getDeliveryDate());
		olcand.setDatePromised(createCalendarDate(datePromisedStr, ctx.getEDIMessageDatePattern(), END_OF_DAY));

		final COrderDeliveryRuleEnum deliveryRule = COrderDeliveryRuleEnum.fromValue(ctx.getDeliveryRule());
		olcand.setDeliveryRule(deliveryRule);
		final COrderDeliveryViaRuleEnum deliveryViaRule = COrderDeliveryViaRuleEnum.fromValue(ctx.getDeliveryViaRule());
		olcand.setDeliveryViaRule(deliveryViaRule);

		// C_OLCand -> C_Order/C_OrderLine aggregation references
		final String poReference = trimString(h100.getDocumentNo());
		olcand.setPOReference(poReference);

		final BigInteger line = new BigInteger(trimString(p100.getPositionNo()));
		olcand.setLine(line);

		final BigDecimal orderQty = new BigDecimal(trimString(p100.getOrderQty()));
		final String CUperTU = trimString(p100.getCUperTU());
		if (!isEmpty(CUperTU))
		{
			final BigDecimal qtyItemCapacity = new BigDecimal(CUperTU);
			olcand.setQtyItemCapacity(stripTrailingDecimalZeros(qtyItemCapacity));

			// we have a bunch concrete examples where the CU-qty is orderQty * qtyItemCapacity
			olcand.setQtyEntered(stripTrailingDecimalZeros(orderQty.multiply(qtyItemCapacity)));
		}
		else
		{ // if we don't have the CUperTU-info, then don't guess! metasfresh might have it in its masterdata
			olcand.setQtyItemCapacity(null);
			olcand.setQtyEntered(stripTrailingDecimalZeros(orderQty));
		}

		// UOM lookup
		final String uom = trimString(p100.getOrderUnit());
		final EDIImpCUOMLookupUOMSymbolType uomLookup = resolveGenericLookup(EDIImpCUOMLookupUOMSymbolType.class,
				Constants.LOOKUP_TEMPLATE_X12DE355.createMandatoryValueLookup(uom));
		olcand.setCUOMID(uomLookup);

		final BigDecimal priceEntered = new BigDecimal(trimString(p100.getBuyerPrice()));
		olcand.setPriceEntered(priceEntered);

		// Note that this price shall not be propagated to the order line, so C_OLCand.IsManual shall not be set to Y.
		// We only need this price in metasfresh so we can detect discrepancies
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

		// Currency lookup
		{
			final String currency = trimString(p100.getCurrency());
			final EDIImpCCurrencyLookupISOCodeType currencyLookup = resolveGenericLookup(EDIImpCCurrencyLookupISOCodeType.class,
					Constants.LOOKUP_TEMPLATE_ISOCode.createMandatoryValueLookup(currency));
			olcand.setCCurrencyID(currencyLookup);
		}

		return createJaxbMessage(
				AbstractEDIOrdersBean.factory.createEDIImpCOLCand(olcand),
				camelContext);
	}

	protected static final class EDIConfigurationContext
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
				final String EDIMessageDatePattern,
				final String ADClientValue,
				final BigInteger ADOrgID,
				final String ADInputDataDestination_InternalName,
				final BigInteger ADInputDataSourceID,
				final BigInteger ADUserEnteredByID,
				final String DeliveryRule,
				final String DeliveryViaRule)
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
