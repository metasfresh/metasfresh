export class User {
  constructor({ lastName, ...vals }) {
    cy.log(`Create user with lastName = ${lastName}`);
    this.lastName = lastName;
    for (let [key, val] of Object.entries(vals)) {
      this[key] = val;
    }
    return this;
  }

  setSystemUser(isSystem) {
    cy.log(`User - set system user = ${isSystem}`);
    this.isSystem = isSystem;
    return this;
  }

  setFirstName(name) {
    cy.log(`User - set first name = ${name}`);
    this.firstName = name;
    return this;
  }

  setLastName(name) {
    cy.log(`User - set last name = ${name}`);
    this.lastName = name;
    return this;
  }

  setEmail(email) {
    cy.log(`User - set email = ${email}`);
    this.email = email;
    return this;
  }

  setLogin(login) {
    cy.log(`User - set login = ${login}`);
    this.login = login;
    return this;
  }

  setPassword(password) {
    cy.log(`User - set password = ${password}`);
    this.password = password;
    return this;
  }

  apply() {
    cy.log(`User - apply - START (name=${this.firstName})`);
    applyUser(this);
    cy.log(`User - apply - END (name=${this.firstName})`);
    return this;
  }
}

function applyUser(user) {
  describe(`Create new user ${user.name}`, function() {
    cy.visitWindow('108', 'NEW');
    cy.writeIntoStringField('Firstname', user.firstName);
    cy.writeIntoStringField('Lastname', user.lastName);
    cy.writeIntoStringField('EMail', user.email);
  });
}
