* This file lists all available commands for reference

** General (general.js)

- loginViaAPI: reusable form login
- tab: emulates tab
- active: query for active element
- waitForHeader: wait for breadcrumb in header
- visitWindow: visit metasfresh document/details and wait for layout and data; or visit window and display rows
- performDocumentViewAction: execute a given function and wait for document/detail window layout and data
- readAllNotifications: mark all notifications as read on the backend
  DOM counter element
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
- executeHeaderActionWithDialog: execute a process/action and wait for the process-dialog
- executeQuickAction

** Test specific (test.js)

- editAddress
- pressStartButton
- processDocument: change document status
- openAdvancedEdit
- pressDoneButton
- pressAddNewButton
- pressBatchEntryButton
