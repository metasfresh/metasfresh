@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00144_Free_Text_Above_Order_Lines
@ghActions:run_on_executor2
Feature: Free text lines print at their position on a sales order confirmation
  A user can put a free-text line between the article lines of a sales order -- a packing instruction, a
  blank spacer, a heading introducing the articles beneath it. On the order confirmation, each text line
  prints at the position it was placed, carrying only its own text: no article number, name, quantity, unit
  or price. Its stored scope (whether it belongs with the whole document or with the article run that
  follows it) is derived from where it was inserted, never set directly.

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
    # another feature in this suite flips this flag; pin it so this scenario cannot depend on suite order.
    # It must stay 'false': the steps read the archive back out of the database.
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    # Value differs from Name on purpose: the article row shows the Name, the product-number row below it
    # shows the Value, which gives each article two distinct text anchors. Keep the Names short - a
    # wrapping Name would change the line counts the assertions below rely on.
    And metasfresh contains M_Products:
      | Identifier | Value    | Name      |
      | productA   | ALPHA-NR | AlphaItem |
      | productB   | BETA-NR  | BetaItem  |
      | productC   | GAMMA-NR | GammaItem |
      | productD   | DELTA-NR | DeltaItem |
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
      | ppD        | plv                    | productD     | 10.0     | PCE      |
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

  @Id:S27486_TC1
  Scenario: A packing instruction and a group heading print at their positions, with their blank lines intact
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line10     | order      | productA     | 1          |
      | line20     | order      | productB     | 1          |
      | line30     | order      | productC     | 1          |
      | line40     | order      | productD     | 1          |

    # the packing instruction: entered above the very first article line, so nothing precedes it at all
    When a text line "topBlock" is inserted above the order line identified by "line10" with text:
      """
      Für die Truhe:
      Bitte saubere Schalen mit ordentlichem beklebten Deckel

      Kartons bitte mit "Truhe" beschriften
      """
    # the group heading: entered above the second group's first article, with an article line standing
    # above it -- its own value begins with a blank line, reproducing the separator row above the heading
    And a text line "groupHeading" is inserted above the order line identified by "line30" with text:
      """

      Sortimentsware
      """

    # the scope is derived from the insert position, never chosen by hand
    Then the text line identified by "topBlock" has TextLineScope "Document"
    And the text line identified by "groupHeading" has TextLineScope "Following"

    # the persisted value is read back, blank lines and all -- not assumed to have round-tripped
    And the text line identified by "topBlock" has text:
      """
      Für die Truhe:
      Bitte saubere Schalen mit ordentlichem beklebten Deckel

      Kartons bitte mit "Truhe" beschriften
      """
    And the text line identified by "groupHeading" has text:
      """

      Sortimentsware
      """

    # inserting text lines never renumbered the article lines around them
    And the order line identified by "line10" still has Line 10
    And the order line identified by "line20" still has Line 20
    And the order line identified by "line30" still has Line 30
    And the order line identified by "line40" still has Line 40

    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |

    Then an AD_Archive exists for the record identified by "order"

    # the packing instruction's own two non-blank lines sit on consecutive visual lines
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Für die Truhe:" and text "Bitte saubere Schalen mit ordentlichem beklebten Deckel"
    # the block prints directly above the first article -- nothing stands between them
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Kartons bitte mit \"Truhe\" beschriften" and text "AlphaItem"
    # ordinary article-to-article adjacency is unaffected by the text lines elsewhere in the document
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "ALPHA-NR" and text "BetaItem"
    # nothing else prints between the second group's preceding article and its own heading -- this is
    # exactly what would break if a text row fell through the article band instead of its own
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "BETA-NR" and text "Sortimentsware"
    # the heading prints directly above the first article of the group it introduces
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Sortimentsware" and text "GammaItem"
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "GAMMA-NR" and text "DeltaItem"

    # the packing instruction's own embedded blank line occupies exactly one full line: the span across it
    # (from the line before the blank to the line after it) covers exactly twice the height of a single
    # ordinary line within the very same block (from its first line to its second) -- not a collapsed row
    And in the PDF archived for the record identified by "order", the vertical distance from text "Bitte saubere Schalen mit ordentlichem beklebten Deckel" to text "Kartons bitte mit \"Truhe\" beschriften" is 2 times the distance from text "Für die Truhe:" to text "Bitte saubere Schalen mit ordentlichem beklebten Deckel"

    # nothing is printed on top of anything else, anywhere in the document
    And the PDF archived for the record identified by "order" has no overlapping text
