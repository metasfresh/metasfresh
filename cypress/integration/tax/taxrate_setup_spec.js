import { Taxrate } from '../../support/utils/taxrate';

describe('Create Taxrate for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/74', function() {
  it('Create new Taxrate', function() {
    cy.fixture('tax/taxrate.json').then(taxrateJson => {
      Object.assign(
        new Taxrate('Normaler Steuersatz 10%', '2019-05-01', 'Normaler Steuersatz 19% (Deutschland)'),
        taxrateJson
      ).apply();
    });
  });
  it(`TEST TEST ${this.documentId} TEST TEST`, function() {
    // Test
  });
});
