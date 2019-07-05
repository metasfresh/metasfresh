export class DataEntryField {
  constructor(name, dataEntryLine) {
    cy.log(`DataEntryField - set name = ${name}; dataEntryLine = ${dataEntryLine}`);
    this.name = name;
    this.dataEntryLine = dataEntryLine;
    this.seqNo = undefined;
    this.description = undefined;
    this.isActive = true;
    this.dataEntryRecordType = undefined;
    this.isMandatory = undefined;
    this.personalDataCategory = undefined;
    this.dataEntryListValues = [];
  }

  setName(name) {
    cy.log(`DataEntryField - set name = ${name}`);
    this.name = name;
    return this;
  }

  setDataEntryLine(dataEntryLine) {
    cy.log(`DataEntryField - set dataEntryLine = ${dataEntryLine}`);
    this.dataEntryLine = dataEntryLine;
    return this;
  }

  setSeqNo(seqNo) {
    cy.log(`DataEntryField - set seqNo = ${seqNo}`);
    this.seqNo = seqNo;
    return this;
  }

  setDescription(description) {
    cy.log(`DataEntryField - set description = ${description}`);
    this.description = description;
    return this;
  }

  setActive(isActive) {
    cy.log(`DataEntryField - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }

  setDataEntryRecordType(dataEntryRecordType) {
    cy.log(`DataEntryField - set dataEntryRecordType = ${dataEntryRecordType}`);
    this.dataEntryRecordType = dataEntryRecordType;
    return this;
  }

  setMandatory(isMandatory) {
    cy.log(`DataEntryField - set isMandatory = ${isMandatory}`);
    this.isMandatory = isMandatory;
    return this;
  }

  setPersonalDataCategory(personalDataCategory) {
    cy.log(`DataEntryField - set personalDataCategory = ${personalDataCategory}`);
    this.personalDataCategory = personalDataCategory;
    return this;
  }

  addDataEntryListValue(dataEntryListValue) {
    cy.log(`DataEntryField - add dataEntryListValue = ${JSON.stringify(dataEntryListValue)}`);
    this.dataEntryListValues.push(dataEntryListValue);
    return this;
  }

  apply() {
    cy.log(`DataEntryField - apply - START (name=${this.name})`);
    applyDataEntryField(this);
    cy.log(`DataEntryField - apply - END (name=${this.name})`);
    return this;
  }
}

export class DataEntryListValue {
  constructor(name) {
    cy.log(`DataEntryListValue - set name = ${name}`);
    this.name = name;
    this.seqNo = undefined;
    this.description = undefined;
    this.isActive = true;
  }

  setSeqNo(seqNo) {
    cy.log(`DataEntryListValue - set seqNo = ${seqNo}`);
    this.seqNo = seqNo;
    return this;
  }

  setDescription(description) {
    cy.log(`DataEntryListValue - set description = ${description}`);
    this.description = description;
    return this;
  }

  setActive(isActive) {
    cy.log(`DataEntryListValue - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }
}

function applyDataEntryField(dataEntryField) {
  describe(`Create new dataEntryField ${dataEntryField.name}`, function() {
    cy.visitWindow('540572', 'NEW');
    cy.writeIntoStringField('Name', dataEntryField.name);
    cy.writeIntoLookupListField('DataEntry_Line_ID', dataEntryField.dataEntryLine, dataEntryField.dataEntryLine);

    if (dataEntryField.seqNo) {
      cy.getStringFieldValue('SeqNo').then(currentValue => {
        if (currentValue !== dataEntryField.seqNo) {
          cy.log(`applyDataEntryField - dataEntryGroup.seqNo=${dataEntryField.seqNo}; currentValue=${currentValue}`);
          cy.clearField('SeqNo');
          cy.writeIntoStringField('SeqNo', `${dataEntryField.seqNo}`);
        }
      });
    }
    if (dataEntryField.description) {
      cy.writeIntoTextField('Description', dataEntryField.description);
    }
    if (!dataEntryField.isActive) {
      cy.clickOnIsActive();
    }
    if (dataEntryField.isMandatory) {
      cy.clickOnCheckBox('IsMandatory');
    }
    if (dataEntryField.personalDataCategory) {
      cy.selectInListField('PersonalDataCategory', dataEntryField.personalDataCategory);
    }
    if (dataEntryField.dataEntryRecordType) {
      cy.getStringFieldValue('DataEntry_RecordType').then(currentValue => {
        if (currentValue !== dataEntryField.dataEntryRecordType) {
          cy.log(
            `applyDataEntryField - dataEntryField.dataEntryRecordType=${
              dataEntryField.dataEntryRecordType
            }; currentValue=${currentValue}`
          );
          cy.selectInListField('DataEntry_RecordType', dataEntryField.dataEntryRecordType);
        }
      });
    }

    // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
    if (dataEntryField.dataEntryListValues.length > 0) {
      dataEntryField.dataEntryListValues.forEach(function(dataEntryListValue) {
        applyDataEntryListValue(dataEntryListValue);
      });

      cy.get('table tbody tr').should('have.length', dataEntryField.dataEntryListValues.length);
    }
  });
}

function applyDataEntryListValue(dataEntryListValue) {
  cy.selectTab('DataEntry_ListValue');
  cy.pressAddNewButton();

  cy.writeIntoStringField('Name', dataEntryListValue.name, true /*modal*/);
  if (dataEntryListValue.seqNo) {
    cy.getStringFieldValue('SeqNo', true /*modal*/).then(currentValue => {
      cy.log(
        `applyDataEntryListValue - dataEntryGroup.seqNo=${dataEntryListValue.seqNo}; currentValue=${currentValue}`
      );
      if (currentValue !== dataEntryListValue.seqNo) {
        cy.clearField('SeqNo', true /*modal*/);
        cy.writeIntoStringField('SeqNo', `${dataEntryListValue.seqNo}`, true /*modal*/);
      }
    });
  }
  if (dataEntryListValue.description) {
    cy.writeIntoTextField('Description', dataEntryListValue.description, true /*modal*/);
  }
  if (!dataEntryListValue.isActive) {
    cy.clickOnIsActive(true /*modal*/);
  }

  cy.pressDoneButton();
}
