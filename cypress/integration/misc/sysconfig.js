import { SysConfig } from '../../support/utils/sysconfig';

describe('purchase order Test', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering

  const sysConfigName = `SysConfig ${timestamp}`;
  const sysConfigValue = `Value ${timestamp}`;
  const sysConfigDescription = `Description ${timestamp}`;

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
