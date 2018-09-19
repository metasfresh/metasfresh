describe('Product DocumentNote test', function() {
    before(function() {
        cy.loginByForm();
    });

    it('open the product window', function() {
        cy.visit('window/140/2005577');

        cy.openAdvancedEdit();
        cy.writeIntoTextField('DocumentNote', 'blah-blah-blah');
    });
});
