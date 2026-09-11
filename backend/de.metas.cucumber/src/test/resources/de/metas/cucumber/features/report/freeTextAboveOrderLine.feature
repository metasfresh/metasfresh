@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00144_Free_Text_Above_Order_Lines
@ghActions:run_on_executor2
Feature: Free text above an order line
## F00144: Free Text Above Order Lines
  A sales order line can carry a free text.
  It prints as a standalone block directly above that line's own article row on the order confirmation.
  The documents that follow from that line print the same block in the same shape: the delivery note of
  its shipment, and the invoice.
  No text means no block: an unset value prints nothing and a whitespace-only value prints nothing.
  A blank band emits no glyphs whether it printed or was suppressed.
  So the whitespace-only scenario also compares vertical positions, where extra space would show up.
  A long text wraps onto as many lines as it needs instead of being clipped.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    # render the real jasper report; the mock report service would archive a placeholder PDF instead
    And set sys config boolean value false for sys config de.metas.report.jasper.IsMockReportService
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    # another feature in this suite flips this flag; pin it so this scenario cannot depend on suite order
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    # Value differs from Name on purpose: the article row shows the Name, the line below it the Value
    # Keep these Names short:
    # - the reference leg spans the article-row band
    # - that band's product name stretches with overflow, so a wrapping Name inflates the leg
    And metasfresh contains M_Products:
      | Identifier | Value    | Name      |
      | productA   | ALPHA-NR | AlphaItem |
      | productB   | BETA-NR  | BetaItem  |
      | productC   | GAMMA-NR | GammaItem |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | ppA        | plv                    | productA     | 10.0     | PCE      |
      | ppB        | plv                    | productB     | 10.0     | PCE      |
      | ppC        | plv                    | productC     | 10.0     | PCE      |
    # dev-note: pin the payment term, so the order does not depend on the branch's default
    And metasfresh contains C_PaymentTerm
      | Identifier          |
      | customerPaymentTerm |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | C_PaymentTerm_ID    |
      | customer   | N        | Y          | ps                 | customerPaymentTerm |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | CH           | Y               | Y               |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Name      | ValidFrom  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | de_ch_tax  | Normal                        | de_ch_tax | 2021-04-02 | 2.5  | DE                       | CH                        |
      | ch_ch_tax  | Normal                        | ch_ch_tax | 2021-04-02 | 2.5  | CH                       | CH                        |

  @Id:S27486_10
  Scenario: A free text prints as the line directly above its own position
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    # position 10 carries no free text and only serves as the anchor above position 20
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lineLeadIn | order      | productA     | 1          |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered | DescriptionAboveLine   |
      | lineWithText | order      | productB     | 1          | Bitte gekuehlt liefern |
    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |
    Then the PDF archived for the record identified by "order" contains text "Bitte gekuehlt liefern"
    # nothing between position 10's product number and the block, and none between block and article row
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "ALPHA-NR" and text "Bitte gekuehlt liefern"
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Bitte gekuehlt liefern" and text "BetaItem"

  @Id:S27486_20
  Scenario: An unset free text prints no block above its position
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    # neither table carries a DescriptionAboveLine column, so the column stays NULL on both lines
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lineLeadIn | order      | productA     | 1          |
      | lineUnset  | order      | productB     | 1          |
    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |
    # position 20's article row follows position 10's product number with nothing in between
    Then in the PDF archived for the record identified by "order", exactly 0 lines appear between text "ALPHA-NR" and text "BetaItem"
    # and it is one band below it: the reference leg stays inside position 10, where no block can land
    And in the PDF archived for the record identified by "order", the vertical distance from text "ALPHA-NR" to text "BetaItem" equals the distance from text "AlphaItem" to text "ALPHA-NR"

  @Id:S27486_30
  Scenario: A whitespace-only free text prints no block and takes no vertical space
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    # position 20 is whitespace-only; position 30 has no free text at all and is the control
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lineLeadIn | order      | productA     | 1          |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered | DescriptionAboveLine                 |
      | lineWithBlank | order      | productB     | 1          | DescriptionAboveLine:WHITESPACE_ONLY |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | lineControl | order      | productC     | 1          |
    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |
    # no text was printed above position 20 ...
    Then in the PDF archived for the record identified by "order", exactly 0 lines appear between text "ALPHA-NR" and text "BetaItem"
    # ... position 20 sits as far below its predecessor as the control position 30 does ...
    And in the PDF archived for the record identified by "order", the vertical distance from text "ALPHA-NR" to text "BetaItem" equals the distance from text "BETA-NR" to text "GammaItem"
    # ... and that is one band, measured inside position 10 where no block of its own can land
    And in the PDF archived for the record identified by "order", the vertical distance from text "ALPHA-NR" to text "BetaItem" equals the distance from text "AlphaItem" to text "ALPHA-NR"

  @Id:S27486_40
  Scenario: A long free text wraps onto further lines instead of being clipped
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lineLeadIn | order      | productA     | 1          |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID | M_Product_ID | QtyEntered | DescriptionAboveLine |
      | lineWithLongText | order      | productB     | 1          | Diese Position wird in mehreren Teillieferungen versandt. Bitte die Ware bei Anlieferung sofort auf Vollstaendigkeit pruefen und jede Abweichung innerhalb von drei Werktagen schriftlich melden. Die fehlende Menge liefern wir dann als Nachlieferung. Teilmengen werden getrennt berechnet und koennen als separate Rechnung zugestellt werden. Ein Avis zur jeweiligen Restmenge erhalten Sie per E-Mail als Teillieferungsavis |
    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |
    # the block starts right below position 10's product number; two words on purpose, since this has to pin its FIRST wrapped line
    Then in the PDF archived for the record identified by "order", exactly 0 lines appear between text "ALPHA-NR" and text "Diese Position"
    # the block occupies at least three lines of its own before position 20's article row
    # text is sized for report_details_v2's full-width band: ~133 chars/line, so it wraps onto 4
    And in the PDF archived for the record identified by "order", at least 2 lines appear between text "Diese Position" and text "BetaItem"
    # the last word is still on the page, so nothing was clipped off the end
    And the PDF archived for the record identified by "order" contains text "Teillieferungsavis"

  @Id:S27486_50
  Scenario: A free text prints as the line directly above its own position on the delivery note
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    # position 10 carries no free text and only serves as the anchor above position 20
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lineLeadIn | order      | productA     | 1          |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered | DescriptionAboveLine |
      | lineWithText | order      | productB     | 1          | Kuehlkettenhinweis   |
    When the order identified by order is completed
    # IsToRecompute=N is the wait for the completion's recompute to land before the schedules are enqueued
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | scheduleLeadIn   | lineLeadIn     | N             |
      | scheduleWithText | lineWithText   | N             |
    # both schedules go into ONE workpackage, so both positions land on ONE delivery note
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | scheduleLeadIn        |
      | scheduleWithText      |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | scheduleWithText      | shipment   |
    And The jasper process is run
      | Value                 | Record_ID |
      | Lieferschein (Jasper) | shipment  |
    Then the PDF archived for the record identified by "shipment" contains text "Kuehlkettenhinweis"
    And in the PDF archived for the record identified by "shipment", exactly 0 lines appear between text "ALPHA-NR" and text "Kuehlkettenhinweis"
    And in the PDF archived for the record identified by "shipment", exactly 0 lines appear between text "Kuehlkettenhinweis" and text "BetaItem"

  @Id:S27486_60
  Scenario: A free text prints as the line directly above its own position on the invoice
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    # position 10 carries no free text and only serves as the anchor above position 20
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lineLeadIn | order      | productA     | 1          |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered | DescriptionAboveLine |
      | lineWithText | order      | productB     | 1          | Nachlieferungsavis   |
    When the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | scheduleLeadIn   | lineLeadIn     | N             |
      | scheduleWithText | lineWithText   | N             |
    # AUTO_SHIP_AND_INVOICE is off, so the invoice candidates only become invoiceable once delivered
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | scheduleLeadIn        |
      | scheduleWithText      |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | scheduleWithText      | shipment   |
    # QtyToInvoice=1 is the wait for the delivery to have reached the candidates
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID   | C_OrderLine_ID | QtyToInvoice |
      | invoiceCandidateLeadIn   | lineLeadIn     | 1            |
      | invoiceCandidateWithText | lineWithText   | 1            |
    # both candidates go into ONE selection, so both positions land on ONE invoice
    When process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID   |
      | invoiceCandidateLeadIn   |
      | invoiceCandidateWithText |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID   | C_Invoice_ID |
      | invoiceCandidateWithText | invoice      |
    And The jasper process is run
      | Value             | Record_ID |
      | Rechnung (Jasper) | invoice   |
    And the PDF archived for the record identified by "invoice" contains text "Nachlieferungsavis"
    And in the PDF archived for the record identified by "invoice", exactly 0 lines appear between text "ALPHA-NR" and text "Nachlieferungsavis"
    And in the PDF archived for the record identified by "invoice", exactly 0 lines appear between text "Nachlieferungsavis" and text "BetaItem"
