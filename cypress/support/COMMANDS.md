* This file lists all available commands for reference

** General (commands.js)

- loginByForm: reusable form login
- tab: emulates tab
- active: query for active element
- waitForHeader: wait for breadcrumb in header
- visitWindow: visit metasfresh and wait for layout
- waitForFieldValue: wait for field data from request

** Navigation (navigation_commands.js)

- clickButtonWithText: click button with given text
- clickElementWithClass: click element with given selector
- selectTab: select tab with a name
- selectSingleTabRow: select row in active tab
- selectReference

** Forms (form_commands.js)

- clearField
- getFieldValue
- isChecked
- clickOnIsActive
- clickOnCheckBox
- writeIntoStringField: insert text in text input
- writeIntoTextField: insert text in a textarea
- writeIntoLookupListField
- selectInListField

** Action

- clickHeaderNav
- executeHeaderAction
- executeHeaderActionWithDialog
- executeQuickAction

** Test specific (test_commands.js)

- editAddress
- pressStartButton
- processDocument: change document status
- openAdvancedEdit
- pressDoneButton
- pressAddNewButton
- pressBatchEntryButton
