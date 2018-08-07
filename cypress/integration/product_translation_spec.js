describe('Product translation Test; #1025-api - Error opening translation windows https://github.com/metasfresh/metasfresh-webui-api/issues/1025', function() {
    before(function() {
        cy.loginByForm();
    });

    it('Create a product translation record', function() {
        cy.visit('/window/540420');
    });
});
