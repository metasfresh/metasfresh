/// <reference types="Cypress" />

import { createAndCompleteTransition } from '../../support/utils/contract';

describe('Create subscription flatrate conditions for three 1-year-periods', function() {
    // before(function() {
    //     // login before each test and open the flatrate conditions window
    //     cy.loginByForm();
    // });
    
    // it('Create subscription flatrate conditions for three 1-year-periods', function () {
        const timestamp = new Date().getTime(); // used in the document names, for ordering

    const year3TransitionName = `${timestamp} Year 3-dontExtend (Cypress Test)`;
    const year3ConditionsName = `${timestamp} Year 3 (Cypress Test)`;
    it('Create transition and conditions for 3rd year', function () {
        cy.loginByForm();
        createAndCompleteTransition(year3TransitionName, null, null);
        createAndCompleteConditions(year3ConditionsName, year3TransitionName);
    });
      
    const year2To3TransitionName = `${timestamp} Year 2-3 (Cypress Test)`;
    const year2ConditionsName = `${timestamp} Year 2 (Cypress Test)`;
    it('Create transition and conditions for 2nd year', function () {
        createAndCompleteTransition(year2To3TransitionName, 'Extend contract for all periods', year3ConditionsName);
        createAndCompleteConditions(year2ConditionsName, year2To3TransitionName);
    });

    const year1To2TransitionName = `${timestamp} Year 1-2 (Cypress Test)`;
    const year1ConditionsName = `${timestamp} Year 1 (Cypress Test)`;
    it('Create transition and conditions for 1st year', function () {
        createAndCompleteTransition(year1To2TransitionName, 'Extend contract for all periods', year2ConditionsName);
        createAndCompleteConditions(year1ConditionsName, year1To2TransitionName);
    });
    // });
});


function createAndCompleteConditions(conditionsName, transitionName) 
{
        cy.visit('/window/540113/NEW');
        cy.writeIntoStringField('Name', conditionsName);
        cy.writeIntoLookupListField('Type_Conditions', 'A', 'Abonnement');
        cy.writeIntoLookupListField('OnFlatrateTermExtend', 'Co', 'Copy prices');
        cy.writeIntoLookupListField('C_Flatrate_Transition_ID', transitionName, transitionName);

        cy.processDocument('Complete', 'Completed');
}