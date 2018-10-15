describe('New boiler plate (textsnippet) test', function() {
    before(function() {
        // login before each test
        cy.loginByForm();
    });

    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const boilerPlateName = `${timestamp}_Name_Cypress_Test`; // name needs to match regex ^[a-zA-Z0-9-_+]+?
    const boilerPlateSubject = `${timestamp} Subject (Cypress Test)`;
    const boilerPlateTextSnippet = `${timestamp} TextSnippet (Cypress Test)`;

    it('Create a new boiler plate record', function() {
        cy.visit('window/504410/NEW');

        cy.writeIntoStringField('Name', boilerPlateName);
        cy.writeIntoStringField('Subject', boilerPlateSubject);
        cy.writeIntoTextField('TextSnippet', boilerPlateTextSnippet);
    });

    
    it('There shall be no button, because lang-records are not inserted via UI', function() {

        cy.selectTab('AD_BoilerPlate_Trl');
        cy.get('.btn').should('not.exist');

    });
    
    it('Select the "English (US)" translation trecord and provide a trl', function() {
        
        cy.fail("DSL parts to select & edit rows is not yet implemented");
        // TODO: https://github.com/metasfresh/metasfresh-webui-frontend/issues/2002
        // select one of of the subtab's records and enter a translation.. for me, the command could be *fluent* and look like this:
        //
        // cy.selectSingleRow('AD_Language', 'English (US)')
        //   .writeIntoTextField('TextSnippet', `en_US trl of ${boilerPlateTextSnippet}`)
        //   .clickOnCheckBox('IsTranslated');
        //
        // if we can do fluent and start this here, it would be great imho. we would step by step also add fluency to the rest
        // if we don't go fluent, i guess it might look like this:
        //
        // cy.selectSingleRow('AD_Language', 'English (US)');
        // cy..writeIntoTextField('TextSnippet', `en_US trl of ${boilerPlateTextSnippet}`);
        // cy.clickOnCheckBox('IsTranslated');
        //    
        // question: could we have a variable number of arguments, so that we could also call the same command with
        // cy.selectSingleRow('AD_Language', 'Deutsch (CH)', 'AD_BoilerPlate_ID', '100005');
        // we need this for the case of subtabs that don't have a unique (visible) column to identify them
        // maybe, at any rate, we can provide a dedicated query type..then it might look like this (syntax might be wrong, but i hope you get the point):
        //
        // const RowQuery query = RowQuery.builder()
        //                                .filter('AD_Language', 'Deutsch (CH)')
        //                                .filter('AD_BoilerPlate_ID', '100005')
        //                                .build();
        // cy.selectSingleRow(query);
   });
});