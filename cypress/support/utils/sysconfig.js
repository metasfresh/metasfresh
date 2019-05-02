export class SysConfig {
  constructor(builder) {
    this.name = builder.name;
    this.value = builder.value;
  }

  apply() {
    cy.log(`Sysconfig - apply - START (name=${this.name})`);
    applySysConfig(this);
    cy.log(`Sysconfig - apply - END (name=${this.name})`);
    return this;
  }

  static get builder() {
    class Builder {
      constructor(name) {
        cy.log(`SysConfigBuilder - set name = ${name}`);
        this.name = name;
        this.value = null;
      }
      setValue(value) {
        cy.log(`SysConfigBuilder - set value = ${value}`);
        this.value = value;
        return this;
      }
      setDescription(description) {
        cy.log(`Sysconfig - set description = ${description}`);
        this.description = description;
        return this;
      }

      build() {
        return new SysConfig(this);
      }
    }
    return Builder;
  }
}

function applySysConfig(sysConfig) {
  describe(`Sysconfig with name=${sysConfig.name}`, function() {
    cy.visit('/window/50006/NEW');

    cy.writeIntoStringField('Name', sysConfig.name);
    cy.writeIntoStringField('Value', sysConfig.value);
  });
}
