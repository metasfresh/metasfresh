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
        assert.isAbove(state.appHandler.inbox.unreadCount, 0);
      });
  });

  it('Check if new notifications are added correctly', function() {
    const timestamp = new Date().getTime();
    const message = `Test notification ${timestamp}`;

    cy.window()
      .its('store')
      .invoke('dispatch', {
        type: 'NEW_NOTIFICATION',
        notification: {
          id: '1003176',
          message,
          timestamp: '2019-05-07T10:58:48.832+02:00',
          important: false,
          read: false,
          target: {
            targetType: 'view',
            windowId: '540375',
            viewId: '540375-Jn',
            documentType: '540375',
          },
        },
        unreadCount: 1,
      });

    cy.get('.header-item-badge')
      .find('.notification-number')
      .contains('1');

    cy.window()
      .its('store')
      .invoke('getState')
      .then(state => {
        assert.equal(state.appHandler.inbox.unreadCount, 1);
        const notificationsInboxString = JSON.stringify(state.appHandler.inbox.notifications);

        expect(notificationsInboxString).to.include(message);
      });
  });
});
