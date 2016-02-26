
# About this document

This file contains the tasks/issues which we implement in metasfresh, in a chronological fashion (latest first)

In this document, we sort of lean on http://www.semanticreleasenotes.org/, 
but since that spec doesn't (yet?) support multiple releases in one file, 
we only adhere to it as far as it's convenient in term of the documents structuring

Meanings of the categories we currently use:
 * it: was developed in the it baseline
 * uat: was developed in the uat baseline (important: this baseline is discontinued!)
 * prod: was deveoped in the prod baseline
 * fix: a bugfix
 * feature: a new feature

Additional notes:
 * The metasfresh source code is hosted at https://github.com/metasfresh/metasfresh
 * The metasfresh website is at http://metasfresh.com/
 * You can also follow us on twitter: @metasfresh

The actual release notes

# 4.5.4 (Upcoming Release)

## Features
 - FRESH-22: 09847 Import adempiere terminology as english trl into metasfresh (107370493155)
    * imported current en_US language translations from adempiere ERP
 - 09824 use locking API to prevent concurrent doc action on the same document (100066000843)

## Fixes
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