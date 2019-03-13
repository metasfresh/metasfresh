export class DataEntrySection {
  constructor(builder) {
    this.name = builder.name;
    this.dataEntrySubGroup = builder.dataEntrySubGroup;
    this.sectionName = builder.sectionName;
    this.seqNo = builder.seqNo;
    this.description = builder.description;
    this.isActive = builder.isActive;
    this.dataEntryLines = builder.dataEntryLines;
  }

  apply() {
    cy.log(`DataEntrySection - apply - START (name=${this.name})`);
    applyDataEntrySection(this);
    cy.log(`DataEntrySection - apply - END (name=${this.name})`);
    return this;
  }

  static get builder() {
    class Builder {
      constructor(name, dataEntrySubGroup) {
        cy.log(`DataEntrySectionBuilder - set name = ${name}; dataEntrySubGroup= ${dataEntrySubGroup}`);
        this.name = name;
        this.dataEntrySubGroup = dataEntrySubGroup;
        this.sectionName = name;
        this.seqNo = undefined;
        this.description = undefined;
        this.isActive = true;
        this.dataEntryLines = [];
      }

      setSectionName(sectionName) {
        cy.log(`DataEntrySectionBuilder - set sectionName = ${sectionName}`);
        this.sectionName = sectionName;
        return this;
      }

      setSeqNo(seqNo) {
        cy.log(`DataEntrySectionBuilder - set seqNo = ${seqNo}`);
        this.seqNo = seqNo;
        return this;
      }

      setDescription(description) {
        cy.log(`DataEntrySectionBuilder - set description = ${description}`);
        this.description = description;
        return this;
      }

      setActive(isActive) {
        cy.log(`DataEntrySectionBuilder - set isActive = ${isActive}`);
        this.isActive = isActive;
        return this;
      }

      addDataEntryLine(dataEntryLine) {
        cy.log(`DataEntrySectionBuilder - add dataEntryLine = ${JSON.stringify(dataEntryLine)}`);
        this.dataEntryLines.push(dataEntryLine);
        return this;
      }

      build() {
        return new DataEntrySection(this);
      }
    }
    return Builder;
  }
}

export class DataEntryLine {
  constructor(builder) {
    this.seqNo = builder.seqNo;
    this.isActive = builder.isActive;
  }

  static get builder() {
    class Builder {
      constructor() {
        cy.log('DataEntryLineBuilder');
        this.seqNo = undefined;
        this.isActive = true;
      }

      setSeqNo(seqNo) {
        cy.log(`DataEntryLineBuilder - set seqNo = ${seqNo}`);
        this.seqNo = seqNo;
        return this;
      }

      setActive(isActive) {
        cy.log(`DataEntryLineBuilder - set isActive = ${isActive}`);
        this.isActive = isActive;
        return this;
      }

      build() {
        return new DataEntryLine(this);
      }
    }
    return Builder;
  }
}

function applyDataEntrySection(dataEntrySection) {
  cy.visitWindow('540593', 'NEW');

  cy.writeIntoLookupListField(
    'DataEntry_SubGroup_ID',
    dataEntrySection.dataEntrySubGroup,
    dataEntrySection.dataEntrySubGroup
  );

  cy.writeIntoStringField('Name', dataEntrySection.name);
  cy.writeIntoStringField('SectionName', dataEntrySection.sectionName);

  if (dataEntrySection.seqNo) {
    cy.writeIntoStringField('SeqNo', `{selectall}{backspace}${dataEntrySection.seqNo}`);
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
    cy.writeIntoStringField('SeqNo', `{selectall}{backspace}${dataEntryLine.seqNo}`, true /*modal*/);
  }
  if (!dataEntryLine.isActive) {
    cy.clickOnIsActive(true /*modal*/);
  }

  cy.pressDoneButton();
}
