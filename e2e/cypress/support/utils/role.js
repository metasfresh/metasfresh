export class Role {
  constructor({ lastName, ...vals }) {
    cy.log(`Create role with nameame = ${name}`);
    this.lastName = lastName;
    for (let [key, val] of Object.entries(vals)) {
      this[key] = val;
    }
    return this;
  }

  setActive(isActive) {
    cy.log(`Role - set active`);
    this.isActive = isActive;
    return this;
  }

  setAutoLogin(autoLogin) {
    cy.log(`Role - set auto login`);
    this.autoLogin = autoLogin;
    return this;
  }

  apply() {
    cy.log(`Role - apply - START (name=${this.name})`);
    applyRole(this);
    cy.log(`Role - apply - END (name=${this.name})`);
    return this;
  }
}

function applyRole(role) {
  describe(`Create new role ${role.name}`, function() {
    cy.visitWindow('111', 'NEW');
    cy.writeIntoStringField('Name', role.name);

    if (role.isAutoLogin) {
      cy.clickOnCheckBox('IsAutoRoleLogin');
    }
    if (!role.isActive) {
      cy.clickOnIsActive();
    }
  });
}
