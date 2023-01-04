/*
 * #%L
 * camel-routes
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

package com.adekia.exchange.camel.provider;

import com.adekia.exchange.context.Ctx;
import com.adekia.exchange.provider.GetShipmentProvider;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.AddressLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ConsignmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ContactType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.CountryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.DeliveryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.DespatchLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.DimensionType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.LineItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.OrderLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyNameType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ShipmentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_23.AdditionalInformationType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_23.CurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_23.ElectronicMailType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_23.MeasureType;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_23.DespatchAdviceType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
@ConditionalOnSingleCandidate(GetShipmentProvider.class)
public class GetShipmentProviderSimpleImpl implements GetShipmentProvider {

    @Override
    public DespatchAdviceType getShipment(Ctx ctx) {
        DespatchAdviceType despatchAdviceType = new DespatchAdviceType();
        CustomerPartyType cpt = new CustomerPartyType();
        cpt.setCustomerAssignedAccountID("1234567890");
        ContactType ct1 = new ContactType();
        ct1.setElectronicMail("director@mycompany.com");
        ct1.setTelephone("444719");
        cpt.setDeliveryContact(ct1);
        despatchAdviceType.setDeliveryCustomerParty(cpt);

        ShipmentType st = new ShipmentType();
        DeliveryType dt = new DeliveryType();

        PartyType pt = new PartyType();
        PartyNameType pnt = new PartyNameType();
        pnt.setName("PartyName");
        pt.addPartyName(pnt);
        dt.setDeliveryParty(pt);

        AddressType da = new AddressType();
        for (int i=1; i<=3;i++)
        {
            AddressLineType alt = new AddressLineType();
            alt.setLine("Line " +i);
            da.addAddressLine(alt);
        }
        da.setPostalZone("34830");
        da.setCityName("Jacou");
        CountryType ct = new CountryType();
        ct.setIdentificationCode("FR");
        ct.setName("France");
        da.setCountry(ct);
        dt.setDeliveryAddress(da);
        st.setDelivery(dt);

        ConsignmentType ct2 = new ConsignmentType();
        ct2.setID("MyConsignement");
        st.addConsignment(ct2);

        st.setGrossVolumeMeasure(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(300) + 1)).setUnitCode("kg");
        st.setDeclaredCustomsValueAmount(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(3000) + 50)).setCurrencyID("EUR");

        despatchAdviceType.setShipment(st);

        despatchAdviceType.setDespatchLine(createShipmentLines());

        return despatchAdviceType;
    }


    private List<DespatchLineType> createShipmentLines() {

        return
                new Random().ints(ThreadLocalRandom.current().nextInt(4) + 1, 0, 4)
                        .mapToObj(i -> createLine(i)
                        ).collect(Collectors.toList());
    }

    private DespatchLineType createLine(int i) {
        DespatchLineType line = new DespatchLineType();
        ItemType it = new ItemType();
        it.setName(products[i][0]);

        AdditionalInformationType ait = new AdditionalInformationType();
        ait.setValue(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(999) + 1).toString());
        it.addAdditionalInformation(ait);

        DimensionType dt = new DimensionType();
        dt.setAttributeID("Weight");
        dt.setMeasure(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(100) + 1)).setUnitCode("kg");
        it.addDimension(dt);

        line.setItem(it);

        line.setDeliveredQuantity(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(5) + 1));

        return line;
    }


    private String[][] products = {
            {"One Ring", "To rule them all"},
            {"Three rings", "For the elven-kings under the sky"},
            {"Seven rings", "For the Dwarf-lords in their halls of stone"},
            {"Nine rings", "For mortal men doomed to die"}
    };


}
