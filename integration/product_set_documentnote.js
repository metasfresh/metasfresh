describe('Product translation Test; #1025-api - Error opening translation windows https://github.com/metasfresh/metasfresh-webui-api/issues/1025', function() {
    before(function() {
        cy.loginByForm();
    });

    it('open the product window', function() {
        cy.visit('window/140/2005577');

        cy.openAdvancedEdit();
//        cy.get('.panel-modal-content').scrollTo('bottom')  
        cy.writeIntoTextField('DocumentNote', 'blah-blah-blah');
    });
});
