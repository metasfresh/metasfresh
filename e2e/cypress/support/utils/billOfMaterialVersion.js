export class BillOfMaterialVersion {
  constructor() {
    this.lines = [];
  }

  setName(name) {
    cy.log(`BillOfMaterialVersion - set name = ${name}`);
    this.name = name;
    return this;
  }

  setProduct(product) {
    cy.log(`BillOfMaterialVersion - set product = ${product}`);
    this.product = product;
    if (!name) { // if name already set explicitly then don't overwrite it
      this.name = product + '_BOM';
    }
    return this;
  }

  setDocumentNo(documentNo) {
    cy.log(`BillOfMaterialVersion - set documentNo = ${documentNo}`);
    this.documentNo = documentNo;
    return this;
  }

  setIsVerified(isVerified) {
    cy.log(`BillOfMaterialVersion - set isVerified = ${isVerified}`);
    this.isVerified = isVerified;
    return this;
  }

  addLine(bomLine) {
    cy.log(`BillOfMaterialVersion - add BOMLine = ${JSON.stringify(bomLine)}`);
    this.lines.push(bomLine);
    return this;
  }

  setBOM(bom) {
    cy.log(`BillOfMaterialVersion - set BOM = ${JSON.stringify(bom)}`);
    this.bom = bom;
    return this;
  }

  apply() {
    cy.log(`BillOfMaterialVersion - apply - START (name=${this.product})`);
    applyBillOfMaterialVersion(this);
    cy.log(`BillOfMaterialVersion - apply - END (name=${this.product})`);
  }
}

function applyBillOfMaterialVersion(billOfMaterialVersion) {
  cy.visitWindow('53006', 'NEW');

  cy.writeIntoTextField('Name', billOfMaterialVersion.name);
  cy.selectInListField('PP_Product_BOMVersions_ID', billOfMaterialVersion.bom, false);
  cy.writeIntoStringField('DocumentNo', billOfMaterialVersion.documentNo);

  billOfMaterialVersion.lines.forEach(line => {
    applyBillOfMaterialVersionLine(line);
  });
  cy.expectNumberOfRows(billOfMaterialVersion.lines.length);

  if (billOfMaterialVersion.isVerified) {
    cy.executeHeaderActionWithDialog('PP_Product_BOM');
    cy.pressStartButton();
  }
}

function applyBillOfMaterialVersionLine(bomLine) {
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

export class BillOfMaterialVersionLine {
  setProduct(product) {
    cy.log(`BillOfMaterialVersionLine - set product = ${product}`);
    this.product = product;
    return this;
  }

  setQuantity(quantity) {
    cy.log(`BillOfMaterialVersionLine - set quantity = ${quantity}`);
    this.quantity = quantity;
    return this;
  }

  setScrap(scrap) {
    cy.log(`BillOfMaterialVersionLine - set scrap = ${scrap}`);
    this.scrap = scrap;
    return this;
  }

  setIssueMethod(issueMethod) {
    cy.log(`BillOfMaterialVersionLine - set issueMethod = ${issueMethod}`);
    this.issueMethod = issueMethod;
    return this;
  }
}
