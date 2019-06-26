export class Attribute {
  constructor(name) {
    cy.log(`Attribute - set name = ${name}`);
    this.name = name;
    this.attributeValues = [];
  }

  setName(name) {
    cy.log(`Attribute - set name = ${name}`);
    this.name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`Attribute - set description = ${description}`);
    this.description = description;
    return this;
  }

  setValue(value) {
    cy.log(`Attribute - set value = ${value}`);
    this.value = value;
    return this;
  }

  setAttributeValueType(attributeValueType) {
    cy.log(`Attribute - set AttributeValueType = ${attributeValueType}`);
    this.attributeValueType = attributeValueType;
    return this;
  }

  setInstanceAttribute(isInstanceAttribute) {
    cy.log(`Attribute - set isInstanceAttribute = ${isInstanceAttribute}`);
    this.isInstanceAttribute = isInstanceAttribute;
    return this;
  }

  setPricingRelevant(isPricingRelevant) {
    cy.log(`Attribute - set isPricingRelevant = ${isPricingRelevant}`);
    this.isPricingRelevant = isPricingRelevant;
    return this;
  }

  setStorageRelevant(isStorageRelevant) {
    cy.log(`Attribute - set isStorageRelevant = ${isStorageRelevant}`);
    this.isStorageRelevant = isStorageRelevant;
    return this;
  }

  setAttributeDocumentRelevant(isAttributeDocumentRelevant) {
    cy.log(`Attribute - set isAttributeDocumentRelevant = ${isAttributeDocumentRelevant}`);
    this.isAttributeDocumentRelevant = isAttributeDocumentRelevant;
    return this;
  }

  addAttribiteValue(attributeValue) {
    cy.log(`Attribute - add AttributeValue = ${JSON.stringify(attributeValue)}`);
    this.attributeValues.push(attributeValue);
    return this;
  }

  apply() {
    cy.log(`Attribute - apply - START (name=${this.name})`);
    applyAttribute(this);
    cy.log(`Attribute - apply - END (name=${this.name})`);
    return this;
  }
}

export class AttributeValue {
  constructor(name) {
    cy.log(`Attribute - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`Attribute - set name = ${name}`);
    this.name = name;
    return this;
  }

  setValue(value) {
    cy.log(`AttributeValue - set value = ${value}`);
    this.value = value;
    return this;
  }
}

function applyAttribute(attribute) {
  describe(`Create new Attribute ${attribute.name}`, function() {
    cy.visitWindow('260', 'NEW');
    cy.writeIntoStringField('Name', attribute.name);
    cy.clearField('Value');
    cy.writeIntoStringField('Value', attribute.value);
    cy.writeIntoStringField('Description', attribute.description);
    cy.selectInListField('AttributeValueType', attribute.attributeValueType);
    if (attribute.isInstanceAttribute) {
      cy.clickOnCheckBox('IsInstanceAttribute');
    }
    if (attribute.isPricingRelevant) {
      cy.clickOnCheckBox('IsPricingRelevant');
    }
    if (attribute.isStorageRelevant) {
      cy.clickOnCheckBox('IsStorageRelevant');
    }
    if (attribute.isAttrDocumentRelevant) {
      cy.clickOnCheckBox('IsAttrDocumentRelevant');
    }

    if (attribute.attributeValues.length > 0) {
      attribute.attributeValues.forEach(function(attributeValue) {
        applyAttributeValue(attributeValue);
      });
      cy.get('table tbody tr').should('have.length', attribute.attributeValues.length);

      applyAttributeSet(attribute);
      applyAttributeSetUse(attribute);
    }
  });
}

function applyAttributeValue(attributeValue) {
  cy.get('#tab_M_AttributeValue').click();
  cy.pressAddNewButton();
  cy.writeIntoStringField('Value', attributeValue.value, true);
  cy.writeIntoStringField('Name', attributeValue.name, true);
  cy.pressDoneButton();
}

function applyAttributeSet(attribute) {
  describe(`Create new AttributeSet ${attribute.name}`, function() {
    cy.visitWindow('256', 'NEW');
    cy.writeIntoStringField('Name', attribute.name);
    cy.selectInListField('MandatoryType', 'Optional');
  });
}

function applyAttributeSetUse(attribute) {
  cy.get('#tab_M_AttributeUse').click();
  cy.pressAddNewButton();
  cy.selectInListField('M_Attribute_ID', `${attribute.value}_${attribute.name}`, true /*modal*/);
  cy.pressDoneButton();
}
