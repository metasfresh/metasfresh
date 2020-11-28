export class DataEntryTab {
  constructor(name, targetWindowName) {
    cy.log(`DataEntryTab - set name = ${name}; targetWindowName = ${targetWindowName}`);
    this.name = name;
    this.tabName = name;
    this.targetWindowName = targetWindowName;
    this.seqNo = undefined;
    this.description = undefined;
    this.isActive = true;
    this.dataEntrySubTabs = [];
    this.dataEntrySections = [];
  }

  setTabName(tabName) {
    cy.log(`DataEntryTab - set tabName = ${tabName}`);
    this.tabName = tabName;
    return this;
  }

  setSeqNo(seqNo) {
    cy.log(`DataEntryTab - set seqNo = ${seqNo}`);
    this.seqNo = seqNo;
    return this;
  }

  setDescription(description) {
    cy.log(`DataEntryTab - set description = ${description}`);
    this.description = description;
    return this;
  }

  setActive(isActive) {
    cy.log(`DataEntryTab - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }

  addDataEntrySubTab(dataEntrySubTab) {
    cy.log(`DataEntryTab - add dataEntrySubTab = ${JSON.stringify(dataEntrySubTab)}`);
    this.dataEntrySubTabs.push(dataEntrySubTab);
    return this;
  }

  addDataEntrySection(dataEntrySection) {
    cy.log(`DataEntryTab - add dataEntrySection = ${JSON.stringify(dataEntrySection)}`);
    this.dataEntrySections.push(dataEntrySection);
    return this;
  }

  apply() {
    cy.log(`DataEntryTab - apply - START (name=${this.name})`);
    const result = applyDataEntryTab(this);
    cy.log(`DataEntryTab - apply - END (name=${this.name}; result=${JSON.stringify(result)})`);
    return this;
  }
}

export class DataEntrySubTab {
  constructor(name) {
    cy.log(`DataEntrySubTab - set name = ${name}`);
    this.name = name;
    this.tabName = name;
    this.seqNo = undefined;
    this.description = undefined;
    this.isActive = true;
  }

  setTabName(tabName) {
    cy.log(`DataEntrySubTab - set tabName = ${tabName}`);
    this.tabName = tabName;
    return this;
  }

  setSeqNo(seqNo) {
    cy.log(`DataEntrySubTab - set seqNo = ${seqNo}`);
    this.seqNo = seqNo;
    return this;
  }

  setDescription(description) {
    cy.log(`DataEntrySubTab - set description = ${description}`);
    this.description = description;
    return this;
  }

  setActive(isActive) {
    cy.log(`DataEntrySubTab - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }
}

function applyDataEntryTab(dataEntryTab) {
  cy.visitWindow('540571', 'NEW');
  // Modified the oredr in which we input things, put  "Eingabefenster" to be the first one - test passes
  cy.writeIntoLookupListField('DataEntry_TargetWindow_ID', dataEntryTab.targetWindowName, dataEntryTab.targetWindowName);
  if (dataEntryTab.seqNo) {
    cy.getStringFieldValue('SeqNo').then(currentValue => {
      if (currentValue !== dataEntryTab.seqNo) {
        cy.clearField('SeqNo');
        cy.writeIntoStringField('SeqNo', `${dataEntryTab.seqNo}`);
      }
    });
  }

  cy.writeIntoStringField('Name', dataEntryTab.name);
  cy.writeIntoStringField('TabName', dataEntryTab.tabName);


  if (dataEntryTab.description) {
    cy.writeIntoTextField('Description', dataEntryTab.description);
  }
  if (!dataEntryTab.isActive) {
    cy.clickOnIsActive(true /*modal*/);
  }

  // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
  if (dataEntryTab.dataEntrySubTabs.length > 0) {
    dataEntryTab.dataEntrySubTabs.forEach(function(dataEntrySubTab) {
      applyDataEntrySubTab(dataEntrySubTab);
    });
    cy.expectNumberOfRows(dataEntryTab.dataEntrySubTabs.length);
  }
}

function applyDataEntrySubTab(dataEntrySubTab) {
  cy.selectTab('DataEntry_SubTab');
  cy.pressAddNewButton(dataEntrySubTab.name);

  cy.writeIntoStringField('Name', dataEntrySubTab.name, true /*modal*/);
  cy.writeIntoStringField('TabName', dataEntrySubTab.tabName, true /*modal*/);

  if (dataEntrySubTab.seqNo) {
    cy.getStringFieldValue('SeqNo', true /*modal*/).then(currentValue => {
      cy.log(`applyDataEntrySubTab - dataEntryTab.seqNo=${dataEntrySubTab.seqNo}; currentValue=${currentValue}`);
      if (currentValue !== dataEntrySubTab.seqNo) {
        cy.clearField('SeqNo', true /*modal*/);
        cy.writeIntoStringField('SeqNo', `${dataEntrySubTab.seqNo}`, true /*modal*/);
      }
    });
  }
  if (dataEntrySubTab.description) {
    cy.writeIntoTextField('Description', dataEntrySubTab.description, true /*modal*/);
  }
  if (!dataEntrySubTab.isActive) {
    cy.clickOnIsActive(true /*modal*/);
  }

  cy.pressDoneButton();
}
