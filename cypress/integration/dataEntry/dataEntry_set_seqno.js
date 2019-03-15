import { DataEntryGroup } from '../../support/utils/dataEntryGroup';

describe('Create bpartner with custom dataentry based tabs', function() {
  before(function() {
    // login before each test and open the flatrate conditions window
    cy.loginByForm();
  });

  it('Create bpartner with custom dataentry based tabs', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const dataEntryGroupName = `Group1 ${timestamp}`;

    new DataEntryGroup(dataEntryGroupName, 'Business Partner')
      .setTabName('Group1-Tab1')
      .setSeqNo('20')
      .apply();
    
    // these are sortof guards, to demonstrate that other fields work.
    cy.get('.form-field-Name').find('input').should('have.value', dataEntryGroupName);
    cy.get('.form-field-TabName').find('input').should('have.value', 'Group1-Tab1');

    // here it comes: SeqNo is empty although we typed '20'
    cy.get('.form-field-SeqNo').find('input').should('have.value', '20');
  });
});
