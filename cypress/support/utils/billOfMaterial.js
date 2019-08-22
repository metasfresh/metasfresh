export class BillOfMaterial {
  constructor() {
    this.lines = [];
  }

  setProduct(product) {
    cy.log(`BillOfMaterial - set product = ${product}`);
    this.product = product;
    this.name = product + '_BOM';
    return this;
  }

  setDocumentNo(documentNo) {
    cy.log(`BillOfMaterial - set documentNo = ${documentNo}`);
    this.documentNo = documentNo;
    return this;
  }

  addLine(bomLine) {
    cy.log(`BillOfMaterial - add BOMLine = ${JSON.stringify(bomLine)}`);
    this.lines.push(bomLine);
    return this;
  }

  apply() {
    cy.log(`BillOfMaterial - apply - START (name=${this.product})`);
    applyBillOfMaterial(this);
    cy.log(`BillOfMaterial - apply - END (name=${this.product})`);
  }
}

function applyBillOfMaterial(billOfMaterial) {
  cy.visitWindow('53006', 'NEW');

  cy.writeIntoTextField('Name', billOfMaterial.name);
  cy.writeIntoLookupListField('M_Product_ID', billOfMaterial.product, billOfMaterial.product);
  cy.writeIntoStringField('DocumentNo', billOfMaterial.documentNo);

  billOfMaterial.lines.forEach(line => {
    applyBillOfMaterialLine(line);
  });
}

function applyBillOfMaterialLine(bomLine) {
  cy.selectTab('PP_Product_BOMLine');
  cy.pressAddNewButton();

  cy.writeIntoLookupListField('M_Product_ID', bomLine.product, bomLine.product, false, true);

  if (bomLine.quantity) {
    cy.writeIntoStringField('QtyBOM', bomLine.quantity, true);
  }
  if (bomLine.scrap) {
    cy.writeIntoStringField('Scrap', bomLine.scrap, true);
  }

  cy.pressDoneButton();
}

export class BillOfMaterialLine {
  setProduct(product) {
    cy.log(`BillOfMaterialLine - set product = ${product}`);
    this.product = product;
    return this;
  }

  setQuantity(quantity) {
    cy.log(`BillOfMaterialLine - set quantity = ${quantity}`);
    this.quantity = quantity;
    return this;
  }

  setScrap(scrap) {
    cy.log(`BillOfMaterialLine - set scrap = ${scrap}`);
    this.scrap = scrap;
    return this;
  }
}
