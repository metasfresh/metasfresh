import { User } from '../../utils/user';

describe('New user tests', function() {
  cy.fixture('price/user.json').then(userJson => {
    const timestamp = new Date().getTime();
    const customLastname = `${userJson.lastName}_${timestamp}`;
    let user = null;

    it('Create new User', function() {
      user = Object.assign(new User(customLastname), userJson);
      user.apply(userJson);
    });

    it('Set user as system', function() {
      user.setSystemUser(true);
      cy.clickOnCheckBox('IsSystemUser');
    });

    it(`Set user's password`, function() {
      cy.clickHeaderNav();
      cy.executeHeaderActionWithDialog('AD_User_ChangeMyPassword');
      cy.writeIntoStringField('NewPassword', userJson.password);
      cy.writeIntoStringField('NewPasswordRetype', userJson.password);
    });
  });
    // cy.get('@priceListObj').then(obj => {
    //   // access the users argument
    //   cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
    //     cy.log(`PriceList - Name = ${priceListName}`);
    //     cy.log(`PriceList - DocID = ${obj.documentId}`);
    //     Object.assign(new PriceListVersion(`${priceListName}`, `${obj.documentId}`), priceListVersionJson).apply();
    //   });
    // });
});
