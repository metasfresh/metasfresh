import config from '../../config';

export class BPartner {
  constructor({ name, ...vals }) {
    cy.log(`BPartner - set name = ${name}`);
    this.name = name;
    for (let [key, val] of Object.entries(vals)) {
      this[key] = val;
    }

    this.bPartnerLocations = [];
    this.contacts = [];

    return this;
  }

  apply() {
    console.log('FOOO: ')
    cy.log(`BPartner - apply - START (name=${this.name})`);
    return BPartner.applyBPartner(this).then(() => {
      console.log('BAR')
      cy.log(`BPartner - apply - END (name=${this.name})`);
      return this;
    });
  }

  static applyBPartner(bPartner) {
    const basicUri = `${config.API_URL}/window/123`;

    console.log('0');

    return cy
      .request({
        url: `${basicUri}/NEW`,
        method: 'PATCH',
        body: JSON.stringify([]),
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(newResponse => {
        bPartner.id = newResponse.body[0].id;

        console.log('A')

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
          .then(initialResponse => {
            console.log('RESPONSE1: ', initialResponse);

            let dataObject = null;
            BPartner.getVendorData(basicUri, bPartner).then(data => (dataObject = data));

            return cy
              .request({
                url: `${basicUri}/${bPartner.id}?advanced=true`,
                method: 'PATCH',
                body: JSON.stringify(dataObject),
                headers: {
                  'Content-Type': 'application/json',
                },
              })
              .then(patchResponse => {
                console.log('PATCH RESPONSE: ', patchResponse);

                return bPartner;
              });
          });
      });
  }

  static getVendorData(basicUri, bPartner) {
    const dataObject = [];

    if (bPartner.isVendor || bPartner.vendorDiscountSchema || bPartner.vendorPricingSystem) {
      // https://dev540.metasfresh.com/rest/api/window/123/2156466/AD_Tab-224

      cy.request({
        url: `${basicUri}/${bPartner.id}/AD_Tab-224`,
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      }).then(getVendorResponse => {
        const isVendorValue = getVendorResponse.body[0].fieldsByName.IsVendor.value;

        console.log('AUUU1')

        if (bPartner.isVendor && !isVendorValue) {
          dataObject.push({
            op: 'replace',
            path: 'IsVendor',
            value: true,
          });
        }

        if (bPartner.vendorPricingSystem) {
          dataObject.push({
            op: 'replace',
            path: 'PO_PricingSystem_ID',
            value: bPartner.vendorPricingSystem,
          });
        }

        if (bPartner.vendorDiscountSchema) {
          dataObject.push({
            op: 'replace',
            path: 'PO_DiscountSchema_ID',
            value: bPartner.vendorDiscountSchema,
          });
        }
      });
    }

    if (bPartner.isCustomer || bPartner.customerPricingSystem) {
      cy.request({
        url: `${basicUri}/${bPartner.id}/AD_Tab-223`,
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      }).then(getCustomerResponse => {
        const isCustomerValue = getCustomerResponse.body[0].fieldsByName.IsCustomer.value;

        console.log('AUUU222')

        if (bPartner.isCustomer && !isCustomerValue) {
          dataObject.push({
            op: 'replace',
            path: 'IsCustomer',
            value: true,
          });
        }

        if (bPartner.customerDiscountSchema) {
          dataObject.push({
            op: 'replace',
            path: 'M_DiscountSchema_ID',
            value: bPartner.customerDiscountSchema,
          });
        }

        if (bPartner.customerPricingSystem) {
          dataObject.push({
            op: 'replace',
            path: 'M_PricingSystem_ID',
            value: bPartner.customerPricingSystem,
          });
        }

        if (bPartner.customerDunning) {
          dataObject.push({
            op: 'replace',
            path: 'C_Dunning_ID',
            value: bPartner.customerDunning,
          });
        }

        if (bPartner.paymentTerm) {
          dataObject.push({
            op: 'replace',
            path: 'C_PaymentTerm_ID',
            value: bPartner.paymentTerm,
          });
        }
      });
    }

  //   if (bPartner.bPartnerLocations.length > 0) {
  //     bPartner.bPartnerLocations.forEach(function (bPartnerLocation) {
  //       applyLocation(bPartnerLocation);
  //     });
  //     cy.get('table tbody tr').should('have.length', bPartner.bPartnerLocations.length);
  //   }
  //   if (bPartner.contacts.length > 0) {
  //     bPartner.contacts.forEach(function (bPartnerContact) {
  //       applyContact(bPartnerContact);
  //     });
  //     cy.get('table tbody tr').should('have.length', bPartner.contacts.length);
  //   }
  //   if (bPartner.bank) {
  //     BPartner.applyBank(bPartner.bank);
  //   }

    console.log('BZIUM')

    return cy.wrap(dataObject);
  }
}

// function applyLocation(bPartnerLocation) {
//   cy.selectTab('C_BPartner_Location');
//   cy.pressAddNewButton();
//   cy.log(`applyLocation - bPartnerLocation.name = ${bPartnerLocation.name}`);
//   cy.writeIntoStringField('Name', `${bPartnerLocation.name}`, true /*modal*/, false, true);
//   cy.get('.panel-modal-header-title').click();

//   cy.editAddress('C_Location_ID', function(url) {
//     cy.writeIntoStringField('Address1', ' ', null, url);
//     cy.writeIntoStringField('City', bPartnerLocation.city, null, url);
//     cy.writeIntoLookupListField(
//       'C_Country_ID',
//       bPartnerLocation.country,
//       bPartnerLocation.country,
//       false /*typeList */,
//       false /*modal THIS MUST BE FALSE EVEN IF IT'S A MODAL!*/,
//       url
//     );
//   });
//   cy.get('.form-field-Address').should('contain', bPartnerLocation.city);
//   cy.pressDoneButton();
// }

// function applyContact(bPartnerContact) {
//   cy.selectTab('AD_User');
//   cy.pressAddNewButton();
//   cy.writeIntoStringField('Firstname', bPartnerContact.firstName, true /*modal*/);
//   cy.writeIntoStringField('Lastname', bPartnerContact.lastName, true /*modal*/);

//   if (bPartnerContact.isDefaultContact) {
//     cy.clickOnCheckBox('IsDefaultContact', true /*expectedPatchValue*/, true /*modal*/);
//   }
//   cy.pressDoneButton();
// }

  // static applyBank(bank) {
  //   cy.selectTab('C_BP_BankAccount');
  //   cy.pressAddNewButton();
  //   cy.writeIntoLookupListField('C_Bank_ID', bank, bank, false, true);
  //   cy.writeIntoStringField('A_Name', 'Test Account', true);
  //   cy.pressDoneButton();
  // }
