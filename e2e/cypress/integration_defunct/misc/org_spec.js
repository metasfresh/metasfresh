/// <reference types="Cypress" />

import { BPartner, BPartnerLocation } from '../../support/utils/bpartner';

describe('New org test', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const orgName = `Org ${timestamp}`; // name needs to match regex ^[a-zA-Z0-9-_+]+?
    const bPartnerName = `Org-BPartner ${timestamp}`

    it('Create a new org and bPartner and link them', function() {

        cy.log(`Create org with name=${orgName}`)
        cy.visit('window/110/NEW');
        cy.wait(500)
        cy.writeIntoStringField('Name', `${orgName}{enter}`);

        //cy.log('There shall be no button, because lang-records are not inserted via UI')
        cy.selectTab('AD_OrgInfo')
        cy.get('.btn').should('not.exist');
        cy.get('table tbody tr').should('have.length', 1);

        cy.screenshot();

        cy.log(`Create bPartner with name=${bPartnerName}`)
        new BPartner
            .builder(bPartnerName)
            .addLocation(new BPartnerLocation
                .builder('Address1')
                .setCity('Cologne')
                .setCountry('Deutschland')
                .build())
            .build()
            .apply();
        cy.screenshot()

        cy.log(`Execute action to link bPartner with org`)
        cy.executeHeaderAction('C_BPartner_Org_Link')
            .selectInListField('C_BPartner_Location_ID', 'Address1', 'Address1')
            .selectInListField('AD_Org_ID', orgName, true/*modal*/)
            .pressStartButton();
        cy.wait(2000)
        cy.screenshot()

        cy.log('Now going to verify all fields were set correctly')
        cy.location().then(($location)=>{
          const apiUrl = `${config.API_URL}${$location.pathname}`
          cy.log(`Get bpartner JSON - apiUrl=${apiUrl}`)
          
          cy.request(apiUrl)
            .then(($response)=>{
              const bpartnerJson = $response.body;
  
              cy.log(`bpartnerJson = ${JSON.stringify(bpartnerJson)}`)
  
              cy.log('verify those fields that are different on each test run')
              expect(bpartnerJson, 'bpartnerJson - length').to.have.lengthOf(1)
              expect(bpartnerJson[0].fieldsByName.CompanyName.value, 'bpartnerJson - CompanyName').to.eq(bPartnerName)            
              expect(bpartnerJson[0].fieldsByName.Name.value, 'bpartnerJson - Name').to.eq(bPartnerName)
              expect(bpartnerJson[0].fieldsByName.Name2.value, 'bpartnerJson - Name2').to.eq(bPartnerName)
  
              const expectedDocumentSummary = `${bPartnerName} ${bpartnerJson[0].fieldsByName.Value.value}`
              expect(bpartnerJson[0].fieldsByName.V$DocumentSummary.value,'V$DocumentSummary').to.eq(expectedDocumentSummary)
  
              cy.log('create and snapshot the invariant part of the test result')
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
              cy.wrap(bpartnerJson).snapshot(`org_spec - apiUrl=${apiUrl}`)
          })
        })
   });
});