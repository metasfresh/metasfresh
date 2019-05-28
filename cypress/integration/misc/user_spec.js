import { User } from '../../support/utils/user';
import { users } from '../../page_objects/users';

describe('New user tests', function() {
  const timestamp = new Date().getTime();
  let customLastName = null;
  let userJSON = null;
  let user = null;

  before(function() {
    cy.fixture('user/user.json').then(userJson => {
      userJSON = userJson;
      customLastName = `${userJSON.lastName}_${timestamp}`;
    });
  });

  it('Create a user', function() {
    user = new User({ ...userJSON, lastName: customLastName });
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

    users.getRowWithValue(customLastName).click();

    cy.executeHeaderActionWithDialog('AD_User_ChangePassword');
    cy.writeIntoStringField('NewPassword', user.password);
    cy.writeIntoStringField('NewPasswordRetype', user.password);
  });
});
