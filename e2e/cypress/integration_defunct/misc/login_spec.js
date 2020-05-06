describe('Login test', function() {
  context('HTML form submission', function() {
    it('redirects to dashboard on success', function() {
      // we should be redirected to dashboard
      cy.url().should('not.include', '/login');
      cy.get('.header-item').should('contain', 'Dashboard');
    });
  });
});
