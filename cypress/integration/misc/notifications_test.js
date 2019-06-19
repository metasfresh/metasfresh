describe('Test notifications', function() {
  before(function() {
    cy.visit('/');
  });

  // Depending on the DB, we might have some notifications already or not
  // it('Test if older notifications are visible', function() {
  //   cy.getDOMNotificationsNumber().should('not.equal', 0);

  //   cy.window()
  //     .its('store')
  //     .invoke('getState')
  //     .then(state => {
  //       assert.notEqual(state.appHandler.inbox.unreadCount, 0);
  //     });
  // });

  it('Check if new notifications are added correctly', function() {
    cy.newNotification(null, 1).then(notifObj => {
      cy.getDOMNotificationsNumber().should('equal', 1);

      cy.getNotificationsInbox(state => {
        assert.equal(state.unreadCount, 1);
        const notificationsInboxString = JSON.stringify(state.notifications);

        expect(notificationsInboxString).to.include(notifObj.message);
      });

      cy.exec
    });
  });
});


asta
