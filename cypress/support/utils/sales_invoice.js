export class SalesInvoice {
  constructor(businessPartnerName, targetDocumentType) {
    cy.log(`SalesInvoice - businessPartnerName=${businessPartnerName}, targetDocumentType=${targetDocumentType}`);
    this.businessPartner = businessPartnerName;
    this.targetDocumentType = targetDocumentType;
    this._toString = `businessPartnerName=${businessPartnerName}, targetDocumentType=${targetDocumentType}`;
    this.lines = [];
  }

  addLine(salesInvoiceLine) {
    console.log(`SalesInvoice - add InvoiceLine = ${JSON.stringify(salesInvoiceLine)}`);
    this.lines.push(salesInvoiceLine);
    return this;
  }

  setDocumentAction(documentAction) {
    this.documentAction = documentAction;
    return this;
  }

  setDocumentStatus(documentStatus) {
    this.documentStatus = documentStatus;
    return this;
  }

  setPriceList(priceList) {
    this.priceList = priceList;
    return this;
  }

  apply() {
    cy.log(`SalesInvoice - apply START (${this._toString})`);
    SalesInvoice.applySalesInvoice(this);
    cy.log(`SalesInvoice - apply STOP (${this._toString})`);
    return this;
  }

  static applySalesInvoice(salesInvoice) {
    describe(`Create new SalesInvoice: ${salesInvoice._toString}`, () => {
      cy.visitWindow('167', 'NEW');
      cy.get('.header-breadcrumb-sitename')
        .should('contain', new Date().getDate())
        .should('contain', new Date().getFullYear());

      cy.writeIntoLookupListField('C_BPartner_ID', salesInvoice.businessPartner, salesInvoice.businessPartner);
      cy.getFieldValue('M_PriceList_ID').should('not.be.empty');
      cy.getFieldValue('C_Currency_ID').should('not.be.empty');

      cy.selectInListField('M_PriceList_ID', salesInvoice.priceList);

      cy.getFieldValue('DocumentNo').should('be.empty');
      cy.selectInListField('C_DocTypeTarget_ID', salesInvoice.targetDocumentType);
      cy.getFieldValue('DocumentNo').should('not.be.empty');

      salesInvoice.lines.forEach(line => {
        SalesInvoice.applyLine(line);
      });

      if (salesInvoice.documentAction) {
        if (salesInvoice.documentStatus) {
          cy.processDocument(salesInvoice.documentAction, salesInvoice.documentStatus);
        } else {
          cy.processDocument(salesInvoice.documentAction);
        }
      }


    });
  }

  static applyLine(salesInvoiceLine) {
    cy.selectTab('C_InvoiceLine');
    cy.pressAddNewButton();

    cy.writeIntoLookupListField('M_Product_ID', salesInvoiceLine.product, salesInvoiceLine.product);
    if (salesInvoiceLine.packingItem) {
      cy.writeIntoLookupListField('M_HU_PI_Item_Product_ID', salesInvoiceLine.packingItem, salesInvoiceLine.packingItem, true, true);
    }

    cy.writeIntoStringField('QtyEntered', salesInvoiceLine.quantity, true, null, true);
    if (salesInvoiceLine.tuQuantity) {
      cy.writeIntoStringField('QtyEnteredTU', salesInvoiceLine.tuQuantity, true, null, true);
    }

    cy.pressDoneButton();

  }
}

export class SalesInvoiceLine {

  setProduct(product) {
    this.product = product;
    return this;
  }

  setPackingItem(packingItem) {
    this.packingItem = packingItem;
    return this;
  }

  setQuantity(quantity) {
    this.quantity = quantity;
    return this;
  }

  setTuQuantity(tuQuantity) {
    this.tuQuantity = tuQuantity;
    return this;
  }

}
