import { PaymentTerm } from '../../support/utils/paymentterm';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create payment term', function() {
  let name;
  let netDays;
  let graceDays;

  it('Read the fixture', function() {
    cy.fixture('misc/paymentterm_setup_spec.json').then(f => {
      name = appendHumanReadableNow(f['name']);
      netDays = f['netDays'];
      graceDays = f['graceDays'];
    });
  });

  it('Create a new Payment Term', function() {
    cy.fixture('misc/paymentterm.json').then(paymenttermJson => {
      Object.assign(new PaymentTerm(), paymenttermJson)
        .setName(name)
        .setNetDays(netDays)
        .setGraceDays(graceDays)
        .apply();
    });
  });
});
