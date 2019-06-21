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

    // stupid hack, because otherwise after selecting all in NewPassword there's a request
    // with empty OldPassword. Go figure...
    cy.get('.form-field-OldPassword').click();
    cy.writeIntoStringField('NewPassword', user.password, false, null, true);
    cy.writeIntoStringField('NewPasswordRetype', user.password, false, null, true);

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

      roles.valueExists('Quicktest1').then(res => {
        if (!res) {
          roles.visitNew();

          cy.fixture('user/role_quicktest.json').then(roleJSON => {
            new Role({ ...roleJSON }).apply();
          });
        }
      });
    });

    it(`Create user's roles`, function() {
      users.visit();

      users.getHeaderFilter('Lastname').click();
      users.getRowWithValue(customLastName).dblclick();

      const addNewText = Cypress.messages.window.addNew.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains(addNewText)
        .should('exist')
        .click();

      cy.get('.modal-content-wrapper').should('exist');
      cy.writeIntoLookupListField('AD_Role_ID', 'W', 'WebUI', true);
      cy.pressDoneButton();

      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains(addNewText)
        .should('exist')
        .click();

      cy.get('.modal-content-wrapper').should('exist');
      cy.writeIntoLookupListField('AD_Role_ID', 'Q', 'Quicktest1', true);
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
    });

    it('Select role using form', function() {
      const roleText = Cypress.messages.login.selectRole.caption;

      cy.get('.login-form').contains(roleText, { timeout: 10000 });

      cy.get('.select-dropdown')
        .find('.input-dropdown')
        .click();

      cy.contains('.input-dropdown-list-option', 'WebUI').click();

      const sendText = Cypress.messages.login.send.caption;
      cy.clickButtonWithText(sendText);
    });
  });
});
