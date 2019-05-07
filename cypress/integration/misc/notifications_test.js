describe('Test notifications', function() {
  before(function() {
    cy.visit('/');
  });

  it('Test if older notifications are visible', function() {
    cy.get('.header-item-badge')
      .find('.notification-number')
      .should('not.contain', 0);

    cy.window()
      .its('store')
      .invoke('getState')
      .then(state => {
        assert.notEqual(state.appHandler.inbox.unreadCount, 0);
      });
  });

  it('Check if new notifications are added correctly', function() {
    cy.newNotification(null, 1).then(notifObj => {
      cy.get('.header-item-badge')
        .find('.notification-number')
        .contains('1');

      cy.window()
        .its('store')
        .invoke('getState')
        .then(state => {
          assert.equal(state.appHandler.inbox.unreadCount, 1);
          const notificationsInboxString = JSON.stringify(state.appHandler.inbox.notifications);

          expect(notificationsInboxString).to.include(notifObj.message);
        });
    });
  });
});
