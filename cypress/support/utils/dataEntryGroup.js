export class DataEntryGroup {
  constructor(name, targetWindowName) {
    cy.log(`DataEntryGroup - set name = ${name}; targetWindowName = ${targetWindowName}`);
    this.name = name;
    this.tabName = name;
    this.targetWindowName = targetWindowName;
    this.seqNo = undefined;
    this.description = undefined;
    this.isActive = true;
    this.dataEntrySubGroups = [];
    this.dataEntrySections = [];
  }

  setTabName(tabName) {
    cy.log(`DataEntryGroup - set tabName = ${tabName}`);
    this.tabName = tabName;
    return this;
  }
  setSeqNo(seqNo) {
    cy.log(`DataEntryGroup - set seqNo = ${seqNo}`);
    this.seqNo = seqNo;
    return this;
  }
  setDescription(description) {
    cy.log(`DataEntryGroup - set description = ${description}`);
    this.description = description;
    return this;
  }

  setActive(isActive) {
    cy.log(`DataEntryGroup - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }

  addDataEntrySubGroup(dataEntrySubGroup) {
    cy.log(`DataEntryGroup - add dataEntrySubGroup = ${JSON.stringify(dataEntrySubGroup)}`);
    this.dataEntrySubGroups.push(dataEntrySubGroup);
    return this;
  }

  addDataEntrySection(dataEntrySection) {
    cy.log(`DataEntryGroup - add dataEntrySection = ${JSON.stringify(dataEntrySection)}`);
    this.dataEntrySections.push(dataEntrySection);
    return this;
  }

  apply() {
    cy.log(`DataEntryGroup - apply - START (name=${this.name})`);
    const result = applyDataEntryGroup(this);
    cy.log(`DataEntryGroup - apply - END (name=${this.name}; result=${JSON.stringify(result)})`);
    return this;
  }
}

export class DataEntrySubGroup {
  constructor(name) {
    cy.log(`DataEntrySubGroup - set name = ${name}`);
    this.name = name;
    this.tabName = name;
    this.seqNo = undefined;
    this.description = undefined;
    this.isActive = true;
  }

  setTabName(tabName) {
    cy.log(`DataEntrySubGroup - set tabName = ${tabName}`);
    this.tabName = tabName;
    return this;
  }

  setSeqNo(seqNo) {
    cy.log(`DataEntrySubGroup - set seqNo = ${seqNo}`);
    this.seqNo = seqNo;
    return this;
  }

  setDescription(description) {
    cy.log(`DataEntrySubGroup - set description = ${description}`);
    this.description = description;
    return this;
  }

  setActive(isActive) {
    cy.log(`DataEntrySubGroup - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }
}

function applyDataEntryGroup(dataEntryGroup) {
  describe(`Create new dataEntryGroup ${dataEntryGroup.name}`, function() {
    cy.visitWindow('540571', 'NEW', dataEntryGroup.name /*documentIdAliasName*/);
    cy.log(`applyDataEntryGroup - visitWindow yielded ${JSON.stringify(this.dataEntryGroupId)}`);

    cy.writeIntoStringField('Name', dataEntryGroup.name);
    cy.writeIntoStringField('TabName', dataEntryGroup.tabName);
    cy.writeIntoLookupListField(
      'DataEntry_TargetWindow_ID',
      dataEntryGroup.targetWindowName,
      dataEntryGroup.targetWindowName
    );

    if (dataEntryGroup.seqNo) {
      cy.writeIntoStringField('SeqNo', `{selectall}{backspace}${dataEntryGroup.seqNo}`);
    }
    if (dataEntryGroup.description) {
      cy.writeIntoTextField('Description', dataEntryGroup.description);
    }
    if (!dataEntryGroup.isActive) {
      cy.clickOnIsActive(true /*modal*/);
    }

    // Thx to https://stackoverflow.com/questions/16626735/how-to-loop-through-an-array-containing-objects-and-access-their-properties
    if (dataEntryGroup.dataEntrySubGroups.length > 0) {
      dataEntryGroup.dataEntrySubGroups.forEach(function(dataEntrySubGroup) {
        applyDataEntrySubGroup(dataEntrySubGroup);
      });

      cy.get('table tbody tr').should('have.length', dataEntryGroup.dataEntrySubGroups.length);
    }
  });
}

function applyDataEntrySubGroup(dataEntrySubGroup) {
  cy.selectTab('DataEntry_SubGroup');
  cy.pressAddNewButton(dataEntrySubGroup.name);

  cy.writeIntoStringField('Name', dataEntrySubGroup.name, true /*modal*/);
  cy.writeIntoStringField('TabName', dataEntrySubGroup.tabName, true /*modal*/);

  if (dataEntrySubGroup.seqNo) {
    cy.writeIntoStringField('SeqNo', `{selectall}{backspace}${dataEntrySubGroup.seqNo}`, true /*modal*/);
  }
  if (dataEntrySubGroup.description) {
    cy.writeIntoTextField('Description', dataEntrySubGroup.description, true /*modal*/);
  }
  if (!dataEntrySubGroup.isActive) {
    cy.clickOnIsActive(true /*modal*/);
  }

  cy.pressDoneButton();
}
