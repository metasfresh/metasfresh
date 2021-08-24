import { SysConfig } from '../../support/utils/sysconfig';
import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';
import { sysconfigs } from '../../page_objects/sysconfig';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('SysConfig Tests', function() {
  let sysConfigName;
  let sysConfigValue;
  let sysConfigDescription;

  it('Read fixture and prepare the names', function() {
    cy.fixture('misc/sysconfig.json').then(f => {
      sysConfigName = appendHumanReadableNow(f['sysConfigName']);
      sysConfigValue = appendHumanReadableNow(f['sysConfigValue']);
      sysConfigDescription = appendHumanReadableNow(f['sysConfigDescription']);
    });
  });
  it('Filter for a sysconfig entry', function() {
    sysconfigs.visit();

    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('Name', 'webui.frontend.allow-cross-site-usage', false, null, true);

    applyFilters();

    sysconfigs.getRows().should('have.length', 1);
  });

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
