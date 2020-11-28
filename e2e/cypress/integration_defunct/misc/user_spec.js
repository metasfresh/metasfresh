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
  let userId = null;

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
    // cy.get(`@${customEmail}`).then(user => {
    //   userId = user.documentId;
    // })

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

    // the following two failed (locally and when run from jenkins) with
    // ---
    // CypressError: Timed out retrying: cy.click() failed because this element:
    // <span title="Lastname" class="th-caption">Lastname</span>
    //  is being covered by another element:
    // <div class="header-breadcrumb">...</div>
    //  Fix this problem, or use {force: true} to disable error checking.
    // ---
    // so, instead we directly open the user's document view
    // users.getHeaderFilter('Lastname').click();
    // users.getRowWithValue(customLastName).click();
    cy.visitWindow(users.windowId, userId);

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

      // fails in the same manner as further up
      // users.getHeaderFilter('Lastname').click();
      // users.getRowWithValue(customLastName).dblclick();
      cy.visitWindow(users.windowId, userId);

      cy.pressAddNewButton();
      cy.writeIntoLookupListField('AD_Role_ID', 'WebU', 'WebUI', true, true /*modal */);
      cy.pressDoneButton();

      // add another role so we will later get the role selection dialog
      // on my local machine, this failed with
      // ---
      //Error: Uncaught TypeError: Cannot read property 'value' of null (http://localhost:30080/bundle-856223f013aa9eba84ce-git-3d66a8f.js:95)
      // ---
      cy.pressAddNewButton();
      cy.writeIntoLookupListField('AD_Role_ID', 'Quickt', 'Quicktest1', true, true /*modal */);
      cy.pressDoneButton();
    });

    it('Logout', function() {
      cy.visit('/logout');

      cy.url().should('include', 'login');
    });

    it('Wait for login prompt and re-login using form', function() {
      cy.get('[name="username"]')
        .should('exist')
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
