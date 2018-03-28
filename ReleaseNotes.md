
# About this document

This file contains the tasks/issues which we implement in metasfresh, in a chronological fashion (latest first)

Additional notes:
 * The metasfresh source code is hosted at https://github.com/metasfresh/metasfresh
 * The metasfresh website is at http://metasfresh.com/en, http://metasfresh.com/ (german)
 * You can also follow us on twitter: @metasfresh (english), @metasfreshDE (german)

Here come the actual release notes:

# metasfresh 5.51 (2018-14)
**release for week 2018-14**

## Features
* metasfreh-webui-frontend
    * [#1586](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1586) Screen aliginment Improvements
      * Improves the Look & Feel, mostly vertical alignments - of all Fields and Field Content. Also Size of Lookup widget dropdowns.

## Fixes
* metasfresh-webui-frontend
  * [#1679](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1679) Layout bug with resolution 1920x1200
    * Fixes a Layout Bug in table header of Grid View, now showing the cells in initial S, M, L sizes again.
  * [#1700](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1700) attribute filter fields are shrinked
    * Fixes the Filter for Attributes e.g. in Window Businesspartner. Now it's able to use the filter again.

# metasfresh 5.50 (2018-13)
**release for week 2018-13**

## Features
* metasfresh
  * [#3585](https://github.com/metasfresh/metasfresh/issues/3585) WebUI window design: Business Partner Credit Limit
    * Improvement of Busines Partner Credit Limit Fieldnames and Translations.
  * [#3687](https://github.com/metasfresh/metasfresh/issues/3687) Replace flags from tax category with a list
  * [#3693](https://github.com/metasfresh/metasfresh/issues/3693) Lot-No lock & control
  * [#3703](https://github.com/metasfresh/metasfresh/issues/3703) Material Receipt Candidates Window, add media types for mobile/ tablet
    * Mobile Usage Improvement. Adds Mediatypes to Material Receipt Candidates window (Tablet proptotype).
  * [#3716](https://github.com/metasfresh/metasfresh/issues/3716) Implement MSV3 server
  * [#3721](https://github.com/metasfresh/metasfresh/issues/3721) Create Request on DD_Order to Quarantine warehouse
  * [#3724](https://github.com/metasfresh/metasfresh/issues/3724) Allow to run print endpoint as standalone service
  * [#3738](https://github.com/metasfresh/metasfresh/issues/3738) Introduce and use AD_OrgInfo.M_WarehousePO_ID
  * [#3743](https://github.com/metasfresh/metasfresh/issues/3743) Translation of Distribution Editor Quickaction
    * Translation added for the Distribution Editor Quickactions.
  * [#3749](https://github.com/metasfresh/metasfresh/issues/3749) Add isPrintPrice flag in orderLine
  * [#3758](https://github.com/metasfresh/metasfresh/issues/3758) Notify users if msv3-server was not reached

* metasfresh-webui-api
  * [#911](https://github.com/metasfresh/metasfresh-webui-api/issues/911) HU attributes shall consider M_HU_PI_Attribute.IsDisplayedUI flag
  * [#913](https://github.com/metasfresh/metasfresh-webui-api/issues/913) Add switch for synchronous availiability check
  * [#919](https://github.com/metasfresh/metasfresh-webui-api/issues/919) Allow to display only positive ATP values in product lookup

* metasfresh-webui-frontend
  * [#1680](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1680) Camera Barcode workflow improvements
    * Detailed improvements in dthe Barcode workflow for Tablet usage.
  * [#1681](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1681) Data Entry via barcode scan in Action Parms
    * New Funcitonality for Barcode Scanning in Action Parms.
  * [#1684](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1684) Combine dropdown list of <List> and <Lookup> component
    * Improvement of Lookup Widgets and List Components, combined functionality  for better maintainability.
  * [#1692](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1692) Detailed adjustments of User Interface for tablet media size
    * UI Improvement for Mobile/ Tablet Usage of storage relevant workflows.
  * [#1706](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1706) Dropdown bug in modal windows
    * Fixes the refactored Lookup & List widgets behavior.

## Fixes
* metasfresh
  * [#3698](https://github.com/metasfresh/metasfresh/issues/3698) NPE when clicking on picking-slot in swing picking terminal first time
  * [#3717](https://github.com/metasfresh/metasfresh/issues/3717) swing - Bereitstellung POS hangs
  * [#3728](https://github.com/metasfresh/metasfresh/issues/3728) Cannot create material receipt if the ADR attribute is disabled in PI template
  * [#3736](https://github.com/metasfresh/metasfresh/issues/3736) Jasper: Qty TU in purchase invoice is wrong again
  * [#3739](https://github.com/metasfresh/metasfresh/issues/3739) Toplevel TUs are created as aggregate HUs
  * [#3746](https://github.com/metasfresh/metasfresh/issues/3746) ZoomTo - exception in console if IsGenericZoomOrigin='N'
  * [#3755](https://github.com/metasfresh/metasfresh/issues/3755) Error in search of Businesspartner
    * Fixes the SQL of a virtual column in C_BPartner Table.

* metasfresh-webui-frontend
  * [#1605](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1605) Scrolling in dropdown fields
    * Fixes the behavior of dropdown fields when scrolling throught the list.
  * [#1641](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1641) Combined Lookup Widget Layout Glitch
    * Fixes a Layout Glitch in combined Lookup Fields.
  * [#1649](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1649) Date range widget needs to support 2018-05-01 to 2019-04-30
    * Fixes the date timezone in date widgets.
  * [#1660](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1660) Single Date Field not patched in some cases
    * Fix for the patching of date fields when manipulating in mouse and keaboard kombination.
  * [#1663](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1663) Pos1 does not jump to page 1 anymore
    * Fixes the Home Button in grid View. Now possible to jump to first page in Pagination again.
  * [#1676](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1676) List widgets with focus follow-up
  * [#1705](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1705) Barcode Scan leaved Webcam switched on
    * Now the Webcam is switched off as soon the Barcode reading process is finished.


# metasfresh 5.49 (2018-12)
**release for week 2018-12**

## Features
* metasfresh
  * [#3240](https://github.com/metasfresh/metasfresh/issues/3240) Drop AD_Field.IsCentrallyMaintained database column
    * Internal Housekeeping Issue, taking care of legacy column "isCentrallyMaintained".
  * [#3584](https://github.com/metasfresh/metasfresh/issues/3584) Implement sales order candidates REST API
    * New REST-API for order candidates. This is part of the new Pharma Availability Check and Ordering Service.
  * [#3658](https://github.com/metasfresh/metasfresh/issues/3658) Introduce specific subtypes for Physical Inventory and for Internal Use Inventory
    * Additional Document Subtypes for Physical Inventory and Internal usage.
  * [#3659](https://github.com/metasfresh/metasfresh/issues/3659) Refactor/adapt Inventory Disposal
    * Improvement of the Inventory disposal functionality. Adapting to new Inventory Control.
  * [#3669](https://github.com/metasfresh/metasfresh/issues/3669) Create all periods of a contract on creation
    * Improving the contract creation, now creating all periods at the moment of contract generation.
  * [#3688](https://github.com/metasfresh/metasfresh/issues/3688) Port Printing REST endpoint from SMX to metasfresh
    * Ports the REST Endpoint for Printing from ServiceMix to metasfresh.
  * [#3705](https://github.com/metasfresh/metasfresh/issues/3705) Inventory Window show Handling Unit Field in Inventorylines
    * Adds the Handling Unit into Inventopylines allowing to use Inventory with our Handling Usage Management.
  * [#3706](https://github.com/metasfresh/metasfresh/issues/3706) Terminating a Contract that was extended ahead of schedule leaves stumb
    * Improvement of the contract Termination action.
  * [#3718](https://github.com/metasfresh/metasfresh/issues/3718) Contracts: Transform IsAutoRenew and IsAutoExtension flags in a list

* metasfresh-api
  * [#894](https://github.com/metasfresh/metasfresh-webui-api/issues/894) New Composed Primary Keys
    * Allows now to use composed primary keys in WebUI.
  * [#895](https://github.com/metasfresh/metasfresh-webui-api/issues/895) Automatic printing HU Labels in WebUI
    * New Feature to automatically print TU Labels for Picking in WebUI.
  * [#901](https://github.com/metasfresh/metasfresh-webui-api/issues/901) Provide media size info for columns
    * Enhances the UI Element Configuration in Application Dictionary. It's now possible to define media size dependant behavior for the Web User Interface.
  * [#910](https://github.com/metasfresh/metasfresh-webui-api/issues/910) 2nd orderline for product missing in picking terminal
    * Now shows the 2nd Orderline in Picking Terminal again.

* metasfresh-webui-frontend
  * [#1609](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1609) Camera barcode reader for Tablet/ mobile device
    * New Barcode Reader that can be used via webcam on mobile devices.
  * [#1677](https://github.com/metasfresh/metasfresh-webui-frontend/pull/1677) Update momentjs in packages.json
    * Updateing Moments.js to the newses Version.
  * [#1699](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1699) Tab does not trigger saving number field
    * Fixes the patching in number fields. Now also patching when leaving the Fields via Tab Key.
  * [#1860](https://github.com/metasfresh/metasfresh/issues/1860) Make documentNo readonly in Payments (and other document types)
    * Improves the documetNo behavior, making all Fields with Document No read-only.

## Fixes
* metasfresh
  * [#3050](https://github.com/metasfresh/metasfresh/issues/3050) Null in SSCC label
    * Fix for the LotNo on SSCC Labels. If not set, the Lot No now shows an empty string instead of null.
  * [#3497](https://github.com/metasfresh/metasfresh/issues/3497) Days Due in Open Items report empty when parameter not set
    * Fixes the Parm description in Open Items report in WebUI, now showing "Unlimited" when the Dates Dues Parm is left empty.
  * [#3579](https://github.com/metasfresh/metasfresh/issues/3579) SSCC18 attribute check digit
    * Fixes the check digit of generated SSCC Numbers.
  * [#3666](https://github.com/metasfresh/metasfresh/issues/3666) Manufacturing Order wrong TU Quantity
    * Fixes the Qty of TU in Manufacturing Orders when automatically created after sales order complete (lot-for-lot).
  * [#3690](https://github.com/metasfresh/metasfresh/issues/3690) Restore swing MRP ProductInfo
    * Fixes the old MRP Product Info Window in Java Swing UI.
  * [#3694](https://github.com/metasfresh/metasfresh/issues/3694) docs_flatrate_term_all_procurements_conditions_report fails if M_Product_ID is left empty
    * Fixes the procurements conditions report. Now not failing anymore when the Product ID is left empty.
  * [#3695](https://github.com/metasfresh/metasfresh/issues/3695) NPE when completing DD_Order
    * Fixes a Null Pointer Exception that occurred sometimes when completung a Distribution Order.
  * [#3701](https://github.com/metasfresh/metasfresh/issues/3701) Jasper: ADR Auswertung report has duplicates
    * Removing Duplicate entries in the Swiss fresh produce ADR report.
  * [#3745](https://github.com/metasfresh/metasfresh/issues/3745) Compensation Group: Sequence of Lines is lost on Clone
    * Fixes the sequence of lines in Compensation groups when cloning a sales order document.

* metasfresh-webui-api
  * [#735](https://github.com/metasfresh/metasfresh-webui-api/issues/735) Hide Receive HUs (default) when there are no HUs
    * Hides the default action for Receive HU in cases where there are only CU's in receipt candidates.

* metasfresh-webui-frontend
  * [#1642](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1642) Lookup Widget drop-down list scroll behavior
    * Improves the behavior of drop-down list scroll behavior.
  * [#1669](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1669) Empty option missing in dropdown
    * Fixes the empty selected elements in list widgets after selecting entry.
  * [#1673](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1673) Improve User Interface for tablet media size
    * Layout adjustments for the usage of webui frontend on tablet media sizes.
  * [#1689](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1689) Scrollbar not shown in order window for subtab order lines
    * Fixes the missing horizontal Scrollbar.

# metasfresh 5.48 (2018-11)
**release for week 2018-11**

## Features
* metasfresh
  * [#2647](https://github.com/metasfresh/metasfresh/issues/2647) Inventory Functionality w/ Handling Unit Assignments
    * Improved Inventory Functionality for WebUI, now allowing to record Handling Unit quantities too.
  * [#3029](https://github.com/metasfresh/metasfresh/issues/3029) WebUI: New Window for Import Formats
    * New Indow in WebUI for Import Formats, now allowing to maintain data formats to import migration data.
  * [#3580](https://github.com/metasfresh/metasfresh/issues/3580) TU label in picking process in webui
    * New Action in Picking, allowing to print a Transport Unit Label.
  * [#3622](https://github.com/metasfresh/metasfresh/issues/3622) Make AD_EventLog available for admin
    * Improved Event Log Handling, now making the logs available for the admin role.
  * [#3634](https://github.com/metasfresh/metasfresh/issues/3634) Pick in manufacturing Order: Picking Slot Filter on selected BPartner from Shipment Schedule
    * New functionality in Manufacturing Workflow, allowing to directly pick produced products via manufacturing receipt workflow.
  * [#3637](https://github.com/metasfresh/metasfresh/issues/3637) Standalone Product Translation Window
    * New Standalone Product Translation window in WebUI.
  * [#3652](https://github.com/metasfresh/metasfresh/issues/3652) WebUI: New Inventory Window
    * New Window in WebUI that allows the maintenance of Inventory recordings and adjustments.
  * [#3591](https://github.com/metasfresh/metasfresh/issues/3591) Add translations for headers in shipment statistics
    * New WebUI window for shipment statistics.
  * [#3618](https://github.com/metasfresh/metasfresh/issues/3618) Add "technical note" field to AD_Column
    * Internal Housekeeping Improvement, adding a new Field to Application Dictionary in Column, that allows to record additional internal information.
  * [#3620](https://github.com/metasfresh/metasfresh/issues/3620) Spring model interceptors: support those who implement IModelInterceptor
    * Internal Housekeeping improvement, now discovering interceptors which implement the IModelInterceptor interface.
  * [#3621](https://github.com/metasfresh/metasfresh/issues/3621) Allow semicolon delimiter for Import Format
    * Enhancement of possible Import Delimiters in Import Format, now also allowing semicolon as delimiter.
  * [#3624](https://github.com/metasfresh/metasfresh/issues/3624) Pharma: When Operation Code is 2, deactivate the product
    * Improvement of the Import of Pharma Products, now dectivating Products with status code 2 (discontinued Products).
  * [#3674](https://github.com/metasfresh/metasfresh/issues/3674) Support Country in local address sequence
    * Now supporting the county variable in the local Address sequence.
  * [#3655](https://github.com/metasfresh/metasfresh/issues/3655) POJOWrapper support for model classes with zero ID
    * Internal Development improvement.
  * [#3667](https://github.com/metasfresh/metasfresh/issues/3667) Prospect is converted to customer when completing a quotation
    * Improvement of the prospect-customer transition, now setting to customer as soon first Partner Sales Order is completed.

* metasfresh-webui-api
  * [#773](https://github.com/metasfresh/metasfresh-webui-api/issues/773) Add name and menu entry for WebUI window Package
    * New Window in WebUI for Package maintenance.
  * [#882](https://github.com/metasfresh/metasfresh-webui-api/issues/882) WebUI: Pimp Data Import window
    * New Window for Date Import in WebUI. now allows to do the data import into metasfresh with drag&drop file attachment upload.

* metasfresh-webui-frontend
  * [#1640](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1640) Drag and Drop Attachments
    * Adds the functionality to upload multiple files at once.

* metasfresh-dist
  * [#35](https://github.com/metasfresh/metasfresh-dist/issues/35) Remove swing-client exe via launch4j
    * Internal Development improvement.

## Fixes
* metasfresh
  * [#3612](https://github.com/metasfresh/metasfresh/issues/3612) Sorting defect: Change of Flatrate Term in Sales Orderline after grouping
    * Fixes the Sorting in recreated Compensation group lines in sales order.
  * [#3639](https://github.com/metasfresh/metasfresh/issues/3639) Material dispo - problem with multiple cost collectors
    * Fixes an Issue in Material Disposition when receiving multiple Handling Units in manufacturing Order resulting in multiple cost collectors.

* metasfresh-api
  * [#850](https://github.com/metasfresh/metasfresh-webui-api/issues/850) Sorting by Virtual Column Follow-up
    * Fixes the sorting of virtual columns which are not numbers in grid view.
  * [#890](https://github.com/metasfresh/metasfresh-webui-api/issues/890) Exception when adding request from partner window for sales rep
    * Fixes a Bug in the Request creation action when started from partner window.

* metasfresh-webui-frontend
  * [#1544](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1544) List Widget does not scroll down with arrow-down
    * Improved scrolling behavior of list widgets.
  * [#1557](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1557) Double click on attributes in orderline
    * Improves the behavopr of the attributes dropdown widget when double clicking cell in subtab grid view. Now the elements are not all selected.
  * [#1615](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1615) Picking terminal window: switching rows is very very slow
    * Performance Improvement of Picking Terminal Window.
  * [#1637](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1637) Collapsible lines in Create Purchase Order modal
    * Fixes the behavior of collapsible lines in modal overlay of create purchase orders.
  * [#1646](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1646) List dropdown improvements
    * Improved behavior of lookup and list widget drop-down. Now keeping the field content selected when reopening the drop-down after hover and leave.
  * [#1650](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1650) Edit mode in grid view when focus lost
    * Improvement of the display status of grid fields in edit mode. Now the edit modus and display is left as soon the field is left.
  * [#1651](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1651) Multi Line Text Field ist not patched when text is removed
    * Fixes the patching of empty Text Fields.
  * [#1653](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1653) Line in modal overlay for creating purchase order from SO disappears
    * Fixes an Issue in Create Purchase Orders from Sales Order Lines. Now it's possible to enter the purchase Quantity again.
  * [#1658](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1658) Improve WebUI performance
    * General Performance Improvement for Grid Views in WebUI.
  * [#1662](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1662) Attributes in OrderLine grid view are not saved
    * Fixes the recording of attributes in document lines. Now they are saved again.

# metasfresh 5.47 (2018-10)
**release for week 2018-10**

## Features
* metasfresh
  * [#3540](https://github.com/metasfresh/metasfresh/issues/3540) WebUI: Show GuaranteeDaysMin on Product and Product Category windows
    * Improved Product and Product Category windows, added new field for Min. Guarantee Days ad part of the best before management.
  * [#3541](https://github.com/metasfresh/metasfresh/issues/3541) Div material dispo issues
    * Implements various detailed features in the material disposition feature.
  * [#3549](https://github.com/metasfresh/metasfresh/issues/3549) Refactor config for Distribution Order/ Movement after Material Receipt
    * Improvement of the new Distribution Order generation after material receipt to allow a better understanding of configuration switches.
  * [#3556](https://github.com/metasfresh/metasfresh/issues/3556) Webui: Pimp Compensation Group Schema window
    * Enhanced Window Compensation group Schema, added fields for the new automatic Discount Functionality.
  * [#3561](https://github.com/metasfresh/metasfresh/issues/3561) Add relation between Invoice and Shipment Constraint
    * New relation added for the navigation between invoices and shipments.
  * [#3562](https://github.com/metasfresh/metasfresh/issues/3562) Primary Layout in Product Planning Subtabs
    * Layout adjustment for the Subtabs in Product planning window, now added primary background to all of them.
  * [#3565](https://github.com/metasfresh/metasfresh/issues/3565) Initial Promised Date calculation and default Preparation Date
    * New Feature that allows to calculate an initial promised date in sales order based on date ordered.
  * [#3570](https://github.com/metasfresh/metasfresh/issues/3570) CustomerLabelName in M_Product and Translation
    * New translateable field in the product masterdata that allws to record the default Customer Label Name for a product.
  * [#3575](https://github.com/metasfresh/metasfresh/issues/3575) Make shipment HU aggregation configurable
    * New feature that allows to configure the aggregation of shipment lines for shipped Handling Units.
  * [#3576](https://github.com/metasfresh/metasfresh/issues/3576) TU label in picking
    * New Label for Transportation Units that can be created in the picking workflow.
  * [#3578](https://github.com/metasfresh/metasfresh/issues/3578) Introduce AD_Column.IsForceIncludeInGeneratedModel to force including a column to generated java models
    * Internal Housekeeping issue that allows to force include columns in model generation no matter what entity type the corresponding table has.
  * [#3582](https://github.com/metasfresh/metasfresh/issues/3582) Avoid mass-creation of "MissingShipmentSchedulesWorkpackages"
    * Performance Improvements to the Subscription evaluation process, now avioiding the creation of mass work packages when not needed.
  * [#3588](https://github.com/metasfresh/metasfresh/issues/3588) Drop support for org.adempiere.server.embedded
    * Internal Housekeeping improvement.
  * [#3587](https://github.com/metasfresh/metasfresh/issues/3587) Implement user token authentication for future REST APIs
    * Implementation for the user token authentication to our REST APIs.
  * [#3590](https://github.com/metasfresh/metasfresh/issues/3590) Make shipment line attribute set instance (ASI) configurable
    * New Feature that allows to configure the creation of shipment line Attributre set instances instead of just creating a copy from sales orderline.
  * [#3592](https://github.com/metasfresh/metasfresh/issues/3592) Add invoice related virtual columns in Shipment Restrictions
    * New searchable Fields in Shipment Restrictions for Invoice ID and isPaid.
  * [#3599](https://github.com/metasfresh/metasfresh/issues/3599) Pharma: Use IFA category when importing IFA products
    * Now using the IFA Category as Product Category after Import of Pharma Products.
  * [#3604](https://github.com/metasfresh/metasfresh/issues/3604) Provide AD_Window_ID to running process
    * Improvement of the Process Info, now adding the Window ID to it and storing it in the Process Instance record. This allows to retrieve more infomration about from where a Process was triggered.
  * [#3605](https://github.com/metasfresh/metasfresh/pull/3605) Add mvnw so that users don't need to install mvn to build from cmdline
    * Internal Housekeeping solution for maven build via commandline.
  * [#3609](https://github.com/metasfresh/metasfresh/issues/3609) Default Product Window take out Pharma specific fields
    * Adjustment of the default product window, removing all pharma vertical fields. These are included in the Pharma product window.

* metasfresh-webui-api
  * [#878](https://github.com/metasfresh/metasfresh-webui-api/issues/878) Distribution Editor Move HU dropdown list HU
    * Improvement of the Move Handling Unit Action in distribution editor, now pre-filtering the entries and matching base data.
  * [#879](https://github.com/metasfresh/metasfresh-webui-api/issues/879) Import File Loader substitute in WebUI
    * New feature in Webui as substitute for the file-loader form in swing client. This enables the import of date via webui.
  * [#880](https://github.com/metasfresh/metasfresh-webui-api/issues/880) Collapse Purchase Order dispo in Sales Order modal
    * Improved visibility in Create Purchase Order from Sales Orderlines, now collapsing the top level entries initially.
  * [#883](https://github.com/metasfresh/metasfresh-webui-api/issues/883) HU Labels in WebUI
    * Refined Handling Unit Labels actio now available in WebUI Handling Unit Editor.
  * [#886](https://github.com/metasfresh/metasfresh-webui-api/issues/886) Field Readonly = 'Y' but still updateable in WebUI
    * Refined Implementation of the Field Readonly Logic in Application Dictionary.

* metasfresh-webui-frontend
  * [#1620](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1620) List widgets with focus shall have dark grey color underline
    * Improved focus indicator line for list widgets.
  * [#1621](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1621) Dropdown List Widget - first entry at top shall be selected entry
    * Improves the behavior of selected elements in List drop-downs. Now showing them as first entry in list.
  * [#1622](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1622) List widget with only 1 entry shall still show the drop-down
    * Now the List widget also shows a drop-down list with only 1 entry.
  * [#1635](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1635) Check code formatting on CI
    * Adding code formatting rules to continuous integration platform.
  * [#1644](https://github.com/metasfresh/metasfresh-webui-frontend/pull/1644) Add cypress e2e test setup
    * Adding cypress setup for end-2-end tests of the webui frontend project.


## Fixes
* metasfresh
  * [#3422](https://github.com/metasfresh/metasfresh/issues/3422) Material Cockpit Document Details Doctype, DocNo wrong/ missing
    * Fixes the document detailes modal overlay in material cockpit. Now showing the document details with document no and document type.
  * [#3569](https://github.com/metasfresh/metasfresh/issues/3569) Purchase order from purchase candidate not working 
    * Fixes a bug that prevented the automatic creation of purchase orders from purchase candidates.
  * [#3571](https://github.com/metasfresh/metasfresh/issues/3571) AD_Element_trl problem with autocomplete fields
    * Fixes an issue that occurred when adding translation elements via autocomplete widgets.
  * [#3572](https://github.com/metasfresh/metasfresh/issues/3572) Internal usage (Materialentnahme partial) broken
    * Fix for the internal usage action when removing partial customer unit quantities, mostly weight unit of measure.
  * [#3595](https://github.com/metasfresh/metasfresh/issues/3595) Tax Error on w101 Sales Order Batch entry
    * Improved error message for Tax errors, now elaborating more whats the reason for the errors.
  * [#3644](https://github.com/metasfresh/metasfresh/issues/3644) "No Selection" error when generating shipments
    * Improvement of action/ Quickaction availability in Shipment Schedule. Now only showing line dependent actions if a line is selected. 

* metasfresh-webui-frontend
  * [#1550](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1550) Dropdown List Widget does not show 1st line as selected with keyboard scroll
    * Now the field content is shown as selected first element in the dropdown.
  * [#1623](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1623) Attributes not shown in Material Receipt Candidates
    * Fixes a Bug in the Handling Unit Editor in Material Receipts, not showing the Attributes View when Receiving CU.
  * [#1625](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1625) Show Attribute Editor for selected CU Level HU
    * Fix for the Handling Unit Editor when selectin the first entry of an HU. Now initially showing the Attributes view again.
  * [#1628](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1628) Clicking in field w/ 1 value in dropdown sets the value directly
    * Fixes the List widget with only 1 entry. Now opening the list widget drop-down instead of directly setting the single entry.
  * [#1630](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1630) Setting attributes in orderline not working
    * Fixes the Listr widget for attributes in document lines.
  * [#1638](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1638) Cannot use the dropdown in login
    * Fixes the dropdown widget in login window.
  * [#1645](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1645) Mandatory List Widget
    * Fixes the visibility for empty mandatory list widget fields.

# metasfresh 5.46 (2018-09)
**release for week 2018-09**

## Features
* metasfresh
  * [#3430](https://github.com/metasfresh/metasfresh/issues/3430) Best before Date evaluating process/ action
    * New action that analyzes the Handling Units on stock and marks them as "Best before" cases if the best-before-date has reached a time offset.
  * [#3503](https://github.com/metasfresh/metasfresh/issues/3503) Use correct picking quantities in shipment
    * Improvement of shipment document lines generation. Adjusting the base data to fill the picking quantities in shipment.
  * [#3513](https://github.com/metasfresh/metasfresh/issues/3513) Webui: Pimp DATEV windows
    * New Windows for accounting export and export formats in webui.
  * [#3515](https://github.com/metasfresh/metasfresh/issues/3515) Webui: Pimp Compensation Group Schema window
    * New Window for Compensation group maintenance.
  * [#3516](https://github.com/metasfresh/metasfresh/issues/3516) Price/ Discount Change restrictions Order/ Invoice Pharma
    * New feature that allows to restrict user changes on discounts and prices during sales order recording, depending on the configuration in product prices.
  * [#3532](https://github.com/metasfresh/metasfresh/issues/3532) New Window for Shipment Statistics
    * New Window that shows shipment statistics and allows to filter and export as excel file.
  * [#3534](https://github.com/metasfresh/metasfresh/issues/3534) Webui Window Design: Create window for Picking Config
    * New Window for Picking group Configuration.
  * [#3537](https://github.com/metasfresh/metasfresh/issues/3537) Remove legacy M_Production code
    * Internal housekeeping, removing some old/ unused M_Production code.
  * [#3555](https://github.com/metasfresh/metasfresh/issues/3555) Automatic Group Discount Calculation
    * New action in Sales order that allows to group lines and automatic select the appropriate discount depending on quantities and amount breaks.
  * [#3557](https://github.com/metasfresh/metasfresh/issues/3557) Respect Picking Group Selection in Picking HU Editor
    * Improved Handling Unit Filterin in Picking, now also respecting the newly introduces Picking Groups.

* metasfresh-webui-api
  * [#820](https://github.com/metasfresh/metasfresh-webui-api/issues/820) Picking Terminal: Detailed Picking Issues
    * Improvents of some workflow and filtering details (overdelivery allowed, initial quantities calculated and set in picking action, initially retrieving and setting the packing instructions) in the new picking Terminal in WebUI.
  * [#857](https://github.com/metasfresh/metasfresh-webui-api/issues/857) Material Cockpit additional Filter criteria
    * New Filter criteria added in the Material Cockpit.
  * [#876](https://github.com/metasfresh/metasfresh-webui-api/issues/876) Preset Product and Qty in Process/ Action Pick CU
    * Improved workflow in Pick Customer Unit Action. Now presetting the product and the remaining quantity to pick.
  * [#870](https://github.com/metasfresh/metasfresh-webui-api/issues/870) Extend Attribute / Label Feature to support tab link columns / Foreign Key
    * Improvement of the Label Feature, now allowing to link to the subtabs parent link column when set.
  * [#877](https://github.com/metasfresh/metasfresh-webui-api/issues/877) Picking tray Clearing action: Take out and add to new/ existing HU
    * New action that allows to take aout multiple Customer Units at once and add them to a new or existing Package Handling Unit.

* metasfresh-webui-frontend
  * [#1554](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1554) Action Parm modal Overlay focus missing n 1st Field
    * Improves the behavior of focused List widgets. Now the drop-down list is only opened if the user presses arrow-down.

## Fixes
* metasfresh
  * [#3452](https://github.com/metasfresh/metasfresh/issues/3452) Material Receipt candidates serial No - TU-CU case
    * Minor fix for the Handling Unit Serial No. distribution in material receipt. Now also working when selecting Transport units and Customer Units together.
  * [#3520](https://github.com/metasfresh/metasfresh/issues/3520) Workpackage with error for Update Invalid Shipment Schedules
    * Internal Housekeeping, solving an issue with a corercase in async workpackage processing.

* metasfresh-webui-frontend
  * [#1394](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1394) Quickactions not loaded after using barcode/ std. filter in HU editor in Manufacturing Order
    * Fixes a Bug that let the Quickactions Buttons disappear after setting filter criteria.
  * [#1567](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1567) Create purchase orders: Date promised format
    * Fixes the date/ time format in create puchase orders modal overlay in sales order.
  * [#1585](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1585) Date recording with keys leads to error
    * Fixes a Bug that occurred when trying to record a date via keyboard instead of calendar widget.
  * [#1604](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1604) Date Range Picker shows wrong day of the week
    * Fixes the shown day of week in date range picker.

* metasfresh-webui-api
  * [#856](https://github.com/metasfresh/metasfresh-webui-api/issues/856) t_query_selection grows huge when importing products or partners massively
    * Improved processing of the Query Selection when doing a Businesspartner or Products Import with large datasets.
  * [#859](https://github.com/metasfresh/metasfresh-webui-api/issues/859) Navigation Menu shown duplicated for Windows not in Menu
    * Fixes the Breadcrumb navigation menu for windows that are not included in WebUI menu. Now not showing a duplicated drop-down list anymore.
  * [#860](https://github.com/metasfresh/metasfresh-webui-api/issues/860) Shipment disposition does not update when you complete the order
    * Fix for the shipment schedule recompute after sales order complete. Now updated correctly again so the picking terminal is now shown correctly in document reference list again.
  * [#863](https://github.com/metasfresh/metasfresh-webui-api/issues/863) Fix available stock (ATP) display in sales order
    * Fixes the available to promise information in sales orderline batch entry.
  * [#869](https://github.com/metasfresh/metasfresh-webui-api/issues/869) Sometimes the error message is in German even if I am logged in with English
    * Minor fix for the translation of error messages. In some cases the error messages were shown in the base language instead of the language the user was logged in.
  * [#871](https://github.com/metasfresh/metasfresh-webui-api/issues/871) Error when selecting tabs in bpartner window
    * Fixes a cornercase that occurred when seleting included tabs in the businesspartner window in WebUI for records that were recorded via swingUI.
  * [#872](https://github.com/metasfresh/metasfresh-webui-api/issues/872) Don't show passwords in grid view
    * Fixes the password fields in Webui Grid View, now showing them obfuscated too, like in main view.

# metasfresh 5.45 (2018-08)
**release for week 2018-08**

## Features
* metasfresh
  * [#2777](https://github.com/metasfresh/metasfresh/issues/2777) Vendor Invoice Layout and Translations in WebUI Improvements
    * Adds Translations and Layout Impovement to Purchase Invoice window.
  * [#3246](https://github.com/metasfresh/metasfresh/issues/3246) Barcode Search in Handling Unit Editor takes too long
    * Improved Performance in handling Unit Editor when searching via Barcode Filter and Default Filter Handling Unit Value.
  * [#3409](https://github.com/metasfresh/metasfresh/issues/3409) Distribution Order/ Movement after Material Receipt
    * Adds an automatism Functionality to Product Plannung. Allows to automatically create Distribution Orders to bring received goods to their locators after material receipt.
  * [#3414](https://github.com/metasfresh/metasfresh/issues/3414) Credit Limit check in Sales Order
    * New Functionality in Sales Order, now checking the customers credit limit when completing the Order.
  * [#3437](https://github.com/metasfresh/metasfresh/issues/3437) MSV3 handle deviating response from remote MSV3 server
    * Improved communication between MSV3 Server and response presentation to the user.
  * [#3451](https://github.com/metasfresh/metasfresh/issues/3451) Extract esb code into dedicated repo
    * Internal housekeeping improvement, moving the code for enterprise service bus to dedicated repository.
  * [#3457](https://github.com/metasfresh/metasfresh/issues/3457) Improve performance around update_trl_tables_on_ad_element_trl_update
    * Performance Improvement of the Translation update mechanisms.
  * [#3481](https://github.com/metasfresh/metasfresh/issues/3481) Force refreshing virtual column CreditLimitIndicator from C_BPartner
    * Now allowing a just in time refresh of the credit limit indicator in Business Partner Window.
  * [#3483](https://github.com/metasfresh/metasfresh/issues/3483) Default Value for process create order from quotation
    * Adds default parms to the create order from quotation process/ action.
  * [#3490](https://github.com/metasfresh/metasfresh/issues/3490) Paperless Credit Limit approval
    * Improved workflow for the credit limit approval. Now the responsible user receives a notification as soon a credit limit shall be approved.
  * [#3502](https://github.com/metasfresh/metasfresh/issues/3502) Default Filter Improvements
    * Adds various Filter criteria to default window filter lists.
  * [#3505](https://github.com/metasfresh/metasfresh/issues/3505) Improvement of Procurement Candidates
    * Usability Improvement of the procurement Planning window. Rearranged fields for better visibility.
  * [#3508](https://github.com/metasfresh/metasfresh/issues/3508) Implement DATEV export window
    * New window and Fucntionality in WebUI that allows to export the Accountings in metasfresh to Datev.

* metasfresh-webui-api
  * [#851](https://github.com/metasfresh/metasfresh-webui-api/issues/851) Virtual field from header doc shall be refreshed when one of the included row is changed
    * Just-in-time update of the windows content for Virtual columns.
  * [#853](https://github.com/metasfresh/metasfresh-webui-api/issues/853) Automatic group creation in sales order lines
    * New automatic grouping functionality in sales orderlines.
  * [#854](https://github.com/metasfresh/metasfresh-webui-api/issues/854) If the window is missing from menu render it's name in breadcrumb
    * Window Breadcrumb improvement, now showing the windowname of windows that were reached via zoom and are not in menu yet.

* metasfresh-webui-frontend
  * [#1596](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1596) Add Enzyme to test setup
    * Extending our Test Framework with Enzyme.

## Fixes
* metasfresh
  * [#3216](https://github.com/metasfresh/metasfresh/issues/3216) Invoice candidate: The isRecompute flag is not reseted on N ever
    * Fix for invoice Candidates, now not recomputing anymore when candidates are processed.
  * [#3357](https://github.com/metasfresh/metasfresh/issues/3357) SQL Exception in Window Entity Type
    * Fixes a SQL Exception that occurred in Entity Type window.
  * [#3465](https://github.com/metasfresh/metasfresh/issues/3465) Window Role Tab User Access shows non systemusers
    * Fixes the Subtab for Tab User Access in Role window. Now only showing available Systemusers.
  * [#3486](https://github.com/metasfresh/metasfresh/issues/3486) Material Disposition automatic entries missing for DD/ PP Orders
    * Fixes the creation of Distribution and Manufacturing Orders in material Disposition.
  * [#3499](https://github.com/metasfresh/metasfresh/issues/3499) Cannot create manual invoice
    * Fixes the functionality to create manual Invoices.
  * [#3521](https://github.com/metasfresh/metasfresh/issues/3521) Exception with CreditStatus in some data constellations
    * Minor Bugfixes for the new credit status functionality.

* metasfresh-webui-api
  * [#846](https://github.com/metasfresh/metasfresh-webui-api/issues/846) Cache issue in shipment schedules subtab
    * Fixes a cache issue in a shipment shedules included subtab.
  * [#847](https://github.com/metasfresh/metasfresh-webui-api/issues/847) Error in picking terminal
    * Fixes a Bug that occurred in Picking terminal when picking a large amount of Handling Units.
  * [#848](https://github.com/metasfresh/metasfresh-webui-api/issues/848) Sorting by Virtual Column manually in webui throws error
    * Fixes the possibility to order by for virtual column in grid view.

* metasfresh-webui-frontend
  * [#1592](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1592) Added subrow is still/again not shown in the frontend
    * Fixes the feature for included subrows and asynchronous reponse.


# metasfresh 5.44 (2018-07)
**release for week 2018-07**

## Features
* metasfresh
  * [#3405](https://github.com/metasfresh/metasfresh/issues/3405) Schema for Product Planning Data
    * New Feature that allows to define default Product Planning Data. These Schemas are used to ensure the automatic creation of proper distribution orders for newly imported products.
  * [#3406](https://github.com/metasfresh/metasfresh/issues/3406) Set Selector in M_Product after Product Data Import
    * New Selector Feature for Product Import allowing to join new Product Data to Product Planning Schema.
  * [#3407](https://github.com/metasfresh/metasfresh/issues/3407) New Businesspartner Window for Pharma vertical
    * New Business Partner Window in WebUI for Pharma Verticals.
  * [#3408](https://github.com/metasfresh/metasfresh/issues/3408) New Product Window for Pharma vertical
    * New Product Window in WebUI for Pharma Verticals.
  * [#3413](https://github.com/metasfresh/metasfresh/issues/3413) Credit Limit data structure as subtab in Businesspartner
    * New Credit Limit functionality. Now allowing to define Credit Limit per Customer on different levels.
  * [#3458](https://github.com/metasfresh/metasfresh/issues/3458) Remove M_Storage-based legacy check from MProduct
    * Internal housekeeping improvement, getting rid of old storage based checks.
  * [#3467](https://github.com/metasfresh/metasfresh/issues/3467) Preparation Date Filer as Date Range
    * Adjustment for the Preparation Date Filter in Manufacturing Order. Now is a Date Range filter instead of date.
  * [#3468](https://github.com/metasfresh/metasfresh/issues/3468) Add Translation for "All Dates available"
    * Adds the translation for en_EN, de_DE of the "Show all dates" hint in Date Range filter.
  * [#3471](https://github.com/metasfresh/metasfresh/issues/3471) Disable Sales Opportunities Window until its permission is configurable
    * Hiding the Sales Opportunity Window from default WebUI Menu, as long as in beta.
  * [#3487](https://github.com/metasfresh/metasfresh/issues/3487) Extend Identifier Transportation Order
    * Improving the Transportation Order identifier, now additionally showing Date and Tour.
  * [#3495](https://github.com/metasfresh/metasfresh/issues/3495) Improvements to Product Price Window
    * Improving Product Price Window in WebUI, adding Active indicator and removing Price Matching Order from grid View.

* metasfresh-webui-api
  * [#818](https://github.com/metasfresh/metasfresh-webui-api/issues/818) Values.valueToJsonObject() shall return JSONNullValue instead of null
    * Internal improvement of the Handling of null values in JSON Objects.

* metasfresh-webui-frontend
  * [#1501](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1501) Automated Javascript and React Testing with Jest
    * Kickoff of the autmated Testing Framework for metasfresh setup on Jest and Enzyme.
  * [#1590](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1590) Included tabs sorting
    * Improvement of the sorting functionality in main and included subtab Grid Views.
  * [#1593](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1593) Copy/ Paste for selected Field in Subtab GridView not giving correct result
    * New Feature that allows to mark a Field in Grid View and copy the field content to clipboard.
  * [#1598](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1598) Changes in location not always saved
    * Fixes the handling of the location recording.

## Fixes
* metasfresh
  * [#3445](https://github.com/metasfresh/metasfresh/issues/3445) Qty TU not correct in purchase invoice jasper
    * Fixes the Purchase Invoice document. Now the correct quantity of Handling units is shown again.
  * [#3477](https://github.com/metasfresh/metasfresh/issues/3477) Cannot save manufacturing order
    * Fixes an error in manufacturing order window, now allowing to save the order again.

* metasfresh-webui-api
  * [#806](https://github.com/metasfresh/metasfresh-webui-api/issues/806) Add to Transportation Order, Ship and Invoice action in Picking Clearing Tray not respecting the invoice schedule
    * Fixes the Action for automatic creation of invoices after picking & packing. Now the action is respecting special invoice schedules for the given customer.
  * [#807](https://github.com/metasfresh/metasfresh-webui-api/issues/807) Quantity to invoice override callout
    * Fixes a Bug when updating the Quantity to Invoice override. Now the effective quantity is updated again.
  * [#829](https://github.com/metasfresh/metasfresh-webui-api/issues/829) Planning status in manufacturing order not updated
    * Fixes an issue in Manufacturing Order, leaving the Planning Status unchanged after processing the order.

* metasfresh-webui-frontend
  * [#1546](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1546) Date Range Filter as Range Filter has initial daterange
    * Leaves the Daterange filter now initially empty to that it does not interrupt other filtering.
  * [#1553](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1553) Drop down lists remain displayed when using tab several times (again)
    * Fixes a Bug with dropdown lists staying open after tabbing away.
  * [#1558](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1558) Dropdown fields not hidden when creating a new Business Partner
    * Fixes the reamining dropdown in Purchase Order when calling the "New Businesspartner" action in context menu.
  * [#1559](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1559) Errors in the console when opening HU Editor
    * Fixes errors in console that popped up when opening the Handling Unit Editor.
  * [#1588](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1588) Zoom Into is not working
    * Fixes the Zoom-To functionality that allows the user to click on field labels and jump to detail data when available.

# metasfresh 5.43 (2018-06)
**release for week 2018-06**

## Features
* metasfresh
  * [#3410](https://github.com/metasfresh/metasfresh/issues/3410) Enlarge AD_Table.TableName
    * Resizing the Fields for Tablename in Applicaiton Dictionary, now allowing 80 chars.
  * [#3411](https://github.com/metasfresh/metasfresh/issues/3411) Implement MSV3 purchase order
    * New Feature in Pharma Vertical, allowing to create MSV3 Purchase Orders.
  * [#3417](https://github.com/metasfresh/metasfresh/issues/3417) Support new VAT rate of 7.7% in Switzerland from 01.01.2018
    * New Tax Masterdate for VAT in Switzerland valid from 01-01-2018.
  * [#3419](https://github.com/metasfresh/metasfresh/issues/3419) Throw meaningful exception if MOrderLine.beforeSave has problems
    * Improving error messages in Orderline, now returning meaningful information to the user.
  * [#3428](https://github.com/metasfresh/metasfresh/issues/3428) Switch off Daterange in Preparation date Filer temporarily
    * Changes the Filter of Preparation Date to simple Date instead of Daterange.
  * [#3438](https://github.com/metasfresh/metasfresh/issues/3438) Qty LU wrong in purchase invoice candidates / invoice
  * [#3439](https://github.com/metasfresh/metasfresh/issues/3439) Improvement of GO Delivery Order Window
    * Improved the visibility of important information in the GO! Delivery Window in WebUI.
  * [#3440](https://github.com/metasfresh/metasfresh/issues/3440) Improvement of Shipper Window in WebUI
    * Extends the Shipper Window in WebUI, now also allowing the recording of the GO! Shipper Configuration.
  * [#3454](https://github.com/metasfresh/metasfresh/issues/3454) Resolve log warning "Skip setting parameter value for X_AD_PInstance_Para[0]"
  * [#3455](https://github.com/metasfresh/metasfresh/issues/3455) Migitate "PO not handled: Document{tableName=M_InOut.." and similar warnings flooding the log

* metasfresh-webui-api
  * [#812](https://github.com/metasfresh/metasfresh-webui-api/issues/812) Picking Tray Clearing: Action take out and add to LU/ TU
    * New Action for Picking Tray Handling Unit removals and automatic adding to an exiting Logistics Unit.
  * [#815](https://github.com/metasfresh/metasfresh-webui-api/issues/815) Transform TU to exiting LU takes too long for Handling Unit Dropdown List
    * Performance Improvement of Transformation List Dropdown in HU Editor.
  * [#817](https://github.com/metasfresh/metasfresh-webui-api/issues/817) Include stacktrace when providing the error to frontend
    * Now providing the stacktrace from Backend to Frontend. Can be switched on in Frontend config for testing purpose.

* metasfresh-webui-frontend

## Fixes
* metasfresh
  * [#3022](https://github.com/metasfresh/metasfresh/issues/3022) Contracts: ContractDocumentNo is 0 all the time
    * Fixes the automatic Document No generation in Contracts, now not leaving Document No 0 there anymore.
  * [#3394](https://github.com/metasfresh/metasfresh/issues/3394) Sales inout Jasper: HU name taken from line
    * Fix for the Packing Instruction shown in Sales Inout Document when using manual Packing Material.
  * [#3402](https://github.com/metasfresh/metasfresh/issues/3402) Can't credit memo a partially paid invoice
    * Improvement of the status Handling after reopening Manufacturing Orders.
  * [#3449](https://github.com/metasfresh/metasfresh/issues/3449) ImportHelper doesn't switch ctx

* metasfresh-webui-api
  * [#617](https://github.com/metasfresh/metasfresh-webui-api/issues/617) Cache is not invalidated on country change
    * Fixes a Bug in Cache Invalidation for Country changes and Pricing.
  * [#801](https://github.com/metasfresh/metasfresh-webui-api/issues/801) Transforming aggregated CU with "CU to existing TU" not working correctly
    * Fixes an error in the "CU to existing TU" action.
  * [#802](https://github.com/metasfresh/metasfresh-webui-api/issues/802) Transforming aggregated CU with "CU to new TU" not working correctly
    * Fixes an error "CU to existing TU" action, sometime leaving wrong calculates quantities behind.
  * [#810](https://github.com/metasfresh/metasfresh-webui-api/issues/810) Update QtyPicked after picking
    * Fixes a Refresh Bug, leaving the Picked Quantities not updates after picking the Product.
  * [#811](https://github.com/metasfresh/metasfresh-webui-api/issues/811) Java Nullpointer Exception in special case with multiple attribute and multi column layout
    * Fixes a NPE in multi (>2) Column Layout Windows.

* metasfresh-webui-frontend
  * [#1528](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1528) Added subrow is not shown in the frontend
    * Improved response from MSV3 request so that purchase candidate row is shown and user can directly start editing.
  * [#1538](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1538) Date Fields not patched
    * Fixes an error with Date Fields, now patching them after changes again.
  * [#1543](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1543) Sales Order window Purchase Order modal overlay broken again
    * Fixes the Purchase Order modal overlay functionality in Sales Order.
  * [#1564](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1564) Some fields are not patched after you change them twice
    * Fixes the multiple adjustment behavior of Text and numeric fields. now content is patched after each change.
  * [#1566](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1566) Subtab Data not shown when clicked first time
    * Fixes a Refresh Bug in Subtab data. Now showing the actual data already when opening the subtab first time.
  * [#1570](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1570) Views: fails when applying a filter without parameters
    * Fixes the Filter. Now does not fail when applied without filter criteria.

# metasfresh 5.42 (2018-05)
**release for week 2018-05**

## Features
* metasfresh
  * [#3299](https://github.com/metasfresh/metasfresh/issues/3299) Automatic Picking
    * New Feature that allows to define Products in Planning Data, that shall be automatically picked after action receipt in manufacturing.
  * [#3302](https://github.com/metasfresh/metasfresh/issues/3302) Payment Discount on Orderline, Invoice Candidate
    * Adding a Payment Discount overwrite to Orderlines and Invoice Candidates. With this feature one can add Payment Discounts as additional Discount Schema entry.
  * [#3310](https://github.com/metasfresh/metasfresh/issues/3310) Webui Window Design: Improve window System Issue Report
    * New Window System Issue Report in WebUI, allowing teh System Administrator to check System Issues.
  * [#3313](https://github.com/metasfresh/metasfresh/issues/3313) Webui Window Design: Improve window Field Group
    * New Window in WebUI for Field Group maintenance. This window can be used by System Admins.
  * [#3317](https://github.com/metasfresh/metasfresh/issues/3317) Webui Window Design: Improve window Report & Process
    * New Window in WebUI for Process & Reports maintenance.
  * [#3318](https://github.com/metasfresh/metasfresh/issues/3318) Webui Window Design: Improve window Reference
    * New Window in WebUI for Reference maintenance.
  * [#3319](https://github.com/metasfresh/metasfresh/issues/3319) Webui Window Design: Improve window Message
    * Improvement of the WebUI message window.
  * [#3322](https://github.com/metasfresh/metasfresh/issues/3322) Webui Window Design: Improve window Validation Rule
    * New Window for the maintenance of Validation Rules in metasfresh. This window can be used by System Admins.
  * [#3329](https://github.com/metasfresh/metasfresh/issues/3329) Webui Window Design: Translate all in window Event store
    * Improved Window, Tab and Fields translations in the Event Store Window..
  * [#3330](https://github.com/metasfresh/metasfresh/issues/3330) Webui Window Design: Improve window Entity Type
    * New Window for the maintenance of Entity Types. This window can be used by System Admins.
  * [#3338](https://github.com/metasfresh/metasfresh/issues/3338) Harmonize description field length between doctype and c_order / c_invoice
    * Improvement of description fields in doctype. Now the fields in Order and Invoice doctype have the same field length.
  * [#3346](https://github.com/metasfresh/metasfresh/issues/3346) Remove rebel-remote.xml files
    * Internal Housekeeping improvement. Removing remote rebel files.
  * [#3347](https://github.com/metasfresh/metasfresh/issues/3347) Drop M_Product_Costing
    * Dropping the legacy Costing Functionality, making place for the new Costing Engine to come.
  * [#3349](https://github.com/metasfresh/metasfresh/issues/3349) Add BPartner to User Window
    * New Field in Window User, allowing to see and maintain the Business Partner.
  * [#3353](https://github.com/metasfresh/metasfresh/issues/3353) Implement MSV3 availability query
    * New vertical Feature for the Pharma Industry. Now it's able to do MSV3 Queries to vendors via metasfresh.
  * [#3354](https://github.com/metasfresh/metasfresh/issues/3354) Different subscription Receiver for ever
  * [#3355](https://github.com/metasfresh/metasfresh/issues/3355) Add option to automatically credit open invoice for terminated contract / subscription
  * [#3359](https://github.com/metasfresh/metasfresh/issues/3359) User Window unlock Account Action
    * Adding new Action in User Window in WebUI allowing to unlock the User Account.  
  * [#3366](https://github.com/metasfresh/metasfresh/issues/3366) Webui Window: Create window for ReferenceNo and Reference Type
    * New Windows for Reference No and Reference No Type maintenance.
  * [#3377](https://github.com/metasfresh/metasfresh/issues/3377) Add an ID to C_ReferenceNo_Type_Table
    * Improvement of the Reference No Type Table, now having a Primary Key.
  * [#3387](https://github.com/metasfresh/metasfresh/issues/3387) WebUI New Window for MSV3 Config
    * New Configuration Window in WebUI for MSV3 Connections to vendors in german Pharma Industry.
  * [#3385](https://github.com/metasfresh/metasfresh/issues/3385) Enlarge column AD_EntityType.EntityType
    * Resizing the Field entitytype from 40 to 512 chars.
  * [#3389](https://github.com/metasfresh/metasfresh/issues/3389) Performance issue related to zooming table record references
    * Improved Performance for table record references.
  * [#3416](https://github.com/metasfresh/metasfresh/issues/3416) Save termination date explicitly

* metasfresh-webui-api
  * [#744](https://github.com/metasfresh/metasfresh-webui-api/issues/744) Support Export of massive records to Excel
    * Improved Functionality of Excel Export, now allowing to export all selected records via main grid view and action menu entry.
  * [#766](https://github.com/metasfresh/metasfresh-webui-api/issues/766) Precision Layout in Price of Orderlines
    * New Precision Functionality in WebUI, now allowing to record and display more that 2 digits in precision.
  * [#772](https://github.com/metasfresh/metasfresh-webui-api/issues/772) EMail Attachment Name in WebUI
    * Improvement of the eMail Attachment Name in WebUI eMail editor, now showing the Documenttype and Documentno as combined attachment Name.
  * [#785](https://github.com/metasfresh/metasfresh-webui-api/issues/785) Act gracefully on old/invalid AD_UserQuery
    * Improvement of the User Query Handling. Now allowing the WebUI to ract on old/ incompatible Queries instead of failing.
  * [#788](https://github.com/metasfresh/metasfresh-webui-api/issues/788) Allow Filtering of Label-Type-Fields in WebUI
    * New Functionality that allows to add Label Type Fields in WebUI as Filter criteria.
  * [#793](https://github.com/metasfresh/metasfresh-webui-api/issues/793) Provide "loadDuration" when fetching document references
    * Internal Housekeeping improvement. Now providing the loadDuration of Document References in WebUI.
  * [#794](https://github.com/metasfresh/metasfresh-webui-api/issues/794) Provide actions evaluateDuration
    * Internal Housekeeping improvement. Now providing the evaluateDuration of Actions in WebUI.
  * [#798](https://github.com/metasfresh/metasfresh-webui-api/issues/798) Allow Filtering for Table References and show table identifier
    * New Feature in Labels widget. Now allowing to add and use Table references instead of reference lists. Also possible to add Label to Filter list.
  * [#813](https://github.com/metasfresh/metasfresh-webui-api/issues/813) Reduce Export Time for massive Exports

* metasfresh-webui-frontend
  * [#1252](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1252) Select all x items shall not be available when there is only one page of entries
    * Improved Handling of the select all functioanlity in Grid View. Now only showing toggle option when more than 1 page.
  * [#1502](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1502) Missing PropTypes Validation for noLabel isOpenDatePicker
    * Internal Housekeeping Issue. Adding PropType Validation for isOpenDatePicker.
  * [#1504](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1504) Make a difference between internal date format (ISO) and user friendly locale date
    * Improved Handlign of Date Widget reponses. Now distiguishing between Date for API and Date presented to the user.
  * [#1509](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1509) Harmonize details calendar widget with daterange widget in Filter
    * Improving the Layout of Date Picker, harmonizing with the look and feel of Date Range Widget.
  * [#1514](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1514) Filter Dropdown dynamically extend height when dropdown list selected
    * Improved Dropdown behavior. Now showing a complete dropdown List in Filters also when at the bottom of a Filter list area.
  * [#1428](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1428) Calendar icon covers date field
    * Refining the look and feel of the calendar icon behavior in WebUI grid views.

## Fixes
* metasfresh
  * [#1196](https://github.com/metasfresh/metasfresh/issues/1196) Creating new product in new database causes error
    * Fixes the sequence for M_Product Values on new Database seeds.
  * [#3196](https://github.com/metasfresh/metasfresh/issues/3196) Dunning run: Exception on PDF generation
    * Fixes the Dunning Run Action. Now the automatic PDF Generation works smoothly again.
  * [#3339](https://github.com/metasfresh/metasfresh/issues/3339) Field "Doctype" is included twice in payment window
    * Fixes a doubled Field for Doctype in the payment window.
  * [#3350](https://github.com/metasfresh/metasfresh/issues/3350) Error creating movement from DDOrder
    * Fixes an Error that prevented the creation of movements from Distribution Orders.
  * [#3386](https://github.com/metasfresh/metasfresh/issues/3386) Issued HUs retain I status after PP_Order unclose
  * [#3426](https://github.com/metasfresh/metasfresh/issues/3426) Dunning Level is not set in invoice after generating dunning doc

* metasfresh-webui-api
  * [#775](https://github.com/metasfresh/metasfresh-webui-api/issues/775) Destroyed HUs are still visible in material receipt
    * Destroyed HU are not shown in Handling Unit Editor anymore during the Material Receipt workflow.
  * [#777](https://github.com/metasfresh/metasfresh-webui-api/issues/777) Date and time filter: truncate to Day when filtering
    * Improvement of the Filter with Field format DateTime, now Truncating to Date when filtering.
  * [#781](https://github.com/metasfresh/metasfresh-webui-api/issues/781) HU editor view gets empty after splitting out one TU
    * Now showing the newly split Transportation Unit in Handling Unit Editor after transform.
  * [#786](https://github.com/metasfresh/metasfresh-webui-api/issues/786) Follow up destroyed HUs are still visible in material receipt
    * Hiding destroyed Handling Units in Material Receipt.
  * [#787](https://github.com/metasfresh/metasfresh-webui-api/issues/787) Deadlocks occurring around material cockpit
    * Fixes some deadlocks that occurred through Material Cockpit logic.
  * [#796](https://github.com/metasfresh/metasfresh-webui-api/issues/796) Error on filter with +/- buttons
    * Fixes an error that occurred when using the new DateSwitcher Widget.
  * [#803](https://github.com/metasfresh/metasfresh-webui-api/issues/803) NPE in Picking Tray Clearing
    * Fixes a Null Pointer Exception that ocurred under certain circumstances in Picking Tray Clearing Window in WebUI.
  * [#804](https://github.com/metasfresh/metasfresh-webui-api/issues/804) Error in Picking Tray Clearing when adding to existing HU
    * Fixes an Error that occurred in Picking Tray Clearing after adding to an existing Handling Unit.

* metasfresh-webui-frontend
  * [#1447](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1447) Picking window broken
    * Fixes the Picking Terminal Picking Slot View. Now showing the proper information about the Picking Slot Content and Businesspartner reservation.
  * [#1494](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1494) Picking Tray Clearing window: fix: Warning: Failed prop type: Invalid prop `selected` of type `string` supplied to `DocumentList`, expected `array`
    * Fixes the props validation in Picking Tray Clearing Window.
  * [#1495](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1495) Error on "add to transportation order"
    * Fixes a Bug that occurred in window Picking Tray Clearing when adding an HU to a Transportation Order.
  * [#1498](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1498) Frontend subscriptions to api's view topic get out of hand
    * Reducing the subscription amount per view, allowing to reduce the websocket connections "overload" on server side.
  * [#1508](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1508) Scrollbar in Filter when hovering apply Button
    * Improvement of the Tooltip Layout for Apply Button in Filter Selection Dropdowns.
  * [#1517](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1517) Discard changes in modal window
    * Fixes the discard of incomplete rows because of not filled mandatory fields.
  * [#1518](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1518) Create purchase orders: error in console
    * Fixes a Bug that prevented the creation of Purchase orders from Sales Orders to be done without errors.
  * [#1520](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1520) Typeahead error in new Label Filter
    * Fixes a Typeahead error in the new Label Filter in WebUI.
  * [#1529](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1529) Email To field does not work correctly
    * Improves the mailTo Field in WebUI. Now showing the Username instead of the user ID.
  * [#1536](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1536) Cannot set attributes in orderline
  * [#1552](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1552) Selecting country in dropdown with mouse click not possible

# metasfresh 5.41 (2018-03)
**release for week 2018-03**

## Features
* metasfresh-app
  * [#3209](https://github.com/metasfresh/metasfresh/issues/3209) Material Receipt candidates action for batch serial No on selected HU
    * New Action added for Serial No generation in Material Receipt and distribution to its received Handling Units.
  * [#3213](https://github.com/metasfresh/metasfresh/issues/3213) New Action for Repost Document in Accounting Transaction Window
    * New Action in Accounting Transaction Window, allowing to repost the shown lines.
  * [#3245](https://github.com/metasfresh/metasfresh/issues/3245) Material Cockpit Details modal overlay
    * Improvement for Material Cockpit, now allowing a modal overlay with detailed information about reserved and ordered quantities and their documents.
  * [#3271](https://github.com/metasfresh/metasfresh/issues/3271) Implement event log
    * New Event Log Functionality that is used first time for nearly realtime Material Cocpit updates.
  * [#3279](https://github.com/metasfresh/metasfresh/issues/3279) Pharma: Copy missing product prices after import
    * New Functionality after import of pharmaceutlical prodcuts. Now automatically creating product prices in the corresponding pricelist versions.
  * [#3284](https://github.com/metasfresh/metasfresh/issues/3284) Translation of eMail Editor in WebUI
    * Translation of the eMail Editor in WebUI for Language/ Locale de_DE.
  * [#3286](https://github.com/metasfresh/metasfresh/issues/3286) Workaround for truncating T_WEBUI_ViewSelection/-Line
    * Temporary Solution for the data growth of ViewSelection and ViewSelectionline tables.
  * [#3288](https://github.com/metasfresh/metasfresh/issues/3288) Shipper Transportation: Shipper Location: change validation rule logic
    * Adjustment of the Shipper Transportation validation rule logic.
  * [#3289](https://github.com/metasfresh/metasfresh/issues/3289) WebUI: New Window for Document Details in Material Cockpit
    * New Window for Document Details that can now be shown via Material Cockpit Window.
  * [#3293](https://github.com/metasfresh/metasfresh/issues/3293) WebUI: Add Translation Messages for Daterangepicker
    * Improved Translations. Added static translations for de_DE, en_US and Date Range Picker.
  * [#3303](https://github.com/metasfresh/metasfresh/issues/3303) WebUI: New Window for GO! Delivery Orders
    * New Window in WebUI for the new Functionality of General Overnight Shipper Transportation.
  * [#3304](https://github.com/metasfresh/metasfresh/issues/3304) Copy C_DocType.Description/DescriptionBottom to Order and Invoice
    * New feature for Documents Generation. Now it's possible to define default text snippets in Document Types and copy these automatically to the created document description when selecting the Document type.
  * [#3309](https://github.com/metasfresh/metasfresh/issues/3309) WebUI Window Design: Improve window Event store
    * New System Admin Window Event Store. Allows the System Admin to view the status of events and checking/ resubmitting them in case of issues.

* metasfresh-webui-API
  * [#672](https://github.com/metasfresh/metasfresh-webui-api/issues/672) Window for easy Distribution Orderlines Handling in WebUI
    * New Window that allows the Handling of Distribution Orderlines in WebUI.
  * [#737](https://github.com/metasfresh/metasfresh-webui-api/issues/737) Deal properly with T_WEBUI_ViewSelection[Line]
    * Improvement of WebUI temporary selections.
  * [#745](https://github.com/metasfresh/metasfresh-webui-api/issues/745) Batch entry mode w/o Packing Instruction branch:master type:enhancement
    * Improvement of the Batch entry mode functionality. Now it's possible to configure the Batch entry via Sysconfig so that no Packing Instruction is needed for recording.
  * [#762](https://github.com/metasfresh/metasfresh-webui-api/issues/762) Show onhand quantity in new WebUI MRP Product Info Window
    * New Field in material Cockpit, now showing the on Hand Quantity too.
  * [#770](https://github.com/metasfresh/metasfresh-webui-api/issues/770) Raise session Timeout of WebUI Application
    * Now possible to configure the session timeout of metasfresh via Spring Boot.
  * [#778](https://github.com/metasfresh/metasfresh-webui-api/issues/778) Use readOnlyFlag from AD_Field and AD_Tab when creating DocumentLayoutElements
    * Improvement of the Read-Only functionality in WebUI, now respecting the configuration doen in AD_Fields and AD_TAB about the readOnly Flag.

* metasfresh-webui-frontend
  * [#1470](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1470) Bug in long texts with new line
    * Improving the behavior in LongText Fields. Now only patching the content when leaving the Field.
  * [#1478](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1478) Attachment in eMail Editor: Show File name/ caption instead of key
    * Improving the Names for attached Files in eMail Editor. Now showing the caption names instead of attachment keys.
  * [#1485](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1485) Tooltip for for "Select all"/ "Select all x rows"
    * New Tooltip for the Select All Functionality.

## Fixes
* metasfresh-app
  * [#3232](https://github.com/metasfresh/metasfresh/issues/3232) Disposal creates Invoice Candidates
    * Improved Feature, now possible to prohibit the creation of Invoice Candidates in Disposal Workflow.
  * [#3291](https://github.com/metasfresh/metasfresh/issues/3291) Search Icon disappears when switching Main-Subtab in Contract Window
    * Fixes the vanishing Search Icon in window contracts of metasfresh swingUI.

* metasfresh-webui-frontend
  * [#1348](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1348) Keeping lines selected when turning the page not working
    * Fixes the Pagination after selecting lines. Now the selections are kept when switching pages.
  * [#1429](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1429) Left and right key doesn't work on date edit field
  * [#1481](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1481) Cannot open "Create purchase orders" modal view
    * Now enables to open modal overlays from action menu again.

# metasfresh 5.40 (2018-02)

**release for week 2018-02**

## Features
* metasfresh-app
  * [#1752](https://github.com/metasfresh/metasfresh/issues/1752) System Element overwrite on Window Field
    *  New Feature in the Application Dictionary, now able to overwrite the system element on Field Level.
  * [#3155](https://github.com/metasfresh/metasfresh/issues/3155) Create special pharma import product table
    * New vertical Feature for the import of products from pharma industry.
  * [#3222](https://github.com/metasfresh/metasfresh/issues/3222) Refactor trx event listeners
    * Improvement of Transaction event listeners, now leading to explicit specification which code shall be executed on which event.
  * [#3229](https://github.com/metasfresh/metasfresh/issues/3229) Show c_activity_ID in purchase Orderlines Grid View
    * New Field for Activity in Purchase Orderlines.
  * [#3234](https://github.com/metasfresh/metasfresh/issues/3234) Material Tracking ID in Invoice Candidates Filter shall be search
    * Improving the Material Tracking ID Lookup widget. It is now an autocomplete search field instead of a dropdown list.
  * [#3237](https://github.com/metasfresh/metasfresh/issues/3237) New Document "Source of Supply"
    * Improvement of the shipping workflow, now also able t create a "Source of Supply" Document for the shipped Products.
  * [#3238](https://github.com/metasfresh/metasfresh/issues/3238) Create Missing M_Cost records on the fly
    * Improvement of the M_Cost handling, now creating missing entries on the fly.
  * [#3230](https://github.com/metasfresh/metasfresh/issues/3230) Pharma: import prices
    * New Import Prices functionality for products from pharma industry.
  * [#3239](https://github.com/metasfresh/metasfresh/issues/3239) WebUI: Material Receipt Candidates Fields not readonly
    * Improvement of the Material Receipt Candidates Window in WebUI. Now Fields are readonly that should not be changes by the user after creation.
  * [#3242](https://github.com/metasfresh/metasfresh/issues/3242) WebUI: Create new Customer Accounts Window
    * New Window for Customer Accounts, allowing the user to define customer relevant accounting information.
  * [#3243](https://github.com/metasfresh/metasfresh/issues/3243) WebUI: Adjust the BOM Configuration window in Subtab for components
    * Improvement of the BOM Configuration Window and Forumula. Now showing most important columns in grid view for components. Renaming Fields and translations for better understanding.
  * [#3255](https://github.com/metasfresh/metasfresh/issues/3255) WebUI: Accounting Schema Window
    * New Window in WebUI for Accounting Schema, allowing the user to maintain the default Accounting properties.
  * [#3256](https://github.com/metasfresh/metasfresh/issues/3256) WebUI: Window for Warehouse Accounting
    * New Window for Vendor Accounts, allowing the user to define warehouse relevant accounting information.
  * [#3257](https://github.com/metasfresh/metasfresh/issues/3257) WebUI: Window for Vendor Accounting
    * New Window for Vendor Accounts, allowing the user to define vendor relevant accounting information.
  * [#3260](https://github.com/metasfresh/metasfresh/issues/3260) Hide Accounts Tab from Warehouse Window in WebUI
    * Hiding Accounts Tab in Warehouse Window, now available via Document Reference.
  * [#3261](https://github.com/metasfresh/metasfresh/issues/3261) WebUI: New Window for BPartner Group Accounts
    * New Window for Business Partner Group Accounts, allowing the user to define relevant accounting information for Business Partner Groups.
  * [#3267](https://github.com/metasfresh/metasfresh/issues/3267) WebUI: Add Create Periods to Action menu in Calendar Window
    * Improving the Calender and Period Window in WebUI, adding the Create Periods process to action menu.
  * [#3269](https://github.com/metasfresh/metasfresh/issues/3269) WebUI: Add Shortcut Filter from Calendar Year to Periods Window
    * Improving the Usability of Calendar and Period Window, now allowing to directly zoom to filtered Periods for a given year.
  * [#3272](https://github.com/metasfresh/metasfresh/issues/3272) Provide Periods for 2018
    * Housekeeping issue, providing the periods for 2018 as migration script.
  * [#3275](https://github.com/metasfresh/metasfresh/issues/3275) New Filter for datepromised in Sales/ Purchase Order
    * Adding the Filter for datepromised in Sales Order and Purchase order Window in WebUI.

* metasfresh-webui-api
  * [#752](https://github.com/metasfresh/metasfresh-webui-api/issues/752) Picking Tray Clearing: picking slot filter no results
    * Improvement of the filtering in Picking Tray Window. Now showing "No Rows" for en empty result instead of en Error message.
  * [#758](https://github.com/metasfresh/metasfresh-webui-api/issues/758) Picking Tray Clearing: process to take out an HU and add it to existing HU
    * New Functionality in Picking tray Clearing, having a new Action that takes out Handling Units from Picking Slot and adds to existing HU.
  * [#760](https://github.com/metasfresh/metasfresh-webui-api/issues/760) Picking Tray Clearing: process to take out an HU and add it to new HU
    * New Functionality in Picking tray Clearing, having a new Action that takes out Handling Units from Picking Slot and adds to a new HU.
  * [#763](https://github.com/metasfresh/metasfresh-webui-api/issues/763) Picking Tray Clearing: packing HUs: Add to Transportation Order, Ship and Invoice action
    * New Functionality in Picking tray Clearing, having a new Action that adds Handling Units to Transportation order and automatically creates Shipments and Invoiced for the HU.
  * [#764](https://github.com/metasfresh/metasfresh-webui-api/issues/764) Receipt candidates: HU editor: cannot call Transform for a CU
    * Improvement of Receipt Candidates, now allowing to transform Customer Units.
  * [#768](https://github.com/metasfresh/metasfresh-webui-api/issues/768) Picking Tray Clearing: packing HUs: generate shipper's package label
    * New Functionality in Picking tray Clearing, generating a shipper package Label for package HU.
  * [#769](https://github.com/metasfresh/metasfresh-webui-api/issues/769) New context variable for isWebUI
    * Improvement of Display logic in WebUI now allowing to use "isWebUI" flag in display logic.

* metasfresh-webui-frontend
  * [#1465](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1465) frontend: Processes: when calling a process frontend shall provide which are the selected rows in the left/right view
    * Enhanced funcktionality in WebUI selectedIDs. Now it's possible to select lines in splitted views so that actions/ process receive all selected Ids.
  * [#1475](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1475) Hover showing Element Content for flags wrongly
    * Improvement of the tablecell tooltips in Grid Views. Not not showing a tooltip for checkboxes and switches anymore.

#Fixes
* metasfresh-app
  * [#2822](https://github.com/metasfresh/metasfresh/issues/2822) Empty Country in Price List not working
    * Fixes the price retrieval Logic for Pricelists with empty country.
  * [#3225](https://github.com/metasfresh/metasfresh/issues/3225) Error on unclosing a "manually" created PPOrder
    * Fixes a Bug that appeared when trying to manually unclose a Manufacturing Order.
  * [#3227](https://github.com/metasfresh/metasfresh/issues/3227) Attachment related perf problem in swing client
    * Fixes a Performance Issue that was related to missing indices in Attachment.
  * [#3233](https://github.com/metasfresh/metasfresh/issues/3233) WebUI: Role permission constraints missing in Subtabs
    * Fixes an issue that allowed to add duplicate permissions in WebUI Role Window.
  * [#3281](https://github.com/metasfresh/metasfresh/issues/3281) C_Tax.ValidFrom bug
    * Fix for the check of valid from date in Tax ID retrieval.

* metasfresh-webui-api
  * [#741](https://github.com/metasfresh/metasfresh-webui-api/issues/741) WebUI window C_Printing_Queue broken
    * Fixes the Prining Queue Window in WebUI.
  * [#757](https://github.com/metasfresh/metasfresh-webui-api/issues/757) elasticsearch shall use slf4j instead of log4j
    * Switches the logger for elasticsearch to slf4j.


# metasfresh 5.39 (2017-50)

**release for week 2017-50 / 2017-51**

## Features
* metasfresh-app
  * [#2338](https://github.com/metasfresh/metasfresh/issues/2338) Relation Type: fact_accounts
    * New Relation Type that allows to show the Fact accounts quickly for a given document.
  * [#2340](https://github.com/metasfresh/metasfresh/issues/2340) Relation Type: changelog
    * New Relation Type that allows to show the Changelog quickly for a given record.
  * [#2646](https://github.com/metasfresh/metasfresh/issues/2646) MRP Product Info Just in Time
    * New Material Cockpit functionality that provides fast infomormation about material availability triggered by material transactions and reservations/ ordered quantities.
  * [#2715](https://github.com/metasfresh/metasfresh/issues/2715) Create webui window for C_Invoice_Candidate_Agg
    * New Window in WebUI for the maintenance of Invoice Candidate Aggregation rules.
  * [#3058](https://github.com/metasfresh/metasfresh/issues/3058) TableRecordId relation types to support Prefix_AD_Table_ID and Prefix_Record_ID
    * New Functionality that supports the usage of Prefix_Table_ID and Prefix_Record_ID in relations.
  * [#3079](https://github.com/metasfresh/metasfresh/issues/3079) Warehouse Picking Group
    * New Functionality that allows to define a group of Warehouses that shall be used as Picking Warehouses.
  * [#3105](https://github.com/metasfresh/metasfresh/issues/3105) Improve Product Import process
    * Improvement of Import action for Product data.
  * [#3107](https://github.com/metasfresh/metasfresh/issues/3107) Warehouse picking group window
    * New Warehouse Picking Group window in WebUi, allowing the user to define groups of Warehouses that shall be considered as warehouses to be picking sources.
  * [#3120](https://github.com/metasfresh/metasfresh/issues/3120) Add Netsum to Customer Invoice Document Grid View
    * Improvement of Customer Invoice Grid View, adding Net Sum.
  * [#3122](https://github.com/metasfresh/metasfresh/issues/3122) Add Netsum to Vendor Invoice Document Grid View
    * Improvement of Vendor Invoice Grid View, adding Net Sum.
  * [#3124](https://github.com/metasfresh/metasfresh/issues/3124) Picking terminal: pimp Picking Configuration window
    * New Window for Picking Profile Configuration. Allowing to switch the used Picking Profile between Product and Sales Order oriented Picking.
  * [#3134](https://github.com/metasfresh/metasfresh/issues/3134) Purchase disposition window shall be readonly
    * Setting Fields Readonly to Puchase Disposition Window.
  * [#3137](https://github.com/metasfresh/metasfresh/issues/3137) Clean C_PaySelection
    * Improvement of the Pay selection window. Removing legacy functionality.
  * [#3140](https://github.com/metasfresh/metasfresh/issues/3140) WebUI: Translate Picking Configuration window to en_US
    * Improvement of the Translation to en_US of Picking Configuration Window.
  * [#3149](https://github.com/metasfresh/metasfresh/issues/3149) Automatic Discount for Group Products
    * New Functionality added to the Group Product lines in sales order, allowing to define and use a discount schema for the group products functionality.
  * [#3150](https://github.com/metasfresh/metasfresh/issues/3150) Always keep Terms & Conditions equal per Compensation Group
    * Improved Compensation group feature in sales orderlines, allowing to keep the Terms and conditions equal in a compensation group.
  * [#3164](https://github.com/metasfresh/metasfresh/issues/3164) WebUI: Import BPartner Translations
    * Translating the Import Business Partner window to en_US and improving advanced edit view.
  * [#3166](https://github.com/metasfresh/metasfresh/issues/3166) Businesspartner Window shows Fields that should be in Advanced edit mode
    * Improved Business Partner window, moved Fields to advanced edit that are not mainly in user focus.
  * [#3171](https://github.com/metasfresh/metasfresh/issues/3171) Add view to keep track of unprocessed async work packages
    * New view to keep tracking the qty of unprocessed
  * [#3176](https://github.com/metasfresh/metasfresh/issues/3176) Translate Sales Order Actions
    * Improvement of translations in sales order action menu.
  * [#3193](https://github.com/metasfresh/metasfresh/issues/3193) Improve Window for manual OnHand Qty in WebUI
    * Improved Window for the recording of manual on Hand Quanitities,
  * [#3211](https://github.com/metasfresh/metasfresh/issues/3211) Picking Tray Clearing: right side view shall display only the HUs for current BP/Location
    * Improved included View in Picking

* metasfresh-webui-api
  * [#706](https://github.com/metasfresh/metasfresh-webui-api/issues/706) Port current MRP Product Info Window to the WebUI
    * Portation of the Swing MRP Product info functionality to WebUI, there now called Material Cockpit.
  * [#711](https://github.com/metasfresh/metasfresh-webui-api/issues/711) Picking terminal: additional shall support grouping lines by order
    * Introducing new Picking Terminal Workflow Pattern. Now allowing to group the Picking Lines by Sales Order instead of Product.
  * [#714](https://github.com/metasfresh/metasfresh-webui-api/issues/714) Login authenticate: provide an unique key for each JSONLoginRole
    * Improvement of JSON endpoint for the Login authentication. Now having a unique key for each Login Roles.
  * [#716](https://github.com/metasfresh/metasfresh-webui-api/issues/716) Picking terminal: scan picking slot by barcode
    * New Feature in Picking Terminal allowing to scan and identify the Picking Tray.
  * [#717](https://github.com/metasfresh/metasfresh-webui-api/issues/717) Picking terminal: Open HUs to pick shall display Best Before date and Locator
    * Adding the Open HUs included view allowing to see the best before date and locator.
  * [#721](https://github.com/metasfresh/metasfresh-webui-api/issues/721) Picking terminal: HUs to pick: scan/filter by Locator
    * New Filter Feature in Picking Terminla window, allowing to scan Locators and filter by them in Handlign Unit selection view.
  * [#723](https://github.com/metasfresh/metasfresh-webui-api/issues/723) Picking terminal: HUs to pick: Pick CUs process
    * New Action in Picking terminal allowing to pick CU by quantity.
  * [#727](https://github.com/metasfresh/metasfresh-webui-api/issues/727) Allow search of Subproducer
    * Improved Subproducer Search in Attributes view of Material Receipt Handling Unit Editor. Now the Subproducer Attribute is a Lookup widget.
  * [#731](https://github.com/metasfresh/metasfresh-webui-api/issues/731) Introduce process parameters callout minimal functionality
    * New functionality that allows to trigger callouts for action parms in webui.
  * [#746](https://github.com/metasfresh/metasfresh-webui-api/issues/746) Picking Tray Clearing: Scan picking slot barcode filter
    * New Functionality in Picking Tray clearing, allowing to filter the picking slot be scanning a barcode.
  * [#747](https://github.com/metasfresh/metasfresh-webui-api/issues/747) Picking Tray Clearing: filter picking slots by Partner
    * New Functionality in Picking Tray clearing, allowing to filter by Business Partner.
  * [#751](https://github.com/metasfresh/metasfresh-webui-api/issues/751) Picking Tray Clearing: packing HUs: Add to Transportation Order action
    * New Action in Picking Tray Window allowing to add Handling Units to a Transporation Order.
  * [#755](https://github.com/metasfresh/metasfresh-webui-api/issues/755) Processes: support for parentViewSelectedIds and childViewSelectedIds
    * New Functionality for record selections in WebUI, now allowing the frontend to provide selected Parent and Child Row Indentifier to be uses in Actions/ Processes.

* metasfresh-webui-frontend
  * [#1206](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1206) Use automatic code style enforcement
    * Automatically transforming code to match code styling rules.
  * [#1375](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1375) Navigation Menu arrow down directly scrolls menu
    * Improving the behavior of scrolling in Navigation Menu with a lot of bookmarks.
  * [#1407](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1407) Process result: open included view: handle profileId property
    * New functionality that allows to open a window and view after a process/ an action is done.
  * [#1414](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1414) View: don't allow user to sort by a given column if layout says so
    * Disabling the sorting feature for columns which are marked as not-sortable.
  * [#1425](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1425) Eslint: increase line max-len from 80 to 120.
    * Internal House Keeping issue that shall improve the Code Quality.
  * [#1433](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1433) Support Lookup view attributes
    * Adding Lookup widget support for the Attributes Panel in WebUI. Now it's able to add autocomplete and search Functionality there.
  * [#1436](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1436) Remove legacy docs
    * Removing legacy aoutdated documents from WebUI Frontend docs folder.

#Fixes
* metasfresh-app
  * [#3110](https://github.com/metasfresh/metasfresh/issues/3110) Request All does not show records
    * Fix for the Request window, now allowing to show all records again without using the filter button.
  * [#3126](https://github.com/metasfresh/metasfresh/issues/3126) Solve issues around standalone report / jasper service
    * Fixes issues that occurred in the new Standalone Jasper report service.
  * [#3138](https://github.com/metasfresh/metasfresh/issues/3138) Import BPartner window: cannot see data
    * Fix for the Import Businesspartner window and functionality. now showing the imported data again.
  * [#3139](https://github.com/metasfresh/metasfresh/issues/3139) Cannot edit System Administrator role (AD_Role_ID=0)
    * Fix in Role Management, now allowed to edit the System Admin Role again.
  * [#3147](https://github.com/metasfresh/metasfresh/issues/3147) Errors with C_BPartner SQL columns
    * Fixes an error which appeared with the new SQL columns in Business Partner window, for systems with a lot of Business Partner records.
  * [#3151](https://github.com/metasfresh/metasfresh/issues/3151) ZoomTo-Performance Issues
    * Improving the Performance of different Zoom-To Releations.
  * [#3159](https://github.com/metasfresh/metasfresh/issues/3159) Translation Tab content missing for Product_Trl
    * Fix for the Product Translation Tab. Now showing the Translations again.
  * [#3160](https://github.com/metasfresh/metasfresh/issues/3160) C_Printing_Queue_ReEnqueue with IsSelected doesn't work
    * Fix for the Reqnqueue action in the Printing Queue Window.
  * [#3169](https://github.com/metasfresh/metasfresh/issues/3169) Updating PMM_Week availability trend fails
    * Fix fir the Procurement Candidates for week availability Trend.
  * [#3174](https://github.com/metasfresh/metasfresh/issues/3174) Material Receipt Candidates not generated when using Purchase Order from Sales Order action
    * Fix for the new Purchase Order from Sales Order action, now also creating Material Receipt candidates for these kind of Purchase Orders.
  * [#3183](https://github.com/metasfresh/metasfresh/issues/3183) Jenkins misinterprets positive downstream build result
    * Solving an internal housekeepinmg issue in Build System.
  * [#3202](https://github.com/metasfresh/metasfresh/issues/3202) Error in Paymentjournal Process/ Report in Payselection
    * Fixes en error in Payselection when starting the Paymentjournal.
  * [#3206](https://github.com/metasfresh/metasfresh/issues/3206) Relation sales order - flatrate term is missing
    * Fixes a missing relation for flatrate term in sales order
  * [#3207](https://github.com/metasfresh/metasfresh/issues/3207) Cannot save an attribute in orderline
    * Fix for the recording of attributes in orderline.
  * [#3208](https://github.com/metasfresh/metasfresh/issues/3208) Console error when copying MDocType
    * Fixes an error in the clone action of Document Types window.

* metasfresh-webui-api
  * [#713](https://github.com/metasfresh/metasfresh-webui-api/issues/713) Error creating bean with name 'scopedTarget.internalUserSessionData'
    * Fixes an issue that occured sometimes when switching the language of the user in WebUI.
  * [#722](https://github.com/metasfresh/metasfresh-webui-api/issues/722) Picking terminal: Picking slot rows shall NOT have duplicate IDs
    * Solving the issue of having duplicated IDs in Picking Slot Rows.

* metasfresh-webui-frontend
  * [#1361](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1361) View field editor: make sure the field value is sent to backend before the view is deleted
    * Fix for the patching of adjusted values in modal overlay action views. Now patching the field value before closing the modal overlay.
  * [#1383](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1383) cannot completely delete a numeric field in grid view
    * Fixes the new Edit mode in main grid views, now allowing to delete recorded numeric field conten completely.
  * [#1393](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1393) View attributes: consider row's supportAttributes property before querying for attributes
    * Fixes the querying of Attributes, now only done if the API demands that.
  * [#1396](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1396) View field editor: Patch request isn't sent after hit enter
    * A Patch Request is now sent again, after hitting enter.
  * [#1404](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1404) login form selects 2 items from roles dropdown
    * Fixes the login form in WebUI, now only selecting 1 role item.
  * [#1406](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1406) Can not enter "-" into any text field
    * Allowing to enter "-" character into text fields in WebUI.
  * [#1415](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1415) View: singleOverlayField filters are half broken
    * Fix for the Overlay when using Barcode Filter.
  * [#1421](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1421) Drop down lists remain displayed when using tab several times
    * Closing opened dropdown lists again when navigating through fields in webUI and the field is losing focus.
  * [#1426](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1426) Connection lost displayed when unchecking a checkbox in Filter
    * Fix for Filter checkboxes. Avoiding connection lost issue.
  * [#1427](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1427) d3 errors on dashboard
    * Fixes D3JS Issues in metasfresh Dashboard for calculatesd negative values.
  * [#1435](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1435) Problem regarding multiple filters
    * Fixing an issue with multiple Filters interfering each other in main and included views.
  * [#1437](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1437) Language bug: The language in Settings is not respected
    * Fixes an issue in User Configuration about language Settings that were not respected.
  * [#1440](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1440) Dropdown in Grid view needs two-time click again
    * Fixes the dropdown selection. Now selections are able to be done with first cklick.
  * [#1444](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1444) Attributes are empty in material receipt candidate
    * Fix for material receipt candidates that were wrongly created with empty attributes.
  * [#1448](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1448) jenkins: fix current lint issues
    * Internal Housekeeping issue fixed according to lint.
  * [#1451](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1451) Batch entry: cursor jumps directly to HU
    * Fix for the Batch entry focus after opening via alt+q or alt++. Now focussing on Product field again.
  * [#1455](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1455) Error when updating an attribute multiple times
    * Fixes an error that ocurred when changing an attribute multiple times.

# metasfresh 5.38 (2017-49)

**release for week 2017-49**

## Features
* metasfresh-app
  * [#937](https://github.com/metasfresh/metasfresh/issues/937) Increase Performance by disabling statistics info
    * Internal Housekeeping Issue, switching off the statistics creation in System Table.
  * [#1767](https://github.com/metasfresh/metasfresh/issues/1767) New Window Board in WebUI
    * New Window for Board Configuration in WebUI, allowing to configure new Kanban Boards and Card Layouts.
  * [#1809](https://github.com/metasfresh/metasfresh/issues/1809) New User Query Window on WebUI
    * New Window in WebUI that allows the maintenance of User specific Filter criteria for Windows & Tabs.
  * [#2177](https://github.com/metasfresh/metasfresh/issues/2177) New Window for Attribute Search in WebUI
    * New Window for Attribute Search in WebUI.
  * [#2868](https://github.com/metasfresh/metasfresh/issues/2868) Make BOMValidate process to work correctly in webui
    * Improvement of the BOM Validation Action in WebUI.
  * [#2956](https://github.com/metasfresh/metasfresh/issues/2956) Drop allowconsolidateinvoice from database
    * Removing column allowconsolidateinvoice thats not needed in Business Partner anymore.
  * [#3027](https://github.com/metasfresh/metasfresh/issues/3027) WebUI: New Window for Import BPartner
    * New Window in WebUI for Import Businesspartner.
  * [#3040](https://github.com/metasfresh/metasfresh/issues/3040) Implement Clone/ Copy for Forecast Window WebUI
    * New Copy Action for Forecast records.
  * [#3041](https://github.com/metasfresh/metasfresh/issues/3041) Forecast Window copy Field content from header to lines when creation
    * Improvement of Forecast creation, now allowing the user to record data on header and automatically copy that to line level.
  * [#3042](https://github.com/metasfresh/metasfresh/issues/3042) C_Bpartner: new Additional fields
    * New pharmacy specific Fields in Business Partner Window.
  * [#3054](https://github.com/metasfresh/metasfresh/issues/3054) Purchase candidates: document references
    * Improvement of navigation in Purchase candidates, now having document references to sales and purchase order.
  * [#3064](https://github.com/metasfresh/metasfresh/issues/3064) Multiple PP Order generation for a materialdispo line
    * Improvement od the generation of Manufacturing Orders via Materialdispo lines.
  * [#3072](https://github.com/metasfresh/metasfresh/issues/3072) de_metas_purchasecandidate schema containing some diagnosis views
    * Adding diagnosis views to purchase candidates DB Schema.
  * [#3086](https://github.com/metasfresh/metasfresh/issues/3086) HU planned after Purchase Order, destroyed after material receipt
    * Improvement of Handling Unit behavior after Purchase Order before Material Receipt. Now not preparing Handling Units anymore.
  * [#3094](https://github.com/metasfresh/metasfresh/issues/3094) Activity ID in Purchase Orderline Grid View
    * New field in purchase orderline, allowing to see and maintain the activity in grid view.
  * [#3095](https://github.com/metasfresh/metasfresh/issues/3095) WebUI: new Window for AD_Session in WebUI
    * New window for session audit in webui, allowing to view session and login information.
  * [#3117](https://github.com/metasfresh/metasfresh/issues/3117) Disable MStorage.add
    * Switching off Legacy M_Storage.

* metasfresh-webui-api
  * [#699](https://github.com/metasfresh/metasfresh-webui-api/issues/699) Implement Batch entry in Forecast Window
    * New Batch entry functionality in Forecast Window, allowing the rcording of Forecasts much faster.
  * [#705](https://github.com/metasfresh/metasfresh-webui-api/issues/705) Backend Logic for new Date Filter Widget
    * Implementation of the Backend Logic for the new Date Filter Widget.

* metasfresh-webui-frontend
  * [#1354](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1354) DateTimeRangePicker default Time from/to in Filter and Process Parms
    * Improving the DateRange Picker Filter widget. Now seetting the initial date and time to the whole day of today.
  * [#1357](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1357) Don't use the legacy view attributes API
    * Frontend Improvement, now not using the lagacy API view for attributes anymore.
  * [#1360](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1360) View field editor: override viewEditorRenderMode on row level
    * New functionality that allows to overwrite the editor render mode on row level.
  * [#1387](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1387) New widget for date filtering
    * New Filter widget, that allows to navigate date & date/time filter settings in lower or higher steps.

## Fixes
* metasfresh-app
  * [#2946](https://github.com/metasfresh/metasfresh/issues/2946) Product: New Field Endcustomer productname and translation
    * Adding new fields for endcustomer productname for product labels and translation.
  * [#2977](https://github.com/metasfresh/metasfresh/issues/2977) Tab Org Access not working in WebUI because of missing primary key
    * Adding a primary Key to Orgaccess Table, allowing to create records in User Window subtab.
  * [#3065](https://github.com/metasfresh/metasfresh/issues/3065) PP Order not shown in materialdispo although automatically generated
    * Fix for PP Orders, now shown in materialdispo too when automatically generated.
  * [#3087](https://github.com/metasfresh/metasfresh/issues/3087) Get rid of group columns in new window for procurement stock count
    * Adjusting the window Purchase Stock Control and adding Translation for en_US.
  * [#3060](https://github.com/metasfresh/metasfresh/issues/3060) TypedSqlQuery Bugs because aggregateList method ignores order
    * Now allowing to use OrderBy in TypedSQLQuery.
  * [#3073](https://github.com/metasfresh/metasfresh/issues/3073) Error: Could not find or load main class ${argLine}
    * Internal housekeeping issue, fixing the local build of metasfresh from command line.
  * [#3088](https://github.com/metasfresh/metasfresh/issues/3088) Transform in material receipt left destroyed HU behind
    * Fix for the Transformation action in handling unit Editor, now undisplaying the destroyed HU after Transformation.
  * [#3089](https://github.com/metasfresh/metasfresh/issues/3089) Not able to start report service with ReportServiceMain.launch
    * Internal Housekeeping Task allowing to start the report service now with ReportServiceMain.launch.
  * [#3100](https://github.com/metasfresh/metasfresh/issues/3100) Move UI Element Action broken
    * Fix for the UI Element (Element, Group) Movement actions.
  * [#3128](https://github.com/metasfresh/metasfresh/issues/3128) Qtydelivered in PMM_Balance gets doubled
    * Fix for the Procurement disposition, now not doubleing the PMM Balance anymore.

* metasfresh-webui-frontend
  * [#1367](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1367) view attributes shall be refreshed when the current selected row is refreshed
    * Fixed the Frontend refresh of the attributes view.
  * [#1405](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1405) Lookup dropdown not Closing after selection w/ [enter/CR]
    * Fix for dropdown lists in Lookupds when confirming a selection with enter/ cr.

# metasfresh 5.37 (2017-48)

**release for week 2017-48**

## Features
* metasfresh-app
  * [#2944](https://github.com/metasfresh/metasfresh/issues/2944) Allow more than 1 PostFinanceUserNo per Account
    * Extended the swiss ESR Postfinance participant No functionality. Now allowing to have more than 1 Postfinance participant No per bank sccount.
  * [#2945](https://github.com/metasfresh/metasfresh/issues/2945) Adjustment in pain001
    * Adjustment of the Sepa pain format for bank interchanges transactions in switzerland.
  * [#2976](https://github.com/metasfresh/metasfresh/issues/2976) Translate Processname to de_DE in Procurement Candidates
    * Improved Translation and Naming in Procurement Planning window.
  * [#2982](https://github.com/metasfresh/metasfresh/issues/2982) Filter for Shipment Documentstatus is missing in WebUI
    * New filter criteria "documentstatus" added to Shipment window.
  * [#2987](https://github.com/metasfresh/metasfresh/issues/2987) Adjust the Naming of Locators
    * Improvement of the Locator Naming.
  * [#2997](https://github.com/metasfresh/metasfresh/issues/2997) Partner Import: Support importing the Org via value
    * Improved Business Partner Import, allowing to import the Organisation via value instead of name.
  * [#3002](https://github.com/metasfresh/metasfresh/issues/3002) WebUI: New Window for HU Transaction
    * New window for Handling Unit transaction in WebUI.
  * [#3003](https://github.com/metasfresh/metasfresh/issues/3003) WebUI: Adjustments on HU Trace Window
    * Improvement in Handling Unit Trace Window, rearranging fields, new filter criteria and improved translations for en_US, de_DE.
  * [#3005](https://github.com/metasfresh/metasfresh/issues/3005) Manufacturing Order add Filter for Planstatus
    * Additional, new filter criteria in manufacturing order.
  * [#3010](https://github.com/metasfresh/metasfresh/issues/3010) Inherit c_order_id and orderline_id from flatrate term to invoice candidate
    * New referenced document in flatrate terms, now allowing to jump directly to referenced invoice candidates directly from there.
  * [#3032](https://github.com/metasfresh/metasfresh/issues/3032) SSCC Product description Field cut off
    * Improvement of the SSCC Label, now allowing to have Product Names that extend the width into more than 1 line.
  * [#3037](https://github.com/metasfresh/metasfresh/issues/3037) WebUI: New Window for Purchase Order Candidates
    * New Window in WebUI for the transactional data of Purchase Candidates.
  * [#3039](https://github.com/metasfresh/metasfresh/issues/3039) Forecast Window adjustments
    * Improvement and extension of the Forecast Window in WebUI, adding new Fields Businesspartner, Date Promised, Warehouse to Forecast Header.
  * [#3047](https://github.com/metasfresh/metasfresh/issues/3047) WebUI: New Window for Quality Note
    * New Window for Quality Note in WebUI, allowing to mmaintain the available Quality Notes and Performace Types for Material Receipt.
  * [#3048](https://github.com/metasfresh/metasfresh/issues/3048) WebUI: Date Filter missing in Material Receipt Dispo
    * New Date Filter added to material Receipt Dispo.
  * [#3055](https://github.com/metasfresh/metasfresh/issues/3055) Purchase candidates: notify user when the purchase order was generated
    * New user notification as soon as purchase candidates were created for the user.
  * [#3056](https://github.com/metasfresh/metasfresh/issues/3056) Purchase candidates: implement locking mechanism
    * Locking mechanism implemented for purchase candidates window.
  * [#3057](https://github.com/metasfresh/metasfresh/issues/3057) Display order, forecast or shipmentschedule in material dispo main window
    * New fields in material disposition, now showing the Order, Forecast and Shipmentschedule information.
  * [#3061](https://github.com/metasfresh/metasfresh/issues/3061) Adjust OrderBy in Materialdispo
    * Improvement of Ordering in Materiadispo.
  * [#3066](https://github.com/metasfresh/metasfresh/issues/3066) WebUI: Adapt Manufacturing Order window to current guidelines
    * Improvement of the Manufacturing Order Window, adapted to current WebUI Guidelines and added Translations.
  * [#3082](https://github.com/metasfresh/metasfresh/issues/3082) New Window for manual OnHand Qty in WebUI
    * New Window for the maintenance of manual OnHand Qty in metasfresh. This feature is used in fresh produce companies with fast moving consumer goods and raw materials.

* metasfresh-webui-api
  * [#611](https://github.com/metasfresh/metasfresh-webui-api/issues/611) Destroyed HUs in HU Editor
    * Improving the filtering of Handling Unit Window now not showing destroyed HU anymore.
  * [#683](https://github.com/metasfresh/metasfresh-webui-api/issues/683) Source HU Actions shall only be "Drop Source HU"
    * Hiding Quickactions in Handling Unit Editor. Now only showing the needed Quickactions for Source Handling Units.
  * [#689](https://github.com/metasfresh/metasfresh-webui-api/issues/689) Picking processed compress to top-level HU
    * New compression visualisation in Pickign window, now only showing top level HU as soon as processed.
  * [#695](https://github.com/metasfresh/metasfresh-webui-api/issues/695) Picking terminal: implement dynamic picking slot allocation/release
    * New Functionality in Picking window, now allowing the dynamic allocation/ clearing of picking slots.

* metasfresh-webui-frontend
  * [#1347](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1347) Dropdown in Grid view needs two-time click if value in list is more than one
    * Fixes the need of 2 times click in included grid view dropdown widgets.
  * [#1363](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1363) Jenkins: run npm test on each build
    * Internal housekeeping issue, improving build system.
  * [#1380](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1380) Shortcut for new document  
    * Adjusted shortcut for the creation of new documents.

## Fixes
* metasfresh-app
  * [#2958](https://github.com/metasfresh/metasfresh/issues/2958) 2nd Discount Break is ignored
    * Bugfix for the 2nd Discount Break that is not ignored anymore.
  * [#3023](https://github.com/metasfresh/metasfresh/issues/3023) ad_org is ignored when doing quick order entry
    * Fixes the Filter criteria for products in batch entry, now only showing the products of the sales order org.
  * [#3034](https://github.com/metasfresh/metasfresh/issues/3034) Solve material dispo regressions
    * Fixes various issue in the Material Disposition Prototype.
  * [#3043](https://github.com/metasfresh/metasfresh/issues/3043) A contract shall not be created when completing a quotation
    * Fixes the contract creation, now not generating a contract when completing a sales order quotation.

* metasfresh-webui-api
  * [#669](https://github.com/metasfresh/metasfresh-webui-api/issues/669) CU-TU's built name and description is wrong
    * Fix for the CU-TU name and description generation when adjusting UOM.
  * [#671](https://github.com/metasfresh/metasfresh-webui-api/issues/671) T_WEBUI_ViewSelection[Line] shall be truncated and not deleted
    * Internal Housekeeping fix for the temporary WebUI Selection Tables, now using truncate instead of delete.
  * [#679](https://github.com/metasfresh/metasfresh-webui-api/issues/679) HU taken out in Picking Tray Clearing still displayed in the Picking Slot in Picking Terminal
    * Fixes the removing of Handling Units in Picking Try Clearing.
  * [#692](https://github.com/metasfresh/metasfresh-webui-api/issues/692) Error in Quickentry Sales Order when canceling input
    * Fixes a Bug in the Quickentry workflow when canceling.
  * [#694](https://github.com/metasfresh/metasfresh-webui-api/issues/694) Purchase view: set modal title as process caption
    * Fix for the modal overview for processes/ actions. now showing process caption as modal title.
  * [#697](https://github.com/metasfresh/metasfresh-webui-api/issues/697) New BPartner is not available as SubProducerBPartner in MaterialReceipt BPartner
    * Improving the time from recording until a Subproducer is available in Material Receipt Subproducer attribute list.
  * [#700](https://github.com/metasfresh/metasfresh-webui-api/issues/700) Filter error after static filter set
    * Fixes the static Filter visibility. Now only showing 1 active static Filter.
  * [#701](https://github.com/metasfresh/metasfresh-webui-api/issues/701) WebUI server is losing session context
    * Fix for the session context after request to server.

* metasfresh-webui-frontend
  * [#1293](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1293) Cannot scroll down in a filter with lots of parameters
    * Now also possible to scroll down long filter lists.
  * [#1345](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1345) Invalid date was used on dateTime tablecell
    * Bugfix for DateTiem tablecells, now presenting the content properly.
  * [#1352](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1352) Cannot clear dropdown fields
    * Fix for Dropdown Fields, now allowing to select empty values again.
  * [#1358](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1358) View field editor: number field is behaving weird when using keyboard
    * Improvement of the behavior of number fields when using keyboard.
  * [#1365](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1365) Error with alt+space for expand
    * Fixes the keyboard Hotkey for expand in Document Windows.
  * [#1366](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1366) Error in Quickentry dropdown List
    * Fixes the Batch Entry result list, now generating the results properly.
  * [#1374](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1374) Keyboard alt+u in Sales Order
    * Fixes the Document Action Shortcut alt+u for complete action in all Document Windows.
  * [#1376](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1376) Navigation Menu Favorite stars wrong
    * Fix for the Bookmark Functionality in WebUI Window and Navigation Sitemap.
  * [#1389](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1389) Alt+a in main Grid View opens Advanced edit mode
    * Fix for the shortcut of advanced edit in main grid view, that blocked the select-all shortcut from being tiggered.

# metasfresh 5.36 (2017-47)

**release for week 2017-47**

## Features
* metasfresh-app
  * [#1964](https://github.com/metasfresh/metasfresh/issues/1964) ESR Import w/o Invoice reference (w/ reversed Invoice)
    * New Action and workflow in ESR Import now creating the Payment first and providing an action if allocation cannot be done.
  * [#2871](https://github.com/metasfresh/metasfresh/issues/2871) Window Design Webui: Improve windows with Posted field
    * Adding missing default Values for Posted columns in various Table Definitions.
  * [#2872](https://github.com/metasfresh/metasfresh/issues/2872) Window Design Webui : Improve Distribution Order window
    * Improved Layout and Translations for the Distribution Order Window in WebUI.
  * [#2908](https://github.com/metasfresh/metasfresh/issues/2908) Provide Doc Action voiding for contract period
    * New document action in contract period allowing to void now.
  * [#2911](https://github.com/metasfresh/metasfresh/issues/2911) Create Action for changing price and qty for a contract
    * New action that allows to adjust the price and qty for an already created contract.
  * [#2932](https://github.com/metasfresh/metasfresh/issues/2932) Standalone report / jasper service
    * Moving the Jasper Handling to a standalone Jasperreport service.
  * [#2937](https://github.com/metasfresh/metasfresh/issues/2937) Jasper Reports: New Purchase Order, Inout and Invoice Layout
    * Additional new Layouts for Document Jasperreports on purchase side.
  * [#2940](https://github.com/metasfresh/metasfresh/issues/2940) Shipment Date = Promised Date
    * Adjustment of the Inout Generate Date when creating from shipment candidates. Now using the promised date as initial shipment date.
  * [#2942](https://github.com/metasfresh/metasfresh/issues/2942) Fix the message for PrintJob_Done
    * Adjusted message for finished Print Jobs.
  * [#2959](https://github.com/metasfresh/metasfresh/issues/2959) Default WebUI Window for Distribution Orderlines Handling
    * New Window for Distribution Orderlines that allows better Distrbution handling in WebUI.
  * [#2963](https://github.com/metasfresh/metasfresh/issues/2963) Flatrate: use datepromised instead of order data for master and startdate
    * Adjustment in Flatrate contract, switching Master Order and Startdate to datepromised.
  * [#2969](https://github.com/metasfresh/metasfresh/issues/2969) Add SQL Column M_Warehouse to Distribution Orderline
    * New Field Warehouse and Filter in Distribution Editor.
  * [#2975](https://github.com/metasfresh/metasfresh/issues/2975) Sales invoice jasper: group products by product category
    * Adjustment of sales invoice jasperreport, now allowing the grouping by products.
  * [#2978](https://github.com/metasfresh/metasfresh/issues/2978) New Window in WebUI for EDI DESADV
    * New Window for EDI DESADV records in WebUI.
  * [#2980](https://github.com/metasfresh/metasfresh/issues/2980) Date Filter missing in Order Control Window
    * New Filter in Order Control Window for Date, CreatedBy and more.
  * [#2981](https://github.com/metasfresh/metasfresh/issues/2981) Action translation in Invoice Candidates Window
    * Improved Translation for Action Menu in invoice candidate window.
  * [#2985](https://github.com/metasfresh/metasfresh/issues/2985) Refining Distribution Editor Window in WebUI
    * Improvements for the Distribution Editor Window in WebUI. Additional Translations for de_DE and en_US Language/ Locale.
  * [#2989](https://github.com/metasfresh/metasfresh/issues/2989) Filter for GAP No. is missing in Businesspartner window
    * Adding the GAP Filter to Businesspartner Window.
  * [#2992](https://github.com/metasfresh/metasfresh/issues/2992) WebUI: Window Shipment Packing Item
    * Adaption of Field Names to current WebUI Guidelines.

* metasfresh-webui-api
  * [#670](https://github.com/metasfresh/metasfresh-webui-api/issues/670) Views: backend shall specify which columns are editable and how to render the editor
    * New functionality in webui api, now specifying the editable fields in grid view to frontend.

* metasfresh-webui-frontend
  * [#1222](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1222) Keyboard Shortcut for "Select all"/ "Select all x rows"
    * New Keyboard shortcut for select all/ select all x rows with toggle functioanlity.
  * [#1335](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1335) Views: backend shall specify which columns are editable and how to render the editor (frontend)
    * Frontend implementation for the editable fields in main grid view.

## Fixes
* metasfesh-app
  * [#2965](https://github.com/metasfresh/metasfresh/issues/2965) Error for HU changing locator if status E
    * Fix for an issue that appeared when moving handling units in the warehouse and changing the locator.
  * [#2966](https://github.com/metasfresh/metasfresh/issues/2966) Error in console when creating customer return
    * Fixes an issue in customer returns.
  * [#3035](https://github.com/metasfresh/metasfresh/issues/3035) Linenet amount not updated in sales order after setting flatrate condition
    * Fix for the Linenet Amount in Sales Order after setting a flatrate condition.

* metasfresh-webui-api
  * [#677](https://github.com/metasfresh/metasfresh-webui-api/issues/677) Close all picking candidates only if the view was closed/removed by user
    * Picking candidates are only closed when the picking slot belongs to a rack system and the user closes the modal view.
  * [#678](https://github.com/metasfresh/metasfresh-webui-api/issues/678) HU that was taken out in Picking Try Clearing shall remain Picked
    * Fix for the status of Handling Units after picking tray clearing. Now the handling units remain in picked status.
  * [#681](https://github.com/metasfresh/metasfresh-webui-api/issues/681) Picking not possible for >1 orderline in Picking Terminal
    * Fix for Picking terminal, now allowing to select and pick more than 1 line at once.
  * [#696](https://github.com/metasfresh/metasfresh-webui-api/issues/696) Material receipt schedule: attributes read-only

* metasfresh-webui-frontend
  * [#1333](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1333) Process panel is closed even if /start failed
    * The modal process panel is now remaining open if an error pops up when starting the action.
  * [#1337](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1337) All values of Dropdown list are highlighted
    * Now only showing highlighted rows in Role List when the role is selected.

# metasfresh 5.35 (2017-46)

**release for week 2017-46**

## Features
* metasfresh
  * [#2407](https://github.com/metasfresh/metasfresh/issues/2407) Translate included subtabs of contract window in WebUI
    * New Translation for en_US in contract subtab view for subscription history and forecast.
  * [#2722](https://github.com/metasfresh/metasfresh/issues/2722) WebUI design: Improve window material schedule
    * Minor additional field translation for en_US added to material schedule window subtab.
  * [#2754](https://github.com/metasfresh/metasfresh/issues/2754) Cleanup function for old AD_Issue records
    * New function that allows to cleanup old and not needed issue records.
  * [#2771](https://github.com/metasfresh/metasfresh/issues/2771) Sales order jasper reports: group products by product category
    * New grouping functionality in sales order jasper, now able to group by product category.
  * [#2816](https://github.com/metasfresh/metasfresh/issues/2816) Material dispo - include storage-relevant attributes
    * Including the attributes into material dispo, now allowing the product planning to be done on detailed attributes level.
  * [#2894](https://github.com/metasfresh/metasfresh/issues/2894) Sys config to set waiting time for async
    * Improving the waiting time handling for asynch after backend start. Now able to set the time via sysconfig.
  * [#2897](https://github.com/metasfresh/metasfresh/issues/2897) Drop out old code related to confirm splitting when printing
    * Improvement in mass printing functionality and workflow. Removing the user confirmation for printing when splitting.
  * [#2913](https://github.com/metasfresh/metasfresh/issues/2913) Column-SQL needs lower-case WHERE and FROM
    * Application dictionary adjustment for AD_Ref_Table, setting lower case statements for the column SQL.
  * [#2914](https://github.com/metasfresh/metasfresh/issues/2914) Replace org.adempiere.util.collections.Predicate with java.util.function.Predicate
    * Internal housekeeping issue, replacing legacy code with java class substitute.
  * [#2919](https://github.com/metasfresh/metasfresh/issues/2919) Trx API: log a warning in case we are registering a ITrxListener which might be never executed because trx is already commit/closed
    * Internal housekeeping improvement for transaction handling.
  * [#2920](https://github.com/metasfresh/metasfresh/issues/2920) Create automated test for extending contracts.
    * Improving the Test coverage for contract extension. Creating automated Tests.
  * [#2934](https://github.com/metasfresh/metasfresh/issues/2934) Additional Locator Dimensions
    * New Locator Dimension, now allowing to additionally record a Dimension for Storage Rack.
  * [#2936](https://github.com/metasfresh/metasfresh/issues/2936) Creating new flatrate condition and transition with autoextension became very tricky
    * Usage improvement of the new Flatrate condition and transition tothether with autoextension.
  * [#2947](https://github.com/metasfresh/metasfresh/issues/2947) Adapt Window Role to WebUI Guidelines
    * Improved Layout of Role Window in WebUI, adapted now to current WebUI Guidelines.

* metasfresh-webui-api
  * [#662](https://github.com/metasfresh/metasfresh-webui-api/issues/662) Aggregate Storage for Product and Warehouse
  * [#664](https://github.com/metasfresh/metasfresh-webui-api/issues/664) Create purchase order line by line via sales order lines
    * New functionality in WebUI, allowing to create purchase orders directly from sales order lines. This feature was wanted from trade companies with very short delivery time requirements.
  * [#666](https://github.com/metasfresh/metasfresh-webui-api/issues/666) Change the JSONLookupValue format
    * Internal housekeeping, adjusting the JSON lookup value format.
  * [#668](https://github.com/metasfresh/metasfresh-webui-api/issues/668) Handling unit editor action internal usage not possible for more than 1 Page
    * Extended functionality for the Internal usage action in handling unit editor. Now possible to select all rows and start the action.
  * [#687](https://github.com/metasfresh/metasfresh-webui-api/issues/687) picking terminal: show BPartner instead of DeliveryDate

* metasfresh-webui-frontend
  * [#1199](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1199) Picking window unselect line in modal overlay
    * Improved usability/ behavior in picking window WebUI when un-selecting rows in modal overlay.
  * [#1308](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1308) Closing a document with changes shall discard those changes
    * New feature that discards uncomplete data rows in WebUI frontend.
  * [#1327](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1327) Frontend: change the JSONLookupValue format
    * Adjustment of the JSON lookup value format in WebUI frontend.

## Fixes
* metasfresh
  * [#2905](https://github.com/metasfresh/metasfresh/issues/2905) Referenced documents: sales order to invoice candidates
    * Fixing the missing reference between invoice candidates and sales order when inouts are created before invoice candidate.
  * [#2917](https://github.com/metasfresh/metasfresh/issues/2917) Allow reactivating procurement contracts
    * Allowing the document action for reactivation of procurement contracts.

* metasfresh-webui-api
  * [#649](https://github.com/metasfresh/metasfresh-webui-api/issues/649) Qty delivered and qty picked not updated correctly in shipment schedule
    * Fix for Qty fields in shipment schedule. Now field contend is updated correctly in WebUI.
  * [#660](https://github.com/metasfresh/metasfresh-webui-api/issues/660) JSONUserSessionChangesEvent not fired when currently logged in user is changed
    * Internal housekeeping issue solving the firing of session change events after chainging the login user.
  * [#665](https://github.com/metasfresh/metasfresh-webui-api/issues/665) Manufacturing order: create source HU is opening same view on right side as we have it on left side
    * Fix for the source HU View in manufacturing order.
  * [#693](https://github.com/metasfresh/metasfresh-webui-api/issues/693) Error in create purchase orders from Sales order line

* metasfresh-webui-frontend
  * [#1279](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1279) Menu actions for included row broken
    * Fix for the usage of menu actions when opening from included rows.
  * [#1283](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1283) Revise shortcut handling
    * Quick fix for the broken shortcut handling in documents.
  * [#1286](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1286) Error when maximize subtab which does not support quick input
    * Fixes a bug that occurred for included subtabs when maximizing and not supporting quick input.
  * [#1324](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1324) Process is started twice when using ctrl-u shortcut
    * Quick fix for the action handling in material receipt when using keyboard shortcuts.
  * [#1328](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1328) Date recording w/ wrong date error
    * Improved error handling and user information when entering an invalif date in date picker via keyboard.
  * [#1332](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1332) View: Edit field value is broken
    * Fix for the edit mode in main grid view.
  * [#1351](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1351) wrong backend api call for grid view editing

# metasfresh 5.34 (2017-45)

**release for week 2017-45**

## Features
* metasfresh
  * [#2113](https://github.com/metasfresh/metasfresh/issues/2113) Move verify BOM process to BOM window
    * Improvement of the BOM Verification Process. Now the BOM Verification is done via action in BOM window instead of Product window.
  * [#2273](https://github.com/metasfresh/metasfresh/issues/2273) Payment Selection as real Document
    * Transforming the Payment Selection into a real document.
  * [#2676](https://github.com/metasfresh/metasfresh/issues/2676) Flatrate: make subscription progress reflecting progress
    * Further Improvements to Subscription Progress Subtab in Business Partner window subtab, now showing the real progress of subscriptions.
  * [#2803](https://github.com/metasfresh/metasfresh/issues/2803) Set Hostkey for printing module when login in WebUI
    * Now setting the Hostkey for a User when logging in via WebUI.
  * [#2854](https://github.com/metasfresh/metasfresh/issues/2854) Save Termination Reason when terminating subscription
    * Recording the Reason when canceling a subscription.
  * [#2859](https://github.com/metasfresh/metasfresh/issues/2859) Pre-enddate termination of flatrate created compensation sales order
    * New sysconfig to control if compensation Sales Orders are created.
  * [#2862](https://github.com/metasfresh/metasfresh/issues/2862) Set MasterStartDate for a contract when creating from order
    * Improvement in Subscription Contract, now setting the Master Start Date initially when creating the Subscription from Sales Order.
  * [#2866](https://github.com/metasfresh/metasfresh/issues/2866) Make sure that 'Print All' is working in webui
    * Further improvement of the Handling of Printing when using select all in WebUI.
  * [#2874](https://github.com/metasfresh/metasfresh/issues/2874) Create special filters in printing queue
    * New Filters in Printing Queue Window in WebUI.
  * [#2878](https://github.com/metasfresh/metasfresh/issues/2878) Support for 6 digits AccountNo length in ReferenceNumber
    * Adjustment of ESR ReferenceNo Handling, extending the allowed Account No to 6 digits.
  * [#2880](https://github.com/metasfresh/metasfresh/issues/2880) Add greeting to partner quick creation from order
    * Enhancement of the Quick Businesspartner creation in Sales Order, now allowing to record the Greeting.
  * [#2881](https://github.com/metasfresh/metasfresh/issues/2881) Add a process that removes pauses and can be ran from Contract window
    * New Action in Contract Window that enables the cancellation of Subscription Pauses.
  * [#2887](https://github.com/metasfresh/metasfresh/issues/2887) Make ShipmentSchedule column reference search in C_SubscriptionProgress
    * New Filter for Shipment Schedule Line in Subscription Progress.
  * [#2895](https://github.com/metasfresh/metasfresh/issues/2895) AD_Ref_Table needs lower-case WHERE and FROM
  * [#2907](https://github.com/metasfresh/metasfresh/issues/2907) set masterenddate correctly when extending contracts

* metasfresh-webui-api
  * [#19](https://github.com/metasfresh/metasfresh-webui-api/issues/19) Cache invalidation
    * Improvement of Cache Invalidation in WebUI.
  * [#652](https://github.com/metasfresh/metasfresh-webui-api/issues/652) Implement document discardChanges endpoint
    * Endpoint Implementation for the possibility of discarding document changes. This endpoint will be used by a new cancel button in WebUI.
  * [#653](https://github.com/metasfresh/metasfresh-webui-api/issues/653) metasfresh.webui.debug.showColumnNamesForCaption shall be false by default
    * Fix in WebUI API, switching the Developer Mode off by default.
  * [#656](https://github.com/metasfresh/metasfresh-webui-api/issues/656) Address editor fields are not translated
    * Improvement of Translations in en_US, de_DE for the Address editor.

* metasfresh-webui-frontend
  * [#1294](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1294) Window actions shall respect the "disabled" flag
    * Now only enabled window actions are active in WebUI Frontend, if disabled then the action is greyed out.
  * [#1295](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1295) Processes: provide current selected tab and rows
    * Processes now provide the info about the selected Tab and rows when called in WebUI.
  * [#1303](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1303) Change the hostname from config.js.dist to localhost
    * Internal Housekeeping Issue, switching Hostnames on config.js for easier maintenance.

* other
  * [#82](https://github.com/metasfresh/metasfresh-dist-orgs/issues/82) Invoice Medium Address Layout Changes
    * Further Layout adjustments in Invoice Document.

## Fixes

* metasfresh
  * [#2876](https://github.com/metasfresh/metasfresh/issues/2876) Fix Recreate printing queue for webui
    * Fix for the Process Recreate Printing Queue, and enabling to run in WebUI.
  * [#2885](https://github.com/metasfresh/metasfresh/issues/2885) Test canceling extended contracts and fix if needed
    * Improvement of the Cancel Action when used on extended contracts.
  * [#2895](https://github.com/metasfresh/metasfresh/issues/2895) AD_Ref_Table needs lower-case WHERE and FROM
    * Fix for SQL Validation in Reference Table.
  * [#2907](https://github.com/metasfresh/metasfresh/issues/2907) Set Master Enddate correctly when extending contracts
    * Fix for the prolongation Functionality of contracts. Now setting the Master End Date correctly.

* metasfresh-webui-api
  * [#633](https://github.com/metasfresh/metasfresh-webui-api/issues/633) Materialdispo Target Warehouse checks Warehouse Routing
    * Fix for the Warehouse Routing in Material Disposition. Now only validating the Document Reference for Source Warehouse.
  * [#650](https://github.com/metasfresh/metasfresh-webui-api/issues/650) Flatrate contractstatus not refresh in single view after terminated
    * Now setting the contractstatus directly when terminating a contract.
  * [#651](https://github.com/metasfresh/metasfresh-webui-api/issues/651) Document references endpoints are not respecting role permissions
    * The Record Reference List is now respecting the Role Permissions of the user and only showing references which are allowed for the user.
  * [#654](https://github.com/metasfresh/metasfresh-webui-api/issues/654) Address country lookup not working correctly
    * Fix for the country Lookup in Location widget.

* metasfresh-webui-frontend
  * [#1312](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1312) Wrong process call
    * Improvement for Action calls in WebUI.

# metasfresh 5.33 (2017-44)

**release for week 2017-44**

## Features
* metasfresh-app
  * [#2546](https://github.com/metasfresh/metasfresh/issues/2546) Mark all Value and DocumentNo columns as IsCalculated=Y
    * Improving the Handling of Document Numbers in WebUI, now defining all Value and DocumentNo columns as auto calculated behavior.
  * [#2684](https://github.com/metasfresh/metasfresh/issues/2684) Material Dispo - map planning records with actual M_Transactions
    * New Data Structure in Material Disposition, now mapping the Planning records with real Material Transactions.
  * [#2719](https://github.com/metasfresh/metasfresh/issues/2719) Window Design Webui: Improve window Schedules
    * Improving Schedules Window in WebUI, adding adjusted Mandatory Lofic for Cron-Pattern Field and Translation for en_US.
  * [#2723](https://github.com/metasfresh/metasfresh/issues/2723) Webui Design Window: Improve window Internal Use
    * Improvement for the Internal Usage Window, adding Field Translations for Language/ Locale en_US.
  * [#2751](https://github.com/metasfresh/metasfresh/issues/2751) Add flatrate term relations from order and partner to new window contract
    * Additional references between Flatrate Terms to Order and Business Partner in the new Contract Windoiw in WebUI.
  * [#2758](https://github.com/metasfresh/metasfresh/issues/2758) remaining static element Translations
    * Improving the Translations of de_DE Language/ Locale for static elements in WebUI.
  * [#2781](https://github.com/metasfresh/metasfresh/issues/2781) Change Default Address Layout for B2C Partners for country CH
    * Adjusting the Adress Layout/ Capture Sequence for Locations in Switzerland.
  * [#2783](https://github.com/metasfresh/metasfresh/issues/2783) WebUI design: Invoice Candidates adjustments
    * Improving the Invoice Candidates Window. Adding missing Translation for Field in en_US.
  * [#2790](https://github.com/metasfresh/metasfresh/issues/2790) Show LoginasUserHostkey in webui window user
    * New Field "Login as User Hostkey" in User Window.
  * [#2800](https://github.com/metasfresh/metasfresh/issues/2800) Make de.metas.document.archive.process.ExportArchivePDF work in webui
    * Improvement of the Archove PDF Exporter, can now also be used in WebUI.
  * [#2815](https://github.com/metasfresh/metasfresh/issues/2815) New flag in Picking Tray window
    * New Field to define the Rack System in Picking Tray Window in WebUI.
  * [#2821](https://github.com/metasfresh/metasfresh/issues/2821) Window Material Dispo in WebUI
    * Improvement of Materialdispo window in WebUI, adding new Fields and Translations.
  * [#2826](https://github.com/metasfresh/metasfresh/issues/2826) WebUI: Orderlines adjustment for new Discount Groups
    * New Fields in Orderlines allowing to use and define Groups Discounts in Sales Orderlines.
  * [#2838](https://github.com/metasfresh/metasfresh/issues/2838) Linenet amount not updated in sales order after changing flatrate condition
    * New Read-Only Logic for Flatrate Terms Fields in Orderlines.
  * [#2843](https://github.com/metasfresh/metasfresh/issues/2843) WebUI: Add new Fields for SQL Columns to Material Dispo Transaction Subtab
    * Improving Transaction included Subtab in Materialdispo Window. Now showing the Shipment/ Receipt Information in there.
  * [#2847](https://github.com/metasfresh/metasfresh/issues/2847) Elasticsearch shall use slf4j instead of log4j
    * Internal Housekeeping Issue, changing the logger for elasticsearch uses. Now using slf4j.

* metasfresh-webui-api
  * [#644](https://github.com/metasfresh/metasfresh-webui-api/issues/644) Hide window actions which are not available
    * Improvement of Action Menu in WebUI, now only showing Actions that are available for the given context.
  * [#645](https://github.com/metasfresh/metasfresh-webui-api/issues/645) window/actions: introduce support for selectedTabId and selectedRowIds
    * Adding support for selected Tabs and Rows in WebUI Windows.

* metasfresh-webui-frontend
  * [#981](https://github.com/metasfresh/metasfresh-webui-frontend/issues/981) New Widget for Date Range
    * New Action Parameter Parm Widget. Now also allowing to use the DateRange Picker in Action Parms.
  * [#1296](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1296) Add storybook for isolated visual component testing
    * Initial Kickoff for automatic testing in WebUI.

* metasfresh-dist-orgs
  * [#41](https://github.com/metasfresh/metasfresh-dist-orgs/issues/41) Add medium invoice report
    * New Invoice Report.
  * [#55](https://github.com/metasfresh/metasfresh-dist-orgs/issues/55) Refactor Migration Scripts for dist orgs
    * Internal Housekeeping Issue, rearranging the migration scripts for dist orgs.
  * [#72](https://github.com/metasfresh/metasfresh-dist-orgs/issues/72) Invoice Medium ESR and other Adjustments
    * Adjustments to ESR Invoice Printformat and further adjustments.

## Fixes
* metasfresh-app
  * [#2634](https://github.com/metasfresh/metasfresh/issues/2634) Make the CalloutBankStatement webui compliant
    * Fixes the Callout BankStatement for the usage in Bankstatement Window in WebUI.
  * [#2719](https://github.com/metasfresh/metasfresh/issues/2357) Exception when using process quotation to sales order with parameter complete order
    * Fixes an exception that occured when using the action Quotation to Sales Order in WebUI.
  * [#2818](https://github.com/metasfresh/metasfresh/issues/2818) Flatrate: Have relation from Sales Order to invoice candidate
    * New Relation from Sales Order to Invoice Candidates for Flatrate Terms.
  * [#2823](https://github.com/metasfresh/metasfresh/issues/2823) Add C_Invoice_Candidate.Priority missing column
    * Adding the column Priority to Invoice Candidates.
  * [#2836](https://github.com/metasfresh/metasfresh/issues/2836) Setup wizard shall not inactivate ESR
    * Improving the Setup Wizard, now avoiding the deactivation of ESR in Setup Workflow.

* metasfresh-webui-api
  * [#643](https://github.com/metasfresh/metasfresh-webui-api/issues/643) Picking Tray Clearing requery to see the right view
    * Fixes the new Picking Tray Clearing, requerying the Window to let the user see the correct data.
  * [#647](https://github.com/metasfresh/metasfresh-webui-api/issues/647) Shipped HU is still visible in picking terminal
    * Fixes the picking Terminal, now not showing shipped HU anymore.

* metasfresh-dist-orgs
  * [#67](https://github.com/metasfresh/metasfresh-dist-orgs/issues/67) Fix Jasper Version of Invoice Medium
    * Fix for the new Jasper Invoice Report.
  * [#80](https://github.com/metasfresh/metasfresh-dist-orgs/issues/80) Invoice Medium Preview working in Jasper but not on server
    * Fix for the new Jasper Invoice Report in dis orgs.

# metasfresh 5.32 (2017-43)

**release for week 2017-43**

## Features
* metasfresh-app
  * [#472](https://github.com/metasfresh/metasfresh/issues/472) Picking from Handling Unit Editor (WebUI)
    * New Functionality that allows a flexible Picking from Handling Unit Editor.
  * [#2283](https://github.com/metasfresh/metasfresh/issues/2283) Window Design WebUI : Order Candidates Improvements
    * Improved Translations for de_DE and en_US and Layout adapted to current Design Guidelines.
  * [#2311](https://github.com/metasfresh/metasfresh/issues/2311) Customer Invoices and Invoice Candidates adjustments
    * Improved Translations for en_US, de_DE and Layout in Invoice Candidates Window and Customer Invoice.
  * [#2379](https://github.com/metasfresh/metasfresh/issues/2379) Procurement Candidates generate Purchase Order
    * New Action in Procurement Candidates Window of WebUI, allowing to create Purchase Orders from selected Procurement Candidates.
  * [#2564](https://github.com/metasfresh/metasfresh/issues/2564) Concept for entering multiple discounts for order and subscription  
    * New Feature that allows to add Discount Grouping and Discount Rows to Sales Order Lines. These Lines allow the createtion of discounts based on LineNetAmt Aggregations.
  * [#2653](https://github.com/metasfresh/metasfresh/issues/2653) Window Design Webui: Improve Businesspartner Dist-Orgs window
    * Improved Translations for en_US. New Fields added for Subscription progress Subtab.
  * [#2725](https://github.com/metasfresh/metasfresh/issues/2725) Webui Window Design: Improvements in window ESR Payment Import
    * New Field Translations added to ESR Payment Import window. Now having a completed Translation for Window, Fields and Actions for en_US Language.
  * [#2726](https://github.com/metasfresh/metasfresh/issues/2726) Set correct identifiers in the table M_ShipmentSchedule v2
    * Setting a readable and searchable Identifier for Shipment Schedule Records.
  * [#2728](https://github.com/metasfresh/metasfresh/issues/2728) Webui Design Window: Add Translations to Business Partner
    * Improved Translation for en_US Language/ Locale in Window, Fields and Actions.
  * [#2729](https://github.com/metasfresh/metasfresh/issues/2729) Window Design Webui: Add missing fields to window Tourversion
    * New Fields added to Tourversion window in WebUI. Translations for en_US improved.
  * [#2730](https://github.com/metasfresh/metasfresh/issues/2730) Window Design Webui: Improvements in window Purchase order
    * Translation improved in Purchase Order window for Language en_US.
  * [#2731](https://github.com/metasfresh/metasfresh/issues/2731) Window Design Webui: Improve window Shipment Restrictions
    * Improved Translation of en_US Language in Shipment Restrictions Window and Actions.
  * [#2732](https://github.com/metasfresh/metasfresh/issues/2732) Window Design Webui : Add description field in sales order line
    * New Field Description added to Sales Orderline Grid View and Advanced edit in WebUI.
  * [#2739](https://github.com/metasfresh/metasfresh/issues/2739) Add context into if exception happens in WorkpackageProcessorTask.processWorkpackage
    * Internal Housekeeping Feature that improves the analysis of failed Workpackage Processor Tasks.
  * [#2740](https://github.com/metasfresh/metasfresh/issues/2740) Picking slot Rack System flag
    * New Feature that enables the Configration of Picking Slots as Rack System. This enables the possbility to reopen an already closed Handling Unit in Picking Slot or restricting it.
  * [#2750](https://github.com/metasfresh/metasfresh/issues/2750) Flatrate Import: Support tax category
    * Adding Support of Tax Categories in Flatrate Contracts Import process.
  * [#2751](https://github.com/metasfresh/metasfresh/issues/2571) Report Translations to en_US: menu reports
    * Translation of default Jasper Reports to en_US.
  * [#2752](https://github.com/metasfresh/metasfresh/issues/2752) Inherit C_Taxcategory_ID and IsTaxIncluded from orderline to FT
    * Functionality allowing the inheritance of Tax relevant information from Orderline to Flatrate Terms.
  * [#2755](https://github.com/metasfresh/metasfresh/issues/2755) Error on hu changing status from shipped to active
    * Fixes an error in Handling Units when transforming HU from shipped back to active status.
  * [#2763](https://github.com/metasfresh/metasfresh/issues/2763) New Window Picking Tray Clearing
    * New Window for the Picking Tray Clearing, allowing the user to maintain content of Picking Trays, do Handling Unit Compression and Preparation for Shipper Transportation.
  * [#2768](https://github.com/metasfresh/metasfresh/issues/2768) Translate window for process "Change Password" into EN
    * Translations added to Parms in Change Password Process.
  * [#2769](https://github.com/metasfresh/metasfresh/issues/2769) Model generator: wrong columns are generated
    * Internal Housekeeping Improvements in Model Generator. Now creating the correct columns.
  * [#2774](https://github.com/metasfresh/metasfresh/issues/2774) Vendor Invoice Layout and Translations in WebUI
    * Improved Window Vendor Invoice Layout to adapt current Design Guidelines and Translations for en_US added.
  * [#2724](https://github.com/metasfresh/metasfresh/issues/2724) Window Design Webui: Please, add translations to the window Contract Term
    * Improved Layout for Window Contract Terms, adapting current Guidelist. New Translation added for en_US Language/ Locale.
  * [#2764](https://github.com/metasfresh/metasfresh/issues/2764) Window Design Webui : Material receipt
    * Layout adapted to current WebUI Design Guidelines. Improved Translation for en_US, de_DE Language/ Locale.

* metasfresh-webui-api
  * [#632](https://github.com/metasfresh/metasfresh-webui-api/issues/632) HU-Trace: show all connected records
    * Improvement of Handling Unit Trace Functionality, now showing all connected records for a given Filter Criteria.
  * [#637](https://github.com/metasfresh/metasfresh-webui-api/issues/637) Move Picking Tray Clearing Functionality to new window
    * New Window for the Picking Tray Clearing Functionality.

* metasfresh-webui-frontend
  * [#1092](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1092) Prepare Update to React 16
    * Preparation Work for the Update to React 16.
  * [#1232](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1232) Labels widget: keyboard support
    * Adding Support for the new Labels widget Lookup.
  * [#1249](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1249) Included view: blurWhenOpen support
    * New Functionality allowing to switch the blur effect on included views in Main Grid View.


## Fixes
* metasfresh-app
  * [#2240](https://github.com/metasfresh/metasfresh/issues/2240) NPE on zoom into header aggregation
    * Bugfix in Zoom To Function when jumping to Header Aggregation.
  * [#2727](https://github.com/metasfresh/metasfresh/issues/2727) Tax is not set properly when creating ICs from flatrate term
    * Fix for the Invoice Candidate generation from Flatrate Terms. Now the Tax is set properly.
  * [#2794](https://github.com/metasfresh/metasfresh/issues/2794) Problem with picking for subscription deliveries
    * Improvement of the Picking workflow when picking subscription shipments.

* metasfresh-webui-api
  * [#625](https://github.com/metasfresh/metasfresh-webui-api/issues/625) WebUI Scheduler Window Log Subtab: Invalid Client ID=0
    * Internal Housekeeping, fix in Schedule Window Log included Tab.
  * [#639](https://github.com/metasfresh/metasfresh-webui-api/issues/639) Can't save a new partner
    * Fixes a Bug that prevented to create and save new Business Partners in WebUI.

* metasfresh-webui-frontend
  * [#1223](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1223) Scrollbar missing in Handling Unit Editor after "Internal Usage Action"
    * Fix for WebUI Windows. Her Handling Unit Editor. Now the Window Scrollbar is shown after the usage of Internal Usage Action.
  * [#1227](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1227) The Letter window dissapears after Print Preview
    * Fix for the Letter Window. now still visible after choosing Print Preview.
  * [#1239](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1239) Included views are not working in non-modal windows
    * Fixes the Included Views Functionality in WebUI. Now it's also possible to use them in non-modal windows.
  * [#1248](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1248) Included view: wrong selectedIds when calling quickActions
    * Fix in Included View. Now retrieving the correct Record/ Row ID's when calling Quick Actions.
  * [#1268](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1268) Sitemap broken
    * Fix for the Sitemap creation.
  * [#1271](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1271) Open ANY included view broken
    * Fixes the Included Views after issues occured for React 16 Refactoring.
  * [#1275](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1275) Scrolling in Modal window throws errors
    * Fixes the errors that occured when scrolling in Modal Overlays.
  * [#1278](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1278) Shortcuts for New, Batch and Expand are broken
    * Fixes the shortcuts for different shortcut keys.
  * [#1281](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1281) Translations of Photo Widget in User WIndow
    * Fixing the Translation of the Photo Widget Buttons in User Window.

# metasfresh 5.31 (2017-42)

**release for week 2017-42**

## Features
* metasfresh-app
  * [#518](https://github.com/metasfresh/metasfresh/issues/518) Easier solution to clear the Picking Slots
    * New Feature for the clearing of Picking Slots and Preparing Handling Units for Logistics.
  * [#2603](https://github.com/metasfresh/metasfresh/issues/2603) Window Design Webui : Translate fields of Purchase Candidates
    * Improved Translations for Languages/ Locales de_DE and en_US added to the Purchase Candidates Window.
  * [#2606](https://github.com/metasfresh/metasfresh/issues/2606) Window Design Webui: Translate GL Journal window in English
    * New Window and Fields Translation for en_US Language/ Locale. Adapting WebUI Design Guidelines. New Filter and Sorting Criteria.
  * [#2607](https://github.com/metasfresh/metasfresh/issues/2607) Window Design Webui: Improve window Internal Use Inventory
    * Adapting the Window to current Design Guidelines in WebUI, new Sorting and Filter Criteria. Adding Translations for en_US.
  * [#2608](https://github.com/metasfresh/metasfresh/issues/2608) Window Design WebUI: Improve Payment Allocation window
    * Improving Payment Allocation Window, adding Translations for en_US and adapting to current WebUI Guidelines.
  * [#2609](https://github.com/metasfresh/metasfresh/issues/2609) Window Design WebUI : Bank Statement Improvements
    * Improving the Fields Translations and adding Layout adaptions to Bank Statement Window in WebUI.
  * [#2636](https://github.com/metasfresh/metasfresh/issues/2636) Webui Report Design : Rüstliste translations
    * Translation of Pick List Report Parms to en_US.
  * [#2643](https://github.com/metasfresh/metasfresh/issues/2643) Forecast in Material Disposition
    * Adding the new Forecast Demand into Material Disposition workflow.
  * [#2645](https://github.com/metasfresh/metasfresh/issues/2645) Source HU Automatism for Manufacturing Issue
    * New automatic Source Handling Unit Functionality now available in manufcaturing Issue.
  * [#2660](https://github.com/metasfresh/metasfresh/issues/2660) Window Design Webui: Improvements in the window Forecast
    * Improvement of Forecast Window. Added Translations for en_US Language/ Locale and adapted Design Guidelines.
  * [#2690](https://github.com/metasfresh/metasfresh/issues/2690) New Window for AD_Scheduler in WebUI
    * New Window for Scheduler Maintenance.
  * [#2691](https://github.com/metasfresh/metasfresh/issues/2691) Adjustments in Materialdispo Window in WebUI
    * Improved Material Dispo Window. Removed deprecated Fields and added Translations for en_US and de_DE Locale/ Language
  * [#2694](https://github.com/metasfresh/metasfresh/issues/2694) Window Design Webui: Improvements in Dunning Type window
    * New Translations added for Language en_US for Action and Referencelist.
  * [#2695](https://github.com/metasfresh/metasfresh/issues/2695) Window Design Webui: Introduce the window Shipment Restrictions in the Webui menu
    * New Window in WebUI Menu. Now allowing the user to maintain Shipment Restrictions.
  * [#2696](https://github.com/metasfresh/metasfresh/issues/2696) Window Design Webui: Add subscription details in Shipment Candidate window
    * New Fields for Subscription/ Flatrate Terms in Shipment Disposition Window. Improved Translations for en_US.
  * [#2700](https://github.com/metasfresh/metasfresh/issues/2700) Set correct identifiers in the table M_ShipmentSchedule
    * Improved Shipment Schedule Identifier, now showing valuable Information about it to the user.
  * [#2704](https://github.com/metasfresh/metasfresh/issues/2704) Show an explicit error message when reports don't open because of logo missing
    * Improved Error Handling in Jasper Report Generation.
  * [#2711](https://github.com/metasfresh/metasfresh/issues/2711) Productdescription in sales order jasper
    * New Product Description Field in Sales Order Jasper Report.

* metasfresh-webui-frontend
  * [#1232](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1232) Labels widget: keyboard support
    * Adding the keyboard support for the new Labels widget.
  * [#1239](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1239) Included views are not working in non-modal windows
    * Now also allowing the usage of included views in non-modal windows.

* metasfresh-webui-api
  * [#628](https://github.com/metasfresh/metasfresh-webui-api/issues/628) Extend framework to allow modification of standard filter results
    * Extension for Filter mechanism in WebUI, now allowing to register a component that enhances the filter result. Needed first in new Handling Unit Tracing window.

## Fixes
* metasfresh-app
  * [#2400](https://github.com/metasfresh/metasfresh/issues/2400) Discount in manual sales invoice
    * Fixes the behavior of discount field in Sales Invoice.
  * [#2693](https://github.com/metasfresh/metasfresh/issues/2693) C_Flatrate_Conditions.C_Flatrate_Transition_ID needs to be mandatory
    * Fixing the mandatory Logic in table/ window Flatrate Conditions.
  * [#2699](https://github.com/metasfresh/metasfresh/issues/2699) NPE when pressing Complete for an invoice with no lines
    * Improves a Cornercase, now avoiding a Null Pointer Exception when completing an Invoice with no lines.

* metasfresh-webui-frontend
  * [#1216](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1216) HU lines appear only after refresh
    * Fix for the missing refresh in Handling Unit editor after using quickactions.
  * [#1221](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1221) Handling Unit Editor "Select all rows" missing
    * Fixes the missing "Select all" Functionality in Handling Unit Editor.
  * [#1224](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1224) Error when pressing Action after completing an invalid document
    * Improving the error Handling in Document Processing, avoiding errors after completing an invalid document.
  * [#1243](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1243) Opening a new included view is broken
    * Fix for the included view in Picking Window. Now working in Non-Modal Views too.
  * [#1254](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1254) Error when navigating with up and down arrows in grid view
    * Fix for the arrow navigation in WebUI Grid View.

# metasfresh 5.30 (2017-41)

**release for week 2017-41**

## Features
* metasfresh-app
  * [#1339](https://github.com/metasfresh/metasfresh/issues/1339) Forecast Window as Document
    * Forecast Look&Feel is now adapted to real document.
  * [#2152](https://github.com/metasfresh/metasfresh/issues/2152) Prepare Default Filters for Retraceability window
    * The Retraceability Window has now a default Subset of filters that allow the user to find the wanted data records quickly.
  * [#2212](https://github.com/metasfresh/metasfresh/issues/2212) Take out password field from user window and use process instead
    * Removing the Password Field from User Window, now only changeable via Password Change Action.
  * [#2432](https://github.com/metasfresh/metasfresh/issues/2432) Process for converting any table to a document
    * New Housekeeping and development Functionality that transforms any selected table into a document table.
  * [#2494](https://github.com/metasfresh/metasfresh/issues/2494) Prices changing in FlatrateTerms after Prolongation
    * New Price change Functionality in Subscription after Prolongation step.
  * [#2517](https://github.com/metasfresh/metasfresh/issues/2517) Freigabe zur Fakturierung as standard filter
    * Adding further default Filter to Invoice Candidate Window in WebUI.
  * [#2525](https://github.com/metasfresh/metasfresh/issues/2525) Generate Picking Document
    * New Jasper Report that allows to create and print a Picklist.
  * [#2590](https://github.com/metasfresh/metasfresh/issues/2590) Make use of product documentnote on Quotation and Order Document
    * New Field in Product that describes additional Information about a product and shall be printed on Quotation and Order reports.
  * [#2592](https://github.com/metasfresh/metasfresh/issues/2592) Make letter print preview work with AD_BoilerPlate jasper process
    * New Functioanlity in Letter feature, now allowing to use Textsnippets and the Boilerplate proces in there.
  * [#2594](https://github.com/metasfresh/metasfresh/issues/2594) New Window for Dunning Candidates in WebUI
    * New Window for Dunning Candidates in WebUI, that allows the preparation and creation of Dunning Documents.
  * [#2604](https://github.com/metasfresh/metasfresh/issues/2604) Printing via standalone client takes too long
    * Improvements of printing performance when printing via Standalone Client.
  * [#2611](https://github.com/metasfresh/metasfresh/issues/2611) Business Partner Import with empty location
    * Adjustment of the Businesspartner Import, now allowing to leave the location empty.
  * [#2614](https://github.com/metasfresh/metasfresh/issues/2614) Overhaul shipment schedule UI window
    * Additional shipment Schedule adjustments, new Quickactions and adding new Fields to Main View.
  * [#2615](https://github.com/metasfresh/metasfresh/issues/2615) Make the callout C_Flatrate_Matching.onC_Flatrate_Transition_ID comply with webui
    * Adapting the Flatrate Transition callout in WebUI to match with the behavior in Swing Client.
  * [#2623](https://github.com/metasfresh/metasfresh/issues/2623) Overhaul shipment schedule closing
    * Improvement of the shipment Schedule closing Feature, now allowing the user to reopen alraedy closed records.
  * [#2626](https://github.com/metasfresh/metasfresh/issues/2626) Window Design Webui: Translations and Improvements for DeliveryDays
    * Improved Translations for Language/ Locale en_US in Delivery Days Window.
  * [#2627](https://github.com/metasfresh/metasfresh/issues/2627) Create Zoom Across Reference between Flatrate Term and Invoice Candidate
    * New references added to Flatrate Term Window, allowing the user to quickly zoom into the other references data.
  * [#2628](https://github.com/metasfresh/metasfresh/issues/2628) Show fields product, qty, price and contractstatus in window contracts
    * Improvements for Subscription Contracts in Contract Window in WebUI. Adding Fields and Translations.
  * [#2631](https://github.com/metasfresh/metasfresh/issues/2631) Window Design Webui: Improve Business Partner window
    * Detailed Improvements to Business Partner Window in WebUI. Adapting current Design Guidelines. Translations added for en_US.
  * [#2632](https://github.com/metasfresh/metasfresh/issues/2632) Window Design Webui: Translations for Tourversion window
    * Additional Translations for Language/ Locale en_US in Window Tourversion in WebUI.
  * [#2635](https://github.com/metasfresh/metasfresh/issues/2635) Window Design Webui: Translations for Bank Statement Line Reference
    * Translations added for Language en_US in Bank Statement Line Reference Window in WebUI
  * [#2637](https://github.com/metasfresh/metasfresh/issues/2637) WebUI Window Design: Improvements to Account Combinations
    * Additional Improvements to Account Combination Window in WebUI. Adding missing Translations for en_US.
  * [#2639](https://github.com/metasfresh/metasfresh/issues/2639) Rename default Account values to be sorted last
    * Moving the 5-digit default Accounts to the end of table.
  * [#2642](https://github.com/metasfresh/metasfresh/issues/2642) Flatrate Import: Support explicit enddate and terminated contracts
    * Enhancement of the Flatrate/ Subscription migration Feature, now allowing to import an explicit enddate and automatically terminate contracts.
  * [#2644](https://github.com/metasfresh/metasfresh/issues/2644) Window Design Webui: Improvements to ESR window
    * Layout Improvements and Translations added for Language en_US.
  * [#2650](https://github.com/metasfresh/metasfresh/issues/2650) Allow subscription pause and recipient change with existing shipment scheds
    * New Functionality in Subscription Feature, that allows to set a delivery pause and receipient change for a given timeframe.
  * [#2655](https://github.com/metasfresh/metasfresh/issues/2655) Window Design WebUI: Translations and Improvements for Product Prices
    * New Translations for en_US in Product Prices Window. Adjusting Layout.
  * [#2657](https://github.com/metasfresh/metasfresh/issues/2657) Don't show inactive HUs in HU costprice report
    * Removing inactive Handling Units from the Costprice Report.
  * [#2665](https://github.com/metasfresh/metasfresh/issues/2665) Translate Add URL Attachment
    * Adding the de_DE message Translations for the URL Attachment Handling.
  * [#2666](https://github.com/metasfresh/metasfresh/issues/2666) Window Design Webui: Tanslations for the window Contract
    * Translation Improvement for Subtab Recors. Now completed the Translation for Language/ Locale en_US.
  * [#2667](https://github.com/metasfresh/metasfresh/issues/2667) Window Design Webui: Tanslations for the window Contractpartner
    * Improvements in Window Layout, Sorting and Filtering. Now translated to Language/ Locale en_US.
  * [#2669](https://github.com/metasfresh/metasfresh/issues/2669) Window Design WebUI: Improvements and Translations in Shipment Candidates
    * Improvement of Translations for en_US Language/ Locale in shipment candidate window in WebUI.

* metasfresh-webui-frontend
  * [#1138](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1138) Frontend: Labels widget
    * New Lookup widget in WebUI. Now allowing to search, select and use Labels in a Main View. Fully customizable via Application Dictionary.
  * [#1220](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1220) Frontend: Attach URL support
    * New Functionality in Document Attachments, now being able to record Bookmarks/ URL instead of attaching a document.

## Fixes
* metasfresh-app
  * [#2543](https://github.com/metasfresh/metasfresh/issues/2543) Businesspartner Import for different Partner with the same address
    * Fixes the Businesspartner Import Feature, now allowing to import different Businesspartners with the same address.
  * [#2678](https://github.com/metasfresh/metasfresh/issues/2678) Shipment Schedules not created for new flatrate terms
    * Bugfix for the shipment Schedule creation for new Subscriptions/ Contracts.
  * [#2688](https://github.com/metasfresh/metasfresh/issues/2688) SQL syntax error in ShipmentSchedulePA.retrieveUnprocessedForRecord()
    * Internal Housekeeping. Fix for a SQL syntax error in Shipment Schedule.

* metasfresh-webui-frontend
  * [#1212](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1212) Selection using SHIFT does not work in Handling unit Editor
    * Keyboard Selection Fix in WebUI now allowing to use shift key in handling unit editor.
  * [#1234](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1234) Letter Window does not store template info
    * Fixes a minor Bug in Letter component that prevented the usage of Text Snippets.
  * [#1238](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1238) Bug in Partner window after setting Attributes (Labels)
    * Fixes a Bug that occurred in Business Partner window after setting new Labels via Label Lookup widget.

# metasfresh 5.29 (2017-40)

**release for week 2017-40**

## Features
* metasfresh-app
  * [#2190](https://github.com/metasfresh/metasfresh/issues/2190) Window Design WebUI: Shipment Improvements
    * Layout adjustments for the Shipment Window in WebUI.
  * [#2221](https://github.com/metasfresh/metasfresh/issues/2221) Window Design WebUI : Bank Statement Improvements
    * Layout Improvements to Bank Statement WIndow in WebUI.
  * [#2271](https://github.com/metasfresh/metasfresh/issues/2271) Window Design WebUI: Adjust Sales Invoice Window Improvements
    * Layout Improvements for the Sales Invoice Window in WebUI.
  * [#2280](https://github.com/metasfresh/metasfresh/issues/2280) Window Design WebUI: c_activity
    * Minor Layout Improvement for the Cost Center Window in WebUI.
  * [#2281](https://github.com/metasfresh/metasfresh/issues/2281) Window Design WebUI: Product Costs
    * Minor Layout Improvement for the Product Cost Window in WebUI.
  * [#2285](https://github.com/metasfresh/metasfresh/issues/2285) Window Design WebUI : Unit of Measure Improvements
    * Minor Layout Improvement for the Unit of Measure Window in WebUI.
  * [#2468](https://github.com/metasfresh/metasfresh/issues/2468) Window Design WebUI: gridview with client
    * Improvements in different Windows, adapting the Layout to our current design Guidelines. Adding Translations for de_DE and en_US Locale/ Language.
  * [#2495](https://github.com/metasfresh/metasfresh/issues/2495) Pause subscription delivery for given timeframe
    * New Functionality in Subscription, allowing to add a Pause Timeframe to a given Subscription.
  * [#2496](https://github.com/metasfresh/metasfresh/issues/2496) Different subscription Receiver for given timeframe
    * New Functionality in Subscription that allows the user to define an alternative shipment Receiver for a chosen timeframe.
  * [#2499](https://github.com/metasfresh/metasfresh/issues/2499) Stop Subscription shipment after 2nd dunning
    * Generic solution in Dunning, that allows to automatically stop Subscription Shipments after chosen amount of dunning Levels.
  * [#2560](https://github.com/metasfresh/metasfresh/issues/2560) Menu Window improvements in WebUI
    * New -Column Layout for the Menu maintenance Window in WebUI.
  * [#2565](https://github.com/metasfresh/metasfresh/issues/2565) Add attributes to subscription flatrate terms
    * Functionality that allows to add Attributes to Subcription contracts.
  * [#2574](https://github.com/metasfresh/metasfresh/issues/2574) Provide data about a shipment schedule's source document
    * Extension of Shipment Schedule, now adding more Transparences about the Shipment Schedule Trigger Document.
  * [#2580](https://github.com/metasfresh/metasfresh/issues/2580) Add Subscription history in Contract window WebUI
    * Adding included Tab for Subscription History to Contract Window in WebUI.
  * [#2584](https://github.com/metasfresh/metasfresh/issues/2584) Remove remaining dependencies between shipment schedule and order line
    * Improving the Shipment Schedule to Orderline Dependancy, now allowing to have more than 1 Shipment Schedule to an Orderline, which is often needed in Subscription Contracts.
  * [#2585](https://github.com/metasfresh/metasfresh/issues/2585) Action/ Process to reopen processed Shipment Schedules
    * New Process that allows to reopen an already closed/ processed shipment Schedule entry.
  * [#2591](https://github.com/metasfresh/metasfresh/issues/2591) Subscription invoicing
    * New/ Extended Functionality for the Invoiceing of Subscription contracts and shipments.
  * [#2593](https://github.com/metasfresh/metasfresh/issues/2593) New Window for Dunning Level in WebUI
    * New window in WebUI that allows the Maintenance of different Dunning Types and Dunning Levels.
  * [#2601](https://github.com/metasfresh/metasfresh/issues/2601) Export AD_System relevant fields to /info endpoint
    * New internal Functionality that allows to get all AD_System relevant fields via /info endpoint.
  * [#2602](https://github.com/metasfresh/metasfresh/issues/2602) Reactivate and fix subscription pricing
    * Improvement of the subscription Pricing Functionality.

* metasfreesh-webui-api
  * [#605](https://github.com/metasfresh/metasfresh-webui-api/issues/605) I as an user want to store an URL as attachment to a record
    * New Feature that allows to add an URL to the attachment List of a given Data Record.
  * [#606](https://github.com/metasfresh/metasfresh-webui-api/issues/606) HU-Transform needs to filter by HU status
    * New Feature that allows to Filter by Handling Unit Status when transforming the HU.
  * [#613](https://github.com/metasfresh/metasfresh-webui-api/issues/613) Set unique hazlecast group name
    * Internal issue to prevent Hazelcast to connect/ communicate with other instances.

## Fixes
* metasfresh-app
  * [#2566](https://github.com/metasfresh/metasfresh/issues/2566) Fix AD_RefList.ValueName value around subscription
    * Reference List Fix in Subscription.
  * [#2598](https://github.com/metasfresh/metasfresh/issues/2598) Fix the callout for CalloutProductCategory to comply with webui
    * Minor fix in the Product Category Callout to comply with WebUI.
  * [#2576](https://github.com/metasfresh/metasfresh/issues/2576) Swing Client. Window for Printing Queue does not open properly anymore
    * Fix for the Printing Queue Window in Swing Client.

# metasfresh 5.28 (2017-39)

**release for week 2017-39**

## Features
* metasfresh-app
  * [#2090](https://github.com/metasfresh/metasfresh/issues/2090) Improve text Variable Window in WebUI
    * Improving the Textvariable and Textsnippets window in WebUI, adapting out current webui design guidelines and adding Translations for de_DE, en_US.
  * [#2146](https://github.com/metasfresh/metasfresh/issues/2146) Implement Generic Multi Tags / labels for records
    * New Lookup Functionality allowing to define Subtab Data as Label Container in a Main View Section.
  * [#2222](https://github.com/metasfresh/metasfresh/issues/2222) Window Design WebUI : Sales Order Improvements
    * Adapting the Sales Order Window to current design guidelines. Adding primary Layout for Included Tabs and rearranging the Grid View Sequence.
  * [#2284](https://github.com/metasfresh/metasfresh/issues/2284) Window Design WebUI : Attributes Improvements
    * Layout adaption to the Attributes Window in WebUI. Minor Improvements.
  * [#2286](https://github.com/metasfresh/metasfresh/issues/2286) Window Design WebUI : Discount Schema Improvements
    * Layout adaption to the Discount Schema Window in WebUI. Minor Improvements.
  * [#2288](https://github.com/metasfresh/metasfresh/issues/2288) Window Design WebUI : Product Category Improvements
    * Layout adaption to the Discount Schema Window in WebUI. Primary Key added. Improvements in Subtab Layout and Grid View.
  * [#2344](https://github.com/metasfresh/metasfresh/issues/2344) Support version 02 CAMT54 file
    * Upgrades the CAMT54 Sepa Import to the new Version 02.
  * [#2376](https://github.com/metasfresh/metasfresh/issues/2376) Detail Improvements to Order Candidates Window
    * Improvement of Order Candidates Window, allowing the user to manipulate the data directly in Main View.
  * [#2395](https://github.com/metasfresh/metasfresh/issues/2395) Drop C_Flatrate_Conditions.IsNewTermCreatesOrder together with java code
    * Housekeeping in Subscripts. Eliminating deprecated functionalities in Code and Application Dictionary.
  * [#2440](https://github.com/metasfresh/metasfresh/issues/2440) Material Tracking adjustments in WebUI
    * Adjustments to the Material Tracking Window. Adding further Filter possibilities.
  * [#2477](https://github.com/metasfresh/metasfresh/issues/2477) Subscription Import Window Adjustments
    * Adds further Improvements to the new Subscription Import Window.
  * [#2478](https://github.com/metasfresh/metasfresh/issues/2478) Check Filter Sequence for Default Fields Organisation and Warehouse
    * Globally moving the Filter for Organisation to the end of each filter list.
  * [#2482](https://github.com/metasfresh/metasfresh/issues/2482) Allow disabling of model interceptors on the fly
    * New technological Improvements that allows to manipulate model interceptors without restarting the instance.
  * [#2483](https://github.com/metasfresh/metasfresh/issues/2483) New Dist-Orgs Businesspartner Window in WebUI
    * New Window in WebUI menu, adapting the requirements of dis-orgs to the Businesspartner window.
  * [#2484](https://github.com/metasfresh/metasfresh/issues/2484) Allow using partner product numbers and name for entering order line
    * New Functionality for Orderline Batch entry, now allowing to also search for customer Product Numbers in Batch entry.
  * [#2498](https://github.com/metasfresh/metasfresh/issues/2498) Window Design WebUI: Returns Improvements
    * Detail Improvements added to the Customer and Vendor Returns window in WebUI. Adapting metasfresh WebUI design Guidelines.
  * [#2501](https://github.com/metasfresh/metasfresh/issues/2501) Import Requests
    * New Functionality the allows the import of Requests.
  * [#2502](https://github.com/metasfresh/metasfresh/issues/2502) Allow multiple shipment schedules per orderline
    * Improvement to the Shipment Schedule Generation and Handling. Now allowing to have more than 1 shipment schedule line for an orderline. This allows a much more flexible handling of Shipping Candidates in Subscriptions.
  * [#2509](https://github.com/metasfresh/metasfresh/issues/2509) Window Design WebUI : Translate Picking Slot Window name in en
    * Translating the Picking Tray Window and Picking to en_US.
  * [#2510](https://github.com/metasfresh/metasfresh/issues/2510) Prevent users from creating duplicate main prices
    * New Feature that dissallows the user to create more than 1 main price.
  * [#2514](https://github.com/metasfresh/metasfresh/issues/2514) Support importing multiline fields
    * Improvements of the data improter, now allowing the import of multiline content.
  * [#2516](https://github.com/metasfresh/metasfresh/issues/2516) Add Rüstliste Report to WebUI
    * New Picking Preview Report now available in shipping reports section of WebUI Menu.
  * [#2529](https://github.com/metasfresh/metasfresh/issues/2529) Import System users
    * Extension of the user import, adding the possibility to mark users as system users.
  * [#2533](https://github.com/metasfresh/metasfresh/issues/2533) Adjust Contract Period Window in WebUI for Subscriptions
    * Improvements for the Contract Period Window in WebUI. Adding Translations for en_US Language/ Locale.
  * [#2538](https://github.com/metasfresh/metasfresh/issues/2538) Remove legacy LDAP code
    * Housekeeping task, removing deprecated legace code of LDAP functionality.
  * [#2541](https://github.com/metasfresh/metasfresh/issues/2541) Allow filtering by product group in window product
    * Adds a new default Filter to the Product Window, now possible to filter by Product Category.

* metasfresh-webui-api
  * [#576](https://github.com/metasfresh/metasfresh-webui-api/issues/576) Implement batch entry for Sales/Purchase Invoice documents
    * New Feature in Sales and Purchase Invoices, now allowing to use the Batch Entry there too.
  * [#591](https://github.com/metasfresh/metasfresh-webui-api/issues/591) Filter by Warehouse when selecting HUs to issue
    * Adds a new default Filter to the Handling Unit Window, allowing users to search for Handling Units only in the selected warehouse.
  * [#603](https://github.com/metasfresh/metasfresh-webui-api/issues/603) Handle incorrect AD_Window_ID in URL
    * Handling the case when a incorrect Window ID is added to the URL in WebUI.
  * [#607](https://github.com/metasfresh/metasfresh-webui-api/issues/607) AD_Column.IsMandatory shall be overwritten by MandatoryLogic
    * Improved Feature that allows the overwrite isMandatory with Mandatory Logic in Application Dictionary.

* metasfresh-webui-frontend
  * [#1167](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1167) Edit Fields in Main Grid View
    * New Functionality that allows to switch into edit mode in Main Grid Views. This Feature was wanted by a lot of users to quickly maintain data changes in long lists, e.g. Product Prices.
  * [#1192](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1192) Grid values are cut off if column size is too small
    * Showing tooltips in Table Grid Cells. Allowing to hover over a field and whoing the complete content in Tooltip. Very helpful for content that does not fit fully ionto a displayed Grid Field.
  * [#1194](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1194) Use API to fetch the locale
     Now fetching the User Locale via API call.
  * [#1195](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1195) Render only provided document standard actions
    * New Functionality in WebUI frontend, now only showing entries in Action Menu that fit to the opened window.
  * [#1196](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1196) Frontend: Letters feature
    * New Letter functionality in WebUI, allowing to write letters via action Menu entry.
  * [#1197](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1197) Frontend: Export view to excel
    * New Functionality that allows to select Record lines and export them directly into an Excel File.

## Fixes
* metasfresh-app
  * [#2503](https://github.com/metasfresh/metasfresh/issues/2503) Typo in German Translation of Contract Terms menu Entry
    * Fixing the Translation of Contract Window for Language/ Locale de_DE in WebUI Menu.

* metasfresh-webui-api
  * [#505](https://github.com/metasfresh/metasfresh-webui-api/issues/505) Record_ID not found for IsSOTrx = "N"
    * Fix for the Zoom-To Functionality. Now possible to Zoom to windows depending on the isSOTrx context.
  * [#593](https://github.com/metasfresh/metasfresh-webui-api/issues/593) Remove Clone action if not supported
    * Now only showing the Close Action when supported on the selected Record/ Record Type.
  * [#608](https://github.com/metasfresh/metasfresh-webui-api/issues/608) CU Quantity in Excel Export from HU Editor is wrong
    * Fix for the new Excel Export when used in Handling Unit Editor. Now exporting the correct Customer Unit Quantity.
  * [#588](https://github.com/metasfresh/metasfresh-webui-api/issues/588) Cloning Feature: Error when cloning BOM
    * Fix of the Record Close action, now allowing to clone BOMs too.
  * [#590](https://github.com/metasfresh/metasfresh-webui-api/issues/590) Issues HUs are still selectable in HU editor while production is not completed
    * Fix for the Action Issue and Handling Unit editor in Manufacturing workflow.

* metasfresh-webui-api
  * [#536](https://github.com/metasfresh/metasfresh-webui-api/issues/536) Fields Org and Client are not filtered by users permissions
    * Fix for the permissions for Filter of organisation and Client in WebUI.

# metasfresh 5.27 (2017-38)

**release for week 2017-38**

## Features
* metasfresh-app
  * [#2089](https://github.com/metasfresh/metasfresh/issues/2089) Inventory Move Window improvements
    * Improvements done to the Inventory Movement Window.
  * [#2218](https://github.com/metasfresh/metasfresh/issues/2218) User would like to import default values for contact and address
    * Providing a possibility to import default Roles for User and Locations in Business Partner.
  * [#2245](https://github.com/metasfresh/metasfresh/issues/2445) Support quantity in flatrate term import
    * Adding the field quantity to the importer of flatrate terms.
  * [#2247](https://github.com/metasfresh/metasfresh/issues/2474) Extend Partner Import with ad_language
    * Now possible to add the language to Business Partner import data.
  * [#2436](https://github.com/metasfresh/metasfresh/issues/2436) Use default values for IsShipTo and isBillTo when importing bpartner
    * Fallback mechanism to set default billto and shipto locations in Business Partner.
  * [#2459](https://github.com/metasfresh/metasfresh/issues/2459) Shipto and billto contact / user
    * New Fields in Business Partner Contact: shipto and billto.
  * [#2298](https://github.com/metasfresh/metasfresh/issues/2298) Introduce source-HU to fine-Picking
    * New functionality for a more efficient Picking workflow. Now able to select a source HU that is used for the following picking steps.
  * [#2361](https://github.com/metasfresh/metasfresh/issues/2361) Invoice Candidates Action Approval for Invoicing
    * New action in Invoice candidates, allowing to mass approve candidates for Invoiceing.
  * [#2381](https://github.com/metasfresh/metasfresh/issues/2381) Detailed Improvement of Flatrate Term window in WebUI
    * New Window for contracts in WebUI, making the contract management easier and more transparent.
  * [#2386](https://github.com/metasfresh/metasfresh/issues/2386) New Window for M_PickingSlot_Trx in WebUI
    * New Window in WebUI allowing to View Picking Slot Transations and involved Handling Units.
  * [#2387](https://github.com/metasfresh/metasfresh/issues/2387) Autocomplete for zip / postal code and city
    * New functionality to autocomplete postal and city in Location recording workflow.
  * [#2397](https://github.com/metasfresh/metasfresh/issues/2397) Import and Store Contract meta-data e.g. startdate on flatrate
    * Now possible to store different contracts metadata via import.
  * [#2405](https://github.com/metasfresh/metasfresh/issues/2405) New Window for C_Invoice_Clearing_Alloc in WebUI
    * New window for Invoice Allocation as substitute for 3rd Level Subtab in old Contract Window.
  * [#2406](https://github.com/metasfresh/metasfresh/issues/2406) New Window for C_SubscriptionProgress in WebUI
    * New window for Subscription Progress/ History as substitute for 3rd Level Subtab in old Contract Window.
  * [#2408](https://github.com/metasfresh/metasfresh/issues/2408) Contract Date and Contract DocumentNo
    * New Fields in contract Window: Document No and Date, allowing a better recognition of the contract creation.
  * [#2413](https://github.com/metasfresh/metasfresh/issues/2413) Make request status a search field in request window
    * Adding a new Filter to request window, allowing to search for data with a given request status.
  * [#2416](https://github.com/metasfresh/metasfresh/issues/2416) New Window for M_QualityInsp_LagerKonf_Version in WebUI
    * New Windows for yearly Swiss Storage Conference, allowing the user to define the Storage Conference Criteria.
  * [#2421](https://github.com/metasfresh/metasfresh/issues/2421) New Fields MasterStartDate and MasterEndDate in FlatRate Term
    * New Fields for Mastercontract Start and End Date added to the Flatrate Term Window in WebUI.
  * [#2426](https://github.com/metasfresh/metasfresh/issues/2426) Translation for window "RfQ" in WebUI
    * Adjustments to the Request for Quotation Window, Refining Layout and adding Translations for en_US, de_DE locale/ Language.
  * [#2429](https://github.com/metasfresh/metasfresh/issues/2429) Hide qty TU when HU name is empty
    * Now hiding the QtyTU Field in different Jasper reports in case the Handling Unit name is empty.
  * [#2431](https://github.com/metasfresh/metasfresh/issues/2431) Product Filter in Handling Unit Editor
    * New Filter in Handling Unit Editor, allowing to search for entries with a given product.
  * [#2434](https://github.com/metasfresh/metasfresh/issues/2434) Make field ad_user_incharge_id from C_Flatrate_Term optional
    * Adjusting the Field inCharge in Flatrate Term, now making it optional.
  * [#2442](https://github.com/metasfresh/metasfresh/issues/2442) New Window for GL Journal Category in WebUI
    * New Window for GL Category in WebUI Finance/ Settings.
  * [#2449](https://github.com/metasfresh/metasfresh/issues/2449) Adjust the window Product for Procurement Contracts
    * Adjustments to the WebUI Window for Product for Procurement Contracts maintenance. Translations added for de_DE and en_US.
  * [#2453](https://github.com/metasfresh/metasfresh/issues/2453) Add missing Reports to WebUI menu
    * New reports in WebUI Menu for Purchase and Sales Tracing and for Finance (Cost Center ajdustments).
  * [#2469](https://github.com/metasfresh/metasfresh/issues/2469) Window for C_Postal in WebUI
    * New Window Country, City and Postal for WebUI. Added to WebUI menu in System/ Settings. Allowing to the User to maintain Postal-City combinations and use them for fast Location recording.
  * [#2450](https://github.com/metasfresh/metasfresh/issues/2450) Translation for Packvorschrift-Produkt-Zuordnung in English version
    * New en_US Translation for the Field Packing Instruction. Making the Name shorter and more comprehensive for the user.
  * [#2463](https://github.com/metasfresh/metasfresh/issues/2463) Extend Partner Import to support contact flags
    * Extended Partner import, allowing to also import contact Y/N settings.

* metasfresh-webui-api
  * [#142](https://github.com/metasfresh/metasfresh-webui-api/issues/142) Provide v11 / CAMT 54 File Import
    * New importer for ESR v11/ camt54 files in WebUI.
  * [#178](https://github.com/metasfresh/metasfresh-webui-api/issues/178) Have Letter Feature in Sales Order Window
    * New Letter Feature in WebUI allowing the user to oppen a text editor and create Letters with then help of Textsnippets.
  * [#491](https://github.com/metasfresh/metasfresh-webui-api/issues/491) Wrong widget Type for Date-Range in Process Parameter
  * [#547](https://github.com/metasfresh/metasfresh-webui-api/issues/547) Provide mass export of Data to Excel
    * Very nice new, Feature allowing the user to select lines and export to excel.
  * [#581](https://github.com/metasfresh/metasfresh-webui-api/issues/581) API to provide user session's locale
    * Internal housekeeping issue allowing the locale of a user to be stored in session.
  * [#583](https://github.com/metasfresh/metasfresh-webui-api/issues/583) Provide the document's standard actions
    * New Feature to restrict actions in the action menu. Now only showing those that fit to the window opened.
  * [#585](https://github.com/metasfresh/metasfresh-webui-api/issues/585) Lookup validation rule shall support @CtxName/Default@ annotation
    * Internal Housekeeping Issue in Applicaiton Dictionary, now allowing to use @notations in Validation rules in WebUI too.

* metasfresh-webui-frontend
  * [#1060](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1060) Locale viewing and editing in User Interface
    * The user has not the possibility to switch the user locale. The formatting of numbers are then shown correct for the selected country.

* other
  * [metasfresh/metasfresh-parent#14](https://github.com/metasfresh/metasfresh-parent/issues/14) Look for a way of not having the "placeholder" on local builds
    * Improvements of continuous integration System when creating local builds.

## Fixes
* metasfresh-app
  * [#2401](https://github.com/metasfresh/metasfresh/issues/2401) Vendor return with quality discount
    * Fix for the Vendor Return Feature, now also working with voided Returns and quality discount properly.
  * [#2412](https://github.com/metasfresh/metasfresh/issues/2412) OrderCheckout Jaspers display empty C_BPartner_Product.ProductName
    * Fix for the Jasper Report of Order Checkout, now showing the BPartner.name properly again.
  * [#2423](https://github.com/metasfresh/metasfresh/issues/2423) Fix jaspers for vendor return with quality issue
    * Fixes in the Vendor Returns Jasper document when having cases with quality issues.
  * [#2430](https://github.com/metasfresh/metasfresh/issues/2430) Invoice candidate close is retrieving the invoice candidates out of transaction
    * Internal Housekeeping fix for invoice candidates close workflow.
  * [#2457](https://github.com/metasfresh/metasfresh/issues/2457) Duplicate locations on partner import when multiple contacts
    * Fix for the Business Partner Location Importer, now not generating multiple entries when multiple user contacts exist to a Partner.
  * [#2492](https://github.com/metasfresh/metasfresh/issues/2492) Process C_Dunning_Candidate_Process fails

* metasfresh-webui-api
  * [#570](https://github.com/metasfresh/metasfresh-webui-api/issues/570) Clicking print on non-document window shows error page
    * Adjustment of Action Menu API, now only showing the actions that are possible in a given Window.
  * [#582](https://github.com/metasfresh/metasfresh-webui-api/issues/582) Cannot invoice disposal's invoice candidates
    * Fix for the disposal invoicing process.
  * [#594](https://github.com/metasfresh/metasfresh-webui-api/issues/594) LU is not created when you receive in production
    * Action Receipt Handling Unit retrieval. Fix when creating Logistic Units in manufacturing.
  * [#597](https://github.com/metasfresh/metasfresh-webui-api/issues/597) error in picking
  * [#598](https://github.com/metasfresh/metasfresh-webui-api/issues/598) partial issue is disabled
  * [#599](https://github.com/metasfresh/metasfresh-webui-api/issues/599) String document IDs cannot be converted to int error on Transform

* metasfresh-webui-frontend
  * [#1205](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1205) Can't open any process while on Dashboard

# metasfresh 5.26 (2017-37)

**release for week 2017-37**

## Features
* metasfresh-app
  * [#2224](https://github.com/metasfresh/metasfresh/issues/2224) Import Subscriptions / Flatrate Terms
    * New Import Functionality. Now allowing the user to Import Subscriptions/ Flatrate Terms.
  * [#2300](https://github.com/metasfresh/metasfresh/issues/2300) Doc Outbound missing Columns
    * Adjustments to Document Outbound Window, adapting to current Design Guidelines, making it more user friendly with more functionality.
  * [#2303](https://github.com/metasfresh/metasfresh/issues/2303) Auto Product No when new
    * Adding the automatic creation of Product No. as soon a new Product Record is created by the user.
  * [#2304](https://github.com/metasfresh/metasfresh/issues/2304) New Action: Create new Product Prices from selected Pricelist Version
    * Improvement of Price List and Product Prices Window, adding the action to create new Price List version easiliy via mass selection and calculation schema.
  * [#2306](https://github.com/metasfresh/metasfresh/issues/2306) Period mass actions missing in WebUI
    * New Action "Open all/ Close all" in Period Window, allowing to change the Period status of all Doctypes in few clicks.
  * [#2307](https://github.com/metasfresh/metasfresh/issues/2307) Window Period adjustments, add missing Translations
    * Translation for en_US and further refinements of Calendar Period Window in WebUI.
  * [#2314](https://github.com/metasfresh/metasfresh/issues/2314) Sales Order detail adjustments from User Feedback
    * Further detailed improvements of Sales Order Window in WebUI, adding Fields and Translations.
  * [#2317](https://github.com/metasfresh/metasfresh/issues/2317) Improvement of Delivery Days Window in WebUI
    * Further detailed improvements done in Delivery Days Window.
  * [#2326](https://github.com/metasfresh/metasfresh/issues/2326) New Window for Internal Usage in WebUI
    * New Window for the maintenance of Internal Inventory Usage.
  * [#2334](https://github.com/metasfresh/metasfresh/issues/2334) Default Filter Criteria for selected Windows
    * Adding some default Filter criteria to selected Windows in WebUI.
  * [#2337](https://github.com/metasfresh/metasfresh/issues/2337) Purchase Order detail adjustments
    * Adjusting the Purchase Order Window with further details.
  * [#2339](https://github.com/metasfresh/metasfresh/issues/2339) New Window in WebUI for Changelog
    * New Window for Changelog maintenance in WebUI. This Window will be added to referenced Documents Sidelist for all records in future and show the data changes done by whom and when.
  * [#2343](https://github.com/metasfresh/metasfresh/issues/2343) Add code coverage metrics to our builds
    * Internal Housekeeping Task in our Jenkis Buildsystem. Now showing Test coverage metrics for each build.
  * [#2348](https://github.com/metasfresh/metasfresh/issues/2348) Add BPartner Value to Material Receipt Label
    * Extended Material Receipt Label for Handling Units. Now showing the Buisness Partner No. for Vendor and Producer/ Manufacturer.
  * [#2352](https://github.com/metasfresh/metasfresh/issues/2352) Invoice Customer window adjustments for WebUI
    * Detailed improvements of the Customer Invoice Window in WebUI, adding new Actions and refining the Grid View Layout.
  * [#2353](https://github.com/metasfresh/metasfresh/issues/2353) Invoice Candidates Window in WebUI detail improvements
    * Detailed improvements of the Invoice Candidates Window in WebUI, refining the Grid View Layout.
  * [#2362](https://github.com/metasfresh/metasfresh/issues/2362) Default Reports to WebUI
    * New Sales, Purchase, Warehouse, Logistics and Finance Reports added to WebUI.
  * [#2378](https://github.com/metasfresh/metasfresh/issues/2378) Procurement candidates detail Improvements
    * Refining the Fieldnames in Procurement Candidates Window, allowing a better transparency for the User.
  * [#2385](https://github.com/metasfresh/metasfresh/issues/2385) New Window for M_PickingSlot in WebUI
    * New Picking Slot Window in WebUI, allowing the maintenance of Picking Locators/ Slots and allocation to Businesspartner and Locations or usage with dynamic allocation.
  * [#2391](https://github.com/metasfresh/metasfresh/issues/2391) Partial vendor Returns
    * New Functionality in Handling unit Editor now allowing to also do partial Vendor Returns.
  * [#2398](https://github.com/metasfresh/metasfresh/issues/2398) Reverse Correct of Disposal takes too long
    * Performance Improvement for the reverse correction of Disposal Documents.

* metasfresh-webui-api
  * [#563](https://github.com/metasfresh/metasfresh-webui-api/issues/563) Handling Unit Editor - remove hardcoded filter for active HUs
    * Removing the hardcoded isActive Filter from Handling Unit Editor in WebUI.
  * [#564](https://github.com/metasfresh/metasfresh-webui-api/issues/564) Filter Layout Sequence
    * New Functionality in Filter Configuration. Now allowing to define the sequence in which Filters are shown.
  * [#573](https://github.com/metasfresh/metasfresh-webui-api/issues/573) MoveToDirectWarehouse shall support multiple HUs
    * New Functionality in Handling Unit Action for Internal Use, now allowing to mass-select and use Handling Units in one step.
  * [#577](https://github.com/metasfresh/metasfresh-webui-api/issues/577) API for Edit Fields in Main Grid View
    * Adding the API for editing Fields in Main Table Grid Views.

* metasfresh-webui-frontend
  * [#1147](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1147) Implement the initial Sorting Indicator in included Tabs
    * Now also showing the initial Sorting indicators in included tabs.
  * [#1152](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1152) find a new shortcut for CTRL + X
    * Providing a new shortcut for eMail send. It's now ctrl+k instead of ctrl+x.
  * [#1164](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1164) frontend: document cloning
    * New Functionality that allows the user to easily copy/ clone documents.


## Fixes
* metasfresh-app
  * [#2141](https://github.com/metasfresh/metasfresh/issues/2141) Year-End P&L Process w/ multiple Organisations error
    * Fixes a Bug that wrongly calculated the year end P&L when organisations are different but youse the identical Accounting Schema (defined on client level).
  * [#2302](https://github.com/metasfresh/metasfresh/issues/2302) Order validation rule for packing instruction needs to look at DatePromised
    * Fix for a minor Bug that appeared when choosing the Packing Instructions in validation rule for Orderlines.
  * [#2342](https://github.com/metasfresh/metasfresh/issues/2342) Make Material Disposal Lines and their Movements respect all the details from the HUs
    * Fixes a minor Bug in the new Disposal Functionality according to missing Handling Unit Information.
  * [#2354](https://github.com/metasfresh/metasfresh/issues/2354) LU has HU_Status Active after picking
    * Minor Fix for the Handling Unit Status Handling, here having the wrong status for included Handling Units after Picking workflow.
  * [#2389](https://github.com/metasfresh/metasfresh/issues/2389) edi problem for customer returns
    * Fix in Customer Returns when using w/ EDI.
  * [#2399](https://github.com/metasfresh/metasfresh/issues/2399) uom in customer return lines
    * Minor fix for UOM Cornercase. Now showing the correct UOM in customer return Line.

* metasfresh-webui-api
  * [#532](https://github.com/metasfresh/metasfresh-webui-api/issues/532) Unable to see list of document's attachments
    * Fix for the new Document Attachments List in WebUI, now showing the attached Files again.
  * [#572](https://github.com/metasfresh/metasfresh-webui-api/issues/572) qty doubled on vendor/customer return and destroyed HUs
    * Quantity Fix in Vendor and Customer Return Lines.
  * [#578](https://github.com/metasfresh/metasfresh-webui-api/issues/578) storage error on disposal
    * Getting rid of en error that appeared in Storage when disposing Handling Units.

* metasfresh-webui-frontend
  * [#1156](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1156) Offline message is no longer displayed
    * Fixes the message that is shown when the application turns offline.
  * [#1159](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1159) batch entry: scrollbar not moving when using keyboard
    * Now moving the scrollbar when using the arrow keys in WebUI frontend.
  * [#1165](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1165) frontend cache for dropdown value not invalidated on lookupValuesStale is true
    * Fix in Frontend Caching.
  * [#1170](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1170) Edit Account Lookup Fields in GridView not possible
    * Fix and adjustment to the Lookup fields behavior in included Views.
  * [#1172](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1172) filtering is not working in picking
    * Allowing the Filters to be uses in the new Picking Window in WebUI.
  * [#1178](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1178) [ctrl]+u sometimes opens chrome sourceview instead of trigger main quickaction
    * Harmonizing the usage of ctrl+u key combination in WebUI Windows and Fields.
  * [#1179](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1179) [ctrl]+enter does not save changes before
    * Harmonizing the usage of ctrl+enter key combination in WebUI Windows and Fields.
  * [#1183](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1183) Purchase order: screen is scrolling when i am trying to type in Delivery from field
    * Avoiding the scroll of the main page when scrolling in a Lookup Field

# metasfresh 5.25 (2017-36)

**release for week 2017-36**

## Features
* metasfresh-app
  * [#529](https://github.com/metasfresh/metasfresh/issues/529) Possibility to easily create more than 1 referenced Doc per Invoice
    * Extending the credit memo generation action for Invoices. Adding more flexibility, now allowing to create more than 1 referenced document.
  * [#1876](https://github.com/metasfresh/metasfresh/issues/1876) New CU Labels
    * New Customer Unit Labels created. These will be uses in manufacturing Action receipt workflow.
  * [#2102](https://github.com/metasfresh/metasfresh/issues/2102) maven change "local" version from 1.0.0 to 10.0.0
    * Internal Housekeeping issue solved for our Continuous Integration/ Build System.
  * [#2145](https://github.com/metasfresh/metasfresh/issues/2145) Partial Internal usage action in Handling Unit Editor
    * Extended Functionality in Internal usage action in Handling Unit Editor, now allowing to select and use partial Handling Units.
  * [#2229](https://github.com/metasfresh/metasfresh/issues/2229) New Window for Product Costs in WebUI
    * New Window for Product Costs in WebUI. Allowing to view/ maintain Product Costs of different Costing Types.
  * [#2230](https://github.com/metasfresh/metasfresh/issues/2230) New Window for Fact_Acct_ActivityChangeRequest
    * New Window in WebUI for the maintenance of Activity ID changes in Documents.
  * [#2232](https://github.com/metasfresh/metasfresh/issues/2232) New Window for c_activity in WebUI
    * New Window added to WebUI Menu that allows the maintenance of Cost Center.
  * [#2234](https://github.com/metasfresh/metasfresh/issues/2234) New Window for Payment Selection in WebUI
    * New Window for the Payment Selection workflow, allowing to pay due Vendor Invoices in Batch like mode.
  * [#2235](https://github.com/metasfresh/metasfresh/issues/2235) New window for Shipper in WebUI
    * New Shipper Window in Webui to allow the maintenance of Businesspartners for Shipper Transportations.
  * [#2236](https://github.com/metasfresh/metasfresh/issues/2236) New Window for Printing Queue in WebUI
    * New window for Printing Queue maintenance in WebUI.
  * [#2245](https://github.com/metasfresh/metasfresh/issues/2245) Replace description field by memo in B2C
    * Detail adjustment in Businesspartner B2C window, changing the Field Type of description field to memo, allowing the user to record longer text.
  * [#2247](https://github.com/metasfresh/metasfresh/issues/2247) Make Request Type a search field
    * Adding the Request Type to Filter criteria in Request Window.
  * [#2258](https://github.com/metasfresh/metasfresh/issues/2258) add due date to request grid view
    * Adding the Field Due Date to the included View of Requests in B2C Customer Window in WebUI.
  * [#2267](https://github.com/metasfresh/metasfresh/issues/2267) New Window for Hardware drucker in WebUI
    * New Window for Printer Configuration and Maintenance in WebUI.
  * [#2268](https://github.com/metasfresh/metasfresh/issues/2268) New Window for Drucker-Zuordnung in WebUI
    * New Window for Printer Matching Configuration and Maintenance in WebUI.
  * [#2269](https://github.com/metasfresh/metasfresh/issues/2269) New Window for Async Batch in WebUI
    * New Window for Async Batch maintenance in WebUI.
  * [#2270](https://github.com/metasfresh/metasfresh/issues/2270) New Window for Async Batch Type in WebUI
    * New Window for the maintenance of Async Batch Types.
  * [#2289](https://github.com/metasfresh/metasfresh/issues/2289) New Window for Async Batch Parameters/ Process Audit
    * New Window for Process Audit in WebUI.
  * [#2294](https://github.com/metasfresh/metasfresh/issues/2294) Window Layout for Picking Window in WebUI
    * New generic Layout for the Picking Window in WebUI.
  * [#2305](https://github.com/metasfresh/metasfresh/issues/2305) Create GL Journal No. automatically when new
  * [#2358](https://github.com/metasfresh/metasfresh/issues/2358) translate process quotation to sales order to en_US
  * [#2364](https://github.com/metasfresh/metasfresh/issues/2364) Rename Packvorschrift-Produkt-Zuordnung to Packvorschrift
    * Renaming the Field for Packing Instructions in the de_DE Baselanguage.
  * [#2365](https://github.com/metasfresh/metasfresh/issues/2365) Rename all Windows with Suffix "kandidaten" to suffix "disposition"
    * Renaming the Candidates Window in baselanguage, now called disposition.
  * [#2370](https://github.com/metasfresh/metasfresh/issues/2370) Price Topic improvements in Product Price and Pricelist
    * Different detail improvements done to Pricing relevant topics in WebUI.
  * [#2372](https://github.com/metasfresh/metasfresh/issues/2372) Detailed Improvements in different Finance Functionalities
    * Detail Improvements added to different Finance windows and processes.
  * [#2374](https://github.com/metasfresh/metasfresh/issues/2374) Detail adjustments to Shipment Candidates Window
    * Detail adjustments to the Shipment Candidates Window adding further data manipulation functionality.
  * [#2294](https://github.com/metasfresh/metasfresh/issues/2396) copy report.properties for purchase order to dist repo

* metasfresh-webui-api
  * [#54](https://github.com/metasfresh/metasfresh-webui-api/issues/54) Implement endpoint for document cloning
    * New Functionality for Data and Docuemnts Copy/ Copy with details. Now combined both to one Action for the User.
  * [#549](https://github.com/metasfresh/metasfresh-webui-api/issues/549) Validcombination Window does not allow NEW in WebUI
    * Now allowing to create valid account combination records in WebUI.
  * [#556](https://github.com/metasfresh/metasfresh-webui-api/issues/556) Picking Window Main View adjustments
    * Detail adjustments added to the new Picking Window in WebUI.
  * [#557](https://github.com/metasfresh/metasfresh-webui-api/issues/557) Picking Window: Open HU Selection window filtering
    * New User Workflow in Picking window, now opening the Handling Unit Selection with additional filter possibilities.
  * [#560](https://github.com/metasfresh/metasfresh-webui-api/issues/560) Picking: don't allow picking included rows
    * Fine tuning of Picking Window workflow, now restricting the user from picking already picked items.

* metasfresh-webui-frontend
  * [#1157](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1157) Delete User Notifications
    * Now the user can delete own User Notifications.

## Fixes
* metasfresh-app
  * [#2237](https://github.com/metasfresh/metasfresh/issues/2237) Order Control Report Attribute missing
    * Fix/ Extension of the Order Control report. Now showing additional Attribute Values.
  * [#2260](https://github.com/metasfresh/metasfresh/issues/2260) SQL - problem to rollout master build to DB with issue-build
    * Fix to Internal Housekeeping Issue during rollout to Database in Build process.
  * [#2345](https://github.com/metasfresh/metasfresh/issues/2345) java.math.BigDecimal Exception when using process quotation to sales order
    * Fix for the Quotation to Sales Order Action.
  * [#2349](https://github.com/metasfresh/metasfresh/issues/2349) Fix AD_Column of C_BPartner.AD_OrgBP_ID
  * [#2367](https://github.com/metasfresh/metasfresh/issues/2367) error on creating receipt

* metasfresh-webui-api
  * [#528](https://github.com/metasfresh/metasfresh-webui-api/issues/528) Exception when opening PP Order issue / receipt
    * Fixes an Exception in Manufaturing Orders action Issue/ Recept.
  * [#551](https://github.com/metasfresh/metasfresh-webui-api/issues/551) verify if lookup value is still valid
    * New Verification in Lookup Fields, now verifying that the included values are still valid.
  * [#553](https://github.com/metasfresh/metasfresh-webui-api/issues/553) webui notification-test fails
    * Fix for the automated Test of User Notifications in Continuous Integration workflow.
  * [#561](https://github.com/metasfresh/metasfresh-webui-api/issues/561) Handling Unit Editor Materialentnahme Bug
    * Fixes an Issue during Internal Usage workflow via Handling Unit Editor.
  * [#567](https://github.com/metasfresh/metasfresh-webui-api/issues/567) Shipped HUs are not displayed correctly
  * [#575](https://github.com/metasfresh/metasfresh-webui-api/issues/575) HU does not disappear after Correction Quick-action.
  * [#579](https://github.com/metasfresh/metasfresh-webui-api/issues/579) NPE when you issue HUs

* metasfresh-webui-frontend
  * [#1142](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1142) issue panel closes right after you issue a product
    * Not closing the issue panel anymore automatically after performing the action issue for a manufacturing order.
  * [#1161](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1161) tab tries to update a readonly field
  * [#1174](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1174) picking: weird issues with included view
    * Stabilization of the new Picking window, now avioding some minor weird behavior.


# metasfresh 5.24 (2017-35)

**release for week 2017-35**

## Features

* metasfresh-app
  * [#2036](https://github.com/metasfresh/metasfresh/issues/2036) Picking Slot Label
    * New Feature, allowing to create a temporary Label for a dynamicly reserved Picking Slot.
  * [#2050](https://github.com/metasfresh/metasfresh/issues/2050) Window Design WebUI : Purchase candidate
    * Adopting the Purchase Candidate Window to the current Design Guidelines.
  * [#2103](https://github.com/metasfresh/metasfresh/issues/2103) Create and implement the usage of a pdf printer
    * New Functionality that allows to create PDF Files w/ more than 1 document per File.
  * [#2109](https://github.com/metasfresh/metasfresh/issues/2109) Move Currency Records from System to Client level so user can edit them
    * Making the Currency Records editable w/ moving them from System to Client Level.
  * [#2116](https://github.com/metasfresh/metasfresh/issues/2116) Use handler when matching partner in ESR import
    * Improvement of ESR Payment Handler when matching a Businesspartner during Import process.
  * [#2144](https://github.com/metasfresh/metasfresh/issues/2144) Action for Warehouse Movements
    * New Action in handling Unit Editory, allowing the User to fast move selected Handling Units to another Warehouses. Material Movement Documents are created automatically on the fly.
  * [#2154](https://github.com/metasfresh/metasfresh/issues/2154) Window Design WebUI: Customer Returns Improvements
    * Improvements to the Cutomer Returns Window adapting to our current Design Guidelines.
  * [#2169](https://github.com/metasfresh/metasfresh/issues/2169) Window Design WebUI : Attributes
    * Adopting the Attributes Window to the current Design Guidelines.
  * [#2170](https://github.com/metasfresh/metasfresh/issues/2170) Window Design WebUI : Bill of Materials
    * Adopting the Bill of Material Window to the current Design Guidelines.
  * [#2173](https://github.com/metasfresh/metasfresh/issues/2173) Window Design WebUI : Discount Schema
    * Adopting the Discount Schema Window to the current Design Guidelines.
  * [#2174](https://github.com/metasfresh/metasfresh/issues/2174) Window Design WebUI : Unit of Measure
    * Adopting the Product Unit of measure Window to the current Design Guidelines.
  * [#2175](https://github.com/metasfresh/metasfresh/issues/2175) Window Design WebUI : Product Category
    * Adopting the Product Category Window to the current Design Guidelines.
  * [#2183](https://github.com/metasfresh/metasfresh/issues/2183) Window Design WebUI : Sales Order
    * Adopting the Sales Order Window to the current Design Guidelines.
  * [#2184](https://github.com/metasfresh/metasfresh/issues/2184) Window Design WebUI : Order Candidates
    * Minor change in Order Candidates Window removing processed Flag from Advanced Edit.
  * [#2192](https://github.com/metasfresh/metasfresh/issues/2192) Window Design WebUI : Business Partner Group Improvements
    * Adjustments added to Business Partner Group Window, adapting it to current design Guidelines.
  * [#2193](https://github.com/metasfresh/metasfresh/issues/2193) Window Design WebUI: Vendor Returns Improvements
    * Adaption of Vendor Returns Window to current WebUI Guidelines.
  * [#2194](https://github.com/metasfresh/metasfresh/issues/2194) Shipment Candidates dropship vendor ID missing
    * Detail Adjustments to Shipment Schedule Window, improving to guidelines adoption.
  * [#2196](https://github.com/metasfresh/metasfresh/issues/2196) Window Design WebUI : Bank Statement
    * Minor detail and adjustments and adding missing Translations to the Product window.
  * [#2198](https://github.com/metasfresh/metasfresh/issues/2198) New Window for filtered Bank Statement Line References
    * New prototype Window for filtered Bank Statement References.
  * [#2200](https://github.com/metasfresh/metasfresh/issues/2200) Window Design WebUI : Payment
    * Additional detailed adjustments to the Payment Window in WebUI, adopting to current design Guidelines.
  * [#2201](https://github.com/metasfresh/metasfresh/issues/2201) Window Design WebUI : GL Journal
    * Adding the GL Journal Window Layout to WebUI.
  * [#2202](https://github.com/metasfresh/metasfresh/issues/2202) Window Design WebUI : Tour Version
    * Minor detail adjustments, missing Translations to the Tour Version Window in WebUI.
  * [#2203](https://github.com/metasfresh/metasfresh/issues/2203) Window Design WebUI : Delivery Days
    * Adapting the Delivery Days Window to the current Design Guidelines.
  * [#2204](https://github.com/metasfresh/metasfresh/issues/2204) Window Design WebUI : Contract
    * Adopting the Contract Window in Webui to the current Design Guidelines.
  * [#2207](https://github.com/metasfresh/metasfresh/issues/2207) Window Design WebUI : Product
    * Adding adjustments to the Product window in WebUI adapting it to our current design guidelines.
  * [#2217](https://github.com/metasfresh/metasfresh/issues/2217) Window Design WebUI : Tour Version Improvements
    * Improvement of the Tour Version Window in WebUI, details changed to fit better to design guidlelines.
  * [#2223](https://github.com/metasfresh/metasfresh/issues/2223) Support longer numbers on documents
    * Order and Invoice Jasper Resports now allow prices with up to 6 digits.
  * [#2226](https://github.com/metasfresh/metasfresh/issues/2226) New Window for GL_Journal in WebUI
    * New Window for GL Journal in WebUI, without Journal Batch level.
  * [#2255](https://github.com/metasfresh/metasfresh/issues/2255) Revamp Validcombination Window
    * Adjustments done to Validcombination Window in WebUI, adapting to current design Guidelines.
  * [#2231](https://github.com/metasfresh/metasfresh/issues/2231) Add Import Partner and Product Processes Available in Import Windows
    * Now allowing to use the Import Partner and Import Product processes in Import windows in WebUI via action Menu.
  * [#2136](https://github.com/metasfresh/metasfresh/issues/2136) add history to B2C window
    * New Communication history in B2C Businesspartner included Tab.

* metasfresh-webui-api
  * [#545](https://github.com/metasfresh/metasfresh-webui-api/issues/545) Notification Persisting
    * Now persisting the User Notifications.
  * [#546](https://github.com/metasfresh/metasfresh-webui-api/issues/546) Notification Deleting
    * Functionality to delete your own User Notifications.

* metasfresh-webui-frontend
  * [#1021](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1021) Combined Filter Functionality
    * New Functionality that allows the combination of different Filters as search queries in generic grid view Windows.

## Fixes

* metasfresh-app
  * [#2112](https://github.com/metasfresh/metasfresh/issues/2112) Reversing a vendor-return-InOut needs to reverse the HU states
    * Now recreating the initial Handling Unit state after reverting a vendor return
  * [#2142](https://github.com/metasfresh/metasfresh/issues/2142) Customer Returns error w/ create from Shipment in WebUI
    * Fix for the Customer Returns Functionality, when creating it from Shipment window.
  * [#2143](https://github.com/metasfresh/metasfresh/issues/2143) Disposal reverse-correct does not recreate the HU
    * Now recreating the initial Handling Unit state after reverting a Disposal Document.

* metasfresh-webui-frontend
  * [#1140](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1140) Lookup Workflow for Batch Entry not correct
    * Fix for the Lookup Workflow in Batch Entry mode.
  * [#1150](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1150) error reappears after you changed window
    * Fix for the case that errors from one window also appeared again when switching to another one.

# metasfresh 5.23 (2017-34)

**release for week 2017-34**

## Features
* metasfresh-app
  * [#2048](https://github.com/metasfresh/metasfresh/issues/2048) Window Design WebUI: Sales Order Candidates
    * New Window Sales Order Candidates for WebUI.
  * [#2051](https://github.com/metasfresh/metasfresh/issues/2051) Window Design WebUI: Material Tracking
    * Adjusted Window Configuration for Material Tracking in WebUI, including Translations for en_US.
  * [#2084](https://github.com/metasfresh/metasfresh/issues/2084) Window Design WebUI: Vendor Returns
    * Adjusted Window Configuration for Vendor Returns Window in WebUI.
  * [#2134](https://github.com/metasfresh/metasfresh/issues/2134) Export all webui AD_Messages from w101
    * Migrating the current static elements from w101 to base.
  * [#2140](https://github.com/metasfresh/metasfresh/issues/2140) Window Design WebUI: Shipment Schedule
    * Adjusted Window Configuration for Shipment Schedule in WebUI, including Translations for en_US.
  * [#2150](https://github.com/metasfresh/metasfresh/issues/2150) Window Design WebUI: Shipment
    * Adjusted Window Configuration for Shipment Window in WebUI, including Translations for en_US.
  * [#2159](https://github.com/metasfresh/metasfresh/issues/2159) Add date field to request
    * New updateable Field in Request Window, allowing the user to maintain the Request Date seperately from DateCreated.
  * [#2161](https://github.com/metasfresh/metasfresh/issues/2161) Window Design WebUI : Business Partner Group
    * Adjusted Window Configuration for Businesspartner Group Window in WebUI, adopting the current window Guidelines.
  * [#2162](https://github.com/metasfresh/metasfresh/issues/2162) Window Design WebUI : Outbound Documents Config
    * Adjusted Window Configuration for the Document Outbound Config Window in WebUI. Adding Translations.
  * [#2167](https://github.com/metasfresh/metasfresh/issues/2167) Window Design WebUI : Product
    * Adjusted Window Configuration for Product Window in WebUI. Main View and Advanced Edit.

* metasfresh-webui-frontend
  * [#1139](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1139) Ellipses when breadcrumb too long & Tooltip
    * Adjustment to the Breadcrumb navigation. Now adding ellipses when the header exceeds the possible space.

## Fixes
* metasfresh-app
  * [#2163](https://github.com/metasfresh/metasfresh/issues/2163) Do not show new button in B2C tab customer
    * Minor Fix in the new Businesspartner B2C window, now not showing the new Button for Included Tab Customer/ Vendor anymore.
* metasfresh-webui-frontend
  * [#1071](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1071) Fix frontend memory leaks
    * Fixes Memory Leaks in WebUI.
  * [#1129](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1129) cannot receive second time
    * Fix for Bug in Action Receipt o Manufacturing Window in WebUI.
  * [#1130](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1130) cursor jumps from qty CU to LU field
    * Fixes a focus Bug in lookup Field Workflow.
  * [#1135](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1135) Browse Whole Tree Caption not refreshed
    * Fix for the Sitemap Message in Navigation Menu. Now receiving a translated Message when changing the locale in Avatar Settings.
  * [#1141](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1141) Sort indicator triangle missing in Grid Header
    * Fix for the new Tooltip and ellipses functionality in Grid Header. Now showing the Sort indicator again.

# metasfresh 5.22 (2017-33)

**release for week 2017-33**

## Features
* metasfresh-app
  * [#2110](https://github.com/metasfresh/metasfresh/issues/2110) make version/build infos more transparent
    * Adding the build/ version infos of the metasfresh applications to spring admin and to file build-info.properties (in dist folder), allowing to have a better overview about the configuration.
  * [#2111](https://github.com/metasfresh/metasfresh/issues/2111) Store version number in DB after successful sql remote
    * Nice improvement of sql remote. Now storning the version number in Database and avoiding unneccesary invocations of sql remote, which speeds up the db migration process a lot.
  * [#2118](https://github.com/metasfresh/metasfresh/issues/2118) ESR - add sysconfig to ignore non-credit lines
    * Improvement of the ESR Import action, now processing all ESR Lines also when individual Lines fail.
  * [#2119](https://github.com/metasfresh/metasfresh/issues/2119) ESR - Show actual number of transactions
    * Adding a Transaction control amount to ESR Imports coming from SEPA camt54 file and presenting to the user.
  * [#2122](https://github.com/metasfresh/metasfresh/issues/2122) allow creating requests from B2C window with new button
    * Now it's able to create Requests from Businesspartner B2C WIndow directly via new Button.

* metasfresh-webui-frontend
  * [#1117](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1117) Cut-Off Header Names in Subtabs
    * Adjusting the Header Names of Grid View Columns in included Subtabs. Now adding elipses when Header Names are too long. Via Hover the user is able to read the Full Name via Tooltip.
  * [#1127](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1127) Allow copy to clipboard of selected grid lines
    * New feature to select Grid View lines and copy/ paste as comma seperated data.

## Fixes
* metasfresh-app
  * [#2121](https://github.com/metasfresh/metasfresh/issues/2121) Can't save purchase order
    * Fixes a minor Bug that occured in Purchase Order under certain circumstances, avoiding the record save.

* metasfresh-webui-frontend
  * [#1066](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1066) board: when adding a new card, the card does not vanish from new cards view
    * Fix for the new Kanban Board. Now the cards are removed from card selection List, as soon as they are dragged & dropped on the Board.
  * [#1121](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1121) Localize remaining static elements
    * Fix localization of missing phrases related to attachment upload
  * [#1124](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1124) issue in manufacturing won't close
    * Fix in Window for Manufacturing Issue and Receipt.

* others
  * [#17](https://github.com/metasfresh/metasfresh-dist-orgs/issues/17) activate role webui
    * Activation of Role WebUI in dist orgs environment.

# metasfresh 5.21 (2017-32)

**release for week 2017-32**

## Features
* metasfresh-app
  * [#1771](https://github.com/metasfresh/metasfresh/issues/1771) add legacy features
    * Adding missing features from legacy systems to current metasfresh codebase.
  * [#2070](https://github.com/metasfresh/metasfresh/issues/2070) Window Design WebUI: Product
    * Adopting the Product Window to the current Window Concept for Master Data in metasfresh WebUI
  * [#2080](https://github.com/metasfresh/metasfresh/issues/2080) Window Design WebUI: Payment Allocation Translation en_US
    * Adopting the Payment Allocation Window to the current Window Concept for Documents in metasfresh WebUI
  * [#2086](https://github.com/metasfresh/metasfresh/issues/2086) Sales Order Advanced Edit Window in main View
    * Adopting the Sales Order Window to the current Window Concept for Documents in metasfresh WebUI
  * [#2087](https://github.com/metasfresh/metasfresh/issues/2087) Purchase Order Advanced Edit Window in main View
    * Adopting the Purchase Order Window to the current Window Concept for Documents in metasfresh WebUI
  * [#2096](https://github.com/metasfresh/metasfresh/issues/2096) Businesspartner B2C Location Editor missing
    * Adjustments for the Businesspartner B2C Window, adding missing Fields and Layout.
  * [#2101](https://github.com/metasfresh/metasfresh/issues/2101) webui - allow disabling auto-close of picking candidates
    * New Switch to influence the closing behavior of picking candidates.

* metasfresh-webui-frontend
  * [#977](https://github.com/metasfresh/metasfresh-webui-frontend/issues/977) Add frontend support for modifying the KPI config (caption, offset, etc)
    * New Functionality allowing the user to adjust KPI Settings via Frontend.
  * [#1051](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1051) Cut-Off Header names
    * Adjusting the Header Names of Grid View Columns. Now adding elipses when Header Names are too long. Via Hover the user is able to read the Full Name via Tooltip.
  * [#1097](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1097) Show progress when uploading big files
    * New Functionality when uploading Files. Now showing a progress Bar and allowing the user to proceed working during upload.

## Fixes
* metasfresh-app
  * [#2106](https://github.com/metasfresh/metasfresh/issues/2106) NullPointerException on CAMT 54 Import
    * Fixes a NPE Bug that happened in minor cases when importing a camt54 sepa file.
  * [#2107](https://github.com/metasfresh/metasfresh/issues/2107) Error: Missing ESR creditor reference on CAMT 54 Import
    * Fixes a Bug in new camt54 import which failed because mandatory ESR Reference Field is missing in sepa file.

* metasfresh-webui-frontend
  * [#1000](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1000) Cursor Missing after [tab] in BPartner Relation Window
    * Fix for Focus after TAB from and into Lookup Fields.
  * [#1006](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1006) row selection in picking
    * When opening the picking window intitially in Grid View the first Row is now selected.
  * [#1048](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1048) POST .../documentView/{windowId} is called twice when opening a view
    * Now Minimizing the amount of calls for documentView/{windowId} endpoint, which improves the performance of the overall System.
  * [#1063](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1063) view: when refreshing the page the data is loaded twice
    * Reducing the load of data after refreshing a page improving the load times of views.
  * [#1077](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1077) Sitemap Navigation [arrow-up] not working
    * Fixes a Bug in the Keyboard Navigation in Sitemap. Now allowing the user to use the arrow-up key again.
  * [#1089](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1089) Avatar Tooltip opens and stays open after selecting Avatar Settings
    * Fixes a Bug in Avatar Tooltip which remained open after selecting the Avatar Settings.
  * [#1095](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1095) Sales Order Batch Entry with Key Shortcut not working
    * Fix for Keyborad Navigation Shortcut starting the Batch Entry in Document Views (where available).
  * [#1099](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1099) Browse whole tree is not translated in DE
    * New Translation for "Sitemap Menu" of static element in WebUI Sitemap.
  * [#1098](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1098) Cant select last entry in dropdown list
    * Fix for Selection Lists, now allowing to also select the last entry of a List.
  * [#1111](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1111) Lookup Selection Lists not shown in Sequence anymore
    * Fixes a Bug in Lookup Fields now showing the wanted selection List sequence when defining included Fields in a Lookup.
  * [#1114](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1114) caught in an empties loop when opened from material receipt candidates
    * Fixes an Navigation/ Workflow Error that occured when creating empties receive from Receiptcandidates.

# metasfresh 5.20 (2017-31)

## Features
* metasfresh-app
  * [#1994](https://github.com/metasfresh/metasfresh/issues/1994) Adjust Sales Invoice Window
    * Finetuning for the Sales Invoice Window to adjust the Layout to meet our current Look And Feel concept.
  * [#2019](https://github.com/metasfresh/metasfresh/issues/2019) Add Widget Size to System Elements
    * New Feature in metasfresh Application Dictionary, allowing the admin to define default widget sizes per System Element.
  * [#2029](https://github.com/metasfresh/metasfresh/issues/2029) New Window for Accounts and Elements in WebUI
    * New Window for the maintenance of Account Elements in WebUI
  * [#2032](https://github.com/metasfresh/metasfresh/issues/2032) Layout for Handling Unit Editor Window in WebUI
    * Refined Layout for the Detail Views in Handling Unit Editor Window of WebUI.
  * [#2042](https://github.com/metasfresh/metasfresh/issues/2042) Process to Update Widget Size in all child UI Elements of a System Element
    * New Process to update all Widget Child Elements of a given System Element.
  * [#2046](https://github.com/metasfresh/metasfresh/issues/2046) Window Design WebUI: Attribute Set
    * Adding the refined Layout for the Attribute Set Window in WebUI.
  * [#2049](https://github.com/metasfresh/metasfresh/issues/2049) Window Design WebUI: Purchase Order
    * Adopting the Purchase Order Window to the current Window Concept for Documents in metasfresh WebUI
  * [#2052](https://github.com/metasfresh/metasfresh/issues/2052) Window Design WebUI: Price System
    * Adopting the Price System Window to the current Window Concept for Master Data in metasfresh WebUI
  * [#2053](https://github.com/metasfresh/metasfresh/issues/2053) Window Design WebUI: Inventory Move
    * Adopting the Inventory Move Window to the current Window Concept for Documents in metasfresh WebUI
  * [#2058](https://github.com/metasfresh/metasfresh/issues/2058) Change AD_Table_ID Identifier to Name only
    * Changing the Identifier for AD_Table from TableName + Name to Name only. This allows a better readability for the user.
  * [#2066](https://github.com/metasfresh/metasfresh/issues/2066) CRM: Businesspartner B2C Reminder Functionality
    *  New CRM Feature in Businesspartner B2C Window, allowing to set reminder Dates via Request Actions for Sales Responsible and Sales Representative.
  * [#2067](https://github.com/metasfresh/metasfresh/issues/2067) switch between C_Order_MFGWarehouse with and without barcode
    * New Feture that allows to switch between Warehouses in Manufacturing with and without Barcodes.
  * [#2069](https://github.com/metasfresh/metasfresh/issues/2069) Window Design WebUI: Bill of Materials
    * Adopting the Bill of Materials Window to the current Window Concept for Master Data in metasfresh WebUI
  * [#2071](https://github.com/metasfresh/metasfresh/issues/2071) Window Design WebUI: Payment Window
    * Adopting the Payment Window to the current Window Concept for Documents in metasfresh WebUI
  * [#2074](https://github.com/metasfresh/metasfresh/issues/2074) CRM: Document List in Subtab Functionality
    * New Feature in the Businesspartner B2C Window. Including a new Included Tab, Data Structure and Functionality for CRM Documents.
  * [#2077](https://github.com/metasfresh/metasfresh/issues/2077) Window Design WebUI: Payment Allocation Window
    * Adopting the Payment Allocation Window to the current Window Concept for Documents in metasfresh WebUI
  * [#2083](https://github.com/metasfresh/metasfresh/issues/2083) Window Design WebUI: Customer Returns
    * Adopting the Customer Returns Window to the current Window Concept for Documents in metasfresh WebUI
  * [#2092](https://github.com/metasfresh/metasfresh/issues/2092) Scheduler - improve error handling if AD_Role_ID can't be found
    * Improving the User Experience with better messages when the Scheduler fails not able to receive the User Role for running a process.
  * [#2096](https://github.com/metasfresh/metasfresh/issues/2096) Businesspartner B2C Location Editor missing
    * Adjustments for the Businesspartner B2C Window, adding missing Fields and Layout.

* metasfresh-webui-api
  * [#503](https://github.com/metasfresh/metasfresh-webui-api/issues/503) Picking prototype (v5)
    * Further Improvements of the Picking API now in Version 5. Belongs to the Development of the new Picking Window in WebUI.
  * [#514](https://github.com/metasfresh/metasfresh-webui-api/issues/514) Support HU transform from HU window
    * Improvement od the Handling Unit Transform Action in Handling Unit Editor Window.
  * [#518](https://github.com/metasfresh/metasfresh-webui-api/issues/518) Picking prototype (v6)
    * Further Improvements of the Picking API now in Version 5. Belongs to the Development of the new Picking Window in WebUI.

* metasfresh-webui-frontend
  * [#982](https://github.com/metasfresh/metasfresh-webui-frontend/issues/982) Functionality to add Actions for Subtab Data
    * New Functionality now allowing to also define actions to included subtabs and use them via Action Menu.
  * [#1031](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1031) Autoselect when doubleclick on included Tab Row Field
    * New Feature wanted heaviliy by Power Users. Now it's possible to directly edit the field content after double clicking a field in Included Tab Grid View.
  * [#1061](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1061) Larger Avatar Menu Photo
    * Resizing of the Avatar Image allowing to better differentiate the User Sessions on smaller Screens.
  * [#1084](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1084) Handle websocket document stale event
    * New internal Functionality to only refresh a Document without all included Tabs when needed. This avoids the reload of mass data although nothing has changed in Subdata.

## Fixes
* metasfresh-app

* metasfresh-webui-api
  * [#496](https://github.com/metasfresh/metasfresh-webui-api/issues/496) String document IDs cannot be converted to int Handling Unit Editor
    * Fixes a Bug that occurred in Handling Unit Editor for String Document No being a String instead of an Integer.
  * [#511](https://github.com/metasfresh/metasfresh-webui-api/issues/511) Problems with Sales Opportunities Board
    * Fix for the Sales Opportunity Board, now removing Cards from the New Selection List that were previously added to the Board.
  * [#516](https://github.com/metasfresh/metasfresh-webui-api/issues/516) moving 1 TU from one LU to another
    * Fixes a refresh issue in Handling Unit Editor when transforming 1 Transport Unit to other existing Logistic Unit.

* metasfresh-webui-frontend
  * [#1026](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1026) grid: Get rid of double scrollbar
    * Fix the double Scrollbar Issue in new Picking Window. Now only showing 1 Scrollbar when needed.
  * [#1027](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1027) line looks selected, but it's not
    * Fixes a minor Bug that presented a row as seleted although it wasn't.
  * [#1045](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1045) frontend shall switch to offline mode on http 503 error
    * Fix for the case that a 503 error is returned. Now the frontend switches to offline mode.
  * [#1046](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1046) opening included view from action not working in picking
    * Fix in Picking Window now also opening included views there too.
  * [#1052](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1052) Breadcrumb of "Sales Opportunities" board is incorrect
    * Fixes the Breadcrumb menu in Windows that are in 2nd level hierarchy in menu tree.
  * [#1062](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1062) Avatar Foto stretches when not Square
    * Fix for the Avatar Photo that was presented in a stretched way when the uploaded Photo was not one with square dimensions.
  * [#1065](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1065) Window title wrong for windows in deeper menu hierarchy
    * Fix for the Window Name in Action Menu, that was not shown properly for Windows in deeper Menu Hierarchy.
  * [#1067](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1067) board: add new: don't show the loading icon after data is loaded
    * Fix for the new Board Functionality, now not showing the Loading Icon after the card data is loaded.
  * [#1068](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1068) board: when there are no new cards show the empty result text/hint
    * Better Transparency for the user after leaving a card selection List empty. Now showing a empty result hint to the user.
  * [#1073](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1073) Bug in Advanced edit of Purchase order
    * Fix for the Advanced Edit Mode in Window Purchase Order, that sometimes did not open.
  * [#1078](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1078) Wrong quickActions call when opening HU editor
    * Fix for action calls when opening the Handling Unit Editor initially.
  * [#1094](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1094) Error on document websocket event
    * Fix for a minor error on document websocket events.

# metasfresh 5.19 (2017-30)

## Features
* metasfresh-app
  * [#525](https://github.com/metasfresh/metasfresh/issues/525) Retraceability Transparency for Product Lifecycle
    * New improved Functionality for retraceability in mtefasresh. Now allowing to analyze the material paths in no time.
  * [#1993](https://github.com/metasfresh/metasfresh/issues/1993) Set default Widget width for certain Columns
    * Refining and Harmonizing the Widget width of columns in GridView for those, where generic width according to their widget type does not match to the user requirement or field content.
  * [#1995](https://github.com/metasfresh/metasfresh/issues/1995) Language Names too Long
    * Renaming of Language Names, avoiding them to get too long because of Country Name. Now added the 2-digit iso country code instead.
  * [#1996](https://github.com/metasfresh/metasfresh/issues/1996) Take out all AD_Client_ID from GridView in WebUI
    * Rearranging GridView in general, taking out the client column from there to free space for other more important columns. Harmonizing oever all GridViews.
  * [#2001](https://github.com/metasfresh/metasfresh/issues/2001) Add AD_Org_ID to all default Filters
    * Including the Organisation and other fields to all generic filter selections. Removing long text fields from filters.
  * [#2007](https://github.com/metasfresh/metasfresh/issues/2007) Feedback WebUI CRM
    * Adjustments to various CRM windows. Adding missing Fields, Optimizing Column widths.
  * [#2009](https://github.com/metasfresh/metasfresh/issues/2009) Feedback WebUI Prices
    * Further Layout Adjustments to Pricelist and Product Price window in WebUI.
  * [#2012](https://github.com/metasfresh/metasfresh/issues/2012) import messages
    * New import of static massages for webUI.
  * [#2015](https://github.com/metasfresh/metasfresh/issues/2015) Create Window Price List Schema in WebUI
    * Adjusting the Layout for Price List Schema Window in WebUI
  * [#2017](https://github.com/metasfresh/metasfresh/issues/2017) Create Window Pricing Rule in WebUI
    * Adding the 2-column Layout for The Pricing Rule Window in WebUI.
  * [#2020](https://github.com/metasfresh/metasfresh/issues/2020) Add System Configuration Window to WebUI
    * New Window for System Configuration in WebUI.
  * [#2022](https://github.com/metasfresh/metasfresh/issues/2022) Create Window Textvariablen in WebUI
    * New Window for Boilerplate Variables in WebUI.
  * [#2023](https://github.com/metasfresh/metasfresh/issues/2023) Create Window Textbausteinein WebUI
    * New Window for Boilerplate Text Elements in WebUI.
  * [#2027](https://github.com/metasfresh/metasfresh/issues/2027) Fine Tuning of Window Bankaccount in WebUI
    * Various Adjustments to the Bankaccount Window in WebUI.
  * [#2028](https://github.com/metasfresh/metasfresh/issues/2028) New Window for Validcombination in WebUI
    * New Window in WebUI thats allows the user to maintain the valid Account Combinations.
  * [#2038](https://github.com/metasfresh/metasfresh/issues/2038) Adjusted Business Partner Window for Small Companies
    * New Business Partner Window for B2C Companies.

* metasfresh-webui-api
  * [#506](https://github.com/metasfresh/metasfresh-webui-api/issues/506) view: accept included referencing documents when creating a new view
    * Providing the REST-API for referenced Documents from Zoom-To in included Tabs
  * [#509](https://github.com/metasfresh/metasfresh-webui-api/issues/509) DocumentIdsSelection.stream() and other methods shall work with "ALL"
    * General clarification and a fix for the concrete problem

* metasfresh-webui-frontend
  * [#999](https://github.com/metasfresh/metasfresh-webui-frontend/issues/999) grid view: use keyboard + and - to expand/collapse included rows
    * New Feature allowing the usage of keyboard to expand/ collapse included rows in Grid Table.
  * [#1007](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1007) eMail Icon in Action Menu
    * Adding the eMail icon to metasfresh WebUI Action Menu.
  * [#1029](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1029) keyboard shortcut and tooltip for button in filters
    * New Keyboard Navigation for Apply Button in Filter selections.
  * [#1030](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1030) Directly display quick actions for the first row of a modal window
    * Autoselection and Quickaction activation for first row of newly opened Window in GridView.
  * [#1038](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1038) Flickering effect in Picking Terminal
    * Eliminating the Flickering Effect in the new Picking Terminal when navigating through the picking lines.


## Fixes
* metasfresh-app
  * [#2005](https://github.com/metasfresh/metasfresh/issues/2005) invoiced and delivered status in order
    * Updating the the invoiced and delivered Status in Orders again.
  * [#2013](https://github.com/metasfresh/metasfresh/issues/2013) Broken Included Tabs to be redone
    * Fixes a broken Included Tab in the Manufacturing Order Window

* metasfresh-webui-api
  * [#1982](https://github.com/metasfresh/metasfresh/issues/1982) partial issue problem
    * Fixes an error in Action Issue of Manufacturing when patially issueing.

* metasfresh-webui-frontend
  * [#1009](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1009) Window Name missing in Action Menu
    * Fixes a Bug not showing the Window Name in Action Menu nor the possibility to set a Bookmark for the window.
  * [#1015](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1015) Uncaught TypeError: Cannot read property 'className' of null
    * Fixes an issue that occured when using the scoll bar in Handling Unit Editor Window.
  * [#1028](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1028) Tooltip when hovering GridView Header
    * Fix for the Grid Header Tooltip, that was not shown anymore.
  * [#1030](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1030) Directly display quick actions for the first row of a modal window
    * Bugfix for the Quickaction behavior in modal overlay when opened and first row is initially selected.
  * [#1040](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1040) Readonly field Contact looks editable
    * Fix for Fields that look editable but are readonly.
  * [#1041](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1041) included tab: when zoom into the new window opens with error
    * Error solved when using Zoom-To in new Tab and window


# metasfresh 5.18 (2017-29)

## Features
* metasfresh-app
  * [#484](https://github.com/metasfresh/metasfresh/issues/484) HU traceability (backend)
    * First Backend Implementation of the new Retraceability Feature allowing the user to backtrack where specific material & components came from and where went to.
  * [#1604](https://github.com/metasfresh/metasfresh/issues/1604) Enhance Material disposal (garbage)
    * Further Improvements of the Material disposal functionality.
  * [#1874](https://github.com/metasfresh/metasfresh/issues/1874) Material Receipt multiselect Close Lines
    * New Functionality in Material Receipt, allowing the user to close multiple lines at once.
  * [#1929](https://github.com/metasfresh/metasfresh/issues/1929) Translate & export webui messages
    * Migrating the latest messages for static webUI elements.
  * [#1938](https://github.com/metasfresh/metasfresh/issues/1938) Adjust Window Greeting in WebUI
    * Adding improvements to the Greeting Window in Form of 2-column Layout and Translations.
  * [#1939](https://github.com/metasfresh/metasfresh/issues/1939) Adjust the Business Partner Relations Window
    * New Window to allow Business Partner Relations maintenance in WebUI.
  * [#1940](https://github.com/metasfresh/metasfresh/issues/1940) Subtabs Layout for Business Partner Window
    * Refining the Business Partner Window Subtabs to Current Layout concept. Adding Translations and Renaming Fields.
  * [#1941](https://github.com/metasfresh/metasfresh/issues/1941) Subtab Layout for Window Outbound Document
    * Adjustment of the Doc Outbound Window. Layout changes for Log Subtab, adding Translations and renaming Fields.
  * [#1942](https://github.com/metasfresh/metasfresh/issues/1942) Payment Terms Window in WebUI
    * Adjustment of Payment Terms Window with harmonized Layout. Adding Translations for en_US.
  * [#1943](https://github.com/metasfresh/metasfresh/issues/1943) Window Business Partner Group Advanced Edit
    * Improvement of the advanced edit mode in Business Partner Group window in WebUI.
  * [#1944](https://github.com/metasfresh/metasfresh/issues/1944) Request Type Window adjustments
    * Minor Adjustments to Request Type Window in WebUI, renaming Fields, adding Translations, Layout improvement in GridView.
  * [#1967](https://github.com/metasfresh/metasfresh/issues/1967) show dropship in trace report
    * Adding dropship references to vendor/ customer trace report
  * [#1969](https://github.com/metasfresh/metasfresh/issues/1969) Adjustment on Average Purchase Prices Function
    * Enhancement for the Average Purchase Price Function, now returning additional Information like DocumentNo and Receiptdate if available.
  * [#1981](https://github.com/metasfresh/metasfresh/issues/1981) Add Docstatus to Grid View in Empties Receive
    * Enhancing the presented Information in the Grid View of Empties Windows (Return & Receive).
  * [#1986](https://github.com/metasfresh/metasfresh/issues/1986) Internal Use Action for Handling Unit Editor WebUI
    * New Action in Handling Unit Editor to allow Internal Use of Handling Units.

* metasfresh-webui-api
  * [#495](https://github.com/metasfresh/metasfresh-webui-api/issues/495) API: Functionality to add Actions for Subtab Data
    * Provides the API Endpoints for actions used in included subtabs. Shall be made available in Frontend via action menu as soon a subtab record is selected.
  * [#501](https://github.com/metasfresh/metasfresh-webui-api/issues/501) Picking prototype (v4)
    * Next iteration of the picking terminal prototype in WebUI.

* metasfresh-webui-frontend
  * [#836](https://github.com/metasfresh/metasfresh-webui-frontend/issues/836) Outbound eMail functionality
    * First Iteration of new eMail Editor for Outbound communication in WebUI
  * [#919](https://github.com/metasfresh/metasfresh-webui-frontend/issues/919) Keyboard shortcut for Quickaction dropdown
    * New Shortcut added for the Quickaction Dropdown Menu, allowing a better keyboard navigation.
  * [#930](https://github.com/metasfresh/metasfresh-webui-frontend/issues/930) Refactor/unify Menu and Sitemap
    * Unifying the the Navigation Menus and Sitemap components for future use and better maintainability.
  * [#942](https://github.com/metasfresh/metasfresh-webui-frontend/issues/942) grid view: automatically open the included view when clicking on a row
    * New Fuctionality to directly open an included view when deleting a row in Grid View.
  * [#973](https://github.com/metasfresh/metasfresh-webui-frontend/issues/973) Disable/ Enable on quick actions do not happen smoothly
    * Now having a smooth transition of action button content when starting an action, avoiding a flickering effect.
  * [#992](https://github.com/metasfresh/metasfresh-webui-frontend/issues/992) Disable quick action dropdown button while running the action
    * Improvement of the usage of Actions. When starting an action, then disabling teh action button for the time the started action is running.

## Fixes
* metasfresh-app
  * [#1970](https://github.com/metasfresh/metasfresh/issues/1970) MatchInv not posting properly
    * Fix for MatchInv Accounting of non service Products

* metasfresh-webui-frontend
  * [#891](https://github.com/metasfresh/metasfresh-webui-frontend/issues/891) Blocked cells
    * Improvement of metasfresh Browser compatibility. Here adjusting the behavior of blocked cells when using Edge Browser.
  * [#948](https://github.com/metasfresh/metasfresh-webui-frontend/issues/948) Define refactoring plan for filters model
    * Improvement of Filtering model in metasfresh.
  * [#966](https://github.com/metasfresh/metasfresh-webui-frontend/issues/966) sitemap: menu item incorrectly flagged as favorite in search result
    * Fix for the User Bookmark menu, fixing a minor case that showed the a menu item incorrectly as favorite.
  * [#978](https://github.com/metasfresh/metasfresh-webui-frontend/issues/978) Make Avatar and Notifications accessible in Edit mode
    * Fixes the Avatar and Notifications menu in action windows line Picking Terminal and others.
  * [#979](https://github.com/metasfresh/metasfresh-webui-frontend/issues/979) Use the Provided Endpoint w/ entry selections for given Breadcrumb
    * Refactoring/ Fixing the Breadcrumb menu usage.
  * [#987](https://github.com/metasfresh/metasfresh-webui-frontend/issues/987) Blue Border in modal overlay when Button pressed
    * Fixes a Stylesheet Issue that caused a blue border around Buttons and Tooltips when hovering.
  * [#998](https://github.com/metasfresh/metasfresh-webui-frontend/issues/998) Picking: quick actions button not shown
    * Fix for the Action Buttons in new Picking Terminal Window, that were not displayed.
  * [#1008](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1008) Breadcrumb Navigation broken for Document List
    * Fix for the breadcrumb menu that was not shown anymore in Document List.
  * [#1010](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1010) Action menu Window Name
    * Now showing the Window Name in the Action Menu dropdown List again.
  * [#1011](https://github.com/metasfresh/metasfresh-webui-frontend/issues/1011) Checking/ unchecking flags in included tabs lead to errors in console
    * Housekeeping Issue, now eliminating console errors that popped up when checking/ unchecking flags.

# metasfresh 5.17 (2017-28)

## Features
* metasfresh-app
  * [#1787](https://github.com/metasfresh/metasfresh/issues/1787) New Window for Quality Inspection Conference Results in WebUI
    * New Window for Quality Insprection Conference (Swiss Produce of Storage Vegetables) maintenance in WebUI.
  * [#1933](https://github.com/metasfresh/metasfresh/issues/1933) Menu Translations for Language en_US
    * Translations for all Menu entries and window Names for WebUI Menu for Language en_US.
  * [#1949](https://github.com/metasfresh/metasfresh/issues/1949) Add Inventory Valuation Report to WebUI menu
    * Adding the Inventory Valuation Report to WebUI.
  * [#1951](https://github.com/metasfresh/metasfresh/issues/1951) Window for Swiss ESR Payment Import in WebUI
    * New window for the Swiss ESR Payment Import in WebUI.
  * [#1952](https://github.com/metasfresh/metasfresh/issues/1952) intern: option bind related processes to a particular window
    * New Functionality in our Application Dictionary, allowing to bind Processes to a given window instead to a Table. This allows a much more generic split of actions depending on the given context it shall be used in.
  * [#1954](https://github.com/metasfresh/metasfresh/issues/1954) Different adjustments for Contract Window in WebUI
    * New Window in WebUI for Contract Maintenance.
  * [#1960](https://github.com/metasfresh/metasfresh/issues/1960) Add window asynchronous workpackage queue to webui
    * New Window in WebUI for Async Workpackage maintainence.

* metasfresh-webui-api
  * [#401](https://github.com/metasfresh/metasfresh-webui-api/issues/401) Support Zooming to NULL values
    * A New Functionality in WebUI Frontend, when zooming-to Fields that are empty and have null value. In theses cases the corresponding Windows open with a "new" action.
  * [#490](https://github.com/metasfresh/metasfresh-webui-api/issues/490) Picking prototype (v3)
    * Introducing the next Prototype iteration of the Picking Window.

* metasfresh-webui-frontend
  * [#793](https://github.com/metasfresh/metasfresh-webui-frontend/issues/793) Zoom-To for empty "null value" fields error
    * New Functionality in WebUI Frontend that allows to Zoom-To windows with empties fields. This allows the user to quickly record missing entries for referenced data and then select and uses when data recording.
  * [#918](https://github.com/metasfresh/metasfresh-webui-frontend/issues/918) Keyboard Shortcuts for Done, Apply, Start and Cancel
    * New Keyboard Shortcuts added for Done, Apply and Cancel Actions.
  * [#919](https://github.com/metasfresh/metasfresh-webui-frontend/issues/919) Keyboard shortcut for Quickaction dropdown
    * New Keyboard Shortcuts added for the Quickaction dropdown.
  * [#941](https://github.com/metasfresh/metasfresh-webui-frontend/issues/941) grid view: show one text for top level rows
    * New Functionality to show grouping Information in Tree View which can be different to the infomraiton in tree nodes/ leafs.

## Fixes
* metasfresh-app
  * [#1947](https://github.com/metasfresh/metasfresh/issues/1947) camt54 import does not support multiple Ntrys
    * Fix for swiss ESR Payment importer from Sepa camt54. Now allowing to import files with more than 1 ntry node and letting the Importer iterate them.
  * [#1953](https://github.com/metasfresh/metasfresh/issues/1953) Support multiple email recipients
    * Fixes a Bug that prevented the initial setting of multiple receipients in eMail editor, when opening from a document window.
  * [#1961](https://github.com/metasfresh/metasfresh/issues/1961) AD_BoilerPlate variables not resolved in Email editor
    * Fixes the Boilerplate usage in eMail Editor that was broken due to a refactoring for the eMail Editor in WebUI.
  * [#1970](https://github.com/metasfresh/metasfresh/issues/1970) MatchInv not posting properly
    * Fixes the posting of Match Invoices with Material Receipts that lead to an error in minor cases.
  * [#1975](https://github.com/metasfresh/metasfresh/issues/1975) new LU is created as planning in production
    * Improvement of newly created Handling Units as Logistic Unit, leaving the status of the LU as "planned" when created from Action Receipt in Manufacturing Workflow.

* metasfresh-webui-frontend
  * [#956](https://github.com/metasfresh/metasfresh-webui-frontend/issues/956) Caption Translation not working for Sidelist Menu
    * Now also translating the static element properly for Sidelist menu.
  * [#957](https://github.com/metasfresh/metasfresh-webui-frontend/issues/957) Sidelist toolips: Remaining translations for static messages not in Frontend
      * Additional i18n of static elements in WebUI Frontend.
  * [#959](https://github.com/metasfresh/metasfresh-webui-frontend/issues/959) Modal Overlay not translated although captions exists
    * Adding the Translation Functionality for static elements in Modal Overlays.
  * [#971](https://github.com/metasfresh/metasfresh-webui-frontend/issues/971) Page Up and Page Down load all data even if there is only one page
    * Thanks [robertup](https://github.com/robertup)
  * [#968](https://github.com/metasfresh/metasfresh-webui-frontend/issues/968) Navigation with arrows in modal windows gets to the background lines
    * Adjustment of the behavior in navigation when using the arrow keys to navigate through modal overlay Grid View in the new Picking Prototype.

# metasfresh 5.16 (2017-27)

## Features
* metasfresh-app
  * [#1854](https://github.com/metasfresh/metasfresh/issues/1854) Material Receipt Dispo window sequence OrderedQty CU vs. TU
    * Adjusting the Sequence of QtyReceives and QtyOrdered for CU and TU in Material Receipt Dispo.
  * [#1896](https://github.com/metasfresh/metasfresh/issues/1896) Overhaul the Product Planning window and subtabs
    * Adjusting the Product Planning Window to our current Layout Concept.
  * [#1903](https://github.com/metasfresh/metasfresh/issues/1903) Support camt.054.001.04 explicitly
    * Adding the Support of Sepa camt.054 import for Swiss ESR Payments which will replace v11 Forman in Beginning of 2018.
  * [#1909](https://github.com/metasfresh/metasfresh/issues/1909) Translate Open/ Close Edit Mode for Windows
    * German Translation of the Window Edit Mode Caption. First time used in Unser Dashboard Customizer Action Menue Entry.
  * [#1919](https://github.com/metasfresh/metasfresh/issues/1919) Additional static element Translations
    * New Translations for static window elements in WebUI.
  * [#1925](https://github.com/metasfresh/metasfresh/issues/1925) New Window for C_Allotment in WebUI
    * A new Window in WebUI allowing to maintain Allotments uses in Fresh Produce Procurement Contracts.
  * [#1926](https://github.com/metasfresh/metasfresh/issues/1926) Change Column Reference of C_Allotment_ID to search
    * Adjusting the Column Reference of C_Allotment_ID to Search and autocomplete allowing a better searchability for the user with a large amount of selection entries.
  * [#1948](https://github.com/metasfresh/metasfresh/issues/1948) eMail Editor does not open anymore
    * Fix for the eMail editor that did not open in swing client anymore via Mail icon.

* metasfresh-webui-api
  * [#485](https://github.com/metasfresh/metasfresh-webui-api/issues/485) backend: refactor sticky filters
    * Refactoring of sticky Filters in webUI API, allowing to set the active Filters more precicely.
  * [#486](https://github.com/metasfresh/metasfresh-webui-api/issues/486) dashboard: support "position" when PATCHing a dashboard item
    * Additional support for Dasboard KPI now allowing to set the precise position when moving the KPI Widgets.
  * [#469](https://github.com/metasfresh/metasfresh-webui-api/issues/469) Picking prototype (v2)
    * First increment of the Picking Window. Still Prototype at the moment, not to be used in live environments yet.

* metasfresh-webui-frontend
  * [#905](https://github.com/metasfresh/metasfresh-webui-frontend/issues/905) Add/ Remove KPI Widget functionality for User Dashboard
    * New Frontend Functionality to add and remove KPI widgets in the User Dashboard.
  * [#920](https://github.com/metasfresh/metasfresh-webui-frontend/issues/920) Improvement of Breadcrumb Navigation Dropdown entries
    * Adjustment of the content in the Breadcrumb Navigation, now only showing the leaf entries of the selected Breadcrumb Hierarchy.
  * [#922](https://github.com/metasfresh/metasfresh-webui-frontend/issues/922) Language switch does not update static elements
    * Adding the immediate Translations of menu Tooltips when switching the Language in User Settings.
  * [#924](https://github.com/metasfresh/metasfresh-webui-frontend/issues/924) Remaining untranslated static elements in WebUI
    * Translations for static elements added to metasfresh WebUI.
  * [#934](https://github.com/metasfresh/metasfresh-webui-frontend/issues/934) Image widget might generate considerable traffic
    * The Upload of Avatar/ User Images is now limited to an image size of 200x200.
  * [#935](https://github.com/metasfresh/metasfresh-webui-frontend/issues/935) Image widget: show uploading indicator
    * Now showing an uploading/ processing Indicator to the user durcing the upload of a User image. The Indicator is shown when the image will be shown.
  * [#944](https://github.com/metasfresh/metasfresh-webui-frontend/issues/944) Show the metasfresh logo centered when there are no KPIs
    * Centering the metasfresh Logo that's shown when the User/ KPI Dashboard remains empty without added KPI Cards.
  * [#945](https://github.com/metasfresh/metasfresh-webui-frontend/issues/945) dashboard: react to websocket notifications
    * New functionality for User Dashboard, now reacting to websocket Notifications. If the Dashboard it changed on other User Browser then the Dashboard is updated also for other opened Browser Tabs.
  * [#949](https://github.com/metasfresh/metasfresh-webui-frontend/issues/949) Board: adding a card as last is not user friendly
    * Implementation of a user friendly behavior when adding Cards to the new Kanban Board.
  * [#950](https://github.com/metasfresh/metasfresh-webui-frontend/issues/950) Dashboard: move the "Open edit mode" button to Actions menu
    * New Action for Dashboard >Edit mode. Allows the user to toggle between Edit and Live Mode of the User/ KPI Dashboard.
  * [#964](https://github.com/metasfresh/metasfresh-webui-frontend/issues/964) again: Editing in the middle of a text field makes the cursor jump to the end
    * thanks [wiadev](https://github.com/wiadev)

## Fixes
* metasfresh-app
  * [#1869](https://github.com/metasfresh/metasfresh/issues/1869) Customer Returns for HU more than 1 Document
    * Fixes a Bug that created too many Customer return Documents when the initial Handling Unit was already created in a Material Receipt.
  * [#1875](https://github.com/metasfresh/metasfresh/issues/1875) New Order Control Report cut off left/ cut off right
    * Adjustments done to the new Order Conrol report, now resizing the margins left and right to avoid cutting off information.
  * [#1905](https://github.com/metasfresh/metasfresh/issues/1905) "Wareneingangsetikett LU (Jasper)" prints >200 pages
    * Fixes a minor Bug in Material Receipt automatic Label printing, that occured when the Receipt was already split into a lot of individual Handling Units.
  * [#1911](https://github.com/metasfresh/metasfresh/issues/1911) memory issue related to swing-client picking terminal
    * Fixes an Out of Memory issue that sometime occurred in the Picking Terminal of the Swing Client.
  * [#1955](https://github.com/metasfresh/metasfresh/issues/1955) memory issues due to default process filter being "true"
    * Fixes an Out ofd memory issues that occured in a few processes due to default process parameter.

* metasfresh-webui-api
  * [#449](https://github.com/metasfresh/metasfresh-webui-api/issues/449) Key Field Missing in KPI Field Translation Window
    * Adding the Key Field to WebUI KPI Translation Window.
  * [#481](https://github.com/metasfresh/metasfresh-webui-api/issues/481) Sticky empty Filter in modal overlay.
    * Getting rid of the empfty Sticky Filter when opening a modal overlay.
  * [#482](https://github.com/metasfresh/metasfresh-webui-api/issues/482) No Role Error for new User
    * Fixes a caching Bug that occurred when adding a new User, without a role and trying to login. After the "No Role exists" error occured it was not possible for the user to login anymore.

* metasfresh-webui-frontend
  * [#921](https://github.com/metasfresh/metasfresh-webui-frontend/issues/921) Browser issue: Window Bookmark Header
    * The jumping linebreak effect for Window headers in Action Menu is now solved in Chrome Browser.
  * [#923](https://github.com/metasfresh/metasfresh-webui-frontend/issues/923) Packageables window: up/down arrows are working weird
    * Fixes the Keyboard Navigaiton in Picking Terminal Window for WebUI.
  * [#931](https://github.com/metasfresh/metasfresh-webui-frontend/issues/931) Notifications are not refreshed when the language is changed
    * Now the Notifications are alss shown in the correct language after switching the language in User settings.
  * [#933](https://github.com/metasfresh/metasfresh-webui-frontend/issues/933) Disable quick action button while running the action (v2)
    * Disabling the Quick Action Buttons while an action is already running, to avoid multiple starts and inconsistencies.
  * [#951](https://github.com/metasfresh/metasfresh-webui-frontend/issues/951) Frontend is broken (showstopper)
    * Fixes a Bug that raises exceptions after login and prevented the usage of Action Menu.

# metasfresh 5.15 (2017-26)

## Features
* metasfresh-app
  * [#1786](https://github.com/metasfresh/metasfresh/issues/1786) New Window for Flatrate Conditions in Webui
    * New Window that allows the maintenance of Flatrate Terms/ Conditions in WebUI.
  * [#1788](https://github.com/metasfresh/metasfresh/issues/1788) New Window for Flatrate Transition in WebUI
    * New Window for Flatrate Transition maintenance.
  * [#1867](https://github.com/metasfresh/metasfresh/issues/1867) restore old ordercheckup jasper files
    * Restoring old Ordercheckup Reports and adding the new ones into a seperate path for future usage.
  * [#1872](https://github.com/metasfresh/metasfresh/issues/1872) WebUI Default Filter missing in Windows compared to Swing
    * Default Filter Settings for WebUI taken from implicit Filtering in Swing Client.
  * [#1877](https://github.com/metasfresh/metasfresh/issues/1877) Translations de_DE for Handling Unit QuickActions
    * Translations for the Customer and Vendor Returns Quick actions in Material Receipt and Handling Unit Editor.
  * [#1890](https://github.com/metasfresh/metasfresh/issues/1890) Harmonize the Shipment Window to our current Look&Feel concept
    * Harmonizing the Look & Feel of the Shipment Window accoring to others. Eliminating the primary Layout chunks.
  * [#1893](https://github.com/metasfresh/metasfresh/issues/1893) Default Doctype for Shipment Window
    * Now receiving a default Document Type when creating Shipment Documents manually.

* metasfresh-webui-api
  * [#464](https://github.com/metasfresh/metasfresh-webui-api/issues/464) Dashboard API: unify get available kpis/targetIndicators endpoints
    * Internal housekeeping Issue, unifying the endpoints for fetching KPI & Target Indicators.
  * [#465](https://github.com/metasfresh/metasfresh-webui-api/issues/465) Provide Endpoint w/ entry selections for given Breadcrumb
    * Improvement of Breadcrumb Navigation, now providing the entry points via API.
  * [#467](https://github.com/metasfresh/metasfresh-webui-api/issues/467) board API: GET board/{boardId}/card?cardIds
    * New API endpoint that allows to receive Cards for the new generic board window.
  * [#468](https://github.com/metasfresh/metasfresh-webui-api/issues/468) Fix user full name in Avatar
    * Adjusting the Avatar Name. Now showing Firstname Lastname.
  * [#470](https://github.com/metasfresh/metasfresh-webui-api/issues/470) Cache image endpoint
    * New Endpoint in Rest-API that enables the caching of images.
  * [#474](https://github.com/metasfresh/metasfresh-webui-api/issues/474) Dashboard API: specify position when adding a new KPI/target indicator
    * New endpoints to specify positions of KPI Cards in User Dashboard.

* metasfresh-webui-frontend
  * [#833](https://github.com/metasfresh/metasfresh-webui-frontend/issues/833) Dashboard Window w/ Swimlane type functionalities
    * New generic Window Implementation that allows to define Kanbanlike Boards in metasfresh.
  * [#864](https://github.com/metasfresh/metasfresh-webui-frontend/issues/864) Show view sticky/readonly filters
    * Adding a new Filter Type that allows to show filtered results to the user when using Zoom-To into new Window.
  * [#896](https://github.com/metasfresh/metasfresh-webui-frontend/issues/896) KPIs: show Loading indicator instead of No data
    * Exchanging "No Data" Notification with a Loading Indicator in KPI when data is not available.
  * [#908](https://github.com/metasfresh/metasfresh-webui-frontend/issues/908) URL widget: disable the link button if the URL is not valid
    * Disabling the URL Widget Button as long as the URL Field is empty.
  * [#926](https://github.com/metasfresh/metasfresh-webui-frontend/issues/926) Board window does not open via Sitemap menu
    * Fix to allow the Opening of Boards via WebUI Menu.
  * [#938](https://github.com/metasfresh/metasfresh-webui-frontend/issues/938) Lookup Fields improvements
    * Adjustment of the Lookup Fields behavior. Now only showing the drop down selection List when there is more than 1 entry in the list.

## Fixes
* metasfresh-app
  * [#1814](https://github.com/metasfresh/metasfresh/issues/1814) Hide Processed flag from all M_InOut/Returns windows (webui)
    * The redundant processed flag is now removed from InOut Windows (Empties return/ receive, Vendor Returns). The information is already given by the Document Status Field.
  * [#1861](https://github.com/metasfresh/metasfresh/issues/1861) Performance degradation in HUKey.isReadonly()
    * Fixes a Bug that decreased the performance of HUKey.isReadonly() method over the time.
  * [#1873](https://github.com/metasfresh/metasfresh/issues/1873) Fix control amount and qty in payment data imported from camt.54
    * Fix for the Import of Sepa camt.54 files accoring to control amount and qty.
  * [#1900](https://github.com/metasfresh/metasfresh/issues/1900) Migration Script for "Select all lines count"
    * Fix of the number delimiter in a message Translation the leads to failing switch of link in frontend for 'Select all x records'.
  * [#1912](https://github.com/metasfresh/metasfresh/issues/1912) Customer return from HUs coming from Verdichtung POS don't have the right quantities
    * Fixing a minor Bug with Handling Units created in Compression utility not having the right quicntities when processed in Customer returns.
  * [#1921](https://github.com/metasfresh/metasfresh/issues/1921) Export webui messages from w101 again
    * Updating the webui messages via migration script.
  * [#1924](https://github.com/metasfresh/metasfresh/issues/1924) ESR Line AcctDate not set error
    * Fixing a Bug in Bank Statements that occure because of a missing account date in ESR Lines.

* metasfresh-webui-frontend
  * [#913](https://github.com/metasfresh/metasfresh-webui-frontend/issues/913) Incompatible Sock.js versions
    * Updating the Sock.js to identical versions in forntend and API.
  * [#926](https://github.com/metasfresh/metasfresh-webui-frontend/issues/926) Board window does not open via Sitemap menu
    * Fixes a Bug that prevented to open the new generic Board wndow via menu.

# metasfresh 5.14 (2017-25)

**release for week 2017-25**

## Features
* metasfresh-backend
  * [#1603](https://github.com/metasfresh/metasfresh/issues/1603) Enhance Vendor returns
    * Different Enhancements in the Vendor Returns Functionality.
  * [#1790](https://github.com/metasfresh/metasfresh/issues/1790) New Window for Calendar in WebUI
    * New Window for Calendar Maintenance in WebUI.
  * [#1816](https://github.com/metasfresh/metasfresh/issues/1816) Fix the webui layout for Vendor returns (Lieferanten Rücklieferung) window
    * New Window Layout for vendor Returns window, Main View and Subtab Lines.
  * [#1817](https://github.com/metasfresh/metasfresh/issues/1817) Allow to switch off transmission to Procurement UI via PMM_Product
    * Enhancement of the functionality to transmit Products to Procurement User Interface. Now allowing the user to switch off selected Transmissions also when contracts exist and are valid.
  * [#1819](https://github.com/metasfresh/metasfresh/issues/1819) make new pricelist a sales pricelist by default
    * New Default behavior when creating new Price Lists. The List is set to "Sales Price List" initially.
  * [#1823](https://github.com/metasfresh/metasfresh/issues/1823) New Procurement Windows in WebUI
    * Adding the Procurement Windows to WebUI.
  * [#1824](https://github.com/metasfresh/metasfresh/issues/1824) New Request for Quotation Windows in WebUI
    * New Window to allow the recording of Request for Quotations in WebUI.
  * [#1825](https://github.com/metasfresh/metasfresh/issues/1825) Disable Quickstart, Admin and System Administrator Roles for WebUI
    * Housekeeping Issue, restricting less default roles for usage in WebUI.
  * [#1829](https://github.com/metasfresh/metasfresh/issues/1829) Different RFQ Windows in WebUI
    * New Windows in WebUI that allow RFQ recording and Response maintenance.
  * [#1830](https://github.com/metasfresh/metasfresh/issues/1830) Add filter for HU status in handling unit editor
    * Additional Default Filters for the new Handling Unit Editor.
  * [#1836](https://github.com/metasfresh/metasfresh/issues/1836) Adjustments for Empties Receive Window WebUI
    * Further Layout adjustments for the Empties Receive Window in WebUI.
  * [#1839](https://github.com/metasfresh/metasfresh/issues/1839) New Window for Period & Period Control in WebUI
    * New Window for Period and Period Control maintenance in Accounting
  * [#1840](https://github.com/metasfresh/metasfresh/issues/1840) internal: refactor and improve PO translation code
    * Enhancement of the Multi-Language Translation Functionality.
  * [#1845](https://github.com/metasfresh/metasfresh/issues/1845) Customer Returns Layout Improvement
    * Layout Adjustments to Customer Return Window in WebUI.
  * [#1848](https://github.com/metasfresh/metasfresh/issues/1848) EDI Desadv Create from Order Warning in Log
    * Application dictionary cleanup on metasfresh installations where it's needed
  * [#1852](https://github.com/metasfresh/metasfresh/issues/1852) intern: discover interceptors which were annotated with @Component
    * Internal Housekeeping Issue about annotations in Spring context.
  * [#1857](https://github.com/metasfresh/metasfresh/issues/1857) Translation de_DE for static UI components in WebUI
    * New base language Translation for the static elements in ad_messages in WebUI.
  * [#1859](https://github.com/metasfresh/metasfresh/issues/1859) Export all webui AD_Messages from w101
    * Exporting the whole webui ad_messages for migration purpose.
  * [#1882](https://github.com/metasfresh/metasfresh/issues/1882) assign role webui to user IT
  * [#1884](https://github.com/metasfresh/metasfresh/issues/1884) make sure no bookmarks for user metasfresh so fallback menu is displayed

* metasfresh-webui-api
  * [#443](https://github.com/metasfresh/metasfresh-webui-api/issues/443) Make remaining UI components translatable
    * Endpoints Implementation in metasfresh API for the new Translation Feature of static Elements in Frontend.
  * [#458](https://github.com/metasfresh/metasfresh-webui-api/issues/458) Debug endpoint to track websocket outbound events
    * Implementation of Debug Endpoints to be able to test the behavior of websockets.
  * [#462](https://github.com/metasfresh/metasfresh-webui-api/issues/462) Dashboard editing API prototype
    * This is the API Prototype for the new Features required for the User Dashboard.
  * [#463](https://github.com/metasfresh/metasfresh-webui-api/issues/463) filter is ignored when doing mass invoice or shipping
    * Fixes an issue in adding Filter Criteria to Mass invoiceing/ shipping process.


* metasfresh-webui-frontend
  * [#861](https://github.com/metasfresh/metasfresh-webui-frontend/issues/861) Make remaining UI components translatable
    * Implementation for translations of static Elements. Now it's able to translate the whole User Interface of metasfresh WebUI.
  * [#885](https://github.com/metasfresh/metasfresh-webui-frontend/issues/885) Change the Bookmark Subtab Group Functionality
    * Changes the Bookmark Grouping Functionality and now shows a flat and compressed representation of the sitemap in shortcut/ bookmark menu.
  * [#886](https://github.com/metasfresh/metasfresh-webui-frontend/issues/886) Optimize Browse-whole-tree Navigation Link
    * Moves the 'Browse whole tree' to more prominent place above the Bookmark menu. This allows the user to quickly open the sitemap if needed.
  * [#895](https://github.com/metasfresh/metasfresh-webui-frontend/issues/895) Modal view: when user presses Done the server shall be notified
    * Enhancement in WebUI for Done Actions. The Frontend is now calling a new Endpoint so the API is aware about that the action was triggered.
  * [#897](https://github.com/metasfresh/metasfresh-webui-frontend/issues/897) Clicking the search icon should open partner list
    * New Feature in Lookup Fields. When pressing the magnifying Glass item, the user receives a list of possible entries.
  * [#898](https://github.com/metasfresh/metasfresh-webui-frontend/issues/898) Lookup Field Dropdowns automatism
    * Enhancements of the Lookup Fields behavior when result selections entries
  * [#907](https://github.com/metasfresh/metasfresh-webui-frontend/issues/907) Margin missing in Advanced Edit when Scrollbar visible
    * Minor Design adjustment in Modal Overlays, adding a larger bottom Margin.

## Fixes

* metasfresh-backend
  * [#1314](https://github.com/metasfresh/metasfresh/issues/1314) lazy-assigned Hostkey is not shown in swing-client's settings
    * Enhancement for the Host-Key Functionality needed in async- and batch-printing. Now showing the Host-Key in Swing Client seetings.
  * [#1808](https://github.com/metasfresh/metasfresh/issues/1808) Sometimes i get empty window when i zoom to related sales invoices of a given sales order
    * Fixes an issue in the Zoom-To related Documents Feature, leaving the destination windows empty in minor cases.
  * [#1843](https://github.com/metasfresh/metasfresh/issues/1843) Error Table M_Product does not have a simple primary key
    * Adding a simple primary key to M_Product database table.
  * [#1855](https://github.com/metasfresh/metasfresh/issues/1855) error with order that has just packaging lines
    * Fixes an error in Orders which don't have products in their lines, but only Packaging Material.

* metasfresh-webui-api
  * [#454](https://github.com/metasfresh/metasfresh-webui-api/issues/454) Can not add attribute to attribute set
    * Fixes an issue when adding a new Attribute to an Attribute set, for mandatory fields.
  * [#1863](https://github.com/metasfresh/metasfresh/issues/1863) memory issue releated to material receipt
    * Fixes an Out of memory issue that occured in cornercases working in material Receipt Canidates window.

* metasfresh-webui-frontend
  * [#876](https://github.com/metasfresh/metasfresh-webui-frontend/issues/876) Clean and unify the two-column structure in MenuOverlay
    * Decided to have a clean 1-column Layout for now for the shortcut/ bookmark menu.
  * [#877](https://github.com/metasfresh/metasfresh-webui-frontend/issues/877) n instead of none in dropdowns
    * Fixes a minor Bug with the description of the emptyvalues in fields.
  * [#893](https://github.com/metasfresh/metasfresh-webui-frontend/issues/893) Navigation Menu keyboard usage broken
    * Adjustments in the Sitemap screen, now allowing the user to navigate smoothly via keyboard.
  * [#900](https://github.com/metasfresh/metasfresh-webui-frontend/issues/900) Handling Unit Window does not open in some cases
    * Fixes a Bug that prevented to open the Handling Unit Editor in minor cases.
  * [#904](https://github.com/metasfresh/metasfresh-webui-frontend/issues/904) Navigation Menu vs. Sitemap entries w/ linebreak
    * Adjustments of the sitemap screen, now showing the menu entries without a linebreak in case there is enough horizontal space.

# metasfresh 5.13 (2017-24)

**release for week 2017-24**

## Features
* metasfresh-backend
  * [#1513](https://github.com/metasfresh/metasfresh/issues/1513) Translating Shipment Candidates and other fields to en_US
    * Improving the Translation of metasfresh for the en_US language. Here adding the Translation of the Shipment Candidates window.
  * [#1590](https://github.com/metasfresh/metasfresh/issues/1590) Translate User Window to en_US
    * Improving the Translation of metasfresh for the en_US language. Here adding the Translation of the User window.
  * [#1655](https://github.com/metasfresh/metasfresh/issues/1655) Add CountExpected in Async Batch and improvements
    * Nice new feature to see the expected counts for AsynchBatch processing queue.
  * [#1741](https://github.com/metasfresh/metasfresh/issues/1741) New Window for Order Control in WebUI
    * New Window for Order Control Report maintenance in WebUI
  * [#1742](https://github.com/metasfresh/metasfresh/issues/1742) Translation for en_US in WebUI
    * Improving the Translation of metasfresh for the en_US language. This is the overall main issue.
  * [#1746](https://github.com/metasfresh/metasfresh/issues/1746) Layout Optimization of User Window in WebUI
    * Adding a few Layout Optimzations to User window. Now showing the Login Field in Grid View and deleting the combined Name Field. Additional sized adjustments.
  * [#1748](https://github.com/metasfresh/metasfresh/issues/1748) Project Documentation: Screenshots
    * Adds WebUI Screenshots to our project and adding to our readme page.
  * [#1753](https://github.com/metasfresh/metasfresh/issues/1753) New Window for Material Transactions in WebUI
    * New Window to Filter/ Search for Material Transaction in WebUI. Alowing the user to search for Products, Business Partners, Dates and Transaction Types.
  * [#1754](https://github.com/metasfresh/metasfresh/issues/1754) New Window for Transport Disposition in WebUI
    * Adding 2 new Windows to WebUI - Transport Disposition and Transport Shipment - allowing the user to find out which shipments have predefined Tour Deliverydays allocated and which have to be joined to a Shipper Transportation manually.
  * [#1757](https://github.com/metasfresh/metasfresh/issues/1757) New Window Shipper Transportation in WebUI
    * Creates the new Window for Shipper Transportation in WebUI.
  * [#1765](https://github.com/metasfresh/metasfresh/issues/1765) Adjustment of advanced edit in Doctype Window
    * Refining the Layout of the Document Type Window, adding missing fields and adjusting the advanced edit layout.
  * [#1769](https://github.com/metasfresh/metasfresh/issues/1769) Window Adjustments for Dashboard Configuration
    * Refining the window Layout of User Dashboard Configuration Window in WebUI.
  * [#1770](https://github.com/metasfresh/metasfresh/issues/1770) New Window for KPI Widgets in WebUI
    * Adds the default 2-column Layout to the KPI Widget Configuration Window in WebUI.
  * [#1773](https://github.com/metasfresh/metasfresh/issues/1773) Add Description Field to User Dashboard
    * New Description Field in Dashboard Window that allows to add a few notes what the Dashboard Configuration is about.
  * [#1778](https://github.com/metasfresh/metasfresh/issues/1778) New Window/ Tab for KPI Field Translation
    * New Window for KPI Field Translation maintenance.
  * [#1782](https://github.com/metasfresh/metasfresh/issues/1782) My profile window: take out Password field
    * Removing the Password Field from my Profile Window.
  * [#1794](https://github.com/metasfresh/metasfresh/issues/1794) Advanced Edit: Business Partner Main View and Customer
    * First Prototype of Advanced Edit Screen, here done in Business Partner window Main View and Customer Subtab.
  * [#1798](https://github.com/metasfresh/metasfresh/issues/1798) Prototype Dashboard for Board frontend development
    * Adds a prototype Project Board to WebUI menu.
  * [#1801](https://github.com/metasfresh/metasfresh/issues/1801) Translation of My Profile Window to en_US
    * Translating the new Avatar Settings of my Profile Windw to en_US Language.
  * [#1805](https://github.com/metasfresh/metasfresh/issues/1805) Add the new Picking Window to WebUI Menu
    * Adding the prototype Picking window to WebUI menu.

* metasfresh-webui-api
  * [#433](https://github.com/metasfresh/metasfresh-webui-api/issues/433) Show Manufacturing Order number in window header
    * Enhancement of the WebUI User Interface for moadl overlays. Implemented the endpoint to show detailed infomration to teh user when opening the Manufacturing Issue/ Receipt window.
  * [#434](https://github.com/metasfresh/metasfresh-webui-api/issues/434) Provide endpoints for user avatar and user settings
    * New Implementation in Rest-API that provided endpoints for the new Avatar and User Settings Functionality.
  * [#435](https://github.com/metasfresh/metasfresh-webui-api/issues/435) document: stale tab events shall be sent to document's websocket endpoint
    * Improvement of the WebUI Rest-API communication for messages because of Stale Tabs.
  * [#437](https://github.com/metasfresh/metasfresh-webui-api/issues/437) Load/ Reload of delivery Days window takes too long.
    * API Improvement for opening windows with a large amount of data.
  * [#441](https://github.com/metasfresh/metasfresh-webui-api/issues/441) Implement Board API
    * Implementing the metasfresh API for the generic Board configuration functionality.
  * [#444](https://github.com/metasfresh/metasfresh-webui-api/issues/444) Make Dashboard Translatable
    * Improvement of the Rest-API now allowing to Translate fixed elements via metasfresh ad_messages and their translation.
  * [#447](https://github.com/metasfresh/metasfresh-webui-api/issues/447) Truncate WEBUI_ViewSelection tables on startup
    * Housekeeping Task to cleanup the WebUI View Selection Tables on server startup, which were growing fast.
  * [#451](https://github.com/metasfresh/metasfresh-webui-api/issues/451) Provide view sticky filters to be displayed by frontend
    * New functionality for better User transparence. Now adding "sticky filters" API to mark windows that were opened and filtered via zoom-to. This endpoint will be used by the frontend in the next milestones.
  * [#452](https://github.com/metasfresh/metasfresh-webui-api/issues/452) Introduce URL widget type
    * API Implementation of the new URL Widget in frontend.
  * [#453](https://github.com/metasfresh/metasfresh-webui-api/issues/453) Password process parameters shall allow showing the password
    * Endpoint that allows to make the passwords visible in the change password process.
  * [#456](https://github.com/metasfresh/metasfresh-webui-api/issues/456) Outbound Mail endpoint prototype
    * Implementation of the eMail ourbound endpoint, which will be used by frontend in the new eMail editor.

* metasfresh-webui-frontend
  * [#802](https://github.com/metasfresh/metasfresh-webui-frontend/issues/802) User/ Session Settings w/ Avatar and Logout
    * New Settings Section that allows the user to change User dependant infomration and reset the password. Further on having possibility to upload a user avatar picure that is shown in the header bar of metasfresh.
  * [#803](https://github.com/metasfresh/metasfresh-webui-frontend/issues/803) Bookmark a window/process/report and show in Quick Menu
    * New Fuctionality that allows the user so bookmark favorite menu entries and show these in the navivation action menu.
  * [#835](https://github.com/metasfresh/metasfresh-webui-frontend/issues/835) Collapse functionality in Grid View included rows
    * A new User Interface Functionality allowing to define collapsible/ expandable Tree Grids in metasfresh. This functionality will be used first time in Picking Terminal and then later in retraceabbility Window too.
  * [#839](https://github.com/metasfresh/metasfresh-webui-frontend/issues/839) Combined Fields/ Lookup adjustment
    * Nice Improvements to Combined Lookup fields adding a step-by-step workflow when recording data in those fields.
  * [#840](https://github.com/metasfresh/metasfresh-webui-frontend/issues/840) Open Sitemap shall focus on Search Field initially and more
    * Navigation Improvements in Sitemap Screen. Now allowing easy switch between filtered menu treee and search field.
  * [#847](https://github.com/metasfresh/metasfresh-webui-frontend/issues/847) Loading of empty delivery Days Window take long
    * Frontend Improvement for opening windows with a large amount of data.
  * [#854](https://github.com/metasfresh/metasfresh-webui-frontend/issues/854) After login to account, go to sitemap page, open developer tab and start using scroll.
    * Additional Improvement for the Usage of Firefox Browser with metasfresh WebUI.
  * [#855](https://github.com/metasfresh/metasfresh-webui-frontend/issues/855) Missing shadow under top menu
    * Improvement of User Interface when using Firefox Browser. Now showing the shadow underneath top navigation when scrolling down.
  * [#862](https://github.com/metasfresh/metasfresh-webui-frontend/issues/862) Modal view: show view description if available
    * Additional Descriptions to modal Overlay windows. First Implementation of this feature in Manufacturing Window Issue/ Receipt now showing the Document Type and Document No.
  * [#863](https://github.com/metasfresh/metasfresh-webui-frontend/issues/863) Show password option for Password widget
    * New Functionality to allow the user to make the passoword visible in Password changing workflow.
  * [#865](https://github.com/metasfresh/metasfresh-webui-frontend/issues/865) Clickable URL in Fields
    * New Widget Type for URL Fields that allow the user to open a new browser Tab with the recorded link.
  * [#867](https://github.com/metasfresh/metasfresh-webui-frontend/issues/867) Layout Adjustment for UI Section Headlines
    * New Layout for Section Headlines, allowing more and better visibility/ readability for the user.
  * [#872](https://github.com/metasfresh/metasfresh-webui-frontend/issues/872) Breadcrumb: separate the document summary and last breadcrumb node with a slash
    * Additional Slash Character in Breadcrumb to seperate the window Name and Document Identifier.
  * [#880](https://github.com/metasfresh/metasfresh-webui-frontend/issues/880) Change Icon for URL Link
    * Adjusting the Icon of the new URL widget with a 'link' image.

* others
  * [metasfresh/metasfresh-docker#23](https://github.com/metasfresh/metasfresh-docker/issues/23) Jenkinsfile make docker tag from MF_UPSTREAM_BRANCH
    * Build System imporvement now allowing to issue the git branch name as docker tag.
  * [metasfresh/metasfresh-scripts#8](https://github.com/metasfresh/metasfresh-scripts/issues/8) implement crude pagination for `cherry-pick-issue-script`
    * Adding support for github issues with more than 30 events.

## Fixes
* metasfresh-backend
  * [#1441](https://github.com/metasfresh/metasfresh/issues/1441) move subproject de.metas.endcustomer.mf15.ait to metasfresh-dist repo
    * Cleanup; Thx [@homebeaver](https://github.com/homebeaver) for the pointer
  * [#1735](https://github.com/metasfresh/metasfresh/issues/1735) istransferwhennull not working in webUI but in java client
    * This Fixes the transmission of Attributes from issued material to received product in the manufacturing workflow.
  * [#1793](https://github.com/metasfresh/metasfresh/issues/1793) fix jasper document for vendor returns
    * Minor Bugfix for the Vendor Returns document.
  * [#1802](https://github.com/metasfresh/metasfresh/issues/1802) picking terminal is not opening
    * Fixes a Bug that prevented the starting/ opening of the Picking Terminal Window.
  * [#1807](https://github.com/metasfresh/metasfresh/issues/1807) Fix "Create primary key" process
    * Fixing the "create primary key" process in Table/ Columns in Application Dictionary.
  * [#1810](https://github.com/metasfresh/metasfresh/issues/1810) Responsible mandatory Field missing in Request
    * Adds the Field back for Resposible Sales Representative to Request Window in WebUI.

* metasfresh-webui-api
  * [#400](https://github.com/metasfresh/metasfresh-webui-api/issues/400) minimum password length error message not displayed
    * Now adding an error message to the user when the changed password does not have a length of at least 8 characters.
  * [#436](https://github.com/metasfresh/metasfresh-webui-api/issues/436) Manufacturing Order Issue not possible after barcode filtering
    * Fixes a minor Bug that occured when using the barcode filtering.
  * [#446](https://github.com/metasfresh/metasfresh-webui-api/issues/446) Cannot open the menu when logged in as System Administrator
    * Now allows also the System Administrator to see the menu in WebUI.

* metasfresh-webui-frontend
  * [#795](https://github.com/metasfresh/metasfresh-webui-frontend/issues/795) On tab stale event, don't refresh tabs which are queryOnLoad but are not the active tab
    * Refreshing/ Reloading improvement fix for stale tabs which are not active and shall only be queried when opened.
  * [#848](https://github.com/metasfresh/metasfresh-webui-frontend/issues/848) notifications don't update
    * Minor fix. Now updating the the user notifications again.
  * [#849](https://github.com/metasfresh/metasfresh-webui-frontend/issues/849) manufacturing order doesn't open
    * Fixes a Bug that prevented the Manufacturing Order window to be opened in WebUI.
  * [#878](https://github.com/metasfresh/metasfresh-webui-frontend/issues/878) Error firework when logout
    * Fixes various issues that occured when logging out of metasfresh.
  * [#879](https://github.com/metasfresh/metasfresh-webui-frontend/issues/879) Avatar Picture not deleted from Header when cleared in Profile Settings
    * Minor fix for not undisplaying the Avatar image from header when deleting the Image from User Profile.
  * [#881](https://github.com/metasfresh/metasfresh-webui-frontend/issues/881) Error when pressing Action Menu in Collapsible Grid Window
    * Fixes an issue with the Action Menu in windows with the new collapsible Grid View.
  * [#883](https://github.com/metasfresh/metasfresh-webui-frontend/issues/883) hu editor doesn't update
    * Bugfix for the HU Editor that did not update in some cases.

# metasfresh 5.12 (2017-23)

## Features
* metasfresh-backend
  * [#1239](https://github.com/metasfresh/metasfresh/issues/1239) Barcode on Bestellkontrolle report
    * The Sales Order Control Report now has an included Barcode with Code39. Ist allows the users in Manufacturing to scan the control report and the Manufacturign Order is automatically opened, ready for action issue and receipt.
  * [#1327](https://github.com/metasfresh/metasfresh/issues/1327) Do not display Destroyed HUs in Korrektur panel
    * Refining the base Filtering of Handling Units in WebUI Handling Unit Editor (here Correction Panel in material Receipt), now not showing destroyed Handling Units anymore.
  * [#1334](https://github.com/metasfresh/metasfresh/issues/1334) Display created/ createdby in BusinessPartner contracts window
    * New Fields Created, CreatedBy now added to the Business Partner contracts window.
  * [#1518](https://github.com/metasfresh/metasfresh/issues/1518) Show currency from pricelist in pricelist / pricelist comparison Jasper
    * Introduces the Currency information to the Pricelist comparison report.
  * [#1564](https://github.com/metasfresh/metasfresh/issues/1564) New Window for Packing Material in WebUI
    * New Window that allows to maintain Packing Material in WebUI.
  * [#1599](https://github.com/metasfresh/metasfresh/issues/1599) Import full menu from w101
    * Initial Import of the current full webUI Menu/ Sitemap.
  * [#1619](https://github.com/metasfresh/metasfresh/issues/1619) Do not process reverse booking lines and improve esr import
    * Improvement of the automatic processing workflow of payment imports in swiss ERS format.
  * [#1641](https://github.com/metasfresh/metasfresh/issues/1641) New Tax Rate window in WebUI
    * Adding the Tax Rate Window to WebUI.
  * [#1644](https://github.com/metasfresh/metasfresh/issues/1644) Add email validator syntax
    * Adding an eMail validator to metasfresh.
  * [#1647](https://github.com/metasfresh/metasfresh/issues/1647) Rename Empties Return in language de_DE
    * Translation of Emptires Return Window in WebUI. Renamed window, tab and menu entry.
  * [#1654](https://github.com/metasfresh/metasfresh/issues/1654) Customer Returns Window in WebUI
    * Creating the Customer Returns Window for WebUI. Adding to the menu.
  * [#1659](https://github.com/metasfresh/metasfresh/issues/1659) Make verify BOM available in webUI
    * Adding the verify BOM to WebUI, allowing the user to better maintain Bill of Material Configurations.
  * [#1657](https://github.com/metasfresh/metasfresh/issues/1657) Initially set Date+Time Filters in WebUI
    * Removing 2 Date Fields from Filter criteria in WebUI that did not have a perfect behavior when using combined filters.
  * [#1661](https://github.com/metasfresh/metasfresh/issues/1661) make process field autocomplete in table and columns window
    * Making the process & report fields autocomplete in table and columns configuration.
  * [#1663](https://github.com/metasfresh/metasfresh/issues/1663) new order checkup jasper
    * Creating a new Order control report that has a more compressed and focused view and reduces the amount of information to the most important.
  * [#1665](https://github.com/metasfresh/metasfresh/issues/1665) Subtab Advanced Edit Configurations
    * Migrating all Subtab UI Elements to show them in Advanced Edit, also swicthing the UI Element Group UI Styles to be not primary.
  * [#1672](https://github.com/metasfresh/metasfresh/issues/1672) New Tax Category Window in WebUI
    * New Window for the Tax Category Maintenance.
  * [#1676](https://github.com/metasfresh/metasfresh/issues/1676) Payment Terms window in WebUI
    * Adding the window Layout For Payment Term window in WebUI.
  * [#1677](https://github.com/metasfresh/metasfresh/issues/1677) Adjustments to Product Window in WebUI
    * Adjustments to Product Window, refining the Layout and Look&Feel of Fields in main View and Advanced Edit. Harmonizing to our current default Layout.
  * [#1679](https://github.com/metasfresh/metasfresh/issues/1679) New Window for Country in WebUI
    * New Window in WebUI allowing the maintaineance of Country, Regions and Cities
  * [#1680](https://github.com/metasfresh/metasfresh/issues/1680) New Window or Request Type in WebUI
    * Creation of the Window Layoutfor the Request Type Window in WebUI.
  * [#1681](https://github.com/metasfresh/metasfresh/issues/1681) New Window for Standard Response in WebUI
    * Creating a new window for Result Schema maintenence in WebUI which is available for the user via menu or zoom-to functionality from Request Window.
  * [#1687](https://github.com/metasfresh/metasfresh/issues/1687) Refactor Request Tab in Partner Window to allow zoom and advanced edit
    * Adjustments in Business window Subtab for Requests, now allowing the user the zoom-to Request Window and records directly.
  * [#1695](https://github.com/metasfresh/metasfresh/issues/1695) Introduce AD_User.Avatar_ID and AD_Language fields
    * Introducting 2 new Fields - Avatarm Language - to User Window and May Profile.
  * [#1701](https://github.com/metasfresh/metasfresh/issues/1701) Customer Return Window additional Fields
    * Including new Fields to Customer Return Lines (QtyTU and HUPIProductItem).
  * [#1705](https://github.com/metasfresh/metasfresh/issues/1705) Add Handling Unit Editor Window to WebUI Menu
    * Adding the Handling Unit Editor to WebUI menu in Logistics group.
  * [#1707](https://github.com/metasfresh/metasfresh/issues/1707) Add Handling Unit Assignment Tab to Customer Returns Window
    * Adds the Handling Unitb Assignment Tab to the Customer Returns window, allowing the user to easily zoom-to and maintain the returned Handling Units.
  * [#1709](https://github.com/metasfresh/metasfresh/issues/1709) Pimp the old "My Profile" window to be used in webui as User settings
    * Reusing the old "My Profile" Window for the new Avatar Settings screen in WebUI.
  * [#1711](https://github.com/metasfresh/metasfresh/issues/1711) Support Product Search in window BOM
    * Adding Filter and Search possibility to Bill of Material Window in WebUI
  * [#1714](https://github.com/metasfresh/metasfresh/issues/1714) Add Validation Rule to Empties Return/ Receive M_Product_ID
    * Changing the Validation Rule for the M_Product_ID in window Empties Return/ Receipt so only have the restrictions in this one specific case.
  * [#1718](https://github.com/metasfresh/metasfresh/issues/1718) Fix BPartner Contact advanced edit layout
    * Finetuning of the Business Partner Contact Subtab Layout.
  * [#1722](https://github.com/metasfresh/metasfresh/issues/1722) New Window for Request Status in WebUI
    * Creating the Request Status Window for WebUI and adding to menu.
  * [#1723](https://github.com/metasfresh/metasfresh/issues/1723) Adjustments to Request Window in WebUI
    * Rearrangements to Request Window in WebUI. Harmonizing Look & Feel to current Layout default.
  * [#1729](https://github.com/metasfresh/metasfresh/issues/1729) Adjustments to window Product Category in WebUI
    * Adjusting different windows to out default Layout and Look&Feel including Product Category, Unit of Measure and Attribute Window.
  * [#1730](https://github.com/metasfresh/metasfresh/issues/1730) New Window for Discount Maintenance in WebUI
    * New Window for Discount Maintenance in WebUI. Allowing to define flat discounts or discount Quantity breaks as Discount Schema.

* metasfresh-webui-api
  * [#86](https://github.com/metasfresh/metasfresh-webui-api/issues/86) Check and get rid of "Parameter 'UOMConversion' not found in context" console warnings
    * Maintenance Issue, removing all UOM Conversions from Dicplay Logics (wasn't used anywhere until now).
  * [#376](https://github.com/metasfresh/metasfresh-webui-api/issues/376) Hide Roles on Logon that are not ideal for WebUI
    * New feature, allowing to qualify certain Roles for Usage in Web User Interface.
  * [#410](https://github.com/metasfresh/metasfresh-webui-api/issues/410) Provide view row field zoom-into endpoint
    * New feature allowing to Zoom-To on included Subtab row level.
  * [#413](https://github.com/metasfresh/metasfresh-webui-api/issues/413) Subtab Fields not shown in Advanced Edit although Displayed = 'Y'
    * Now the Subtab Fields are shown in Advanced Edit mode when Displayed = 'Y'.
  * [#414](https://github.com/metasfresh/metasfresh-webui-api/issues/414) Referenced Windows for Subtab Records
    * New Functionality to jump to referenced data from SUbtab rows. Opens a new browser Tab with the referenced window and record.
  * [#415](https://github.com/metasfresh/metasfresh-webui-api/issues/415) Notification for Vendor returns jumps to wrong window
    * New User Interface Functionality allowing to define different  Window References for the same underlying table and therefor providing possibilities to zoom-to different windows with the same table depending on the business case.
  * [#416](https://github.com/metasfresh/metasfresh-webui-api/issues/416) AD_UI_Section's Name and Description shall be translatable
    * The Section Names for Fieldgroup creation are now translatable in WebUI.
  * [#424](https://github.com/metasfresh/metasfresh-webui-api/issues/424) Implement picking window
    * First prototype of the picking Terminal overview and selection screen in WebUI.
  * [#425](https://github.com/metasfresh/metasfresh-webui-api/issues/425) API support for bookmarking menu items
    * Adding the API Support for Menu entry bookmarks.
  * [#426](https://github.com/metasfresh/metasfresh-webui-api/issues/426) internal: JSONDocument - drop "fields" array field
    * Clean-Up Issue/ Maintenance Task for JSOn Document eliminating the "fields" property.
  * [#427](https://github.com/metasfresh/metasfresh-webui-api/issues/427) space in Field shall provide first dropdown results
    * New Functionality in Lookup Fields. Just press space key and receive a list of first available selections.

* metasfresh-webui-frontend
  * [#600](https://github.com/metasfresh/metasfresh-webui-frontend/issues/600) Lookup revamp – collective issue
    * Completely redone lookup widget functionality in Web User Interface allowing to select elements of combined fields separately. Additionallly allowing a smooth and efficient assistant like workflow during data recording.
  * [#681](https://github.com/metasfresh/metasfresh-webui-frontend/issues/681) KPI maximize w/ Data Table
    * New Feature in metasfresh Dashboard now adding detail Datatables on which the KPI are based on.
  * [#713](https://github.com/metasfresh/metasfresh-webui-frontend/issues/713) KPIs: In case datasets is empty or missing shall show "No data" text
    * The Dashboard now does sow "No data" instaead of error, when no Data is available.
  * [#773](https://github.com/metasfresh/metasfresh-webui-frontend/issues/773) frontend: JSON documents: always use "fieldsByName" instead of "fields"
    * Internal Task for changing API responses for single documents, vie data and Patches.
  * [#781](https://github.com/metasfresh/metasfresh-webui-frontend/issues/781) Sorting of Included Tab columns
    * Now it is possible to sort in Included Subtabs. Also possible now: Intial Order By criteria via Window Layout.
  * [#789](https://github.com/metasfresh/metasfresh-webui-frontend/issues/789) Advanced Edit w/ key [ctrl]+e too many [tab] for scroll
    * Nice Usability Improvement for the keyboard Navigation in Advanced Edit Mode. Scroll w/ page-up/ page-down can be used directly after opening the Advanced Edit mode.
  * [#790](https://github.com/metasfresh/metasfresh-webui-frontend/issues/790) Advanced edit section header for better structuring
    * Adding the possibility to have Fieldgroups in WebUI Advanced Edit Windows.
  * [#797](https://github.com/metasfresh/metasfresh-webui-frontend/issues/797) Action/ Quickaction Layout wrong
    * Refining the Action Buttons Layout and visibility to enhance User Experience, keeping the Buttons now visible for the user when opening the included view.
  * [#801](https://github.com/metasfresh/metasfresh-webui-frontend/issues/801) Included row: show row's references in context menu
    * New Functionality allowing to jump to referenced from included Subtab Views.
  * [#809](https://github.com/metasfresh/metasfresh-webui-frontend/issues/809) Kickstart Avatar button
    * First prototype of the Avatar Button, moving the settings out of the main menu and incluing into Avatar and profile area.
  * [#819](https://github.com/metasfresh/metasfresh-webui-frontend/issues/819) HTTP header "Accept-Language" shall be correctly set
    * Now setting the Language Header in API-calls correctly.
  * [#820](https://github.com/metasfresh/metasfresh-webui-frontend/issues/820) Combobox's current value shall be selected by default in dropdown
    * Opening a dropdown combo on a fields with existing value now initially selects the value in the dropdown list too.

* other
  * [metasfresh-documentation#71](https://github.com/metasfresh/metasfresh-documentation/issues/71) Describe how to update database in local dev env
    * Thx [@daveyx](https://github.com/daveyx)

## Fixes

* metasfresh-backend
  * [#1559](https://github.com/metasfresh/metasfresh/issues/1559) Minor Invoice Layout Issues
    * Minor Adjustments in the Jasper Reports Layout of the invoice Print Format.
  * [#1581](https://github.com/metasfresh/metasfresh/issues/1581) Changing the qty in PP_Order causes error
    * Fixes a Bug that occurres when trying to change the quantity ordered in Manufacturing Order.
  * [#1640](https://github.com/metasfresh/metasfresh/issues/1640) fix prepare_services_superuser.sh
    * Maintenance & fix Issue for the prepare_services shell script.
  * [#1656](https://github.com/metasfresh/metasfresh/issues/1656) No Packing Item in jaspers
    * Hiding the Packing Material Names in Jasper Reports when its a "No Packing Item".
  * [#1712](https://github.com/metasfresh/metasfresh/issues/1712) Wrong Handling Unit Assignment in Shipment Schedule via aggregated Picking
    * Provides a solution for combined picking of Handling Units for more than 1 Shipment Schedlule Line atb the same time.
  * [#1739](https://github.com/metasfresh/metasfresh/issues/1739) Menu entry for M_ProductCategory window wrong
    * Adding the correct window to the WebUI menu for Product Category Window.
  * [#1759](https://github.com/metasfresh/metasfresh/issues/1759) HUTransform might pick the wrong capacity definition
    * Fixes an error that occured when selecting a specific Packing Material in Handling Unit Transform Action. In minor cases the wrong capacity configuration was chosen and processed.

* metasfresh-webui-api
  * [#418](https://github.com/metasfresh/metasfresh-webui-api/issues/418) Disable zoom into string lookups until it's fixed
    * Avoiding current error, disabling zoom-to String fields as long as not implemented.
  * [#428](https://github.com/metasfresh/metasfresh-webui-api/issues/428) internal: Include AD_Language in layout ETags
    * Adding the AD_Language into eTags, allowing the language siwtiching to happen instantly without refreshing in other browser windows.
  * [#431](https://github.com/metasfresh/metasfresh-webui-api/issues/431) Create request from BPartner not working
    * Fixes a Bug in the "Create new Partner" action that can be called in Lookup fields.

* metasfresh-webui-frontend
  * [#714](https://github.com/metasfresh/metasfresh-webui-frontend/issues/714) Included tab shall preserve the retrieve order
    * When opening the window in WebUI, the fontend now respects the ordering sequence transmitted by the webui-api.
  * [#744](https://github.com/metasfresh/metasfresh-webui-frontend/issues/744) product/packing lookup does nothing when clicking on the single result
    * Optimizing the behavior of Lookup Dropdowns in cases of multiple one or nor List entries.
  * [#804](https://github.com/metasfresh/metasfresh-webui-frontend/issues/804) Clicking on a menu item shall open the view without any filter
    * When Zooming-To an new window and the user navigates with breadcrumb or sitemap, now the view-id filtering is initializes. This way the user can easily rest the filter the window was opened with.
  * [#810](https://github.com/metasfresh/metasfresh-webui-frontend/issues/810) quickInput endpoints are wrongly called
    * Internal fix for the api endpoint calls sometimes missing the correct Tab_ID.
  * [#811](https://github.com/metasfresh/metasfresh-webui-frontend/issues/811) Print Preview Stopped working
    * Fixes a Bug with Print action, that did not show the print preview in a new Tab anymore.
  * [#812](https://github.com/metasfresh/metasfresh-webui-frontend/issues/812) 1 Key Press save action error during typing
    * Fixes a Bug that sometimes occured inString fields, letting the frontend save after each character recording.
  * [#813](https://github.com/metasfresh/metasfresh-webui-frontend/issues/813) included row: typeahead and dropdown endpoints are wrongly called
    * Fix for Lookup fields that did not display the dropdown list correctly, mostly empty because the endpoint was wrongly called.
  * [#814](https://github.com/metasfresh/metasfresh-webui-frontend/issues/814) Deleting a row in included tab makes the included tab empty
    * Fixes a Bug that undisplayed all rows of a Subtab when deleting one of the included rows.
  * [#815](https://github.com/metasfresh/metasfresh-webui-frontend/issues/815) attributes are missing
    * Fixing a Bug that prevented attributes to be shown in manufacturing order issue/ receipt.
  * [#816](https://github.com/metasfresh/metasfresh-webui-frontend/issues/816) Double arrow-down for Dropdown selection
    * Fixes a glitch when autocomplete usage and selection took 2 keystrokes with arrow down. Now works with auto selection of first entry and direct reaction after first arrow-down.
  * [#823](https://github.com/metasfresh/metasfresh-webui-frontend/issues/832) Create new bpartner (from lookup) not working
    * Fixes a Bug in the "Create new Partner" action that can be called in Lookup fields.
  * [#829](https://github.com/metasfresh/metasfresh-webui-frontend/issues/829) Filter criteria not shown when typing error
    * Eliminates a Bug in Filtering and Typeahead. Now the Search criteria is shown and possibility to type more than 1 character is provided.
  * [#830](https://github.com/metasfresh/metasfresh-webui-frontend/issues/830) Layout broken in included Row Grid
    * Fixing the Layout of Included Grid Rows, now showing the element connections nicely again.
  * [#842](https://github.com/metasfresh/metasfresh-webui-frontend/issues/842) Lookup Field clear w/o selection does not work
    * Now it is possible to uses the clear-x-circle to empty recording before initial confirmation with [enter]
  * [#860](https://github.com/metasfresh/metasfresh-webui-frontend/issues/860) Change Placeholder name "Marcus Gronholm" to "Profile"
    * Adding another name as placeholder in Avatar dropdown, as long as name is not taken from Avarat API property.

# metasfresh 5.11 (2017-21)

## Features

* metasfresh-backend
  * [#1389](https://github.com/metasfresh/metasfresh/issues/1389) Make eMail adress fields longer
    * Enhancing the lenght of the eMail address field in Business Partner Contact Window/ Tab. Allowing also to include multiple eMail Addresses semicolon seperated.
  * [#1506](https://github.com/metasfresh/metasfresh/issues/1506) Add the possibility to track easily contracts that ended naturally
    * New feature that adds more transparency to the contract management, allowing to easily track contracts that are resigned.
  * [#1507](https://github.com/metasfresh/metasfresh/issues/1507) Add the possibility to close or not invoice candidates when canceling a contract
    * New feature that prohibits invoice candidates to be invoiced after ther referenced contract has been terminated.
  * [#1560](https://github.com/metasfresh/metasfresh/issues/1560) Add description fields to invoice window
    * Adding the Prefix and Suffix Description Fields for Invoice Documents. These Fields allow the user to write an individual text that shall be shown in the beginning and at the end of an Invoice Doument.
  * [#1577](https://github.com/metasfresh/metasfresh/issues/1577) Introduce /test/ping/notifications server troubleshooting endpoint
    * Internal Issue adding a Testing possibility that checks if websockets is available after starting metasfresh.
  * [#1584](https://github.com/metasfresh/metasfresh/issues/1584) New Window for Org in WebUI
    * Adding a window that allows the maintenance of Organisations in WebUI.
  * [#1585](https://github.com/metasfresh/metasfresh/issues/1585) New Window Client in WebUI
    * Adding a window that allows the maintenance of Clients in WebUI.
  * [#1586](https://github.com/metasfresh/metasfresh/issues/1586) New Window for OrgType in WebUI
    * Adding a window that allows the maintenance of Organisation Type Settings in WebUI.
  * [#1591](https://github.com/metasfresh/metasfresh/issues/1591) Password Field is missing in user window in WebUI
    * Adding the Password Field in User Window of WebUI.
  * [#1595](https://github.com/metasfresh/metasfresh/issues/1595) Translate Important Doc Types to en_US
    * Translation for a subset of Document Types to Language en_US.
  * [#1598](https://github.com/metasfresh/metasfresh/issues/1598) update jaxb maven plugin to 2.3.1
    * Solves an internal issue updating the jaxb maven plugin to new version 2.3.1
  * [#1602](https://github.com/metasfresh/metasfresh/issues/1602) Show manufactoring order documentno in production order overview
    * Now the Manufacturing Order No. is shown in Grid overview
  * [#1606](https://github.com/metasfresh/metasfresh/issues/1606) Add IsQualityReturnWarehouse flag to warehouse window WebUI
    * Adding the new Field isQualityReturnWarehouse to Main View of Warehouse Window in WebUI.
  * [#1609](https://github.com/metasfresh/metasfresh/issues/1609) Provide Basic Test config Data for manufacturing / production
    * Includes some initial settings for manufacturing that provides a good starting point for new users in this topic.
  * [#1612](https://github.com/metasfresh/metasfresh/issues/1612) New Window for Business Partner Group in WebUI
    * Created a new Window in WebUI that allows different settings for Business Partner Groups.
  * [#1614](https://github.com/metasfresh/metasfresh/issues/1614) New Window Attribute Set Instance in WebUI
    * Adds the new Window for Attribute Set Maintenance to WebUI.
  * [#1617](https://github.com/metasfresh/metasfresh/issues/1617) Check if imported esr file is v11 file
    * Adding a consitency check in Swiss ESR File Processing, allowing to import files only w/ the defined format.
  * [#1621](https://github.com/metasfresh/metasfresh/issues/1621) Adjustments of Pricelist window in WebUI
    * Adjusting the Pricelist Window in WebUI, renaming Fields, Adding Translations, Order by Valid from in Subtab.
  * [#1627](https://github.com/metasfresh/metasfresh/issues/1627) Adjustments of Business Partner window in WebUI
    * Adjustments, Refining of Business Partner Window with Subtabs and new Fields.
  * [#1628](https://github.com/metasfresh/metasfresh/issues/1628) Sales Order Window Advanced edit rearrangement
    * Refining the Sales Order window for a better Layout and Look and Feel. Rearranging the Advanced edit to allow better visibility and navigation.
  * [#1642](https://github.com/metasfresh/metasfresh/issues/1642) Column Resizing in Sales Order Grid View
    * Finetuning in Sales Order Grid View, making important fields larger so their content is not cut off.
  * [#1650](https://github.com/metasfresh/metasfresh/issues/1650) Resize Fields in Pricelist Window and Field and Layout adjustments
    * Adding various windget sizes to fields shown in Grid View (Main and Subtab) to allow a better visibility, readability)

* metasfresh-webui-api
  * [#396](https://github.com/metasfresh/metasfresh-webui-api/issues/396) Vendor Return Actions in WebUI
    * Adding the Vendor Return Actions to WebUI Handling Unit Editor in Material Receipt.
  * [#409](https://github.com/metasfresh/metasfresh-webui-api/issues/409) backend: change view attributes endpoint location
    * Internal Issue about switching the Attributes endpoint in WebUI.
  * [#411](https://github.com/metasfresh/metasfresh-webui-api/issues/411) Provide "size" to layout element
    * New feature for Admins, allowing to change the size of widgets in grid view to allow a better distribution of columns on each data table.
  * [#412](https://github.com/metasfresh/metasfresh-webui-api/issues/412) Provide API for sorting included tab
    * Functionality that allows to define sorting critieria for included tabs.


* metasfresh-webui-frontend
  * [#762](https://github.com/metasfresh/metasfresh-webui-frontend/issues/762) Empty Filter vs. Default Value in Window Filtering
    * New Design and Functionality for the Filter Definition of Y/N Fields.
  * [#763](https://github.com/metasfresh/metasfresh-webui-frontend/issues/763) grid: right click on a cell and zoom into
    * New Zoom-To Functionality in grid rows, allowing the user to right click on a table cell and selecting Zoo-To. A new browser Tab is created with the referenced window and record.
  * [#764](https://github.com/metasfresh/metasfresh-webui-frontend/issues/764) Long text boxes shall have a border around
    * New Layout element for long text fields. These fields now have a surrounding visible border.
  * [#765](https://github.com/metasfresh/metasfresh-webui-frontend/issues/765) Date + Time Info in Notification window
    * Adds Date and Time Infomration as Tooltip Overlay in Notification window.
  * [#772](https://github.com/metasfresh/metasfresh-webui-frontend/issues/772) automatic Column sizing not showing all text although space would be available
    * Optimizing the Size mechanism für Window and Field Layout createn.
  * [#778](https://github.com/metasfresh/metasfresh-webui-frontend/issues/778) Yellow pulse effect w/o data change
    * Restricting the pulse effect only to changed data.
  * [#787](https://github.com/metasfresh/metasfresh-webui-frontend/issues/787) window endpoint: change "attribute" to "field"
    * Adjusting API Calls, chinging used endpoints to avoid using deprecated ones.

## Fixes

* metasfresh-backend
  * [#1566](https://github.com/metasfresh/metasfresh/issues/1566) Invoicing problem with reversed inouts that have a quality discount
    * Fix that solves an issue when processing Invoice candidates that are associated with inoutlines which are reversed and have a quclity discount recorded.
  * [#1578](https://github.com/metasfresh/metasfresh/issues/1578) spring-boot services try to start local broker and fail
    * Internal task that solves an issue with failing spring boot services.
  * [#1607](https://github.com/metasfresh/metasfresh/issues/1607) DB Function dba_seq_check_native needs to explicitly work in public schema
    * Fixes an issue in sequence generation, trying to create sequences in wrong, active db schemas.
  * [#1615](https://github.com/metasfresh/metasfresh/issues/1615) Address lines is cut off on shipment jasper document
    * Resizing and adjustments of Address lines in documents. Thanks to @thelightsense for reporting and testing
  * [#1624](https://github.com/metasfresh/metasfresh/issues/1624) NPE when closing the pp_order
    * Fixing a Bug that caused a Null Pointer Exception after closinf a manufacturing Order.
  * [#1639](https://github.com/metasfresh/metasfresh/issues/1635) cannot complete distribution order
    * Fixing a Bug that restricted the closing of Distribution Orders.

* metasfresh-webui-api
  * [#378](https://github.com/metasfresh/metasfresh-webui-api/issues/378) Document changes: provide the validStatus and saveStatus only when changed
    * Adjustments to the API of Documents, now prividing the validStatus/ saveStatus only when these are change.
  * [#405](https://github.com/metasfresh/metasfresh-webui-api/issues/405) Backend shall provide JSON document fields indexed by field's name
    * Internal Issue to improve performance using name indexed JSON document fields in Map.
  * [#407](https://github.com/metasfresh/metasfresh-webui-api/issues/407) View filtering by virtual SQL column not working
    * Fixing the usage of virtual columns in Filtering criteria.

* metasfresh-webui-frontend
  * [#753](https://github.com/metasfresh/metasfresh-webui-frontend/issues/753) Location dropdown missing in Order windows
    * Fixes the missing Dropdown List for Locations in combined Businesspartner Lookups.  
  * [#760](https://github.com/metasfresh/metasfresh-webui-frontend/issues/760) Fix the texts displayed while loading
    * Optimizing the Text shown to the user when lazy loading actions and referenced.
  * [#766](https://github.com/metasfresh/metasfresh-webui-frontend/issues/766) issue panel doesn't open
    * Fixes a Bug in Manufacturing Action Issue and Receipt opening the Issue modal overlay.
  * [#767](https://github.com/metasfresh/metasfresh-webui-frontend/issues/767) Got same notification several times
    * Avoiding the creation of notifications multiple times.
  * [#769](https://github.com/metasfresh/metasfresh-webui-frontend/issues/769) Honor tab stale flag also when editing in advanced mode
    * Now honoring the stale flag of included tabs also in Advanced edit mode of main view.
  * [#774](https://github.com/metasfresh/metasfresh-webui-frontend/issues/774) Fix invalid "GET /window/{windowId}/{tabId} call
    * Fixing an invalid GET after completing a sales Order.

# metasfresh 5.10 (2017-20)

## Features

* metasfresh-backend
  * [#1304](https://github.com/metasfresh/metasfresh/issues/1304) Jasper Document for Material disposal
    * New Jasper Report for the Material disposal Document.
  * [#1306](https://github.com/metasfresh/metasfresh/issues/1306) Customer Returns Functionality
    * New Functionality for the customer Returns workflow, allowing to receive and book the returns including Packing Material and empties.
  * [#1474](https://github.com/metasfresh/metasfresh/issues/1474) New Window Handling Unit Packing Instruction in WebUI
    * Creates a new Packing Instruction Window in WebUI.
  * [#1475](https://github.com/metasfresh/metasfresh/issues/1475) New Window for Handling Unit Packing Instruction Item in WebUI
    * Creates a new Window in WebUI allowing the maintenance of Packing Instruction Versions.
  * [#1485](https://github.com/metasfresh/metasfresh/issues/1485) New Window Distribution Order in WebUI
    * Adding the Distribution Order window to WebUI.
  * [#1489](https://github.com/metasfresh/metasfresh/issues/1489) Disable/delete legacy Cockpit window
    * Disabling the legacy Business Partner Cockpit Window.
  * [#1501](https://github.com/metasfresh/metasfresh/issues/1501) Rename "No Handling Unit" and "Virtual PI" PIs
    * Renames the internal Packing Items (former Virtual PI, No Handling Unit) to names that describe what these Items really are.
  * [#1504](https://github.com/metasfresh/metasfresh/issues/1504) Configure our services for logstash
    * Add an option to have metasfresh-backend, -admin, -material-dispo and -webui-api send log messages to an ELK Stack
  * [#1509](https://github.com/metasfresh/metasfresh/issues/1509) New Window for Doctype in WebUI
    * Adding a new Window for Doctype in webUI allowing the admin user to maintain Document Types via Web User Interface.
  * [#1517](https://github.com/metasfresh/metasfresh/issues/1517) intern: refactor LanguageDAO and LanguageBL
    * Internal issue, refactoring and improving the language handling in metasfresh.
  * [#1520](https://github.com/metasfresh/metasfresh/pull/1520) add .metadata/ and .recommenders/ to .gitignore
    * Thx to @homebeaver
  * [#1523](https://github.com/metasfresh/metasfresh/issues/1523) New Window for Print Format in WebUI
    * New Window in WebUI to allow doing the Print Format maintenance.
  * [#1524](https://github.com/metasfresh/metasfresh/issues/1524) New Window for Document Sequence in WebUI
    * Adding the Document Sequence Window in webUI allowing the admin user to have control over the Document Sequences in metasfresh.
  * [#1529](https://github.com/metasfresh/metasfresh/issues/1529) Fresh Migration of WebUI Menu for rel. 5.10
    * Adding the new Menu Configuration for WebUI.
  * [#1539](https://github.com/metasfresh/metasfresh/issues/1539) New Window for Product BOM in WebUI
    * Adding the BOM Product Window to WebUI. Now allowing the easy BOM Configuration via the Web User Interface.
  * [#1541](https://github.com/metasfresh/metasfresh/issues/1541) New Window for Packing Configuration in WebUI
    * Adding the Packing Configuration (readonly) Window to webUI.
  * [#1546](https://github.com/metasfresh/metasfresh/issues/1546) Add login Field to User Window in WebUI Main View
    * Move the Login Field from Advanced Edit to Main View in User Window of WebUI.
  * [#1551](https://github.com/metasfresh/metasfresh/issues/1551) Jasper Document for Customer Returns
    * New Jasper Report for the Customer Returns Document.
  * [#1555](https://github.com/metasfresh/metasfresh/issues/1555) add default search fields for business partner window
    * Adding default search filters for the Business Partner Window in WebUI.
  * [#1561](https://github.com/metasfresh/metasfresh/issues/1561) Rearrange Role Window w/ more important fields on Main view
    * Adjustments of Main View and advanced Edit field content in Role Window. Moving Fields according to their importance/ usage.
  * [#1572](https://github.com/metasfresh/metasfresh/issues/1572) User Window adjustment in WebUI
    * Adjustment of User Window in WebUI, Harmonizing Look&Feel, important Fields to Main View.
  * [#1574](https://github.com/metasfresh/metasfresh/issues/1574) New Field for Menu Root Node in Role window of WebUI
    * Adding a new Field to Role Window of WebUI, Menu Root Node ID, allowing to define partial Menus as Main Menu for a given Role.

* metasfresh-webui-api
  * [#362](https://github.com/metasfresh/metasfresh-webui-api/issues/362) Add filters in Handling Units window
    * Adding default filters to the Handling Unit Window in WebUI.
  * [#364](https://github.com/metasfresh/metasfresh-webui-api/issues/364) Replace HU editor's Barcode quick action with Barcode filter
    * Replacing the action based Barcode selection with the new special Barcode Filter.
  * [#366](https://github.com/metasfresh/metasfresh-webui-api/issues/366) Make Permission change active without server cache reset
    * First Implemantation of the distributed Cache invalidation. Now it's possible to change role permissions without the need of a  server cache reset.
  * [#370](https://github.com/metasfresh/metasfresh-webui-api/issues/370) Avoid spamming the console with "Connection refused" when elasticsearch connection is not available
    * Housekeeping task about log messages when elasticsearch is not available.
  * [#372](https://github.com/metasfresh/metasfresh-webui-api/issues/372) backend: endpoint for creating a filtered view
    * New Functionality in WebUI backend that allows to provide filtered views for the frontend.
  * [#388](https://github.com/metasfresh/metasfresh-webui-api/issues/388) grid view: select "ALL" support
    * New Feature that allows to select more records than are visible on the page. The new selection possibility allows the user to select all records which are available with the current filtering. These selected records can then also be used for following processes or quickactions.
  * [#390](https://github.com/metasfresh/metasfresh-webui-api/issues/390) Change advanced edit layout
    * Adjusting the advanced edit mode to the Main View Layout. This makes it easier for the user to find additional fields that are not displayed on Main View and makes a more tidy look & feel.
  * [#391](https://github.com/metasfresh/metasfresh-webui-api/issues/391) role: besides menu tree, allow configuring the sub-tree to be used
    * New feature that allows an easier Menu maintenance in future via Menu Nodes that can be selected as Root of main menu for a given role.


* metasfresh-webui-frontend
  * [#679](https://github.com/metasfresh/metasfresh-webui-frontend/issues/679) Record selection behavior w/ browser back/ forward
    * New Implementation of a refined Browser Navigation in metasfresh. It is now possible to use the browser back button and also persist the previously made selection in Grid View Lines.
  * [#738](https://github.com/metasfresh/metasfresh-webui-frontend/issues/738) Use Key in long drop down list
    * New Alphanumeric key handling in drop down list. The user can press the first key and the cursor jumps to the first found apperarance in the drop down list.
  * [#739](https://github.com/metasfresh/metasfresh-webui-frontend/issues/739) When included view is displayed add an alpha layer over the main view
    * The new included view implementation is now better focussable for the user. The remaining non active main view has an opacity overlay, to avoid distraction for the user.
  * [#740](https://github.com/metasfresh/metasfresh-webui-frontend/issues/740) When filtering a view please use the newly introduced "/documentView/{windowId}/{viewId}/filter" endpoint
    * Internal housekeeping task moving the filtering implementations to a new endpoint.
  * [#742](https://github.com/metasfresh/metasfresh-webui-frontend/issues/742) Filter singleOverlayField support
    * Adding a new Overlay method to WebUI allowing to create special Filtering screens. First implementation done with Barcode Filter functionality in Manufacturing Issue and Receipt Editor.
  * [#743](https://github.com/metasfresh/metasfresh-webui-frontend/issues/743) modal panel: background looks a bit ugly
    * Adjustment of the Look and Fied design of the modal overlay.
  * [#752](https://github.com/metasfresh/metasfresh-webui-frontend/issues/752) Select-all-filtered option in general
    * Providing the possibility to use the select-all filtering generally/ systemwide.

## Fixes

* metasfresh-backend
  * [#1515](https://github.com/metasfresh/metasfresh/issues/1515) Project "de.metas.material.dispo" shall not be part of Swing client
    * Extracting the project material dispo from swing client.
  * [#1516](https://github.com/metasfresh/metasfresh/issues/1516) HU Transform - split out some TUs from LU does not work correctly with custom LUs
    * Fix for the Split Functionality in handling Unit Editor. Sometimes the Split off for Transport Units did not work properly.
  * [#1521](https://github.com/metasfresh/metasfresh/issues/1521) standard database: add SSCC attribute to "DIM_Barcode_Attributes" dimension spec
    * Adding the SSCC Attribute to the default database seed of metasfresh.
  * [#1580](https://github.com/metasfresh/metasfresh/issues/1580) NPE when setting up production ressource
    * Fixing a Null Pointer Exception when inserting an new manufacturing ressource.

* metasfresh-webui-api
  * [#302](https://github.com/metasfresh/metasfresh-webui-api/issues/302) jasper processes default values not taken
    * Fix for Jasper Processing in WebUI. In a minor case some default values were not considered when doing the Jasper Processing.
  * [#363](https://github.com/metasfresh/metasfresh-webui-api/issues/363) make LU not mandatory in Receive HUs
    * Fix for the Field Logistic Unit in Receive Handling Units action. Now allowing to receive without a Logistics Unit.
  * [#369](https://github.com/metasfresh/metasfresh-webui-api/issues/369) adding role to user does not work
    * Fix for Role Permissions in WebUI. Adding of new Users to a roles was not working anymore.
  * [#377](https://github.com/metasfresh/metasfresh-webui-api/issues/377) Don't show zoom-to for non-zoom reference Lists
    * Restricting the new zoom-to action in WebUI. Not allowing non-zoom reference Lists anymore.
  * [#381](https://github.com/metasfresh/metasfresh-webui-api/issues/381) ZoomInto shall consider AD_Ref_List.AD_Reference_ID if any
    * Enhancing zoom-to, now considering the Reference List ID if available.
  * [#380](https://github.com/metasfresh/metasfresh-webui-api/issues/380) readonly permission in webui
    * Possibility to use the readonly Layout configuration also in WebUI now.
  * [#382](https://github.com/metasfresh/metasfresh-webui-api/issues/382) menu is not updating when you change permission
    * After changing a permission in Role window, the menu was not adjusted to the new permission.
  * [#386](https://github.com/metasfresh/metasfresh-webui-api/issues/386) Purchase Order from Sales Order Parms not initially filled in WebUI
    * Now setting the needed default values in Processes when calling actions from window Main view.
  * [#375](https://github.com/metasfresh/metasfresh-webui-api/issues/375) Adding Role for User w/o Org_access restricts access totally
    * Fixing a Bug with All Orgs Access permission, which restricted the access totally after setting.
  * [#389](https://github.com/metasfresh/metasfresh-webui-api/issues/389) Zoom-Into error for subtab orderline
    * Fixing a Bug when using zoom-to for subtab lines of a main window.
  * [#392](https://github.com/metasfresh/metasfresh-webui-api/issues/392) barcode field is readonly
    * Fix of the barcode field. Was readonly and should be editable.
  * [#393](https://github.com/metasfresh/metasfresh-webui-api/issues/393) Manufacturing Issue/Receipt: cannot receive HUs
    * Fix for the receive Handling Units error, that prevented the receipt of new Handling Units.

* metasfresh-webui-frontend
  * [#706](https://github.com/metasfresh/metasfresh-webui-frontend/issues/706) Manufacturing order - Issue HUs - wrong /quickActions call
    * Fix for the action menu behavior in Manufacturing Order Window of WebUI. Now the actions are fitting to the selected rows and context.
  * [#707](https://github.com/metasfresh/metasfresh-webui-frontend/issues/707) Page popup shall be closed when clicking o a page
    * The Page selection popup now closes automatically when clicking on a page selection.
  * [#715](https://github.com/metasfresh/metasfresh-webui-frontend/issues/715) KPI widgets shall have the same height
    * Adjustment to the KPI widget heights. Now all widgets have the same height and result in a tidier look and feel of the metasfresh Dashboard.
  * [#732](https://github.com/metasfresh/metasfresh-webui-frontend/issues/732) issue not opening on single document
    * Fixing an error that avoided the opening of the action issue window in Manufaturing workflow.
  * [#734](https://github.com/metasfresh/metasfresh-webui-frontend/issues/734) included view: don't reset when closing an error notification
    * Fix in included view. Not resetting the context anymore when clicking on an error notification.
  * [#749](https://github.com/metasfresh/metasfresh-webui-frontend/issues/749) singleOverlayField: pressing ESC shall close the popup
    * Fix for the single field overlay, now allowing to press [esc] to close that overlay.
  * [#751](https://github.com/metasfresh/metasfresh-webui-frontend/issues/751) Initial Setup Wizard does not open after switching language
    * Fixing a Bug in Initial setup wizard that did not open anymore when using the language selector previously.
  * [#755](https://github.com/metasfresh/metasfresh-webui-frontend/issues/755) mandatory numeric field is underlined with blue, even if it's set
    * Adjustment in the mandatory field look & feel for number fields. Although a number was set, the mandatory logic said that the fields still has to be recorded.
  * [#759](https://github.com/metasfresh/metasfresh-webui-frontend/issues/759) Action button remains gray if the process execution fails with error 404
    * Fixing an error leaving the action button greyed out after running into a previous 404 error.

# metasfresh 5.9 (2017-19)

## Features

* metasfresh-backend
  * [#1305](https://github.com/metasfresh/metasfresh/issues/1305) Jasper Document for Vendor Returns
    * Created a new Jasper Document that allows to Vendor Return Confirmation.
  * [#1320](https://github.com/metasfresh/metasfresh/issues/1320) Translation for nl_NL in WebUI
    * Initial Translation for nl_NL for multiple WebUI Windows and elements. Thanks to @Arjanvb for this contribution.
  * [#1460](https://github.com/metasfresh/metasfresh/issues/1460) The Bestellkontrolle barcodes shall be generated and persisted in database
    * New Barcode Generator for the Order Control report in Manufacuring. This Barcode is needed to the scan possibility in Manufacturing when starting the action issue and receipt workflow in WebUI.
  * [#1463](https://github.com/metasfresh/metasfresh/issues/1463) Add Window for S_Resource_Ressource to WebUI
    * Adding a new window to WebUI to allow maintaining Manufacturing Resources.
  * [#1464](https://github.com/metasfresh/metasfresh/issues/1464) New Window for Manufacturing Workflow in WebUI
    * Adding the Manufacturing Workflow Window to WebUI, now allowing to maintain these.
  * [#1465](https://github.com/metasfresh/metasfresh/issues/1465) New Window for Distribution Network in WebUI
    * Including the Distribution Network Window in WebUI.
  * [#1466](https://github.com/metasfresh/metasfresh/issues/1466) New Window for Resource Type in WebUI
    * Adding the ResourceType maintenance Window to WebUI.
  * [#1467](https://github.com/metasfresh/metasfresh/issues/1467) New Window for Product Planning Data in WebUI
    * Including the Window Product Planning Data to WebUI, to allow the user to maintain Manufacturing dependant data for products.
  * [#1471](https://github.com/metasfresh/metasfresh/issues/1471) Provide material-dispo and spring-boot-admin as services
    * New Material Disposition Framework alowing to start the "Make or Buy" decision nearly just-in-time. This implementation is preparing the gound for the furture metasfresh microservices framework
  * [#1473](https://github.com/metasfresh/metasfresh/issues/1473) New Window for Node Transitions in webUI
    * Creating a new Window in WebUI for Node Transition maintenance in Manufacturing Workflow.
  * [#1478](https://github.com/metasfresh/metasfresh/issues/1478) New Window for InvoiceSchedule in WebUI
    * Adding the Invoice Schedule Window to WebUI to allow the user to define invoiceing frequencies.
  * [#1486](https://github.com/metasfresh/metasfresh/issues/1486) Add Status Field to Material Disposition Window in WebUI
    * Adding the Status Field to Material Disposition Window in WebUI. Minor Adjustments in window Layout.
  * [#1487](https://github.com/metasfresh/metasfresh/issues/1487) Allow the editing of Workflow Node in Window
    * Adjustment in Window Workflow Node Transition, now allowing the user to select the Workflow Node.
  * [#1494](https://github.com/metasfresh/metasfresh/issues/1494) Add HU_PI_Item to Product Price Window in WebUI
    * Adding the Field Handling Unit Packing Instruction to Product Price Window. Minor Adjustments in Layout.
  * [#1499](https://github.com/metasfresh/metasfresh/issues/1499) Update to spring-boot 1.5.3
    * Updating spring-boot from 1.4.2 to version 1.5.3
  * [#1502](https://github.com/metasfresh/metasfresh/issues/1502) Drop the CU-TU assignment for "No PI" (M_HU_ID=100)
    * Dropping the Customer Unit/Transport Unit assignment creation for "No Packing Instructions2 Handling Units.

* metasfresh-webui-api
  * [#280](https://github.com/metasfresh/metasfresh-webui-api/issues/280) Scan of Barcode "Bestellkontrolle" for Manufacturing execution
    * New Functionality for manufacturing action issue/ receipt processing, allowing the user to scan the Order control report for efficient workflow start.
  * [#348](https://github.com/metasfresh/metasfresh-webui-api/issues/348) backend: Implement Zoom-into in webUI
    * New usability feature for zoom-into. Fields with references to other records now create a link to the corresponding records via available field label.
  * [#352](https://github.com/metasfresh/metasfresh-webui-api/issues/352) Introduce JSONLayoutType.primaryLongLabels
    * Introducing a new Lable LayoutType for longer label names.
  * [#354](https://github.com/metasfresh/metasfresh-webui-api/issues/354) Support for Record_ID buttons
    * New Functionality for table-ID record-ID buttons, allowing the user to navigate quickly into the referenced window.
  * [#356](https://github.com/metasfresh/metasfresh-webui-api/issues/356) Manufacturing Quickaction: Reverse Issue / Receipt
    * New quickaction allowing the reverse issues/ receipts planning in Action-receipt and -issue window of manufacturing in WebUI.
  * [#357](https://github.com/metasfresh/metasfresh-webui-api/issues/357) Manufacturing Quickaction: Issue selected HU w/ TU Quantity parm
    * New Quickaction in manufacturing issue/ receipt window. Allowing to issue Handling Units with a predefined quantity of Transport Units.

* metasfresh-webui-frontend
  * [#216](https://github.com/metasfresh/metasfresh-webui-frontend/issues/216) Button record_id does not open according window
    * Frontend implementation of table-id/ record-id buttons.
  * [#716](https://github.com/metasfresh/metasfresh-webui-frontend/issues/716) Image widget: button to clear current image
    * New button that allows to clear the current saved image.
  * [#719](https://github.com/metasfresh/metasfresh-webui-frontend/issues/719) Document references: group them by their top level menu
    * New Grouping functionality in referenced documents sidelist allowing a better usability.
  * [#720](https://github.com/metasfresh/metasfresh-webui-frontend/issues/720) Implement Zoom-into in webUI
    * Implementation of the new Zoom-into functionality in WebUI.

* other
  * [#1](https://github.com/metasfresh/metasfresh-dev/issues/1) extend metasfresh-eclipse-config repo to generally contain dev resources
    * Extension of the metasfresh-eclipse-config repository to generally contain development resources
  * [#2](https://github.com/metasfresh/metasfresh-dev/issues/2) add Vagrantfile for developers
    * Adding Vagrantfile for Developers.
  * [#3](https://github.com/metasfresh/metasfresh-admin/issues/3) create docker image for metasfresh-admin
    * Creating a docker image for usage of metasfresh-admin.

## Fixes

* metasfresh-backend
  * [#1179](https://github.com/metasfresh/metasfresh/issues/1179) Process logs are not displayed in Swing
    * Fixes an exception of processe which create process logs in swing client.

* metasfresh-webui-api
  * [#255](https://github.com/metasfresh/metasfresh-webui-api/issues/255) Document fields: when receiving empty string values from frontend, convert them to nulls
    * Converting empty string values to null for document fields.
  * [#351](https://github.com/metasfresh/metasfresh-webui-api/issues/351) Fix NPE when opening Materialdisposition
    * Fixes a Null Pointer Exception that happend when opening the Material Disposition Window in WebUI.
  * [#360](https://github.com/metasfresh/metasfresh-webui-api/issues/360) Material receipt candidates: Error while Receving HUs for a particular case
    * Fixes an error that occured in Material receipt window when trying to receive Handling units for a minor case.

* metasfresh-webui-frontend
  * [#705](https://github.com/metasfresh/metasfresh-webui-frontend/issues/705) Debug/Fix why /window/540189 shows empty
    * Fix in webui. Window 540189 was only shown as empty screeen.
  * [#717](https://github.com/metasfresh/metasfresh-webui-frontend/issues/717) Sidelist's scrollbar is on wrong container.
    * Adding the sidelist scrollbar now in the correct container.
  * [#724](https://github.com/metasfresh/metasfresh-webui-frontend/issues/724) Login role selection keyboard down does not scroll automatically
    * Fixing the login role selection. Now allowing the user to also select and see roles also in long role lists.
  * [#725](https://github.com/metasfresh/metasfresh-webui-frontend/issues/725) Sidelist closes when doubleclick
    * Fixing the sidelist navigational behavior. When selecting a record the sidelist now remains sticky, until the user closes it via "oustide" cklick.
  * [#727](https://github.com/metasfresh/metasfresh-webui-frontend/issues/727) Grid view: reset current page to 1 when filtering
    * Fixes the filtering functionality in grid view. Retestting the page counter to 1 after adding new Filter criteria.

# metasfresh 5.8 (2017-18)

## Features

* metasfresh-backend
  * [#796](https://github.com/metasfresh/metasfresh/issues/796) Fact Account WebUI Window Readonly
    * Including the Fact Account window to WebUI.
  * [#886](https://github.com/metasfresh/metasfresh/issues/866) Make payment callouts work in webui
    * Implementation in WebUI to also allow the Payment Callouts there.
  * [#1315](https://github.com/metasfresh/metasfresh/issues/1315) 2D Barcode wit ad_table_id/ record_id on bestellkontrolle
    * Add a 2D Barcode to each line of Order control report for manufacturing and logistics. This Barcode will be used in manufacturing to scan and trigger the manufacturing Order issue/ receipt record and workflow.
  * [#1362](https://github.com/metasfresh/metasfresh/issues/1362) Activate auto numbering for incoming and outgoing payments
    * Adjusting the Document Type setting for incoming and outgoing payment Documents. Now allowing the Document No. to be automatically create taken from Document sequences in WebUI.
  * [#1369](https://github.com/metasfresh/metasfresh/issues/1369) Translate request type to de_DE Vorgang Art
    * Adding the Translation of Request Type in webUI for language en_US.
  * [#1398](https://github.com/metasfresh/metasfresh/issues/1398) Take out "New" in C_DocTypeTarget_ID in PP_Order
    * Adjusted the behavior of Manufacturing Order Document when manually created. Taken out the Document Target "New", which only confuses user when selectable.
  * [#1399](https://github.com/metasfresh/metasfresh/issues/1399) DocumentNo not generated for PP_Orders in WebUI
    * Adding Document No. control to all manufacturing Document Types.
  * [#1400](https://github.com/metasfresh/metasfresh/issues/1400) DocumentNo not generated for empties in WebUI
    * Adding the Document No. control to the empties return document.
  * [#1401](https://github.com/metasfresh/metasfresh/issues/1401) Translate Process "New Request" to de_DE
    * Translating the jump Navigation of "New Request" to de_DE.
  * [#1403](https://github.com/metasfresh/metasfresh/issues/1403) technical: refactor TaxNotFoundException
    * Technical Task to make the Tax selection easier and more robust in background implementation.
  * [#1405](https://github.com/metasfresh/metasfresh/issues/1405) Adjust Empties Return Window to mandatory field grouping layout
    * Adjusting empties Return Window to mandatory Field primary layout.
  * [#1408](https://github.com/metasfresh/metasfresh/issues/1408) Create the WebUI Layout for Material Candidates
    * Creating the initial Layout for the Material Candidates Window in WebUI.
  * [#1411](https://github.com/metasfresh/metasfresh/issues/1411) Possible 1:1 switch for Data Lifecycle Management users
    * New feature to allow individual users to be switched on/ off for the Data Lifecycle Management functionality. Before it was only possible to switch all users on for the same environment.
  * [#1417](https://github.com/metasfresh/metasfresh/issues/1417) Window for c_activity in WebUI
    * Adding the Activity window to WebUI.
  * [#1418](https://github.com/metasfresh/metasfresh/issues/1418) Window for c_bank in WebUI
    * Adding the Bank Master data window definition to WebUI.
  * [#1419](https://github.com/metasfresh/metasfresh/issues/1419) Window for c_currency in WebUI
    * Adding the Currency master data window definition to WebUI.
  * [#1424](https://github.com/metasfresh/metasfresh/issues/1424) Fresh Migration of WebUI Menu for rel. 5.8
    * Building an updated menue in WebUI for Release 5.8
  * [#1425](https://github.com/metasfresh/metasfresh/issues/1425) make price field bigger in sales invoice document
    * Resizing the price field in sales Invoice document, now allowing greater prices.
  * [#1429](https://github.com/metasfresh/metasfresh/issues/1429) Window for Bank Account in WebUI
    * Created a new Bank Account window and added to WebUI
  * [#1430](https://github.com/metasfresh/metasfresh/issues/1430) Window for c_conversion_rate in WebUI
    * Adding the Currency Conversion rate window to WebUI.
  * [#1432](https://github.com/metasfresh/metasfresh/issues/1432) technical: HUReportService shall work with AD_Process_ID instead of I_AD_Process
    * Technical Task to improve performance and reduce possible caching issues.
  * [#1435](https://github.com/metasfresh/metasfresh/issues/1435) Add method for Escaping '@' char, by replacing one @ with double @@
    * Internal Implementation, allowing to escape @ characters.
  * [#1438](https://github.com/metasfresh/metasfresh/issues/1438) Fix spring scanBasePackageClasses and introduce profiles
    * Adjusting the spring boot configurations for the different metasfresh applications to allow a better maintenance.
  * [#1442](https://github.com/metasfresh/metasfresh/issues/1442) Window for C_Doc_Outbound_Config in WebUI
    * This new Window in WebUI allows to do the configuration of outbound Documents.
  * [#1444](https://github.com/metasfresh/metasfresh/issues/1444) Window for C_Doc_Outbound_Log in WebUI
    * New Window for the maintenance of outbound Documents in webUI.
  * [#1446](https://github.com/metasfresh/metasfresh/issues/1446) Add filter to currency conversion window in WebUI
    * Adding Filter criteria to currency conversion window.
  * [#1451](https://github.com/metasfresh/metasfresh/issues/1451) Add scale Price Tab in Window Product Price
    * Adding the Scale Price Tab in Product Price Window on WebUI.
  * [#1456](https://github.com/metasfresh/metasfresh/issues/1456) Translation of Initial Setup Wizard Process Window and Menu entry
    * Translation of Initial Client Setup Added to WebUI Menu for language en_US

* metasfresh-webui-api
  * [#325](https://github.com/metasfresh/metasfresh-webui-api/issues/325) make error message more clear when printing in receipt candidates
    * Enhancing error messages in Printing Framework, now allowing to receive a helpful error message when printing fails.
  * [#330](https://github.com/metasfresh/metasfresh-webui-api/issues/330) Implement webui HUEditor support for massive amount of HUs
    * Enhancing the WebUI Handling Unit Editor to allow performant usage also when loading mass data Handling Units.
  * [#334](https://github.com/metasfresh/metasfresh-webui-api/issues/334) switch filters to primary layout
    * Improving the usability and readability of process Parameters, switching the labels and fields to primary layout there.
  * [#344](https://github.com/metasfresh/metasfresh-webui-api/issues/344) backend: Document references: group them by their top level menu
    * New Layout feature in Document references for better visibility. Grouping the referenced in Sidelist via their top level (1 beneath root) Navigation node. This is the backend task, waiting for frontend implementation in the next week to visualize the reference results.

## Fixes

* metasfresh-backend
  * [#1044](https://github.com/metasfresh/metasfresh/issues/1044) System Elements w/ centrally maintained Y shall also migrate migration scripts for Translations
    * Now allowing more efficient Translation creation. System elements with centrally maintained = 'Y' now also create migration scripts when adjusted.
  * [#1353](https://github.com/metasfresh/metasfresh/issues/1353) Problem with C_Order filtering in M_ShipmentSchedule
    * Fixes a Bug when filtering Document No. and more than 1 exact result is returned. Added validation rule to exclude Purchase Orders from Search.
  * [#1440](https://github.com/metasfresh/metasfresh/issues/1440) ClassCastException: CompositeQueryFilter cannot be cast to SqlQueryFilter
    * Fixes a Bug that was introduced in Data Lifecycle Task and caused a Null Pointer Exception in Doc Outbound eMail functionality .

* metasfresh-webui-api
  * [#257](https://github.com/metasfresh/metasfresh-webui-api/issues/257) BPartner contact cannot be saved because mandatory field Name is not set
    * Fixing a Bug that Business Partner record was not saved because mandatory name field was not set. Implemented a callout which  builds the Name when the user changes Firstname or Lastname.
  * [#333](https://github.com/metasfresh/metasfresh-webui-api/issues/333) HUs child and parent get separated when both selected on issue
    * Fixes a Bug that seperated the Child-Parent-Handling Units of a Component when issueing to Manufacturing Order.
  * [#339](https://github.com/metasfresh/metasfresh-webui-api/issues/339) Sales Order also show Purchase Orders
    * Fixes a Bug that showed Purchase Orders also in the Sales Order Window.
  * [#340](https://github.com/metasfresh/metasfresh-webui-api/issues/340) Manufacturing order: Prevent issuing VHUs which are not top level
    * Fixing a Bug that did not handle Sub Handling Units correctly when selected during manufacturing Issue.
  * [#341](https://github.com/metasfresh/metasfresh-webui-api/issues/341) Apply role permissions when browsing/editing data
    * Fixes a Role Permission issue when browsing and editing data.
  * [#342](https://github.com/metasfresh/metasfresh-webui-api/issues/342) Exception in WebUI when opening initial Doc Outbound window
    * Fixes a Bug that occured when opening the new Doc Outbound window in WebUI.
  * [#345](https://github.com/metasfresh/metasfresh-webui-api/issues/345) Batch entry not working in order
    * Fixes the Batch entry in Order Windows, which was nor working anymore due to internal error.
  * [#346](https://github.com/metasfresh/metasfresh-webui-api/issues/346) Zoom into error
    * Fixing the Zoom-Accross functionality via document references.


* metasfresh-webui-frontend
  * [#675](https://github.com/metasfresh/metasfresh-webui-frontend/issues/675) Icons for manufacturing components
    * Changing the icons for manufacturing element types in Action Issue and Receipt window on WebUI to allow a better visibility.
  * [#697](https://github.com/metasfresh/metasfresh-webui-frontend/issues/697) New Businesspartner modal overlay cancel
    * Including the cancel button in modal overlay of new Business Partner window.
  * [#700](https://github.com/metasfresh/metasfresh-webui-frontend/issues/700) Typeahead not working in lookup filter parameters
    * Fixing typahead fields/ dropdowns in process parameters, that were not working.
  * [#701](https://github.com/metasfresh/metasfresh-webui-frontend/issues/701) "Filter" panel does not disappear
    * Fixing a Bug that prohibited to close Filter panels without filling mandatory fields.
  * [#702](https://github.com/metasfresh/metasfresh-webui-frontend/issues/702) Running a process from menu does not work, again
    * Fixes the Process Panel issue when a Processes is started from menu. Now the panel is shown again.
  * [#703](https://github.com/metasfresh/metasfresh-webui-frontend/issues/703) leave the page message on request modal
    * Now its possible again to leave a fully recorded request modal overlay without receiving a question about saving.
  * [#704](https://github.com/metasfresh/metasfresh-webui-frontend/issues/704) request modal saving is loading 4 tabs
    * Internal issue fixed, when saving the request modal overlay, loading multiple tabs.


# metasfresh 5.7 (2017-17)

## Features

* metasfresh-backend
  * [#427](https://github.com/metasfresh/metasfresh/issues/427) Integrate spring DI with metasfresh Services helper class
    * Now integrated Sprint Dependancy Injection into metasfresh Services.
  * [#880](https://github.com/metasfresh/metasfresh/issues/880) Improve IProcessPrecondition framework
    * Improvements on Process Framework and issue handling.
  * [#1211](https://github.com/metasfresh/metasfresh/issues/1211) Preparation Time: Change to first possible on same Day as Promised Date-Time
    * Adjustment of the Tour Planning Preparation Date calculation. Now taking the earliest Tour preparation time according to the date promised.
  * [#1300](https://github.com/metasfresh/metasfresh/issues/1300) Bank Statement Window in WebUI
    * Adding the initial window layout for Bankstatement window in webUI.
  * [#1322](https://github.com/metasfresh/metasfresh/issues/1382) Make Packing Item editable in Manufacturing Order Header
    * Making the Field Packing Item editable in Manufacturing Order header.
  * [#1347](https://github.com/metasfresh/metasfresh/issues/1347) technical: ModelClassGenerator shall generate BigDecimal.ZERO instead of Env.ZERO
  * [#1349](https://github.com/metasfresh/metasfresh/issues/1349) Create Product category webui window
    * Adding the product category window to WebUI.
  * [#1351](https://github.com/metasfresh/metasfresh/issues/1351) IsTransferWhenNull not working correctly anymore
    * Attributes which have IsTransferWhenNull=Y are now also transferred during propagation to the received product in manufacturing.
  * [#1355](https://github.com/metasfresh/metasfresh/issues/1355) Allow Business Partner group without setting price system
    * The creation of Business Partner Groups are now possible without mandatory Pricing System.
  * [#1358](https://github.com/metasfresh/metasfresh/issues/1358) Drop de.metas.order.callout.CheckDouble
  * [#1360](https://github.com/metasfresh/metasfresh/issues/1360) Translating processes and windows for order to shipment process to en_US
    * Adding the Translation of en_US for the order to shipment workflow.
  * [#1366](https://github.com/metasfresh/metasfresh/issues/1366) Fields missing in Sales Order WebUI for advanced Edit
    * Adding missing fields into advanced edit of Sales Order Window in WebUI.
  * [#1370](https://github.com/metasfresh/metasfresh/issues/1370) Make request type names translatable
    * Translated the Request Names into Language en_US.
  * [#1382](https://github.com/metasfresh/metasfresh/issues/1382) Make Packing Item editable in Manufacturing Order Header
    * Allowing to edit the Packing Instruction in Manufacturing Order.
  * [#1393](https://github.com/metasfresh/metasfresh/issues/1393) Translate New Partner Quick Input window to en_US
    * Translating the Quickentry for new Business Partners to en_US Language.
  * [#1395](https://github.com/metasfresh/metasfresh/issues/1395) Translate Payment Terms to en_US
    * Adding the Translation for Payment Terms of language en_US.

* metasfresh-webui-api
  * [#261](https://github.com/metasfresh/metasfresh-webui-api/issues/261) Date offset in Dashboard KPI because of missing Timezone
    * Adjustment of Dashboard KPI, now ofsetting the Date because of missing Timezone.
  * [#291](https://github.com/metasfresh/metasfresh-webui-api/issues/291) Show value/ name in breadcrumb but edit in window
    * Adjusting the look and feel of the breadcrumb structure and navigation.
  * [#300](https://github.com/metasfresh/metasfresh-webui-api/issues/300) Quickaction new Request in BPartner Window
    * New Functionality to quickly add a quickaction for request creation to WebUI windows.
  * [#313](https://github.com/metasfresh/metasfresh-webui-api/issues/313) Handling Unit Barcode selection 1:1
    * Adding the 1:1 selection functionality to Barcode scanning and Handling Unit selection.
  * [#314](https://github.com/metasfresh/metasfresh-webui-api/issues/314) HU attributes shall be readonly if the HUStatus is not planning
    * Handling Unit attributes are editable as long as the Handling Unit status is 'planning'.
  * [#315](https://github.com/metasfresh/metasfresh-webui-api/issues/315) Manufacturing Issue/Receipt: merge BOM Type and HU Type columns
    * Merging the Handling Unit Type and BOM Type on HU Level and Issue/ Receipt Lines to 1 column called Type.
  * [#316](https://github.com/metasfresh/metasfresh-webui-api/issues/316) Implement password widgetType support
    * Adding support for password widget type in Rest-API, preparing the frontend implementation.
  * [#317](https://github.com/metasfresh/metasfresh-webui-api/issues/317) Manufacturing Issue/Receipt: drop StatusInfo column
    * Adjustment in Manufacturing Issue and receipt, drpping the status info column in WebUI.
  * [#318](https://github.com/metasfresh/metasfresh-webui-api/issues/318) Manufacturing Issue/Receipt: show packing info for BOM components
    * Adding the Packaging Info Action Issue & Receipt Editor in new Manufacturing.
  * [#319](https://github.com/metasfresh/metasfresh-webui-api/issues/319) Manufacturing Issue/Receipt: show BOM line attributes if any
    * Adding BOM line Attributes to manufacturing Order WebUI.
  * [#321](https://github.com/metasfresh/metasfresh-webui-api/issues/321) Manufacturing Issue/Receipt: introduce planning status
    * Introducing the planning status to manufacturing Order WebUI.
  * [#322](https://github.com/metasfresh/metasfresh-webui-api/issues/322) Manufacturing Issue/Receipt: Fix Qty and QtyPlan columns
    * Adding Fix Qty and Qty Plan to manufacturing Order WebUI.
  * [#323](https://github.com/metasfresh/metasfresh-webui-api/issues/323) Manufacturing Issue/Receipt: receipt lines shall be displayed first
    * Reordering the manufacturing order ui, now ordered by receipt lines first.
  * [#326](https://github.com/metasfresh/metasfresh-webui-api/issues/326) technical: Remove JSONProcessInstanceResult deprecated properties
    * Technical issue for further improvement of JSON Messaging in Rest-API.
  * [#332](https://github.com/metasfresh/metasfresh-webui-api/issues/332) Empties window without pre-selection possible
    * Adding a new Functioanlity to allow empties receive without having preselected material receipt lines.

* metasfresh-webui-frontend
  * [#248](https://github.com/metasfresh/metasfresh-webui-frontend/issues/248) Handle Password Fields in WebUI
    * New widget for Password Handling in WebUI.
  * [#411](https://github.com/metasfresh/metasfresh-webui-frontend/issues/411) Make the parameter names visible in filters
    * Improvement in Process Parameter dropdown, now allowing to define the Parameter layout with primary/ secondary indicator.
  * [#537](https://github.com/metasfresh/metasfresh-webui-frontend/issues/537) KPI: implement a non intrusive way to display KPI related errors
    * Improvement for the KPI error Handling.
  * [#649](https://github.com/metasfresh/metasfresh-webui-frontend/issues/649) windowId shall be handled as a string and not as a number
    * Changing the format of window ID, now representing as string and not numbers.
  * [#666](https://github.com/metasfresh/metasfresh-webui-frontend/issues/666) refresh in attachments side list
    * Now refreshing the sidelist and showing the attached files after new attachments to a record.
  * [#667](https://github.com/metasfresh/metasfresh-webui-frontend/issues/667) Change /rest/api/pattribute request message
    * Internal improvement of the Rest-API request message.
  * [#676](https://github.com/metasfresh/metasfresh-webui-frontend/issues/676) Process result: implement openDocument as modal
    * New functionality for the openDocument Implementation, now allowing the modal overlay usage.
  * [#678](https://github.com/metasfresh/metasfresh-webui-frontend/issues/678) Get rid of deprecated static docNo input in header.
    * Housekeeping issue, getting rid of deprecated document No Field.
  * [#683](https://github.com/metasfresh/metasfresh-webui-frontend/issues/683) Tooltip on first level breadcrumb
    * New Tooltip on first level breadcrumb.
  * [#695](https://github.com/metasfresh/metasfresh-webui-frontend/issues/695) Larger photo preview screen/ modal overlay
    * Resizing the preview image in material receipt, allowing to preview in modal full width.
  * [#682](https://github.com/metasfresh/metasfresh-webui-frontend/issues/682) Sidelist opening shall automatically have the first line selected
    * When opening the sidelist, the first line is now automatically preselected, so the user can use arrow down directly to navigate.

* other
  * [metasfresh/metasfresh-dist-orgs#8](https://github.com/metasfresh/metasfresh-dist-orgs/issues/8) add trainings management
    * Adding Traning Management window, tab and field changes to metasfresh.

## Fixes

* metasfresh-backend
  * [#1343](https://github.com/metasfresh/metasfresh/issues/1343) Cannot reverse documents
    * Fixing the bug that prevented the usage of document reversals.
  * [#1346](https://github.com/metasfresh/metasfresh/issues/1346) Cannot "LU zuteilen" (Swing HU Editor)
    * Fixes a Bug that did not allow to add split-off Transport Units to be added to a new Logistic Unit.
  * [#1378](https://github.com/metasfresh/metasfresh/issues/1378) Packing item not displayed in receipt jasper if none was ordered
    * Decoupling the Packing Instructions in Material Receipt from orderline Packing Instructions.
  * [#1379](https://github.com/metasfresh/metasfresh/issues/1379) Problem creating FK reference on DLM'ed table
    * Fixing a issue when creating foreign key constraints on tables which are referenced to data lifecyle mangement.
  * [#1387](https://github.com/metasfresh/metasfresh/issues/1387) Permission Tabs not displayed in window Role
    * Adding the permission Tabs to window Role in webUI.
  * [#1388](https://github.com/metasfresh/metasfresh/issues/1388) HU from Cost Collector is locked and cannot be selected anymore after the CC was reversed
    * After reversing a Cost Collerctor the referenced HU is now not locked anymore.
  * [#1348](https://github.com/metasfresh/metasfresh/issues/1348) M_ShipmentSchedule_EnqueueSelection ignores role's org access

* metasfesh-webui-api
  * [#151](https://github.com/metasfresh/metasfresh-webui-api/issues/151) grid view websocket notifications: fullyChanged shall not be set when changedIds is set
    * Internal websocket improvement for grid view handling.
  * [#267](https://github.com/metasfresh/metasfresh-webui-api/issues/267) Show an error or message for user when printing not possible bc of missing config
    * Now showing an eror message to the user when printing is not possible.
  * [#268](https://github.com/metasfresh/metasfresh-webui-api/issues/268) HU attributes shall always provide the WidgetType along with the data/changes
    * WebUI Improvement of Handling Unit Attributes Widget.
  * [#270](https://github.com/metasfresh/metasfresh-webui-api/issues/270) cannot add trading unit with batch entry after using add new
    * Fix for Sales Order Batch entry in WebUI, now also allowing to add Trade Unit Packing Material after adding via "add new".
  * [#306](https://github.com/metasfresh/metasfresh-webui-api/issues/306) Address missing in 'Adresse' for bpartner created on-the-fly
    * Fix for quickentry of Business Partner and Locations via Sales Order now also adding the location name.
  * [#310](https://github.com/metasfresh/metasfresh-webui-api/issues/310) Switching language does not translate document references
    * Fix for the new Language change switch. Now also translating document references in WebUI.
  * [#311](https://github.com/metasfresh/metasfresh-webui-api/issues/311) Switching language does not translate document lookup values
    * Fix for the new Language change switch. Now also translating document lookup values in WebUI.
  * [#328](https://github.com/metasfresh/metasfresh-webui-api/issues/328) Manufacturing receipt mandatory Field not filled
    * Fix for manufacturing action_receipt. Now filling all mandatory fields.
  * [#324](https://github.com/metasfresh/metasfresh-webui-api/issues/324) File Size Limit
    * Raising the file size limit for uploading attachments to webUI.
  * [#329](https://github.com/metasfresh/metasfresh-webui-api/issues/329) Issue HU 404 not found
    * Fixing a 404 Page not found Bug when trying to issue product 2 times.

* metasfresh-webui-frontend
  * [#433](https://github.com/metasfresh/metasfresh-webui-frontend/issues/433) ShipmentSchedule: Only displayed after "No data" showing up
    * Eliminating the "No data" page when loading correctly. It should only be displayed if there actually is no data.
  * [#551](https://github.com/metasfresh/metasfresh-webui-frontend/issues/551) Error when recording Swiss location in webUI
    * Fixing a Bug when recording new locations via Business Partner Quick Input.
  * [#567](https://github.com/metasfresh/metasfresh-webui-frontend/issues/567) Date attribute is not correctly rendered
    * Fixes the rendering of the Date attribute Field.
  * [#579](https://github.com/metasfresh/metasfresh-webui-frontend/issues/579) DocumentNo field is not updated
    * Fixes the creation of new Business Partner search values.
  * [#596](https://github.com/metasfresh/metasfresh-webui-frontend/issues/596) red line appears on document only after refresh
    * Fixes the behavior of the save indicator. Now showing the Red save eroror indicators also whithout need to refresh.
  * [#641](https://github.com/metasfresh/metasfresh-webui-frontend/issues/641) Shortcuts behavior sometimes weird
    * Fixes a weird behavior of shortcut usage and navigation in WebUI action bar menues.
  * [#644](https://github.com/metasfresh/metasfresh-webui-frontend/issues/644) Creating a new Discount record error
    * Fixes a Bug when creating a new Discount.
  * [#664](https://github.com/metasfresh/metasfresh-webui-frontend/issues/664) process doesn't open directly
    * Fixing a weird behavior when opening processes, only worked when pressing something else in menu after first try.
  * [#672](https://github.com/metasfresh/metasfresh-webui-frontend/issues/672) Process error not displayed
    * Now responding errors to the user when trying to start a process without filling all mandatory fields.
  * [#673](https://github.com/metasfresh/metasfresh-webui-frontend/issues/673) Wrong report file endpoint is called for processes which are started from main menu
    * Fixes a Bug that opens a printing preview report pdf with the completely wrong URL.
  * [#680](https://github.com/metasfresh/metasfresh-webui-frontend/issues/680) Navigation breadcrumb vs. Navigation Menu
    * Unifying the behavior between opening windows via Breadcrumb menue and Navigation menue.
  * [#685](https://github.com/metasfresh/metasfresh-webui-frontend/issues/685) Cannot open manufacturing modal overlay anymore
    * Fixing a Bug that disallowed the opening of manufacturing modal overlay.
  * [#688](https://github.com/metasfresh/metasfresh-webui-frontend/issues/688) Main page /dashboard is broken
    * Fixes the main Page/ Dashboard layout.
  * [#689](https://github.com/metasfresh/metasfresh-webui-frontend/issues/689) Image widget is missing right border
    * Adding the right border to image widget.


* other
  * [#7](https://github.com/metasfresh/metasfresh-dist/issues/7) metasfresh-dist parent pom.xml's artifact name shall be metasfresh-dist
    * Now making sure that by default, the project is imported (into eclipse) as "metasfresh-dist" that name is used in some .launch files, e.g. the one to start the swing client.
  * [#6](https://github.com/metasfresh/metasfresh-parent/issues/6) still using metasfresh-snapshots repo
    * Eliminating the dependancy to metasfresh-snapshots repo.

# metasfresh 5.6 (2017-16)

## Features

* metasfresh-backend
  * [#1062](https://github.com/metasfresh/metasfresh/issues/1062) Return HU to vendor
    * New Functionality to send received Material back to Vendor via HU Editor. Can be automatically filtered directly via correction functionality in Material Receipt POS or manually filtered  in Handling Unit Editor.
  * [#1064](https://github.com/metasfresh/metasfresh/issues/1064) Move HU to garbage
    * Implementation of the Handling Unit Material disposal functionality as part of the RMA workflow.
  * [#1065](https://github.com/metasfresh/metasfresh/issues/1065) Move HU to other Warehouse/ Locator
    * New functionality to easy move Handling Units from the current Locator to a new selected Locator.
  * [#1223](https://github.com/metasfresh/metasfresh/issues/1223) Show line number in jaspers
    * Now showing the line No in all Jasper Documents - order, inout, invoice - for sales and purchase.
  * [#1286](https://github.com/metasfresh/metasfresh/issues/1286) Performance Business Partner Pricelist generation process
    * Improving the performance of the BusinessPartner Pricelist generation process. The time for the report generation is reduced by aprox 60%.
  * [#1322](https://github.com/metasfresh/metasfresh/issues/1322) Forecast Window in WebUI
    * Adding the initial Layout of the Forecast Window to WebUI.

* metasfresh-webui-api
  * [#269](https://github.com/metasfresh/metasfresh-webui-api/issues/269) API for Manufacturing UI Planning/ Doing
    * Implemention of new manufacturing window and Functionalities (issue, receipt) in WebUI. Refactoring of current backend logic to serve the Rest-API.

* metasfresh-webui-frontend
  * [#96](https://github.com/metasfresh/metasfresh-webui-frontend/issues/96) Actions overlay menu cleanup
    * Housekeeping task. Tidying the action menu and removing the actions that are not attached to working procedures.
  * [#355](https://github.com/metasfresh/metasfresh-webui-frontend/issues/355) view websocket notifications: handle "changedIds"
    * Adding internal functionality to react on websocket information about changed Element ID's
  * [#608](https://github.com/metasfresh/metasfresh-webui-frontend/issues/608) Chart animations
    * Adding animation to dashboard charts (pie and bar charts).
  * [#630](https://github.com/metasfresh/metasfresh-webui-frontend/issues/630) Open docLists side by side in QuickAction response.
    * New frontend functionality to open Document Lists side-by-side at the same time.
  * [#643](https://github.com/metasfresh/metasfresh-webui-frontend/issues/643) Reduce size of Action Menu
    * Reducing the side of action menu. Now that the attachments and referenced documents have been moved to sidelist, the action menue does not need the large size anymore.
  * [#651](https://github.com/metasfresh/metasfresh-webui-frontend/issues/651) 404 errors for non-existing pages
    * Implemetation of 404-page for not existing window elements.
  * [#653](https://github.com/metasfresh/metasfresh-webui-frontend/issues/653) Honor openIncludedView's viewType property
    * New feature viewType to tell the frontend to open the viewID in the requested viewType. This allows the opening of viewID's also in other Types than GridView.
  * [#655](https://github.com/metasfresh/metasfresh-webui-frontend/issues/655) Handle process layout type field
    * New modal overlay type for processes. This is firt time needed for scan control prcess view.

## Fixes
* metasfresh-backend
  * [#1191](https://github.com/metasfresh/metasfresh/issues/1191) small adjustments in jasper documents
    * fix empty page in sales invoice document
  * [#1264](https://github.com/metasfresh/metasfresh/issues/1264) Material Receipt document best before date missing
    * Fixes the best-before-date issues in Material Receipt documents.
  * [#1343](https://github.com/metasfresh/metasfresh/issues/1343) Cannot reverse documents
    * Fixes a Bug that did not allow the reversal of Documents.
  * [#1346](https://github.com/metasfresh/metasfresh/issues/1346) Cannot "LU zuteilen" (Swing HU Editor)
    * Fixes a Bug that did not allow to allocate more than one selected Handling Unit after split to an existing Logistic Unit.
  * [#1351](https://github.com/metasfresh/metasfresh/issues/1351) IsTransferWhenNull not working correctly anymore
    * Fixing an issue in attribute transfer. The flag isTransferWhenNull was not considered properly.
  * [#1636](https://github.com/metasfresh/metasfresh/issues/1336) npe in purchase order
    * Fixing a Null Pointer Exception in Purchase Order when using Drop Shipment.

* metasfresh-webui-api
  * [#303](https://github.com/metasfresh/metasfresh-webui-api/issues/303) ASI button not working from process panel
    * This fix allows to to open the Attribute Panel as Process Panel.

* metasfresh-webui-frontend
  * [#645](https://github.com/metasfresh/metasfresh-webui-frontend/issues/645) HOME/END keys not working in process parameter field when opened from view
    * The HOME and END Buttons now also work in process Parameter fields.
  * [#656](https://github.com/metasfresh/metasfresh-webui-frontend/issues/656) Selected grid row and quick actions not consistent after closing a modal
    * Returning to the main view after closing modal overlay process parameters now preserves the previously selected grid row.
  * [#657](https://github.com/metasfresh/metasfresh-webui-frontend/issues/657) Manufacturing Order Issue/Receipt is not displaying the attributes
    * Manufacturing project. Now the grid view lines also display attributes if available for the corresponding line.
  * [#659](https://github.com/metasfresh/metasfresh-webui-frontend/issues/659) Double-clicking on included view row shall not open the document
    * Manufacturing project. The double click on a selected line now does not open the underlying document anymore.
  * [#661](https://github.com/metasfresh/metasfresh-webui-frontend/issues/661) Service not available not detected on login
    * The login menu now shows an overly with "Service not available" if this is the case.

# metasfresh 5.5 (2017-15)

## Features
* metasfresh-backend
  * [#695](https://github.com/metasfresh/metasfresh/issues/695) swingUI provide lib-dirs to access user jars and dlls at runtime
    * Adding a possibility to provide user jars and dlls at runtime for swing client, thanks to @homebeaver
  * [#992](https://github.com/metasfresh/metasfresh/issues/992) Allow subscription without shipment
    * Allowing to use subsriptions for services that are not "shipped" and therfor will not be shown in shipment schedule.
  * [#1057](https://github.com/metasfresh/metasfresh/issues/1057) Role window WebUI
    * Initial Layout configuration for Role maintenance Window.
  * [#1287](https://github.com/metasfresh/metasfresh/issues/1287) Search for name in product window in webUI
    * Now it's possible to search and filter by product name in product window in WebUI.
  * [#1291](https://github.com/metasfresh/metasfresh/issues/1291) Manufacturing Order Window adjustments
    * Adding further adjustments to new manufacturing order in WebUI, allowing an easier/ better user experience.
  * [#1301](https://github.com/metasfresh/metasfresh/issues/1301) DeliveryDays Window for WebUI
    * Adding the window layout for delivery days in WebUI.
  * [#1302](https://github.com/metasfresh/metasfresh/issues/1302) Tour Window in WebUI
    * Adding the tour maintenance window in new WebUI.
  * [#1303](https://github.com/metasfresh/metasfresh/issues/1303) Tour Version Window in WebUI
    * Adding the new window tour version to WebUI, allowing tour maintenance for businesspartner, locations and delivery days.
  * [#1307](https://github.com/metasfresh/metasfresh/issues/1307) Only show Partner Name fields needed for case company
    * Now switching between company name field and person name field depending on isCompany flag in window businesspartner in WebUI.
  * [#1309](https://github.com/metasfresh/metasfresh/issues/1309) Unit of Measure Window in WebUI
    * Adding the initial window setup for unit of measure window in WebUI.
  * [#1324](https://github.com/metasfresh/metasfresh/issues/1324) Take out pwd Field from AD_User in WebUI
    * Not showing the pwd field anymore un user window WebUI.

* metasfresh-webui-api
  * [#294](https://github.com/metasfresh/metasfresh-webui-api/issues/294) Refactor /process/start response
    * Massive technical improvement about processes and documents in metasfresh. This refactoring was needed to meet new requirements in WebUI in a better way.

* metasfresh-webui-frontend
  * [#88](https://github.com/metasfresh/metasfresh-webui-frontend/issues/88) Special Place for Record dependent content
    * Record dependant content and references are now moved from action menu to the extended sidelist menu. Each reference area has its own keyboard shortcut so that navigation to that content is easy and fast.
  * [#110](https://github.com/metasfresh/metasfresh-webui-frontend/issues/110) Don't fetch the side list when it's not needed
    * Performance improvement. Only fetching the sidelist initially when the user opens it first time.
  * [#267](https://github.com/metasfresh/metasfresh-webui-frontend/issues/267) Sidelist refactor
    * Refactoring of sidelist, now including other document references (zoom accross, record attachements)
  * [#628](https://github.com/metasfresh/metasfresh-webui-frontend/issues/628) frontend: refactor /process/start response
    * Frontend work for the refactoring of processes and documents in metasfresh.
  * [#631](https://github.com/metasfresh/metasfresh-webui-frontend/issues/631) Minor grid view layout tweaks
    * Minor UX improvements/ prettyfying to grid view in WebUI.
  * [#636](https://github.com/metasfresh/metasfresh-webui-frontend/issues/636) Sidelist Fontsize too small
    * Raising the font size in sidelist to match the fontsize in other menues.

## Fixes
* metasfresh-backend
  * [#886](https://github.com/metasfresh/metasfresh/issues/886) GrandTotal missing in GridView of Purchase Order
    * Adding column grandtotal to gridview of purchase orders in WebUI.
  * [#1241](https://github.com/metasfresh/metasfresh/issues/1241) metasfresh does not notify procurement-webui about new contracts
    * Fix for procurement WebUI when automatically prolonging new procurement contracts.
  * [#1294](https://github.com/metasfresh/metasfresh/issues/1294) Report with Attribute Set Parameter cannot be started anymore
    * Fixing an exception when calling the attribute editor in process parameters.
  * [#1318](https://github.com/metasfresh/metasfresh/issues/1318) Exception when creating translation entries for new System Language
    * Fixing an exception which popped up when adding a new system language.

* metasfresh-webui-frontend
  * [#619](https://github.com/metasfresh/metasfresh-webui-frontend/issues/619) Address editor silently ignores changes if the mandatory fields are not filled
    * Usability fix for the location editor in WebUI. Improvement of user interaction possibility when mandatory fields are not filled in location editor.
  * [#620](https://github.com/metasfresh/metasfresh-webui-frontend/issues/620) When deleting a newly created document, frontend shall not ask the user if the user wants to leave the page
    * Usability fix. After deleting a document the user is now not asked anymore if the page shall be left altough it does not exist anymore, because deleted.
  * [#637](https://github.com/metasfresh/metasfresh-webui-frontend/issues/637) CTRL+1 and keyboard navigation left-right not working anymore
    * Fix for action menu (ctrl+1) for keyboard arrow navigation.
  * [#639](https://github.com/metasfresh/metasfresh-webui-frontend/issues/639) Navigation shortcut ctrl+2 is broken
    * Fixing the shortcut for navigation menu.

# metasfresh 5.4 (2017-14)

## Features
* metasfresh-backend
  * [#741](https://github.com/metasfresh/metasfresh/issues/741) packing material product category config
    * New functionality to be able to define the package material category per organisational unit, so that jasper reports can do the package material groupings because of that.
  * [#995](https://github.com/metasfresh/metasfresh/issues/995) Translation en_US for Material Receipt Candidates Window
    * Adding the initial set of Translation en_US to the material Receipt Candidates Window in WebUI.
  * [#1181](https://github.com/metasfresh/metasfresh/issues/1181) Refine Layout for Attribute Window in WebUI
    * Adding the initial Layout for the Attribute Window in WebUI.
  * [#1182](https://github.com/metasfresh/metasfresh/issues/1182) Create Layout for User Window in WebUI
    * Adding the initial Layout for the User window into WebUI.
  * [#1185](https://github.com/metasfresh/metasfresh/issues/1185) Initial Layout for vendor Invoices in WebUI
    * Adding the initial Layout for vendor Invoice Window in WebUI.
  * [#1169](https://github.com/metasfresh/metasfresh/issues/1169) Prevent C_Flatrate_Terms with overlapping dates
  * [#1205](https://github.com/metasfresh/metasfresh/issues/1205) Provide Default KPI Config
    * Prepared a default KPI Configuration to show an example Dashboard based on elasticsearch and with KPI done with D3JS on the entry screen of metasfresh after login.
  * [#1206](https://github.com/metasfresh/metasfresh/issues/1206) Remove Gebinderückgabe from Shipment Note for mf15 endcustomer
    * Removing the Text for "empties return" in Shipment Documents for default Jasper Docuiment. The Text does not make sense for companies that don't use the empties management functionality.
  * [#1222](https://github.com/metasfresh/metasfresh/issues/1222) Show orderline description only in the first column of the Jasper
    * Enhanced Reports definition, so that orderline desciptions are only shown in the first column, and not overlaying the others anymore.
  * [#1224](https://github.com/metasfresh/metasfresh/issues/1224) hide HU related data where is not used
  * [#1228](https://github.com/metasfresh/metasfresh/issues/1228) create translate properties for footer report
    * New properties file for the footer subreport for Jasper documents. Now allowing to add translations for that.
  * [#1247](https://github.com/metasfresh/metasfresh/issues/1247) Don't try to create empties movements if empties warehouse same as current warehouse
    * Now not creating movements anymore if the source and target warehouse of empty movements are identical.
  * [#1249](https://github.com/metasfresh/metasfresh/issues/1249) Initial Layout for Material Movement Window WebUI
    * Adding the initial Windows Layout for material movements in WebUI.
  * [#1264](https://github.com/metasfresh/metasfresh/issues/1264) Material Receipt document "old" best before date missing
  * [#1268](https://github.com/metasfresh/metasfresh/issues/1268) Do not print label automatically by default on material receipt
    * Switching off the automatic label printing in material receipt for default configurations.
  * [#1277](https://github.com/metasfresh/metasfresh/issues/1277) Migrate current WebUI Menu from Development to master
    * Migration script for the currents metasfresh webUI menu tree, now showing the new windows.
  * [#1282](https://github.com/metasfresh/metasfresh/issues/1282) Exception splitting aggregate HU with UOM that has no UOMType

* metasfresh-webui-api
  * [#286](https://github.com/metasfresh/metasfresh-webui-api/issues/286) UserSession language endpoint shall always work with JSON values

* metasfresh-webui-frontend  
  * [#541](https://github.com/metasfresh/metasfresh-webui-frontend/issues/541) Hide new and delete included documents when they are not available.
    * New Functionality to show/ not show the new and delete buttons on included tabs/ subtabs if not available.
  * [#587](https://github.com/metasfresh/metasfresh-webui-frontend/issues/587) Language Switcher for WebUI
  * [#604](https://github.com/metasfresh/metasfresh-webui-frontend/issues/604) Mandatory lookup fields are not marked as error
  * [#605](https://github.com/metasfresh/metasfresh-webui-frontend/issues/605) When opening a process panel, the first field shall be focus by default
  * [#614](https://github.com/metasfresh/metasfresh-webui-frontend/issues/614) When the language is switched widgets shall be invalidated/refreshed

## Fixes
* metasfresh-backend
  * [#1191](https://github.com/metasfresh/metasfresh/issues/1191) small adjustments in jasper documents
    * Adding a few detailed adjustments to the default metasfresh documents layout, especially for cases when generating documents with many lines.
  * [#1222](https://github.com/metasfresh/metasfresh/issues/1222) Show orderline description only in the first column of the Jasper
    * Adjustment of the document line description field now defining boundaries for the field content on the generated Jasper Documents making the content better readable.
  * [#1225](https://github.com/metasfresh/metasfresh/issues/1225) Drop legacy jasper sql logic
    * Maintainance of Jasper Document SQL. Removing legace SQL that's not needed anymore.
  * [#1240](https://github.com/metasfresh/metasfresh/issues/1240) Number-of-copies parameter is ignored in direct print
    * Fixes the document printing copies configuration. The number of copies parameter is now also considered in direct print.
  * [#1244](https://github.com/metasfresh/metasfresh/issues/1244) Shipment Schedule's QtyDeliveredTU is not updated correctly
    * Fixed a minor bug that prevented the update of the QtyDelivered TU Field in Shipment schdules.
  * [#1248](https://github.com/metasfresh/metasfresh/issues/1248) Empties movements are not generated from empties shipment/receipt
    * Fixing a bug that did not create movements for empties receive documents after completion.
  * [#1256](https://github.com/metasfresh/metasfresh/issues/1256) Database tables are created in wrong schema
    * Fixes a Bug that created db tables in wrong schema. Now the tables are created in public schema again.
  * [#1260](https://github.com/metasfresh/metasfresh/issues/1260) DocumentNo not generated for manual invoices in WebUI
    * Adding a minor fix to the customer Invoice Window in WebUI, that prevented the creation of manual Invoices for customers.
  * [#1263](https://github.com/metasfresh/metasfresh/issues/1263) ITrxListener.afterCommit is fired twice with TrxPropagation.REQUIRES_NEW
    * Fixes the double tap of ITrxListener.afterCommit.
  * [#1267](https://github.com/metasfresh/metasfresh/issues/1267) Cannot open ASI editor in Swing
    * The Attrubute Set Instance Widget could not be opened in Swing Client anymore. This Bugfix now enables that again.
  * [#1272](https://github.com/metasfresh/metasfresh/issues/1272) Vendor ADR configuration not initially considered in Orderline
    * Fixing a Bug that prevented the default settings of vendor attributes in orderline.
  * [#1274](https://github.com/metasfresh/metasfresh/issues/1274) webui - allow using the session's remote host name or IP as hostkey
    * New feature that allows to use the sessions host name or IP Address as hostkey for printing rounting and configuration.
  * [#1282](https://github.com/metasfresh/metasfresh/issues/1282) Exception splitting aggregate HU with UOM that has no UOMType
    * Fixes a Bug in Splitting HU action. Now also allowing splitting action with compressed Handling Units that don't have a Unit of measure Type.

* metasfresh-webui-api
  * [#277](https://github.com/metasfresh/metasfresh-webui-api/issues/277) Don't export JSONDocument.fields if empty
    * Fixes a Bug that exported empty JSON Document Fields
  * [#283](https://github.com/metasfresh/metasfresh-webui-api/issues/283) Build does not use the specified parent version
    * Fix for maven/ build system to fetch the specified parent version for build.
  * [#284](https://github.com/metasfresh/metasfresh-webui-api/issues/284) HU editor: Cannot receive stand alone TUs by default
    * Now also standalone Transport Units can be received and processed in Material Receipt.
  * [#287](https://github.com/metasfresh/metasfresh-webui-api/issues/287) Update current notifications when user language was changed
  * [#289](https://github.com/metasfresh/metasfresh-webui-api/issues/289) New/Delete buttons missing when a document was initially loaded
    * Now showing the New and Delete Buttons in Subtabs/ included Tabs, when initially loaded.

* metasfresh-webui-frontend
  * [#594](https://github.com/metasfresh/metasfresh-webui-frontend/issues/594) inform users that only Chrome is currently supported on login screen
    * The current development for metasfresh WebUI ist done optimized for Chrome browser. Now informing the user about that on login screen, if trying to login with other browser.
  * [#595](https://github.com/metasfresh/metasfresh-webui-frontend/issues/595) kpi disappears when minimize
    * Fixing a Bug that vanished the KPI widgets after minimizing action.
  * [#597](https://github.com/metasfresh/metasfresh-webui-frontend/issues/597) cancel on "Do you really want to leave?" sends you 2 steps back
    * Fix for the leave confirmation popup. Now only going back 1 step after confiormation.
  * [#599](https://github.com/metasfresh/metasfresh-webui-frontend/issues/599) Action button remains gray if the process execution fails
  * [#609](https://github.com/metasfresh/metasfresh-webui-frontend/issues/609) Included subtab height 100% broken again
    * Fix for the 100% height Layout of windows with included Tab. Subtab Shall always expand to 100% of screen resolution height until available spave is used. After that exceeding page size.
  * [#616](https://github.com/metasfresh/metasfresh-webui-frontend/issues/616) delete option missing after add new
    * The delete Button is now available again after starting the add new action.
  * [#618](https://github.com/metasfresh/metasfresh-webui-frontend/issues/618) Done button is not responding

# metasfresh 5.3 (2017-13)

## Important Changes
* metasfresh-backend
  * [#1199](https://github.com/metasfresh/metasfresh/issues/1199) user credentials of "SuperUser" are renamed to metasfresh
    * Changing the default login credentials to the vanilla system to metasfresh/ metasfresh.

## Features
* metasfresh-backend
  * [#1197](https://github.com/metasfresh/metasfresh/issues/1197) Introduce AdempiereException setParameter/getParameters
    * Introducing getter and setter for Parameters in ADempiereException.
  * [#1201](https://github.com/metasfresh/metasfresh/issues/1201) Add Manufacturing Order Window for WebUI
    * Adding a new window to webUI for Manufacturing Order. This is the first step in the new Manufacuring Project introducing Manufacturing disposition and WebUI Interface. The first Milestone of the Project is planned for early June 2017.
  * [#1202](https://github.com/metasfresh/metasfresh/issues/1202) Clean up ReplicationException
    * Maintenance Task. Cleaning up the addParameter method in Replication Exception which is now available in ADempiereException and therfor deprecated.


* metasfresh-webui-api
  * [#273](https://github.com/metasfresh/metasfresh-webui-api/issues/273) remove deprecated staleTabIds
    * Adding a functionality to not show stale Tabs in WebUI.
  * [#272](https://github.com/metasfresh/metasfresh-webui-api/issues/272) Document Line Delete Behaviour
    * Adjusting the delete behavior of document lines. Now the line also visibly dissappears after delete action.
  * [#276](https://github.com/metasfresh/metasfresh-webui-api/issues/276) Cannot change BPartner address
    * Now allowing the change of BusinessPartner Locations after initial creation.

* metasfresh-webui-frontend
  * [#118](https://github.com/metasfresh/metasfresh-webui-frontend/issues/118) Copy-paste behaviour for document and lists
    * New functionality to Copy-Paste Grid view content in webUI. This was an activly used functionality in Swing User Interface now ported to the new WebUI.
  * [#442](https://github.com/metasfresh/metasfresh-webui-frontend/issues/442) Image Widget from Attachment in User Window
    * New Image widget for WebUI. Will be able to used to include images/ phots for record which are attached to a record.

## Fixes
* metasfresh-backend
  * [#1194](https://github.com/metasfresh/metasfresh/issues/1194) HU "Herkunft" HU_Attribute is not propagated from LU to VHU
    * Fix for the the propagation of attributes top-down. In this case an Attribute added to the Top-Level HU was not propagated down to the included Customer Units.
  * [#1203](https://github.com/metasfresh/metasfresh/issues/1203) Rounding of weights after split in HU Editors is not working correctly
    * Fixing the rounding of weights after Split with the new HU compression Implementation.
  * [#1237](https://github.com/metasfresh/metasfresh/issues/1237) Split fails with aggregate HUs that don't have an integer storage qty
    * Fixes a Bug that appeared when splitting HU generated with the new HU Compression functionality and dis not lead into an integer number storage quantity.

* metasfresh-webui-frontend
  * [#133](https://github.com/metasfresh/metasfresh-webui-frontend/issues/133) Notification display problem
    * Fixes a display bug in notifications created for the user after finishing asynchronous generated documents. Some documents were displayed more than once.
  * [#469](https://github.com/metasfresh/metasfresh-webui-frontend/issues/469) missing value to for range date-time filter
    * Fixes the Bug in Date Range filter widget, where the selection possibility for date-to was missing.
  * [#470](https://github.com/metasfresh/metasfresh-webui-frontend/issues/470) changing AM to PM in Date+Time fields
    * Adjustment of Date-Time Fields, fixing the wrong AP/ PM declaration.
  * [#475](https://github.com/metasfresh/metasfresh-webui-frontend/issues/475) When editing the text field in grid mode i cannot see selected text
    * Color Adjustment of selected Text. Now allowing the user to see what is selected.
  * [#593](https://github.com/metasfresh/metasfresh-webui-frontend/issues/593) DocAction bug when opening a document from references of another
    * Fixes a Bug that appeared when jumping to referenced documents without a valid docaction.

# metasfresh 5.2 (2017-12)

## Features
* metasfresh-webui-frontend
  * [#453](https://github.com/metasfresh/metasfresh-webui-frontend/issues/453) Closing modal or window with unsaved changes
    * Providing a better usability when changing the window or modal overlay with unsaved changes.
  * [#461](https://github.com/metasfresh/metasfresh-webui-frontend/issues/461) If tab layout's "supportQuickInput" is false then don't show the "Batch entry" button
    * The batch entry buttrons for quick input are now not shown when the layout provided by rest-api does not support that.
  * [#462](https://github.com/metasfresh/metasfresh-webui-frontend/issues/462) If document was not found forward to documents view
    * The User Nterface now automatically forwards to documents view in the case that a document is not found.
  * [#484](https://github.com/metasfresh/metasfresh-webui-frontend/issues/484) Login Screen 2nd Window usability
    * Improving the usability of the login screen so that the user can navigate, edit and confirm with keyboard completely without mouse usage.
  * [#491](https://github.com/metasfresh/metasfresh-webui-frontend/issues/491) Line height "jump" when editing mode
    * Improving the behavior of grid view so the lines height dows not change depending of its content.
  * [#532](https://github.com/metasfresh/metasfresh-webui-frontend/issues/532) KPI: Remove the Refresh option
    * Removing the refresh button from KPI because this is not needed anymore since D3JS implementation.
  * [#533](https://github.com/metasfresh/metasfresh-webui-frontend/issues/533) KPI: maximize/restore when double clicking on title bar
    * Improved usability on dashboard. When the user double clicks on the titlebar, then the KPI widget maximizes/ minimizes automatically.
  * [#540](https://github.com/metasfresh/metasfresh-webui-frontend/issues/540) Don't use deprecated staleTabIds
    * Improving the behavior of included tab and staled information.

* metasfresh-webui-api
  * [#252](https://github.com/metasfresh/metasfresh-webui-api/issues/252) Provide to frontend: tab allow create new and delete as they change
    * New functionalities to tab rows deleting/ adding functions. These shall only be shown in user interface if the api provides the  possibilities.
  * [#264](https://github.com/metasfresh/metasfresh-webui-api/issues/264) Support different printers for same user and different login locations
    * Additional api improvements to allow different printers for the same users with different login locations.

* metasfresh-backend
  * [#1145](https://github.com/metasfresh/metasfresh/issues/1145) Refactor adempiereJasper servlets and implement them with @RestController
    * Complete refactoring of adempiereJasper servlets so that they now can work together with the Rest API Controller.
  * [#1146](https://github.com/metasfresh/metasfresh/issues/1146) Change "sent by" in Request Notifications
    * New information in Requests, now keeping the infomration about the notification sender.
  * [#1152](https://github.com/metasfresh/metasfresh/issues/1152) Support address sequence configuration in multi org environment
    * Improvement of the country location sequence configuration in multi organisational environments.
  * [#1178](https://github.com/metasfresh/metasfresh/issues/1178) Warehouse Window in WebUI Layout
    * New Window in Web User Interface to allow the creation and maintenance of Warehouses.

## Fixes

* metasfresh-webui-frontend
  * [#451](https://github.com/metasfresh/metasfresh-webui-frontend/issues/451) Bug in Sales Order Line, Add new
    * Fixed an issue in the "add new" functionality of Sales Order Line.
  * [#474](https://github.com/metasfresh/metasfresh-webui-frontend/issues/474) Editing in the middle of a text field makes the cursor jump to the end
    * This change fixes a user experience issue that let the curson jump to the end when the user tried to edit in the middle of a text.
  * [#502](https://github.com/metasfresh/metasfresh-webui-frontend/issues/502) Lookup field layout issue when it has red border
    * Adjusting the layout of red bordered lookup elements, now aboiding that existing icons overlap the border.
  * [#526](https://github.com/metasfresh/metasfresh-webui-frontend/issues/526) Running a process from menu does not work
    * Fixing a Bug that prevented to run processes called from navigation menu.
  * [#539](https://github.com/metasfresh/metasfresh-webui-frontend/issues/539) Confirm autocomplete Field entry in grid functionality
    * Now it is possible for the user to select and confirm autocomplete entries in the grid view of included tabs.
  * [#545](https://github.com/metasfresh/metasfresh-webui-frontend/issues/545) View's windowId is not matching the expected one
    * This is fixing a Bug which mixed up the viewID's when navigating fast via browser forth and back through the screens.
  * [#547](https://github.com/metasfresh/metasfresh-webui-frontend/issues/547) Menu's first element is hidden behind on mobile
    * Fixing an issue in mobile responsive navigation design. Now also showing the first link on mobile size resolution.
  * [#550](https://github.com/metasfresh/metasfresh-webui-frontend/issues/550) Clicking on grid view breadcrumb item does not work
    * Bugfix for the breadcrumb navigaion on griwd view items.
  * [#558](https://github.com/metasfresh/metasfresh-webui-frontend/issues/558) Respect saveStatus in connected modal
    * Fixing a Bug to repect the saveStatus also in connected modal overlays.
  * [#561](https://github.com/metasfresh/metasfresh-webui-frontend/issues/561) KPI Pie Chart on Start defect

* metasfresh-webui-api
  * [#256](https://github.com/metasfresh/metasfresh-webui-api/issues/256) Cannot create a new BPartner contact
    * Bugfix for the creation of a business partner contact in Web UI.
  * [#259](https://github.com/metasfresh/metasfresh-webui-api/issues/259) New Warehouse is not saveable
    * Bugfix for the creation of a new warehouses in Web UI.
  * [#260](https://github.com/metasfresh/metasfresh-webui-api/issues/260) cannot create receipt with multiple TU on LU
    * Fix for the Material Receipt that did not properly generate receipt lines whan receiving Handling Units with Transprot Units on Load Units.
  * [#263](https://github.com/metasfresh/metasfresh-webui-api/issues/263) Bug in Warehouse window: Auftragsübersicht (intern) NPE
    * Eliminated the reason for the Null Pointer Exception in Warehouse Window for the Sales Order Overview.

* metasfresh-backend
  * [#473](https://github.com/metasfresh/metasfresh/issues/473) Adjust ESR layout for E-Druck
    * Adjusted the Layout of the swiss ESR bill for electronic exchange.
  * [#1165](https://github.com/metasfresh/metasfresh/issues/1165) QtyDelivered not set back correctly after reactivating and voiding a material receipt
    * Now correctly resetting the Qty delivered when reactivating or voiding a material receipt.
  * [#1177](https://github.com/metasfresh/metasfresh/issues/1177) Qties in Material Receipt not correct after several splitting and transforming in HU Editor
    * Handling Unit Transforming is now delivering the correct results after splitting and merging via the new Handling Unit editor and Handling Unit compression.
  * [#1184](https://github.com/metasfresh/metasfresh/issues/1184) Price is found for C_OlCand despite it was deactivated
    * Although the price was deactivated in Product Price it was used in Order Line Candidates. This is now fixed.
  * [#1192](https://github.com/metasfresh/metasfresh/issues/1192) Pricing: IsDefault was not properly migrated
    * Fixing a Bug with the product price migration after price refactoring.

# metasfresh 5.1 (2017-11)

## Features
* metasfresh-backend
  * [#1102](https://github.com/metasfresh/metasfresh/issues/1102) Field Price List Version as search Field
    * Adjusted the Fieldtype for PriceListVersion. Ist now a search field (before a direct Table drop-down). This allows the user now to do a fulltext search and autocomplete and the usage of wildcards.
  * [#1122](https://github.com/metasfresh/metasfresh/issues/1122) Reporting SQL for Products and vendor/ customer
    * New Reporting for Customer and Vendor delivered quantities for a specified Time range.
  * [#1124](https://github.com/metasfresh/metasfresh/issues/1124) metasfresh App Server start takes considerably longer
    * Improvement of Startup time for the metasfresh App server.
  * [#1134](https://github.com/metasfresh/metasfresh/issues/1134) Show Order ID in main window of Empties Receive
    * Add a new Field in Empties receive to show the Document No. of the Sales Order.
  * [#1142](https://github.com/metasfresh/metasfresh/issues/1142) Improve migration scripts handling
    * Adjustment of the Migration Scripts handling. Now saving the migration scripts in a dedicated folder called migration_scripts instead of tmp folder.
  * [#1161](https://github.com/metasfresh/metasfresh/issues/1161) Picking Terminal add Packing Material to Picking Slot takes too long
    * Large Performance Improvement of Picking processing duration when adding Packing Material to a picking Slot. This solution improves the overall performance of the picking workflow.

* metasfresh-webui-api
  * [#244](https://github.com/metasfresh/metasfresh-webui-api/issues/244) KPIs: Introduce TimeRange ending offset
    * Extended functionalitie for KPI definition. Now allowing to set an offet that is used for a ofsetted timerange dataset used for comparison in bar charts.
  * [#246](https://github.com/metasfresh/metasfresh-webui-api/issues/246) Row is not always marked as not saved
    * Extended Functionality to mark rows as saved, allowing the front-end save indicator to react on that.

* metasfresh-webui-frontend
  * [#200](https://github.com/metasfresh/metasfresh-webui-frontend/issues/200) D3JS API definition
    * Integration of D3JS into the webui frontend. D3JS is used for the generation of Barchart and Piechart KPI on the metasfresh Dashboard.
  * [#444](https://github.com/metasfresh/metasfresh-webui-frontend/issues/444) KPI Number Indicator w/ comparator
    * Implementation of the Number Indicator/ comparator widget for the Target KPI in Dashboard.
  * [#459](https://github.com/metasfresh/metasfresh-webui-frontend/issues/459) If quick input fails then don't show the quick input fields
    * Improvement of User Experience. Not showing Quick Input Fields only if these are configured in the application dictionary.

## Fixes
* metasfresh-backend
  * [#1140](https://github.com/metasfresh/metasfresh/issues/1140) too many prices in pricelist report
    * Fixed a Bug that showes to many prices on printed Partner Pricelists.
  * [#1153](https://github.com/metasfresh/metasfresh/issues/1153) Pricing wrong w/ more than 1 Packing Instruction
    * Fixed a bug that did not wlloe to define and use prices for product prices withe more than one packing instruction.
  * [#1160](https://github.com/metasfresh/metasfresh/issues/1160) Material receipt label is printed for each TU on an LU
    * Bug Fix for the automatic Label Printing in Material Receipt Process. Now only the defined amount of copies are printed.
  * [#1162](https://github.com/metasfresh/metasfresh/issues/1162) Split from non-aggregate HUs can lead to wrong TU quantities
    * Bug Fix for the Split action in Handling Unit Editor.
  * [#1171](https://github.com/metasfresh/metasfresh/issues/1171) Inactive BPartner is not shown in open items report
    * Fix for the Open Items report. Now also showing Open Items for decativated Business Partners.
  * [#1172](https://github.com/metasfresh/metasfresh/issues/1172) Show only those weighing machines which are available for HU's warehouse
    * Filtering the shown weighing devices to thiose that are valid for the given Warehouse in HU Storage.

* metasfresh-webui-api
  * [#179](https://github.com/metasfresh/metasfresh-webui-api/issues/179) Cleanup metasfresh-webui repository
    * Housekeeping task to cleanup the metasfresh-webui repository, getting rid of discontinued stuff.
  * [#238](https://github.com/metasfresh/metasfresh-webui/issues/238) Attributes propagated on everything in TU
    * Fixes a Bug that allowed to propoagate Attribute Values on a whole Handling Unit just through moving a low level HU into the Handling Unit.

* metasfresh-webui-frontend
  * [#524](https://github.com/metasfresh/metasfresh-webui-frontend/issues/524) Location Editor cannot read property bug
    * Fixing a Bug that broke the functionality of the Location Editor.

# metasfresh 5.0 (2017-10)

## Features
* metasfresh-backend
  * [#987](https://github.com/metasfresh/metasfresh/issues/987) Create a vertical solution for organisations
    * Adding customizations for associations/ organisations as a vertical solution based on metasfresh.
  * [#1000](https://github.com/metasfresh/metasfresh/issues/1000) Support long address in letter window
    * Now long addresses are supported in documents for a proper display in a letter window.
  * [#1035](https://github.com/metasfresh/metasfresh/issues/1035) DLM - restrict number of mass-archived records
    * Performance and Database autovacuum improvement. Segmenting the mass archiving to allow better database maintenance.
  * [#1046](https://github.com/metasfresh/metasfresh/issues/1046) Automatic Naming of Pricelist Version Name
    * Introducing the automatic naming of pricelist Version, adding the needed Information into the Version identifier.
  * [#1070](https://github.com/metasfresh/metasfresh/issues/1070) Price not transferred to flatrate term
    * Enhancing the processing of flatrate Terms after recording in orderline. Now also transferring the price into the flatrate term data after order completion.
  * [#1071](https://github.com/metasfresh/metasfresh/issues/1071) Empties document Jasper takes very long to generate
    * Improving the performance of empties document generation.
  * [#1075](https://github.com/metasfresh/metasfresh/issues/1075) Create Window for Material Tracking in WebUI
    * New Window "Material Tracking" in WebUI, allowing the creation and maintenance of material Tracking datasets in preparation for usage in procurement, receipt and manufacuring. Material Tracking is important for the retraceability of products in metasfresh.
  * [#1079](https://github.com/metasfresh/metasfresh/issues/1079) Refine the Material Receipt Candidates Grid View
    * Adding detailes Layout to material Receipts Candidates Grid view to allow the receipt user to have a better overview.
  * [#1080](https://github.com/metasfresh/metasfresh/issues/1080) Virtual Column for qtyenteredTU from Purchase Orderline in Receipt Candidate
    * Introduced a virtual column qtyEnteredTU in Material Receipt candidate, to be able to compare ordered TU qty with the already received TU Quantity.
  * [#1081](https://github.com/metasfresh/metasfresh/issues/1081) Allow the generic configuration of C_Printing_Queue_Recipient_IDs for system users
    * New Printing Client Functionality to allow the generic configuration of Printing Queue recepients for system users.
  * [#1090](https://github.com/metasfresh/metasfresh/issues/1090) Introduce C_BPartner_QuickInput table/window to capture new BPartners
    * New Table created for Business Partner creation on the fly during Order editing.
  * [#1105](https://github.com/metasfresh/metasfresh/issues/1105) Translate Action in Material Receipt Schedule for de_DE
    * Translating some of the Quickactions in Material Receipt Candidate window.
  * [#1107](https://github.com/metasfresh/metasfresh/issues/1107) Implement interactive mode for RolloutMigrate
    * Adding a interactive mode for sql_remote.
  * [#1113](https://github.com/metasfresh/metasfresh/issues/1113) BPartner quick input modal overlay Layout for WebUI
    * Created a new Layout based on C_BPartner_QuickInput table and open it in Sales Order editing of Order Partner as "new Partner"  action.
  * [#1118](https://github.com/metasfresh/metasfresh/issues/1118) Change migration scripts folder from TEMP to METASFRESH_HOME/migration_scripts/
    * Customizing and migration enhancement. Now the automatically generated migration scripts for application dictionary changes are saved in dedicated migration-scripts filder instead of TEMP.
  * [#1126](https://github.com/metasfresh/metasfresh/issues/1126) Possibility for easiest regression Test with old and new Pricing Hierarchy
    * Testing SQL to check if the migration of old Pricing Hierarchy to new one was sucessful.
  * [#1130](https://github.com/metasfresh/metasfresh/issues/1130) LU Transform "own Palette" Packing material
    * New functionality to set a "own palette" flag in Handling Unit Editor of Material Receipt, to allow the usage of own Packing Material (here only palettes) that are then not added to Material Receipt Lines from the vendor.
  * [#1132](https://github.com/metasfresh/metasfresh/issues/1132) Close/ Open Action for Material Receipt Candidate Lines
    * Adding the Open/ Close processes to quickaction drop-down in Material Receipt candidates Window allowing the user to mark rows as "processed" or reopen processed rows.
  * [#1133](https://github.com/metasfresh/metasfresh/issues/1133) Reorder the columns in Grid View for Material Receipt Window
    * Reordering columns in Material Receipt Grid View in WebUI.
  * [#1135](https://github.com/metasfresh/metasfresh/issues/1135) Translation de_DE for Process Empties receive
    * Adding a new Translation for "Empties receive" action in Material Receipt Candidates Window in WebUI.


* metasfresh-webui-api
  * [#181](https://github.com/metasfresh/metasfresh-webui/issues/181) Transforming HU in Handling Unit Editor
    * New Functionality in Handling Unit Management. Possibility to apply different actions on Handling Units to transform the Packing, Quantities and Hierarchy easily.
  * [#199](https://github.com/metasfresh/metasfresh-webui/issues/199) KPI master data shall provide to frontend a recommended refresh interval
    * New automatic refresh functionality for Dashboard KPI. The KPI now automatically gets the fresh data automatically and updates the KPI with changed data.
  * [#206](https://github.com/metasfresh/metasfresh-webui/issues/206) CU Handling Unit creation without multiline
    * Now the multiline functionality for creating CU without packing material (TU, LU) is allowed also for only 1 selected line.
  * [#207](https://github.com/metasfresh/metasfresh-webui/issues/207) Prohibit Material Receipt w/ selection of multi BP
    * Disallowing the Material Receipt workflow when lines with different vendors are selected. This was a confusing experience for the user, so we decided to switch off this functioanlity.
  * [#208](https://github.com/metasfresh/metasfresh-webui/issues/208) Receive CU w/ possible quantity adjustment
    * Adjustments to the action "Receive CU", now allowing the change of the initial quantity to be received.
  * [#209](https://github.com/metasfresh/metasfresh-webui/issues/209) HU Automatic Label Printing when received
    * New Functionality that automatically prints the Material Receipt Label for the LU as soon as the HU is switched from planning status to active. This allows a more efficient workflow for the user and avoids the printing of labels before being operative (active).
  * [#210](https://github.com/metasfresh/metasfresh-webui/issues/210) Print Material Receipt Document via Material Receipt Candidates Window
    * Added an action that provides the possibility to print the material Receipt Document for all Handling Units of a given Material Receipt candidate Line.
  * [#223](https://github.com/metasfresh/metasfresh-webui/issues/223) Attributes shall be shown on CU level too
    * Possibility to now edit Attributes also on CU Level.
  * [#228](https://github.com/metasfresh/metasfresh-webui/issues/228) Remaining TU Qty in Quickaction and HU Config
    * When using transform actions in Handling Unit editor, now setting the quantity of the source HU automatically in the Parameter window as initial, but overwritable quantity.
  * [#234](https://github.com/metasfresh/metasfresh-webui/issues/234) HU Config in handling Unit Editor
    * Providing an action to transform Handling Units into other Handling Unit combinations in Handling Unit Editor.
  * [#236](https://github.com/metasfresh/metasfresh-webui/issues/236) Remaining TU Qty in Receive HUs window
    * Automatically calculating the remaining TU Quantity to receive in Material Receipt Canddate Window.

* metasfresh-webui-frontend
  * [#126](https://github.com/metasfresh/metasfresh-webui-frontend/issues/126) Add new Business Partner functionality in search Field
    * New Feature that allows to create a new customer Business Partner on the fly when editing a sales Order. This new functionality automatically is suggested to the user when the businesspartner search does not find a result.
  * [#446](https://github.com/metasfresh/metasfresh-webui-frontend/issues/446) Error indicator for fields
    * Introducing new Error indicators for fields, allowing to provide detailed information to the user in case of error situations for a field. The available information is shown with a red color on the field and available when hovering then field with the mouse.
  * [#447](https://github.com/metasfresh/metasfresh-webui-frontend/issues/447) Not saved indicator for Grid view lines
    * Introducting a "not saved indicator" for grid view lines, now giving the user the transparency to recognize why a line cannot be saved.
  * [#448](https://github.com/metasfresh/metasfresh-webui-frontend/issues/448) Query on activate document included tab
    * New functionality to reduce the traffic and ressource load whan opening a window with included subtabs. This new feature allows to flag subtabs as high volume or frequent change and then reloads the data only when the user selects that subtab.
  * [#452](https://github.com/metasfresh/metasfresh-webui-frontend/issues/452) Saving Indicator Bar in modal window
    * Adding the global save indicator bar also to modal overlays.
  * [#482](https://github.com/metasfresh/metasfresh-webui-frontend/issues/482) Quick actions not queried in HU editor for included rows
    * Added a missing functionality, so that now quick actions can be used also for newly added rows in Handling Unit Editor.
  * [#488](https://github.com/metasfresh/metasfresh-webui-frontend/issues/488) Restrict the minimum size of action menu
    * Layout change to prohibit the action menu getting to narrow, disallowing the user to read the actions.
  * [#490](https://github.com/metasfresh/metasfresh-webui-frontend/issues/490) Filter drop-down list too narrow
    * Layout change to prohibit the filter drop-down list getting to narrow, disallowing the user to read the filter criteria.
  * [#510](https://github.com/metasfresh/metasfresh-webui-frontend/issues/510) Improved Not Saved Indicator design
    * Nice improvement for the design of the "not saved" indicator. It is now a thin red line instead of the "progress and save" indicator.
  * [#1073](https://github.com/metasfresh/metasfresh/issues/1073) Material Receipt Candidates order by Purchase Order ID desc
    * New Order by criteria for the Grid view in the Material Receipt Candidates Window.

## Fixes
* metasfresh-backend
  * [#936](https://github.com/metasfresh/metasfresh/issues/936) Logfile on application-server gets flooded
    * Fixes an issue that leads to high volume logging on metasfresh application server.
  * [#1039](https://github.com/metasfresh/metasfresh/issues/1039) Make C_OrderLine.M_Product_ID mandatory
    * Minor Fix making the Orderline ID mandatory.
  * [#1056](https://github.com/metasfresh/metasfresh/issues/1056) Purchase Order from Sales Order process, wrong InvoiceBPartner
    * Fixes a Bug that did not select the correct Invoice Business Partrner vendor from the Partner Releationship having the delivery vendor Partner different than the Invoice Business Partner vendor.
  * [#1059](https://github.com/metasfresh/metasfresh/issues/1059) ShipmentScheduleBL.updateSchedules fails after C_Order was voided
    * Minor fix in the ShipmentScheduler update that failed for a cornercase when the sales Order was voided.
  * [#1068](https://github.com/metasfresh/metasfresh/issues/1068) Settings on Swing Client are saved but window does not close
    * Minor fix in Swing Client Settings window. Changes to the settings were save but it was not possible to close the window via done after any change.
  * [#1076](https://github.com/metasfresh/metasfresh/issues/1076) NoDataFoundHandlers can cause StackOverflowError
    * Fix for a Bug cause through new Data Lifecycle Management Feature.
  * [#1088](https://github.com/metasfresh/metasfresh/issues/1088) pricing problem wrt proccurement candidates
    * Fix a minor Bug that was introduced with the new pricing changes.
  * [#1094](https://github.com/metasfresh/metasfresh/issues/1094) Drop deprecated and confusing I_AD_User get/setFirstName methods
    * Dropping lagacy getter/ setter for FirstName LastName of User data.
  * [#1097](https://github.com/metasfresh/metasfresh/issues/1097) field too small in jasper document for invoice
    * Enhancing a field size in JasperReports Invoice Document.
  * [#1099](https://github.com/metasfresh/metasfresh/issues/1099) Fix Materialentnahme movement creation
    * Includes a fix for the Material Movement creation for used products in Swing Handling Unit editor.
  * [#1103](https://github.com/metasfresh/metasfresh/issues/1103) TU Ordered Qty in Material Receipt Schedule shows movedQty
    * Fix in the virtual column Ordered Quantitity TU that showed the moved Quantity instead.
  * [#1106](https://github.com/metasfresh/metasfresh/issues/1106) inactive org still selectable on login
    * Fixes the login procedure, now disallowing the loging for inactive Organisations.
  * [#1110](https://github.com/metasfresh/metasfresh/issues/1110) webui HU Editor: conversion error when selecting SubProducer
    * Fixing an error that prohibited the editing of Subproducers in the Material Receipt Handling Unit Editor.
  * [#1121](https://github.com/metasfresh/metasfresh/issues/1121) TU receipt label is just a white sheet of paper
    * Fixes an error in the printing process of Material Receipts and Labels via the standalone printing client and WebUI Interface.

* metasfresh-webui-api
  * [#159](https://github.com/metasfresh/metasfresh-webui/issues/159) Error opening the "Report & Process" window
    * Fixes a Bug that prevented the preview of reports via Chrome new Tab.
  * [#187](https://github.com/metasfresh/metasfresh-webui/issues/187) Documents shall automatically have a default value for standard fields
    * Enhancement to automaticall have default values for document standard fields.
  * [#188](https://github.com/metasfresh/metasfresh-webui/issues/188) Field's mandatory flag is not considered
    * Fixes cases in WebUI that shall trigger a mandatory behavior for input fields.
  * [#213](https://github.com/metasfresh/metasfresh-webui/issues/213) Use document's BPartner language when printing
    * Fixes the output of Documents. Now also in WebUI the documents are created in the Business Partner Language.
  * [#222](https://github.com/metasfresh/metasfresh-webui/issues/222) Carrot Paloxe Error in Material Receipt
    * Fixing an error that appeared when receiving HU which were not LU Level.
  * [#225](https://github.com/metasfresh/metasfresh-webui/issues/225) Receipt Candidates - Foto process is not attaching the picture
    * Fixes the save process when taking a photo via webcam in material Receipt Candidates. The Photo is automatically uploaded and saved as attachment to the candidate record.
  * [#237](https://github.com/metasfresh/metasfresh-webui/issues/237) Transform CU on existing TU not working
    * Fixes an error when trying to transform a CU Handling Unit to an already existing TU.

* metasfresh-webui-frontend
  * [#214](https://github.com/metasfresh/metasfresh-webui-frontend/issues/214) Global shortcuts are not working when focused in inputs
    * Fixing some of the global shortcuts when the focus is on an input field.
  * [#465](https://github.com/metasfresh/metasfresh-webui-frontend/issues/465) DateTime widget not respected in included tab
    * Fix that now also uses the correct widget for DateTime fields.
  * [#473](https://github.com/metasfresh/metasfresh-webui-frontend/issues/473) Sidelist broken
    * Fixing a bug that broke the sidelist.
  * [#485](https://github.com/metasfresh/metasfresh-webui-frontend/issues/485) Subtab 100% height broken again
    * Fixing the Layout of included subtabs when subtab lines are able to fit completely on one screen.
  * [#487](https://github.com/metasfresh/metasfresh-webui-frontend/issues/487) Expand batch entry when completed docuement
    * Fixed an error that appeared when the user tried to use the batch entry mode expansion with a completed document.
  * [#489](https://github.com/metasfresh/metasfresh-webui-frontend/issues/489) Grid view not refreshed on websocket event
    * Fix for websocket eventy that triggers the refresh of the Grid View after external data changes.

# metasfresh 4.58.57 (2017-09)

## Features
* metasfresh-backend
  * [#850](https://github.com/metasfresh/metasfresh/issues/850) Add Migration Script to rename Attribute Set Instance Field
    * Global renaming of Label "Attribute Set Instance" to "Attribute".
  * [#968](https://github.com/metasfresh/metasfresh/issues/968) Include webui in the normal rollout process
    * Optimizing the Continuous Integration workflow to allow the build of WebUI in default Rollout Process.
  * [#1028](https://github.com/metasfresh/metasfresh/issues/1028) extract distributable stuff into dedicated repo
    * Create a dedicated repository and moved distributably stuff there for betterseperation of core and customized implementations.
  * [#1040](https://github.com/metasfresh/metasfresh/issues/1040) Have new request type opportunity
    * Included the new Request Type "Opportunity". First step preparing data structure for our new Opportunity Dashboard in WebUI.
  * [#1049](https://github.com/metasfresh/metasfresh/issues/1049) inDispute Fields and Quality% missing in main Invoice Candidate Window
    * Adjustments in Invoice Candidates Window of WebUI. Included Fields with infomration about the Dispute Status and Quality Inspection information of Material Receipt.

* metasfresh-webui-api
  * [#171](https://github.com/metasfresh/metasfresh-webui/issues/171) No packing item selectable for M_HU_PI_Item_Product
    * Now allowing the recording/ editing of Packing Items in CU:TU Configuration of Product.
  * [#182](https://github.com/metasfresh/metasfresh-webui/issues/182) Material Receipt w/ multiple lines and solitary CU Buckets
    * Implementation of multi line receiving in Material Receipt and Handling Unit Editor.
  * [#183](https://github.com/metasfresh/metasfresh-webui/issues/183) Implement userSession endpoint which also provides the timeZone upcoming
    * When parsing JSON Dates without time, always ignoring the sent timezone.
  * [#184](https://github.com/metasfresh/metasfresh-webui/issues/184) Implement KPI service
    * First prototype Implementation for new metasfresh WebUI Dashboard with usage of D3JS Charts.
  * [#191](https://github.com/metasfresh/metasfresh-webui/issues/191) "Not saved yet" info in REST-API
    * Now providing the information up to Frontend that if data has been saved yet in database. This implementation allows to give the user more feedback about editing errors or missing mandatory data.
  * [#200](https://github.com/metasfresh/metasfresh-webui/issues/200) Generic "add new" search field functionality
    * Implementation of a generic functionality to add new data when not found during autocomplete search workflow. This new functionality will be used in search widget and allows to record data on the fly that belongs to the search field column and ID.

* metasfresh-webui-frontend
  * [#377](https://github.com/metasfresh/metasfresh-webui-frontend/issues/377) grid view: when initially clicking on first row, the second one is first selected
    * Changed the focus behavior in Grid View rows. The cursor "jump" is now eliminated. The user can use the keyboard arrow down to now firstly focus and further navigate trhough the grid.
  * [#392](https://github.com/metasfresh/metasfresh-webui-frontend/issues/392) Filters are not respecting mandatory property
    * Adjustment on filter datils for webUI. Now respecting and showing if parameter/ filter fields are mandatory to be filled.
  * [#416](https://github.com/metasfresh/metasfresh-webui-frontend/issues/416) moving scrollbar on arrow key in dropdown
    * Large dropdown lists now scroll down together with keyboard arrow down navigation.
  * [#428](https://github.com/metasfresh/metasfresh-webui-frontend/issues/428) When calling a process from HU editor open the process parameters on top of the HU editor modal
    * Now allowing modal over modal process windows, to allow the usage of actions also in modal overlays.
  * [#435](https://github.com/metasfresh/metasfresh-webui-frontend/issues/435) filter in Material Receipt Candidates does not work
    * Fixing a minor issue that occured when creating and using a filter criteria that does not have any variable parameters.
  * [#458](https://github.com/metasfresh/metasfresh-webui-frontend/issues/458) HU Editor Attribute editing not possible
    * Fixing a Bug that occured when trying to edit attributes in HU Editor for a selected HU Level.
  * [#1004](https://github.com/metasfresh/metasfresh/issues/1004) Order by C_Order_ID desc in Sales Order Window
    * New Order by criteria in Order Window, so that newest Orders always occur at Tio initially.
  * [#1007](https://github.com/metasfresh/metasfresh/issues/1007) Window for PMM_PurchaseCandidate in WebUI
    * New Window in WebUI for Procurement candidates.
  * [#1032](https://github.com/metasfresh/metasfresh/issues/1032) Material Receipt Candidates Grid View finetuning
    * Adjustments to the Material Receipt Candidates fintuning. Rearranged and reduced the columns shown, so allow a better recognition of important data for the Material Receipt end user.

## Fixes
* metasfresh-backend
  * [#1036](https://github.com/metasfresh/metasfresh/issues/1036) Harmonize BL for ModelCacheService.IsExpired
    * Fixing a Bug in the ModelCacheService that considered records to be expired under certain conditions although they weren't.

* metasfresh-webui-api
  * [#140](https://github.com/metasfresh/metasfresh-webui/issues/140) Failed retrieving included documents when one of them is no longer in repository
    * Fixed a Bug that occured in one time situation and restricted the retrieving of included documents.
  * [#160](https://github.com/metasfresh/metasfresh-webui/issues/160) Don't load documents when dealing with attachments API
    * Fix Material Receipt in WebUI to avoid interfering attachments api that cause error in minor cases when receiving HU.
  * [#176](https://github.com/metasfresh/metasfresh-webui/issues/176) Attributes editor problems
    * Fixes an issue that only showed 3 Attribute lines when opening the Attribute Editor.
  * [#177](https://github.com/metasfresh/metasfresh-webui/issues/177) Date range parameters are not consistent with the ones I introduce
    * Harmonized the date ranges selected in WebUI Frontend with the Parameter Date Ranges used for filtering of data in Backend.
  * [#194](https://github.com/metasfresh/metasfresh-webui/issues/194) Quality discount not considered when receipving HUs
    * Now the Quality discount is considered in Material Receipt WebUI.

* metasfresh-webui-frontend
  * [#404](https://github.com/metasfresh/metasfresh-webui-frontend/issues/404) Wrong viewId used when running "Create material receipt" using keyboard shotcuts
    * Fixes an issue that connected the wrong viewID in "created material receipt" workflow using keyboard navigation and selection.


# metasfresh 4.57.56 (2017-08)

## Features
* metasfresh
  * [#739](https://github.com/metasfresh/metasfresh/issues/739) remove X_BPartner_Stats_MV
    * Removing the legacy DB Table with Business Partner statistics, because of big performance penalty. Will be replaces later via modern data storage and KPI in webUI.
  * [#920](https://github.com/metasfresh/metasfresh/issues/920) Show date promised on order confirmation
    * Now displaying the date promised on order confirmation document.
  * [#927](https://github.com/metasfresh/metasfresh/issues/927) Use partner specific product number and name in documents
    * Extending the Jasperreports documents for order confirmation, inouts and invoices. Now displaying further customer specific product numbers and names.
  * [#928](https://github.com/metasfresh/metasfresh/issues/928) have a way to control which contact is used on addresses in order
    * Enhanced functionality for the selection and usage of contact information on order confirmations. Now its possible to do detailed presettings of Business Partner contacts to be used in Sales and purchase workflows.
  * [#941](https://github.com/metasfresh/metasfresh/issues/941) Make TableSequenceChecker more robust with corner cases
    * Enhancing the Table Sequence Checker to allow more robustness. Issues was recognized in corner cases of Data Life-cycle Management archiving process of legacy data.
  * [#942](https://github.com/metasfresh/metasfresh/issues/942) Improve Transparency & Usability of Pricing definitions
    * Huge Improvement of Transparency & Usability of Pricing definitions. The Pricing Data Structure has grown during the last decade to allow very detailed price definitions bases on countries, currencies, products, packages, attributes and more. This lead into a data structure that is tough to understand and maintain for new users. This implementation improves the price hierarchy to allow much easier maintenance and better transparency.
  * [#954](https://github.com/metasfresh/metasfresh/issues/954) Address Layout Dunning Document
    * Changed the Address Layout on Dunning Document to fit better in standardized letter envelope windows.
  * [#956](https://github.com/metasfresh/metasfresh/issues/956) Adjust Price list reports to new data structure
    * Adjustments to existing Price list reports dues to refactoring of new pricing hierarchy and functionality.
  * [#957](https://github.com/metasfresh/metasfresh/issues/957) Empties Returns for Vendors and Customers in Material Receipt Window
    * Implementation of empties return and receive for Business Partners. The new functionality can be used in Material Receipt candidates window and automatically creates Shipment/ Receive documents for empties with predefined settings.
  * [#969](https://github.com/metasfresh/metasfresh/issues/969) mass migration for 2014, 2015 and further
    * Process to allow to archive during operational time for large chunks of data. This is a spacial migration process to prepare environments for the usage of metasfresh Data Life-cycle Management.
  * [#975](https://github.com/metasfresh/metasfresh/issues/975) Set DLM_Level via properties/preferences UI
    * Allows the user to set if his client shall work with archived data or only show operational data (highly improved performance).
  * [#993](https://github.com/metasfresh/metasfresh/issues/993) New ProductPrice Window for Price Maintenance
    * A new Windows called Product Price which allows the easy filtering and maintenance of all product prices. The user filters the data via preset Price List version and is able to maintain the data vie Grid view or single view/ advanced edit.
  * [#981](https://github.com/metasfresh/metasfresh/issues/981) Introduce AD_PInstance(AD_Pinstance_ID, ColumnName) unique index
    * Minor improvement to raise the reading performance of Process Instances.
  * [#983](https://github.com/metasfresh/metasfresh/issues/983) Upgrade guava version from 18 to 21
    * Upgrading guava (google core libraries) from version 18 to 21.
  * [#986](https://github.com/metasfresh/metasfresh/issues/986) Handle the case that PO can't load a record
    * Allows now fallback scenarios (for example retry) for the Persistance Object to retry in case of thrown exceptions during record loading.
  * [#1018](https://github.com/metasfresh/metasfresh/issues/1018) support description fields in shipment
    * Add the fields description bottom and description to metasfresh client in Shipment Window and displays the field content also on Shipment Jasperreports.

* webUI
  * [#146](https://github.com/metasfresh/metasfresh-webui/issues/146) Receipt schedules: show empties receive/return actions only when only one row is selected
    * Possible actions are now only shown when at least 1 Grid View row is selected. if none are selected then the actions menu is shown, but actions are not able to be started.
  * [#147](https://github.com/metasfresh/metasfresh-webui/issues/147) HU editor: hide filters because they are not supported atm
    * Currently hiding the filter selection in Handling Unit editor until the implementation of filtering is also done in this modal overlay window.
  * [#138](https://github.com/metasfresh/metasfresh-webui/issues/138) change name of new record button
    * The button for new Record is now variable depending on the settings in ad_menu and ad_menu_trl.
  * [#252](https://github.com/metasfresh/metasfresh-webui-frontend/issues/252) Add keyboard handling in POS   
    * Implemented an improved keyboard navigation in the new metasfresh WebUI. Shortcuts and unified behavior is now provided among different windows.
  * [#215](https://github.com/metasfresh/metasfresh-webui-frontend/issues/215) Shortcut for direct Document Action "complete"
    * Further usability Improvement. Added a new shortcut for document complete action. This action is mostly used among all documents and reduces the amount of user keystrokes or mouse click by 1 per document.
  * [#227](https://github.com/metasfresh/metasfresh-webui-frontend/issues/277) Processed HU in Material Receipt Workflow
    * When creating a Material Receipt in handlign Unit editor, then the processed Handling Units are now read-only and are grayed out, so the user can distinguis very well beween proceed and unprocessed Handling Units.
  * [#282](https://github.com/metasfresh/metasfresh-webui-frontend/issues/282) Implement attachments list in Actions menu
    * The action menu now shows another section called "attachments" where all files are listet that are attached to the currently selected record (e.g. product record, document). Photos made per Webcam and uploaded are also automatically shown as Photo attachment.
  * [#323](https://github.com/metasfresh/metasfresh-webui-frontend/issues/323) Provide login credentials in request body
    * Created a new API to provide the login credentials in the request body instead of parameters.
  * [#996](https://github.com/metasfresh/metasfresh/issues/996) Add Warehouse and processed Filter to Material Receipt Candidates Window
    * The Window Material Receipt candidates has now 2 further filter selections (Warehouse, processed). This allows the user to search and filter the needed Data much faster.
  * [#265](https://github.com/metasfresh/metasfresh-webui-frontend/issues/265) Create Jenkinsfile for metasfresh-webui-frontend
    * Including the automatic build of the metasfresh WebUI into the Jenkins Build Infrastructure.
  * [#345](https://github.com/metasfresh/metasfresh-webui-frontend/issues/345) Grid view layout: honor supportNewRecord and newRecordCaption
    * Implementation that brings New Record information up to the frontend and allowss the frontend now to react in a more flexible way.
  * [#162](https://github.com/metasfresh/metasfresh-webui/issues/162) Possibility to choose used filter criteria for webUI
    * Reducing the amount of predefined filter selections per window to saved filter sets created by special user.
  * [#1014](https://github.com/metasfresh/metasfresh/issues/1014) Window Layout Purchase Order: Warehouse
    * This is an Feature List Item that is part of an Feature List. Notice the connector between the three graphics to show that they are related.

## Fixes

* metasfresh
  * [#161](https://github.com/metasfresh/metasfresh-webui/issues/161) Button Action in Subtab for Price List Version creation
    * Enabled the functionality in WebUI to generate new Product prices via the Pricelist Version record and configured calculation Schema with source Price List.
  * [#912](https://github.com/metasfresh/metasfresh/issues/912) New role added .. login not possible after that
    * Fixed a minor Bug that restricted the login after creating a new Role.
  * [#998](https://github.com/metasfresh/metasfresh/issues/998) ClassNotFoundException: de.metas.dlm.swingui.model.interceptor.Main
    * Bugfix for ClassNotFound Exception in Data Life-cycle Management Interceptor.
  * [#1010](https://github.com/metasfresh/metasfresh/issues/1010) M_ProductPrice with Season fix price=Y should not be modified when copied
    * Fix that now prohibits the modification of Product Prices which are flagged as SeasonFixPrice during copy.

* webUI
  * [#124](https://github.com/metasfresh/metasfresh-webui/issues/124) Default/ Standard Filter not correct in WebUI
    * Fix to show the right default Filters in WebUI as defined in Search Columns in Swing Client for a given window.
  * [#135](https://github.com/metasfresh/metasfresh-webui/issues/135) process parameter defaults are not set
    * Fix that ensures the proper setting of report Parameters in WebUI to Jasperreports to allow creation of reports.
  * [#137](https://github.com/metasfresh/metasfresh-webui/issues/137) Make sure ProcessInstance is not override by concurrent REST api call
    * Fixing timing conditions that lead to overriding ProcessInstance through concurrent REST API call.
  * [#141](https://github.com/metasfresh/metasfresh-webui/issues/141) edit address not working anymore
    * Fix to allow the usage of buttons in the advanced edit overlays of WebUI (for example Button for location editor or Attributes Editor)
  * [#145](https://github.com/metasfresh/metasfresh-webui/issues/145) HU editor - Create material receipt not enabled when the whole palet is selected
    * Fix that enables the QuickAction in Handling Unit Editor of Material Receipt when selecting an HU with LU Level (e.g. Pallet)
  * [#150](https://github.com/metasfresh/metasfresh-webui/issues/150) Material Receipt Candidates not updated after receive HU
    * Fixed a Bug that occured when doing mass enqueing of invoice candidate lines of different business partner.
  * [#155](https://github.com/metasfresh/metasfresh-webui/issues/155) Error in invoice candidate enqueuing
    * Fixed a Bug that occured when doing mass enqueing of invoice candidate lines of different business partners.
  * [#157](https://github.com/metasfresh/metasfresh-webui/issues/157) Receive HU opens with 10 LUs
    * Bugfix for Material Recipts via Handling Unit Editor. Always opened with wrong amount of Top Level Handling Units (LU).
  * [#164](https://github.com/metasfresh/metasfresh-webui/issues/164) Create migration script for missing Menu
    * Recreated the missing migration for the newly built WebUI menu with flatter and more comprehensible Hierarchy and Structure.
  * [#168](https://github.com/metasfresh/metasfresh-webui/issues/168) internal: Don't create layout elements if there are no fields inside
    * Fixed a Bug that occured first time in General Ledger window in WebUI, caused through layout elements without included fields.
  * [#170](https://github.com/metasfresh/metasfresh-webui/issues/170) date attributes in hu modal window
    * Bugfix to allow the setting and editing of attributes in date format.
  * [#171](https://github.com/metasfresh/metasfresh-webui/issues/171) No packing item selectable for M_HU_PI_Item_Product
    * Fix to allow the user to select the Packing Item field in WebUI.
  * [#172](https://github.com/metasfresh/metasfresh-webui/issues/172) Entries skipped at the begining of pages
    * Fixed a User Interface bug that caused ugly jumps of the Grid View/ Table View when turning pages.
  * [#263](https://github.com/metasfresh/metasfresh-webui-frontend/issues/263) Grid view attributes were queried when there are no rows
    * Fix in Handling Unit Editor that was trying to read data for an already reversed/ vanished Handling unit.
  * [#294](https://github.com/metasfresh/metasfresh-webui-frontend/issues/294) Leave Location editor with no entry
    * Bugfix. The user is now allowed to leave the location editor in Business Partner Location without changing any data.
  * [#311](https://github.com/metasfresh/metasfresh-webui-frontend/issues/311) Fix the scrollbars when having an overlay grid view
    * Bugfix. The user is now allowed to leave the location editor in Business Partner Location without changing any data.
  * [#313](https://github.com/metasfresh/metasfresh-webui-frontend/issues/313) Scrollbar missing on LU/ TU Level in HU Editor
    * Rearrangement of the Handling Unit Editor layout to not overload the window with too many scrollbars when not really needed.
  * [#314](https://github.com/metasfresh/metasfresh-webui-frontend/issues/314) Grid view filtering: don't send valueTo if it's not a range parameter
    * Avoid the sending of the valueTo as longs it's not a range parameter.
  * [#315](https://github.com/metasfresh/metasfresh-webui-frontend/issues/315) Quick action button layout is broken on smaller resolution
    * Fixed the responsive layout of the Quick Input Button behavior on smaller screen resolutions.
  * [#317](https://github.com/metasfresh/metasfresh-webui-frontend/issues/317) Quick actions are not refreshed when opening the modal HU editor
    * Fix that now refreshes the list of available quick actions when opening the modal Handling Unit Editor overlay.
  * [#322](https://github.com/metasfresh/metasfresh-webui-frontend/issues/322) Quick input's mandatory=false not respected
    * Now evaluating the mandatory false parameter in Quick Batch entry functionality.
  * [#330](https://github.com/metasfresh/metasfresh-webui-frontend/issues/330) HUEditor displays HUs which are destroyed
    * The Handling Unit is now not showing Handling Units anymore which are destroyed (e.g. after reversing the creation of an already active Handling Unit).
  * [#331](https://github.com/metasfresh/metasfresh-webui-frontend/issues/331) Debug/fix: if a PATCH operation fails some wrong calls are performed
    * Bugfix for Patch Operation that lead into wrong calls to REST API.
  * [#333](https://github.com/metasfresh/metasfresh-webui-frontend/issues/333) The whole process parameters content vanished
    * Process Panel fix in Handling Unit Editor that caused the initialization of the whole panel and left it empty.
  * [#353](https://github.com/metasfresh/metasfresh-webui-frontend/issues/353) Wrong sitemap breadcrumb
    * Fix for a minor Issue that showed the wrong breadcrumb path when opening the sitemap.
  * [#354](https://github.com/metasfresh/metasfresh-webui-frontend/issues/354) Filter w/o variable Parameters not working
    * Fixed a Bug that prevented Filter selections without variable Filter criteria included.
  * [#364](https://github.com/metasfresh/metasfresh-webui-frontend/issues/364) Handling Unit Double click Icon changes data underneath overlay
    * Now preventing clicks on modal overlay leading to navigational main window changes underneath.
  * [#259](https://github.com/metasfresh/metasfresh-webui-frontend/issues/259) Grid view selection lost when trying to use the scroll bar
    * Now preventing clicks on modal overlay leading to navigational main window changes underneath.
  * [#352](https://github.com/metasfresh/metasfresh-webui-frontend/issues/352) Lines not "refreshed" after docaction reactivation
    * Bugfix that now refreshes the document lines after reactivation action.
  * [#383](https://github.com/metasfresh/metasfresh-webui-frontend/issues/383) Shortcut for Batch entry space not usable when in input field
    * Fix for keyboard navigation shortcut that now allows the direct jump to Quick Batch entry even when in field focus.
  * [#376](https://github.com/metasfresh/metasfresh-webui-frontend/issues/376) Don't render unknown widget types but log in console
    * Built restriction to not render unnown widget types in metasfresh WebUI.
  * [#350](https://github.com/metasfresh/metasfresh-webui-frontend/issues/350) Strange pulse effect in Subtab Gridview
    * Fix for minor issue in Pulse effect when updating document rows.
  * [#176](https://github.com/metasfresh/metasfresh-webui/issues/176) Attributes editor problems
    * Now the editing of all listed attributes are allowed in Attribute editor when displayed.

# metasfresh 4.56.55 (2017-07)

## Features
* metasfresh
  * #913 include branch name in build version string
    * Added the branch name into the build version string to be able to distinguish between builds & rollouts in development branches.

* webUI
  * #112 On login page, deactivate the fields while logging in
    * Now making the login fields read-only as soon ad the authentication process is triggered.
  * #118 Functionality to easily add files to current record in webUI
    * New Functionality to upload files to a given dataset in the new WebUI. This implementation is also used for the new attachment functionality.
  * #120 Material Receipt WebUI: Attribute Values wrong
    * Instead of short Attribute identifiers now the resolved values are shon in the Attribute Editor.
  * #121 Empties Returns for Vendors and Customers in Material Receipt Window
    * New Functionality in WebUI. Possibility to create empties return/ receive documents quickly from Material Receipts window.
  * #127 Receipt schedules - Receive with configuration improvements
    * Enhanced configuration and saving functionality in material receipt workflow in WebUI.
  * #132 Receiving HUs: already received HUs shall be flagged as processed
    * Now flagging the already processed Handling Units in material Receipts workflow as processed and make visible to the user.
  * #226 Implement document field device support
    * New generic Device functionality to add device buttons to an input field. In the first Implementation used to attach weighing machines to receive the current value for gross weight field. Can be used in Material Receipt window/ Handling Unit editor.
  * #227 Wrong breadcrumb when the view is opened after process execution
    * Adjust for the breadcrumb navigation to show the corresponding path after process execution from window action.
  * #254 Cannot see the HU editor icons
    * Added missing HU editor icons for logistic-, transport- and customer-unit level.
  * #256 Implement document attachments
    * New, fast and easy implematation to upload attachments to an existing record in metasfresh.
  * #257 login page: focus on username
    * When opening the login page to enter metasfresh webUI then initially have the focus on Username field.
  * #275 None of the menus could be opened when in full screen mode
    * Allows to open all top bar menues now, also when quick batch entry mode is activated.
  * #281 Remove margin from wrapper modal to make 0-padding inside
    * Adjusting the look and feel of the modal overlay, now reducing the padding to minimum.
  * #734 Add Translation for en_US in WebUI
    * Translation of metasfresh is now available in en_US.
  * #833 Invoice Process in Invoice Candidates WebUI
    * Add the Invoiceing process to easy create customer and vendor invoices from filtered and selected records in invoice candidates window in webUI.
  * #894 Payment Allocation Window WebUI
    * Include the Payment allocation window in new Web User Interface.
  * #895 Dunning Candidates Window WebUI
    * Include the Dunning Candidates window in new Web User Interface.
  * #947 Window Greeting Add Translation for en_US
    * Minor enhancement, translating the Greeting window for language en_US.
  * #966 Provide WebUI Default Role
    * Add a default role and permission to use the already implemented functionalities for the new Web User Interface.

## Fixes
* metasfresh
  * #797 Zoom does not open new document
    * Fix that allows to open a referenced document record in metasfresh via the reference action and zoom accross.

* webUI
  * #119 Error when Pressing the Attribute Button
    * Fixes an error that occured in sales order advancededit and grid view edit when trying to record product attributes.
  * #116 qty 0 in purchase order
    * Fix for the Quick batch entry bug that leads into wrong quantity 0 in generated order line.
  * #229 Location editor button called "edit attributes"
    * Minor fix renaming the button for Location editing.
  * #261 When the attribute is readonly don't show the Device button(s)
    * Making the new Field device buttons invisible when the corresponding field in read-only.
  * #264 Included tabs layout is broken
    * Fix for the broken layout of included tabs when opened in lower screen resolution.
  * #268 Wrong viewId when starting the process
    * Fix that now provides the correct View-ID as process Parameter.
  * #276 Wrong timing when completing a quick input entry
    * Fix for the Quick Order Batch entry that leads to wrong prices in certain cases.
  * #279 While browsing a document, pressing New does nothing
    * Fixing the new document functionality in action menu while viewing a document in detail view.
  * #295 Attribute Editor too narrow
    * Makes the Attribute Editor now better readable.

# metasfresh 4.55.54 (2017-06)

## Features
* metasfresh
  * #877 Make "Wareneingang POS (Jasper)" report work with M_ReceiptSchedule_ID as parameter
    * Adapt the reports for Material Receipt to work with Receipt Schedule ID as Paramater. This is needed for the new Material Receipt Workflow in WebUI which is now based on generic Material Receipt Schedule Window.
  * #460 Provide aggregate HUs
    * Introduce the Handling Unit compression to only store and process the minimal needed information about Handling Units per step in supply chain. This Implementation is a huge Performance gain in Handling Unit processing.
  * #815 Jasper Footer: Show bank account in one line
    * Adjust the Jasper Reports Footer subdocument. Show all bank information now in same line.
  * #904 New Field "Zulieferant" in R_Request Window
    * Add new Field in Request window to allow the storage of an explicit Vendor Businesspartner.
  * #914 adjust weight in Docs_Purchase_InOut_Customs_Details function
    * Adjust the customs report to fit for swiss requirements in customs reporting. The gross weight is now calculated as Handling Units weight minus weight Logistics Unit Package Item.

* webui
  * #873 Customer & Vendor Subtab in BPartner Window WebUI
    * Add and arrange the customer and vendor subtab in Businesspartner Window in Web User Interface.
  * #196 Grid View 100% height
    * Adjust the Grid view height to expand to screensize.
  * #795 Price Window WebUI Layout
    * Add the Layout for the Price window in WebUI.
  * #896 Dunning Window WebUI
    * Add the Layout for the Dunning Window in WebUI.
  * #194 Open views from process execution result
    * Add a new functionality that allows process results to receive a Window ID and open the corresponding Window after finishing the process execution.


## Fixes
* metasfresh
  * #857 Fix String Attributes Save in POS
    * Fix that now allows the possibility to save String Attribute in POS Windows also without loosing focus for recorded field.
  * #863 No Result Window for Prosesses that don't allow rerun
    * Fix for rerun parameter in Processes. Now possible to switch off the rerun confirmation Window after Process.
  * #879 Fix "ValueType not supported: D" when HU attributes are generated
    * Minor Fix for Value Type of generated HU Attributes.
  * #781 ESR scan processing returns improper bpartner
    * Fix and Enhancement of ESR Scan functionality in Purchase Invoices. Now allowing to select alternative Business Partner for on the Fly Bank Account generation.
  * #783 DocAction on Sales Order not available although permission existing
    * Sysconfig to certrally enable/ disable the Document Action Close.
  * #813 hide packing instruction and qty when null
    * Fix that does not show the Packing Instruction and Packing Qty on documents anymore when null.
  * #903 Jenkins build error with slash in branch name
    * Minor Fix for Jenkins Build.
  * #870 Invoice Candidate price-qty overwrite lost when ReverseCorrect
    * Fix that stores the price & qty override in Invoice Candidates after Reverse-Correct of Invoice.
  * #910 Put explicit delivery date on invoice
    * Add the precise description for delivery date as demanded by german law.

* webui
  * #204 Can not complete Order
    * Minor Fix that now allows the completion of Sales Order in Web User Interface.
  * #886 GrandTotal missing in Purchase Order Grid view
    * Fix that enables the display of Grandtotal Field in Purchase Order Grid View.
  * #179 Fields too short for documentno in Breadcrumbs
    * Fix that extends the number of visible digits (now 9 digits) in Breadcrumb menu for Document or masterdata identifiers.

# metasfresh 4.54.53 (2017-05)

## Features
* metasfresh
  * #858 Adjustments for Shipment Schedule Grid View
    * Minor changes on the Grid View for the Shipment Schedules Window.
  * #868 Weekly Revenue Report
    * Create a weekly Revenue Report in Jasperreports. Similar to the montly report, just comparing different weeks instead months.
  * #827 use the barcode field to select HU using attribute value
    * New feature to be able to scan barcode attrivutes attached to a Handling Unit fir precise identifying.

* webUI
  * #198 Process with parameters cannot be started
    * Fix a Bug in WebUI that did not allow the start of processes with parameters.
  * #205 Batch entry Dropdown for Handling Unit Missing in Workflow
    * Add a Packing Unit dropdown in combined Product Field in Sales Orderline Batchentry.
  * #206 Deleting Batch entry product with "X" only deletes Product not Handling unit
    * Now allowing to delete the whole content of the combined Product-Packing Unit Field.
  * #208 After New autofocus on first record field
    * New UX Feature that automatically sets the focus onto the first recordable Field in Window when "New Record"
  * #213 Do not focus fields "in background" when in Expanded view
    * Adjust the navigation behavior and sequence when using TAB jumping from field to field, now avoids that the focus gets "under" the overlay panel.
  * #218 Reduce Gap Height between Layout Sections
    * Refine UX. Reduced the height between Layout Sections to not have the feeling of having a too big gap between them.
  * #862 Payment Window in WebUI
    * Include the payment Window in WebUI.
  * #873 Customer and Vendor Subtab in BPartner WebUI Window
    * Include the Customer and Vendor Subtab in Business Partner Window.
  * #883 Sales Purchase Order Window Grid View
    * Include the Grid View for the Sales and Purchase Order in WebUI.
  * #878 Purchase Order Window WebUI
    * Include the Purchase Order Window in WebUI.

## Fixes
* metasfresh
  * #782 Focus on the first process parameter
    * Fix to allow the first recordable Field having focus when opening a process paramater window.
  * #864 Adjust C_Country Location Print generation DE
    * Adjust the Location capture Sequence for Germany.

# metasfresh 4.53.52 (2017-04)

## Features
* metasfresh
  * #800 Order by product name and partner name in pmm_PurchaseCandidates
    * Add a new possibility to be able to sort combined search fields by a selected element in the combined Field, e.g. a field combined as Value + Name can now be sorted with Name, and not just Value + Name.
  * #829 receivedVia entry not translated in Baselanguage de_DE
    * Add the Translation for receivedVia Field in current Baselanguage de_DE.
  * #810 Propagate Attribute from Issue to Receive in Production
    * New Functionality to propagate selected Attributes vertically though a manufacturing process, from action issue to action receipt.
  * #835 Switch off Process Confirmation Window
    * Switch off all process confirmation Windows per default. These can be switched on individually per Process where wanted.

* webui
  * #817 Request Window in WebUI
    * Initial setup of Request Window in Web User Interface including default view, advanced edit, grid view and sidelist.
  * #831 Default Document Layout for WebUI
    * Overhaul of the current general Document Layout for WebUI
  * #847 Shipment Schedule Window in WebUI
    * Initial setup of the Shipment Schedule Window in Web User Interface.
  * #853 Shipment Schedule Window Subtabs in WebUI
    * Add the Subtabs definition to Shipment Schedule Window in the new metasfresh Web User Interface, including Sidelist.
  * #855 Shipment Schedule Advanced Edit Mode
    * Setup for the Advanced Edit Mode of Shipment Scheule in WebUI.
  * #108 Create Callout for DocNo in Request
    * Adjustment/ Enhancement of the DocumentNo Generation in non Document datastrucures of WebUI


## Fixes
* metasfresh
  * #785 Make M_InOutLine.IsInvoiceCandidate Iscalculated
    * Adjust the flag isInvoiceCandidate to be calculated for M_InoutLine records.
  * #808 DocActionBL.retrieveString method is broken
    * Fixing the method that retrieves the Document Action Name.
  * #819 fix/refactor CalloutRequest
    * Adopting the Callouts in Request window to also work in Web User Interface.
  * #806 Customs report minor fixes
    * Minor adjustments and fixes in the Customs report.
  * #837 Marginal return accounts doubled
    * Minor Bug Fix in marginal return report that doubled the sums on certain accounts.
  * #844 Gear from Pricing System and PriceList show wrong processes
    * Fixing a Bug that leads into wrong representation of Processes in Gear of Pricing System and Priceliste Window.

* parent
  * #3 Add repo.metasfresh.com also as plugin repo
    * thx to @sramazzina

# metasfresh 4.52.51 (2017-03)

## Features
* metasfresh
  * #774 show address on all docs so it fits the letter window
    * Adjust all Documents so that the address fits into the letter window od envelops C5/ C6 according to ISO 269 und DIN 678.
  * #773 show delivery address on sales order
    * Show the deliverTo location on Sales Order Documents.
  * #507 Copy with Details for PP_Product_BOM
    * Add a new Functionality to allow copy-with-details on Bill of Materials records.
  * #780 Have logo on jasper report that spans from left to right
    * Rearranged the logo placing on Documents to allow the upload and usage of large, page-spanning Logos from left to right.
  * #816 Do not show prices on shipment note
    * Undisplayed the prices on shipment documents.

## Fixes
* metasfresh
  * #615 Purchase Order wrong Price from Contract or Pricesystem for specific Product
    * Addresses the possible case of different procurement products that have different attributes and still both match equally well.
  * #791 Create Nachbelastung from Invoice Cast exception
    * Fixes a Bug that lead into an exception when creating an adjustment charge to an existing invoice.
  * #761 Reactivating an InOut fails sometimes
    * Now the reactivation of InOuts also works with records that were deleted at the time the async-package is processed

# metasfresh 4.51.50 (2017-02)

## Features
* metasfresh
  * #696 add multi line description per order line
    * Possibility to now add multiline descriptions. These can be used to add individual Texts to an Orderline.
  * #755 Automatic upload orders in csv file with COPY into c_olcand
    * Enhancement to allow the Upload of Sales Orders into Order Candidates via COPY.

## Fixes
* metasfresh  
  * #752 request report does not show requests that don't have product or inout
    * Fix to show request lines in report, which don't have a product included nor a reference to an inout line.
  * #759 Destroyed HU causes problem with shipment creation
    * Fix problem when checking for not-yet-delivered M_ShipmentSchedule_QtyPicked records, the system did not check if they reference actually destroyed HUs.
  * #766 fix for "DocumentPA will not be intercepted because final classes are not supported"
    * Fix this error shown on server startup. Making DocumentPA not final anymore.
  * #770 When extending a procurement contract, null becomes 0.00
    * Fix an issue that set the price to 0,00 when extending a procurement contract, although the initial price was null which means "not set".

# metasfresh 4.50.49 (2017-01)

## Features
* metasfresh
  * #615 Purchase Order wrong Price from Contract or Pricesystem for specific Product
    * Working Increment that works for the current requirement at hand.
  * #653 Calculated DailyLotNo in Material Receipt Candidates
    * Add a Daily Lot No. thats calculated as Day from year, from a given Date in Purchase Order, Orderline Attributes.
  * #714 Marginal Return report calculation add additional Costcenter
    * Adjust the marginal return report to show allow more columns with cost center sums on 1 page.
  * #742 R_Request column c_order_id autocomplete too slow
    * Take out the autocomplete of c_order_id to speed up the lookup performance in R_Request.

## Fixes
* metasfresh
  * #757 Automatic contract extension doesn't work anymore
    * Fix the automatic extension of contracts when flatrate term conditions are met.
  * #681 Automatic filling of BPartner and Location only shows value
    * Fix the Search Field reference that showed only the value, instead of Name and value, in case of BPartner and BPartner Location.
  * #718 Wrong location in empties vendor return
    * Close the Gap that allows to record empty returs with BPartner Location that does not belog to the empties BPartner.
  * #744 Report Bestellkontrolle promised Date-Time seems to have am/pm time formatting
    * Adjust the Purchase Order control report to have the correct locale for time formatting.
  * #763 material receipt HU label always shown in preview
    * Migrate the Handling Unit label enabling direct print, without print preview.

* webui
  * #89 Adjust DocAction Names
    * Adjust/ migrate DocAction Names for WebUI.

# metasfresh 4.49.48 (2016-51)

## Features
* metasfresh
  * #489 Implement DLM within single logical tables
    * Data Life-cycle Management Implementation to enable archiving of non-operational data to separate partitions.
  * #682 Translation in window Vendor Returns
    * Add german translation of additional Fields in Window Vendor Returns.

* webui
  * #698 Pipeline - add webui deployment
    * Add a new Pipeline into Continuous Integration/ Deployment for metasfresh WebUI.

## Fixes
* metasfresh
  * #380 duplicate lines in inout
    * Worked over each jasper report in order not to display materdata records that were deactivated.
  * #710 MRP Product Info: Qtyies issued to a production shall be subtracted from onhand qty
    * Fix a Bug that prevented Handling Units Storage to be adjusted when adding raw material to manufacturing order via action issue.
  * #724 Aggregation Shipment Jasper Documents shows reference from other ad_org_id
    * Extend the where clause for matching of PO References in Aggregation inout documents. Additional Aggregation matching criteria now are ad_org_id, c_bpartner_id.
  * #713 Marginal Return Report (short version) doubled sums for accounting group
    * Fix a partially double summed up accounting group in marginal return Report.

# metasfresh 4.48.47 (2016-50)

## Features
* metasfresh
  * #677 make customs report faster
    * Significant improvement of the customs report performance.
  * #541 Remove PiPo from metasfresh removing code and data
    * Remove the legacy code from Pack-In and Pack-out from metasfresh. The underlying concept is flawed and does not scale.

* webui
  * #625 Shipment Schedule Window WebUI
    * Add initial Layout configuration of Shipment Schedule window in metasfresh WebUI.
  * #687 webUI bundle
    * Add different Layout changes in a fair amount of windows for Web User Interface.

## Fixes
* metasfresh
  * #679 Bug in ClientUpdateValidator
    * Fix a Bug in ClientUpdateValidator that avoided starting the client via eclipse for local-build.
  * #721 Wrong error message displayed when user enters wrong password on login
    * Fix for Bug when entering wrong password in Login. Said "locked" but was just wrong credentials/ password.

# metasfresh 4.47.46 (2016-49)

## Features
* metasfresh
  * #639 Marginal Return report calculation does not check ad_org_id
    * Extend the marginal return report with ad_org_id parm to allow to seperate user for other organisations.
  * #585 Adjust the remaining Property names
    * Change properties to metasfresh namespace.
  * #661 Cultivation Planning report adjustments
    * Adjustments made to the cultivation planning report in procurement.
  * #515 Generating C_Flatrate_Term from C_RfQ_Response then don't complete the term
    * Avoid automatic completion of flatrate term contracts when triffered manually from process gear. This allows the user to record further adjustments after creation.

* webui
  * #48 Add initial setup of kibana kpi for new webUI dashboard
    * Setup an initial set of 10 key perfroamnce indicators for the new metasfresh webui.
  * #59 User friendly URL for Print Endpoint
    * Add a user frindly/ comprehensive endpoint for document printing tab in webUI.
  * #45 Dashboard Target area backend support
    * Add support for Target widgets and target widget area in webUI dashboard
  * #567 WebUI - Material Receipt Schedule
    * Initial set of windows, grid views, sidelist and elements and fields for material receipt schedule window.

## Fixes
* metasfresh
  * #658 make Ini more robust: throw ex if file can't be read
    * Fix error with long loading of ini file in Tomcat.
  * #664 R_Request Performance Issue
    * Swap Table direct references against search in all R_Request table/ subtable fields to reduce current performance issues.
  * #674 Filter operator "between" broken
    * Fix the operator "between" which is used in filtering/ search criteria.

* webui
  * #67 Error when introducing parameters to report
    * Fix parameters support for report usage in webui.
  * #70 Add BPLocation Error
    * Fix Errors that prevented the creation of new Business Partner Location lines in webUI.

# metasfresh 4.46.45 (2016-48)

## Features
* webui
  * #425 Kickstart elasticsearch integration
    * Add the first prototype of elasticsearch integration in WebUI environment of metasfresh ERP. Data for elasticsearch index is created via metasfresh async framework.
  * #598 WebUI Dashboard initial Prototype definition
    * Create a prototype dashboard in new metasfresh WebUI. Current prototype uses kibana for KPI and data visualization.

## Fixes
* metasfresh
  * #583 Reports without ad_org_id show wrong results
    * Add support for multi organisation usage of selected 22 reports.
  * #620 Marginal Return Report doubled sums for accounting group
    * Fix the doubled sums in Marginal return report for specific accounting group.    
  * #656 Bug in Import Format - Copy lines process
    * Fix a minor Bug in Import format.
  * #646 Fix support for groovy scripts
    * Fix groovy Script support and extend fieldsize for script recording.

# metasfresh 4.45.44 (2016-47)

This week's RC

## Features
* metasfresh
  * #515 Generating C_Flatrate_Term from C_RfQ_Response then don't complete the term
    * Adjust the completion process of Flatrate terms created manually. Now the flatrate term in not completed and can be manually adjusted by the user without reactivating.
  * #563 Report Statistics qty per Week
    * New sales qty report that shows the sold product quanities per week and in comparison the last 11 weeks.
  * #579 Handling units without washing cycle shall be allowed in washing Manufacturing Order
    * Adjustment of Handling Unit permissions in manufacturing order, initially filtering out HU with washing cycle set.
  * #597 Empties mask and functionality with autom. set the selected bpartner
    * New functionality to add informations about Businsspartner, Location and Purchase Order Reference. This allows the to raise the efficiency when checking and creating purchase invoices via invoice candidates.
  * #576 Report Reclamation result, quality note and minor changes
    * New reqirements implemented in reclamation report.
  * #539 Add missing FK constraints
    * Add further missin Foreign Key constraints surfacing during Data Lifecycle Management implementation.
* webui
  * #567 WebUI - Material Receipt Schedule
    * Add initial Screen Layout for Material receipt schedule in metasfresh WebUI.
  * #497 WebUI - ShipmentSchedule Window
    * Add initial Screen Layout for Shipment Schedule in metasfresh WebUI.

## Fixes
* metasfresh
  * #589 console error when doing bpartner setup
    * Fix a minor bug with jax-rs/ jms timeout in Business Partner setup workflow, which contantly popped up in console.
  * #553 Report Account Info adjustments. Add parms date range.
    * Add the parms date range back into Account Info report in Jasper.
  * #611 IBAN Error for RBS Bank
    * Add support for RBS Bank in metasfresh IBAN check when creating a new Businesspartner Bankaccount.

# metasfresh 4.44.43 (2016-46)

## Features
* metasfresh
  * #553 Report Account Info adjustments. Add parms date range.
    * Enhancing the Filter parms to allow variable daterange for selection.
  * #557 Report Saldenbilanz & Account Info native Excel Export
    * Now Allowing an Excel Export though Report viewer process.
  * #558 Marginal return calculation - Accountings without c_activity_id
    * Marginal Return now considers specific records without activity to be calculated on account specific one.
  * #568 Change on Report "Lieferschein" for one specific Customer
    * Add properties File for Shipment Report.
  * #555 Businesspartner Location isEDI shall not be ticked by default
    * Don't set the Flag isEDI per default when recording new Businesspartner Loactions.
  * #548 keep M_QualityNote and M_AttributeValue in sync
    * New Functionality to sync the M_QualityNote and M_AttributeValue for R_Request complaints usage.
  * #577 Button Request shows too many results
    * Adjust the Filtering of Request Button in main menue and show Role Based counter.
  * #565 Report Revenue per Week and BPartner also show qty
    * Add a new Quantity value in reports Revenue per week and week Businesspartner.
  * #416 Extended async notification features
    * Prepare the notification features for WebUI exposure in metasfresh nextGen.

## Fixes
* metasfresh
  * #578 Request Window Attachment Image too large in viewer
    * Fixes a Bug that scales window too large after uplaoding a large image.

# metasfresh 4.43.42 (2016-45)

## Features
* metasfresh
  * #504 new filter in saldobilanz report
    * Added a new filter in saldobilanz report to exclude the year end accountings (profit & loss) from report.

* metasfresh-webui
  * #41 Implement Dashboard REST endpoint
    * Added a new REST-API endpoint for WebUI KPI widgets.

## Fixes
* metasfresh
  * #552 division by 0 in costprice report
    * Fixed a division by 0 Bug in costprice report.

* metasfresh-webui
  * #40 Account fields are not working
    * Fix in new WebUI Implementation. An exception occured because of Field Type account.

# metasfresh 4.42.41 (2016-44)

## Features
* metasfresh
 * #500 Migration: Create Requests for all inout lines with quality issues
   * SQL Migration Path for all Material Receipts with Quality Issues. Reclamation requests are created.
 * #514 Reclamations report: group the inouts with ff.
   * Create a new Report to analyze the Performance Issues in Vendor receipts/ customer deliveries. The report shows all details to performance issues (Quanitity-, Quality-, Delivery-, Receipt-Performance).

## Fixes
* metasfresh
 * FRESH-823:#536 Context bug in MLookup
   * Fixed a minor context Bug in MLookup Fields.
 * #540 Table and Columns - IsLazyLoading flag is not displayed
   * Fixed a Bug that occured in Table and Columns Definition, preventing isLazyLoading to be shown.


# metasfresh 4.41.40 (2016-43)

## Features
* metasfresh
 * #505 Possibility to define multiple Washing Testcycles for Carrots
   * Quality Assurance Feature for Long Term Storage vegetables. Prossibility to define Washing cycles and route  the Logistic Units to manufacturing Order.
 * #503 Beautify C_PaySelection_CreateFrom and C_PaymentTerm fields
   * Adding better descriptions for Parameters in Payment selection Process.
 * #412 Get rid of AD_Tab.OrderByClause
   * Adapting the sorting machanism in Tabs to allow Layout engines to receive precise Informations which columns are sorted. Initially needed for new WebUI.
 * #424 Migrate spring-boot from 1.3.3 to 1.4.x
   * Updated spring boot-from to to allow the usage of a recent elasticsearch version.

* metasfresh-webui
 * #27 Support for custom order bys in browseView
   * Added new Support for a custom order by criteria in grid-/ browse view.
 * #29 Adapt Invoice candidates window to webui
   * Adapted the Invoice Candidates window to WebUI.
 * #31 Implement document actions
   * Implemented the Document Action for the Web User Interface.
 * #32 Implement document references
   * Provided the Document References to embed these in navigation contex of each document.
 * #33 Implement document filters from AD_UserQuery
   * New and much easier Filtering criteria for data selections in metasfresh nextgen.
 * #20 Cache lookups
   * Optimize lookups content loading with cache functionality.

## Fixes
* metasfresh
 * #508 Creating User without Business Partner throws Exception
 * #487 Attribute editor dialog stores empty field as ''

# metasfresh 4.40.39 (2016-42)

## Features
 * #443 Add is to be sent as email to doc outbound log
 * #418 Improve sales and purchase tracking reports

## Fixes
 * #407 CCache always creates HashMap cache even if LRU was requested
 * #428 NPE when reversing an invoice including a product with inactive UOM conversion
 * #492 build issue with jaxb2-maven-plugin 1.6 and java-8
 * #483 Gebindeübersicht Report Typo fix
 * #482 Unable to issue certain HUs to a PP_Order
 * #494 R_Request new Request context missing

# metasfresh 4.39.38 (2016-41)

## Features
 * #388 make M_ReceiptSchedule.IsPackagingMaterial a physical column
   - Changing the Field in Material Receipt Schedule fpr Packing Materiel. Swapped from pirtual to physical column.


## Fixes
 * #448 Rounding issue with partical credit memos
   - Fixing a rounding issue which popped up after createing a partial credit memo for referenced invoice document.
 * #270 Purchase Order from Sales Order Process wrong Aggregation
   - Optimized the Purchase Order creation process from Procurement candidates. Purchase Orders are now aggregated properly when identical Vendor and products (and further details).
 * #433 C_Order copy with details: Packing Instructions are not copied
   - Fixed a Bug when using Copy with details in c_order. Packing Instructions are now copied too.

# metasfresh 4.38.37 (2016-40)

## Features
 * #395 Add Description in Jasper Invoice Vendor
   - Added a new row in to allow the display of optional line text in further invoices

## Fixes
 * #451 OCRB not available in JVM but needed for ESR page
 * #431 QtyTU does not update in wareneingang pos
 * #436 Single lookup/list value for mandatory field is not automatically set
 * #454 barcode field is reset after 500ms
 * #455 autocomplete in non-generic fields not working anymore

# metasfresh 4.37.36 (2016-39)

## Features
 * #302 Add different onError policies to TrxItemChunkProcessorExecutor
   - Added further policies for InvoiceCandidate processing.
 * #213 Add onhand qty to MRP Product Info
   - Added a new column in MRP Product Info to now show the Handling Unit Storage On Hand Quantity.
 * #375 Jasper: extend product name on report_details
   - Extension of name Field in Jasper Report (report_details).

## Fixes
 * #409 MRP Product Info might leave back stale entries after fast changes
   - Making sure that statistics in MRP Product Info are updated also after quick complete and reactivate of sales and purchase orders.
 * #387 Purchase Order generation in Procurement Candidates not to be grouped by user
   - Ensured that Purchase Order Candidates are aggregated to 1 Purchase Order per Vendor when triggered.
 * #370 Material Receipt - Somtimes double click needed for weighing machine
   - Fixing a bug that occured on certain Windows clients with connected weighing machines.
 * #420 NPE in CalloutOrder.bPartner
   - Eliminated a Null Pointer Exception in Sales Order Callout CalloutOrder.bPartner.
 * #410 sscc label org fix
   - Fixed a minor issue in SSCC Label to load the correct Orgabnisation and Logo when generated and printed.
 * #422 pricelist report do not show virtual HU
   - Fixed the pricelist report to also show virtual HU.
 * #331 Activating the trace log file doesn't always work
   - Stabilized the new trace log feature. Here switching on/ off visibility.
 * #437 Old window Produktion is opened automatically by menu search
   - Fixed the autocall in menu search.

# metasfresh 4.36.35 (2016-38)

## Features
 * #395 Add Description in Jasper Invoice Vendor
   - Added a new row in Vendor Invoice to allow the display of optional line Text.
 * ME-46 Support Ubuntu 16.04 server with metasfresh server installer
   - Milestone Feature: Provided a new metasfresh installer for Ubuntu 16.04
 * #369 request report
   - Provided an excel report for quality analysis bases on dispute request history and data.
 * #361 Request change for customer service
   - Added possibility for dispute requests
 * #377 Implement executed SQLs tracing
   - Admin Functionality to enable better Performance Tracking of SQL.
 * #338 Get rid of legacy NOT-EQUALS operators from logic expression
   - Getting rid of not-equals operators in logic expressions.
 * #333 All tables shall have a single column primary key
   - Change needed for metasfresh WebUI and Rest API. All tables used in WebUI/ Rest API shall have a primary Key.
 * #21 UI Style default for elements
   - WebUI Fallback Szenario for elements when UI Style is not explicitly set.
 * #20 Cache lookups
   - WebUI Performance tweak. Now allowing caching for lookups.
 * #18 Optimization of root & node requests.
   - Added limitCount to path elements to allow accurate results for Navigational structure in WebUI.
 * #16 Implement virtual document fields support
   - New functionality for WebUI to allow fields with content computed by Business Logic.
 * #14 Layout documentSummaryElement field to be used for rendering breadcrumb info
   - Added DocumentSummary support for breadcrumbs in metasfresh WebUI/ Rest API.
 * #13 elementPath should return path without element
   - Possibility to return path without leaf node.
 * #11 Implement grid view support
   - Awesome, new possibility to open Windows in grid view representation.
 * #10 implement documents filtering support
   - Providing metadata for filtering via RestAPI for example for grid view.
 * #9 provide precision for numeric layout elements
   - WebUI: Detailed precision funcionality for amount and costs/ prices elements.
 * #7 provide "grid-align" for layout elements
   - Generic alignment possibility via application dictionary used for metasfresh WebUI.
 * #24 Breadcrumb Navigation Plural caption
   - Added a plural caption for WebUI Breadycrumb navigation.

## Fixes
 * #411 missing index on C_OrderTax.C_Order_ID
   - Performance change. Adding index for c_ordertax.
 * #367 Invoice candidates invoicing Pricelist not found
   - Fixing a minor issue during invoiceing. Pricelist was not found under certain circumstances.
 * #380 duplicate lines in inout
   - Eliminating an issue when deactivating a product and adding another product with same EDI GLN.
 * #348 Sort tabs shall consider Link column and parent link column if set
   - Fixing issue considering link and parent column when sorting.
 * #330 Process's RefreshAllAfterExecution does not work when the record was moved
   - Eliminiating an issue when refreshing after execution of processes.
 * #327 Got NPE when completing a drafted order
   - Fixing a Null Pointer Exception when trying to complete a drafted order document.
 * #337 ERROR: duplicate key value violates unique constraint "c_bpartner_stats_c_bpartner_id_unique" triggered from some callouts
   - Fixing an exception when trying to select a BPartner without valid ship location in Sales Order.
 * FRESH-257 WI1 - rendering a window with tab, one field per field type incl. editor and fieldgroup
   - Initial WebUI Proof of Concept Task. A lot has already done since this one, even more to be expected.
 * FRESH-369 Change bpartner in order -> pricelist does not update
   - Fixing callout issue not updateing the correct pricelist whan changing a Business Partner in Sales Order.
 * #379 Included tab randomly not working in inout and invoice
   - Fixed a bug that ranomly prevented the correct rendering of included Tabs in Invoice window.
 * #12 Data not shown in SubTab
   - Adjusted the data defined in RestAPI for Subtab content.
 * #311 Payment Selection Exception when not able to find bpartner account
   - Added further Account seelection functionality to prevent Exception when selecting BPartner without Bank account.
 * #378 Bug in validation of field docsubtype
   - Eliminated an issue which apperared in Doctype Definition when selecting a DocSubtype.
 * #262 sales and purchase tracking
   - Minor tweaks and fixes in sales and purchase tracking Report.

# metasfresh 4.35.34 (2016-37)

## Features
 * FRESH-112 metasfresh web
   - Integrated recent backend related changes done for metasfresh REST API Implementation.
 * #359 document Note not displayed on invoice
   - Fixed the issue that c_doctype.documentnote was not shown properly on Jasper invoice documents.
 * #262 sales and purchase tracking
   - Implemenation of a large Sales and purchase Tracking Report inclusing possibility to export to excel.
 * #354 Rearrange unloading fields in Sales Order Window
   - Adjusted the validation- and display-rules in sales order window abould fields for unloading (Partner, Location).

## Fixes
 * #366 Faulty unique constraint on M_PriceList
   - Fixed a wrong contraint in M_Pricelist, that prevented creating Product-BPartner-Price-Combination with BPartner recorded on client level.

# metasfresh 4.34.33 (2016-36)

## Features
* #297 Performance problems related to zoom-to
  - Improved user experience, massively reduced loading times for generic zoom-to links in icon-bar.
* #249 Referenzliste in AttributeValue
  - Getting rid of Reference List (System) in client side Attribute Values.
* #347 change default docaction after complete
  - After eliminating all close docactions in permissions now changing the next docaction in document Workflow.

## Fixes
* #315 ReceiptSchedule.QtyToMove not properly updated on reopen
  - Fixed a Bug that prevented the correct update of ReceiptSchedule.QtyToMove after reactivating the record.
* #319 material tracking - deduplicate numbers in article statistics report
  - Getting rid of duplicated statistic qmounts in material Tracking report.
* #329 Revenue reports BPartner & Week show different amounts when HU Price
  - Fixing an issue in Business Partner Revenue Weeek Report that appeared with Usage of Transportation Units with multiple Customer Units in shipments.
* #340 Validation Rule in C_BPartner_Product for C_BPartner_ID wrong
  - Minor Fix of the Validation Rule in C_BPartner_Product. Now also allowing Businesspartners on Client Level.
* #351 translate order summary
  - The Order summary is now covered by translation.
* #335 Invoicing taking wrong Documenttype for Producer Invoice
  - Issue solved when changing the Parms for Producer Invoiceing in Business Partner after already having created Invoice Candidates.

# metasfresh 4.33.32 (2016-35)

## Features
 * #320 material tracking - provide excel friendly information view
   - New SQL report for material tracking

## Fixes
 * #299 Report "Leergutausgabe" from Window "Lieferantenrücklieferung"
   - Localized the empties Report so that one can now include alternative languages.
 * #315 M_ReceiptSchedule.QtyToMove not properly updated on reopen
   - The QtyToMove is now updated after reactivating/ reopening the Material Receipt Schedule.
 * #225 Allocation - Accounting 0,00 when Posted
   - Fixed further minor issues in reposting manchanism for Payment Allocations.
 * #277 Invoice candidates sums at the bottom are not considering org-assignment
   - Fixed an issue with Org Role Access in Window Invoice Candidates. Now the Status Row considers the Org Access Permission.

# metasfresh 4.32.31 (2016-34)

## Features
 * #276 Report Konten-Info new Parameter
   - Added new Parameters (VAT-Period from-to, Account No. from-to) to Account Information Report.
 * #273 Report "Anbauplanung" addition & adjustment
   - Adjusted  the Report for "Anbauplanung" to print out additional information.
 * #272 Report Karottenendabrechnung / Translate Headlines in Reportlanguage = FRENCH
   - Implemented the localization for the fresh produce invoice document.
 * #295 sql in purchase inout takes too long
   - Performance improvement in the jasper file.
 * #292 Automatically add reference no from purchase order to invoice candidate
   - The purchase order reference No is now automatically included in the corresponding invoice canidates.
 * #293 Create cron job for archiving the async-tables
   - Created a cron Job to automatically archive the async data. This speeds up the overall async Performance for large environments.

## Fixes
 * #251 Invoice Candidates double invoiced
   - Fixed a bug that seldomly appeared after invoiceing and caused that an invoice candidate could be invoiced twice.

# metasfresh 4.31.30a (2016-33a)

## Features
 - #297 Performance problems related to zoom-to
   * improve the documentation, both in that code and in the client

## Fixes
 - #298 ShipmentSchedule updating fails on missing UOM conversion
   * prevent an NPE on missing master data

# metasfresh 4.31.30 (2016-33)

## Features
 - #288 Problem with individual client log settings
   * outputting the individual log settings on user login to ease support

## Fixes
 - #275 In Picking HU Editor. New Flag ignore attributes for Filter
   * fixing some corner cases

# metasfresh 4.30.29 (2016-32)

## Features
 - #279 Set document type Bestellung as default value in purchase order
 - #275 In Picking HU Editor. New Flag ignore attributes for Filter
 - #283 Make Gebindesaldo Report support Multi Org

## Fixes
 - #255 Invoice candidates action bar is not considering org-assignment
 - #274 Purchase Order without BPartner Contact, Billto Contact wrong email
 - #252 Fix the code for ADR Attribute Retrieval
 - #243 C_Invoice_Candidate - Processed not always updated if IsToClear

# metasfresh 4.29.28 (2016-31)

## Features
 - #241 Excel Export for Open Items accounting currency
   * Adjusted the Excel Export of Open Items report ro show the sums in accounting currency.
 - #240 Consitency check page for Saldobilanz
   * Added a new Page in Salsobilanz report to allow quick consistency check of accountings.
 - #225 Allocation - Accounting 0,00 when Posted
   * Added a process which double checks the accountings of the day. If a document ist posted and the accounting results are 0,00 then a the document information is logged and the document is reposted.

## Fixes
 - #176 Bestellkontrolle add Promised Date
   * Added the Date promised onto report Bestellkontrolle.
 - #263 Delivery Conditions Flag sometimes not set in Procurement Candidates
   * Fixed a Bug that did not detect contracted lines in Procurement candidates if the contractline was not defined with given price.
 - #248 Admin Login when deleted properties
   * Adressed a security issue that allowed to gain admin permissions after deleting the client properties.

# metasfresh 4.28.27 (2016-30)

## Features
 - #201 KPI Accounted Documents
   * Created the logic for our new KPI "Accounted Documents" which will be included into our new metasfresh webUI.
 - #212 MRPProductInfo display Conference flag with sys config
   * Added a configuration possibility to be able to switch the conference flitering functionality on-off in MRP Product Info.
 - #226 show accounting currency in open items report
   * Implemented a counter check Open Items Reports (customer, vendor) with Accounting Balance. Open Items in foreign currency now show also the sums in accounting currency.

## Fixes
 - #153 PaymentRule = S in Invoice
   * Checked Code for direct setting of PaymentRule cheque got rid of it.
 - #220 Do not load pricelist and pricelist version  on login
   * Not loading Pricelist and Pricelist Version into context anymore during login.
 - FRESH-402 Procurement bidding
   * Minor jasper fix
 - #232 Separate c_flatrate_terms from the normal procurement and RfQ in procurement Excel
   * Separated the sums of procurement contracts vs. sums of RfQ biddings.

# metasfresh 4.27.26 (2016-29)

## Features
 - #152 Improvements in  counter documents view and window
   * Added different fields. Renamed fields for better understanding. Added different validation rules.
 - #173 Window Dunning Candidates - new Field DocumentNo
   * Included the Invoice Document No. in Dunning Candidates window.
 - #183 Error in material tracking if one partner has two contracts (with different conditions) for the same product
   * Skipping a number of unneccesary things if an invoice candidate's `Processed_Override` value is set to "Y"
 - FRESH-402 Procurement bidding
   * Adding jasper file for the procurement documents
 - #181 Customer specific Lieferschein without Price
   * New Jasper Shipment Document that does not show the prices for each line.

## Fixes
 - #216 Accounting: Invoice grand total Fact_Acct line was not found
   * Fixed a Bug in accounting of Allocation Header that appeared in certain circumstances when writing off paid amount completely.
 - #100 EDI wrong handover location in Picking Terminal
   * Sales Orders created via EDI Import could have the wrong handover Locations in a certain condition. This wrong Location appeared in Picking Terminal. Fixed this.
 - #174 Report Konten-Information empty c_activity_id
   * Added a new Flag in Report Parameters to show all entries which have a empty c_activity_id.
 - #203 Payment writeoff not possible for Incoming Payment
   * Fixed a Bug that prohibited the writeoff in incoming Payments.
 - #175 C_Invoice_Update_PaymentRule
   * Changed Lagacy Code that set the Payment Rule to cheque.
 - #210 product appears twice in invoice print preview
   * Minor Bug in Jasper Document that printed out the wrong quantity and total for an invoiceline when shipment quantity was 0 and the invoiceline was aggregated with more than 1 shipment.
 - FRESH-529 drop qtyreserved from product info
   * Dropped the column qtyreserved from product info window.

# metasfresh 4.26.25a (2016-28a)

## Fixes
 - #204 FRESH-525 db_columns_fk view is not working correctly anymore
 - #194 FRESH-517 Jasper Report Error: java.net.BigDecimal
 - #158 FRESH-495 Make de.metas.fresh.printing.spi.impl.C_Order_MFGWarehouse_Report_NotificationCtxProvider thread safe
 - #202 FRESH-522 Payment-in-out-allocation buggy when partial allocation
    * Fixed the newly introduced Feature about allocating 2 Payments (in-out) in cornercase with partial allocated Payments.

# metasfresh 4.26.25 (2016-28)

## Features
 - #182 FRESH-510 Report "Wareneingangsbeleg" with Information "1." / "2. Waschprobe" ergänzen
    * Small layout-change and additional field for quality inspection. Thanks to our new contributor @Spavetti
 - #185 Fresh-512 Receipt POS - sometimes gets wrong numbers from weighting machines
    * Additional glasspane implementation to avoid uncontrolled button activations during the wighing process in Material receipt. Extended logging of weighing information.
 - FRESH-402 Procurement bidding
    * Major new Feature allowing an efficient Procurement Request for bidding workflow, including the extended Procurement bidding web application and automated creation of procurement candidates for selected winners.
 - #119 FRESH-455 different email per org in inout print preview
    * Possibility to define and use different eMail adresses for InOuts, depending on document Organization.
 - #142 FRESH-479 C_AllocationHdr.C_AllocationHdr_ID: Loader too many records
    * Changed the Fieldreference in Subtab to Search-Field to improve opening Performance.
 - #150 FRESH-492 Fix implementation for BPartner Statistics
    * Refactoring, code improvement
 - #128 FRESH-465 Extend Record_ID Column Implementation
    * Extended the Record_ID column Feature to allow more than 1 generic table-record-button to jump to referenced Dataset.
 - #145 FRESH-482 Don't log migration scripts if the transaction failed

## Fixes
 - #197 FRESH-519 Payment void or reverse correct
    * Fixed a Bug that could occure when trying to void or reverse-correct a Payment Document.
 - #151 FRESH-491 When creating a new organization, don't create org access for System role
    * Now we don't automatically create an Org Access for System Role anymore.

# metasfresh 4.25.24a (2016-27a)

## Features
 - #162 FRESH-499 modernize the server's index.html
    * Adjusted the index.html to upgrade of metasfresh to Java 8 usage.
 - #139 FRESH-475 Check for java8 in the rollout-scripts
    * Adjusted the rollout scripts of metasfresh to Java 8 usage.

## Fixes
 - #123 FRESH-460 Users find window name "window, tab, field" confusing
   * also updated menu items
 - #147 FRESH-484 Error creating manual DD_Order
 - #148 FRESH-485 de.metas.async.api.<WP-Name>.AD_User_InCharge_ID can't be overriden on org level

# metasfresh 4.25.24 (2016-27)

## Features
 - **FRESH-399 Upgrade to java-8**
    * Existing users, please see [this howto](http://docs.metasfresh.org/howto_collection/Wie_aktualisiere_ich_die_Java_Version_auf_meinem_server.html) for instructions on how to update your metasfresh server
 - FRESH-397 Upgrade to JasperStudio and latest jasper version
    * Update of Reports and Documents in metasfresh to use the latest Jasper Studio and Jasper Reports Version. Updating to jasperreports-6.2.1
 - #136 FRESH-472 Sequence on Org for more than 1 Doctype
    * Enhancement of Document Sequence seperation among different Organisations. Implemented so that Organisations may uses same Doctypes but seperated Doc Sequences.
 - #90 FRESH-417 Create view and window to identify missing counter documents
    * Implemented View to allow check if counter Documents are missing.
 - #132 FRESH-468 Excel Export of report Konten-Information not working
 - #123 FRESH-460 Users find window name "window, tab, field" confusing
    * Adjusted Name of Window "Windows, Tabs and Fields"
 - #124 FRESH-461 Role "System Administrator" is disabled
    * Note: Not a "fix" because we deliberately deactivated it before and now find that the normal user is better off with the role being available.
 - #125 FRESH-462 enable all entity types
    * Not a fix, the reasoning is similar to #124

## Fixes
 - #137 FRESH-473 Glitches running metasfresh out of eclipse
    * adding a lauch config to run the client with embedded server
    * removing a not-needed dependency that might not be available
    * ignoring local activemq data
    * thx to @pmaingi for going through them with us

# metasfresh 4.24.23a

## Features
 - #121 FRESH-457 Make recipient of the mail configurable in Process SendPDFMailsForSelection

## Fixes

# metasfresh 4.24.23

## Features
 - FRESH-378 process to close invoice candidates
    * New Feature in Invoice Candidates Window which allows the mass manipulation of records setting these to "processed". Also checking and updating referenced shipment candidates during this workflow.

## Fixes
 - #118 FRESH-454 Dont create InvoiceCandidates for DocSubType Saldokorrektur
    * Changed InOut Handler to not create Invoice candidates when DocSubType is "Saldokorrektur".
 - #104 FRESH-441 Notification bar in Material Receipt (POS) covers OK Button
    * Moved the Notification Bar slightly up, so the OK Button, Cancel Button and Changelog Link is not covered anymore.
 - #107 FRESH-445 Awkward eMail encoding in Swiss language
    * Simple Fix to ensure the right encoding when sending eMails.
 - FRESH-280 Period sorting in all Dropdowns where uses year-month numeric
    * Fix related to Order by of Calender and Periods in all relevant Dropdown Lists.
 - #105 FRESH-442 Annotated model interceptor
    * Annotated model interceptor with timing after delete and ifColumnChanged does not work correctly. Fixed.
 - FRESH-438 Make MRP Product Info Work
    * Removed stale data and added FK-constraints and improved logging to avoid Null Pointer Exception
 - FRESH-306 Customer alloc with Vendor Payment: Wrong Accounting
    * Adjusted the accounting of the Alocoation of Incoming and Outgoing Payments.

## Documentation
 - FRESH-323 metasfresh Developer Documentation
    * Added some clarifications and described how to import the initial DB-dump

# metasfresh 4.23.22a

## Features

## Fixes
 - FRESH-408 Picking Issue

# metasfresh 4.23.22

## Features
 - FRESH-280 Period sorting in all Dropdowns where uses year-month numeric
    * Changed sorting for all Dropdown entries about Periods (Month-Year) to have order-by year-month numeric desc

## Fixes
 - FRESH-412 quick input in orders not working
    * Fixed an issue in Quick order entry in Orders (sales and purchase) window
 - FRESH-409 Creating Partner Relation throws Exception
    * Fixing an exception that appeared when creating and saving a Business Partner relation
 - FRESH-407 M_ShipperTransportation Terminated after complete
    * Fixing an exception that popped up when completing a shipper transportation document
 - FRESH-339 Order Candidates BPartner Change does not effect Delivery Adress
    * Introduced additional callout to adjust the corresponding locations when choosing a different Business Partner in Order Candidates Window
 - FRESH-279 DD Order CU calculation wrong when TU = 1
    * Adjusted the Qty CU Calculation when TUQty is 1
 - FRESH-309 Missing ADR ASIs in purchase order lines since february
    * Restored attribute set instances that might have been missing in the past on some systems
 - FRESH-386 another error when sales order is automatically created as counter doc from a purchase order with packagings
    * Fixed error that appears in sales order counter document because of automatic Handling Unit generation in complete.
 - FRESH-388 Invoice Candidates not updated for some material receipts
    * Introducing a view to assist support troubleshooting

# metasfresh 4.22.21

## Features
 - FRESH-275 Search Field in Role _Access Windows with autocomplete
    * In Window Role, allow the user to search and autocomplete Windows, Processes, Forms and more, instead of using a dropdown list. This Functioanality allows a faster creation of Permission rules.
 - FRESH-349 KPI: Printing Performance
    * Adding a window to show per-shipment performance. This will help to understand if printing performance changes over time.
 - FRESH-350 check if purchase inout label and print preview can run faster
    * Improving Performance of material Receipt Labels in Print Preview and Printing.
 - FRESH-377 make invoice print preview faster
    * Improving Performance of Purchase- and Sales-Invoice Document in Print Preview and Printing.
 - FRESH-383 make orders print preview run faster
    * Improving Performance of Purchase- and Sales-Order-Document in Print Preview and Printing.

## Fixes
 - FRESH-400 Cut off in invoice jaspers
    * Header Label for UOM was cut off in Invoice Document. Fixed.
 - FRESH-344 Move KPI SQL to repository and new DB Schema
    * Fix: KPI SQLs were in the default/public schema
 - FRESH-356 make logo work for any org

## Documentation
 - Creation of HowTo's
    * You can now find a quickly growing Set of HowTo's in our metasfresh documentation Project. Check the details here : <a href="http://metasfresh.github.io/metasfresh-documentation/">http://metasfresh.github.io/metasfresh-documentation/</a>

# metasfresh 4.21.20

## Features
 - FRESH-349 KPI: Printing Performance
    * First step in creating queries for printing performance analysis. In near future these queries will be part of an administrator Dashboard and show average Printing performance for different documenttypes.
 - FRESH-344 Move KPI SQL to repository and new DB Schema
    * Moving all prepared KPI Queries to an own Database Schema called de_metas_fresh_kpi.
 - FRESH-347: Relation type between PMM_Purchase_Candidate and C_Order
    * Create an AD_relationType between PMM_PurchaseCandidate and C_Order.
 - FRESH-352 Colored Bar
    * Extending WindowHeaderNotice to also allow setting the notice's foreground and background color. Extending WindowHeaderNotice to also allow setting the notice's foreground and background color. Requirement to be able to create a different color Bar in metasfresh, so visually seperate Logins from different Organizations.
 - FRESH-342 Shipments not created
    * Made the shipment schedule enqueuer's doings more transparent to the user. Also added a house keeping tasks to reenqueue stale shipment schedules.

## Fixes
 - FRESH-374 duplicates asi in purchase inout print preview
    * Fixed a minor issue in Jasper Layout for meterial receipt document.
 - FRESH-363 Client metasfresh not getting results from server due to cxf bug
    * Workaround: Never log incoming payload with JMS transport until https://issues.apache.org/jira/browse/CXF-6930 is solved.
 - FRESH-358 Producer Invoice: Jasper Document shows Recapitulation for technical Tax
    * Fixed a wrong display of special Tax for Urproduzenten in Switzerland.
 - FRESH-360 EDI files occasianally still have wrong encoding
    * Fixed occasionally apperaring wrong encoding in EDI communication.
 - FRESH-356 make logo work for any org
    * Possibility to show the Logo on Jasper Documents. The Logo is taken from Organisation or Businesspartner joined to Org-ID.
 - FRESH-351 Error when sales order is automatically created as counter doc from a purchase order with packagings
    * Solving an issues which appeared in usage of counter documents, because of not matching packagings in each Organisation.
 - FRESH-348 purchase orders created with wrong IsTaxIncluded value
    * Ensuring that whenever the price list changes in an order, IsTaxInCluded, M_PricingSystem_ID and C_Currency are updated.

## Documentation
 - GROWTH-65 Community and Legal Files
    * Added LICENSE.md, CODE_OF_CONDUCT.md and modified The Contributing Guidelines.

# metasfresh 4.20.19

## Features
 - FRESH-254 Customer-Vendor Returns manual flag
    * Set the "manual" flag's default to Y in vendor and customer return windows allowing a more efficient recording.
 - FRESH-334 Product BPartner Contraint Issue
    * Prevent the user from accidentally creating C_BPartner_Product record whose AD_Org_ID makes no sense.
 - FRESH-326 Set the Correct Org in Fact_Acct_Summary
    * changed the migration script to be more repeatable

## Fixes
 - FRESH-152 Extract statistics fields from C_BPartner and put them to a new table called C_BPartner_Stats
    * Fix to avoid multiple updates of same statistical value.
 - FRESH-343 Unwanted PInstance log shown after olCands were cleared for processing
    * Took out changelog for Orderline Candidates which were cleared for processing.
 - FRESH-314 Foreign BPartner reference included in sales order C_Order.C_BPartner_ID.
    * Some polishing around AD_ChangeLog creation.

# metasfresh 4.19.18

## Features
 - FRESH-335 create an initial contributor's guideline
    * Initial Setup of Contributing Guidelines. Further improving.
 - FRESH-278 Umsatzreport Geschäftspartner copy and modify details
    * Created an new Report for Turnover. Data of Report now depending on Delivered Quantities and value.

## Fixes
 - FRESH-338 Async not running
    * Fixed an additional problem with creating AD_ChangeLogs
 - FRESH-314 Foreign BPartner reference included in sales order C_Order.C_BPartner_ID
    * fixed a problem with creating AD_ChangeLogs
 - FRESH-311 Packvorschriften from different Org shown in Leergut
    * Make sure that the Empties Return Window only shows Packing Material that is defined in Logged in Organisation.
 - FRESH-333 Procurement candidate prices not updated correctly
    * Solved an Issue in Proce Calculation of Procurement Candidates when New Pricelist was created for already existing Procurement candidates.
 - FRESH-307 New Organisation: Financial Data of existing Org
    * Make sure that Financial Reports only show Data from selected Organisation.
 - FRESH-326 Set the Correct Org in Fact_Acct_Summary
    * Bug with AD_Org_ID not properly set in FACT_ACCT. Solved the Issue and Created Migration Script for Old Data.
 - FRESH-331 Double click needed for weighing machine and occasional NPE
    * Improved logging and making the application more robust
 - FRESH-329: periods missing in dropdown because of no translations
    * Fixed an Issue with Calendar Periods. These were not shown because of missing Translations.
 - FRESH-327 Subsequent change of logo not working correctly without cache reset
    * Fixing an issue with Caching of Logo. Cache was not resetted after changing the Logo in running Client Session.
 - FRESH-312 Project cannot be compiled when downloading from github directly
    * Removed references to our internal maven repo, fixed a wrong groupid and provided a public keystore for development purposes.
 - FRESH-302 make inout print preview faster
    * Improved the Performance of Print Preview of In-Out Jasper Documents.

# metasfresh 4.18.17

## Features
 - FRESH-314 Foreign BPartner reference included in sales order C_Order.C_BPartner_ID
    * We could'nt reproduce this issue. We improved AD_ChangeLog to also log server-side changes (which have no session-id) and also store the AD_Pinstance_ID if available. If it happens again we will be will be able to trace it.
 - FRESH-320: Swing UI: License aggrement popup shall have an icon down in task bar
    * Adding an Icon in License agreement popup.
 - FRESH-278 Umsatzreport Geschäftspartner copy and modify
    * Adding a revenue report that is week-based and also based on the delivered quantities. The original revenue report still exits, but is based on invoiced quantities instead.
 - FRESH-305 Reduce Warehouse Dropdown List in Wareneingang (POS)
    * After selecting the source warehouse in Wareneingang POS, check all the Distribution Network Line entries that have this specific warehouse as source and provide the Target Warehouses for selection in Target Warehouse Dropdown List.
 - FRESH-312 Project cannot be compiled when downloading from github directly
    * Adding 3rd-party jars that are not available from the standard maven repos to a github hosted repo. Thanks a lot to @mckayERP and @e-Evolution for pointing us to the issue.
 - FRESH-228 Change jxls-poi version from 1.0.8 to 1.0.9 when it will be released
    * Updated to jxls-poi 1.0.9
 - FRESH-302 make inout print preview faster
    * Improved the InOut Print Preview Performance. Is now nearly 50% faster as before.
 - FRESH-298 Setup Printing Dunning Docs to separate tray for ESR Zahlschein
    * Adjusted the layout of Jasper Dunning Documents to better match pre-printed paper without having to use calibration.
 - FRESH-304 Report Konten-Information Rev+Exp accounts Saldovortrag year end
    * Switching the Report "Account -Information" to automatic Year-End initialization of Revenue and Expense Account balance.

## Fixes
 - FRESH-318 ESR String Processing not working with multiple partner bank accounts
    * C_PaySelectionLine: combining two methods into one, to avoid duplicate effort and FUD with their execution order.
    * making sure that annotated model interceptor methods are ordered by their method name
 - FRESH-251 Inout created from Picking-Parm shall only have picked Qty LU-TU too
    * The creation of InOuts shall consider the Picked Quantities of LU-TU via Picking Terminal, when Inout Creations is done from Inout-Candidate Window with Parm PickedQty = 'Y'.
 - FRESH-300 client not starting when config is not completed
    * Fixed a Bug that appeared in Client/ Org Setup when this initially was cancelled or not completed.
 - FRESH-152 Extract statistics fields from C_BPartner and put them to a new table called C_BPartner_Stats
    * Additional Fix.
 - FRESH-93 Purchase Order 848092, Row Missing in Invoice Candidates
    * Extending/ fixing the views to also find wrong Quantity ordered from Inout Lines referencing Invoice Candidates.
	* adding the ddl to our repository

# metasfresh 4.17.16

## Features
 - Fresh 271 Allow easy and riskless experimental builds
    * DevOps - we can now build the complete metasfresh distributable for an issue branch, without artifact GAV collisions
    * Some changes in the buildsystem that allow us to build and rollout feature branches before they were integrated.
 - FRESH-265 Procurement Candidates: Packvorschrift overwrite
    * Possibility to Overwrite the default Packing Instructions/ Handling Units for a reported Product Quantity. The Repor Informations come from Procurement Application.
 - FRESH-286 jenkins envInject plugin overwrites BUILD_URL value
    * DevOps - introducing a new environment variable ROLLOUT_BUILD_URL to be set by the caller. Fallback to BUILD_URL if the new var is not set.
 - FRESH-203 Procurement: Mail in BPartner language, other eMail Address
    * CRM - Enhancing and extending mail configuration and functionality to select an eMail configuration by document type or base type.

## Fixes
 - FRESH-203 Procurement: Mail in BPartner language, other eMail Address
	* Fixed the formatting and encoding problem in the mails sent by our async processor

# metasfresh 4.16.15

## Features
 - FRESH-259 Completely remove zkwebui from metasfresh
 - FRESH-261 Create simple process to change the hostname of a device configuration
    * adding a simple DB function to do the job for now

## Fixes
 - FRESH-267 aparently Loglevels are changed somewhere in the code
 - FRESH-270 material tracking: total received qty and scrap sometimes missing on invoice
     * fixed a problem where those two invoice detail records were attached to a not-displayed group
 - FRESH-234 report sales inout qtys for products and TUs are not alligned

# metasfresh 4.15.14

## Features
  - FRESH-245 filter columns in procurement for year as well
     * Possibility to hide also the columns filtered by year in Excel Reporting. Here especially done for Procurement contract report.
  - FRESH-152 Extract statistics fields from C_BPartner and put them to a new table called C_BPartner_Stats
     * Moving the Business Partner Status out of C_BPartner Data structure into C_BPartner_Stats. This was we avoid performance and blocking issues when updateing the Business Partner statistics.
  - FRESH-252 New Field datePromissed in Invoice Candidates Window
     * User Requirement for selecting Service Data to be invoiced via Invoice Candidates Window. Now the User has the possibility to also filter rows which are not triggered by an Inout Document.

## Fixes
  - FRESH-234 report sales inout qtys for products and TUs are not alligned
     * Minor Layout change for customer individual Inout Documents (Alignment of  identical Column headers on same Page).
  - FRESH-249 hubalance general report missing TU when no carry
     * Small fix in Handling Unit Balance Report.


# metasfresh 4.14.13a

## Features

  - FRESH-206 metasfresh server informs the procurement webui server about what supplies were synchronized
     * when data is received by metasfresh, it now asynchronously sends back a confirmation to the procurement UI. This way it is possible to monitor the procurement webUI for supply reports that were not yet received by the metasfresh system , e.g. due to internet problems.
  - FRESH-187 Filter date-from and date-to in procurement excel
     * also fixing a problem that the library-version we use only supports a hardcoded 50 columns
  - FRESH-218 Create archives in partner's language
     * adding language info to archive records, so when mailing the PDF, the system can choose a mail template in the correct language

## Fixes
 - FRESH-235: User to Role assignment not working with some postgres versions
    * Fix of sql alias Issue. Recognized in User to Role Assignment.
 - FRESH-191 Procurement Excel: Although received no qties does not show
    * Fixed Issue. The quantities were not shown correctly in Procurement contract Excel Report.
 - FRESH-220 Autocomplete does not work if the underlying table has translated columns
    * Fixing the Issue with autocomplete not working whan the underlying table has translated columns.
 - FRESH-210 Org Name not updated after setting different name in Set Up Wizard
    * Using the SetupWizard, the Org name was not updated correctly.
 - FRESH-213 Process panel's Back button not working
    * Fixing an Issue with the back Button in Process Panel.
 - FRESH-222 QtyDelivered not updated for PMM_Balance
    * The PMM_Balance was not updateing QtyDelivered for contracted PMM_Products correctly. Fixed this Issue.

# metasfresh 4.14.13

## Features
 - FRESH-87 Log the JSON-Packages that are exchanged between the service endpoints
    * enabling the cfx LoggingFeature so we can log the data that comes in or goes out via jax-rs
 - FRESH-196 Procurement WebUI: Initially open with tomorrow
 - FRESH-197 Procurement WebUI: firefox's remember password does not popup
 - FRESH-176 Procurement WebUI: Allow switch enabled users between BPartners
 - FRESH-186 change gebindeubersicht to show all until selected date
 - ME-30 09951 Move code to github (108691256234)
	* switching from our internal mercurial server to actually work with and on github
 - FRESH-183 Price editable in Procurement Candidates
 - FRESH-97 Improve metas fresh server and client logging
 - FRESH-59 09915 procurement conditions jasper process (109344045046)
    * finetuning
 - FRESH-59 09915 procurement conditions jasper process (109344045046)FRESH-59 09915 procurement conditions jasper process (109344045046)
    * adding french translations
 - FRESH-172 Procurement: Double entries in PMM_Product: adding unique constraint after having cleaned up

## Fixes
 - FRESH-241 Get rid of com.verisign together with all those legacy payment processors
 - FRESH-215 Procurement WebUI language not updated when changed in bpartner
 - FRESH-219 Procurement: Contract missing in Procurement webUI:
    * making queue subscriptions durable to make sure data is not lost if a subscriber is not present
 - FRESH-205 Doc Outbound: eMail sent not updated
    * the EMail-sent counter is now correct
 - FRESH-219 - Procurement: Contract missing in Procurement webUI
    * adding JMX operation to send all contracts to the webui
 - FRESH-216 Error creating InvoiceCandidates for PP_Orders without issued quantities
    * the system now deals more graciously with material tracking PP_Orders that have receipts but no issues
 - FRESH-194 Picking: Uncompatible CU-TU Issue
 - FRESH-179 Remove customer specifics from jasper and DB functions
 - FRESH-193 problems after temporary JMS link outage
 - FRESH-184 Saldobilanz report year switching saldo for R+E Accounts
 - FRESH-176: Allow switch enabled users between BPartners
 - FRESH-160 Fix Report "Gebindesaldo all"
 - FRESH-177 MRP Product Info Window with empty columns: removing obsolete DB-functions
 - FRESH-37 09955 excel Report Procurement Statistics (105226320154)

# metasfresh 4.13.12

## Features
 - FRESH-70 Model interceptor to prevent deactivating PMM_Products that are unter contract
    * and making sure that activating a record is still allowed
 - FRESH-170 Laufender Vertrag, show Lieferprodukt also on Tab "Liefersatz"
 - FRESH-168 Procurement: Support logon for vendors with no contract
 - FRESH-86 Procurement Show aggregation of reported Qty in MRP Product Info
    * adding a column for the (offered) vendor supply quantity to the MRP product info window
 - FRESH-134 Make syncing on the webUI side more robust
    * refactored how we import the data, make it fail only bpartner level, product level
 - FRESH-139 webui log: show user's ip address and email as part of the log line
 - FRESH-141 Procurement: Procurement Candidates initial qtyToOrder 0
 - FRESH-128 Provide Basic CRM Features
    * adding a simple table to the business partner window that displays related documents etc in cronological order
 - FRESH-71 - extend metasfresh and provide Product-TRLs to the webUI
    * exposing a JMX operation to push all products and translations to the webUI

## Fixes
 - FRESH-171 fix qtyPlanned in procurements excel
 - FRESH-55 Add ASI-support to procurement contracts
    * fix: not all UOMs were eligible anymore
 - FRESH-167: Procurement: Provide Trend also when qty reported = 0
 - FRESH-164 Procurement: multiple Purchase Candidates after reporting qty for same day
 - FRESH-129 contact email in jaspers only after setting the user created as sales contact
 - FRESH-93 Purchase Order 848092, Row Missing in Invoice Candidates
    * adding an SQL based process that allows us to identify, fix and log occasional stale ICs
 - FRESH-126 Procurement WebUI: Qty 4 digits needed
 - FRESH-108 Fix Summary in C_Flatrate_Term_Create_ProcurementContract
 - FRESH-132 Assumption failure when trying to change the payment method in an invoice
 - FRESH-119 Setup wizard does not update field C BPartner Location.address
 - FRESH-125 Default User Record is on Wrong Org *
 - FRESH-30 09628 Procurement vendor-webUI purchasing and contract management
    * make sure that a PMM_Product can't be deactivated if it is part of a contract

----------------------------------------------------------------

# metasfresh 4.12.11

## Features
 - FRESH-82 Adapt PMM Balance reports to respect our new aggregations
 - FRESH-95 Modify the report C_Flatrate_Term_Create_ProcurementContract
    * now displaying the PMM_Product's Name which can also contain ASI-Information
 - FRESH-55 Add ASI-support to procurement contracts
 - FRESH-71 extend metasfresh and provide Product-TRLs to the webUI
 - FRESH-81 PMM purchase candidate: qty aggregation this/next week shall be on BP, product, ASI but NOT hupiitemproduct
 - FRESH-83 webui: another button called "Info" which displays a plain text which was configured in metasfresh

## Fixes
 - FRESH-78 In webui user is reporting in Qty CU and not Qty TU
 - FRESH-108 Fix Summary in C_Flatrate_Term_Create_ProcurementContract
 - FRESH-115 09933 InvoiceCandidates not created - Deadlock
    * had to revisit the task and fix some more

# metasfresh 4.11.10

## Features
 - FRESH-47	Don't create oracle migration scripts
    * removing another legacy piece of code
 - FRESH-37: 09955 excel Report Procurement Statistics (105226320154)
    * now using a high level excel templating engine (jxls)
 - 09961 MRP Product Info row selection persistency (109095061891)
    * when switching between days and filter settings, the selected row remains the same (if is is still there with the new filter settings)
 - when implementing a process, it is now possible to specifiy which record shall be selected after the process is executed
 - 09915 procurement conditions jasper process (109344045046)
    * now also supports contracts that have quantities, but no prices
 - 09628 procurement management (106716240958)
    * all changes in products, partners, contracts etc are now directly pushed to the mobile webUI
 - 09931 Bootify metasfresh (100627676679)
    * metasfresh now runs with spring-boot (server and client)
	* also replacing the JUL-based CLogger with slf4j

## Fixes
 - 09844 Login Problem: Client level Mandant and Org for System Administrator (102554242797)
 - 09915 procurement conditions jasper process (109344045046)
    * fix related to the contract start and end dates
 - usability fix in VNumber: first key press was lost
 - 09945 Pricelist for french customer - excel export not in customer language (107056752126)
 - 09944 Performance: Inout Reactivate-Complete taking too long (107539809839)
 - 09502 quality inspection (100925494419)
    * the final settlement invoice didn't have the correct witholding amount

## Instructions
 - When updateing an existing instalation, the script minor_remote.sh will stop.
   Please read the instructions to update your instalation to the new JBoss free version.


# metasfresh 4.10.9

## Features
 - 09915 procurement conditions jasper process (109344045046)
    * adding jasper, so that a procurement contract can be printed and singned by the vendor
 - 09924 Remove legacy oracle drivers and code (107845685842)
 - 09925 Create Balance Sheet Jasper (107146104064)

## Fixes
 - 09844 Login Problem: Client level Mandant and Org for System Administrator (102554242797)
 - 09939 Inconsistent ASI in InOutLine (109676742143)
 - 09922 null in tageslot sscc etikett (100681187457)
 - 09923 sepa export npe (106359694025)
	* if a given bank account info has no aasociated bank, we now give an informative error message
 - 09933 InvoiceCandidates not created - Deadlock
    * now we generally try processing a work package on deadlock.
 - Fixing NPE when invoicing failed *and* the user in charge had no C_BPartner

# metasfresh 4.9.8b

## Features
 - 09628 procurement management (106716240958)
    *  working on having metasfresh also talking to the webUI and communicate various changes instead of just responding to requests
 - FRESH-21: 09848 enable metasfresh to provide jax-rs services (101763395402)
    * when the client inititally starts and tries to connect the server, the timout is reduces from 1 minute to 2 seconds, to not stall the client and give quicker feedback if there is a problem with the server

## Fixes
 - 09628 procurement management (106716240958)
    * the first event was created with AD_Client_ID=0 and was therefore not processed

# metasfresh 4.9.8

## Features
 - 09920 Support sending invoice without an order via EDI: send the invoice's POReference if there is no cctop111 record coming from metasfresh
 - 09628 procurement management (106716240958)
	* the procurementUI communicates with metasfresh via jax-rs on a jms transport
	* support for the procurementUI to run an embedded broker and connect to metasfresh's broker over SSL
	* metasfresh contains a list with goods that are avaiable to be supplied by any vendor (without contract)
	* supply notes from the UI are sent to metasfresh and are shown to the metasfresh user as purchase order candidates which can directly be transformed into purchase orders
 - 09910 extend sql rollout tool to first create a new db from template (100074461801):
    * extending the tool so we can start by creating a new database from a templated-DB and then apply the scripts to that new database

## Fixes
 - 09912 AD EntityType ID is not acquired from project ID server (106063434593)

# metasfresh 4.8.7

## Features
 - 09628 procurement management (106716240958)
     * still a work in progress
	 * standalone procurement web application that is optimized for mobile and communicated with metasfresh
	 * procurement management in metasfresh
	    * manintain vendor contracts
		* create purchase orders for the supplies reproced via the web app
  - FRESH-21: 09848 enable metasfresh to provide jax-rs services (101763395402)
    *Moved and extended the AD_JavaClasses framework
    *Removed javax.jnlp (it was only needed because there was some code wrt WebStart,
     but that's not done anymore)
    *Removed javax.ejb (we now use jax.rs for the invokations)
     CConnection now also uses a proxy provided by jax-rs to query the application
     server state
     The next step can be to change jboss for a less old & heavy environment,
     like tomcat or something else
    *Extracted de.metas.event's JMS coded into de.metas.jms, because it's now also
     used by de.metas.jax.rs
    *Added table AD_JAXRS_Enpoint to manage our endpoints
    *moving replication and metas-esb folgers into a new ad_menu folder called
     "communication". Also moving the new AD_JAXRS_Enpoint windo to that folder
    *Changed startup-behavior so that when running in embedded-server-mode, the
     client always starts a local jms broker and also connects to it

## Fixes
 - 09643 Zahlung und Zahlung-Zuordnung Datum unterschiedlich (108395556223)
 - 09894 take email address on inout from contact and not properties (108765877478)
 - 09901 Report for packaging material balance: recap differences
 - 09890 Manual Fixing of voided Bankstatement after ESR Import (108847010077)
    * when a bankstatement is voided, all ESR import lines (if any) are unlinked from the bank statement

# metasfresh 4.7.6

## Features
 - FRESH-28: 09869 Enable subscription contracts (104949638829)
    * re-enabling a on-the-fly creation of subscription contracts with automated regular deliveries (via shipment schedule, of course).
	  We implemented this some years ago, but it was in use only briefly, so in this task, adapt and polish the old code.
 - 09859 Fix open items report - the whole query shall go in the reporting function (103228618621)
    * refactored the reports to be more maintainable and easier to support
 - 09766 VAT codes (107418136945)
    * new accounting report centered on VAT codes

## Fixes
 - 09862 Report Footer missing in inout jasper from Wareneingang POS
 - 09861 Error ESR Import with Partner that has Value bigger than 1000
 - when creating an accounting fact_acct for a C_BankStatementLine_Ref, we now set the fact_acct's C_BPartner_ID correctly
 - 09643 Zahlung und Zahlung-Zuordnung Datum unterschiedlich (108395556223)
    * when allocating an invoice with a payment, we now use the later date of the two documents in that allocation

# metasfresh 4.6.5

## Features
 - 09873 translate all of the metasfresh UI to english (105052594715)
    * translated more than 300 window, tab and field names that don't exist in adempiere and therefore weren't translated in task 09847

## Fixes
 - adding an index to C_InvoiceTax to solve a performance issue

# metasfresh 4.5.4

## Features
 - 09863 add comment to SQL generated by Jasper reports to identify (102778506388)
    * with this feature we can now see which running query belong to which jasper report
 - 09836 Show on HU-Receipt label if a HU was flagged for quality instpection (104096142493)
 - 09833 Show additional infos in print problem notification (105117588718)
    * extending the API to allow addtional context info to be added to a notification
 - FRESH-22: 09847 Import adempiere terminology as english trl into metasfresh (107370493155)
    * imported current en_US language translations from adempiere ERP
 - 09824 use locking API to prevent concurrent doc action on the same document (100066000843)

## Fixes
  - FRESH-22: 09847 Import adempiere terminology as english trl into metasfresh (107370493155):
     * fixed a bug in metasfresh that prevented to select the language by its name
      (just its value would have worked) as soon as "English" is available
 - 09776 EDI - Receiver without ORDERS (100584995833)
    * allow to distinguish between Edi-ORDERS and Excel files and allow both kinds to be processed in an EDI fashion
 - FRESH-20: 09661 Statistik für monatliche Lagermeldung - grouping per Product and ASI (106566269211)
    * fix for some quantities being counted multiple times
 - 09852 ESR-Import allow importing with invoice-partners that have AD Org ID 0 (109927070478):
	* when importing ESR-Data, we now accept C_BPartners with AD_Org_ID=0, because besides being a different number, it's not an inconsistency to have a partner with no org
  - 09823 Tax Code Migration (107275491843)
    * making a fix to the work-package processor that is used by the database function "de.metas.async".executeSqlAsync(p_Sql text)
 - 09812 create report for daily packaging material balance (101400050316)
    * minor layouting fixes
 - 09834 adjustment possibility for empties (105285329048)
    * minor layouting fixes

## Instructions

# metasfresh 4.4.3

## Features
 - 09834 adjustment possibility for empties (105285329048)
    * introducing a new doctype to be used for packaging material (empties) adjustments
 - 09839 Customer Individual InOut (109838130709)
    * another alternative shipment document that among other things also lists product which are generally delivered to the customer, even if they are not part of  this particular shipment.
 - 09822 Inter-Org Product Mapping Process (104151535488)
    * allows a user to add and remove product mappings between different organisations
 - 09661 Report "Statistik für monatliche Lagermeldung" - grouping per product and selected attributes (106566269211)
 - 09837 Report Gebinde Übersicht change (103471986337)
 - 09740 ADR revenue report by product categories (101851459609) +it +feature
    * added french translations

## Fixes
 - 09812 create report for daily packaging material balance (101400050316)
    * minor layouting fixes
 - 09281 create report for packaging material balance (106483495857)
    * grouping/summing fixes

# metasfresh 4.3.2

## Features

 - 09826 Take out foot note in purchase order Jasper
    * it's now configurable via property file
 - 09827 Do not show ADR Keine/Leer on HU material receipt label
 - 09828 Salesgroups report add a filter for domestic/foreign country (101738461475)
 - 09832 Allow the printing client to retry on error (106406507107)
 - 09814 Send valid json to the printing client, also if there is an exception in the ESB (103239718792)
 - 09823 Tax Code Migration (107275491843)
    * implemented the database function "de.metas.async".executeSqlAsync(p_Sql text) as a tool to perform time-consuming SQL-migrations in the background
 - 09812 create report for daily packaging material balance (101400050316)
 - 09776 EDI - Receiver without ORDERS (100584995833)
    * we now can specify a default POReference value for orders that is autumatically set by the system, unless there already was another POReference set by a users of from an importnert order
	* for a DESADV lines with unknown CUperTU, we can now set a default-value such a "1" per C_BPartner.

## Fixes

 - 09829 ESB sends HTTP code 204 to the printing client (108552946334)
 - 09281 create report for packaging material balance (106483495857)
    * the former version also showed for a partner also packgings that were not on a particular partner's contract
 - 09820 Header notification bar not shown in main menu (100919535984)
 - 09831 Exception in the Initial Setup Wizard (108054071490)
 - In process parameter panel, display VImage preview, but bound to a maximum size
 - ClientSetup: company name changes were not persisted

## Instructions

 - to use the latest printing ESB bundle (tasks 09829 and 09814), one needs to install the jackson-jaxrs-json provider in the OSGI container (smx):
 ```
bundle:install mvn:com.fasterxml.jackson.jaxrs/jackson-jaxrs-base/2.6.3
bundle:install mvn:com.fasterxml.jackson.jaxrs/jackson-jaxrs-json-provider/2.6.3
 ```

# metasfresh 4.2.1

 - 09281 create report for packaging material balance (106483495857) +it +feature
 - 09740 ADR revenue report by product categories (101851459609) +it +feature
 - 09819 Eliminate duplicated TaxBL and throw an informative exception if... (103899585460) +it +feature
    * removed a code redundancy and source of FUD
    * made it easier to understand why no C_Tax was found for a particular order line
 - 09799 Confusion around Picking Process (108712103881) +it +fix
    * fixing a problem that the picked quantity was sometimes not updated properly in the shipment schedule
 - 09700 Counter orders with mapped products (100691234288) +it +fix
    * fixing a problem with inconsistencies between the newly copied counter order line's old PLV and its new order's PL
 - 09811 EDI creating adjustment charge fails with NullPointerException (100746048824) +it +fix
 - 09797 Confirm completion of order with enter after popup (104892862993) +it +fix
 - 09802 Docaction after Prepared shall be Completed (100319295346) +it +fix
    * when createing purchase orders from sales orders, the drafted purchase orders' doc action is now "complete"
 - 09794 Metasfresh performance improvements (109453118224) +it +feature
    * speedup for the check whether the client still has a DB connection
	* not always loading each field's context menu items without them being needed
 - 09800 Scrolling in Picking terminal first window looks weird (106972300023) +it +fix
 - 09502 quality inspection (100925494419) +it +fix
    * recompute invoice candidates, also on PP_Order unclose
	* when computing the already-paid amount in the final settlement, don't take into account ordinary invoiced like packaging releated invoices
 - 09809 Report direct costing Year Title wrong (103628559355) +it +fix
 - 09803 Revenue report fix (109269170462) +it +fix
 - 09281 create report for packaging material balance (106483495857) +it +feature
 - 09779 Report C Activity ID swap changes (101962781663) +it +feature
 - 09783 Improve Salesgroups - migration and report (105684868719) +it +feature
 - 09801 Customer Individual Shipment change (104284980744) +it +feature
    * minor change to the alternative shipment jasper
 - 09804 Account-Information: don't show and sum Budget (106521617847) +it +fix
 - 09677 extending the BPartner changes report (107837562286) +it +feature
 - 09502_Support quality based invoicing +it +fix
    * directly refresh/recreate existing invoice candidates when a PP_Order is unclosed; don't wait for it to be closed again
 - 09700 Counter orders with mapped products (100691234288) +it +feature
    * allow counter orders (sames order in one org => purchase order in another) with org-internal products that are mapped against each other
 - 09773 Signature fix in shipment jasper (100363111538) +it +fix
 - 09782 remove note from purchase invoice (109638032503) +it +fix
 - 09788 Show isInfiniteCapacity in Pricelist (106197421720) +it +fix
 - 09785 F4-save does not work anymore in included invoice line tab +it +fix
 - 09780 Sorting of List Reference for PriceListVersion (105389853564) +it +feature
 - 09777 German Translations for Salesgroup (106405148729) +it +fix
    * small changes for Salesgroup Translations
 - 09625 Costing short report incl Budget (105806249331) +it +fix
 - 09710 Report regarding effective prices (107746499502) +uat +fix
    * layout/display fix rergarding the report's page number
 - 09766 VAT codes (107418136945) +uat +feature
    * allow defining different VAT codes for sales and purchase, and matching them with each other
 - 09739 gain and loss during bank transfers in foreign currencies (108136874441) +uat +feature
    * introducing default conversion type that can be specified to be active at a given time
	* currency gain and loss from bank account transfers are computed by comparing default conversion type with the default conversion rate
 - 09110 Make activity mandatory in accounting documents and allow the user to select one on demand (105477200774) +uat +feature
 - 09775 Import GL Journal Number Format Exception (104021981594): +it +feature
    * when importing values into a non-text filed that can't be parsed as number, date etc, then don't fail the whole import
 - minor, unrelated fix: when retrieving dunning levels, order them by +it +fix
    *"DaysAfterDue" to make sure they are dealt with in chronological order.
    *Actually, the order might not matter, but a counterintuitive ordering causes FUD.
 - 09771 Dunning docs mail (102929053917) +it +fix +feature
    * small changes around the dunning jasper

# it-S16_05-20160202
 - fixed the default location of the client properties file from <user.home>/.metas-fresh to <user.home>/.metasfresh +it +fix
 - 09741 Problems with HU labels for split HUs (104680331233) +uat +fix
 - 09765 Process to manually re-open C_PAySelection records that were already prepared (108508031142) +uat +feature
 - 09745 alternative jasper shipment document without ADR but explicit GMAA-values (107947997555) +uat +feature
 - 09726 Deep-copy support for AD Roles (106651676304) +uat +feature
    * we now also copy user-role assignements to the target role
 - 09625 Costing short report incl Budget (105806249331) +uat +feature
 - 09767 DBMoreThenOneRecordsFoundException when retrieving from picking slot queue (105944016827) +uat +fix
 - 09710 Report regarding effective prices (107746499502)
 - 09704 Migration ADempiere to metasfresh (100169279454) +it +feature
    * making hardcoded endcustomer-feature configurable for all metasfresh users
 - 09752 system creates two printing queue items for gernic reports (107420055849) +it +fix
 - 09764 servicemix update (102943200308): the esb bundles now use
