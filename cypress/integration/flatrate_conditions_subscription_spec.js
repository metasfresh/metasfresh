describe('New subscription flatrate conditions Test', function() {
    before(function() {
        // login before each test and open the flatrate conditions window
        cy.loginByForm();
    });

    const flatrateTransitionName = `Cypress Test ${new Date().getTime()}`;

    it('Create and complete a new flatrate transition record', function() {
        describe('Create a new flatrate transition record', function() {
            cy.visit('/window/540120/NEW');

            cy.writeIntoStringField('Name', `${flatrateTransitionName}`);
            cy.writeIntoListField('C_Calendar_Contract_ID', 'Buch', 'Buchf');
            cy.writeIntoStringField('TermDuration', '0000000001'); // note: there seems to be some bug somewhere, just '1' dos not work
            cy.writeIntoListField('TermDurationUnit', 'J', 'Jahr');
            cy.writeIntoStringField('TermOfNotice', '0000000001');
            cy.writeIntoListField('TermOfNoticeUnit', 'Mo', 'Monat');
            cy.writeIntoStringField('DeliveryInterval', '0000000001');
            cy.writeIntoListField('DeliveryIntervalUnit', 'Mo', 'Monat');
        });

        describe('Complete new flatrate transistion', function() {
            cy.processDocument('Complete', 'Completed');
        });
    });

    it('Create a new flatrate conditions record', function() {
        cy.visit('/window/540113/NEW');

        cy.writeIntoStringField('Name', `Cypress Test ${new Date().getTime()}`);

        cy.writeIntoListField('Type_Conditions', 'A', 'Abonnement');
        cy.writeIntoListField('OnFlatrateTermExtend', 'Co', 'Copy prices');

        cy.writeIntoListField('C_Flatrate_Transition_ID', flatrateTransitionName, flatrateTransitionName);
    });
});
