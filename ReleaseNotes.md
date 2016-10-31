
# About this document

This file contains the tasks/issues which we implement in metasfresh, in a chronological fashion (latest first)

Additional notes:
 * The metasfresh source code is hosted at https://github.com/metasfresh/metasfresh
 * The metasfresh website is at http://metasfresh.com/en, http://metasfresh.com/ (german)
 * You can also follow us on twitter: @metasfresh (english), @metasfreshDE (german)


Here come the actual release notes:

# metasfresh 4.42.40 (2016-44)

upcoming

## Features
* metasfresh
 * #500 Migration: Create Requests for all inout lines with quality issues
 * #514 Reclamations report: group the inouts with ff.

## Fixes
* metasfresh
 * #540 Table and Columns - IsLazyLoading flag is not displayed


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
    * Fixed an Issue with Calendar Periods. These were  not shown because of missing Translations.
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
 - 09764 servicemix update (102943200308): the esb bundles now use servmicemix-6.1 +uat +feature
    *also switching everything from activemq-5.7 to activemq-5.12.1
    *ActiveMQJMSEndpoint now needs to provide username and password to the jms broker
    *commenting out the sniffer plugin because we can now build against java 1.7
    *moving org.adempiere.event to de.metas.event
    *moving EqualsBuilder and HashcodeBuilder from base to util
	* removing javax.mail from printing.esb, instead using guava to encode base64
    * removing javax.mail from de.metas.util
    * further build fixes and clean-ups
 - 09746_Change of activity report adaptions +fix +uat
    * fixing a / by zero issue
 - 09756 Record from foreign org in material receipt (POS) (105735084840) +fix +uat
    * applying user's access rules in all terminal windows
 - 09203_avoid setting IT staff as sales rep in orders that were generated from EDI +fix +uat
    * refined the logic with new specs that came out during QA
 - 09618 order-checkup printing problems (106933593952): +fix +uat
    * server/core changes: 
      * allowing an ITrxListener to deactivate itself in case it does not want to be called more than once 
    * printing client changes
      * using guava to decode the base64, got rid of javax.mail
      * Http endpoint: storing the received data in a file that's deleted once the print worked if the print failed, the file's content can be inspected
      * parent-pom: managing the guava version to be used (=>18.0)
      * cleaned up the printlcient's code
- 09761 Do research and improve logging of the stand alone printing client (104599264471) +feature +uat
    * adding a more usable JUL properties file that includes instructions.
    * minor changes (removing customer specifics, fixing a log level in the ESB to avoid log file flooding)
 - 09618 Bestellkontrolle Druck Probleme (106933593952): allowing an ITrxListener to deactivate itself in case it does not want to be called more than once +fix +uat
 - 09744_Dunning Report and UI changes +uat
 - 09668_Change quality based invoicing for fresh products +uat
 - 09203_avoid setting IT staff as sales rep in orders that were generated from EDI +uat +fix
 - 09743_option to show bugdet data in the balance report  +uat
 - 09746_Change of activity report adaptions +uat
 - 09670_Daily Lot for material storing +uat
 - 09671_accounting_always book discount onto actNo 150 +uat
 - 09704_Migration ADempiere nach metasfresh +fix
    * adjusting the included tab size in the windows order (sales+purchase), shipment and invoice (sales+purchase) to 800, 
    * additional fixes
 - 09748_Menu search box working weird +fix
 - 09709_metasfresh web (early prototyping, nothing to use yet)
 - 09733_Problems with Sepa-XML import into Mammut +uat
 - 09694_Fact Acct Summary incremental updates +uat
 - 09502_Support quality based invoicing +uat +fix
 - 09690_Report change of activity +uat +fix
 - 09726_Deep-copy support for AD_Roles +uat
 - 09722_Changes to the costing report +uat
 - 09717_Costprice is 0 on multiple selection in wareneingang pos +uat +fix
 - 09564_Report all not-completed Documents +uat 
 - 09718_Revenuereport Excel-Export broken +uat
 - 09687_DD order Copy with details +uat
