import config from '../../config';
import { wrapRequest, findByName } from './utils';

export class BPartner {
  constructor({ name, ...vals }) {
    cy.log(`BPartner - set name = ${name}`);
    this.name = name;
    this.bPartnerLocations = [];
    this.contacts = [];

    for (let [key, val] of Object.entries(vals)) {
      this[key] = val;
    }
    return this;
  }

  setName(name) {
    cy.log(`BPartner - set name = ${name}`);
    this.name = name;
    return this;
  }

  setVendor(isVendor) {
    cy.log(`BPartner - set isVendor = ${isVendor}`);
    this.isVendor = isVendor;
    return this;
  }

  setVendorPricingSystem(vendorPricingSystem) {
    cy.log(`BPartner - set vendorPricingSystem = ${vendorPricingSystem}`);
    this.vendorPricingSystem = vendorPricingSystem;
    return this;
  }

  setVendorDiscountSchema(vendorDiscountSchema) {
    cy.log(`BPartner - set vendorDiscountSchema = ${vendorDiscountSchema}`);
    this.vendorDiscountSchema = vendorDiscountSchema;
    return this;
  }

  setVendorPaymentTerm(paymentTerm) {
    cy.log(`BPartner - set vendorPaymentTerm = ${paymentTerm}`);
    this.vendorPaymentTerm = paymentTerm;
    return this;
  }

  setCustomerDunning(customerDunning) {
    cy.log(`BPartner - set customerDunning = ${customerDunning}`);
    this.customerDunning = customerDunning;
    return this;
  }

  setCustomer(isCustomer) {
    cy.log(`BPartner - set isCustomer = ${isCustomer}`);
    this.isCustomer = isCustomer;
    return this;
  }

  setPaymentTerm(paymentTerm) {
    cy.log(`BPartner - set paymentTerm = ${paymentTerm}`);
    this.paymentTerm = paymentTerm;
    return this;
  }

  setCustomerPricingSystem(customerPricingSystem) {
    cy.log(`BPartner - set customerPricingSystem = ${customerPricingSystem}`);
    this.customerPricingSystem = customerPricingSystem;
    return this;
  }

  setCustomerDiscountSchema(customerDiscountSchema) {
    cy.log(`BPartner - set customerDiscountSchema = ${customerDiscountSchema}`);
    this.customerDiscountSchema = customerDiscountSchema;
    return this;
  }

  setBank(bank) {
    cy.log(`BPartner - set Bank = ${bank}`);
    this.bank = bank;
    return this;
  }

  setDunning(dunning) {
    cy.log(`BPartner - set Dunning = ${dunning}`);
    this.dunning = dunning;
    return this;
  }

  addContact(contact) {
    cy.log(`BPartner - add contact = ${JSON.stringify(contact)}`);
    this.contacts.push(contact);
    return this;
  }

  addLocation(bPartnerLocation) {
    cy.log(`BPartner - add location = ${JSON.stringify(bPartnerLocation)}`);
    this.bPartnerLocations.push(bPartnerLocation);
    return this;
  }

  clearLocations() {
    cy.log(`BPartner - clear locations`);
    this.bPartnerLocations = [];
    return this;
  }

  clearContacts() {
    cy.log(`BPartner - clear contacts`);
    this.contacts = [];
    return this;
  }

  apply() {
    cy.log(`BPartner - apply - START (name=${this.name})`);
    return BPartner.applyBPartner(this).then(() => {
      cy.log(`BPartner - apply - END (name=${this.name})`);

      if (this.bPartnerLocations.length || this.contacts.length || this.bank) {
        cy.visitWindow(123, this.id);

        // @TODO: Temporarily we're adding contacts, bank & location via UI because API...
        BPartner.applyManual(this);
      }
      return cy.wrap(this);
    });
  }

  static applyBPartner(bPartner) {
    const basicUri = `${config.API_URL}/window/123`;
    cy.get('.avatar').should('be.visible');
    return cy
      .request({
        url: `${basicUri}/NEW`,
        method: 'PATCH',
        body: JSON.stringify([]),
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then((newResponse) => {
        if (!newResponse.body.documents) {
          newResponse.body.documents = newResponse.response.body;
        }
        bPartner.id = newResponse.body.documents[0].id;

        const basicDataObject = [
          {
            op: 'replace',
            path: 'CompanyName',
            value: bPartner.name,
          },
          {
            op: 'replace',
            path: 'Name2',
            value: bPartner.name,
          },
        ];

        return cy
          .request({
            url: `${basicUri}/${bPartner.id}`,
            method: 'PATCH',
            body: JSON.stringify(basicDataObject),
            headers: {
              'Content-Type': 'application/json',
            },
          })
          .then(() => {
            BPartner.getVendorData(basicUri, bPartner).then((data) => {
              const dataObject = data;

              return cy
                .request({
                  url: `${basicUri}/${bPartner.id}?advanced=true`,
                  method: 'PATCH',
                  body: JSON.stringify(dataObject),
                  headers: {
                    'Content-Type': 'application/json',
                  },
                })
                .then(() => bPartner);
            });
          });
      });
  }

  static getVendorData(basicUri, bPartner) {
    const dataObject = [];

    if (bPartner.isVendor || bPartner.vendorDiscountSchema || bPartner.vendorPricingSystem || bPartner.isCustomer || bPartner.customerPricingSystem || bPartner.bPartnerLocations || bPartner.dunning) {
      const vendorRequest = wrapRequest(
        cy.request({
          url: `${basicUri}/${bPartner.id}/AD_Tab-224`,
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
      );

      const vendorDiscountSchemaRequest = wrapRequest(
        cy.request({
          url: `${basicUri}/${bPartner.id}/AD_Tab-224/${bPartner.id}/field/PO_DiscountSchema_ID/dropdown`,
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
      );

      const vendorPricingSystemRequest = wrapRequest(
        cy.request({
          url: `${basicUri}/${bPartner.id}/AD_Tab-224/${bPartner.id}/field/PO_PricingSystem_ID/dropdown`,
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
      );

      const vendorPaymentTermRequest = wrapRequest(
        cy.request({
          url: `${basicUri}/${bPartner.id}/AD_Tab-224/${bPartner.id}/field/PO_PaymentTerm_ID/dropdown`,
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
      );

      const bPartnerRequest = wrapRequest(
        cy.request({
          url: `${basicUri}/${bPartner.id}/AD_Tab-223`,
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
      );

      const cDiscountSchemaRequest = wrapRequest(
        cy.request({
          url: `${basicUri}/${bPartner.id}/AD_Tab-223/${bPartner.id}/field/M_DiscountSchema_ID/dropdown`,
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
      );

      const cPricingSystemRequest = wrapRequest(
        cy.request({
          url: `${basicUri}/${bPartner.id}/AD_Tab-223/${bPartner.id}/field/M_PricingSystem_ID/dropdown`,
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
      );

      const cPaymentTermRequest = wrapRequest(
        cy.request({
          url: `${basicUri}/${bPartner.id}/AD_Tab-223/${bPartner.id}/field/C_PaymentTerm_ID/dropdown`,
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
      );

      const cDunningRequest = wrapRequest(
        cy.request({
          url: `${basicUri}/${bPartner.id}/AD_Tab-223/${bPartner.id}/field/C_Dunning_ID/dropdown`,
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
      );

      return Cypress.Promise.all([
        vendorRequest,
        vendorDiscountSchemaRequest,
        vendorPricingSystemRequest,
        vendorPaymentTermRequest,
        bPartnerRequest,
        cDiscountSchemaRequest,
        cPricingSystemRequest,
        cPaymentTermRequest,
        cDunningRequest,
      ]).then((vals) => {
        const [
          vendorResponse,
          vendorDiscountSchemaResponse,
          vendorPricingSystemResponse,
          vendorPaymentTermResponse,
          bPartnerResponse,
          discountSchemaResponse,
          pricingSystemResponse,
          paymentTermResponse,
          dunningResponse,
        ] = vals;

        const isVendorValue = vendorResponse.fieldsByName.IsVendor.value;
        if (bPartner.isVendor && !isVendorValue) {
          dataObject.push({
            op: 'replace',
            path: 'IsVendor',
            value: true,
          });
        }

        const vendorDiscountSchema = findByName(vendorDiscountSchemaResponse, bPartner.vendorDiscountSchema);
        if (bPartner.vendorDiscountSchema && vendorDiscountSchema) {
          dataObject.push({
            op: 'replace',
            path: 'PO_DiscountSchema_ID',
            value: {
              key: vendorDiscountSchema.key,
              caption: vendorDiscountSchema.caption,
            },
          });
        }

        const vendorPricingSystem = findByName(vendorPricingSystemResponse, bPartner.vendorPricingSystem);
        if (bPartner.vendorPricingSystem && vendorPricingSystem) {
          dataObject.push({
            op: 'replace',
            path: 'PO_PricingSystem_ID',
            value: {
              key: vendorPricingSystem.key,
              caption: vendorPricingSystem.caption,
            },
          });
        }

        const vendorPaymentTerm = findByName(vendorPaymentTermResponse, bPartner.vendorPaymentTerm);
        if (bPartner.vendorPaymentTerm && vendorPaymentTerm) {
          dataObject.push({
            op: 'replace',
            path: 'PO_PaymentTerm_ID',
            value: {
              key: vendorPaymentTerm.key,
              caption: vendorPaymentTerm.caption,
            },
          });
        }

        const isCustomerValue = bPartnerResponse.fieldsByName.IsCustomer.value;
        if (bPartner.isCustomer && !isCustomerValue) {
          dataObject.push({
            op: 'replace',
            path: 'IsCustomer',
            value: true,
          });
        }
        if (bPartner.customerDunning) {
          dataObject.push({
            op: 'replace',
            path: 'C_Dunning_ID',
            value: bPartner.customerDunning,
          });
        }

        const discountSchema = findByName(discountSchemaResponse, bPartner.customerDiscountSchema);
        if (bPartner.customerDiscountSchema && discountSchema) {
          dataObject.push({
            op: 'replace',
            path: 'M_DiscountSchema_ID',
            value: {
              key: discountSchema.key,
              caption: discountSchema.caption,
            },
          });
        }

        const pricingSystem = findByName(pricingSystemResponse, bPartner.customerPricingSystem);
        if (bPartner.customerPricingSystem && pricingSystem) {
          dataObject.push({
            op: 'replace',
            path: 'M_PricingSystem_ID',
            value: {
              key: pricingSystem.key,
              caption: pricingSystem.caption,
            },
          });
        }

        const paymentTerm = findByName(paymentTermResponse, bPartner.paymentTerm);
        if (bPartner.paymentTerm && paymentTerm) {
          dataObject.push({
            op: 'replace',
            path: 'C_PaymentTerm_ID',
            value: {
              key: paymentTerm.key,
              caption: paymentTerm.caption,
            },
          });
        }

        const dunning = findByName(dunningResponse, bPartner.dunning);
        if (bPartner.dunning && dunning) {
          dataObject.push({
            op: 'replace',
            path: 'C_Dunning_ID',
            value: {
              key: dunning.key,
              caption: dunning.caption,
            },
          });
        }

        return dataObject;
      });
    }

    return cy.wrap(dataObject);
  }

  static applyManual(bPartner) {
    if (bPartner.bPartnerLocations.length > 0) {
      bPartner.bPartnerLocations.forEach(function (bPartnerLocation) {
        applyLocation(bPartnerLocation);
      });
      cy.get('table tbody tr').should('have.length', bPartner.bPartnerLocations.length);
    }
    if (bPartner.contacts.length > 0) {
      bPartner.contacts.forEach(function (bPartnerContact) {
        applyContact(bPartnerContact);
      });
      cy.get('table tbody tr').should('have.length', bPartner.contacts.length);
    }

    if (bPartner.bank) {
      applyBank(bPartner.bank);
    }
  }
}

function applyLocation(bPartnerLocation) {
  cy.selectTab('C_BPartner_Location');
  cy.pressAddNewButton();
  cy.log(`applyLocation - bPartnerLocation.name = ${bPartnerLocation.name}`);
  cy.writeIntoStringField('Name', `${bPartnerLocation.name}`, true /*modal*/, false, true);
  cy.get('.panel-modal-header-title').click();

  cy.editAddress('C_Location_ID', function (url) {
    cy.writeIntoStringField('Address1', ' ', null, url);
    cy.writeIntoStringField('City', bPartnerLocation.city, null, url);
    cy.writeIntoLookupListField('C_Country_ID', bPartnerLocation.country, bPartnerLocation.country, false /*typeList */, false /*modal THIS MUST BE FALSE EVEN IF IT'S A MODAL!*/, url);
  });
  cy.get('.form-field-Address').should('contain', bPartnerLocation.city);
  cy.pressDoneButton();
}

function applyContact(bPartnerContact) {
  cy.selectTab('AD_User');
  cy.pressAddNewButton();
  cy.writeIntoStringField('Firstname', bPartnerContact.firstName, true /*modal*/);
  cy.writeIntoStringField('Lastname', bPartnerContact.lastName, true /*modal*/);

  if (bPartnerContact.isDefaultContact) {
    cy.clickOnCheckBox('IsDefaultContact', true /*expectedPatchValue*/, true /*modal*/);
  }
  cy.pressDoneButton();
}

function applyBank(bank) {
  cy.selectTab('C_BP_BankAccount');
  cy.pressAddNewButton();
  cy.writeIntoLookupListField('C_Bank_ID', bank, bank, false, true);
  cy.writeIntoStringField('A_Name', 'Test Account', true);
  cy.pressDoneButton();
}
