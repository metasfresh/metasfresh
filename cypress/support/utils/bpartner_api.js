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
    cy.log(`BPartner - apply - START (name=${this.name})`);
    return BPartner.applyBPartner(this).then(() => {
      cy.log(`BPartner - apply - END (name=${this.name})`);
      return cy.wrap(this);
    });
  }

  static applyBPartner(bPartner) {
    const basicUri = `${config.API_URL}/window/123`;

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
            BPartner.getVendorData(basicUri, bPartner).then(data => {
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

    if (
      bPartner.isVendor ||
      bPartner.vendorDiscountSchema ||
      bPartner.vendorPricingSystem ||
      bPartner.isCustomer ||
      bPartner.customerPricingSystem
    ) {
      const vendorRequest = wrapRequest(
        cy.request({
          url: `${basicUri}/${bPartner.id}/AD_Tab-224`,
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

      return Cypress.Promise.all([
        vendorRequest,
        bPartnerRequest,
      ]).then(vals => {
        const isVendorValue = vals[0].fieldsByName.IsVendor.value;

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

        const isCustomerValue = vals[1].fieldsByName.IsCustomer.value;

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
            value: {
              key: bPartner.customerDiscountSchema.key,
              caption: bPartner.customerDiscountSchema.caption,
            },
          });
        }

        if (bPartner.customerPricingSystem) {
          dataObject.push({
            op: 'replace',
            path: 'M_PricingSystem_ID',
            value: {
              key: bPartner.customerPricingSystem.key,
              caption: bPartner.customerPricingSystem.caption,
            },
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
            value: {
              key: bPartner.paymentTerm.key,
              caption: bPartner.paymentTerm.caption,
            },
          });
        }

        return dataObject;
      });
    }

    return cy.wrap(dataObject);
  }
}

const wrapRequest = req => {
  return new Promise((resolve, reject) => {
    req.then(response => {
      resolve(response.body[0]);
    });
  });
};
