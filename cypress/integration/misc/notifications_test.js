describe('Test notifications', function() {
  before(function() {
    cy.visit('/');
  });

  it('Check if new notifications are added correctly', function() {
    cy.newNotification(null, 1).then(notifObj => {
      cy.getDOMNotificationsNumber().should('equal', 1);

      cy.getNotificationsInbox().then(state => {
        assert.equal(state.unreadCount, 1);
        const notificationsInboxString = JSON.stringify(state.notifications);

        expect(notificationsInboxString).to.include(notifObj.message);
      });
    });
  });

  it('Test if older notifications are visible', function() {
    cy.readAllNotifications();

    cy.window()
      .its('store')
      .invoke('getState')
      .then(state => {
        assert.equal(state.appHandler.inbox.unreadCount, 0);
      });
  });
});
