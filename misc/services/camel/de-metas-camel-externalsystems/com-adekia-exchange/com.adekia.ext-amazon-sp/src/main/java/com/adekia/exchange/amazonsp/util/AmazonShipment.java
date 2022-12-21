/*
 * #%L
 * ext-amazon-sp
 * %%
 * Copyright (C) 2022 metas GmbH
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

import com.adekia.exchange.amazonsp.client.shipments.model.Address;
import com.adekia.exchange.amazonsp.client.shipments.model.Container;
import com.adekia.exchange.amazonsp.client.shipments.model.ContainerItem;
import com.adekia.exchange.amazonsp.client.shipments.model.ContainerList;
import com.adekia.exchange.amazonsp.client.shipments.model.CreateShipmentRequest;
import com.adekia.exchange.amazonsp.client.shipments.model.Currency;
import com.adekia.exchange.amazonsp.client.shipments.model.Weight;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.AddressLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ConsignmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.DespatchLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.DimensionType;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_23.DespatchAdviceType;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AmazonShipment
{
	public static CreateShipmentRequest fromDespatch(DespatchAdviceType despatchAdvice)
	{
		CreateShipmentRequest shipmentRequest = new CreateShipmentRequest();

		shipmentRequest.setClientReferenceId(despatchAdvice.getDeliveryCustomerParty().getCustomerAssignedAccountIDValue()); // todo npe

		Address shipTo = new Address();
		shipTo.setName(despatchAdvice.getShipment().getDelivery().getDeliveryParty().getPartyName().get(0).getNameValue()); // todo npe
		AddressType deliveryAddress = despatchAdvice.getShipment().getDelivery().getDeliveryAddress();
		List<AddressLineType> deliveryAddressLines = deliveryAddress.getAddressLine();
		if (deliveryAddressLines.size() > 0)
			shipTo.setAddressLine1(deliveryAddressLines.get(0).getLineValue());
		if (deliveryAddressLines.size() > 1)
			shipTo.setAddressLine2(deliveryAddressLines.get(1).getLineValue());
		if (deliveryAddressLines.size() > 2)
			shipTo.setAddressLine3(deliveryAddressLines.get(2).getLineValue());
		shipTo.setPostalCode(deliveryAddress.getPostalZoneValue());
		shipTo.setCity(deliveryAddress.getCityNameValue());
		shipTo.setCountryCode(deliveryAddress.getCountry().getIdentificationCodeValue());
		shipTo.setStateOrRegion(deliveryAddress.getCountrySubentityValue());
		shipTo.setEmail(despatchAdvice.getDeliveryCustomerParty().getDeliveryContact().getElectronicMailValue());
		shipTo.setPhoneNumber(despatchAdvice.getDeliveryCustomerParty().getDeliveryContact().getTelephoneValue());
		shipmentRequest.setShipTo(shipTo);


		// For now 1 consignement with every items of the shipment
		ContainerList containers = new ContainerList();
		Container container = new Container();
		container.setContainerType(Container.ContainerTypeEnum.PACKAGE);
		List<ConsignmentType> consignements = despatchAdvice.getShipment().getConsignment();
		if (!CollectionUtils.isEmpty(consignements))
			container.setContainerReferenceId(consignements.get(0).getIDValue());
		List<ContainerItem> cItems = new ArrayList<ContainerItem>();
		for (DespatchLineType despatchLine : despatchAdvice.getDespatchLine())
		{
			ContainerItem cItem = new ContainerItem();
			cItem.setTitle(despatchLine.getItem().getNameValue());
			cItem.setQuantity(despatchLine.getDeliveredQuantityValue());
			Currency cur = new Currency();
			cur.setUnit("EUR");                                                                                         // todo
			cur.setValue(BigDecimal.ZERO/*despatchLine.getItem().getAdditionalInformation().get(0).getValue()*/);      // todo
			cItem.setUnitPrice(cur);

			for (DimensionType dimension : despatchLine.getItem().getDimension())
			{
				if ("Weight".equalsIgnoreCase(dimension.getAttributeIDValue()))
				{
					Weight weight = new Weight();
					weight.setUnit(Weight.UnitEnum.fromValue(dimension.getMeasure().getUnitCode()));
					weight.setValue(dimension.getMeasure().getValue());
					cItem.setUnitWeight(weight);
				}
                /*  todo
                else if ("Height".equalsIgnoreCase(dimension.getAttributeIDValue()))
                {
                    dim.setUnit(Dimensions.UnitEnum.fromValue(dimension.getMeasure().getUnitCode()));
                    dim.setHeight(dimension.get);
                    dim.setLength();
                    dim.setWidth();
                    container.setDimensions(dim);
                }
                else if ("???".equalsIgnoreCase(dimension.getAttributeIDValue()))
                {
                ??
                }
            */
			}

			cItems.add(cItem);
		}
		container.setItems(cItems);

		if (despatchAdvice.getShipment() != null)
		{
			if (despatchAdvice.getShipment().getGrossWeightMeasure() != null)
			{
				Weight weight = new Weight();
				weight.setUnit(Weight.UnitEnum.fromValue(despatchAdvice.getShipment().getGrossWeightMeasure().getUnitCode()));
				weight.setValue(despatchAdvice.getShipment().getGrossWeightMeasureValue());
				container.setWeight(weight);
			}
			if (despatchAdvice.getShipment().getDeclaredCustomsValueAmount() != null)
			{
				Currency cur = new Currency();
				cur.setUnit(despatchAdvice.getShipment().getDeclaredCustomsValueAmount().getCurrencyID());
				cur.value(despatchAdvice.getShipment().getDeclaredCustomsValueAmountValue());
				container.setValue(cur);
			}
		}

		shipmentRequest.setContainers(containers);

		return shipmentRequest;
	}
}
