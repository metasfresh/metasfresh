describe('Login test', function(){
  context('HTML form submission', function(){
    beforeEach(function(){
      cy.visit('localhost:3000/login')
    });

    it('redirects to dashboard on success', function(){
      cy.get('input[name=username]').type('kuba')
      cy.get('input[name=password]').type('kuba1234{enter}')

      // we should be redirected to dashboard
      cy.url().should('not.include', '/login')
      cy.get('.header-item').should('contain', 'Dashboard')
    });
  })
});
