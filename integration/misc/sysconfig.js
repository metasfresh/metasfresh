/// <reference types="Cypress" />

import { SysConfig } from '../../support/utils/sysconfig';

describe('purchase order Test', function() {
    before(function() {
        // login before each test
        cy.loginByForm();
    });
    
    const timestamp = new Date().getTime() // used in the document names, for ordering

    const sysConfigName = `SysConfig ${timestamp}`
    const sysConfigValue = `Value ${timestamp}`
    const sysConfigDescription = `Description ${timestamp}`

    it('Create a sysconfig entry', function() {
       
        cy.log(`Create sys config with name=${sysConfigName}`)
        new SysConfig
            .builder(sysConfigName)
            .setValue(sysConfigValue)
            .setDescription(sysConfigDescription)
            .build()
            .apply();
        cy.screenshot();

        // to work with filtering, we need https://github.com/metasfresh/metasfresh-webui-frontend/issues/2143
        // cy.visit('/window/50006');
        // cy.get('.btn-filter').click()

        // cy.get('.filters-overlay')
        //     .find('.filter-menu').should('exist')
        //     .contains('Default')
        //     .click()
        
        // cy.get('.filters-overlay')
        //     .find('.filter-widget').should('exist')
        //     .find('Name')
        //     .find('input')
        //     .type('Test')

    });
});
