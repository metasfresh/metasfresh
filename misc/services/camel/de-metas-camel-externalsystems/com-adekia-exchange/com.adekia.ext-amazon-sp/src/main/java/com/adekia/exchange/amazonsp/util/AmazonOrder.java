/*
 * #%L
 * ext-amazon-sp
 * %%
 * Copyright (C) 2022 Adekia
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

package com.adekia.exchange.amazonsp.util;

import com.adekia.exchange.amazonsp.client.orders.model.Address;
import com.adekia.exchange.amazonsp.client.orders.model.Money;
import com.adekia.exchange.amazonsp.client.orders.model.Order;
import com.adekia.exchange.amazonsp.client.orders.model.OrderItem;
import com.adekia.exchange.amazonsp.client.orders.model.OrderItemList;
import com.adekia.exchange.amazonsp.client.orders.model.PaymentExecutionDetailItem;
import com.helger.commons.string.StringHelper;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.AddressLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ContactType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.CountryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.DeliveryTermsType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.DeliveryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ItemIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.LineItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.MonetaryTotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.OrderLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyNameType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PaymentTermsType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.TransactionConditionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_23.AmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_23.PaymentMeansIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_23.SpecialTermsType;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class AmazonOrder {

    public static OrderType toOrderType(Order amazonOrder, OrderItemList amazonOrderItemList) {
        if (amazonOrder == null)
            throw new IllegalStateException("Order Shouldn't be null");
        if (amazonOrderItemList == null
                || amazonOrderItemList.size() == 0)
            throw new IllegalStateException("Order Should have lines");

        OrderType order = new OrderType();

        if (!StringHelper.hasText(amazonOrder.getAmazonOrderId()))
            raiseRequiredException("AmazonOrderId");
        order.setSalesOrderID(AmazonOrderConstant.AMAZON_PREFIX + amazonOrder.getSellerOrderId());
        order.setUUID(amazonOrder.getAmazonOrderId());
        if (amazonOrder.getPurchaseDate() == null)
            raiseRequiredException("PurchaseDate");
        order.setIssueDate(OffsetDateTime.parse(amazonOrder.getPurchaseDate()).toLocalDate());
        if (amazonOrder.getOrderStatus() == null)
            raiseRequiredException("OrderStatus");
        TransactionConditionsType tct = new TransactionConditionsType();
        tct.setActionCode(amazonOrder.getOrderStatus().getValue());
        order.getTransactionConditions();
        if (amazonOrder.getOrderType() != null)
            order.setOrderTypeCode(amazonOrder.getOrderType().getValue());

        if (amazonOrder.getBuyerInfo() != null)
            order.setCustomerReference(amazonOrder.getBuyerInfo().getPurchaseOrderNumber());

        MonetaryTotalType mtt = new MonetaryTotalType();
        mtt.setTaxInclusiveAmount(toBigDecimal(amazonOrder.getOrderTotal()));
        order.setAnticipatedMonetaryTotal(mtt);
        order.setDocumentCurrencyCode(toCurrency(amazonOrder.getOrderTotal()));

        if (StringHelper.hasText(amazonOrder.getShipmentServiceLevelCategory())) {
            DeliveryTermsType dtt = new DeliveryTermsType();
            SpecialTermsType stt = new SpecialTermsType();
            stt.setValue(amazonOrder.getShipmentServiceLevelCategory());
            dtt.addSpecialTerms(stt);
            order.addDeliveryTerms(dtt);
        }

        // Handle Supplier Party
        SupplierPartyType spt = new SupplierPartyType();
        PartyType ptSupplier = new PartyType();
        if (!StringHelper.hasText(amazonOrder.getMarketplaceId()))
            raiseRequiredException("MarketplaceId");
        ptSupplier.setEndpointID(amazonOrder.getMarketplaceId());
        if (amazonOrder.getBuyerInfo() != null) {
            PartyNameType pnt = new PartyNameType();
            pnt.setName(amazonOrder.getBuyerInfo().getBuyerName());
            ptSupplier.addPartyName(pnt);

            ContactType ct = new ContactType();
            ct.setElectronicMail(amazonOrder.getBuyerInfo().getBuyerEmail());
            ct.setName(amazonOrder.getBuyerInfo().getBuyerName());

            ptSupplier.setContact(ct);
        }
        if (amazonOrder.getBuyerTaxInformation() != null) {
            PartyIdentificationType pit = new PartyIdentificationType();
            pit.setID(amazonOrder.getBuyerTaxInformation().getBuyerTaxRegistrationId());
            ptSupplier.addPartyIdentification(pit);
        }
        spt.setParty(ptSupplier);
        order.setSellerSupplierParty(spt);

        // Handle Customer Party
        CustomerPartyType cpt = null;
        PartyType pt = null;
        if (amazonOrder.getBuyerInfo() != null) {
            cpt = new CustomerPartyType();
            pt = new PartyType();

            ContactType ct = new ContactType();
            if (amazonOrder.getShippingAddress() != null)
                ct.setTelephone(amazonOrder.getShippingAddress().getPhone());
            ct.setElectronicMail(amazonOrder.getBuyerInfo().getBuyerEmail());
            pt.setContact(ct);

            PartyNameType pnt = new PartyNameType();
            pnt.setName(amazonOrder.getBuyerInfo().getBuyerName());
            pt.addPartyName(pnt);
            cpt.setParty(pt);

            if (amazonOrder.getShippingAddress() != null)
                pt.setPostalAddress(getAddressType(amazonOrder.getShippingAddress()));

        }

        if (amazonOrder.getBuyerTaxInformation() != null) {
            if (cpt == null) {
                cpt = new CustomerPartyType();
                pt = new PartyType();
            }
            PartyIdentificationType pit = new PartyIdentificationType();
            pit.setID(amazonOrder.getBuyerTaxInformation().getBuyerTaxRegistrationId());
            pt.addPartyIdentification(pit);
        }
        if (cpt != null) {
            cpt.setParty(pt);
            order.setBuyerCustomerParty(cpt);
        }

        // Handle Delivery
        DeliveryType delivery = new DeliveryType();
        if (amazonOrder.getEarliestShipDate() != null)
            delivery.setActualDeliveryDate(OffsetDateTime.parse(amazonOrder.getPurchaseDate()).toLocalDate());
        if (amazonOrder.getFulfillmentChannel() != null && StringHelper.hasText(amazonOrder.getFulfillmentChannel().getValue())) {
            PartyType ptCarrier = new PartyType();
            PartyNameType ptt = new PartyNameType();
            ptt.setName(amazonOrder.getFulfillmentChannel().getValue());
            ptCarrier.addPartyName(ptt);
            delivery.setCarrierParty(ptCarrier);
        }
        if (StringHelper.hasText(amazonOrder.getShipmentServiceLevelCategory())) {
            DeliveryTermsType dtt = new DeliveryTermsType();
            SpecialTermsType stt = new SpecialTermsType();
            stt.setValue(amazonOrder.getShipmentServiceLevelCategory());
            dtt.addSpecialTerms(stt);
            delivery.addDeliveryTerms(dtt);
        }
        if (amazonOrder.getShippingAddress() != null) {
            delivery.setDeliveryAddress(getAddressType(amazonOrder.getShippingAddress()));
        }

        order.addDelivery(delivery);

        // Handle Payments
        if (amazonOrder.getPaymentExecutionDetail() != null) {
            int i = 1;
            for (PaymentExecutionDetailItem pedi : amazonOrder.getPaymentExecutionDetail()) {
                PaymentTermsType ptt = new PaymentTermsType();
                ptt.setPaymentDueDate(OffsetDateTime.parse(amazonOrder.getPurchaseDate()).toLocalDate());
                AmountType at = new AmountType();
                at.setValue(toBigDecimal(pedi.getPayment()));
                at.setCurrencyID(pedi.getPayment().getCurrencyCode());
                ptt.setAmount(at);
                PaymentMeansIDType pmit = new PaymentMeansIDType();
                pmit.setValue(pedi.getPaymentMethod());
                ptt.addPaymentMeansID(pmit);
                order.addPaymentTerms(ptt);
            }
        }

        if (amazonOrderItemList != null) {
            for (OrderItem amazonOrderItem : amazonOrderItemList) {
                OrderLineType orderLine = new OrderLineType();
                LineItemType lineItem = new LineItemType();

                if (!StringHelper.hasText(amazonOrderItem.getOrderItemId()))
                    raiseRequiredException("OrderItemId");
                lineItem.setID(amazonOrderItem.getOrderItemId());

                if (!StringHelper.hasText(amazonOrderItem.getASIN()))
                    raiseRequiredException("ASIN");
                ItemType item = new ItemType();
                item.setName(amazonOrderItem.getTitle());
                ItemIdentificationType iit = new ItemIdentificationType();
                iit.setID(amazonOrderItem.getASIN());
                item.setSellersItemIdentification(iit);

                lineItem.setItem(item);

                lineItem.setQuantity(BigDecimal.valueOf(amazonOrderItem.getQuantityOrdered()));

                //  ItemPrice  + ShippingPrice - ShippingDiscount + CODFee - CODFeeDiscount - PromotionDiscount
                //	ItemTax + ShippingTax - ShippingDiscountTax - PromotionDiscountTAx

                BigDecimal itemPrice = toBigDecimal(amazonOrderItem.getItemPrice());
                BigDecimal itemTax = toBigDecimal(amazonOrderItem.getItemTax());
                BigDecimal shippingPrice = toBigDecimal(amazonOrderItem.getShippingPrice());
                BigDecimal shippingTax = toBigDecimal(amazonOrderItem.getShippingTax());
                BigDecimal shippingDiscount = toBigDecimal(amazonOrderItem.getShippingDiscount());
                BigDecimal shippingDiscountTax = toBigDecimal(amazonOrderItem.getShippingDiscountTax());
                BigDecimal promotionDiscount = toBigDecimal(amazonOrderItem.getPromotionDiscount());
                BigDecimal promotionDiscountTax = toBigDecimal(amazonOrderItem.getPromotionDiscountTax());
                BigDecimal codFee = toBigDecimal(amazonOrderItem.getCoDFee());
                BigDecimal codFeeDiscount = toBigDecimal(amazonOrderItem.getCoDFeeDiscount());

                BigDecimal netAmount = itemPrice
                        .add(shippingPrice)
                        .subtract(shippingDiscount)
                        .add(codFee)
                        .subtract(codFeeDiscount)
                        .subtract(promotionDiscount);
                PriceType netPrice = new PriceType();
                netPrice.setPriceAmount(netAmount);
                lineItem.setPrice(netPrice);

                BigDecimal taxAmount = itemTax
                        .add(shippingTax)
                        .subtract(shippingDiscountTax)
                        .subtract(promotionDiscountTax);
                lineItem.setTotalTaxAmount(taxAmount);

                DeliveryType dt = new DeliveryType();
                if (amazonOrderItem.getScheduledDeliveryStartDate() != null)
                    dt.setActualDeliveryDate(OffsetDateTime.parse(amazonOrderItem.getScheduledDeliveryStartDate()).toLocalDate());
                if (amazonOrderItem.getScheduledDeliveryEndDate() != null)
                    dt.setActualDeliveryDate(OffsetDateTime.parse(amazonOrderItem.getScheduledDeliveryEndDate()).toLocalDate());
                lineItem.addDelivery(dt);

                orderLine.setLineItem(lineItem);
                order.addOrderLine(orderLine);
            }
        }

        return order;
    }

    private static BigDecimal toBigDecimal(Money money) {
        BigDecimal retValue = BigDecimal.ZERO;
        if (money != null && StringHelper.hasText(money.getAmount())) {
            try {
                retValue = new BigDecimal(money.getAmount());
            } catch (NumberFormatException nfe) {
                // unsure if i throw something here ...
            }
        }
        return retValue;
    }

    private static String toCurrency(Money money) {
        if (money != null && StringHelper.hasText(money.getCurrencyCode()))
            return money.getCurrencyCode().toUpperCase();
        return "EUR"; // todo NULL / "" / ... ???;
    }

    private static AddressType getAddressType(Address amazonAddress) {
        AddressType address = new AddressType();

        if (!StringHelper.hasText(amazonAddress.getName()))
            raiseRequiredException("ShippingAddress.Name");
        address.setID(amazonAddress.getName());
        if (amazonAddress.getAddressType() != null)
            address.setAddressTypeCode(amazonAddress.getAddressType().getValue());

        if (amazonAddress.getAddressLine1() != null) {
            AddressLineType alt1 = new AddressLineType();
            alt1.setLine(amazonAddress.getAddressLine1());
            address.addAddressLine(alt1);
        }
        if (amazonAddress.getAddressLine2() != null) {
            AddressLineType alt2 = new AddressLineType();
            alt2.setLine(amazonAddress.getAddressLine2());
            address.addAddressLine(alt2);
        }
        if (amazonAddress.getAddressLine3() != null) {
            AddressLineType alt3 = new AddressLineType();
            alt3.setLine(amazonAddress.getAddressLine3());
            address.addAddressLine(alt3);
        }
        address.setCityName(amazonAddress.getCity());
        address.setCountrySubentity(amazonAddress.getStateOrRegion());
        address.setPostalZone(amazonAddress.getPostalCode());
        if (amazonAddress.getCountryCode() != null) {
            CountryType country = new CountryType();
            country.setIdentificationCode(amazonAddress.getCountryCode());
            address.setCountry(country);
        }
        return address;
    }


    private static void raiseRequiredException(String fieldName) throws IllegalStateException {
        if (!StringHelper.hasText(fieldName))
            throw new IllegalStateException("Seriously, there is nothing i can do for you, check your code !");

        throw new IllegalStateException(fieldName + " required !");
    }

}
