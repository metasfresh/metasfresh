export class DataEntrySection {
  constructor(name, dataEntrySubTab) {
    cy.log(`DataEntrySection - set name = ${name}; dataEntrySubTab= ${dataEntrySubTab}`);
    this.name = name;
    this.dataEntrySubTab = dataEntrySubTab;
    this.sectionName = name;
    this.seqNo = undefined;
    this.description = undefined;
    this.isActive = true;
    this.dataEntryLines = [];
  }

  setSectionName(sectionName) {
    cy.log(`DataEntrySection - set sectionName = ${sectionName}`);
    this.sectionName = sectionName;
    return this;
  }

  setSeqNo(seqNo) {
    cy.log(`DataEntrySection - set seqNo = ${seqNo}`);
    this.seqNo = seqNo;
    return this;
  }

  setDescription(description) {
    cy.log(`DataEntrySection - set description = ${description}`);
    this.description = description;
    return this;
  }

  setActive(isActive) {
    cy.log(`DataEntrySection - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }

  addDataEntryLine(dataEntryLine) {
    cy.log(`DataEntrySection - add dataEntryLine = ${JSON.stringify(dataEntryLine)}`);
    this.dataEntryLines.push(dataEntryLine);
    return this;
  }

  apply() {
    cy.log(`DataEntrySection - apply - START (name=${this.name})`);
    applyDataEntrySection(this);
    cy.log(`DataEntrySection - apply - END (name=${this.name})`);
    return this;
  }
}

export class DataEntryLine {
  constructor() {
    cy.log('DataEntryLine');
    this.seqNo = undefined;
    this.isActive = true;
  }

  setSeqNo(seqNo) {
    cy.log(`DataEntryLine - set seqNo = ${seqNo}`);
    this.seqNo = seqNo;
    return this;
  }

  setActive(isActive) {
    cy.log(`DataEntryLine - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }
}

function applyDataEntrySection(dataEntrySection) {
  cy.visitWindow('540593', 'NEW');

  cy.writeIntoLookupListField(
    'DataEntry_SubTab_ID',
    dataEntrySection.dataEntrySubTab,
    dataEntrySection.dataEntrySubTab
  );

  cy.writeIntoStringField('Name', dataEntrySection.name);
  cy.writeIntoStringField('SectionName', dataEntrySection.sectionName);

  if (dataEntrySection.seqNo) {
    cy.getStringFieldValue('SeqNo').then(currentValue => {
      if (currentValue !== dataEntrySection.seqNo) {
        cy.log(
          `applyDataEntrySection - dataEntrySection.seqNo=${dataEntrySection.seqNo}; currentValue=${currentValue}`
        );
        cy.clearField('SeqNo');
        cy.writeIntoStringField('SeqNo', `${dataEntrySection.seqNo}`);
      }
    });
  }
  if (dataEntrySection.description) {
    cy.writeIntoTextField('Description', dataEntrySection.description);
  }
  if (!dataEntrySection.isActive) {
    cy.clickOnIsActive();
  }

  // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
  if (dataEntrySection.dataEntryLines.length > 0) {
    dataEntrySection.dataEntryLines.forEach(function(dataEntryLine) {
      applyDataEntryLine(dataEntryLine);
    });
    cy.get('table tbody tr').should('have.length', dataEntrySection.dataEntryLines.length);
  }
}

function applyDataEntryLine(dataEntryLine) {
  cy.selectTab('DataEntry_Line');
  cy.pressAddNewButton();

  if (dataEntryLine.seqNo) {
    cy.getStringFieldValue('SeqNo', true /*modal*/).then(currentValue => {
      cy.log(`applyDataEntryLine - dataEntryTab.seqNo=${dataEntryLine.seqNo}; currentValue=${currentValue}`);
      if (currentValue !== dataEntryLine.seqNo) {
        cy.clearField('SeqNo', true /*modal*/);
        cy.writeIntoStringField('SeqNo', `${dataEntryLine.seqNo}`, true /*modal*/);
      }
    });
  }
  if (!dataEntryLine.isActive) {
    cy.clickOnIsActive(true /*modal*/);
  }

  cy.pressDoneButton();
}
