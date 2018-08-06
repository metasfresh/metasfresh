describe('New subscription flatrate conditions Test', function() {
    before(function() {
        // login before each test and open the flatrate conditions window
        cy.loginByForm();
    });
    
    const timestamp = new Date().getTime(); // used in the document names, for ordering

    const year3TransitionName = `${timestamp} Year 3-dontExtend (Cypress Test)`;
    createAndCompleteTransistion(year3TransitionName, null, null);

    const year3ConditionsName = `${timestamp} Year 3 (Cypress Test)`;
    createAndCompleteConditions(year3ConditionsName, year3TransitionName);
    
    const year2To3TransitionName = `${timestamp} Year 2-3 (Cypress Test)`;
    createAndCompleteTransistion(year2To3TransitionName, 'Extend contract for all periods', year3ConditionsName);

    const year2ConditionsName = `${timestamp} Year 2 (Cypress Test)`;
    createAndCompleteConditions(year2ConditionsName, year2To3TransitionName);

    const year1To2TransitionName = `${timestamp} Year 1-2 (Cypress Test)`;
    createAndCompleteTransistion(year1To2TransitionName, 'Extend contract for all periods', year2ConditionsName);

    const year1ConditionsName = `${timestamp} Year 1 (Cypress Test)`;
    createAndCompleteConditions(year1ConditionsName, year1To2TransitionName);
});

function createAndCompleteTransistion(transitionName, extensionType, nextConditionsName) {
    it(`Create and complete transition record ${transitionName}`, function () {
        //describe('Create and complete a new flatrate transition record', function () {
            cy.visit('/window/540120/NEW');
            cy.writeIntoStringField('Name', transitionName);
            cy.writeIntoLookupListField('C_Calendar_Contract_ID', 'Buch', 'Buchf');
            cy.writeIntoStringField('TermDuration', '0000000001'); // note: there seems to be some bug somewhere, just '1' dos not work
            cy.writeIntoLookupListField('TermDurationUnit', 'J', 'Jahr');
            cy.writeIntoStringField('TermOfNotice', '0000000001');
            cy.writeIntoLookupListField('TermOfNoticeUnit', 'Mo', 'Monat');
            cy.writeIntoStringField('DeliveryInterval', '0000000001');
            cy.writeIntoLookupListField('DeliveryIntervalUnit', 'Mo', 'Monat');
            
            if(extensionType)
            {
                cy.selectInListField('ExtensionType', 'Extend contract for all periods');
            }
            if(nextConditionsName)
            {
                cy.writeIntoLookupListField('C_Flatrate_Conditions_Next_ID', nextConditionsName, nextConditionsName);
            }

            cy.get('.btn').click(); // there is just one button, so i guess it's OK on this case..still, we should add a command that selects the button by some key and then click it
            
            cy.writeIntoStringField('DeadLine', '0');
            cy.writeIntoLookupListField('DeadLineUnit', 'T', 'Tag');
            
            cy.selectInListField('Action', 'Statuswechsel');
            cy.selectInListField('ContractStatus', 'Gekündigt');
            cy.get('.items-row-2 > .btn').click(); // same a with the other button further up
            
            cy.processDocument('Complete', 'Completed');
      //  });
    });
}

function createAndCompleteConditions(conditionsName, transitionName) {
    it(`Create and complete conditions record ${conditionsName}`, function () {
        cy.visit('/window/540113/NEW');
        cy.writeIntoStringField('Name', conditionsName);
        cy.writeIntoLookupListField('Type_Conditions', 'A', 'Abonnement');
        cy.writeIntoLookupListField('OnFlatrateTermExtend', 'Co', 'Copy prices');
        cy.writeIntoLookupListField('C_Flatrate_Transition_ID', transitionName, transitionName);

        cy.processDocument('Complete', 'Completed');
    });
}