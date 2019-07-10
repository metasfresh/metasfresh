import config from '../../config';
import { wrapRequest, findByName } from './utils';

export class SalesOrder {
  constructor({ reference, ...vals }) {
    cy.log(`SalesOrder - set reference = ${reference}`);
    this.reference = reference;
    this.bPartner = undefined;
    this.bPartnerLocation = undefined;

    for (let [key, val] of Object.entries(vals)) {
      this[key] = val;
    }
    return this;
  }

  setBPartner(bPartner) {
    cy.log(`SalesOrder - setBPartner = ${bPartner}`);
    this.bPartner = bPartner;
    return this;
  }

  setBPartnerLocation(location) {
    cy.log(`SalesOrder - setBPartnerLocation = ${location}`);
    this.bPartnerLocation = location;
    return this;
  }

  setPoReference(reference) {
    cy.log(`SalesOrder - setReference = ${reference}`);
    this.reference = reference;
    return this;
  }

  setWarehouse(warehouse) {
    cy.log(`SalesOrder - setWarehouse = ${warehouse}`);
    this.warehouse = warehouse;
    return this;
  }

  apply() {
    cy.log(`SalesOrder - apply - START (${this.reference})`);
    return SalesOrder.applySalesOrder(this).then(() => {
      cy.log(`SalesOrder - apply - END (${this.reference})`);

      return cy.wrap(this);
    });
  }

  static applySalesOrder(salesOrder) {
    const basicUri = `${config.API_URL}/window/143`;

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
        salesOrder.id = newResponse.body[0].id;

        SalesOrder.getData(basicUri, salesOrder).then(data => {
          const dataObject = [
            {
              op: 'replace',
              path: 'POReference',
              value: salesOrder.reference,
            },
            ...data,
          ];

          return cy
            .request({
              url: `${basicUri}/${salesOrder.id}`,
              method: 'PATCH',
              body: JSON.stringify(dataObject),
              headers: {
                'Content-Type': 'application/json',
              },
            })
            .then(() => salesOrder);
        });
      });
  }

  static getData(basicUri, salesOrder) {
    const dataObject = [];

    const bPartnerRequest = wrapRequest(
      cy.request({
        url: `${basicUri}/${salesOrder.id}/field/C_BPartner_ID/typeahead`,
        method: 'GET',
        qs: {
          query: salesOrder.bPartner,
        },
        headers: {
          'Content-Type': 'application/json',
        },
      })
    );

    const bPartnerLocationRequest = wrapRequest(
      cy.request({
        url: `${basicUri}/${salesOrder.id}/field/C_BPartner_Location_ID/dropdown`,
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
    );

    const warehouseRequest = wrapRequest(
      cy.request({
        url: `${basicUri}/${salesOrder.id}/field/M_Warehouse_ID/dropdown`,
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
    );

    return Cypress.Promise.all([bPartnerRequest, bPartnerLocationRequest, warehouseRequest]).then(vals => {
      const [bPartnerResponse, bPartnerLocationResponse, warehouseResponse] = vals;

      const bPartner = findByName(bPartnerResponse, salesOrder.bPartner);
      if (salesOrder.bPartner && bPartner) {
        dataObject.push({
          op: 'replace',
          path: 'C_BPartner_ID',
          value: {
            key: bPartner.key,
            caption: bPartner.caption,
          },
        });
      }

      const location = findByName(bPartnerLocationResponse, salesOrder.bPartnerLocation);
      if (salesOrder.bPartnerLocation && location) {
        dataObject.push({
          op: 'replace',
          path: 'C_BPartner_Location_ID',
          value: {
            key: location.key,
            caption: location.caption,
          },
        });
      }

      const warehouse = findByName(warehouseResponse, salesOrder.warehouse);
      if (salesOrder.warehouse && warehouse) {
        dataObject.push({
          op: 'replace',
          path: 'M_Warehouse_ID',
          value: {
            key: warehouse.key,
            caption: warehouse.caption,
          },
        });
      }

      return dataObject;
    });
  }
}
