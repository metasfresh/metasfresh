package com.adekia.exchange.model;

import com.helger.commons.string.StringHelper;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyType;

public class BPartnerIdentifier {

    private PartyType party;

    public  BPartnerIdentifier(PartyType party)
    {
            this.party = party;
    }

    public String getPartnerIdentifier()
    {
        if (party == null || party.getContact() == null)
            raiseRequiredException("ElectronicMail");
        return party.getContact().getElectronicMailValue();

    }

    public String getPartnerContactIdentifier()
    {
        if (party == null || party.getContact() == null)
            raiseRequiredException("ElectronicMail");
        return party.getContact().getElectronicMailValue();

    }

    public String getPartnerLocationIdentifier()
    {
        if (party == null || party.getPostalAddress() == null || ! StringHelper.hasText(party.getPostalAddress().getIDValue()))
           raiseRequiredException("Postal Address");
        return party.getPostalAddress().getIDValue();
    }

    private void raiseRequiredException(String field) {
        throw new IllegalStateException("Partner " + field + " required");
    }
}
