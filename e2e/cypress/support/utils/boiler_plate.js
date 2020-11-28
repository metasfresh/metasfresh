export class BoilerPlate {
  setName(name) {
    cy.log(`BoilerPlateBuilder - set name = ${name}`);
    this.name = name;
    return this;
  }

  setSubject(subject) {
    cy.log(`BoilerPlateBuilder - set subject = ${subject}`);
    this.subject = subject;
    return this;
  }

  setTextSnippet(snippet) {
    cy.log(`BoilerPlateBuilder - set snippet = ${snippet}`);
    this.snippet = snippet;
    return this;
  }
  setJasperProcess(jasperProcess) {
    cy.log(`BoilerPlateBuilder - set jasperProcess = ${jasperProcess}`);
    this.jasperProcess = jasperProcess;
    return this;
  }

  apply() {
    cy.log(`BoilerPlate - apply - START (name=${this.name})`);
    applyBoilerPlate(this);
    cy.log(`BoilerPlate - apply - END (name=${this.name})`);
    return this;
  }
}
function applyBoilerPlate(boilerPlate) {
  describe(`Create new BoilerPlate ${boilerPlate.name}`, function() {
    cy.visitWindow(504410, 'NEW');
    cy.writeIntoStringField('Name', boilerPlate.name);
    cy.writeIntoStringField('Subject', boilerPlate.subject);
    cy.writeIntoTextField('TextSnippet', boilerPlate.subject);
    if (this.jasperProcess) {
      cy.selectInListField('JasperProcess_ID', this.jasperProcess);
    }
  });
}
