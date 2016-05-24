
# About this document

This file contains the tasks/issues which we implement in metasfresh, in a chronological fashion (latest first)

In this document, we sort of lean on http://www.semanticreleasenotes.org/, 

Additional notes:
 * The metasfresh source code is hosted at https://github.com/metasfresh/metasfresh
 * The metasfresh website is at http://metasfresh.com/en, http://metasfresh.com/ (german)
 * You can also follow us on twitter: @metasfresh (english), @metasfreshDE (german)

The actual release notes

# metasfresh 4.19.18 (upcoming)

## Features
 - FRESH-335 create an initial contributor's guideline

## Fixes
 - FRESH-311 Packvorschriften from different Org shown in Leergut
 - FRESH-278 Umsatzreport Geschäftspartner copy and modify details
 - FRESH-333 Procurement candidate prices not updated correctly
 - FRESH-307 New Organisation: Financial Data of existing Org
 - FRESH-326 Set the Correct Org in Fact_Acct_Summary
 - FRESH-331 Double click needed for weighing machine and occasional NPE
    * Improved logging and making the application more robust
 - FRESH-329: periods missing in dropdown because of no translations
 - FRESH-327 Subsequent change of logo not working correctly without cache reset
 - FRESH-312 Project cannot be compiled when downloading from github directly
    * removed references to our internal maven repo, fixed a wrong groupid and provided a public keystore for development purposes
 - FRESH-302 make inout print preview faster

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
