export class BillOfMaterial {
  constructor() {
    this.lines = [];
  }

  setName(name) {
    cy.log(`BillOfMaterial - set name = ${name}`);
    this.name = name;
    return this;
  }

  setProduct(product) {
    cy.log(`BillOfMaterial - set product = ${product}`);
    this.product = product;
    if (!name) { // if name already set explicitly then don't overwrite it
      this.name = product + '_BOM';
    }
    return this;
  }

  setDocumentNo(documentNo) {
    cy.log(`BillOfMaterial - set documentNo = ${documentNo}`);
    this.documentNo = documentNo;
    return this;
  }

  setIsVerified(isVerified) {
    cy.log(`BillOfMaterial - set isVerified = ${isVerified}`);
    this.isVerified = isVerified;
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
  cy.expectNumberOfRows(billOfMaterial.lines.length);

  if (billOfMaterial.isVerified) {
    cy.executeHeaderActionWithDialog('PP_Product_BOM');
    cy.pressStartButton();
  }
}

function applyBillOfMaterialLine(bomLine) {
  cy.selectTab('PP_Product_BOMLine');
  cy.pressAddNewButton();

  if (bomLine.issueMethod) {
    cy.resetListValue('IssueMethod');
    cy.selectInListField('IssueMethod', bomLine.issueMethod, true);
  }
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

  setIssueMethod(issueMethod) {
    cy.log(`BillOfMaterialLine - set issueMethod = ${issueMethod}`);
    this.issueMethod = issueMethod;
    return this;
  }
}
