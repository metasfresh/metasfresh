import { Taxrate } from '../../support/utils/taxrate';

describe('Create Taxrate for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/74', function() {
  it('Create and deactivate new Taxrate', function() {
    cy.fixture('tax/taxrate.json').then(taxrateJson => {
      Object.assign(
        new Taxrate('Normaler Steuersatz 10%', '2019-05-01', 'Normaler Steuersatz 19% (Deutschland)'),
        taxrateJson
      )
        .apply()
        .setActive(false)
        .activate();
    });
    cy.get('@taxRateObj').then(obj => {
      // access the users argument
      cy.log(`Taxrate - Name (name=${obj.documentId}`);
    });

    // 2. Deactivate Taxrate

    // 3. Open Window Product Prices and new Record. Deactivated Taxrate cannot be selected.

    // 4. Activate Taxrate

    // 5. Open Window Product Prices and new Record. Activated Taxrate can be selected.
  });
});
