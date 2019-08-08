import { DataEntryTab } from '../../support/utils/dataEntryTab';
import { humanReadableNow } from '../../support/utils/utils';

describe('Reproduce issue https://github.com/metasfresh/metasfresh-webui-frontend/issues/2214', function() {
  it('Create dataEntry group with SeqNo 21', function() {
    const date = humanReadableNow();
    const dataEntryTabName = `Group1 ${date}`;

    new DataEntryTab(dataEntryTabName, 'Business Partner')
      .setTabName('Group1-Tab1')
      .setSeqNo(21)
      .apply();

    // these are sortof guards, to demonstrate that other fields work.
    cy.get('.form-field-Name')
      .find('input')
      .should('have.value', dataEntryTabName);
    cy.get('.form-field-TabName')
      .find('input')
      .should('have.value', 'Group1-Tab1');

    // here it comes: SeqNo has a value of '21', as entered by us
    cy.get('.form-field-SeqNo')
      .find('input')
      .should('have.value', '21');

    // deactivate the custom tab, because we don't want other tests to unexpectedly have it among their respective bpartner-tabs
    cy.clickOnIsActive();
  });
});
