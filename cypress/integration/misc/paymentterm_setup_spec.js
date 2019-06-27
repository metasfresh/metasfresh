import { PaymentTerm } from '../../support/utils/paymentterm';

describe('Create test: payment term, https://github.com/metasfresh/metasfresh-e2e/issues/45 with fixt+obj', function(){
    it('Create a new Payment Term', function() {
        cy.fixture('misc/paymentterm.json').then(paymenttermJson => {
            Object.assign(new PaymentTerm(), paymenttermJson)
            .getNameAndValue()
            .setNetDays(5)
            .setGraceDays(3)
            .apply();
        });
    });
});
