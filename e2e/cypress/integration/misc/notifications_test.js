import notificationFixtures from '../../fixtures/misc/notifications.json';

const NOTIFICATION_FIXTURE = notificationFixtures['540375'];

describe('Test notifications', function() {
  before(function() {
    cy.server();
    cy.route('GET', '/rest/api/notifications/all?limit=20').as('notificationsAlias');

    cy.visit('/');

    cy.wait(`@notificationsAlias`, {
      requestTimeout: 10000,
      responseTimeout: 10000,
    });
  });

  it('Check if new notifications are added correctly', function() {
    newNotification(null, 1).then(notifObj => {
      cy.expectNumberOfDOMNotifications(1);

      getNotificationsInbox().then(state => {
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

function getNotificationsInbox() {
  return cy
    .window()
    .its('store')
    .invoke('getState')
    .then(state => {
      return cy.wrap(state.appHandler.inbox);
    });
}

function newNotification(notificationObject, unreadCount) {
  notificationObject = notificationObject || getNotificationFixture();

  return cy
    .window()
    .its('store')
    .invoke('dispatch', {
      type: 'NEW_NOTIFICATION',
      notification: {
        ...notificationObject,
      },
      unreadCount,
    })
    .then(() => notificationObject);
}

function getNotificationFixture() {
  const timestamp = new Date().getTime();
  const message = `Test notification ${timestamp}`;

  return {
    ...NOTIFICATION_FIXTURE,
    message,
  };
}
