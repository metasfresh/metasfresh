/// <reference types="Cypress" />

import { createAndCompleteTransition, createAndCompleteRefundAmountConditions } from '../../support/utils/contract';

describe('Create accumulated amount-based refund conditions', function() {
    before(function() {
        // login before each test and open the flatrate conditions window
        cy.loginByForm();
    });
    
    it('Create accumulated amount-based refund conditions', function () {
        const timestamp = new Date().getTime(); // used in the document names, for ordering

        const transitionName = `${timestamp} accumulated amount-based refund conditions (Cypress Test)`;
        createAndCompleteTransition(transitionName, null, null);

        const conditionsName = `${timestamp} accumulated amount-based refund conditions (Cypress Test)`;
        createAndCompleteRefundAmountConditions(conditionsName, transitionName, 'A'/*Accumulated / Gesamtrückvergütung*/);
    });
});

