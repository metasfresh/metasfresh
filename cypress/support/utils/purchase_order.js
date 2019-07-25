export class PurchaseOrder {
  constructor() {
    this.lines = [];
  }

  setBPartner(bPartner) {
    cy.log(`PurchaseOrder - setBPartner = ${bPartner}`);
    this.bPartner = bPartner;
    return this;
  }

  setPoReference(poReference) {
    cy.log(`PurchaseOrder - setPoReference = ${poReference}`);
    this.poReference = poReference;
    return this;
  }

  // there's only 1 doctype: PurchaseOrder
  setDocumentType(documentType) {
    cy.log(`PurchaseOrder - setDocumentType = ${documentType}`);
    this.documentType = documentType;
    return this;
  }

  setDropShip(isDropShip) {
    cy.log(`PurchaseOrder - setDropShip = ${isDropShip}`);
    this.isDropShip = isDropShip;
    return this;
  }

  addLine(purchaseOrderLine) {
    cy.log(`PurchaseOrder - addLine = ${JSON.stringify(purchaseOrderLine)}`);
    this.lines.push(purchaseOrderLine);
    return this;
  }

  apply() {
    cy.log(`PurchaseOrder - apply - START (poReference=${this.poReference})`);
    applyPurchaseOrder(this);
    cy.log(`PurchaseOrder - apply - END (poReference=${this.poReference})`);
  }
}

export class PurchaseOrderLine {
  setProduct(product) {
    cy.log(`PurchaseOrderLine - setProduct = ${product}`);
    this.product = product;
    return this;
  }

  setQuantity(quantity) {
    cy.log(`PurchaseOrderLine - setQuantity = ${quantity}`);
    this.quantity = quantity;
    return this;
  }
}

function applyPurchaseOrder(purchaseOrder) {
  describe(`Create new Purchase Order`, function() {
    cy.visitWindow('181', 'NEW');

    cy.writeIntoLookupListField('C_BPartner_ID', purchaseOrder.bPartner, purchaseOrder.bPartner);
    if (purchaseOrder.doctype) {
      cy.writeIntoStringField('C_DocTypeTarget_ID', purchaseOrder.documentType);
    }
    if (purchaseOrder.poReference) {
      cy.writeIntoStringField('POReference', purchaseOrder.poReference);
    }

    if (purchaseOrder.isDropShip) {
      cy.setCheckBoxValue('IsDropShip', purchaseOrder.isDropShip);
    }

    purchaseOrder.lines.forEach(function(purchaseOrderLine) {
      applyPurchaseOrderLine(purchaseOrderLine);
    });
    cy.get('table tbody tr').should('have.length', purchaseOrder.lines.length);
  });
}

function applyPurchaseOrderLine(purchaseOrderLine) {
  cy.selectTab('C_OrderLine');
  cy.pressAddNewButton();

  cy.writeIntoLookupListField('M_Product_ID', purchaseOrderLine.product, purchaseOrderLine.product, false, true);
  cy.writeIntoStringField('QtyEntered', purchaseOrderLine.quantity, true);
  cy.pressDoneButton();
}
