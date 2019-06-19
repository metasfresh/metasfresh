import { User } from '../../support/utils/user';
import { Role } from '../../support/utils/role';
import { users } from '../../page_objects/users';
import { roles } from '../../page_objects/roles';

describe('New user tests', function() {
  const timestamp = new Date().getTime();
  let customLastName = null;
  let customEmail = null;
  let password = null;
  let userJSON = null;
  let user = null;

  before(function() {
    cy.fixture('user/user.json').then(userJson => {
      userJSON = userJson;
      password = userJSON.password;
      customLastName = `${userJSON.lastName}_${timestamp}`;
      customEmail = `${timestamp}_${userJSON.email}`;
    });
  });

  it('Create a user', function() {
    user = new User({ ...userJSON, lastName: customLastName, email: customEmail });
    user.apply();

    cy.get('form-field-Login').should('not.exist');
  });

  it('Set user as system', function() {
    user.setSystemUser(true);
    cy.clickOnCheckBox('IsSystemUser');

    user.setLogin(user.lastName.toLowerCase());
    cy.writeIntoStringField('Login', user.login);
  });

  it(`Set user's password`, function() {
    users.visit();

    users.getHeaderFilter('Lastname').click();

    users.getRowWithValue(customLastName).click();

    cy.executeHeaderActionWithDialog('AD_User_ChangePassword');
    cy.writeIntoStringField('NewPassword', user.password, false, null, true);

    cy.get('.modal-content-wrapper .btn-icon')
      .eq(1)
      .click();

    cy.writeIntoStringField('NewPasswordRetype', user.password, false, null, true);

    cy.get('.modal-content-wrapper .btn-icon')
      .eq(2)
      .click();

    cy.pressStartButton();
    cy.get('.modal-content-wrapper').should('not.exist');
  });

  context('Existing user tests', function() {
    before(function() {
      roles.visit();
      roles.verifyElements();

      // Check if WebUI role already exists - if not, add it
      roles.valueExists('WebUI').then(res => {
        if (!res) {
          roles.visitNew();

          cy.fixture('user/role_webui.json').then(roleJSON => {
            new Role({ ...roleJSON }).apply();
          });
        }
      });
    });

    it(`Create user's role`, function() {
      users.visit();

      users.getHeaderFilter('Lastname').click();
      users.getRowWithValue(customLastName).dblclick();

      const addNewText = Cypress.messages.window.addNew.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains(addNewText)
        .should('exist')
        .click();

      cy.writeIntoLookupListField('AD_Role_ID', 'W', 'WebUI', true);
      cy.pressDoneButton();
    });

    it('Logout', function() {
      cy.visit('/logout');

      cy.url().should('include', 'login');
    });

    it('Re-login using form', function() {
      cy.get('[name="username"]')
        .type('{selectall}')
        .type(`${user.lastName.toLowerCase()}`)
        .type('{enter}');

      cy.get('[name="password"]')
        .type('{selectall}')
        .type(`${password}`)
        .type('{enter}');

      const loginText = Cypress.messages.login.login.caption;
      cy.clickButtonWithText(loginText);
    });

    it('Select role using form', function() {
      const roleText = Cypress.messages.login.selectRole.caption;

      cy.get('.form-control-label').contains(roleText);

      cy.get('.select-dropdown')
        .find('.input-dropdown')
        .click();

      cy.contains('.input-dropdown-list-option', 'WebUI').click();

      const sendText = Cypress.messages.login.send.caption;
      cy.clickButtonWithText(sendText);
    });
  });
});
