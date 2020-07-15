export class SalesOrder {
  constructor(reference) {
    this.reference = reference;
    this.bPartner = undefined;
    this.bPartnerLocation = undefined;
    this.lines = [];
  }

  setBPartner(bPartner) {
    cy.log(`SalesOrder - setBPartner = ${bPartner}`);
    this.bPartner = bPartner;
    return this;
  }

  addLine(salesOrderLine) {
    this.lines.push(salesOrderLine);
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

  setPriceSystem(priceSystem) {
    cy.log(`SalesOrder - priceSystem = ${priceSystem}`);
    this.priceSystem = priceSystem;
    return this;
  }

  setWarehouse(warehouse) {
    cy.log(`SalesOrder - warehouse = ${warehouse}`);
    this.warehouse = warehouse;
    return this;
  }

  apply() {
    cy.log(`SalesOrder - apply - START (${this.reference})`);
    SalesOrder.applySalesOrder(this);
    cy.log(`SalesOrder - apply - END (${this.reference})`);
    return this;
  }

  static applySalesOrder(salesOrder) {
    describe(`Create new salesOrder`, function() {
      cy.visitWindow('143', 'NEW');
      cy.get('.header-breadcrumb-sitename').should('contain', '<');

      cy.writeIntoLookupListField('C_BPartner_ID', salesOrder.bPartner, salesOrder.bPartner);
      if (salesOrder.bPartnerLocation) {
        cy.writeIntoLookupListField('C_BPartner_Location_ID', salesOrder.bPartnerLocation, salesOrder.bPartnerLocation, true /*typeList*/);
      }

      if (salesOrder.warehouse) {
        cy.selectInListField('M_Warehouse_ID', salesOrder.warehouse);
      }

      if (salesOrder.priceSystem) {
        cy.resetListValue('M_PricingSystem_ID');
        cy.writeIntoLookupListField('M_PricingSystem_ID', salesOrder.priceSystem, salesOrder.priceSystem);
      }

      cy.get('.header-breadcrumb-sitename').should('not.contain', '<'); // make sure the order was successfully saved as bpartner, warehouse price system were dealt with

      if (salesOrder.reference) {
        cy.writeIntoStringField('POReference', salesOrder.reference);
      }

      salesOrder.lines.forEach(line => {
        SalesOrder.applyLine(line);
      });
    });
  }

  static applyLine(salesOrderLine) {
    cy.selectTab('C_OrderLine');
    cy.pressAddNewButton();

    cy.writeIntoLookupListField('M_Product_ID', salesOrderLine.product, salesOrderLine.product, false /*typeList*/, true /*modal*/);

    cy.writeIntoStringField('QtyEntered', salesOrderLine.quantity, true /*modal*/, null /*rewriteUrl*/, true /*noRequest, bc the patch response is e.g. 20 and we would be waiting for e.g. '20' */);

    cy.pressDoneButton();
  }
}

export class SalesOrderLine {
  setProduct(product) {
    this.product = product;
    return this;
  }

  setQuantity(quantity) {
    this.quantity = quantity;
    return this;
  }
}
