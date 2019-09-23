export class PurchaseInvoice {
  constructor(businessPartnerName, targetDocumentType) {
    cy.log(`PurchaseInvoice - businessPartnerName=${businessPartnerName}, targetDocumentType=${targetDocumentType}`);
    this.businessPartner = businessPartnerName;
    this.targetDocumentType = targetDocumentType;
    this._toString = `businessPartnerName=${businessPartnerName}, targetDocumentType=${targetDocumentType}`;
    this.lines = [];
  }

  addLine(purchaseInvoiceLine) {
    //console.log(`PurchaseInvoice - add InvoiceLine = ${JSON.stringify(purchaseInvoiceLine)}`);
    this.lines.push(purchaseInvoiceLine);
    return this;
  }

  setPriceList(priceList) {
    this.priceList = priceList;
    return this;
  }

  apply() {
    cy.log(`PurchaseInvoice - apply START (${this._toString})`);
    PurchaseInvoice.applyPurchaseInvoice(this);
    cy.log(`PurchaseInvoice - apply STOP (${this._toString})`);
  }

  static applyPurchaseInvoice(purchaseInvoice) {
    cy.visitWindow('183', 'NEW');

    cy.get('.header-breadcrumb-sitename')
      .should('contain', new Date().getDate())
      .should('contain', new Date().getFullYear());

    cy.writeIntoLookupListField('C_BPartner_ID', purchaseInvoice.businessPartner, purchaseInvoice.businessPartner);

    cy.getStringFieldValue('M_PriceList_ID').should('not.be.empty');
    cy.getStringFieldValue('C_Currency_ID').should('not.be.empty');
    if (purchaseInvoice.priceList) {
      cy.selectInListField('M_PriceList_ID', purchaseInvoice.priceList);
    }

    cy.getStringFieldValue('DocumentNo').should('be.empty');
    cy.selectInListField('C_DocTypeTarget_ID', purchaseInvoice.targetDocumentType);
    cy.getStringFieldValue('DocumentNo').should('not.be.empty');

    purchaseInvoice.lines.forEach(line => {
      PurchaseInvoice.applyLine(line);
    });
  }

  /**
   * Possible workaround for applyLine_buggy:
   * It could be that setting the qty before setting the product id will fix the issue where 'done' button is pressed BEFORE
   * the QtyEntered is saved.
   *
   * This is just a workaround though which isn't very cool to use.
   */
  static applyLine(purchaseInvoiceLine) {
    cy.selectTab('C_InvoiceLine');
    cy.pressAddNewButton();

    cy.writeIntoStringField(
      'QtyEntered',
      purchaseInvoiceLine.quantity,
      true /*modal*/,
      null /*rewriteUrl*/,
      true /*noRequest, bc the patch response is e.g. 20 and we would be waiting for e.g. '20' */
    );
    // instead of waiting for the patch in writeIntoStringField, we wait for the "pending" indicator to go away
    cy.get('.indicator-pending').should('not.exist');

    cy.writeIntoLookupListField('M_Product_ID', purchaseInvoiceLine.product, purchaseInvoiceLine.product, false /*typeList*/, true /*modal*/);

    if (purchaseInvoiceLine.tuQuantity) {
      cy.writeIntoStringField('QtyEnteredTU', purchaseInvoiceLine.tuQuantity, true /*modal*/);
    }
    if (purchaseInvoiceLine.packingItem) {
      cy.writeIntoLookupListField('M_HU_PI_Item_Product_ID', purchaseInvoiceLine.packingItem, purchaseInvoiceLine.packingItem, false /*typeList*/, true /*modal*/);
    }
    cy.pressDoneButton();
  }

  /*
  static applyLine_buggy(purchaseInvoiceLine) {
    cy.selectTab('C_InvoiceLine');
    cy.pressAddNewButton();

    cy.writeIntoLookupListField('M_Product_ID', purchaseInvoiceLine.product, purchaseInvoiceLine.product);
    cy.writeIntoStringField('QtyEntered', purchaseInvoiceLine.quantity, true, null, true);

    if (purchaseInvoiceLine.packingItem) {
      cy.writeIntoLookupListField('M_HU_PI_Item_Product_ID', purchaseInvoiceLine.packingItem, purchaseInvoiceLine.packingItem, true, true);
    }
    if (purchaseInvoiceLine.tuQuantity) {
      cy.writeIntoStringField('QtyEnteredTU', purchaseInvoiceLine.tuQuantity, true, null, true);
    }
    // something's flaky around here and, it seems that i need this delay as 'QtyEntered' or 'QtyEnteredTU' is sometimes NOT updated (it remains 1).
    // problem is it only happens sometimes :(.
    // how to fix? i'm not sure :( (is there a function "wait_until_all_requests_are_finished_before_pressing_done" ? maybe that helps)
    // how to quickly test? ask me and i'll help.
    cy.wait(5000);
    cy.pressDoneButton();
  }
   */
}

export class PurchaseInvoiceLine {
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
