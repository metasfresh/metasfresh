import { DataEntryGroup } from '../../support/utils/dataEntryGroup';

describe('Reproduce issue https://github.com/metasfresh/metasfresh-webui-frontend/issues/2214', function() {
  before(function() {
    // login before each test and open the flatrate conditions window
    cy.loginByForm();
  });

  it('Create dataEntry group with SeqNo 21', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const dataEntryGroupName = `Group1 ${timestamp}`;

    new DataEntryGroup(dataEntryGroupName, 'Business Partner')
      .setTabName('Group1-Tab1')
      .setSeqNo(21)
      .apply();

    // these are sortof guards, to demonstrate that other fields work.
    cy.get('.form-field-Name')
      .find('input')
      .should('have.value', dataEntryGroupName);
    cy.get('.form-field-TabName')
      .find('input')
      .should('have.value', 'Group1-Tab1');

    // here it comes: SeqNo has a value of '21', as entered by us
    cy.get('.form-field-SeqNo')
      .find('input')
      .should('have.value', '21');
  });
});
