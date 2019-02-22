describe('Login test', function() {
  context('HTML form submission', function() {
    beforeEach(function() {
      // login before each test
      cy.loginByForm();
    });

    it('redirects to dashboard on success', function() {
      // we should be redirected to dashboard
      cy.url().should('not.include', '/login');
      cy.get('.header-item').should('contain', 'Dashboard');
    });
  });
});
