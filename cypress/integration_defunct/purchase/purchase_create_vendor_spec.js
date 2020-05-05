/// <reference types="Cypress" />

import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import config from '../../config';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('purchase - vendor spec', function() {
  let vendorName;
  let discountSchemaName;
  let discountSchemaValidFrom;

  it('Read the fixture', function() {
    cy.fixture('purchase/purchase_create_vendor_spec.json').then(f => {
      vendorName = appendHumanReadableNow(f['vendorName']);
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      discountSchemaValidFrom = f['discountSchemaValidFrom'];
    });
  });

  it('Create test data', function() {
    new DiscountSchema(discountSchemaName).setValidFrom(discountSchemaValidFrom).apply();

    cy.fixture('purchase/simple_vendor.json').then(vendorJson => {
      new BPartner({ name: vendorName, ...vendorJson }).setVendorDiscountSchema(discountSchemaName).apply();
    });
  });

  it('Create a vendor with two contacts', function() {
    cy.log('Now going to verify all fields were set correctly');
    cy.location().then(location => {
      const apiUrl = `${config.API_URL}${location.pathname}`;
      cy.log(`Get bpartner JSON - apiUrl=${apiUrl}`);

      cy.request(apiUrl).then(response => {
        const bpartnerJson = response.body;

        cy.log(`bpartnerJson = ${JSON.stringify(bpartnerJson)}`);

        cy.log('verify those fields that are different on each test run');
        expect(bpartnerJson, 'bpartnerJson - length').to.have.lengthOf(1);
        expect(bpartnerJson[0].fieldsByName.CompanyName.value, 'bpartnerJson - CompanyName').to.eq(vendorName);
        expect(bpartnerJson[0].fieldsByName.Name.value, 'bpartnerJson - Name').to.eq(vendorName);
        expect(bpartnerJson[0].fieldsByName.Name2.value, 'bpartnerJson - Name2').to.eq(vendorName);

        const expectedDocumentSummary = `${vendorName} ${bpartnerJson[0].fieldsByName.Value.value}`;
        expect(bpartnerJson[0].fieldsByName.V$DocumentSummary.value, 'V$DocumentSummary').to.eq(expectedDocumentSummary);

        cy.log('create and snapshot the invariant part of the test result');
        // remove the fields that are different on each test run from the JSON; the result is invariant between test runs
        delete bpartnerJson[0].id;
        delete bpartnerJson[0].fieldsByName.ID;
        delete bpartnerJson[0].fieldsByName.Value;
        delete bpartnerJson[0].fieldsByName.Name;
        delete bpartnerJson[0].fieldsByName.Name2;
        delete bpartnerJson[0].fieldsByName.CompanyName;
        delete bpartnerJson[0].fieldsByName.V$DocumentSummary;
        delete bpartnerJson[0].websocketEndpoint;
        // snapshot the invariant result
        cy.wrap(bpartnerJson).toMatchSnapshot(`purchase_create_vendor_bpartner - apiUrl=${apiUrl}`);
      });

      // TODO look into making this work too
      // cy.log('retrieve and snapshot the location(s) we created')
      // cy.request(`${apiUrl}/541016`)
      //   .then(($response)=>{
      //     const bpartnerLocationsJson = $response.body;
      //     cy.wrap(bpartnerLocationsJson).snapshot('purchase_create_vendor_bpartnerLocations')
      // })

      // cy.log('retrieve and snapshot the contact(s) we created')
      // cy.request(`${apiUrl}/541017`)
      //   .then(($response)=>{
      //     const contactsJson = $response.body;
      //     cy.wrap(contactsJson).snapshot('purchase_create_vendor_contacts')
      // })
    });
  });
});
