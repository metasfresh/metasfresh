* This file lists all available commands for reference

** General (general.js)

- loginViaAPI: reusable form login
- tab: emulates tab
- active: query for active element
- waitForHeader: wait for breadcrumb in header
- visitWindow: visit metasfresh and wait for layout
- readAllNotifications: mark all notifications as read on the backend
- resetNotifications: clear notifications inbox and unread count
- addNotification: adds a new notification to the list
- newNotification: creates fresh notification list with 1 element
- getDOMNotificationsNumber: gets the number of notifications in the
  DOM counter element
- getNotificationsInbox: gets app notifications inbox
- getNotificationModal: get the HTML element with notification
- waitForFieldValue: wait for field data from request

** Navigation (navigation.js)

- clickButtonWithText: click button with given text
- clickElementWithClass: click element with given selector
- selectTab: select tab with a name
- selectSingleTabRow: select row in active tab
- selectReference

** Forms (form.js)

- assertFieldNotShown
- clearField
- getStringFieldValue
- getCheckboxValue
- clickOnIsActive
- clickOnCheckBox
- resetListValue
- writeIntoStringField: insert text in text input
- writeIntoTextField: insert text in a textarea
- writeIntoLookupListField
- selectInListField
- selectDateViaPicker: open date picker and select date (atm works for today only)

** Action

- clickHeaderNav
- executeHeaderAction
- executeHeaderActionWithDialog
- executeQuickAction

** Test specific (test.js)

- editAddress
- pressStartButton
- processDocument: change document status
- openAdvancedEdit
- pressDoneButton
- pressAddNewButton
- pressBatchEntryButton
