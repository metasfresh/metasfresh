export class QualityNote {
  constructor(name) {
    cy.log(`QualityNoteBuilder - set name = ${name}`);
    this.name = name;
    this.value = undefined;
    this.performanceType = undefined;
  }

  setValue(value) {
    cy.log(`QualityNoteBuilder - set value = ${value}`);
    this.value = value;
    return this;
  }

  setName(name) {
    cy.log(`QualityNoteBuilder - set name = ${name}`);
    this.name = name;
    return this;
  }

  setPerformanceType(performanceType) {
    cy.log(`QualityNoteBuilder - add performanceType = ${performanceType}`);
    this.performanceType = performanceType;
    return this;
  }

  apply() {
    cy.log(`QualityNote - apply - START (name=${this.name})`);
    applyQualityNote(this);
    cy.log(`QualityNote - apply - END (name=${this.name})`);
    return this;
  }
}
function applyQualityNote(qualityNote) {
  describe(`Create new quality note ${qualityNote.name}`, function() {
    cy.visitWindow(540316, 'NEW');
    cy.writeIntoStringField('Name', qualityNote.name);
    cy.writeIntoStringField('Value', qualityNote.value);
    cy.selectInListField('PerformanceType', 'Quality Performance');
  });
}
