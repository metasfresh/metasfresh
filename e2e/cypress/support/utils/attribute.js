export class Attribute {
  constructor(name) {
    cy.log(`Attribute - name = ${name}`);
    this.name = name;
    this.attributeValues = [];
    this.isInstanceAttribute = false;
    this.isPricingRelevant = false;
    this.isStorageRelevant = false;
    this.isAttrDocumentRelevant = false;
  }

  setName(name) {
    cy.log(`Attribute - name = ${name}`);
    this.name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`Attribute - description = ${description}`);
    this.description = description;
    return this;
  }

  setValue(value) {
    cy.log(`Attribute - value = ${value}`);
    this.value = value;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setAttributeValueType(attributeValueType) {
    cy.log(`Attribute - AttributeValueType = ${attributeValueType}`);
    this.attributeValueType = attributeValueType;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setInstanceAttribute(isInstanceAttribute) {
    cy.log(`Attribute - isInstanceAttribute = ${isInstanceAttribute}`);
    this.isInstanceAttribute = isInstanceAttribute;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setPricingRelevant(isPricingRelevant) {
    cy.log(`Attribute - isPricingRelevant = ${isPricingRelevant}`);
    this.isPricingRelevant = isPricingRelevant;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setStorageRelevant(isStorageRelevant) {
    cy.log(`Attribute - isStorageRelevant = ${isStorageRelevant}`);
    this.isStorageRelevant = isStorageRelevant;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setAttrDocumentRelevant(isAttrDocumentRelevant) {
    cy.log(`Attribute - isAttrDocumentRelevant = ${isAttrDocumentRelevant}`);
    this.isAttrDocumentRelevant = isAttrDocumentRelevant;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  addAttributeValue(attributeValue) {
    cy.log(`Attribute - add AttributeValue = ${JSON.stringify(attributeValue)}`);
    this.attributeValues.push(attributeValue);
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  clearAttributeValues() {
    cy.log(`Attribute - Clear attributeValues`);
    this.attributeValues = [];
    return this;
  }

  apply() {
    cy.log(`Attribute - apply - START (name=${this.name})`);
    applyAttribute(this);
    cy.log(`Attribute - apply - END (name=${this.name})`);
    return this;
  }
}

// noinspection JSUnusedGlobalSymbols
export class AttributeValue {
  constructor(name) {
    cy.log(`AttributeValue - name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`AttributeValue - name = ${name}`);
    this.name = name;
    return this;
  }

  setValue(value) {
    cy.log(`AttributeValue - value = ${value}`);
    this.value = value;
    return this;
  }
}

function applyAttribute(attribute) {
  describe(`Create new Attribute ${attribute.name}`, function() {
    cy.visitWindow('260', 'NEW');
    cy.writeIntoStringField('Value', attribute.value);
    cy.writeIntoStringField('Name', attribute.name);
    cy.writeIntoStringField('Description', attribute.description);
    cy.selectInListField('AttributeValueType', attribute.attributeValueType);
    cy.setCheckBoxValue('IsInstanceAttribute', attribute.isInstanceAttribute);
    cy.setCheckBoxValue('IsPricingRelevant', attribute.isPricingRelevant);
    cy.setCheckBoxValue('IsStorageRelevant', attribute.isStorageRelevant);
    cy.setCheckBoxValue('IsAttrDocumentRelevant', attribute.isAttrDocumentRelevant);

    attribute.attributeValues.forEach(function(attributeValue) {
      applyAttributeValue(attributeValue);
    });
    cy.expectNumberOfRows(attribute.attributeValues.length);
  });
}

function applyAttributeValue(attributeValue) {
  cy.get('#tab_M_AttributeValue').click();
  cy.pressAddNewButton();
  cy.writeIntoStringField('Value', attributeValue.value, true);
  cy.writeIntoStringField('Name', attributeValue.name, true);
  cy.pressDoneButton();
}

export class AttributeSet {
  constructor(name) {
    cy.log(`AttributeSet - name = ${name}`);
    this.name = name;
    this.mandatoryType = 'Optional';
    this.attributes = [];
  }

  setName(name) {
    cy.log(`AttributeSet - name = ${name}`);
    this.name = name;
    return this;
  }

  // noinspection JSUnusedGlobalSymbols
  setMandatoryType(mandatoryType) {
    cy.log(`AttributeSet - mandatoryType = ${mandatoryType}`);
    this.mandatoryType = mandatoryType;
    return this;
  }

  addAttribute(attribute) {
    cy.log(`AttributeSet - add attribute = ${JSON.stringify(attribute)}`);
    this.attributes.push(attribute);
    return this;
  }

  apply() {
    cy.log(`AttributeSet - apply - START (${this.name})`);
    applyAttributeSet(this);
    cy.log(`AttributeSet - apply - END (${this.name})`);
  }
}

function applyAttributeSet(attributeSet) {
  describe(`Create new AttributeSet ${attributeSet.name}`, function() {
    cy.visitWindow('256', 'NEW');
    cy.writeIntoStringField('Name', attributeSet.name);
    cy.selectInListField('MandatoryType', attributeSet.mandatoryType);

    attributeSet.attributes.forEach(att => {
      applyAttributeSetUse(att);
    });
    cy.expectNumberOfRows(attributeSet.attributes.length);
  });
}

function applyAttributeSetUse(attribute) {
  cy.get('#tab_M_AttributeUse').click();
  cy.pressAddNewButton();
  cy.selectInListField('M_Attribute_ID', `${attribute}_${attribute}`, true /*modal*/);
  cy.pressDoneButton();
}
