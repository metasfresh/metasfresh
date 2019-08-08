import { SysConfig } from '../../support/utils/sysconfig';
import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';
import { sysconfigs } from '../../page_objects/sysconfig';
import { humanReadableNow } from '../../support/utils/utils';

describe('SysConfig Tests', function() {
  it('Filter for a sysconfig entry', function() {
    sysconfigs.visit();

    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('Name', 'webui.frontend.cors.enabled', false, null, true);

    applyFilters();

    sysconfigs.getRows().should('have.length', 1);
  });

  const date = humanReadableNow();
  const sysConfigName = `SysConfig ${date}`;
  const sysConfigValue = `Value ${date}`;
  const sysConfigDescription = `Description ${date}`;

  it('Create a sysconfig entry', function() {
    cy.log(`Create Sysconfig with name=${sysConfigName}`);
    new SysConfig.builder(sysConfigName)
      .setValue(sysConfigValue)
      .setDescription(sysConfigDescription)
      .build()
      .apply();
    cy.screenshot();
  });
});
